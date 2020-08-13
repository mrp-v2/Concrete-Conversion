package mrp_v2.concreteconversion.util;

import mrp_v2.concreteconversion.ConcreteConversion;
import mrp_v2.concreteconversion.block.ConstantConcretePowderBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ConcreteConversion.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler
{
    @SubscribeEvent public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry()
             .registerAll(new ConstantConcretePowderBlock(Blocks.WHITE_CONCRETE, Blocks.WHITE_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.ORANGE_CONCRETE, Blocks.ORANGE_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.MAGENTA_CONCRETE, Blocks.MAGENTA_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.LIGHT_BLUE_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.YELLOW_CONCRETE, Blocks.YELLOW_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.LIME_CONCRETE, Blocks.LIME_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.PINK_CONCRETE, Blocks.PINK_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.GRAY_CONCRETE, Blocks.GRAY_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.LIGHT_GRAY_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.CYAN_CONCRETE, Blocks.CYAN_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.PURPLE_CONCRETE, Blocks.PURPLE_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.BLUE_CONCRETE, Blocks.BLUE_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.BROWN_CONCRETE, Blocks.BROWN_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.GREEN_CONCRETE, Blocks.GREEN_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.RED_CONCRETE, Blocks.RED_CONCRETE_POWDER),
                     new ConstantConcretePowderBlock(Blocks.BLACK_CONCRETE, Blocks.BLACK_CONCRETE_POWDER));
    }

    @SubscribeEvent public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry()
             .registerAll(makeBlockItem(Blocks.WHITE_CONCRETE_POWDER), makeBlockItem(Blocks.ORANGE_CONCRETE_POWDER),
                     makeBlockItem(Blocks.MAGENTA_CONCRETE_POWDER), makeBlockItem(Blocks.LIGHT_BLUE_CONCRETE_POWDER),
                     makeBlockItem(Blocks.YELLOW_CONCRETE_POWDER), makeBlockItem(Blocks.LIME_CONCRETE_POWDER),
                     makeBlockItem(Blocks.PINK_CONCRETE_POWDER), makeBlockItem(Blocks.GRAY_CONCRETE_POWDER),
                     makeBlockItem(Blocks.LIGHT_GRAY_CONCRETE_POWDER), makeBlockItem(Blocks.CYAN_CONCRETE_POWDER),
                     makeBlockItem(Blocks.PURPLE_CONCRETE_POWDER), makeBlockItem(Blocks.BLUE_CONCRETE_POWDER),
                     makeBlockItem(Blocks.BROWN_CONCRETE_POWDER), makeBlockItem(Blocks.GREEN_CONCRETE_POWDER),
                     makeBlockItem(Blocks.RED_CONCRETE_POWDER), makeBlockItem(Blocks.BLACK_CONCRETE_POWDER));
    }

    private static BlockItem makeBlockItem(Block block)
    {
        BlockItem item = new BlockItem(block, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
        item.setRegistryName(block.getRegistryName());
        return item;
    }
}
