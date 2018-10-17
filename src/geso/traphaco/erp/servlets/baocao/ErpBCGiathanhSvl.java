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
import java.util.Hashtable;

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

public class ErpBCGiathanhSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpBCGiathanhSvl() {
        super();
    } 
    
    NumberFormat formatter = new DecimalFormat("#,###,###");
    NumberFormat formatter2 = new DecimalFormat("#,###,###.#######");
    
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
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCGiaThanh.jsp";
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
	    
	    obj.createRsBC_GiaThanh();
		session.setAttribute("obj", obj);
	    
		if( action.equals("taobaocao") )
		{
	    	try 
	    	{	
	    		OutputStream out = response.getOutputStream(); 
	    		
				response.setContentType("application/xlsm");
	    		response.setHeader("Content-Disposition", "attachment; filename=BCGiaThanh.xlsm");
	
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
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCGiaThanh.jsp";
			response.sendRedirect(nextJSP);
		} 
	}
	
	private void TongHopChiPhiSX(OutputStream out, IBaocao obj) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCGiaThanh.xlsm");

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

	    cell = cells.getCell("C3");
	    cell.setValue( "THÁNG " + obj.getMonth() + " - NĂM " + obj.getYear() );
	    
	    String query = "";
	    
	    //Lấy số lượng + giá trị sản xuất trong tháng
	    if( obj.getSanPhamIds().trim().length() <= 0 )
	    {
		    query = " select idthanhpham, mathanhpham, tenthanhpham, donvi, soluong, tonggiatri, soluong_dinhmuc, tonggiatri_dinhmuc " + 
		    		" from dbo.ufn_chiphi_giathanh_sxtrongthang( " + obj.getCongtyId() + ", " + obj.getMonth() + ", " + obj.getYear() + ", null ) ";
	    }
	    else
	    {
	    	query = " select idthanhpham, mathanhpham, tenthanhpham, donvi, soluong, tonggiatri, soluong_dinhmuc, tonggiatri_dinhmuc " + 
		    		" from dbo.ufn_chiphi_giathanh_sxtrongthang( " + obj.getCongtyId() + ", " + obj.getMonth() + ", " + obj.getYear() + ", " + obj.getSanPhamIds() + " ) ";
	    }
	    
	    System.out.println("::: THANH PHAM SX: " + query);
	    ResultSet rsSX = db.get(query);
	    Hashtable<String, String> thanhphamSX = new Hashtable<String, String>();
	    if( rsSX != null )
	    {
	    	try 
	    	{
				while( rsSX.next() )
				{
					String idthanhpham = rsSX.getString("idthanhpham");
					String mathanhpham = rsSX.getString("mathanhpham");
					String tenthanhpham = rsSX.getString("tenthanhpham");
					String donvi = rsSX.getString("donvi");
					
					String soluong = rsSX.getString("soluong");
					String tonggiatri = rsSX.getString("tonggiatri");
					
					String soluong_dinhmuc = rsSX.getString("soluong_dinhmuc");
					String tonggiatri_dinhmuc = rsSX.getString("tonggiatri_dinhmuc");
					
					thanhphamSX.put(idthanhpham, mathanhpham + "__" +  tenthanhpham + "__" + donvi + "__" + soluong_dinhmuc + "__" + tonggiatri_dinhmuc + "__" + soluong + "__" + tonggiatri   );	
				}
				rsSX.close();
			} 
	    	catch (Exception e) { }
	    }
	    
	    
	    if( obj.getSanPhamIds().trim().length() <= 0 )
	    	query = "select * from dbo.ufn_chiphi_giathanh( " + obj.getCongtyId() + ", " + obj.getMonth() + ", " + obj.getYear() + ", null, null ) A inner join ERP_SANPHAM B on A.idthanhpham = B.PK_SEQ order by B.ten, A.stt, A.ten asc ";
	    else
	    	query = "select * from dbo.ufn_chiphi_giathanh( " + obj.getCongtyId() + ", " + obj.getMonth() + ", " + obj.getYear() + ", " + obj.getSanPhamIds() + ", null ) A inner join ERP_SANPHAM B on A.idthanhpham = B.PK_SEQ order by B.ten, A.stt, A.ten asc ";
	    
	    System.out.println(":: LAY CHI PHI: " + query);
	    ResultSet rsChiphi = db.get(query);
	    
		try 
		{
			int rowIndex = 6;
			int colIndex = 1;
			
			Style D1Style = cells.getCell("D1").getStyle();
			D1Style.setTextWrapped(true);
			
			Style G1Style = cells.getCell("G1").getStyle();
			G1Style.setTextWrapped(true);
			
			Style H1Style = cells.getCell("H1").getStyle();
			H1Style.setTextWrapped(true);
			
			String idThanhpham = "";
			
			if(rsChiphi != null)
			{
				while( rsChiphi.next() )
				{
					if( !rsChiphi.getString("idthanhpham").equals(idThanhpham) )
					{
						idThanhpham = rsChiphi.getString("idthanhpham");
						String values = "";
						if( thanhphamSX.get(idThanhpham) != null )
							values = thanhphamSX.get(idThanhpham);
						
						if( values.trim().length() > 0 )
						{
							//mathanhpham + "__" +  tenthanhpham + "__" + donvi + "__" + soluong + "__" + tonggiatri
							String[] data = values.split("__");
							double tonggiatri_chenhlech = Double.parseDouble( data[6] ) - Double.parseDouble( data[4] ) ;
							
							cell = cells.getCell( rowIndex, colIndex );     cell.setStyle(D1Style); cell.setValue( data[0] );
							cell = cells.getCell( rowIndex, colIndex + 1 ); cell.setStyle(D1Style); cell.setValue( data[1] );
							cell = cells.getCell( rowIndex, colIndex + 2 );     cell.setStyle(D1Style); cell.setValue( "" );
							cell = cells.getCell( rowIndex, colIndex + 3 ); cell.setStyle(D1Style); cell.setValue( "" );
							cell = cells.getCell( rowIndex, colIndex + 4 ); cell.setStyle(D1Style); cell.setValue( data[2] );
							
							cell = cells.getCell( rowIndex, colIndex + 5 ); cell.setStyle(G1Style); cell.setValue( Double.parseDouble( data[3] ) );
							cell = cells.getCell( rowIndex, colIndex + 6 ); cell.setStyle(H1Style); cell.setValue( Double.parseDouble( data[4] ) );
							
							cell = cells.getCell( rowIndex, colIndex + 7 ); cell.setStyle(G1Style); cell.setValue( Double.parseDouble( data[5] ));
							cell = cells.getCell( rowIndex, colIndex + 8 ); cell.setStyle(H1Style); cell.setValue( Double.parseDouble( data[6] ) );
							
							cell = cells.getCell( rowIndex, colIndex + 9 ); cell.setStyle(G1Style); cell.setValue( 0 );
							cell = cells.getCell( rowIndex, colIndex + 10 ); cell.setStyle(H1Style); cell.setValue( tonggiatri_chenhlech );
								
							rowIndex ++;
						}
					}
					//else
					//{
						String maTP = "";
						String tenTP = "";
						String ma = rsChiphi.getString("ma");
						String ten = rsChiphi.getString("ten");
						String donvi = rsChiphi.getString("donvi");
						
						double soluong_dinhmuc = rsChiphi.getDouble("soluong_dinhmuc");
						double tonggiatri_dinhmuc = rsChiphi.getDouble("tonggiatri_dinhmuc");
						
						double soluong = rsChiphi.getDouble("soluong");
						double tonggiatri = rsChiphi.getDouble("tonggiatri");
						
						double soluong_chenhlech = soluong - soluong_dinhmuc;
						double tonggiatri_chenhlech = tonggiatri - tonggiatri_dinhmuc;
						
						cell = cells.getCell( rowIndex, colIndex );     cell.setStyle(D1Style); cell.setValue( maTP );
						cell = cells.getCell( rowIndex, colIndex + 1 ); cell.setStyle(D1Style); cell.setValue( tenTP );
						cell = cells.getCell( rowIndex, colIndex + 2 );     cell.setStyle(D1Style); cell.setValue( ma );
						cell = cells.getCell( rowIndex, colIndex + 3 ); cell.setStyle(D1Style); cell.setValue( ten );
						cell = cells.getCell( rowIndex, colIndex + 4 ); cell.setStyle(D1Style); cell.setValue( donvi );
						
						cell = cells.getCell( rowIndex, colIndex + 5 ); cell.setStyle(G1Style); cell.setValue( soluong_dinhmuc );
						cell = cells.getCell( rowIndex, colIndex + 6 ); cell.setStyle(H1Style); cell.setValue( tonggiatri_dinhmuc );
						
						cell = cells.getCell( rowIndex, colIndex + 7 ); cell.setStyle(G1Style); cell.setValue( soluong );
						cell = cells.getCell( rowIndex, colIndex + 8 ); cell.setStyle(H1Style); cell.setValue( tonggiatri );
						
						cell = cells.getCell( rowIndex, colIndex + 9 ); cell.setStyle(G1Style); cell.setValue( soluong_chenhlech );
						cell = cells.getCell( rowIndex, colIndex + 10 ); cell.setStyle(H1Style); cell.setValue( tonggiatri_chenhlech );
							
						rowIndex ++;
					//}
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
