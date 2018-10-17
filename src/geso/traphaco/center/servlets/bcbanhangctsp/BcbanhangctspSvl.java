package geso.traphaco.center.servlets.bcbanhangctsp;

import geso.traphaco.center.beans.bcbanhangctsp.IBcbanhangctsp;
import geso.traphaco.center.beans.bcbanhangctsp.IBcbanhangctspList;
import geso.traphaco.center.beans.bcbanhangctsp.imp.Bcbanhangctsp;
import geso.traphaco.center.beans.bcbanhangctsp.imp.BcbanhangctspList;
import geso.traphaco.center.beans.bchoadonbanhang.imp.BchoadonbanhangList;
import geso.traphaco.center.beans.thongtinsanpham.IThongtinsanphamList;
import geso.traphaco.center.beans.thongtinsanpham.imp.ThongtinsanphamList;
import geso.traphaco.center.beans.upload.IUpload;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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

import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.text.DecimalFormat;
import java.text.NumberFormat;



public class BcbanhangctspSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	private int items = 50;
	private int splittings = 10;
	public BcbanhangctspSvl()
	{
		super();
	}
	 private void settingPage(IBcbanhangctspList obj) {
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
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out  = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		IBcbanhangctspList obj =  (IBcbanhangctspList) new BcbanhangctspList();
		obj.setUserId(userId);

		String action = util.getAction(querystring);
		String khlId = util.getId(querystring);

		String msg = "";

		obj.init("");
		obj.setMsg(msg);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Center/Bcbanhangctsp.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	

		HttpSession session = request.getSession();

		OutputStream out = response.getOutputStream();
		
		
		Utility util = new Utility();

		String userId = util.antiSQLInspection(request.getParameter("userId"));

		BcbanhangctspList obj = new BcbanhangctspList() ;

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		
		
	
	

		if (action.equals("excel"))
	    {
	    	obj = new BcbanhangctspList();
	    	
	    	obj.setQuery(getSearchQuery(request, obj));
	    	
	    	try
		    {
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=BCBanhangCTSP_"+getDateTime()+".xls");
		        
		        Workbook workbook = new Workbook();
		    	
		        CreateStaticHeader(workbook, "Nguyen Duy Hai");
			    CreateStaticData(workbook, getQueryExcel(request, obj));
			
			     //Saving the Excel file
			     workbook.save(out);
		    }
		    catch (Exception ex)
		    {
		        obj.setMsg("Khong the tao pivot.");
		    }
	    	
			obj.setUserId(userId);
			
			
	    	session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/Bcbanhangctsp.jsp");	    		
	    }
		else if(action.equals("view") || action.equals("next") || action.equals("prev")){
			
			
			obj = new BcbanhangctspList();
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);

	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Center/Bcbanhangctsp.jsp");
	    }
	
		else
		{
			obj = new BcbanhangctspList();
			obj.setUserId(userId);

			String search = getSearchQuery(request, obj);

		
			obj.init(search);

			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);

			response.sendRedirect("/TraphacoHYERP/pages/Center/Bcbanhangctsp.jsp");
		}
	}
	private String getQueryExcel(HttpServletRequest request, IBcbanhangctspList obj){
		
		Utility util = new Utility();

		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String makh = util.antiSQLInspection(request.getParameter("makh"));
		obj.setmaKH(makh);
		
		String mafast =util.antiSQLInspection(request.getParameter("mafast"));
		obj.setmaFast(mafast);
		
		String ddkd =util.antiSQLInspection(request.getParameter("ddkd"));
		obj.setddkd(ddkd);
		
		String kho =util.antiSQLInspection(request.getParameter("khohang"));
		obj.setKhohang(kho);
		
		String query = "";

		if(tungay.length()==0 && denngay.length()==0 && mafast.length()== 0 && ddkd.length()==0  && kho.length()==0 && makh.length()==0){
			
			query=" select sp.MA,sp.TEN as tensanpham, dvdl.DIENGIAI  , donhang.SLban, (donhang.SLban*bg.GIABANLECHUAN) as doanhthu, "  +
					" (donhang.SLban*bg.GIABANLECHUAN*donhang.THUEVAT)/100 as VAT, (donhang.SLban*bg.GIABANLECHUAN*(1+donhang.THUEVAT/100)) as Tongtien " +
					" from " +
					" (select dhsp.SANPHAM_FK, dhsp.THUEVAT ,SUM(dhsp.SOLUONG) as SLban " +
					" from DONHANG dh " +
					" inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ=dhsp.DONHANG_FK " +
					" where dh.TRANGTHAI=1  " +
					" group by dhsp.SANPHAM_FK, dhsp.THUEVAT) donhang " + 
					" inner join BANGGIABLC_SANPHAM bg on donhang.SANPHAM_FK= bg.SANPHAM_FK " +
					" inner join SANPHAM sp on donhang.SANPHAM_FK = sp.PK_SEQ " +
					" inner join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ " ;
		}
		else{
		
		query =" select sp.MA,sp.TEN as tensanpham, dvdl.DIENGIAI,  donhang.SLban, (donhang.SLban*bg.GIABANLECHUAN) as doanhthu, " +
				  " (donhang.SLban*bg.GIABANLECHUAN*donhang.THUEVAT)/100 as VAT, (donhang.SLban*bg.GIABANLECHUAN*(1+donhang.THUEVAT/100)) as Tongtien "+
				  "	 from " +
					" (select dh.KHACHHANG_FK, dh.DDKD_FK,dhsp.SANPHAM_FK, dhsp.THUEVAT,  dh.NGAYNHAP,  dh.KHO_FK ,SUM(dhsp.SOLUONG) as SLban " +
					" from DONHANG dh " +
					" inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ=dhsp.DONHANG_FK " +
					" where dh.TRANGTHAI=1  " +
					" group by dh.KHACHHANG_FK, dh.DDKD_FK ,dhsp.SANPHAM_FK, dhsp.THUEVAT, dh.KHO_FK,  dh.NGAYNHAP  ) donhang " +
					" inner join BANGGIABLC_SANPHAM bg on donhang.SANPHAM_FK= bg.SANPHAM_FK " +
					" inner join SANPHAM sp on donhang.SANPHAM_FK = sp.PK_SEQ " +
					" inner join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ " +
					" inner join KHACHHANG kh on kh.PK_SEQ = donhang.KHACHHANG_FK " +
					" inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = donhang.DDKD_FK " +
					" where 1=1";
		
		if(tungay.length() > 0)
			query += " and donhang.NGAYNHAP >= '" + tungay + "' ";

		if(denngay.length() > 0)
			query += " and donhang.NGAYNHAP <= '" + denngay + "' ";
		
		if(mafast.length() > 0)
			query += " and kh.maFAST like '%"+mafast+"%' ";
		
		if(ddkd.length() > 0)
			query += " and ddkd.PK_SEQ='"+ddkd+"' ";
		if(kho.length() > 0)
			query += " and donhang.KHO_FK='"+kho+"' ";
		
		if(makh.length() > 0)
			query += " and kh.PK_SEQ='"+makh+"' ";

		}
		
		System.out.println("[query excel]: "+ query);
		return query;
	}

	private String getSearchQuery(HttpServletRequest request, BcbanhangctspList obj)
	{
		Utility util = new Utility();

		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String makh = util.antiSQLInspection(request.getParameter("makh"));
		obj.setmaKH(makh);
		
		String mafast =util.antiSQLInspection(request.getParameter("mafast"));
		obj.setmaFast(mafast);
		
		String ddkd =util.antiSQLInspection(request.getParameter("ddkd"));
		obj.setddkd(ddkd);
		
		String kho =util.antiSQLInspection(request.getParameter("khohang"));
		obj.setKhohang(kho);
	
		String sql = "";

		if(tungay.length()==0 && denngay.length()==0 && mafast.length()== 0 && ddkd.length()==0  && kho.length()==0 && makh.length()==0){
			
			sql=" select sp.MA,sp.TEN as tensanpham, dvdl.DIENGIAI  , donhang.SLban, (donhang.SLban*bg.GIABANLECHUAN) as doanhthu, "  +
					" (donhang.SLban*bg.GIABANLECHUAN*donhang.THUEVAT)/100 as VAT, (donhang.SLban*bg.GIABANLECHUAN*(1+donhang.THUEVAT/100)) as Tongtien " +
					" from " +
					" (select dhsp.SANPHAM_FK, dhsp.THUEVAT ,SUM(dhsp.SOLUONG) as SLban " +
					" from DONHANG dh " +
					" inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ=dhsp.DONHANG_FK " +
					" where dh.TRANGTHAI=1  " +
					" group by dhsp.SANPHAM_FK, dhsp.THUEVAT) donhang " + 
					" inner join BANGGIABLC_SANPHAM bg on donhang.SANPHAM_FK= bg.SANPHAM_FK " +
					" inner join SANPHAM sp on donhang.SANPHAM_FK = sp.PK_SEQ " +
					" inner join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ " ;
		}
		else{

		  sql = " select sp.MA,sp.TEN as tensanpham, dvdl.DIENGIAI,  donhang.SLban, (donhang.SLban*bg.GIABANLECHUAN) as doanhthu, " +
				  " (donhang.SLban*bg.GIABANLECHUAN*donhang.THUEVAT)/100 as VAT, (donhang.SLban*bg.GIABANLECHUAN*(1+donhang.THUEVAT/100)) as Tongtien "+
				  "	 from " +
					" (select dh.KHACHHANG_FK, dh.DDKD_FK,dhsp.SANPHAM_FK, dhsp.THUEVAT,  dh.NGAYNHAP,  dh.KHO_FK ,SUM(dhsp.SOLUONG) as SLban " +
					" from DONHANG dh " +
					" inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ=dhsp.DONHANG_FK " +
					" where dh.TRANGTHAI=1  " +
					" group by dh.KHACHHANG_FK, dh.DDKD_FK ,dhsp.SANPHAM_FK, dhsp.THUEVAT, dh.KHO_FK,  dh.NGAYNHAP  ) donhang " +
					" inner join BANGGIABLC_SANPHAM bg on donhang.SANPHAM_FK= bg.SANPHAM_FK " +
					" inner join SANPHAM sp on donhang.SANPHAM_FK = sp.PK_SEQ " +
					" inner join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ " +
					" inner join KHACHHANG kh on kh.PK_SEQ = donhang.KHACHHANG_FK " +
					" inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = donhang.DDKD_FK " +
					" where 1=1";

		if(tungay.length() > 0)
			sql += " and donhang.NGAYNHAP >= '" + tungay + "' ";

		if(denngay.length() > 0)
			sql += " and donhang.NGAYNHAP <= '" + denngay + "' ";
		
		if(mafast.length() > 0)
			sql += " and kh.maFAST like '%"+mafast+"%' ";
		
		if(ddkd.length() > 0)
			sql += " and ddkd.PK_SEQ='"+ddkd+"' ";
		if(kho.length() > 0)
			sql += " and donhang.KHO_FK='"+kho+"' ";

		}
		return sql;
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
	    cell = cells.getCell("A8");cell.setValue("Mã vật tư");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);    
	    cell = cells.getCell("B8");cell.setValue(" Tên vật tư");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("C8");cell.setValue(" ĐVT");  					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("D8");cell.setValue("Số lượng"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("E8");cell.setValue("Doanh thu");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("F8");cell.setValue(" Thuế giá trị");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("G8");cell.setValue(" Tổng tiền"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	   
	    worksheet.setName("BC Bán hàng chi tiết theo sản phẩm");
	}

	private void CreateStaticData(Workbook workbook, String query) 
	{
		
		System.out.println("đã vào đây !");
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		//System.out.println("Get san pham :"+query);
		NumberFormat formatter = new DecimalFormat("#,###,###");
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
					
					String ma = rs.getString("ma");
					String ten = rs.getString("tensanpham");
					String dvt = rs.getString("DIENGIAI");
					String soluong = rs.getString("SLban");
					String doanhthu = formatter.format(rs.getDouble("doanhthu"));
					String vat = formatter.format(rs.getDouble("VAT"));
					String tongtien = formatter.format(rs.getDouble("Tongtien"));
					

				
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(ma); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(ten); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(dvt); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(soluong); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(doanhthu); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(vat); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(tongtien); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
				
					i++;
				}
				rs.close();
			}
			catch (SQLException e){ e.printStackTrace(); }
		}
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
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}
