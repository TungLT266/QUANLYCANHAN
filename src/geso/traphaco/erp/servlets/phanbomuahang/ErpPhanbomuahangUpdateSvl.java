package geso.traphaco.erp.servlets.phanbomuahang;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.phanbomuahang.ICongty;
import geso.traphaco.erp.beans.phanbomuahang.IErpPhanbomuahang;
import geso.traphaco.erp.beans.phanbomuahang.IErpPhanbomuahangList;
import geso.traphaco.erp.beans.phanbomuahang.ISanpham;
import geso.traphaco.erp.beans.phanbomuahang.imp.Congty;
import geso.traphaco.erp.beans.phanbomuahang.imp.ErpPhanbomuahang;
import geso.traphaco.erp.beans.phanbomuahang.imp.ErpPhanbomuahangList;
import geso.traphaco.erp.beans.phanbomuahang.imp.Sanpham;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpPhanbomuahangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpPhanbomuahangUpdateSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nextJSP;
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

			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IErpPhanbomuahang timnccBean = new ErpPhanbomuahang(id);
			timnccBean.setCongtyId((String)session.getAttribute("congtyId"));
			timnccBean.setUserId(userId); // phai co UserId truoc khi Init
			
			String task = request.getParameter("task");
			if(task == null)
				task = "";

				
			if (request.getQueryString().indexOf("display") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHangDisplay.jsp";
			}
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHangUpdate.jsp";
			}
			
			session.setAttribute("timnccBean", timnccBean);
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
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			session.setMaxInactiveInterval(30000);
			
			IErpPhanbomuahang timnccBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			System.out.println("ID" + id);
			if (id == null)
			{
				timnccBean = new ErpPhanbomuahang("");
			}
			else
			{
				timnccBean = new ErpPhanbomuahang(id);
			}
			String contyId = (String)session.getAttribute("congtyId");
			
			timnccBean.setCongtyId(contyId);
			timnccBean.setUserId(userId);
			
			String dnmhId = util.antiSQLInspection(request.getParameter("dmhId"));
			if (dnmhId == null)
				dnmhId = "";
			session.setAttribute("dmhId", dnmhId);
			timnccBean.setDnmhId(dnmhId);
			System.out.println("dmhId " + dnmhId);
			
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "";
			session.setAttribute("trangthai", trangthai);
			timnccBean.setTrangthai(trangthai);
			
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			session.setAttribute("diengiai", diengiai);
			timnccBean.setDiengiai(diengiai);
			
			String vat = util.antiSQLInspection(request.getParameter("vat"));
			if (vat == null)
				vat = "";
			timnccBean.setVat(vat);
			
			String dungsai = util.antiSQLInspection(request.getParameter("dungsai"));
			if (dungsai == null)
				dungsai = "";
			timnccBean.setDungsai(dungsai);
			
			String ngaymua = util.antiSQLInspection(request.getParameter("ngaymua"));
			if (ngaymua == null)
				ngaymua = "";
			timnccBean.setNgaymua(ngaymua);
			
			String dvth = util.antiSQLInspection(request.getParameter("dvth"));
			if (dvth == null)
				dvth = "";
			timnccBean.setDvth(dvth);
			
			String ncc = util.antiSQLInspection(request.getParameter("ncc"));
			if (ncc == null)
				ncc = "";
			timnccBean.setNcc(ncc);
			
			/*String[] ctIdarr = request.getParameterValues("ctId");
			String[] mactarr = request.getParameterValues("mact");
			String[] tenctarr = request.getParameterValues("tenct");
			if(ctIdarr != null){
				List<ICongty> lstct = new ArrayList<ICongty>();
				for (int i = 0; i < ctIdarr.length; i++) {
					ICongty ct = new Congty();
					ct.setId(ctIdarr[i]);
					ct.setMacongty(mactarr[i]);
					ct.setTencongty(tenctarr[i]);
					System.out.println("ct Id = " + ctIdarr[i] + ", mact = " + mactarr[i]+", tenct = " + tenctarr[i]);
					
					String[] idsp = request.getParameterValues("idsp"+i);
					
					if(idsp != null)
					{
						List<ISanpham> lstsp = new ArrayList<ISanpham>();
						String[] masp = request.getParameterValues("masp"+i);
						String[] tensp = request.getParameterValues("tensp"+i);
						String[] donvi = request.getParameterValues("donvi"+i);
						String[] soluong = request.getParameterValues("soluong"+i);
						String[] soluongpb = request.getParameterValues("soluongpb"+i);
						String[] dongia = request.getParameterValues("dongia"+i);
						String[] nhomkenh = request.getParameterValues("nhomkenh"+i);
						int k = 0;
						for (int j = 0; j < idsp.length; j++) {
							ISanpham sp = new Sanpham();
							sp.setId(idsp[j]);
							sp.setMasanpham(masp[j]);
							sp.setTensanpham(tensp[j]);
							sp.setDonvi(donvi[j]);
							sp.setSoluong(soluong[j]);
							sp.setSoluongpb(soluongpb[j]);
							sp.setDongia(dongia[j]);
							sp.setNhomkenh(nhomkenh[j]);
							
							lstsp.add(sp);
						}
						ct.setSanphamList(lstsp);
					}
					lstct.add(ct);
				}
				timnccBean.setCtList(lstct);
			}*/
			
			String[] idsp = request.getParameterValues("idsp");
			
			if(idsp != null)
			{
				List<ISanpham> lstsp = new ArrayList<ISanpham>();
				String[] masp = request.getParameterValues("masp");
				String[] tensp = request.getParameterValues("tensp");
				String[] donvi = request.getParameterValues("donvi");
				String[] solo = request.getParameterValues("solo");
				String[] soluong = request.getParameterValues("soluong");
				String[] thuexuat = request.getParameterValues("thuexuat");
				
				String[] dongia = request.getParameterValues("dongia");
				String[] nhomkenh = request.getParameterValues("nhomkenh");
				for (int i = 0; i < idsp.length; i++) {
					ISanpham sp = new Sanpham();
					sp.setId(idsp[i]);
					sp.setMasanpham(masp[i]);
					sp.setTensanpham(tensp[i]);
					sp.setDonvi(donvi[i]);
					sp.setSolo(solo[i]);
					sp.setSoluong(soluong[i]);
					sp.setDongia(dongia[i]);
					sp.setNhomkenh(nhomkenh[i]);
					sp.setThuexuat(thuexuat[i]);
					
					String[] idct = request.getParameterValues("idct"+i);
					String[] mact = request.getParameterValues("mact"+i);
					String[] tenct = request.getParameterValues("tenct"+i);
					String[] soluongpb = request.getParameterValues("soluongpb"+i);
					if(idct != null)
					{
						List<ICongty> lstct = new ArrayList<ICongty>();
						for (int j = 0; j < idct.length; j++) {
							ICongty ct = new Congty();
							ct.setId(idct[j]);
							ct.setMacongty(mact[j]);
							ct.setTencongty(tenct[j]);
							if(soluongpb[j].equals(""))
								soluongpb[j] = "0";
							ct.setSoluongpb(soluongpb[j]);
							lstct.add(ct);
						}
						sp.setCongty(lstct);
					}
					lstsp.add(sp);
				}
				timnccBean.setSpList(lstsp);
			}
			
		
		
			String action = request.getParameter("action");
			
			if (action.equals("save") )
			{
				if(dnmhId == ""){
					timnccBean.setMessage("Vui lòng chọn đơn mua hàng");
					timnccBean.createRs();
					String nextJSP;
					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHangNew.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHangUpdate.jsp";
					}
					session.setAttribute("timnccBean", timnccBean);
					response.sendRedirect(nextJSP);				
					return;
				}

				if (id == null) // tao moi
				{
					if (!timnccBean.createTimNcc())
					{
						timnccBean.createRs();
						session.setAttribute("timnccBean", timnccBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHangNew.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
					else
					{
						IErpPhanbomuahangList obj = new ErpPhanbomuahangList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHang.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}
				else
				{
					if (!timnccBean.updateTimNcc())
					{
						timnccBean.createRs();
						session.setAttribute("timnccBean", timnccBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHangUpdate.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
					else
					{
						IErpPhanbomuahangList obj = new ErpPhanbomuahangList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHang.jsp";
						response.sendRedirect(nextJSP);
						return;
					}
				}
			}
			else
			{
				String nextJSP;
				timnccBean.createRs();
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHangNew.jsp";
				}
				else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhanBoMuaHangUpdate.jsp";
				}
				session.setAttribute("timnccBean", timnccBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
}
