package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ModRegistry;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class CustomBlockPressurePlate extends BlockPressurePlate {
	
	private final Sensitivity sens;
	private final boolean playerOnly;
	
	public CustomBlockPressurePlate(String name, boolean wooden, boolean playerOnly) {
		super(wooden ? Material.WOOD : Material.ROCK, wooden ? Sensitivity.EVERYTHING : Sensitivity.MOBS);
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.setHardness(0.5F);
		this.setSoundType(wooden ? SoundType.WOOD : SoundType.STONE);
		this.sens = wooden ? Sensitivity.EVERYTHING : Sensitivity.MOBS;
		this.playerOnly = playerOnly;
		ModRegistry.ITEMS_PRESSUREPLATES.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey()));
	}
	
	@Override
	protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
		AxisAlignedBB axisalignedbb = PRESSURE_AABB.offset(pos);
		List<? extends Entity> list;
		
		if(this.playerOnly) {
			list = worldIn.<Entity>getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
		}
		else {
			switch(this.sens) {
				case EVERYTHING:
					list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);
					break;
				case MOBS:
					list = worldIn.<Entity>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
					break;
				default:
					return 0;
			}
		}
		
		if(!list.isEmpty()) {
			for(Entity entity : list) {
				if(!entity.doesEntityNotTriggerPressurePlate()) {
					return 15;
				}
			}
		}
		
		return 0;
	}
}