package mrp_v2.concreteconversion.mixin;

import net.minecraft.entity.item.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FallingBlockEntity.class) public class FallingBlockEntityMixin
{
    @ModifyVariable(at = @At("HEAD"), method = "tick", name = "flag") private boolean tick(boolean flag)
    {
        return false;
    }
}
