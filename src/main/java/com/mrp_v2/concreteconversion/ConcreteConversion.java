package com.mrp_v2.concreteconversion;

import java.io.File;

import com.mrp_v2.concreteconversion.config.ConfigOptions;
import com.mrp_v2.concreteconversion.event.handlers.ConcretePowderConverter;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class ConcreteConversion implements ModInitializer {

	public static final String MODID = "concreteconversion";

	@Override
	public void onInitialize() {

		ConfigOptions.init(new File(FabricLoader.getInstance().getConfigDirectory(), MODID + ".json"));
		ConcretePowderConverter.itemChecker();
	}
}
