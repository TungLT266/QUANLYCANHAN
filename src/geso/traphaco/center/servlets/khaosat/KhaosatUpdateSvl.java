package geso.traphaco.center.servlets.khaosat;

import geso.traphaco.center.beans.khaosat.IKhaosat;
import geso.traphaco.center.beans.khaosat.IKhaosatList;
import geso.traphaco.center.beans.khaosat.imp.Khaosat;
import geso.traphaco.center.beans.khaosat.imp.KhaosatList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhaosatUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public KhaosatUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IKhaosat csxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    csxBean = new Khaosat(id);
	    
	    csxBean.setId(id);
	    csxBean.setUserId(userId);
	    
	    csxBean.init();
        session.setAttribute("csxBean", csxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Center/KhaoSatUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Center/KhaoSatDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IKhaosat csxBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	csxBean = new Khaosat();
	    }else{
	    	csxBean = new Khaosat(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		csxBean.setUserId(userId);	       
		
		String tieude = util.antiSQLInspection(request.getParameter("tieude"));
		if (tieude == null)
			tieude = "";
		csxBean.setTieude(tieude);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		csxBean.setDiengiai(diengiai);
		
		String bophan = util.antiSQLInspection(request.getParameter("bophan"));
		if (bophan == null)
			bophan = "";
		csxBean.setBophan(bophan);
		
		String socauhoi = util.antiSQLInspection(request.getParameter("socauhoi"));
		if (socauhoi == null)
			socauhoi = "";
		csxBean.setSocauhoi(socauhoi);
		
		String kvId = util.antiSQLInspection(request.getParameter("kvId"));
    	if(kvId == null)
    		kvId = "";
    	csxBean.setKhuvucId(kvId);
    	
		String vungId = util.antiSQLInspection(request.getParameter("vungId"));
		if(vungId == null)
			vungId = "";
		csxBean.setVungId(vungId);
		
		String[] nvIds = request.getParameterValues("nvIds");
	    if(nvIds != null)
	    {
	    	String nvId = "";
	    	for(int i = 0; i < nvIds.length; i++)
	    		nvId += nvIds[i] + ",";
	    	
	    	if(nvId.trim().length() > 0)
	    	{
	    		nvId = nvId.substring(0, nvId.length() - 1);
	    		csxBean.setDdkdId(nvId);
	    	}
	    }
		
		if(socauhoi.trim().length() > 0)
		{
			Hashtable<String, String> cauhoi_noidung = new Hashtable<String, String>();
			
			for(int i = 0; i < Integer.parseInt(socauhoi.trim()); i++)
			{
				String cauhoiId = "cau" + (i + 1);
				
				String loaicauhoi = request.getParameter(cauhoiId + "_loaicauhoi");
				if(loaicauhoi == null)
					loaicauhoi = "0";
				
				String cauhoi = request.getParameter(cauhoiId + "_cauhoi");
				if(cauhoi == null || cauhoi.trim().length() <= 0 )
					cauhoi = " ";
				
				String huongdantraloi = request.getParameter(cauhoiId + "_huongdantraloi");
				if(huongdantraloi == null || huongdantraloi.trim().length() <= 0)
					huongdantraloi = " ";
				
				String cautraloi = "";
				String[] motluachon = request.getParameterValues(cauhoiId + "_motluachon");
				String[] nhieuluachon = request.getParameterValues(cauhoiId + "_nhieuluachon");
				
				if(loaicauhoi.equals("1"))
				{
					if(motluachon != null)
					{
						//System.out.println("__Do dai dap an mot lua chon: " + motluachon.length);
						for(int j = 0; j < motluachon.length; j++)
						{
							if(motluachon[j].trim().length() <= 0)
								motluachon[j] = "NA";
							cautraloi += motluachon[j] + "__";
						}
						
						if(cautraloi.trim().length() > 0)
							cautraloi = cautraloi.substring(0, cautraloi.length() - 2);
					}
				}
				else
				{
					if(loaicauhoi.equals("2"))
					{
						if(nhieuluachon != null)
						{
							//System.out.println("__Do dai dap an nhieu lua chon: " + nhieuluachon.length);
							for(int j = 0; j < nhieuluachon.length; j++)
							{
								if(nhieuluachon[j].trim().length() <= 0)
									nhieuluachon[j] = "NA";
								cautraloi += nhieuluachon[j] + "__";
							}
							
							if(cautraloi.trim().length() > 0)
								cautraloi = cautraloi.substring(0, cautraloi.length() - 2);
						}
					}
					else
					{
						cautraloi = " ";
					}
				}
				
				System.out.println("---- " + cauhoiId + ": " + loaicauhoi + ",," + cauhoi + ",," + huongdantraloi + ",," + cautraloi);
				cauhoi_noidung.put(cauhoiId, loaicauhoi + ",," + cauhoi + ",," + huongdantraloi + ",," + cautraloi );
			}
			
			//System.out.println("___Cau 1: " + cauhoi_noidung.get("cau1"));
			//System.out.println("___Cau 2: " + cauhoi_noidung.get("cau2"));
			
			csxBean.setNoidungcauhoi(cauhoi_noidung);
		}
		
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(csxBean.createKhaosat()))
				{
	 				csxBean.createRs();
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Center/KhaoSatNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IKhaosatList obj = new KhaosatList();
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/KhaoSat.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(csxBean.updateKhaosat()))
				{
 					csxBean.createRs();
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Center/KhaoSatUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IKhaosatList obj = new KhaosatList();
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/KhaoSat.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			csxBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", csxBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Center/KhaoSatNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Center/KhaoSatUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
