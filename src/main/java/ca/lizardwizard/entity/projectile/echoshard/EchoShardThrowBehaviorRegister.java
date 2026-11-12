package ca.lizardwizard.entity.projectile.echoshard;


import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class EchoShardThrowBehaviorRegister {
    public static void register() {
        UseItemCallback.EVENT.register(EchoShardThrowBehaviorRegister::use);
    }

    // Same shape as Item.use(), so it reads like an item class
    private static InteractionResultHolder<ItemStack> use(Player player, Level level, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.is(Items.ECHO_SHARD)) return InteractionResultHolder.pass(stack);

        Optional<GlobalPos> death = player.getLastDeathLocation();
        if (death.isEmpty() || !death.get().dimension().equals(level.dimension()))
            return InteractionResultHolder.pass(stack);          // no death, or death was in another dimension

        if (level instanceof ServerLevel serverLevel) {
            EchoShardEntity shard = new EchoShardEntity(level, player.getX(), player.getY(0.5), player.getZ());
            shard.setItem(stack);
            shard.signalTo(death.get().pos());//Vec3.atCenterOf(death.get().pos())
            serverLevel.addFreshEntity(shard);
            serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENDER_PEARL_THROW, SoundSource.PLAYERS,
                    0.5F, 0.4F / (serverLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            stack.consume(1, player);
        }



        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}