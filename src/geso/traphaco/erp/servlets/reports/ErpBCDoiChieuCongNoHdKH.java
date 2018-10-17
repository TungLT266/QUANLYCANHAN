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

public class ErpBCDoiChieuCongNoHdKH extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpBCDoiChieuCongNoHdKH() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		obj.setErpCongtyId(ctyId);
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setnppdangnhap(util.getUserId(querystring));
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
		String view = util.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		obj.setView(view);		
		obj.InitErp();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCDoiChieuCongNoHdKH.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		String userId = (String) session.getAttribute("userId");

		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		IStockintransit obj = new Stockintransit();
		obj.setErpCongtyId(ctyId);
		obj.setuserId(userId);
		obj.setnppdangnhap(userId);
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		String[] ctyIds = request.getParameterValues("ctyIds");
		obj.setCtyIds(ctyIds);
		obj.setView(util.antiSQLInspection(request.getParameter("view")));
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.settungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setdenngay(denngay);
		
		String kenhbhId = util.antiSQLInspection(request.getParameter("kbhid"));
		if(kenhbhId == null)
			kenhbhId = "";
		obj.setkenhId(kenhbhId);
		
		String khachhangId = util.antiSQLInspection(request.getParameter("khid"));
		if(khachhangId == null)
			khachhangId  = "";		
		obj.setErpKhachHangId(util.antiSQLInspection(request.getParameter("khid")));
		 
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");	
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCDoiChieuCongNoHdKH.jsp";
		
		obj.InitErp();
		
		if(denngay.trim().length()<=0 || khachhangId.trim().length()<=0) 
			{
				if(denngay.trim().length()<=0)
					obj.setMsg("Vui lòng chọn thời gian kết thúc lấy bc!");
				else if(khachhangId.trim().length()<=0)
					obj.setMsg("Vui lòng chọn khách hàng!");
				
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
			}

		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCDoiChieuCongNoKH.xlsm");
			
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
		Utility util = new Utility();
		
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KH", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() );
		
		String query = ""+
		// HÓA ĐƠN TÀI CHÍNH
		"\n SELECT KHACHHANG.TENKH ,HOADON.PK_SEQ, HOADON.SOHOADON, HOADON.NGAYXUATHD, HOADON.TENSP, HOADON.TIENHD, HOADON.THANHTOAN, HOADON.CONLAI, HOADON.MASOSANH "+		
		"\n FROM "+ 
		"\n( "+
		"\n	SELECT 'KH'+CAST( PK_SEQ AS NVARCHAR ) KHID, MA, maFAST, TEN TENKH "+
		"\n	FROM KHACHHANG KH WHERE 1 = 1 "+ strQUYEN;
		
		if(obj.getkenhId().trim().length()>0)
		{
			query += "\n AND PK_SEQ IN (SELECT KHACHHANG_FK FROM KHACHHANG_KENHBANHANG WHERE KBH_FK = "+obj.getkenhId()+") ";
		}		
		query+=
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
		"\n		SELECT DISTINCT	HD.PK_SEQ, HD.SOHOADON, HD.NGAYXUATHD, HD.NGAYGHINHAN,  "+
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
		
		query+=
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
		
		query+=
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
		
		query+=
		"\n						GROUP BY BT_KH.HOADON_FK \n"+						

		"\n		 			) THU \n"+ 
		"\n	  			 GROUP BY  THU.HOADON_FK \n"+
		"\n	 		)DATHU ON HD.PK_SEQ = DATHU.HOADON_FK \n"+
		"\n		WHERE HD.TRANGTHAI IN (2,4) AND HD.tongtienavat > 0 AND LOAIHOADON = 0 and HD.kbh_fk in " + util.quyen_kenh( obj.getuserId() ) + " \n"+
		"\n ) HOADON ON KHACHHANG.KHID = HOADON.MASOSANH "+
		"\n WHERE 1=1 AND HOADON.CONLAI > 0 ";
		if(obj.getErpKhachHangId().trim().length()>0)
			query+= " AND KHACHHANG.KHID = '"+obj.getErpKhachHangId()+"'";
		
		if(obj.gettungay().trim().length()>0)
			query+= " AND HOADON.NGAYGHINHAN >= '"+obj.gettungay()+"'" ;
		
		if(obj.getdenngay().trim().length()>0)
			query+= " AND HOADON.NGAYGHINHAN <= '"+obj.getdenngay()+"'" ;
		
		
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
		  
		" WHERE 1 = 1 AND HDPL.doanhthu_fk = '400002' AND HDPL.TRANGTHAI = 1 AND ( HDPL.avat - isnull( DATHU.TONGTHU,0 ) ) != 0 ";
		
		if(obj.getErpKhachHangId().trim().length()>0)
			query+= "  AND ('KH'+ CAST( KH.PK_SEQ as nvarchar(50) ) ) = '"+obj.getErpKhachHangId()+"'";
		
		if(obj.gettungay().trim().length()>0)
			query+= " AND HDPL.NGAYGHINHAN >= '"+obj.gettungay()+"'" ;
		
		if(obj.getdenngay().trim().length()>0)
			query+= " AND HDPL.NGAYGHINHAN <= '"+obj.getdenngay()+"'" ;
				 
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
		 " WHERE 1 = 1 AND HDPL.doanhthu_fk = '400003' AND HDPL.TRANGTHAI = 1 ";
		 
		if(obj.getErpKhachHangId().trim().length()>0)
				query+= "  AND ('KH'+ CAST( KH.PK_SEQ as nvarchar(50) ) ) = '"+obj.getErpKhachHangId()+"'";
			
		if(obj.gettungay().trim().length()>0)
			query+= " AND HDPL.NGAYGHINHAN >= '"+obj.gettungay()+"'" ;
		
		if(obj.getdenngay().trim().length()>0)
			query+= " AND HDPL.NGAYGHINHAN <= '"+obj.getdenngay()+"'" ;
		
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
		" WHERE 1 = 1 AND LoaiHoaDon = 2 AND HD.TRANGTHAI = 1 \n";
		
		 if(obj.getErpKhachHangId().trim().length()>0)
				query+= "  AND ('KH'+ CAST( KH.PK_SEQ as nvarchar(50) ) ) = '"+obj.getErpKhachHangId()+"'";
			
		if(obj.gettungay().trim().length()>0)
			query+= " AND HD.NGAYXUATHD >= '"+obj.gettungay()+"'" ;
		
		if(obj.getdenngay().trim().length()>0)
			query+= " AND HD.NGAYXUATHD <= '"+obj.getdenngay()+"'" ;
		
		System.out.println(query);
	    return query;   
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCDoiChieuCongNoKH.xlsm");
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		//CreateHeader(workbook, obj);
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
		
	private void setStyleColorGray(Cells cells ,Cell cell, String leftright)
	{			
	    
        if(leftright.equals("1")){
        	Cell cell1 = cells.getCell("Y1");
    		Style style;	
    		style = cell1.getStyle();	
    		
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
            cell.setStyle(style);
        }else if(leftright.equals("2"))
        {    		
    		Cell cell1 = cells.getCell("AD1");
    		Style style;	
    		style = cell1.getStyle();	
    		
    		style.setBorderLine(BorderType.TOP, BorderLineType.NONE);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.NONE);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
        	
    		cell.setStyle(style);
        }
        else if(leftright.equals("3"))
        {
        	Cell cell1 = cells.getCell("AA1");
    		Style style;	
    		style = cell1.getStyle();
    		
    		style.setBorderLine(BorderType.TOP, BorderLineType.NONE);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.NONE);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
        	
    		cell.setStyle(style);
        }
        else if(leftright.equals("4"))
        {
        	Cell cell1 = cells.getCell("AD1");
    		Style style;	
    		style = cell1.getStyle();
    		
    		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        	
    		cell.setStyle(style);
        }
        else if(leftright.equals("5"))
        {
        	Cell cell1 = cells.getCell("AB1");
    		Style style;	
    		style = cell1.getStyle();
    		
    		style.setBorderLine(BorderType.TOP, BorderLineType.NONE);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.NONE);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
        	
    		cell.setStyle(style);
        }
        else {
        	Cell cell1 = cells.getCell("Y1");
    		Style style;	
    		style = cell1.getStyle();	
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
        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        cell.setStyle(style);
        
        
	}
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		
		ResultSet rs = db.get(query);
		int index = 13;
		double totaltienhd=0;
		double totaldathanhtoan=0;
		double totalconlai=0;
		
		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				String tenkh = "";
				while (rs.next())
				{		
					
					cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index-12);	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("SOHOADON"));	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("NGAYXUATHD"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("TENSP"));	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getDouble("TIENHD"));
					this.setStyleColorGray(cells, cell, "1");
					cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getDouble("THANHTOAN"));
					this.setStyleColorGray(cells, cell, "1");
					cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getDouble("CONLAI"));	//Nhan hang   	6
					this.setStyleColorGray(cells, cell, "1");
					
					index++;	
					totaltienhd += rs.getDouble("TIENHD");
					totaldathanhtoan += rs.getDouble("THANHTOAN");
					totalconlai += rs.getDouble("CONLAI");
					tenkh = rs.getString("TENKH");
				}

				if (rs != null){
					rs.close();
				}
				
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("B" + String.valueOf(index));	 cell.setValue("Tổng cộng");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("C" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue("");	
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(totaltienhd);	
				this.setStyleColorGray(cells, cell, "4");
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(totaldathanhtoan);
				this.setStyleColorGray(cells, cell, "4");
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(totalconlai);
				this.setStyleColorGray(cells, cell, "4");
				
				index=index+1;
				
				cells.merge(index - 1, 0, index - 1, 2);
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("Số tiền thanh toán: "+totalconlai);
				this.setStyleColorGray(cells, cell, "2");
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(totalconlai);
				this.setStyleColorGray(cells, cell, "2");
				
				index++;
				
				doctienrachu doctien = new doctienrachu();
				String gta = "";
				if(totalconlai<0) 
					{
						totalconlai= totalconlai*(-1);
						gta = "âm ";
					}
				
			    String tien = doctien.docTien(Math.round(totalconlai));	
			    String TienIN =  gta + (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			    
			    cells.merge(index - 1, 0, index - 1, 8);
			    cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("Bằng chữ: "+TienIN);
				this.setStyleColorGray(cells, cell, "2");
				
				index++;
				cells.merge(index - 1, 0, index - 1, 8);
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("- Quý BV vui lòng kiểm tra, xác nhận số liệu trên và phản hồi cho chúng tôi qua số fax (08) 38 630 394");
				this.setStyleColorGray(cells, cell, "3");
				
				index++;
				cells.merge(index - 1, 0, index - 1, 8);
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("- Để thuận tiện trong việc thanh toán và giao hàng, kính đề nghị Quý BV thanh toán số nợ trong thời gian sớm nhất.");
				this.setStyleColorGray(cells, cell, "3");
				
				index = index + 2 ;
				cells.merge(index - 1, 0, index - 1, 8);
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("Hình thức chuyển khoản như sau:");
				this.setStyleColorGray(cells, cell, "3");
				
				index = index + 2;
				cells.merge(index - 1, 1, index - 1, 8);
				cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("Tên Tài khoản: Cty Cổ Phần Dược Pha Nam");
				this.setStyleColorGray(cells, cell, "2");
				
				index++;
				cells.merge(index - 1, 1, index - 1, 8);
				cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("Số Tài khoản: 20711 0009 0009");
				this.setStyleColorGray(cells, cell, "2");
				
				index++;
				cells.merge(index - 1, 1, index - 1, 8);
				cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("Tại: Ngân hàng Quân Đội - PGD Q.10, TP.HCM");
				this.setStyleColorGray(cells, cell, "2");
				
				index++;
				cells.merge(index - 1, 0, index - 1, 8);
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("Trân trọng kính chào!");
				this.setStyleColorGray(cells, cell, "3");
				
				index = index + 2;
				cells.merge(index - 1, 1, index - 1, 2);
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("XÁC NHẬN CỦA KHÁCH HÀNG");
				this.setStyleColorGray(cells, cell, "2");
				
				cells.merge(index - 1, 4, index - 1, 5);
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue("CTY CỔ PHẦN DƯỢC PHA NAM");
				this.setStyleColorGray(cells, cell, "2");
				
				index = 5;		
				cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("Kính gửi: "+tenkh);
				this.setStyleColorGray(cells, cell, "2");
				
				index = 7;		
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("Chúng tôi đã ghi nhận nợ Quý Khách Hàng đến ngày "+obj.getdenngay()+" với chi tiết số liệu như sau:");
				this.setStyleColorGray(cells, cell, "3");
				
				index = 10;		
				if(obj.getdenngay().trim().length()>0)
				{
					cells.merge(9, 0, 9, 6);		
					cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("Đến ngày: "+obj.getdenngay());
					this.setStyleColorGray(cells, cell, "5");
				}
				
				index = 11;		
				
				if(obj.getdenngay().trim().length()>0)
				{
					cells.merge(10, 4, 10, 6);
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue("TP HCM ngày "+obj.getDateTime());
					this.setStyleColorGray(cells, cell, "3");
				}
			}

			catch(Exception ex)
			{
				ex.printStackTrace();
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