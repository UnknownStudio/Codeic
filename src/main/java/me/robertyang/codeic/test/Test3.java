package me.robertyang.codeic.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.WString;

import me.robertyang.codeic.Debug;

public class Test3 {
    public interface TestDll1 extends Library {


        TestDll1 INSTANCE = (TestDll1)Native.loadLibrary(path, TestDll1.class);

        public void printf(String format, Object... args); 
        public int say(WString wString);
       

}
    static String path = new Test3().getClass().getResource("/").getFile().toString() + "test.dll";
    						//"/C:/Users/Robert/.gradle/caches/minecraft/net/minecraftforge/forge/1.11.2-13.20.0.2224/start/test.dll";
    public static void main(String args[]) throws IOException
	{
		//System.load(path);
		InputStream inputStream1 = new Test3().getClass().getResourceAsStream("src/main/java/me/robertyang/codeic/dll/test.dll");
		if(inputStream1!=null)Debug.log("YES1");
		InputStream inputStream2 = new Test3().getClass().getResourceAsStream("/assets/codeic/dll/test.dll");
		if(inputStream2!=null)Debug.log("YES2");
		
		File file = new File(path);
		file.createNewFile();
		byte[] data = new byte[inputStream2.available()];
		inputStream2.read(data);
		FileOutputStream fileOutputStream = new FileOutputStream(path);
		fileOutputStream.write(data);
		fileOutputStream.close();
		path = path.substring(1, path.length());
		Debug.log(""+TestDll1.INSTANCE.say(new WString("0.0")));
		//TestDll1.INSTANCE.printf("Helloworld");
		Debug.log("END");
	}
}
