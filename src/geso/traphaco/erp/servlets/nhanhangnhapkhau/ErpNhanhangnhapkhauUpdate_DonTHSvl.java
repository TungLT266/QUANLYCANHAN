package geso.traphaco.erp.servlets.nhanhangnhapkhau;

import geso.dms.distributor.util.Utility;
import geso.traphaco.erp.beans.nhanhangnhapkhau.*;
import geso.traphaco.erp.beans.nhanhangnhapkhau.imp.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhanhangnhapkhauUpdate_DonTHSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
	public ErpNhanhangnhapkhauUpdate_DonTHSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.*
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			Utility util = new Utility();
			
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			
			out.println(userId);
			
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IErpNhanhang_DonTH nhBean = new ErpNhanhang_DonTH(id);
			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
			nhBean.setUserId(userId); // phai co UserId truoc khi Init
			nhBean.init();
			
			String nextJSP;
			
			if (request.getQueryString().indexOf("display") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangDisplay_DonTH.jsp";
			}
			else if(request.getQueryString().indexOf("hoantat") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpHTDonMuaHangDisplay.jsp";
			}
			else if(request.getQueryString().indexOf("create_nhanhang") >=0){
				 nhBean = new ErpNhanhang_DonTH();
				 nhBean.setCongtyId((String)session.getAttribute("congtyId"));
				 nhBean.setUserId(userId); // phai co UserId truoc khi Init
				 nhBean.setDonmuahangId(id);
				 nhBean.init_nhanhang();
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_DonTH.jsp";	
			}
			
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_DonTH.jsp";	
			}
			
			session.setAttribute("nhBean", nhBean);			
			response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		String sum = (String) session.getAttribute("sum");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpNhanhang_DonTH nhBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
			if (id == null)
			{
				nhBean = new ErpNhanhang_DonTH("");
			}
			else
			{
				nhBean = new ErpNhanhang_DonTH(id);
			}
			
			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
			nhBean.setUserId(userId);
			
			String ngaygd = util.antiSQLInspection(request.getParameter("ngaynhanhang"));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();
			
			nhBean.setNgaynhanhang(ngaygd);
			
			String ngaychot = util.antiSQLInspection(request.getParameter("ngaychot"));
			if (ngaychot == null || ngaychot == "")
				ngaychot = this.getDateTime();
			
			nhBean.setNgaychot(ngaychot);
			
			String loaihh = util.antiSQLInspection(request.getParameter("loaihh"));
			if (loaihh == null)
				loaihh = "";
			nhBean.setLoaihanghoa(loaihh);
			
			String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
			if (sohoadon == null)
				sohoadon = "";
			nhBean.setSohoadon(sohoadon);
			
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			nhBean.setDiengiai(diengiai);
			
			String soPO = request.getParameter("soPO");
			if (soPO == null)
				soPO = "";
			nhBean.setDonmuahangId(soPO);
			
			String mahangmua = request.getParameter("mahangmua");
			if (mahangmua == null)
				mahangmua = "";
			nhBean.setMahangmuaId(mahangmua);
			
			String dvth = request.getParameter("dvthId");
			if (dvth == null)
				dvth = "";
			nhBean.setDvthId(dvth);
			
			String ndnId = request.getParameter("ndnId");
			if (ndnId == null)
				ndnId = "";
			nhBean.setNdnId(ndnId);
			
			String ldnId = request.getParameter("ldnId");
			if (ldnId == null)
				ldnId = "";
			nhBean.setLdnId("100004"); //FIX LUON LA NHAN HANG TRA VE
			
			String nccId = request.getParameter("nccId");
			if (nccId == null)
				nccId = "";
			nhBean.setNccId(nccId);
			
			String khonhanxl = request.getParameter("khonhanxl");
			if (khonhanxl == null)
				khonhanxl = "";
			nhBean.setKhoCxlId(khonhanxl);
			
			
			
			// Luu lai san pham
			String[] spId = request.getParameterValues("idhangmua");
			String[] spMa = request.getParameterValues("mahangmua");
			String[] spTen = request.getParameterValues("tenhangmua");
			String[] spDonvi = request.getParameterValues("dvdl");
			String[] soluongnhan = request.getParameterValues("soluongnhan");
			String[] hansudung = request.getParameterValues("hansudung");
			String[] soluongdat = request.getParameterValues("soluongdat");
			String[] ngaynhandukien = request.getParameterValues("ngaynhandukien");
			String[] dungsai = request.getParameterValues("dungsai");
			String[] dongia = request.getParameterValues("dongiaMua");
			
			String[] tiente = request.getParameterValues("tiente");
			String[] tygiaquydoi = request.getParameterValues("tygiaquydoi");
			String[] dongiaViet = request.getParameterValues("dongiaViet");
			
			String[] spKhoId = request.getParameterValues("khonhanId");
			String[] spKhoTen = request.getParameterValues("khonhanTen");
			String[] soluongMaxNhan = request.getParameterValues("soluongMaxNhan");
			String[] soluongdanhan = request.getParameterValues("soluongdanhan");
			
			List<ISanpham> spList = new ArrayList<ISanpham>();
			
			if (spMa != null)
			{
				ISanpham sanpham = null;
				int m = 0;
				while (m < spMa.length)
				{
					sanpham = new Sanpham(spId[m], spMa[m], spTen[m], soluongnhan[m].replaceAll(",", ""), hansudung[m], ngaynhandukien[m], soluongdat[m].replaceAll("", ""), spDonvi[m]);
					
					String tem = spId[m] + "." + ngaynhandukien[m];
					String[] khuvuc_id = request.getParameterValues(tem + ".khuvuc_id");
					String[] solo = request.getParameterValues(tem + ".solo");
					
					String[] soluong = request.getParameterValues(tem + ".soluong");
					String[] ngaysanxuat = request.getParameterValues(tem + ".ngaysanxuat");
					String[] ngayhethan = request.getParameterValues(tem + ".ngayhethan");
					
					if(loaihh.equals("0"))
					{
						List<ISpDetail> spConList = new ArrayList<ISpDetail>();
						ISpDetail spCon = null;
						int n = 0;
						while (n < soluong.length)
						{
							if (soluong[n] != "" && solo[n] != "" && ngaysanxuat[n] != "")
							{
								spCon = new SpDetail(spMa[m], solo[n], soluong[n].replaceAll(",", ""), ngaysanxuat[n], ngayhethan[n]);
								spCon.setkhuid(khuvuc_id[n]);
								spConList.add(spCon);
							}
							n++;
						}
						sanpham.setSpDetail(spConList);
					}
					
					sanpham.setDungsai(dungsai[m]);
					sanpham.setDongia(dongia[m]);
					sanpham.setTiente(tiente[m]);
					sanpham.setTigiaquydoi(tygiaquydoi[m]);
					sanpham.setDongiaViet(dongiaViet[m]);
					sanpham.setSoluongDaNhan(soluongdanhan[m]);
					sanpham.setSoluongMaxNhan(soluongMaxNhan[m]);
					 sanpham.setKhoNhanRs(nhBean.getkhoRs(spId[m]));
					if(spKhoId != null)
					{
						sanpham.setKhonhanId(spKhoId[m]);
						sanpham.setKhonhanTen(spKhoTen[m]);
					}
					
					spList.add(sanpham);
					
					m++;
				}
			}
			//System.out.println("So sp trong List: " + spList.size());
			nhBean.setSpList(spList);
			
			String action = request.getParameter("action");
			
			if (action.equals("save"))
			{
				if (id == null) // tao moi
				{
					if (!nhBean.createNhanHang( request.getParameter("congtyId").toString() ))
					{
						nhBean.createRs();
						
						session.setAttribute("nhBean", nhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_DonTH.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						//Do co dung sai nen ko the hoan tat don mua hang tai cho nay ????????
						nhBean.updateDonmuahang(nhBean.getDonmuahangId());
						
						IErpNhanhangList_DonTH obj = new ErpNhanhangList_DonTH();
						
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHang_DonTH.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if (!nhBean.updateNhanHang(request.getParameter("congtyId").toString()))
					{
						nhBean.createRs();
						
						session.setAttribute("nhBean", nhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_DonTH.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						//Do co dung sai nen ko the hoan tat don mua hang tai cho nay ???????
						nhBean.updateDonmuahang(nhBean.getDonmuahangId());
						
						IErpNhanhangList_DonTH obj = new ErpNhanhangList_DonTH();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHang_DonTH.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if ( action.equals("changePO") || action.equals("changeLHH") )
				{
					nhBean.setSpList(new ArrayList<ISanpham>());
					if(action.equals("changeLHH"))
					{
						nhBean.setDonmuahangId("");
					}
					
					String nextJSP;
					nhBean.createRs(); // khoi tao lai san pham
					
					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_DonTH.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_DonTH.jsp";
					}
					
					session.setAttribute("nhBean", nhBean);
					response.sendRedirect(nextJSP);
				}
				else
				{
					String nextJSP;
					nhBean.createRs();
					
					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_DonTH.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_DonTH.jsp";
					}
					
					session.setAttribute("nhBean", nhBean);
					response.sendRedirect(nextJSP);
				}
				
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
