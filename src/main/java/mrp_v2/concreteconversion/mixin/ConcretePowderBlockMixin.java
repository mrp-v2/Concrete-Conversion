package mrp_v2.concreteconversion.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mrp_v2.concreteconversion.server.Config;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(ConcretePowderBlock.class) public class ConcretePowderBlockMixin
{
    @Inject(at = @At("HEAD"), method = "canSolidify", cancellable = true)
    private static void canSolidify(BlockState state, CallbackInfoReturnable<Boolean> cir)
    {
        if (Config.SERVER.getDisableVanillaConversionMechanic())
        {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
