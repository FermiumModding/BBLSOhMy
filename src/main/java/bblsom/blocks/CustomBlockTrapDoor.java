package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ModRegistry;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class CustomBlockTrapDoor extends BlockTrapDoor {
	
	public CustomBlockTrapDoor(String name, boolean wooden) {
		super(wooden ? Material.WOOD : Material.IRON);
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.setHardness(wooden ? 3.0F : 5.0F);
		this.setSoundType(wooden ? SoundType.WOOD : SoundType.METAL);
		ModRegistry.ITEMS_TRAPDOORS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey()));
	}
}