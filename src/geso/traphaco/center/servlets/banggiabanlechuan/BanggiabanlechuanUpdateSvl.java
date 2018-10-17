package geso.traphaco.center.servlets.banggiabanlechuan;

import geso.traphaco.center.beans.banggiablc.IBanggiablc;
import geso.traphaco.center.beans.banggiablc.IBanggiablcList;
import geso.traphaco.center.beans.banggiablc.imp.Banggiablc;
import geso.traphaco.center.beans.banggiablc.imp.BanggiablcList;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


 public class BanggiabanlechuanUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
  

   public BanggiabanlechuanUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
		HttpSession session = request.getSession();
		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    out.println(id);
	    
	    IBanggiablc bgBean = new Banggiablc();
	    
        bgBean.setUserId(userId);
        bgBean.setId(id);
        bgBean.init();
        
        session.setAttribute("bgblcBean", bgBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeChuanUpdate.jsp";
        response.sendRedirect(nextJSP);

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		OutputStream out = response.getOutputStream();
		
		IBanggiablc bgBean = new Banggiablc();
	    Utility util = new Utility();
	    
		String id =  util.antiSQLInspection(request.getParameter("id"));
	    
	    if(id == null){  	
	    	id = "";
	    }
	    bgBean.setId(id);
	    
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		bgBean.setUserId(userId);
	    
    	String bgTen = request.getParameter("bgTen");
		if (bgTen == null)
			bgTen = "";				
    	bgBean.setTen(bgTen);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		bgBean.setDvkdId(dvkdId);
	        	
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	bgBean.setTrangthai(trangthai);
		
    	
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		bgBean.setTungay(tungay);
    	
    	String[] giablc = request.getParameterValues("giablc");
    	bgBean.setGiablc(giablc);
    	
    	String[] quycach= request.getParameterValues("quycach");
    	bgBean.setQuycach(quycach);
    	
    	
		String ngaysua = getDateTime();
    	bgBean.setNgaysua(ngaysua);
    	bgBean.setNgaytao(ngaysua);
    	
		bgBean.setNguoitao(userId);
		bgBean.setNguoisua(userId);
    			
		boolean error = false;
				
		if (dvkdId.trim().length()== 0){
			bgBean.setMessage("Vui long chon Don vi kinh doanh");
			error = true;
		}
		if (bgTen.trim().length()== 0){
			bgBean.setMessage("Vui long nhap vao ten bang gia");
			error = true;
		}

		String action = request.getParameter("action");
	    
		if (action.equals("excel"))
	    {
	    	try
		    {
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=BangGiaBanLeChuan.xls");
		        
		        Workbook workbook = new Workbook();
		    	
			     CreateStaticHeader(workbook, bgTen, dvkdId);
			     CreateStaticData(workbook, getSearchQuery2(request, "", ""));
			
			     //Saving the Excel file
			     workbook.save(out);
		    }
		    catch (Exception ex){ }
	    	
		    bgBean.setUserId(userId);
			bgBean.setId(id);
			bgBean.init();
			session.setAttribute("bgblcBean", bgBean);
			String nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeChuanUpdate.jsp";
			response.sendRedirect(nextJSP);	
	    }
		
		if(action.equals("save"))
		{
			if (id.length()==0){
				if (!(bgBean.CreateBgblc(request))){									
					bgBean.setUserId(userId);
					bgBean.createRS();
					session.setAttribute("bgblcBean", bgBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeChuanNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IBanggiablcList obj = new BanggiablcList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeChuan.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(bgBean.UpdateBgblc(request)))
				{								
					bgBean.setUserId(userId);
					bgBean.init();
					session.setAttribute("bgblcBean", bgBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeChuanUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IBanggiablcList obj = new BanggiablcList();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeChuan.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}else {
			System.out.println("id la "+id);
			String nextJSP;
			if (id.length()==0)
			{							
				bgBean.setUserId(userId);
				bgBean.createRS();
				session.setAttribute("bgblcBean", bgBean);
				nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeChuanNew.jsp";
			}
			else
			{
				bgBean.setUserId(userId);
				bgBean.setId(id);
				bgBean.init();
				bgBean.setDvkdId(dvkdId);
				session.setAttribute("bgblcBean", bgBean);
				
				nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeChuanUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
			
		}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private void CreateStaticHeader(Workbook workbook, String tenbg, String dvkd) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("Bảng giá bán");
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Bảng giá:  " + tenbg);
	    cell = cells.getCell("A4");
	    cell.setValue("Mã đơn vị kinh doanh:  " + dvkd);
	    cell = cells.getCell("A5");
	    cell.setValue("Ngày tạo: " + this.getDateTime());
	    
	    //tieu de
	    cell = cells.getCell("A10");
	    cell.setValue("Mã sản phẩm");
	    cell = cells.getCell("B10");
	    cell.setValue("Tên sản phẩm");
	    cell = cells.getCell("C10");
	    cell.setValue("Giá bán lẻ chuẩn");

	    worksheet.setName("Bảng giá bán");
	}

	private void CreateStaticData(Workbook workbook, String query) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		
		int i = 11;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 25.0f);
				cells.setColumnWidth(1, 45.0f);
				cells.setColumnWidth(2, 20.0f);
				
				for(int j = 0; j < 3; j++)
					cells.setRowHeight(j, 13.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					String masp = "";
					if(rs.getString("spMa")!= null)
						masp = rs.getString("spMa");
					
					String tensp = "";
					if(rs.getString("spTen") != null)
						tensp = rs.getString("spTen");
					
					String gblc = "";
					if(rs.getString("gblc") != null)
						gblc = rs.getString("gblc");
					
					
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(masp);
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(tensp);
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(gblc);
					
					i++;
				}
				rs.close();
			}
			catch(Exception e){ }
		}
		/*
		//create pivot
		PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);		
	    int index = pivotTables.add("=A8:H" + pos,"A8","DanhSachSanPham");

	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);

	    //Unshowing grand totals for rows.
	    pivotTable.setRowGrand(false);

	    //Draging the first field to the row area.
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 6);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 7);
	    */
	}
	
	private String getSearchQuery2(HttpServletRequest request, String pages, String soDong)
	{
		Utility util =  new Utility();
		String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null)  	
	    	id = "";
	    
	    String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
    	if (dvkdId == null)
    		dvkdId = "";
	
    	String query = "select b.ma as spMa, b.ten as spTen, a.giabanlechuan as gblc from banggiablc_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where bgblc_fk = '" + id + "'";
    	
    	System.out.print("\nQuery la: " + query + "\n");
    	return query;
    	
	}
	
}