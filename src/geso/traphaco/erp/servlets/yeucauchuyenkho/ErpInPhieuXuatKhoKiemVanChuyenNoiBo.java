package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.db.sql.dbutils;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuat.IYeucau;
import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapkho;
import geso.traphaco.erp.beans.nhapkho.giay.imp.ErpNhapkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkho;
import geso.traphaco.erp.servlets.denghimuahang.ErpDenghimuahangUpdateSvl;
import java.util.Hashtable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class ErpInPhieuXuatKhoKiemVanChuyenNoiBo extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public ErpInPhieuXuatKhoKiemVanChuyenNoiBo()
	{
		super();
	}
	float CONVERT = 28.346457f; // = 1cm
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		// String userTen = (String) session.getAttribute("userTen");

		if (userId.length() == 0)
			userId = request.getParameter("userId");
		
		Utility util=new Utility();

		String querystring = request.getQueryString();
	    String id = util.getId(querystring); 

		IErpChuyenkho pxkBean = new ErpChuyenkho(id);
		
		 String isnhanhang = request.getParameter("isnhanHang");
			if(isnhanhang == null)
				isnhanhang = "0";
			pxkBean.setIsnhanHang(isnhanhang);
			
		
		pxkBean.setUserId(userId);
		pxkBean.init();
		dbutils db = new dbutils();
		
			//pxkBean.initPdf();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=PhieuXuatKhoKiemVanChuyenDongBo.pdf");

			float CONVERT = 28.346457f;// 1 cm
//			float PAGE_LEFT = 2.0f * CONVERT, PAGE_RIGHT = 1.5f * CONVERT, PAGE_TOP = 0.5f * CONVERT, PAGE_BOTTOM = 0.0f * CONVERT;
			Document document = new Document();
			ServletOutputStream outstream;

	
			//this.CreatePxk(document, outstream, pxkBean);
			try{
				String query = "select PK_SEQ, MA from ERP_NOIDUNGNHAP where pk_seq = '"+ pxkBean.getNdxId() +"'";
				
				ResultSet rs = db.get(query);
				
				try {
					if(rs.next()){
						
						String ma = rs.getString("MA");
						
						if (ma.equals("XK17")){
							outstream = response.getOutputStream();
							this.CreatePxk(document, outstream, pxkBean);
						}
						else{
							pxkBean.setMsg("Chỉ in khi nội dung xuất là XK17 - Xuất chuyển từ HO-> CN");
							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoDisplay.jsp";
							//pxkBean.init(); 
							session.setAttribute("khochuyenIds", pxkBean.getKhoXuatId());
					        session.setAttribute("ckBean", pxkBean);
					        response.sendRedirect(nextJSP);
						}
					}
					
				} catch (Exception e) {
				
					e.printStackTrace();
					
				}
				
				String query1 ="UPDATE ERP_CHUYENKHONPP SET Dain = 1 where PK_SEQ = "+id;
				if(!db.update(query1))
				{
					
				}
			}
			catch(Exception e) {}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, IErpChuyenkho pxkBean) throws IOException
	{
		try
		{
		
		
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		    document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.5f*CONVERT, 1.0f*CONVERT, 1.8f*CONVERT, 1.0f*CONVERT); // L,R,T,B
			PdfWriter.getInstance(document, outstream);

			document.open() ;
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.6f * CONVERT);
			cell.setPaddingLeft(12.0f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayck = pxkBean.getNgayyeucau().split("-");
			Paragraph pxk = new Paragraph(	ngayck[2] + "                        " + ngayck[1] +  "                        " + ngayck[0] , new Font(bf, 9, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);

			
			// Thông tin NCC
			PdfPTable table1 =new PdfPTable(1);
			table1.setWidthPercentage(100);
			
			// TÊN TỔ CHỨC,CÁ NHÂN
			PdfPCell cell7 = new PdfPCell();			
			pxk = new Paragraph(""  , new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);			
			cell7.setPaddingTop(-0.1f*CONVERT);
			cell7.addElement(pxk);
			cell7.setBorder(0);						
			table1.addCell(cell7);				
						
			// ĐỊA CHỈ
			PdfPCell cell9 = new PdfPCell();			
			pxk = new Paragraph(" "   , new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell9.addElement(pxk);
			cell9.setBorder(0);						
			table1.addCell(cell9);	
			
			//MÃ SỐ THUẾ
			PdfPCell cell11 = new PdfPCell();
			pxk = new Paragraph(" " , new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell11.setPaddingLeft(1.5f*CONVERT);
			cell11.addElement(pxk);
			cell11.setBorder(0);						
			table1.addCell(cell11);	
			
			document.add(table1);
			
			// CĂN CỨ LỆNH ĐIỀU ĐỘNG
			String chuoingay = "                             ";
			if(pxkBean.getNgayyeucau().trim().length() > 0)
			{
			  String[] ngaydd = pxkBean.getNgaydieudong().split("-");
			  chuoingay =ngayck[2] + "              " + ngayck[1] + "             " + ngayck[0];
			}
			
			System.out.println("sdfdsfdsfsdfs"+ngayck);
			
			PdfPTable tableLDD = new PdfPTable(4);
			tableLDD.setWidthPercentage(100);
			float[] withs =  { 8.0f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableLDD.setWidths(withs);
			
			// LỆNH DD SỐ
			PdfPCell cell_so = new PdfPCell();			
			pxk = new Paragraph(" "+ pxkBean.getLenhdieudong()  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_so.setPaddingTop(0.5f*CONVERT);
			cell_so.setPaddingLeft(4.5f*CONVERT);
			cell_so.addElement(pxk);
			cell_so.setBorder(0);	
			tableLDD.addCell(cell_so);
			
			// NGÀY DD 
			PdfPCell cell_ngaydd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoingay  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngaydd.setPaddingTop(0.5f*CONVERT);
			cell_ngaydd.setPaddingLeft(0.2f * CONVERT);
			cell_ngaydd.addElement(pxk);
			cell_ngaydd.setBorder(0);
			tableLDD.addCell(cell_ngaydd);
			
			// LỆNH DD CỦA
			PdfPCell cell_lddcua = new PdfPCell();			
			pxk = new Paragraph(" "+ pxkBean.getNguoidieudong()  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_lddcua.setPaddingTop(0.5f*CONVERT);
			cell_lddcua.setPaddingLeft(0.8f*CONVERT);
			cell_lddcua.addElement(pxk);
			cell_lddcua.setBorder(0);
			tableLDD.addCell(cell_lddcua);
			
			// LỆNH DD VỀ VIỆC
			PdfPCell cell_veviec = new PdfPCell();			
			/*pxk = new Paragraph(" "+ pxkBean.getLydoyeucau()  , new Font(bf, 9, Font.NORMAL));*/
			pxk = new Paragraph(" "  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_veviec.setPaddingTop(0.5f*CONVERT);
			cell_veviec.setPaddingLeft(2.4f*CONVERT);
			cell_veviec.addElement(pxk);
			cell_veviec.setBorder(0);
			tableLDD.addCell(cell_veviec);
			
			document.add(tableLDD);				
			
			
			// HỌ TÊN NGƯỜI VẬN CHUYỂN
			PdfPTable tableVC = new PdfPTable(4);
			tableVC.setWidthPercentage(100);
			float[] withs1 =  { 9.5f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableVC.setWidths(withs1);
			
			// NGƯỜI VC
			PdfPCell cell_ten = new PdfPCell();			
			pxk = new Paragraph(" "+ pxkBean.getNguoivanchuyen()  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ten.setPaddingTop(0.1f*CONVERT);
			cell_ten.setPaddingLeft(4.5f*CONVERT);
			cell_ten.addElement(pxk);
			cell_ten.setBorder(0);
			tableVC.addCell(cell_ten);
			
			// SỐ HD
			PdfPCell cell_sohd = new PdfPCell();			
			pxk = new Paragraph(" "+ pxkBean.getHopdong()  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_sohd.setPaddingTop(0.1f*CONVERT);
			cell_sohd.setPaddingLeft(2.5f*CONVERT);
			cell_sohd.addElement(pxk);
			cell_sohd.setBorder(0);	
			tableVC.addCell(cell_sohd);
			
			// NGÀY HỢP ĐỒNG
			String chuoi = "";
			/*if(pxkBean..trim().length() > 0)
			{
				String[] ngayhd = ngayhopdong.split("-");
				chuoi = ngayhd[2] + "/" + ngayhd[1] + "/" + ngayhd[0];
			}*/
			
			PdfPCell cell_ngayhd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoi  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngayhd.setPaddingTop(0.1f*CONVERT);
			cell_ngayhd.setPaddingLeft(2.8f*CONVERT);
			cell_ngayhd.addElement(pxk);
			cell_ngayhd.setBorder(0);	
			tableVC.addCell(cell_ngayhd);
			
			// PHƯƠNG TIỆN VC
			PdfPCell cell_pt = new PdfPCell();			
			pxk = new Paragraph(" "+ pxkBean.getPhuongtien()  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_pt.setPaddingTop(0.1f*CONVERT);
			cell_pt.setPaddingLeft(4.0f*CONVERT);
			cell_pt.addElement(pxk);
			cell_pt.setBorder(0);	
			tableVC.addCell(cell_pt);
			
			document.add(tableVC);							
								
			
			// XUẤT TẠI KHO  + NHẬP TẠI KH
			
			
			PdfPTable tableKHO = new PdfPTable(2);
			tableKHO.setWidthPercentage(100);			
			float[] withs3 =  { 13.0f*CONVERT, 13.0f*CONVERT };
			tableKHO.setWidths(withs3);
			
			
			// XUẤT TẠI KHO = 
			ResultSet rskx = pxkBean.getKhoXuatRs();
			String khoxuatStr = "";
			try {
				if(rskx!= null){
					while(rskx.next()){
						if(rskx.getString("PK_SEQ").equals(pxkBean.getKhoXuatId())){
							khoxuatStr = rskx.getString("TEN");
						}
					}
					
					rskx.close();
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			PdfPCell cell_khoxuat = new PdfPCell();	
			System.out.println("--kho xuat: " + khoxuatStr);
			pxk = new Paragraph(" "+ khoxuatStr  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			/*cell_khoxuat.setPaddingTop(0.3f*CONVERT);*/
			cell_khoxuat.setPaddingTop(-0.2f*CONVERT);
			cell_khoxuat.setPaddingLeft(2.5f*CONVERT);
			cell_khoxuat.addElement(pxk);
			cell_khoxuat.setBorder(0);
			tableKHO.addCell(cell_khoxuat);
			
			
			// NHẬP TẠI KHO	
			dbutils db=new dbutils();
			String sqlkhonhan=" select  isnull (XUATTAIKHO,'') as XUATTAIKHO from nhaphanphoi where pk_seq= "+ pxkBean.getDoituongNhanId();
			System.out.println(" kho nhan : "+sqlkhonhan);
			ResultSet rskn = db.get(sqlkhonhan);
			String khonhapStr = "";
			try {
				if(rskn!= null){
					while(rskn.next()){
						
							khonhapStr = rskn.getString("XUATTAIKHO");
						
					}
					
					rskn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			PdfPCell cell_khonhap = new PdfPCell();
			System.out.println("Kho nhap: " + khonhapStr);
			pxk = new Paragraph(" " + khonhapStr , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			/*cell_khonhap.setPaddingTop(0.3f*CONVERT);*/
			cell_khonhap.setPaddingTop(-0.2f*CONVERT);
			cell_khonhap.setPaddingLeft(2.3f*CONVERT);
			cell_khonhap.addElement(pxk);
			cell_khonhap.setBorder(0);	
			tableKHO.addCell(cell_khonhap);
			
			document.add(tableKHO);			
					
			
			
			// Table sản phẩm
			//String[] th = new String[]{ "STT", "Tên hàng hóa,dịch vụ", "Số kiểm soát", "Hạn dùng","Số kiện" ,"ĐVT", "Thực xuất", "Thực nhận","Đơn giá", "Thành tiền"};
			String[] th = new String[]{ " "," ", " ", " ", " "," "," " ," "," ", " "," "};
			
			PdfPTable table = new PdfPTable(th.length);
			table.setWidthPercentage(100);
			table.setSpacingBefore(2.0f*CONVERT);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);

			
			
			float[] withssp =  { 1.5f*CONVERT,1.5f*CONVERT ,5.7f*CONVERT, 2.0f*CONVERT, 1.7f*CONVERT,2f*CONVERT , 1.8f*CONVERT, 2.5f*CONVERT,  2.0f*CONVERT, 2.0f*CONVERT, 3.0f*CONVERT  };
			table.setWidths(withssp);
			
			PdfPCell cells = new PdfPCell();
			
	
			

			/*List<geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau> spList = pxkBean.getSpList();
			int stt = 1;
			Hashtable<String, String> sanpham_soluong = pxkBean.getSanpham_Soluong();
			
			for(int i = 0; i< spList.size(); i++)
			{
				geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau sp = spList.get(i);

				double soluongNhan = 0;
				String chuoingaysd = "";
				String solo = "";
				
				Enumeration<String> keys = sanpham_soluong.keys();
				String[] arr;
				while (keys.hasMoreElements()) {
					String key = keys.nextElement();

					if (key.startsWith(sp.getMa())) {
						arr = key.split("__");
						String temID = sp.getMa() + "__ ";

						String soluongXUAT = sanpham_soluong.get(key);
				
						soluongNhan= Double.parseDouble(soluongXUAT);
				
						solo = arr[2];
						if(!arr[3].equals("null")){
							String[] hsd = arr[3].split("-");
							chuoingaysd = hsd[2] + "/" + hsd[1] + "/" + hsd[0] ;
						
						}else{
							chuoingaysd = "";
					
						}
						
						// quy doi ra kien theo yeu cau THU_2017-01-23 , pk_seq kien= 100018
						String quydoikien="";
						String qr_kien=" select round( ("+soluongNhan +"* SOLUONG2 )/SOLUONG1 ,2)  as quydoikien "+
										" \n from QUYCACH where SANPHAM_FK ="+sp.getId()+" and DVDL1_FK="+ sp.getDvtinh_id()+" AND DVDL2_FK =100018";
						
						System.out.println(" doi quy cach : "+qr_kien );
						ResultSet rs_kien= db.get(qr_kien);
						if(rs_kien!=null){
							try {
								while(rs_kien.next()){
									quydoikien= formatter.format( rs_kien.getDouble("quydoikien"));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
						if(quydoikien ==null || quydoikien.equals("0")){
							quydoikien="";
						}
						

						String[] arrDuLieu = new String[] { Integer.toString(stt),sp.getMa(),sp.getTen(),solo, chuoingaysd, quydoikien,
						sp.getDonViTinh(),formatter.format(soluongNhan), "","", "" };


						for (int j = 0; j < arrDuLieu.length; j++)
						{
							cells = new PdfPCell(new Paragraph(arrDuLieu[j], new Font(bf, 9, Font.NORMAL)));
							
							if(j==0){
								cells.setHorizontalAlignment(Element.ALIGN_CENTER);
							}
							else
							if (j <= 4 ){
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							}
							
							else{
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(2.0f);	
							cells.setFixedHeight(0.6f*CONVERT);
							cells.setBorder(0);
									
							table.addCell(cells);
						}
				
						stt++;
				
					}
				}
			}*/
			
			int stt = 1;
			String query= " select  b.DVDL_FK  as  DVDL_ID, b.pk_seq as spId, isnull( b.MA,b.ma) as spMa, b.Ten as spTen, \n" + 
						  " 		DONVIDOLUONG.DIENGIAI AS DVT, isnull( a.solo,'') solo   , isnull( a.ngayhethan,'') ngayhethan, \n" + 
						  " 		SUM(ISNULL(a.soluong, 0)) as SOLUONGYEUCAU , SUM(a.PK_SEQ)/ COUNT (a.SANPHAM_FK) as stt  \n" + 
						  " from	ERP_CHUYENKHO_SANPHAM_CHITIET a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ   \n" + 
						  " 		inner join erp_chuyenkho ycck on ycck.pk_seq= a.CHUYENKHO_FK    \n" + 
						  " 		LEFT JOIN DONVIDOLUONG ON DONVIDOLUONG.PK_SEQ = b.DVDL_FK    \n" + 
						  " where	a.CHUYENKHO_FK = '"+pxkBean.getId() +"'    \n" + 
						  " group by b.DVDL_FK,b.pk_seq, b.MA,b.ma, b.Ten,DONVIDOLUONG.DIENGIAI,a.solo, a.ngayhethan \n" + 
						  " ORDER BY  SUM(a.PK_SEQ)/ COUNT (a.SANPHAM_FK) asc"		;
			System.out.println(" lay du lieu xuat chuyen noi bo: "+ query);
			ResultSet rsSp= db.get(query);
			if( rsSp!= null){
				try {
					while(rsSp.next()){
						String spid=rsSp.getString("spId");
						String spMa = rsSp.getString("spMa");
						String spTen = rsSp.getString("spTen");
						String spSolo = rsSp.getString("solo");
						String spNgayhethan = rsSp.getString("ngayhethan");
						String spDvt = rsSp.getString("DVT");
						String spDvtId = rsSp.getString("DVDL_ID");
						double soluongXUAT=rsSp.getDouble("SOLUONGYEUCAU") ;				
						String chuoingaysd="";
						if(!spNgayhethan.equals("null")){
							String[] hsd =spNgayhethan.split("-");
							chuoingaysd = hsd[2] + "/" + hsd[1] + "/" + hsd[0] ;
						
						}else{
							chuoingaysd = "";
					
						}
						
						// quy doi ra kien theo yeu cau THU_2017-01-23 , pk_seq kien= 100018
						String quydoikien="";
						String qr_kien=" select round( ("+soluongXUAT +"* SOLUONG2 )/SOLUONG1 ,2)  as quydoikien "+
										" \n from QUYCACH where SANPHAM_FK ="+spid+" and DVDL1_FK="+ spDvtId+" AND DVDL2_FK =100018";
						
						System.out.println(" doi quy cach : "+qr_kien );
						ResultSet rs_kien= db.get(qr_kien);
						if(rs_kien!=null){
							try {
								while(rs_kien.next()){
									quydoikien= formatter.format( rs_kien.getDouble("quydoikien"));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
						if(quydoikien ==null || quydoikien.equals("0")){
							quydoikien="";
						}
						

						String[] arrDuLieu = new String[] { Integer.toString(stt),spMa,spTen,spSolo, chuoingaysd, quydoikien,spDvt,formatter.format(soluongXUAT), "","", "" };


						for (int j = 0; j < arrDuLieu.length; j++)
						{
							cells = new PdfPCell(new Paragraph(arrDuLieu[j], new Font(bf, 9, Font.NORMAL)));
							
							if(j==0){
								cells.setHorizontalAlignment(Element.ALIGN_CENTER);
							}
							else
							if (j <= 4 ){
									cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							}
							
							else{
								cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
							cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cells.setPadding(2.0f);	
							cells.setFixedHeight(0.6f*CONVERT);
							cells.setBorder(0);
									
							table.addCell(cells);
						}
				
						stt++;
					}
					rsSp.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			
		// DÒNG TRỐNG
			int kk=0;
			while(kk < 13-stt)
			{
				String[] arr_bosung = new String[] { " "," ", " ", " "," ", " "," " ," ", " "," ", " "};
	
				for (int j = 0; j < arr_bosung.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setPadding(2.0f);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				kk++;
			}
			
		String[] arr = new String[] {" "," ", " "," "," "," "," ","", " "," ",""};// totalTienTruocVAT
			for (int j = 0; j < arr.length; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
				if (j <= 5){
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_TOP);
				cells.setFixedHeight(0.8f*CONVERT);
				cells.setBorder(0);
				table.addCell(cells);
			}																	
			document.add(table);

			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/*private void CreatePxk_HOCHIMINH(Document document, ServletOutputStream outstream, IErpChuyenkho pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
			String ddh="";
			double Vat= 0;
			
			String khoxuat="";
			String khonhan ="";
								
	
			String  sql = " select xuattaikho as khoxuat," +
					      "       (SELECT xuattaikho FROM NHAPHANPHOI WHERE pk_seq = " +
					      "       (select npp_dat_fk from ERP_CHUYENKHONPP where pk_seq = "+ pxkBean.getId() +")) as khonhan "+
				          " from NHAPHANPHOI " +
				          " where pk_seq = '"+ pxkBean.getNppId() +"'";				  
				   
			   
			   System.out.println("Lấy Kho xuất/Kho nhận "+sql);
			   ResultSet rsINFO = db.get(sql);
			   if(rsINFO.next())
			   {
				   khoxuat = rsINFO.getString("khoxuat");
				   khonhan = rsINFO.getString("khonhan");
				   rsINFO.close();
				   
			   }
			
			   // LAY KHO XUAT/ KHO NHAP
			   String ngaychuyen = "";
			   String khoxuat_fk = "";
			   String tenNPP = "";
			   String lenhdieudong = "";
			   String lddcua = "";
			   String lddveviec = "";
			   String ngaylenhdieudong = "";
			   String sohopdong = "";
			   String ngayhopdong = "";
			   String nguoivanchuyen = "";
			   String ptvanchuyen = "";
			
			   
			   sql = " select NGAYCHUYEN,C.TEN AS KHOXUAT ,B.TEN AS TENNPP, " +
			   		 "        lenhdieudong, lddcua, lddveviec, ngaydieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen " +
		        	 " from  ERP_CHUYENKHONPP A INNER JOIN NHAPHANPHOI B ON A.NPP_FK = B.PK_SEQ" +
		        	 "       INNER JOIN KHO C ON A.KHOXUAT_FK=C.PK_SEQ " +
		        	 " where A.PK_SEQ = "+ pxkBean.getId() +" ";				  
		   	   
				   System.out.println("KHO XUAT/ KHO NHAP1_ "+sql);
				   ResultSet rskho = db.get(sql);
				   if(rskho.next())
				   {
					   ngaychuyen = rskho.getString("NGAYCHUYEN");
					   khoxuat_fk = rskho.getString("KHOXUAT");
					   tenNPP = rskho.getString("TENNPP");

					   lenhdieudong =rskho.getString("lenhdieudong")== null ? "":rskho.getString("lenhdieudong") ;
					   lddcua =rskho.getString("lddcua")== null ? "  ":rskho.getString("lddcua");
					   lddveviec =rskho.getString("lddveviec")== null ? "  ":rskho.getString("lddveviec");
					   ngaylenhdieudong =rskho.getString("ngaydieudong")== null ? "  ":rskho.getString("ngaydieudong");
					   sohopdong =rskho.getString("sohopdong")== null ? "":rskho.getString("sohopdong");
					   ngayhopdong =rskho.getString("ngayhopdong")== null ? "  ":rskho.getString("ngayhopdong");
					   nguoivanchuyen =rskho.getString("nguoivanchuyen")== null ? "  ":rskho.getString("nguoivanchuyen");
					   ptvanchuyen =rskho.getString("ptvanchuyen")== null ? "  ":rskho.getString("ptvanchuyen");
					   
						
				   rskho.close();
					   
				   }
			   			 
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		    document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.5f*CONVERT, 1.7f*CONVERT, 3.3f*CONVERT, 1.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);

			document.open() ;
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);

			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.3f * CONVERT);
			cell.setPaddingLeft(2.4f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayck = ngaychuyen.split("-");
			Paragraph pxk = new Paragraph(ngayck[2] + "                        " + ngayck[1] +  "                       " + ngayck[0].substring(2, 4) , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);

			
			// Thông tin NCC
			PdfPTable table1 =new PdfPTable(1);
			table1.setWidthPercentage(100);
			
			// TÊN TỔ CHỨC,CÁ NHÂN
			PdfPCell cell7 = new PdfPCell();			
			pxk = new Paragraph(""  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell7.setPaddingTop(0.05f*CONVERT);
			cell7.addElement(pxk);
			cell7.setBorder(0);						
			table1.addCell(cell7);				
						
			// ĐỊA CHỈ
			PdfPCell cell9 = new PdfPCell();			
			pxk = new Paragraph(" "   , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell9.addElement(pxk);
			cell9.setBorder(0);						
			table1.addCell(cell9);	
			
			//MÃ SỐ THUẾ
			PdfPCell cell11 = new PdfPCell();
			pxk = new Paragraph(" " , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell11.setPaddingLeft(1.4f*CONVERT);
			cell11.addElement(pxk);
			cell11.setBorder(0);						
			table1.addCell(cell11);	
			
			document.add(table1);
			
			// CĂN CỨ LỆNH ĐIỀU ĐỘNG
			String chuoingay = "                             ";
			if(ngaylenhdieudong.trim().length() > 0)
			{
			  String[] ngaydd = ngaylenhdieudong.split("-");
			  chuoingay = "         "+ ngaydd[2] + "                 " + ngaydd[1] + "              " + ngaydd[0] +"";
			}
			
			PdfPTable tableLDD = new PdfPTable(4);
			tableLDD.setWidthPercentage(100);
			float[] withs =  { 8.0f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableLDD.setWidths(withs);
			
			// LỆNH DD SỐ
			PdfPCell cell_so = new PdfPCell();			
			pxk = new Paragraph(" "+ lenhdieudong  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_so.setPaddingTop(1.0f*CONVERT);
			cell_so.setPaddingLeft(5.0f*CONVERT);
			cell_so.addElement(pxk);
			cell_so.setBorder(0);	
			tableLDD.addCell(cell_so);
			
			// NGÀY DD 
			PdfPCell cell_ngaydd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoingay  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngaydd.setPaddingTop(1.0f*CONVERT);
			cell_ngaydd.setPaddingLeft(0.5f*CONVERT);
			cell_ngaydd.addElement(pxk);
			cell_ngaydd.setBorder(0);
			tableLDD.addCell(cell_ngaydd);
			
			// LỆNH DD CỦA
			PdfPCell cell_lddcua = new PdfPCell();			
			pxk = new Paragraph(" "+ lddcua  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_lddcua.setPaddingTop(1.0f*CONVERT);
			cell_lddcua.setPaddingLeft(1.4f*CONVERT);
			cell_lddcua.addElement(pxk);
			cell_lddcua.setBorder(0);
			tableLDD.addCell(cell_lddcua);
			
			// LỆNH DD VỀ VIỆC
			PdfPCell cell_veviec = new PdfPCell();			
			pxk = new Paragraph(" "+ lddveviec  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_veviec.setPaddingTop(1.0f*CONVERT);
			cell_veviec.setPaddingLeft(2.4f*CONVERT);
			cell_veviec.addElement(pxk);
			cell_veviec.setBorder(0);
			tableLDD.addCell(cell_veviec);
			
			document.add(tableLDD);				
			
			
			// HỌ TÊN NGƯỜI VẬN CHUYỂN
			PdfPTable tableVC = new PdfPTable(4);
			tableVC.setWidthPercentage(100);
			float[] withs1 =  { 9.5f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableVC.setWidths(withs1);
			
			// NGƯỜI VC
			PdfPCell cell_ten = new PdfPCell();			
			pxk = new Paragraph(" "+ nguoivanchuyen  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ten.setPaddingTop(0.1f*CONVERT);
			cell_ten.setPaddingLeft(4.9f*CONVERT);
			cell_ten.addElement(pxk);
			cell_ten.setBorder(0);
			tableVC.addCell(cell_ten);
			
			// SỐ HD
			PdfPCell cell_sohd = new PdfPCell();			
			pxk = new Paragraph(" "+ sohopdong  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_sohd.setPaddingTop(0.1f*CONVERT);
			cell_sohd.setPaddingLeft(2.9f*CONVERT);
			cell_sohd.addElement(pxk);
			cell_sohd.setBorder(0);	
			tableVC.addCell(cell_sohd);
			
			// NGÀY HỢP ĐỒNG
			String chuoi = "";
			if(ngayhopdong.trim().length() > 0)
			{
				String[] ngayhd = ngayhopdong.split("-");
				chuoi = ngayhd[2] + "/" + ngayhd[1] + "/" + ngayhd[0];
			}
			
			PdfPCell cell_ngayhd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoi  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngayhd.setPaddingTop(0.1f*CONVERT);
			cell_ngayhd.setPaddingLeft(3.1f*CONVERT);
			cell_ngayhd.addElement(pxk);
			cell_ngayhd.setBorder(0);	
			tableVC.addCell(cell_ngayhd);
			
			// PHƯƠNG TIỆN VC
			PdfPCell cell_pt = new PdfPCell();			
			pxk = new Paragraph(" "+ ptvanchuyen  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_pt.setPaddingTop(0.1f*CONVERT);
			cell_pt.setPaddingLeft(3.0f*CONVERT);
			cell_pt.addElement(pxk);
			cell_pt.setBorder(0);	
			tableVC.addCell(cell_pt);
			
			document.add(tableVC);							
								
			
			// XUẤT TẠI KHO  + NHẬP TẠI KHO		
			PdfPTable tableKHO = new PdfPTable(2);
			tableKHO.setWidthPercentage(100);			
			float[] withs3 =  { 13.0f*CONVERT, 13.0f*CONVERT };
			tableKHO.setWidths(withs3);
			
			
			// XUẤT TẠI KHO
			PdfPCell cell_khoxuat = new PdfPCell();			
			pxk = new Paragraph(" "+ khoxuat_fk  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khoxuat.setPaddingTop(0.1f*CONVERT);
			cell_khoxuat.setPaddingLeft(2.5f*CONVERT);
			cell_khoxuat.addElement(pxk);
			cell_khoxuat.setBorder(0);
			tableKHO.addCell(cell_khoxuat);
			
			
			// NHẬP TẠI KHO	
			PdfPCell cell_khonhap = new PdfPCell();			
			pxk = new Paragraph(" "+khonhan  , new Font(bf, 10, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khonhap.setPaddingTop(0.1f*CONVERT);
			cell_khonhap.setPaddingLeft(2.8f*CONVERT);
			cell_khonhap.addElement(pxk);
			cell_khonhap.setBorder(0);	
			tableKHO.addCell(cell_khonhap);
			
			document.add(tableKHO);			
					
			
			
			// Table Content

			Font font4 = new Font(bf, 8, Font.BOLD);
			Font font5 = new Font(bf, 8, Font.BOLDITALIC);

			//String[] th = new String[]{ "STT", "Tên hàng hóa,dịch vụ", "Số kiểm soát", "Hạn dùng","Số kiện" ,"ĐVT", "Thực xuất", "Thực nhận","Đơn giá", "Thành tiền"};
			String[] th = new String[]{ " ", " ", " ", " "," " ," "," ", " "," "};
			
			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setSpacingBefore(2.1f*CONVERT);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withssp =  { 11.4f, 139.0f, 32.0f, 56.0f, 32.0f, 56.0f, 56.0f,60.0f,84.0f  }; //24.4f, 140.0f,
			table.setWidths(withssp);

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double tongslnhan=0;
			double tongslchuyen=0;
			
			String query =
			"	select d.ten as tenSP, case when c.solo = 'NA' then '' else isnull(c.solo,'') end as solo, isnull(c.ngayhethan,'') as handung, e.DONVI,c.soluong as SOLUONG, b.DONGIA  "+
			"	from ERP_CHUYENKHONPP a " +
			"   inner join ERP_CHUYENKHONPP_SANPHAM b on a.pk_seq=b.chuyenkho_fk "+	
			"   inner join ERP_CHUYENKHONPP_SANPHAM_CHITIET c on a.pk_seq=c.chuyenkho_fk  and b.sanpham_fk= c.sanpham_fk " +
			"   inner join SANPHAM d on c.SANPHAM_FK = d.PK_SEQ " +
			"   inner join DONVIDOLUONG e on b.dvdl_fk = e.PK_SEQ "+			
			"	where a.PK_SEQ ="+ pxkBean.getId() +" ";
			
			
			System.out.println("[ERP_CHUYENKHO_SANPHAM]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soluongChuyen = rsSP.getDouble("soluong");
				tongslchuyen +=soluongChuyen;
				
				double soluongNhan= 0;
			
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soluongChuyen*dongia;	
						
				totalTienTruocVAT+=thanhtien;
				String chuoingaysd = "";
				if(rsSP.getString("handung").trim().length() > 0)
				{
					String[] hsd = rsSP.getString("handung").split("-");
					chuoingaysd = hsd[2] + "/" + hsd[1] + "/" + hsd[0] ;
				}
								
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("tenSP"), rsSP.getString("solo"), chuoingaysd, 
						rsSP.getString("DONVI"),formatter.format(soluongChuyen)," ", "" , "" };


				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.NORMAL)));
					if (j <= 4){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					
					if(j == 1)
						cells.setPaddingLeft(0.3f*CONVERT);
					if(j == 2)
						cells.setPaddingLeft(-1.1f*CONVERT);
					if(j == 3)
						cells.setPaddingLeft(-1.2f*CONVERT);
					if(j == 4)
						cells.setPaddingLeft(-1.5f*CONVERT);
					if(j == 5)
						cells.setPaddingRight(4.7f*CONVERT);
					
						
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				stt++;
				
			}
						
		// DÒNG TRỐNG
			
			// DONG TRONG
			int kk=0;
			while(kk < 11-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " ", " "," " ," ", " "," ", " "};
	
				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 11, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setPadding(2.0f);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				kk++;
			}
			
		String[] arr = new String[] {" ", " "," "," "," "," "," "," ", ""};//totalTienTruocVAT
			for (int j = 0; j < 9; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
				if (j <= 4){
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				

				if(j == 1)
					cells.setPaddingLeft(1.2f*CONVERT);
				if(j == 2)
					cells.setPaddingLeft(-1.1f*CONVERT);
				if(j == 3)
					cells.setPaddingLeft(-0.2f*CONVERT);
				if(j == 4)
					cells.setPaddingLeft(-0.5f*CONVERT);
				if(j == 5)
					cells.setPaddingRight(3.0f*CONVERT);
				
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setPadding(2.0f);	
				cells.setFixedHeight(0.8f*CONVERT);
				cells.setBorder(0);
				table.addCell(cells);
			}
			
																								
			document.add(table);
								
			//Table thời gian nhập xuất
			PdfPTable tableTg = new PdfPTable(3);
			tableTg.setWidthPercentage(90);
			tableTg.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableTg.setWidths(new float[]
			{ 150.0f, 100.0f, 150.0f });

			PdfPCell cella = new PdfPCell(new Paragraph(""+ ngayck[2] + "                  "+ ngayck[1] + "                  "+ ngayck[0], new Font(bf, 9, Font.NORMAL)));
			cella.setPaddingLeft(1.2f*CONVERT);
			cella.setPaddingTop(0.2f*CONVERT);
			cella.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cellb = new PdfPCell(new Paragraph("", new Font(bf, 8, Font.BOLD)));
			cellb.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cellc = new PdfPCell(new Paragraph(" ", new Font(bf, 9, Font.NORMAL)));
			cellc.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cella.setBorder(0);
			cellb.setBorder(0);
			cellc.setBorder(0);
			
			tableTg.addCell(cella);
			tableTg.addCell(cellb);
			tableTg.addCell(cellc);

			document.add(tableTg);
			
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void CreatePxk_CANTHO(Document document, ServletOutputStream outstream, IErpChuyenkho pxkBean) throws IOException
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
 
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		    document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.5f*CONVERT, 1.0f*CONVERT, 2.0f*CONVERT, 1.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);

			document.open() ;
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);

			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.55f * CONVERT);
			cell.setPaddingLeft(1.9f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayck = pxkBean.getNgayyeucau().split("-");
			Paragraph pxk = new Paragraph(ngayck[2] + "                        " + ngayck[1] +  "                        " + ngayck[0].substring(2, 4) , new Font(bf, 8, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);

			
			// Thông tin NCC
			PdfPTable table1 =new PdfPTable(1);
			table1.setWidthPercentage(100);
			
			// TÊN TỔ CHỨC,CÁ NHÂN
			PdfPCell cell7 = new PdfPCell();			
			pxk = new Paragraph(""  , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell7.setPaddingTop(0.05f*CONVERT);
			cell7.addElement(pxk);
			cell7.setBorder(0);						
			table1.addCell(cell7);				
						
			// ĐỊA CHỈ
			PdfPCell cell9 = new PdfPCell();			
			pxk = new Paragraph(" "   , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell9.addElement(pxk);
			cell9.setBorder(0);						
			table1.addCell(cell9);	
			
			//MÃ SỐ THUẾ
			PdfPCell cell11 = new PdfPCell();
			pxk = new Paragraph(" " , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell11.setPaddingLeft(1.5f*CONVERT);
			cell11.addElement(pxk);
			cell11.setBorder(0);						
			table1.addCell(cell11);	
			
			document.add(table1);
			
			// CĂN CỨ LỆNH ĐIỀU ĐỘNG
			String chuoingay = "                             ";
			if(pxkBean.getNgaydieudong().trim().length() > 0)
			{
			  String[] ngaydd = pxkBean.getNgaydieudong().split("-");
			  chuoingay = "         "+ ngaydd[2] + "                 " + ngaydd[1] + "                   " + ngaydd[0] +"  ";
			}
			
			PdfPTable tableLDD = new PdfPTable(4);
			tableLDD.setWidthPercentage(100);
			float[] withs =  { 8.0f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableLDD.setWidths(withs);
			
			// LỆNH DD SỐ
			PdfPCell cell_so = new PdfPCell();			
			pxk = new Paragraph(" "+ pxkBean.getLenhdieudong()  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_so.setPaddingTop(0.5f*CONVERT);
			cell_so.setPaddingLeft(4.5f*CONVERT);
			cell_so.addElement(pxk);
			cell_so.setBorder(0);	
			tableLDD.addCell(cell_so);
			
			// NGÀY DD 
			PdfPCell cell_ngaydd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoingay  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngaydd.setPaddingTop(0.5f*CONVERT);
			cell_ngaydd.addElement(pxk);
			cell_ngaydd.setBorder(0);
			tableLDD.addCell(cell_ngaydd);
			
			// LỆNH DD CỦA
			PdfPCell cell_lddcua = new PdfPCell();			
			pxk = new Paragraph(" "+ pxkBean.  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_lddcua.setPaddingTop(0.5f*CONVERT);
			cell_lddcua.setPaddingLeft(0.8f*CONVERT);
			cell_lddcua.addElement(pxk);
			cell_lddcua.setBorder(0);
			tableLDD.addCell(cell_lddcua);
			
			// LỆNH DD VỀ VIỆC
			PdfPCell cell_veviec = new PdfPCell();			
			pxk = new Paragraph(" "+ lddveviec  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_veviec.setPaddingTop(0.5f*CONVERT);
			cell_veviec.setPaddingLeft(2.4f*CONVERT);
			cell_veviec.addElement(pxk);
			cell_veviec.setBorder(0);
			tableLDD.addCell(cell_veviec);
			
			document.add(tableLDD);				
			
			
			// HỌ TÊN NGƯỜI VẬN CHUYỂN
			PdfPTable tableVC = new PdfPTable(4);
			tableVC.setWidthPercentage(100);
			float[] withs1 =  { 9.5f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableVC.setWidths(withs1);
			
			// NGƯỜI VC
			PdfPCell cell_ten = new PdfPCell();			
			pxk = new Paragraph(" "+ nguoivanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ten.setPaddingTop(0.1f*CONVERT);
			cell_ten.setPaddingLeft(4.5f*CONVERT);
			cell_ten.addElement(pxk);
			cell_ten.setBorder(0);
			tableVC.addCell(cell_ten);
			
			// SỐ HD
			PdfPCell cell_sohd = new PdfPCell();			
			pxk = new Paragraph(" "+ sohopdong  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_sohd.setPaddingTop(0.1f*CONVERT);
			cell_sohd.setPaddingLeft(2.5f*CONVERT);
			cell_sohd.addElement(pxk);
			cell_sohd.setBorder(0);	
			tableVC.addCell(cell_sohd);
			
			// NGÀY HỢP ĐỒNG
			String chuoi = "";
			if(ngayhopdong.trim().length() > 0)
			{
				String[] ngayhd = ngayhopdong.split("-");
				chuoi = ngayhd[2] + "/" + ngayhd[1] + "/" + ngayhd[0];
			}
			
			PdfPCell cell_ngayhd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoi  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngayhd.setPaddingTop(0.1f*CONVERT);
			cell_ngayhd.setPaddingLeft(2.8f*CONVERT);
			cell_ngayhd.addElement(pxk);
			cell_ngayhd.setBorder(0);	
			tableVC.addCell(cell_ngayhd);
			
			// PHƯƠNG TIỆN VC
			PdfPCell cell_pt = new PdfPCell();			
			pxk = new Paragraph(" "+ ptvanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_pt.setPaddingTop(0.1f*CONVERT);
			cell_pt.setPaddingLeft(4.0f*CONVERT);
			cell_pt.addElement(pxk);
			cell_pt.setBorder(0);	
			tableVC.addCell(cell_pt);
			
			document.add(tableVC);							
								
			
			// XUẤT TẠI KHO  + NHẬP TẠI KHO		
			PdfPTable tableKHO = new PdfPTable(2);
			tableKHO.setWidthPercentage(100);			
			float[] withs3 =  { 13.0f*CONVERT, 13.0f*CONVERT };
			tableKHO.setWidths(withs3);
			
			
			// XUẤT TẠI KHO
			PdfPCell cell_khoxuat = new PdfPCell();			
			pxk = new Paragraph(" "+ khoxuat_fk  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khoxuat.setPaddingTop(0.2f*CONVERT);
			cell_khoxuat.setPaddingLeft(2.5f*CONVERT);
			cell_khoxuat.addElement(pxk);
			cell_khoxuat.setBorder(0);
			tableKHO.addCell(cell_khoxuat);
			
			
			// NHẬP TẠI KHO	
			PdfPCell cell_khonhap = new PdfPCell();			
			pxk = new Paragraph(" "  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khonhap.setPaddingTop(0.2f*CONVERT);
			cell_khonhap.setPaddingLeft(1.5f*CONVERT);
			cell_khonhap.addElement(pxk);
			cell_khonhap.setBorder(0);	
			tableKHO.addCell(cell_khonhap);
			
			document.add(tableKHO);			
					
			
			
			// Table Content

			Font font4 = new Font(bf, 8, Font.BOLD);
			Font font5 = new Font(bf, 8, Font.BOLDITALIC);

			//String[] th = new String[]{ "STT", "Tên hàng hóa,dịch vụ", "Số kiểm soát", "Hạn dùng","Số kiện" ,"ĐVT", "Thực xuất", "Thực nhận","Đơn giá", "Thành tiền"};
			String[] th = new String[]{ " ", " ", " ", " "," " ," "," ", " "," "};
			
			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setSpacingBefore(1.6f*CONVERT);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withssp =  { 16.0f, 160.0f, 32.0f, 56.0f, 32.0f, 56.0f, 56.0f,60.0f,84.0f  };
			table.setWidths(withssp);

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double tongslnhan=0;
			double tongslchuyen=0;
			
			String query =
			"	select d.ten as tenSP, case when c.solo = 'NA' then '' else isnull(c.solo,'') end as solo, isnull(c.ngayhethan,'') as handung, e.DONVI,c.soluong as SOLUONG, b.DONGIA  "+
			"	from ERP_CHUYENKHONPP a " +
			"   inner join ERP_CHUYENKHONPP_SANPHAM b on a.pk_seq=b.chuyenkho_fk "+	
			"   inner join ERP_CHUYENKHONPP_SANPHAM_CHITIET c on a.pk_seq=c.chuyenkho_fk  and b.sanpham_fk= c.sanpham_fk " +
			"   inner join SANPHAM d on c.SANPHAM_FK = d.PK_SEQ " +
			"   inner join DONVIDOLUONG e on b.dvdl_fk = e.PK_SEQ "+			
			"	where a.PK_SEQ ="+ pxkBean.getId() +" ";
			
			
			System.out.println("[ERP_CHUYENKHO_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soluongChuyen = rsSP.getDouble("soluong");
				tongslchuyen +=soluongChuyen;
				
				double soluongNhan= 0;
			
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soluongChuyen*dongia;	
						
				totalTienTruocVAT+=thanhtien;
				String chuoingaysd = "";
				if(rsSP.getString("handung").trim().length() > 0)
				{
					String[] hsd = rsSP.getString("handung").split("-");
					chuoingaysd = hsd[2] + "/" + hsd[1] + "/" + hsd[0] ;
				}
								
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("tenSP"), rsSP.getString("solo"), chuoingaysd, 
						rsSP.getString("DONVI"),formatter.format(soluongChuyen)," ", "" , "" };


				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.BOLD)));
					if (j <= 4){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					
					if(j == 1)
						cells.setPaddingLeft(0.2f*CONVERT);
					if(j == 2)
						cells.setPaddingLeft(0.5f*CONVERT);
					if(j == 3)
						cells.setPaddingLeft(1.0f*CONVERT);
					if(j == 4)
						cells.setPaddingLeft(0.8f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(-0.5f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(-0.3f*CONVERT);
					
						
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				stt++;
				
			}
						
		// DÒNG TRỐNG
			
			// DONG TRONG
			int kk=0;
			while(kk < 13-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " ", " "," " ," ", " "," ", " "};
	
				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 10, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setPadding(2.0f);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				kk++;
			}
			
		String[] arr = new String[] {" ", " "," "," "," ",formatter.format(tongslchuyen)," "," ", ""};//totalTienTruocVAT
			for (int j = 0; j < 9; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 8, Font.BOLD)));
				if (j <= 4){
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				
				if(j == 1)
					cells.setPaddingLeft(0.2f*CONVERT);
				if(j == 2)
					cells.setPaddingLeft(1.0f*CONVERT);
				if(j == 3)
					cells.setPaddingLeft(1.0f*CONVERT);
				if(j == 4)
					cells.setPaddingLeft(0.8f*CONVERT);
				if(j == 7)
					cells.setPaddingRight(-0.5f*CONVERT);
				if(j == 8)
					cells.setPaddingRight(-0.3f*CONVERT);
				
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setPadding(2.0f);	
				cells.setFixedHeight(0.8f*CONVERT);
				cells.setBorder(0);
				table.addCell(cells);
			}
			
																								
			document.add(table);
											
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void CreatePxk_DANANG(Document document, ServletOutputStream outstream, IErpChuyenkho pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
					String ddh="";
					double Vat= 0;
					
					String khoxuat="";
					String khonhan ="";
										
			
			String  sql = " select xuattaikho as khoxuat," +
					      "       (SELECT xuattaikho FROM NHAPHANPHOI WHERE pk_seq = " +
					      "       (select npp_dat_fk from ERP_CHUYENKHONPP where pk_seq = "+ pxkBean.getId() +")) as khonhan "+
				          " from NHAPHANPHOI " +
				          " where pk_seq = '"+ pxkBean.getNppId() +"'";				  
				   
			   
			   System.out.println("Lấy Kho xuất/Kho nhận "+sql);
			   ResultSet rsINFO = db.get(sql);
			   if(rsINFO.next())
			   {
				   khoxuat = rsINFO.getString("khoxuat");
				   khonhan = rsINFO.getString("khonhan");
				   rsINFO.close();
				   
			   }
			
			   // LAY KHO XUAT/ KHO NHAP
			   String ngaychuyen = "";
			   String khoxuat_fk = "";
			   String tenNPP = "";
			   String lenhdieudong = "";
			   String lddcua = "";
			   String lddveviec = "";
			   String ngaylenhdieudong = "";
			   String sohopdong = "";
			   String ngayhopdong = "";
			   String nguoivanchuyen = "";
			   String ptvanchuyen = "";
			
			   
			   sql = " select NGAYCHUYEN,B.TEN AS TENNPP, " +
			   		 "        lenhdieudong, lddcua, lddveviec, ngaydieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen" +
		        	 " from  ERP_CHUYENKHONPP A INNER JOIN NHAPHANPHOI B ON A.NPP_FK = B.PK_SEQ" +
		        	 " where A.PK_SEQ = "+ pxkBean.getId() +" ";				  
		   	   
				   System.out.println("KHO XUAT/ KHO NHAP_ "+sql);
				   ResultSet rskho = db.get(sql);
				   if(rskho.next())
				   {
					   ngaychuyen = rskho.getString("NGAYCHUYEN");
					   tenNPP = rskho.getString("TENNPP");

					   lenhdieudong =rskho.getString("lenhdieudong")== null ? "":rskho.getString("lenhdieudong") ;
					   lddcua =rskho.getString("lddcua")== null ? "  ":rskho.getString("lddcua");
					   lddveviec =rskho.getString("lddveviec")== null ? "  ":rskho.getString("lddveviec");
					   ngaylenhdieudong =rskho.getString("ngaydieudong")== null ? "  ":rskho.getString("ngaydieudong");
					   sohopdong =rskho.getString("sohopdong")== null ? "":rskho.getString("sohopdong");
					   ngayhopdong =rskho.getString("ngayhopdong")== null ? "  ":rskho.getString("ngayhopdong");
					   nguoivanchuyen =rskho.getString("nguoivanchuyen")== null ? "  ":rskho.getString("nguoivanchuyen");
					   ptvanchuyen =rskho.getString("ptvanchuyen")== null ? "  ":rskho.getString("ptvanchuyen");
					   
					   
						
				   rskho.close();
					   
				   }
			   			 
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		    document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.8f*CONVERT, 1.0f*CONVERT, 2.1f*CONVERT, 1.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);

			document.open() ;
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);

			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.55f * CONVERT);
			cell.setPaddingLeft(0.6f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayck = ngaychuyen.split("-");
			Paragraph pxk = new Paragraph(ngayck[2] + "                        " + ngayck[1] +  "                        " + ngayck[0] , new Font(bf, 9, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);

			
			// Thông tin NCC
			PdfPTable table1 =new PdfPTable(1);
			table1.setWidthPercentage(100);
			
			// TÊN TỔ CHỨC,CÁ NHÂN
			PdfPCell cell7 = new PdfPCell();			
			pxk = new Paragraph(""  , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell7.setPaddingTop(0.05f*CONVERT);
			cell7.addElement(pxk);
			cell7.setBorder(0);						
			table1.addCell(cell7);				
						
			// ĐỊA CHỈ
			PdfPCell cell9 = new PdfPCell();			
			pxk = new Paragraph(" "   , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell9.addElement(pxk);
			cell9.setBorder(0);						
			table1.addCell(cell9);	
			
			//MÃ SỐ THUẾ
			PdfPCell cell11 = new PdfPCell();
			pxk = new Paragraph(" " , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell11.setPaddingLeft(1.5f*CONVERT);
			cell11.addElement(pxk);
			cell11.setBorder(0);						
			table1.addCell(cell11);	
			
			document.add(table1);
			
			// CĂN CỨ LỆNH ĐIỀU ĐỘNG
			String chuoingay = "                             ";
			if(ngaylenhdieudong.trim().length() > 0)
			{
			  String[] ngaydd = ngaylenhdieudong.split("-");
			  chuoingay = "         "+ ngaydd[2] + "                   " + ngaydd[1] + "                   " + ngaydd[0] +"  ";
			}
			
			PdfPTable tableLDD = new PdfPTable(4);
			tableLDD.setWidthPercentage(100);
			float[] withs =  { 8.0f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableLDD.setWidths(withs);
			
			// LỆNH DD SỐ
			PdfPCell cell_so = new PdfPCell();			
			pxk = new Paragraph(" "+ lenhdieudong  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_so.setPaddingTop(0.6f*CONVERT);
			cell_so.setPaddingLeft(4.9f*CONVERT);
			cell_so.addElement(pxk);
			cell_so.setBorder(0);	
			tableLDD.addCell(cell_so);
			
			// NGÀY DD 
			PdfPCell cell_ngaydd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoingay  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngaydd.setPaddingTop(0.6f*CONVERT);
			cell_ngaydd.addElement(pxk);
			cell_ngaydd.setBorder(0);
			tableLDD.addCell(cell_ngaydd);
			
			// LỆNH DD CỦA
			PdfPCell cell_lddcua = new PdfPCell();			
			pxk = new Paragraph(" "+ lddcua  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_lddcua.setPaddingTop(0.6f*CONVERT);
			cell_lddcua.setPaddingLeft(0.8f*CONVERT);
			cell_lddcua.addElement(pxk);
			cell_lddcua.setBorder(0);
			tableLDD.addCell(cell_lddcua);
			
			// LỆNH DD VỀ VIỆC
			PdfPCell cell_veviec = new PdfPCell();			
			pxk = new Paragraph(" "+ lddveviec  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_veviec.setPaddingTop(0.6f*CONVERT);
			cell_veviec.setPaddingLeft(1.4f*CONVERT);
			cell_veviec.addElement(pxk);
			cell_veviec.setBorder(0);
			tableLDD.addCell(cell_veviec);
			
			document.add(tableLDD);				
			
			
			// HỌ TÊN NGƯỜI VẬN CHUYỂN
			PdfPTable tableVC = new PdfPTable(4);
			tableVC.setWidthPercentage(100);
			float[] withs1 =  { 9.5f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableVC.setWidths(withs1);
			
			// NGƯỜI VC
			PdfPCell cell_ten = new PdfPCell();			
			pxk = new Paragraph(" "+ nguoivanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ten.setPaddingTop(0.1f*CONVERT);
			cell_ten.setPaddingLeft(4.7f*CONVERT);
			cell_ten.addElement(pxk);
			cell_ten.setBorder(0);
			tableVC.addCell(cell_ten);
			
			// SỐ HD
			PdfPCell cell_sohd = new PdfPCell();			
			pxk = new Paragraph(" "+ sohopdong  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_sohd.setPaddingTop(0.1f*CONVERT);
			cell_sohd.setPaddingLeft(3.2f*CONVERT);
			cell_sohd.addElement(pxk);
			cell_sohd.setBorder(0);	
			tableVC.addCell(cell_sohd);
			
			// NGÀY HỢP ĐỒNG
			String chuoi = "";
			if(ngayhopdong.trim().length() > 0)
			{
				String[] ngayhd = ngayhopdong.split("-");
				chuoi = ngayhd[2] + "/" + ngayhd[1] + "/" + ngayhd[0];
			}
			
			PdfPCell cell_ngayhd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoi  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngayhd.setPaddingTop(0.1f*CONVERT);
			cell_ngayhd.setPaddingLeft(2.3f*CONVERT);
			cell_ngayhd.addElement(pxk);
			cell_ngayhd.setBorder(0);	
			tableVC.addCell(cell_ngayhd);
			
			// PHƯƠNG TIỆN VC
			PdfPCell cell_pt = new PdfPCell();			
			pxk = new Paragraph(" "+ ptvanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_pt.setPaddingTop(0.1f*CONVERT);
			cell_pt.setPaddingLeft(3.5f*CONVERT);
			cell_pt.addElement(pxk);
			cell_pt.setBorder(0);	
			tableVC.addCell(cell_pt);
			
			document.add(tableVC);							
								
			
			// XUẤT TẠI KHO  + NHẬP TẠI KHO		
			PdfPTable tableKHO = new PdfPTable(2);
			tableKHO.setWidthPercentage(100);			
			float[] withs3 =  { 13.0f*CONVERT, 13.0f*CONVERT };
			tableKHO.setWidths(withs3);
			
			
			// XUẤT TẠI KHO
			PdfPCell cell_khoxuat = new PdfPCell();			
			pxk = new Paragraph(" "+ khoxuat  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khoxuat.setPaddingTop(-0.05f*CONVERT);
			cell_khoxuat.setPaddingLeft(2.2f*CONVERT);
			cell_khoxuat.addElement(pxk);
			cell_khoxuat.setBorder(0);
			tableKHO.addCell(cell_khoxuat);
			
			
			// NHẬP TẠI KHO	
			PdfPCell cell_khonhap = new PdfPCell();			
			pxk = new Paragraph(" "+khonhan  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khonhap.setPaddingTop(-0.05f*CONVERT);
			cell_khonhap.setPaddingLeft(2.2f*CONVERT);
			cell_khonhap.addElement(pxk);
			cell_khonhap.setBorder(0);	
			tableKHO.addCell(cell_khonhap);
			
			document.add(tableKHO);			
					
			
			
			// Table Content


			//String[] th = new String[]{ "STT", "Tên hàng hóa,dịch vụ", "Số kiểm soát", "Hạn dùng","Số kiện" ,"ĐVT", "Thực xuất", "Thực nhận","Đơn giá", "Thành tiền"};
			String[] th = new String[]{ " ", " ", " ", " "," " ," "," ", " "," "};
			
			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setSpacingBefore(2.1f*CONVERT);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withssp =  { 20.0f, 145.0f, 25.6f, 72.6f, 24.4f, 56.0f, 56.0f,60.0f,84.0f  }; 
			table.setWidths(withssp);

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double tongslnhan=0;
			double tongslchuyen=0;
			
			String query =
			"	select d.ten as tenSP, case when c.solo = 'NA' then '' else isnull(c.solo,'') end as solo, isnull(c.ngayhethan,'') as handung, e.DONVI,c.soluong as SOLUONG, b.DONGIA  "+
			"	from ERP_CHUYENKHONPP a " +
			"   inner join ERP_CHUYENKHONPP_SANPHAM b on a.pk_seq=b.chuyenkho_fk "+	
			"   inner join ERP_CHUYENKHONPP_SANPHAM_CHITIET c on a.pk_seq=c.chuyenkho_fk  and b.sanpham_fk= c.sanpham_fk " +
			"   inner join SANPHAM d on c.SANPHAM_FK = d.PK_SEQ " +
			"   inner join DONVIDOLUONG e on b.dvdl_fk = e.PK_SEQ "+			
			"	where a.PK_SEQ ="+ pxkBean.getId() +" ";
			
			
			System.out.println("[ERP_CHUYENKHO_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soluongChuyen = rsSP.getDouble("soluong");
				tongslchuyen +=soluongChuyen;
				
				double soluongNhan= 0;
				
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soluongChuyen*dongia;	
						
				totalTienTruocVAT+=thanhtien;
				String chuoingaysd = "";
				if(rsSP.getString("handung").trim().length() > 0)
				{
					String[] hsd = rsSP.getString("handung").split("-");
					chuoingaysd = hsd[2] + "/" + hsd[1] + "/" + hsd[0] ;
				}
								
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("tenSP"), rsSP.getString("solo"), chuoingaysd, 
						rsSP.getString("DONVI"),formatter.format(soluongChuyen), " ", "" , "" };


				for (int j = 0; j < 9; j++)
				{
					if (j <= 4){
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else if(j==5){
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					else{
						cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					if(j == 0)
						cells.setPaddingLeft(0.9f*CONVERT);
					if(j == 1)
						cells.setPaddingLeft(0.8f*CONVERT);
					if(j == 2)
						cells.setPaddingLeft(-0.6f*CONVERT);
					if(j == 3)
						cells.setPaddingLeft(-1.0f*CONVERT);
					if(j == 4)
						cells.setPaddingLeft(2.0f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(-0.8f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(-0.3f*CONVERT);
					
						
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);	
					cells.setFixedHeight(0.6f*CONVERT);
					//cells.setBorderWidth(1);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				stt++;
				
			}
						
		// DÒNG TRỐNG
			
			// DONG TRONG
			int kk=0;
			while(kk < 11-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " ", " "," " ," ", " "," ", " "};
	
				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 9, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setPadding(2.0f);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				kk++;
			}
			
		String[] arr = new String[] {" ", " "," "," "," ",formatter.format(tongslchuyen), " "," ", ""};//totalTienTruocVAT
			for (int j = 0; j < 9; j++)
			{
				if (j <= 4){
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else if(j==5){
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 10, Font.BOLD)));
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				else{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				if(j == 0)
					cells.setPaddingLeft(0.9f*CONVERT);
				if(j == 1)
					cells.setPaddingLeft(0.8f*CONVERT);
				if(j == 2)
					cells.setPaddingLeft(-0.6f*CONVERT);
				if(j == 3)
					cells.setPaddingLeft(-1.0f*CONVERT);
				if(j == 4)
					cells.setPaddingLeft(2.0f*CONVERT);
				if(j == 7)
					cells.setPaddingRight(-0.8f*CONVERT);
				if(j == 8)
					cells.setPaddingRight(-0.3f*CONVERT);
				
				cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
				cells.setPadding(2.0f);	
				cells.setFixedHeight(0.8f*CONVERT);
				cells.setBorder(0);
				table.addCell(cells);
			}
			
																								
			document.add(table);
											
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private void CreatePxk_KHANHHOA(Document document, ServletOutputStream outstream, IErpChuyenkho pxkBean) throws IOException
	{
		try
		{
						
			dbutils db = new dbutils();
				
			//LAY THONG TIN NCC
					String ddh="";
					double Vat= 0;
					
					String khoxuat="";
					String khonhan ="";
										
			
			String  sql = " select xuattaikho as khoxuat," +
					      "       (SELECT xuattaikho FROM NHAPHANPHOI WHERE pk_seq = " +
					      "       (select npp_dat_fk from ERP_CHUYENKHONPP where pk_seq = "+ pxkBean.getId() +")) as khonhan "+
				          " from NHAPHANPHOI " +
				          " where pk_seq = '"+ pxkBean.getNppId() +"'";				  
				   
			   
			   System.out.println("Lấy Kho xuất/Kho nhận "+sql);
			   ResultSet rsINFO = db.get(sql);
			   if(rsINFO.next())
			   {
				   khoxuat = rsINFO.getString("khoxuat");
				   khonhan = rsINFO.getString("khonhan");
				   rsINFO.close();
				   
			   }
			
			   // LAY KHO XUAT/ KHO NHAP
			   String ngaychuyen = "";
			   String khoxuat_fk = "";
			   String tenNPP = "";
			   String lenhdieudong = "";
			   String lddcua = "";
			   String lddveviec = "";
			   String ngaylenhdieudong = "";
			   String sohopdong = "";
			   String ngayhopdong = "";
			   String nguoivanchuyen = "";
			   String ptvanchuyen = "";
			
			   
			   sql = " select NGAYCHUYEN,B.TEN AS TENNPP, " +
			   		 "        lenhdieudong, lddcua, lddveviec, ngaydieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen" +
		        	 " from  ERP_CHUYENKHONPP A INNER JOIN NHAPHANPHOI B ON A.NPP_FK = B.PK_SEQ" +
		        	 " where A.PK_SEQ = "+ pxkBean.getId() +" ";				  
		   	   
				   System.out.println("KHO XUAT/ KHO NHAP_ "+sql);
				   ResultSet rskho = db.get(sql);
				   if(rskho.next())
				   {
					   ngaychuyen = rskho.getString("NGAYCHUYEN");
					   tenNPP = rskho.getString("TENNPP");

					   lenhdieudong =rskho.getString("lenhdieudong")== null ? "":rskho.getString("lenhdieudong") ;
					   lddcua =rskho.getString("lddcua")== null ? "  ":rskho.getString("lddcua");
					   lddveviec =rskho.getString("lddveviec")== null ? "  ":rskho.getString("lddveviec");
					   ngaylenhdieudong =rskho.getString("ngaydieudong")== null ? "  ":rskho.getString("ngaydieudong");
					   sohopdong =rskho.getString("sohopdong")== null ? "":rskho.getString("sohopdong");
					   ngayhopdong =rskho.getString("ngayhopdong")== null ? "  ":rskho.getString("ngayhopdong");
					   nguoivanchuyen =rskho.getString("nguoivanchuyen")== null ? "  ":rskho.getString("nguoivanchuyen");
					   ptvanchuyen =rskho.getString("ptvanchuyen")== null ? "  ":rskho.getString("ptvanchuyen");
					   
					   
						
				   rskho.close();
					   
				   }
			   			 
		    NumberFormat formatter = new DecimalFormat("#,###,###.##");
		    document.setPageSize(PageSize.A4.rotate());
			document.setMargins(1.1f*CONVERT, 1.0f*CONVERT, 2.6f*CONVERT, 1.0f*CONVERT); // L,R,T,B
			PdfWriter writer = PdfWriter.getInstance(document, outstream);

			document.open() ;
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);

			
			PdfPTable tableheader =new PdfPTable(1);
			tableheader.setWidthPercentage(100);			

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			cell.setPaddingTop(0.65f * CONVERT);//0.55
			cell.setPaddingLeft(1.4f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			String [] ngayck = ngaychuyen.split("-");
			Paragraph pxk = new Paragraph("       "+ngayck[2] + "                     " + ngayck[1] +  "                   " + ngayck[0] , new Font(bf, 9, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			pxk.setSpacingAfter(2);
			cell.addElement(pxk);

			tableheader.addCell(cell);
			document.add(tableheader);

			
			// Thông tin NCC
			PdfPTable table1 =new PdfPTable(1);
			table1.setWidthPercentage(100);
			
			// TÊN TỔ CHỨC,CÁ NHÂN
			PdfPCell cell7 = new PdfPCell();			
			pxk = new Paragraph(""  , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell7.setPaddingTop(0.05f*CONVERT);
			cell7.addElement(pxk);
			cell7.setBorder(0);						
			table1.addCell(cell7);				
						
			// ĐỊA CHỈ
			PdfPCell cell9 = new PdfPCell();			
			pxk = new Paragraph(" "   , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell9.addElement(pxk);
			cell9.setBorder(0);						
			table1.addCell(cell9);	
			
			//MÃ SỐ THUẾ
			PdfPCell cell11 = new PdfPCell();
			pxk = new Paragraph(" " , new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell11.setPaddingLeft(1.5f*CONVERT);
			cell11.addElement(pxk);
			cell11.setBorder(0);						
			table1.addCell(cell11);	
			
			document.add(table1);
			
			// CĂN CỨ LỆNH ĐIỀU ĐỘNG
			String chuoingay = "                             ";
			if(ngaylenhdieudong.trim().length() > 0)
			{
			  String[] ngaydd = ngaylenhdieudong.split("-");
			  chuoingay = "         "+ ngaydd[2] + "                   " + ngaydd[1] + "                   " + ngaydd[0] +"  ";
			}
			
			PdfPTable tableLDD = new PdfPTable(4);
			tableLDD.setWidthPercentage(100);
			float[] withs =  { 8.0f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableLDD.setWidths(withs);
			
			// LỆNH DD SỐ
			PdfPCell cell_so = new PdfPCell();			
			pxk = new Paragraph(" "+ lenhdieudong  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_so.setPaddingTop(0.6f*CONVERT);
			cell_so.setPaddingLeft(4.9f*CONVERT);
			cell_so.addElement(pxk);
			cell_so.setBorder(0);	
			tableLDD.addCell(cell_so);
			
			// NGÀY DD 
			PdfPCell cell_ngaydd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoingay  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngaydd.setPaddingTop(0.5f*CONVERT);
			cell_ngaydd.addElement(pxk);
			cell_ngaydd.setBorder(0);
			tableLDD.addCell(cell_ngaydd);
			
			// LỆNH DD CỦA
			PdfPCell cell_lddcua = new PdfPCell();			
			pxk = new Paragraph(" "+ lddcua  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_lddcua.setPaddingTop(0.5f*CONVERT);
			cell_lddcua.setPaddingLeft(0.8f*CONVERT);
			cell_lddcua.addElement(pxk);
			cell_lddcua.setBorder(0);
			tableLDD.addCell(cell_lddcua);
			
			// LỆNH DD VỀ VIỆC
			PdfPCell cell_veviec = new PdfPCell();			
			pxk = new Paragraph(" "+ lddveviec  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_veviec.setPaddingTop(0.5f*CONVERT);
			cell_veviec.setPaddingLeft(1.4f*CONVERT);
			cell_veviec.addElement(pxk);
			cell_veviec.setBorder(0);
			tableLDD.addCell(cell_veviec);
			
			document.add(tableLDD);				
			
			
			// HỌ TÊN NGƯỜI VẬN CHUYỂN
			PdfPTable tableVC = new PdfPTable(4);
			tableVC.setWidthPercentage(100);
			float[] withs1 =  { 9.5f*CONVERT, 6.0f*CONVERT, 5.5f*CONVERT, 5.0f*CONVERT };
			tableVC.setWidths(withs1);
			
			// NGƯỜI VC
			PdfPCell cell_ten = new PdfPCell();			
			pxk = new Paragraph(" "+ nguoivanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ten.setPaddingTop(0f*CONVERT);
			cell_ten.setPaddingLeft(4.7f*CONVERT);
			cell_ten.addElement(pxk);
			cell_ten.setBorder(0);
			tableVC.addCell(cell_ten);
			
			// SỐ HD
			PdfPCell cell_sohd = new PdfPCell();			
			pxk = new Paragraph(" "+ sohopdong  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_sohd.setPaddingTop(0f*CONVERT);
			cell_sohd.setPaddingLeft(3.2f*CONVERT);
			cell_sohd.addElement(pxk);
			cell_sohd.setBorder(0);	
			tableVC.addCell(cell_sohd);
			
			// NGÀY HỢP ĐỒNG
			String chuoi = "";
			if(ngayhopdong.trim().length() > 0)
			{
				String[] ngayhd = ngayhopdong.split("-");
				chuoi = ngayhd[2] + "/" + ngayhd[1] + "/" + ngayhd[0];
			}
			
			PdfPCell cell_ngayhd = new PdfPCell();			
			pxk = new Paragraph(" "+ chuoi  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_ngayhd.setPaddingTop(0f*CONVERT);
			cell_ngayhd.setPaddingLeft(2.3f*CONVERT);
			cell_ngayhd.addElement(pxk);
			cell_ngayhd.setBorder(0);	
			tableVC.addCell(cell_ngayhd);
			
			// PHƯƠNG TIỆN VC
			PdfPCell cell_pt = new PdfPCell();			
			pxk = new Paragraph(" "+ ptvanchuyen  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_pt.setPaddingTop(0f*CONVERT);
			cell_pt.setPaddingLeft(3.5f*CONVERT);
			cell_pt.addElement(pxk);
			cell_pt.setBorder(0);	
			tableVC.addCell(cell_pt);
			
			document.add(tableVC);							
								
			
			// XUẤT TẠI KHO  + NHẬP TẠI KHO		
			PdfPTable tableKHO = new PdfPTable(2);
			tableKHO.setWidthPercentage(100);			
			float[] withs3 =  { 13.0f*CONVERT, 13.0f*CONVERT };
			tableKHO.setWidths(withs3);
			
			
			// XUẤT TẠI KHO
			PdfPCell cell_khoxuat = new PdfPCell();			
			pxk = new Paragraph(" "+ khoxuat  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khoxuat.setPaddingTop(-0.15f*CONVERT);
			cell_khoxuat.setPaddingLeft(2.2f*CONVERT);
			cell_khoxuat.addElement(pxk);
			cell_khoxuat.setBorder(0);
			tableKHO.addCell(cell_khoxuat);
			
			
			// NHẬP TẠI KHO	
			PdfPCell cell_khonhap = new PdfPCell();			
			pxk = new Paragraph(" "+khonhan  , new Font(bf, 9, Font.NORMAL));
			pxk.setAlignment(Element.ALIGN_LEFT);
			pxk.setSpacingAfter(2);
			cell_khonhap.setPaddingTop(-0.15f*CONVERT);
			cell_khonhap.setPaddingLeft(2.2f*CONVERT);
			cell_khonhap.addElement(pxk);
			cell_khonhap.setBorder(0);	
			tableKHO.addCell(cell_khonhap);
			
			document.add(tableKHO);			
					
			
			
			// Table Content


			//String[] th = new String[]{ "STT", "Tên hàng hóa,dịch vụ", "Số kiểm soát", "Hạn dùng","Số kiện" ,"ĐVT", "Thực xuất", "Thực nhận","Đơn giá", "Thành tiền"};
			String[] th = new String[]{ " ", " ", " ", " "," " ," "," ", " "," "};
			
			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setSpacingBefore(2.2f*CONVERT);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			//float[] withssp =  { 20.0f, 145.0f, 22.6f, 75.6f, 24.4f, 56.0f, 56.0f,60.0f,84.0f  };
			float[] withssp =  { 20.0f, 137.0f, 55.6f, 50.6f, 24.4f, 56.0f, 56.0f,60.0f,84.0f  }; 

			table.setWidths(withssp);

			PdfPCell cells = new PdfPCell();
			
			double totalTienTruocVAT=0;
			double totalThueGTGT=0;
			double totalSotienTT=0;
			
			double tongslnhan=0;
			double tongslchuyen=0;
			
			String query =
			"	select d.ten as tenSP, case when c.solo = 'NA' then '' else isnull(Rtrim(c.solo),'') end as solo, isnull(c.ngayhethan,'') as handung, e.DONVI,c.soluong as SOLUONG, b.DONGIA  "+
			"	from ERP_CHUYENKHONPP a " +
			"   inner join ERP_CHUYENKHONPP_SANPHAM b on a.pk_seq=b.chuyenkho_fk "+	
			"   inner join ERP_CHUYENKHONPP_SANPHAM_CHITIET c on a.pk_seq=c.chuyenkho_fk  and b.sanpham_fk= c.sanpham_fk " +
			"   inner join SANPHAM d on c.SANPHAM_FK = d.PK_SEQ " +
			"   inner join DONVIDOLUONG e on b.dvdl_fk = e.PK_SEQ "+			
			"	where a.PK_SEQ ="+ pxkBean.getId() +" ";
			
			
			System.out.println("[ERP_CHUYENKHO_SANPHAM1]"+query);
			
			ResultSet rsSP = db.get(query);
			
			int stt = 1;
			while(rsSP.next())
			{
				double soluongChuyen = rsSP.getDouble("soluong");
				tongslchuyen +=soluongChuyen;
				
				double soluongNhan= 0;
				
				double dongia = rsSP.getDouble("dongia");
				double thanhtien = soluongChuyen*dongia;	
						
				totalTienTruocVAT+=thanhtien;
				String chuoingaysd = "";
				if(rsSP.getString("handung").trim().length() > 0)
				{
					String[] hsd = rsSP.getString("handung").split("-");
					chuoingaysd = hsd[2] + "/" + hsd[1] + "/" + hsd[0] ;
				}
				
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("tenSP"), rsSP.getString("solo"), chuoingaysd, 
						rsSP.getString("DONVI"),formatter.format(soluongChuyen), " ", "" , "" };
				String[] arr = new String[] { Integer.toString(stt), rsSP.getString("tenSP"), rsSP.getString("solo")+"   "+ chuoingaysd, "",
						rsSP.getString("DONVI"),formatter.format(soluongChuyen), " ", "" , "" };


				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
					if (j <= 4){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					if(j == 0)
						cells.setPaddingLeft(0.9f*CONVERT);
					if(j == 1)
						cells.setPaddingLeft(0.8f*CONVERT);
					if(j == 2)
						cells.setPaddingLeft(-0.6f*CONVERT);
					
					if(j == 4)
						cells.setPaddingLeft(2.0f*CONVERT);
					if(j == 7)
						cells.setPaddingRight(-0.8f*CONVERT);
					if(j == 8)
						cells.setPaddingRight(-0.3f*CONVERT);
					
						
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);	
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				stt++;
				
			}
						
		// DÒNG TRỐNG
			
			// DONG TRONG
			int kk=0;
			while(kk < 11-stt)
			{
				String[] arr_bosung = new String[] { " ", " ", " ", " "," " ," ", " "," ", " "};
	
				for (int j = 0; j < 9; j++)
				{
					cells = new PdfPCell(new Paragraph(arr_bosung[j], new Font(bf, 9, Font.NORMAL)));
					cells.setVerticalAlignment(Element.ALIGN_BOTTOM);
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
					cells.setPadding(2.0f);
					cells.setFixedHeight(0.6f*CONVERT);
					cells.setBorder(0);
										
					table.addCell(cells);
				}
				
				kk++;
			}
			
		String[] arr = new String[] {" ", " "," "," "," ",formatter.format(tongslchuyen), " "," ", ""};//totalTienTruocVAT
			for (int j = 0; j < 9; j++)
			{
				cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.BOLD)));
				if (j <= 4){
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				}
				else{
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				
				
				if(j == 0)
					cells.setPaddingLeft(0.9f*CONVERT);
				if(j == 1)
					cells.setPaddingLeft(0.8f*CONVERT);
				if(j == 2)
					cells.setPaddingLeft(-0.6f*CONVERT);
				
				if(j == 4)
					cells.setPaddingLeft(2.0f*CONVERT);
				if(j == 7)
					cells.setPaddingRight(-0.8f*CONVERT);
				if(j == 8)
					cells.setPaddingRight(-0.3f*CONVERT);
				
				
				cells.setVerticalAlignment(Element.ALIGN_TOP);
				cells.setPaddingTop(-1.0f);	
				cells.setFixedHeight(0.6f*CONVERT);
				cells.setBorder(0);
				table.addCell(cells);
			}
			
																								
			document.add(table);
											
			document.close();
		
			
		} catch (Exception e)
		{
			System.out.println("115.Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}*/
}
