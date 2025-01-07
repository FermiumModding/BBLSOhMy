package bblsom.mixin.vanilla;

import bblsom.handlers.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderGlobal.class)
public abstract class RenderGlobalMixin {
	
	@Shadow @Final private Minecraft mc;
	
	@Inject(
			method = "loadRenderers",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockLeaves;setGraphicsLevel(Z)V", ordinal = 0)
	)
	private void bblsom_vanillaRenderGlobal_loadRenderers(CallbackInfo ci) {
		for(Block block : ModRegistry.BLOCKS_LEAVES) {
			if(block instanceof BlockLeaves) {
				((BlockLeaves)block).setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
			}
		}
	}
}