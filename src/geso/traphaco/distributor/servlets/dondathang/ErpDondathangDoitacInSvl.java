package geso.traphaco.distributor.servlets.dondathang;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Document;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.distributor.beans.dondathang.IErpDondathangDoitac;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDondathangDoitac;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpDondathangDoitacInSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpDondathangDoitacInSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			
			Utility util = new Utility();
			/*String querystring = request.getQueryString();
			String dhid = util.getId(querystring);
			System.out.println(dhid);*/

			String nppId = request.getParameter("nppId");
			if(nppId == null)
			    nppId = "";
			  
			String dhid = request.getParameter("id");
			IErpDondathangDoitac dhBean = new ErpDondathangDoitac(dhid);
			dhBean.setNppId(nppId);
			dhBean.setUserId(userId);
			dhBean.init();
			
			response.setContentType("application/pdf");

			Document document = new Document(PageSize.A4);
			ServletOutputStream outstream = response.getOutputStream();
			try {
				CreatePxk(document, outstream, dhBean,dhid,userId);
			} catch (DocumentException e) {

				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, IErpDondathangDoitac dhBean,String id,String userid) throws IOException, DocumentException
	{
		dbutils db = new dbutils();
		Utility util = new Utility();

		NumberFormat formatter2 = new DecimalFormat("#,###,###");
		try
		{

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 9, Font.BOLD);
			Font font8normal = new Font(bf, 9, Font.NORMAL);
			Font font8bold = new Font(bf, 9, Font.BOLD);
			Font font4 = new Font(bf, 7, Font.BOLD);
			Font font4normal = new Font(bf, 7, Font.NORMAL);
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			String tencongty = "";
			String diachi = "", dienthoainpp = "", fax="";
			String query = "select ten,diachi,dienthoai,fax from nhaphanphoi where pk_seq = "+ util.getIdNhapp(dhBean.getUserId());
			System.out.println("tencongty "+query);
			ResultSet rs = db.get(query);
			try 
			{
				rs.next();
				tencongty = rs.getString("ten");
				diachi = rs.getString("diachi");
				dienthoainpp = rs.getString("dienthoai");
				fax = rs.getString("fax");
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			query = " select b.ten, c.ten as nguoitao, a.machungtu, CONVERT(VARCHAR(20), a.Created_Date, 120) as ngaylap " + 
					" from ERP_DONDATHANGNPP a inner join KHO b on a.Kho_FK = b.pk_seq inner join NHANVIEN c on a.nguoitao = c.pk_seq  where a.PK_SEQ = "+id;
			ResultSet khors = db.get(query);
			
			String khohanghoa = "";
			String nguoilap = "";
			String machungtu = "";
			String ngaylap = "";
			try 
			{
				if(khors.next())
				{
					khohanghoa = khors.getString("ten");
					nguoilap = khors.getString("nguoitao");
					machungtu = khors.getString("machungtu");
					ngaylap = khors.getString("ngaylap");
				}
				khors.close();
			} 
			catch (Exception e1)
			{
				e1.printStackTrace();
			}

			Paragraph tieude1 = new Paragraph(tencongty, font4);
			tieude1.setIndentationRight(0);
			document.add(tieude1);

			Paragraph tieude2 = new Paragraph("Kho "+khohanghoa, font4normal);
			tieude2.setIndentationRight(0);
			document.add(tieude2);

			Paragraph tieude3 = new Paragraph(diachi.trim(), font4normal);
			tieude3.setIndentationRight(0);
			document.add(tieude3);
			
			Paragraph tieude4 = new Paragraph("Tel: " + dienthoainpp.trim() + " - Fax: " + fax, font4normal);
			tieude4.setIndentationRight(0);
			document.add(tieude4);

			Paragraph tieudebm = new Paragraph("BM-20-1" , font4normal);
			tieudebm.setIndentationLeft(10);			
			tieudebm.setAlignment(Element.ALIGN_RIGHT);
			document.add(tieudebm);
			String str_tieude = "Lệnh xuất hàng";
			Paragraph tieude = new Paragraph(str_tieude, font);
			tieude.setAlignment(Element.ALIGN_CENTER);
			tieude.setSpacingAfter(10);
			document.add(tieude);


			String makh = "", tenkh = "", diachikh = "",nvbh = "",ghichu = "",dienthoai = "",nguoilienhe ="";
			String nguoinhanhang = "";
			
			query = "select b.ma as makh,b.NGUOI_LIENHE_DH, isnull(b.TEN, c.TEN) as ten, isnull(b.DIACHI, c.diachi) as diachi, isnull(d.ten, '') as nvbh,isnull(a.GHICHU,'') as ghichu, isnull(b.DIENTHOAI, c.dienthoai) as dienthoai, isnull(b.TENXUATHD, '') as TENXUATHD  "+
					"from ERP_DONDATHANGNPP a " + 
					" 	left join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ " + 
					" 	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ " + 
					" 	left join DAIDIENKINHDOANH d on a.DDKD_FK = c.PK_SEQ "+
					"where a.PK_SEQ = "+id;
			ResultSet ttkhrs = db.get(query);
			try 
			{
				if(ttkhrs.next())
				{
					makh = ttkhrs.getString("makh");
					
					diachi = ttkhrs.getString("DIACHI");
					nvbh = ttkhrs.getString("nvbh");
					ghichu = ttkhrs.getString("ghichu");
					dienthoai = ttkhrs.getString("dienthoai");
					nguoilienhe = ttkhrs.getString("NGUOI_LIENHE_DH");
					
					tenkh = ttkhrs.getString("TENXUATHD");
					if( tenkh.trim().length() <= 0 )
						tenkh = ttkhrs.getString("ten");
					
					nguoinhanhang = ttkhrs.getString("ten");
				}
				ttkhrs.close();
			} 
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
			
			float[] withs1 = { 4.0f};
			PdfPTable table1 = new PdfPTable(withs1);
			table1.setWidthPercentage(100);
			table1.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell[] cell1 = new PdfPCell[1];
			cell1[0] = new PdfPCell(new Paragraph("Thông tin chứng từ", font2));
			cell1[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			cell1[0].setBackgroundColor(BaseColor.LIGHT_GRAY);
			table1.addCell(cell1[0]);
			document.add(table1);
			float[] withs2 = { 50f,50f };
			PdfPTable table2 = new PdfPTable(withs2);
			table2.setWidthPercentage(100);
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell[] cell2 = new PdfPCell[2];
			Paragraph p1 = new Paragraph();
			p1.add(new Phrase( "Mã chứng từ: ", font8bold));
			p1.add(new Phrase( machungtu , font8normal));
			cell2[0] = new PdfPCell(p1);
			table2.addCell(cell2[0]);
			
			Paragraph p2 = new Paragraph();
			p2.add(new Phrase( "Ngày lập: ", font8bold));
			p2.add(new Phrase( ngaylap, font8normal));
			cell2[1] = new PdfPCell(p2);
			table2.addCell(cell2[1]);
			
			Paragraph p3 = new Paragraph();
			p3.add(new Phrase( "Mã khách hàng: ", font8bold));
			p3.add(new Phrase( "" + makh, font8normal));
			cell2[0] = new PdfPCell(p3);
			table2.addCell(cell2[0]);
			
			Paragraph p4 = new Paragraph();
			p4.add(new Phrase("Khách hàng: ", font8bold));
			p4.add(new Phrase( ""+tenkh, font8normal));
			cell2[1] = new PdfPCell(p4);
			table2.addCell(cell2[1]);
			
			p3 = new Paragraph();
			p3.add(new Phrase( "Người nhận hàng: ", font8bold));
			p3.add(new Phrase( "" + nguoinhanhang, font8normal));
			cell2[0] = new PdfPCell(p3);
			table2.addCell(cell2[0]);
			
			p4 = new Paragraph();
			p4.add(new Phrase(" ", font8bold));
			p4.add(new Phrase(" ", font8normal));
			cell2[1] = new PdfPCell(p4);
			table2.addCell(cell2[1]);

			
			Paragraph p5 = new Paragraph();
			p5.add(new Phrase("Địa chỉ: ", font8bold));
			p5.add(new Phrase( ""+diachi, font8normal));
			cell2[0] = new PdfPCell(p5);
			table2.addCell(cell2[0]);
		
			Paragraph p6 = new Paragraph();
			p6.add(new Phrase("Địa chỉ giao hàng: ", font8bold));
			p6.add(new Phrase( ""+diachi, font8normal));
			cell2[1] = new PdfPCell(p6);
			table2.addCell(cell2[1]);
			
			Paragraph p7 = new Paragraph();
			p7.add(new Phrase("Người liên hệ: ", font8bold));
			p7.add(new Phrase( nguoilienhe, font8normal));
			p7.add(new Phrase(" - "+ "Điện thoại: ", font8bold));
			p7.add(new Phrase( dienthoai, font8normal));
			
			cell2[0] = new PdfPCell(p7);
			table2.addCell(cell2[0]);
			
			Paragraph p8 = new Paragraph();
			p8.add(new Phrase("Ghi chú: ", font8bold));
			p8.add(new Phrase( ghichu, font8normal));
			cell2[1] = new PdfPCell(p8);
			table2.addCell(cell2[1]);
			
			
			Paragraph p9 = new Paragraph();
			p9.add(new Phrase("Nhân viên bán hàng: ", font8bold));
			p9.add(new Phrase( nvbh, font8normal));
			p9.add(new Phrase(" - "+ "Điện thoại: ", font8bold));
			p9.add(new Phrase( "", font8normal));
			cell2[0] = new PdfPCell(p9);
			table2.addCell(cell2[0]);

			Paragraph p10 = new Paragraph();
			p10.add(new Phrase("Mã số thuế: ", font8bold));
			p10.add(new Phrase( "", font8normal));
			cell2[1] = new PdfPCell(p10);
			table2.addCell(cell2[1]);

			document.add(table2);
			float[] withs3 =
				{ 4.0f};
			PdfPTable table3 = new PdfPTable(withs3);
			table3.setWidthPercentage(100);
			table3.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell[] cell3 = new PdfPCell[1];
			cell3[0] = new PdfPCell(new Paragraph("Danh mục sản phẩm ", font2));
			cell3[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			cell3[0].setBackgroundColor(BaseColor.WHITE);
			table3.addCell(cell3[0]);
			document.add(table3);	
			float[] withs4 = {8.0f, 15.0f, 25.0f, 12.0f, 15.0f, 8.0f, 13.0f, 15.0f, 15.0f };
			PdfPTable table4 = new PdfPTable(withs4);
			table4.setWidthPercentage(100);
			table4.setHorizontalAlignment(Element.ALIGN_LEFT);
			String[] th = new String[] { "STT", "Mã sản phẩm","Tên sản phẩm", "Số lô","Hạn dùng", "ĐVT", "Số lượng", "Giá sản phẩm", "Thành tiền"};
			PdfPCell[] cell4 = new PdfPCell[9];
			for (int i = 0; i < th.length; i++)
			{
				cell4[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell4[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
				table4.addCell(cell4[i]);
			}	

			double tongtien = 0;
			double pt_chietkhau = 0;
			double tongtien_chietkhau = 0;
			
			//ResultSet Sprs = dhBean.getSPRs();
			
			query = "select b.MA, 0 as soluongton,  isnull(a.sanphamTEN, b.TEN) as TEN, DV.donvi, c.soluong, c.solo, c.ngayhethan, a.dongia, a.dongiaGOC, isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, isnull(a.tungay, '') as tungay, isnull(a.denngay, '') as denngay, a.thueVAT, 0 as spQuyDoi, a.ddkd_fk, round( ( isnull(a.chietkhau_CSBH, 0) + isnull(a.chietkhau_KM, 0) ) * 100.0 / a.dongiaGOC, 1 ) as ptChietkhau_KMBH, 0 as dagiao   "+
					 "	from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ      INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK   inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET c on a.dondathang_fk = c.dondathang_fk and a.sanpham_fk = c.sanpham_fk     "+
					 "	where a.DONDATHANG_FK = '" + id + "'  and c.scheme = '' and c.soluong > 0  "+
					 "union ALL "+
					 "	select b.MA, 0 as soluongton,  b.TEN as TEN, DV.donvi, c.soluong, c.solo, c.ngayhethan, 0 dongia, 0 dongiaGOC, 0 as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, '' as tungay, '' denngay, 0 thueVAT, 0 as spQuyDoi, -1 as ddkd_fk, 0 as ptChietkhau_KMBH, 0 as dagiao   "+
					 "	from SanPham b INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK    "+
					 "		inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET c on b.PK_SEQ = c.sanpham_fk     "+
					 "	where c.dondathang_fk = '" + id + "'  and c.scheme != '' and c.soluong > 0 ";
			
			System.out.println("::: LAY SAN PHAM: " + query);
			ResultSet Sprs = db.get(query);
			int stt = 0;
			try {
				
				String masp = "";
				String tensp = "";
				String donvi = "";
				double soluong = 0;
				String solo = "";
				String ngayhethan = "";
				double dongia = 0;
				double dongiaGOC = 0;
				double thanhtien = 0;
				
				while (Sprs.next())
				{
					PdfPCell[] cell5 = new PdfPCell[9];
					
					masp = Sprs.getString("ma");
					tensp = Sprs.getString("ten");
					donvi =  Sprs.getString("donvi");
					solo =  Sprs.getString("solo");
					ngayhethan = this.DinhDangSoLo ( Sprs.getString("ngayhethan") );
					
					soluong = Sprs.getDouble("soluong");
					dongia = Math.round( Sprs.getDouble("dongiaGOC") * ( 1 + Sprs.getDouble("thueVAT") / 100.0 ) );
					//dongiaGOC = Math.round( Sprs.getDouble("dongiaGOC") * ( 1 + Sprs.getDouble("thueVAT") / 100.0 ) );
					
					pt_chietkhau = Sprs.getDouble("ptChietkhau_KMBH");
					
					thanhtien = Math.round( Sprs.getDouble("dongiaGOC") * ( 1 + Sprs.getDouble("thueVAT") / 100.0 ) ) * soluong;
					tongtien_chietkhau += Math.round( ( thanhtien * pt_chietkhau / 100.0 ) );
					tongtien += thanhtien;
					
					stt++;
					
					cell5[0] = new PdfPCell(new Paragraph("" + stt, font8normal));
					cell5[0].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[0]);

					cell5[1] = new PdfPCell(new Paragraph(masp, font8normal));
					cell5[1].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[1]);
					
					cell5[2] = new PdfPCell(new Paragraph(tensp, font8normal));
					cell5[2].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[2]);
					
					cell5[3] = new PdfPCell(new Paragraph(solo, font8normal));
					cell5[3].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[3]);
					
					cell5[4] = new PdfPCell(new Paragraph(ngayhethan, font8normal));
					cell5[4].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[4]);
					
					cell5[5] = new PdfPCell(new Paragraph(donvi, font8normal));
					cell5[5].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[5]);

					cell5[6] = new PdfPCell(new Paragraph(formatter2.format(soluong), font8normal));
					cell5[6].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[6].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[6]);

					cell5[7] = new PdfPCell(new Paragraph(formatter2.format(dongia), font8normal));
					cell5[7].setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell5[7].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[7]);

					cell5[8] = new PdfPCell(new Paragraph(formatter2.format(thanhtien), font8normal));
					cell5[8].setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell5[8].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[8]);
						
				}
			} 
			catch (Exception e1) {

				e1.printStackTrace();
			}
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
			//String tien = doctien.docTien(Long.parseLong( stt ));
			
			//Viết hoa ký tự đầu tiên
		    //String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			
			try {

				PdfPCell cell = new PdfPCell(new Paragraph("Tổng đơn vị sản phẩm " + stt + " - (Bằng chữ ): " + doctien.docSo((long)stt) + " sản phẩm", font8normal));
				cell.setColspan(6);
				cell.setPadding(2.0f);
				table4.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Thành tiền: ", font8bold));
				cell.setColspan(2);
				cell.setPadding(2.0f);
				cell.setBorderWidthRight(0);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table4.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(formatter2.format(tongtien), font8bold));
				cell.setPadding(2.0f);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table4.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("Chiết khấu (Bằng chữ): "  + doctien.docTien( (long) tongtien_chietkhau ), font8normal));
				cell.setColspan(6);
				cell.setPadding(2.0f);
				table4.addCell(cell);
				
				//cell = new PdfPCell(new Paragraph("Chiết khấu (" + formatter2.format(pt_chietkhau) + "%): ", font8bold));
				cell = new PdfPCell(new Paragraph("Chiết khấu: ", font8bold));
				cell.setColspan(2);
				cell.setPadding(2.0f);
				cell.setBorderWidthRight(0);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table4.addCell(cell);
				
				cell = new PdfPCell();
				cell = new PdfPCell(new Paragraph(formatter2.format(tongtien_chietkhau), font8bold));
				cell.setPadding(2.0f);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table4.addCell(cell);
				
				//TỔNG TIỀN
				cell = new PdfPCell(new Paragraph("Tổng tiền (Bằng chữ): "  + doctien.docTien( (long)( tongtien - tongtien_chietkhau )), font8normal));
				cell.setColspan(6);
				cell.setPadding(2.0f);
				table4.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Tổng tiền: ", font8bold));
				cell.setColspan(2);
				cell.setPadding(2.0f);
				cell.setBorderWidthRight(0);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table4.addCell(cell);
				
				cell = new PdfPCell();
				cell = new PdfPCell(new Paragraph( formatter2.format( tongtien - tongtien_chietkhau ), font8bold));
				cell.setPadding(2.0f);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table4.addCell(cell);
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			document.add(table4);		


			/*PdfPCell[] cell6 = new PdfPCell[9];
			cell6[0] = new PdfPCell(new Paragraph("Tổng đơn vị sản phẩm "+stt+"(bằng chữ ): "+reader(stt)+" sản phẩm ", font8normal));
			cell6[1] = new PdfPCell();
			cell6[2] = new PdfPCell();
			cell6[3] = new PdfPCell();
			cell6[4] = new PdfPCell(new Paragraph("Thành tiền:", font8bold));
			cell6[4].setHorizontalAlignment(Element.ALIGN_CENTER);
			cell6[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell6[5] = new PdfPCell(new Paragraph(""+formatter2.format(tongthanhtien), font8bold));
			cell6[5].setHorizontalAlignment(Element.ALIGN_CENTER);
			cell6[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell6[6] = new PdfPCell();
			cell6[7] = new PdfPCell();
			cell6[0].setColspan(7);
			cell6[6].setColspan(2);
			table4.addCell(cell6[0]);
			table4.addCell(cell6[4]);
			table4.addCell(cell6[5]);*/
			
			Paragraph ngaythang = new Paragraph("Ngày "+getDateTime().substring(0,2)+" Tháng "+getDateTime().substring(3,5)+" Năm "+getDateTime().substring(6,10), font4normal);
			ngaythang.setSpacingBefore(10);
			ngaythang.setAlignment(Element.ALIGN_LEFT);
			document.add(ngaythang);
			
			Paragraph td = new Paragraph("Người lập 	              	              Trưởng phòng cung ứng                     Kế toán trưởng                     Thủ kho                Khách hàng", font2);
			td.setAlignment(Element.ALIGN_LEFT);
			document.add(td);
			
			Paragraph td1 = new Paragraph("(Ký tên & Họ và tên)", font2);
			td1.setAlignment(Element.ALIGN_RIGHT);
			document.add(td1);
			
			td = new Paragraph( " " , font4normal);
			td.setAlignment(Element.ALIGN_LEFT);
			document.add(td);
			
			td = new Paragraph( " " , font4normal);
			td.setAlignment(Element.ALIGN_LEFT);
			document.add(td);
			
			td = new Paragraph( nguoilap , font4normal);
			td.setAlignment(Element.ALIGN_LEFT);
			document.add(td);
			
			document.close();
		} 
		catch (Exception e)
		{
			db.shutDown();
			e.printStackTrace();
		}
		db.shutDown();
	}

	private String DinhDangSoLo(String solo) 
	{
		if( !solo.contains("-") )
			return solo;
		
		String[] arr = solo.split("-");
		
		return arr[1] + "/" + arr[0];
	}
	
	  
}
