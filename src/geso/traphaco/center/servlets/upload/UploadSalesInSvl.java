package geso.traphaco.center.servlets.upload;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;

import geso.traphaco.center.beans.upload.IUpload;
import geso.traphaco.center.beans.upload.imp.Upload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


import java.sql.ResultSet;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.CellView;
import jxl.Sheet;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

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
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/UploadSalesInSvl")
public class UploadSalesInSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\excel\\";
	
	public UploadSalesInSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IUpload obj = new Upload();
			obj.createRs_SalesIn();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		obj.setUserId(userId);
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/UploadSalesIn.jsp";
		String id = util.getId(querystring);
		 String action = util.getAction(querystring);
		 System.out.println("_____"+action);
		if (action.equals("excel"))
		{
			obj.setMsg(SalesIn_Details(response, id));
		}
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IUpload dmhBean;
		dmhBean=new Upload();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();
		Utility util = new Utility();
		String userId;
		OutputStream out = response.getOutputStream();
		String action = request.getParameter("action");
		if (action == null)
			action = "";
		String thang=request.getParameter("thang");
		if(thang==null)
			thang="";
		dmhBean.setThang(thang);

		String nam=request.getParameter("nam");
		if(nam==null)
			nam="";
		dmhBean.setNam(nam);
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			dmhBean.setUserId(userId);
			dmhBean.setThang(multi.getParameter("thang"));
			dmhBean.setNam(multi.getParameter("nam"));
			Enumeration files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			if (filename != null && filename.length() > 0)
				dmhBean.setMsg(	this.readExcel(UPLOAD_DIRECTORY + filename, dmhBean));
			
			if (dmhBean.getMsg().trim().length() <= 0 && filename != null)
			{
				dmhBean.setMsg("Đọc file thành công ");
			} else if (filename == null)
			{
				dmhBean.setMsg("Vui lòng chọn file ");
			} 
		}
		else if(action.equals("excel"))
		{
			System.out.println("excel");
			dmhBean.setMsg(XuatFileExcel(response,dmhBean));
		}
		dmhBean.createRs_SalesIn();
		session.setAttribute("obj", dmhBean);
		String nextJSP = "/TraphacoHYERP/pages/Center/UploadSalesIn.jsp";
		response.sendRedirect(nextJSP);
	}

	private String readExcel(String fileName, IUpload dhBean)
	{
		File inputWorkbook = new File(fileName);
		jxl.Workbook w = null;
		dbutils db = new dbutils();
		try
		{
			w = jxl.Workbook.getWorkbook(inputWorkbook);
			jxl.Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			int socot = sheet.getColumns();
			System.out.println("So dong " + sodong + "socot " + socot);
			String query = "delete from SalesIn_Tmp";
			db.update(query);

			for (int i = 1; i < sodong; i++)
			{
				String manpp = getValue(sheet, 16, i).trim();
				String masp = getValue(sheet, 21, i).trim();
				String spTen=getValue(sheet, 4, i);
				String soluong = getValue(sheet, 7, i).trim();
				String loaihd = getValue(sheet, 5, i).trim();
				String lydohuy = getValue(sheet, 48, i).trim();
				if ((loaihd.equals("YSTD") || loaihd.equals("YFRE")) && lydohuy.length() > 0)
				{
					soluong = "0";
				} else if (loaihd.equals("YRET"))
				{
					soluong = "-" + soluong;
				}
				spTen=spTen.replace("'", "''");
				System.out.println("[loaihd]"+loaihd+"[lydohuy]"+lydohuy+"[soluong]"+soluong);
				if (manpp.length() > 0 && masp.length() > 0)
				{
					query = "Insert into SalesIn_Tmp(MaSp,spTen,SoLuong,manpp) " + " select '" + masp + "',N'"+spTen+"','" + soluong + "','" + manpp + "'";
					if(!db.update(query))
					{
						System.out.println("[i]"+i+query);	
					}
				}
			}
			dhBean.SalesIn();
			System.out.print(dhBean.getMsg());
			return dhBean.getMsg();
		} catch (BiffException e)
		{
			System.out.println("Loi doc file" + e.getMessage());
			e.printStackTrace();
			return "Vui lòng coi lại định dạng file";
		} catch (IOException e)
		{
			e.printStackTrace();
			return "Vui lòng coi lại định dạng file";
		} finally
		{

		}
	}

	private String getValue(Sheet sheet, int column, int row)
	{
		jxl.Cell cell;
		cell = sheet.getCell(column, row);
		return cell.getContents();
	}

	public static void main(String[] arg)
	{
		UploadSalesInSvl p = new UploadSalesInSvl();
		IUpload obj = new Upload();
		p.readExcel("", obj);
		String str = "JW  Gold Label Reserve 750ml";

	}

	
	private boolean CreatePivotTable(OutputStream out, IUpload obj) throws Exception
	{
		Workbook workbook = new Workbook();
		workbook.setFileFormatType(FileFormatType.EXCEL2003);
		boolean isFill = false;
		isFill = CreateStaticData(workbook, obj);
		if (!isFill)
		{
			return false;
		} else
		{
			workbook.save(out);
			return true;
		}
	}

	private boolean CreateStaticData(Workbook workbook, IUpload obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		int row = 3;
		int col = 2;
		int stt=0;
		String query = 
		"select sp.*,s.SalesIn ,t.TonDau\n  "+ 
		"from\n  "+ 
		"	(\n  "+ 
		"		select * 				 \n  "+ 
		"		from \n  "+ 
		"			(\n  "+ 
		"				select distinct TEN as spTen,dvTheTich ,THETICH 					\n  "+ 
		"				from SANPHAM sp\n  "+ 
		"				where TRANGTHAI=1\n  "+ 
		"			)as sp,\n  "+ 
		"			(\n  "+ 
		"				select ma,matat,PK_SEQ as nppId\n  "+ 
		"				from NHAPHANPHOI \n  "+ 
		"			)as npp\n  "+ 
		"	)as sp\n  "+ 
		"	left join\n  "+ 
		"	(\n  "+ 
		"		select b.Npp_FK,c.TEN as spTen,c.dvTheTich,c.THETICH,sum(b.SoLuong) as SalesIn\n  "+ 
		"		from\n  "+ 
		"		SalesIn a inner join SalesIn_Details b on b.SalesIn_FK=a.PK_SEQ\n  "+ 
		"		inner join SANPHAM c on c.PK_SEQ=b.SanPham_FK\n  "+ 
		"		group by b.Npp_FK,c.TEN ,c.dvTheTich,c.THETICH\n  "+ 
		"	) as s on s.Npp_FK=sp.nppId and s.spTen=sp.spTen and s.THETICH=sp.THETICH and sp.dvTheTich=s.dvTheTich\n  "+ 
		"	left join \n  "+ 
		"	(	\n  "+ 
		"		select b.Npp_FK,c.TEN as spTen,c.dvTheTich,c.THETICH,sum(b.toncuoi) as TonDau\n  "+ 
		"		from \n  "+ 
		"			TonKho a inner join TonKho_NPP b on a.Pk_Seq=b.TonKho_FK    \n  "+ 
		"			inner join SANPHAM c on c.PK_SEQ=b.SanPham_FK    \n  "+ 
		"			inner join NHAPHANPHOI d on d.PK_SEQ=b.Npp_fk    \n  "+ 
		"		where a.Thang=4 and a.Nam=2013    \n  "+ 
		"		group by b.Npp_FK,c.TEN ,c.dvTheTich,c.THETICH\n  "+ 
		"	)as t on t.Npp_fk=sp.nppId and t.spTen=sp.spTen and t.THETICH=sp.THETICH and t.dvTheTich=sp.dvTheTich\n  "+ 
		"order by sp.nppId,spTen	\n  ";
		ResultSet rs = db.get(query);
		System.out.println("Query___" + query);
		if (rs != null)
		{
			try
			{
				for (int j = 0; j < 15; j++)
					cells.setColumnWidth(row, 12.0f);
				Cell cell = null;
				String spIdPrev = "";
				String nppIdPrev = "";
				
				while (rs.next())
				{
					String nppId = rs.getString("nppId");
					String nppMa=rs.getString("nppMa");
					String nppTen=rs.getString("nppTen");
					String spId=rs.getString("spId");
					String spTen = rs.getString("spTen");
					float dungtich=rs.getFloat("spTen");
					String quycach=rs.getString("spTen");
					Float tondau=rs.getFloat("tondau");
					float toncuoi=rs.getFloat("toncuoi");
					float nhapIn=rs.getFloat("NhapIn");
					float nhapKhac=rs.getFloat("NhapKhac");
					float ban=rs.getFloat("Ban");
					if (nppIdPrev.equals(nppId))
					{
						
					} else if (!nppId.equals(nppIdPrev))
					{
						col++;
						nppIdPrev = nppId;
					}
					cell = cells.getCell(row,1);cell.setValue(spTen);
					cell = cells.getCell(row,1);cell.setValue(spTen);
					cell = cells.getCell(row, 2);cell.setValue(dungtich);
					cell = cells.getCell(row,3);cell.setValue(quycach);
					cell = cells.getCell(0, 4+col*4);cell.setValue(nppMa);
					cell = cells.getCell(0, 5);cell.setValue(nppMa);
					cell = cells.getCell(4, 7);cell.setValue(tondau);
					cell = cells.getCell(4,8);cell.setValue(nhapIn);
					cell = cells.getCell(4, 9);cell.setValue(nhapKhac);
					cell = cells.getCell(4, 10);cell.setValue(ban);
					cell = cells.getCell(4, 11);cell.setValue(toncuoi);
						Style style;
						for (int j = 5; j <= 8; j++)
						{
							setBorder(cell, cells, j, j, 0, col);
							cell = cells.getCell(j, col);
							style = cell.getStyle();
							style.setNumber(3);
							cell.setStyle(style);
						}
				}

				
				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();
				if (row == 8)
				{
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("115.Error: " + e.getMessage());
				return false;
			}
		}
		return true;
	}

	private void CreateCell(Cells cells, Cell cell, String CellPos, String Content, Color Color, Boolean Bold, int Size)
	{
		cell = cells.getCell(CellPos);
		ReportAPI.getCellStyle(cell, Color, Bold, Size, Content);
	}

	private void setBorder(Cell cell, Cells cells, int startRow, int endRow, int startColumn, int endColumn)
	{
		Style style;
		style = cell.getStyle();
		style.setBorderLine(BorderType.TOP, BorderLineType.DASHED);
		style.setBorderLine(BorderType.BOTTOM, BorderLineType.DASHED);
		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		cells.setRangeStyle(startRow, endRow, startColumn, endColumn, style);
	}
	
	
private String XuatFileExcel(HttpServletResponse response,IUpload obj) throws IOException {
		
		String fromMonth=obj.getNam()+"-"+obj.getThang()+"-01";
		if(obj.getThang().length()<2)
			fromMonth=obj.getNam()+"-0"+obj.getThang()+"-01";
		
		if(obj.getNam().trim().equals("0")||obj.getThang().trim().trim().equals("0"))
			return "Vui lòng chọn thời gian";
		OutputStream out = null;
		  try {
			   response.setContentType("application/vnd.ms-excel");
			   response.setHeader
			     ("Content-Disposition", "attachment; filename=TonKhoImport_"+fromMonth +".xls");
			   WritableWorkbook w = 
				jxl.Workbook.createWorkbook(response.getOutputStream());
			   WritableSheet sheet = w.createSheet("IssueLock", 0);
			   dbutils db=new dbutils();
			   String sql=
	   		   "select sp.nppMaTat,sp.spTen+ ' '+CAST(sp.thetich as varchar(20))+sp.dvTheTich as spTen,CAST(sp.thetich as varchar(20))+sp.dvTheTich  as thetich,sp.QuyCach,sp.nppMa,sp.nppTen,ton.tondau,sale.SaleIn\n  "+ 
			   "from\n  "+ 
			   "(\n  "+ 
			   "	select * from \n  "+ 
			   "	(\n  "+ 
			   "		select distinct ten as spTen ,THETICH,dvTheTich,(select soluong1 from quycach where SANPHAM_FK=sp.PK_SEQ and DVDL2_FK=100018 )as QuyCach\n  "+ 
			   "		from SANPHAM sp\n  "+ 
			   "	)as sp,\n  "+ 
			   "	(\n  "+ 
			   "		select ma as nppMa,ten as nppTen,matat as nppMaTat,PK_SEQ as npp_FK\n  "+ 
			   "		from NHAPHANPHOI\n  "+ 
			   "	)as npp\n  "+ 
			   ")as sp\n  "+ 
			   "left join \n  "+ 
			   "(\n  "+ 
			   "	select sum(toncuoi)as tondau,npp_fk,c.ten as spten,c.thetich,c.dvthetich\n  "+ 
			   "	from tonkho a inner join tonkho_npp b on b.tonkho_fk=a.pk_seq\n  "+ 
			   "	inner join sanpham c on c.pk_seq=b.sanpham_fk\n  "+ 
			   "	where a.ThoiGian=(select convert(varchar(10),DATEADD(month,-1,'"+fromMonth+"'),20) )\n  "+ 
			   "	group by npp_fk,c.ten ,c.thetich,c.dvthetich\n  "+ 
			   ")as ton on ton.Npp_fk=sp.npp_FK and ton.spten=sp.spTen and ton.THETICH=sp.THETICH\n  "+ 
			   "and ton.dvTheTich=sp.dvTheTich\n  "+ 
			   "left join \n  "+ 
			   "(\n  "+ 
			   "	select sum(SoLuong)as SaleIn,npp_fk,c.ten as spten,c.thetich,c.dvthetich\n  "+ 
			   "	from SalesIn a inner join SalesIn_Details b on b.SalesIn_FK=a.pk_seq\n  "+ 
			   "	inner join sanpham c on c.pk_seq=b.sanpham_fk\n  "+ 
			   "	where thoigian='"+fromMonth+"'\n  "+ 
			   "	group by npp_fk,c.ten ,c.thetich,c.dvthetich\n  "+ 
			   ")as sale on sale.dvTheTich=sp.dvTheTich and sale.spten=sp.spTen\n  "+ 
			   "and sale.THETICH=sp.THETICH and sale.Npp_FK=sp.npp_FK\n  "+ 
			   "order by sp.spTen,sp.dvTheTich,sp.THETICH,sp.npp_FK";
			   
			   System.out.println("1.Khoi tao upload : "+sql);
			   
			   
			   WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
			    cellFont.setColour(Colour.BLACK);
			    
			    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			    cellFormat.setBackground(Colour.WHITE);
			    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			    
			    WritableFont fBold = new WritableFont(WritableFont.ARIAL, 10);
			    fBold.setColour(Colour.RED);
			    fBold.setBoldStyle(jxl.write.WritableFont.BOLD);
			    WritableCellFormat fmBold = new WritableCellFormat(fBold);
			    fmBold.setBorder(Border.ALL, BorderLineStyle.THIN);
			    
			    
			   sheet.addCell(new Label(0, 1, "STT",cellFormat));
			   sheet.addCell(new Label(1, 1, "Sản phẩm",cellFormat));
			   sheet.addCell(new Label(2, 1, "Dung tích",cellFormat));
			   sheet.addCell(new Label(3, 1, "Quy cách",cellFormat));
			   ResultSet rs=db.get(sql);
			   Label label ;
			   jxl.write.Number number;
			   int row=1;
			   int col=0;
			   int stt=0;
			   String nppMaPrev="";
			   String spPrev="";
			   if(rs!=null)
			   {
				   while (rs.next())
				   {
					   String nppMa=rs.getString("nppMa");
					   String nppTen=rs.getString("nppMaTat");
					   String spTen=rs.getString("spTen");
					   String thetich=rs.getString("thetich");
					   float quycach=rs.getFloat("quycach");
					   float tondau=rs.getFloat("tondau");
					   float nhapIn=rs.getFloat("salein");
					   if(!spPrev.equals(spTen))
					   {
						   spPrev=spTen;
						   row++;
						   col=0;
						   stt++;
					   }				
					   number = new jxl.write.Number(0, row, stt); sheet.addCell(number);
					   sheet.addCell(new Label(1, row, spTen));
					   sheet.addCell(new Label(2, row, thetich));
					   number = new jxl.write.Number(3, row, quycach); sheet.addCell(number);
					   number = new jxl.write.Number(4+col*6, row, tondau); sheet.addCell(number);
					   number = new jxl.write.Number(5+col*6, row, nhapIn); sheet.addCell(number);			
					   if(!nppMaPrev.equals(nppMa))
					   {
						   nppMaPrev=nppMa;
						   sheet.addCell(new Label(4+col*6, 0, nppMa,fmBold));
						   sheet.addCell(new Label(5+col*6, 0, nppTen,fmBold));
						   sheet.addCell(new Label(4+col*6, 1, "Open",format(cellFont,Colour.BLACK,Colour.WHITE,Border.ALL,BorderLineStyle.THIN)));
						   sheet.addCell(new Label(5+col*6, 1, "In (Diageo)",format(cellFont,Colour.BLACK,Colour.WHITE,Border.ALL,BorderLineStyle.THIN)));
						   sheet.addCell(new Label(6+col*6, 1, "In (other)",format(cellFont,Colour.BLACK,Colour.WHITE,Border.ALL,BorderLineStyle.THIN)));
						   sheet.addCell(new Label(7+col*6, 1, "Out",format(cellFont,Colour.BLACK,Colour.WHITE,Border.ALL,BorderLineStyle.THIN)));
						   sheet.addCell(new Label(8+col*6, 1, "Close",format(cellFont,Colour.BLACK,Colour.WHITE,Border.ALL,BorderLineStyle.THIN)));
						   sheet.addCell(new Label(9+col*6, 1, "Check",format(cellFont,Colour.BLACK,Colour.WHITE,Border.ALL,BorderLineStyle.THIN)));
						   col++;
					   }
					}
				   sheet.addCell(new Label(1, ++row, "Total"));
			   }
			   sheetAutoFitColumns(sheet);
				   w.write();
				   w.close();
			   }
		  catch (Exception e) {
			  e.printStackTrace();
			  return "Không có báo cáo trong thời gian đã chọn ";
		}
		return "";
	}
	private void sheetAutoFitColumns(WritableSheet sheet) {
    for (int i = 0; i < sheet.getColumns(); i++) {
        jxl.Cell[] cells = sheet.getColumn(i);
        int longestStrLen = -1;

        if (cells.length == 0)
            continue;

        /* Find the widest cell in the column. */
        for (int j = 0; j < cells.length; j++) {
            if ( cells[j].getContents().length() > longestStrLen ) {
                String str = cells[j].getContents();
                if (str == null || str.isEmpty())
                    continue;
                longestStrLen = str.trim().length();
            }
        }

        /* If not found, skip the column. */
        if (longestStrLen == -1) 
            continue;

        /* If wider than the max width, crop width */
        if (longestStrLen > 255)
            longestStrLen = 255;

        CellView cv = sheet.getColumnView(i);
        cv.setSize(longestStrLen * 256 + 100); /* Every character is 256 units wide, so scale it. */
        sheet.setColumnView(i, cv);
    }
}

	private String SalesIn_Details(HttpServletResponse response,String  id) throws IOException 
	{
		OutputStream out = null;
		try {
		   response.setContentType("application/vnd.ms-excel");
		   response.setHeader
		     ("Content-Disposition", "attachment; filename=SalesIn" +".xls");
		   WritableWorkbook w = 
			jxl.Workbook.createWorkbook(response.getOutputStream());
		   WritableSheet sheet = w.createSheet("IssueLock", 0);
		   dbutils db=new dbutils();
		   String sql=
				  " select sp.ten as spTen,sp.thetich,sp.dvthetich,npp.ma as nppMa, "+
				   " npp.maTat as nppMaTat,npp.ten as nppTen,tk_npp.soluong, "+
				  " (select soluong1 from quycach where sanpham_fk=sp.pk_Seq and dvdl2_Fk=100018) as QuyCach "+
				  " from salesIn tk inner join salesIn_details tk_npp on tk_npp.salesin_fk=tk.pk_Seq "+
				  " inner join nhaphanphoi npp on npp.pk_seq=tk_npp.npp_fk "+
				  " inner join sanpham sp on sp.pk_Seq=tk_npp.sanpham_fk "+
				  " where tk.pk_Seq='"+id+"' "+
				  " order by sp.ten,sp.thetich,sp.dvthetich,npp.Ma ";
		  System.out.println("1.Khoi tao upload : "+sql);
		   WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
		    cellFont.setColour(Colour.BLACK);		    
		    
		    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(Colour.WHITE);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    
		   sheet.addCell(new Label(0, 0, "STT",cellFormat));
		   sheet.addCell(new Label(1, 0, "Sản phẩm",cellFormat));
		   sheet.addCell(new Label(2, 0, "Dung tích",cellFormat));
		   sheet.addCell(new Label(3, 0, "Quy cách",cellFormat));
		   sheet.addCell(new Label(4, 0, "Mã Đại lý",cellFormat));
		   sheet.addCell(new Label(5, 0, "Tên Đại lý",cellFormat));
		   sheet.addCell(new Label(6, 0, "Số lượng",cellFormat));
		   ResultSet rs=db.get(sql);
		   jxl.write.Number number;
		   int row=1;
		   int stt=1;
		   if(rs!=null)
		   while (rs.next())
		   {
			   String nppMa=rs.getString("nppMa");
			   String nppTen=rs.getString("nppMaTat");
			   String spTen=rs.getString("spTen");
			   String thetich=rs.getString("thetich");
			   String dvthetich=rs.getString("dvthetich");
			   spTen+=" " +thetich +dvthetich;
			   int quycach=rs.getInt("quycach");
			   int soluong=rs.getInt("soluong");
			   number = new jxl.write.Number(0, row, stt); sheet.addCell(number);
			   sheet.addCell(new Label(1, row, spTen));
			   sheet.addCell(new Label(2, row, thetich));
			   number = new jxl.write.Number(3, row, quycach); sheet.addCell(number);
			   sheet.addCell(new Label(4, row, nppMa,cellFormat));
			   sheet.addCell(new Label(5, row, nppTen,cellFormat));
			   number = new jxl.write.Number(6, row, soluong); sheet.addCell(number);	
			   row++;
			   stt++;
			}
		   sheetAutoFitColumns(sheet);
			   w.write();
			   w.close();
		   }
	  catch (Exception e) {
		  e.printStackTrace();
		  return "Không có báo cáo trong thời gian đã chọn ";
	}
	return "";
	}
	
	private WritableCellFormat format(WritableFont font,Colour color,Colour background,Border boder,BorderLineStyle lineStyle)
	{
		WritableFont cellFont = new WritableFont(font);
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
	    try
		{
			cellFont.setColour(color);
			cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(background);
		    cellFormat.setBorder(boder, lineStyle);
		} catch (WriteException e)
		{
			e.printStackTrace();
		}		    
	    return cellFormat;
	    
	}
	
}
