package mrp_v2.concreteconversion.server;

import mrp_v2.concreteconversion.ConcreteConversionCommon;

public interface ConcreteConfigCommon {

    String TRANSLATION_KEY = ConcreteConversionCommon.ID + ".config.gui.";
    String OPTI = "onlyPlayerThrownItems";
    String[] OPTI_COMMENT = {" If true, only items thrown by the player will be converted into concrete.", " If false, all concrete powder items will be converted,", " including items dispensed by droppers,", " items that drop when a chest is destroyed, etc."};
    String CCD = "conversionCheckDelay";
    String[] CCD_COMMENT = {" Every this many game ticks,", " the mod will check whether the currently tracked concrete powder items are in water,", " and if they are, will convert them.", " The default is 20 ticks, or 1 second."};
    String CD = "conversionDelay";
    String[] CD_COMMENT = {" After this many game ticks spent in water,", " the item will be converted."};
    String DVCM = "disableVanillaConversionMechanic";
    String[] DVCM_COMMENT = {" Whether to disable to vanilla concrete conversion mechanic.", " If this is true, concrete powder in block form will not solidify upon contact with water.", " The only way to convert concrete powder to concrete would be to throw concrete powder items into water."};
    String CM = "convertMud";
    String[] CM_COMMENT = {" Whether to convert dirt into mud.", " If this is true, all blocks defined in the tag convertable_to_mud will become mud upon contact with water."};

    default String getTranslationKey(String value) {
        return TRANSLATION_KEY + value;
    }

    void updateValues();

    boolean getOnlyPlayerThrownItems();

    int getConversionCheckDelay();

    int getConversionDelay();

    boolean getDisableVanillaConversionMechanic();

    boolean getConvertMud();
}
