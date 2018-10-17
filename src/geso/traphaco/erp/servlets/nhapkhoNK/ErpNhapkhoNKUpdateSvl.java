package geso.traphaco.erp.servlets.nhapkhoNK;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.shiphang.ISanpham;
import geso.traphaco.erp.beans.shiphang.imp.Sanpham;
import geso.traphaco.erp.beans.nhapkhoNK.*;
import geso.traphaco.erp.beans.nhapkhoNK.imp.*;
import geso.traphaco.erp.beans.nhapkhoNK.ISpDetail;
import geso.traphaco.erp.beans.nhapkhoNK.imp.SpDetail;
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

public class ErpNhapkhoNKUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
	public ErpNhapkhoNKUpdateSvl()
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
			IErpNhapkhoNK nhBean = new ErpNhapkhoNK(id);
			nhBean.setUserId(userId);
			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
			String nextJSP;
			
			if (request.getQueryString().indexOf("chot") >= 0)
			{
				IErpNhapkhoNKList obj = new ErpNhapkhoNKList();
				String nccId = util.antiSQLInspection(request.getParameter("nccId"));
				String msg = nhBean.ChotNhapKho(id, nccId, userId);
				if(msg.trim().length() > 0)
				{
					obj.setmsg(msg);
				}
				
				obj.setUserId(userId);
				obj.setCongtyId((String)session.getAttribute("congtyId"));	
				obj.init("");
				session.setAttribute("obj", obj);			
				response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhapKhoNK.jsp");
				
			}
			else
			{
				if (request.getQueryString().indexOf("display") >= 0)
				{
					
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNKDisplay.jsp";
					
				}
				else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNKUpdate.jsp";
					
				}
				
				nhBean.setCongtyId((String)session.getAttribute("congtyId"));
				nhBean.setUserId(userId); 
				nhBean.init();
				session.setAttribute("nhBean", nhBean);			
				response.sendRedirect(nextJSP);
			}
			
			
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
			IErpNhapkhoNK nhBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
			if (id == null)
			{
				nhBean = new ErpNhapkhoNK("");
			}
			else
			{
				nhBean = new ErpNhapkhoNK(id);
			}
			
			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
			nhBean.setUserId(userId);
			
			String ngaygd = util.antiSQLInspection(request.getParameter("ngaynhap"));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();			
			nhBean.setNgaychungtu(ngaygd);
			
			
			String ngaychot = util.antiSQLInspection(request.getParameter("ngaychot"));
			if (ngaychot == null || ngaychot == "")
				ngaychot = this.getDateTime();		
			nhBean.setNgaychot(ngaychot);
			
			
			String soPO = request.getParameter("poId");
			if (soPO == null)
				soPO = "";
			nhBean.setDonmuahangId(soPO);
			
			
			String nccId = request.getParameter("nccId");
			if (nccId == null)
				nccId = "";
			nhBean.setNccId(nccId);

			
			String ghichu = request.getParameter("ghichu");
			if(ghichu==null){
				ghichu="";
			}
			nhBean.setGhichu(ghichu);
			
						
			// Luu lai san pham
			String[] spId = request.getParameterValues("idhangmua");
			String[] spMa = request.getParameterValues("mahangmua");
			String[] spTen = request.getParameterValues("tenhangmua");
			String[] spDonvi = request.getParameterValues("dvdl");
			String[] soluongdat = request.getParameterValues("soluongdat");
			String[] soluongnhan = request.getParameterValues("soluongnhan");
			String[] soluongMAXnhan = request.getParameterValues("soluongMAXnhan");
			String[] ngaynhap = request.getParameterValues("ngaynhap");
			String[] songayluukho = request.getParameterValues("songayluukho");
			String[] khonhap = request.getParameterValues("khonhapId");
			String[] khonhapten = request.getParameterValues("khonhapten");
			String[] vat = request.getParameterValues("vat");
			String[] hansudung = request.getParameterValues("hansudung");
			String[] dongia = request.getParameterValues("dongiaMua");
			
			String action = request.getParameter("action");
			if (action.equals("save"))
			{
				
				List<ISanpham> spList = new ArrayList<ISanpham>();
				
				if (spMa != null)
				{
					ISanpham sanpham = null;
					int m = 0;
					String slnhan = "0";
					String snluukho = "0";
					while (m < spMa.length)
					{
						if(Integer.parseInt(soluongnhan[m].replaceAll(",", "")) > 0)
						{
							if(soluongnhan[m].trim().length() > 0) slnhan =soluongnhan[m].replaceAll(",", "");
							if(songayluukho[m].trim().length() > 0) snluukho = songayluukho[m].replaceAll(",", "");
							
							sanpham = new Sanpham(spId[m], spMa[m], spTen[m],"", "", ngaynhap[m], soluongdat[m].replaceAll(",", ""), spDonvi[m]);
							
							sanpham.setSoluongnhan(slnhan);
							sanpham.setSoluongMaxNhan(soluongMAXnhan[m].replaceAll(",", ""));
							sanpham.setKhonhanId(khonhap[m]);
							sanpham.setKhonhanTen(khonhapten[m]);
							sanpham.setSongayluukho(snluukho);
							sanpham.setVat(vat[m]);
							sanpham.setDongia(dongia[m]);
							sanpham.setHansudung(hansudung[m]);
							String[] solo = request.getParameterValues(spId[m]+".solo");
							String[] soluong = request.getParameterValues(spId[m]+".soluong");
							String[] ngaysanxuat = request.getParameterValues(spId[m]+".ngaysanxuat");
							String[] ngayhethan = request.getParameterValues(spId[m]+".ngayhethan");
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
							sanpham.setSpDetail(lstspdt);						
							spList.add(sanpham);
						}
												
						m++; 
					}
				}
				//System.out.println("So sp trong List: " + spList.size());
				nhBean.setSpList(spList);
				
				if (id == null) // tao moi
				{
					if (!nhBean.createNhapKho())
					{
						nhBean.createRs();
						
						session.setAttribute("nhBean", nhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNKNew.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{ 
						
						IErpNhapkhoNKList obj = new ErpNhapkhoNKList();
						
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNK.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if (!nhBean.updateNhapKho())
					{
						nhBean.createRs();
						
						session.setAttribute("nhBean", nhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNKUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						
						IErpNhapkhoNKList obj = new ErpNhapkhoNKList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNK.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
					String nextJSP;
					nhBean.createRs();
					
					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNKNew.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNKUpdate.jsp";
					}
					
					session.setAttribute("nhBean", nhBean);
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
