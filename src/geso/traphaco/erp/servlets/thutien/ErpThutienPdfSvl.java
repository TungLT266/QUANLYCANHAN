package geso.traphaco.erp.servlets.thutien;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.doctien.DocTien;
import geso.traphaco.center.beans.cauhinhinhoadon.IErpCauHinhInHoaDon;
import geso.traphaco.center.beans.cauhinhinhoadon.imp.ErpCauHinhInHoaDon;
import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.lenhsanxuat.IErpChuyenkhoSX;
import geso.traphaco.erp.beans.thutien.IErpThutien;
import geso.traphaco.erp.beans.thutien.imp.ErpThutien;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.TabStop;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

public class ErpThutienPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpThutienPdfSvl() {
        super();
    }
   
    float CONVERT = 15.346457f;
    dbutils db = new dbutils();
    NumberFormat formatter = new DecimalFormat("#,###,###");
    NumberFormat formatterNT = new DecimalFormat("#,###,###.##");
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/*HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		//String userTen = (String) session.getAttribute("userTen");  	

		 if (userId.length() == 0)
		    	userId = request.getParameter("userId");
			   
		String id = request.getParameter("id");
		
		IErpThutien tthdBean = new ErpThutien(id);
	    tthdBean.setUserId(userId);*/

		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
		
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    String ctyId = (String)session.getAttribute("congtyId");
		
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    
		    String id = util.getId(querystring);  	
			IErpThutien tthdBean = new ErpThutien(id);
	        tthdBean.setCtyId(ctyId);
			tthdBean.setUserId(userId);			
			tthdBean.setnppdangnhap(util.getIdNhapp(userId));
			
			tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			tthdBean.setDoituongIddn(session.getAttribute("doituongId"));
			    
	        
	        String nextJSP;
	        tthdBean.initDisplay();
	        try {
	        	
	        	//tthdBean.init();
	        	response.setContentType("application/pdf");
	    		response.setHeader("Content-Disposition", " inline; filename=Phieuthutien.pdf");
	    		
	    		float CONVERT = 28.346457f;
	    		float PAGE_LEFT = 1.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 0.5f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
	    		Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
	    		
	    		ServletOutputStream outstream = response.getOutputStream();		
	    		
	    		this.CreatePTPdf(document, outstream,tthdBean, id);
	    		
	    		document.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienDisplay.jsp";
			      
		        session.setAttribute("nvgnId", tthdBean.getNvgnId());
		        
		        session.setAttribute("tthdBean", tthdBean);
		        response.sendRedirect(nextJSP);
			}
	        	
	        	
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
	
	private void createBody(Document document, BaseFont bf,  String nguoiNop, String doiTuong, String diaChi, String khoanThu,
			String soTien,String docTienRaChu, String chungtukemtheo) {
		try {
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			float[] withs1 = { 15f,90f };
			table1.setWidths(withs1);
			PdfPCell cell = null;
			Paragraph hd = null;

			//--1
			cell = new PdfPCell();
			hd = new Paragraph("Người nộp: ", new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			cell = new PdfPCell();
			hd = new Paragraph( nguoiNop, new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			//--2
			cell = new PdfPCell();
			hd = new Paragraph("Địa chỉ: " , new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph( diaChi, new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			//--3
			cell = new PdfPCell();
			hd = new Paragraph("Về khoản: ", new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			cell = new PdfPCell();
			hd = new Paragraph( khoanThu, new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			//--4
			cell = new PdfPCell();
			hd = new Paragraph("Số tiền: " , new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph( soTien, new Font(bf, 11, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			//--5
			cell = new PdfPCell();
			hd = new Paragraph("Bằng chữ :" , new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			cell = new PdfPCell();
			hd = new Paragraph(docTienRaChu, new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			cell = new PdfPCell();
			if(chungtukemtheo.equals("")){
				hd = new Paragraph("Kèm theo ...................... chứng từ gốc ", new Font(bf, 11, Font.NORMAL));
			}else{
				hd = new Paragraph("Kèm theo "+ chungtukemtheo+" chứng từ gốc ", new Font(bf, 11, Font.NORMAL));
			}
		
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);
			cell.setColspan(2);

			table1.addCell(cell);

			table1.setSpacingAfter(2.0f);//10
			
			document.add(table1);
			
			// Phần kế toán trưởng và đã nhận đủ tiền
			table1 = new PdfPTable(2);
			float[] withs2 = { 100f, 100f };
			table1.setWidthPercentage(100);
			
			cell = new PdfPCell();
			hd = new Paragraph("KẾ TOÁN TRƯỞNG " , new Font(bf, 11, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			cell = new PdfPCell();
			hd = new Paragraph("KẾ TOÁN THANH TOÁN ", new Font(bf, 11, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			table1.setSpacingAfter(40f);//30
			
			document.add(table1);
			
			hd = new Paragraph("Đã nhân đủ số tiền ( viết bằng chữ): ......................................................................................................." +
					".........................................\n .................................................................................." +
					".......................................................................................................................... ", new Font(bf, 11, Font.NORMAL));
			hd.setSpacingAfter(2.0f);
			document.add(hd);
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void createFooter(Document document, BaseFont bf) {
		try {
			PdfPTable table1 = new PdfPTable(2);
			table1.setWidthPercentage(100);
			float[] withs1 = { 100f, 100f};
			table1.setWidths(withs1);
			PdfPCell cell = null;
			Paragraph hd = null;

			//-1
			cell = new PdfPCell();
			hd = new Paragraph("", new Font(bf, 11, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph("Ngày ........ tháng ........ năm ........  ", new Font(bf, 11, Font.ITALIC));			
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);
			
			//--2
			cell = new PdfPCell();
			hd = new Paragraph("NGƯỜI NỘP ", new Font(bf, 11, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			/*hd.setPaddingTop(-0.1f);*/
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph("THỦ QUỸ ", new Font(bf, 11, Font.BOLD));			
			hd.setAlignment(Element.ALIGN_CENTER);
			//hd.setPaddingTop(-0.1f);
			cell.addElement(hd);
			cell.setPaddingTop(-0.1f*CONVERT);
			cell.setBorder(0);

			table1.addCell(cell);
			
			

			table1.setSpacingAfter(55f);//30
			document.add(table1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void createHeader(Document document, BaseFont bf, String id) {
		try {
			PdfPTable table1 = new PdfPTable(3);
			table1.setWidthPercentage(100);
			float[] withs1 = { 100f, 34f, 100f };
			table1.setWidths(withs1);
			PdfPCell cell = null;
			Paragraph hd = null;

			cell = new PdfPCell();
			hd = new Paragraph("CÔNG TY TNHH TRAPHACO HƯNG YÊN \n Tân Quang, Văn Lâm, Hưng Yên", new Font(bf, 11, Font.BOLD));
			hd.setAlignment(Element.ALIGN_LEFT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph("", new Font(bf, 11, Font.BOLD));
			hd.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			cell = new PdfPCell();
			hd = new Paragraph("Số phiếu  " + id, new Font(bf, 11, Font.BOLD));
			hd.setAlignment(Element.ALIGN_RIGHT);
			cell.addElement(hd);
			cell.setBorder(0);

			table1.addCell(cell);

			table1.setSpacingAfter(15f);
			document.add(table1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	private void CreatePTPdf(Document document, ServletOutputStream outstream, IErpThutien tthdBean, String Id) throws IOException
	{
		dbutils db = new dbutils();
		try{
			//LẤY THÔNG TIN
			
			String nguoiNop = "";
			String doiTuong = "";
			String diaChi = "";
			String khoanThu = "";
			String soTien = "";
			String ngayChungTu = "";
			String chungtukemtheo = "";
			long soTienN = 0;
			nguoiNop = tthdBean.getNguoinoptien();
			khoanThu = tthdBean.getNoidungtt();
			System.out.println("KhoanThu: " + khoanThu);
			
			/*ResultSet ndList = tthdBean.getNoidungRs(); 
			if(ndList != null){
				try {
					while (ndList.next()){
						if(ndList.getString("pk_seq").equals(tthdBean.getNoidungId())){

							//khoanThu = ndList.getString("ten");

							khoanThu = ndList.getString("ten");

							//if(khoanThu.trim().length() == 0) khoanThu = ndList.getString("ten");

						}
					}
					ndList.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}*/

			if(tthdBean.getNoidungId().equals("100002")){ //khác
				diaChi = tthdBean.getDiachi();
			}else if(tthdBean.getNoidungId().equals("100000")){//hóa đơn
				ResultSet rsnpp = tthdBean.getNppRs();
				if(rsnpp != null){
					try {
						while (rsnpp.next()){
							if(rsnpp.getString("PK_SEQ").equals(tthdBean.getNppId()) ||rsnpp.getString("PK_SEQ").equals(tthdBean.getNPPChinhanhId()) )
							{
								diaChi = rsnpp.getString("DIACHI");
							}
						}
						rsnpp.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
			else if(tthdBean.getNoidungId().equals("100001"))//khách hàng
			{
				String qu = "select pk_seq, DIACHI from NHAPHANPHOI where trangthai = '1' and PK_SEQ!=1 and PK_SEQ = "+ tthdBean.getNPPChinhanhId();
				System.out.println("npp: " + qu);
				ResultSet rsnpp = db.get(qu);
				
				if(rsnpp != null){
					try {
						
						System.out.println("npp:" + tthdBean.getNPPChinhanhId());
						while (rsnpp.next()){

							/*System.out.println("khach hang : " + rsnpp.getString("nppTen"));*/
							

							
							if(rsnpp.getString("PK_SEQ").equals(tthdBean.getNPPChinhanhId()) )
							{

							//System.out.println("khach hang : " + rsnpp.getString("nppTen"));
							if(rsnpp.getString("PK_SEQ").equals(tthdBean.getnppIdgoc()) ||rsnpp.getString("PK_SEQ").equals(tthdBean.getNPPChinhanhId()) )
							{

								diaChi = rsnpp.getString("DIACHI");
							
							}
							}
						}
						rsnpp.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				
				if (tthdBean.getnppIdgoc().length() > 0) {
					String q = "select *from [KhachHang_ThuTien_ERP]";
					ResultSet rs = db.get(q);
					System.out.println("goc:" + tthdBean.getnppIdgoc());
					if(rs != null){
						try {
							while(rs.next()){
								if(rs.getString("pk_seq").indexOf(tthdBean.getnppIdgoc()) >= 0){
									diaChi = rs.getString("DIACHI");
								}
							}
							rs.close();
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
			}else if(tthdBean.getNoidungId().equals("100003")) //thu hồi tạm ứng
			{
				//1. nhà cung cấp
				
				if(tthdBean.getDoiTuongTamUng().equals("1")){
					ResultSet rsncc = tthdBean.getNccList();
					if(rsncc != null){
						try {
							while(rsncc.next()){
								if(rsncc.getString("pk_seq").equals(tthdBean.getNccId())){
									diaChi = rsncc.getString("DIACHI");
								}
							}
							rsncc.close();
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}//2. nhân viên
				else if(tthdBean.getDoiTuongTamUng().equals("0")){
					ResultSet rsnv = db.get("select isnull(dvth.TEN,'') as DIACHI, nv.TEN , nv.PK_SEQ from ERP_NHANVIEN nv left join ERP_DONVITHUCHIEN dvth on nv.DVTH_FK = dvth.PK_SEQ where TRANGTHAI = 1 ");
					if(rsnv != null){
						try {
							while(rsnv.next()){
								if(rsnv.getString("PK_SEQ").equals(tthdBean.getNvtuId())){
									diaChi = rsnv.getString("DIACHI");
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
				
				
				
			}
			/*if(!tthdBean.getTienteId().equals("100000")){
           				
				soTien = formatterNT.format(Double.parseDouble(tthdBean.getThuduocNT().replaceAll(",",""))); 
          
			} 	                 
			else{*/
         
				soTien = formatter.format(Double.parseDouble(tthdBean.getThuduoc().replaceAll(",",""))) ;
										
			//}
			DocTien doctien = new DocTien();
			String docTienRaChu = doctien.docTien((long)Double.parseDouble(tthdBean.getThuduoc().replaceAll(",","")));
			
			
			document.setMargins(1.0f*CONVERT, 1.0f*CONVERT, 1.0f*CONVERT, 1.0f*CONVERT); // T, R, B, L
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			
			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			//GIẤY ĐỀ NGHỊ THANH TOÁN
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();		
			
			Paragraph hd = new Paragraph();
			
			//tính số phiếu
			
			
			
			// liên 1
			// tạo header
			createHeader(document, bf,tthdBean.getSochungtu().toUpperCase());
			
			hd.add(new Chunk(" PHIẾU THU", new Font(bf, 18, Font.BOLD)));
			hd.setAlignment(Element.ALIGN_CENTER);
			document.add(hd);
			
			hd = new Paragraph(); 
			hd.add(new Chunk(" Ngày:" + this.getDate(tthdBean.getNgaychungtu()), 
					new Font(bf, 11, Font.NORMAL)));
			hd.setAlignment(Element.ALIGN_CENTER);
			document.add(hd);
			
			
			createBody(document, bf, nguoiNop, doiTuong, diaChi, khoanThu, soTien,docTienRaChu, chungtukemtheo );
			createFooter(document, bf);

			
			hd = new Paragraph(".................................................................................." +
					"................................................................................................ ", 
					new Font(bf, 11, Font.NORMAL));
			hd.setAlignment(Element.ALIGN_CENTER);
			document.add(hd);
			
			
			// liên 2
			// tạo header
			createHeader(document, bf,tthdBean.getSochungtu().toUpperCase());
			hd = new Paragraph(); 
			hd.add(new Chunk(" PHIẾU THU", new Font(bf, 18, Font.BOLD)));
			hd.setAlignment(Element.ALIGN_CENTER);
			document.add(hd);
			
			hd = new Paragraph(); 
			hd.add(new Chunk(" Ngày:" + this.getDate(tthdBean.getNgaychungtu()), 
					new Font(bf, 11, Font.NORMAL)));
			hd.setAlignment(Element.ALIGN_CENTER);
			document.add(hd);
			
			
			createBody(document, bf, nguoiNop, doiTuong, diaChi, khoanThu, soTien,docTienRaChu, chungtukemtheo );
			createFooter(document, bf);
			
		}
		catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		} finally{
			db.shutDown();
		}
		
	}
	private String getDate(String date)
	{
		String arr[] = date.split("-");
		String nam = arr[0];
		String thang = arr[1];
		String ngay = arr[2];
		
		return ngay + "/" + thang + "/" + nam;
	}
	
	
	
	
}
