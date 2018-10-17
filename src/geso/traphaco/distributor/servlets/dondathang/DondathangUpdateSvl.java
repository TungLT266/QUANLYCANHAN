package geso.traphaco.distributor.servlets.dondathang;

import geso.traphaco.distributor.beans.dondathang.IDondathang;
import geso.traphaco.distributor.beans.dondathang.IDondathangList;
import geso.traphaco.distributor.beans.dondathang.imp.Dondathang;
import geso.traphaco.distributor.beans.dondathang.imp.DondathangList;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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

public class DondathangUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	public DondathangUpdateSvl()
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

			session.setMaxInactiveInterval(30000);

			String nextJSP;
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			String action = util.getAction(querystring);

			IDondathang ddhBean = (IDondathang) new Dondathang();
			ddhBean.setUserId(userId);

			if (action.equals("dathang"))
			{
				ddhBean.setDndhId(id);
				ddhBean.init2();
				nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHangNewfromPR.jsp";
			} else
			{
				ddhBean.setId(id);
				ddhBean.init1();
				nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHangUpdate.jsp";
			}

			session.setAttribute("ddhBean", ddhBean);

			response.sendRedirect(nextJSP);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(1200);

			IDondathang ddhBean = (IDondathang) new Dondathang();
			Utility util = new Utility();

			String id = util.antiSQLInspection(request.getParameter("id"));

			if (id == null)
			{
				id = "";
			}
			ddhBean.setId(id);

			userId = util.antiSQLInspection(request.getParameter("userId"));
			ddhBean.setUserId(userId);

			ddhBean.setNguoitao(userId);
			ddhBean.setNguoisua(userId);

			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "0";
			ddhBean.setNppId(nppId);

			String dndhId = util.antiSQLInspection(request.getParameter("dndhId"));
			if (dndhId == null)
				dndhId = "0";
			ddhBean.setDndhId(dndhId);

			String ngaydh = util.antiSQLInspection(request.getParameter("ngaydh"));
			if (ngaydh == null||ngaydh.length()<=0)
				ngaydh = getDateTime();

			ddhBean.setNgaydh(ngaydh);

			String ngaydenghigiaohang= util.antiSQLInspection(request.getParameter("ngaydenghigiaohang"));
				
			if(ngaydenghigiaohang==null)
			{
				ngaydenghigiaohang="";
			}
			ddhBean.setNgaydenghigh(ngaydenghigiaohang);
				
				
			
			String nccId = util.antiSQLInspection(request.getParameter("nccId"));
			ddhBean.setNccId(nccId);

			String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
			ddhBean.setDvkdId(dvkdId);

			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			ddhBean.setKbhId(kbhId);

			String tuvanchuyen = util.antiSQLInspection(request.getParameter("tuvanchuyen"));
			System.out.println(tuvanchuyen);
			if (tuvanchuyen == null)
			{
				tuvanchuyen = "0";
			}
			ddhBean.settuvanchuyen(tuvanchuyen);

			String dondathangpr = util.antiSQLInspection(request.getParameter("dondathangpr"));

			if (dondathangpr == null)
			{
				dondathangpr = "";
			}
			String thuesuat=util.antiSQLInspection(request.getParameter("thuesuat"));
			if (thuesuat == null)
			{
				thuesuat = "0";
			}
			ddhBean.setThueSuat(Double.valueOf(thuesuat));
			
			String ghichu=util.antiSQLInspection(request.getParameter("ghichu"));
			
			if(ghichu==null){
				ghichu="";
				
			}
			ddhBean.setghichu(ghichu);
			
			
			String action = request.getParameter("action");
			System.out.println("Action la: " + action);

			if (action.equals("save"))
			{
				if (id.length() == 0)
				{
					if (!(ddhBean.CreateDdhnpp(request)))
					{
						ddhBean.initSelectboxData();
						session.setAttribute("ddhBean", ddhBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHangNewfromPR.jsp";
						if (dondathangpr.equals(""))
						{
							nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHangNew.jsp";
						}
						response.sendRedirect(nextJSP);
					} else
					{
						IDondathangList obj = new DondathangList();
						obj.setUserId(userId);
						obj.createDdhlist("");
						session.setAttribute("obj", obj);

						String nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHang.jsp";
						response.sendRedirect(nextJSP);

					}
				} else
				{
					if (!(ddhBean.UpdateDdhnpp(request)))
					{
						ddhBean.initSelectboxData();
						session.setAttribute("ddhBean", ddhBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						IDondathangList obj = new DondathangList();
						obj.setUserId(userId);
						obj.createDdhlist("");
						session.setAttribute("obj", obj);

						String nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHang.jsp";
						response.sendRedirect(nextJSP);

					}
				}

			} else
			{
				if (action.equals("print"))
				{
					request.setCharacterEncoding("UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/pdf");

					ddhBean.init1();

					Document document = new Document();
					ServletOutputStream outstream = response.getOutputStream();
					this.CreateDondathangHPC(document, outstream, ddhBean);

				} else
				{
					if (action.equals("chot"))
					{
						if (!(ddhBean.ChotDdhnpp(request)))
						{
							ddhBean.initSelectboxData();
							session.setAttribute("ddhBean", ddhBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHangUpdate.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							IDondathangList obj = new DondathangList();
							obj.setUserId(userId);
							obj.createDdhlist("");
							session.setAttribute("obj", obj);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHang.jsp";
							response.sendRedirect(nextJSP);

						}
					} else if (action.equals("freshorder"))
					{
						String nextJSP;

						if (ngaydh.length() > 0 & nccId.length() > 0 & dvkdId.length() > 0 & kbhId.length() > 0)
						{
							ddhBean.init3();
							nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHangNew.jsp";
						} else
						{
							ddhBean.init0();
							nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHangParam.jsp";
						}
						session.setAttribute("ddhBean", ddhBean);

						response.sendRedirect(nextJSP);

					} else if (action.equals("reload"))
					{
						String nextJSP;
						if (id.length() == 0)
						{
							ddhBean.init_reload(request);
							if (dondathangpr.equals(""))
							{

								nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHangNew.jsp";
							} else
							{
								nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHangNewfromPR.jsp";
							}
						} else
						{
							ddhBean.init_reload(request);
							nextJSP = "/TraphacoHYERP/pages/Distributor/DonDatHangUpdate.jsp";
						}
						session.setAttribute("ddhBean", ddhBean);
						response.sendRedirect(nextJSP);
					}
				}
			}
		}

	}

	private void CreateDondathangHPC(Document document, ServletOutputStream outstream, IDondathang ddhBean) throws IOException
	{
		try
		{

			PdfWriter.getInstance(document, outstream);
			document.open();

			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 12, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 8, Font.UNDERLINE);

			Paragraph ddh = new Paragraph("ĐƠN ĐẶT HÀNG", font);
			ddh.setSpacingAfter(15);
			ddh.setAlignment(Element.ALIGN_CENTER);
			document.add(ddh);

			PdfPTable tableHead = new PdfPTable(4);
			tableHead.setWidthPercentage(90);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(10);
			float[] with =
			{ 15.0f, 60.0f, 12f, 18.0f }; // set chieu rong cac columns
			tableHead.setWidths(with);

			PdfPCell cell1 = new PdfPCell(new Paragraph("Tên NPP: ", font3));
			PdfPCell cell2 = new PdfPCell(new Paragraph(ddhBean.getNppTen(), font2));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Số FAX: ", font3));
			PdfPCell cell4 = new PdfPCell(new Paragraph("(08)382 882 828", font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			cell3.setBorder(0);
			cell4.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);
			tableHead.addCell(cell4);

			PdfPCell cell5 = new PdfPCell(new Paragraph("Tinh/ TP: ", font3));
			PdfPCell cell6 = new PdfPCell(new Paragraph("", font2));
			PdfPCell cell7 = new PdfPCell(new Paragraph("ĐT/ Fax NPP: ", font3));
			PdfPCell cell8 = new PdfPCell(new Paragraph("", font2));
			cell5.setBorder(0);
			cell6.setBorder(0);
			cell7.setBorder(0);
			cell8.setBorder(0);
			tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			tableHead.addCell(cell7);
			tableHead.addCell(cell8);

			PdfPCell cell9 = new PdfPCell(new Paragraph("Ngày đặt hàng: ", font3));
			PdfPCell cell10 = new PdfPCell(new Paragraph(ddhBean.getNgaydh(), font2));
			cell9.setBorder(0);
			cell10.setBorder(0);
			cell10.setColspan(3);
			tableHead.addCell(cell9);
			tableHead.addCell(cell10);

			document.add(tableHead);

			// Table Content
			PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);
			float[] withs =
			{ 19.0f, 45.0f, 11.0f, 8.0f, 16.0f, 14.0f, 10.0f, 18.0f };
			table.setWidths(withs);

			String[] th = new String[]
			{ "Mã Code", "Diễn giải", "Quy cách", "DV", "Đơn giá NPP", "Đặt hàng" };
			String[] th2 = new String[]
			{ "(Trước VAT)", "Số lượng", "Thùng", "TT((Trước) VAT)" };
			PdfPCell[] cell = new PdfPCell[8];
			for (int i = 0; i <= 5; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setPadding(5);
				if (i <= 3)
					cell[i].setRowspan(2);
				if (i == 5)
				{
					cell[i].setColspan(3);
				}
				cell[i].setBackgroundColor(BaseColor.WHITE);

				table.addCell(cell[i]);

			}
			for (int i = 0; i < 4; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th2[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setPadding(5);
				cell[i].setBackgroundColor(BaseColor.WHITE);
				table.addCell(cell[i]);
			}

			String[] masp = ddhBean.getMasp();
			String[] tensp = ddhBean.getTensp();
			String[] qc = ddhBean.getQuycach();
			String[] dv1 = ddhBean.getDv1();
			String[] sl = ddhBean.getSl();
			String[] tienbVAT = ddhBean.getTienbVAT();
			String[] dg = ddhBean.getDongia();
			// String[] dv = ddhBean.getDonvi() ;
			NumberFormat formatter = new DecimalFormat("#,###,###");

			for (int j = 0; j < Integer.valueOf(ddhBean.getSize()).intValue(); j++)
			{
				if (Float.valueOf(sl[j].replace(",", "")) > 0)
				{
					Font font4 = new Font(bf, 8);
					cell[0] = new PdfPCell(new Paragraph(masp[j], font4));
					cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[0].setPadding(4);
					table.addCell(cell[0]);

					cell[1] = new PdfPCell(new Paragraph(tensp[j], font4));
					cell[1].setHorizontalAlignment(Element.ALIGN_LEFT);
					cell[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[1].setPadding(4);
					table.addCell(cell[1]);

					cell[2] = new PdfPCell(new Paragraph(qc[j], font4));
					cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[2].setPadding(4);
					table.addCell(cell[2]);

					cell[3] = new PdfPCell(new Paragraph(dv1[j], font4));
					cell[3].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[3].setPadding(4);
					table.addCell(cell[3]);

					cell[4] = new PdfPCell(new Paragraph(formatter.format(Double.valueOf(dg[j])), font4));
					cell[4].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[4].setPadding(4);
					table.addCell(cell[4]);

					cell[5] = new PdfPCell(new Paragraph("" + (Double.valueOf(sl[j]).doubleValue() * Double.valueOf(qc[j]).doubleValue()), font4));
					cell[5].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[5].setPadding(4);
					table.addCell(cell[5]);

					cell[6] = new PdfPCell(new Paragraph(sl[j], font4));
					cell[6].setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell[6].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[6].setPadding(4);
					table.addCell(cell[6]);

					cell[7] = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(tienbVAT[j])), font4));
					cell[7].setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell[7].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[7].setPadding(4);
					table.addCell(cell[7]);
				}

			}
			document.add(table);

			// Table Footer
			PdfPTable tableFooter = new PdfPTable(2);
			tableFooter.setWidthPercentage(40);
			tableFooter.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableFooter.setSpacingBefore(10);
			float[] with3 =
			{ 25.0f, 25.0f };
			tableFooter.setWidths(with3);

			PdfPCell cell11 = new PdfPCell(new Paragraph("Tổng Tiền: ", new Font(bf, 9, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cell12 = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(ddhBean.getTongtienbVAT())), new Font(bf, 9, Font.ITALIC)));
			cell11.setBorder(0);
			cell12.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);

			cell11 = new PdfPCell(new Paragraph("Thuế VAT (10%): ", new Font(bf, 9, Font.BOLDITALIC)));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell12 = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(ddhBean.getVat())), new Font(bf, 9, Font.ITALIC)));
			cell11.setBorder(0);
			cell12.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);

			cell11 = new PdfPCell(new Paragraph("Tổng giá trị: ", new Font(bf, 9, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell12 = new PdfPCell(new Paragraph(formatter.format(Double.parseDouble(ddhBean.getTongtienaVAT())), new Font(bf, 9, Font.ITALIC)));
			cell11.setBorder(0);
			cell12.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);

			document.add(tableFooter);

			tableFooter = new PdfPTable(2);
			tableFooter.setWidthPercentage(80);
			tableFooter.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tableFooter.setSpacingBefore(15);
			tableFooter.setWidths(new float[]
			{ 50.0f, 50.0f });

			cell11 = new PdfPCell(new Paragraph("Xác nhận NPP", new Font(bf, 11, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12 = new PdfPCell(new Paragraph("Xác nhận giám sát OneOne", new Font(bf, 11, Font.BOLD)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorder(0);
			cell12.setBorder(0);
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			document.add(tableFooter);
			document.close();
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
		ddhBean.DBclose();
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}