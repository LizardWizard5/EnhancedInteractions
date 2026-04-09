package ca.lizardwizard.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Mixin(net.minecraft.world.level.block.FarmBlock.class)
public class FarmlandMixin {
    List<String > crops = Arrays.asList(
        "minecraft:wheat_seeds",
        "minecraft:beetroot_seeds",
        "minecraft:potato",
        "minecraft:carrot"
    );
    @Inject(at =@At("TAIL"), method ="fallOn")
    private void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, double d, CallbackInfo ci) {
        if (level instanceof ServerLevel serverLevel) {//Check if level is a server level
            if(entity instanceof ItemEntity itemEntity) {//Check if the entity is an item entity
                if(crops.contains(itemEntity.getItem().getItem().toString())) {//Check if item is within the list of crops
                    int count = itemEntity.getItem().getCount();//Get the count of the item stack
                    if(count <= 1) {//If the count is 1 or less, discard the item entity
                        itemEntity.discard();
                    }
                    else {//Else reduce the count of the item stack by 1
                        itemEntity.setItem(itemEntity.getItem().copyWithCount(itemEntity.getItem().getCount() - 1));
                    }
                    //Find exactly which crop it is and set the block above the farmland to the corresponding crop block
                    BlockPos above = blockPos.above(1);
                    switch (itemEntity.getItem().getItem().toString()) {
                        case "minecraft:wheat_seeds" -> serverLevel.setBlock(above, net.minecraft.world.level.block.Blocks.WHEAT.defaultBlockState(), 3);
                        case "minecraft:beetroot_seeds" -> serverLevel.setBlock(above, net.minecraft.world.level.block.Blocks.BEETROOTS.defaultBlockState(), 3);
                        case "minecraft:potato" -> serverLevel.setBlock(above, net.minecraft.world.level.block.Blocks.POTATOES.defaultBlockState(), 3);
                        case "minecraft:carrot" -> serverLevel.setBlock(above, net.minecraft.world.level.block.Blocks.CARROTS.defaultBlockState(), 3);
                    }
                }
            }
        }
    }
}
