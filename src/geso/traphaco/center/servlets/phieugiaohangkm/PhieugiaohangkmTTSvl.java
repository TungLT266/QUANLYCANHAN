package geso.traphaco.center.servlets.phieugiaohangkm;

import geso.traphaco.center.beans.phieugiaohangkm.IPhieugiaohangkm;
import geso.traphaco.center.beans.phieugiaohangkm.IPhieugiaohangkmList;
import geso.traphaco.center.beans.phieugiaohangkm.imp.PhieugiaohangkmList;

import geso.traphaco.center.util.Utility;
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

public class PhieugiaohangkmTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public PhieugiaohangkmTTSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IPhieugiaohangkmList obj;
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
	    obj = new PhieugiaohangkmList();
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/PhieuGiaoHangKM.jsp";
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
	    	    
	    
		IPhieugiaohangkmList obj = new PhieugiaohangkmList();

		  String madonhang = request.getParameter("madonhang");
		     if(madonhang == null)
		      madonhang = "";
		     obj.setMadonhang(madonhang);
		     
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
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Distributor/PhieuGiaoHangKM.jsp";
			response.sendRedirect(nextJSP);
	    }
    	else
    	{
    		obj.setUserId(userId);
	    	String search = getSearchQuery(request, obj);
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		
	    	obj.init(search);								
	    	
	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/PhieuGiaoHangKM.jsp";
    		response.sendRedirect(nextJSP);
    	}
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, IPhieugiaohangkmList obj)
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
		
		String madonhang = request.getParameter("madonhang");
		if(madonhang == null)
			madonhang = "";
		obj.setMadonhang(madonhang);
		
		String trangthai = request.getParameter("trangthai");
	     if(trangthai == null)
	    	 trangthai = "";
	     obj.setTrangthai(trangthai);
	     
	    String query =  
	    		"select  a.sohoadon, a.kyhieu ,  a.PK_SEQ as hdId, a.trangthai, a.ngayxuatHD as ngaynhap, NV.TEN as nguoitao, KH.TEN as TENKH, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua," +
				"        a.tongtienavat as avat, case a.trangthai when 1 then 1 when 2 then 2 when 3 then 4 when 4 then 3 when 5 then 5 end as STT_SORT,isnull(d.InPhieuGiaoHangKM, 0) as DAINPGH,c.ddh_fk as pk_seq  " +
				"from ERP_HOADON a  " +
				"	inner join nhaphanphoi KH on a.npp_FK=KH.PK_SEQ  " +
				"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
				"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ "+
				" inner join  ERP_hoadon_ddh c on c.hoadon_fk=a.pk_Seq  " +
				" inner join ERP_DONDATHANG  d on c.DDH_FK=d.PK_SEQ "+
				"where a.pk_seq > 0  and isnull(loaihoadon,0) in ( 1, 2 ) " +
				"  and a.trangthai not in (1, 3, 5) ";	

		
		String khten = request.getParameter("khTen");		
		if(khten == null)
			khten = "";
		obj.setKhTen(khten);
				
		
		if(tungay.length() > 0)
			query += " and a.ngayxuatHD >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayxuatHD <= '" + denngay + "'";
			
		if(khten.length() > 0)
			query += " and kh.pk_seq =" + khten + " ";
		
		if(madonhang.length() > 0)
			query += " and a.PK_SEQ in ( select hoadon_fk from HOADON_DDH where ddh_fk in ( select pk_seq from DONHANG where cast( pk_seq as varchar(10) ) like '%"+madonhang+"%'  ) ) ";
		
		if(trangthai.length() > 0)
			query += " and isnull(d.InPhieuGiaoHangKM,0) = '"+trangthai+"'";
				
		System.out.println("PHIEU GIAO HANG KM SEARCH: "+query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
