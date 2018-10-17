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

public class ErpReportChietKhauThanhToan extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpReportChietKhauThanhToan() {
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
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiChietKhauThanhToan.jsp";
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
		
		String khuvucId = util.antiSQLInspection(request.getParameter("khuvucid"));
		if(khuvucId == null)
			khuvucId  = "";		
		obj.setkhuvucId(khuvucId);
		 
		String vungid = util.antiSQLInspection(request.getParameter("vungid"));
		if(vungid == null)
			vungid  = "";		
		obj.setvungId(vungid);
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");	
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheoDoiChietKhauThanhToan.jsp";
		
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
			response.setHeader("Content-Disposition", "attachment; filename=BCTheoDoiCKTT.xlsm");
			
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
		String kh = obj.getErpKhachHangId();
		
		if(obj.getErpKhachHangId().startsWith("KH"))
			kh = obj.getErpKhachHangId().substring(2, obj.getErpKhachHangId().length());
		else if(obj.getErpKhachHangId().startsWith("NPP"))
			kh = obj.getErpKhachHangId().substring(3, obj.getErpKhachHangId().length());
		else if(obj.getErpKhachHangId().startsWith("NV"))
			kh = obj.getErpKhachHangId().substring(2, obj.getErpKhachHangId().length());
		
		String query = 
			
		"	SELECT isnull(F.TEN,'') DIABAN, isnull(G.TEN,'') KHUVUC, ISNULL((SELECT ISNULL(KBH.DIENGIAI,'') FROM KHACHHANG_KENHBANHANG KH_KBH INNER JOIN KENHBANHANG KBH ON KH_KBH.kbh_fk = KBH.PK_SEQ \n"+
		"			WHERE C.PK_SEQ = KH_KBH.khachhang_fk),'') KENHBH, ISNULL(ISNULL(C.maFAST, D.MaFAST), E.MA) MAKH, ISNULL(ISNULL(C.TEN, D.TEN), E.TEN) TENKH, \n"+
		"			TT.machungtu, TT.NGAYCHUNGTU, B.SOHOADON, B.NGAYXUATHD, SP.MA_FAST MASP, SP.tenviettat TENSP, HDSP.ptchietkhau, HDSP.tienck \n"+
		"	FROM \n"+ 
		"	ERP_THUTIEN TT INNER JOIN ERP_THUTIEN_HOADON_SP HDSP ON TT.PK_SEQ = HDSP.thutien_fk \n"+
		"	INNER JOIN SANPHAM SP ON HDSP.sanpham_fk = SP.PK_SEQ \n"+
		"	INNER JOIN ERP_HOADONNPP B ON HDSP.HOADON_FK = B.PK_SEQ  \n"+
		"	LEFT JOIN KHACHHANG C ON B.KHACHHANG_FK = C.PK_SEQ \n"+
		"	LEFT JOIN NHAPHANPHOI D ON B.NPP_DAT_FK = D.PK_SEQ \n"+
		"	LEFT JOIN ERP_NHANVIEN E ON B.NHANVIEN_FK = E.PK_SEQ \n"+
		"	LEFT JOIN DIABAN F ON F.PK_SEQ = C.diaban \n"+
		"   LEFT JOIN KHUVUC G ON F.KHUVUC_FK = G.PK_SEQ \n"+	
		"	WHERE TT.TRANGTHAI = 1 AND HDSP.LOAIHOADON = 0 AND TT.CONGTY_FK = "+obj.getErpCongtyId()+" AND isnull(HDSP.ptchietkhau,0) > 0 \n";
		
		if(obj.getErpKhachHangId().startsWith("KH"))
			query += " AND C.PK_SEQ = "+kh;
		
		if(obj.getErpKhachHangId().startsWith("NPP"))
			query += " AND C.PK_SEQ = "+kh;
		
		if(obj.getErpKhachHangId().startsWith("NV"))
			query += " AND C.PK_SEQ = "+kh;
		
		if( obj.getkenhId().trim().length() > 0 )
			query+= " AND C.PK_SEQ IN  ( SELECT KHACHHANG_FK FROM KHACHHANG_KENHBANHANG WHERE KBH_FK IN ( " + obj.getkenhId() + " )  ) ";
		
		if( obj.gettungay().trim().length() > 0 )
			query+= " AND TT.NGAYCHUNGTU >= '"+obj.gettungay()+"'" ;
		
		if(obj.getdenngay().trim().length()>0)
			query+= " AND TT.NGAYCHUNGTU <= '"+obj.getdenngay()+"'" ;
		
		if(obj.getvungId().length() >1 ){
			query+=" and C.DIABAN IN ( "+ 
					 " SELECT DB.PK_SEQ FROM DIABAN DB INNER JOIN KHUVUC KV On  KV.PK_SEQ=DB.KHUVUC_FK  "+ 
					 " WHERE KV.VUNG_FK IN ("+obj.getvungId()+")  " +
					 		"" +(obj.getkhuvucId().length() >1? " AND KV.PK_SEQ IN ("+obj.getkhuvucId()+") " : "") +" )";
			  	
		}else{
			if(obj.getkhuvucId().length() >1){
				query+=	 " and C.DIABAN IN ( "+ 
						 " SELECT DB.PK_SEQ FROM DIABAN DB INNER JOIN KHUVUC KV On  KV.PK_SEQ = DB.KHUVUC_FK  "+ 
						 " WHERE    KV.PK_SEQ IN ("+obj.getkhuvucId()+") "   +" )";
			}
			
		}		
		
		
		System.out.println(query);
	    return query;   
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCTheoDoiChietKhauThanhToan.xlsm");
		
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
		Cell cell1 = cells.getCell("Y1");
		Style style;	
		style = cell1.getStyle();		
	    
        if(leftright.equals("1")){
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
            cell.setStyle(style);
        }else if(leftright.equals("2"))
        {
        	Font font = new Font();
    		font.setBold(true);
    		style.setFont(font);
    		
    		style.setBorderLine(BorderType.TOP, BorderLineType.NONE);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.NONE);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
        	
    		cell.setStyle(style);
        }
        else if(leftright.equals("3"))
        {
        	Font font = new Font();
    		font.setBold(false);
    		style.setFont(font);
    		
    		style.setBorderLine(BorderType.TOP, BorderLineType.NONE);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.NONE);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
        	
    		cell.setStyle(style);
        }
        else if(leftright.equals("4"))
        {
        	Font font = new Font();
    		font.setBold(true);
    		style.setFont(font);
    		
    		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        	
    		cell.setStyle(style);
        }
        else {
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
		Style style;
		ResultSet rs = db.get(query);
		int index = 10;
		double total_tienck=0;
		
		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				String tenkh = "";
				double tien=0;
				while (rs.next())
				{		
					tien=rs.getDouble("tienck");
					if(tien>0)
					{
					cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index-9);	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("KENHBH"));	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("KHUVUC"));	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("DIABAN"));	
					this.setStyleColorNormar(cells, cell);					
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("MAKH"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getString("TENKH"));	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getString("MACHUNGTU"));
					this.setStyleColorGray(cells, cell, "1");
					cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getString("NGAYCHUNGTU"));
					this.setStyleColorGray(cells, cell, "1");
					cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getString("SOHOADON"));	//Nhan hang   	6
					this.setStyleColorGray(cells, cell, "1");
					cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getString("NGAYXUATHD"));	//Nhan hang   	6
					this.setStyleColorGray(cells, cell, "1");
					cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(rs.getString("MASP"));	//Nhan hang   	6
					this.setStyleColorGray(cells, cell, "1");
					cell = cells.getCell("L" + String.valueOf(index));		cell.setValue(rs.getString("TENSP"));	//Nhan hang   	6
					this.setStyleColorGray(cells, cell, "1");
					cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(rs.getDouble("ptchietkhau"));	//Nhan hang   	6
					this.setStyleColorGray(cells, cell, "1");

					cell = cells.getCell("N" + String.valueOf(index));		cell.setValue(tien);	//Nhan hang   	6
					
					
					this.setStyleColorGray(cells, cell, "1");
					style = cell.getStyle();
					style.setNumber(3);
					cell.setStyle(style);
					index++;	
					total_tienck += rs.getDouble("tienck");
					}
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
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue("");	
				this.setStyleColorGray(cells, cell, "4");
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "4");
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "4");
				
				cell = cells.getCell("H" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "4");
				
				cell = cells.getCell("I" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "4");
				
				cell = cells.getCell("J" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "4");
				
				cell = cells.getCell("K" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "4");
				
				cell = cells.getCell("L" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "4");
				
				cell = cells.getCell("M" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell, "4");
				
				cell = cells.getCell("N" + String.valueOf(index));		cell.setValue(total_tienck);
				this.setStyleColorGray(cells, cell, "4");
				style = cell.getStyle();
				style.setNumber(3);
				cell.setStyle(style);
				
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