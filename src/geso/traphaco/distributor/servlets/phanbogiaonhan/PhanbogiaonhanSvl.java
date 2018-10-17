package geso.traphaco.distributor.servlets.phanbogiaonhan;

import geso.traphaco.distributor.beans.phanbogiaonhan.IPhanbogiaonhan;
import geso.traphaco.distributor.beans.phanbogiaonhan.IPhanbogiaonhanList;
import geso.traphaco.distributor.beans.phanbogiaonhan.imp.Phanbogiaonhan;
import geso.traphaco.distributor.beans.phanbogiaonhan.imp.PhanbogiaonhanList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PhanbogiaonhanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public PhanbogiaonhanSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		IPhanbogiaonhanList obj;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String nvgnId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(nvgnId);
	    	out.print(nvgnId);
	    }
	   	    
	    obj = new PhanbogiaonhanList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setUserId(userId);
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/PhanBoGiaoNhan.jsp";
		response.sendRedirect(nextJSP);
		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		IPhanbogiaonhanList obj  = new PhanbogiaonhanList();
		PrintWriter out; 
		String userId;
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    Utility util = new Utility();
		
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("new"))
	    {
	    	// Empty Bean for distributor
	    	IPhanbogiaonhan nvgnBean = new Phanbogiaonhan("");
	    	nvgnBean.setUserId(userId);
	    	
	    	nvgnBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	nvgnBean.setDoituongId(session.getAttribute("doituongId"));
	    	
	    	nvgnBean.createRS();
	    	nvgnBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	// Save Data into session
	    	session.setAttribute("nvgnBean", nvgnBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/PhanBoGiaoNhanNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else
	    {
	    	String search = this.getSearchQuery(request, obj);
	    	obj.setCongtyId((String)session.getAttribute("congtyId"));
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/PhanBoGiaoNhan.jsp");	    		    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IPhanbogiaonhanList obj) 
	{	
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String nvgnId = util.antiSQLInspection(request.getParameter("nvgnId"));
    	if ( nvgnId == null)
    		nvgnId = "";
    	obj.setNvgnId(nvgnId);
    	
    	String ngay = util.antiSQLInspection(request.getParameter("ngay"));
    	if (ngay == null)
    		ngay = "";    	
    	obj.setNgay(ngay);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai")); 	
    	if (trangthai == null)
    		trangthai = "";    		
    	if (trangthai.equals("2"))
    		trangthai = "";   	
    	obj.setTrangthai(trangthai);
    	    	
    	String query = "select a.pk_seq, a.diengiai, a.ngay, a.trangthai, a.nhanviengn, a.nhanviengn_moi, d.ten as tennv, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua ";
		query = query + "from phanbogiaonhan a inner join nhanviengiaonhan d on a.nhanviengn = d.pk_seq inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq "
				+ "where 1=1 and congty_fk = '"+obj.getCongtyId()+"' AND A.NPP_FK=" +util.getIdNhapp(obj.getUserId());
    	if (nvgnId.length() > 0)
    	{ 
			query = query + " and  a.nhanviengn = '" + nvgnId + "'";		
    	}
    	
    	if (ngay.length() > 0)
    	{
			query = query + " and a.ngay = '" + ngay + "'";		
    	}
    	
    	if (trangthai.length() > 0)
    	{
    		query = query + " and a.trangthai='" + trangthai + "'";
    	}
    	  	
    	query+=" order by a.pk_seq";
    	System.out.println("\nQuery search fina: "+query);		
    	
    	
    	return query;
	}

	private void Delete(String nvgnId) 
	{
		dbutils db = new dbutils(); 
		
		db.update("delete from Phanbogiaonhan where pk_seq='" + nvgnId + "'");
		
		db.update("commit");
		db.shutDown();
	}

}
