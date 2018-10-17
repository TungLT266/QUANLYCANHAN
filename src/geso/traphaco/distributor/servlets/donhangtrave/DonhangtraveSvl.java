package geso.traphaco.distributor.servlets.donhangtrave;

import geso.traphaco.distributor.beans.donhangtrave.*;
import geso.traphaco.distributor.beans.donhangtrave.imp.*;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DonhangtraveSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	IDonhangtraveList obj;
	PrintWriter out; 
	String userId;
	
    public DonhangtraveSvl() 
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

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String dhtvId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(dhtvId);
	    	out.print(dhtvId);
	    }
	    
	    if (action.equals("chotdh")){
	    	ChotDonHang(dhtvId);
	    }
	   	    
	    obj = new DonhangtraveList();
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("tbhId", "");
		session.setAttribute("khId", "");
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangTraVe.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    Utility util = new Utility();
		HttpSession session = request.getSession();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action1");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("taomoi")){
	    	// Empty Bean for distributor
	    	IDonhangtrave dhtvBean = (IDonhangtrave) new Donhangtrave("");
	    	dhtvBean.setUserId(userId);
	    	dhtvBean.createRS();
	    	// Save Data into session
	    	
	    	session.setAttribute("nppId", dhtvBean.getNppId());
	    	session.setAttribute("dhtvBean", dhtvBean);
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangTraVeNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else if(action.equals("guilai")){    
	 	
	    }	
	    else{
	    	String search = getSearchQuery(request);
	    	//search = search + " and a.npp_fk='" + userId + "' order by a.ten";
	    	//obj = new KhachhangList(search);
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/DonHangTraVe.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request) 
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
    	
    	String query = "select a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, a.VAT";
		query = query + " from donhangtrave a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
		query = query + " where a.npp_fk = '" + nppId + "'";
    	
    	if (ddkdId.length()>0)
    	{
			query = query + " and e.pk_seq = '" + ddkdId + "'";		
    	}
    	
    	if (trangthai.length()>0){
			query = query + " and a.trangthai ='" + trangthai + "'";
			
    	}
    	
    	if (tungay.length()>0){
			query = query + " and a.ngaynhap > '" + tungay + "'";
			
    	}
    	
    	if (denngay.length()>0){
			query = query + " and a.ngaynhap < '" + denngay + "'";
			
    	}
    	query = query + " order by a.pk_seq";
    	return query;
	}

	private void Delete(String id)
	{
		dbutils db = new dbutils();
		String sql = "update donhangtrave set trangthai='2' where pk_seq= '" + id + "'";
		if(!db.update(sql))
		{
			db.update("rollback");			
			System.out.println("Loi : "+sql);
			db.shutDown();
		}	
		
	}
	
	private void ChotDonHang(String id) 
	{
		dbutils db = new dbutils();
		db.update("update donhangtrave set trangthai='1' where pk_seq= '" + id + "'");
		db.update("commit");
		db.shutDown();
	}

}

