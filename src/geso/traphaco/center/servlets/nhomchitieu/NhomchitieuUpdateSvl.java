package geso.traphaco.center.servlets.nhomchitieu;

import geso.traphaco.center.beans.nhomsanpham.INhomsanpham;
import geso.traphaco.center.beans.nhomsanpham.INhomsanphamList;
import geso.traphaco.center.beans.nhomsanpham.imp.Nhomsanpham;
import geso.traphaco.center.beans.nhomsanpham.imp.NhomsanphamList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomchitieuUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;
	private PrintWriter out;

	public NhomchitieuUpdateSvl()
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
		INhomsanpham nspBean = new Nhomsanpham(id);
		
		nspBean.Init();
		session.setAttribute("nspBean", nspBean);
		session.setAttribute("userId", userId);

		String nextJSP = "/TraphacoHYERP/pages/Center/NhomChiTieuUpdate.jsp";
		response.sendRedirect(nextJSP);
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		INhomsanpham nspBean = new Nhomsanpham();

		Utility util = new Utility();

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		nspBean.setUserId(userId);
		
		String tennhom = util.antiSQLInspection(request.getParameter("tennhom"));
		nspBean.setTen(tennhom);

		String id = util.antiSQLInspection(request.getParameter("id"));
		nspBean.setId(id);

		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		nspBean.setDiengiai(diengiai);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if ( trangthai == null)
			trangthai = "0";
		nspBean.setTrangthai(trangthai);

		String loainhom = util.antiSQLInspection(request.getParameter("lnhom"));
		nspBean.setLoainhom(loainhom);
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		nspBean.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		nspBean.setDenngay(denngay);

		String[] spIds = request.getParameterValues("spIds");
		if( spIds != null )
		{
			String _spIds = "";
			for(int i = 0; i < spIds.length; i++)
			{
				_spIds += spIds[i] + ",";
			}
			
			if( _spIds.length() > 0 )
			{
				_spIds = _spIds.substring(0, _spIds.length() - 1);
				nspBean.setSpIds(_spIds);
			}
		}
		
		String action = request.getParameter("action");
		session.setAttribute("userId", util.antiSQLInspection(request.getParameter("userId")));

		if (action.equals("save")) 
		{
			if( id == null )
			{
				if( !nspBean.save() )
				{
					session.setAttribute("nspBean", nspBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/NhomChiTieuNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					INhomsanphamList obj = new NhomsanphamList();
					obj.setIsnhomchitieu("1");
					obj.init("");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					String nextJSP = "/TraphacoHYERP/pages/Center/NhomChiTieu.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				if( !nspBean.update() )
				{
					session.setAttribute("nspBean", nspBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/NhomChiTieuUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					INhomsanphamList obj = new NhomsanphamList();
					obj.setIsnhomchitieu("1");
					obj.init("");
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					String nextJSP = "/TraphacoHYERP/pages/Center/NhomChiTieu.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
	}

}