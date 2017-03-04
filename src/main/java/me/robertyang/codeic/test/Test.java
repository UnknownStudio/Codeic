package me.robertyang.codeic.test;

import java.util.ArrayList;

public class Test {
	public static void main(String args[])
	{
		System.out.println("Test Start...");
		//System.out.println("aaa".substring(0, 3));
		ArrayList<String> test = new ArrayList<String>();
		test.add("sssssss");
		//test.add("111");
		System.out.println(stringArrayToString(test));
	}
    public static String stringArrayToString(ArrayList<String> value)
    {
    	StringBuilder resultTemp = new StringBuilder();
    	for(int i = 0;i<value.size();i++)
    	{
    		resultTemp.append(value.get(i));
    		resultTemp.append("a");
    	}
    	String result = resultTemp.toString();
    	result = result.substring(0,result.length()==0?0:result.length()-1);
    	return result;
    }
}
