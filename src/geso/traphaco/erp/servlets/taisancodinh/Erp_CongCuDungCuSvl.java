package geso.traphaco.erp.servlets.taisancodinh;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.taisancodinh.IErp_CongCuDungCu;
import geso.traphaco.erp.beans.taisancodinh.imp.Erp_CongCuDungCu;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_CongCuDungCuSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public Erp_CongCuDungCuSvl() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		HttpSession session = request.getSession();	   	    
	    Utility util = new Utility();	   
	    
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();	   
	    out = response.getWriter();
	    
	    String userId;
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);
	    
	    IErp_CongCuDungCu obj = new Erp_CongCuDungCu();
	    obj.setCtyId((String) session.getAttribute("congtyId"));
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    out.println("userId la :"+ userId);
	   
	    String id = util.getId(querystring);
    	if (id == null)
    		id = "";
	    
    	String action = util.getAction(querystring);
    	if (action == null)
    		action = "";

		if (action.equals("delete"))
		{
    		if(id != null)		
			obj.Delete(id);
		}
		obj.setUserid(userId);
		obj.createRs();
		obj.init("");		
		
		session.setAttribute("obj", obj);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_CongCuDungCu.jsp");	
	}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
	    IErp_CongCuDungCu obj;	    
	    obj = new Erp_CongCuDungCu();	
	    
	    obj.setCtyId((String) session.getAttribute("congtyId"));
	  
	    String userId;
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    System.out.println("Sadasdasd"+userId);
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }    
	    out.println(action); 	 
	    
	    if(action.equals("new"))
	    {	    	
	    	//obj.init("");
	    	obj.createRs();
	    	session.setAttribute("obj", obj);
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongCuDungCuNew.jsp";	 
	    	response.sendRedirect(nextJSP);
	    }
	    
	    else
	    {	    	   
	    	String search = getSearchQuery(request, obj);   	
	    	obj.setUserid(userId);
	    	obj.createRs();
	    	obj.init(search);	    	
	    	session.setAttribute("obj", obj);	    		
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongCuDungCu.jsp";	    	
		    response.sendRedirect(nextJSP);
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErp_CongCuDungCu obj)
	{	
		Utility util = new Utility();
		    	
    	String ma = util.antiSQLInspection(request.getParameter("ma"));
    	if (ma == null)
    		ma = "";    	
    	obj.setMa(ma);

    	String lccdcId = util.antiSQLInspection(request.getParameter("lccdcId"));
    	if (lccdcId == null)
    		lccdcId = "";    	
    	obj.setLccdcId(lccdcId);   
    	
    	String nccdcId = util.antiSQLInspection(request.getParameter("nccdcId"));
    	if (nccdcId == null)
    		nccdcId = "";    	
    	obj.setNccdcId(nccdcId);   
    		
    	String	query =	"SELECT  CCDC.PK_SEQ, CCDC.MA, CCDC.DIENGIAI, CCDC.TRANGTHAI, ISNULL(LCCDC.DIENGIAI, '') AS LOAICCDC, " +
						"ISNULL(NCCDC.MA, '') AS NHOMCCDC, ISNULL(TTCP.MA, '') AS TTCP, " +
						"CCDC.SOTHANGKH, CCDC.THANGBATDAUKH, ISNULL((DVTH.MA+'-'+DVTH.TEN),'') AS DVTH " +
						"FROM ERP_CONGCUDUNGCU CCDC " +
						"INNER JOIN ERP_LOAICCDC LCCDC ON LCCDC.PK_SEQ = CCDC.LOAICCDC_FK " +
						"LEFT JOIN ERP_NHOMCCDC NCCDC ON NCCDC.PK_SEQ = CCDC.NHOMCCDC_FK " +
						" LEFT JOIN ERP_DONVITHUCHIEN  DVTH ON CCDC.DVTH_FK = DVTH.PK_SEQ  "+
						"LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = CCDC.TTCP_FK " +
						"WHERE CCDC.CONGTY_FK = " + obj.getCtyId() + " ";


    	if (ma.length()>0)
    	{
			query = query + " and CCDC.MA LIKE '%" + ma+ "%'";    		
    	}

    	if (lccdcId.length()>0)
    	{
			query = query + " and CCDC.LOAICCDC_FK = " + lccdcId + " ";		    		
    	}
    	

    	if (nccdcId.length()>0)
    	{
			query = query + " and CCDC.NHOMCCDC_FK = " + nccdcId + " ";		    		
    	}

    	
    	System.out.println("query search la: " + query);
    	return query;
	}	
	

}



