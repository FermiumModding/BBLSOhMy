package bblsom.blocks;

import net.minecraft.block.Block;

import javax.annotation.Nonnull;

public interface ICustomBlockLayered {
	
	@Nonnull
	Block getBaseBlock();
}