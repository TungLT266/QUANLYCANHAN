package geso.traphaco.erp.servlets.buttoantonghop;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.buttoantonghop.IErpButToanTongHop;
import geso.traphaco.erp.beans.buttoantonghop.IErpButToanTongHopList;
import geso.traphaco.erp.beans.buttoantonghop.imp.ErpButToanTongHop;
import geso.traphaco.erp.beans.buttoantonghop.imp.ErpButToanTongHopList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpButToanTongHopSvl")
public class ErpButToanTongHopSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpButToanTongHopSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		IErpButToanTongHopList btthList;
		String userId;
		String ServerletName = this.getServletName();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		out.println(action);

		String hdId = util.getId(querystring);

		userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		btthList = new ErpButToanTongHopList();
		
		btthList.setCongtyId((String)session.getAttribute("congtyId"));
		out.println(userId);

		System.out.println("action: " + action);
		if (action.equals("chot"))
		{
			IErpButToanTongHop btth = new ErpButToanTongHop(hdId);
			btth.setCongtyId((String)session.getAttribute("congtyId"));
			btth.setUserId(userId);
			btthList.setMsg(btth.Chot());
			String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(btthList, searchQuery);

		} else if (action.equals("delete"))
		{
			
			IErpButToanTongHop hdth = new ErpButToanTongHop(hdId);
			hdth.setCongtyId((String)session.getAttribute("congtyId"));
			hdth.setUserId(userId);
			btthList.setMsg(hdth.Delete());
			String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(btthList, searchQuery);
		}
		btthList.init();
		String querySearch = GiuDieuKienLoc.createParams(btthList);
	    util.setSearchToHM(userId, session, ServerletName, querySearch);

		session.setAttribute("btthList", btthList);
		session.setAttribute("congtyId", btthList.getCongtyId());
		
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHop.jsp";
		response.sendRedirect(nextJSP);
	}
	public static void main(String args[]){
		System.out.println("hii");
		dbutils db = new dbutils();
		String sql = " SELECT DISTINCT SOCHUNGTU,NGUOISUA FROM ERP_PHATSINHKETOAN_T PS " +
					" INNER JOIN ERP_BUTTOANTONGHOP BTTH ON BTTH.PK_SEQ = PS.SOCHUNGTU" ;
		String idKhongTaoDuoc = "";
		ResultSet rs= db.get(sql);
		if(rs != null){
			try {
				while(rs.next()){
					IErpButToanTongHop btth = new ErpButToanTongHop();
					btth.setId(rs.getString("SOCHUNGTU"));
					btth.setUserId(rs.getString("NGUOISUA"));
					if(!btth.Chot().equals("")){
						idKhongTaoDuoc += btth.getId() +",";
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Số chứng từ chưa tạo được phát sinh:" + idKhongTaoDuoc);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String querystring = request.getQueryString();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		IErpButToanTongHopList btthList;
		String userId;
		Utility util = new Utility();
		String ServerletName = this.getServletName();

		HttpSession session = request.getSession();
		userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
	
		
		btthList = new ErpButToanTongHopList();
		btthList.setCongtyId((String)session.getAttribute("congtyId"));
		
		String NgayButToan = util.antiSQLInspection(request.getParameter("NgayButToan"));
		if (NgayButToan == null) NgayButToan ="";
		btthList.setNgayButToan(NgayButToan);
		
		String DenNgayButToan = util.antiSQLInspection(request.getParameter("DenNgayButToan"));
		if (DenNgayButToan == null) DenNgayButToan ="";
		btthList.setDenNgayButToan(DenNgayButToan);
		
		String Sochungtu = util.antiSQLInspection(request.getParameter("Sochungtu"));
		if (Sochungtu == null) Sochungtu ="";
		btthList.setSoChungTu(Sochungtu);
		
		String Nguoitao = util.antiSQLInspection(request.getParameter("Nguoitao"));
		if (Nguoitao == null) Nguoitao ="";
		btthList.setNguoiTao(Nguoitao);
		
		String Sotien = util.antiSQLInspection(request.getParameter("Sotien"));
		if (Sotien == null) Sotien ="";
		btthList.setSoTien(Sotien.replaceAll(",", ""));
		
		String Taikhoanno = util.antiSQLInspection(request.getParameter("Taikhoanno"));
		if (Taikhoanno == null) Taikhoanno ="";
		btthList.setTaiKhoanNo(Taikhoanno);
		
		String Taikhoanco = util.antiSQLInspection(request.getParameter("Taikhoanco"));
		if (Taikhoanco == null) Taikhoanco ="";
		btthList.setTaiKhoanCo(Taikhoanco);
		
		String machungtu = util.antiSQLInspection(request.getParameter("machungtu"));
		if (machungtu == null) machungtu ="";
		btthList.setSoId(machungtu);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null) trangthai ="";
		btthList.setTrangthai(trangthai);
		
		userId = util.antiSQLInspection(request.getParameter("userId"));
		btthList.setUserId(userId);
		String action = request.getParameter("action");
		if (action == null)
			action = "";
		
		String chungtu=util.antiSQLInspection(request.getParameter("chungtu"));
		if (chungtu == null)
			chungtu = "";

		if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			btthList.setCongtyId((String)session.getAttribute("congtyId"));
			
			btthList.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			// btthList.setUserId(userId);
			btthList.init();
			btthList.setAttribute(request, action, "list", "crrApprSplitting","nxtApprSplitting");
			session.setAttribute("btthList", btthList);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHop.jsp";
			response.sendRedirect(nextJSP);
			
		} else if (action.equals("new"))
		{
			IErpButToanTongHop btthBean = new ErpButToanTongHop();
			btthBean.setUserId(userId);
			btthBean.setCongtyId((String)session.getAttribute("congtyId"));
			btthBean.Init();
			btthList.DBClose();
			btthBean.createRs();
			session.setAttribute("btthBean", btthBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHopNew.jsp";
			response.sendRedirect(nextJSP);
		}
		else if (action.equals("chot"))
		{
			IErpButToanTongHop btth = new ErpButToanTongHop(chungtu);
			btth.setCongtyId((String)session.getAttribute("congtyId"));
			btth.setUserId(userId);
			btthList.setMsg(btth.Chot());
			String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(btthList, searchQuery);
			
			btthList.init();
			session.setAttribute("btthList", btthList);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHop.jsp";
			response.sendRedirect(nextJSP);

		}
		else if (action.equals("moChot"))
		{
			IErpButToanTongHop btth = new ErpButToanTongHop(chungtu);
			btth.setCongtyId((String)session.getAttribute("congtyId"));
			btth.setUserId(userId);
			btthList.setMsg(btth.MoChot());
			String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(btthList, searchQuery);
			
			btthList.init();
			session.setAttribute("btthList", btthList);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHop.jsp";
			response.sendRedirect(nextJSP);
		}
		 else if (action.equals("delete"))
			{
				
				IErpButToanTongHop hdth = new ErpButToanTongHop(chungtu);
				hdth.setCongtyId((String)session.getAttribute("congtyId"));
				hdth.setUserId(userId);
				btthList.setMsg(hdth.Delete());
				String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
				geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(btthList, searchQuery);
				
				btthList.init();
				session.setAttribute("btthList", btthList);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHop.jsp";
				response.sendRedirect(nextJSP);
			}
		else
		{
			// btthList.setUserId(userId);
			btthList.init();
			String querySearch = GiuDieuKienLoc.createParams(btthList);
		    util.setSearchToHM(userId, session, ServerletName, querySearch);
			session.setAttribute("btthList", btthList);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpButToanTongHop.jsp";
			response.sendRedirect(nextJSP);
		}

	}
}
