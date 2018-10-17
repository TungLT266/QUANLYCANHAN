package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.distributor.beans.xuatkho.IErpImportdonhangETCList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpImportdonhangETCList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.oreilly.servlet.MultipartRequest;
import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ErpImportdonhangETCSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\upload\\excel\\";
       
    public ErpImportdonhangETCSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpImportdonhangETCList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    
	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
    	String lsxId = util.getId(querystring);
	    obj = new ErpImportdonhangETCList();
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    obj.setUserId(userId);
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpImportDonHangETC.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	    
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
		IErpImportdonhangETCList obj = new ErpImportdonhangETCList();
		
		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		obj.setNpp_duocchon_id(npp_duocchon_id);
		    
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    obj.setUserId(userId);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    String khId = request.getParameter("khId");
	    if(khId == null)
	    	khId = "";
	    obj.setNppTen(khId);
	    
	    String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTungay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    System.out.println("---NPP ID: " + nppId);
	    
	    String contentType = request.getContentType();
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			session.setAttribute("userId", userId);
			obj.setUserId(userId);
			
			Enumeration files = multi.getFileNames();
			String filename = "";
			System.out.println("__userId" + userId);
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			if (filename != null && filename.length() > 0)
				obj.setMsg(this.readExcel(UPLOAD_DIRECTORY + filename, obj));

			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpImportDonHangETC.jsp";
			response.sendRedirect(nextJSP);
		} 
		else
		{
			String condition = "";
			if( tungay.trim().length() > 0 )
				condition += " and a.NgayDonHang >= '" + tungay + "'  ";
			if( denngay.trim().length() > 0 )
				condition += " and a.NgayDonHang <= '" + denngay + "' ";
			
	    	String query =   "select a.machungtu, a.NgayDonHang, ISNULL(f.MaHopDong, '') as sohopdong,  b.maFAST as khMa, b.TEN as khTen, d.MA as spMa, d.TEN as spTen, e.DONVI,  "+
			    			 "		dbo.DinhDangTien( c.soluong ) as soluong, dbo.DinhDangTien( c.dongia ) as dongia, dbo.DinhDangTien( c.soluong * c.dongia ) as thanhtien, "+
			    			 "		isnull(g.maFAST, '') as tdvMa, isnull(g.TEN, '') as tdvTen "+
			    			 "from ERP_DONDATHANGNPP a inner join KHACHHANG b on a.khachhang_fk = b.pk_seq "+
			    			 "	inner join ERP_DONDATHANGNPP_SANPHAM c on a.PK_SEQ = c.dondathang_fk "+
			    			 "	inner join SANPHAM d on c.sanpham_fk = d.PK_SEQ "+
			    			 "	left join DONVIDOLUONG e on c.dvdl_fk = e.PK_SEQ "+
			    			 "	left join ERP_HOPDONGNPP f on a.HOPDONG_FK = f.PK_SEQ "+
			    			 "	left join DAIDIENKINHDOANH g on c.ddkd_fk = g.PK_SEQ "+
			    			 "where a.LoaiDonHang = '1' and a.TRANGTHAI != 3  "+ condition +
			    			 "order by a.NgayDonHang asc ";
	    	System.out.println("::: LAY BC: " + query );
	    	
	    	this.XuatExcel(response, query, tungay, denngay);
		    
		}
	}
	
	private String readExcel(String fileName, IErpImportdonhangETCList obj) 
	{
		dbutils db = new dbutils();
		try
		{
			File inputWorkbook = new File(fileName);
			jxl.Workbook w = null;
			w = jxl.Workbook.getWorkbook(inputWorkbook);
			jxl.Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			
			
			String query = "";
			for (int i = 5; i < sodong; i++)
			{
				String machungtu = getValue(sheet, 0, i).trim();
				String masanpham = getValue(sheet, 5, i).trim();
				String matdv = getValue(sheet, 11, i).trim();
				
				System.out.println("::: MA CHUNG TU:  " + machungtu + " -- MA SAN PHAM: " + masanpham + " -- MA TDV: " + matdv);
				if( machungtu.length() > 0 && masanpham.length() > 0 && matdv.length() > 0 )
				{
					query = "Update ERP_DONDATHANGNPP_SANPHAM set ddkd_fk = ( select top(1) pk_seq from DAIDIENKINHDOANH where maFAST = '" + matdv + "' and TRANGTHAI = '1' )  " + 
							"where dondathang_fk = ( select pk_seq from ERP_DONDATHANGNPP where machungtu = '" + machungtu + "' ) " + 
							"	and sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + masanpham + "' )	";
					
					System.out.println("::: CAP NHAT:  " + query);
					db.update(query);
				}

			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return "Vui lòng coi lại file " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		
		return "Import thành công";
	}

	private void XuatExcel(HttpServletResponse response, String query, String tungay, String denngay) throws IOException
	{
		dbutils db = new dbutils();
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=ImportDonHangETC.xls");
			
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Đơn hàng ETC", 0);
			
			sheet.addCell(new Label(0, 0, "Import đơn hàng ETC "));
			
			sheet.addCell(new Label(0, 1, "Từ ngày: "));
			sheet.addCell(new Label(1, 1, tungay));

			sheet.addCell(new Label(0, 2, "Đến ngày: "));
			sheet.addCell(new Label(1, 2, denngay));

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.GRAY_25);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			sheet.addCell(new Label(0, 4, "Mã chứng từ", cellFormat));
			sheet.addCell(new Label(1, 4, "Ngày đặt hàng", cellFormat));
			sheet.addCell(new Label(2, 4, "Số hợp đồng", cellFormat));
			sheet.addCell(new Label(3, 4, "Mã khách hàng", cellFormat));
			sheet.addCell(new Label(4, 4, "Tên khách hàng", cellFormat));
			sheet.addCell(new Label(5, 4, "Mã sản phẩm", cellFormat));
			sheet.addCell(new Label(6, 4, "Tên sản phẩm", cellFormat));
			sheet.addCell(new Label(7, 4, "Đơn vị", cellFormat));
			sheet.addCell(new Label(8, 4, "Số lượng", cellFormat));
			sheet.addCell(new Label(9, 4, "Đơn giá", cellFormat));
			sheet.addCell(new Label(10, 4, "Thành tiền", cellFormat));
			sheet.addCell(new Label(11, 4, "Mã TDV", cellFormat));
			sheet.addCell(new Label(12, 4, "Tên TDV", cellFormat));
			
			// set style to cell data
			WritableCellFormat cellFormat2 = new WritableCellFormat();

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
	
			ResultSet rs = db.get(query);
			
			int j = 5;
			while (rs.next())
			{
				Label label;

				label = new Label(0, j, rs.getString("machungtu"), cellFormat2);
				sheet.addCell(label);

				label = new Label(1, j, rs.getString("NgayDonHang"), cellFormat2);
				sheet.addCell(label);

				label = new Label(2, j, rs.getString("sohopdong"), cellFormat2);
				sheet.addCell(label);

				label = new Label(3, j, rs.getString("khMa"), cellFormat2);
				sheet.addCell(label);

				label = new Label(4, j, rs.getString("khTen"), cellFormat2);
				sheet.addCell(label);

				label = new Label(5, j, rs.getString("spMa"), cellFormat2);
				sheet.addCell(label);

				label = new Label(6, j, rs.getString("spTen"), cellFormat2);
				sheet.addCell(label);

				label = new Label(7, j, rs.getString("DONVI"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(8, j, rs.getString("soluong"), cellFormat2);
				sheet.addCell(label);

				label = new Label(9, j, rs.getString("dongia"), cellFormat2);
				sheet.addCell(label);

				label = new Label(10, j, rs.getString("thanhtien"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(11, j, rs.getString("tdvMa"), cellFormat2);
				sheet.addCell(label);

				label = new Label(12, j, rs.getString("tdvTen"), cellFormat2);
				sheet.addCell(label);

				j++;	
			}
			
			for(int x = 0; x <= 12; x++)
			{
			    CellView cell = sheet.getColumnView(x);
			    cell.setAutosize(true);
			    sheet.setColumnView(x, cell);
			}
			
			
			w.write();
			w.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
	}
	
	private String getValue(Sheet sheet, int column, int row)
	{
		Cell cell;
		cell = sheet.getCell(column, row);
		try
		{
			return cell.getContents();
		} 
		catch (Exception er)
		{
			return "0";
		}
	}
	
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
