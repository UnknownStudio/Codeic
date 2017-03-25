package me.robertyang.codeic.dllloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.WString;

import me.robertyang.codeic.Debug;
import me.robertyang.codeic.test.Test3;
import me.robertyang.codeic.test.Test3.TestDll1;

public class DllLoader {
	static String path = new DllLoader().getClass().getResource("/").getFile().toString();
	enum FileName
	{
		//test("test.dll");
		abacus("codeic.dll","/assets/codeic/dll/codeic.dll")
		;
		String fileName = "";
		String resourcePath = "";
		public String getFileName()
		{
			return fileName;
		}
		public String getResourcePath()
		{
			return resourcePath;
		}
		private FileName(String fileName,String resourcePath)
		{
			this.fileName = fileName;
			this.resourcePath = resourcePath;
		}
	}
	public static void load()
	{
		if(DllAbacus.INSTANCE.Run())
			Debug.log("GOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOD!");
	}
	
	public static void moveFiles() throws IOException
	{
		for (FileName fileName : FileName.values()) {
			InputStream inputStream = new DllLoader().getClass().getResourceAsStream(fileName.getResourcePath());
			String filePath = path + fileName.getFileName();
			filePath = filePath.substring(1, filePath.length());//TODO
			byte[] data = new byte[inputStream.available()];
			inputStream.read(data);
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);
			fileOutputStream.write(data);
			fileOutputStream.close();
		}
	}
	
	static String path_Abacus;
	public interface DllAbacus extends Library
	{
		DllAbacus INSTANCE = (DllAbacus)Native.loadLibrary(path+FileName.abacus.getFileName(), DllAbacus.class);

        //public void printf(String format, Object... args); 
        public int Compute(WString wString);
        
        public boolean Run();
	}
}
