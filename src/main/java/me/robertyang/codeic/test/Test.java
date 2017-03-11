package me.robertyang.codeic.test;

import java.util.Arrays;

import me.robertyang.codeic.Debug;
import me.robertyang.codeic.logic.Runner;
import me.robertyang.codeic.tileentity.TileEntityExecuteBlock;

public class Test {
	static Object aObject = new Object();
	static Object bObject = new Object();
	public static void main(String args[])
	{
		System.out.println("Test Start...");
		Runner runner = new Runner();
		
		runner.executeInstruction(null, "set:north,15");
		System.out.println("End");
	}
	enum Type
	{
		A,
		B
	}
}
