package com.mrp_v2.concreteconversion.util;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mrp_v2.concreteconversion.ConcreteConversion;
import com.mrp_v2.concreteconversion.config.Config;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = ConcreteConversion.MODID)
public class EventHandler {

	private static Map<ItemEntity, Integer> entities = Maps.newHashMap();

	@SubscribeEvent
	public static void itemTossEvent(ItemTossEvent event) {
		if (isServer(event)) {
			addPlayerThrownItemEntity(event.getEntityItem());
		}
	}

	private static void addPlayerThrownItemEntity(ItemEntity itemEntity) {
		if (Config.SERVER.onlyPlayerThrownItems.get() && isConcretePowder(itemEntity)) {
			entities.putIfAbsent(itemEntity, 0);
		}
	}

	private static int lastCheck = 0;

	@SubscribeEvent
	public static void worldTickEvent(TickEvent.WorldTickEvent event) {
		if (isServer(event) && event.world instanceof ServerWorld) {
			itemEntityCheck((ServerWorld) event.world);
		}
	}

	private static void itemEntityCheck(ServerWorld world) {
		lastCheck++;
		if ((Config.SERVER.conversionCheckDelay.get() <= lastCheck)) {
			lastCheck = 0;
			if (!Config.SERVER.onlyPlayerThrownItems.get()) {
				for (Entity itemEntity : world.getEntities(EntityType.ITEM, (entity) -> true)) {
					entities.putIfAbsent((ItemEntity) itemEntity, 0);
				}
			}
			Set<ItemEntity> removes = Sets.newHashSet();
			for (ItemEntity itemEntity : entities.keySet()) {
				if (!itemEntity.isAlive()) {
					removes.add(itemEntity);
					continue;
				}
				if (itemEntity.isInWater()) {
					if (entities.get(itemEntity) >= Config.SERVER.conversionDelay.get()) {
						convertEntity(itemEntity);
						removes.add(itemEntity);
					} else {
						entities.replace(itemEntity, entities.get(itemEntity) + 1);
					}
				} else {
					entities.replace(itemEntity, 0);
				}
			}
			for (ItemEntity e : removes) {
				entities.remove(e);
			}
			removes.clear();
		}
	}

	private static void convertEntity(ItemEntity itemEntity) {
		itemEntity.setItem(tryConvertStack(itemEntity.getItem()));
	}

	private static ItemStack tryConvertStack(ItemStack stack) {
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
		return stack;
	}

	private static boolean isConcretePowder(ItemEntity itemEntity) {
		return isConcretePowder(itemEntity.getItem());
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
