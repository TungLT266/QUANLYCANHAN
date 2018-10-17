package geso.traphaco.distributor.servlets.huynhaphang;

import geso.traphaco.distributor.beans.huynhaphang.*;
import geso.traphaco.distributor.beans.huynhaphang.imp.*;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HuynhaphangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
 
	
    public HuynhaphangSvl() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
 
	    	    
	    String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
	    
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
/*	    String action = util.getAction(querystring);
   
	    String dhtvId = util.getId(querystring);
 */
	    IHuynhaphangList obj;
	    obj = new HuynhaphangList();
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("tbhId", "");
		session.setAttribute("khId", "");
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/Huynhaphang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	  
	    Utility util = new Utility();
		HttpSession session = request.getSession();
		String   userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action1");
	    if (action == null){
	    	action = "";
	    }
	  
	          
	    if (action.equals("taomoi")){
	  
	    	IHuynhaphang dhtvBean = new Huynhaphang("");
	    	dhtvBean.setUserId(userId);
	    	dhtvBean.createRS();
	    	 
	    	session.setAttribute("nppId", dhtvBean.getNppId());
	    	session.setAttribute("dhtvBean", dhtvBean);
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/HuynhaphangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    	
	    else{
	    	IHuynhaphangList obj= new HuynhaphangList();
	    	String search = getSearchQuery(request,obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/Huynhaphang.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IHuynhaphangList obj) 
	{
		Utility util = new Utility();
		String ddkdId = util.antiSQLInspection(request.getParameter("ddkdTen"));
    	if ( ddkdId == null)
    		ddkdId = "";
    	obj.setDdkdId(ddkdId);
    	
    	String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "";    	
    	obj.setTrangthai(trangthai);
    	
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
		String query = " select a.pk_seq  , a.ngaychot , a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua " + 
		 " from huynhaphang a inner join nhanvien b on a.nguoitao = b.pk_seq " +
		 "  inner join nhanvien c on a.nguoisua = c.pk_seq " +
		 " inner join nhaphanphoi f on a.npp_fk = f.pk_seq "+
		 " where a.npp_fk = " + nppId ;
    	
    	
    	
    	if (trangthai.length()>0){
			query = query + " and a.trangthai ='" + trangthai + "'";
			
    	}
    	
    	if (tungay.length()>0){
			query = query + " and a.ngaychot > '" + tungay + "'";
			
    	}
    	
    	if (denngay.length()>0){
			query = query + " and a.ngaychot < '" + denngay + "'";
			
    	}
    	query = query + " order by a.pk_seq";
    	return query;
	}
 

}

