package geso.traphaco.erp.servlets.buttoantonghop;

import geso.traphaco.center.beans.doctien.DocTien;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.buttoantonghop.IErpButToanTongHop;
import geso.traphaco.erp.beans.buttoantonghop.imp.ErpButToanTongHop;
import geso.traphaco.erp.beans.hoadontaichinh.IErpHoaDonTaiChinh;
import geso.traphaco.erp.beans.hoadontaichinh.IErpHoaDon_SP;
import geso.traphaco.erp.beans.hoadontaichinh.imp.ErpHoaDonTaiChinh;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aspose.cells.BorderType;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Cell;



public class ErpButToanTongHopPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpButToanTongHopPdfSvl() {
        super();
    }

    float CONVERT = 28.346457f;  // =1cm
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		IErpButToanTongHop obj;
		String userId;
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();	    
	    String hdId = util.getId(querystring);
	    
	    userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new ErpButToanTongHop();
	    //obj.initdisplay(ddhId);
	    
		
		if(querystring.contains("print"))
		{		
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=ButToanTongHop.pdf");
			try {
				Document document = new Document();
				ServletOutputStream outstream = response.getOutputStream();	
				PdfWriter writer =	PdfWriter.getInstance(document, outstream);
				
				//HeaderFooter event = new HeaderFooter();
				 writer.setPageEvent(new HeaderAndFooter(hdId));
				//writer.setPageEvent(event);
				//writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
				
				this.CreateButToanTongHop(document,outstream, obj, hdId, writer );
				
				document.close();
			} catch (DocumentException e) {
				
				e.printStackTrace();
			}
			
			
			
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}
	
	private void CreateButToanTongHop(Document document, ServletOutputStream outstream, IErpButToanTongHop pxkBean, String hdId, PdfWriter writer) throws IOException
	{
		try{
			dbutils db = new dbutils();
			
			String query = 
			" SELECT TEN, DIACHI  FROM ERP_CONGTY  WHERE PK_SEQ = 100005";
			
			String TENCT = "";
			String DIACHICT = "";
			ResultSet rsbp = db.get(query);
			if(rsbp!=null){
				while(rsbp.next()){
					TENCT = rsbp.getString("TEN");
					DIACHICT = rsbp.getString("DIACHI");
				}	
			}
			rsbp.close();
						
			
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.0f*CONVERT, 1.0f*CONVERT, 1.5f*CONVERT, 2.0f*CONVERT); // L,R, T, B
			//PdfWriter.getInstance(document, outstream);
			  
			document.open();			
			document.newPage();
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			PdfPTable tableheader_ct =new PdfPTable(1);
			tableheader_ct.setWidthPercentage(100);			
			
			//TÊN CTY
			PdfPCell cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			Paragraph hd = new Paragraph( TENCT, new Font(bf, 10, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.7f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader_ct.addCell(cell);
			
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			//ĐỊA CHỈ CTY
			hd = new Paragraph(DIACHICT, new Font(bf, 10, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(0.7f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader_ct.addCell(cell);
			
			document.add(tableheader_ct);
			
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			hd = new Paragraph("PHIẾU KẾ TOÁN TỔNG HỢP ", new Font(bf, 16, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			//cell.setFixedHeight(1.8f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			//cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
		
			
			query = " SELECT NGAYBUTTOAN, DIENGIAI  FROM ERP_BUTTOANTONGHOP  WHERE PK_SEQ = "+hdId+"";				
			String NGAYBUTTOAN = "";
			String DIENGIAI = "";
			ResultSet tt = db.get(query);
			if(tt!=null){
				while(tt.next()){
					NGAYBUTTOAN = tt.getString("NGAYBUTTOAN");
					DIENGIAI = tt.getString("DIENGIAI");
				}	
			}
			tt.close();	
			
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			String [] ngayHD = NGAYBUTTOAN.split("-");
			hd = new Paragraph("Ngày "+ngayHD[2] + " Tháng  " + ngayHD[1] +  " Năm " + ngayHD[0], new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_CENTER);
			//cell.setFixedHeight(0.8f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			//cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			
			
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			hd = new Paragraph("Số chứng từ: "+hdId, new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_CENTER);
			//cell.setFixedHeight(0.8f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			//cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			
			
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			hd = new Paragraph("Diễn giải: "+DIENGIAI, new Font(bf, 12, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			//cell.setFixedHeight(0.8f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setPaddingTop(0.85f*CONVERT);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tableheader.addCell(cell);
			
			document.add(tableheader);
			
			String[] th = new String[]{ "SỐ TÀI KHOẢN", "TÊN TÀI KHOẢN", "NỢ", "CÓ","TÊN ĐỐI TƯỢNG", "TRUNG TÂM CHI PHÍ"};			
			
			PdfPTable nhanvien = new PdfPTable(th.length);
			nhanvien.setSpacingBefore(0.5f*CONVERT);
			nhanvien.setWidthPercentage(100);
			nhanvien.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			float[] withsKM = { 5.5f*CONVERT,13.0f*CONVERT, 6.0f*CONVERT, 6.0f*CONVERT,13.0f*CONVERT ,13.0f*CONVERT};
			nhanvien.setWidths(withsKM);
			
			PdfPCell cells = new PdfPCell();
			
			for (int j = 0; j < th.length; j++)
			{
				System.out.println(th[j]);
				cells = new PdfPCell(new Paragraph(th[j], new Font(bf, 10, Font.BOLD)));				
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);				
				cells.setBorderWidth(1);
				cells.setFixedHeight(0.8f * CONVERT);	
				nhanvien.addCell(cells);
			}
			
			query =
				" SELECT	BT.PK_SEQ AS BTID,ISNULL(CT.NO ,0) NO ,ISNULL(CT.CO ,0) CO,CT.TTCP_FK, CT.KHO_FK, CT.NGANHANG_FK, CT.NCC_FK, CT.TAISAN_FK, \n"+ 
				"			CT.KHACHHANG_FK, CT.NHANVIEN_FK,  CT.TAIKHOANKT_FK, \n"+
				"			CASE WHEN TTCP.PK_SEQ = CT.TTCP_FK THEN TTCP.DIENGIAI ELSE '' END AS TRUNGTAMCP, \n"+
				"			CASE WHEN CT.KHO_FK = KHO.PK_SEQ  THEN KHO.MA + ' - ' +KHO.TEN \n"+ 
				"			WHEN NH.PK_SEQ = CT.NGANHANG_FK THEN NH.MA + ' - ' + NH.TEN \n"+
				"			WHEN NCC.PK_SEQ = CT.NCC_FK THEN NCC.MA + ' - ' + NCC.TEN \n"+ 
				"			WHEN TS.pk_seq = CT.TAISAN_FK THEN TS.MA + ' - ' + TS.ten \n"+
				"			WHEN KH.PK_SEQ = CT.KHACHHANG_FK THEN  KH.MA + ' - ' +KH.TenXuatHD \n"+
				"			WHEN NV.PK_SEQ =  CT.NHANVIEN_FK THEN NV.MA  + ' - '+ NV.TEN \n"+
				"			ELSE '' END AS TENDOITUONG, TAIKHOAN.TENTAIKHOAN, TAIKHOAN.SOHIEUTAIKHOAN \n"+
				" FROM		ERP_BUTTOANTONGHOP BT  INNER JOIN ERP_BUTTOANTONGHOP_CHITIET CT ON CT.BUTTOANTONGHOP_FK=BT.PK_SEQ \n"+ 
				"			LEFT JOIN ERP_NGANHANG NH ON NH.PK_SEQ = CT.NGANHANG_FK \n"+
				"			LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = CT.NCC_FK \n"+
				"			LEFT JOIN ERP_TAISAN TS ON TS.pk_seq = CT.TAISAN_FK \n"+
				"			LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ = CT.KHACHHANG_FK \n"+
				"			LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ =  CT.NHANVIEN_FK \n"+
				"			LEFT JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = CT.KHO_FK \n"+
				"			INNER JOIN ERP_TAIKHOANKT TAIKHOAN ON TAIKHOAN.PK_SEQ = CT.TAIKHOANKT_FK \n"+
				"			LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = CT.TTCP_FK \n"+
				" WHERE 	BT.PK_SEQ = '"+hdId+"' AND BT.CONGTY_FK = 100005 \n"+  
				" ORDER BY CT.STT \n"; 
			
			System.out.println(query);
			
			String SOHIEUTAIKHOAN="";
			String TENTAIKHOAN = "";
			String NO = "";
			String CO = "";
			
			double SUM_NO = 0;
			double SUM_CO = 0;
			
			String TENDOITUONG = "";
			String TRUNGTAMCHIPHI = "";
			
			int stt =0;		
			int dong = 0;
			
			ResultSet rs = db.get(query);
			if(rs!=null){
				while(rs.next()){
					stt++;	
					SOHIEUTAIKHOAN = rs.getString("SOHIEUTAIKHOAN");
					TENTAIKHOAN  = rs.getString("TENTAIKHOAN");
					
					TENDOITUONG = rs.getString("TENDOITUONG");
					TRUNGTAMCHIPHI = rs.getString("TRUNGTAMCP");
					
					SUM_NO = SUM_NO + rs.getDouble("NO");
					SUM_CO = SUM_CO + rs.getDouble("CO");
					
					NO = rs.getString("NO"); 
					CO = rs.getString("CO"); 
					
					if(Double.parseDouble(NO) == 0 ) 
						NO = "";
					else
						NO = DinhDangCANFOCO(formatter1.format(Double.parseDouble(NO)));
					
					if(Double.parseDouble(CO) == 0 ) 
						CO = "";
					else
						CO = DinhDangCANFOCO(formatter1.format(Double.parseDouble(CO)));
					
					
					
					String[] arr = new String[] { SOHIEUTAIKHOAN, TENTAIKHOAN , NO ,CO, TENDOITUONG,TRUNGTAMCHIPHI };					
					
					for (int j = 0; j < th.length; j++)
					{
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 11, Font.NORMAL)));
						if (j <= 1 ){ //STT
							if(j == 0 )
								cells.setHorizontalAlignment(Element.ALIGN_CENTER);
							if(j == 1 )
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else{							
								if(j == 2 )//ĐƠN VỊ TÍNH
								{
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
								}
								else if( j==4||j==5)
								{
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
								}
								else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
									cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
								}
						}
						
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setBorderWidth(1);
						cells.setPaddingBottom(0.2f*CONVERT);
						nhanvien.addCell(cells);
					}
				}
			}
			rs.close();
			
			String[] arr = new String[] { "Cộng", "" , DinhDangCANFOCO(formatter1.format(SUM_NO)) ,DinhDangCANFOCO(formatter1.format(SUM_CO)), "","" };
			
			for (int j = 0; j < th.length; j++)
			{
				System.out.println(arr[j]);
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 11, Font.BOLD)));
				if (j <= 1 ){ //STT
					if(j == 0 )
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					if(j == 1 )
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else{							
					if(j == 2 )//ĐƠN VỊ TÍNH
					{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					else if( j==4)
					{
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					else{//SỐ LƯỢNG, ĐƠN GIÁ, ĐƠN ĐÃ GIẢM GIÁ, THÀNH TIỀN
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
				}
				
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setBorderWidth(1);
				cells.setFixedHeight(0.8f * CONVERT);	
				nhanvien.addCell(cells);
			}
			
			document.add(nhanvien);
			
			PdfPTable tablefooter =new PdfPTable(2);
			tablefooter.setSpacingBefore(0.2f*CONVERT);	
			float[] withs1 = {300f, 500f};
			tablefooter.setWidths(withs1);
			
			cell = new PdfPCell();	
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
			hd = new Paragraph("Ngày ...... tháng ........ năm ......" , new Font(bf, 12, Font.ITALIC));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(0.8f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cell.setColspan(2);
			cell.setBorder(0);
			cell.addElement(hd);
			
			tablefooter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph("Người lập phiếu" , new Font(bf, 12, Font.BOLDITALIC));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);			
			cell.setBorder(0);
			cell.addElement(hd);
			
			tablefooter.addCell(cell);
			
			cell = new PdfPCell();	
			hd = new Paragraph("Kế toán trưởng" , new Font(bf, 12, Font.BOLDITALIC));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.setFixedHeight(1.6f*CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			
			cell.setBorder(0);
			cell.addElement(hd);
			
			tablefooter.addCell(cell);			
			document.add(tablefooter);		
			//document.close();
			
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private String DinhDangCANFOCO(String sotien)
	{
		sotien = sotien.replaceAll("\\.", "_");
		sotien = sotien.replaceAll(",", "\\.");
		sotien = sotien.replaceAll("_", ",");
		
		return sotien;
	}
	
/*    class HeaderFooter extends PdfPageEventHelper 
    {
        Phrase[] header = new Phrase[2];
        int pagenumber;
 
        public void onStartPage(PdfWriter writer, Document document) 
        {
            pagenumber++;
            
            if(pagenumber>1)
            {
	            BaseFont bf;
				try
				{
					try {
						bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);						
						float[] withsKM = { 5.5f*CONVERT,13.0f*CONVERT, 6.0f*CONVERT, 6.0f*CONVERT,13.0f*CONVERT ,13.0f*CONVERT};
					
						String[] th = new String[]{ "SỐ TÀI KHOẢN", "TÊN TÀI KHOẢN", "NỢ", "CÓ","TÊN ĐỐI TƯỢNG", "TRUNG TÂM CHI PHÍ"};			
						PdfPTable table = new PdfPTable(th.length);
						table.setWidths(withsKM);
						table.setWidthPercentage(100);
						table.setHorizontalAlignment(Element.ALIGN_LEFT);					
				
						PdfPCell[] cell = new PdfPCell[th.length];
						
						for(int i= 0; i < th.length ; i++)
						{
							cell[i] = new PdfPCell(new Paragraph(th[i], new Font(bf, 10, Font.BOLD)));
							cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell[i].setFixedHeight(0.8f * CONVERT);
							table.addCell(cell[i]);							
						}
					document.add(table);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
					
					
				} catch (DocumentException e)
				{
				
					e.printStackTrace();
				}
            }
            
        }
 
      
        public void onEndPage(PdfWriter writer, Document document) {
          
        	BaseFont bf;
			try
			{
				try {
					bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
					
					String[] th = new String[]{"Trang :" +pagenumber };
					System.out.println("Vaoday"+pagenumber);
					PdfPCell[] cell = new PdfPCell[th.length];
					PdfPTable table = new PdfPTable(th.length);
					
					for(int i= 0; i < th.length ; i++)
					{
						cell[i] = new PdfPCell(new Paragraph(th[i], new Font(bf, 10, Font.BOLD)));						
						cell[i].setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell[i].setVerticalAlignment(Element.ALIGN_BOTTOM);
						cell[i].setBorder(0);
					
						table.addCell(cell[i]);			
					}
					document.add(table);
				} catch (IOException e) {
				
					e.printStackTrace();
				}
				
				
			} catch (DocumentException e )
			{
				e.printStackTrace();
			}
        	
        }
    }*/
	
	
	public class HeaderAndFooter extends PdfPageEventHelper {

	    private String name = "";


	    protected Phrase footer;
	    protected Phrase header;
	    BaseFont bf;

	    /*
	     * Font for header and footer part.
	     *//*
	    private static Font headerFont = new Font(bf, 10, Font.BOLD);

	    private static Font footerFont = new Font(Font.TIMES_ROMAN, 9,
	            Font.BOLD,Color.blue);*/


	    /*
	     * constructor
	     */
	    public HeaderAndFooter(String name) {
	        super();

	        this.name = name;


	        header = new Phrase("***** Header *****");
	        footer = new Phrase("**** Footer ****");
	    }


	    @Override
	    public void onEndPage(PdfWriter writer, Document document) {

	        PdfContentByte cb = writer.getDirectContent();
	        
	        try {
				bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			} catch (DocumentException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
	        
	        //header content
	        String headerContent = "Số chứng từ: " +name;

	        //header content
	        String footerContent = headerContent;
	        /*
	         * Header
	         */ 
	        if(writer.getPageNumber()>1){
		        ColumnText.showTextAligned(cb, Element.ALIGN_MIDDLE, new Phrase(headerContent,new Font(bf, 12, Font.NORMAL)), 
		                document.leftMargin() + 12.5f*CONVERT, document.top()+15, 0);				
		        
	        }

	        /*
	         * Foooter
	         */
	        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase("Trang: "+String.format(" %d ", 
	                writer.getPageNumber()),new Font(bf, 10, Font.BOLD)), 
	                document.right() - 2 , document.bottom() - 20, 0);

	    }

	}
}



