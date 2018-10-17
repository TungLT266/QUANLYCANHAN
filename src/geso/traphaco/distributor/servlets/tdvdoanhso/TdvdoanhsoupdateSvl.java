package geso.traphaco.distributor.servlets.tdvdoanhso;

import geso.traphaco.distributor.beans.tdvdoanhso.ITDVDoanhso;
import geso.traphaco.distributor.beans.tdvdoanhso.ITdvdoanhsoList;
import geso.traphaco.distributor.beans.tdvdoanhso.imp.TdvdoanhsoList;
import geso.traphaco.distributor.beans.tdvdoanhso.imp.tdvdoanhso;
import geso.traphaco.distributor.util.Utility;

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

public class TdvdoanhsoupdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public TdvdoanhsoupdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
			ITDVDoanhso khBean;
			PrintWriter out;

			out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
				
			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			String thang = util.antiSQLInspection(getThang(querystring));
			if(thang == null)
				thang ="";
			String nam = util.antiSQLInspection(getNam(querystring));
			if(nam == null)
				nam ="";
			khBean = new tdvdoanhso(id);
			khBean.setDdkdId(id);
			khBean.setUserId(userId);
			khBean.setThang(thang);
			khBean.setNam(nam);
			khBean.init();
			session.setAttribute("khBean", khBean);
			String nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSoUpdate.jsp";
			response.sendRedirect(nextJSP);
		}

	}
	public String getThang(String querystring){
	    String id;
	    String tmp;
		if (querystring != null){
	    	if (querystring.contains("&")){
	    		tmp = querystring.split("&")[2];
	    		System.out.println("tmp "+tmp);
	    		id = tmp.split("=")[1];
	    		System.out.println("id  "+id);
	    	}else{
	    		id = "";
	    	}
		}else{
			id = "";
		}
	    return id;
		
	}
	public String getNam(String querystring){
	    String id;
	    String tmp;
		if (querystring != null){
	    	if (querystring.contains("&")){
	    		tmp = querystring.split("&")[3];
	    		System.out.println("tmp "+tmp);
	    		id = tmp.split("=")[1];
	    	}else{
	    		id = "";
	    	}
		}else{
			id = "";
		}
	    return id;
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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

			ITDVDoanhso khBean;
			PrintWriter out;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			out = response.getWriter();
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
			if (id == null)
			{
				khBean = new tdvdoanhso("");
			} else
			{
				khBean = new tdvdoanhso(id);
			}

			userId = util.antiSQLInspection(request.getParameter("userId"));
			khBean.setUserId(userId);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			khBean.setNppId(nppId);
				
			String thang = util.antiSQLInspection(request.getParameter("thang"));
			if (thang == null)
				thang = "";
			khBean.setThang(thang);
			
			String nam = util.antiSQLInspection(request.getParameter("nam"));
			if (nam == null)
				nam = "";
			khBean.setNam(nam);
			
			String[] ddkdId = request.getParameterValues("ddkdId");
			String str = "";
			if (ddkdId != null)
			{
				
				for (int i = 0; i < ddkdId.length; i++)
				{
					
					str += ddkdId[i] + ",";
					System.out.println(ddkdId.length+"___________"+str);
				}
				if (str.length() > 0)
					str = str.substring(0, str.length() - 1);
			}
			System.out.println("___________"+str);
			khBean.setDdkdId(str);

			String[] makh = request.getParameterValues("makh");
			String str1 = "";
			if (makh != null)
			{
				for (int i = 0; i < makh.length; i++)
					str1 += makh[i] + ",";
				if (str1.length() > 0)
					str1 = str1.substring(0, str1.length() - 1);
			}
			System.out.println("makh "+str1);
			khBean.setKhachhangId(str1);
			

			String[] doanhso = request.getParameterValues("doanhso");
			 str1 = "";
			if (doanhso != null)
			{
				for (int i = 0; i < doanhso.length; i++)
					if(doanhso[i].length() > 0)
						str1 += doanhso[i] + ",";
				if (str1.length() > 0)
					str1 = str1.substring(0, str1.length() - 1);
			}
			khBean.setDoanhso(str1);
			
			String[] doanhsobosung = request.getParameterValues("doanhsobosung");
			 str1 = "";
			if (doanhsobosung != null)
			{
				for (int i = 0; i < doanhsobosung.length; i++)
					if(doanhsobosung[i].length() > 0)
						str1 += doanhsobosung[i] + ",";
				if (str1.length() > 0)
					str1 = str1.substring(0, str1.length() - 1);
			}
			System.out.println("doanhsobosung "+str1);
			khBean.setDoanhsobosung(str1);
			String ngaysua = getDateTime();
			khBean.setNgaysua(ngaysua);

			boolean error = false;
			String action = request.getParameter("action");
			if (!error)
			{
				if (action.equals("save"))
				{
					if (id == null)
					{						
						if (!(khBean.CreateKh(request)))
						{
							khBean.createRS();
							session.setAttribute("khBean", khBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSoNew.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							ITdvdoanhsoList obj = new TdvdoanhsoList();
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSo.jsp";
							response.sendRedirect(nextJSP);
						}

					} 
					else
					{
						if (!(khBean.UpdateKh(request)))
						{
							khBean.createRS();
							session.setAttribute("khBean", khBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSoUpdate.jsp";
							response.sendRedirect(nextJSP);
						} 
						else
						{
							ITdvdoanhsoList obj = new TdvdoanhsoList();
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSo.jsp";
							response.sendRedirect(nextJSP);
						}
					}
				} 
				else
				{
					khBean.setUserId(userId);
					khBean.createRS();

					session.setAttribute("khBean", khBean);

					String nextJSP;
					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSoNew.jsp";
					} else
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSoUpdate.jsp";
					}
					response.sendRedirect(nextJSP);
				}
			} 
			else
			{
				khBean.setUserId(userId);
				khBean.createRS();

				session.setAttribute("khBean", khBean);

				String nextJSP;
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSoNew.jsp";
				} else
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/TDVDoanhSoUpdate.jsp";
				}
				response.sendRedirect(nextJSP);

			}
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
