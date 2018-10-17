package geso.dms.center.util;

import geso.traphaco.erp.db.sql.dbutils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Style;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ImportChitien extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ImportChitien() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		doPost(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/xlsm");
		response.setHeader("Content-Disposition", "attachment; filename=Chi_tien_1_2_3_2014.xlsm");
				
		try{
			ImportData(request, response);
		
		}catch (Exception e) 
        {
			System.out.println(e.toString());
		}

	}
	

	private boolean ImportData(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		dbutils db = new dbutils();
		OutputStream out = response.getOutputStream();
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\Chi_tien_1_2_3_2014_THEMMOI.xlsm");
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		Worksheets worksheets = workbook.getWorksheets();
		try{
			
			Worksheet worksheet1 = worksheets.getSheet("phatsinh");
			//KiemtraTT(db, worksheet1);
			//TaoChitien_New(db,worksheets, worksheet1);
			
		 	 TaoChitien_ThemBangMoi(db,worksheets, worksheet1); 
			
//			Worksheet worksheet2 = worksheets.getSheet("hoadon");
			
//			TaoChitien_Hoadon(db, worksheet2, "1");
//			TaoChitien_Hoadon(db, worksheet2, "2");
			
		}catch(Exception e){System.out.println(e.toString());}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	
	private void KiemtraTT(dbutils db, Worksheet worksheet){
		Cells cells = worksheet.getCells();
		
		Cell cell;
		Style style;	
		String ngay1, ngay2;
		double tongtien1_VND, tongtien1_NT; 
		double tongtien2_VND, tongtien2_NT;
		String ttId="";
		String nccId="";
		String tkco;
		String query;
		
		try{
		//db.getConnection().setAutoCommit(false);		
			ResultSet rs ;
		for (int row = 8; row <= 1428; row ++){
			//System.out.println(row);
			nccId = "";
			ngay1 = "";
			tongtien2_VND = 0;
			tongtien2_NT = 0;
			ttId = "";
			tkco = "";
			
			cell = cells.getCell("B" + row);
			nccId = cell.getStringValue();
			
			cell = cells.getCell("D" + row);
			ngay1 = cell.getStringValue();
					
			cell = cells.getCell("J" + row);
			tkco = cell.getStringValue();
			 
			
			if(tkco.indexOf("112") >= 0 ){
	 
				cell = cells.getCell("K" + row);
				if(cell.getStringValue().trim().length() > 0){
					tongtien1_VND = Double.parseDouble(cell.getStringValue().trim().replaceAll(",", ""));
				}else{
					tongtien1_VND = 0;
				}

				cell = cells.getCell("M" + row);
				if(cell.getStringValue().trim().length() > 0){
					tongtien1_NT = Double.parseDouble(cell.getStringValue().trim().replaceAll(",", ""));
				}else{
					tongtien1_NT = 0;
				}

				query = " SELECT TT.PK_SEQ, HD.SOHOADON, TT.NGAYGHINHAN, ISNULL(TT.TIENTE_FK, 100000) AS TIENTE_FK, " +  
						" CASE WHEN ISNULL(TT.TIENTE_FK, 100000) <> 100000 THEN ISNULL(TT.SOTIENHDNT, 0) ELSE 0 END AS SOTIENHDNT , " +
						" CASE WHEN ISNULL(TT.TIENTE_FK, 100000) = 100000 THEN ISNULL(TT.SOTIENHD, 0) ELSE 0 END AS SOTIENHDVND, " +
						" CASE WHEN ISNULL(TT.TRICHPHI, '0') = '0' THEN ISNULL(TT.PHINGANHANGNT, 0) ELSE ISNULL(TT.PHINGANHANG, 0) END AS PHINGANHANG " +
						" FROM ERP_THANHTOANHOADON TT " +
						" INNER JOIN ERP_THANHTOANHOADON_HOADON TT_HD ON TT_HD.THANHTOANHD_FK = TT.PK_SEQ " +
						" INNER JOIN ERP_HOADONNCC HD ON HD.PK_SEQ = TT_HD.HOADON_FK " +
						" WHERE TT.NCC_FK = " + nccId + " AND TT.NGAYGHINHAN = '" + ngay1 + "'";

			//	System.out.println(query);
			
				  rs = db.get(query);
			 
					if(rs.next()){
						ngay2 = rs.getString("NGAYGHINHAN");
						ttId = rs.getString("TIENTE_FK");
						tongtien2_VND = rs.getDouble("SOTIENHDVND");
						tongtien2_NT = rs.getDouble("SOTIENHDNT");
							 
						if(ttId.equals("100000")){

							if(tongtien1_VND != tongtien2_VND){
								cell = cells.getCell("A" + row);
								cell.setValue("Sai số tiền: Id = " + rs.getString("PK_SEQ") + " : " + tongtien2_VND);
								style = cell.getStyle();
								style.setNumber(2);
								cell.setStyle(style);

							}
						
						}else{
						
							if(tongtien1_NT != tongtien2_NT){
								cell = cells.getCell("A" + row);
								cell.setValue("Sai số tiền: Id = " + rs.getString("PK_SEQ") + " : "  + tongtien2_NT);
								style = cell.getStyle();
								style.setNumber(2);
								cell.setStyle(style);
							}
 
						}
						
						if(!ngay2.equals(ngay1)){
							cell = cells.getCell("A" + row);
							cell.setValue("Sai ngày: Id = " + rs.getString("PK_SEQ") + " : "  +ngay2);							
						}
				}else{
					
					query = " SELECT LOAITHANHTOAN ,TT.PK_SEQ,   TT.NGAYGHINHAN, ISNULL(TT.TIENTE_FK, 100000) AS TIENTE_FK, " +  
					" CASE WHEN ISNULL(TT.TIENTE_FK, 100000) <> 100000 THEN ISNULL(TT.SOTIENHDNT, 0) ELSE 0 END AS SOTIENHDNT , " +
					" CASE WHEN ISNULL(TT.TIENTE_FK, 100000) = 100000 THEN ISNULL(TT.SOTIENHD, 0) ELSE 0 END AS SOTIENHDVND, " +
					" CASE WHEN ISNULL(TT.TRICHPHI, '0') = '0' THEN ISNULL(TT.PHINGANHANGNT, 0) ELSE ISNULL(TT.PHINGANHANG, 0) END AS PHINGANHANG " +
					" FROM ERP_THANHTOANHOADON TT " +
					" WHERE TT.NCC_FK = " + nccId + " AND TT.NGAYGHINHAN = '" + ngay1 + "'";
					rs=db.get(query);
					if(rs.next()){
						ngay2 = rs.getString("NGAYGHINHAN");
						ttId = rs.getString("TIENTE_FK");
						tongtien2_VND = rs.getDouble("SOTIENHDVND");
						tongtien2_NT = rs.getDouble("SOTIENHDNT");
							 
						if(ttId.equals("100000")){
	
							if(tongtien1_VND != tongtien2_VND){
								cell = cells.getCell("A" + row);
								cell.setValue("Sai số tiền: Id = " + rs.getString("PK_SEQ") + " : " + tongtien2_VND);
								style = cell.getStyle();
								style.setNumber(2);
								cell.setStyle(style);
	
							}
						
						}else{
						
							if(tongtien1_NT != tongtien2_NT){
								cell = cells.getCell("A" + row);
								cell.setValue("Sai số tiền: Id = " + rs.getString("PK_SEQ") + " : "  + tongtien2_NT);
								style = cell.getStyle();
								style.setNumber(2);
								cell.setStyle(style);
							}
	
						}
					}else{
						
						if(tongtien1_VND >0){
						query=	" select * from ERP_THANHTOANHOADON tt where ( SOTIENTT =  "+tongtien1_VND+" or SOTIENHD="+tongtien1_VND+"  ) " +
								"  and  TT.NGAYGHINHAN = '" + ngay1 + "'";
						}else{
							query=	" select * from ERP_THANHTOANHOADON tt where ( SOTIENTTNT =  "+tongtien1_NT+" or SOTIENHDNT="+tongtien1_NT+"  ) " +
							"  and  TT.NGAYGHINHAN = '" + ngay1 + "'";
						}
 
						rs=db.get(query);
						if(rs.next()){
							cell = cells.getCell("A" + row);
							cell.setValue("  tìm thấy số tiền và ngày tương ứng thanh toán : "+ rs.getString("pk_seq"));
						}else{
							if(tongtien1_VND >0){
								query=	" select * from ERP_THANHTOANHOADON tt where (SOTIENTT =  "+tongtien1_VND+" or SOTIENHD="+tongtien1_VND+"  ) " ;
							}else{
								query=	" select * from ERP_THANHTOANHOADON tt where (SOTIENTTNT =  "+tongtien1_NT+" or SOTIENHDNT="+tongtien1_NT+"  ) " ;
							}
							 rs=db.get(query);
							 if(rs.next()){
								 cell = cells.getCell("A" + row);
									cell.setValue("  tìm thấy số TIEN  tương ứng thanh toán : "+ rs.getString("pk_seq"));

							 }else{
								 cell = cells.getCell("A" + row);
								 //Chỉ tạo thêm chi tiền cho những dòng không có này.
								 
								 cell.setValue("0");
							 }
						}
						
					}
					
					
				}
					rs.close();
			 
		}
		}

		/*db.getConnection().commit();
		db.getConnection().setAutoCommit(true);*/

		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	private void TaoChitien_ThemBangMoi(dbutils db, Worksheets worksheets, Worksheet worksheet){
		Cells cells = worksheet.getCells();
		
		Cell cell;
		String query;
		String Id, ngaychi, nccId, tkcoId, httt, ttId;
		String chungtu, nhId, cnId, sotaikhoan;
		String sotienVND, sotienNT;
		Worksheet worksheet2 = worksheets.getSheet("hoadon");
		
		try{
		db.getConnection().setAutoCommit(false);		
		for (int row = 8; row <= 1428; row ++){
		/*
			for (int row = 290; row <= 290; row ++){*/
			Id = "";
			ngaychi = "";
			nccId = "";
			tkcoId = "";
			sotienVND = "";
			sotienNT = "";
			nhId = ""; 
			cnId = ""; 
			sotaikhoan = "";
			chungtu = "";
			
			cell = cells.getCell("B" + row);
			nccId = cell.getStringValue();

			cell = cells.getCell("D" + row);
			ngaychi = cell.getStringValue(); 

			cell = cells.getCell("J" + row);
			tkcoId = cell.getStringValue();
			cell = cells.getCell("A" + row);
			String themmoi = cell.getStringValue();
			 
			if(ngaychi.length() == 10   ){
				//if(ngaychi.length() == 10 && themmoi.trim().equals("0") ){
			if(  ngaychi.substring(0, 7).equals("2014-02") || ngaychi.substring(0, 7).equals("2014-03")){
			/*	if(ngaychi.substring(0, 7).equals("2014-01")){*/
				cell = cells.getCell("K" + row);
				sotienVND = cell.getStringValue().trim();
				if(sotienVND.length() == 0  || sotienVND.equals("-")){
					sotienVND = "0";
				}
			
				cell = cells.getCell("M" + row);
				sotienNT = cell.getStringValue().trim();
				if(sotienNT.length() == 0 || sotienNT.equals("-")){
					sotienNT = "0";
				}
				sotienNT = sotienNT.replaceAll(",", "");
				sotienVND = sotienVND.replaceAll(",", "");
				double tigia = 1;
				if(sotienNT.equals("0")){
					ttId = "100000";
				}else{
					ttId = "100001";
					tigia = Double.parseDouble(sotienVND) / Double.parseDouble(sotienNT); 
				}
				cell = cells.getCell("E" + row);
				chungtu = cell.getStringValue();
				// nếu là số tài khoản có bắt đầu 112
				if(   (tkcoId.equals("11210") || tkcoId.equals("11220"))){
					httt = "100001"; // Hình thức thanh toán là chuyển khoản
					if(chungtu.indexOf("A") >= 0){
						if(sotienNT.equals("0")){ // Tiền VND
							sotaikhoan = "65490509";
							nhId = "100002";
							cnId = "100055";
							ttId = "100000";
						}else{
							sotaikhoan = "6549 0559";
							nhId = "100002";
							cnId = "100055";
							ttId = "100001";
						}
					}
				
					if(chungtu.indexOf("V") >= 0){
						if(sotienNT.equals("0")){ // Tiền VND
							sotaikhoan = "0181000333997";
							nhId = "100039";
							cnId = "100051";
							ttId = "100000";
						
						}else{
							sotaikhoan = "0181372051113";
							nhId = "100039";
							cnId = "100051";
							ttId = "100001";
						}
					}

					if(chungtu.indexOf("U") >= 0){
						if(sotienNT.equals("0")){ // Tiền VND
							sotaikhoan = "1023321179";
							nhId = "100003";
							cnId = "100024";
							ttId = "100000";
							
						}else{
							sotaikhoan = "1029307151";
							nhId = "100003";
							cnId = "100024";
							ttId = "100001";
						}
					}
				 
						//System.out.println("Dòng: " + row);
						query = " INSERT INTO ERP_THANHTOANHOADON_1 " +
								" (NGAYGHINHAN, TRANGTHAI, NCC_FK, HTTT_FK, NGANHANG_FK, " + 
								" CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, SOTIENTT, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, PREFIX, LOAITHANHTOAN, " + 
								" SOTIENHD, SOTIENHDNT, PHINGANHANG, PHINGANHANGNT, VAT, VATNT, SOTIENTTNT, TRICHPHI, CHENHLECHVND, TIENTE_FK, TIGIA) " +
								" VALUES(" +
								" '" + ngaychi + "', '1', " + nccId + ", " + httt + ", " + nhId + ", " +
								" " + cnId + ", '" + sotaikhoan + "', N'Thanh toán HĐ mua hàng', " + sotienVND + ", " +
								" '" + this.getDateTime() + "', '100307', '" + this.getDateTime() + "', '100307', '180', '1', " +
								" '0', '0', '0', '0', '0', '0', " + sotienNT + ", '0', '0', '" + ttId + "', " + tigia + ")";
						
					 /*
						if(!db.update(query)){
							System.out.println("Không thành công  : "+query);
						}*/
						 
						 TaoChitien_Hoadon_ThemBangMoi(db,worksheet2 ,nccId,ngaychi);
		 
						/*if(!db.update(query)){
							System.out.println("Không thành công  : "+query);
							
						}else{
		
							query = "SELECT SCOPE_IDENTITY() AS ID ";
							ResultSet rs = db.get(query);
							if(rs != null){
								rs.next();
								cell = cells.getCell("A" + row);
								cell.setValue(rs.getString("ID"));			
								Id = rs.getString("ID");
								rs.close();
							}
							//TAO NHỮNG PHIẾU THEO NCC,NGAYTHANHTOAN
							
						}*/
				}
			} 
			}
		}

		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);

		}catch(Exception  e){
			e.printStackTrace();
			
		}

	}
	
	
	private void TaoChitien_New(dbutils db, Worksheets worksheets, Worksheet worksheet){
		Cells cells = worksheet.getCells();
		
		Cell cell;
		String query;
		String Id, ngaychi, nccId, tkcoId, httt, ttId;
		String chungtu, nhId, cnId, sotaikhoan;
		String sotienVND, sotienNT;
		Worksheet worksheet2 = worksheets.getSheet("hoadon");
		
		try{
		db.getConnection().setAutoCommit(false);		
		/*for (int row = 8; row <= 1428; row ++){*/
			for (int row = 261; row <= 261; row ++){
			Id = "";
			ngaychi = "";
			nccId = "";
			tkcoId = "";
			sotienVND = "";
			sotienNT = "";
			nhId = ""; 
			cnId = ""; 
			sotaikhoan = "";
			chungtu = "";
			
			cell = cells.getCell("B" + row);
			nccId = cell.getStringValue();

			cell = cells.getCell("D" + row);
			ngaychi = cell.getStringValue(); 

			cell = cells.getCell("J" + row);
			tkcoId = cell.getStringValue();
			cell = cells.getCell("A" + row);
			String themmoi = cell.getStringValue();
			
		 
			if(ngaychi.length() == 10 && themmoi.trim().equals("0") ){
			if( ngaychi.substring(0, 7).equals("2014-02") || ngaychi.substring(0, 7).equals("2014-03")){
				/*if(ngaychi.substring(0, 7).equals("2014-01")){*/
					cell = cells.getCell("K" + row);
					sotienVND = cell.getStringValue().trim();
					
					if(sotienVND.length() == 0  || sotienVND.equals("-")){
						sotienVND = "0";
					}
			
					cell = cells.getCell("M" + row);
					sotienNT = cell.getStringValue().trim();
	
					if(sotienNT.length() == 0 || sotienNT.equals("-")){
						sotienNT = "0";
					}
					
					sotienNT = sotienNT.replaceAll(",", "");
					sotienVND = sotienVND.replaceAll(",", "");
				
					double tigia = 1;
					if(sotienNT.equals("0")){
						ttId = "100000";
					}else{
						ttId = "100001";
						tigia = Double.parseDouble(sotienVND) / Double.parseDouble(sotienNT); 
					}

				cell = cells.getCell("E" + row);
				chungtu = cell.getStringValue();
				// nếu là số tài khoản có bắt đầu 112
				
				if(   (tkcoId.equals("11210") || tkcoId.equals("11220"))){
					
					httt = "100001"; // Hình thức thanh toán là chuyển khoản
					if(chungtu.indexOf("A") >= 0){
						if(sotienNT.equals("0")){ // Tiền VND
							sotaikhoan = "65490509";
							nhId = "100002";
							cnId = "100055";
							ttId = "100000";
						}else{
							sotaikhoan = "6549 0559";
							nhId = "100002";
							cnId = "100055";
							ttId = "100001";
						}
					}
				
					if(chungtu.indexOf("V") >= 0){
						if(sotienNT.equals("0")){ // Tiền VND
							sotaikhoan = "0181000333997";
							nhId = "100039";
							cnId = "100051";
							ttId = "100000";
						
						}else{
							sotaikhoan = "0181372051113";
							nhId = "100039";
							cnId = "100051";
							ttId = "100001";
						}
					}

					if(chungtu.indexOf("U") >= 0){
						if(sotienNT.equals("0")){ // Tiền VND
							sotaikhoan = "1023321179";
							nhId = "100003";
							cnId = "100024";
							ttId = "100000";
							
						}else{
							sotaikhoan = "1029307151";
							nhId = "100003";
							cnId = "100024";
							ttId = "100001";
						}
					}
				 
						//System.out.println("Dòng: " + row);
						query = " INSERT INTO ERP_THANHTOANHOADON " +
								" (NGAYGHINHAN, TRANGTHAI, NCC_FK, HTTT_FK, NGANHANG_FK, " + 
								" CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, SOTIENTT, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, PREFIX, LOAITHANHTOAN, " + 
								" SOTIENHD, SOTIENHDNT, PHINGANHANG, PHINGANHANGNT, VAT, VATNT, SOTIENTTNT, TRICHPHI, CHENHLECHVND, TIENTE_FK, TIGIA) " +
								" VALUES(" +
								" '" + ngaychi + "', '1', " + nccId + ", " + httt + ", " + nhId + ", " +
								" " + cnId + ", '" + sotaikhoan + "', N'Thanh toán HĐ mua hàng', " + sotienVND + ", " +
								" '" + this.getDateTime() + "', '100307', '" + this.getDateTime() + "', '100307', '180', '1', " +
								" '0', '0', '0', '0', '0', '0', " + sotienNT + ", '0', '0', '" + ttId + "', " + tigia + ")";
						System.out.println(query);
						
						if(!db.update(query)){
							System.out.println("Không thành công  : "+query);
						}
						
						if(ngaychi.substring(0, 7).equals("2014-01")){
							TaoChitien_Hoadon_New(db,worksheet2 ,nccId,ngaychi);
						}
						
						/*if(!db.update(query)){
							System.out.println("Không thành công  : "+query);
							
						}else{
		
							query = "SELECT SCOPE_IDENTITY() AS ID ";
							ResultSet rs = db.get(query);
							if(rs != null){
								rs.next();
								cell = cells.getCell("A" + row);
								cell.setValue(rs.getString("ID"));			
								Id = rs.getString("ID");
								rs.close();
							}
							//TAO NHỮNG PHIẾU THEO NCC,NGAYTHANHTOAN
							
						}*/
				}
			} 
			}
		}

		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);

		}catch(Exception  e){
			e.printStackTrace();
			
		}

	}
	NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
	private void TaoChitien_Hoadon_ThemBangMoi (dbutils db, Worksheet worksheet,String NCC_FK,String NGAYGHINHAN){
		Cells cells = worksheet.getCells();
		
		Cell cell;
		
		String query;
		String ngay, ngaytt1, ngaytt2, nccId;
		String sotienTT, sotienNT, sotienAVAT;
		String hdId, ttId, tienteId; 
		String tthd, ttct, sohoadon, ngayhd, sotienttexcel;
		try{
	/*	db.getConnection().setAutoCommit(false);		*/
 
			query = "SELECT PK_SEQ AS CTID, SOTIENTT,SOTIENTTNT ,isnull(tiente_fk,100000) as tiente_fk,  TRANGTHAI FROM ERP_THANHTOANHOADON_1 WHERE NGAYGHINHAN = '" + NGAYGHINHAN + "' AND NCC_FK = " + NCC_FK + "" ;
		 
		//System.out.println(query);
			String tiente_fk="";
		ResultSet rs = db.get(query);
		double tongsotienchi_VND=0;
		double tongsotienchi_NT=0;
		
		ttId="";
		if(rs.next()){
			ttId=rs.getString("CTID");
			tongsotienchi_VND=rs.getDouble("SOTIENTT");
			tongsotienchi_NT=rs.getDouble("SOTIENTTNT");
			tiente_fk=rs.getString("tiente_fk");
		}
		rs.close();
		
		
		double sumsotienchi_VND=0;
		double sumsotienchi_NT=0;
		
		
		for (int row = 4; row <= 776; row ++){
			
			ngaytt1 = "";
			ngaytt2 = "";
			hdId = "";
  
			sotienTT = "0"; 
			sotienAVAT = "0";
			sotienNT = "0";
			tthd = "";
			sohoadon = "";
			nccId = "";
			ngay = "";
			ttct = "";
			tienteId = "";
			ngayhd = "";
			sotienttexcel = "";
			nccId = "";
			
			cell = cells.getCell("L" + row);
			nccId = cell.getStringValue(); 
			
			cell = cells.getCell("M" + row);
			ngayhd = cell.getStringValue(); 

			cell = cells.getCell("N" + row);
			sohoadon = cell.getStringValue(); 

			cell = cells.getCell("S" + row);
			ngaytt1 = cell.getStringValue(); 

			cell = cells.getCell("Y" + row);
			ngaytt2 = cell.getStringValue(); 
			
			String sotienexcel = "0";
			cell = cells.getCell("O" + row);
			sotienexcel = cell.getStringValue().trim().replaceAll(",", ""); 

			if(sotienexcel.equals("0.00") || sotienexcel.length() == 0){
				cell = cells.getCell("P" + row);
				sotienexcel = cell.getStringValue().trim().replaceAll(",", ""); 							
			}
			if(sotienexcel.trim().length() == 0) sotienexcel = "0";
			boolean exit = false;
			 
			 // Là nhà cung cấp cần tìm và có ngày ghi nhận lần 1 hoặc ngày ghi nhận lần 2 bằng với phiếu chi
			
				if(nccId.equals(NCC_FK) &&  ( NGAYGHINHAN.equals(ngaytt2) || NGAYGHINHAN.equals(ngaytt1) )){
					exit = true;
				}
				
			  /*if(ngaytt2.equals(ngaytt1) && ngaytt2.length() >0){
				  System.out.println("Có truong hợp 2 ngày bằng nhau "+row );
			  }*/
				if(exit){
					
					//System.out.println(NCC_FK+"- "+ngaytt1+row);
					
					// Tìm hóa đơn (đã được thanh toán), theo nhà cung cấp, ngày hóa đơn, số tiền
					
					// chỉ cần đưa những hóa đơn được đánh dấu chưa tìm thấy.
					 
					query = " SELECT  HD.PK_SEQ AS HDID, PARK.NCC_FK, HD.sotienAVAT, PARK.TRANGTHAI, ISNULL(PARK.TIENTE_FK,100000) AS TIENTE_FK, HD.NGAYHOADON " +
							" FROM ERP_HOADONNCC HD " +
							" INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " +
							" WHERE PARK.NCC_FK = " + nccId + " AND HD.NGAYHOADON = '" + ngayhd + "' AND HD.sotienAVAT = " + sotienexcel + "";
 
					 rs = db.get(query);
									
					// Nếu NccId, ngày hóa đơn, số tiền đúng	
					
					if(rs.next()){
						hdId = rs.getString("HDID");
						sotienAVAT = rs.getString("sotienAVAT");
						nccId = rs.getString("NCC_FK");
						tthd = rs.getString("TRANGTHAI");
						tienteId = rs.getString("TIENTE_FK");
						cell = cells.getCell("H" + row);
						cell.setValue(tthd + " - " + hdId);
						rs.close();
					}else{
	
							// Tìm hóa đơn (đã được thanh toán), theo nhà cung cấp và ngày hóa đơn				
							query = " SELECT  HD.PK_SEQ AS HDID, PARK.NCC_FK, HD.sotienAVAT, PARK.TRANGTHAI,ISNULL(PARK.TIENTE_FK,100000) AS TIENTE_FK , HD.NGAYHOADON " +
									" FROM ERP_HOADONNCC HD " +
									" INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " +
									" WHERE PARK.NCC_FK = " + nccId + " AND HD.NGAYHOADON = '" + ngayhd + "'  AND HD.SOHOADON = '" + sohoadon + "' ";
							//System.out.println(query);
							rs = db.get(query);
											
	// Nếu NccId và ngày hóa đơn đúng				
								if(rs.next()){
									hdId = rs.getString("HDID");
									sotienAVAT = rs.getString("sotienAVAT");
									nccId = rs.getString("NCC_FK");
									tthd = rs.getString("TRANGTHAI");
									tienteId = rs.getString("TIENTE_FK");
									
									//tìm được số hóa đơn
									cell = cells.getCell("H" + row);
									cell.setValue(tthd + " - " + hdId);
									
									if(tienteId.equals("100001")){
										cell = cells.getCell("O" + row);
										sotienexcel = cell.getStringValue(); 
									}else{
										cell = cells.getCell("P" + row);
										sotienexcel = cell.getStringValue(); 							
									}
									sotienexcel = sotienexcel.replaceAll(",", "");
									
									if(Double.parseDouble(sotienAVAT) != Double.parseDouble(sotienexcel)){
										// In ra sai tiền hóa đơn
										cell = cells.getCell("I" + row);
										cell.setValue("Sai tiền trong hóa đơn : "+formatter.format(Double.parseDouble(sotienAVAT)));
									}
									rs.close();
								}else{					
						 
									query = " SELECT  HD.PK_SEQ AS HDID, PARK.NCC_FK, HD.sotienAVAT, PARK.TRANGTHAI,ISNULL(PARK.TIENTE_FK,100000) AS TIENTE_FK, HD.NGAYHOADON " +
											" FROM  ERP_HOADONNCC HD  " +
											" INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " +
											" WHERE PARK.NCC_FK = " + nccId + " AND HD.sotienAVAT = " + sotienexcel + " AND HD.SOHOADON = '" + sohoadon + "' ";
									
									rs = db.get(query);
									// Nếu tìm thấy, thì nghĩa là ngày hóa đơn không đúng						
									if(rs.next()){
										
										hdId = rs.getString("HDID");
										sotienAVAT = rs.getString("sotienAVAT");
										nccId = rs.getString("NCC_FK");
										tthd = rs.getString("TRANGTHAI");
										tienteId = rs.getString("TIENTE_FK");
										cell = cells.getCell("H" + row);
										cell.setValue(tthd + " - " + hdId);
										cell = cells.getCell("J" + row);
										cell.setValue("Ngày Hóa đơn không đúng: "+rs.getString("NGAYHOADON"));
										rs.close();
										
									}else{
										// tìm trường hợp đúng số hóa đơn ,đúng tiền
										query = " SELECT  HD.PK_SEQ AS HDID, PARK.NCC_FK, HD.sotienAVAT, PARK.TRANGTHAI,ISNULL(PARK.TIENTE_FK,100000) AS TIENTE_FK, HD.NGAYHOADON " +
										" FROM  ERP_HOADONNCC HD  " +
										" INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " +
										" WHERE   HD.sotienAVAT = " + sotienexcel + " AND HD.SOHOADON = '" + sohoadon + "' ";
										rs = db.get(query);
										if(rs.next()){
											hdId = rs.getString("HDID");
											sotienAVAT = rs.getString("sotienAVAT");
											nccId = rs.getString("NCC_FK");
											tthd = rs.getString("TRANGTHAI");
											tienteId = rs.getString("TIENTE_FK");
											cell = cells.getCell("H" + row);
											cell.setValue(tthd + " - " + hdId);
											cell = cells.getCell("J" + row);
											cell.setValue("Đung tiền đúng số hóa đơn,sai ngày,sai NCC "+rs.getString("HDID"));
											rs.close();
											
										}else{
											cell = cells.getCell("H" + row);
											cell.setValue("HĐ không tìm thấy");
										}
									}
								}
					
					}
 
					 
					sotienTT="0";
					sotienNT="0";
					//nếu ngày chi là bằng lần 1
					// không biết có trường hợp  này thu lần 1 với lần 2 bằng nhau ko?,check cái coi
					
					
					if(NGAYGHINHAN.equals(ngaytt1)){
						cell = cells.getCell("U" + row);
						try{
						sotienTT =""+ Double.parseDouble(cell.getStringValue().replaceAll(",", ""));
						}catch(Exception er){
							
						}
						cell = cells.getCell("T" + row);
						try{
						sotienNT = ""+ Double.parseDouble(cell.getStringValue().replaceAll(",", ""));
						}catch(Exception er){ }
						
					}
					if(NGAYGHINHAN.equals(ngaytt2)) {
						
						cell = cells.getCell("Z" + row);
						try{
							sotienNT =""+( Double.parseDouble(sotienNT) + Double.parseDouble(cell.getStringValue().replaceAll(",", "")));
						}catch(Exception er){}
						cell = cells.getCell("AA" + row);
						try{
							sotienTT =""+ (Double.parseDouble(sotienTT) + Double.parseDouble( cell.getStringValue().replaceAll(",", "")));
						}catch(Exception er){}
					 
					}
					
					
					if(Double.parseDouble(sotienNT) >0){
						sumsotienchi_NT=sumsotienchi_NT + Double.parseDouble(sotienNT);
						 
					}else{
						 
						sumsotienchi_VND=sumsotienchi_VND + Double.parseDouble(sotienTT);
					}
					
					
					if(hdId.length() > 0 & ttId.length() > 0){
						ngay=NGAYGHINHAN;
					
						 
							query = "SELECT COUNT(*) AS NUM FROM ERP_THANHTOANHOADON_HOADON_1 WHERE THANHTOANHD_FK = " + ttId + " AND HOADON_FK = " + hdId + "";
							rs = db.get(query);
	
							// Chua co trong ERP_THANHTOANHOADON_HOADON							
							if(rs != null){
								rs.next();
								if (rs.getString("NUM").equals("0")){
								 
									query = "INSERT INTO ERP_THANHTOANHOADON_HOADON_1(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI, SOTIENNT) " +
											"VALUES(" +  ttId +", " + hdId + ", " + Double.parseDouble(sotienTT) + ", " + Double.parseDouble(sotienAVAT) + ", " +
											"" + (Double.parseDouble(sotienAVAT) - Double.parseDouble(sotienTT)) + ", " + Double.parseDouble(sotienNT) + ")";
							
									//System.out.println("" + row + ": " + query);
									/*if(!db.update(query)){
										System.out.println("KHONG THANH CONG : " + row + ": " + query);
									}*/
								}									
								rs.close();
							}
						 
					}else{
						System.out.println("hdId: "+hdId);
						System.out.println("ttId: "+ttId);
						// Không vào được
						 System.out.println("KHONG THUC HIEN DUOC GIAO TAC "); 
					}
				}
		}
			
	 
		if(!tiente_fk.equals("100000")){
				if(Math.round(tongsotienchi_NT)  != Math.round(sumsotienchi_NT)){
					
					query="  SELECT * FROM ERP_HOADONNCC WHERE kyhieu='TEM' and  SOTIENAVAT="+tongsotienchi_NT+" and ncc_fk="+NCC_FK;
					ResultSet rs1=db.get(query);
					if(rs1.next()){
						// 
						 
						hdId=rs1.getString("pk_seq");
						query = " INSERT INTO ERP_THANHTOANHOADON_HOADON_1(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI, SOTIENNT) " +
						"VALUES(" +  ttId +", " + hdId + ", " + tongsotienchi_NT + ", " + tongsotienchi_NT + ", " +
						"" +0 + ", " + tongsotienchi_NT + ")";
						
						/*if(!db.update(query)){
							System.out.println("KHONG THANH CONG : "  + query);
						}*/
						
						 
					}else{
						System.out.println("tongsotienchi_NT : "+Math.round( tongsotienchi_NT));
						System.out.println("sumsotienchi_NT : "+Math.round(sumsotienchi_NT));
						System.out.println("Nha Cung Cap :  "+NCC_FK +"-  Ngay ghi nhan : "+NGAYGHINHAN);
						
					}
					
					 
				} 
		}else{
				if(Math.round(tongsotienchi_VND ) != Math.round(sumsotienchi_VND)){
					query="  SELECT * FROM ERP_HOADONNCC WHERE kyhieu='TEM' and  SOTIENAVAT="+tongsotienchi_VND+" and ncc_fk="+NCC_FK;
					ResultSet rs1=db.get(query);
					if(rs1.next()){
						// 
						 
						hdId=rs1.getString("pk_seq");
						query = " INSERT INTO ERP_THANHTOANHOADON_HOADON_1(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI, SOTIENNT) " +
						"VALUES(" +  ttId +", " + hdId + ", " + tongsotienchi_VND + ", " + tongsotienchi_VND + ", " +
						"" +0 + ", " + tongsotienchi_VND + ")";
						
					/*	if(!db.update(query)){
							System.out.println("KHONG THANH CONG : "  + query);
						}*/
						 
					}else{
						System.out.println("tongsotienchi_VND : "+Math.round(tongsotienchi_VND));
						System.out.println("sumsotienchi_VND : "+Math.round(sumsotienchi_VND));
						System.out.println("Nha Cung Cap :  "+NCC_FK +"-  Ngay ghi nhan : "+NGAYGHINHAN);
					}
				}
		}
		
		/*	db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
*/
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.toString());
		}
		
	}

	private void TaoChitien_Hoadon_New (dbutils db, Worksheet worksheet,String NCC_FK,String NGAYGHINHAN){
		Cells cells = worksheet.getCells();
		
		Cell cell;
		
		String query;
		String ngay, ngaytt1, ngaytt2, nccId;
		String sotienTT, sotienNT, sotienAVAT;
		String hdId, ttId ="", tienteId; 
		String tthd, ttct, sohoadon, ngayhd, sotienttexcel;
		try{
	/*	db.getConnection().setAutoCommit(false);		*/
 
			query = "SELECT PK_SEQ AS CTID, SOTIENTT,SOTIENTTNT ,isnull(tiente_fk,100000) as tiente_fk,  TRANGTHAI FROM ERP_THANHTOANHOADON WHERE NGAYGHINHAN = '" + NGAYGHINHAN + "' AND NCC_FK = " + NCC_FK + "" ;
		 
		//System.out.println(query);
			String tiente_fk="";
		ResultSet rs = db.get(query);
		double tongsotienchi_VND=0;
		double tongsotienchi_NT=0;
		
		
		if(rs.next()){
			ttId=rs.getString("CTID");
			tongsotienchi_VND=rs.getDouble("SOTIENTT");
			tongsotienchi_NT=rs.getDouble("SOTIENTTNT");
			tiente_fk=rs.getString("tiente_fk");
		}
		rs.close();
		
		
		double sumsotienchi_VND=0;
		double sumsotienchi_NT=0;
		
		
		for (int row = 4; row <= 776; row ++){
			
			ngaytt1 = "";
			ngaytt2 = "";
			hdId = "";
			 
			sotienTT = "0"; 
			sotienAVAT = "0";
			sotienNT = "0";
			tthd = "";
			sohoadon = "";
			nccId = "";
			ngay = "";
			ttct = "";
			tienteId = "";
			ngayhd = "";
			sotienttexcel = "";
			nccId = "";
			
			cell = cells.getCell("L" + row);
			nccId = cell.getStringValue(); 
			
			cell = cells.getCell("M" + row);
			ngayhd = cell.getStringValue(); 

			cell = cells.getCell("N" + row);
			sohoadon = cell.getStringValue(); 

			cell = cells.getCell("S" + row);
			ngaytt1 = cell.getStringValue(); 

			cell = cells.getCell("Y" + row);
			ngaytt2 = cell.getStringValue(); 
			
			String sotienexcel = "0";
			cell = cells.getCell("O" + row);
			sotienexcel = cell.getStringValue().trim().replaceAll(",", ""); 

			if(sotienexcel.equals("0.00") || sotienexcel.length() == 0){
				cell = cells.getCell("P" + row);
				sotienexcel = cell.getStringValue().trim().replaceAll(",", ""); 							
			}
			if(sotienexcel.trim().length() == 0) sotienexcel = "0";
			boolean exit = false;
			 
			 // Là nhà cung cấp cần tìm và có ngày ghi nhận lần 1 hoặc ngày ghi nhận lần 2 bằng với phiếu chi
			
				if(nccId.equals(NCC_FK) &&  ( NGAYGHINHAN.equals(ngaytt2) || NGAYGHINHAN.equals(ngaytt1) )){
					exit = true;
				}
 
				if(exit){
					
					//System.out.println(NCC_FK+"- "+ngaytt1+row);
					
					// Tìm hóa đơn (đã được thanh toán), theo nhà cung cấp, ngày hóa đơn, số tiền
					
					// chỉ cần đưa những hóa đơn được đánh dấu chưa tìm thấy.
					
					
					query = " SELECT  HD.PK_SEQ AS HDID, PARK.NCC_FK, HD.sotienAVAT, PARK.TRANGTHAI, PARK.TIENTE_FK, HD.NGAYHOADON " +
							" FROM ERP_HOADONNCC HD " +
							" INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " +
							" WHERE PARK.NCC_FK = " + nccId + " AND HD.NGAYHOADON = '" + ngayhd + "' AND HD.sotienAVAT = " + sotienexcel + "";
								
					//System.out.println(query);
					 rs = db.get(query);
									
					// Nếu NccId, ngày hóa đơn, số tiền đúng	
					
					if(rs.next()){
						hdId = rs.getString("HDID");
						sotienAVAT = rs.getString("sotienAVAT");
						nccId = rs.getString("NCC_FK");
						tthd = rs.getString("TRANGTHAI");
						tienteId = rs.getString("TIENTE_FK");
						cell = cells.getCell("H" + row);
						cell.setValue(tthd + " - " + hdId);
						rs.close();
					}else{
	
	// Tìm hóa đơn (đã được thanh toán), theo nhà cung cấp và ngày hóa đơn				
							query = " SELECT  HD.PK_SEQ AS HDID, PARK.NCC_FK, HD.sotienAVAT, PARK.TRANGTHAI, PARK.TIENTE_FK, HD.NGAYHOADON " +
									" FROM ERP_HOADONNCC HD " +
									" INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " +
									" WHERE PARK.NCC_FK = " + nccId + " AND HD.NGAYHOADON = '" + ngayhd + "'  AND HD.SOHOADON = '" + sohoadon + "' ";
							//System.out.println(query);
							rs = db.get(query);
											
	// Nếu NccId và ngày hóa đơn đúng				
								if(rs.next()){
									hdId = rs.getString("HDID");
									sotienAVAT = rs.getString("sotienAVAT");
									nccId = rs.getString("NCC_FK");
									tthd = rs.getString("TRANGTHAI");
									tienteId = rs.getString("TIENTE_FK");
									
									//tìm được số hóa đơn
									cell = cells.getCell("H" + row);
									cell.setValue(tthd + " - " + hdId);
									
									if(tienteId.equals("100001")){
										cell = cells.getCell("O" + row);
										sotienexcel = cell.getStringValue(); 
									}else{
										cell = cells.getCell("P" + row);
										sotienexcel = cell.getStringValue(); 							
									}
									sotienexcel = sotienexcel.replaceAll(",", "");
									
									if(Double.parseDouble(sotienAVAT) != Double.parseDouble(sotienexcel)){
										// In ra sai tiền hóa đơn
										cell = cells.getCell("I" + row);
										cell.setValue("Sai tiền trong hóa đơn : "+Double.parseDouble(sotienAVAT));
									}
									rs.close();
								}else{					
						 
									query = " SELECT  HD.PK_SEQ AS HDID, PARK.NCC_FK, HD.sotienAVAT, PARK.TRANGTHAI, PARK.TIENTE_FK, HD.NGAYHOADON " +
											" FROM  ERP_HOADONNCC HD  " +
											" INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " +
											" WHERE PARK.NCC_FK = " + nccId + " AND HD.sotienAVAT = " + sotienexcel + " AND HD.SOHOADON = '" + sohoadon + "' ";

									rs = db.get(query);
									// Nếu tìm thấy, thì nghĩa là ngày hóa đơn không đúng						
									if(rs.next()){
										
										hdId = rs.getString("HDID");
										sotienAVAT = rs.getString("sotienAVAT");
										nccId = rs.getString("NCC_FK");
										tthd = rs.getString("TRANGTHAI");
										tienteId = rs.getString("TIENTE_FK");
										cell = cells.getCell("H" + row);
										cell.setValue(tthd + " - " + hdId);
										cell = cells.getCell("J" + row);
										cell.setValue("Ngày Hóa đơn không đúng: "+rs.getString("NGAYHOADON"));
										rs.close();
										
									}else{
										
										cell = cells.getCell("H" + row);
										cell.setValue("HĐ không tìm thấy");				
									}
								}
					
					}
 
					 
					sotienTT="0";
					sotienNT="0";
					//nếu ngày chi là bằng lần 1
					// không biết có trường hợp  này thu lần 1 với lần 2 bằng nhau ko?,check cái coi
					
					
					if(NGAYGHINHAN.equals(ngaytt1)){
						cell = cells.getCell("U" + row);
						try{
						sotienTT =""+ Double.parseDouble(cell.getStringValue().replaceAll(",", ""));
						}catch(Exception er){
							
						}
						cell = cells.getCell("T" + row);
						try{
						sotienNT = ""+ Double.parseDouble(cell.getStringValue().replaceAll(",", ""));
						}catch(Exception er){ }
						
					} 
					if(NGAYGHINHAN.equals(ngaytt2)) {
						
						cell = cells.getCell("Z" + row);
						try{
							sotienNT =""+( Double.parseDouble(sotienNT) + Double.parseDouble(cell.getStringValue().replaceAll(",", "")));
						}catch(Exception er){}
						cell = cells.getCell("AA" + row);
						try{
							sotienTT =""+ (Double.parseDouble(sotienTT) + Double.parseDouble( cell.getStringValue().replaceAll(",", "")));
						}catch(Exception er){}
					 
					}
					
					if(Double.parseDouble(sotienNT) >0){
						sumsotienchi_NT=sumsotienchi_NT + Double.parseDouble(sotienNT);
						 
					}else{
						 
						sumsotienchi_VND=sumsotienchi_VND + Double.parseDouble(sotienTT);
					}
					
					/*System.out.println("hdId: "+hdId );
					System.out.println("ttId: "+ttId);
					System.out.println("tthd : "+tthd);*/
					
					if(hdId.length() > 0 & ttId.length() > 0  ){
						ngay=NGAYGHINHAN;
					
						/*if(ngay.substring(0, 7).equals("2014-02") || ngay.substring(0, 7).equals("2014-03") || ngay.substring(0, 7).equals("2014-01")){*/
						if(ngay.substring(0, 7).equals("2014-01")){
							query = "SELECT COUNT(*) AS NUM FROM ERP_THANHTOANHOADON_HOADON WHERE THANHTOANHD_FK = " + ttId + " AND HOADON_FK = " + hdId + "";
							rs = db.get(query);
	
							// Chua co trong ERP_THANHTOANHOADON_HOADON							
							if(rs != null){
								rs.next();
								if (rs.getString("NUM").equals("0")){
					 
									query = "INSERT INTO ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI, SOTIENNT) " +
											"VALUES(" +  ttId +", " + hdId + ", " + Double.parseDouble(sotienTT) + ", " + Double.parseDouble(sotienAVAT) + ", " +
											"" + (Double.parseDouble(sotienAVAT) - Double.parseDouble(sotienTT)) + ", " + Double.parseDouble(sotienNT) + ")";
							
								//	System.out.println("" + row + ": " + query);
								
									if(!db.update(query)){
										System.out.println("khong thanh cong : "+query);
									}
									
								}									
								rs.close();
							}
						}
					}else{
						// Không vào được
						System.out.println("hdId: "+hdId );
						System.out.println("ttId: "+ttId);
						System.out.println("Nha Cung Cap :  "+NCC_FK +"-  Ngay ghi nhan : "+NGAYGHINHAN);
						System.out.println("KHONG THUC HIEN DUOC GIAO TAC "); 
					}
				}
		}
			
		/*System.out.println("tongsotienchi_NT : "+tongsotienchi_NT);
		System.out.println("tongsotienchi_VND : "+tongsotienchi_VND);
		
		System.out.println("sumsotienchi_NT : "+sumsotienchi_NT);
		System.out.println("sumsotienchi_VND : "+sumsotienchi_VND);*/
		
		if(!tiente_fk.equals("100000")){
				if(Math.round(tongsotienchi_NT)  != Math.round(sumsotienchi_NT)){
					
					if(sumsotienchi_NT==0){
						query="  SELECT * FROM ERP_HOADONNCC WHERE kyhieu='TEM' and  SOTIENAVAT="+tongsotienchi_NT+" and ncc_fk="+NCC_FK;
						
						//System.out.println(query);
						ResultSet rs1=db.get(query);
						if(rs1.next()){
							// 
							//System.out.println(" Tim thay hoa don dau ky  ");
							hdId=rs1.getString("pk_seq");
							query = "INSERT INTO ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI, SOTIENNT) " +
							"VALUES(" +  ttId +", " + hdId + ", " + tongsotienchi_NT + ", " + tongsotienchi_NT + ", " +
							"" +0 + ", " + tongsotienchi_NT + ")";
							if(!db.update(query)){
								System.out.println("khong thanh cong : "+query);
							}
						}else{
							System.out.println("tongsotienchi_NT : "+Math.round( tongsotienchi_NT));
							System.out.println("sumsotienchi_NT : "+Math.round(sumsotienchi_NT));
							System.out.println("Nha Cung Cap :  "+NCC_FK +"-  Ngay ghi nhan : "+NGAYGHINHAN);
							
						}
						
						
					}
					
				} 
		}else{
				if(Math.round(tongsotienchi_VND ) != Math.round(sumsotienchi_VND)){
					
					query="  SELECT * FROM ERP_HOADONNCC WHERE kyhieu='TEM' and SOTIENAVAT="+tongsotienchi_VND+" and ncc_fk="+NCC_FK;
					//System.out.println(query);
					ResultSet rs1=db.get(query);
					if(rs1.next()){
						// 
						hdId=rs1.getString("pk_seq");
						
						query = "INSERT INTO ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI, SOTIENNT) " +
						"VALUES(" +  ttId +", " + hdId + ", " + tongsotienchi_VND + ", " + tongsotienchi_VND + ", " +
						"" +0 + ", 0)";
						if(!db.update(query)){
							System.out.println("khong thanh cong : "+query);
						}
						//System.out.println(" Tim thay hoa don dau ky  ");
					}else{
						System.out.println("tongsotienchi_VND : "+Math.round(tongsotienchi_VND));
						System.out.println("sumsotienchi_VND : "+Math.round(sumsotienchi_VND));
						System.out.println("Nha Cung Cap :  "+NCC_FK +"-  Ngay ghi nhan : "+NGAYGHINHAN);
					}
				}
		}
		
		/*	db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
*/
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.toString());
		}
		
	}

	//truong hop tao moi hoan toan 
	
	
	
	private void TaoChitien(dbutils db, Worksheet worksheet){
		Cells cells = worksheet.getCells();
		
		Cell cell;
		String query;
		String Id, ngaychi, nccId, tkcoId, httt, ttId;
		String chungtu, nhId, cnId, sotaikhoan;
		String sotienVND, sotienNT;
		try{
		db.getConnection().setAutoCommit(false);		

		for (int row = 8; row <= 1428; row ++){
			//System.out.println("Dòng: " + row);
			Id = "";
			ngaychi = "";
			nccId = "";
			tkcoId = "";
			sotienVND = "";
			sotienNT = "";
			nhId = ""; 
			cnId = ""; 
			sotaikhoan = "";
			chungtu = "";
			
			cell = cells.getCell("B" + row);
			nccId = cell.getStringValue();

			cell = cells.getCell("D" + row);
			ngaychi = cell.getStringValue(); 

			cell = cells.getCell("J" + row);
			tkcoId = cell.getStringValue();
			cell = cells.getCell("A" + row);
			String themmoi = cell.getStringValue();
			
			if(ngaychi.length() == 10 && themmoi.trim().equals("0") ){
				
			if(ngaychi.substring(0, 7).equals("2014-01") || ngaychi.substring(0, 7).equals("2014-02") || ngaychi.substring(0, 7).equals("2014-03")){
				cell = cells.getCell("L" + row);
				sotienVND = cell.getStringValue().trim();
				
				if(sotienVND.length() == 0  || sotienVND.equals("-")){
					sotienVND = "0";
				}
			
				cell = cells.getCell("N" + row);
				sotienNT = cell.getStringValue().trim();

				if(sotienNT.length() == 0 || sotienNT.equals("-")){
					sotienNT = "0";
				}
			
				sotienNT = sotienNT.replaceAll(",", "");
				sotienVND = sotienVND.replaceAll(",", "");
			
				double tigia = 1;
				if(sotienNT.equals("0")){
					ttId = "100000";
				}else{
					ttId = "100001";
					tigia = Double.parseDouble(sotienVND) / Double.parseDouble(sotienNT); 
				}

				cell = cells.getCell("E" + row);
				chungtu = cell.getStringValue();
				// nếu là số tài khoản có bắt đầu 112
				
				if(   (tkcoId.equals("11210") || tkcoId.equals("11220"))){
					httt = "100001"; // Hình thức thanh toán là chuyển khoản
 
					if(chungtu.indexOf("A") >= 0){
						if(sotienNT.equals("0")){ // Tiền VND
							sotaikhoan = "65490509";
							nhId = "100002";
							cnId = "100055";
							ttId = "100000";
						}else{
							sotaikhoan = "6549 0559";
							nhId = "100002";
							cnId = "100055";
							ttId = "100001";
						}
					}
				
					if(chungtu.indexOf("V") >= 0){
						if(sotienNT.equals("0")){ // Tiền VND
							sotaikhoan = "0181000333997";
							nhId = "100039";
							cnId = "100051";
							ttId = "100000";
						
						}else{
							sotaikhoan = "0181372051113";
							nhId = "100039";
							cnId = "100051";
							ttId = "100001";
						}
					}

					if(chungtu.indexOf("U") >= 0){
						if(sotienNT.equals("0")){ // Tiền VND
							sotaikhoan = "1023321179";
							nhId = "100003";
							cnId = "100024";
							ttId = "100000";
							
						}else{
							sotaikhoan = "1029307151";
							nhId = "100003";
							cnId = "100024";
							ttId = "100001";
						}
					}
					query = "SELECT COUNT(*) AS NUM FROM ERP_THANHTOANHOADON WHERE NCC_FK = " + nccId + " AND NGAYGHINHAN = '" + ngaychi + "' ";
					ResultSet count = db.get(query);

					count.next();
					if(count.getString("NUM").equals("0")){	
						
						query = " INSERT INTO ERP_THANHTOANHOADON " +
								" (NGAYGHINHAN, TRANGTHAI, NCC_FK, HTTT_FK, NGANHANG_FK, " + 
								" CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, SOTIENTT, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, PREFIX, LOAITHANHTOAN, " + 
								" SOTIENHD, SOTIENHDNT, PHINGANHANG, PHINGANHANGNT, VAT, VATNT, SOTIENTTNT, TRICHPHI, CHENHLECHVND, TIENTE_FK, TIGIA) " +
								" VALUES(" +
								" '" + ngaychi + "', '1', " + nccId + ", " + httt + ", " + nhId + ", " +
								" " + cnId + ", '" + sotaikhoan + "', N'Thanh toán HĐ mua hàng', " + sotienVND + ", " +
								" '" + this.getDateTime() + "', '100307', '" + this.getDateTime() + "', '100307', '180', '1', " +
								" '0', '0', '0', '0', '0', '0', " + sotienNT + ", '0', '0', '" + ttId + "', " + tigia + ")";
						System.out.println(query);
						if(!db.update(query)){
							System.out.println("Không thành công  : "+query);
						}else{
		
							query = "SELECT SCOPE_IDENTITY() AS ID ";
							ResultSet rs = db.get(query);
			
							if(rs != null){
								rs.next();
								cell = cells.getCell("A" + row);
								cell.setValue(rs.getString("ID"));			
								Id = rs.getString("ID");
								rs.close();
							}
						}
					}else{
						count.close();
						
						query = "SELECT PK_SEQ AS ID FROM ERP_THANHTOANHOADON WHERE NCC_FK = " + nccId + " AND NGAYGHINHAN = '" + ngaychi + "' ";
						System.out.println(query);
						ResultSet rs = db.get(query);
						if(rs != null){
							rs.next();
							cell = cells.getCell("A" + row);
							cell.setValue(rs.getString("ID"));			
							Id = rs.getString("ID");
							rs.close();
						}
					}
				}
			}else{
				
				if(!chungtu.equals("VayVCB") & (tkcoId.equals("11210") || tkcoId.equals("11220"))){
					query = "SELECT PK_SEQ AS ID FROM ERP_THANHTOANHOADON WHERE NCC_FK = " + nccId + " AND NGAYGHINHAN = '" + ngaychi + "' ";
					System.out.println(query);
					ResultSet rs = db.get(query);
					if(rs != null){
						if(rs.next()){
							cell = cells.getCell("A" + row);
							cell.setValue(rs.getString("ID"));			
							Id = rs.getString("ID");
							rs.close();
						}else{
							cell = cells.getCell("A" + row);
							cell.setValue("N/A");									
						}
					}
				}
				
			}
			}
		}

		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);

		}catch(Exception  e){
			e.printStackTrace();
			
		}

	}
	
	private void TaoChitien_Hoadon(dbutils db, Worksheet worksheet, String lanchi){
		Cells cells = worksheet.getCells();
		
		Cell cell;
		
		String query;
		String ngay, ngaytt1, ngaytt2, nccId;
		String sotienTT, sotienNT, sotienAVAT;
		String hdId, ttId, tienteId; 
		String tthd, ttct, sohoadon, ngayhd, sotienttexcel;
		try{
		db.getConnection().setAutoCommit(false);		

		for (int row = 4; row <= 776; row ++){
			
			ngaytt1 = "";
			ngaytt2 = "";
			hdId = "";
			ttId = "";
			sotienTT = "0"; 
			sotienAVAT = "0";
			sotienNT = "0";
			tthd = "";
			sohoadon = "";
			nccId = "";
			ngay = "";
			ttct = "";
			tienteId = "";
			ngayhd = "";
			sotienttexcel = "";
			nccId = "";
			
			cell = cells.getCell("L" + row);
			nccId = cell.getStringValue(); 
			
			cell = cells.getCell("M" + row);
			ngayhd = cell.getStringValue(); 

			cell = cells.getCell("N" + row);
			sohoadon = cell.getStringValue(); 

			cell = cells.getCell("S" + row);
			ngaytt1 = cell.getStringValue(); 

			cell = cells.getCell("Y" + row);
			ngaytt2 = cell.getStringValue(); 
			
			String sotienexcel = "0";
			cell = cells.getCell("O" + row);
			sotienexcel = cell.getStringValue().trim().replaceAll(",", ""); 

			if(sotienexcel.equals("0.00") || sotienexcel.length() == 0){
				cell = cells.getCell("P" + row);
				sotienexcel = cell.getStringValue().trim().replaceAll(",", ""); 							
			}
			if(sotienexcel.trim().length() == 0) sotienexcel = "0";
			boolean exit = false;

			if((lanchi.equals("2") & ngaytt2.length() == 0) || (lanchi.equals("1") & ngaytt1.length() == 0)){
				exit = true;
			}
			
				if(!exit){
	
					// Tìm hóa đơn (đã được thanh toán), theo nhà cung cấp, ngày hóa đơn, số tiền
					
					// chỉ cần đưa những hóa đơn được đánh dấu chưa tìm thấy.
					
					
					query = " SELECT  HD.PK_SEQ AS HDID, PARK.NCC_FK, HD.sotienAVAT, PARK.TRANGTHAI, PARK.TIENTE_FK, HD.NGAYHOADON " +
							" FROM ERP_HOADONNCC HD " +
							" INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " +
							" WHERE PARK.NCC_FK = " + nccId + " AND HD.NGAYHOADON = '" + ngayhd + "' AND HD.sotienAVAT = " + sotienexcel + "";
								
					System.out.println(query);
					ResultSet rs = db.get(query);
									
					// Nếu NccId, ngày hóa đơn, số tiền đúng	
					
					if(rs.next()){
						hdId = rs.getString("HDID");
						sotienAVAT = rs.getString("sotienAVAT");
						nccId = rs.getString("NCC_FK");
						tthd = rs.getString("TRANGTHAI");
						tienteId = rs.getString("TIENTE_FK");
						cell = cells.getCell("H" + row);
						cell.setValue(tthd + " - " + hdId);
						rs.close();
					}else{
	
	// Tìm hóa đơn (đã được thanh toán), theo nhà cung cấp và ngày hóa đơn				
							query = " SELECT  HD.PK_SEQ AS HDID, PARK.NCC_FK, HD.sotienAVAT, PARK.TRANGTHAI, PARK.TIENTE_FK, HD.NGAYHOADON " +
									" FROM ERP_HOADONNCC HD " +
									" INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " +
									" WHERE PARK.NCC_FK = " + nccId + " AND HD.NGAYHOADON = '" + ngayhd + "'  AND HD.SOHOADON = '" + sohoadon + "' ";
							System.out.println(query);
							rs = db.get(query);
											
	// Nếu NccId và ngày hóa đơn đúng				
								if(rs.next()){
									hdId = rs.getString("HDID");
									sotienAVAT = rs.getString("sotienAVAT");
									nccId = rs.getString("NCC_FK");
									tthd = rs.getString("TRANGTHAI");
									tienteId = rs.getString("TIENTE_FK");
									
									cell = cells.getCell("H" + row);
									cell.setValue(tthd + " - " + hdId);
									
									if(tienteId.equals("100001")){
										cell = cells.getCell("O" + row);
										sotienexcel = cell.getStringValue(); 
									}else{
										cell = cells.getCell("P" + row);
										sotienexcel = cell.getStringValue(); 							
									}
									sotienexcel = sotienexcel.replaceAll(",", "");
									
									if(Double.parseDouble(sotienAVAT) != Double.parseDouble(sotienexcel)){
										cell = cells.getCell("I" + row);
										cell.setValue(Double.parseDouble(sotienAVAT));
									}
									rs.close();
								}else{					
						
							
	// Nếu không tìm thấy hóa đơn theo nccId và ngày hóa đơn, thì tìm theo nccId và số tiền						
							
									query = "SELECT  HD.PK_SEQ AS HDID, PARK.NCC_FK, HD.sotienAVAT, PARK.TRANGTHAI, PARK.TIENTE_FK, HD.NGAYHOADON " +
											"FROM ERP_THANHTOANHOADON_HOADON TT_HD " +
											"INNER JOIN ERP_HOADONNCC HD ON HD.PK_SEQ = TT_HD.HOADON_FK " +
											"INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " +
											"WHERE PARK.NCC_FK = " + nccId + " AND HD.sotienAVAT = " + sotienexcel + " AND HD.SOHOADON = '" + sohoadon + "' ";
									rs = db.get(query);
	// Nếu tìm thấy, thì nghĩa là ngày hóa đơn không đúng						
									if(rs.next()){
										hdId = rs.getString("HDID");
										sotienAVAT = rs.getString("sotienAVAT");
										nccId = rs.getString("NCC_FK");
										tthd = rs.getString("TRANGTHAI");
										tienteId = rs.getString("TIENTE_FK");
	
										cell = cells.getCell("H" + row);
										cell.setValue(tthd + " - " + hdId);
	
										cell = cells.getCell("J" + row);
										cell.setValue(rs.getString("NGAYHOADON"));
										rs.close();
									}else{
										cell = cells.getCell("H" + row);
										cell.setValue("HĐ không tìm thấy");				
									}
								}
					
					}
	
					if(lanchi.equals("1")){
						query = "SELECT PK_SEQ AS CTID, SOTIENTT, TRANGTHAI FROM ERP_THANHTOANHOADON WHERE NGAYGHINHAN = '" + ngaytt1 + "' AND NCC_FK = " + nccId + "" ;
					}else{
						query = "SELECT PK_SEQ AS CTID, SOTIENTT, TRANGTHAI FROM ERP_THANHTOANHOADON WHERE NGAYGHINHAN = '" + ngaytt2 + "' AND NCC_FK = " + nccId + "" ;
					}
					System.out.println(query);
					rs = db.get(query);
	
	//Nếu tìm thấy thanh toán lần 1/ lần 2					
					if(rs.next()){
						ttId = rs.getString("CTID");
						sotienTT = rs.getString("SOTIENTT");
							
	// Thanh toán tiền USD						
						if(tienteId.equals("100001")){
							sotienNT = sotienTT;
	// Thanh toán lần 1
							if(lanchi.equals("1")){
								cell = cells.getCell("T" + row);
								sotienttexcel = cell.getStringValue().replaceAll(",", ""); 
									
	// Nếu tiền trong file excel khác với tiền trong DB								
								if(!sotienttexcel.equals(sotienNT)){
									cell = cells.getCell("D" + row);
									cell.setValue(rs.getString("TRANGTHAI") + " - " + ttId );
										
									cell = cells.getCell("E" + row);
									cell.setValue(Double.parseDouble(sotienNT));
	
								}else{
									cell = cells.getCell("D" + row);
									cell.setValue(rs.getString("TRANGTHAI") + " - " + ttId );									
								}
	
	// Thanh toán lần 2								
							}else{
								cell = cells.getCell("X" + row);
								sotienttexcel = cell.getStringValue().replaceAll(",", ""); 
	
	//Nếu tiền trong file excel khác với tiền trong DB								
								if(sotienttexcel.equals(sotienNT)){
									cell = cells.getCell("F" + row);
									cell.setValue(rs.getString("TRANGTHAI") + " - " + ttId );
	
									cell = cells.getCell("G" + row);
									cell.setValue(Double.parseDouble(sotienNT));
										
								}else{
									cell = cells.getCell("F" + row);
									cell.setValue(rs.getString("TRANGTHAI") + " - " + ttId );									
								}
							}
	// Thanh toán tiền VND				
						}else{ 
							sotienNT = "0";
	// Thanh toán lần 1
							if(lanchi.equals("1")){
								cell = cells.getCell("S" + row);
								sotienttexcel = cell.getStringValue().replaceAll(",", ""); 
									
	// Nếu tiền trong file excel khác với tiền trong DB								
								if(!sotienttexcel.equals(sotienTT)){
									cell = cells.getCell("D" + row);
									cell.setValue(rs.getString("TRANGTHAI") + " - " + ttId );
	
									cell = cells.getCell("E" + row);
									cell.setValue(Double.parseDouble(sotienTT));
										
								}else{
									cell = cells.getCell("D" + row);
									cell.setValue(rs.getString("TRANGTHAI") + " - " + ttId );									
								}
	
	// Thanh toán lần 2								
							}else{
								cell = cells.getCell("Y" + row);
								sotienttexcel = cell.getStringValue().replaceAll(",", ""); 
	
	//Nếu tiền trong file excel khác với tiền trong DB								
								if(!sotienttexcel.equals(sotienTT)){
									cell = cells.getCell("F" + row);
									cell.setValue(rs.getString("TRANGTHAI") + " - " + ttId );
										
									cell = cells.getCell("G" + row);
									cell.setValue(Double.parseDouble(sotienTT));
	
								}else{
									cell = cells.getCell("F" + row);
									cell.setValue(rs.getString("TRANGTHAI") + " - " + ttId );									
								}
							}
								
						}
						rs.close();
					}
	
					if(hdId.length() > 0 & ttId.length() > 0 & tthd.equals("1")){
						if(lanchi.equals("1")){
							ngay = ngaytt1;
						}else{
							ngay = ngaytt2;
						}
					
						if(ngay.substring(0, 7).equals("2014-02") || ngay.substring(0, 7).equals("2014-03") || ngay.substring(0, 7).equals("2014-01")){
							query = "SELECT COUNT(*) AS NUM FROM ERP_THANHTOANHOADON_HOADON WHERE THANHTOANHD_FK = " + ttId + " AND HOADON_FK = " + hdId + "";
							rs = db.get(query);
	
	// Chua co trong ERP_THANHTOANHOADON_HOADON							
							if(rs != null){
								rs.next();
								if (rs.getString("NUM").equals("0")){
									query = "INSERT INTO ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI, SOTIENNT) " +
											"VALUES(" +  ttId +", " + hdId + ", " + Double.parseDouble(sotienTT) + ", " + Double.parseDouble(sotienAVAT) + ", " +
											"" + (Double.parseDouble(sotienAVAT) - Double.parseDouble(sotienTT)) + ", " + Double.parseDouble(sotienNT) + ")";
							
									System.out.println("" + row + ": " + query);
								
									db.update(query);
								}									
								rs.close();
							}
						}
					}
				}
		}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.toString());
		}
		
	}

	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
