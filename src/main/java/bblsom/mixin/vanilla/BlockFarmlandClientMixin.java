package bblsom.mixin.vanilla;

import bblsom.blocks.CustomBlockFarmland;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockFarmland.class)
public abstract class BlockFarmlandClientMixin {
	
	@ModifyExpressionValue(
			method = "shouldSideBeRendered",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;")
	)
	private Block bblsom_vanillaBlockFarmland_shouldSideBeRendered(Block original) {
		if(original instanceof CustomBlockFarmland) return Blocks.FARMLAND;
		return original;
	}
}