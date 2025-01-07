package bblsom.mixin.vanilla;

import bblsom.blocks.ICustomBlockFertile;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldGenBigMushroom.class)
public abstract class WorldGenBigMushroomMixin {
	
	@ModifyExpressionValue(
			method = "generate",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;", ordinal = 2)
	)
	private Block bblsom_vanillaWorldGenBigMushroom_generate(Block original) {
		if(original instanceof ICustomBlockFertile &&
				(((ICustomBlockFertile)original).getSupportsGeneralPlants() ||
				((ICustomBlockFertile)original).getSupportsMushrooms())) return Blocks.MYCELIUM;
		return original;
	}
}