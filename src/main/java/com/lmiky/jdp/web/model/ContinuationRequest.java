package com.lmiky.jdp.web.model;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 类说明
 * @author lmiky
 * @date 2013-4-24
 */
public class ContinuationRequest extends HttpServletRequestWrapper implements HttpServletRequest {
	@SuppressWarnings("rawtypes")
	private Map parameters;
	
	/**
	 * @param request
	 * @param parameters
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ContinuationRequest(HttpServletRequest request, Map parameters) {
		super(request);
		this.parameters = parameters;
		//追加request的参数
		Enumeration parameterNames = request.getParameterNames();
		while(parameterNames!=null && parameterNames.hasMoreElements()) {
			String parameterName = (String)parameterNames.nextElement();
			this.parameters.put(parameterName, request.getParameterValues(parameterName));
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameterMap()
	 */
	@SuppressWarnings("rawtypes")
	public Map getParameterMap() {
		return parameters;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameterNames()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Enumeration getParameterNames() {
		Set names = parameters.keySet();
		Hashtable hashtable = new Hashtable();
		if(names!=null && !names.isEmpty()) {
			for(Iterator iterator = names.iterator(); iterator.hasNext();) {
				String name = (String)iterator.next();
				hashtable.put(name, name);
			}
		}
		return hashtable.elements();
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	 */
	public String[] getParameterValues(String arg0) {
		String[]  parameterValues = (String[])parameters.get(arg0);
		if(parameterValues == null || parameterValues.length == 0) {
			return getRequest().getParameterValues(arg0);
		}
		return parameterValues;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String arg0) {
		String[] values = getParameterValues(arg0);
		return values==null || values.length==0 ? null : values[0];
	}

}