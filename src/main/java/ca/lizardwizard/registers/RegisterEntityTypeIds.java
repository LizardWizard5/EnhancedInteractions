package ca.lizardwizard.registers;

import ca.lizardwizard.EnhancedInteractions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import org.intellij.lang.annotations.Identifier;

public class RegisterEntityTypeIds {
    public static final ResourceKey<EntityType<?>> ECHO_SHARD_KEY = create("echo_shard");

    private static ResourceKey<EntityType<?>> create(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(EnhancedInteractions.MOD_ID, name));
    }
}
