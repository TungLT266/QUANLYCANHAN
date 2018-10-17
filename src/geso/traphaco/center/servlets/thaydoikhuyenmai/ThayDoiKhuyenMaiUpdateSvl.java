package geso.traphaco.center.servlets.thaydoikhuyenmai;

import geso.traphaco.center.beans.thaydoikhuyenmai.ISanPham;
import geso.traphaco.center.beans.thaydoikhuyenmai.IThayDoiKhuyenMai;
import geso.traphaco.center.beans.thaydoikhuyenmai.IThayDoiKhuyenMaiList;
import geso.traphaco.center.beans.thaydoikhuyenmai.imp.SanPham;
import geso.traphaco.center.beans.thaydoikhuyenmai.imp.ThayDoiKhuyenMai;
import geso.traphaco.center.beans.thaydoikhuyenmai.imp.ThayDoiKhuyenMaiList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ThayDoiKhuyenMaiUpdateSvl")
public class ThayDoiKhuyenMaiUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ThayDoiKhuyenMaiUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();

		IThayDoiKhuyenMai tdkmBean;
		PrintWriter out = response.getWriter();

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);

		
		String action = util.getAction(querystring);
		out.println(action);
		tdkmBean = new ThayDoiKhuyenMai(id);
		tdkmBean.setUserId(userId);
		tdkmBean.init();

		session.setAttribute("tdkmBean", tdkmBean);
		if (action.equals("edit"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Center/ThayDoiKhuyenMaiUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("display"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Center/ThayDoiKhuyenMaiDisplay.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		IThayDoiKhuyenMai tdkmBean;
		Utility util = new Utility();

		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null)
		{
			tdkmBean = new ThayDoiKhuyenMai("");
		} else
		{
			tdkmBean = new ThayDoiKhuyenMai(id);
		}

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		tdkmBean.setUserId(userId);

		String loai = util.antiSQLInspection(request.getParameter("loai")==null?"":request.getParameter("loai"));
		if (loai == null)
			loai = "";
		tdkmBean.setLoai(loai);

		String tongtien = util.antiSQLInspection(request.getParameter("tongtien"));
		if (tongtien == null)
			tongtien = "";
		tdkmBean.setTongtien(tongtien);

		String chietkhau = util.antiSQLInspection(request.getParameter("chietkhau"));
		if (chietkhau == null)
			chietkhau = "";
		tdkmBean.setChietkhau(chietkhau);

		String tongluong = util.antiSQLInspection(request.getParameter("tongluong"));
		if (tongluong == null)
			tongluong = "";
		tdkmBean.setTongluong(tongluong);

		String hinhthuc = util.antiSQLInspection(request.getParameter("hinhthuc"));
		if (hinhthuc == null)
			hinhthuc = "";
		tdkmBean.setHinhthuc(hinhthuc);
		
		String trakmId = util.antiSQLInspection(request.getParameter("trakmId"));
		if (trakmId == null)
			trakmId = "";
		tdkmBean.setTrakmId(trakmId);
		
		session.setAttribute("trakmId", trakmId);
		
		String dkkmId = util.antiSQLInspection(request.getParameter("dkkmId"));
		if (dkkmId == null)
			dkkmId = "";
		tdkmBean.setDkkmId(dkkmId);
		
		
		String ctkmId = util.antiSQLInspection(request.getParameter("ctkmId")==null?"":request.getParameter("ctkmId"));
		tdkmBean.setCtkmId(ctkmId);
		
		session.setAttribute("dkkmId", dkkmId);
		
		String type =util.antiSQLInspection(request.getParameter("type"));
		
		if(loai.equals("2"))
			type = util.antiSQLInspection(request.getParameter("hinhthuc"));
		
		if (type == null)
			type = "";
		
		tdkmBean.setType(type.trim());
		

		String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");

		List<ISanPham> spList = new ArrayList<ISanPham>();
		{
			if (masp != null)
			{
				for (int i = 0; i < masp.length; i++)
				{
					if (masp[i] != "")
					{
						SanPham sp = new SanPham("", masp[i], tensp[i], soluong[i], "");
						spList.add(sp);
					}
				}
			}
		}
		tdkmBean.setSpList(spList);
		
		String action = request.getParameter("action");
		if (action.equals("save"))
		{
			if (id == null)
			{
				if (!tdkmBean.save())
				{
					tdkmBean.setUserId(userId);
					tdkmBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tdkmBean", tdkmBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/ThayDoiKhuyenMaiNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IThayDoiKhuyenMaiList obj = new ThayDoiKhuyenMaiList();
					obj.setUserId(userId);
					obj.createNhaphanglist("");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					response.sendRedirect("/TraphacoHYERP/pages/Center/ThayDoiKhuyenMai.jsp");
				}
			} else
			{
				if (!(tdkmBean.edit()))
				{
					tdkmBean.setUserId(userId);
					tdkmBean.createRs();

					session.setAttribute("userId", userId);
					session.setAttribute("tdkmBean", tdkmBean);

					String nextJSP = "/TraphacoHYERP/pages/Center/ThayDoiKhuyenMaiUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IThayDoiKhuyenMaiList obj = new ThayDoiKhuyenMaiList();
					obj.setUserId(userId);
					obj.createNhaphanglist("");

					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					response.sendRedirect("/TraphacoHYERP/pages/Center/ThayDoiKhuyenMai.jsp");
				}
			}
		} else
		{
			tdkmBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tdkmBean", tdkmBean);
			String nextJSP;
			if (id == null)
			{
				nextJSP = "/TraphacoHYERP/pages/Center/ThayDoiKhuyenMaiNew.jsp";
			} else
			{
				nextJSP = "/TraphacoHYERP/pages/Center/ThayDoiKhuyenMaiUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}
}
