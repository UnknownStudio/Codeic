package me.robertyang.codeic.test;

import java.util.ArrayList;
import java.util.regex.*;

public class Test {
	public static void main(String args[])
	{
		System.out.println("Test Start...");
		ArrayList<String> test = new ArrayList<String>();
		test.add("111");
		test.add("222");
		test.add(1,"000");
		for (String string : test) {
			System.out.println(string);
		}
		System.out.println("End");
	}
}
