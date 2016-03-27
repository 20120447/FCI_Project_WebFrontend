package com.application;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 * 
 * UserController  class contains rest services  , this class contain all action functions for web Application.
 * version 1.0
 * @author Mohamed samir ,Hadeer Tarek , Nesma mahmoud , Nada Nashaat , Fatma Abdelaty
 * since 17/3/2016
 */
@Path("/")
public class UserController 
{
	@Context
	HttpServletRequest request;

	/**
	 * Action function to render login page this function will be excuted by using url like (rest/login)
	 * @return login page
	 */
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public Response loginPage() 
	{
		return Response.ok(new Viewable("/Login.jsp")).build();
	}

	
	/**
	 * Action function to render signup page this function will be excuted
	 *  by using url like (rest/signup)
	 * @return signup page 
	 */
	
	@GET
	@Path("/signUp")
	@Produces(MediaType.TEXT_HTML)

	public Response signUpPage() 
	{
		return Response.ok(new Viewable("/Signup.jsp")).build();
	}
	
	
	/**
	 * Action function to render show location page  page 
	 * this function will be excuted by using url lik (rest/showLocation)
	 * @return  show location page 
	 */
	
	@GET
	@Path("/showLocation")
	@Produces(MediaType.TEXT_HTML)
	public Response showLocationPage(){
		return Response.ok(new Viewable("/ShowLocation.jsp")).build();
	}
	
	/**
	 * updateLocation Action function , this function act as a controller part 
	 * it will call updatePosition service to update user data 
	 * @param lat  
	 *        provided Latitude of current user's position
	 * @param lon  
	 *        provide Longitude of current user's position
	 * @return  status json
	 */
	@POST
	@Path("/updateMyLocation")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateLocation(@FormParam("lat") String lat, @FormParam("long") String lon)
	{
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute("id");
		//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/updatePosition";
		String serviceUrl = "http://localhost:8080/FCISquare/rest/login";

		String urlParameters = "id=" + id + "&lat=" + lat + "&long="+ lon;
		// System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		JSONObject obj;
		try {
			obj = (JSONObject)parser.parse(retJson);
			Long status = (Long) obj.get("status");
			if(status == 1)
				return "Your location is updated";
			else
				return "A problem occured";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "A problem occured";
		
	}
	
	///////follow User////
	
	/**
	 * Action function , this function act as a controller part 
	 * it will call  followUser service to check user data  and update it to add new user
	 * @param tofollow 
	 *               provided user id the want to follow his/her
	 * @return  string statement
	 */

	@POST
	@Path("/updateNewFollower")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateNewFollower(@FormParam("tofollow") String tofollow)
	{
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute("id");
		//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/updatePosition";
		String serviceUrl = "http://localhost:8080/FCISquare/rest/followUser";

		String urlParameters = "id=" + id + "&tofollow=" + tofollow ;
		// System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		JSONObject obj;
		try {
			obj = (JSONObject)parser.parse(retJson);
			Long status = (Long) obj.get("status");
			if(status == 1)
				return "Your follower is updated ";
			else
				return "A problem occured";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "A problem occured";
		
	}
	//////////////////////
	

	/**
	 *  Action function used to call login service to check user data and get user from data store
	 * @param email 
	 *            provided user email 
	 * @param pass  
	 *          provided user password
	 * @return  login page 
	 */
	@POST
	@Path("/doLogin")
	@Produces(MediaType.TEXT_HTML)
	public Response showHomePage(@FormParam("email") String email,
			@FormParam("pass") String pass) 
	{
		//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/login";
		String serviceUrl = "http://localhost:8080/FCISquare/rest/login";

		String urlParameters = "email=" + email + "&pass=" + pass;
		// System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		HttpSession session = request.getSession();
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			obj = (JSONObject) parser.parse(retJson);
			session.setAttribute("email", obj.get("email"));
			session.setAttribute("name", obj.get("name"));
			session.setAttribute("id", obj.get("id"));
			session.setAttribute("lat", obj.get("lat"));
			session.setAttribute("long", obj.get("long"));
			session.setAttribute("pass", obj.get("pass"));
			Map<String, String> map = new HashMap<String, String>();

			map.put("name", (String) obj.get("name"));
			map.put("email", (String) obj.get("email"));

			return Response.ok(new Viewable("/home.jsp", map)).build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Action function used to call signup  service to check user data and store user in data store
	 * @param name     provided user name   
	 * @param email    provided user email
	 * @param pass     provided user password
	 * @return   signup page view
	 */
	
	@POST
	@Path("/doSignUp")
	@Produces(MediaType.TEXT_HTML)
	public Response showHomePage(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass) 
	{
		//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/signup";
		String serviceUrl = "http://localhost:8080/FCISquare/rest/signup";

		String urlParameters = "name=" + name + "&email=" + email + "&pass="
				+ pass;
		// System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		HttpSession session = request.getSession();
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			obj = (JSONObject) parser.parse(retJson);
			session.setAttribute("email", obj.get("email"));
			session.setAttribute("name", obj.get("name"));
			session.setAttribute("id", obj.get("id"));
			session.setAttribute("lat", obj.get("lat"));
			session.setAttribute("long", obj.get("long"));
			session.setAttribute("pass", obj.get("pass"));
			Map<String, String> map = new HashMap<String, String>();

			map.put("name", (String) obj.get("name"));
			map.put("email", (String) obj.get("email"));

			return Response.ok(new Viewable("/home.jsp", map)).build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	
	/**
	 * Action function used to call getFollower service
	 *  and get data of followed users from data store 
	 * @param id   provided user id 
	 * @return   userd ids
	 */
	
	
	@POST
	@Path("/getFollower")
	@Produces(MediaType.TEXT_PLAIN)
	public ArrayList getFollower(@FormParam("ID") int id){
		HttpSession session = request.getSession();
		//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/updatePosition";
		String serviceUrl = "http://localhost:8080/FCISquare/rest/getFollower";

		String urlParameters = "id=" + id;
		// System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		ArrayList <Integer> IDs = new ArrayList<Integer>();
		JSONObject obj;
		try {
			obj = (JSONObject)parser.parse(retJson);
			int i = 0;
			IDs.add((int) session.getAttribute("followerID"), i);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return IDs;
		
	}
	
	// ------------------------------------------- GetLastPosition --------------------------------------------------//

	    /**
	     * Action function used to call GetLastPosition service to check user
	     *  data  and get user information from data store
	     * @param id  provided user id 
	     * @return  status json
	     */
	
		@POST
		@Path("/GetLastPosition")
		@Produces(MediaType.TEXT_PLAIN)
		public String GetLastPosition(@FormParam("id") String id) {
			HttpSession session = request.getSession();
			//Long id = (Long) session.getAttribute("id");
			// String serviceUrl =
			// "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/updatePosition";
			String serviceUrl = "http://localhost:8080/FCISquare/rest/GetLastPosition";

			String urlParameters = "id=" + id;
			System.out.println(urlParameters);
			String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
					"application/x-www-form-urlencoded;charset=UTF-8");
			JSONParser parser = new JSONParser();
			JSONObject obj;
			try {
				obj = (JSONObject) parser.parse(retJson);
				session.getAttribute("lat");
				session.getAttribute("long");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return session.toString();

		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * Action function used to call unfollowUSer to check user data and update  
		 * data store after deleting the unfollowed user
		 * @param followerID   
		 *                 provide follower id user 
		 * @param followedID
		 *                 provided followed id user 
		 * @return   string statement  
		 */
		
		@POST
		@Path("/unfollowUSer")
		@Produces(MediaType.TEXT_PLAIN)
		public String unfollowUSer(@FormParam("id_1")Integer followerID,  @FormParam("id_2") Integer followedID){
			HttpSession session = request.getSession();
			Long id = (Long) session.getAttribute("id");
			//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/unfollowUser";
			String serviceUrl = "http://localhost:8080/FCISquare/rest/unfollowUser";

			String urlParameters = "id=" + id + "&id_1=" + followerID+ "&id_2=" + followedID ;  
			// System.out.println(urlParameters);
			String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
					"application/x-www-form-urlencoded;charset=UTF-8");
			JSONParser parser = new JSONParser();
			JSONObject obj;
			try {
				obj = (JSONObject)parser.parse(retJson);
				Long status = (Long) obj.get("status");
				if(status == 1)
					return "Your request is done ";
				else
					return "A problem occured";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "A problem occured";
			
		}


}
