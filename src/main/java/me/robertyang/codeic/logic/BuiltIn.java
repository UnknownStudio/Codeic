package me.robertyang.codeic.logic;

import me.robertyang.codeic.tileentity.TileEntityExecuteBlock;
import net.minecraft.util.EnumFacing;

public class BuiltIn {
	public static boolean executeCommand(Runner runner,TileEntityExecuteBlock teb,String commandName,String[] parameters)
	{
		if(commandName.equals("set"))
		{
			cmd_set(runner, teb, parameters);
		}
		return false;
	}
	public static boolean cmd_set(Runner runner,TileEntityExecuteBlock teb,String[] parameters)
	{
		String direction = parameters[0];//It must be a value(include built-in value).
		String power = parameters[1];
		if(runner.values.getValue(direction)==(Object)BuiltInData.BuiltInValue.north)
		{
			teb.command_set(EnumFacing.NORTH,Integer.parseInt(power));
			//Debug.log("DONE");
			return true;
		}
		return false;
	}
}
