package me.robertyang.codeic.test;

import java.util.Arrays;

import me.robertyang.codeic.Debug;
import me.robertyang.codeic.logic.Runner;
import me.robertyang.codeic.tileentity.TileEntityExecuteBlock;
import net.minecraft.util.EnumFacing;
import scala.reflect.internal.Trees.If;

public class Test {
	static Object aObject = new Object();
	static Object bObject = new Object();
	public static void main(String args[])
	{
		System.out.println("Test Start...");
		//Runner runner = new Runner();
		EnumFacing enumFacing = EnumFacing.NORTH.getOpposite();
		Debug.log(enumFacing.toString());
		Debug.log(""+Integer.parseInt("15"));
		//runner.executeInstruction(null, "set:north,15");
		System.out.println("End");
	}
	enum Type
	{
		A,
		B
	}
}
