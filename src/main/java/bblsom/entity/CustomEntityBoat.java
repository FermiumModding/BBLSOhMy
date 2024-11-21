package bblsom.entity;

import bblsom.BBLSOhMy;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class CustomEntityBoat extends EntityBoat {
	
	private static final DataParameter<String> CUSTOM_BOAT_TYPE = EntityDataManager.<String>createKey(CustomEntityBoat.class, DataSerializers.STRING);
	
	public CustomEntityBoat(World worldIn) {
		super(worldIn);
	}
	
	public CustomEntityBoat(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}
	
	public ItemStack getDroppedPlanks() {
		return this.getCustomBoatType().getDroppedPlanks();
	}
	
	public ItemStack getDroppedSticks() {
		return this.getCustomBoatType().getDroppedSticks();
	}
	
	public void setCustomBoatType(CustomBoatType customBoatType) {
		this.dataManager.set(CUSTOM_BOAT_TYPE, customBoatType.getName());
	}
	
	public CustomBoatType getCustomBoatType() {
		return CustomBoatType.getCustomTypeFromString(this.dataManager.get(CUSTOM_BOAT_TYPE));
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(CUSTOM_BOAT_TYPE, "null");
	}
	
	@Override
	public Item getItemBoat() {
		return this.getCustomBoatType().getItemBoat();
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setString("CustomType", this.getCustomBoatType().getName());
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("CustomType", 8)) {
			this.setCustomBoatType(CustomBoatType.getCustomTypeFromString(compound.getString("CustomType")));
		}
	}
	
	public static class CustomBoatType {
		
		public static final List<CustomBoatType> CUSTOM_BOAT_TYPES = new ArrayList<>();
		
		public static final CustomBoatType NULL_TYPE;
		
		static {
			//Fallback default if there's a data mismatch for whatever reason
			CustomBoatType nullType = new CustomBoatType("null");
			nullType.setItemBoat(Items.BOAT);
			nullType.setDroppedPlanks(Item.getItemFromBlock(Blocks.PLANKS), 0);
			nullType.setDroppedSticks(Items.STICK, 0);
			NULL_TYPE = nullType;
		}
		
		private final String name;
		private final ResourceLocation texture;
		private Item boatItem = null;
		private Item plankItem = null;
		private int plankMeta = 0;
		private Item stickItem = null;
		private int stickMeta = 0;
		
		public CustomBoatType(String name) {
			this.name = name;
			this.texture = new ResourceLocation(BBLSOhMy.MODID + ":" + "textures/entity/boat/" + name + ".png");
		}
		
		public String getName() {
			return this.name;
		}
		
		public ResourceLocation getTexture() {
			return this.texture;
		}
		
		public Item getItemBoat() {
			return this.boatItem;
		}
		
		public ItemStack getDroppedPlanks() {
			return new ItemStack(this.plankItem, 1, this.plankMeta);
		}
		
		public ItemStack getDroppedSticks() {
			return new ItemStack(this.stickItem, 1, this.stickMeta);
		}
		
		public void setItemBoat(Item boatItem) {
			this.boatItem = boatItem;
		}
		
		public void setDroppedPlanks(Item plankItem, int plankMeta) {
			this.plankItem = plankItem;
			this.plankMeta = plankMeta;
		}
		
		public void setDroppedSticks(Item stickItem, int stickMeta) {
			this.stickItem = stickItem;
			this.stickMeta = stickMeta;
		}
		
		public static CustomBoatType getCustomTypeFromString(String name) {
			for(CustomBoatType customBoatType : CUSTOM_BOAT_TYPES) {
				if(customBoatType.getName().equals(name)) return customBoatType;
			}
			BBLSOhMy.LOGGER.log(Level.ERROR, "Attempted to get custom boat by name: " + name + " but it is not registered");
			return NULL_TYPE;
		}
	}
}