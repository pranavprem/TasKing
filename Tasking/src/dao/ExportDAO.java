package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import beans.ExportTaskBean;

public class ExportDAO {
	public static void export(ArrayList<ExportTaskBean> completedTasks){
		try{
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh mm");
			PrintWriter pw = new PrintWriter (new File(util.Utilities.getLocation()+"Completed Tasks List "+df.format(new Date())+".csv"));
			pw.println(ExportTaskBean.getHeaders());
			for(ExportTaskBean task: completedTasks){
				pw.println(task.toString());
			}
			pw.close();
} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
