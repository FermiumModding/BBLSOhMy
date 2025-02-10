package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ForgeConfigHandler;
import bblsom.handlers.ModRegistry;
import bblsom.mixin.vanilla.IBlockBushMixin;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

public class CustomBlockGrass extends Block implements ICustomBlockFertile, ICustomBlockLayered, ICustomBlockTillable {
	
	private final ResourceLocation baseBlockLocation;
	private final ResourceLocation farmlandBlockLocation;
	private final boolean supportsGeneralPlants;
	private final boolean supportsSandyPlants;
	private final boolean supportsMushrooms;
	
	private Block baseBlock;
	private Block farmlandBlock;
	
	public CustomBlockGrass(String name, boolean supportsGeneralPlants, boolean supportsSandyPlants, boolean supportsMushrooms, float hardness, Material material, SoundType soundType, ResourceLocation baseBlockLocation, ResourceLocation farmlandBlockLocation) {
		super(material);
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.setHardness(hardness);
		this.setSoundType(soundType);
		this.setDefaultState(this.blockState.getBaseState().withProperty(BlockGrass.SNOWY, Boolean.FALSE));
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.supportsGeneralPlants = supportsGeneralPlants;
		this.supportsSandyPlants = supportsSandyPlants;
		this.supportsMushrooms = supportsMushrooms;
		this.baseBlockLocation = baseBlockLocation;
		this.farmlandBlockLocation = farmlandBlockLocation;
		ModRegistry.ITEMS_GRASS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey()));
	}
	
	@Override
	public boolean getSupportsGeneralPlants() {
		return this.supportsGeneralPlants;
	}
	
	@Override
	public boolean getSupportsSandyPlants() {
		return this.supportsSandyPlants;
	}
	
	@Override
	public boolean getSupportsMushrooms() {
		return this.supportsMushrooms;
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
	
	@Nullable
	@Override
	public Block getFarmlandBlock() {
		if(this.farmlandBlock == null && this.farmlandBlockLocation != null) {
			this.farmlandBlock = ForgeRegistries.BLOCKS.getValue(this.farmlandBlockLocation);
			if(this.farmlandBlock == null) {
				BBLSOhMy.LOGGER.log(Level.WARN, "Failed to find farmland block: " + this.farmlandBlockLocation + " for block: " + this.getRegistryName());
				this.farmlandBlock = Blocks.FARMLAND;
			}
		}
		return this.farmlandBlock;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return state.withProperty(BlockGrass.SNOWY, block == Blocks.SNOW || block == Blocks.SNOW_LAYER);
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(!worldIn.isRemote) {
			if(!worldIn.isAreaLoaded(pos, 3)) return;
			if(worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
				worldIn.setBlockState(pos, this.getBaseBlock().getDefaultState());
			}
			else {
				if(worldIn.getLightFromNeighbors(pos.up()) >= 9) {
					for(int i = 0; i < 4; ++i) {
						BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
						
						if(blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
							return;
						}
						
						IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
						IBlockState iblockstate1 = worldIn.getBlockState(blockpos);
						
						Map<ForgeConfigHandler.BlockEntry,ForgeConfigHandler.BlockEntry> conversionMap = ForgeConfigHandler.getGrassConversions(state);
						if(conversionMap != null) {
							boolean matched = false;
							for(Map.Entry<ForgeConfigHandler.BlockEntry,ForgeConfigHandler.BlockEntry> entry : conversionMap.entrySet()) {
								if(entry.getKey().entryMatches(iblockstate1)) {
									if(worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, blockpos.up()) <= 2) {
										worldIn.setBlockState(blockpos, entry.getValue().getState());
									}
									matched = true;
									break;
								}
							}
							//Continue if there was a valid result, to allow for replacing default block handling
							if(matched) continue;
						}
						
						if(iblockstate1.getBlock() == this.getBaseBlock() && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, blockpos.up()) <= 2) {
							worldIn.setBlockState(blockpos, this.getDefaultState());
						}
					}
				}
			}
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return this.getBaseBlock().getItemDropped(this.getBaseBlock().getDefaultState(), rand, fortune);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockGrass.SNOWY);
	}
	
	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, net.minecraftforge.common.IPlantable plantable) {
		IBlockState plant = plantable.getPlant(world, pos.offset(direction));
		net.minecraftforge.common.EnumPlantType plantType = plantable.getPlantType(world, pos.offset(direction));
		
		if(plant.getBlock() == Blocks.CACTUS) return this.getSupportsSandyPlants();
		if(plantable instanceof BlockBush && ((IBlockBushMixin)plantable).getCanSustainBush(state)) return true;
		
		switch(plantType) {
			case Water:
			case Nether:
			case Crop: return false;
			case Plains: return this.getSupportsGeneralPlants();
			case Desert: return this.getSupportsSandyPlants();
			case Cave:   return state.isSideSolid(world, pos, EnumFacing.UP);
			case Beach: return (this.getSupportsGeneralPlants() || this.getSupportsSandyPlants()) &&
					(world.getBlockState(pos.east()).getMaterial() == Material.WATER ||
							world.getBlockState(pos.west()).getMaterial() == Material.WATER ||
							world.getBlockState(pos.north()).getMaterial() == Material.WATER ||
							world.getBlockState(pos.south()).getMaterial() == Material.WATER);
		}
		return false;
	}
	
	@Override
	public void onPlantGrow(IBlockState state, World world, BlockPos pos, BlockPos source) {
		world.setBlockState(pos, this.getBaseBlock().getDefaultState(), 2);
	}
}