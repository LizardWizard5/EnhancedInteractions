package ca.lizardwizard.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(net.minecraft.world.level.block.FarmBlock.class)
public class FarmlandMixin {
    Item[] crops = new Item[] {
        net.minecraft.world.item.Items.WHEAT_SEEDS,
        net.minecraft.world.item.Items.BEETROOT_SEEDS,
        net.minecraft.world.item.Items.POTATO,
        net.minecraft.world.item.Items.CARROT
    };
    @Inject(at =@At("TAIL"), method ="fallOn")
    private void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, double d, CallbackInfo ci) {
        if (level instanceof ServerLevel serverLevel) {
            if(entity instanceof ItemEntity itemEntity) {
                List<Item> cropList = List.of(crops);
                if(cropList.contains(itemEntity.getItem().getItem())) {
                    itemEntity.setItem(itemEntity.getItem().copyWithCount(itemEntity.getItem().getCount() -1));

                }
            }
        }
    }
}
