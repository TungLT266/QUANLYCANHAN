package geso.traphaco.distributor.servlets.khachhangtt;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.distributor.beans.khachhangtt.IKhachhangTT;
import geso.traphaco.distributor.beans.khachhangtt.IKhachhangTTList;
import geso.traphaco.distributor.beans.khachhangtt.imp.KhachhangTT;
import geso.traphaco.distributor.beans.khachhangtt.imp.KhachhangTTList;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class KhachHangTraTruocSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public KhachHangTraTruocSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IKhachhangTTList obj;
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
	    
	    String tthdId = util.getId(querystring);
	    
	    obj = new KhachhangTTList();
	    obj.setCtyId(ctyId);

	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(tthdId);
	    	if(msg.length() > 0)
	    		obj.setmsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		IKhachhangTT tthd = new KhachhangTT(tthdId);
	    		if(!tthd.chotTTHD(userId))
	    		{
	    			obj.setmsg(tthd.getMsg());
	    		}
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangTT.jsp";
		response.sendRedirect(nextJSP);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IKhachhangTTList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    OutputStream out = response.getOutputStream();
	    
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
	    	IKhachhangTT tthdBean = new KhachhangTT();
	    	tthdBean.setCtyId(ctyId);
	    	tthdBean.setUserId(userId);
	    	tthdBean.setCtyId(ctyId);
	    	tthdBean.createRs();
    		
	    	session.setAttribute("tthdBean", tthdBean);

    		String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangTTNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new KhachhangTTList();
	    		obj.setCtyId(ctyId);
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/ErpThuTienNPP.jsp");	
		    }	    	
	    	else
	    	{
		    	obj = new KhachhangTTList();
		    	obj.setCtyId(ctyId);
		    	String search = getSearchQuery(request, obj);
		    	obj.setUserId(userId);
		    	obj.init(search);
				
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/KhachHangTT.jsp");
	    	}
	    }
	}
	
	
	
	
	
	private String getSearchQuery(HttpServletRequest request, IKhachhangTTList obj)
	{
		Utility util = new Utility();
		
		String query =  "select a.pk_seq as tthdId, a.trangthai, a.ngaychungtu,  a.ngaytao, a.ngaysua,  "+
	     "  d.ten as nguoitao, e.ten as nguoisua,  isnull(a.SOTIENHD,0) AS THUCTHU  "+
	     " from KHACHHANGTRATRUOC a  inner join NHANVIEN d on a.nguoitao = d.pk_seq   "+
	     " inner join NHANVIEN e on a.nguoisua = e.pk_seq where a.pk_seq > 0 ";

		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);		
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nvgnId = util.antiSQLInspection(request.getParameter("nvgnId"));
		if(nvgnId == null)
			nvgnId = "";
		obj.setNvgnId(nvgnId);
		
		String nvbhId = util.antiSQLInspection(request.getParameter("nvbhId"));
		if(nvbhId == null)
			nvbhId = "";
		obj.setNvbhId(nvbhId);
		
		String khId = util.antiSQLInspection(request.getParameter("khId"));
		if(khId == null)
			khId = "";
		obj.setKhId(khId);
		
		if(tungay.length() > 0)
			query += " and a.ngaychungtu >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaychungtu <= '" + denngay + "'";
		
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		if(nvgnId.length() > 0)
			query += " and a.nvgn_fk = '" + nvgnId + "'";
		
		if(nvbhId.length() > 0)
			query += " and a.nvbh_fk = '" + nvbhId + "'";
		
		if(khId.length() > 0)
			query += " and a.khachhang_fk = '" + khId + "'";
		
		return query;
	}
	
	private String Delete(String tthdId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			db.update("update KHACHHANGTRATRUOC set trangthai= '2' where PK_SEQ = '" + tthdId + "'");			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (SQLException e)
		{ 
			db.shutDown(); 
			return "Khong the xoa KHACHHANGTRATRUOC"; 
		}
		
	}

}
