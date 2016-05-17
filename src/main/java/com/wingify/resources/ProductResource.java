package com.wingify.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
import com.wingify.beans.Product;
import com.wingify.beans.Variant;
import com.wingify.daos.AttributesDAO;
import com.wingify.daos.ProductsDAO;
import com.wingify.daos.VariantsDAO;
import com.wingify.utility.GlobalResources;

/**
 * Servlet implementation class ProductResource
 */
public class ProductResource extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductsDAO productsDAO = new ProductsDAO();

	private VariantsDAO variantsDAO = new VariantsDAO();

	private AttributesDAO attributesDAO = new AttributesDAO();

	private Gson gson = GlobalResources.getGson();

	private final String PRODUCT_URL = "localhost:8080/onlinestore/products/";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Get Request recieved for Product Resource");
		String pathInfo = request.getPathInfo();
		System.out.println("Path Info is : "+pathInfo);
		response.setContentType("application/json");
		try{
			if(pathInfo == null || pathInfo.length()==1){
				//Sending all attributes since no id is mentioned
				String name = request.getParameter("name");
				String categoryId = request.getParameter("categoryId");
				String availability = request.getParameter("availability");
				ArrayList<Product> products = null;
				if(name != null){
					products = productsDAO.getByName(name);
				}else if(categoryId!= null){
					products = productsDAO.getByCategory(categoryId.trim());
				}else if(availability!=null){
					products = productsDAO.getByAvailability(availability.trim());
				}else{
					products = productsDAO.getAll();
				}
				JsonElement element = gson.toJsonTree(products,new TypeToken<ArrayList<Product>>(){}.getType());
				JsonArray jsonArray = element.getAsJsonArray();
				if(products.size()>0){
					response.setStatus(HttpServletResponse.SC_OK);
				}else{
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				}
				response.getWriter().write(jsonArray.toString());
			}else{
				String[] pathParams = null;
				if(pathInfo!=null){
					pathParams = pathInfo.split("/");
					System.out.println("Path param length : "+pathParams.length);
				}

				if(pathParams.length == 2){
					String id = pathParams[1];
					System.out.println("Product ID from get request "+id);
					int productId = Integer.parseInt(id.trim());
					Product product = productsDAO.getById(productId);
					if(product!=null){
						JsonElement element = gson.toJsonTree(product,new TypeToken<Product>(){}.getType());
						response.setStatus(HttpServletResponse.SC_OK);
						response.getWriter().write(element.toString());
					}else{
						response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					}
				}else if(pathParams.length > 2){
					String id= pathParams[1];
					String subResource = pathParams[2].trim();
					String subResourceId = null;
					if(pathParams.length==4){
						subResourceId = pathParams[3].trim();
					}
					int productId = Integer.parseInt(id);
					System.out.println("Getting further sub resource : "+subResource+" for the product.with id : "+productId);
					Product product = productsDAO.getById(productId);
					if(subResource.equals("variants")){
						System.out.println("Fetching variants for product ID : "+productId);
						String variantSKU = request.getParameter("variantSKU");
						ArrayList<Variant> variants = null;
						if(subResourceId!=null){
							variants = variantsDAO.getByProductIdAndVariantId(productId, Integer.parseInt(subResourceId.trim()));
						}else if(variantSKU != null){
							variants = variantsDAO.getByProductIdAndVariantSKU(productId, variantSKU.trim());
						}else{
							variants = variantsDAO.getByProductId(productId);
						}
						JsonElement element = gson.toJsonTree(variants,new TypeToken<ArrayList<Variant>>(){}.getType());
						JsonArray jsonArray = element.getAsJsonArray();
						if(variants.size()>0){
							response.setStatus(HttpServletResponse.SC_OK);
						}else{
							response.setStatus(HttpServletResponse.SC_NO_CONTENT);
						}
						response.getWriter().write(jsonArray.toString());
					}else if(subResource.equals("attributes")){
						System.out.println("Fetching attributes for product ID : "+productId);
						
						ArrayList<Attribute> attributes = attributesDAO.getById(product.getAttributes());
						JsonElement element = gson.toJsonTree(attributes,new TypeToken<ArrayList<Attribute>>(){}.getType());
						JsonArray jsonArray = element.getAsJsonArray();
						if(attributes.size()>0){
							response.setStatus(HttpServletResponse.SC_OK);
						}else{
							response.setStatus(HttpServletResponse.SC_NO_CONTENT);
						}
						response.getWriter().write(jsonArray.toString());
					}
				}else{
					ErrorMessage error = new ErrorMessage("Bad Request try Again");
					JsonElement element = gson.toJsonTree(error,new TypeToken<ErrorMessage>(){}.getType());
					response.getWriter().write(element.toString());
				}
			}
		}catch(Exception exception){
			System.out.println("Exception while getting products "+exception.getMessage());
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
		System.out.println("POST Request recieved for Product Resource");
		String pathInfo = request.getPathInfo();
		System.out.println("Path Info is : "+pathInfo);
		response.setContentType("application/json");
		if(pathInfo == null || pathInfo.length()==1){
			//Adding product
			Product insertedProduct = null;
			try{
				Product productToInsert = getProductFromRequest(request);
				if(productToInsert != null){
					insertedProduct = productsDAO.insert(productToInsert);
				}
			}catch(Exception exception){
				System.out.println("Exception in doPost "+exception.toString());
			}
			if(insertedProduct != null){
				JsonElement element = gson.toJsonTree(insertedProduct,new TypeToken<Product>(){}.getType());
				response.setStatus(HttpServletResponse.SC_CREATED);
				response.addHeader("Location",PRODUCT_URL+insertedProduct.getProductId());
				response.getWriter().write(element.toString());
			}else{
				response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			}
		}else{
			String[] pathParams = null;
			if(pathInfo!=null){
				pathParams = pathInfo.split("/");
				System.out.println("Path param length : "+pathParams.length);
			}

			if(pathParams.length > 2){
				String id= pathParams[1];
				String subResource = pathParams[2].trim();
				int productId = Integer.parseInt(id);
				System.out.println("Getting further sub resource : "+subResource+" for the product.with id : "+productId);
				if(subResource.equals("variants")){
					System.out.println("Inserting variant for product ID : "+productId);
					Variant variantToInsert = getVariantFromRequestToInsert(request);
					Variant insertedVariant = null;
					try{
						insertedVariant = variantsDAO.insert(variantToInsert);
					}catch(Exception exception){
						System.out.println("Exception while inserting Variant "+exception.getMessage());
					}
					JsonElement element = gson.toJsonTree(insertedVariant,new TypeToken<Variant>(){}.getType());
					if(insertedVariant != null){
						response.setStatus(HttpServletResponse.SC_CREATED);
						response.getWriter().write(element.toString());
					}else{
						response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
					}
				}
			}else{
				ErrorMessage error = new ErrorMessage("Bad Request try Again");
				JsonElement element = gson.toJsonTree(error,new TypeToken<ErrorMessage>(){}.getType());
				response.getWriter().write(element.toString());
			}
		}
	}

	private Product getProductFromRequest(HttpServletRequest request) throws IOException{
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line = null;
		while((line = reader.readLine())!=null){
			sb.append(line);
		}
		JsonParser parser=new JsonParser();
		JsonElement jsonElement = parser.parse(sb.toString());
		System.out.println("Converted json element is : "+jsonElement.toString());
		Product product = gson.fromJson(jsonElement, Product.class);
		System.out.println("converted from json data product is "+product);
		return product;
	}

	private Variant getVariantFromRequest(HttpServletRequest request) throws IOException{
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line = null;
		while((line = reader.readLine())!=null){
			sb.append(line);
		}
		JsonParser parser=new JsonParser();
		JsonElement jsonElement = parser.parse(sb.toString());
		Variant variant = gson.fromJson(jsonElement, Variant.class);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map = gson.fromJson(sb.toString(),new TypeToken<HashMap<String,Object>>(){}.getType());
		if(map.size()!=7){
			variant = null;
		}
		return variant;
	}
	
	private Variant getVariantFromRequestToInsert(HttpServletRequest request) throws IOException{
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line = null;
		while((line = reader.readLine())!=null){
			sb.append(line);
		}
		JsonParser parser=new JsonParser();
		JsonElement jsonElement = parser.parse(sb.toString());
		Variant variant = gson.fromJson(jsonElement, Variant.class);
		return variant;
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PUT Request recieved for Product Resource");
		response.setContentType("application/json");
		String pathInfo = request.getPathInfo();
		System.out.println("Path Info for implementing variants : "+pathInfo);
		String[] pathParams = null;
		if(pathInfo!=null){
			pathParams = pathInfo.split("/");
			System.out.println("Path param length : "+pathParams.length);
		}
		if(pathInfo == null || pathParams == null || pathParams.length<=2){
			Product updatedProduct = null;
			try{
				Product productToUpdate = getProductFromRequest(request);
				if(productToUpdate != null){
					updatedProduct = productsDAO.update(productToUpdate);
				}
			}catch(Exception exception){
				System.out.println("Exception in doPost "+exception.toString());
			}
			if(updatedProduct != null){
				JsonElement element = gson.toJsonTree(updatedProduct,new TypeToken<Product>(){}.getType());
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write(element.toString());
			}else{
				response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			}
		}else if(pathParams != null && pathParams.length>2){
			int productId = Integer.parseInt(pathParams[1].trim());
			String subResource = pathParams[2].trim();
			if(subResource.equals("variants")){
				Variant updatedVariant = null;
				try{
					Variant variantToUpdate = getVariantFromRequest(request);
					if(variantToUpdate != null){
						updatedVariant=variantsDAO.update(variantToUpdate);
					}
				}catch(Exception exception){
					System.out.println("Exception while updating variant for product id : "+productId);
					exception.printStackTrace();
				}
				if(updatedVariant != null){
					JsonElement element = gson.toJsonTree(updatedVariant,new TypeToken<Variant>(){}.getType());
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().write(element.toString());
				}else{
					response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("DELETE Request recieved for Product Resources");
		String pathInfo = request.getPathInfo();
		System.out.println("Path Info is : "+pathInfo);
		response.setContentType("application/json");
		try{
			String[] pathParams = null;
			if(pathInfo!=null){
				pathParams = pathInfo.split("/");
				System.out.println("Path param length : "+pathParams.length);
			}
			if(pathInfo != null && pathParams.length<3){
				String id = pathInfo.substring(pathInfo.indexOf("/")+1);
				System.out.println("Category ID from get request"+id);
				if(id!=null){
					int productId = Integer.parseInt(id.trim());
					Product deletedProduct = productsDAO.delete(productId);
					if(deletedProduct!=null){
						JsonElement element = gson.toJsonTree(deletedProduct,new TypeToken<Product>(){}.getType());
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
			}else{
				int productId = Integer.parseInt(pathParams[1].trim());
				String subResource = pathParams[2].trim();
				int subResourceId = Integer.parseInt(pathParams[3].trim());
				if(subResource.equals("variants")){
					Variant deletedVariant = null;
					try{
						deletedVariant=variantsDAO.delete(subResourceId);
					}catch(Exception exception){
						System.out.println("Exception while deleting variant for product id : "+productId+" and with variantId : "+subResourceId);
						exception.printStackTrace();
					}
					if(deletedVariant != null){
						JsonElement element = gson.toJsonTree(deletedVariant,new TypeToken<Variant>(){}.getType());
						response.setStatus(HttpServletResponse.SC_OK);
						response.getWriter().write(element.toString());
					}else{
						response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
					}
				}
			}
		}catch(Exception exception){
			System.out.println("Exception while deleting product "+exception.getMessage());
			exception.printStackTrace();
			ErrorMessage error = new ErrorMessage("Some error occured. Please try Again!");
			JsonElement element = gson.toJsonTree(error,new TypeToken<ErrorMessage>(){}.getType());
			response.getWriter().write(element.toString());
		}

	}

}
