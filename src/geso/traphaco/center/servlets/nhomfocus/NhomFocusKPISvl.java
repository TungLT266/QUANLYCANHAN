package geso.traphaco.center.servlets.nhomfocus;

import geso.traphaco.center.beans.nhomfocus.INhomfocusKPI;
import geso.traphaco.center.beans.nhomfocus.INhomfocusKPIList;
import geso.traphaco.center.beans.nhomfocus.imp.NhomfocusKPI;
import geso.traphaco.center.beans.nhomfocus.imp.NhomfocusKPIList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class NhomFocusKPISvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NhomFocusKPISvl() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			INhomfocusKPIList obj;
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
		    
		    String nhId = util.getId(querystring);
		    
		    obj = new NhomfocusKPIList();
		    if (action.equals("delete"))
		    {	
		    	String msg = Delete(nhId);
		    	if(msg.length() > 0)
		    		obj.setMsg(msg);
		    }
		    else
		    {
		    	if(action.equals("chot"))
		    	{
		    		String kq=ChotChuyenkho(nhId);
		    		if(kq.length() > 0)
		    		{
		    			obj.setMsg("Chốt không thành công, lỗi: "+ kq);
		    		}
		    	}
		    }
		    
		    obj.setUserId(userId);
		    obj.init(""); 
		    session.setAttribute("obj", obj);
		    String nextJSP = "/TraphacoHYERP/pages/Center/NhomSkufocusKPI.jsp";
    		response.sendRedirect(nextJSP);	
	    }
	
	private String getSearchQuery(HttpServletRequest request, INhomfocusKPIList obj)
	{
		String query="select a.pk_seq,a.trangthai,a.thang,a.nam,a.dvkd_fk,b.diengiai as tendvkd,c.diengiai as tenkbh,a.kbh_fk,a.doituong,NV.TEN as TENNV,a.NGAYSUA,a.NGAYTAO,NV.PK_SEQ as MANV,NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS" +
 		" from nhomfocuskpi a  inner join donvikinhdoanh b on a.dvkd_fk=b.pk_seq inner join kenhbanhang c on a.kbh_fk=c.pk_seq" +
 		" inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where 1=1 " ;
		
		String thang= request.getParameter("thang");
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		
		String nam = request.getParameter("nam");
		if(nam == null)
			nam = "";
		obj.setNam(nam);
		
		String donvikinhdoanh = request.getParameter("donvikinhdoanh");
		if(donvikinhdoanh == null)
			donvikinhdoanh = "";
		obj.setDoituong(donvikinhdoanh);
		
		String kenhbanhang = request.getParameter("kenhbanhang");
		if(kenhbanhang == null)
			kenhbanhang = "";
		obj.setKenhbanhang(kenhbanhang);
		
		String doituong = request.getParameter("doituong");
		if(doituong == null)
			doituong = "";
		obj.setDoituong(doituong);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		if(!thang.equals("0"))
			query += " and a.thang = '" + thang + "'";
		
		if(!nam.equals("0"))
			query += " and a.nam = '" + nam + "'";
		
		if(donvikinhdoanh.length() > 0)
			query += " and a.dvkd_fk ='" + donvikinhdoanh + "'";
		
		if(kenhbanhang.length() > 0)
			query += " and a.kbh_fk = '" + kenhbanhang + "'";
		
		if(doituong.length() > 0)
			query += " and a.doituong = '" + doituong + "'";
		
		if(trangthai.length() > 0)
			query += " and a.trangthai ='" + trangthai + "'";
		
		System.out.println("cau query loc la "+query);
		return query;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		INhomfocusKPIList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	INhomfocusKPI ckBean = new NhomfocusKPI();
	    	ckBean.createRs();
	    	session.setAttribute("ckBean", ckBean);
	    	String nextJSP = "/TraphacoHYERP/pages/Center/NhomfocusKPINew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new NhomfocusKPIList();
		    	String search = getSearchQuery(request, obj);
		    	System.out.println("nxtApprSplitting "+Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	String nextJSP = "/TraphacoHYERP/pages/Center/NhomSkufocusKPI.jsp";
	    		response.sendRedirect(nextJSP);	
		    }
	    	else
	    	{
		    	obj = new NhomfocusKPIList();    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
	    		String nextJSP = "/TraphacoHYERP/pages/Center/NhomSkufocusKPI.jsp";
	    		response.sendRedirect(nextJSP);	
	    	}
	    }
	}
	
	private String Delete(String nhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
				
			if(!db.update("update nhomfocuskpi set trangthai='2' where pk_seq='"+ nhId +"'"))
					{
						db.update("rollback");
						return "update nhomfocuskpi set trangthai='2' where pk_seq='"+ nhId +"'";
					}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			return "Không thể xóa! Lỗi: " + e.getMessage(); 
		}
		
	}
	public String ChotChuyenkho(String nhId) 
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
				
			if(!db.update("update nhomfocuskpi set trangthai='1' where pk_seq='"+ nhId +"'"))
					{
						db.update("rollback");
						return "update nhomfocuskpi set trangthai='1' where pk_seq='"+ nhId +"'";
					}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			return "Không thể xóa! Lỗi: " + e.getMessage(); 
		}
	}
}
