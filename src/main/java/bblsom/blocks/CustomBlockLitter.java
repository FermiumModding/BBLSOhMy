package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ModRegistry;
import bblsom.item.CustomItemLitter;
import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class CustomBlockLitter extends BlockBush {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyInteger SLICES = PropertyInteger.create("slices", 0, 3);
	
	private static final AxisAlignedBB AABB_FULL = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
	
	private final boolean checkCanStay;
	private final double dropChance;
	private final Item dropItem;
	
	public CustomBlockLitter(String name, boolean checkCanStay, double dropChance, SoundType soundType) {
		super();
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SLICES, 0));
		this.checkCanStay = checkCanStay;
		this.dropChance = dropChance;
		this.dropItem = new CustomItemLitter(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey());
		ModRegistry.ITEMS_LITTER.add(this.dropItem);
		this.setSoundType(soundType);
	}
	
	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		if(state.getBlock() == this) {
			IBlockState soil = worldIn.getBlockState(pos.down());
			return this.checkCanStay ? soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this) : soil.isSideSolid(worldIn, pos, EnumFacing.UP);
		}
		return !this.checkCanStay || this.canSustainBush(worldIn.getBlockState(pos.down()));
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return this.dropChance > 0 ? this.dropItem : Items.AIR;
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this.dropItem, 1);
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		if(state.getBlock() != this) return 0;
		int toDrop = 0;
		for(int i = 0; i < state.getValue(SLICES) + 1; i++) {
			if(random.nextFloat() < this.dropChance) toDrop++;
		}
		return toDrop;
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		if(state.getBlock() != this) return state;
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		if(state.getBlock() != this) return state;
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(state.getBlock() != this) return;
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2);
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta&3)).withProperty(SLICES, (meta&15)>>2);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		if(state.getBlock() != this) return 0;
		int i = 0;
		i |= state.getValue(FACING).getHorizontalIndex();
		i |= state.getValue(SLICES) << 2;
		return i;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, SLICES);
	}
		
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB_FULL;
	}
	
	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean isTopSolid(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean canSpawnInBlock() {
		return true;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState soil = worldIn.getBlockState(pos.down());
		return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) && (!this.checkCanStay || soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this));
	}
	
	@Override
	public net.minecraftforge.common.EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Plains;
	}
	
	@Override
	public IBlockState getPlant(net.minecraft.world.IBlockAccess world, BlockPos pos) {
		return this.getDefaultState();
	}
}