package com.mrp_v2.concreteconversion.event.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mrp_v2.concreteconversion.config.ConfigOptions;

import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class ConcretePowderConverter {

	private static HashMap<ItemEntity, Integer> entities = new HashMap<ItemEntity, Integer>();
	private static List<ItemEntity> removes = new ArrayList<ItemEntity>();

	private static int lastCheck = 0;

	public static void itemChecker() {

		ServerTickCallback.EVENT.register((listener) -> {
			lastCheck++;
			if ((ConfigOptions.conversionCheckDelay <= lastCheck)) {
				lastCheck = 0;
				try {
					for(ServerWorld w : listener.getWorlds()) {
						for(Entity e : w.getEntities(null, (entity)-> entity instanceof ItemEntity)) {
							try {
								entities.putIfAbsent((ItemEntity) e, 0);
							} catch (Exception localException) {
							}
						}
					}
				} catch (java.lang.NullPointerException e) {
				}
				for (int i = 0; i < entities.size(); i++) {
					ItemEntity e = (ItemEntity) entities.keySet().toArray()[i];
					if (e.isSubmergedInWater()) {
						if (entities.get(e) >= ConfigOptions.conversionDelay) {
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

	private static boolean tryConvertEntity(ItemEntity itemEntity) {
		ItemStack item = itemEntity.getStack();
		if (isConcretePowder(item)) {
			itemEntity.setStack(tryConvertStack(item));
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
}
