package geso.traphaco.erp.servlets.reports;

import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.erp.beans.stockintransit.*;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

public class ErpBCCongNoTongHopHdNCC extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpBCCongNoTongHopHdNCC() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String ctyId = (String)session.getAttribute("congtyId");
		String querystring = request.getQueryString();
		Utility util = new Utility();
		
		obj.setErpCongtyId(ctyId);
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setnppdangnhap(util.getIdNhapp(util.getUserId(querystring)));
		
		String view = util.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		obj.setView(view);
		obj.InitErp();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBcTongHopCongNoHdNCC.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		Utility util = new Utility();
		 
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		obj.setErpCongtyId(ctyId);
		obj.setnppdangnhap(util.getIdNhapp(userId));
		
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		
		String[] ctyIds = request.getParameterValues("ctyIds");
		obj.setCtyIds(ctyIds);
		
		obj.setView(util.antiSQLInspection(request.getParameter("view")));
		
		obj.settungay(util.antiSQLInspection(request.getParameter("tungay")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null?util.antiSQLInspection(request.getParameter("kenhId")):"");
		
		String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		if (nccId == null)
			nccId = "";			
    	obj.setErpNCCId(nccId);
    	
		obj.InitErp();
		
		String str2 = "";
		String str="";
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBcTongHopCongNoHdNCC.jsp";
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{						
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCTongHopCongNoHdNCC.xlsm");
			
			//String query = setQuery(obj); 
			
			String[] fieldsHien = request.getParameterValues("fieldsHien");
			obj.setFieldShow(fieldsHien);
			
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
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
				
			}
		}else{
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
			return;
		}
	}
	
	public String setQuery(IStockintransit obj)
	{
		String query=	" SELECT PK_SEQ, MA, TAIKHOAN_FK, TEN , ISNULL(DAUKY.DAUKY,0) AS DUNODAUKY, ISNULL(TANGTRONGKY.TANGTRONGKY,0) TANGTRONGKY , ISNULL(GIAMTRONGKY.GIAMTRONGKY,0) GIAMTRONGKY \n" +
						" FROM ERP_NHACUNGCAP NCC  \n" +
						" LEFT JOIN ( " +
						"	SELECT NCCID, ISNULL(SUM(TANGTRONGKY - GIAMTRONGKY), 0) AS DAUKY \n" +
						"	FROM (" +						
						
						//CHI PHÍ NHẬN HÀNG
						" SELECT	CPNK.NCCID_CN AS NCCID, N'Chi phí nhận hàng' AS LOAI,SUM(ISNULL(PS.CO,0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY, 1 AS STT \n"+
						" FROM		ERP_CHIPHINHAPKHAU CPNK INNER JOIN ERP_CHIPHINHAPKHAU_CHITIET CPNK_CT ON CPNK.PK_SEQ = CPNK_CT.CHIPHINHAPKHAU_FK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE		CPNK.trangthai = '1' AND  PS.NGAYCHUNGTU < '" + obj.gettungay() + "' AND CPNK.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +			
						"			AND PS.LOAICHUNGTU = N'Chi phí nhập khẩu' \n"+
						"			AND CPNK.NCCID_CN IS NOT NULL \n"+ 
						"			AND PS.CO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) \n"+
						" GROUP BY  CPNK.NCCID_CN \n" +
												
						//THUE NHAP KHAU
						"	UNION ALL \n"+
						
						"	SELECT	TNK.NCC_FK AS NCCID, N'Thuế nhập khẩu' AS LOAI, SUM(ISNULL(PS.CO, 0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY,  2 AS STT \n"+
						"	FROM	ERP_THUENHAPKHAU_CHITIET TNK_CT INNER JOIN ERP_THUENHAPKHAU TNK ON TNK.PK_SEQ = TNK_CT.THUENHAPKHAU_FK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON TNK.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						"	WHERE	TNK.TRANGTHAI IN (1,2) AND PS.NGAYCHUNGTU < '" + obj.gettungay() + "' AND TNK.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +
						"			AND (PS.LOAICHUNGTU = N'Thuế nhập khẩu' OR PS.LOAICHUNGTU = N'Thuế VAT nhập khẩu' ) \n"+
						"			AND PS.CO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null  ) "+
						"   GROUP BY TNK.NCC_FK "+
												
						//CHI PHI KHAC
						" UNION ALL \n"+
						
						" SELECT	CPK.DOITUONG AS NCCID, N'Chi phí khác' AS LOAI, SUM(ISNULL(PS.CO,0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY \n"+
						"			,3 AS STT \n"+
						" FROM		ERP_CHIPHIKHAC_CHITIET CPK_CT INNER JOIN ERP_CHIPHIKHAC CPK ON CPK.PK_SEQ = CPK_CT.CHIPHIKHAC_FK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPK.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE		CPK.TRANGTHAI = '2' AND CPK.LOAI = 0 AND CPK.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +
						"			AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "' \n"+
						"			AND PS.LOAICHUNGTU = N'Chi phí khác' \n"+
						"			AND PS.CO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null  ) \n"+
						" GROUP BY  CPK.DOITUONG \n"+
						
						// BUTTOANTONGHOP
						" UNION ALL \n"+
						
						" SELECT	BTTH_CT.NCC_FK AS NCCID, N'Bút toán tổng hợp' AS LOAI, SUM(ISNULL(PS.CO, 0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY,  \n"+
						"			 4 AS STT \n"+
						" FROM		ERP_BUTTOANTONGHOP_CHITIET BTTH_CT INNER JOIN ERP_BUTTOANTONGHOP BTTH ON BTTH_CT.BUTTOANTONGHOP_FK = BTTH.PK_SEQ \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+		
						" WHERE		BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "' AND BTTH.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +
						"			AND PS.CO > 0 \n"+
						"			AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' \n"+
						"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+
						" GROUP BY  BTTH_CT.NCC_FK \n"+				
						
						//HOA DON NCC
						" UNION ALL \n"+
						
						" SELECT	P.NCC_FK AS NCCID, N'Hóa đơn NCC' AS LOAI, SUM(ISNULL(PS.CO,0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY, \n"+
						"			5 AS STT \n"+
						" FROM		ERP_HOADONNCC HD INNER JOIN ERP_PARK P ON P.PK_SEQ=HD.PARK_FK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON P.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+	
						" WHERE   	P.trangthai = 2 AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "' AND HD.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +
						"			AND PS.CO > 0 \n"+ 	
						"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) \n"+	
						"			AND (PS.LOAICHUNGTU = N'Duyệt PARK hóa đơn' OR PS.LOAICHUNGTU = N'Duyệt hóa đơn NCC' )"+
						" GROUP BY  P.NCC_FK "+
														
						//UY NHIEM CHI/PHIEU CHI
						"	UNION ALL \n"+
						
						"	SELECT	TT.NCC_FK AS NCCID, N'Phiếu thanh toán' AS LOAI, 0 AS TANGTRONGKY, SUM(ISNULL(PS.NO,0)) AS GIAMTRONGKY,  \n"+
						"			6 AS STT \n"+
						"	FROM	ERP_THANHTOANHOADON TT INNER JOIN ERP_THANHTOANHOADON_HOADON TT_HD ON TT.PK_SEQ = TT_HD.THANHTOANHD_FK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON TT.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+	
						" 	WHERE	TT.TRANGTHAI = 1 AND TT_HD.THANHTOANHD_FK = TT.PK_SEQ AND TT.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +
						"			AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "' \n"+
						"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) \n"+
						"			AND PS.LOAICHUNGTU = N'Thanh toán hóa đơn' \n"+
						"			AND PS.NO >0 "+
						"	GROUP BY TT.NCC_FK " +
						
						
						// BUT TOAN TONG HOP
						" UNION ALL \n"+
						
						" SELECT	BTTH_CT.NCC_FK, N'Bút toán tổng hợp' AS LOAI,  0 AS TANGTRONGKY, SUM(ISNULL(PS.NO, 0)) AS GIAMTRONGKY,  7 AS STT \n"+
						" FROM		ERP_BUTTOANTONGHOP_CHITIET BTTH_CT INNER JOIN ERP_BUTTOANTONGHOP BTTH ON BTTH_CT.BUTTOANTONGHOP_FK = BTTH.PK_SEQ \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+		
						" WHERE		BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "' AND BTTH.CONGTY_FK IN ("+obj.getErpCongtyId()+")" +
						"			AND PS.NO > 0 \n"+
						"			AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' \n"+
						"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+
						" GROUP BY  BTTH_CT.NCC_FK "+													
						
						//GIAM GIA HANG MUA
						" UNION ALL \n"+
						
						" SELECT	GGHM.NCC_FK AS NCCID,N'Tăng/Giảm giá hàng mua' AS LOAI,  0 AS TANGTRONGKY, SUM(ISNULL(PS.NO, 0)) AS GIAMTRONGKY,  8 AS STT \n"+
						" FROM		ERP_GIAMGIAHANGMUA_HOADON GGHM_CT INNER JOIN ERP_GIAMGIAHANGMUA GGHM ON GGHM_CT.giamgia_fk = GGHM.PK_SEQ \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON GGHM.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE		GGHM.TRANGTHAI = '1' AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "' AND GGHM.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +
						"			AND PS.NO > 0 \n"+
						"			AND PS.LOAICHUNGTU = N'Giảm giá hàng mua' \n"+
						"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+
						" GROUP BY  GGHM.NCC_FK " +
						
						" UNION ALL \n"+
						
						//NHẬN HÀNG
						" SELECT	CPNK.NCC_KH_FK, N'Nhận hàng' AS LOAI,SUM(ISNULL(PS.CO,0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY \n"+ 
						"			, 9 AS STT \n"+
						" FROM		ERP_NHANHANG CPNK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE		CPNK.trangthai IN (1,2) AND  PS.NGAYCHUNGTU < '" + obj.gettungay() + "' " +
						"			AND CPNK.CONGTY_FK IN ("+obj.getErpCongtyId()+") " + 									
						"			AND PS.LOAICHUNGTU = N'Nhận hàng' \n"+
						"			AND PS.CO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+
						" GROUP BY  CPNK.NCC_KH_FK \n"+
						
						" UNION ALL \n"+	
						
						//ĐỀ NGHỊ THANH TOÁN
						" SELECT	CPNK.NHACUNGCAP_FK, N'Đề nghị thanh toán' AS LOAI,SUM(ISNULL(PS.CO,0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY,\n"+ 
						"			 10 AS STT \n"+
						" FROM		ERP_MUAHANG CPNK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE		CPNK.trangthai IN (1,2) AND  PS.NGAYCHUNGTU < '" + obj.gettungay() + "' AND CPNK.CONGTY_FK IN ("+obj.getErpCongtyId()+") " + 
						"			AND PS.LOAICHUNGTU = N'Chi phí khác' \n"+
						"			AND PS.CO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+
						" GROUP BY  CPNK.NHACUNGCAP_FK "+		
						
						//HOA DON NCC - TIỀN CHIẾT KHẤU < 0
						" UNION ALL \n"+
						
						" SELECT	P.NCC_FK AS NCCID, N'Hóa đơn NCC' AS LOAI, 0 AS TANGTRONGKY, SUM(ISNULL(PS.NO,0)) AS GIAMTRONGKY, \n"+
						"			11 AS STT \n"+
						" FROM		ERP_HOADONNCC HD INNER JOIN ERP_PARK P ON P.PK_SEQ=HD.PARK_FK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON P.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+	
						" WHERE   	P.trangthai = 2 AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "' AND HD.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +
						"			AND PS.NO > 0 \n"+ 	
						"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) \n"+	
						"			AND (PS.LOAICHUNGTU = N'Duyệt PARK hóa đơn' OR PS.LOAICHUNGTU = N'Duyệt hóa đơn NCC' )"+
						" GROUP BY  P.NCC_FK "+
						
						//CẤN TRỪ CÔNG NỢ
						" UNION ALL \n"+
						
						" SELECT	CTCN.NCC_FK AS NCCID, N'Hóa đơn NCC' AS LOAI, 0 AS TANGTRONGKY, SUM(ISNULL(PS.NO,0)) AS GIAMTRONGKY, \n"+
						"			11 AS STT \n"+
						" FROM		ERP_CANTRUCONGNO CTCN \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE		CTCN.trangthai IN (1) AND  PS.NGAYCHUNGTU < '" + obj.gettungay() + "' AND CTCN.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +			
						"			AND PS.LOAICHUNGTU = N'Cấn trừ công nợ' \n"+
						"			AND PS.NO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK  IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+
						" GROUP BY  CTCN.NCC_FK "+
						
						// HOA DON TRA VỀ 
						" UNION ALL \n"+
						" SELECT	CTCN.NCC_FK AS NCCID, N'Cấn trừ công nợ' AS LOAI, 0 AS TANGTRONGKY, SUM(ISNULL(PS.NO,0)) AS GIAMTRONGKY, \n"+ 
						" 12 AS STT  \n"+
						" FROM		ERP_HOADON CTCN \n"+ 
						" INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU "+ 
						" INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ  "+
						" INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ  "+
						" WHERE		CTCN.trangthai IN (1) AND   PS.NGAYCHUNGTU < '" + obj.gettungay() + "'"+ 
						" AND CTCN.CONGTY_FK IN ("+obj.getErpCongtyId()+")    "+
						" AND PS.LOAICHUNGTU = N'Hóa đơn trả hàng NCC' "+ 
						" AND PS.NO > 0  "+
						" AND CTCN.LOAIHOADON=6 "+
						" AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+  	
						" GROUP BY  CTCN.NCC_FK  "+						
						
						//THU HỒI TẠM ỨNG
						" UNION ALL \n"+
						"	SELECT	CTCN.NCC_FK AS NCCID,N'Thu hồi tạm ứng' AS LOAI,0 AS TANGTRONGKY, SUM(ISNULL(PS.CO,0)) AS GIAMTRONGKY,  \n"+ 
						"			13 AS STT \n"+ 
						" FROM		ERP_THUTIEN CTCN \n"+ 
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+ 
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+ 
						" WHERE	CTCN.trangthai IN (1) AND PS.NGAYCHUNGTU < '"+obj.gettungay()+ "' \n"+
						"			AND CTCN.CONGTY_FK IN ( "+obj.getErpCongtyId()+" ) \n"+ 
						"			AND PS.LOAICHUNGTU = N'Thu tiền_Thu hồi tạm ứng' \n"+
						" 		AND CTCN.CONGTY_FK = "+obj.getErpCongtyId()+	
						"			AND PS.CO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN IN ('33100000') ) \n"+
						"  GROUP BY CTCN.NCC_FK \n"+
						"	)SUM GROUP BY SUM.NCCID \n" +
						")DAUKY ON DAUKY.NCCID = PK_SEQ \n" +
						"LEFT JOIN ( \n" +
						"	SELECT NCCID, SUM(TANGTRONGKY) AS TANGTRONGKY \n" +
						"	FROM ( \n" +
						
						//CHI PHÍ NHẬN HÀNG
						" SELECT	CPNK.NCCID_CN AS NCCID, N'Chi phí nhận hàng' AS LOAI,SUM(ISNULL(PS.CO,0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY, 1 AS STT \n"+
						" FROM		ERP_CHIPHINHAPKHAU CPNK INNER JOIN ERP_CHIPHINHAPKHAU_CHITIET CPNK_CT ON CPNK.PK_SEQ = CPNK_CT.CHIPHINHAPKHAU_FK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE		CPNK.trangthai = '1' AND  PS.NGAYCHUNGTU >= '" + obj.gettungay() + "' AND  PS.NGAYCHUNGTU <= '" + obj.getdenngay() + "' \n"+
						"			AND CPNK.CONGTY_FK IN ("+obj.getErpCongtyId()+")" +
						"			AND PS.LOAICHUNGTU = N'Chi phí nhập khẩu' \n"+
						"			AND CPNK.NCCID_CN IS NOT NULL \n"+ 
						"			AND PS.CO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null  ) \n"+
						" GROUP BY  CPNK.NCCID_CN \n" +
						
						//THUE NHAP KHAU
						"	UNION ALL \n"+
						
						"	SELECT	TNK.NCC_FK AS NCCID, N'Thuế nhập khẩu' AS LOAI, SUM(ISNULL(PS.CO, 0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY,  2 AS STT \n"+
						"	FROM	ERP_THUENHAPKHAU_CHITIET TNK_CT INNER JOIN ERP_THUENHAPKHAU TNK ON TNK.PK_SEQ = TNK_CT.THUENHAPKHAU_FK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON TNK.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						"	WHERE	TNK.TRANGTHAI IN (1,2) AND PS.NGAYCHUNGTU >= '" + obj.gettungay() + "' AND  PS.NGAYCHUNGTU <= '" + obj.getdenngay() + " ' \n"+
						"			AND TNK.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +	
						"			AND (PS.LOAICHUNGTU = N'Thuế nhập khẩu' OR PS.LOAICHUNGTU = N'Thuế VAT nhập khẩu' ) \n"+
						"			AND PS.CO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null  ) "+
						"   GROUP BY TNK.NCC_FK "+
						
						//CHI PHI KHAC
						" UNION ALL \n"+
						
						" SELECT	CPK.DOITUONG AS NCCID, N'Chi phí khác' AS LOAI, SUM(ISNULL(PS.CO,0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY \n"+
						"			,3 AS STT \n"+
						" FROM		ERP_CHIPHIKHAC_CHITIET CPK_CT INNER JOIN ERP_CHIPHIKHAC CPK ON CPK.PK_SEQ = CPK_CT.CHIPHIKHAC_FK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPK.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE		CPK.TRANGTHAI = '1' AND CPK.LOAI = 0 \n"+
						"			AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' \n"+
						"			AND CPK.CONGTY_FK IN ("+obj.getErpCongtyId()+")" +
						"			AND PS.LOAICHUNGTU = N'Chi phí khác' \n"+
						"			AND PS.CO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) \n"+
						" GROUP BY  CPK.DOITUONG  \n"+
						
						// BUTTOANTONGHOP
						" UNION ALL \n"+
						
						" SELECT	BTTH_CT.NCC_FK AS NCCID, N'Bút toán tổng hợp' AS LOAI, SUM(ISNULL(PS.CO, 0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY  \n"+
						"			 ,4 AS STT \n"+
						" FROM		ERP_BUTTOANTONGHOP_CHITIET BTTH_CT INNER JOIN ERP_BUTTOANTONGHOP BTTH ON BTTH_CT.BUTTOANTONGHOP_FK = BTTH.PK_SEQ \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+		
						" WHERE		BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' \n"+
						"			AND BTTH.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +
						"			AND PS.CO > 0 \n"+
						"			AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' \n"+
						"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+
						" GROUP BY  BTTH_CT.NCC_FK \n"+						
						
						//HOA DON NCC
						" UNION ALL \n"+
						
						" SELECT	P.NCC_FK AS NCCID, N'Hóa đơn NCC' AS LOAI, SUM(ISNULL(PS.CO,0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY, \n"+
						"			5 AS STT \n"+
						" FROM		ERP_HOADONNCC HD INNER JOIN ERP_PARK P ON P.PK_SEQ=HD.PARK_FK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON P.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+	
						" WHERE   	P.trangthai = 2 AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' \n"+
						"			AND HD.CONGTY_FK IN ("+obj.getErpCongtyId()+")" +
						"			AND PS.CO > 0 \n"+ 	
						"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) \n"+	
						"			AND (PS.LOAICHUNGTU = N'Duyệt PARK hóa đơn' OR PS.LOAICHUNGTU = N'Duyệt hóa đơn NCC') "+
						" GROUP BY P.NCC_FK "+
						
						" UNION ALL \n"+
						
						" SELECT	CPNK.NCC_KH_FK, N'Nhận hàng' AS LOAI,SUM(ISNULL(PS.CO,0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY \n"+ 
						"			, 6 AS STT \n"+
						" FROM		ERP_NHANHANG CPNK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE		CPNK.trangthai IN (1,2) AND  PS.NGAYCHUNGTU >= '" + obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' \n"+
						"			AND CPNK.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +	
						"			AND PS.LOAICHUNGTU = N'Nhận hàng' \n"+
						"			AND PS.CO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+
						" GROUP BY  CPNK.NCC_KH_FK \n"+
						
						" UNION ALL \n"+	
						
						" SELECT	CPNK.NHACUNGCAP_FK, N'Đề nghị thanh toán' AS LOAI,SUM(ISNULL(PS.CO,0)) AS TANGTRONGKY, 0 AS GIAMTRONGKY,\n"+ 
						"			 7 AS STT \n"+
						" FROM		ERP_MUAHANG CPNK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE		CPNK.trangthai IN (1,2) AND  PS.NGAYCHUNGTU >= '" + obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' \n"+
						"			AND CPNK.CONGTY_FK IN ("+obj.getErpCongtyId()+") " + 	
						"			AND PS.LOAICHUNGTU = N'Chi phí khác' \n"+
						"			AND PS.CO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+
						" GROUP BY  CPNK.NHACUNGCAP_FK "+		
						
						
						"	)SUM  GROUP BY SUM.NCCID \n" +
						")TANGTRONGKY ON TANGTRONGKY.NCCID = PK_SEQ \n" +

						"LEFT JOIN ( \n" +
						"	SELECT NCCID, SUM(GIAMTRONGKY) AS GIAMTRONGKY \n" +
						"	FROM ( \n" +
						//UY NHIEM CHI/PHIEU CHI
						
						"	SELECT	TT.NCC_FK AS NCCID, N'Phiếu chi' AS LOAI, 0 AS TANGTRONGKY, SUM(ISNULL(PS.NO,0)) AS GIAMTRONGKY,  \n"+
						"			6 AS STT \n"+
						"	FROM	ERP_THANHTOANHOADON TT INNER JOIN ERP_THANHTOANHOADON_HOADON TT_HD ON TT.PK_SEQ = TT_HD.THANHTOANHD_FK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON TT.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+	
						" 	WHERE	TT.TRANGTHAI = 1 AND TT_HD.THANHTOANHD_FK = TT.PK_SEQ \n"+
						"			AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' \n"+
						"			AND TT.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +
						"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+
						"			AND PS.LOAICHUNGTU = N'Thanh toán hóa đơn' \n"+
						"			AND PS.NO >0 "+
						"	GROUP BY TT.NCC_FK " +
												
						// BUT TOAN TONG HOP
						" UNION ALL \n"+
						
						" SELECT	BTTH_CT.NCC_FK, N'Bút toán tổng hợp' AS LOAI, 0 AS TANGTRONGKY, SUM(ISNULL(PS.NO, 0)) AS GIAMTRONGKY, 7 AS STT \n"+
						" FROM		ERP_BUTTOANTONGHOP_CHITIET BTTH_CT INNER JOIN ERP_BUTTOANTONGHOP BTTH ON BTTH_CT.BUTTOANTONGHOP_FK = BTTH.PK_SEQ \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+		
						" WHERE		BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "'"+
						"			AND BTTH.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +
						"			AND PS.NO > 0 \n"+
						"			AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' \n"+
						"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+
						" GROUP BY  BTTH_CT.NCC_FK "+													
						
						//GIAM GIA HANG MUA
						" UNION ALL \n"+
						
						" SELECT	GGHM.NCC_FK AS NCCID,N'Tăng/Giảm giá hàng mua' AS LOAI,  0 AS TANGTRONGKY, SUM(ISNULL(PS.NO, 0)) AS GIAMTRONGKY, 8 AS STT \n"+
						" FROM		ERP_GIAMGIAHANGMUA_HOADON GGHM_CT INNER JOIN ERP_GIAMGIAHANGMUA GGHM ON GGHM_CT.giamgia_fk = GGHM.PK_SEQ \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON GGHM.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE		GGHM.TRANGTHAI = '1' AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' \n"+
						"			AND GGHM.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +
						"			AND PS.NO > 0 \n"+
						"			AND PS.LOAICHUNGTU = N'Giảm giá hàng mua' \n"+
						"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+
						" GROUP BY  GGHM.NCC_FK " +
						
						//HOA DON NCC - TIỀN CHIẾT KHẤU < 0
						" UNION ALL \n"+
						
						" SELECT	P.NCC_FK AS NCCID, N'Hóa đơn NCC' AS LOAI, 0 AS TANGTRONGKY, SUM(ISNULL(PS.NO,0)) AS GIAMTRONGKY, \n"+
						"			9 AS STT \n"+
						" FROM		ERP_HOADONNCC HD INNER JOIN ERP_PARK P ON P.PK_SEQ=HD.PARK_FK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON P.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+	
						" WHERE   	P.trangthai = 2 AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' \n"+
						"			AND HD.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +
						"			AND PS.NO > 0 \n"+ 	
						"			AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+	
						"			AND (PS.LOAICHUNGTU = N'Duyệt PARK hóa đơn' OR PS.LOAICHUNGTU = N'Duyệt hóa đơn NCC') "+
						" GROUP BY P.NCC_FK "+
						
						
						//CẤN TRỪ CÔNG NỢ
						" UNION ALL \n"+
						
						" SELECT	CTCN.NCC_FK AS NCCID, N'Cấn trừ công nợ' AS LOAI, 0 AS TANGTRONGKY, SUM(ISNULL(PS.NO,0)) AS GIAMTRONGKY, \n"+
						"			11 AS STT \n"+
						" FROM		ERP_CANTRUCONGNO CTCN \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE		CTCN.trangthai IN (1) AND  PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' \n"+
						"			AND CTCN.CONGTY_FK IN ("+obj.getErpCongtyId()+") " +
						"			AND PS.LOAICHUNGTU = N'Cấn trừ công nợ' \n"+
						"			AND PS.NO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+	
						" GROUP BY  CTCN.NCC_FK "+
						
						// HOA DON TRA VỀ 
						" UNION ALL \n"+
						
						" SELECT	CTCN.NCC_FK AS NCCID, N'Cấn trừ công nợ' AS LOAI, 0 AS TANGTRONGKY, SUM(ISNULL(PS.NO,0)) AS GIAMTRONGKY, "+ 
						" 12 AS STT  "+
						" FROM		ERP_HOADON CTCN "+ 
						" INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU "+ 
						" INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ  "+
						" INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ  "+
						" WHERE		CTCN.trangthai IN (1) AND  PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' \n"+ 
						" AND CTCN.CONGTY_FK IN ("+obj.getErpCongtyId()+")    "+
						" AND PS.LOAICHUNGTU = N'Hóa đơn trả hàng NCC' "+ 
						" AND PS.NO > 0  "+
						" AND CTCN.LOAIHOADON=6 "+
						" AND PS.TAIKHOAN_FK IN (select distinct TAIKHOAN_FK from ERP_NHACUNGCAP WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+") and TAIKHOAN_FK is not null ) "+  	
						" GROUP BY  CTCN.NCC_FK  "+
						
						//THU HỒI TẠM ỨNG
						" UNION ALL \n"+
						"	SELECT	CTCN.NCC_FK AS NCCID,N'Thu hồi tạm ứng' AS LOAI,0 AS TANGTRONGKY, SUM(ISNULL(PS.CO,0)) AS GIAMTRONGKY,  \n"+ 
						"			13 AS STT \n"+ 
						" FROM		ERP_THUTIEN CTCN \n"+ 
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+ 
						"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+ 
						" WHERE	CTCN.trangthai IN (1) AND PS.NGAYCHUNGTU >= '"+obj.gettungay()+ "' AND PS.NGAYCHUNGTU <= '"+obj.getdenngay()+ "' \n"+
						"			AND CTCN.CONGTY_FK IN ( "+obj.getErpCongtyId()+" ) \n"+ 
						"			AND PS.LOAICHUNGTU = N'Thu tiền_Thu hồi tạm ứng' \n"+
						" 		AND CTCN.CONGTY_FK = "+obj.getErpCongtyId()+	
						"			AND PS.CO > 0 \n"+
						"			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN IN ('33100000') ) \n"+
						"  GROUP BY CTCN.NCC_FK \n"+
						
						"	)SUM  GROUP BY SUM.NCCID \n" +
						" )GIAMTRONGKY ON GIAMTRONGKY.NCCID = PK_SEQ \n" +
						" WHERE 1 = 1 \n" ;
			
		if( obj.getLoaiNCCIds().length() > 0){
					query+=" AND  LOAINCC IN (" + obj.getLoaiNCCIds() + ") \n";
		}
		if(obj.getErpNCCId().length() >0){
			query+=" AND  NCC.PK_SEQ IN (" + obj.getErpNCCId() + ") ";
		}
		System.out.println("Get Sql : "+ query);   
	    return query;   
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BaoCaoTongHopCongNoHdNCC.xlsm");
		
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	
		CreateHeader(workbook,obj);
		isFillData = FillData(workbook, obj);
	
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
		
		/*Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, ctyName);
	    
	    cells.setRowHeight(1, 20.0f);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Địa chỉ: " + diachi); 
	    
	    cells.setRowHeight(2, 20.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Mã số thuế: " + masothue); 
	    
	    cell = cells.getCell("D5"); cell.setValue("BÁO CÁO TỔNG HỢP CÔNG NỢ NHÀ CUNG CẤP");
	    
	    cell = cells.getCell("A8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày: ");
	    
	    cell = cells.getCell("B8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, obj.gettungay());

	    cell = cells.getCell("C8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày: " + obj.getdenngay());

	    cell = cells.getCell("A9"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Loại nhà cung cấp: ");
	    
	    cell = cells.getCell("C9"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, obj.getLoaincc());
	    */
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
        }
        else if(leftright.equals("2")){
        	style.setBorderLine(BorderType.TOP, BorderLineType.NONE);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.NONE);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
            cell.setStyle(style);
        }
        else{
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
            cell.setStyle(style);        	
        }
        
	}
	
	private void setStyleColorNormar(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("X1");
		Style style;	
		style = cell1.getStyle();
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        cell.setStyle(style);        
	}
	
	private boolean FillData(Workbook workbook, IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		ResultSet	rs = null;
						
		if(obj.getErpNCCId().length()>0)
		{
			String[] param = new String[4];
			   
			param[0] = obj.gettungay().equals("") ? null : obj.gettungay();
			param[1] = obj.getdenngay().equals("") ? null : obj.getdenngay();
			param[2] = obj.getErpCongtyId().equals("") ? null : obj.getErpCongtyId();
			param[3] = obj.getErpNCCId().equals("") ? null : obj.getErpNCCId();
			
			System.out.println("param0:"+param[0]);
			System.out.println("param1:"+param[1]);
			System.out.println("param2:"+param[2]);
			System.out.println("param2:"+param[3]);
			rs = db.getRsByPro("REPORT_CONGNONCC_HOADON_NCCID", param);
		}
		else
		{
			String[] param = new String[3];
			   
			param[0] = obj.gettungay().equals("") ? null : obj.gettungay();
			param[1] = obj.getdenngay().equals("") ? null : obj.getdenngay();
			param[2] = obj.getErpCongtyId().equals("") ? null : obj.getErpCongtyId();
			
			System.out.println("param0:"+param[0]);
			System.out.println("param1:"+param[1]);
			System.out.println("param2:"+param[2]);
			rs = db.getRsByPro("REPORT_CONGNONCC_HOADON", param);
		}
		
		
		
		int index = 11;
		double totalpsntrongky=0;
		double totalpsctrongky=0;
		double totalnodauky=0;
		double totalcodauky=0;
		double totalnocuoiky=0;
		double totalcocuoiky=0;
		
		
		double dndk = 0;
		double dcdk = 0;
		double psndk = 0;
		double pscdk = 0;
		double dnck = 0;
		double dcck = 0;
		
		double sum_dndk = 0;
		double sum_dcdk = 0;
		double sum_psndk = 0;
		double sum_pscdk = 0;
		double sum_dnck = 0;
		double sum_dcck = 0;
		
		
		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				
				cell = cells.getCell("D" + String.valueOf(5));		cell.setValue("Từ ngày:"+obj.gettungay()+" đến ngày: "+obj.getdenngay());
				this.setStyleColorGray(cells, cell, "2");
				
				String kh1 = "";
				String kh2 = "";
				
				String tenkh1 = "";
				String tenkh2 = "";
				
				while (rs.next())
				{		
					// CỨ HẾT 1 KHÁCH HÀNG LÀ CÓ DÒNG TỔNG CỘNG
					
					System.out.println("kh1:"+kh1+", kh2:"+kh2);
					kh2 = rs.getString("MANCC");
					tenkh2 = rs.getString("TENNCC");
					
					if(!kh1.equals(kh2) && kh1.length() >0 )
					{
						cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(kh1);
						this.setStyleColorGray(cells, cell, "0");
						
						cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(tenkh1 );
						this.setStyleColorGray(cells, cell, "1");
						
						cell = cells.getCell("C" + String.valueOf(index));		cell.setValue("Tổng cộng ");
						this.setStyleColorGray(cells, cell, "1");
												
						cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(sum_dndk);	
						this.setStyleColorGray(cells, cell, "1");
						
						cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(sum_dcdk);	
						this.setStyleColorGray(cells, cell, "1");
						
						cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(sum_psndk);
						this.setStyleColorGray(cells, cell, "1");
						
						cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(sum_pscdk);	//Nhan hang   	6
						this.setStyleColorGray(cells, cell, "1");
						
						cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(sum_dnck);	//Nhan hang   	6
						this.setStyleColorGray(cells, cell, "1");
						
						cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(sum_dcck);	//Nhan hang   	6
						this.setStyleColorGray(cells, cell, "1");
						
						index++;
						
						sum_dndk = 0;
						sum_dcdk = 0;
						sum_psndk = 0;
						sum_pscdk = 0;
						sum_dnck = 0;
						sum_dcck = 0;
					}
											
					dndk = rs.getDouble("DNDK");
					dcdk = rs.getDouble("DCDK");
					
					psndk = rs.getDouble("PSNDK");
					pscdk = rs.getDouble("PSCDK");
					
					sum_dndk += dndk;
					sum_dcdk += dcdk;
					sum_psndk += psndk;
					sum_pscdk += pscdk;
					
					totalnodauky += dndk;
					totalcodauky += dcdk;
					totalpsntrongky += psndk;
					totalpsctrongky += pscdk;
					
					
					double tc =  dcdk - dndk  + pscdk - psndk;
					
					cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(rs.getString("MANCC"));	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("TENNCC"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("SOHOPDONG"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(dndk);	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(dcdk);
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(psndk);
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(pscdk);	//Nhan hang   	6
					this.setStyleColorNormar(cells, cell);
					
					if(tc >= 0 ) // NỢ LỚN HƠN CÓ
					{
						cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(0);	//Nhan hang   	6
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(tc);	//Nhan hang   	6
						this.setStyleColorNormar(cells, cell);
						
						sum_dcck += tc;
						
						totalcocuoiky += tc;
					}
					
					else
					{
						cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(tc);	//Nhan hang   	6
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(0);	//Nhan hang   	6
						this.setStyleColorNormar(cells, cell);
						
						sum_dnck += tc;
						totalnocuoiky += tc;
					}
					
					index++;	
					
					kh1 = kh2;
					tenkh1 = tenkh2;
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
				
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(totalnodauky);	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(totalcodauky);	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(totalpsntrongky);
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(totalpsctrongky);	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(totalnocuoiky);	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(totalcocuoiky);	
				this.setStyleColorGray(cells, cell, "1");
				
				index=index+3;
				
				cell = cells.getCell("B" + String.valueOf(index));	
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Lập biểu");

				cell = cells.getCell("F" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Giám đốc");
				
				index=index+5;
				cell = cells.getCell("B" + String.valueOf(index));
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
				
				cell = cells.getCell("F" + String.valueOf(index));
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
		
		    	
			}
			catch(Exception ex)
			{
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
	
	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String name = sdf.format(cal.getTime());
		name = name.replaceAll("-", "");
		name = name.replaceAll(" ", "_");
		name = name.replaceAll(":", "");
		return "_" + name;
		
	}
}
