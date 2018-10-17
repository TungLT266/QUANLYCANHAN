package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
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
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpXuatnhaptonKTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpXuatnhaptonKTSvl() {
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

	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new Baocao();
	    obj.setUserId(userId);
	    obj.setXemtheolo("3"); //mac dinh la bao cao theo hoa don
	    obj.createRsBCKHO();
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCNhapXuatTonKT.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		OutputStream out = response.getOutputStream(); 
		IBaocao obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    String userId = request.getParameter("userId");

	    obj = new Baocao();
	    obj.setUserId(userId);
	    
	    String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTuNgay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenNgay(denngay);
	    
	    String khoId = request.getParameter("khoId");
	    if(khoId == null)
	    	khoId = "";
	    obj.setKhoIds(khoId);
	    
	    String khoTen = request.getParameter("khoTen");
	    if(khoTen == null)
	    	khoTen = "";
	    obj.setKhoTen(khoTen);
	    
	    String check_laysolieucophatsinh = request.getParameter("check_laysolieucophatsinh");
	    if(check_laysolieucophatsinh == null)
	    	check_laysolieucophatsinh = "";
	    obj.setCheck_SpCoPhatSinh(check_laysolieucophatsinh);
	    
	    String[] lspIds = request.getParameterValues("loaisanpham");
	    String lspId = "";
	    if(lspIds != null)
	    {
	    	for(int i = 0; i < lspIds.length; i++)
	    		lspId += lspIds[i] + ",";
	    	if(lspId.length() > 0)
	    		lspId = lspId.substring(0, lspId.length() - 1);
	    	obj.setLoaiSanPhamIds(lspId);
	    }
	    
		String[] maspIds = request.getParameterValues("maspIds");
		String maspId = "";
		if (maspIds != null)
		{
			for (int i = 0; i < maspIds.length; i++)
				maspId += "'" + maspIds[i] + "',";
			if (maspId.length() > 0)
				maspId = maspId.substring(0, maspId.length() - 1);
			obj.setMaSanPhamIds(maspId);
		}

		String[] dvkdIds = request.getParameterValues("dvkdIds");
		String dvkdId = "";
		if (dvkdIds != null)
		{
			for (int i = 0; i < dvkdIds.length; i++)
				dvkdId +=  dvkdIds[i] + ",";
			if (dvkdId.length() > 0)
				dvkdId = dvkdId.substring(0, dvkdId.length() - 1);
			obj.setDvkdIds(dvkdId);
		}
		
	    String[] spIds = request.getParameterValues("spIds");
	    String spId = "";
	    if(spIds != null)
	    {
	    	for(int i = 0; i < spIds.length; i++)
	    		spId += spIds[i] + ",";
	    	if(spId.length() > 0)
	    		spId = spId.substring(0, spId.length() - 1);
	    	obj.setSanPhamIds(spId);
	    }
	    
	    String dinhdang = request.getParameter("dinhdang");
	    if(dinhdang == null)
	    	dinhdang = "0";
	    obj.setXemtheolo(dinhdang);
	       
	    String action = request.getParameter("action");
	    //System.out.println("Action nhan duoc: " + action + " -- LOAI SP: " + obj.getLoaiSanPhamIds() );
	    
	    if(action.equals("search"))
	    {
	    	obj.createRsBCKHO();
	    	session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCNhapXuatTonKT.jsp";
			response.sendRedirect(nextJSP);
	    } 
	    else
	    {
	    	try 
	    	{	
    			response.setContentType("application/xlsm");
	    		response.setHeader("Content-Disposition", "attachment; filename=ErpTongHopNhapXuatTonTT.xlsm");

    			TongHopNXT(out, obj);
			} 
	    	catch (Exception e) 
	    	{ 
	    		e.printStackTrace();
	    		System.out.println("Exception: " + e.getMessage()); 
	    	}
	    }
	}
	
	private void TongHopNXT(OutputStream out, IBaocao obj) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpNhapXuatTon_KeToan.xlsm");

		workbook.open(fstream);
		
	    BaoCaoTongHopNXT(workbook, obj);	     
	     
	    workbook.save(out);
			
		fstream.close();
		
    }

	private void BaoCaoTongHopNXT(Workbook workbook, IBaocao obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    worksheet.setName("Sheet1");
	    
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
	    String query = "";
	     
    	query = "select ten, DIACHI, DIENTHOAI, FAX, " + 
			    " 	0 as coDoituong, " +
			    "	'' as tenkho	" +
			    "from NHAPHANPHOI where pk_seq = '1' ";

	    System.out.println("::: LAY INFO: " + query);
	    ResultSet rsInfo = db.get(query);
	    String tenKHO = "";
	    String tenDN = "";
	    String diachi = "";
	    String dienthoai = "";
	    if( rsInfo != null )
	    {
	    	try 
	    	{
				rsInfo.next();
				tenDN = rsInfo.getString("ten");
				diachi = rsInfo.getString("DIACHI");
				dienthoai = rsInfo.getString("DIENTHOAI") + "  -  Fax: " + rsInfo.getString("FAX");
				tenKHO = rsInfo.getString("tenkho");
				rsInfo.close();
			} 
	    	catch (Exception e) { e.printStackTrace(); }
	    }
	    
	    String tieude = "Tên DN: " + tenDN;
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    cells.merge(0, 0, 0, 8);
	    
	    cell = cells.getCell("A2");
	    tieude = "Địa chỉ: " + diachi;
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    cells.merge(1, 0, 0, 8);
	    
	    cell = cells.getCell("A3");
	    tieude = "Điện thoại: " + dienthoai;
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    cells.merge(2, 0, 0, 8);
	    
	    cells.setRowHeight(4, 30.0f);
	    cell = cells.getCell("A5");
	    tieude = "BÁO CÁO NHẬP XUẤT TỒN KẾ TOÁN";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 18, tieude);
	    	    
	    cell = cells.getCell("A6");
	    tieude = "Kho: " + tenKHO;
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    cells.merge(2, 0, 0, 8);
	    
	    cell = cells.getCell("A7");
	    tieude = "Từ ngày " + obj.getTuNgay() + " đến ngày " + obj.getDenNgay();
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    
	    //Utility util = new Utility();
	    //String condition = " and kho_fk in  " + util.quyen_khott(obj.getUserId());
	    String condition = "";
	    
	    //if( obj.getKhoIds().trim().length() > 0 )
	    	//condition += " and kho_fk in ( " + obj.getKhoIds() + " ) ";
	    
	    if( obj.getLoaiSanPhamIds().trim().length() > 0 )
	    	condition += " and a.sanpham_fk in ( select PK_SEQ from ERP_SANPHAM where LOAISANPHAM_FK in ( " + obj.getLoaiSanPhamIds() + " )  ) ";
	    
	    if( obj.getSanPhamIds().trim().length() > 0 )
	    	condition += " and a.sanpham_fk in ( " + obj.getSanPhamIds() + " ) ";
	    
	    query = "";

    	//Tạm lấy hàm UFN_NXT_HO_FULL_REPORT sau này truyền NPP_FK vào hàm gốc UFN_NXT_HO_FULL
    	query = " select  ma, ten, donvi, "+ 
    			" 		sum(dauky) as dauky, sum(nhap) as nhap, sum(xuat) as xuat, sum( toncuoi ) as toncuoi, " + 
    			" 		sum(thanhtienDK) as thanhtienDK, sum(thanhtienNHAP) as thanhtienNHAP, " + 
    			//" 		sum(thanhtienXUAT) as thanhtienXUAT, " + 
    			"			sum(thanhtienDK) +  sum(thanhtienNHAP) - sum( thanhtien ) as thanhtienXUAT,	" +
    			" 		sum( thanhtien ) as thanhtien " + 
			    " from UFN_NXT_HO_FULL_KETOAN ( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' ) a   " +
			    " where 1 = 1 " + condition;

	    query += " group by a.sanpham_fk, a.ma, a.ten, a.donvi ";
	    query += " order by ma ";
	    
		System.out.println(":::: LAY BAO CAO: " + query);
		ResultSet  chiTietNXT = db.get(query);
		
		try 
		{
			int index = 11;
			if(chiTietNXT != null)
			{
				while(chiTietNXT.next())
				{
					String stt = Integer.toString(index - 10);

					String ma = chiTietNXT.getString("ma");
					String ten = chiTietNXT.getString("ten");
					String donvi = chiTietNXT.getString("donvi");
					
					double dauky = chiTietNXT.getDouble("dauky");
					double nhap = chiTietNXT.getDouble("nhap");
					double xuat = chiTietNXT.getDouble("xuat");
					double toncuoi = dauky + nhap - xuat;	

					cell = cells.getCell("A" + Integer.toString(index)); 	cell.setValue(stt);
					
					cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue(ma);
					cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue(ten);
					cell = cells.getCell("D" + Integer.toString(index)); 	cell.setValue(donvi);
					
					cell = cells.getCell("E" + Integer.toString(index)); 	cell.setValue(dauky); 
					cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue( chiTietNXT.getDouble("thanhtienDK") ); 
					
					cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(nhap); 
					cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue( chiTietNXT.getDouble("thanhtienNHAP") ); 
					
					cell = cells.getCell("I" + Integer.toString(index)); 	cell.setValue(xuat); 
					cell = cells.getCell("J" + Integer.toString(index)); 	cell.setValue( chiTietNXT.getDouble("thanhtienXUAT")  ); 
					
					cell = cells.getCell("K" + Integer.toString(index)); 	cell.setValue(toncuoi); 
					cell = cells.getCell("L" + Integer.toString(index)); 	cell.setValue( chiTietNXT.getDouble("thanhtien")  ); 

					index++;
				}
				chiTietNXT.close();
			}		
		}
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
	    
	    db.shutDown();	    
	} 

}
