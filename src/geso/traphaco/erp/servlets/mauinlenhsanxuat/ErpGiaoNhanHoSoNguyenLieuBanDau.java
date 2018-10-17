package geso.traphaco.erp.servlets.mauinlenhsanxuat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;

public class ErpGiaoNhanHoSoNguyenLieuBanDau {
	
	public ErpGiaoNhanHoSoNguyenLieuBanDau(){
		
	}
	
	public static int Create (int i,String lsxid, String cdidcurrent,String gdid, String tengd,  dbutils db, Worksheet worksheet, Cells cells,  String pathPDF)
	{
		
		System.out.println("========================="+gdid);
		int sdbd=i;
		
		System.out.println(" bat dau in");
		String tenCD="";
		String tenGD="";
		String dong="";
	
		/*Tên Giai Đoạn*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0,51,"A",tengd,true,true,27,TextAlignmentType.CENTER);
		sdbd++;
		
		/*Loại hồ sơ*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd,0,0,"A","TT",true,true,27,TextAlignmentType.CENTER);
		
		/*Loại hồ sơ*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 1,19,"B","Loại hồ sơ",true,true,27,TextAlignmentType.CENTER);
		
		/*Yêu cầu*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd,20,40,"U","Yêu cầu",true,true,27,TextAlignmentType.CENTER);
		
		/*Kết quả kiểm tra*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd,41,51,"AP","Kết quả kiểm tra",true,true,27,TextAlignmentType.CENTER);
		sdbd++;
		
		String query="select a.tenthongsokythuat,ct_thongso.LOAITHONGSO,ct_thongso.giatrithongso,ct_thongso.stt,a.trangthai" + 
				"\n  from ERP_HOSOCDSX_CHITIET a inner join ERP_HOSOCDSX HOSO on a.HOSOCDSX_FK = HOSO.pk_Seq" + 
				"\n  left join ERP_HOSOCDSX_CHITIET_THONGSO ct_thongso on ct_thongso.HOSOCDSX_CHITIET_FK=a.PK_SEQ" + 
				"\n left join ERP_GIAIDOANSX_THIETBI gdtb  on gdtb.GIAIDOAN_FK=a.GIAIDOANSANXUAT_FK\r\n" + 
				"\n left join ERP_GIAIDOANSX_THIETBI_CHITIET gdtbct on gdtb.PK_SEQ=gdtbct.GIAIDOAN_TB_FK and gdtbct.PK_SEQ= a.THONGSO_FK\r\n" + 
				"\n where  \r\n" + 
				" HOSO.LENHSANXUAT_FK="+ lsxid +" \r\n" + 
				"AND HOSO.CONGDOANSANXUAT_FK= "+ cdidcurrent +"\r\n" + 
				"and a.GIAIDOANSANXUAT_FK="+ gdid +"\r\n" + 
				" and ct_thongso.LOAITHONGSO=1  and a.trangthai=1"
				+ "\n ORDER BY a.stt,A.ME, gdtbct.STT";
		System.out.println("lay loa hoa so gia doan 4.3: " + query);
		ResultSet lhsRs = db.get(query);
		int bdct=sdbd;
		if(lhsRs!=null){
			try {
				int stt=0;
				while(lhsRs.next()){
					//lay stt
					FormatExcel.mergeCells_Style12(worksheet, bdct, bdct, 0,0,"A",stt+1+"",true,false,27,TextAlignmentType.CENTER);
					
					//lay loai ho so
					FormatExcel.mergeCells_Style12(worksheet, bdct, bdct, 1,19,"B",lhsRs.getString("tenthongsokythuat"),true,false,
							27,TextAlignmentType.LEFT);
					
					/*Kết quả kiểm tra*/
					String chk="";
					if(lhsRs.getString("giatrithongso").equals("1")){
						chk="☑";
					}
					else{
						chk="☐";
					}
					FormatExcel.mergeCells_Style12(worksheet, bdct, bdct, 41,51,"AP",chk,true,false,
							27,TextAlignmentType.CENTER);
					stt++;
					bdct++;
					
				}
				lhsRs.close();
			} catch (SQLException e) {
				System.out.println("=========== query loi lay du lieu: "+query);
				e.printStackTrace();
			}
			
		}
		
		//yeu cau
		FormatExcel.mergeCells_Style12(worksheet, sdbd, bdct-1, 20,40,"U","Thông tin đủ, phù hợp với hồ sơ",true,false,35,TextAlignmentType.CENTER);
		sdbd=bdct++;
		
		
		/*Thời gian thực hiện*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd+1, 0, 4,"A","Thời gian thực hiện:",true,false,35,TextAlignmentType.CENTER);
		
		/*Người giao*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd+1, 5, 14,"F","Người giao",true,false,35,TextAlignmentType.CENTER);

		/*Người nhận*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd+1, 15, 24,"P","Người nhận",true,false,35,TextAlignmentType.CENTER);
		
		/*Người kiểm tra*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 25, 51,"Z","Người kiểm tra",true,false,35,TextAlignmentType.CENTER);
		sdbd++;
		
		/*ĐBCL*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 25, 38,"Z","ĐBCL",true,false,35,TextAlignmentType.CENTER);
		
		/*QĐ/ PQĐ PX*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 39, 51,"AN","QĐ/ PQĐ PX",true,false,35,TextAlignmentType.CENTER);

		sdbd++;
		
		/*...../...../......*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 4,"A","...../...../.....",true,false,35,TextAlignmentType.CENTER);
		
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 5, 14,"F"," ",true,false,35,TextAlignmentType.CENTER);
		
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 15, 24,"P"," ",true,false,35,TextAlignmentType.CENTER);
		
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 25, 38,"Z"," ",true,false,35,TextAlignmentType.CENTER);
		
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 39, 51,"AN"," ",true,false,35,TextAlignmentType.CENTER);
		sdbd++;
		
		String ghichu="";

		/*String queryGC =" select Ghichu from ERP_HOSOCDSX_GHICHU where hosocdsx_fk=(select PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"
				+ lsxid + "' and CONGDOANSANXUAT_FK='"
				+ cdidcurrent + "') and GIAIDOAN_FK= "+gdid + "  " ;
		System.out.println("query lay ghichu: "+queryGC);
		ResultSet ghichuRs = db.get(queryGC);*/

		/*query =" select Ghichu from ERP_HOSOCDSX_GHICHU where hosocdsx_fk="+cdidcurrent+" and GIAIDOAN_FK= "+gdid + "  " ;
		System.out.println("query lay ghichu: "+query);
		ResultSet ghichuRs = db.get(query);*/

		String queryGC =" select Ghichu from ERP_HOSOCDSX_GHICHU where hosocdsx_fk=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"
				+ lsxid + "' and CONGDOANSANXUAT_FK='"
				+ cdidcurrent + "') and GIAIDOAN_FK='"+gdid + "'" ;
		System.out.println("query lay ghichu: "+queryGC);
		ResultSet ghichuRs = db.get(queryGC);

		if(ghichuRs!=null){
			try {
				while(ghichuRs.next()){
					ghichu=ghichuRs.getString("ghichu");
				}
				ghichuRs.close();
			} catch (SQLException e) {
				System.out.println("=========== query loi lay ghichu: "+queryGC);
				e.printStackTrace();
			}
			
		}
		
		/*Ghi Chú*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 51,"A","Ghi chú:"+ghichu,true,false,35,TextAlignmentType.LEFT);

		FormatExcel.setborder_shadow(cells, i, 0, sdbd, 51);
		sdbd++;
		System.out.println("so dong ket thuc :"+sdbd);
		return sdbd;
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
