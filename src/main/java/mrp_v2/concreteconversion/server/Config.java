package mrp_v2.concreteconversion.server;

import mrp_v2.concreteconversion.ConcreteConversion;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;

@Mod.EventBusSubscriber(modid = ConcreteConversion.ID, bus = Mod.EventBusSubscriber.Bus.MOD) public class Config
{
    public static final Config SERVER;
    public static final ForgeConfigSpec serverSpec;
    private static final String TRANSLATION_KEY = ConcreteConversion.ID + ".config.gui.";

    static
    {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    private final ForgeConfigSpec.BooleanValue onlyPlayerThrownItems;
    private final ForgeConfigSpec.IntValue conversionCheckDelay;
    private final ForgeConfigSpec.IntValue conversionDelay;
    private final ForgeConfigSpec.BooleanValue disableVanillaConversionMechanic;
    private boolean opti;
    private int ccd;
    private int cd;
    private boolean dvcm;

    Config(final ForgeConfigSpec.Builder builder)
    {
        builder.comment(" Server configuration settings").push("server");
        final String opti = "onlyPlayerThrownItems";
        onlyPlayerThrownItems =
                builder.comment(" If true, only items thrown by the player will be converted into concrete.",
                        " If false, all concrete powder items will be converted,",
                        " including items dispensed by droppers,", " items that drop when a chest is destroyed, etc.")
                        .translation(TRANSLATION_KEY + opti).define(opti, true);
        final String ccd = "conversionCheckDelay";
        conversionCheckDelay = builder.comment(" Every this many game ticks,",
                " the mod will check whether the currently tracked concrete powder items are in water,",
                " and if they are, will convert them.", " The default is 20 ticks, or 1 second.")
                .translation(TRANSLATION_KEY + ccd).defineInRange(ccd, 20, 1, 200);
        final String cd = "conversionDelay";
        conversionDelay = builder.comment(" After this many game ticks spent in water,", " the item will be converted.")
                .translation(TRANSLATION_KEY + cd).defineInRange(cd, 0, 0, 6000);
        final String dvcm = "disableVanillaConversionMechanic";
        disableVanillaConversionMechanic =
                builder.comment(" Whether to disable to vanilla concrete conversion mechanic.",
                        " If this is true, concrete powder in block form will not solidify upon contact with water.",
                        " The only way to convert concrete powder to concrete would be to throw concrete powder items into water.")
                        .translation(TRANSLATION_KEY + dvcm).define(dvcm, false);
        builder.pop();
    }

    @SubscribeEvent public static void onLoad(final ModConfig.Loading configEvent)
    {
        LogManager.getLogger()
                .debug("Loaded Concrete Conversion config file {}", configEvent.getConfig().getFileName());
        SERVER.updateValues();
    }

    private void updateValues()
    {
        this.opti = this.onlyPlayerThrownItems.get();
        this.ccd = this.conversionCheckDelay.get();
        this.cd = this.conversionDelay.get();
        this.dvcm = this.disableVanillaConversionMechanic.get();
    }

    @SubscribeEvent public static void onFileChange(final ModConfig.Reloading configEvent)
    {
        LogManager.getLogger().debug("Concrete Conversion config just got changed on the file system!");
        SERVER.updateValues();
    }

    public boolean getOnlyPlayerThrownItems()
    {
        return this.opti;
    }

    public int getConversionCheckDelay()
    {
        return this.ccd;
    }

    public int getConversionDelay()
    {
        return this.cd;
    }

    public boolean getDisableVanillaConversionMechanic()
    {
        return this.dvcm;
    }
}
