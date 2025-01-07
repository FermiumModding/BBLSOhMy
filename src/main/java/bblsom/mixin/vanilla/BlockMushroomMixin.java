package bblsom.mixin.vanilla;

import bblsom.blocks.ICustomBlockFertile;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockMushroom.class)
public abstract class BlockMushroomMixin {
	
	@ModifyExpressionValue(
			method = "canBlockStay",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;", ordinal = 0)
	)
	private Block bblsom_vanillaBlockMushroom_canBlockStay(Block original) {
		if(original instanceof ICustomBlockFertile && ((ICustomBlockFertile)original).getSupportsMushrooms()) return Blocks.MYCELIUM;
		return original;
	}
}