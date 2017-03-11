package me.robertyang.codeic.test;

import me.robertyang.codeic.logic.Runner;

public class Test {
	public static void main(String args[])
	{
		System.out.println("Test Start...");
		Runner runner = new Runner();
		runner.executeCommand(null, "set:north,15");
		System.out.println("End");
	}
}
