package bblsom.mixin.vanilla;

import bblsom.entity.CustomEntityBoat;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityBoat.class)
public abstract class EntityBoatMixin {
	
	@Redirect(
			method = "updateFallState",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityBoat;entityDropItem(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/item/EntityItem;")
	)
	public EntityItem bblsom_vanillaEntityBoat_updateFallState_entityDropItem(EntityBoat instance, ItemStack stack, float v) {
		if(instance instanceof CustomEntityBoat) {
			return instance.entityDropItem(((CustomEntityBoat)instance).getDroppedPlanks(), v);
		}
		return instance.entityDropItem(stack, v);
	}
	
	@Redirect(
			method = "updateFallState",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityBoat;dropItemWithOffset(Lnet/minecraft/item/Item;IF)Lnet/minecraft/entity/item/EntityItem;")
	)
	public EntityItem bblsom_vanillaEntityBoat_updateFallState_dropItemWithOffset(EntityBoat instance, Item item, int i, float v) {
		if(instance instanceof CustomEntityBoat) {
			return instance.entityDropItem(((CustomEntityBoat)instance).getDroppedSticks(), v);
		}
		return instance.dropItemWithOffset(item, i, v);
	}
}