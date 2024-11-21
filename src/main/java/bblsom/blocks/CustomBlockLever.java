package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ModRegistry;
import net.minecraft.block.BlockLever;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;

public class CustomBlockLever extends BlockLever {
	
	public CustomBlockLever(String name) {
		super();
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.STONE);
		ModRegistry.ITEMS_LEVERS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey()));
	}
}