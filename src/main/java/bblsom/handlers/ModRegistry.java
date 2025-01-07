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
        public static final List<Item> ITEMS_DOORS = new ArrayList<>();
        public static final List<Item> ITEMS_TRAPDOORS = new ArrayList<>();
        public static final List<Item> ITEMS_PRESSUREPLATES = new ArrayList<>();
        public static final List<Item> ITEMS_PRESSUREPLATESWEIGHTED = new ArrayList<>();
        public static final List<Item> ITEMS_DIRT = new ArrayList<>();
        public static final List<Item> ITEMS_FALLINGDIRT = new ArrayList<>();
        public static final List<Item> ITEMS_GRASS = new ArrayList<>();
        public static final List<Item> ITEMS_FARMLAND = new ArrayList<>();
        public static final List<Item> ITEMS_LEAVES = new ArrayList<>();
        public static final List<Item> ITEMS_MUSHROOM = new ArrayList<>();
        public static final List<Item> ITEMS_HUGEMUSHROOM = new ArrayList<>();
        public static final List<CustomItemBoat> ITEMS_BOATS = new ArrayList<>();
        public static final List<Item> ITEMS_HORSEARMOR = new ArrayList<>();
        public static final List<Block> BLOCKS_BUTTONS = new ArrayList<>();
        public static final List<Block> BLOCKS_LEVERS = new ArrayList<>();
        public static final List<Block> BLOCKS_SIGNS = new ArrayList<>();
        public static final List<Block> BLOCKS_HONEY = new ArrayList<>();
        public static final List<Block> BLOCKS_DOORS = new ArrayList<>();
        public static final List<Block> BLOCKS_TRAPDOORS = new ArrayList<>();
        public static final List<Block> BLOCKS_PRESSUREPLATES = new ArrayList<>();
        public static final List<Block> BLOCKS_PRESSUREPLATESWEIGHTED = new ArrayList<>();
        public static final List<Block> BLOCKS_DIRT = new ArrayList<>();
        public static final List<Block> BLOCKS_FALLINGDIRT = new ArrayList<>();
        public static final List<Block> BLOCKS_GRASS = new ArrayList<>();
        public static final List<Block> BLOCKS_FARMLAND = new ArrayList<>();
        public static final List<Block> BLOCKS_LEAVES = new ArrayList<>();
        public static final List<Block> BLOCKS_MUSHROOM = new ArrayList<>();
        public static final List<Block> BLOCKS_HUGEMUSHROOM = new ArrayList<>();
        
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
                for(String entry : ForgeConfigHandler.server.customDoors) {
                        if(entry.isEmpty()) continue;
                        String[] args = entry.split(",");
                        try {
                                String name = args[0].trim();
                                boolean wooden = Boolean.parseBoolean(args[1].trim());
                                BLOCKS_DOORS.add(new CustomBlockDoor(name, wooden));
                        }
                        catch(Exception ex) {
                                BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse door entry: " + entry);
                        }
                }
                for(String entry : ForgeConfigHandler.server.customTrapDoors) {
                        if(entry.isEmpty()) continue;
                        String[] args = entry.split(",");
                        try {
                                String name = args[0].trim();
                                boolean wooden = Boolean.parseBoolean(args[1].trim());
                                BLOCKS_TRAPDOORS.add(new CustomBlockTrapDoor(name, wooden));
                        }
                        catch(Exception ex) {
                                BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse trapdoor entry: " + entry);
                        }
                }
                for(String entry : ForgeConfigHandler.server.customPressurePlates) {
                        if(entry.isEmpty()) continue;
                        String[] args = entry.split(",");
                        try {
                                String name = args[0].trim();
                                boolean wooden = Boolean.parseBoolean(args[1].trim());
                                boolean playerOnly = false;
                                if(args.length > 2) {
                                        playerOnly = Boolean.parseBoolean(args[2].trim());
                                }
                                BLOCKS_PRESSUREPLATES.add(new CustomBlockPressurePlate(name, wooden, playerOnly));
                        }
                        catch(Exception ex) {
                                BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse pressure plate entry: " + entry);
                        }
                }
                for(String entry : ForgeConfigHandler.server.customWeightedPressurePlates) {
                        if(entry.isEmpty()) continue;
                        String[] args = entry.split(",");
                        try {
                                String name = args[0].trim();
                                int weight = Integer.parseInt(args[1].trim());
                                boolean playerOnly = false;
                                if(args.length > 2) {
                                        playerOnly = Boolean.parseBoolean(args[2].trim());
                                }
                                BLOCKS_PRESSUREPLATESWEIGHTED.add(new CustomBlockPressurePlateWeighted(name, weight, playerOnly));
                        }
                        catch(Exception ex) {
                                BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse weighted pressure plate entry: " + entry);
                        }
                }
                for(String entry : ForgeConfigHandler.server.customDirt) {
                        if(entry.isEmpty()) continue;
                        String[] args = entry.split(",");
                        try {
                                String name = args[0].trim();
                                boolean supportsGeneralPlants = true;
                                if(args.length > 1) {
                                        supportsGeneralPlants = Boolean.parseBoolean(args[1].trim());
                                }
                                boolean supportsSandyPlants = false;
                                if(args.length > 2) {
                                        supportsSandyPlants = Boolean.parseBoolean(args[2].trim());
                                }
                                boolean supportsMushrooms = false;
                                if(args.length > 3) {
                                        supportsMushrooms = Boolean.parseBoolean(args[3].trim());
                                }
                                ResourceLocation farmlandBlockLocation = null;
                                if(args.length > 4) {
                                        farmlandBlockLocation = new ResourceLocation(args[4].trim());
                                }
                                BLOCKS_DIRT.add(new CustomBlockDirt(name, supportsGeneralPlants, supportsSandyPlants, supportsMushrooms, farmlandBlockLocation));
                        }
                        catch(Exception ex) {
                                BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse dirt entry: " + entry);
                        }
                }
                for(String entry : ForgeConfigHandler.server.customFallingDirt) {
                        if(entry.isEmpty()) continue;
                        String[] args = entry.split(",");
                        try {
                                String name = args[0].trim();
                                int dustColor = Integer.parseInt(args[1].trim());
                                boolean supportsGeneralPlants = true;
                                if(args.length > 2) {
                                        supportsGeneralPlants = Boolean.parseBoolean(args[2].trim());
                                }
                                boolean supportsSandyPlants = false;
                                if(args.length > 3) {
                                        supportsSandyPlants = Boolean.parseBoolean(args[3].trim());
                                }
                                boolean supportsMushrooms = false;
                                if(args.length > 4) {
                                        supportsMushrooms = Boolean.parseBoolean(args[4].trim());
                                }
                                BLOCKS_FALLINGDIRT.add(new CustomBlockFallingDirt(name, dustColor, supportsGeneralPlants, supportsSandyPlants, supportsMushrooms));
                        }
                        catch(Exception ex) {
                                BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse falling dirt entry: " + entry);
                        }
                }
                for(String entry : ForgeConfigHandler.server.customGrass) {
                        if(entry.isEmpty()) continue;
                        String[] args = entry.split(",");
                        try {
                                String name = args[0].trim();
                                ResourceLocation baseBlockLocation = new ResourceLocation(args[1].trim());
                                boolean supportsGeneralPlants = true;
                                if(args.length > 2) {
                                        supportsGeneralPlants = Boolean.parseBoolean(args[2].trim());
                                }
                                boolean supportsSandyPlants = false;
                                if(args.length > 3) {
                                        supportsSandyPlants = Boolean.parseBoolean(args[3].trim());
                                }
                                boolean supportsMushrooms = false;
                                if(args.length > 4) {
                                        supportsMushrooms = Boolean.parseBoolean(args[4].trim());
                                }
                                ResourceLocation farmlandBlockLocation = null;
                                if(args.length > 5) {
                                        farmlandBlockLocation = new ResourceLocation(args[5].trim());
                                }
                                BLOCKS_GRASS.add(new CustomBlockGrass(name, supportsGeneralPlants, supportsSandyPlants, supportsMushrooms, baseBlockLocation, farmlandBlockLocation));
                        }
                        catch(Exception ex) {
                                BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse grass entry: " + entry);
                        }
                }
                for(String entry : ForgeConfigHandler.server.customFarmland) {
                        if(entry.isEmpty()) continue;
                        String[] args = entry.split(",");
                        try {
                                String name = args[0].trim();
                                ResourceLocation baseBlockLocation = new ResourceLocation(args[1].trim());
                                float growthMult = 1.0F;
                                if(args.length > 2) {
                                        growthMult = Float.parseFloat(args[2].trim());
                                }
                                boolean alwaysFertile = false;
                                if(args.length > 3) {
                                        alwaysFertile = Boolean.parseBoolean(args[3].trim());
                                }
                                BLOCKS_FARMLAND.add(new CustomBlockFarmland(name, baseBlockLocation, growthMult, alwaysFertile));
                        }
                        catch(Exception ex) {
                                BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse farmland entry: " + entry);
                        }
                }
                for(String entry : ForgeConfigHandler.server.customLeaves) {
                        if(entry.isEmpty()) continue;
                        String[] args = entry.split(",");
                        try {
                                String name = args[0].trim();
                                Item saplingItem = Item.getByNameOrId(args[1].trim());
                                boolean decayable = true;
                                if(args.length > 2) {
                                        decayable = Boolean.parseBoolean(args[2].trim());
                                }
                                Item fruitItem = null;
                                if(args.length > 3) {
                                        fruitItem = Item.getByNameOrId(args[3].trim());
                                }
                                float fruitChanceGeneral = 0.05F;
                                if(args.length > 4) {
                                        fruitChanceGeneral = Float.parseFloat(args[4].trim());
                                }
                                float fruitChanceFruiting = 1.0F;
                                if(args.length > 5) {
                                        fruitChanceFruiting = Float.parseFloat(args[5].trim());
                                }
                                boolean canGrow = true;
                                if(args.length > 6) {
                                        canGrow = Boolean.parseBoolean(args[6].trim());
                                }
                                float growthChance = 0.05F;
                                if(args.length > 7) {
                                        growthChance = Float.parseFloat(args[7].trim());
                                }
                                BLOCKS_LEAVES.add(new CustomBlockLeaves(name, saplingItem, decayable, fruitItem, fruitChanceGeneral, fruitChanceFruiting, canGrow, growthChance));
                        }
                        catch(Exception ex) {
                                BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse leaves entry: " + entry);
                        }
                }
                for(String entry : ForgeConfigHandler.server.customMushroom) {
                        if(entry.isEmpty()) continue;
                        String[] args = entry.split(",");
                        try {
                                String name = args[0].trim();
                                boolean canGrowLarge = true;
                                if(args.length > 1) {
                                        canGrowLarge = Boolean.parseBoolean(args[1].trim());
                                }
                                boolean rounded = false;
                                if(args.length > 2) {
                                        rounded = Boolean.parseBoolean(args[2].trim());
                                }
                                BLOCKS_MUSHROOM.add(new CustomBlockMushroom(name, canGrowLarge, rounded));
                        }
                        catch(Exception ex) {
                                BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse mushroom entry: " + entry);
                        }
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
                for(Item item : ITEMS_DOORS) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_TRAPDOORS) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_PRESSUREPLATES) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_PRESSUREPLATESWEIGHTED) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_DIRT) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_FALLINGDIRT) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_GRASS) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_FARMLAND) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_LEAVES) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_MUSHROOM) {
                        event.getRegistry().register(item);
                }
                for(Item item : ITEMS_HUGEMUSHROOM) {
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
                for(Block block : BLOCKS_DOORS) {
                        event.getRegistry().register(block);
                }
                for(Block block : BLOCKS_TRAPDOORS) {
                        event.getRegistry().register(block);
                }
                for(Block block : BLOCKS_PRESSUREPLATES) {
                        event.getRegistry().register(block);
                }
                for(Block block : BLOCKS_PRESSUREPLATESWEIGHTED) {
                        event.getRegistry().register(block);
                }
                for(Block block : BLOCKS_DIRT) {
                        event.getRegistry().register(block);
                }
                for(Block block : BLOCKS_FALLINGDIRT) {
                        event.getRegistry().register(block);
                }
                for(Block block : BLOCKS_GRASS) {
                        event.getRegistry().register(block);
                }
                for(Block block : BLOCKS_FARMLAND) {
                        event.getRegistry().register(block);
                }
                for(Block block : BLOCKS_LEAVES) {
                        event.getRegistry().register(block);
                }
                for(Block block : BLOCKS_MUSHROOM) {
                        event.getRegistry().register(block);
                }
                for(Block block : BLOCKS_HUGEMUSHROOM) {
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