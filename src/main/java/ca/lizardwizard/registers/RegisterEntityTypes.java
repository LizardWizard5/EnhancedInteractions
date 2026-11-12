package ca.lizardwizard.registers;

import ca.lizardwizard.EnhancedInteractions;
import ca.lizardwizard.entity.projectile.echoshard.EchoShardEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class RegisterEntityTypes {

    //Echo Shard entity type for the throwing of Echo Shard


   /* public static final EntityType<EchoShardEntity> ECHO_SHARD = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ECHO_SHARD_KEY,
            EntityType.Builder.<EchoShardEntity>of(EchoShardEntity::new, MobCategory.MISC)
                    .noLootTable()
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(4)
                    .build(ECHO_SHARD_KEY));*/

    public static final EntityType<EchoShardEntity> ECHO_SHARD = register(
            RegisterEntityTypeIds.ECHO_SHARD_KEY,
            EntityType.Builder.<EchoShardEntity>of(EchoShardEntity::new, MobCategory.MISC)
                    .noLootTable()
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(4)
            );

    private static <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    private static <T extends LivingEntity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder, AttributeSupplier.Builder attributes) {
        EntityType<T> type = register(key, builder);
        FabricDefaultAttributeRegistry.register(type, attributes);
        return type;
    }

    public static void initialize() {
    }

}

