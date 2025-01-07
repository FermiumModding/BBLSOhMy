package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ModRegistry;
import bblsom.mixin.vanilla.IBlockBushMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.util.Random;

public class CustomBlockFarmland extends BlockFarmland implements ICustomBlockLayered {
	
	private final ResourceLocation baseBlockLocation;
	private final float growthMult;
	private final boolean alwaysFertile;
	
	private Block baseBlock;
	
	public CustomBlockFarmland(String name, ResourceLocation baseBlockLocation, float growthMult, boolean alwaysFertile) {
		super();
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.setHardness(0.6F);
		this.setSoundType(SoundType.GROUND);
		this.setDefaultState(this.blockState.getBaseState().withProperty(MOISTURE, alwaysFertile ? 7 : 0));
		this.baseBlockLocation = baseBlockLocation;
		this.growthMult = growthMult;
		this.alwaysFertile = alwaysFertile;
		ModRegistry.ITEMS_FARMLAND.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey()));
	}
	
	@Nonnull
	@Override
	public Block getBaseBlock() {
		if(this.baseBlock == null) {
			this.baseBlock = ForgeRegistries.BLOCKS.getValue(this.baseBlockLocation);
			if(this.baseBlock == null) {
				BBLSOhMy.LOGGER.log(Level.WARN, "Failed to find base block: " + this.baseBlockLocation + " for block: " + this.getRegistryName());
				this.baseBlock = Blocks.DIRT;
			}
		}
		return this.baseBlock;
	}
	
	public float getFarmlandMult() {
		return this.growthMult;
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(this.alwaysFertile) {
			int i = state.getValue(MOISTURE);
			if(i < 7) {
				worldIn.setBlockState(pos, state.withProperty(MOISTURE, 7), 2);
			}
		}
		else {
			int i = state.getValue(MOISTURE);
			
			if(!this.hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.up())) {
				if(i > 0) {
					worldIn.setBlockState(pos, state.withProperty(MOISTURE, i - 1), 2);
				}
				else if(!this.hasCrops(worldIn, pos)) {
					this.turnInstanceToDirt(worldIn, pos);
				}
			}
			else if(i < 7) {
				worldIn.setBlockState(pos, state.withProperty(MOISTURE, 7), 2);
			}
		}
	}
	
	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		if(net.minecraftforge.common.ForgeHooks.onFarmlandTrample(worldIn, pos, this.getBaseBlock().getDefaultState(), fallDistance, entityIn)) {
			this.turnInstanceToDirt(worldIn, pos);
		}
		
		entityIn.fall(fallDistance, 1.0F);
	}
	
	private void turnInstanceToDirt(World world, BlockPos worldIn) {
		world.setBlockState(worldIn, this.getBaseBlock().getDefaultState());
		AxisAlignedBB axisalignedbb = field_194405_c.offset(worldIn);
		
		for(Entity entity : world.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb)) {
			double d0 = Math.min(axisalignedbb.maxY - axisalignedbb.minY, axisalignedbb.maxY - entity.getEntityBoundingBox().minY);
			entity.setPositionAndUpdate(entity.posX, entity.posY + d0 + 0.001D, entity.posZ);
		}
	}
	
	private boolean hasCrops(World worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return block instanceof net.minecraftforge.common.IPlantable && canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable)block);
	}
	
	private boolean hasWater(World worldIn, BlockPos pos) {
		for(BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
			if(worldIn.getBlockState(blockpos$mutableblockpos).getMaterial() == Material.WATER) {
				return true;
			}
		}
		return net.minecraftforge.common.FarmlandWaterManager.hasBlockWaterTicket(worldIn, pos);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
			this.turnInstanceToDirt(worldIn, pos);
		}
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if(worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
			this.turnInstanceToDirt(worldIn, pos);
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return this.getBaseBlock().getItemDropped(this.getBaseBlock().getDefaultState(), rand, fortune);
	}
	
	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, net.minecraftforge.common.IPlantable plantable) {
		net.minecraftforge.common.EnumPlantType plantType = plantable.getPlantType(world, pos.offset(direction));
		if(plantable instanceof BlockBush && ((IBlockBushMixin)plantable).getCanSustainBush(state)) return true;
		
		switch(plantType) {
			case Desert:
			case Nether:
			case Water:
			case Beach: return false;
			case Crop:
			case Plains: return true;
			case Cave: return state.isSideSolid(world, pos, EnumFacing.UP);
		}
		return false;
	}
	
	@Override
	public void onPlantGrow(IBlockState state, World world, BlockPos pos, BlockPos source) {
		world.setBlockState(pos, this.getBaseBlock().getDefaultState(), 2);
	}
	
	@Override
	public boolean isFertile(World world, BlockPos pos) {
		if(this.alwaysFertile) return true;
		return world.getBlockState(pos).getValue(BlockFarmland.MOISTURE) > 0;
	}
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if(base_state.isTopSolid() && side == EnumFacing.UP) return true;
		return (side != EnumFacing.DOWN && side != EnumFacing.UP);
	}
}