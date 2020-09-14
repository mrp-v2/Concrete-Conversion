package mrp_v2.concreteconversion.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ConstantConcretePowderBlock extends ConcretePowderBlock
{
    public ConstantConcretePowderBlock(Block solidified, Block base)
    {
        super(solidified, Properties.from(base));
        this.setRegistryName(base.getRegistryName());
    }

    @Override
    public void onEndFalling(World worldIn, BlockPos pos, BlockState fallingState, BlockState hitState,
            FallingBlockEntity fallingBlock)
    {
    }

    @Override public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState();
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
            BlockPos currentPos, BlockPos facingPos)
    {
        worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, this.getFallDelay());
        return stateIn;
    }
}
