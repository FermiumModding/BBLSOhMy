package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ModRegistry;
import bblsom.item.CustomItemLeaves;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class CustomBlockLeaves extends BlockLeaves {
	
	public static final PropertyEnum<AgePhase> AGE = PropertyEnum.create("age", AgePhase.class);
	
	private final Item saplingItem;
	
	private final Item fruitItem;
	private final float fruitChanceGeneral;
	private final float fruitChanceFruiting;
	private final boolean canGrow;
	private final float growthChance;
	
	public CustomBlockLeaves(String name, Item saplingItem, boolean decayable, Item fruitItem, float fruitChanceGeneral, float fruitChanceFruiting, boolean canGrow, float growthChance) {
		super();
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, AgePhase.NORMAL).withProperty(CHECK_DECAY, decayable).withProperty(DECAYABLE, decayable));
		this.saplingItem = saplingItem;
		this.fruitItem = fruitItem;
		this.fruitChanceGeneral = fruitChanceGeneral;
		this.fruitChanceFruiting = fruitChanceFruiting;
		this.canGrow = canGrow;
		this.growthChance = growthChance;
		ModRegistry.ITEMS_LEAVES.add(new CustomItemLeaves(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey()));
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		if(!worldIn.isRemote && this.canGrow) {
			if(!worldIn.isAreaLoaded(pos, 1)) return;
			if(rand.nextFloat() < this.growthChance) {
				IBlockState worldState = worldIn.getBlockState(pos);
				//Likely decayed, don't grow
				if(worldState.getBlock() != this) return;
				int age = worldState.getValue(AGE).getMetadata();
				if(age < 2) {
					worldIn.setBlockState(pos, this.getDefaultState()
												   .withProperty(AGE, AgePhase.fromMetadata(age + 1))
												   .withProperty(CHECK_DECAY, worldState.getValue(CHECK_DECAY))
												   .withProperty(DECAYABLE, worldState.getValue(DECAYABLE)), 2);
				}
			}
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return saplingItem;
	}
	
	@Override
	protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
		if(this.fruitItem != null) {
			if(state.getValue(AGE) == AgePhase.FRUITING) {
				if(worldIn.rand.nextFloat() < this.fruitChanceFruiting) {
					spawnAsEntity(worldIn, pos, new ItemStack(this.fruitItem));
				}
			}
			else {
				if(worldIn.rand.nextFloat() < this.fruitChanceGeneral) {
					spawnAsEntity(worldIn, pos, new ItemStack(this.fruitItem));
				}
			}
		}
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this, 1, state.getBlock().getMetaFromState(state)&3);
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this, 1, 0));
		items.add(new ItemStack(this, 1, 1));
		items.add(new ItemStack(this, 1, 2));
	}
	
	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(AGE).getMetadata());
	}
	
	@Override
	public BlockPlanks.EnumType getWoodType(int meta) {
		//Seems only used for metadata variants which shouldn't matter for this
		return BlockPlanks.EnumType.OAK;
	}
	
	public AgePhase getAgePhase(int meta) {
		return AgePhase.fromMetadata((meta&3)%4);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AGE, CHECK_DECAY, DECAYABLE);
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(AGE).getMetadata();
	}
	
	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
		if(!worldIn.isRemote && stack.getItem() == Items.SHEARS) {
			spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(AGE).getMetadata()));
		}
		else {
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}
	
	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this, 1, world.getBlockState(pos).getValue(AGE).getMetadata()));
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AGE, this.getAgePhase(meta)).withProperty(DECAYABLE, (meta&4) == 0).withProperty(CHECK_DECAY, (meta&8) > 0);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(AGE).getMetadata();
		
		if(!state.getValue(DECAYABLE)) {
			i |= 4;
		}
		
		if(state.getValue(CHECK_DECAY)) {
			i |= 8;
		}
		
		return i;
	}
	
	public enum AgePhase implements IStringSerializable {
		
		NORMAL(0, "normal"),
		FLOWERING(1, "flowering"),
		FRUITING(2, "fruiting");
		
		private static final AgePhase[] METADATA_LOOKUP = new AgePhase[values().length];
		
		private final int metadata;
		private final String name;
		
		AgePhase(int metadata, String name) {
			this.metadata = metadata;
			this.name = name;
		}
		
		public static AgePhase fromMetadata(int metadata) {
			if(metadata < 0 || metadata >= METADATA_LOOKUP.length) {
				metadata = 0;
			}
			return METADATA_LOOKUP[metadata];
		}
		
		public int getMetadata() {
			return this.metadata;
		}
		
		public String getName() {
			return this.name;
		}
		
		public String toString() {
			return this.name;
		}
		
		static {
			for(AgePhase phase : values()) {
				METADATA_LOOKUP[phase.getMetadata()] = phase;
			}
		}
	}
}