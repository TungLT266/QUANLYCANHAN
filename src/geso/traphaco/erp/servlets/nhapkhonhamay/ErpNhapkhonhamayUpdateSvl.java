package geso.traphaco.erp.servlets.nhapkhonhamay;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.nhapkhonhamay.IErpNhapkhonhamay;
import geso.traphaco.erp.beans.nhapkhonhamay.IErpNhapkhonhamayList;
import geso.traphaco.erp.beans.nhapkhonhamay.ISanpham;
import geso.traphaco.erp.beans.nhapkhonhamay.ISpDetail;
import geso.traphaco.erp.beans.nhapkhonhamay.imp.ErpNhapkhonhamay;
import geso.traphaco.erp.beans.nhapkhonhamay.imp.ErpNhapkhonhamayList;
import geso.traphaco.erp.beans.nhapkhonhamay.imp.Sanpham;
import geso.traphaco.erp.beans.nhapkhonhamay.imp.SpDetail;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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

public class ErpNhapkhonhamayUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
	public ErpNhapkhonhamayUpdateSvl()
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
			IErpNhapkhonhamay nhBean = new ErpNhapkhonhamay(id);
			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
			nhBean.setUserId(userId); // phai co UserId truoc khi Init
			nhBean.init();
			String nextJSP;
			
			if (request.getQueryString().indexOf("display") >= 0)
			{
				System.out.println("vao day");
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMayDisplay.jsp";
			}
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMayUpdate.jsp";	
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
		Utility_Kho util_kho=new Utility_Kho();
		dbutils db=new dbutils();
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
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
			IErpNhapkhonhamay nhBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
			if (id == null)
			{
				nhBean = new ErpNhapkhonhamay("");
			}
			else
			{
				nhBean = new ErpNhapkhonhamay(id);
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
						
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			nhBean.setDiengiai(diengiai);
			
			String soPO = request.getParameter("soPO");
			if (soPO == null)
				soPO = "";
			nhBean.setDonmuahangId(soPO);
			
			String dvth = request.getParameter("dvthId");
			if (dvth == null)
				dvth = "";
			nhBean.setDvthId(dvth);
			
			String ncc = request.getParameter("ncc");
			if (ncc == null)
				ncc = "";
			nhBean.setNcc(ncc);
			
			String[] spid = request.getParameterValues("idhangmua");
			String[] spma = request.getParameterValues("mahangmua");
			String[] spten = request.getParameterValues("tenhangmua");
			//String[] spngaynhan = request.getParameterValues("ngaynhandukien");
			String[] spdv = request.getParameterValues("dvdl");
			String[] spsld = request.getParameterValues("soluongdat");
			String[] spdungsai = request.getParameterValues("dungsai");
			String[] spsln = request.getParameterValues("soluongnhan");
			String[] spgia = request.getParameterValues("gia");
			String[] nhomkenh = request.getParameterValues("nhomkenh");
			String[] thuexuat = request.getParameterValues("thuexuat");
			String[] hansudung = request.getParameterValues("hansudung");
			List<ISanpham> lstsp = new ArrayList<ISanpham>();
			if(spid != null)
			{
				for (int j = 0; j < spid.length; j++) 
				{
					ISanpham sp = new Sanpham();
					sp.setId(spid[j]);
					sp.setMa(spma[j]);
					sp.setDiengiai(spten[j]);
					//sp.setNgaynhandukien(spngaynhan[j]);
					sp.setDvdl(spdv[j]);
					sp.setSoluongdat(spsld[j]);
					sp.setDungsai(spdungsai[j]);
					sp.setSoluongnhan(spsln[j]);
					sp.setGia(spgia[j]);
					sp.setNhomkenh(nhomkenh[j]);
					sp.setThuexuat(thuexuat[j]);
					sp.setHansudung(hansudung[j]);
					String[] solo = request.getParameterValues(spid[j]+".solo");
					String[] soluong = request.getParameterValues(spid[j]+".soluong");
					String[] ngaysanxuat = request.getParameterValues(spid[j]+".ngaysanxuat");
					String[] ngayhethan = request.getParameterValues(spid[j]+".ngayhethan");
					List<ISpDetail> lstspdt = new ArrayList<ISpDetail>();
					for(int i = 0; i < solo.length; i++)
					{
						if (soluong[i] != "" && solo[i] != "" && ngaysanxuat[i] != "" && ngayhethan[i] != "")
						{
							ISpDetail spdt = new SpDetail();
							spdt.setSolo(solo[i]);
							spdt.setSoluong(soluong[i]);
							spdt.setNgaySx(ngaysanxuat[i]);
							spdt.setNgayHethan(ngayhethan[i]);
							lstspdt.add(spdt);
						}
						System.out.println("solo "+ solo[i]);
					}
					sp.setSpDetail(lstspdt);
					lstsp.add(sp);
				}
			}
			nhBean.setSpList(lstsp);
			
			String action = request.getParameter("action");
			if (action.equals("save"))
			{
				if (id == null) // tao moi
				{
					if (!nhBean.createNhanHang())
					{
						nhBean.createRs();
						
						session.setAttribute("nhBean", nhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMayNew.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{ 
						
						IErpNhapkhonhamayList obj = new ErpNhapkhonhamayList();
						
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMay.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if (!nhBean.updateNhanHang())
					{
						nhBean.createRs();
						
						session.setAttribute("nhBean", nhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMayUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						
						IErpNhapkhonhamayList obj = new ErpNhapkhonhamayList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMay.jsp";
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
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMayNew.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMayUpdate.jsp";
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
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMayNew.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMayUpdate.jsp";
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
