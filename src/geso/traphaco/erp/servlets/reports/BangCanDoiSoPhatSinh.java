package geso.traphaco.erp.servlets.reports;

import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BangCanDoiSoPhatSinh extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public BangCanDoiSoPhatSinh() {
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
		obj.InitErp();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangCanDoiSoPhatSinh.jsp";
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
		
		
		obj.setYear(util.antiSQLInspection(request.getParameter("nam")));
		
		obj.setMonth(util.antiSQLInspection(request.getParameter("thang")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		
		obj.setErpTienteId(util.antiSQLInspection(request.getParameter("tienteid")));
		
		obj.setErpLoaiKhoanKTId(util.antiSQLInspection(request.getParameter("loaitkid")));
		
		
		obj.setErpTaiKhoanKTId(util.antiSQLInspection(request.getParameter("taikhoanktid"))!=null?
				util.antiSQLInspection(request.getParameter("taikhoanktid")):"");

		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangCanDoiSoPhatSinh.jsp";
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BangCanDoiSoPhatSinh.xlsm");
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
		
		
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BoBaoCaoTaiChinh.xlsx");
		
				
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
	
	private void setStyleColorGray(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("Y1");
		Style style;	
		 style = cell1.getStyle();
        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	private void setStyleColorRed(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("Z1");
		Style style;	
		 style = cell1.getStyle();
       //style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
       // style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
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

	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet("BCDSPS");
		
		Cells cells = worksheet.getCells();	
		

		Cell cell = null;
		
		 
		cell = cells.getCell("A6");		cell.setValue(obj.getYear()+"-"+ (obj.getMonth().length() >1 ?obj.getMonth():"0"+obj.getMonth()));	
		 
		 
		
		int thang=Integer.parseInt(obj.getMonth());
		int namdk=0;
		int thangdk=0;
		
		if(thang == 1)
		{
			thangdk = 12;
			namdk = Integer.parseInt(obj.getYear())-1;
		}
		else
		{
			thangdk = thang - 1;
			namdk=Integer.parseInt(obj.getYear());
		}
		 
	/*	String sql=" select sohieutaikhoan,tentaikhoan,isnull(dauky.giatricovnd,0) as codk,isnull( dauky.giatrinovnd,0) as nodk , "+
		" isnull(trongky.giatricovnd,0) as cotk,isnull(trongky.giatrinovnd,0) as notk "+
		" from erp_taikhoankt tk   "+
		" left join  "+
		" (  "+
		" select taikhoankt_fk,thang,nam,giatricovnd,giatrinovnd  from erp_taikhoan_noco a "+
		" inner join erp_taikhoankt tkkt on tkkt.pk_seq=a.taikhoankt_fk "+
		" where nguyente_fk=100000  and nam="+namdk+" and thang= "+thangdk+
		" ) as dauky on dauky.taikhoankt_fk=tk.pk_seq "+
		" left join "+ 
		" ( "+
		" select taikhoankt_fk,thang,nam,giatricovnd,giatrinovnd from erp_taikhoan_noco a "+
		" inner join erp_taikhoankt tkkt on tkkt.pk_seq=a.taikhoankt_fk "+
		" where nguyente_fk=100000  and nam="+obj.getYear()+" and thang= "+obj.getMonth()+
		" ) as trongky on trongky.taikhoankt_fk=tk.pk_seq where 1=1 and congty_fk="+obj.getErpCongtyId();
		*/
		
		/* query = "select top(1) NAM as namMax, THANGKS as thangMax from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc ";
		 System.out.println("1.Khoi tao thang: " + query);
		 ResultSet rs = db.get(query);
		
		 String thangKsMax = "";
		 String namKsMax = "";
		
		 if(rs != null)
		 {
			while(rs.next())
			{
				thangKsMax = rs.getString("thangMax");
				namKsMax = rs.getString("namMax"); 
			}
			rs.close();
		 }
		 
		 if(Integer.parseInt(obj.getMonth()) == Integer.parseInt(thangKsMax) )
		 {
			 thangKsMax = Integer.toString(Integer.parseInt(thangKsMax) - 1);
		 }*/
		
		String sql=" select tktmp.ma as sohieutaikhoan,tktmp.ten as tentaikhoan,isnull(dauky.giatricovnd,0) as codk, "+
					" isnull( dauky.giatrinovnd,0) as nodk ,  isnull(trongky.giatricovnd,0) as cotk "+
					" ,isnull(trongky.giatrinovnd,0) as notk  "+
					" from "+
					" taikhoankttmp tktmp  "+
					" left join   "+
					" erp_taikhoankt tk  on tk.sohieutaikhoan=tktmp.ma and congty_fk=  "+obj.getErpCongtyId()+
					"  left join   "+
					" (   select taikhoankt_fk, thang, nam, a.giatricovnd, a.giatrinovnd  "+
						"  from erp_taikhoan_noco a  inner join erp_taikhoankt tkkt  "+
							" on tkkt.pk_seq=a.taikhoankt_fk  where nguyente_fk=100000   "+
							" and nam = " + namdk + " and thang= " + thangdk + " " +
					 ") as dauky on dauky.taikhoankt_fk=tk.pk_seq   "+
					" left join  (  select taikhoankt_fk,thang,nam,a.giatricovnd,a.giatrinovnd from erp_taikhoan_noco a  "+
					"  inner join erp_taikhoankt tkkt on tkkt.pk_seq=a.taikhoankt_fk  where nguyente_fk=100000  "+
					" and nam="+obj.getYear()+" and thang= "+obj.getMonth()+" ) as trongky on trongky.taikhoankt_fk=tk.pk_seq where 1=1 ";
		
		if(obj.getErpLoaiTaiKhoanKTId().length() >0){
			sql=sql +" and  tk.loaitaikhoan_fk="+obj.getErpLoaiTaiKhoanKTId();
		}
		
		 System.out.println(sql);
		 ResultSet rs = db.get(sql);
		 int index = 11;
		 
		 double totalnodk=0;
		 double totalcodk=0;
		 
		 double totalnotk=0;
		 double totalcotk=0;
		 
		 double totalnock=0;
		 double totalcock=0;
		 
		 if (rs != null) 
		 {
			try 
			{
				
				while (rs.next())
				{		
					
					cell = cells.getCell("A"+String.valueOf(index));
					cell.setValue(rs.getString("sohieutaikhoan"));	
					cell = cells.getCell("B"+String.valueOf(index));
					cell.setValue(rs.getString("tentaikhoan"));	
					
					System.out.println("_____No dau ky: " + rs.getDouble("nodk") + "____" + " Co dau ky:  " + rs.getDouble("codk"));
					
					if(rs.getDouble("nodk") - rs.getDouble("codk") < 0 )
					{
						cell = cells.getCell("C"+String.valueOf(index));
						cell.setValue(0);	
						
						cell = cells.getCell("D"+String.valueOf(index));
						cell.setValue(Math.abs(rs.getDouble("nodk")-rs.getDouble("codk")));	
						
						totalcodk += Math.abs(rs.getDouble("nodk")- rs.getDouble("codk"));
					}
					else
					{
						cell = cells.getCell("C"+String.valueOf(index));
						cell.setValue( Math.abs(rs.getDouble("nodk") - rs.getDouble("codk")) ); 
						
						cell = cells.getCell("D"+String.valueOf(index));
						//cell.setValue( Math.abs(rs.getDouble("nodk") - rs.getDouble("codk")) );
						cell.setValue(0);
						
						totalnodk += Math.abs(rs.getDouble("nodk")-rs.getDouble("codk"));
					}
					
					cell = cells.getCell("E" + String.valueOf(index));
					cell.setValue(rs.getDouble("notk"));
					totalnotk +=  rs.getDouble("notk");
					
					cell = cells.getCell("F" + String.valueOf(index));
					cell.setValue(rs.getDouble("cotk"));	
					totalcotk += rs.getDouble("cotk");

					double no_cocuoiky = ( rs.getDouble("nodk") + rs.getDouble("notk") ) - ( rs.getDouble("codk") + rs.getDouble("cotk") );
					if(no_cocuoiky < 0 )
					{
						cell = cells.getCell("G" + String.valueOf(index));
						cell.setValue(0);
						
						cell = cells.getCell("H" + String.valueOf(index));
						cell.setValue(Math.abs(no_cocuoiky));	
						
						totalnock += Math.abs(no_cocuoiky);
					}
					else
					{
						cell = cells.getCell("G" + String.valueOf(index));
						cell.setValue(Math.abs(no_cocuoiky));	

						cell = cells.getCell("H" + String.valueOf(index));
						cell.setValue(0);	
						
						totalcock +=  Math.abs(no_cocuoiky);
					}
					
					index++;
					
				}

				if (rs != null){
					rs.close();
				}
				
				index++;
				cell = cells.getCell("A"+String.valueOf(index));
				cell.setValue("");	
				this.setStyleColorNormar(cells, cell);
				
				cell = cells.getCell("B"+String.valueOf(index));
				cell.setValue("Tổng");	
				this.setStyleColorNormar(cells, cell);
				
				cell = cells.getCell("C"+String.valueOf(index));
				cell.setValue(totalcodk);	
				this.setStyleColorNormar(cells, cell);
				
				cell = cells.getCell("D"+String.valueOf(index));
				cell.setValue(totalnodk);	
				this.setStyleColorNormar(cells, cell);
				
				cell = cells.getCell("E"+String.valueOf(index));
				cell.setValue(totalcotk);	
				this.setStyleColorNormar(cells, cell);
				
				cell = cells.getCell("F"+String.valueOf(index));
				cell.setValue(totalnotk);	
				this.setStyleColorNormar(cells, cell);
				
				cell = cells.getCell("G"+String.valueOf(index));
				cell.setValue(totalcock);	
				this.setStyleColorNormar(cells, cell);
				cell = cells.getCell("H"+String.valueOf(index));
				cell.setValue(totalnock);	
				this.setStyleColorNormar(cells, cell);
				
				
				
				     sql="select ten,masothue  from erp_congty where pk_seq="+obj.getErpCongtyId();
				     rs=db.get(sql);
				     worksheet = worksheets.getSheet("P&L");					
					 cells = worksheet.getCells();
					 String masothue="";
					 String ten="";
						if(rs!=null){
							if(rs.next()){
								masothue=rs.getString("masothue");
								ten=rs.getString("ten");
								cell = cells.getCell("E5");		cell.setValue(masothue);
								cell = cells.getCell("E6");		cell.setValue(ten );
								cell = cells.getCell("F4");		cell.setValue("Kỳ : "+obj.getYear()+"-"+ (obj.getMonth().length() >1 ?obj.getMonth():"0"+obj.getMonth()));	

							}
							rs.close();
						}
						worksheet = worksheets.getSheet("BS");					
						 cells = worksheet.getCells();
						 cell = cells.getCell("H5");		cell.setValue(masothue);
							cell = cells.getCell("H6");		cell.setValue(ten );
							cell = cells.getCell("C4");		cell.setValue("Kỳ: "+obj.getYear()+"-"+ (obj.getMonth().length() >1 ?obj.getMonth():"0"+obj.getMonth()));	

							worksheet = worksheets.getSheet("CS");					
							 	cells = worksheet.getCells();
							 	cell = cells.getCell("B4");		cell.setValue("Công ty : "+ten);
								cell = cells.getCell("B5");		cell.setValue("Mã số Thuế: "+masothue );
								cell = cells.getCell("B6");		cell.setValue(obj.getYear()+"-"+ (obj.getMonth().length() >1 ?obj.getMonth():"0"+obj.getMonth()));	
				
								
								worksheet = worksheets.getSheet("LCTTGT");					
								cells = worksheet.getCells();
								cell = cells.getCell("H6");		cell.setValue(masothue);
								cell = cells.getCell("H7");		cell.setValue(ten );
								cell = cells.getCell("E4");		cell.setValue("Kỳ :"+obj.getYear()+"-"+ (obj.getMonth().length() >1 ?obj.getMonth():"0"+obj.getMonth()));	
								
								worksheet = worksheets.getSheet("LCTTTT");					
								cells = worksheet.getCells();
								cell = cells.getCell("H6");		cell.setValue(masothue);
								cell = cells.getCell("H7");		cell.setValue(ten );
								cell = cells.getCell("F4");		cell.setValue("Kỳ :"+obj.getYear()+"-"+ (obj.getMonth().length() >1 ?obj.getMonth():"0"+obj.getMonth()));	

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
