package geso.traphaco.center.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class GiuDieuKienLoc<T> implements Serializable
{
	private static final long serialVersionUID = -417296541724890668L;
	public HashMap  <String , String > hmSearch  = new HashMap<String, String>();
		
	public HashMap<String, String> getHmSearch() {
		return hmSearch;
	}
	
	public void setHmSearch(HashMap<String, String> hmSearch) {
		this.hmSearch = hmSearch;
	}
	@SuppressWarnings("unchecked")
	public String getSearchFromHM(String userId,String ServerletName, HttpSession session)   
	{  
		String searchQuery="";
		ServerletName=ServerletName.toLowerCase();
		ServerletName=ServerletName.replaceAll("update", "");
	    String keyHM= ServerletName+'_'+userId;
		this.hmSearch = (HashMap<String,String>) session.getAttribute("hmSearch");
		if(null==this.hmSearch)
		{
			this.hmSearch= new HashMap<String,String>();
		}
	    searchQuery=hmSearch.get(keyHM);
	    return searchQuery;
	}
	
	@SuppressWarnings("unchecked")
	public void setSearchToHM(String userId,HttpSession session,String ServerletName,String searchQuery)   
	{  
		ServerletName=ServerletName.toLowerCase();
		ServerletName=ServerletName.replaceAll("update", "");
	    String keyHM= ServerletName+'_'+userId;
		this.hmSearch= (HashMap<String,String>) session.getAttribute("hmSearch");
		if(null==this.hmSearch)
		{
			this.hmSearch= new HashMap<String,String>();
		}
	    hmSearch.put(keyHM, searchQuery);
	    session.setAttribute("hmSearch", this.hmSearch);
	}
	
	public static <T> String createParams(T obj)
	{
		String params = "";
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		Field[] supperDeclaredFields = obj.getClass().getSuperclass().getDeclaredFields();
		params = createParams(obj, declaredFields);
		String params2 = createParams(obj, supperDeclaredFields);
		params=params.length()>0?("&" +params) :params;
		
        if (params.trim().length() > 0 && params2.trim().length() > 0)
        	params += "&";
        
        params += params2;
        
        System.out.println("params:\n" + params);
		return params;
	}
	
	@SuppressWarnings({ "rawtypes"})
	private static <T> String createParams(T obj, Field[] fieldList)
	{
		String params = "";
		for (Field field : fieldList) {
			Object value = null;
        	field.setAccessible(true);
			Class type = field.getType();
             String name = field.getName();
        	 System.out.println("name: " + name + ": " + type.toString());
             if (type.equals(double[].class) || type.equals(double.class) || type.equals(float.class) || type.equals(int.class) || type.equals(String.class) 
            		 || type.equals(long.class) || type.equals(Integer.class) || type.equals(Double.class) || type.equals(Float.class) 
            		 || type.equals(char.class) || type.equals(boolean.class)
            		 || type.equals(int[].class) || type.equals(Integer[].class)
            		 )
             {
	             try {
	            	 if(!name.equals("serialVersionUID")&&!name.toLowerCase().equals("msg"))
	            	 {
		            	 if (params.trim().length() > 0)
		                	 params += "&";
		            	 params += name;
		            	 String myValue = "null";
	            		 value = field.get(obj);
		            		 
						if (value != null)
						{
							 if (type.equals(int[].class) || type.equals(Integer[].class))
			            	 {
			            		 int[] arr = (int[])value; 
			            		 if (arr != null)
			            			 myValue = Utility.arrayToString(arr);
			            	 }
							 else myValue = value.toString();
						}
						
						params += "=" + myValue;
	            	 }
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
             }
        }
		
		return params;
	}
	
	public static <T> boolean setParamsToOject(T obj, String params)
	{
		try{
			
			System.out.println("params :" +params);
			if (params == null || params.trim().length() == 0)
				return true;
				
			Field[] declaredFields = obj.getClass().getDeclaredFields();
			setParamsToOject(obj, declaredFields, params);
			
			Field[] supperDeclaredFields = obj.getClass().getSuperclass().getDeclaredFields();
			setParamsToOject(obj, supperDeclaredFields, params);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private static <T> boolean setParamsToOject(T obj, Field[] fieldList, String params)
	{
		try{
			if (params == null || params.trim().length() == 0)
				return true;
				
	        for (Field field : fieldList) 
	        {
	        	try 
	        	{
	        		field.setAccessible(true);
	        		String name = field.getName();
	        		System.out.println("field.getName() "+field.getName());
	        		if(!((field.getModifiers() & java.lang.reflect.Modifier.FINAL) == java.lang.reflect.Modifier.FINAL))
//	        		if(!name.equals("serialVersionUID") && java.lang.reflect.Modifier.isFinal(field.getModifiers()))
	        		if(!name.equals("serialVersionUID")&&!name.equals("splittingData_list")&&!name.toLowerCase().equals("msg"))
//	        			if (type.equals(double.class) || type.equals(float.class) || type.equals(int.class) || type.equals(String.class) 
//	                   		 || type.equals(long.class) || type.equals(Integer.class) || type.equals(Double.class) || type.equals(Float.class) 
//	                   		 || type.equals(char.class) || type.equals(boolean.class))
	        			if (params.contains("&" +name + "="))
		        		{
			        		Object value = Utility.getParameter(params, name);
			        		if (value.equals("null")) value = "";
			        		field.set(obj, GiuDieuKienLoc.convertToValueByClass(value, "" + field.getGenericType()));
		        		}
	        	} catch (Exception e) {
					e.printStackTrace();
				}
	        }
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@SuppressWarnings("unchecked")
	public static <T> T convertToValueByClass(Object obj, String type)
	{
		if (type.equals(int[].class.toString()) || type.equals(Integer[].class.toString())) 
		{
			int[] rs = Utility.stringToIntArr(obj.toString(), ",");
			return (T)rs;
		}
		
		if (type.equals(double.class.toString()) || type.equals(Double.class.toString())) 
		{
			Double rs = Double.parseDouble(obj.toString());
			return (T)rs;
		}
		
		if (type.equals(float.class.toString()) || type.equals(Float.class.toString())) 
		{
			Float rs = Float.parseFloat(obj.toString());
			return (T)rs;
		}
		
		if (type.equals(int.class.toString()) || type.toString().equals(Integer.class.toString())) 
		{
			Integer rs = Integer.parseInt(obj.toString());
			return (T)rs;
		}
		
		if (type.equals(long.class.toString()) || type.equals(Long.class.toString())) 
		{
			Long rs = Long.parseLong(obj.toString());
			return (T)rs;
		}
		
		if (type.equals(long.class.toString()) || type.equals(Long.class.toString())) 
		{
			Long rs = Long.parseLong(obj.toString());
			return (T)rs;
		}
		
		if (type.equals(boolean.class.toString())) 
		{
			Boolean rs = Boolean.parseBoolean(obj.toString());
			return (T)rs;
		}
		
		if (type.equals(String.class.toString()) || type.equals(char.class.toString())) 
		{
			String rs = (obj.toString());
			return (T)rs;
		}
		
		String s = "";
		return (T)s;
	}
	
	//The way to get funtion's name
	//Thread.currentThread().getStackTrace()[1].getMethodName()
	public static <T> boolean getSetDieuKienLoc(HttpSession session, String servletName, String functionName, String action, String userId, T obj)
	{
		try {
			Utility util = new Utility();
			if (action.trim().toLowerCase().equals("chot") || action.trim().toLowerCase().equals("unchot")
					|| action.trim().toLowerCase().equals("duyet") || action.trim().toLowerCase().equals("chuyen")
					|| action.trim().toLowerCase().equals("boduyet") || action.trim().toLowerCase().equals("bochot")
					|| action.trim().toLowerCase().equals("delete") || action.trim().toLowerCase().equals("xoa")
					|| action.trim().toLowerCase().equals("save") || action.trim().toLowerCase().equals("create")
					|| action.trim().toLowerCase().equals("luu") || action.trim().toLowerCase().equals("taomoi")
					|| action.trim().toLowerCase().equals("edit") || action.trim().toLowerCase().equals("chotthue") 
					|| action.trim().toLowerCase().equals("chotvat") ||action.trim().toLowerCase().equals("back")
					)
				//session -params-> obj
			{
				String params = util.getSearchFromHM(userId, servletName, session);
	    		GiuDieuKienLoc.setParamsToOject(obj, params);
			}
			else 
//				if(action.equals("view") || action.equals("next") || action.equals("prev")//doPost svl tổng Phân trang
//					|| action.trim().length() == 0//doGet svl tổng (gọi từ left menu) || doPost svl tổng tìm kiếm
//					)
				//session <-params- obj
				{
					String querySearch = GiuDieuKienLoc.createParams(obj);
					util.setSearchToHM(userId, session, servletName, querySearch);
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}