package bblsom.mixin.vanilla;

import bblsom.entity.CustomEntityBoat;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderBoat.class)
public abstract class RenderBoatMixin {
	
	@Inject(
			method = "getEntityTexture(Lnet/minecraft/entity/item/EntityBoat;)Lnet/minecraft/util/ResourceLocation;",
			at = @At("HEAD"),
			cancellable = true
	)
	public void bblsom_vanillaRenderBoat_getEntityTexture(EntityBoat entity, CallbackInfoReturnable<ResourceLocation> cir) {
		if(entity instanceof CustomEntityBoat) {
			cir.setReturnValue(((CustomEntityBoat)entity).getCustomBoatType().getTexture());
		}
	}
}