package bblsom.handlers;

import bblsom.blocks.CustomBlockLeaves;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import bblsom.BBLSOhMy;

@Mod.EventBusSubscriber(modid = BBLSOhMy.MODID, value = Side.CLIENT)
public class ClientModRegistry {
    
    @SubscribeEvent
    public static void modelRegisterEvent(ModelRegistryEvent event) {
        registerModels(ModRegistry.ITEMS_BUTTONS.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_LEVERS.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_SIGNS.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_HONEY.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_BOATS.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_HORSEARMOR.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_DOORS.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_TRAPDOORS.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_PRESSUREPLATES.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_PRESSUREPLATESWEIGHTED.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_DIRT.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_FALLINGDIRT.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_GRASS.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_FARMLAND.toArray(new Item[0]));
        registerLeavesModels(ModRegistry.ITEMS_LEAVES.toArray(new Item[0]));
        registerModels(ModRegistry.ITEMS_MUSHROOM.toArray(new Item[0]));
        registerHugeMushroomModels(ModRegistry.ITEMS_HUGEMUSHROOM.toArray(new Item[0]));
        
        registerDoorMapper(ModRegistry.BLOCKS_DOORS.toArray(new Block[0]));
        registerLeavesMapper(ModRegistry.BLOCKS_LEAVES.toArray(new Block[0]));
    }

    private static void registerModels(Item... values) {
        for(Item entry : values) {
            ModelLoader.setCustomModelResourceLocation(entry, 0, new ModelResourceLocation(entry.getRegistryName(), "inventory"));
        }
    }
    
    private static void registerLeavesModels(Item... values) {
        for(Item entry : values) {
            for(CustomBlockLeaves.AgePhase phase : CustomBlockLeaves.AgePhase.values()) {
                ModelLoader.setCustomModelResourceLocation(entry, phase.getMetadata(), new ModelResourceLocation(entry.getRegistryName().getNamespace() + ":" + phase.getName() + "_" + entry.getRegistryName().getPath(), "inventory"));
            }
        }
    }
    
    private static void registerHugeMushroomModels(Item... values) {
        for(Item entry : values) {
            ModelLoader.setCustomModelResourceLocation(entry, BlockHugeMushroom.EnumType.ALL_INSIDE.getMetadata(), new ModelResourceLocation(entry.getRegistryName(), "inventory"));
        }
    }
    
    private static void registerDoorMapper(Block... values) {
        for(Block entry : values) {
            ModelLoader.setCustomStateMapper(entry, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        }
    }
    
    private static void registerLeavesMapper(Block... values) {
        for(Block entry : values) {
            ModelLoader.setCustomStateMapper(entry, new StateMap.Builder().withName(CustomBlockLeaves.AGE).withSuffix("_" + entry.getRegistryName().getPath()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        }
    }
    
    @SubscribeEvent
    public static void blockColorHandlerEvent(ColorHandlerEvent.Block event) {
        final BlockColors colors = event.getBlockColors();
        
        colors.registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
                                                                 worldIn != null && pos != null ?
                                                                 BiomeColorHelper.getGrassColorAtPos(worldIn, pos) :
                                                                 ColorizerGrass.getGrassColor(0.5D, 1.0D),
                                                         ModRegistry.BLOCKS_GRASS.toArray(new Block[0]));
        
        colors.registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
                                                                 worldIn != null && pos != null ?
                                                                 BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) :
                                                                 ColorizerFoliage.getFoliageColorBasic(),
                                                         ModRegistry.BLOCKS_LEAVES.toArray(new Block[0]));
    }
    
    @SubscribeEvent
    public static void itemColorHandlerEvent(ColorHandlerEvent.Item event) {
        final BlockColors colors = event.getBlockColors();
        
        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
			IBlockState iblockstate = ((ItemBlock)stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
			return colors.colorMultiplier(iblockstate, null, null, tintIndex);
		}, ModRegistry.BLOCKS_GRASS.toArray(new Block[0]));
        
        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            IBlockState iblockstate = ((ItemBlock)stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
            return colors.colorMultiplier(iblockstate, null, null, tintIndex);
        }, ModRegistry.BLOCKS_LEAVES.toArray(new Block[0]));
    }
}