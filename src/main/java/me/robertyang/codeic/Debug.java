package me.robertyang.codeic;

public class Debug {
	public static void log(String message)
	{
		if(Codeic.instance!=null) return;//if running environment is mod,do not use this.
		System.out.println(message);
	}
}
