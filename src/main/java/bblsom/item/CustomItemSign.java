package bblsom.item;

import bblsom.BBLSOhMy;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSign;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CustomItemSign extends ItemSign {
	
	private Block wallSign = Blocks.WALL_SIGN;
	private Block standingSign = Blocks.STANDING_SIGN;
	
	public CustomItemSign(String name) {
		super();
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
	}
	
	public void setWallSign(Block wallSign) {
		this.wallSign = wallSign;
	}
	
	public void setStandingSign(Block standingSign) {
		this.standingSign = standingSign;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		boolean flag = iblockstate.getBlock().isReplaceable(worldIn, pos);
		
		if(facing != EnumFacing.DOWN && (iblockstate.getMaterial().isSolid() || flag) && (!flag || facing == EnumFacing.UP)) {
			pos = pos.offset(facing);
			ItemStack itemstack = player.getHeldItem(hand);
			
			if(player.canPlayerEdit(pos, facing, itemstack) && this.standingSign.canPlaceBlockAt(worldIn, pos)) {
				if(worldIn.isRemote) {
					return EnumActionResult.SUCCESS;
				}
				else {
					pos = flag ? pos.down() : pos;
					
					if(facing == EnumFacing.UP) {
						int i = MathHelper.floor((double)((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
						worldIn.setBlockState(pos, this.standingSign.getDefaultState().withProperty(BlockStandingSign.ROTATION, Integer.valueOf(i)), 11);
					}
					else {
						worldIn.setBlockState(pos, this.wallSign.getDefaultState().withProperty(BlockWallSign.FACING, facing), 11);
					}
					
					TileEntity tileentity = worldIn.getTileEntity(pos);
					
					if(tileentity instanceof TileEntitySign && !ItemBlock.setTileEntityNBT(worldIn, player, pos, itemstack)) {
						player.openEditSign((TileEntitySign)tileentity);
					}
					
					if(player instanceof EntityPlayerMP) {
						CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
					}
					
					itemstack.shrink(1);
					return EnumActionResult.SUCCESS;
				}
			}
			else {
				return EnumActionResult.FAIL;
			}
		}
		else {
			return EnumActionResult.FAIL;
		}
	}
}