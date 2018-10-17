package geso.traphaco.center.servlets.giamsatbanhang;

import geso.traphaco.center.beans.giamsatbanhang.imp.*;
import geso.traphaco.center.beans.giamsatbanhang.*;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GiamsatbanhangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	
	public GiamsatbanhangUpdateSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		out.println(userId);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String id = util.getId(querystring);
		
		IGiamsatbanhang gsbhBean = new Giamsatbanhang(id);
		gsbhBean.setUserId(userId);
		
		session.setAttribute("gsbhBean", gsbhBean);
		String nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHangUpdate.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IGiamsatbanhang gsbhBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null)
		{
			gsbhBean = new Giamsatbanhang("");
		} else
		{
			gsbhBean = new Giamsatbanhang(id);
		}
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		gsbhBean.setUserId(userId);
		
		String TenGSBH = util.antiSQLInspection(request.getParameter("TenGSBH"));
		if (TenGSBH == null)
			TenGSBH = "";
		gsbhBean.setTen(TenGSBH);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "0";
		else
			trangthai = "1";
		gsbhBean.setTrangthai(trangthai);
		
		String DiaChi = util.antiSQLInspection(request.getParameter("DiaChi"));
		if (DiaChi == null)
			DiaChi = "";
		gsbhBean.setDiachi(DiaChi);
		
		String DienThoai = util.antiSQLInspection(request.getParameter("DienThoai"));
		if (DienThoai == null)
			DienThoai = "";
		gsbhBean.setSodienthoai(DienThoai);
		
		String Email = util.antiSQLInspection(request.getParameter("Email"));
		if (Email == null)
			Email = "";
		gsbhBean.setEmail(Email);
		
		String NCC = util.antiSQLInspection(request.getParameter("NCC"));
		if (NCC == null)
			NCC = "";
		gsbhBean.setNccId(NCC);
		
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		gsbhBean.setDvkdId(dvkdId);
		
		String KenhBH = util.antiSQLInspection(request.getParameter("KenhBH"));
		if (KenhBH == null)
			KenhBH = "";
		gsbhBean.setKbhId(KenhBH);
		
		String vungId = util.antiSQLInspection(request.getParameter("vungId"));
		if (vungId == null)
			vungId = "";
		gsbhBean.setVungId(vungId);
		
		String bmId = util.antiSQLInspection(request.getParameter("bmId"));
		if (bmId == null)
			bmId = "";
		gsbhBean.setBmId(bmId);
		
		String nganhang = util.antiSQLInspection(request.getParameter("NganHang"));
		if (nganhang == null)
			nganhang = "";
		gsbhBean.setNganHang(nganhang);
		
		String chinhanh = util.antiSQLInspection(request.getParameter("ChiNhanh"));
		if (chinhanh == null)
			chinhanh = "";
		gsbhBean.setChiNhanh(chinhanh);
		
		String manhanvien = util.antiSQLInspection(request.getParameter("MaNhanVien"));
		if (manhanvien == null)
			manhanvien = "";
		gsbhBean.setMaNhanVien(manhanvien);
		
		String mathuviec = util.antiSQLInspection(request.getParameter("MaThuViec"));
		if (mathuviec == null)
			mathuviec = "";
		gsbhBean.setMaThuViec(mathuviec);
		
		// lay khuvuc ids
		String[] kvIds = request.getParameterValues("kvIds");
		gsbhBean.setKvIds(kvIds);
		
		String[] npp = request.getParameterValues("npp");
		gsbhBean.setNpp(npp);
		
		String ngaysua = getDateTime();
		gsbhBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		gsbhBean.setNguoisua(nguoisua);
		
		String mact = util.antiSQLInspection(request.getParameter("mact"));
		if (mact == null)
			mact = "";
		gsbhBean.setMaCt(mact);
		
		String vitri = util.antiSQLInspection(request.getParameter("vitri"));
		if (vitri == null)
			vitri = "";
		gsbhBean.setVitri(vitri);
		
		String vungtt = util.antiSQLInspection(request.getParameter("vungtt"));
		if (vungtt == null)
			vungtt = "";
		gsbhBean.setVungTT(vungtt);
		
		String sotk = util.antiSQLInspection(request.getParameter("sotk"));
		if (sotk == null)
			sotk = "";
		gsbhBean.setSotk(sotk);
		
		String dakyhd = util.antiSQLInspection(request.getParameter("dakyhd"));
		if (dakyhd == null)
			dakyhd = "";
		gsbhBean.setDakyHD(dakyhd);
		
		String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		if (ghichu == null)
			ghichu = "";
		gsbhBean.setGhichu(ghichu);
		
		String loaihd = util.antiSQLInspection(request.getParameter("loaihd"));
		if (loaihd == null)
			loaihd = "";
		gsbhBean.setLoaiHD(loaihd);
		
		String ngaykthd = util.antiSQLInspection(request.getParameter("ngaykthd"));
		if (ngaykthd == null)
			ngaykthd = "";
		gsbhBean.setNgayketthucHD(ngaykthd);
		
		String ngaykyhd = util.antiSQLInspection(request.getParameter("ngaykyhd"));
		if (ngaykyhd == null)
			ngaykyhd = "";
		gsbhBean.setNgaykyHD(ngaykyhd);
		
		String ngayvaoct = util.antiSQLInspection(request.getParameter("ngayvaoct"));
		if (ngayvaoct == null)
			ngayvaoct = "";
		gsbhBean.setNgayvaoct(ngayvaoct);
		
		String sodtct = util.antiSQLInspection(request.getParameter("sodtct"));
		if (sodtct == null)
			sodtct = "";
		gsbhBean.setSoDTcongty(sodtct);
		
		String sonamlamviec = util.antiSQLInspection(request.getParameter("sonamlamviec"));
		if (sonamlamviec == null)
			sonamlamviec = "";
		gsbhBean.setSonamlamviec(sonamlamviec);
		
		String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
		if (maFAST == null)
			maFAST = "";
		gsbhBean.setMaFAST(maFAST);
		
		boolean error = false;
		vungId = util.Doisangchuoi(request.getParameterValues("vungId"));
		String kvId = util.Doisangchuoi(request.getParameterValues("kvId"));
		
		String nppId = util.Doisangchuoi(request.getParameterValues("npp"));
		
		gsbhBean.setNppIds(nppId);
		
		if (vungId == null)
			vungId = "";
		gsbhBean.setVungId(vungId);
		
		if (kvId == null)
			kvId = "";
		gsbhBean.setKvId(kvId);
	
		
		System.out.println("[nppId]" + nppId);
		
		String tendangnhap = util.antiSQLInspection(request.getParameter("tendangnhap"));
		String matkhau = util.antiSQLInspection(request.getParameter("matkhau"));
		
		gsbhBean.setTenDangNhap(tendangnhap);
		gsbhBean.setMatKhau(matkhau);
		
		System.out.println("[TenDangNhap]" + tendangnhap + "[MatKhau]" + matkhau);
		
		if (kvIds != null && kvIds[0].trim().length() == 0)
		{
			gsbhBean.setMessage("vui long chon khu vuc");
			error = true;
		}
		if (NCC.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui Long Chon Nha Cung Cap");
			error = true;
		}
		
		if (dvkdId.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui Long Chon Don Vi Kinh Doanh");
			error = true;
		}
		
		if (Email.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui Long Nhap Email");
			error = true;
		}
		
		if (DienThoai.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui Long Nhap So Dien Thoai");
			error = true;
		}
		
		if (DiaChi.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui Long Nhap Dia Chi");
			error = true;
		}
		
		if (TenGSBH.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui Long Nhap Ten Giam Sat Ban Hang");
			error = true;
		}
		
		
		if (matkhau.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui Long Nhap Mat Khau");
			error = true;
		}
		
		
		String nextJSP = "";
		String action = request.getParameter("action");
		IGiamsatbanhangList obj = new GiamsatbanhangList();
		if (!error)
		{
			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!(gsbhBean.CreateGsbh(request))) 
					{
						session.setAttribute("gsbhBean", gsbhBean);
						gsbhBean.setUserId(userId);
						nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHangNew.jsp";
						
					} else
					{
						obj.setUserId(userId);
						obj.init("");
						obj.setCrrSplitting(obj.getTheLastSplitting() <= 15 ? obj.getTheLastSplitting() : 15);
						session.setAttribute("obj", obj);
						nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHang.jsp";
					}
					
				} else
				{
					if (!(gsbhBean.UpdateGsbh(request)))
					{
						session.setAttribute("gsbhBean", gsbhBean);
						nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHangUpdate.jsp";
						// response.sendRedirect(nextJSP);
					} else
					{
						
						obj.setUserId(userId);
						obj.init("");
						obj.setCrrSplitting(obj.getTheLastSplitting() <= 15 ? obj.getTheLastSplitting() : 15);
						session.setAttribute("obj", obj);
						nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHang.jsp";
					}
				}
			} else
			{
				gsbhBean.createRS();
				gsbhBean.setUserId(userId);
				session.setAttribute("gsbhBean", gsbhBean);
				
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHangNew.jsp";
				} else
				{
					nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHangUpdate.jsp";
				}
			}
		} else
		{
			gsbhBean.createRS();
			gsbhBean.setUserId(userId);
			session.setAttribute("gsbhBean", gsbhBean);
			
			if (id == null)
			{
				nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHangNew.jsp";
			} else
			{
				nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHangUpdate.jsp";
			}
		}
		response.sendRedirect(nextJSP);
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}
