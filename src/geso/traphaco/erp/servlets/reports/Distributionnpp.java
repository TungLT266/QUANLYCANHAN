package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class Distributionnpp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Distributionnpp() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		//Ireportnpp obj = new Reports();
		IStockintransit obj = new Stockintransit();
		obj.settungay("");
		obj.setdenngay("");
		obj.init();
		obj.setsanphamId("");
		session.setAttribute("obj", obj);
		session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);
		String nextJSP = "/TraphacoHYERP/pages/Center/Distributionnpp.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		HttpSession session = request.getSession();
		try {
			
			
		
			obj.setuserTen((String) session.getAttribute("userTen"));
			obj.settungay(util.antiSQLInspection(request.getParameter("tungay")));
			obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay")));
			obj.setsanphamId(util.antiSQLInspection(request.getParameter("skuid")));
			obj.setuserId((String) session.getAttribute("userId"));
			
			obj.setDdkd(request.getParameter("ddkdId")!=null? 
					request.getParameter("ddkdId"):"");
			
		
			
			
			String[] skutest = request.getParameterValues("skutest");
			String[] valuechecked = request.getParameterValues("valuechecked");
			String whereSKU = "";
			if(skutest!=null)	
			for (int i = 0; i < skutest.length; i++) {
				if (valuechecked[i].equals("1")) {
					whereSKU += "'" + skutest[i] + "',";

				}
			}
			boolean bfasle = false;
			session.setAttribute("checkedSKU", skutest);
			if (whereSKU == "") {
				bfasle = false;
			} else {
				whereSKU = " (" + whereSKU.substring(0, whereSKU.length() - 1)
						+ ")";
				bfasle = true;
			}
			obj.setsanphamId(whereSKU);
			
			obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nhanhangId"))!=null?
			util.antiSQLInspection(request.getParameter("nhanhangId")):"");
			obj.setchungloaiId(util.antiSQLInspection(request.getParameter("chungloaiId"))!=null?
			util.antiSQLInspection(request.getParameter("chungloaiId")):"");
			obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))!=null?
			util.antiSQLInspection(request.getParameter("dvkdId")):"");
			obj.SetNhoSPId(util.antiSQLInspection(request.getParameter("nhomspid"))!=null?
					util.antiSQLInspection(request.getParameter("nhomspid")):"");
			
			 obj.settype(request.getParameter("typeid"));
			
			System.out.println("Type :"+obj.gettype());	
				
		 	String action = request.getParameter("action");
			System.out.println("Action  :"+action);	
			if(action.equals("create")){
			
				/*	response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition",
							"attachment; filename=DoPhuCuaSanPham.xls");
*/
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=DistributionReport(NPP)"+this.getPiVotName()+".xlsm");

				CreatePivotTable(out, response, request,obj,bfasle,whereSKU);
			}else{
				obj.init();
			
				session.setAttribute("obj", obj);
				
				session.setAttribute("userTen", obj.getuserTen());
				String nextJSP = "/TraphacoHYERP/pages/Center/Distributionnpp.jsp";
				response.sendRedirect(nextJSP);
			}
			
			
			
		} catch (Exception ex) {
			obj.init();
			obj.setMsg("Khong The Tao Duoc Bao Cao. -- "+ ex.toString());
			session.setAttribute("obj", obj);
			
			session.setAttribute("userTen", obj.getuserTen());
			String nextJSP = "/TraphacoHYERP/pages/Center/Distributionnpp.jsp";
			response.sendRedirect(nextJSP);
		}
		
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
			HttpServletResponse response, HttpServletRequest request,IStockintransit obj,boolean bfasle,String whereSKU)
			throws IOException { // khoi tao de viet pivottable
									// buoc 1
		//khoi tao de viet pivottable
		//buoc 1
		//String strfstream="D:\\Best Stable\\SalesUp\\WebContent\\pages\\Templates\\Distribution(NPP).xlsm";
		String strfstream = getServletContext().getInitParameter("path") + "\\Distribution(NPP).xlsm";		

		FileInputStream fstream = new FileInputStream(strfstream);	
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook,obj.gettungay(),obj.getdenngay(),obj.getuserTen());
	     //Buoc3 
	     // day du lieu vao
	     bfasle=  CreateStaticData(workbook,obj);
	     HttpSession session = request.getSession();
	     
		  obj.init();
		 session.setAttribute("obj", obj);
	     
	     if(bfasle==false){
	    	
	    	
	    	
	    	session.setAttribute("checkedSKU","");
	
	    	session.setAttribute("userId", obj.getuserId());
			session.setAttribute("userTen", obj.getuserTen());	 		
	    	obj.init();
	 		session.setAttribute("obj",obj);
	 		String nextJSP = "/TraphacoHYERP/pages/Center/Distributionnpp.jsp";
	 		response.sendRedirect(nextJSP);
	     }else{
	    	 //Saving the Excel file
	    	 workbook.save(out);
			    fstream.close();
	     }
	}

	private void CreateStaticHeader(Workbook workbook, String dateFrom,
			String dateTo, String UserName) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();

		Style style;
		// cells.setColumnWidth(0, 200.0f);
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("ĐỘ PHỦ SẢN PHẨM");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(16);// size chu
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);
		cell = cells.getCell("A2");
		getCellStyle(workbook, "A2", Color.NAVY, true, 10);
		cell.setValue("Từ Ngày " + dateFrom + "      Đến Ngày   " + dateTo);
		cell = cells.getCell("A3");
		getCellStyle(workbook, "A3", Color.NAVY, true, 10);
		cell.setValue("Ngày Tạo: " + this.getDateTime());
		cell = cells.getCell("A4");
		getCellStyle(workbook, "A4", Color.NAVY, true, 10);
		cell.setValue("Tạo Bởi :  " + UserName);

		cell = cells.getCell("AA1");
		cell.setValue("Kenh Ban Hang");
		
		cell = cells.getCell("AB1");
		cell.setValue("Don Vi Kinh Doanh");
		
		cell = cells.getCell("AC1");
		cell.setValue("Dai Dien Kinh Doanh");
		
		cell = cells.getCell("AD1");
		cell.setValue("Khach Hang");
		
		
		
		cell = cells.getCell("AE1");
		cell.setValue("Loai Cua Hang");
		
		cell = cells.getCell("AF1");
		cell.setValue("Vi Tri Cua Hang");
		cell = cells.getCell("AG1");
		cell.setValue("Hang Cua Hang");
		cell = cells.getCell("AH1");
		cell.setValue("Nhom Khach Hang");
		cell = cells.getCell("AI1");
		cell.setValue("Nhan Hang");
		cell = cells.getCell("AJ1");
		cell.setValue("Chung Loai");
		
		//nhom sp
		
		cell = cells.getCell("AK1");
		cell.setValue("Nhom San Pham");
		
		
		cell = cells.getCell("AL1");
		cell.setValue("San Pham");
		
		
		
		cell = cells.getCell("AM1");
		
		cell.setValue("Do Phu");
		
		cell = cells.getCell("AN1");
		
		cell.setValue("Doanh So");
	
	}

	private boolean CreateStaticData(Workbook workbook,IStockintransit obj) {
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		Utility util = new Utility();
		obj.setnppId(util.getIdNhapp(obj.getuserId()));		
		
			String[] param = new String[14];
			param[0] = obj.getnppId().equals("")?null:obj.getnppId();
			param[1] = obj.gettungay();
			param[2] = obj.getdenngay();
				param[3]=obj.getkenhId().equals("")?null:obj.getkenhId();	
			param[4]=obj.getnhanhangId().equals("")? null:obj.getnhanhangId();
			param[5]=obj.getchungloaiId().equals("")?null:obj.getchungloaiId();
			param[6]=obj.getdvkdId().equals("")?null:obj.getdvkdId();
			
			param[7]=obj.getkhuvucId()==""?null:obj.getkhuvucId();
			param[8]=obj.getvungId()==""?null:obj.getvungId();
			param[9]=obj.getsanphamId();
			param[10]=obj.getuserId();
			param[11]="0";//LAY BAO CAO DISTRIBUTOR
			
			param[12]=obj.gettype();//type=1 la lay bao cao theo thoi gian
		    
			param[13]=obj.GetNhoSPId();
			
			System.out.println(param[0]);
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
			System.out.println("TYPE :"+ param[12]);
			System.out.println("NHOSP :"+ param[13]);
			
			ResultSet rs=db.getRsByPro("REPORT_DOPHUSANPHAM", param);
		    


		int i = 2;
		if (rs != null) {

			try {// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 8.0f);
				cells.setColumnWidth(5, 8.0f);
				cells.setColumnWidth(6, 8.0f);
				cells.setColumnWidth(7, 15.0f);

				Cell cell = null;
				while (rs.next())// lap den cuoi bang du lieu
				{

					/*
					 * //lay tu co so du lieu, gan bien String Channel =
					 * rs.getString("channel"); String Region
					 * =rs.getString("region"); String Area
					 * =rs.getString("area"); String Dist =
					 * rs.getString("distributor");
					 * 
					 * 
					 * String Outlet = rs.getString("distribution");
					 */
					String kenhbanhang=rs.getString("channel");
					
					String SaleRep = rs.getString("Sale_rep");
					String Customer = rs.getString("custommer");
					String SKU = rs.getString("SKU");
					
					String OutletType = rs.getString("outlet_type");
					String OutletLocation = rs.getString("outlet_location");
					String OutletClass = rs.getString("outlet_class");
					String GroupCustomer = rs.getString("group_customer");
					String Brand = rs.getString("Brand");
					String Catogery = rs.getString("catogery");
					String nhomsp=rs.getString("nhomsp");
					String SumOutlet = rs.getString("outlet");
					float volume=rs.getFloat("volume");
					String BusinessUnit=rs.getString("unit");
					
;					cell = cells.getCell("AA" + Integer.toString(i));
					cell.setValue(kenhbanhang);
					cell = cells.getCell("AB" + Integer.toString(i));
					cell.setValue(BusinessUnit);	
					cell = cells.getCell("AC" + Integer.toString(i));
					cell.setValue(SaleRep);
					
					cell = cells.getCell("AD" + Integer.toString(i));
					cell.setValue(Customer);
					
					cell = cells.getCell("AE" + Integer.toString(i));
					cell.setValue(OutletType);
					cell = cells.getCell("AF" + Integer.toString(i));
					cell.setValue(OutletLocation);
					cell = cells.getCell("AG" + Integer.toString(i));
					cell.setValue(OutletClass);
					cell = cells.getCell("AH" + Integer.toString(i));
					
					cell.setValue(GroupCustomer);
					cell = cells.getCell("AI" + Integer.toString(i));
					
					cell.setValue(Brand);
					cell = cells.getCell("AJ" + Integer.toString(i));
					cell.setValue(Catogery);
					
					//nhom hang
					cell = cells.getCell("AK" + Integer.toString(i));
					cell.setValue(nhomsp);
					
					//sku
				
					//ten sp
					cell = cells.getCell("AL" + Integer.toString(i));
					cell.setValue(SKU);
					//do phu
					cell = cells.getCell("AM" + Integer.toString(i));
					
					cell.setValue(Float.parseFloat(SumOutlet));
					//volum
					cell = cells.getCell("AN" + Integer.toString(i));
					cell.setValue(volume);
					
					i++;
				}
				if(i==2){
					obj.setMsg("Khong Lay Duoc Du Lieu Bao Cao");
					return false;
				}
				// xong buoc dua du lieu vao exel
				if(rs!=null)
					rs.close();
				if(db!=null)
					db.shutDown();
				// create pivot
				/*getAn(workbook, i);
				PivotTables pivotTables = worksheet.getPivotTables();

				// Adding a PivotTable to the worksheet
				String pos = Integer.toString(i - 1); // pos la dong cuoi cung
														// ,A12 la toa do dau
														// cua banng du lieu, Q
														// pos la dong cuoi
				int index = pivotTables.add("=AA1:AJ" + pos, "A12",
						"PivotTableDemo");
				// System.out.println("index:"+index);
				// Accessing the instance of the newly added PivotTable
				PivotTable pivotTable = pivotTables.get(index);// truyen index

				pivotTable.setRowGrand(false);
				pivotTable.setColumnGrand(true);
				pivotTable.setAutoFormat(true);
				// Setting the PivotTable autoformat type.
				// pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);

				pivotTable.addFieldToArea(PivotFieldType.ROW, 0);
				pivotTable.getRowFields().get(0).setAutoSubtotals(false);
				pivotTable.addFieldToArea(PivotFieldType.ROW, 1);
				pivotTable.getRowFields().get(1).setAutoSubtotals(true);
				// pivotTable.addFieldToArea(PivotFieldType.ROW, 7);
				// pivotTable.getRowFields().get(2).setAutoSubtotals(false);
				// pivotTable.addFieldToArea(PivotFieldType.ROW, 3);
				// pivotTable.getRowFields().get(3).setAutoSubtotals(false);
				pivotTable.addFieldToArea(PivotFieldType.COLUMN, 2);
				// pivotTable.addFieldToArea(PivotFieldType.ROW, 5);
				pivotTable.addFieldToArea(PivotFieldType.DATA, 9);
				pivotTable.getDataFields().get(0).setDisplayName("Cửa Hiệu");

				pivotTable.getDataFields().get(0).setNumber(3);
				// pivotTable.getDataFields().get(1).setNumber(3);
				// pivotTable.addFieldToArea(PivotFieldType.COLUMN,
				// pivotTable.getDataField());
*/				return true;
			} catch (Exception e) {
				obj.setMsg("Khong Lay Duoc Bao Cao.Loi :"+ e.toString());
				return false;
			}
		} else {
			obj.setMsg("Khong Lay Duoc Bao Cao");
			return false;
		}

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
		for (int j = 1; j <= i; j++) {
			cells.hideRow(j);
		}

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
