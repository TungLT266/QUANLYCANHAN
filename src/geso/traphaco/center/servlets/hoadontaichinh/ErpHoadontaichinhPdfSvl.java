package geso.traphaco.center.servlets.hoadontaichinh;

import geso.traphaco.center.beans.hoadontaichinh.imp.SanphamHoadontaichinhObj;
import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.center.beans.hoadontaichinh.IErpHoadontaichinh;
import geso.traphaco.center.beans.hoadontaichinh.imp.ErpHoadontaichinh;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import C.V;

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
import com.itextpdf.text.pdf.PdfContentByte;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.lowagie.text.pdf.PdfTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ErpHoadontaichinhPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpHoadontaichinhPdfSvl()
	{
		super();
	}
	float CONVERT = 28.346457f; // = 1cm
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		// String userTen = (String) session.getAttribute("userTen");

		Utility util = new Utility();

		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String querystring = request.getQueryString();
		String id = util.antiSQLInspection(request.getParameter("pdf"));
		IErpHoadontaichinh pxkBean = new ErpHoadontaichinh(id);
		pxkBean.setUserId(userId);
		pxkBean.init();

		if (querystring.indexOf("pdf") > 0)
		{
			//pxkBean.initPdf();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=HOADONTAICHINHHUNGYEN.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();

		//	this.CreatePxk(document, outstream, pxkBean);
			CreatePxk_2017_jasper( document,  outstream, request,  response ,  pxkBean) ;

			String msg = this.CapnhatTT(id);
			pxkBean.setMsg(msg);
		} 


	}


	private String CapnhatTT(String id) {
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "";

			// Cập nhật trạng thái Đã in
			query = "update ERP_HOADON set trangthai = '4' where pk_seq = '" + id + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_HOADON " + query;
				db.getConnection().rollback();
				return msg;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}

		return "";
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, IErpHoadontaichinh pxkBean) throws IOException
	{
		try
		{

			dbutils db = new dbutils();

			//LAY THONG TIN NCC
			String ddh="";
			double Vat= 0;

			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			String ngaytao = "";
			String sql ="SELECT A.PK_SEQ as ddh,A.VAT, (select TEN from ERP_KHOTT where PK_SEQ = A.KHO_FK ) AS KHO,A.NGAYTAO " +
					"FROM ERP_DONDATHANG A " +
					"WHERE A.PK_SEQ IN  " +
					"(select DDH_FK from ERP_HOADON_DDH where HOADON_FK = '" + pxkBean.getId() + "' )";

			System.out.println("[INIT_ERP_DONDATHANG]"+sql);

			ResultSet rsCheck = db.get(sql);					

			if(rsCheck.next())
			{					
				khoxuat = rsCheck.getString("KHO");
				ngaytao = rsCheck.getString("NGAYTAO");
				ddh = rsCheck.getString("ddh");
				Vat = rsCheck.getDouble("VAT");
				rsCheck.close();
			}

			sql = " select PK_SEQ, TEN ,DIACHI,'04.38454813' AS DIENTHOAI,'04.36811542' AS FAX, '0100108656' AS MASOTHUE, '' as SOTAIKHOAN "+
					" from NHACUNGCAP ";				  


			System.out.println("Lấy TT CTY "+sql);
			ResultSet rsINFO = db.get(sql);
			if(rsINFO.next())
			{
				ctyTen = rsINFO.getString("TEN");
				cty_MST = rsINFO.getString("MASOTHUE");
				cty_Diachi = rsINFO.getString("DIACHI");
				cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
				cty_Dienthoai = rsINFO.getString("DIENTHOAI");
				cty_Fax = rsINFO.getString("FAX");
				rsINFO.close();

			}


			//LAY THONG TIN KHACHHANG
			String nguoimua= "";
			String kyhieu="";
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String hinhthucTT= "";
			String npp_fk ="";
			String soin="";
			String nguoimuahang="";


			sql ="SELECT A.HINHTHUCTT, A.NPP_FK, A.KYHIEU, A.NGUOIMUA, A.SOHOADON " +
					"FROM ERP_HOADON A " +
					"WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";

			System.out.println("[INIT_ERP_DONDATHANG]"+sql);

			ResultSet rsHD = db.get(sql);					

			if(rsHD.next())
			{		
				nguoimua = rsHD.getString("NGUOIMUA");
				hinhthucTT = rsHD.getString("HINHTHUCTT");
				npp_fk = rsHD.getString("NPP_FK");
				kyhieu = rsHD.getString("KYHIEU");
				soin = rsHD.getString("SOHOADON");
				rsHD.close();
			}

			sql = " select  TEN ,DIACHI, MASOTHUE ,isnull( TENNGUOIDAIDIEN ,'') TENNGUOIDAIDIEN "+
					" from NHAPHANPHOI" +
					" where PK_SEQ = '"+ npp_fk +"' ";				  


			System.out.println("Lấy TT KH "+sql);
			ResultSet rsLayKH= db.get(sql);
			if(rsLayKH.next())
			{
				Donvi = rsLayKH.getString("TEN");
				kh_MST = rsLayKH.getString("MASOTHUE");
				kh_Diachi = rsLayKH.getString("DIACHI");
				nguoimuahang= rsLayKH.getString("TENNGUOIDAIDIEN");
				rsLayKH.close();

			}   


			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			document.setPageSize(PageSize.A4.rotate());
			//document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 2.0f*CONVERT); // L,R,T,B

			document.setMargins(1.3f*CONVERT, 1.0f*CONVERT, 1.7f*CONVERT, 0.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);

			document.open() ;

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);


			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);
			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			//cell.setPaddingTop(0.17f * CONVERT);
			cell.setPaddingTop(1.6f * CONVERT);//1.5
			cell.setPaddingLeft(13.5f * CONVERT);//2.4
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			String [] ngayHD = pxkBean.getNgayxuatHD().split("-");
			System.out.println(" ngay xuat hoa don:"+pxkBean.getNgayxuatHD());
			Paragraph pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                       " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_LEFT);
			//pxk.setSpacingAfter(1f);//2
			
			cell.addElement(pxk);
			tableheader.addCell(cell);	
			document.add(tableheader);

			// Thông tin Khach Hang
			PdfPTable table1 =new PdfPTable(2);
			table1.setWidthPercentage(103);
			table1.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
			//	table1.setSpacingBefore(0.5f*CONVERT);
			table1.setWidths(withs1);									

			// DONG 1-- NGUOI MUA HANG
			PdfPCell cell_nguoimua = new PdfPCell();	
			pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(4);//4
			cell_nguoimua.addElement(pxk);
			cell_nguoimua.setBorder(0);						
			table1.addCell(cell_nguoimua);	

			PdfPCell cell_nguoimua1 = new PdfPCell();
			//cell_nguoimua1.setPaddingTop(0.5f * CONVERT);
			cell_nguoimua1.setPaddingLeft(5.5f * CONVERT);//4.5
			pxk = new Paragraph(nguoimuahang, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_nguoimua1.addElement(pxk);
			cell_nguoimua1.setBorder(0);						
			table1.addCell(cell_nguoimua1);

			// DONG 2-- DON VI
			PdfPCell cell8 = new PdfPCell();	
			cell8.setPaddingLeft(2.7f * CONVERT);
			pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(5);
			cell8.addElement(pxk);
			cell8.setBorder(0);						
			table1.addCell(cell8);	

			PdfPCell cell8a = new PdfPCell();
			cell8a.setPaddingTop(-0.1f * CONVERT);//-0.19
			cell8a.setPaddingLeft(3.7f * CONVERT);
			pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(5);//3
			cell8a.addElement(pxk);
			cell8a.setBorder(0);						
			table1.addCell(cell8a);

			// DONG 3 ---- DIA CHI
			PdfPCell cell10 = new PdfPCell();
			cell10.setPaddingLeft(2.5f * CONVERT);	
			cell10.setPaddingTop(0.3f * CONVERT);//-0.1
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell10.addElement(pxk);
				cell10.setBorder(0);						
			table1.addCell(cell10);


			cell10 = new PdfPCell();
			cell10.setPaddingLeft(3.2f * CONVERT);//3.0
			cell10.setFixedHeight(1.0f*CONVERT);
			cell10.setPaddingTop(-0.1f * CONVERT);//-0.2
			pxk = new Paragraph(kh_Diachi, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_TOP);
			cell10.setHorizontalAlignment(Element.ALIGN_TOP);
			cell10.addElement(pxk);
			cell10.setBorder(0);						
			table1.addCell(cell10);







			// DONG 5 ----KHO XUAT
			PdfPCell cell17 = new PdfPCell();	
			cell17.setPaddingLeft(2.9f * CONVERT);
			cell17.setPaddingTop(-0.2f * CONVERT);//-0.2
			pxk = new Paragraph(khoxuat, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(3);
			cell17.addElement(pxk);
			cell17.setBorder(0);						
			table1.addCell(cell17);	


			if(kh_MST.trim().length() <= 0)
			{
				kh_MST = "";
			}

			PdfPCell cell18 = new PdfPCell();
			PdfPTable tbl_mst = new PdfPTable(2);
			tbl_mst.setWidthPercentage(100);
			cell= new PdfPCell(new Paragraph (kh_MST, new Font(bf, 10, Font.NORMAL)));
			cell.setPaddingLeft(3.8f * CONVERT);
			cell.setBorder(0);
			tbl_mst.addCell(cell);

			cell= new PdfPCell(new Paragraph (hinhthucTT, new Font(bf, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(0);
		//	cell.setPaddingRight(-1.0f*CONVERT);
			tbl_mst.addCell(cell);

			cell18.setBorder(0);
			cell8.setPaddingTop(-0.1f*CONVERT);
			cell18.addElement(tbl_mst);
			table1.addCell(cell18);       


			document.add(table1);

			/*---------------------------------------------------------------------------------------*/
			// Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);

			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = { 95.0f, 100.0f };
			root.setWidths(cr);

			String[] th = new String[]{ " ", " ", " ", "  ", " "," " ," ", " "," ", " " ," ", " "};

			PdfPTable sanpham = new PdfPTable(th.length);
			//sanpham.setSpacingBefore(47.4f);
			sanpham.setSpacingBefore(1.7f*CONVERT);//1.8
			sanpham.setWidthPercentage(104);//100
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);

			//float[] withsKM = { 7.3f, 15.0f, 60f, 17f, 16f, 8.0f, 14.0f, 20f, 24.0f, 8.0f, 23f, 25f };
			float[] withsKM = { 7.3f, 15.0f, 58f, 18f, 19f, 8.0f, 15.0f, 20f, 25.0f, 8.0f, 21f, 22f };
			sanpham.setWidths(withsKM);


			PdfPCell cells = new PdfPCell();


			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;

			Utility util = new Utility();
			if(ngaytao.trim().length()==0)
			{
				ngaytao = getDateTime();
			}
			int		donhang_sau_ngay_22 = util.CompareDATE(ngaytao, "2017-12-22");	
			System.out.println("donhang_sau_ngay_22:"+donhang_sau_ngay_22);
			String query ="";
			if(donhang_sau_ngay_22 >=0)
			{
				// DO TRANG NÓI BÊN HƯNG YÊN CHUA CÓ CHIẾT KHÂU, VÀ CHƯA CUNG CẤP CÔNG THỨC TÍNH NÊN BỎ CHIẾT KHẤU (2017-12-22)
				query =
						"	select distinct c.MA, c.TEN, d.DONVI,   dhsp.soluong, (SELECT dongia FROM ERP_DONDATHANG_SANPHAM DH_SP WHERE DH_SP.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=DH_SP.sanpham_fk ) AS DONGIA ,(SELECT CHIETKHAU FROM ERP_DONDATHANG_SANPHAM DH_SP WHERE DH_SP.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=DH_SP.sanpham_fk ) AS chietkhau, dhsp.solo as solo, dhsp.ngayhethan, "
								+ "	 TIENHANG, \n"
								+ "  TIENVAT, \n"+

						"      (SELECT THUEVAT FROM ERP_DONDATHANG_SANPHAM DH_SP WHERE DH_SP.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=DH_SP.sanpham_fk ) AS thuevat,(SELECT isnull(DH_SP.IS_KHONGTHUE,0) FROM ERP_DONDATHANG_SANPHAM DH_SP WHERE DH_SP.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=DH_SP.sanpham_fk ) as IS_KHONGTHUE "+
						"	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.pk_seq=b.ycxk_fk 		"+
						"	inner join ERP_SANPHAM c on b.sanpham_fk = c.PK_SEQ                    "+
						"	inner join ERP_DONDATHANG dh on dh.pk_seq= '"+ ddh +"'        "+
						"	inner join ERP_DONDATHANG_SANPHAM_CHITIET dhsp on dhsp.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=dhsp.sanpham_fk    "+  
						"	left join DONVIDOLUONG d on d.PK_SEQ=(SELECT dvdl_fk FROM ERP_DONDATHANG_SANPHAM DH_SP WHERE DH_SP.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=DH_SP.sanpham_fk )	"+
						"	where a.PK_SEQ =( select ycxk_fk from ERP_YCXUATKHO_DDH where DDH_FK= '"+ ddh +"' ) and b.soluong > 0 "+
						"	 " ;
			}else
			{
				query =
						"	select distinct c.MA, c.TEN, d.DONVI,   dhsp.soluong, dhsp.dongia , dhsp.chietkhau, ' ' as solo, ' ' as ngayhethan, " +
								"      dhsp.thuevat,isnull(dhsp.IS_KHONGTHUE,0) as IS_KHONGTHUE "+
								"	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.pk_seq=b.ycxk_fk 		"+
								"	inner join ERP_SANPHAM c on b.sanpham_fk = c.PK_SEQ                    "+
								"	inner join ERP_DONDATHANG dh on dh.pk_seq= '"+ ddh +"'        "+
								"	inner join ERP_DONDATHANG_SANPHAM dhsp on dhsp.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=dhsp.sanpham_fk    "+  
								"	left join DONVIDOLUONG d on d.PK_SEQ=dhsp.dvdl_fk 	"+
								"	where a.PK_SEQ =( select ycxk_fk from ERP_YCXUATKHO_DDH where DDH_FK= '"+ ddh +"' ) and b.soluong > 0 ";

			}


			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);

			ResultSet rsSP = db.get(query);
			int numMax=16;
			int stt = 1;
			while(rsSP.next())
			{
				List <String> tenlist=xuLyChuoi(48,48,30, "Phí gia công - "+rsSP.getString("TEN"));
				System.out.println("xu ly chuoi :"+ tenlist.size());

				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = 0;	
				double thueGTGT =0;
				if(donhang_sau_ngay_22 >=0)
				{
					thanhtien = rsSP.getDouble("TIENHANG");
					thueGTGT = rsSP.getDouble("TIENVAT");
				}else
				{
					thueGTGT = thanhtien*rsSP.getDouble("thuevat")/100;
					thanhtien = soLUONG*dongia - chietkhau;	
				}

				double sotientt = thanhtien + thueGTGT;

				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				System.out.println("thanh tien"+thanhtien);
				totalSotienTT +=sotientt;
				double is_khongthue = rsSP.getDouble("IS_KHONGTHUE");

				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				if(tenlist!= null)
				{
					for (int n = 0; n < tenlist.size(); n++) 
					{
						String chuoi1=tenlist.get(n);
						// in dong đầu
						if(n==0)
						{
							String[] arr = new String[] { Integer.toString(stt), rsSP.getString("MA"), chuoi1, rsSP.getString("solo"), 
									chuoi, rsSP.getString("DONVI"),
									formatter.format(soLUONG), formatter.format(dongia),formatter.format(thanhtien),
									(is_khongthue==1?"X":formatter.format(rsSP.getDouble("thuevat"))), 
									(is_khongthue==1?"X":formatter.format(thueGTGT)),
									formatter.format(sotientt) };


							for (int j = 0; j < th.length; j++)
							{
								cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.NORMAL)));
								if (j == 2 || j==1 || j==0 ){
									cells.setPaddingLeft(0.2f*CONVERT);
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
								}
								else{
									//if(j==9|| j==6 || j==0 || j==4 || j==3 || j==5 )
									if(j <=5 )
									{

										cells.setHorizontalAlignment(Element.ALIGN_CENTER);
									}
									else{

										cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
									}
								}

								cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
								cells.setBorder(0);
								cells.setFixedHeight(0.6f * CONVERT);
								cells.setPaddingTop(2.5f);

								sanpham.addCell(cells);
							}

							--numMax;
							stt++;
						}
						else{
							String[] arr = new String[] { "", "", chuoi1, "", 
									"", "", "", "","",
									"",	"","" };
							for (int j = 0; j < th.length; j++)
							{
								cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.NORMAL)));
								if (j == 2 || j==1 || j==0 ){
									cells.setPaddingLeft(0.2f*CONVERT);
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
								}
								else{
									//if(j==9|| j==6 || j==0 || j==4 || j==3 || j==5 )
									if(j <=4 )
									{
										cells.setHorizontalAlignment(Element.ALIGN_CENTER);
									}
									else{

										cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
									}
								}

								cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
								cells.setBorder(0);
								cells.setFixedHeight(0.6f * CONVERT);
								cells.setPaddingTop(2.5f);

								sanpham.addCell(cells);
							}
							--numMax;
						}
					}
				}


			}		


			// DONG TRONG
			int kk=0;
			//while(kk < 15-stt)
			while(numMax-1>0)
			{
				String[] arr_bosung = new String[] { " ", " " , " ", " "," ", " "," "," "," "," ", " "," " };

				for (int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);

					cells.setBorder(0);
					cells.setFixedHeight(0.6f*CONVERT);

					sanpham.addCell(cells);
				}
				numMax--;
				kk++;
			}
			System.out.println("tien tc vat "+formatter.format(totalTienTruocVAT));
			// Tong tien thanh toan	
			String[] arr = new String[] {"                             ", formatter.format(totalTienTruocVAT),"",formatter.format(totalThueGTGT),formatter.format(Math.round(totalTienTruocVAT + totalThueGTGT )) };
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(8);
				} else if(j==1)
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);

				}else
				{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				//cells.setPaddingLeft(1.0f * CONVERT);
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}


			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
			String tien = doctien.docTien(Math.round(totalTienTruocVAT + totalThueGTGT));	   
			//Viết hoa ký tự đầu tiên
			String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);

			String[] arr1 = new String[] {"                                          " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(12);
				} 
				cells.setVerticalAlignment(Element.ALIGN_LEFT);
				cells.setPaddingLeft(2.5f * CONVERT);//0.8
				cells.setPaddingTop(5.0f);
				cells.setBorder(0);
				cells.setFixedHeight(0.6f*CONVERT);
				sanpham.addCell(cells);
			}	
			document.add(sanpham);



			// FOOTER


			/*PdfPTable tbl_footer = new PdfPTable(4);
			tbl_footer.setWidthPercentage(100);
			cell= new PdfPCell(new Paragraph (nguoimuahang , new Font(bf, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setPaddingTop(0.5f*CONVERT);
			tbl_footer.addCell(cell);

			cell= new PdfPCell(new Paragraph ("", new Font(bf, 10, Font.NORMAL)));
			cell.setBorder(0);
			cell.setColspan(3);
			tbl_footer.addCell(cell);
			document.add(tbl_footer);*/

			document.close();


		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}


	}


	private String getSTT(int stt)
	{
		if (stt < 10)
			return "000" + Integer.toString(stt);
		if (stt < 100)
			return "00" + Integer.toString(stt);
		if (stt < 1000)
			return "0" + Integer.toString(stt);
		return Integer.toString(stt);
	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}


	// độ rộng cột, số lượng chữ thường tối đa cột chứa, max chữ hoa , chuỗi		
	public List<String> xuLyChuoi (float chieudai,int sochuthuong,int sochuhoa,  String chuoi ) 
	{  
		float chieudaimotchuthuong = chieudai/sochuthuong;
		float chieudaimotchuhoa = chieudai/sochuhoa;
		float sokytu1sp = 0;  
		List<String> _ghichuList =new ArrayList<String>();	  
		String[] words = new String[0];
		String _ten = "", _ten2 = "";
		words = chuoi.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong quy cach
		_ten = "";
		for (int _i = 0; _i < words.length; _i++) {
			char []temp = words[_i].toCharArray();
			if(sokytu1sp !=0 && sokytu1sp > chieudai)
				sokytu1sp = dodaichuoi(temp,chieudaimotchuthuong,chieudaimotchuhoa);
			else
				sokytu1sp += dodaichuoi(temp,chieudaimotchuthuong,chieudaimotchuhoa);
			if (sokytu1sp > chieudai)
			{
				if (_ten.trim().length() > 0) {

					_ghichuList.add(_ten); // Thêm dòng cũ
				}
				_ten = words[_i]; 
				sokytu1sp = 0;
			} else {
				_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
				float tam = (dodaichuoi(_ten2.toCharArray(),chieudaimotchuthuong,chieudaimotchuhoa));
				if (tam > chieudai) {

					_ghichuList.add(_ten);						
					_ten = words[_i];
					sokytu1sp = 0;
				} else {
					_ten = _ten2;
				}
			}
		}
		if (_ten.trim().length() > 0) {
			_ghichuList.add(_ten);	
		}
		return _ghichuList;				
	}

	public static float dodaichuoi(char[] chuoi, float chieudaimotchuthuong, float chieudaimotchuhoa)
	{
		float dodai = 0;
		for(int i=0;i<chuoi.length;i++)
		{
			if(Character.isUpperCase(chuoi[i]))
			{
				dodai+=chieudaimotchuhoa;
			}
			else
			{
				dodai+=chieudaimotchuthuong;
			}
		}
		return dodai;
	}
	
	
	//Jasper chung
		private void CreatePxk_2017_jasper(Document document, ServletOutputStream outstream,HttpServletRequest request, HttpServletResponse response , IErpHoadontaichinh pxkBean) throws IOException
		{
			try
			{
				dbutils db = new dbutils();

				//LAY THONG TIN NCC
				String ddh="";
				double Vat= 0;

				String ctyTen="";
				String cty_MST ="";
				String cty_Diachi="";
				String cty_Sotaikhoan= "";
				String cty_Dienthoai= "";
				String cty_Fax= "";
				String khoxuat ="";
				String ngaytao = "";
				String sql ="SELECT A.PK_SEQ as ddh,A.VAT, (select TEN from ERP_KHOTT where PK_SEQ = A.KHO_FK ) AS KHO,A.NGAYTAO " +
						"FROM ERP_DONDATHANG A " +
						"WHERE A.PK_SEQ IN  " +
						"(select DDH_FK from ERP_HOADON_DDH where HOADON_FK = '" + pxkBean.getId() + "' )";

				System.out.println("[INIT_ERP_DONDATHANG]"+sql);

				ResultSet rsCheck = db.get(sql);					

				if(rsCheck.next())
				{					
					khoxuat = rsCheck.getString("KHO");
					ngaytao = rsCheck.getString("NGAYTAO");
					ddh = rsCheck.getString("ddh");
					Vat = rsCheck.getDouble("VAT");
					rsCheck.close();
				}

				sql = " select PK_SEQ, TEN ,DIACHI,'04.38454813' AS DIENTHOAI,'04.36811542' AS FAX, '0100108656' AS MASOTHUE, '' as SOTAIKHOAN "+
						" from NHACUNGCAP ";				  


				System.out.println("Lấy TT CTY "+sql);
				ResultSet rsINFO = db.get(sql);
				if(rsINFO.next())
				{
					ctyTen = rsINFO.getString("TEN");
					cty_MST = rsINFO.getString("MASOTHUE");
					cty_Diachi = rsINFO.getString("DIACHI");
					cty_Sotaikhoan = rsINFO.getString("SOTAIKHOAN");
					cty_Dienthoai = rsINFO.getString("DIENTHOAI");
					cty_Fax = rsINFO.getString("FAX");
					rsINFO.close();

				}


				//LAY THONG TIN KHACHHANG
				String nguoimua= "";
				String kyhieu="";
				String Donvi="";
				String kh_MST ="";
				String kh_Diachi="";
				String hinhthucTT= "";
				String npp_fk ="";
				String soin="";
				String nguoimuahang="";


				sql ="SELECT A.HINHTHUCTT, A.NPP_FK, A.KYHIEU, A.NGUOIMUA, A.SOHOADON " +
						"FROM ERP_HOADON A " +
						"WHERE A.PK_SEQ = '" + pxkBean.getId() + "' ";

				System.out.println("[INIT_ERP_DONDATHANG]"+sql);

				ResultSet rsHD = db.get(sql);					

				if(rsHD.next())
				{		
					nguoimua = rsHD.getString("NGUOIMUA");
					hinhthucTT = rsHD.getString("HINHTHUCTT");
					npp_fk = rsHD.getString("NPP_FK");
					kyhieu = rsHD.getString("KYHIEU");
					soin = rsHD.getString("SOHOADON");
					rsHD.close();
				}

				sql = " select  TEN ,DIACHI, MASOTHUE ,isnull( TENNGUOIDAIDIEN ,'') TENNGUOIDAIDIEN "+
						" from NHAPHANPHOI" +
						" where PK_SEQ = '"+ npp_fk +"' ";				  


				System.out.println("Lấy TT KH "+sql);
				ResultSet rsLayKH= db.get(sql);
				if(rsLayKH.next())
				{
					Donvi = rsLayKH.getString("TEN");
					kh_MST = rsLayKH.getString("MASOTHUE");
					kh_Diachi = rsLayKH.getString("DIACHI");
					nguoimuahang= rsLayKH.getString("TENNGUOIDAIDIEN");
					rsLayKH.close();

				}   


				NumberFormat formatter = new DecimalFormat("#,###,###.##");
				NumberFormat formatter1 = new DecimalFormat("#,###,###");
				////------------------------------------------------------
				try {

					///---------KHAI BÁO
					DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance(); 
					JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.awt.igno‌​re.missing.font","true"); 
					ServletOutputStream servletOutputStream = response.getOutputStream();
					JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.embedded", "true");
					JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.font.name", "arial.tff");

					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition", " inline; filename=Bangkehoadon.pdf");
					
					String [] ngayHD = pxkBean.getNgayxuatHD().split("-");
				
					// gán dũ liệu vao hashmap
					HashMap<String, Object> parameterMap = new HashMap<String, Object>();
					parameterMap.put("ngay", ngayHD[2] ); 
					parameterMap.put("thang", ngayHD[1]  );
					parameterMap.put("nam",  ngayHD[0] );
					
					parameterMap.put("chucuahieu", nguoimuahang); 
					parameterMap.put("donvi", Donvi );
					parameterMap.put("kh_MST",kh_MST); 
					
					parameterMap.put("kh_Diachi", kh_Diachi); 
					parameterMap.put("hinhthucTT", hinhthucTT );	
					parameterMap.put("khoxuat", khoxuat );
					
					List <String> splist= new ArrayList<String>();
					pxkBean.setDatasource(getDataSource_2017(  pxkBean, db, splist));
					
					parameterMap.put("truocVat",splist.get(0));
					parameterMap.put("tienVat", splist.get(1)); 
					parameterMap.put("sauVat",  splist.get(2));
					
					// Tien bang chu
					doctienrachu doctien = new doctienrachu();
					//String tien = doctien.docTien(Math.round(totalSotienTT - totalTienCK));
					String tien = doctien.docTien(Long.parseLong(splist.get(2).replaceAll(",", "")));
					//Viết hoa ký tự đầu tiên
					String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
					
					parameterMap.put("TienIN", TienIN );

					try
					{		
						response.setContentType("application/pdf");
						InputStream reportStream =  new FileInputStream (getServletContext().getInitParameter("pathjasper") + "\\HoaDonTaiChinhPDF-mauchung.jasper");
						JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameterMap, pxkBean.getDatasource());
						JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
						servletOutputStream.flush();
						servletOutputStream.close();

					} catch (Exception e)
					{
						// display stack trace in the browser
						StringWriter stringWriter = new StringWriter();
						PrintWriter printWriter = new PrintWriter(stringWriter);
						e.printStackTrace(printWriter);
						response.setContentType("text/plain");
						response.getOutputStream().print(stringWriter.toString());
						e.printStackTrace();
					}
					db.shutDown();
					return;


				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Exception print PDF: " + e.getMessage());
				}


			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception print PDF: " + e.getMessage());
			}
		}
		
		//
		private  JRDataSource getDataSource_2017(  IErpHoadontaichinh pxkBean,dbutils db,List<String> splist12) throws SQLException 
		{
	
			Collection<SanphamHoadontaichinhObj> splist1 = new ArrayList<SanphamHoadontaichinhObj>(); // DANH SANH SP CHI TIET
			// Lay danh sp dang list

			List<SanphamHoadontaichinhObj> splist= new ArrayList<SanphamHoadontaichinhObj>();
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
						 
			//
			//LAY THONG TIN NCC
			String ddh="";
			double Vat= 0;

			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			String ngaytao = "";
			String sql ="SELECT A.PK_SEQ as ddh,A.VAT, (select TEN from ERP_KHOTT where PK_SEQ = A.KHO_FK ) AS KHO,A.NGAYTAO " +
					"FROM ERP_DONDATHANG A " +
					"WHERE A.PK_SEQ IN  " +
					"(select DDH_FK from ERP_HOADON_DDH where HOADON_FK = '" + pxkBean.getId() + "' )";

			System.out.println("[INIT_ERP_DONDATHANG]"+sql);

			ResultSet rsCheck = db.get(sql);					

			if(rsCheck.next())
			{					
				khoxuat = rsCheck.getString("KHO");
				ngaytao = rsCheck.getString("NGAYTAO");
				ddh = rsCheck.getString("ddh");
				Vat = rsCheck.getDouble("VAT");
				rsCheck.close();
			}

			//
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;

			Utility util = new Utility();
			if(ngaytao.trim().length()==0)
			{
				ngaytao = getDateTime();
			}
			int		donhang_sau_ngay_22 = util.CompareDATE(ngaytao, "2017-12-22");	
			System.out.println("donhang_sau_ngay_22:"+donhang_sau_ngay_22);
			String query ="";
			if(donhang_sau_ngay_22 >=0)
			{
				// DO TRANG NÓI BÊN HƯNG YÊN CHUA CÓ CHIẾT KHÂU, VÀ CHƯA CUNG CẤP CÔNG THỨC TÍNH NÊN BỎ CHIẾT KHẤU (2017-12-22)
				query =
						"	select distinct c.MA, c.TEN, d.DONVI,   dhsp.soluong, (SELECT dongia FROM ERP_DONDATHANG_SANPHAM DH_SP WHERE DH_SP.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=DH_SP.sanpham_fk ) AS DONGIA ,(SELECT CHIETKHAU FROM ERP_DONDATHANG_SANPHAM DH_SP WHERE DH_SP.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=DH_SP.sanpham_fk ) AS chietkhau, dhsp.solo as solo, dhsp.ngayhethan, "
								+ "	 TIENHANG, \n"
								+ "  TIENVAT, \n"+

						"      (SELECT THUEVAT FROM ERP_DONDATHANG_SANPHAM DH_SP WHERE DH_SP.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=DH_SP.sanpham_fk ) AS thuevat,(SELECT isnull(DH_SP.IS_KHONGTHUE,0) FROM ERP_DONDATHANG_SANPHAM DH_SP WHERE DH_SP.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=DH_SP.sanpham_fk ) as IS_KHONGTHUE "+
						"	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.pk_seq=b.ycxk_fk 		"+
						"	inner join ERP_SANPHAM c on b.sanpham_fk = c.PK_SEQ                    "+
						"	inner join ERP_DONDATHANG dh on dh.pk_seq= '"+ ddh +"'        "+
						"	inner join ERP_DONDATHANG_SANPHAM_CHITIET dhsp on dhsp.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=dhsp.sanpham_fk    "+  
						"	left join DONVIDOLUONG d on d.PK_SEQ=(SELECT dvdl_fk FROM ERP_DONDATHANG_SANPHAM DH_SP WHERE DH_SP.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=DH_SP.sanpham_fk )	"+
						"	where a.PK_SEQ =( select ycxk_fk from ERP_YCXUATKHO_DDH where DDH_FK= '"+ ddh +"' ) and b.soluong > 0 "+
						"	 " ;
			}else
			{
				query =
						"	select distinct c.MA, c.TEN, d.DONVI,   dhsp.soluong, dhsp.dongia , dhsp.chietkhau, ' ' as solo, ' ' as ngayhethan, " +
								"      dhsp.thuevat,isnull(dhsp.IS_KHONGTHUE,0) as IS_KHONGTHUE "+
								"	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.pk_seq=b.ycxk_fk 		"+
								"	inner join ERP_SANPHAM c on b.sanpham_fk = c.PK_SEQ                    "+
								"	inner join ERP_DONDATHANG dh on dh.pk_seq= '"+ ddh +"'        "+
								"	inner join ERP_DONDATHANG_SANPHAM dhsp on dhsp.dondathang_fk=dh.PK_SEQ  and c.PK_SEQ=dhsp.sanpham_fk    "+  
								"	left join DONVIDOLUONG d on d.PK_SEQ=dhsp.dvdl_fk 	"+
								"	where a.PK_SEQ =( select ycxk_fk from ERP_YCXUATKHO_DDH where DDH_FK= '"+ ddh +"' ) and b.soluong > 0 ";

			}


			System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);

			ResultSet rsSP = db.get(query);
			int numMax=16;
			int stt = 1;
			while(rsSP.next())
			{
				String tensp= "Phí gia công - "+rsSP.getString("TEN");
				
				double soLUONG = rsSP.getDouble("soluong");
				double chietkhau = rsSP.getDouble("chietkhau");
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = 0;	
				double thueGTGT =0;
				if(donhang_sau_ngay_22 >=0)
				{
					thanhtien = rsSP.getDouble("TIENHANG");
					thueGTGT = rsSP.getDouble("TIENVAT");
				}else
				{
					thueGTGT = thanhtien*rsSP.getDouble("thuevat")/100;
					thanhtien = soLUONG*dongia - chietkhau;	
				}

				double sotientt = thanhtien + thueGTGT;

				totalThueGTGT +=thueGTGT;
				totalTienTruocVAT+=thanhtien;
				System.out.println("thanh tien"+thanhtien);
				totalSotienTT +=sotientt;
				double is_khongthue = rsSP.getDouble("IS_KHONGTHUE");

				String chuoi ="";
				if(rsSP.getString("ngayhethan").trim().length() > 0)
				{
					String[] ngayHH =  rsSP.getString("ngayhethan").split("-");
					chuoi= ngayHH[2]+ "/" + ngayHH[1] + "/" + ngayHH[0];
				}
				

				SanphamHoadontaichinhObj obj = new SanphamHoadontaichinhObj( Integer.toString(stt),  rsSP.getString("MA"),tensp, rsSP.getString("solo"),
						chuoi,rsSP.getString("DONVI"),
						formatter.format(soLUONG), formatter.format(dongia),formatter.format(thanhtien),
						(is_khongthue==1?"X":formatter.format(rsSP.getDouble("thuevat"))), 
						(is_khongthue==1?"X":formatter.format(thueGTGT)),
						formatter.format(sotientt));
				stt++;
				splist1.add(obj);					

			}		
			//	String[] arr = new String[] {"                             ", formatter.format(totalTienTruocVAT),"",formatter.format(totalThueGTGT),
			///formatter.format(Math.round(totalTienTruocVAT + totalThueGTGT )) };
			System.out.println("tien tc vat "+formatter.format(totalTienTruocVAT));
			 splist12.add(formatter.format(totalTienTruocVAT));
			 splist12.add(formatter.format(totalThueGTGT));
			 splist12.add(formatter.format(Math.round(totalTienTruocVAT + totalThueGTGT )));
			
			return new JRBeanCollectionDataSource(splist1);
		}
}
