package bblsom.mixin.vanilla;

import bblsom.blocks.CustomBlockStandingSign;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockDynamicLiquid.class)
public abstract class BlockDynamicLiquidMixin {
	
	@Redirect(
			method = "isBlocked",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;")
	)
	public Block bblsom_vanillaBlockDynamicLiquid_isBlocked_getBlock(IBlockState instance) {
		Block block = instance.getBlock();
		//Janky workaround but easier
		return block instanceof CustomBlockStandingSign ? Blocks.STANDING_SIGN : block;
	}
}