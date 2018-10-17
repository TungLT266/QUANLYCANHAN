package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.distributor.beans.xuatkho.IErpSoxuathangNpp;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpSoxuathangNpp;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class SoxuathangInSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public SoxuathangInSvl()
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
		} 
		else
		{
			String id = request.getParameter("id");
			
			IErpSoxuathangNpp dhBean = new ErpSoxuathangNpp(id);
			dhBean.setUserId(userId);
			response.setContentType("application/pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			try 
			{
				CreatePxk(document, outstream, dhBean, id, userId);
			} 
			catch (Exception e) {
				
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

	private void CreatePxk(Document document, ServletOutputStream outstream, IErpSoxuathangNpp dhBean,String id,String userid) throws IOException, DocumentException
	{
		dbutils db = new dbutils();
		Utility util = new Utility();
		
		// font2.setColor(BaseColor.GREEN);
		NumberFormat formatter = new DecimalFormat("#,###,###");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		NumberFormat formatter2 = new DecimalFormat("#,###,###");
		NumberFormat formatter3 = new DecimalFormat("#,###,##0.00");
		try
		{

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 8, Font.UNDERLINE);
			Font fontnomar = new Font(bf, 10, Font.NORMAL);
			Font font8normal = new Font(bf, 8, Font.NORMAL);
			Font font8bold = new Font(bf, 8, Font.BOLD);
			Font font4 = new Font(bf, 6, Font.BOLD);
			Font font4normal = new Font(bf, 6, Font.NORMAL);
			PdfWriter.getInstance(document, outstream);
			document.open();
			String tencongty = "";
			String diachi = "";
			String query = "select ten, diachi from nhaphanphoi where pk_seq = "+ util.getIdNhapp(dhBean.getUserId());
			System.out.println("tencongty "+query);
			ResultSet rs = db.get(query);
			try {
				rs.next();
				tencongty = rs.getString("ten");
				diachi = rs.getString("diachi");
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
			String khohanghoa = "";
			String nguoilap = "";
			String nguoigh = "";
			String machungtu = "";
			String ngaylap = "";
			
			/*query = " select a.machungtu, b.ten, c.ten as nguoilap, CONVERT(VARCHAR(20), a.Created_Date, 120) as ngaylap, isnull( ( select TEN from NHANVIENGIAONHAN where PK_SEQ = a.nvgn_fk ), '' ) as nguoigiaohang " + 
					" from ERP_SOXUATHANGNPP a inner join KHO b on a.Kho_FK = b.pk_seq inner join NHANVIEN c on a.nguoitao = c.pk_seq where a.PK_SEQ = "+id;*/
			
			query = "select a.machungtu, N'Kho hàng bán' ten, c.ten as nguoilap, CONVERT(VARCHAR(20), a.Created_Date, 120) as ngaylap,  "+
					 "	isnull( ( select TEN from NHANVIENGIAONHAN where PK_SEQ = a.nvgn_fk ),  "+
					 "	( "+
					 "		select top(1) TEN from NHANVIENGIAONHAN where PK_SEQ in ( "+
					 "		select top(1) NVGN_FK from NVGN_KH where KHACHHANG_FK in ( "+
					 "					select KHACHHANG_FK from ERP_HOADONNPP where PK_SEQ in ( select hoadon_fk from ERP_SOXUATHANGNPP_DDH a inner join ERP_HOADONNPP b on a.hoadon_fk = b.PK_SEQ ) ) ) "+
					 "  "+
					 "	 ) ) as nguoigiaohang  "+
					 "from ERP_SOXUATHANGNPP a  inner join NHANVIEN c on a.nguoitao = c.pk_seq  "+
					 "where a.PK_SEQ = '" + id + "' ";
			ResultSet khors = db.get(query);
			try 
			{
				if(khors.next())
				{
					khohanghoa = khors.getString("ten");
					nguoilap = khors.getString("nguoilap");
					nguoigh = khors.getString("nguoigiaohang");
					machungtu = khors.getString("machungtu");
					ngaylap = khors.getString("ngaylap");
				}
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
			
			Paragraph tieude3 = new Paragraph(diachi, font4normal);
			tieude3.setIndentationRight(0);
			document.add(tieude3);

			Paragraph tieudebm = new Paragraph("BM-20-1" , font4normal);
			tieudebm.setIndentationLeft(10);			
			tieudebm.setAlignment(Element.ALIGN_RIGHT);
			document.add(tieudebm);
			String str_tieude = "Sổ xuất hàng";
			Paragraph tieude = new Paragraph(str_tieude, font);
			tieude.setAlignment(Element.ALIGN_CENTER);
			tieude.setSpacingAfter(10);
			document.add(tieude);
			
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
			cell2[0] = new PdfPCell(new Paragraph("Mã chứng từ: " + machungtu, font8normal));
			cell2[1] = new PdfPCell(new Paragraph("Ngày lập: " + ngaylap, font8normal));
			table2.addCell(cell2[0]);
			table2.addCell(cell2[1]);
			cell2[0] = new PdfPCell(new Paragraph("Công ty: " + tencongty, font8normal));
			cell2[1] = new PdfPCell(new Paragraph("Kho hàng hóa: " + khohanghoa, font8normal));
			table2.addCell(cell2[0]);
			table2.addCell(cell2[1]);
			cell2[0] = new PdfPCell(new Paragraph("Người lập: " + nguoilap, font8normal));
			cell2[1] = new PdfPCell(new Paragraph("Người giao hàng: " + nguoigh , font8normal));
			table2.addCell(cell2[0]);
			table2.addCell(cell2[1]);
			document.add(table2);
			float[] withs3 = { 4.0f};
			PdfPTable table3 = new PdfPTable(withs3);
			table3.setWidthPercentage(100);
			table3.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell[] cell3 = new PdfPCell[1];
			cell3[0] = new PdfPCell(new Paragraph("Đơn đặt hàng ", font2));
			cell3[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			cell3[0].setBackgroundColor(BaseColor.WHITE);
			table3.addCell(cell3[0]);
			document.add(table3);	
			
			float[] withs4 = { 6.0f, 10.0f, 20.0f, 20.0f, 25.0f, 20.0f, 15.0f, 17.0f, 10.0f };
			//float[] withs4 = { 6.0f, 10.0f, 20.0f, 25.0f, 30.0f, 20.0f, 17.0f };
			PdfPTable table4 = new PdfPTable(withs4);
			table4.setWidthPercentage(100);
			table4.setHorizontalAlignment(Element.ALIGN_LEFT);
			String[] th = new String[] { "STT", "Số hóa đơn", "Tên xuất HĐ", "Tên nội bộ", "Địa chỉ giao hàng", "Sản phẩm : Số lượng", "Thành tiền ", "Nhân viên bán hàng", "Ghi chú" };
			//String[] th = new String[] { "STT", "Mã chứng từ", "Khách hàng", "Địa chỉ giao hàng", "Sản phẩm : Số lượng", "Nhân viên bán hàng", "Ghi chú" };
			PdfPCell[] cell4 = new PdfPCell[9];
			for (int i = 0; i < th.length; i++)
			{
				cell4[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell4[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4[i].setBackgroundColor(BaseColor.LIGHT_GRAY );
				table4.addCell(cell4[i]);
			}	
			
			query = " select sxh.hoadon_fk, ISNULL( ( select GHICHU from ERP_DONDATHANGNPP where PK_SEQ = ( select ddh_fk from ERP_HOADONNPP_DDH where HOADONNPP_FK = sxh.hoadon_fk ) ), '') as ghichu " +
					" from ERP_SOXUATHANGNPP_DDH sxh inner join ERP_HOADONNPP hd on sxh.hoadon_fk = hd.pk_seq left join KHACHHANG kh on hd.khachhang_fk = kh.pk_seq " + 
					" where soxuathang_fk = '" + id + "' order by isnull( kh.ten, '' ) asc  ";
			System.out.println("::: LAY HOA DON:  " + query);
			ResultSet rsdhid = db.get(query);
			double tongthanhtien = 0;
			try 
			{
				int stt = 0;
				while(rsdhid.next())
				{
					/*query = " select  a.PK_SEQ, a.machungtu as sochungtu, isnull(c.TEN, npp.ten) as tenkhachhang, isnull(c.DIACHI, npp.diachi) as diachigiaohang, "+
							" ( d.TEN + '/' + cast(b.soluong as varchar(10))) as soluongsanpham, (AVG(b.dongia)*b.soluong) as thanhtien, e.TEN as nhanvienbanhang, a.GHICHU as ghichu "+ 
							" from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM b "+
							" on a.PK_SEQ = b.dondathang_fk left join KHACHHANG c on a.KHACHHANG_FK = c.PK_SEQ left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ " + 
							"  inner join sanpham d on b.sanpham_fk = d.PK_SEQ left join DAIDIENKINHDOANH e on a.DDKD_FK = e.PK_SEQ "+
							" where a.PK_SEQ = " + rsdhid.getString("ddh_fk") + " "+
							" group by a.PK_SEQ, a.machungtu, c.TEN,c.DIACHI, npp.ten, npp.diachi, d.TEN,b.soluong,b.dongia,e.TEN,a.GHICHU";*/	
					
					//GHI CHÚ LẤY TỪ ĐƠN HÀNG
					query = "select  a.PK_SEQ, a.SOHOADON as sochungtu, isnull( a.TENXUATHD, '' ) as TENXUATHD, isnull(c.TEN, npp.ten) as tenkhachhang, isnull(c.DIACHI, npp.diachi) as diachigiaohang,  "+
							 " d.TEN as tenSP, sum(b.soluong) as soluong, sum(  b.soluong *   round( b.DONGIA * ( 1 + b.vat / 100.0 ), 0 ) ) as thanhtien, e.TEN as nhanvienbanhang, a.ghichu   "+
							 "from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b  on a.PK_SEQ = b.HOADON_FK  "+
							 "		left join KHACHHANG c on a.KHACHHANG_FK = c.PK_SEQ  "+
							 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   "+
							 "		inner join sanpham d on b.sanpham_fk = d.PK_SEQ  "+
							 "		left join DAIDIENKINHDOANH e on a.DDKD_FK = e.PK_SEQ "+
							 "where a.PK_SEQ = '" + rsdhid.getString("hoadon_fk") + "' "+
							 "group by a.PK_SEQ, a.SOHOADON, a.TENXUATHD, c.TEN, c.DIACHI, npp.ten, npp.diachi, d.TEN, e.TEN, a.ghichu ";	

					System.out.println("Form In SO XUAT HANG: " + query);
					ResultSet rs1 = db.get(query);
					try {
						
						PdfPCell[] cell5 = new PdfPCell[8];
						String sochungtu = "";
						String khachhangTXHD = "";
						String khachhangTEN = "";
						String diachigiaohang = "";
						String sanpham  = "";
						String thanhtien = "";
						String nvbh = "";
						String ghichu = rsdhid.getString("ghichu");
						double tongtien = 0;
						while(rs1.next())
						{
							sochungtu = rs1.getString("sochungtu");
							khachhangTXHD = rs1.getString("TENXUATHD");
							khachhangTEN = rs1.getString("tenkhachhang");
							diachi =  rs1.getString("diachigiaohang");
							sanpham += rs1.getString("tenSP") + " : " +  formatter.format(rs1.getDouble("soluong")) + ", " ;
							thanhtien = rs1.getString("thanhtien") == null ? "0" : rs1.getString("thanhtien");
							if(thanhtien.length() <= 0)
								thanhtien = "0";
							tongtien += Double.parseDouble(thanhtien);
							nvbh = rs1.getString("nhanvienbanhang");
							//ghichu = rs1.getString("ghichu");
						}
						stt++;
						
						if( sanpham.trim().length() > 0 )
							sanpham = sanpham.substring(0, sanpham.length() - 2);
						
						System.out.println("IN SAN PHAM...............");
						cell5[0] = new PdfPCell(new Paragraph(""+stt, font8normal));
						cell5[0].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell5[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
						table4.addCell(cell5[0]);

						cell5[1] = new PdfPCell(new Paragraph(sochungtu, font8normal));
						cell5[1].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell5[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
						table4.addCell(cell5[1]);

						cell5[2] = new PdfPCell(new Paragraph(khachhangTXHD, font8normal));
						cell5[2].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell5[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
						table4.addCell(cell5[2]);
						
						cell5[2] = new PdfPCell(new Paragraph(khachhangTEN, font8normal));
						cell5[2].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell5[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
						table4.addCell(cell5[2]);

						cell5[3] = new PdfPCell(new Paragraph(diachi, font8normal));
						cell5[3].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell5[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
						table4.addCell(cell5[3]);

						cell5[4] = new PdfPCell(new Paragraph(sanpham, font8normal));
						cell5[4].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell5[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
						table4.addCell(cell5[4]);
						
						
						tongthanhtien += tongtien;
						cell5[5] = new PdfPCell(new Paragraph(formatter2.format(tongtien), font8normal));
						cell5[5].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell5[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
						table4.addCell(cell5[5]);

						cell5[6] = new PdfPCell(new Paragraph(nvbh, font8normal));
						cell5[6].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell5[6].setVerticalAlignment(Element.ALIGN_MIDDLE);
						table4.addCell(cell5[6]);

						cell5[7] = new PdfPCell(new Paragraph(ghichu, font8normal));
						cell5[7].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell5[7].setVerticalAlignment(Element.ALIGN_MIDDLE);
						table4.addCell(cell5[7]);

					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				
				/*PdfPCell[] cell6 = new PdfPCell[8];
				cell6[0] = new PdfPCell();
				cell6[1] = new PdfPCell();
				cell6[2] = new PdfPCell();
				cell6[3] = new PdfPCell();
				cell6[4] = new PdfPCell(new Paragraph("Tổng tiền:", font8bold));
				cell6[4].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell6[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell6[5] = new PdfPCell(new Paragraph(""+formatter2.format(tongthanhtien), font8bold));
				cell6[5].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell6[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell6[6] = new PdfPCell();
				cell6[7] = new PdfPCell();
				cell6[0].setColspan(4);
				cell6[6].setColspan(2);
				table4.addCell(cell6[0]);
				table4.addCell(cell6[4]);
				table4.addCell(cell6[5]);
				table4.addCell(cell6[6]);*/

			} catch (Exception e) {

				e.printStackTrace();
			}
			
			PdfPCell[] cell6 = new PdfPCell[3];
			cell6[0] = new PdfPCell(new Paragraph("Tổng tiền:", font8bold));
			cell6[0].setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell6[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell6[0].setColspan(6);
			
			cell6[1] = new PdfPCell(new Paragraph("" + formatter2.format(tongthanhtien), font8bold));
			cell6[1].setHorizontalAlignment(Element.ALIGN_CENTER);
			cell6[1].setVerticalAlignment(Element.ALIGN_MIDDLE);

			cell6[2] = new PdfPCell(new Paragraph("", font8bold));
			cell6[2].setColspan(2);
			
			table4.addCell(cell6[0]);
			table4.addCell(cell6[1]);
			table4.addCell(cell6[2]);

			document.add(table4);		

			float[] withstongluong = { 4.0f };
			PdfPTable tabletl = new PdfPTable(withstongluong);
			tabletl.setWidthPercentage(100);
			tabletl.setSpacingBefore(10);
			tabletl.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell[] celltl = new PdfPCell[1];
			celltl[0] = new PdfPCell(new Paragraph("TỔNG SỐ LƯỢNG MÀ KHO GIAO CHO NHÂN VIÊN GIAO HÀNG", font2));
			celltl[0].setHorizontalAlignment(Element.ALIGN_CENTER);
			celltl[0].setBackgroundColor(BaseColor.LIGHT_GRAY);
			tabletl.addCell(celltl[0]);
			document.add(tabletl);	
			
			//query = " select hoadon_fk from ERP_SOXUATHANGNPP_DDH where soxuathang_fk ="+id;
			//ResultSet rsdhid1 = db.get(query);
		
			//float[] withstl = { 40.0f, 20.0f,10.0f, 10.0f, 20.0f};
			float[] withstl = { 60.0f, 20.0f,10.0f, 10.0f};
			PdfPTable tablesp = new PdfPTable(withstl);
			//String[] th1 = new String[] { "Tên sản phẩm", "Số lô", "Hạn dùng", "Số lượng", "Giá sản phẩm"};
			String[] th1 = new String[] { "Tên sản phẩm", "Số lô", "Hạn dùng", "Số lượng", };
			PdfPCell[] cellsp = new PdfPCell[4];
			for (int i = 0; i < th1.length; i++)
			{
				cellsp[i] = new PdfPCell(new Paragraph(th1[i], font2));
				cellsp[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cellsp[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				tablesp.addCell(cellsp[i]);
			}	
			
			tablesp.setWidthPercentage(100);
			tablesp.setHorizontalAlignment(Element.ALIGN_LEFT);
			try {
				/*while(rsdhid1.next())
				{*/
					/*query = "select  b.TEN, a.solo, a.ngayhethan, a.soluong, ROUND( ( c.dongia * ( 1 + b.thuexuat / 100.0 )), 0 ) as dongia  "+
							 "from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA "+
							 "		inner join ERP_HOADONNPP_SP c on a.hoadon_fk = c.HOADON_FK and b.PK_SEQ = c.SANPHAM_FK "+
							 "where a.hoadon_fk = '" + rsdhid1.getString("hoadon_fk") + "' ";*/

					query = "select TEN, solo, ngayhethan, sum(soluong) as soluong, AVG( dongia ) as dongia  "+
							 "from "+
							 "( "+
							 "	select  b.TEN, a.solo, isnull(a.ngayhethan, '') as ngayhethan, a.soluong, "+
							 "			( select AVG( round( DONGIA * ( 1 + vat / 100.0 ), 0 ) ) from ERP_HOADONNPP_SP where sanpham_fk = b.PK_SEQ and HOADON_FK in ( select hoadon_fk from ERP_SOXUATHANGNPP_DDH where soxuathang_fk = '" + id + "' ) ) as dongia "+
							 "	from ERP_SOXUATHANGNPP_SANPHAM_CHITIET a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
							 "	where a.soxuathang_fk = '" + id + "' "+
							 ") "+
							 "SXH "+
							 "group by TEN, solo, ngayhethan ";
					
					System.out.println("Form In so xuat hang: " + query);
					ResultSet rs1 = db.get(query);
					try {
						PdfPCell[] cell5 = new PdfPCell[8];
						while(rs1.next())
						{
							cell5[0] = new PdfPCell(new Paragraph(""+rs1.getString("Ten"), font8normal));
							cell5[0].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell5[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
							tablesp.addCell(cell5[0]);

							cell5[1] = new PdfPCell(new Paragraph(rs1.getString("solo"), font8normal));
							cell5[1].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell5[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
							tablesp.addCell(cell5[1]);

							cell5[2] = new PdfPCell(new Paragraph(rs1.getString("ngayhethan"), font8normal));
							cell5[2].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell5[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
							tablesp.addCell(cell5[2]);

							cell5[3] = new PdfPCell(new Paragraph(formatter2.format( rs1.getDouble("soluong") ), font8normal));
							cell5[3].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell5[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
							tablesp.addCell(cell5[3]);
							
							/*String dongia = rs1.getString("dongia");
							if(dongia.length() <= 0)
								dongia = "0";
							cell5[4] = new PdfPCell(new Paragraph(formatter2.format(Double.parseDouble(dongia)), font8normal));
							cell5[4].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell5[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
							tablesp.addCell(cell5[4]);*/

						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				//}
					
				document.add(tablesp);		

				Paragraph ngaythang = new Paragraph("Ngày "+getDateTime().substring(0,2)+" Tháng "+getDateTime().substring(3,5)+" Năm "+getDateTime().substring(6,10), font2);
				ngaythang.setSpacingBefore(10);
				ngaythang.setAlignment(Element.ALIGN_RIGHT);
				document.add(ngaythang);
				
				//Table Footer			
				PdfPTable tableFooter = new PdfPTable(4);
				tableFooter.setWidthPercentage(100);
				tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
				tableFooter.setSpacingBefore(15);
				tableFooter.setWidths(new float[]{60.0f, 60.0f, 60.0f, 60.0f});
				
				PdfPCell cell11 = new PdfPCell(new Paragraph("Người giao hàng", font2));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell13 = new PdfPCell(new Paragraph(" ", font2));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell14 = new PdfPCell(new Paragraph(" ", font2));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell cell12 = new PdfPCell(new Paragraph("Người lập", font2));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell11.setBorder(0);
				cell12.setBorder(0);
				cell14.setBorder(0);
				cell13.setBorder(0);
				
				tableFooter.addCell(cell11);
				tableFooter.addCell(cell13);
				tableFooter.addCell(cell14);
				tableFooter.addCell(cell12);

				
				//KHOANG TRANG
				cell11 = new PdfPCell(new Paragraph(" ", font2));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell12 = new PdfPCell(new Paragraph(" ", font2));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14 = new PdfPCell(new Paragraph(" ", font2));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell13 = new PdfPCell(new Paragraph(" ", font2));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell11.setBorder(0);
				cell12.setBorder(0);
				cell13.setBorder(0);
				cell14.setBorder(0);

				tableFooter.addCell(cell11);
				tableFooter.addCell(cell13);
				tableFooter.addCell(cell14);
				tableFooter.addCell(cell12);
				//
				
				
				cell11 = new PdfPCell(new Paragraph(nguoigh, font2));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell12 = new PdfPCell(new Paragraph(nguoilap, font2));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell14 = new PdfPCell(new Paragraph(" ", font2));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell13 = new PdfPCell(new Paragraph(" ", font2));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cell11.setBorder(0);
				cell12.setBorder(0);
				cell13.setBorder(0);
				cell14.setBorder(0);

				tableFooter.addCell(cell11);
				tableFooter.addCell(cell13);
				tableFooter.addCell(cell14);
				tableFooter.addCell(cell12);
				
				document.add(tableFooter);
				
				document.close();
			} 
			catch (DocumentException e)
			{
				e.printStackTrace();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
