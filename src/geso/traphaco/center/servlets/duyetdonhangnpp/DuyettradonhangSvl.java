package geso.traphaco.center.servlets.duyetdonhangnpp;

import geso.traphaco.center.beans.duyettradonhang.IDuyettradonhang;
import geso.traphaco.center.beans.duyettradonhang.IDuyettradonhangList;
import geso.traphaco.center.beans.duyettradonhang.imp.Duyettradonhang;
import geso.traphaco.center.beans.duyettradonhang.imp.DuyettradonhangList;
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

public class DuyettradonhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	
   	
    public DuyettradonhangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IDuyettradonhangList obj;
	   	PrintWriter out; 
	   	String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
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

	    obj = new DuyettradonhangList();
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/DuyetDonHangTra.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDuyettradonhangList obj = new DuyettradonhangList();
	   	PrintWriter out; 
	   	String userId;
		
	   	Utility util = new Utility();
	   	
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("taomoi")){
	    	IDuyettradonhang dtdhBean = (IDuyettradonhang) new Duyettradonhang("");
	    	dtdhBean.setUserId(userId);
	    	dtdhBean.createRS();

	    	session.setAttribute("dtdhBean", dtdhBean);
    		String nextJSP = "/TraphacoHYERP/pages/Center/DuyetDonHangTraNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else{
	    	String search = getSearchQuery(request, obj);
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/DuyetDonHangTra.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IDuyettradonhangList obj) 
	{
		
	   	PrintWriter out; 
	   	String userId;
	   	Utility util = new Utility();
		
    	String nppId = util.antiSQLInspection(request.getParameter("nppTen"));
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
    	
    	String query = "select a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, isnull(b.ten, '') as nguoiduyet, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, f.pk_seq as nppId, a.VAT, isnull(a.ngayduyet, '') as ngayduyet";
		query = query + " from donhangtrave a left join nhanvien b on a.nguoiduyet = b.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
		query = query + " where a.trangthai in (1, 3) and a.donhang_fk is not null ";
    	
    	if (nppId.length() > 0)
    	{
			query = query + " and a.npp_fk = '" + nppId + "'";		
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
    	query = query + " order by a.pk_seq desc";
    	return query;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
