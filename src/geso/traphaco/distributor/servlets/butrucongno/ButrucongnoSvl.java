package geso.traphaco.distributor.servlets.butrucongno;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.distributor.beans.butrucongno.IButrucongno;
import geso.traphaco.distributor.beans.butrucongno.IButrucongnoList;
import geso.traphaco.distributor.beans.butrucongno.imp.Butrucongno;
import geso.traphaco.distributor.beans.butrucongno.imp.ButrucongnoList;
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

public class ButrucongnoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public ButrucongnoSvl() 
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
		
		IButrucongnoList obj;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
		util.setSearchToHM(userId, session, this.getServletName(), "");
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String xnkhId = util.getId(querystring);
	    
	    //XÓA PHIẾU
	    if (action.equals("delete"))
	    {	   		  	    	
	    	Delete(xnkhId);	    	
	    }
	    //CHỐT PHIẾU
	    else if (action.equals("chot"))
	    {
	    	Chot(xnkhId);
	    }
	   	    
	    obj = new ButrucongnoList();
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/BuTruCongNoKH.jsp";
		response.sendRedirect(nextJSP);
		
		}
	}

	private void Chot(String id) 
	{
		dbutils db = new dbutils(); 
		
		db.update("update BUTRUCONGNO set trangthai = '1' where pk_seq='" + id + "'");
		
		db.update("commit");
		db.shutDown();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IButrucongnoList obj  = new ButrucongnoList();
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
	    	IButrucongno nvgnBean = (IButrucongno) new Butrucongno("");
	    	nvgnBean.setUserId(userId);
	    	nvgnBean.createRS();
	    	
	    	session.setAttribute("nvgnBean", nvgnBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/BuTruCongNoKHNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else
	    {
//	    	String search = this.getSearchQuery(request, obj);
	    	this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
//	    	obj.init(search);
	    	String querySearch = GiuDieuKienLoc.createParams(obj);
			util.setSearchToHM(userId, session,this.getServletName(), querySearch);
			
	    	
	    	obj.init("");
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/BuTruCongNoKH.jsp");	    		    	
	    }
	}
	
	//private String getSearchQuery(HttpServletRequest request, IButrucongnoList obj) 
	private void getSearchQuery(HttpServletRequest request, IButrucongnoList obj) 
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
    	
    	//CÂU SEARCH
//    	String query = " select a.pk_seq as id, a.ngaychungtu , a.trangthai , b.ten as nguoitao, c.ten as nguoisua, a.ngaytao, a.ngaysua \n"+
//					   " from BUTRUCONGNO a inner join NHANVIEN b on a.nguoitao = b.pk_seq \n"+ 
//					   " inner join NHANVIEN c on a.nguoisua = c.pk_seq \n"+					    
//        			   " where a.npp_fk='" + nppId + "'  ";
//    	
//    	if (trangthai.length() > 0)
//    	{
//    		query = query + " and a.trangthai='" + trangthai + "'";
//    	}
//    	if (tungay.length() > 0)
//    	{
//    		query = query + " and a.ngaychungtu >= '" + tungay + "'";
//    	}
//    	if (denngay.length() > 0)
//    	{
//    		query = query + " and a.ngaychungtu <= '" + denngay + "'";
//    	} 
//    	if (sohoadon.length() > 0)
//    	{
//    		query = query + " and a.pk_seq in (select BTCN_FK from BUTRUCONGNO_HOADON where hoadon_fk in ( select pk_seq from hoadon where sohoadon like '%" + sohoadon + "%'  ) )";
//    		query = query + " or a.pk_seq in (select BTCN_FK from BUTRUCONGNO_HOADON where hoadon_fk in ( select pk_seq from erp_hoadonnpp where sohoadon like '%" + sohoadon + "%'  ) )";
//    	}
//    	
//    	query+=" order by a.pk_seq";
//    	
//    	System.out.println("\n SEARCH ________: "+query);		
//    	
//    	
//    	return query;
	}

	private void Delete(String id) 
	{
		dbutils db = new dbutils(); 
		
		db.update("update BUTRUCONGNO set trangthai = '2' where pk_seq='" + id + "'");
		
		db.update("commit");
		db.shutDown();
	}

}
