package geso.traphaco.center.servlets.asm;

import geso.traphaco.center.beans.asm.IAsm;
import geso.traphaco.center.beans.asm.IAsmList;
import geso.traphaco.center.beans.asm.imp.Asm;
import geso.traphaco.center.beans.asm.imp.AsmList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ASMUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ASMUpdateSvl()
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

		IAsm asmBean = new Asm();

		System.out.println("[ASMUpdateSvl.doGet] userId = " + userId);

		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String action = util.getAction(querystring);
		System.out.println("[ASMUpdateSvl.doGet] action = " + action);

		String Id = util.getId(querystring);

		asmBean.setId(Id);

		asmBean.init_Update();
		// Save data into session
		session.setAttribute("asmBean", asmBean);

		String nextJSP = "/TraphacoHYERP/pages/Center/ASMUpdate.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		IAsm asmBean = new Asm();

		String Id = util.antiSQLInspection(request.getParameter("Id"));
		if (Id == null)
			Id = "";
		asmBean.setId(Id);

		String ten = util.antiSQLInspection(request.getParameter("asmTen"));
		if (ten == null)
			ten = "";
		asmBean.setTen(ten);

		String kbhId[] = request.getParameterValues("KenhBH");
		if (kbhId != null)
		{
			String str = "";
			for(int i = 0; i < kbhId.length; i++)
			 if(kbhId[i].length() > 0)
				 str += kbhId[i] +",";
			if(str.length() > 0)
				str = str.substring(0,str.length() - 1);
			asmBean.setKbhId(str);
		}
		

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		asmBean.setDvkdId(dvkdId);

		String diachi = util.antiSQLInspection(request.getParameter("DiaChi"));
		if (diachi == null)
			diachi = "";
		asmBean.setDiachi(diachi);

		String email = util.antiSQLInspection(request.getParameter("Email"));
		if (email == null)
			email = "";
		asmBean.setEmail(email);

		String dienthoai = util.antiSQLInspection(request.getParameter("DienThoai"));
		if (dienthoai == null)
			dienthoai = "";
		asmBean.setDienthoai(dienthoai);

		String nganhang = util.antiSQLInspection(request.getParameter("NganHang"));
		if (nganhang == null)
			nganhang = "";
		asmBean.setNganHang(nganhang);

		String chinhanh = util.antiSQLInspection(request.getParameter("ChiNhanh"));
		if (chinhanh == null)
			chinhanh = "";
		asmBean.setChiNhanh(chinhanh);

		String manhanvien = util.antiSQLInspection(request.getParameter("MaNhanVien"));
		if (manhanvien == null)
			manhanvien = "";
		asmBean.setMaNhanVien(manhanvien);

		String mathuviec = util.antiSQLInspection(request.getParameter("MaThuViec"));
		if (mathuviec == null)
			mathuviec = "";
		asmBean.setMaThuViec(mathuviec);

		String[] kvId = request.getParameterValues("kvId");
		asmBean.setKvId(kvId);

		String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));
		if (trangthai == null)
			trangthai = "0";
		else
			trangthai = "1";
		asmBean.setTrangthai(trangthai);

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		if (userId == null)
			userId = "";
		asmBean.setUserId(userId);
		
		String manhansu = util.antiSQLInspection(request.getParameter("manhansu"));
		if (manhansu == null)
			manhansu = "";
		asmBean.setMaNhanSu(manhansu);

		String action = util.antiSQLInspection(request.getParameter("action"));
		if (action == null)
			action = "";

		String mact = util.antiSQLInspection(request.getParameter("mact"));
		if (mact == null)
			mact = "";
		asmBean.setMaCt(mact);

		String vitri = util.antiSQLInspection(request.getParameter("vitri"));
		if (vitri == null)
			vitri = "";
		asmBean.setVitri(vitri);

		String vungtt = util.antiSQLInspection(request.getParameter("vungtt"));
		if (vungtt == null)
			vungtt = "";
		asmBean.setVungTT(vungtt);

		String sotk = util.antiSQLInspection(request.getParameter("sotk"));
		if (sotk == null)
			sotk = "";
		asmBean.setSotk(sotk);

		String dakyhd = util.antiSQLInspection(request.getParameter("dakyhd"));
		if (dakyhd == null)
			dakyhd = "";
		asmBean.setDakyHD(dakyhd);

		String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		if (ghichu == null)
			ghichu = "";
		asmBean.setGhichu(ghichu);

		String loaihd = util.antiSQLInspection(request.getParameter("loaihd"));
		if (loaihd == null)
			loaihd = "";
		asmBean.setLoaiHD(loaihd);

		String ngaykthd = util.antiSQLInspection(request.getParameter("ngaykthd"));
		if (ngaykthd == null)
			ngaykthd = "";
		asmBean.setNgayketthucHD(ngaykthd);

		String ngaykyhd = util.antiSQLInspection(request.getParameter("ngaykyhd"));
		if (ngaykyhd == null)
			ngaykyhd = "";
		asmBean.setNgaykyHD(ngaykyhd);

		String ngayvaoct = util.antiSQLInspection(request.getParameter("ngayvaoct"));
		if (ngayvaoct == null)
			ngayvaoct = "";
		asmBean.setNgayvaoct(ngayvaoct);

		String sodtct = util.antiSQLInspection(request.getParameter("sodtct"));
		if (sodtct == null)
			sodtct = "";
		asmBean.setSoDTcongty(sodtct);

		String sonamlamviec = util.antiSQLInspection(request.getParameter("sonamlamviec"));
		if (sonamlamviec == null)
			sonamlamviec = "";
		asmBean.setSonamlamviec(sonamlamviec);

		String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		if (nccId == null)
			nccId = "";
		asmBean.setNccId(nccId);
		
		String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
		if (maFAST == null)
			maFAST = "";
		asmBean.MaFAST(maFAST);
		String sohd = util.antiSQLInspection(request.getParameter("sohdld"));
		if (sohd == null)
			sohd = "";
		asmBean.setSoHD(sohd);
		String tructhuocid = util.antiSQLInspection(request.getParameter("tructhuoc"));
		System.out.println("tructhuoc "+tructhuocid);
		if (tructhuocid == null)
			tructhuocid = "";
		asmBean.setTructhuoc(tructhuocid);
		
		String ngaysinh = util.antiSQLInspection(request.getParameter("bmngaysinh"));
		if (ngaysinh == null)
			ngaysinh = "";
		asmBean.setNgaysinh(ngaysinh);
		if (action.equals("save"))
		{
			if (asmBean.Save(request))
			{
				IAsmList obj = new AsmList();
				obj.init();

				session.setAttribute("obj", obj);

				String nextJSP = "/TraphacoHYERP/pages/Center/ASM.jsp";
				response.sendRedirect(nextJSP);
			} else
			{

				asmBean.init_New();
				// Save data into session
				session.setAttribute("asmBean", asmBean);

				String nextJSP = "/TraphacoHYERP/pages/Center/ASMNew.jsp";
				response.sendRedirect(nextJSP);

			}
		}
	}
}
