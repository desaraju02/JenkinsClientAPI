package com.nag;

import java.util.ArrayList;
import java.util.List;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class JenkinsAPIClient {
	
	public static String USERNAME = "admin";
	public static String PASSWORD = "admin";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Check 0: List Jobs");
		List<String> jobList = listJobs("http://localhost:8080");
		System.out.println("First Job:"+jobList.get(0));

	}
	
	
	public static List<String> listJobs(String url) {
		Client client = Client.create();
		client.addFilter(new com.sun.jersey.api.client.filter.HTTPBasicAuthFilter(USERNAME, PASSWORD));
		WebResource webResource = client.resource(url+"/api/xml");
		ClientResponse response = webResource.get(ClientResponse.class);
		String jsonResponse = response.getEntity(String.class);
		client.destroy();
//		System.out.println("Response listJobs:::::"+jsonResponse);
		
		// Assume jobs returned are in xml format, TODO using an XML Parser would be better here
		// Get name from <job><name>...
		List<String> jobList = new ArrayList<String>();
		String[] jobs = jsonResponse.split("job>"); // 1, 3, 5, 7, etc will contain jobs
		for(String job: jobs){
			String[] names = job.split("name>");
			if(names.length == 3) {
				String name = names[1];
				name = name.substring(0,name.length()-2); // Take off </ for the closing name tag: </name>
				jobList.add(name);
//				System.out.println("name:"+name);
			}
//			System.out.println("job:"+job);
//			for(String name: names){
//				System.out.println("name:"+name);
//			}
		}
		return jobList;
	}

}
