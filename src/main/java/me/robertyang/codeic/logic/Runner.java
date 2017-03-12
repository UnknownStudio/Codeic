package me.robertyang.codeic.logic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import me.robertyang.codeic.Codeic;
import me.robertyang.codeic.logic.BuiltInData.InstructionType;
import me.robertyang.codeic.logic.BuiltInData.Packet;
import me.robertyang.codeic.tileentity.TileEntityExecuteBlock;

public class Runner {
	
	public static BuiltInData builtInData = new BuiltInData();
	
	public Map<String,Integer> commandGroup = new HashMap<String,Integer>();
	public ValuePool values = new ValuePool();
	
	public Runner()
	{

	}
	
	public boolean executeInstruction(TileEntityExecuteBlock teb,String instruction)
	{
		Packet packet = builtInData.getPacket(instruction);
		switch (packet.type) {
		case useCommand:
			return command_useCommand(teb, packet);

		default:
			break;
		}
		return false;
	}
	
	public boolean command_useCommand(TileEntityExecuteBlock teb,Packet packet)
	{
		String[] src = packet.matcher.group().split(":",2);
		String commandName = src[0];
		String[] parameters = src[1].split(",");
		return BuiltIn.executeCommand(this, teb, commandName, parameters);
	}
}