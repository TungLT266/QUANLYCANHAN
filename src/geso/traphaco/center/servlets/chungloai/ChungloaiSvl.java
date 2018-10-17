package geso.traphaco.center.servlets.chungloai;

import geso.traphaco.center.beans.chungloai.IChungloai;
import geso.traphaco.center.beans.chungloai.imp.Chungloai;
import geso.traphaco.center.beans.chungloai.IChungloaiList;
import geso.traphaco.center.beans.chungloai.imp.ChungloaiList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
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

 public class ChungloaiSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;

   public ChungloaiSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util = new Utility();
		IChungloaiList obj;

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    obj = new ChungloaiList();
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String idlist = util.getId(querystring);

	    
	    //Is a Chung loai deleted?
	    if (action.equals("delete")){	   		
		   	Delete(idlist, obj);
	    }
	    
	    // Collect all of Chung loai, each Chung loai is saved into Bean Chungloai
	    
	    ResultSet clList = getChungloaiBeanList("");
	    obj.setClList(clList);	    
		
		// Save data into session
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
			
		String nextJSP = "/TraphacoHYERP/pages/Center/ChungLoai.jsp";
		response.sendRedirect(nextJSP);
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    //PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    IChungloaiList obj;

	    obj = new ChungloaiList();
	    
	    String userId = request.getParameter("userId");
	    
	    // Perform searching. 
	    if (request.getParameter("action").equals("search")){
	    	String search = getSearchQuery(request, obj);
	    	System.out.println(search);

	    	ResultSet cllist = getChungloaiBeanList(search);	    	
    	    obj.setClList(cllist);	    
    		
    		session.setAttribute("userId", userId);
    			
    		// Saving data into session
    		session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/ChungLoai.jsp");
	    }

	    else if (request.getParameter("action").equals("excel")) {
	    	ToExcel(response, getSearchQuery(request,obj));
	    }
	    
	    // Create a new Business Unit
	    else if (request.getParameter("action").equals("new")){
	    	// Empty Bean for distributor
	    	IChungloai cloaiBean = new Chungloai();	    	
	    	ResultSet nhlist = (ResultSet)getNhanhangList();
	    	// Save Data into session
	    	
    		session.setAttribute("cloaiBean", cloaiBean);
    		session.setAttribute("nhlist", nhlist);
    		session.setAttribute("userId", userId);
    		
	    	String nextJSP = "/TraphacoHYERP/pages/Center/ChungLoaiNew.jsp";
    		response.sendRedirect(nextJSP);
	    
	    }
		
	}   
	
/*	private void Delete(String idlist){
		dbutils db = new dbutils();
		String clId = idlist.split(";")[0];
	    String nhId = idlist.split(";")[1];

	    ResultSet rs = db.get("select count(pk_seq) as num from sanpham where chungloai_fk='" + clId + "'");
	    try{
	    	rs.next();
	    	if (rs.getString("num").equals("0")){
	    		String command = "delete from nhanhang_chungloai where cl_fk ='" + clId + "' and nh_fk='" + nhId + "'";
	    		db.update(command);
	    		
	    		rs = db.get("select count(cl_fk) as num from nhanhang_chungloai where cl_fk='" + clId + "'");
	    		rs.next();
	    		if(rs.getString("num").equals("0")){	    			
	    			command = "delete from chungloai where pk_seq ='" + clId + "'";
	    			db.update(command);
	    		}
	    	}
	   	}catch(Exception e){
	   		
	   	}
		
	}
	*/
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


	private void Delete(String idlist, IChungloaiList obj){
		dbutils db = new dbutils();
		String clId = idlist.split(";")[0];
	    String nhId = idlist.split(";")[1];
	    
	    String sql ="select count(pk_seq) as num from sanpham where chungloai_fk='" + clId + "'";
	    if(kiemtra(sql))
	    {  
	    	String command = "delete from nhanhang_chungloai where cl_fk ='" + clId + "' and nh_fk='" + nhId + "'";
    		db.update(command);
    		sql = "select count(cl_fk) as num from nhanhang_chungloai where cl_fk='" + clId + "'";
    		if(kiemtra(sql))
    		{
    			command = "delete from chungloai where pk_seq ='" + clId + "'";
    			db.update(command);
    		}
	    	
	    }
	    else
	    	obj.setMsg("Da ton tai san pham cua chung loai nay roi, nen khong xoa duoc");

	  /*  ResultSet rs = db.get("select count(pk_seq) as num from sanpham where chungloai_fk='" + clId + "'");
	    try{
	    	rs.next();
	    	if (rs.getString("num").equals("0")){
	    		String command = "delete from nhanhang_chungloai where cl_fk ='" + clId + "' and nh_fk='" + nhId + "'";
	    		db.update(command);
	    		
	    		rs = db.get("select count(cl_fk) as num from nhanhang_chungloai where cl_fk='" + clId + "'");
	    		rs.next();
	    		if(rs.getString("num").equals("0")){	    			
	    			command = "delete from chungloai where pk_seq ='" + clId + "'";
	    			db.update(command);
	    		}
	    	}
	   	}catch(Exception e){
	   		
	   	}
		*/
	}
	private ResultSet getChungloaiBeanList(String search){
			ResultSet rs1;
			String query;
			dbutils db = new dbutils();
			if(search.length()==0){
				query = " select a.pk_seq as clId, a.ten as chungloai,a.trangthai, c.pk_seq as nhId, c.ten as nhanhang, "+
					" a.ngaytao, a.ngaysua, d.ten as nguoitao, e.ten as nguoisua from chungloai a left join   nhanhang_chungloai b on a.pk_seq=b.cl_fk "+
					" left join nhanhang c on c.pk_Seq=b.nh_fk  inner join  nhanvien d on d.pk_seq=a.nguoitao inner join   nhanvien e  on e.pk_seq=a.nguoisua "+
					" order by chungloai ";
				rs1 = db.get(query);   					
			}else{
				query = search;
				rs1 = db.get(query);
			}
			
		return rs1;
	}
	
	private ResultSet getNhanhangList(){
		String query;
		dbutils db = new dbutils();
		//query = "select pk_seq, ten, trangthai from nhanhang where trangthai='1'";
		query = "select a.pk_seq, a.ten, a.trangthai from nhanhang a,nhacungcap_dvkd b where a.dvkd_fk = b.dvkd_fk and b.checked ='1' and  a.trangthai='1'";
		ResultSet rs =  db.get(query);
		return rs;
		
	}
	
	private String getSearchQuery(HttpServletRequest request, IChungloaiList obj){
//	    PrintWriter out = response.getWriter();
		Utility util = new Utility();
		String cloai = util.antiSQLInspection(request.getParameter("chungloai"));
    	if (cloai == null)
    		cloai = "";
    	obj.setCloai(cloai);
    	
    	String nhId = util.antiSQLInspection(request.getParameter("nhId"));
    	
    	if (nhId == null)
    		nhId = "";    	
    	obj.setNhId(nhId);
    	
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
    	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String query = "select a.pk_seq as clId, a.ten as chungloai,a.trangthai, c.pk_seq as nhId, c.ten as nhanhang, "+
    		"  a.ngaytao, a.ngaysua, d.ten as nguoitao, e.ten as nguoisua from chungloai a left join   nhanhang_chungloai b on a.pk_seq=b.cl_fk "+
    		" left join nhanhang c on c.pk_Seq=b.nh_fk  inner join  nhanvien d on d.pk_seq=a.nguoitao inner join   nhanvien e  on e.pk_seq=a.nguoisua ";

    	if (cloai.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%"+ util.replaceAEIOU(cloai) + "%')";
    	}   		
    	
    	if (nhId.length()>0){
    		query = query + " and c.pk_seq = '" + nhId + "'";	    		
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
		return query + " order by chungloai";
	}


	private void ToExcel(HttpServletResponse response, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=ChungLoai.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("ChungLoai", k);
			sheet.addCell(new Label(0, 1, "CHỦNG LOẠI: ", new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "CHỦNG LOẠI", cellFormat));
			sheet.addCell(new Label(2, 4, "NHÃN HÀNG", cellFormatSpecical));
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
				label = new Label(1, j, rs.getString("chungloai"), cformat);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("nhanhang"), cformat);
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