package me.robertyang.codeic.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValuePool {
	Map<String,Object> valueGroup = new HashMap<String, Object>();
	Map<String,ValueType> valueTypeGroup = new HashMap<String, ValueType>();
	
	public enum ValueType
	{
		t_double("[+]?[-]?\\d+(.)?(\\d+)?"),
		t_string("\"+\\S*\"+"),
		t_boolean(" *(true|false) *");
		
		private String regex;
		public String getRegex()
		{
			return regex;
		}
		
		private ValueType(String regex)
		{
			this.regex = regex;
		}
		
		public static ValueType judgeType(String value)
		{
			for (ValueType valueType : ValueType.values()) {
				Matcher matcher = Pattern.compile(valueType.getRegex()).matcher(value);
				if(matcher.find())
					return valueType;
			}
			return null;//If it is a variable,then return null.
		}
	}
	
	/**
	 * Get value from pool.If it doesn't appear in valueGroup,then try to find it in built-in values automatically. 
	 * @param name
	 * @return
	 */
	public Object getValue(String name)
	{
		if(valueGroup.containsKey(name))
			return valueGroup.get(name);
		else
			return Runner.builtInData.getValue(name);			
	}
	
	/**
	 * Set value.If it fail,it is also a success operate.
	 * @param name
	 * @param value
	 */
	public void setValue(String name,Object value)
	{
		if(valueGroup.containsKey(name))
		{
			
		}
	}
	
	public void addValue(ValueType type,String name,Object value)
	{
		valueGroup.put(name, type);
		valueTypeGroup.put(name, type);
	}
	public void editValue(String name,Object value)
	{
	
	}
	public void removeValue(String name)
	{
		valueGroup.remove(name);
		valueTypeGroup.remove(name);
	}
}
