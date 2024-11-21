package bblsom.item;

import bblsom.BBLSOhMy;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomItemHorseArmor extends Item {
	
	private final HorseArmorType horseArmorType;
	
	public CustomItemHorseArmor(String name, HorseArmorType horseArmorType) {
		super();
		this.setRegistryName(BBLSOhMy.MODID + ":" + name);
		this.setTranslationKey(BBLSOhMy.MODID + "." + name);
		this.horseArmorType = horseArmorType;
	}
	
	@Override
	public HorseArmorType getHorseArmorType(ItemStack stack) {
		return this.horseArmorType;
	}
}