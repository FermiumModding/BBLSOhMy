package bblsom.mixin.vanilla;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockBush.class)
public interface IBlockBushMixin {
	
	@Invoker(value = "canSustainBush")
	boolean getCanSustainBush(IBlockState state);
}