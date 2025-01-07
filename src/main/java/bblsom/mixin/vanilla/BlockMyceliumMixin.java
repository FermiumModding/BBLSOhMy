package bblsom.mixin.vanilla;

import bblsom.handlers.ForgeConfigHandler;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;
import java.util.Random;

@Mixin(BlockMycelium.class)
public abstract class BlockMyceliumMixin {
	
	@Unique
	private boolean bblsom$replacedGrass = false;
	
	@Inject(
			method = "updateTick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;", shift = At.Shift.BEFORE),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void bblsom_vanillaBlockMycelium_updateTick_inject(World worldIn, BlockPos pos, IBlockState state, Random rand, CallbackInfo ci, int i, BlockPos blockpos, IBlockState iblockstate, IBlockState iblockstate1) {
		this.bblsom$replacedGrass = false;
		Map<ForgeConfigHandler.BlockEntry,ForgeConfigHandler.BlockEntry> conversionMap = ForgeConfigHandler.getGrassConversions(state);
		if(conversionMap != null) {
			for(Map.Entry<ForgeConfigHandler.BlockEntry,ForgeConfigHandler.BlockEntry> entry : conversionMap.entrySet()) {
				if(entry.getKey().entryMatches(iblockstate)) {
					if(worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate1.getLightOpacity(worldIn, blockpos.up()) <= 2) {
						worldIn.setBlockState(blockpos, entry.getValue().getState());
					}
					this.bblsom$replacedGrass = true;
					return;
				}
			}
		}
	}
	
	@ModifyExpressionValue(
			method = "updateTick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;")
	)
	private Block bblsom_vanillaBlockMycelium_updateTick_modify(Block original) {
		if(!this.bblsom$replacedGrass) return original;
		this.bblsom$replacedGrass = false;
		return Blocks.AIR;
	}
}