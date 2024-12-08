package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ModRegistry;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class CustomBlockDoor extends BlockDoor {
	
	private final Item doorItem;
	
	public CustomBlockDoor(String name, boolean wooden) {
		super(wooden ? Material.WOOD : Material.IRON);
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.setHardness(wooden ? 3.0F : 5.0F);
		this.setSoundType(wooden ? SoundType.WOOD : SoundType.METAL);
		this.doorItem = new ItemDoor(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey());
		ModRegistry.ITEMS_DOORS.add(this.doorItem);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : this.getItemDropped();
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this.getItemDropped());
	}
	
	private Item getItemDropped() {
		return this.doorItem;
	}
}