package geso.traphaco.erp.servlets.khovung;

import geso.traphaco.erp.beans.khovung.*;
import geso.traphaco.erp.beans.khovung.imp.*;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class KhoVungSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	//PrintWriter out;
	
    public KhoVungSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
//	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	    
	    
	    Utility util = new Utility();
	        
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IKhoVungList obj = (IKhoVungList) new KhoVungList();
	    obj.setCtyId(ctyId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String kvId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(kvId, obj);
	    }
	   	obj.init("");	   
	    obj.setUserId(userId);
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/KhoVung.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IKhoVungList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    Utility util = new Utility();
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    	        
	    
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	IKhoVung kvBean = (IKhoVung) new KhoVung("");
	    	kvBean.setCtyId(ctyId);
	    	kvBean.setUserId(userId);

	    	// Save Data into session
	    	session.setAttribute("kvBean", kvBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Erp/KhoVungNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    
	    if (action.equals("search"))	    
	    {
		   	obj = (IKhoVungList) new KhoVungList();
		   	obj.setCtyId(ctyId);
		   	
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/KhoVung.jsp");	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IKhoVungList obj){
		   // PrintWriter out = response.getWriter();
			Utility util = new Utility();
			String ma = util.antiSQLInspection(request.getParameter("ma"));
	    	if ( ma == null)
	    		ma = "";
	    	obj.setMa(ma);
	    	
	    	String ten = util.antiSQLInspection(request.getParameter("ten"));
	    	if (ten == null)
	    		ten = "";    	
	    	obj.setTen(ten);
	    	
	    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	    	if (trangthai.equals("2"))
	    		trangthai = "";
	    	
	    	obj.setTrangthai(trangthai);
	    	
	    	String query;
			query = "SELECT KV.PK_SEQ as ID, KV.MA, KV.TEN, KV.TRANGTHAI, KV.NGAYTAO, NV1.TEN as NGUOITAO, KV.NGAYSUA, NV2.TEN as NGUOISUA \n " +
					"FROM ERP_KHOVUNG KV \n " +
					"INNER JOIN NHANVIEN NV1 on nv1.pk_seq = KV.NGUOITAO \n " +
					"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = KV.NGUOISUA \n " +
					"WHERE KV.CONGTY_FK = " + obj.getCtyId() + " ORDER BY KV.TEN ";

	    	if (ma.length()>0){
				query = query + " and upper(a.ma) like upper(N'%" + ma + "%')";
				
	    	}
	    	
	    	if (ten.length()>0){
				query = query + " and upper(a.ten) like upper(N'%" + ten + "%')";
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = '" + trangthai + "'";
	    	}
	    	query = query + " order by ma";
	    	System.out.println("sql:"+ query);
	    	return query;
		}	
	
	private void Delete(String id, IKhoVungList obj)
	{
		dbutils db = new dbutils();
		ResultSet rs1 = db.get("select count(KHOVUNG_FK) as num from ERP_SOHOADON where KHOVUNG_FK='"+ id + "'");
		try{
			rs1.next();			
			if (rs1.getString("num").equals("0")){
				db.update("delete from ERP_KHOVUNG where pk_seq = '" + id + "'");
				db.shutDown();
			}
			else
				obj.setMsg("Kho vùng này đã được dùng để khai báo số hóa đơn, nên không thể xóa được");
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
	}

}

