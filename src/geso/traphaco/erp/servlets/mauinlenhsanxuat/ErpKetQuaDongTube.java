package geso.traphaco.erp.servlets.mauinlenhsanxuat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import java.util.List;

import geso.traphaco.center.db.sql.dbutils;

import com.aspose.cells.Cells;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;



public class ErpKetQuaDongTube {

	public ErpKetQuaDongTube() {

	}

	public static int Create(int i, String lsxid, String cdidcurrent,
			String gdid, String tengd, dbutils db, Worksheet worksheet,
			Cells cells, String pathPDF) {
		int sodong=i;
		int some=4;
		/*System.out.println("=========================gdid:" + gdid);
		System.out.println("=========================cdidcurrent:" + cdidcurrent);
		System.out.println("=========================lsxid:" + lsxid);*/
		
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
		
		/* Tên Giai Đoạn */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 51,"A",tengd,true,true,27,TextAlignmentType.LEFT);
		sodong++;
		
		/* Kết quả thực hiện */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 51,"A","Kết quả thực hiện quá trình",true,true,27,TextAlignmentType.CENTER);
		sodong++;
		
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 2,"A","Ngày",true,false,50,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 3, 9,"D","Mẻ",true,false,50,TextAlignmentType.CENTER);
		ArrayList<String> arrTS = new ArrayList<String>();
		String queryMe = "  select kbsx_cdsx_ts.THONGSOKYTHUAT,kbsx_cdsx_ts.ischeck "
				+ "\n from Erp_kichbansx_cdsx_thongso kbsx_cdsx_ts left join ERP_GIAIDOANSX gd on gd.PK_SEQ = kbsx_cdsx_ts.GIAIDOANSX_FK "
				+ "\n where giaidoansx_fk=107160 and chon=1 order by kbsx_cdsx_ts.STT";
		//System.out.println("===============Query Thong So Ky Thuat================" + queryMe);
		ResultSet rsTS = db.get(queryMe);
		if (rsTS != null) {
			try {
				while (rsTS.next()) {
					arrTS.add(rsTS.getString("THONGSOKYTHUAT"));
				}
				rsTS.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<ArrayList<String>> arrSum=new ArrayList<ArrayList<String>>();
		int tongso_cot = 28;
		/* Mảng merge mẻ */
		String[] arrMe = getMangPhanBoMe(arrTS.size(), tongso_cot);
		
		/*cotdautien(cột bắt đầu merge mẻ) của tiêu đề mẻ*/
		int cotdautien = 10;
		int g=sodong;/*số dòng hiện tại đang đứng khi bắt đầu merge mẻ*/
		
		/*Duyệt mảng arrMe*/
		for (int k = 0; k < arrMe.length; k++) {
			FormatExcel.mergeCells_Style12(worksheet, g, g, cotdautien,cotdautien + Integer.parseInt(arrMe[k]) - 1,cotdautien,
					arrTS.get(k),true,false,50,TextAlignmentType.CENTER);
			
			cotdautien += Integer.parseInt(arrMe[k]);
		}
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 38, 43,"AM","Người",true,true,50,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 44, 51,"AS","Người kiểm tra",true,true,50,TextAlignmentType.CENTER);
		sodong++;
	
		
		String queryCT="select distinct THOIGIANGHI,ME,NGUOITHUCHIEN,NGUOIKIEMTRA "
				+ "\n from ERP_HOSOCDSX_CHITIET where HOSOCDSX_FK='"+maHoSo+"'"
				+ "\n and GIAIDOANSANXUAT_FK='"+gdid+"' and trangthai=1";
		ResultSet rsCT=db.get(queryCT);
		if (rsCT!=null) {
			try {
				while(rsCT.next()){
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 2,"A",rsCT.getString("THOIGIANGHI"),true,false,27,TextAlignmentType.CENTER);
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 3, 9,"D","Mẻ "+rsCT.getString("ME"),true,false,27,TextAlignmentType.CENTER);
				
					/*Đổ chi tiết thông số từng mẻ*/
					ArrayList<String> arr=getGiatriThongSo(maHoSo,gdid,rsCT.getString("ME"),arrMe,db);
					
					cotdautien = 10;
					g=sodong;
					for (int k = 0; k < arrMe.length; k++) {
						FormatExcel.mergeCells_Style12(worksheet, g, g, cotdautien,cotdautien + Integer.parseInt(arrMe[k]) - 1,cotdautien,
								arr.get(k),true,false,50,TextAlignmentType.CENTER);
						cotdautien += Integer.parseInt(arrMe[k]);
					}
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 38, 43,"AM",rsCT.getString("NGUOITHUCHIEN"),true,false,27,TextAlignmentType.CENTER);
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 44, 51,"AS",rsCT.getString("NGUOIKIEMTRA"),true,false,27,TextAlignmentType.CENTER);
					arrSum.add(arr);
					sodong++;
				}rsCT.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ArrayList<String> arr1=tinhTong(arrSum);
		/*số dòng hiện tại đang đứng khi bắt đầu merge mẻ*/
		cotdautien = 10;
		g=sodong;
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 9,"A","Tổng kết",true,false,27,TextAlignmentType.CENTER);
	
		/*Duyệt mảng arrMe*/
		for (int k = 0; k < arrMe.length; k++) {
			FormatExcel.mergeCells_Style12(worksheet, g, g, cotdautien,cotdautien + Integer.parseInt(arrMe[k]) - 1,cotdautien,
					arr1.get(k),true,false,50,TextAlignmentType.CENTER);
			cotdautien += Integer.parseInt(arrMe[k]);
		}
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 38, 43,"AM","",true,false,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 44, 51,"AS","",true,false,27,TextAlignmentType.CENTER);
		sodong++;
		
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+4, 0, 16,"A","Thời gian thực hiện toàn bộ quá trình đóng lọ",true,false,true,27,TextAlignmentType.RIGHT);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+4, 17, 27,"R","Ghi giờ, ngày",true,false,27,TextAlignmentType.CENTER);
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong+4, 28, 51,"AC","",true,false,27,TextAlignmentType.CENTER);
		sodong=sodong+4;
		sodong++;
		
		/*Ghi chú*/
		String ghichu="";
		String query =" select Ghichu from ERP_HOSOCDSX_GHICHU where hosocdsx_fk=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"
							+ lsxid + "' and CONGDOANSANXUAT_FK='"
							+ cdidcurrent + "') and GIAIDOAN_FK="+gdid + "" ;
		//System.out.println("query lay ghichu: "+query);
		ResultSet ghichuRs = db.get(query);
		if(ghichuRs!=null){
			try {
				while(ghichuRs.next()){
					ghichu=ghichuRs.getString("ghichu");
				}
				ghichuRs.close();
			} catch (SQLException e) {
				System.out.println("query loi lay ghichu: "+query);
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

	


	private static ArrayList<String> getGiatriThongSo(String maHoSo,
			String gdid, String me, String[] arrMe,dbutils db) {
		ArrayList<String> arr=new ArrayList<String>();
		String queryCTTS="select giatrithongso from erp_hosocdsx_chitiet_thongso "
				+ "\n where hosocdsx_chitiet_fk in (select PK_SEQ from ERP_HOSOCDSX_CHITIET "
				+ "\n where HOSOCDSX_FK='"+maHoSo+"'  and GIAIDOANSANXUAT_FK='"+gdid+"' and trangthai=1 and me="+me+") and loaithongso=0 "
				+ "\n order by stt";
		ResultSet rsCTTS=db.get(queryCTTS);
		if (rsCTTS!=null) {
			
			try {
				while (rsCTTS.next()) {
					arr.add(rsCTTS.getString("giatrithongso"));
				}rsCTTS.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (arrMe.length > arr.size()) {
			for (int j = 0; j <=arrMe.length-arr.size() ; j++) {
				arr.add(" ");
			}
		}
		return arr;
	}

	private static ArrayList<String> tinhTong(List<ArrayList<String>> arrSum) {
		// TODO Auto-generated method stub
		ArrayList<String> arr1=new ArrayList<String>();
		for (int j = 0; j < arrSum.size(); j++) {
			for (int u = 0; u < arrSum.get(j).size(); u++) {
				if (arrSum.get(j).get(u)!=null && arrSum.get(j).get(u).trim().length() > 0) {
					double sum=Double.parseDouble(arrSum.get(j).get(u));
					if ( j+1 < arrSum.size() ) {
						if (arrSum.get(j+1).get(u).trim().length()>0) {
							sum+=Double.parseDouble(arrSum.get(j+1).get(u));
							arr1.add(u, Math.round(sum)+"");
						}
					}

				}else{
					arr1.add(u," ");
				}
				
			}
		}
		return arr1;
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
