package geso.traphaco.erp.servlets.donmuahangtrongnuoc;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IErpDonmuahangList;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.ErpDonmuahangList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.security.krb5.internal.tools.Ktab;

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

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class ErpBCDonmuahangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpBCDonmuahangSvl() {
        super();
    }   

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDonmuahangList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new ErpDonmuahangList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setUserId(userId);
	    obj.initBaoCao();
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiDMH.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream(); 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IErpDonmuahangList obj = new ErpDonmuahangList();
		obj.setCongtyId((String)session.getAttribute("congtyId"));
		
		String userId = request.getParameter("userId");
		obj.setUserId(userId);
		
		String loaingay = request.getParameter("loaingay");
		if(loaingay == null)
			loaingay = "";
		obj.setLoaingay(loaingay);
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String dvthId = request.getParameter("dvth");
		if(dvthId == null)
			dvthId = "";
		obj.setDvthId(dvthId);
		
		String sanpham = request.getParameter("sanpham");
		if(sanpham == null)
			sanpham = "";
		obj.setSanphamId(sanpham);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";

		String pivot = request.getParameter("pivot");
		if (pivot == null)
			pivot = "0";
		obj.setPivot(pivot);
		
		String[] nccIds = request.getParameterValues("nccIds");
		String str = "";
		if(nccIds != null)
		{
			for(int i = 0; i < nccIds.length; i++)
				str += nccIds[i] + ",";
			
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
			
			obj.setNccIds(str);
		}
		
		
		
		String action = request.getParameter("action");
		if(action.equals("taoBC"))
		{
			//XEM BÁO CÁO THEO DÕI
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCTheodoiDMH.xlsm");
			
			try 
			{
				CreatePivotTable(out, obj, trangthai, tungay, denngay, loaingay);
			} 
			catch (Exception e) 
			{
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiDMH.jsp";
				obj.setmsg("Không thể tạo báo cáo - " + e.getMessage());
				
				response.sendRedirect(nextJSP);	
			}
		}
		else 
			if(action.equals("xemNH"))
			{
				//XEM BÁO CÁO NHẬN HÀNG
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCTheodoiNhanHang.xlsm");
				
				try 
				{
					CreatePivotTableTheoDoiNhan(out, obj, trangthai, tungay, denngay);
				} 
				catch (Exception e) 
				{
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiDMH.jsp";
					obj.setmsg("Không thể tạo báo cáo - " + e.getMessage());
					
					response.sendRedirect(nextJSP);	
				}
			}
			else
			{
				obj.initBaoCao();
			    
				session.setAttribute("obj", obj);
						
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiDMH.jsp";
				response.sendRedirect(nextJSP);
			}
		
	}
	
	private boolean CreatePivotTable(OutputStream out, IErpDonmuahangList obj, String trangthai, String tungay, String denngay, String loaingay) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		 

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpTheoDoiDMH.xlsm");

		
		// -- báo cáo mới --//
		/*fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCTheodoiDMH.xlsm");*/
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
	    /* CreateStaticHeader(workbook, obj.getTungay(), obj.getDenngay(), obj.getUserId(), obj.getPivot() != null && obj.getPivot().equals("1"));*/	     
	    boolean isTrue = CreateStaticData(workbook, obj, trangthai, tungay, denngay, loaingay);
	     if(!isTrue){
	    	 return false;
	     }
	     workbook.save(out);
			
		fstream.close();
	     return true;
   }
	
	private boolean CreatePivotTableTheoDoiNhan(OutputStream out, IErpDonmuahangList obj, String trangthai, String tungay, String denngay) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		 

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpNhacNhanHang.xlsm");

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		boolean isTrue = CreateStaticDataTheoDoiNhan(workbook, obj, trangthai, tungay, denngay);
	     if(!isTrue){
	    	 return false;
	     }
	     workbook.save(out);
			
		fstream.close();
	     return true;
   }
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName, boolean pivot) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    worksheet.setName("Sheet1");
	    
	    String col = pivot ? "A" : "";
		String row = pivot ? "1" : "8";
	    
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
	    
	    String tieude = "BÁO CÁO THEO DÕI ĐƠN MUA HÀNG";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	         
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + dateFrom + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B2"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + dateTo + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);
	    
	    cells.setRowHeight(6, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, pivot ? "PIVOT" : "");
	    

	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    cell = cells.getCell(col+"A"+row); 	cell.setValue("MaDonMuaHang");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"B"+row); 	cell.setValue("NgayDonMuaHang");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"C"+row); 	cell.setValue("NhaCungCap");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"D"+row); 	cell.setValue("MaSanPham");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"E"+row); 	cell.setValue("MA");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"F"+row); 	cell.setValue("TenSanPham"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"G"+row); 	cell.setValue("Dai"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"H"+row); 	cell.setValue("Rong"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"I"+row); 	cell.setValue("DinhLuong"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"J"+row); 	cell.setValue("NguonGoc"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"K"+row); 	cell.setValue("DonViTinh");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"L"+row); 	cell.setValue("SoLuong");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"M"+row); 	cell.setValue("DonGia");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"N"+row); 	cell.setValue("ThanhTien");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"O"+row); 	cell.setValue("TienTe");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"P"+row); 	cell.setValue("NgayGiao");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"Q"+row); 	cell.setValue("SoLuongDaNhan");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"R"+row); 	cell.setValue("NgayNhan");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"S"+row); 	cell.setValue("SoPhieuNhap");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"T"+row); 	cell.setValue("SoHoaDon");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"U"+row); 	cell.setValue("SoLuongConLai");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"V"+row); 	cell.setValue("TrangThai");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"W"+row); 	cell.setValue("NhomSanPham");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"X"+row); 	cell.setValue("GhiChu");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell(col+"Y"+row); 	cell.setValue("LoaiGiaMua");  ReportAPI.setCellHeader(cell);
	    
	}
	
	
	
	private boolean CreateStaticData(Workbook workbook, IErpDonmuahangList obj, String trang_thai, String tungay, String denngay, String loaingay) throws Exception
	{
		
		
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
				
		
		Font font = new Font();
	 
		String query="	SELECT Distinct A.*,(A.SOLUONG-A.SOLUONGDANHAN) as SOLUONGCONLAI  FROM( \n"+
			 	"	     (SELECT ISNULL(DATHANG.MHID,NHANHANG.MHID) AS MUAHANG_FK, ISNULL(DATHANG.MASP,'') AS MASANPHAM, ISNULL(DATHANG.MAFAST,'') AS MAFAST,  \n"+
				"	     DATHANG.NGAYMUA, DATHANG.POID,DATHANG.NCC as TENNCC, ISNULL(DATHANG.TEN,DATHANG.DIENGIAI)  AS TENSP , \n"+
				"	     DATHANG.DONVI,   DATHANG.SOLUONG, DATHANG.DONGIA, DATHANG.THANHTIEN ,DATHANG.TIENTE, \n"+
				"	 	 ISNULL(DATHANG.NGAYDENGHI,'') AS NGAYYEUCAUNHAN, ISNULL(NHANHANG.SOLUONGNHAN,0) AS SOLUONGDANHAN, \n"+
				"	     NHANHANG.NHID, ISNULL(NHANHANG.NGAYNHAN,'') AS NGAYNHAN,  \n"+
				"	     ISNULL(NHANHANG.DONGIA, DATHANG.DONGIA) AS DONGIANHAN, \n"+
				"	     ISNULL(NHANHANG.SOHOADON,'') AS SOHOADON, \n"+
				"	     ISNULL(NHANHANG.NGAYHOADON,'') AS NGAYHOADON,  DATHANG.TRANGTHAI, \n"+
				"	     DATHANG.DONVITHUCHIEN_FK,  DATHANG.NHACUNGCAP_FK,DATHANG.GHICHU, NHANHANG.KHONHAN, NHANHANG.SOLO , ISNULL(NHANHANG.SOLUONGHD,0) SOLUONGHD , DATHANG.SOHOPDONG, isNull(CPLK.CHIPHI,0) CHIPHI,  \n"+
				"        ROW_NUMBER() OVER(PARTITION BY DATHANG.MHID,DATHANG.SPID,DATHANG.DONVI,ISNULL(DATHANG.NGAYDENGHI,'') ORDER BY DATHANG.MHID ASC) AS ROW ,  \n"+
				"        ROW_NUMBER() OVER(PARTITION BY DATHANG.MHID,DATHANG.SPID,DATHANG.DONVI,ISNULL(DATHANG.NGAYDENGHI,''), ISNULL(NHANHANG.SOHOADON,'') ORDER BY DATHANG.MHID ASC) AS ROWHD   \n"+
				"	 FROM   \n"+
				"	 	( \n"+
				"	 		SELECT MH.PK_SEQ AS MHID,  MHSP.SANPHAM_FK AS SPID, MH.NGAYTAO, MH.SOPO AS POID, ISNULL(NCC.TEN,'') AS NCC, \n"+
				"	 			   ISNULL(SP.MA_FAST,'') AS MAFAST, ISNULL(SP.MA,'') AS MASP, MH.NGAYMUA   ,  MHSP.DONVI, MHSP.SOLUONG,    \n"+
				"	 		       MHSP.DONGIA, (MHSP.DONGIA*MHSP.SOLUONG) AS THANHTIEN,TIENTE.TEN as TIENTE, \n"+
				"	 			   ISNULL(MHSP.NGAYNHAN,'') AS NGAYDENGHI, MH.TRANGTHAI,  \n"+
				"	 		       DVTH.MA, SP.TEN, MHSP.DIENGIAI, MH.NGUOITAO, SP.LOAISANPHAM_FK , MH.DONVITHUCHIEN_FK, MH.NHACUNGCAP_FK,MH.GHICHU, isnull(MH.SOHOPDONG, '') SOHOPDONG \n"+
				"	 		FROM ERP_MUAHANG MH     \n"+
				"	 		     INNER JOIN ERP_MUAHANG_SP MHSP ON MH.PK_SEQ=MHSP.MUAHANG_FK   \n"+ 
				"	 		     LEFT JOIN ERP_NHACUNGCAP NCC ON MH.NHACUNGCAP_FK=NCC.PK_SEQ \n"+
				"	 		     LEFT JOIN SANPHAM SP ON SP.PK_SEQ=MHSP.SANPHAM_FK \n"+
				"			     inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ \n"+
				"	 		     INNER JOIN ERP_DONVITHUCHIEN DVTH ON MH.DONVITHUCHIEN_FK= DVTH.PK_SEQ  \n"+  
				"	 		WHERE ISNULL(MH.ISDNTT,0) = 0 AND MHSP.SANPHAM_FK is not null  \n"+									
				"	 ) DATHANG   \n"+
				"	 LEFT  JOIN       \n"+
				"	 ( "+
				"	 		SELECT DISTINCT  NHSP.SANPHAM_FK AS SPID, NH.PK_SEQ AS NHID  ,ISNULL(NHSPCT.SOLUONG,0) AS SOLUONGNHAN , ISNULL(NHSPCT.SOLO,'') SOLO, ISNULL(HDCT.SOLUONG,0) AS SOLUONGHD, \n"+
				"	 		       MH.PK_SEQ AS MHID, NH.NGAYNHAN, NHSP.DONGIA, MH.SOPO AS POID,  HD.SOHOADON, HD.NGAYHOADON, MH.PK_SEQ, NHSP.DIENGIAI,TIENTE.TEN as TIENTE, KHO.TEN KHONHAN   \n"+
				"	 		FROM ERP_NHANHANG NH  INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ = NHSP.NHANHANG_FK     \n"+
				"	 		     INNER JOIN ERP_NHANHANG_SP_CHITIET NHSPCT ON NHSP.NHANHANG_FK=NHSPCT.NHANHANG_FK AND NHSP.SANPHAM_FK = NHSPCT.SANPHAM_FK     \n"+
				"	 		     INNER JOIN KHO  ON KHO.PK_SEQ = NHSP.KHONHAN     \n"+
				"	 		     INNER JOIN ERP_MUAHANG MH ON NHSP.MUAHANG_FK=MH.PK_SEQ     \n"+
				"	 		     LEFT JOIN SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK \n"+
				"			     inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ  \n"+
				"	 		     INNER JOIN ERP_HOADONNCC HD ON  HD.PK_SEQ= NH.hdNCC_fk   \n"+	
				"	 		     INNER JOIN ERP_HOADONNCC_DONMUAHANG HDCT ON  HD.PK_SEQ= HDCT.HOADONNCC_FK AND NHSPCT.SANPHAM_FK = HDCT.SANPHAM_FK   \n"+	
				"	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is not null    \n"+
				"	 ) NHANHANG  ON   DATHANG.MHID=NHANHANG.MHID AND DATHANG.SPID= NHANHANG.SPID  "+
				"   LEFT JOIN  \n"+
				"	( \n"+
				"		SELECT NH.PK_SEQ NHID , CPLK.MUAHANG_FK MHID, isnull(CPLK.THANHTIEN,0) CHIPHI, CPLK.SANPHAM_FK AS SPID  "+
				"		FROM ERP_NHANHANG NH INNER JOIN ERP_CHIPHILUUKHO_SP_CHITIET CPLK ON NH.PK_SEQ = CPLK.NHANHANG_FK \n"+
				"	) CPLK ON  DATHANG.MHID = CPLK.MHID AND DATHANG.SPID = CPLK.SPID AND NHANHANG.NHID = CPLK.NHID  \n"+
				
				
				
				") \n"+

				"	 union all "+
 
				"	 (SELECT ISNULL(DATHANG.MHID,NHANHANG.MHID) AS MUAHANG_FK, ISNULL(DATHANG.MASP,'') AS MASANPHAM, '' AS MAFAST, \n"+
				"	         DATHANG.NGAYMUA, DATHANG.POID,DATHANG.NCC as TENNCC, ISNULL(DATHANG.TEN,DATHANG.DIENGIAI)  AS TENSP , \n"+
				"	         DATHANG.DONVI,   DATHANG.SOLUONG, DATHANG.DONGIA, (DATHANG.SOLUONG* DATHANG.DONGIA) as THANHTIEN,DATHANG.TIENTE, \n"+
				"	         ISNULL(DATHANG.NGAYDENGHI,'') AS NGAYYEUCAUNHAN, ISNULL(NHANHANG.SOLUONGNHAN,0) AS SOLUONGDANHAN, \n"+
				"	         NHANHANG.NHID, ISNULL(NHANHANG.NGAYNHAN,'') AS NGAYNHAN,  \n"+
				"	         ISNULL(NHANHANG.DONGIA, DATHANG.DONGIA) AS DONGIANHAN, \n"+
				"	         ISNULL(NHANHANG.SOHOADON,'') AS SOHOADON, "+
				"	         ISNULL(NHANHANG.NGAYHOADON,'') AS NGAYHOADON,  DATHANG.TRANGTHAI, \n"+
				"	         DATHANG.DONVITHUCHIEN_FK,  DATHANG.NHACUNGCAP_FK,DATHANG.GHICHU, NHANHANG.KHONHAN, NHANHANG.SOLO, ISNULL(NHANHANG.SOLUONGHD,0) SOLUONGHD, '' SOHOPDONG, isnull(CPLK.CHIPHI,0) CHIPHI ,    \n"+
				"            ROW_NUMBER() OVER(PARTITION BY DATHANG.MHID,DATHANG.SPID,DATHANG.DONVI,ISNULL(DATHANG.NGAYDENGHI,'') ORDER BY DATHANG.MHID ASC) AS ROW,   \n"+
				"        	 ROW_NUMBER() OVER(PARTITION BY DATHANG.MHID,DATHANG.SPID,DATHANG.DONVI,ISNULL(DATHANG.NGAYDENGHI,''), ISNULL(NHANHANG.SOHOADON,'') ORDER BY DATHANG.MHID ASC) AS ROWHD   \n"+
				"	 FROM   \n"+
				"	 	( \n"+
				"	 		SELECT MH.PK_SEQ AS MHID,  isnull(MHSP.SANPHAM_FK, MHSP.CHIPHI_FK) AS SPID, MH.NGAYTAO, MH.SOPO AS POID, ISNULL(NCC.TEN,'') AS NCC, \n"+
				"	 		       CASE WHEN TS.PK_SEQ IS NOT NULL THEN TS.MA WHEN CCDC.PK_SEQ IS NOT NULL THEN CCDC.MA  ELSE CP.TEN END AS MASP, "+
				"                  MH.NGAYMUA   , MHSP.DONVI, MHSP.SOLUONG,    \n"+
				"	 		       MHSP.DONGIA, (MHSP.DONGIA*MHSP.SOLUONG) AS THANHTIEN,TIENTE.TEN as TIENTE, ISNULL( MH.SOTHAMCHIEU,'')AS SPYC,  \n"+
				"	 		       ISNULL(MHSP.NGAYNHAN,'') AS NGAYDENGHI, MH.TRANGTHAI,  SUBSTRING(CAST(MHSP.NGAYNHAN AS VARCHAR(10)),6,2)  AS THANGDAT , \n"+
				"	 		       DVTH.MA, CASE WHEN TS.PK_SEQ IS NOT NULL THEN TS.ten WHEN CCDC.PK_SEQ IS NOT NULL THEN CCDC.DIENGIAI  ELSE CP.DIENGIAI END AS TEN,  "+
				"                  MHSP.DIENGIAI, MH.NGUOITAO, 0 LOAISANPHAM_FK , MH.DONVITHUCHIEN_FK, MH.NHACUNGCAP_FK,MH.GHICHU \n"+
				"	 		FROM ERP_MUAHANG MH     \n"+
				"	 		     INNER JOIN ERP_MUAHANG_SP MHSP ON MH.PK_SEQ=MHSP.MUAHANG_FK      \n"+
				"	 		     LEFT JOIN ERP_NHACUNGCAP NCC ON MH.NHACUNGCAP_FK=NCC.PK_SEQ \n"+
				"			     inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ \n"+
				"	 		     LEFT JOIN ERP_TAISANCODINH TS ON TS.PK_SEQ=MHSP.TAISAN_FK   \n"+
				"	 		     LEFT JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.PK_SEQ=MHSP.CCDC_FK   \n"+
				"	 		     LEFT JOIN ERP_NHOMCHIPHI CP ON CP.PK_SEQ=MHSP.CHIPHI_FK   \n"+
				"	 		     INNER JOIN ERP_DONVITHUCHIEN DVTH ON MH.DONVITHUCHIEN_FK= DVTH.PK_SEQ    \n"+
				"	 		WHERE ISNULL(MH.ISDNTT,0) = 0 AND MHSP.SANPHAM_FK is null  \n";

		query +=
				"	 ) DATHANG   \n"+
				"	 LEFT  JOIN       \n"+
				"	 ( \n"+
				"	 		SELECT DISTINCT CASE WHEN TS.PK_SEQ IS NOT NULL THEN TS.PK_SEQ WHEN CCDC.PK_SEQ IS NOT NULL THEN CCDC.PK_SEQ  ELSE CP.PK_SEQ END  AS SPID, "+
				"                 NH.PK_SEQ AS NHID  ,ISNULL(NHSP.SOLUONGNHAN,0) AS SOLUONGNHAN , '' SOLO, ISNULL(HDCT.SOLUONG,0) AS SOLUONGHD, \n"+
				"	 		       MH.PK_SEQ AS MHID, NH.NGAYNHAN, NHSP.DONGIA, TIENTE.TEN as TIENTE,MH.SOPO AS POID,  HD.SOHOADON, HD.NGAYHOADON, MH.PK_SEQ, NHSP.DIENGIAI, ISNULL(KHO.TEN,'') KHONHAN    \n"+
				"	 		FROM ERP_NHANHANG NH     INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK     \n"+
				"	 		     INNER JOIN ERP_MUAHANG MH ON NHSP.MUAHANG_FK = MH.PK_SEQ  \n"+
				"	 		     LEFT JOIN KHO  ON KHO.PK_SEQ = NHSP.KHONHAN     \n"+
				"			     inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ    \n"+
				"	 		     LEFT JOIN ERP_TAISANCODINH TS ON TS.PK_SEQ=NHSP.TAISAN_FK   \n"+
				"	 		     LEFT JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.PK_SEQ=NHSP.CCDC_FK   \n"+
				"	 		     LEFT JOIN ERP_NHOMCHIPHI CP ON CP.PK_SEQ=NHSP.CHIPHI_FK   \n"+
				"	 		     LEFT JOIN ERP_HOADONNCC HD ON  HD.PK_SEQ= NH.hdNCC_fk   \n"+
				"	 		     INNER JOIN ERP_HOADONNCC_DONMUAHANG HDCT ON  HD.PK_SEQ= HDCT.HOADONNCC_FK  AND ( NHSP.TAISAN_FK = HDCT.TAISAN_FK OR  NHSP.CCDC_FK = HDCT.CCDC_FK OR  NHSP.CHIPHI_FK = HDCT.CHIPHI_FK)  \n"+
				"	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is null  \n";
		
		query +=
				"	 ) NHANHANG  \n"+
				"	 ON   DATHANG.MHID=NHANHANG.MHID AND DATHANG.SPID= NHANHANG.SPID   and DATHANG.DIENGIAI = NHANHANG.DIENGIAI "+
				"   LEFT JOIN  \n"+
				"	( \n"+
				"		SELECT NH.PK_SEQ NHID , CPLK.MUAHANG_FK MHID, isnull(CPLK.THANHTIEN,0) CHIPHI, CPLK.SANPHAM_FK AS SPID  "+
				"		FROM ERP_NHANHANG NH INNER JOIN ERP_CHIPHILUUKHO_SP_CHITIET CPLK ON NH.PK_SEQ = CPLK.NHANHANG_FK \n"+
				"	) CPLK ON  DATHANG.MHID = CPLK.MHID AND DATHANG.SPID = CPLK.SPID AND NHANHANG.NHID = CPLK.NHID \n"+				
				"	) \n"+
				" )A WHERE 1=1   ";

		if (trang_thai.length() > 0){
			if(trang_thai.equals("-1")){ // Đã nhận hàng			
				query += " and A.NHID is not null ";
			}else{
				query += " and A.TRANGTHAI = '" + trang_thai + "'";
			}
		}
		
		if (obj.getDvthId().length() > 0){
			query += " and A.donvithuchien_fk = '" + obj.getDvthId() + "'";
		}
		
		if (obj.getNccIds().length() > 0){
			query += " and A.NHACUNGCAP_FK in (" + obj.getNccIds() + ")";
		}
		
		// 0 : ngày mua; 1: ngày nhận; 2: ngày hóa đơn
		if(loaingay.equals("0")){
			query += " and A.NGAYMUA >='"+tungay+"'   AND A.NGAYMUA <='"+denngay+"' \n";
		}else if(loaingay.equals("1")){
			query += " and A.NGAYNHAN >='"+tungay+"'   AND A.NGAYNHAN <='"+denngay+"' \n";
		}else if (loaingay.equals("2")){
			query += " and A.NGAYHOADON >='"+tungay+"'   AND A.NGAYHOADON <='"+denngay+"' \n";
		}
		
		query +=" ORDER BY A.MUAHANG_FK, A.SOHOADON, A.NHID " ;
		
		System.out.println("Theo doi Don Mua Hang TT hien tai 1: " + query);
		ResultSet rs = db.get(query);
		
		
		
		// --- đổ dữ liệu ---//
		
		int index =6;
		try{
			//CHÈN VÀO EXCEL
			int indexHeader = index-1;
			Cells cells = worksheet.getCells();	
			
			Cell cell = cells.getCell("A2");
		    cell.setValue(" Từ ngày :  " + tungay + " 			Đến ngày : " + denngay);
	/*	    cell = cells.getCell("A3");
		    cell.setValue("Người tạo: ");	
		    cell = cells.getCell("A4");
		    cell.setValue("Đơn vị thực hiện: " );
		    cell = cells.getCell("A5");
		    cell.setValue("Loại sản phẩm: ");
		    cell = cells.getCell("A6");
		    cell.setValue("Nhà cung cấp: ");*/
		    
			cell=null;
			Style style= null;
			
			
			if(obj.getPivot().equals("1")){
				cell = cells.getCell("BA" + String.valueOf(indexHeader));		
				cell.setValue("SODONMUA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("BB" + String.valueOf(indexHeader));		
				cell.setValue("NGAYMUA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BC" + String.valueOf(indexHeader));		
				cell.setValue("TENNCC");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BD" + String.valueOf(indexHeader));		
				cell.setValue("MASANPHAM");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BE" + String.valueOf(indexHeader));		
				cell.setValue("TENSANPHAM");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BF" + String.valueOf(indexHeader));		
				cell.setValue("DONVI");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BG" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONG");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BH" + String.valueOf(indexHeader));		
				cell.setValue("DONGIA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BI" + String.valueOf(indexHeader));		
				cell.setValue("THANHTIEN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BJ" + String.valueOf(indexHeader));		
				cell.setValue("TIENTE");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BK" + String.valueOf(indexHeader));		
				cell.setValue("NGAYYEUCAUNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BL" + String.valueOf(indexHeader));		
				cell.setValue("NGAYNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BM" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BN" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGCONLAI");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("BO" + String.valueOf(indexHeader));		
				cell.setValue("GHICHU");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
			}else{
				cell = cells.getCell("A" + String.valueOf(indexHeader));		
				cell.setValue("SODONMUA");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("B" + String.valueOf(indexHeader));		
				cell.setValue("NGAYMUA");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("C" + String.valueOf(indexHeader));		
				cell.setValue("TENNCC");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("D" + String.valueOf(indexHeader));		
				cell.setValue("SONHANHANG");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("E" + String.valueOf(indexHeader));		
				cell.setValue("SOHOADON");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("F" + String.valueOf(indexHeader));		
				cell.setValue("KHONHAN");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("G" + String.valueOf(indexHeader));		
				cell.setValue("MASANPHAM");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("H" + String.valueOf(indexHeader));		
				cell.setValue("MAFAST");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("I" + String.valueOf(indexHeader));		
				cell.setValue("TENSANPHAM");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("J" + String.valueOf(indexHeader));		
				cell.setValue("DONVI");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("K" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGDAT");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("L" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGHD");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("M" + String.valueOf(indexHeader));		
				cell.setValue("DONGIA");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("N" + String.valueOf(indexHeader));		
				cell.setValue("THANHTIEN");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);				
				
				cell = cells.getCell("O" + String.valueOf(indexHeader));		
				cell.setValue("SOLO");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("P" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGNHAN");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("Q" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGCONLAI");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("R" + String.valueOf(indexHeader));		
				cell.setValue("NGAYYEUCAUNHAN");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("S" + String.valueOf(indexHeader));		
				cell.setValue("NGAYNHAN");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);		
				
				cell = cells.getCell("T" + String.valueOf(indexHeader));		
				cell.setValue("NGAYHOADON");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);		
				
				cell = cells.getCell("U" + String.valueOf(indexHeader));		
				cell.setValue("TIENTE");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("V" + String.valueOf(indexHeader));		
				cell.setValue("GHICHU");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("W" + String.valueOf(indexHeader));		
				cell.setValue("SỐ HỢP ĐỒNG NGOẠI");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("X" + String.valueOf(indexHeader));		
				cell.setValue("CHI PHÍ LƯU KHO");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
			}
			
			
			while(rs.next()){
				cell=null;
				style= null;
				NumberFormat formatter = new DecimalFormat("#,###,###.###");

				String MUAHANG_FK = rs.getString("POID");
				String NGAYMUA = rs.getString("NGAYMUA");
				String TENNCC = rs.getString("TENNCC");
				String SONHANHANG = rs.getString("NHID");
				String SOHOADON = rs.getString("SOHOADON");
				String KHONHAN = rs.getString("KHONHAN");
				String MASANPHAM = rs.getString("MASANPHAM");
				String MAFAST = rs.getString("MAFAST");
				String TENSP = rs.getString("TENSP");
				String DONVI = rs.getString("DONVI");
				String SOLUONG = rs.getString("SOLUONG");
				String SOLUONGHD = rs.getString("SOLUONGHD");
				String DONGIA = rs.getString("DONGIA");
				String THANHTIEN = rs.getString("THANHTIEN");
				String TIENTE = rs.getString("TIENTE");
				String NGAYYEUCAUNHAN = rs.getString("NGAYYEUCAUNHAN");
				String NGAYNHAN = rs.getString("NGAYNHAN");
				String NGAYHOADON = rs.getString("NGAYHOADON");
				String SOLO = rs.getString("SOLO");
				String SOLUONGNHAN = rs.getString("SOLUONGDANHAN");
				String SOLUONGCONLAI = rs.getString("SOLUONGCONLAI");
				String GHICHU = rs.getString("GHICHU");		
				String HDNGOAI = rs.getString("SOHOPDONG");	
				String CPLK = rs.getString("CHIPHI");	
				int ROW = rs.getInt("ROW");
				int ROWHD = rs.getInt("ROWHD");
				
				if(ROW != 1) SOLUONG = "0";
				if(ROWHD != 1) SOLUONGHD = "0";
				
				//CHÈN VÀO EXCEL
				
				if(obj.getPivot().equals("1")){
					cell = cells.getCell("BA" + String.valueOf(index));		
					cell.setValue(MUAHANG_FK);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("BB" + String.valueOf(index));		
					cell.setValue(NGAYMUA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BC" + String.valueOf(index));		
					cell.setValue(TENNCC);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BD" + String.valueOf(index));		
					cell.setValue(MASANPHAM);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BE" + String.valueOf(index));		
					cell.setValue(TENSP);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BF" + String.valueOf(index));		
					cell.setValue(DONVI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BG" + String.valueOf(index));		
					cell.setValue(SOLUONG);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BH" + String.valueOf(index));		
					cell.setValue(DONGIA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BI" + String.valueOf(index));		
					cell.setValue(THANHTIEN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BJ" + String.valueOf(index));		
					cell.setValue(TIENTE);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BK" + String.valueOf(index));		
					cell.setValue(NGAYYEUCAUNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BL" + String.valueOf(index));		
					cell.setValue(NGAYNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BM" + String.valueOf(index));		
					cell.setValue(SOLUONGNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BN" + String.valueOf(index));		
					cell.setValue(SOLUONGCONLAI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("BO" + String.valueOf(index));		
					cell.setValue(GHICHU);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
				}else{
					cell = cells.getCell("A" + String.valueOf(index));		
					cell.setValue(MUAHANG_FK);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("B" + String.valueOf(index));		
					cell.setValue(NGAYMUA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(TENNCC);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("D" + String.valueOf(index));		
					cell.setValue(SONHANHANG);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("E" + String.valueOf(index));		
					cell.setValue(SOHOADON);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("F" + String.valueOf(index));		
					cell.setValue(KHONHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("G" + String.valueOf(index));		
					cell.setValue(MASANPHAM);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("H" + String.valueOf(index));		
					cell.setValue(MAFAST);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("I" + String.valueOf(index));		
					cell.setValue(TENSP);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("J" + String.valueOf(index));		
					cell.setValue(DONVI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("K" + String.valueOf(index));		
					cell.setValue(Double.parseDouble(SOLUONG));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("L" + String.valueOf(index));		
					cell.setValue(Double.parseDouble(SOLUONGHD));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("M" + String.valueOf(index));		
					cell.setValue(Double.parseDouble(DONGIA));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("N" + String.valueOf(index));		
					cell.setValue( Double.parseDouble(THANHTIEN));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("O" + String.valueOf(index));		
					cell.setValue(SOLO);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("P" + String.valueOf(index));		
					cell.setValue(Double.parseDouble( SOLUONGNHAN ));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("Q" + String.valueOf(index));		
					cell.setValue(Double.parseDouble(SOLUONGCONLAI));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("R" + String.valueOf(index));		
					cell.setValue(NGAYYEUCAUNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("S" + String.valueOf(index));		
					cell.setValue(NGAYNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("T" + String.valueOf(index));		
					cell.setValue( NGAYHOADON);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					
					cell = cells.getCell("U" + String.valueOf(index));		
					cell.setValue( TIENTE);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("V" + String.valueOf(index));		
					cell.setValue(GHICHU);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("W" + String.valueOf(index));		
					cell.setValue(HDNGOAI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("X" + String.valueOf(index));		
					cell.setValue(Double.parseDouble(CPLK));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
				}
				
				
				index++;
				
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally
		{
		db.shutDown();
		}
		
		
		
	}
	
	private boolean CreateStaticDataTheoDoiNhan(Workbook workbook, IErpDonmuahangList obj, String trang_thai, String tungay, String denngay) throws Exception
	{		
		
		
		dbutils db = new dbutils();
		Utility util = new Utility();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		
		Font font = new Font();
	 	
	
		String query="	SELECT   Distinct A.*,(A.SOLUONG-A.SOLUONGDANHAN) as SOLUONGCONLAI  FROM( "+
				 	"	(SELECT ISNULL(DATHANG.MHID,NHANHANG.MHID) AS MUAHANG_FK,ISNULL(DATHANG.SPID,NHANHANG.SPID) AS MASANPHAM,  "+
					"	 DATHANG.NGAYMUA, DATHANG.POID,DATHANG.NCC as TENNCC, ISNULL(DATHANG.TEN,DATHANG.DIENGIAI)  AS TENSP , "+
					"	  DATHANG.DONVI,   DATHANG.SOLUONG, DATHANG.DONGIA, DATHANG.THANHTIEN ,DATHANG.TIENTE, "+
					"	 ISNULL(DATHANG.NGAYDENGHI,'') AS NGAYYEUCAUNHAN, ISNULL(NHANHANG.SOLUONGNHAN,0) AS SOLUONGDANHAN, "+
					"	 NHANHANG.NHID, ISNULL(NHANHANG.NGAYNHAN,'') AS NGAYNHAN,  "+
					"	 ISNULL(NHANHANG.DONGIA, DATHANG.DONGIA) AS DONGIANHAN, "+
					"	 ISNULL(NHANHANG.SOHOADON,'') AS SOHOADON, "+
					"	 ISNULL(NHANHANG.NGAYHOADON,'') AS NGAYHOADON,  DATHANG.NGUOITAO, "+
					"	 DATHANG.DONVITHUCHIEN_FK,  DATHANG.NHACUNGCAP_FK,DATHANG.GHICHU      "+
					"	 FROM   "+
					"	 	( "+
					"	 		SELECT MH.PK_SEQ AS MHID,  MHSP.SANPHAM_FK AS SPID, MH.NGAYTAO, MH.SOPO AS POID, ISNULL(NCC.TEN,'') AS NCC, "+
					"	 		ISNULL(SP.MA,'') AS ITEM, MH.NGAYMUA   ,  MHSP.DONVI, MHSP.SOLUONG,    "+
					"	 		MHSP.DONGIA, (MHSP.DONGIA*MHSP.SOLUONG) AS THANHTIEN,TIENTE.TEN as TIENTE, "+
					"	 		ISNULL(MHSP.NGAYNHAN,'') AS NGAYDENGHI,   "+
					"	 		DVTH.MA, SP.TEN, MHSP.DIENGIAI, MH.NGUOITAO, SP.LOAISANPHAM_FK , MH.DONVITHUCHIEN_FK, MH.NHACUNGCAP_FK,MH.GHICHU "+
					"	 		FROM ERP_MUAHANG MH     "+
					"	 		INNER JOIN ERP_MUAHANG_SP MHSP ON MH.PK_SEQ=MHSP.MUAHANG_FK   "+   
					"	 		INNER JOIN ERP_NHACUNGCAP NCC ON MH.NHACUNGCAP_FK=NCC.PK_SEQ "+
					"	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=MHSP.SANPHAM_FK "+
					"			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ "+
					"	 		INNER JOIN ERP_DONVITHUCHIEN DVTH ON MH.DONVITHUCHIEN_FK= DVTH.PK_SEQ  "+  
					"	 		LEFT JOIN(  SELECT 	MUAHANG_FK AS DMHID,  "+
					"	 		CASE WHEN SUM(QUYETDINH) > 0 THEN  "+
					"	 		(CASE WHEN  ( SELECT SUM(TRANGTHAI)    FROM ERP_DUYETMUAHANG  	 "+
					"	 		WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1) > 0 THEN 0  ELSE 1  END)	  "+
					"	 		ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	  END AS DUYET    "+
					"	 		FROM ERP_DUYETMUAHANG DUYETMUAHANG  "+
					"	 		GROUP BY MUAHANG_FK  ) DUYET ON DUYET.DMHID = MH.PK_SEQ     "+
					"	 		WHERE MHSP.SANPHAM_FK is not null "+//and MH.NGAYMUA >='"+tungay+"'   AND MH.NGAYMUA <='"+denngay+"' "+
					"	 ) DATHANG   "+
					"	 LEFT  JOIN       "+
					"	 ( "+
					"	 		SELECT NHSP.SANPHAM_FK AS SPID,NH.PK_SEQ AS NHID  ,ISNULL(NHSP.SOLUONGNHAN,0) AS SOLUONGNHAN , "+
					"	 		MH.PK_SEQ AS MHID, NH.NGAYNHAN, NHSP.DONGIA, MH.SOPO AS POID,  NH.SOHOADON, HD.NGAYHOADON, MH.PK_SEQ, NHSP.DIENGIAI,TIENTE.TEN as TIENTE   "+
					"	 		FROM ERP_NHANHANG NH     INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK     "+
					"	 		INNER JOIN ERP_MUAHANG MH ON NH.MUAHANG_FK=MH.PK_SEQ     "+
					"	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK "+
					"			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ  "+
					"	 		LEFT JOIN ERP_HOADONNCC_PHIEUNHAP HDPN ON  NH.PK_SEQ= HDPN.PHIEUNHAN_FK   "+
					"	 		LEFT JOIN ERP_HOADONNCC HD ON  HD.PK_SEQ= HDPN.HOADONNCC_FK   "+
					"	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is not null   "+ //AND MH.NGAYMUA >='"+tungay+"'    AND MH.NGAYMUA <='"+denngay+"' "+
					"	 		UNION ALL      "+
					"	 		SELECT  "+
					"	 		NHSP.SANPHAM_FK AS SPID,NH.PK_SEQ AS NHID, "+
					"	 		ISNULL(NHSP.SOLUONGNHAN,0) AS SOLUONGNHAN,  NHSP.MUAHANG_FK AS MHID, NH.NGAYNHAN, NHSP.DONGIA, MH.SOPO AS POID, "+
					"	 		NH.SOHOADON, HD.NGAYHOADON  , MH.PK_SEQ ,TIENTE.TEN as TIENTE, NHSP.DIENGIAI       "+
					"	 		FROM ERP_NHANHANG NH    "+
					"	 		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK     "+
					"	 		INNER JOIN ERP_THUENHAPKHAU  TNK ON TNK.PK_SEQ=NH.SOTOKHAI_FK     "+
					"	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK     "+
					"	 		INNER JOIN ERP_MUAHANG MH ON NHSP.MUAHANG_FK=MH.PK_SEQ    "+
					"			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ "+
					"	 		INNER JOIN ERP_THUENHAPKHAU_HOADONNCC TNK_HD ON TNK.PK_SEQ= TNK_HD.THUENHAPKHAU_FK   "+
					"	 		LEFT JOIN ERP_HOADONNCC HD ON HD.PK_SEQ= TNK_HD.HOADONNCC_FK   "+
					"	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is not null  AND NH.MUAHANG_FK IS NULL  AND NHSP.MUAHANG_FK IS NOT NULL "+  
					//"	 		AND MH.NGAYMUA >='"+tungay+"'   AND MH.NGAYMUA <='"+denngay+"' "+
					"	 ) NHANHANG  ON   DATHANG.MHID=NHANHANG.MHID AND DATHANG.SPID= NHANHANG.SPID  ) "+

					"	 union all "+
	 
					"	 (SELECT ISNULL(DATHANG.MHID,NHANHANG.MHID) AS MUAHANG_FK,ISNULL(DATHANG.SPID,NHANHANG.SPID) AS MASANPHAM,  "+
					"	 DATHANG.NGAYMUA, DATHANG.POID,DATHANG.NCC as TENNCC, ISNULL(DATHANG.TEN,DATHANG.DIENGIAI)  AS TENSP , "+
					"	  DATHANG.DONVI,   DATHANG.SOLUONG, DATHANG.DONGIA, (DATHANG.SOLUONG* DATHANG.DONGIA) as THANHTIEN,DATHANG.TIENTE, "+
					"	 ISNULL(DATHANG.NGAYDENGHI,'') AS NGAYYEUCAUNHAN, ISNULL(NHANHANG.SOLUONGNHAN,0) AS SOLUONGDANHAN, "+
					"	 NHANHANG.NHID, ISNULL(NHANHANG.NGAYNHAN,'') AS NGAYNHAN,  "+
					"	 ISNULL(NHANHANG.DONGIA, DATHANG.DONGIA) AS DONGIANHAN, "+
					"	 ISNULL(NHANHANG.SOHOADON,'') AS SOHOADON, "+
					"	 ISNULL(NHANHANG.NGAYHOADON,'') AS NGAYHOADON,  DATHANG.NGUOITAO, "+
					"	 DATHANG.DONVITHUCHIEN_FK,  DATHANG.NHACUNGCAP_FK,DATHANG.GHICHU      "+
					"	 FROM   "+
					"	 	( "+
					"	 		SELECT MH.PK_SEQ AS MHID,  isnull(MHSP.SANPHAM_FK, MHSP.CHIPHI_FK) AS SPID, MH.NGAYTAO, MH.SOPO AS POID, ISNULL(NCC.TEN,'') AS NCC, "+
					"	 		ISNULL(SP.MA,'') AS ITEM, MH.NGAYMUA   , MHSP.DONVI, MHSP.SOLUONG,    "+
					"	 		MHSP.DONGIA, (MHSP.DONGIA*MHSP.SOLUONG) AS THANHTIEN,TIENTE.TEN as TIENTE, ISNULL( MH.SOTHAMCHIEU,'')AS SPYC,  "+
					"	 		ISNULL(MHSP.NGAYNHAN,'') AS NGAYDENGHI,    SUBSTRING(CAST(MHSP.NGAYNHAN AS VARCHAR(10)),6,2)  AS THANGDAT , "+
					"	 		DVTH.MA, SP.TEN, MHSP.DIENGIAI, MH.NGUOITAO, SP.LOAISANPHAM_FK , MH.DONVITHUCHIEN_FK, MH.NHACUNGCAP_FK,MH.GHICHU "+
					"	 		FROM ERP_MUAHANG MH     "+
					"	 		INNER JOIN ERP_MUAHANG_SP MHSP ON MH.PK_SEQ=MHSP.MUAHANG_FK      "+
					"	 		INNER JOIN ERP_NHACUNGCAP NCC ON MH.NHACUNGCAP_FK=NCC.PK_SEQ "+
					"			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ "+
					"	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=MHSP.SANPHAM_FK "+
					"	 		INNER JOIN ERP_DONVITHUCHIEN DVTH ON MH.DONVITHUCHIEN_FK= DVTH.PK_SEQ    "+
					"	 		LEFT JOIN(  SELECT 	MUAHANG_FK AS DMHID,  "+
					"	 		CASE WHEN SUM(QUYETDINH) > 0 THEN  "+
					"	 		(CASE WHEN  ( SELECT SUM(TRANGTHAI)    FROM ERP_DUYETMUAHANG  	 "+
					"	 		WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1) > 0 THEN 0  ELSE 1  END)	  "+
					"	 		ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	  END AS DUYET    "+
					"	 		FROM ERP_DUYETMUAHANG DUYETMUAHANG  "+
					"	 		GROUP BY MUAHANG_FK  ) DUYET ON DUYET.DMHID = MH.PK_SEQ     "+
					"	 		WHERE MHSP.SANPHAM_FK is null "+// and MH.NGAYMUA >='"+tungay+"'   AND MH.NGAYMUA <='"+denngay+"' "+
					"	 ) DATHANG   "+
					"	 LEFT  JOIN       "+
					"	 ( "+
					"	 		SELECT isnull(NHSP.SANPHAM_FK, NHSP.CHIPHI_FK) AS SPID,NH.PK_SEQ AS NHID  ,ISNULL(NHSP.SOLUONGNHAN,0) AS SOLUONGNHAN , "+
					"	 		MH.PK_SEQ AS MHID, NH.NGAYNHAN, NHSP.DONGIA, TIENTE.TEN as TIENTE,MH.SOPO AS POID,  NH.SOHOADON, HD.NGAYHOADON, MH.PK_SEQ, NHSP.DIENGIAI   "+
					"	 		FROM ERP_NHANHANG NH     INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK     "+
					"	 		INNER JOIN ERP_MUAHANG MH ON NH.MUAHANG_FK=MH.PK_SEQ "+
					"			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ     "+
					"	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK   "+
					"	 		LEFT JOIN ERP_HOADONNCC_PHIEUNHAP HDPN ON  NH.PK_SEQ= HDPN.PHIEUNHAN_FK   "+
					"	 		LEFT JOIN ERP_HOADONNCC HD ON  HD.PK_SEQ= HDPN.HOADONNCC_FK   "+
					"	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is null "+//   AND MH.NGAYMUA >='"+tungay+"'    AND MH.NGAYMUA <='"+denngay+"' "+
					"	 		UNION ALL      "+
					"	 		SELECT  "+
					"	 		isnull(NHSP.SANPHAM_FK, NHSP.CHIPHI_FK) AS SPID,NH.PK_SEQ AS NHID, "+
					"	 		ISNULL(NHSP.SOLUONGNHAN,0) AS SOLUONGNHAN,  NHSP.MUAHANG_FK AS MHID, NH.NGAYNHAN, NHSP.DONGIA,TIENTE.TEN as TIENTE, MH.SOPO AS POID, "+
					"	 		NH.SOHOADON, HD.NGAYHOADON  , MH.PK_SEQ , NHSP.DIENGIAI       "+
					"	 		FROM ERP_NHANHANG NH    "+
					"	 		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK     "+
					"	 		INNER JOIN ERP_THUENHAPKHAU  TNK ON TNK.PK_SEQ=NH.SOTOKHAI_FK     "+
					"	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK     "+
					"	 		INNER JOIN ERP_MUAHANG MH ON NHSP.MUAHANG_FK=MH.PK_SEQ  "+
					"			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ   "+
					"	 		INNER JOIN ERP_THUENHAPKHAU_HOADONNCC TNK_HD ON TNK.PK_SEQ= TNK_HD.THUENHAPKHAU_FK   "+
					"	 		LEFT JOIN ERP_HOADONNCC HD ON HD.PK_SEQ= TNK_HD.HOADONNCC_FK   "+
					"	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is null  AND NH.MUAHANG_FK IS NULL  AND NHSP.MUAHANG_FK IS NOT NULL   "+
					//"	 		AND MH.NGAYMUA >='"+tungay+"'   AND MH.NGAYMUA <='"+denngay+"' "+
					"	 ) NHANHANG  "+
					"	 ON   DATHANG.MHID=NHANHANG.MHID AND DATHANG.SPID= NHANHANG.SPID   and DATHANG.DIENGIAI=NHANHANG.DIENGIAI) "+
					"	 )A WHERE 1=1  and A.NGAYYEUCAUNHAN < CONVERT(VARCHAR(10),DATEADD(DAY,10,GETDATE()),126) and (A.SOLUONG-A.SOLUONGDANHAN) > 0 ";
						 			
		
		if(obj.getNguoitaoIds().trim().length() > 0){
			query += " and A.NGUOITAO = '" + obj.getNguoitaoIds() + "' ";
		}
		
		if (obj.getDvthId().length() > 0){
			query += " and A.donvithuchien_fk = '" + obj.getDvthId() + "'";
		}
		
		if (obj.getNspIds().length() > 0){
			query += " and A.LOAISANPHAM_FK in (" + obj.getNspIds() + ")";
		}
		
		if (obj.getNccIds().length() > 0){
			query += " and A.NHACUNGCAP_FK in (" + obj.getNccIds() + ")";
		}
		
		query +=" ORDER BY A.MUAHANG_FK, A.SOHOADON " ;
		
		System.out.println("Theo dõi nhắc nhở nhận hàng 2: " + query);
		ResultSet rs = db.get(query);
		
		
		
		// --- đổ dữ liệu ---//
		
		int index =6;
		try{
			//CHÈN VÀO EXCEL
			int indexHeader = index-1;
			Cells cells = worksheet.getCells();	
			
			//LẤY NGÀY HIỆN TẠI & 10 NGÀY TIẾP THEO
			
			DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
			Calendar c = Calendar.getInstance();    
			c.setTime(new Date());
			c.add(Calendar.DATE, 10);
			
			Cell cell = cells.getCell("A2");
		    cell.setValue(" Từ ngày :  " + dateFormat.format(Calendar.getInstance().getTime()) + " 			Đến ngày : " + dateFormat.format(c.getTime()));
		    
		    /*  cell = cells.getCell("A3");
		    cell.setValue("Người tạo: "+util.getTenNhaPP());	
		    cell = cells.getCell("A4");
		    cell.setValue("Đơn vị thực hiện: " );
		    cell = cells.getCell("A5");
		    cell.setValue("Loại sản phẩm: ");
		    cell = cells.getCell("A6");
		    cell.setValue("Nhà cung cấp: ");*/
		    
			cell=null;
			Style style= null;
			
			if(obj.getPivot().equals("1")){
				cell = cells.getCell("BA" + String.valueOf(indexHeader));		
				cell.setValue("MUAHANG_FK");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("BB" + String.valueOf(indexHeader));		
				cell.setValue("NGAYMUA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BC" + String.valueOf(indexHeader));		
				cell.setValue("TENNCC");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BD" + String.valueOf(indexHeader));		
				cell.setValue("MASANPHAM");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BE" + String.valueOf(indexHeader));		
				cell.setValue("TENSP");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BF" + String.valueOf(indexHeader));		
				cell.setValue("DONVI");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BG" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONG");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BH" + String.valueOf(indexHeader));		
				cell.setValue("DONGIA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BI" + String.valueOf(indexHeader));		
				cell.setValue("THANHTIEN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BJ" + String.valueOf(indexHeader));		
				cell.setValue("TIENTE");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BK" + String.valueOf(indexHeader));		
				cell.setValue("NGAYYEUCAUNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BL" + String.valueOf(indexHeader));		
				cell.setValue("NGAYNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BM" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BN" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGCONLAI");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("BO" + String.valueOf(indexHeader));		
				cell.setValue("GHICHU");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
			}else{
				
				cell = cells.getCell("A" + String.valueOf(indexHeader));		
				cell.setValue("MUAHANG_FK");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("B" + String.valueOf(indexHeader));		
				cell.setValue("NGAYMUA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("C" + String.valueOf(indexHeader));		
				cell.setValue("TENNCC");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("D" + String.valueOf(indexHeader));		
				cell.setValue("MASANPHAM");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("E" + String.valueOf(indexHeader));		
				cell.setValue("TENSP");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("F" + String.valueOf(indexHeader));		
				cell.setValue("DONVI");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("G" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONG");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("H" + String.valueOf(indexHeader));		
				cell.setValue("DONGIA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("I" + String.valueOf(indexHeader));		
				cell.setValue("THANHTIEN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("J" + String.valueOf(indexHeader));		
				cell.setValue("TIENTE");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("K" + String.valueOf(indexHeader));		
				cell.setValue("NGAYYEUCAUNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("L" + String.valueOf(indexHeader));		
				cell.setValue("NGAYNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("M" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("N" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGCONLAI");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("O" + String.valueOf(indexHeader));		
				cell.setValue("GHICHU");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
			}
			
			
			while(rs.next()){
				cell=null;
				style= null;
				NumberFormat formatter = new DecimalFormat("#,###,###.###");

				String MUAHANG_FK = rs.getString("MUAHANG_FK");
				String NGAYMUA = rs.getString("NGAYMUA");
				String TENNCC = rs.getString("TENNCC");
				String MASANPHAM = rs.getString("MASANPHAM");
				String TENSP = rs.getString("TENSP");
				String DONVI = rs.getString("DONVI");
				String SOLUONG = rs.getString("SOLUONG");
				String DONGIA = rs.getString("DONGIA");
				String THANHTIEN = rs.getString("THANHTIEN");
				String TIENTE = rs.getString("TIENTE");
				String NGAYYEUCAUNHAN = rs.getString("NGAYYEUCAUNHAN");
				String NGAYNHAN = rs.getString("NGAYNHAN");
				String SOLUONGNHAN = rs.getString("SOLUONGDANHAN");
				String SOLUONGCONLAI = rs.getString("SOLUONGCONLAI");
				String GHICHU = rs.getString("GHICHU");
				
				//CHÈN VÀO EXCEL
				
				if(obj.getPivot().equals("1")){
					
			
					cell = cells.getCell("BA" + String.valueOf(index));		
					cell.setValue(MUAHANG_FK);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("BB" + String.valueOf(index));		
					cell.setValue(NGAYMUA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BC" + String.valueOf(index));		
					cell.setValue(TENNCC);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BD" + String.valueOf(index));		
					cell.setValue(MASANPHAM);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BE" + String.valueOf(index));		
					cell.setValue(TENSP);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BF" + String.valueOf(index));		
					cell.setValue(DONVI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BG" + String.valueOf(index));		
					cell.setValue(SOLUONG);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BH" + String.valueOf(index));		
					cell.setValue(DONGIA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BI" + String.valueOf(index));		
					cell.setValue(THANHTIEN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BJ" + String.valueOf(index));		
					cell.setValue(TIENTE);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BK" + String.valueOf(index));		
					cell.setValue(NGAYYEUCAUNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BL" + String.valueOf(index));		
					cell.setValue(NGAYNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BM" + String.valueOf(index));		
					cell.setValue(SOLUONGNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BN" + String.valueOf(index));		
					cell.setValue(SOLUONGCONLAI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("BO" + String.valueOf(index));		
					cell.setValue(GHICHU);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
				}else{
					cell = cells.getCell("A" + String.valueOf(index));		
					cell.setValue(MUAHANG_FK);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("B" + String.valueOf(index));		
					cell.setValue(NGAYMUA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(TENNCC);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("D" + String.valueOf(index));		
					cell.setValue(MASANPHAM);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("E" + String.valueOf(index));		
					cell.setValue(TENSP);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("F" + String.valueOf(index));		
					cell.setValue(DONVI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("G" + String.valueOf(index));		
					cell.setValue(SOLUONG);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("H" + String.valueOf(index));		
					cell.setValue(DONGIA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("I" + String.valueOf(index));		
					cell.setValue(THANHTIEN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("J" + String.valueOf(index));		
					cell.setValue(TIENTE);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("K" + String.valueOf(index));		
					cell.setValue(NGAYYEUCAUNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("L" + String.valueOf(index));		
					cell.setValue(NGAYNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("M" + String.valueOf(index));		
					cell.setValue(SOLUONGNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("N" + String.valueOf(index));		
					cell.setValue(SOLUONGCONLAI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("O" + String.valueOf(index));		
					cell.setValue(GHICHU);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
				}
				
				
				index++;
				
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally
		{
			db.shutDown();
		}
		
		
		
	}
	private String getdatetime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
