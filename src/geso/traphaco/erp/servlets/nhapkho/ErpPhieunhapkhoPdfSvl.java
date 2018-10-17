package geso.traphaco.erp.servlets.nhapkho;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.congty.IErpCongTy;
import geso.traphaco.erp.beans.congty.imp.ErpCongTy;
import geso.traphaco.erp.beans.nhapkho.IErpNhapkho;
import geso.traphaco.erp.beans.nhapkho.ISanpham;
import geso.traphaco.erp.beans.nhapkho.imp.ErpNhapkho;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpPhieunhapkhoPdfSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpPhieunhapkhoPdfSvl() {
        super();
    }

    private IErpCongTy getCongTy()
	{
		dbutils db = new dbutils();
		String query = "Select Ma,Ten,MaSoThue,DiaChi,DienThoai,Fax From Erp_CongTy Where PK_SEQ =100000 ";
		ResultSet rs = db.get(query);
		IErpCongTy c = new ErpCongTy();
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					c.setTEN(rs.getString("Ten"));
					c.setDIACHI(rs.getString("DiaChi"));
					c.setDIENTHOAI(rs.getString("DienThoai"));
					c.setFAX(rs.getString("Fax"));
					c.setMASOTHUE(rs.getString("MaSoThue"));
					
				}
			}
			catch (SQLException e)
			{
				
				e.printStackTrace();
			}
		}
		return c;
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		//String userTen = (String) session.getAttribute("userTen");  	
		IErpCongTy c=getCongTy();
		 if (userId.length() == 0)
		    	userId = request.getParameter("userId");
			   
		String id = request.getParameter("id");
			
		IErpNhapkho pnkBean = new ErpNhapkho(id);
	    pnkBean.setUserId(userId);
	    
	    pnkBean.initPdf();
	    
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=PhieuNhapKhoTT.pdf");
		
		//Rectangle a4Landscape = a4.rotate();
		Document document = new Document(PageSize.A4.rotate());
		
		ServletOutputStream outstream = response.getOutputStream();
		
		this.CreatePxk(document, outstream, pnkBean);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, IErpNhapkho pnkBean) throws IOException
	{
		try
		{		
			IErpCongTy c=getCongTy();
			NumberFormat formatter = new DecimalFormat("###,###.##"); 
			PdfWriter.getInstance(document, outstream);
			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			//font2.setColor(BaseColor.GREEN);
			 
			Paragraph pxk = new Paragraph("Đơn vị : "+c.getTEN(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Địa chỉ : "+c.getDIACHI(), new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("PHIẾU NHẬP KHO", font);
			pxk.setSpacingAfter(3);
			pxk.setSpacingBefore(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("Số: " + pnkBean.getId(), new Font(bf, 7, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_RIGHT);
			document.add(pxk);
			
			pxk = new Paragraph(getDate(pnkBean.getNgaynhapkho()),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("Họ tên người giao hàng: " + pnkBean.getNguoigiaohang(),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Địa chỉ: " + pnkBean.getDiachi(),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Lý do nhập: " + pnkBean.getNdnId(),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Nhập tại kho: " + pnkBean.getKhoId(),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(3);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Ghi chú: ",  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			//Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = {95.0f, 100.0f};
			root.setWidths(cr);
			
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {7.0f, 22.0f, 40.0f, 13.0f, 13.0f};
	        table.setWidths(withs);
	        
	        Font font4 = new Font(bf, 9, Font.BOLD);
		
			PdfPTable sanpham = new PdfPTable(10);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsKM = {10.0f, 50.0f, 30.0f, 15.0f, 15.0f, 15.0f, 20.0f, 15.0f, 15.0f, 30.0f};
			sanpham.setWidths(withsKM);
		    
		    String[] th = new String[]{"STT", "Tên, nhãn hiệu, quy cách, phẩm chất vật tư (sản phẩm, hàng hóa)", "Mã số", "ĐVT", "Lô", "Quy cách", "Số lượng", "Thùng", "Lẻ", "Ghi chú"};
			PdfPCell[] cell = new PdfPCell[10];
			for(int i= 0; i < 10 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font4));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				if(i == 1)
					cell[i].setHorizontalAlignment(Element.ALIGN_LEFT);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				cell[i].setPadding(5);
				sanpham.addCell(cell[i]);			
			}
			
			
			List<ISanpham> spList = pnkBean.getSpList();
			PdfPCell cells = new PdfPCell();
			float totalTrongLuong = 0;
			float totalTheTich = 0;
			int totalSoluong=0;
			int totalthung=0;
			int totalle=0;
			for(int i = 0; i < spList.size(); i++)
			{
				ISanpham sp = (ISanpham)spList.get(i);
				String[] arr = new String[]{Integer.toString(i+1), sp.getDiengiai(), sp.getMa(), sp.getDVT(), sp.getSolo(), sp.getQuycach(), 
						formatter.format(Float.parseFloat(sp.getSoluongnhapkho())), formatter.format(Float.parseFloat(sp.getThung())), 
						formatter.format(Float.parseFloat(sp.getLe())), ""};
				
				totalSoluong += Float.parseFloat(sp.getSoluongnhapkho());
				
				if(sp.getThung().length() > 0)
					totalthung += Float.parseFloat(sp.getThung());
				if(sp.getLe().length() > 0)
					totalle += Float.parseFloat(sp.getLe());
				
				if(sp.getTrongluong().length() > 0)
					totalTrongLuong += Float.parseFloat(sp.getTrongluong());
				if(sp.getThetich().length() > 0)
					totalTheTich += Float.parseFloat(sp.getThetich());
				
				for(int j = 0; j < 10; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.NORMAL)));
					if(j == 1)
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else
					{
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						if( j >= 5)
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(3.0f);
					
					sanpham.addCell(cells);
				}
			}	
			
			document.add(sanpham);
			
			PdfPTable tableHead = new PdfPTable(3);
			tableHead.setWidthPercentage(40);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingBefore(3);
			tableHead.setSpacingAfter(5);
			float[] with = {25.0f, 20.0f, 10.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("Tổng thể tích: ", new Font(bf, 9, Font.NORMAL)));
			PdfPCell cell2 = new PdfPCell(new Paragraph(Float.toString(totalTheTich), font2));
			PdfPCell cell3 = new PdfPCell(new Paragraph("M3", font2));
			
			cell1.setBorder(0);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setBorder(0);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);
	
			cell1 = new PdfPCell(new Paragraph("Tổng khối lượng: ", new Font(bf, 9, Font.NORMAL)));
			cell2 = new PdfPCell(new Paragraph(Float.toString(totalTrongLuong), font2));
			cell3 = new PdfPCell(new Paragraph("KG", font2));
			
			cell1.setBorder(0);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell2.setBorder(0);
			cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setBorder(0);
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);
			
			document.add(tableHead);
			
			//Table Footer			
			PdfPTable tableFooter = new PdfPTable(5);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setWidths(new float[]{30.0f, 30.0f, 30.0f, 30.0f, 30.0f});
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Người lập phiếu", new Font(bf, 9, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell12 = new PdfPCell(new Paragraph("Tài xế", new Font(bf, 9, Font.BOLD)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Thủ kho", new Font(bf, 9, Font.BOLD)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Người nhận", new Font(bf, 9, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell15 = new PdfPCell(new Paragraph("Kế toán", new Font(bf, 9, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);
			
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);
			
			cell11 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell13 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell14 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell15 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);
			
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);
			
			document.add(tableFooter);
			document.close(); 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
	}


	private String getDate(String date)
	{
		String arr[] = date.split("-");
		String nam = arr[0];
		String thang = arr[1];
		String ngay = arr[2];
		
		return "Ngày  " + ngay + "  tháng  " + thang + "  Năm  " + nam;
	}

}
