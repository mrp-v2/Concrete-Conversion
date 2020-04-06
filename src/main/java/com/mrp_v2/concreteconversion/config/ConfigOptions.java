package com.mrp_v2.concreteconversion.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ConfigOptions {

	public static BooleanValue onlyPlayerThrownItems;

	public static IntValue conversionCheckDelay;

	public static IntValue conversionDelay;

	public static class Server {

		Server(final ForgeConfigSpec.Builder builder) {
			builder.comment("Server configuration settings").push("server");

			onlyPlayerThrownItems = builder
					.comment("If true, only items thrown by the player will be converted into concrete."
							+ "If false, all concrete powder items will be converted,"
							+ " including items dispensed by droppers,"
							+ " items that drop when a chest is destroyed, etc.")
					.translation("mrp_v2.concreteconversion.configgui.onlyPlayerThrownItems")
					.define("onlyPlayerThrownItems", true);

			conversionCheckDelay = builder
					.comment("Every this many game ticks,"
							+ " the mod will check wether the currently tracked concrete powder items are in water,"
							+ " and if they are, will convert them." + " The default is 20 ticks, or 1 second.")
					.translation("mrp_v2.concreteconversion.configgui.conversionCheckDelay")
					.defineInRange("conversionCheckDelay", 20, 1, 200);

			conversionDelay = builder
					.comment("After this many game ticks spent in water," + " the item will be converted.")
					.translation("mrp_v2.concreteconversion.configgui.conversionDelay")
					.defineInRange("conversionDelay", 0, 0, 6000);

			builder.pop();
		}
	}

	public static final ForgeConfigSpec serverSpec;
	public static final Server SERVER;
	static {
		final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
		serverSpec = specPair.getRight();
		SERVER = specPair.getLeft();
	}
}
