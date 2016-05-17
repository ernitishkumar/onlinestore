package com.wingify.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import com.wingify.beans.Attribute;
import com.wingify.beans.ErrorMessage;
import com.wingify.daos.AttributesDAO;
import com.wingify.utility.GlobalResources;

/**
 * Servlet implementation class AttributeResource
 */
public class AttributeResource extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private AttributesDAO attributeDAO = new AttributesDAO();

	private Gson gson = GlobalResources.getGson();
	
	private final String ATTRIBUTE_URL = "localhost:8080/onlinestore/attributes/";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Get Request recieved for Attribute Resources");
		String pathInfo = request.getPathInfo();
		System.out.println("Path Info is : "+pathInfo);
		response.setContentType("application/json");
		try{
			if(pathInfo == null || pathInfo.length()==1){
				//Sending all attributes since no id is mentioned
				String attributeName = request.getParameter("attributeName");
				
				ArrayList<Attribute> attributes = null;
				if(attributeName != null){
					attributes = attributeDAO.getByAttributeName(attributeName);
				}else{
					attributes = attributeDAO.getAll();
				}
				JsonElement element = gson.toJsonTree(attributes,new TypeToken<ArrayList<Attribute>>(){}.getType());
				JsonArray jsonArray = element.getAsJsonArray();
				if(attributes.size()>0){
					response.setStatus(HttpServletResponse.SC_OK);
				}else{
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				}
				response.getWriter().write(jsonArray.toString());
			}else{
				String id = pathInfo.substring(pathInfo.indexOf("/")+1);
				System.out.println("Attribute ID from get request"+id);
				if(id!=null && id.length()>0){
					int attributeId = Integer.parseInt(id.trim());
					Attribute attribute = attributeDAO.getById(attributeId);
					if(attribute!=null){
						JsonElement element = gson.toJsonTree(attribute,new TypeToken<Attribute>(){}.getType());
						response.setStatus(HttpServletResponse.SC_OK);
						response.getWriter().write(element.toString());
					}else{
						response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					}
				}else{
					ErrorMessage error = new ErrorMessage("Bad Request try Again");
					JsonElement element = gson.toJsonTree(error,new TypeToken<ErrorMessage>(){}.getType());
					response.getWriter().write(element.toString());
				}
			}
		}catch(Exception exception){
			System.out.println("Exception while getting attributes "+exception.getMessage());
			ErrorMessage error = new ErrorMessage("Some error occured. Please try Again!");
			JsonElement element = gson.toJsonTree(error,new TypeToken<ErrorMessage>(){}.getType());
			response.getWriter().write(element.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST Request recieved for Attribute Resources");
		String pathInfo = request.getPathInfo();
		System.out.println("Path Info is : "+pathInfo);
		response.setContentType("application/json");
		Attribute insertedAttribute = null;
		try{
			Attribute attributeToInsert = getAttributeFromRequest(request);
			if(attributeToInsert != null){
				insertedAttribute = attributeDAO.insert(attributeToInsert);
			}
		}catch(Exception exception){
			System.out.println("Exception in doPost "+exception.toString());
		}
		if(insertedAttribute != null){
			JsonElement element = gson.toJsonTree(insertedAttribute,new TypeToken<Attribute>(){}.getType());
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.setHeader("Location", ATTRIBUTE_URL+insertedAttribute.getAttributeId());
			response.getWriter().write(element.toString());
		}else{
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
		}
	}

	private Attribute getAttributeFromRequest(HttpServletRequest request) throws IOException{
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line = null;
		while((line = reader.readLine())!=null){
			sb.append(line);
		}
		JsonParser parser=new JsonParser();
		JsonElement jsonElement = parser.parse(sb.toString());
		System.out.println("Converted json element is : "+jsonElement.toString());
		Attribute attribute = gson.fromJson(jsonElement, Attribute.class);
		System.out.println("converted from json data attribute is "+attribute);
		return attribute;
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PUT Request recieved for Attribute Resources");
		response.setContentType("application/json");
		Attribute updatedAttribute = null;
		try{
			Attribute attributeToUpdate = getAttributeFromRequest(request);
			if(attributeToUpdate != null){
				updatedAttribute = attributeDAO.update(attributeToUpdate);
			}
		}catch(Exception exception){
			System.out.println("Exception in doPost "+exception.toString());
		}
		if(updatedAttribute != null){
			JsonElement element = gson.toJsonTree(updatedAttribute,new TypeToken<Attribute>(){}.getType());
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write(element.toString());
		}else{
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("DELETE Request recieved for Attribute Resources");
		String pathInfo = request.getPathInfo();
		System.out.println("Path Info is : "+pathInfo);
		response.setContentType("application/json");
		try{
			if(pathInfo != null){
				String id = pathInfo.substring(pathInfo.indexOf("/")+1);
				System.out.println("Attribute ID from get request"+id);
				if(id!=null){
					int attributeId = Integer.parseInt(id.trim());
					Attribute deletedAttribute = attributeDAO.delete(attributeId);
					if(deletedAttribute!=null){
						JsonElement element = gson.toJsonTree(deletedAttribute,new TypeToken<Attribute>(){}.getType());
						response.setStatus(HttpServletResponse.SC_OK);
						response.getWriter().write(element.toString());
					}else{
						response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					}
				}else{
					ErrorMessage error = new ErrorMessage("Bad Request try Again");
					JsonElement element = gson.toJsonTree(error,new TypeToken<ErrorMessage>(){}.getType());
					response.getWriter().write(element.toString());
				}
			}
		}catch(Exception exception){
			System.out.println("Exception while deleting attributes "+exception.getMessage());
			ErrorMessage error = new ErrorMessage("Some error occured. Please try Again!");
			JsonElement element = gson.toJsonTree(error,new TypeToken<ErrorMessage>(){}.getType());
			response.getWriter().write(element.toString());
		}
	
	}

}
