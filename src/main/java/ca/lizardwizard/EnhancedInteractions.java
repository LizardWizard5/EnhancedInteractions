package ca.lizardwizard;

import net.fabricmc.api.ModInitializer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
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

        DispenserBlock.registerBehavior(Items.WATER_BUCKET, new DefaultDispenseItemBehavior(){
            protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
                //Get details of block in front of dispenser
                LevelAccessor levelAccessor = blockSource.level();
                BlockPos blockPos = blockSource.pos().relative((Direction)blockSource.state().getValue(DispenserBlock.FACING));
                BlockState blockState = levelAccessor.getBlockState(blockPos);
                //If the block is a cauldrons, fill it with water and return an empty bucket
                if(blockState.is(net.minecraft.world.level.block.Blocks.CAULDRON)) {
                    levelAccessor.setBlock(
                            blockPos,
                            Blocks.WATER_CAULDRON
                                    .defaultBlockState()
                                    .setValue(LayeredCauldronBlock.LEVEL, 3),
                            3
                    );
                    return new ItemStack(Items.BUCKET);
                }
                //Else, execute the default behavior of dispensing a water bucket
                return super.execute(blockSource, stack);
            }
        });
        DispenserBlock.registerBehavior(Items.LAVA_BUCKET, new DefaultDispenseItemBehavior(){
            protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
                //Get details of block in front of dispenser
                LevelAccessor levelAccessor = blockSource.level();
                BlockPos blockPos = blockSource.pos().relative((Direction)blockSource.state().getValue(DispenserBlock.FACING));
                BlockState blockState = levelAccessor.getBlockState(blockPos);
                //If the block is a cauldrons, fill it with water and return an empty bucket
                if(blockState.is(net.minecraft.world.level.block.Blocks.CAULDRON)) {

                    levelAccessor.setBlock(
                            blockPos,
                            Blocks.LAVA_CAULDRON.defaultBlockState(),
                            3
                    );

                    return new ItemStack(Items.BUCKET);
                }
                //Else, execute the default behavior of dispensing a water bucket
                return super.execute(blockSource, stack);
            }
        });
        DispenserBlock.registerBehavior(Items.POWDER_SNOW_BUCKET, new DefaultDispenseItemBehavior(){
            protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
                //Get details of block in front of dispenser
                LevelAccessor levelAccessor = blockSource.level();
                BlockPos blockPos = blockSource.pos().relative((Direction)blockSource.state().getValue(DispenserBlock.FACING));
                BlockState blockState = levelAccessor.getBlockState(blockPos);
                //If the block is a cauldrons, fill it with water and return an empty bucket
                if(blockState.is(net.minecraft.world.level.block.Blocks.CAULDRON)) {
                    levelAccessor.setBlock(
                            blockPos,
                            Blocks.POWDER_SNOW_CAULDRON
                                    .defaultBlockState()
                                    .setValue(LayeredCauldronBlock.LEVEL, 3),
                            3
                    );
                    return new ItemStack(Items.BUCKET);
                }
                //Else, execute the default behavior of dispensing a water bucket
                return super.execute(blockSource, stack);
            }
        });
        DispenserBlock.registerBehavior(Items.BUCKET, new DefaultDispenseItemBehavior(){
            protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
                //Get details of block in front of dispenser
                LevelAccessor levelAccessor = blockSource.level();
                BlockPos blockPos = blockSource.pos().relative((Direction)blockSource.state().getValue(DispenserBlock.FACING));
                BlockState blockState = levelAccessor.getBlockState(blockPos);
                //If the block is a water cauldron, empty it and return a water bucket
                if(blockState.is(net.minecraft.world.level.block.Blocks.WATER_CAULDRON)) {
                    levelAccessor.setBlock(
                            blockPos,
                            Blocks.CAULDRON.defaultBlockState(),
                            3
                    );
                    return new ItemStack(Items.WATER_BUCKET);
                }
                //If the block is a lava cauldron, empty it and return a lava bucket
                else if(blockState.is(net.minecraft.world.level.block.Blocks.LAVA_CAULDRON)) {
                    levelAccessor.setBlock(
                            blockPos,
                            Blocks.CAULDRON.defaultBlockState(),
                            3
                    );
                    return new ItemStack(Items.LAVA_BUCKET);
                }
                //If the block is a powder snow cauldron, empty it and return a powder snow bucket
                else if(blockState.is(net.minecraft.world.level.block.Blocks.POWDER_SNOW_CAULDRON)) {
                    levelAccessor.setBlock(
                            blockPos,
                            Blocks.CAULDRON.defaultBlockState(),
                            3
                    );
                    return new ItemStack(Items.POWDER_SNOW_BUCKET);
                }
                //Else, execute the default behavior of dispensing an empty bucket
                return super.execute(blockSource, stack);
            }
        });
		LOGGER.info("Hello Fabric world!");
	}
}