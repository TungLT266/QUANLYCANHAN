package geso.traphaco.erp.servlets.tieuchuankiemnghiem;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.thutien.IErpThutienCK;
import geso.traphaco.erp.beans.thutien.IErpThutienCKList;
import geso.traphaco.erp.beans.thutien.imp.ErpThutienCK;
import geso.traphaco.erp.beans.thutien.imp.ErpThutienCKList;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.IErpTieuChuanKiemNghiem;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.IErpTieuChuanKiemNghiemList;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.imp.ErpTieuChuanKiemNghiem;
import geso.traphaco.erp.beans.tieuchuankiemnghiem.imp.ErpTieuChuanKiemNghiemList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class ErpTieuChuanKiemNghiemSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	Runtime rt = Runtime.getRuntime();    
    public ErpTieuChuanKiemNghiemSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
	    
	    String id = util.getId(querystring);
	    System.out.println("id: " + id);
	    String action = util.getAction(querystring);
	    System.out.println("load: " + querystring);
	    
		IErpTieuChuanKiemNghiemList obj;
		
		//String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null) id = "";

	    obj = new ErpTieuChuanKiemNghiemList();
	    System.out.println("action :"+action);
	    
	 
	    
	    if (action.equals("delete"))
	    {	
	    	obj.Delete(id, userId);
	    	obj.init();
			session.setAttribute("obj", obj);
					
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuChuanKiemNghiem.jsp";
			response.sendRedirect(nextJSP);
	    }
    	
	    else
	    {	    
		    obj.setUserId(userId);
		    obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuChuanKiemNghiem.jsp";
			response.sendRedirect(nextJSP);
	    }
	}
	public static void main(String args[]){
		
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpTieuChuanKiemNghiemList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    OutputStream out = response.getOutputStream();
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    obj = new ErpTieuChuanKiemNghiemList();
	    Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null) id = "";
		obj.setId(id);
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String tungaySX = util.antiSQLInspection(request.getParameter("tungaySX"));
		if (tungaySX == null) tungaySX = "";
		obj.setTungay(tungaySX);
		
		String denngaySX = util.antiSQLInspection(request.getParameter("denngaySX"));
		if (denngaySX == null) denngaySX = "";
		obj.setDenngay(denngaySX);
		
		String matieuchuan = util.antiSQLInspection(request.getParameter("matieuchuan"));
		if (matieuchuan == null) matieuchuan = "";
		obj.setMaTieuChuan(matieuchuan);
		
		String masanpham = util.antiSQLInspection(request.getParameter("masanpham"));
		if (masanpham == null) masanpham = "";
		obj.setMaSanPham(masanpham);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpTieuChuanKiemNghiem tcknBean = new ErpTieuChuanKiemNghiem();
	    	tcknBean.setCongtyId(ctyId);
	    	tcknBean.setUserId(userId);
	    	tcknBean.createRs();
	    	session.setAttribute("tcknBean", tcknBean);

    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuChuanKiemNghiemNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
    	 	obj.setUserId(userId);
		    obj.init();
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuChuanKiemNghiem.jsp";
			response.sendRedirect(nextJSP);
	    }
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}