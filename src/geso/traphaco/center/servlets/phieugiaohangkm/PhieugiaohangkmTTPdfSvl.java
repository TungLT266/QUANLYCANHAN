package geso.traphaco.center.servlets.phieugiaohangkm;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.distributor.beans.phieugiaohangkm.IPhieugiaohangkm;
import geso.traphaco.distributor.beans.phieugiaohangkm.imp.Phieugiaohangkm;
import geso.traphaco.distributor.beans.noptien.INoptien;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.PageSize;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhieugiaohangkmTTPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public PhieugiaohangkmTTPdfSvl()
	{
		super();
	}
	float CONVERT = 28.346457f;  // =1cm
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		// String userTen = (String) session.getAttribute("userTen");

		Utility util = new Utility();
		
		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String querystring = request.getQueryString();
		String id = util.antiSQLInspection(request.getParameter("pdf"));
		System.out.println("vay day: id:"+ id);
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));

		IPhieugiaohangkm pxkBean = new Phieugiaohangkm(id);
		pxkBean.setUserId(userId);

		
			String nppTEN = "";
			String ngaynhap="";
			String sodonhang="";
			String query = "select  dh.ngaydonhang, dh.PK_SEQ from ERP_DONDATHANG dh  where dh.PK_SEQ ="+id;					
			
			System.out.println(query);
			
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			try 
			{
				if(rs.next())
				{	
					nppTEN = "Tổng công ty TraPhaCo";
					ngaynhap = rs.getString("ngaydonhang");
					sodonhang = rs.getString("PK_SEQ");
				}
				rs.close();
			} 
			catch (Exception e) {}
			
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", " inline; filename=PhieuGiaoHang.pdf");

				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();
				
				System.out.println("sodonhang"+sodonhang);
				try{
					this.CreatePhieuGiaoHang(document, outstream, id,  nppTEN, ngaynhap, sodonhang);
					
					query ="UPDATE DONHANG SET InPhieuGiaoHangKM = 1 where PK_SEQ="+id;
					if(!db.update(query))
					{
						
					}
				}
				catch(Exception e) {}
				
				
				db.shutDown();
			
		}



	
	private String getDate()
	{			
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        dateFormat.format(date);
	        
/*		String arr[] = date.split("-");
		String nam = arr[0];
		String thang = arr[1];
		String ngay = arr[2];*/
		
		return dateFormat.format(date);
	}

	


	private void CreatePhieuGiaoHang(Document document, ServletOutputStream outstream, String id, String nppTen, String ngaynhap, String sodonhang)
	{
		try
		{	
			dbutils db = new dbutils();
			document.setPageSize(PageSize.A4);
			
			String[] ngayDH = ngaynhap.split("-");
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//lay doi tuong khach hang
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			//BaseFont bfTimes = BaseFont.CreateFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, false);
			
			/*Font font = new Font(FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD, BaseColor.BLACK);
			Font font2 = new Font(FontFamily.TIMES_ROMAN, 8.0f, Font.BOLD, BaseColor.BLACK);
			Font fontnomar = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.NORMAL, BaseColor.BLACK);*/
			
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 12, Font.BOLD);
			Font fontnomar = new Font(bf, 12,Font.NORMAL);

			//font2.setColor(BaseColor.GREEN);
			 //KHAI BAO 1 BANG CO BAO NHIEU COT
			
			PdfPTable tableheader = new PdfPTable(1);
			tableheader.setWidthPercentage(100);
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {300.0f};
			tableheader.setWidths(withsheader); 
			
			Image hinhanh = Image.getInstance( getServletContext().getInitParameter("path") + "/logo.gif");	
			hinhanh.scalePercent(70);
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			
			PdfPCell cellslogo = new PdfPCell(hinhanh);
			cellslogo.setPadding(10);
			cellslogo.setBorder(0);
			tableheader.addCell(cellslogo);
			
			cellslogo = new PdfPCell( new Paragraph(nppTen , fontnomar) );
			cellslogo.setPadding(5);
			cellslogo.setBorder(0);
			tableheader.addCell(cellslogo);
			
			
			Paragraph pxk = new Paragraph("PHIẾU GIAO HÀNG KHUYẾN MẠI", font);
			pxk.setSpacingAfter(2);
			pxk.setAlignment(Element.ALIGN_CENTER);
			
			PdfPCell celltieude = new PdfPCell();
			celltieude.addElement(pxk);
			Paragraph dvbh = new Paragraph("(Kèm theo Đơn hàng số " +sodonhang+ " Ngày "+ ngayDH[2]+" tháng "+ ngayDH[1]+" năm " + ngayDH[0] + " )" , fontnomar);
			dvbh.setSpacingAfter(3);
			dvbh.setAlignment(Element.ALIGN_CENTER);
			celltieude.addElement(dvbh);
			celltieude.setBorder(0);
			
			tableheader.addCell(celltieude);
			document.add(tableheader);						
			
			
			
			PdfPTable table_info = new PdfPTable(1);
			float[] with3 = {300.0f};
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();
			
			String tenkh = "";
			String diachikh = "";
			String masothuekh = "";
			String sql_getinfokh = "select ten,isnull(dienthoai,'Chua xac dinh') as dienthoai,isnull(diachi,'Chua xac dinh') as diachi," +
								   "	isnull(masothue,'Chua xac dinh') as masothue from nhaphanphoi " +
								   "where pk_seq = ( select npp_fk from ERP_DONDATHANG where pk_seq = '" + id + "' ) ";
			
			ResultSet rs_kh = db.get(sql_getinfokh);
			try
			{
				if(rs_kh.next())
				{
					tenkh = rs_kh.getString("ten");
					diachikh = rs_kh.getString("diachi");
					masothuekh = rs_kh.getString("masothue");
				}
				rs_kh.close();
			}
			catch(Exception er ){
				System.out.println("Loi Khach Hang : " + er.toString());
			}

			cell111.addElement(new Paragraph("Tên khách hàng : " + tenkh, fontnomar));
			cell111.setBorder(0);
			
			cell111.addElement(new Paragraph("Địa chỉ : " + diachikh, fontnomar));
			cell111.setBorder(0);
			
			cell111.addElement(new Paragraph("Mã số thuế : " + masothuekh, fontnomar));
			cell111.setBorder(0);

			cell111.setPaddingBottom(10);
			table_info.addCell(cell111);
			
			
			document.add(table_info);
			//Table Content
			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {5.0f, 45.0f, 7.0f, 9.0f, 9.0f, 9.0f}; 	

	        table.setWidths(withs);
			String[] th = new String[]{ "STT", "Tên hàng hoá, dịch vụ", "ĐVT", "Số lượng", "Đơn giá ", "Thành tiền"};
			PdfPCell[] cell = new PdfPCell[6];
			for(int i = 0; i < 6 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);	
				cell111.setPadding(2);
				table.addCell(cell[i]);			
			}
			
			String query = 
			"	SELECT dh.PK_SEQ, dh_km.SPMA MA, sp.TEN, dh_km.SOLUONG,dvdl.DONVI, 0 DONGIA, 0 THANHTIEN \n"+
			"	FROM ERP_DONDATHANG dh INNER JOIN ERP_DONDATHANG_CTKM_TRAKM dh_km on dh.PK_SEQ = dh_km.DONDATHANGID \n"+
			"			INNER JOIN SANPHAM sp on dh_km.SPMA = sp.MA \n"+
			"	     INNER JOIN CTKHUYENMAI km on km.PK_SEQ = dh_km.CTKMID \n"+
			"	     LEFT JOIN DONVIDOLUONG dvdl on dvdl.PK_SEQ = sp.DVDL_FK \n"+
			"	WHERE dh.TRANGTHAI != 2  and  dh.PK_SEQ='"+id+"' \n";
			
			System.out.println(query);
			
			int sl =0;
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				PdfPCell cellSP = null;
				
				int stt = 1;
				NumberFormat format = new DecimalFormat("#,###,###");
				while(rs.next())
				{
					double soluong = rs.getDouble("SOLUONG");
					sl+= soluong;
					double dongia = rs.getDouble("donGIA");
					
					cellSP = new PdfPCell(new Paragraph(Integer.toString(stt), font2));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);		
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("TEN"), font2));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("DONVI"), font2));
					cellSP.setPadding(3.0f);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(format.format(soluong) , font2));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(format.format(dongia) , font2));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					cellSP = new PdfPCell(new Paragraph(format.format(0) , font2));
					cellSP.setPadding(3.0f);
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);	
					
					stt++;
				}
				rs.close();
				
				
				cellSP = new PdfPCell(new Paragraph("", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);		
/*				
				cellSP = new PdfPCell(new Paragraph("GGCĐ (Chiết khấu bán hàng ngay)", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("" , font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("" , font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph(format.format(totalCK) , font2));
				cellSP.setPadding(3.0f);
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellSP);	*/
				
				stt ++;

				cellSP = new PdfPCell(new Paragraph("CỘNG", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("", font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph(Integer.toString(sl) , font2));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph("" , font2));
				cellSP.setPadding(3.0f);
				table.addCell(cellSP);	
				
				cellSP = new PdfPCell(new Paragraph(format.format(0) , font2));
				cellSP.setPadding(3.0f);
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellSP);	
				
			}
			
			document.add(table);
			
			//Thêm
			PdfPTable tablethongtinD =new PdfPTable(2);//số cột
			tablethongtinD.setWidthPercentage(100);//độ giãn của cột
			float[] withthongtinKT = { 100.0f, 100.0f};
			tablethongtinD.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tablethongtinD.setWidths(withthongtinKT); //khai báo	
						
			PdfPCell cell25 =  new PdfPCell();
			cell25.addElement(new Paragraph(" ", fontnomar));
			cell25.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell25.setBorder(0);
			tablethongtinD.addCell(cell25);
			
			PdfPCell cell26 =  new PdfPCell();
			cell26 =  new PdfPCell();
			cell26.addElement(new Paragraph(" ", fontnomar));
			cell26.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell26.setBorder(0);
			tablethongtinD.addCell(cell26);
			
			PdfPCell cell27 =  new PdfPCell();
			Paragraph para = new Paragraph("KHÁCH HÀNG", fontnomar);;
			/*td.setAlignment(Element.ALIGN_CENTER);*/
			cell27.setPaddingLeft(2.5f*CONVERT);
			cell27.addElement(para);
			cell27.setBorder(0);
			tablethongtinD.addCell(cell27);
			
			PdfPCell cell28 =  new PdfPCell();
			Paragraph para2 = new Paragraph("NGƯỜI LẬP BẢNG KÊ", fontnomar);;
			/*td.setAlignment(Element.ALIGN_CENTER);*/
			cell28.setPaddingLeft(2.5f*CONVERT);
			cell28.addElement(para2);
			cell28.setBorder(0);
			tablethongtinD.addCell(cell28);
			
			//hết
			/*PdfPTable tablefooter = new PdfPTable(2);
			tablefooter.setWidthPercentage(100);
			tablefooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] withfooterder = {150.0f,300.0f};
			tablefooter.setWidths(withfooterder); 
			
			//nguoimua hang 
			
			PdfPCell cell11 = new PdfPCell();
			cell11.addElement(new Paragraph(" ", fontnomar));
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setBorder(0);
			tablefooter.addCell(cell11);
			
			cell11 = new PdfPCell();
			cell11.addElement(new Paragraph(" ", fontnomar));
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setBorder(0);
			tablefooter.addCell(cell11);
			
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setBorder(0);
			Paragraph para = new Paragraph("NGƯỜI LẬP BẢNG KÊ", fontnomar);
			para.setAlignment(Element.ALIGN_RIGHT);

			
			para.add("\n");
			para.add("\n");
			
			cell11.addElement(para);*/
			/*tablefooter.addCell(cell11);
			
			
			PdfPCell cell12 = new PdfPCell();
			cell12.addElement(new Paragraph(" ", fontnomar));
			cell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell12.setBorder(0);
			tablefooter.addCell(cell12);
			
			cell12 = new PdfPCell();
			cell12.addElement(new Paragraph(" ", fontnomar));
			cell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell12.setBorder(0);
			tablefooter.addCell(cell11);
			
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setBorder(0);
			Paragraph para = new Paragraph("NGƯỜI LẬP BẢNG KÊ", fontnomar);
			para.setAlignment(Element.ALIGN_RIGHT);*/
			
			document.add(tablethongtinD);
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}



	
	private String getSTT(int stt)
	{
		if (stt < 10)
			return "000" + Integer.toString(stt);
		if (stt < 100)
			return "00" + Integer.toString(stt);
		if (stt < 1000)
			return "0" + Integer.toString(stt);
		return Integer.toString(stt);
	}
	
	public static void main(String[] arg)
	{
		//CHAY LAI HOA DON   --HA DONG: 106174,  HA NOI: 100002 --> BEN THAT CHAY 2 NHA TRUOC, 3 THANG 7, 8, 9
		/*String nppID = "106182, 100010";
		int thang = 8;
		int thang_to = 9;
		int nam = 2014;
		
		dbutils db = new dbutils();
		String query = "select pk_seq from HOADON " +
					   "where NPP_FK in ( " + nppID + " ) and month(ngayxuatHD) >= '" + thang + "' and month(ngayxuatHD) <= '" + thang_to + "' " +
					   		" and year(ngayxuatHD) = '" + nam + "'  and trangthai not in ( 3, 5 ) and THOIDIEMTAO_HD is NULL ";
		System.out.println("---LAY HOA DON: " + query );
		ResultSet rsHD = db.get(query);
		
		try 
		{
			//db.getConnection().setAutoCommit(false);
			
			if(rsHD != null)
			{
				while(rsHD.next())
				{
					String hoadonID = rsHD.getString("pk_seq");
					
					CapNhatHoaDon(hoadonID, db);
				}
				rsHD.close();
			}
			
			db.getConnection().setAutoCommit(true);
			
			//CAP NHAT LAI
			query = "update hd set tongtienavat = a.AVAT, tongtienbvat = a.BVAT, vat = a.VAT, chietkhau = a.chietkhau   " +
					"from  " +
					"(  " +
					"	select hd.PK_SEQ AS HDID, HOADON_CT.tongtien as BVAT, HOADON_CT.Chietkhau as chietkhau,  " +
					"		 ( HOADON_CT.VAT - HOADON_CT. VAT_KM) as VAT,  " +
					"		  round((HOADON_CT.tongtien + HOADON_CT.VAT ) - ( HOADON_CT.Chietkhau  + HOADON_CT.VAT_KM), 0) AS AVAT  " +
					"	from HOADON hd inner join  " +
					"	(      " +
					"		select AA.HOADON_FK, AA.tongtien, AA .VAT, BB.Chietkhau, BB.VAT as VAT_KM  " +
					"		from  " +
					"		(  " +
					"		   select HOADON_fk ,  SUM( SOLUONG * DONGIA ) as tongtien , SUM (SOLUONG * DONGIA * VAT /100) as VAT   " +
					"		   from HOADON_SP  " +
					"		   group by HOADON_FK  " +
					"		 )   " +
					"		 AA inner join  " +
					"		 (  " +
					"			select hoadon_fk, sum(chietkhau) as chietkhau, sum(chietkhau * thueVAT / 100) as VAT  " +
					"			from HOADON_CHIETKHAU    " +
					"			group by hoadon_fk  " +
					"		)   " +
					"		BB  ON AA. HOADON_FK=BB .HOADON_FK   " +
					"	)   " +
					"	HOADON_CT on hd.PK_SEQ = HOADON_CT. HOADON_FK  " +
					"       left join KHACHHANG kh on kh .PK_SEQ= hd.KHACHHANG_FK  " +
					"	where  LOAIHOADON = 0 and month(ngayxuatHD) >= '" + thang + "' and month(ngayxuatHD) <= '" + thang_to + "' " +
							"	and year(ngayxuatHD) = '" + nam + "' and THOIDIEMTAO_HD is NULL  " +
					")  " +
					"as a inner join HOADON hd on hd .PK_SEQ = a.HDID  " +
					"where  hd.NPP_FK in (  " + nppID + "  ) and hd.THOIDIEMTAO_HD is NULL  " ;
			
			db.update(query);
			
			System.out.println("-------------CHAY XONG ROI.............");
			//db.getConnection().commit();
			
		} 
		catch (Exception e) 
		{
			try {
				db.getConnection().rollback();
			} 
			catch (SQLException e1) { }
			
			e.printStackTrace();
			System.out.println("---LOI ROI...........");
		}*/
		
	}


	
	
	private String DinhDangTRAPHACO(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}
	
	private int Songaytrongthang(int month, int year) 
    {
        int ngay = 0;
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                {
                    ngay = 31;
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                {
                    ngay = 30;
                    break;
                }
            case 2:
                {
                    if (year % 4 == 0)
                        ngay = 29;
                    else
                        ngay = 28;
                    break;
                }
        }

        return ngay;
    }

}
