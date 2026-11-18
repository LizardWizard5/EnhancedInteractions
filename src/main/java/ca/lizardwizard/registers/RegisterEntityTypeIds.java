package ca.lizardwizard.registers;

import ca.lizardwizard.EnhancedInteractions;
import net.minecraft.core.registries.Registries;
//? if >=26.2 {
import net.minecraft.resources.Identifier;
//?} else {
/*import net.minecraft.resources.ResourceLocation;
*///?}
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public class RegisterEntityTypeIds {
    public static final ResourceKey<EntityType<?>> ECHO_SHARD_KEY = create("echo_shard");

    private static ResourceKey<EntityType<?>> create(String name) {
        //? if >=26.2 {
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(EnhancedInteractions.MOD_ID, name));
        //?} else {
        /*return ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(EnhancedInteractions.MOD_ID, name));
        *///?}
    }
}
