package mrp_v2.concreteconversion;

import java.io.File;

import mrp_v2.concreteconversion.server.Config;
import mrp_v2.concreteconversion.util.EventHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class ConcreteConversion implements ModInitializer {

	public static final String MODID = "concreteconversion";

	@Override
	public void onInitialize() {

		Config.init(new File(FabricLoader.getInstance().getConfigDirectory(), MODID + ".json"));
		EventHandler.itemChecker();
	}
}
