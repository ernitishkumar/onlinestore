package com.wingify.filters;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.wingify.beans.ErrorMessage;
import com.wingify.beans.User;
import com.wingify.daos.UserDAO;
import com.wingify.utility.GlobalResources;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
public class AuthenticationFilter implements Filter {

	private UserDAO userDAO = new UserDAO();
	private Gson gson = GlobalResources.getGson();

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String authData = httpServletRequest.getHeader(AUTHORIZATION_HEADER_KEY);
		boolean authenticated = false;
		if(authData != null){
			authData = authData.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
			Decoder decoder = Base64.getDecoder();
			authData = new String(decoder.decode(authData));
			StringTokenizer authTokens = new StringTokenizer(authData,":");
			String username = authTokens.nextToken();
			String password = authTokens.nextToken();

			try{
				User user = userDAO.getByUsername(username);
				if(user != null && user.getPassword().equals(password)){
					authenticated = true;
				}
			}catch(Exception exception){
				System.out.println("Exception while getting user.");
			}
		}
		if(authenticated){
			chain.doFilter(request, response);
		}else{
			System.out.println("Filter coming here");
			response.setContentType("application/json");
			ErrorMessage error = new ErrorMessage("Unauthorized Access. Access Denied!");
			JsonElement jsonElement = gson.toJsonTree(error, new TypeToken<ErrorMessage>(){}.getType());
			response.getWriter().write(jsonElement.toString());
			return;
		}	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}

}
