package geso.traphaco.erp.servlets.vuviec;

import geso.traphaco.erp.beans.vuviec.*;
import geso.traphaco.erp.beans.vuviec.imp.*;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class VuviecSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	IVuviecList obj;
	PrintWriter out;
	
    public VuviecSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new VuviecList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String dmplId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(dmplId);
	    	out.print(dmplId);
	    } else if (action.equals("hoantat")){	   		  	    	
	    	HoanTat(dmplId);
	    	out.print(dmplId);
	    } else if (action.equals("chot")){	   		  	    	
	    	Chot(dmplId);
	    	out.print(dmplId);
	    }
	   	
	   
	    obj.setUserId(userId);
	    obj.setCongty(ctyId);
		session.setAttribute("obj", obj);
		obj.init("");
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/VuViecList.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    String userId = request.getParameter("userId");
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	        
	    
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	IVuviec dmplBean = (IVuviec) new Vuviec("");
	    	dmplBean.setUserId(userId);
	    	dmplBean.setCongty(ctyId);
	    	// Save Data into session
	    	session.setAttribute("dmplBean", dmplBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Erp/VuViecNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search")){
	    	obj = new VuviecList();
	    	String search = getSearchQuery(request);
	    	
	    	obj.init(search);
			obj.setUserId(userId);
			obj.setCongty(ctyId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/VuViecList.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request)
	{
		   // PrintWriter out = response.getWriter();
			
			String ma = request.getParameter("ma");
	    	if ( ma == null)
	    		ma = "";
	    	obj.setMa(ma );
	    	
	    	String diengiai = request.getParameter("DienGiai");
	    	if (diengiai == null)
	    		diengiai = "";    	
	    	obj.setDiengiai(diengiai);
	    	
	    	String trangthai = request.getParameter("TrangThai");   	
	    	if (trangthai == null)
	    		trangthai = "";    	    	
	    	obj.setTrangthai(trangthai);
	    		    	
			String query = "select a.pk_seq as id, a.ten as vvTen, a.ma, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua "+
					       " from vuviec a, nhanvien b, nhanvien c "+
						   " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq "; 
			
	    	if (ma.length()>0){
	    	
				query = query + " and upper(dbo.ftBoDau(a.ma)) like upper(N'%" + ma + "%')";
				
	    	}
	    	
	    	if (diengiai.length()>0){
				query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + diengiai + "%')";
				
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = '" + trangthai + "'";
	    		
	    	}
	    	query = query + "  order by a.ma";
	    	return query;
		}	
	
	boolean kiemtra(String sql)
	{dbutils db =new dbutils();
    	ResultSet rs = db.get(sql);
		try {//kiem tra ben san pham
		while(rs.next())
		{ if(rs.getString("num").equals("0"))
		   return true;
		}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		 return false;
	}
	
	private void Delete(String id)
	{
		dbutils db = new dbutils();
		 db.update("update vuviec set trangthai = 3 where pk_seq = '" + id + "'");
			db.shutDown();	

	}
	
	private void HoanTat(String id)
	{
		dbutils db = new dbutils();
 
	   db.update("update vuviec set trangthai = '2' where pk_seq = '" + id + "'");
	   db.shutDown();		   

	}
	
	private void Chot(String id)
	{
		dbutils db = new dbutils();
 
	   db.update("update vuviec set trangthai = '1' where pk_seq = '" + id + "'");
	   db.shutDown();		   


	}

}
