package mrp_v2.concreteconversion.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ConcretePowderBlock.class) public class ConcretePowderBlockMixin
{
    @Redirect(at = @At("HEAD"), method = "causesSolidify") private static boolean causesSolidify(BlockState state)
    {
        return false;
    }
}
