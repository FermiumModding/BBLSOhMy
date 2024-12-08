package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ModRegistry;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class CustomBlockPressurePlate extends BlockPressurePlate {
	
	public CustomBlockPressurePlate(String name, boolean wooden) {
		super(wooden ? Material.WOOD : Material.ROCK, wooden ? Sensitivity.EVERYTHING : Sensitivity.MOBS);
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.setHardness(0.5F);
		this.setSoundType(wooden ? SoundType.WOOD : SoundType.STONE);
		ModRegistry.ITEMS_PRESSUREPLATES.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey()));
	}
}