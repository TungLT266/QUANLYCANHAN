package geso.traphaco.erp.servlets.mauinlenhsanxuat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;

public class ErpGiaiDoanCanChia_GiaoNhanNguyenLieuDaCan {


	public static int Create (int i,String lsxid, String cdidcurrent,String gdid, String tengd,  dbutils db, Worksheet worksheet, Cells cells,  String pathPDF){	
		System.out.println("=============GD Giao nhan nl ban dau da can============"+gdid);
		System.out.println("============cdidcurrent============="+cdidcurrent);
		System.out.println("============lsxid============="+lsxid);
		int sodong=0;
		int socot=51;
		int some=0;
		String sql = "select me from ERP_HOSOCDSX_CHITIET where GIAIDOANSANXUAT_FK="+gdid+" "
				+ " and HOSOCDSX_FK=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK="+lsxid+" and CONGDOANSANXUAT_FK="+cdidcurrent+") "
				+ " group by me";
		System.out.println("CAU QUERY SL ME: " + sql);
		ResultSet rsSoMe=db.get(sql);
		ArrayList<String> arrSoMe = new ArrayList<String>();
		if(rsSoMe!=null){
			try {
				while(rsSoMe.next()){
					arrSoMe.add(rsSoMe.getString("me"));
				}
			} catch (SQLException e) {
	
				e.printStackTrace();
			}
		}
		System.out.print("SO ME: " + arrSoMe.size());
		some = arrSoMe.size();
		/*ArrayList<Integer> arrMerge=new ArrayList<Integer>();*/
		
		/* Lấy tổng số mẻ */
		/*String querySoMe = "SELECT DISTINCT ISNULL(SOLUONGME,0) AS  SOLUONGME "
				+ "\n FROM ERP_LENHSANXUAT_GIAY A left join erp_dondathang ddh on ddh.pk_seq=a.dondathang_fk "
				+ "\n INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK  LEFT JOIN ERP_KICHBANSANXUAT_GIAY KB ON KB.PK_SEQ=KICHBANSANXUAT_FK "
				+ "\n WHERE A.PK_SEQ = '" + lsxid + "'";
		System.out.println("=========================Query Số Mẻ:" + querySoMe);
		ResultSet rsSoMe = db.get(querySoMe);
		if (rsSoMe != null) {
			try {
				while (rsSoMe.next()) {
					some = rsSoMe.getInt("SOLUONGME");
				}
				rsSoMe.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}*/
		
		System.out.println(" bat dau in");
		System.out.println(" dong bat dau gd 4.2: " + i);
		String tenCD="";
		String tenGD="";
		String dong="";
		
		Style style;
		Font font_12_bold=new Font();
		font_12_bold.setName("Times New Roman");
		font_12_bold.setSize(12);
		font_12_bold.setBold(true);
		
		Font font_12=new Font();
		font_12.setName("Times New Roman");
		font_12.setSize(12);
		
	 
		Cell cell;
		/*Tên Công Đoạn 
		//String tenCD="";
		ReportAPI.mergeCells(worksheet, i, i, 0, 51);
		Cell cell = cells.getCell("A" + Integer.toString(i+1));
		cell.setValue(tenCD);
		style=cell.getStyle();
		style.setFont(font_12_bold);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		cell.setStyle(style);
		cells.setRowHeightPixel(i, 27);*/
		
		/*Tên Giai Đoạn*/
		/*ReportAPI.mergeCells(worksheet, i, i,0,51);
		Cell cell = cells.getCell("A" + Integer.toString(i+1));
		//cell = cells.getCell("A" + Integer.toString(i+2));
		cell.setValue(tengd);
		style=cell.getStyle();
		style.setFont(font_12_bold);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.JUSTIFY); 
		cell.setStyle(style);
		cells.setRowHeightPixel(i, 27);*/
		
		FormatExcel.mergeCells_Style12(worksheet, i, i, 0, 51,"A",tengd,true,true,27,TextAlignmentType.CENTER);
		
		System.out.println("TEN GIAI DOAN: " + tengd);
		
		/*Dòng trắng*/
	/*	ReportAPI.mergeCells(worksheet, i+1, i+1, 0,51);
		cell = cells.getCell("A" + Integer.toString(i+2));
		cell.setValue(dong);
		style=cell.getStyle();
		style.setFont(font_12);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		cell.setStyle(style);
		cells.setRowHeightPixel(i, 27);*/
		
		FormatExcel.mergeCells_Style12(worksheet, i+1, i+1, 0, 51,"A",dong,true,true,27,TextAlignmentType.CENTER);
		
		// Thứ tự
		/*ReportAPI.mergeCells(worksheet, i+2, i+3, 0,0);
		cell = cells.getCell("A" + Integer.toString(i+3));
		cell.setValue("STT");
		style=cell.getStyle();
		style.setFont(font_12_bold);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		cell.setStyle(style);
		cells.setRowHeightPixel(i, 27);*/
		
		FormatExcel.mergeCells_Style12(worksheet, i+2, i+3, 0, 0,"A","STT",true,true,27,TextAlignmentType.CENTER);
		
		/*Tên nguyên liệu*/
		/*ReportAPI.mergeCells(worksheet, i+2, i+3, 1,6);
		cell = cells.getCell("B" + Integer.toString(i+3));
		cell.setValue("Tên nguyên liệu");
		style=cell.getStyle();
		style.setFont(font_12_bold);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		cell.setStyle(style);
		cells.setRowHeightPixel(i, 27);*/
		
		FormatExcel.mergeCells_Style12(worksheet, i+2, i+3, 1, 6,"B","Tên nguyên liệu",true,true,27,TextAlignmentType.CENTER);
		
		//Yêu cầu đóng gói lại, ghi nhãn
		/*ReportAPI.mergeCells(worksheet, i+2, i+3, 7,21);
		cell = cells.getCell("H" + Integer.toString(i+3));
		cell.setValue("Yêu cầu đóng gói lại, ghi nhãn");
		style=cell.getStyle();
		style.setFont(font_12_bold);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		cell.setStyle(style);
		cells.setRowHeightPixel(i, 27);*/
		
		FormatExcel.mergeCells_Style12(worksheet, i+2, i+3, 7, 21,"H","Yêu cầu đóng gói lại, ghi nhãn",true,true,27,TextAlignmentType.CENTER);
		
		//Ô trắng
		/*ReportAPI.mergeCells(worksheet, i+2, i+3, 22,27);
		cell = cells.getCell("W" + Integer.toString(i+3)); 
		style=cell.getStyle();
		style.setFont(font_12_bold);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		cell.setStyle(style);
		cells.setRowHeightPixel(i, 27); */
		
		FormatExcel.mergeCells_Style12(worksheet, i+2, i+3, 22, 27,"W","",true,true,27,TextAlignmentType.CENTER);
		
		/*Lô*/
		/*ReportAPI.mergeCells(worksheet, i+2, i+2,28,51);
		cell = cells.getCell("AC" + Integer.toString(i+3));
		cell.setValue("Lô");
		style=cell.getStyle();
		style.setFont(font_12_bold);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		cell.setStyle(style);
		cells.setRowHeightPixel(i, 27);*/
		
		FormatExcel.mergeCells_Style12(worksheet, i+2, i+2, 28, 51,"AC","Lô",true,true,27,TextAlignmentType.CENTER);
	
		// in số mẻ
		// 24 là số cột để cho phần số mẻ
		 int tongso_cot= 24;
		
		 /*Merge mẻ*/
		 String[] arrMe =getMangPhanBoMe(some ,tongso_cot);
		 int cotdautien=28;
		 System.out.println("DO DAI MANG ME: " + arrMe.length);
		 for(int k=0;k<arrMe.length ;k++){
			 System.out.println("ME THU "+k+": " + arrMe[k]);
			 /*ReportAPI.mergeCells(worksheet, i+3,i+3,cotdautien,cotdautien+Integer.parseInt(arrMe[k])-1);
			 cell = cells.getCell(i+3,cotdautien);
			 cell.setValue("Mẻ "+ (k+1));
			 style=cell.getStyle();
			style.setFont(font_12_bold);
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setVAlignment(TextAlignmentType.JUSTIFY);
			cell.setStyle(style);
			cells.setRowHeightPixel(i+3, 27);*/
			 
			 FormatExcel.mergeCells_Style12(worksheet, i+3, i+3, cotdautien, cotdautien+Integer.parseInt(arrMe[k])-1,cotdautien,"Mẻ "+ (k+1),true,true,27,TextAlignmentType.CENTER);
				
			 /*String[] mangTS=getMangPhanBoMe(arrTS.size(), Integer.parseInt(arrMe[k]));
			 int m=cotdautien;
			 for (int j = 0; j < mangTS.length; j++) {
				 ReportAPI.mergeCells(worksheet, i+5,i+5,m,m+Integer.parseInt(mangTS[j])-1);
				 cell = cells.getCell(i+5,m);
				 cell.setValue(arrTS.get(j));
				 style=cell.getStyle();
					style.setFont(font_12_bold);
					style.setHAlignment(TextAlignmentType.CENTER);
					style.setVAlignment(TextAlignmentType.JUSTIFY);
					cell.setStyle(style);
					cells.setRowHeightPixel(i+5, 27);
	
				 m+=Integer.parseInt(mangTS[j]);
			}*/
			 cotdautien+=Integer.parseInt(arrMe[k]);
		 } 
		System.out.println("============================getMaxColumn:"+cells.getMaxColumn());
		System.out.println("============================getMaxDataColumn:"+cells.getMaxDataColumn());
		System.out.println("============================getMaxDataRow:"+cells.getMaxDataRow());
		System.out.println("============================getMaxRow:"+cells.getMaxRow());
		System.out.println("============================getMaxRow:"+cells.getCellIterator());
		/*System.out.println("============================Các cột megr:"+cells.getMergedCells().);*/
		/*Đổ dữ liệu chi tiết*/
		
		/*Lấy tổng số cột sau khi Merged*/
		//cell=cells.getColumns();
		//System.out.println("============================getMaxRow:"+cell.getRowIndex());
		
		String query="";
		
		/*Pha chế*/
		/*ReportAPI.mergeCells(worksheet, i+4, i+4,0,51);
		cell = cells.getCell("A" + Integer.toString(i+5));
		cell.setValue("Pha chế");
		style=cell.getStyle();
		style.setFont(font_12_bold);
		style.setHAlignment(TextAlignmentType.LEFT);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		cell.setStyle(style);
		cells.setRowHeightPixel(i, 27);*/
		
		FormatExcel.mergeCells_Style12(worksheet, i+4, i+4, 0, 51,"A","Pha chế",true,true,27,TextAlignmentType.LEFT);
		
		//Tên Nguyên liệu
		query = " select c.PK_SEQ pk_seq, c.TEN TenNguyenLieu from ERP_HOSOCDSX_CHITIET_THONGSO a inner join \r\n" + 
				" ERP_HOSOCDSX_CHITIET b on a.HOSOCDSX_CHITIET_FK=b.PK_SEQ\r\n" + 
				" inner join ERP_SANPHAM c on c.PK_SEQ=b.SANPHAM_FK\r\n" + 
				" where b.GIAIDOANSANXUAT_FK="+gdid+" and b.HOSOCDSX_FK=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK="+lsxid+" and CONGDOANSANXUAT_FK="+cdidcurrent+") \r\n" + 
				" group by c.TEN, c.PK_SEQ";
		System.out.println("CAU QUERY NGUYEN LIEU: " + query);
		ResultSet rsNguyenLieu = db.get(query);
		ArrayList<String> arrThongSo = null;
		int dongbd = i+5;
		System.out.println("DONG BAT DAU: " + dongbd);
		int dongbdnguyenlieu = dongbd;
		if(rsNguyenLieu!=null){
			int stt = 1; 
			try {
				while(rsNguyenLieu.next()){ 
					String nguyenLieuId = rsNguyenLieu.getString("pk_seq");
					String nguyenLieuTen = rsNguyenLieu.getString("TenNguyenLieu");
					
					//Thứ tự
					/*ReportAPI.mergeCells(worksheet, dongbdnguyenlieu, dongbdnguyenlieu+2,0,0);
					cell = cells.getCell("A" + Integer.toString(dongbdnguyenlieu+1));
					cell.setValue(stt);
					style=cell.getStyle();
					style.setFont(font_12);
					style.setHAlignment(TextAlignmentType.CENTER);
					style.setVAlignment(TextAlignmentType.JUSTIFY);
					cell.setStyle(style);
					cells.setRowHeightPixel(i, 27);*/
					
					FormatExcel.mergeCells_Style12(worksheet, dongbdnguyenlieu, dongbdnguyenlieu+2, 0, 0,"A",String.valueOf(stt),true,false,27,TextAlignmentType.CENTER);
					
					//Nguyên liệu
					/*ReportAPI.mergeCells(worksheet, dongbdnguyenlieu, dongbdnguyenlieu+2,1,6);
					cell = cells.getCell("B" + Integer.toString(dongbdnguyenlieu+1));
					cell.setValue(nguyenLieuTen);
					style=cell.getStyle();
					style.setFont(font_12);
					style.setHAlignment(TextAlignmentType.CENTER);
					style.setVAlignment(TextAlignmentType.JUSTIFY);
					cell.setStyle(style);
					cells.setRowHeightPixel(i, 27);*/
					
					FormatExcel.mergeCells_Style12(worksheet, dongbdnguyenlieu, dongbdnguyenlieu+2, 1, 6,"B",nguyenLieuTen,true,false,27,TextAlignmentType.LEFT);
					
					query = " select b.THONGSOKYTHUAT tenthongso from  ERP_GIAIDOANSX_THIETBI a \r\n" + 
							"inner join ERP_GIAIDOANSX_THIETBI_CHITIET b on a.PK_SEQ=b.GIAIDOAN_TB_FK\r\n" + 
							"where a.GIAIDOAN_FK="+gdid+" order by b.stt";
					System.out.println("CAU QUERY THONG SO KY THUAT: " + query);
					ResultSet rsThongSo = db.get(query); 
				 		
					arrThongSo = new ArrayList<String>();
					int dongbdthongso = dongbdnguyenlieu;
					if(rsThongSo!=null){
						while(rsThongSo.next()){
							
							arrThongSo.add(rsThongSo.getString("tenthongso"));
							String thongso = rsThongSo.getString("tenthongso");
							System.out.println("TEN THONG SO: " + thongso);
							//Thông số
							/*ReportAPI.mergeCells(worksheet, dongbdthongso, dongbdthongso,22,27);
							System.out.println("DONG BAT DAU TEN THONG SO: " + dongbdthongso);
							cell = cells.getCell("W" + Integer.toString(dongbdthongso+1));
							cell.setValue(thongso);
							style=cell.getStyle();
							style.setFont(font_12);
							style.setHAlignment(TextAlignmentType.CENTER);
							style.setVAlignment(TextAlignmentType.JUSTIFY); 
							cell.setStyle(style);
							cells.setRowHeightPixel(i, 27);*/
							
							FormatExcel.mergeCells_Style12(worksheet, dongbdthongso, dongbdthongso, 22, 27,"W",thongso,true,false,27,TextAlignmentType.CENTER);
							
							dongbdthongso++;
						}
					}
					
					System.out.println("-----ARR THONG SO SIZE-----: " + arrThongSo.size());
					System.out.println("STT: " + stt + " -- " + "Ten Nguyen Lieu: " + nguyenLieuTen);
					System.out.println("DONG BAT DAU: " + dongbdnguyenlieu);
					stt++; 	
					dongbdnguyenlieu = dongbdnguyenlieu + arrThongSo.size();
				
				}
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}
		int cotgtdau = 28;
		for(int j=0; j<arrMe.length; j++){
			int sttme = j+1;
			query = " select c.PK_SEQ NguyenLieuId, c.TEN TenNguyenLieu,  a.TENTHONGSO tenthongso, b.me, a.LOAITHONGSO loaithongso, a.GIATRITHONGSO giatrits \r\n" + 
					" from ERP_HOSOCDSX_CHITIET_THONGSO a inner join \r\n" + 
					" ERP_HOSOCDSX_CHITIET b on a.HOSOCDSX_CHITIET_FK=b.PK_SEQ\r\n" + 
					" inner join ERP_SANPHAM c on c.PK_SEQ=b.SANPHAM_FK\r\n" + 
					" where b.GIAIDOANSANXUAT_FK="+gdid+" and b.HOSOCDSX_FK=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK="+lsxid+" and CONGDOANSANXUAT_FK="+cdidcurrent+") and b.me="+sttme+"\r\n" + 
					" order by c.TEN";
			System.out.println("CAU QUERY GIA TRI THONG SO: " + query);
			ResultSet rsGiaTriTS = db.get(query);
			int dongbdgiatri = dongbd;
			if(rsGiaTriTS!=null){
				try {
					while(rsGiaTriTS.next()){
						String loaits = rsGiaTriTS.getString("loaithongso");
						String giatrits = rsGiaTriTS.getString("giatrits");
						if(giatrits.equals("")){
							giatrits="0";
						}
						String chk1="";
						//Giá trị mẻ
						//ReportAPI.mergeCells(worksheet, dongbdgiatri, dongbdgiatri,cotgtdau,cotgtdau+Integer.parseInt(arrMe[j])-1);
						//cell = cells.getCell("AC" + Integer.toString(dongbdgiatri+1));
						cell = cells.getCell(dongbdgiatri,cotgtdau);
						if(loaits.equals("1") && giatrits.equals("1")){
							//cell.setValue(chk1="☑");
							FormatExcel.mergeCells_Style12(worksheet, dongbdgiatri, dongbdgiatri, cotgtdau, cotgtdau+Integer.parseInt(arrMe[j])-1,cotgtdau,chk1="☑",true,true,27,TextAlignmentType.CENTER);
						}else if(loaits.equals("1") && giatrits.equals("0")){
							//cell.setValue(chk1="☐");
							FormatExcel.mergeCells_Style12(worksheet, dongbdgiatri, dongbdgiatri, cotgtdau, cotgtdau+Integer.parseInt(arrMe[j])-1,cotgtdau,chk1="☐",true,true,27,TextAlignmentType.CENTER);
						}else{
							//cell.setValue(giatrits);
							FormatExcel.mergeCells_Style12(worksheet, dongbdgiatri, dongbdgiatri, cotgtdau, cotgtdau+Integer.parseInt(arrMe[j])-1,cotgtdau,giatrits,true,true,27,TextAlignmentType.CENTER);
						}
						//cell = cells.getCell(dongbdgiatri,cotgtdau);
						style=cell.getStyle();
						style.setFont(font_12);
						style.setHAlignment(TextAlignmentType.CENTER);
						style.setVAlignment(TextAlignmentType.JUSTIFY);
						cell.setStyle(style);
						cells.setRowHeightPixel(i, 27);
						dongbdgiatri++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			cotgtdau+=Integer.parseInt(arrMe[j]);
		}
		
		System.out.println("-----COT GIA TRI DAU-----: " + cotgtdau);
		System.out.println("-----DONG BD-----: " + dongbd);
		System.out.println("-----DONG BD NL-----: " + dongbdnguyenlieu); 
		
		//Yêu cầu đóng gói, ghi nhãn
		/*ReportAPI.mergeCells(worksheet, dongbd, dongbdnguyenlieu-1,7,12);
		cell = cells.getCell("H" + Integer.toString(dongbd+1));
		cell.setValue("Nguyên liệu đóng trong 2 lần túi PE dày..., không màu, buộc kín.");
		style=cell.getStyle();
		style.setFont(font_12);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.TOP);
		style.setTextWrapped(true);
		cell.setStyle(style);
		cells.setRowHeightPixel(i, 27);*/
		
		FormatExcel.mergeCells_Style12_new(worksheet, dongbd, dongbdnguyenlieu-1, 7, 12,"H","Nguyên liệu đóng trong 2 lần túi PE dày..., không màu, buộc kín.",true,false,27,TextAlignmentType.CENTER, TextAlignmentType.TOP);
		
		/*ReportAPI.mergeCells(worksheet, dongbd, dongbdnguyenlieu-1,13,21);
		cell = cells.getCell("N" + Integer.toString(dongbd+1));
		cell.setValue("Thông tin trên nhãn phải phù hợp với thông tin ghi trên hồ sơ và trên lệnh SX (SL, NSX, HD…)");
		style=cell.getStyle();
		style.setFont(font_12);
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.TOP);
		style.setTextWrapped(true);
		cell.setStyle(style);
		cells.setRowHeightPixel(i, 27);*/
		
		FormatExcel.mergeCells_Style12_new(worksheet, dongbd, dongbdnguyenlieu-1, 13, 21,"N","Thông tin trên nhãn phải phù hợp với thông tin ghi trên hồ sơ và trên lệnh SX (SL, NSX, HD…)",true,false,27,TextAlignmentType.CENTER, TextAlignmentType.TOP);
		
		FormatExcel.setborder_shadow(cells, i, 0, dongbdnguyenlieu, 51); 
		return dongbdnguyenlieu;
	}
	

	private static String[] getMangPhanBoMe(int some, int tongso_cot) {
		// TODO Auto-generated method stub
		
		String[] mang=new String[some];
		
		System.out.println("tongso_cot: "+tongso_cot);
		int so_o_merce=tongso_cot/some;
		System.out.println("so_o_merce: "+so_o_merce);
		int so_o_du= tongso_cot- so_o_merce* some;
		System.out.println("so_o_du :" +so_o_du); 
		
		for(int i=0;i<mang.length;i++){
			if(so_o_du >0){
					mang[i]= (so_o_merce +1)+"";
					so_o_du--; 
			}else {
				mang[i]= (so_o_merce)+""; 
			}
		}
		return mang;
		
	}


	
}
