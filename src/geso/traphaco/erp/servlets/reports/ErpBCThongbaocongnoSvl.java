package geso.traphaco.erp.servlets.reports;

import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

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
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ErpBCThongbaocongnoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpBCThongbaocongnoSvl() {
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
		String ctyId = (String)session.getAttribute("congtyId");
		obj.setErpCongtyId(ctyId);
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setnppdangnhap(util.getUserId(querystring));
		String view = util.antiSQLInspection(request.getParameter("view"));
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
		obj.setDoituongId(session.getAttribute("doituongId"));
		
		if(view == null) view = "";
		
		obj.setView(view);		
		obj.InitErp();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCThongbaocongno.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		//String ctyId = (String)session.getAttribute("congtyId");
		
		
		//obj.setuserTen((String) session.getAttribute("userTen"));
		//String userTen = "Admin HCM";
		
		String ctyId = (String)session.getAttribute("congtyId");
		String userId = (String) session.getAttribute("userId");

		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		IStockintransit obj = new Stockintransit();
		obj.setErpCongtyId(ctyId);
		obj.setuserId(userId);
		obj.setnppdangnhap(userId);
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		String[] ctyIds = request.getParameterValues("ctyIds");
		obj.setCtyIds(ctyIds);
		
		obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");		
		
		String khId = util.antiSQLInspection(request.getParameter("khId"));
		obj.SetKHid(khId);
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
		obj.setDoituongId(session.getAttribute("doituongId"));
		
		obj.InitErp();
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCThongbaocongno.jsp";
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCThongbaocongno.xlsm");
			
			String query = getQuery(obj); 

	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj, query))
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
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				System.out.println(e.toString());
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
			}
		}else{
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}

		
	}
	
	public String getQuery(IStockintransit obj)
	{
		String maKH = obj.GetKhId();
		String[] tmp = maKH.split("-");
		if(tmp[1].equals("0")){
			maKH = "KH" + tmp[0];
		}else{
			maKH = "NPP"+ tmp[0];
		}
		
		Utility util = new Utility();
		
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KH", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() );
		
		String query =			
			"\n SELECT KHACHHANG.TENKH ,HOADON.PK_SEQ, HOADON.SOHOADON, HOADON.NGAYXUATHD NGAY, HOADON.TENSP, HOADON.TIENHD, HOADON.THANHTOAN DATRA, HOADON.CONLAI, HOADON.MASOSANH "+		
			"\n FROM "+ 
			"\n( "+
			"\n	SELECT 'KH'+CAST(PK_SEQ AS NVARCHAR) KHID, MA, maFAST, TEN TENKH "+
			"\n	FROM KHACHHANG WHERE 1 = 1 "+strQUYEN +
			"\n	UNION ALL "+
			"\n	SELECT 'NPP'+CAST(PK_SEQ AS NVARCHAR) KHID,MA, '' MAFAST, TEN TENKH "+
			"\n	FROM NHAPHANPHOI "+
			"\n WHERE PK_SEQ in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + obj.getuserId() + "' ) \n"+			
			"\n	UNION ALL "+
			"\n	SELECT 'NV'+CAST(PK_SEQ AS NVARCHAR) KHID,MA ,'' MAFAST, TEN TENKH "+
			"\n	FROM ERP_NHANVIEN "+
			"\n ) KHACHHANG "+ 
			"\n INNER JOIN "+
			"\n ( "+
			"\n		SELECT DISTINCT	HD.PK_SEQ, HD.SOHOADON, HD.NGAYXUATHD, "+
			"\n				ISNULL((SELECT distinct isnull(SP.TENVIETTAT,'') +N', SL:'+ cast(hdsp.SOLUONG as nvarchar(50)) +'; ' "+
			"\n	    				FROM ERP_HOADONNPP_SP hdsp INNER JOIN SANPHAM SP ON hdsp.SANPHAM_FK = SP.PK_SEQ "+
			"\n	    				WHERE hdsp.HOADON_FK = HD.PK_SEQ for xml path('') ),'') TENSP, "+
			"\n	    		HD.tongtienavat TIENHD, "+
			"\n	    		isnull(DATHU.TONGTHU,0) THANHTOAN, "+
			"\n	    		HD.tongtienavat - isnull(DATHU.TONGTHU,0) CONLAI, "+
			"\n				CASE WHEN HD.KHACHHANG_FK IS NOT NULL THEN 'KH'+CAST(HD.KHACHHANG_FK AS NVARCHAR (255)) "+
			"\n				WHEN HD.nhanvien_fk IS NOT NULL THEN 'NV'+CAST(HD.nhanvien_fk AS NVARCHAR(255)) "+
			"\n				WHEN HD.NPP_DAT_FK IS NOT NULL THEN 'NPP'+CAST(HD.NPP_DAT_FK AS NVARCHAR(255))  END MASOSANH "+
			"\n		FROM ERP_HOADONNPP HD "+
			"\n 	LEFT JOIN KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ "+
			"\n     LEFT JOIN NHAPHANPHOI NPP ON HD.NPP_DAT_FK = NPP.PK_SEQ "+
			"\n		LEFT JOIN ERP_NHANVIEN NV ON HD.nhanvien_fk = NV.PK_SEQ "+
			"\n	 	LEFT JOIN "+ 
			"\n		( "+
			"\n			SELECT  THU.HOADON_FK, SUM(ISNULL(THU.TONGTHU,0)) TONGTHU "+
			"\n			FROM ( "+
			//	BẢNG KÊ THU TIỀN
			"\n						SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU "+
			"\n						FROM ERP_THUTIEN_HOADON TTHD "+
			"\n						INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ "+
			"\n						WHERE TT.TRANGTHAI = 1 AND TTHD.LOAIHOADON = 0 AND isnull(TTHD.XOACHENHLECH,0) = 0 AND TT.BANGKE_FK IS NOT NULL ";
			if(obj.gettungay().trim().length()>0)
				query+= " AND TT.NGAYGHISO >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND TT.NGAYGHISO <= '"+obj.getdenngay()+"'" ;
			
			query+=
			"\n						GROUP BY HOADON_FK "+
			
			"\n   					UNION ALL "+

									// THU TIỀN KHÔNG THEO BẢNG KÊ
			"\n						SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU "+
			"\n						FROM ERP_THUTIEN_HOADON TTHD "+
			"\n						INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ "+
			"\n						WHERE TT.TRANGTHAI = 1 AND TTHD.LOAIHOADON = 0 AND isnull(TTHD.XOACHENHLECH,0) = 0 AND TT.BANGKE_FK IS NULL ";
			
			if(obj.gettungay().trim().length()>0)
				query+= " AND TT.NGAYGHISO >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND TT.NGAYGHISO <= '"+obj.getdenngay()+"'" ;
			
			query +=			
			"\n						GROUP BY HOADON_FK "+
			
			"\n   					UNION ALL "+

									// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
			"\n						SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
			"\n						FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
			"\n						INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+
			"\n						WHERE TT.TRANGTHAI IN (1) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '0'  \n";
			
			if(obj.gettungay().trim().length()>0)
				query+= " AND TT.NGAYGHISO >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND TT.NGAYGHISO <= '"+obj.getdenngay()+"'" ;
			
			query +=
			"\n						GROUP BY HOADON_FK "+
			
			"\n   					UNION ALL \n"+

									// BÙ TRỪ KHÁCH HÀNG
			"\n						SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS DATHANHTOAN  \n"+
		    "\n						FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n "+
			"\n						WHERE  BT.TRANGTHAI IN (1) AND BT_KH.LOAIHD = 0 \n";
			
			if(obj.gettungay().trim().length()>0)
				query+= " AND BT.NGAYBUTRU >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND BT.NGAYBUTRU <= '"+obj.getdenngay()+"'" ;
			
			query +=
			"\n						GROUP BY BT_KH.HOADON_FK \n"+						

			"\n		 			) THU \n"+ 
			"\n	  			 GROUP BY  THU.HOADON_FK \n"+
			"\n	 		)DATHU ON HD.PK_SEQ = DATHU.HOADON_FK \n"+
			"\n		WHERE HD.TRANGTHAI IN (2,4) AND HD.tongtienavat > 0 AND LOAIHOADON = 0 ";
			//"and HD.KBH_FK in " + util.quyen_kenh( obj.getuserId() ) + " \n";
			
			if(obj.gettungay().trim().length()>0)
				query+= " AND HD.NGAYGHINHAN >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND HD.NGAYGHINHAN <= '"+obj.getdenngay()+"'" ;
			
			query+=
			"\n ) HOADON ON KHACHHANG.KHID = HOADON.MASOSANH "+
			"\n WHERE 1=1 AND HOADON.MASOSANH = '" + maKH +  "'  AND CONLAI > 0  \n ";
		
		query +=
			" UNION ALL \n"+
			 
			 // HÓA ĐƠN TĂNG
			" SELECT KH.TEN TENKH, HDPL.pk_seq, HDPL.sohoadon, HDPL.ngayhoadon, \n"+
			" ISNULL((SELECT distinct isnull(SP.TENVIETTAT,'') +N', SL:'+ cast(hdsp.SOLUONG as nvarchar(50)) +'; ' \n"+
			"		 FROM erp_hoadonphelieu_sanpham hdsp LEFT JOIN SANPHAM SP ON hdsp.SANPHAM_FK = SP.PK_SEQ   \n"+
			"		 WHERE hdsp.hoadonphelieu_fk = HDPL.PK_SEQ for xml path('') ),'') TENSP,  HDPL.avat TIENHD, 0 thanhtoan, HDPL.avat - isnull( DATHU.TONGTHU,0 ) conlai, ('KH'+ CAST( KH.PK_SEQ as nvarchar(50) ) ) MASOSANH \n"+
				    				
			" FROM ERP_HOADONPHELIEU HDPL \n"+
			" LEFT JOIN KHACHHANG KH ON HDPL.khachhang_fk = KH.PK_SEQ \n"+
			" LEFT JOIN	\n"+
			"  ( 	\n"+
			"		SELECT THU.HOADON_FK, SUM(THU.TONGTHU) AS TONGTHU \n"+
			"		FROM ( \n" +
				
				// THU TIỀN KHÔNG THEO BẢNG KÊ
			"				SELECT TTHD.HOADON_FK, SUM( TTHD.SOTIENTT ) AS TONGTHU \n"+
			"				FROM ERP_THUTIEN_HOADON TTHD \n"+
			"				INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
			"				WHERE TT.TRANGTHAI = 1 AND TTHD.LOAIHOADON = 1 \n";
			if(obj.gettungay().trim().length()>0)
				query+= " AND TT.NGAYGHISO >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND TT.NGAYGHISO <= '"+obj.getdenngay()+"'" ;
			
			query+=
			"				GROUP BY HOADON_FK \n"+
			  
			"   			UNION ALL \n"+
				
				// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
			"				SELECT TTHD.HOADON_FK, SUM( TTHD.SOTIENTT ) AS TONGTHU \n"+
			"				FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
			"				INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+
			"				WHERE TT.TRANGTHAI IN (1) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '1'  \n";
			if(obj.gettungay().trim().length()>0)
				query+= " AND TT.NGAYGHISO >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND TT.NGAYGHISO <= '"+obj.getdenngay()+"'" ;
			
			query+=
			"				GROUP BY HOADON_FK \n"+

			"   			UNION ALL \n"+
				
				// BÙ TRỪ KHÁCH HÀNG
			"				SELECT BT_KH.HOADON_FK, SUM( BT_KH.XOANO ) AS DATHANHTOAN  \n"+
			"				FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n "+
			"				WHERE  BT.TRANGTHAI IN (1) AND BT_KH.LOAIHD = 1 \n";
			
			if(obj.gettungay().trim().length()>0)
				query+= " AND BT.NGAYBUTRU >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND BT.NGAYBUTRU <= '"+obj.getdenngay()+"'" ;
			
			query+=
			"				GROUP BY BT_KH.HOADON_FK \n"+	
			  
			"		 	) THU \n"+							 
			"		GROUP BY HOADON_FK \n"+
			" )DATHU ON HDPL.PK_SEQ = DATHU.HOADON_FK \n"+
			
			" WHERE 1 = 1 AND HDPL.doanhthu_fk = '400002' AND HDPL.TRANGTHAI = 1 AND ( HDPL.avat - isnull( DATHU.TONGTHU,0 ) ) != 0 AND ('KH'+ CAST( KH.PK_SEQ as nvarchar(50) ) ) = '"+maKH+"'";
			
			if(obj.gettungay().trim().length()>0)
				query+= " AND HDPL.ngayghinhan >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND HDPL.ngayghinhan <= '"+obj.getdenngay()+"'" ;
					 
			 // HÓA ĐƠN GIẢM
			 query += 
			  
			 " UNION ALL \n"+
			  
			 " SELECT KH.TEN TENKH, HDPL.pk_seq, HDPL.sohoadon, HDPL.ngayhoadon,  \n"+
			 " 		  ISNULL((SELECT distinct isnull(SP.TENVIETTAT,'') +N', SL:'+ cast(hdsp.SOLUONG as nvarchar(50)) +'; ' \n"+
			 " 		  FROM erp_hoadonphelieu_sanpham hdsp LEFT JOIN SANPHAM SP ON hdsp.SANPHAM_FK = SP.PK_SEQ   \n"+
			 "		  WHERE hdsp.hoadonphelieu_fk = HDPL.PK_SEQ for xml path('') ),'') TENSP, 0 TIENHD, (-1)*HDPL.avat - isnull( DATHU.TONGTHU,0 ) thanhtoan, 0 - (-1)*HDPL.avat - isnull( DATHU.TONGTHU,0 ) conlai, ('KH'+ CAST( KH.PK_SEQ as nvarchar(50) ) ) MASOSANH \n"+
				    				
			 " FROM ERP_HOADONPHELIEU HDPL \n"+
			 " LEFT JOIN KHACHHANG KH ON HDPL.khachhang_fk = KH.PK_SEQ \n"+
			 " LEFT JOIN	\n"+
			 "  ( 	\n"+
			"		SELECT THU.HOADON_FK, SUM(THU.TONGTHU) AS TONGTHU \n"+
			"		FROM ( \n" +
				
				// THU TIỀN KHÔNG THEO BẢNG KÊ
			"				SELECT TTHD.HOADON_FK, SUM( TTHD.SOTIENTT ) AS TONGTHU \n"+
			"				FROM ERP_THUTIEN_HOADON TTHD \n"+
			"				INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
			"				WHERE TT.TRANGTHAI = 1 AND TTHD.LOAIHOADON = 1 \n";
			if(obj.gettungay().trim().length()>0)
				query+= " AND TT.NGAYGHISO >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND TT.NGAYGHISO <= '"+obj.getdenngay()+"'" ;
			
			query+=
			"				GROUP BY HOADON_FK \n"+
			  
			"   			UNION ALL \n"+
				
				// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
			"				SELECT TTHD.HOADON_FK, SUM( TTHD.SOTIENTT ) AS TONGTHU \n"+
			"				FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
			"				INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+
			"				WHERE TT.TRANGTHAI IN (1) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '1'  \n";
			if(obj.gettungay().trim().length()>0)
				query+= " AND TT.NGAYGHISO >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND TT.NGAYGHISO <= '"+obj.getdenngay()+"'" ;
			
			query+=
			"				GROUP BY HOADON_FK \n"+

			"   			UNION ALL \n"+
				
				// BÙ TRỪ KHÁCH HÀNG
			"				SELECT BT_KH.HOADON_FK, SUM( BT_KH.XOANO ) AS DATHANHTOAN  \n"+
			"				FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n "+
			"				WHERE  BT.TRANGTHAI IN (1) AND BT_KH.LOAIHD = 1 \n";
			
			if(obj.gettungay().trim().length()>0)
				query+= " AND BT.NGAYBUTRU >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND BT.NGAYBUTRU <= '"+obj.getdenngay()+"'" ;
			
			query+=
			"				GROUP BY BT_KH.HOADON_FK \n"+	
			  
			"		 	) THU \n"+							 
			"		GROUP BY HOADON_FK \n"+
			" )DATHU ON HDPL.PK_SEQ = DATHU.HOADON_FK \n"+
			 " WHERE 1 = 1 AND HDPL.doanhthu_fk = '400003' AND HDPL.TRANGTHAI = 1   AND ('KH'+ CAST( KH.PK_SEQ as nvarchar(50) ) ) = '"+maKH+"'";
				
			if(obj.gettungay().trim().length()>0)
				query+= " AND HDPL.ngayghinhan >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND HDPL.ngayghinhan <= '"+obj.getdenngay()+"'" ;
			
			// HÓA ĐƠN HÀNG TRẢ VỀ
			
			query += 
			" UNION ALL \n"+
			
			" SELECT KH.TEN TENKH, HD.pk_seq, HD.sohoadon, HD.NGAYXUATHD, " +
			" ISNULL((SELECT distinct isnull(SP.TENVIETTAT,'') +N', SL:'+ cast(hdsp.SOLUONG as nvarchar(50)) +'; ' \n"+
			"		 FROM ERP_HOADON_SP hdsp  INNER JOIN SANPHAM SP ON hdsp.SANPHAM_FK = SP.PK_SEQ \n"+
			"		 WHERE hdsp.HOADON_FK = HD.PK_SEQ for xml path('') ),'') TENSP, \n"+
			" 0 TIENHD, HD.TONGTIENAVAT thanhtoan, 0 - HD.TONGTIENAVAT conlai, \n"+
			" ('KH'+ CAST( KH.PK_SEQ as nvarchar(50) ) ) MASOSANH \n"+
				    				
			" FROM ERP_HOADON HD \n"+
			" LEFT JOIN KHACHHANG KH ON HD.khachhang_fk = KH.PK_SEQ \n"+
			" WHERE 1 = 1 AND LoaiHoaDon = 2 AND HD.TRANGTHAI = 1  AND ('KH'+ CAST( KH.PK_SEQ as nvarchar(50) ) ) = '"+maKH+"'";
				
			if(obj.gettungay().trim().length()>0)
				query+= " AND HD.NGAYXUATHD >= '"+obj.gettungay()+"'" ;
			
			if(obj.getdenngay().trim().length()>0)
				query+= " AND HD.NGAYXUATHD <= '"+obj.getdenngay()+"'" ;
			
			System.out.println(query);
			
			//"\n HAVING (SUM(A.TIENHD) - SUM(A.THANHTOAN) > 0 OR SUM(A.TRATRUOC) > 0)";
			
		
		System.out.println(query);
	    return query;   
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCThongbaocongno.xlsm");
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateHeader(workbook, obj);
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
	
	private void CreateHeader(Workbook workbook, IStockintransit obj) {
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		
		String ctyName = "";
		String diachi = "";
		String masothue = "";
		String dienthoai = "";
		String fax = "";
		String tenKH = "";
		ResultSet ctyRs = obj.getCongtyRS(obj.getErpCongtyId());
		try{
			if(ctyRs != null){
				ctyRs.next();
			
				ctyName = ctyRs.getString("TEN");
				diachi =  ctyRs.getString("DIACHI");
				masothue =  ctyRs.getString("MASOTHUE");
				dienthoai = ctyRs.getString("DIENTHOAI");
				fax = ctyRs.getString("FAX");
				ctyRs.close();
			}
			
		}catch(java.sql.SQLException e){
			System.out.println(e.toString());
		}
		
		ResultSet khRs = obj.getKhachhangRS(obj.getErpCongtyId());
		try{
			if(khRs != null){
				khRs.next();
			
				tenKH = khRs.getString("TEN");

				khRs.close();
			}
			
		}catch(java.sql.SQLException e){
			System.out.println(e.toString());
		}
		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 15, ctyName);
	    
	    cells.setRowHeight(1, 20.0f);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, "Địa chỉ: " + diachi); 
	       
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, "Điện thoại: " + dienthoai + " . Fax: " + fax); 
	    
	    cell = cells.getCell("B5"); cell.setValue(tenKH);

	    cell = cells.getCell("A7"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Chúng tôi ghi nhận nợ quý Khách hàng đến ngày " + obj.getdenngay() + " với số liệu chi tiết như sau:") ;
	    
	    cell = cells.getCell("A8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 16, "THÔNG BÁO CÔNG NỢ");

	    cell = cells.getCell("A9"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày " + obj.getdenngay());

	    cell = cells.getCell("E11"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Tp. HCM ngày " + obj.getdenngay().substring(8, 10) + " tháng " + obj.getdenngay().substring(5, 7) + " năm " + obj.getdenngay().substring(0, 4));
	}
	
	private void setBorder(Cell cell, int border)
	{
		Style style;	
		style = cell.getStyle();
        if(border == 1){
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
            cell.setStyle(style);
        }else{
        	style.setBorderLine(BorderType.TOP, BorderLineType.NONE);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.NONE);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
            cell.setStyle(style);        	
        }
        
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

	private void setStyleColorNormar(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("X1");
		Style style;	
		style = cell1.getStyle();
       //style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
       // style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		NumberFormat formatter=new DecimalFormat("#,###,###"); 
		
		Cells cells = worksheet.getCells();	
		
		for(int i = 0;i < 7; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	
		
		ResultSet rs = db.get(query);
		int index = 13;
		double TCsotien = 0;
		double TCdathanhtoan = 0;
		
		int i = 1;
		
		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				while (rs.next())
				{		
					TCsotien += rs.getDouble("TIENHD");
					TCdathanhtoan += rs.getDouble("DATRA");
					
					cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(i);	
					setBorder(cell, 1);
					
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("SOHOADON"));
					setBorder(cell, 1);
					
					cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("NGAY"));	
					setBorder(cell, 1);
					
					cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("TENSP"));	
					setBorder(cell, 1);
					
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getDouble("TIENHD"));
					setBorder(cell, 1);
					
					cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getDouble("DATRA"));	
					setBorder(cell, 1);
					
					cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getDouble("TIENHD") - rs.getDouble("DATRA"));
					setBorder(cell, 1);
					
					i++;
					index++;					
				}

				if (rs != null){
					rs.close();
				}
				
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("CỘNG");
				setBorder(cell, 1);
				
				cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("");
				setBorder(cell, 1);
				
				cell = cells.getCell("C" + String.valueOf(index));		cell.setValue("");
				setBorder(cell, 1);
				
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue("");	
				setBorder(cell, 1);
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(TCsotien);	
				setBorder(cell, 1);
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(TCdathanhtoan);
				setBorder(cell, 1);
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(TCsotien - TCdathanhtoan);
				setBorder(cell, 1);
				
				
				index++;
				if(TCsotien - TCdathanhtoan > 0){
					cell = cells.getCell("A" + String.valueOf(index));		
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Số tiền phải thanh toán là: " + formatter.format(TCsotien - TCdathanhtoan) + " đồng");
				}else{
					cell = cells.getCell("A" + String.valueOf(index));		
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Số tiền trả trước còn lại là: " + formatter.format((-1)*(TCsotien - TCdathanhtoan)) + " đồng");
				}
				index++;
				
				//ĐỌC TIỀN RA CHỮ
				
				// Tien bang chu
				doctienrachu doctien = new doctienrachu();
				double total = 0;
				String tien = "";
				String TienIN = "";
				
				total = TCsotien - TCdathanhtoan;
				
				String gta = "";
					
					
				if(total < 0) 
					{
						total = total*(-1);
						gta = "Âm ";
					}
				
				String sotien = formatter.format(total);
				System.out.println(sotien);
				
				tien = doctien.docTien( Long.parseLong(sotien.replaceAll(",", "")) );
				
				//Viết hoa ký tự đầu tiên
				TienIN = gta + (tien.substring(0,1)).toUpperCase() + tien.substring(1);   
			   
				tien = tien.substring(0, 1).toUpperCase() + tien.substring(1, tien.length());
				
				if(tien.equals("Đồng"))
					 TienIN = "Không Đồng";

				cell = cells.getCell("A" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Bằng chữ: " + TienIN);
				
				index = index + 2;
			
				cell = cells.getCell("A" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "- Quý BV vui lòng kiểm tra, xác nhận số liệu trên và phản hồi cho chúng tôi qua số fax (08) 38 630 394");
								
				
				if(TCsotien - TCdathanhtoan > 0){
					index++;
					
					cell = cells.getCell("A" + String.valueOf(index));		
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "- Để thuận tiện trong việc thanh toán và giao hàng, kính đề nghị Quý BV thanh toán số nợ trong thời gian sớm nhất.");

					index = index + 2;
			
					cell = cells.getCell("A" + String.valueOf(index));		
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Hình thức chuyển khoản như sau:");
				
					index = index + 2;
				
					cell = cells.getCell("B" + String.valueOf(index));		
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Tên Tài khoản: ");
				
					cell = cells.getCell("C" + String.valueOf(index));		
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Cty Cổ Phần Dược Pha Nam");

					index++;
					cell = cells.getCell("B" + String.valueOf(index));		
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Số Tài khoản: ");
				
					cell = cells.getCell("C" + String.valueOf(index));		
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "20711 0009 0009");
				
					index++;
					cell = cells.getCell("B" + String.valueOf(index));		
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Tại: ");
				
					cell = cells.getCell("C" + String.valueOf(index));		
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngân hàng Quân Đội - PGD Q.10, TP.HCM");
				
				}
				index = index + 2;
				
				cell = cells.getCell("A" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Trân trọng kính chào!");
				
				index = index + 2;
				
				cell = cells.getCell("F" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "CTY CỔ PHẦN DƯỢC PHA NAM");

				cells.setColumnWidth(0, 10.0f);
				cells.setColumnWidth(1, 40.0f);
				cells.setColumnWidth(2, 10.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
				
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
	

}
