package mrp_v2.concreteconversion;

import mrp_v2.concreteconversion.server.Config;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;

@Mod(value = ConcreteConversionCommon.ID)
@Mod.EventBusSubscriber(modid = ConcreteConversionCommon.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConcreteConversion {

    public ConcreteConversion() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.serverSpec);
        ConcreteConversionCommon.init();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading event) {
        ConcreteConversionCommon.LOG.debug("Loaded Concrete Conversion config file {}", event.getConfig().getFileName());
        ConcreteConversionCommon.CONFIG.updateValues();
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading event) {
        ConcreteConversionCommon.LOG.debug("Concrete Conversion config just got changed on the file system!");
        ConcreteConversionCommon.CONFIG.updateValues();
    }
}
