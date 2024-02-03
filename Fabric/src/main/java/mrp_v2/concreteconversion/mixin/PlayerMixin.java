package mrp_v2.concreteconversion.mixin;

import mrp_v2.concreteconversion.event.ConcreteEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(method = "drop(Lnet/minecraft/world/item/ItemStack;Z)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At("RETURN"), cancellable = true)
    public void drop(CallbackInfoReturnable<ItemEntity> cir) {
        if (cir.getReturnValue() != null)
            if (!ConcreteEvents.ITEM_TOSS.post().handle(cir.getReturnValue(), (Player)(Object)this))
                cir.cancel();
    }
}
