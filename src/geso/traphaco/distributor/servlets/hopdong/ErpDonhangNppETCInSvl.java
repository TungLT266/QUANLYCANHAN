package geso.traphaco.distributor.servlets.hopdong;

import geso.traphaco.center.beans.mucchietkhau.imp.Chietkhau;
import geso.traphaco.distributor.beans.hopdong.IErpDonhangNppETC;
import geso.traphaco.distributor.beans.hopdong.imp.ErpDonhangNppETC;
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
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.GroupLayout.Alignment;

import com.aspose.cells.Color;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpDonhangNppETCInSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpDonhangNppETCInSvl()
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
			String querystring = request.getQueryString();
			String dhid = util.getId(querystring);
			System.out.println(dhid);
			userId = util.getUserId(querystring);
			 String nppId = request.getParameter("nppId");
			    if(nppId == null)
			    	nppId = "";
			  
			IErpDonhangNppETC dhBean = new ErpDonhangNppETC(dhid);
			dhBean.setNppId(nppId);
			dhBean.setUserId(userId);
			dhBean.init();
			
			response.setContentType("application/pdf");

			Document document = new Document();
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

	private void CreatePxk(Document document, ServletOutputStream outstream, IErpDonhangNppETC dhBean,String id,String userid) throws IOException, DocumentException
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

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\cour.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
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
			String query = "select ten,diachi from nhaphanphoi where pk_seq = "+ util.getIdNhapp(dhBean.getUserId());
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
			String khohanghoa ="";
			query = " select b.ten from ERP_DONDATHANGNPP a inner join KHO b on a.Kho_FK = b.pk_seq where a.PK_SEQ = "+id;
			ResultSet khors = db.get(query);
			try 
			{
				if(khors.next())
				{
					khohanghoa = khors.getString("ten");
				}
			} catch (SQLException e1)
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

			Paragraph tieudebm = new Paragraph("BM-20-1" , font4normal);
			tieudebm.setIndentationLeft(10);			
			tieudebm.setAlignment(Element.ALIGN_RIGHT);
			document.add(tieudebm);
			String str_tieude = "ĐƠN ĐẶT HÀNG";
			Paragraph tieude = new Paragraph(str_tieude, font);
			tieude.setAlignment(Element.ALIGN_CENTER);
			tieude.setSpacingAfter(10);
			document.add(tieude);


			String nguoilap ="";
			query = " select ten from nhanvien where pk_seq = '"+userid+"'" ;
			System.out.println("Nhan vien dang nhap: "+query);
			ResultSet nguoilaprs = db.get(query);
			try 
			{
				if(nguoilaprs.next())
				{
					nguoilap = nguoilaprs.getString("ten");
				}
			} catch (SQLException e1)
			{

				e1.printStackTrace();
			}
			String makh = "",tenkh = "",diachikh = "",nvbh = "",ghichu = "",dienthoai = "";
			query = "select b.ma as makh, b.TEN, isnull(b.DIACHI,'') as diachi,c.ten as nvbh,isnull(a.GHICHU,'') as ghichu, isnull(b.DIENTHOAI,'') as dienthoai "+
					"from ERP_DONDATHANGNPP a inner join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ inner join DAIDIENKINHDOANH c on a.DDKD_FK = c.PK_SEQ "+
					" where a.PK_SEQ = "+id;
			ResultSet ttkhrs = db.get(query);
			try 
			{
				if(ttkhrs.next())
				{
					makh = ttkhrs.getString("makh");
					tenkh = ttkhrs.getString("ten");
					diachi = ttkhrs.getString("DIACHI");
					nvbh = ttkhrs.getString("nvbh");
					ghichu = ttkhrs.getString("ghichu");
					dienthoai = ttkhrs.getString("dienthoai");
				}
			} catch (SQLException e1)
			{

				e1.printStackTrace();
			}
			float[] withs1 =
				{ 4.0f};
			PdfPTable table1 = new PdfPTable(withs1);
			table1.setWidthPercentage(100);
			table1.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell[] cell1 = new PdfPCell[1];
			cell1[0] = new PdfPCell(new Paragraph("Thông tin chứng từ", font2));
			cell1[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			cell1[0].setBackgroundColor(BaseColor.GRAY);
			table1.addCell(cell1[0]);
			document.add(table1);
			float[] withs2 =
				{ 50f,50f };
			PdfPTable table2 = new PdfPTable(withs2);
			table2.setWidthPercentage(100);
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell[] cell2 = new PdfPCell[2];
			cell2[0] = new PdfPCell(new Paragraph("Mã chứng từ: "+id, font8normal));
			cell2[1] = new PdfPCell(new Paragraph("Ngày lập: "+getDateTime(), font8normal));
			table2.addCell(cell2[0]);
			table2.addCell(cell2[1]);
			cell2[0] = new PdfPCell(new Paragraph("Mã khách hàng: "+makh, font8normal));
			cell2[1] = new PdfPCell(new Paragraph("Khách hàng: "+tenkh, font8normal));
			table2.addCell(cell2[0]);
			table2.addCell(cell2[1]);
			cell2[0] = new PdfPCell(new Paragraph("Địa chỉ: "+diachi, font8normal));
			cell2[1] = new PdfPCell(new Paragraph("Địa chỉ giao hàng: "+diachi, font8normal));
			table2.addCell(cell2[0]);
			table2.addCell(cell2[1]);
			cell2[0] = new PdfPCell(new Paragraph("Nhân viên bán hàng: "+nvbh, font8normal));
			cell2[1] = new PdfPCell(new Paragraph("Người giao hàng: ", font8normal));
			table2.addCell(cell2[0]);
			table2.addCell(cell2[1]);
			cell2[0] = new PdfPCell(new Paragraph("Điện thoại: "+dienthoai, font8normal));
			cell2[1] = new PdfPCell(new Paragraph("Ghi chú: "+ghichu, font8normal));
			table2.addCell(cell2[0]);
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
			float[] withs4 =
				{8.0f, 10.0f,25.0f, 25.0f, 27.0f, 20.0f,17.0f };
			PdfPTable table4 = new PdfPTable(withs4);
			table4.setWidthPercentage(100);
			table4.setHorizontalAlignment(Element.ALIGN_LEFT);
			String[] th = new String[]
					{ "STT", "Mã sản phẩm","Tên sản phẩm", "ĐVT", "Số lượng", "Giá sản phẩm", "Thành tiền"};
			PdfPCell[] cell4 = new PdfPCell[7];
			for (int i = 0; i < th.length; i++)
			{
				cell4[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell4[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4[i].setBackgroundColor(BaseColor.GRAY);
				table4.addCell(cell4[i]);
			}	
			String dhid = "";
			double tongthanhtien = 0;
			double tongtien = 0;
			ResultSet Sprs = dhBean.getSPRs();
			double chietkhau = 0;
			int stt = 0;
			try {
				

				String masp = "";
				String tensp = "";
				String donvi = "";
				String soluong  = "";
				double thanhtien = 0;
				 chietkhau = 0;
				double gia = 0;
				while (Sprs.next())
				{


					PdfPCell[] cell5 = new PdfPCell[7];
					masp = Sprs.getString("ma");
					tensp = Sprs.getString("ten");
					donvi =  Sprs.getString("donvi");
					soluong = Sprs.getString("soluong");
					gia = Sprs.getDouble("dongia");
					thanhtien = Double.parseDouble(soluong)*gia;
					tongtien += thanhtien;
					stt++;
					cell5[0] = new PdfPCell(new Paragraph(""+stt, font8normal));
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

					cell5[3] = new PdfPCell(new Paragraph(donvi, font8normal));
					cell5[3].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[3]);

					cell5[4] = new PdfPCell(new Paragraph(soluong, font8normal));
					cell5[4].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[4]);

					cell5[5] = new PdfPCell(new Paragraph(formatter2.format(gia), font8normal));
					cell5[5].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[5]);

					cell5[6] = new PdfPCell(new Paragraph(formatter2.format(thanhtien), font8normal));
					cell5[6].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[6].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[6]);


				}
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			try {



				PdfPCell[] cell6 = new PdfPCell[7];
				cell6[0] = new PdfPCell(new Paragraph("Tổng đơn vị sản phẩm "+stt+"(bằng chữ ): "+reader(stt), font8normal));
				cell6[1] = new PdfPCell();
				cell6[2] = new PdfPCell();
				cell6[3] = new PdfPCell();
				cell6[4] = new PdfPCell(new Paragraph("Thành tiền:   "+formatter2.format(tongtien), font8bold));
				cell6[4].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell6[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell6[5] = new PdfPCell(new Paragraph("", font8bold));
				cell6[5].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell6[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell6[4].setColspan(2);
				cell6[6] = new PdfPCell();
				cell6[0].setColspan(5);
				table4.addCell(cell6[0]);
				table4.addCell(cell6[4]);
				
				PdfPCell[] cell7 = new PdfPCell[7];
				cell7[0] = new PdfPCell(new Paragraph("Chiết khấu(0%)      "+chietkhau, font8bold));
				cell7[0].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell7[0].setColspan(7);
				table4.addCell(cell7[0]);
				
				PdfPCell[] cell8 = new PdfPCell[7];
				cell8[0] = new PdfPCell(new Paragraph("Tổng tiền (bằng chữ ): ", font8normal));
				cell8[1] = new PdfPCell();
				cell8[2] = new PdfPCell();
				cell8[3] = new PdfPCell();
				cell8[4] = new PdfPCell(new Paragraph("Tổng tiền:   "+formatter2.format(tongtien-(tongtien *(chietkhau/100.0))), font8bold));
				cell8[4].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell8[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell8[5] = new PdfPCell(new Paragraph("", font8bold));
				cell8[5].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell8[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell8[4].setColspan(2);
				cell8[6] = new PdfPCell();
				cell8[0].setColspan(5);
				table4.addCell(cell8[0]);
				table4.addCell(cell8[4]);
				table4.addCell(cell8[5]);

			} catch (Exception e) {

				e.printStackTrace();
			}
			document.add(table4);		


			PdfPCell[] cell6 = new PdfPCell[8];
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
			cell6[0].setColspan(5);
			cell6[6].setColspan(2);
			table4.addCell(cell6[0]);
			table4.addCell(cell6[4]);
			table4.addCell(cell6[5]);

			document.close();
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
	}
	String  NumberString[] = { "không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín", "mười", "trăm", "nghìn", "triệu", "tỷ" };
	String SubNumberString[] = { "mốt", "lăm", "lẻ", "mươi" };
	long number;
	public String reader()
	{
		
		
	   String numberString = ""+number;
	   String result = "";
	   String sub = "";
	    boolean le = false;
	    while (numberString.length() > 0)
	    {
	       String temp;
	        if (numberString.length() > 3)
	        {
	            int t = numberString.length() % 3;
	            if (t == 0) t = 3;
	            temp = numberString.substring(0, t);
	            if (temp == "000") le = true;
	            int d = numberString.length() - 1;
	            d = d / 3;
	            d = d % 3;
	            switch (d)
	            {
	                case 0: sub = NumberString[14] + " "; break;
	                case 1: sub = NumberString[12] + " "; break;
	                case 2: sub = NumberString[13] + " "; break;
	            }
	            numberString = numberString.substring(t, numberString.length() - t);
	        }
	        else
	        {
	            temp = numberString;
	            numberString = "";
	            sub = "";
	        }
	        while (temp.length() > 0)
	        {
	            if ((temp != "000") && (temp != "00") && (temp != "0"))
	            {
	                if (le) { result += "lẻ "; le = false; }
	                switch (temp.charAt(0))
	                {
	                    case '0':
	                        {
	                            if (temp.length() == 3)
	                            {
	                                result += NumberString[0] + " " + NumberString[11] + " ";
	                            }
	                            else if (temp.length() == 2)
	                            {
	                                result += SubNumberString[2] + " ";
	                            }
	                            else
	                            {
	                                result += NumberString[0] + " ";
	                            }
	                            break;
	                        }
	                    case '1':
	                        {
	                            if (temp.length() == 3)
	                            {
	                                result += NumberString[1] + " " + NumberString[11] + " ";
	                            }
	                            else if (temp.length() == 2)
	                            {
	                                result += NumberString[10] + " ";
	                            }
	                            else
	                            {
	                                result += NumberString[1] + " ";
	                            }
	                            break;
	                        }
	                    case '2':
	                        {
	                            result = Convert(temp, NumberString[2], result);
	                            break;
	                        }
	                    case '3':
	                        {
	                            result = Convert(temp, NumberString[3], result);
	                            break;
	                        }
	                    case '4':
	                        {
	                            result = Convert(temp, NumberString[4], result);
	                            break;
	                        }
	                    case '5':
	                        {
	                            result = Convert(temp, NumberString[5], result);
	                            break;
	                        }
	                    case '6':
	                        {
	                            result = Convert(temp, NumberString[6], result);
	                            break;
	                        }
	                    case '7':
	                        {
	                            result = Convert(temp, NumberString[7], result);
	                            break;
	                        }
	                    case '8':
	                        {
	                            result = Convert(temp, NumberString[8], result);
	                            break;
	                        }
	                    case '9':
	                        {
	                            result = Convert(temp, NumberString[9], result);
	                            break;
	                        }
	                }
	                System.out.println("temp "+temp);
	                temp = temp.substring(0, temp.length() - 1);
	            }
	            else break;
	        }
	        result += sub;
	    }
	    result = result.replace("mươi năm", "mươi lăm");
	    result = result.replace("mười năm", "mười lăm");
	    result = result.replace("tỷ triệu nghìn", "tỷ");
	    result = result.replace("tỷ triệu", "tỷ");
	    result = result.replace("triệu nghìn", "triệu");
	    result = result.replace("triệu không trăm", "triệu");
	    result = result.replace("lẻ không trăm", "lẻ");
	    result = result.replace("lẻ lẻ", "lẻ");
	    result = result.replace("mươi một", "mươi mốt");
	    if (result.trim().endsWith("lẻ")) result = result.substring(0, result.length() - 3);
	    System.out.println("result "+result);
	    return result;
	}
	  public String Convert(String s1, String s2, String result)
      {
          if (s1.length() == 3)
          {
              result += s2 + " " + NumberString[11] + " ";
          }
          else if (s1.length() == 2)
          {
              result += s2 + " " + SubNumberString[3] + " ";
          }
          else
          {
              result += s2 + " ";
          }
          return result;
      }
	  public String reader(long i)
      {
          number = i;
          return reader();
      }
}
