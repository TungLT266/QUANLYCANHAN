package geso.traphaco.erp.servlets.kiemdinhchatluong;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kiemdinhchatluong.IErpKiemdinhchatluong_kho;
import geso.traphaco.erp.beans.kiemdinhchatluong.imp.ErpKiemdinhchatluong_kho;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKiemdinhchatluong_khoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpKiemdinhchatluong_khoSvl() {
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
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    String action = util.getAction(querystring);
	    String Id = util.getId(querystring);
	    String msg="";	    

	    IErpKiemdinhchatluong_kho obj = new ErpKiemdinhchatluong_kho();
	    obj.setUserId(userId);
	    if(action.equals("hoantat")){
	    	 msg = obj.Hoantatkiemdinh(Id,userId);
	    	 obj.setId("");
	    }else if(action.equals("delete")){
	    	 msg = obj.Xoakiemdinh(Id,userId);
	    	 obj.setId("");
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_kho.jsp";
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
	    IErpKiemdinhchatluong_kho obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
	    	ErpKiemdinhchatluong_kho kdcl = new ErpKiemdinhchatluong_kho();
    		kdcl.setUserId(userId);
    		kdcl.createRs();
    		
	    	session.setAttribute("kdcl", kdcl);  	
    		session.setAttribute("userId", userId);
    	
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_KhoNew.jsp");
	    }
	    
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
    	
    		obj = new ErpKiemdinhchatluong_kho();
    		
    		/*obj.setCongtyId((String)session.getAttribute("congtyId"));*/
    		
	    	String search = getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_kho.jsp");	
	    }
	    else
	    {
	    	obj = new ErpKiemdinhchatluong_kho();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	System.out.println("---- 13. init search query: " + search);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_kho.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpKiemdinhchatluong_kho obj) 
	{
		Utility util = new Utility();
		String sql = " SELECT KD.PK_SEQ, SP.TEN AS SPTEN, KD.SOLO,  isnull((select SUM(soluong) from ERP_KIEMDINHCHATLUONG_KHO_CHITIET a "+ 
		" where a.KIEMDINHCHATLUONGKHO_FK=KD.PK_SEQ  ),0)  AS SOLUONGKHONGDAT, KD.TRANGTHAI, " +
		" KD.NGAYKD, NV.TEN AS NGUOICHOT " +
		" FROM ERP_KIEMDINHCHATLUONG_KHO KD " +
		" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KD.SANPHAM_FK " +
		" INNER JOIN NHANVIEN NV ON NV.PK_SEQ = KD.NGUOICHOT " +
		" where 1=1  " ; 
		
		
		String solo = request.getParameter("solo");
		if(solo == null)
			solo = "";
		obj.setSolo(solo);
		
		String tungayKD = request.getParameter("tungayKD");
		if(tungayKD == null)
			tungayKD = "";
		obj.setTungay(tungayKD);
		
		String denngayKD = request.getParameter("denngayKD");
		if(denngayKD == null)
			denngayKD = "";
		obj.setDenngay(denngayKD);
		
		String sanpham = request.getParameter("sanpham");
		if(sanpham == null)
			sanpham = "";
		obj.setSpTen(sanpham);
		
		
		String sochungtu = request.getParameter("sochungtu");
		if(sochungtu == null)
			sochungtu = "";
		obj.setId(sochungtu);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		 
		
		
		if(tungayKD.length() > 0)
			sql += " and isnull(KD.ngaysua, '') >= '" + tungayKD + "'";
				
		if(denngayKD.length() > 0)
			sql += " and isnull(KD.ngaysua, '') <= '" + denngayKD + "'";
		
	 
		
		if(sanpham.length() > 0)
			sql += " and ( sp.timkiem like N'%" + util.replaceAEIOU(sanpham) + "%' ) ";
				
		if(sochungtu.length() > 0)
			sql += " and (  CAST(KD.pk_seq as varchar(10)) like N'%" + sochungtu + "%' ) ";
		
		if(trangthai.length() > 0)
			sql += " and KD.trangthai = '" + trangthai + "' ";
		
	
		
		return  sql;
	}
	
	

}
