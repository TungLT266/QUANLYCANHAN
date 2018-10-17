package geso.traphaco.distributor.servlets.donhangtrave;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.distributor.beans.donhangtrave.IDonhangtrave;
import geso.traphaco.distributor.beans.donhangtrave.ISanpham;
import geso.traphaco.distributor.beans.donhangtrave.imp.Donhangtrave;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class DonhangtravePdfSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public DonhangtravePdfSvl() {
        super();
    }
    dbutils db = new dbutils();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/Tomi/index.jsp");
		}
		else
		{
			Utility util=new Utility();
			String querystring=request.getQueryString();
			String id = util.getId(querystring);
			userId=util.getUserId(querystring);
			
			IDonhangtrave dhtvBean = new Donhangtrave(id);
			dhtvBean.setUserId(userId);
			dhtvBean.init();
			
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition"," inline; filename=DonHangTraVe.pdf");
			
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			
			this.CreatePdf(document, outstream, dhtvBean);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}
	private void CreatePdf(Document document, ServletOutputStream outstream, IDonhangtrave dhtvBean) throws IOException
	{
		try
		{
			String ddkdTen = "";
			ResultSet rs = dhtvBean.getDdkdList();
			try{ 
				while(rs.next()){
		    		if(rs.getString("ddkdId").equals(dhtvBean.getDdkdId())){
		    			ddkdTen = rs.getString("ddkdTen");
		    			break;
		    		}
				}
				rs.close();
			}
			catch(java.sql.SQLException e){ }
			
			
			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//lay doi tuong khach hang
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 9, Font.BOLD);
			Font fontnomar=new Font(bf,10,Font.NORMAL);
			//font2.setColor(BaseColor.GREEN);
			 //KHAI BAO 1 BANG CO BAO NHIEU COT
			
			PdfPTable tableheader=new PdfPTable(2);
			tableheader.setWidthPercentage(100);//chieu dai cua báº£ng 
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {50.0f, 30.0f};//SET DO RONG CAC COT
			tableheader.setWidths(withsheader);
			
			Paragraph pxk = new Paragraph("ĐƠN HÀNG TRẢ VỀ", font);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			PdfPCell celltieude = new PdfPCell();
			celltieude.addElement(pxk);
						
			//Paragraph dvbh = new Paragraph("Nhà phân phối: " + tennpp, fontnomar1);
			PdfPTable table2 =new PdfPTable(2);
			table2.setWidthPercentage(100);//chieu dai cua báº£ng 
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] width = {50.0f, 30.0f};//SET DO RONG CAC COT
			table2.setWidths(width);
			
			Paragraph dvbh = new Paragraph("Nhà phân phối: " + dhtvBean.getNppTen(), fontnomar);						
			dvbh.setSpacingAfter(3);
			dvbh.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh);
			
			/*Paragraph dvbh1 = new Paragraph(tennpp, font);			
			dvbh1.setSpacingAfter(3);
			dvbh1.setAlignment(Element.ALIGN_LEFT);
			celltieude.addElement(dvbh1);*/						
			
			tableheader.addCell(celltieude);
			
			PdfPCell cellinfo = new PdfPCell();
			
			cellinfo.addElement(new Paragraph("Số chứng từ    : " + dhtvBean.getId(),fontnomar));
						 
			String ngay = dhtvBean.getNgaygiaodich().substring(8,10)+ "-" +dhtvBean.getNgaygiaodich().substring(5,7)+"-"+dhtvBean.getNgaygiaodich().substring(0,4);
			cellinfo.addElement(new Paragraph("Ngày chứng từ: " + ngay,fontnomar));
			tableheader.addCell(cellinfo);
						
			//Add bang vao document
			document.add(tableheader);
			
			PdfPTable table_info=new PdfPTable(1);
			float[] with3 = {50.0f};//SET DO RONG CAC COT
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();
			
			cell111.addElement(new Paragraph("Trình dược viên: " + ddkdTen, fontnomar));
			
			String dd_khachhang = "Mã khách hàng: " + dhtvBean.getSmartId() + "     Tên khách hàng : " + dhtvBean.getKhTen();
			
			cell111.addElement(new Paragraph(dd_khachhang ,fontnomar));

			cell111.setPaddingBottom(10);
			table_info.addCell(cell111);
			document.add(table_info);
			//Table Content
			

			float[] widths = {5.0f, 17.0f, 30.0f, 15.0f, 10.0f, 15.0f, 20.0f};
			String[] headers = new String[]{"STT", "MÃ SP", "TÊN SP", "SỐ LƯỢNG", "ĐVT", "ĐƠN GIÁ", "THÀNH TIỀN"};
			int[] aligns = new int[]{Element.ALIGN_CENTER, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_RIGHT, Element.ALIGN_CENTER, Element.ALIGN_RIGHT, Element.ALIGN_RIGHT, };
			PdfPTable table = new PdfPTable(widths.length);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);		
	        table.setWidths(widths);
			PdfPCell[] cell = new PdfPCell[10];
			for(int i=0; i < headers.length; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(headers[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);	
				cell[i].setPaddingBottom(5f);
				cell[i].setPaddingTop(5f);	
				table.addCell(cell[i]);			
			}
			
			float size= 9f;
			Font font4 = new Font(bf,size );
			PdfPCell cells_detail = new PdfPCell();
			double totalle=0;
			
			List<ISanpham> spList = (List<ISanpham>)dhtvBean.getSpList();
			
			if(spList != null) 
			{
				ISanpham sanpham = null;
				
				for(int i = 0; i < spList.size(); i++)
				{
					sanpham = spList.get(i);
					double dongia = Double.parseDouble(sanpham.getDongia());
					
					String[] arr = new String[]{
						Integer.toString(i+1), 
						sanpham.getMasanpham(), 
						sanpham.getTensanpham(), 
						sanpham.getSoluong(), 
						sanpham.getDonvitinh() ,
						formatter1.format(dongia),
						formatter1.format(Double.parseDouble(sanpham.getSoluong())* dongia )
					};					
					totalle = totalle + Double.parseDouble(sanpham.getSoluong()) * dongia;
					
					for(int j=0; j < arr.length; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						cells_detail.setHorizontalAlignment(aligns[j]);
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells_detail.setPaddingBottom(5f);
						cells_detail.setPaddingTop(5f);
						table.addCell(cells_detail);
					}
				}
			}
			
			//Cộng tiền hàng
			PdfPCell cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph km=new Paragraph("Cộng tiền hàng ", font2);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(widths.length-1);
			cell11.setPaddingBottom(5f);
			cell11.setPaddingTop(5f);	
			table.addCell(cell11);
			
			PdfPCell cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph tongtien = new Paragraph( formatter1.format(totalle), font2);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			cell12.setPaddingBottom(5f);
			cell12.setPaddingTop(5f);	
			table.addCell(cell12);
			
			//Tiền trước thuế
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			km=new Paragraph("Tổng tiền trước thuế ", font2);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(widths.length-1);
			cell11.setPaddingBottom(5f);
			cell11.setPaddingTop(5f);	
			table.addCell(cell11);
			
			//
			double tongtiensauthue = totalle;
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien = new Paragraph(formatter1.format(tongtiensauthue), font2);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			cell12.setPaddingBottom(5f);
			cell12.setPaddingTop(5f);	
			table.addCell(cell12);
			
			
			//
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			km=new Paragraph("Tổng tiền thanh toán (sau thuế) ", font2);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(widths.length-1);
			cell11.setPaddingBottom(5f);
			cell11.setPaddingTop(5f);	
			table.addCell(cell11);
			
			//
			//double tongtiensauthue = totalle / 100 * 10 + totalle;
			//double tongtiensauthue = tongtienthue;
			double tongtienthanhtoansauthue = tongtiensauthue * 1.1; // - tongtienkhuyenmai 
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien = new Paragraph(formatter.format(tongtienthanhtoansauthue) , font2);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			cell12.setPaddingBottom(5f);
			cell12.setPaddingTop(5f);	
			table.addCell(cell12);
			
			//doc tien bang chu
			doctienrachu doctien = new doctienrachu();
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			Long tongtienst = Math.round(tongtienthanhtoansauthue);//tongtiensauthue - tongtienkhuyenmai);
			km = new Paragraph("Số tiền bằng chữ : " + doctien.tranlate(tongtienst+""),  font2);
			km.setAlignment(Element.ALIGN_LEFT);
			cell11.addElement(km);
			cell11.setColspan(10);
			cell11.setPaddingBottom(5f);
			cell11.setPaddingTop(5f);	
			table.addCell(cell11);
			
			document.add(table);
			
		
			PdfPTable tablefooter = new PdfPTable(5);
			tablefooter.setWidthPercentage(100);//chieu dai cua báº£ng 
			tablefooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] withfooterder = {30.0f, 30.0f, 30.0f, 30.0f, 30.0f};//SET DO RONG CAC COT
			tablefooter.setWidths(withfooterder); 
			
			//nguoimua hang 
			Paragraph para = new Paragraph("Khách hàng", fontnomar);
			
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);
			
			//nvgn
			para = new Paragraph("Giao nhận", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);
			
			//o giua
			para = new Paragraph("Thủ kho", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);
			
			//Káº¿ toÃ¡n
			para = new Paragraph("Kế toán", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setBorderWidthRight(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablefooter.addCell(para);
			
			//nguoi nhan hang
			para = new Paragraph("Người lập phiếu", fontnomar);
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");
			 
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setBorder(0);
			
			tablefooter.addCell(para);
			
			document.add(tablefooter);
			

			dhtvBean.DBclose();
			document.close(); 
		}
		catch(DocumentException e)
		{
			dhtvBean.DBclose();
			e.printStackTrace();
		}
	}

}
