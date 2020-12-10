package mrp_v2.concreteconversion.util;

import com.google.common.collect.Maps;
import mrp_v2.concreteconversion.ConcreteConversion;
import mrp_v2.concreteconversion.server.Config;
import net.minecraft.block.Block;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.Iterator;
import java.util.Map;

@EventBusSubscriber(modid = ConcreteConversion.ID) public class EventHandler
{
    private static final Map<ItemEntity, Integer> entities = Maps.newHashMap();
    private static int lastCheck = 0;

    @SubscribeEvent public static void itemTossEvent(ItemTossEvent event)
    {
        if (!event.getEntity().getEntityWorld().isRemote())
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
        return (Block.getBlockFromItem(itemEntity.getItem().getItem()) instanceof ConcretePowderBlock);
    }

    @SubscribeEvent public static void worldTickEvent(TickEvent.WorldTickEvent event)
    {
        itemEntityCheck((ServerWorld) event.world);
    }

    private static void itemEntityCheck(ServerWorld world)
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
        Block block = getPowderConverted(Block.getBlockFromItem(stack.getItem()));
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
        return concretePowderBlock.solidifiedState.getBlock();
    }
}
