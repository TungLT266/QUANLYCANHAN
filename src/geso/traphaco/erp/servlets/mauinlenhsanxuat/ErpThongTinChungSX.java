package geso.traphaco.erp.servlets.mauinlenhsanxuat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ILenhSXCongDoan;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuat;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Column;
import com.aspose.cells.Font;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;
import com.extentech.formats.XLS.Array;

public class ErpThongTinChungSX {

	public ErpThongTinChungSX() {

	}
	
	public static int Create(int i, String lsxid, String cdidcurrent,
			String gdid, String tengd, dbutils db, Worksheet worksheet,
			Cells cells, String pathPDF) {
		System.out.println("=========================lsxid:" + lsxid);
		int sodongbd = 0;
		int some = 0;
		int cotdbct = 0;
		int sdbd=i;
		String quycach="";
		String sdk="";
		String ngayhethansdk="";
		String qtsx="";
		String ngaybhqtsx="";
		String ngaybh="";
		double handung=0;

		/* Lấy tổng số mẻ */
		String querySoMe =" SELECT top(1) ISNULL(SOLUONGME,0) AS SOLUONGME ,isnull(KB.NGAYBANHANHHSL,'') as NGAYBANHANH,isnull(SP.HANSUDUNG,0) as hansudung,"
				+ "\n isnull(cbsp.hancongbo,'') as hancongbo,isnull(cbsp.sodangki,'') as sodangki,bom.quycach, isnull(bom.ngaybanhanhqtsx,'') as ngaybanhanhqtsx,"
				+ "\n isnull(bom.quytrinhsanxuat,'') as quytrinhsanxuat,isnull(bom.daychuyensanxuat,'') as daychuyen,isnull(dvdl.diengiai,'') as dvdl "
				+ "\n FROM ERP_LENHSANXUAT_GIAY A left join erp_dondathang ddh on ddh.pk_seq=a.dondathang_fk "
				+ "\n INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK "
				+ "\n right join erp_sanpham_nhacungcap cbsp on sp.pk_seq=cbsp.sanpham_fk "
				+ "\n LEFT JOIN ERP_KICHBANSANXUAT_GIAY KB ON KB.PK_SEQ=KICHBANSANXUAT_FK "
				+ "\n inner join  Erp_KichBanSanXuat_CongDoanSanXuat_Giay kbcd on kb.pk_seq=kbcd.kichbansanxuat_fk "
				+ "\n inner join erp_danhmucvattu bom on kbcd.DanhMucVattu_FK=bom.pk_seq "
				+ "\n inner join donvidoluong dvdl on bom.dvdl_fk=dvdl.pk_seq WHERE A.PK_SEQ = '"+lsxid+"'order by kbcd.thutu desc";
		System.out.println("=========================Query Số Mẻ:" + querySoMe);
		ResultSet rsSoMe = db.get(querySoMe);
		if (rsSoMe != null) {
			try {
				while (rsSoMe.next()) {
					some = rsSoMe.getInt("SOLUONGME");
					ngaybhqtsx=formatDate(rsSoMe.getString("ngaybanhanhqtsx"));
					qtsx=rsSoMe.getString("quytrinhsanxuat");
					sdk=rsSoMe.getString("sodangki");
					ngayhethansdk=formatDate(rsSoMe.getString("hancongbo"));
					ngaybh=formatDate(rsSoMe.getString("NGAYBANHANH"));
					if (rsSoMe.getDouble("hansudung")!=0) {
						handung=Math.round(rsSoMe.getDouble("hansudung")/30);
					}
				}
				rsSoMe.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("Bắt đầu in thông tin chung sản xuất");
		/*Khoảng trắng đầu tiên*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd+1, 0, 2, "A", "", true,true,35,TextAlignmentType.CENTER);

		/*Người Phê duyệt*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 3,19, "D", "Người phê duyệt", true,false,35,TextAlignmentType.CENTER);

		/*Người soát xét*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 20,36, "U", "Người soát xét", true,false,35,TextAlignmentType.CENTER);

		/*Người soạn thảo*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 37,51, "AL", "Người soạn thảo", true,false,35,TextAlignmentType.CENTER);
		sdbd++;

		/*TP.QLCL*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 3,19, "D", "TP.QLCL", true,false,35,TextAlignmentType.CENTER);

		/*QĐPX*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 20,36, "U", "QĐPX", true,false,35,TextAlignmentType.CENTER);

		/* Khoảng trắng dưới người soạn thảo*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 37,51, "AL", " ", true,false,35,TextAlignmentType.CENTER);
		sdbd++;

		/*Ngày*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 2, "A", "Ngày", true,false,35,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 3,19, "D", ngaybh, true,false,35,TextAlignmentType.CENTER);


		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 20,36, "U", " ", true,false,35,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 37,51, "AL", " ", true,false,35,TextAlignmentType.CENTER);
		sdbd++;

		/*Ký tên*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd+2, 0, 2, "A", "Ký tên", true,false,35,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd+2, 3,19, "D", " ", true,false,35,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd+2, 20,36, "U", " ", true,false,35,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd+2, 37,51, "AL", " ", true,false,35,TextAlignmentType.CENTER);
		/*sdbd+=sdbd+3;*/
		sdbd=sdbd+3;

		/*Họ tên*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 2, "A", "Họ tên", true,false,35,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 3,19, "D", "Hoàng Thị Hường", true,false,35,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 20,36, "U", " ", true,false,35,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 37,51, "AL", "Nguyễn Minh Tiến", true,false,35,TextAlignmentType.CENTER);
		sdbd++;

		/*Tiêu đề Thông tin lô sản xuất */
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 51, "A", "I.THÔNG TIN LÔ SẢN XUẤT", true,true,35,TextAlignmentType.CENTER);
		sdbd++;

		/*Nội dung Thông tin lô sản xuất */
		String tenSP="",quyCach="",kichbansx_fk="",soLo ="";
		double slTheoLenh = 0;
		String queryTTLSX="SELECT LSX.KICHBANSANXUAT_FK, PX.tennhamay TENPHANXUONG, SP.MA MASP, SP.TEN TENSP, DBC.TEN AS DANGBAOCHE, isnull(LSX.SOLO,'') as SOLO ,"
				+ "\n LSX.SOLUONG,isnull(SP.QUYCACH_TEXT,'') as QUYCACH, isnull(dmvt.quycach,'') as quycach1 "
				+ "\n FROM ERP_LENHSANXUAT_GIAY LSX INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= LSX.SANPHAM_FK "
				+ "\n INNER JOIN Erp_KichBanSanXuat_Giay KB ON KB.PK_SEQ= LSX.KICHBANSANXUAT_FK "
				+ "\n left join ERP_DANHMUCVATTU dmvt on dmvt.sanpham_fk = LSX.SANPHAM_FK and dmvt.dvdl_fk = sp.dvdl_fk "
				+ "\n LEFT JOIN ERP_NHAMAY PX ON PX.pk_seq= KB.NhaMay_FK LEFT JOIN DANGBAOCHE DBC ON DBC.PK_SEQ= SP.DANGBAOCHE"
				+ "\n WHERE LSX.PK_SEQ='"+lsxid+"'";
		System.out.println("lay thong tin lo san xuat: " + queryTTLSX);
		ResultSet rsLSX=db.get(queryTTLSX);
		if (rsLSX!=null) {
			try {
				while (rsLSX.next()) {
					tenSP=rsLSX.getString("TENSP");
					slTheoLenh=rsLSX.getDouble("SOLUONG");
					soLo=rsLSX.getString("SOLO");
					quyCach=rsLSX.getString("quycach1");
					kichbansx_fk = rsLSX.getString("KICHBANSANXUAT_FK");
				}rsLSX.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*Tên sản phẩm*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 3, "A", "Tên sản phẩm: ", true,false,41,TextAlignmentType.LEFT);

		/*Tên sản phẩm*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 4, 27, "E", tenSP, true,true,41,TextAlignmentType.CENTER);

		/*SĐK:*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 28, 51, "AC", "SĐK: "+sdk, true,false,41,TextAlignmentType.LEFT);
		sdbd++;

		/*Quy cách*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 3, "A", "Quy cách: ", true,false,41,TextAlignmentType.LEFT);

		/*quy cách*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 4, 27, "E",quyCach, true,false,41,TextAlignmentType.CENTER);

		/*Ngày hết hạn*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 28, 51, "AC", "Ngày hết hạn SĐK: "+ngayhethansdk, true,false,41,TextAlignmentType.LEFT);
		sdbd++;

		/*SL theo lệnh*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 3, "A", "SL theo lệnh: ", true,false,41,TextAlignmentType.LEFT);

		/*sl*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 4, 15, "E",slTheoLenh, true,false,41,TextAlignmentType.CENTER);

		/*số lô sx*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 16,27, "Q", "Số lô SX: " + soLo, true,true,41,TextAlignmentType.LEFT);

		/*HD đăng ký*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 28, 51, "AC", "HD đăng ký: "+(handung!=0?Math.round(handung)+" tháng":" "), true,false,41,TextAlignmentType.LEFT);
		sdbd++;

		/*Ngày bắt đầu SX*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 3, "A", "Ngày bắt đầu SX: ", true,false,41,TextAlignmentType.LEFT);

		/*Ngày bắt đầu SX*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 4, 15, "E", " ", true,false,41,TextAlignmentType.CENTER);

		/*NSX*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 16,27, "Q", "NSX: ", true,true,41,TextAlignmentType.LEFT);

		/*QTSX số:*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 28, 51, "AC", "QTSX số: "+qtsx, true,false,41,TextAlignmentType.LEFT);
		sdbd++;

		/*Ngày hoàn thành*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 3, "A", "Ngày hoàn thành: ", true,false,41,TextAlignmentType.LEFT);

		/*Ngày hoàn thành*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 4, 15, "E", " ", true,false,41,TextAlignmentType.CENTER);

		/*HD:*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 16,27, "Q", "HD: ", true,true,41,TextAlignmentType.LEFT);

		/*Ngày BH:*/
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 28, 51, "AC", "Ngày BH: "+ngaybhqtsx, true,false,41,TextAlignmentType.LEFT);
		sdbd++;

		/*Tiêu đề Thông tin thiết bị sản xuất */
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 51, "A", "II.THÔNG TIN THIẾT BỊ SẢN XUẤT", true,true,35,TextAlignmentType.CENTER);
		sdbd++;

		/*Thông tin thiết bị sản xuất */
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 17, "A", "Tên thiết bị", true,true,41,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 18,24, "S", "Mã TB", true,true,41,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 25,44, "Z", "Tên thiết bị", true,true,41,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 45,51, "AT", "Mã TB", true,true,41,TextAlignmentType.CENTER);
		sdbd++;

//		cdidcurrent="100093";
		String count = "select count(*) as sodong from  (select DISTINCT isnull(tb.TEN,'') as tenthietbi, isnull(tb.ma,'') as mathietbi\r\n" + 
				" from Erp_CongDoanSanXuat_GIAIDOAN_Giay cdsx_gd inner join ERP_GIAIDOANSX_THIETBI gdsx_tb on gdsx_tb.GIAIDOAN_FK = cdsx_gd.GiaiDoan_FK \r\n" + 
				" inner join ERP_GIAIDOANSX_THIETBI_CHITIET gdsx_tbct on gdsx_tbct.GIAIDOAN_TB_FK = gdsx_tb.PK_SEQ \r\n" + 
				" left join ERP_PHONGBANSX pb on pb.PK_SEQ = cdsx_gd.PhongBan_FK \r\n" + 
				" left join ERP_GIAIDOANSX gd on gd.PK_SEQ = cdsx_gd.GiaiDoan_FK \r\n" + 
				" left join ERP_TAISANCODINH tscd on tscd.pk_seq = gdsx_tb.TSCD_CCDC_FK and gdsx_tb.loai = 0 \r\n" + 
				" left join ERP_CONGCUDUNGCU ccdc on ccdc.PK_SEQ = gdsx_tb.TSCD_CCDC_FK and gdsx_tb.loai = 1 \r\n" + 
				" left join ERP_THIETBISX tb on tb.PK_SEQ = gdsx_tb.ThietBiSX_FK left join DONVIDOLUONG dvdl on dvdl.PK_SEQ = gdsx_tbct.DVDL_FK \r\n" + 
				" where isnull(tb.TEN,'')!='') as tb ";
		ResultSet demrs = db.get(count);
		int sodong=0;
		try {
			if(demrs.next()){
				sodong=demrs.getInt("sodong");
			}demrs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}


		String query ="select DISTINCT isnull(tb.TEN,'') as tenthietbi, isnull(tb.ma,'') as mathietbi\r\n" + 
				" from Erp_CongDoanSanXuat_GIAIDOAN_Giay cdsx_gd inner join ERP_GIAIDOANSX_THIETBI gdsx_tb on gdsx_tb.GIAIDOAN_FK = cdsx_gd.GiaiDoan_FK \r\n" + 
				" inner join ERP_GIAIDOANSX_THIETBI_CHITIET gdsx_tbct on gdsx_tbct.GIAIDOAN_TB_FK = gdsx_tb.PK_SEQ \r\n" + 
				" left join ERP_PHONGBANSX pb on pb.PK_SEQ = cdsx_gd.PhongBan_FK \r\n" + 
				" left join ERP_GIAIDOANSX gd on gd.PK_SEQ = cdsx_gd.GiaiDoan_FK \r\n" + 
				" left join ERP_TAISANCODINH tscd on tscd.pk_seq = gdsx_tb.TSCD_CCDC_FK and gdsx_tb.loai = 0 \r\n" + 
				" left join ERP_CONGCUDUNGCU ccdc on ccdc.PK_SEQ = gdsx_tb.TSCD_CCDC_FK and gdsx_tb.loai = 1 \r\n" + 
				" left join ERP_THIETBISX tb on tb.PK_SEQ = gdsx_tb.ThietBiSX_FK left join DONVIDOLUONG dvdl on dvdl.PK_SEQ = gdsx_tbct.DVDL_FK \r\n" + 
				" where isnull(tb.TEN,'')!='' ";

		System.out.println("lay thiet bi: " + query);
		ResultSet thietbiRs = db.get(query);
		int tmp_dong=0;

		List <String> Ten_MaThietBi =new ArrayList<String>();
		try {
			while(thietbiRs.next()){
				String ten_ma=thietbiRs.getString("tenthietbi")+"//"+thietbiRs.getString("mathietbi");
				Ten_MaThietBi.add(ten_ma);
			}thietbiRs.close();

			while(Ten_MaThietBi.size()>tmp_dong){
				//chay het 1 vong for thi xuat 1 dong 
				for( int coti=0;coti<=45;coti++){
					//Kiểm tra có bị dư thì xuất dòng trắng
					if(Ten_MaThietBi.size()==tmp_dong){
						//COT Tên thiết BI - Z22
						Cell cell=cells.getCell(sdbd,25);
						FormatExcel.mergeCells_Style1_laygetCell(cell,worksheet, sdbd, sdbd, 25,44, "AT","", true,false,41,TextAlignmentType.LEFT);

						coti+=20;
						//cot Mã TB - AT22
						cell=cells.getCell(sdbd,45);
						FormatExcel.mergeCells_Style1_laygetCell(cell,worksheet, sdbd, sdbd, 45,51, "AT", "", true,false,41,TextAlignmentType.CENTER);

						sdbd++;
					}
					else{	
						//Tên thiết bị - cot A22
						if(coti<=24){	
							Cell cell=cells.getCell(sdbd,coti);
							FormatExcel.mergeCells_Style1_laygetCell(cell,worksheet, sdbd, sdbd, coti,17, "AT",Ten_MaThietBi.get(tmp_dong).split("//")[0], true,false,41,TextAlignmentType.LEFT);
							
							coti+=18;
							//cot Mã TB - S22
							cell=cells.getCell(sdbd,coti);
							FormatExcel.mergeCells_Style1_laygetCell(cell,worksheet, sdbd, sdbd, coti, 24, "AT", Ten_MaThietBi.get(tmp_dong).split("//")[1], true,false,41,TextAlignmentType.CENTER);
							coti+=6;

							tmp_dong++;

						}else{
							//COT Tên thiết BI - Z22
							Cell cell=cells.getCell(sdbd,coti);
							FormatExcel.mergeCells_Style1_laygetCell(cell,worksheet, sdbd, sdbd, coti,44, "AT",Ten_MaThietBi.get(tmp_dong).split("//")[0], true,false,41,TextAlignmentType.LEFT);

							//cot Mã TB - AT22
							coti+=20;
							cell=cells.getCell(sdbd,coti);
							FormatExcel.mergeCells_Style1_laygetCell(cell,worksheet, sdbd, sdbd, coti,51, "AT", Ten_MaThietBi.get(tmp_dong).split("//")[1], true,false,41,TextAlignmentType.CENTER);
							tmp_dong++;

							sdbd++;
						}
					}
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*Tiêu đề Đặc điểm kỹ thuật */
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 51, "A", "III.ĐẶC ĐIỂM KỸ THUẬT", true,true,35,TextAlignmentType.CENTER);
		sdbd++;

		/*Tiêu đề 3.1 Công thức */
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 51, "A", "3.1 Công thức ", true,true,35,TextAlignmentType.LEFT);
		sdbd++;

		/*Thông tin Công thức */
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd+1, 0, 0, "A", "TT", true,true,41,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd+1, 1, 22, "B", "Tên nguyên liệu", true,true,41,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd+1, 23,27, "X", "Đơn vị tính", true,true,41,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 28,39, "AC", "Mẻ pha", true,true,41,TextAlignmentType.CENTER);

		//FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 36,43, "AK", "Mẻ pha dịch bao(cho 2 Mẻ bao)", true,true,41,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 40,51, "AO", "Lô", true,true,41,TextAlignmentType.CENTER);
		sdbd++;
		double soluongInt = slTheoLenh;
		//		int soluongInt1 = Double.parseDouble(slTheoLenh);
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 28,39, "AC", (soluongInt/some)+ " " + "viên", true,true,41,TextAlignmentType.CENTER);

		//FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 36,43, "AK", " ", true,true,41,TextAlignmentType.CENTER);

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 40,51, "AO", slTheoLenh + " viên", true,true,41,TextAlignmentType.CENTER);
		sdbd++;

		query=" SELECT ROW_NUMBER() OVER(ORDER BY isnull(A.SOTT,0) ASC) AS STT, ( select DANHMUCVATTU_FK  from ERP_LENHSANXUAT_CONGDOAN_GIAY "
				+ " where LENHSANXUAT_FK= A.LENHSANXUAT_FK and CONGDOAN_FK= a.CONGDOAN_FK ) AS  bomid "
				+ " ,  isnull(A.SOLUONG,0) as SOLUONG , ISNULL(DUTONKHO,0) AS DUTONKHO  , CD.DIENGIAI AS TENCONGDOAN,  \n" +
				" LENHSANXUAT_FK, CONGDOAN_FK, SP.MA AS VTMA, SP.TEN AS VTTEN, A.VATTU_FK AS IDSP, A.KHOTT_FK, \n" +  
				" isnull( A.SOLUONG,0) as soluongdayc , isnull( A.SOLUONGCHUAN,0) AS SOLUONGCHUAN, DVT as VTDONVI , \n" +
				" isnull(a.isTINHHAMAM,'0') as isTINHHAMAM  ,isnull(a.isTINHHAMLUONG,'0') as isTINHHAMLUONG ,ISNULL(A.HAMAM,0) AS HAMAM \n" +
				",ISNULL(A.HAMLUONG,0) AS HAMLUONG  ,case when tpdaura.sanpham_fk is not null then 1 else 0 end as is_tpdaura  FROM \n" +	  
				" ERP_LENHSANXUAT_BOM_GIAY A  INNER JOIN   \n" +
				" ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=A.LENHSANXUAT_FK   \n" +
				" INNER JOIN ERP_CONGDOANSANXUAT_GIAY CD ON A.CONGDOAN_FK=CD.PK_SEQ \n" +  
				" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = A.VATTU_FK    \n"  
				+ " left join "
				+ "(SELECT DISTINCT  DMVT.SANPHAM_FK  FROM ERP_KICHBANSANXUAT_CONGDOANSANXUAT_GIAY A   "
				+ "INNER JOIN ERP_DANHMUCVATTU DMVT ON DMVT.PK_SEQ= A.DANHMUCVATTU_FK "
				+ "  WHERE KICHBANSANXUAT_FK= "+kichbansx_fk+") as tpdaura on tpdaura.sanpham_fk = SP.PK_SEQ     \n" +  
				" WHERE A.LENHSANXUAT_FK = "+lsxid +"  "
				+ " \n";
		System.out.println("lay ra cong doan :" + query);
		ResultSet kythuatRs = db.get(query);
		String stt="";
		String tennl="";
		String donvitinh="";
		String slmepha="";
		double slchuantheoLo=0.0;
		int n=sdbd;
		try{
			while (kythuatRs.next()){
				stt=kythuatRs.getString("stt");
				FormatExcel.mergeCells_Style12(worksheet, n, n, 0, 0, "A", stt, true,false,41,TextAlignmentType.CENTER);
				tennl=kythuatRs.getString("VTTEN");
				FormatExcel.mergeCells_Style12(worksheet, n, n, 1, 22, "B", tennl, true,false,41,TextAlignmentType.LEFT);
				donvitinh=kythuatRs.getString("VTDONVI");
				FormatExcel.mergeCells_Style12(worksheet, n, n, 23,27, "X", donvitinh.toLowerCase(), true,false,41,TextAlignmentType.LEFT);
				slchuantheoLo=kythuatRs.getDouble("soluongchuan");
				double sl1 = slchuantheoLo;
				//double slmepha1= sl1/some;
				double roundOff = Math.round((sl1/some) * 10.0) / 10.0;
				FormatExcel.mergeCells_Style12(worksheet, n, n, 28,39, "AC", roundOff, true,false,41,TextAlignmentType.CENTER);
				//FormatExcel.mergeCells_Style12(worksheet, n, n, 36,43, "AK", " ", true,false,41,TextAlignmentType.CENTER);
				FormatExcel.mergeCells_Style12(worksheet, n, n, 40,51, "AO", slchuantheoLo, true,false,41,TextAlignmentType.CENTER);
				n++;
			}


		}catch(Exception er){
			er.printStackTrace();
		}
		sdbd=n;

		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 51, "A", "Ghi chú: *Bù hàm lượng 100%, hàm ẩm 0%",
				true,false,true,35,TextAlignmentType.LEFT);
		sdbd++;
		/*Tiêu đề 3.2 Yêu cầu nguyên liệu */
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 51, "A", "3.2 Yêu cầu nguyên liệu: Tất cả nguyên liệu phải được kiểm tra đạt tiêu chuẩn "
				+ "trước khi đưa vào sản xuất, các nguyên liệu là hoạt chất phải được định tính 100% trước khi sản xuất.",
				true,true,41,TextAlignmentType.LEFT);
		sdbd++;

		/*Tiêu đề 3.3 Mô tả sản phẩm */
		FormatExcel.mergeCells_Style12(worksheet, sdbd, sdbd, 0, 51, "A", "3.3 Mô tả sản phẩm: Viên nén bao pim màu xanh, thành và cạnh viên lành lặn,đường kính 10 mm.",
				true,true,35,TextAlignmentType.LEFT);
		sdbd++;

		FormatExcel.setborder_shadow(cells, i, 0, sdbd, 51);
		sdbd++;
		System.out.println("so dong ket thuc :"+sdbd);
		return sdbd;
	}

	private static String formatDate(String date){
		if (date.trim().length()>0 ) {
			String[] s=date.split("-");
			return s[2]+"/"+s[1]+"/"+s[0];
		}
		return "";
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
