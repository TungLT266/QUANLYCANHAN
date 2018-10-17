package geso.traphaco.erp.servlets.butrucongno;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.butrucongno.IButrucongno;
import geso.traphaco.erp.beans.butrucongno.IButrucongnoList;
import geso.traphaco.erp.beans.butrucongno.imp.Butrucongno;
import geso.traphaco.erp.beans.butrucongno.imp.ButrucongnoList;


@WebServlet("/ButrucongnoUpdateSvl")
public class ButrucongnoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ButrucongnoUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
//		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
//		out = response.getWriter();

		String querystring = request.getQueryString();
		String action = util.getAction(querystring);

		IButrucongno nvgnBean;
		
		String id = util.getId(querystring);
		
		String nextJSP = "";
		nvgnBean = new Butrucongno(id);
		
		String userId = util.getUserId(querystring);
		//String userTen = util.getIdNhapp(userId);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		nvgnBean.setUserId(userId);
		nvgnBean.setCongtyId((String)session.getAttribute("congtyId"));		
		nvgnBean.setnppdangnhap(util.getIdNhapp(userId));		
		nvgnBean.setLoainhanvien(session.getAttribute("loainhanvien"));
		nvgnBean.setDoituongIddn(session.getAttribute("doituongId"));
		
		 
		session.setAttribute("nvgnBean", nvgnBean);
		session.setAttribute("userId", userId);
		if(action.equals("update"))
		{
		   nvgnBean.init();
		   nextJSP = "/TraphacoHYERP/pages/Erp/BuTruCongNoKHUpdate.jsp";
		}
		else
		{
			nvgnBean.init_display();
			nextJSP = "/TraphacoHYERP/pages/Erp/BuTruCongNoKHDisplay.jsp";
		}
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		Utility util = new Utility();
		session.setAttribute("util", util);
		String userId = request.getParameter("userId");
		
		String userTen = request.getParameter("userTen");
		
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);

		String action = request.getParameter("action");
			
		IButrucongno nvgnBean;
//		PrintWriter out;
//		out = response.getWriter();
		
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null)
		{
			id = "";
			nvgnBean = new Butrucongno("");
		} else
		{
			nvgnBean = new Butrucongno(id);
		}

		userId = util.antiSQLInspection(request.getParameter("userId"));
		nvgnBean.setUserId(userId);
		nvgnBean.setCongtyId((String)session.getAttribute("congtyId"));		
		nvgnBean.setnppdangnhap(util.getIdNhapp(userId));
		

		String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
		if (ngaychungtu == null)
			ngaychungtu = "";
		nvgnBean.setNgaychungtu(ngaychungtu);
		
		String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
		if (sochungtu == null)
			sochungtu = "";
		nvgnBean.setSochungtu(sochungtu);
		
		String dienGiaiCT = util.antiSQLInspection(request.getParameter("dienGiaiCT"));
		if(dienGiaiCT== null)
			dienGiaiCT ="";
		nvgnBean.setDienGiaiCT(dienGiaiCT);
		
		String tiente = util.antiSQLInspection(request.getParameter("tienteId"));
		if (tiente == null)
			tiente = "";
		nvgnBean.setTienteId(tiente);
		
		String khchuyennoId = util.antiSQLInspection(request.getParameter("khchuyennoId"));
		if (khchuyennoId == null)
			khchuyennoId = "";
		else
		{
//			String arr[] = khchuyennoId.split(",");
//			nvgnBean.setIsNPPChuyenNo(arr[0]);
			nvgnBean.setKHChuyenNoId(khchuyennoId);
		}
					
		String khnhannoId = util.antiSQLInspection(request.getParameter("khnhannoId"));
		if (khnhannoId == null)
			khnhannoId = "";
		else
		{
//			String arr[] = khchuyennoId.split(",");
//			nvgnBean.setIsNPPNhanNo(arr[0]);
			nvgnBean.setKHNhanNoId(khnhannoId);
		}
		
		String tenKHChuyenNo = util.antiSQLInspection(request.getParameter("tenKHChuyenNo"));
		if (tenKHChuyenNo != null)
			nvgnBean.setTenKHChuyenNo(tenKHChuyenNo);
		
		String tenKHNhanNo = util.antiSQLInspection(request.getParameter("tenKHNhanNo"));
		if (tenKHNhanNo != null)
			nvgnBean.setTenKHNhanNo(tenKHNhanNo);
		
		String isNPPChuyenNo = util.antiSQLInspection(request.getParameter("isNPPChuyenNo"));
		if (isNPPChuyenNo != null)
			nvgnBean.setIsNPPChuyenNo(isNPPChuyenNo);
		
		String isNPPNhanNo = util.antiSQLInspection(request.getParameter("isNPPNhanNo"));
		if (isNPPNhanNo != null)
			nvgnBean.setIsNPPNhanNo(isNPPNhanNo);
		
		
		String tongchuyenno = util.antiSQLInspection(request.getParameter("tongchuyenno"));
		if (tongchuyenno == null)
			tongchuyenno = "0";
		nvgnBean.setTongchuyenno(tongchuyenno);
		
		String tongnhanno = util.antiSQLInspection(request.getParameter("tongnhanno"));
		if (tongnhanno == null)
			tongnhanno = "0";
		nvgnBean.setTongnhanno(tongnhanno);
											
		String ngaytao = Utility.getCurrentDate();
		nvgnBean.setNgaytao(ngaytao);
		
		String ngaysua = Utility.getCurrentDate();
		nvgnBean.setNgaysua(ngaysua);
		
		String[] hdId = request.getParameterValues("hdId");			
		nvgnBean.setHdId(hdId);
		
		String[] hdLoai = request.getParameterValues("hdloai");			
		nvgnBean.setHdLoai(hdLoai);
		
		String[] hdNgayhd = request.getParameterValues("hdNgay");
		nvgnBean.setHdNgayhd(hdNgayhd);
		
		String[] hdsohoadon = request.getParameterValues("hdsohoadon");
		nvgnBean.setHdSohd(hdsohoadon);
		
		String[] hdsotiengoc = request.getParameterValues("hdsotiengoc");
		nvgnBean.setHdSotiengoc(hdsotiengoc);
		
		String[] hdsotienphaixoano = request.getParameterValues("hdsotienphaixoano");
		nvgnBean.setHdSotienphaixoa(hdsotienphaixoano);
		
		String[] hdsotienxoa = request.getParameterValues("hdsotienxoa");
		nvgnBean.setHdSotienxoa(hdsotienxoa);
		
		String[] hdsotienconlai = request.getParameterValues("hdsotienconlai");
		nvgnBean.setHdSotienconlai(hdsotienconlai);
		
		String[] hdtigia = request.getParameterValues("hdtigia");
		nvgnBean.setHdTigia(hdtigia);
					
		boolean error = false;
		
		if (!error)
		{
			if (action.equals("save"))
			{
				if (id.length() == 0)
				{
					if (!(nvgnBean.CreateNvgn()))//TAO MOI
					{
						nvgnBean.createRS();
						session.setAttribute("nvgnBean", nvgnBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/BuTruCongNoKHNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IButrucongnoList obj = new ButrucongnoList();
						obj.setUserId(userId);
						obj.setCongtyId((String)session.getAttribute("congtyId"));		
						obj.setnppdangnhap(util.getIdNhapp(userId));
						obj.init("");
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/BuTruCongNoKH.jsp";
						response.sendRedirect(nextJSP);
					}

				} else
				{
					if (!(nvgnBean.UpdateNvgn()))//CAP NHAT
					{
						nvgnBean.createRS();
						session.setAttribute("nvgnBean", nvgnBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/BuTruCongNoKHUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IButrucongnoList obj = new ButrucongnoList();
						obj.setUserId(userId);
						obj.setCongtyId((String)session.getAttribute("congtyId"));		
						obj.setnppdangnhap(util.getIdNhapp(userId));
						obj.init("");
						session.setAttribute("obj", obj);

						String nextJSP = "/TraphacoHYERP/pages/Erp/BuTruCongNoKH.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else
				//SEARCH 
			{
				//
				if (action.equals("search"))
				{
					nvgnBean.setHdId(null);
					nvgnBean.createRS();
					
				}
				//
				if(action.equals("submitForm"))
				{
					if (id.length() > 0)
					{
						nvgnBean.setId("");
						nvgnBean.setHdId(null);
						nvgnBean.createRS();
					} else
					{
						nvgnBean.setHdId(null);
						nvgnBean.createRS();
					}
				}
				session.setAttribute("nvgnBean", nvgnBean);

				String nextJSP;
				if (id.length() == 0)
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/BuTruCongNoKHNew.jsp";
				} else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/BuTruCongNoKHUpdate.jsp";
				}
				response.sendRedirect(nextJSP);
			}
		} else
		{
			if (id.length() > 0)
			{
				nvgnBean.createRS();
			} else
			{
				nvgnBean.createRS();
			}
			session.setAttribute("nvgnBean", nvgnBean);
			String nextJSP;
			if (id.length() == 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/BuTruCongNoKHNew.jsp";
			} else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/BuTruCongNoKHUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}
}