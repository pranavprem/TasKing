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
import beans.UserBean;

public class UserDAO {

	public static void putAllUsers(ArrayList<UserBean> users){
		ObjectMapper mapper = new ObjectMapper();
		try{
			mapper.writeValue(new File(util.Utilities.getLocation()+"User.json"), users);
		} catch (JsonGenerationException e) {
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
	
	public static ArrayList<NewTaskBean> getAllUsers(){
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<NewTaskBean> users = new ArrayList<NewTaskBean>();
		
		try{
			users= mapper.readValue(new File(util.Utilities.getLocation()+"User.json"), new TypeReference<ArrayList<NewTaskBean>>() {
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
		
		
		return users;
	}
	
}
