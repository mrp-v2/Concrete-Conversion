package mrp_v2.concreteconversion.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mrp_v2.concreteconversion.util.EventHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("HEAD"), method = "dropItem", cancellable = true)
	public void dropItem(ItemStack stack, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
		if(!this.world.isClient) {
			ItemEntity drop = this.dropItem(stack, false, retainOwnership);
			EventHandler.addPlayerThrownItemEntity(drop);
			cir.setReturnValue(drop);
			cir.cancel();
		}
	}
	
	@Shadow
	public abstract ItemEntity dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership);

}
