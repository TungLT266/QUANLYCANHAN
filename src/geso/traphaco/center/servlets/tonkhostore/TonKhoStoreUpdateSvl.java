package geso.traphaco.center.servlets.tonkhostore;

import geso.traphaco.center.beans.upload.*;
import geso.traphaco.center.beans.upload.imp.*;
import geso.traphaco.center.util.Utility;

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

public class TonKhoStoreUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public TonKhoStoreUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();

		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);

		System.out.println("[TonKhoStoreUpdateSvl.doGet] id = " + id);

		IUpload uploadBean = new Upload(id);
		uploadBean.setUserId(userId);
		uploadBean.init();
		uploadBean.createSpNpp();

		session.setAttribute("userId", userId);
		session.setAttribute("uploadBean", uploadBean);
		String nextJSP = "/TraphacoHYERP/pages/Center/TonKhoStoreUpdate.jsp";
		if (querystring.indexOf("display") > 0)
			nextJSP = "/TraphacoHYERP/pages/Center/TonKhoStoreDisplay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IUpload bean;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		this.out = response.getWriter();

		Utility util = new Utility();

		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null)
		{
			bean = new Upload("");
		} else
		{
			bean = new Upload(id);
		}
		System.out.println("[TonKhoStoreUpdateSvl.doPost] id = " + bean.getId());

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		bean.setUserId(userId);
		System.out.println("[TonKhoStoreUpdateSvl.doPost] userId = " + bean.getUserId());

		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
		{
			thang = "";
		}
		bean.setThang(thang.trim());
		System.out.println("[TonKhoStoreUpdateSvl.doPost] thang = " + bean.getThang());

		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
		{
			nam = "";
		}
		bean.setNam(nam.trim());
		System.out.println("[TonKhoStoreUpdateSvl.doPost] nam = " + bean.getNam());

		String spnppList = util.antiSQLInspection(request.getParameter("spnppList"));
		if (spnppList == null)
		{
			spnppList = "";
		}
		bean.setSpNpp(spnppList);
		System.out.println("[TonKhoStoreUpdateSvl.doPost] spnppList = " + spnppList);

		String ngaysua = getDateTime();
		bean.setNgaySua(ngaysua.trim());
		System.out.println("[TonKhoStoreUpdateSvl.doPost] ngaysua = " + bean.getNgaySua());

		String action = request.getParameter("action");
		System.out.println("[TonKhoStoreUpdateSvl.doPost] action = " + action);

		boolean flag = true;
		if (!bean.checkThangNam())
		{
			flag = false;
			bean.setSpNpp("");
			bean.setMsg("Đã tồn tại tồn kho tháng " + thang + ", năm " + nam);
		}

		if (flag && action.equals("save"))
		{
			if (id == null || id.length() == 0)
			{ // Create New Upload
				if (!bean.create())
				{
					bean.setUserId(userId);
					session.setAttribute("userId", userId);
					session.setAttribute("uploadBean", bean);

					String nextJSP = "/TraphacoHYERP/pages/Center/TonKhoStoreNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					IUpload obj = new Upload();
					obj.createRs_Tonkho();
					session.setAttribute("userId", userId);
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Center/TonKhoStore.jsp";
					response.sendRedirect(nextJSP);
				}
			} else
			{
				// Update Upload
				if (!(bean.update()))
				{
					// Update unsuccessfully
					bean.setUserId(userId);

					session.setAttribute("userId", userId);
					session.setAttribute("uploadBean", bean);

					String nextJSP = "/TraphacoHYERP/pages/Center/TonKhoStoreUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					// Update successfully
					IUpload obj = new Upload();
					obj.createRs_Tonkho();
					session.setAttribute("userId", userId);
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Center/TonKhoStore.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} else
		{
			bean.createNppList();
			if (spnppList.trim().length() == 0)
			{
				bean.createSpNpp();
			}
			session.setAttribute("userId", userId);
			session.setAttribute("uploadBean", bean);
			String nextJSP = id == null ? "/TraphacoHYERP/pages/Center/TonKhoStoreNew.jsp" : "/TraphacoHYERP/pages/Center/TonKhoStoreUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
