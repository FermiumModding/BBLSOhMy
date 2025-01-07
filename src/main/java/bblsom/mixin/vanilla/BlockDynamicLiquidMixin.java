package bblsom.mixin.vanilla;

import bblsom.blocks.CustomBlockStandingSign;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockDynamicLiquid.class)
public abstract class BlockDynamicLiquidMixin {
	
	@ModifyExpressionValue(
			method = "isBlocked",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;")
	)
	private Block bblsom_vanillaBlockDynamicLiquid_isBlocked(Block original) {
		if(original instanceof CustomBlockStandingSign) return Blocks.STANDING_SIGN;
		return original;
	}
}