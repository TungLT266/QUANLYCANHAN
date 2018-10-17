package geso.traphaco.erp.servlets.huychungtu;
 
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.huychungtu.IErpHuychungtubanhang;
import geso.traphaco.erp.beans.huychungtu.IErpHuychungtubanhangList;
import geso.traphaco.erp.beans.huychungtu.imp.ErpHuychungtubanhang;
import geso.traphaco.erp.beans.huychungtu.imp.ErpHuychungtubanhangList;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHuychungtuBhSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpHuychungtuBhSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHuychungtubanhangList obj;
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
	    
	    String hctbhId = util.getId(querystring);
	    
	    
        String nppId = util.getIdNhapp(userId);
	    if(nppId == null)
	    	nppId = "";
	    
	    
	    obj = new ErpHuychungtubanhangList();
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(hctbhId);
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
	    }
	    if (action.equals("chot"))
	    {	
	    	IErpHuychungtubanhang hct = new ErpHuychungtubanhang(hctbhId);

	    	hct.setnppId(util.getIdNhapp(userId));
	    	String msg = hct.Chot(hctbhId);
	    	
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
	    }
	   	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuBanHang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpHuychungtubanhangList obj;
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
	    	IErpHuychungtubanhang hctbhBean = new ErpHuychungtubanhang();
	    	hctbhBean.createRs();
    		
	    	session.setAttribute("hctbhBean", hctbhBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuBanHangNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpHuychungtubanhangList();
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHuyChungTuBanHang.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpHuychungtubanhangList();
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHuyChungTuBanHang.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpHuychungtubanhangList obj)
	{
		String query = "select a.PK_SEQ as SOPHIEU, a.SOCHUNGTU, isnull(a.SOCHUNGTUPHATSINH, '') as SOCHUNGTUPHATSINH, a.TRANGTHAI, a.NGAYTAO, a.NGAYSUA, b.TEN as NGUOITAO, c.TEN as NGUOISUA " +
				"from ERP_HUYCHUNGTUBANHANG a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq where a.PK_SEQ > 0 ";
		
		String nguoitao = request.getParameter("tennguoitao");
		if(nguoitao == null) nguoitao = "";
		obj.setTennguoitao(nguoitao.trim());
		nguoitao=nguoitao.trim();
		System.out.println("ten nguoi tao="+obj.getTennguoitao());
		
		String sochungtu = request.getParameter("sochungtu");
		if(sochungtu == null)
			sochungtu = "";
		obj.setSochungtu(sochungtu);
		
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
		
		if(tungay.length() > 0)
			query += " and a.ngaytao >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaysua <= '" + denngay + "'";		
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";

		if(sochungtu.length() > 0)
			query += " and a.sochungtu like N'%" + sochungtu + "%'";
		
		if(nguoitao.length() > 0)
			query += " and b.TEN like N'%" + nguoitao + "%'";
		
		return query;
	}
	
	private String Delete(String hctbhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			db.update("update ERP_HUYCHUNGTUBANHANG set trangthai = '2' where pk_seq = '" + hctbhId + "'");
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (SQLException e)
		{ 
			db.shutDown(); 
			return "Khong the xoa huy chung tu ban hang"; 
		}
		
	}

}
