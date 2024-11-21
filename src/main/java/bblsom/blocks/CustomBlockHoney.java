package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ModRegistry;
import bblsom.mixin.vanilla.IEntityLivingBaseMixin;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomBlockHoney extends BlockBreakable {
	
	private static final AxisAlignedBB HONEY_COLLISION_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
	protected static final AxisAlignedBB HONEY_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);
	
	public CustomBlockHoney() {
		super(Material.CLAY, false, MapColor.GOLD);
		this.setRegistryName(BBLSOhMy.MODID + ":block_honey");
		this.setTranslationKey(BBLSOhMy.MODID + ".block_honey");
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setSoundType(ModRegistry.BLOCK_HONEY_TYPE);
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return HONEY_COLLISION_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return HONEY_AABB.offset(pos);
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		entityIn.world.playSound(null, pos, ModRegistry.BLOCK_HONEY_SLIDE, SoundCategory.BLOCKS, 1.0F, 1.0F);
		if(entityIn.isSneaking()) {
			super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
		}
		else {
			entityIn.fall(fallDistance, 0.2F);
			entityIn.world.playSound(null, pos, this.blockSoundType.getFallSound(), SoundCategory.BLOCKS, this.blockSoundType.getVolume() * 0.5F, this.blockSoundType.getPitch() * 0.75F);
		}
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if(entityIn instanceof EntityLivingBase) {
			//Janky but works?
			((IEntityLivingBaseMixin)((EntityLivingBase)entityIn)).setJumpTicks(10);
		}
		
		super.onEntityWalk(worldIn, pos, entityIn);
	}
	
	@Override
	public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
		if(this.isSlidingDown(pos, entity)) {
			this.doSlideMovement(entity);
			if(entity instanceof EntityLivingBase) {
				if(world.rand.nextInt(5) == 0) {
					entity.playSound(ModRegistry.BLOCK_HONEY_SLIDE, 1.0F, 1.0F);
				}
			}
		}
		else if(entity.motionY <= 0.05D){
			entity.motionX *= 0.4D;
			entity.motionZ *= 0.4D;
		}
		
		
		super.onEntityCollision(world, pos, state, entity);
	}
	
	private boolean isSlidingDown(BlockPos pos, Entity entity) {
		if(entity.onGround) {
			return false;
		}
		else if(entity.posY > (double)pos.getY() + 0.9375D - 1.0E-7D) {
			return false;
		}
		else if(entity.motionY >= -0.08D) {
			return false;
		}
		else {
			double d0 = Math.abs((double)pos.getX() + 0.5D - entity.posX);
			double d1 = Math.abs((double)pos.getZ() + 0.5D - entity.posZ);
			double d2 = 0.4375D + (double)(entity.width / 2.0F);
			return d0 + 1.0E-7D > d2 || d1 + 1.0E-7D > d2;
		}
	}
	
	private void doSlideMovement(Entity entity) {
		if(entity.motionY < -0.13D) {
			double d0 = -0.05D / entity.motionY;
			entity.motionX = entity.motionX * d0;
			entity.motionY = -0.05D;
			entity.motionZ = entity.motionZ * d0;
		}
		else {
			entity.motionY = -0.05D;
		}
		entity.fallDistance = 0.0F;
		entity.onGround = true;
	}
}