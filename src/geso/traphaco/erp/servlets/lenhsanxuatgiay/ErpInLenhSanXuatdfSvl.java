package geso.traphaco.erp.servlets.lenhsanxuatgiay;
 
import geso.traphaco.erp.db.sql.dbutils;
/*import geso.dms.distributor.util.Utility;*/
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuat;


import com.itextpdf.text.Image;
import java.io.IOException;
 
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
 

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import com.itextpdf.text.Document;
 
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class ErpInLenhSanXuatdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ErpInLenhSanXuatdfSvl() {
        super();
      
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
 
			IErpLenhsanxuat dhBean;
			dbutils db;
		
			Utility util=new Utility();
			String querystring=request.getQueryString();
			String dhid=util.getId(querystring);
			 
			userId=util.getUserId(querystring);
			dhBean=new ErpLenhsanxuat();
			
			
			dhBean.setId(dhid);
			 
			
			dhBean.setUserId(userId); //phai co UserId truoc khi Init
 
			db = new dbutils();
			
			String sql= " select distinct sp.DVKD_FK from ERP_LENHSANXUAT_SANPHAM lsxsp "+
						" inner join ERP_SANPHAM sp on sp.PK_SEQ=lsxsp.SANPHAM_FK where lsxsp.LENHSANXUAT_FK="+dhid;
			ResultSet rs=db.get(sql);
			try{
				if(rs.next()){
					dhBean.setDvkdId(rs.getString("DVKD_FK"));
				}
				rs.close();
			}catch(Exception er){
				
			}
			
			response.setContentType("application/pdf");	
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			
			if(dhBean.getDvkdId().trim().equals("100000"))
			{
				this.CreatePxk_Nhom(document, outstream, dhBean, db);
			}else if(dhBean.getDvkdId().trim().equals("100004"))
			{
				this.CreatePxk_Loi(document, outstream, dhBean, db);
			}else
			{
				
			}
			db.shutDown();
 
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
			IErpLenhsanxuat dhBean;
			 
			userId = cutil.antiSQLInspection(request.getParameter("userId"));
			String id=cutil.antiSQLInspection(request.getParameter("id"));
		
			dhBean=new ErpLenhsanxuat(id);
			dhBean.setUserId(userId); //phai co UserId truoc khi Init
			dhBean.init();
			 
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition"," inline; filename=HoaDonGTGT.pdf");
 
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			 
			 
		}

	}

	private void CreatePxk_Loi(Document document, ServletOutputStream outstream,IErpLenhsanxuat dhBean, dbutils db) throws IOException
	{
		try{		
			 
 
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 16, Font.BOLD);
			Font font8_bold = new Font(bf, 8, Font.BOLD);
			
			Font font_14 = new Font(bf, 14, Font.BOLD);
			
			Font font14_nomar= new Font(bf, 14, Font.NORMAL);
			
			Font font10_nomar=new Font(bf,10,Font.NORMAL);
			Font font8normal=new Font(bf,8,Font.NORMAL);
			
			Font font_BOLD_10 = new Font(bf, 10, Font.BOLD);
			
			//font8_bold.setColor(BaseColor.GREEN);
			 //KHAI BAO 1 BANG CO BAO NHIEU COT
			
			PdfPTable tableheader=new PdfPTable(3);
			tableheader.setWidthPercentage(100);//chieu dai cua báº£ng 
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {35.0f, 65.0f,35.0f};//SET DO RONG CAC COT
			tableheader.setWidths(withsheader); 
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/logoNewToYo.png");	
			hinhanh.setAbsolutePosition(190, 101);
			
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cellslogo = new PdfPCell(hinhanh);
			
		
			cellslogo.setPadding(10);
			cellslogo.setHorizontalAlignment(Element.ALIGN_CENTER);
 
			tableheader.addCell(cellslogo);
			 
			Paragraph pxk = new Paragraph("LỆNH SẢN XUẤT ", font);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			
			PdfPCell celltieude=new PdfPCell();
			celltieude.addElement(pxk);
			Paragraph dvbh = new Paragraph(" PRODUCTION ORDER ", font);
			dvbh.setSpacingAfter(3);
			dvbh.setAlignment(Element.ALIGN_CENTER);
			celltieude.addElement(dvbh);
		 
			tableheader.addCell(celltieude);
			
			PdfPCell cellinfo = new PdfPCell();
			
			cellinfo.addElement(new Paragraph("Loại  : Biểu mẫu",font10_nomar));
			cellinfo.addElement(new Paragraph("Mã số : BM-PXGN-001",font10_nomar));
			cellinfo.addElement(new Paragraph("Soát xét : 02",font10_nomar));
			cellinfo.addElement(new Paragraph("Điều chỉnh : 01",font10_nomar));
			cellinfo.addElement(new Paragraph("Trang : 1",font10_nomar));
			tableheader.addCell(cellinfo);
		 
			document.add(tableheader);
			
			PdfPTable table_benngoai =new PdfPTable(2);
			float[] with_benngoai = {50.0f,50.0f};//SET DO RONG CAC COT
			
			table_benngoai.setWidthPercentage(100);
			table_benngoai.setWidths(with_benngoai);
		
			PdfPCell cell_trai= new PdfPCell();
			 
			String sql= " SELECT DISTINCT isnull(SP.CHUNGLOAI_FK,0) as chungloai, SP.MA,SP.TEN,LSX.NGAYBATDAU,LSX.NGAYDUKIENHT ,KH.PK_SEQ,KH.MA as MAKH ,KH.TEN as tenkh, LSX.soPoKH "+
						" FROM ERP_LENHSANXUAT_GIAY LSX INNER JOIN  "+
						" ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.LENHSANXUAT_FK=LSX.PK_SEQ "+
						" LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=LSXSP.KHACHHANG_FK "+
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=LSXSP.SANPHAM_FK  WHERE LSX.PK_SEQ= "+dhBean.getId();
			
			ResultSet rs=db.get(sql);
			String maspsx="";
			String tenspsx="";
			String ngaysx="";
			String tenkh="";
			String chungloai="";
			String ngayketthuc="";
			String soPoKH= "";
			if(rs.next()){
				maspsx=rs.getString("MA");
				chungloai=rs.getString("chungloai");
				
				tenspsx=rs.getString("ten");
				ngaysx=rs.getString("NGAYBATDAU");
				tenkh=rs.getString("tenkh");
				ngayketthuc=rs.getString("NGAYDUKIENHT");
				soPoKH = rs.getString("soPoKH");
			}
			rs.close();
			//2013-11-01
			cell_trai.addElement(new Paragraph("            PHIẾU LỆNH SẢN XUẤT" ,font_14));
			
			
			cell_trai.addElement(new Paragraph("                    Ngày  "+ ngaysx.substring(8, 10) +" tháng "+ngaysx.substring(5, 7)+ " năm "+ngaysx.substring(0, 4)   ,font10_nomar));
			
			 for(int k=0;k<5 ;k ++){
				 cell_trai.addElement(new Paragraph("  " ,font_BOLD_10));
			 }
			 
			 
			cell_trai.addElement(new Paragraph("Số LSX  :  "+dhBean.getId()+"             Số Po(KH) : "+ soPoKH ,font10_nomar));
			cell_trai.addElement(new Paragraph("Khách hàng  : "+ tenkh  ,font10_nomar));
			cell_trai.setPaddingBottom(10);
			
			
			
			
		
			
			// thong tin các quy cach san pham san xuat
			
			PdfPTable table_spsx = new PdfPTable(5);  
			table_spsx.setWidthPercentage(100); 
			table_spsx.setHorizontalAlignment(Element.ALIGN_LEFT);
			//Ống giấy (Cone)
			String[] th ;
			PdfPCell[] cell;
			 int bien=15;
			 int i=0;
			int sott=1;
			if(chungloai.equals("100040"))
			{
					float[] withs1 = {4.0f,14.0f,10.0f,10.0f,10.0f}; 			
					table_spsx.setWidths(withs1); 
					  th = new String[]{"STT", "Quy cách thành phẩm",  "Code màu","Số lượng","Ngày giao"};
					 cell = new PdfPCell[5];
					for(  i=0; i < 5 ; i++)
					{
						cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
						cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
						 
						table_spsx.addCell(cell[i]);			
					}
					
								sql= "  SELECT  Sp.TEN+' x '+  CAST(SP.DAULON as nvarchar(20)) +SP.DVDL_DAULON+' x ' +CAST( SP.DAI as nvarchar(20))+SP.DVDL_DAI+' x ' +CAST( SP.DAUNHO as nvarchar(20)) +DVDL_DAUNHO  as quycach "+ 
								 "  FROM ERP_LENHSANXUAT_SANPHAM  A   "+ 
								 "  INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK  "+ 
								 "  LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ  " +
								 "  left join ERP_KHACHHANG kh on kh.pk_seq=a.khachhang_fk "+ 
								 "  WHERE LENHSANXUAT_FK="+ dhBean.getId();
								  rs=db.get(sql);
								  try{
									  if(rs.next()){
										  PdfPCell	cell1 = new PdfPCell(new Paragraph(rs.getString("quycach"), font8_bold));
										  cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
										  cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
										  cell1.setBorderWidthBottom(0);
										  cell1.setBorderWidthTop(0);
										  cell1.setColspan(5);
										  table_spsx.addCell(cell1);
										  bien=bien-1;
									  }
									  rs.close();
									  
								  }catch(Exception er){
									  er.printStackTrace();
							    }
			
					
					
							sql= "  SELECT isnull(kh.ma,'')+' , '+isnull(kh.ten,'')  as tenkh,SP.PK_SEQ,A.SOLUONG,SP.MAU ,SP.MA,SP.TEN,SP.QUYCACH,isnull(SP.DINHLUONG,0) as dinhluong,SP.TRONGLUONG   "+ 
								 "  ,SP.DUONGKINHTRONG ,QC.SOLUONG1,QC.SOLUONG2,QC.DVDL2_FK,QC.DVDL1_FK ,isnull( A.codemau,'') as codemau,isnull(SP.MAUIN,'')  as MauIn  "+ 
								 "  FROM ERP_LENHSANXUAT_SANPHAM  A   "+ 
								 "  INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK  "+ 
								 "  LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ  " +
								 "  left join ERP_KHACHHANG kh on kh.pk_seq=a.khachhang_fk "+ 
								 "  WHERE LENHSANXUAT_FK="+ dhBean.getId();
					  rs=db.get(sql);
					
					
					try{
						
					while (rs.next()){
						 th = new String[]{(sott)+"" ,rs.getString("MauIn")+" "+ rs.getString("mau"),rs.getString("codemau")  , 
								  rs.getString("SOLUONG"),ngayketthuc };
						 sott++;
						 for(  i=0; i <5 ; i++)
							{
							 
								cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
								cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
								cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell[i].setBorderWidthBottom(0);
								cell[i].setBorderWidthTop(0);
								table_spsx.addCell(cell[i]);			
							}
						 bien=bien-1;
						 
					}
					}catch(Exception er){
						er.printStackTrace();
					}
					
					 th = new String[]{ " " , " "," "  , 
							 " "," " };
					 sott++;
					 for(int k=0;k<bien ;k ++){
						 for(  i=0; i <5 ; i++)
						 {
							cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
							cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
							
							if(k==bien-1){
								cell[i].setBorderWidthTop(0);
							}else{
								cell[i].setBorderWidthBottom(0);
								cell[i].setBorderWidthTop(0);
							}
							
							table_spsx.addCell(cell[i]);			
						 }
					 }
					cell_trai.addElement(table_spsx);
			
			}else{
				 table_spsx = new PdfPTable(4); 
					table_spsx.setWidthPercentage(100); 
					table_spsx.setHorizontalAlignment(Element.ALIGN_LEFT);
				// những chủng loại khác 
				float[] withs1 = {4.0f,14.0f,10.0f ,10.0f}; 			
				table_spsx.setWidths(withs1); 
				  th = new String[]{"STT", "Quy cách thành phẩm",   "Số lượng","Ngày giao"};
				 cell = new PdfPCell[4];
				for(  i=0; i < 4 ; i++)
				{
					cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
					cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					 
					table_spsx.addCell(cell[i]);			
				}
			 
				
						sql= "  SELECT isnull(kh.ma,'')+' , '+isnull(kh.ten,'')  as tenkh,SP.PK_SEQ,A.SOLUONG,SP.MAU ,SP.MA,SP.TEN,SP.QUYCACH,isnull(SP.DINHLUONG,0) as dinhluong,SP.TRONGLUONG   "+ 
							 "  ,SP.DUONGKINHTRONG ,QC.SOLUONG1,QC.SOLUONG2,QC.DVDL2_FK,QC.DVDL1_FK ,isnull( A.codemau,'') as codemau  "+ 
							 "  FROM ERP_LENHSANXUAT_SANPHAM  A   "+ 
							 "  INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK  "+ 
							 "  LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ  " +
							 "  left join ERP_KHACHHANG kh on kh.pk_seq=a.khachhang_fk "+ 
							 "  WHERE LENHSANXUAT_FK="+ dhBean.getId();
				  rs=db.get(sql);
				
				
				try{
					
				while (rs.next()){
					 th = new String[]{(sott)+"" , rs.getString("quycach"),  
							  rs.getString("SOLUONG"),ngayketthuc };
					 sott++;
					 for(  i=0; i <4 ; i++)
						{
						 
							cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
							cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell[i].setBorderWidthBottom(0);
							cell[i].setBorderWidthTop(0);
							table_spsx.addCell(cell[i]);			
						}
					 bien=bien-1;
					 
				}
				}catch(Exception er){
					er.printStackTrace();
				}
				
				 th = new String[]{ " " , " "," "  , 
						 " "  };
				 sott++;
				 for(int k=0;k<bien ;k ++){
					 for(  i=0; i <4 ; i++)
					 {
						cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
						cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
						
						if(k==bien-1){
							cell[i].setBorderWidthTop(0);
						}else{
							cell[i].setBorderWidthBottom(0);
							cell[i].setBorderWidthTop(0);
						}
						
						table_spsx.addCell(cell[i]);			
					 }
				 }
				cell_trai.addElement(table_spsx);
			}
			
			cell_trai.addElement(new Paragraph("Yêu cầu chạy đúng kỹ thuật :" ,font_BOLD_10));
			 for(int k=0;k<7 ;k ++){
				 cell_trai.addElement(new Paragraph("  " ,font_BOLD_10));
			 }
			 cell_trai.addElement(new Paragraph("       Giám đốc sản xuất                  Trưởng ca" ,font_BOLD_10));
			 for(int k=0;k<7 ;k ++){
				 cell_trai.addElement(new Paragraph("  " ,font_BOLD_10));
			 }
			table_benngoai.addCell(cell_trai);
			
			PdfPCell cell_phai= new PdfPCell();
			
			
			//********************** CELL PHẢI *****************************
			
			
			cell_phai.addElement(new Paragraph("            PHIẾU KIỂM SOÁT NVL" ,font_14));
			
			cell_phai.addElement(new Paragraph("              "  ,font10_nomar));
			
			 for(int k=0;k<3 ;k ++){
				 cell_phai.addElement(new Paragraph("  " ,font_BOLD_10));
			 }
			 
			 
			 cell_phai.addElement(new Paragraph(tenspsx,font10_nomar));
				cell_phai.addElement(new Paragraph(" ",font10_nomar));
			cell_phai.addElement(new Paragraph(" ",font10_nomar));
			cell_phai.addElement(new Paragraph(" "  ,font10_nomar));
			cell_phai.setPaddingBottom(10);
			
			
			// 
			PdfPTable table_nvl = new PdfPTable(5);  
			table_nvl.setWidthPercentage(100); 
			table_nvl.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withs2 = {4.0f,14.0f,20.0f,10.0f,10.0f }; 			
			table_nvl.setWidths(withs2); 
			th = new String[]{"STT","Loại giấy", "Khổ", 
	        		"Số sợi","Ghi chú" };
			  cell = new PdfPCell[5];
			for(  i=0; i < 5; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				table_nvl.addCell(cell[i]);			
			}
			sql=" select a.SOLUONG,sp.MA,sp.TEN,sp.QUYCACH,sp.nguongoc,sp.dinhluong,sp.rong  from ERP_LENHSANXUAT_BOM_GIAY a "+
				" inner join ERP_SANPHAM sp on sp.PK_SEQ=VATTU_FK "+
				" where LENHSANXUAT_FK="+dhBean.getId()+" and loai=1";
			rs=db.get(sql);
			   bien=15;
			try{
				sott=1;
				while (rs.next()){
					bien=bien-1;
					
					 th = new String[]{(sott)+"",rs.getString("dinhluong"), rs.getString("rong") 
							  , "0", rs.getString("SOLUONG") };
					 sott++;
					 for(  i=0; i < 5 ; i++)
						{
							cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
							cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell[i].setBorderWidthBottom(0);
							cell[i].setBorderWidthTop(0);
							table_nvl.addCell(cell[i]);			
						}
				 
				}
				rs.close();
				
				}catch(Exception er){
					er.printStackTrace();
				}
				 th = new String[]{ " " , " "," "  , 
						 " "," " };
				 sott++;
				 for(int k=0;k<bien ;k ++){
					 for(  i=0; i <5 ; i++)
					 {
						cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
						cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
						
						if(k==bien-1){
							cell[i].setBorderWidthTop(0);
						}else{
							cell[i].setBorderWidthBottom(0);
							cell[i].setBorderWidthTop(0);
						}
						
						table_nvl.addCell(cell[i]);			
					 }
				 }
				cell_phai.addElement(table_nvl);
			
				cell_phai.addElement(new Paragraph("Trưởng máy phải báo cáo với Phụ trách khu vực khi phát hiện sai lỗi :" ,font_BOLD_10));
				 for(int k=0;k<6 ;k ++){
					 cell_phai.addElement(new Paragraph("   " ,font_BOLD_10));
				 }
				 cell_phai.addElement(new Paragraph("                    Ngày  "+ ngaysx.substring(8, 10) +" tháng "+ngaysx.substring(5, 7)+ " năm "+ngaysx.substring(0, 4)   ,font10_nomar));
				 cell_phai.addElement(new Paragraph("                           Người lập PO"   ,font10_nomar));

				 for(int k=0;k<7 ;k ++){
					 cell_phai.addElement(new Paragraph("  " ,font_BOLD_10));
				 }
			table_benngoai.addCell(cell_phai);
			 
			document.add(table_benngoai);
			
			
			document.setPageSize(PageSize.A4.rotate());
			document.newPage();
			document.add(new Paragraph(" P/O: "+dhBean.getId(),font10_nomar));
			
			document.add(new Paragraph(" Khách hàng: "+tenkh,font10_nomar));
			
			  if(chungloai.equals("100040")) {
			document.add(new Paragraph("                                           THEO DÕI THÀNH PHẨM ",font14_nomar));
			  }else{
					document.add(new Paragraph("                                           THEO DÕI BÁN THÀNH PHẨM ",font14_nomar));
			  }
			
			PdfPTable table_bantp = new PdfPTable(15);  
			table_bantp.setWidthPercentage(100); 
			table_bantp.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			float[] withs_btp = {8.0f,20.0f,14.0f,10.0f,10.0f,10.0f,10.0f,10.0f,10.0f,10.0f,10.0f,10.0f,10.0f,12.0f,12.0f }; 			
			table_bantp.setWidths(withs_btp); 
	       
	        
	        
	        PdfPCell cell_btp= new PdfPCell(new Paragraph("STT", font8_bold));
	        cell_btp.setFixedHeight(18f);
	        cell_btp.setRowspan(2);
	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
	        table_bantp.addCell(cell_btp);
	        
	        
	        cell_btp= new PdfPCell(new Paragraph("Quy cách màu", font8_bold));
	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        cell_btp.setFixedHeight(18f);
	        cell_btp.setRowspan(2);
	        table_bantp.addCell(cell_btp);
	        
	        cell_btp= new PdfPCell(new Paragraph("Số lượng yêu cầu", font8_bold));
	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        cell_btp.setFixedHeight(18f);
	        cell_btp.setRowspan(2);
	        table_bantp.addCell(cell_btp);
	        
	        
	        cell_btp= new PdfPCell(new Paragraph("Ngày sản xuất và số lượng thực hiện", font8_bold));

	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        cell_btp.setFixedHeight(18f);
	        cell_btp.setColspan(10);
	        table_bantp.addCell(cell_btp);
	        
	        
	        cell_btp= new PdfPCell(new Paragraph("Ghi chú", font8_bold));

	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        cell_btp.setFixedHeight(18f);
	        cell_btp.setColspan(2);
	        table_bantp.addCell(cell_btp);
	        
	        
	        for(int k=0;k<10;k++){
		        cell_btp= new PdfPCell(new Paragraph(" ", font8_bold));
		        cell_btp.setFixedHeight(18f);
		        table_bantp.addCell(cell_btp);
	        }
	        
	        cell_btp= new PdfPCell(new Paragraph("QCTP ", font8_bold));
	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
	        table_bantp.addCell(cell_btp);
			  
	        cell_btp= new PdfPCell(new Paragraph(" SL ", font8_bold));
	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        table_bantp.addCell(cell_btp);
	        cell = new PdfPCell[15];
	        th = new String[]{ " " , " "," "  , 
					 " "," "," " , " "," "  , 
					 " "," "," " , " "," "  , 
					 " "," " };
	        if(chungloai.equals("100040")) {
	        	//là công cone thì in 20 dong,không có bán thành phẩm
			 sott =20;
				 for(int k=0;k<sott ;k ++){
					 for(  i=0; i <15 ; i++)
					 {
						cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
						cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell[i].setFixedHeight(18f);
						cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
						 
						table_bantp.addCell(cell[i]);			
					 }
				 }
	        }else{
	        	
	        	
	        	 sott=10;	
	        	 for(int k=0;k<sott ;k ++){
					 for(  i=0; i <15 ; i++)
					 {
						cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
						cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell[i].setFixedHeight(18f);
						table_bantp.addCell(cell[i]);			
					 }
				 }
	        	 
	        	  document.add(table_bantp);
	        	  
	        	  
	        	 document.add(new Paragraph("                                           THEO DÕI   THÀNH PHẨM ",font14_nomar));
	        	 
	        	 table_bantp = new PdfPTable(15);  
	 			table_bantp.setWidthPercentage(100); 
	 			table_bantp.setHorizontalAlignment(Element.ALIGN_CENTER);
	 			
	 			 			
	 			table_bantp.setWidths(withs_btp); 
	 	       
	 	        
	 	        
	 	          cell_btp= new PdfPCell(new Paragraph("STT", font8_bold));
	 	         cell_btp.setFixedHeight(18f);
	 	        cell_btp.setRowspan(2);
	 	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 			
	 	        table_bantp.addCell(cell_btp);
	 	        
	 	        
	 	        cell_btp= new PdfPCell(new Paragraph("Quy cách màu", font8_bold));
	 	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 	       cell_btp.setFixedHeight(18f);
	 	        cell_btp.setRowspan(2);
	 	        table_bantp.addCell(cell_btp);
	 	        
	 	        cell_btp= new PdfPCell(new Paragraph("Số lượng yêu cầu", font8_bold));
	 	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 		    
	 	        cell_btp.setRowspan(2);
	 	        table_bantp.addCell(cell_btp);
	 	        
	 	        
	 	        cell_btp= new PdfPCell(new Paragraph("Ngày sản xuất và số lượng thực hiện", font8_bold));
	 	       cell_btp.setFixedHeight(18f);
	 	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 		    
	 	        cell_btp.setColspan(10);
	 	        table_bantp.addCell(cell_btp);
	 	        
	 	       cell_btp.setFixedHeight(18f);
	 	        cell_btp= new PdfPCell(new Paragraph("Ghi chú", font8_bold));

	 	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 	       cell_btp.setFixedHeight(18f);
	 	        cell_btp.setColspan(2);
	 	        table_bantp.addCell(cell_btp);
	 	        
	 	        
	 	        for(int k=0;k<10;k++){
	 		        cell_btp= new PdfPCell(new Paragraph(" ", font8_bold));
	 		       cell_btp.setFixedHeight(18f);
	 		        table_bantp.addCell(cell_btp);
	 	        }
	 	        
	 	        cell_btp= new PdfPCell(new Paragraph("QCTP ", font8_bold));
	 	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 		
	 	        table_bantp.addCell(cell_btp);
	 			  
	 	        cell_btp= new PdfPCell(new Paragraph(" SL ", font8_bold));
	 	        cell_btp.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	        cell_btp.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 	        table_bantp.addCell(cell_btp);
	 	        cell = new PdfPCell[15];
	 	        th = new String[]{ " " , " "," "  , 
	 					 " "," "," " , " "," "  , 
	 					 " "," "," " , " "," "  , 
	 					 " "," " };
	        	 sott=10;
	        	 
	        	 for(int k=0;k<sott ;k ++){
					 for(  i=0; i <15 ; i++)
					 {
						cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
						cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell[i].setFixedHeight(18f);
						table_bantp.addCell(cell[i]);			
					 }
				 }
	        }
	         
	        document.add(table_bantp);
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void CreatePxk_Nhom(Document document, ServletOutputStream outstream,IErpLenhsanxuat dhBean, dbutils db) throws IOException
	{
		try{		
			 
 
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
		 
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font8_bold = new Font(bf, 8, Font.BOLD);
			
			Font font10_nomar=new Font(bf,10,Font.NORMAL);
			Font font8normal=new Font(bf,8,Font.NORMAL);
			
			Font font_BOLD_10 = new Font(bf, 10, Font.BOLD);
			
			//font8_bold.setColor(BaseColor.GREEN);
			 //KHAI BAO 1 BANG CO BAO NHIEU COT
			
			PdfPTable tableheader=new PdfPTable(3);
			tableheader.setWidthPercentage(100);//chieu dai cua báº£ng 
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {35.0f, 65.0f,35.0f};//SET DO RONG CAC COT
			tableheader.setWidths(withsheader); 
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/images/logoNewToYo.png");	
			hinhanh.setAbsolutePosition(190, 101);
			
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cellslogo = new PdfPCell(hinhanh);
		
			cellslogo.setPadding(0);
			cellslogo.setHorizontalAlignment(Element.ALIGN_CENTER);
 
			tableheader.addCell(cellslogo);
			 
			Paragraph pxk = new Paragraph("LỆNH SẢN XUẤT ", font);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			
			PdfPCell celltieude=new PdfPCell();
			celltieude.addElement(pxk);
			Paragraph dvbh = new Paragraph(" PRODUCTION ORDER ", font);
			dvbh.setSpacingAfter(3);
			dvbh.setAlignment(Element.ALIGN_CENTER);
			celltieude.addElement(dvbh);
		 
			tableheader.addCell(celltieude);
			
			PdfPCell cellinfo = new PdfPCell();
			
			cellinfo.addElement(new Paragraph("Loại  : Biểu mẫu",font10_nomar));
			cellinfo.addElement(new Paragraph("Mã số : BM-PXGN-001",font10_nomar));
			cellinfo.addElement(new Paragraph("Soát xét : 02",font10_nomar));
			cellinfo.addElement(new Paragraph("Điều chỉnh : 01",font10_nomar));
			cellinfo.addElement(new Paragraph("Trang : 1",font10_nomar));
			tableheader.addCell(cellinfo);
		 
			document.add(tableheader);						
			
			PdfPTable table_info=new PdfPTable(1);
			float[] with3 = {50.0f};//SET DO RONG CAC COT
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();
		 
			String sql= " select distinct sp.MA,sp.TEN,lsx.NGAYBATDAU from ERP_LENHSANXUAT_GIAY lsx inner join "+ 
						" Erp_KichBanSanXuat_Giay kb on lsx.KICHBANSANXUAT_FK=kb.PK_SEQ "+
						" inner join ERP_SANPHAM sp on sp.MA=kb.MASANPHAM "+
						" where lsx.PK_SEQ= "+dhBean.getId();
			ResultSet rs=db.get(sql);
			String maspsx="";
			String tenspsx="";
			String ngaysx="";
			if(rs.next()){
				maspsx=rs.getString("MA");
				tenspsx=rs.getString("ten");
				ngaysx=rs.getString("NGAYBATDAU");
			}
			
			cell111.addElement(new Paragraph("Phiếu SX/P.O No :"+ dhBean.getId() ,font10_nomar));
			
			cell111.addElement(new Paragraph("Sản phẩm sản xuất  :  "+tenspsx,font10_nomar));
			cell111.addElement(new Paragraph("Mã sản phẩm  : "+ maspsx  ,font10_nomar));
			cell111.addElement(new Paragraph("Ngày sản xuất  : " +ngaysx ,font10_nomar));
			cell111.setPaddingBottom(10);
			
			
			cell111.addElement(new Paragraph("I/ THANH PHAM SAN XUAT/FINISHED GOODS: "   ,font_BOLD_10));
			table_info.addCell(cell111);
			 
			document.add(table_info);
			
			// thong tin các quy cach san pham san xuat
			
			PdfPTable table_spsx = new PdfPTable(10);  
			table_spsx.setWidthPercentage(100); 
			table_spsx.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			
			float[] withs1 = {4.0f,14.0f,10.0f,10.0f, 10.0f, 10.0f, 10.0f,10.0f,10.0f,10.0f}; 			
			table_spsx.setWidths(withs1); 
			String[] th = new String[]{"STT","Khách hàng", "Đơn đặt hàng","Quy cách/Size","Định lượng \n SUBS","ĐK Lõi  \n CORE ID","Số lượng cuộn \n QUANTITY / ROLL","Trọng lượng (Kg) \n WEIGHT KG","Tồn kho \n In Stock","Ngày giao hàng"};
			PdfPCell[] cell = new PdfPCell[10];
			for(int i=0; i < 10 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				 
				table_spsx.addCell(cell[i]);			
			}
					sql= "  SELECT isnull(kh.ma,'')+' , '+isnull(kh.ten,'')  as tenkh,SP.PK_SEQ,A.SOLUONG,SP.MA,SP.TEN,SP.QUYCACH,isnull(SP.DINHLUONG,0) as dinhluong,SP.TRONGLUONG   "+ 
						 "  ,SP.DUONGKINHTRONG ,QC.SOLUONG1,QC.SOLUONG2,QC.DVDL2_FK,QC.DVDL1_FK  "+ 
						 "  FROM ERP_LENHSANXUAT_SANPHAM  A   "+ 
						 "  INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK  "+ 
						 "  LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ  " +
						 "  left join ERP_KHACHHANG kh on kh.pk_seq=a.khachhang_fk "+ 
						 "  WHERE LENHSANXUAT_FK="+ dhBean.getId();
			  rs=db.get(sql);
			int i=0;
			int sott=1;
			try{
				
			while (rs.next()){
				 th = new String[]{(sott)+"",rs.getString("tenkh"), " ",rs.getString("QUYCACH"), rs.getString("dinhluong"), rs.getString("DUONGKINHTRONG") 
						 ,"",rs.getString("TRONGLUONG"),"0",""};
				 sott++;
				 for(  i=0; i < 10 ; i++)
					{
						cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
						cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
						 
						table_spsx.addCell(cell[i]);			
					}
			}
			}catch(Exception er){
				er.printStackTrace();
			}
			
			  									

			
			document.add(table_spsx);
	 
				Paragraph para = new Paragraph("II/ BÁN THÀNH PHẨM SẢN XUẤT/FINISHED GOODS:", font_BOLD_10);
				para.setSpacingAfter(10);
				document.add(para);
			 
			//Table Content
			PdfPTable table = new PdfPTable(5);  
			table.setWidthPercentage(100); 
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			
			float[] withs = {4.0f,14.0f,30.0f,10.0f, 8.0f}; 			
	        table.setWidths(withs); 
	        th = new String[]{"STT","Tên bán thành phẩm", "Quy cách","Chiều dài","Trọng lượng"};
			  cell = new PdfPCell[5];
			for(  i=0; i < 5 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell[i]);			
			}
			sql=" select a.SOLUONG,sp.MA,sp.TEN,sp.QUYCACH from ERP_LENHSANXUAT_BOM_GIAY a "+
				" inner join ERP_SANPHAM sp on sp.PK_SEQ=VATTU_FK "+
				" where LENHSANXUAT_FK="+dhBean.getId()+" and loai=3";
			
			rs=db.get(sql);
			try{
				sott=1;
				while (rs.next()){
					 th = new String[]{(sott)+"",rs.getString("TEN"), rs.getString("quycach"), "" 
							  ,rs.getString("SOLUONG") };
					 sott++;
					 for(  i=0; i < 5 ; i++)
						{
							cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
							cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
							table.addCell(cell[i]);			
						}
				}
				rs.close();
				
				}catch(Exception er){
					er.printStackTrace();
				}
			
			document.add(table);
			 
			
			para = new Paragraph("III/ TEN MAY GHEP  SAN XUAT :", font_BOLD_10);
			para.setSpacingAfter(10);
			document.add(para);
			
			// 
			PdfPTable table_nvl = new PdfPTable(7);  
			table_nvl.setWidthPercentage(100); 
			table_nvl.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withs2 = {4.0f,14.0f,20.0f,10.0f, 10.0f,10.0f,10.0f}; 			
			table_nvl.setWidths(withs2); 
	        th = new String[]{"STT \n No","Tên vật liệu \n Materials", "QUY CÁCH / SIZE", 
	        		"S.LƯỢNG / CUỘN \n QUANTITY / ROLL","THÔNG SỐ \n ĐỘ NHỚT","T.LƯỢNG / CUỘN \n QUANTITY / ROLL","NHÀ SX \n MANUFACTURER"};
			  cell = new PdfPCell[7];
			for(  i=0; i < 7; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				table_nvl.addCell(cell[i]);			
			}
			sql=" select a.SOLUONG,sp.MA,sp.TEN,sp.QUYCACH,sp.nguongoc from ERP_LENHSANXUAT_BOM_GIAY a "+
				" inner join ERP_SANPHAM sp on sp.PK_SEQ=VATTU_FK "+
				" where LENHSANXUAT_FK="+dhBean.getId()+" and loai=1";
			
			rs=db.get(sql);
			try{
				sott=1;
				while (rs.next()){
					 th = new String[]{(sott)+"",rs.getString("TEN"), rs.getString("quycach") 
							  ,rs.getString("SOLUONG"),"", rs.getString("SOLUONG"),rs.getString("nguongoc")};
					 sott++;
					 for(  i=0; i < 7 ; i++)
						{
							cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
							cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
							table_nvl.addCell(cell[i]);			
						}
				 
				}
				rs.close();
				
				}catch(Exception er){
					er.printStackTrace();
				}
			
			document.add(table_nvl);
			
			
			
			para = new Paragraph("IV/ TÊN MÁY CHUYỂN ĐỔI :", font_BOLD_10);
			para.setSpacingAfter(10);
			document.add(para);
			
			PdfPTable table_nhatdao = new PdfPTable(9);  
			table_nhatdao.setWidthPercentage(100); 
			table_nhatdao.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs3 = {4.0f,14.0f,20.0f,10.0f, 10.0f,10.0f,10.0f,10.0f,10.0f}; 			
			table_nhatdao.setWidths(withs3); 
	        th = new String[]{"STT \n No" , 
	        		"QUY CÁCH THÀNH PHẨM \n SEMI FINISHED GOODS SIZE", 
	        		"SỐ LẦN \n TIMES", 
	        		"SỐ DAO CẮT \n KNIFE CUT", 
	        		"DK LÕI   \n CORE ID", 
	        		"MỐI NỐI", 
	        		"BAO GÓI", 
	        		"QUY CÁCH PALLET \n PALLET SIZE", 
	        		"SỐ LƯỢNG \n CUÔN/PALLET"};
			  cell = new PdfPCell[9];
			for(  i=0; i < 9; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				table_nhatdao.addCell(cell[i]);			
			}
			for(int k=1;k<5;k++){
					th = new String[]{k+"" , 
			        		" ", 
			        		" ", 
			        		" ", 
			        		" ", 
			        		" ", 
			        		" ", 
			        		" ", 
			        		" "};
					  cell = new PdfPCell[9];
					for(  i=0; i < 9; i++)
					{
						cell[i] = new PdfPCell(new Paragraph(th[i], font8_bold));
						cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					 
						table_nhatdao.addCell(cell[i]);			
					}
			}
			
			document.add(table_nhatdao);
			
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	
}
