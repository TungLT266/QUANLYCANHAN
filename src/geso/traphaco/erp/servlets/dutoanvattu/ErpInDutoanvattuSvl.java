package geso.traphaco.erp.servlets.donmuahang;

import geso.traphaco.erp.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import geso.traphaco.erp.beans.donmuahang.IErpDonmuahang;
import geso.traphaco.erp.beans.donmuahang.ISanpham;
import geso.traphaco.erp.beans.donmuahang.imp.ErpDonmuahang;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/ErpInDonMuaHangSvl")
public class ErpInDonMuaHangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpInDonMuaHangSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.*
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{

			Utility util = new Utility();
			String querystring = request.getQueryString();
			String dhid = util.getId(querystring);
			System.out.println(dhid);
			userId = util.getUserId(querystring);

			IErpDonmuahang dhBean = new ErpDonmuahang(dhid);
			dhBean.init();

			//

			response.setContentType("application/pdf");
			// response.setHeader("Content-Disposition"," inline; filename=Phieuxuatkho.pdf");

			// hien thi hop thoai dialog save file xuong may Client
			// response.setHeader("Content-Disposition"," attachment; filename=\"Phieuxuatkho.pdf\" ");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			this.CreateDonMuaHang(document, outstream, dhBean);
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

	private void CreateDonMuaHang(Document document, ServletOutputStream outstream, IErpDonmuahang dhBean) throws IOException
	{
		try
		{
			List<ISanpham> spList = dhBean.getSpList();
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###.##");

			PdfWriter.getInstance(document, outstream);
			document.open();
			// lay doi tuong khach hang

			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font fonttb = new Font(bf, 12, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 8, Font.UNDERLINE);
			Font fontnomar = new Font(bf, 10, Font.NORMAL);
			Font font8normal = new Font(bf, 8, Font.NORMAL);

			// heder
			//
			PdfPTable tableheader = new PdfPTable(2);// Chu y nhe 7 cot o day,
														// thi xuong duoi nho
														// set withs la 6 cot
			tableheader.setWidthPercentage(100);// chieu dai cua báº£ng
			tableheader.setSpacingAfter(14);
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withheder =
			{ 6.0f, 3.0f };

			tableheader.setWidths(withheder);

			// logo
			// Image
			// hinhanh=Image.getInstance("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/TraphacoHYERP/pages/images/logo.png");
			// Image hinhanh =Image.getInstance("path")+"\\logo.png";
			String logo = getServletContext().getInitParameter("path") + "\\logo.png";

			Image hinhanh = Image.getInstance(logo);
			if (hinhanh == null)
			{
				return;
			}

			hinhanh.scaleAbsolute(100, 40);

			PdfPCell cell111 = new PdfPCell();

			cell111.addElement(hinhanh);

			//

			cell111.addElement(new Paragraph("", fontnomar));

			cell111.addElement(new Paragraph("Lầu 3, Tòa nhà Vina Giầy, 180-182 Lý Chính Thắng, P.9, Q.3, TP.HCM", fontnomar));
			cell111.addElement(new Paragraph("Tel : (08) 6290 5560 - Fax(08) 6290 5104", fontnomar));
			cell111.addElement(new Paragraph("Tax Code : 0310776071", fontnomar));

			cell111.setBorder(0);

			tableheader.addCell(cell111);

			PdfPCell cell2 = new PdfPCell();
			cell2.addElement(new Paragraph("Mã số ", font8normal));
			cell2.addElement(new Paragraph("Ngày hiệu lực ", font8normal));
			cell2.addElement(new Paragraph("Lần soát xét :01", font8normal));
			cell2.addElement(new Paragraph("Trang :1 of 1", font8normal));

			cell2.setBorder(0);
			tableheader.addCell(cell2);

			document.add(tableheader);

			// end header
			// font2.setColor(BaseColor.GREEN);
			// KHAI BAO 1 BANG CO BAO NHIEU COT

			Paragraph tieude = new Paragraph("ĐƠN ĐẶT HÀNG", font);
			tieude.setAlignment(Element.ALIGN_CENTER);
			document.add(tieude);

			Paragraph tieudeE = new Paragraph("(PURCHASE ORDER)", fonttb);
			tieudeE.setAlignment(Element.ALIGN_CENTER);
			document.add(tieudeE);

			dbutils db = new dbutils();
			String sql = "select isnull(diachi,'') as diachi,isnull(dienthoai,'') as dienthoai,isnull(fax,'') as fax from erp_nhacungcap where pk_seq=" + dhBean.getNCC().split("-")[0];
			ResultSet rs = db.get(sql);
			String tel_ = "";
			String Fax_ = "";
			String Addr = "";
			if (rs != null)
			{
				try
				{
					if (rs.next())
					{
						tel_ = rs.getString("diachi");
						Fax_ = rs.getString("dienthoai");
						Addr = rs.getString("fax");
					}
					rs.close();
				}
				catch (Exception er)
				{

				}
			}

			ResultSet rsDvth;
			String dvth = "";
			sql = "Select Ten From ERP_DONVITHUCHIEN Where PK_SEQ=" + dhBean.getDvthId();
			rsDvth = db.get(sql);
			if (rsDvth != null)
				try
				{
					if (rsDvth.next())
						dvth = rsDvth.getString("Ten");
					rsDvth.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			/*----------------Tien Te--------------------*/
			ResultSet rsTienTe;
			String  tiente="";
			sql = "Select MA From ERP_TIENTE Where PK_SEQ = " + dhBean.getTienTe_FK();
			System.out.println(sql);
			rsTienTe = db.get(sql);
			if (rsDvth != null)
				try
				{
					if (rsTienTe.next())
						tiente = rsTienTe.getString("Ma");
					rsTienTe.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			/*----------------Tien Te--------------------*/
			db.shutDown();

			// / THONG TIN 2 BEN MUA HANG

			PdfPTable table1 = new PdfPTable(2);// Chu y nhe 7 cot o day, thi
												// xuong duoi nho set withs la 6
												// cot
			table1.setWidthPercentage(100);// chieu dai cua báº£ng
			table1.setSpacingBefore(14);

			table1.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withfooter =
			{ 4.0f, 4.0f };

			PdfPCell cells_detail = new PdfPCell(new Paragraph("TO : " + dhBean.getNCC(), font2));

			cells_detail.setBorder(0);
			table1.addCell(cells_detail);

			cells_detail = new PdfPCell(new Paragraph("PO No: " + dhBean.getSochungtu(), font2));

			cells_detail.setBorder(0);
			table1.addCell(cells_detail);

			cells_detail = new PdfPCell(new Paragraph("Addr : " + tel_, font2));

			cells_detail.setBorder(0);
			table1.addCell(cells_detail);
			cells_detail = new PdfPCell(new Paragraph(" ", font2));

			cells_detail.setBorder(0);
			table1.addCell(cells_detail);

			cells_detail = new PdfPCell(new Paragraph("Tel: " + Addr, font2));

			cells_detail.setBorder(0);
			table1.addCell(cells_detail);

			cells_detail = new PdfPCell(new Paragraph("From : " + dvth, font2));

			cells_detail.setBorder(0);
			table1.addCell(cells_detail);

			cells_detail = new PdfPCell(new Paragraph("Fax : " + Fax_, font2));

			cells_detail.setBorder(0);
			table1.addCell(cells_detail);

			cells_detail = new PdfPCell(new Paragraph("Date : " + dhBean.getNgaymuahang(), font2));

			cells_detail.setBorder(0);
			table1.addCell(cells_detail);

			cells_detail = new PdfPCell(new Paragraph("Attn : ", font2));

			cells_detail.setBorder(0);
			table1.addCell(cells_detail);

			cells_detail = new PdfPCell(new Paragraph("PO return : ", font2));

			cells_detail.setBorder(0);
			table1.addCell(cells_detail);

			document.add(table1);

			// /KET THUC THONG TIN 2 BEN MUA BAN

			// THONG TIN BANG DE NGHI

			PdfPTable table2 = new PdfPTable(2);// Chu y nhe 7 cot o day, thi
												// xuong duoi nho set withs la 6
												// cot
			table2.setWidthPercentage(100);// chieu dai cua báº£ng
			table2.setSpacingAfter(14);
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] with2 =
			{ 8.0f, 3.0f };

			table2.setWidths(with2);

			cell111 = new PdfPCell();

			cell111.addElement(new Paragraph("Thưa Ông (Bà),/Dear Sir(Madam),", fontnomar));

			cell111.addElement(new Paragraph("Chúng tôi xin đặt mua các mặt hàng/nguyên vật liệu như sau :", fontnomar));
			cell111.addElement(new Paragraph("We have pleasure in ordering the goods/material specified below :", fontnomar));

			cell111.setBorder(0);

			table2.addCell(cell111);
			cell2 = new PdfPCell();
			cell2.addElement(new Paragraph(" ", fontnomar));
			cell2.addElement(new Paragraph(" ", fontnomar));
			cell2.addElement(new Paragraph("Đ.vị tiền tệ/Currency : "+tiente, fontnomar));
			cell2.setBorder(0);
			table2.addCell(cell2);

			document.add(table2);

			PdfPTable table = new PdfPTable(8);// Chu y nhe 7 cot o day, thi
												// xuong duoi nho set withs la 6
												// cot

			table.setWidthPercentage(100);// chieu dai cua báº£ng
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs =
			{ 4.0f, 11.0f, 20.0f, 10.0f, 8.0f, 10.0f, 10.0f, 10.0f };
			table.setWidths(withs);
			String[] th = new String[]
			{ "STT", "Mã hàng/ Material-Code", "Tên Hàng/ Material-Description ", "Ngày giao Delivery-date", "ĐVT/ Unit", "Số lượng/ Quantity ", "Đơn giá /  Unit-price", "Thành tiền Total-Price" };
			PdfPCell[] cell = new PdfPCell[8];
			for (int i = 0; i < 8; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell[i]);
			}

			float size = 8.5f;
			Font font4 = new Font(bf, size);
			cells_detail = new PdfPCell();
			double totalle = 0;

			for (int i = 0; i < spList.size(); i++)
			{
				ISanpham sanpham = (ISanpham) spList.get(i);

				totalle = totalle + Double.parseDouble(sanpham.getSoluong()) * Double.parseDouble(sanpham.getDongia());

				String[] arr = new String[]
				{ Integer.toString(i + 1), sanpham.getMasanpham(), sanpham.getTensanpham(), sanpham.getNgaynhan(), sanpham.getDonvitinh(), "" + sanpham.getSoluong(), formatter1.format(Double.parseDouble(sanpham.getDongia())) + "", formatter1.format(Double.parseDouble(sanpham.getSoluong()) * Double.parseDouble(sanpham.getDongia())) };

				for (int j = 0; j < 8; j++)
				{
					cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
					if (j == 2)
					{
						cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);

					}
					else
					{
						cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
					// cells_detail.setBorderWidthBottom(0);
					// cells_detail.setBorderWidthTop(0);
					table.addCell(cells_detail);

				}
			}

			cells_detail = new PdfPCell();
			cells_detail.addElement(new Paragraph("", fontnomar));

			cells_detail.setColspan(5);

			cells_detail.setBorderWidthRight(0);
			table.addCell(cells_detail);

			cells_detail = new PdfPCell();
			cells_detail.addElement(new Paragraph("Tổng cộng / Sub Total :", fontnomar));
			cells_detail.setBorderWidthLeft(0);
			cells_detail.setBorderWidthRight(0);
			cells_detail.setColspan(2);

			table.addCell(cells_detail);

			cells_detail = new PdfPCell(new Paragraph(formatter1.format(totalle), font2));
			cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells_detail.setBorderWidthLeft(0);

			table.addCell(cells_detail);

			cells_detail = new PdfPCell();
			cells_detail.addElement(new Paragraph("", fontnomar));

			cells_detail.setBorderWidthRight(0);
			cells_detail.setColspan(5);

			table.addCell(cells_detail);
			cells_detail = new PdfPCell();
			cells_detail.addElement(new Paragraph("Vat(+" + dhBean.getVat() + ") %:", fontnomar));
			cells_detail.setBorderWidthLeft(0);
			cells_detail.setBorderWidthRight(0);
			cells_detail.setColspan(2);
			table.addCell(cells_detail);

			cells_detail = new PdfPCell(new Paragraph(formatter1.format(totalle * 0.1), font2));
			cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells_detail.setBorderWidthLeft(0);

			table.addCell(cells_detail);

			cells_detail = new PdfPCell();
			cells_detail.addElement(new Paragraph("", fontnomar));

			cells_detail.setBorderWidthRight(0);
			cells_detail.setColspan(5);

			table.addCell(cells_detail);
			cells_detail = new PdfPCell();
			cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells_detail.addElement(new Paragraph("Tổng cộng/ Grand Total :", fontnomar));
			cells_detail.setBorderWidthLeft(0);
			cells_detail.setBorderWidthRight(0);
			cells_detail.setColspan(2);

			table.addCell(cells_detail);

			cells_detail = new PdfPCell(new Paragraph(formatter1.format(totalle * 1.1), font2));
			cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells_detail.setBorderWidthLeft(0);

			table.addCell(cells_detail);

			document.add(table);

			// Bang Phuong Thuc Thanh Toan

			String query = "Select ";

			PdfPTable table3 = new PdfPTable(2);// Chu y nhe 7 cot o day, thi
												// xuong duoi nho set withs la 6
												// cot
			table3.setWidthPercentage(100);// chieu dai cua báº£ng
			table3.setSpacingAfter(14);
			// table3.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] with3 =
			{ 5.0f, 5.0f };

			table3.setWidths(with3);

			cell111 = new PdfPCell();

			cell111.addElement(new Paragraph("Phương thức thanh toán/Payment terms:", fontnomar));

			cell111.addElement(new Paragraph("Phương thức giao hàng/Delivery terms :", fontnomar));
			cell111.addElement(new Paragraph("Nơi giao hàng :", fontnomar));
			cell111.addElement(new Paragraph("Notes 1 (ghi chú) :"+dhBean.getGhiChu() , fontnomar));

			cell111.setBorder(0);

			table3.addCell(cell111);

			cell2 = new PdfPCell();
			cell2.addElement(new Paragraph(" ", fontnomar));

			cell2.setBorder(0);
			table3.addCell(cell2);

			// hang 2
			cell111 = new PdfPCell();

			cell111.addElement(new Paragraph("      Chấp thuận bởi người bán", fontnomar));

			cell111.addElement(new Paragraph("       Accepted  by Supplier : ", fontnomar));
			cell111.addElement(new Paragraph("", fontnomar));
			cell111.addElement(new Paragraph("", fontnomar));
			cell111.addElement(new Paragraph("", fontnomar));
			cell111.addElement(new Paragraph("", fontnomar));
			cell111.addElement(new Paragraph("", fontnomar));

			cell111.setBorder(0);

			table3.addCell(cell111);

			cell2 = new PdfPCell();
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.addElement(new Paragraph("Công ty PROVENCE", font2));

			cell2.setBorder(0);
			table3.addCell(cell2);
			// hang 3
			cell111 = new PdfPCell();
			cell111.addElement(new Paragraph(".........................................", fontnomar));

			cell111.addElement(new Paragraph("Tên và chữ ký (Name & Signature ) ", fontnomar));
			cell111.addElement(new Paragraph("", fontnomar));
			cell111.addElement(new Paragraph("", fontnomar));

			cell111.setBorder(0);

			table3.addCell(cell111);
			table3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell111 = new PdfPCell();
			cell111.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell111.addElement(new Paragraph(".........................................", fontnomar));

			cell111.addElement(new Paragraph("Tên và chữ ký (Name & Signature ) ", fontnomar));
			cell111.addElement(new Paragraph("", fontnomar));
			cell111.addElement(new Paragraph("", fontnomar));
			cell111.setBorder(0);
			table3.addCell(cell111);

			document.add(table3);
			// ket thuc

			document.close();
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
	}

}
