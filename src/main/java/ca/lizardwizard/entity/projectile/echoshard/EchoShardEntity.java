package ca.lizardwizard.entity.projectile.echoshard;

import ca.lizardwizard.registers.RegisterEntityTypes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

// Behavior of the EchoShard is very similar to Eye of Ender
public class EchoShardEntity extends EyeOfEnder {

    public EchoShardEntity(final EntityType<? extends EchoShardEntity> entityType, final Level level) {super(entityType, level);}

    public EchoShardEntity(final Level level, final double x, final double y, final double z){
        this(RegisterEntityTypes.ECHO_SHARD, level);
        this.setPos(x, y, z);
    }

}
