package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ModRegistry;
import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CustomBlockPressurePlateWeighted extends BlockPressurePlateWeighted {
	
	private final int mweight;
	private final boolean playerOnly;
	
	public CustomBlockPressurePlateWeighted(String name, int maxWeight, boolean playerOnly) {
		super(Material.IRON, maxWeight);
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.setHardness(0.5F);
		this.setSoundType(SoundType.WOOD);
		this.mweight = maxWeight;
		this.playerOnly = playerOnly;
		ModRegistry.ITEMS_PRESSUREPLATESWEIGHTED.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey()));
	}
	
	@Override
	protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
		int i = Math.min(worldIn.getEntitiesWithinAABB((playerOnly ? EntityPlayer.class : Entity.class), PRESSURE_AABB.offset(pos)).size(), this.mweight);
		
		if(i > 0) {
			float f = (float)Math.min(this.mweight, i) / (float)this.mweight;
			return MathHelper.ceil(f * 15.0F);
		}
		else {
			return 0;
		}
	}
}