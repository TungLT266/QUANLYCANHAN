package geso.traphaco.center.servlets.tieuchithuong;

import geso.traphaco.center.beans.tieuchithuong.ITieuchithuongTLList;
import geso.traphaco.center.beans.tieuchithuong.imp.TieuchithuongTLList;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

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

public class TieuchithuongTLReportSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    
    public TieuchithuongTLReportSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    ITieuchithuongTLList obj = new TieuchithuongTLList();
	    obj.setUserId(userId);
	    
	    obj.initReport("");
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTLReport.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
	    ITieuchithuongTLList obj = new TieuchithuongTLList();
	    
	    String userName = (String) session.getAttribute("userTen");  
	    if(userName == null)
	    	userName = "";
	    
	    String thang = request.getParameter("thang");
	    if(thang == null)
	    	thang = "";
	    obj.setThang(thang);
	    
	    String nam = request.getParameter("nam");
	    if(nam == null)
	    	nam = "";
	    obj.setNam(nam);
	    
	    String tungay = request.getParameter("Sdays");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTungay(tungay);
	    
	    String denngay = request.getParameter("Edays");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    String xemtheo = request.getParameter("xemtheo");
	    if(xemtheo == null)
	    	xemtheo = "1";
	    System.out.println("___Type la: " + xemtheo);
	    obj.setType(xemtheo);
	    
	    String vungId = request.getParameter("vungId");
	    if(vungId == null)
	    	vungId = "";
	    obj.setVungId(vungId);
	    
	    String khuvucId = request.getParameter("khuvucId");
	    if(khuvucId == null)
	    	khuvucId = "";
	    obj.setKvId(khuvucId);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppIds(nppId);
	    
	    String schemeId = request.getParameter("schemeId");
	    if(schemeId == null)
	    	schemeId = "";
	    obj.setSchemeIds(schemeId);
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("taobc"))
	    {
    		if(obj.getNam().trim().length() <= 0 || obj.getThang().trim().length() <= 0 || obj.getSchemeIds().trim().length() <= 0 )
    		{
    			obj.setUserId(userId);
        		obj.initReport("");
        		
        		if(obj.getSchemeIds().trim().length() <= 0)
    			{
        			obj.setMsg("Vui lòng chọn Scheme.");
    			}
        		else
        		{
        			obj.setMsg("Thời gian bạn chọn không hợp lệ");
        		}

    	    	session.setAttribute("obj", obj);  	
        		session.setAttribute("userId", userId);
    		
        		response.sendRedirect("/TraphacoHYERP/pages/Center/TieuChiThuongTLReport.jsp");
    		}
    		else
    		{
    			response.setContentType("application/xlsm");
    			response.setHeader("Content-Disposition", "attachment; filename=TieuChiThuongTL.xlsm");
    			
    			OutputStream outPv = response.getOutputStream();
    			obj.setUserId(userName);
    			createPivotTable(obj, outPv);
    		}
	    }
	    else
	    {
    		obj.setUserId(userId);
    		obj.initReport("");

	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/TieuChiThuongTLReport.jsp");
	    }
	   
	}

	private void createPivotTable(ITieuchithuongTLList obj, OutputStream outPv)
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();	
		
		try 
		{
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\TieuChiThuongTL.xlsm");
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
		     CreateStaticHeader(workbook, obj.getThang(), obj.getNam(), obj.getUserId(), obj);
		     CreateStaticData(workbook, obj);
		    
		     workbook.save(outPv);
				
			fstream.close();
		} 
		catch (Exception e) {}
	}
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName, ITieuchithuongTLList obj) 
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
	    
	    String tieude = "THƯỞNG TÍCH LŨY DOANH SỐ";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	         
	    if(obj.getType().trim().equals("1"))
	    {
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng: " + dateFrom );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B2"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm: " + dateTo );
	    }
	    else
	    {
	    	cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày: " + obj.getTungay() );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B2"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày: " + obj.getDenngay() );
	    }
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);
	    
	    cell = cells.getCell("EA1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EB1"); 	cell.setValue("KhuVuc");  ReportAPI.setCellHeader(cell);	     
	    cell = cells.getCell("EC1"); 	cell.setValue("MaNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("ED1"); 	cell.setValue("TenNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EE1"); 	cell.setValue("ASM");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EF1"); 	cell.setValue("GiamSatBanHang");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EG1"); 	cell.setValue("DaiDienKinhDoanh");  ReportAPI.setCellHeader(cell); 
	    cell = cells.getCell("EH1"); 	cell.setValue("KhachHang");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EI1"); 	cell.setValue("DoanhSo");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EJ1"); 	cell.setValue("Scheme");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EK1"); 	cell.setValue("Thuong");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EL1"); 	cell.setValue("ThuongSR");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EM1"); 	cell.setValue("ThuongSS");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("EN1"); 	cell.setValue("ThuongASM");  ReportAPI.setCellHeader(cell);
	}
	
	private boolean CreateStaticData(Workbook workbook, ITieuchithuongTLList obj) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    
	    String condition = "";
	    if(obj.getVungId().length() > 0)
	    	condition += " and VUNG.pk_seq = '" + obj.getVungId() + "' ";
	    if(obj.getKvId().length() > 0)
	    	condition += " and KHUVUC.pk_seq = '" + obj.getKvId() + "' ";
	    if(obj.getNppIds().length() > 0)
	    	condition += " and NHAPHANPHOI.pk_seq = '" + obj.getNppIds() + "' ";
		
	    System.out.println("Tu ngay: " + obj.getTungay() + "  -- Den ngay: " + obj.getDenngay());
	    
		/*String query = " select Vung.TEN as vungTen, KhuVuc.TEN as kvTen, NhaPhanPhoi.sitecode, NhaPhanPhoi.TEN as nppTen, ASM.TEN as asmTen, GIAMSATBANHANG.TEN as gsbhTen, " +
							"DAIDIENKINHDOANH.TEN as ddkdTen, KHACHHANG.ten as khTen, dophu.*,  " +
							"ISNULL(thuongSales.thuongSR, 0) as thuongSR, ISNULL(thuongSales.thuongTDSR, 0) as thuongTDSR,  " +
							"ISNULL(thuongSales.thuongSS, 0) as thuongSS, ISNULL(thuongSales.thuongTDSS, 0) as thuongTDSS, " +
							"ISNULL(thuongSales.thuongASM, 0) as thuongASM, ISNULL(thuongSales.thuongTDASM, 0) as thuongTDASM  " +
						"from " +
						"( " +
							"select dieukien.tctId, dieukien.SCHEME, thucdat.NPP_FK, thucdat.ASM, thucdat.GSBH_FK, thucdat.DDKD_FK, thucdat.KHACHHANG_FK, thucdat.DoanhSo,  " +
								"case when dieukien.donvi = 0 then dieukien.chietkhau * thucdat.DoanhSo / 100 " +
								"else dieukien.chietkhau end as thuongKH  " +
							"from " +
							"( " +
								"select a.NPP_FK, a.ASM, a.GSBH_FK, a.DDKD_FK, a.KHACHHANG_FK, SUM(b.soluong * b.GIAMUA ) as DoanhSo, 1 as type  " +
								"from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK  " +
								"where a.TRANGTHAI in (1, 3) and a.NGAYNHAP >= '" + obj.getTungay() + "' and a.NGAYNHAP <= '" + obj.getDenngay() + "'  " +
								"group by a.NPP_FK, a.ASM, a.GSBH_FK, a.DDKD_FK, a.KHACHHANG_FK  " +
							") " +
							"thucdat inner join " +
							"( " +
								"select a.PK_SEQ as tctId, a.SCHEME, b.tumuc, b.denmuc, b.chietkhau, b.donvi, 1 as type  " +
								"from TIEUCHITHUONGTL a inner join TIEUCHITHUONGTL_TIEUCHI b on a.PK_SEQ = b.thuongtl_fk " +
								"where a.THANG >= '" + obj.getTungay() + "' and a.NAM <= '" + obj.getDenngay() + "' and a.TRANGTHAI = '1' " +
							") " +
							"dieukien on thucdat.type = dieukien.type " +
							"where thucdat.DoanhSo >= dieukien.tumuc and thucdat.DoanhSo <= dieukien.denmuc " +
						")  " +
						"dophu left join TIEUCHITHUONGTL_MUCTHUONG thuongSales on dophu.tctId = thuongSales.thuongtl_fk  " +
						"inner join NHAPHANPHOI on dophu.npp_fk = NHAPHANPHOI.pk_seq   " +
						"inner join KHUVUC on NHAPHANPHOI.khuvuc_fk = KHUVUC.pk_seq  " +
						"inner join VUNG on KHUVUC.vung_fk = VUNG.pk_seq  " +
						"inner join GIAMSATBANHANG on dophu.gsbh_fk = GIAMSATBANHANG.pk_seq   " +
						"inner join DAIDIENKINHDOANH on dophu.ddkd_fk = DAIDIENKINHDOANH.pk_seq   " +
						"inner join KHACHHANG on dophu.KHACHHANG_FK = KHACHHANG.PK_SEQ  " +
						"left join ASM_KHUVUC on ASM_KHUVUC.khuvuc_fk = KHUVUC.pk_seq   " +
						"left join ASM on ASM_KHUVUC.asm_fk = ASM.pk_seq and ASM.TRANGTHAI = '1' and ASM.PK_SEQ = dophu.ASM  " +
						"where NHAPHANPHOI.TRANGTHAI = '1' and GIAMSATBANHANG.TRANGTHAI = '1' and DAIDIENKINHDOANH.TRANGTHAI = '1' " +
							"and KHUVUC.TRANGTHAI = '1' and VUNG.TRANGTHAI = '1' ";*/
		
	    String[] tumuc = null;
	    String[] denmuc = null;
	    String[] chietkhau = null;
	    String[] donvi = null;
	    
	    double MucVuot = 0;
	    double ckMucVuot = 0;
	    int dvMucVuot = 0;
	    
	    String sql = "select a.PK_SEQ as tctId, a.SCHEME, b.tumuc, b.denmuc, b.chietkhau, b.donvi, ISNULL(a.mucvuot, 0) as mucvuot, isnull(a.chietkhauMucVuot, 0) as ckMucVuot, a.donviMucVuot  " +
	    			 "from TIEUCHITHUONGTL a inner join TIEUCHITHUONGTL_TIEUCHI b on a.PK_SEQ = b.thuongtl_fk  " +
	    			 "where a.PK_SEQ = '" + obj.getSchemeIds() + "' and a.TRANGTHAI = '1'";
	    
	    //System.out.println("__Init MUC: " + sql);
	    
	    ResultSet rsThuong = db.get(sql);
	    if(rsThuong != null)
	    {
	    	try 
	    	{
	    		String tu_muc = "";
	    		String den_muc = "";
	    		String chiet_khau = "";
	    		String don_vi = "";
	    		
				while(rsThuong.next())
				{
					tu_muc += rsThuong.getString("tumuc") + "__";
					den_muc += rsThuong.getString("denmuc") + "__";
					chiet_khau += rsThuong.getString("chietkhau") + "__";
					don_vi += rsThuong.getString("donvi") + "__";
					
					MucVuot = rsThuong.getDouble("mucvuot");
					ckMucVuot = rsThuong.getDouble("ckMucVuot");
					dvMucVuot = rsThuong.getInt("donviMucVuot");
				}
				rsThuong.close();

				if(tu_muc.trim().length() > 0)
				{
					tu_muc = tu_muc.substring(0, tu_muc.length() - 2);
					tumuc = tu_muc.split("__");
					
					den_muc = den_muc.substring(0, den_muc.length() - 2);
					denmuc = den_muc.split("__");
					
					chiet_khau = chiet_khau.substring(0, chiet_khau.length() - 2);
					chietkhau = chiet_khau.split("__");
					
					don_vi = don_vi.substring(0, don_vi.length() - 2);
					donvi = don_vi.split("__");
				}
				
				
			} 
	    	catch (Exception e) 
	    	{
	    		System.out.println("___EXCEPTION LAY THUONG: " + e.getMessage());
	    	}	
	    }
	    
	    sql = "";
	    
	    String query = "select Vung.TEN as vungTen, KhuVuc.TEN as kvTen, NhaPhanPhoi.sitecode, NhaPhanPhoi.TEN as nppTen, ASM.TEN as asmTen, GIAMSATBANHANG.TEN as gsbhTen,  " +
	    					"DAIDIENKINHDOANH.TEN as ddkdTen, KHACHHANG.ten as khTen, " +
	    					"( select scheme from TIEUCHITHUONGTL where pk_seq = dophu.thuongtl_fk ) as scheme, dophu.*  " +
	    				"from  " +
	    				"(  " +
		    				"select c.thuongtl_fk, a.NPP_FK, a.ASM, a.GSBH_FK, a.DDKD_FK, a.KHACHHANG_FK, " +
								"isnull(thuongSR, 0) as thuongSR, isnull(thuongTDSR, 0) as thuongTDSR, isnull(thuongSS, 0) as thuongSS, isnull(thuongTDSS, 0) as thuongTDSS, isnull(thuongASM, 0) as thuongASM, isnull(thuongTDASM, 0) as thuongTDASM, " +
								"case when isnull(tc.doanhsotheothung, 0) = 0 then  SUM(b.soluong * b.GIAMUA ) else " +
																				 "  SUM(b.soluong * isnull( e.SOLUONG1, 0) / ISNULL(e.SOLUONG2, 1)) end as DoanhSo  " +
							"from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK  " +
								"inner join TIEUCHITHUONGTL_SANPHAM c on b.SANPHAM_FK = c.sanpham_fk  " +
								"left join TIEUCHITHUONGTL_MUCTHUONG d on d.thuongtl_fk = c.thuongtl_fk " +
								"left join QUYCACH e on b.SANPHAM_FK = e.SANPHAM_FK and e.DVDL1_FK = '100018' " +
								"inner join TIEUCHITHUONGTL tc on c.thuongtl_fk = tc.PK_SEQ " +
							"where a.TRANGTHAI in (1, 3) and c.thuongtl_fk = '" + obj.getSchemeIds() + "' and a.ngaynhap >= tc.thang and a.ngaynhap <= tc.nam  " +
									"and a.khachhang_fk in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG  " +
															"where DKKMTICHLUY_FK in ( select PK_SEQ from DANGKYKM_TICHLUY where TIEUCHITL_FK = '" + obj.getSchemeIds() + "' ) ) " +
							"group by c.thuongtl_fk, tc.doanhsotheothung, a.NPP_FK, a.ASM, a.GSBH_FK, a.DDKD_FK, a.KHACHHANG_FK, thuongSR, thuongTDSR, thuongSS, thuongTDSS, thuongASM, thuongTDASM   " +
	    				")   " +
	    				"dophu " +
	    				"inner join NHAPHANPHOI on dophu.npp_fk = NHAPHANPHOI.pk_seq   inner join KHUVUC on NHAPHANPHOI.khuvuc_fk = KHUVUC.pk_seq   " +
	    				"inner join VUNG on KHUVUC.vung_fk = VUNG.pk_seq  inner join GIAMSATBANHANG on dophu.gsbh_fk = GIAMSATBANHANG.pk_seq    " +
	    				"inner join DAIDIENKINHDOANH on dophu.ddkd_fk = DAIDIENKINHDOANH.pk_seq    " +
	    				"inner join KHACHHANG on dophu.KHACHHANG_FK = KHACHHANG.PK_SEQ   " +
	    				"left join ASM on ASM.PK_SEQ = KHUVUC.asm_fk";
	    
		System.out.println("1.Chi tieu tich luy tap trung KH: " + query);
		ResultSet rs = db.get(query);
		
		int i = 2;
		if(rs!=null)
		{
			try 
			{
				for(int j = 0; j < 15; j++)
					cells.setColumnWidth(i, 15.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					String vung = rs.getString("vungTen");
					String khuvuc = rs.getString("kvTen");
					
					String maNPP = rs.getString("sitecode");
					String tenNPP = rs.getString("nppten");
					
					String asm = rs.getString("asmTen");
					String gsbh = rs.getString("gsbhTen");
					String ddkd = rs.getString("ddkdTen");
					String kh = rs.getString("khTen");
					
					String scheme = rs.getString("scheme");
					//String scheme = "TEST";
					
					double doanhso = rs.getDouble("doanhso");
					//System.out.println("__DOANH SO: " + doanhso);
					
					//double thuongKH = find_thuong_khachhang(doanhso, tumuc, denmuc, chietkhau, donvi, MucVuot, ckMucVuot, dvMucVuot);
					String arr = find_thuong_khachhang(doanhso, tumuc, denmuc, chietkhau, donvi, MucVuot, ckMucVuot, dvMucVuot);
					//System.out.println("----Ket qua tinh thuong: " + arr);
					double thuongKH = Double.parseDouble(arr.split("_")[0]);
					String donviThuong = arr.split("_")[1];
					
					//double thuongSR = thuongKH;
					//double thuongSS = thuongKH;
					//double thuongASM = thuongKH;
					
					//double thuongKH = rs.getDouble("thuongKH");
					
					if(thuongKH > 0)
					{
						double thuongSR = rs.getDouble("thuongSR");
						double thuongSS = rs.getDouble("thuongSS");
						double thuongASM = rs.getDouble("thuongASM");
						 
						cell = cells.getCell("EA" + Integer.toString(i)); 	cell.setValue(vung);
						cell = cells.getCell("EB" + Integer.toString(i)); 	cell.setValue(khuvuc);
						cell = cells.getCell("EC" + Integer.toString(i)); 	cell.setValue(maNPP);
						cell = cells.getCell("ED" + Integer.toString(i)); 	cell.setValue(tenNPP);
						cell = cells.getCell("EE" + Integer.toString(i)); 	cell.setValue(asm);
						cell = cells.getCell("EF" + Integer.toString(i)); 	cell.setValue(gsbh);
						cell = cells.getCell("EG" + Integer.toString(i)); 	cell.setValue(ddkd);	
						cell = cells.getCell("EH" + Integer.toString(i)); 	cell.setValue(kh);
						cell = cells.getCell("EI" + Integer.toString(i)); 	cell.setValue(doanhso);
						cell = cells.getCell("EJ" + Integer.toString(i)); 	cell.setValue(scheme);
						cell = cells.getCell("EK" + Integer.toString(i)); 	cell.setValue(thuongKH);
						cell = cells.getCell("EL" + Integer.toString(i)); 	cell.setValue(thuongSR);
						cell = cells.getCell("EM" + Integer.toString(i)); 	cell.setValue(thuongSS);
						cell = cells.getCell("EN" + Integer.toString(i)); 	cell.setValue(thuongASM);
					}
					
					i++;
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
			
			} 
			catch (Exception e) 
			{
				System.out.println("Exception: " + e.getMessage());
			}
		} 
		else 
		{
			if(db != null) 
				db.shutDown();
			return false;
		}
		
		if(db != null) 
			db.shutDown();
		return true;
	}
	/*
	public double find_thuong_khachhang(double doanhso, String[] tumuc, String[] denmuc, String[] chietkhau, String[] donvi, double MucVuot, double ckMucVuot, int dvMucVuot) 
	{
		if(tumuc == null || denmuc == null || chietkhau == null || donvi == null)
		{
			//System.out.println("___CO NULL RUI>>>>");
			return 0;
		}
		
		if( ( ckMucVuot > 0 ) && ( doanhso >= MucVuot ) )
		{
			if(dvMucVuot == 0)
			{
				//System.out.println("___Vuot muc: " + ckMucVuot * doanhso / 100);
				return ckMucVuot * doanhso / 100;
			}
			else
			{
				//System.out.println("___Vuot muc: " + ckMucVuot);
				return ckMucVuot;
			}
		}
		
		double thuong = 0;
		for(int i = 0; i < tumuc.length; i++)
		{
			//System.out.println("___Tu muc: " + tumuc[i] + " ___ Den muc: " + denmuc[i] + "  ___ Doanh so: " + doanhso);
			if( ( Double.parseDouble(tumuc[i]) <= doanhso ) && ( doanhso < Double.parseDouble(denmuc[i]) ) )
			{
				// 0 -- Chiet khau , 1 -- Tien
				if(Double.parseDouble(donvi[i]) < 1 )
				{
					thuong = Double.parseDouble(chietkhau[i]) * doanhso / 100;
				}
				else
				{
					thuong = Double.parseDouble(chietkhau[i]);
				}
			}
		}
		
		//System.out.println("__Thuong toi day: " + thuong);
		if(thuong == 0) //Doanh so khong nam trong tu muc - den muc, tim muc gan nhat neu co
		{
			int pos = -1;
			double max = -1;
			for(int i = 0; i < denmuc.length; i++)
			{
				if( doanhso > Double.parseDouble(denmuc[i]) )
				{
					if( Double.parseDouble(denmuc[i]) >= max )
					{
						max = Double.parseDouble(denmuc[i]);
						pos = i;
					}
				}
			}
			
			//System.out.println("___MUC GAN NHAT TIM DUOC: " + denmuc[pos]);
			//Lay duoc Den muc gan nhat
			if(pos != -1)
			{
				if(Double.parseDouble(donvi[pos]) < 1 )
				{
					thuong = Double.parseDouble(chietkhau[pos]) * doanhso / 100;
				}
				else
				{
					thuong = Double.parseDouble(chietkhau[pos]);
				}
			}
		}
		
		//System.out.println("Doanh so: " + doanhso + "___Tien thuong: " + thuong);
		return thuong;
	}
*/
	public String find_thuong_khachhang(double doanhso, String[] tumuc, String[] denmuc, String[] chietkhau, String[] donvi, double MucVuot, double ckMucVuot, int dvMucVuot) 
	{
		String donviThuong = "0";
		
		if(tumuc == null || denmuc == null || chietkhau == null || donvi == null)
		{
			System.out.println("___CO NULL RUI>>>>");
			return "0_0";
		}
		
		if( ( ckMucVuot > 0 ) && ( doanhso >= MucVuot ) )
		{
			if(dvMucVuot == 0)
			{
				//System.out.println("___Vuot muc: " + ckMucVuot * doanhso / 100);
				donviThuong = "0";
				return Double.toString(ckMucVuot * doanhso / 100) + "_" + donviThuong;
			}
			else
			{
				//System.out.println("___Vuot muc: " + ckMucVuot);
				if(dvMucVuot == 1)
				{
					donviThuong = "1";
					return Double.toString(ckMucVuot) + "_" + donviThuong;
				}
				else
				{
					donviThuong = "2";
					return Double.toString(ckMucVuot) + "_" + donviThuong;
				}
			}
		}
		
		double thuong = 0;
		for(int i = 0; i < tumuc.length; i++)
		{
			//System.out.println("___Tu muc: " + tumuc[i] + " ___ Den muc: " + denmuc[i] + "  ___ Doanh so: " + doanhso);
			if( ( Double.parseDouble(tumuc[i]) <= doanhso ) && ( doanhso < Double.parseDouble(denmuc[i]) ) )
			{
				// 0 -- Chiet khau , 1 -- Tien, 2 -- SP
				if(Double.parseDouble(donvi[i]) == 0 )
				{
					donviThuong = "0";
					thuong = Double.parseDouble(chietkhau[i]) * doanhso / 100;
				}
				else
				{
					if(Double.parseDouble(donvi[i]) == 1 )
					{
						donviThuong = "1";
						thuong = Double.parseDouble(chietkhau[i]);
					}
					else
					{
						donviThuong = "2";
						thuong = Double.parseDouble(chietkhau[i]);
					}
				}
			}
		}
		
		//System.out.println("__Thuong toi day: " + thuong);
		if(thuong == 0) //Doanh so khong nam trong tu muc - den muc, tim muc gan nhat neu co
		{
			int pos = -1;
			double max = -1;
			for(int i = 0; i < denmuc.length; i++)
			{
				if( doanhso > Double.parseDouble(denmuc[i]) )
				{
					if( Double.parseDouble(denmuc[i]) >= max )
					{
						max = Double.parseDouble(denmuc[i]);
						pos = i;
					}
				}
			}
			
			//System.out.println("___MUC GAN NHAT TIM DUOC: " + denmuc[pos]);
			//Lay duoc Den muc gan nhat
			if(pos != -1)
			{
				if(Double.parseDouble(donvi[pos]) == 0 )
				{
					donviThuong = "0";
					thuong = Double.parseDouble(chietkhau[pos]) * doanhso / 100;
				}
				else
				{
					if(Double.parseDouble(donvi[pos]) == 1 )
					{
						donviThuong = "1";
						thuong = Double.parseDouble(chietkhau[pos]);
					}
					else
					{
						donviThuong = "2";
						thuong = Double.parseDouble(chietkhau[pos]);
					}
				}
			}
		}
		
		//System.out.println("Doanh so: " + doanhso + "___Tien thuong: " + thuong);
		//System.out.println("___Don vi thuong trong ham: " + donviThuong);
		return Double.toString(thuong) + "_" + donviThuong;
	}
	
	public static void main(String[] arg)
	{
		double doanhso = 350;
		
		String[] tumuc = new String[]{"0", "110", "250"};
		String[] denmuc = new String[]{"99", "200", "300"};
		String[] chietkhau = new String[]{"2", "3", "4"};
		String[] donvi = new String[]{"1", "1", "1"};
		
		double MucVuot = 345;
		double ckMucVuot = 3;
		int dvMucVuot = 1;
		
		
		//TieuchithuongTLReportSvl tc = new TieuchithuongTLReportSvl();
		//double thuong = tc.find_thuong_khachhang(doanhso, tumuc, denmuc, chietkhau, donvi, MucVuot, ckMucVuot, dvMucVuot);
		
	}
	
	
}
