package mrp_v2.concreteconversion.util;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import mrp_v2.concreteconversion.server.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ConcreteConversionClothConfig {
	
	public static Screen getConfigScreen(Screen parent)
	{
		ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(new TranslatableText("concreteconversion.config.gui.title"));

		builder.setSavingRunnable(() -> {
			Config.save(Config.getFile(), Config.getObject());
			Config.load(Config.getFile());
		});

		builder.setDefaultBackgroundTexture(new Identifier("minecraft:textures/block/blue_concrete.png"));
		
		ConfigCategory common = builder.getOrCreateCategory(new TranslatableText("concreteconversion.config.gui.common"));
		ConfigEntryBuilder entryBuilder = builder.entryBuilder();
		
		common.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("concreteconversion.config.gui.onlyPlayerThrownItems"), Config.onlyPlayerThrownItems).setTooltip(new TranslatableText("concreteconversion.config.gui.onlyPlayerThrownItems.tooltip")).setDefaultValue(true).setSaveConsumer(newValue->Config.onlyPlayerThrownItems = newValue).build());
		common.addEntry(entryBuilder.startIntSlider(new TranslatableText("concreteconversion.config.gui.conversionCheckDelay"), Config.conversionCheckDelay, 1, 200).setTooltip(new TranslatableText("concreteconversion.config.gui.conversionCheckDelay.tooltip")).setDefaultValue(20).setSaveConsumer(newValue->Config.conversionCheckDelay = newValue).build());
		common.addEntry(entryBuilder.startIntSlider(new TranslatableText("concreteconversion.config.gui.conversionDelay"), Config.conversionDelay, 0, 6000).setTooltip(new TranslatableText("concreteconversion.config.gui.conversionDelay.tooltip")).setDefaultValue(0).setSaveConsumer(newValue->Config.conversionDelay = newValue).build());
		common.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("concreteconversion.config.gui.disableVanillaConversionMechanic"), Config.disableVanillaConversionMechanic).setTooltip(new TranslatableText("concreteconversion.config.gui.disableVanillaConversionMechanic.tooltip")).setDefaultValue(false).setSaveConsumer(newValue->Config.disableVanillaConversionMechanic = newValue).build());
		
		return builder.build();
	}
}
