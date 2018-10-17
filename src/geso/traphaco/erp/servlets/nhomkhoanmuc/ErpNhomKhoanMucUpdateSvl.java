package geso.traphaco.erp.servlets.nhomkhoanmuc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhomkhoanmuc.IErpNhomKhoanMuc;
import geso.traphaco.erp.beans.nhomkhoanmuc.IErpNhomKhoanMucList;
import geso.traphaco.erp.beans.nhomkhoanmuc.imp.ErpKhoanMucChiPhi;
import geso.traphaco.erp.beans.nhomkhoanmuc.imp.ErpNhomKhoanMuc;
import geso.traphaco.erp.beans.nhomkhoanmuc.imp.ErpNhomKhoanMucList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ErpNhomKhoanMucUpdateSvl")
public class ErpNhomKhoanMucUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public ErpNhomKhoanMucUpdateSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility util;
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
			String nextJSP = "";
			util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			
			IErpNhomKhoanMuc nkmBean = new ErpNhomKhoanMuc(id);
			
			String ctyId = (String)session.getAttribute("congtyId");
		    String nppId = (String)session.getAttribute("nppId");
		    nkmBean.setCongtyId(ctyId);
		    nkmBean.setNppId(nppId);
		    
		    nkmBean.setUserId(userId);
			if (request.getQueryString().indexOf("display") >= 0)
			{
				nkmBean.Init();
				nkmBean.createListKhoanMucChiPhi();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomKhoanMucDisplay.jsp";
			}
			else if (request.getQueryString().indexOf("update") >= 0)
			{
				nkmBean.Init();
				nkmBean.createListKhoanMucChiPhi();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomKhoanMucUpdate.jsp";
			}
			System.out.println("Ban dang o trang" + nextJSP);
			session.setAttribute("nkmBean", nkmBean);
			response.sendRedirect(nextJSP);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if (!util.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			String nextJSP = "";
			IErpNhomKhoanMuc nkmBean = new ErpNhomKhoanMuc();
			String ctyId = (String)session.getAttribute("congtyId");
		    String nppId = (String)session.getAttribute("nppId");
		    nkmBean.setCongtyId(ctyId);
		    nkmBean.setNppId(nppId);
			String Id = util.antiSQLInspection(request.getParameter("id"));
			String Ma = util.antiSQLInspection(request.getParameter("Ma"));
			String Ten = util.antiSQLInspection(request.getParameter("Ten"));
			String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));
			String tenKhoanMucChiPhi = util.antiSQLInspection(request.getParameter("tenKhoanMucChiPhi"));
			String dvthId = util.antiSQLInspection(request.getParameter("dvthId"));
			String action = request.getParameter("action");
			
			
			if (action == null)
			{
				action = "";
			}
			if (Ma == null)
			{
				Ma = "";
			}
			if (Ten == null)
			{
				Ten = "";
			}
			if (trangthai == null)
			{
				trangthai = "0";
			}
		
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			nkmBean.setId(Id);
			System.out.println("Id: " + Id);
			
			nkmBean.setMa(Ma);
			nkmBean.setTen(Ten);
			nkmBean.setTrangThai(trangthai);
			nkmBean.setUserId(userId);
			nkmBean.setDvthid(dvthId);
			nkmBean.setTenKhoanMucChiPhi(tenKhoanMucChiPhi);
			
			List<ErpKhoanMucChiPhi> listKhoanMucChecked = new ArrayList<ErpKhoanMucChiPhi>();
		
			String[] check = request.getParameterValues("check");
			String[] kmcpId = request.getParameterValues("kmcpId");
			String[] kmcpMa = request.getParameterValues("kmcpMa");
			String[] kmcpTen = request.getParameterValues("kmcpTen");
			if (check != null) {
				for (int i = 0; i < check.length; i++) {
					if (check[i].equals("1")) {
						ErpKhoanMucChiPhi kmcp = new ErpKhoanMucChiPhi();
						kmcp.setIdKhoanMuc(kmcpId[i]);
						kmcp.setMaKhoanMuc(kmcpMa[i]);
						kmcp.setTenKhoanMuc(kmcpTen[i]);
						kmcp.setChecked(check[i]);
						listKhoanMucChecked.add(kmcp);
					}
				}
			}
			nkmBean.setListKhoanMuc(listKhoanMucChecked);
			System.out.println("listKhoanMucChecked.length" + listKhoanMucChecked.size());
			nkmBean.setAction(action);
			if (action.equals("Create"))
			{
				System.out.println("Tao moi");
				if (nkmBean.Create())
				{
					System.out.println("Tao duoc");
					IErpNhomKhoanMucList obj = new ErpNhomKhoanMucList();
					obj.setCongtyId(ctyId);
					obj.setNppId(nppId);
					obj.Init();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomKhoanMuc.jsp";
				}
				else
				{
					nkmBean.createListKhoanMucChiPhi();
					session.setAttribute("nkmBean", nkmBean);
					System.out.println("Khong tao  duoc");
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomKhoanMucNew.jsp";
				}
			}
			if (action.equals("Update"))
			{
				if (!nkmBean.Update())
				{
					nkmBean.createListKhoanMucChiPhi();
					session.setAttribute("lnccBean", nkmBean);
					System.out.println("Khong Update  duoc");
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomKhoanMucUpdate.jsp";
				}
				else
				{
					System.out.println("Update duoc");
					IErpNhomKhoanMucList obj = new ErpNhomKhoanMucList();
					obj.setCongtyId(ctyId);
					obj.setNppId(nppId);
					obj.Init();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomKhoanMuc.jsp";
				}
			}
			if(action.equals("search")){
				nkmBean.createListKhoanMucChiPhi();
				session.setAttribute("nkmBean", nkmBean);
				if (Id.length() >0)
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomKhoanMucUpdate.jsp";
				else
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhomKhoanMucNew.jsp";
			}
			
			System.out.println(nextJSP);
			response.sendRedirect(nextJSP);
		}
	}
}
