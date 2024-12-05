package bblsom.handlers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
        for(Block block : ModRegistry.BLOCKS_BUTTONS) {
            registerModels(Item.getItemFromBlock(block));
        }
        for(Block block : ModRegistry.BLOCKS_LEVERS) {
            registerModels(Item.getItemFromBlock(block));
        }
        for(Block block : ModRegistry.BLOCKS_HONEY) {
            registerModels(Item.getItemFromBlock(block));
        }
    }

    private static void registerModels(Item... values) {
        for(Item entry : values) {
            ModelLoader.setCustomModelResourceLocation(entry, 0, new ModelResourceLocation(entry.getRegistryName(), "inventory"));
        }
    }
}