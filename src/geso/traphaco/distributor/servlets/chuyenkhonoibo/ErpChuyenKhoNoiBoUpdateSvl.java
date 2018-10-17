package geso.traphaco.distributor.servlets.chuyenkhonoibo;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.chuyenkhonoibo.IErpChuyenKhoNoiBo;
import geso.traphaco.distributor.beans.chuyenkhonoibo.IErpChuyenKhonoiboList;
import geso.traphaco.distributor.beans.chuyenkhonoibo.imp.ErpChuyenKhoNoiBo;
import geso.traphaco.distributor.beans.chuyenkhonoibo.imp.ErpChuyenKhonoiboList;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpChuyenKhoNoiBoUpdateSvl")
public class ErpChuyenKhoNoiBoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public ErpChuyenKhoNoiBoUpdateSvl()
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
			IErpChuyenKhoNoiBo lsxBean = new ErpChuyenKhoNoiBo(id);
			lsxBean.setUserId(userId);
			
			String nextJSP = "";
			
			lsxBean.init();
			session.setAttribute("khoxuat", lsxBean.getKhoXuatId());
			session.setAttribute("kenhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			
			if (!querystring.contains("display"))
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpChuyenKhoNoiBoUpdate.jsp";
			else
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpChuyenKhoNoiBoDisplay.jsp";
			
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
			IErpChuyenKhoNoiBo lsxBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
			{
				System.out.println("Vao day");
				lsxBean = new ErpChuyenKhoNoiBo("");
			} else
			{
				
				lsxBean = new ErpChuyenKhoNoiBo(id);
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
			
			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null)
				kbhId = "";
			lsxBean.setKbhId(kbhId);
			session.setAttribute("kenhId", lsxBean.getKbhId());
			
			// THONG TIN IN
			String lenhdieudong = util.antiSQLInspection(request.getParameter("lenhdieudong"));
			if (lenhdieudong == null)
				lenhdieudong = "";
			lsxBean.setLenhdieudong(lenhdieudong);
			
			String lddcua = util.antiSQLInspection(request.getParameter("lddcua"));
			if (lddcua == null)
				lddcua = "";
			lsxBean.setLDDcua(lddcua);
			
			String lddveviec = util.antiSQLInspection(request.getParameter("lddveviec"));
			if (lddveviec == null)
				lddveviec = "";
			lsxBean.setLDDveviec(lddveviec);
			
			String ngaydieudong = util.antiSQLInspection(request.getParameter("ngaydieudong"));
			if (ngaydieudong == null)
				ngaydieudong = "";
			lsxBean.setNgaydieudong(ngaydieudong);
			
			String sohopdong = util.antiSQLInspection(request.getParameter("sohopdong"));
			if (sohopdong == null)
				sohopdong = "";
			lsxBean.setSohopdong(sohopdong);
			
			String ngayhopdong = util.antiSQLInspection(request.getParameter("ngayhopdong"));
			if (ngayhopdong == null)
				ngayhopdong = "";
			lsxBean.setNgayhopdong(ngayhopdong);
			
			String nguoivanchuyen = util.antiSQLInspection(request.getParameter("nguoivanchuyen"));
			if (nguoivanchuyen == null)
				nguoivanchuyen = "";
			lsxBean.setNguoivanchuyen(nguoivanchuyen);
			
			String ptvanchuyen = util.antiSQLInspection(request.getParameter("ptvanchuyen"));
			if (ptvanchuyen == null)
				ptvanchuyen = "";
			lsxBean.setPtvanchuyen(ptvanchuyen);
			
			String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu") == null ? "" : request.getParameter("sochungtu").trim());
			lsxBean.setSoChungTu(sochungtu);
			
			// System.out.println("----KHO XUAT: " + khoxuatId + "  -- KHO NHAP:  " +
			// khonhapId );
			
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
			
			String action = request.getParameter("action");
			if (spMa != null && action.equals("save")) // LUU LAI THONG TIN NGUOI DUNG
																								 // NHAP
			{
				Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
				for (int i = 0; i < spMa.length; i++)
				{
					//System.out.println("---SP MA LA: " + spMa[i]);
					String temID = spMa[i];
					
					String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
					
					String[] spNgayHetHan = request.getParameterValues(temID + "_spNGAYHETHAN");
					
					String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
					
					if (soLUONGXUAT != null)
					{
						for (int j = 0; j < soLUONGXUAT.length; j++)
						{
							if (soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0)
							{
								sanpham_soluong.put(spMa[i] + "__" + spSOLO[j] + "__" + spNgayHetHan[j], soLUONGXUAT[j].replaceAll(",", ""));
							}
						}
					}
				}
				
				lsxBean.setSanpham_Soluong(sanpham_soluong);
			}
			
			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!lsxBean.createNK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpChuyenKhoNoiBoNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IErpChuyenKhonoiboList obj = new ErpChuyenKhonoiboList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);
						
						response.sendRedirect("/TraphacoHYERP/pages/Distributor/ErpChuyenKhoNoiBo.jsp");
					}
				} else
				{
					if (!lsxBean.updateNK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpChuyenKhoNoiBoUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IErpChuyenKhonoiboList obj = new ErpChuyenKhonoiboList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpChuyenKhoNoiBo.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else
			{
				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpChuyenKhoNoiBoNew.jsp";
				if (id != null)
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpChuyenKhoNoiBoUpdate.jsp";
				
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
