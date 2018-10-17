package geso.traphaco.erp.servlets.dieuchuyentien;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.dieuchuyentien.IErpDieuchuyentien;
import geso.traphaco.erp.beans.dieuchuyentien.IErpDieuchuyentienList;
import geso.traphaco.erp.beans.dieuchuyentien.imp.ErpDieuchuyentien;
import geso.traphaco.erp.beans.dieuchuyentien.imp.ErpDieuchuyentienList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDieuchuyentienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDieuchuyentienSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDieuchuyentienList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String Id = util.getId(querystring);
	    
	    obj = new ErpDieuchuyentienList();
	    obj.setCongtyId(ctyId);
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(Id);
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		IErpDieuchuyentien dct = new ErpDieuchuyentien();
	    		dct.setId(Id);
	    		///
	    	    dct.setCongtyId(ctyId);
	    		///
	    		dct.init();
	    		if(!dct.Chot())
	    		{
	    			obj.setMsg(dct.getMsg());
	    		}
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDieuChuyenTien.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDieuchuyentienList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    if(action.equals("Tao moi"))
	    {
		    IErpDieuchuyentien dc = new ErpDieuchuyentien();
		    dc.setCongtyId(ctyId);
		    dc.setUserId(userId);
		    dc.createRs();
		    
			session.setAttribute("obj", dc);
					
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDieuChuyenTienNew.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		System.out.println("Chạy vào đây");
	    		obj = new ErpDieuchuyentienList();
	    		obj.setCongtyId(ctyId);
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init(search);
		    	obj.setUserId(userId);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	session.setAttribute("userId", userId);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDieuChuyenTien.jsp");	
		    }else{
		    	obj = new ErpDieuchuyentienList();
		    	obj.setCongtyId(ctyId);
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDieuChuyenTien.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDieuchuyentienList obj)
	{

		String /*query = 	"SELECT DC.PK_SEQ AS DCID, DC.SOCHUNGTU, DC.NGAYCHUNGTU, TT.PK_SEQ AS TTID, TT.MA AS TIENTE, " + 
						"NH1.MA + ' - ' + CN1.MA AS NHCHUYEN, " +
						"NH2.MA + ' - ' + CN2.MA AS NHNHAN, " +
						"DC.SOTIENNT, DC.SOTIENVND, DC.TRANGTHAI, NV1.TEN AS NGUOISUA, DC.NGAYSUA " +

						"FROM ERP_DIEUCHUYENTIEN DC " +
						"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = DC.TIENTE_FK " +
						"INNER JOIN ERP_NGANHANG_CONGTY NH_CTY1 ON NH_CTY1.PK_SEQ = DC.NGANHANGCHUYEN_FK " +
						"INNER JOIN ERP_NGANHANG NH1 ON NH1.PK_SEQ = NH_CTY1.NGANHANG_FK " +
						"INNER JOIN ERP_CHINHANH CN1 ON CN1.PK_SEQ = NH_CTY1.CHINHANH_FK " +
						
						"INNER JOIN ERP_NGANHANG_CONGTY NH_CTY2 ON NH_CTY2.PK_SEQ = DC.NGANHANGNHAN_FK " +
						"INNER JOIN ERP_NGANHANG NH2 ON NH2.PK_SEQ = NH_CTY2.NGANHANG_FK " +
						"INNER JOIN ERP_CHINHANH CN2 ON CN2.PK_SEQ = NH_CTY2.CHINHANH_FK " +
						"WHERE DC.CONGTY_FK = " + obj.getCongtyId() + " ";*/
		query = 	"SELECT DC.PK_SEQ AS DCID, DC.SOCHUNGTU, DC.NGAYCHUNGTU, TT.PK_SEQ AS TTID, TT.MA AS TIENTE, " + 
		"NH1.MA + ' - ' + CN1.MA AS NHCHUYEN, " +
		"NH2.MA + ' - ' + CN2.MA AS NHNHAN, " +
		"DC.SOTIENNT, DC.SOTIENVND, DC.TRANGTHAI, NV1.TEN AS NGUOISUA, DC.NGAYSUA " +

		"FROM ERP_DIEUCHUYENTIEN DC " +
		"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = DC.TIENTE_FK " +
		"INNER JOIN ERP_NGANHANG_CONGTY NH_CTY1 ON NH_CTY1.PK_SEQ = DC.NGANHANGCHUYEN_FK " +
		"INNER JOIN ERP_NGANHANG NH1 ON NH1.PK_SEQ = NH_CTY1.NGANHANG_FK " +
		"INNER JOIN ERP_CHINHANH CN1 ON CN1.PK_SEQ = NH_CTY1.CHINHANH_FK " +

		"INNER JOIN ERP_NGANHANG_CONGTY NH_CTY2 ON NH_CTY2.PK_SEQ = DC.NGANHANGNHAN_FK " +
		"INNER JOIN ERP_NGANHANG NH2 ON NH2.PK_SEQ = NH_CTY2.NGANHANG_FK " +
		"INNER JOIN ERP_CHINHANH CN2 ON CN2.PK_SEQ = NH_CTY2.CHINHANH_FK " +
		"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = DC.NGUOISUA " +
		"WHERE DC.CONGTY_FK = " + obj.getCongtyId() + "";
		
		
		Utility util = new Utility();
		
		String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
		if(ngaychungtu == null)
			ngaychungtu = "";
		obj.setNgaychungtu(ngaychungtu);
		
		String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
		if(sochungtu == null)
			sochungtu = "";
		obj.setSochungtu(sochungtu);
		
		String ttId = util.antiSQLInspection(request.getParameter("ttId"));
		if(ttId == null)
			ttId = "";
		obj.setTtId(ttId);
		
		String nhchuyenId = util.antiSQLInspection(request.getParameter("nhchuyenId"));
		if(nhchuyenId == null)
			nhchuyenId = "";
		obj.setNhchuyenId(nhchuyenId);

		String nhnhanId = util.antiSQLInspection(request.getParameter("nhnhanId"));
		if(nhnhanId == null)
			nhnhanId = "";
		obj.setNhnhanId(nhnhanId);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		if(ngaychungtu.length() > 0)
			query += " AND DC.NGAYCHUNGTU = '" + ngaychungtu + "'";
		
		if(sochungtu.length() > 0)
			query += " AND DC.SOCHUNGTU LIKE N'%" + sochungtu + "%'";
		
		if(ttId.length() > 0)
			query += " AND DC.TIENTE_FK = '" + ttId + "'";
		
		if(nhchuyenId.length() > 0)
			query += " AND DC.NGANHANGCHUYEN_FK = '" + nhchuyenId + "'";
		
		if(nhnhanId.length() > 0)
			query += " AND DC.NGANHANGNHAN_FK = '" + nhnhanId + "'";

		if(trangthai.length() > 0)
			query += " AND DC.TRANGTHAI = '" + trangthai + "'";
		
		System.out.println("search quyry chuyen tien: " + query);
		
		return query;
	}
	
	private String Delete(String Id)
	{
		dbutils db = new dbutils();
		
		String query = "DELETE ERP_DIEUCHUYENTIEN WHERE PK_SEQ = " + Id + "";
		db.update(query);
			
		db.shutDown();
			
		return "";
	}
}