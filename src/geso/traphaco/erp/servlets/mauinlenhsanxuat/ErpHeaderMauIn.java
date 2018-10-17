package geso.traphaco.erp.servlets.mauinlenhsanxuat;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import jxl.format.BorderLineStyle;

import org.apache.poi.hssf.util.HSSFColor.BLACK;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellArea;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Picture;
import com.aspose.cells.PlacementType;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;
import com.cete.dynamicpdf.Placement;


public class ErpHeaderMauIn  extends HttpServlet {
	public ErpHeaderMauIn() {

	}

	public  int Create(int i,String lsxid, String gdid,String cdidcurrent, String tengd,  dbutils db, Worksheet worksheet, Cells cells,  String pathPDF) {
		int sodong = i;
		String tenPX="";
		String tenSP="";
		String hoSo="";
		String soLo="";
		String lanbh="";
		String ngaybh="";
		String daychuyen="";
		String query="SELECT PX.tennhamay TENPHANXUONG, SP.MA MASP, SP.TEN TENSP, DBC.TEN AS DANGBAOCHE, isnull(KB.HOSOLOSX,'') as HOSO,"
				+ "\n isnull(KB.NGAYBANHANHHSL,'') as NGAYBANHANH, isnull(KB.LANBANHANHHSL,'') as LANBANHANH"
				+ "\n FROM ERP_LENHSANXUAT_GIAY LSX  INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= LSX.SANPHAM_FK "
				+ "\n INNER JOIN Erp_KichBanSanXuat_Giay KB ON KB.PK_SEQ= LSX.KICHBANSANXUAT_FK left JOIN ERP_NHAMAY PX ON PX.pk_seq= KB.NhaMay_FK "
				+"\n LEFT JOIN DANGBAOCHE DBC ON DBC.PK_SEQ= SP.DANGBAOCHE "
				+"\n WHERE LSX.PK_SEQ='"+lsxid+"'";
		System.out.println("===========Query Header=============="+query);
		ResultSet rs=db.get(query);
		if(rs!=null){
			try {
				while (rs.next()) {
					tenPX=rs.getString("TENPHANXUONG");
					tenSP=rs.getString("TENSP");
					hoSo=rs.getString("DANGBAOCHE");
					soLo=rs.getString("HOSO");
					lanbh=rs.getString("LANBANHANH");
					ngaybh=formatDate(rs.getString("NGAYBANHANH"));
				}rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		 query="select top(1) isnull(bom.daychuyensanxuat,'') as daychuyensx "
		 		+ "\n from erp_lenhsanxuat_giay lsx left JOIN Erp_KichBanSanXuat_Giay KB ON KB.PK_SEQ= LSX.KICHBANSANXUAT_FK "
		 		+ "\n inner join  Erp_KichBanSanXuat_CongDoanSanXuat_Giay kbcd on kb.pk_seq=kbcd.kichbansanxuat_fk "
		 		+ "\n inner join erp_danhmucvattu bom on kbcd.DanhMucVattu_FK=bom.pk_seq where lsx.pk_seq='"+lsxid+"' "
		 		+ "\n order by kbcd.thutu desc";
		 rs=db.get(query);
		if(rs!=null){
			try {
				while (rs.next()) {
					daychuyen=rs.getString("daychuyensx");
				}rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		FormatExcel.setborder_shadow(cells, 0, 0, 3, 51);
		
		/*Hình Logo*/
		ReportAPI.mergeCells(worksheet, sodong, sodong + 1, 0, 8);
		Cell cell = cells.getCell("A" + Integer.toString(sodong + 1));
		/*String urlanh=pathPDF+"/logotrahy.png";*/
		String urlanh=pathPDF+"\\logotrahy.png";
		System.out.println(" lay img: "+ urlanh);
		int index=worksheet.getPictures().add(0,0,0,0,urlanh);
		Picture picture=worksheet.getPictures().get(index);
		picture.setWidthInch(1.55);
		picture.setHeightInch(0.50);
		picture.setLeftInch(0.2);
		picture.setTopInch(0.05);
		cells.setRowHeightPixel(sodong, 27);
		
		/*Hồ Sơ Sản Xuất*/
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 9, 40, "J", "HỒ SƠ LÔ SẢN XUẤT",true, true,27,TextAlignmentType.CENTER);
		
		/*Số*/
		FormatExcel.mergeCells_Style12(worksheet, sodong, sodong, 41, 51,"AP", "Số: "+soLo,true,false,27,TextAlignmentType.CENTER);
		
		/*Dây chuyền*/
		FormatExcel.mergeCells_Style12(worksheet, sodong+2, sodong +3, 0, 8,"A","Dây chuyền: "+daychuyen,true,true,27,TextAlignmentType.CENTER);
		
		/*Tên Sản Phẩm*/
		FormatExcel.mergeCells_Style12(worksheet, sodong + 1, sodong + 2, 9, 40,"J",tenSP,true,true,27,TextAlignmentType.CENTER,14);
		
		/*Dạng bào chế:..................*/
		FormatExcel.mergeCells_Style12(worksheet, sodong + 3, sodong + 3, 9, 40,"J","Dạng bào chế: "+hoSo,true,false,41,TextAlignmentType.CENTER);

		/*Ngày BH*/
		FormatExcel.mergeCells_Style12(worksheet, sodong + 1, sodong + 1, 41, 45,"AP","Ngày BH: ",true,false,27,TextAlignmentType.LEFT);
		
		FormatExcel.mergeCells_Style12(worksheet, sodong +1, sodong + 1, 46, 51,"AU",ngaybh,true,false,27,TextAlignmentType.CENTER);
		
		/*Lần BH*/
		FormatExcel.mergeCells_Style12(worksheet, sodong + 2, sodong + 2, 41, 45,"AP","Lần BH: ",true,false,27,TextAlignmentType.LEFT);
		
		FormatExcel.mergeCells_Style12(worksheet, sodong +2, sodong + 2, 46, 51,"AU",lanbh,true,false,27,TextAlignmentType.CENTER);
		
		/*Số Lô SX*/
		FormatExcel.mergeCells_Style12(worksheet, sodong + 3, sodong + 3, 41, 45,"AP","Số Lô SX: ",true,false,27,TextAlignmentType.LEFT);
		
		FormatExcel.mergeCells_Style12(worksheet, sodong + 3, sodong + 3, 46, 51,"AU"," ",true,false,27,TextAlignmentType.CENTER);
		
		/*sodong++;*/
		sodong = 6;
		return sodong;
	}
	private static String formatDate(String date){
		if (date.trim().length()>0 ) {
			String[] s=date.split("-");
			return s[2]+"/"+s[1]+"/"+s[0];
		}
		return "";
	}
}
