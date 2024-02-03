package mrp_v2.concreteconversion;

import mrp_v2.concreteconversion.event.ConcreteEvents;
import mrp_v2.concreteconversion.server.ConcreteConfigCommon;
import mrp_v2.concreteconversion.util.EventHandlerCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

public class ConcreteConversionCommon {

    public static final String ID = "concreteconversion";
    public static final Logger LOG = LoggerFactory.getLogger(ID);

    public static final ConcreteConfigCommon CONFIG = load(ConcreteConfigCommon.class);

    public static void init() {
        ConcreteEvents.ITEM_TOSS.register((EventHandlerCommon::itemTossEvent));
        ConcreteEvents.SERVER_LEVEL_TICK.register(EventHandlerCommon::worldTickEvent);
    }

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
