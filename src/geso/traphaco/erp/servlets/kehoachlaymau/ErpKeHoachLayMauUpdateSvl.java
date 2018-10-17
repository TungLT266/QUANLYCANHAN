package geso.traphaco.erp.servlets.kehoachlaymau;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.kehoachlaymau.IKeHoachLayMau;
import geso.traphaco.erp.beans.kehoachlaymau.IKeHoachLayMauList;
import geso.traphaco.erp.beans.kehoachlaymau.imp.KeHoachLayMau;
import geso.traphaco.erp.beans.kehoachlaymau.imp.KeHoachLayMauList;
import geso.traphaco.erp.beans.kehoachlaymau.imp.PhieuKiemDinh;
import geso.traphaco.erp.beans.thutien.IErpThutien;
import geso.traphaco.erp.beans.thutien.imp.ErpThutien;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class YCLayMauKiemNghiemSvl
 */
@WebServlet("/ErpKeHoachLayMauUpdateSvl")
public class ErpKeHoachLayMauUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ErpKeHoachLayMauUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		String querystring = request.getQueryString();
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session
				.getAttribute("util");
		PrintWriter out;
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} 
		
		else {
			session.setMaxInactiveInterval(30000);

			out = response.getWriter();
			Utility util = new Utility(); 
			userId = util.getUserId(querystring);
			String ctyId = (String) session.getAttribute("congtyId");
			String nppId = (String) session.getAttribute("nppId");
			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			if (id == null)
				id = "";

			IKeHoachLayMau obj = new KeHoachLayMau(id);
			obj.setCtyId(ctyId);
			obj.setUserId(userId);
			obj.setNppId(nppId);
			obj.init();
			obj.createRs();
			String nextJSP;
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachLayMauUpdate.jsp";

			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session
				.getAttribute("util");
		PrintWriter out;
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);
			out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			String ctyId = (String) session.getAttribute("congtyId");
			String nppId = (String) session.getAttribute("nppId");
			out.println(userId);
			
			String action = util.antiSQLInspection(request
					.getParameter("action"));

			if (action == null)
				action = "";

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
				id = ""; 
			
			
			
			IKeHoachLayMau obj = new KeHoachLayMau(id);
			obj.setCtyId(ctyId);
			obj.setUserId(userId);
			obj.setNppId(nppId);
			filterData(obj, request);

			String nextJSP = "";
			 
			if (action.equals("save")) {
				System.out.println("VAO DAY");
				if (id.trim().length() == 0) {
					if (!obj.create()) {
						obj.createRs();
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachLayMauNew.jsp";
						session.setAttribute("obj", obj);
						response.sendRedirect(nextJSP);
					} else {
						IKeHoachLayMauList obj1 = new KeHoachLayMauList(id);
				        obj1.setCtyId(ctyId);
						obj1.setUserId(userId);			
						obj1.setNppId(nppId); 
						obj1.createRs(); 
						obj1.init();
				        nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachLayMau.jsp"; 
				        session.setAttribute("obj", obj1);
				        response.sendRedirect(nextJSP);
					}
				} else {
					if (!obj.update()) {
						obj.createRs();
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachLayMauUpdate.jsp";
						session.setAttribute("obj", obj);
						response.sendRedirect(nextJSP);
					} else {
						IKeHoachLayMauList obj1 = new KeHoachLayMauList(id);
				        obj1.setCtyId(ctyId);
						obj1.setUserId(userId);			
						obj1.setNppId(nppId); 
						obj1.createRs(); 
						obj1.init();
				        nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachLayMau.jsp";
				 
				        session.setAttribute("obj", obj1);
				        response.sendRedirect(nextJSP);
					}
				}
			} else if (action.equals("filter")) {
				obj.createRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachLayMauNew.jsp";
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
			}
		}
	}

	private void filterData(IKeHoachLayMau obj, HttpServletRequest request) {
		Utility util = new Utility();
		String ngaykh = util.antiSQLInspection(request.getParameter("ngaykh"));
		if (ngaykh == null)
			ngaykh = "";
		obj.setNgayKeHoach(ngaykh);

		String phongbanthId = util.antiSQLInspection(request
				.getParameter("phongbanthId"));
		if (phongbanthId == null)
			phongbanthId = "";
		obj.setPhongBanId(phongbanthId);

		String diengiai = util.antiSQLInspection(request
				.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		obj.setDienGiai(diengiai);

		String sanphamkiemdinh = util.antiSQLInspection(request
				.getParameter("sanphamkiemdinh"));
		if (sanphamkiemdinh == null)
			sanphamkiemdinh = "";
		obj.setSanphamId(sanphamkiemdinh);

		String trangthai = util.antiSQLInspection(request
				.getParameter("checkboxId"));
		obj.setTrangThai(trangthai);

		String[] sott = request.getParameterValues("sott");
		String[] ngaydanhgia = request.getParameterValues("ngaydanhgia");
		String[] ngaydanhgialai = request.getParameterValues("ngaydanhgialai");
		String[] ngaythucte = request.getParameterValues("ngaythucte");
		String[] ghichu = request.getParameterValues("ghichu");

		if (sott != null) {
			System.out.println("SO THU TU: " + sott.length);
			for (int i = 0; i < sott.length; i++) {
				PhieuKiemDinh pkd = new PhieuKiemDinh();
				pkd.setSott(sott[i]);
				pkd.setNgayDanhGia(ngaydanhgia[i]);
				pkd.setNgayDanhGiaLai(ngaydanhgialai[i]);
				pkd.setNgayThucTe(ngaythucte[i]);
				pkd.setGhiChu(ghichu[i]);
				obj.getDsPhieuKiemDinhs().add(pkd);
			}

		}

	}

}
