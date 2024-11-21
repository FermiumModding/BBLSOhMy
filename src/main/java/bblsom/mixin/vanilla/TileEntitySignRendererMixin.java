package bblsom.mixin.vanilla;

import bblsom.blocks.CustomBlockStandingSign;
import bblsom.blocks.ICustomBlockSign;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TileEntitySignRenderer.class)
public abstract class TileEntitySignRendererMixin extends TileEntitySpecialRenderer<TileEntitySign> {
	
	@Unique
	private ResourceLocation bblsom$currentSignTexture = null;
	
	@Redirect(
			method = "render(Lnet/minecraft/tileentity/TileEntitySign;DDDFIF)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/TileEntitySign;getBlockType()Lnet/minecraft/block/Block;")
	)
	public Block bblsom_vanillaTileEntitySignRenderer_render_getBlockType(TileEntitySign instance) {
		Block block = instance.getBlockType();
		if(block instanceof ICustomBlockSign) this.bblsom$currentSignTexture = ((ICustomBlockSign)block).getSignTexture();
		else this.bblsom$currentSignTexture = null;
		//Janky workaround but easier
		return block instanceof CustomBlockStandingSign ? Blocks.STANDING_SIGN : block;
	}
	
	@Redirect(
			method = "render(Lnet/minecraft/tileentity/TileEntitySign;DDDFIF)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/tileentity/TileEntitySignRenderer;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", ordinal = 1)
	)
	public void bblsom_vanillaTileEntitySignRenderer_render_bindTexture(TileEntitySignRenderer instance, ResourceLocation resourceLocation) {
		if(this.bblsom$currentSignTexture != null) this.bindTexture(this.bblsom$currentSignTexture);
		else this.bindTexture(resourceLocation);
	}
}