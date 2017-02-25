package me.robertyang.codeic;

import me.robertyang.codeic.block.BlockExecuteBlock;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

public class BlockLoader {
 
	public static Block execute_block = new BlockExecuteBlock()
			.setRegistryName("execute_block")
			.setUnlocalizedName("executeBlock")
			.setCreativeTab(CreativeTabs.REDSTONE);
	
	public static void loadBlock()
	{
		Global.registerBlock(execute_block);
	}
	
	public static void loadRender()
	{
		Global.registerRender(execute_block);
	}
}
