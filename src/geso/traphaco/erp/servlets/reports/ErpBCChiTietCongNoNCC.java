package geso.traphaco.erp.servlets.reports;

import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpBCChiTietCongNoNCC extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpBCChiTietCongNoNCC() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		String ctyId = (String)session.getAttribute("congtyId");
		Utility util = new Utility();
		
		obj.setuserId(util.getUserId(querystring));
		obj.setErpCongtyId(ctyId);
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setnppdangnhap(util.getIdNhapp(util.getUserId(querystring)));
		
		//null thi se lay khach hang trong csdl
		obj.setRsKhErp(null);
		
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiTietCongNoNCC.jsp";
		
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
		//System.out.println("cong ty nek: "+ctyId);
		
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
		obj.setnppdangnhap(userId);
		
		obj.setErpKhachHangId(util.antiSQLInspection(request.getParameter("khid")));
		obj.setRsKhErp(null);
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiTietCongNoNCC.jsp";
		obj.InitErp();
		
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChiTietCongNoNCC.xlsm");
			
	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Khong co du lieu trong thoi gian da chon");
				    writer.close();
				}
			} 
	        catch (Exception e) 
	        {
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				//System.out.println(e.toString());
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
	
	public String getQuery(IStockintransit obj)
	{
		String kh = null;
		if(!obj.getErpKhachHangId().equals("")){
			 kh = obj.getErpKhachHangId().split(" - ")[0];		
		 }
		
		String query = "";

				//NHẬN HÀNG
		query += "\n SELECT	N'Chi phí nhận hàng' AS LOAI,'' AS SOHOADON, CAST( CPNK.pk_seq AS NVARCHAR(50))ID ,ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, " 
				+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CPNK.NGAY, '' TENSP, '' SOPO , 1 AS STT "
				+"\n FROM		ERP_CHIPHINHAPKHAU CPNK \n"
				+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "
				+"\n WHERE		CPNK.trangthai = '1' AND  PS.NGAYCHUNGTU >= '" + obj.gettungay() + "' AND  PS.NGAYCHUNGTU <= '" + obj.getdenngay() + "' "
				+"\n			AND CPNK.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") " 
				+"\n			AND PS.LOAICHUNGTU = N'Chi phí nhập khẩu' "
				+"\n			AND PS.CO > 0 "
				+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
				
				if(kh!=null)
						query += "  AND CPNK.NCCID_CN = '"+kh+"'";	//AND PS.MADOITUONG = '"+kh+"'
		
				//THUE NHAP KHAU
		query+=
				"\n	UNION ALL "
				
				+"\n	SELECT	N'Thuế nhập khẩu' AS LOAI, '' AS SOHOADON, CAST(TNK.PK_SEQ AS NVARCHAR(50)) ID, ROUND(ISNULL(PS.CO, 0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "
				+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,TNK.NGAY,'' TENSP, '' SOPO ,2 AS STT "
				+"\n	FROM	ERP_THUENHAPKHAU TNK "
				+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON TNK.PK_SEQ = PS.SOCHUNGTU "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "
				+"\n	WHERE	TNK.TRANGTHAI IN (1,2) AND PS.NGAYCHUNGTU >= '" + obj.gettungay() + "' AND  PS.NGAYCHUNGTU <= '" + obj.getdenngay() + "' "
				+"\n			AND TNK.CONGTY_FK IN ("+obj.getErpCongtyId()+ ") " 
				+"\n			AND (PS.LOAICHUNGTU = N'Thuế nhập khẩu' OR PS.LOAICHUNGTU = N'Thuế VAT nhập khẩu' ) "
				+"\n			AND PS.CO > 0 "
				+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
				
				if(kh!=null)
					query+= "\n	AND TNK.NCC_FK = '"+kh+"'"; // AND PS.MADOITUONG = '"+kh+"'
				
				//CHI PHI KHAC
		query+=	"\n UNION ALL "
				
				+"\n SELECT	N'Chi phí khác' AS LOAI, '' AS SOHOADON, CAST(CPK.PK_SEQ AS NVARCHAR(50)) ID, ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "
				+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, CPK.NGAY, '' TENSP ,'' SOPO ,3 AS STT "
				+"\n FROM		ERP_CHIPHIKHAC CPK "
				+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CPK.PK_SEQ = PS.SOCHUNGTU "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "
				+"\n WHERE		CPK.TRANGTHAI = '1' AND CPK.LOAI = 0 "
				+"\n			AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' "
				+"\n			AND CPK.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") "  //AND CPK.NPP_FK = "+obj.getnppdangnhap()+
				+"\n			AND PS.LOAICHUNGTU = N'Chi phí khác' "
				+"\n			AND PS.CO > 0 "
				+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
				
				if(kh!=null)
					query += "\n		AND CPK.DOITUONG = '"+kh +"'"; //		AND PS.MADOITUONG = '"+kh+"' 
			
				// BUTTOANTONGHOP
		query +=
				"\n UNION ALL "
		
				+"\n SELECT	N'Bút toán tổng hợp' AS LOAI, '' AS SOHOADON, CAST(BTTH.MACHUNGTU AS NVARCHAR(50)) ID, ROUND(ISNULL(PS.CO, 0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "
				+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, BTTH.NGAYBUTTOAN AS NGAY ,'' TENSP,'' SOPO,4 AS STT "
				+"\n FROM		ERP_BUTTOANTONGHOP BTTH "
				+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH.PK_SEQ = PS.SOCHUNGTU "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "		
				+"\n WHERE		BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' "
				+"\n			AND BTTH.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") "  //AND BTTH.NPP_FK = "+obj.getnppdangnhap()+
				+"\n			AND PS.CO > 0 "
				+"\n			AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' "
				+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
		
				if(kh!=null)
					query+= "\n AND PS.MADOITUONG = '"+kh+"' ";// AND PS.MADOITUONG = '"+kh+"' 
		
		// HOA DON TRA VE NCC
		query+=
				"\n UNION ALL "			
		 
				+"\n SELECT	N'Hóa đơn trả hàng NCC' AS LOAI, P.SOHOADON AS SOHOADON, CAST(P.pk_seq AS NVARCHAR(50)) ID, ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, ISNULL(PS.NO,0)  AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "  
				+"\n TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,PS.NGAYCHUNGTU AS NGAY ,'' TENSP,'' SOPO,5 AS STT   "
				+"\n FROM		ERP_HOADON P    "
				+"\n INNER JOIN ERP_PHATSINHKETOAN PS ON P.PK_SEQ = PS.SOCHUNGTU "  
				+"\n INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ   "
				+"\n INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "	
				+"\n WHERE   	P.trangthai = 1     AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "'  "
				+"\n AND P.CONGTY_FK IN ("+obj.getErpCongtyId()+") "
				+"\n AND PS.NO >0   "
				+"\n AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) "
				+"\n AND ( PS.LOAICHUNGTU = N'Hóa đơn trả hàng NCC'  ) " ;
			
				if(kh!=null)
					query+=" AND P.NCC_FK = '"+kh+"'"; //AND PS.MADOITUONG = '"+kh+"' 
			
			//HOA DON NCC
		query+=
				"\n UNION ALL "
		
				 +"\n SELECT	N'Hóa đơn NCC' AS LOAI, HD.SOHOADON AS SOHOADON, CAST(P.pk_seq AS NVARCHAR(50)) ID, ROUND(ISNULL(PS.NO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK_DU.SOHIEUTAIKHOAN, " 
				 +"\n			TK.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,PS.NGAYCHUNGTU AS NGAY , "
				 +"\n 			'' AS TENSP , '' SOPO, 5 AS STT " 
				 +"\n   FROM ERP_HOADONNCC HD INNER JOIN ERP_PARK P ON P.PK_SEQ=HD.PARK_FK " 
				 +"\n		INNER JOIN ERP_PHATSINHKETOAN PS ON P.PK_SEQ = PS.SOCHUNGTU " 
				 +"\n		INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ " 
				 +"\n		INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ " 
				 +"\n 	WHERE P.trangthai = 2 AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' "
				 +"\n		  AND HD.CONGTY_FK IN ("+obj.getErpCongtyId()+ ") " //AND HD.NPP_FK = "+obj.getnppdangnhap()+
				 +"\n		  AND PS.NO >0 " 	
				 +"\n		  AND PS.TAIKHOANDOIUNG_FK IN  (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) "
				 +"\n		  AND ( PS.LOAICHUNGTU = N'Duyệt hóa đơn NCC' )";
			
				if(kh!=null)
					query+="\n AND P.NCC_FK = '"+kh+"'"; 
				
		
				//UY NHIEM CHI/PHIEU CHI

		query+= "\n	UNION ALL "
		
				+"\n	SELECT	N'Phiếu thanh toán' AS LOAI, '' AS SOHOADON, CAST(TT.MACHUNGTU AS NVARCHAR(50)) ID, 0 AS TANGTRONGKY, ROUND(ISNULL(PS.NO,0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "
				+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, PS.NGAYCHUNGTU AS NGAY ,'' TENSP , '' SOPO,6 AS STT "
				+"\n	FROM	ERP_THANHTOANHOADON TT "
				+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON TT.PK_SEQ = PS.SOCHUNGTU "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "	
				+"\n 	WHERE	TT.TRANGTHAI = 1 "
				+"\n			AND TT.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") "  //AND TT.NPP_FK = "+obj.getnppdangnhap()+
				+"\n			AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' "
				+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) "
				+"\n			AND PS.LOAICHUNGTU = N'Thanh toán hóa đơn' \n"
				+"\n			AND PS.NO >0 ";
		
		if(kh!=null)
			query += "\n AND TT.NCC_FK = '"+kh+"' "; // AND PS.MADOITUONG = '"+kh+"'
					
				// BUT TOAN TONG HOP

		query+=	"\n UNION ALL "		
				+"\n SELECT	N'Bút toán tổng hợp' AS LOAI, '' AS SOHOADON, CAST( BTTH.MACHUNGTU AS NVARCHAR(50)) ID, 0 AS TANGTRONGKY, ROUND(ISNULL(PS.NO, 0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "
				+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, PS.NGAYCHUNGTU AS NGAY ,'' TENSP ,'' SOPO ,7 AS STT "
				+"\n FROM		ERP_BUTTOANTONGHOP BTTH "
				+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH.PK_SEQ = PS.SOCHUNGTU "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "		
				+"\n WHERE		BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "'"
				+"\n			AND PS.NO > 0 "
				+"\n			AND BTTH.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") "  //AND BTTH.NPP_FK = "+obj.getnppdangnhap()
				+"\n			AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' "
				+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
				if(kh!=null)
					query+= "\n	AND PS.MADOITUONG = '"+kh+"'"; //	AND PS.MADOITUONG = '"+kh+"' 
					
		//GIAM GIA HANG MUA

		query+=	" UNION ALL "		
				+"\n SELECT	N'Tăng/Giảm giá hàng mua' AS LOAI, '' AS SOHOADON, CAST(GGHM.PK_SEQ AS NVARCHAR(50)) ID, 0 AS TANGTRONGKY, ROUND(ISNULL(PS.NO, 0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "
				+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, PS.NGAYCHUNGTU AS NGAY ,'' TENSP,'' SOPO ,8 AS STT "
				+"\n FROM		ERP_GIAMGIAHANGMUA GGHM "
				+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON GGHM.PK_SEQ = PS.SOCHUNGTU "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "
				+"\n WHERE		GGHM.TRANGTHAI = '1' AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' "
				+"\n			AND PS.NO > 0 "
				+"\n			AND GGHM.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") " //AND GGHM.NPP_FK = "+obj.getnppdangnhap()+
				+"\n			AND PS.LOAICHUNGTU = N'Giảm giá hàng mua' "
				+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
		
				if(kh!=null)
					query+= "\n	AND GGHM.NCC_FK  = '"+kh+"'"; //	AND PS.MADOITUONG = '"+kh+"' 
	
		
		//CHI PHÍ NHẬN HÀNG
		query+=	"\n UNION ALL "	
		
				+"\n  SELECT	N'Nhận hàng' AS LOAI,'' AS SOHOADON, CAST(CPNK.PK_SEQ AS NVARCHAR(50)) ID ,ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, " 
				+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CPNK.NGAYCHOT,'' TENSP,'' SOPO ,9 AS STT "
				+"\n FROM		ERP_NHANHANG CPNK "
				+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "
				+"\n WHERE		CPNK.trangthai IN (1,2) AND  PS.NGAYCHUNGTU >= '" + obj.gettungay() + "' AND  PS.NGAYCHUNGTU <= '" + obj.getdenngay() + "' "				
				+"\n			AND PS.LOAICHUNGTU = N'Nhận hàng' "
				+"\n			AND CPNK.CONGTY_FK IN ( "+obj.getErpCongtyId()+ " )"  //AND CPNK.NHAPHANPHOI_FK = "+obj.getnppdangnhap()+
				+"\n			AND PS.CO > 0 "
				+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
			
				if(kh!=null)
					query += "\n  AND CPNK.NCC_KH_FK = '"+kh+"'";	//AND PS.MADOITUONG = '"+kh+"'
			
		
		//ĐỀ NGHỊ THANH TOÁN
		query+="\n UNION ALL "
		
				+"\n SELECT	N'Đề nghị thanh toán' AS LOAI,'' AS SOHOADON, CAST( CPNK.pk_seq AS NVARCHAR(50) ) ID ,ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, " 
				+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CPNK.NGAYMUA,'' TENSP,'' SOPO, 10 AS STT "
				+"\n FROM		ERP_MUAHANG CPNK "
				+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
				+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "
				+"\n WHERE		CPNK.trangthai IN (1,2) AND  PS.NGAYCHUNGTU >= '" + obj.gettungay() + "' AND  PS.NGAYCHUNGTU <= '" + obj.getdenngay() + "' "				
				+"\n			AND PS.LOAICHUNGTU = N'Chi phí khác' "
				+"\n			AND CPNK.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") "  //AND CPNK.NHAPHANPHOI_FK = "+obj.getnppdangnhap()+
				+"\n			AND PS.CO > 0 "
				+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
				
				if(kh!=null)
					query += "\n  AND CPNK.NHACUNGCAP_FK = '"+kh+"'";	//AND PS.MADOITUONG = '"+kh+"'
		
		
		//HOA DON NCC

		query+="\n UNION ALL "
		
				 +"\n SELECT	N'Hóa đơn NCC' AS LOAI, HD.SOHOADON AS SOHOADON, CAST( P.pk_seq AS NVARCHAR(50) ) ID, 0 AS TANGTRONGKY, ROUND(ISNULL(PS.CO,0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK_DU.SOHIEUTAIKHOAN, "
				 +"\n			TK.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,PS.NGAYCHUNGTU AS NGAY ,SP.MA +' - ' +SP.TEN TENSP," 
				 +"\n			'' SOPO ,11 AS STT "
				 +"\n FROM		ERP_HOADONNCC HD INNER JOIN ERP_PARK P ON P.PK_SEQ=HD.PARK_FK "
				 +"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON P.PK_SEQ = PS.SOCHUNGTU "
				 +"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
				 +"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "
				 +"\n			INNER JOIN SANPHAM SP ON SP.PK_SEQ = CAST(PS.MADOITUONG AS NUMERIC(18,0)) "
				 +"\n WHERE   	P.trangthai = 2 AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' "
				 +"\n			AND HD.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") " 
				 +"\n			AND PS.CO >0 " 			
				 +"\n			AND PS.TAIKHOANDOIUNG_FK IN  (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) "
				 +"\n			AND (PS.LOAICHUNGTU = N'Duyệt hóa đơn NCC' )";
				
				if(kh!=null)
					query+="\n  AND P.NCC_FK = '"+kh+"'"; //AND PS.MADOITUONG = '"+kh+"'	
				
				
		query+="\n UNION ALL "
						
				 +"\n SELECT	N'Hóa đơn NCC' AS LOAI, HD.SOHOADON AS SOHOADON, CAST( P.pk_seq AS NVARCHAR(50) ) ID, 0 AS TANGTRONGKY, ROUND(ISNULL(PS.CO,0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK_DU.SOHIEUTAIKHOAN, "
				 +"\n			TK.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,PS.NGAYCHUNGTU AS NGAY ,SP.MA +' - ' +SP.TEN TENSP," 
				 +"\n			'' SOPO ,11 AS STT "
				 +"\n FROM		ERP_HOADONNCC HD INNER JOIN ERP_PARK P ON P.PK_SEQ=HD.PARK_FK "
				 +"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON P.PK_SEQ = PS.SOCHUNGTU "
				 +"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
				 +"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "
				 +"\n			INNER JOIN SANPHAM SP ON SP.PK_SEQ = CAST(PS.MADOITUONG AS NUMERIC(18,0)) "
				 +"\n WHERE   	P.trangthai = 2 AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' "
				 +"\n			AND HD.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") " 
				 +"\n			AND PS.CO >0 " 			
				 +"\n			AND PS.TAIKHOANDOIUNG_FK IN  (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) "
				 +"\n			AND (PS.LOAICHUNGTU = N'Duyệt hóa đơn NCC' )";
				
				if(kh!=null)
					query+="\n  AND P.NCC_FK = '"+kh+"'"; //AND PS.MADOITUONG = '"+kh+"'			
		// CẤN TRỪ CÔNG NỢ
		query+=
				"\n UNION ALL "+	
				
				"\n SELECT	N'Cấn trừ công nợ' AS LOAI,'' AS SOHOADON, CAST( CTCN.pk_seq AS NVARCHAR(50)) ID ,0 AS TANGTRONGKY, ROUND(ISNULL(PS.NO,0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "+ 
				"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CTCN.NGAYCANTRU,'' TENSP,'' SOPO , 12 AS STT "+
				"\n FROM		ERP_CANTRUCONGNO CTCN "+
				"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU "+
				"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "+
				"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "+
				"\n WHERE		CTCN.trangthai IN (1) AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' "+
				"\n			AND CTCN.CONGTY_FK IN ( "+obj.getErpCongtyId()+ " ) " + //AND CTCN.NPP_FK = "+obj.getnppdangnhap()+
				"\n			AND PS.LOAICHUNGTU = N'Cấn trừ công nợ' "+
				"\n			AND PS.NO > 0 "+
				"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
				
				if(kh!=null)
						query += " AND CTCN.NCC_FK = '"+kh+"'";	// AND PS.MADOITUONG = '"+kh+"'
		
		
		// THU HỒI TẠM ỨNG
		
		query+=
				"\n UNION ALL "+
				"\n	SELECT	N'Thu hồi tạm ứng' AS LOAI,'' AS SOHOADON, CAST( CTCN.MACHUNGTU AS NVARCHAR(50)) ID , ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "+ 
				"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CTCN.NGAYCHUNGTU,'' TENSP,'' SOPO , 13 AS STT "+ 
				"\n FROM	ERP_THUTIEN CTCN "+ 
				"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU "+
				"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "+ 
				"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "+ 
				"\n WHERE		CTCN.trangthai IN (1) AND PS.NGAYCHUNGTU >= '"+obj.gettungay()+"' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay()+"' "+ 
				"\n			AND CTCN.CONGTY_FK IN ( "+obj.getErpCongtyId()+" ) "+ 
				"\n			AND PS.LOAICHUNGTU = N'Thu tiền_Thu hồi tạm ứng' "+ 
				"\n			AND PS.CO > 0 "+ 
				"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
		
				if(kh!=null)
					query+= " AND CTCN.NCC_FK = '"+kh+"' ";
		
		
		// THU KHÁC
		query+=
				"\n UNION ALL "+
				"\n	SELECT	N'Thu khác' AS LOAI,'' AS SOHOADON, CAST( CTCN.MACHUNGTU AS NVARCHAR(50)) ID ,ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "+ 
				"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CTCN.NGAYCHUNGTU,'' TENSP,'' SOPO , 13 AS STT "+ 
				"\n FROM	ERP_THUTIEN CTCN "+ 
				"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU "+
				"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "+ 
				"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "+ 
				"\n WHERE		CTCN.trangthai IN (1) AND PS.NGAYCHUNGTU >= '"+obj.gettungay()+"' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay()+"' "+ 
				"\n			AND CTCN.CONGTY_FK IN ( "+obj.getErpCongtyId()+" ) "+ 
				"\n			AND PS.LOAICHUNGTU = N'Thu khác' "+ 
				"\n			AND PS.CO > 0 "+ 
				"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
			
				if(kh!=null)
					query+= " AND  PS.MADOITUONG = '"+kh+"' ";
			
		
		// Trả khác
		query+=
				"\n UNION ALL "+
				"\n	SELECT	N'Chi khác' AS LOAI,'' AS SOHOADON, CAST( CTCN.MACHUNGTU AS NVARCHAR(50)) ID , 0 AS TANGTRONGKY, ROUND(ISNULL(PS.NO,0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "+ 
				"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CTCN.NGAYGHINHAN,'' TENSP,'' SOPO , 13 AS STT "+ 
				"\n FROM	ERP_THANHTOANHOADON CTCN "+ 
				"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU "+
				"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "+ 
				"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "+ 
				"\n WHERE		CTCN.trangthai IN (1) AND PS.NGAYCHUNGTU >= '"+obj.gettungay()+"' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay()+"' "+ 
				"\n			AND CTCN.CONGTY_FK IN ( "+obj.getErpCongtyId()+" ) "+ 
				"\n			AND PS.LOAICHUNGTU = N'Trả khác' "+ 
				"\n			AND PS.NO > 0 "+ 
				"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
			
				if(kh!=null)
					query+= " AND  PS.MADOITUONG = '"+kh+"' ";

				// HOA DON DIEU CHINH TANG GIAM NHA CUNG CAP
						
		query+="\n UNION ALL "
						
				 +"\n SELECT	N'Hóa đơn điều chỉnh NCC' AS LOAI, HD.SOHOADON AS SOHOADON, CAST( HD.pk_seq AS NVARCHAR(50) ) ID, ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, ROUND(ISNULL(PS.NO,0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK_DU.SOHIEUTAIKHOAN, "
				 +"\n			TK.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,PS.NGAYCHUNGTU AS NGAY ,SP.MA +' - ' +SP.TEN TENSP," 
				 +"\n			'' SOPO ,14 AS STT "
				 +"\n FROM ERP_HOADONKHACNCC HD INNER JOIN ERP_HOADONKHACNCC_SANPHAM HDSP ON HD.PK_SEQ = HDSP.HOADONKHACNCC_FK "
				 +"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON HD.PK_SEQ = PS.SOCHUNGTU "
				 +"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
				 +"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "
				 +"\n			INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = HDSP.SANPHAM_FK "
				 +"\n WHERE   	HD.trangthai = 1 AND PS.NGAYCHUNGTU >= '"+ obj.gettungay() + "' AND PS.NGAYCHUNGTU <='"+ obj.getdenngay() + "' "
				 +"\n			AND HD.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") " 		
				 +"\n			AND PS.TAIKHOANDOIUNG_FK IN  (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) "
				 +"\n			AND (PS.LOAICHUNGTU = N'Hóa đơn điều chỉnh giảm giá NCC' ) ";		

		
		query += " ORDER BY NGAY";
		
		System.out.println("TRONG KỲ : "+ query);
	    return query;   
	}
	
	public String getDauky(IStockintransit obj)
	{
		String kh = null;
		if(!obj.getErpKhachHangId().equals("")){
			 kh = obj.getErpKhachHangId().split(" - ")[0];		
		 }
		
		dbutils db = new dbutils();
		String month = obj.gettungay().substring(5, 7);
		String year = obj.gettungay().substring(0, 4);	
		//Chọn tháng khóa sổ gần nhất
		String sqlKhoaSo= "SELECT DISTINCT TOP 1 NAM,THANG FROM ERP_TAIKHOAN_NOCO_KHOASO \n" +
				 "ORDER BY NAM DESC,THANG DESC";
		ResultSet rsKhoaSo = db.get(sqlKhoaSo);
		int thangKS = 0;
		int namKS = 0;
		if(rsKhoaSo!= null){
			try {
				while(rsKhoaSo.next()){
					thangKS = rsKhoaSo.getInt("THANG");
					namKS = rsKhoaSo.getInt("NAM");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				String error = "[ERROR 1.1] Không thể lấy tháng KS gần nhất : "+ e.toString();
				System.out.println(error);
			}
		}
		
		
		int lastyear = Integer.parseInt(year) - 1;
		int lastmonth = 0;
		
		if (Integer.parseInt(month) > 1){
			lastmonth = Integer.parseInt(month) - 1;
		}else{
			lastmonth = 12;
		}
			
		int thangdauky = 0;
		int namdauky = 0;
		if(lastmonth != 12){
			thangdauky = lastmonth;
			namdauky = Integer.parseInt(year);
		}else{
			thangdauky = lastmonth;
			namdauky = lastyear;
		}
		
		if (thangKS != 0 && namKS != 0){
			if(thangKS >= thangdauky && namKS >= namdauky){
				
			}
			else {
				thangdauky = thangKS;
				namdauky = namKS;
			}
		}
		String thangDauKy = "0"+Integer.toString(thangdauky);
		thangDauKy.substring(thangDauKy.length() - 1, thangDauKy.length());
		
		String query;
		query = 
		"	SELECT NCCID, ISNULL(SUM(TANGTRONGKY - GIAMTRONGKY), 0) AS DAUKY \n" +
		"	FROM ( \n" +
		" 			SELECT ROUND(ISNULL(KS.GIATRICOVND,0),0) AS TANGTRONGKY,ROUND(ISNULL(KS.GIATRINOVND,0),0) AS GIAMTRONGKY, \n " + 
		" 			NCC.PK_SEQ AS NCCID \n " + 
		" 			FROM ERP_TAIKHOAN_NOCO_KHOASO KS INNER JOIN ERP_TAIKHOANKT TK ON KS.TAIKHOANKT_FK = TK.PK_SEQ \n " + 
		" 			INNER JOIN ERP_NHACUNGCAP NCC ON KS.MADOITUONG = CAST(NCC.PK_SEQ AS NVARCHAR(50)) \n " + 
		" 			WHERE KS.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN IN ( '33111000', '33112000' ) AND CONGTY_FK IN ( "+obj.getErpCongtyId()+") )  \n " + 
		" 			AND KS.THANG = "+thangdauky+"  AND KS.NAM = "+namdauky+"  AND  NCC.PK_SEQ IN (" + kh + ")  \n " + 
		" 			UNION ALL \n " + 
		"			SELECT  ROUND(ISNULL(PS.CO,0),0) TANGTRONGKY , ROUND(ISNULL(PS.NO, 0),0) GIAMTRONGKY, \n"+
		"			NCC.PK_SEQ NCCID \n"+
		"			FROM ERP_PHATSINHKETOAN PS INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
		"			INNER JOIN ERP_NHACUNGCAP NCC ON PS.MADOITUONG = CAST( NCC.PK_SEQ AS NVARCHAR(50)) \n"+
		"			WHERE PS.TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN IN ( '33111000', '33112000' ) AND CONGTY_FK IN ( "+obj.getErpCongtyId()+") ) \n"+
		//"			AND DOITUONG = N'Nhà cung cấp' \n"+
		"   		AND PS.NGAYGHINHAN >'"+namdauky+"-"+ thangDauKy +"-32' AND PS.NGAYGHINHAN < '"+ obj.gettungay() +"'" + "  AND  NCC.PK_SEQ IN (" + kh + ") \n"+
		/*//CHI PHÍ NHẬN HÀNG - TĂNG TRONG KỲ
		
	 	" SELECT	cast(CPNK.NCCID_CN as nvarchar(50)) AS NCCID ,N'Chi phí nhận hàng' AS LOAI,'' AS SOHOADON, CAST( CPNK.pk_seq AS NVARCHAR(50))ID ,ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, \n"+ 
		"			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CPNK.NGAY, '' TENSP, '' SOPO , 1 AS STT \n"+
		" FROM		ERP_CHIPHINHAPKHAU CPNK \n"+
		"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
		" WHERE		CPNK.trangthai = '1' AND  PS.NGAYCHUNGTU < '" + obj.gettungay() + "' \n"+
		"			AND CPNK.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") \n" +
		"			AND PS.LOAICHUNGTU = N'Chi phí nhập khẩu' \n"+
		"			AND PS.CO > 0 \n"+
		"			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) \n"+
										
		//THUE NHAP KHAU - TĂNG TRONG KỲ
		"	UNION ALL \n"+
		
		"	SELECT	cast(TNK.NCC_FK as nvarchar(50)) AS NCCID ,N'Thuế nhập khẩu' AS LOAI, '' AS SOHOADON, CAST(TNK.PK_SEQ AS NVARCHAR(50)) ID, ROUND(ISNULL(PS.CO, 0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, \n"+
		"			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,TNK.NGAY,'' TENSP, '' SOPO ,2 AS STT \n"+
		"	FROM	ERP_THUENHAPKHAU TNK \n"+
		"			INNER JOIN ERP_PHATSINHKETOAN PS ON TNK.PK_SEQ = PS.SOCHUNGTU \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
		"	WHERE	TNK.TRANGTHAI IN (1,2) AND PS.NGAYCHUNGTU < '" + obj.gettungay() + "' "+
		"			AND TNK.CONGTY_FK IN ("+obj.getErpCongtyId()+ ") " +
		"			AND (PS.LOAICHUNGTU = N'Thuế nhập khẩu' OR PS.LOAICHUNGTU = N'Thuế VAT nhập khẩu' ) "+
		"			AND PS.CO > 0 "+
		"			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) "+
								
		//CHI PHI KHAC - TĂNG TRONG KỲ
		" UNION ALL \n"+

		" SELECT	cast(CPK.DOITUONG as nvarchar(50)) AS NCCID , N'Chi phí khác' AS LOAI, '' AS SOHOADON, CAST(CPK.PK_SEQ AS NVARCHAR(50)) ID, ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, \n"+
		"			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, CPK.NGAY, '' TENSP ,'' SOPO ,3 AS STT \n"+
		" FROM		ERP_CHIPHIKHAC CPK \n"+
		"			INNER JOIN ERP_PHATSINHKETOAN PS ON CPK.PK_SEQ = PS.SOCHUNGTU \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
		" WHERE		CPK.TRANGTHAI = '1' AND CPK.LOAI = 0 \n"+
		"			AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "' \n"+
		"			AND CPK.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") \n"+  //AND CPK.NPP_FK = "+obj.getnppdangnhap()+
		"			AND PS.LOAICHUNGTU = N'Chi phí khác' \n"+
		"			AND PS.CO > 0 \n"+
		"			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) \n"+
		
		// BUTTOANTONGHOP - TĂNG TRONG KỲ
		" UNION ALL \n"+
		
		" SELECT	cast(PS.MADOITUONG as nvarchar(50)) AS NCCID, N'Bút toán tổng hợp' AS LOAI, '' AS SOHOADON, CAST(BTTH.PK_SEQ AS NVARCHAR(50)) ID, ROUND(ISNULL(PS.CO, 0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, \n"+
		"			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, BTTH.NGAYBUTTOAN AS NGAY ,'' TENSP,'' SOPO,4 AS STT \n"+
		" FROM		ERP_BUTTOANTONGHOP BTTH \n"+
		"			INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH.PK_SEQ = PS.SOCHUNGTU \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
		"			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+		
		" WHERE		BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "'  \n"+
		"			AND BTTH.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") \n"+  //AND BTTH.NPP_FK = "+obj.getnppdangnhap()+
		"			AND PS.CO > 0 \n"+
		"			AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' \n"+
		"			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) "+
		
		//HOA DON TRA VE NCC - TĂNG TRONG KỲ
		" UNION ALL \n"+			
 
		" SELECT	cast (P.NCC_FK as nvarchar(50)) AS NCCID, N'Hóa đơn trả hàng NCC' AS LOAI, P.SOHOADON AS SOHOADON, CAST(P.pk_seq AS NVARCHAR(50)) ID, ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, ISNULL(PS.NO,0)  AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, \n"+  
		" 			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,PS.NGAYCHUNGTU AS NGAY ,'' TENSP,'' SOPO,5 AS STT   \n"+
		" FROM		ERP_HOADON P    \n"+
		" 			INNER JOIN ERP_PHATSINHKETOAN PS ON P.PK_SEQ = PS.SOCHUNGTU \n"+ 
		" 			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ   \n"+
		" 			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+	
		" WHERE   	P.trangthai = 1  AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "' \n"+
		" AND P.CONGTY_FK IN ("+obj.getErpCongtyId()+") \n"+
		" AND PS.NO >0   \n"+
		" AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) \n"+
		" AND ( PS.LOAICHUNGTU = N'Hóa đơn trả hàng NCC'  ) \n"+					
										
		//HOA DON NCC - TĂNG TRONG KỲ

		"\n UNION ALL "

		 +"\n SELECT	cast(P.NCC_FK as nvarchar(50)) AS NCCID, N'Hóa đơn NCC' AS LOAI, HD.SOHOADON AS SOHOADON, CAST(P.pk_seq AS NVARCHAR(50)) ID, ROUND(ISNULL(PS.NO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK_DU.SOHIEUTAIKHOAN, " 
		 +"\n			TK.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,PS.NGAYCHUNGTU AS NGAY , "
		 +"\n			'' AS TENSP , "					
		 +"\n		'' SOPO,5 AS STT " 
		 +"\n   FROM ERP_HOADONNCC HD INNER JOIN ERP_PARK P ON P.PK_SEQ=HD.PARK_FK " 
		 +"\n		INNER JOIN ERP_PHATSINHKETOAN PS ON P.PK_SEQ = PS.SOCHUNGTU " 
		 +"\n		INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ " 
		 +"\n		INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ " 
		 +"\n 	WHERE P.trangthai = 2 AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "'  "
		 +"\n		  AND HD.CONGTY_FK IN ("+obj.getErpCongtyId()+ ") " //AND HD.NPP_FK = "+obj.getnppdangnhap()+
		 +"\n		  AND PS.NO >0 " 	
		 +"\n		  AND PS.TAIKHOANDOIUNG_FK IN  (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) "
		 +"\n		  AND ( PS.LOAICHUNGTU = N'Duyệt hóa đơn NCC' )";
							
		//CHI PHÍ NHẬN HÀNG - TĂNG TRONG KỲ
query+=	"\n UNION ALL "	

		+"\n  SELECT	cast(CPNK.NCC_KH_FK as nvarchar(50)) AS NCCID, N'Nhận hàng' AS LOAI,'' AS SOHOADON, CAST(CPNK.pk_seq AS NVARCHAR(50)) ID ,ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, " 
		+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CPNK.NGAYCHOT,'' TENSP,'' SOPO ,9 AS STT "
		+"\n FROM		ERP_NHANHANG CPNK "
		+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU "
		+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
		+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "
		+"\n WHERE		CPNK.trangthai IN (1,2) AND  PS.NGAYCHUNGTU < '" + obj.gettungay() + "' "				
		+"\n			AND PS.LOAICHUNGTU = N'Nhận hàng' "
		+"\n			AND CPNK.CONGTY_FK IN ( "+obj.getErpCongtyId()+ " )"  //AND CPNK.NHAPHANPHOI_FK = "+obj.getnppdangnhap()+
		+"\n			AND PS.CO > 0 "
		+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
	

//ĐỀ NGHỊ THANH TOÁN - TĂNG TRONG KỲ
query+="\n UNION ALL "

		+"\n SELECT		cast(CPNK.NHACUNGCAP_FK as nvarchar(50)) AS NCCID, N'Đề nghị thanh toán' AS LOAI,'' AS SOHOADON, CAST( CPNK.pk_seq AS NVARCHAR(50) ) ID ,ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, " 
		+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CPNK.NGAYMUA,'' TENSP,'' SOPO, 10 AS STT "
		+"\n FROM		ERP_MUAHANG CPNK "
		+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CPNK.pk_seq = PS.SOCHUNGTU "
		+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
		+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "
		+"\n WHERE		CPNK.trangthai IN (1,2) AND  PS.NGAYCHUNGTU < '" + obj.gettungay() + "' "				
		+"\n			AND PS.LOAICHUNGTU = N'Chi phí khác' "
		+"\n			AND CPNK.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") "  //AND CPNK.NHAPHANPHOI_FK = "+obj.getnppdangnhap()+
		+"\n			AND PS.CO > 0 "
		+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
		
// THU HỒI TẠM ỨNG - TĂNG TRONG KỲ				
query+=
		"\n UNION ALL "+
		"\n	SELECT	cast(CTCN.NCC_FK as nvarchar(50)) AS NCCID, N'Thu hồi tạm ứng' AS LOAI,'' AS SOHOADON, CAST( CTCN.pk_seq AS NVARCHAR(50)) ID , ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "+ 
		"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CTCN.NGAYCHUNGTU,'' TENSP,'' SOPO , 13 AS STT "+ 
		"\n FROM	ERP_THUTIEN CTCN "+ 
		"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU "+
		"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "+ 
		"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "+ 
		"\n WHERE		CTCN.trangthai IN (1) AND PS.NGAYCHUNGTU < '"+obj.gettungay()+"'  "+ 
		"\n			AND CTCN.CONGTY_FK IN ( "+obj.getErpCongtyId()+" ) "+ 
		"\n			AND PS.LOAICHUNGTU = N'Thu tiền_Thu hồi tạm ứng' "+ 
		"\n			AND PS.CO > 0 "+ 
		"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";

// THU KHÁC - TĂNG TRONG KỲ
query+=
		"\n UNION ALL "+
		"\n	SELECT	cast(PS.MADOITUONG as nvarchar(50)) AS NCCID, N'Thu khác' AS LOAI,'' AS SOHOADON, CAST( CTCN.pk_seq AS NVARCHAR(50)) ID ,ROUND(ISNULL(PS.CO,0),0) AS TANGTRONGKY, 0 AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "+ 
		"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CTCN.NGAYCHUNGTU,'' TENSP,'' SOPO , 13 AS STT "+ 
		"\n FROM	ERP_THUTIEN CTCN "+ 
		"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU "+
		"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "+ 
		"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "+ 
		"\n WHERE		CTCN.trangthai IN (1) AND PS.NGAYCHUNGTU < '"+obj.gettungay()+"' "+ 
		"\n			AND CTCN.CONGTY_FK IN ( "+obj.getErpCongtyId()+" ) "+ 
		"\n			AND PS.LOAICHUNGTU = N'Thu khác' "+ 
		"\n			AND PS.CO > 0 "+ 
		"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
					
//UY NHIEM CHI/PHIEU CHI - GIẢM TRONG KỲ				
query+= "\n	UNION ALL "

		+"\n	SELECT	cast(TT.NCC_FK as nvarchar(50)) AS NCCID, N'Phiếu thanh toán' AS LOAI, '' AS SOHOADON, TT.PREFIX + '' + CAST(TT.PK_SEQ AS NVARCHAR(50)) ID, 0 AS TANGTRONGKY, ROUND(ISNULL(PS.NO,0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "
		+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, PS.NGAYCHUNGTU AS NGAY ,'' TENSP , '' SOPO,6 AS STT "
		+"\n	FROM	ERP_THANHTOANHOADON TT "
		+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON TT.PK_SEQ = PS.SOCHUNGTU "
		+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
		+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "	
		+"\n 	WHERE	TT.TRANGTHAI = 1  "
		+"\n			AND TT.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") "  //AND TT.NPP_FK = "+obj.getnppdangnhap()+
		+"\n			AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "' "
		+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) "
		+"\n			AND PS.LOAICHUNGTU = N'Thanh toán hóa đơn' \n"
		+"\n			AND PS.NO >0 "

// BUT TOAN TONG HOP - GIẢM TRONG KÌ
		+"\n UNION ALL "		
		+"\n SELECT		cast(PS.MADOITUONG as nvarchar(50)) AS NCCID, N'Bút toán tổng hợp' AS LOAI, '' AS SOHOADON, CAST( BTTH.PK_SEQ AS NVARCHAR(50)) ID, 0 AS TANGTRONGKY, ROUND(ISNULL(PS.NO, 0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "
		+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, PS.NGAYCHUNGTU AS NGAY ,'' TENSP ,'' SOPO ,7 AS STT "
		+"\n FROM		ERP_BUTTOANTONGHOP BTTH "
		+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH.PK_SEQ = PS.SOCHUNGTU "
		+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
		+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "		
		+"\n WHERE		BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "' "
		+"\n			AND PS.NO > 0 "
		+"\n			AND BTTH.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") "  //AND BTTH.NPP_FK = "+obj.getnppdangnhap()
		+"\n			AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' "
		+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
		

//GIAM GIA HANG MUA - GIẢM TRONG KÌ		
query+=	" UNION ALL "		
		+"\n SELECT		cast(GGHM.NCC_FK as nvarchar(50)) AS NCCID, N'Tăng/Giảm giá hàng mua' AS LOAI, '' AS SOHOADON, CAST(GGHM.PK_SEQ AS NVARCHAR(50)) ID, 0 AS TANGTRONGKY, ROUND(ISNULL(PS.NO, 0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "
		+"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU, PS.NGAYCHUNGTU AS NGAY ,'' TENSP,'' SOPO ,8 AS STT "
		+"\n FROM		ERP_GIAMGIAHANGMUA GGHM "
		+"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON GGHM.PK_SEQ = PS.SOCHUNGTU "
		+"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
		+"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "
		+"\n WHERE		GGHM.TRANGTHAI = '1' AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "' "
		+"\n			AND PS.NO > 0 "
		+"\n			AND GGHM.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") " //AND GGHM.NPP_FK = "+obj.getnppdangnhap()+
		+"\n			AND PS.LOAICHUNGTU = N'Giảm giá hàng mua' "
		+"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
										
//HOA DON NCC - GIẢM TRONG KỲ
query+="\n UNION ALL "

	 +"\n SELECT	cast(P.NCC_FK as nvarchar(50)) AS NCCID, N'Hóa đơn NCC' AS LOAI, HD.SOHOADON AS SOHOADON, CAST( P.pk_seq AS NVARCHAR(50) ) ID, 0 AS TANGTRONGKY, ROUND(ISNULL(PS.CO,0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK_DU.SOHIEUTAIKHOAN, "
	 +"\n			TK.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,PS.NGAYCHUNGTU AS NGAY ,SP.MA +' - ' +SP.TEN TENSP," 
	 +"\n			'' SOPO ,11 AS STT "
	 +"\n FROM		ERP_HOADONNCC HD INNER JOIN ERP_PARK P ON P.PK_SEQ=HD.PARK_FK "
	 +"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON P.PK_SEQ = PS.SOCHUNGTU "
	 +"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "
	 +"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "
	 +"\n			INNER JOIN SANPHAM SP ON SP.PK_SEQ = CAST(PS.MADOITUONG AS NUMERIC(18,0)) "
	 +"\n WHERE   	P.trangthai = 2 AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "'  "
	 +"\n			AND HD.CONGTY_FK IN ( "+obj.getErpCongtyId()+ ") " 
	 +"\n			AND PS.CO >0 " 			
	 +"\n			AND PS.TAIKHOANDOIUNG_FK IN  (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) "
	 +"\n			AND (PS.LOAICHUNGTU = N'Duyệt hóa đơn NCC' )";
	
// CẤN TRỪ CÔNG NỢ - GIẢM TRONG KỲ
query+=
		"\n UNION ALL "+	
		
		"\n SELECT	cast(CTCN.NCC_FK as nvarchar(50)) AS NCCID,N'Cấn trừ công nợ' AS LOAI,'' AS SOHOADON, CAST( CTCN.pk_seq AS NVARCHAR(50)) ID ,0 AS TANGTRONGKY, ROUND(ISNULL(PS.NO,0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "+ 
		"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CTCN.NGAYCANTRU,'' TENSP,'' SOPO , 12 AS STT "+
		"\n FROM		ERP_CANTRUCONGNO CTCN "+
		"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU "+
		"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "+
		"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "+
		"\n WHERE		CTCN.trangthai IN (1) AND PS.NGAYCHUNGTU < '"+ obj.gettungay() + "' "+
		"\n			AND CTCN.CONGTY_FK IN ( "+obj.getErpCongtyId()+ " ) " + //AND CTCN.NPP_FK = "+obj.getnppdangnhap()+
		"\n			AND PS.LOAICHUNGTU = N'Cấn trừ công nợ' "+
		"\n			AND PS.NO > 0 "+
		"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) ";
		

// TRẢ KHÁC - GIẢM TRONG KỲ
query+=
		"\n UNION ALL "+
		"\n	SELECT	cast(PS.MADOITUONG as nvarchar(50)) AS NCCID, N'Chi khác' AS LOAI,'' AS SOHOADON, CAST( CTCN.pk_seq AS NVARCHAR(50)) ID , 0 AS TANGTRONGKY, ROUND(ISNULL(PS.NO,0),0) AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, "+ 
		"\n			TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU,CTCN.NGAYGHINHAN,'' TENSP,'' SOPO , 13 AS STT "+ 
		"\n FROM	ERP_THANHTOANHOADON CTCN "+ 
		"\n			INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.pk_seq = PS.SOCHUNGTU "+
		"\n			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ "+ 
		"\n			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ "+ 
		"\n WHERE		CTCN.trangthai IN (1) AND PS.NGAYCHUNGTU < '"+obj.gettungay()+"'  "+ 
		"\n			AND CTCN.CONGTY_FK IN ( "+obj.getErpCongtyId()+" ) "+ 
		"\n			AND PS.LOAICHUNGTU = N'Trả khác' "+ 
		"\n			AND PS.NO > 0 "+ 
		"\n			AND PS.TAIKHOAN_FK IN (SELECT PK_SEQ  FROM ERP_TAIKHOANKT WHERE CONGTY_FK IN ("+obj.getErpCongtyId()+")  AND SOHIEUTAIKHOAN LIKE '331%' ) "+*/

		" )DAUKY \n"+
		" WHERE 1 = 1 "+
		" AND  DAUKY.NCCID IN (" + kh + ") "+
		" GROUP BY DAUKY.NCCID \n";
		
		System.out.println("Get sql dauky : \n"+ query);
	    return query;   
	}

	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
				
		fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\BaoCaoChiTietCongNoNCC.xlsm");
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		CreateHeader(workbook, obj);
		String sqldauky = this.getDauky(obj);
		String sqltrongky = this.getQuery(obj);
		
		isFillData = FillData(workbook, obj, sqldauky, sqltrongky);
   
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
			
		}catch(Exception e){
			e.printStackTrace();
			
			//System.out.println(e.toString());
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
	    

	    cell = cells.getCell("D5"); cell.setValue("BÁO CÁO CHI TIẾT CÔNG NỢ NHÀ CUNG CẤP");

	    cell = cells.getCell("A8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày: " + obj.gettungay());
	    
	    cell = cells.getCell("C8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày: " + obj.getdenngay());
	    	    
	    cell = cells.getCell("A9"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Mã và tên Khách hàng: "  + obj.getErpKhachHangId()); 
	    
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
		
		for(int i = 0;i < 15; i++)
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
	    		cells.setColumnWidth(i, 35.0f);
	    	else if(i==8)
	    		cells.setColumnWidth(i, 35.0f);
	    }	
		
		double totaltangtrongky=0;
		double totalgiamtrongky=0;
		double totalnocodauky=0;

		// Lay so du dau ky
		System.out.println("no dau ky:"+totalnocodauky);
		ResultSet rs = db.get(sqlDauky);
		
		if(rs != null){
			while(rs.next())
			{
				totalnocodauky = rs.getDouble("DAUKY");
			}
			rs.close();
		}
		
		Cell cell = null;
		cell = cells.getCell("K11"); cell.setValue(totalnocodauky);
		this.setStyleColorGray(cells, cell, "1");
		System.out.println("Trong Kỳ   nek  :  "+sqlTrongky);
		rs = db.get(sqlTrongky);
		int index = 12;
		
		 
			try 
			{
				cell = null;
				while (rs.next())
				{		
					System.out.println("SOHIEUTAIKHOAN :  "+rs.getString("SOHIEUTAIKHOAN"));
					
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
		 
		if(db != null)
			db.shutDown();
		return true;
	}	
	

}
