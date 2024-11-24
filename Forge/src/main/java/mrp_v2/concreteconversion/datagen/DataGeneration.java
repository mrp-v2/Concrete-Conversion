package mrp_v2.concreteconversion.datagen;

import mrp_v2.concreteconversion.ConcreteConversionCommon;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ConcreteConversionCommon.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();

        gen.addProvider(event.includeServer(), new RecipeProvider(packOutput, event.getLookupProvider()));
        gen.addProvider(event.includeClient(), new LanguageProvider(packOutput, "en_us"));
    }

    public static class RecipeProvider extends VanillaRecipeProvider implements IConditionBuilder {

        public RecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected void buildRecipes(@NotNull RecipeOutput exporter) {
            ConcreteRecipes.generatePowderFromConcreteRecipes(exporter, RecipeProvider::has);
        }
    }

    public static class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

        public LanguageProvider(PackOutput output, String locale) {
            super(output, ConcreteConversionCommon.ID, locale);
        }

        @Override
        protected void addTranslations() {
            ConcreteLanguage.english(this::add);
        }
    }
}
