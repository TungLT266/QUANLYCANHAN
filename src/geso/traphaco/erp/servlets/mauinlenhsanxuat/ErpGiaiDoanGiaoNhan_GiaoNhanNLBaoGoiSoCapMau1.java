package geso.traphaco.erp.servlets.mauinlenhsanxuat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Column;
import com.aspose.cells.Font;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;
import com.extentech.formats.XLS.Array;

public class ErpGiaiDoanGiaoNhan_GiaoNhanNLBaoGoiSoCapMau1 {

	public ErpGiaiDoanGiaoNhan_GiaoNhanNLBaoGoiSoCapMau1() {

	}

	public static int Create(int i, String lsxid, String cdidcurrent,
			String gdid, String tengd, dbutils db, Worksheet worksheet,
			Cells cells, String pathPDF) {
		int sodongbd = 0;
		int some = 0;
		int cotdbct = 0;
		int sdbd=i;
		
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
		System.out.println(" bat dau in");

		/* Tên Công Đoạn */
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 51,"A",tenCD.toUpperCase(),true,true,27,TextAlignmentType.CENTER);
		sdbd++;
		
		/*Dòng Hướng đãn nguyên liệu*/	
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 35,"A","Hướng dẫn nhận nguyên liệu:",true,false,27,TextAlignmentType.CENTER);
		
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 36, 51,"AK","SOP.SX.03",true,false,27,TextAlignmentType.CENTER);
		sdbd++;

		/* Tên Giai Đoạn */
		FormatExcel.mergeCells_Style12(worksheet, sdbd , sdbd , 0, 51,"A",tengd,true,true,27,TextAlignmentType.CENTER);
		sdbd++;

		/* TT */
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd + 1, 0, 1,"A","TT",true,true,50,TextAlignmentType.CENTER);

		/* Tên nguyên liệu */
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd + 1, 2, 9,"C","Tên nguyên liệu",true,true,50,TextAlignmentType.CENTER);
		
		/* Yêu cầu đóng gói, ghi nhãn */
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd + 1, 10, 13,"K","Yêu cầu đóng gói, ghi nhãn",true,true,50,TextAlignmentType.CENTER,TextAlignmentType.TOP);
		
		/* Số lượng theo lệnh(kg) */
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd + 1, 14, 17,"O","Số lượng theo lệnh(kg)",true,true,50,TextAlignmentType.CENTER,TextAlignmentType.TOP);
		
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd + 1, 18, 19,"S","Lần",true,true,50,TextAlignmentType.CENTER,TextAlignmentType.TOP);
		
		
		/*Lấy thông số kỹ thuật*/
		ArrayList<String> arrTS = new ArrayList<String>();
		ArrayList<String> arrTam = new ArrayList<String>();
		String yeucau="";
		String queryTS = " select kbsx_cdsx_ts.THONGSOKYTHUAT,kbsx_cdsx_ts.ischeck,kbsx_cdsx_ts.YEUCAU"
				+ "\n from Erp_kichbansx_cdsx_thongso kbsx_cdsx_ts left join ERP_PHONGBANSX pbsx on pbsx.PK_SEQ = kbsx_cdsx_ts.KHUVUCSX_FK "
				+ "\n left join ERP_GIAIDOANSX gd on gd.PK_SEQ = kbsx_cdsx_ts.GIAIDOANSX_FK "
				+ "\n where giaidoansx_fk='"+gdid+"' and chon=1 order by kbsx_cdsx_ts.STT";
		System.out.println("===============Query Thong So Ky Thuat================" + queryTS);
		ResultSet rsTS = db.get(queryTS);
		if (rsTS != null) {
			try {
				while (rsTS.next()) {
					arrTS.add(rsTS.getString("THONGSOKYTHUAT"));
					if(rsTS.getString("YEUCAU").trim().length()>0){
						yeucau=rsTS.getString("YEUCAU");
					}
					arrTam.add(rsTS.getString("ischeck"));
					arrTam.add(" ");
				}
				rsTS.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int tongso_cot = 32;
		/* Merge theo thông số kỹ thuật*/
		String[] arrMe = getMangPhanBoMe(arrTS.size(), tongso_cot);
		int cotdautien = 20;
		for (int k = 0; k < arrMe.length; k++) {
			FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd+1, cotdautien,cotdautien + Integer.parseInt(arrMe[k]) - 1,cotdautien,
					arrTS.get(k),true,true,50,TextAlignmentType.CENTER,TextAlignmentType.TOP);
			cotdautien += Integer.parseInt(arrMe[k]);
		}
		sdbd=sdbd+2;
		/*Đỗ dữ liệu*/
		int ctbd=sdbd;
		
		String queryCT="select distinct a.SANPHAM_FK,max(a.solan) as solan,sp.TEN as TENNL, sp.MA as MANL ,donvi.donvi as tendv ,ROUND(a.SLDINHMUC, 1) as SLDINHMUC"
				+ "\n from ERP_HOSOCDSX_CHITIET a right join ERP_HOSOCDSX_CHITIET_THONGSO b on a.pk_seq=b.HOSOCDSX_CHITIET_FK"
				+ "\n inner join ERP_HOSOCDSX HOSO on a.HOSOCDSX_FK = HOSO.pk_Seq "
				+ "\n inner join ERP_SANPHAM sp on sp.pk_seq = a.sanpham_fk "
				+ "\n left join donvidoluong donvi on donvi.pk_seq = a.dvdl_fk "
				+ "\n inner join Erp_CongDoanSanXuat_Giay cdsx on cdsx.pk_seq = HOSO.CONGDOANSANXUAT_FK "
				+ "\n where HOSOCDSX_FK=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"+lsxid+"' and CONGDOANSANXUAT_FK='"+cdidcurrent+"') "
				+ "\n and GIAIDOANSANXUAT_FK='"+gdid+"' and b.GIATRITHONGSO <> '' and  a.trangthai=1 "
				+ "\n group by a.SANPHAM_FK,sp.MA,sp.TEN,donvi.donvi,a.SLDINHMUC";
		System.out.println("==========Query Chi Tiet=============="+queryCT);
		ResultSet rsCT=db.get(queryCT);
		int n=0;
		if (rsCT!=null) {
			try {
				int tt=0;
				
				while(rsCT.next()){
					int solan=rsCT.getInt("solan");
					/*TT Nguyên liệu Bao gói*/
					FormatExcel.mergeCells_Style12(worksheet, ctbd, ctbd+(solan-1), 0, 1,"A",tt+1+"",true,true,45,TextAlignmentType.CENTER);

					/* Tên nguyên liệu */
					FormatExcel.mergeCells_Style12(worksheet, ctbd, ctbd + (solan-1), 2, 9,"C",rsCT.getString("TENNL"),true,false,45
							,TextAlignmentType.CENTER,TextAlignmentType.TOP);		
					
					 /*Số lượng theo lệnh(kg) */
					FormatExcel.mergeCells_Style12(worksheet, ctbd, ctbd + (solan-1), 14, 17,"O",rsCT.getString("SLDINHMUC"),true,false,
							45,TextAlignmentType.CENTER);
					
					/*Chia theo thông số nằm ngang*/
					int ctd=ctbd;/*dòng bắt đầu chi tiết*/
					int y=20;/*cột bắt đầu chi tiết*/
					for (int j2 = 0; j2 < solan; j2++) {
						n++;
						FormatExcel.mergeCells_Style12(worksheet, ctd, ctd, 18, 19,"S",(j2+1)+"",true,true,45,TextAlignmentType.CENTER);
						/*lấy chi tiết theo số lần*/
						List<ArrayList<String>> arrCTTS = new ArrayList<ArrayList<String>>();
						String queryCTTS="select b.LOAITHONGSO,b.GIATRITHONGSO "
								+ "\n from ERP_HOSOCDSX_CHITIET a right join ERP_HOSOCDSX_CHITIET_THONGSO b on a.pk_seq=b.HOSOCDSX_CHITIET_FK "
								+ "\n where HOSOCDSX_FK=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"+lsxid+"' and CONGDOANSANXUAT_FK='"+cdidcurrent+"')"
								+ "\n  and GIAIDOANSANXUAT_FK='"+gdid+"' and me=1 and sanpham_fk='"+rsCT.getString("SANPHAM_FK")+"' "
								+ "\n and solan="+(j2+1)+"  order by b.stt";
						System.out.println("-------------------------------"+queryCTTS);
						ResultSet rsCTTS=db.get(queryCTTS);
						if(rsCTTS!=null){
							if (rsCTTS.next() == false) {
								arrCTTS.add(arrTam);
						      } else {
						        do {
						        	ArrayList<String> arrCTMe = new ArrayList<String>();
									arrCTMe.add(rsCTTS.getString("loaithongso"));
									arrCTMe.add(rsCTTS.getString("giatrithongso"));
									arrCTTS.add(arrCTMe);
						        } while (rsCTTS.next());
						      }
							rsCTTS.close();
						}
						ArrayList<String> arrTSCT =getGiaTriThongSo(arrCTTS);
						
						for (int k = 0; k < arrMe.length; k++) {
								FormatExcel.mergeCells_Style12(worksheet, ctd, ctd, y,y + Integer.parseInt(arrMe[k]) - 1,y,
									arrTSCT.get(k),true,false,45,TextAlignmentType.CENTER);
							y+=Integer.parseInt(arrMe[k]);
						}
						ctd++;
						y=20;
					}
					
					ctbd+=solan;
					sdbd++;
					tt++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/* Yêu cầu đóng gói, ghi nhãn */
		FormatExcel.mergeCells_Style12(worksheet, ctbd-n, ctbd-1, 10, 13,"K",yeucau,true,false,45,TextAlignmentType.CENTER,TextAlignmentType.TOP);
		
		sdbd=ctbd;
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
		String queryGC =" select Ghichu from ERP_HOSOCDSX_GHICHU where hosocdsx_fk=(select PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"
				+ lsxid + "' and CONGDOANSANXUAT_FK='"
				+ cdidcurrent + "') and GIAIDOAN_FK= "+gdid + "  " ;
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
		sdbd=sdbd+2;
		System.out.println("so dong ket thuc :"+sdbd);
		return sdbd;
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
	private static ArrayList<String> getGiaTriThongSo(List<ArrayList<String>> arrCTTS){
		ArrayList<String> arrTSCT = new ArrayList<String>();
		String txt="";
		String chk1="☐";
			for (int j = 0; j < arrCTTS.size(); j++) {
				for (int j2 = 0; j2 < arrCTTS.get(j).size()-1; j2++) {
					if (arrCTTS.get(j).get(j2).equals("0")) { 
						if(arrCTTS.get(j).get(1).trim().length() > 0){
							txt=arrCTTS.get(j).get(1);
						}
						arrTSCT.add(txt);
					} 
					 if (arrCTTS.get(j).get(j2).equals("1") || arrCTTS.get(j).get(j2).equals(" ")) {
						 if(arrCTTS.get(j).get(1).trim().length() > 0){
							 if (arrCTTS.get(j).get(1).equals("1")) {
									chk1 = "☑";
							 }
						 }
						arrTSCT.add(chk1);
					}
				}
			}
		return arrTSCT;
	}

}
