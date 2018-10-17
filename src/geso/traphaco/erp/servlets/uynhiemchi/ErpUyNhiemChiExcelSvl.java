package geso.traphaco.erp.servlets.uynhiemchi;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.doctien.DocTien;
import geso.traphaco.erp.beans.uynhiemchi.IErpUynhiemchi;
import geso.traphaco.erp.beans.uynhiemchi.imp.ErpUynhiemchi;
import geso.traphaco.erp.servlets.uynhiemchi.ErpUynhiemchiPdfSvl.HeaderAndFooter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpUyNhiemChiExcelSvl  extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
    public ErpUyNhiemChiExcelSvl() {
        super();
    }

    float CONVERT = 28.346457f;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		//String userTen = (String) session.getAttribute("userTen");  	

		 if (userId.length() == 0)
		    	userId = request.getParameter("userId");
			   
		String id = request.getParameter("id");
		String httt = request.getParameter("httt");
		String inchitiet = request.getParameter("inchitiet");
		String ctyId = (String)session.getAttribute("congtyId");
		String nppId = (String)session.getAttribute("nppId");
		 
		System.out.println("inchitiet:"+inchitiet);
		if(httt == null)
			httt = "";
			
		IErpUynhiemchi tthdBean = new ErpUynhiemchi(id);
		tthdBean.setHtttId(httt);
	    tthdBean.setUserId(userId);
	    tthdBean.setCtyId(ctyId);
	    
	    if(tthdBean.getHtttId().equals("100001"))
	    {
	    	tthdBean.DBclose();
	    	
	    	if(inchitiet.equals("0")){
		    	//KIỂM TRA NẾU MÃ NGÂN HÀNG TRẢ LÀ IVB THÌ CANH CHIỀU DỌC
		    	String query = 
					"	SELECT 	tthd.SOTAIKHOAN as SoTaiKhoan, isnull(nh.TEN, '') as traNganHang, isnull(nh.MA, '') as traNganHangMa \n"+ 
					"	FROM  	ERP_THANHTOANHOADON tthd  left join ERP_NGANHANG nh on tthd.nganhang_fk = nh.pk_seq \n"+   
					"   WHERE   tthd.pk_seq = '"+tthdBean.getId()+"' ";
					
					System.out.println("MA NGAN HANG__:"+query);
					dbutils db = new dbutils();
					ResultSet rs = db.get(query);
					
					String manganhangtra = "";
					try{
						if(rs!=null){
							while(rs.next()){
								manganhangtra = rs.getString("traNganHangMa");
							}
							rs.close();
						}
					}
					catch (Exception e){
						e.printStackTrace();
					}
				System.out.println("Mã ngân hàng trả : "+manganhangtra);
				
				query =   " select isnull(LOAINCC,0) AS LOAINCC from ERP_NHACUNGCAP where PK_SEQ in"
						+ " ( select NCC_FK from ERP_THANHTOANHOADON where PK_Seq ="+tthdBean.getId()+") ";
				
				String maloaincc = "";
				rs = db.get(query);
				try{
					if(rs!=null){
						if(rs.next()){
							maloaincc = rs.getString("LOAINCC");
						}
						rs.close();
					}
				}
				catch (Exception e){
					e.printStackTrace();
				}
				
				if(maloaincc.equals("100011")){
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=UyNhiemChi"+tthdBean.getSohoadon()+".xlsm");
			        try {
			        	OutputStream outstream = response.getOutputStream();
						this.CreateNopNganSach(outstream, tthdBean);
						return;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				
				if(manganhangtra.equals("IVB")){
					
					
				} else if (manganhangtra.equals("ACB"))
				{
					
				}
				else if (manganhangtra.equals("SACOMBANK")) 
				{
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=UyNhiemChi"+tthdBean.getSohoadon()+".xlsx");
			        try {
			        	OutputStream outstream = response.getOutputStream();
						this.CreateUyNhiemChiVCB(outstream, tthdBean);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if (manganhangtra.equals("VCB")) 
				{
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=UyNhiemChi"+tthdBean.getSohoadon()+".xlsx");
			        try {
			        	OutputStream outstream = response.getOutputStream();
						this.CreateUyNhiemChiVCB(outstream, tthdBean);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if (manganhangtra.equals("ANBINH")) 
				{
					
				}
				else if (manganhangtra.equals("VIETIN")) 
				{
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=UyNhiemChi"+tthdBean.getSohoadon()+".xlsx");
			        try {
			        	OutputStream outstream = response.getOutputStream();
						this.CreatePhieuChiVIETINBANK(outstream, tthdBean,nppId);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if (manganhangtra.equals("BIDV"))
				{
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=UyNhiemChi"+tthdBean.getSohoadon()+".xlsx");
			        try {
			        	OutputStream outstream = response.getOutputStream();
						this.CreatePhieuChiBIDV(outstream, tthdBean);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	    	} else if(inchitiet.equals("2")){
	    		// NOP NGAN SACH
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=NopNganSach"+tthdBean.getSohoadon()+".xlsx");
		        try {
		        	OutputStream outstream = response.getOutputStream();
					this.CreateNopNganSach(outstream, tthdBean);
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			
	    	}
	    }
	    else
	    {
	    	
	    	response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=Thanhtoan"+tthdBean.getSohoadon()+".xlsm");
	        try {
	        	OutputStream outstream = response.getOutputStream();
				this.CreatePhieuChiExcel(outstream, tthdBean);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		
	}
	private void CreateNopNganSach(OutputStream outstream,IErpUynhiemchi tthdBean){

		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2 = new DecimalFormat("###########"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
			
			String tencty = "", masothue = "", diachi = "", quan = "", thanhpho = "";
			String soTk = "";
			String sotientt = "";
			long sotien = 0;
			int loaitt = 0;
			String tienbangchu = "";
			String noidungtt = "";
			String ngayghinhan = "";
			
			String traTen = "", traStk = "", traNganHang = "", traNganHangMa = "",  traDiaChi = "", traChiNhanh = "";
			String nhanTen = "", nhanStk = "", nhanNganHang = "", nhanDiaChi = "", nhanChiNhanh = "" , nhanvien_fk, donvithuchien = "" , pk_seq ="";

			dbutils db = new dbutils();
			String query = "SELECT TEN, MASOTHUE, DIACHI, QUAN, THANHPHO FROM ERP_CONGTY WHERE PK_SEQ = " + tthdBean.getCtyId() ;
			ResultSet rs = db.get(query);
			try{
				if(rs != null){
					rs.next();
					tencty = rs.getString("TEN");
					masothue = rs.getString("MASOTHUE");
					diachi = rs.getString("DIACHI");
					quan = rs.getString("QUAN");
					thanhpho = rs.getString("THANHPHO");
					rs.close();
					
				}
				
			}catch(java.sql.SQLException e){}
			
			query =  		" select tthd.SOTAIKHOAN as SoTaiKhoan, isnull(nh.TEN, '') as traNganHang, isnull(nh.MA, '') as traNganHangMa, isnull(cn.TEN, '') as traChiNhanh, isnull((select ten from erp_congty where pk_seq = 100000), '') as congty, \n" +
							"		 isnull(tthd.LOAITHANHTOAN,0) as LOAITHANHTOAN, \n " +
							" 		 isnull(ncc.TEN,'') as nccTen, isnull(ncc.DIACHI,'') as nccDiaChi, ncc.sotaikhoan as nccStk, isnull(nccnh.TEN, '') AS nccNganHang, isnull(ncccn.TEN, '') AS nccChiNhanh, \n " +
							" 		 isnull(nv.TEN,'') as nvTen, isnull(nv.DIACHI,'') as nvDiaChi, nv.sotaikhoan as nvStk, isnull(nvnh.TEN, '') AS nvNganHang, isnull(nvcn.TEN, '') AS nvChiNhanh, \n " +
							" 		 tthd.SOTIENTT as SOTIENTT, tthd.NOIDUNGTT as NOIDUNGTT, \n " +
							" 		 tthd.NGAYGHINHAN as NGAYGHINHAN, \n" +
							"		 tthd.nhanvien_fk  , \n" +
							"   	 tthd.pk_seq , \n " +
							" 		 isnull(dv.ten, '') as donvithuchien, \n " +
							"		 isnull((select DIENTHOAI from erp_congty where pk_seq = 100005), '') as dienthoai \n"+  
							" from ERP_THANHTOANHOADON tthd \n " +
							" left join ERP_NGANHANG nh on tthd.nganhang_fk = nh.pk_seq  \n" +
							" left join ERP_CHINHANH cn on tthd.chinhanh_fk = cn.pk_seq  \n" +
							" left join ERP_NHACUNGCAP ncc on tthd.ncc_fk = ncc.pk_seq \n" +
							" left join ERP_NGANHANG nccnh on ncc.nganhang_fk = nccnh.pk_seq  \n" +
							" left join ERP_CHINHANH ncccn on ncc.chinhanh_fk = ncccn.pk_seq  \n" +
							" left join ERP_NHANVIEN nv on tthd.NHANVIEN_FK = nv.PK_SEQ \n" +
							" left join ERP_NGANHANG nvnh on nv.nganhang_fk = nvnh.pk_seq  \n" +
							" left join ERP_CHINHANH nvcn on nv.chinhanh_fk = nvcn.pk_seq  \n" +
							" left join ERP_DONVITHUCHIEN dv on dv.pk_seq = nv.dvth_fk \n" +
							" where tthd.pk_seq = '"+tthdBean.getId()+"' \n";
			System.out.println("[ErpTTHoaDonPdfSvl.XuatExcel] query 1 = " + query);
			
			rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					while(rs.next())
					{
						ngayghinhan = rs.getString("NGAYGHINHAN");
						loaitt = rs.getInt("LOAITHANHTOAN");
						noidungtt = rs.getString("NOIDUNGTT");
						sotientt = formatter.format(rs.getDouble("SOTIENTT"));
						
						traTen = rs.getString("congty");
						traStk = rs.getString("SoTaiKhoan");
						traNganHang = rs.getString("traNganHang");
						traNganHangMa = rs.getString("traNganHangMa");
						traChiNhanh = rs.getString("traChiNhanh");
						//traDiaChi = rs.getString("traDiaChi");
						 nhanvien_fk = rs.getString("nhanvien_fk");
						 donvithuchien = rs.getString("donvithuchien");
						pk_seq = rs.getString("pk_seq");
						 
						if(nhanvien_fk != null) {
							nhanTen = rs.getString("nvTen");
							nhanDiaChi = rs.getString("nvDiaChi");
							nhanStk = rs.getString("nvStk");
							nhanNganHang = rs.getString("nvNganHang");
						} else {
							nhanTen = rs.getString("nccTen");
							nhanDiaChi = rs.getString("nccDiaChi");
							nhanStk = rs.getString("nccStk");
							nhanNganHang = rs.getString("nccNganHang");
						}
						try {
							sotien = Long.parseLong(formatter2.format(rs.getDouble("SOTIENTT")));
							tienbangchu = DocTien.docTien(sotien);
						} catch (Exception e) {
							e.printStackTrace();
						}
					
					}
				} 
				catch (SQLException e) 
				{
					System.out.println("[ErpTTHoaDonPdfSvl.CreateUyNhiemChi] Exception: " + e.getMessage());
				}
			}
			
			
			db.shutDown();
			
			
			FileInputStream fstream;
			Cell cell = null;
			
			fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\GiayNopTien.xlsx");
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007);
			workbook.open(fstream);
			
			Worksheets worksheets = workbook.getWorksheets();
			
			//Sheet 1
		    Worksheet worksheet1 = worksheets.getSheet(0);
		    Cells cells1 = worksheet1.getCells();
		    
		    cell = cells1.getCell("C6"); cell.setValue(tencty);
		    cell = cells1.getCell("C7"); cell.setValue(masothue);
		    cell = cells1.getCell("F7"); cell.setValue(diachi);
		    cell = cells1.getCell("E8"); cell.setValue("Quận/Huyện: " + quan);
		    cell = cells1.getCell("H8"); cell.setValue("Tỉnh/Thành: " + thanhpho);
		    
		    cell = cells1.getCell("I23"); cell.setValue(formatter.format(sotien)); 
		    cell = cells1.getCell("D24"); cell.setValue(tienbangchu); 
	
			workbook.save(outstream);
			fstream.close();
		
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
	
	}
	
	private void CreateUyNhiemChiVCB(OutputStream outstream,IErpUynhiemchi tthdBean){
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2 = new DecimalFormat("###########"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
			
			String soTk = "";
			String sotientt = "";
			long sotien = 0;
			int loaitt = 0;
			String tienbangchu = "";
			String noidungtt = "";
			String ngayghinhan = "";
			
			String traTen = "", traStk = "", traNganHang = "", traNganHangMa = "",  traDiaChi = "", traChiNhanh = "";
			String nhanTen = "", nhanStk = "", nhanNganHang = "", nhanDiaChi = "", nhanChiNhanh = "" , nhanvien_fk, donvithuchien = "" , pk_seq ="";

			dbutils db = new dbutils();
			String query =  " select tthd.SOTAIKHOAN as SoTaiKhoan, isnull(nh.TEN, '') as traNganHang, isnull(nh.MA, '') as traNganHangMa, isnull(cn.TEN, '') as traChiNhanh, isnull((select ten from erp_congty where pk_seq = 100000), '') as congty, \n" +
							"		 isnull(tthd.LOAITHANHTOAN,0) as LOAITHANHTOAN, isnull((select diachi from erp_congty where pk_seq = 100000), '') as traDiaChi, \n " +
							" 		 isnull(ncc.TEN,'') as nccTen, isnull(ncc.DIACHI,'') as nccDiaChi, ncc.sotaikhoan as nccStk, isnull(nccnh.TEN, '') AS nccNganHang, isnull(ncccn.TEN, '') AS nccChiNhanh, \n " +
							" 		 isnull(nv.TEN,'') as nvTen, isnull(nv.DIACHI,'') as nvDiaChi, nv.sotaikhoan as nvStk, isnull(nvnh.TEN, '') AS nvNganHang, isnull(nvcn.TEN, '') AS nvChiNhanh, \n " +
							" 		 tthd.SOTIENTT as SOTIENTT, tthd.NOIDUNGTT as NOIDUNGTT, \n " +
							" 		 tthd.NGAYGHINHAN as NGAYGHINHAN, \n" +
							"		 tthd.nhanvien_fk  , \n" +
							"   	 tthd.pk_seq , \n " +
							" 		 isnull(dv.ten, '') as donvithuchien, \n " +
							"		 isnull((select DIENTHOAI from erp_congty where pk_seq = 100005), '') as dienthoai \n"+  
							" from ERP_THANHTOANHOADON tthd \n " +
							" left join ERP_NGANHANG nh on tthd.nganhang_fk = nh.pk_seq  \n" +
							" left join ERP_CHINHANH cn on tthd.chinhanh_fk = cn.pk_seq  \n" +
							" left join ERP_NHACUNGCAP ncc on tthd.ncc_fk = ncc.pk_seq \n" +
							" left join ERP_NGANHANG nccnh on ncc.nganhang_fk = nccnh.pk_seq  \n" +
							" left join ERP_CHINHANH ncccn on ncc.chinhanh_fk = ncccn.pk_seq  \n" +
							" left join ERP_NHANVIEN nv on tthd.NHANVIEN_FK = nv.PK_SEQ \n" +
							" left join ERP_NGANHANG nvnh on nv.nganhang_fk = nvnh.pk_seq  \n" +
							" left join ERP_CHINHANH nvcn on nv.chinhanh_fk = nvcn.pk_seq  \n" +
							" left join ERP_DONVITHUCHIEN dv on dv.pk_seq = nv.dvth_fk \n" +
							" where tthd.pk_seq = '"+tthdBean.getId()+"' \n";
			System.out.println("[ErpTTHoaDonPdfSvl.XuatExcel] query 1 = " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					while(rs.next())
					{
						ngayghinhan = rs.getString("NGAYGHINHAN");
						loaitt = rs.getInt("LOAITHANHTOAN");
						noidungtt = rs.getString("NOIDUNGTT");
						sotientt = formatter.format(rs.getDouble("SOTIENTT"));
						
						traTen = rs.getString("congty");
						traStk = rs.getString("SoTaiKhoan");
						traNganHang = rs.getString("traNganHang");
						traNganHangMa = rs.getString("traNganHangMa");
						traChiNhanh = rs.getString("traChiNhanh");
						traDiaChi = rs.getString("traDiaChi");
						 nhanvien_fk = rs.getString("nhanvien_fk");
						 donvithuchien = rs.getString("donvithuchien");
						pk_seq = rs.getString("pk_seq");
						 
						if(nhanvien_fk != null) {
							nhanTen = rs.getString("nvTen");
							nhanDiaChi = rs.getString("nvDiaChi");
							nhanStk = rs.getString("nvStk");
							nhanNganHang = rs.getString("nvNganHang");
							nhanChiNhanh = rs.getString("nvChiNhanh");
						} else {
							nhanTen = rs.getString("nccTen");
							nhanDiaChi = rs.getString("nccDiaChi");
							nhanStk = rs.getString("nccStk");
							nhanNganHang = rs.getString("nccNganHang");
							nhanChiNhanh = rs.getString("nccChiNhanh");
						}
						try {
							sotien = Long.parseLong(formatter2.format(rs.getDouble("SOTIENTT")));
							tienbangchu = DocTien.docTien(sotien);
						} catch (Exception e) {
							e.printStackTrace();
						}
					
					}
				} 
				catch (SQLException e) 
				{
					System.out.println("[ErpTTHoaDonPdfSvl.CreateUyNhiemChi] Exception: " + e.getMessage());
				}
			}
			
			db.shutDown();
			
			
			FileInputStream fstream;
			Cell cell = null;
			
			fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\UyNhiemChiVCB1.xlsx");
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007);
			workbook.open(fstream);
			
			Worksheets worksheets = workbook.getWorksheets();
			
			//Sheet 1
		    Worksheet worksheet1 = worksheets.getSheet(0);
		    Cells cells1 = worksheet1.getCells();
		    
		    cell = cells1.getCell("L5");cell.setValue(traStk);
		    cell = cells1.getCell("L6"); cell.setValue(traTen); 
		    cell = cells1.getCell("L7"); cell.setValue(traDiaChi); 
		    cell = cells1.getCell("L8"); cell.setValue(traNganHang+" "+traChiNhanh); 
		    cell = cells1.getCell("L9"); cell.setValue(nhanStk); 
		    cell = cells1.getCell("L10"); cell.setValue(nhanTen); 
		    cell = cells1.getCell("L11"); cell.setValue(nhanDiaChi); 
		    cell = cells1.getCell("L12"); cell.setValue(nhanNganHang+" "+nhanChiNhanh); 
		    cell = cells1.getCell("F4"); cell.setValue(formatter.format(sotien)); 
		    cell = cells1.getCell("F5"); cell.setValue(tienbangchu); 
		    cell = cells1.getCell("E9"); cell.setValue(noidungtt); 
			workbook.save(outstream);
			fstream.close();
		
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	private void CreatePhieuChiVIETINBANK(OutputStream outstream,IErpUynhiemchi tthdBean,String nppId){
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2 = new DecimalFormat("###########"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
			
			String soTk = "";
			String sotientt = "";
			long sotien = 0;
			int loaitt = 0;
			String tienbangchu = "";
			String noidungtt = "";
			String ngayghinhan = "";
			
			String traTen = "", traStk = "", traNganHang = "", traNganHangMa = "",  traDiaChi = "", traChiNhanh = "";
			String nhanTen = "", nhanStk = "", nhanNganHang = "", nhanDiaChi = "", nhanChiNhanh = "" , nhanvien_fk, donvithuchien = "" , pk_seq ="";
			String sochungtu="";
			dbutils db = new dbutils();
			String query =  " select tthd.SOCHUNGTU,tthd.SOTAIKHOAN as SoTaiKhoan, isnull(nh.TEN, '') as traNganHang, isnull(nh.MA, '') as traNganHangMa, isnull(cn.TEN, '') as traChiNhanh, isnull((select ten from erp_congty where pk_seq = 100000), '') as congty, \n" +
							"		 isnull(tthd.LOAITHANHTOAN,0) as LOAITHANHTOAN, \n " +
							" 		 isnull(ncc.TEN,'') as nccTen, isnull(ncc.DIACHI,'') as nccDiaChi, ncc.sotaikhoan as nccStk, isnull(nccnh.TEN, '') AS nccNganHang, isnull(ncccn.TEN, '') AS nccChiNhanh, \n " +
							" 		 isnull(nv.TEN,'') as nvTen, isnull(nv.DIACHI,'') as nvDiaChi, nv.sotaikhoan as nvStk, isnull(nvnh.TEN, '') AS nvNganHang, isnull(nvcn.TEN, '') AS nvChiNhanh, \n " +
							" 		 tthd.SOTIENTT as SOTIENTT, tthd.NOIDUNGTT as NOIDUNGTT, \n " +
							" 		 tthd.NGAYGHINHAN as NGAYGHINHAN, \n" +
							"		 tthd.nhanvien_fk  , \n" +
							"   	 tthd.pk_seq , \n " +
							" 		 isnull(dv.ten, '') as donvithuchien, \n " +
							"		 isnull((select DIENTHOAI from erp_congty where pk_seq = 100005), '') as dienthoai \n"+  
							" from ERP_THANHTOANHOADON tthd \n " +
							" left join ERP_NGANHANG nh on tthd.nganhang_fk = nh.pk_seq  \n" +
							" left join ERP_CHINHANH cn on tthd.chinhanh_fk = cn.pk_seq  \n" +
							" left join ERP_NHACUNGCAP ncc on tthd.ncc_fk = ncc.pk_seq \n" +
							" left join ERP_NGANHANG nccnh on ncc.nganhang_fk = nccnh.pk_seq  \n" +
							" left join ERP_CHINHANH ncccn on ncc.chinhanh_fk = ncccn.pk_seq  \n" +
							" left join ERP_NHANVIEN nv on tthd.NHANVIEN_FK = nv.PK_SEQ \n" +
							" left join ERP_NGANHANG nvnh on nv.nganhang_fk = nvnh.pk_seq  \n" +
							" left join ERP_CHINHANH nvcn on nv.chinhanh_fk = nvcn.pk_seq  \n" +
							" left join ERP_DONVITHUCHIEN dv on dv.pk_seq = nv.dvth_fk \n" +
							" where tthd.pk_seq = '"+tthdBean.getId()+"' \n";
			System.out.println("[ErpTTHoaDonPdfSvl.XuatExcel] query 1 = " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					while(rs.next())
					{
						sochungtu = rs.getString("sochungtu");
						ngayghinhan = rs.getString("NGAYGHINHAN");
						loaitt = rs.getInt("LOAITHANHTOAN");
						noidungtt = rs.getString("NOIDUNGTT");
						sotientt = formatter.format(rs.getDouble("SOTIENTT"));
						
						traTen = rs.getString("congty");
						traStk = rs.getString("SoTaiKhoan");
						traNganHang = rs.getString("traNganHang");
						traNganHangMa = rs.getString("traNganHangMa");
						traChiNhanh = rs.getString("traChiNhanh");
						//traDiaChi = rs.getString("traDiaChi");
						 nhanvien_fk = rs.getString("nhanvien_fk");
						 donvithuchien = rs.getString("donvithuchien");
						pk_seq = rs.getString("pk_seq");
						 
						if(nhanvien_fk != null) {
							nhanTen = rs.getString("nvTen");
							nhanDiaChi = rs.getString("nvDiaChi");
							nhanStk = rs.getString("nvStk");
							nhanNganHang = rs.getString("nvNganHang");
							nhanChiNhanh = rs.getString("nvChiNhanh");
						} else {
							nhanTen = rs.getString("nccTen");
							nhanDiaChi = rs.getString("nccDiaChi");
							nhanStk = rs.getString("nccStk");
							nhanNganHang = rs.getString("nccNganHang");
							nhanChiNhanh = rs.getString("nccChiNhanh");
						}
						try {
							sotien = Long.parseLong(formatter2.format(rs.getDouble("SOTIENTT")));
							tienbangchu = DocTien.docTien(sotien);
						} catch (Exception e) {
							
						}
						
					
					}
					rs.close();
				} 
				
				catch (SQLException e) 
				{
					System.out.println("[ErpTTHoaDonPdfSvl.CreateUyNhiemChi] Exception: " + e.getMessage());
				}
			}
			
			
			
			String sql =" SELECT  ISNULL( CT.TEN,NPP.TEN) AS TEN,   ISNULL( CT.DIACHI,NPP.DIACHIXHD)  AS DIACHI   "
					+ " \n FROM NHAPHANPHOI NPP LEFT JOIN ERP_CONGTY CT ON NPP.congty_fk=CT.PK_SEQ WHERE NPP.PK_SEQ ="+nppId+" ";
			System.out.println(" lay ten chi nhanh: "+sql );
			ResultSet rs1=db.get(sql);
			String tencongty="";
			String diachi="";
			try
			{
			if(rs1.next())
			{
				tencongty=rs1.getString("TEN");
				diachi=rs1.getString("DIACHI");
			}
			rs1.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		
			
			
			FileInputStream fstream;
			Cell cell = null;
			
//			fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\UNCVietTinBankNew1.xlsx");
			fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\UNCVietTinBankNew12.xlsx");
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007);
			workbook.open(fstream);			
			Worksheets worksheets = workbook.getWorksheets();
			
			
		
			
			//Sheet 1
		    Worksheet worksheet1 = worksheets.getSheet(0);
		    Cells cells1 = worksheet1.getCells();
		    cell = cells1.getCell("K6");cell.setValue(sochungtu);
		    cell = cells1.getCell("C9");cell.setValue(tencongty);
		    cell = cells1.getCell("C10");cell.setValue(diachi);
		  //  cell = cells1.getCell("C9");cell.setValue(traTen);
		      cell = cells1.getCell("C11");cell.setValue(traStk);
		      cell = cells1.getCell("J11"); cell.setValue(traNganHang + " - " + traChiNhanh);
		      cell = cells1.getCell("N12"); cell.setValue(traChiNhanh);
		      cell = cells1.getCell("C17");cell.setValue(tienbangchu);
		      cell = cells1.getCell("C16"); cell.setValue(formatter.format(sotien));
		      cell = cells1.getCell("C15");cell.setValue(nhanDiaChi);
		      //cell = cells1.getCell("G1"); cell.setValue(pk_seq); 
		      cell = cells1.getCell("C12"); cell.setValue(nhanTen); 
		      cell = cells1.getCell("N16"); cell.setValue(donvithuchien); 
		      cell = cells1.getCell("B20"); cell.setValue(noidungtt);
		      cell = cells1.getCell("C13");cell.setValue(nhanStk);
		      cell = cells1.getCell("K13");cell.setValue(nhanNganHang  + "-" + nhanChiNhanh );
		      cell = cells1.getCell("N20");cell.setValue(nhanChiNhanh);
		      
		      
		      cell = cells1.getCell("B54"); cell.setValue(noidungtt);
		     /* cell = cells1.getCell("C44");cell.setValue(traTen + " - " + traChiNhanh);
		      cell = cells1.getCell("C45");cell.setValue(traStk);*/
		     
		    
			workbook.save(outstream);
			fstream.close();
		db.shutDown();
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void CreatePhieuChiBIDV(OutputStream outstream,IErpUynhiemchi tthdBean){

		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2 = new DecimalFormat("###########"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
			
			String soTk = "";
			String sotientt = "";
			long sotien = 0;
			int loaitt = 0;
			String tienbangchu = "";
			String noidungtt = "";
			String ngayghinhan = "";
			
			String traTen = "", traStk = "", traNganHang = "", traNganHangMa = "",  traDiaChi = "", traChiNhanh = "";
			String nhanTen = "", nhanStk = "", nhanNganHang = "", nhanDiaChi = "", nhanChiNhanh = "" , nhanvien_fk, donvithuchien = "" , pk_seq ="";

			dbutils db = new dbutils();
			String query =  " select tthd.SOTAIKHOAN as SoTaiKhoan, isnull(nh.TEN, '') as traNganHang, isnull(nh.MA, '') as traNganHangMa, isnull(cn.TEN, '') as traChiNhanh, isnull((select ten from erp_congty where pk_seq = 100000), '') as congty, \n" +
							"		 isnull(tthd.LOAITHANHTOAN,0) as LOAITHANHTOAN, \n " +
							" 		 isnull(ncc.TEN,'') as nccTen, isnull(ncc.DIACHI,'') as nccDiaChi, ncc.sotaikhoan as nccStk, isnull(nccnh.TEN, '') AS nccNganHang, isnull(ncccn.TEN, '') AS nccChiNhanh, \n " +
							" 		 isnull(nv.TEN,'') as nvTen, isnull(nv.DIACHI,'') as nvDiaChi, nv.sotaikhoan as nvStk, isnull(nvnh.TEN, '') AS nvNganHang, isnull(nvcn.TEN, '') AS nvChiNhanh, \n " +
							" 		 tthd.SOTIENTT as SOTIENTT, tthd.NOIDUNGTT as NOIDUNGTT, \n " +
							" 		 tthd.NGAYGHINHAN as NGAYGHINHAN, \n" +
							"		 tthd.nhanvien_fk  , \n" +
							"   	 tthd.pk_seq , \n " +
							" 		 isnull(dv.ten, '') as donvithuchien, \n " +
							"		 isnull((select DIENTHOAI from erp_congty where pk_seq = 100005), '') as dienthoai \n"+  
							" from ERP_THANHTOANHOADON tthd \n " +
							" left join ERP_NGANHANG nh on tthd.nganhang_fk = nh.pk_seq  \n" +
							" left join ERP_CHINHANH cn on tthd.chinhanh_fk = cn.pk_seq  \n" +
							" left join ERP_NHACUNGCAP ncc on tthd.ncc_fk = ncc.pk_seq \n" +
							" left join ERP_NGANHANG nccnh on ncc.nganhang_fk = nccnh.pk_seq  \n" +
							" left join ERP_CHINHANH ncccn on ncc.chinhanh_fk = ncccn.pk_seq  \n" +
							" left join ERP_NHANVIEN nv on tthd.NHANVIEN_FK = nv.PK_SEQ \n" +
							" left join ERP_NGANHANG nvnh on nv.nganhang_fk = nvnh.pk_seq  \n" +
							" left join ERP_CHINHANH nvcn on nv.chinhanh_fk = nvcn.pk_seq  \n" +
							" left join ERP_DONVITHUCHIEN dv on dv.pk_seq = nv.dvth_fk \n" +
							" where tthd.pk_seq = '"+tthdBean.getId()+"' \n";
			System.out.println("[ErpTTHoaDonPdfSvl.XuatExcel] query 1 = " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					while(rs.next())
					{
						ngayghinhan = rs.getString("NGAYGHINHAN");
						loaitt = rs.getInt("LOAITHANHTOAN");
						noidungtt = rs.getString("NOIDUNGTT");
						sotientt = formatter.format(rs.getDouble("SOTIENTT"));
						
						traTen = rs.getString("congty");
						traStk = rs.getString("SoTaiKhoan");
						traNganHang = rs.getString("traNganHang");
						traNganHangMa = rs.getString("traNganHangMa");
						traChiNhanh = rs.getString("traChiNhanh");
						//traDiaChi = rs.getString("traDiaChi");
						 nhanvien_fk = rs.getString("nhanvien_fk");
						 donvithuchien = rs.getString("donvithuchien");
						pk_seq = rs.getString("pk_seq");
						 
						if(nhanvien_fk != null) {
							nhanTen = rs.getString("nvTen");
							nhanDiaChi = rs.getString("nvDiaChi");
							nhanStk = rs.getString("nvStk");
							nhanNganHang = rs.getString("nvNganHang");
							nhanChiNhanh = rs.getString("nvChiNhanh");
						} else {
							nhanTen = rs.getString("nccTen");
							nhanDiaChi = rs.getString("nccDiaChi");
							nhanStk = rs.getString("nccStk");
							nhanNganHang = rs.getString("nccNganHang");
							nhanChiNhanh = rs.getString("nvChiNhanh");
						}
						try {
							sotien = Long.parseLong(formatter2.format(rs.getDouble("SOTIENTT")));
							tienbangchu = DocTien.docTien(sotien);
						} catch (Exception e) {
							
						}
					
					}
				} 
				catch (SQLException e) 
				{
					System.out.println("[ErpTTHoaDonPdfSvl.CreateUyNhiemChi] Exception: " + e.getMessage());
				}
			}
			
			db.shutDown();
			
			
			FileInputStream fstream;
			Cell cell = null;
			
			fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\UyNhiemChiBIDV1.xlsx");
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007);
			workbook.open(fstream);			
			Worksheets worksheets = workbook.getWorksheets();
			
			//Sheet 1
		    Worksheet worksheet1 = worksheets.getSheet(0);
		    Cells cells1 = worksheet1.getCells();
		    cell = cells1.getCell("C15");cell.setValue(traTen);
		    cell = cells1.getCell("B19");cell.setValue(traStk);
		    cell = cells1.getCell("B20"); cell.setValue(traNganHang);
		    cell = cells1.getCell("K3");cell.setValue(tienbangchu);
		    cell = cells1.getCell("K2"); cell.setValue(formatter.format(sotien));
		    //cell = cells1.getCell("G1"); cell.setValue(pk_seq); 
		    cell = cells1.getCell("K5"); cell.setValue(nhanTen); 
		    //cell = cells1.getCell("N15"); cell.setValue(donvithuchien); 
		    cell = cells1.getCell("K4"); cell.setValue(noidungtt);
		    cell = cells1.getCell("H19");cell.setValue(nhanStk);
		    cell = cells1.getCell("H20");cell.setValue(nhanNganHang);
		     
		    
			workbook.save(outstream);
			fstream.close();
		
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
	
	}
	private void CreatePhieuChiExcel(OutputStream outstream,IErpUynhiemchi tthdBean) {
		// TODO Auto-generated method stub
		
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			NumberFormat formatter2 = new DecimalFormat("###########"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
			
			String soTk = "";
			String sotientt = "";
			long sotien = 0;
			int loaitt = 0;
			String tienbangchu = "";
			String noidungtt = "";
			String ngayghinhan = "";
			
			String traTen = "", traStk = "", traNganHang = "", traNganHangMa = "",  traDiaChi = "", traChiNhanh = "";
			String nhanTen = "", nhanStk = "", nhanNganHang = "", nhanDiaChi = "", nhanChiNhanh = "" , nhanvien_fk, donvithuchien = "" , pk_seq ="";

			dbutils db = new dbutils();
			String query =  " select tthd.SOTAIKHOAN as SoTaiKhoan, isnull(nh.TEN, '') as traNganHang, isnull(nh.MA, '') as traNganHangMa, isnull(cn.TEN, '') as traChiNhanh, isnull((select ten from erp_congty where pk_seq = 100000), '') as congty, \n" +
							"		 isnull(tthd.LOAITHANHTOAN,0) as LOAITHANHTOAN, \n " +
							" 		 isnull(ncc.TEN,'') as nccTen, isnull(ncc.DIACHI,'') as nccDiaChi, ncc.sotaikhoan as nccStk, isnull(nccnh.TEN, '') AS nccNganHang, isnull(ncccn.TEN, '') AS nccChiNhanh, \n " +
							" 		 isnull(nv.TEN,'') as nvTen, isnull(nv.DIACHI,'') as nvDiaChi, nv.sotaikhoan as nvStk, isnull(nvnh.TEN, '') AS nvNganHang, isnull(nvcn.TEN, '') AS nvChiNhanh, \n " +
							" 		 tthd.SOTIENTT as SOTIENTT, tthd.NOIDUNGTT as NOIDUNGTT, \n " +
							" 		 tthd.NGAYGHINHAN as NGAYGHINHAN, \n" +
							"		 tthd.nhanvien_fk  , \n" +
							"   	 tthd.pk_seq , \n " +
							" 		 isnull(dv.ten, '') as donvithuchien, \n " +
							"		 isnull((select DIENTHOAI from erp_congty where pk_seq = 100005), '') as dienthoai \n"+  
							" from ERP_THANHTOANHOADON tthd \n " +
							" left join ERP_NGANHANG nh on tthd.nganhang_fk = nh.pk_seq  \n" +
							" left join ERP_CHINHANH cn on tthd.chinhanh_fk = cn.pk_seq  \n" +
							" left join ERP_NHACUNGCAP ncc on tthd.ncc_fk = ncc.pk_seq \n" +
							" left join ERP_NGANHANG nccnh on ncc.nganhang_fk = nccnh.pk_seq  \n" +
							" left join ERP_CHINHANH ncccn on ncc.chinhanh_fk = ncccn.pk_seq  \n" +
							" left join ERP_NHANVIEN nv on tthd.NHANVIEN_FK = nv.PK_SEQ \n" +
							" left join ERP_NGANHANG nvnh on nv.nganhang_fk = nvnh.pk_seq  \n" +
							" left join ERP_CHINHANH nvcn on nv.chinhanh_fk = nvcn.pk_seq  \n" +
							" left join ERP_DONVITHUCHIEN dv on dv.pk_seq = nv.dvth_fk \n" +
							" where tthd.pk_seq = '"+tthdBean.getId()+"' \n";
			System.out.println("[ErpTTHoaDonPdfSvl.XuatExcel] query 1 = " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					while(rs.next())
					{
						ngayghinhan = rs.getString("NGAYGHINHAN");
						loaitt = rs.getInt("LOAITHANHTOAN");
						noidungtt = rs.getString("NOIDUNGTT");
						sotientt = formatter.format(rs.getDouble("SOTIENTT"));
						
						traTen = rs.getString("congty");
						traStk = rs.getString("SoTaiKhoan");
						traNganHang = rs.getString("traNganHang");
						traNganHangMa = rs.getString("traNganHangMa");
						traChiNhanh = rs.getString("traChiNhanh");
						//traDiaChi = rs.getString("traDiaChi");
						 nhanvien_fk = rs.getString("nhanvien_fk");
						 donvithuchien = rs.getString("donvithuchien");
						pk_seq = rs.getString("pk_seq");
						 
						if(nhanvien_fk != null) {
							nhanTen = rs.getString("nvTen");
							nhanDiaChi = rs.getString("nvDiaChi");
							nhanStk = rs.getString("nvStk");
							nhanNganHang = rs.getString("nvNganHang");
						} else {
							nhanTen = rs.getString("nccTen");
							nhanDiaChi = rs.getString("nccDiaChi");
							nhanStk = rs.getString("nccStk");
							nhanNganHang = rs.getString("nccNganHang");
						}
						try {
							sotien = Long.parseLong(formatter2.format(rs.getDouble("SOTIENTT")));
							tienbangchu = DocTien.docTien(sotien);
						} catch (Exception e) {
							
						}
					
					}
				} 
				catch (SQLException e) 
				{
					System.out.println("[ErpTTHoaDonPdfSvl.CreateUyNhiemChi] Exception: " + e.getMessage());
				}
			}
			
			db.shutDown();
			
			
			FileInputStream fstream;
			Cell cell = null;
			
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ThanhToanTienMat.xlsx");
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007);
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			Worksheets worksheets = workbook.getWorksheets();
			
			//Sheet 1
		    Worksheet worksheet1 = worksheets.getSheet("inchi");
		    Cells cells1 = worksheet1.getCells();
	
		    cell = cells1.getCell("G1"); cell.setValue(pk_seq); 
		    cell = cells1.getCell("E6"); cell.setValue(nhanTen); 
		    cell = cells1.getCell("E7"); cell.setValue(donvithuchien); 
		    cell = cells1.getCell("D8"); cell.setValue(noidungtt); 
		    cell = cells1.getCell("E9"); cell.setValue(sotien); 
		    cell = cells1.getCell("C10"); cell.setValue(tienbangchu + "./."); 
		    
			workbook.save(outstream);
			fstream.close();
		
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
}
