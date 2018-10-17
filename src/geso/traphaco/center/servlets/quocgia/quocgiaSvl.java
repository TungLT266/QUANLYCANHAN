package geso.traphaco.center.servlets.quocgia;

import geso.traphaco.center.beans.quocgia.Iquocgia;
import geso.traphaco.center.beans.quocgia.imp.QuocGia;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class quocgiaSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public quocgiaSvl()
	    {
	        super();
	    }
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
			
			Iquocgia obj;
			
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    //this.out  = response.getWriter();
		    	    
		    HttpSession session = request.getSession();	    
		    obj = new QuocGia();
		    Utility util = new Utility();
		    	    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    String update = request.getParameter("update");
		    if(update != null)
		    {
		    	System.out.println("up "+update);
		    	obj.setId(update);
		    	obj.init("update");
				session.setAttribute("obj", obj);
		    	String nextJSP = "/TraphacoHYERP/pages/Center/QuocGiaUpdate.jsp";
				response.sendRedirect(nextJSP);
		    }
		    else
		    {
		    	String action = util.getAction(querystring);
		    
		    	String kvId = util.getId(querystring);
		    	String delete = request.getParameter("delete");
		    	if (action.equals("delete"))
		    	{	   		  	    	
		    		dbutils db = new dbutils();
					String sql ="delete from quocgia  where pk_seq="+ delete + "";
					if(!db.update(sql))
					{
						obj.setMessage("Lỗi không xóa được");
			
					}
					else
						obj.setMessage("Xóa thành công");
		    	}
		    	obj.setUserId(userId);
		    	obj.init("");
		    	session.setAttribute("obj", obj);
		    	String nextJSP = "/TraphacoHYERP/pages/Center/QuocGia.jsp";
		    	response.sendRedirect(nextJSP);
		    }
		}

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			
			Iquocgia obj = new QuocGia();
			Utility util = new Utility();
			dbutils db = new dbutils();
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    //this.out = response.getWriter();
		    
			HttpSession session = request.getSession();
		    String userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = "";
		    }
		    
		    String quocgia = request.getParameter("quocgia");
		    if(quocgia == null)
		    	quocgia = "";
		    obj.setTen(quocgia);
		    
		    String diengiai = request.getParameter("diengiai");
		    if(diengiai == null)
		    	diengiai = "";
		    obj.setDiengiai(diengiai);
		    
		    String trangthai = request.getParameter("trangthai");
		    System.out.println("Trang thai "+trangthai);
		    if(trangthai == null)
		    	trangthai = "0";
		    else
		    	trangthai = "1";
		    obj.setTrangthai(trangthai);
		    String id = request.getParameter("id");
		    if(id == null)
		    	id = "";
		    obj.setId(id);
		    
		    if (action.equals("new"))
		    {
		    	// Empty Bean for distributor
		    	Iquocgia obj1 = (Iquocgia) new QuocGia();
		    	obj1.setUserId(userId);
		    	// Save Data into session
		    	session.setAttribute("obj", obj1);
	    		
	    		String nextJSP = "/TraphacoHYERP/pages/Center/QuocGiaNew.jsp";
	    		response.sendRedirect(nextJSP);
	    		
		    }
		    
		    if (action.equals("search")){
		    	String search = getSearchQuery(request,obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
	    		session.setAttribute("abc", search);
		    		
	    		response.sendRedirect("/TraphacoHYERP/pages/Center/QuocGia.jsp");	    	
		    	
		    }
		    else
		    	if(action.equals("savenew"))
		    	{
		    		boolean kt = true;
		    		String slqkt = "select 1 from quocgia where tenqg = N'"+quocgia+"'";
		    		ResultSet rskt = db.get(slqkt);
		    		try {
						if(rskt.next())
						{
							kt = false;
							rskt.close();
						}
					} catch (SQLException e) 
		    		{
						
						e.printStackTrace();
					}
		    		
						if(kt)
						{
						
						String sql  = "Insert into QUOCGIA values(N'"+quocgia+"', N'"+diengiai+"', '"+getDateTime()+"','"+getDateTime()+"', "+userId+", "+userId+", '"+trangthai+"')";
						
						if(!db.update(sql))
						{
							obj.setMessage("Lỗi không thêm được quốc gia "+sql);
							session.setAttribute("obj", obj);
							String nextJSP = "/TraphacoHYERP/pages/Center/QuocGiaNew.jsp";
							response.sendRedirect(nextJSP);
						}
						else
						{
							obj.setMessage("Thêm quốc gia thành công");
							session.setAttribute("obj", obj);
							String nextJSP = "/TraphacoHYERP/pages/Center/QuocGiaNew.jsp";
							response.sendRedirect(nextJSP);
						}
						}
						else
						{
							obj.setMessage("Quốc gia đã tồn tại");
							session.setAttribute("obj", obj);
							String nextJSP = "/TraphacoHYERP/pages/Center/QuocGiaNew.jsp";
							response.sendRedirect(nextJSP);
						}
					
		    	}else
		    		if(action.equals("saveupdate"))
			    	{
			    		
			    		if(!obj.update())
			    		{
			    			obj.setMessage("Lỗi không sửa được quốc gia ");
			    			session.setAttribute("obj", obj);
			    			String nextJSP = "/TraphacoHYERP/pages/Center/QuocGiaUpdate.jsp";
				    		response.sendRedirect(nextJSP);
			    		}
			    		else
			    		{
			    			obj.setMessage("Sửa quốc gia thành công");
			    			session.setAttribute("obj", obj);
			    			String nextJSP = "/TraphacoHYERP/pages/Center/QuocGiaUpdate.jsp";
				    		response.sendRedirect(nextJSP);
			    		}
			    	}
		    
		}
		private String getSearchQuery(HttpServletRequest request,Iquocgia obj)
		{
			
			Utility util = new Utility();
			
		    String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));   	
		    	if (trangthai == null)
		    		trangthai = "";    	
			String TenQg = request.getParameter("TenQg");
				if(TenQg == null)
					TenQg = "";
			obj.setTen(TenQg);
		   if (trangthai.equals("2"))
			   trangthai = "";
		   obj.setTrangthai(trangthai);
		    	
		 
				String query = "Select * from quocgia where 1 = 1";
				
				if(TenQg.length() > 0)
				query += " and tenqg like N'%"+TenQg+"%'";
				if(trangthai.length() > 0)
				query = query + " and trangthai = "+trangthai;
		    	query = query + " order by tenqg";
		    	System.out.println("cau lenh:"+ query);
		    	return query;
			}	

		
		private String getDateTime()
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			return dateFormat.format(date);
		}

}
