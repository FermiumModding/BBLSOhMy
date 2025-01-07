package bblsom.item;

import bblsom.blocks.CustomBlockLeaves;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class CustomItemLeaves extends ItemBlock {
	private final CustomBlockLeaves leaves;
	
	public CustomItemLeaves(CustomBlockLeaves leaves) {
		super(leaves);
		this.leaves = leaves;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	public int getMetadata(int damage) {
		return damage|4;
	}
	
	public String getTranslationKey(ItemStack stack) {
		return super.getTranslationKey() + "." + this.leaves.getAgePhase(stack.getMetadata()).getName();
	}
}
