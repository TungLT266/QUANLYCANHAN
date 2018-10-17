package geso.traphaco.center.servlets.kenhbanhang;

import geso.traphaco.center.beans.donvidoluong.imp.DonvidoluongList;
import geso.traphaco.center.beans.kenhbanhang.*;
import geso.traphaco.center.beans.kenhbanhang.imp.*;
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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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


public class KenhbanhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public KenhbanhangSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		IKenhbanhangList obj;
		PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new KenhbanhangList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String kbhId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(kbhId);
	    	out.print(kbhId);
	    }
	   	
	   
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/KenhBanHang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IKenhbanhangList obj;
		//PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    //out = response.getWriter();
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    //out.println(action); 
	        
	    
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	IKenhbanhang kbhBean = (IKenhbanhang) new Kenhbanhang("");
	    	kbhBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("kbhBean", kbhBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Center/KenhBanHangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }

	    else if (request.getParameter("action").equals("excel")) {
	    	obj = new KenhbanhangList();
	    	obj.init(getSearchQuery(request,obj));
	    	ToExcel(response, obj);
	    }
	    
	    else if (action.equals("search")){
	    	obj = new KenhbanhangList();
	    	String search = getSearchQuery(request,obj);
	    	
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/KenhBanHang.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,IKenhbanhangList obj)
	{
		   // PrintWriter out = response.getWriter();
		
		//IKenhbanhangList obj = new KenhbanhangList();
		PrintWriter out;
		
		Utility util = new Utility();
			
			String kenhbanhang = util.antiSQLInspection(request.getParameter("KenhBanHang"));
	    	if ( kenhbanhang == null)
	    		kenhbanhang = "";
	    	obj.setKenhbanhang(kenhbanhang );
	    	
	    	String diengiai = util.antiSQLInspection(request.getParameter("DienGiai"));
	    	if (diengiai == null)
	    		diengiai = "";    	
	    	obj.setDiengiai(diengiai);
	    	
	    	String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
		
	    	if (trangthai.equals("2"))
	    		trangthai = "";
	    	
	    	obj.setTrangthai(trangthai);
	    	//geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
			String query = "select a.pk_seq as id, a.ten as kbhTen, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from kenhbanhang a, nhanvien b, nhanvien c ";
			query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq"; 			
	    	if (kenhbanhang.length()>0){
	    	
				query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(kenhbanhang) + "%')";
				
	    	}
	    	
	    	if (diengiai.length()>0){
				query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";
				
	    	}
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and a.trangthai = '" + trangthai + "'";
	    		
	    	}
	    	query = query + "  order by a.ten";
	    	return query;
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
	private void Delete(String id)
	{
		
		IKenhbanhangList obj = new KenhbanhangList();
		PrintWriter out;
		
		dbutils db = new dbutils();
		String sql ="select count(kbh_fk) as num from khachhang where kbh_fk='"+ id + "'";
		if(kiemtra(sql))
		{  sql = " select count(kbh_fk) as num from giamsatbanhang where kbh_fk ='"+ id +"'";
		   System.out.println(sql);
		   if(kiemtra(sql))
		   {
			   db.update("delete from kenhbanhang where pk_seq = '" + id + "'");
				db.shutDown();
		   }
		   else
			   obj.setMsg("Kenh nay da co giam sat ban hang roi, khong the xoa duoc");
		}
		else
			obj.setMsg("Kenh nay da co khach hang roi, khong the xoa duoc");
	/*	ResultSet rs1 = db.get("select count(kbh_fk) as num from khachhang where kbh_fk='"+ id + "'");
		try{
			rs1.next();			
			if (rs1.getString("num").equals("0")){
				db.update("delete from kenhbanhang where pk_seq = '" + id + "'");
				db.shutDown();
			}
			else
				obj.setMsg("");
		}catch(Exception e){}
		*/
	}


	private void ToExcel(HttpServletResponse response, IKenhbanhangList obj) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=KenhBanHang.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("KenhBanHang", k);
			sheet.addCell(new Label(0, 1, "KÊNH BÁN HÀNG: ", new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "KÊNH BÁN HÀNG", cellFormat));
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
			
			List<IKenhbanhang> kbhlist = (List<IKenhbanhang>)obj.getKbhList();
			IKenhbanhang kbh = null;

			int stt = 0;
			for(int i = 0; i < kbhlist.size(); i++) {
				kbh = kbhlist.get(i);
				stt++;
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;

				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, kbh.getKenhbanhang(), cformat);
				sheet.addCell(label);
				label = new Label(2, j, kbh.getDiengiai(), cformat);
				sheet.addCell(label);
				label = new Label(3, j, kbh.getTrangthai().equals("1") ? "Hoạt động" : "Ngưng hoạt động", cformat);
				sheet.addCell(label);
				label = new Label(4, j, kbh.getNgaytao(), cformat);
				sheet.addCell(label);
				label = new Label(5, j, kbh.getNguoitao(), cformat);
				sheet.addCell(label);
				label = new Label(6, j, kbh.getNgaysua(), cformat);
				sheet.addCell(label);
				label = new Label(7, j, kbh.getNguoisua(), cformat);
				sheet.addCell(label);

				j++;
			}
			w.write();
			w.close();
			obj.DBClose();
			for(int i = 0; i < kbhlist.size(); i++){
				kbh = kbhlist.get(i);
				kbh.DBClose();
			}
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
