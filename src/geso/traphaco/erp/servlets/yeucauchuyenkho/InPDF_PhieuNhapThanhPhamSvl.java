package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkho;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



@WebServlet("/InPDF_PhieuNhapThanhPhamSvl")
public class InPDF_PhieuNhapThanhPhamSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InPDF_PhieuNhapThanhPhamSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		System.out.println("Action = " + action);
		create_PO_PDF(response, request);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

	private void create_PO_PDF(HttpServletResponse response, HttpServletRequest request)
	throws UnsupportedEncodingException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition",
		" inline; filename=Traphaco_PhieuNhapThanhPham2.pdf");

		float CONVERT = 28.346457f;// 1 cm
		float PAGE_LEFT = 1.0f * CONVERT, PAGE_RIGHT = 1.5f * CONVERT, PAGE_TOP = 0.5f * CONVERT, PAGE_BOTTOM = 0.0f * CONVERT;
		Document document = new Document(PageSize.A4.rotate(), PAGE_LEFT, PAGE_RIGHT,
				PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream;

		try {
			outstream = response.getOutputStream();
			dbutils db = new dbutils();
			CreatePO_Training(document, outstream, response, request, db);
			db.shutDown();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception PO Print: " + e.getMessage());
		}
	}

	private void CreatePO_Training(Document document,
			ServletOutputStream outstream, HttpServletResponse response,
			HttpServletRequest request, dbutils db) throws UnsupportedEncodingException {

		HttpSession session = request.getSession();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String congTyId = (String) session.getAttribute("congtyId");
		System.out.println("congTyId" + congTyId);
		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		System.out.println("userId" + userId);
		String id = util.getId(querystring);
		if (id.length() == 0)
			id = util.antiSQLInspection(request.getParameter("id"));
		System.out.println("id" + id);
		String loaikho = util.antiSQLInspection(request.getParameter("loai"));
		if (loaikho == null)
			loaikho = "";

		System.out.println("\n loai kho nhap" + loaikho);

		IErpChuyenkho lsxBean = new ErpChuyenkho(id);
		lsxBean.setUserId(userId);
		lsxBean.setIsnhanHang("1");
		lsxBean.init(); 


		//lay kho xuat
		String tenkhonhap="";
		ResultSet khonhapRs=lsxBean.getKhoNhapRs();
		if(khonhapRs != null)
		{
			try {
				while(khonhapRs.next()){
					if( khonhapRs.getString("pk_seq").equals(lsxBean.getKhoNhapId())){
						tenkhonhap=khonhapRs.getString("ten");
					}
				}
				khonhapRs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

				
		//lay kho nhan
		String khoxuat="";
		ResultSet khoxuatRs=lsxBean.getKhoXuatRs();
		if(khoxuatRs != null)
		{
			try {
				while(khoxuatRs.next()){
					if( khoxuatRs.getString("pk_seq").equals(lsxBean.getKhoXuatId())){
						khoxuat=khoxuatRs.getString("ten");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//________________________________________________

		NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##");
		NumberFormat formatter = new DecimalFormat("#,###,###");
		NumberFormat formater = new DecimalFormat("##,###,###.############");

		try {

			PdfWriter.getInstance(document, outstream);
			document.open();

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font10_bold = new Font(bf, 10, Font.BOLD);
			Font font10_ita = new Font(bf, 10, Font.ITALIC);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font11_bold = new Font(bf, 11, Font.BOLD);
			Font font11_normal = new Font(bf, 11, Font.NORMAL);
			Font font11_ita = new Font(bf, 11, Font.ITALIC);
			Font font12_bold = new Font(bf, 12, Font.BOLD);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);
			Font font14_bold = new Font(bf, 14, Font.BOLD);

			PdfPTable table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setWidthPercentage(100);
			table.setSpacingBefore(3.0f);
			table.setSpacingAfter(4.0f);

			// insert logo
			Image img = Image.getInstance(getServletContext().getInitParameter("pathPrint")+ "\\logo.gif");
			img.scalePercent(10);
			img.setAbsolutePosition(3.5f * CONVERT, document.getPageSize().getHeight() - 1.2f * CONVERT);
			document.add(img);

			PdfPCell cell;
			cell = new PdfPCell();
			Paragraph p = new Paragraph();
			p = new Paragraph();
			p.add("\n");
			cell.setBorder(0);

			p.add(new Chunk("CÔNG TY TNHH TRAPHACO HƯNG YÊN", font12)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table.addCell(cell);

			cell = new PdfPCell();
			p = new Paragraph();
			
			System.out.println(" ngay xxxx :"+lsxBean.getNgaytao() );
			if(lsxBean.getNgaytao().trim().compareTo("2018-06-13")>=0){
				p.add(new Chunk("BM 53/12\n", font11_ita));  // chung tu moi
			}
			else
			{
				p.add(new Chunk("BM 51/06\n", font11_ita));  // chung tu cu
			}
			p.add(new Chunk("BH/SĐ: 03/07/2017\n", font11_ita)); 
			p.setAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table.addCell(cell);

			document.add(table);

			//Header 2
			Paragraph pr;
			if(loaikho.equals("5"))
			{

				pr = new Paragraph("PHIẾU NHẬP THÀNH PHẨM", new Font(bf, 16, Font.BOLD));

			}
			else{

				pr = new Paragraph("PHIẾU NHẬP VẬT TƯ", new Font(bf, 16, Font.BOLD));
			}
			pr.setSpacingBefore(10);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_CENTER);
			document.add(pr);


			pr = new Paragraph("Số: "+ lsxBean.getId(), new Font(bf, 14, Font.BOLD));
			pr.setSpacingBefore(0);
			/*pr.setSpacingAfter(10);*/
			pr.setAlignment(Element.ALIGN_CENTER);
			document.add(pr);

			pr = new Paragraph("Họ tên người giao hàng: " +lsxBean.getNguoinhan(), font14);
			pr.setSpacingBefore(10);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);

			///

			String sql_lsx= " select  isnull(SX.SOLUONG ,'') as soluong ,isnull(sx1.soluong,'') as soluong1,isnull(sx2.soluong,'') as soluong2, " 
					 +"   ISNULL( (SELECT DV.DONVI FROM DONVIDOLUONG DV WHERE DV.PK_SEQ= BOM.DVDL_FK),'') AS DONVI ,\n" 
					 +"   isnull((SELECT DV1.DONVI FROM DONVIDOLUONG DV1 WHERE DV1.PK_SEQ= BOM1.DVDL_FK),'') as donvi1,\n" 
					 +"   isnull((SELECT DV2.DONVI FROM DONVIDOLUONG DV2 WHERE DV2.PK_SEQ= BOM2.DVDL_FK),'') as donvi2,\n" 
					 +"   isnull(ktt.tennhamay,'') as ten, isnull(ktt1.tennhamay,'') as ten1,\n" 
					 +"    isnull(ktt2.tennhamay,'') as ten2 \n" 
					 +"  from erp_chuyenkho ck \n" 
					 +"  left join erp_lenhsanxuat_giay sxg on ck.lenhsanxuat_fk=sxg.pk_seq\n" 
					 +"  left join ERP_NHAMAY ktt on sxg.nhamay_fk=ktt.pk_seq \n" 
					 +"  left join ERP_LENHSANXUAT_SANPHAM  sx on ck.lenhsanxuat_fk=sx.LENHSANXUAT_FK \n" 
					 +"  left JOIN ERP_DANHMUCVATTU BOM ON BOM.PK_SEQ= SX.DanhMucVT_FK \n" 
					 +" \n " 
					 +"  left join ERP_NHANHANG nh on ck.nhanhang_Fk=nh.pk_seq \n" 
					 +"  left join erp_lenhsanxuat_giay sxg1 on nh.lenhsanxuat_fk=sxg1.pk_seq\n" 
					 +"  left join ERP_NHAMAY ktt1 on sxg1.nhamay_fk=ktt1.pk_seq \n" 
					 +"  left join ERP_LENHSANXUAT_SANPHAM  sx1 on nh.lenhsanxuat_fk=sx1.LENHSANXUAT_FK \n" 
					 +"  left JOIN ERP_DANHMUCVATTU BOM1 ON BOM1.PK_SEQ= SX1.DanhMucVT_FK \n" 
					 +"  \n" 
					 +"  left join ERP_YEUCAUKIEMDINH yckd on ck.yckd_fk=yckd.pk_seq \n" 
					 +"  left join ERP_NHANHANG nh2 on yckd.nhanhang_fk=nh2.pk_seq \n" 
					 +"  left join erp_lenhsanxuat_giay sxg2 on nh2.lenhsanxuat_fk=sxg2.pk_seq \n" 
					 +"  left join ERP_NHAMAY ktt2 on sxg2.nhamay_fk=ktt2.pk_seq \n" 
					 +"  left join ERP_LENHSANXUAT_SANPHAM  sx2 on nh2.lenhsanxuat_fk=sx2.LENHSANXUAT_FK \n" 
					 +"  left JOIN ERP_DANHMUCVATTU BOM2 ON BOM2.PK_SEQ= SX2.DanhMucVT_FK \n" 
					 +" \n" 
					 +"  where ck.pk_seq='"+ 	lsxBean.getId()+"'"	;
	
	String soluongsx="",diachi="",px="";
	System.out.println(" so luong san xuat nhap:" + sql_lsx);
	ResultSet rs_lsx= db.get(sql_lsx);
	if(rs_lsx!=null){
		try {
			while(rs_lsx.next()){
				soluongsx= formatter.format(rs_lsx.getDouble("SOLUONG")+rs_lsx.getDouble("SOLUONG1")+rs_lsx.getDouble("SOLUONG2")  ) 
						+  " " +  rs_lsx.getString("DONVI") +rs_lsx.getString("DONVI1")+rs_lsx.getString("DONVI2");
				px=rs_lsx.getString("ten")+rs_lsx.getString("ten1")+rs_lsx.getString("ten2");
			}
			rs_lsx.close();
		} catch (Exception e) {
		e.printStackTrace();
		}
	}
	System.out.println("pr "+px);
			
			if(loaikho.equals("5"))
			{

				pr = new Paragraph("Địa chỉ: "+px  , font14);

			}
			else{

				pr = new Paragraph("Địa chỉ: "+ khoxuat , font14);
			}
		
			pr.setSpacingBefore(3);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);

			pr = new Paragraph("Lý do nhập: " +lsxBean.getLydoyeucau(), font14);
			pr.setSpacingBefore(3);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);

			
			// so luong lo sx
			// lay so luong xuat cua so lo san xuat cua lenh san xuat
				
			
			if( lsxBean.getLsxIds()!=null && lsxBean.getLsxIds()!="") {
			pr = new Paragraph("Số lượng lô sản xuất : "+soluongsx, font14);
			pr.setSpacingBefore(3);
			pr.setSpacingAfter(3);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);
			}
			
			


			pr = new Paragraph("Nhập tại kho: " + tenkhonhap, font14);
			pr.setSpacingBefore(3);
			pr.setSpacingAfter(20);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);

			// Data table => Dựa vào loại kho mà chọn cặp crPhieuNhap-spTitles phù hợp

			// 1. Kho phụ liệu cấp 1
			float[] crPhieuNhap1 = { 0.5f * CONVERT, 0.7f * CONVERT, 2.0f * CONVERT,
					1.0f * CONVERT, 0.7f * CONVERT, 1.0f * CONVERT, 1.0f * CONVERT,
					0.5f * CONVERT, 0.6f * CONVERT, 1.0f * CONVERT, 1.9f * CONVERT};

			// 2. Kho phụ liệu cấp 2
			float[] crPhieuNhap2 = { 0.5f * CONVERT, 1.0f * CONVERT, 2.5f * CONVERT,
					1.0f * CONVERT, 0.7f * CONVERT, 1.5f * CONVERT, 1.0f * CONVERT,
					1.0f * CONVERT, 1.0f * CONVERT, 2.0f * CONVERT};

			// 3. Kho nguyên liệu
			float[] crPhieuNhap3 = { 0.5f * CONVERT, 1.0f * CONVERT, 2.0f * CONVERT,
					1.0f * CONVERT, 0.7f * CONVERT, 1.0f * CONVERT, 0.7f * CONVERT,
					1.0f * CONVERT, 1.0f * CONVERT, 0.8f * CONVERT, 1.0f * CONVERT, 1.4f * CONVERT};

			// 4. Kho Cao
			float[] crPhieuNhap4 = { 0.5f * CONVERT, 0.8f * CONVERT, 1.7f * CONVERT,
					1.2f * CONVERT, 0.8f * CONVERT, 1.0f * CONVERT, 0.7f * CONVERT,
					1.0f * CONVERT, 0.7f * CONVERT, 0.8f * CONVERT, 1.0f * CONVERT, 1.4f * CONVERT};
			
			// 5. Kho thành phẩm
			float[] crPhieuNhap5 = { 0.3f * CONVERT, 1.0f * CONVERT,
					2.5f * CONVERT, 0.8f * CONVERT, 1.0f * CONVERT,
					1.4f * CONVERT, 1.4f * CONVERT,0.7f * CONVERT, 2.4f * CONVERT};

			int socot;
			if(loaikho.equals("1")){
				socot=crPhieuNhap1.length;
			}
			else if(loaikho.equals("2")){
				socot=crPhieuNhap2.length;
			}
			else if(loaikho.equals("3")){
				socot=crPhieuNhap3.length;
			}
			else if(loaikho.equals("4")){
				socot=crPhieuNhap4.length;
			}
			else {
				socot=crPhieuNhap5.length;
			}


			table = new PdfPTable(socot);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);

			table.getDefaultCell().setBorder(0);
			table.setSpacingAfter(8.0f);



			// 1. Kho phụ liệu cấp 1
			String[] spTitles1 = { "TT", "Mã hàng","Tên hàng", "Đơn vị", "Số lượng", "Số PKN",
					"Số lô EO", "Mẻ", "Thùng", "Số lô NSX", "Vị trí"};

			// 2. Kho phụ liệu cấp 2
			String[] spTitles2 = { "TT", "Mã hàng","Tên hàng", "Đơn vị", "Số lượng", "Số PKN",
					"Maquette", "Số đăng kí", "Số lô NSX", "Vị trí"};
			// 3. Kho nguyên liệu
			String[] spTitles3 = { "TT", "Mã hàng","Tên hàng", "Đơn vị", "Số lượng", "Số PKN",
					"Hàm ẩm", "Hàm lượng", "Phiếu định tính", "Thùng", "Số lô NSX", "Vị trí"};

			// 4. Kho Cao
			String[] spTitles4 = { "TT", "Mã hàng","Tên hàng", "Đơn vị", "Số lượng", "Số PKN",
					"Hàm ẩm", "Hàm lượng", "Mẻ", "Thùng", "Số lô NSX", "Vị trí"};

			// 5. Kho thành phẩm
			String[] spTitles5 = { "TT", "Mã hàng","Tên hàng", "Đơn vị", "Số lượng", 
					"Số lô", "Số PKN", "Mã mẻ",  "Vị trí"};

			if(loaikho.equals("1")){

				table.setWidths(crPhieuNhap1);
				//in tieu de
				for (int z = 0; z < spTitles1.length; z++) {
					cell = new PdfPCell(new Paragraph(spTitles1[z], font11_bold));
					cell.setPadding(3.0f);
					cell.setPaddingBottom(7);
					//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
				}
			}
			else
				if(loaikho.equals("2")){

					table.setWidths(crPhieuNhap2);
					//in tieu de
					for (int z = 0; z < spTitles2.length; z++) {
						cell = new PdfPCell(new Paragraph(spTitles2[z], font11_bold));
						cell.setPadding(3.0f);
						cell.setPaddingBottom(7);
						//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cell);

					}
				}
				else
					if(loaikho.equals("3")){

						table.setWidths(crPhieuNhap3);
						//in tieu de
						for (int z = 0; z < spTitles3.length; z++) {
							cell = new PdfPCell(new Paragraph(spTitles3[z], font11_bold));
							cell.setPadding(3.0f);
							cell.setPaddingBottom(7);
							//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cell);

						}
					}
					else	if(loaikho.equals("4")){

						table.setWidths(crPhieuNhap4);
						//in tieu de
						for (int z = 0; z < spTitles4.length; z++) {
							cell = new PdfPCell(new Paragraph(spTitles4[z], font11_bold));
							cell.setPadding(3.0f);
							cell.setPaddingBottom(7);
							//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cell);

						}
					}
					else{

						table.setWidths(crPhieuNhap5);
						//in tieu de
						for (int z = 0; z < spTitles5.length; z++) {
							cell = new PdfPCell(new Paragraph(spTitles5[z], font11_bold));
							cell.setPadding(3.0f);
							cell.setPaddingBottom(7);
							//cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cell);
						}
					}

			// Chỗ này viết câu query ( query lấy data đổ vào các dòng table)

			List<geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau> danhsach_sp = (List<geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau>)lsxBean.getSpList(); 
			Hashtable<String, String> sanpham_soluong = lsxBean.getSanpham_Soluong();

			// Chỗ này viết vòng FOR lấy data từ ResultSet đổ vào table

			//in danh sach san pham 
			int  j =0;  
			int sott = 1;
			if(danhsach_sp!=null)
				while (j< danhsach_sp.size()) { 

					IYeucau sanpham=danhsach_sp.get(j);
					String Masp=sanpham.getMa();
					String Tensp=sanpham.getTen();
					String Donvi=sanpham.getDonViTinh();

					System.out.println("ma san pham:" + Masp);

					//duyet trong thong tin cua sanpham_soluong
					Enumeration<String> keys = sanpham_soluong.keys(); 
					while( keys.hasMoreElements() )
					{
						String key = keys.nextElement();
						System.out.println("\n key trong hastable:" + key);

						if( key.startsWith(sanpham.getMa() ) )
						{
							String[] arr = key.split("__");
							String temID = sanpham.getMa() + "__ ";
							String soluongXUAT = sanpham_soluong.get(key);
							String solo=arr[2];
							String ngayhh=arr[3];
							String ngaynk=arr[4];
							String me=arr[5];
							String thung=arr[6];
							String vitri=arr[7];
							String maphieu=arr[8];
							String phieudt=arr[9];
							String phieueo=arr[10];
							String marquete=arr[11];
							/*String hamluong=arr[12];
							String hamam=arr[13];*/
							
							String hamluong=arr[12].equals("100")?" ":arr[12] ;
                			String hamam=arr[13].equals("0")?" ":arr[13];
                			
                			String vitriMA="";
                			vitriMA=arr.length>15? arr[15]:"";
                			
                			
                			
							
							String pkn=maphieu;
							String phieudangki="";
							System.out.println("thong tin san pham so lo:" + solo);

							//day la thong cua san pham
							//xet loai
							String[] sp_data1={sott+" ",Masp,Tensp,Donvi, formater.format(Double.parseDouble( soluongXUAT )),pkn,phieueo,me,thung,solo,vitriMA};
							String[] sp_data2={sott+" ",Masp,Tensp,Donvi, formater.format(Double.parseDouble( soluongXUAT )),pkn,marquete,phieudangki,solo,vitriMA};
							String[] sp_data3={sott+" ",Masp,Tensp,Donvi, formater.format(Double.parseDouble( soluongXUAT )),pkn,hamam,hamluong,phieudt,thung,solo,vitriMA};
							String[] sp_data4={sott+" ",Masp,Tensp,Donvi, formater.format(Double.parseDouble( soluongXUAT )),pkn,hamam,hamluong,me,thung,solo,vitriMA};
							String[] sp_data5={sott+" ",Masp,Tensp,Donvi, formater.format(Double.parseDouble( soluongXUAT )), solo,pkn,me,vitriMA};

							if(loaikho.equals("1")){
								for (int z = 0; z < sp_data1.length; z++) {
									cell = new PdfPCell(new Paragraph(sp_data1[z],new Font(bf, 11,Font.NORMAL)));
									cell.setPadding(3.0f);
									cell.setPaddingBottom(7);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

									if(z==1 || z==2||z==10||z==5){
										cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									}
									else
										if(z==4 ){
											cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
										}
										else
											cell.setHorizontalAlignment(Element.ALIGN_CENTER);

									table.addCell(cell);
								}
								sott++;
							}
							if(loaikho.equals("2")){

								for (int z = 0; z < sp_data2.length; z++) {
									cell = new PdfPCell(new Paragraph(sp_data2[z],new Font(bf, 11,Font.NORMAL)));
									cell.setPadding(3.0f);
									cell.setPaddingBottom(7);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									if(z==1 || z==2||z==9||z==5){
										cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									}
									else
										if(z==4 ){
											cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
										}
										else
											cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									table.addCell(cell);
								}
								sott++;
							}
							if(loaikho.equals("3")){

								for (int z = 0; z < sp_data3.length; z++) {
									cell = new PdfPCell(new Paragraph(sp_data3[z],new Font(bf, 11,Font.NORMAL)));
									cell.setPadding(3.0f);
									cell.setPaddingBottom(7);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									if(z==1 || z==2||z==11||z==5){
										cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									}
									else
										if(z==4 ){
											cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
										}
										else
											cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									table.addCell(cell);
								}
								sott++;
							}
							if(loaikho.equals("4")){
								for (int z = 0; z < sp_data4.length; z++) {
									cell = new PdfPCell(new Paragraph(sp_data4[z],new Font(bf, 11,Font.NORMAL)));
									cell.setPadding(3.0f);
									cell.setPaddingBottom(7);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									if(z==1 || z==2||z==11||z==5){
										cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									}
									else
										if(z==4 ){
											cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
										}
										else
											cell.setHorizontalAlignment(Element.ALIGN_CENTER);


									table.addCell(cell);
								}
								sott++;
							}
							if(loaikho.equals("5")){
								for (int z = 0; z < sp_data5.length; z++) {
									cell = new PdfPCell(new Paragraph(sp_data5[z],new Font(bf, 11,Font.NORMAL)));
									cell.setPadding(3.0f);
									cell.setPaddingBottom(7);
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									if(z==1 || z==2||z==8){
										cell.setHorizontalAlignment(Element.ALIGN_LEFT);
									}
									else
										if(z==4 ){
											cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
										}
										else
											cell.setHorizontalAlignment(Element.ALIGN_CENTER);
									table.addCell(cell);
								}
								sott++;
							}


						}
					}	
					j++;
				}





			//-------------------------------------------------

			document.add(table);
			
			
			String ngaychungtu=lsxBean.getNgayyeucau();
			
			if(ngaychungtu.length()>8){
			pr = new Paragraph("Hưng Yên, ngày "+ngaychungtu.substring(8)+"  tháng "+ ngaychungtu.substring(5,7)  + " năm "+ ngaychungtu.substring(0,4), font11_normal);
			}
			else
			{
				pr = new Paragraph("Hưng Yên, ngày  ...  tháng   ...   năm	                         ", font11_normal);
				
			}

			pr.setSpacingBefore(5);
			pr.setAlignment(Element.ALIGN_RIGHT);
			document.add(pr);

			if(loaikho.equals("4"))
			{
			pr = new Paragraph("             GIÁM ĐỐC                                  "+ "PP.KH-CƯVT                                 "+
					"NGƯỜI GIAO                                      " + "THỦ KHO                                   " + "LẬP PHIẾU", font11_bold);
			
			/*pr = new Paragraph("        T/L TỔNG GIÁM ĐỐC                                   "+ "QĐPX                                 "+
					"P. KẾ HOẠCH                                 " + "THỦ KHO                                " + "LẬP PHIẾU", font11_bold);
		*/	
			 
			/*pr = new Paragraph("           T/L TỔNG GIÁM ĐỐC                                                      "+ 
										"NGƯỜI GIAO                               "+
										"                          THỦ KHO                                                     " + 
										
										"LẬP PHIẾU", font11_bold);*/
		
			
			
			pr.setSpacingBefore(5);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);
			
			
			
			pr = new Paragraph("        Phạm Thị Thanh Duyên",font11_normal);
			pr.setSpacingBefore(2f*CONVERT);
			pr.setAlignment(Element.ALIGN_LEFT);
			document.add(pr);
			
			}
			else
			if(loaikho.equals("5"))
			{

				table = new PdfPTable(4);
				table.setWidthPercentage(100);
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.getDefaultCell().setBorder(0);
				
				cell = new PdfPCell(new Paragraph("T/L. GIÁM ĐỐC",font11_bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(0.2f*CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
				/*cell = new PdfPCell(new Paragraph("TP. KH-CƯVT",font11_bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingTop(0.2f*CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				table.addCell(cell);*/
				
				cell = new PdfPCell(new Paragraph("THỦ KHO",font11_bold));
				cell.setPaddingTop(0.2f*CONVERT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("NGƯỜI GIAO HÀNG",font11_bold));
				cell.setPaddingTop(0.2f*CONVERT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("NGƯỜI LẬP PHIẾU",font11_bold));
				cell.setPaddingTop(0.2f*CONVERT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				table.addCell(cell);
				
				//
				cell=new PdfPCell(new Paragraph("TP.KH-CƯVT",font12_bold));
			//	cell.setPaddingTop(0.2f*CONVERT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBorder(0);
				table.addCell(cell);
				
				cell=new PdfPCell(new Paragraph("",font12_bold));
				cell.setPaddingTop(0.2f*CONVERT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				cell.setColspan(3);
				table.addCell(cell);
				
				//---------
				cell = new PdfPCell(new Paragraph("Trần Công Vĩnh",font11_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(3.0f*CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
				
			/*	cell = new PdfPCell(new Paragraph("Trần Công Vĩnh",font11_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(3.0f*CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
				*/
				cell = new PdfPCell(new Paragraph("Nguyễn Thu Hà",font11_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(3.0f*CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(lsxBean.getNguoinhan(),font11_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(3.0f*CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Đoàn Thị Hằng",font11_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(3.0f*CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
				
				document.add(table);
			}
			else
			{

				table = new PdfPTable(5);
				table.setWidthPercentage(100);
				table.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.getDefaultCell().setBorder(0);
				
				cell = new PdfPCell(new Paragraph("T/L. GIÁM ĐỐC",font11_bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(0.2f*CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("PP.KH-CƯVT",font11_bold));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingTop(0.2f*CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("NGƯỜI GIAO",font11_bold));
				cell.setPaddingTop(0.2f*CONVERT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("THỦ KHO",font11_bold));
				cell.setPaddingTop(0.2f*CONVERT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				table.addCell(cell);
								
				cell = new PdfPCell(new Paragraph("NGƯỜI LẬP PHIẾU",font11_bold));
				cell.setPaddingTop(0.2f*CONVERT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				table.addCell(cell);
				
				
				cell=new PdfPCell(new Paragraph("TP.KH-CƯVT",font12_bold));
				//	cell.setPaddingTop(0.2f*CONVERT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(0);
					table.addCell(cell);
					
					cell=new PdfPCell(new Paragraph("",font12_bold));
					cell.setPaddingTop(0.2f*CONVERT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(0);
					cell.setColspan(4);
					table.addCell(cell);
				//---------------------------------------
				cell = new PdfPCell(new Paragraph("Trần Công Vĩnh",font11_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(3.0f*CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Đỗ Thị Thanh Tâm",font11_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(3.0f*CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(lsxBean.getNguoinhan(),font11_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(3.0f*CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
				
				if(loaikho.equals("3"))
				{
					
					if(lsxBean.getNgaytao().trim().compareTo("2018-06-13")>=0){
					// tu ngay 2018-06-13:lay ten moi
					cell = new PdfPCell(new Paragraph("Nguyễn Thị Hải Yến",font11_normal));
					}
					else
					{
						// chưng tu  < 2018-06-13
						cell = new PdfPCell(new Paragraph("Hy Thị Thanh",font11_normal));
					}
				}
				else
				if(loaikho.equals("2"))
					cell = new PdfPCell(new Paragraph("Nguyễn Thu Hà",font11_normal));
				else
				if(loaikho.equals("1"))
					cell = new PdfPCell(new Paragraph("Trương Thị Hồng Nhung",font11_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(3.0f*CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Đoàn Xuân Thông",font11_normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(3.0f*CONVERT);
				cell.setBorder(0);
				table.addCell(cell);
				
				document.add(table);
			}
			
			
			document.close();

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception print PDF: " + e.getMessage());
		}
	}

}
