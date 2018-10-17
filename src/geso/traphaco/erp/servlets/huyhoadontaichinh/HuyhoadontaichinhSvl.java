package geso.traphaco.erp.servlets.huyhoadontaichinh;

import geso.traphaco.distributor.beans.hoadontaichinhNPP.IErpHuyhoadontaichinhNPPList;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.imp.ErpHuyhoadontaichinhNPPList;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.IErpHuyhoadontaichinhNPP;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.imp.ErpHuyhoadontaichinhNPP;
import geso.traphaco.center.db.sql.dbutils;
//import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HuyhoadontaichinhSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public HuyhoadontaichinhSvl()
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
		String ctyId = (String)session.getAttribute("congtyId");
		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
	

		IErpHuyhoadontaichinhNPPList obj = new ErpHuyhoadontaichinhNPPList();
		obj.setUserId(userId);
		obj.setCtyId(ctyId);
		
		String action = util.getAction(querystring);
		
		String msg = "";
		
		String loaiHD = util.antiSQLInspection(request.getParameter("loaiHD"));
		if(loaiHD == null) loaiHD = "";
		System.out.println("loaiHD:" + loaiHD);
		
		String chot = util.antiSQLInspection(request.getParameter("chot"));
		if(chot == null){
			chot = "";
		}
		
		String xoa = util.antiSQLInspection(request.getParameter("delete"));
		if(xoa == null){
			xoa = "";
		}
		
		if(action.equals("chot")){
			obj.chot(chot, loaiHD);
		}
		
		if(action.equals("delete")){
			obj.delete(xoa);
		}

		obj.init("");
		obj.setMsg(msg);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Huyhoadontaichinh.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		//ctyId = "100001";
		
		out = response.getWriter();
		Utility util = new Utility();

		String userId = util.antiSQLInspection(request.getParameter("userId"));

		IErpHuyhoadontaichinhNPPList obj = new ErpHuyhoadontaichinhNPPList();
		obj.setCtyId(ctyId);
		
		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		System.out.println("action :" + action);

		
		//******** THÔNG TIN TÌM KIẾM **********/
		String tungay = request.getParameter("tungay");
		if (tungay == null)
		{
			tungay = "";
		}
		obj.settungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if (denngay == null)
		{
			denngay = "";
		}
		obj.setdenngay(denngay);
		
		String sochungtu = request.getParameter("sochungtu");
		if (sochungtu == null)
		{
			sochungtu = "";
		}
		obj.setsochungtu(sochungtu);

		String trangthai = request.getParameter("trangthai");
		if (trangthai == null)
		{
			trangthai = "";
		}
		obj.setTrangthai(trangthai);
		
		
		String khachhang = request.getParameter("khachhang");
		if (khachhang == null)
		{
			khachhang = "";
		}
		obj.setkhId(khachhang);
	
		String sohoadon = request.getParameter("sohoadon");
		if (sohoadon == null)
		{
			sohoadon = "";
		}
		obj.setsohoadon(sohoadon);
		
		
		
		if(action.equals("new"))
		{
			IErpHuyhoadontaichinhNPP hhdtc = new ErpHuyhoadontaichinhNPP();
			hhdtc.setUserId(userId);
			hhdtc.setCtyId(ctyId);
			hhdtc.createRs();
			
			session.setAttribute("obj", hhdtc);
			session.setAttribute("userId", userId);

			response.sendRedirect("/TraphacoHYERP/pages/Erp/HuyhoadontaichinhNew.jsp");
		}
		else if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
    		System.out.println("toi day");
    		obj = new ErpHuyhoadontaichinhNPPList();
    	
    		
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    
	    	obj.init("");
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Huyhoadontaichinh.jsp");	
	    }
		else
		{
			
			obj.setUserId(userId);

			String search = getSearchQuery(request, obj);
			obj.setCtyId(ctyId);
			obj.setUserId(userId);
			obj.init(search);

			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);

			response.sendRedirect("/TraphacoHYERP/pages/Erp/Huyhoadontaichinh.jsp");
		}
	}

	private String getSearchQuery(HttpServletRequest request, IErpHuyhoadontaichinhNPPList obj)
	{
		Utility util = new Utility();

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String  sql = "";
		sql =	" SELECT HHD.PK_SEQ, HD.SOHOADON, HD.KHACHHANG_FK, KH.TEN AS KHTEN, HHD.TRANGTHAI, HHD.NGAYTAO, NT.TEN AS NVTEN1, HHD.NGAYSUA, NS.TEN AS NVTEN2 "+
		" FROM ERP_HUYHOADONTAICHINH HHD "+
		" INNER JOIN ERP_KHACHHANG KH ON HHD.KHACHHANG_FK= KH.PK_SEQ "+
		" INNER JOIN NHANVIEN NT ON HHD.NGUOITAO= NT.PK_SEQ "+
		" INNER JOIN NHANVIEN NS ON HHD.NGUOISUA= NS.PK_SEQ "+
		" INNER JOIN ERP_HOADON HD ON HD.PK_SEQ = HHD.HOADON_FK " +
		" WHERE 1=1 ";

		if(obj.gettungay().length() >0){
			sql += " AND HHD.NGAYTAO>='" + obj.gettungay() + "'";
		}

		if(obj.getdenngay().length() >0){
			sql += " AND HHD.NGAYTAO <= '" + obj.getdenngay() + "'";
		}

		if(obj.getTrangthai().length() >0){
			sql += " AND HHD.TRANGTHAI= " + obj.getTrangthai() + " ";
		}

		if(obj.getsochungtu().length() >0){
			sql += " AND HHD.PK_SEQ = '" + obj.getsochungtu() + "'";
		}

		if(obj.getkhId().length() >0){
			sql += " AND HD.KHACHHANG_FK= '" + obj.getkhId() + "'";
		}

		if(obj.getsohoadon().length() > 0){
			sql += " AND HD.SOHOADON LIKE '%" + obj.getsohoadon() + "%'";
		}
		
		//sql += " order by a.pk_seq desc ";

		return sql;
	}
}
