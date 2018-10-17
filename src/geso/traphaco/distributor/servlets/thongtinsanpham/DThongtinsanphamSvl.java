package geso.traphaco.distributor.servlets.thongtinsanpham;

import geso.traphaco.distributor.beans.thongtinsanpham.IThongtinsanphamList;
import geso.traphaco.distributor.beans.thongtinsanpham.imp.ThongtinsanphamList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class DThongtinsanphamSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private int items;
	private int splittings;
	
    
	public DThongtinsanphamSvl() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		IThongtinsanphamList obj;       
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
   

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	        
	    String action = util.getAction(querystring);
	    out.println(action);
	      	
	    obj = new ThongtinsanphamList();
	    
	    obj.setUserId(userId);
	    settingPage(obj);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ThongTinSanPham.jsp";
		response.sendRedirect(nextJSP);	
		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		IThongtinsanphamList obj = new ThongtinsanphamList(); 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    OutputStream out = response.getOutputStream();
	    

	    userId = request.getParameter("userId");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    settingPage(obj);
	            	
   		if (action.equals("search"))
	    {	    
   			//obj = new ThongtinsanphamList();			    
   			obj.setUserId(userId);
   			obj.init(getSearchQuery(request, obj));
   			
   	    	session.setAttribute("obj", obj);

   	   		session.setAttribute("userId", userId);
   		    		
   	   		response.sendRedirect("/TraphacoHYERP/pages/Distributor/ThongTinSanPham.jsp");	  		    	
	    }
   		else if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);

	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/ThongTinSanPham.jsp");
	    }
	    
   		if (action.equals("excel"))
   		{
	    	obj = new ThongtinsanphamList();
	    	try
		    {
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=DanhSachSanPham.xls");
		        
		        Workbook workbook = new Workbook();
		    	
			    CreateStaticHeader(workbook, "Nha Phan Phoi");
			    CreateStaticData(workbook, getQueryExcel(request, obj));
			
			    //Saving the Excel file
			    workbook.save(out);
		    }
		    catch (Exception ex){}
	    	
			obj.setUserId(userId);
			obj.init(getSearchQuery(request, obj));
			
	    	session.setAttribute("obj", obj);

	    	session.setAttribute("userId", userId);
	    		
	    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/ThongTinSanPham.jsp");	    		
	    }
   		
   		
   		

		}
    	
	}

	private void settingPage(IThongtinsanphamList obj) {
		// TODO Auto-generated method stub
		
		Utility util = new Utility();
		if(getServletContext().getInitParameter("items") != null){
	    	String i = getServletContext().getInitParameter("items").trim();
	    	if(util.isNumeric(i))
	    		items = Integer.parseInt(i);
	    }
	    
	    if(getServletContext().getInitParameter("splittings") != null){
	    	String i = getServletContext().getInitParameter("splittings").trim();
	    	if(util.isNumeric(i))
	    		splittings = Integer.parseInt(i);
	    }
	    
    	obj.setItems(items);
    	obj.setSplittings(splittings);
		
	}


	private String getSearchQuery(HttpServletRequest request, IThongtinsanphamList obj)
	{
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if (nppId == null)
    		nppId = "";
    	
		String masp = util.antiSQLInspection(request.getParameter("masp"));
    	if (masp == null)
    		masp = "";
    	obj.setMasp(masp);

		String tensp = util.antiSQLInspection(request.getParameter("tensp"));
    	if (tensp == null)
    		tensp = "";
    	obj.setTensp(tensp);
    	
    	String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
    	if (dvkdId == null)
    		dvkdId = "";    	
    	obj.setDvkdId(dvkdId);
    	
    	String nhId = util.antiSQLInspection(request.getParameter("nhId"));
    	if (nhId == null)
    		nhId = "";    	
    	obj.setNhId(nhId);
    	
    	String clId = util.antiSQLInspection(request.getParameter("clId"));
    	if (clId == null)
    		clId = "";    	
    	obj.setClId(clId);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);
    	
    
    	
     String	query ="select ROW_NUMBER() OVER(ORDER BY a.ma DESC) AS 'stt', a.pk_seq,a.ma,a.ten,b.donvikinhdoanh  as dvkd,b.pk_seq as dvkdId,c.ten as chungloai, e.pk_seq as nhId,d.donvi,e.ten as nhanhang,d.pk_seq as clId,a.trangthai,f.giabanlechuan as giablc, h.TEN as nganhhang  ";
		query = query + " from sanpham a left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq left join chungloai c on a.chungloai_fk = c.pk_seq left join donvidoluong d on a.dvdl_fk = d.pk_seq left join nhanhang e on a.nhanhang_fk = e.pk_seq ";
		query = query  + " left join banggiablc_sanpham f on a.pk_seq = f.sanpham_fk left join banggiabanlechuan g on f.bgblc_fk = g.pk_seq left join NGANHHANG h on a.NGANHHANG_FK = h.PK_SEQ  " +
				" where 1=1    ";
		if (masp.length()>0){
			query = query + " and upper(a.ma) like upper('%" + masp + "%')";
			
    	}
    
	    if (tensp.length()>0){
			query = query + "and upper(dbo.ftBoDau(a.ten)) like upper('%" + util.replaceAEIOU(tensp) + "%')";
					/*"" and upper(a.ten) like upper(N'%" + util.replaceAEIOU(tensp) + "%')";
*/
    	}
    	
    	if (dvkdId.length()>0){
			query = query + " and b.pk_seq = '" + dvkdId + "'";
    	}

    	if (nhId.length()>0){
			query = query + " and e.pk_seq = '" + nhId + "'";
    	}
    	
    	if (clId.length()>0){
			query = query + " and c.pk_seq = '" + clId + "'";
    		
    	}

    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		
    	}
		

    	
    	return query;
	}	
	
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    Style style;
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("DANH SÁCH SẢN PHẨM");	    
	    style = cell.getStyle();
		Font font2 = new Font();	
		font2.setName("Calibri");
		font2.setColor(Color.NAVY);
		font2.setSize(18);
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setName("Calibri");
		font2.setBold(true);
		font2.setSize(11);
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Ngày tạo : " + this.getDateTime());
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
	    
	    //tieu de
	    cell = cells.getCell("A8");cell.setValue("Đơn vị kinh doanh");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);    
	    cell = cells.getCell("B8");cell.setValue("Ngành hàng");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("C8");cell.setValue("Packsize");  					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("D8");cell.setValue("Nhãn hàng"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("E8");cell.setValue("Chủng loại");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("F8");cell.setValue("Mã sản phẩm");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("G8");cell.setValue("Mã chuẩn"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("H8");cell.setValue("Tên sản phẩm");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("I8");cell.setValue("Tên chuẩn");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("J8");cell.setValue("Đơn vị đo lường"); 			style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("K8");cell.setValue("Giá bán lẻ chuẩn"); 	 		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	  //Them trong luong +The tich
	    cell = cells.getCell("L8");cell.setValue("Trọng lượng");	      		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("M8");cell.setValue("Thể tích"); 	 				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("N8");cell.setValue("Quy cách Thùng");  			style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("O8");cell.setValue("Quy cách Gói vận chuyển");	style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("P8");cell.setValue("Tình trạng");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    worksheet.setName("DANH SÁCH SẢN PHẨM");
	}

	private void CreateStaticData(Workbook workbook, String query) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		//System.out.println("Get san pham :"+query);
		NumberFormat formatter = new DecimalFormat("#,###,##0.0"); 
		NumberFormat formatTheTich = new DecimalFormat("#,###,##0.00000"); 
		NumberFormat formatKhoiLuong = new DecimalFormat("#,###,##0.000"); 
		int i = 9;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 35.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 20.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 25.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 45.0f);
				cells.setColumnWidth(8, 40.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);
				cells.setColumnWidth(11, 15.0f);
				cells.setColumnWidth(12, 15.0f);
				cells.setColumnWidth(13, 15.0f);
				cells.setColumnWidth(14, 25.0f);
				cells.setColumnWidth(15, 15.0f);
				for(int j = 0; j < 11; j++)
				{
					if(j==0)
						cells.setRowHeight(j, 23.0f);
					else
						cells.setRowHeight(j, 13.0f);
				}
				
				Cell cell = null;
				
				Style style;
				Font font2 = new Font();
				font2.setName("Calibri");				
				font2.setSize(11);
				
				while(rs.next())
				{
					
					String nganhang = "";
					if(rs.getString("nganhhang")!= null)
						nganhang = rs.getString("nganhhang");
					
					String packsize = "";
					if(rs.getString("packsize")!= null)
						packsize = rs.getString("packsize");
					
					String dvkd = "";
					if(rs.getString("dvkd")!= null)
						dvkd = rs.getString("dvkd");
					
					String nhanhang = "";
					if(rs.getString("nhanhang") != null)
						nhanhang = rs.getString("nhanhang");
					
					String chungloai = "";
					if(rs.getString("chungloai") != null)
						chungloai = rs.getString("chungloai");
						
					String masp = "";
					if(rs.getString("ma") != null)
						masp = rs.getString("ma");
					
					String machuan = "";
					if(rs.getString("machuan") != null)
						machuan = rs.getString("machuan");
					
					
					String tensp = "";
					if(rs.getString("ten") != null)
						tensp = rs.getString("ten");
					
					String tenchuan = "";
					if(rs.getString("tenchuan") != null)
						tenchuan = rs.getString("tenchuan");
					
					
					String dvdl = "";
					if(rs.getString("donvi") != null)
						dvdl = rs.getString("donvi");
					
					String gblc = "";
					if(rs.getString("giablc") != null)
						gblc = formatter.format(rs.getDouble("giablc"));
					
					String quycach = "";
					if(rs.getString("quycach") != null)
						quycach = rs.getString("quycach");
					
					String goivanchuyen = "";
					if(rs.getString("goivanchuyen") != null)
						goivanchuyen = rs.getString("goivanchuyen");
					
					String trangthai="Hoạt động";
					if(rs.getString("trangthai").equals("0")){
						trangthai="Không hoạt động";
					}
					
				

					String trongluong="";
					if(rs.getString("trongluong")!=null)
						trongluong=formatKhoiLuong.format((rs.getDouble("trongluong")));
						
					String thetich="";
					if(rs.getString("thetich")!=null)
						thetich=formatTheTich.format((rs.getDouble("thetich")));
				
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(dvkd); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(nganhang); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(packsize); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(nhanhang); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(chungloai); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(masp); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(machuan); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(tensp); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(tenchuan); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(dvdl); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(gblc); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					//Trong luong
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(trongluong); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					//The tich
					cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(thetich); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("N" + Integer.toString(i));	cell.setValue(quycach); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("O" + Integer.toString(i));	cell.setValue(goivanchuyen);	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("P" + Integer.toString(i));	cell.setValue(trangthai); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					i++;
				}
				rs.close();
			}
			catch (SQLException e){ e.printStackTrace(); }
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getQueryExcel(HttpServletRequest request, IThongtinsanphamList obj)
	{
		String masp = request.getParameter("masp");
    	if (masp == null)
    		masp = "";
    	obj.setMasp(masp);

		String tensp = request.getParameter("tensp");
    	if (tensp == null)
    		tensp = "";
    	obj.setTensp(tensp);
    	
    	String dvkdId = request.getParameter("dvkdId");
    	if (dvkdId == null)
    		dvkdId = "";    	
    	obj.setDvkdId(dvkdId);
    	
    	String nhId = request.getParameter("nhId");
    	if (nhId == null)
    		nhId = "";    	
    	obj.setNhId(nhId);
    	
    	String clId = request.getParameter("clId");
    	if (clId == null)
    		clId = "";    	
    	obj.setClId(clId);
    	
    	String trangthai = request.getParameter("trangthai");   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);
    	
    	String   query =
    			"select  b.pk_seq as dvkdId,b.donvikinhdoanh as dvkd,e.ten as nhanhang, d.pk_seq as clId,\n  "+ 
    			"	n.TEN as NganhHang,p.TEN as PackSize,qc.SOLUONG1 as QuyCach,qvc.SOLUONG2 as GoiVanChuyen\n  "+ 
    			"	,a.pk_seq, a.ma,a.MACHUAN ,a.ten, a.tenchuan , a.trongluong,\n  "+ 
    			"	a.thetich, c.ten as chungloai, e.pk_seq as nhId, d.donvi, a.trangthai, isnull(f.giabanlechuan,'0') as giablc  \n  "+ 
    			"	from sanpham a \n  "+ 
    			"left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq \n  "+ 
    			"left join chungloai c on a.chungloai_fk = c.pk_seq \n  "+ 
    			"left join donvidoluong d on a.dvdl_fk = d.pk_seq\n  "+ 
    			"left join nhanhang e on a.nhanhang_fk = e.pk_seq  \n  "+ 
    			"left join banggiablc_sanpham f on a.pk_seq = f.sanpham_fk\n  "+ 
    			"left join banggiabanlechuan g on f.bgblc_fk = g.pk_seq \n  "+ 
    			"left join NGANHHANG n on n.PK_SEQ=a.NGANHHANG_FK\n  "+ 
    			"left join Packsize p  on p.PK_SEQ=a.PACKSIZE_FK\n  "+ 
    			"left join QUYCACH qc on qc.SANPHAM_FK=a.PK_SEQ and qc.DVDL2_FK=100018 \n  "+ 
    			"left join QUYCACH qvc on qvc.SANPHAM_FK=a.PK_SEQ and qvc.DVDL2_FK=100052 " +
    			"where 1=1 ";
    	if (masp.length()>0){
			query = query + " and upper(a.ma) like upper('%" + masp + "%')";	
    	}

	    if (tensp.length()>0){
	    	   geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%"+ util.replaceAEIOU(tensp.trim()).trim()+"%')";	
    	}
    	
    	if (dvkdId.length()>0){
			query = query + " and b.pk_seq = '" + dvkdId + "'";	
    	}

    	if (nhId.length()>0){
			query = query + " and e.pk_seq = '" + nhId + "'";   		
    	}
    	
    	if (clId.length()>0){
			query = query + " and c.pk_seq = '" + clId + "'";    		
    	}

    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";   		
    	}
    	System.out.println("Excel "+query);
    	return query;
    	
	}
	
	private void setCellBorderStyle(Cell cell) {
		Style style = cell.getStyle();
		style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
}
