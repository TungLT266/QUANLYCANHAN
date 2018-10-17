package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.beans.baocao.BaoCaoVayTienPoJo;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Z.DB;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpBCVayTienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpBCVayTienSvl() {
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
 
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBaoCaoVayTien.jsp";
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
		
		Utility util = new Utility();
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
	    if(tungay == null)
	    	tungay = "";
	    obj.settungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
	    if(denngay == null)
	    	denngay = "";
	    obj.setdenngay(denngay);
 
	    
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBaoCaoVayTien.jsp";
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=ErpBCVayTien.xlsm");
			
		   

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
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				e.printStackTrace();
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
			}
		} else if(action.equals("View")){
			
			List<BaoCaoVayTienPoJo> list = View(obj.gettungay(), obj.getdenngay());
			obj.setuserTen((String) session.getAttribute("userTen"));
	 
			obj.setListBaoCaoVayTien(list);
			
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
			
		}
  
	}
	
	private List<BaoCaoVayTienPoJo>  View(String tungay, String denngay){
		dbutils db = new dbutils();
		
		try {
			String query= " SELECT HDV.SOHD,NTV.TKVAY, NTV.NGAYNHAN,  REPLACE(CONVERT(CHAR(10), DATEADD(MONTH, NTV.THOIHAN, " +
			  "CAST(NTV.NGAYNHAN AS DATETIME)) , 102) , '.', '-' )  AS NGAYDAOHAN , "+
			  " NTV.SOTIEN,TT.MA  as LOAITIEN,NTV.LAISUAT,(SELECT ISNULL( SUM(TIENGOC),0)  " +
			  "FROM ERP_THANHTOANNOVAY TTNV WHERE TTNV.TRANGTHAI<> 2 AND TTNV.NHANTIENVAY_FK=NTV.PK_SEQ ) " +
			  "AS DATRA,NTV.GHICHU "+
			  " FROM ERP_NHANTIENVAY  NTV INNER JOIN ERP_HOPDONGVAY HDV ON HDV.PK_SEQ = NTV.HOPDONG_FK "+
			  " INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ=NTV.TIENTE_FK WHERE NTV.TRANGTHAI ='1' ";
			
			query = " SELECT HDV.SOHD,NTV.TKVAY, NTV.NGAYNHAN,  REPLACE(CONVERT(CHAR(10), DATEADD(MONTH, NTV.THOIHAN," +
			" CAST(NTV.NGAYNHAN AS DATETIME)) , 102) , '.', '-' )  AS NGAYDAOHAN ,TT.MA  as LOAITIEN, " +
			" CASE WHEN TT.MA like '%VND%' THEN Isnull(NTV.SOTIENVND,0) " +
			" WHEN TT.MA  NOT like '%VND%' THEN Isnull(NTV.SOTIENNT,0) " +
			" ELSE ISNUll(NTV.SOTIEN,0) END AS SOTIEN,      " +
			" NTV.LAISUAT,(SELECT ISNULL( SUM(TIENGOC),0)  " +
			" FROM ERP_THANHTOANNOVAY TTNV " +
			" WHERE TTNV.TRANGTHAI<> 2 AND TTNV.NHANTIENVAY_FK=NTV.PK_SEQ ) AS DATRA,NTV.GHICHU  " +
			" FROM ERP_NHANTIENVAY  NTV INNER JOIN ERP_HOPDONGVAY HDV ON HDV.PK_SEQ = NTV.HOPDONG_FK  " +
			" INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ=NTV.TIENTE_FK " +
			" WHERE NTV.TRANGTHAI ='1'";
			
			if(tungay.length() >0){
				query=query + " and NTV.NGAYNHAN >='"+tungay+"' ";
			}
			if(denngay.length() >0){
				query=query + " and NTV.NGAYNHAN <='"+denngay+"' ";
			}  
			query +=" Order by TT.MA ";
			System.out.println("Du Lieu : "+query);
			
			ResultSet	rs= db.get(query);
			
			List<BaoCaoVayTienPoJo> list = new ArrayList<BaoCaoVayTienPoJo>();
			if(rs!=null){
				while(rs.next()){
					BaoCaoVayTienPoJo ct = new BaoCaoVayTienPoJo();
					ct.setDaTra(rs.getFloat("DATRA"));
					ct.setSoHopDong(rs.getString("SOHD"));
					ct.setTaiKhoanVay(rs.getString("TKVAY"));
					ct.setNgayNhan(rs.getString("NGAYNHAN"));
					ct.setNgayDaoHan(rs.getString("NGAYDAOHAN"));
					ct.setSoTien(rs.getFloat("SOTIEN"));
					ct.setLoaiTien(rs.getString("LOAITIEN"));
					ct.setLaiSuat(rs.getFloat("LAISUAT"));
					ct.setGhiChu(rs.getString("GHICHU"));
					
					list.add(ct);
				}
			}
			if(rs!=null){
				rs.close();
			}
			return list;
			
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			db.shutDown();
		}
		return null;
	}
	int index=3;
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
 
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCVayTien.xlsm");
		//fstream = new FileInputStream(getServletContext().getInitParameter("path_Sang") + "\\ErpBCVayTien.xlsm");
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		index=3;
		
		  query= 	" SELECT  DISTINCT TT.PK_SEQ ,TT.MA "+
					" FROM ERP_NHANTIENVAY  NTV INNER JOIN ERP_HOPDONGVAY HDV ON HDV.PK_SEQ = NTV.HOPDONG_FK "+
					" INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ=NTV.TIENTE_FK";
		    if(obj.gettungay().length() >0){
				query=query + " and NTV.NGAYNHAN >='"+obj.gettungay()+"' ";
			}
			if(obj.getdenngay().length() >0){
				query=query + " and NTV.NGAYNHAN <='"+obj.getdenngay()+"' ";
			}  
			
		  dbutils db=new dbutils();
		ResultSet rs=db.get(query);
		
		System.out.println(query);
		while (rs.next()){
			isFillData = FillData(workbook, obj,rs.getString("pk_seq"),rs.getString("MA"));
		}
		rs.close();
		
   
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
	
	
	private void setStyleDongCuoi(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("Y1");
		Style style;	
		 style = cell1.getStyle();
        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	private void setStyleDongCuoi_Format_Num(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("Z1");
		Style style;	
		 style = cell1.getStyle();
        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	
	private void setStyleDong_LoaiTien_Xanh(Cells cells ,Cell cell)
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
	private void setStyleDong_Header(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("W1");
		Style style;	
		 style = cell1.getStyle();
        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	private void setStyleDong_Format_Numeric(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("V1");
		Style style;	
		 style = cell1.getStyle();
        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	
	private void setStyleColorNormar(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("H1");
		Style style;	
		 style = cell1.getStyle();
       //style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
       // style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}

	
	private boolean FillData(Workbook workbook, IStockintransit obj ,String tiente_fk,String MaTienTe  ) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		
		Cell cell = null;
		cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("Vay "+MaTienTe);	
		this.setStyleDong_LoaiTien_Xanh(cells, cell);
		
		cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("");	
		this.setStyleDong_LoaiTien_Xanh(cells, cell);
	 
		cell = cells.getCell("C" + String.valueOf(index));		cell.setValue("");	
		this.setStyleDong_LoaiTien_Xanh(cells, cell);
		cell = cells.getCell("D" + String.valueOf(index));		cell.setValue("");	
		this.setStyleDong_LoaiTien_Xanh(cells, cell);
		
		cell = cells.getCell("E" + String.valueOf(index));		cell.setValue("");	
		this.setStyleDong_LoaiTien_Xanh(cells, cell);
		 
		cell = cells.getCell("F" + String.valueOf(index));		cell.setValue("");	
		this.setStyleDong_LoaiTien_Xanh(cells, cell);
		 
		cell = cells.getCell("G" + String.valueOf(index));		cell.setValue("");	
		this.setStyleDong_LoaiTien_Xanh(cells, cell);
		
		cell = cells.getCell("H" + String.valueOf(index));		cell.setValue("");	
		this.setStyleDong_LoaiTien_Xanh(cells, cell);
		
		cell = cells.getCell("I" + String.valueOf(index));		cell.setValue("");	
		this.setStyleDong_LoaiTien_Xanh(cells, cell);
	 
		cell = cells.getCell("J" + String.valueOf(index));		cell.setValue("");	
		this.setStyleDong_LoaiTien_Xanh(cells, cell);	
		
		index++;
		// set Header
		
		cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("Tài khoản vay");	
		this.setStyleDong_Header(cells, cell);
	 
		cell = cells.getCell("C" + String.valueOf(index));		cell.setValue("Ngày vay");	
		this.setStyleDong_Header(cells, cell);
		cell = cells.getCell("D" + String.valueOf(index));		cell.setValue("Ngày đáo hạn");	
		this.setStyleDong_Header(cells, cell);
		
		cell = cells.getCell("E" + String.valueOf(index));		cell.setValue("Loại tiền");	
		this.setStyleDong_Header(cells, cell);
		 
		cell = cells.getCell("F" + String.valueOf(index));		cell.setValue("Số tiền vay");	
		this.setStyleDong_Header(cells, cell);
		 
		cell = cells.getCell("G" + String.valueOf(index));		cell.setValue("Lãi suất");	
		this.setStyleDong_Header(cells, cell);
		
		cell = cells.getCell("H" + String.valueOf(index));		cell.setValue("Đã trả");	
		this.setStyleDong_Header(cells, cell);
		
		cell = cells.getCell("I" + String.valueOf(index));		cell.setValue("Còn phải trả");	
		this.setStyleDong_Header(cells, cell);
	 
		cell = cells.getCell("J" + String.valueOf(index));		cell.setValue("Nội dung");	
		this.setStyleDong_Header(cells, cell);
		
		index++;
		
		String query= " SELECT HDV.SOHD,NTV.TKVAY, NTV.NGAYNHAN,  REPLACE(CONVERT(CHAR(10), DATEADD(MONTH, NTV.THOIHAN, CAST(NTV.NGAYNHAN AS DATETIME)) , 102) , '.', '-' )  AS NGAYDAOHAN , "+
		 			  " NTV.SOTIEN,TT.MA  as LOAITIEN,NTV.LAISUAT,(SELECT ISNULL( SUM(TIENGOC),0)  FROM ERP_THANHTOANNOVAY TTNV WHERE TTNV.TRANGTHAI<> 2 AND TTNV.NHANTIENVAY_FK=NTV.PK_SEQ ) AS DATRA,NTV.GHICHU "+
		 			  " FROM ERP_NHANTIENVAY  NTV INNER JOIN ERP_HOPDONGVAY HDV ON HDV.PK_SEQ = NTV.HOPDONG_FK "+
		 			  " INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ=NTV.TIENTE_FK WHERE NTV.TRANGTHAI ='1' AND TT.PK_SEQ="+tiente_fk ;
		
		query = " SELECT HDV.SOHD,NTV.TKVAY, NTV.NGAYNHAN,  REPLACE(CONVERT(CHAR(10), DATEADD(MONTH, NTV.THOIHAN," +
				" CAST(NTV.NGAYNHAN AS DATETIME)) , 102) , '.', '-' )  AS NGAYDAOHAN ,TT.MA  as LOAITIEN, " +
				" CASE WHEN TT.MA like '%VND%' THEN Isnull(NTV.SOTIENVND,0) " +
				" WHEN TT.MA  NOT like '%VND%' THEN Isnull(NTV.SOTIENNT,0) " +
				" ELSE ISNUll(NTV.SOTIEN,0) END AS SOTIEN,      " +
				" NTV.LAISUAT,(SELECT ISNULL( SUM(TIENGOC),0)  " +
				" FROM ERP_THANHTOANNOVAY TTNV " +
				" WHERE TTNV.TRANGTHAI<> 2 AND TTNV.NHANTIENVAY_FK=NTV.PK_SEQ ) AS DATRA,NTV.GHICHU  " +
				" FROM ERP_NHANTIENVAY  NTV INNER JOIN ERP_HOPDONGVAY HDV ON HDV.PK_SEQ = NTV.HOPDONG_FK  " +
				" INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ=NTV.TIENTE_FK " +
				" WHERE NTV.TRANGTHAI ='1' AND TT.PK_SEQ="+tiente_fk ;
		
		if(obj.gettungay().length() >0){
			query=query + " and NTV.NGAYNHAN >='"+obj.gettungay()+"' ";
		}
		if(obj.getdenngay().length() >0){
			query=query + " and NTV.NGAYNHAN <='"+obj.getdenngay()+"' ";
		}  
		System.out.println("Du Lieu : "+query);
		
	    ResultSet	rs= db.get(query);
	
		
		double totalsotienvay=0;
		double totalsotientra=0;
  
		if (rs != null) 
		{
			try 
			{
			 
			 
				while (rs.next())
				{		
 
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("TKVAY"));	
					this.setStyleColorNormar(cells, cell);
				 
					cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("NGAYNHAN"));	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("NGAYDAOHAN"));	
					this.setStyleColorNormar(cells, cell);
					
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("LOAITIEN"));	
					this.setStyleColorNormar(cells, cell);
					 
					cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getDouble("SOTIEN"));	
					this.setStyleDong_Format_Numeric(cells, cell);
					
					totalsotienvay=totalsotienvay +rs.getDouble("SOTIEN");
					
					cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getDouble("LAISUAT"));	
					this.setStyleDong_Format_Numeric(cells, cell);
					
					cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getDouble("DATRA"));	
					this.setStyleDong_Format_Numeric(cells, cell);
					
					totalsotientra=totalsotientra+rs.getDouble("DATRA");
					
					cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getDouble("SOTIEN")-rs.getDouble("DATRA"));	
					this.setStyleDong_Format_Numeric(cells, cell);
				 
					cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getString("GHICHU"));	
					this.setStyleColorNormar(cells, cell);
					
					index++;					
				}

				if (rs != null){
					rs.close();
				}
				
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("Tổng cộng :");	
				this.setStyleDongCuoi(cells, cell);
				
				cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("");	
				this.setStyleDongCuoi(cells, cell);
			 
				cell = cells.getCell("C" + String.valueOf(index));		cell.setValue("");	
				this.setStyleDongCuoi(cells, cell);
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue("");	
				this.setStyleDongCuoi(cells, cell);
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue("");	
				this.setStyleDongCuoi(cells, cell);
				 
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(totalsotienvay);	
				this.setStyleDongCuoi_Format_Num(cells, cell);
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue("");	
				this.setStyleDongCuoi(cells, cell);
				
				cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(totalsotientra);	
				this.setStyleDongCuoi_Format_Num(cells, cell);
				
				cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(totalsotienvay-totalsotientra);	
				this.setStyleDongCuoi_Format_Num(cells, cell);
			 
				cell = cells.getCell("J" + String.valueOf(index));		cell.setValue("");	
				this.setStyleDongCuoi(cells, cell);		 
				index++;
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
