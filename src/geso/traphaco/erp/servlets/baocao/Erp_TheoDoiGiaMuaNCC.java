package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.servlets.baocao.ReportAPI;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class Erp_TheoDoiGiaMuaNCC extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public Erp_TheoDoiGiaMuaNCC() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String ctyId = (String)session.getAttribute("congtyId");
		
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setErpCongtyId(ctyId);
		
		obj.InitErp();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTheoDoiGiaMuaNCC.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");

		IStockintransit obj = new Stockintransit();
		obj.setdiscount("1");
		obj.setvat("1");
		obj.setErpCongtyId(ctyId);
		
		OutputStream out = response.getOutputStream();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		obj.settungay(util.antiSQLInspection(request.getParameter("Sdays")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		
		obj.setLNccIds(request.getParameterValues("lnccId"));
		obj.setNccIds(request.getParameterValues("nccId"));
		obj.setLSPIds(request.getParameterValues("lspId"));
		
		String tuthang=request.getParameter("tuthang").length()< 2 ? ("0"+request.getParameter("tuthang")) :request.getParameter("tuthang") ;
		String toithang=request.getParameter("denthang").length()< 2 ? ("0"+request.getParameter("denthang")) :request.getParameter("denthang") ;
		obj.setFromMonth(tuthang);
		
		obj.setToMonth(toithang);
		obj.setToYear(request.getParameter("dennam"));
		obj.setFromYear(request.getParameter("tunam"));
		String type= request.getParameter("xemtheo");
		
		if(type.equals("1")){
			obj.settungay(obj.getFromYear()+"-"+ (obj.getFromMonth().length() >1?obj.getFromMonth():"0"+obj.getFromMonth()) +"-01");
			obj.setdenngay(obj.getToYear()+"-"+ (obj.getToMonth().length() >1?obj.getToMonth():"0"+obj.getToMonth()) +"-31");
		}
		obj.settype(type);
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTheoDoiGiaMuaNCC.jsp";
		
		//System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=TheoDoiGiaMua.xlsm");
			
			String query = setQuery(obj); 

	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj, query))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Xin loi khong co bao cao trong thoi gian nay");
				    writer.close();
				}
			} 
	        catch (Exception e) 
	         { 
	        	e.printStackTrace();
	        	obj.init();
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
			}
		}else{
		
				obj.InitErp();
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
		}
	}
	
	public String setQuery(IStockintransit obj)
	{		
		String query = 	"SELECT * " +
						"FROM	" +
						"(	" +
						"	SELECT INFO.NCCID, INFO.LSPID, INFO.MANCC, INFO.TENNCC,	" + 
						"	INFO.LOAISP, INFO.SPID, INFO.MASP, INFO.TENSP , INFO.QUYCACH,	" +
						"	INFO.DONGIA, INFO.DONVI,	" +
						"	(	" +
						"		SELECT TOP (1) NGAYMUA	" +
						"		FROM ERP_MUAHANG MH	" +		
						"		INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ " +
						"		WHERE MHSP.SANPHAM_FK = INFO.SPID AND INFO.DONGIA = MHSP.DONGIA AND MH.NGAYMUA IS NOT NULL " +
						"		ORDER BY MH.NGAYMUA DESC " +
						"	) AS NGAY " +
						"	FROM " +
						"	(	" +
						"		SELECT	DISTINCT LOAISP.PK_SEQ AS LSPID, NCC.PK_SEQ AS NCCID, NCC.MA AS MANCC, NCC.TEN AS TENNCC, " + 
						"		LOAISP.TEN AS LOAISP, SP.PK_SEQ AS SPID, SP.MA AS MASP, SP.TEN AS TENSP , SP.QUYCACH,	" +
						"		MHSP.DONGIA, DVDL.DIENGIAI AS DONVI " +
						"		FROM ERP_MUAHANG MH	" +
						"		INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ " +
						"		INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MH.NHACUNGCAP_FK " +
						"		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = MHSP.SANPHAM_FK " +
						"		INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						"		INNER JOIN ERP_LOAISANPHAM LOAISP ON LOAISP.PK_SEQ = SP.LOAISANPHAM_FK " +	
						"	)INFO " +
						"	WHERE SPID IS NOT NULL AND DONGIA IS NOT NULL " +
						")A WHERE 1 = 1 " ;
						
		System.out.println("Query: "+query);
		if(obj.gettungay().length() > 0){
			 query = query + " AND NGAY >= '" + obj.gettungay() + "' ";
		}
		 
		if(obj.getdenngay().length() > 0){
			 query = query + " AND NGAY <= '" + obj.getdenngay() + "' ";
		}

		
		if(obj.getLNccIds() != null){
			if(obj.getLNccIds().length > 0){
				String lncclist = "";
				for(int i = 0; i < obj.getLNccIds().length; i++){
					if(lncclist.length() == 0){
						lncclist = lncclist + obj.getLNccIds()[i];
					}else{
						lncclist = lncclist + ", " + obj.getLNccIds()[i];
					}
				}
				if(lncclist.length() > 0){
					query = query + " AND LOAINCCID IN (" + lncclist + " ) " ;
				}
				System.out.println(lncclist);
			}
		}
			
		if(obj.getNccIds() != null){
			if(obj.getNccIds().length > 0){
				String ncclist = "";
				for(int i = 0; i < obj.getNccIds().length; i++){
					if(ncclist.length() == 0){
						ncclist = ncclist + obj.getNccIds()[i];
					}else{
						ncclist = ncclist + ", " + obj.getNccIds()[i];
					}
				}
				if(ncclist.length() > 0){
					query = query + " AND NCCID IN (" + ncclist + " ) " ;
				}

			}
		}
			
		if(obj.getLSPIds() != null){
			if(obj.getLSPIds().length > 0){
				String lsplist = "";
				for(int i = 0; i < obj.getLSPIds().length; i++){
					if(lsplist.length() == 0){
						lsplist = lsplist + obj.getLSPIds()[i];
					}else{
						lsplist = lsplist + ", " + obj.getLSPIds()[i];
					}
				}
				if(lsplist.length() > 0){
					query = query + " AND LSPID IN (" + lsplist + " ) " ;
				}
				
			}
		}
		 
		 query = query + " ORDER BY MANCC, TENNCC, LOAISP, MASP, TENSP, QUYCACH, NGAY ";
		 System.out.println(query);
	    return query;   
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\TheoDoiGiaMua.xlsm");
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateHeader(workbook,obj);
		isFillData = FillData(workbook, obj, query);
   
		if (!isFillData)
		{
			if(fstream != null)
				fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	private void CreateHeader(Workbook workbook, IStockintransit obj)throws Exception
	{	
 		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		
	    Style style;
	    Font font = new Font();
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);
	   	
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	        
	    
	    String tieude = "BÁO CÁO THEO DÕI GIÁ MUA THEO NHÀ CUNG CẤP";
	    
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
		
	    cell = cells.getCell("A3"); 
	   
    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
	  
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); 
	   
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
	   
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    		
/*		cell = cells.getCell("A7");		cell.setValue("LOẠI NCC");			ReportAPI.setCellHeader(cell);//1
	    cell = cells.getCell("B7");		cell.setValue("MÃ NCC");			ReportAPI.setCellHeader(cell);///2
		cell = cells.getCell("C7");		cell.setValue("TÊN NHÀ CUNG CẤP");				ReportAPI.setCellHeader(cell);//5
		cell = cells.getCell("D7");		cell.setValue("LOẠI SẢN PHẨM");			ReportAPI.setCellHeader(cell);//6
		cell = cells.getCell("E7");		cell.setValue("MÃ SẢN PHẨM");					ReportAPI.setCellHeader(cell);//8
		cell = cells.getCell("F7");		cell.setValue("TÊN SẢN PHẨM");				ReportAPI.setCellHeader(cell);//9
		cell = cells.getCell("G7");		cell.setValue("QUY CÁCH");				ReportAPI.setCellHeader(cell);//10
		cell = cells.getCell("H7");		cell.setValue("ĐƠN VỊ");				ReportAPI.setCellHeader(cell);//10
		cell = cells.getCell("I7");		cell.setValue("GIÁ 1");			ReportAPI.setCellHeader(cell);//15
		cell = cells.getCell("J7");		cell.setValue("NGÀY THAY ĐỔI");				ReportAPI.setCellHeader(cell);//10
		cell = cells.getCell("K7");		cell.setValue("GIÁ 2");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("L7");		cell.setValue("NGÀY THAY ĐỔI");				ReportAPI.setCellHeader(cell);//10
		cell = cells.getCell("M7");		cell.setValue("GIÁ 3");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("N7");		cell.setValue("NGÀY THAY ĐỔI");				ReportAPI.setCellHeader(cell);//10
		cell = cells.getCell("O7");		cell.setValue("GIÁ 4");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("P7");		cell.setValue("NGÀY THAY ĐỔI");				ReportAPI.setCellHeader(cell);//10
		cell = cells.getCell("Q7");		cell.setValue("GIÁ 5");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("R7");		cell.setValue("NGÀY THAY ĐỔI");				ReportAPI.setCellHeader(cell);//10	
		cell = cells.getCell("Q7");		cell.setValue("GIÁ 6");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("R7");		cell.setValue("NGÀY THAY ĐỔI");				ReportAPI.setCellHeader(cell);//10*/
		
		
		
	}
	private Hashtable< Integer, String > htbValueCell ()
	{
		Hashtable<Integer, String> htb=new Hashtable<Integer, String>();
		htb.put(1,"A");htb.put(2,"B");htb.put(3,"C");htb.put(4,"D");htb.put(5,"E");
		htb.put(6,"F");htb.put(7,"G");htb.put(8,"H");htb.put(9,"I");htb.put(10,"J");
		htb.put(11,"K");htb.put(12,"L");htb.put(13,"M");htb.put(14,"N");htb.put(15,"O");
		htb.put(16,"P");htb.put(17,"Q");htb.put(18,"R");htb.put(19,"S");htb.put(20,"T");
		htb.put(21,"U");htb.put(22,"V");
		return htb; 
	}
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Hashtable<Integer, String> htb = this.htbValueCell();
		Cells cells = worksheet.getCells();	
		
		for(int i = 0;i < 7; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	
		
		ResultSet rs = db.get(query);
		int index = 8;
		String chuoibk = "";
		String chuoi;
		
			try 
			{
				int sott = 0;
				Cell cell = null;
				Style style = null;
				
				cells.setColumnWidth(0, 11.29f);
				cells.setColumnWidth(1, 15.00f);
				cells.setColumnWidth(2, 28.00f);
				cells.setColumnWidth(3, 15.00f);
				cells.setColumnWidth(4, 26.29f);
				cells.setColumnWidth(5, 40.43f);
				cells.setColumnWidth(6, 54.57f);
				cells.setColumnWidth(7, 8.43f);
				
				while (rs.next())
				{					
					chuoi = rs.getString("MANCC").trim()+rs.getString("SPID").trim();
					if(!chuoibk.equals(chuoi)){
						if(index > 8){
							// Ve khung cho cac cell
							for(int i = sott; i < 21; i++){
								cell = cells.getCell(htb.get(i) + String.valueOf(index-1));		cell.setValue("");	
								ReportAPI.setCellHasBorder(cell);
							}
						}
						
//						cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("");	//Loai nha cung cap  
//						cell = CreateBorderSetting(cell);
						
						cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("MANCC"));	//Ma NCC  						
						cell = CreateBorderSetting(cell);
						
						cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("TENNCC"));	//   						
						cell = CreateBorderSetting(cell);
						
						cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("LOAISP"));	// 
						cell = CreateBorderSetting(cell);
						
						cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("MASP"));	// 
						cell = CreateBorderSetting(cell);
						
						cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getString("TENSP"));	// 
						cell = CreateBorderSetting(cell);
						
						cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getString("QUYCACH"));	// 
						cell = CreateBorderSetting(cell);

						cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getString("DONVI"));	// 
						cell = CreateBorderSetting(cell);
						style = cell.getStyle();				
						style.setHAlignment(TextAlignmentType.RIGHT);					
						cell.setStyle(style);
						
						chuoibk = chuoi;
					 	index++;	
					  sott = 9;
					}
					
					if(sott < 21){
						cell = cells.getCell(htb.get(sott) + String.valueOf(index-1));		cell.setValue(rs.getDouble("DONGIA"));	// 5
						cell = CreateBorderSetting(cell);
						style = cell.getStyle();				
						style.setNumber(3);
						style.setHAlignment(TextAlignmentType.RIGHT);					
						cell.setStyle(style);
					
						sott++;
						cell = cells.getCell(htb.get(sott) + String.valueOf(index-1));		cell.setValue(rs.getString("NGAY"));	// 5
						cell = CreateBorderSetting(cell);
						style = cell.getStyle();				
						style.setHAlignment(TextAlignmentType.RIGHT);					
						cell.setStyle(style);
					
						sott++;
					}
				}
				
				if(sott > 0){
					for(int i= sott; i < 21;i++){
						
						cell = cells.getCell(htb.get(i) + String.valueOf(index-1));		cell.setValue("");	// 5
						cell = CreateBorderSetting(cell);
					}
				}
				if (rs != null)
					rs.close();
				
				if(db != null)
					db.shutDown();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		
		return true;
	}	
	 public Cell CreateBorderSetting(Cell cell) throws IOException
	    {
		
	        Style style;
	        style = cell.getStyle();

	        //Set border color
	        style.setBorderColor(BorderType.TOP, Color.BLACK);
	        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
	        style.setBorderColor(BorderType.LEFT, Color.BLACK);
	        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

	        //Set the cell border type
	        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
	        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

	        cell.setStyle(style);
	        return cell;
	    }


}
