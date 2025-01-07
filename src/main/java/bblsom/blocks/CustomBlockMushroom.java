package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.generator.CustomWorldGenBigMushroom;
import bblsom.handlers.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class CustomBlockMushroom extends BlockMushroom {
	
	private final boolean canGrowLarge;
	private final boolean rounded;
	
	private final Block largeMushroomBlock;
	
	public CustomBlockMushroom(String name, boolean canGrowLarge, boolean rounded) {
		super();
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.setHardness(0.0F);
		this.setSoundType(SoundType.PLANT);
		this.canGrowLarge = canGrowLarge;
		this.rounded = rounded;
		this.largeMushroomBlock = new CustomBlockHugeMushroom(name, this);
		ModRegistry.BLOCKS_HUGEMUSHROOM.add(this.largeMushroomBlock);
		ModRegistry.ITEMS_MUSHROOM.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey()));
	}
	
	@Override
	public boolean generateBigMushroom(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		worldIn.setBlockToAir(pos);
		WorldGenerator worldgenerator = new CustomWorldGenBigMushroom(this.largeMushroomBlock, this.rounded);
		if(worldgenerator.generate(worldIn, rand, pos)) {
			return true;
		}
		else {
			worldIn.setBlockState(pos, state, 3);
			return false;
		}
	}
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return this.canGrowLarge;
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return this.canGrowLarge && (double)rand.nextFloat() < 0.4D;
	}
	
	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		if(this.canGrowLarge) this.generateBigMushroom(worldIn, pos, state, rand);
	}
	
	@Override
	public net.minecraftforge.common.EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos) {
		return net.minecraftforge.common.EnumPlantType.Cave;
	}
}