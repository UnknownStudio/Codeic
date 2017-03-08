package me.robertyang.codeic.block;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPortBlock extends Block{
	public static final PropertyBool ISPOWERED = PropertyBool.create("ispowered");
	
	
	public BlockPortBlock() {
		super(Material.GROUND);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ISPOWERED, false));
	}
	
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        Boolean isPowered = Boolean.valueOf((meta & 1) != 0);
        return this.getDefaultState().withProperty(BlockPortBlock.ISPOWERED, isPowered);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int isPowered = state.getValue(ISPOWERED).booleanValue() ? 1 : 0;
        return isPowered;
    }
	
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(ISPOWERED, false));
    }

}
