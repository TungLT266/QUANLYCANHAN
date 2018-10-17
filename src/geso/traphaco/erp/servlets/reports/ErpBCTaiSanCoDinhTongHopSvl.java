package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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

public class ErpBCTaiSanCoDinhTongHopSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpBCTaiSanCoDinhTongHopSvl() {
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
		
		//null thi se lay khach hang trong csdl
		obj.setRsKhErp(null);
		obj.InitErp();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTaiSanCoDinhTongHop.jsp";
		
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
		
		System.out.println("view:"+request.getParameter("view"));
		
		String loaibc = util.antiSQLInspection(request.getParameter("loaiBC")) != null ? util.antiSQLInspection(request.getParameter("loaiBC")) : "";
		obj.setLoaiBC(loaibc);
		
		String view = util.antiSQLInspection(request.getParameter("view")) != null ? util.antiSQLInspection(request.getParameter("view")) : "";
		
		if(loaibc.equals("1"))
			view = "1";
		obj.setView(view);		
		
		obj.settungay(util.antiSQLInspection(request.getParameter("tungay"))!= null ? util.antiSQLInspection(request.getParameter("tungay")): "");
		obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay"))!= null ? util.antiSQLInspection(request.getParameter("denngay")): "");
		
		obj.setFromMonth(util.antiSQLInspection(request.getParameter("tuthang"))!= null ? util.antiSQLInspection(request.getParameter("tuthang")): "");
		obj.setToMonth(util.antiSQLInspection(request.getParameter("denthang"))!= null ? util.antiSQLInspection(request.getParameter("denthang")): "");
		
		obj.setFromYear(util.antiSQLInspection(request.getParameter("tunam"))!= null ? util.antiSQLInspection(request.getParameter("tunam")): "");
		obj.setToYear(util.antiSQLInspection(request.getParameter("dennam"))!= null ? util.antiSQLInspection(request.getParameter("dennam")): "" );
		
		obj.setLoaiTaiSanId(util.antiSQLInspection(request.getParameter("loaits")) != null ? util.antiSQLInspection(request.getParameter("loaits")): "");
		obj.setTTCPId(util.antiSQLInspection(request.getParameter("ttcpid")) != null ? util.antiSQLInspection(request.getParameter("ttcpid")): "");
		
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		
		/*obj.setErpKhachHangId(util.antiSQLInspection(request.getParameter("khid")));
		obj.setRsKhErp(null);*/
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTaiSanCoDinhTongHop.jsp";
		obj.InitErp();
		
		String[] taisanIds = request.getParameterValues("taisanIds");
		String str2 = "";
		String str="";
		if(taisanIds != null)
		{
			for(int i = 0; i < taisanIds.length; i++)
			{
				str += taisanIds[i] + ",";
			}
			if(str.length() > 0)
				str2 = str.substring(0, str.length() - 1);
			
			obj.settaisanIds(str2);
		}
		
		System.out.println(str);
		
		
		System.out.println("Action la 1: " + action);
		
		if (action.equals("Taomoi")) 
		{
			response.setContentType("application/xlsm");
			
			if(obj.getLoaiBC().equals("0"))  // BC tài sản tổng hợp tất cả
			{				
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoTSCDTonghop.xlsm");
					
		        try 
		        {
					if(!CreatePivotTable_TongHop(out, response, request, obj))
					{
						response.setContentType("text/html");
					    PrintWriter writer = new PrintWriter(out);
					    writer.print("Không có dữ liệu trong thời gian đã chọn");
					    writer.close();
					}
				} 
		        catch (Exception e) 
		        {
		        	obj.setMsg("Không thể tạo báo cáo " + e.getMessage());
					e.printStackTrace();
					session.setAttribute("obj", obj);
					response.sendRedirect(nextJSP);
					return;
					
				}
			}
			else if(obj.getLoaiBC().equals("1"))  // BC Tài sản tổng hợp theo loại
			{				
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoTSCDTongHopTheoLoai.xlsm");
	
		        try 
		        {
					if(!CreatePivotTable_BCTheoLoaiTS(out, response, request, obj))
					{
						response.setContentType("text/html");
					    PrintWriter writer = new PrintWriter(out);
					    writer.print("Không có dữ liệu trong thời gian đã chọn");
					    writer.close();
					}
				} 
		        catch (Exception e) 
		        {
		        	obj.setMsg("Không thể tạo báo cáo " + e.getMessage());
					e.printStackTrace();
					session.setAttribute("obj", obj);
					response.sendRedirect(nextJSP);
					return;
					
				}
			}
			else if(obj.getLoaiBC().equals("2"))  // BC Tài sản chi tiết theo loại tài sản
			{				
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChiTietTSCDTheoLoaiTS.xlsm");
					
		        try 
		        {
					if(!CreatePivotTable_BCCTLoaiTS(out, response, request, obj))
					{
						response.setContentType("text/html");
					    PrintWriter writer = new PrintWriter(out);
					    writer.print("Không có dữ liệu trong thời gian đã chọn");
					    writer.close();
					}
				} 
		        catch (Exception e) 
		        {
		        	obj.setMsg("Không thể tạo báo cáo " + e.getMessage());
					e.printStackTrace();
					session.setAttribute("obj", obj);
					response.sendRedirect(nextJSP);
					return;
					
				}
			}
			else if(obj.getLoaiBC().equals("3"))  // BC Chi tiết tài sản theo TTCP
			{				
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChiTietTSCD.xlsm");
	
		        try 
		        {
					if(!CreatePivotTable_BCCTLoaiTSTTCP(out, response, request, obj))
					{
						response.setContentType("text/html");
					    PrintWriter writer = new PrintWriter(out);
					    writer.print("Không có dữ liệu trong thời gian đã chọn");
					    writer.close();
					}
				} 
		        catch (Exception e) 
		        {
		        	obj.setMsg("Không thể tạo báo cáo " + e.getMessage());
					e.printStackTrace();
					session.setAttribute("obj", obj);
					response.sendRedirect(nextJSP);
					return;
					
				}
			}
			else if(obj.getLoaiBC().equals("4"))  // BC Tăng giảm tài sản
			{				
				response.setHeader("Content-Disposition", "attachment; filename=BCTaiSanTangGiamTS.xlsm");
					
		        try 
		        {
					if(!CreatePivotTable_TangGiamTS(out, response, request, obj))
					{
						response.setContentType("text/html");
					    PrintWriter writer = new PrintWriter(out);
					    writer.print("Không có dữ liệu trong thời gian đã chọn");
					    writer.close();
					}
				} 
		        catch (Exception e) 
		        {
					obj.setMsg("Không thể tạo báo cáo " + e.getMessage());
					e.printStackTrace();
					session.setAttribute("obj", obj);
					response.sendRedirect(nextJSP);
					return;
					
				}
			}
			
		}
		else
		{
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
			return;
		}
	}
	
	private String getQueryBCTongHop(IStockintransit obj) 
	{
		String query = "";
		
		query = 
		"SELECT DISTINCT MH.PK_SEQ AS MHID, MH.SOPO, MH.NGAYMUA, TS.PK_SEQ AS TSID, TS.MA AS MATS, LTS.MA AS LOAITS, \n"+
		"		       TS.DIENGIAI AS TENTS, NH.NGAYNHAN,TS.THANHTIEN AS NGUYENGIA ,TS.SOTHANGKH ,  \n"+
		"              ISNULL( (SELECT DISTINCT COUNT(*) FROM ERP_KHAUHAOTAISAN WHERE TRANGTHAI = 1 AND TAISAN_FK = TS.PK_SEQ GROUP BY THANG, NAM) , 0) AS SOTHANGDAKH, \n"+
		"		       KH.KHAUHAO AS KHAUHAOTHANG , CAST( (KH.KHAUHAO/TS.THANHTIEN*100) AS NUMERIC(18,2)) AS TYLEKH , \n"+
        "              CASE WHEN LEN(KH.THANG)< 2 THEN  CAST(KH.NAM AS NVARCHAR(10)) + '-' + '0' + CAST(KH.THANG AS NVARCHAR(10))   \n"+
        "                   ELSE   CAST(KH.NAM AS NVARCHAR(10)) + '-' + CAST(KH.THANG AS NVARCHAR(10))  \n"+
        "               END AS NGAYKH  \n"+
        "FROM ERP_MUAHANG MH INNER JOIN ERP_NHANHANG NH ON  MH.PK_SEQ = NH.MUAHANG_FK \n"+
        "            		 INNER JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH.PK_SEQ = NH_SP.NHANHANG_FK \n"+
		"					 INNER JOIN ERP_TAISANCODINH TS ON NH_SP.TAISAN_FK = TS.PK_SEQ \n"+
		"					 INNER JOIN Erp_LOAITAISAN LTS ON TS.LOAITAISAN_FK = LTS.PK_SEQ \n"+
		"					 INNER JOIN ERP_KHAUHAOTAISAN KH ON TS.pk_seq = KH.TAISAN_FK \n"+
		"WHERE KH.TRANGTHAI = 1 AND MH.LOAIHANGHOA_FK = '1' AND KH.THANG = "+ obj.getFromMonth() +"  AND KH.NAM = "+ obj.getFromYear() +" \n";
		
		
		return query;
	}

	private boolean CreatePivotTable_BCCTLoaiTS(OutputStream out,HttpServletResponse response, HttpServletRequest request,IStockintransit obj) 
	{

		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
			
		try
		{
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCTaiSanChiTietTheoLoaiTS.xlsx");
			
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			//Câu query lấy tổng hợp giá trị 
			String queryTongGiaTriTS = getQueryTongGiaTriTS(obj);
			
			//Câu query lấy loại tài sản
			
			FillData_BCChiTietGiaTriTS(workbook, queryTongGiaTriTS, obj);
			
			workbook.save(out);
			fstream.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return true;	

	}

	
	private boolean CreatePivotTable_BCCTLoaiTSTTCP(OutputStream out,HttpServletResponse response, HttpServletRequest request,IStockintransit obj) 
	{

		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
			
		try
		{
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCTaiSanChiTietTheoLoaiTSTTCP.xlsx");
			
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			//Câu query lấy tổng hợp giá trị 
			String queryTongGiaTriTS = getQueryTongGiaTriTSTTCP(obj);
			
			//Câu query lấy loại tài sản
			
			FillData_BCChiTietGiaTriTSTTCP(workbook, queryTongGiaTriTS, obj);
			
			workbook.save(out);
			fstream.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return true;	

	}

	
	private void FillData_BCTongHopTS(Workbook workbook, String query,IStockintransit obj)
	{
		NumberFormat formatter = new DecimalFormat("#,###,###");

		System.out.println("cau qery" + query);
		
		ResultSet rs = null;
		dbutils db = new dbutils();
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			
			Cells cells = worksheet.getCells();
			Style style;
			
			
			rs = db.get(query);
			int index = 8;
		    Cell cell = null;

		    
		    double nguyengia = 0;
		    
		    double khauhaothang = 0;
		    double khauhaonam = 0;		    
		    double tongkhauhao = 0;		    
		    double tyleKH = 0;
		    double conlai = 0;		    
		    int sothangdakhauhao = 0;
		    
		    double tt_nguyengia = 0;
		    double tt_khauhaothang = 0;
		    double tt_khauhaonam = 0;
		    double tt_tongkhauhao =0;
		    double tt_conlai = 0;

		    
		    cell = cells.getCell("G" + String.valueOf(3));	cell.setValue("Tháng "+obj.getFromMonth()+" năm "+obj.getFromYear());	
		    
		    if(rs!=null)
		    {
		    	System.out.println("vao xuat excell");
			while (rs.next())
			{	
				cell = cells.getCell("A" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(rs.getString("SOPO"));	
				cell = cells.getCell("B" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(rs.getString("NGAYMUA"));			
				cell = cells.getCell("C" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(rs.getString("MATS"));
				cell = cells.getCell("D" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(rs.getString("TENTS"));
				cell = cells.getCell("E" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue("");
				cell = cells.getCell("F" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(rs.getString("NGAYNHAN"));
				cell = cells.getCell("G" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(rs.getString("NGAYKH"));
				cell = cells.getCell("H" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(rs.getString("LOAITS"));
				
				nguyengia = Math.round( rs.getDouble("NGUYENGIA"));
				khauhaothang = Math.round(rs.getDouble("KHAUHAOTHANG"));
				tyleKH = rs.getDouble("TYLEKH");
				
				
				cell = cells.getCell("I" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(formatter.format(nguyengia));
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);	
				cell.setStyle(style);
				
				cell = cells.getCell("J" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(formatter.format(tyleKH));
				cell = cells.getCell("K" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(rs.getString("SOTHANGDAKH"));
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);	
				cell.setStyle(style);
				
				
				cell = cells.getCell("L" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(formatter.format(khauhaothang));
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);	
				cell.setStyle(style);
				
				
				khauhaonam = 0 ;
				cell = cells.getCell("M" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(formatter.format(khauhaonam));
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);	
				cell.setStyle(style);
				
				tongkhauhao = rs.getInt("SOTHANGDAKH")*khauhaothang;				
				conlai = nguyengia - tongkhauhao;
				
				cell = cells.getCell("N" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(formatter.format(tongkhauhao));
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);	
				cell.setStyle(style);
				
				cell = cells.getCell("O" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(formatter.format(conlai));
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.RIGHT);	
				cell.setStyle(style);
				
				tt_nguyengia += nguyengia;
				tt_khauhaothang += khauhaothang;
				tt_khauhaonam += khauhaonam;
				tt_tongkhauhao += tongkhauhao;
				
				index++;
			}
		    }
			if(rs!=null){
				rs.close();
			}	
			
			tt_conlai = tt_nguyengia - tt_tongkhauhao;
			
			
			cells.merge(index - 1, 0, index - 1, 8);
			cell = cells.getCell("A" + String.valueOf(index));	SetStyleForDataCell2(cell);	cell.setValue(" Tổng cộng:                         " + formatter.format(tt_nguyengia));
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.RIGHT);	
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("B" + index));
			cell = CreateBorderSetting(cells.getCell("C" + index));
			cell = CreateBorderSetting(cells.getCell("D" + index));
			cell = CreateBorderSetting(cells.getCell("E" + index));
			cell = CreateBorderSetting(cells.getCell("F" + index));
			cell = CreateBorderSetting(cells.getCell("G" + index));
			cell = CreateBorderSetting(cells.getCell("H" + index));
			cell = CreateBorderSetting(cells.getCell("I" + index));

			cells.merge(index - 1, 9, index - 1, 10);
			cell = cells.getCell("J" + String.valueOf(index));	SetStyleForDataCell2(cell);	cell.setValue("");
			cell = CreateBorderSetting(cells.getCell("K" + index));
			
			cell = cells.getCell("L" + String.valueOf(index));	SetStyleForDataCell2(cell);	cell.setValue(formatter.format(tt_khauhaothang));
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.RIGHT);	
			cell.setStyle(style);
			
			cell = cells.getCell("M" + String.valueOf(index));	SetStyleForDataCell2(cell);	cell.setValue(formatter.format(tt_khauhaonam));
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.RIGHT);	
			cell.setStyle(style);
			
			cell = cells.getCell("N" + String.valueOf(index));	SetStyleForDataCell2(cell);	cell.setValue(formatter.format(tt_tongkhauhao));
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.RIGHT);	
			cell.setStyle(style);
			
			cell = cells.getCell("O" + String.valueOf(index));	SetStyleForDataCell2(cell);	cell.setValue(formatter.format(tt_conlai));
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.RIGHT);	
			cell.setStyle(style);
			

			
			index = index + 3;
			cell = cells.getCell("I" + String.valueOf(index));	cell.setValue("Ngày 	   tháng	    năm");
			index++;
			cell = cells.getCell("C" + String.valueOf(index));	cell.setValue("Người lập biểu");
			cell = cells.getCell("I" + String.valueOf(index));	cell.setValue("Kế toán trưởng");
			
			index++;
			cell = cells.getCell("C" + String.valueOf(index ));	cell.setValue("(Ký, họ tên)");
			cell = cells.getCell("I" + String.valueOf(index));	cell.setValue("(Ký, họ tên)");
			
			ReportAPI.setHidden(workbook,14);
			 
		
		    			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();			
		}
	
		
	}

	public String getQuery_BCTheoLoaiTS(IStockintransit obj)
	{
		String query = "";
		query =
		" SELECT lts.pk_seq, lts.ma, lts.diengiai, isnull(dk.nguyengia,0) nguyengiadk, isnull(dk.khauhao,0) khauhaodk, (isnull(dk.nguyengia,0) - isnull(dk.khauhao,0)) conlaidk, \n"+ 
		"     isnull(tk.khauhao,0) khauhaotk, (isnull(dk.khauhao,0) + isnull(tk.khauhao,0)) khauhaock, ISNULL(tk.nguyengia,0) nguyengiack \n"+
		" FROM \n"+ 
		" ERP_LOAITAISAN lts LEFT JOIN \n"+
		"	( \n"+
		"		SELECT b.pk_seq,b.ma, b.diengiai, SUM(a.ThanhTien) nguyengia, SUM(c.KHAUHAO) khauhao \n"+
		"		FROM   ERP_TAISANCODINH a INNER JOIN Erp_LOAITAISAN b ON a.LOAITAISAN_FK = b.pk_seq \n"+
		"			   INNER JOIN ERP_KHAUHAOTAISAN c ON a.pk_seq = c.TAISAN_FK \n"+
		"		WHERE  c.TRANGTHAI = 1 and a.trangthai = 1 and c.THANG < "+obj.getFromMonth()+" and c.NAM <= "+obj.getFromYear()+" \n"+
		"		GROUP BY  b.pk_seq,b.ma, b.diengiai \n"+
		"	) dk ON lts.pk_seq = dk.pk_seq LEFT JOIN \n"+
		"	( \n"+
		"		SELECT b.pk_seq,b.ma, b.diengiai, SUM(a.ThanhTien) nguyengia, SUM(c.KHAUHAO) khauhao \n"+
		"		FROM   ERP_TAISANCODINH a INNER JOIN Erp_LOAITAISAN b ON a.LOAITAISAN_FK = b.pk_seq \n"+
		"			   INNER JOIN ERP_KHAUHAOTAISAN c ON a.pk_seq = c.TAISAN_FK \n"+
		"		WHERE  c.TRANGTHAI = 1 and a.trangthai = 1 and c.THANG = "+obj.getFromMonth()+" and c.NAM = "+obj.getFromYear()+" \n"+
		"		GROUP BY  b.pk_seq,b.ma, b.diengiai \n"+
		" ) tk ON lts.pk_seq = tk.pk_seq \n"+
		" where 1 = 1 and lts.trangthai = 1 "+
		" and (isnull(dk.nguyengia,0) !=0 or isnull(dk.khauhao,0) !=0 or isnull(tk.khauhao,0) !=0 )";
		if(obj.getLoaiTaiSanId().trim().length()>0)
			query += " and lts.pk_seq ='"+obj.getLoaiTaiSanId()+"'";
		
		System.out.println("init: \n"+query);
		return query;
	}
	
	public String getQuery_BCTheoLoaiTSTTCP(IStockintransit obj, String idlts)
	{
		String ttcp = "";
		if(obj.getTTCPId().trim().length()>0)
			ttcp = " and cp.pk_seq = "+obj.getTTCPId()+"";
		
		String query =
		" SELECT cp.PK_SEQ, cp.MA, cp.DIENGIAI,  ISNULL(dk.nguyengia,0) nguyengiadk, \n"+
		"	     ISNULL(dk.khauhao,0) khauhaodk, (ISNULL(dk.nguyengia, 0)- ISNULL(dk.khauhao,0)) conlaidk, \n"+ 
		"	     ISNULL(tk.khauhao, 0) khauhaotk, ISNULL(tk.nguyengia,0)  nguyengiack \n"+
		" FROM \n"+ 
		" ERP_TRUNGTAMCHIPHI cp \n"+
		" LEFT JOIN \n"+
		" ( \n"+
		"	SELECT 	a.TTCP_FK, SUM(ISNULL(a.ThanhTien,0)) nguyengia, SUM(ISNULL(c.KHAUHAO,0)) khauhao \n"+
		"	FROM 	ERP_TAISANCODINH a INNER JOIN ERP_KHAUHAOTAISAN c ON a.pk_seq = c.TAISAN_FK \n"+
		"	WHERE 	c.TRANGTHAI = 1 and a.trangthai = 1 and c.THANG < "+obj.getFromMonth()+" and c.NAM  <="+obj.getFromYear()+" \n"+
		"		  	and a.LOAITAISAN_FK = "+idlts+" "+
		"	GROUP BY a.TTCP_FK \n"+
		" ) dk ON dk.TTCP_FK = cp.PK_SEQ \n"+
		" LEFT JOIN \n"+ 
		" ( \n"+
		"	SELECT 	a.TTCP_FK, SUM(ISNULL(a.ThanhTien,0)) nguyengia, SUM(ISNULL(c.KHAUHAO,0)) khauhao \n"+
		"	FROM 	ERP_TAISANCODINH a INNER JOIN ERP_KHAUHAOTAISAN c ON a.pk_seq = c.TAISAN_FK \n"+
		"	WHERE 	c.TRANGTHAI = 1 and a.trangthai = 1 and c.THANG = "+obj.getFromMonth()+" and c.NAM  = "+obj.getFromYear()+" \n"+
		"		  	and a.LOAITAISAN_FK = "+idlts+" "+
		"	GROUP BY a.TTCP_FK \n"+
		" ) tk ON tk.TTCP_FK = cp.PK_SEQ \n"+
		" where 1 = 1 and cp.trangthai = 1  \n"+ 
		" 		and (isnull(dk.nguyengia,0) !=0 or isnull(dk.khauhao,0) !=0 or isnull(tk.khauhao,0) !=0 ) "+ttcp;
		
		System.out.println("init: \n"+query);
		return query;
	}
	
	public String getQuery(IStockintransit obj)
	{		
		String query = "";		
	    return query;   
	}
	
	public String getTaiSanTang(IStockintransit obj)
	{
		String query = 		
		" SELECT b.diengiai as loaits,a.pk_seq, a.ma, a.diengiai as tents, d.diengiai donviSD, a.thangbatdauKH, '' ngaythanhly, a.ThanhTien, khauhao.khauhao, (a.ThanhTien - khauhao.khauhao) conlai \n"+
		" FROM	ERP_TAISANCODINH a INNER JOIN Erp_LOAITAISAN b on a.LOAITAISAN_FK = b.pk_seq \n"+
		"		INNER JOIN Erp_NHOMTAISAN d ON a.nhomtaisan_fk = d.pk_seq \n"+
		"		INNER JOIN \n"+ 
		"		 ( \n"+
		"			SELECT 	a.pk_seq, SUM(ISNULL(c.KHAUHAO,0)) khauhao \n"+ 
		"			FROM 	ERP_TAISANCODINH a INNER JOIN ERP_KHAUHAOTAISAN c ON a.pk_seq = c.TAISAN_FK \n"+ 
		"			WHERE 	c.TRANGTHAI = 1 and a.trangthai = 1 and c.THANG = "+obj.getFromMonth()+" and c.NAM  <= "+obj.getFromYear()+" \n"+ 
		"			        and MONTH(a.thangbatdauKH) = "+obj.getFromMonth()+" and YEAR(a.thangbatdauKH) = "+obj.getFromYear()+" \n"+
		"		  	GROUP BY a.pk_seq \n"+ 
		"		 ) khauhao ON khauhao.pk_seq = a.pk_seq \n"+  
		" WHERE a.trangthai = 1 \n";
		
		System.out.println(query);
	    return query;   
	}
	
	public String getTaiSanGiam(IStockintransit obj)
	{
		String query = 		
			" SELECT b.diengiai as loaits ,a.pk_seq, a.ma, a.diengiai as tents, d.diengiai donviSD, a.thangbatdauKH, '' ngaythanhly, a.ThanhTien, khauhao.khauhao, (a.ThanhTien - khauhao.khauhao) conlai \n"+
			" FROM	ERP_TAISANCODINH a INNER JOIN Erp_LOAITAISAN b on a.LOAITAISAN_FK = b.pk_seq \n"+
			"		INNER JOIN Erp_NHOMTAISAN d ON a.nhomtaisan_fk = d.pk_seq \n"+
			"		INNER JOIN \n"+ 
			"		 ( \n"+
			"			SELECT 	a.pk_seq, SUM(ISNULL(c.KHAUHAO,0)) khauhao \n"+ 
			"			FROM 	ERP_TAISANCODINH a INNER JOIN ERP_KHAUHAOTAISAN c ON a.pk_seq = c.TAISAN_FK \n"+ 
			"			WHERE 	c.TRANGTHAI = 1 and a.trangthai = 1 and c.THANG < "+obj.getFromMonth()+" and c.NAM  <= "+obj.getFromYear()+"  \n"+
			"			        and (MONTH(a.thangbatdauKH) < "+obj.getFromMonth()+" and YEAR(a.thangbatdauKH) <= "+obj.getFromYear()+") \n"+
			"		  	GROUP BY a.pk_seq \n"+ 
			"		 ) khauhao ON khauhao.pk_seq = a.pk_seq \n"+  
			" WHERE a.trangthai = 1 \n";
		
		System.out.println(query);
	    return query;   
	}
	
	private boolean CreatePivotTable_BCTheoLoaiTS(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj) throws Exception 
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
				
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCChiTietSoTSCD.xlsm");
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		//CreateHeader_getQuery_BCTheoLoaiTS(workbook,obj);
		String query = getQuery_BCTheoLoaiTS(obj);
		FillData_BCTheoLoaiTS(workbook, query, obj);			
		workbook.save(out);
		fstream.close();
		
		return true;	
	}
	
	private boolean CreatePivotTable_TangGiamTS(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
				
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCTaiSanTangGiamTS.xlsx");
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		//CreateHeader(workbook, obj);
		String sqlTSTang = this.getTaiSanTang(obj);
		
		String sqlTSGiam = this.getTaiSanGiam(obj);
		
		isFillData = FillData_TSTangGiam(workbook, obj, sqlTSTang, sqlTSGiam);
   
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
	
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
				
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BaoCaoChiTietCongNoNCC.xlsm");
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		CreateHeader(workbook, obj);
		String sqldauky = this.getQuery(obj);
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
	    
	    cell = cells.getCell("D5"); cell.setValue("BÁO CÁO CHI TIẾT CÔNG NỢ NHÀ CUNG CẤP");
	    
	    cell = cells.getCell("A8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày: " + obj.gettungay());
	    
	    cell = cells.getCell("C8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày: " + obj.getdenngay());
	    	    
	    cell = cells.getCell("A9"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Mã và tên NCC: " + obj.getErpKhachHangId().substring(obj.getErpKhachHangId().indexOf(",") + 1, obj.getErpKhachHangId().length())); 

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
		Cell cell1 = cells.getCell("X1");
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
		
		for(int i = 0;i < 7; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
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
		cell = cells.getCell("F12"); cell.setValue(totalnocodauky);
		this.setStyleColorNormar(cells, cell);

		// Lay du lieu trong ky
		rs = db.get(sqlTrongky);
		int index = 13;
		
		if (rs != null) 
		{
			try 
			{
				
				while (rs.next())
				{		
					
					totalgiamtrongky=totalgiamtrongky+rs.getDouble("GIAMTRONGKY");
					totaltangtrongky=totaltangtrongky +rs.getDouble("TANGTRONGKY");
					
					cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index-12);	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("ID"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("NGAY"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getDouble("TANGTRONGKY"));
					this.setStyleColorNormar(cells, cell);
					
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getDouble("GIAMTRONGKY"));	
					this.setStyleColorNormar(cells, cell);
					
					cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(0);
						
					
					index++;					
				}

				if (rs != null){
					rs.close();
				}
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("");
				
				this.setStyleColorGray(cells, cell, "0");

				cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("Cuối kỳ:");
				
				this.setStyleColorGray(cells, cell, "0");
								
				cell = cells.getCell("C" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(totaltangtrongky);	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(totalgiamtrongky);	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(totalnocodauky+totaltangtrongky-totalgiamtrongky);					index=index+3;
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("B" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Lập biểu");
				
				cell = cells.getCell("F" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Giám đốc");
				
				index=index+5;
				cell = cells.getCell("B" + String.valueOf(index));
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
				
				cell = cells.getCell("F" + String.valueOf(index));
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
				
		    	cells.setColumnWidth(0, 8.0f);
		    	cells.setColumnWidth(1, 15.0f);
		    	cells.setColumnWidth(2, 66.0f);
		    	cells.setColumnWidth(3, 15.0f);
		    	cells.setColumnWidth(4, 21.0f);
		    	cells.setColumnWidth(5, 21.0f);
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
	
	private void SetStyleForCell(Cell cell, int borderWidth, Color borderColor, Color backgroundColor, Color textColor, boolean bold) {
		Style style = cell.getStyle();
		style.setBorderColor(BorderType.TOP, borderColor);
		style.setBorderColor(BorderType.BOTTOM, borderColor);
		style.setBorderColor(BorderType.LEFT, borderColor);
		style.setBorderColor(BorderType.RIGHT, borderColor);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setColor(backgroundColor);
		Font font = new Font();
		font.setColor(textColor);
		font.setBold(bold);
		style.setFont(font);
		cell.setStyle(style);
	}
	
	private void SetStyleForCell2(Cell cell, int borderWidth, Color borderColor, Color backgroundColor, Color textColor, boolean bold) {
		Style style = cell.getStyle();
		style.setBorderColor(BorderType.TOP, borderColor);
		style.setBorderColor(BorderType.BOTTOM, borderColor);
		style.setBorderColor(BorderType.LEFT, borderColor);
		style.setBorderColor(BorderType.RIGHT, borderColor);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setColor(backgroundColor);
		Font font = new Font();
		font.setColor(textColor);
		font.setBold(bold);
		style.setFont(font);
		cell.setStyle(style);
	}
	
	private void SetStyleForDataCell(Cell cell) {
		SetStyleForCell(cell, 1, Color.GRAY, Color.WHITE, Color.BLACK, false);
	}
	
	private void SetStyleForDataCell2(Cell cell) {
		SetStyleForCell2(cell, 1, Color.GRAY, Color.WHITE, Color.BLACK, true);
	}
	
	private void FillData_BCTheoLoaiTS(Workbook workbook, String query, IStockintransit obj) throws Exception
	{
		System.out.println("cau qery" + query);
		
		NumberFormat formatter = new DecimalFormat("#,###,###");
		
		ResultSet rs = null;
		dbutils db = new dbutils();
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			
			Cells cells = worksheet.getCells();
			
			
			Number number;
			
			rs = db.get(query);
			int index = 10;
		    Cell cell = null;
		    String thets="";
		    String tents = "";
		    
		    double nguyengiadk = 0;
		    double khauhaodk = 0;
		    double conlaidk = 0;
		    
		    double khauhaotk = 0;
		    
		    double nguyengiack = 0;
		    double khauhaock =0;
		    double conlaick = 0;
		    
		    
		    double tt_nguyengiadk = 0;
		    double tt_khauhaodk = 0;
		    double tt_conlaidk = 0;
		    
		    double tt_khauhaotk = 0;
		    
		    double tt_nguyengiack = 0;
		    double tt_khauhaock =0;
		    double tt_conlaick = 0;
		    
		    cell = cells.getCell("D" + String.valueOf(5));	cell.setValue("Tháng "+obj.getFromMonth()+" năm "+obj.getFromYear());	
		    
		    if(rs!=null)
		    {
		    	System.out.println("vao xuat excell");
			while (rs.next())
			{		
				cell = cells.getCell("B" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(rs.getString("ma"));			
				cell = cells.getCell("C" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(rs.getString("diengiai"));
				
				nguyengiadk = Math.round( rs.getDouble("nguyengiadk"));
				khauhaodk = Math.round(rs.getDouble("khauhaodk"));
				conlaidk = Math.round(rs.getDouble("conlaidk"));
				
				cell = cells.getCell("D" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(formatter.format(nguyengiadk));
				cell = cells.getCell("E" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(formatter.format(khauhaodk));
				cell = cells.getCell("F" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(formatter.format(conlaidk));
				
				khauhaotk = Math.round(rs.getDouble("khauhaotk"));
				cell = cells.getCell("G" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(formatter.format(khauhaotk));
				
				nguyengiack = Math.round(rs.getDouble("nguyengiack"));
				khauhaock = Math.round(rs.getDouble("khauhaock"));
				conlaick = nguyengiack - khauhaock;
				
				cell = cells.getCell("H" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(formatter.format(nguyengiack));
				cell = cells.getCell("I" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(formatter.format(khauhaock));
				cell = cells.getCell("J" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(formatter.format(conlaick));
				
				tt_nguyengiadk += nguyengiadk;
				tt_khauhaodk += khauhaodk;
				tt_conlaidk += conlaidk;
				
				tt_khauhaotk += khauhaotk;
				
				tt_nguyengiack += nguyengiack;
				tt_khauhaock += khauhaock;
				tt_conlaick += conlaick;
				
				index++;
			}
		    }
			if(rs!=null){
				rs.close();
			}	
			
			cell = cells.getCell("D" + String.valueOf(index));	SetStyleForDataCell2(cell);	cell.setValue(formatter.format(tt_nguyengiadk));
			cell = cells.getCell("E" + String.valueOf(index));	SetStyleForDataCell2(cell);	cell.setValue(formatter.format(tt_khauhaodk));
			cell = cells.getCell("F" + String.valueOf(index));	SetStyleForDataCell2(cell);	cell.setValue(formatter.format(tt_conlaidk));
			cell = cells.getCell("G" + String.valueOf(index));	SetStyleForDataCell2(cell);	cell.setValue(formatter.format(tt_khauhaotk));
			cell = cells.getCell("H" + String.valueOf(index));	SetStyleForDataCell2(cell);	cell.setValue(formatter.format(tt_nguyengiack));
			cell = cells.getCell("I" + String.valueOf(index));	SetStyleForDataCell2(cell);	cell.setValue(formatter.format(tt_khauhaock));
			cell = cells.getCell("J" + String.valueOf(index));	SetStyleForDataCell2(cell);	cell.setValue(formatter.format(tt_conlaick));
			
			index = index + 3;
			cell = cells.getCell("I" + String.valueOf(index));	cell.setValue("Ngày 	tháng	 năm");
			index++;
			cell = cells.getCell("C" + String.valueOf(index));	cell.setValue("Người lập biểu");
			cell = cells.getCell("E" + String.valueOf(index));	cell.setValue("Kế toán trưởng");
			cell = cells.getCell("I" + String.valueOf(index));	cell.setValue("Giám đốc");
			ReportAPI.setHidden(workbook,14);
			 
		
		    			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			if(rs != null)
			{
				rs.close();
			}
			ex.printStackTrace();
			if(db != null)
				db.shutDown();
			
			throw new Exception(ex.getMessage());
		}
	}

	 public Cell CreateBorderSetting(Cell cell) throws IOException
	    {
		
	        Style style;
	        style = cell.getStyle();

	        //Set border color
	        style.setBorderColor(BorderType.TOP, Color.BLACK);
	        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
	        style.setBorderColor(BorderType.LEFT, Color.BLACK);
	        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

	        //Set the cell border type
	        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
	        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

	        cell.setStyle(style);
	        return cell;
	    }
	 
	 public Cell CreateBorderSetting_2(Cell cell) throws IOException
	    {
	        Style style;
	        style = cell.getStyle();

	        //Set border color
	        style.setBorderColor(BorderType.TOP, Color.GRAY);
	        style.setBorderColor(BorderType.BOTTOM, Color.GRAY);
	        style.setBorderColor(BorderType.LEFT, Color.GRAY);
	        style.setBorderColor(BorderType.RIGHT, Color.GRAY);

	        //Set the cell border type
	        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
	        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

	        cell.setStyle(style);
	        return cell;
	    }
	 
	 private boolean CreatePivotTable_TongHop(OutputStream out,HttpServletResponse response, HttpServletRequest request,IStockintransit obj) 
		{

			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
				
			try
			{
				fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCTaiSanTongHopTatCa.xlsx");
				
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				String query = getQueryBCTongHop(obj);
				FillData_BCTongHopTS(workbook, query, obj);			
				workbook.save(out);
				fstream.close();
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return true;	

		}

	 private String getQueryTongGiaTriTS(IStockintransit obj) 
		{
		 	String lts = "";
		 	if(obj.getLoaiTaiSanId().trim().length()>0)
		 		lts = " and lts.pk_seq = "+obj.getLoaiTaiSanId().trim();
		 		
			String query = 
			
			"SELECT SUM(TC.nguyengiadk) nguyengiadk, SUM(TC.khauhaodk) khauhaodk, SUM(TC.conlaidk) conlaidk, SUM(TC.khauhaotk) khauhaotk, SUM(TC.khauhaock) khauhaock, \n"+
			"	    SUM(TC.nguyengiack) nguyengiack \n"+
			"FROM ( \n"+
			"  		SELECT isnull(dk.nguyengia,0) nguyengiadk, isnull(dk.khauhao,0) khauhaodk, (isnull(dk.nguyengia,0) - isnull(dk.khauhao,0)) conlaidk, \n"+  
			"	 			isnull(tk.khauhao,0) khauhaotk, (isnull(dk.khauhao,0) + isnull(tk.khauhao,0)) khauhaock, ISNULL(tk.nguyengia,0) nguyengiack \n"+ 
			" 		FROM ERP_LOAITAISAN lts LEFT JOIN \n"+ 
			"	( \n"+ 
			"		SELECT b.pk_seq,b.ma, b.diengiai, SUM(a.ThanhTien) nguyengia, SUM(c.KHAUHAO) khauhao \n"+ 
			"		FROM   ERP_TAISANCODINH a INNER JOIN Erp_LOAITAISAN b ON a.LOAITAISAN_FK = b.pk_seq \n"+ 
			"			   INNER JOIN ERP_KHAUHAOTAISAN c ON a.pk_seq = c.TAISAN_FK \n"+ 
			"		WHERE  c.TRANGTHAI = 1 and a.trangthai = 1 and c.THANG < "+obj.getFromMonth()+" and c.NAM <= "+obj.getFromYear()+" \n"+
			"		GROUP BY  b.pk_seq,b.ma, b.diengiai \n"+ 
			"	) dk ON lts.pk_seq = dk.pk_seq LEFT JOIN \n"+ 
			"	( \n"+ 
			"		SELECT b.pk_seq,b.ma, b.diengiai, SUM(a.ThanhTien) nguyengia, SUM(c.KHAUHAO) khauhao \n"+ 
			"		FROM   ERP_TAISANCODINH a INNER JOIN Erp_LOAITAISAN b ON a.LOAITAISAN_FK = b.pk_seq \n"+ 
			"			   INNER JOIN ERP_KHAUHAOTAISAN c ON a.pk_seq = c.TAISAN_FK \n"+ 
			"		WHERE  c.TRANGTHAI = 1 and a.trangthai = 1 and c.THANG = "+obj.getFromMonth()+" and c.NAM = "+obj.getFromYear()+" \n"+
			"		GROUP BY  b.pk_seq,b.ma, b.diengiai \n"+ 
			" ) tk ON lts.pk_seq = tk.pk_seq \n"+ 
			" where 1 = 1 and lts.trangthai = 1 \n"+ 
			" and (isnull(dk.nguyengia,0) !=0 or isnull(dk.khauhao,0) !=0 or isnull(tk.khauhao,0) !=0 ) \n" + lts +
			" ) TC \n";
		 
			return query;
		}
	 
	 private String getQueryTongGiaTriTSTTCP(IStockintransit obj) 
		{
		 	String ttcp ="";
		 	if(obj.getTTCPId().trim().length()>0)
		 		ttcp = " and a.TTCP_FK = "+obj.getTTCPId()+"";
		 	
			String query = "";			
			query =
			" SELECT lts.pk_seq, lts.ma, lts.diengiai, isnull(dk.nguyengia,0) nguyengiadk, isnull(dk.khauhao,0) khauhaodk, (isnull(dk.nguyengia,0) - isnull(dk.khauhao,0)) conlaidk, \n"+ 
			"     isnull(tk.khauhao,0) khauhaotk, (isnull(dk.khauhao,0) + isnull(tk.khauhao,0)) khauhaock, ISNULL(tk.nguyengia,0) nguyengiack \n"+
			" FROM \n"+ 
			" ERP_LOAITAISAN lts LEFT JOIN \n"+
			"	( \n"+
			"		SELECT b.pk_seq,b.ma, b.diengiai, SUM(a.ThanhTien) nguyengia, SUM(c.KHAUHAO) khauhao \n"+
			"		FROM   ERP_TAISANCODINH a INNER JOIN Erp_LOAITAISAN b ON a.LOAITAISAN_FK = b.pk_seq \n"+
			"			   INNER JOIN ERP_KHAUHAOTAISAN c ON a.pk_seq = c.TAISAN_FK \n"+
			"		WHERE  c.TRANGTHAI = 1 and a.trangthai = 1 and c.THANG < "+obj.getFromMonth()+" and c.NAM <= "+obj.getFromYear()+" \n"+ ttcp+
			"		GROUP BY  b.pk_seq,b.ma, b.diengiai \n"+
			"	) dk ON lts.pk_seq = dk.pk_seq LEFT JOIN \n"+
			"	( \n"+
			"		SELECT b.pk_seq,b.ma, b.diengiai, SUM(a.ThanhTien) nguyengia, SUM(c.KHAUHAO) khauhao \n"+
			"		FROM   ERP_TAISANCODINH a INNER JOIN Erp_LOAITAISAN b ON a.LOAITAISAN_FK = b.pk_seq \n"+
			"			   INNER JOIN ERP_KHAUHAOTAISAN c ON a.pk_seq = c.TAISAN_FK \n"+
			"		WHERE  c.TRANGTHAI = 1 and a.trangthai = 1 and c.THANG = "+obj.getFromMonth()+" and c.NAM = "+obj.getFromYear()+" \n"+ ttcp+
			"		GROUP BY  b.pk_seq,b.ma, b.diengiai \n"+
			" ) tk ON lts.pk_seq = tk.pk_seq \n"+
			" where 1 = 1 and lts.trangthai = 1 "+
			" and (isnull(dk.nguyengia,0) !=0 or isnull(dk.khauhao,0) !=0 or isnull(tk.khauhao,0) !=0 )";
			if(obj.getLoaiTaiSanId().trim().length()>0)
				query += " and lts.pk_seq ='"+obj.getLoaiTaiSanId()+"'";
			
			System.out.println("init: \n"+query);
			return query;
		}
	 
	 private String getQueryChiTietGiaTriTS(IStockintransit obj, String lts) 
		{
			String query = 
			
			" SELECT ts.pk_seq, ts.ma, ts.diengiai, ts.thangbatdauKH ngaykhauhao,isnull(dk.nguyengia,0) nguyengiadk, isnull(dk.khauhao,0) khauhaodk, \n"+
			"		 (isnull(dk.nguyengia,0) - isnull(dk.khauhao,0)) conlaidk, "+
			"		 ts.sothangKH, isnull(tk.khauhao,0) khauhaotk, ts.ThanhTien nguyengiack \n"+
			" FROM \n"+  
			" ERP_TAISANCODINH ts INNER JOIN \n"+ 
			" ERP_LOAITAISAN lts ON ts.LOAITAISAN_FK = lts.pk_seq \n"+ 
			" LEFT JOIN \n"+ 
			"	(	SELECT a.pk_seq,a.LOAITAISAN_FK,SUM(isnull(a.ThanhTien,0)) nguyengia, SUM(isnull(c.KHAUHAO,0)) khauhao \n"+ 
			"		FROM   ERP_TAISANCODINH a INNER JOIN ERP_KHAUHAOTAISAN c ON a.pk_seq = c.TAISAN_FK \n"+ 
			"		WHERE  c.TRANGTHAI = 1 and a.trangthai = 1 and c.THANG < "+obj.getFromMonth()+" and c.NAM <= "+obj.getFromYear()+" \n"+
			"		GROUP BY  a.pk_seq,a.LOAITAISAN_FK \n"+
			"	) dk ON ts.pk_seq = dk.pk_seq and lts.pk_seq = dk.LOAITAISAN_FK \n"+
			" LEFT JOIN \n"+ 
			"	( \n"+ 
			"		SELECT a.pk_seq,a.LOAITAISAN_FK,SUM(isnull(a.ThanhTien,0)) nguyengia, SUM(isnull(c.KHAUHAO,0)) khauhao \n"+ 
			"		FROM   ERP_TAISANCODINH a INNER JOIN ERP_KHAUHAOTAISAN c ON a.pk_seq = c.TAISAN_FK \n"+ 
			"		WHERE  c.TRANGTHAI = 1 and a.trangthai = 1 and c.THANG = "+obj.getFromMonth()+" and c.NAM = "+obj.getFromYear()+" \n"+
			"		GROUP BY  a.pk_seq, a.LOAITAISAN_FK \n"+
			" ) tk ON ts.pk_seq = tk.pk_seq  and lts.pk_seq = tk.LOAITAISAN_FK \n"+
			" where 1 = 1 and lts.trangthai = 1 and ts.trangthai = 1 \n"+
			" and (isnull(dk.nguyengia,0) !=0 or isnull(dk.khauhao,0) !=0 or isnull(tk.khauhao,0) !=0 )"+
			" and lts.pk_seq = "+lts+"";
		 
			return query;
		}
	 
	 
	 private void FillData_BCChiTietGiaTriTS(Workbook workbook, String query,IStockintransit obj)
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");

			System.out.println("cau query tong" + query);
			
			ResultSet rs = null;
			dbutils db = new dbutils();
			try
			{	
				Worksheets worksheets = workbook.getWorksheets();
				Worksheet worksheet = worksheets.getSheet(0);
				
				Cells cells = worksheet.getCells();
				Style style;
				
				
				rs = db.get(query);
				int index = 8;
			    Cell cell = null;

			    
			    double nguyengiadk = 0;			    
			    double khauhaodk = 0;
			    double conlaidk = 0;	
			    		    
			    int sothangkhauhao = 0;
			    
			    double khauhaotk = 0;
			    
			    double nguyengiack = 0;
			    double khauhaock = 0;
			    double conlaick =0;

			    
			    cell = cells.getCell("E" + String.valueOf(3));	cell.setValue("Tháng "+obj.getFromMonth()+" năm "+obj.getFromYear());	
			    
			    //CÂU LẤY TỔNG CHO TẤT CẢ CÁC LOẠI TS
			    if(rs!=null)
			    {			    	
					while (rs.next())
					{	
						nguyengiadk = rs.getDouble("nguyengiadk");
						khauhaodk = rs.getDouble("khauhaodk");
						conlaidk = rs.getDouble("conlaidk");
						
						khauhaotk = rs.getDouble("khauhaotk");
						
						nguyengiack = rs.getDouble("nguyengiack");
						khauhaock = rs.getDouble("khauhaock");
						
						conlaick = nguyengiack - khauhaock;
						
						cell = cells.getCell("D" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengiadk);	
						cell = cells.getCell("E" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaodk);			
						cell = cells.getCell("F" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(conlaidk);
						
						cell = cells.getCell("H" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaotk);
						
						cell = cells.getCell("I" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengiack);
						cell = cells.getCell("J" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaock);
						cell = cells.getCell("K" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(conlaick);
												
						index++;
					}
			    }
				if(rs!=null){
					rs.close();
				}	
				
				//Câu tính tổng theo từng loại tài sản
				String queryloaits = getQuery_BCTheoLoaiTS(obj);
				
				String thets = "";
				String tents = "";
				String idloaits = "";
				String ngaykhauhao = "";
				
				rs = db.get(queryloaits);
				
				if(rs!=null){
					while(rs.next()){
						idloaits = rs.getString("pk_seq");
						tents = rs.getString("diengiai");
						
						nguyengiadk = rs.getDouble("nguyengiadk");
						khauhaodk = rs.getDouble("khauhaodk");
						conlaidk = rs.getDouble("conlaidk");
						
						khauhaotk = rs.getDouble("khauhaotk");
						
						nguyengiack = rs.getDouble("nguyengiack");
						khauhaock = rs.getDouble("khauhaock");
						
						conlaick = nguyengiack - khauhaock;
						
						cells.merge(index-1, 0, index-1 , 2);						
						cell = cells.getCell("A" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(tents);
							   style = cell.getStyle();
							   style.setHAlignment(TextAlignmentType.LEFT);
							   Font font = new Font();
							   font.setBold(true);
							   style.setFont(font);
							   cell.setStyle(style);
						
						cell = cells.getCell("D" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengiadk);
						cell = cells.getCell("E" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaodk);			
						cell = cells.getCell("F" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(conlaidk);
						
						cell = cells.getCell("H" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaotk);
						
						cell = cells.getCell("I" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengiack);
						cell = cells.getCell("J" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaock);
						cell = cells.getCell("K" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(conlaick);
												
						index++;
						
						//Chi tiết theo từng loại ts 
						String queryct = getQueryChiTietGiaTriTS(obj, idloaits);
						System.out.println("init: \n"+queryct);
						
						ResultSet rschitiet = db.get(queryct);
						
						if(rschitiet!=null){
							while(rschitiet.next()){
								thets = rschitiet.getString("ma");
								tents = rschitiet.getString("diengiai");
								
								ngaykhauhao = rschitiet.getString("ngaykhauhao");
								sothangkhauhao = rschitiet.getInt("sothangKH");
								
								nguyengiadk = rschitiet.getDouble("nguyengiadk");
								khauhaodk = rschitiet.getDouble("khauhaodk");
								conlaidk = rschitiet.getDouble("conlaidk");
								
								khauhaotk = rschitiet.getDouble("khauhaotk");
								
								nguyengiack = rschitiet.getDouble("nguyengiack");
								khauhaock = khauhaodk + khauhaotk;
								
								//hoi lai
								conlaick = nguyengiack - khauhaock;
								
								cell = cells.getCell("A" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(thets);
								cell = cells.getCell("B" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(tents);			
								cell = cells.getCell("C" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(ngaykhauhao);
								cell = cells.getCell("D" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengiadk);
								cell = cells.getCell("E" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaodk);
								cell = cells.getCell("F" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(conlaidk);
								cell = cells.getCell("G" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(sothangkhauhao);
								cell = cells.getCell("H" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaotk);
								cell = cells.getCell("I" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengiack);
								cell = cells.getCell("J" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaock);
								cell = cells.getCell("K" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(conlaick);
								index++;
							}
						}
						rschitiet.close();
					}
				}
				rs.close();
				
				index = index + 3;
				cell = cells.getCell("I" + String.valueOf(index));	cell.setValue("Ngày 	   tháng	    năm");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				Font font = new Font();
				font.setItalic(true);
				style.setFont(font);
				cell.setStyle(style);
				   
				index++;
				cell = cells.getCell("B" + String.valueOf(index));	cell.setValue("Người lập biểu");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				font = new Font();
				font.setBold(true);
				style.setFont(font);
				cell.setStyle(style);
				
				cell = cells.getCell("E" + String.valueOf(index));	cell.setValue("Kế toán trưởng");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				font = new Font();
				font.setBold(true);
				style.setFont(font);
				cell.setStyle(style);
				
				cell = cells.getCell("I" + String.valueOf(index));	cell.setValue("Giám đốc");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				font = new Font();
				font.setBold(true);
				style.setFont(font);
				cell.setStyle(style);
								
				ReportAPI.setHidden(workbook,14);
				 
			
			    			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();			
			}
		
			
		}
	 
	 
	 private void FillData_BCChiTietGiaTriTSTTCP(Workbook workbook, String query,IStockintransit obj)
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");

			System.out.println("cau query tong" + query);
			
			ResultSet rs = null;
			dbutils db = new dbutils();
			try
			{	
				Worksheets worksheets = workbook.getWorksheets();
				Worksheet worksheet = worksheets.getSheet(0);
				
				Cells cells = worksheet.getCells();
				Style style;
				
				//rs = db.get(query);
				int index = 8;
			    Cell cell = null;

			    double nguyengiadk = 0;			    
			    double khauhaodk = 0;
			    double conlaidk = 0;	
			    		    
			    int sothangkhauhao = 0;
			    
			    double khauhaotk = 0;
			    
			    double nguyengiack = 0;
			    double khauhaock = 0;
			    double conlaick =0;
			    
			   /* 
			    double nguyengiadk = 0;			    
			    double khauhaodk = 0;
			    double conlaidk = 0;	
			    		    
			    int sothangkhauhao = 0;
			    
			    double khauhaotk = 0;
			    
			    double nguyengiack = 0;
			    double khauhaock = 0;
			    double conlaick =0;

			    
			    cell = cells.getCell("E" + String.valueOf(3));	cell.setValue("Tháng "+obj.getFromMonth()+" năm "+obj.getFromYear());	
			    
			    //CÂU LẤY TỔNG CHO TẤT CẢ CÁC LOẠI TS
			    if(rs!=null)
			    {			    	
					while (rs.next())
					{	
						nguyengiadk = rs.getDouble("nguyengiadk");
						khauhaodk = rs.getDouble("khauhaodk");
						conlaidk = rs.getDouble("conlaidk");
						
						khauhaotk = rs.getDouble("khauhaotk");
						
						nguyengiack = rs.getDouble("nguyengiack");
						khauhaock = rs.getDouble("khauhaock");
						
						conlaick = nguyengiack - khauhaock;
						
						cell = cells.getCell("D" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengiadk);	
						cell = cells.getCell("E" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaodk);			
						cell = cells.getCell("F" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(conlaidk);
						
						cell = cells.getCell("H" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaotk);
						
						cell = cells.getCell("I" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengiack);
						cell = cells.getCell("J" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaock);
						cell = cells.getCell("K" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(conlaick);
												
						index++;
					}
			    }
				if(rs!=null){
					rs.close();
				}	*/
				
				//Câu tính tổng theo từng loại tài sản
			    
				//String queryloaits = getQuery_BCTheoLoaiTSTTCP(obj);
				
				String thets = "";
				String tents = "";
				String idloaits = "";
				String ngaykhauhao = "";
				
				rs = db.get(query);
				
				if(rs!=null){
					while(rs.next()){
						idloaits = rs.getString("pk_seq");
						tents = rs.getString("diengiai");
						
						nguyengiadk = rs.getDouble("nguyengiadk");
						khauhaodk = rs.getDouble("khauhaodk");
						conlaidk = rs.getDouble("conlaidk");
						
						khauhaotk = rs.getDouble("khauhaotk");
						
						nguyengiack = rs.getDouble("nguyengiack");
						khauhaock = rs.getDouble("khauhaock");
						
						conlaick = nguyengiack - khauhaock;
						
						//cell.merge(index-1, 0, index-1 , 2);						
						cell = cells.getCell("A" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(tents);
							   style = cell.getStyle(); style.setHAlignment(TextAlignmentType.LEFT); 
							   Font font = new Font(); font.setBold(true); style.setFont(font); cell.setStyle(style);
						
						cell = cells.getCell("C" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengiadk);
						cell = cells.getCell("D" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaodk);			
						cell = cells.getCell("E" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(conlaidk);
						
						cell = cells.getCell("F" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaotk);
						
						cell = cells.getCell("G" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengiack);
						cell = cells.getCell("H" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaock);
						cell = cells.getCell("I" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(conlaick);
												
						index++;
						
						//Chi tiết theo từng loại ts 
						String queryct = getQuery_BCTheoLoaiTSTTCP(obj, idloaits);
						System.out.println("init: \n"+queryct);
						
						ResultSet rschitiet = db.get(queryct);
						
						if(rschitiet!=null){
							while(rschitiet.next()){
								thets = rschitiet.getString("ma");
								tents = rschitiet.getString("diengiai");
																
								nguyengiadk = rschitiet.getDouble("nguyengiadk");
								khauhaodk = rschitiet.getDouble("khauhaodk");
								conlaidk = rschitiet.getDouble("conlaidk");
								
								khauhaotk = rschitiet.getDouble("khauhaotk");
								
								nguyengiack = rschitiet.getDouble("nguyengiack");
								khauhaock = khauhaodk + khauhaotk;
								
								//hoi lai
								conlaick = nguyengiack - khauhaock;
								
								cell = cells.getCell("A" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(thets);
								cell = cells.getCell("B" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(tents);		
								cell = cells.getCell("C" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengiadk);
								cell = cells.getCell("D" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaodk);
								cell = cells.getCell("E" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(conlaidk);
								cell = cells.getCell("F" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaotk);
								cell = cells.getCell("G" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengiack);
								cell = cells.getCell("H" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(khauhaock);
								cell = cells.getCell("I" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(conlaick);
								index++;
							}
						}
						rschitiet.close();
					}
				}
				rs.close();
				
				index = index + 3;
				cell = cells.getCell("I" + String.valueOf(index));	cell.setValue("Ngày 	   tháng	    năm");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				Font font = new Font();
				font.setItalic(true);
				style.setFont(font);
				cell.setStyle(style);
				   
				index++;
				cell = cells.getCell("B" + String.valueOf(index));	cell.setValue("Người lập biểu");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				font = new Font();
				font.setBold(true);
				style.setFont(font);
				cell.setStyle(style);
				
				cell = cells.getCell("E" + String.valueOf(index));	cell.setValue("Kế toán trưởng");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				font = new Font();
				font.setBold(true);
				style.setFont(font);
				cell.setStyle(style);
				
				cell = cells.getCell("I" + String.valueOf(index));	cell.setValue("Giám đốc");
				style = cell.getStyle();
				style.setHAlignment(TextAlignmentType.CENTER);
				font = new Font();
				font.setBold(true);
				style.setFont(font);
				cell.setStyle(style);
								
				ReportAPI.setHidden(workbook,14);
				 
			
			    			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();			
			}
		
			
		}
	 
		private boolean FillData_TSTangGiam(Workbook workbook, IStockintransit obj, String sqlTaisantang, String sqlTaisangiam) throws Exception
		{
			dbutils db = new dbutils();
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			
			Cells cells = worksheet.getCells();	
			
			Cell cell = null;
			
			// Tài sản tăng
			ResultSet rs = db.get(sqlTaisantang);
			
			String loaits = "";
			String mats = "";
			String tents = "";
			
			String donvisd = "";
			String ngaybatdausd = "";
			
			String ngaythanhly = "";
			
			double nguyengia = 0;
			double giatridakhauhao = 0;
			double giatriconlai = 0;
			
			double nguyengia_total = 0;
			double giatrikhauhao_total = 0;
			double giatriconlai_total = 0;
			
			int index = 7;
			Style style;
			
			cells.merge(index-1, 0, index-1 , 8);	
			cell = cells.getCell("A" + String.valueOf(index)); SetStyleForDataCell(cell);	cell.setValue("TÀI SẢN TĂNG");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.LEFT);
			Font font = new Font();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			
			cell = CreateBorderSetting_2(cell);
			cell = CreateBorderSetting_2(cells.getCell("B" + index));
			cell = CreateBorderSetting_2(cells.getCell("C" + index));
			cell = CreateBorderSetting_2(cells.getCell("D" + index));
			cell = CreateBorderSetting_2(cells.getCell("E" + index));
			cell = CreateBorderSetting_2(cells.getCell("F" + index));
			cell = CreateBorderSetting_2(cells.getCell("G" + index));
			cell = CreateBorderSetting_2(cells.getCell("H" + index));
			cell = CreateBorderSetting_2(cells.getCell("I" + index));
			
			index++;
			if (rs != null) 
			{
				try 
				{	
					// TÀI SẢN TĂNG
					while (rs.next())
					{	
						loaits=rs.getString("loaits");
						mats = rs.getString("ma");
						tents = rs.getString("tents");
						donvisd = rs.getString("donviSD");
						ngaybatdausd = rs.getString("thangbatdauKH");
						ngaythanhly = rs.getString("ngaythanhly"); 
						nguyengia = rs.getDouble("thanhtien");
						giatridakhauhao = rs.getDouble("khauhao");
						giatriconlai = rs.getDouble("conlai");
						
						nguyengia_total += nguyengia;
						giatrikhauhao_total += giatridakhauhao;
						giatriconlai_total += giatriconlai;
						cell = cells.getCell("A" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(loaits);	
						cell = cells.getCell("B" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(mats);
						cell = cells.getCell("C" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(tents);
						cell = cells.getCell("D" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(donvisd);
						cell = cells.getCell("E" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(ngaybatdausd);	
						cell = cells.getCell("F" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(ngaythanhly);
						cell = cells.getCell("G" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengia);		
						cell = cells.getCell("H" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(giatridakhauhao);
						cell = cells.getCell("I" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(giatriconlai);
						
						index++;					
					}
					rs.close();
					
					
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
			
			cells.merge(index-1, 0, index-1 , 5);	
			cell = cells.getCell("A" + String.valueOf(index)); SetStyleForDataCell(cell);	cell.setValue("Tổng cộng");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			font = new Font();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			cell = CreateBorderSetting_2(cell);
			cell = CreateBorderSetting_2(cells.getCell("B" + index));
			cell = CreateBorderSetting_2(cells.getCell("C" + index));
			cell = CreateBorderSetting_2(cells.getCell("D" + index));
			cell = CreateBorderSetting_2(cells.getCell("E" + index));
			cell = CreateBorderSetting_2(cells.getCell("F" + index));
			
			cell = cells.getCell("G" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengia_total);
			style = cell.getStyle();
			font = new Font();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			
			cell = cells.getCell("H" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(giatrikhauhao_total);
			style = cell.getStyle();
			font = new Font();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			
			cell = cells.getCell("I" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(giatriconlai_total);
			style = cell.getStyle();
			font = new Font();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			
			index++;
			
			cells.merge(index-1, 0, index-1 , 8);	
			cell = cells.getCell("A" + String.valueOf(index)); SetStyleForDataCell(cell);	cell.setValue("TÀI SẢN GIẢM");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.LEFT);
			font = new Font();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			
			cell = CreateBorderSetting_2(cell);
			cell = CreateBorderSetting_2(cells.getCell("B" + index));
			cell = CreateBorderSetting_2(cells.getCell("C" + index));
			cell = CreateBorderSetting_2(cells.getCell("D" + index));
			cell = CreateBorderSetting_2(cells.getCell("E" + index));
			cell = CreateBorderSetting_2(cells.getCell("F" + index));
			cell = CreateBorderSetting_2(cells.getCell("G" + index));
			cell = CreateBorderSetting_2(cells.getCell("H" + index));
			cell = CreateBorderSetting_2(cells.getCell("I" + index));
			
			index++;
			
			nguyengia_total = 0;
			giatrikhauhao_total = 0;
			giatriconlai_total =0;
			
			// Tài sản giảm
			ResultSet rs_giam = db.get(sqlTaisangiam);
			if (rs != null) 
			{
				try 
				{	
					// TÀI SẢN GIẢM
					while (rs_giam.next())
					{	
						loaits=rs_giam.getString("loaits");
						mats = rs_giam.getString("ma");
						tents = rs_giam.getString("tents");
						donvisd = rs_giam.getString("donviSD");
						ngaybatdausd = rs_giam.getString("thangbatdauKH");
						ngaythanhly = rs_giam.getString("ngaythanhly"); 
						nguyengia = rs_giam.getDouble("thanhtien");
						giatridakhauhao = rs_giam.getDouble("khauhao");
						giatriconlai = rs_giam.getDouble("conlai");
						
						nguyengia_total += nguyengia;
						giatrikhauhao_total += giatridakhauhao;
						giatriconlai_total += giatriconlai;
						
						cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(loaits);	
						this.setStyleColorGray(cells, cell, "1");
						cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(mats);
						this.setStyleColorGray(cells, cell, "1");
						cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(tents);
						this.setStyleColorGray(cells, cell, "1");
						cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(donvisd);
						this.setStyleColorGray(cells, cell, "1");					
						cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(ngaybatdausd);	
						this.setStyleColorGray(cells, cell, "1");					
						cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(ngaythanhly);
						this.setStyleColorGray(cells, cell, "1");
						cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(nguyengia);
						this.setStyleColorGray(cells, cell, "1");					
						cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(giatridakhauhao);
						this.setStyleColorGray(cells, cell, "1");
						cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(giatriconlai);
						this.setStyleColorGray(cells, cell, "1");
						
						index++;					
					}
					rs.close();
					
					
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
			
			cells.merge(index-1, 0, index-1 , 5);	
			cell = cells.getCell("A" + String.valueOf(index)); SetStyleForDataCell(cell);	cell.setValue("Tổng cộng");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			font = new Font();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			cell = CreateBorderSetting_2(cell);
			cell = CreateBorderSetting_2(cells.getCell("B" + index));
			cell = CreateBorderSetting_2(cells.getCell("C" + index));
			cell = CreateBorderSetting_2(cells.getCell("D" + index));
			cell = CreateBorderSetting_2(cells.getCell("E" + index));
			cell = CreateBorderSetting_2(cells.getCell("F" + index));
			
			cell = cells.getCell("G" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(nguyengia_total);
			style = cell.getStyle();
			font = new Font();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			
			cell = cells.getCell("H" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(giatrikhauhao_total);
			style = cell.getStyle();
			font = new Font();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			
			cell = cells.getCell("I" + String.valueOf(index));	SetStyleForDataCell(cell);	cell.setValue(giatriconlai_total);
			style = cell.getStyle();
			font = new Font();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			
			index++;
			
			index = index + 3;
			cell = cells.getCell("H" + String.valueOf(index));	cell.setValue("Ngày 	   tháng	    năm");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			font = new Font();
			font.setItalic(true);
			style.setFont(font);
			cell.setStyle(style);
			   
			index++;
			cell = cells.getCell("B" + String.valueOf(index));	cell.setValue("Người lập biểu");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			font = new Font();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			
			cell = cells.getCell("E" + String.valueOf(index));	cell.setValue("Kế toán trưởng");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			font = new Font();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			
			cell = cells.getCell("H" + String.valueOf(index));	cell.setValue("Giám đốc");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			font = new Font();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
							
			ReportAPI.setHidden(workbook,14);
			
			if(db != null)
				db.shutDown();
			return true;
		}	
}
