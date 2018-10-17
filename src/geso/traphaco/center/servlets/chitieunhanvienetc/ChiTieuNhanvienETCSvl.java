package geso.traphaco.center.servlets.chitieunhanvienetc;

import geso.traphaco.center.beans.chitieunhanvienetc.IChiTieuNhanvienETC;
import geso.traphaco.center.beans.chitieunhanvienetc.imp.ChiTieuNhanvienETC;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChiTieuNhanvienETCSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public ChiTieuNhanvienETCSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		
		IChiTieuNhanvienETC obj = new ChiTieuNhanvienETC();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);  
		obj.setUserId(userId);
		
		String action = util.getAction(querystring); 
		String id = util.getId(querystring); 
		obj.setID(id);
		obj.createRs();

		String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuNhanvienETC.jsp";
		obj.setDisplay("0");
		if(action.equals("delete"))
		{
			obj.Delete();
			obj.initCtLict("");
			session.setAttribute("obj", obj);
		}
		else if(action.equals("chot"))
		{
			obj.Chot();
			obj.initCtLict("");
			session.setAttribute("obj", obj);
		}
		else if(action.equals("unchot"))
		{ 
			obj.setID(id);			
			obj.UnChot();							
			obj.initCtLict("");
			session.setAttribute("obj", obj);
		}
		else
		{
			obj.initCtLict("");
			session.setAttribute("obj", obj);
		}
		
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		String username = util.antiSQLInspection(request.getParameter("userTen"));

		String quytk = util.antiSQLInspection(request.getParameter("quy"));
		String namtk = util.antiSQLInspection(request.getParameter("nam"));
		String action =request.getParameter("action");

		IChiTieuNhanvienETC chitieu = new ChiTieuNhanvienETC();
		chitieu.setUserId(userId);
		HttpSession session = request.getSession();

		if(action.equals("search"))
		{
			String sql = "";

			sql = "SELECT c.trangthai,C.PK_SEQ, C.quy, C.NAM, C.DIENGIAI, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
				  "NS.TEN AS NGUOISUA FROM ChiTieuNhanVienETC AS C INNER JOIN "+
				  "dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ " +
				  "where 1=1 ";

			String where = "";
			if(!quytk.equals("0")){
				where=" and C.Quy="+ quytk + " ";	
			}
			if(!namtk.equals("0")){
				where=where + "and C.NAM="+namtk +" ";
			}
	
			chitieu.initCtLict(sql);
			session.setAttribute("obj", chitieu);
			String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuNhanvienETC.jsp";//default
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("new"))
		{
			chitieu.setID("");
			chitieu.createRs();
			
			session.setAttribute("userId",userId);
			session.setAttribute("userTen", username);
			session.setAttribute("obj",  chitieu);
			
			String	nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuNhanvienETCUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
	}

}
