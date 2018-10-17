package geso.traphaco.distributor.servlets.bchoadonbanhang;

import geso.traphaco.distributor.beans.bcbanhangctsp.IBcbanhangctspList;
import geso.traphaco.distributor.beans.bcbanhangctsp.imp.BcbanhangctspList;
import geso.traphaco.distributor.beans.bchoadonbanhang.IBchoadonbanhang;
import geso.traphaco.distributor.beans.bchoadonbanhang.IBchoadonbanhangList;
import geso.traphaco.distributor.beans.bchoadonbanhang.imp.Bchoadonbanhang;
import geso.traphaco.distributor.beans.bchoadonbanhang.imp.BchoadonbanhangList;
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

public class BchoadonbanhangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	private int items = 50;
	private int splittings = 10;
	public BchoadonbanhangSvl()
	{
		super();
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
		

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		IBchoadonbanhangList obj = (IBchoadonbanhangList) new BchoadonbanhangList();
		obj.setUserId(userId);

		String action = util.getAction(querystring);
		String khlId = util.getId(querystring);

		String msg = "";

		obj.init("");
		obj.setMsg(msg);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Distributor/Bchoadonbanhang.jsp";
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

		BchoadonbanhangList obj;

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}

	
		

		if (action.equals("excel"))
	    {
	    	obj = new BchoadonbanhangList();
	    	
	    	obj.setQuery(getSearchQuery(request, (IBchoadonbanhangList) obj));
	    	
	    	try
		    {
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=BCHoadonbanhang_"+getDateTime()+".xls");
		        
		        Workbook workbook = new Workbook();
		    	
		        CreateStaticHeader(workbook, "Nguyen Duy Hai");
			    CreateStaticData(workbook, getQueryExcel(request, (IBchoadonbanhangList) obj));
			
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
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/Bchoadonbanhang.jsp");	    		
	    }
		else if(action.equals("view") || action.equals("next") || action.equals("prev")){
			
			
			obj = new BchoadonbanhangList();
	    	String search = getSearchQuery(request, (IBchoadonbanhangList) obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);

	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/Bchoadonbanhang.jsp");
	    }
		else
		{
			obj = new BchoadonbanhangList();
			obj.setUserId(userId);

			String search = getSearchQuery(request, (IBchoadonbanhangList) obj);

			obj.setUserId(userId);
			obj.init(search);

			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);

			response.sendRedirect("/TraphacoHYERP/pages/Distributor/Bchoadonbanhang.jsp");
			

			
		}
		
	}

	private String getSearchQuery(HttpServletRequest request, IBchoadonbanhangList obj)
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
		if(makh == null)
			makh = "";
		obj.setMakh(makh);

		String mafast = util.antiSQLInspection(request.getParameter("mafast"));
		if(mafast == null)
			mafast = "";
		obj.setMafast(mafast);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String khohang = util.antiSQLInspection(request.getParameter("khohang"));
		if(khohang == null)
			khohang = "";
		obj.setKhohang(khohang);

		String trinhduocvien = util.antiSQLInspection(request.getParameter("trinhduocvien"));
		if(trinhduocvien == null)
			trinhduocvien = "";
		obj.setTrinhduocvien(trinhduocvien);

		String  sql = 	"select  donhang.NGAYNHAP, donhang.PK_SEQ, kh.maFAST, kh.TEN, donhang.Tientruocthue,   "+
						" donhang.VAT, donhang.Tongtien  "+
						" from  ( "+
						" select dh.PK_SEQ, dh.TRANGTHAI, dh.KHACHHANG_FK,dh.NGAYNHAP, dh.DDKD_FK,  dh.KHO_FK," +
						" sum(dhsp.SOLUONG*dhsp.GIAMUA) as Tientruocthue,  sum(dhsp.SOLUONG*dhsp.GIAMUA*dhsp.THUEVAT/100) as VAT,  " + 
						" sum(dhsp.SOLUONG*dhsp.GIAMUA*(1+dhsp.THUEVAT/100)) as Tongtien   " +
						" from DONHANG dh  inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ=dhsp.DONHANG_FK  " +
						" group by dh.PK_SEQ, dh.TRANGTHAI, dh.KHACHHANG_FK,dh.NGAYNHAP , dh.DDKD_FK, dh.KHO_FK ) donhang  "+
						" inner join KHACHHANG kh on donhang.KHACHHANG_FK= kh.PK_SEQ"+
						" inner join DAIDIENKINHDOANH ddkd on donhang.DDKD_FK=ddkd.PK_SEQ" +
						"  where 1=1 " ;

		if(tungay.length() > 0)
			sql += " and donhang.NGAYNHAP >='"+tungay+"' ";

		if(denngay.length() > 0)
			sql += " and donhang.NGAYNHAP <='"+denngay+"' ";

		if(makh.length() > 0)
			sql += " and kh.PK_SEQ='"+makh+"' ";

		if(mafast.length() > 0)
			sql += " and kh.maFAST like '%"+mafast+"%' ";

		if(trangthai.length() > 0)
			sql += " and donhang.trangthai='"+trangthai+"' ";

		if(khohang.length() > 0)
			sql += " and donhang.KHO_FK='"+khohang+"' ";

		if(trinhduocvien.length() > 0)
			sql += " and ddkd.PK_SEQ='"+trinhduocvien+"' ";

		System.out.println("[query tÃ¬m kiáº¿m]: "+ sql);

		return sql;
	}
	
	private String getQueryExcel(HttpServletRequest request, IBchoadonbanhangList obj){
		
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
	if(makh == null)
		makh = "";
	obj.setMakh(makh);

	String mafast = util.antiSQLInspection(request.getParameter("mafast"));
	if(mafast == null)
		mafast = "";
	obj.setMafast(mafast);

	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
	if(trangthai == null)
		trangthai = "";
	obj.setTrangthai(trangthai);

	String khohang = util.antiSQLInspection(request.getParameter("khohang"));
	if(khohang == null)
		khohang = "";
	obj.setKhohang(khohang);

	String trinhduocvien = util.antiSQLInspection(request.getParameter("trinhduocvien"));
	if(trinhduocvien == null)
		trinhduocvien = "";
	obj.setTrinhduocvien(trinhduocvien);

	String  sql = 	"select  donhang.NGAYNHAP, donhang.PK_SEQ, kh.maFAST, kh.TEN, donhang.Tientruocthue,   "+
			" donhang.VAT, donhang.Tongtien  "+
			" from  ( "+
			" select dh.PK_SEQ, dh.TRANGTHAI, dh.KHACHHANG_FK,dh.NGAYNHAP, dh.DDKD_FK,  dh.KHO_FK," +
			" sum(dhsp.SOLUONG*dhsp.GIAMUA) as Tientruocthue,  sum(dhsp.SOLUONG*dhsp.GIAMUA*dhsp.THUEVAT/100) as VAT,  " + 
			" sum(dhsp.SOLUONG*dhsp.GIAMUA*(1+dhsp.THUEVAT/100)) as Tongtien   " +
			" from DONHANG dh  inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ=dhsp.DONHANG_FK  " +
			" group by dh.PK_SEQ, dh.TRANGTHAI, dh.KHACHHANG_FK,dh.NGAYNHAP , dh.DDKD_FK, dh.KHO_FK ) donhang  "+
			" inner join KHACHHANG kh on donhang.KHACHHANG_FK= kh.PK_SEQ"+
			" inner join DAIDIENKINHDOANH ddkd on donhang.DDKD_FK=ddkd.PK_SEQ" +
			"  where 1=1 " ;

			if(tungay.length() > 0)
			sql += " and donhang.NGAYNHAP >='"+tungay+"' ";
			
			if(denngay.length() > 0)
			sql += " and donhang.NGAYNHAP <='"+denngay+"' ";
			
			if(makh.length() > 0)
			sql += " and kh.PK_SEQ='"+makh+"' ";
			
			if(mafast.length() > 0)
			sql += " and kh.maFAST like '%"+mafast+"%' ";
			
			if(trangthai.length() > 0)
				sql += " and donhang.trangthai='"+trangthai+"' ";
			
			if(khohang.length() > 0)
			sql += " and donhang.KHO_FK='"+khohang+"' ";
			
			if(trinhduocvien.length() > 0)
			sql += " and ddkd.PK_SEQ='"+trinhduocvien+"' ";

			System.out.println("[query excel 111 ]: "+ sql);
			System.out.println("an file");
			return sql;
	}
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    Style style;
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("DANH SÁCH HÓA ĐƠN BÁN HÀNG");	    
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
		cell = cells.getCell("A8");cell.setValue(" Số TT");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell); 
	    cell = cells.getCell("B8");cell.setValue("Ngày đơn hàng");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);    
	    cell = cells.getCell("C8");cell.setValue(" Số đơn hàng ");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("D8");cell.setValue(" Mã KH(FAST) ");  					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("E8");cell.setValue(" Tên đơn vị "); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("F8");cell.setValue(" Tiền trước thuế");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("G8");cell.setValue(" Tiền thuế ");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	    cell = cells.getCell("H8");cell.setValue(" Tổng tiền(sau VAT)"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell);
	   
	    worksheet.setName("BC Hóa đơn bán hàng ");
	}

	private void CreateStaticData(Workbook workbook, String query) 
	{
		
		System.out.println("Ä‘Ã£ vÃ o Ä‘Ã¢y !");
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
				cells.setColumnWidth(0, 10.0f);
				cells.setColumnWidth(1, 20.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 20.0f);
				cells.setColumnWidth(4, 50.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 25.0f);
				cells.setColumnWidth(7, 15.0f);
				
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
				int n=1;
				while(rs.next())
				{
					
					String ngay = rs.getString("NGAYNHAP");
					String maHD = rs.getString("PK_SEQ");
					String mafast = rs.getString("maFAST");
					String ten = rs.getString("TEN");
					String doanhthu = formatter.format(rs.getDouble("Tientruocthue"));
					String vat = formatter.format(rs.getDouble("VAT"));
					String tongtien = formatter.format(rs.getDouble("Tongtien"));
					
	
				
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(ngay); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(maHD); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(mafast); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(ten); 		style  = cell.getStyle(); style.setFont(font2); style.setHAlignment(HorizontalAlignmentType.LEFT); cell.setStyle(style); 
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(doanhthu); 		style = cell.getStyle(); style.setFont(font2);  style.setHAlignment(HorizontalAlignmentType.RIGHT); cell.setStyle(style);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(vat); 			style = cell.getStyle(); style.setFont(font2);  style.setHAlignment(HorizontalAlignmentType.RIGHT); cell.setStyle(style);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(tongtien); 		style = cell.getStyle(); style.setFont(font2);  style.setHAlignment(HorizontalAlignmentType.RIGHT);  cell.setStyle(style); 
				
					i++;
					
					System.out.println("Da in thanh cong nhe"); 
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
	
	private void setcanhphai(Cell cell) {
	
		
		Style phai = cell.getStyle();

		
		phai.setHAlignment(HorizontalAlignmentType.RIGHT);
		phai.setBorderLine(BorderType.TOP, 1);
		phai.setBorderLine(BorderType.RIGHT, 1);
		phai.setBorderLine(BorderType.BOTTOM, 1);
		phai.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(phai);
		
		
	}
	
	private void setcanhtrai(Cell cell) {
		
		Style trai = cell.getStyle();

		
		trai.setHAlignment(HorizontalAlignmentType.LEFT);
		trai.setBorderLine(BorderType.TOP, 1);
		trai.setBorderLine(BorderType.RIGHT, 1);
		trai.setBorderLine(BorderType.BOTTOM, 1);
		trai.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(trai);
		
	
		
	}
	

	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
