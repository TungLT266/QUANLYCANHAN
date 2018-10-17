package geso.traphaco.erp.servlets.hosokiemnghiem;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hosokiemnghiem.IErpHoSoKiemNghiem;
import geso.traphaco.erp.beans.hosokiemnghiem.IErpHoSoKiemNghiemList;
import geso.traphaco.erp.beans.hosokiemnghiem.IHoSoKiemNghiem;
import geso.traphaco.erp.beans.hosokiemnghiem.imp.ErpHoSoKiemNghiem;
import geso.traphaco.erp.beans.hosokiemnghiem.imp.ErpHoSoKiemNghiemList;
import geso.traphaco.erp.beans.hosokiemnghiem.imp.HoSoKiemNghiem;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErpHoSoKiemNghiemSvl
 */
@WebServlet("/ErpHoSoKiemNghiemSvl")
public class ErpHoSoKiemNghiemSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpHoSoKiemNghiemSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    IErpHoSoKiemNghiemList obj;
		obj = new ErpHoSoKiemNghiemList();
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null) id = "";
		obj.setId(id);
		System.out.println("id la: "+id);
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setCongtyId(ctyId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    obj.setUserId(userId);
	    String action=Utility.getParameter(querystring, "delete");
	    if(action==null)action="";
	    System.out.println("action :"+action);
	    
	    if (querystring.contains("delete"))
	    {	
	    	String msg = obj.Delete(action, userId);
	    	obj.setMsg(msg);
	    	obj.setUserId(userId);
		    obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiem.jsp";
			response.sendRedirect(nextJSP);
			
	    }
    	
	    else
	    {	    
	    	obj.setUserId(userId);
		    obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiem.jsp";
			response.sendRedirect(nextJSP);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IErpHoSoKiemNghiemList obj;
		
	    OutputStream out = response.getOutputStream();
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    obj = new ErpHoSoKiemNghiemList();
	    Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("chungtu"));
		if (id == null) id = "";
		obj.setId(id);
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String tungaySX = util.antiSQLInspection(request.getParameter("tungaySX"));
		if (tungaySX == null) tungaySX = "";
		obj.setTungay(tungaySX);
		
		String denngaySX = util.antiSQLInspection(request.getParameter("denngaySX"));
		if (denngaySX == null) denngaySX = "";
		obj.setDenngay(denngaySX);
		
		String sophieu = util.antiSQLInspection(request.getParameter("sophieu"));
		if (sophieu == null) sophieu = "";
		obj.setMa(sophieu);
		
		String mahoso = util.antiSQLInspection(request.getParameter("mahoso"));
		if (mahoso == null) mahoso = "";
		obj.setMaSoKN(mahoso);
		
		String masanpham = util.antiSQLInspection(request.getParameter("masanpham"));
		if (masanpham == null) masanpham = "";
		obj.setMaSanPham(masanpham);
		
		String spid = util.antiSQLInspection(request.getParameter("masanpham"));
		if (spid == null) spid = "";
		obj.setSpId(spid);
		
		String sophieukiemnghiem = util.antiSQLInspection(request.getParameter("sophieukiemnghiem"));
		if (sophieukiemnghiem == null) sophieukiemnghiem = "";
		obj.setSoPhieuKN(sophieukiemnghiem);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null) trangthai = "";
		obj.setTrangThai(trangthai);
		
	    if(action.equals("Tao moi"))
	    {
	    	IHoSoKiemNghiem bean =new HoSoKiemNghiem();
	    	bean.creates();
	    	bean.setUserId(userId);
			session.setAttribute("obj", bean);
			
			String nextJSP="/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiemNew.jsp"; 
			response.sendRedirect(nextJSP);
	    }else if(action.equals("chot")){
	    	String msg=this.chot(id);
	    	obj.setMsg(msg);
	    	obj.setUserId(userId);
		    obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiem.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else if(action.equals("bochot")){
	    	String msg=this.bochot(id);
	    	obj.setMsg(msg);
	    	obj.setUserId(userId);
		    obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiem.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
    	 	obj.setUserId(userId);
		    obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoSoKiemNghiem.jsp";
			response.sendRedirect(nextJSP);
	    }
	}

	private String chot(String id) {
		String msg = "";
		dbutils db = new dbutils();
		try{
		String query = "update erp_hosokiemnghiem set trangthai=1 where pk_seq ="+ id +"  and trangthai=0 ";
		System.out.println("::: CHOT : " + query);
		
		if( db.updateReturnInt(query)!=1 )
		{
			msg = "Lỗi khi chốt: " + query;
			db.shutDown();
			return msg;
		}
		}catch (Exception e)
		{
			e.printStackTrace();
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.shutDown();
		}
		return msg;
	}
	
	private String bochot(String id) {
		String msg = "";
		dbutils db = new dbutils();
		try{
		String query = "update erp_hosokiemnghiem set trangthai=0 where pk_seq ="+ id +"  and trangthai=1 ";
		System.out.println("::: CHOT : " + query);
		
		if( db.updateReturnInt(query)!=1 )
		{
			msg = "Lỗi khi bỏ chốt: " + query;
			db.shutDown();
			return msg;
		}
		}catch (Exception e)
		{
			e.printStackTrace();
			msg = "Lỗi khi bỏ chốt: " + e.getMessage();
			db.shutDown();
		}
		return msg;
	}
}
