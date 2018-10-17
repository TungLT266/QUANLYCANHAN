package geso.traphaco.distributor.servlets.dontrahang;

import geso.traphaco.distributor.beans.dontrahang.IDontrahang;
import geso.traphaco.distributor.beans.dontrahang.IDontrahangList;
import geso.traphaco.distributor.beans.dontrahang.imp.Dontrahang;
import geso.traphaco.distributor.beans.dontrahang.imp.DontrahangList;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DontrahangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
	public DontrahangUpdateSvl()
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
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
			
			Utility util = new Utility();
			
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IDontrahang lsxBean = new Dontrahang(id);
			lsxBean.setUserId(userId);
			lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	lsxBean.setNpp_duocchon_id(npp_duocchon_id);
			lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			lsxBean.setDoituongId(session.getAttribute("doituongId"));
			
			String nextJSP = "";
			
			lsxBean.init();
			
			if (!querystring.contains("display"))
				nextJSP = "/TraphacoHYERP/pages/Distributor/DonTraHangUpdate.jsp";
			else
				nextJSP = "/TraphacoHYERP/pages/Distributor/DonTraHangDisplay.jsp";
			
			session.setAttribute("lsxBean", lsxBean);
			
			session.setAttribute("kenhId", lsxBean.getKbhId());
			System.out.println("kbhId142512141:"+lsxBean.getKbhId());
			
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("khoId", lsxBean.getKhoXuatId());
			session.setAttribute("nppId",lsxBean.getNppId() );
			session.setAttribute("khachhangId",lsxBean.getKhId() );
			
			response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		
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
			IDontrahang lsxBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
			{
				lsxBean = new Dontrahang("");
			} else
			{
				
				lsxBean = new Dontrahang(id);
			}
			
			lsxBean.setUserId(userId);
			lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	lsxBean.setNpp_duocchon_id(npp_duocchon_id);
			
			lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			lsxBean.setDoituongId(session.getAttribute("doituongId"));
			
			String loaidonhang = request.getParameter("loaidonhang");
		    if(loaidonhang == null)
		    	loaidonhang = "";
		    lsxBean.setLoaidonhang(loaidonhang);
		    
			String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
			if (ngayyeucau == null || ngayyeucau.trim().length() <= 0)
				ngayyeucau = getDateTime();
			lsxBean.setNgayyeucau(ngayyeucau);
			
			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";
			lsxBean.setGhichu(ghichu);
			
			
			String sochungtu = util.antiSQLInspection( request.getParameter("sochungtu")==null? "":request.getParameter("sochungtu") );
			lsxBean.setSoChungTu(sochungtu);

			
			String khoxuatId = util.antiSQLInspection(request.getParameter("khoxuatId"));
			if (khoxuatId == null)
				khoxuatId = "";
			lsxBean.setKhoXuatId(khoxuatId);
			
			String ptChietkhau = util.antiSQLInspection(request.getParameter("ptChietkhau"));
	    	if(ptChietkhau == null || ptChietkhau == "")	    		
	    		ptChietkhau = "0";
	    	lsxBean.setTienCK(ptChietkhau.replaceAll(",", ""));
	    	
	    	String tienbvat = util.antiSQLInspection(request.getParameter("BVAT"));
	    	if(tienbvat == null || tienbvat == "")
	    		tienbvat = "0";
	    	lsxBean.setTienBvat(tienbvat.replaceAll(",", ""));
			
	    	String tienvat = util.antiSQLInspection(request.getParameter("ptVat"));
	    	if(tienvat == null || tienvat == "" )	
	    		tienvat = "0";
	    	lsxBean.setTienVat(tienvat.replaceAll(",", ""));
	    	
	    	String tienavat = util.antiSQLInspection(request.getParameter("SauVAT"));
	    	if(tienavat == null || tienavat == "")
	    		tienavat = "0";
	    	lsxBean.setTienAVat(tienavat.replaceAll(",", ""));
	    				
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
			
			String[] tichluy = request.getParameterValues("tichluyId");
			String str7 = "";
			if(tichluy != null)
			{
				for(int i = 0; i < tichluy.length; i++)
					str7 += tichluy[i] + ",";
				if(str7.length() > 0)
					str7 = str7.substring(0, str7.length() - 1);
			}
			lsxBean.setTichluyId(str7);
			
			System.out.println("kenhbanhang: "+lsxBean.getKbhId());
			session.setAttribute("kenhId", lsxBean.getKbhId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("khoId", lsxBean.getKhoXuatId());
			session.setAttribute("khachhangId", lsxBean.getKhId());
			session.setAttribute("nppId", request.getParameter("nppId"));
					
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
			
			String[] spVat = request.getParameterValues("spVat");
			lsxBean.setSpVat(spVat);
			
			
			String action = request.getParameter("action");
			
			if( spMa != null  )  //LUU LAI THONG TIN NGUOI DUNG NHAP
			{
				Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
				for(int i = 0; i < spMa.length; i++ )
				{
					String temID = spMa[i];
					
					String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
					String[] spNgayHetHan = request.getParameterValues(temID + "_spNGAYHETHAN");
					String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
					
					if(soLUONGXUAT != null)
					{
						for(int j = 0; j < soLUONGXUAT.length; j++ )
						{
							if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0 && spSOLO[j].trim().length() > 0 && spNgayHetHan[j].trim().length() > 0 )
							{
								System.out.println(":::: MA HANG: " + temID + " LO XUAT: " + spSOLO[j].trim() );
								sanpham_soluong.put(spMa[i] + "__" + spSOLO[j] + "__" + spNgayHetHan[j], soLUONGXUAT[j].replaceAll(",", "") );
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
						lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
				    	lsxBean.setNpp_duocchon_id(npp_duocchon_id);
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/DonTraHangNew.jsp";
						response.sendRedirect(nextJSP);
					} 
					else
					{
						IDontrahangList obj = new DontrahangList();
						obj.setUserId(userId);
						obj.setTdv_dangnhap_id(tdv_dangnhap_id);
						obj.setNpp_duocchon_id(npp_duocchon_id);
						obj.setLoaidonhang(loaidonhang);
						
						obj.setLoainhanvien(session.getAttribute("loainhanvien"));
						obj.setDoituongId(session.getAttribute("doituongId"));
						
						obj.init("");
						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);
						
						response.sendRedirect("/TraphacoHYERP/pages/Distributor/DonTraHang.jsp");
					}
				} 
				else
				{
					if (!lsxBean.updateNK())
					{
						lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
						lsxBean.setNpp_duocchon_id(npp_duocchon_id);
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/DonTraHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					} 
					else
					{
						IDontrahangList obj = new DontrahangList();
						obj.setUserId(userId);
						obj.setTdv_dangnhap_id(tdv_dangnhap_id);
						obj.setNpp_duocchon_id(npp_duocchon_id);
						obj.setLoainhanvien(session.getAttribute("loainhanvien"));
						obj.setDoituongId(session.getAttribute("doituongId"));
						
						obj.setLoaidonhang(loaidonhang);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/DonTraHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} 
			else
			{
				lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
				lsxBean.setNpp_duocchon_id(npp_duocchon_id);
				lsxBean.createRs();
				
				session.setAttribute("kenhId", lsxBean.getKbhId());
				session.setAttribute("kbhId", lsxBean.getKbhId());
				session.setAttribute("khoId", lsxBean.getKhoXuatId());
				session.setAttribute("khachhangId", lsxBean.getKhId());
				session.setAttribute("nppId", request.getParameter("nppId"));
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Distributor/DonTraHangNew.jsp";
				if (id != null)
					nextJSP = "/TraphacoHYERP/pages/Distributor/DonTraHangUpdate.jsp";
				
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
