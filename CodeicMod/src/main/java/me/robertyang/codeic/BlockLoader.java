package me.robertyang.codeic;

import me.robertyang.codeic.block.BlockExecuteBlock;
import me.robertyang.codeic.block.BlockPortBlock;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

public class BlockLoader {
 
	public static Block execute_block = new BlockExecuteBlock()
			.setRegistryName("execute_block")
			.setUnlocalizedName("executeBlock")
			.setCreativeTab(CreativeTabs.REDSTONE);
	public static Block port_block = new BlockPortBlock()
			.setRegistryName("port_block")
			.setUnlocalizedName("portBlock")
			.setCreativeTab(CreativeTabs.REDSTONE);
	
	public void loadBlock()
	{
		Global.registerBlock(execute_block);
		Global.registerBlock(port_block);
	}
	
	public void loadRender()
	{
		Global.registerRender(execute_block);
		Global.registerRender(port_block,0,"port_block_off");
		Global.registerRender(port_block,1,"port_block_on");
	}
}
