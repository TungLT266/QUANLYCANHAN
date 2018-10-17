package geso.traphaco.center.servlets.nhaphanphoi;

import geso.traphaco.center.beans.nhaphanphoi.INhaphanphoi;
import geso.traphaco.center.beans.nhaphanphoi.INhaphanphoiList;
import geso.traphaco.center.beans.nhaphanphoi.imp.Nhaphanphoi;
import geso.traphaco.center.beans.nhaphanphoi.imp.NhaphanphoiList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhaphanphoiUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public NhaphanphoiUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		INhaphanphoi nppBean;

		// this.out = response.getWriter();
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		System.out.println(userId);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		String id = util.getId(querystring);
		nppBean = new Nhaphanphoi(id, "");
		nppBean.setUserId(userId);

		session.setAttribute("nppBean", nppBean);
		String nextJSP = "/TraphacoHYERP/pages/Center/NhaPhanPhoiUpdate.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		INhaphanphoi nppBean;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		this.out = response.getWriter();

		Utility util = new Utility();

		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null)
		{
			nppBean = new Nhaphanphoi("", request.getParameter("isKH"));
		} else
		{
			nppBean = new Nhaphanphoi(id, request.getParameter("isKH"));
		}

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		nppBean.setUserId(userId);

		String nppTen = util.antiSQLInspection(request.getParameter("nppTen"));
		if (nppTen == null)
			nppTen = "";
		nppBean.setTen(nppTen);

		String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));
		if (trangthai == null)
			trangthai = "0";
		else
			trangthai = "1";
		nppBean.setTrangthai(trangthai);

		String prisec = util.antiSQLInspection(request.getParameter("prisec"));
		if (prisec == null)
			prisec = "0";
		else
			prisec = "1";
		nppBean.setPriSec(prisec);

		String diachi = util.antiSQLInspection(request.getParameter("DiaChi"));
		if (diachi == null)
			diachi = "";
		nppBean.setDiachi(diachi);
		
		String diachixhd = util.antiSQLInspection(request.getParameter("diachixhd"));
		if (diachixhd == null)
			diachixhd = "";
		nppBean.setDiaChiXuatHoaDon(diachixhd);

		String masothue = util.antiSQLInspection(request.getParameter("masothue"));
		if (masothue == null)
			masothue = "";
		nppBean.setMaSoThue(masothue);

		String khottid = util.antiSQLInspection(request.getParameter("khottid"));
		if (khottid == null)
			khottid = "";
		nppBean.setKhoTT(khottid);

		String tpId = util.antiSQLInspection(request.getParameter("tpId"));
		if (tpId == null)
			tpId = "";
		nppBean.setTpId(tpId);

		String qhId = util.antiSQLInspection(request.getParameter("qhId"));
		if (qhId == null)
			qhId = "";
		nppBean.setQhId(qhId);

		String ma = util.antiSQLInspection(request.getParameter("maSAP"));
		if (ma == null)
			ma = "";
		nppBean.setMaSAP(ma);

		String dienthoai = util.antiSQLInspection(request.getParameter("DienThoai"));
		if (dienthoai == null)
			dienthoai = "";
		nppBean.setSodienthoai(dienthoai);

		String kvId = util.antiSQLInspection(request.getParameter("kvId"));
		if (kvId == null)
			kvId = "";
		nppBean.setKvId(kvId);

		String tennguoidaidien = util.antiSQLInspection(request.getParameter("ddthd"));
		if (tennguoidaidien == null)
			tennguoidaidien = "";
		nppBean.setTenNguoiDaiDien(tennguoidaidien);

		String fax = util.antiSQLInspection(request.getParameter("fax"));
		if (fax == null)
			fax = "";
		nppBean.setFAX(fax);

		String email = util.antiSQLInspection(request.getParameter("email"));
		if (email == null)
			email = "";
		nppBean.setEmail(email);

		String httt = util.antiSQLInspection(request.getParameter("httt"));
		if (httt == null)
			httt = "";
		nppBean.setHinhThucThanhToan(httt);

		String nganhang = util.antiSQLInspection(request.getParameter("nganhang"));
		if (nganhang == null)
			nganhang = "";
		nppBean.setNganHang(nganhang);

		String sotaikhoan = util.antiSQLInspection(request.getParameter("sotaikhoan"));
		if (sotaikhoan == null)
			sotaikhoan = "";
		nppBean.setSoTK(sotaikhoan);
		
		String tenkyhd = util.antiSQLInspection(request.getParameter("tenkyhd"));
		if (tenkyhd == null)
			tenkyhd = "";
		nppBean.setTenKyHd(tenkyhd);
		
		String gsbhId = util.antiSQLInspection(request.getParameter("gsbhId"));
		if (gsbhId == null)
			gsbhId = "";
		nppBean.setGsbhId(gsbhId);
		
		String manx = util.antiSQLInspection( request.getParameter("manx")==null?"":request.getParameter("manx").trim());
		nppBean.setMaNX(manx);
		
		String makho = util.antiSQLInspection( request.getParameter("makho")==null?"":request.getParameter("makho").trim());
		nppBean.setMaKho(makho);  
		
		String hanmucdoanhthu = util.antiSQLInspection( request.getParameter("hanmucdoanhthu")==null?"":request.getParameter("hanmucdoanhthu").trim().replaceAll(",", ""));
		nppBean.setHanmucdoanhthu(hanmucdoanhthu);  
	
		String loaiNPP = request.getParameter("loaiNPP");
		if(loaiNPP == null)
			loaiNPP = "0";
		nppBean.setLoaiNPP(loaiNPP);
		
		String tructhuocId = request.getParameter("tructhuocId");
		if(tructhuocId == null)
			tructhuocId = "";
		nppBean.setTructhuocId(tructhuocId);
		
		String nvbhId = request.getParameter("nvbhId");
		if(nvbhId == null)
			nvbhId = "";
		nppBean.setNvbhId(nvbhId);
		
		String kyhieuhd = util.antiSQLInspection(request.getParameter("kyhieuhd"));
		if (kyhieuhd == null)
			kyhieuhd = "";
		nppBean.setKyhieuHD(kyhieuhd);
		
		String soHDTu = util.antiSQLInspection(request.getParameter("soHDTu"));
		if (soHDTu == null)
			soHDTu = "";
		nppBean.setSoHDTu(soHDTu);
		
		String soHDDen = util.antiSQLInspection(request.getParameter("soHDDen"));
		if (soHDDen == null)
			soHDDen = "";
		nppBean.setSoHDDen(soHDDen);
		
		String chietkhau = util.antiSQLInspection(request.getParameter("chietkhau"));
		if (chietkhau == null)
			chietkhau = "";
		nppBean.setChietkhau(chietkhau);
		
		String xuattaikho= util.antiSQLInspection(request.getParameter("xuattaikho"));
		if (xuattaikho == null)
			xuattaikho = "";
		nppBean.setXuattaikho(xuattaikho);
		
		String tuxuatETC = request.getParameter("tuxuatETC");
		if (tuxuatETC == null)
			tuxuatETC = "0";
		nppBean.setTuxuatkhoETC(tuxuatETC);
		System.out.println("TU XUAT ETC:::::: " + tuxuatETC );
		
		String tutaohoadon = request.getParameter("tutaohoadon");
		if (tutaohoadon == null)
			tutaohoadon = "0";
		nppBean.setTutaohoadonOTC(tutaohoadon);
		
		String dungchungkenh = request.getParameter("dungchungkenh");
		if (dungchungkenh == null)
			dungchungkenh = "0";
		nppBean.setDungchungkenh(dungchungkenh);
		
		String loaicn = request.getParameter("loaicn");
		if (loaicn == null)
			loaicn = "";
		nppBean.setLoaiCN(loaicn);
		
		String hanmucno = request.getParameter("hanmucno");
		if (hanmucno == null)
			hanmucno = "";
		nppBean.setHanmucno(hanmucno.replace(",", ""));
		
		String songayno = request.getParameter("songayno");
		if (songayno == null)
			songayno = "";
		nppBean.setSongayno(songayno.replace(",", ""));
		
		String CMTND = request.getParameter("cmnd");
		if (CMTND == null)
			CMTND = "";
		nppBean.setCMTND(CMTND);
		
		String thukho = request.getParameter("tenthukho");
		if (thukho == null)
			thukho = "";
		nppBean.setThuKho(thukho);
		
		String taiKhoanCongNo = request.getParameter("taiKhoanCongNo");
		if (taiKhoanCongNo != null)
			nppBean.setTaiKhoanCongNo(taiKhoanCongNo);
		
		String taiKhoanNoiBo = request.getParameter("taiKhoanNoiBo");
		if (taiKhoanNoiBo != null)
			nppBean.setTaiKhoanNoiBo(taiKhoanNoiBo);
		
		System.out.println("CMND ________"+CMTND);
		
		String action = request.getParameter("action");

		if (action.equals("save"))
		{
			if (id == null)
			{
				if (!(nppBean.CreateNpp(request)))
				{
					session.setAttribute("nppBean", nppBean);
					nppBean.setUserId(userId);
					String nextJSP = "/TraphacoHYERP/pages/Center/NhaPhanPhoiNew.jsp";
					response.sendRedirect(nextJSP);
				} 
				else
				{
					INhaphanphoiList obj = new NhaphanphoiList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
					obj.setIsKhachhang(request.getParameter("isKH"));
					obj.init("");
					String nextJSP = "/TraphacoHYERP/pages/Center/NhaPhanPhoi.jsp";
					response.sendRedirect(nextJSP);
				}
			} 
			else
			{
				if (!(nppBean.UpdateNpp(request)))
				{
					session.setAttribute("nppBean", nppBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/NhaPhanPhoiUpdate.jsp";
					response.sendRedirect(nextJSP);
				} 
				else
				{
					INhaphanphoiList obj = new NhaphanphoiList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
					obj.setIsKhachhang(request.getParameter("isKH"));
					obj.init("");
					String nextJSP = "/TraphacoHYERP/pages/Center/NhaPhanPhoi.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} 
		else
		{
			nppBean.createRS();
			nppBean.setUserId(userId);
			session.setAttribute("nppBean", nppBean);
			String nextJSP;
			if (id == null)
			{
				nextJSP = "/TraphacoHYERP/pages/Center/NhaPhanPhoiNew.jsp";
			} 
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Center/NhaPhanPhoiUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}
}
