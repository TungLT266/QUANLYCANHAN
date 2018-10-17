package geso.traphaco.erp.servlets.debit;

import geso.traphaco.center.util.Utility;
import geso.dms.db.sql.dbutils;
import geso.traphaco.erp.beans.debit.IErpDebit;
import geso.traphaco.erp.beans.debit.IErpDebitList;
import geso.traphaco.erp.beans.debit.imp.ErpDebit;
import geso.traphaco.erp.beans.debit.imp.ErpDebitList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpDebitUpdateSvl extends HttpServlet{
private static final long serialVersionUID = 1L;
	
    public ErpDebitUpdateSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = null;
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String action = util.getAction(querystring);
	    
	    String Id = util.getId(querystring);
	    
	    String userId = (String)session.getAttribute("userId");
		String userTen=(String)session.getAttribute("userTen");
		
	    if(action.equals("update")){
		   	IErpDebit obj = new ErpDebit();
		   	
		   	obj.setId(Id);
		   	System.out.println("Id"+ Id);
		 	obj.getView();
		     
			String  nextJSP = "/TraphacoHYERP/pages/Erp/ErpDebitNew.jsp";
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			session.setAttribute("action", "update");
			response.sendRedirect(nextJSP);
	    } else if(action.equals("display")) {
	    	IErpDebit obj = new ErpDebit();
	    	obj.setId(Id);
	    	obj.getView();
		    
		 	session.setAttribute("obj",obj);
		    session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			
			String  nextJSP = "/TraphacoHYERP/pages/Erp/ErpDebitDisplay.jsp";
			response.sendRedirect(nextJSP);
	   } else if( action.equals("delete")){
		   IErpDebitList obj = new ErpDebitList();
		   // Mã ở đây chính là Id 
		   boolean check = obj.delete(Id);
		   if( check == false){
			   obj.setMsg("Đã xảy ra lỗi trong việc xoá"+ Id);
		   }
		   obj.init("");
		   session.setAttribute("obj", obj);
		   session.setAttribute("userId", userId);
		   session.setAttribute("userTen", userTen);
		   String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDebit.jsp";
		   response.sendRedirect(nextJSP);
	   }else if( action.equals("chot")){
		   IErpDebitList obj = new ErpDebitList();
		   // Mã ở đây chính là Id 
		   boolean check = obj.chot(Id);
		   if( check == false){
			   obj.setMsg("Đã xảy ra lỗi trong việc xoá"+ Id);
		   }
		   obj.init("");
		   session.setAttribute("obj", obj);
		   session.setAttribute("userId", userId);
		   session.setAttribute("userTen", userTen);
		   String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDebit.jsp";
		   response.sendRedirect(nextJSP);
		} else if (action.equals("excel")) {
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition","attachment; filename=debit.xls");
			IErpDebitList obj = new ErpDebitList();
			OutputStream out1 = response.getOutputStream();
			try {
				if (!CreatePivotTable(out1, response, request, Id)) {
					response.setContentType("text/html");
					PrintWriter writer = new PrintWriter(out);
					writer.print("Xin loi khong co bao cao trong thoi gian nay");
					writer.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.toString());
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDebit.jsp";
				response.sendRedirect(nextJSP);
				return;
			}
		}
	}

	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response, HttpServletRequest request,String id) throws IOException {
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\debit.xls");
		
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL97TO2003);

		isFillData = FillData(workbook, id);
   
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

	private boolean FillData(Workbook workbook, String id) {
		dbutils db = new dbutils();
		try{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
	
		Cell cell = null;
		
		// truy vấn thông tin công ty
		String sql = " select de.TIENTE_FK, tt.MA, case when de.LOAIDOITUONG ='1' then " +
					 " ncc.TEN when de.LOAIDOITUONG ='2' then  kh.Ten END as TENDOITUONG," +
					 " case when de.LOAIDOITUONG ='1' then " +
					 " ncc.DIACHI when de.LOAIDOITUONG ='2' then  kh.DiaChi END as DIACHIDOITUONG " +
					 " from ERP_DEBIT  de left join ERP_TIENTE tt on tt.PK_SEQ= de.TIENTE_FK  " +
					 " left join ERP_NHACUNGCAP  ncc on ncc.PK_SEQ = de.DOITUONG  " +
					 " left join ERP_KHACHHANG kh on kh.PK_SEQ = de.DOITUONG"+
					 " where de.PK_SEQ = "+id;
		String tenNCC = "";
		String diaChiNCC = "";
		String maTienTe = "";
		
		ResultSet rs = db.get(sql);
		if( rs!=null){
			if( rs.next()){
				tenNCC = rs.getString("TENDOITUONG");
				diaChiNCC = rs.getString("DIACHIDOITUONG");
				maTienTe = rs.getString("MA");
			}
			rs.close();
		}
		
		cell = cells.getCell("D10" );		cell.setValue(tenNCC);	
		cell = cells.getCell("D11");		cell.setValue(diaChiNCC);
		
		//ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Ngày :___________ ");
		
		// truy vấn thông tin debit_details
		sql = "  select DIENGIAI, NONGUYENTE from ERP_DEBIT_DETAILS where DEBIT_FK = "+ id;
		
		List<String> dienGiais = new ArrayList<String>();
		List<Double> noNguyenTes = new ArrayList<Double>();
		rs = db.get(sql);
		double tongTien = 0;
		if( rs !=null){
			while( rs.next()){
				dienGiais.add(rs.getString("DIENGIAI"));
				noNguyenTes.add(rs.getDouble("NONGUYENTE"));
				tongTien += rs.getDouble("NONGUYENTE");
			}
			rs.close();
		}
		/*Vị trí bắt đầu*/
		int index = 17;
		for( int i=0; i< dienGiais.size(); i++){
			
			// set đường gạch đứng
			cell= cells.getCell("A"+ index);
			Style style = cell.getStyle();
			style.setBorderLine(BorderType.LEFT,BorderLineType.THIN);
			cell.setStyle(style);
			
			cell= cells.getCell("A"+ (index+1));
			style = cell.getStyle();
			style.setBorderLine(BorderType.LEFT,BorderLineType.THIN);
			cell.setStyle(style);
			
			// số thứ tự
			cell = cells.getCell("B"+index );
			style = cell.getStyle();
			Font font = style.getFont();
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			cell.setValue((i+1));
			
			// set đường gạch đứng
			cell= cells.getCell("D"+ index);
			style = cell.getStyle();
			style.setBorderLine(BorderType.RIGHT,BorderLineType.THIN);
			cell.setStyle(style);
			
			cell= cells.getCell("D"+ (index+1));
			style = cell.getStyle();
			style.setBorderLine(BorderType.RIGHT,BorderLineType.THIN);
			cell.setStyle(style);
			
			// diễn giải
			cell = cells.getCell("E"+index );		
			cell.setValue(dienGiais.get(i));	
			
			
			cell= cells.getCell("H"+ index);
			style = cell.getStyle();
			style.setBorderLine(BorderType.RIGHT,BorderLineType.THIN);
			cell.setStyle(style);
			
			cell= cells.getCell("H"+ (index+1));
			style = cell.getStyle();
			style.setBorderLine(BorderType.RIGHT,BorderLineType.THIN);
			cell.setStyle(style);
			
			
			// số tiền
			cell = cells.getCell("I"+index );		
			cell.setValue(noNguyenTes.get(i) +"  " +maTienTe);
			style = cell.getStyle();
			font = style.getFont();
			style.setNumber(4);
			font.setBold(true);
			style.setFont(font);
			cell.setStyle(style);
			
			cell= cells.getCell("J"+ index);
			style = cell.getStyle();
			style.setBorderLine(BorderType.RIGHT,BorderLineType.THIN);
			cell.setStyle(style);
			
			cell= cells.getCell("J"+ (index+1));
			style = cell.getStyle();
			style.setBorderLine(BorderType.RIGHT,BorderLineType.THIN);
			cell.setStyle(style);
			
			index= index +2;
		}
		
		// Total từ A đến J
		cell= cells.getCell("A"+ index);
		Style style = cell.getStyle();
		style.setBorderLine(BorderType.LEFT,BorderLineType.THIN);
		cell.setStyle(style);
		setBorderTotal(cell);
		
		cell= cells.getCell("B"+ index);
		setBorderTotal(cell);
		
		cell= cells.getCell("C"+ index);
		setBorderTotal(cell);
		
		cell= cells.getCell("D"+ index);
		setBorder3Total(cell);
		// set đứng
		
		
		cell= cells.getCell("E"+ index);
		setBorderTotal(cell);
		
		cell= cells.getCell("F"+ index);
		setBorderTotal(cell);
		
		cell= cells.getCell("G"+ index);
		cell.setValue("TOTAL");
		style = cell.getStyle();
		Font font = style.getFont();
		font.setBold(true);
		style.setFont(font);
		cell.setStyle(style);
		setBorderTotal(cell);
		
		cell= cells.getCell("H"+ index);
		setBorder3Total(cell);
		//set đứng
		
		cell= cells.getCell("I"+ index);
		cell.setValue(tongTien + "  " +maTienTe);
		style = cell.getStyle();
		style.setNumber(4);
		font = style.getFont();
		font.setBold(true);
		style.setFont(font);
		cell.setStyle(style);
		setBorderTotal(cell);
		
		cell= cells.getCell("J"+ index);
		setBorder3Total(cell);
		
		//set đứng
		
		if(db != null)
			db.shutDown();
		return true;
		} catch ( Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	private void setBorderTotal( Cell cell){
		Style style = cell.getStyle();
		style.setBorderLine(BorderType.TOP,BorderLineType.THIN);
		style.setBorderLine(BorderType.BOTTOM,BorderLineType.THIN);
		cell.setStyle(style);
	}
	
	private void setBorder3Total( Cell cell){
		Style style = cell.getStyle();
		style.setBorderLine(BorderType.TOP,BorderLineType.THIN);
		style.setBorderLine(BorderType.BOTTOM,BorderLineType.THIN);
		style.setBorderLine(BorderType.RIGHT,BorderLineType.THIN);
		cell.setStyle(style);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		Utility util= new  Utility();
		session.setAttribute("util",util);  
		String userId = (String)session.getAttribute("userId");
		String userTen=(String)session.getAttribute("userTen");
		
		
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		
		ErpDebit obj = new ErpDebit();
		String action = request.getParameter("action");		
 		String nextJSP="";
 		
 		// get tham so tu url
 		String ngayGhiNhan = request.getParameter("ngayghinhan");
 		if(ngayGhiNhan == null){
 			ngayGhiNhan = "";
 		}
 		obj.setNgayGhiNhan(ngayGhiNhan);
 		
 		String dienGiai = request.getParameter("diengiai");
 		if(dienGiai == null){
 			dienGiai = "";
 		}
 		obj.setDienGiai(dienGiai);
 		
 		String doiTuong = request.getParameter("doituong");
 		if( doiTuong == null){
 			doiTuong = "";
 		}
 		obj.setDoiTuong(doiTuong);
 		
 		String tienTe = request.getParameter("tiente");
 		if( tienTe == null){
 			tienTe= ""; // mặc định là usd
 		}
 		obj.setTienTe(tienTe);
 		
 		String id = request.getParameter("id");  
 		if( id == null){
 			id ="";
 		} 
 	
 		
 		String tongTienNguyenTe = request.getParameter("tongtiennguyente");
 		if(tongTienNguyenTe == null){
 			tongTienNguyenTe = "0";
 		}
 		double temp = Double.parseDouble(tongTienNguyenTe.replaceAll(",", ""));
 		obj.setTongTienNguyenTe(temp);
 		
 		String tongtienVND = request.getParameter("tongtienVND");
 		if( tongtienVND == null){
 			tongtienVND = "0";
 		}
 		temp = Double.parseDouble(tongtienVND.replaceAll(",", ""));
 		obj.setTongTienVND(temp);
 		
 		String tigia = request.getParameter("tigia");
 		if( tigia == null){
 			tigia = "0";
 		}
 		double tempf = Double.parseDouble(tigia);
 		obj.setTigia(tempf);
 		
 		String[] taiKhoans = request.getParameterValues("taikhoan");
 		String[] noNguyenTes = request.getParameterValues("nonguyente");
 		String[] noVNDs = request.getParameterValues("noVND");
 		String[] moTas = request.getParameterValues("mota");
 		
 		if(taiKhoans !=null){
 			obj.setTaiKhoans(convertArrayToList(taiKhoans));
 		}
 		if( noNguyenTes !=null){
 			obj.setNoNguyenTes(convertArrayToDouble(noNguyenTes));
 		}
 		if(noVNDs != null){
 			obj.setNoVNDs(convertArrayToDouble(noVNDs));
 		}
 		if(moTas != null){
 			obj.setMoTas(convertArrayToList(moTas));
 		}
 		if( action.equals("new")){
 			obj.setNguoiTao(userId);
 			obj.setNguoiSua(userId);
 			obj.setNgaySua(getDateTime());
 			obj.setNgayTao(getDateTime());
 			if(!obj.create()){
 				obj.init(obj.getTienTe(), true);
 				session.setAttribute("obj", obj);
 				session.setAttribute("userId", userId);
 				session.setAttribute("userTen", userTen);
 				session.setAttribute("action", "new");
 				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDebitNew.jsp";
 				response.sendRedirect(nextJSP);
 			} else{
 				IErpDebitList bean = new ErpDebitList();
 				bean.init("");
 				
 				session.setAttribute("obj", bean);
 				session.setAttribute("userId", userId);
 				session.setAttribute("userTen", userTen);
 				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDebit.jsp";
 				response.sendRedirect(nextJSP);
 			}
 		} else if( action.equals("update")){
 			obj.setId(id);
 			obj.setNguoiSua(userId);
 			obj.setNgaySua(getDateTime());
 			if(!obj.update()){
 				obj.init(obj.getTienTe(), true);
 				session.setAttribute("obj", obj);
 				session.setAttribute("userId", userId);
 				session.setAttribute("userTen", userTen);
 				session.setAttribute("action", "update");
 				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDebitNew.jsp";
 				response.sendRedirect(nextJSP);
 			} else{
 				IErpDebitList bean = new ErpDebitList();
 				bean.init("");
 				
 				session.setAttribute("obj", bean);
 				session.setAttribute("userId", userId);
 				session.setAttribute("userTen", userTen);
 				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDebit.jsp";
 				response.sendRedirect(nextJSP);
 			}
 		} else{
 				obj.setId(id);
 				obj.init(obj.getTienTe(), false);
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				session.setAttribute("userTen", userTen);
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDebitNew.jsp";
				response.sendRedirect(nextJSP);
 		}
 		
	}
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private List<String> convertArrayToList(String[] string){
		List<String> list = new ArrayList<String>();
		for(int i=0; i< string.length; i++){
			list.add(string[i]);
		}
		return list;
		
	}
	private List<Double> convertArrayToDouble(String[] string){
		List<Double> list = new ArrayList<Double>();
		for(int i=0; i< string.length; i++){
			if(string[i].trim().length() == 0){
				list.add(new Double("0"));
			} else {
				list.add(Double.parseDouble(string[i].replaceAll(",", "")));
			}
		}
		return list;
	}
}
