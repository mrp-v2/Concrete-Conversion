package mrp_v2.concreteconversion.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mrp_v2.concreteconversion.server.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;

@Mixin(ConcretePowderBlock.class) public class ConcretePowderBlockMixin
{
    @Inject(at = @At("HEAD"), method = "hardensIn", cancellable = true)
    private static void hardensIn(BlockState state, CallbackInfoReturnable<Boolean> cir)
    {
        if (Config.disableVanillaConversionMechanic)
        {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}