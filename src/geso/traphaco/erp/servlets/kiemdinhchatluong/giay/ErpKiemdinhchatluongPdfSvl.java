package geso.traphaco.erp.servlets.kiemdinhchatluong.giay;

import geso.traphaco.erp.beans.kiemdinhchatluong.giay.*;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.imp.*;
import geso.traphaco.center.beans.doctien.DocTien;
import geso.traphaco.center.beans.thongtinsanpham.ITieuchikiemdinh;
import geso.traphaco.center.beans.thongtinsanpham.imp.Tieuchikiemdinh;
import geso.traphaco.center.util.*;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.aspose.cells.BorderType;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
//import com.lowagie.text.Cell;


public class ErpKiemdinhchatluongPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public ErpKiemdinhchatluongPdfSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpKiemdinhchatluong dcsxBean;

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		System.out.println(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);

		dcsxBean = new ErpKiemdinhchatluong(id);
		dcsxBean.setYcId(id);
		dcsxBean.setUserId(userId);
		
		String muahang = util.antiSQLInspection(request.getParameter("muahang"));
		if(muahang == null) muahang = "";

		System.out.println("muahang:"+muahang);
		//dcsxBean.init();
		session.setAttribute("dcsxBean", dcsxBean);
		
		if(querystring.contains("print"))
		{		
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=Kiemdinhchatluong.pdf");
			
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();			
			
			if(muahang.equals("1"))//PHIẾU KIỂM ĐỊNH MUA HÀNG
				this.CreateKiemdinhchatluongMHPdf(document, outstream,dcsxBean, id);		
			else
				this.CreateKiemdinhchatluongPdf(document, outstream,dcsxBean, id);			
			
			document.close();
		}		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	float CONVERT = 28.346457f; 
	
	
	private void CreateKiemdinhchatluongMHPdf(Document document, ServletOutputStream outstream, IErpKiemdinhchatluong pxkBean, String Id) throws IOException
	{
		try{
			dbutils db = new dbutils();
			
			//LẤY THÔNG TIN PHIẾU KIỂM ĐỊNH
			String query =
				" select a.DVDL_NHAP_FK, a.pk_seq, a.nhapkho_fk  as sonhapkho,a.ngaysanxuat,isnull(a.ngaynhan,'') as ngaynhan , c.pk_seq as spId, c.MA  + ' - ' + c.Ten  +  ' (' + isnull(dvdl.diengiai, '')  +')' as spTen, a.SOLUONG_DVNHAP as SoLuongKiemDinh,isnull(a.SOLUONGDAT_DVNHAP,0) as soluongdat , a.solo, isnull(SOLUONGMAU_DVNHAP,0)  as soluongmau , " +				
				" a.trangthai,a.LenhSanXuat_FK,a.CongDoan_FK,isnull(a.DatChatLuong,0) as DatChatLuong,Isnull(a.DinhLuong,0)as DinhLuong,isnull(a.DinhTinh,0) as DinhTinh,isnull(a.NgayKiem,'') as NgayKiem,isnull(d.Ten,'') as NguoiDuyet, isnull(a.denghixuly, '') as denghixuly, ISNULL(NCC.TEN,'') AS nhacungcap, isnull(a.KYHIEUMAU,'') KYHIEUMAU \n"+   
				" from ERP_YeuCauKiemDinh a \n"+   
				" inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ \n"+  
				" left join nhanvien d on d.pk_seq=a.NguoiDuyet \n"+  
				" left join donvidoluong dvdl on dvdl.pk_seq = c.dvdl_fk \n"+
				" LEFT JOIN ERP_NHANHANG B ON A.NHANHANG_FK = B.PK_SEQ  \n"+
				" left join ERP_NHACUNGCAP NCC ON b.NCC_KH_FK = NCC.PK_SEQ  "+
				" where a.pk_seq = '"+Id+"'";
			
			System.out.println(query);
			String SOCHUNGTU = "";
			String NGAYSANXUAT = "";
			double SOLUONG = 0;
			String NGAYLAYMAU = "";
			String DONVISANXUAT = "";
			String KETQUAKT = "";
			String TENSP = "";
			String CONGDOANSX = "";
			String SPID = "";
			String KETLUAN = "";
			String KYHIEUKD = "";
			
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					SOCHUNGTU = rs.getString("pk_seq");
					NGAYSANXUAT = rs.getString("ngaysanxuat");
					SOLUONG = rs.getDouble("soluongdat");
					NGAYLAYMAU = rs.getString("ngaykiem").trim().length() <= 0 ? getDateTime() : rs.getString("NgayKiem").trim();
					DONVISANXUAT = rs.getString("nhacungcap");
					KETQUAKT = "";
					TENSP = rs.getString("spTen")== null?"":rs.getString("spTen");;
					CONGDOANSX=rs.getString("CongDoan_FK");
					SPID = rs.getString("spId")== null?"":rs.getString("spId");
					KETLUAN = rs.getString("denghixuly");
					KYHIEUKD = rs.getString("KYHIEUMAU");
				}
			}
			rs.close();
			
			query =
				" SELECT SUM(ISNULL(SOLUONGDUYET,0))-SUM(ISNULL(SOLUONGMAU,0))- SUM(ISNULL(SOLUONGHONG,0)) AS SOLUONGDAT \n"+ 
				" FROM ERP_KIEMDINHCHATLUONG_LANDUYET DUYET INNER JOIN NHANVIEN NV ON NV.PK_SEQ=DUYET.NGUOIDUYET \n"+  
				" where DUYET.YEUCAUKIEMDINH_FK="+Id+" \n"+
				" GROUP BY DUYET.YEUCAUKIEMDINH_FK ";
			
			System.out.println(query);
			ResultSet rssoluongdat = db.get(query);
			if(rssoluongdat!=null){
				while(rssoluongdat.next()){					
					SOLUONG = rssoluongdat.getDouble("SOLUONGDAT");					
				}
			}
			rssoluongdat.close();
			
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			//document.setPageSize(PageSize.A4.rotate()); CANH HÓA ĐƠN THEO CHIỀU DỌC
			document.setMargins(2.0f*CONVERT, 1.0f*CONVERT, 1.0f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			font.setStyle("bold,underline");
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			//CÔNG TY HẠ LONG - BM.01/CQSPT
			PdfPTable tableheader =new PdfPTable(2);
			tableheader.setWidthPercentage(100);
			float[] withsheader = {200f,200f};
			tableheader.setWidths(withsheader);

			PdfPCell cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						
			Paragraph hd = new Paragraph("CÔNG TY CP ĐỒ HỘP HẠ LONG" , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(1.6f*CONVERT);
			//cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			//cell.setPaddingLeft(1.8f*CONVERT);
			//cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			
			
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);						
			hd = new Paragraph(KYHIEUKD , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(1.6f*CONVERT);
			//cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			//cell.setPaddingLeft(1.8f*CONVERT);
			//cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			
			document.add(tableheader);
			
			//GIẤY KIỂM TRA CHẤT LƯỢNG
			
			PdfPTable table1 = new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = {100f};
			table1.setWidths(withs1);			
					
			cell = new PdfPCell();	
			hd = new Paragraph("GIẤY KIỂM TRA CHẤT LƯỢNG", new Font(bf, 16, Font.BOLD));//NGUOI MUA
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table1.addCell(cell);
			document.add(table1);
			
			//SỐ:....
			
			PdfPTable table2 = new PdfPTable(1);
			table2.setWidthPercentage(100);
			float[] withs2 = {100f};
			table1.setWidths(withs2);
					
			cell = new PdfPCell();	
			hd = new Paragraph("SỐ: "+SOCHUNGTU , new Font(bf, 12, Font.BOLDITALIC));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(0.8f*CONVERT);	
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table2.addCell(cell);
			document.add(table2);
			
			//TÊN SẢN PHẨM
			
			PdfPTable table3 =new PdfPTable(1);
			table3.setWidthPercentage(100);
			float[] withs3 = {100f};
			table3.setWidths(withs3);
						
			cell = new PdfPCell();	
			hd = new Paragraph("- Tên sản phẩm: " + TENSP , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table3.addCell(cell);
			document.add(table3);
			
			//NGÀY SẢN XUẤT - SỐ LƯỢNG
			
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200f,200f};
			table4.setWidths(withs4);
				
			cell= new PdfPCell();
			String [] ngaySX = NGAYSANXUAT.split("-");			
			hd = new Paragraph();
			hd.add(new Chunk("- Ngày nhận hàng: ", new Font(bf, 12, Font.BOLD))); 
			hd.add(new Chunk(ngaySX[2] + "/" + ngaySX[1] +  "/" + ngaySX[0], new Font(bf, 12, Font.NORMAL)));
			
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			table4.addCell(cell);
			
			
			cell = new PdfPCell();	
			hd = new Paragraph(" Số lượng: "+ DinhDangTraphacoERP(formatter.format(SOLUONG)) , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.setBorder(0);	
			
			table4.addCell(cell);
			
			document.add(table4);
			
			//NGÀY LẤY MẪU
			
			PdfPTable table5 =new PdfPTable(1);
			table5.setWidthPercentage(100);
			float[] withs5 = {100f};
			table5.setWidths(withs5);
						
			cell = new PdfPCell();	
			String [] ngayLM = NGAYLAYMAU.split("-");
			hd = new Paragraph();
			hd.add(new Chunk("- Ngày kiểm tra: ", new Font(bf, 12, Font.BOLD))); 
			hd.add(new Chunk(ngayLM[2] + "/" + ngayLM[1] +  "/" + ngayLM[0], new Font(bf, 12, Font.NORMAL)));
			
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table5.addCell(cell);
			document.add(table5);
			
			//ĐƠN VỊ SẢN XUẤT
			
			PdfPTable table6 =new PdfPTable(1);
			table6.setWidthPercentage(100);
			float[] withs6 = {100f};
			table6.setWidths(withs6);
						
			cell = new PdfPCell();	
			hd = new Paragraph("- Đơn vị cung cấp: " +DONVISANXUAT , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table6.addCell(cell);
			document.add(table6);
			
			//KẾT QUẢ KIỂM TRA
			
			PdfPTable table7 =new PdfPTable(1);
			table7.setWidthPercentage(100);
			float[] withs7 = {100f};
			table7.setWidths(withs7);
						
			cell = new PdfPCell();	
			hd = new Paragraph("- Kết quả kiểm tra: " , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table7.addCell(cell);
			document.add(table7);
			
			
			//THÔNG TIN KIỂM TRA
			
			String INIT_DINHLUONG = " select count(tieuchi) as sodong from ERP_YeuCauKiemDinh_TieuChi  " +
									" where yeucaukiemdinh_fk = '" + Id + "' and DinhLuong = 1";
			
			System.out.println(INIT_DINHLUONG);
			ResultSet rsCheck = db.get(INIT_DINHLUONG);
			boolean flag = false;
			 
				try 
				{
					if (rsCheck.next())
					{
						if (rsCheck.getString("sodong").equals("0")){
							flag = true;
						}
						
						rsCheck.close();
					}
				} 
				catch (Exception e) {e.printStackTrace();}
		
			INIT_DINHLUONG = " SELECT C.TIEUCHI,C.PHEPTOAN,C.GIATRICHUAN,'' AS DIEMDAT,'0' AS DAT,'0' AS QUYETDINH,'' AS TRANGTHAI,''  AS NGUOISUA "+  
	  		  				 " FROM ERP_CONGDOANSANXUAT_TIEUCHIKIEMDINH_GIAY C   "+
	  		  				 " WHERE C.CONGDOANSANXUAT_FK="+CONGDOANSX+" AND ISNULL(C.DINHLUONG,'0') ='1' "; 
			
			System.out.println(INIT_DINHLUONG);
			
			rsCheck = db.get(INIT_DINHLUONG);
			try{
				if(rsCheck.next()){
					if (rsCheck.getString("sodong").equals("0"))
						flag = true;
					rsCheck.close();
				}
			rsCheck.close();
			}catch(Exception err){}
		
		if(flag)
		{
			INIT_DINHLUONG = " SELECT c.TieuChi,c.PhepToan,c.GiaTriChuan,'' as DiemDat,'0' as Dat,'0' as QuyetDinh,'' as TrangThai,''  as NguoiSua  " +
				  			 " from SANPHAM_TIEUCHIKIEMDINH c " +
				  			 " WHERE c.sanpham_fk="+SPID+" and c.Loai=0  " +
				  			 " UNION  " +
				  			 " SELECT C.TIEUCHI,C.PHEPTOAN,C.GIATRICHUAN,'' AS DIEMDAT,'0' AS DAT,'0' AS QUYETDINH,'' AS TRANGTHAI,''  AS NGUOISUA "+  
				  			 " FROM ERP_CONGDOANSANXUAT_TIEUCHIKIEMDINH_GIAY C   "+
				  			 " WHERE C.CONGDOANSANXUAT_FK="+CONGDOANSX+" AND ISNULL(C.DINHLUONG,0) =1 ";
		}
		else
		{
			INIT_DINHLUONG = 
				 " select a.TieuChi,a.PhepToan,a.GiaTriChuan, isnull( cast(a.DiemDat as nvarchar(10)),'') as diemdat , a.Dat,isnull(b.QuyetDinh,0)as QuyetDinh,isnull(a.TrangThai,'')as TrangThai,c.TEN + '--' +b.NgaySua  as NguoiSua from erp_yeucaukiemdinh_tieuchi a "+
				 " left join "+ 
				 " ( "+
				 "	select * "+
				 "	from Erp_YeuCauKiemDinh_TieuChi_Log "+
				 "	where NgayGio = (select max(NgayGio) from Erp_YeuCauKiemDinh_TieuChi_Log where YeuCauKiemDinh_FK ='"+Id+"' and DinhLuong=1) and YeuCauKiemDinh_FK='"+Id+"' and DinhLuong=1 "+
				 " ) "+
				 "  b on b.YeuCauKiemDinh_FK=a.yeucaukiemdinh_fk and a.tieuchi=b.TieuChi " +
				 "  left join NHANVIEN c on c.PK_SEQ=b.NguoiSua " +
				 "  where a.YeuCauKiemDinh_FK='"+Id+"' and a.DinhLuong=1 and a.DIEMDAT IS NOT NULL ";
		}
	    System.out.println("1.Khoi tao tieu chi dinh luong: " + INIT_DINHLUONG);
	    
	    
	    PdfPTable root = new PdfPTable(1);
		root.setKeepTogether(false);
		root.setSplitLate(false);
		root.setWidthPercentage(100);
		root.setHorizontalAlignment(Element.ALIGN_LEFT);
		root.getDefaultCell().setBorder(0);
		float[] cr = { 100.0f };
		root.setWidths(cr);

		String[] th = new String[]{ "", "", ""};
		
		PdfPTable sanpham = new PdfPTable(th.length);
		sanpham.setSpacingBefore(0.5f*CONVERT);
		sanpham.setWidthPercentage(100);
		sanpham.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		float[] withsKM = { 5.0f*CONVERT, 15.0f*CONVERT, 6.0f*CONVERT};
		sanpham.setWidths(withsKM);
				
		PdfPCell cells = new PdfPCell();
		hd = new Paragraph("Tên chỉ tiêu " , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_CENTER);
		cells.addElement(hd);
		cells.setBorderWidth(1);	
		
		sanpham.addCell(cells);
		
		cells = new PdfPCell();
		hd = new Paragraph("Mức cho phép" , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_CENTER);
		cells.addElement(hd);
		cells.setBorderWidth(1);	
		
		sanpham.addCell(cells);
		
		cells = new PdfPCell();
		hd = new Paragraph("Kết quả " , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_CENTER);
		cells.addElement(hd);
		cells.setBorderWidth(1);	
		
		sanpham.addCell(cells);		
		
		//KIỂM TRA SỐ DÒNG ĐỊNH LƯỢNG
		ResultSet rsSP_dinluong_countdong= db.get(INIT_DINHLUONG);
		
		int stt = 0;
		try 
		{
			while (rsSP_dinluong_countdong.next())
			{				
				stt++;
			}
			rsSP_dinluong_countdong.close();
		} 
		catch (Exception e) {e.printStackTrace();}
		
		String tieuchi = "";
		String mucchophep = "";
		String ketqua = "";
		
		if(stt>0){
		ResultSet rsSP_dinhluong = db.get(INIT_DINHLUONG);
		
		
		cells = new PdfPCell();
		hd = new Paragraph("Định lượng" , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_CENTER);
		cells.addElement(hd);
		cells.setHorizontalAlignment(Element.ALIGN_MIDDLE);
		cells.setBorderWidth(1);	
		cells.setRowspan(stt);
		sanpham.addCell(cells);	
		
		
			if(rsSP_dinhluong!=null){				
				while(rsSP_dinhluong.next()){
					stt++;
					tieuchi = rsSP_dinhluong.getString("TieuChi");
					mucchophep = rsSP_dinhluong.getString("TieuChi");
					ketqua = rsSP_dinhluong.getString("DiemDat")+ " " + rsSP_dinhluong.getString("trangthai");
									
					String[] arr = new String[] { mucchophep ,ketqua };
										
					for (int j = 0; j < 2; j++)
					{
						System.out.println(arr[j]);
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.NORMAL)));
						if (j == 0 ){ //ĐỊNH LƯỢNG
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							//cells.setPaddingLeft(-0.5f*CONVERT);
						}
						else{							
								
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
								
						}
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						//cells.setBorder(0);
						cells.setBorderWidth(1);	
						sanpham.addCell(cells);
					}
					
				}
			}
			rsSP_dinhluong.close();
		}				
			//document.add(sanpham);
		
			String INIT_DINHTINH = 
				" select count(c.TieuChi) as sodong "+
				" from "+ 
				" ERP_YeuCauKiemDinh_TieuChi c  "+ 
				" where c.YeuCauKiemDinh_FK='"+Id+"'  and c.DinhTinh=1 ";
			
		   rsCheck = db.get(INIT_DINHTINH);
		   flag = false;
		   int numb=0; 
		 
			try 
			{
				if (rsCheck.next())
				{
					numb=rsCheck.getInt(1);
					if (numb==0){
						flag = true;
					}					
					rsCheck.close();
				}
			} 
			catch (Exception e) { e.printStackTrace();}
		 
			INIT_DINHTINH = " select count(*) as sodong " +
	  			" from  SanPham_TieuChiKiemDinh cd where cd.sanpham_fk = '" + SPID + "' and loai = '1' "; 
			rsCheck = db.get(INIT_DINHTINH);
			
			if (rsCheck != null)
			{
				try 
				{
					if (rsCheck.next())
					{
						numb=rsCheck.getInt(1);					
					}
					rsCheck.close();
				} 
				catch (Exception e) {e.printStackTrace();}
			}
			
			INIT_DINHTINH = " select count(*) as sodong " +
				" from  Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay cd where " +
				" cd.CongDoanSanXuat_FK = '" +CONGDOANSX + "' AND  ISNULL(DINHTINH,'0') ='1'  "; 
			rsCheck = db.get(INIT_DINHTINH);
	 
			try 
			{
				if (rsCheck.next())
				{
					numb=numb+ rsCheck.getInt(1);
					stt=stt+ rsCheck.getInt(1);
					rsCheck.close();
				}
			} 
			catch (Exception e) { e.printStackTrace();}
			
			if(flag)
			{
				INIT_DINHTINH =" SELECT c.TieuChi, '' as ghinhan  ,'0' as QuyetDinh,'' as TrangThai,''  as NguoiSua  " +
					 " from SANPHAM_TIEUCHIKIEMDINH c " +
					 " WHERE c.sanpham_fk="+SPID+" and c.Loai=1 and (isnull(c.pheptoan,'')!= '') and (isnull(c.giatrichuan,'') != '' ) "+
					 " Union all  SELECT c.TieuChi,'' as GhiNhan,0 as QuyetDinh,'' as TrangThai,''  as NguoiSua  " +
					 " FROM Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay c  " +
					 " WHERE c.CongDoanSanXuat_FK= "+CONGDOANSX+" and c.DinhTinh='1' and (isnull(c.pheptoan,'')!= '') and (isnull(c.giatrichuan,'') != '' ) ";
			}
			else
			{
				INIT_DINHTINH =
					 " select a.TieuChi,isnull(a.GhiNhan,'') as GhiNhan,isnull(b.QuyetDinh,0)as QuyetDinh " +
					 " ,isnull(a.TrangThai,'')as TrangThai,c.TEN + '--' +b.NgaySua  as NguoiSua from erp_yeucaukiemdinh_tieuchi a "+
					 " left join "+ 
					 " ( "+
					 "	select * "+
					 "	from Erp_YeuCauKiemDinh_TieuChi_Log "+
					 "	where NgayGio = (select max(NgayGio) from Erp_YeuCauKiemDinh_TieuChi_Log where YeuCauKiemDinh_FK ='"+SPID+"') and YeuCauKiemDinh_FK='"+Id+"' "+
					 "	) "+
					 " b on b.YeuCauKiemDinh_FK=a.yeucaukiemdinh_fk and a.tieuchi=b.TieuChi and b.DinhTinh=a.DinhTinh  " +
					 " left join NHANVIEN c on c.PK_SEQ=b.NguoiSua where a.YeuCauKiemDinh_FK='"+Id+"' and a.DinhTinh=1  and a.GhiNhan IS not Null and a.GhiNhan != '' ";
			}
			
			System.out.println("2. Định tính: " + INIT_DINHTINH);
			
			ResultSet rsSP_dinhtinh_countdong = db.get(INIT_DINHTINH);
			
			stt = 0;
			try 
			{
				while (rsSP_dinhtinh_countdong.next())
				{				
					stt++;
				}
				rsSP_dinhtinh_countdong.close();
			} 
			catch (Exception e) {e.printStackTrace();}
			
			
			if(stt>0){
			ResultSet rsSP_dinhtinh = db.get(INIT_DINHTINH);
							
			cells = new PdfPCell();
			hd = new Paragraph("Định tính " , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_CENTER);
			cells.addElement(hd);
			cells.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cells.setBorderWidth(1);	
			cells.setRowspan(stt);
			
			sanpham.addCell(cells);	
				
			tieuchi = "";
			mucchophep = "";
			ketqua = "";
				if(rsSP_dinhtinh!=null){				
					while(rsSP_dinhtinh.next()){
						stt++;
						tieuchi = rsSP_dinhtinh.getString("TieuChi");
						mucchophep = rsSP_dinhtinh.getString("TieuChi");
						ketqua =  rsSP_dinhtinh.getString("GhiNhan");
										
						String[] arr = new String[] {  mucchophep ,ketqua };
											
						for (int j = 0; j < 2; j++)
						{
							System.out.println(arr[j]);
							cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.NORMAL)));
							if (j == 0 ){ //ĐỊNH LƯỢNG
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
								//cells.setPaddingLeft(-0.5f*CONVERT);
							}
							else{							
									
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
									
							}
							
							cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
							//cells.setBorder(0);
							cells.setBorderWidth(1);	
							sanpham.addCell(cells);
						}
						
					}
				}
				rsSP_dinhtinh.close();
			}	
				document.add(sanpham);
				
				//KẾT LUẬN
				
				PdfPTable table8 =new PdfPTable(1);
				table8.setWidthPercentage(100);
				float[] withs8 = {100f};
				table8.setWidths(withs8);
							
				cell = new PdfPCell();	
				hd = new Paragraph("KẾT LUẬN ",font);
				hd.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(hd);
				cell.setBorder(0);	
				
				table8.addCell(cell);
				document.add(table8);
				
				//KẾT LUẬN
				
				PdfPTable table9 =new PdfPTable(1);
				table9.setWidthPercentage(100);
				float[] withs9 = {100f};
				table9.setWidths(withs9);
							
				cell = new PdfPCell();	
				hd = new Paragraph(KETLUAN, new Font(bf, 12, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setBorder(0);	
				
				table9.addCell(cell);
				document.add(table9);
				
				//NGÀY THÁNG NĂM
				
				PdfPTable table10 =new PdfPTable(1);
				table10.setWidthPercentage(100);
				float[] withs10 = {100f};
				table10.setWidths(withs10);
							
				cell = new PdfPCell();	
				hd = new Paragraph("Ngày ... tháng ... năm ...", new Font(bf, 12, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_RIGHT);
				cell.addElement(hd);
				cell.setBorder(0);	
				
				table10.addCell(cell);
				document.add(table10);
				
				//NGƯỜI KIỂM TRA - TRƯỞNG PHÒNG QLCL
				
				PdfPTable table11 =new PdfPTable(2);
				table11.setWidthPercentage(100);
				float[] withs11 = {100f, 100f};
				table11.setWidths(withs11);
							
				cell = new PdfPCell();	
				hd = new Paragraph("NGƯỜI KIỂM TRA ", new Font(bf, 12, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(hd);
				cell.setBorder(0);	
				
				table11.addCell(cell);
				
				cell = new PdfPCell();	
				hd = new Paragraph("T.PHÒNG QLCL ", new Font(bf, 12, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(hd);
				cell.setBorder(0);	
				
				table11.addCell(cell);
				
				document.add(table11);
					
					
					
		
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private void CreateKiemdinhchatluongPdf(Document document, ServletOutputStream outstream, IErpKiemdinhchatluong pxkBean, String Id) throws IOException
	{
		try{
			dbutils db = new dbutils();
			
			//LẤY THÔNG TIN PHIẾU KIỂM ĐỊNH
			String query =
				" select a.DVDL_NHAP_FK, a.pk_seq, a.nhapkho_fk  as sonhapkho,a.ngaysanxuat,isnull(a.ngaynhan,'') as ngaynhan , c.pk_seq as spId, c.MA  + ' - ' + c.Ten  +  ' (' + isnull(dvdl.diengiai, '')  +')' as spTen, a.SOLUONG_DVNHAP as SoLuongKiemDinh,isnull(a.SOLUONGDAT_DVNHAP,0) as soluongdat , cast(a.pk_seq as varchar(20)) + '-' + a.solo  as solo, isnull(SOLUONGMAU_DVNHAP,0)  as soluongmau , " +				
				" a.trangthai,a.LenhSanXuat_FK,a.CongDoan_FK,isnull(a.DatChatLuong,0) as DatChatLuong,Isnull(a.DinhLuong,0)as DinhLuong,isnull(a.DinhTinh,0) as DinhTinh,isnull(a.NgayKiem,'') as NgayKiem,isnull(d.Ten,'') as NguoiDuyet, isnull(a.denghixuly, '') as denghixuly, ISNULL(NCC.TEN,'') AS nhacungcap, isnull(KYHIEUMAU, '') KYHIEUMAU, ISNULL(TENNHAMAY,'') TENNHAMAY \n"+   
				" from ERP_YeuCauKiemDinh a \n"+   
				" inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ \n"+  
				" left join nhanvien d on d.pk_seq=a.NguoiDuyet \n"+  
				" left join donvidoluong dvdl on dvdl.pk_seq = c.dvdl_fk \n"+
				" left join erp_lenhsanxuat_giay lsx on lsx.pk_seq= a.LENHSANXUAT_FK  "+ 
				" left join ERP_NHAMAY nm on nm.pk_seq = lsx.nhamay_fk "+
				" LEFT JOIN ERP_NHANHANG B ON A.NHANHANG_FK = B.PK_SEQ  \n"+
				" left join ERP_NHACUNGCAP NCC ON b.NCC_KH_FK = NCC.PK_SEQ  "+
				" where a.pk_seq = '"+Id+"'";
			
			String SOCHUNGTU = "";
			String NGAYSANXUAT = "";
			double SOLUONG = 0;
			String NGAYLAYMAU = "";
			String DONVISANXUAT = "";
			String KETQUAKT = "";
			String TENSP = "";
			String CONGDOANSX = "";
			String SPID = "";
			String KETLUAN = "";
			String KYHIEUMAU = "";
			String SOLO = "";
			
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					SOCHUNGTU = rs.getString("pk_seq");
					NGAYSANXUAT = rs.getString("ngaysanxuat");
					SOLUONG = rs.getDouble("soluongdat");
					NGAYLAYMAU = rs.getString("ngaykiem").trim().length() <= 0 ? getDateTime() : rs.getString("NgayKiem").trim();
					DONVISANXUAT = rs.getString("TENNHAMAY");
					KETQUAKT = "";
					TENSP = rs.getString("spTen")== null?"":rs.getString("spTen");;
					CONGDOANSX=rs.getString("CongDoan_FK");
					SPID = rs.getString("spId")== null?"":rs.getString("spId");
					KETLUAN = rs.getString("denghixuly");
					KYHIEUMAU = rs.getString("KYHIEUMAU");
					SOLO =  rs.getString("SOLO");
				}
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			//document.setPageSize(PageSize.A4.rotate()); CANH HÓA ĐƠN THEO CHIỀU DỌC
			document.setMargins(2.0f*CONVERT, 1.0f*CONVERT, 1.0f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();
			//document.setPageSize(new Rectangle(100.0f, 100.0f));
			//document.setPageSize(PageSize.A4.rotate());
		

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			font.setStyle("bold,underline");
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			
			//CÔNG TY HẠ LONG - BM.01/CQSPT
			PdfPTable tableheader =new PdfPTable(2);
			tableheader.setWidthPercentage(100);
			float[] withsheader = {200f,200f};
			tableheader.setWidths(withsheader);

			PdfPCell cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						
			Paragraph hd = new Paragraph("CÔNG TY CP ĐỒ HỘP HẠ LONG" , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(1.6f*CONVERT);
			//cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			//cell.setPaddingLeft(1.8f*CONVERT);
			//cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			
			
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						
			hd = new Paragraph(KYHIEUMAU , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(1.6f*CONVERT);
			//cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			//cell.setPaddingLeft(1.8f*CONVERT);
			//cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			
			document.add(tableheader);
			
			//GIẤY KIỂM TRA CHẤT LƯỢNG
			
			PdfPTable table1 = new PdfPTable(1);
			table1.setWidthPercentage(100);
			float[] withs1 = {100f};
			table1.setWidths(withs1);			
					
			cell = new PdfPCell();	
			hd = new Paragraph("GIẤY KIỂM TRA CHẤT LƯỢNG", new Font(bf, 16, Font.BOLD));//NGUOI MUA
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table1.addCell(cell);
			document.add(table1);
			
			//SỐ:....
			
			PdfPTable table2 = new PdfPTable(1);
			table2.setWidthPercentage(100);
			float[] withs2 = {100f};
			table1.setWidths(withs2);
					
			cell = new PdfPCell();	
			hd = new Paragraph("SỐ: "+SOCHUNGTU , new Font(bf, 12, Font.BOLDITALIC));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(0.8f*CONVERT);	
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table2.addCell(cell);
			document.add(table2);
			
			//TÊN SẢN PHẨM
			
			PdfPTable table3 =new PdfPTable(1);
			table3.setWidthPercentage(100);
			float[] withs3 = {100f};
			table3.setWidths(withs3);
						
			cell = new PdfPCell();	
			hd = new Paragraph("- Tên sản phẩm: " + TENSP , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table3.addCell(cell);
			document.add(table3);
			
			//NGÀY SẢN XUẤT - SỐ LƯỢNG
			
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200f,200f};
			table4.setWidths(withs4);
				
			cell= new PdfPCell();
			String [] ngaySX = NGAYSANXUAT.split("-");			
			hd = new Paragraph();
			hd.add(new Chunk("- Ngày sản xuất: ", new Font(bf, 12, Font.BOLD))); 
			hd.add(new Chunk(ngaySX[2] + "/" + ngaySX[1] +  "/" + ngaySX[0], new Font(bf, 12, Font.NORMAL)));
			
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);
			
			table4.addCell(cell);
			
			
			cell = new PdfPCell();	
			hd = new Paragraph(" Số lượng: "+ DinhDangTraphacoERP(formatter.format(SOLUONG)) , new Font(bf, 12, Font.BOLD)); 
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setPaddingLeft(2.0f*CONVERT);
			cell.setBorder(0);	
			
			table4.addCell(cell);
			
			document.add(table4);
			
			//SỐ LÔ
			
			PdfPTable table5 =new PdfPTable(1);
			table5.setWidthPercentage(100);
			float[] withs5 = {100f};
			table5.setWidths(withs5);
						
			cell = new PdfPCell();	
			String [] ngayLM = NGAYLAYMAU.split("-");
			hd = new Paragraph();
			hd.add(new Chunk("- Ngày kiểm tra: ", new Font(bf, 12, Font.BOLD))); 
			hd.add(new Chunk(ngayLM[2] + "/" + ngayLM[1] +  "/" + ngayLM[0], new Font(bf, 12, Font.NORMAL)));
			
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table5.addCell(cell);
			document.add(table5);
			
			//NGÀY LẤY MẪU
			
			PdfPTable table6_1 =new PdfPTable(1);
			table6_1.setWidthPercentage(100);
			float[] withs6_1 = {100f};
			table6_1.setWidths(withs6_1);
						
			cell = new PdfPCell();	
			hd = new Paragraph();
			hd.add(new Chunk("- Số lô: ", new Font(bf, 12, Font.BOLD))); 
			hd.add(new Chunk(SOLO, new Font(bf, 12, Font.NORMAL)));
			
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table6_1.addCell(cell);
			document.add(table6_1);
			
			//ĐƠN VỊ SẢN XUẤT
			
			PdfPTable table6 =new PdfPTable(1);
			table6.setWidthPercentage(100);
			float[] withs6 = {100f};
			table6.setWidths(withs6);
						
			cell = new PdfPCell();	
			hd = new Paragraph();
			hd.add(new Chunk("- Đơn vị sản xuất: ", new Font(bf, 12, Font.BOLD))); 
			hd.add(new Chunk(DONVISANXUAT, new Font(bf, 12, Font.NORMAL)));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table6.addCell(cell);
			document.add(table6);
			
			//KẾT QUẢ KIỂM TRA
			
			PdfPTable table7 =new PdfPTable(1);
			table7.setWidthPercentage(100);
			float[] withs7 = {100f};
			table7.setWidths(withs7);
						
			cell = new PdfPCell();	
			hd = new Paragraph("- Kết quả kiểm tra: " , new Font(bf, 12, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);	
			
			table7.addCell(cell);
			document.add(table7);
			
			
			//THÔNG TIN KIỂM TRA
			
			String INIT_DINHLUONG = " select count(tieuchi) as sodong from ERP_YeuCauKiemDinh_TieuChi  " +
									" where yeucaukiemdinh_fk = '" + Id + "' and DinhLuong = 1";
			
			System.out.println(INIT_DINHLUONG);
			ResultSet rsCheck = db.get(INIT_DINHLUONG);
			boolean flag = false;
			 
				try 
				{
					if (rsCheck.next())
					{
						if (rsCheck.getString("sodong").equals("0")){
							flag = true;
						}
						
						rsCheck.close();
					}
				} 
				catch (Exception e) {e.printStackTrace();}
		
			INIT_DINHLUONG = " SELECT C.TIEUCHI,C.PHEPTOAN,C.GIATRICHUAN,'' AS DIEMDAT,'0' AS DAT,'0' AS QUYETDINH,'' AS TRANGTHAI,''  AS NGUOISUA "+  
	  		  				 " FROM ERP_CONGDOANSANXUAT_TIEUCHIKIEMDINH_GIAY C   "+
	  		  				 " WHERE C.CONGDOANSANXUAT_FK="+CONGDOANSX+" AND ISNULL(C.DINHLUONG,'0') ='1' "; 
			
			System.out.println(INIT_DINHLUONG);
			
			rsCheck = db.get(INIT_DINHLUONG);
			try{
				if(rsCheck.next()){
					if (rsCheck.getString("sodong").equals("0"))
						flag = true;
					rsCheck.close();
				}
			rsCheck.close();
			}catch(Exception err){}
		
		if(flag)
		{
			INIT_DINHLUONG = " SELECT c.TieuChi,c.PhepToan,c.GiaTriChuan,'' as DiemDat,'0' as Dat,'0' as QuyetDinh,'' as TrangThai,''  as NguoiSua  " +
				  			 " from SANPHAM_TIEUCHIKIEMDINH c " +
				  			 " WHERE c.sanpham_fk="+SPID+" and c.Loai=0  " +
				  			 " UNION  " +
				  			 " SELECT C.TIEUCHI,C.PHEPTOAN,C.GIATRICHUAN,'' AS DIEMDAT,'0' AS DAT,'0' AS QUYETDINH,'' AS TRANGTHAI,''  AS NGUOISUA "+  
				  			 " FROM ERP_CONGDOANSANXUAT_TIEUCHIKIEMDINH_GIAY C   "+
				  			 " WHERE C.CONGDOANSANXUAT_FK="+CONGDOANSX+" AND ISNULL(C.DINHLUONG,0) =1 ";
		}
		else
		{
			INIT_DINHLUONG = " select a.TieuChi,a.PhepToan,a.GiaTriChuan, isnull( cast(a.DiemDat as nvarchar(10)),'') as diemdat , a.Dat,isnull(b.QuyetDinh,0)as QuyetDinh,isnull(a.TrangThai,'')as TrangThai,c.TEN + '--' +b.NgaySua  as NguoiSua from erp_yeucaukiemdinh_tieuchi a "+
				 " left join "+ 
				 " ( "+
				 "	select * "+
				 "	from Erp_YeuCauKiemDinh_TieuChi_Log "+
				 "	where NgayGio = (select max(NgayGio) from Erp_YeuCauKiemDinh_TieuChi_Log where YeuCauKiemDinh_FK ='"+Id+"' and DinhLuong=1) and YeuCauKiemDinh_FK='"+Id+"' and DinhLuong=1 "+
				 " ) "+
				 "  b on b.YeuCauKiemDinh_FK=a.yeucaukiemdinh_fk and a.tieuchi=b.TieuChi " +
				 "  left join NHANVIEN c on c.PK_SEQ=b.NguoiSua where a.YeuCauKiemDinh_FK='"+Id+"' and a.DinhLuong=1 and isnull(a.Trangthai,'') != '' ";
		}
	    System.out.println("1.Khoi tao tieu chi dinh luong: " + INIT_DINHLUONG);
	    
	    
	    PdfPTable root = new PdfPTable(1);
		root.setKeepTogether(false);
		root.setSplitLate(false);
		root.setWidthPercentage(100);
		root.setHorizontalAlignment(Element.ALIGN_LEFT);
		root.getDefaultCell().setBorder(0);
		float[] cr = { 100.0f };
		root.setWidths(cr);

		String[] th = new String[]{ "", "", ""};
		
		PdfPTable sanpham = new PdfPTable(th.length);
		sanpham.setSpacingBefore(0.5f*CONVERT);
		sanpham.setWidthPercentage(100);
		sanpham.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		float[] withsKM = { 5.0f*CONVERT, 15.0f*CONVERT, 6.0f*CONVERT};
		sanpham.setWidths(withsKM);
				
		PdfPCell cells = new PdfPCell();
		hd = new Paragraph("Tên chỉ tiêu " , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_CENTER);
		cells.addElement(hd);
		cells.setBorderWidth(1);	
		
		sanpham.addCell(cells);
		
		cells = new PdfPCell();
		hd = new Paragraph("Mức cho phép" , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_CENTER);
		cells.addElement(hd);
		cells.setBorderWidth(1);	
		
		sanpham.addCell(cells);
		
		cells = new PdfPCell();
		hd = new Paragraph("Kết quả " , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_CENTER);
		cells.addElement(hd);
		cells.setBorderWidth(1);	
		
		sanpham.addCell(cells);		
		
		//KIỂM TRA SỐ DÒNG ĐỊNH LƯỢNG
		ResultSet rsSP_dinluong_countdong= db.get(INIT_DINHLUONG);
		
		int stt = 0;
		try 
		{
			while (rsSP_dinluong_countdong.next())
			{				
				stt++;
			}
			rsSP_dinluong_countdong.close();
		} 
		catch (Exception e) {e.printStackTrace();}
		
		String tieuchi = "";
		String mucchophep = "";
		String ketqua = "";
		
		if(stt>0){
		ResultSet rsSP_dinhluong = db.get(INIT_DINHLUONG);
		
		
		cells = new PdfPCell();
		hd = new Paragraph("Định lượng" , new Font(bf, 12, Font.NORMAL));
		hd.setAlignment(Element.ALIGN_CENTER);
		cells.addElement(hd);
		cells.setHorizontalAlignment(Element.ALIGN_MIDDLE);
		cells.setBorderWidth(1);	
		cells.setRowspan(stt);
		sanpham.addCell(cells);	
		
		
			if(rsSP_dinhluong!=null){				
				while(rsSP_dinhluong.next()){
					stt++;
					tieuchi = rsSP_dinhluong.getString("TieuChi");
					mucchophep = rsSP_dinhluong.getString("TieuChi");
					ketqua = rsSP_dinhluong.getString("DiemDat")+ " " + rsSP_dinhluong.getString("trangthai");
									
					String[] arr = new String[] { mucchophep ,ketqua };
										
					for (int j = 0; j < 2; j++)
					{
						System.out.println(arr[j]);
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.NORMAL)));
						if (j == 0 ){ //ĐỊNH LƯỢNG
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							//cells.setPaddingLeft(-0.5f*CONVERT);
						}
						else{							
								
							cells.setHorizontalAlignment(Element.ALIGN_LEFT);
								
						}
						
						cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
						//cells.setBorder(0);
						cells.setBorderWidth(1);	
						sanpham.addCell(cells);
					}
					
				}
			}
			rsSP_dinhluong.close();
		}				
			//document.add(sanpham);
		
			String INIT_DINHTINH = 
				" select count(c.TieuChi) as sodong "+
				" from "+ 
				" ERP_YeuCauKiemDinh_TieuChi c  "+ 
				" where c.YeuCauKiemDinh_FK='"+Id+"'  and c.DinhTinh=1 ";
			
		   rsCheck = db.get(INIT_DINHTINH);
		   flag = false;
		   int numb=0; 
		 
			try 
			{
				if (rsCheck.next())
				{
					numb=rsCheck.getInt(1);
					if (numb==0){
						flag = true;
					}					
					rsCheck.close();
				}
			} 
			catch (Exception e) { e.printStackTrace();}
		 
			INIT_DINHTINH = " select count(*) as sodong " +
	  			" from  SanPham_TieuChiKiemDinh cd where cd.sanpham_fk = '" + SPID + "' and loai = '1' "; 
			rsCheck = db.get(INIT_DINHTINH);
			
			if (rsCheck != null)
			{
				try 
				{
					if (rsCheck.next())
					{
						numb=rsCheck.getInt(1);					
					}
					rsCheck.close();
				} 
				catch (Exception e) {e.printStackTrace();}
			}
			
			INIT_DINHTINH = " select count(*) as sodong " +
				" from  Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay cd where " +
				" cd.CongDoanSanXuat_FK = '" +CONGDOANSX + "' AND  ISNULL(DINHTINH,'0') ='1'  "; 
			rsCheck = db.get(INIT_DINHTINH);
	 
			try 
			{
				if (rsCheck.next())
				{
					numb=numb+ rsCheck.getInt(1);
					stt=stt+ rsCheck.getInt(1);
					rsCheck.close();
				}
			} 
			catch (Exception e) { e.printStackTrace();}
			
			if(flag)
			{
				INIT_DINHTINH =" SELECT c.TieuChi, '' as ghinhan  ,'0' as QuyetDinh,'' as TrangThai,''  as NguoiSua  " +
					 " from SANPHAM_TIEUCHIKIEMDINH c " +
					 " WHERE c.sanpham_fk="+SPID+" and c.Loai=1 "+
					 " Union all  SELECT c.TieuChi,'' as GhiNhan,0 as QuyetDinh,'' as TrangThai,''  as NguoiSua  " +
					 " FROM Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay c  " +
					 " WHERE c.CongDoanSanXuat_FK= "+CONGDOANSX+" and c.DinhTinh='1'";
			}
			else
			{
				INIT_DINHTINH =" select a.TieuChi,isnull(a.GhiNhan,'') as GhiNhan,isnull(b.QuyetDinh,0)as QuyetDinh " +
					 " ,isnull(a.TrangThai,'')as TrangThai,c.TEN + '--' +b.NgaySua  as NguoiSua " +
					 "	from erp_yeucaukiemdinh_tieuchi a "+
					 " left join "+ 
					 " ( "+
					 "	select * "+
					 "	from Erp_YeuCauKiemDinh_TieuChi_Log "+
					 "	where NgayGio = (select max(NgayGio) from Erp_YeuCauKiemDinh_TieuChi_Log where YeuCauKiemDinh_FK ='"+SPID+"') and YeuCauKiemDinh_FK='"+Id+"' "+
					 "	) "+
					 " b on b.YeuCauKiemDinh_FK=a.yeucaukiemdinh_fk and a.tieuchi=b.TieuChi and b.DinhTinh=a.DinhTinh  " +
					 " left join NHANVIEN c on c.PK_SEQ=b.NguoiSua " +
					 " where a.YeuCauKiemDinh_FK='"+Id+"' and a.DinhTinh=1 and isnull(a.Ghinhan,'') != '' ";
			}
			
			System.out.println("2. Định tính: " + INIT_DINHTINH);
			
			ResultSet rsSP_dinhtinh_countdong = db.get(INIT_DINHTINH);
			
			stt = 0;
			try 
			{
				while (rsSP_dinhtinh_countdong.next())
				{				
					stt++;
				}
				rsSP_dinhtinh_countdong.close();
			} 
			catch (Exception e) {e.printStackTrace();}
			
			
			if(stt>0){
			ResultSet rsSP_dinhtinh = db.get(INIT_DINHTINH);
							
			cells = new PdfPCell();
			hd = new Paragraph("Định tính " , new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_CENTER);
			cells.addElement(hd);
			cells.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			cells.setBorderWidth(1);	
			cells.setRowspan(stt);
			
			sanpham.addCell(cells);	
				
			tieuchi = "";
			mucchophep = "";
			ketqua = "";
				if(rsSP_dinhtinh!=null){				
					while(rsSP_dinhtinh.next()){
						stt++;
						tieuchi = rsSP_dinhtinh.getString("TieuChi");
						mucchophep = rsSP_dinhtinh.getString("TieuChi");
						ketqua =  rsSP_dinhtinh.getString("GhiNhan");
										
						String[] arr = new String[] {  mucchophep ,ketqua };
											
						for (int j = 0; j < 2; j++)
						{
							System.out.println(arr[j]);
							cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.NORMAL)));
							if (j == 0 ){ //ĐỊNH LƯỢNG
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
								//cells.setPaddingLeft(-0.5f*CONVERT);
							}
							else{							
									
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
									
							}
							
							cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
							//cells.setBorder(0);
							cells.setBorderWidth(1);	
							sanpham.addCell(cells);
						}
						
					}
				}
				rsSP_dinhtinh.close();
			}	
				document.add(sanpham);
				
				//KẾT LUẬN
				
				PdfPTable table8 =new PdfPTable(1);
				table8.setWidthPercentage(100);
				float[] withs8 = {100f};
				table8.setWidths(withs8);
							
				cell = new PdfPCell();	
				hd = new Paragraph("KẾT LUẬN ",font);
				hd.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(hd);
				cell.setBorder(0);	
				
				table8.addCell(cell);
				document.add(table8);
				
				//KẾT LUẬN
				
				PdfPTable table9 =new PdfPTable(1);
				table9.setWidthPercentage(100);
				float[] withs9 = {100f};
				table9.setWidths(withs9);
							
				cell = new PdfPCell();	
				hd = new Paragraph(KETLUAN, new Font(bf, 12, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_LEFT);
				cell.addElement(hd);
				cell.setBorder(0);	
				
				table9.addCell(cell);
				document.add(table9);
				
				//NGÀY THÁNG NĂM
				
				PdfPTable table10 =new PdfPTable(1);
				table10.setWidthPercentage(100);
				float[] withs10 = {100f};
				table10.setWidths(withs10);
							
				cell = new PdfPCell();	
				hd = new Paragraph("Ngày ... tháng ... năm ...", new Font(bf, 12, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_RIGHT);
				cell.addElement(hd);
				cell.setBorder(0);	
				
				table10.addCell(cell);
				document.add(table10);
				
				//NGƯỜI KIỂM TRA - TRƯỞNG PHÒNG QLCL
				
				PdfPTable table11 =new PdfPTable(2);
				table11.setWidthPercentage(100);
				float[] withs11 = {100f, 100f};
				table11.setWidths(withs11);
							
				cell = new PdfPCell();	
				hd = new Paragraph("NGƯỜI KIỂM TRA ", new Font(bf, 12, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(hd);
				cell.setBorder(0);	
				
				table11.addCell(cell);
				
				cell = new PdfPCell();	
				hd = new Paragraph("T.PHÒNG QLCL ", new Font(bf, 12, Font.NORMAL));
				hd.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(hd);
				cell.setBorder(0);	
				
				table11.addCell(cell);
				
				document.add(table11);
					
					
					
		
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	private String DinhDangTraphacoERP(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}
}
