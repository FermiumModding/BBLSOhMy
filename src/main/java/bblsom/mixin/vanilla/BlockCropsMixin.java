package bblsom.mixin.vanilla;

import bblsom.blocks.CustomBlockFarmland;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockCrops.class)
public abstract class BlockCropsMixin {
	
	@Unique
	private static float bblsom$farmlandMult = 1.0F;
	
	@ModifyReturnValue(
			method = "canSustainBush",
			at = @At("RETURN")
	)
	private boolean bblsom_vanillaBlockCrops_canSustainBush(boolean original, IBlockState state) {
		if(!original) {
			if(state.getBlock() instanceof CustomBlockFarmland) {
				return true;
			}
		}
		return original;
	}
	
	@ModifyExpressionValue(
			method = "getGrowthChance",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", ordinal = 0)
	)
	private static IBlockState bblsom_vanillaBlockCrops_getGrowthChance_getBlockState(IBlockState original) {
		if(original.getBlock() instanceof CustomBlockFarmland) bblsom$farmlandMult = ((CustomBlockFarmland)original).getFarmlandMult();
		else bblsom$farmlandMult = 1.0F;
		return original;
	}
	
	@ModifyConstant(
			method = "getGrowthChance",
			constant = @Constant(floatValue = 1.0F, ordinal = 1)
	)
	private static float bblsom_vanillaBlockCrops_getGrowthChance_float0(float constant) {
		return bblsom$farmlandMult * constant;
	}
	
	@ModifyConstant(
			method = "getGrowthChance",
			constant = @Constant(floatValue = 3.0F)
	)
	private static float bblsom_vanillaBlockCrops_getGrowthChance_float1(float constant) {
		return bblsom$farmlandMult * constant;
	}
}