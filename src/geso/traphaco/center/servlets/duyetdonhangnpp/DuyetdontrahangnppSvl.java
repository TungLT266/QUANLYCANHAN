package geso.traphaco.center.servlets.duyetdonhangnpp;

import geso.traphaco.center.beans.duyetdontrahangnpp.imp.Duyetdontrahangnpp;
import geso.traphaco.distributor.beans.donhangtrave.IDonhangtrave;
import geso.traphaco.distributor.beans.donhangtrave.IDonhangtraveList;
import geso.traphaco.distributor.beans.donhangtrave.imp.Donhangtrave;
import geso.traphaco.distributor.beans.donhangtrave.imp.DonhangtraveList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DuyetdontrahangnppSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
       public DuyetdontrahangnppSvl() {
        super();
       
    }
    IDonhangtraveList obj;
   	PrintWriter out; 
   	String userId;


		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
		    	userId = request.getParameter("userId");
		    
		    String action = util.getAction(querystring);
		    out.println(action);
		    
		    String dhtvId = util.getId(querystring);
		    if (action.equals("chot")){	   		  	    	
		      dbutils db = new dbutils();
		      db.update("update donhangtrave set trangthai = 2,nguoisua ='"+ userId +"',ngaysua ='"+ getDateTime() +"' where pk_seq ='"+ dhtvId +"'");
		      
		      
		    }
		    
		    obj = new Duyetdontrahangnpp();
		    obj.setUserId(userId);
		    obj.init("");
		    
			session.setAttribute("obj", obj);
			session.setAttribute("tbhId", "");
			session.setAttribute("khId", "");
					
			String nextJSP = "/TraphacoHYERP/pages/Center/DuyetTraDonHang.jsp";
			response.sendRedirect(nextJSP);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    userId = request.getParameter("userId");
	    
	    String action = request.getParameter("action1");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	  
	    	String search = getSearchQuery(request);
	    	//search = search + " and a.npp_fk='" + userId + "' order by a.ten";
	    	//obj = new KhachhangList(search);
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/DuyetTraDonHang.jsp");	    	
	    	
	  
	}
	
	private String getSearchQuery(HttpServletRequest request) 
	{
		String ddkdId = request.getParameter("ddkdTen");
    	if ( ddkdId == null)
    		ddkdId = "";
    	obj.setDdkdId(ddkdId);
    	
    	String nppId = request.getParameter("nppId");
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String trangthai = request.getParameter("trangthai");
    	if (trangthai == null)
    		trangthai = "";    	
    	obj.setTrangthai(trangthai);
    	
    	String tungay = request.getParameter("tungay");
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = request.getParameter("denngay");
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String query = "select a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, a.VAT";
		query = query + " from donhangtrave a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
		query = query + " where a.trangthai <> '0'";
    	
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
		db.update("update donhangtrave set trangthai='2' where pk_seq= '" + id + "'");
		db.update("commit");
		db.shutDown();
		
	}
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
