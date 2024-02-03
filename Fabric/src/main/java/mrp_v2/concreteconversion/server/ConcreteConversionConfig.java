package mrp_v2.concreteconversion.server;

public class ConcreteConversionConfig implements ConcreteConfigCommon {

    @Override
    public void updateValues() {
        Config.load(Config.getFile());
    }

    @Override
    public boolean getOnlyPlayerThrownItems() {
        return Config.onlyPlayerThrownItems;
    }

    @Override
    public int getConversionCheckDelay() {
        return Config.conversionCheckDelay;
    }

    @Override
    public int getConversionDelay() {
        return Config.conversionDelay;
    }

    @Override
    public boolean getDisableVanillaConversionMechanic() {
        return Config.disableVanillaConversionMechanic;
    }

    @Override
    public boolean getConvertMud() {
        return Config.convertMud;
    }
}