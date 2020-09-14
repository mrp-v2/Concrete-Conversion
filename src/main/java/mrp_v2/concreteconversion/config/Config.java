package mrp_v2.concreteconversion.config;

import mrp_v2.concreteconversion.ConcreteConversion;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;

public class Config
{
    public static final ForgeConfigSpec serverSpec;
    public static final Server SERVER;
    private static final String TRANSLATION_KEY = ConcreteConversion.ID + ".config.gui.";

    static
    {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    @SubscribeEvent public static void onLoad(final ModConfig.Loading configEvent)
    {
        LogManager.getLogger()
                .debug("Loaded Concrete Conversion config file {}", configEvent.getConfig().getFileName());
    }

    @SubscribeEvent public static void onFileChange(final ModConfig.Reloading configEvent)
    {
        LogManager.getLogger().debug("Concrete Conversion config just got changed on the file system!");
    }

    public static class Server
    {
        public final BooleanValue onlyPlayerThrownItems;
        public final IntValue conversionCheckDelay;
        public final IntValue conversionDelay;

        Server(final ForgeConfigSpec.Builder builder)
        {
            builder.comment(" Server configuration settings").push("server");
            onlyPlayerThrownItems =
                    builder.comment(" If true, only items thrown by the player will be converted into concrete.",
                            " If false, all concrete powder items will be converted,",
                            " including items dispensed by droppers,",
                            " items that drop when a chest is destroyed, etc.")
                            .translation(TRANSLATION_KEY + "onlyPlayerThrownItems")
                            .define("onlyPlayerThrownItems", true);
            conversionCheckDelay = builder.comment(" Every this many game ticks,",
                    " the mod will check whether the currently tracked concrete powder items are in water,",
                    " and if they are, will convert them.", " The default is 20 ticks, or 1 second.")
                    .translation(TRANSLATION_KEY + "conversionCheckDelay")
                    .defineInRange("conversionCheckDelay", 20, 1, 200);
            conversionDelay =
                    builder.comment(" After this many game ticks spent in water,", " the item will be converted.")
                            .translation(TRANSLATION_KEY + "conversionDelay")
                            .defineInRange("conversionDelay", 0, 0, 6000);
            builder.pop();
        }
    }
}
