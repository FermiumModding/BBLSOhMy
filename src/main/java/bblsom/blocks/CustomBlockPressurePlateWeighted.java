package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ModRegistry;
import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class CustomBlockPressurePlateWeighted extends BlockPressurePlateWeighted {
	
	public CustomBlockPressurePlateWeighted(String name, int maxWeight) {
		super(Material.IRON, maxWeight);
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.WOOD);
		ModRegistry.ITEMS_PRESSUREPLATESWEIGHTED.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey()));
	}
}