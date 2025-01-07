package bblsom.mixin.vanilla;

import bblsom.blocks.CustomBlockFarmland;
import bblsom.blocks.ICustomBlockFertile;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBush.class)
public abstract class BlockBushMixin {
	
	@ModifyReturnValue(
			method = "canSustainBush",
			at = @At("RETURN")
	)
	private boolean bblsom_vanillaBlockBush_canSustainBush(boolean original, IBlockState state) {
		if(!original) {
			if(state.getBlock() instanceof ICustomBlockFertile) {
				return ((ICustomBlockFertile)state.getBlock()).getSupportsGeneralPlants();
			}
			if(state.getBlock() instanceof CustomBlockFarmland) return true;
		}
		return original;
	}
}