package geso.traphaco.distributor.servlets.tuyenbanhang;

import geso.traphaco.distributor.beans.tuyenbanhang.*;
import geso.traphaco.distributor.beans.tuyenbanhang.imp.*;
import geso.traphaco.distributor.util.Utility;
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

public class TuyenbanhangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public TuyenbanhangUpdateSvl()
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

			ITuyenbanhang tbhBean;
			PrintWriter out;

			out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length() == 0)
				userId = request.getParameter("userId");

			String id = util.getId(querystring);
			tbhBean = new Tuyenbanhang(id);
			tbhBean.setUserId(userId);
			tbhBean.init();			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHangUpdate.jsp";
			String copy = request.getParameter("copy");
			if(copy != null)
			{			
				nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHangNew.jsp";
				tbhBean.setDiengiai("");
				tbhBean.setId(null);
				tbhBean.setNgaylamviec("");
			}
			session.setAttribute("tbhBean", tbhBean);
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

			ITuyenbanhang tbhBean;
			PrintWriter out;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			out = response.getWriter();

			String id = request.getParameter("id");
			if (id == null)
				tbhBean = new Tuyenbanhang("");
			else
				tbhBean = new Tuyenbanhang(id);

			userId = request.getParameter("userId");
			tbhBean.setUserId(userId);

			String nppId = request.getParameter("nppId");
			if (nppId == null)
				nppId = "";
			tbhBean.setNppId(nppId);

			String ddkdId = request.getParameter("ddkdTen");
			if (ddkdId == null)
				ddkdId = "";
			tbhBean.setDdkdId(ddkdId);
			System.out.print("[ddkdId]"+ddkdId);
			String diengiai = request.getParameter("tbhTen");
			if (diengiai == null)
				diengiai = "";
			tbhBean.setDiengiai(diengiai);

			String nlv = request.getParameter("ngaylamviec");
			if (nlv == null)
				nlv = "";
			tbhBean.setNgaylamviec(nlv);

			String khTen = request.getParameter("khTen");
			if (khTen == null)
				khTen = "";
			tbhBean.setTenkhachhang(khTen);

			String diachi = request.getParameter("diachi");
			if (diachi == null)
				diachi = "";
			tbhBean.setDiachi(diachi);

			String[] tansoIds = request.getParameterValues("tansoList");
			tbhBean.setTansoList(tansoIds);

			String[] kh_tbh_dptIds = request.getParameterValues("kh_tbh_dptList");
			tbhBean.setKh_Tbh_DptIds(kh_tbh_dptIds);

			String[] kh_tbh_cdptIds = request.getParameterValues("kh_tbh_cdptList");
			tbhBean.setKh_Tbh_CdptIds(kh_tbh_cdptIds);

			String[] thutu = request.getParameterValues("thutu");
			tbhBean.setSoTT(thutu);

			String ngaysua = getDateTime();
			tbhBean.setNgaysua(ngaysua);

			boolean error = false;

			if (ddkdId.trim().length() == 0)
			{
				tbhBean.setMessage("Vui Long Chon Dai Dien Kinh Doanh");
				error = true;
			}

			if (diengiai.trim().length() == 0)
			{
				tbhBean.setMessage("Vui Long Nhap Dien Giai Tuyen Ban Hang");
				error = true;
			}

			if (nlv.trim().length() == 0)
			{
				tbhBean.setMessage("Vui Long Chon Ngay Lam Viec");
				error = true;
			}

			String action = request.getParameter("action");

			if (action.equals("save"))
			{
				if (id == null)
				{
					System.out.println("Ban nhan nut tao moi ... \n");
					if (!(tbhBean.CreateTbh()))
					{
						tbhBean.setUserId(userId);
						tbhBean.createRS();
						session.setAttribute("tbhBean", tbhBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHangNew.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						ITuyenbanhangList obj = new TuyenbanhangList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHang.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					if (!(tbhBean.UpdateTbh()))
					{
						tbhBean.setUserId(userId);
						tbhBean.init();
						session.setAttribute("tbhBean", tbhBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						ITuyenbanhangList obj = new TuyenbanhangList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else
			{
				tbhBean.setUserId(userId);
				tbhBean.createRS();
				session.setAttribute("tbhBean", tbhBean);
				String nextJSP;
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHangNew.jsp";
				} else
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHangUpdate.jsp";
				}
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
