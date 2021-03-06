package me.robertyang.codeic.block;

import me.robertyang.codeic.Codeic;
import me.robertyang.codeic.GuiLoader;
import me.robertyang.codeic.tileentity.TileEntityExecuteBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Execute Block
 * @author Robert
 *
 */
public class BlockExecuteBlock extends Block implements ITileEntityProvider {

	public BlockExecuteBlock() {
		super(Material.GROUND);
	}

    /**
     * Called when the block is right clicked by a player.
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if(worldIn.isRemote)
    	{
    		playerIn.openGui(Codeic.instance, GuiLoader.Gui_ExecuteBlock, worldIn, pos.getX(), pos.getY(), pos.getZ());
    	}
        return true; 
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityExecuteBlock();
    }
    
    
    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
    	for (EnumFacing enumfacing : EnumFacing.values())
    	{
    		IBlockState neighborState = worldIn.getBlockState(pos.offset(enumfacing));
    		if(neighborState.getWeakPower(worldIn, pos, enumfacing)>0)
    		{
    			((TileEntityExecuteBlock)worldIn.getTileEntity(pos)).isPowered = true;
    			Codeic.logger.info("POWERED");
    		}
    	}
    }
    
    public void notifyUpdateState()
    {
    	
    }
}
