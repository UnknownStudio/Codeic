package me.robertyang.codeic.dllloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import me.robertyang.codeic.test.Test3;

public class DllLoader {
	static String path = new Test3().getClass().getResource("/").getFile().toString();
	enum FileName
	{
		//test("test.dll");
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
		
	}
	
	static void moveFiles() throws IOException
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
}
