package geso.traphaco.center.servlets.bm;

import geso.traphaco.center.beans.bm.IBm;
import geso.traphaco.center.beans.bm.imp.Bm;
import geso.traphaco.center.beans.bm.IBmList;
import geso.traphaco.center.beans.bm.imp.BmList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BmUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public BmUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		IBm bmBean = new Bm();

		System.out.println(userId);

		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String action = util.getAction(querystring);
		System.out.println(action);

		String Id = util.getId(querystring);

		bmBean.setId(Id);

		bmBean.init_Update();
		// Save data into session
		session.setAttribute("bmBean", bmBean);

		String nextJSP = "/TraphacoHYERP/pages/Center/BMUpdate.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		IBm bmBean = new Bm();

		String Id = util.antiSQLInspection(request.getParameter("Id"));
		if (Id == null)
			Id = "";
		bmBean.setId(Id);

		String ten = util.antiSQLInspection(request.getParameter("bmTen"));
		if (ten == null)
			ten = "";
		bmBean.setTen(ten);

		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		bmBean.setKbhId(kbhId);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		bmBean.setDvkdId(dvkdId);

		String diachi = util.antiSQLInspection(request.getParameter("DiaChi"));
		if (diachi == null)
			diachi = "";
		bmBean.setDiachi(diachi);

		String email = util.antiSQLInspection(request.getParameter("Email"));
		if (email == null)
			email = "";
		bmBean.setEmail(email);

		String dienthoai = util.antiSQLInspection(request.getParameter("DienThoai"));
		if (dienthoai == null)
			dienthoai = "";
		bmBean.setDienthoai(dienthoai);

		String[] vungId = request.getParameterValues("vungId");
		bmBean.setVungId(vungId);

		String nganhang = util.antiSQLInspection(request.getParameter("NganHang"));
		if (nganhang == null)
			nganhang = "";
		bmBean.setNganHang(nganhang);

		String chinhanh = util.antiSQLInspection(request.getParameter("ChiNhanh"));
		if (chinhanh == null)
			chinhanh = "";
		bmBean.setChiNhanh(chinhanh);

		
		String manhansu = util.antiSQLInspection(request.getParameter("manhansu"));
		if (manhansu == null)
			manhansu = "";
		bmBean.setMaNhanSu(manhansu);
		
		String manhanvien = util.antiSQLInspection(request.getParameter("MaNhanVien"));
		if (manhanvien == null)
			manhanvien = "";
		bmBean.setMaNhanVien(manhanvien);

		String mathuviec = util.antiSQLInspection(request.getParameter("MaThuViec"));
		if (mathuviec == null)
			mathuviec = "";
		bmBean.setMaThuViec(mathuviec);

		String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));
		if (trangthai == null)
			trangthai = "0";
		else
			trangthai = "1";
		bmBean.setTrangthai(trangthai);

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		if (userId == null)
			userId = "";
		bmBean.setUserId(userId);

		String action = util.antiSQLInspection(request.getParameter("action"));
		if (action == null)
			action = "";

		String mact = util.antiSQLInspection(request.getParameter("mact"));
		if (mact == null)
			mact = "";
		bmBean.setMaCt(mact);

		String vitri = util.antiSQLInspection(request.getParameter("vitri"));
		if (vitri == null)
			vitri = "";
		bmBean.setVitri(vitri);

		String vungtt = util.antiSQLInspection(request.getParameter("vungtt"));
		if (vungtt == null)
			vungtt = "";
		bmBean.setVungTT(vungtt);

		String sotk = util.antiSQLInspection(request.getParameter("sotk"));
		if (sotk == null)
			sotk = "";
		bmBean.setSotk(sotk);

		String dakyhd = util.antiSQLInspection(request.getParameter("dakyhd"));
		if (dakyhd == null)
			dakyhd = "";
		bmBean.setDakyHD(dakyhd);

		String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		if (ghichu == null)
			ghichu = "";
		bmBean.setGhichu(ghichu);

		String loaihd = util.antiSQLInspection(request.getParameter("loaihd"));
		if (loaihd == null)
			loaihd = "";
		bmBean.setLoaiHD(loaihd);

		String ngaykthd = util.antiSQLInspection(request.getParameter("ngaykthd"));
		if (ngaykthd == null)
			ngaykthd = "";
		bmBean.setNgayketthucHD(ngaykthd);

		String ngaykyhd = util.antiSQLInspection(request.getParameter("ngaykyhd"));
		if (ngaykyhd == null)
			ngaykyhd = "";
		bmBean.setNgaykyHD(ngaykyhd);

		String ngayvaoct = util.antiSQLInspection(request.getParameter("ngayvaoct"));
		if (ngayvaoct == null)
			ngayvaoct = "";
		bmBean.setNgayvaoct(ngayvaoct);

		String sodtct = util.antiSQLInspection(request.getParameter("sodtct"));
		if (sodtct == null)
			sodtct = "";
		bmBean.setSoDTcongty(sodtct);

		String sonamlamviec = util.antiSQLInspection(request.getParameter("sonamlamviec"));
		if (sonamlamviec == null)
			sonamlamviec = "";
		bmBean.setSonamlamviec(sonamlamviec);
		String ngaysinh = util.antiSQLInspection(request.getParameter("bmngaysinh"));
		if (ngaysinh == null)
			ngaysinh = "";
		bmBean.setNgaysinh(ngaysinh);
		
		String tructhuocid = util.antiSQLInspection(request.getParameter("tructhuoc"));
		System.out.println("tructhuoc "+tructhuocid);
		if (tructhuocid == null)
			tructhuocid = "";
		bmBean.setTructhuoc(tructhuocid);
		
		String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
		if (maFAST == null)
			maFAST = "";
		bmBean.MaFAST(maFAST);
		String sohd = util.antiSQLInspection(request.getParameter("sohdld"));
		if (sohd == null)
			sohd = "";
		bmBean.setSoHD(sohd);
		
		if (action.equals("save"))
		{
			if (bmBean.Save(request))
			{
				IBmList obj = new BmList();
				obj.init();
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/BM.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				bmBean.init_New();
				// Save data into session
				session.setAttribute("bmBean", bmBean);
				String nextJSP = "/TraphacoHYERP/pages/Center/BMNew.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
}
