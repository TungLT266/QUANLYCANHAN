package geso.traphaco.erp.servlets.reports;

import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

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

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpBCTheodoitamungNV extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpBCTheodoitamungNV() {
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
		obj.setErpCongtyId((String)session.getAttribute("congtyId"));
		
		obj.InitErp();
		session.setAttribute("kbhId", "");
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheodoitamungNV.jsp";
		
		
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		IStockintransit obj = new Stockintransit();
		obj.setErpCongtyId(ctyId);
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		obj.settungay(util.antiSQLInspection(request.getParameter("tungay")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
	
		obj.setNhanvienId(util.antiSQLInspection(request.getParameter("nvid")));
		
		obj.setErpCongtyId((String)session.getAttribute("congtyId"));		

		obj.InitErp();
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheodoitamungNV.jsp";
		
		System.out.println("Action la: " + action);
		
		if (action.equals("Taomoi")) 
		{			
//			response.setContentType("application/xlsm");
//			response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChiTietCongNoNV.xlsm");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=PhieuXuatKhoTT.pdf");
			
	        try 
	        {

	        	if(obj.getNhanvienId().length() > 0){
	    			Document document = new Document(PageSize.LETTER);
	    			ServletOutputStream outstream = response.getOutputStream();
	        		CreateTheodoitamung_chitiet(document, outstream, obj);
	        		
	        	}else{
	    			Document document = new Document(PageSize.LETTER.rotate());
	    			ServletOutputStream outstream = response.getOutputStream();
	        		CreateTheodoitamung_tongthe(document, outstream, obj);
	        	}
/*				if(!CreatePivotTable(out, response, request, obj))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Xin loi khong co bao cao trong thoi gian nay");
				    writer.close();
				}*/
			} 
	        catch (Exception e) 
	        {
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				System.out.println(e.toString());
			}
		}
		
		
		return;
	}
	
	public String getQuery(IStockintransit obj)
	{
			
		String query = 	"\n SELECT * FROM " +
						"\n ( " +
						
						// PHIẾU CHI || ỦY NHIỆM CHI
						"\n	SELECT NV.PK_SEQ AS NVID, NV.TEN, PSKT.PK_SEQ, PSKT.NGAYGHINHAN AS NGAYCT, PSKT.SOCHUNGTU, " +
						"\n	TTHD.NOIDUNGTT, ISNULL(PSKT.NO, 0) AS NO, ISNULL(PSKT.CO, 0) AS CO " +
						"\n	FROM ERP_PHATSINHKETOAN PSKT  " +
						"\n	INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU " +
						"\n	INNER JOIN ERP_THANHTOANHOADON_HOADON TTHD_HD ON TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK " +
						"\n	INNER JOIN ERP_TAMUNG TU ON TU.PK_SEQ = TTHD_HD.HOADON_FK " +
						"\n	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG " +
						"\n	WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR  " +
						"\n		   PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') " +
						"\n		   ) " +
						"\n	AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') " +
						"\n	AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' " +
						"\n AND TTHD.CONGTY_FK = "+obj.getErpCongtyId()+

						// ĐỀ NGHỊ THANH TOÁN
						"\n	UNION ALL " +
						"\n	SELECT NV.PK_SEQ AS NVID,  NV.TEN, PSKT.PK_SEQ, PSKT.NGAYGHINHAN AS NGAYCT,  " +
						"\n	PSKT.SOCHUNGTU, MH.LYDOTT, ISNULL(PSKT.NO, 0) AS NO, ISNULL(PSKT.CO, 0) AS CO " +
						"\n	FROM ERP_PHATSINHKETOAN PSKT " +
						"\n	INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = PSKT.SOCHUNGTU " +
						"\n	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG " +
						"\n	WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR " +
						"\n		   PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') " +
						"\n		   ) " +
						"\n	AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  AND PSKT.CO > 0 " +
						"\n	AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' " +
						"\n AND MH.CONGTY_FK = "+obj.getErpCongtyId()+

						"\n )A WHERE A.NVID = " + obj.getNhanvienId() + " " +
						"\n ORDER BY A.NGAYCT "; 
			
				
				
		System.out.println("Get Sql : "+ query);
	    return query;   
	}
	
	public String getDauky(IStockintransit obj)
	{
		String query;
		query = "\n SELECT ISNULL(SUM(NO), 0) AS NO, ISNULL(SUM(CO), 0) AS CO FROM " +
				"\n (  " +
				
				// PHIẾU CHI || ỦY NHIỆM CHI
			
				"\n		SELECT NV.PK_SEQ AS NVID, NV.TEN,  TU.NGAYTAMUNG AS NGAYCT, CONVERT(VARCHAR, TU.PK_SEQ)  AS SOCHUNGTU, TU.LYDOTAMUNG,  " + 
				"\n		SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO  " +
				"\n		FROM ERP_PHATSINHKETOAN PSKT  " +
				"\n		INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU \n " +
				"\n		INNER JOIN ERP_THANHTOANHOADON_HOADON TTHD_HD ON TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK " +
				"\n		INNER JOIN ERP_TAMUNG TU ON TU.PK_SEQ = TTHD_HD.HOADON_FK " +
				"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TTHD.NHANVIEN_FK = NV.PK_SEQ " +
				"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR " +
				"\n				PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')) " +
				"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  AND TTHD.TRANGTHAI != 2 " + 
				"\n		AND PSKT.NGAYGHINHAN < '" + obj.gettungay() + "' " +
				"\n 	AND (PSKT.LOAICHUNGTU =  N'Thanh toán hóa đơn') " +
				"\n		AND TTHD.CONGTY_FK = "+obj.getErpCongtyId()+	
				"\n		GROUP BY NV.PK_SEQ, NV.TEN, TU.NGAYTAMUNG, TU.PK_SEQ, TU.LYDOTAMUNG  " +

				"\n		UNION ALL  " +
				
				// ĐỀ NGHỊ THANH TOÁN
				
				"\n		SELECT NV.PK_SEQ AS NVID,  NV.TEN, MH.NGAYMUA AS NGAYCT, MH.SOPO AS SOCHUNGTU, MH.LYDOTT, " + 
				"\n		SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO  " +
				"\n		FROM ERP_PHATSINHKETOAN PSKT  " +
				"\n		INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = PSKT.SOCHUNGTU  " +
				"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND MH.NHANVIEN_FK = NV.PK_SEQ  " +
				"\n		WHERE MH.TYPE = 1 AND MH.LOAIHANGHOA_FK = '2' AND " +
				"\n		(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR  " +
				"\n		 PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') " +
				"\n		)  " +
				"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') AND MH.TRANGTHAI != 2 " +
				"\n		AND PSKT.NGAYGHINHAN < '" + obj.gettungay() + "' " +
				"\n		AND MH.CONGTY_FK = "+obj.getErpCongtyId()+
				"\n 	AND PSKT.LOAICHUNGTU = N'Chi phí khác' "+
				"\n		GROUP BY NV.PK_SEQ,  NV.TEN, MH.NGAYMUA, MH.SOPO, MH.LYDOTT " + 
				
		 		"\n		UNION ALL  " +
		 		
		 		// BÚT TOÁN TỔNG HỢP
		 		
		 		"\n		SELECT BTTH.NHANVIEN_FK AS NVID, BTTH.TEN, BTTH.NGAYBUTTOAN AS NGAYCT, CONVERT(VARCHAR, BTTH.PK_SEQ) AS SOCHUNGTU, BTTH.DIENGIAI, " +
		 		"\n	   	SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO " +
		 		"\n		FROM ERP_PHATSINHKETOAN PSKT  " +
		 		"\n		INNER JOIN "+
		 		"\n		(SELECT distinct BTTH.NGAYBUTTOAN, BTTH.PK_SEQ,  BTTH_CT.NHANVIEN_FK, BTTH_CT.TAIKHOANKT_FK, NV.TEN, BTTH.DIENGIAI "+
		 		"\n		 FROM   ERP_BUTTOANTONGHOP BTTH INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTH_CT ON BTTH_CT.BUTTOANTONGHOP_FK = BTTH.PK_SEQ "+ 
		 		"\n	  			INNER JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = BTTH_CT.NHANVIEN_FK "+
		 		"\n		 WHERE BTTH.CONGTY_FK = "+obj.getErpCongtyId()+ " AND BTTH.TRANGTHAI !=2 "+
		 		"\n		) BTTH ON CAST(BTTH.PK_SEQ AS NVARCHAR(50)) = PSKT.SOCHUNGTU    AND PSKT.MADOITUONG = CAST(BTTH.NHANVIEN_FK AS NVARCHAR(50)) "+ 
		 		"\n		WHERE BTTH.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') AND " +
		 		"\n		(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR " +
		 		"\n			PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')  " + 
		 		"\n		)  " +
		 		"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  " + 
		 		"\n		AND PSKT.NGAYGHINHAN < '" + obj.gettungay() + "' " +
		 		"\n	 	AND PSKT.LOAICHUNGTU = N'Bút toán tổng hợp' " +
		 		"\n		GROUP BY BTTH.NHANVIEN_FK,  BTTH.TEN, BTTH.NGAYBUTTOAN, BTTH.PK_SEQ, BTTH.DIENGIAI " +
		 		
		 		"\n 	UNION ALL "+	
		 		
		 		// PHIẾU CHI || ỦY NHIỆM CHI
		 		
				"\n 	SELECT NV.PK_SEQ AS NVID, NV.TEN, TU.NGAYMUA AS NGAYCT,  CONVERT(VARCHAR, TTHD.PK_SEQ) AS SOCHUNGTU, TU.LYDOTT, "+  
				"\n  	SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO "+ 
				"\n  	FROM ERP_PHATSINHKETOAN PSKT "+ 
				"\n  	INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU "+ 
				"\n  	INNER JOIN ERP_THANHTOANHOADON_HOADON TTHD_HD ON TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK "+ 
				"\n  	INNER JOIN ERP_MUAHANG TU ON TU.PK_SEQ = TTHD_HD.HOADON_FK "+ 
				"\n  	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TTHD.NHANVIEN_FK = NV.PK_SEQ "+ 
				"\n  	WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
				"\n  	PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') "+ 
				"\n  	) "+ 
				"\n  	AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  "+
				"\n 	AND (PSKT.LOAICHUNGTU =  N'Thanh toán hóa đơn') AND TTHD.TRANGTHAI != 2 "+	
				"\n		AND PSKT.NGAYGHINHAN < '" + obj.gettungay() + "' AND TTHD.CONGTY_FK = " +obj.getErpCongtyId()+
				"\n     GROUP BY NV.PK_SEQ, NV.TEN, TU.NGAYMUA, TTHD.PK_SEQ, TU.LYDOTT "+		  
		 		
				"\n 	UNION ALL "+	
				
				// THU TIỀN - THU HỒI TẠM ỨNG
				"\n 	SELECT NV.PK_SEQ AS NVID, NV.TEN, TT.NGAYCHUNGTU AS NGAYCT, CONVERT(VARCHAR, TT.PK_SEQ) AS SOCHUNGTU, "+ 
		 		"\n		TT.GhiChu, SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO "+ 
		 		"\n		FROM ERP_PHATSINHKETOAN PSKT "+ 
		 		"\n		INNER JOIN ERP_THUTIEN TT ON TT.PK_SEQ = CONVERT(VARCHAR, PSKT.SOCHUNGTU ) "+		  	
		 		"\n		INNER JOIN ERP_THUTIEN_HOADON TTHD ON TT.PK_SEQ = TTHD.THUTIEN_FK "+
		 		"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TT.NHANVIEN_FK = NV.PK_SEQ "+ 
		 		"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
		 		"\n		PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') "+  	 
		 		"\n 	) "+ 
		 		"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') "+  
		 		"\n		AND PSKT.NGAYGHINHAN < '" + obj.gettungay() + "' AND TT.TRANGTHAI != 2 " +
				"\n 	AND PSKT.LOAICHUNGTU = N'Thu tiền_Thu hồi tạm ứng' AND TT.CONGTY_FK = "+obj.getErpCongtyId()+
		 		"\n		GROUP BY NV.PK_SEQ, NV.TEN, TT.NGAYCHUNGTU, TT.PK_SEQ, TT.GhiChu "+
				
		 		"\n     UNION ALL "+
		 		
		 		// THU TIỀN - THU KHÁC
		 		
				"\n		SELECT NV.PK_SEQ AS NVID, NV.TEN, TT.NGAYCHUNGTU AS NGAYCT,  CONVERT(VARCHAR, TT.PK_SEQ) AS SOCHUNGTU, isnull(TT.GhiChu,'') GHICHU, "+
				"\n	  	SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO "+ 
				"\n	  	FROM ERP_PHATSINHKETOAN PSKT "+ 
				"\n	  	INNER JOIN ERP_THUTIEN TT ON TT.PK_SEQ = PSKT.SOCHUNGTU "+ 
				"\n	  	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG "+ 
				"\n	  	WHERE "+ 
				"\n	  	(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%' AND congty_fk = "+obj.getErpCongtyId()+" ) OR "+ 
				"\n		  PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%' AND congty_fk = "+obj.getErpCongtyId()+") ) "+ 
				"\n	  	AND "+ 
				"\n	  	(PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') AND TT.TRANGTHAI !=2  "+  
				"\n	 	AND "+ 
				"\n	 	(PSKT.LOAICHUNGTU =  N'Thu khác') "+ 
				"\n	  	AND PSKT.NGAYGHINHAN < '" + obj.gettungay() + "' " +
				"\n	     AND TT.CONGTY_FK = "+obj.getErpCongtyId()+" "+ 
				"\n	     GROUP BY NV.PK_SEQ, NV.TEN, TT.NGAYCHUNGTU, TT.PK_SEQ, isnull(TT.GhiChu,'') "+
		 		
				// PHIẾU CHI || ỦY NHIỆM CHI - TRẢ KHÁC
				"\n     UNION ALL "+
				
				"\n		SELECT NV.PK_SEQ AS NVID, NV.TEN,  TTHD.NGAYGHINHAN AS NGAYCT, CONVERT(VARCHAR, TTHD.PK_SEQ)  AS SOCHUNGTU, isnull(TTHD.GHICHU, '') GHICHU, "+   
				"\n		SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO "+  
				"\n		FROM ERP_PHATSINHKETOAN PSKT "+  
				"\n		INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU "+ 
				"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND PSKT.MADOITUONG = NV.PK_SEQ "+ 
				"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
				"\n			PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')) "+ 
				"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') AND TTHD.TRANGTHAI!=2 "+   
				"\n		AND PSKT.NGAYGHINHAN < '" + obj.gettungay() + "' " +
				"\n		AND (PSKT.LOAICHUNGTU =  N'Trả khác') "+ 
				"\n		AND TTHD.CONGTY_FK ="+obj.getErpCongtyId()+
				"\n		GROUP BY NV.PK_SEQ, NV.TEN, TTHD.NGAYGHINHAN, TTHD.PK_SEQ, isnull(TTHD.GHICHU, '') "+ 

				")A WHERE A.NVID = " + obj.getNhanvienId() + " \n " ;

			System.out.println("Get sql dauky : "+ query);
	    return query;   
	}

	public String getTongTrongky(IStockintransit obj)
	{
		String query;
		query = "\n SELECT SUM(NO) AS NO, SUM(CO) AS CO FROM  " +
				"\n (  " +
				// PHIẾU CHI || ỦY NHIỆM CHI
				"\n		SELECT NV.PK_SEQ AS NVID, NV.TEN,  TU.NGAYTAMUNG AS NGAYCT, CONVERT(VARCHAR, TU.PK_SEQ)  AS SOCHUNGTU, TU.LYDOTAMUNG,  " + 
				"\n		SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO  " +
				"\n		FROM ERP_PHATSINHKETOAN PSKT  " +
				"\n		INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU \n " +
				"\n		INNER JOIN ERP_THANHTOANHOADON_HOADON TTHD_HD ON TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK " +
				"\n		INNER JOIN ERP_TAMUNG TU ON TU.PK_SEQ = TTHD_HD.HOADON_FK " +
				"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TTHD.NHANVIEN_FK = NV.PK_SEQ " +
				"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR " +
				"\n				PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')) " +
				"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  " + 
				"\n		AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' " +
				"\n 	AND (PSKT.LOAICHUNGTU =  N'Thanh toán hóa đơn') AND TTHD.TRANGTHAI != 2 " +
				"\n		AND TTHD.CONGTY_FK = "+obj.getErpCongtyId()+	
				"\n		GROUP BY NV.PK_SEQ, NV.TEN, TU.NGAYTAMUNG, TU.PK_SEQ, TU.LYDOTAMUNG  " +

				"\n		UNION ALL  " +
				
				// THANH TOÁN HÓA ĐƠN
				
				"\n		SELECT NV.PK_SEQ AS NVID,  NV.TEN, MH.NGAYMUA AS NGAYCT, MH.SOPO AS SOCHUNGTU, MH.LYDOTT, " + 
				"\n		SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO  " +
				"\n		FROM ERP_PHATSINHKETOAN PSKT  " +
				"\n		INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = PSKT.SOCHUNGTU  " +
				"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND MH.NHANVIEN_FK = NV.PK_SEQ  " +
				"\n		WHERE MH.TYPE = 1 AND MH.LOAIHANGHOA_FK = '2' AND " +
				"\n		(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR  " +
				"\n		 PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') " +
				"\n		)  " +
				"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') AND MH.TRANGTHAI!=2 " +
				"\n		AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' " +
				"\n 	AND PSKT.LOAICHUNGTU = N'Chi phí khác' AND MH.CONGTY_FK = "+obj.getErpCongtyId()+
				"\n		GROUP BY NV.PK_SEQ,  NV.TEN, MH.NGAYMUA, MH.SOPO, MH.LYDOTT " + 
				
		 		"\n		UNION ALL  " +
		 		
		 		// BÚT TOÁN TỔNG HỢP
		 		
		 		"\n		SELECT BTTH.NHANVIEN_FK AS NVID, BTTH.TEN, BTTH.NGAYBUTTOAN AS NGAYCT, CONVERT(VARCHAR, BTTH.PK_SEQ) AS SOCHUNGTU, BTTH.DIENGIAI, " +
		 		"\n	   	SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO " +
		 		"\n		FROM ERP_PHATSINHKETOAN PSKT  " +
		 		"\n		INNER JOIN "+
		 		"\n		(SELECT distinct BTTH.NGAYBUTTOAN, BTTH.PK_SEQ,  BTTH_CT.NHANVIEN_FK, BTTH_CT.TAIKHOANKT_FK, NV.TEN, BTTH.DIENGIAI "+
		 		"\n		 FROM   ERP_BUTTOANTONGHOP BTTH INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTH_CT ON BTTH_CT.BUTTOANTONGHOP_FK = BTTH.PK_SEQ "+ 
		 		"\n	  			INNER JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = BTTH_CT.NHANVIEN_FK " +
		 		"\n		 WHERE  BTTH.CONGTY_FK = "+obj.getErpCongtyId()+" AND BTTH.TRANGTHAI != 2 "+
		 		"\n		) BTTH ON CAST(BTTH.PK_SEQ AS NVARCHAR(50)) = PSKT.SOCHUNGTU    AND PSKT.MADOITUONG = CAST(BTTH.NHANVIEN_FK AS NVARCHAR(50)) "+ 
		 		"\n		WHERE BTTH.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') AND " +
		 		"\n		(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR " +
		 		"\n			PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')  " + 
		 		"\n		)   " +
		 		"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  " + 
		 		"\n		AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' " +
		 		"\n	 	AND PSKT.LOAICHUNGTU = N'Bút toán tổng hợp'" +
		 		"\n		GROUP BY BTTH.NHANVIEN_FK,  BTTH.TEN, BTTH.NGAYBUTTOAN, BTTH.PK_SEQ, BTTH.DIENGIAI " +
		 		
		 		"\n 	UNION ALL "+	
		 		
		 		// PHIẾU CHI || ỦY NHIỆM CHI
		 		
				"\n 	SELECT NV.PK_SEQ AS NVID, NV.TEN, TU.NGAYMUA AS NGAYCT,  CONVERT(VARCHAR, TTHD.PK_SEQ) AS SOCHUNGTU, TU.LYDOTT, "+  
				"\n  	SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO "+ 
				"\n  	FROM ERP_PHATSINHKETOAN PSKT "+ 
				"\n  	INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU "+ 
				"\n  	INNER JOIN ERP_THANHTOANHOADON_HOADON TTHD_HD ON TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK "+ 
				"\n  	INNER JOIN ERP_MUAHANG TU ON TU.PK_SEQ = TTHD_HD.HOADON_FK "+ 
				"\n  	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TTHD.NHANVIEN_FK = NV.PK_SEQ "+ 
				"\n  	WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
				"\n  	PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') "+ 
				"\n  	) AND TTHD.TRANGTHAI != 2 "+ 
				"\n  	AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  "+
				"\n 	AND (PSKT.LOAICHUNGTU =  N'Thanh toán hóa đơn') "+	
				"\n  	AND PSKT.NGAYGHINHAN >= '"+obj.gettungay()+"' AND PSKT.NGAYGHINHAN <= '"+obj.getdenngay()+"' " +
				"\n 	AND TTHD.CONGTY_FK = "+obj.getErpCongtyId()+
				"\n     GROUP BY NV.PK_SEQ, NV.TEN, TU.NGAYMUA, TTHD.PK_SEQ, TU.LYDOTT "+		  
		 		
				"\n 	UNION ALL "+	
				
				// THU TIỀN - THU HỒI TẠM ỨNG
				
				"\n 	SELECT NV.PK_SEQ AS NVID, NV.TEN, TT.NGAYCHUNGTU AS NGAYCT, CONVERT(VARCHAR, TT.PK_SEQ) AS SOCHUNGTU, "+ 
		 		"\n		TT.GhiChu, SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO "+ 
		 		"\n		FROM ERP_PHATSINHKETOAN PSKT "+ 
		 		"\n		INNER JOIN ERP_THUTIEN TT ON TT.PK_SEQ = CONVERT(VARCHAR, PSKT.SOCHUNGTU ) "+		  	
		 		"\n		INNER JOIN ERP_THUTIEN_HOADON TTHD ON TT.PK_SEQ = TTHD.THUTIEN_FK "+
		 		"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TT.NHANVIEN_FK = NV.PK_SEQ "+ 
		 		"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
		 		"\n		PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') "+  	 
		 		"\n 	) AND TT.TRANGTHAI!=2 "+ 
		 		"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') "+  
				"\n  	AND PSKT.NGAYGHINHAN >= '"+obj.gettungay()+"' AND PSKT.NGAYGHINHAN <= '"+obj.getdenngay()+"' " +
				"\n 	AND PSKT.LOAICHUNGTU = N'Thu tiền_Thu hồi tạm ứng' " +
				"\n     AND TT.CONGTY_FK = "+obj.getErpCongtyId()+
		 		"\n		GROUP BY NV.PK_SEQ, NV.TEN, TT.NGAYCHUNGTU, TT.PK_SEQ, TT.GhiChu "+
				
				"\n     UNION ALL "+
	
				// THU TIỀN - THU KHÁC
				"\n		SELECT NV.PK_SEQ AS NVID, NV.TEN, TT.NGAYCHUNGTU AS NGAYCT,  CONVERT(VARCHAR, TT.PK_SEQ) AS SOCHUNGTU, isnull(TT.GhiChu,'') GHICHU, "+
				"\n	  	SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO "+ 
				"\n	  	FROM ERP_PHATSINHKETOAN PSKT "+ 
				"\n	  	INNER JOIN ERP_THUTIEN TT ON TT.PK_SEQ = PSKT.SOCHUNGTU "+ 
				"\n	  	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG "+ 
				"\n	  	WHERE "+ 
				"\n	  	(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%' AND congty_fk = "+obj.getErpCongtyId()+" ) OR "+ 
				"\n		  PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%' AND congty_fk = "+obj.getErpCongtyId()+") ) "+ 
				"\n	  	AND "+ 
				"\n	  	(PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') AND TT.TRANGTHAI!=2  "+  
				"\n	 	AND "+ 
				"\n	 	(PSKT.LOAICHUNGTU =  N'Thu khác') "+ 
				"\n	  	AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' "+
				"\n	     AND TT.CONGTY_FK = "+obj.getErpCongtyId()+" "+ 
				"\n	     GROUP BY NV.PK_SEQ, NV.TEN, TT.NGAYCHUNGTU, TT.PK_SEQ, isnull(TT.GhiChu,'') "+
				
				//PHIẾU CHI || ỦY NHIỆM CHI - TRẢ KHÁC
				"\n     UNION ALL "+
				
				"\n		SELECT NV.PK_SEQ AS NVID, NV.TEN,  TTHD.NGAYGHINHAN AS NGAYCT, CONVERT(VARCHAR, TTHD.PK_SEQ)  AS SOCHUNGTU, isnull(TTHD.GHICHU, '') GHICHU, "+   
				"\n		SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO "+  
				"\n		FROM ERP_PHATSINHKETOAN PSKT "+  
				"\n		INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU "+ 
				"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND PSKT.MADOITUONG = NV.PK_SEQ "+ 
				"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
				"\n			PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')) "+ 
				"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') AND TTHD.TRANGTHAI!= 2 "+   
				"\n		AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND  PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' "+
				"\n		AND (PSKT.LOAICHUNGTU =  N'Trả khác') "+ 
				"\n		AND TTHD.CONGTY_FK ="+obj.getErpCongtyId()+
				"\n		GROUP BY NV.PK_SEQ, NV.TEN, TTHD.NGAYGHINHAN, TTHD.PK_SEQ, isnull(TTHD.GHICHU, '') "+ 
		 		
				")A WHERE A.NVID = " + obj.getNhanvienId() + " \n " ;

			System.out.println("Get sql trong ky 1: "+ query);
	    return query;   
	}
	
	public String getTrongky(IStockintransit obj)
	{
		String query;
		query = "\n SELECT * FROM " +
				"\n (  " +
				// PHIẾU CHI || ỦY NHIỆM CHI - TẠM ỨNG
				"\n		SELECT NV.PK_SEQ AS NVID, NV.TEN,  TU.NGAYTAMUNG AS NGAYCT, CONVERT(VARCHAR, TU.PK_SEQ)  AS SOCHUNGTU, TU.LYDOTAMUNG,  " + 
				"\n		SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO  " +
				"\n		FROM ERP_PHATSINHKETOAN PSKT  " +
				"\n		INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU \n " +
				"\n		INNER JOIN ERP_THANHTOANHOADON_HOADON TTHD_HD ON TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK " +
				"\n		INNER JOIN ERP_TAMUNG TU ON TU.PK_SEQ = TTHD_HD.HOADON_FK " +
				"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TTHD.NHANVIEN_FK = NV.PK_SEQ " +
				"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR " +
				"\n				PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')) " +
				"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  " + 
				"\n		AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' " +
				"\n 	AND (PSKT.LOAICHUNGTU =  N'Thanh toán hóa đơn') AND TTHD.TRANGTHAI!=2 " +
				"\n     AND TTHD.CONGTY_FK = "+obj.getErpCongtyId()+	
				"\n		GROUP BY NV.PK_SEQ, NV.TEN, TU.NGAYTAMUNG, TU.PK_SEQ, TU.LYDOTAMUNG  " +

				"\n		UNION ALL  " +
				
				// ĐỀ NGHỊ THANH TOÁN
				"\n		SELECT NV.PK_SEQ AS NVID,  NV.TEN, MH.NGAYMUA AS NGAYCT, MH.SOPO AS SOCHUNGTU, MH.LYDOTT, " + 
				"\n		SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO  " +
				"\n		FROM ERP_PHATSINHKETOAN PSKT  " +
				"\n		INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = PSKT.SOCHUNGTU  " +
				"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND MH.NHANVIEN_FK = NV.PK_SEQ  " +
				"\n		WHERE MH.TYPE = 1 AND MH.LOAIHANGHOA_FK = '2' AND " +
				"\n		(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR  " +
				"\n		 PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') " +
				"\n		)  AND MH.TRANGTHAI !=2 " +
				"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  " +
				"\n		AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' " +
				"\n 	AND PSKT.LOAICHUNGTU = N'Chi phí khác' AND MH.CONGTY_FK = "+obj.getErpCongtyId()+
				"\n		GROUP BY NV.PK_SEQ,  NV.TEN, MH.NGAYMUA, MH.SOPO, MH.LYDOTT " + 
				
		 		"\n		UNION ALL  " +
		 		
		 		//BÚT TOÁN TỔNG HỢP
		 		
		 		"\n		SELECT BTTH.NHANVIEN_FK AS NVID, BTTH.TEN, BTTH.NGAYBUTTOAN AS NGAYCT, CONVERT(VARCHAR, BTTH.PK_SEQ) AS SOCHUNGTU, BTTH.DIENGIAI, " +
		 		"\n	   	SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO " +
		 		"\n		FROM ERP_PHATSINHKETOAN PSKT  " +
		 		"\n		INNER JOIN "+
		 		"\n		(SELECT distinct BTTH.NGAYBUTTOAN, BTTH.PK_SEQ,  BTTH_CT.NHANVIEN_FK, BTTH_CT.TAIKHOANKT_FK, NV.TEN, BTTH.DIENGIAI "+
		 		"\n		 FROM   ERP_BUTTOANTONGHOP BTTH INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTH_CT ON BTTH_CT.BUTTOANTONGHOP_FK = BTTH.PK_SEQ "+ 
		 		"\n	  			INNER JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = BTTH_CT.NHANVIEN_FK " +
		 		"\n		 WHERE BTTH.CONGTY_FK = "+obj.getErpCongtyId()+ " AND BTTH.TRANGTHAI!=2 "+
		 		"\n		) BTTH ON CAST(BTTH.PK_SEQ AS NVARCHAR(50)) = PSKT.SOCHUNGTU    AND PSKT.MADOITUONG = CAST(BTTH.NHANVIEN_FK AS NVARCHAR(50)) "+ 
		 		"\n		WHERE BTTH.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') AND " +
		 		"\n		(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR " +
		 		"\n			PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')  " + 
		 		"\n		)  " +
		 		"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  " + 
		 		"\n		AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' " +
		 		"\n	 	AND PSKT.LOAICHUNGTU = N'Bút toán tổng hợp' " +
		 		"\n		GROUP BY BTTH.NHANVIEN_FK,  BTTH.TEN, BTTH.NGAYBUTTOAN, BTTH.PK_SEQ, BTTH.DIENGIAI " +
		 		
		 		"\n 	UNION ALL "+	
		 		
		 		// PHIẾU CHI || ỦY NHIỆM CHI - ĐỀ NGHỊ THANH TOÁN
		 		
				"\n 	SELECT NV.PK_SEQ AS NVID, NV.TEN, TU.NGAYMUA AS NGAYCT,  CONVERT(VARCHAR, TTHD.PK_SEQ) AS SOCHUNGTU, TU.LYDOTT, "+  
				"\n  	SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO "+ 
				"\n  	FROM ERP_PHATSINHKETOAN PSKT "+ 
				"\n  	INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU "+ 
				"\n  	INNER JOIN ERP_THANHTOANHOADON_HOADON TTHD_HD ON TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK "+ 
				"\n  	INNER JOIN ERP_MUAHANG TU ON TU.PK_SEQ = TTHD_HD.HOADON_FK "+ 
				"\n  	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TTHD.NHANVIEN_FK = NV.PK_SEQ "+ 
				"\n  	WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
				"\n  	PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') "+ 
				"\n  	) AND TTHD.TRANGTHAI!=2 "+ 
				"\n  	AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  "+
				"\n 	AND (PSKT.LOAICHUNGTU =  N'Thanh toán hóa đơn') "+	
				"\n  	AND PSKT.NGAYGHINHAN >= '"+obj.gettungay()+"' AND PSKT.NGAYGHINHAN <= '"+obj.getdenngay()+"' " +
				"\n     AND TTHD.CONGTY_FK = "+obj.getErpCongtyId()+
				"\n     GROUP BY NV.PK_SEQ, NV.TEN, TU.NGAYMUA, TTHD.PK_SEQ, TU.LYDOTT "+		  
		 		
				"\n 	UNION ALL "+	
				
				// THU TIỀN - THU HỒI TẠM ỨNG
				
				"\n 	SELECT NV.PK_SEQ AS NVID, NV.TEN, TT.NGAYCHUNGTU AS NGAYCT, CONVERT(VARCHAR, TT.PK_SEQ) AS SOCHUNGTU, "+ 
		 		"\n		TT.GhiChu, SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO "+ 
		 		"\n		FROM ERP_PHATSINHKETOAN PSKT "+ 
		 		"\n		INNER JOIN ERP_THUTIEN TT ON TT.PK_SEQ = CONVERT(VARCHAR, PSKT.SOCHUNGTU ) "+		  	
		 		"\n		INNER JOIN ERP_THUTIEN_HOADON TTHD ON TT.PK_SEQ = TTHD.THUTIEN_FK "+
		 		"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TT.NHANVIEN_FK = NV.PK_SEQ "+ 
		 		"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
		 		"\n		PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') "+  	 
		 		"\n 	) AND TT.TRANGTHAI !=2 "+ 
		 		"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') "+  
				"\n  	AND PSKT.NGAYGHINHAN >= '"+obj.gettungay()+"' AND PSKT.NGAYGHINHAN <= '"+obj.getdenngay()+"' " +
				"\n 	AND PSKT.LOAICHUNGTU = N'Thu tiền_Thu hồi tạm ứng' " +
				"\n     AND TT.CONGTY_FK = "+obj.getErpCongtyId()+
		 		"\n		GROUP BY NV.PK_SEQ, NV.TEN, TT.NGAYCHUNGTU, TT.PK_SEQ, TT.GhiChu "+
				
				"\n     UNION ALL "+

				// THU TIỀN - THU KHÁC
				"\n		SELECT NV.PK_SEQ AS NVID, NV.TEN, TT.NGAYCHUNGTU AS NGAYCT,  CONVERT(VARCHAR, TT.PK_SEQ) AS SOCHUNGTU, isnull(TT.GhiChu,'') GHICHU, "+
				"\n	  	SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO "+ 
				"\n	  	FROM ERP_PHATSINHKETOAN PSKT "+ 
				"\n	  	INNER JOIN ERP_THUTIEN TT ON TT.PK_SEQ = PSKT.SOCHUNGTU "+ 
				"\n	  	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG "+ 
				"\n	  	WHERE "+ 
				"\n	  	(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%' AND congty_fk = "+obj.getErpCongtyId()+" ) OR "+ 
				"\n		  PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%' AND congty_fk = "+obj.getErpCongtyId()+") ) "+ 
				"\n	  	AND "+ 
				"\n	  	(PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') "+  
				"\n	 	AND "+ 
				"\n	 	(PSKT.LOAICHUNGTU =  N'Thu khác') "+ 
				"\n	  	AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' AND TT.TRANGTHAI!=2 "+
				"\n	     AND TT.CONGTY_FK = "+obj.getErpCongtyId()+" "+ 
				"\n	     GROUP BY NV.PK_SEQ, NV.TEN, TT.NGAYCHUNGTU, TT.PK_SEQ, isnull(TT.GhiChu,'') "+
				
				//PHIẾU CHI || ỦY NHIỆM CHI - TRẢ KHÁC
				"\n     UNION ALL "+
				
				"\n		SELECT NV.PK_SEQ AS NVID, NV.TEN,  TTHD.NGAYGHINHAN AS NGAYCT, CONVERT(VARCHAR, TTHD.PK_SEQ)  AS SOCHUNGTU, isnull(TTHD.GHICHU, '') GHICHU, "+   
				"\n		SUM(ISNULL(PSKT.NO, 0)) AS NO, SUM(ISNULL(PSKT.CO, 0)) AS CO "+  
				"\n		FROM ERP_PHATSINHKETOAN PSKT "+  
				"\n		INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU "+ 
				"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND PSKT.MADOITUONG = NV.PK_SEQ "+ 
				"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
				"\n			PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')) "+ 
				"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') "+   
				"\n		AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' AND TTHD.TRANGTHAI!=2 "+
				"\n		AND (PSKT.LOAICHUNGTU =  N'Trả khác') "+ 
				"\n		AND TTHD.CONGTY_FK ="+obj.getErpCongtyId()+
				"\n		GROUP BY NV.PK_SEQ, NV.TEN, TTHD.NGAYGHINHAN, TTHD.PK_SEQ, isnull(TTHD.GHICHU, '') "+ 

				")A WHERE A.NVID = " + obj.getNhanvienId() + " \n " ;

			System.out.println("Get sql trong ky : "+ query);
	    return query;   
	}

	public String getTrongky_Tongthe(IStockintransit obj)
	{
		String query;
		query = "\n SELECT A.MA, A.TEN, SUM(NO_DK) AS NO_DK, SUM(CO_DK) AS CO_DK, " +
				"\n SUM(NO_TK) AS NO_TK, SUM(CO_TK) AS CO_TK,  " +
				"\n SUM(NO_CK) AS NO_CK, SUM(CO_CK) AS CO_CK " +
				"\n FROM  " +
				"\n ( " +
				
				// ỦY NHIỆM CHI || PHIẾU CHI
				"\n SELECT NV.MA, NV.TEN, " + 
				"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_DK, SUM(ISNULL(PSKT.CO, 0)) AS CO_DK, " + 
				"\n 0 AS NO_TK, 0 AS CO_TK,  " +
				"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK " +
				"\n	FROM ERP_PHATSINHKETOAN PSKT  " +
				"\n	INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU \n " +
				"\n	INNER JOIN ERP_THANHTOANHOADON_HOADON TTHD_HD ON TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK " +
				"\n	INNER JOIN ERP_TAMUNG TU ON TU.PK_SEQ = TTHD_HD.HOADON_FK " +
				"\n	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TTHD.NHANVIEN_FK = NV.PK_SEQ " +
				"\n	WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR " +
				"\n				PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')) " +
				"\n	AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  " + 
				"\n	AND PSKT.NGAYGHINHAN < '" + obj.gettungay() + "'  " +
				"\n AND (PSKT.LOAICHUNGTU =  N'Thanh toán hóa đơn') AND TTHD.CONGTY_FK =  "+obj.getErpCongtyId()+ " AND TTHD.TRANGTHAI != 2 "+	
				"\n GROUP BY NV.MA, NV.TEN  " +

				// ĐỀ NGHỊ THANH TOÁN
				"\n UNION ALL  " +
				"\n SELECT NV.MA, NV.TEN,  " + 
				"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_DK, SUM(ISNULL(PSKT.CO, 0)) AS CO_DK, " + 
				"\n 0 AS NO_TK, 0 AS CO_TK, " +
				"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK " +
				"\n	FROM ERP_PHATSINHKETOAN PSKT  " +
				"\n	INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = PSKT.SOCHUNGTU  " +
				"\n	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND MH.NHANVIEN_FK = NV.PK_SEQ  " +
				"\n	WHERE MH.TYPE = 1 AND MH.LOAIHANGHOA_FK = '2' AND " +
				"\n	(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR  " +
				"\n	 PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') " +
				"\n	) AND MH.TRANGTHAI != 2  " +
				"\n	AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') AND MH.CONGTY_FK = " +obj.getErpCongtyId()+
				"\n	AND PSKT.NGAYGHINHAN < '" + obj.getdenngay() + "' " +
				"\n AND PSKT.LOAICHUNGTU = N'Chi phí khác' "+
				"\n GROUP BY NV.MA, NV.TEN  \n " +
				
				//BÚT TOÁN TỔNG HỢP
				"\n UNION ALL " +
		 		"\n SELECT BTTH.MA, BTTH.TEN,  " +
		 		"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_DK, SUM(ISNULL(PSKT.CO, 0)) AS CO_DK, " + 
				"\n 0 AS NO_TK, 0 AS CO_TK, " +
				"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK " +
				
				"\n	FROM ERP_PHATSINHKETOAN PSKT  " +
		 		"\n	INNER JOIN "+
		 		"\n	(SELECT distinct BTTH.NGAYBUTTOAN, BTTH.PK_SEQ,  BTTH_CT.NHANVIEN_FK, BTTH_CT.TAIKHOANKT_FK, NV.TEN, BTTH.DIENGIAI, NV.MA "+
		 		"\n	 FROM   ERP_BUTTOANTONGHOP BTTH INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTH_CT ON BTTH_CT.BUTTOANTONGHOP_FK = BTTH.PK_SEQ "+ 
		 		"\n	  			INNER JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = BTTH_CT.NHANVIEN_FK "+
		 		"\n  WHERE BTTH.CONGTY_FK = "+obj.getErpCongtyId()+ " AND BTTH.TRANGTHAI != 2 "+
		 		"\n	 ) BTTH ON CAST(BTTH.PK_SEQ AS NVARCHAR(50)) = PSKT.SOCHUNGTU    AND PSKT.MADOITUONG = CAST(BTTH.NHANVIEN_FK AS NVARCHAR(50)) "+ 
		 		"\n	 WHERE BTTH.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') AND " +
		 		"\n	(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR " +
		 		"\n	 PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')  " + 
		 		"\n	 )  " +
		 		"\n	AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  " + 
		 		"\n	AND PSKT.NGAYGHINHAN < '" + obj.getdenngay() + "' " +
		 		"\n	 AND PSKT.LOAICHUNGTU = N'Bút toán tổng hợp' " +
		 		"\n GROUP BY BTTH.MA,  BTTH.TEN  " +
		 		
				"\n UNION ALL  " +
				
				//PHIẾU CHI || ỦY NHIỆM CHI
				"\n SELECT NV.MA, NV.TEN,  " + 
				"\n 0 AS NO_DK, 0 AS CO_DK,  " +
				"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_TK, SUM(ISNULL(PSKT.CO, 0)) AS CO_TK,  " + 
	   
				"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK " +
				"\n		FROM ERP_PHATSINHKETOAN PSKT  " +
				"\n		INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU " +
				"\n		INNER JOIN ERP_THANHTOANHOADON_HOADON TTHD_HD ON TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK " +
				"\n		INNER JOIN ERP_TAMUNG TU ON TU.PK_SEQ = TTHD_HD.HOADON_FK " +
				"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TTHD.NHANVIEN_FK = NV.PK_SEQ " +
				"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR " +
				"\n				PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')) " +
				"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') AND TTHD.CONGTY_FK = " +obj.getErpCongtyId()+ 
				"\n		AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' " +
				"\n 	AND (PSKT.LOAICHUNGTU =  N'Thanh toán hóa đơn') AND TTHD.TRANGTHAI != 2 "+	
				"\n GROUP BY NV.MA, NV.TEN  " +

				//  ĐỀ NGHỊ THANH TOÁN
				"\n UNION ALL   " +
				"\n SELECT NV.MA, NV.TEN,    " +
				"\n 0 AS NO_DK, 0 AS CO_DK,    " +
				"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_TK, SUM(ISNULL(PSKT.CO, 0)) AS CO_TK,   " +
				"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK  " +
	   
				"\n	FROM ERP_PHATSINHKETOAN PSKT  " +
				"\n	INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = PSKT.SOCHUNGTU  " +
				"\n	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND MH.NHANVIEN_FK = NV.PK_SEQ  " +
				"\n	WHERE MH.TYPE = 1 AND MH.LOAIHANGHOA_FK = '2' AND " +
				"\n	(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR  " +
				"\n	 PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') " +
				"\n	)  " +
				"\n	AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') AND MH.CONGTY_FK = " +obj.getErpCongtyId()+
				"\n	AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' " +
				"\n AND PSKT.LOAICHUNGTU = N'Chi phí khác' AND MH.TRANGTHAI != 2 "+
				"\n GROUP BY NV.MA, NV.TEN  " +
				
				"\n UNION ALL  " +
				
		 		"\n SELECT BTTH.MA, BTTH.TEN,  " +
		 		"\n 0 AS NO_DK, 0 AS CO_DK,   " +
				"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_TK, SUM(ISNULL(PSKT.CO, 0)) AS CO_TK,   " +
				"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK  " +
				
				"\n		FROM ERP_PHATSINHKETOAN PSKT  " +
		 		"\n		INNER JOIN "+
		 		"\n		(SELECT distinct BTTH.NGAYBUTTOAN, BTTH.PK_SEQ,  BTTH_CT.NHANVIEN_FK, BTTH_CT.TAIKHOANKT_FK, NV.TEN, BTTH.DIENGIAI, NV.MA "+
		 		"\n		 FROM   ERP_BUTTOANTONGHOP BTTH INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTH_CT ON BTTH_CT.BUTTOANTONGHOP_FK = BTTH.PK_SEQ "+ 
		 		"\n	  			INNER JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = BTTH_CT.NHANVIEN_FK " +
		 		"\n		 WHERE BTTH.CONGTY_FK = "+obj.getErpCongtyId()+ " AND BTTH.TRANGTHAI != 2"+
		 		"\n		) BTTH ON CAST(BTTH.PK_SEQ AS NVARCHAR(50)) = PSKT.SOCHUNGTU    AND PSKT.MADOITUONG = CAST(BTTH.NHANVIEN_FK AS NVARCHAR(50)) "+ 
		 		"\n		WHERE BTTH.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') AND " +
		 		"\n		(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR " +
		 		"\n			PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')  " + 
		 		"\n		)  " +
		 		"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  " + 
		 		"\n		AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' " +
		 		"\n	 	AND PSKT.LOAICHUNGTU = N'Bút toán tổng hợp' " +
		 		"GROUP BY BTTH.MA,  BTTH.TEN  \n " +
				
		 		"\n UNION ALL "+
		 		 
		 		// PHIẾU CHI || ỦY NHIỆM CHI
		 		"\n SELECT NV.MA, NV.TEN, "+   
		 		"\n 0 AS NO_DK, 0 AS CO_DK, "+   
		 		"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_TK, SUM(ISNULL(PSKT.CO, 0)) AS CO_TK, "+   
		 		"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK "+  
		 		"\n FROM ERP_PHATSINHKETOAN PSKT "+  
				"\n  	INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU "+ 
				"\n  	INNER JOIN ERP_THANHTOANHOADON_HOADON TTHD_HD ON TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK "+ 
				"\n  	INNER JOIN ERP_MUAHANG TU ON TU.PK_SEQ = TTHD_HD.HOADON_FK "+ 
				"\n  	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TTHD.NHANVIEN_FK = NV.PK_SEQ "+ 
				"\n  	WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
				"\n  	PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') "+ 
				"\n  	) "+ 
				"\n  	AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') AND TTHD.CONGTY_FK =   "+obj.getErpCongtyId()+
				"\n 	AND (PSKT.LOAICHUNGTU =  N'Thanh toán hóa đơn') AND TTHD.TRANGTHAI!= 2 "+	
				"\n  	AND PSKT.NGAYGHINHAN >= '"+obj.gettungay()+"' AND PSKT.NGAYGHINHAN <= '"+obj.getdenngay()+"' "+
		 		"\n GROUP BY NV.MA, NV.TEN "+  

		 		"\n UNION ALL "+
		 		 
		 		// PHIẾU CHI || ỦY NHIỆM CHI
		 		"\n SELECT NV.MA, NV.TEN, "+   
		 		"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_DK, SUM(ISNULL(PSKT.CO, 0)) AS CO_DK, "+ 
		 		"\n 0 AS NO_TK, 0 AS CO_TK, "+ 
		 		"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK "+ 
		 		"\n  	FROM ERP_PHATSINHKETOAN PSKT "+ 
				"\n  	INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU "+ 
				"\n  	INNER JOIN ERP_THANHTOANHOADON_HOADON TTHD_HD ON TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK "+ 
				"\n  	INNER JOIN ERP_MUAHANG TU ON TU.PK_SEQ = TTHD_HD.HOADON_FK "+ 
				"\n  	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TTHD.NHANVIEN_FK = NV.PK_SEQ "+ 
				"\n  	WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
				"\n  	PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') "+ 
				"\n  	) AND TTHD.TRANGTHAI != 2 "+ 
				"\n  	AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên')  "+
				"\n 	AND (PSKT.LOAICHUNGTU =  N'Thanh toán hóa đơn') AND TTHD.CONGTY_FK = "+obj.getErpCongtyId()+	
				"\n  AND PSKT.NGAYGHINHAN < '"+obj.getdenngay()+"' "+
		 		"\n GROUP BY NV.MA, NV.TEN "+		 		 
		 		 
		 		"\n  UNION ALL "+
		 		 
		 		// THU TIỀN - THU HỒI TẠM ỨNG
		 		"\n SELECT NV.MA, NV.TEN, "+   
		 		"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_DK, SUM(ISNULL(PSKT.CO, 0)) AS CO_DK, "+ 
		 		"\n 0 AS NO_TK, 0 AS CO_TK, "+ 
		 		"\n SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK "+ 
		 		"\n		FROM ERP_PHATSINHKETOAN PSKT "+ 
		 		"\n		INNER JOIN ERP_THUTIEN TT ON TT.PK_SEQ = CONVERT(VARCHAR, PSKT.SOCHUNGTU ) "+		  	
		 		"\n		INNER JOIN ERP_THUTIEN_HOADON TTHD ON TT.PK_SEQ = TTHD.THUTIEN_FK "+
		 		"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TT.NHANVIEN_FK = NV.PK_SEQ "+ 
		 		"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
		 		"\n		PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')  "+  	 
		 		"\n 	) AND TT.TRANGTHAI != 2 "+ 
		 		"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') " +
		 		"\n     AND TT.CONGTY_FK = "+obj.getErpCongtyId()+  
				"\n  	AND PSKT.NGAYGHINHAN <'"+obj.getdenngay()+"' " +
				"\n 	AND PSKT.LOAICHUNGTU = N'Thu tiền_Thu hồi tạm ứng' "+
		 		"\n GROUP BY NV.MA, NV.TEN "+
		 		 
		 		"\n UNION ALL "+ 
		 		 
		 		//THU TIỀN - THU HỒI TẠM ỨNG
		 		"\n SELECT 	NV.MA, NV.TEN, "+   
		 		"\n 		0 AS NO_DK, 0 AS CO_DK, "+   
		 		"\n 		SUM(ISNULL(PSKT.NO, 0)) AS NO_TK, SUM(ISNULL(PSKT.CO, 0)) AS CO_TK, "+   
		 		"\n 		SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK "+  
		 		"\n		FROM ERP_PHATSINHKETOAN PSKT "+ 
		 		"\n		INNER JOIN ERP_THUTIEN TT ON TT.PK_SEQ = CONVERT(VARCHAR, PSKT.SOCHUNGTU ) "+		  	
		 		"\n		INNER JOIN ERP_THUTIEN_HOADON TTHD ON TT.PK_SEQ = TTHD.THUTIEN_FK "+
		 		"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND TT.NHANVIEN_FK = NV.PK_SEQ "+ 
		 		"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
		 		"\n		PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')) "+  	 
		 		"\n 	AND TT.CONGTY_FK = "+obj.getErpCongtyId()+ 
		 		"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') "+  
				"\n  	AND PSKT.NGAYGHINHAN >= '"+obj.gettungay()+"' AND PSKT.NGAYGHINHAN <= '"+obj.getdenngay()+"' " +
				"\n 	AND PSKT.LOAICHUNGTU = N'Thu tiền_Thu hồi tạm ứng' AND TT.TRANGTHAI !=2 "+
		 		"\n GROUP BY NV.MA, NV.TEN "+
				
				"\n     UNION ALL "+
				
				// THU TIỀN  - THU KHÁC
				"\n		SELECT NV.MA  NV.TEN, "+
				"\n		SUM(ISNULL(PSKT.NO, 0)) AS NO_DK, SUM(ISNULL(PSKT.CO, 0)) AS CO_DK, "+ 
		 		"\n 	0 AS NO_TK, 0 AS CO_TK, "+ 
		 		"\n 	SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK "+
				"\n	  	FROM ERP_PHATSINHKETOAN PSKT "+ 
				"\n	  	INNER JOIN ERP_THUTIEN TT ON TT.PK_SEQ = PSKT.SOCHUNGTU "+ 
				"\n	  	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG "+ 
				"\n	  	WHERE "+ 
				"\n	  	(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%' AND congty_fk = "+obj.getErpCongtyId()+" ) OR "+ 
				"\n		  PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%' AND congty_fk = "+obj.getErpCongtyId()+") ) "+ 
				"\n	  	AND "+ 
				"\n	  	(PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') "+  
				"\n	 	AND "+ 
				"\n	 	(PSKT.LOAICHUNGTU =  N'Thu khác') "+ 
				"\n	  	AND PSKT.NGAYGHINHAN < '"+obj.getdenngay()+"' "+
				"\n	     AND TT.CONGTY_FK = "+obj.getErpCongtyId()+" AND TT.TRANGTHAI != 2 "+ 
				"\n	     GROUP BY NV.MA  NV.TEN "+
				
		 		 
				"\n     UNION ALL "+
				
				// THU TIỀN - THU KHÁC
				
				"\n SELECT 	NV.MA, NV.TEN, "+   
				"\n 		0 AS NO_DK, 0 AS CO_DK, "+   
				"\n 		SUM(ISNULL(PSKT.NO, 0)) AS NO_TK, SUM(ISNULL(PSKT.CO, 0)) AS CO_TK, "+   
				"\n 		SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK "+  
				"\n	  	FROM ERP_PHATSINHKETOAN PSKT "+ 
				"\n	  	INNER JOIN ERP_THUTIEN TT ON TT.PK_SEQ = PSKT.SOCHUNGTU "+ 
				"\n	  	INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG "+ 
				"\n	  	WHERE "+ 
				"\n	  	(PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%' AND congty_fk = "+obj.getErpCongtyId()+" ) OR "+ 
				"\n		  PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%' AND congty_fk = "+obj.getErpCongtyId()+") ) "+ 
				"\n	  	AND "+ 
				"\n	  	(PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') "+  
				"\n	 	AND "+ 
				"\n	 	(PSKT.LOAICHUNGTU =  N'Thu khác') "+ 
				"\n	  	AND PSKT.NGAYGHINHAN >= '"+obj.gettungay()+"' AND PSKT.NGAYGHINHAN <= '"+obj.getdenngay()+"' AND TT.TRANGTHAI != 2 " +
				"\n	     AND TT.CONGTY_FK = "+obj.getErpCongtyId()+" "+ 
				"\n	     GROUP BY NV.MA  NV.TEN "+
				
				
				//PHIẾU CHI || ỦY NHIỆM CHI - TRẢ KHÁC
				"\n     UNION ALL "+
				
				"\n		SELECT NV.PK_SEQ AS NVID, NV.TEN, "+
				"\n 		0 AS NO_DK, 0 AS CO_DK, "+   
				"\n 		SUM(ISNULL(PSKT.NO, 0)) AS NO_TK, SUM(ISNULL(PSKT.CO, 0)) AS CO_TK, "+   
				"\n 		SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK "+  
				"\n		FROM ERP_PHATSINHKETOAN PSKT "+  
				"\n		INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU  "+ 
				"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND PSKT.MADOITUONG = NV.PK_SEQ "+ 
				"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
				"\n			PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')) "+ 
				"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') "+   
				"\n		AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND  PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' "+
				"\n		AND (PSKT.LOAICHUNGTU =  N'Trả khác')  AND TTHD.TRANGTHAI!=2 "+ 
				"\n		AND TTHD.CONGTY_FK ="+obj.getErpCongtyId()+
				"\n		GROUP BY NV.PK_SEQ, NV.TEN, TTHD.NGAYGHINHAN, TTHD.PK_SEQ, isnull(TTHD.GHICHU, '') "+
				
				//PHIẾU CHI || ỦY NHIỆM CHI - TRẢ KHÁC
				"\n     UNION ALL "+
				
				"\n		SELECT NV.MA  NV.TEN, "+
				"\n		SUM(ISNULL(PSKT.NO, 0)) AS NO_DK, SUM(ISNULL(PSKT.CO, 0)) AS CO_DK, "+ 
				"\n 	0 AS NO_TK, 0 AS CO_TK, "+ 
				"\n 	SUM(ISNULL(PSKT.NO, 0)) AS NO_CK, SUM(ISNULL(PSKT.CO, 0)) AS CO_CK "+ 
				"\n		FROM ERP_PHATSINHKETOAN PSKT "+  
				"\n		INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = PSKT.SOCHUNGTU  "+ 
				"\n		INNER JOIN ERP_NHANVIEN NV ON CONVERT(VARCHAR, NV.PK_SEQ) = PSKT.MADOITUONG AND PSKT.MADOITUONG = NV.PK_SEQ "+ 
				"\n		WHERE (PSKT.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%') OR "+ 
				"\n			PSKT.TAIKHOANDOIUNG_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '141%')) "+ 
				"\n		AND (PSKT.DOITUONG = N'NHÂN VIÊN' OR PSKT.DOITUONG = N'Nhân viên') "+   
				"\n		AND PSKT.NGAYGHINHAN >= '" + obj.gettungay() + "' AND PSKT.NGAYGHINHAN <= '" + obj.getdenngay() + "' "+
				"\n		AND (PSKT.LOAICHUNGTU =  N'Trả khác') AND TTHD.TRANGTHAI!=2 "+ 
				"\n		AND TTHD.CONGTY_FK ="+obj.getErpCongtyId()+
				"\n		GROUP BY NV.PK_SEQ, NV.TEN, TTHD.NGAYGHINHAN, TTHD.PK_SEQ, isnull(TTHD.GHICHU, '') "+ 

				")A   \n " +
				"GROUP BY A.MA, A.TEN\n " ;

			System.out.println("Get sql trong ky : "+ query);
	    return query;   
	}

	private void CreateTheodoitamung_chitiet(Document document, ServletOutputStream outstream, IStockintransit  obj) throws IOException
	{
		dbutils db = new dbutils();
		try{
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			double no = 0;
			double co = 0;
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			Paragraph p = new Paragraph("CTY CỔ PHẦN DƯỢC PHANAM", new Font(bf, 10, Font.BOLD));
			p.setSpacingAfter(2);
			p.setSpacingBefore(-25);
			p.setAlignment(Element.ALIGN_LEFT);
			document.add(p);

			p = new Paragraph("CHI TIẾT CÔNG NỢ", new Font(bf, 15, Font.BOLD));
			p.setSpacingAfter(3);
			p.setSpacingBefore(10);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);

			p = new Paragraph("Từ ngày " + obj.gettungay() + " đến ngày " + obj.getdenngay(), new Font(bf, 10, Font.BOLD));
			p.setSpacingAfter(3);
			p.setSpacingBefore(10);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			
			float[] w = { 10.0f, 20.0f};
			PdfPTable nv = new PdfPTable(w.length);
			nv.setWidthPercentage(70);
			nv.setHorizontalAlignment(Element.ALIGN_CENTER);
			nv.setSpacingBefore(25);
			nv.setWidths(w);

			PdfPCell cells ;			
			cells = new PdfPCell(new Paragraph("Tài khoản:", new Font(bf, 10, Font.BOLD )));
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			nv.addCell(cells);

			cells = new PdfPCell(new Paragraph("14100000 - Tạm ứng cho CBNV - VNĐ", new Font(bf, 10, Font.BOLD )));
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			nv.addCell(cells);
			
			
			ResultSet rs = db.get("SELECT MA + ' - ' + TEN as NV FROM ERP_NHANVIEN WHERE PK_SEQ = '" + obj.getNhanvienId() + "' ");
			
			rs.next();
			
			cells = new PdfPCell(new Paragraph("Nhân viên:", new Font(bf, 10, Font.BOLD )));
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			nv.addCell(cells);

			cells = new PdfPCell(new Paragraph(rs.getString("NV"), new Font(bf, 10, Font.BOLD )));
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			nv.addCell(cells);
			
			rs.close();
			
			p.setSpacingAfter(3);
			p.setSpacingBefore(10);
			p.setAlignment(Element.ALIGN_LEFT);
			document.add(nv);

			p = new Paragraph("", new Font(bf, 10, Font.BOLD));
			p.setAlignment(Element.ALIGN_RIGHT);
			p.setSpacingAfter(5);
			
			float[] rong = { 10.0f, 10.0f, 10.0f };
			PdfPTable data = new PdfPTable(rong.length);
			data.setWidthPercentage(50);
			data.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			data.setHorizontalAlignment(1);
			data.setSpacingBefore(25);
			data.setWidths(rong);
			
			
			cells = new PdfPCell(new Paragraph("", new Font(bf, 9, Font.NORMAL)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(0);	
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			data.addCell(cells);
			
			cells = new PdfPCell(new Paragraph("Nợ", new Font(bf, 9, Font.BOLD + Font.ITALIC)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			data.addCell(cells);
			
			cells = new PdfPCell(new Paragraph("Có", new Font(bf, 9, Font.BOLD + Font.ITALIC)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			data.addCell(cells);
					
			rs = db.get(this.getDauky(obj));
			rs.next();
			
			cells = new PdfPCell(new Paragraph("Đầu kỳ", new Font(bf, 9, Font.BOLD + Font.ITALIC)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			data.addCell(cells);
			
			no += rs.getDouble("NO");
			co += rs.getDouble("CO");
			
			double noco = no - co;
			
			if(noco>0)
			{
				cells = new PdfPCell(new Paragraph(formatter.format(noco), new Font(bf, 9, Font.BOLD + Font.ITALIC)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(0);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				data.addCell(cells);
				
				
				cells = new PdfPCell(new Paragraph("", new Font(bf, 9, Font.BOLD + Font.ITALIC)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(0);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				data.addCell(cells);
			}
			else
			{
				cells = new PdfPCell(new Paragraph("", new Font(bf, 9, Font.BOLD + Font.ITALIC)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(0);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				data.addCell(cells);
				
				
				cells = new PdfPCell(new Paragraph(formatter.format((-1)*noco), new Font(bf, 9, Font.BOLD + Font.ITALIC)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(0);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				data.addCell(cells);
			}
						
			rs = db.get(this.getTongTrongky(obj));
			rs.next();
			
			cells = new PdfPCell(new Paragraph("Trong kỳ", new Font(bf, 9, Font.BOLD + Font.ITALIC)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			data.addCell(cells);
			
			no += rs.getDouble("NO");
			cells = new PdfPCell(new Paragraph(formatter.format(rs.getDouble("NO")), new Font(bf, 9, Font.BOLD + Font.ITALIC)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			data.addCell(cells);
			
			co += rs.getDouble("CO");
			cells = new PdfPCell(new Paragraph(formatter.format(rs.getDouble("CO")), new Font(bf, 9, Font.BOLD + Font.ITALIC)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			data.addCell(cells);
			
			rs.close();
			
			cells = new PdfPCell(new Paragraph("Cuối kỳ", new Font(bf, 9, Font.BOLD + Font.ITALIC)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			data.addCell(cells);
			
			noco = no - co;
			if(noco > 0){
			
				cells = new PdfPCell(new Paragraph(formatter.format(noco), new Font(bf, 9, Font.BOLD + Font.ITALIC)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(0);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				data.addCell(cells);
			
				cells = new PdfPCell(new Paragraph("", new Font(bf, 9, Font.BOLD + Font.ITALIC)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(0);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				data.addCell(cells);
			}else{
				cells = new PdfPCell(new Paragraph("", new Font(bf, 9, Font.BOLD + Font.ITALIC)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(0);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				data.addCell(cells);
			
				cells = new PdfPCell(new Paragraph(formatter.format((-1)*noco), new Font(bf, 9, Font.BOLD + Font.ITALIC)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(0);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				data.addCell(cells);				
			}
			p.add(data);
			document.add(p);
			
			
			float[] width = { 10.0f, 10.0f, 30.0f, 15.0f, 15f };
			PdfPTable trongky = new PdfPTable(width.length);
			trongky.setWidthPercentage(100);
			trongky.setHorizontalAlignment(Element.ALIGN_CENTER);
			//trongky.setHorizontalAlignment(1);
			trongky.setSpacingBefore(25);
			trongky.setWidths(width);
						
			cells = new PdfPCell(new Paragraph("Ngày CT", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);	
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);
			
			cells = new PdfPCell(new Paragraph("Số CT", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);
			
			cells = new PdfPCell(new Paragraph("Diễn giải", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);

			cells = new PdfPCell(new Paragraph("TK Nợ", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);
			
			cells = new PdfPCell(new Paragraph("TK Có", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);
			
			
			rs = db.get(this.getTrongky(obj));
			while(rs.next()){
				cells = new PdfPCell(new Paragraph(rs.getString("NGAYCT"), new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setBorder(com.lowagie.text.Cell.BOX);	
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(rs.getString("SOCHUNGTU"), new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(rs.getString("LYDOTAMUNG"), new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);

				cells = new PdfPCell(new Paragraph(formatter.format(rs.getDouble("NO")), new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(formatter.format(rs.getDouble("CO")), new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
				
				
			}
			document.add(trongky);
			document.close();
			}catch(java.sql.SQLException e){}
			
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
		
	}

	private void CreateTheodoitamung_tongthe(Document document, ServletOutputStream outstream, IStockintransit  obj) throws IOException
	{
		dbutils db = new dbutils();
		try{
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			double no = 0;
			double co = 0;
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			Paragraph p = new Paragraph("CÔNG TY CỔ PHẦN ĐỒ HỘP HẠ LONG", new Font(bf, 10, Font.BOLD));
			p.setSpacingAfter(2);
			p.setSpacingBefore(-25);
			p.setAlignment(Element.ALIGN_LEFT);
			document.add(p);

			p = new Paragraph("CHI TIẾT CÔNG NỢ", new Font(bf, 15, Font.BOLD));
			p.setSpacingAfter(3);
			p.setSpacingBefore(10);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);

			p = new Paragraph("Từ ngày " + obj.gettungay() + " đến ngày " + obj.getdenngay(), new Font(bf, 10, Font.BOLD));
			p.setSpacingAfter(3);
			p.setSpacingBefore(10);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			
			float[] w = { 10.0f, 20.0f};
			PdfPTable nv = new PdfPTable(w.length);
			nv.setWidthPercentage(40);
			nv.setHorizontalAlignment(Element.ALIGN_CENTER);
			nv.setSpacingBefore(25);
			nv.setWidths(w);

			PdfPCell cells ;			
			cells = new PdfPCell(new Paragraph("Tài khoản:", new Font(bf, 10, Font.BOLD )));
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			nv.addCell(cells);

			cells = new PdfPCell(new Paragraph("14100000 - Tạm ứng cho CBNV - VNĐ", new Font(bf, 10, Font.BOLD )));
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setBorder(0);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			nv.addCell(cells);
					
			p.setSpacingAfter(3);
			p.setSpacingBefore(10);
			p.setAlignment(Element.ALIGN_LEFT);
			document.add(nv);

			p = new Paragraph("", new Font(bf, 10, Font.BOLD));
			p.setAlignment(Element.ALIGN_RIGHT);
			p.setSpacingAfter(5);
					
			float[] width = { 5.0f, 10.0f, 17.0f, 12.0f, 12.0f, 12.0f, 12.0f, 12.0f, 12.0f };
			PdfPTable trongky = new PdfPTable(width.length);
			trongky.setWidthPercentage(100);
			trongky.setHorizontalAlignment(Element.ALIGN_CENTER);
			//trongky.setHorizontalAlignment(1);
			trongky.setSpacingBefore(25);
			trongky.setWidths(width);
						
			cells = new PdfPCell(new Paragraph("STT", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);	
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			cells.setRowspan(2);
			trongky.addCell(cells);
			
			cells = new PdfPCell(new Paragraph("MÃ", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			cells.setRowspan(2);
			trongky.addCell(cells);
			
			cells = new PdfPCell(new Paragraph("TÊN ĐỐI TƯỢNG", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			cells.setRowspan(2);
			trongky.addCell(cells);

			cells = new PdfPCell(new Paragraph("SỐ DƯ ĐẦU", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			cells.setColspan(2);
			trongky.addCell(cells);
			
			cells = new PdfPCell(new Paragraph("PHÁT SINH TRONG KỲ", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			cells.setColspan(2);
			trongky.addCell(cells);
			
			cells = new PdfPCell(new Paragraph("SỐ DƯ CUỐI KỲ", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			cells.setColspan(2);
			trongky.addCell(cells);
			
			cells = new PdfPCell(new Paragraph("NỢ", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);
			
			cells = new PdfPCell(new Paragraph("CÓ", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);

			cells = new PdfPCell(new Paragraph("NỢ", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);

			cells = new PdfPCell(new Paragraph("CÓ", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);

			cells = new PdfPCell(new Paragraph("NỢ", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);

			cells = new PdfPCell(new Paragraph("CÓ", new Font(bf, 10, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);

			ResultSet rs = db.get(this.getTrongky_Tongthe(obj));
			int i = 0;
			double no_dk = 0;
			double co_dk = 0;
			
			double no_tk = 0;
			double co_tk = 0;
			
			double no_ck = 0;
			double co_ck = 0;
			
			while(rs.next()){
				cells = new PdfPCell(new Paragraph("" + i , new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				cells.setBorder(com.lowagie.text.Cell.BOX);	
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(rs.getString("MA"), new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
				
				cells = new PdfPCell(new Paragraph(rs.getString("TEN"), new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_LEFT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);

				no_dk += rs.getDouble("NO_DK");
				cells = new PdfPCell(new Paragraph(formatter.format(rs.getDouble("NO_DK")), new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
				
				co_dk += rs.getDouble("CO_DK");
				cells = new PdfPCell(new Paragraph(formatter.format(rs.getDouble("CO_DK")), new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
				
				no_tk += rs.getDouble("NO_TK");
				cells = new PdfPCell(new Paragraph(formatter.format(rs.getDouble("NO_TK")), new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
				
				co_tk += rs.getDouble("CO_TK");
				cells = new PdfPCell(new Paragraph(formatter.format(rs.getDouble("CO_TK")), new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
				
				no_ck += rs.getDouble("NO_CK");
				cells = new PdfPCell(new Paragraph(formatter.format(rs.getDouble("NO_CK")), new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
				
				co_ck += rs.getDouble("CO_CK");
				cells = new PdfPCell(new Paragraph(formatter.format(rs.getDouble("CO_CK")), new Font(bf, 9, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
				i++;
			}

			cells = new PdfPCell(new Paragraph("TỔNG", new Font(bf, 11, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			cells.setColspan(3);
			cells.setRowspan(2);
			trongky.addCell(cells);

			cells = new PdfPCell(new Paragraph(formatter.format(no_dk), new Font(bf, 11, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);

			cells = new PdfPCell(new Paragraph(formatter.format(co_dk), new Font(bf, 11, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);
			
			cells = new PdfPCell(new Paragraph(formatter.format(no_tk), new Font(bf, 11, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);

			cells = new PdfPCell(new Paragraph(formatter.format(co_tk), new Font(bf, 11, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);

			cells = new PdfPCell(new Paragraph(formatter.format(no_ck), new Font(bf, 11, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);

			cells = new PdfPCell(new Paragraph(formatter.format(co_ck), new Font(bf, 11, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);
			
			if(no_dk - co_dk > 0){
				cells = new PdfPCell(new Paragraph(formatter.format(no_dk - co_dk), new Font(bf, 11, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);

				cells = new PdfPCell(new Paragraph("", new Font(bf, 11, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
			}else{
				cells = new PdfPCell(new Paragraph("", new Font(bf, 11, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);

				cells = new PdfPCell(new Paragraph(formatter.format((-1)*(no_dk - co_dk)), new Font(bf, 11, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);				
			}

			cells = new PdfPCell(new Paragraph("", new Font(bf, 11, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);

			cells = new PdfPCell(new Paragraph("", new Font(bf, 11, Font.BOLD)));
			cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cells.setBorder(com.lowagie.text.Cell.BOX);
			cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cells.setPadding(4.0f);
			trongky.addCell(cells);
			
			if(no_ck - co_ck > 0){
				cells = new PdfPCell(new Paragraph(formatter.format(no_ck - co_ck), new Font(bf, 11, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);

				cells = new PdfPCell(new Paragraph("", new Font(bf, 11, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);
			}else{
				cells = new PdfPCell(new Paragraph("", new Font(bf, 11, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);

				cells = new PdfPCell(new Paragraph(formatter.format((-1)*(no_ck - co_ck)), new Font(bf, 11, Font.BOLD)));
				cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cells.setBorder(com.lowagie.text.Cell.BOX);
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(4.0f);
				trongky.addCell(cells);				
			}

			
			document.add(trongky);
			document.close();
			}catch(java.sql.SQLException e){}
			
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
		
	}

	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
				
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BaoCaoChiTietCongNoNV.xlsm");
						
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateHeader(workbook, obj);
		isFillData = FillData(workbook, obj, this.getDauky(obj), this.getQuery(obj));
   
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
	
	private void CreateHeader(Workbook workbook, IStockintransit obj) {
		
		dbutils db = new dbutils();
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		ResultSet ctyRs = obj.getRsErpCongty();
		String ctyName = "";
		String diachi = "";
		String masothue = "";
		
		try{
			if(ctyRs != null){
				ctyRs.next();
			
				ctyName = ctyRs.getString("TEN");
				diachi =  ctyRs.getString("DIACHI");
				masothue =  ctyRs.getString("MASOTHUE");
				
				ctyRs.close();
			}
			
		}catch(java.sql.SQLException e){
			System.out.println(e.toString());
		}
		
		//LẤY TÊN VÀ MÃ NHÂN VIÊN
		
		String query = " select Ma+ ' - '+ Ten as tenkh from ERP_NHANVIEN WHERE PK_SEQ = "+obj.getNhanvienId();
		
		ResultSet khRs = db.get(query);
		
		String tenkh = "";
		
		try{
			if(khRs != null){
				khRs.next();
			
				tenkh = khRs.getString("tenkh");
				
				khRs.close();
			}
			
		}catch(java.sql.SQLException e){
			System.out.println(e.toString());
		}
		
		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, ctyName);
	    
	    cells.setRowHeight(1, 20.0f);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Địa chỉ: " + diachi); 
	    
	    cells.setRowHeight(2, 20.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Mã số thuế: " + masothue); 
	    

	    cell = cells.getCell("D5"); cell.setValue("BÁO CÁO CHI TIẾT CÔNG NỢ KHÁCH HÀNG");

	    cell = cells.getCell("A8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày: " + obj.gettungay());
	    
	    cell = cells.getCell("C8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày: " + obj.getdenngay());
	    	    
	    cell = cells.getCell("A9"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Mã và tên Khách hàng: "  + tenkh); 
	    db.shutDown();
	    
	}
	
	private void setStyleColorGray(Cells cells ,Cell cell, String leftright)
	{
		Cell cell1 = cells.getCell("Y1");
		Style style;	
		style = cell1.getStyle();
        if(leftright.equals("1")){
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
            cell.setStyle(style);
        }else{
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
            cell.setStyle(style);        	
        }
        
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
		Cell cell1 = cells.getCell("Y1");
		Style style;	
		 style = cell1.getStyle();		 
       //style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
       // style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        cell.setStyle(style);
        
        
	}

	private boolean FillData(Workbook workbook, IStockintransit obj, String sqlDauky, String sqlTrongky) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		
		for(int i = 0;i < 8; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 0)
	    		cells.setColumnWidth(i, 5.0f);
	    	else if(i==1)
	    		cells.setColumnWidth(i, 25.0f);
	    	else if(i==2)
	    		cells.setColumnWidth(i, 25.0f);
	    	else if(i==3)
	    		cells.setColumnWidth(i, 25.0f);
	    	else if(i==7)
	    		cells.setColumnWidth(i, 25.0f);
	    }	
		
		double totaltangtrongky=0;
		double totalgiamtrongky=0;
		double totalnocodauky=0;

		// Lay so du dau ky
		ResultSet rs = db.get(sqlDauky);
		if(rs != null){
			rs.next();
			totalnocodauky = rs.getDouble("DAUKY");
		}
		rs.close();
		
		Cell cell = null;
		cell = cells.getCell("K11"); cell.setValue(totalnocodauky);
		this.setStyleColorGray(cells, cell, "1");
		
		rs = db.get(sqlTrongky);
		int index = 12;
		
		if (rs != null) 
		{
			try 
			{
				cell = null;
				while (rs.next())
				{		
					
					totalgiamtrongky=totalgiamtrongky+rs.getDouble("GIAMTRONGKY");
					totaltangtrongky=totaltangtrongky +rs.getDouble("TANGTRONGKY");
					
					cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index-11);	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("LOAI"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("SOHIEUTAIKHOAN"));	
					this.setStyleColorNormar(cells, cell);	
					cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("SOHIEUTAIKHOAN_DU"));	
					this.setStyleColorNormar(cells, cell);	
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("ID"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getString("SOHOADON"));
					this.setStyleColorNormar(cells, cell);					
					cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getString("NGAY"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getString("KHOANMUC"));	
					this.setStyleColorNormar(cells, cell);	
					cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getDouble("TANGTRONGKY"));
					this.setStyleColorNormar(cells, cell);					
					cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getDouble("GIAMTRONGKY"));	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(0);
					this.setStyleColorNormar(cells, cell);
					
					
					
					index++;					
				}

				if (rs != null){
					rs.close();
				}
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("Tổng cộng");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("C" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");				
				
				cell = cells.getCell("H" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(totaltangtrongky);	
				this.setStyleColorGray(cells, cell, "1");
				cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(totalgiamtrongky);	
				this.setStyleColorGray(cells, cell, "1");
				cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(totalnocodauky+totaltangtrongky-totalgiamtrongky);	//Nhan hang   	6
				this.setStyleColorGray(cells, cell, "1");
				
				index=index+3;
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("B" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Lập biểu");
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue("Giám đốc");
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Giám đốc");
				
				index=index+5;
				cell = cells.getCell("B" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
				
				cell = cells.getCell("F" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			
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
