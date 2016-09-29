package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupService {
	private void copyFolder(File src, File dest) throws IOException{
		if (src.isDirectory()){
			//create if directory doesn't exist
			if(!dest.exists()){
				dest.mkdir();
			}
			
			//list all directory contents
			String files[] = src.list();
			
			for(String file:files){
				File srcFile = new File (src, file);
				File destFile = new File(dest, file);
				//recursive call
				copyFolder(srcFile,destFile);
			}
		}
		
		else{
			//if file the copy
			
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			
			byte[] buffer = new byte[1024];
			
			int length;
			while((length = in.read(buffer))>0){
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();	
			
			
		}
	}
	
	
	public void backup(){
		File srcFile = new File(util.Utilities.getLocation());
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		File destFile = new File("D:\\TasKing Backup"+df.format(new Date()));
		try{
			copyFolder(srcFile, destFile);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void flush() throws FileNotFoundException{
		File srcFile = new File (util.Utilities.getLocation());
		PrintWriter pw;
		for(String path:srcFile.list()){
			if(!path.contains("User")&&!path.contains("location")  &&! path.contains("tasknum")){
				pw = new PrintWriter(new File(srcFile,path));
				pw.println("[]");
				pw.close();
			}
		}
	}

}
