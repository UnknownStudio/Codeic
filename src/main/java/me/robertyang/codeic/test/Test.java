package me.robertyang.codeic.test;

public class Test {
	public static void main(String args[])
	{
		System.out.println("Test Start...");
		xxx(1);
		System.out.println("End");
	}
	static int xxx(int a)
	{
		if(a>9)
			return 0;
		else
		{
			for(int b =1;b<=9;b++)
				System.out.print(b+"*" + a+"="+a*b+"\t");
			System.out.print('\n');
			xxx(a+1);
			return 0;
		}
	}
}
