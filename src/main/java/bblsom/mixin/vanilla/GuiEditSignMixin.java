package bblsom.mixin.vanilla;

import bblsom.blocks.CustomBlockStandingSign;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiEditSign.class)
public abstract class GuiEditSignMixin {
	
	@ModifyExpressionValue(
			method = "drawScreen",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/TileEntitySign;getBlockType()Lnet/minecraft/block/Block;")
	)
	private Block bblsom_vanillaGuiEditSign_drawScreen_getBlockType(Block original) {
		if(original instanceof CustomBlockStandingSign) return Blocks.STANDING_SIGN;
		return original;
	}
}