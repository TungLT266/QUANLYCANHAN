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

public class ErpBCCongNoTongHopKH extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpBCCongNoTongHopKH() {
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
	    
	    System.out.println("session.getAttribute"+session.getAttribute("loainhanvien"));
	    
		String view = util.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		obj.setView(view);		
		obj.InitErp();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTongHopCongNoKH.jsp";
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
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		String[] ctyIds = request.getParameterValues("ctyIds");
		obj.setCtyIds(ctyIds);
		obj.setView(util.antiSQLInspection(request.getParameter("view")));
		
		obj.settungay(util.antiSQLInspection(request.getParameter("tungay")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay")));
		obj.setkenhId(util.antiSQLInspection(request.getParameter("kbhid")));
		 
		obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId")));
		obj.settype(util.antiSQLInspection(request.getParameter("type")));
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
		String kh_khongcosolieu= request.getParameter("kh_khongcosolieu");
		if(kh_khongcosolieu==null){
			kh_khongcosolieu="0";
		}
		
		obj.settype(kh_khongcosolieu);
		
		
		String[] nkhIds = request.getParameterValues("nkhIds");
		String str2_ = "";
		String str_="";
		if(nkhIds != null)
		{
			for(int i = 0; i < nkhIds.length; i++)
			{
				str_ += nkhIds[i] + ",";
			}
			if(str_.length() > 0)
				str2_ = str_.substring(0, str_.length() - 1);
			
			obj.setNhomKhId(str2_);
		}
		
		String[] khIds = request.getParameterValues("khIds");
		String str2 = "";
		String str="";
		if(khIds != null)
		{
			for(int i = 0; i < khIds.length; i++)
			{
				str += khIds[i] + ",";
			}
			if(str.length() > 0)
				str2 = str.substring(0, str.length() - 1);
			
			obj.SetKHid(str2);
		}

		obj.InitErp();
		
		System.out.println("type:"+util.antiSQLInspection(request.getParameter("type")));
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTongHopCongNoKH.jsp";
		
		System.out.println("session.getAttribute"+session.getAttribute("loainhanvien"));
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCTongHopCongNoKH.xlsm");
			
			String query = getQuery(obj ); 

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
		
		String tenXuatHD = "";
		
		if(obj.gettype().equals("1")) // LẤY TÊN KHÁCH HÀNG XUẤT HÓA ĐƠN
		{
			tenXuatHD = ", a.TENXUATHD ";
		}
		else
		{
			tenXuatHD = ", ( select KH_TTHD.DONVI +','  from KHACHHANG_THONGTINHOADON KH_TTHD where b.PK_SEQ = KH_TTHD.KHACHHANG_FK for xml path('')) TENXUATHD ";
		}
		
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KH", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() );
		
		String query = ""+
		
		" SELECT CASE WHEN LEN ( ISNULL( HOADON.TENXUATHD, '' ) ) <= 1 THEN KHACHHANG.TENKH ELSE ISNULL( HOADON.TENXUATHD, '' ) END TENKH , KHACHHANG.MA, HOADON.SOHOADON,  \n"+
		"	case when HOADON.NGAYGHINHAN < '"+obj.gettungay()+"' then HOADON.TIENHD else 0 end DNDK, \n"+
		"	case when HOADON.NGAYGHINHAN < '"+obj.gettungay()+"' then ISNULL(HOADON.DCDK,0) else 0 end DCDK, \n"+ 
		"	case when HOADON.NGAYGHINHAN >= '"+obj.gettungay()+"'  AND HOADON.NGAYGHINHAN <= '"+obj.getdenngay()+"' then HOADON.TIENHD else 0 end PSNDK, \n"+
		"	case when HOADON.NGAYGHINHAN >= '"+obj.gettungay()+"'  AND HOADON.NGAYGHINHAN <= '"+obj.getdenngay()+"' then ISNULL(HOADON.PSCTK,0) else 0 end PSCDK \n"+
		" FROM \n"+
		" ( \n"+ 
		"	SELECT 'KH'+CAST(PK_SEQ AS NVARCHAR) KHID, MA, maFAST, TEN TENKH \n"+
		"	FROM KHACHHANG KH WHERE 1 = 1	\n"+strQUYEN+
		"	UNION ALL \n"+
		"	SELECT 'NPP'+CAST(PK_SEQ AS NVARCHAR) KHID,MA, '' MAFAST, TEN TENKH \n"+
		"	FROM NHAPHANPHOI \n"+
		" 	WHERE PK_SEQ in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + obj.getuserId() + "' ) \n"+
		"	UNION ALL \n"+
		"	SELECT 'NV'+CAST(PK_SEQ AS NVARCHAR) KHID, MA ,'' MAFAST, TEN TENKH \n"+
		"	FROM ERP_NHANVIEN \n"+
		" ) KHACHHANG \n"+  
		" INNER JOIN \n"+
		" ( \n"+
		" 	SELECT	a.PK_SEQ hdId, a.KBH_FK kbhId, isnull(isnull(b.TEN, c.TEN), d.TEN) as khTen, a.NGAYXUATHD, A.SOHOADON, \n"+
		"		CASE WHEN a.KHACHHANG_FK IS NOT NULL THEN 'KH'+CAST(a.KHACHHANG_FK AS NVARCHAR (255)) \n"+
		"		WHEN a.nhanvien_fk IS NOT NULL THEN 'NV'+CAST(a.nhanvien_fk AS NVARCHAR(255)) \n"+
		"		WHEN a.NPP_DAT_FK IS NOT NULL THEN 'NPP'+CAST(a.NPP_DAT_FK AS NVARCHAR(255))  END MASOSANH, \n"+
		"		a.tongtienavat TIENHD, DCDK.dathanhtoan DCDK,  PSCTK.dathanhtoan PSCTK, a.NGAYGHINHAN \n"+tenXuatHD+
		" FROM ERP_HOADONNPP a  \n"+
		" INNER JOIN KENHBANHANG e on a.KBH_FK = e.PK_SEQ " +
		//"and a.kbh_fk in " + util.quyen_kenh( obj.getuserId() ) + " "+
		" LEFT JOIN KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ \n"+ 
		" LEFT JOIN NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ \n"+
		" LEFT JOIN ERP_NHANVIEN d on a.nhanvien_fk = d.PK_SEQ \n"+
		" LEFT JOIN \n"+
		" ( \n"+
		"	SELECT  THU.HOADON_FK, SUM(ISNULL(THU.dathanhtoan,0)) dathanhtoan \n"+
		"	FROM ( \n"+
		// BẢNG KÊ THU TIỀN
		"			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS dathanhtoan \n"+
		"			FROM ERP_THUTIEN_HOADON TTHD \n"+
		"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
		"			WHERE TT.TRANGTHAI = 1 AND TTHD.LOAIHOADON = 0 AND isnull(TTHD.XOACHENHLECH,0) = 0 AND TT.BANGKE_FK IS NOT NULL \n"+
		"			AND TT.NGAYGHISO <'"+obj.gettungay()+"' "+
		"			GROUP BY HOADON_FK \n"+
		
		"  		UNION ALL \n"+
		
		// THU TIỀN KHÔNG THEO BẢNG KÊ
		"			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS dathanhtoan \n"+
		"			FROM ERP_THUTIEN_HOADON TTHD \n"+
		"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
		"			WHERE TT.TRANGTHAI = 1 AND TTHD.LOAIHOADON = 0 AND isnull(TTHD.XOACHENHLECH,0) = 0 AND TT.BANGKE_FK IS NULL \n"+
		"			AND TT.NGAYGHISO <'"+obj.gettungay()+"' "+
		"			GROUP BY HOADON_FK \n"+
		
		"  		UNION ALL \n"+
		
		// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
		"			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
		"			FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
		"			INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+
		"			WHERE TT.TRANGTHAI = 1 AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '0' \n" +		
		"			AND TT.NGAYGHISO <'"+obj.gettungay()+"' "+
		"			GROUP BY HOADON_FK \n"+
		
		"  		UNION ALL \n"+
		
		// BÙ TRỪ KHÁCH HÀNG
		"			SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS DATHANHTOAN  \n"+
		"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n"+
		"			WHERE  BT.TRANGTHAI =  1 AND BT_KH.LOAIHD = 0 \n"+
		"			AND BT.NGAYBUTRU < '"+obj.gettungay()+"' "+
		"			GROUP BY BT_KH.HOADON_FK \n"+
		
		"		) THU \n"+
		"	 GROUP BY  THU.HOADON_FK \n"+
		
		" ) DCDK on a.PK_SEQ = DCDK.hoadon_fk 	\n"+
		" LEFT JOIN \n"+
		" ( \n"+
		"	SELECT  THU.HOADON_FK, SUM(ISNULL(THU.dathanhtoan,0)) dathanhtoan \n"+
		"	FROM ( \n"+
		// BẢNG KÊ THU TIỀN
		"			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS dathanhtoan \n"+
		"			FROM ERP_THUTIEN_HOADON TTHD \n"+
		"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
		"			WHERE TT.TRANGTHAI = 1 AND TTHD.LOAIHOADON = 0 AND isnull(TTHD.XOACHENHLECH,0) = 0 AND TT.BANGKE_FK IS NOT NULL \n"+
		"			AND TT.NGAYGHISO >= '"+obj.gettungay()+"' AND TT.NGAYGHISO <= '"+obj.getdenngay()+"' "+
		"			GROUP BY HOADON_FK \n"+
		
		"  		UNION ALL \n"+
		
		// THU TIỀN KHÔNG THEO BẢNG KÊ
		"			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS dathanhtoan \n"+
		"			FROM ERP_THUTIEN_HOADON TTHD \n"+
		"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
		"			WHERE TT.TRANGTHAI = 1 AND TTHD.LOAIHOADON = 0 AND isnull(TTHD.XOACHENHLECH,0) = 0 AND TT.BANGKE_FK IS NULL \n"+
		"			AND TT.NGAYGHISO >= '"+obj.gettungay()+"' AND TT.NGAYGHISO <= '"+obj.getdenngay()+"' "+
		"			GROUP BY HOADON_FK \n"+
		
		"  		UNION ALL \n"+
		
		// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
		"			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
		"			FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
		"			INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+
		"			WHERE TT.TRANGTHAI = 1 AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '0' \n"+
		"			AND TT.NGAYGHISO >= '"+obj.gettungay()+"' AND TT.NGAYGHISO <= '"+obj.getdenngay()+"' "+
		"			GROUP BY HOADON_FK \n"+
		
		"  		UNION ALL \n"+
		
		// BÙ TRỪ KHÁCH HÀNG
		"			SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS DATHANHTOAN  \n"+
		"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n"+
		"			WHERE  BT.TRANGTHAI = 1 AND BT_KH.LOAIHD = 0 \n"+
		"			AND BT.NGAYBUTRU >= '"+obj.gettungay()+"' AND BT.NGAYBUTRU  <= '"+ obj.getdenngay() +"' "+
		"			GROUP BY BT_KH.HOADON_FK \n"+
		
		"		) THU \n"+
		"	 GROUP BY  THU.HOADON_FK \n"+
		
		" ) PSCTK on a.PK_SEQ = PSCTK.hoadon_fk  \n"+
		" WHERE a.trangthai in ( 2, 4 ) and a.congty_fk = "+obj.getErpCongtyId()+"  \n"+
		" )	HOADON ON KHACHHANG.KHID = HOADON.MASOSANH \n"+
		" WHERE 1=1 \n"+
		" ORDER BY KHACHHANG.MA ASC \n";

		System.out.println(query);
	    return query;   
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BaoCaoTongHopCongNoKH_2.xlsm");
		
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
		ResultSet ctyRs = obj.getCongtyRS(obj.getErpCongtyId());
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
		
		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		
	    cell = cells.getCell("A5"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày: " + obj.gettungay() + "  Đến ngày: " + obj.getdenngay()+ "") ;
	    
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
	
	private void setStyleColorGray(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("X1");
		
		Style style;	
		style = cell1.getStyle();
		Font font = new Font();
		font.setBold(true);
		style.setFont(font);
		
		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
    	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
    	style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
    	style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        cell.setStyle(style);
        
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
		ResultSet	rs = null;
				
		rs = db.get(query);
				
		int index = 10;
		double totalnotrongky=0;
		double totalcotrongky=0;
		double totalnodauky=0;
		double totalcodauky=0;
		
		double subnotrongky = 0;
		double subcotrongky=0;
		double subnodauky=0;
		double subcodauky=0;
		
		String ma = "";
		String ten = "";
		String ma_bk = "";
		String ten_bk = "";
		Style style;
		Font font;
		
		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				while (rs.next())
				{		
					
					ma = rs.getString("MA");
					ten = rs.getString("TENKH");
					if(ten_bk.length() == 0){
						ten_bk = ten;
						ma_bk = ma;
					}
					
					if(!ten.equals(ten_bk) & ten_bk.length() > 0){
						
						cell = cells.getCell("A" + String.valueOf(index));		
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, ma_bk);
						this.setStyleColorGray(cells, cell);
					
						cell = cells.getCell("B" + String.valueOf(index));	
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, ten_bk);
						this.setStyleColorGray(cells, cell);

						cell = cells.getCell("C" + String.valueOf(index));	
						this.setStyleColorGray(cells, cell);
						
						cell = cells.getCell("D" + String.valueOf(index));	cell.setValue(subnodauky);	
						this.setStyleColorGray(cells, cell);

						cell = cells.getCell("E" + String.valueOf(index));	cell.setValue(subcodauky);		
						this.setStyleColorGray(cells, cell);

						cell = cells.getCell("F" + String.valueOf(index));	cell.setValue(subnotrongky);		
						this.setStyleColorGray(cells, cell);

						cell = cells.getCell("G" + String.valueOf(index));	cell.setValue(subcotrongky);		
						this.setStyleColorGray(cells, cell);

						double temp = subnodauky - subcodauky + subnotrongky - subcotrongky;
						
						if(temp >= 0){
							cell = cells.getCell("H" + String.valueOf(index));	cell.setValue(temp);	
							
						}else{
							cell = cells.getCell("H" + String.valueOf(index));	cell.setValue(0);
							
						}
						this.setStyleColorGray(cells, cell);
					
						if(temp < 0){
							cell = cells.getCell("I" + String.valueOf(index));	cell.setValue(temp);
							
						}else{
							cell = cells.getCell("I" + String.valueOf(index));	cell.setValue(0);
								
						}
						this.setStyleColorGray(cells, cell);
						
						subnotrongky = 0;
						subcotrongky = 0;
						subnodauky = 0;
						subcodauky = 0;
						ma_bk = ma;
						ten_bk = ten;
						index++;
					}
						
					subnotrongky += rs.getDouble("PSNDK");
					subcotrongky += rs.getDouble("PSCDK");
					subnodauky += rs.getDouble("DNDK");
					subcodauky += rs.getDouble("DCDK");
						
					totalnodauky= totalnodauky + rs.getDouble("DNDK");
					totalcodauky= totalcodauky + rs.getDouble("DCDK");
					
					totalnotrongky = totalnotrongky + rs.getDouble("PSNDK");
					totalcotrongky = totalcotrongky +rs.getDouble("PSCDK");
					
					cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(rs.getString("MA"));	
					
					setBorder(cell, 1);
					
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("TENKH"));
					setBorder(cell, 1);
					
					cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("SOHOADON"));	
					
					setBorder(cell, 1);
					
					cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getDouble("DNDK"));	
					
					this.setStyleColorNormar(cells, cell);
					
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getDouble("DCDK"));
					this.setStyleColorNormar(cells, cell);
					
					cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getDouble("PSNDK"));	
					this.setStyleColorNormar(cells, cell);
					
					cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getDouble("PSCDK"));
					this.setStyleColorNormar(cells, cell);
					
					double temp = rs.getDouble("DNDK") - rs.getDouble("DCDK") + rs.getDouble("PSNDK") - rs.getDouble("PSCDK");
					
					if(temp >= 0){
						cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(temp);
					}else{
						cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(0);
					}
					this.setStyleColorNormar(cells, cell);
					
					if(temp < 0){
						cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(temp);
					}else{
						cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(0);
					}
					this.setStyleColorNormar(cells, cell);
					
					index++;					
				}

				if (rs != null){
					rs.close();
				}
				
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("CỘNG");
				this.setStyleColorGray(cells, cell);
				//this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell);
				
				cell = cells.getCell("C" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell);
				
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(totalnodauky);
				this.setStyleColorGray(cells, cell);
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(totalcodauky);	
				this.setStyleColorGray(cells, cell);
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(totalnotrongky);
				this.setStyleColorGray(cells, cell);
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(totalcotrongky);
				this.setStyleColorGray(cells, cell);
				
				double temp = totalnodauky - totalcodauky + totalnotrongky - totalcotrongky;
				if(temp >= 0){
					cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(temp);
				}else{
					cell = cells.getCell("H" + String.valueOf(index));		cell.setValue("0");
				}
				this.setStyleColorGray(cells, cell);
				
				if(temp <= 0){
					cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(temp);
				}else{
					cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(0);
				}
				this.setStyleColorGray(cells, cell);
				
				index=index+2;
				
				cell = cells.getCell("B" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");

				index++;
				
				cell = cells.getCell("B" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Người lập biểu");
				
				cell = cells.getCell("E" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Kế toán trưởng");
				
				cell = cells.getCell("H" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Giám đốc");

				cells.setColumnWidth(0, 10.0f);
				cells.setColumnWidth(1, 40.0f);
				cells.setColumnWidth(2, 10.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
				
			}

			catch(java.sql.SQLException ex)
			{
				System.out.println(ex.toString());
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