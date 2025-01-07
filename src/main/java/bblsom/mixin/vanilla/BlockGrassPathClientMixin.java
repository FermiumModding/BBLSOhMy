package bblsom.mixin.vanilla;

import bblsom.blocks.CustomBlockFarmland;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockGrassPath.class)
public abstract class BlockGrassPathClientMixin {
	
	@ModifyExpressionValue(
			method = "shouldSideBeRendered",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;")
	)
	private Block bblsom_vanillaBlockGrassPath_shouldSideBeRendered(Block original) {
		if(original instanceof CustomBlockFarmland) return Blocks.FARMLAND;
		return original;
	}
}