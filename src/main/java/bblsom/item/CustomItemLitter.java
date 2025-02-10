package bblsom.item;

import bblsom.blocks.CustomBlockLitter;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CustomItemLitter extends ItemBlockSpecial {
	
	private final CustomBlockLitter litter;
	
	public CustomItemLitter(CustomBlockLitter litter) {
		super(litter);
		this.litter = litter;
		this.setCreativeTab(CreativeTabs.DECORATIONS);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = player.getHeldItem(hand);
		
		if(!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack)) {
			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();
			BlockPos blockpos = pos;
			
			if(block != this.litter && !block.isReplaceable(worldIn, pos)) {
				blockpos = pos.offset(facing);
				iblockstate = worldIn.getBlockState(blockpos);
				block = iblockstate.getBlock();
			}
			
			if(block == this.litter) {
				int i = iblockstate.getValue(CustomBlockLitter.SLICES);
				if(i < 3) {
					IBlockState iblockstate1 = iblockstate.withProperty(CustomBlockLitter.SLICES, i + 1);
					
					if(worldIn.setBlockState(blockpos, iblockstate1, 10)) {
						SoundType soundtype = this.litter.getSoundType(iblockstate1, worldIn, pos, player);
						worldIn.playSound(player, blockpos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
						
						itemstack.shrink(1);
						return EnumActionResult.SUCCESS;
					}
				}
				return EnumActionResult.FAIL;
			}
			return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		}
		return EnumActionResult.FAIL;
	}
}