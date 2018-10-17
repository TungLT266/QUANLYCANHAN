package geso.traphaco.erp.servlets.banggiaban;

import geso.traphaco.erp.beans.banggiaban.IBanggiaban;
import geso.traphaco.erp.beans.banggiaban.IBanggiabanList;
import geso.traphaco.erp.beans.banggiaban.imp.Banggiaban;
import geso.traphaco.erp.beans.banggiaban.imp.BanggiabanList;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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

 public class ErpBanggiabanUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
	public ErpBanggiabanUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		PrintWriter out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String action = util.getAction(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    out.println(id);
	    
	    IBanggiaban bgBean = new Banggiaban();
	    String ctyId = (String)session.getAttribute("congtyId");
	    String ctyTen = (String)session.getAttribute("congtyTen");
	    bgBean.setCtyId(ctyId);
	    bgBean.setCtyTen(ctyTen);
	    
        bgBean.setUserId(userId);
        bgBean.setId(id);
        bgBean.init();
        
        session.setAttribute("bgbanBean", bgBean);
        String nextJSP;
        
        if(action.equals("update")){
        	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBanUpdate.jsp";
        }else{
        	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBanDisplay.jsp";
        }
        response.sendRedirect(nextJSP);

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		OutputStream out = response.getOutputStream();

		IBanggiaban bgBean = (IBanggiaban) new Banggiaban();
		
	    String ctyId = (String)session.getAttribute("congtyId");
	    String ctyTen = (String)session.getAttribute("congtyTen");
	    
	    bgBean.setCtyId(ctyId);
	    bgBean.setCtyTen(ctyTen);
	    
		Utility util = new Utility();
	    
		String id =  util.antiSQLInspection(request.getParameter("id"));
	    
	    if(id == null){  	
	    	id = "";
	    }
	    bgBean.setId(id);
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		bgBean.setUserId(userId);
	    System.out.println("userId :"+userId);
    	String bgTen = util.antiSQLInspection(request.getParameter("bgTen"));
		if (bgTen == null)
			bgTen = "";				
    	bgBean.setTen(bgTen);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if(dvkdId == null)
			dvkdId = "";	
		bgBean.setDvkdId(dvkdId);
	    
		String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
		if(kenhId == null)
			kenhId = "";

		bgBean.setKenhId(kenhId);

		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	bgBean.setTrangthai(trangthai);
		   	
		String ngaysua = getDateTime();
    	bgBean.setNgaysua(ngaysua);
    	bgBean.setNgaytao(ngaysua);
    	
		bgBean.setNguoitao(userId);
		bgBean.setNguoisua(userId);
    			
		boolean error = false;

		if (kenhId.trim().length()== 0){
			bgBean.setMessage("Vui long lua chon Kenh Ban Hang");
			error = true;
		}

		if (dvkdId.trim().length()== 0){
			bgBean.setMessage("Vui long lua chon Đơn Vị Kinh Doanh");
			error = true;
		}
		
		if (bgTen.trim().length()== 0){
			bgBean.setMessage("Vui Long Nhap Ten Bang Gia");
			error = true;
		}

		String action = request.getParameter("action");
	    
		if (action.equals("excel"))
	    {
	    	try
		    {
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=BangGiaBan.xls");
		        
		        Workbook workbook = new Workbook();
		    	
			     CreateStaticHeader(workbook, "Nguyen Duy Hai");
			     CreateStaticData(workbook, getSearchQuery2(request, "", ""));
			
			     //Saving the Excel file
			     workbook.save(out);
		    }
		    catch (Exception ex){ }
	    	
		    bgBean.setUserId(userId);
			bgBean.setId(id);
			bgBean.init();
			session.setAttribute("bgbanBean", bgBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBanUpdate.jsp";
			response.sendRedirect(nextJSP);		    		
	    }
		
		
		if(action.equals("save") & !error)
		{
			if (id.length()==0){
				if (!(bgBean.CreateBgban(request)))
				{			
					bgBean.setCtyId(ctyId);
					bgBean.setCtyTen(ctyTen);
					
					bgBean.setUserId(userId);
					bgBean.createRS();
					session.setAttribute("bgbanBean", bgBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBanNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					
					IBanggiabanList obj = new BanggiabanList();
			    	obj.setCtyId(ctyId);
			    	obj.setCtyTen(ctyTen);
					
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
					bgBean.closeDB();
					String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBan.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(bgBean.UpdateBgban(request)))
				{								
					bgBean.setCtyId(ctyId);
					bgBean.setCtyTen(ctyTen);

					bgBean.setUserId(userId);
					bgBean.setId(id);
					bgBean.init();
					session.setAttribute("bgbanBean", bgBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBanUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IBanggiabanList obj = new BanggiabanList();
					//bgBean.setUserId(userId);
			    	obj.setCtyId(ctyId);
			    	obj.setCtyTen(ctyTen);
			    	bgBean.closeDB();
					obj.setUserId(userId);
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBan.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}else
			if(action.equals("display")){
				if(bgBean.ChangeBgban(request)){
			    	IBanggiabanList obj = new BanggiabanList();
			    	obj.setCtyId(ctyId);
			    	obj.setCtyTen(ctyTen);
			    	obj.init("");
			    	
			    	obj.setUserId(userId);
			    	session.setAttribute("obj", obj);
						
			    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBan.jsp";
			    	response.sendRedirect(nextJSP);					
				}else{
					bgBean.setCtyId(ctyId);
					bgBean.setCtyTen(ctyTen);
					
			        session.setAttribute("bgbanBean", bgBean);
			        String nextJSP;
			        
		        	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBanDisplay.jsp";

			        response.sendRedirect(nextJSP);
					
				}
			}else
			{	
			
			String nextJSP;
			if (id.length()==0){			
				bgBean.setCtyId(ctyId);
		    	bgBean.setCtyTen(ctyTen);

				bgBean.setUserId(userId);
				bgBean.createRS();
				session.setAttribute("bgbanBean", bgBean);
				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBanNew.jsp";
			}else{
				bgBean.setCtyId(ctyId);
		    	bgBean.setCtyTen(ctyTen);

				bgBean.setUserId(userId);
				bgBean.setId(id);
				bgBean.init();
				session.setAttribute("bgbanBean", bgBean);

				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangGiaBanUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
			
		}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("Bảng giá bán");
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Date Create: " + this.getDateTime());
	    //cell = cells.getCell("A4");
	    //cell.setValue("User:  " + UserName);
	    
	    //tieu de
	    cell = cells.getCell("A8");
	    cell.setValue("Đơn vị kinh doanh");
	    cell = cells.getCell("B8");
	    cell.setValue("Kênh bán hàng");
	    cell = cells.getCell("C8");
	    cell.setValue("Mã sản phẩm");
	    cell = cells.getCell("D8");
	    cell.setValue("Tên sản phẩm");
	    cell = cells.getCell("E8");
	    cell.setValue("Giá bán");
	    cell = cells.getCell("F8");
	    cell.setValue("Đơn vị");
	    
	    worksheet.setName("Bang gia ban");
	}
	
	private void CreateStaticData(Workbook workbook, String query) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		
		int i = 9;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 25.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 20.0f);
				cells.setColumnWidth(3, 40.0f);
				cells.setColumnWidth(4, 25.0f);
				cells.setColumnWidth(5, 15.0f);
				
				for(int j = 0; j < 6; j++)
					cells.setRowHeight(j, 13.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					String dvkd = "";
					if(rs.getString("dvkd")!= null)
						dvkd = rs.getString("dvkd");
					
					String kbh = "";
					if(rs.getString("kenhbanhang") != null)
						kbh = rs.getString("kenhbanhang");
						
					String masp = "";
					if(rs.getString("ma") != null)
						masp = rs.getString("ma");
					
					String tensp = "";
					if(rs.getString("ten") != null)
						tensp = rs.getString("ten");
					
					String dvdl = "";
					if(rs.getString("donvi") != null)
						dvdl = rs.getString("donvi");
					
					String gblc = "";
					if(rs.getString("giablc") != null)
						gblc = rs.getString("giablc");
					
					
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(dvkd);
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(kbh);
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(masp);
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(tensp);
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(gblc);
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(dvdl);
					
					i++;
				}
				rs.close();
			}
			catch (SQLException e){ e.printStackTrace(); }
			db.shutDown();
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
		Utility util = new Utility();
		String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null)  	
	    	id = "";
	    
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
    	if (dvkdId == null)
    		dvkdId = "";
    	
		String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
		if(kenhId == null)
			kenhId = "";
				
    	String query = 	"select d.donvikinhdoanh as dvkd, c.ten as nhanhang, c.ma, c.ten, f.donvi, " +
    					"b.GIABAN as giablc, g.ten as kenhbanhang " +
    					"from ERP_BANGGIABAN a " +
    					"inner join ERP_BGBAN_SANPHAM b on a.pk_seq = b.BGBAN_FK " +
    					"inner join sanpham c on b.sanpham_fk = c.pk_seq";
    	query += " left join donvikinhdoanh d on c.dvkd_fk = d.pk_seq " +
    			 "inner join nhanhang e on c.nhanhang_fk = e.pk_seq " +
    			 "inner join donvidoluong f on c.dvdl_fk = f.pk_seq inner join kenhbanhang g on a.kenh_fk = g.pk_seq " +
    			 "where b.GIABAN >0  and a.pk_seq = '" + id + "' ";
		     
    	System.out.print("\nQuery cua ban: " + query + "\n");
    	
    	if(dvkdId.length() > 0)
    		query += "and d.pk_seq = '" + dvkdId + "' ";
 
    	if(kenhId.length() > 0)
    		query += "and a.kenh_fk = '" + kenhId + "'";
    	
    	return query;
    	
	}
	
}