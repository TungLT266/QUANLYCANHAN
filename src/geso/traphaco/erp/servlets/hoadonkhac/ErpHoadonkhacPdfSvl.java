package geso.traphaco.erp.servlets.hoadonkhac;



import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadonkhac.IErpHoadonkhac;
import geso.traphaco.erp.beans.hoadonkhac.imp.ErpHoadonkhac;
import geso.traphaco.erp.beans.hoadonkhac.imp.SanphamhoadonkhacObj;
import geso.traphaco.erp.beans.hoadonphelieu.IErpHoaDonPL_SP;
import geso.traphaco.erp.beans.hoadontravencc.IErpHoaDon;
import geso.traphaco.erp.beans.hoadontravencc.imp.SanphamHoadontraveObj;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.TabExpander;









import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpHoadonkhacPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final float CONVERT = 28.3464f;

	public ErpHoadonkhacPdfSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpHoadonkhac hdkBean;
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);
		if(id==null){
			userId = util.antiSQLInspection(request.getParameter("id"));
		}

		hdkBean = new ErpHoadonkhac(id);
		String ctyId = (String)session.getAttribute("congtyId");

		hdkBean.setCongtyId(ctyId);
		hdkBean.setId(id);
		System.out.println(" id: "+ id);
		hdkBean.setUserId(userId);
		hdkBean.init();
		session.setAttribute("hdkBean", hdkBean);




		try{
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=HoaDonTaiChinh.pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();		
			String queryhd="";

			//	CreatePxk_hanoi_2017( document,  outstream,  hdkBean, queryhd);

			CreateHoaDonhac_PdfJASPER( document, outstream,  request,  response , hdkBean);
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}



	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}


	public static void main(String[] arg) throws DocumentException, IOException
	{

	}



	private void CreateHoaDonhac_PdfJASPER(Document document,ServletOutputStream outstream, HttpServletRequest request, HttpServletResponse response ,IErpHoadonkhac pxkBean)
	{

		try {

			dbutils db = new dbutils();

			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			String hinhthucTT= "TM/CK/BTCN"; // set cung lun yeu cau ngay 30/9/2017
			String ngayxuathd =pxkBean.getNgayhoadon();
			String nguoimuahang="";
			double chietkhauDH = 0;
			String sql ="";
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String thukho = "";



			// thong tin khach hang
			String qr="SELECT PK_SEQ, TEN, DIACHI, MASOTHUE, DIENTHOAI, FAX,tennguoidaidien as nguoimuahang from NHAPHANPHOI Where PK_SEQ="  + pxkBean.getkhId();
			System.out.println("  thong tin khac hang:" + qr);
			ResultSet  khRs = db.get(qr);
			if(khRs!=null)
			{
				try {
					while (khRs.next()){  
						Donvi = khRs.getString("TEN");
						kh_MST = khRs.getString("MASOTHUE");
						kh_Diachi = khRs.getString("DIACHI");
						nguoimuahang=khRs.getString("nguoimuahang");
					}

					khRs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}



			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
			NumberFormat formatter2=new DecimalFormat("#,###,###.##"); 

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=HoaDonKhac.pdf");

			String ngay="",thang="",nam="";
			String [] ngayHD;
			if(ngayxuathd.length()>=10)
			{
				ngayHD =  ngayxuathd.split("-");
				ngay= ngayHD[2]; 
				thang= ngayHD[1]; 
				nam= ngayHD[0];
			}

			String userten="";
			ResultSet rsuser=db.get("select ten from nhanvien where pk_seq in(  select nguoitao from erp_hoadonkhac where pk_seq='"+pxkBean.getId()+"' )");
			System.out.println(" lay nguoi in: "+ "select ten from nhanvien where pk_seq in(  select nguoitao from erp_hoadonkhac where pk_seq='"+pxkBean.getId()+"' )");
			try {
				rsuser.next();
				userten=rsuser.getString("ten");
				rsuser.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			//---------------------------------------------------------------

			// gán dũ liệu vao hashmap
			HashMap<String, Object> parameterMap = new HashMap<String, Object>();

			parameterMap.put("ngay", ngay); 
			parameterMap.put("thang", thang); 
			parameterMap.put("nam", nam);
			parameterMap.put("tenkh", nguoimuahang); 
			parameterMap.put("nguoimua",Donvi ); 
			parameterMap.put("diachi", kh_Diachi);
			parameterMap.put("khoxuat", khoxuat); 
			parameterMap.put("masothue", kh_MST);
			parameterMap.put("hinhthuctt",hinhthucTT);
			parameterMap.put("userten", userten);
			parameterMap.put("thukho",thukho);

			// thong tin sum
			List<IErpHoaDonPL_SP> splist =pxkBean.GetSanPhamList();
			double ptvattong= getPtVatTong(splist);
			String ptvattongString=ptvattong==0?"": formatter4.format(ptvattong);
			double totalvat=Double.parseDouble(pxkBean.getAvat() ) - Double.parseDouble( pxkBean.getBvat());
			parameterMap.put("tongtienSauVat", formatter.format(Double.parseDouble(pxkBean.getAvat()))); 
			parameterMap.put("tongtienVat",formatter.format( totalvat));
			parameterMap.put("ptVat",ptvattongString);
			parameterMap.put("tongtienTruocVat",formatter.format(Double.parseDouble( pxkBean.getBvat())));

			doctienrachu doctien = new doctienrachu();
			String tien = doctien.docTien(Math.round(Double.parseDouble(pxkBean.getAvat())));		   
			String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			parameterMap.put("tienchu",TienIN);

			try {
				DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance(); 
				JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.awt.igno‌​re.missing.font","true"); 
				ServletOutputStream servletOutputStream = response.getOutputStream();

				try
				{		
					response.setContentType("application/pdf");
					InputStream reportStream =  new FileInputStream (getServletContext().getInitParameter("pathjasper") + "\\ErpHoaDonKhacPdf.jasper");

					JasperPrint jasperPrint;
					jasperPrint = JasperFillManager.fillReport(reportStream, parameterMap, getDataSource(  pxkBean, db));

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


	private  JRDataSource getDataSource(  IErpHoadonkhac pxkBean,   dbutils db)
	{
		NumberFormat formatter = new DecimalFormat("#,###,###");
		NumberFormat formatter1 = new DecimalFormat("#,###,###");
		NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
		NumberFormat formatter2=new DecimalFormat("#,###,###.##"); 

		Collection<SanphamhoadonkhacObj> splist1 = new ArrayList<SanphamhoadonkhacObj>(); // DANH SANH SP CHI TIET
		List<IErpHoaDonPL_SP> splist =pxkBean.GetSanPhamList();
		int m=0;
		int stt=1;
		System.out.println( " do dai danh sach: "+ splist.size());
		if(splist!= null){
			IErpHoaDonPL_SP sp;
			while(m < splist.size()) { 
				sp = splist.get(m);
				System.out.println(" ten sp :"+ sp.getTenSanPham());
				if(  (sp.getTenSanPham()!= null && sp.getTenSanPham().length() > 0 )  || (sp.getMaSP()!= null && sp.getMaSP().length()>0)  )
				{
					String ma= sp.getMaSP();
					String ten=  sp.getTenSP();
					String tendiengiai= sp.getTenSanPham();
					String donvi= sp.getDonViTinh();
					double soluong=  0;
					double dongia=  0 ;
					if(sp.getSoLuong()!= null && sp.getSoLuong().trim().length()>0){
						soluong=  Double.parseDouble( sp.getSoLuong().replaceAll(",", "")) ;
					}

					if( sp.getDonGia()!= null &&  sp.getDonGia().trim().length()>0){
						dongia=  Double.parseDouble(  sp.getDonGia().replaceAll(",", ""))  ;
					}

					if(pxkBean.getKhoanmucDTId().equals("400002") || pxkBean.getKhoanmucDTId().equals("400003")) {
						dongia = Math.abs(dongia-   Double.parseDouble(sp.getDonGiaDaGiam().replaceAll(",", "")) ) ;
					}

					if(ten.trim().length()<=0){
						ten=tendiengiai;
					}

					String tiendong = sp.getThanhTien().trim().length()<=0?"0":sp.getThanhTien()  ;	
					double tienvat= Double.parseDouble( sp.getTienthue().replaceAll(",", ""));
					double tienthanhtoan=Double.parseDouble(tiendong.replaceAll(",", ""))+tienvat;
					System.out.println(" ten sp :"+ ten);

					// cai sp ko thue suat
					String spptvat="";
					String sptienvat="";
					String IS_KHONGTHUE=sp.getIS_KHONGTHUE();
					if(IS_KHONGTHUE.trim().equals("1")){
						spptvat="X";
						sptienvat="X";
					}
					else
					{
						spptvat= formatter4.format(Double.parseDouble(sp.getThuesuat().replaceAll(",", "")));
						sptienvat=formatter.format(tienvat);
					}

					SanphamhoadonkhacObj obj = new SanphamhoadonkhacObj(Integer.toString(stt), ma, ten,"","",donvi,formatter.format(soluong), formatter4.format(dongia),
							tiendong,spptvat, formatter.format(tienvat),formatter.format(tienthanhtoan)) ;
					splist1.add(obj);
					stt++;
					
				}

				m++;
			}
		}
		return new JRBeanCollectionDataSource(splist1);
	}




	private void CreatePxk_hanoi_2017(Document document, ServletOutputStream outstream, IErpHoadonkhac pxkBean,String queryhd) throws IOException
	{
		try
		{

			dbutils db = new dbutils();

			//LAY THONG TIN NCC
			String kbh="";
			String ddh="";
			String npp_fk="";
			String khId="";
			String ctyTen="";
			String cty_MST ="";
			String cty_Diachi="";
			String cty_Sotaikhoan= "";
			String cty_Dienthoai= "";
			String cty_Fax= "";
			String khoxuat ="";
			String hinhthucTT= "TM/CK/BTCN"; // set cung lun yeu cau ngay 30/9/2017
			String ngayxuathd =pxkBean.getNgayhoadon();
			String nguoimuahang="";
			double chietkhauDH = 0;
			String sql ="";
			String Donvi="";
			String kh_MST ="";
			String kh_Diachi="";
			String thukho = "";


			// lay httt
			/*String[] mangchuoi=new String[]{"TM/CK","CK","TM","Thanh toán sau","Bù trừ công nợ"};
		   for(int k=0;k < mangchuoi.length;k ++ ){
			   if(pxkBean.gethinhthucthanhtoan().equals(mangchuoi[k])) {
				   hinhthucTT=mangchuoi[k];
			   }
		   }*/

			// thong tin khach hang
			String qr="SELECT PK_SEQ, TEN, DIACHI, MASOTHUE, DIENTHOAI, FAX,tennguoidaidien as nguoimuahang from NHAPHANPHOI Where PK_SEQ="  + pxkBean.getkhId();
			System.out.println("  thong tin khac hang:" + qr);
			ResultSet  khRs = db.get(qr);
			if(khRs!=null)
			{
				try {
					while (khRs.next()){  
						Donvi = khRs.getString("TEN");
						kh_MST = khRs.getString("MASOTHUE");
						kh_Diachi = khRs.getString("DIACHI");
						nguoimuahang=khRs.getString("nguoimuahang");
					}

					khRs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			NumberFormat formatter4 = new DecimalFormat("#,###,###.####");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(2.0f*CONVERT, 1.5f*CONVERT, 1.7f*CONVERT, 0.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);

			document.open() ;



			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);


			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(1.0f * CONVERT);//0.67
			cell.setPaddingLeft(12.0f * CONVERT);//2.6
			cell.setVerticalAlignment(Element.ALIGN_TOP);

			String [] ngayHD;
			Paragraph pxk;

			if(ngayxuathd.length()>=10)
			{
				ngayHD = ngayxuathd.split("-");
				pxk = new Paragraph(ngayHD[2] + "                        " + ngayHD[1] +  "                        " + ngayHD[0] , new Font(bf, 8, Font.BOLDITALIC));
			}
			else
				pxk = new Paragraph("", new Font(bf, 8, Font.BOLDITALIC));

			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);

			// Thông tin Khach Hang
			PdfPTable table1 =new PdfPTable(2);
			table1.setWidthPercentage(100);
			float[] withs1 = {15.0f * CONVERT, 15.0f * CONVERT};
			table1.setWidths(withs1);									


			// DONG 1-- NGUOI MUA HANG
			PdfPCell cell_nguoimua = new PdfPCell();	
			pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(4);
			cell_nguoimua.setFixedHeight(0.6f*CONVERT);
			cell_nguoimua.addElement(pxk);
			cell_nguoimua.setBorder(0);						
			table1.addCell(cell_nguoimua);	

			System.out.println("Nguoi mua hang: "+ nguoimuahang );
			PdfPCell cell_nguoimua1 = new PdfPCell();
			//cell_nguoimua1.setPaddingTop(0.5f * CONVERT);
			cell_nguoimua1.setPaddingTop(-0.10f * CONVERT);
			cell_nguoimua1.setVerticalAlignment(Element.ALIGN_TOP);
			cell_nguoimua1.setPaddingLeft(4.5f * CONVERT);
			pxk = new Paragraph(nguoimuahang, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cell_nguoimua1.setFixedHeight(0.6f*CONVERT);
			cell_nguoimua1.addElement(pxk);
			cell_nguoimua1.setBorder(0);						
			table1.addCell(cell_nguoimua1);

			// DONG 2-- DON VI
			PdfPCell cell8 = new PdfPCell();	
			cell8.setPaddingLeft(2.7f * CONVERT);
			pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(4);
			cell8.addElement(pxk);
			cell8.setBorder(0);						
			table1.addCell(cell8);	

			PdfPCell cell8a = new PdfPCell();
			cell8a.setPaddingTop(-0.19f * CONVERT);
			cell8a.setPaddingLeft(3.7f * CONVERT);
			pxk = new Paragraph(Donvi, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell8a.addElement(pxk);
			cell8a.setBorder(0);						
			table1.addCell(cell8a);


			// DONG 3 ---- DIA CHI
			PdfPCell cell10 = new PdfPCell();
			cell10.setPaddingLeft(2.5f * CONVERT);	
			cell10.setPaddingTop(-0.1f * CONVERT);
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell10.addElement(pxk);
			cell10.setBorder(0);						
			table1.addCell(cell10);

			String chuoi_= "";
			String chuoi1= kh_Diachi;
			String chuoi2= "";
			int vitri= 0;
			int dodaichuoi = kh_Diachi.length();
			if(dodaichuoi >= 70)
			{
				chuoi_ = kh_Diachi.substring(0, 70);
				vitri = chuoi_.lastIndexOf(" ");
				chuoi1 = chuoi_.substring(0, vitri);
				chuoi2 = kh_Diachi.substring(vitri + 1,dodaichuoi );
			}

			PdfPCell cell14 = new PdfPCell();
			cell14.setPaddingTop(0.0f * CONVERT);//-0.1
			cell14.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi1, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			//pxk.setSpacingBefore(12.0f);
			cell14.addElement(pxk);
			cell14.setBorder(0);						
			table1.addCell(cell14);		


			//DONG 4 --- DIA CHI : dai se xuong dong
			PdfPCell cell10a = new PdfPCell();
			cell10a.setPaddingTop(-0.3f * CONVERT);
			cell10a.setPaddingLeft(2.5f * CONVERT);	
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell10a.addElement(pxk);
			cell10a.setBorder(0);						
			table1.addCell(cell10a);


			PdfPCell cell14a = new PdfPCell();
			cell14a.setPaddingTop(-0.3f * CONVERT);//-0.5
			cell14a.setPaddingLeft(1.6f * CONVERT);
			pxk = new Paragraph(chuoi2, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);		
			cell14a.addElement(pxk);
			cell14a.setBorder(0);						
			table1.addCell(cell14a);	


			// DONG 5 ----KHO XUAT
			PdfPCell cell17 = new PdfPCell();	
			cell17.setPaddingLeft(2.9f * CONVERT);
			cell17.setPaddingTop(0.1f * CONVERT);
			pxk = new Paragraph(khoxuat, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell17.addElement(pxk);
			cell17.setBorder(0);						
			table1.addCell(cell17);	


			if(kh_MST.trim().length() <= 0)
			{
				kh_MST = "                             ";
			}

			PdfPTable table12 =new PdfPTable(2);
			table12.setWidthPercentage(100);
			float[] withs12 = {10.0f * CONVERT, 10.0f * CONVERT};
			table12.setWidths(withs12);	

			PdfPCell cell181 = new PdfPCell();
			cell181.setPaddingRight(0.8f * CONVERT);
			cell181.setPaddingTop(-0.1f * CONVERT);
			pxk = new Paragraph( kh_MST , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_RIGHT);
			pxk.setSpacingAfter(2);
			cell181.addElement(pxk);
			cell181.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell181.setBorder(0);		
			table12.addCell(cell181); 

			cell181 = new PdfPCell();
			//cell181.setPaddingRight(0.2f * CONVERT);
			cell181.setPaddingTop(-0.1f * CONVERT);
			pxk = new Paragraph( hinhthucTT, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_RIGHT);
			pxk.setSpacingAfter(2);
			cell181.addElement(pxk);
			cell181.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell181.setBorder(0);		
			table12.addCell(cell181); 

			PdfPCell cell18 = new PdfPCell();
			cell18.setBorder(0);	
			cell18.addElement(table12);

			/*cell18.setPaddingLeft(5.0f * CONVERT);
			cell18.setPaddingTop(-0.1f * CONVERT);
			pxk = new Paragraph( kh_MST +"                                                     " +hinhthucTT, new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell18.addElement(pxk);
			cell18.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//	cell18.setBorder(0);	 */ 					
			table1.addCell(cell18);     

			document.add(table1);

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

			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			//	sanpham.setSpacingBefore(46.8f - (float)(0.5*CONVERT));
			sanpham.setSpacingBefore(1.4f*CONVERT);
			//float[] withsKM = { 7.0f, 15.0f, 57f,  20.0f-(float)(0.3*CONVERT), 15f, 9.0f, 14.0f, 20f, 24.0f, 8.0f, 23f, 25f };
			float[] withsKM = { 7.0f, 15.0f, 57f,  20.0f-(float)(0.3*CONVERT), 15f, 13.0f, 13.0f, 23f, 23.0f, 8.0f, 23f, 25f };
			sanpham.setWidths(withsKM);


			PdfPCell cells = new PdfPCell();

			int stt = 1;
			int m=0;
			int numMax=13;//15
			List<IErpHoaDonPL_SP> splist =pxkBean.GetSanPhamList();



			System.out.println( " do dai danh sach: "+ splist.size());
			if(splist!= null){
				IErpHoaDonPL_SP sp;
				while(m < splist.size()) { 
					sp = splist.get(m);

					System.out.println(" ten sp :"+ sp.getTenSanPham());
					if(  (sp.getTenSanPham()!= null && sp.getTenSanPham().length() > 0 )  || (sp.getMaSP()!= null && sp.getMaSP().length()>0)  ){

						String ma= sp.getMaSP();
						String ten=  sp.getTenSP();
						String tendiengiai= sp.getTenSanPham();
						String donvi= sp.getDonViTinh();

						double soluong=  0;
						double dongia=  0 ;

						if(sp.getSoLuong()!= null && sp.getSoLuong().trim().length()>0){
							soluong=  Double.parseDouble( sp.getSoLuong().replaceAll(",", "")) ;
						}

						if( sp.getDonGia()!= null &&  sp.getDonGia().trim().length()>0){
							dongia=  Double.parseDouble(  sp.getDonGia().replaceAll(",", ""))  ;
						}

						if(pxkBean.getKhoanmucDTId().equals("400002") || pxkBean.getKhoanmucDTId().equals("400003")) {
							dongia = Math.abs(dongia-   Double.parseDouble(sp.getDonGiaDaGiam().replaceAll(",", "")) ) ;
						}

						if(ten.trim().length()<=0){
							ten=tendiengiai;
						}

						String tiendong = sp.getThanhTien().trim().length()<=0?"0":sp.getThanhTien()  ;	
						double tienvat= Double.parseDouble( sp.getTienthue().replaceAll(",", ""));
						double tienthanhtoan=Double.parseDouble(tiendong.replaceAll(",", ""))+tienvat;
						System.out.println(" ten sp :"+ ten);


						// cai sp ko thue suat
						String spptvat="";
						String sptienvat="";
						String IS_KHONGTHUE=sp.getIS_KHONGTHUE();
						if(IS_KHONGTHUE.trim().equals("1")){
							spptvat="X";
							sptienvat="X";
						}
						else
						{
							spptvat= formatter4.format(Double.parseDouble(sp.getThuesuat().replaceAll(",", "")));
							sptienvat=formatter.format(tienvat);
						}
						// cai sp ko thue suat


						List <String> tenlist=xuLyChuoi(40,35,27, ten);//50,45

						System.out.println("xu ly chuoi :"+ tenlist.size());
						if(tenlist!= null)
						{
							for (int n = 0; n < tenlist.size(); n++) 
							{

								String chuoi=tenlist.get(n);
								// in dong đầu
								if(n==0)
								{


									String[] arr = new String[] { Integer.toString(stt), ma, chuoi,"","",donvi,
											formatter.format(soluong), formatter4.format(dongia), tiendong, 
											spptvat, 
											formatter.format(tienvat), 
											formatter.format(tienthanhtoan)};



									for (int j = 0; j < th.length; j++)
									{
										cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
										if (j == 2 || j==1 || j==0 ){
											cells.setHorizontalAlignment(Element.ALIGN_LEFT);
										}
										else{

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

										if(j==4 ) cells.setPaddingLeft(-0.10f *CONVERT);
										if(j>=10)
										{
											cells.setPaddingRight(0.3f*CONVERT);
										}

										sanpham.addCell(cells);
									}

									numMax-=1;
									stt++;
								}

								else
								{
									String[] arr = new String[] { "", "", chuoi,"","","","", "", "", "","", ""};



									for (int j = 0; j < th.length; j++)
									{
										cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
										if (j == 2 || j==1 || j==0 ){
											cells.setHorizontalAlignment(Element.ALIGN_LEFT);
										}
										else{

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

										if(j==4 ) cells.setPaddingLeft(-0.10f *CONVERT);
										if(j>=10)
										{
											cells.setPaddingRight(0.3f*CONVERT);
										}

										sanpham.addCell(cells);
									}

									numMax-=1;
								}


							}


						}

					}
					m ++;
				}


			}	


			// DONG TRONG
			while(numMax>0)
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
			}


			// Tong tien thanh toan	

			double totalvat=Double.parseDouble(pxkBean.getAvat() ) - Double.parseDouble( pxkBean.getBvat());
			System.out.println( " VAT : "+pxkBean.getVat() );

			// neu list sp cung vat thi hien vat --> hk cung vat hien rong
			double ptvattong= getPtVatTong(splist);
			String ptvattongString=ptvattong==0?"": formatter4.format(ptvattong);
			String[] arr = new String[] {" ", " " , " ", " "," ", " "," "," ", formatter.format(Double.parseDouble( pxkBean.getBvat())),
					ptvattongString ,formatter.format( totalvat),
					formatter.format(Double.parseDouble(pxkBean.getAvat()))};

			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 2 || j==1 || j==0 ){
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else{

					if(j <=4 )
					{
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
				}

				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);//bottom
				cells.setBorder(0);

				cells.setFixedHeight(0.84f * CONVERT);//0.6

				cells.setPaddingTop(2.5f);

				if(j==4 ) cells.setPaddingLeft(-0.10f *CONVERT);
				if(j>=10)
				{
					cells.setPaddingRight(0.3f*CONVERT);
				}
				sanpham.addCell(cells);
			}


			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
			String tien = doctien.docTien(Math.round(Double.parseDouble(pxkBean.getAvat())));		   
			//Viết hoa ký tự đầu tiên
			String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);

			String[] arr1 = new String[] {"                                           " + TienIN};
			for (int j = 0; j < arr1.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 10, Font.BOLDITALIC)));
				if (j == 0)
				{
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(12);
				} 
				//cells.setVerticalAlignment(Element.ALIGN_TOP);
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setPaddingLeft(0.8f * CONVERT);
				//cells.setPaddingTop(5.0f);
				cells.setPaddingTop(-0.5f);
				cells.setBorder(0);
				cells.setFixedHeight(0.5f*CONVERT);//0.5
				sanpham.addCell(cells);
			}

			document.add(sanpham);




			// Thông tin Khach Hang
			PdfPTable table_footer =new PdfPTable(3);
			table_footer.setWidthPercentage(100);
			float[] withsfooter = { 20.0f * CONVERT ,  13.0f * CONVERT,13.0f * CONVERT};
			table_footer.setWidths(withsfooter);		

			PdfPCell cellfooter = new PdfPCell(new Paragraph(thukho  , new Font(bf, 13, Font.NORMAL)));	
			cellfooter.setBorder(0);
			table_footer.addCell(cellfooter);

			// DONG 1-- NGUOI MUA HANG
			cellfooter = new PdfPCell();	
			pxk = new Paragraph(thukho  , new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cellfooter.setPaddingTop(2.2f*CONVERT);//2.5
			cellfooter.setPaddingRight(2.5f*CONVERT);
			cellfooter.addElement(pxk);
			cellfooter.setBorder(0);
			table_footer.addCell(cellfooter);	
			String userten="";
			ResultSet rsuser=db.get("select ten from nhanvien where pk_seq in(  select nguoitao from erp_hoadonkhac where pk_seq='"+pxkBean.getId()+"' )");
			System.out.println(" lay nguoi in: "+ "select ten from nhanvien where pk_seq in(  select nguoitao from erp_hoadonkhac where pk_seq='"+pxkBean.getId()+"' )");
			try {
				rsuser.next();
				userten=rsuser.getString("ten");
				rsuser.close();
			} catch (Exception e) {
				e.printStackTrace();
			}


			PdfPCell cellfooter2 = new PdfPCell();
			pxk = new Paragraph(userten , new Font(bf, 13, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cellfooter2.setPaddingTop(2.2f*CONVERT);//2.5

			cellfooter2.addElement(pxk);
			cellfooter2.setBorder(0);	
			cellfooter2.setBorderWidth(1);
			table_footer.addCell(cellfooter2);






			document.add(table_footer);

			document.close();

		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}



	private double getPtVatTong (List<IErpHoaDonPL_SP> splist ){

		int m=0;
		double vatruoc=0;
		int kt=0;
		if(splist!= null){
			IErpHoaDonPL_SP sp;
			while(m < splist.size()) { 
				sp = splist.get(m);
				double vat= Double.parseDouble(sp.getThuesuat()) ;
				if( vatruoc != vat){
					kt++;
				}
				vatruoc= vat;
				m++;
			}
		}

		// nếu giống nhau
		if(kt==1){
			return vatruoc;
		}
		else
			return 0;
	}


	private boolean SoNgay (String ngayxuathd){
		boolean kt = false;
		int songay = 0;
		//NẾU NGÀY XUẤT HÓA ĐƠN > '2014-12-08' THÌ ĐƯA VỀ ĐỊNH DẠNG MỚI
		dbutils db = new dbutils();
		String layngay = "select datediff(DD,'2014-01-09', '"+ngayxuathd+"') songay";
		ResultSet checkngay = db.get(layngay);

		try{
			if(checkngay.next())
			{
				songay = checkngay.getInt("songay");
				checkngay.close();
			}
			if(songay >=0 ) kt = true;
		}
		catch (Exception e){
			e.printStackTrace();
			kt = false;
		}

		return kt;

	}



	private double roundNumer(double num, int dec)
	{
		double result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}

	private String DinhDangTRAPHACO(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");

		return sotien;
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





}
