package mrp_v2.concreteconversion.util;

import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Maps;

import mrp_v2.concreteconversion.ConcreteConversion;
import mrp_v2.concreteconversion.server.Config;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = ConcreteConversion.ID) public class EventHandler
{
    private static final Map<ItemEntity, Integer> entities = Maps.newHashMap();
    private static int lastCheck = 0;

    @SubscribeEvent public static void itemTossEvent(ItemTossEvent event)
    {
        if (!event.getEntity().getCommandSenderWorld().isClientSide())
        {
            addPlayerThrownItemEntity(event.getEntityItem());
        }
    }

    private static void addPlayerThrownItemEntity(ItemEntity itemEntity)
    {
        if (Config.SERVER.getOnlyPlayerThrownItems() && isConcretePowder(itemEntity))
        {
            entities.putIfAbsent(itemEntity, 0);
        }
    }

    private static boolean isConcretePowder(ItemEntity itemEntity)
    {
        return (Block.byItem(itemEntity.getItem().getItem()) instanceof ConcretePowderBlock);
    }

    @SubscribeEvent public static void worldTickEvent(TickEvent.WorldTickEvent event)
    {
        itemEntityCheck((ServerLevel) event.world);
    }

    private static void itemEntityCheck(ServerLevel world)
    {
        if ((Config.SERVER.getConversionCheckDelay() <= ++lastCheck))
        {
            lastCheck = 0;
            if (!Config.SERVER.getOnlyPlayerThrownItems())
            {
                for (Entity itemEntity : world.getEntities(EntityType.ITEM, (entity) -> true))
                {
                    entities.putIfAbsent((ItemEntity) itemEntity, 0);
                }
            }
            Iterator<ItemEntity> iterator = entities.keySet().iterator();
            while (iterator.hasNext())
            {
                ItemEntity itemEntity = iterator.next();
                if (!itemEntity.isAlive())
                {
                    iterator.remove();
                    continue;
                }
                if (itemEntity.isInWater())
                {
                    if (entities.get(itemEntity) >= Config.SERVER.getConversionDelay())
                    {
                        convertEntity(itemEntity);
                        iterator.remove();
                    } else
                    {
                        entities.replace(itemEntity, entities.get(itemEntity) + 1);
                    }
                } else
                {
                    entities.replace(itemEntity, 0);
                }
            }
        }
    }

    private static void convertEntity(ItemEntity itemEntity)
    {
        ItemStack stack = itemEntity.getItem();
        Block block = getPowderConverted(Block.byItem(stack.getItem()));
        if (block != null)
        {
            itemEntity.setItem(new ItemStack(block, stack.getCount()));
        }
    }

    private static Block getPowderConverted(Block block)
    {
        ConcretePowderBlock concretePowderBlock =
                block instanceof ConcretePowderBlock ? (ConcretePowderBlock) block : null;
        if (concretePowderBlock == null)
        {
            return null;
        }
        return concretePowderBlock.concrete.getBlock();
    }
}
