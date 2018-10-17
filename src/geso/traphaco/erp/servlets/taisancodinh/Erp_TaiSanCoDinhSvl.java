package geso.traphaco.erp.servlets.taisancodinh;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.taisancodinh.IErp_TaiSanCoDinh;
import geso.traphaco.erp.beans.taisancodinh.imp.Erp_TaiSanCoDinh;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.record.formula.functions.Npv;

public class Erp_TaiSanCoDinhSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public Erp_TaiSanCoDinhSvl() {
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
	    
	    IErp_TaiSanCoDinh obj = new Erp_TaiSanCoDinh();
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
		
		if (action.equals("chuyen"))
		{
			obj.setId(id);
	    	obj.init_convert();
	    	obj.createRs();
	    	session.setAttribute("obj", obj);
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinhNew.jsp";	 
	    	response.sendRedirect(nextJSP);
	    	return;
		}
		
		/*String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
		System.out.println("SEQRCH :"+searchQuery);
 		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);*/
	    obj.setUserid(userId);
		obj.createRs();
		obj.init("");	
		String querySearch = GiuDieuKienLoc.createParams(obj);
		util.setSearchToHM(userId, session, this.getServletName(), querySearch);
		
		session.setAttribute("obj", obj);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinh.jsp");	
	}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
	    IErp_TaiSanCoDinh obj;	    
	    obj = new Erp_TaiSanCoDinh();	
	    
	    obj.setCtyId((String) session.getAttribute("congtyId"));
	    
	    String userId;
	    userId = util.antiSQLInspection(request.getParameter("userId"));

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
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinhNew.jsp";	 
	    	response.sendRedirect(nextJSP);
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
    		System.out.println("toi day");
    		obj = new Erp_TaiSanCoDinh();
    		obj.setCtyId((String)session.getAttribute("congtyId"));
 
	    	String search = getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	/*obj.setUserId(userId);*/
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinh.jsp");	
	    	
	    	
    	}
	    
	    else
	    {	    	   
	    	String search = getSearchQuery(request, obj);   	
	    	obj.setUserid(userId);
	    	obj.createRs();
	    	obj.init(search);
	    	String querySearch = GiuDieuKienLoc.createParams(obj);
			util.setSearchToHM(userId, session,this.getServletName(), querySearch);
	    	session.setAttribute("obj", obj);	    		
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinh.jsp";	    	
		    response.sendRedirect(nextJSP);
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErp_TaiSanCoDinh obj)
	{	
		Utility util = new Utility();
		    	
    	String ma = util.antiSQLInspection(request.getParameter("ma"));
    	if (ma == null)
    		ma = "";    	
    	obj.setMa(ma);
    	System.out.println("bo m day ne"+ma);
    	String ltsId = util.antiSQLInspection(request.getParameter("ltsId"));
    	if (ltsId == null)
    		ltsId = "";    	
    	obj.setLtsId(ltsId);  
    	
    	
    	String ttcpId = util.antiSQLInspection(request.getParameter("ttcpId"));
    	if (ttcpId == null)
    		ttcpId = "";    	
    	obj.setTtcp(ttcpId);
    	System.out.println("tao day ne"+ttcpId);
    	
    	String ntsId = util.antiSQLInspection(request.getParameter("ntsId"));
    	if (ntsId == null)
    		ntsId = "";    	
    	obj.setNtsId(ntsId);   
    		
    	String phanloai = util.antiSQLInspection(request.getParameter("phanloai"));
    	if (phanloai == null)
    		phanloai = "";    	
    	obj.setPhanloai(phanloai);
    	
    	String trangthai = request.getParameter("trangthai");
 	    if (trangthai == null){
 	    	trangthai = "";
 	    }    
 	    obj.setTrangthai(trangthai);
 	    

    	String query =	"SELECT * FROM ( \n " +
    					"SELECT  TSCD.PK_SEQ, TSCD.MA, TSCD.DIENGIAI, TSCD.TRANGTHAI, LTS.PK_SEQ AS LTSID, ISNULL(LTS.DIENGIAI, '') AS LOAITAISAN,  \n " +
    					"ISNULL(NTS.MA, '') AS NHOMTAISAN, ISNULL(TTCP.MA, '') AS TTCP,  \n " +
    					"TSCD.SOTHANGKH, TSCD.THANGBATDAUKH,  \n " +
    					"N'TSCD' AS PHANLOAI, TSCD.PHANLOAI AS LOAI \n " +
    					"FROM ERP_TAISANCODINH TSCD  \n " +
    					"LEFT JOIN ERP_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK  \n " +
    					"LEFT JOIN ERP_NHOMTAISAN NTS ON NTS.PK_SEQ = TSCD.NHOMTAISAN_FK  \n " +
    					"LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK  \n " +
    					"WHERE TSCD.CONGTY_FK = " + obj.getCtyId() + " AND ISNULL(PHANLOAI,'1') = 1 \n " +

    					"UNION ALL  \n " +
    					"SELECT TSCD.PK_SEQ, TSCD.MA, TSCD.DIENGIAI,  TSCD.TRANGTHAI, LTS.PK_SEQ AS LTSID,  \n " +
    					"ISNULL(LTS.DIENGIAI, '') AS LOAITAISAN,  '' AS NHOMTAISAN, '' AS TTCP,   \n " +
    					"TSCD.SOTHANGKH, TSCD.THANGBATDAUKH,  \n " +
    					"N'TSDD' AS PHANLOAI, TSCD.PHANLOAI AS LOAI \n " +
    					"FROM ERP_TAISANCODINH TSCD   \n " +
    					"LEFT JOIN ERP_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK  \n " +
    					"WHERE TSCD.CONGTY_FK = " + obj.getCtyId() + " AND PHANLOAI = 2 \n"+ 
    					")A WHERE 1 = 1 \n " ;


    	if (ma.length()>0)
    	{
    		query = query + " and A.MA LIKE '%" + ma+ "%'";    		
    	}

    	if (ltsId.length()>0)
    	{
    		query = query + " and A.LTSID = " + ltsId + " ";		    		
    	}


    	if (ntsId.length()>0)
    	{
    		query = query + " and A.NHOMTAISAN = " + ntsId + " ";		    		
    	}

    	if (phanloai.length()>0)
    	{
    		query = query + " and A.LOAI = " + phanloai + " ";		    		
    	}
    	
     	if (trangthai.length()>0)
    	{
    		query = query + " and A.TRANGTHAI = " + trangthai + " ";		    		
    	}
    	
    	
    	System.out.println("query search la: " + query);
    	return query;
	}	
	

}



