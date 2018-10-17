package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.PivotTable;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import java.util.*;

public class SecondarySalesPIRTT extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SecondarySalesPIRTT() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		obj.setuserId(userId);
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/SecondarySalesPurchaseInventoryReport.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		// OutputStream out = response.getOutputStream();
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		geso.dms.distributor.util.Utility Ult = new geso.dms.distributor.util.Utility();

		String nppId = request.getParameter("nppId");
		obj.setnppId(nppId);
		obj.setuserTen(userTen);

		String vungId = request.getParameter("vungId"); // <!---
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);

		String khuvucId = request.getParameter("khuvucId"); // <!---
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);

		String kenhId = request.getParameter("kenhId"); // <!---
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);

		String dvkdId = request.getParameter("dvkdId"); // <!---
		if (dvkdId == null)
			dvkdId = "";
		obj.setdvkdId(dvkdId);

		String nhanhangId = request.getParameter("nhanhangId"); // <!---
		if (nhanhangId == null)
			nhanhangId = "";
		obj.setnhanhangId(nhanhangId);

		String chungloaiId = request.getParameter("chungloaiId");// <!---
		if (chungloaiId == null)
			chungloaiId = "";
		obj.setchungloaiId(chungloaiId);

		String tungay = request.getParameter("Sdays"); // <!---
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = request.getParameter("Edays");// <!---
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);

		String khoId = request.getParameter("khoId"); // <!---
		if (khoId == null)
			khoId = "";
		obj.setkhoId(khoId);

		String vat = request.getParameter("vat"); // <!---
		obj.setvat(vat);
		String instransit = request.getParameter("instransit"); // <!---
		obj.setdiscount(instransit);

		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);

		String[] fieldsAn = request.getParameterValues("fieldsAn");
		obj.setFieldHidden(fieldsAn);
		
		
		if (!tungay.equals("") && !denngay.equals("")) {
			String sql = "";
			
		
			String action = request.getParameter("action");
			String query = setQuery(sql, obj);
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			if (action.equals("tao")) {
				
				response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "Attachment; filename=BaoCaoXuatNhapTon(TT)"+this.getPiVotName()+".xlsm");


				OutputStream out = response.getOutputStream();
				try {
					CreatePivotTable(out, response, request, fieldsHien, obj,query);
					// PivotTable
				} catch (Exception ex) {
					System.out.println("Loi tai day :"+ ex.toString());
					
					request.getSession()
							.setAttribute("errors", ex.getMessage());
				}
			}
		}
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/SecondarySalesPurchaseInventoryReport.jsp";
		// String nextJSP =
		// "/TraphacoHYERP/pages/Distributor/SecondarySalesPurchaseInventoryReport.jsp";
		response.sendRedirect(nextJSP);
	}
	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    String name = sdf.format(cal.getTime());
	    name = name.replaceAll("-", "");
	    name = name.replaceAll(" ", "_");
	    name = name.replaceAll(":", "");
	    return "_" + name;
	    
	}
	private void CreatePivotTable(OutputStream out,
			HttpServletResponse response, HttpServletRequest request,
			String[] fieldsHien, IStockintransit obj, String query)
			throws Exception {
		try {
			
				//String strfstream="D:\\Provence Projects\\SalesUp\\WebContent\\pages\\Templates\\BaoCaoXuatNhapTon(TT).xlsm";
			String strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoXuatNhapTon(TT).xlsm";		

			FileInputStream fstream = new FileInputStream(strfstream);	
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	     

			CreateHeader(workbook, obj);
			// FillData(workbook, fieldsHien,query);
			FillData(workbook, fieldsHien, obj);
			
			workbook.save(out);
		    fstream.close();
			

			
			
		} catch (Exception ex) {
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}

	private void FillData(Workbook workbook, String[] fieldsHien,
			IStockintransit obj) throws Exception {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
	
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();
		//System.out.println("Get Du Lieu : "+query);
		//ResultSet rs = db.get(query);
		
		String[] param = new String[13];
		param[0] = obj.getnppId().equals("")?null:obj.getnppId();
		param[1] = obj.gettungay();
		param[2] = obj.getdenngay();
			param[3]=obj.getkenhId().equals("")?null:obj.getkenhId();	
		param[4]=obj.getnhanhangId().equals("")? null:obj.getnhanhangId();
		param[5]=obj.getchungloaiId().equals("")?null:obj.getchungloaiId();
		param[6]=obj.getdvkdId().equals("")?null:obj.getdvkdId();
		param[7]=obj.getkhoId()=="" ?  null:obj.getkhoId();
		param[8]=obj.getkhuvucId()==""?null:obj.getkhuvucId();
		param[9]=obj.getvungId()==""?null:obj.getvungId();
		param[10]=obj.getvat().equals("")?null:obj.getvat();
		param[11]=obj.getuserId();
		param[12]="1";//LAY BAO CAO CENTER
		
	/*	System.out.println(param[0]);
		System.out.println(param[1]);
		System.out.println(param[2]);
		System.out.println(param[3]);
		System.out.println(param[4]);
		System.out.println(param[5]);
		System.out.println(param[6]);
		System.out.println(param[7]);
		System.out.println(param[8]);	
		System.out.println(param[9]);
		System.out.println(param[10]);
		System.out.println(param[11]);
		System.out.println(param[12]);*/
		ResultSet rs = db.getRsByPro("REPORT_XUATNHAPTON", param);
		int index = 2;
		Cell cell = null;
		while (rs.next()) {
			cell = cells.getCell("CA" + String.valueOf(index));
			cell.setValue(rs.getString("KENH")); //0
			cell = cells.getCell("CB" + String.valueOf(index));
			cell.setValue(rs.getString("DVKD")); cell.setValue(rs.getString("VUNG"));//1
			cell = cells.getCell("CC" + String.valueOf(index));
			cell.setValue(rs.getString("VUNG"));//2
			cell = cells.getCell("CD" + String.valueOf(index));
			cell.setValue(rs.getString("KHUVUC"));//3
			
			cell = cells.getCell("CE" + String.valueOf(index));
			cell.setValue(rs.getString("NPPID"));//4
			cell = cells.getCell("CF" + String.valueOf(index));
			cell.setValue(rs.getString("NPP"));//5
			
			cell = cells.getCell("CG" + String.valueOf(index));
			cell.setValue(rs.getString("NHAN"));//6
			
			cell = cells.getCell("CH" + String.valueOf(index));
			cell.setValue(rs.getString("CHUNGLOAI"));//7
			
			
			cell = cells.getCell("CI" + String.valueOf(index));
			cell.setValue(rs.getString("MASP"));  ///8
			cell = cells.getCell("CJ" + String.valueOf(index));
			cell.setValue(rs.getString("TENSP"));//9
			
			
		
			
			cell = cells.getCell("CK" + String.valueOf(index));
			cell.setValue(rs.getString("KHO")); //10
			
			cell = cells.getCell("CL" + String.valueOf(index));
			cell.setValue(rs.getString("Type"));//11
			
		
			cell = cells.getCell("CM" + String.valueOf(index));
			cell.setValue(Float.parseFloat(rs.getString("Quantily")));//12
			cell = cells.getCell("CN" + String.valueOf(index));
			cell.setValue(Float.parseFloat(rs.getString("soluong"))); // 13vQuantity
			cell = cells.getCell("CO" + String.valueOf(index));
			cell.setValue(Float.parseFloat(rs.getString("AMOUNT"))); // 14 Piece
			index++;
		}
		if (rs != null)
			rs.close();
		if (db != null)
			db.shutDown();

		// Set key
		Hashtable<String, Integer> selected = new Hashtable<String, Integer>();
		selected.put("Channel", 0);
		selected.put("Business_Unit", 1);	
		selected.put("Region", 2);
		selected.put("Area", 3);	
		selected.put("Distributor", 5);	
		selected.put("Distributor_Code", 4);		
		selected.put("Brands", 6);
		selected.put("Catergory", 7);	
		selected.put("SKU", 8);		
		selected.put("sku_name", 9);		
		selected.put("Warehouse", 10);			
		selected.put("Quantity", 12);
		selected.put("Piece", 13);
		selected.put("Amount", 14);

		ReportAPI.setHidden(workbook, 79);
		
/*		PivotTables pivotTables = worksheet.getPivotTables();
		
		String pos = Integer.toString(index - 1);
		int j = pivotTables.add("=SecondarySalesPurchaseInventoryReport!AA1:AO"
				+ pos, "B12", "PivotTable");
		
		PivotTable pivotTable = pivotTables.get(j);
		pivotTable.setRowGrand(true);
		pivotTable.setColumnGrand(true);
		pivotTable.setAutoFormat(true);
		for (int i = 0; i < fieldsHien.length; ++i){
			System.out.println("THU TU : " + i +" :"+ fieldsHien[i]);
		}
			
		
		
		boolean flag = false;
		int dataCount = 1;
		for (int i = 0; i < fieldsHien.length; ++i) {
			int value = selected.get(fieldsHien[i]);
			switch (value) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				pivotTable.addFieldToArea(PivotFieldType.ROW, value);
				break;
			case 6:
				pivotTable.addFieldToArea(PivotFieldType.ROW, value);
				
				break;
			case 7:
				pivotTable.addFieldToArea(PivotFieldType.ROW, value);
				
				break;
			case 8:
				pivotTable.addFieldToArea(PivotFieldType.ROW, value);
				break;
			case 9:
				pivotTable.addFieldToArea(PivotFieldType.ROW, value);
				break;
			case 10:
				pivotTable.addFieldToArea(PivotFieldType.ROW, value);
				break;
			case 12:
				pivotTable.addFieldToArea(PivotFieldType.DATA, value);
				++dataCount;
				break;
			case 13:
				pivotTable.addFieldToArea(PivotFieldType.DATA, value);
				++dataCount;
				break;
			case 14:
				pivotTable.addFieldToArea(PivotFieldType.DATA, value);
				++dataCount;
				break;
			}
			//if (flag == false) {
			//	if ((value == 5) || (value == 6) || (value == 7)
			//			|| (value == 8) || (value == 9) || (value == 10)
			//			|| (value == 11)) {
			//		pivotTable.addFieldToArea(PivotFieldType.COLUMN, 5);
			//		flag = true;
			//	}
			//}
		}
		System.out.println(pivotTable.getRowFields().size());
		for (int i = 0; i < pivotTable.getRowFields().size(); ++i) {
			pivotTable.getRowFields().get(i).setAutoSubtotals(false);
		}
		pivotTable.addFieldToArea(PivotFieldType.COLUMN,
				11);*/

	}

	private void CreateHeader(Workbook workbook, IStockintransit obj)
			throws Exception {

		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.RED, true, 14, "XUẤT NHẬP TỒN");
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(
				cell,
				Color.NAVY,
				true,
				10,
				"Từ ngày : " + obj.gettungay() + "   Đến ngày: "
						+ obj.getdenngay());
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,
				"Ngày tạo: " + obj.getDateTime());
		
		cell = cells.getCell("B3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,
				"Tạo bởi : " + obj.getuserTen());

		cell = cells.getCell("CA1");
		cell.setValue("Kenh Ban Hang");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CB1");
		cell.setValue("Don Vi Kinh Doanh");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CC1");
		cell.setValue("Vung");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CD1");
		cell.setValue("Khu Vuc");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CE1");
		cell.setValue("Ma Nha Phan Phoi");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CF1");
		cell.setValue("Ten Nha Phan Phoi");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CG1");
		cell.setValue("Nhan Hang");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CH1");
		cell.setValue("Chung Loai");
		ReportAPI.setCellHeader(cell);
		
		
		cell = cells.getCell("CI1");
		cell.setValue("Ma San Pham");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CJ1");	
		cell.setValue("Ten San Pham");
		ReportAPI.setCellHeader(cell);
		
		
		
		cell = cells.getCell("CK1");
		cell.setValue("Kho");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CL1");
		cell.setValue("Du Lieu");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CM1");
		cell.setValue("Thung");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CN1");
		cell.setValue("So Luong Le");
		ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("CO1");
		cell.setValue("So Tien ");
		ReportAPI.setCellHeader(cell);
	}

	private String setQuery(String sql, IStockintransit obj) {
		Calendar tondau = Calendar.getInstance();
		int nam = Integer.parseInt(obj.gettungay().substring(0, 4));
		int thang = Integer.parseInt(obj.gettungay().substring(5, 7));
		int ngay = Integer.parseInt(obj.gettungay().substring(8, 10));

		System.out.println("ngay" + nam + "-" + thang + "-" + ngay);
		// tondau.set(nam,ngay,thang);
		System.out.println("ngay" + nam + "-" + thang + "-" + ngay);
		// tondau.set(nam,ngay,thang);
		thang = thang - 1;
		tondau.set(nam, thang, ngay);
		tondau.add(Calendar.DATE, -1);

		nam = tondau.get(Calendar.YEAR);
		thang = tondau.get(Calendar.MONTH);
		thang = thang + 1;// trong java thang bat dau la 0
		ngay = tondau.get(Calendar.DATE);
		String ngay1 = "" + ngay;
		String thang1 = "" + thang;
		if (thang < 10)
			thang1 = "0" + thang;
		if (ngay < 10)
			ngay1 = "0" + ngay;

		String chuoi = nam + "-" + thang1 + "-" + ngay1;
		System.out.println("ngay ton day" + chuoi);
		String query = "SELECT"
				+ "	     TONG.KBH_FK AS KENHID, KBH.TEN AS KENH,"
				+ "		 NPP.MA AS NPPID, NPP.TEN AS NPP,"
				+ "		 DVKD.PK_SEQ AS DVKDID, DVKD.DONVIKINHDOANH AS DVKD,"
				+ "		 KHO.PK_SEQ AS KHOID, KHO.TEN AS KHO,"
				+ "		 SP.MA AS MASP, SP.TEN AS TENSP,VUNG.TEN AS VUNG,KV.TEN AS KHUVUC,"
				+ "		 NHAN.PK_SEQ AS NHANID, NHAN.TEN AS NHAN,"
				+ "		 CHUNGLOAI.PK_SEQ AS CHUNGLOAIID, CHUNGLOAI.TEN AS CHUNGLOAI,"
				+ "        TONG.TYPE, case when qc.soluong1 is null then 0 else TONG.SOLUONG/qc.soluong1 end as Quantily,"
				+ "        TONG.SOLUONG,TONG.SOLUONG * isnull(nppk.GIAMUANPP,0)*"
				+ obj.getvat()
				+ " AS AMOUNT "
				+ "FROM ("
				+ "		(	SELECT  NPP_FK,KBH_FK, KHO_FK, SANPHAM_FK, SUM(SOLUONG) AS SOLUONG ,'1.Ton Dau' as 'Type' "
				+ "			FROM TONKHONGAY"
				+ "			WHERE NGAY = '"
				+ chuoi
				+ "' AND NPP_FK ="
				+ obj.getnppId()
				+ "			GROUP BY KBH_FK, NPP_FK, KHO_FK, SANPHAM_FK"
				+ "		) "
				+ "		union"
				+ "		(  "
				+ "		SELECT NPP_FK,KBH_FK, KHO_FK, SANPHAM_FK, SUM(SOLUONG) AS SOLUONG,'7.Ton Cuoi' as 'Type'"
				+ "			FROM TONKHONGAY"
				+ "			WHERE NGAY ='"
				+ obj.getdenngay()
				+ "'  AND NPP_FK ="
				+ obj.getnppId()
				+ " and sanpham_fk in (select pk_seq from sanpham where trangthai =1)"
				+ "			GROUP BY KBH_FK, NPP_FK, KHO_FK, SANPHAM_FK"
				+ "		)"
				+ "      union "
				+ "		(	"
				+ "			select spnhap_npp.npp_fk,spnhap_npp.kbh_fk,spnhap_npp.kho_fk,spnhap_npp.sanpham_fk ,spnhap_npp.soluong - isnull(sptrave_tt.soluong,0) as soluong ,'2.Luong Hang Thuc Te' as 'Type'"
				+ "          from"
				+ "			(		select b.npp_fk,b.kbh_fk,b.kho_fk,c.pk_seq as sanpham_fk,sum(cast(soluong as int)) as soluong"
				+ "					from nhaphang_sp a inner join nhaphang b on a.nhaphang_fk = b.pk_seq"
				+ "					inner join sanpham c on c.ma = a.sanpham_fk";
		if (obj.getdiscount().equals("1"))// hang van chuyen
			query += " where a.gianet <> 0 and b.npp_fk ='" + obj.getnppId()
					+ "' and b.ngaynhan >='" + obj.gettungay()
					+ "' and b.ngaynhan <='" + obj.getdenngay() + "'";
		else
			query += " where a.gianet <> 0 and b.trangthai ='1' and b.ngaynhan >='"
					+ obj.gettungay()
					+ "' and b.ngaynhan <='"
					+ obj.getdenngay()
					+ "' and b.npp_fk ='"
					+ obj.getnppId()
					+ "'";
		query += " group by b.npp_fk,b.kbh_fk,c.pk_seq,b.kho_fk"
				+ "   	   ) spnhap_npp"
				+ "	      left join "
				+ "		  ("
				+ "			 select dth.npp_fk,dth.kbh_fk,thsp.sanpham_fk ,dth.kho_fk ,sum(thsp.soluong) as soluong "
				+ "			 from dontrahang_sp	 thsp inner join  dontrahang dth on  dth.pk_seq = thsp.dontrahang_fk"
				+ "            where dth.trangthai ='1' and dth.ngaytra >='"
				+ obj.gettungay()
				+ "' and dth.ngaytra <='"
				+ obj.gettungay()
				+ "' and  dth.npp_fk ='"
				+ obj.getnppId()
				+ "'"
				+ "			 group by dth.npp_fk,dth.kbh_fk,thsp.sanpham_fk,dth.kho_fk"
				+ "		  )sptrave_tt on sptrave_tt.npp_fk = spnhap_npp.npp_fk and sptrave_tt.kbh_fk = spnhap_npp.kbh_fk and sptrave_tt.sanpham_fk = spnhap_npp.sanpham_fk and sptrave_tt.kho_fk = spnhap_npp.kho_fk"
				+ "	   )"
				+ " union"
				+ "		(	select nppban.npp_fk, nppban.kbh_fk, nppban.kho_fk ,nppban.sanpham_fk, nppban.soluong - isnull(khachhang_tra.soluong,0) as soluong ,'5.Doanh So Ban' as'Type'"
				+ "			from"
				+ "			   ( select dh.npp_fk,dh.kbh_fk,dhsp.sanpham_fk,dhsp.kho_fk,sum(soluong) as soluong "
				+ "				 from donhang_sanpham dhsp "
				+ "						inner join donhang dh on dh.pk_seq = dhsp.donhang_fk"
				+ "				 where dh.trangthai ='1' and dh.ngaynhap >= '"
				+ obj.gettungay()
				+ "' and dh.ngaynhap <= '"
				+ obj.getdenngay()
				+ "' and dh.npp_fk ='"
				+ obj.getnppId()
				+ "'"
				+ "						and pk_seq not in ("
				+ "											select isnull(tra.donhang_fk,0) "
				+ "											from donhangtrave tra "
				+ "											where tra.trangthai ='1' and tra.npp_fk = dh.npp_fk and tra.ngaynhap >='"
				+ obj.gettungay()
				+ "' and tra.ngaynhap <= '"
				+ obj.getdenngay()
				+ "' and tra.npp_fk ='"
				+ obj.getnppId()
				+ "')"
				+ "											group by  dh.npp_fk,dh.kbh_fk,dhsp.sanpham_fk,dhsp.kho_fk  "
				+ "										  ) nppban"
				+ "			   							  left join"
				+ "										  (	"
				+ "											select dontra.npp_fk, dontra.kbh_fk,dontra_sp.sanpham_fk,dontra_sp.kho_fk,sum(soluong) as soluong"
				+ "											from  donhangtrave_sanpham dontra_sp"
				+ "											inner join donhangtrave dontra  on dontra.pk_seq = dontra_sp.donhangtrave_fk"
				+ "											where dontra.trangthai ='3' and dontra.ngaynhap >= '"
				+ obj.gettungay()
				+ "' and dontra.ngaynhap <= '"
				+ obj.getdenngay()
				+ "' and dontra.npp_fk ='"
				+ obj.getnppId()
				+ "'"
				+ "											group by dontra.npp_fk, dontra.kbh_fk,dontra_sp.sanpham_fk,dontra_sp.kho_fk"
				+ "										  )khachhang_tra on khachhang_tra.npp_fk =nppban.npp_fk and khachhang_tra.kbh_fk =nppban.kbh_fk and khachhang_tra.sanpham_fk =nppban.sanpham_fk and khachhang_tra.kho_fk = nppban.kho_fk"
				+ "		)"
				+ "		union"
				+ "("
				+ " select nhap_km.npp_fk,nhap_km.kbh_fk,nhap_km.kho_fk,c.pk_seq as sanpham_fk,sum(convert(numeric(18,0),nhap_sp_km.soluong)) as soluong ,'3.KM Nhap' as 'Type' "
				+ " from nhaphang_sp nhap_sp_km   "
				+ "		inner join nhaphang nhap_km on nhap_sp_km.nhaphang_fk = nhap_km.pk_seq "
				+ "		inner join sanpham c on c.ma = nhap_sp_km.sanpham_fk  "
				+ " where nhap_sp_km.gianet = 0 and nhap_km.ngaynhan >='"
				+ obj.gettungay()
				+ "' and nhap_km.ngaynhan <= '"
				+ obj.getdenngay()
				+ "' and nhap_km.npp_fk ='"
				+ obj.getnppId()
				+ "' AND nhap_sp_km.SOLUONG> 0 AND nhap_sp_km.gianet= 0 "
				+ " group by nhap_km.npp_fk,nhap_km.kbh_fk,c.pk_seq,nhap_km.kho_fk "
				+ ") "
				+
				// "		( 	select khuyenmai_nhap.npp_fk,khuyenmai_nhap.kbh_fk ,nhap_km.kho_fk,khuyenmai_nhap.sanpham_fk,khuyenmai_nhap.soluong,'3.Promotion in' as 'Type' "
				// +
				// "			from ( " +
				// "					(select nhap_km.npp_fk,nhap_km.kbh_fk,c.pk_seq as sanpham_fk,nhap_km.kho_fk,sum(convert(numeric(18,0),nhap_sp_km.soluong)) as soluong"
				// +
				// "					 from nhaphang_sp nhap_sp_km " +
				// "							inner join nhaphang nhap_km on nhap_sp_km.nhaphang_fk = nhap_km.pk_seq"
				// +
				// "							inner join sanpham c on c.ma = nhap_sp_km.sanpham_fk"
				// ;

				// "							inner join ctkhuyenmai ctkm on ctkm.pk_seq = cast(nhap_sp_km.scheme as int)"
				// ;
				// if(obj.getdiscount().equals("1")){//hang dang van chuyen
				// query
				// +="		 where nhap_sp_km.gianet = 0 and nhap_sp_km.soluong>0 and nhap_km.ngaynhan >='"
				// + obj.gettungay() +"' and nhap_km.ngaynhan <= '" +
				// obj.getdenngay() + "' and nhap_km.npp_fk ='"+ obj.getnppId()
				// +"'";
				// }
				// else{
				// query
				// +="		 where nhap_sp_km.gianet = 0 and nhap_sp_km.soluong>0 and nhap_km.trangthai ='1'  and nhap_km.ngaynhan >='"
				// + obj.gettungay() +"' and nhap_km.ngaynhan <='" +
				// obj.getdenngay() + "' and  nhap_km.npp_fk ='"+ obj.getnppId()
				// +"'";
				// }
				//
				// query
				// +="					 group by nhap_km.npp_fk,nhap_km.kbh_fk,c.pk_seq,nhap_km.kho_fk"
				// +
				// " 				   )" +
				// "				 union" +
				// "				(" +
				// "					select nhap_km.npp_fk,nhap_km.kbh_fk,c.pk_seq as sanpham_fk,nhap_km.kho_fk,sum(cast(nhap_sp_km.soluong as int)) as soluong"
				// +
				// "					from nhaphang_sp nhap_sp_km " +
				// "						inner join nhaphang nhap_km on nhap_sp_km.nhaphang_fk = nhap_km.pk_seq"
				// +
				// "							inner join sanpham c on c.ma = nhap_sp_km.sanpham_fk"
				// +
				// "	    			where nhap_sp_km.gianet = 0" +
				// "						   and cast(nhap_sp_km.scheme as int) not in (select isnull(pk_seq,0) from ctkhuyenmai)"
				// +
				// "							and nhap_km.trangthai ='1' and nhap_km.npp_fk ='" +
				// obj.getnppId() + "' and nhap_km.ngaynhan >= '" +
				// obj.gettungay() +"' and nhap_km.ngaynhan <= '" +
				// obj.getdenngay() +"'" +
				// "					group by nhap_km.npp_fk,nhap_km.kbh_fk,c.pk_seq,nhap_km.kho_fk"
				// +
				// "				 )" +

				// "	 		) khuyenmai_nhap" +
				// "		)" +
				"		union"
				+ "		(  	select donh.npp_fk,donh.kbh_fk,km.kho_fk,spkm.pk_seq as sanpham_fk,sum(trakm_khachhang.soluong) as soluong,'4.KM Xuat' as 'Type'"
				+ "			from donhang_ctkm_trakm trakm_khachhang"
				+ "				inner join ("
				+ "							select * "
				+ "							from donhang "
				+ "							where trangthai ='1' and npp_fk ='"
				+ obj.getnppId()
				+ "' and ngaynhap >='"
				+ obj.gettungay()
				+ "' and ngaynhap <= '"
				+ obj.getdenngay()
				+ "' "
				+ "							) donh on donh.pk_seq = trakm_khachhang.donhangid"
				+ "				inner join sanpham spkm on spkm.ma = trakm_khachhang.spma"
				+ "				inner join ctkhuyenmai km on km.pk_seq = trakm_khachhang.ctkmid"
				+ "				where len(trakm_khachhang.spma) >0 and  donh.pk_seq not in (select isnull(donhang_fk,0) from donhangtrave where trangthai ='1')"
				+ "				group by donh.npp_fk,donh.kbh_fk,km.kho_fk,spkm.pk_seq"
				+ "		)"
				+ "		union"
				+ "		("
				+
				// "			SELECT DCTK.NPP_FK, DCTK.KBH_FK, DCTK.KHO_FK, DCTK_SP.SANPHAM_FK, SUM(cast(DCTK_SP.DIEUCHINH as int)) AS SOLUONG,'5.Adjust inventory' as 'Type'"
				// +
				// "			FROM DIEUCHINHTONKHO DCTK" +
				// "				INNER JOIN DIEUCHINHTONKHO_SP DCTK_SP ON DCTK_SP.DIEUCHINHTONKHO_FK = DCTK.PK_SEQ"
				// +
				// "			WHERE DCTK.TRANGTHAI = '1' AND DCTK.NPP_FK = '" +
				// obj.getnppId() + "' AND DCTK.NGAYDC >= '"+ obj.gettungay()
				// +"' AND DCTK.NGAYDC <='"+ obj.getdenngay()+"'" +

				"SELECT NPP.PK_SEQ AS NPP_FK, KBH.PK_SEQ AS KBH_FK, NPP_KHO.KHO_FK AS KHO_FK,SP.PK_SEQ AS SANPHAM_FK ,"
				+ "SUM( cast( ISNULL(DCTK_SP.DIEUCHINH,0) as int) ) AS SOLUONG, '6.Dieu Chinh' as 'Type' "
				+ "				 FROM NHAPHANPHOI NPP "
				+ "				 INNER JOIN NHAPP_KBH NPP_KBH ON NPP_KBH.NPP_FK = NPP.PK_SEQ "
				+ "				 INNER JOIN KENHBANHANG KBH ON NPP_KBH.KBH_FK = KBH.PK_SEQ "
				+ "				  INNER JOIN NHAPP_KHO NPP_KHO ON NPP_KHO.NPP_FK = NPP.PK_SEQ AND NPP_KHO.KBH_FK = KBH.PK_SEQ "
				+ "				 INNER JOIN SANPHAM SP ON SP.PK_SEQ = NPP_KHO.SANPHAM_FK "
				+ "				 LEFT JOIN DIEUCHINHTONKHO DCTK ON DCTK.NPP_FK = NPP.PK_SEQ AND DCTK.KHO_FK = NPP_KHO.KHO_FK AND DCTK.KBH_FK = KBH.PK_SEQ  and DCTK.TRANGTHAI = '1' AND DCTK.NGAYDC >= '"
				+ obj.gettungay()
				+ "' AND DCTK.NGAYDC <='"
				+ obj.getdenngay()
				+ "'"
				+ "				 LEFT JOIN DIEUCHINHTONKHO_SP DCTK_SP ON DCTK_SP.DIEUCHINHTONKHO_FK = DCTK.PK_SEQ AND DCTK_SP.SANPHAM_FK = SP.PK_SEQ "
				+ "				WHERE NPP_KHO.NPP_FK = '"
				+ obj.getnppId()
				+ "'"
				+ "							GROUP BY NPP.PK_SEQ , KBH.PK_SEQ , NPP_KHO.KHO_FK ,SP.PK_SEQ  "
				+

				"		)"
				+

				"	) tong"
				+ "	INNER JOIN (select * from sanpham) SP ON SP.PK_SEQ = tong.SANPHAM_FK "
				+ "	LEFT JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK "
				+ "   INNER JOIN KHO KHO ON KHO.PK_SEQ = 	tong.KHO_FK "
				+ "   LEFT JOIN NHANHANG NHAN ON NHAN.PK_SEQ = SP.NHANHANG_FK  "
				+ "   LEFT JOIN CHUNGLOAI CHUNGLOAI ON CHUNGLOAI.PK_SEQ = SP.CHUNGLOAI_FK  "
				+ "   INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = tong.KBH_FK  "
				+ "   INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = tong.NPP_FK "
				+ "	LEFT JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK "
				+ " 	LEFT JOIN VUNG VUNG ON VUNG.PK_SEQ = KV.VUNG_FK "
				+ "	left join quycach qc on qc.dvdl1_fk = sp.dvdl_fk and qc.sanpham_fk = sp.pk_seq "
				+ "	left join ( "
				+ "		  select distinct bgm.kenh_fk as kbh_fk,bgm_sp.sanpham_fk,bgmnpp.npp_fk,bgm_sp.giamuanpp as GIAMUANPP from banggiamuanpp_npp bgmnpp "
				+ "		  inner join banggiamuanpp bgm on bgm.pk_seq = bgmnpp.banggiamuanpp_fk "
				+ "		  inner join bgmuanpp_sanpham bgm_sp on bgm_sp.bgmuanpp_fk = bgm.pk_seq "
				+ "	 ) nppk on nppk.npp_fk = tong.npp_fk and nppk.sanpham_fk = tong.sanpham_fk and nppk.kbh_fk = tong.kbh_fk ";
		query += " order by substring(TONG.Type,1,2) asc ";

		return query;

	}

}
