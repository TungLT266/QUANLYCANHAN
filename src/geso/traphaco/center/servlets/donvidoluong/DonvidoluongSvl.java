package geso.traphaco.center.servlets.donvidoluong;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;

import geso.traphaco.center.db.sql.*;
import geso.traphaco.center.beans.donvidoluong.imp.Donvidoluong;
import geso.traphaco.center.beans.donvidoluong.imp.DonvidoluongList;
import geso.traphaco.center.beans.donvidoluong.IDonvidoluong;
import geso.traphaco.center.beans.donvidoluong.IDonvidoluongList;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.util.UtilitySyn;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DonvidoluongSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;

	public DonvidoluongSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		    dbutils db  = new dbutils();
		    Utility util = new Utility();
		   //HttpServletRequest request;
		    IDonvidoluongList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    obj = new DonvidoluongList();
		
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String donviId = util.getId(querystring);
	    out.println(donviId);
	    
	    //Is a Nhan hang deleted?
	    if (action.equals("delete")){	   		
		   	Delete(donviId);
	    }
	    session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
			
		String nextJSP = "/TraphacoHYERP/pages/Center/DonViDoLuong.jsp";
		response.sendRedirect(nextJSP);
		
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    PrintWriter out = response.getWriter();
	    
		dbutils db  = new dbutils();
	    Utility util = new Utility();
	   //HttpServletRequest request;
	    IDonvidoluongList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		HttpSession session = request.getSession();
	    //this.request = request;
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
	    if (request.getParameter("action").equals("search"))
	    {
	    	obj = new DonvidoluongList();
	    	
	    	String search = getSearchQuery(request,obj);
	    	
	    	obj.setQuery(search);
	    			    
			session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/DonViDoLuong.jsp");
	    }

	    else if (request.getParameter("action").equals("excel")) {
	    	obj = new DonvidoluongList(); obj.DBClose();
	    	ToExcel(response, getSearchQuery(request,obj));
	    }
	    
	    // Create a new Business Unit
	    else if (request.getParameter("action").equals("new")){
	    	// Empty Bean for distributor
	    	IDonvidoluong dvdlBean = new Donvidoluong();
	    	
	    	// Save Data into session
    		session.setAttribute("dvdlBean", dvdlBean);
    		session.setAttribute("userId", userId);
    		
	    	String nextJSP = "/TraphacoHYERP/pages/Center/DonViDoLuongNew.jsp";
    		response.sendRedirect(nextJSP);
	    
	    }

	}

	private void Delete(String donviId)
	{
		dbutils db  = new dbutils();
	   //HttpServletRequest request;
	    IDonvidoluongList obj = new DonvidoluongList();
		
	   	db = new dbutils();
	   	ResultSet rs = db.get("select count(pk_seq) as num from sanpham where dvdl_fk ='" + donviId + "'");
	   	try{
	   	    while(rs.next())
	   		if(rs.getString("num").equals("0"))
	   		{
	   			String command = "delete from donvidoluong where pk_seq ='" + donviId + "'";
	   			db.update(command);
	   			
	   			//SYN QUA DMS
				UtilitySyn.SynData(db, "DONVIDOLUONG", "DONVIDOLUONG", "PK_SEQ", donviId, "2", true);
	   			
	   			db.shutDown();
	   		}
	   		else
	   			obj.setMsg("Don vi do luong nay da co san pham roi, khong the xoa duoc");
	   	}
	   	catch(Exception e){}
	}


	private String getSearchQuery(ServletRequest request,IDonvidoluongList obj){
		
		dbutils db  = new dbutils();
	    Utility util = new Utility();
	   //HttpServletRequest request;
		
//	    PrintWriter out = response.getWriter();
		String dvdl = util.antiSQLInspection(request.getParameter("dvdl"));
    	if (dvdl == null)
    		dvdl = "";
    	obj.setDvdl(dvdl);
    	
		String diengiai = request.getParameter("diengiai");
    	if (diengiai == null)
    		diengiai = "";
    	 
    	obj.setDiengiai(diengiai);
    	
    	String tungay = request.getParameter("tungay");
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = request.getParameter("denngay");
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String trangthai = request.getParameter("trangthai");   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String query = "select a.pk_seq, a.donvi, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from donvidoluong a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
    	
    	if (dvdl.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.donvi)) like upper(N'%" + util.replaceAEIOU(dvdl) + "%')";
			
    	}

    	if (diengiai.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";
			
    	}
    	
    	if (tungay.length() > 0) {
    		query = query + " and a.ngaytao >= '" + tungay + "'";
    	}
    	
    	if (denngay.length() > 0) {
    		query = query + " and a.ngaytao <= '" + denngay + "'";
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    	}
    	System.out.println("Tim kiem : "+query);
		return query;
	}


	private void ToExcel(HttpServletResponse response, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=DonViDoLuong.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("DonViDoLuong", k);
			sheet.addCell(new Label(0, 1, "ĐƠN VỊ ĐO LƯỜNG: ", new WritableCellFormat(cellTitle)));

			sheet.addCell(new Label(0, 2, "Ngày tạo: "));
			sheet.addCell(new Label(1, 2, "" + getDateTime()));

			sheet.addCell(new Label(2, 4, "Đơn vị tiền tệ:VND"));

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.LIME);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.LIGHT_ORANGE);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "ĐƠN VỊ ĐO LƯỜNG", cellFormat));
			sheet.addCell(new Label(2, 4, "DIỄN GIẢI", cellFormatSpecical));
			sheet.addCell(new Label(3, 4, "TRẠNG THÁI", cellFormat));
			sheet.addCell(new Label(4, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(5, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(6, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(7, 4, "NGƯỜI SỬA", cellFormat));

			sheet.setRowView(100, 4);

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 30);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 15);
			sheet.setColumnView(7, 35);
			sheet.setColumnView(8, 15);
			sheet.setColumnView(9, 15);
			sheet.setColumnView(10, 15);
			sheet.setColumnView(11, 15);
			sheet.setColumnView(12, 15);
			sheet.setColumnView(13, 15);
			sheet.setColumnView(14, 60);
			dbutils db = new dbutils();

			ResultSet rs = db.get(query);

			WritableCellFormat cellFormat2 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormat3 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cformat = null;
			Label label;
			Number number;

			int stt = 0;
			while (rs.next())
			{
				stt++;
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;

				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, rs.getString("donvi"), cformat);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("diengiai"), cformat);
				sheet.addCell(label);
				label = new Label(3, j, rs.getInt("trangthai") == 0 ? "Ngưng hoạt động" : "Hoạt động", cformat);
				sheet.addCell(label);
				label = new Label(4, j, rs.getString("NgayTao"), cformat);
				sheet.addCell(label);
				label = new Label(5, j, rs.getString("NguoiTao"), cformat);
				sheet.addCell(label);
				label = new Label(6, j, rs.getString("NgaySua"), cformat);
				sheet.addCell(label);
				label = new Label(7, j, rs.getString("NguoiSua"), cformat);
				sheet.addCell(label);

				j++;
			}
			w.write();
			w.close();
			rs.close();
			db.shutDown();
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out != null)
				out.close();

		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}