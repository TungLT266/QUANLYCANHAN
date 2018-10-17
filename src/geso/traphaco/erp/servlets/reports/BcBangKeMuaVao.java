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
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
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
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.aspose.cells.Font;

public class BcBangKeMuaVao extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public BcBangKeMuaVao() {
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
		//String ctyId = (String)session.getAttribute("congtyId");
		obj.setErpCongtyId(util.antiSQLInspection(""));
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		String view = util.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		obj.setView(view);		
		obj.InitErp();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBcBangKeMuaVao.jsp";
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
		

		String ctyId = (String)session.getAttribute("congtyId");
		if(ctyId == null) ctyId = "";
		obj.setCongTy(ctyId);
		//session.setAttribute("congtyId", obj.getErpCongtyId());
		obj.setErpCongtyId(util.antiSQLInspection(""));
		System.out.println("congty" + obj.getErpCongtyId());
		String ctyIds = util.antiSQLInspection(request.getParameter("ctyIds"));
		obj.setErpCongtyId(ctyIds);
		obj.setView(util.antiSQLInspection(request.getParameter("view")));
		
		/*obj.setYear(util.antiSQLInspection(request.getParameter("nam")));
		
		obj.setMonth(util.antiSQLInspection(request.getParameter("thang")));*/

	    String tuNgay = request.getParameter("tuNgay");
	    if (tuNgay == null)
	    	tuNgay = "";
	    obj.settungay(tuNgay);
	    
	    String denNgay = request.getParameter("denNgay");
	    if (denNgay == null)
	    	denNgay = "";
	    obj.setdenngay(denNgay);

	    String year = request.getParameter("year");
	    if (year == null)
	    	year = "";
	    obj.setYear(year);
	    
	    String thoiDiem = request.getParameter("thoiDiem");
	    if (thoiDiem == null)
	    	thoiDiem = "";
	    obj.setMonth(thoiDiem);
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.InitErp();
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBcBangKeMuaVao.jsp";
  
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xls");
			response.setHeader("Content-Disposition", "attachment; filename=BcBangKeMuaVao.xls");
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
	        	e.printStackTrace();
	    		session.setAttribute("obj", obj);
	    	 
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				response.sendRedirect(nextJSP);
			}
		}else{
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
			return;
		}
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BangKeMuaVao.xls");
		
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL97TO2003);

		
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

	private void setStyleColorRight(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("U1");
	
		Style style;	
		 style = cell1.getStyle();
		 					
		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        cell.setStyle(style);
	}
	
	private void setStyleColorCenter(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("T1");
	
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
		try{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			
			Cells cells = worksheet.getCells();	
			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			
			/*for(int i = 0;i < 7; ++i)
			{
		    	cells.setColumnWidth(i, 15.0f);
		    	if(i == 4)
		    		cells.setColumnWidth(i, 30.0f);
		    }	*/
			Cell cell = null;
			
			 int quy = 1;
			 if(Integer.parseInt(obj.getMonth()) >= 4 && Integer.parseInt(obj.getMonth()) <= 6) quy = 2;
			 else if(Integer.parseInt(obj.getMonth()) >= 7 && Integer.parseInt(obj.getMonth()) <= 9) quy = 3;
			 else if (Integer.parseInt(obj.getMonth()) >= 10 && Integer.parseInt(obj.getMonth()) <= 12) quy = 4;
			 
			cell = cells.getCell("F7");		cell.setValue("Kỳ Tính Thuế : Tháng "+obj.getMonth()+" năm "+obj.getYear() +" /Quý " + quy + " Năm " +obj.getYear() );	
			
			String sql="select ten,masothue  from erp_congty where pk_seq IN ("+obj.getCongTy()+")";
			 ResultSet rs=db.get(sql);
			 
			if(rs!=null){
				if(rs.next()){
					cell = cells.getCell("B9");		cell.setValue("Người nộp thuế: "+rs.getString("ten"));
					cell = cells.getCell("B10");		cell.setValue("Mã số thuế: "+rs.getString("masothue"));
				}
				rs.close();
			}
			
	 
			 
			db.getConnection().setAutoCommit(false);
			 
			sql = 
					
					//"-- 13/06/2017 CAC PHIEU CO THUE BANG 0 THI VAN LEN BAO CAO \r\n" + 
					" IF OBJECT_ID('tempdb.dbo.#Tb_ufn_bangkedauvaoERP') IS NOT NULL DROP TABLE #Tb_ufn_bangkedauvaoERP \n" +
					"SELECT A.LOAIHOADON, PK_SEQ, UPPER(A.MAUSOHOADON) MAUSOHOADON, UPPER(A.KYHIEU) KYHIEU, A.SOHOADON,   \r\n" + 
					" CASE WHEN LEN(NGAYXUATHD) > 0 THEN SUBSTRING(NGAYXUATHD, 9, 2) +'-'+ SUBSTRING(NGAYXUATHD, 6, 2) +'-'+ SUBSTRING(NGAYXUATHD, 1, 4) ELSE '' END AS NGAY,   \r\n" + 
					" A.NCC_FK, A.TENNGUOIMUA,    \r\n" + 
					"       LTRIM(A.MASOTHUE) AS MASOTHUE, TONGTIENBVAT , A.THUESUAT, VAT, TONGTIENAVAT, '' AS GHICHU, A.MATHANG  \n"
					+ "INTO #Tb_ufn_bangkedauvaoERP \r\n" + 
					" FROM (  			  \r\n" + 
					"		--BEGIN HÓA ĐƠN NCC   \r\n" + 
					" 		 SELECT  N'DUYỆT HÓA ĐƠN NCC ERP' AS LOAIHOADON, CONVERT(VARCHAR, P.PK_SEQ) AS PK_SEQ, \r\n" + 
					"				HD.MAUSOHOADON, HD.KYHIEU, HD.SOHOADON, P.NGAYGHINHAN AS NGAYXUATHD ,  \r\n" + 
					" 		 		CASE WHEN P.NCCTHAYTHE_FK IS NOT NULL AND NCCTT.TEN != ''  THEN P.NCCTHAYTHE_FK ELSE P.NCC_FK END NCC_FK,    \r\n" + 
					" 		 		CASE WHEN P.NCCTHAYTHE_FK IS NOT NULL AND NCCTT.TEN != ''  THEN NCCTT.TEN ELSE NCC.TEN END AS TENNGUOIMUA,    \r\n" + 
					" 		 		CASE WHEN P.NCCTHAYTHE_FK IS NOT NULL AND NCCTT.TEN != ''  THEN  LTRIM(NCCTT.MASOTHUE) ELSE LTRIM(NCC.MASOTHUE) END MASOTHUE,   		 		  \r\n" + 
					"			SUM(HDMH.THANHTIENVIET) AS TONGTIENBVAT, \r\n" + 
					"			ISNULL(HDMH.VAT, 0) THUESUAT, \r\n" + 
					"			SUM(ISNULL(HDMH.TIENVAT,0)) VAT,   \r\n" + 
					"			SUM(HDMH.THANHTIENVIET + ISNULL(HDMH.TIENVAT,0)) AS TONGTIENAVAT, '' GHICHU,   \r\n" + 
					"			(SELECT TOP 1 SP.TEN FROM ERP_HOADONNCC_DONMUAHANG HDMH2 \r\n" + 
					" 				LEFT JOIN ERP_SANPHAM SP ON HDMH2.SANPHAM_FK = SP.PK_SEQ    \r\n" + 
					"				WHERE HOADONNCC_FK = HD.PK_SEQ AND ISNULL(HDMH2.VAT, 0) = ISNULL(HDMH.VAT, 0) AND HDMH2.THANHTIENVIET =   \r\n" + 
					"				(SELECT MAX(THANHTIENVIET) FROM ERP_HOADONNCC_DONMUAHANG HDMH3\r\n" + 
					"				WHERE HOADONNCC_FK = HD.PK_SEQ AND ISNULL(HDMH3.VAT, 0) = ISNULL(HDMH.VAT, 0))  \r\n" + 
					"			) MATHANG    \r\n" + 
					" 		 FROM ERP_HOADONNCC HD INNER JOIN ERP_HOADONNCC_DONMUAHANG HDMH ON HD.PK_SEQ = HDMH.HOADONNCC_FK    \r\n" + 
					" 		 INNER JOIN ERP_PARK P  ON HD.PARK_FK = P.PK_SEQ    \r\n" + 
					" 		 LEFT JOIN ERP_NHACUNGCAPTHAYTHE NCCTT ON P.NCCTHAYTHE_FK = NCCTT.PK_SEQ    \r\n" + 
					" 		 INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = P.NCC_FK     \r\n" + 
					" 		 WHERE PARK_FK IN   \r\n" + 
					"				(SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE NGAYGHINHAN >= '"+obj.gettungay()+"' AND NGAYGHINHAN <= '"+obj.getdenngay()+"' AND \r\n" + 
					"				LOAICHUNGTU = N'DUYỆT HÓA ĐƠN NCC'   \r\n" + 
					"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+") AND SOHIEUTAIKHOAN LIKE '133%'))  \r\n" + 
					"		GROUP BY HD.MAUSOHOADON, HD.KYHIEU, HD.SOHOADON, P.NGAYGHINHAN, P.NCCTHAYTHE_FK, P.NCC_FK, HDMH.VAT, NCC.TEN,    \r\n" + 
					"		LTRIM(NCC.MASOTHUE),NCCTT.TEN, NCCTT.MASOTHUE,HDMH.VAT, P.PK_SEQ, HD.PK_SEQ  \r\n" + 
					"			--END HÓA ĐƠN NCC   \r\n" + 
					/*"	UNION ALL \n"+
					"		--BEGIN HÓA ĐƠN NỘI BỘ CHO CHI NHÁNH \n"+
					"		SELECT N'HÓA ĐƠN NỘI BỘ' LOAIHOADON, HD.NGAYXUATHD + ' -- ' +  CONVERT(VARCHAR, HD.PK_SEQ) PK_SEQ, '' MAUSOHOADON, ISNULL(HD.KYHIEU, '') KYHIEU, ISNULL(HD.SOHOADON, '') AS SOHOADON, HD.NGAYXUATHD AS NGAYHOADON,  \r\n" + 
					"		ISNULL(HD.KHACHHANG_FK, HD.NPP_FK) AS KH_FK, NPP.TEN AS TENNGUOIMUA, NPP.MASOTHUE AS MASOTHUE, \r\n" + 
					"		SUM(ROUND(HD_SP.THANHTIEN, 0)) AS TONGTIENBVAT,  \r\n" + 
					"		HD_SP.VAT AS THUESUAT,\r\n" + 
					"		SUM( TIENVAT ) AS VAT,  \r\n" + 
					"		SUM(ROUND(HD_SP.THANHTIEN, 0) + TIENVAT) AS TONGTIENAVAT,  \r\n" + 
					"		ISNULL(HD.GHICHU, '') AS GHICHU,  \r\n" + 
					"		(SELECT TOP 1 D.TEN FROM "+ geso.traphaco.center.util.Utility.prefixDMS +"ERP_HOADON_SP HDMH \r\n" + 
					"			INNER JOIN "+ geso.traphaco.center.util.Utility.prefixDMS +"SANPHAM D ON HDMH.SANPHAM_FK = D.PK_SEQ  \r\n" + 
					"			WHERE HOADON_FK = HD.PK_SEQ AND HDMH.VAT = HD_SP.VAT AND THANHTIEN =  \r\n" + 
					"			(SELECT MAX( THANHTIEN) FROM "+ geso.traphaco.center.util.Utility.prefixDMS +"ERP_HOADON_SP HDMH WHERE HOADON_FK = HD.PK_SEQ AND HDMH.VAT = HD_SP.VAT) \r\n" + 
					"		) MATHANG   \r\n" + 
					"		FROM  "+ geso.traphaco.center.util.Utility.prefixDMS +"ERP_HOADON HD \r\n" + 
					"		INNER JOIN  "+ geso.traphaco.center.util.Utility.prefixDMS +"ERP_HOADON_SP HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK\r\n" + 
					"		INNER JOIN  "+ geso.traphaco.center.util.Utility.prefixDMS +"NHAPHANPHOI NPP ON HD.NPP_FK = NPP.PK_SEQ  \r\n" + 
					"		WHERE HD.PK_SEQ IN (SELECT DISTINCT SOCHUNGTU FROM "+ geso.traphaco.center.util.Utility.prefixDMS +"ERP_PHATSINHKETOAN WHERE MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND  \r\n" + 
					"		LOAICHUNGTU = N'Hóa đơn nội bộ' AND LOAIHD = N'HDHO_NB' \r\n" + 
					"		AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM "+ geso.traphaco.center.util.Utility.prefixDMS +"ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+"))) AND HD.LOAIHOADON = 0 \r\n" + 
					"		GROUP BY HD_SP.VAT, HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD, HD.KHACHHANG_FK, HD.NPP_FK, NPP.TEN, NPP.MASOTHUE, HD.GHICHU \r\n" + 
					"--END HÓA ĐƠN NỘI BỘ CHO CHI NHÁNH \n"+*/
					"	UNION ALL   \r\n" + 
					"		--BEGIN CHI PHI NHAP KHAU   \r\n" + 
					"		SELECT N'CHI PHÍ NHẬP KHẨU ERP' AS LOAIHOADON, CONVERT(VARCHAR, NK.PK_SEQ) AS PK_SEQ, \r\n" + 
					"		CT.MAUSOHOADON, CT.KYHIEUCHUNGTU AS KYHIEU, CT.SOCHUNGTU AS SOHOADON, CT.NGAYCHUNGTU AS NGAYXUATHD, NULL AS NCC_FK,    \r\n" + 
					"       CT.NHACUNGCAP AS TENNGUOIMUA, LTRIM(CT.MASOTHUE) AS MASOTHUE,   \r\n" + 
					"	   SUM(CT.TIENHANG) AS TONGTIENBVAT,   \r\n" + 
					"	   CT.THUESUAT AS THUESUAT, \r\n" + 
					"	   SUM(TIENTHUE) AS VAT,   \r\n" + 
					"	   SUM((CT.TIENHANG + CT.TIENHANG*CT.THUESUAT/100)) AS TONGTIENAVAT,   \r\n" + 
					"	   CT.DIENGIAI AS GHICHU, '' AS MATHANG    \r\n" + 
					"		FROM ERP_CHIPHINHAPKHAU_CHITIET CT    \r\n" + 
					"		INNER JOIN ERP_CHIPHINHAPKHAU NK ON NK.PK_SEQ = CT.CHIPHINHAPKHAU_FK    \r\n" + 
					"		WHERE NK.PK_SEQ IN   \r\n" + 
					"				(SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE NGAYGHINHAN >= '"+obj.gettungay()+"' AND NGAYGHINHAN <= '"+obj.getdenngay()+"'  AND  \r\n" + 
					"				LOAICHUNGTU = N'CHI PHÍ NHẬP KHẨU'  \r\n" + 
					"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+") AND SOHIEUTAIKHOAN LIKE '133%'))  \r\n" + 
					"		GROUP BY NK.PK_SEQ, CT.MAUSOHOADON, CT.KYHIEUCHUNGTU, CT.SOCHUNGTU, CT.NGAYCHUNGTU, CT.NHACUNGCAP,  \r\n" + 
					"		CT.MASOTHUE, CT.THUESUAT, CT.DIENGIAI, NK.PK_SEQ   \r\n" + 
					"		--END CHI PHI NHAP KHAU   \r\n" + 
					"		UNION ALL   \r\n" + 
					"		--BEGIN THUE NHAP KHAU   \r\n" + 
					"		SELECT DISTINCT N'THUẾ NHẬP KHẨU' AS LOAIHOADON, CONVERT(VARCHAR, TNK.PK_SEQ) AS PK_SEQ, \r\n" + 
					"		'' AS MAUSOHOADON, '' AS KYHIEU, TNK.SOCHUNGTU AS SOHOADON, TNK.NGAYCHUNGTU AS NGAYXUATHD, NCC_FK, NCC.TEN AS TENNGUOIMUA, LTRIM(NCC.MASOTHUE) AS MASOTHUE,   \r\n" + 
					"        SUM(TNK_CT.TIENTINHTHUENK) + SUM(TNK_CT.THUENHAPKHAU) AS TONGTIENBVAT,    \r\n" + 
					"		TNK_CT.PHANTRAMVATNK AS THUESUAT,   \r\n" + 
					"		SUM(TNK_CT.VATNK) AS VAT,   \r\n" + 
					"		SUM(TNK_CT.TIENTINHTHUENK + (TNK_CT.THUENHAPKHAU) + (TNK_CT.VATNK)) AS TONGTIENAVAT,   \r\n" + 
					"		ISNULL(TNK.DIENGIAI,'') AS GHICHU, \r\n" + 
					"		(SELECT TOP 1 SP.TEN FROM ERP_THUENHAPKHAU_CHITIET TNK_CT2 \r\n" + 
					" 				LEFT JOIN ERP_SANPHAM SP ON TNK_CT2.SANPHAM_FK = SP.PK_SEQ    \r\n" + 
					"				WHERE TNK_CT2.THUENHAPKHAU_FK = TNK.PK_SEQ AND ISNULL(TNK_CT2.PHANTRAMVATNK, 0) = TNK_CT.PHANTRAMVATNK AND \r\n" + 
					"				(TNK_CT2.TIENTINHTHUENK) + (TNK_CT2.THUENHAPKHAU) + (TNK_CT2.VATNK) =   \r\n" + 
					"				(SELECT MAX((TNK_CT3.TIENTINHTHUENK) + (TNK_CT3.THUENHAPKHAU) + (TNK_CT3.VATNK)) FROM  ERP_THUENHAPKHAU_CHITIET TNK_CT3\r\n" + 
					"				WHERE TNK_CT3.THUENHAPKHAU_FK = TNK.PK_SEQ AND ISNULL(TNK_CT3.PHANTRAMVATNK, 0) = TNK_CT.PHANTRAMVATNK)\r\n" + 
					"		) AS MATHANG    \r\n" + 
					"		FROM ERP_THUENHAPKHAU TNK    \r\n" + 
					"		INNER JOIN ERP_THUENHAPKHAU_CHITIET TNK_CT ON TNK.PK_SEQ = TNK_CT.THUENHAPKHAU_FK\r\n" + 
					"		INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TNK.COQUANTHUE_FK     \r\n" + 
					"		WHERE TNK.PK_SEQ IN   \r\n" + 
					"				(SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE NGAYGHINHAN >= '"+obj.gettungay()+"' AND NGAYGHINHAN <= '"+obj.getdenngay()+"'  AND  \r\n" + 
					"				(LOAICHUNGTU = N'THUẾ NHẬP KHẨU' OR LOAICHUNGTU = N'THUẾ VAT NHẬP KHẨU'  ) AND NO > 0  \r\n" + 
					"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN ("+obj.getErpCongtyId()+") AND SOHIEUTAIKHOAN LIKE '133%'))  \r\n" + 
					"				--AND TNK.PK_SEQ = 100018  \r\n" + 
					"		GROUP BY TNK.SOCHUNGTU, TNK.NGAYCHUNGTU, NCC_FK, NCC.TEN, NCC.MASOTHUE, TNK.PTVAT, TNK.DIENGIAI, TNK.TIGIA, TNK.PK_SEQ, TNK_CT.PHANTRAMVATNK   \r\n" + 
					"		--END THUE NHAP KHAU  \r\n" + 
					" 	UNION ALL    \r\n" + 
					"		--BEGIN DE NGHI THANH TOAN    \r\n" + 
					"		SELECT DISTINCT N'ĐỀ NGHỊ THANH TOÁN ERP' AS LOAIHOADON"+ 
					"       , STUFF((  SELECT DISTINCT ',' +DANHSACH.NGAYCHUNGTU + ' -- '+ CONVERT(VARCHAR, DANHSACH.SOCHUNGTU)  AS [text()]\r\n" + 
					"		FROM (\r\n" + 
					"				SELECT DISTINCT MH.PK_SEQ AS SOCHUNGTU, MH.NGAYMUA AS NGAYCHUNGTU, \r\n" + 
					"				HD.MAUSOHOADON, HD.KYHIEU, HD.SOHOADON, HD.NGAYHOADON AS NGAYXUATHD,     \r\n" + 
					"				NULL AS NCC_FK,  HD.TENNHACUNGCAP   AS TENNGUOIMUA,  \r\n" + 
					"				HD.MASOTHUE  AS MASOTHUE  \r\n" + 
					"				FROM ERP_MUAHANG MH       \r\n" + 
					"				INNER JOIN ERP_MUAHANG_SP_HOADON HD ON MH.PK_SEQ = HD.MUAHANG_FK  \r\n" + 
					"		WHERE (MH.HTTT_FK = 100004 AND MH.PK_SEQ IN   \r\n" + 
					"			 (SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE NGAYGHINHAN >= '"+obj.gettungay()+"' AND NGAYGHINHAN <= '"+obj.getdenngay()+"'  AND   \r\n" + 
					"				LOAICHUNGTU = N'DUYỆT ĐỀ NGHỊ THANH TOÁN'    \r\n" + 
					"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+"))))   \r\n" + 
					"		OR (MH.HTTT_FK IN (100000, 100001) AND MH.PK_SEQ IN   \r\n" + 
					"			(SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE LOAIHD = 6 AND THANHTOANHD_FK IN (  \r\n" + 
					"			SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE NGAYGHINHAN >= '"+obj.gettungay()+"' AND NGAYGHINHAN <= '"+obj.getdenngay()+"'  AND   \r\n" + 
					"				(LOAICHUNGTU = N'CHI PHÍ KHÁC' OR LOAICHUNGTU = N'THANH TOÁN TẠM ỨNG' OR LOAICHUNGTU = N'PHIẾU CHI' OR LOAICHUNGTU = N'ỦY NHIỆM CHI') \r\n" + 
					"					AND TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+") )))) \n"+
					"			) DANHSACH\r\n" + 
					"			WHERE DANHSACH.MAUSOHOADON = HD.MAUSOHOADON AND DANHSACH.SOHOADON = HD.SOHOADON AND DANHSACH.KYHIEU = HD.KYHIEU AND DANHSACH.NGAYXUATHD = HD.NGAYHOADON AND DANHSACH.MASOTHUE = HD.MASOTHUE\r\n" + 
					"			FOR XML PATH('') \r\n" + 
					"			), 1, 1, '' ) AS PK_SEQ, \r\n" + 
					"		HD.MAUSOHOADON, HD.KYHIEU, HD.SOHOADON, MH.NGAYMUA AS NGAYXUATHD,     \r\n" + 
					"      NULL AS NCC_FK, CASE WHEN HD.TENNHACUNGCAP = '' THEN ISNULL(NCC.TEN, ISNULL(NV.TEN, '')) ELSE HD.TENNHACUNGCAP  END AS TENNGUOIMUA,  \r\n" + 
					"	   CASE WHEN HD.MASOTHUE = '' THEN NCC.MASOTHUE ELSE HD.MASOTHUE END AS MASOTHUE,  \r\n" + 
					"		SUM(HD.TIENHANG) AS TONGTIENBVAT, CAST(HD.THUESUAT AS NUMERIC(18,0)) AS THUESUAT, SUM(HD.TIENTHUE) AS VAT, SUM(HD.TONGCONG) AS TONGTIENAVAT,    \r\n" + 
					"		ISNULL(HD.GHICHU, '') GHICHU, DSSP.DIENGIAI MATHANG     \r\n" + 
					" 		FROM ERP_MUAHANG MH       \r\n" + 
					"		INNER JOIN ERP_MUAHANG_SP_HOADON HD ON MH.PK_SEQ = HD.MUAHANG_FK    \r\n" + 
					"		LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MH.NHACUNGCAP_FK AND MH.NHACUNGCAP_FK IS NOT NULL  \r\n" + 
					"		LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = MH.NHANVIEN_FK AND MH.NHANVIEN_FK IS NOT NULL   \r\n" + 
					"		INNER JOIN (\r\n" + 
					"			SELECT NGAYHOADON, MAUSOHOADON, KYHIEU, SOHOADON, THUESUAT , MAX(DIENGIAI) DIENGIAI\r\n" + 
					"			FROM ERP_MUAHANG_SP_HOADON HD1\r\n" + 
					"			INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.PK_SEQ =  HD1.MUAHANG_SP_FK  \r\n" + 
					"			 GROUP BY NGAYHOADON, MAUSOHOADON, KYHIEU, SOHOADON, THUESUAT, TONGCONG \r\n" + 
					"			 HAVING (TONGCONG) = (SELECT MAX(TONGCONG) FROM ERP_MUAHANG_SP_HOADON HD2 WHERE HD1.MAUSOHOADON = HD2.MAUSOHOADON AND HD1.KYHIEU = HD2.KYHIEU \r\n" + 
					"							AND HD1.SOHOADON = HD2.SOHOADON AND HD1.THUESUAT = HD2.THUESUAT  AND HD1.NGAYHOADON = HD2.NGAYHOADON)\r\n" + 
					"		) DSSP ON HD.MAUSOHOADON = DSSP.MAUSOHOADON AND HD.KYHIEU = DSSP.KYHIEU AND HD.SOHOADON = DSSP.SOHOADON AND HD.THUESUAT = DSSP.THUESUAT AND HD.NGAYHOADON = DSSP.NGAYHOADON \n"+
					"		WHERE (MH.HTTT_FK = 100004 AND MH.PK_SEQ IN   \r\n" + 
					"			 (SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE NGAYGHINHAN >= '"+obj.gettungay()+"' AND NGAYGHINHAN <= '"+obj.getdenngay()+"'  AND   \r\n" + 
					"				LOAICHUNGTU = N'DUYỆT ĐỀ NGHỊ THANH TOÁN'    \r\n" + 
					"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+"))))   \r\n" + 
					"		OR (MH.HTTT_FK IN (100000, 100001) AND MH.PK_SEQ IN   \r\n" + 
					"			(SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE LOAIHD = 6 AND THANHTOANHD_FK IN (  \r\n" + 
					"			SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE NGAYGHINHAN >= '"+obj.gettungay()+"' AND NGAYGHINHAN <= '"+obj.getdenngay()+"'  AND   \r\n" + 
					"				(LOAICHUNGTU = N'CHI PHÍ KHÁC' OR LOAICHUNGTU = N'THANH TOÁN TẠM ỨNG' OR LOAICHUNGTU = N'PHIẾU CHI' OR LOAICHUNGTU = N'ỦY NHIỆM CHI') \r\n" + 
					"					AND TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+") )))) \n"+
					"		GROUP BY HD.MAUSOHOADON, HD.KYHIEU, HD.SOHOADON, HD.NGAYHOADON,MH.NGAYMUA, HD.MASOTHUE, NCC.TEN, NCC.MASOTHUE, NV.TEN, HD.TENNHACUNGCAP, HD.THUESUAT, HD.GHICHU, DSSP.DIENGIAI  \n"+
					"		--END DE NGHI THANH TOAN   \r\n" + 
				/*	"	UNION ALL    \r\n" + 
					"		--BEGIN DE NGHI THANH TOAN CHI NHANH (DMS)   \r\n" + 
					"SELECT * FROM OPENQUERY ("+geso.traphaco.center.util.Utility.prefixLinkDMS+""+
					", 'SELECT DISTINCT N''ĐỀ NGHỊ THANH TOÁN DMS'' AS LOAIHOADON"+
									"       , STUFF((  SELECT DISTINCT '','' +DANHSACH.NGAYCHUNGTU + '' -- ''+ CONVERT(VARCHAR, DANHSACH.SOCHUNGTU)  AS [text()]\r\n" + 
									"		FROM (\r\n" + 
									"				SELECT DISTINCT MH.PK_SEQ AS SOCHUNGTU, MH.NGAYMUA AS NGAYCHUNGTU, \r\n" + 
									"				HD.MAUSOHOADON, HD.KYHIEU, HD.SOHOADON, HD.NGAYHOADON AS NGAYXUATHD,     \r\n" + 
									"				NULL AS NCC_FK,  HD.TENNHACUNGCAP   AS TENNGUOIMUA,  \r\n" + 
									"				HD.MASOTHUE  AS MASOTHUE  \r\n" + 
									"				FROM ERP_MUAHANG MH       \r\n" + 
									"				INNER JOIN ERP_MUAHANG_SP_HOADON HD ON MH.PK_SEQ = HD.MUAHANG_FK  \r\n" + 
									"		WHERE (MH.HTTT_FK = 100004 AND MH.PK_SEQ IN   \r\n" + 
									"			 (SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND   \r\n" + 
									"				LOAICHUNGTU = N''DUYỆT ĐỀ NGHỊ THANH TOÁN''    \r\n" + 
									"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+")))   \r\n" + 
									"		OR (MH.HTTT_FK IN (100000, 100001) AND MH.PK_SEQ IN  \r\n" + 
									"			(SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE LOAIHD = 6 AND THANHTOANHD_FK IN (  \r\n" + 
									"			SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND   \r\n" + 
									"				(LOAICHUNGTU = N''CHI PHÍ KHÁC'' OR LOAICHUNGTU = N''THANH TOÁN TẠM ỨNG'' OR LOAICHUNGTU = N''PHIẾU CHI'' OR LOAICHUNGTU = N''ỦY NHIỆM CHI'')  AND NO > 0  \r\n" + 
									"					AND TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+")))))) \n"+
									"		) DANHSACH\r\n" + 
									"			WHERE DANHSACH.MAUSOHOADON = HD.MAUSOHOADON AND DANHSACH.SOHOADON = HD.SOHOADON AND DANHSACH.KYHIEU = HD.KYHIEU AND DANHSACH.NGAYXUATHD = HD.NGAYHOADON AND DANHSACH.MASOTHUE = HD.MASOTHUE\r\n" + 
									"			FOR XML PATH('''') \r\n" + 
									"			), 1, 1, '''' ) AS PK_SEQ, \r\n" + 
									"		HD.MAUSOHOADON, HD.KYHIEU, HD.SOHOADON, HD.NGAYHOADON AS NGAYXUATHD,     \r\n" + 
									"      NULL AS NCC_FK, CASE WHEN HD.TENNHACUNGCAP = '''' THEN ISNULL(NCC.TEN, ISNULL(NV.TEN, '''')) ELSE HD.TENNHACUNGCAP  END AS TENNGUOIMUA,  \r\n" + 
									"	   CASE WHEN HD.MASOTHUE = '''' THEN NCC.MASOTHUE ELSE HD.MASOTHUE END AS MASOTHUE,  \r\n" + 
									"		SUM(HD.TIENHANG) AS TONGTIENBVAT, CAST(HD.THUESUAT AS NUMERIC(18,0)) AS THUESUAT, SUM(HD.TIENTHUE) AS VAT, SUM(HD.TONGCONG) AS TONGTIENAVAT,    \r\n" + 
									"		ISNULL(HD.GHICHU, '''') GHICHU, DSSP.DIENGIAI MATHANG     \r\n" + 
									" 		FROM  ERP_MUAHANG MH      \r\n" + 
									"		INNER JOIN  ERP_MUAHANG_SP_HOADON HD ON MH.PK_SEQ = HD.MUAHANG_FK   \r\n" + 
									"		LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MH.NHACUNGCAP_FK AND MH.NHACUNGCAP_FK IS NOT NULL  \r\n" + 
									"		LEFT JOIN  ERP_NHANVIEN NV ON NV.PK_SEQ = MH.NHANVIEN_FK AND MH.NHANVIEN_FK IS NOT NULL  \r\n" + 
									"		INNER JOIN (\r\n" + 
									"			SELECT NGAYHOADON, MAUSOHOADON, KYHIEU, SOHOADON, THUESUAT , MAX(DIENGIAI) DIENGIAI\r\n" + 
									"			FROM ERP_MUAHANG_SP_HOADON HD1\r\n" + 
									"			INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.PK_SEQ =  HD1.MUAHANG_SP_FK  \r\n" + 
									"			 GROUP BY NGAYHOADON, MAUSOHOADON, KYHIEU, SOHOADON, THUESUAT, TONGCONG \r\n" + 
									"			 HAVING (TONGCONG) = (SELECT MAX(TONGCONG) FROM ERP_MUAHANG_SP_HOADON HD2 WHERE HD1.MAUSOHOADON = HD2.MAUSOHOADON AND HD1.KYHIEU = HD2.KYHIEU \r\n" + 
									"							AND HD1.SOHOADON = HD2.SOHOADON AND HD1.THUESUAT = HD2.THUESUAT AND HD1.NGAYHOADON = HD2.NGAYHOADON)\r\n" + 
									"		) DSSP ON HD.MAUSOHOADON = DSSP.MAUSOHOADON AND HD.KYHIEU = DSSP.KYHIEU AND HD.SOHOADON = DSSP.SOHOADON AND HD.THUESUAT = DSSP.THUESUAT AND HD.NGAYHOADON = DSSP.NGAYHOADON \n"+
									"		WHERE (MH.HTTT_FK = 100004 AND MH.PK_SEQ IN   \r\n" + 
									"			 (SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND   \r\n" + 
									"				LOAICHUNGTU = N''DUYỆT ĐỀ NGHỊ THANH TOÁN''    \r\n" + 
									"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+")))   \r\n" + 
									"		OR (MH.HTTT_FK IN (100000, 100001) AND MH.PK_SEQ IN  \r\n" + 
									"			(SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE LOAIHD = 6 AND THANHTOANHD_FK IN (  \r\n" + 
									"			SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND   \r\n" + 
									"				(LOAICHUNGTU = N''CHI PHÍ KHÁC'' OR LOAICHUNGTU = N''THANH TOÁN TẠM ỨNG'' OR LOAICHUNGTU = N''PHIẾU CHI'' OR LOAICHUNGTU = N''ỦY NHIỆM CHI'')  AND NO > 0  \r\n" + 
									"					AND TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+")))))) \n"+
									"		GROUP BY HD.MAUSOHOADON, HD.KYHIEU, HD.SOHOADON, HD.NGAYHOADON, HD.MASOTHUE, NCC.TEN, NCC.MASOTHUE, NV.TEN, HD.TENNHACUNGCAP, HD.THUESUAT, HD.GHICHU, DSSP.DIENGIAI  \n'"+
				")"+
					"		--END ?E NGHI THANH TOAN CHI NHANH (DMS)   \r\n" + */
					"		UNION ALL    \r\n" + 
					"		--BEGIN HOA DON TRA LAI NHA CUNG CAP   \r\n" + 
					"		SELECT N'HÓA ĐƠN TRẢ LẠI NHÀ CUNG CẤP ERP' AS LOAIHOADON, CONVERT(VARCHAR, HD.PK_SEQ) AS PK_SEQ, \r\n" + 
					"		'' AS MAUSOHOADON, ISNULL(HD.KYHIEU, '') AS KYHIEU, ISNULL(HD.SOHOADON, '') AS SOHOADON, HD.NGAYXUATHD,    \r\n" + 
					"		NCC.PK_SEQ AS NCC_FK,   \r\n" + 
					"		NCC.TEN  TENNGUOIMUA,   \r\n" + 
					"		NCC.MASOTHUE AS MASOTHUE,   \r\n" + 
					"		-(SUM(HD_CT.TIENBVAT)) AS TONGTIENBVAT,   \r\n" + 
					"		DTH.VAT AS THUESUAT,   \r\n" + 
					"		-(SUM(HD_CT.VAT))AS VAT, \r\n" + 
					"		-(SUM(HD_CT.TIENAVAT)) TONGTIENAVAT,   \r\n" + 
					"		ISNULL(HD.GHICHU, '') AS GHICHU,   \r\n" + 
					"		(SELECT TOP 1 SP.TEN FROM ERP_HOADON_SP HDMH  \r\n" + 
					"		 INNER JOIN ERP_HOADON_DDH HD_DTH ON HDMH.HOADON_FK = HD_DTH.HOADON_FK  \r\n" + 
					"		 INNER JOIN ERP_MUAHANG DTH2 ON DTH2.PK_SEQ = HD_DTH.DDH_FK\r\n" + 
					" 		 LEFT JOIN ERP_SANPHAM SP ON HDMH.SANPHAM_FK = SP.PK_SEQ    \r\n" + 
					"		WHERE HDMH.HOADON_FK = HD.PK_SEQ AND DTH2.VAT = DTH.VAT AND TIENAVAT = \r\n" + 
					"		(SELECT MAX(TIENAVAT) FROM ERP_HOADON_SP WHERE HOADON_FK = HD.PK_SEQ AND DTH2.VAT = DTH.VAT)\r\n" + 
					"		) MATHANG    \r\n" + 
					"		FROM ERP_HOADON HD \r\n" + 
					"		INNER JOIN ERP_HOADON_SP HD_CT ON HD.PK_SEQ = HD_CT.HOADON_FK\r\n" + 
					"		INNER JOIN ERP_HOADON_DDH HD_DTH ON HD.PK_sEQ = HD_DTH.HOADON_FK  \r\n" + 
					"		INNER JOIN ERP_MUAHANG DTH ON DTH.PK_SEQ = HD_DTH.DDH_FK AND DTH.TYPE = 2 \r\n" + 
					"		LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ=HD.NCC_FK 	  \r\n" + 
					"		WHERE HD.PK_SEQ IN (SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE NGAYGHINHAN >= '"+obj.gettungay()+"' AND NGAYGHINHAN <= '"+obj.getdenngay()+"'  AND  \r\n" + 
					"				LOAICHUNGTU = N'HÓA ĐƠN TRẢ HÀNG NCC'  \r\n" + 
					"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+")))  \r\n" + 
					"		GROUP BY HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD, NCC.PK_SEQ, NCC.TEN, NCC.MASOTHUE, HD.THUESUAT, HD.GHICHU, HD.PK_SEQ, DTH.VAT     \r\n" + 
					"		--END HOA DON TRA LAI NHA CUNG CAP   \r\n" + 
					/*"	UNION ALL  \r\n" + 
					"	  --BEGIN HOA DON TRA LAI NHA CUNG CAP CHI NHANH (DMS)  \r\n" + 
					"		--KHONG TIM DUOC HACH TOAN  \r\n" + 
					"		SELECT N'HÓA ĐƠN TRẢ LẠI NHÀ CUNG CẤP DMS' AS LOAIHOADON, CONVERT(VARCHAR, A.PK_SEQ) AS PK_SEQ, '' AS MAUSOHOADON, ISNULL(KYHIEU, '') AS KYHIEU, ISNULL(SOHOADON, '') AS SOHOADON, A.NGAYTRA, B.PK_SEQ , \r\n" + 
					"		B.TEN AS TENNGNUOIMUA, B.MASOTHUE, \r\n" + 
					"		-SUM(ROUND(DONGIA*SOLUONG, 0)) AS TONGTIENBVAT,  \r\n" + 
					"		ISNULL(HD_SP.PTVAT, 0) AS THUESUAT, \r\n" + 
					"		-SUM(ROUND(DONGIA*SOLUONG*ISNULL(HD_SP.PTVAT, 0)/100, 0)) AS VAT, \r\n" + 
					"		-( SUM(ROUND(DONGIA*SOLUONG, 0)) + SUM(ROUND(DONGIA*SOLUONG*ISNULL(HD_SP.PTVAT, 0)/100, 0) )) AS TONGTIENAVAT, \r\n" + 
					"		ISNULL(A.GHICHU, '') GHICHU,  \r\n" + 
					"		(		SELECT TOP 1 SP.TEN FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "DONTRAHANG_SP HDMH \r\n" + 
					"					inner join  " + geso.traphaco.center.util.Utility.prefixDMS + "SANPHAM SP on SP.PK_SEQ= HDMH.Sanpham_fk   \r\n" + 
					"					WHERE DONTRAHANG_FK = A.PK_SEQ AND ISNULL(HDMH.PTVAT, 0) = ISNULL(HD_SP.PTVAT, 0) \r\n" + 
					"					AND ( DONGIA * SOLUONG )=  \r\n" + 
					"					(SELECT MAX( DONGIA*SOLUONG) FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "DONTRAHANG_SP HDMH WHERE DONTRAHANG_FK = A.PK_SEQ AND ISNULL(HDMH.PTVAT, 0) = ISNULL(HD_SP.PTVAT, 0)) \r\n" + 
					"				) MATHANG  \r\n" + 
					"		FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "DONTRAHANG A  \r\n" + 
					"		INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "DONTRAHANG_SP HD_SP ON HD_SP.DONTRAHANG_FK = A.PK_SEQ \r\n" + 
					"		INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "NHAPHANPHOI B ON B.PK_SEQ=A.NPP_FK \n"+
					"		WHERE A.NPP_FK IN  ("+obj.getErpCongtyId()+") AND MONTH(A.NGAYTRA) = "+obj.getMonth()+" AND YEAR(A.NGAYTRA) ="+obj.getYear()+"  AND A.TRANGTHAI IN (1, 2)  \r\n" + 
					"		GROUP BY A.KYHIEU, A.SOHOADON, A.NGAYTRA, B.PK_SEQ, B.TEN ,  B.MASOTHUE, HD_SP.PTVAT, A.SOTIENBVAT, A.GHICHU, A.PK_SEQ 	 \n"+
					"		--END HOA DON TRA LAI NHA CUNG CAP CHI NHANH (DMS)  \r\n" + */
					"  	UNION ALL    \r\n" + 
					"		--BEGIN BUT TOAN TONG HOP  CO HOA DON \r\n" + 
					" 		SELECT DISTINCT N'BTTH ERP' AS LOAIHOADON \n" +
					"       , STUFF((  SELECT DISTINCT ',' +DANHSACH.NGAYCHUNGTU + ' -- '+ CONVERT(VARCHAR, DANHSACH.SOCHUNGTU)  AS [text()]\r\n" + 
					"		FROM (\r\n" +
					"				SELECT DISTINCT MACHUNGTU as SOCHUNGTU, NGAYBUTTOAN AS NGAYCHUNGTU, \r\n" + 
					"				BTTH_HD.MAUSOHOADON, ISNULL(BTTH_HD.KYHIEU, '') KYHIEU, ISNULL(BTTH_HD.SOHOADON,'') SOHOADON, ISNULL(BTTH_HD.NGAYHOADON,'') AS NGAYXUATHD,    \r\n" + 
					"				NULL AS NCC_FK, ISNULL(BTTH_HD.TENNHACUNGCAP,'') AS TENNGUOIMUA, LTRIM(ISNULL(BTTH_HD.MASOTHUE,'')) MASOTHUE \r\n" + 
					"				FROM ERP_BUTTOANTONGHOP BTTH      \r\n" + 
					"				INNER JOIN ERP_BUTTOANTONGHOP_CHITIET_HOADON BTTH_HD ON BTTH.PK_SEQ = BTTH_HD.BTTH_FK  \r\n" + 
					" 		WHERE BTTH.PK_SEQ IN  \r\n" + 
					"			 (SELECT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE NGAYGHINHAN >= '"+obj.gettungay()+"' AND NGAYGHINHAN <= '"+obj.getdenngay()+"'  AND  \r\n" + 
					"				LOAICHUNGTU = N'BÚT TOÁN TỔNG HỢP' AND NO > 0  \r\n" + 
					"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+")))  \r\n" + 
					"			) DANHSACH\r\n" + 
					"			WHERE DANHSACH.MAUSOHOADON = BTTH_HD.MAUSOHOADON AND DANHSACH.SOHOADON = BTTH_HD.SOHOADON AND DANHSACH.KYHIEU = BTTH_HD.KYHIEU AND DANHSACH.NGAYXUATHD = BTTH_HD.NGAYHOADON AND DANHSACH.MASOTHUE = BTTH_HD.MASOTHUE\r\n" + 
					"			FOR XML PATH('') \r\n" + 
					"			), 1, 1, '' ) PK_SEQ, \r\n" + 
					"				BTTH_HD.MAUSOHOADON, ISNULL(BTTH_HD.KYHIEU, '') KYHIEU, ISNULL(BTTH_HD.SOHOADON,'') SOHOADON, ISNULL(BTTH_HD.NGAYHOADON,'') AS NGAYXUATHD,    \r\n" + 
					"                  NULL AS NCC_FK, ISNULL(BTTH_HD.TENNHACUNGCAP,'') AS TENNGUOIMUA, LTRIM(ISNULL(BTTH_HD.MASOTHUE,'')) MASOTHUE,    \r\n" + 
					"				   SUM(ISNULL(BTTH_HD.TIENHANG,0)) AS TONGTIENBVAT, ISNULL(BTTH_HD.THUESUAT,0) AS THUESUAT,    \r\n" + 
					"				   SUM(ISNULL(BTTH_HD.TIENTHUE,0)) AS VAT, SUM(ISNULL(BTTH_HD.TONGCONG,0)) AS TONGTIENAVAT,    \r\n" + 
					"				   ISNULL(BTTH_HD.GHICHU,'') GHICHU, ISNULL(DSSP.DIENGIAI,'') MATHANG    \r\n" + 
					" 		FROM ERP_BUTTOANTONGHOP BTTH     \r\n" + 
					" 		INNER JOIN ERP_BUTTOANTONGHOP_CHITIET_HOADON BTTH_HD ON BTTH.PK_SEQ = BTTH_HD.BTTH_FK    \r\n" + 
					"		INNER JOIN (\r\n" + 
					"			SELECT NGAYHOADON, MAUSOHOADON, KYHIEU, SOHOADON, THUESUAT , MAX(DIENGIAI) DIENGIAI\r\n" + 
					"			FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON HD1\r\n" + 
					"			INNER JOIN ERP_BUTTOANTONGHOP_CHITIET MHSP ON MHSP.PKSEQ =  HD1.PKSEQ  AND HD1.BTTH_FK =  MHSP.BUTTOANTONGHOP_FK \r\n" + 
					"			 GROUP BY NGAYHOADON, MAUSOHOADON, KYHIEU, SOHOADON, THUESUAT, TONGCONG \r\n" + 
					"			 HAVING (TONGCONG) = (SELECT MAX(TONGCONG) FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON HD2 WHERE HD1.MAUSOHOADON = HD2.MAUSOHOADON AND HD1.KYHIEU = HD2.KYHIEU \r\n" + 
					"							AND HD1.SOHOADON = HD2.SOHOADON AND HD1.THUESUAT = HD2.THUESUAT AND HD1.NGAYHOADON = HD2.NGAYHOADON )\r\n" + 
					"		) DSSP ON BTTH_HD.MAUSOHOADON = DSSP.MAUSOHOADON AND BTTH_HD.KYHIEU = DSSP.KYHIEU AND BTTH_HD.SOHOADON = DSSP.SOHOADON AND BTTH_HD.THUESUAT = DSSP.THUESUAT    \n" +
					" 		WHERE BTTH.PK_SEQ IN  \r\n" + 
					"			 (SELECT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE NGAYGHINHAN >= '"+obj.gettungay()+"' AND NGAYGHINHAN <= '"+obj.getdenngay()+"'  AND  \r\n" + 
					"				LOAICHUNGTU = N'BÚT TOÁN TỔNG HỢP' AND NO > 0  \r\n" + 
					"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+")))  \r\n" + 
					"  		GROUP BY BTTH_HD.MAUSOHOADON,DSSP.DIENGIAI, BTTH_HD.KYHIEU, BTTH_HD.SOHOADON , BTTH_HD.NGAYHOADON, BTTH_HD.TENNHACUNGCAP, BTTH_HD.MASOTHUE,   \r\n" + 
					" 		ISNULL(BTTH_HD.THUESUAT,0),ISNULL(BTTH_HD.GHICHU,'')\r\n" + 
					"		--END BUT TOAN TONG HOP CO HOA DON   \r\n" + 
					/*"  	UNION ALL    \r\n" + 
					"		--BEGIN BUT TOAN TONG HOP  CO HOA DON \r\n" + 
					" 		SELECT DISTINCT N'BTTH DMS' AS LOAIHOADON "+
					"       , STUFF((  SELECT DISTINCT ',' +DANHSACH.NGAYCHUNGTU + ' -- '+ CONVERT(VARCHAR, DANHSACH.SOCHUNGTU)  AS [text()]\r\n" + 
					"		FROM (\r\n" +
					"				SELECT DISTINCT MACHUNGTU as SOCHUNGTU, NGAYBUTTOAN AS NGAYCHUNGTU, \r\n" + 
					"				BTTH_HD.MAUSOHOADON, ISNULL(BTTH_HD.KYHIEU, '') KYHIEU, ISNULL(BTTH_HD.SOHOADON,'') SOHOADON, ISNULL(BTTH_HD.NGAYHOADON,'') AS NGAYXUATHD,    \r\n" + 
					"				NULL AS NCC_FK, ISNULL(BTTH_HD.TENNHACUNGCAP,'') AS TENNGUOIMUA, LTRIM(ISNULL(BTTH_HD.MASOTHUE,'')) MASOTHUE \r\n" + 
					"				FROM " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_BUTTOANTONGHOP BTTH      \r\n" + 
					"				INNER JOIN " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_BUTTOANTONGHOP_CHITIET_HOADON BTTH_HD ON BTTH.PK_SEQ = BTTH_HD.BTTH_FK  \r\n" + 
					" 		WHERE BTTH.PK_SEQ IN  \r\n" + 
					"			 (SELECT SOCHUNGTU FROM " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN WHERE MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND  \r\n" + 
					"				LOAICHUNGTU = N'BÚT TOÁN TỔNG HỢP' AND NO > 0  \r\n" + 
					"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+")))  \r\n" + 
					"			) DANHSACH\r\n" + 
					"			WHERE DANHSACH.MAUSOHOADON = BTTH_HD.MAUSOHOADON AND DANHSACH.SOHOADON = BTTH_HD.SOHOADON AND DANHSACH.KYHIEU = BTTH_HD.KYHIEU AND DANHSACH.NGAYXUATHD = BTTH_HD.NGAYHOADON AND DANHSACH.MASOTHUE = BTTH_HD.MASOTHUE\r\n" + 
					"			FOR XML PATH('') \r\n" + 
					"			), 1, 1, '' ) PK_SEQ, \r\n" + 
					"				BTTH_HD.MAUSOHOADON, ISNULL(BTTH_HD.KYHIEU, '') KYHIEU, ISNULL(BTTH_HD.SOHOADON,'') SOHOADON, ISNULL(BTTH_HD.NGAYHOADON,'') AS NGAYXUATHD,    \r\n" + 
					"                  NULL AS NCC_FK, ISNULL(BTTH_HD.TENNHACUNGCAP,'') AS TENNGUOIMUA, LTRIM(ISNULL(BTTH_HD.MASOTHUE,'')) MASOTHUE,    \r\n" + 
					"				   SUM(ISNULL(BTTH_HD.TIENHANG,0)) AS TONGTIENBVAT, ISNULL(BTTH_HD.THUESUAT,0) AS THUESUAT,    \r\n" + 
					"				   SUM(ISNULL(BTTH_HD.TIENTHUE,0)) AS VAT, SUM(ISNULL(BTTH_HD.TONGCONG,0)) AS TONGTIENAVAT,    \r\n" + 
					"				   ISNULL(BTTH_HD.GHICHU,'') GHICHU, ISNULL(DSSP.DIENGIAI,'') MATHANG    \r\n" + 
					" 		FROM " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_BUTTOANTONGHOP BTTH     \r\n" + 
					" 		INNER JOIN " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_BUTTOANTONGHOP_CHITIET_HOADON BTTH_HD ON BTTH.PK_SEQ = BTTH_HD.BTTH_FK    \r\n" + 
					"		INNER JOIN (\r\n" + 
					"			SELECT NGAYHOADON, MAUSOHOADON, KYHIEU, SOHOADON, THUESUAT , MAX(DIENGIAI) DIENGIAI\r\n" + 
					"			FROM " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_BUTTOANTONGHOP_CHITIET_HOADON HD1\r\n" + 
					"			INNER JOIN " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_BUTTOANTONGHOP_CHITIET MHSP ON MHSP.PKSEQ =  HD1.PKSEQ  AND HD1.BTTH_FK =  MHSP.BUTTOANTONGHOP_FK \r\n" + 
					"			 GROUP BY NGAYHOADON, MAUSOHOADON, KYHIEU, SOHOADON, THUESUAT, TONGCONG \r\n" + 
					"			 HAVING (TONGCONG) = (SELECT MAX(TONGCONG) FROM " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_BUTTOANTONGHOP_CHITIET_HOADON HD2 WHERE HD1.MAUSOHOADON = HD2.MAUSOHOADON AND HD1.KYHIEU = HD2.KYHIEU \r\n" + 
					"							AND HD1.SOHOADON = HD2.SOHOADON AND HD1.THUESUAT = HD2.THUESUAT AND HD1.NGAYHOADON = HD2.NGAYHOADON )\r\n" + 
					"		) DSSP ON BTTH_HD.MAUSOHOADON = DSSP.MAUSOHOADON AND BTTH_HD.KYHIEU = DSSP.KYHIEU AND BTTH_HD.SOHOADON = DSSP.SOHOADON AND BTTH_HD.THUESUAT = DSSP.THUESUAT    \n" +
					" 		WHERE BTTH.PK_SEQ IN  \r\n" + 
					"			 (SELECT SOCHUNGTU FROM " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN WHERE MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND  \r\n" + 
					"				LOAICHUNGTU = N'BÚT TOÁN TỔNG HỢP' AND NO > 0  \r\n" + 
					"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+")))  \r\n" + 
					"  		GROUP BY BTTH_HD.MAUSOHOADON,DSSP.DIENGIAI, BTTH_HD.KYHIEU, BTTH_HD.SOHOADON , BTTH_HD.NGAYHOADON, BTTH_HD.TENNHACUNGCAP, BTTH_HD.MASOTHUE,   \r\n" + 
					" 		ISNULL(BTTH_HD.THUESUAT,0),ISNULL(BTTH_HD.GHICHU,'')\r\n" + 
					"		--END BUT TOAN TONG HOP CO HOA DON   \r\n" + */
					"	UNION ALL  \r\n" + 
					"		--BEGIN CHI/UY NHIEM CHI KHAC \r\n" + 
					"		SELECT  N'CHI/ỦY NHIỆM CHI KHÁC ERP' AS LOAIHOADON, CONVERT(VARCHAR, A.PK_SEQ) AS PK_SEQ, \r\n" + 
					"		ISNULL(B.MAUHOADON, '') AS MAUSOHOADON, B.KYHIEU, B.SOHOADON, B.NGAYHOADON AS NGAYXUATHD, NULL AS NCC_FK, B.TENNCC AS TENNGUOIMUA, B.MST AS MASOTHUE,   \r\n" + 
					"		B.TIENHANG AS TONGTIENBVAT, B.THUESUAT, TIENTHUE AS VAT, B.TIENHANG+B.TIENTHUE AS TONGTIENAVAT  \r\n" + 
					"		, B.DIENGIAI AS GHICHU, B.DIENGIAI AS MATHANG  \r\n" + 
					"		FROM ERP_THANHTOANHOADON A  \r\n" + 
					"		INNER JOIN ERP_THANHTOANHOADON_PHINGANHANG B ON A.PK_SEQ = B.THANHTOANHD_FK  \r\n" + 
					" 		WHERE A.PK_SEQ IN  \r\n" + 
					"			 (SELECT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE NGAYGHINHAN >= '"+obj.gettungay()+"' AND NGAYGHINHAN <= '"+obj.getdenngay()+"'  AND  \r\n" + 
					"				(LOAICHUNGTU = N'TRẢ KHÁC')  \r\n" + 
					"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+")))  \r\n" + 
					"		--END CHI/UY NHIEM CHI  \r\n" + 
				/*	"	UNION ALL  \r\n" + 
					"		--BEGIN CHI/UY NHIEM CHI  \r\n" + 
					"		SELECT  N'CHI/ỦY NHIỆM CHI KHÁC DMS' AS LOAIHOADON, CONVERT(VARCHAR, A.PK_SEQ)  AS PK_SEQ, \r\n" + 
					"		ISNULL(B.MAUHOADON, '') AS MAUSOHOADON, B.KYHIEU, B.SOHOADON, B.NGAYHOADON AS NGAYXUATHD, NULL AS NCC_FK, B.TENNCC AS TENNGUOIMUA, B.MST AS MASOTHUE,   \r\n" + 
					"		B.TIENHANG AS TONGTIENBVAT, B.THUESUAT, TIENTHUE AS VAT, B.TIENHANG+B.TIENTHUE AS TONGTIENAVAT  \r\n" + 
					"		, B.DIENGIAI AS GHICHU, B.DIENGIAI AS MATHANG  \r\n" + 
					"		FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_THANHTOANHOADON A  \r\n" + 
					"		INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_THANHTOANHOADON_PHINGANHANG B ON A.PK_SEQ = B.THANHTOANHD_FK  \r\n" + 
					" 		WHERE A.PK_SEQ IN  \r\n" + 
					"			 (SELECT SOCHUNGTU FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN WHERE MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND  \r\n" + 
					"				(LOAICHUNGTU = N'TRẢ KHÁC' ) \r\n" + 
					"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+")))  \r\n" + 
					"		--END CHI/UY NHIEM CHI  \r\n" + */
					"	UNION ALL  \r\n" + 
					"		--BEGIN HOA DON DIEU CHINH TANG GIAM NCC  \r\n" + 
					"		SELECT N'HÓA ĐƠN ĐIỀU CHỈNH TĂNG GIẢM NCC' AS LOAIHOADON, CONVERT(VARCHAR, HDSP.HOADONKHACNCC_FK) AS PK_SEQ,\r\n" + 
					"		ISNULL(HD.MAUHOADON, '') AS MAUSOHOADON, HD.KYHIEUHD KYHIEU, HD.SOHOADON, HD.NGAYHOADON AS NGAYXUATHD, \r\n" + 
					"		HD.NHACUNGCAP_FK AS NCC_FK, NCC.TEN AS TENNGUOIMUA, HD.MASOTHUE AS MASOTHUE, \r\n" + 
					"		SUM(HDSP.SOLUONG*HDSP.DONGIA) AS TONGTIENBVAT, HDSP.PHANTRAMVAT AS THUESUAT, SUM(TIENVATVND) AS VAT, SUM(THANHTIENVND) AS TONGTIENAVAT \r\n" + 
					"		, '' AS GHICHU, DSSP.DIENGIAI AS MATHANG\r\n" + 
					"		FROM ERP_HOADONKHACNCC HD\r\n" + 
					"		INNER JOIN ERP_HOADONKHACNCC_SANPHAM HDSP ON HD.PK_SEQ = HDSP.HOADONKHACNCC_FK\r\n" + 
					"		INNER JOIN ERP_NHACUNGCAP NCC ON HD.NHACUNGCAP_FK = NCC.PK_SEQ\r\n" + 
					"		INNER JOIN \r\n" + 
					"		(\r\n" + 
					"			SELECT HOADONKHACNCC_FK, PHANTRAMVAT , MAX(TENHANG) DIENGIAI\r\n" + 
					"			FROM ERP_HOADONKHACNCC_SANPHAM HDSP\r\n" + 
					"			GROUP BY HOADONKHACNCC_FK, PHANTRAMVAT, THANHTIENVND \r\n" + 
					"			HAVING ABS(THANHTIENVND) = (SELECT MAX(ABS(THANHTIENVND)) FROM ERP_HOADONKHACNCC_SANPHAM HDSP1 WHERE HDSP.HOADONKHACNCC_FK = HDSP1.HOADONKHACNCC_FK \r\n" + 
					"			AND HDSP.PHANTRAMVAT = HDSP1.PHANTRAMVAT)\r\n" + 
					"		) DSSP ON DSSP.HOADONKHACNCC_FK = HDSP.HOADONKHACNCC_FK AND HDSP.PHANTRAMVAT = DSSP.PHANTRAMVAT\r\n" + 
					"		WHERE HDSP.HOADONKHACNCC_FK IN   \r\n" + 
					"			 (SELECT SOCHUNGTU FROM  ERP_PHATSINHKETOAN WHERE NGAYGHINHAN >= '"+obj.gettungay()+"' AND NGAYGHINHAN <= '"+obj.getdenngay()+"'  AND  \r\n" + 
					"				(LOAICHUNGTU = N'Hóa đơn điều chỉnh giảm giá NCC' ) \r\n" + 
					"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM  ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+")))  \r\n" + 
					"		GROUP BY HDSP.HOADONKHACNCC_FK, HD.MAUHOADON, HD.KYHIEUHD, HD.SOHOADON, HD.NGAYHOADON, HD.MASOTHUE, HD.NHACUNGCAP_FK, NCC.TEN, HD.MASOTHUE, HDSP.PHANTRAMVAT, DSSP.DIENGIAI \n"	+
					"		--END HOA DON DIEU CHINH TANG GIAM NCC  \r\n" + 
					") A  \r\n" + 
					"WHERE (NGAYXUATHD <> '' OR MAUSOHOADON != '' OR KYHIEU != '' OR SOHOADON != '' ) \r\n" + 
					"ORDER BY NGAYXUATHD   \r\n" + 
					"    \n" ;

			System.out.println("::: CHEN BANG TAM:\n " + sql);
			db.update(sql);
			sql = "select * from #Tb_ufn_bangkedauvaoERP";
			System.out.println("BC Thue: " + sql);

		  	rs=db.get(sql);
		  	
		  	int index = 18;
			int stt = 1;
			
			Style style;
			Font font2 = new Font();
			font2.setName("Arial");				
			font2.setSize(10);
			
			Font font3 = new Font();
			font3.setName("Arial");				
			font3.setSize(10);
			font3.setBold(true);
			
			Cell cell_mau01 = cells.getCell("B1");
//			Cell cell_mau02 = cells.getCell("L1");
			
			Cell cell_mau = cells.getCell("C7");
//			Cell cell_mau2 = cells.getCell("L7");
			 
//			double thuesuat=0;
//			double totaltienavat=0;
			double totaltienbvat=0;
			double totalvat=0;
			stt = 1;
			if (rs != null) 
			{
				try 
				{				
					while (rs.next())
					{	
						cell = cells.getCell("B"+String.valueOf(index));cell.setValue(stt);	
						this.setStyleColorLeft(cells, cell);
						cell = cells.getCell("C"+String.valueOf(index));cell.setValue(rs.getString("mausohoadon"));	
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("D"+String.valueOf(index));cell.setValue(rs.getString("kyhieu"));
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("E"+String.valueOf(index));cell.setValue(rs.getString("sohoadon"));
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("F"+String.valueOf(index));cell.setValue(rs.getString("NGAY"));
						this.setStyleColorCenter(cells, cell);
						cell = cells.getCell("G"+String.valueOf(index));cell.setValue(rs.getString("tennguoimua"));
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("H"+String.valueOf(index));cell.setValue(rs.getString("masothue"));
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("I"+String.valueOf(index));cell.setValue(rs.getString("mathang"));
						this.setStyleColorNormar(cells, cell);
						
						cell = cells.getCell("J"+String.valueOf(index));
						this.setStyleColorRight(cells, cell);
						style = cell.getStyle();
						style.setNumber(3);
						cell.setStyle(style);
						cell.setValue(formatter.format(rs.getDouble("tongtienbvat")));
						
						totaltienbvat=totaltienbvat+rs.getDouble("tongtienbvat");
						
						//thuesuat=rs.getDouble("vat")*100 / rs.getDouble("tongtienbvat");
						
						cell = cells.getCell("K"+String.valueOf(index));
						cell.setValue(rs.getDouble("THUESUAT"));
						this.setStyleColorRight(cells, cell);
						style = cell.getStyle();
						style.setNumber(3);
						cell.setStyle(style);
	
						cell = cells.getCell("L"+String.valueOf(index));
						this.setStyleColorRight(cells, cell);
						style = cell.getStyle();
						style.setNumber(3);
						cell.setStyle(style);
						cell.setValue(formatter.format(rs.getDouble("vat")));
	
						totalvat=totalvat + rs.getDouble("vat");					
						
						cell = cells.getCell("M"+String.valueOf(index));cell.setValue(rs.getString("ghichu"));		
						this.setStyleColorNormar(cells, cell);


						Font whiteFont = new Font();
						whiteFont.setName("Arial");				
						whiteFont.setSize(10);
						whiteFont.setColor(Color.WHITE);
						
						cell = cells.getCell("N"+String.valueOf(index));
						cell.setValue(rs.getString("LOAIHOADON"));
						style = cell.getStyle();
						cell.setStyle(style);
						this.setStyleColorNormar(cells, cell);
						
						String daySoChungTu = "";
						String day = rs.getString("PK_SEQ").trim();
						if (day.indexOf(",") > -1) {
							String[] thongTinChungTuArray = day.split(",");
							for (String thongTinChungTu : thongTinChungTuArray) {
								if (thongTinChungTu.indexOf(" -- ") > -1) {
									daySoChungTu += ( daySoChungTu.length() > 0 ) ? ", "+thongTinChungTu.split(" -- ")[1] : thongTinChungTu.split(" -- ")[1] ;
								} else {
									daySoChungTu += thongTinChungTu;
								}
							}
						} else {
							if (day.indexOf(" -- ") > -1) {
								daySoChungTu += ( daySoChungTu.length() > 0 ) ? ", "+day.split(" -- ")[1] : day.split(" -- ")[1] ;
							} else {
								daySoChungTu += day;
							}
						}
						
						cell = cells.getCell("O"+String.valueOf(index));
						cell.setValue(rs.getString("PK_SEQ"));
						style = cell.getStyle();
						style.setNumber(0);
						cell.setStyle(style);
						this.setStyleColorNormar(cells, cell);
						

						cell = cells.getCell("P"+String.valueOf(index));
						cell.setValue(daySoChungTu);
						style = cell.getStyle();
						cell.setStyle(style);
						this.setStyleColorNormar(cells, cell);
						
						index++;
						stt++;
					}
					rs.close();
												
					cell = cells.getCell("B" + Integer.toString(index));	
	        		cell.setValue("Tổng"); 	 
	        		style = cell_mau.getStyle(); style.setFont(font3); 
	        		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
	        		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	        		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
	        		cell.setStyle(style); 
	        		setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
	        		
					cell = cells.getCell("C"+String.valueOf(index));cell.setValue("");	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("D"+String.valueOf(index));cell.setValue("");
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("E"+String.valueOf(index));cell.setValue("");
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("F"+String.valueOf(index));cell.setValue("");
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("G"+String.valueOf(index));cell.setValue("");
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("H"+String.valueOf(index));cell.setValue("");
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("I"+String.valueOf(index));cell.setValue("");
					this.setStyleColorNormar(cells, cell);
	
					cell = cells.getCell("J"+String.valueOf(index));
					this.setStyleColorRight(cells, cell);
					style = cell.getStyle();
					 style.setFont(font3); 
					style.setNumber(3);
					cell.setStyle(style);
					cell.setValue(formatter.format(totaltienbvat));
					
					cell = cells.getCell("K"+String.valueOf(index));cell.setValue("");
					this.setStyleColorRight(cells, cell);
									
					cell = cells.getCell("L"+String.valueOf(index));
					this.setStyleColorRight(cells, cell);
					style = cell.getStyle();
					 style.setFont(font3); 
					style.setNumber(3);
					cell.setStyle(style);
					cell.setValue(formatter.format(totalvat));
					
					cell = cells.getCell("M"+String.valueOf(index));cell.setValue("");
					this.setStyleColorNormar(cells, cell);

					cell = cells.getCell("N"+String.valueOf(index));cell.setValue("");
					this.setStyleColorNormar(cells, cell);

					cell = cells.getCell("O"+String.valueOf(index));cell.setValue("");
					this.setStyleColorNormar(cells, cell);

					cell = cells.getCell("P"+String.valueOf(index));cell.setValue("");
					this.setStyleColorNormar(cells, cell);
					
					for(int k =0; k<4; k++){
						index = index+1;					
						
						cells.merge(index-1, 1, index-1, 12);		
						cell = cells.getCell("B" + Integer.toString(index));	
						if(k==0){
							cell.setValue("2. Hàng hoá, dịch vụ không đủ điều kiện khấu trừ::"); 	style = cell_mau01.getStyle(); style.setFont(font2);  style.setBorderLine(BorderType.LEFT, BorderLineType.THIN); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						}else if (k == 1){
							cell.setValue("3. Hàng hoá, dịch vụ dùng chung cho SXKD chịu thuế và không chịu thuế đủ điều kiện khấu trừ thuế:"); 	style = cell_mau01.getStyle(); style.setFont(font2);  style.setBorderLine(BorderType.LEFT, BorderLineType.THIN); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						}else if (k == 2){
							cell.setValue("4. Hàng hóa, dịch vụ dùng cho dự án đầu tư đủ điều kiện được khấu trừ thuế (*):"); 	style = cell_mau01.getStyle(); style.setFont(font2);  style.setBorderLine(BorderType.LEFT, BorderLineType.THIN); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						}else if (k == 3){
							cell.setValue("5. Hàng hóa, dịch vụ không phải tổng hợp trên tờ khai 01/GTGT:"); 	style = cell_mau01.getStyle(); style.setFont(font2);  style.setBorderLine(BorderType.LEFT, BorderLineType.THIN); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						}
				
			
						cell = cells.getCell("C" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("D" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("E" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
						cell = cells.getCell("F" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
						cell = cells.getCell("G" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("H" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
						cell = cells.getCell("I" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
						cell = cells.getCell("J" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("K" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("L" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("M" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("N" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("O" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("P" + Integer.toString(index));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						
						
						index = index+1;	
						cell = cells.getCell("B" + Integer.toString(index));	
		        		cell.setValue("Tổng"); 	 
		        		style = cell_mau.getStyle(); style.setFont(font3); 
		        		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		        		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		        		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
		        		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
		        		cell.setStyle(style); 
		        		setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		        		
						cell = cells.getCell("C"+String.valueOf(index));cell.setValue("");	
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("D"+String.valueOf(index));cell.setValue("");
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("E"+String.valueOf(index));cell.setValue("");
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("F"+String.valueOf(index));cell.setValue("");
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("G"+String.valueOf(index));cell.setValue("");
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("H"+String.valueOf(index));cell.setValue("");
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("I"+String.valueOf(index));cell.setValue("");
						this.setStyleColorNormar(cells, cell);
	
						cell = cells.getCell("J"+String.valueOf(index));
						this.setStyleColorRight(cells, cell);
						style = cell.getStyle();
						style.setNumber(3);
						cell.setStyle(style);
						cell.setValue("");
						
						cell = cells.getCell("K"+String.valueOf(index));cell.setValue("");
						this.setStyleColorRight(cells, cell);
										
						cell = cells.getCell("L"+String.valueOf(index));
						this.setStyleColorRight(cells, cell);
						style = cell.getStyle();
						style.setNumber(3);
						cell.setStyle(style);
						cell.setValue("");
						
						cell = cells.getCell("M"+String.valueOf(index));cell.setValue("");
						this.setStyleColorNormar(cells, cell);	
						cell = cells.getCell("N"+String.valueOf(index));cell.setValue("");
						this.setStyleColorNormar(cells, cell);	
						cell = cells.getCell("O"+String.valueOf(index));cell.setValue("");
						this.setStyleColorNormar(cells, cell);	
						cell = cells.getCell("P"+String.valueOf(index));cell.setValue("");
						this.setStyleColorNormar(cells, cell);		
					}
							
									
					index=index+2;
					
					cells.merge(index-1, 1, index-1, 7);		
					cell = cells.getCell("B" + Integer.toString(index));	
					cell.setValue("Tổng giá trị HHDV mua vào phục vụ SXKD được khấu trừ thuế GTGT (**):" + formatter.format(totaltienbvat) ); 	style = cell_mau01.getStyle(); style.setFont(font2);  
									
					index++;
					
					cells.merge(index-1, 1, index-1, 7);		
					cell = cells.getCell("B" + Integer.toString(index));	
					cell.setValue("Tổng số thuế GTGT của HHDV mua vào đủ điều kiện được khấu trừ (***):" + formatter.format(totalvat) ); 	style = cell_mau01.getStyle(); style.setFont(font2);
		
					
					index=index+2;
					
					cells.merge(index-2, 1, index-2, 3);				
					cell = cells.getCell("J" + Integer.toString(index));cell.setValue(",ngày "+obj.getDate().substring(8,10)+" tháng  "+obj.getDate().substring(5,7)+" năm "+obj.getDate().substring(0,4)); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style);
					index=index+1;
					
					cells.merge(index-1, 1, index-1, 3);
					cell = cells.getCell("J"+String.valueOf(index));cell.setValue("NGƯỜI NỘP THUẾ hoặc"); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle_(cell, HorizontalAlignmentType.CENTRED);
					index=index+1;
					
					cells.merge(index-1, 1, index-1, 3);
					cell = cells.getCell("J"+String.valueOf(index));cell.setValue("ĐẠI DIỆN HỢP PHÁP CỦA NGƯỜI NỘP THUẾ"); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle_(cell, HorizontalAlignmentType.CENTRED);
					index=index+1;
					
					cells.merge(index-1, 1, index-1, 3);
					cell = cells.getCell("J"+String.valueOf(index));cell.setValue(" Ký tên, đóng dấu (ghi rõ họ tên và chức vụ)");	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style);	 setCellBorderStyle_(cell, HorizontalAlignmentType.CENTRED);	
					this.setStyleColorNormar(cells, cell);
					
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					throw new Exception(ex.getMessage());
				}finally {
					if (rs != null)
						rs.close();
				}
			}
			else
			{
				return false;
			}	
		}catch (Exception e) {
			e.printStackTrace();
		}finally
		{	
			String sql = 
					"--CAC PHIEU PHAI PHAT SINH DINH KHOAN MOI LEN BAO CAO  \r\n" + 
					" IF OBJECT_ID('tempdb.dbo.#Tb_ufn_bangkedauvaoERP') IS NOT NULL DROP TABLE #Tb_ufn_bangkedauvaoERP ";
			db.update(sql);

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			if(db != null)
				db.shutDown();
		}

		return true;
	}	
	
	public static String ConvertDate(String st){
		if(st.length() > 0){
			String nam  = st.substring(0, 4);
			String thang = st.substring(5, 7);
			String ngay = st.substring(8, 10);
		
			return (ngay + "-" + thang + "-" + nam);
		}else{
			return "";
		}
	}
	private void setCellBorderStyle(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		//style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setHAlignment(alignment);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
	private void setCellBorderStyle_(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		//style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setHAlignment(alignment);
		cell.setStyle(style);
	}
	
	private void setStyleColorLeft(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("P1");
		Style style;	
		 style = cell1.getStyle();
		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        cell.setStyle(style);
	}
}