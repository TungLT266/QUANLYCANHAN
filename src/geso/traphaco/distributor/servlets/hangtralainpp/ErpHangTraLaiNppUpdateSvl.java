package geso.traphaco.distributor.servlets.hangtralainpp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.hangtralai.IErpHangTraLaiNpp;
import geso.traphaco.distributor.beans.hangtralai.IErpHangTraLaiNppList;
import geso.traphaco.distributor.beans.hangtralai.imp.ErpHangTraLaiNpp;
import geso.traphaco.distributor.beans.hangtralai.imp.ErpHangTraLaiNppList;

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

public class ErpHangTraLaiNppUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	public ErpHangTraLaiNppUpdateSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			session.setMaxInactiveInterval(30000);
			
			Utility util = new Utility();
			
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IErpHangTraLaiNpp lsxBean = new ErpHangTraLaiNpp(id);
			lsxBean.setUserId(userId);
			
			String nextJSP = "";
			
			lsxBean.init();
			session.setAttribute("khoxuat", lsxBean.getKhoXuatId());
			session.setAttribute("kenhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			
			if (!querystring.contains("display"))
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHangTraLaiNppUpdate.jsp";
			else
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHangTraLaiNppDisplay.jsp";
			
			session.setAttribute("lsxBean", lsxBean);
			response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpHangTraLaiNpp lsxBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
			{
				lsxBean = new ErpHangTraLaiNpp("");
			} else
			{
				
				lsxBean = new ErpHangTraLaiNpp(id);
			}
			
			lsxBean.setUserId(userId);
			
			String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
			if (ngayyeucau == null || ngayyeucau.trim().length() <= 0)
				ngayyeucau = getDateTime();
			lsxBean.setNgayyeucau(ngayyeucau);
			
			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";
			lsxBean.setGhichu(ghichu);
			
			String so = util.antiSQLInspection(request.getParameter("so"));
			if (so == null)
				so = "";
			lsxBean.setSo(so);
			
			String khoxuatId = util.antiSQLInspection(request.getParameter("khoxuatId"));
			if (khoxuatId == null)
				khoxuatId = "";
			lsxBean.setKhoXuatId(khoxuatId);
			session.setAttribute("khoxuat", lsxBean.getKhoXuatId());
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			lsxBean.setNppId(nppId);
			
			String khId = util.antiSQLInspection(request.getParameter("khId"));
			if (khId == null)
				khId = "";
			lsxBean.setKhId(khId);
			
			String dtId = util.antiSQLInspection(request.getParameter("dtId") == null ? "" : request.getParameter("dtId"));
			lsxBean.setDtId(dtId);
			
			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null)
				kbhId = "";
			lsxBean.setKbhId(kbhId);
			session.setAttribute("kenhId", lsxBean.getKbhId());
			
			String doituong = util.antiSQLInspection(request.getParameter("doituong") == null ? "" : request.getParameter("doituong"));
			lsxBean.setDoituong(doituong);
			
			String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon") == null ? "" : request.getParameter("sohoadon"));
			lsxBean.setSoHoaDon(sohoadon);
			
			String kyhieu = util.antiSQLInspection(request.getParameter("kyhieu") == null ? "" : request.getParameter("kyhieu"));
			lsxBean.setKyHieu(kyhieu);
			
			String ngayhoadon = util.antiSQLInspection(request.getParameter("ngayhoadon") == null ? "" : request.getParameter("ngayhoadon"));
			lsxBean.setNgayHoaDon(ngayhoadon);
			
			String sohs = util.antiSQLInspection(request.getParameter("so") == null ? "" : request.getParameter("so"));
			lsxBean.setSo(sohs);
			
			String loaitra = util.antiSQLInspection(request.getParameter("loaitra") == null ? "" : request.getParameter("loaitra"));
			lsxBean.setLoaitra(loaitra);
			String loaihd = util.antiSQLInspection(request.getParameter("loaihd") == null ? "" : request.getParameter("loaihd"));
			lsxBean.setLoaihd(loaihd);
			
			String dhid = util.antiSQLInspection(request.getParameter("donhangid") == null ? "" : request.getParameter("donhangid"));
			lsxBean.setDhid(dhid);
			
			String makh = util.antiSQLInspection(request.getParameter("makhachhang") == null ? "" : request.getParameter("makhachhang"));
			lsxBean.setMakh(makh);
		
			System.out.println("don hang ::::::::"+dhid);
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] tonkho = request.getParameterValues("tonkho");
			lsxBean.setSpTonkho(tonkho);
			
			String[] dongia = request.getParameterValues("dongia");
			lsxBean.setSpGianhap(dongia);
			
			String[] solo = request.getParameterValues("solo");
			lsxBean.setSpSolo(solo);
			
			String[] ngayhethan = request.getParameterValues("ngayhethan");
			lsxBean.setSpNgayHetHan(ngayhethan);
			
			String[] spGhiChu = request.getParameterValues("spGhiChu");
			lsxBean.setSpGhiChu(spGhiChu);
			
			String[] spVat = request.getParameterValues("spVat");
			lsxBean.setSpVat(spVat);
			
			String action = request.getParameter("action");
			
			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!lsxBean.createNK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHangTraLaiNppNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IErpHangTraLaiNppList obj = new ErpHangTraLaiNppList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);
						
						response.sendRedirect("/TraphacoHYERP/pages/Distributor/ErpHangTraLaiNpp.jsp");
					}
				} else
				{
					if (!lsxBean.updateNK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHangTraLaiNppUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IErpHangTraLaiNppList obj = new ErpHangTraLaiNppList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHangTraLaiNpp.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else
			{
				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHangTraLaiNppNew.jsp";
				if (id != null)
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHangTraLaiNppUpdate.jsp";
				
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}
