package me.robertyang.codeic.logic;

import java.util.HashMap;
import java.util.Map;

public class ValuePool {
	Map<String,Object> valueGroup = new HashMap<String, Object>();
	Map<String,ValueType> valueTypeGroup = new HashMap<String, ValueType>();
	
	public enum ValueType
	{
		t_double,
		t_string,
		t_boolean
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
