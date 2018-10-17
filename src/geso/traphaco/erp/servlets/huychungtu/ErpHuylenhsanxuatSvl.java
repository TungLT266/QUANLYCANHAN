package geso.traphaco.erp.servlets.huychungtu;

import geso.traphaco.erp.beans.huychungtu.IErpHuylenhsanxuat;
import geso.traphaco.erp.beans.huychungtu.IErpHuylenhsanxuatList;
import geso.traphaco.erp.beans.huychungtu.imp.ErpHuylenhsanxuat;
import geso.traphaco.erp.beans.huychungtu.imp.ErpHuylenhsanxuatList;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
 
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHuylenhsanxuatSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHuylenhsanxuatSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHuylenhsanxuatList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String hctmhId = util.getId(querystring);
	    
	    obj = new ErpHuylenhsanxuatList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    System.out.println("cong ty id: " + (String)session.getAttribute("congtyId"));
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(hctmhId);
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
	    }
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyLenhsanxuat.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHuylenhsanxuatList obj;
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
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpHuylenhsanxuat hctmhBean = new ErpHuylenhsanxuat();
	    	hctmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	hctmhBean.createRs();
    		
	    	session.setAttribute("hctmhBean", hctmhBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyLenhSanXuatNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpHuylenhsanxuatList();
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHuyLenhsanxuat.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpHuylenhsanxuatList();
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHuyLenhsanxuat.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpHuylenhsanxuatList obj)
	{
	 
		String query =  " select  CASE A.LOAICHUNGTU WHEN 'LSX' THEN  N'LỆNH SẢN XUẤT' WHEN 'THNL' THEN N'TIÊU HAO NGUYÊN LIỆU' WHEN 'NK' THEN N'NHẬP KHO' " +
						" WHEN 'YEUCAU' THEN N'YÊU CẦU NGUYÊN LIỆU' WHEN 'CHUYENKHO' THEN N'CHUYỂN KHO'  WHEN 'KDCL' THEN N'KIỂM ĐỊNH CHẤT LƯỢNG'  END AS LOAICHUNGTU , a.PK_SEQ as SOPHIEU, a.LENHSANXUAT_FK AS  SOCHUNGTU, isnull(a.SOCHUNGTUPHATSINH, '') as SOCHUNGTUPHATSINH, a.TRANGTHAI, a.NGAYTAO, a.NGAYSUA, b.TEN as NGUOITAO, c.TEN as NGUOISUA " +
						" from ERP_HUYLENHSANXUAT a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
						" where a.congty_fk = '" +  obj.getCongtyId() + "' ";

		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String sochungtu = request.getParameter("sochungtu");
		if(sochungtu == null)
			sochungtu = "";
		obj.setsochungtu(sochungtu);
		
		
		
		
		if(tungay.length() > 0)
			query += " and a.ngaytao >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaysua <= '" + denngay + "'";
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		if(nguoitao.length() > 0)
			query += " and b.TEN like N'%" + nguoitao + "%'";
		
		if(sochungtu.length() > 0)
			query += " and a.LENHSANXUAT_FK like '%" + sochungtu + "%'";
		
		
		return query;
	}
	private String Delete(String hctmhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			db.update("update ERP_HUYLENHSANXUAT set trangthai = '2' where pk_seq = '" + hctmhId + "'");
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (SQLException e)
		{ 
			db.shutDown(); 
			return "Khong the xoa huy chung tu mua hang"; 
		}
		
	}

}
