package ca.lizardwizard;

import net.fabricmc.api.ModInitializer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BoneMealItem;
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
        LOGGER.info("EnhancedInteractions: Initialized");
        //Dispenser cauldron filling with water, powder snow, and lava buckets
        dispenserCauldronBehavior(Items.WATER_BUCKET, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3));
        dispenserCauldronBehavior(Items.LAVA_BUCKET, Blocks.LAVA_CAULDRON.defaultBlockState());
        dispenserCauldronBehavior(Items.POWDER_SNOW_BUCKET, Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL,3));
        //Dispenser:emptying cauldron with buckets and reimplementing default bucket behavior.
        dispenserBucketCauldronBehavior();
        //Dispenser crop planting on farmland
        dispenserCropBehavior(Items.WHEAT_SEEDS, Blocks.WHEAT.defaultBlockState(),Blocks.FARMLAND);
        dispenserCropBehavior(Items.BEETROOT_SEEDS, Blocks.BEETROOTS.defaultBlockState(),Blocks.FARMLAND);
        dispenserCropBehavior(Items.POTATO, Blocks.POTATOES.defaultBlockState(),Blocks.FARMLAND);
        dispenserCropBehavior(Items.CARROT, Blocks.CARROTS.defaultBlockState(),Blocks.FARMLAND);
        dispenserCropBehavior(Items.PUMPKIN_SEEDS, Blocks.PUMPKIN_STEM.defaultBlockState(),Blocks.FARMLAND);
        dispenserCropBehavior(Items.MELON_SEEDS, Blocks.MELON_STEM.defaultBlockState(),Blocks.FARMLAND);
        dispenserCropBehavior(Items.NETHER_WART, Blocks.NETHER_WART.defaultBlockState(),Blocks.SOUL_SAND);
        //Dispenser planting sapling on dirt/grass block
        dispenserSaplingBehavior(Items.OAK_SAPLING, Blocks.OAK_SAPLING.defaultBlockState(), BlockTags.DIRT);
        dispenserSaplingBehavior(Items.SPRUCE_SAPLING, Blocks.SPRUCE_SAPLING.defaultBlockState(), BlockTags.DIRT);
        dispenserSaplingBehavior(Items.BIRCH_SAPLING, Blocks.BIRCH_SAPLING.defaultBlockState(), BlockTags.DIRT);
        dispenserSaplingBehavior(Items.JUNGLE_SAPLING, Blocks.JUNGLE_SAPLING.defaultBlockState(), BlockTags.DIRT);
        dispenserSaplingBehavior(Items.ACACIA_SAPLING, Blocks.ACACIA_SAPLING.defaultBlockState(), BlockTags.DIRT);
        dispenserSaplingBehavior(Items.CHERRY_SAPLING, Blocks.CHERRY_SAPLING.defaultBlockState(), BlockTags.DIRT);
        dispenserSaplingBehavior(Items.DARK_OAK_SAPLING, Blocks.DARK_OAK_SAPLING.defaultBlockState(), BlockTags.DIRT);
        dispenserSaplingBehavior(Items.MANGROVE_PROPAGULE, Blocks.MANGROVE_PROPAGULE.defaultBlockState(), BlockTags.DIRT);
        //Nether trees sapling on nylium block
        dispenserSaplingBehavior(Items.CRIMSON_FUNGUS,Blocks.CRIMSON_FUNGUS.defaultBlockState(), BlockTags.NYLIUM,BlockTags.DIRT);
        dispenserSaplingBehavior(Items.WARPED_FUNGUS,Blocks.WARPED_FUNGUS.defaultBlockState(), BlockTags.NYLIUM,BlockTags.DIRT);
        //Dispenser apply bonemeal to any bonemealable block
        dispenserBonemealBehavior();
        LOGGER.info("EnhancedInteractions: Dispenser behaviors registered");



    }

    private void dispenserBonemealBehavior() {
        Item bone_meal = Items.BONE_MEAL;

        DispenserBlock.registerBehavior(bone_meal, new DefaultDispenseItemBehavior(){
            @Override
            protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
                LevelAccessor level = blockSource.level();
                BlockPos targetPos = blockSource.pos()
                        .relative(blockSource.state().getValue(DispenserBlock.FACING));
                BlockState targetState = level.getBlockState(targetPos);
                //If crop is completely grown, do regular shoot out onto ground
                if(targetState.getBlock() instanceof CropBlock){
                    CropBlock crop = (CropBlock) targetState.getBlock();
                    if(crop.getAge(targetState) == crop.getMaxAge()){
                        return super.execute(blockSource, stack);
                    }

                }
                //The assumption behind this is that all blocks that can be Bonemeal'd are based on BonemealableBlock
                if(targetState.getBlock() instanceof BonemealableBlock){
                    ((BonemealableBlock) targetState.getBlock()).performBonemeal(blockSource.level(),level.getRandom(),targetPos,targetState);
                    stack.shrink(1);
                    return stack;
                }
                return super.execute(blockSource, stack);
            }
        });
    }

    private static void dispenserCauldronBehavior(Item bucketItem, BlockState resultState) {
        DispenserBlock.registerBehavior(bucketItem, new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
                LevelAccessor level = blockSource.level();
                BlockPos targetPos = blockSource.pos()
                        .relative(blockSource.state().getValue(DispenserBlock.FACING));
                BlockState targetState = level.getBlockState(targetPos);

                if (targetState.is(Blocks.CAULDRON)) {
                    level.setBlock(targetPos, resultState, 3);
                    stack.shrink(1);
                    return new ItemStack(Items.BUCKET);
                }
                else if(targetState.is(Blocks.AIR)){
                    //Determine block.
                    BlockState blockstate;
                    switch (bucketItem.toString()){
                        case "minecraft:water_bucket":
                            blockstate= Blocks.WATER.defaultBlockState();
                            break;
                        case "minecraft:lava_bucket":
                            blockstate= Blocks.LAVA.defaultBlockState();
                            break;
                        case "minecraft:powder_snow_bucket":
                            blockstate = Blocks.POWDER_SNOW.defaultBlockState();
                            break;
                        default:
                            blockstate = Blocks.AIR.defaultBlockState();
                            break;
                    }
                    level.setBlock(targetPos, blockstate, 3);
                    stack.shrink(1);
                    return new ItemStack(Items.BUCKET);
                }

                return super.execute(blockSource, stack);
            }
        });
    }

    private static void dispenserBucketCauldronBehavior() {
        DispenserBlock.registerBehavior(Items.BUCKET, new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
                LevelAccessor level = blockSource.level();
                BlockPos targetPos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
                BlockState targetState = level.getBlockState(targetPos);

                Item filled = null;
                BlockState emptied = null;


                if (targetState.is(Blocks.WATER_CAULDRON) && targetState.getValue(LayeredCauldronBlock.LEVEL) == 3) {
                    filled = Items.WATER_BUCKET;
                } else if (targetState.is(Blocks.LAVA_CAULDRON)) {
                    filled = Items.LAVA_BUCKET;
                } else if (targetState.is(Blocks.POWDER_SNOW_CAULDRON) && targetState.getValue(LayeredCauldronBlock.LEVEL) == 3) {
                    filled = Items.POWDER_SNOW_BUCKET;
                }
                if (filled != null) {
                    emptied = Blocks.CAULDRON.defaultBlockState();
                }

                else if (targetState.is(Blocks.WATER) && targetState.getFluidState().isSource()) {
                    filled = Items.WATER_BUCKET;
                } else if (targetState.is(Blocks.LAVA) && targetState.getFluidState().isSource()) {
                    filled = Items.LAVA_BUCKET;
                } else if (targetState.is(Blocks.POWDER_SNOW)) {
                    filled = Items.POWDER_SNOW_BUCKET;
                }
                if (filled != null && emptied == null) {
                    emptied = Blocks.AIR.defaultBlockState();
                }

                if (filled != null) {
                    level.setBlock(targetPos, emptied, 3);
                    return consumeWithRemainder(blockSource, stack, new ItemStack(filled));
                }
                return super.execute(blockSource, stack);
            }
        });
    }

    private static void dispenserCropBehavior(Item seedItem, BlockState cropState, Block block) {
        DispenserBlock.registerBehavior(seedItem, new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
                LevelAccessor level = blockSource.level();
                BlockPos targetPos = blockSource.pos()
                        .relative(blockSource.state().getValue(DispenserBlock.FACING));
                BlockState targetState = level.getBlockState(targetPos);

                if (targetState.is(block) && level.getBlockState(targetPos.above()).isAir()) {
                    level.setBlock(targetPos.above(), cropState, 3);
                    stack.shrink(1);
                    return stack;
                }

                return super.execute(blockSource, stack);
            }
        });
    }
    @SafeVarargs
    private static void dispenserSaplingBehavior(Item saplingItem, BlockState saplingState, TagKey<Block>... tags) {
        DispenserBlock.registerBehavior(saplingItem, new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
                LevelAccessor level = blockSource.level();
                BlockPos forward = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
                BlockPos forwardDown = forward.below();
                Block dirtBlock = level.getBlockState(forwardDown).getBlock();
                BlockState dirtState = level.getBlockState(forwardDown);
                if (level.getBlockState(forward).isAir()) {
                    for (TagKey<Block> tag : tags) {
                        if (dirtState.is(tag)) {
                            level.setBlock(forward, saplingState, 3);
                            stack.shrink(1);
                            return stack;
                        }
                    }
                }

                return super.execute(blockSource, stack);
            }
        });
    }


}