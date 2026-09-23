package ca.lizardwizard.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(HopperBlockEntity.class)
public abstract class HopperMixin {


    @Inject(at = @At("TAIL"), method = "suckInItems", cancellable = true)
    private static void onSuckInItems(Level level, Hopper hopper, CallbackInfoReturnable<Boolean> cir) {
        if(level.getServer() == null) return;
        if(hopper instanceof MinecartHopper) return; //To balance it out, no hopper minecart
        BlockPos farmlandPos = BlockPos.containing(hopper.getLevelX(), hopper.getLevelY() + 1.0, hopper.getLevelZ());
        if((level.getBlockState(farmlandPos).getBlock() instanceof FarmlandBlock ||level.getBlockState(farmlandPos).getBlock() instanceof SoulSandBlock )){

            BlockPos cropPos = farmlandPos.above();
            BlockState cropState = level.getBlockState(cropPos);
            ServerLevel sLevel = (ServerLevel) level;
            List<ItemStack> drops = Block.getDrops(cropState, sLevel, cropPos, null);
            Item seed_item;//Seed item is used to remove 1 of whatever item is used to plant the crop. (Pretty much for specifying removal of wheat seed instead of the wheat)

            //These conditionals are used to set the finer behavior for setBlock and the difference in States.
            //It might be possible to remove some duplicate logic, but I think the conditionals would be needed anyways and I don't want to further thinker with what's already working.
            if(cropState.getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(cropState)){
                seed_item = cropBlock.asItem();
                sLevel.setBlock(cropPos, cropBlock.getStateForAge(0), Block.UPDATE_ALL);
            } else if (cropState.getBlock() instanceof NetherWartBlock wartBlock &&(cropState.getValue(NetherWartBlock.AGE) >= NetherWartBlock.MAX_AGE)) {
                sLevel.setBlock(cropPos,cropState.setValue(NetherWartBlock.AGE, 0) , Block.UPDATE_ALL);
                seed_item=wartBlock.asItem();

            }
            else{

                return;//Return if no crop is found
            }
            //For pushing crop items to hopper
            for (ItemStack drop : drops) {
                if (drop.isEmpty()) continue;
                //Remove 1 seed to accommodate the replant
                if (drop.is(seed_item)) {
                    drop.shrink(1);

                }
                //If hopper is full, the plant still replants and one is removed but the rest of the items just spit back on the floor
                ItemStack leftover = HopperBlockEntity.addItem(null, hopper, drop, null);
                if (!leftover.isEmpty()) {
                    Block.popResource(sLevel, cropPos, leftover);
                }
            }
            cir.setReturnValue(true);
        }
    }

}

