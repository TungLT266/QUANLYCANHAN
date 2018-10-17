package geso.traphaco.center.servlets.nhomfocus;

import geso.traphaco.center.beans.nhomfocus.INhomfocus;
import geso.traphaco.center.beans.nhomfocus.INhomfocusList;
import geso.traphaco.center.beans.nhomfocus.imp.Nhomfocus;
import geso.traphaco.center.beans.nhomfocus.imp.NhomfocusList;

import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhomFocusUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	public NhomFocusUpdateSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		INhomfocus tctskuBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		out.println(userId);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String id = util.getId(querystring);
		
		tctskuBean = new Nhomfocus(id);
		tctskuBean.setId(id);
		tctskuBean.setUserId(userId);
		
		tctskuBean.init();
		session.setAttribute("tctskuBean", tctskuBean);
		
		String nextJSP = "/TraphacoHYERP/pages/Center/NhomfocusUpdate.jsp";
		if (querystring.indexOf("display") >= 0)
			nextJSP = "/TraphacoHYERP/pages/Center/NhomfocusDisplay.jsp";
		
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		INhomfocus tctskuBean;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		Utility util = new Utility();
		
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null)
		{
			tctskuBean = new Nhomfocus("");
		} else
		{
			tctskuBean = new Nhomfocus(id);
		}
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		tctskuBean.setUserId(userId);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		tctskuBean.setDiengiai(diengiai);
		
		String scheme = util.antiSQLInspection(request.getParameter("scheme"));
		if (scheme == null)
			scheme = "";
		tctskuBean.setScheme(scheme);
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = "";
		tctskuBean.setThang(thang);
		
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		tctskuBean.setNam(nam);
		
		String quy = util.antiSQLInspection(request.getParameter("quy")==null?"":request.getParameter("quy"));
		tctskuBean.setQuy(quy);
		
		String apdung = util.antiSQLInspection(request.getParameter("apdung")==null?"":request.getParameter("apdung"));
		tctskuBean.setApdung(apdung);
		
		
		String loaichitieu = util.antiSQLInspection(request.getParameter("loaichitieu")==null?"0":request.getParameter("loaichitieu"));
		tctskuBean.setLoaichitieu(loaichitieu);
		
		String[] spIds = request.getParameterValues("spIds");
		if (spIds != null)
		{
			String spId = "";
			for (int i = 0; i < spIds.length; i++)
			{
				spId += spIds[i] + ",";
			}
			
			if (spId.trim().length() > 0)
			{
				spId = spId.substring(0, spId.length() - 1);
				tctskuBean.setSpIds(spId);
			}
		}
		
		String[] nppIds = request.getParameterValues("nppIds");
		String[] npp_chitieu = request.getParameterValues("npp_chitieu");
		String[] npp_donvi_chitieu = request.getParameterValues("npp_donvi_chitieu");
		if (nppIds != null)
		{
			
			Hashtable<String, String> htb_npp_chitieu = new Hashtable<String, String>();
			Hashtable<String, String> htb_npp_donvi_chitieu = new Hashtable<String, String>();
		
			String nppId = "";
			for (int i = 0; i < nppIds.length; i++)
			{
				htb_npp_chitieu.put(nppIds[i]  , npp_chitieu[i].replaceAll(",","") );
				htb_npp_donvi_chitieu.put(nppIds[i]  , npp_donvi_chitieu[i]);
				nppId += nppIds[i] + ",";
			}
			tctskuBean.setNpp_chitieu(htb_npp_chitieu);
			tctskuBean.setNpp_donvi_chitieu(htb_npp_donvi_chitieu);
			
			if (nppId.trim().length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
				tctskuBean.setNppIds(nppId);
			}
		}
		
		String[] tumuc = request.getParameterValues("tumuc");
		tctskuBean.setTumuc(tumuc);
		
		String[] denmuc = request.getParameterValues("denmuc");
		tctskuBean.setDenmuc(denmuc);

		String[] donvi_tinh_ds = request.getParameterValues("donvi_tinh_ds");
		tctskuBean.setDonvi_tinh_ds(donvi_tinh_ds);
		
		String[] thuong = request.getParameterValues("thuong");
		tctskuBean.setThuong(thuong);
	
		String[] donvi_tinh_thuong = request.getParameterValues("donvi_tinh_thuong");
		tctskuBean.setDonvi_tinh_thuong(donvi_tinh_thuong);
		
		
	
	
		// VUNG
		String vungId = request.getParameter("vungId");
		if (vungId == null)
			vungId = "";
		tctskuBean.setVungIds(vungId);
		
		String kvId = request.getParameter("khuvucId");
		if (kvId == null)
			kvId = "";
		tctskuBean.setKhuvucIds(kvId);
		
	
		
		String action = request.getParameter("action");
		System.out.println("Action la: " + action);
		
		if (action.equals("save"))
		{
			if (id == null)
			{
				if (!tctskuBean.save())
				{
					tctskuBean.setUserId(userId);
					
					tctskuBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tctskuBean", tctskuBean);
					
					String nextJSP = "/TraphacoHYERP/pages/Center/NhomfocusNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					INhomfocusList obj = new NhomfocusList();
					obj.setUserId(userId);
					
					obj.init("");
					session.setAttribute("obj", obj);
					
					String nextJSP = "/TraphacoHYERP/pages/Center/Nhomfocus.jsp";
					response.sendRedirect(nextJSP);
				}
			} else
			{
				if (!(tctskuBean.edit()))
				{
					tctskuBean.setUserId(userId);
					
					tctskuBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tctskuBean", tctskuBean);
					
					String nextJSP = "/TraphacoHYERP/pages/Center/NhomfocusUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					INhomfocusList obj = new NhomfocusList();
					obj.setUserId(userId);
					
					obj.init("");
					session.setAttribute("obj", obj);
					
					String nextJSP = "/TraphacoHYERP/pages/Center/Nhomfocus.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} else
		{
			tctskuBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);
			
			String nextJSP;
			if (id == null)
			{
				nextJSP = "/TraphacoHYERP/pages/Center/NhomfocusNew.jsp";
			} else
			{
				nextJSP = "/TraphacoHYERP/pages/Center/NhomfocusUpdate.jsp";
			}
			response.sendRedirect(nextJSP);
		}
	}
	
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}
