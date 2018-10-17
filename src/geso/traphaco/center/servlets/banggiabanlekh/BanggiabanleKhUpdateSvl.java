package geso.traphaco.center.servlets.banggiabanlekh;

import geso.traphaco.center.beans.banggiablkh.IBanggiablKh;
import geso.traphaco.center.beans.banggiablkh.IBanggiablKhList;
import geso.traphaco.center.beans.banggiablkh.IGiaKh;
import geso.traphaco.center.beans.banggiablkh.imp.BanggiablKh;
import geso.traphaco.center.beans.banggiablkh.imp.BanggiablKhList;
import geso.traphaco.center.beans.banggiablkh.imp.GiaKh;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


 public class BanggiabanleKhUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
  

   public BanggiabanleKhUpdateSvl() {
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
	    
	    IBanggiablKh bgBean = new BanggiablKh();
	    
        bgBean.setUserId(userId);
        bgBean.setSpId(id);
        bgBean.init();
        
        session.setAttribute("bgblcBean", bgBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeKhUpdate.jsp";
        response.sendRedirect(nextJSP);

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		OutputStream out = response.getOutputStream();
		
		IBanggiablKh bgBean = new BanggiablKh();
	    Utility util = new Utility();
	    
		String id =  util.antiSQLInspection(request.getParameter("id"));	    
	    if(id == null){  	
	    	id = "";
	    }
	    bgBean.setSpId(id);
	    
	    String spma =  util.antiSQLInspection(request.getParameter("spMa"));	    
	    if(spma == null){  	
	    	spma = "";
	    }
	    bgBean.setSpMa(spma);
	    
	    String spTen =  util.antiSQLInspection(request.getParameter("spTen"));	    
	    if(spTen == null){  	
	    	spTen = "";
	    }
	    bgBean.setSpTen(spTen);
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		bgBean.setUserId(userId);
	    
		List<IGiaKh> khList = new ArrayList<IGiaKh>();
		
    	String[] khid = request.getParameterValues("khid");
    	String[] khma = request.getParameterValues("khma");
    	String[] khten = request.getParameterValues("khten");
    	String[] dongia= request.getParameterValues("dongia");
  
    			
		boolean error = false;
		if(id.length() == 0)
			error = true;
				
		if(khid != null){
			for(int i=0; i< khid.length; i++){
				if(khid[i].trim().length() > 0){
					IGiaKh kh = new GiaKh();
					kh.setKhId(khid[i].trim());
					kh.setKhTen(khten[i]);
					kh.setKhMa(khma[i].trim());
					if(dongia[i].replace(",", "").trim().length() == 0 || Float.parseFloat(dongia[i].replace(",", "").trim()) == 0){
						dongia[i] = "0";
						error = true;
					}
					kh.setGiaban(dongia[i].replace(",", ""));
					khList.add(kh);
				}
			}
		}
		bgBean.setKhlist(khList);

		String action = request.getParameter("action");
		String nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeKhUpdate.jsp";
		
		if(action.equals("save"))
		{
			
			if (error || !bgBean.UpdateBg()){								
				bgBean.setUserId(userId);
				if(error)
					bgBean.setMessage("Vui lòng nhập giá cho khách hàng");
				if(id.length() == 0){
					nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeKhNew.jsp";
					bgBean.setMessage("Vui lòng chọn sản phẩm");
				}
				session.setAttribute("bgblcBean", bgBean);
				response.sendRedirect(nextJSP);
			}
			else{
				IBanggiablKhList obj = new BanggiablKhList();
				obj.setUserId(userId);
				session.setAttribute("obj", obj);
				obj.init("");
				nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeKh.jsp";
				response.sendRedirect(nextJSP);			    			    									
			}
			
		}
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
	    ///cell.setValue("Ngày tạo: " + this.getDateTime());
	    
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