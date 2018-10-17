package geso.traphaco.erp.servlets.kehoachkinhdoanh;

import geso.traphaco.erp.beans.kehoachkinhdoanh.*;
import geso.traphaco.erp.beans.kehoachkinhdoanh.imp.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKehoachkinhdoanhSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpKehoachkinhdoanhSvl() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpKehoachkinhdoanhList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String dmhId = util.getId(querystring);
	    String loai = util.antiSQLInspection(request.getParameter("loai"));
	    String loaikh = request.getParameter("loaikh");
	    String msg = "";
	    if (action.equals("delete"))
	    {	
	    	msg = Delete(dmhId,userId);
	        obj = new ErpKehoachkinhdoanhList();
		    obj.setUserId(userId);
		    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    obj.setLoai(loai);
		    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    if(msg.length() > 0) obj.setmsg(msg);
		    
		    obj.init("");
		    
			session.setAttribute("obj", obj);
					
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanh.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else if(action.equals("chot"))
		    	{
		    		dbutils db = new dbutils();
		    		db.update(" update ERP_kehoachkinhdoanh set trangthai = '1' where pk_seq = '" + dmhId + "' and trangthai=0");
		    		db.shutDown();
		    		loai = loaikh;
		    	    obj = new ErpKehoachkinhdoanhList();
		    	    obj.setUserId(userId);
		    	    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	    obj.setLoai(loai);
		    	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	    if(msg.length() > 0) obj.setmsg(msg);
		    	    
		    	    obj.init("");
		    	    
		    		session.setAttribute("obj", obj);
		    				
		    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanh.jsp";
		    		response.sendRedirect(nextJSP);
		    	}
	    		
	    		/*if(action.equals("unchot"))
		    	{
		    		dbutils db = new dbutils();
		    		db.update("update ERP_kehoachkinhdoanh set trangthai = '0' where pk_seq = '" + dmhId + "'");
		    		db.shutDown();
		    	}*/
	    
	else{

	    obj = new ErpKehoachkinhdoanhList();
	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setLoai(loai);
	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    if(msg.length() > 0) obj.setmsg(msg);
	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanh.jsp";
		response.sendRedirect(nextJSP);
	}
}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpKehoachkinhdoanhList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String loai = util.antiSQLInspection(request.getParameter("loai")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	String msg = Kiemtratontai(loai);
	    	if(msg.length() > 0)
	    	{
	    		obj = new ErpKehoachkinhdoanhList();
	    		obj.setmsg(msg);
	    		obj.setLoai(loai);
	    		obj.setUserId(userId);
    		    obj.setCongtyId((String)session.getAttribute("congtyId"));
    		    if(msg.length() > 0) obj.setmsg(msg);
    		    
    		    obj.init("");
    		    
    			session.setAttribute("obj", obj);
    					
    			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanh.jsp";
    			response.sendRedirect(nextJSP);
	    	}
	    	else
	    	{
		    	IErpKehoachkinhdoanh dmhBean = new ErpKehoachkinhdoanh();
		    	dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
		    	dmhBean.setUserId(userId);
		    	dmhBean.setLoai(loai);
		    	dmhBean.createRs();
		    	session.setAttribute("noibo", "");
		    	
		    	session.setAttribute("dmhBean", dmhBean);
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanhNew.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpKehoachkinhdoanhList();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setLoai(loai);
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

	    		this.getSearchQuery(request, obj);
		    	
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanh.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpKehoachkinhdoanhList();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	obj.setLoai(loai);
		    	this.getSearchQuery(request, obj);
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKeHoachKinhDoanh.jsp");  
	    	}
	    }
	    
	}

	private void getSearchQuery(HttpServletRequest request, IErpKehoachkinhdoanhList obj)
	{
		Utility util = new Utility();
		
		geso.traphaco.center.db.sql.dbutils db = new geso.traphaco.center.db.sql.dbutils();
		
		String nam = request.getParameter("nam");
		if(nam == null)
			nam = "";
		obj.setNam(nam);

//		String query = " select a.PK_SEQ as id, a.nam , a.diengiai,\n " +
//				" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua \n " +
//				" from erp_kehoachkinhdoanh a  \n " +
//				" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n " +
//					" where  a.congty_fk = '" + obj.getCongtyId() + "' and a.loai = '"+obj.getLoai()+"'\n " ;
//		
//		if(nam.length() > 0)
//			query += " and a.nam = '" + nam + "'";
//		
//		System.out.println("cau search "+query);
//
//		return query;
	}

	private String Delete(String dmhId,String userId)
	{
		dbutils db = new dbutils();
		String query="";
		try 
		{
			db.getConnection().setAutoCommit(false);
				
			query = "delete erp_kehoachkinhdoanh_chitiet where khkd_fk = '"+dmhId+"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa erp_kehoachkinhdoanh_chitiet: " + query;
			}
			
			query = "delete erp_kehoachkinhdoanh where pk_seq = '"+dmhId+"' and trangthai=0";
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Không thể xóa erp_kehoachkinhdoanh: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			db.shutDown(); 
			return "Loi-khong the xoa ke hoach kinh doanh: "+query; 
		}		
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String Kiemtratontai(String loai)
	{
		dbutils db = new dbutils();
		String query="";
		try 
		{
			String ngayhientai = getDateTime();
			query = "select * from erp_kehoachkinhdoanh where nam = '"+ngayhientai.substring(0, 4)+"' and loai = '"+loai+"'"; 
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				return "Nam "+ngayhientai.substring(0, 4)+" da co ke hoach kinh doanh. Khong the tao moi";
			}
			else
				return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			db.shutDown(); 
			return "Loi-khong the xoa kiem tra ke hoach kinh doanh da tao: "+query; 
		}		
	}
}
