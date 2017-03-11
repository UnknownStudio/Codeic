package me.robertyang.codeic.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandGroup
{
	/**
	 * Key: command regex
	 * Data: command name
	 */
	Map<String,CommandType> command = new HashMap<String, CommandType>();
	public CommandGroup()
	{
		initCommand();
	}
	
	void initCommand()
	{
		command.put(" *\\S+:(\\S+,?)+", CommandType.useCommand);
	}
	
	class Packet
	{
		public CommandType type;
		public Matcher matcher;
		public Packet(CommandType type,Matcher matcher)
		{
			this.type = type;
			this.matcher = matcher;
		}
	}
	
	public enum CommandType
	{
		useCommand
	}
	
	/**
	 * Get packet which contain command name and matcher
	 * @param instruction
	 * @return
	 */
	public Packet getPacket(String instruction)
	{
		for(String cmd :command.keySet())
		{
			Matcher matcher = Pattern.compile(cmd).matcher(instruction);
			if(matcher.find())
				return new Packet(command.get(cmd), matcher);
		}
		return null;
	}
}
