package me.robertyang.codeic.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contain built-in data like instructions regex and built-in value
 * @author Robert
 *
 */
public class BuiltInData
{
	public enum BuiltInValue
	{
		north
	}
	/**
	 * Key: command regex
	 * Data: command name
	 */
	//Map<String,InstructionType> instructions = new HashMap<String, InstructionType>();
	
	Map<String,BuiltInValue> values = new HashMap<String, BuiltInData.BuiltInValue>();
	
	public BuiltInData()
	{
		//initInstructions();
		initValue();
	}
	
//	/**
//	 * Register new instruction in this.
//	 */
//	void initInstructions()
//	{
//		instructions.put(" *\\S+:(\\S+,?)+", InstructionType.useCommand);
//	}
	
	void initValue()
	{
		values.put("north", BuiltInValue.north);
	}
	
	class Packet
	{
		public InstructionType type;
		public Matcher matcher;
		public Packet(InstructionType type,Matcher matcher)
		{
			this.type = type;
			this.matcher = matcher;
		}
	}
	
	public enum InstructionType
	{
		useCommand(" *\\S+:(\\S+,?)+"),
		operateVariable("");
		
		private String regex;
		public String getRegex()
		{
			return regex;
		}
		
		private InstructionType(String regex)
		{
			
		}
	}
	
	/**
	 * Get packet which contain command name and matcher
	 * @param instruction
	 * @return
	 */
	public Packet getPacket(String instruction)
	{
		for (InstructionType instructionType : InstructionType.values()) {
			Matcher matcher = Pattern.compile(instructionType.getRegex()).matcher(instruction);
			if(matcher.find())
				return new Packet(instructionType, matcher);
		}
		return null;
	}
	public Object getValue(String name)
	{
		if(values.containsKey(name))
			return values.get(name);
		return null;
	}
}
