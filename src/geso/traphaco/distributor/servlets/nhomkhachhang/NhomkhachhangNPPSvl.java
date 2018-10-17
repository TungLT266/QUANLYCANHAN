package geso.traphaco.distributor.servlets.nhomkhachhang;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.nhomkhachhang.INhomkhachhangListNPP;
import geso.traphaco.distributor.beans.nhomkhachhang.INhomkhachhangNPP;
import geso.traphaco.distributor.beans.nhomkhachhang.imp.NhomkhachhangListNPP;
import geso.traphaco.distributor.beans.nhomkhachhang.imp.NhomkhachhangNPP;

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
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

public class NhomkhachhangNPPSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   PrintWriter out;
   
	public NhomkhachhangNPPSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		//HttpServletRequest request;
		  //HttpServletResponse response;
		   INhomkhachhangListNPP obj;
		   dbutils db;
		
	   // this.request = request;
	    //this.response = response;
	    db = new dbutils();
	    
	    response.setContentType("text/html");
	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    obj = new NhomkhachhangListNPP();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String nspId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(nspId);

	    }
	    
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    List<INhomkhachhangNPP> nkhlist = new ArrayList<INhomkhachhangNPP>(); 
	    
	    getNkhBeanList(nkhlist,"", (String)session.getAttribute("congtyId"));
	    
		// Save data into session
	    obj.setNkhList(nkhlist);
	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		session.setAttribute("congtyId", session.getAttribute("congtyId"));
		
		String nextJSP = "/TraphacoHYERP/pages/Distributor/NhomKhachHangNPP.jsp";
		response.sendRedirect(nextJSP);
	    
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		//HttpServletRequest request;
		  //HttpServletResponse response;
		   INhomkhachhangListNPP obj;
		   dbutils db;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    
		HttpSession session = request.getSession();
	    String userId = request.getParameter("userId");

	    obj = new NhomkhachhangListNPP();
	    db = new dbutils();
	    

	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    
	    // Perform searching. Each Nhomkhachhang is saved into Nhomkhachhang
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	INhomkhachhangNPP nkhBean = (INhomkhachhangNPP) new NhomkhachhangNPP();
	    	System.out.println("cong ty ID " + session.getAttribute("congtyId"));
	    	nkhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	nkhBean.setUserId(userId);
	    	nkhBean.UpdateRS();
	    	// Save Data into session
    		session.setAttribute("nkhBean", nkhBean);
    		session.setAttribute("userId", userId);
    		session.setAttribute("congtyId", session.getAttribute("congtyId"));

    		String nextJSP = "/TraphacoHYERP/pages/Distributor/NhomKhachHangNPPNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search")){
	    	
		    	String search = getSearchQuery(request, obj, (String)session.getAttribute("congtyId"));
		    	System.out.print("Search : "+search);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	obj.setUserId(userId);
//		    	out.println(search);
		    	List<INhomkhachhangNPP> nkhlist = new ArrayList<INhomkhachhangNPP>(); 
		    	getNkhBeanList(nkhlist, search, (String)session.getAttribute("congtyId"));	    	

	    		// Saving data into session
			    obj.setNkhList(nkhlist);
			    obj.setSearch(true);
				session.setAttribute("obj", obj);

	    		session.setAttribute("userId", userId);
	    		session.setAttribute("congtyId", session.getAttribute("congtyId"));
	    		
	    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/NhomKhachHangNPP.jsp");
		    }
	    if (action.equals("1")){
	    	List<INhomkhachhangNPP> nkhlist = new ArrayList<INhomkhachhangNPP>(); 
	    	obj.setCongtyId((String)session.getAttribute("congtyId"));
	    	obj.setUserId(userId);
	    	getNkhBeanList(nkhlist, "", (String)session.getAttribute("congtyId"));
	    
		// 	Save data into session
	    	obj.setNkhList(nkhlist);
	    
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userId", userId);
	    	session.setAttribute("congtyId", session.getAttribute("congtyId"));
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Distributor/NhomKhachHangNPP.jsp";
	    	response.sendRedirect(nextJSP);
	    }
	    
	    if (action.equals("excel"))
		{
			ToExcel(response, obj,  getSearchQuery(request,obj, (String)session.getAttribute("congtyId")));
		}
	    
	    
	}

	private void Delete(String nkhId){
	    
		//HttpServletRequest request;
		  //HttpServletResponse response;
		   INhomkhachhangListNPP obj;
		   dbutils db = new dbutils();
		
		String command;
		
		command = "delete from nhomkhachhang_khachhang where nkh_fk ='" + nkhId + "'";
		db.update(command);

		command = "delete from nhomkhachhang where pk_seq ='" + nkhId + "'";
	   	db.update(command);
		
	}

	
	private void  getNkhBeanList(List<INhomkhachhangNPP> nkhlist, String search, String congtyId){	
	    String query;
	    
	    
	  //HttpServletRequest request;
		  //HttpServletResponse response;
		   INhomkhachhangListNPP obj;
		   dbutils db = new dbutils();
	    
	    if (search.length() > 0){
	    	query = search;
	    }else{
	    	query = "select a.ten,a.pk_seq, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomkhachhangnpp a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ  order by diengiai";
	    }
	   	ResultSet rs = db.get(query);
	   	try{	
	   		String[] param = new String[11];
    		INhomkhachhangNPP nkhBean;
    		while (rs.next()){	    			
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("diengiai");				
				param[2]= rs.getString("trangthai");
				param[3]= rs.getString("ngaytao");
				param[4]= rs.getString("ngaysua");
				param[5]= rs.getString("nguoitao");
				param[6]= rs.getString("nguoisua");			
				
				nkhBean = new NhomkhachhangNPP(param);	
				nkhBean.setTen(rs.getString("ten"));
				nkhlist.add(nkhBean);
    		}  
    		db.shutDown();
	   	}catch(Exception e){}
	}
	

	private String getSearchQuery(ServletRequest request, INhomkhachhangListNPP obj, String congtyId){
//	    PrintWriter out = response.getWriter();
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		
		//HttpServletRequest request;
		//HttpServletResponse response;
		//obj = new NhomkhachhangList();		
		
		String maKH = request.getParameter("maKH");
		if(maKH == null)
			maKH = "";
		obj.setMaKH(maKH);
		
		String diengiai = request.getParameter("diengiai");
    	if (diengiai == null)
    		diengiai = "";
    	obj.setDiengiai(diengiai);

    	String thanhvien = request.getParameter("thanhvien");
    	if (thanhvien == null)
    		thanhvien = "";    	
    	   		 
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
    	obj.setTrangthai(trangthai);
	
    	if (trangthai.equals("2"))
    		trangthai = "";

    	//String query = "select a.pk_seq, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomkhachhang a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
    	String query = "select distinct a.pk_seq, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomkhachhang a, khachhang kh, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and congty_fk = '"+congtyId+"' ";
    	
    	if(maKH.length() > 0){
    		query += " and upper(a.pk_seq) like upper('%" + maKH + "%')";
    		
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
    	query = query + "  order by diengiai";
    	return query;
	}
	private void ToExcel(HttpServletResponse response, INhomkhachhangListNPP obj, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=NhomKhachHang.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("NhomKhachHang", k);
			sheet.addCell(new Label(0, 1, "Nhóm khách hàng : ", new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "DIỄN GIẢI", cellFormatSpecical));
			sheet.addCell(new Label(2, 4, "TRẠNG THÁI", cellFormat));
			sheet.addCell(new Label(3, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(4, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(5, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(6, 4, "NGƯỜI SỬA", cellFormat));

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
			int stt=0;
			while (rs.next())
			{
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;
				stt++;
				number = new Number(0, j, stt, cformat);sheet.addCell(number);
				label = new Label(1, j, rs.getString("diengiai"), cformat);sheet.addCell(label);
				label = new Label(2, j, rs.getInt("trangthai") == 0 ? "Ngưng hoạt động" : "Hoạt động", cformat);sheet.addCell(label);
				label = new Label(3, j, rs.getString("ngaytao"), cformat);sheet.addCell(label);
				label = new Label(4, j, rs.getString("NguoiTao"), cformat);sheet.addCell(label);
				label = new Label(5, j, rs.getString("NgaySua"), cformat);sheet.addCell(label);
				label = new Label(6, j, rs.getString("NguoiSua"), cformat);sheet.addCell(label);

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
