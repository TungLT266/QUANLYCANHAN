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

public class ErpGiaiDoanCanChiaMauA1 {

	public ErpGiaiDoanCanChiaMauA1() {

	}

	public static int Create(int i, String lsxid, String cdidcurrent,
			String gdid, String tengd, dbutils db, Worksheet worksheet,
			Cells cells, String pathPDF) {
		System.out.println("=========================gdid:" + gdid);
		
		/* int socot=51; */
		int some = 0;
		int cotdbct = 10;
		
		/*Lấy tên công đoạn*/
		String tenCD = "";
		String queryTenCD="select DIENGIAI from Erp_CongDoanSanXuat_Giay where pk_seq='"+cdidcurrent+"'";
		System.out.println("=========================Query Lấy Tên CĐ:" + queryTenCD);
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
		System.out.println("=========================Query Số Mẻ:" + querySoMe);
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
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 51,"A",tengd,true,true,27,TextAlignmentType.CENTER);
		sodong++;

		/* Dòng trắng */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 51,"A","Cân, chia nguyên liệu ban đầu theo QT54/KH",true,false,27,TextAlignmentType.CENTER);
		sodong++;

		/* Tên nguyên liệu */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong + 2, 0, 2,"A","Tên nguyên liệu",true,true,35,TextAlignmentType.CENTER);

		/* Đơn vị */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong + 2, 3, 4,"D","ĐV",true,true,35,TextAlignmentType.CENTER);

		/* Trắng trên KL yêu cầu */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong + 1, 5, 9,"F"," ",true,true,35,TextAlignmentType.CENTER);
		
		/* Lô */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 10, 41,"K","Lô",true,true,27,TextAlignmentType.CENTER);
		
		/* Loại thiết bị */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong + 2, 42,46,"AQ","Loại thiết bị",true,true,35,TextAlignmentType.CENTER);

		/* TB Khác */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong + 2, 47, 51,"AV","TB Khác",true,true,35,TextAlignmentType.CENTER);
		sodong++;
		
		/* Lấy thông số kỹ thuật theo từng mẻ */
		ArrayList<String> arrTS = new ArrayList<String>();
		String queryMe = "select b.THONGSOKYTHUAT"
				+ "\n from  ERP_GIAIDOANSX_THIETBI a inner join ERP_GIAIDOANSX_THIETBI_CHITIET b on a.PK_SEQ=b.GIAIDOAN_TB_FK "
				+ "\n where a.GIAIDOAN_FK='" + gdid + "' order by a.stt";
		System.out.println("===============Query Thong So Ky Thuat================" + queryMe);
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
			FormatExcel.mergeCells_Style12(worksheet, g, g, cotdautien,cotdautien + Integer.parseInt(arrMe[k]) - 1,cotdautien,
					"Mẻ " + (k + 1),true,true,41,TextAlignmentType.CENTER);
			String[] mangTS = getMangPhanBoMe(arrTS.size(),
					Integer.parseInt(arrMe[k]));
			int m = cotdautien;
			for (int j = 0; j < mangTS.length; j++) {
				FormatExcel.mergeCells_Style12(worksheet, g+1 , g+1 , m,m + Integer.parseInt(mangTS[j]) - 1,m,
						arrTS.get(j),true,true,80,TextAlignmentType.CENTER);
				m += Integer.parseInt(mangTS[j]);
			}
			cotdautien += Integer.parseInt(arrMe[k]);
		}
		
		sodong++;
		/* KL Yêu cầu */
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 5, 9,"F","KL Yêu Cầu",true,true,41,TextAlignmentType.CENTER);
		sodong++;
		/* Đổ dữ liệu chi tiết */
		int meHienCo=0;
		String queryMeHienCo= " select count(distinct a.me) as mehienco"
				+ "\n from ERP_HOSOCDSX_CHITIET a inner join ERP_HOSOCDSX HOSO on a.HOSOCDSX_FK = HOSO.pk_Seq "
				+ "\n inner join Erp_CongDoanSanXuat_Giay cdsx on cdsx.pk_seq = HOSO.CONGDOANSANXUAT_FK "
				+ "\n where HOSOCDSX_FK=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"
				+ lsxid
				+ "' and CONGDOANSANXUAT_FK='"
				+ cdidcurrent
				+ "') "
				+ "\n and GIAIDOANSANXUAT_FK='"
				+ gdid+"'";
		System.out.println("===========Query me hien co==========:"+queryMeHienCo);
		ResultSet rsMeHienCo=db.get(queryMeHienCo);
		if (rsMeHienCo!=null) {
			try {
				while(rsMeHienCo.next()){
					meHienCo=rsMeHienCo.getInt("mehienco");
				}rsMeHienCo.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String query = " select distinct  a.SANPHAM_FK,sp.TEN as TENNL, sp.MA as MANL ,donvi.donvi as tendv,a.SLDINHMUCTU SLDINHMUCTU, a.SLDINHMUCDEN SLDINHMUCDEN"
				+ "\n  ,a.THIETBI_FK, sp.THIETBICAN,sp.THIETBICANKHAC"
				+ "\n from ERP_HOSOCDSX_CHITIET a inner join ERP_HOSOCDSX HOSO on a.HOSOCDSX_FK = HOSO.pk_Seq "
				+ "\n inner join ERP_SANPHAM sp on sp.pk_seq = a.sanpham_fk "
				+ "\n left join donvidoluong donvi on donvi.pk_seq = a.dvdl_fk "
				+ "\n inner join Erp_CongDoanSanXuat_Giay cdsx on cdsx.pk_seq = HOSO.CONGDOANSANXUAT_FK "
				+ "\n where HOSOCDSX_FK=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"
				+ lsxid
				+ "' and CONGDOANSANXUAT_FK='"
				+ cdidcurrent
				+ "') "
				+ "\n and GIAIDOANSANXUAT_FK='"
				+ gdid+"'";
		System.out.println("===========Query ==========:"+query);
		ResultSet rsQuery = db.get(query);
		int sobdct = sodong;

		//System.out.println("me hien co:"+meHienCo);
		if (rsQuery != null) {
			try {
				while (rsQuery.next()) {
					/* Tên nguyên liệu */
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 2,"A",rsQuery.getString("TENNL"),true,false,65,TextAlignmentType.LEFT);

					/* Đơn vị */
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 3, 4,"D",rsQuery.getString("tendv").toLowerCase(),true,false,65,TextAlignmentType.CENTER);

					/* Trắng trên KL yêu cầu */
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 5, 9,"F",rsQuery.getString("SLDINHMUCTU") + "→"+
							rsQuery.getString("SLDINHMUCDEN"),true,false,65,TextAlignmentType.CENTER);
					
					int u=cotdbct;
					for (int k = 0; k < arrMe.length; k++) {
						List<ArrayList<String>> arrCTTS = new ArrayList<ArrayList<String>>();
						String[] mangTS = getMangPhanBoMe(arrTS.size(),
								Integer.parseInt(arrMe[k]));
						String queryCTTS = "select a.loaithongso,a.giatrithongso "
								+ "\n from erp_hosocdsx_chitiet_thongso a inner join ERP_HOSOCDSX_CHITIET b on a.HOSOCDSX_CHITIET_FK=b.PK_SEQ "
								+ "\n where HOSOCDSX_FK=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"
								+ lsxid + "' and CONGDOANSANXUAT_FK='"
								+ cdidcurrent + "')"
								+ "\n  and GIAIDOANSANXUAT_FK='" + gdid
								+ "' and me='" +(k+1)
								+ "' and sanpham_fk='"
								+ rsQuery.getString("SANPHAM_FK") + "'";
						System.out.println("==========queryCTTS==============="+queryCTTS);
						ResultSet rsCTTS=db.get(queryCTTS);
						if(rsCTTS!=null){
							if (rsCTTS.next() == false) {
								ArrayList<String> arrCTMe = new ArrayList<String>();
								arrCTMe.add("0");
								arrCTMe.add(" ");
								arrCTMe.add("1");
								arrCTMe.add(" ");
								arrCTTS.add(arrCTMe);
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
						
						ArrayList<String> arrTSCT = new ArrayList<String>();
						String txt="";
						String chk1="☐";
							for (int j = 0; j < arrCTTS.size(); j++) {
								for (int j2 = j; j2 < arrCTTS.get(j).size(); j2++) {
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
							
						for (int j = 0; j < mangTS.length; j++) {

								FormatExcel.mergeCells_Style12(worksheet, sodong, sodong,u,u+Integer.parseInt(mangTS[j])-1,u,arrTSCT.get(j),true,false,65,TextAlignmentType.CENTER);
								u += Integer.parseInt(mangTS[j]);

						}
					}
					
					String queryTBC=" select TEN from (select ('TSCD' + cast(pk_seq as varchar)) as pk_seq, ma + case when diengiai is null then '' else ' - ' + diengiai end as ten "
							+ "\n from ERP_TAISANCODINH union all select ('CPTT' + cast(pk_seq as varchar)) as pk_seq,ma + case when diengiai is null then '' else ' - ' + diengiai end as ten from ERP_CONGCUDUNGCU) DATA "
							+ "\n where PK_SEQ='"+rsQuery.getString("THIETBICAN")+"'";
					ResultSet rsTBC=db.get(queryTBC);
					if(rsTBC!=null){
						if (rsTBC.next() == false) {
							FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 42,46,"AQ","☐ ",true,
				        			false,65,TextAlignmentType.CENTER);
					      } else {
					        do {
					        	FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 42,46,"AQ","☐"+rsTBC.getString("TEN"),true,
					        			false,65,TextAlignmentType.CENTER);
					        } while (rsTBC.next());
					      }
						rsTBC.close();
						
					}
					/* TB Khác */
					queryTBC=" select TEN from (select ('TSCD' + cast(pk_seq as varchar)) as pk_seq, ma + case when diengiai is null then '' else ' - ' + diengiai end as ten "
							+ "\n from ERP_TAISANCODINH union all select ('CPTT' + cast(pk_seq as varchar)) as pk_seq,ma + case when diengiai is null then '' else ' - ' + diengiai end as ten from ERP_CONGCUDUNGCU) DATA "
							+ "\n where PK_SEQ='"+rsQuery.getString("THIETBICANKHAC")+"'";
					rsTBC=db.get(queryTBC);
					if(rsTBC!=null){
						if (rsTBC.next() == false) {
							FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 47, 51,"AV","☐ ",true,
									false,65,TextAlignmentType.CENTER);
					      } else {
					        do {
					        	FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 47, 51,"AV","☐"+rsQuery.getString("THIETBICANKHAC"),true,
										false,65,TextAlignmentType.CENTER);
					        } while (rsTBC.next());
					      }
						rsTBC.close();
						
					}
					sodong++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sobdct=sodong;



	
		System.out.println("me hien co:"+meHienCo);
		if (rsQuery != null) {
			try {
				while (rsQuery.next()) {
					/* Tên nguyên liệu */
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 0, 2,"A",rsQuery.getString("TENNL"),true,false,65,TextAlignmentType.LEFT);

					/* Đơn vị */
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 3, 4,"D",rsQuery.getString("tendv").toLowerCase(),true,false,65,TextAlignmentType.CENTER);

					/* Trắng trên KL yêu cầu */
					FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 5, 9,"F",rsQuery.getString("SLDINHMUCTU") + "→"+
							rsQuery.getString("SLDINHMUCDEN"),true,false,65,TextAlignmentType.CENTER);
					
					int u=cotdbct;
					for (int k = 0; k < arrMe.length; k++) {
						List<ArrayList<String>> arrCTTS = new ArrayList<ArrayList<String>>();
						String[] mangTS = getMangPhanBoMe(arrTS.size(),
								Integer.parseInt(arrMe[k]));
						String queryCTTS = "select a.loaithongso,a.giatrithongso "
								+ "\n from erp_hosocdsx_chitiet_thongso a inner join ERP_HOSOCDSX_CHITIET b on a.HOSOCDSX_CHITIET_FK=b.PK_SEQ "
								+ "\n where HOSOCDSX_FK=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"
								+ lsxid + "' and CONGDOANSANXUAT_FK='"
								+ cdidcurrent + "')"
								+ "\n  and GIAIDOANSANXUAT_FK='" + gdid
								+ "' and me='" +(k+1)
								+ "' and sanpham_fk='"
								+ rsQuery.getString("SANPHAM_FK") + "'";
						System.out.println("==========queryCTTS==============="+queryCTTS);
						ResultSet rsCTTS=db.get(queryCTTS);
						if(rsCTTS!=null){
							if (rsCTTS.next() == false) {
								ArrayList<String> arrCTMe = new ArrayList<String>();
								arrCTMe.add("0");
								arrCTMe.add(" ");
								arrCTMe.add("1");
								arrCTMe.add(" ");
								arrCTTS.add(arrCTMe);
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
						
						ArrayList<String> arrTSCT = new ArrayList<String>();
						String txt="";
						String chk1="☐";
							for (int j = 0; j < arrCTTS.size(); j++) {
								for (int j2 = j; j2 < arrCTTS.get(j).size(); j2++) {
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
							
						for (int j = 0; j < mangTS.length; j++) {

								FormatExcel.mergeCells_Style12(worksheet, sodong, sodong,u,u+Integer.parseInt(mangTS[j])-1,u,arrTSCT.get(j),true,false,65,TextAlignmentType.CENTER);
								u += Integer.parseInt(mangTS[j]);

						}
					}
					
					String queryTBC=" select TEN from (select ('TSCD' + cast(pk_seq as varchar)) as pk_seq, ma + case when diengiai is null then '' else ' - ' + diengiai end as ten "
							+ "\n from ERP_TAISANCODINH union all select ('CPTT' + cast(pk_seq as varchar)) as pk_seq,ma + case when diengiai is null then '' else ' - ' + diengiai end as ten from ERP_CONGCUDUNGCU) DATA "
							+ "\n where PK_SEQ='"+rsQuery.getString("THIETBICAN")+"'";
					ResultSet rsTBC=db.get(queryTBC);
					if(rsTBC!=null){
						if (rsTBC.next() == false) {
							FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 42,46,"AQ","☐ ",true,
				        			false,65,TextAlignmentType.CENTER);
					      } else {
					        do {
					        	FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 42,46,"AQ","☐"+rsTBC.getString("TEN"),true,
					        			false,65,TextAlignmentType.CENTER);
					        } while (rsTBC.next());
					      }
						rsTBC.close();
						
					}
					/* TB Khác */
					queryTBC=" select TEN from (select ('TSCD' + cast(pk_seq as varchar)) as pk_seq, ma + case when diengiai is null then '' else ' - ' + diengiai end as ten "
							+ "\n from ERP_TAISANCODINH union all select ('CPTT' + cast(pk_seq as varchar)) as pk_seq,ma + case when diengiai is null then '' else ' - ' + diengiai end as ten from ERP_CONGCUDUNGCU) DATA "
							+ "\n where PK_SEQ='"+rsQuery.getString("THIETBICANKHAC")+"'";
					rsTBC=db.get(queryTBC);
					if(rsTBC!=null){
						if (rsTBC.next() == false) {
							FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 47, 51,"AV","☐ ",true,
									false,65,TextAlignmentType.CENTER);
					      } else {
					        do {
					        	FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 47, 51,"AV","☐"+rsQuery.getString("THIETBICANKHAC"),true,
										false,65,TextAlignmentType.CENTER);
					        } while (rsTBC.next());
					      }
						rsTBC.close();
						
					}
					sodong++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sobdct=sodong;

		/*Có in nghiên*/
		/*Thời gian thực hiện*/
		FormatExcel.mergeCells_Style12(worksheet, sobdct, sobdct, 0, 4,"A","Thời gian thực hiện:",
				true,false,true,35,TextAlignmentType.LEFT);
		
		FormatExcel.mergeCells_Style12(worksheet, sobdct, sobdct, 5, 51,"F","... .... h ... .......-... ..... h.... ......, ngày ...............",
				true,false,true,35,TextAlignmentType.LEFT);
		sobdct++;
		
		/*Người thực hiện*/
		FormatExcel.mergeCells_Style12(worksheet, sobdct, sobdct, 0, 4,"A","Người thực hiện:",
				true,false,true,35,TextAlignmentType.LEFT);
		
		FormatExcel.mergeCells_Style12(worksheet, sobdct, sobdct, 5, 51,"F","... ...",
				true,false,35,TextAlignmentType.LEFT);

		sobdct++;
		
		/*Người kiểm tra*/
		FormatExcel.mergeCells_Style12(worksheet, sobdct, sobdct, 0, 4,"A","Người kiểm tra:",
				true,false,true,35,TextAlignmentType.LEFT);
		
		FormatExcel.mergeCells_Style12(worksheet, sobdct, sobdct, 5, 28,"F","Thủ kho:     .....",
				true,false,35,TextAlignmentType.LEFT);
		
		FormatExcel.mergeCells_Style12(worksheet, sobdct, sobdct, 29, 51,"AD","ĐBCL:     .....",
				true,false,35,TextAlignmentType.LEFT);
		sobdct++;

		String ghichu="";
		query =" select Ghichu from ERP_HOSOCDSX_GHICHU where hosocdsx_fk=(select top(1) PK_SEQ from ERP_HOSOCDSX WHERE LENHSANXUAT_FK='"
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
		FormatExcel.mergeCells_Style12(worksheet, sobdct, sobdct, 0, 51,"A","Ghi chú: "+ghichu,
				true,false,35,TextAlignmentType.LEFT);

		FormatExcel.setborder_shadow(cells, i, 0, sobdct, 51);
		sobdct++;
		System.out.println("so dong ket thuc :"+sobdct);
		return sobdct+1;
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
