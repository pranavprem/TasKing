package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utilities {
	public static String getLocation(){
		Scanner sc=null;
		String location=null;
		try{
			sc=new Scanner(new File("location.txt"));
			location=sc.nextLine();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		finally{
			sc.close();
		}
		
		return location;
	}
	
	public static String cleanString(String stringToBeCleaned){
		return stringToBeCleaned.replace("\n", "").replace("\r", "").replace(Constants.comma, ".");
	}

}
