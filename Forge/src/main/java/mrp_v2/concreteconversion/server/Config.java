package mrp_v2.concreteconversion.server;

import mrp_v2.concreteconversion.ConcreteConversionCommon;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {

    public static final Config SERVER;
    public static final ForgeConfigSpec serverSpec;

    protected final ForgeConfigSpec.BooleanValue onlyPlayerThrownItems;
    protected final ForgeConfigSpec.IntValue conversionCheckDelay;
    protected final ForgeConfigSpec.IntValue conversionDelay;
    protected final ForgeConfigSpec.BooleanValue disableVanillaConversionMechanic;
    protected final ForgeConfigSpec.BooleanValue convertMud;
    protected boolean opti;
    protected int ccd;
    protected int cd;
    protected boolean dvcm;
    protected boolean cm;

    private Config(final ForgeConfigSpec.Builder builder) {
        builder.comment(" Server configuration settings").push("server");
        onlyPlayerThrownItems = builder.comment(ConcreteConversionCommon.CONFIG.OPTI_COMMENT).translation(ConcreteConversionCommon.CONFIG.getTranslationKey(ConcreteConversionCommon.CONFIG.OPTI)).define(ConcreteConversionCommon.CONFIG.OPTI, true);
        conversionCheckDelay = builder.comment(ConcreteConversionCommon.CONFIG.CCD_COMMENT).translation(ConcreteConversionCommon.CONFIG.getTranslationKey(ConcreteConversionCommon.CONFIG.CCD)).defineInRange(ConcreteConversionCommon.CONFIG.CCD, 20, 1, 200);
        conversionDelay = builder.comment(ConcreteConversionCommon.CONFIG.CD_COMMENT).translation(ConcreteConversionCommon.CONFIG.getTranslationKey(ConcreteConversionCommon.CONFIG.CD)).defineInRange(ConcreteConversionCommon.CONFIG.CD, 0, 0, 6000);
        disableVanillaConversionMechanic = builder.comment(ConcreteConversionCommon.CONFIG.DVCM_COMMENT).translation(ConcreteConversionCommon.CONFIG.getTranslationKey(ConcreteConversionCommon.CONFIG.DVCM)).define(ConcreteConversionCommon.CONFIG.DVCM, false);
        convertMud = builder.comment(ConcreteConversionCommon.CONFIG.CM_COMMENT).translation(ConcreteConversionCommon.CONFIG.getTranslationKey(ConcreteConversionCommon.CONFIG.CM)).define(ConcreteConversionCommon.CONFIG.CM, false);
        builder.pop();
    }

    static {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    }
}
