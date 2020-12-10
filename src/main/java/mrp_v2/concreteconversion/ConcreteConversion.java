package mrp_v2.concreteconversion;

import mrp_v2.concreteconversion.server.Config;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(value = ConcreteConversion.ID) public class ConcreteConversion
{
    public static final String ID = "concreteconversion";

    public ConcreteConversion()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.serverSpec);
    }
}
