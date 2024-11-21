package bblsom.mixin.vanilla;

import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityLivingBase.class)
public interface IEntityLivingBaseMixin {
	
	@Accessor("jumpTicks")
	void setJumpTicks(int jumpTicks);
}