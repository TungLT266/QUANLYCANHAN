package geso.traphaco.center.servlets.dieuchinhtonkho;

import geso.traphaco.center.beans.dieuchinhtonkho.IDieuchinhtonkho;
import geso.traphaco.center.beans.dieuchinhtonkho.imp.Dieuchinhtonkho;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DctkDisplaySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public DctkDisplaySvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String nextJSP;
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String id = util.getId(querystring);
		String action = util.getAction(querystring);

		IDieuchinhtonkho dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();
		dctkBean.setUserId(userId);
		String sql = "select ten from nhanvien where pk_seq=" + userId;

		dbutils db = new dbutils();
		ResultSet rs = db.get(sql);
		if (rs != null)
		{
			try
			{
				if (rs.next())
				{
					dctkBean.setUserTen(rs.getString("ten"));
				}
				rs.close();
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		db.shutDown();
		dctkBean.setId(id);
		dctkBean.initDisplay();
		session.setAttribute("dctkBean", dctkBean);
		nextJSP = "/TraphacoHYERP/pages/Center/DieuChinhTonKhoDisplay.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

}
