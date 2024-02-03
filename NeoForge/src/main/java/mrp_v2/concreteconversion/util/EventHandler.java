package mrp_v2.concreteconversion.util;

import mrp_v2.concreteconversion.ConcreteConversionCommon;
import mrp_v2.concreteconversion.event.ConcreteEvents;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;

@Mod.EventBusSubscriber(modid = ConcreteConversionCommon.ID)
public class EventHandler {

    @SubscribeEvent
    public static void itemTossEvent(ItemTossEvent event) {
        if (!ConcreteEvents.ITEM_TOSS.post().handle(event.getEntity(), event.getPlayer()))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void worldTickEvent(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.SERVER)
            ConcreteEvents.SERVER_LEVEL_TICK.post().handle((ServerLevel) event.level);
    }
}
