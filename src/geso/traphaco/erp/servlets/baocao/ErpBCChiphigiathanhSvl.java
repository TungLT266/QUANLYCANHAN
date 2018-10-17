package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.baocao.IBaocao;
import geso.traphaco.erp.beans.baocao.imp.Baocao;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpBCChiphigiathanhSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpBCChiphigiathanhSvl() {
        super();
    } 
    
    NumberFormat formatter = new DecimalFormat("#,###,###");
    NumberFormat formatter2 = new DecimalFormat("#,###,###.###");
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBaocao obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String congtyId = (String)session.getAttribute("congtyId");
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new Baocao();
	    obj.setUserId(userId);
	    obj.setCongtyId(congtyId);
	    obj.createRsBC_GiaThanh();
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiPhiGiaThanh.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBaocao obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    String userId = request.getParameter("userId");
	    String action = request.getParameter("action");
		if (action == null){
			action = "";
		}
		
	    obj = new Baocao();
	    obj.setUserId(userId);
	    
	    String congtyId = (String)session.getAttribute("congtyId");
		obj.setCongtyId(congtyId);
		
	    String nam = request.getParameter("nam");
	    if(nam == null)
	    	nam = "";
	    obj.setYear(nam);
	    
	    String thang = request.getParameter("thang");
	    if(thang == null)
	    	thang = "";
	    obj.setMonth(thang);
	    
	    String sanphamId = request.getParameter("sanphamId");
	    if(sanphamId == null)
	    	sanphamId = "";
	    obj.setSanPhamIds(sanphamId); 
	    
	    String lsxId = request.getParameter("lsxId");
	    if(lsxId == null)
	    	lsxId = "";
	    obj.setLsxIds(lsxId);
	    
	    obj.createRsBC_GiaThanh();
		session.setAttribute("obj", obj);
	    
		if( action.equals("taobaocao") )
		{
	    	try 
	    	{	
	    		OutputStream out = response.getOutputStream(); 
	    		
				response.setContentType("application/xlsm");
	    		response.setHeader("Content-Disposition", "attachment; filename=BCChiPhiGiaThanh.xlsm");
	
				TongHopChiPhiSX(out, obj);
			} 
	    	catch (Exception e) 
	    	{ 
	    		e.printStackTrace();
	    		System.out.println("Exception: " + e.getMessage()); 
	    	}
		}
		else
		{
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiPhiGiaThanh.jsp";
			response.sendRedirect(nextJSP);
		}
	    
	}
	
	private void TongHopChiPhiSX(OutputStream out, IBaocao obj) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCChiPhiGiaThanh.xlsm");

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		BCTongHopChiPhiSX(workbook, obj);

		workbook.save(out);
		fstream.close();
		
    }

	private void BCTongHopChiPhiSX(Workbook workbook, IBaocao obj) 
	{ 
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    //worksheet.setName("Sheet1");
	    
	    Cells cells = worksheet.getCells();
		
	    Style style;
	    Font font = new Font();
	    font.setName("Times New Roman");
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);

	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 
	    
	    //create data
	    dbutils db = new dbutils();

	    String query = "Select ma, ten, 0 as SOLUONGSX, 0 as SOLUONGTT " +
					   "From ERP_SANPHAM  " +
					   "where pk_seq = '" + obj.getSanPhamIds() + "' ";
	    
	    if( obj.getLsxIds().trim().length() > 0 )
	    {
	    	query = "select b.MA, b.TEN, a.SOLUONG as SOLUONGSX,  "+
	    			"	(  "+
	    			"		SELECT  sum( NKSP.SOLUONGNHAN )      "+
	    			"		FROM ERP_NHANHANG NK           "+
	    			"				INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHANHANG_FK  "+
	    			"		WHERE  NK.lenhsanxuat_fk = a.LENHSANXUAT_FK  AND NK.TRANGTHAI = 1         	 "+
	    			"	 ) as SOLUONGTT "+
	    			"from ERP_LENHSANXUAT_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ "+
	    			"where a.LENHSANXUAT_FK = '" + obj.getLsxIds() + "' ";
	    }
	    
	    System.out.println(":::: LAY SAN PHAM: " + query);
	    ResultSet spRs = db.get(query);
	    String maSP = "";
	    String tenSP = "";
	    double SOLUONGSX = 0;
	    double SOLUONGTT = 0;
	    if( spRs != null )
	    {
	    	try 
	    	{
				if( spRs.next() )
				{
					maSP = spRs.getString("ma");
					tenSP = spRs.getString("ten");
					SOLUONGSX = spRs.getDouble("SOLUONGSX");
					SOLUONGTT = spRs.getDouble("SOLUONGTT");
				}
				spRs.close();
			} 
	    	catch (Exception e) { }
	    }
	    
	    cell = cells.getCell("C3");
	    if( obj.getLsxIds().trim().length() <= 0 )
	    	cell.setValue( "Lệnh SX số:……………………………… Số lượng KH: ………………. Số lượng Thực tế:  ………………." );
	    else
	    	cell.setValue( "Lệnh SX số: " + obj.getLsxIds() + " - Số lượng KH: " + formatter2.format( SOLUONGSX ) + " - Số lượng Thực tế: " + formatter2.format( SOLUONGTT ) );
	    
	    cell = cells.getCell("C4");
	    cell.setValue( "Mã sản phẩm: " + maSP + " - Tên Sản phẩm: " + tenSP );
	    
	    cell = cells.getCell("C5");
	    cell.setValue( "THÁNG " + obj.getMonth() + " - NĂM " + obj.getYear() );
	    
	    if( obj.getLsxIds().trim().length() <= 0 )
	    	query = "select * from dbo.ufn_chiphi_giathanh( " + obj.getCongtyId() + ", " + obj.getMonth() + ", " + obj.getYear() + ", " + obj.getSanPhamIds() + ", null ) order by stt asc, ma asc ";
	    else
	    	query = "select * from dbo.ufn_chiphi_giathanh( " + obj.getCongtyId() + ", " + obj.getMonth() + ", " + obj.getYear() + ", " + obj.getSanPhamIds() + ", " + obj.getLsxIds() + " ) order by stt asc, ma asc ";
	    
	    System.out.println(":: LAY CHI PHI: " + query);
	    ResultSet rsChiphi = db.get(query);
	    
		try 
		{
			int rowIndex = 8;
			int colIndex = 1;
			
			Style B1Style = cells.getCell("B1").getStyle();
			B1Style.setTextWrapped(true);
			
			Style E1Style = cells.getCell("E1").getStyle();
			E1Style.setTextWrapped(true);
			
			Style F1Style = cells.getCell("F1").getStyle();
			F1Style.setTextWrapped(true);
			
			if(rsChiphi != null)
			{
				while( rsChiphi.next() )
				{
					//int stt = rsChiphi.getInt("stt");
					String ma = rsChiphi.getString("ma");
					String ten = rsChiphi.getString("ten");
					String donvi = rsChiphi.getString("donvi");
					
					double soluong_dinhmuc = rsChiphi.getDouble("soluong_dinhmuc");
					double tonggiatri_dinhmuc = rsChiphi.getDouble("tonggiatri_dinhmuc");
					
					double soluong = rsChiphi.getDouble("soluong");
					double tonggiatri = rsChiphi.getDouble("tonggiatri");
					
					double soluong_chenhlech = soluong - soluong_dinhmuc;
					double tonggiatri_chenhlech = tonggiatri - tonggiatri_dinhmuc;
					
					cell = cells.getCell( rowIndex, colIndex );     cell.setStyle(B1Style); cell.setValue( ma );
					cell = cells.getCell( rowIndex, colIndex + 1 ); cell.setStyle(B1Style); cell.setValue( ten );
					cell = cells.getCell( rowIndex, colIndex + 2 ); cell.setStyle(B1Style); cell.setValue( donvi );
					
					cell = cells.getCell( rowIndex, colIndex + 3 ); cell.setStyle(E1Style); cell.setValue( soluong_dinhmuc );
					cell = cells.getCell( rowIndex, colIndex + 4 ); cell.setStyle(F1Style); cell.setValue( tonggiatri_dinhmuc );
					
					cell = cells.getCell( rowIndex, colIndex + 5 ); cell.setStyle(E1Style); cell.setValue( soluong );
					cell = cells.getCell( rowIndex, colIndex + 6 ); cell.setStyle(F1Style); cell.setValue( tonggiatri );
					
					cell = cells.getCell( rowIndex, colIndex + 7 ); cell.setStyle(E1Style); cell.setValue( soluong_chenhlech );
					cell = cells.getCell( rowIndex, colIndex + 8 ); cell.setStyle(F1Style); cell.setValue( tonggiatri_chenhlech );
						
					rowIndex ++;
				}
				rsChiphi.close();				
			}		
		}
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
	    
	    db.shutDown();	    
	}

	

}
