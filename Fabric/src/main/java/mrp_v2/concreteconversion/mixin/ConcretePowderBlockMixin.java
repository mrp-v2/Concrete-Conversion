package mrp_v2.concreteconversion.mixin;

import mrp_v2.concreteconversion.ConcreteConversionCommon;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConcretePowderBlock.class)
public class ConcretePowderBlockMixin {

    @Inject(method = "canSolidify", at = @At("HEAD"), cancellable = true)
    private static void canSolidify(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (ConcreteConversionCommon.CONFIG.getDisableVanillaConversionMechanic()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
