package geso.erp.servlets.lapkehoach;

import geso.erp.beans.lapkehoach.*;
import geso.erp.beans.lapkehoach.imp.*;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDenghihuymuaSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpDenghihuymuaSvl() {
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
	    
	    
	    Utility util = new Utility();
		    	    
	    String querystring = request.getQueryString();
		    
	    String userId = util.getUserId(querystring);
		    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
	    IErpDenghihuymua obj = new ErpDenghihuymua();
		obj.setCtyId(ctyId);
		obj.setUserId(userId);
		 
		obj.init();
		session.setAttribute("obj", obj);
		    
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDenghihuymua.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
	    ;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if(nam == null) nam = "";
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if(thang == null) thang = "";
		
    	String msg = "";

    	IErpDenghihuymua obj = new ErpDenghihuymua();
    	obj.setCtyId(ctyId);
	    obj.setUserId(userId);
	    obj.setNam(nam);
	    obj.setThang(thang);
	    obj.init();
	    obj.setMsg(msg);
				
	    session.setAttribute("obj", obj);  	
    	session.setAttribute("userId", userId);
		
    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDenghihuymua.jsp");	
	    
	}
	
	
	private String getSearchQuery(HttpServletRequest request, IErpDenghihuymua obj) 
	{
		Utility util = new Utility();
		
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if(nam == null)
			nam = "";
		obj.setNam(nam);
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		
		/*String sql =	"SELECT	MNLDK.NAM, MNLDK.THANG,  'PR-' + CONVERT(VARCHAR, MNLDK.SANPHAM_FK) AS ID, MNLDK.SANPHAM_FK AS SPID,	" + 
						"MNLDK.SOLUONG AS QTY, MNLDK.DADATHANG AS DADAT, " +
						"CASE WHEN (MNLDK.SOLUONG - MNLDK.DADATHANG) < 0 THEN (MNLDK.DADATHANG - MNLDK.SOLUONG) ELSE 0 END AS DENGHIHUY, " + 
						"MHSP.MUAHANG_FK AS POID, NCC.TEN, MHSP.SOLUONG AS DATHANG, MHSP.NGAYNHAN " +
						"FROM ERP_MUANGUYENLIEUDUKIEN MNLDK " +
						"INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.SANPHAM_FK = MNLDK.SANPHAM_FK AND SUBSTRING(MHSP.NGAYNHAN, 1, 4) = MNLDK.NAM " +
						"AND CONVERT(INT, SUBSTRING(MHSP.NGAYNHAN, 6, 2)) = MNLDK.THANG " +
						"INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MHSP.MUAHANG_FK " +
						"INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MH.NHACUNGCAP_FK " +
						"WHERE (MNLDK.DADATHANG - MNLDK.SOLUONG) > 0 AND MNLDK.CONGTY_FK = '" + obj.getCtyId() + " ";*/
		String sql = " select *, huymua.DADAT - huymua.QTY as DENGHIHUY" + 
					 " from" + 
					 " (" + 
					 " 	SELECT	MNLDK.PK_SEQ AS ID, MNLDK.MASANPHAM AS SPID, MNLDK.MASANPHAM, MNLDK.MASANPHAM AS SP, MNLDK.DORONG, MNLDK.NGAYKETTHUC AS NGAY,   " + 
					 " 		NV1.TEN AS NGUOITAO, NV2.TEN AS NGUOISUA, MNLDK.NGAYTAO, MNLDK.NGAYSUA, MNLDK.TRANGTHAI, MNLDK.NAM, MNLDK.THANG, MNLDK.SOLUONG as QTY, " + 
					 " 		isnull( ( SELECT SUM(SOLUONG) AS SOLUONG" + 
					 " 				  FROM ERP_MUAHANG_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " + 
					 " 				  WHERE a.SANPHAM_FK IS NOT NULL and SUBSTRING(NGAYNHAN, 1, 4) = MNLDK.NAM and CONVERT(INT, SUBSTRING(NGAYNHAN, 6,2)) = MNLDK.THANG " + 
					 " 						and b.MA = MNLDK.MASANPHAM" + 
					 " 				  GROUP BY b.MA  ) , 0) as DADAT  " + 
					 " 	FROM ERP_MUANGUYENLIEUDUKIEN MNLDK  " + 
					 " 	INNER JOIN NHANVIEN NV1 ON MNLDK.NGUOITAO = NV1.PK_SEQ  " + 
					 " 	INNER JOIN NHANVIEN NV2 ON MNLDK.NGUOISUA = NV2.PK_SEQ  " + 
					 " 	WHERE MNLDK.CONGTY_FK =  '100005'  AND MNLDK.NAM = '" + Integer.parseInt(nam) + "'  ";
		
		if(!thang.equals("0")){
			
			if(thang.length() > 0)
				sql += " and MNLDK.thang = " + Integer.parseInt(thang) + " ";
		}

		sql += 		 " )" + 
					 " huymua where huymua.DADAT - huymua.QTY > 0" ;
		
		
		sql += " ORDER BY NAM, THANG, MASANPHAM ";
		
		return sql;
	}
	

}
