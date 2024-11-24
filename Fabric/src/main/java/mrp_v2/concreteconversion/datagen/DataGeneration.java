package mrp_v2.concreteconversion.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DataGeneration implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(RecipeProvider::new);
        pack.addProvider(LanguageProvider::new);
    }

    public static class RecipeProvider extends FabricRecipeProvider {

        public RecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output ,registriesFuture);
        }

        @Override
        public void buildRecipes(@NotNull RecipeOutput exporter) {
            ConcreteRecipes.generatePowderFromConcreteRecipes(exporter, RecipeProvider::has);
        }
    }

    public static class LanguageProvider extends FabricLanguageProvider {

        public LanguageProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, "en_us", registriesFuture);
        }

        @Override
        public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder builder) {
            ConcreteLanguage.english(builder::add);
        }
    }
}
