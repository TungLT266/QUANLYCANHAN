package geso.traphaco.erp.servlets.huyhoadontaichinh;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.IErpHuyhoadontaichinhNPP;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.imp.ErpHuyhoadontaichinhNPP;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.IErpHuyhoadontaichinhNPPList;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.imp.ErpHuyhoadontaichinhNPPList;
import geso.traphaco.distributor.util.Utility;

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


public class HuyhoadontaichinhUpdateSvl extends HttpServlet
{
    	private static final long serialVersionUID = 1L;
    	PrintWriter out;

    	public HuyhoadontaichinhUpdateSvl()
    	{
    		super();
    	}

    	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    	{
    		HttpSession session = request.getSession();
    		String ctyId = (String)session.getAttribute("congtyId");
   		
    		IErpHuyhoadontaichinhNPP obj;

    		PrintWriter out = response.getWriter();
    		Utility util = new Utility();
    		out = response.getWriter();
    		
    		String querystring = request.getQueryString();
    		String userId = util.getUserId(querystring);

    		if (userId.length() == 0)
    			userId = util.antiSQLInspection(request.getParameter("userId"));

    		String id = util.getId(querystring);

    		String loaiHD = util.antiSQLInspection(request.getParameter("loaiHD"));
    		
    		obj = (IErpHuyhoadontaichinhNPP) new ErpHuyhoadontaichinhNPP();
    		
    		obj.setCtyId(ctyId);
    		
    		obj.setId(id);
    		
    		obj.setUserId(userId);
    		
    		obj.setLoaiHD(loaiHD);
    		
    		session.setAttribute("obj", obj);
    		obj.init_new();
    		
    		String nextJSP = "/TraphacoHYERP/pages/Erp/HuyhoadontaichinhUpdate.jsp";
    		if(querystring.indexOf("display") >= 0)
    		{
    			nextJSP = "/TraphacoHYERP/pages/Erp/HuyhoadontaichinhDisplay.jsp";
    		}

    		response.sendRedirect(nextJSP);
    	}

    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    	{
    		request.setCharacterEncoding("UTF-8");
    		response.setCharacterEncoding("UTF-8");
    		response.setContentType("text/html; charset=UTF-8");
    		HttpSession session = request.getSession();
    		
    		String ctyId = (String)session.getAttribute("congtyId");
    		//ctyId = "100001";
    		IErpHuyhoadontaichinhNPP huyhdbean;
    		
    		
    		Utility util = new Utility();
    		String id = util.antiSQLInspection(request.getParameter("id"));
    		if(id == null){
    			huyhdbean = (IErpHuyhoadontaichinhNPP) new ErpHuyhoadontaichinhNPP();
    		}else{
    			huyhdbean = (IErpHuyhoadontaichinhNPP) new ErpHuyhoadontaichinhNPP(id);
    		}
    		
    		huyhdbean.setCtyId(ctyId);
    		String userId = util.antiSQLInspection(request.getParameter("userId"));
    		huyhdbean.setUserId(userId);

    		String action = request.getParameter("action");
    		if(action==null){
    			action="";
    		}
    	 
    		//----- lấy thông tin phiếu nhập -----//
    		
    		String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
    		if(sochungtu==null){
    			sochungtu="";
    		}
    		huyhdbean.setSoCT(sochungtu);
     
    		String loaiHD = util.antiSQLInspection(request.getParameter("loaiHD"));
    		if(loaiHD==null){
    			loaiHD="";
    		}
    		huyhdbean.setLoaiHD(loaiHD);
     
    		String ngayhuy = util.antiSQLInspection(request.getParameter("ngayhuy"));
    		if(ngayhuy==null){
    			ngayhuy="";
    		}
    		huyhdbean.setNgayHuy(ngayhuy);
    		
    		if(action.equals("save"))
    		{
    			if(id.length() ==0 )
    			{
    				if (!(huyhdbean.createHuyhoadontaichinh()))
    				{
    					huyhdbean.createRs();
    					huyhdbean.init_new();
    					session.setAttribute("obj", huyhdbean);
    					session.setAttribute("userId", userId);

    					String nextJSP = "/TraphacoHYERP/pages/Erp/HuyhoadontaichinhNew.jsp";
    					response.sendRedirect(nextJSP);
    				}
    				else
    				{
    					
    					IErpHuyhoadontaichinhNPPList obj = new ErpHuyhoadontaichinhNPPList();
    					obj.setUserId(userId);
    					obj.init("");

    					session.setAttribute("obj", obj);

    					String nextJSP = "/TraphacoHYERP/pages/Erp/Huyhoadontaichinh.jsp";
    					response.sendRedirect(nextJSP);
    				}
    			}
    			else
    			{
    				if (!(huyhdbean.updateHuyhoadontaichinh()))
    				{
    					huyhdbean.createRs();
    					session.setAttribute("obj", huyhdbean);
    					session.setAttribute("userId", userId);

    					String nextJSP = "/TraphacoHYERP/pages/Erp/HuyhoadontaichinhUpdate.jsp";
    					response.sendRedirect(nextJSP);
    				}
    				else
    				{
    					 
    					String nextJSP = "/TraphacoHYERP/pages/Erp/Huyhoadontaichinh.jsp";
    					response.sendRedirect(nextJSP);
    				}
    			}
    		}
    		else if(action.equals("search")) {
    			session.setAttribute("obj", huyhdbean);
    			session.setAttribute("userId", userId);
    			
    			huyhdbean.init();
    			
    			String nextJSP = "/TraphacoHYERP/pages/Erp/HuyhoadontaichinhNew.jsp";
    			response.sendRedirect(nextJSP);
    		}
    	
    		else
    		{
    			System.out.println("I am here!");
    			session.setAttribute("userId", userId);
    			session.setAttribute("obj", huyhdbean);

    			String nextJSP = "/TraphacoHYERP/pages/Erp/HuyhoadontaichinhNew.jsp";
    			huyhdbean.init_new();
    			
    			if( id != null && id.length() >0 )
    			{
    				huyhdbean.init();
    				nextJSP = "/TraphacoHYERP/pages/Erp/HuyhoadontaichinhUpdate.jsp";
    			}
    			
    			response.sendRedirect(nextJSP);
    		}
    	}
}

