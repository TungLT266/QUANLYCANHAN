package geso.traphaco.center.servlets.nhomkhachhangkm;

import geso.traphaco.center.beans.nhomkhachhangkm.INhomkhachhangkm;
import geso.traphaco.center.beans.nhomkhachhangkm.INhomkhachhangkmList;
import geso.traphaco.center.beans.nhomkhachhangkm.imp.Nhomkhachhangkm;
import geso.traphaco.center.beans.nhomkhachhangkm.imp.NhomkhachhangkmList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomkhachhangkmUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public NhomkhachhangkmUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);
		INhomkhachhangkm nkhKmBean = new Nhomkhachhangkm(id);

		session.setAttribute("nkhKmBean", nkhKmBean);
		response.sendRedirect("/TraphacoHYERP/pages/Center/NhomkhachhangkmUpdate.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		INhomkhachhangkm nkhKmBean = new Nhomkhachhangkm();

		Utility util = new Utility();

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		nkhKmBean.setuserId(userId);

		String Ten = util.antiSQLInspection(request.getParameter("ten"));
		if (Ten == null)
			Ten = "";
		nkhKmBean.setTen(Ten);

		String Diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (Diengiai == null)
			Diengiai = "";
		nkhKmBean.setDiengiai(Diengiai);

		String Trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (Trangthai == null)
			Trangthai = "0";
		nkhKmBean.setTrangthai(Trangthai);

		String active = util.antiSQLInspection(request.getParameter("active"));
		if (active == null)
			active = "";
		nkhKmBean.setActive(active);

		String id = util.antiSQLInspection(request.getParameter("id"));
		String Id = request.getParameter("id");
		if (Id == null)
			Id = "";
		nkhKmBean.setId(Id);

		String kenhId = "";
		String[] kenhIds = request.getParameterValues("kenhId");
		if (kenhIds != null)
		{
			int size = kenhIds.length;
			for (int i = 0; i < size; i++)
			{
				kenhId += kenhIds[i] + ",";
			}
			if (kenhId.length() > 0)
			{
				kenhId = kenhId.substring(0, kenhId.length() - 1);
			}
		}
		nkhKmBean.setKenhId(kenhId);

		String kvId = "";
		String[] kvIds = request.getParameterValues("khuvucId");
		if (kvIds != null)
		{
			int size = kvIds.length;
			for (int i = 0; i < size; i++)
			{
				kvId += kvIds[i] + ",";
			}
			if (kvId.length() > 0)
			{
				kvId = kvId.substring(0, kvId.length() - 1);
			}
		}
		nkhKmBean.setKhuvucId(kvId);

		String vungId = "";
		String[] vungIds = request.getParameterValues("vungId");
		if (vungIds != null)
		{
			int size = vungIds.length;
			for (int i = 0; i < size; i++)
			{
				vungId += vungIds[i] + ",";
			}
			if (vungId.length() > 0)
			{
				vungId = vungId.substring(0, vungId.length() - 1);
			}
		}
		nkhKmBean.setVungId(vungId);

		String nppId = "";
		String[] nppIds = request.getParameterValues("nppId");
		if (nppIds != null)
		{
			int size = nppIds.length;
			for (int i = 0; i < size; i++)
			{
				nppId += nppIds[i] + ",";
			}
			if (nppId.length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
			}
		}
		nkhKmBean.setNppId(nppId);

		String ddkdId = "";
		String[] ddkdIds = request.getParameterValues("ddkdId");
		if (ddkdIds != null)
		{
			int size = ddkdIds.length;
			for (int i = 0; i < size; i++)
			{
				ddkdId += ddkdIds[i] + ",";
			}
			if (ddkdId.length() > 0)
			{
				ddkdId = ddkdId.substring(0, ddkdId.length() - 1);
			}
		}
		nkhKmBean.setDdkdId(ddkdId);

		String khId = "0";
		String[] khIds = request.getParameterValues("khId");
		if (khIds != null)
		{
			int size = khIds.length;
			for (int i = 0; i < size; i++)
			{
				khId += khIds[i] + ",";
			}
			if (khId.length() > 0)
			{
				khId = khId.substring(0, khId.length() - 1);
			}
		}
		nkhKmBean.setKhId(khId);
		String action = request.getParameter("action");
		if (action.equals("save"))
		{

			if (id == null)
			{

				if (!(nkhKmBean.Save()))
				{
					nkhKmBean.createRs();
					nkhKmBean.createNppRs();
					nkhKmBean.createDdkdRs();
					nkhKmBean.createKhRs();
					session.setAttribute("nkhKmBean", nkhKmBean);
					response.sendRedirect("/TraphacoHYERP/pages/Center/NhomKhachHangKmNew.jsp");
				} else
				{
					INhomkhachhangkmList nkhBean1 = new NhomkhachhangkmList();
					nkhBean1.init("");
					session.setAttribute("obj", nkhBean1);
					session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Center/Nhomkhachhangkm.jsp";
					response.sendRedirect(nextJSP);
				}
			} else
			{
				if (!(nkhKmBean.Update()))
				{
					nkhKmBean.createRs();
					nkhKmBean.createNppRs();
					nkhKmBean.createDdkdRs();
					nkhKmBean.createKhRs();
					session.setAttribute("nkhKmBean", nkhKmBean);
					response.sendRedirect("/TraphacoHYERP/pages/Center/NhomKhachHangKmUpdate.jsp");
				} else
				{
					INhomkhachhangkmList nkhBean1 = new NhomkhachhangkmList();
					nkhBean1.init("");
					session.setAttribute("obj", nkhBean1);
					session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Center/Nhomkhachhangkm.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} else
		{
			nkhKmBean.createRs();
			nkhKmBean.createNppRs();
			nkhKmBean.createDdkdRs();
			nkhKmBean.createKhRs();
			session.setAttribute("nkhKmBean", nkhKmBean);
			if (id == null)
			{
				response.sendRedirect("/TraphacoHYERP/pages/Center/NhomKhachHangKmNew.jsp");
			} else
			{
				response.sendRedirect("/TraphacoHYERP/pages/Center/NhomKhachHangKmUpdate.jsp");
			}
		}
	}
}