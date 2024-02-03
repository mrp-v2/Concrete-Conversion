package mrp_v2.concreteconversion.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.RecipeOutput;
import org.jetbrains.annotations.NotNull;

public class DataGeneration implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(RecipeProvider::new);
        pack.addProvider(LanguageProvider::new);
    }

    public static class RecipeProvider extends FabricRecipeProvider {

        public RecipeProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void buildRecipes(@NotNull RecipeOutput exporter) {
            ConcreteRecipes.generatePowderFromConcreteRecipes(exporter, RecipeProvider::has);
        }
    }

    public static class LanguageProvider extends FabricLanguageProvider {

        public LanguageProvider(FabricDataOutput output) {
            super(output, "en_us");
        }

        @Override
        public void generateTranslations(TranslationBuilder builder) {
            ConcreteLanguage.english(builder::add);
        }
    }
}
