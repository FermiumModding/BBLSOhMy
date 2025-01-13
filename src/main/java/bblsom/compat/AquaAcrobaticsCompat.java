package bblsom.compat;

import com.fuzs.aquaacrobatics.core.UnderwaterGrassLikeHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

public abstract class AquaAcrobaticsCompat {
	
	public static void handleUnderwaterGrassLikeBlockWrapped(World world, BlockPos pos, IBlockState state, Random rand, CallbackInfo ci) {
		UnderwaterGrassLikeHandler.handleUnderwaterGrassLikeBlock(world, pos, state, rand, ci);
	}
}