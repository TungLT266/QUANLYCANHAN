package geso.traphaco.erp.servlets.phieuthanhtoan;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.phieuthanhtoan.IDuyetdonmuahang;
import geso.traphaco.erp.beans.phieuthanhtoan.imp.Duyetdonmuahang;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_DuyetthanhtoanSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Erp_DuyetthanhtoanSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		HttpSession session = request.getSession();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		IDuyetdonmuahang ddmhBean = new Duyetdonmuahang();
		ddmhBean.setCongtyId((String) session.getAttribute("congtyId"));
		ddmhBean.setUserId(userId);

		ddmhBean.init();
		util.setSearchToHM(userId, session, this.getServletName(), "");
		// Data is saved into session
		session.setAttribute("ddmhBean", ddmhBean);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiThanhToan.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		Utility util = new Utility();
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		// String ctyId = util.antiSQLInspection(request.getParameter("ctyId"));
		String dvthId = util.antiSQLInspection(request.getParameter("dvthId"));
		String lspId = util.antiSQLInspection(request.getParameter("lspId"));
		String Id = util.antiSQLInspection(request.getParameter("Id"));
		String ngaymua = util.antiSQLInspection(request.getParameter("ngaymua"));
		String denNgay = util.antiSQLInspection(request.getParameter("denNgay"));
		String trangThai = util.antiSQLInspection(request.getParameter("trangThai"));
		String maDMH = util.antiSQLInspection(request.getParameter("maDMH"));
		String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		String htttId = util.antiSQLInspection(request.getParameter("htttId"));
		String action = util.antiSQLInspection(request.getParameter("action"));
		String loaicap = util.antiSQLInspection(request.getParameter("loaicap"));
		String nguoitao = util.antiSQLInspection(request.getParameter("nguoitao"));
		String tongTien = util.antiSQLInspection(request.getParameter("tongTien"));
		IDuyetdonmuahang ddmhBean = new Duyetdonmuahang();

		if (userId == null)
			userId = "";
		if (dvthId == null)
			dvthId = "";
		if (nccId == null)
			nccId = "";
		if (htttId == null)
			htttId = "";
		if (maDMH == null)
			maDMH = "";
		if (ngaymua == null)
			ngaymua = "";
		if (denNgay == null)
			denNgay = "";
		if (lspId == null)
			lspId = "";
		if (nguoitao == null)
			nguoitao = "";
		if (trangThai == null)
			trangThai = "";

		if (tongTien == null)
			tongTien = "";

		ddmhBean.setCongtyId((String) session.getAttribute("congtyId"));
		ddmhBean.setUserId(userId);
		ddmhBean.setCtyId((String) session.getAttribute("congtyId"));
		ddmhBean.setDvthId(dvthId);
		ddmhBean.setNccId(nccId);
		ddmhBean.setHtttid(htttId);
		ddmhBean.setMaDMH(maDMH);
		ddmhBean.setNgaymua(ngaymua);
		ddmhBean.setDenNgay(denNgay);
		ddmhBean.setTrangThai(trangThai);
		ddmhBean.setLspId(lspId);
		ddmhBean.setNguoitao(nguoitao);
		ddmhBean.setTongTien(tongTien);

		String lydoxoa = util.antiSQLInspection(request.getParameter("lydoxoa"));
		if (lydoxoa == null)
			lydoxoa = "";
		ddmhBean.setLydoxoa(lydoxoa);

		String lydomo = util.antiSQLInspection(request.getParameter("lydomo"));
		if (lydomo == null)
			lydomo = "";
		ddmhBean.setLydomoduyet(lydomo);

		String lydosua = util.antiSQLInspection(request.getParameter("lydosua"));
		if (lydosua == null)
			lydosua = "";
		ddmhBean.setLydosua(lydosua);

		ddmhBean.setRequest(request);
		if (action.equals("duyetTP") || action.equals("duyetKTV") || action.equals("duyetKTT")) { //các cấp duyệt

			ddmhBean.Duyetmuahang(Id, action);
			//////////////// Giu dieu kien loc
			String params = util.getSearchFromHM(userId, this.getServletName(), session);
			GiuDieuKienLoc.setParamsToOject(ddmhBean, params);
			////////////////

			ddmhBean.init();
			session.setAttribute("ddmhBean", ddmhBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiThanhToan.jsp";
			response.sendRedirect(nextJSP);

		} else if (action.equals("boduyet")) // BỎ DUYỆT => CHẮC CHẮN QUYỀN CỦA USER ĐÃ CÓ TRONG ERP_DUYETMUAHANG
		{
			ddmhBean.BoDuyetmuahang(Id);
			//////////////// Giu dieu kien loc
			String params = util.getSearchFromHM(userId, this.getServletName(), session);
			GiuDieuKienLoc.setParamsToOject(ddmhBean, params);
			//////////////////
			ddmhBean.init();

			session.setAttribute("ddmhBean", ddmhBean);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiThanhToan.jsp";
			response.sendRedirect(nextJSP);

		} else if (action.equals("xoaphieu")) // XÓA => CÓ || CHƯA CÓ USER TRONG
												// BẢNG ERP_DUYETMUAHANG
		{
			ddmhBean.Xoamuahang(Id);
			/////////////////// Giu dieu kien loc
			String params = util.getSearchFromHM(userId, this.getServletName(), session);
			GiuDieuKienLoc.setParamsToOject(ddmhBean, params);
			/////////////////
			ddmhBean.init();
			session.setAttribute("ddmhBean", ddmhBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiThanhToan.jsp";
			response.sendRedirect(nextJSP);

		} else if (action.equals("suaphieu")) // SỬA => CÓ || CHƯA CÓ USER TRONG
												// BẢNG ERP_DUYETMUAHANG
		{
			ddmhBean.Suamuahang(Id);
			String nextJSP = "ErpPhieuThanhToanUpdateSvl?userId=" + userId + "&update=" + Id + "&chucnang=" + loaicap
					+ "&duyetdn=1";
			response.sendRedirect(nextJSP);
			return;
		}else if (action.equals("boChotNhanVien")||action.equals("boChotTruongPhong")) 									
		{
			ddmhBean.boChot(Id, action);
			////////////////////// Giu dieu kien loc
			String params = util.getSearchFromHM(userId, this.getServletName(), session);
			GiuDieuKienLoc.setParamsToOject(ddmhBean, params);
			////////////////////
			ddmhBean.init();
			session.setAttribute("ddmhBean", ddmhBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiThanhToan.jsp";
			response.sendRedirect(nextJSP);

		} else if (action.equals("view") || action.equals("next") || action.equals("prev")) {

			ddmhBean.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

			ddmhBean.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			ddmhBean.init();

			session.setAttribute("ddmhBean", ddmhBean);
			////////////////////////// SET DIEU KIEN LOC
			String querySearch = GiuDieuKienLoc.createParams(ddmhBean);
			util.setSearchToHM(userId, session, this.getServletName(), querySearch);
			///////////////////////////
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiThanhToan.jsp";
			response.sendRedirect(nextJSP);
		}

		else {
			String querySearch = GiuDieuKienLoc.createParams(ddmhBean);
			util.setSearchToHM(userId, session, this.getServletName(), querySearch);
			ddmhBean.init();
			session.setAttribute("ddmhBean", ddmhBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiThanhToan.jsp";
			response.sendRedirect(nextJSP);

		}
	}
	public static void main(String args[]){
		System.out.println("hii");
		dbutils db = new dbutils();
		String sql = " SELECT DISTINCT SOCHUNGTU,KETOANVIEN_FK FROM ERP_PHATSINHKETOAN_T PS " +
					" INNER JOIN ERP_MUAHANG BTTH ON BTTH.PK_SEQ = PS.SOCHUNGTU" ;
		String idKhongTaoDuoc = "";
		ResultSet rs= db.get(sql);
		if(rs != null){
			try {
				while(rs.next()){
					IDuyetdonmuahang ddmhBean = new Duyetdonmuahang();
					ddmhBean.setUserId(rs.getString("KETOANVIEN_FK"));
					if(!ddmhBean.Duyetmuahang(rs.getString("SOCHUNGTU"), "duyetKTV")){
						idKhongTaoDuoc += rs.getString("SOCHUNGTU") +",";
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Số chứng từ chưa tạo được phát sinh:" + idKhongTaoDuoc);
		
	}
}