package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.NewTaskBean;

public class TaskDAO {

	public static void putAllTasks(ArrayList<NewTaskBean> tasks, String user) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File(util.Utilities.getLocation() + user + ".json"), tasks);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void complete(NewTaskBean task, String user) {
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<NewTaskBean> tasks = null;
		try {
			tasks = mapper.readValue(new File(util.Utilities.getLocation() + user + "-completed.json"),
					new TypeReference<ArrayList<NewTaskBean>>() {
					});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static ArrayList<NewTaskBean> getAllTasks(String user){
	
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<NewTaskBean> tasks = new ArrayList<NewTaskBean>();
		
		try{
			tasks= mapper.readValue(new File(util.Utilities.getLocation()+user+".json"), new TypeReference<ArrayList<NewTaskBean>>() {
			});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return tasks;
	}
	
	
	public static ArrayList<NewTaskBean> getAllCompletedTasks(String id){
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<NewTaskBean> tasks = null;
		
		try{
			tasks = mapper.readValue(new File(util.Utilities.getLocation()+id+"-completed.json"), new TypeReference<ArrayList<NewTaskBean>>(){});
		}catch(JsonParseException e){
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return tasks;
	}
	
	
	

}
