package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class CustomBlockHugeMushroom extends BlockHugeMushroom {
	
	public CustomBlockHugeMushroom(String name, Block smallMushroom) {
		super(Material.WOOD, MapColor.DIRT, smallMushroom);
		this.setRegistryName(BBLSOhMy.MODID + ":" + name + "_huge");
		this.setTranslationKey(BBLSOhMy.MODID + "." + name + "_huge");
		this.setHardness(0.2F);
		this.setSoundType(SoundType.WOOD);
		ModRegistry.ITEMS_HUGEMUSHROOM.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey()));
	}
}