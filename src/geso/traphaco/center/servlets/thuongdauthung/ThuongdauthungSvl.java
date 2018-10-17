package geso.traphaco.center.servlets.thuongdauthung;

import geso.traphaco.center.beans.thuongdauthung.IThuongdauthung;
import geso.traphaco.center.beans.thuongdauthung.imp.Thuongdauthung;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThuongdauthungSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ThuongdauthungSvl() 
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Utility util = new Utility();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IThuongdauthung obj = new Thuongdauthung();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);  
		obj.setUserId(userId);
		if (userId.length()==0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		session.setAttribute("userId", userId); 
		String action = util.getAction(querystring);


		String idlist = util.getId(querystring); 
		try{
			obj.setID(Double.parseDouble(idlist));
		}catch(Exception er){};

		String loai = request.getParameter("loai")==null?"0":request.getParameter("loai");
		obj.setLoai(loai);

		String nextJSP = "/TraphacoHYERP/pages/Center/Thuongdauthung.jsp";//default

		if(action.equals("delete"))
		{
			obj.DeleteUploadLuongCoBan();
			obj.setLuongkhacRs("");
			session.setAttribute("obj", obj);
		}else if(action.equals("chot"))
		{
			obj = new Thuongdauthung(idlist);	
			obj.chotUploadLuongCoBan();
			obj.setLuongkhacRs("");
			session.setAttribute("obj", obj);
		}
		else if(action.equals("new"))
		{
			nextJSP= "/TraphacoHYERP/pages/Center/ThuongdauthungUpdate.jsp";
		}
		else if(action.equals("unchot"))
		{ 
			obj.setID(Double.parseDouble(idlist));			
			obj.UnchotUploadLuongCoBan();
			obj.setLuongkhacRs("");
			session.setAttribute("obj", obj);
		}
		else
		{
			session.setAttribute("thang",0);
			session.setAttribute("nam",0);
			obj.setLuongkhacRs("");
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
		String username=util.antiSQLInspection(request.getParameter("userTen"));

		String thangtk=util.antiSQLInspection(request.getParameter("thang"));
		String namtk=util.antiSQLInspection(request.getParameter("nam"));
		String action =request.getParameter("action");

		IThuongdauthung thuongdauthung =new Thuongdauthung();
		thuongdauthung.setUserId(userId);
		HttpSession session=request.getSession();

		session.setAttribute("nam",Integer.parseInt(namtk));
		session.setAttribute("thang",Integer.parseInt(thangtk));


		String loai = request.getParameter("loai")==null?"0":request.getParameter("loai");
		thuongdauthung.setLoai(loai);

		if(action.equals("search"))
		{
			String  sql_getdata="";

			sql_getdata="SELECT    c.trangthai,C.PK_SEQ, C.tungay, C.denngay, C.DIENGIAI,  C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO "+ 
					",NS.TEN AS NGUOISUA,c.LOAI FROM thuongdauthung AS C INNER JOIN "+
					"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ " +
					"where 1=1 and c.Loai="+loai+" ";
			String where ="";

			if(!thangtk.equals("0"))
			{
				where=" and month(C.denngay)="+ thangtk + " ";	
			}
			if(!namtk.equals("0"))
			{
				where=where + "and year(C.denngay)="+namtk +" ";
			}

			if(where !="")
			{
				sql_getdata=sql_getdata+ where;	
			}
			sql_getdata += "\n order by  C.PK_SEQ desc";
			thuongdauthung.setLuongkhacRs(sql_getdata);
			session.setAttribute("obj", thuongdauthung);
			String nextJSP = "/TraphacoHYERP/pages/Center/Thuongdauthung.jsp";//default
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("new"))
		{
			IThuongdauthung obj = new Thuongdauthung();
			obj.setUserId(userId);
			obj.setLoai(loai);
			session.setAttribute("userId",userId);
			session.setAttribute("userTen", username);
			session.setAttribute("obj", thuongdauthung);
			session.setAttribute("check", "0");
			String	nextJSP= "/TraphacoHYERP/pages/Center/ThuongdauthungUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
	}
}
