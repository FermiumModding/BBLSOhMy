package bblsom.blocks;

import bblsom.BBLSOhMy;
import bblsom.handlers.ModRegistry;
import bblsom.mixin.vanilla.IBlockBushMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;

public class CustomBlockDirt extends Block implements ICustomBlockFertile, ICustomBlockTillable {
	
	private final ResourceLocation farmlandBlockLocation;
	private final boolean supportsGeneralPlants;
	private final boolean supportsSandyPlants;
	private final boolean supportsMushrooms;
	
	private Block farmlandBlock;
	
	public CustomBlockDirt(String name, boolean supportsGeneralPlants, boolean supportsSandyPlants, boolean supportsMushrooms, float hardness, Material material, SoundType soundType, ResourceLocation farmlandBlockLocation) {
		super(material);
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setHardness(hardness);
		this.setSoundType(soundType);
		this.supportsGeneralPlants = supportsGeneralPlants;
		this.supportsSandyPlants = supportsSandyPlants;
		this.supportsMushrooms = supportsMushrooms;
		this.farmlandBlockLocation = farmlandBlockLocation;
		ModRegistry.ITEMS_DIRT.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setTranslationKey(this.getTranslationKey()));
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
}