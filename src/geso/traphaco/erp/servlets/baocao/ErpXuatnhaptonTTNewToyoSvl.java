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
import java.sql.SQLException;
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

public class ErpXuatnhaptonTTNewToyoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpXuatnhaptonTTNewToyoSvl() {
        super();
    }
    
    NumberFormat formatter = new DecimalFormat("#,###,###");
    NumberFormat formatter2 = new DecimalFormat("#,###,###.000");
    NumberFormat formatter3 = new DecimalFormat("#########.###");
    
    
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
	    obj.createRs();
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCNhapXuatTonNewtoyo.jsp";
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
	    
	    
	    String check_layhangkhocxl = request.getParameter("check_layhangkhocxl");
	    if(check_layhangkhocxl == null)
	    	check_layhangkhocxl = "";
	    obj.setLayHangKho_CXL(check_layhangkhocxl);
	    
	    
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
	    
 
	    
	    String action = request.getParameter("action");
	    //System.out.println("Action nhan duoc: " + action + " -- LOAI SP: " + obj.getLoaiSanPhamIds() );
	    
	    if(action.equals("search"))
	    {
	    	obj.createRs();
	    	session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCNhapXuatTonNewtoyo.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else
	    { 
	    	Utility util = new Utility();
    		dbutils db = new dbutils();
    		// check kho đang lấy báo cáo có phải là kho thành phẩm ko,nếu trường hợp lấy báo cáo kho thành phẩm bao gồm hàng chờ xử lý
    		String sql="";
    		if(obj.getLayHangKho_CXL().equals("1")){
    		  sql="select loai from erp_khott where dvkd_fk is not null and pk_Seq="+obj.getKhoIds();
    			ResultSet rs=db.get(sql);
    			String loai="";
    			try{
	    			if(rs.next()){
	    				loai=rs.getString("loai");
	    			}
	    			rs.close();
	    		 
	    			if(!loai.trim().equals("2")){
	    				//không phải kho thành phẩm
	    				obj.createRs();
	    				obj.setMsg("Vui lòng chọn đúng kho thành phẩm ");
	    		    	session.setAttribute("obj", obj);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCNhapXuatTonNewtoyo.jsp";
	    				response.sendRedirect(nextJSP);
	    				return;
	    			}
	    			
    			}catch(Exception er){
    				er.printStackTrace();
    			}
    		}
    		
	    	try 
	    	{	
	    		
	    		  sql = "delete sanpham_tmp ";
	    	    db.update(sql);
	    	    
	    	    sql = " insert into sanpham_tmp select pk_seq from erp_sanpham where 1 = 1 ";
	    	    if(obj.getSanPhamIds().trim().length() > 0)
	    	    	sql += " AND pk_seq in (" + obj.getSanPhamIds() + ")";
	    	    if(obj.getLoaiSanPhamIds().trim().length() > 0)
	    	    	sql += " and loaisanpham_fk in (" + obj.getLoaiSanPhamIds() + ")";
	    	    if(obj.getChungloaiIds().trim().length() > 0)
	    	    	sql += " and chungloai_fk in (" + obj.getChungloaiIds() + ")";
	    	    	db.update(sql);
	    			response.setContentType("application/xlsm");
		    		response.setHeader("Content-Disposition", "attachment; filename=ErpTongHopNhapXuatTonTT.xlsm");
 
		    		TongHopNXT(out, obj);
	    		 
			} 
	    	catch (Exception e) { 
	    		
	    		e.printStackTrace();
	    		obj.createRs();
		    	session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCNhapXuatTonNewtoyo.jsp";
				response.sendRedirect(nextJSP);
	    		
	    	}
	    	
	    }
	}
 
	private void TongHopNXT(OutputStream out, IBaocao obj) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ERPTongHopNhapXuatTonTT_Newtoyo.xlsm");

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
	    
	    String tieude = "Tên DN: ";
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    cells.merge(0, 0, 0, 8);
	    
	    cell = cells.getCell("A2");
	    tieude = "Địa chỉ: ";
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    cells.merge(1, 0, 0, 8);
	    
	    cell = cells.getCell("A3");
	    tieude = "MST: 0 3 1 0 7 7 6 0 7 1";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    cells.merge(2, 0, 0, 8);
	    
	    if(obj.getKhoIds().trim().length() > 0)
	    {
		    cell = cells.getCell("A4");
		    tieude = "Kho: " + obj.getKhoTen();
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    cells.merge(2, 0, 0, 8);
	    }
	    
	    cells.setRowHeight(4, 30.0f);
	    cell = cells.getCell("A5");
	    tieude = "BÁO CÁO TỔNG HỢP NHẬP XUẤT TỒN";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 18, tieude);
	    //cells.merge(4, 3, 0, 10);
	    
	    cell = cells.getCell("A6");
	    tieude = "Từ ngày " + obj.getTuNgay() + " đến ngày " + obj.getDenNgay();
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    //cells.merge(5, 4, 0, 10);
	    
	    if(obj.getLayHangKho_CXL().equals("1")){
	    	cell = cells.getCell("A7");
		    tieude = "Lấy hàng bao gồm kho chờ xử lý";
		    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    }
	    cell = cells.getCell("L7");
	    tieude = "Đơn vị tính: VND";
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    //cells.merge(7, 11, 0, 2);
	    
	    cells.setRowHeight(8, 30.0f);
	    
	    cell = cells.getCell("A9");
	    tieude = "STT";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    //cells.merge(7, 1, 2, 2);
	    
	    cell = cells.getCell("B9");
	    tieude = "Mã hàng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    //cells.merge(7, 1, 2, 2);
	    
	    cell = cells.getCell("C9");
	    tieude = "Tên hàng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    //cells.merge(7, 3, 2, 6);
	    
	    cell = cells.getCell("D9");
	    tieude = "Đơn vị tính";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    //cells.merge(7, 9, 2, 2);
	    //cells.merge(7, 3, 2, 6);
	    
	    cell = cells.getCell("E9");
	    tieude = "Loại sản phẩm";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("F9");
	    tieude = "Nhãn hàng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("G9");
	    tieude = "Chủng loại";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("H9");
	    tieude = "ID_SANPHAM";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    

	    cell = cells.getCell("I9");
	    tieude = "DÀI";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("J9");
	    tieude = "RỘNG";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("K9");
	    tieude = "ĐỊNH LƯỢNG";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("L9");
	    tieude = "MÀU";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    

	    cell = cells.getCell("M9");
	    tieude = "ĐƯỜNG KÍNH TRONG";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    
 
	    cell = cells.getCell("N9");
	    tieude = "ĐỘ DÀY";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);


	    cell = cells.getCell("O9");
	    tieude = "LOGO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    cell = cells.getCell("P9");
	    tieude = "MẪU IN";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    
	    cell = cells.getCell("Q9");
	    tieude = "CHUẨN NÉN";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    cell = cells.getCell("R9");
	    tieude = "DÀI ĐÁY";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);


	    cell = cells.getCell("S9");
	    tieude = "ĐẦU LỚN";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    cell = cells.getCell("T9");
	    tieude = "ĐẦU NHỎ";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
 
	    cell = cells.getCell("U9");
	    tieude = "NGUỒN GỐC ";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    
	    cell = cells.getCell("V8");//e8
	    tieude = "Tồn đầu kỳ";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    //cells.merge(7, 11, 0, 3);
	    
	    cell = cells.getCell("X8");
	    tieude = "Nhập kho";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    //cells.merge(7, 6, 0, 3);
	    
	    cell = cells.getCell("Z8");
	    tieude = "Nhập đổi quy cách";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    cells.merge(7, 14, 0, 3);
	    
	    cell = cells.getCell("AB8");
	    tieude = "Nhập khác";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    
	    cell = cells.getCell("AD8");
	    tieude = "Nhập Điều chỉnh";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    
	    cell = cells.getCell("AF8");
	    tieude = "Nhập kiểm kê";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    
	    
	    cell = cells.getCell("AH8");
	    tieude = "Tổng nhập";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AJ8");
	    tieude = "Xuất kho HD";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    

	    cell = cells.getCell("AL8");
	    tieude = "Xuất đổi QC";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);


	    cell = cells.getCell("AN8");
	    tieude = "Xuất khác";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    cell = cells.getCell("AP8");
	    tieude = "Xuất điều chỉnh tồn kho";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);


	    cell = cells.getCell("AR8");
	    tieude = "Xuất điều chỉnh kiểm kê";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    cell = cells.getCell("AT8");
	    tieude = "Tổng xuất";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    cell = cells.getCell("AV8");
	    tieude = "Tổng tồn";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    
	    
	    //cells.merge(7, 17, 0, 3);
	    
	    //đầu kỳ
	    cell = cells.getCell("V9");//e9
	    tieude = "Số lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    cell = cells.getCell("W9");//e9
	    tieude = "Trọng lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    // nhập kho
	    cell = cells.getCell("X9"); //h9
	    tieude = "Số lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("Y9"); //h9
	    tieude = "Trọng lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	     
	    // nhập dqc
	    cell = cells.getCell("Z9"); //h9
	    tieude = "Số lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AA9"); //h9
	    tieude = "Trọng lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    // nhập khác
	    cell = cells.getCell("AB9"); //h9
	    tieude = "Số lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AC9"); //h9
	    tieude = "Trọng lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	   
	    
	    // nhập điều chỉnh
	    cell = cells.getCell("AD9"); //h9
	    tieude = "Số lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AE9"); //h9
	    tieude = "Trọng lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	   
	    // nhập kiểm kê
	    cell = cells.getCell("AF9"); //h9
	    tieude = "Số lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AG9"); //h9
	    tieude = "Trọng lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	   
	    // tổng nhập
	    cell = cells.getCell("AH9"); //h9
	    tieude = "Số lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AI9"); //h9
	    tieude = "Trọng lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	   
	    
	 // X_XUATKHO
	    cell = cells.getCell("AJ9"); //h9
	    tieude = "Số lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AK9"); //h9
	    tieude = "Trọng lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	   

		 // X_XUATDOIQUYCACH
		    cell = cells.getCell("AL9"); //h9
		    tieude = "Số lượng";
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    
		    cell = cells.getCell("AM9"); //h9
		    tieude = "Trọng lượng";
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		   
	    //X_XUATKHAC
		    cell = cells.getCell("AN9"); //h9
		    tieude = "Số lượng";
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    
		    cell = cells.getCell("AO9"); //h9
		    tieude = "Trọng lượng";
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    //X_DIEUCHINHTONKHO
		    cell = cells.getCell("AP9"); //h9
		    tieude = "Số lượng";
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    
		    cell = cells.getCell("AQ9"); //h9
		    tieude = "Trọng lượng";
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    //X_DIEUCHINHKIEMKHO
		    cell = cells.getCell("AR9"); //h9
		    tieude = "Số lượng";
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    
		    cell = cells.getCell("AS9"); //h9
		    tieude = "Trọng lượng";
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

		  //X_TOTALXUAT
		    cell = cells.getCell("AT9"); //h9
		    tieude = "Số lượng";
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    
		    cell = cells.getCell("AU9"); //h9
		    tieude = "Trọng lượng";
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

		    //TOTAL_TONCUOI
		    
		    cell = cells.getCell("AV9"); //h9
		    tieude = "Số lượng";
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		    
		    cell = cells.getCell("AW9"); //h9
		    tieude = "Trọng lượng";
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

		    
		    
	    //create data
	    dbutils db = new dbutils();
	    
	    //System.out.println("Tong hop NXT: " + sql);
	    ResultSet   tongHopNXT;
	    
	    if(obj.getLayHangKho_CXL().equals("1")){
	    	 String[] param = new String[4];
	    	 String khocxl="100017";
	    	 if(obj.getKhoIds().equals("100012")){
	    		 khocxl="100017";
	    	 }else if(obj.getKhoIds().equals("100009")){
	    		 khocxl="";
	    	 }
	    	 
	    	 param[0] =khocxl;
	 	    param[1] =obj.getKhoIds();
	 	    param[2] =obj.getTuNgay();
	 	    param[3] =obj.getDenNgay();
	 	    	
	 	        tongHopNXT = db.getRsByPro("REPORT_XUATNHAPTON_TP_NHOM", param);
	    }else{
		    String[] param = new String[3];
		    param[0] =obj.getKhoIds();
		    param[1] =obj.getTuNgay();
		    param[2] =obj.getDenNgay();
		    	
		        tongHopNXT = db.getRsByPro("REPORT_XUATNHAPTON_TONGHOP_TL_SL", param);
	    }
	    	try 
	    	{
	    		int index = 10;
				while(tongHopNXT.next())
				{
					String stt = Integer.toString(index - 9);
					String spMa = tongHopNXT.getString("SPMA");
					String spTen = tongHopNXT.getString("SPTEN");
					String donvi = tongHopNXT.getString("DONVI");
					String cl = tongHopNXT.getString("CL");
					String nh = tongHopNXT.getString("NH");
					String lsp = tongHopNXT.getString("LSP");
					
					double TONDAUKY=Double.parseDouble(formatter3.format( (tongHopNXT.getDouble("TONDAUKY"))));
					double TL_TONDAUKY=tongHopNXT.getDouble("TL_TONDAUKY");
					
					double N_NHAPKHO=tongHopNXT.getDouble("N_NHAPKHO");
					double N_TL_NHAPKHO=tongHopNXT.getDouble("N_TL_NHAPKHO");
					
					double N_NHANDOIQUYCACH=tongHopNXT.getDouble("N_NHANDOIQUYCACH");
					double N_TL_NHANDOIQUYCACH=tongHopNXT.getDouble("N_TL_NHANDOIQUYCACH");
					
					
					double N_NHAPKHAC=tongHopNXT.getDouble("N_NHAPKHAC");
					double N_TL_NHAPKHAC=tongHopNXT.getDouble("N_TL_NHAPKHAC");

					double N_DIEUCHINHTONKHO=tongHopNXT.getDouble("N_DIEUCHINHTONKHO");
					double N_TL_DIEUCHINHTONKHO=tongHopNXT.getDouble("N_TL_DIEUCHINHTONKHO");
					
					double N_DIEUCHINHKIEMKE=tongHopNXT.getDouble("N_DIEUCHINHKIEMKE");
					double N_TL_DIEUCHINHKIEMKE=tongHopNXT.getDouble("N_TL_DIEUCHINHKIEMKE");
					
					double TotalSoLuongNhap=Double.parseDouble(formatter3.format( (N_NHAPKHO+ N_NHANDOIQUYCACH+N_NHAPKHAC+N_DIEUCHINHTONKHO+N_DIEUCHINHKIEMKE)));
					double TotalTrongLuongNhap=N_TL_NHAPKHO+ N_TL_NHANDOIQUYCACH+N_TL_NHAPKHAC+N_TL_DIEUCHINHTONKHO+N_TL_DIEUCHINHKIEMKE;
					
					
					//xuất 
					
					double X_XUATKHO=tongHopNXT.getDouble("X_XUATKHO");
					double X_XUATDOIQUYCACH=tongHopNXT.getDouble("X_XUATDOIQUYCACH");
					double X_XUATKHAC=tongHopNXT.getDouble("X_XUATKHAC");
					double X_DIEUCHINHTONKHO=tongHopNXT.getDouble("X_DIEUCHINHTONKHO");
					double X_DIEUCHINHKIEMKHO=tongHopNXT.getDouble("X_DIEUCHINHKIEMKHO");
					double X_TL_XUATKHO=tongHopNXT.getDouble("X_TL_XUATKHO");
					double X_TL_XUATDOIQUYCACH=tongHopNXT.getDouble("X_TL_XUATDOIQUYCACH");
					double X_TL_XUATKHAC=tongHopNXT.getDouble("X_TL_XUATKHAC");
					double X_TL_DIEUCHINHTONKHO=tongHopNXT.getDouble("X_TL_DIEUCHINHTONKHO");
					double X_TL_DIEUCHINHKIEMKHO=tongHopNXT.getDouble("X_TL_DIEUCHINHKIEMKHO");
					 
					double TOTAL_SL_XUAT=Double.parseDouble(formatter3.format( (X_XUATKHO+ X_XUATDOIQUYCACH+X_XUATKHAC+X_DIEUCHINHTONKHO+X_DIEUCHINHKIEMKHO )) );
					
					double TOTAL_TRONGLUONG_XUAT=X_TL_XUATKHO+ X_TL_XUATDOIQUYCACH+X_TL_XUATKHAC+X_TL_DIEUCHINHTONKHO+X_TL_DIEUCHINHKIEMKHO ;
					
					double TOTAL_SL_TONCUOI=TONDAUKY+ TotalSoLuongNhap -TOTAL_SL_XUAT;
					
					double TOTAL_TRONGLUONG_TONCUOI=TL_TONDAUKY+TotalTrongLuongNhap- TOTAL_TRONGLUONG_XUAT;
					
					
					if( TONDAUKY != 0 || TotalSoLuongNhap !=0 || TOTAL_SL_XUAT!=0  ){
					
							 
							cell = cells.getCell("A" + Integer.toString(index)); 	cell.setValue(stt);
							cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue(spMa);
							cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue(spTen);
							cell = cells.getCell("D" + Integer.toString(index)); 	cell.setValue(donvi);
							cell = cells.getCell("E" + Integer.toString(index)); 	cell.setValue(lsp);
							cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue(nh);
							cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(cl);
							 
							cell = cells.getCell("H" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("PK_SEQ"));
							cell = cells.getCell("I" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DAI"));
							cell = cells.getCell("J" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("RONG"));
							cell = cells.getCell("K" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DINHLUONG"));
							cell = cells.getCell("L" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("MAU"));
							cell = cells.getCell("M" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DUONGKINHTRONG"));
							cell = cells.getCell("N" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DODAY"));
							cell = cells.getCell("O" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("LOGO"));
							cell = cells.getCell("P" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("MAUIN"));
							cell = cells.getCell("Q" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("CHUANNEN"));
							cell = cells.getCell("R" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DAIDAY"));
							cell = cells.getCell("S" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DAULON"));
							cell = cells.getCell("T" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DAUNHO"));
							cell = cells.getCell("U" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("QUYCACH_NGUONGOC"));
							
						
							cell = cells.getCell("V" + Integer.toString(index));    cell.setValue(TONDAUKY);
							cell = cells.getCell("W" + Integer.toString(index));    cell.setValue(TL_TONDAUKY);
							
							cell = cells.getCell("X" + Integer.toString(index));    cell.setValue(N_NHAPKHO);
							cell = cells.getCell("Y" + Integer.toString(index));    cell.setValue(N_TL_NHAPKHO);
							
							cell = cells.getCell("Z" + Integer.toString(index));    cell.setValue(N_NHANDOIQUYCACH);
							cell = cells.getCell("AA" + Integer.toString(index));    cell.setValue(N_TL_NHANDOIQUYCACH);
							
							cell = cells.getCell("AB" + Integer.toString(index));    cell.setValue(N_NHAPKHAC);
							cell = cells.getCell("AC" + Integer.toString(index));    cell.setValue(N_TL_NHAPKHAC);
							
							cell = cells.getCell("AD" + Integer.toString(index));    cell.setValue(N_DIEUCHINHTONKHO);
							cell = cells.getCell("AE" + Integer.toString(index));    cell.setValue(N_TL_DIEUCHINHTONKHO);
							
							cell = cells.getCell("AF" + Integer.toString(index));    cell.setValue(N_DIEUCHINHKIEMKE);
							cell = cells.getCell("AG" + Integer.toString(index));    cell.setValue(N_TL_DIEUCHINHKIEMKE);
							
							cell = cells.getCell("AH" + Integer.toString(index));    cell.setValue(TotalSoLuongNhap);
							cell = cells.getCell("AI" + Integer.toString(index));    cell.setValue(TotalTrongLuongNhap);
							
							
							
							
							cell = cells.getCell("AJ" + Integer.toString(index));    cell.setValue(X_XUATKHO);
							cell = cells.getCell("AK" + Integer.toString(index));    cell.setValue(X_TL_XUATKHO);
			
		
							cell = cells.getCell("AL" + Integer.toString(index));    cell.setValue(X_XUATDOIQUYCACH);
							cell = cells.getCell("AM" + Integer.toString(index));    cell.setValue(X_TL_XUATDOIQUYCACH);
							
							cell = cells.getCell("AN" + Integer.toString(index));    cell.setValue(X_XUATKHAC);
							cell = cells.getCell("AO" + Integer.toString(index));    cell.setValue(X_TL_XUATKHAC);
							
							cell = cells.getCell("AP" + Integer.toString(index));    cell.setValue(X_DIEUCHINHTONKHO);
							cell = cells.getCell("AQ" + Integer.toString(index));    cell.setValue(X_TL_DIEUCHINHTONKHO);
							
							cell = cells.getCell("AR" + Integer.toString(index));    cell.setValue(X_DIEUCHINHKIEMKHO);
							cell = cells.getCell("AS" + Integer.toString(index));    cell.setValue(X_TL_DIEUCHINHKIEMKHO);
							
							cell = cells.getCell("AT" + Integer.toString(index));    cell.setValue(TOTAL_SL_XUAT);
							cell = cells.getCell("AU" + Integer.toString(index));    cell.setValue(TOTAL_TRONGLUONG_XUAT);
							
							cell = cells.getCell("AV" + Integer.toString(index));    cell.setValue(TOTAL_SL_TONCUOI);
							cell = cells.getCell("AW" + Integer.toString(index));    cell.setValue(TOTAL_TRONGLUONG_TONCUOI);	 
							index++;
					}
				}
				tongHopNXT.close();
				
				cell = cells.getCell("A" + Integer.toString(index)); 	ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, "Tổng cộng");
				
				//cell = cells.getCell("H" + Integer.toString(index)); 	cell.set
				
				
			/*	cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue(totalSoluongD);
				cell = cells.getCell("J" + Integer.toString(index)); 	cell.setValue(totalThanhtienD);
				
				cell = cells.getCell("K" + Integer.toString(index)); 	cell.setValue(totalSoluongN);
				cell = cells.getCell("M" + Integer.toString(index)); 	cell.setValue(totalThanhtienN);
				
				cell = cells.getCell("N" + Integer.toString(index)); 	cell.setValue(totalSoluongX);
				cell = cells.getCell("P" + Integer.toString(index)); 	cell.setValue(totalThanhtienX);
				
				cell = cells.getCell("Q" + Integer.toString(index)); 	cell.setValue(totalSoluongT);
				cell = cells.getCell("S" + Integer.toString(index)); 	cell.setValue(totalThanhtienT);*/
			}
	    	catch (SQLException e) 
	    	{ 
	    		e.printStackTrace();
	    		System.out.println("Exception2: " + e.getMessage());
	    	}
	    
	}
 
	public static void main(String[] arg)
	{
	 
	}
 
}
