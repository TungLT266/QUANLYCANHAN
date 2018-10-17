package geso.traphaco.center.servlets.nhanhang;

import geso.traphaco.center.beans.nhanhang.INhanhang;
import geso.traphaco.center.beans.nhanhang.INhanhangList;
import geso.traphaco.center.beans.nhanhang.imp.Nhanhang;
import geso.traphaco.center.beans.nhanhang.imp.NhanhangList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

public class NhanhangSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   
   public NhanhangSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		Utility util = new Utility();
		//HttpServletRequest request;
		INhanhangList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    obj = (INhanhangList) new NhanhangList();
	    	
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    	    
	   String idlist = util.getId(querystring).split(";")[0];
	 //  String idlist1 = util.getId(querystring).split(";")[1];
	  
	    out.println(idlist);
	  //  System.out.println("ma:"+idlist);
	    //Is a Nhan hang deleted?
	    if (action.equals("delete")){	   		
		  	Delete(idlist);
	    }
	   
	    // Collect all of Nhan hang, each Nhan hang is saved into Bean Nhanhang
	    String query = "select a.pk_seq, a.ten, b.pk_seq as dvkdId, b.donvikinhdoanh, a.trangthai, a.ngaytao, a.ngaysua, c.ten as nguoitao, d.ten as nguoisua from nhanhang a, donvikinhdoanh b, nhanvien c, nhanvien d where a.dvkd_fk = b.pk_seq and a.nguoitao = c.PK_SEQ and a.nguoisua = d.PK_SEQ  ";

	    ResultSet nhlist = getNhBeanList(query);
	    
		// Save data into session
		obj.setNhlist(nhlist);
		
	    session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
			
		String nextJSP = "/TraphacoHYERP/pages/Center/NhanHang.jsp";
		response.sendRedirect(nextJSP);
		
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		Utility util = new Utility();
		//HttpServletRequest request;
		INhanhangList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		//PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    //this.request = request;
	    obj = (INhanhangList) new NhanhangList();
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    
	    if (action.equals("search")) {
	    	String search = getSearchQuery(request,obj);
	    	//out.println(search);

	    	ResultSet nhlist = getNhBeanList(search);	    	

    		// Saving data into session
    		obj.setNhlist(nhlist);
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
	  
    		response.sendRedirect("/TraphacoHYERP/pages/Center/NhanHang.jsp");
	    }
	    else if (action.equals("excel")) {
	    	ToExcel(response, getSearchQuery(request,obj));
	    }
	    else if (action.equals("new")) {
	    	// Empty Bean for distributor
	    	INhanhang nhanhangBean = new Nhanhang();
	    	
	    	// Save Data into session
    		session.setAttribute("nhanhangBean", nhanhangBean);
    		session.setAttribute("userId", userId);
    		
	    	String nextJSP = "/TraphacoHYERP/pages/Center/NhanHangNew.jsp";
    		response.sendRedirect(nextJSP);
	    
	    }

	}

	private void Delete(String idlist){
		
		//Utility util = new Utility();
		//HttpServletRequest request;
		INhanhangList obj = new NhanhangList();
		
		dbutils db = new dbutils();
		 String nhId = idlist;
	  //  String nhId = idlist.split(";")[0];
	 //   String dvkdId = idlist.split(";")[1];
	    String sql = "select count(pk_seq) as num from sanpham where nhanhang_fk='" + nhId + "'";
	  //  System.out.println(sql);
	    if(kiemtra(sql))
	    {
	    	sql ="select count(cl_fk) as num from nhanhang_chungloai where nh_fk='" + nhId + "'";
	    //	System.out.println(sql);
	    	if(kiemtra(sql))
	    	{
	    		String command = "delete from nhanhang where pk_seq ='" + nhId + "'";
	    	//	System.out.println(sql);
    			db.update(command);
	    	}
	    	else
	    		obj.setMsg("Nhan hang da co chung loai roi nen khong xoa duoc");
	    }
	    else
	    	obj.setMsg("Nhan hang da co trong san pham roi nen khong xoa duoc");
	    	
/*
	    ResultSet rs = db.get("select count(pk_seq) as num from sanpham where nhanhang_fk='" + nhId + "'");
	    try{
	    	while(rs.next())
	    	{
	    	if (rs.getString("num").equals("0")){
	    		
	    		rs = db.get("select count(cl_fk) as num from nhanhang_chungloai where nh_fk='" + nhId + "'");
	    		rs.next();
	    		if(rs.getString("num").equals("0")){	    			
    			
	    			String command = "delete from nhanhang where pk_seq ='" + nhId + "'";
	    			db.update(command);
	    		}
	    	
	    			
	    	}
	   	}catch(Exception e){
	   		
	   	}
		*/
	}
	    boolean kiemtra(String sql)
		{dbutils db =new dbutils();
	    	ResultSet rs = db.get(sql);
			try {//kiem tra ben san pham
			while(rs.next())
			{ if(rs.getString("num").equals("0"))
			   return true;
			}
			} catch(Exception e) {
			
				e.printStackTrace();
			}
			 return false;
		}
	private ResultSet getNhBeanList(String query){  	
		dbutils db = new dbutils();
		ResultSet rs =  db.get(query);
		return rs;
	}

	private String getSearchQuery(ServletRequest request,INhanhangList obj){
		
		Utility util = new Utility();
		//HttpServletRequest request;
		
		
//	    PrintWriter out = response.getWriter();
		//Utility util = new Utility();
		String nhanhang = util.antiSQLInspection(request.getParameter("nhanhang"));
    	if (nhanhang == null)
    		nhanhang = "";
    	obj.setNhanhang(nhanhang);
    	
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
    	if (dvkdId == null)
    		dvkdId = "";
    	obj.setDvkdId(dvkdId);

    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
    	if (trangthai == null)
			trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	if (trangthai.equals("2"))
    		trangthai = "";    	
    	


    	String query = "select a.pk_seq, a.ten, b.pk_seq as dvkdId, b.donvikinhdoanh, a.trangthai, a.ngaytao, a.ngaysua, c.ten as nguoitao, d.ten as nguoisua from nhanhang a, donvikinhdoanh b, nhanvien c, nhanvien d where a.dvkd_fk = b.pk_seq and a.nguoitao = c.PK_SEQ and a.nguoisua = d.PK_SEQ  ";
    	
    	if (nhanhang.length()>0){
			query = query + " and upper(a.ten) like upper(N'%" + util.replaceAEIOU(nhanhang) + "%')";
    	}
    	
    	if (dvkdId.length()>0){
			query = query + " and b.pk_seq ='" + dvkdId + "'";
    	}

    	if (tungay.length()>0) {
    		query = query + " and a.ngaytao >= '" + tungay + "'";
    	}
    	
    	if (denngay.length()>0) {
    		query = query + " and a.ngaytao <= '" + denngay + "'";
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    	}
    	System.out.print(query);
		return query;
	}	


	private void ToExcel(HttpServletResponse response, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=NhanHang.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("NhanHang", k);
			sheet.addCell(new Label(0, 1, "NHÃN HÀNG: ",
					new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "NHÃN HÀNG", cellFormat));
			sheet.addCell(new Label(2, 4, "ĐƠN VỊ KINH DOANH",
					cellFormatSpecical));
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
				label = new Label(1, j, rs.getString("ten"), cformat);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("donvikinhdoanh"), cformat);
				sheet.addCell(label);
				label = new Label(3, j,
						rs.getInt("trangthai") == 0 ? "Ngưng hoạt động"
								: "Hoạt động", cformat);
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