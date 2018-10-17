package geso.traphaco.distributor.servlets.donhang;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.dondathang.ErpDondathangUpdateSvl;
import geso.traphaco.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

@WebServlet("/ImportDonHangSvl")
public class ImportDonHangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ImportDonHangSvl()
	{
		super();

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}
	
	
	public static void main(String[] arg)
	{
		ImportDonHangSvl ddh = new ImportDonHangSvl();
		/*ddh.NhapHang("D:\\Phanam\\HCM\\Kho_59\\9 BK PHIEU NHAP NOI BO THANG 10 2014_2003.xls");*/
		
		ddh.NhapHang("D:\\Phanam\\HCM\\Kho_59\\9 BK PHIEU NHAP NOI BO THANG 10 2014_2003.xls");
		
		/*ddh.NhapHang_SoLo("D:\\Phanam\\Quay_Q10\\Phiếu nhập từ 01 - 13 09 2014.xls");*/
		
		/*ddh.XuatChuyen_NoiBo("D:\\Phanam\\HCM\\Kho_59\\9 BK PHIEU XUAT NOI BO THANG 10 2014_2003.xls", "100002");*/
	}
	
	private void NhapHang_SoLo(String fileName)
  {
		File inputWorkbook = new File(fileName);
		File output_Workbook = new File("D:\\importExcelFILE.xls");
		Workbook w;
		try
		{
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			
			WritableSheet w_sheet =null;
			WritableWorkbook workbook = jxl.Workbook.createWorkbook(output_Workbook);
			w_sheet = workbook.createSheet("importExcelFILE",1);
			
			int sodong = sheet.getRows();
			int socot = sheet.getColumns();
			
			System.out.println("So dong " + sodong + "  -- socot " + socot );
			
			dbutils db = new dbutils();
			String query = "";
			
			//sodong = 18;
			
			String nhaphangId="";
			String kbhId="100025";
			String khoId="100000";
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);
			
			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);
			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);

			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

			cellFormat.setBackground(jxl.format.Colour.GRAY_80);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);



			WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont);
			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
					

			
			w_sheet.addCell(new Label(0, 8, "Ngày",cellFormat));
			w_sheet.addCell(new Label(1, 8, "Mã CT",cellFormat));
			w_sheet.addCell(new Label(2, 8, "Số Chứng từ",cellFormat));
			w_sheet.addCell(new Label(3, 8, "Mã Khách Hàng",cellFormat));
			w_sheet.addCell(new Label(4, 8, "Mã vật tư",cellFormat));
			w_sheet.addCell(new Label(5, 8, "Tên vật tư",cellFormat));
			w_sheet.addCell(new Label(6, 8, "Số lô",cellFormat));
			w_sheet.addCell(new Label(7, 8, "Hạn dùng",cellFormat));
			w_sheet.addCell(new Label(8, 8, "Số lượng",cellFormat));
			
		
			String userId="100214";
			String nppId ="";
			query=" Select pk_Seq from nhaphanphoi where  timkiem like  '%KIEN-GIANG%' and TRANGTHAI=1  ";	
			ResultSet rs =db.get(query);
			while(rs.next())
			{
				nppId=rs.getString("pk_Seq");
			}
			
			for (int i=9; i < sodong ; i++)
			{
				String ngayHD="";
				Cell valueCell = sheet.getCell(0, i);  
				if (valueCell.getType() == CellType.DATE) 
				{  
					DateCell dCell = (DateCell) valueCell;  
	        TimeZone gmtZone = TimeZone.getTimeZone("GMT");  
	        DateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd");  
	        destFormat.setTimeZone(gmtZone);  
	        ngayHD = destFormat.format(dCell.getDate());  
				}   
				String SoHD = sheet.getCell(1, i).getContents().trim();
				String DienGiai = sheet.getCell(2, i).getContents().trim();
				String DonViTinh = sheet.getCell(3, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","").trim();
				String SoLuong = sheet.getCell(4, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","").trim();
				
				
				w_sheet.addCell(new Label(0, i, ngayHD,cellFormat2));
				w_sheet.addCell(new Label(1, i, SoHD,cellFormat2));
				w_sheet.addCell(new Label(2, i, DienGiai,cellFormat2));
				w_sheet.addCell(new Label(3, i, DonViTinh,cellFormat2));
				w_sheet.addCell(new Label(4, i, SoLuong,cellFormat2));
				
				
				String maSP="";
				Utility util = new Utility();
				
			
				if(ngayHD.length()>0 && SoHD.length()>0)
				{
					nhaphangId="";
					System.out.println("___NEW_______________"+DienGiai);
	
					query=
						"	insert into NHAPHANG(NGAYNHAN,NGAYCHUNGTU,NPP_FK,NCC_FK,KBH_FK,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,PhieuNhap) " +
						" select '"+ngayHD+"','"+ngayHD+"','"+nppId+"',100001 as nccId,'"+kbhId+"',1 as trangthai,"+userId+",'"+getDateTime()+"','"+userId+"','"+getDateTime()+"','"+SoHD+"'" +
						" where not exists (select 1 from nhaphang where PhieuNhap='"+SoHD+"' and npp_fk='"+nppId+"' and ngaynhan='"+ngayHD+"')   ";
					
					System.out.println("::::::::::"+query);
					
					int SoDong =db.updateReturnInt(query);
					if(SoDong==1)
					{
						query = "select SCOPE_IDENTITY() as  dhId ";
					} else if(SoDong==0) 
						query = "select pk_Seq as dhId from nhaphang where PhieuNhap='"+SoHD+"' and npp_fk='"+nppId+"' ";
					
					if(SoDong<=1)
					{
						rs = db.get(query);
						try
            {
	            rs.next();
	            nhaphangId = rs.getString("dhId");
	            if(rs!=null)rs.close();
            } catch (SQLException e)
            {
	            e.printStackTrace();
            }
					}
					
					query="delete from nhaphang_sp where nhaphang_fk='"+nhaphangId+"'";
					if(!db.update(query))
					{
						w_sheet.addCell(new Label(11, i,query,cellFormat2));
					}					 
				}
					
				if( DienGiai.indexOf("-") > 0 && SoLuong.trim().length() > 0) 
				{
					maSP = DienGiai.substring(0,DienGiai.indexOf("-")).trim().replace(".", "");
					w_sheet.addCell(new Label(12, i,nhaphangId));
					if(nhaphangId.length()>0)
					{
						int SoDong=0;
						query = "select COUNT(*) as SoDong from SanPham where MA='"+maSP+"' ";
						ResultSet	rsCHECK = db.get(query);
						if(rsCHECK.next())
						{
							SoDong=rsCHECK.getInt("SoDong");
						}
						if(rsCHECK!=null)rsCHECK.close();
						
						if(SoDong==0)
						{
							w_sheet.addCell(new Label(10, i,maSP,cellFormat2));
						}
						
						query="select count(*) as SoDong from NhapHang_Sp where nhaphang_Fk='"+nhaphangId+"' and sanpham_Fk =(select pk_Seq from sanpham where ma='"+maSP+"') and SoLO='NA' ";
						rs= db.get(query);
						
						while(rs.next())
						{
							SoDong=rs.getInt("SoDong");
						}
						if(rs!=null)rs.close();
						if(SoDong>0)
						{
							query= "update NhapHang_Sp set soluong=soluong+'"+SoLuong+"' where nhaphang_Fk='"+nhaphangId+"' and sanpham_fk=(select pk_Seq from sanpham where ma='"+maSP+"') and SoLo='NA' ";
							SoDong =db.updateReturnInt(query);
							if(SoDong<=0)
							{
								w_sheet.addCell(new Label(11, i,query,cellFormat2));
								System.out.println("DONHANG_SANPHAM"+query);
							}
						}
						{
							query=
							"insert into NHAPHANG_SP(NHAPHANG_FK,SANPHAM_FK,soluong,DONGIA,CHIETKHAU,DVDL_FK,LOAI,SCHEME,KHONHAN_FK,SOLO,NGAYHETHAN,soluongNHAN)"+
							"select '"+nhaphangId+"' as dhId,sp.pk_Seq as spId,'"+SoLuong+"' as soluong,0 as DonGia ,0 as ck,sp.dvdl_fk,0 as Loai,'' as sCHEME,100002 as kho_fk ,'NA' as SoLo,'2030-12-31','"+SoLuong+"' "+ 
							"from SANPHAM sp  where MA='"+maSP+"' ";
							SoDong =db.updateReturnInt(query);
							if(SoDong<=0)
							{
								w_sheet.addCell(new Label(11, i,query,cellFormat2));
								System.out.println("NHAPHANG_SP"+query);
							}
						}						
					}
				}
			}
			workbook.write();
			workbook.close();
			if(db!=null)db.shutDown();
			
			System.out.println("**************************CHAY XONG ROI********************************");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			
		}
	  
  }

	private void NhapHang(String fileName) 
	{
		File inputWorkbook = new File(fileName);
		File output_Workbook = new File("D:\\importExcelFILE.xls");
		Workbook w;
		try
		{
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			
			WritableSheet w_sheet =null;
			WritableWorkbook workbook = jxl.Workbook.createWorkbook(output_Workbook);
			w_sheet = workbook.createSheet("importExcelFILE",1);
			
			int sodong = sheet.getRows();
			int socot = sheet.getColumns();
			
			System.out.println("So dong " + sodong + "  -- socot " + socot );
			
			dbutils db = new dbutils();
			String query = "";
			
			//sodong = 18;
			
			String nhaphangId="";
			String kbhId="100025";
			String khoId="100002";
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);
			
			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);
			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);

			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

			cellFormat.setBackground(jxl.format.Colour.GRAY_80);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);



			WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont);
			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
					

			
			w_sheet.addCell(new Label(0, 8, "Ngày",cellFormat));
			w_sheet.addCell(new Label(1, 8, "Chứng từ",cellFormat));
			w_sheet.addCell(new Label(2, 8, "Diễn giải",cellFormat));
			w_sheet.addCell(new Label(4, 8, "Số lượng",cellFormat));
			w_sheet.addCell(new Label(3, 8, "Đơn giá",cellFormat));
			
		
			String userId="100173";
			String nppId ="";
			query=" Select pk_Seq from nhaphanphoi  where timkiem like  '%HO-CHI-MINH%' and TRANGTHAI=1  ";	
			ResultSet rs =db.get(query);
			while(rs.next())
			{
				nppId=rs.getString("pk_Seq");
			}
			
			for (int i=9; i < sodong ; i++)
			{
				String ngayHD="";
				Cell valueCell = sheet.getCell(0, i);  
				if (valueCell.getType() == CellType.DATE) 
				{  
					DateCell dCell = (DateCell) valueCell;  
	        TimeZone gmtZone = TimeZone.getTimeZone("GMT");  
	        DateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd");  
	        destFormat.setTimeZone(gmtZone);  
	        ngayHD = destFormat.format(dCell.getDate());  
				}   
				String SoHD = sheet.getCell(1, i).getContents().trim();
				String DienGiai = sheet.getCell(2, i).getContents().trim();
				String DonViTinh = sheet.getCell(3, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","").trim();
				String SoLuong = sheet.getCell(4, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","").trim();
				
				
				w_sheet.addCell(new Label(0, i, ngayHD,cellFormat2));
				w_sheet.addCell(new Label(1, i, SoHD,cellFormat2));
				w_sheet.addCell(new Label(2, i, DienGiai,cellFormat2));
				w_sheet.addCell(new Label(3, i, DonViTinh,cellFormat2));
				w_sheet.addCell(new Label(4, i, SoLuong,cellFormat2));
				
				
				String maSP="";
				Utility util = new Utility();
				
			
				if(ngayHD.length()>0 && SoHD.length()>0)
				{
					nhaphangId="";
					System.out.println("___NEW_______________"+DienGiai);
	
					query=
						"	insert into NHAPHANG(NGAYNHAN,NGAYCHUNGTU,NPP_FK,NCC_FK,KBH_FK,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,PhieuNhap) " +
						" select '"+ngayHD+"','"+ngayHD+"','"+nppId+"',100001 as nccId,'"+kbhId+"',1 as trangthai,"+userId+",'"+getDateTime()+"','"+userId+"','"+getDateTime()+"','"+SoHD+"'" +
						" where not exists (select 1 from nhaphang where PhieuNhap='"+SoHD+"' and npp_fk='"+nppId+"' and ngaynhan='"+ngayHD+"')   ";
					
					
					
					int SoDong =db.updateReturnInt(query);
					if(SoDong==1)
					{
						query = "select SCOPE_IDENTITY() as  dhId ";
					} else if(SoDong==0) 
						query = "select pk_Seq as dhId from nhaphang where PhieuNhap='"+SoHD+"' and npp_fk='"+nppId+"' and NGAYNHAN='"+ngayHD+"'  ";
					
					if(SoDong<=1)
					{
						rs = db.get(query);
						try
            {
	            rs.next();
	            nhaphangId = rs.getString("dhId");
	            if(rs!=null)rs.close();
            } catch (SQLException e)
            {
	            e.printStackTrace();
            }
					}
					System.out.println("_________"+nhaphangId);
					
					query="delete from nhaphang_sp where nhaphang_fk='"+nhaphangId+"'";
					if(!db.update(query))
					{
						w_sheet.addCell(new Label(11, i,query,cellFormat2));
					}					 
				}
				
				
					
				if( DienGiai.indexOf("-") > 0 && SoLuong.trim().length() > 0) 
				{
					maSP = DienGiai.substring(0,DienGiai.indexOf("-")).trim().replace(".", "");
					w_sheet.addCell(new Label(12, i,nhaphangId));
					
					System.out.println("_________"+maSP+"________"+SoLuong);
					
					if(nhaphangId.length()>0)
					{
						int SoDong=0;
						query = "select COUNT(*) as SoDong from SanPham where MA='"+maSP+"' ";
						ResultSet	rsCHECK = db.get(query);
						if(rsCHECK.next())
						{
							SoDong=rsCHECK.getInt("SoDong");
						}
						if(rsCHECK!=null)rsCHECK.close();
						
						if(SoDong==0)
						{
							w_sheet.addCell(new Label(10, i,maSP,cellFormat2));
						}
						
						query=
								"insert into NHAPHANG_SP(NHAPHANG_FK,SANPHAM_FK,soluong,DONGIA,CHIETKHAU,DVDL_FK,LOAI,SCHEME,KHONHAN_FK,SOLO,NGAYHETHAN,soluongNHAN)"+
								"select '"+nhaphangId+"' as dhId,sp.pk_Seq as spId,'"+SoLuong+"' as soluong,0 as DonGia ,0 as ck,sp.dvdl_fk,0 as Loai,'' as sCHEME,"+khoId+" as kho_fk ,'NA' as SoLo,'2030-12-31','"+SoLuong+"' "+ 
								"from SANPHAM sp  where MA='"+maSP+"' ";
								SoDong =db.updateReturnInt(query);
								if(SoDong<=0)
								{
									w_sheet.addCell(new Label(11, i,query,cellFormat2));
									System.out.println("NHAPHANG_SP"+query);
								}
						
						
					/*	query="select count(*) as SoDong from NhapHang_Sp where nhaphang_Fk='"+nhaphangId+"' and sanpham_Fk =(select pk_Seq from sanpham where ma='"+maSP+"') and SoLO='NA' ";
						rs= db.get(query);
						
						while(rs.next())
						{
							SoDong=rs.getInt("SoDong");
						}
						if(rs!=null)rs.close();
						if(SoDong>0)
						{
							query= "update NhapHang_Sp set soluong=soluong+'"+SoLuong+"',soluongNHAN=soluongNHAN+'"+SoLuong+"' where nhaphang_Fk='"+nhaphangId+"' and sanpham_fk=(select pk_Seq from sanpham where ma='"+maSP+"') and SoLo='NA' ";
							SoDong =db.updateReturnInt(query);
							System.out.println("DONHANG_SANPHAM"+query);
							if(SoDong<=0)
							{
								w_sheet.addCell(new Label(11, i,query,cellFormat2));
								
							}
						}*/
						{
						
						}						
					}
				}
			}
			workbook.write();
			workbook.close();
			if(db!=null)db.shutDown();
			
			System.out.println("**************************CHAY XONG ROI********************************");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			
		}
		
	}
	
	
	private void XuatChuyen_NoiBo(String fileName,String khoId) 
	{
		File inputWorkbook = new File(fileName);
		File output_Workbook = new File("D:\\importExcelFILE.xls");
		Workbook w;
		try
		{
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			
			WritableSheet w_sheet =null;
			WritableWorkbook workbook = jxl.Workbook.createWorkbook(output_Workbook);
			w_sheet = workbook.createSheet("importExcelFILE",1);
			
			int sodong = sheet.getRows();
			int socot = sheet.getColumns();
			
			System.out.println("So dong " + sodong + "  -- socot " + socot );
			
			dbutils db = new dbutils();
			String query = "";
			
			//sodong = 18;
			
			String nhaphangId="";
			String kbhId="100025";
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);
			
			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);
			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);

			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

			cellFormat.setBackground(jxl.format.Colour.GRAY_80);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);



			WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont);
			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
					

			
			w_sheet.addCell(new Label(0, 8, "Ngày",cellFormat));
			w_sheet.addCell(new Label(1, 8, "Chứng từ",cellFormat));
			w_sheet.addCell(new Label(2, 8, "Diễn giải",cellFormat));
			w_sheet.addCell(new Label(2, 8, "Số lượng",cellFormat));
			w_sheet.addCell(new Label(3, 8, "Đơn giá",cellFormat));
			
		
			
			String userId="100161";
			String nppId ="";
			query=" Select pk_Seq from nhaphanphoi where timkiem like '%HO-CHI-MINH%'  ";	
			ResultSet rs =db.get(query);
			while(rs.next())
			{
				nppId=rs.getString("pk_Seq");
			}
			
			Hashtable<String, String> htbDoiTac =getDoiTacId(nppId, db);
			
			
			for (int i=9; i < sodong ; i++)
			{
				String ngayHD="";
				Cell valueCell = sheet.getCell(0, i);  
				if (valueCell.getType() == CellType.DATE) 
				{  
					DateCell dCell = (DateCell) valueCell;  
	        TimeZone gmtZone = TimeZone.getTimeZone("GMT");  
	        DateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd");  
	        destFormat.setTimeZone(gmtZone);  
	        ngayHD = destFormat.format(dCell.getDate());  
				}   
				String SoHD = sheet.getCell(1, i).getContents().trim();
				String DienGiai = sheet.getCell(2, i).getContents().trim();
				String DonViTinh = sheet.getCell(3, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","").trim();
				String SoLuong = sheet.getCell(4, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","").trim();
				
				
				w_sheet.addCell(new Label(0, i, ngayHD,cellFormat2));
				w_sheet.addCell(new Label(1, i, SoHD,cellFormat2));
				w_sheet.addCell(new Label(2, i, DienGiai,cellFormat2));
				w_sheet.addCell(new Label(3, i, DonViTinh,cellFormat2));
				w_sheet.addCell(new Label(4, i, SoLuong,cellFormat2));
				
				
				String MaKho="";
				String maSP="";
				Utility util = new Utility();
				
			
				if(ngayHD.length()>0 && SoHD.length()>0)
				{
					nhaphangId="";
					System.out.println("___NEW_______________"+DienGiai);
					
					
					MaKho = DienGiai.substring(0,DienGiai.indexOf("-")).trim().replace(".", "");
					String	doitacId=htbDoiTac.get(MaKho);
	
					query=
						"INSERT INTO ERP_CHUYENKHONPP(NgayChuyen,KhoXuat_FK,TRANGTHAI,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,GHICHU,NPP_FK,NPP_DAT_FK,KBH_FK,SoChungTu,MaKho)" +
						" select '"+ngayHD+"','"+khoId+"',1 as TrangThai,'"+getDateTime()+"','"+userId+"','"+getDateTime()+"','"+userId+"', N'Import From Excel','"+nppId+"' ,'"+doitacId+"' as npp_DAT,'"+kbhId+"','"+SoHD+"','"+MaKho+"'  " +
						" where not exists (select 1 from ERP_CHUYENKHONPP where SoChungTu='"+SoHD+"' and npp_fk='"+nppId+"' and NgayChuyen='"+ngayHD+"')   ";
					
					System.out.println("::::::::::"+query);
					
					int SoDong =db.updateReturnInt(query);
					if(SoDong==1)
					{
						query = "select SCOPE_IDENTITY() as  dhId ";
					} else if(SoDong==0) 
						query = "select pk_Seq as dhId from ERP_CHUYENKHONPP where  SoChungTu='"+SoHD+"' and npp_fk='"+nppId+"' and NgayChuyen='"+ngayHD+"' ";
					
					if(SoDong<=1)
					{
						rs = db.get(query);
						try
            {
	            rs.next();
	            nhaphangId = rs.getString("dhId");
	            if(rs!=null)rs.close();
            } catch (SQLException e)
            {
	            e.printStackTrace();
            }
					}
					
					query="delete from ERP_CHUYENKHONPP_SANPHAM where chuyenkho_fk='"+nhaphangId+"'";
					if(!db.update(query))
					{
						w_sheet.addCell(new Label(11, i,query,cellFormat2));
					}					 
					query="delete from ERP_CHUYENKHONPP_SANPHAM_CHITIET where chuyenkho_fk='"+nhaphangId+"'";
					if(!db.update(query))
					{
						w_sheet.addCell(new Label(11, i,query,cellFormat2));
					}	
					
					
				}
					
				if( DienGiai.indexOf("-") > 0 && SoLuong.trim().length() > 0) 
				{
					maSP = DienGiai.substring(0,DienGiai.indexOf("-")).trim().replace(".", "");
					w_sheet.addCell(new Label(12, i,nhaphangId));
					if(nhaphangId.length()>0)
					{
						int SoDong=0;
						query = "select COUNT(*) as SoDong from SanPham where MA='"+maSP+"' ";
						ResultSet	rsCHECK = db.get(query);
						if(rsCHECK.next())
						{
							SoDong=rsCHECK.getInt("SoDong");
						}
						if(rsCHECK!=null)rsCHECK.close();
						
						if(SoDong==0)
						{
							w_sheet.addCell(new Label(10, i,maSP,cellFormat2));
						}
						
						/*query="select count(*) as SoDong from ERP_CHUYENKHONPP_SANPHAM where chuyenkho_fk='"+nhaphangId+"' and sanpham_Fk =(select pk_Seq from sanpham where ma='"+maSP+"') and SoLO='NA' ";
						rs= db.get(query);
						
						while(rs.next())
						{
							SoDong=rs.getInt("SoDong");
						}
						if(rs!=null)rs.close();
						if(SoDong>0)
						{
							query= "update ERP_CHUYENKHONPP_SANPHAM set soluongchuyen=soluongchuyen+'"+SoLuong+"' where chuyenkho_fk='"+nhaphangId+"' and sanpham_fk=(select pk_Seq from sanpham where ma='"+maSP+"') and SoLo='NA' ";
							SoDong =db.updateReturnInt(query);
							if(SoDong<=0)
							{
								w_sheet.addCell(new Label(11, i,query,cellFormat2));
								System.out.println("ERP_CHUYENKHONPP_SANPHAM"+query);
							}
						}*/
						{
							query=
							"insert into ERP_CHUYENKHONPP_SANPHAM(chuyenkho_fk,sanpham_fk,soluongchuyen,dongia,dvdl_fk,solo,ngaysanxuat,ngayhethan)"+
							"select '"+nhaphangId+"' as dhId,sp.pk_Seq,'"+SoLuong+"' as soluong,0 as DonGia,sp.dvdl_Fk,'NA' as SoLo,'','2030-12-31' "+ 
							"from SANPHAM sp  where MA='"+maSP+"' ";
							SoDong =db.updateReturnInt(query);
							if(SoDong<=0)
							{
								w_sheet.addCell(new Label(11, i,query,cellFormat2));
								System.out.println("ERP_CHUYENKHONPP_SANPHAM"+query);
							}
							
							query=
									"insert into ERP_CHUYENKHONPP_SANPHAM_CHITIET(chuyenkho_fk,SANPHAM_FK,solo,soluong,ngayhethan)"+
									"select '"+nhaphangId+"' as dhId,sp.pk_Seq,'NA','"+SoLuong+"' as soluong,'2030-12-31' "+ 
									"from SANPHAM sp  where MA='"+maSP+"' ";
									SoDong =db.updateReturnInt(query);
									if(SoDong<=0)
									{
										w_sheet.addCell(new Label(11, i,query,cellFormat2));
										System.out.println("ERP_CHUYENKHONPP_SANPHAM"+query);
									}
							
							
						}						
					}
				}
			}
			workbook.write();
			workbook.close();
			if(db!=null)db.shutDown();
			
			System.out.println("**************************CHAY XONG ROI********************************");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			
		}
		
	}
	
	public static Hashtable<String, String> getDoiTacId(String nppId,dbutils db )
	{
		Hashtable<String, String> htb = new Hashtable<String, String>();
		ResultSet rs = db.get("select MaKho,PK_SEQ,TEN from NHAPHANPHOI where TRUCTHUOC_FK='"+nppId+"'");
		try
		{
			while (rs.next())
			{
				htb.put(rs.getString("MaKho"), rs.getString("PK_SEQ"));
			}
			rs.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} 
		return htb;
	}

	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	
	
}
}
