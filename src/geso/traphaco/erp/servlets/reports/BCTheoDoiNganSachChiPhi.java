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
import java.net.Inet4Address;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

public class BCTheoDoiNganSachChiPhi extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public BCTheoDoiNganSachChiPhi() {
        super();
    }
    private Hashtable< Integer, String > htbValueCell (){
		Hashtable<Integer, String> htb=new Hashtable<Integer, String>();
		htb.put(1,"A");htb.put(2,"B");htb.put(3,"C");htb.put(4,"D");htb.put(5,"E");
		htb.put(6,"F");htb.put(7,"G");htb.put(8,"H");htb.put(9,"I");htb.put(10,"J");
		htb.put(11,"K");htb.put(12,"L");htb.put(13,"M");htb.put(14,"N");htb.put(15,"O");
		htb.put(16,"P");htb.put(17,"Q");htb.put(18,"R");htb.put(19,"S");htb.put(20,"T");
		htb.put(21,"U");htb.put(22,"V");htb.put(23,"W");htb.put(24,"X");htb.put(25,"Y");
		htb.put(26,"Z");
		
		
		return htb; 
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
		obj.InitErp();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiNganSachChiPhi.jsp";
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
		
		obj.setErpCongtyId(util.antiSQLInspection(request.getParameter("congtyid")));
		session.setAttribute("congtyId", obj.getErpCongtyId());
		
		
		obj.setToYear(util.antiSQLInspection(request.getParameter("nam")));
		
		obj.setFromMonth(util.antiSQLInspection(request.getParameter("tuthang")));
		obj.setToMonth(util.antiSQLInspection(request.getParameter("denthang")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		
		obj.setErpTienteId(util.antiSQLInspection(request.getParameter("tienteid")));
		obj.setErpDonViTHid(util.antiSQLInspection(request.getParameter("donvithid")));
		
		obj.setErpTaiKhoanKTId(util.antiSQLInspection(request.getParameter("taikhoanktid"))!=null?
				util.antiSQLInspection(request.getParameter("taikhoanktid")):"");

		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiNganSachChiPhi.jsp";
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCTheoDoiNganSachChiPhi.xlsm");
	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj, ""))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Xin loi khong co bao cao trong thoi gian nay");
				    writer.close();
				}
			} 
	        catch (Exception e) 
	        {
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				System.out.println(e.toString());
			}
		}
		
		
		
		obj.InitErp();
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		return;
	}
	

	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\TheoDoiNganSachChiPhi.xlsm");
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

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
	private void setStyleColorNormar(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("X1");
		Style style;	
		 style = cell1.getStyle();
		 style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	private void setStyleColorHeader(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("R1");
		Style style;	
		 style = cell1.getStyle();
       style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Hashtable<Integer, String> htb=this.htbValueCell();
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		Cell cell = null;
		
		cell = cells.getCell("B3");		cell.setValue(obj.getToYear());	
		String sql="select ten,masothue  from erp_congty where pk_seq="+obj.getErpCongtyId();
		 ResultSet rs=db.get(sql);
		 
		if(rs!=null){
			if(rs.next()){
				cell = cells.getCell("B2");		cell.setValue(rs.getString("ten"));
			
			}
			rs.close();
		}
		int tuthang=Integer.parseInt( obj.getFromMonth());
		int toithang=Integer.parseInt( obj.getToMonth());
		String[] mang=new String[toithang-tuthang+1];
		int nampre=Integer.parseInt( obj.getToYear())-1;
		//mang luu cac bien tong 
		double[] mangvalues=new double[toithang-tuthang+1];
		 String chuoingoac="";
			int j=0;
			for(int i=tuthang;i<=toithang;i++){
				String thang=((i+"").length() >1 ?i+"":"0"+i);
				if(i==tuthang){
				chuoingoac="["+obj.getToYear() +"-"+ thang +"]";
				}else{
					chuoingoac=chuoingoac +" ,["+obj.getToYear() +"-"+ thang +"]";
				}
				mang[j]=obj.getToYear() +"-"+ thang ;
				j++;
			}
			System.out.println("Chuoi Ngoac : "+chuoingoac);
		
			String tungay=obj.getToYear() +"-"+ (obj.getFromMonth().length() > 1?obj.getFromMonth():"0"+obj.getFromMonth())+"-01";
			String toingay=obj.getToYear() +"-"+ (obj.getToMonth().length() > 1?obj.getToMonth():"0"+obj.getToMonth())+"-31";
			
			String tungaypre=nampre +"-"+ (obj.getFromMonth().length() > 1?obj.getFromMonth():"0"+obj.getFromMonth())+"-01";
			String toingaypre=nampre +"-"+ (obj.getToMonth().length() > 1?obj.getToMonth():"0"+obj.getToMonth())+"-31";

		
		
				sql="SELECT	LNS.PK_SEQ AS LNSID, LNS_CHIPHI.DONVITHUCHIEN_FK AS DVTHID, "+										
                " LNS.NAM, NHOMCHIPHI.PK_SEQ AS CPID, NHOMCHIPHI.TEN AS CPTEN ,NAMTRUOC.CHIPHI AS CPNAMTRUOC "+ 							 			
                " ,CHIPHI.*, "+										
                " DUTOAN AS NGANSACH "+										
                " FROM ERP_LAPNGANSACH_CHIPHI LNS_CHIPHI "+											
                " INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_CHIPHI.LAPNGANSACH_FK "+											
                " INNER JOIN ERP_NHOMCHIPHI NHOMCHIPHI ON NHOMCHIPHI.PK_SEQ = LNS_CHIPHI.CHIPHI_FK "+
                " LEFT JOIN (" +
                " 	SELECT CHIPHI_FK AS CPID, SUM(SOLUONGNHAN*DONGIA) AS CHIPHI "+										
                " FROM ERP_NHANHANG_SANPHAM NH_SP "+										
                " INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ = NH_SP.NHANHANG_FK "+										
                " WHERE NH_SP.CHIPHI_FK IS NOT NULL AND NH.TRANGTHAI in (1,2)   "+
                " AND  NH.NGAYNHAN >='"+tungaypre+"' AND NH.NGAYNHAN <='"+toingaypre+"' "+	
                " GROUP BY NH_SP.CHIPHI_FK	 " +
                ") AS NAMTRUOC ON NAMTRUOC.CPID= NHOMCHIPHI.PK_SEQ" +
                " LEFT JOIN ( "+
                " SELECT  CHIPHI_FK AS CPID, SUM(SOLUONGNHAN*DONGIA) AS CHIPHI ,SUBSTRING(NH.NGAYNHAN, 0, 8) AS THANG "+ 										
                " FROM ERP_NHANHANG_SANPHAM NH_SP										 "+
                " INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ = NH_SP.NHANHANG_FK "+										
                " WHERE NH_SP.CHIPHI_FK IS NOT NULL AND NH.TRANGTHAI in (1,2) 		 "+
                " AND  NH.NGAYNHAN >='"+tungay+"' AND NH.NGAYNHAN <='"+toingay+"' "+							
                " GROUP BY NH_SP.CHIPHI_FK,SUBSTRING(NH.NGAYNHAN, 0, 8) "+ 
                " ) P "+
                " PIVOT  "+
                " (  "+
                " sum(CHIPHI) "+ 
                " FOR THANG IN  "+
                " ("+chuoingoac+")  "+
                " ) CHIPHI ON CHIPHI.CPID= NHOMCHIPHI.PK_SEQ "+ 
                " WHERE 1=1 AND LNS.NAM = '"+obj.getToYear()+"'";
				if(obj.getErpCongtyId().length()>0){
					sql=sql +" and LNS.congty_fk="+ obj.getErpCongtyId();
				}
				if(obj.getErpDonViTHId().length()>0){
					sql=sql +" and	LNS_CHIPHI.DONVITHUCHIEN_FK = '"+obj.getErpDonViTHId()+"'";
				}
				
		 System.out.println(sql);
		 rs=db.get(sql);
		 int index = 7;
		 // set header
		 for(int i=0;i<mang.length;i++){
			 cell = cells.getCell(htb.get(3+i)+String.valueOf(index));
			 cell.setValue(mang[i]);	
			 this.setStyleColorHeader(cells, cell);
		 }
		 
		 j=mang.length+3;
	
		 cell = cells.getCell(htb.get(j)+String.valueOf(index));
		 cell.setValue("Lũy kế");	
		 this.setStyleColorHeader(cells, cell);
		 j++;
		 cell = cells.getCell(htb.get(j)+String.valueOf(index));
		 cell.setValue("Ngân sách");	
		 this.setStyleColorHeader(cells, cell);
		 j++;
		
		 cell = cells.getCell(htb.get(j)+String.valueOf(index));
		 cell.setValue("% Đạt");	
		 this.setStyleColorHeader(cells, cell);
		 
		 j++;
		 cell = cells.getCell(htb.get(j)+String.valueOf(index));
		 cell.setValue("Cùng kỳ");	
		 this.setStyleColorHeader(cells, cell);
		 
		 j++;
		 cell = cells.getCell(htb.get(j)+String.valueOf(index));
		 cell.setValue("% Tăng/Giảm");	
		 this.setStyleColorHeader(cells, cell);
		 index++;
		 double totalngansach=0;
		 double totalnamtruoc=0;
		 if (rs != null) 
		 {
			try 
			{
				
				while (rs.next())
				{		
					 cell = cells.getCell("A"+String.valueOf(index));
					 cell.setValue(index-7);	
					 this.setStyleColorNormar(cells, cell);
					 cell = cells.getCell("B"+String.valueOf(index));
					 cell.setValue(rs.getString("cpten"));	
					 this.setStyleColorNormar(cells, cell);
					  
					double totalchiphi=0;
					 for(int i=0;i<mang.length;i++){
						 mangvalues[i]=mangvalues[i]+rs.getDouble(mang[i]);
						 
						 cell = cells.getCell(htb.get(3+i)+String.valueOf(index));
						 cell.setValue(rs.getDouble(mang[i]));	
						 totalchiphi=totalchiphi+rs.getDouble(mang[i]);
						 this.setStyleColorNormar(cells, cell);
					 }
					 
					 double ngansach=rs.getDouble("ngansach");
					 totalngansach=totalngansach+ngansach;
					 
					 double phantramdat=0;
					 if(ngansach >0){
					  phantramdat=totalchiphi*100 /ngansach;
					 }
					 double cungky=rs.getDouble("CPNAMTRUOC");
					 totalnamtruoc=totalnamtruoc+cungky;
					 
					 double Phantramtang_giam=0;
					 if(cungky >0){
					  Phantramtang_giam=totalchiphi*100/cungky;
					 }
					  j=mang.length+3;
						
					 cell = cells.getCell(htb.get(j)+String.valueOf(index));
					 cell.setValue(totalchiphi);	
					 this.setStyleColorNormar(cells, cell);
					 j++;
					 cell = cells.getCell(htb.get(j)+String.valueOf(index));
					 cell.setValue(ngansach);	
					 this.setStyleColorNormar(cells, cell);
					 j++;
					
					 cell = cells.getCell(htb.get(j)+String.valueOf(index));
					 cell.setValue(phantramdat);	
					 this.setStyleColorNormar(cells, cell);
					 
					 j++;
					 cell = cells.getCell(htb.get(j)+String.valueOf(index));
					 cell.setValue(cungky);	
					 this.setStyleColorNormar(cells, cell);
					 
					 j++;
					 cell = cells.getCell(htb.get(j)+String.valueOf(index));
					 cell.setValue(Phantramtang_giam);	
					 this.setStyleColorNormar(cells, cell);
						
				index++;
									
				}
				// total
				 cell = cells.getCell("A"+String.valueOf(index));
				 cell.setValue("");	
				 this.setStyleColorNormar(cells, cell);
				 cell = cells.getCell("B"+String.valueOf(index));
				 cell.setValue("Tổng");	
				 this.setStyleColorNormar(cells, cell);
				 double totalchiphi=0;
				 for(int i=0;i<mang.length;i++){
					
					 
					 cell = cells.getCell(htb.get(3+i)+String.valueOf(index));
					 cell.setValue(mangvalues[i]);	
					 totalchiphi=totalchiphi+mangvalues[i];
					 
					 this.setStyleColorNormar(cells, cell);
				 }
				
				 double phantramdat=0;
				 if(totalngansach >0){
				  phantramdat=totalchiphi*100 /totalngansach;
				 }
				
				 double Phantramtang_giam=0;
				 if(totalnamtruoc >0){
				  Phantramtang_giam=totalchiphi*100/totalnamtruoc;
				 }
				  j=mang.length+3;
					
				 cell = cells.getCell(htb.get(j)+String.valueOf(index));
				 cell.setValue(totalchiphi);	
				 this.setStyleColorNormar(cells, cell);
				 j++;
				 cell = cells.getCell(htb.get(j)+String.valueOf(index));
				 cell.setValue(totalngansach);	
				 this.setStyleColorNormar(cells, cell);
				 j++;
				
				 cell = cells.getCell(htb.get(j)+String.valueOf(index));
				 cell.setValue(phantramdat);	
				 this.setStyleColorNormar(cells, cell);
				 
				 j++;
				 cell = cells.getCell(htb.get(j)+String.valueOf(index));
				 cell.setValue(totalnamtruoc);	
				 this.setStyleColorNormar(cells, cell);
				 
				 j++;
				 cell = cells.getCell(htb.get(j)+String.valueOf(index));
				 cell.setValue(Phantramtang_giam);	
				 this.setStyleColorNormar(cells, cell);
				 
				if (rs != null){
					rs.close();
				}

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
		if(db != null)
			db.shutDown();
		return true;
	}	
	

}
