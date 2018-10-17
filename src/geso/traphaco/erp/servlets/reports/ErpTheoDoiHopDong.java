package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

public class ErpTheoDoiHopDong extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpTheoDoiHopDong() {
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
		
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		
		obj.setdiscount("1");
		obj.setvat("1");
		obj.init();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpTheoDoiHopDong.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		obj.settungay(util.antiSQLInspection(request.getParameter("Sdays")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null?
				util.antiSQLInspection(request.getParameter("kenhId")):"");
		
		obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))!=null?
				util.antiSQLInspection(request.getParameter("vungId")):"");
			
		obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))!=null?
				util.antiSQLInspection(request.getParameter("khuvucId")):"");
		
		obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhs"))!=null?
				util.antiSQLInspection(request.getParameter("gsbhs")):"");
		
		obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))!=null?
				util.antiSQLInspection(request.getParameter("nppId")):"");
		
		obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))!= null?
				util.antiSQLInspection(request.getParameter("dvkdId")):"");
		
		obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nhanhangId"))!= null?
			util.antiSQLInspection(request.getParameter("nhanhangId")):"");
		obj.setchungloaiId(util.antiSQLInspection(request.getParameter("chungloaiId"))!= null?
				util.antiSQLInspection(request.getParameter("chungloaiId")):"");
		
		
		String tuthang=request.getParameter("tuthang").length()< 2 ? ("0"+request.getParameter("tuthang")) :request.getParameter("tuthang") ;
		String toithang=request.getParameter("denthang").length()< 2 ? ("0"+request.getParameter("denthang")) :request.getParameter("denthang") ;
		obj.setFromMonth(tuthang);
		
		obj.setToMonth(toithang);
			obj.setToYear(request.getParameter("dennam"));
			obj.setFromYear(request.getParameter("tunam"));
		String type= request.getParameter("xemtheo");
		
		
		obj.settype(type);
		if(type.equals("1")){
			obj.settungay(obj.getFromYear()+"-"+ (obj.getFromMonth().length() >1?obj.getFromMonth():"0"+obj.getFromMonth()) +"-01");
			obj.setdenngay(obj.getToYear()+"-"+ (obj.getToMonth().length() >1?obj.getToMonth():"0"+obj.getToMonth()) +"-31");
		}
		
		String vat = util.antiSQLInspection(request.getParameter("vats"));
		if (vat.equals("1"))
			obj.setvat("1.1");
		else
			obj.setvat("1");
 
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
	 
		geso.traphaco.center.util.Utility utilcenter = new geso.traphaco.center.util.Utility();
		
		String sql ="";
		
		sql =" and kh.pk_seq in " + utilcenter.quyen_npp(obj.getuserId()) + " and kenh.pk_seq in  " + utilcenter.quyen_kenh(obj.getuserId()) + " and sp.pk_seq in " + utilcenter.quyen_sanpham(obj.getuserId()); 
		
		if (obj.getkenhId().length() > 0)
			sql = sql + " and kenh.pk_seq ='" + obj.getkenhId() + "'";
		if (obj.getvungId().length() > 0)
			sql = sql + " and v.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and dvkd.PK_SEQ ='" + obj.getdvkdId() + "'";
		if (obj.getnppId().length() > 0)
			sql = sql + " and kh.pk_seq ='" + obj.getnppId() + "'";
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and nh.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and cl.pk_seq ='" + obj.getchungloaiId()	+ "'";
		if(trangthai.length() > 0)
			sql = sql + " and DDH.trangthai = '" + trangthai + "'";
		
		//System.out.println("SQL la: " + sql + "\n");
		

		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpTheoDoiHopDong.jsp";
		
		//System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BaoCaoTheoDoiHopDong.xlsm");
			String query = setQuery(obj, sql); 
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
	        	obj.init();
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
			}
		}else{
		
		obj.init();
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		return;
		}
	}
	
	public String setQuery(IStockintransit obj, String sql)
	{
 
		
		String query=  "	SELECT DATA.MAHOPDONG, DATA.SOHOPDONG,DATA.SOLUONG AS SOLUONGHD,HD_DH.SOLUONG AS SOLUONGDH ,  " +  
					   "  	ISNULL(DATA.SOLUONG,0)- ISNULL(HD_DH.SOLUONG,0) AS SLCONLAI,NH.TEN AS NHANHANG,CL.TEN AS CHUNGLOAI,     SP.MA AS MASANPHAM,SP.TEN AS TENSANPHAM  " +  
					   "  	,SP.QUYCACH,KH.TEN AS TENKHACHHANG ,KH.MA AS MAKHACHHANG  ,DATA.TUNGAY,DATA.DENNGAY,    DVKD.DONVIKINHDOANH ,V.TEN AS VUNG,KV.TEN AS KHUVUC    " +  
					   "  	FROM	( " +  
					   "     " +  
					   "  		SELECT    HD.CONGTY_FK,HD.MAHOPDONG,HD.TUNGAY,HD.DENNGAY ,HD.PK_SEQ AS SOHOPDONG     , HD.KHACHHANG_FK,HD_SP.SOLUONG,  " +  
					   "  		HD_SP.DONGIA,HD_SP.SANPHAM_FK     FROM ERP_HOPDONG HD     INNER JOIN ERP_HOPDONG_SANPHAM HD_SP ON HD.PK_SEQ=HD_SP.HOPDONG_FK     " +  
					   "  		WHERE HD.TRANGTHAI=2  ";
					   if(obj.gettungay().length() > 0 ){
						   query += "	AND HD.NGAYTAO>='"+obj.gettungay()+"' " ;
					   }
					   if(obj.getdenngay().length() > 0 ){
						   query +=   "	AND   HD.NGAYTAO<='"+obj.getdenngay()+"' " ;
					   }
					 query +=
					   "  		) DATA LEFT JOIN  " +  
					   "  		 ( " +  
					   "  		SELECT    DDH.HOPDONG_FK, DDH_SP.SOLUONG     ,DDH_SP.DONGIA,DDH_SP.SANPHAM_FK   " +  
					   "  		FROM ERP_DONDATHANG DDH     INNER JOIN ERP_DONDATHANG_SP DDH_SP ON DDH.PK_SEQ=DDH_SP.DONDATHANG_FK       " +  
					   "  		INNER JOIN ERP_HOPDONG HD ON HD.PK_SEQ=DDH.HOPDONG_FK   " +  
					   "  		WHERE HD.TRANGTHAI=2 AND HD.NGAYTAO>='"+obj.gettungay()+"' AND   HD.NGAYTAO<='"+obj.getdenngay()+"' " +  
					   "  		) HD_DH ON DATA.SOHOPDONG=HD_DH.HOPDONG_FK AND DATA.SANPHAM_FK=HD_DH.SANPHAM_FK " +  
					   "  	INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=DATA.KHACHHANG_FK    INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DATA.SANPHAM_FK     " +  
					   "  	LEFT JOIN NHANHANG NH ON NH.PK_SEQ=SP.NHANHANG_FK    LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ=SP.CHUNGLOAI_FK    " +  
					   "  	LEFT JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ=SP.DVKD_FK    INNER JOIN ERP_CONGTY CT ON CT.PK_SEQ=DATA.CONGTY_FK    " +  
					   "  	LEFT JOIN KHUVUC KV ON KV.PK_SEQ=KH.KHUVUC_FK    " +
					   "	LEFT JOIN VUNG V ON KV.VUNG_FK=V.PK_SEQ " +
					   "	LEFT JOIN KENHBANHANG KENH ON KENH.PK_SEQ = KH.kenhbanhang_fk  " +
					   " WHERE 1=1 " + sql;
			
	    			 
	    			 
		 		System.out.println("Query1 Mua hang hang ngay: " + query);
	    
	    return query;   
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		 		 
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ERP_BaoCaoTheoDoiHD.xlsm");
		 
	 
				
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
	    
	    String tieude = "BÁO CÁO THEO DÕI HỢP ĐỒNG";
	    
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	            
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    
	    if(obj.gettype().trim().equals("0")){
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
	    }
	    else
	    {
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ tháng : " + obj.getFromMonth() + "" );
	    }
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B4"); 
	    if(obj.gettype().trim().equals("0")){
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
	    }
	    else{
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến tháng : " + obj.getToMonth() + "" );
	    }
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    		
		cell = cells.getCell("DA1");		cell.setValue("DonViKinhDoanh");			ReportAPI.setCellHeader(cell);///2
		cell = cells.getCell("DB1");		cell.setValue("Vung");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("DC1");		cell.setValue("KhuVuc");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("DD1");		cell.setValue("MaHopDong");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("DE1");		cell.setValue("TuNgay");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("DF1");		cell.setValue("DenNgay");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("DG1");		cell.setValue("MaKhachHang");					ReportAPI.setCellHeader(cell);//8
		cell = cells.getCell("DH1");		cell.setValue("TenKhachHang");				ReportAPI.setCellHeader(cell);//9
		cell = cells.getCell("DI1");		cell.setValue("Type");			ReportAPI.setCellHeader(cell);//15
		cell = cells.getCell("DJ1");		cell.setValue("NhanHang");				ReportAPI.setCellHeader(cell);//5
		cell = cells.getCell("DK1");		cell.setValue("ChungLoai");			ReportAPI.setCellHeader(cell);//6
		cell = cells.getCell("DL1");		cell.setValue("MaSanPham");			ReportAPI.setCellHeader(cell);//15
		cell = cells.getCell("DM1");		cell.setValue("TenSanPham");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("DN1");		cell.setValue("QuyCach");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("DO1");		cell.setValue("SoLuongHopDong");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("DP1");		cell.setValue("SoLuongDonHang");			ReportAPI.setCellHeader(cell);//16
		cell = cells.getCell("DQ1");		cell.setValue("SoLuongConLai");			ReportAPI.setCellHeader(cell);//16
	}
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		
		for(int i = 0;i < 7; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	
		
		ResultSet rs = db.get(query);
		int index = 2;
		
		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				while (rs.next())
				{					
					cell = cells.getCell("DA" + String.valueOf(index));		cell.setValue(rs.getString("DonViKinhDoanh"));	//Kenh ban hang  0	
					cell = cells.getCell("DB" + String.valueOf(index));		cell.setValue(rs.getString("vung"));	//Kenh ban hang  1	
					cell = cells.getCell("DC" + String.valueOf(index));		cell.setValue(rs.getString("khuvuc"));	//   4
					cell = cells.getCell("DD" + String.valueOf(index));		cell.setValue(rs.getString("Mahopdong"));	// 5
					cell = cells.getCell("DE" + String.valueOf(index));		cell.setValue(rs.getString("tungay"));	//Chung loai 7
					cell = cells.getCell("DF" + String.valueOf(index));		cell.setValue(rs.getString("denngay"));	//8
					cell = cells.getCell("DG" + String.valueOf(index));		cell.setValue(rs.getString("makhachhang"));//9
					cell = cells.getCell("DH" + String.valueOf(index));		cell.setValue(rs.getString("tenkhachhang")); //10
					cell = cells.getCell("DI" + String.valueOf(index));		cell.setValue("0"); //11
					cell = cells.getCell("DJ" + String.valueOf(index));		cell.setValue(rs.getString("nhanhang")); //12
					cell = cells.getCell("DK" + String.valueOf(index));		cell.setValue(rs.getString("chungloai")); //13
					cell = cells.getCell("DL" + String.valueOf(index));		cell.setValue(rs.getString("masanpham")); //13
					cell = cells.getCell("DM" + String.valueOf(index));		cell.setValue(rs.getString("tensanpham")); //13
					cell = cells.getCell("DN" + String.valueOf(index));		cell.setValue(rs.getString("quycach")); //13
					cell = cells.getCell("DO" + String.valueOf(index));		cell.setValue(rs.getDouble("soluonghd")); //14
					cell = cells.getCell("DP" + String.valueOf(index));		cell.setValue(rs.getDouble("SOLUONGDH")); //14
					cell = cells.getCell("DQ" + String.valueOf(index));		cell.setValue(rs.getDouble("SLCONLAI")); //14
					index++;					
				}

				if (rs != null)
					rs.close();
				
				if(db != null)
					db.shutDown();
			}
			catch(Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		else
		{
			return false;
		}		
		return true;
	}	
	

}
