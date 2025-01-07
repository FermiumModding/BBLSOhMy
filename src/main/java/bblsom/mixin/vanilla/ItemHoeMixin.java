package bblsom.mixin.vanilla;

import bblsom.blocks.ICustomBlockTillable;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemHoe.class)
public abstract class ItemHoeMixin {
	
	@Shadow protected abstract void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState state);
	
	@ModifyReturnValue(
			method = "onItemUse",
			at = @At("RETURN")
	)
	private EnumActionResult bblsom_vanillaItemHoe_onItemUse(EnumActionResult original, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(original == EnumActionResult.PASS) {
			ItemStack itemstack = player.getHeldItem(hand);
			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();
			
			if(block instanceof ICustomBlockTillable) {
				Block farmlandBlock = ((ICustomBlockTillable)block).getFarmlandBlock();
				if(farmlandBlock != null && facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up())) {
					this.setBlock(itemstack, player, worldIn, pos, farmlandBlock.getDefaultState());
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return original;
	}
}