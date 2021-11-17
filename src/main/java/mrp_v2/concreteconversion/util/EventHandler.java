package mrp_v2.concreteconversion.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mrp_v2.concreteconversion.mixin.ConcretePowderBlockAccessor;
import mrp_v2.concreteconversion.server.Config;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;

public class EventHandler {

	private static HashMap<ItemEntity, Integer> entities = new HashMap<ItemEntity, Integer>();
	private static List<ItemEntity> removes = new ArrayList<ItemEntity>();

	private static int lastCheck = 0;

	public static void addPlayerThrownItemEntity(ItemEntity itemEntity)
	{
		if (Config.onlyPlayerThrownItems && itemEntity != null && isConcretePowder(itemEntity))
		{
			entities.putIfAbsent(itemEntity, 0);
		}
	}

	private static boolean isConcretePowder(ItemEntity itemEntity)
	{
		return (Block.getBlockFromItem(itemEntity.getStack().getItem()) instanceof ConcretePowderBlock);
	}

	public static void itemChecker() {

		ServerTickEvents.END_SERVER_TICK.register((listener) -> {
			lastCheck++;
			if ((Config.conversionCheckDelay <= ++lastCheck)) {
				lastCheck = 0;
				if(!Config.onlyPlayerThrownItems) 
				{
					try {
						for(ServerWorld w : listener.getWorlds()) {
							w.getProfiler().visit("getEntities");
							for(Entity e : w.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), (entity)-> entity instanceof ItemEntity)) {
								try {
									entities.putIfAbsent((ItemEntity) e, 0);
								} catch (Exception localException) {
								}
							}
						}
					} catch (java.lang.NullPointerException e) {
					}
				}
				for (int i = 0; i < entities.size(); i++) {
					ItemEntity e = (ItemEntity) entities.keySet().toArray()[i];
					if (e.isSubmergedInWater()) {
						if (entities.get(e) >= Config.conversionDelay) {
							tryConvertEntity(e);
							removes.add(e);
						} else {
							entities.replace(e, entities.get(e) + 1);
						}
					} else {
						entities.replace(e, 0);
					}
				}
				for (ItemEntity e : removes) {
					entities.remove(e);
				}
				removes.clear();
			}
		});
	}

	private static boolean tryConvertEntity(ItemEntity itemEntity)
	{
		ItemStack stack = itemEntity.getStack();
		Block block = getPowderConverted(Block.getBlockFromItem(stack.getItem()));
		if (block != null)
		{
			itemEntity.setStack(new ItemStack(block, stack.getCount()));
			return true;
		}
		return false;
	}

	private static Block getPowderConverted(Block block)
	{
		ConcretePowderBlock concretePowderBlock =
				block instanceof ConcretePowderBlock ? (ConcretePowderBlock) block : null;
		if (concretePowderBlock == null)
		{
			return null;
		}
		return ((ConcretePowderBlockAccessor) concretePowderBlock).getHardenedState().getBlock();
	}
}
