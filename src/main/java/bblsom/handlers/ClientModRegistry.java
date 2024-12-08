package bblsom.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
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
        for(Item item : ModRegistry.ITEMS_BUTTONS) {
            registerModels(item);
        }
        for(Item item : ModRegistry.ITEMS_LEVERS) {
            registerModels(item);
        }
        for(Item item : ModRegistry.ITEMS_SIGNS) {
            registerModels(item);
        }
        for(Item item : ModRegistry.ITEMS_HONEY) {
            registerModels(item);
        }
        for(Item item : ModRegistry.ITEMS_BOATS) {
            registerModels(item);
        }
        for(Item item : ModRegistry.ITEMS_HORSEARMOR) {
            registerModels(item);
        }
        for(Item item : ModRegistry.ITEMS_DOORS) {
            registerModels(item);
        }
        for(Item item : ModRegistry.ITEMS_TRAPDOORS) {
            registerModels(item);
        }
        for(Item item : ModRegistry.ITEMS_PRESSUREPLATES) {
            registerModels(item);
        }
        for(Item item : ModRegistry.ITEMS_PRESSUREPLATESWEIGHTED) {
            registerModels(item);
        }
        for(Block block : ModRegistry.BLOCKS_DOORS) {
            registerDoorModels(block);
        }
    }

    private static void registerModels(Item... values) {
        for(Item entry : values) {
            ModelLoader.setCustomModelResourceLocation(entry, 0, new ModelResourceLocation(entry.getRegistryName(), "inventory"));
        }
    }
    
    private static void registerDoorModels(Block... values) {
        for(Block entry : values) {
            ModelLoader.setCustomStateMapper(entry, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        }
    }
}