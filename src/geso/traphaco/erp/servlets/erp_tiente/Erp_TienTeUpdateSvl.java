package geso.traphaco.erp.servlets.erp_tiente;

import geso.traphaco.center.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.erp.beans.erp_tiente.IErp_tiente;
import geso.traphaco.erp.beans.erp_tiente.IErp_tienteList;
import geso.traphaco.erp.beans.erp_tiente.imp.Erp_tiente;
import geso.traphaco.erp.beans.erp_tiente.imp.Erp_tienteList;

public class Erp_TienTeUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_TienTeUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		String id = util.getId(querystring);
		IErp_tiente ttBean = new Erp_tiente(id);
		ttBean.setUserid(userId);
		ttBean.init();
		session.setAttribute("ttBean", ttBean);
		if(querystring.contains("display"))
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_TienTeDisplay.jsp");
		else response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_TienTeUpdate.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
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

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			IErp_tiente ttBean = new Erp_tiente();
			String nextJSP = "";
			
			session.setAttribute("util", cutil);
			String id = cutil.antiSQLInspection(request.getParameter("id"));
			session.setAttribute("userid", userId);
			session.setAttribute("userten", userTen);

			String ma = cutil.antiSQLInspection(request.getParameter("ma"));
			String ten = cutil.antiSQLInspection(request.getParameter("ten"));
			String doctiensonguyen = cutil.antiSQLInspection(request.getParameter("doctiensonguyen"));
			String doctiensole = cutil.antiSQLInspection(request.getParameter("doctiensole"));
			
			if (ma != null)
				ttBean.setMA(ma);
			if (ten != null)
				ttBean.setTEN(ten);
			if (id != null)
				ttBean.setID(id);
			if (userId != null)
				ttBean.setUserid(userId);
			if (doctiensonguyen != null)
				ttBean.setDoctiensonguyen(doctiensonguyen);
			if (doctiensole != null)
				ttBean.setDoctiensole(doctiensole);
			
			String action = request.getParameter("action");
			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!ttBean.ThemMoiMa())
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TienTeNew.jsp";
					} else
					{
						IErp_tienteList ttList = new Erp_tienteList();
						ttList.init();
						session.setAttribute("tt", ttList);
						nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TienTe.jsp";

					}
				} else if (id != null)
				{
					if (!ttBean.UpdateMa())
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TienTeUpdate.jsp";
					} else
					{
						IErp_tienteList ttList = new Erp_tienteList();
						ttList.init();
						session.setAttribute("tt", ttList);
						nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TienTe.jsp";
					}
				}
			}
			session.setAttribute("ttBean", ttBean);
			response.sendRedirect(nextJSP);
		}
	}
}
