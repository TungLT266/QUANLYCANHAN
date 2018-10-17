package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.util.Utility;
import geso.dms.distributor.beans.reports.imp.Reports;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.Channel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

/***
 * Servlet implementation class Disproforcustomer
 */
public class Disproforcustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * String userId; String userTen; String NhappId=""; String tungay="";
	 * String denngay=""; boolean bfasle=true; Utility util =new Utility();
	 */
	public Disproforcustomer() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Reports report = new Reports();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		String userTen = (String) session.getAttribute("userTen");
		report.setUser(userId);
		report.setTuNgay("");
		report.setDenNgay("");
		report.setNppId("");
		report.setcttbid("");
		report.setLoi("");
		report.CreateRsNpp();
		report.CreateRsCTTB();
		report.setUserTen(userTen);
		session.setAttribute("report", report);
		session.setAttribute("util", util);
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);

		String nextJSP = "/TraphacoHYERP/pages/Center/R_Disproforcustomer.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		HttpSession session = request.getSession();
		Reports obj = new Reports();
		Utility util = new Utility();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		userId = (String) session.getAttribute("userId");
		obj.setUser(userId);
		
		String[] cttbid = request.getParameterValues("cttbid");
		//lay id cttb da chon ngoai giao dien
		String str3 = "";
		if(cttbid != null)
		{
			for(int i = 0; i < cttbid.length; i++)
				str3 += cttbid[i] + ",";
			if(str3.length() > 0)
				str3 = str3.substring(0, str3.length() - 1);
		}
		obj.setcttbid(str3);
		
		boolean bfasle = true;
		try {

			userTen = (String) session.getAttribute("userTen");
			if (userTen == null)
				userTen = "";
			obj.setUserTen(userTen);
			userId = (String) session.getAttribute("userId");
			if (userId == null)
				userId = "";
			obj.setUser(userId);

			String tungay = util.antiSQLInspection(request
					.getParameter("tungay"));
			if (tungay == null)
				tungay = "";
			obj.setTuNgay(tungay);

			String denngay = util.antiSQLInspection(request
					.getParameter("denngay"));
			if (denngay == null)
				denngay = "";
			obj.setDenNgay(denngay);

			String NhappId = util.antiSQLInspection(request.getParameter("nhappid"));
			if (NhappId == null)
				NhappId = "";

			obj.setNppId(NhappId);

			session.setAttribute("userTen", userTen);
			session.setAttribute("userId", userId);

			System.out.print(userId + " d s " + userTen);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=DISPLAYPROGRAMAPPLYFORCUSTOMER.xls");

			CreatePivotTable(out, response, request, obj, bfasle,str3);
		} catch (Exception ex) 
		{
			
			ex.printStackTrace();

			// say sorry
			response.setContentType("text/html");
			PrintWriter writer = new PrintWriter(out);

			writer.println("<html>");
			writer.println("<head>");
			writer.println("<title>sorry</title>");
			writer.println("</head>");
			writer.println("<body>");
			writer.println("<h1>Xin loi, khong the tao pivot table...</h1>");
			ex.printStackTrace(writer);
			writer.println("</body>");
			writer.println("</html>");
			writer.close();
		}
	}

	private void CreatePivotTable(OutputStream out,
			HttpServletResponse response, HttpServletRequest request,
			Reports obj, boolean bfasle,String strctkbid) throws IOException { // khoi tao de
																// viet
																// pivottable
																// buoc 1

		Workbook workbook = new Workbook();
		// Buoc2 tao khung
		// ham tao khu du lieu

		CreateStaticHeader(workbook, obj.getTuNgay(), obj.getDenNgay(),
				obj.getTenUser());
		// Buoc3
		// day du lieu vao
		bfasle =CreateStaticData(workbook, obj, bfasle,strctkbid);
		if (bfasle == false) {
			String loi = "chua co bao cao trong thoi gian nay, vui long hcon lai thoi gian xem bao cao";
			HttpSession session = request.getSession();
			obj.CreateRsNpp();
			obj.CreateRsCTTB();
			session.setAttribute("report", obj);
			String nextJSP = "/TraphacoHYERP/pages/Center/R_Disproforcustomer.jsp";
			response.sendRedirect(nextJSP);
		} else {
			// Saving the Excel file
			workbook.save(out);
		}
		workbook.save(out);
	}

	private void CreateStaticHeader(Workbook workbook, String dateFrom,
			String dateTo, String UserName) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		// HttpSession session = request.getSession();
		// userId = (String)session.getAttribute("userId");
		// userTen = (String)session.getAttribute("userTen");
		Cells cells = worksheet.getCells();

		Style style;
		// cells.setColumnWidth(0, 200.0f);
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("BÁO CÁO TRƯNG BÀY");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(16);// size chu
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);

		// cell = cells.getCell("B2");
		// getCellStyle(workbook,"B2",Color.BLUE,true,10);

		// cell.setValue("From " + dateFrom + "      To " + dateTo);
		int i=3;
		if(!dateFrom.equals("")){
			cell = cells.getCell("A3");
			getCellStyle(workbook, "A3", Color.NAVY, true, 9);
			cell.setValue("Từ ngày: " + dateFrom);
			
		}
		if(!dateTo .equals("")){
			cell = cells.getCell("B3");
			getCellStyle(workbook, "B3", Color.NAVY, true, 9);
			cell.setValue("Đến ngày: " + dateTo);
			
		}
		
		i=4;
		cell = cells.getCell("A"+i);
		getCellStyle(workbook, "A"+i, Color.NAVY, true, 9);
		cell.setValue("Ngày tạo báo cáo: " + this.getDateTime());
		i++;
		cell = cells.getCell("A"+i);
		getCellStyle(workbook, "A"+i, Color.NAVY, true, 9);
		cell.setValue("Được tạo bởi:  " + UserName);
		// tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
		// cell = cells.getCell("AA1"); cell.setValue("Distributor");

		cell = cells.getCell("AA1");
		cell.setValue("Chi nhánh");
		cell = cells.getCell("AB1");
		cell.setValue("Vùng");

		
		cell = cells.getCell("AC1");
		cell.setValue("Sitecode");

		cell = cells.getCell("AD1");
		cell.setValue("Nhà phân phối");
		cell = cells.getCell("AE1");
		cell.setValue("Mã chương trình");
		cell = cells.getCell("AF1");
		cell.setValue("Diễn giải");
		cell = cells.getCell("AG1");
		cell.setValue("Mã KH SALESUP");
		cell = cells.getCell("AH1");
		cell.setValue("Mã KH SMART");
		cell = cells.getCell("AI1");
		cell.setValue("Tên khách hàng");
		cell = cells.getCell("AJ1");
		cell.setValue("Địa chỉ");
		
		cell = cells.getCell("AK1");
		cell.setValue("Tỉnh thành");
		cell = cells.getCell("AL1");
		cell.setValue("Quận huyện");
		
		cell = cells.getCell("AM1");
		cell.setValue("Điện thoại");

		cell = cells.getCell("AN1");
		cell.setValue("Lần trả");
		cell = cells.getCell("AO1");
		cell.setValue("Đăng ký");
		cell = cells.getCell("AP1");
		cell.setValue("Đề nghị");
		cell = cells.getCell("AQ1");
		cell.setValue("Duyệt");
		worksheet.setName("BAO CAO TRUNG BAY(TT)");
	}

	private boolean CreateStaticData(Workbook workbook, Reports obj, boolean bfasle,String strctkbid) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();

		
		Utility Ult = new Utility();
/*
		String sql = "select distinct kv.ten as Area ,v.ten as Region ,tt.ten as tinhthanh ,qh.ten as quanhuyen ,npp.sitecode,npp.ten as Distributor,cb.scheme as  "
				+ " programid,cb.diengiai,kh.pk_seq as CustomerKey, substring(kh.smartid,CHARINDEX('_',kh.smartid)+1,10)+npp.sitecode  as smartid, "
				+ " kh.ten as CustomerName,kh.diachi as Address,kh.dienthoai, isnull(dntb.lanthanhtoan,0) as PayTime,isnull(tbkh.dangky,0) as Allocation "
				+ "  ,isnull(dntbkh.xuatdenghi,0) as Request_pay,case when dntbkh.xuatduyet is null then 0 else dntbkh.xuatduyet end  as Approval from cttrungbay "
				+ " cb inner join phanbotrungbay pb on pb.cttb_fk = cb.pk_seq  "
				+ " inner join dangkytrungbay dktb on dktb.cttrungbay_fk = cb.pk_seq and dktb.npp_fk = pb.npp_fk "
				+ " inner join dktrungbay_khachhang tbkh on tbkh.dktrungbay_fk = dktb.pk_seq  "
				+ " left join denghitratrungbay dntb on dntb.cttrungbay_fk = cb.pk_seq and dntb.npp_fk = dktb.npp_fk "
				+ " left join DENGHITRATB_KHACHHANG dntbkh on dntbkh.denghitratb_fk = dntb.pk_seq  "
				+ " and dntbkh.khachhang_fk =tbkh.khachhang_fk and isnull(tbkh.ddkd_fk,0) = isnull(dntbkh.ddkd_fk,0) "
				+ " inner join khachhang kh on kh.pk_seq = tbkh.khachhang_fk "
				+ " inner join nhaphanphoi npp on npp.pk_seq = kh.npp_fk  "
				+ " inner join tinhthanh tt on tt.pk_Seq=npp.tinhthanh_fk "
				+ " inner join quanhuyen qh on qh.pk_Seq=npp.quanhuyen_fk "
				+ " left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk  "
				+ " left join vung v on v.pk_seq = kv.vung_fk "
				*/
		
		String sql="SELECT  KV.TEN AS AREA ,V.TEN AS REGION ,TT.TEN AS TINHTHANH ,QH.TEN AS QUANHUYEN ,NPP.SITECODE,  "+
			" NPP.TEN AS DISTRIBUTOR,CB.SCHEME AS   PROGRAMID,CB.DIENGIAI,KH.PK_SEQ AS CUSTOMERKEY,   "+
			" SUBSTRING(KH.SMARTID,CHARINDEX('_',KH.SMARTID)+1,10)+NPP.SITECODE  AS SMARTID,  KH.TEN AS CUSTOMERNAME,KH.DIACHI AS ADDRESS  "+
			" ,KH.DIENTHOAI, ISNULL(DNTB.LANTHANHTOAN,1) AS PAYTIME,ISNULL(TBKH.DANGKY,0) AS ALLOCATION   ,  "+
			" ISNULL(DNTBKH.XUATDENGHI,0) AS REQUEST_PAY,ISNULL(DNTBKH.XUATDUYET,0) AS APPROVAL   "+
			" FROM CTTRUNGBAY  CB    "+
			" INNER JOIN PHANBOTRUNGBAY PB ON PB.CTTB_FK = CB.PK_SEQ     "+
			" INNER JOIN DANGKYTRUNGBAY DKTB ON DKTB.CTTRUNGBAY_FK = CB.PK_SEQ AND DKTB.NPP_FK = PB.NPP_FK    "+
			" INNER JOIN DKTRUNGBAY_KHACHHANG TBKH ON TBKH.DKTRUNGBAY_FK = DKTB.PK_SEQ    "+ 
			" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ =DKTB.NPP_FK    "+
		
			" LEFT JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK "+   
			" LEFT JOIN VUNG V ON V.PK_SEQ = KV.VUNG_FK  "+
			" LEFT JOIN DENGHITRATRUNGBAY DNTB ON DNTB.CTTRUNGBAY_FK = DKTB.CTTRUNGBAY_FK AND DNTB.NPP_FK = DKTB.NPP_FK "+  
			" LEFT JOIN DENGHITRATB_KHACHHANG DNTBKH ON DNTBKH.DENGHITRATB_FK = DNTB.PK_SEQ "+   
			" AND DNTBKH.KHACHHANG_FK =TBKH.KHACHHANG_FK "+
			" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = TBKH.KHACHHANG_FK " +
			" LEFT JOIN TINHTHANH TT ON TT.PK_SEQ=KH.TINHTHANH_FK    "+
			" LEFT JOIN QUANHUYEN QH ON QH.PK_SEQ=KH.QUANHUYEN_FK    "+
			 " where 1=1  and DKTB.trangthai= '1'";
				if(!obj.getTuNgay().equals("")){
					sql=sql+" and cb.ngaytrungbay>='"+ obj.getTuNgay()+"'";
				}
				if(!obj.getDenNgay().equals("")){
					
					sql=sql+ " and cb.ngaytrungbay<='"+ obj.getDenNgay()+"'";
				} 
				
				sql=sql+ " and pb.npp_fk in " + Ult.quyen_npp(obj.getUser());
				
				if(!strctkbid.equals("")){
					sql=sql+ " and cb.pk_seq in ("+strctkbid+")";
				}
		
		if (obj.getNppId() != "") {
			sql = sql + " and npp.pk_seq=" + obj.getNppId();
		}
		sql=sql +" order by DNTB.LANTHANHTOAN" ;
		System.out.println("Sql Get Bao Cao Trung Bay Trung tam  :" + sql);
		ResultSet rs = db.get(sql);

		//if(strctkbid.equals("")){
		///	strctkbid="select pk_seq from cttrungbay";
	//	}
	//	String[] Param = {obj.getTuNgay(), obj.getDenNgay(), obj.getUser(),obj.getNppId(),strctkbid};
		//  ResultSet rs  = db.getRsByPro("getRsDisPromotion_report", Param);
		
		
		int i = 2;
		if (rs != null) {
			try {// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 20.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);
				cells.setColumnWidth(11, 15.0f);
				cells.setColumnWidth(12, 15.0f);
				cells.setColumnWidth(13, 15.0f);
				cells.setColumnWidth(14, 15.0f);
				cells.setColumnWidth(15, 15.0f);
				cells.setColumnWidth(15, 15.0f);
				// set do rong cho dong
				// for(int j = 12; j <= 11; j++)
				// cells.setRowHeight(j, 14.0f);

				Cell cell = null;
				while (rs.next())// lap den cuoi bang du lieu
				{
					// lay tu co so du lieu, gan bien
					String Area = rs.getString("Area");
					String Region = rs.getString("Region");
					String Distributor = rs.getString("Distributor");
					String Program_ID = rs.getString("programid");
					String Program_Des = rs.getString("diengiai");

					String CustomerKey = rs.getString("CustomerKey");
					String CustomerName = rs.getString("CustomerName");
					String Address = rs.getString("Address");
					String PayTime = rs.getString("PayTime");
					String Allocation = rs.getString("Allocation");
					String Request_pay = rs.getString("Request_pay");
					String Approval_pay = rs.getString("Approval");
					String Cus_Smartid = rs.getString("smartid");

					String province = rs.getString("tinhthanh");
					String town = rs.getString("quanhuyen");
					String sitecode = rs.getString("sitecode");
					String tel = rs.getString("dienthoai");

					cell = cells.getCell("AA" + Integer.toString(i));cell.setValue(Region); //0
					
					cell = cells.getCell("AB" + Integer.toString(i));cell.setValue(Area); //1
					

							
					cell = cells.getCell("AC" + Integer.toString(i));cell.setValue(sitecode);//4
					

					cell = cells.getCell("AD" + Integer.toString(i));cell.setValue(Distributor);//5
					
					cell = cells.getCell("AE" + Integer.toString(i));cell.setValue(Program_ID);//6
					
					cell = cells.getCell("AF" + Integer.toString(i));cell.setValue(Program_Des);//7
					
					cell = cells.getCell("AG" + Integer.toString(i));	cell.setValue(CustomerKey);//8
				

					cell = cells.getCell("AH" + Integer.toString(i));cell.setValue(Cus_Smartid);//9
					

					cell = cells.getCell("AI" + Integer.toString(i));cell.setValue(CustomerName);//10
					
					cell = cells.getCell("AJ" + Integer.toString(i));cell.setValue(Address);//11
					
					cell = cells.getCell("AK" + Integer.toString(i));cell.setValue(province);//2
					
					cell = cells.getCell("AL" + Integer.toString(i));cell.setValue(town); //3


					cell = cells.getCell("AM" + Integer.toString(i));cell.setValue(tel);//12
					
					cell = cells.getCell("AN" + Integer.toString(i));	cell.setValue(PayTime);//13
				
					cell = cells.getCell("AO" + Integer.toString(i));cell.setValue(Float.parseFloat(Allocation)); //14
					
					cell = cells.getCell("AP" + Integer.toString(i));cell.setValue(Float.parseFloat(Request_pay));//15
					
					cell = cells.getCell("AQ" + Integer.toString(i));cell.setValue(Float.parseFloat(Approval_pay)); //16
					
					
					i++;
				}
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				
				e.printStackTrace();
				bfasle = false;
				return false;
			}
		} else {
			bfasle = false;
			return false;
		}
		System.out.println("Oke Da TOi day :" +i);
		if(i==2){
			return false;
		}else{
					// xong buoc dua du lieu vao exel
					
					// create pivot
					getAn(workbook, 70);
					PivotTables pivotTables = worksheet.getPivotTables();
					
					// Adding a PivotTable to the worksheet
					String pos = Integer.toString(i - 1); // pos la dong cuoi cung ,A12 la
														// toa do dau cua banng du lieu,
														// Q pos la dong cuoi
					int index = pivotTables.add("=AA1:AQ" + pos, "A12", "PivotTableDemo");
					System.out.println("index:" + index);
					// Accessing the instance of the newly added PivotTable
					PivotTable pivotTable = pivotTables.get(index);// truyen index
					
					pivotTable.setRowGrand(false);
					pivotTable.setColumnGrand(false);
					pivotTable.setAutoFormat(true);
					// Setting the PivotTable autoformat type.
					// pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 0);
						pivotTable.getRowFields().get(0).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
						pivotTable.getRowFields().get(1).setAutoSubtotals(true);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 2);
						pivotTable.getRowFields().get(2).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
						pivotTable.getRowFields().get(3).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 4);
						pivotTable.getRowFields().get(4).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 5);
						pivotTable.getRowFields().get(5).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 6);
						pivotTable.getRowFields().get(6).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 7);
						pivotTable.getRowFields().get(7).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 8);
						pivotTable.getRowFields().get(8).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 9);
						pivotTable.getRowFields().get(9).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 10);
						pivotTable.getRowFields().get(10).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 11);
						pivotTable.getRowFields().get(11).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 12);
						pivotTable.getRowFields().get(12).setAutoSubtotals(false);
						pivotTable.addFieldToArea(PivotFieldType.ROW, 13);
						
					  
					    
						pivotTable.addFieldToArea(PivotFieldType.DATA, 14);
						pivotTable.getDataFields().get(0).setDisplayName("Đăng ký");
					pivotTable.addFieldToArea(PivotFieldType.DATA, 15);
					pivotTable.getDataFields().get(1).setDisplayName("Đề nghị");
					pivotTable.addFieldToArea(PivotFieldType.DATA, 16);
					pivotTable.getDataFields().get(2).setDisplayName("Duyệt");
					
					pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
		
		}
		return true;
	}

	private void getCellStyle(Workbook workbook, String a, Color mau,
			Boolean dam, int size) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a);
		style = cell.getStyle();
		Font font1 = new Font();
		font1.setColor(mau);
		font1.setBold(dam);
		font1.setSize(size);
		style.setFont(font1);
		cell.setStyle(style);
	}

	private void getAn(Workbook workbook, int i) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		for (int j = 24; j <= i; j++) {
			cells.hideColumn(j);
		}

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
