package me.robertyang.codeic;

public class Debug {
	public static void log(String message)
	{
		if(Codeic.instance==null) 
			System.out.println("[DEBUG]" + message);
		else
			Codeic.logger.info("[DEBUG]" + message);
	}
}
