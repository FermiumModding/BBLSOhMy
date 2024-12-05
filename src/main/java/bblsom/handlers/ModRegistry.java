package bblsom.handlers;

import bblsom.blocks.*;
import bblsom.entity.CustomEntityBoat;
import bblsom.item.CustomItemBoat;
import bblsom.item.CustomItemHorseArmor;
import bblsom.item.CustomItemSign;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import bblsom.BBLSOhMy;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = BBLSOhMy.MODID)
public class ModRegistry {
        
        public static final List<Item> ITEMS_BUTTONS = new ArrayList<>();
        public static final List<Item> ITEMS_LEVERS = new ArrayList<>();
        public static final List<Item> ITEMS_SIGNS = new ArrayList<>();
        public static final List<Item> ITEMS_HONEY = new ArrayList<>();
        public static final List<CustomItemBoat> ITEMS_BOATS = new ArrayList<>();
        public static final List<Item> ITEMS_HORSEARMOR = new ArrayList<>();
        public static final List<Block> BLOCKS_BUTTONS = new ArrayList<>();
        public static final List<Block> BLOCKS_LEVERS = new ArrayList<>();
        public static final List<Block> BLOCKS_SIGNS = new ArrayList<>();
        public static final List<Block> BLOCKS_HONEY = new ArrayList<>();
        
        public static final SoundEvent BLOCK_HONEY_SLIDE = new SoundEvent(new ResourceLocation(BBLSOhMy.MODID, "block_honey_slide")).setRegistryName("block_honey_slide");
        public static final SoundEvent BLOCK_HONEY_BREAK = new SoundEvent(new ResourceLocation(BBLSOhMy.MODID, "block_honey_break")).setRegistryName("block_honey_break");
        public static final SoundEvent BLOCK_HONEY_STEP = new SoundEvent(new ResourceLocation(BBLSOhMy.MODID, "block_honey_step")).setRegistryName("block_honey_step");
        public static final SoundEvent BLOCK_HONEY_PLACE = new SoundEvent(new ResourceLocation(BBLSOhMy.MODID, "block_honey_place")).setRegistryName("block_honey_place");
        public static final SoundEvent BLOCK_HONEY_HIT = new SoundEvent(new ResourceLocation(BBLSOhMy.MODID, "block_honey_hit")).setRegistryName("block_honey_hit");
        public static final SoundEvent BLOCK_HONEY_FALL = new SoundEvent(new ResourceLocation(BBLSOhMy.MODID, "block_honey_fall")).setRegistryName("block_honey_fall");
        
        public static final SoundType BLOCK_HONEY_TYPE = new SoundType(1.0F, 1.0F, BLOCK_HONEY_BREAK, BLOCK_HONEY_STEP, BLOCK_HONEY_PLACE, BLOCK_HONEY_HIT, BLOCK_HONEY_FALL);
        
        public static void init() {
                for(String entry : ForgeConfigHandler.server.customButtons) {
                        if(entry.isEmpty()) continue;
                        String[] args = entry.split(",");
                        try {
                                String name = args[0].trim();
                                boolean wooden = Boolean.parseBoolean(args[1].trim());
                                int tickrate = Integer.parseInt(args[2].trim());
                                BLOCKS_BUTTONS.add(new CustomBlockButton(name, wooden, tickrate));
                        }
                        catch(Exception ex) {
                                BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse button entry: " + entry);
                        }
                }
                for(String entry : ForgeConfigHandler.server.customLevers) {
                        String name = entry.trim();
                        if(name.isEmpty()) continue;
                        BLOCKS_LEVERS.add(new CustomBlockLever(name));
                }
                for(String entry : ForgeConfigHandler.server.customSigns) {
                        String name = entry.trim();
                        if(name.isEmpty()) continue;
                        CustomItemSign itemSign = new CustomItemSign(name);
                        Block blockWallSign = new CustomBlockWallSign(name, itemSign);
                        Block blockStandingSign = new CustomBlockStandingSign(name, itemSign);
                        itemSign.setWallSign(blockWallSign);
                        itemSign.setStandingSign(blockStandingSign);
                        ITEMS_SIGNS.add(itemSign);
                        BLOCKS_SIGNS.add(blockWallSign);
                        BLOCKS_SIGNS.add(blockStandingSign);
                }
                for(String entry : ForgeConfigHandler.server.customBoats) {
                        if(entry.isEmpty()) continue;
                        String[] args = entry.split(",");
                        try {
                                String name = args[0].trim();
                                Item plankItem = Item.getByNameOrId(args[1].trim());
                                int plankMeta = Integer.parseInt(args[2].trim());
                                Item stickItem = Item.getByNameOrId(args[3].trim());
                                int stickMeta = Integer.parseInt(args[4].trim());
                                ITEMS_BOATS.add(new CustomItemBoat(name, plankItem, plankMeta, stickItem, stickMeta));
                        }
                        catch(Exception ex) {
                                BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse boat entry: " + entry);
                        }
                }
                for(String entry : ForgeConfigHandler.server.customHorseArmors) {
                        if(entry.isEmpty()) continue;
                        String[] args = entry.split(",");
                        try {
                                String name = args[0].trim();
                                int armorStrength = Integer.parseInt(args[1].trim());
                                HorseArmorType horseArmorType = EnumHelper.addHorseArmor(name, BBLSOhMy.MODID + ":" + "textures/entity/horse/armor/" + name + ".png", armorStrength);
                                ITEMS_HORSEARMOR.add(new CustomItemHorseArmor(name, horseArmorType));
                        }
                        catch(Exception ex) {
                                BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse horse armor entry: " + entry);
                        }
                }
                for(String entry : ForgeConfigHandler.server.customHoneyBlocks) {
                        String name = entry.trim();
                        if(name.isEmpty()) continue;
                        BLOCKS_HONEY.add(new CustomBlockHoney(name));
                }
        }

        @SubscribeEvent
        public static void registerItemEvent(RegistryEvent.Register<Item> event) {
                for(Item item : ITEMS_BUTTONS) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_LEVERS) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_SIGNS) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_BOATS) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_HORSEARMOR) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_HONEY) {
                        event.getRegistry().register(item);
                }
        }
        
        @SubscribeEvent
        public static void registerBlockEvent(RegistryEvent.Register<Block> event) {
                for(Block block : BLOCKS_BUTTONS) {
                        event.getRegistry().register(block);
                }
                for(Block block : BLOCKS_LEVERS) {
                        event.getRegistry().register(block);
                }
                for(Block block : BLOCKS_SIGNS) {
                        event.getRegistry().register(block);
                }
                for(Block block : BLOCKS_HONEY) {
                        event.getRegistry().register(block);
                }
        }
        
        @SubscribeEvent
        public static void registerEntityEvent(RegistryEvent.Register<EntityEntry> event) {
                int id = 1;
                for(CustomItemBoat item : ITEMS_BOATS) {
                        registerEntity(EntityEntryBuilder.<CustomEntityBoat>create(), event, CustomEntityBoat.class, item.getCustomBoatType().getName(), id, 64);
                        id++;
                }
        }
        
        @SubscribeEvent
        public static void registerSoundEvent(RegistryEvent.Register<SoundEvent> event) {
                event.getRegistry().register(BLOCK_HONEY_SLIDE);
                event.getRegistry().register(BLOCK_HONEY_BREAK);
                event.getRegistry().register(BLOCK_HONEY_STEP);
                event.getRegistry().register(BLOCK_HONEY_PLACE);
                event.getRegistry().register(BLOCK_HONEY_HIT);
                event.getRegistry().register(BLOCK_HONEY_FALL);
        }
        
        private static void registerEntity(EntityEntryBuilder builder, RegistryEvent.Register<EntityEntry> event, Class<? extends Entity> entityClass, String name, int id, int range) {
                id += 1069;
                builder.entity(entityClass);
                builder.id(new ResourceLocation(BBLSOhMy.MODID, name), id);
                builder.name(name);
                builder.tracker(range, 1, true);
                event.getRegistry().register(builder.build());
        }
}