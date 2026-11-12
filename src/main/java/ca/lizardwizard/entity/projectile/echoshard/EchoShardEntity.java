package ca.lizardwizard.entity.projectile.echoshard;

import ca.lizardwizard.registers.RegisterEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.level.Level;

// Behavior of the EchoShard is very similar to Eye of Ender
public class EchoShardEntity extends EyeOfEnder {

    public EchoShardEntity(final EntityType<? extends EchoShardEntity> entityType, final Level level) {super(entityType, level);}

    public EchoShardEntity(final Level level, final double x, final double y, final double z){
        this(RegisterEntityTypes.ECHO_SHARD, level);
        this.setPos(x, y, z);
    }

}