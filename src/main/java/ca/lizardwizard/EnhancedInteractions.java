package ca.lizardwizard;

import ca.lizardwizard.entity.projectile.echoshard.EchoShardThrowBehaviorRegister;
import ca.lizardwizard.registers.RegisterDispenserBehaviors;
import ca.lizardwizard.registers.RegisterEntityTypes;
import net.fabricmc.api.ModInitializer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnhancedInteractions implements ModInitializer {
	public static final String MOD_ID = "enhancedinteractions";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("EnhancedInteractions: Initializing");
		LOGGER.info("EnhancedInteractions: Registering Dispenser Behaviors");
		RegisterDispenserBehaviors.register();
		LOGGER.info("EnhancedInteractions: Dispenser behaviors registered");
		LOGGER.info("EnhancedInteractions: Registering Entities");
		RegisterEntityTypes.initialize();
		LOGGER.info("EnhancedInteractions: Entities registered");
		LOGGER.info("EnhancedInteractions: Registering Echo Shard behaviors");
		EchoShardThrowBehaviorRegister.register();
		LOGGER.info("EnhancedInteractions: Echo Shard behaviors registered");

		LOGGER.info("EhnacedInteractions: Initalized!");

	}




}