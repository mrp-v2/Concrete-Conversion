package mrp_v2.concreteconversion.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ConstantConcretePowderBlock extends FallingBlock
{
    public final Block solidified;

    public ConstantConcretePowderBlock(Block solidified, Block base)
    {
        super(Properties.from(base));
        this.solidified = solidified;
        this.setRegistryName(base.getRegistryName());
    }

    @Override public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return super.getStateForPlacement(context);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
            BlockPos currentPos, BlockPos facingPos)
    {
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void onEndFalling(World worldIn, BlockPos pos, BlockState fallingState, BlockState hitState,
            FallingBlockEntity fallingBlock)
    {
        super.onEndFalling(worldIn, pos, fallingState, hitState, fallingBlock);
    }

    @OnlyIn(Dist.CLIENT) public int getDustColor(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return state.getMaterialColor(reader, pos).colorValue;
    }
}
