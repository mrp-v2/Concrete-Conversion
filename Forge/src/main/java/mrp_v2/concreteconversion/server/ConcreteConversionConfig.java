package mrp_v2.concreteconversion.server;

public class ConcreteConversionConfig implements ConcreteConfigCommon {

    @Override
    public void updateValues() {
        Config.SERVER.opti = Config.SERVER.onlyPlayerThrownItems.get();
        Config.SERVER.ccd = Config.SERVER.conversionCheckDelay.get();
        Config.SERVER.cd = Config.SERVER.conversionDelay.get();
        Config.SERVER.dvcm = Config.SERVER.disableVanillaConversionMechanic.get();
        Config.SERVER.cm = Config.SERVER.convertMud.get();
    }

    @Override
    public boolean getOnlyPlayerThrownItems() {
        return Config.SERVER.opti;
    }

    @Override
    public int getConversionCheckDelay() {
        return Config.SERVER.ccd;
    }

    @Override
    public int getConversionDelay() {
        return Config.SERVER.cd;
    }

    @Override
    public boolean getDisableVanillaConversionMechanic() {
        return Config.SERVER.dvcm;
    }

    @Override
    public boolean getConvertMud() {
        return Config.SERVER.cm;
    }
}