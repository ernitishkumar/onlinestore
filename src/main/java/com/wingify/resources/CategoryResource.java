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
import com.wingify.beans.Category;
import com.wingify.beans.ErrorMessage;
import com.wingify.daos.CategoriesDAO;
import com.wingify.utility.GlobalResources;

/**
 * Servlet implementation class CategoryResource
 */
public class CategoryResource extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CategoriesDAO categoryDAO = new CategoriesDAO();
	
	private Gson gson = GlobalResources.getGson();
	
	private final String CATEGORY_URL = "localhost:8080/onlinestore/categories/";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Get Request recieved for Category Resource");
		String pathInfo = request.getPathInfo();
		System.out.println("Path Info is : "+pathInfo);
		response.setContentType("application/json");
		try{
			if(pathInfo == null || pathInfo.length()==1){
				//Sending all attributes since no id is mentioned
				String name = request.getParameter("name");
				String parentId = request.getParameter("parentId");
				ArrayList<Category> categories = null;
				if(name != null){
					categories = categoryDAO.getByName(name);
				}else if(parentId != null){
					categories = categoryDAO.getByParentId(Integer.parseInt(parentId.trim()));
				}else{
					categories = categoryDAO.getAll();
				}
				JsonElement element = gson.toJsonTree(categories,new TypeToken<ArrayList<Category>>(){}.getType());
				JsonArray jsonArray = element.getAsJsonArray();
				if(categories.size()>0){
					response.setStatus(HttpServletResponse.SC_OK);
				}else{
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				}
				response.getWriter().write(jsonArray.toString());
			}else{
				String id = pathInfo.substring(pathInfo.indexOf("/")+1);
				System.out.println("Category ID from get request"+id);
				if(id!=null && id.length()>0){
					int categoryId = Integer.parseInt(id.trim());
					Category category = categoryDAO.getById(categoryId);
					if(category!=null){
						JsonElement element = gson.toJsonTree(category,new TypeToken<Category>(){}.getType());
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
			System.out.println("Exception while getting categories "+exception.getMessage());
			exception.printStackTrace();
			ErrorMessage error = new ErrorMessage("Some error occured. Please try Again!");
			JsonElement element = gson.toJsonTree(error,new TypeToken<ErrorMessage>(){}.getType());
			response.getWriter().write(element.toString());
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST Request recieved for Category Resource");
		String pathInfo = request.getPathInfo();
		System.out.println("Path Info is : "+pathInfo);
		response.setContentType("application/json");
		Category insertedCategory = null;
		try{
			Category categoryToInsert = getCategoryFromRequest(request);
			if(categoryToInsert != null){
				insertedCategory = categoryDAO.insert(categoryToInsert);
			}
		}catch(Exception exception){
			System.out.println("Exception in doPost "+exception.toString());
		}
		if(insertedCategory != null){
			JsonElement element = gson.toJsonTree(insertedCategory,new TypeToken<Category>(){}.getType());
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.setHeader("Location", CATEGORY_URL+insertedCategory.getCategoryId());
			response.getWriter().write(element.toString());
		}else{
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
		}
	}

	private Category getCategoryFromRequest(HttpServletRequest request) throws IOException{
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line = null;
		while((line = reader.readLine())!=null){
			sb.append(line);
		}
		JsonParser parser=new JsonParser();
		JsonElement jsonElement = parser.parse(sb.toString());
		System.out.println("Converted json element is : "+jsonElement.toString());
		Category category = gson.fromJson(jsonElement, Category.class);
		System.out.println("converted from json data category is "+category);
		return category;
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PUT Request recieved for Attribute Resources");
		response.setContentType("application/json");
		Category updatedCategory = null;
		try{
			Category categoryToUpdate = getCategoryFromRequest(request);
			if(categoryToUpdate != null){
				updatedCategory = categoryDAO.update(categoryToUpdate);
			}
		}catch(Exception exception){
			System.out.println("Exception in doPost "+exception.toString());
		}
		if(updatedCategory != null){
			JsonElement element = gson.toJsonTree(updatedCategory,new TypeToken<Category>(){}.getType());
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
				System.out.println("Category ID from get request"+id);
				if(id!=null){
					int categoryId = Integer.parseInt(id.trim());
					Category deletedCategory = categoryDAO.delete(categoryId);
					if(deletedCategory!=null){
						JsonElement element = gson.toJsonTree(deletedCategory,new TypeToken<Category>(){}.getType());
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
			System.out.println("Exception while deleting category "+exception.getMessage());
			exception.printStackTrace();
			ErrorMessage error = new ErrorMessage("Some error occured. Please try Again!");
			JsonElement element = gson.toJsonTree(error,new TypeToken<ErrorMessage>(){}.getType());
			response.getWriter().write(element.toString());
		}
	
	}

}
