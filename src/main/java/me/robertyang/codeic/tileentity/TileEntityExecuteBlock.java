package me.robertyang.codeic.tileentity;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityExecuteBlock extends TileEntity {
	
	public ArrayList<String> commands = new ArrayList<String>();
	public static final String Key_commands = "commands";
	
	public TileEntityExecuteBlock getTileEntity(ArrayList<String> commands) {
		TileEntityExecuteBlock result = new TileEntityExecuteBlock();
		result.commands = commands;
		return result;
	}
	
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        commands = stringToStringArray(compound.getString(Key_commands));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);       
        compound.setString(Key_commands, stringArrayToString(commands));
        return compound;
    }
    
    public static String stringArrayToString(ArrayList<String> value)
    {
    	StringBuilder resultTemp = new StringBuilder();
    	for(int i = 0;i<value.size();i++)
    	{
    		resultTemp.append(value.get(i));
    		resultTemp.append('\n');
    	}
    	String result = resultTemp.toString();
    	result.substring(0,result.length());
    	return result;
    }
    public static ArrayList<String> stringToStringArray(String value)
    {
    	String[] array = value.split("\n");
    	ArrayList<String> result = new ArrayList<String>();
    	for (String string : array) {
			result.add(string);
		}
    	return result;
    }
}
