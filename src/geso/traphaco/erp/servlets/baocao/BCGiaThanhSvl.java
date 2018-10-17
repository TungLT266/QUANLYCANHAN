package geso.traphaco.erp.servlets.baocao;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.baocao.IBaocao;
import geso.traphaco.erp.beans.baocao.imp.Baocao;
import geso.traphaco.erp.db.sql.dbutils;

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
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCGiaThanhSvl extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;

	public BCGiaThanhSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility Ult = new Utility();
		HttpSession session = request.getSession();

		String querystring = request.getQueryString();
		String userId = Ult.getUserId(querystring);
		
		IBaocao obj =  new Baocao();
		obj.setUserId(userId);
		
		
		
		
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCGiaThanh.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();
		   
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");

		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			OutputStream out = response.getOutputStream();
			IBaocao obj = new Baocao();
		
			obj.setUserId(userId);		
			
			String year = util.antiSQLInspection(request.getParameter("year"));
			if (year == null)
				year = "";
			obj.setYear(year);

			String month = util.antiSQLInspection(request.getParameter("month"));
			if (month == null)
				month = "";
			
			obj.setMonth(month);
				
			String tkId = util.antiSQLInspection(request.getParameter("tkId"));
			if (tkId == null)
				tkId = "";
			
		

	
			String action = request.getParameter("action");
			String lenhsanxuat = util.antiSQLInspection(request.getParameter("lenhsanxuat"));
		
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCGiaThanh.jsp";

			if (action.equals("excel")) {
				try {
					getData(obj, lenhsanxuat);
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BCTinhGiaThanh.xlsm");
					CreateReport(out, obj,lenhsanxuat);

				} catch (Exception ex) {
					obj.setMsg(ex.getMessage());
					session.setAttribute("obj", obj);
					response.sendRedirect(nextJSP);
					return;
				}
			}
			
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}
	}
	private void getData(IBaocao obj,String lenhsanxuat) throws SQLException
	{
		String query= "select lsx.pk_seq,sp.MA,sp.TEN,lsx.SOLUONG from ERP_LENHSANXUAT_GIAY lsx inner join Erp_KichBanSanXuat_Giay kb on kb.PK_SEQ= lsx.KICHBANSANXUAT_FK inner join ERP_SANPHAM sp on kb.SanPham_FK= sp.PK_SEQ"
			+ " where lsx.pk_Seq = " + lenhsanxuat +"" ;
		dbutils db= new dbutils();
		ResultSet rs = db.get(query);
		if(rs!=null)
		while (rs.next())
		{
			obj.setTenSp(rs.getString("Ten"));
			obj.setLsxID(rs.getString("PK_seq"));
			obj.setSoluong(rs.getString("Soluong"));
			obj.setMaSp(rs.getString("Ma"));
			
		}
		rs.close();
		db.shutDown();
	}
	private void CreateReport(OutputStream out, IBaocao obj, String lenhsanxuat)throws Exception {

		try{
			String file = getServletContext().getInitParameter("path") + "\\ErpBCGiaThanh.xlsm";
			
			System.out.println(file);
			
			FileInputStream fstream = new FileInputStream(file);
				
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			
			Cells cells = worksheet.getCells();

			Style style;		
			Font font = new Font();
			font.setColor(Color.NAVY);
			font.setSize(10);
			font.setBold(true);
			
			
		
			cells.setColumnWidth(0, 1.4f);
			cells.setColumnWidth(1, 20.0f);
			cells.setColumnWidth(2, 15.29f);
			cells.setColumnWidth(3, 30.00f);
			cells.setColumnWidth(4, 15.29f);
			cells.setColumnWidth(5, 26.86f);
			cells.setColumnWidth(6, 26.86f);
			cells.setColumnWidth(7, 33.29f);
			cells.setColumnWidth(8, 33.29f);
			Cell cell = cells.getCell("A1");			
			cell.setValue(""); 

			
			style = cell.getStyle();
			

		
			cells.merge(6, 1, 6, 8);
			cell = cells.getCell("E3");
			font.setColor(Color.BLUE);
			cell.setValue("BÁO CÁO CHI PHÍ GIÁ THÀNH THEO LỆNH SẢN XUẤT");			
			font.setSize(16);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			
			String lenhsanxuat_soluong ="Lệnh sản xuất : "+ obj.getLsxID() + " Định mức: " + obj.getSoluong();
			cells.merge(8, 1, 8, 8);
			cell = cells.getCell("E4");
			font.setColor(Color.NAVY);
			cell.setValue(lenhsanxuat_soluong);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			
			String Sanpham= "Mã :" + obj.getMaSp() + "Tên sản phẩm :" + obj.getTenSp() ;
			cell = cells.getCell("E5");
			font.setColor(Color.NAVY);
			cell.setValue(Sanpham);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style); 
			cells.merge(7, 1, 7, 8);
			
			cell = cells.getCell("E6");
			font.setColor(Color.NAVY);
			cell.setValue("Tháng " + obj.getMonth() + " Năm " + obj.getYear());			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
		
			
			ResultSet rs = obj.getListGia(lenhsanxuat);
			int row = 10;
			double Soluongchenhlech=0;
			double Giatrichenhlech = 0;
			while(rs.next()){
				cell = cells.getCell("B" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("MA"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("C" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("TEN"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);

				cell = cells.getCell("D" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("DVT"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("E" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("SOLUONG_DM"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);

				cell = cells.getCell("F" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("GIATRI_DM"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				
				cell = cells.getCell("G" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("SOLUONG"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
			
				
				cell = cells.getCell("H" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("GIATRI"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				Soluongchenhlech= rs.getDouble("SOLUONG")-rs.getDouble("SOLUONG_DM");
				Giatrichenhlech= rs.getDouble("GIATRI")-rs.getDouble("GIATRI_DM");
				
				cell = cells.getCell("I" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("Soluongchenhlech"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("J" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("Giatrichenhlech"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);


				row++;
			}

			
			
			
		
			
			

			
			workbook.save(out);
			
			fstream.close();
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Khong tao duoc header cho bao cao");
					}
	}
	


		private String getDate() 
		{
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = new Date();
	        return dateFormat.format(date);	
		}
		
		 public Cell CreateBorderSetting(Cell cell) throws IOException
		    {
			
		        Style style;
		        style = cell.getStyle();

		        //Set border color
		        style.setBorderColor(BorderType.TOP, Color.BLACK);
		        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		        style.setBorderColor(BorderType.LEFT, Color.BLACK);
		        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

		        //Set the cell border type
		        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
		        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
		        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

		        cell.setStyle(style);
		        return cell;
		    }

		 public Cell CreateBorderSetting2(Cell cell) throws IOException
		    {
			
		        Style style;
		        style = cell.getStyle();

		        //Set border color
		        style.setBorderColor(BorderType.TOP, Color.BLACK);
		        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		        style.setBorderColor(BorderType.LEFT, Color.BLACK);
		        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

		        //Set the cell border type
		        style.setBorderLine(BorderType.TOP, BorderLineType.DOTTED);
		        style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
		        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

		        cell.setStyle(style);
		        return cell;
		    }

}
