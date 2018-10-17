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
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpBCChiTietCongNoNV extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpBCChiTietCongNoNV() {
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
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiTietCongNoNV.jsp";
		
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
	/*	obj.setkenhId(util.antiSQLInspection(request.getParameter("kbhId"))!=null?
				util.antiSQLInspection(request.getParameter("kbhId")):"");*/
		
	/*	session.setAttribute("kbhId", obj.getkenhId());*/
		
		obj.setNhanvienId(util.antiSQLInspection(request.getParameter("nvid")));
	/*	
		String nkhId = util.antiSQLInspection(request.getParameter("nkhId"));
		if(nkhId == null)
			nkhId = "";
		obj.setNhomKhId(nkhId);*/
		
		obj.InitErp();
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiTietCongNoNV.jsp";
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChiTietCongNoNV.xlsm");
			
			String query = getQuery(obj); 

	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj))
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
		
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		return;
	}
	
	public String getQuery(IStockintransit obj)
	{
		String kh = null;
				
		if(!obj.getNhanvienId().equals("")){
			 kh = obj.getNhanvienId();
		 }
		
		//XÓA NỢ KHÁCH HÀNG CHƯA CÀI KẾ TOÁN - KHI NÀO CÀI THÌ ĐƯA VÀO
		String query = "";
			
			//2. Phiếu chi
			//3. Ủy nhiệm chi
			//4. Phiếu thu
			//5. Đề nghị thanh toán
			//6. Bút toán tổng hợp
			
				//PHIẾU CHI, ỦY NHIỆM CHI
				query =				
				"	SELECT	N'Phiếu chi' AS LOAI, '' AS SOHOADON, cast ( TT.PK_SEQ as nvarchar(50)) ID, PS.NGAYCHUNGTU AS NGAY , ISNULL(PS.NO,0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, \n"+
				"			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, 1 AS STT \n"+
				"	FROM	ERP_THANHTOANHOADON TT " +
				"	INNER JOIN ERP_THANHTOANHOADON_HOADON TT_HD ON TT.PK_SEQ = TT_HD.THANHTOANHD_FK \n"+
				"	INNER JOIN ERP_HOADONNCC HD ON HD.pk_seq = TT_HD.HOADON_FK \n"+
				"	INNER JOIN ERP_PHATSINHKETOAN PS ON TT.PK_SEQ = PS.SOCHUNGTU \n"+
				"	INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
				"	INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+	
				" 	WHERE	TT.TRANGTHAI = 1 AND TT_HD.THANHTOANHD_FK = TT.PK_SEQ \n"+
				"			AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' \n"+
				"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHANVIEN ) \n"+
				"			AND PS.LOAICHUNGTU = N'Thanh toán hóa đơn' \n"+
				"			AND TT.NHANVIEN_FK IS NOT NULL \n"+
				"			AND PS.NO >0 ";
				
				if(kh!=null)
				{
					query += " AND TT.NHANVIEN_FK = '"+kh+"' "; // AND PS.MADOITUONG = '"+kh+"'
				}
						
				query+=
				" UNION ALL \n"+
				
				//BÚT TOÁN TỔNG HỢP
				"	SELECT 	N'Bút toán tổng hợp' AS LOAI,'' SOHOADON, cast ( BTTH_CT.BUTTOANTONGHOP_FK as nvarchar(50)) AS ID, BTTH.NGAYBUTTOAN AS NGAY, PS.NO AS TANGTRONGKY, 0 AS GIAMTRONGKY , \n"+
				"	   		PS.KHOANMUC, TK.SOHIEUTAIKHOAN,TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, " +
				" 			2 AS STT  \n"+
				"	FROM   	ERP_BUTTOANTONGHOP_CHITIET BTTH_CT \n"+
				"	   		INNER JOIN ERP_BUTTOANTONGHOP BTTH ON BTTH.PK_SEQ = BTTH_CT.BUTTOANTONGHOP_FK \n"+
				"	   		INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH_CT.BUTTOANTONGHOP_FK = PS.SOCHUNGTU \n"+
				"	   		INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
				"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+ 
				"	WHERE   BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU >= '"+obj.gettungay()+"' AND PS.NGAYCHUNGTU <= '"+obj.getdenngay()+"' \n"+ 
				"	   		AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' \n"+ 
				"	   		AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHANVIEN ) \n"+
				"	   		AND PS.NO > 0 AND BTTH_CT.NHANVIEN_FK IS NOT NULL \n";
				
				if(kh!=null)
				{
					query+= " AND BTTH_CT.NHANVIEN_FK = '"+kh+"'";
				}
				
				query+=
				" UNION ALL \n"+
				
				//BÚT TOÁN TỔNG HỢP
				"	SELECT 	N'Bút toán tổng hợp' AS LOAI,'' SOHOADON,cast ( BTTH_CT.BUTTOANTONGHOP_FK as nvarchar(50)) AS ID, BTTH.NGAYBUTTOAN AS NGAY, 0 AS TANGTRONGKY, PS.CO AS GIAMTRONGKY , \n"+
				"	   		PS.KHOANMUC, TK.SOHIEUTAIKHOAN,TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, " +
				" 			3 AS STT  \n"+
				"	FROM   	ERP_BUTTOANTONGHOP_CHITIET BTTH_CT \n"+
				"	   		INNER JOIN ERP_BUTTOANTONGHOP BTTH ON BTTH.PK_SEQ = BTTH_CT.BUTTOANTONGHOP_FK \n"+
				"	   		INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH_CT.BUTTOANTONGHOP_FK = PS.SOCHUNGTU \n"+
				"	   		INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
				"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+ 
				"	WHERE   BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU >= '"+obj.gettungay()+"' AND PS.NGAYCHUNGTU <= '"+obj.getdenngay()+"' \n"+ 
				"	   		AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' \n"+ 
				"	   		AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHANVIEN ) \n"+
				"	   		AND PS.CO > 0 AND BTTH_CT.NHANVIEN_FK IS NOT NULL \n";
				
				if(kh!=null)
				{
					query+= " AND BTTH_CT.NHANVIEN_FK = '"+kh+"'";
				}	
		
				//THU TIỀN
				
				query+=
				" UNION ALL \n"+
					
				" SELECT  	N'Thu tiền' as LOAI,'' SOHOADON , cast (  TT.PK_SEQ as nvarchar(50) ) AS ID, TT.NGAYCHUNGTU AS NGAY, 0 AS TANGTRONGKY, PS.CO AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU," +
				"			4 AS STT \n"+
				" FROM    	ERP_THUTIEN TT LEFT JOIN  ERP_THUTIEN_HOADON TTHD ON TT.PK_SEQ = TTHD.THUTIEN_FK \n"+
				"			INNER JOIN ERP_PHATSINHKETOAN PS ON TT.PK_SEQ = PS.SOCHUNGTU \n"+
				"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
				"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+ 
				" WHERE    	PS.NGAYCHUNGTU >= '"+obj.gettungay()+"' AND PS.NGAYCHUNGTU <= '"+obj.getdenngay()+"' \n"+ 
				"			AND TT.TRANGTHAI = 1 \n"+
				"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHANVIEN ) \n"+
				"			AND PS.LOAICHUNGTU = N'Thu tiền_Thu hồi tạm ứng' AND TT.NHANVIEN_FK IS NOT NULL  \n"+
				"			AND PS.CO > 0 \n";		
				
				if(kh!=null)
				{
					query+= " AND TT.NHANVIEN_FK IN ( "+kh+" ) ";
				}
				
				//ĐỀ NGHỊ THANH TOÁN
				query+=
				" UNION ALL \n"+	
				
				" SELECT	N'Đề nghị thanh toán' AS LOAI,'' AS SOHOADON,cast ( CPNK.SOPO as nvarchar(50))  ID , CPNK.NGAYMUA, 0 AS TANGTRONGKY,ISNULL(PS.CO,0) AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, \n"+ 
				"			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, " +
				"			5 AS STT \n"+
				" FROM		ERP_MUAHANG CPNK \n"+
				"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU \n"+
				"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
				"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
				" WHERE		CPNK.trangthai IN (1,2) AND  PS.NGAYCHUNGTU >= '" + obj.gettungay() + "' AND  PS.NGAYCHUNGTU <= '" + obj.getdenngay() + "' \n"+				
				"			AND PS.LOAICHUNGTU = N'Chi phí khác' \n"+
				"			AND PS.CO > 0 \n"+
				"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHANVIEN ) AND CPNK.NHANVIEN_FK IS NOT NULL ";
				
				if(kh!=null)
				{
						query += "  AND CPNK.NHANVIEN_FK = '"+kh+"'";	//AND PS.MADOITUONG = '"+kh+"'
				}
				
				//ĐỀ NGHỊ THANH TOÁN
				query+=
				" UNION ALL \n"+	
				
				" SELECT	N'Đề nghị thanh toán' AS LOAI,'' AS SOHOADON,cast ( CPNK.SOPO as nvarchar(50))  ID , CPNK.NGAYMUA, 0 AS TANGTRONGKY,ISNULL(PS.CO,0) AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, \n"+ 
				"			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, " +
				"			5 AS STT \n"+
				" FROM		ERP_MUAHANG CPNK \n"+
				"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU \n"+
				"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
				"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
				" WHERE		CPNK.trangthai IN (1,2) AND  PS.NGAYCHUNGTU >= '" + obj.gettungay() + "' AND  PS.NGAYCHUNGTU <= '" + obj.getdenngay() + "' \n"+				
				"			AND PS.LOAICHUNGTU = N'Chi phí khác' \n"+
				"			AND PS.CO > 0 \n"+
				"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHANVIEN ) AND CPNK.NHANVIEN_FK IS NOT NULL ";
				
				if(kh!=null)
				{
						query += "  AND CPNK.NHANVIEN_FK = '"+kh+"'";	//AND PS.MADOITUONG = '"+kh+"'
				}
				
				//HÓA ĐƠN TÀI CHÍNH
				query+=
				" UNION ALL \n"+	
				
				" SELECT	N'Hóa đơn tài chính' AS LOAI,'' AS SOHOADON,cast ( CPNK.PK_SEQ as nvarchar(50))  ID , CPNK.NGAYXUATHD, ISNULL(PS.NO,0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN,  \n"+ 
				"			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,  " +
				"			6 AS STT \n"+
				" FROM		ERP_HOADONNPP CPNK \n"+
				"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU \n"+
				"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
				"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ  \n"+
				" WHERE		CPNK.trangthai IN (1,2) AND  PS.NGAYCHUNGTU >= '" + obj.gettungay() + "' AND  PS.NGAYCHUNGTU <= '" + obj.getdenngay() + "' \n"+				
				"			AND PS.LOAICHUNGTU = N'Hóa đơn tài chính' \n"+
				"			AND PS.NO > 0 \n"+
				"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13100000' AND congty_fk = '"+obj.getErpCongtyId()+"') AND CPNK.NHANVIEN_FK IS NOT NULL ";
				
				if(kh!=null)
				{
						query += "  AND CPNK.NHANVIEN_FK = '"+kh+"'";	//AND PS.MADOITUONG = '"+kh+"'
				}
				
			System.out.println("Get Sql : "+ query);
		    return query;   
	}
	
	public String getDauky(IStockintransit obj)
	{
		String kh = null;
				
		if(obj.getNhomKhId().trim().length() > 0){
			kh = "SELECT PK_SEQ FROM ERP_KHACHHANG WHERE NHOMKHACHHANG_FK = '"+obj.getNhomKhId()+"'";
		}
		
		if(!obj.getErpKhachHangId().equals("")){
			 kh = obj.getErpKhachHangId();
		 }
		
		String query;
		query =     
		"	SELECT ISNULL(SUM(ISNULL(a.TANGTRONGKY,0)) - SUM (ISNULL(a.GIAMTRONGKY, 0)),0) AS DAUKY \n" +
		"   FROM  ( \n" ;
		
		//PHIẾU CHI, ỦY NHIỆM CHI
		query +=				
		"	SELECT	N'Phiếu chi' AS LOAI, '' AS SOHOADON, TT.PK_SEQ ID, PS.NGAYCHUNGTU AS NGAY , ISNULL(PS.NO,0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, \n"+
		"			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,  1 AS STT \n"+
		"	FROM	ERP_THANHTOANHOADON TT INNER JOIN ERP_THANHTOANHOADON_HOADON TT_HD ON TT.PK_SEQ = TT_HD.THANHTOANHD_FK \n"+
		"			INNER JOIN ERP_HOADONNCC HD ON HD.pk_seq = TT_HD.HOADON_FK \n"+
		"			INNER JOIN ERP_PHATSINHKETOAN PS ON TT.PK_SEQ = PS.SOCHUNGTU \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+	
		" 	WHERE	TT.TRANGTHAI = 1 AND TT_HD.THANHTOANHD_FK = TT.PK_SEQ \n"+
		"			AND PS.NGAYCHUNGTU <='"+ obj.gettungay() + "' \n"+
		"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHANVIEN ) \n"+
		"			AND PS.LOAICHUNGTU = N'Thanh toán hóa đơn' \n"+
		"			AND TT.NHANVIEN_FK IS NOT NULL \n"+
		"			AND PS.NO >0 ";
		
		if(kh!=null)
		{
			query += " AND TT.NHANVIEN_FK = '"+kh+"' "; // AND PS.MADOITUONG = '"+kh+"'
		}
				
		query+=
		" UNION ALL \n"+
		
		//BÚT TOÁN TỔNG HỢP
		"	SELECT 	N'Bút toán tổng hợp' AS LOAI,'' SOHOADON,BTTH_CT.BUTTOANTONGHOP_FK AS ID, BTTH.NGAYBUTTOAN AS NGAY, PS.NO AS TANGTRONGKY, 0 AS GIAMTRONGKY , \n"+
		"	   		PS.KHOANMUC, TK.SOHIEUTAIKHOAN,TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, " +
		" 			2 AS STT  \n"+
		"	FROM   	ERP_BUTTOANTONGHOP_CHITIET BTTH_CT \n"+
		"	   		INNER JOIN ERP_BUTTOANTONGHOP BTTH ON BTTH.PK_SEQ = BTTH_CT.BUTTOANTONGHOP_FK \n"+
		"	   		INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH_CT.BUTTOANTONGHOP_FK = PS.SOCHUNGTU \n"+
		"	   		INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+ 
		"	WHERE   BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU <= '"+obj.gettungay()+"' \n"+ 
		"	   		AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' \n"+ 
		"	   		AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHANVIEN ) \n"+
		"	   		AND PS.NO > 0 AND BTTH_CT.NHANVIEN_FK IS NOT NULL \n";
		
		if(kh!=null)
		{
			query+= " AND BTTH_CT.NHANVIEN_FK = '"+kh+"'";
		}
		
		query+=
		" UNION ALL \n"+
		
		//BÚT TOÁN TỔNG HỢP
		"	SELECT 	N'Bút toán tổng hợp' AS LOAI,'' SOHOADON,BTTH_CT.BUTTOANTONGHOP_FK AS ID, BTTH.NGAYBUTTOAN AS NGAY, 0 AS TANGTRONGKY, PS.CO AS GIAMTRONGKY , \n"+
		"	   		PS.KHOANMUC, TK.SOHIEUTAIKHOAN,TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, " +
		" 			3 AS STT  \n"+
		"	FROM   	ERP_BUTTOANTONGHOP_CHITIET BTTH_CT \n"+
		"	   		INNER JOIN ERP_BUTTOANTONGHOP BTTH ON BTTH.PK_SEQ = BTTH_CT.BUTTOANTONGHOP_FK \n"+
		"	   		INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH_CT.BUTTOANTONGHOP_FK = PS.SOCHUNGTU \n"+
		"	   		INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+ 
		"	WHERE   BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU <= '"+obj.gettungay()+"' \n"+ 
		"	   		AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' \n"+ 
		"	   		AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHANVIEN ) \n"+
		"	   		AND PS.CO > 0 AND BTTH_CT.NHANVIEN_FK IS NOT NULL \n";
		
		if(kh!=null)
		{
			query+= " AND BTTH_CT.NHANVIEN_FK = '"+kh+"'";
		}	

		//THU TIỀN
		
		query+=
		" UNION ALL \n"+
			
		" SELECT  	N'Thu tiền' as LOAI,'' SOHOADON ,TT.PK_SEQ AS ID, TT.NGAYCHUNGTU AS NGAY, 0 AS TANGTRONGKY, PS.CO AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU," +
		"			4 AS STT \n"+
		" FROM    	ERP_THUTIEN TT LEFT JOIN  ERP_THUTIEN_HOADON TTHD ON TT.PK_SEQ = TTHD.THUTIEN_FK \n"+
		"			INNER JOIN ERP_PHATSINHKETOAN PS ON TT.PK_SEQ = PS.SOCHUNGTU \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+ 
		" WHERE    	PS.NGAYCHUNGTU <= '"+obj.gettungay()+"' \n"+ 
		"			AND TT.TRANGTHAI = 1 \n"+
		"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHANVIEN ) \n"+
		"			AND PS.LOAICHUNGTU = N'Thu tiền_Thu hồi tạm ứng' AND TT.NHANVIEN_FK IS NOT NULL  \n"+
		"			AND PS.CO > 0 \n";		
		
		if(kh!=null)
		{
			query+= " AND TT.NHANVIEN_FK IN ( "+kh+" ) ";
		}
		
		//ĐỀ NGHỊ THANH TOÁN
		query+=
		" UNION ALL \n"+	
		
		" SELECT	N'Đề nghị thanh toán' AS LOAI,'' AS SOHOADON,CPNK.pk_seq ID ,CPNK.NGAYMUA, 0 AS TANGTRONGKY,ISNULL(PS.CO,0) AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, \n"+ 
		"			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, " +
		"			5 AS STT \n"+
		" FROM		ERP_MUAHANG CPNK \n"+
		"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
		" WHERE		CPNK.trangthai IN (1,2) AND  PS.NGAYCHUNGTU <= '" + obj.gettungay() + "' \n"+				
		"			AND PS.LOAICHUNGTU = N'Chi phí khác' \n"+
		"			AND PS.CO > 0 \n"+
		"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHANVIEN ) AND CPNK.NHANVIEN_FK IS NOT NULL ";
		
		if(kh!=null)
		{
				query += "  AND CPNK.NHANVIEN_FK = '"+kh+"'";	//AND PS.MADOITUONG = '"+kh+"'
		}
		
		query+=
		" ) a \n";
		
		System.out.println("Get sql dauky : "+ query);
	    return query;   
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
		
		String query = " select Ma+ ' - '+ TenXuatHD as tenkh from ERP_NHANVIEN WHERE PK_SEQ = "+obj.getNhanvienId();
		
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
	    

	    cell = cells.getCell("D5"); cell.setValue("BÁO CÁO CHI TIẾT CÔNG NỢ NHÂN VIÊN");

	    cell = cells.getCell("A8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày: " + obj.gettungay());
	    
	    cell = cells.getCell("C8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày: " + obj.getdenngay());
	    	    
	    cell = cells.getCell("A9"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Mã và tên nhân viên: "  + tenkh); 
	    
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
