package bblsom.mixin.vanilla;

import bblsom.blocks.ICustomBlockFertile;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockDeadBush.class)
public abstract class BlockDeadBushMixin {
	
	@ModifyReturnValue(
			method = "canSustainBush",
			at = @At("RETURN")
	)
	private boolean bblsom_vanillaBlockDeadBush_canSustainBush(boolean original, IBlockState state) {
		if(!original) {
			if(state.getBlock() instanceof ICustomBlockFertile) {
				return ((ICustomBlockFertile)state.getBlock()).getSupportsGeneralPlants() || ((ICustomBlockFertile)state.getBlock()).getSupportsSandyPlants();
			}
		}
		return original;
	}
}