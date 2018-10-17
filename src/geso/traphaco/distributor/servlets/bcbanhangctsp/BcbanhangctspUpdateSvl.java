package geso.traphaco_09082014.center.servlets.bcbanhangctsp;

import geso.traphaco_09082014.center.beans.bcbanhangctsp.IBcbanhangctsp;
import geso.traphaco_09082014.center.beans.bcbanhangctsp.IBcbanhangctspList;
import geso.traphaco_09082014.center.beans.bcbanhangctsp.imp.Bcbanhangctsp;
import geso.traphaco_09082014.center.beans.bcbanhangctsp.imp.BcbanhangctspList;
import geso.traphaco_09082014.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BcbanhangctspUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public BcbanhangctspUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IBcbanhangctsp fileBean;

		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);

		fileBean = new Bcbanhangctsp(id);

		fileBean.setId(id);
		fileBean.setUserId(userId);

		fileBean.init();
		session.setAttribute("fileBean", fileBean);

		String nextJSP = "/Traphaco_09082014/pages/Center/BcbanhangctspUpdate.jsp";
		if(querystring.indexOf("display") >= 0)
		{
			nextJSP = "/Traphaco_09082014/pages/Center/BcbanhangctspDisplay.jsp";
		}

		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IBcbanhangctsp fileBean;

		Utility util = new Utility();
		String id = util.antiSQLInspection(request.getParameter("id"));
		if(id == null){
			fileBean = new Bcbanhangctsp();
		}else{
			fileBean = new Bcbanhangctsp(id);
		}

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		fileBean.setUserId(userId);

		String action = request.getParameter("action");
		if(action.equals("save"))
		{
			if(id == null)
			{
				if (!(fileBean.createBcbanhangctsp()))
				{
					fileBean.createRs();
					session.setAttribute("fileBean", fileBean);
					session.setAttribute("userId", userId);

					String nextJSP = "/Traphaco_09082014/pages/Center/BcbanhangctspNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IBcbanhangctspList obj = new BcbanhangctspList();
					obj.setUserId(userId);
					obj.init("");

					session.setAttribute("obj", obj);

					String nextJSP = "/Traphaco_09082014/pages/Center/Bcbanhangctsp.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				if (!(fileBean.updateBcbanhangctsp()))
				{
					fileBean.createRs();
					session.setAttribute("fileBean", fileBean);
					session.setAttribute("userId", userId);

					String nextJSP = "/Traphaco_09082014/pages/Center/BcbanhangctspUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IBcbanhangctspList obj = new BcbanhangctspList();
					obj.setUserId(userId);
					obj.init("");

					session.setAttribute("obj", obj);

					String nextJSP = "/Traphaco_09082014/pages/Center/Bcbanhangctsp.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
		else
		{
			fileBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("fileBean", fileBean);

			String nextJSP = "/Traphaco_09082014/pages/Center/BcbanhangctspNew.jsp";

			if( id != null )
			{
				nextJSP = "/Traphaco_09082014/pages/Center/BcbanhangctspUpdate.jsp";
			}

			response.sendRedirect(nextJSP);
		}
	}
}
