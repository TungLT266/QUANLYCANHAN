package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.baocao.IBaocao;
import geso.traphaco.erp.beans.baocao.imp.Baocao;
import geso.traphaco.erp.db.sql.dbutils;

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

public class ErpXuatnhaptonTTTHSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpXuatnhaptonTTTHSvl() {
        super();
    }
    
    NumberFormat formatter = new DecimalFormat("#,###,###");
    NumberFormat formatter2 = new DecimalFormat("#,###,###.00");
    
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
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCNhapXuatTonTH.jsp";
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
 
	    
	    
	    String action = request.getParameter("action");
	    System.out.println("Action nhan duoc: " + action + " -- LOAI SP: " + obj.getLoaiSanPhamIds() );
	    
	    if(action.equals("search"))
	    {
	    	obj.createRs();
	    	session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCNhapXuatTonTH.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	try 
	    	{	
//	    		Utility util = new Utility();
	    		dbutils db = new dbutils();
	    		String sql = "delete sanpham_tmp ";
	    	    db.update(sql);
	    	    
	    	    sql = " insert into sanpham_tmp select pk_seq from erp_sanpham where 1 = 1 ";
	    	    if(obj.getSanPhamIds().trim().length() > 0)
	    	    	sql += " AND pk_seq in (" + obj.getSanPhamIds() + ")";
	    	    if(obj.getLoaiSanPhamIds().trim().length() > 0)
	    	    	sql += " and loaisanpham_fk in (" + obj.getLoaiSanPhamIds() + ")";
	    	    if(obj.getChungloaiIds().trim().length() > 0)
	    	    	sql += " and chungloai_fk in (" + obj.getChungloaiIds() + ")";
	    	    
	    	
	    	    
	    	    db.update(sql);
	    	    
	    		if( spId.length() >= 0   )   //Bao cao tong hop
	    		{
	    			response.setContentType("application/xlsm");
		    		response.setHeader("Content-Disposition", "attachment; filename=ErpTongHopNhapXuatTonTTTH.xlsm");
 
	    			TongHopNXT(out, obj);
		    	 
	    		}
	    		db.shutDown();
	    		
	    		 
			} 
	    	catch (Exception e) { 
	    		e.printStackTrace();
	    		
	    		 
	    	}
	    }
	}
	

	private void TongHopNXT(OutputStream out, IBaocao obj) throws Exception 
    {   
	
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		
		try{
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ERPTongHopNhapXuatTonTTTH.xlsm");
	
			workbook.open(fstream);
		
				
		    BaoCaoTongHopNXT(workbook, obj);	     
		     
		    workbook.save(out);
				
			fstream.close();
		}catch(Exception er){
			er.printStackTrace();
			fstream.close();
			throw er;
		}
    }
	
	

	private void BaoCaoTongHopNXT(Workbook workbook, IBaocao obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
//	    NumberFormat formatter = new DecimalFormat("#,###,###");
	    
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
	    tieude = "BÁO CÁO TỔNG HỢP NHẬP XUẤT TỒN TỔNG HỢP";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 18, tieude);
	    //cells.merge(4, 3, 0, 10);
	    
	    cell = cells.getCell("A6");
	    tieude = "Từ ngày " + obj.getTuNgay() + " đến ngày " + obj.getDenNgay();
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    //cells.merge(5, 4, 0, 10);
	    
	    cell = cells.getCell("L7");
	    tieude = "Đơn vị tính: VND";
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    //cells.merge(7, 11, 0, 2);
	    
	    cells.setRowHeight(8, 30.0f);
	    
	    cell = cells.getCell("A8");
	    tieude = "STT";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    //cells.merge(7, 1, 2, 2);
	    
	    cell = cells.getCell("B8");
	    tieude = "Mã hàng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    //cells.merge(7, 1, 2, 2);
	    
	    cell = cells.getCell("C8");
	    tieude = "Tên hàng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    //cells.merge(7, 3, 2, 6);
	    
	    cell = cells.getCell("D8");
	    tieude = "Đơn vị tính";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    //cells.merge(7, 9, 2, 2);
	    //cells.merge(7, 3, 2, 6);
	    
	    cell = cells.getCell("E8");
	    tieude = "Loại sản phẩm";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("F8");
	    tieude = "Nhãn hàng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("G8");
	    tieude = "Chủng loại";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("H8");//e8
	    tieude = "Tồn đầu kỳ";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    //cells.merge(7, 11, 0, 3);
	    
	    cell = cells.getCell("K8");
	    tieude = "Tổng nhập";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("N8");
	    tieude = "Tổng xuất";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	   // cells.merge(7, 14, 0, 3);
	    
	    cell = cells.getCell("Q8");
	    tieude = "Tổng tồn";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    cell = cells.getCell("H9");//e9
	    tieude = "Số lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("I9");
	    tieude = "Đơn giá";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("J9");
	    tieude = "Thành tiền";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("K9"); //h9
	    tieude = "Số lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("L9");
	    tieude = "Đơn giá";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("M9");
	    tieude = "Thành tiền";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("N9"); //k9
	    tieude = "Số lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);	    
	    
	    cell = cells.getCell("O9");
	    tieude = "Đơn giá";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("P9");
	    tieude = "Thành tiền";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("Q9"); //N9
	    tieude = "Số lượng";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("R9");
	    tieude = "Đơn giá";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("S9");
	    tieude = "Thành tiền";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("T9");
	    tieude = "ID_SANPHAM";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("U9");
	    tieude = "DÀI";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("V9");
	    tieude = "RỘNG";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    //cells.merge(7, 14, 0, 3);
	    
	    cell = cells.getCell("W9");
	    tieude = "ĐỊNH LƯỢNG";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    //cells.merge(7, 17, 0, 3);
	    
	    cell = cells.getCell("X9");//e9
	    tieude = "CHUẨN NÉN";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("Y9");
	    tieude = "NGUỒN GỐC";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("Z9");
	    tieude = "LOGO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AA9"); //h9
	    tieude = "MẪU IN";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AB9");
	    tieude = "MÀU";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AC9");
	    tieude = "DÀI ĐÁY";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AD9"); //k9
	    tieude = "ĐƯỜNG KÍNH TRONG";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    cell = cells.getCell("AE9");
	    tieude = "ĐỘ DÀY";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AF9");
	    tieude = "ĐẦU LỚN";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AG9"); //N9
	    tieude = "ĐẦU NHỎ";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	  
	    cell = cells.getCell("AH9");
	    tieude = "N_NHANHANG";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);	    

	    cell = cells.getCell("AI9");
	    tieude = "TIEN_NHANHANG";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AJ9");
	    tieude = "TIEN_CHIPHINHANHANG";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AK9");
	    tieude = "N_KIEMDINHHANGDAT";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AL9");
	    tieude = "TIEN_KIEMDINHHANGDAT";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AM9");
	    tieude = "N_NHANDOIQUYCACH";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	     
	    cell = cells.getCell("AN9");
	    tieude = "TIEN_NHANDOIQUYCACH";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    cell = cells.getCell("AO9");
	    tieude = "N_CHUYENKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    cell = cells.getCell("AP9");
	    tieude = "TIEN_CHUYENKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AQ9");
	    tieude = "N_DIEUCHINHTONKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    cell = cells.getCell("AR9");
	    tieude = "TIEN_DIEUCHINHTONKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    cell = cells.getCell("AS9");
	    tieude = "N_DIEUCHINHKIEMKE";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    cell = cells.getCell("AT9");
	    tieude = "TIEN_DIEUCHINHKIEMKE";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

	    cell = cells.getCell("AU9");
	    tieude = "N_KIEMDINHHANGHONG";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AV9");
	    tieude = "TIEN_KIEMDINHHANGHONG";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AW9");
	    tieude = "N_CHUYENKHOSANXUAT";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    cell = cells.getCell("AX9");
	    tieude = "TIEN_CHUYENKHOSANXUAT";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    
	    
	    cell = cells.getCell("AY9");
	    tieude = "N_NHAPKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("AZ9");
	    tieude = "TIEN_NHAPKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("BA9");
	    tieude = "X_YEUCAUCHUYENKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("BB9");
	    tieude = "TIEN_X_YEUCAUCHUYENKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("BC9");
	    tieude = "X_XUATKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    	    
	    cell = cells.getCell("BD9");
	    tieude = "TIEN_X_XUATKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("BE9");
	    tieude = "X_CHUYENKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("BF9");
	    tieude = "TIEN_X_CHUYENKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("BG9");
	    tieude = "X_XUATDOIQUYCACH";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("BH9");
	    tieude = "TIEN_X_XUATDOIQUYCACH";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    
	    cell = cells.getCell("BI9");
	    tieude = "X_DIEUCHINHTONKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("BJ9");
	    tieude = "TIEN_X_DIEUCHINHTONKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("BK9");
	    tieude = "X_DIEUCHINHKIEMKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("BL9");
	    tieude = "TIEN_X_DIEUCHINHKIEMKHO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("BM9");
	    tieude = "X_TIEUHAO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    cell = cells.getCell("BN9");
	    tieude = "TIEN_X_TIEUHAO";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    
	    
	    //create data
	    dbutils db = new dbutils();
	    
	    //System.out.println("Tosng hop NXT: " + sql);
	    String[] param = new String[3];
	    param[0] =obj.getKhoIds();
	    param[1] =obj.getTuNgay();
	    param[2] =obj.getDenNgay();
	    ResultSet   tongHopNXT = db.getRsByPro("REPORT_XUATNHAPTON_TONGHOP", param);
	 
	    double totalSoluongD = 0;
	    double totalThanhtienD = 0;
	    double totalSoluongN = 0;
	    double totalThanhtienN = 0;
	    double totalSoluongX = 0;
	    double totalThanhtienX = 0;
	    double totalSoluongT = 0;
	    double totalThanhtienT = 0;
	  
	     
	    	try 
	    	{
	    		int index = 10;
	    		if (tongHopNXT != null)
				while(tongHopNXT.next())
				{
					String stt = Integer.toString(index - 9);
			 
					double tondau = tongHopNXT.getDouble("TONDAUKY");
					long thanhtiendau = Math.round(tongHopNXT.getDouble("THANHTIENDAUKY"));
					double dongiadau = 0; 
					if(tondau > 0)
						dongiadau = (double)thanhtiendau / (double)tondau;
					
					totalSoluongD += tondau;
					totalThanhtienD += thanhtiendau; 
					
					double soluongnhap =0;
					long thanhtiennhap =0;
					double dongianhap = 0;
					if(soluongnhap > 0)
						dongianhap = (double)thanhtiennhap / (double)soluongnhap;
					
					totalSoluongN += soluongnhap;
					totalThanhtienN += thanhtiennhap; 
					
					double soluongxuat =0;
					long thanhtienxuat =0;
					double dongiaxuat = 0;
					if(soluongxuat > 0)
						dongiaxuat = (double)thanhtienxuat / (double)soluongxuat;
					
					totalSoluongX += soluongxuat;
					totalThanhtienX += thanhtienxuat;
					
					double soluongton = tondau + soluongnhap - soluongxuat;
					long thanhtienton = thanhtiendau + thanhtiennhap - thanhtienxuat;
					 
					
					double dongiaton = 0; 
					if(soluongton > 0)
						dongiaton = (double)thanhtienton / (double)soluongton;
					
					totalSoluongT += soluongton;
					totalThanhtienT += thanhtienton;
					
					cell = cells.getCell("A" + Integer.toString(index)); 	cell.setValue(stt);
					cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue(tongHopNXT.getString("SPMA"));
					cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue(tongHopNXT.getString("SPTEN"));
					cell = cells.getCell("D" + Integer.toString(index)); 	cell.setValue(tongHopNXT.getString("DONVI"));
					cell = cells.getCell("E" + Integer.toString(index)); 	cell.setValue(tongHopNXT.getString("LSP"));
					cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue(tongHopNXT.getString("NH"));
					cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(tongHopNXT.getString("CL"));
					
					cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue(tondau);				
					cell = cells.getCell("I" + Integer.toString(index)); 	cell.setValue(dongiadau);
					cell = cells.getCell("J" + Integer.toString(index)); 	cell.setValue(thanhtiendau);
					cell = cells.getCell("K" + Integer.toString(index)); 	cell.setValue(soluongnhap); 
					cell = cells.getCell("L" + Integer.toString(index)); 	cell.setValue(dongianhap);
					cell = cells.getCell("M" + Integer.toString(index)); 	cell.setValue(thanhtiennhap);
					cell = cells.getCell("N" + Integer.toString(index)); 	cell.setValue(soluongxuat); 
					cell = cells.getCell("O" + Integer.toString(index)); 	cell.setValue(dongiaxuat);
					cell = cells.getCell("P" + Integer.toString(index)); 	cell.setValue(thanhtienxuat);
					cell = cells.getCell("Q" + Integer.toString(index)); 	cell.setValue(soluongton); 
					cell = cells.getCell("R" + Integer.toString(index)); 	cell.setValue(dongiaton); 
					cell = cells.getCell("S" + Integer.toString(index));    cell.setValue(thanhtienton);							
					
					cell = cells.getCell("T" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("PK_SEQ"));
					cell = cells.getCell("U" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DAI"));
					cell = cells.getCell("V" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("RONG"));
					cell = cells.getCell("W" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DINHLUONG"));
					cell = cells.getCell("X" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("CHUANNEN"));
					cell = cells.getCell("Y" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("QUYCACH_NGUONGOC"));
					cell = cells.getCell("Z" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("LOGO"));
					cell = cells.getCell("AA" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("MAUIN"));
					cell = cells.getCell("AB" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("MAU"));
					cell = cells.getCell("AC" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DAIDAY"));
					cell = cells.getCell("AD" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DUONGKINHTRONG"));
					cell = cells.getCell("AE" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DODAY"));
					cell = cells.getCell("AF" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DAULON"));
					cell = cells.getCell("AG" + Integer.toString(index));    cell.setValue(tongHopNXT.getString("DAUNHO"));
					cell = cells.getCell("AH" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("N_NHANHANG"));
					cell = cells.getCell("AI" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_NHANHANG"));
					cell = cells.getCell("AJ" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_CHIPHINHANHANG"));
					cell = cells.getCell("AK" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("N_KIEMDINHHANGDAT"));
					cell = cells.getCell("AL" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_KIEMDINHHANGDAT"));
					cell = cells.getCell("AM" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("N_NHANDOIQUYCACH"));
					cell = cells.getCell("AN" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_NHANDOIQUYCACH"));
					cell = cells.getCell("AO" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("N_CHUYENKHO"));
					cell = cells.getCell("AP" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_CHUYENKHO"));
					cell = cells.getCell("AQ" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("N_DIEUCHINHTONKHO"));
					cell = cells.getCell("AR" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_DIEUCHINHTONKHO"));
					cell = cells.getCell("AS" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("N_DIEUCHINHKIEMKE"));
					cell = cells.getCell("AT" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_DIEUCHINHKIEMKE"));
					cell = cells.getCell("AU" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("N_KIEMDINHHANGHONG"));
					cell = cells.getCell("AV" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_KIEMDINHHANGHONG"));
					cell = cells.getCell("AW" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("N_CHUYENKHOSANXUAT"));
					cell = cells.getCell("AX" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_CHUYENKHOSANXUAT"));
					cell = cells.getCell("AY" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("N_NHAPKHO"));
					cell = cells.getCell("AZ" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_NHAPKHO"));
					cell = cells.getCell("BA" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("X_YEUCAUCHUYENKHO"));
					cell = cells.getCell("BB" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_X_YEUCAUCHUYENKHO"));
					cell = cells.getCell("BC" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("X_XUATKHO"));
					cell = cells.getCell("BD" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_X_XUATKHO"));
					cell = cells.getCell("BE" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("X_CHUYENKHO"));
					cell = cells.getCell("BF" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_X_CHUYENKHO"));
					cell = cells.getCell("BG" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("X_XUATDOIQUYCACH"));
					cell = cells.getCell("BH" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_X_XUATDOIQUYCACH"));
					
					cell = cells.getCell("BI" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("X_DIEUCHINHTONKHO"));
					cell = cells.getCell("BJ" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_X_DIEUCHINHTONKHO"));
					
					cell = cells.getCell("BK" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("X_DIEUCHINHKIEMKHO"));
					cell = cells.getCell("BL" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_X_DIEUCHINHKIEMKHO"));
					
					cell = cells.getCell("BM" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("X_TIEUHAO"));
					cell = cells.getCell("BN" + Integer.toString(index));    cell.setValue(tongHopNXT.getDouble("TIEN_X_TIEUHAO"));
				 
					 
					index++;
				}
				tongHopNXT.close();
				
				cell = cells.getCell("A" + Integer.toString(index)); 	ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, "Tổng cộng");
				
				cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue(totalSoluongD);
				cell = cells.getCell("J" + Integer.toString(index)); 	cell.setValue(totalThanhtienD);
				
				cell = cells.getCell("K" + Integer.toString(index)); 	cell.setValue(totalSoluongN);
				cell = cells.getCell("M" + Integer.toString(index)); 	cell.setValue(totalThanhtienN);
				
				cell = cells.getCell("N" + Integer.toString(index)); 	cell.setValue(totalSoluongX);
				cell = cells.getCell("P" + Integer.toString(index)); 	cell.setValue(totalThanhtienX);
				
				cell = cells.getCell("Q" + Integer.toString(index)); 	cell.setValue(totalSoluongT);
				cell = cells.getCell("S" + Integer.toString(index)); 	cell.setValue(totalThanhtienT);
			}
	    	catch (Exception e) 
	    	{ 
	    		e.printStackTrace();
	    		 
	    	}
	    	db.shutDown();
	    
	}
 
	public static void main(String[] arg)
	{
	 
	}
	
	

}
