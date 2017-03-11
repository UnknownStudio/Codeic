package me.robertyang.codeic.block;


import me.robertyang.codeic.tileentity.TileEntityExecuteBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPortBlock extends Block{
	public static final PropertyBool ISPOWERED = PropertyBool.create("ispowered");
	public int power;
	
	public BlockPortBlock() {
		super(Material.GROUND);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ISPOWERED, false));
	}
	
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ISPOWERED);
    }
	
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        Boolean isPowered = Boolean.valueOf((meta & 1) != 0);
        power = meta;
        return this.getDefaultState().withProperty(BlockPortBlock.ISPOWERED, isPowered);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        //int isPowered = state.getValue(ISPOWERED).booleanValue() ? 1 : 0;
        return power;
    }
	
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(ISPOWERED, false));
        updateState(worldIn, pos);
    }

    /**
     * Called when a tile entity on a side of this block changes is created or is destroyed.
     * @param world The world
     * @param pos Block position in world
     * @param neighbor Block position of neighbor
     */
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos){
    	updateState(worldIn, pos);
    }
    
    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return power>0;
    }
    
    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return power;
    }
    
    public void updateState(World worldIn,BlockPos pos)
    {
		int power_temp = 0;
    	for (EnumFacing facing : EnumFacing.values()) {
			if(worldIn.getBlockState(pos.offset(facing)).getBlock() instanceof BlockExecuteBlock)
			{
				TileEntityExecuteBlock tileEntity = (TileEntityExecuteBlock)worldIn.getTileEntity(pos.offset(facing));
				int p = tileEntity.outputPower[facing.getOpposite().getIndex()];
				if(p>0){
					power_temp = p;
				}
			}
		}
    	worldIn.setBlockState(pos, this.getDefaultState().withProperty(ISPOWERED, power_temp>0?true:false));
    	power=power_temp;
    }
}
