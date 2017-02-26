package me.robertyang.codeic.block;

import me.robertyang.codeic.Codeic;
import me.robertyang.codeic.GuiLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Execute Block
 * @author Robert
 *
 */
public class BlockExecuteBlock extends Block {

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
        return false; 
    }
}
