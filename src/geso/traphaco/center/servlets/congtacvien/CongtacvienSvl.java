package geso.traphaco.center.servlets.congtacvien;

import geso.traphaco.center.beans.congtacvien.*;
import geso.traphaco.center.beans.congtacvien.imp.*;
import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CongtacvienSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet 
{
	private static final long serialVersionUID = 1L;
   
    public CongtacvienSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ICongtacvienList obj = new CongtacvienList();
	    
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	       	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    PrintWriter out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);

	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    
	    String ddkdId = util.getId(querystring);
	    
	   // System.out.println("Action nek :"+action);
	    if (action.equals("delete"))
	    { 
	    	Delete(ddkdId,obj);
	    	//out.print(ddkdId);
	    }
	    obj.setRequestObj(request);
	    obj.setUserId(userId);
	    //System.out.println("user iad 1: "+userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/CongTacVien.jsp";
		response.sendRedirect(nextJSP);
	}

	String query1="";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ICongtacvienList obj = new CongtacvienList();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	   
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    String action = request.getParameter("action");
	      //----
	    obj.setUserId(userId);
	    obj.setRequestObj(request);
	    String search = "";
	    String nextJSP = "";
	    
	    if (action.equals("new"))
	    {
	    	// Empty Bean for distributor
	    	ICongtacvien ddkdBean = (ICongtacvien) new Congtacvien("");
	    	ddkdBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("ddkdBean", ddkdBean);
    		
    		nextJSP = "/TraphacoHYERP/pages/Center/CongTacVienNew.jsp";  		
	    }
	    else if (action.equals("search")){	    
	    	search = getSearchQuery(request,obj);
			obj.setUserId(userId);
			
    		session.setAttribute("abc", search);
	    		
    		nextJSP = "/TraphacoHYERP/pages/Center/CongTacVien.jsp";
	    }
	    else{
		    
		    //phantrang
		    
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");

	    	//------------------------
	    	
	    	search = getSearchQuery(request, obj);

	    	nextJSP = "/TraphacoHYERP/pages/Center/CongTacVien.jsp";
	    }
	    
	    obj.init(search);
	   
    	session.setAttribute("obj", obj);  	
		session.setAttribute("userId", userId);
    		
		response.sendRedirect(nextJSP); 
	}
	
	private String getSearchQuery(HttpServletRequest request,ICongtacvienList obj)
	{	
		
		Utility util = new Utility();
		
		String ma = util.antiSQLInspection(request.getParameter("ma"));
    	if (ma == null)
    		ma = "";    	
    	obj.setMa(ma);
		
		String ten = util.antiSQLInspection(request.getParameter("ten"));
    	if ( ten == null)
    		ten = "";
    	obj.setTen(ten);
    	
    	String sodienthoai = util.antiSQLInspection(request.getParameter("DienThoai"));
    	if (sodienthoai == null)
    		sodienthoai = "";    	
    	obj.setSodienthoai(sodienthoai);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));   	
    	if (trangthai == null)
    		trangthai = "";
    	if (trangthai.equals("2"))
    		trangthai = "";   
    	obj.setTrangthai(trangthai);
    	
    	String query =  "select a.ma, a.pk_seq  as id, a.ten , a.dienthoai, a.diachi, a.trangthai, a.ngaytao,   " +
						"	b.ten as nguoitao, a.ngaysua, c.ten as nguoisua " +
						"from congtacvien a inner join nhanvien b on a.nguoitao = b.pk_seq   " +
				
						"	 inner join  nhanvien c on   a.nguoisua = c.pk_seq  " +
				
						"where 1=1  ";
		
    	if (ma.length()>0)
    	{
			query = query + " and a.MA like upper('%" + ma + "%')";
    	}
    	
    	if (ten.length()>0)
    	{
			query = query + " and a.TIMKIEM like upper(dbo.ftBoDau(N'%" +ten + "%'))";
    	}
    	
    	if (sodienthoai.length()>0)
    	{
			query = query + " and upper(a.dienthoai) like upper('%" + sodienthoai + "%')";
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		
    	}
    	
    	System.out.println("Serch   "+query);
    	return query;
	}
	
	private void Delete(String id,ICongtacvienList obj)
	{
		dbutils db = new dbutils();
		
			try{
			db.getConnection().setAutoCommit(false);
			String sql="delete from ddkd_gsbh where ddkd_fk = '" + id + "'";
			System.out.println("1." +sql);
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa khi có giám sát");
				db.shutDown();
				return;
			}
			 sql="delete from daidienkinhdoanh where pk_Seq = '" + id + "'";
			 System.out.println("2." +sql);
			
			if(!db.update(sql))
			{
				db.update("rollback");
				System.out.println("Vao rollback");
				obj.setMsg("Không thể xóa khi đã có tuyến");
				db.shutDown();
				return;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}catch(Exception  e)
		{
			System.out.println("Loi "+e.toString());
			db.update("rollback");
			obj.setMsg("Lỗi " + e.toString());
			db.shutDown();
			return;
		}
		
	}
}




