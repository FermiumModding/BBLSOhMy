package bblsom.mixin.vanilla;

import bblsom.compat.AquaAcrobaticsCompat;
import bblsom.compat.CompatUtil;
import bblsom.handlers.ForgeConfigHandler;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Random;

@Mixin(value = BlockMycelium.class, priority = 990)
public abstract class BlockMyceliumMixin {
	
	/**
	 * @author fonnymunkey
	 * @reason add grass spreading customization, overwrite needed to fix crash/compatibility with FluidLogged
	 */
	@Overwrite
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(!worldIn.isRemote) {
			if(!worldIn.isAreaLoaded(pos, 3)) return;
			
			if(CompatUtil.isAquaAcrobaticsLoaded()) {
				CallbackInfo ci = new CallbackInfo("bblsom$aquaAcrobaticsCompat", true);
				AquaAcrobaticsCompat.handleUnderwaterGrassLikeBlockWrapped(worldIn, pos, state, rand, ci);
				if(ci.isCancelled()) return;
			}
			
			int lightUp = worldIn.getLightFromNeighbors(pos.up());
			if(lightUp < 4 && worldIn.getBlockLightOpacity(pos.up()) > 2) {
				worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
			}
			else if(lightUp >= 9) {
				for(int i = 0; i < 4; ++i) {
					BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
					
					if(blockpos.getY() < 0 || blockpos.getY() >= 256 || !worldIn.isBlockLoaded(blockpos)) return;
					
					IBlockState iblockstate = worldIn.getBlockState(blockpos);
					if(state.getBlock() == Blocks.AIR) continue;
					
					if(CompatUtil.isAquaAcrobaticsLoaded()) {
						if(worldIn.getBlockState(blockpos.up()).getMaterial().isLiquid()) continue;
					}
					
					Map<ForgeConfigHandler.BlockEntry,ForgeConfigHandler.BlockEntry> conversionMap = ForgeConfigHandler.getGrassConversions(state);
					if(conversionMap != null) {
						boolean matched = false;
						for(Map.Entry<ForgeConfigHandler.BlockEntry,ForgeConfigHandler.BlockEntry> entry : conversionMap.entrySet()) {
							if(entry.getKey().entryMatches(iblockstate)) {
								if(worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && worldIn.getBlockLightOpacity(blockpos.up()) <= 2) {
									worldIn.setBlockState(blockpos, entry.getValue().getState());
								}
								matched = true;
								break;
							}
						}
						if(matched) continue;
					}
					
					if(iblockstate.getBlock() == Blocks.DIRT && iblockstate.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && worldIn.getBlockLightOpacity(blockpos.up()) <= 2) {
						worldIn.setBlockState(blockpos, Blocks.MYCELIUM.getDefaultState());
					}
				}
			}
		}
	}
}