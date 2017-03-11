package me.robertyang.codeic.logic;

import me.robertyang.codeic.Debug;
import me.robertyang.codeic.logic.CommandGroup.Packet;
import me.robertyang.codeic.tileentity.TileEntityExecuteBlock;

public class Runner {
	
	static CommandGroup defaultCommandGroup = new CommandGroup();
	
	public Runner()
	{

	}
	
	public boolean executeCommand(TileEntityExecuteBlock teb,String command)
	{
		Packet packet = defaultCommandGroup.getPacket(command);
		switch (packet.type) {
		case useCommand:
			command_set(teb, packet);
			break;

		default:
			break;
		}
		return false;
	}
	
	public boolean command_set(TileEntityExecuteBlock teb,Packet packet)
	{
		String src = packet.matcher.group();
		Debug.log(src);
		return false;
	}
}