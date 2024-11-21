package bblsom.blocks;

import bblsom.BBLSOhMy;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class CustomBlockWallSign extends BlockWallSign implements ICustomBlockSign {
	
	private final ResourceLocation SIGN_TEXTURE;
	
	private final Item item;
	
	public CustomBlockWallSign(String name, Item item) {
		super();
		this.setRegistryName(BBLSOhMy.MODID + ":" + name + "_wall");
		this.setTranslationKey(BBLSOhMy.MODID + "." + name + "_wall");
		this.setHardness(0.5F);
		this.setSoundType(SoundType.WOOD);
		this.item = item;
		this.SIGN_TEXTURE = new ResourceLocation(BBLSOhMy.MODID + ":" + "textures/signs/" + name + ".png");
	}
	
	@Override
	public ResourceLocation getSignTexture() {
		return this.SIGN_TEXTURE;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return this.item;
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this.item);
	}
}