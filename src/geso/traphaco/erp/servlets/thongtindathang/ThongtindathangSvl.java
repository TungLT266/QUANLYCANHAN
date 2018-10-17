package geso.traphaco.erp.servlets.thongtindathang;

import geso.traphaco.erp.beans.thongtindathang.IThongtindathang;
import geso.traphaco.erp.beans.thongtindathang.imp.Thongtindathang;
import geso.traphaco.erp.beans.thongtindathang.IThongtindathangList;
import geso.traphaco.erp.beans.thongtindathang.imp.ThongtindathangList;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThongtindathangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public ThongtindathangSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out  = response.getWriter();

		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId"); 
	    String ctyTen = (String)session.getAttribute("congtyTen");

		Utility util = new Utility();
		out = response.getWriter();

		IThongtindathangList obj = new ThongtindathangList();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		obj.setCtyId(ctyId);
		obj.setCtyTen(ctyTen);
	    
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj.setUserId(userId);

		String action = util.getAction(querystring);
		out.println(action);

		String ttdhId = util.getId(querystring);

		obj.setRequestObj(request);
		if (action.equals("delete"))
		{
			String msg = Delete(ttdhId);
			if (msg.length() > 0)
			obj.setMsg(msg);
		}
		String msg = "";
		obj.setMsg(msg);

		obj.init("");
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Thongtindathang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId"); 
	    String ctyTen = (String)session.getAttribute("congtyTen");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();
		this.out = response.getWriter();
		Utility util = new Utility();

		IThongtindathangList obj = new ThongtindathangList();
		obj.setCtyId(ctyId);
		obj.setCtyTen(ctyTen);

		String userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = request.getParameter("action");
		if (action == null) action = "";

		String search = getSearchQuery(request, obj);
		obj.setRequestObj(request);

		if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.setUserId(userId);
			obj.init(search);
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", obj);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Thongtindathang.jsp");
		}else if(action.equals("New")){
			IThongtindathang ttdh = new Thongtindathang();
			ttdh.setCtyId(ctyId);
			ttdh.setUserId(userId);
			ttdh.createRs();
			session.setAttribute("obj", ttdh);
			session.setAttribute("userId", userId);
			session.setAttribute("abc", search);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ThongtindathangNew.jsp");
		}
		else
		{
			obj.init(search);
			obj.setUserId(userId);
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			session.setAttribute("abc", search);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Thongtindathang.jsp");
		}
	}

	private String getSearchQuery(HttpServletRequest request, IThongtindathangList obj)
	{
		Utility util = new Utility();
		
		String lspId = util.antiSQLInspection(request.getParameter("lspId"));
    	if (lspId == null)
    		lspId = "";
    	obj.setLspId(lspId);
    	
    	String spId = util.antiSQLInspection(request.getParameter("spId"));
    	if (spId == null)
    		spId = "";
    	obj.setSpId(spId);
    	
    	String nccId = util.antiSQLInspection(request.getParameter("nccId"));
    	if (nccId == null)
    		nccId = "";
    	obj.setNccId(nccId);
    	
    	String isMua = util.antiSQLInspection(request.getParameter("isMua"));
    	if (isMua == null)
    		isMua = "";
    	obj.setIsMua(isMua);

    	String query = "";
    	
		return query;
	}

	private String convertDate(String date)
	{
		// chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if (!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if (arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}

	private String Delete(String congbosanphamId)
	{
		
		dbutils db = new dbutils();
		String query = "select count(*) as num from ";
		try{
		ResultSet rs = db.get(query);

		
		
			rs.next();
			if (!rs.getString("num").equals("0"))
			{
				rs.close();
				return "Congbosanpham nay da duoc su dung";
			}
			rs.close();

			db.update("delete from ");
			db.update("delete from TraphacoERP where pk_seq = '" + congbosanphamId + "'");
			db.update("commit");
			return "";

		} catch (Exception e)
		{
			return "Khong the xoa ";
		}
		finally
		{
		db.shutDown();
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
