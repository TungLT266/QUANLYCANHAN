package geso.traphaco.center.servlets.reports;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotField;
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldSubtotalType;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotFields;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class DailyStocknpp extends HttpServlet {	
	
    public DailyStocknpp() {
        super();
    }


    private String gettenuser(String userId_){
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq="+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
			  while(rs_tenuser.next()){
				  return rs_tenuser.getString("ten");
			  }
			}catch(Exception er){
				return "";
			}finally{
				try{
					if(rs_tenuser != null) rs_tenuser.close();
					if(db != null) db.shutDown();
				}catch(java.sql.SQLException e){}
			}
		}
		
		return "";
	}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");	
		IStockintransit obj = new Stockintransit();
		 Utility util=new Utility();
		
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
		

		obj.setuserTen(userTen);
		obj.setuserId(userId);
		obj.setMsg("");
		obj.settungay("");
		obj.setdenngay("");
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/rp_DailyStocknpp.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream(); 
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");	
		IStockintransit obj = new Stockintransit();
		request.setCharacterEncoding("utf-8");
		String nextJSP = "/TraphacoHYERP/pages/Center/rp_DailyStocknpp.jsp";
		Utility util = new Utility();
		try
		    {
			HttpSession session = request.getSession();
			obj.setuserTen((String)session.getAttribute("userTen")!=null? 
					(String)session.getAttribute("userTen"):"");
			
			
			obj.settungay(util.antiSQLInspection(request.getParameter("tungay"))!=null? 
					util.antiSQLInspection(request.getParameter("tungay")):"");
			
			
			obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay"))!=null?  
					util.antiSQLInspection(request.getParameter("denngay")):"");
			
			obj.setuserId(util.antiSQLInspection(request.getParameter("userId"))!=null?
						util.antiSQLInspection(request.getParameter("userId")):"");
			
	    /*	response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=TonKhoTheoNgay.xls");
*/	
			   response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DailyStock(NPP).xlsm");

			
			boolean isTrue =  CreatePivotTable(out,obj);
	       if(!isTrue){
	    	   
	       }else{
	    	   PrintWriter writer = new PrintWriter(out);
	    	   writer.write("Xin loi. Khong tao duoc bao cao trong thoi gian nay..!!");
	    	   writer.close();
	       }
	     }
		catch (Exception ex) {
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
	}
	private boolean CreatePivotTable(OutputStream out,IStockintransit obj) throws Exception
    {   
		
		FileInputStream fstream;
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\DailyStock(NPP).xlsm");
//		 fstream = new FileInputStream("D:\\Best Stable\\SalesUp\\WebContent\\pages\\Templates\\DailyStock(NPP).xlsm");	

			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook, obj.gettungay(),obj.getdenngay(), obj.getuserTen());
	     //Buoc3 
	     // day du lieu vao
	    boolean isTrue = CreateStaticData(workbook,obj);
	    if(!isTrue){
	    	return false;
	    }
	    workbook.save(out);
	    fstream.close();
	    return true;
   }
	
	private void CreateStaticHeader(Workbook workbook, String tungay, String denngay, String UserName)  throws Exception
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	    
	    
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("TỒN KHO THEO NGÀY");   	
	    
	  
	    style = cell.getStyle();
	   
	   Font font2 = new Font();
       font2.setColor(Color.RED);//mau chu
       font2.setSize(14);// size chu
       font2.setBold(true);
       style.setFont(font2); 
       style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
       cell.setStyle(style);
       
	    cell = cells.getCell("A3"); getCellStyle(workbook,"A3",Color.NAVY,true,9);
       
	    cell.setValue("Từ ngày  " + tungay + "      Đến ngày  " + denngay);    
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày Tạo: " + this.getDateTime());
	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
	    cell.setValue("Tạo Bởi:  " + UserName);
	    
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    cell = cells.getCell("EA1"); cell.setValue("Kênh Bán Hàng"); 
	    cell = cells.getCell("EB1"); cell.setValue("Tên Sản Phẩm");
	    cell = cells.getCell("EC1"); cell.setValue("Số Lượng Quy Lẻ");
	    cell = cells.getCell("ED1"); cell.setValue("Ngày");	   
	    cell = cells.getCell("EE1"); cell.setValue("Mã Nhà Phân Phối");
	    cell = cells.getCell("EF1"); cell.setValue("Mã Sản Phẩm");
	    cell = cells.getCell("EG1"); cell.setValue("Số Lượng Thùng");
	    cell = cells.getCell("EH1"); cell.setValue("Kho");
	    cell = cells.getCell("EI1"); cell.setValue("Đơn Vị Kinh Doanh");
	    cell = cells.getCell("EJ1"); cell.setValue("Chủng Loại");
	    cell = cells.getCell("EK1"); cell.setValue("Nhãn Hàng");
	    cell = cells.getCell("EL1"); cell.setValue("Số Tiền");
	  
	    worksheet.setName("Sheet1");
	}
	private boolean CreateStaticData(Workbook workbook,IStockintransit obj)  throws Exception
	{dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    Utility util = new Utility();
	
				String sql = " select isnull(d.ten, 'Chua xac dinh') as Chanel, f.ten as Region, h.ten as Area, e.ten as Distributor, e.sitecode as Distcode, "+
			   " b.ma  +'_'+  b.ten as SKU, b.ma as SKUcode, a.ngay as Date, c.ten as Warehouse, g.ten as Province, "+
			   " i.donvikinhdoanh as BusinessUnit, k.ten as Brands, j.ten as Category, a.soluong as Piece, "+
			   " case when a.soluong is null then 0 else a.soluong/qc.soluong1 end as Quatity,"+   
			   " case when a.soluong * nppk.giamua*1.1 is null then 0 else a.soluong * nppk.giamua*1.1 end as Amount "+
			   " from tonkhongay a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join kho c on a.kho_fk = c.pk_seq "+ 
			   " left join kenhbanhang d on a.kbh_fk = d.pk_seq	inner join nhaphanphoi e on a.npp_fk = e.pk_seq "+
			   " inner join khuvuc f on e.khuvuc_fk = f.pk_seq left join tinhthanh g on e.tinhthanh_fk = g.pk_seq "+
			   " inner join vung h on f.vung_fk = h.pk_seq inner join donvikinhdoanh i on b.dvkd_fk = i.pk_seq "+
			   " inner join chungloai j on b.chungloai_fk = j.pk_seq inner join nhanhang k on b.nhanhang_fk = k.pk_seq "+ 
		       " left join ( "+
			   " select distinct bgm.kenh_fk as kbh_fk,bgm_sp.sanpham_fk,bgmnpp.npp_fk,bgm_sp.giamuanpp as giamua from banggiamuanpp_npp bgmnpp "+ 
	           " inner join banggiamuanpp bgm on bgm.pk_seq = bgmnpp.banggiamuanpp_fk "+
	           " inner join bgmuanpp_sanpham bgm_sp on bgm_sp.bgmuanpp_fk = bgm.pk_seq "+
			   " where bgmnpp.npp_fk ='"+util.getIdNhapp(obj.getuserId())+"'"+
			   " ) nppk on "+
	           " nppk.npp_fk = a.npp_fk "+ 
	           " and nppk.sanpham_fk = a.sanpham_fk and nppk.kbh_fk = a.kbh_fk "+ 
		       " left join quycach qc on qc.dvdl1_fk = b.dvdl_fk and a.sanpham_fk = qc.sanpham_fk "+ 
		       " where a.ngay >='" + obj.gettungay() + "' and a.ngay <= '" + obj.getdenngay() + "' and a.npp_fk = '"+util.getIdNhapp(obj.getuserId())+"' ";
		 System.out.println("Lay Du Lieu :"+sql);

			ResultSet rs = db.get(sql);
		 int i = 2;
		if(rs != null)
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
				cells.setColumnWidth(7, 15.0f);
				
				
				//set do rong cho dong
				
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{ 
				
					//lay tu co so du lieu, gan bien
					String Channel = rs.getString("Chanel");
					String SKU = rs.getString("SKU");				
					String Piece = rs.getString("Piece");
					String Date = rs.getString("Date");					
					String DistributorCode = rs.getString("Distributor");
					String SkuCode = rs.getString("SKUcode");					
					String Quantily = rs.getString("Quatity");
					String Warehouse = rs.getString("Warehouse");
					String BusinessUnit = rs.getString("BusinessUnit");
					String Category = rs.getString("Category");
					String Brands = rs.getString("Brands");
					String Amount = rs.getString("Amount");
				
					cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(SKU);
					cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(Float.parseFloat(Piece));
					cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(Date);					
					cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(DistributorCode);
					cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(SkuCode);
					cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(Quantily);
					cell = cells.getCell("EH" + Integer.toString(i)); cell.setValue(Warehouse);			
					cell = cells.getCell("EI" + Integer.toString(i)); cell.setValue(BusinessUnit);
					cell = cells.getCell("EJ" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("EK" + Integer.toString(i)); cell.setValue(Brands);
					cell = cells.getCell("EL" + Integer.toString(i)); cell.setValue(Float.parseFloat(Amount));
					
					i++;
					if(i>65000){
						if(rs!=null) rs.close();
						if(db != null) db.shutDown();
						throw new Exception("Du lieu vuot qua gioi han file Excel. Vui long chon dieu kien.");
					}
				}
				if(rs!=null) 	rs.close();
				if(db != null)  db.shutDown();
				
				if(i==2)
					throw new Exception("Khong co bao cao trong thoi gian nay...!!!");
		
		
		
			 getAn(workbook,156);
			PivotTables pivotTables = worksheet.getPivotTables();

		    //Adding a PivotTable to the worksheet
			String pos = Integer.toString(i-1);	//pos la dong cuoi cung	,A12 la toa do dau cua banng du lieu, Q pos la dong cuoi
		    int index = pivotTables.add("=EA1:EL" + pos,"A12","PivotTableDemo");
	        // System.out.println("index:"+index);
		    //Accessing the instance of the newly added PivotTable
		    PivotTable pivotTable = pivotTables.get(index);//truyen index

		    pivotTable.setRowGrand(false);
		    pivotTable.setColumnGrand(true);
		    pivotTable.setAutoFormat(true);
		    
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 0); pivotTable.getRowFields().get(0).setAutoSubtotals(false);  
		    pivotTable.addFieldToArea(PivotFieldType.ROW, 1); pivotTable.getRowFields().get(1).setAutoSubtotals(false);  
		    pivotTable.addFieldToArea(PivotFieldType.DATA, 2); pivotTable.getDataFields().get(0).setNumber(3);
		    pivotTable.getDataFields().get(0).setDisplayName("Số Lượng Lẻ");
		    pivotTable.addFieldToArea(PivotFieldType.COLUMN, 3);
	    
	   
	}
	catch (Exception e){
		throw new Exception(e.getMessage());
	}
}else{
	return false;
}
		return true;

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
	    for(int j = 130; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
