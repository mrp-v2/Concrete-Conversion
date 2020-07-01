package com.mrp_v2.concreteconversion.event.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mrp_v2.concreteconversion.ConcreteConversion;
import com.mrp_v2.concreteconversion.config.ConcreteConversionConfig;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = ConcreteConversion.MODID)
public class ConcretePowderConverter {

	private static HashMap<ItemEntity, Integer> entities = new HashMap<ItemEntity, Integer>();
	private static List<ItemEntity> removes = new ArrayList<ItemEntity>();

	@SubscribeEvent
	public static void itemTossEvent(ItemTossEvent event) {
		if (isServer(event) && ConcreteConversionConfig.SERVER.onlyPlayerThrownItems.get()
				&& isConcretePowder(event.getEntityItem().getItem())) {
			entities.putIfAbsent(event.getEntityItem(), 0);
		}
	}

	@SubscribeEvent
	public static void itemPickedUp(PlayerEvent.ItemPickupEvent event) {
		if (isServer(event) && entities.containsKey(event.getOriginalEntity())) {
			removes.add(event.getOriginalEntity());
		}
	}

	@SubscribeEvent
	public static void itemExpired(ItemExpireEvent event) {
		if (isServer(event) && entities.containsKey(event.getEntityItem())) {
			removes.add(event.getEntityItem());
		}
	}

	private static int lastCheck = 0;

	@SubscribeEvent
	public static void itemChecker(TickEvent.ServerTickEvent event) {
		if (isServer(event)) {
			lastCheck++;
			if ((ConcreteConversionConfig.SERVER.conversionCheckDelay.get() <= lastCheck)) {
				lastCheck = 0;
				if (!ConcreteConversionConfig.SERVER.onlyPlayerThrownItems.get()) {
					try {
						for (Entity e : Minecraft.getInstance().world.getAllEntities()) {
							try {
								entities.putIfAbsent((ItemEntity) e, 0);
							} catch (Exception localException) {
							}
						}
					} catch (java.lang.NullPointerException e) {
					}
				}
				for (int i = 0; i < entities.size(); i++) {
					ItemEntity e = (ItemEntity) entities.keySet().toArray()[i];
					if (e.isInWater()) {
						if (entities.get(e) >= ConcreteConversionConfig.SERVER.conversionDelay.get()) {
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
		}
	}

	private static boolean tryConvertEntity(ItemEntity itemEntity) {
		ItemStack item = itemEntity.getItem();
		if (isConcretePowder(item)) {
			itemEntity.setItem(tryConvertStack(item));
			return true;
		}
		return false;
	}

	private static ItemStack tryConvertStack(ItemStack stack) {
		if (isConcretePowder(stack)) {
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.BLACK_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.BLACK_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.BLUE_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.BLUE_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.BROWN_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.BROWN_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.CYAN_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.CYAN_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.GRAY_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.GRAY_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.GREEN_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.GREEN_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.LIGHT_BLUE_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.LIGHT_BLUE_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.LIGHT_GRAY_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.LIGHT_GRAY_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.LIME_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.LIME_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.MAGENTA_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.MAGENTA_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.ORANGE_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.ORANGE_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.PINK_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.PINK_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.PURPLE_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.PURPLE_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.RED_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.RED_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.WHITE_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.WHITE_CONCRETE, stack.getCount());
			}
			if (Block.getBlockFromItem(stack.getItem()) == Blocks.YELLOW_CONCRETE_POWDER) {
				stack = new ItemStack(Blocks.YELLOW_CONCRETE, stack.getCount());
			}
		}
		return stack;
	}

	private static boolean isConcretePowder(ItemStack stack) {
		return (Block.getBlockFromItem(stack.getItem()) instanceof ConcretePowderBlock);
	}

	private static boolean isServer(EntityEvent event) {
		return !event.getEntity().getEntityWorld().isRemote();
	}

	private static boolean isServer(TickEvent event) {
		return event.side == LogicalSide.SERVER;
	}
}
