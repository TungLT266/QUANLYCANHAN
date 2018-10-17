package geso.traphaco.erp.servlets.marquette;
import geso.traphaco.center.beans.nganhhang.INganhHang;
import geso.traphaco.center.beans.nganhhang.imp.NganhHang;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.marquette.IMarquette;
import geso.traphaco.erp.beans.marquette.IMarquetteUpdate;
import geso.traphaco.erp.beans.marquette.imp.Marquette;
import geso.traphaco.erp.beans.marquette.imp.MarquetteUpdate;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinsanphamgiay;
import geso.traphaco.erp.beans.thongtinsanphamgiay.imp.Thongtinsanphamgiay;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class MarquetteSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public MarquetteSvl()
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
	    String UserId = util.getUserId(querystring);
	    System.out.println("userid bao nhieu "+UserId);
	    out.println(UserId);
	  
	    if (UserId.length() == 0)
	    	UserId = util.antiSQLInspection(request.getParameter("userId"));
	    System.out.println("userid bao nhieu "+UserId);
	    IMarquette obj = new Marquette();
	    obj.setUserId(UserId);
	    String msg = "";
	    String action = util.getAction(querystring);
	    if (action == null){
	    	action = "";
	    }
	    String mkid = util.getId(querystring);
	    if (action.equals("delete"))
	    {	
		 	 obj.setId(mkid);
			 obj.delete();
			
	    }
	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, UserId, obj);
	    obj.Init();
	    obj.setMsg(msg);
	    session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/MarquetteNew.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	  
	    
	    
	   
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if (action.equals("new"))
	    {
	    	System.out.println("aldk:; "+action);
	        IMarquetteUpdate	obj =  new MarquetteUpdate("");
	    	obj.setUserId(userId);
	    	session.setAttribute("obj", obj);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/MarquetteUpdate.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    
	    IMarquette obj = (IMarquette)new Marquette();
	  
	    if (action.equals("search"))
	    {
	    	obj.setQuery(getSearchQuery(request, obj));
			obj.setUserId(userId);
			GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			obj.Init();
			
	    	session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/MarquetteNew.jsp");	    	
	    	
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.setUserId(userId);
			GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			obj.Init();
			
	    	session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/MarquetteNew.jsp");	  
	    }
	   
	}
	
	private String getSearchQuery(HttpServletRequest request, IMarquette obj) 
	{
		String mamk=request.getParameter("mamk");
		if (mamk == null)
			mamk= "";
    	obj.setMarket(mamk);
		String masp = request.getParameter("masp");
    	if (masp == null)
    		masp = "";
    	obj.setMasp(masp);

		String tensp = request.getParameter("tensp");
    	if (tensp == null)
    		tensp = "";
    	obj.setTensp(tensp);
    	
    	String dvkdId = request.getParameter("dvkdId");
    	if (dvkdId == null)
    		dvkdId = "";    	
    	obj.setDvkdId(dvkdId);
    	
    	String nhId = request.getParameter("nhId");
    	if (nhId == null)
    		nhId = "";    	
    	obj.setNhanhangId(nhId);
    	
    	String clId = request.getParameter("clId");
    	if (clId == null)
    		clId = "";    	
    	obj.setChungloaiId(clId);
    	
    	String lspId = request.getParameter("lspId");
    	if (lspId == null)
    		lspId = "";    	
    	obj.setLoaispId(lspId);
    	
    	String trangthai = request.getParameter("trangthai");   	
    	if (trangthai == null)
    		trangthai = "";    	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	String query = " select ma.pk_seq,ma.ma,ma.DIENGIAI,ma.TRANGTHAI,ma.DENNGAY "+
    				   " from marquette ma left join erp_sanpham a on ma.sanpham_fk=a.PK_SEQ "+
    				   " left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq "+
    				   " left join erp_loaisanpham lsp on lsp.pk_seq = a.loaisanpham_fk  left join chungloai c on a.chungloai_fk = c.pk_seq "+  
    				   " left join donvidoluong d on a.dvdl_fk = d.pk_seq  left join nhanhang e on a.nhanhang_fk = e.pk_seq where 1=1";
        
    	if (mamk.length()>0){
			query = query + " and upper(ma.ma) like upper('%" + mamk + "%')";	
    	}

    	if (masp.length()>0){
			query = query + " and upper(a.ma) like upper('%" + masp + "%')";	
    	}

	    if (tensp.length()>0){
	    	   Utility util = new Utility();
			query = query + " and upper(a.ten) like upper(N'%"+ util.replaceAEIOU(tensp) + "%')";	
    	}
	    
	    if(lspId.length() > 0)
	    {
	    	query = query + " and a.loaisanpham_fk = '" + lspId + "' ";	
	    }
    	
		if (dvkdId.length()>0)
    	{
			query = query + " and a.dvkd_fk = '" + dvkdId + "'";	
    	}
		if (nhId.length()>0)
    	{
			query = query + " and a.dvkd_fk = '" + nhId + "'";	
    	}
		if (clId.length()>0)
    	{
			query = query + " and a.dvkd_fk = '" + clId + "'";	
    	}

    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";   		
    	}
 
    	return query;
    	
		
	}
}
