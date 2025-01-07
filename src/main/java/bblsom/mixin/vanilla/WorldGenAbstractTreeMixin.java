package bblsom.mixin.vanilla;

import bblsom.blocks.ICustomBlockFertile;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldGenAbstractTree.class)
public abstract class WorldGenAbstractTreeMixin {
	
	@ModifyReturnValue(
			method = "canGrowInto",
			at = @At("RETURN")
	)
	private boolean bblsom_vanillaWorldGenAbstractTreeMixin_canGrowInto(boolean original, Block blockType) {
		if(blockType instanceof ICustomBlockFertile && ((ICustomBlockFertile)blockType).getSupportsGeneralPlants()) return true;
		return original;
	}
}