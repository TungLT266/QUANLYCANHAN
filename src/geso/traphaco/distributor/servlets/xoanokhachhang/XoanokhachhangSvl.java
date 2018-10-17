package geso.traphaco.distributor.servlets.xoanokhachhang;

import geso.traphaco.distributor.beans.xoanokhachhang.IXoanokhachhang;
import geso.traphaco.distributor.beans.xoanokhachhang.IXoanokhachhangList;
import geso.traphaco.distributor.beans.xoanokhachhang.imp.Xoanokhachhang;
import geso.traphaco.distributor.beans.xoanokhachhang.imp.XoanokhachhangList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class XoanokhachhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public XoanokhachhangSvl() 
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
		
		IXoanokhachhangList obj;
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
	    
	    String xnkhId = util.getId(querystring);

	    if (action.equals("delete"))
	    {	   		  	    	
	    	Delete(xnkhId);
	    }else if (action.equals("chot"))
	    {
	    	Chot(xnkhId);
	    }
	   	    
	    obj = new XoanokhachhangList();
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/XoaNoKhachHang.jsp";
		response.sendRedirect(nextJSP);
		
		}
	}

	private void Chot(String id) 
	{
		dbutils db = new dbutils(); 
		
		db.update("update XOANOKHACHHANG set trangthai = '1' where pk_seq='" + id + "'");
		
		db.update("commit");
		db.shutDown();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IXoanokhachhangList obj  = new XoanokhachhangList();
		PrintWriter out; 
		String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    Utility util = new Utility();
		HttpSession session = request.getSession();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("new"))
	    {
	    	IXoanokhachhang nvgnBean = (IXoanokhachhang) new Xoanokhachhang("");
	    	nvgnBean.setUserId(userId);
	    	nvgnBean.createRS();
	    	
	    	session.setAttribute("nvgnBean", nvgnBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/XoaNoKhachHangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else
	    {
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/XoaNoKhachHang.jsp");	    		    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IXoanokhachhangList obj) 
	{	
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if ( tungay == null)
    		tungay = "";
    	obj.setTungay(tungay);
    	
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if ( denngay == null)
    		denngay = "";
    	obj.setDenngay(denngay);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai")); 	
    	if (trangthai == null)
    		trangthai = "";    			
    	obj.setTrangthai(trangthai);
    	
    	String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon")); 	
    	if (sohoadon == null)
    		sohoadon = "";    			
    	obj.setSohoadon(sohoadon);
    	    	
    	String query = " select a.pk_seq as id, a.ngaychungtu , a.trangthai , b.ten as nguoitao, c.ten as nguoisua, a.ngaytao, a.ngaysua "+
        " from xoanokhachhang a inner join nhanvien b on a.nguoitao = b.pk_seq " +
        "      inner join nhanvien c on a.nguoisua = c.pk_seq" +
        " where a.npp_fk='" + nppId + "'  ";
    	
    	
    	if (trangthai.length() > 0)
    	{
    		query = query + " and a.trangthai='" + trangthai + "'";
    	}
    	if (tungay.length() > 0)
    	{
    		query = query + " and a.ngaychungtu >= '" + tungay + "'";
    	}
    	if (denngay.length() > 0)
    	{
    		query = query + " and a.ngaychungtu <= '" + denngay + "'";
    	} 
    	if (sohoadon.length() > 0)
    	{
    		query = query + " and a.pk_seq in (select xnkh_fk from xoanokhachhang_hoadon where hoadon_fk in ( select pk_seq from hoadon where sohoadon like '%" + sohoadon + "%'  ) )";
    		query = query + " or a.pk_seq in (select xnkh_fk from xoanokhachhang_hoadon where hoadon_fk in ( select pk_seq from erp_hoadonnpp where sohoadon like '%" + sohoadon + "%'  ) )";
    	}
    	
    	query+=" order by a.pk_seq";
    	
    	System.out.println("\nQuery search fina: "+query);		
    	
    	
    	return query;
	}

	private void Delete(String id) 
	{
		dbutils db = new dbutils(); 
		
		db.update("update XOANOKHACHHANG set trangthai = '2' where pk_seq='" + id + "'");
		
		db.update("commit");
		db.shutDown();
	}

}
