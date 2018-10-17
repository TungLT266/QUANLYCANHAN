package geso.traphaco.distributor.servlets.phieuxuatkho;

import geso.traphaco.center.beans.doctien.doctienrachu;


import geso.traphaco.distributor.beans.donhang.IDonhang;
import geso.traphaco.distributor.beans.donhang.ISanpham;
import geso.traphaco.distributor.beans.donhang.imp.Donhang;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;

import java.util.Enumeration;
import java.util.List;
import java.util.Hashtable;
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
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Phieuxuatkho_DonhangPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public Phieuxuatkho_DonhangPdfSvl()
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
			
			dbutils db = new dbutils();
			Utility util = new Utility();
			String querystring = request.getQueryString();
			
			if (querystring != null)
			{
				userId = util.getUserId(querystring);
				if (userId.length() == 0)
					userId = util.antiSQLInspection(request.getParameter("userId"));
				
				if (querystring.indexOf("pdf") > 0)
				{
					try
					{
						
						String id = util.getId(querystring);
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition", " inline; filename=Phieuxuatkho.pdf");
						
						ServletOutputStream outstream = response.getOutputStream();
						
						String sql = "select donhang_fk from phieuxuatkho_donhang where pxk_fk=" + id;
						ResultSet rs = db.get(sql);
						Document document = new Document();
						PdfWriter writer=	PdfWriter.getInstance(document, outstream);
						while (rs.next())
						{
							String dhId = rs.getString("donhang_fk");
							createDonHangPdf(document, dhId, db, userId, outstream);
							document.newPage();
							
						}
						rs.close();
						document.close();
						outstream.flush();
						outstream.close();
						db.shutDown();
						util = null;
						
					} catch (Exception er)
					{
						er.printStackTrace();
					}
				}
				
			}
		}
		
	}
	
	private void createDonHangPdf(Document document, String dhid, dbutils db, String userId, ServletOutputStream outstream) throws IOException
	{
		try
		{
			
			IDonhang dhBean;
			
			System.out.println("[DonHangId__createDonHangPdf]" + dhid);
			dhBean = new Donhang(dhid);
			dhBean.setUserId(userId); // phai co UserId truoc khi Init
			dhBean.init();
			dhBean.setKhId(dhBean.getKhId());
			List<ISanpham> splist = (List<ISanpham>) dhBean.getSpList();
			List<ISanpham> spkmlist = (List<ISanpham>) dhBean.getScheme_SpList();
			Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien();
			Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau();
			System.out.println("[DonHangId__CreatePxk]" + dhBean.getId());
			
			String tennpp = "";
			String diachi = "";
			String dienthoai = "";
			String masothue = "";
			
			String sql_getinfodis = "select ten,isnull(diachi,'Chua xac dinh') as diachi,isnull( masothue,'Chua xac dinh') as masothue ,isnull( dienthoai,'Chua xac dinh') as dienthoai from nhaphanphoi where pk_seq='" + dhBean.getNppId() + "'";
			ResultSet rs = db.get(sql_getinfodis);
			try
			{
				if (rs.next())
				{
					tennpp = rs.getString("ten");
					diachi = rs.getString("diachi");
					dienthoai = rs.getString("dienthoai");
					masothue = rs.getString("masothue");
				}
				rs.close();
			} catch (Exception er)
			{
				
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###.##");
			
			
			
			document.open();
			
			// lay doi tuong khach hang
			
			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font fontnomar = new Font(bf, 10, Font.NORMAL);
			// font2.setColor(BaseColor.GREEN);
			// KHAI BAO 1 BANG CO BAO NHIEU COT
			
			PdfPTable tableheader = new PdfPTable(3);
			tableheader.setWidthPercentage(100);
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader =
			{
			30.0f, 50.0f, 30.0f
			};
			tableheader.setWidths(withsheader);
			
			Image hinhanh = Image.getInstance(getServletContext().getInitParameter("path") + "/logo.gif");
			hinhanh.setWidthPercentage(70.0f);
			hinhanh.scalePercent(70);
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cellslogo = new PdfPCell(hinhanh);
			
			cellslogo.setPadding(10);
			
			tableheader.addCell(cellslogo);
			
			Paragraph pxk = new Paragraph("CHI TIẾT ĐƠN HÀNG BÁN", font);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			
			PdfPCell celltieude = new PdfPCell();
			celltieude.addElement(pxk);
			Paragraph dvbh = new Paragraph("Đơn vị bán hàng : " + tennpp, fontnomar);
			dvbh.setSpacingAfter(3);
			dvbh.setAlignment(Element.ALIGN_CENTER);
			celltieude.addElement(dvbh);
			
			tableheader.addCell(celltieude);
			
			PdfPCell cellinfo = new PdfPCell();
			
			cellinfo.addElement(new Paragraph("Số chứng từ     : " + dhBean.getId(), fontnomar));
			
			System.out.println("[DonHangId__CreatePxk Số chứng từ ]" + dhBean.getId());
			
			String ngay = dhBean.getNgaygiaodich().substring(8, 10) + "-" + dhBean.getNgaygiaodich().substring(5, 7) + "-" + dhBean.getNgaygiaodich().substring(0, 4);
			cellinfo.addElement(new Paragraph("Ngày chứng từ : " + ngay, fontnomar));
			tableheader.addCell(cellinfo);
			
			document.add(tableheader);
			
			PdfPTable table_info = new PdfPTable(1);
			float[] with3 =
			{
				50.0f
			};// SET DO RONG CAC COT
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111 = new PdfPCell();
			
			String tenkh = "";
			String diachikh = "";
			String dienthoaikh = "";
			String masothuekh = "";
			String sql_getinfokh = "select ten,isnull(dienthoai,'Chua xac dinh') as dienthoai,isnull(diachi,'Chua xac dinh') as diachi,isnull(masothue,'Chua xac dinh') as masothue from khachhang where pk_seq=" + dhBean.getKhId();
			
			ResultSet rs_kh = db.get(sql_getinfokh);
			try
			{
				if (rs_kh.next())
				{
					tenkh = rs_kh.getString("ten");
					diachikh = rs_kh.getString("diachi");
					dienthoaikh = rs_kh.getString("dienthoai");
					masothuekh = rs_kh.getString("masothue");
				}
				rs_kh.close();
			} catch (Exception er)
			{
				System.out.println("Loi Khach Hang : " + er.toString());
			}
			// lay dai dien kinh doanh
			String ddkdten = "";
			String sql_getddkd = "select pk_seq,ten from daidienkinhdoanh where pk_seq=" + dhBean.getDdkdId();
			ResultSet rs_getddkd = db.get(sql_getddkd);
			
			try
			{
				if (rs_getddkd.next())
				{
					ddkdten = rs_getddkd.getString("ten");
				}
				rs_getddkd.close();
			} catch (Exception er)
			{
				System.out.println("Loi DDkD : " + er.toString());
			}
			
			cell111.addElement(new Paragraph("Nhân viên bán hàng : " + ddkdten, fontnomar));
			
			cell111.addElement(new Paragraph("Mã khách hàng : " + dhBean.getKhId() + "   Tên khách hàng : " + tenkh, fontnomar));
			cell111.addElement(new Paragraph("Địa chỉ : " + diachikh, fontnomar));
			
			cell111.setPaddingBottom(10);
			table_info.addCell(cell111);
			
			document.add(table_info);
			// Table Content
			PdfPTable table = new PdfPTable(10);// Chu y nhe 6 cot o day, thi xuong
																					// duoi nho set withs la 6 cot
			table.setWidthPercentage(100);// chieu dai cua báº£ng
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs =
			{
			5.0f, 14.0f, 30.0f, 10.0f, 8.0f, 8.0f, 10.0f, 5.0f, 10.0f, 12.0f
			};
			table.setWidths(withs);// set cÃ¡c Ä‘á»™ rá»™ng cho báº£ng
			String[] th = new String[]
			{
			"STT", "Mã hàng", "Tên hàng", "Kho", "Số lượng", "ĐVT", "Đơn giá", "%CK", "Chiết khấu", "Thành tiền"
			};
			PdfPCell[] cell = new PdfPCell[10];
			for (int i = 0; i < 10; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
				table.addCell(cell[i]);
			}
			float size = 8.5f;
			Font font4 = new Font(bf, size);
			PdfPCell cells_detail = new PdfPCell();
			double totalle = 0;
			for (int i = 0; i < splist.size(); i++)
			{
				ISanpham sanpham = (ISanpham) splist.get(i);
				double dongia = Double.parseDouble(sanpham.getDongia());
				double chietkhau = Double.parseDouble(sanpham.getSoluong()) * Double.parseDouble(sanpham.getDongia()) / 100 * Double.parseDouble(sanpham.getChietkhau());
				// lay kho
				String sql_getkho = "select ten  from kho where pk_seq =" + dhBean.getKhoId();
				// System.out.println(sql_getkho);
				
				rs = db.get(sql_getkho);
				String TenKho = "Chính";
				try
				{
					if (rs.next())
					{
						TenKho = rs.getString("ten");
						rs.close();
					}
				} catch (Exception er)
				{
					System.out.println("Loi tai day :" + er.toString());
				}
				
				String[] arr = new String[]
				{
				Integer.toString(i + 1), sanpham.getMasanpham(), sanpham.getTensanpham(), TenKho, sanpham.getSoluong(), sanpham.getDonvitinh(), formatter1.format(dongia), formatter1.format(Double.parseDouble(sanpham.getChietkhau())), formatter1.format(chietkhau), formatter1.format(Double
				    .parseDouble(sanpham.getSoluong()) * Double.parseDouble(sanpham.getDongia()) - chietkhau)
				};
				
				totalle = totalle + Double.parseDouble(sanpham.getSoluong()) * Double.parseDouble(sanpham.getDongia()) - chietkhau;
				for (int j = 0; j < 10; j++)
				{
					cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
					if (j == 2)
					{
						cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						
					} else
					{
						cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
					// cells_detail.setBorderWidthBottom(0);
					// cells_detail.setBorderWidthTop(0);
					table.addCell(cells_detail);
					
				}
			}
			
			if (spkmlist.size() > 0)
			{
				PdfPCell cellkhuyenmai = new PdfPCell();
				cellkhuyenmai.setHorizontalAlignment(Element.ALIGN_CENTER);
				Paragraph km1 = new Paragraph("Hàng khuyến mại", font2);
				km1.setAlignment(Element.ALIGN_LEFT);
				cellkhuyenmai.addElement(km1);
				cellkhuyenmai.setColspan(10);
				table.addCell(cellkhuyenmai);
			}
			// hang khuyen mai
			int demtt = 0;
			for (int i = 0; i < spkmlist.size(); i++)
			{
				ISanpham sanpham = (ISanpham) spkmlist.get(i);
				double dongia = Double.parseDouble(sanpham.getDongia());
				
				double chietkhau = Double.parseDouble(sanpham.getSoluong()) * Double.parseDouble(sanpham.getDongia()) / 100 * Double.parseDouble(sanpham.getChietkhau());
				String sql_getkho = "select kho_fk,kho.ten  from ctkhuyenmai inner join kho on  kho.pk_Seq=kho_fk  where scheme ='" + sanpham.getCTKM() + "'";
				// System.out.println(sql_getkho);
				
				rs = db.get(sql_getkho);
				String TenKho = "Khuyến mại";
				try
				{
					if (rs.next())
					{
						TenKho = rs.getString("ten");
					}
					rs.close();
				} catch (Exception er)
				{
					System.out.println("Loi tai day :" + er.toString());
				}
				demtt = demtt + 1;
				String[] arr = new String[]
				{
				"" + demtt, sanpham.getMasanpham(), sanpham.getTensanpham(), TenKho, sanpham.getSoluong(), sanpham.getDonvitinh(), formatter1.format(dongia), formatter1.format(Double.parseDouble(sanpham.getChietkhau())), formatter.format(chietkhau), formatter
				    .format(Double.parseDouble(sanpham.getSoluong()) * Double.parseDouble(sanpham.getDongia()) - chietkhau)
				};
				
				for (int j = 0; j < 10; j++)
				{
					cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
					if (j == 2)
					{
						cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
					} else
					{
						cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					
					cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cells_detail);
					
				}
			}
			// Khai bao 1 bien luu tien khuyen mai
			float tongtienkhuyenmai = 0;
			// Xuat ra khuyen mai tra tien
			if (scheme_tongtien.size() > 0)
			{
				Enumeration<String> keys = scheme_tongtien.keys();
				while (keys.hasMoreElements())
				{
					String key = keys.nextElement();
					tongtienkhuyenmai = tongtienkhuyenmai + scheme_tongtien.get(key);
					String tongtien = Float.toString(scheme_tongtien.get(key));
					String sql_tenschem = "select diengiai from ctkhuyenmai where scheme='" + key + "'";
					rs = db.get(sql_tenschem);
					String Tenschem = "";
					try
					{
						if (rs.next())
						{
							Tenschem = rs.getString("diengiai");
						}
						rs.close();
					} catch (Exception er)
					{
						System.out.println("Loi tai day :" + er.toString());
					}
					demtt = demtt + 1;
					String[] arr = new String[]
					{
					"" + demtt, key, Tenschem, "", "", "", "", "", "", "-" + tongtien
					};
					for (int j = 0; j < 10; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if (j == 2)
						{
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						} else
						{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);
						
					}
				}
				
			}
			
			if (scheme_chietkhau.size() > 0)
			{
				
				Enumeration<String> keys = scheme_chietkhau.keys();
				while (keys.hasMoreElements())
				{
					String key = keys.nextElement();
					String sql_tenschem = "select diengiai from ctkhuyenmai where scheme='" + key + "'";
					rs = db.get(sql_tenschem);
					String Tenschem = "";
					try
					{
						if (rs.next())
						{
							Tenschem = rs.getString("diengiai");
						}
						rs.close();
					} catch (Exception er)
					{
						System.out.println("Loi tai day :" + er.toString());
					}
					
					tongtienkhuyenmai = tongtienkhuyenmai + scheme_chietkhau.get(key);
					String tienchietkhau = Float.toString(scheme_chietkhau.get(key));
					demtt = demtt + 1;
					String[] arr = new String[]
					{
					"" + demtt, key, Tenschem, "", "", "", "", "", "-" + tienchietkhau, ""
					};
					for (int j = 0; j < 10; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if (j == 2)
						{
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						} else
						{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						
						table.addCell(cells_detail);
						
					}
				}
				
			}
			
			PdfPCell cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph km = new Paragraph("Cộng tiền hàng ", font2);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(9);
			table.addCell(cell11);
			
			PdfPCell cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph tongtien = new Paragraph(formatter1.format(totalle), font2);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			
			// tong tien khuyen mai
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			km = new Paragraph("Tổng số tiền chiết khấu khuyến mại ", font2);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(9);
			table.addCell(cell11);
			//
			
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien = new Paragraph(formatter1.format(tongtienkhuyenmai), font2);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			//
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			km = new Paragraph("Tổng tiền thanh toán(đã làm tròn) ", font2);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(9);
			table.addCell(cell11);
			//
			// double tongtiensauthue=totalle/100 *10 +totalle;
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien = new Paragraph(formatter.format(totalle - tongtienkhuyenmai), font2);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			
			// doc tien bang chu
			doctienrachu doctien = new doctienrachu();
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			// Long tongtienst= Math.round(tongtiensauthue-tongtienkhuyenmai);
			Long tongtienst = Math.round(totalle - tongtienkhuyenmai);
			km = new Paragraph("Số tiền bằng chữ : " + doctien.tranlate(tongtienst + ""), font2);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			cell11.setColspan(10);
			table.addCell(cell11);
			
			document.add(table);
			
			PdfPTable tablefooter = new PdfPTable(4);
			tablefooter.setWidthPercentage(100);// chieu dai cua báº£ng
			tablefooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] withfooterder =
			{
			35.0f, 35.0f, 35.0f, 35.0f
			};// SET DO RONG CAC COT
			tablefooter.setWidths(withfooterder);
			
			// nguoimua hang
			Paragraph para = new Paragraph("Người lập phiếu", fontnomar);
			
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);
			// o giua
			para = new Paragraph("Thủ kho", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);
			// Káº¿ toÃ¡n
			para = new Paragraph("Nhân viên giao nhận", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setBorderWidthRight(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablefooter.addCell(para);
			// nguoi nhan hang
			para = new Paragraph("Khách hàng", fontnomar);
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
			splist.clear();
			spkmlist.clear();
			scheme_tongtien.clear();
			scheme_chietkhau.clear();
			dhBean.DBclose();
			
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}
	
}
