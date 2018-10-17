package geso.traphaco.erp.servlets.kiemdinhchatluong;

import geso.traphaco.erp.beans.kiemdinhchatluong.*;
import geso.traphaco.erp.beans.kiemdinhchatluong.imp.*;
import geso.traphaco.center.util.*;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKiemdinhchatluongSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpKiemdinhchatluongSvl() {
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
	    
	    IErpKiemdinhchatluongList obj = new ErpKiemdinhchatluongList();
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	/*dbutils db = new dbutils();
	    	if(!db.update("update Erp_Kiemdinhchatluong set trangthai = '0' where pk_seq = '" + khlId + "'"))
	    	{
	    		msg = "Không thể xóa Erp_Kiemdinhchatluong";
	    	}
	    	db.shutDown();*/
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong.jsp";
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
	    IErpKiemdinhchatluongList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		/*IErpKiemdinhchatluong khl = new ErpKiemdinhchatluong();
    		khl.setUserId(userId);

	    	session.setAttribute("dcsxBean", khl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDayChuyenSanXuatNew.jsp");*/
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
    		System.out.println("toi day");
    	
    	
    		obj = new ErpKiemdinhchatluongList();
    		/*obj.setCongtyId((String)session.getAttribute("congtyId"));*/
    		
	    	String search = getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong.jsp");	
	    }
	    else
	    {
	    	obj = new ErpKiemdinhchatluongList();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpKiemdinhchatluongList obj) 
	{
		String solo = request.getParameter("solo");
		if(solo == null)
			solo = "";
		obj.setMa(solo);
		
		String sanpham = request.getParameter("sanpham");
		if(sanpham == null)
			sanpham = "";
		obj.setSanpham(sanpham);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String  sql = "select a.pk_seq, b.PREFIX + CAST(a.nhapkho_fk as varchar(10)) as sonhapkho, c.TEN as spTen, a.trangthai, " +
							"a.soluong, a.solo, a.ngaytao, isnull(a.ngaysua, '') as ngaysua, d.TEN as nguoitao, isnull(e.TEN, '') as nguoisua   " +
					  "from ERP_YeuCauKiemDinh a inner join ERP_NHAPKHO b on a.nhapkho_fk = b.PK_SEQ  " +
					  	"inner join SANPHAM c on a.sanpham_fk = c.PK_SEQ left join NHANVIEN d on a.nguoitao = d.PK_SEQ " +
					  	"left join NHANVIEN e on a.nguoisua = e.PK_SEQ where a.pk_seq > 0  ";
		
		if(solo.length() > 0)
			sql += " and a.solo like N'%" + solo + "%' ";
		
		if(sanpham.length() > 0)
			sql += " and ( c.ten like N'%" + sanpham + "%' or c.ma like N'%" + sanpham + "%' ) ";
		
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		sql += " order by a.pk_seq desc ";
		
		return sql;
	}
	
	

}
