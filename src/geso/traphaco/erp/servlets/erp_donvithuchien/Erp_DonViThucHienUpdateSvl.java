package geso.traphaco.erp.servlets.erp_donvithuchien;

import geso.traphaco.center.util.Utility;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.erp.beans.erp_donvithuchien.IErp_donvithuchien;
import geso.traphaco.erp.beans.erp_donvithuchien.IErp_donvithuchienList;
import geso.traphaco.erp.beans.erp_donvithuchien.imp.Erp_donvithuchien;
import geso.traphaco.erp.beans.erp_donvithuchien.imp.Erp_donvithuchienList;

public class Erp_DonViThucHienUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_DonViThucHienUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		Utility util = new Utility();
		// out = response.getWriter();

		String querystring = request.getQueryString();
		String userid = util.getUserId(querystring);
		System.out.println("User id is " + userid);

		String id = util.getId(querystring);
		System.out.println("ID is " + id);

		IErp_donvithuchien dvthBean = new Erp_donvithuchien(id);
		dvthBean.setCtyId((String)session.getAttribute("congtyId"));
		
		dvthBean.init();
		session.setAttribute("dvthBean", dvthBean);
		
		if(querystring.contains("display"))
		{
			response.sendRedirect("pages/Erp/Erp_DonViThucHienDisplay.jsp");
		}
		else 
		{	
			response.sendRedirect("pages/Erp/Erp_DonViThucHienUpdate.jsp");
		}
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
		String nextJSP = "";
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);

		IErp_donvithuchien dvthBean = new Erp_donvithuchien();
		dvthBean.setCtyId((String)session.getAttribute("congtyId"));

		String id = util.antiSQLInspection(request.getParameter("id"));
//		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		String ten = util.antiSQLInspection(request.getParameter("ten"));
		String prefix = util.antiSQLInspection(request.getParameter("prefix"));
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai")); 
			
//		if (dvkdId != null)  dvthBean.setDvkdId(dvkdId);
		if (ma != null) dvthBean.setMA(ma);
		if (ten != null) dvthBean.setTEN(ten);
		if (id != null)  dvthBean.setID(id);

		if (prefix != null) dvthBean.setPrefix(prefix);
		
		if (trangthai != null) 
			dvthBean.setTrangThai("1");
		else
			dvthBean.setTrangThai("0");
		
		dvthBean.setNGUOITAO(userId);
		dvthBean.setNGUOISUA(userId);

		String action = request.getParameter("action");
		if (action.equals("save"))
		{
			if (id == null)
			{
				if (!dvthBean.themMoiMa())
				{
					nextJSP = "pages/Erp/Erp_DonViThucHienNew.jsp";
				} else
				{
					IErp_donvithuchienList dvthList = new Erp_donvithuchienList();
					dvthList.setCongtyId((String)session.getAttribute("congtyId"));
					dvthList.setUserid(userId);
					dvthList.init();
					dvthBean.DBClose();
					session.setAttribute("dvthList", dvthList);
					nextJSP = "pages/Erp/Erp_DonViThucHien.jsp";
				}
			} else if (id != null)
			{
				if (!dvthBean.UpdateMa())
				{
					nextJSP = "pages/Erp/Erp_DonViThucHienUpdate.jsp";
				} else
				{
					IErp_donvithuchienList dvthList = new Erp_donvithuchienList();
					dvthList.setCongtyId((String)session.getAttribute("congtyId"));
					dvthList.setUserid(userId);
					dvthList.init();
					dvthBean.DBClose();
					session.setAttribute("dvthList", dvthList);
					nextJSP = "pages/Erp/Erp_DonViThucHien.jsp";

				}
			}
		} else if (id == null)
			nextJSP = "pages/Erp/Erp_DonViThucHienNew.jsp";
		else
			nextJSP = "pages/Erp/Erp_DonViThucHienUpdate.jsp";
		session.setAttribute("dvthBean", dvthBean);
		response.sendRedirect(nextJSP);
	}
}
