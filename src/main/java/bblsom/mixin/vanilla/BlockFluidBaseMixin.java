package bblsom.mixin.vanilla;

import bblsom.blocks.ICustomBlockSign;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BlockFluidBase.class)
public abstract class BlockFluidBaseMixin {
	
	@Inject(
			method = "canDisplace",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;isAir(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Z"),
			locals = LocalCapture.CAPTURE_FAILHARD,
			cancellable = true,
			remap = false
	)
	public void bblsom_vanillaBlockFluidBase_canDisplace_isAir(IBlockAccess world, BlockPos pos, CallbackInfoReturnable<Boolean> cir, IBlockState state, Block block) {
		if(block instanceof ICustomBlockSign) cir.setReturnValue(false);
	}
}