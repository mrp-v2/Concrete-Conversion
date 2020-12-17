package mrp_v2.concreteconversion.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;

@Mixin(ConcretePowderBlock.class)
public interface ConcretePowderBlockAccessor {
	@Accessor("hardenedState")
	public BlockState getHardenedState();
}