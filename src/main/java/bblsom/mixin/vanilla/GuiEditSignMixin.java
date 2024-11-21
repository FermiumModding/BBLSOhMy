package bblsom.mixin.vanilla;

import bblsom.blocks.CustomBlockStandingSign;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiEditSign.class)
public abstract class GuiEditSignMixin {
	
	@Redirect(
			method = "drawScreen",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/TileEntitySign;getBlockType()Lnet/minecraft/block/Block;")
	)
	public Block bblsom_vanillaGuiEditSign_drawScreen_getBlockType(TileEntitySign instance) {
		Block block = instance.getBlockType();
		//Janky workaround but easier
		return block instanceof CustomBlockStandingSign ? Blocks.STANDING_SIGN : block;
	}
}