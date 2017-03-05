package me.robertyang.codeic.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.robertyang.codeic.tileentity.TileEntityExecuteBlock;

public class Runner {
	
	TileEntityExecuteBlock block;
	Map<String, String> command = new HashMap<String, String>();
	String command_Output = "Output";
	
	public Runner(TileEntityExecuteBlock teb)
	{
		block = teb;
	}
	
	public void initCommand()
	{
		command.put(" *set:(north|south|west|east|up|down),?\\d{0,}", command_Output);
	}
	
	public void interpretCommand(String instruction)
	{
		for (String cmd : command.keySet()) {
			Matcher matcher = Pattern.compile(cmd).matcher(instruction);
			if(matcher.find()){
				executeCommand(command.get(cmd),matcher);
			}
		}
	}
	
	void executeCommand(String cmdName,Matcher matcher)
	{
		if(cmdName==command_Output)
		{
			for(int i = 0;i<matcher.groupCount();i++)
			{
				command_set(matcher.group(i));
			}
		}
	}	
	
	void command_set(String src)
	{
		src = src.replace(" ","").replace("set:", "");
		if(src=="north")
		{
			
		}
	}
}
