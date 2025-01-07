package bblsom.mixin.vanilla;

import bblsom.blocks.CustomBlockFarmland;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.BlockStem;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockStem.class)
public abstract class BlockStemMixin {
	
	@ModifyReturnValue(
			method = "canSustainBush",
			at = @At("RETURN")
	)
	private boolean bblsom_vanillaBlockStem_canSustainBush(boolean original, IBlockState state) {
		if(!original) {
			if(state.getBlock() instanceof CustomBlockFarmland) {
				return true;
			}
		}
		return original;
	}
}