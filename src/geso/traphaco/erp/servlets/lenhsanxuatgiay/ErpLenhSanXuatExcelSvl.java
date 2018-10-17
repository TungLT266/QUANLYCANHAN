package geso.traphaco.erp.servlets.lenhsanxuatgiay;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuat;
import geso.traphaco.center.db.sql.dbutils;
import geso.salesup.utils.ExcelUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;


@WebServlet("/ErpLenhSanXuatExcelSvl")
public class ErpLenhSanXuatExcelSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpLenhSanXuatExcelSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");

		IErpLenhsanxuat dhBean;
		dbutils db;

		Utility util = new Utility();
		String querystring = request.getQueryString();
		String dhid = util.getId(querystring);

		userId = util.getUserId(querystring);
		dhBean = new ErpLenhsanxuat();

		dhBean.setId(dhid);

		dhBean.setUserId(userId); // phai co UserId truoc khi Init

		db = new dbutils();

		String sql = " select distinct sp.DVKD_FK from ERP_LENHSANXUAT_SANPHAM lsxsp " + " inner join ERP_SANPHAM sp on sp.PK_SEQ=lsxsp.SANPHAM_FK where lsxsp.LENHSANXUAT_FK=" + dhid;
		ResultSet rs = db.get(sql);
		try
		{
			if (rs.next())
			{
				dhBean.setDvkdId(rs.getString("DVKD_FK"));
			}
			rs.close();
		} catch (Exception er)
		{
			er.printStackTrace();
		}
		
		response.setContentType("application/xlsm");
		response.setHeader("Content-Disposition", "attachment; filename=LenhSanXuat_"+this.getDateTime()+".xlsm");
	 
        OutputStream out = response.getOutputStream();
		inPhieu_Nhom(out,dhBean,db);
		db.shutDown();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}
	
	
	public void inPhieu_Nhom(OutputStream out ,IErpLenhsanxuat dhBean,geso.traphaco.center.db.sql.dbutils db)
	{ 
		try
		{
			Workbook workbook = new Workbook();
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpLenhsanxuatExcel.xlsm");
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			
			Worksheet worksheet = workbook.getWorksheets().getSheet(0);
			Style tableHeaderStyle = worksheet.getCells().getCell("A1").getStyle();
			tableHeaderStyle.setBorderLine(BorderType.BOTTOM, 1);
			tableHeaderStyle.setBorderLine(BorderType.LEFT, 1);
			tableHeaderStyle.setBorderLine(BorderType.TOP, 1);
			tableHeaderStyle.setBorderLine(BorderType.RIGHT, 1);
			com.aspose.cells.Font font = tableHeaderStyle.getFont();
			font.setSize(9);
			font.setBold(true);
			tableHeaderStyle.setFont(font);
			tableHeaderStyle.setHAlignment(TextAlignmentType.CENTER);		
				
			Style tableContainStyle = worksheet.getCells().getCell("A7").getStyle();
			tableContainStyle.setBorderLine(BorderType.BOTTOM, 1);			
			tableContainStyle.setBorderLine(BorderType.LEFT, 1);
			tableContainStyle.setBorderLine(BorderType.TOP, 1);			
			tableContainStyle.setBorderLine(BorderType.RIGHT, 1);
			font = tableContainStyle.getFont();
			font.setSize(9);
			font.setBold(false);
			tableContainStyle.setFont(font);
			
			String sql= 
			"	select a.NGAYDUKIENHT,a.NGAYBATDAU ,c.Ma as khMa, c.Ten as khTen,  "+
			"		a.dondathang_fk,d.ngaydukiengiao ,sp.MA as spMa, sp.TEN as spTen, isnull(sp.DAI, 0) AS DAI, "+ 
			"		ISNULL(sp.RONG, 0) AS RONG, ISNULL(sp.DINHLUONG,0) AS DINHLUONG,   "+
			"		isnull(sp.DVDL_DAI, '') as DVDL_DAI, isnull(sp.DVDL_RONG, '') as DVDL_RONG, "+ 
			"		isnull(sp.DVDL_DINHLUONG, '') as DVDL_DINHLUONG,   "+
			"		ISNULL(dvdl.donvi, 'NA') as donvi, isnull(sp.mau, '') as mau, "+
			"		isnull(sp.QuyCach_NguonGoc, '') as nguongoc "+  
			"	from ERP_LENHSANXUAT_GIAY a "+ 
			"		inner join ERP_LENHSANXUAT_SANPHAM b on b.LENHSANXUAT_FK=a.PK_SEQ "+ 
			"		left join ERP_SANPHAM sp on sp.PK_SEQ = b.SANPHAM_FK  "+
			"		left join ERP_KHACHHANG c on c.PK_SEQ=b.KHACHHANG_FK   "+
			"		left join ERP_DONDATHANG_SP d on d.DONDATHANG_FK=a.dondathang_fk "+ 
			"		left join DONVIDOLUONG dvdl on sp.DVDL_FK = dvdl.PK_SEQ  " +
			"  where b.lenhsanxuat_fk='"+dhBean.getId()+"' "; 
	
			ResultSet rs=db.get(sql);
			String khMa="";
			String khTen="";
			String spTen="";
			String spMa="";
			String ddhId="";
			String ngaydukiengiao="";
			String ngayBatDau="";
		
			if(rs.next())
			{
				khMa=rs.getString("khMa")==null?"":rs.getString("khMa");
				khTen=rs.getString("khTen")==null?"":rs.getString("khTen");
				ddhId =rs.getString("dondathang_fk")==null?"":rs.getString("dondathang_fk");
				spMa=rs.getString("spMa");
				spTen=rs.getString("spTen");
				ngayBatDau=rs.getString("NGAYBATDAU");
				ngaydukiengiao=rs.getString("ngaydukiengiao")==null?"":rs.getString("ngaydukiengiao");
			}
			
			
			
			ExcelUtil excelUtil = new ExcelUtil();
			excelUtil.setWorksheet(worksheet);
			
			int indexRow=7;
			
			excelUtil.addRow(new String[]{"Phiếu SX/P.O No :"+dhBean.getId()},new String[]{"B"},indexRow);
			excelUtil.addRow(new String[]{"Ngày: "+ngayBatDau},new String[]{"L"},indexRow);
			indexRow++;
			
			
			excelUtil.addRow(new String[]{"Khách hàng/Customer: "+khMa+khTen},new String[]{"B"},indexRow);
			indexRow++;
						
			excelUtil.addRow(new String[]{"Sản phẩm sản xuất: "+spTen},new String[]{"B"},indexRow);			
			excelUtil.addRow(new String[]{"Mã sản phẩm: "+spMa},new String[]{"L"},indexRow);
			indexRow++;
			
			excelUtil.addRow(new String[]{"Số phiếu xác nhận đặt hàng: "+ddhId},new String[]{"B"},indexRow);
			
			excelUtil.addRow(new String[]{"Ngày Giao Hàng: "+ngaydukiengiao},new String[]{"L"},indexRow);
			
			
			
			com.aspose.cells.Cell cell = worksheet.getCells().getCell("A1");
			Style titleStyle = cell.getStyle();
			font  = titleStyle.getFont();
			font.setBold(true);
			font.setSize(18);	
			titleStyle.setFont(font);
			titleStyle.setHAlignment(TextAlignmentType.CENTER);
			
			indexRow=13;
			
			sql=
			"	select sp.pk_seq,a.soluong, "+
			"		sp.duongkinhtrong,qc.soluong1,qc.soluong2,qc.dvdl2_fk,qc.dvdl1_fk  , "+
			"		sp.ten as spten, isnull(sp.dai, 0) as dai,  "+
			"		isnull(sp.rong, 0) as rong, isnull(sp.dinhluong,0) as dinhluong,   "+
			"		isnull(sp.dvdl_dai, '') as dvdl_dai, isnull(sp.dvdl_rong, '') as dvdl_rong, "+ 
			"		isnull(sp.dvdl_dinhluong, '') as dvdl_dinhluong,   "+
			"		isnull(c.donvi, 'na') as donvi, isnull(sp.mau, '') as mau, "+
			"		isnull(sp.quycach_nguongoc, '') as nguongoc "+   
			"	from erp_lenhsanxuat_sanpham  a    "+
			"		inner join erp_sanpham sp on sp.pk_seq=a.sanpham_fk  "+ 
			"		left join quycach qc on qc.sanpham_fk=sp.pk_seq   "+
			"		left join donvidoluong c on sp.dvdl_fk = c.pk_seq "+   
			"	WHERE a.LENHSANXUAT_FK='"+ dhBean.getId()+"' "; 

		  rs=db.get(sql);
		  
		  System.out.println("[erp_lenhsanxuat_sanpham]"+sql);
		  
		int i=0;
		
		Cells cells = worksheet.getCells();
		try
		{
			while (rs.next())
			{
				i++;
				
				String qc = "";

			    if(rs.getFloat("DINHLUONG") != 0 ) 
			    {
			        qc += rs.getString("DINHLUONG") + rs.getString("DVDL_DINHLUONG");
			    }
			    if(rs.getFloat("RONG") != 0 ) 
			    {
			        if(qc.length() > 0) 
			        { 
			        	qc += " x "; 
			        }
			        qc += rs.getString("RONG") + rs.getString("DVDL_RONG");
			    }
			    if(rs.getFloat("DAI") != 0 ) 
			    {
			    	if(qc.length() > 0) 
			    	{ 
			    		qc += " x "; 
			    	}
			    	qc += rs.getString("dai") + rs.getString("DVDL_DAI");
			    }
				String mau = rs.getString("mau"); if(mau == null || mau.trim().toLowerCase().indexOf("không") >= 0 ) mau = ""; else mau = mau.trim();
				String nguongoc = rs.getString("nguongoc"); if(nguongoc == null || nguongoc.trim().toLowerCase().equals("no") || nguongoc.trim().toLowerCase().indexOf("không") >= 0) nguongoc = ""; else nguongoc = nguongoc.trim();
				
				double dinh_luong=rs.getDouble("dinhluong");
				double so_luong=rs.getDouble("soluong");
				double trong_luong=0;
				if(rs.getDouble("soluong1")!=0)
					trong_luong=rs.getDouble("soluong")*rs.getDouble("soluong2")/rs.getDouble("soluong1");
				
				
				cell = cells.getCell("A" + String.valueOf(indexRow));cell.setValue(i);	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);

				
				cell = cells.getCell("B" + String.valueOf(indexRow));cell.setValue(qc);	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				
				cell = cells.getCell("E" + String.valueOf(indexRow));cell.setValue(dinh_luong);	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				
				cell = cells.getCell("E" + String.valueOf(indexRow));cell.setValue(so_luong);	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
				
				cell = cells.getCell("E" + String.valueOf(indexRow));cell.setValue(trong_luong);	
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
				
		
				excelUtil.mergeCells(worksheet, indexRow, indexRow, 1, 3);
				
				excelUtil.mergeCells(worksheet, indexRow, indexRow, 4, 6);
		
				excelUtil.mergeCells(worksheet, indexRow, indexRow, 7, 9);
				
				excelUtil.mergeCells(worksheet, indexRow, indexRow, 10, 12);
				
				excelUtil.mergeCells(worksheet, indexRow, indexRow, 13, 14);
				indexRow++;
			}
		}catch(Exception er)
		{
			er.printStackTrace();
		}
		while(i<3)
		{
			i++;
			excelUtil.mergeCells(worksheet, indexRow, indexRow, 1, 3);
			
			excelUtil.mergeCells(worksheet, indexRow, indexRow, 4, 6);
			
			excelUtil.mergeCells(worksheet, indexRow, indexRow, 7, 9);
			
			excelUtil.mergeCells(worksheet, indexRow, indexRow, 10, 12);
			
			excelUtil.mergeCells(worksheet, indexRow, indexRow, 13, 14);
			indexRow++;
		}
		
		  
		indexRow=19;
		sql= 
		"select a.SOLUONG,qc.soluong1,qc.soluong2 , "+
		"	sp.pk_seq as spId,sp.duongkinhtrong,sp.ma as spMa,sp.ten as spten, isnull(sp.dai, 0) as dai, "+ 
		"	isnull(sp.rong, 0) as rong, isnull(sp.dinhluong,0) as dinhluong,   "+
		"	isnull(sp.dvdl_dai, '') as dvdl_dai, isnull(sp.dvdl_rong, '') as dvdl_rong, "+ 
		"	isnull(sp.dvdl_dinhluong, '') as dvdl_dinhluong,   "+
		"	isnull(c.donvi, 'na') as donvi, isnull(sp.mau, '') as mau, "+
		"	isnull(sp.quycach_nguongoc, '') as nguongoc  "+
		"from ERP_LENHSANXUAT_BOM_GIAY a   "+
		"	inner join ERP_SANPHAM sp on sp.PK_SEQ=VATTU_FK "+  
		"	left join donvidoluong c on sp.dvdl_fk = c.pk_seq  "+   
		"	left join QUYCACH qc on qc.SANPHAM_FK=sp.PK_SEQ and qc.DVDL1_FK=100003 "+ 
		"where LENHSANXUAT_FK='"+dhBean.getId()+"' AND  "+
		"SP.LOAISANPHAM_FK IN  "+
		"(  "+
		"	100013,	100014,100015,100016  "+
		") "; 

	  rs=db.get(sql);
	  
	  System.out.println("[ERP_LENHSANXUAT_BOM_GIAY]"+sql);
	  
			 i=0;
			try
			{
				while (rs.next())
				{
					i++;
					
					String qc = "";

					 if(rs.getFloat("DINHLUONG") != 0 ) 
					    {
					        qc += rs.getString("DINHLUONG") + rs.getString("DVDL_DINHLUONG");
					    }
					    if(rs.getFloat("RONG") != 0 ) 
					    {
					        if(qc.length() > 0) 
					        { 
					        	qc += " x "; 
					        }
					        qc += rs.getString("RONG") + rs.getString("DVDL_RONG");
					    }
					    if(rs.getFloat("DAI") != 0 ) 
					    {
					    	if(qc.length() > 0) 
					    	{ 
					    		qc += " x "; 
					    	}
					    	qc += rs.getString("dai") + rs.getString("DVDL_DAI");
					    }
						String mau = rs.getString("mau"); if(mau == null || mau.trim().toLowerCase().indexOf("không") >= 0 ) mau = ""; else mau = mau.trim();
						String nguongoc = rs.getString("nguongoc"); if(nguongoc == null || nguongoc.trim().toLowerCase().equals("no") || nguongoc.trim().toLowerCase().indexOf("không") >= 0) nguongoc = ""; else nguongoc = nguongoc.trim();
					double so_luong=rs.getDouble("soluong");
					double trong_luong=0;
					if(rs.getDouble("soluong1")!=0)
						trong_luong=rs.getDouble("soluong")*rs.getDouble("soluong2")/rs.getDouble("soluong1");
				
					 spMa = rs.getString("spMa");
					 spTen = rs.getString("spTen");
					 
					cell = cells.getCell("A" + String.valueOf(indexRow));cell.setValue(i);	
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);

					cell = cells.getCell("B" + String.valueOf(indexRow));cell.setValue(spTen);	
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("C" + String.valueOf(indexRow));cell.setValue(qc);	
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("K" + String.valueOf(indexRow));cell.setValue(so_luong);	
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);

					cell = cells.getCell("N" + String.valueOf(indexRow));cell.setValue(trong_luong);	
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);
					
					cell = cells.getCell("O" + String.valueOf(indexRow));cell.setValue(nguongoc);	
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
					indexRow++;
				}
			}catch(Exception er)
			{
				er.printStackTrace();
			}
			while(i<5)
			{
				
				i++;
			}
		workbook.save(out);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
	}
	
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	

}
