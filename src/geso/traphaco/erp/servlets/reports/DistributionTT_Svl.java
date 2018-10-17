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

public class DistributionTT_Svl extends HttpServlet {
		
    public DistributionTT_Svl() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		Utility util=new Utility();
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
		IStockintransit obj = new Stockintransit();
		obj.settungay("");
		obj.setdenngay("");
		obj.init();
		session.setAttribute("checkedSKU","");
		session.setAttribute("obj",obj);
    	session.setAttribute("loi", "");
    	session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		String nextJSP = "/TraphacoHYERP/pages/Center/DistributionTT.jsp";
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		//Ireportnpp obj = new Reports();
		IStockintransit obj = new Stockintransit();
		boolean bfasle = true;
		Utility util = new Utility();
		try
		    {
			HttpSession session = request.getSession();
			String userTen = (String)session.getAttribute("userTen");
			if(userTen ==null) userTen ="";
			obj.setuserTen(userTen);
			String tungay=util.antiSQLInspection(request.getParameter("tungay"));
			if(tungay==null) tungay ="";
			obj.settungay(tungay);
			
			String userId =(String)session.getAttribute("userId");
			obj.setuserId(userId);
			String denngay=util.antiSQLInspection(request.getParameter("denngay"));
			if(denngay ==null)
				denngay ="";
			obj.setdenngay(denngay);
			
		 	String skuId = util.antiSQLInspection(request.getParameter("skuid"));
		 	if(skuId==null)
		 		skuId ="";
		   obj.setsanphamId(skuId);
		   
		 	String manpp = util.antiSQLInspection(request.getParameter("nppId"));
		 	if(manpp == null)
		 		manpp ="";
		 	obj.setnppId(manpp);
		 	
		 	obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))!=null? 
		 			util.antiSQLInspection(request.getParameter("vungId")):"");
		 	obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))!=null? 
		 			util.antiSQLInspection(request.getParameter("khuvucId")):"");
		 	obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null? 
		 			util.antiSQLInspection(request.getParameter("kenhId")):"");
		 	
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
		 	if(action.equals("create")){
		 		String[] skutest =request.getParameterValues("skutest");
			 	String[] valuechecked=request.getParameterValues("valuechecked");
			 	String whereSKU = "";
			 	String	chuoichuyen="";
			 		if(valuechecked.length>0)
			 		for(int i= 0; i<skutest.length;i++ ){
			 		if(valuechecked[i].equals("1")){
			 			whereSKU+="'"+skutest[i]+"',";
			 			chuoichuyen=chuoichuyen + ","+ skutest[i];
			 		}
			 		
			 		}
			 		if(whereSKU !="")
			 		whereSKU =" ("+ whereSKU.substring(0, whereSKU.length()-1)+")";
			 		if(chuoichuyen!=""){
			 		session.setAttribute("checkedSKU",chuoichuyen.substring(1,chuoichuyen.length()) );
			 		}else{
			 			session.setAttribute("checkedSKU","");
			 		}
			 		System.out.println ("Chuoi SKU la "+ whereSKU);
			 
			 	/*	response.setContentType("application/vnd.ms-excel");
			 		response.setHeader("Content-Disposition", "attachment; filename=DistributionReport.xls");
			 		*/
			 		response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "Attachment; filename=DistributionReport(TT)"+this.getPiVotName()+".xlsm");

			 		CreatePivotTable(out,response,request,obj,bfasle);
		 	}else{
		 		session.setAttribute("checkedSKU","");
				session.setAttribute("obj",obj);
		    	session.setAttribute("loi", "");
		    	session.setAttribute("userId", obj.getuserId());
				session.setAttribute("userTen", obj.getuserTen());	 		
		    	obj.init();
		 		session.setAttribute("obj",obj);
		 		String nextJSP = "/TraphacoHYERP/pages/Center/DistributionTT.jsp";
		 		response.sendRedirect(nextJSP);
		 	}
	     }
	    catch (Exception ex)
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
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj,boolean bfasle) throws IOException
    {   
		
		//khoi tao de viet pivottable
		//buoc 1
		//String strfstream="D:\\Best Stable\\SalesUp\\WebContent\\pages\\Templates\\Distribution(TT).xlsm";
		String strfstream = getServletContext().getInitParameter("path") + "\\Distribution(TT).xlsm";		

		FileInputStream fstream = new FileInputStream(strfstream);	
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook,obj.gettungay(),obj.getdenngay(),obj.getuserTen());
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook,obj,bfasle);
	     HttpSession session = request.getSession();
	     
		  obj.init();
		 session.setAttribute("obj", obj);
	     
	     if(bfasle==false){
	    	String loi="chua co bao cao trong thoi gian nay, vui long chon lai thoi gian xem bao cao";
	    	
	    	
	    	session.setAttribute("checkedSKU","");
			session.setAttribute("obj",obj);
	    	session.setAttribute("loi", "");
	    	session.setAttribute("userId", obj.getuserId());
			session.setAttribute("userTen", obj.getuserTen());	 		
	    	obj.init();
	 		session.setAttribute("obj",obj);
	 		String nextJSP = "/TraphacoHYERP/pages/Center/DistributionTT.jsp";
	 		response.sendRedirect(nextJSP);
	     }else{
	    	 //Saving the Excel file
	    	 workbook.save(out);
			    fstream.close();
	     }
   }
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    worksheet.setName("Sheet1");
	    Cells cells = worksheet.getCells();
	   
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("BÁO CÁO ĐỘ PHỦ SẢN PHẨM");   	
	    
	  
	    style = cell.getStyle();
	   
	   Font font2 = new Font();
       font2.setColor(Color.RED);//mau chu
       font2.setSize(14);// size chu
       style.setFont(font2); 
       style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
       cell.setStyle(style);
	    cell = cells.getCell("A2"); getCellStyle(workbook,"A2",Color.NAVY,true,10);
	    cell.setValue("Từ ngày " + dateFrom );    
	    cell = cells.getCell("B2"); getCellStyle(workbook,"B2",Color.NAVY,true,10);
	    cell.setValue("Tới Ngày " + dateTo);    
	    
	    
	    cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.NAVY,true,10);
	     cell.setValue("Ngày báo cáo: " + this.getDateTime());
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,10);
	    cell.setValue("Được tạo bởi:  " + UserName);
	    
	 
	    cell = cells.getCell("AA1"); cell.setValue("Kenh");
	    cell = cells.getCell("AB1"); cell.setValue("Vung");
	    cell = cells.getCell("AC1"); cell.setValue("Khu Vuc");
	    cell = cells.getCell("AD1"); cell.setValue("Nha Phan Phoi");
	    cell = cells.getCell("AE1"); cell.setValue("Dai Dien Kinh Doanh");
	    cell = cells.getCell("AF1"); cell.setValue("Khach Hang");
	    cell = cells.getCell("AG1"); cell.setValue("SKU");
	    cell = cells.getCell("AH1"); cell.setValue("Loai Cua Hang");
	    cell = cells.getCell("AI1"); cell.setValue("Vi Tri Cua Hang");
	    cell = cells.getCell("AJ1"); cell.setValue("Hang Cua Hang");
	    cell = cells.getCell("AK1"); cell.setValue("Nhom Khach Hang");
	    cell = cells.getCell("AL1"); cell.setValue("Nhan Hang");
	    cell = cells.getCell("AM1"); cell.setValue("Chung Loai");
	    cell = cells.getCell("AN1"); cell.setValue("Nhom San Pham");
	    cell = cells.getCell("AO1"); cell.setValue("Outlet");
	    cell = cells.getCell("AP1"); cell.setValue("Doanh So");
	  
	}
	private void CreateStaticData(Workbook workbook,IStockintransit obj,boolean bfasle) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	
	    
	   /* String st ="";
	    if(obj.getsanphamId() !="")
	   st = " and sph.sanpham_fk in "+ obj.getsanphamId() ;
	    //khoi tao ket noi csdl
		String dk = obj.getnppId() != ""? " and sph.npp_fk = '"+ obj.getnppId() + "'" : "";
		if(obj.getvungId().length()>0){
			dk+= " and v.pk_seq='" + obj.getvungId() +"'";
		}
		if(obj.getkhuvucId().length()>0){
			dk+= " and kv.pk_seq ='" + obj.getkhuvucId() +"'";
		}
		if(obj.getkenhId().length()>0)
		{
			dk +=" and kbh.pk_seq ='" + obj.getkenhId() +"'";
		}
		//truoc khi phan quyen
		String sql  ="select " +
        " kbh.ten as Channel," +
        " v.ten as Region," +
        " kv.ten as Area," +
        " npp.ten as Distributor,"+
		" ddkd.ten as Sale_rep,"+
		" kh.ten as custommer,"+
		" sp.ten as SKU,"+
		" lch.diengiai as outlet_type,"+
		" vt.diengiai as outlet_location,"+
		" hch.diengiai as outlet_class,"+
		" case when nhomkh.diengiai is null then '' else nhomkh.diengiai end as group_customer,"+
		" nh.ten as Brand,"+
		" cl.ten as catogery,"+
		" sph.outlet,"+
		" sph.baophu"+
		" from (" +
		"  select distinct dh.npp_fk,dh.ddkd_fk,dh.khachhang_fk,dhsp.sanpham_fk,dh.ngaynhap,1 as outlet, 1 as baophu"+
		" from donhang_sanpham dhsp inner join donhang dh on dh.pk_seq = dhsp.donhang_fk where dh.trangthai ='1' and dh.tonggiatri > 0 and dh.ngaynhap  >= '"+ obj.gettungay() +"' and dh.ngaynhap <='"+ obj.getdenngay() +"') sph"+
		" inner join khachhang kh on kh.pk_seq = sph.khachhang_fk and kh.npp_fk = sph.npp_fk"+
		" left join loaicuahang lch on lch.pk_seq = kh.lch_fk"+
		" left join hangcuahang hch on hch.pk_seq = kh.hch_fk"+
		" left join vitricuahang vt on vt.pk_seq = kh.vtch_fk"+
		" left join sanpham sp on sp.pk_seq = sph.sanpham_fk"+
		" inner join daidienkinhdoanh ddkd on ddkd.pk_seq = sph.ddkd_fk"+
		" left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk"+
		" left join chungloai cl on cl.pk_seq = sp.chungloai_fk"+
		" left join kenhbanhang kbh on kbh.pk_seq = kh.kbh_fk " +
		" left join nhaphanphoi npp on npp.pk_seq = sph.npp_fk " +
		" left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk " +
		" left join vung v on v.pk_seq = kv.vung_fk "+
		" left join (select distinct a.kh_fk,b.diengiai from NHOMKHACHHANG_KHACHHANG a inner join nhomkhachhang b on b.pk_seq= a.nkh_fk) nhomkh"+
		" on nhomkh.kh_fk = kh.pk_seq "+
		" where  sph.ngaynhap >= '"+ obj.gettungay() +"' and sph.ngaynhap <='"+ obj.getdenngay() +"' "  + dk + st;
		
		//phanquyen
		geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
		sql += " and npp.pk_seq in "+ ut.quyen_npp(obj.getuserId()) + " and kbh.pk_seq in " + ut.quyen_kenh(obj.getuserId())
							+" and sp.pk_seq in "+ ut.quyen_sanpham(obj.getuserId());
							System.out.println(sql);	
*/
		
		
		
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
		param[11]="1";//LAY BAO CAO CENTER
		
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
		System.out.println("Nhom SP :"+ param[13]);
		ResultSet rs=db.getRsByPro("REPORT_DOPHUSANPHAM", param);
	    
	    	
	    	
		int i = 2;
		if(rs!=null)
		{
			
			
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 8.0f);			
				cells.setColumnWidth(8, 8.0f);	
				cells.setColumnWidth(9, 8.0f);	
				cells.setColumnWidth(10, 15.0f);
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{
					String Channel = rs.getString("Channel");
					
					String SaleRep = rs.getString("Sale_rep");
					String Region = rs.getString("Region");
					String Area = rs.getString("Area");
					String Distributor = rs.getString("Distributor");
					String Customer = rs.getString("custommer");
					String SKU =rs.getString("SKU");
					String OutletType = rs.getString("outlet_type");
					String OutletLocation = rs.getString("outlet_location");
					String OutletClass = rs.getString("outlet_class");
					String GroupCustomer = rs.getString("group_customer");
					String Brand = rs.getString("Brand");
					String Catogery = rs.getString("catogery");
					String NhomSP = rs.getString("NHOMSP");
					
					String SumOutlet = rs.getString("outlet");
					String VOLUME = rs.getString("VOLUME");
					
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(Region);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(Area);
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Distributor);
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(SaleRep);
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(Customer);			
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(SKU);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(OutletType==null?"Chua Xac Dinh":OutletType);
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(OutletLocation==null?"Chua Xac Dinh":OutletLocation);
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(OutletClass==null?"Chua Xac Dinh":OutletClass );
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(GroupCustomer==null?"Chua Xac Dinh":GroupCustomer);
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue(Brand);
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue(Catogery);
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue(NhomSP);
					cell = cells.getCell("AO" + Integer.toString(i)); cell.setValue(Float.parseFloat(SumOutlet));
					cell = cells.getCell("AP" + Integer.toString(i)); cell.setValue(Float.parseFloat(VOLUME));
					i++;
				}
				System.out.println("so dong:"+i);
	         if(rs!=null) rs.close();		
		
		//xong buoc dua du lieu vao exel
		
		
		
	    
		
		//create pivot
		 getAn(workbook,i);
	         
		/*PivotTables pivotTables = worksheet.getPivotTables();

	    //Adding a PivotTable to the worksheet
		String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
	    int index = pivotTables.add("=AA1000:AN" + pos,"A12","PivotTableDemo");
        // System.out.println("index:"+index);
	    //Accessing the instance of the newly added PivotTable
	    PivotTable pivotTable = pivotTables.get(index);//truyen index

	    pivotTable.setRowGrand(false);
	    pivotTable.setColumnGrand(true);
	    pivotTable.setAutoFormat(true);
	    //Setting the PivotTable autoformat type.
	    //pivotTable.setAutoFormatType(PivotTableAutoFormatType.REPORT6);
 
	   
	  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	 pivotTable.getRowFields().get(0).setAutoSubtotals(false);  
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(true);	 
	 
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	pivotTable.getRowFields().get(2).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	pivotTable.getRowFields().get(3).setAutoSubtotals(false);
	    pivotTable.addFieldToArea(PivotFieldType.ROW, 4);	pivotTable.getRowFields().get(4).setAutoSubtotals(false);
		 
	    pivotTable.addFieldToArea(PivotFieldType.COLUMN, 6);	
	    //pivotTable.addFieldToArea(PivotFieldType.ROW, 5);	
	   pivotTable.addFieldToArea(PivotFieldType.DATA, 10);	pivotTable.getDataFields().get(0).setDisplayName("Outlet");
	    
	    pivotTable.getDataFields().get(0).setNumber(3);
	    //pivotTable.getDataFields().get(1).setNumber(3);
	    //pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());
	   */ bfasle=true;
			}
			catch (Exception e){
				bfasle=false;
				e.printStackTrace(); 
				System.out.println("Error : Inventory Report : "+e.toString());
			}
		}else{
			bfasle=false;
		}
		
	}
	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
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
	private void getAn(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 1000; j <= i; j++)
	    { 
	    	cells.hideRow(j);
	    }
		
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}

