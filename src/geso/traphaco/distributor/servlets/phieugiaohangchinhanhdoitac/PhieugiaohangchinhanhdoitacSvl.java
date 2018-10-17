package geso.traphaco.distributor.servlets.phieugiaohangchinhanhdoitac;

import geso.traphaco.distributor.beans.phieugiaohangchinhanhdoitac.IPhieugiaohangchinhanhdoitacList;
import geso.traphaco.distributor.beans.phieugiaohangchinhanhdoitac.imp.PhieugiaohangchinhanhdoitacList;
import geso.traphaco.distributor.beans.phieugiaohangkm.IPhieugiaohangkmList;
import geso.traphaco.distributor.beans.phieugiaohangkm.imp.PhieugiaohangkmList;

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

public class PhieugiaohangchinhanhdoitacSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public PhieugiaohangchinhanhdoitacSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IPhieugiaohangchinhanhdoitacList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    
    	String lsxId = util.getId(querystring);
	    obj = new PhieugiaohangchinhanhdoitacList();
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/PhieuGiaoHangChiNhanhDoiTac.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    	    
	    
		IPhieugiaohangchinhanhdoitacList obj = new PhieugiaohangchinhanhdoitacList();

		  String sohoadon = request.getParameter("sohoadon");
		     if(sohoadon == null)
		    	 sohoadon = "";
		     obj.setSoHoaDon(sohoadon);
		     
		     String khTen = request.getParameter("khTen");
		     if(khTen == null)
		      khTen = "";
		     obj.setKhTen(khTen);
		     
		     String tungay = request.getParameter("tungay");
		     if(tungay == null)
		      tungay = "";
		     obj.setTungay(tungay);
		     
		     String denngay = request.getParameter("denngay");
		     if(denngay == null)
		      denngay = "";
		     obj.setDenngay(denngay);
		     
		     String trangthai = request.getParameter("trangthai");
		     if(trangthai == null)
		    	 trangthai = "";
		     obj.setTrangthai(trangthai);
		     
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 

    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
	    	String search = getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Distributor/PhieuGiaoHangChiNhanhDoiTac.jsp";
			response.sendRedirect(nextJSP);
	    }
    	else
    	{
	    	String search = getSearchQuery(request, obj);
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		obj.setUserId(userId);
	    	obj.init(search);								
	    	
	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/PhieuGiaoHangChiNhanhDoiTac.jsp";
    		response.sendRedirect(nextJSP);
    	}
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, IPhieugiaohangchinhanhdoitacList obj)
	{
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSoHoaDon(sohoadon);
		
		String trangthai = request.getParameter("trangthai");
	     if(trangthai == null)
	    	 trangthai = "";
	     obj.setTrangthai(trangthai);
	     
		String query =
			"SELECT a.PK_SEQ, isnull(a.dainPGH,0) trangthai, a.ngayxuatHD, a.sohoadon + a.kyhieu as sohoadon, NV.TEN as nguoitao, a.tongtienavat as tongtien , \n " +
			"		case when a.NPP_DAT_FK is not null then npp.TEN else KH.TEN end as khTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua  \n " +
			"FROM 	ERP_HOADONNPP a \n " +
			"		LEFT JOIN KHACHHANG KH on a.KHACHHANG_FK=KH.PK_SEQ  \n " +
			"		LEFT JOIN NHAPHANPHOI npp on a.NPP_DAT_FK=npp.PK_SEQ  \n " +
			"		INNER JOIN NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ  \n  " +
			"		INNER JOIN NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ \n " +
			"WHERE 	a.pk_seq > 0 and a.npp_fk ='"+ nppId +"' and a.trangthai not in (1, 3, 5) \n ";

		
		String khten = request.getParameter("khTen");		
		if(khten == null)
			khten = "";
		obj.setKhTen(khten);
				
		
		if(tungay.length() > 0)
			query += " and a.ngayxuatHD >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayxuatHD <= '" + denngay + "'";
		
		if(khten.length() > 0)
			query += " and ( kh.pk_seq ='" + khten + "' or a.npp_dat_fk = '" + khten + "' ) ";
		
		if(sohoadon.length() > 0)
			query += " and a.SOHOADON like '%"+sohoadon+"%'";
		
		if(trangthai.length() > 0)
			query += " and isnull(a.DAINPGH,0) = '"+trangthai+"'";
				
		System.out.println("PHIEU GIAO HANG SEARCH: "+query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
