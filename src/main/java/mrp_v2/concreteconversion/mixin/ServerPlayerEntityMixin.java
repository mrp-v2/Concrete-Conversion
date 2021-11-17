package mrp_v2.concreteconversion.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.GameProfile;

import mrp_v2.concreteconversion.util.EventHandler;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	
	public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}

	@Inject(at = @At("HEAD"), method = "dropItem", cancellable = true)
	public void dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
		if(!this.world.isClient) {
			ItemEntity drop = super.dropItem(stack, throwRandomly, retainOwnership);
			if (drop == null) {
				cir.setReturnValue(null);
				cir.cancel();
			} else {
				this.world.spawnEntity(drop);
				ItemStack itemStack = drop.getStack();
				if (retainOwnership) {
					if (!itemStack.isEmpty()) {
						this.increaseStat(Stats.DROPPED.getOrCreateStat(itemStack.getItem()), stack.getCount());
					}

					this.incrementStat(Stats.DROP);
				}

				EventHandler.addPlayerThrownItemEntity(drop);
				cir.setReturnValue(drop);
				cir.cancel();
			}
		}
	}

}
