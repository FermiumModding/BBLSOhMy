package bblsom.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import bblsom.BBLSOhMy;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Config(modid = BBLSOhMy.MODID)
public class ForgeConfigHandler {
	
	@Config.Comment("Server-Side Options")
	@Config.Name("Server Options")
	public static final ServerConfig server = new ServerConfig();

	public static class ServerConfig {

		@Config.Comment(
				"Custom Buttons to be registered" + "\n" +
				"Format: String name, Boolean wooden, int tickrate" + "\n" +
				"String name: the name of the item/block to be registered" + "\n" +
				"Boolean wooden: whether the button should be treated as wooden or not (Affects sound and tickrate)" + "\n" +
				"int tickrate: tickrate override for the button, if left as 0 uses the vanilla handling based on wooden or not")
		@Config.Name("Custom Button Entries")
		@Config.RequiresMcRestart
		public String[] customButtons = {""};
		
		@Config.Comment(
				"Custom Levers to be registered" + "\n" +
						"Format: String name" + "\n" +
						"String name: the name of the item/block to be registered")
		@Config.Name("Custom Lever Entries")
		@Config.RequiresMcRestart
		public String[] customLevers = {""};
		
		@Config.Comment(
				"Custom Signs to be registered" + "\n" +
						"Format: String name" + "\n" +
						"String name: the name of the item/block to be registered")
		@Config.Name("Custom Sign Entries")
		@Config.RequiresMcRestart
		public String[] customSigns = {""};
		
		@Config.Comment(
				"Custom Boats to be registered" + "\n" +
						"Format: String name, ItemID plankItem, int plankMeta, ItemID stickItem, int stickMeta" + "\n" +
						"String name: the name of the item/boat to be registered" + "\n" +
						"ItemID plankItem: The item id of the plank-like item to be dropped when the boat is broken dangerously" + "\n" +
						"int plankMeta: The metadata of the plank-like item to be dropped when the boat is broken dangerously" + "\n" +
						"ItemID stickItem: The item id of the stick-like item to be dropped when the boat is broken dangerously" + "\n" +
						"int stickMeta: The metadata of the stick-like item to be dropped when the boat is broken dangerously")
		@Config.Name("Custom Boat Entries")
		@Config.RequiresMcRestart
		public String[] customBoats = {""};
		
		@Config.Comment(
				"Custom Horse Armors to be registered" + "\n" +
						"Format: String name, int armorStrength" + "\n" +
						"String name: the name of the armor to be registered" + "\n" +
						"int armorStrength: the strength of the armor to be registered")
		@Config.Name("Custom Horse Armor Entries")
		@Config.RequiresMcRestart
		public String[] customHorseArmors = {""};
		
		@Config.Comment(
				"Custom Honey Blocks to be registered" + "\n" +
						"Format: String name" + "\n" +
						"String name: the name of the item/block to be registered")
		@Config.Name("Custom Honey Block Entries")
		@Config.RequiresMcRestart
		public String[] customHoneyBlocks = {"block_honey"};
		
		@Config.Comment(
				"Custom Doors to be registered" + "\n" +
						"Format: String name, Boolean wooden" + "\n" +
						"String name: the name of the item/block to be registered" + "\n" +
						"Boolean wooden: whether the door should be treated as wooden or not (Affects sound and redstone interaction)")
		@Config.Name("Custom Door Entries")
		@Config.RequiresMcRestart
		public String[] customDoors = {""};
		
		@Config.Comment(
				"Custom Trap Doors to be registered" + "\n" +
						"Format: String name, Boolean wooden" + "\n" +
						"String name: the name of the item/block to be registered" + "\n" +
						"Boolean wooden: whether the trap door should be treated as wooden or not (Affects sound and redstone interaction)")
		@Config.Name("Custom Trap Door Entries")
		@Config.RequiresMcRestart
		public String[] customTrapDoors = {""};
		
		@Config.Comment(
				"Custom Pressure Plates to be registered" + "\n" +
						"Format: String name, Boolean wooden" + "\n" +
						"String name: the name of the item/block to be registered" + "\n" +
						"Boolean wooden: whether the pressure plate should be treated as wooden or not (Affects sound and redstone interaction)" + "\n" +
						"(Optional, default false) Boolean playerOnly: whether only players should be able to trigger the pressure plate")
		@Config.Name("Custom Pressure Plate Entries")
		@Config.RequiresMcRestart
		public String[] customPressurePlates = {""};
		
		@Config.Comment(
				"Custom Weighted Pressure Plates to be registered" + "\n" +
						"Format: String name, int maxWeight" + "\n" +
						"String name: the name of the item/block to be registered" + "\n" +
						"int maxWeight: max weight to trigger the pressure plate" + "\n" +
						"(Optional, default false) Boolean playerOnly: whether only players should be able to trigger the pressure plate")
		@Config.Name("Custom Weighted Pressure Plate Entries")
		@Config.RequiresMcRestart
		public String[] customWeightedPressurePlates = {""};
		
		@Config.Comment(
				"Custom Dirt to be registered" + "\n" +
						"Format: String name, Boolean supportsGeneralPlants, Boolean supportsSandyPlants, Boolean supportsMushrooms, BlockID farmlandBlock" + "\n" +
						"String name: the name of the item/block to be registered" + "\n" +
						"(Optional, default true) Boolean supportsGeneralPlants: if the dirt should support general plants growing on it" + "\n" +
						"(Optional, default false) Boolean supportsSandyPlants: if the dirt should support sandy plants growing on it" + "\n" +
						"(Optional, default false) Boolean supportsMushrooms: if the dirt should support mushrooms growing on it" + "\n" +
						"(Optional, default disabled) BlockID farmlandBlock: the farmland block that the dirt should turn into upon hoe-ing")
		@Config.Name("Custom Dirt Entries")
		@Config.RequiresMcRestart
		public String[] customDirt = {""};
		
		@Config.Comment(
				"Custom Falling Dirt to be registered" + "\n" +
						"Format: String name, int dustColor, Boolean supportsGeneralPlants, Boolean supportsSandyPlants, Boolean supportsMushrooms" + "\n" +
						"String name: the name of the item/block to be registered" + "\n" +
						"int dustColor: decimal color value to be used for block falling particles" + "\n" +
						"(Optional, default true) Boolean supportsGeneralPlants: if the dirt should support general plants growing on it" + "\n" +
						"(Optional, default false) Boolean supportsSandyPlants: if the dirt should support sandy plants growing on it" + "\n" +
						"(Optional, default false) Boolean supportsMushrooms: if the dirt should support mushrooms growing on it")
		@Config.Name("Custom Falling Dirt Entries")
		@Config.RequiresMcRestart
		public String[] customFallingDirt = {""};
		
		@Config.Comment(
				"Custom Grass to be registered" + "\n" +
						"Format: String name, BlockID baseBlock, Boolean supportsGeneralPlants, Boolean supportsSandyPlants, Boolean supportsMushrooms, BlockID farmlandBlock" + "\n" +
						"String name: the name of the item/block to be registered" + "\n" +
						"BlockID baseBlock: the block that the grass converts to more grass, and reverts to" + "\n" +
						"(Optional, default true) Boolean supportsGeneralPlants: if the dirt should support general plants growing on it" + "\n" +
						"(Optional, default false) Boolean supportsSandyPlants: if the dirt should support sandy plants growing on it" + "\n" +
						"(Optional, default false) Boolean supportsMushrooms: if the dirt should support mushrooms growing on it" + "\n" +
						"(Optional, default disabled) BlockID farmlandBlock: the farmland block that the grass should turn into upon hoe-ing")
		@Config.Name("Custom Grass Entries")
		@Config.RequiresMcRestart
		public String[] customGrass = {""};
		
		@Config.Comment(
				"Custom Farmland to be registered" + "\n" +
						"Format: String name, BlockID baseBlock, Float growthMult, Boolean alwaysFertile" + "\n" +
						"String name: the name of the item/block to be registered" + "\n" +
						"BlockID baseBlock: the block that the farmland reverts to" + "\n" +
						"(Optional, default 1.0F) Float growthMult: the multiplier for speed of crops growing on the farmland" + "\n" +
						"(Optional, default false) Boolean alwaysFertile: if the farmland should always be moisturized and fertile")
		@Config.Name("Custom Farmland Entries")
		@Config.RequiresMcRestart
		public String[] customFarmland = {""};
		
		@Config.Comment(
				"Custom Leaves to be registered" + "\n" +
						"Format: String name, ItemID saplingItem, Boolean decayable, ItemID fruitItem, float fruitChanceGeneral, float fruitChanceFruiting, boolean canGrow, float growthChance" + "\n" +
						"String name: the name of the item/block to be registered" + "\n" +
						"ItemID saplingItem: the sapling rarity item randomly dropped when leaves are broken" + "\n" +
						"(Optional, default true) Boolean decayable: if the leaves should be allows to decay like vanilla" + "\n" +
						"(Optional, default ignored) ItemID fruitItem: the fruit item randomly dropped when leaves are broken" + "\n" +
						"(Optional, default 0.05F) Float fruitChanceGeneral: the chance when any type of leaves are broken to drop fruit" + "\n" +
						"(Optional, default 1.0F) Float fruitChanceFruiting: the chance when fruiting leaves are broken to drop fruit" + "\n" +
						"(Optional, default true) Boolean canGrow: if the leaves should be able to grow from default to flowering to fruiting when random ticked" + "\n" +
						"(Optional, default 0.05F) Float growthChance: the chance when random ticked to grow the leaves")
		@Config.Name("Custom Leaves Entries")
		@Config.RequiresMcRestart
		public String[] customLeaves = {""};
		
		@Config.Comment(
				"Custom Mushroom to be registered" + "\n" +
						"Format: String name, Boolean canGrowLarge, boolean rounded" + "\n" +
						"String name: the name of the item/block to be registered" + "\n" +
						"(Optional, default true) Boolean canGrowLarge: if the mushroom should be able to be turned into a large mushroom such as with bonemeal" + "\n" +
						"(Optional, default false) Boolean rounded: if the large mushroom should be rounded like red mushrooms, or flat like brown mushrooms")
		@Config.Name("Custom Mushroom Entries")
		@Config.RequiresMcRestart
		public String[] customMushroom = {""};
		
		@Config.Comment(
				"Allows for additional customization of what blocks grass-like blocks can target and what blocks they convert into" + "\n" +
						"Format: BlockID sourceBlock, BlockID convertBlock ; BlockID resultBlock, BlockID convertBlock ; BlockID resultBlock, ... (Multiple pairs allowed)" + "\n" +
						"(Optional: add :metadata after a BlockID to specify metadata)" + "\n" +
						"BlockID sourceBlock: the grass-like block that starts the conversion of other blocks" + "\n" +
						"BlockID convertBlock: a block that the sourceBlock can target for conversion, non-repeatable per sourceBlock" + "\n" +
						"BlockID resultBlock: the block that the convertBlock turns into after being converted by the sourceBlock, repeatable per sourceBlock")
		@Config.Name("Additional Grass Spreading Settings")
		@Config.RequiresMcRestart
		public String[] additionalGrassSpreading = {""};
	}
	
	private static Map<BlockEntry,Map<BlockEntry, BlockEntry>> grassConversions = null;
	
	//Gross
	@Nullable
	public static Map<BlockEntry, BlockEntry> getGrassConversions(IBlockState stateIn) {
		if(grassConversions == null) {
			grassConversions = new HashMap<>();
			for(String entry : ForgeConfigHandler.server.additionalGrassSpreading) {
				if(entry.trim().isEmpty()) continue;
				String[] arr = entry.split(",");
				try {
					BlockEntry sourceBlockEntry = BlockEntry.getEntryFromIdentifier(arr[0].trim());
					if(sourceBlockEntry == null) throw new Exception();
					Map<BlockEntry, BlockEntry> conversionMap = new HashMap<>();
					for(int i = 1; i < arr.length; i++) {
						String pairEntry = arr[i].trim();
						if(pairEntry.isEmpty()) continue;
						String[] pair = pairEntry.split(";");
						try {
							BlockEntry convertBlockEntry = BlockEntry.getEntryFromIdentifier(pair[0].trim());
							BlockEntry resultBlockEntry = BlockEntry.getEntryFromIdentifier(pair[1].trim());
							if(convertBlockEntry == null || resultBlockEntry == null) throw new Exception();
							conversionMap.put(convertBlockEntry, resultBlockEntry);
						}
						catch(Exception ex) {
							BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse grass conversion entry pair: " + pairEntry);
						}
					}
					grassConversions.put(sourceBlockEntry, conversionMap);
				}
				catch(Exception ex) {
					BBLSOhMy.LOGGER.log(Level.ERROR, "Failed to parse grass conversion entry: " + entry);
				}
			}
		}
		for(Map.Entry<BlockEntry, Map<BlockEntry, BlockEntry>> entry : grassConversions.entrySet()) {
			if(entry.getKey().entryMatches(stateIn)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	public static class BlockEntry {
		
		private final Block block;
		private final int metadata;
		
		private BlockEntry(Block block, int metadata) {
			this.block = block;
			this.metadata = metadata;
		}
		
		public IBlockState getState() {
			if(this.metadata < 0) return this.block.getDefaultState();
			return this.block.getStateFromMeta(this.metadata);
		}
		
		public boolean entryMatches(IBlockState state) {
			if(state == null) return false;
			if(state.getBlock() == this.block) {
				return this.metadata == -1 || this.metadata == state.getBlock().getMetaFromState(state);
			}
			return false;
		}
		
		//Really gross optional parsing
		@Nullable
		public static BlockEntry getEntryFromIdentifier(String entry) {
			String[] arr = entry.split(":");
			if(arr.length == 0) return null;
			int meta = -1;
			boolean hasMeta = false;
			
			try {
				meta = Integer.parseInt(arr[arr.length-1].trim());
				hasMeta = true;
			}
			catch(Exception ignored) {}
			
			ResourceLocation loc;
			if(arr.length == 1) {
				if(hasMeta) return null;
				loc = new ResourceLocation(arr[0].trim());
			}
			else if(arr.length == 2) {
				loc = hasMeta ? new ResourceLocation(arr[0].trim()) : new ResourceLocation(arr[0].trim(), arr[1].trim());
			}
			else if(arr.length == 3) {
				if(!hasMeta) return null;
				loc = new ResourceLocation(arr[0].trim(), arr[1].trim());
			}
			else return null;
			
			Block entryBlock = ForgeRegistries.BLOCKS.getValue(loc);
			if(entryBlock == null) return null;
			
			return new BlockEntry(entryBlock, meta);
		}
	}

	@Mod.EventBusSubscriber(modid = BBLSOhMy.MODID)
	private static class EventHandler{

		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(BBLSOhMy.MODID)) {
				ConfigManager.sync(BBLSOhMy.MODID, Config.Type.INSTANCE);
			}
		}
	}
}