package geso.traphaco.erp.servlets.mauinlenhsanxuat;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;
import geso.traphaco.center.beans.dondathang.IErpDondathang;
import geso.traphaco.center.beans.dondathang.imp.ErpDondathang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.servlets.report.ReportAPI;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;
import com.extentech.ExtenXLS.WorkSheet;
import com.extentech.formats.XLS.Array;
import com.oreilly.servlet.MultipartRequest;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Woodstox;


public class ErpPhaCheXuLyNguyenLieu {
	PrintWriter out;
	public ErpPhaCheXuLyNguyenLieu() {

	}

	public static int Create(int i, String lsxid, String cdidcurrent,
			String gdid, String tengd, dbutils db, Worksheet worksheet,
			Cells cells, String pathPDF) {
		String pathPage=pathPDF.substring(0, pathPDF.lastIndexOf("\\images")+1)+"upload\\";
		System.out.println("=========================gdid:" +pathPage);
		
		/* int socot=51; */
		int some = 0;
		int cotdbct = 20;
		
		/*Lấy tên công đoạn*/
		String tenCD = "";
		String queryTenCD="select DIENGIAI from Erp_CongDoanSanXuat_Giay where pk_seq='"+cdidcurrent+"'";
		System.out.println("=========================Query lay ten CD:" + queryTenCD);
		ResultSet rsTenCD = db.get(queryTenCD);
		if (rsTenCD != null) {
			try {
				while (rsTenCD.next()) {
					tenCD = rsTenCD.getString("DIENGIAI");
				}
				rsTenCD.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/* Lấy tổng số mẻ */
		String querySoMe = "SELECT DISTINCT ISNULL(SOLUONGME,0) AS  SOLUONGME "
				+ "\n FROM ERP_LENHSANXUAT_GIAY A left join erp_dondathang ddh on ddh.pk_seq=a.dondathang_fk "
				+ "\n INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK  LEFT JOIN ERP_KICHBANSANXUAT_GIAY KB ON KB.PK_SEQ=KICHBANSANXUAT_FK "
				+ "\n WHERE A.PK_SEQ = '" + lsxid + "'";
		System.out.println("=========================Query so me:" + querySoMe);
		ResultSet rsSoMe = db.get(querySoMe);
		if (rsSoMe != null) {
			try {
				while (rsSoMe.next()) {
					some = rsSoMe.getInt("SOLUONGME");
				}
				rsSoMe.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int sodong = i;
	
		System.out.println(" bat dau in");
		
		String dong = "";

		/* Tên Công Đoạn */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 51,"A",tenCD.toUpperCase(),true,true,27,TextAlignmentType.CENTER);
		sodong++;
		/* Tên Giai Đoạn */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 51,"A",tengd,true,true,27,TextAlignmentType.LEFT);
		sodong++;

		/* Hoạt động */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 0, 11,"A","Hoạt động",true,true,27,TextAlignmentType.CENTER);
		
		/* Yêu cầu */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 12, 19,"M","Yêu cầu",true,true,27,TextAlignmentType.CENTER);
		
		/* Thực hiện */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 20, 51,"U","Thực hiện",true,true,27,TextAlignmentType.CENTER);
		sodong=sodong+1;

		// in số mẻ
		// 32 là số cột để cho phần số mẻ
		int tongso_cot = 32;
		/* Mảng merge mẻ */
		String[] arrMe = getMangPhanBoMe(some, tongso_cot);
		
		/*cotdautien(cột bắt đầu merge mẻ) của tiêu đề mẻ*/
		int cotdautien = cotdbct;
		int g=sodong;/*số dòng hiện tại đang đứng khi bắt đầu merge mẻ*/
		
		/*Duyệt mảng arrMe*/
		for (int k = 0; k < arrMe.length; k++) {
			FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, cotdbct,cotdbct + Integer.parseInt(arrMe[k]) - 1,cotdbct,
					"Mẻ " + (k + 1),true,true,41,TextAlignmentType.CENTER);
			cotdbct += Integer.parseInt(arrMe[k]);
		}
		sodong++;
		
		String queryCountTSChung="select distinct isnull(tb.ten,kbsx_cdsx_ts.THONGSOCHUNG) as THONGSOCHUNG,count(isnull(tb.ten,  kbsx_cdsx_ts.THONGSOCHUNG)) as tongCT "
				+ "\n from Erp_kichbansx_cdsx_thongso kbsx_cdsx_ts left join ERP_GIAIDOANSX gd on gd.PK_SEQ = kbsx_cdsx_ts.GIAIDOANSX_FK "
				+ "\n left join ERP_THIETBISX tb on tb.PK_SEQ = kbsx_cdsx_ts.THIETBISX_FK "
				+ "\n where kbsx_cdsx_ts.GIAIDOANSX_FK='"+gdid+"' and kbsx_cdsx_ts.chon=1 "
				+ "\n group by tb.ten,kbsx_cdsx_ts.THONGSOCHUNG";
		ResultSet rsCountTSChung=db.get(queryCountTSChung);
		int countTSChung=0;
		int soDongChiTietTS=0;
		if (rsCountTSChung!=null) {
			try {
				while(rsCountTSChung.next()){
					countTSChung++;
					soDongChiTietTS=rsCountTSChung.getInt("tongCT");
				}rsCountTSChung.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		/*đỗ dữ liệu cho thông số chung*/
		ArrayList<String> arrTam = new ArrayList<String>();
		String queryMe = "select DISTINCT kbsx_cdsx_ts.STT, isnull(tb.TEN,  kbsx_cdsx_ts.THONGSOCHUNG) as THONGSOCHUNG, kbsx_cdsx_ts.THONGSOKYTHUAT, "
				+ "\n kbsx_cdsx_ts.YEUCAU, isnull(kbsx_cdsx_ts.ISCHECK, 0) as ischeck, isnull(dvdl.donvi,'') as dvdl,"
				+ "\n cast(kbsx_cdsx_ts.fileupload as varchar(max) ) as fileupload "
				+ "\n from Erp_kichbansx_cdsx_thongso kbsx_cdsx_ts left join ERP_GIAIDOANSX gd on gd.PK_SEQ = kbsx_cdsx_ts.GIAIDOANSX_FK "
				+ "\n left join ERP_THIETBISX tb on tb.PK_SEQ = kbsx_cdsx_ts.THIETBISX_FK "
				+ "\n left join DONVIDOLUONG dvdl on dvdl.PK_SEQ = kbsx_cdsx_ts.DVDL_FK "
				+ "\n where kbsx_cdsx_ts.GIAIDOANSX_FK='"+gdid+"' and kbsx_cdsx_ts.chon=1 "
				+ "\n order by kbsx_cdsx_ts.STT";
		System.out.println("===============Query Thong So Ky Thuat================" + queryMe);
		ResultSet rsTS = db.get(queryMe);
		String thongsochung="";
		String thongsomoi="";
		int count=0;
		if (rsTS != null) {
			try {
				
				while (rsTS.next()) {
					
					arrTam.add(rsTS.getString("ischeck"));
					arrTam.add(" ");
					thongsochung=rsTS.getString("THONGSOCHUNG");
					
					if(thongsochung.trim().length() > 0){
						thongsomoi=rsTS.getString("THONGSOCHUNG");
						FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 3, 11,"D",rsTS.getString("THONGSOKYTHUAT"),true,false,27,TextAlignmentType.LEFT);
						count++;
					}
					else{
						FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 11,"A",rsTS.getString("THONGSOKYTHUAT"),true,false,27,TextAlignmentType.LEFT);
					}
					if(!thongsomoi.equals(thongsochung) && count > 0){
						FormatExcel.mergeCells_Style12(worksheet, sodong-count, sodong-1, 0, 2,"A",thongsomoi,true,false,27,
								TextAlignmentType.CENTER,TextAlignmentType.TOP);
						count=0;
					}
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 12, 19,"M",rsTS.getString("yeucau"),true,false,27,TextAlignmentType.CENTER);
				
					/*Đổ dữ liệu thông số chung cho từng mẻ*/
					cotdautien = 20;
					for (int k = 0; k < arrMe.length; k++) {
						String queryCTTS="select DISTINCT b.giatrithongso "
								+ "\n from ERP_HOSOCDSX_CHITIET a inner join  ERP_HOSOCDSX_CHITIET_THONGSO b on a.pk_seq=b.HOSOCDSX_CHITIET_FK"
								+ "\n  where HOSOCDSX_FK=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"+lsxid+"' "
								+ "\n and CONGDOANSANXUAT_FK='"+cdidcurrent+"') and GIAIDOANSANXUAT_FK='"+gdid+"' and me="+(k+1)+""
								+ "\n and b.loaithongso="+rsTS.getString("ischeck")+" "
								+ "\n and yeucau=N'"+rsTS.getString("yeucau")+"'and TENTHONGSOKYTHUAT=N'"+rsTS.getString("THONGSOKYTHUAT")+"'";
						ResultSet rsCTTS=db.get(queryCTTS);
						String ts="";
						if (rsCTTS!=null) {
							if(rsCTTS.next()==false){
								
								if(rsTS.getString("ischeck").equals("0")){
									ts="... "+rsTS.getString("dvdl").toLowerCase();
								}
								else{
									ts="☐";
								}
								
							}else{
								do {
									ts=getGiaTriThongSo(rsTS.getString("ischeck"), rsCTTS.getString("giatrithongso"), rsTS.getString("dvdl"),1);
									
								} while (rsCTTS.next());
							}rsCTTS.close();
						}
						FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, cotdautien,cotdautien + Integer.parseInt(arrMe[k]) - 1,cotdautien,
								ts,true,false,40,TextAlignmentType.CENTER);
						cotdautien += Integer.parseInt(arrMe[k]);
						//count=0;
					}
					
					
						sodong=sodong+1;
					
					
					String filepath=rsTS.getString("fileupload");
					if(filepath.length() > 0){
						pathPage+=filepath.substring(filepath.lastIndexOf("\\")+1, filepath.length());;
						sodong=readExcel(pathPage,sodong,worksheet);
					}
					
				}
				rsTS.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(countTSChung == 1){
			FormatExcel.mergeCells_Style12(worksheet, sodong-soDongChiTietTS, sodong-1, 0, 2,"A",thongsochung,true,false,27,
					TextAlignmentType.CENTER,TextAlignmentType.TOP);
			//sodong=sodong+soDongChiTietTS;
			//count=0;
		}
		
		String[] arrCuoi={"Thời gian thực hiện","Người thực hiện","Người kiểm tra"};
		String[] arrYCCuoi={" ","CN được đào tạo","Tổ trưởng","DBCL"};
		for (int j = 0; j < arrCuoi.length; j++) {
			/* Hoạt động */
			if(j==0 ||j==arrCuoi.length-1){
				FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 0, 11,"A",arrCuoi[j],true,false,true,27,TextAlignmentType.LEFT);
			}
			else{
				FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 11,"A",arrCuoi[j],true,false,true,27,TextAlignmentType.LEFT);
			}
			
			 /*Thực hiện*/ 
			if (j==0) {
				
				mergeTheoMe(worksheet,sodong,20,arrMe,0);
				
			}
			else if (j== arrCuoi.length-1) {
				mergeTheoMe(worksheet,sodong,20,arrMe,1);
				mergeTheoMe(worksheet,sodong+1,20,arrMe,1);
				
			}
			else{
				mergeTheoMe(worksheet,sodong,20,arrMe,1);
				
			}/*
			 Yêu cầu */
			if (j == arrCuoi.length-1) {
				
				FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 12, 19,"M",arrYCCuoi[j],true,false,27,TextAlignmentType.CENTER);
				sodong++;
				FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 12, 19,"M",arrYCCuoi[j+1],true,false,27,TextAlignmentType.CENTER);
				//sodong++;

			}else if(j==0){
				FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 12, 19,"M",arrYCCuoi[j],true,false,27,TextAlignmentType.CENTER);
				sodong++;
			}
			else{
				FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 12, 19,"M",arrYCCuoi[j],true,false,27,TextAlignmentType.CENTER);
			}
			sodong++;
			
		}
		//sodong++;
		String ghichu="";
		String query =" select Ghichu from ERP_HOSOCDSX_GHICHU where hosocdsx_fk=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"
							+ lsxid + "' and CONGDOANSANXUAT_FK='"
							+ cdidcurrent + "') and GIAIDOAN_FK="+gdid + "" ;
		System.out.println("query lay ghichu: "+query);
		ResultSet ghichuRs = db.get(query);
		if(ghichuRs!=null){
			try {
				while(ghichuRs.next()){
					ghichu=ghichuRs.getString("ghichu");
				}
				ghichuRs.close();
			} catch (SQLException e) {
				System.out.println("=========== query loi lay ghichu: "+query);
				e.printStackTrace();
			}
			
		}
		
		/*Ghi Chú*/
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 51,"A","Ghi chú: "+ghichu,
				true,false,35,TextAlignmentType.LEFT);
		FormatExcel.setborder_shadow(cells, i, 0, sodong, 51);
		sodong++;
		System.out.println("so dong ket thuc :"+sodong);
		return sodong+1;
	}

	private static void mergeTheoMe(Worksheet worksheet,int sodong, int cotdautien, String[] arrMe, int l) {
		// TODO Auto-generated method stub
		String txt="...";
		for (int j2 = 0; j2 < arrMe.length; j2++) {
			if(l==0){
				FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, cotdautien, cotdautien + Integer.parseInt(arrMe[j2]) - 1,
						cotdautien,txt,true,false,27,TextAlignmentType.CENTER);
			}else{
				FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, cotdautien, cotdautien + Integer.parseInt(arrMe[j2]) - 1,
						cotdautien,txt,true,false,27,TextAlignmentType.CENTER);
			}
		

			cotdautien += Integer.parseInt(arrMe[j2]);
		}
		
	}

	private static int readExcel(String string, int dong,Worksheet worksheet) {
		// TODO Auto-generated method stub
		File inputWorkbook = new File(string);
		Workbook w;
		try
		{
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			int socot = sheet.getColumns();
			
			System.out.println("So dong " + sodong + "socot " + socot );
			for (int i = 0; i < sodong; i++) {
				System.out.println("=========================="+i);
				for (int j = 0; j < socot; j++) {
					System.out.println("=========================="+j);
					String data=sheet.getCell(j ,i).getContents();
					System.out.println("======================"+data);
					if(j==0){
							FormatExcel.mergeCells_Style12(worksheet, dong, dong, 0, 11,"A",data,true,true,35,TextAlignmentType.CENTER);
						}else{
						String[] arr = getMangPhanBoMe(socot-1, 51-11);
						int cotdautien = 11+1;
						int g=dong;/*số dòng hiện tại đang đứng khi bắt đầu merge mẻ*/
					/*	Duyệt mảng arrMe*/
						for (int k = 0; k < arr.length; k++) {
							FormatExcel.mergeCells_Style12(worksheet, dong, dong, cotdautien,cotdautien + Integer.parseInt(arr[k]) - 1,cotdautien,
									sheet.getCell(j++ ,i).getContents(),true,true,41,TextAlignmentType.CENTER);
							cotdautien += Integer.parseInt(arr[k]);
						}
					}
				}dong++;
			}}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Loi doc file Excel" + e.getMessage());
		}
		return dong++;
	}

	private static String[] getMangPhanBoMe(int some, int tongso_cot) {
		// TODO Auto-generated method stub

		String[] mang = new String[some];

		System.out.println("tongso_cot: " + tongso_cot);
		int so_o_merce = tongso_cot / some;
		System.out.println("so_o_merce: " + so_o_merce);
		int so_o_du = tongso_cot - so_o_merce * some;
		System.out.println("so_o_du :" + so_o_du);

		for (int i = 0; i < mang.length; i++) {
			if (so_o_du > 0) {
				mang[i] = (so_o_merce + 1) + "";
				so_o_du--;
			} else {
				mang[i] = (so_o_merce) + "";
			}
		}
		return mang;

	}
	private static String getGiaTriThongSo(String loaithongso,String giatrithongso,String dvdl,int rong){
		String txt="";
	
			if(loaithongso.equals("0")){
				txt=giatrithongso+" "+dvdl.toLowerCase();
			}
			else{
				if(giatrithongso.equals("1")){
					txt="☑";
				}
				else{
					txt="☐";
				}
			}
		return txt;
	}
	
	
}
