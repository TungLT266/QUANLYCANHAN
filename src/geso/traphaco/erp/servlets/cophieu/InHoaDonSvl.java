package geso.traphaco.erp.servlets.nganhang;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.*;


@WebServlet("/InHoaDonSvl")
public class InHoaDonSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public InHoaDonSvl() {
        super();
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/*IErpHoaDon obj;
		String userId;
	    Utility util = new Utility();
	  
	    
	    String querystring = request.getQueryString();	    
	    String ddhId = util.getId(querystring);
	    
	    userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new ErpHoaDon();
	    obj.initdisplay(ddhId);*/
		response.setContentType("application/pdf");
		//response.setHeader("Content-Disposition"," attach; filename=InHoaDon.pdf");
		
//		//com.itextpdf.text.Rectangle pageSize = new com.itextpdf.text.Rectangle(510,680);
		//Document document =new Document();
		//document.setPageSize(pageSize);
		
		Document document = new Document(PageSize.A4);
		ServletOutputStream outstream = response.getOutputStream();
		this.InHoaDon(document, outstream);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}


	private void InHoaDon(Document document, ServletOutputStream outstream) throws IOException
	{
		try
		{			 
			//NumberFormat formatter = new DecimalFormat("##.###,##"); 
			PdfWriter.getInstance(document, outstream);
			
			document.setMargins(0, 0, 50f, 0);//set cho can le = 0
			//document.setMargins(0, 0, 0, 0);
			document.setMarginMirroring(true); // phai co them thuoc tinh nay thi moi su dung duoc/
			
			document.open();
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			/*Paragraph sohd = new Paragraph("  ", new Font(bf, 9, Font.BOLD));
			document.add(sohd);
			sohd = new Paragraph("  " + pxkBean.getId(), new Font(bf, 9, Font.BOLD));
			document.add(sohd);*/
				
			//Font font2 = new Font(bf, 8, Font.BOLD);
			
			Font font3 = new Font(bf, 8, Font.ITALIC);
			
			System.out.println("Toi o day...");
			
			Paragraph date = new Paragraph("25-06-2012",font3);
			date.setIndentationLeft(298.2f);	
			//date.setSpacingBefore(100f);
			date.setSpacingAfter(60f);
			document.add(date);
			
			
			/*PdfPTable table = new PdfPTable(2);			
			table.setSpacingBefore(55f);*/  //CAI NAY DUNG
			
			PdfPTable table = new PdfPTable(2);
			table.setHorizontalAlignment(50);
			
			//table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(90);// lay khung
			float[] widths = {200f, 200f}; //set chinh xac bang khoang cach
	        table.setWidths(widths);    
	        
	        PdfPCell cells = new PdfPCell();
					
			cells.setPaddingTop(2.0f);
			
			Paragraph sotk = new Paragraph("235643", font3);
			cells = new PdfPCell(sotk);
			cells.setPaddingLeft(60f);
			cells.setFixedHeight(30f);
			cells.setBorder(0);
			table.addCell(cells);
			
			Paragraph sotien = new Paragraph("200.000 VND", font3);
			cells = new PdfPCell(sotien);
			cells.setPaddingLeft(70f);
			table.addCell(cells);
			
			Paragraph tenkh= new Paragraph("Võ Hoài Ngân", font3);
			cells = new PdfPCell(tenkh);			
			table.addCell(cells);
			
			Paragraph tienbangchu = new Paragraph("Hai Trăm Nghìn Đồng", font3);
			cells = new PdfPCell(tienbangchu);			
			table.addCell(cells);
				
			
			document.add(table);
	
			
			
			/*Paragraph sotk = new Paragraph("002111235344", new Font(bf, 10, Font.BOLDITALIC));
			sotk.setIndentationLeft(90f);
			sotk.setSpacingBefore(55f);
			document.add(sotk);
			
			Paragraph sotien = new Paragraph("120.000.000 VND", new Font(bf, 10, Font.BOLDITALIC));
			sotien.setIndentationLeft(345.6f);
			sotien.setSpacingBefore(55f);
			document.add(sotien);*/
			
			
			
			document.close(); 
			//List<IErpHoaDon_SP> spList = pxkBean.GetListSanPham();	
				
			/*Paragraph ngayxuathang = new Paragraph(pxkBean.getNgayxuathd().substring(8, 10) + "               " + pxkBean.getNgayxuathd().substring(5, 7) 
											+ "              " + pxkBean.getNgayxuathd().substring(0, 4), font3);
			ngayxuathang.setIndentationLeft(253.0f);
			ngayxuathang.setSpacingBefore(125.575f);
			ngayxuathang.setSpacingAfter(18.0f); //tam thoi cho cach dong duoi  la 0.5 cm
			document.add(ngayxuathang);
			
			dbutils db = new dbutils();
			String sql = "select * from nhaphanphoi where pk_seq = " + pxkBean.getNppId();
			String _diachi = "";
			String _masothue = "";
			String _nguoidaidien = "";
			String _tenxhd = "";
			
			try
			{
				ResultSet rs = db.get(sql);
				if(rs.next())
				{
					if(rs.getString("masothue") != null)
						_masothue=rs.getString("masothue");
					_diachi = rs.getString("diachixhd");
					if(rs.getString("tennguoidaidien") != null)
						_nguoidaidien = rs.getString("tennguoidaidien");
					_tenxhd = rs.getString("tenxhd");
				}
				rs.close();
			}
			catch(Exception ex){}

			String[] ddh = pxkBean.getDonDatHang();
			String condition = "";
			for(int j = 0; j < ddh.length; j++)
				condition += ddh[j] + ",";
			if(condition.length() > 0)
				condition = condition.substring(0, condition.length() -1);
			
			sql = "select isnull(ghichu, '') as ghichu, isnull(noidungchietkhau, '') as noidungchietkhau, vat, sotienbvat from dondathang where pk_seq in(" + condition + ")";
			String ghichu = "";
			String noidungck = "";
			String thue = "";
			try
			{
				ResultSet rs = db.get(sql);
				if(rs.next())
				{
					ghichu = rs.getString("ghichu") + ",";
					noidungck = rs.getString("noidungchietkhau") + ",";
					
					if(rs.getString("vat") == null)
						thue = "X";
					else
					{
						if(rs.getFloat("sotienbvat") <= 0)
							thue = "0";
						else
						{
							double tienbvat = rs.getDouble("sotienbvat") - pxkBean.getTienCK();
							thue = Long.toString(Math.round((rs.getDouble("vat") / tienbvat )* 100));
							
							System.out.println("Vat: "+ rs.getFloat("vat"));
							System.out.println("Truoc vat: " + rs.getFloat("sotienbvat"));
							
						}
					}
				}
				rs.close();
			}
			catch(Exception ex){}
			
			System.out.println("Thue la: " + thue);
			
			if(ghichu.length() > 0)
				ghichu = ghichu.substring(0, ghichu.length() - 1);
			if(noidungck.length() > 0)
				noidungck = noidungck.substring(0, noidungck.length() - 1);	
			
			Paragraph tendonvi = new Paragraph("  " + _nguoidaidien , font3);
			tendonvi.setIndentationLeft(150.575f);
			tendonvi.setSpacingAfter(4.0f);//tam thoi cho cach dong duoi  la 0.5 cm
			document.add(tendonvi);
			
			Paragraph nguoimuahang = new Paragraph(_tenxhd.toUpperCase() + " ", new Font(bf, 9, Font.BOLD));
			nguoimuahang.setSpacingAfter(6.0f);//
			nguoimuahang.setIndentationLeft(127.575f);
		
			document.add(nguoimuahang);
			
			Paragraph masothue = new Paragraph(_masothue.toUpperCase() + "  ", new Font(bf, 9, Font.BOLD));
			masothue.setIndentationLeft(127.575f);
			masothue.setSpacingAfter(6.0f);
			document.add(masothue);
			
			Paragraph diachi = new Paragraph(_diachi, new Font(bf, 9, Font.BOLD));
			diachi.setIndentationLeft(127.575f);
			diachi.setSpacingAfter(5.55f);//tam thoi cho cach dong duoi  la 0.5 cm
			document.add(diachi);
		
			Paragraph httt = new Paragraph(" " + pxkBean.gethinhthuctt(), new Font(bf, 9, Font.BOLD));
			httt.setIndentationLeft(127.575f);
			//httt.setSpacingAfter(11.34f);//tam thoi cho cach dong duoi  la 0.5 cm
			document.add(httt);
	
			
			//Table Content
			PdfPTable table = new PdfPTable(7);
			table.setSpacingBefore(64.03f);
			
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(90);
			float[] withs = {22.68f, 170f, 73.71f, 31.185f, 31.185f, 48.195f, 73.71f}; //set chinh xac bang khoang cach
	        table.setWidths(withs);
	       
			Font font4 = new Font(bf, 9, Font.BOLD);
			PdfPCell cells = new PdfPCell();
			cells.setBorder(0);
			double tongthanhtien = 0;
			
			int count = 1;
			for(int i = 0; i < spList.size(); i++)
			{
				IErpHoaDon_SP sanpham = (IErpHoaDon_SP)spList.get(i);
				long thanhtien = 0;
				
				try
				{
					thanhtien = Math.round(sanpham.getDonGia() * sanpham.getSoLuong());
					
				}
				catch(Exception ex){}
				
				tongthanhtien = tongthanhtien + thanhtien;
				System.out.println("Thanh tien: " + tongthanhtien);
				
				String[] arr = new String[]{Integer.toString(count), sanpham.getTenSanPham(),sanpham.getMaSanPham(),
						sanpham.getDonViTinh(), FormatNumber((double)sanpham.getSoLuong(), 0), FormatNumber(sanpham.getDonGia(), 0), FormatNumber(thanhtien, 0)};
				
				for(int j = 0; j < 7; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], font4));
					cells.setBorder(0);
					cells.setFixedHeight(18.0f);
					if(j == 2)
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else
					{
						if( j < 3)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cells.setPaddingTop(2.0f);
					table.addCell(cells);
				}
				count++;
			}	
			
			//double tienCK = pxkBean.getTienCK() / tongthanhtien;
			double tienCK = pxkBean.getTienCK();
			tongthanhtien = tongthanhtien - tienCK;
			
			System.out.println("tien chiet khau: " + tienCK);
			
			if(ghichu.length() > 0)
			{
				String[] arr2 = new String[]{Integer.toString(count), ghichu, "", "", "", "" , ""};
				for(int j = 0; j < 7; j++)
				{
					cells = new PdfPCell(new Paragraph(arr2[j], font4));
					cells.setBorder(0);
					cells.setFixedHeight(18.0f);
					if(j == 2)
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else
					{
						if( j <3)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cells.setPaddingTop(2.0f);
					table.addCell(cells);
				}
				count++;
			}
			
			if(tienCK > 0)
			{
				if(noidungck.length() > 0)
				{
					String[] arr2 = new String[]{Integer.toString(count), noidungck, "", "", "", "" , FormatNumber(tienCK, 0)};
					for(int j = 0; j < 7; j++)
					{
						cells = new PdfPCell(new Paragraph(arr2[j], font4));
						cells.setBorder(0);
						cells.setFixedHeight(18.0f);
						if(j == 2)
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						else
						{
							if( j <3)
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							else
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						cells.setPaddingTop(2.0f);
						table.addCell(cells);
					}
					count++;
				}
			}
			
			System.out.println("Count1: " + count);
			if(count < 13)
			{
				for(int k = 0; k < (13 - count) ; k++)
				{
					for(int j = 0; j < 7; j++)
					{
						cells = new PdfPCell(new Paragraph("      ", font4));
						cells.setBorder(0);
						cells.setFixedHeight(18.0f); //15.762
						
						cells.setPaddingTop(2.0f);
						table.addCell(cells);
					}
				}
			}
			
			System.out.println("Count2: " + count);
			
			PdfPCell cell8 = new PdfPCell(new Paragraph("  ", font3));
			cell8.setColspan(7);
			cell8.setBorder(0);
			cell8.setFixedHeight(20.0f);
			table.addCell(cell8);
			
			cell8 = new PdfPCell(new Paragraph("  ", font3));
			cell8.setColspan(7);
			cell8.setBorder(0);
			cell8.setFixedHeight(20.0f);
			table.addCell(cell8);
			
			cell8 = new PdfPCell(new Paragraph("  ", font3));
			cell8.setColspan(7);
			cell8.setBorder(0);
			cell8.setFixedHeight(20.0f);
			table.addCell(cell8);
			
			cell8 = new PdfPCell(new Paragraph("  ", font3));
			cell8.setColspan(6);
			cell8.setBorder(0);
			cell8.setFixedHeight(13.0f);
			table.addCell(cell8);
			
			PdfPCell cell11 = new PdfPCell(new Paragraph(FormatNumber(tongthanhtien, 0), font2));
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setBorder(0);
			cell11.setPaddingTop(2.0f);
			cell11.setFixedHeight(18.0f);
			table.addCell(cell11);
		
			double vat = tongthanhtien;
			
			double tongtiencovat = tongthanhtien;
			
			cell11 = new PdfPCell(new Paragraph("                                            " + thue, font2));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell11.setBorder(0);
			cell11.setColspan(6);
			cell11.setFixedHeight(18.0f);
			table.addCell(cell11);
			
			if(thue.equals("X") || thue.equals("0"))
			{
				cell11 = new PdfPCell(new Paragraph("  ", font2));
			}
			else
			{
				vat = Math.round(tongthanhtien * Integer.parseInt(thue) / 100);
				tongtiencovat = tongthanhtien + vat;
				cell11 = new PdfPCell(new Paragraph(FormatNumber(vat, 0), font2));
			}
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setBorder(0);
			cell11.setPaddingTop(2.0f);
			cell11.setFixedHeight(18.0f);
			table.addCell(cell11);
			
			table.addCell(cell8);
			cell11 = new PdfPCell(new Paragraph(FormatNumber(tongtiencovat, 0), font2));
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell11.setBorder(0);
			cell11.setPaddingTop(4.0f);
			cell11.setFixedHeight(18.0f);
			table.addCell(cell11);
		
			document.add(table);
		  
		    doctienrachu doctien = new doctienrachu();
		    
		    String tien = doctien.tranlate(Math.round(tongtiencovat) + "");
		    tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
		    
		    Paragraph paradoctien = new Paragraph("            " + tien, new Font(bf, 9, Font.BOLD));
		    paradoctien.setSpacingBefore(10.0f);
		    paradoctien.setIndentationLeft(140.575f);
			
			document.add(paradoctien);
			
			document.close(); 
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	private String FormatNumber(double number, int round)
	{
		//System.out.println("Truoc kho Format: " + number);
		String format = "#,###,###";
		if(round >= 1)
			format += ".";
		
		for(int i = 0; i < round; i++)
			format += "0";
		
		//System.out.println("Chuoi Format: " + format);
		
		DecimalFormat df = new DecimalFormat(format);
		String result = df.format(number);
		
		if(number > 999)
		{
			//result = result.replaceAll(".", "+");
			result = result.replaceAll(",", ".");
			if(round > 0)
				result = result.substring(0, result.length() - (round + 1)) + "," + result.substring(result.length() - round);
			//result = result.replaceFirst("-", ",");
		}
		else
			result = result.replaceAll(",", ".");
			
		//System.out.println("ket qua: " + result);
		return result;
	}*/
}catch(Exception e){
		System.out.println("Day nay : " + e.toString());}
	}
}

