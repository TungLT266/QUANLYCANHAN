package geso.traphaco.distributor.servlets.dondathang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.hopdong.IErpDonhangNppETC;
import geso.traphaco.distributor.beans.hopdong.IErpDonhangNppETCList;
import geso.traphaco.distributor.beans.hopdong.imp.ErpDonhangNppETC;
import geso.traphaco.distributor.beans.hopdong.imp.ErpDonhangNppETCList;

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

@WebServlet("/ErpDuyetDdhKhacSvl")
public class ErpDuyetDdhKhacSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public ErpDuyetDdhKhacSvl()
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
			IErpDonhangNppETC lsxBean = new ErpDonhangNppETC(id);
			lsxBean.setUserId(userId);
			
			String nextJSP = "";
			lsxBean.init();
			
			session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			session.setAttribute("khoId", lsxBean.getKhoNhapId());
			
			if (querystring.contains("display"))
			{
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCDisplay.jsp";
			} else if (querystring.contains("duyet"))
			{
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCDuyetDisplay.jsp";
			} else
			{
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
			}
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
			
			IErpDonhangNppETC lsxBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
			{
				lsxBean = new ErpDonhangNppETC("");
			} else
			{
				lsxBean = new ErpDonhangNppETC(id);
			}
			
			lsxBean.setUserId(userId);
			
			String tungay = util.antiSQLInspection(request.getParameter("hopdong_tungay"));
			if (tungay == null || tungay.trim().length() <= 0)
				tungay = getDateTime();
			lsxBean.setTungay(tungay);
			
			String denngay = util.antiSQLInspection(request.getParameter("hopdong_denngay"));
			if (denngay == null || denngay.trim().length() <= 0)
				denngay = getDateTime();
			lsxBean.setDenngay(denngay);
			
			String mahopdong = util.antiSQLInspection(request.getParameter("mahopdong"));
			if (mahopdong == null)
				mahopdong = "";
			lsxBean.setMahopdong(mahopdong);
			
			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";
			lsxBean.setGhichu(ghichu);
			
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";
			lsxBean.setKhoNhapId(khonhapId);
			
			String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
			if (dvkdId == null)
				dvkdId = "";
			lsxBean.setDvkdId(dvkdId);
			
			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null)
				kbhId = "";
			lsxBean.setKbhId(kbhId);
			
			String gsbhId = util.antiSQLInspection(request.getParameter("gsbhId"));
			if (gsbhId == null)
				gsbhId = "";
			lsxBean.setGsbhId(gsbhId);
			
			String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
			if (ddkdId == null)
				ddkdId = "";
			lsxBean.setDdkdId(ddkdId);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			lsxBean.setNppId(nppId);
			
			String khId = util.antiSQLInspection(request.getParameter("khId"));
			if (khId == null)
				khId = "";
			lsxBean.setKhId(khId);
			
			String vat = util.antiSQLInspection(request.getParameter("ptVat"));
			if (vat == null)
				vat = "";
			lsxBean.setVat(vat);
			
			String ptChietkhau = util.antiSQLInspection(request.getParameter("ptChietkhau"));
			if (ptChietkhau == null)
				ptChietkhau = "0";
			lsxBean.setChietkhau(ptChietkhau);
			
			String loaidonhang = util.antiSQLInspection(request.getParameter("loaidonhang"));
			if (loaidonhang == null)
				loaidonhang = "0";
			lsxBean.setLoaidonhang(loaidonhang);
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] soluongton = request.getParameterValues("soluongton");
			lsxBean.setSpSoluongton(soluongton);
			
			String[] dongia = request.getParameterValues("dongia");
			lsxBean.setSpGianhap(dongia);
			
			String[] spQuyDoi = request.getParameterValues("spQuyDoi");
			lsxBean.setSpQuyDoi(spQuyDoi);
			
			String[] spChietkhau = request.getParameterValues("chietkhau");
			lsxBean.setSpChietkhau(spChietkhau);
			
			String[] spVat = request.getParameterValues("spvat");
			lsxBean.setSpVat(spVat);
			
			
			String[] trongluong = request.getParameterValues("spTrongLuong");
			lsxBean.setSpTrongluong(trongluong);
			
			String[] thetich = request.getParameterValues("spTheTich");
			lsxBean.setSpThetich(thetich);
			
			String[] spTungay = request.getParameterValues("tungay");
			lsxBean.setSpTungay(spTungay);
			
			String[] spDenngay = request.getParameterValues("denngay");
			lsxBean.setSpDenngay(spDenngay);
			
			// THEM CAC LOAI CHIET KHAU
			String[] dhCK_diengiai = request.getParameterValues("dhCK_diengiai");
			lsxBean.setDhck_Diengiai(dhCK_diengiai);
			String[] dhCK_giatri = request.getParameterValues("dhCK_giatri");
			lsxBean.setDhck_giatri(dhCK_giatri);
			String[] dhCK_loai = request.getParameterValues("dhCK_loai");
			lsxBean.setDhck_loai(dhCK_loai);
			
			String action = request.getParameter("action");
			System.out.println(" action :" + action);
			
			if (action.equals("save"))
			{
				if (id == null)
				{
					boolean kq = lsxBean.createNK();
					if (!kq)
					{
						System.out.println(" vao day 2.2");
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						System.out.println(" vao day 2.3");
						IErpDonhangNppETCList obj = new ErpDonhangNppETCList();
						obj.setLoaidonhang(loaidonhang);
						
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);
						
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETC.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					boolean kq = lsxBean.updateNK("1");
					if (!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IErpDonhangNppETCList obj = new ErpDonhangNppETCList();
						obj.setLoaidonhang(loaidonhang);
						
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETC.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else if (action.equals("duyet"))
			{
				Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
				for (int i = 0; i < spMa.length; i++)
				{
					// System.out.println("---SP MA LA: " + spMa[i]);
					String temID = spMa[i];
					
					String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
					String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
					
					String[] spNgayHetHan = request.getParameterValues(temID + "_spNGAYHETHAN");
					
					if (soLUONGXUAT != null)
					{
						for (int j = 0; j < soLUONGXUAT.length; j++)
						{
							if (soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0)
							{
								// System.out.println("---KEY SVL: " + ( spId[i] + "__" +
								// spLoai[i] + "__" + spScheme[i].trim() + "__" + spSOLO[j] ) +
								// "   --- GIA TRI: " + soLUONGXUAT[j].replaceAll(",", "") );
								sanpham_soluong.put(spMa[i] + "__" + spSOLO[j] + "__" + spNgayHetHan[j], soLUONGXUAT[j].replaceAll(",", ""));
							}
						}
					}
				}
				
				lsxBean.setSanpham_Soluong(sanpham_soluong);
				
				String dungchungkenh = request.getParameter("dungchungkenh");
				if (dungchungkenh == null)
					dungchungkenh = "0";
				lsxBean.setDungchungKenh(dungchungkenh);
				
				if (!lsxBean.duyetETC( session.getAttribute("congtyId").toString() ))
				{
					lsxBean.init();
					lsxBean.setSanpham_Soluong(sanpham_soluong);
					session.setAttribute("lsxBean", lsxBean);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCDuyetDisplay.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					/*
					 * IErpDuyetddhNppList obj = new ErpDuyetddhNppList();
					 * obj.setUserId(userId); obj.init("");
					 * 
					 * session.setAttribute("obj", obj);
					 * 
					 * String nextJSP =
					 * "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCDuyetDisplay.jsp" ;
					 * response.sendRedirect(nextJSP);
					 */
					lsxBean = new ErpDonhangNppETC(id);
					lsxBean.setUserId(userId);
					lsxBean.init();
					lsxBean.setSanpham_Soluong(sanpham_soluong);
					session.setAttribute("lsxBean", lsxBean);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCDuyetDisplay.jsp";
					response.sendRedirect(nextJSP);
				}
			} else
			{
				System.out.println("id : " + userId);
				lsxBean.createRs();
				
				session.setAttribute("dvkdId", lsxBean.getDvkdId());
				session.setAttribute("kbhId", lsxBean.getKbhId());
				session.setAttribute("khoId", lsxBean.getKhoNhapId());
				session.setAttribute("nppId", request.getParameter("nppId"));
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCNew.jsp";
				if (id != null)
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
				}
				
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
