package me.robertyang.codeic.logic;

import me.robertyang.codeic.tileentity.TileEntityExecuteBlock;

public class Runner {
	
	TileEntityExecuteBlock block;

	static CommandGroup defaultCommandGroup = new CommandGroup();
	
	public Runner(TileEntityExecuteBlock teb)
	{
		block = teb;
	}
	
	public void executeCommand(String command)
	{
		
	}
	
}