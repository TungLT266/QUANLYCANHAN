package geso.traphaco.distributor.servlets.hangtralainpp;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.distributor.beans.phieugiaohangkm.IPhieugiaohangkm;
import geso.traphaco.distributor.beans.phieugiaohangkm.imp.Phieugiaohangkm;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/ErpHangTraLaiNppPdfSvl")
public class ErpHangTraLaiNppPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ErpHangTraLaiNppPdfSvl()
	{
		super();
		
	}
	
	float CONVERT = 28.346457f; // =1cm
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		
		Utility util = new Utility();
		
		if (userId.length() == 0)
			userId = request.getParameter("userId");
		
		String id = util.antiSQLInspection(request.getParameter("pdf"));
		System.out.println("vay day: id:" + id);
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", " inline; filename=NhapHangBanTraLai.pdf");
		
		Document document = new Document();
		ServletOutputStream outstream = response.getOutputStream();
		
		try
		{
			this.CreatePhieuGiaoHang(document, outstream, id);
			
		} catch (Exception e)
		{
		}
	}
	
	private String getDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		dateFormat.format(date);
		return dateFormat.format(date);
	}
	
	private void CreatePhieuGiaoHang(Document document, ServletOutputStream outstream, String id)
	{
		try
		{
			dbutils db = new dbutils();
			document.setPageSize(PageSize.A4);
			document.setMargins(1.0f*CONVERT, 1f*CONVERT, 1.0f*CONVERT, 1.0f*CONVERT);
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			Font font = new Font(bf, 16, Font.BOLD);
			Font font2 = new Font(bf, 12, Font.BOLD);
			Font fontnomar = new Font(bf, 12, Font.NORMAL);
			
			PdfPTable tableheader = new PdfPTable(1);
			tableheader.setWidthPercentage(100);
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader =
			{
				300.0f
			};
			tableheader.setWidths(withsheader);
			
			String query=
				"	select a.GhiChu,b.MA as nppMa,b.TEN as nppTEN,b.DIACHI as nppDIACHI,a.ngaytra,A.SoHoaDon,A.KyHieu,b.MaFAST,b.MaKho,b.MaNX,b.XUATTAIKHO,  "+
				"		isnull(c.maFAST,d.mafast) as khMA,isnull(c.TEN,d.ten) as khTEN,isnull(c.DIACHI,d.diachi) as khDIACHI,isnull(a.so,'') as so  "+
				"	from Erp_HangTraLaiNpp a inner join NHAPHANPHOI b on b.PK_SEQ=a.npp_fk  "+
				"		left  join KHACHHANG c on c.PK_SEQ=a.khachhang_fk  "+
				"		left  join NHAPHANPHOI d on d.PK_SEQ=a.npptra_fk  "+
				"	where a.pk_seq='"+id+"'  ";
			System.out.println("cau query vao day" + query);
			ResultSet rs =db.getScrol(query);

			while(rs.next())
			{
					String[]	 ngayDH=rs.getString("NgayTra").split("-");
				 
					PdfPCell cellslogo = new PdfPCell(new Paragraph(rs.getString("nppTEN"), fontnomar));
					cellslogo.setPadding(5);
					cellslogo.setBorder(0);
					tableheader.addCell(cellslogo);
					
					cellslogo = new PdfPCell(new Paragraph(rs.getString("nppDIACHI"), fontnomar));
					cellslogo.setPadding(5);
					cellslogo.setBorder(0);
					tableheader.addCell(cellslogo);

					Paragraph pxk = new Paragraph("PHIẾU NHẬP HÀNG BÁN BỊ TRẢ LẠI", font);
					pxk.setSpacingAfter(2);
					pxk.setAlignment(Element.ALIGN_CENTER);
					

					PdfPCell celltieude = new PdfPCell();
					celltieude.addElement(pxk);
					Paragraph dvbh = new Paragraph("                                                              "+"Ngày " + ngayDH[2] + " tháng " + ngayDH[1] + " năm " + ngayDH[0] + "                             Số:              "+rs.getString("so"), fontnomar);
					dvbh.setSpacingAfter(3);
					dvbh.setAlignment(Element.ALIGN_LEFT);
					celltieude.addElement(dvbh);
					celltieude.setBorder(0);
					
					tableheader.addCell(celltieude);

			}
			document.add(tableheader);
			
			//test table
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			table1.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs1 ={7.0f, 40.0f};
			table1.setWidths(withs1);
			PdfPCell cellSP1 = null;
			rs.beforeFirst();
			while(rs.next()){
				//cell 1
				System.out.println("ten khach hang"+rs.getString("khTEN"));
				cellSP1 = new PdfPCell(new Paragraph("Người giao hàng: ", fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				
				cellSP1 = new PdfPCell(new Paragraph(" ", fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				//cell 2
				cellSP1 = new PdfPCell(new Paragraph("Đơn vị:", fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				
				cellSP1 = new PdfPCell(new Paragraph(rs.getString("khTEN"), fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				//cell 3
				cellSP1 = new PdfPCell(new Paragraph("Địa chỉ:", fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				
				cellSP1 = new PdfPCell(new Paragraph(rs.getString("khDIACHI"), fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				//cell 4
				PdfPTable tables = new PdfPTable(6);
				tables.setWidthPercentage(100);
				tables.setHorizontalAlignment(Element.ALIGN_LEFT);
				float[] withss ={1.0f, 0.8f,0.5f,1.0f,0.5f,2.0f};
				tables.setWidths(withss);
				PdfPCell cellSPs = null;
				
				cellSPs = new PdfPCell(new Paragraph("Số hóa đơn", fontnomar));
				cellSPs.setBorder(0);
				tables.addCell(cellSPs);
				
				cellSPs = new PdfPCell(new Paragraph(rs.getString("SoHoaDon"), fontnomar));
				cellSPs.setBorder(0);
				tables.addCell(cellSPs);
				
				cellSPs = new PdfPCell(new Paragraph("Seri :", font2));
				cellSPs.setBorder(0);
				tables.addCell(cellSPs);
				
				cellSPs = new PdfPCell(new Paragraph((rs.getString("kyhieu")==null?"":rs.getString("kyhieu").toUpperCase()), font2));
				cellSPs.setBorder(0);
				tables.addCell(cellSPs);
				
				cellSPs = new PdfPCell(new Paragraph("Ngày :", font2));
			
				cellSPs.setBorder(0);
				tables.addCell(cellSPs);
				
				cellSPs = new PdfPCell(new Paragraph(rs.getString("ngaytra"), fontnomar));
				cellSPs.setBorder(0);
				tables.addCell(cellSPs);
				
				document.add(tables);
				/*cellSPs = new PdfPCell(new Paragraph(rs.getString("SoHoaDon")+ "      Seri:   "+rs.getString("kyhieu") +"       Ngày:    " +rs.getString("ngaytra"), fontnomar));
				cellSPs.setPadding(0.1f * CONVERT);
				cellSPs.setBorder(0);
				tables.addCell(cellSP1);*/
				//cell 5
				cellSP1 = new PdfPCell(new Paragraph("Nội dung:", fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				
				cellSP1 = new PdfPCell(new Paragraph(rs.getString("GhiChu"), fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				//cell 6
				cellSP1 = new PdfPCell(new Paragraph("Tài khoản có:", fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				
				cellSP1 = new PdfPCell(new Paragraph(rs.getString("MaNX")+" Phải thu của khách hàng tại "+rs.getString("khDIACHI"), fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				//cell 7
				cellSP1 = new PdfPCell(new Paragraph("Nhập lại kho:", fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				
				cellSP1 = new PdfPCell(new Paragraph(rs.getString("XuatTaiKho"), fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				
				cellSP1 = new PdfPCell(new Paragraph("", fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				
				cellSP1 = new PdfPCell(new Paragraph("", fontnomar));
				cellSP1.setPadding(0.1f * CONVERT);
				cellSP1.setBorder(0);
				table1.addCell(cellSP1);
				
			}
			document.add(table1);
			//end test table
			
			// Table Content
			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs ={8.0f, 34.0f, 7.0f, 7.0f, 9.0f, 14.0f, 14.0f};
			
			table.setWidths(withs);
			String[] th = new String[]
			{
			"Mã Kho", "Tên vật tư", "TK", "Đơn vị", "Số lượng ", "Đơn giá ", "Thành tiền"
			};
			PdfPCell[] cell = new PdfPCell[7];
			for (int i = 0; i < 7; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
				table.addCell(cell[i]);
			}
			
			 query = 
			"	select d.MaKho,e.TKVT,e.ma as spMa,e.ten as spTen,f.DONVI,sum((b.SoLuong)) as SoLuong,sum((round(b.SoLuong*b.DonGia,0))) as ThanhTien ,sum((round(b.SoLuong*b.DonGia,0) * b.vat/100.0)) as VAT	,sum((round(b.SoLuong*b.DonGia,0))/b.SoLuong) as DonGia	from Erp_HangTraLaiNpp a inner join Erp_HangTraLaiNpp_SanPham b on b.HangTraLai_fk=a.pk_seq "+ 			
			"			inner join NHAPHANPHOI d on d.PK_SEQ=a.npp_fk  		 "+	
			"			inner join SANPHAM e on e.PK_SEQ=b.Sanpham_fk  "+
			"			inner join DONVIDOLUONG f on f.PK_SEQ=b.Dvdl_Fk "+	
			"		where a.pk_seq='"+id+"' 	 "+
			"		group by d.MaKho,e.TKVT,e.ma,e.ten,f.DONVI,b.soluong,b.dongia 	 ";
			
			System.out.println(query);
			int total = 0;
			double tienvat = 0;
			rs = db.get(query);
			int SoDong=0;
			if (rs != null)
			{
				PdfPCell cellSP = null;
				NumberFormat format = new DecimalFormat("#,###,###");
				NumberFormat format2 = new DecimalFormat("#,###,###.####");
				while (rs.next())
				{
					SoDong++;
					double soluong = rs.getDouble("SOLUONG");
					double dongia = rs.getDouble("donGIA");
					double vat = rs.getDouble("vat");
					
					total+=rs.getDouble("ThanhTien");
					tienvat+=rs.getDouble("VAT");
					cellSP = new PdfPCell(new Paragraph(rs.getString("makho"), fontnomar));
					cellSP.setPadding(0.1f * CONVERT);
					table.addCell(cellSP);
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("spMa") + " - " + rs.getString("spTen"), fontnomar));
					cellSP.setPadding(0.1f * CONVERT);
					table.addCell(cellSP);
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("tkvt"), fontnomar));
					cellSP.setPadding(0.1f * CONVERT);
					table.addCell(cellSP);
					
					cellSP = new PdfPCell(new Paragraph(rs.getString("DONVI"), fontnomar));
					cellSP.setPadding(0.1f * CONVERT);
					table.addCell(cellSP);
					
					cellSP = new PdfPCell(new Paragraph(format.format(soluong), fontnomar));
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellSP.setPadding(0.1f * CONVERT);
					table.addCell(cellSP);
					
					cellSP = new PdfPCell(new Paragraph(format2.format(dongia), fontnomar));
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellSP.setPadding(0.1f * CONVERT);
					table.addCell(cellSP);
					
					cellSP = new PdfPCell(new Paragraph(format.format(rs.getDouble("ThanhTien")), fontnomar));
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellSP.setPadding(0.1f * CONVERT);
					table.addCell(cellSP);
					
				}
				rs.close();
				
				for (int i = SoDong; i < 17; i++)
        {
					cellSP = new PdfPCell(new Paragraph(" ", fontnomar));
					table.addCell(cellSP);
					
					cellSP = new PdfPCell(new Paragraph(" ", fontnomar));
					table.addCell(cellSP);
					
					cellSP = new PdfPCell(new Paragraph(" ", fontnomar));
					table.addCell(cellSP);
					
					
					cellSP = new PdfPCell(new Paragraph(" ", fontnomar));
					table.addCell(cellSP);
					
					cellSP = new PdfPCell(new Paragraph( "", fontnomar));
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);
					
					cellSP = new PdfPCell(new Paragraph(" ", fontnomar));
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);
					
					cellSP = new PdfPCell(new Paragraph(" ", fontnomar));
					cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cellSP);
				
        }
				
				cellSP = new PdfPCell(new Paragraph("Tổng cộng tiền hàng ", fontnomar));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setColspan(6);
				table.addCell(cellSP);
				
				cellSP = new PdfPCell(new Paragraph(format.format(total), fontnomar));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellSP);
				
				
				cellSP = new PdfPCell(new Paragraph("Thuế giá trị gia tăng ", fontnomar));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setColspan(6);
				table.addCell(cellSP);
				
				cellSP = new PdfPCell(new Paragraph(format.format(tienvat), fontnomar));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellSP);
				
				
				cellSP = new PdfPCell(new Paragraph("Tổng cộng tiền thanh toán", font2));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellSP.setColspan(6);
				table.addCell(cellSP);
				
				cellSP = new PdfPCell(new Paragraph(format.format(total+tienvat), font2));
				cellSP.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellSP);
			
			}
			document.add(table);
			NumberFormat formatter = new DecimalFormat("#,###,###");
			doctienrachu doctien=new doctienrachu();
			//int tien=total+ Integer.parseInt(String.valueOf(tienvat));
			
			

			String tmp = formatter.format((tienvat+ Double.parseDouble(String.valueOf(total)))) ;
			
			
			String sotien=doctien.tranlate( tmp.replace(",", "")); 
			
			
			Paragraph p = new Paragraph("Bằng chữ: "+sotien.toUpperCase().charAt(0)+sotien.substring(1), fontnomar);
			p.setSpacingBefore(0.3f * CONVERT);
			p.setSpacingAfter(0.2f * CONVERT);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
		
			
			
			p = new Paragraph("Nhập ngày.....tháng....năm....", fontnomar);
			p.setSpacingBefore(0.1f * CONVERT);
			p.setSpacingAfter(0.1f * CONVERT);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			
			p = new Paragraph("", fontnomar);
			p.setSpacingBefore(0.1f * CONVERT);
			p.setSpacingAfter(0.1f * CONVERT);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			
			Font bold = new Font(bf, 9, Font.NORMAL);
			Font itatic = new Font(bf, 9, Font.NORMAL);
			
			 withs = new float[5];
			for (int i = 0; i < 5; i++)
			{
				switch (i)
				{
				case 0:
					withs[i] = 20.0f;
					break;
				case 1:
					withs[i] = 15.0f;
					break;
				case 2:
					withs[i] = 10.0f;
					break;
				case 3:
					withs[i] = 20.0f;
					break;
				case 4:
					withs[i] = 25.0f;
					break;
				case 5:
					withs[i] = 20.0f;
					break;
				default:
					withs[i] = 20.0f;
					break;
				}
			}
			
			table = new PdfPTable(withs.length);
			table.setWidths(withs);
			table.setWidthPercentage(100);
			
			String[] spTitles ={ "PHỤ TRÁCH KINH DOANH", "NGƯỜI LẬP PHIẾU", "THỦ KHO", "TL KẾ TOÁN TRỦỜNG", "TL GIÁM ĐỐC CÔNG TY" };
				for (int i = 0; i < spTitles.length; i++)
				{
					cell[i] = new PdfPCell(new Paragraph(spTitles[i], bold));
					cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[i].setVerticalAlignment(Element.ALIGN_CENTER);
					cell[i].setBorder(0);
					table.addCell(cell[i]);
				}
				spTitles =new String[]{ "(Ký,Họ Tên)", "(Ký,Họ Tên)", "(Ký,Họ Tên)", "(Ký,Họ Tên)", "(Ký,Họ Tên)" };
				for (int i = 0; i < spTitles.length; i++)
				{
					cell[i] = new PdfPCell(new Paragraph(spTitles[i], itatic));
					cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[i].setBorder(0);
					table.addCell(cell[i]);
				}
				document.add(table);
			
		
			document.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
	
}
