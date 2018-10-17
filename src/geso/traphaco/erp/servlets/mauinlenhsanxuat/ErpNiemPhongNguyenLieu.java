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


public class ErpNiemPhongNguyenLieu {

	public ErpNiemPhongNguyenLieu() {

	}

	public static int Create(int i, String lsxid, String cdidcurrent,
			String gdid, String tengd, dbutils db, Worksheet worksheet,
			Cells cells, String pathPDF) {
		int sodong=i;
		System.out.println("=========================gdid:" + gdid);
		System.out.println("=========================cdidcurrent:" + cdidcurrent);
		System.out.println("=========================lsxid:" + lsxid);
		/* Tên Giai Đoạn */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 51,"A",tengd,true,true,27,TextAlignmentType.LEFT);
		sodong++;
		
		/* hướng dẫn*/
		String txt="Khi niêm phong nguyên liệu sau cân: thực hiện kiểm tra nhãn nguyên liệu, nếu đúng tên nguyên liệu, lô, mẻ đánh dấu ☑ vào từng ô nguyên liệu";
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 0, 51,"A",txt,true,false,27,TextAlignmentType.CENTER);
		sodong=sodong+2;
		
		/*Đổ chi tiết*/
		String maHoSo="";
		String queryHoSo="select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"+lsxid+"' "
				+ "\n and CONGDOANSANXUAT_FK='"+cdidcurrent+"'";
		ResultSet rsHoSo=db.get(queryHoSo);
		if (rsHoSo!=null) {
			try {
				while (rsHoSo.next()) {
					maHoSo=rsHoSo.getString("PK_SEQ");
				}rsHoSo.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*Lấy số mẻ dã nhập trong giai đoạn*/
		int tongMe=0;
		String queryMe=" select max(me) as me from ERP_HOSOCDSX_CHITIET "
				+ "\n where HOSOCDSX_FK='"+maHoSo+"'  and GIAIDOANSANXUAT_FK='"+gdid+"'";
		ResultSet rsMe=db.get(queryMe);
		if (rsMe!=null) {
			try {
				while (rsMe.next()) {
					tongMe=rsMe.getInt("me");
				}rsMe.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int j = 0; j < tongMe; j++) {
			FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 1,"A","Mẻ",
					true,true,27,TextAlignmentType.CENTER);
			FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 2, 6,"C","Thùng",
					true,true,27,TextAlignmentType.CENTER);
			FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 7, 26,"H","Tên nguyên liệu",
					true,true,27,TextAlignmentType.CENTER);
			FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 27, 31,"AB","Thùng",
					true,true,27,TextAlignmentType.CENTER);
			FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 32, 51,"AG","Tên nguyên liệu",
					true,true,27,TextAlignmentType.CENTER);
			/*Lấy nguyên liệu của từng mẻ*/
			String queryNL="select distinct b.SANPHAM_FK,sp.ten as TENNL "
					+ "\n from erp_hosocdsx_chitiet_thongso a inner join ERP_HOSOCDSX_CHITIET b on a.HOSOCDSX_CHITIET_FK=b.PK_SEQ "
					+ "\n inner join ERP_SANPHAM sp on sp.pk_seq = b.sanpham_fk "
					+ "\n where HOSOCDSX_FK='"+maHoSo+"'"
					+ "\n and GIAIDOANSANXUAT_FK='"+gdid+"' and b.me="+(j+1)+" and b.TRANGTHAI=1 "
					+ "\n order by b.SANPHAM_FK,sp.ten";
			ResultSet rsNL=db.get(queryNL);
			List<ArrayList<String>> arrNL=new ArrayList<ArrayList<String>>();
			if (rsNL!=null) {
				try {
					while (rsNL.next()) {
						ArrayList<String> arr=new ArrayList<String>();
						arr.add(rsNL.getString("SANPHAM_FK"));
						arr.add(rsNL.getString("TENNL"));
						arrNL.add(arr);
					}rsNL.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (arrNL.size()%2 !=0) {
				ArrayList<String> arr=new ArrayList<String>();
				arr.add(" ");
				arr.add(" ");
				arrNL.add(arr);
			}
			
			sodong++;
			int j2;
			int sodongmer=sodong;
			for (j2=0; j2 < arrNL.size(); j2++) {
				String[] arr1=getGiaTriNguyenLieu(j+1,arrNL.get(j2).get(0),maHoSo,gdid,db);
				FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 2, 6,"C",arr1[0],
						true,false,27,TextAlignmentType.CENTER);
				FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 7, 22,"H",arrNL.get(j2).get(1),
						true,false,27,TextAlignmentType.LEFT);
				FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 23, 26,"X",arr1[1],
						true,false,27,TextAlignmentType.CENTER);
				j2++;
				
				if (arrNL.get(j2).get(0).trim().length() > 0) {
					arr1=getGiaTriNguyenLieu(j+1,arrNL.get(j2).get(0),maHoSo,gdid,db);
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 27, 31,"AB",arr1[0],
							true,false,27,TextAlignmentType.CENTER);
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 32, 47,"AG",arrNL.get(j2).get(1),
							true,false,27,TextAlignmentType.LEFT);
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 48, 51,"AW",arr1[1],
							true,false,27,TextAlignmentType.CENTER);
				}
				else{
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 27, 51,"AB"," ",
							true,false,27,TextAlignmentType.CENTER);
				}
				
				sodong++;
			}
			FormatExcel.mergeCells_Style12(worksheet, sodongmer, sodong-1, 0, 1,"A",j+1+"",true,false,27,TextAlignmentType.CENTER);
			
			
		}
		
		
		/* Tổng số thùng niêm phong */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 11,"A","Tổng số thùng niêm phong",true,true,40,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 12, 51,"M","............................thùng",true,false,40,TextAlignmentType.CENTER);
		sodong++;

		/* Thời gian thực hiện */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 11,"A","Thời gian thực hiện",true,false,true,40,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 12, 51,"M","... ... ... ... ... ... ..., ngày ...............................",
				true,false,40,TextAlignmentType.CENTER);
		sodong++;

		/* Người thực hiện */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 11,"A","Người thực hiện(tên và chữ ký)",true,false,true,40,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 12, 31,"M"," ",
				true,false,40,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 32, 51,"AG"," ",
				true,false,40,TextAlignmentType.CENTER);
		sodong++;

		/* Kiểm soát ..... */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 51,"A","Kiểm soát quá trình cân chia lẻ và niêm phong nguyên liệu (toàn bộ quá trình IV)",
				true,true,35,TextAlignmentType.LEFT);
		sodong++;
		
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 0,3,"A"," ",true,false,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 4,10,"E","Thời điểm/ công đoạn KT",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 11,17,"L","Nội dung KT(*)",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 18,22,"S","Kết quả KT",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 23,27,"X","Người KT",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 28,34,"AC","Thời điểm/ công đoạn KT",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 35,41,"AJ","Nội dung KT(*)",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 42,46,"AQ","Kết quả KT",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+1, 47,51,"AV","Người KT",true,false,true,27,TextAlignmentType.CENTER);
		sodong=sodong+2;
		
		/*Nội dung kiểm tra*/
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 1,3,"B","PTCCL",true,false,true,35,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 4,10,"E"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 11,17,"L"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 18,22,"S"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 23,27,"X"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 28,34,"AC"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 35,41,"AJ"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 42,46,"AQ"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 47,51,"AV"," ",true,false,true,27,TextAlignmentType.CENTER);
		sodong++;
		
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 1,3,"B","DBCL",true,false,true,35,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 4,10,"E"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 11,17,"L"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 18,22,"S"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 23,27,"X"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 28,34,"AC"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 35,41,"AJ"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 42,46,"AQ"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 47,51,"AV"," ",true,false,true,27,TextAlignmentType.CENTER);
		sodong++;
		
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 1,3,"B","PTCCL",true,false,true,35,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 4,10,"E"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 11,17,"L"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 18,22,"S"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 23,27,"X"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 28,34,"AC"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 35,41,"AJ"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 42,46,"AQ"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 47,51,"AV"," ",true,false,true,27,TextAlignmentType.CENTER);
		sodong++;
		
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 1,3,"B","DBCL",true,false,true,35,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 4,10,"E"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 11,17,"L"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 18,22,"S"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 23,27,"X"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 28,34,"AC"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 35,41,"AJ"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 42,46,"AQ"," ",true,false,true,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 47,51,"AV"," ",true,false,true,27,TextAlignmentType.CENTER);
		
		
		FormatExcel.mergeCells_Style12(worksheet, sodong-3, sodong, 0,0,"A","Kiểm soát quá trình",true,true,35,
				TextAlignmentType.CENTER,TextAlignmentType.TOP);
		sodong++;
		
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+4, 0,3,"A","Nội dung kiểm tra(*)",true,true,35,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+4, 4,26,"E","1. Tuân thủ QTSX \n 2. Cập nhật HSL \n 3. Tuân thủ quy định trang phục, SOP và QT khác \n"
				+ " 4. Hình thức NL, BTP, TP (ghi rõ đối tượng kiểm tra vào nội dung KT, ví dụ 4(BTP, TP))",true,false,true,35,TextAlignmentType.LEFT);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+4, 27,51,"AB","\n 5. Tuân thủ quy định nhãn biển \n 6. Tình trạng vệ sinh \n 7. Nội dung khác: Nếu có ghi rõ vào nội dung kiểm tra \n",true,false,true,
				35,TextAlignmentType.LEFT);
		sodong=sodong+5;
		
		/*Ghi chú*/
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
		sodong=sodong+2;
		
		return sodong;
	}

	

	private static String[] getGiaTriNguyenLieu(int me, String masp,String mahoso,String gdid, dbutils db) {
		// TODO Auto-generated method stub
		String[] arr =new String[2];
		String txt="";
		String chk="☐";
		String queryCT="select a.loaithongso,a.giatrithongso from erp_hosocdsx_chitiet_thongso a "
				+ "\n inner join ERP_HOSOCDSX_CHITIET b on a.HOSOCDSX_CHITIET_FK=b.PK_SEQ "
				+ "\n inner join ERP_SANPHAM sp on sp.pk_seq = b.sanpham_fk "
				+ "\n where HOSOCDSX_FK='"+mahoso+"'  and GIAIDOANSANXUAT_FK='"+gdid+"' "
				+ "\n and b.me="+me+" and b.TRANGTHAI=1 and b.sanpham_fk='"+masp+"' order by b.stt";
		System.out.println("--------------------------------"+queryCT);
		ResultSet rsCT=db.get(queryCT);
		if (rsCT!=null) {
			try {
				while(rsCT.next()){
						if(rsCT.getString("loaithongso").equals("0")){
							
							if (rsCT.getString("giatrithongso").trim().length() > 0) {
								txt=rsCT.getString("giatrithongso");
							}
							arr[0]=txt;
							
						}
						else{
							
							if (rsCT.getString("giatrithongso").equals("1")) {
								chk="☑";
							}
							arr[1]=chk;
						}
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return arr;
		
		
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
	
	
	
}
