package mrp_v2.concreteconversion.mixin;

import mrp_v2.concreteconversion.server.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConcretePowderBlock.class) public class ConcretePowderBlockMixin
{
    @Inject(at = @At("HEAD"), method = "causesSolidify", cancellable = true)
    private static void causesSolidify(BlockState state, CallbackInfoReturnable<Boolean> cir)
    {
        if (Config.SERVER.getDisableVanillaConversionMechanic())
        {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
