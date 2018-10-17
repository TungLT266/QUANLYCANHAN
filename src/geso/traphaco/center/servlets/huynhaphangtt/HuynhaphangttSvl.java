package geso.traphaco.center.servlets.huynhaphangtt;

import geso.traphaco.center.beans.huynhaphangtt.*;
import geso.traphaco.center.beans.huynhaphangtt.imp.*;
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

public class HuynhaphangttSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	PrintWriter out; 
	 
    public HuynhaphangttSvl() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String  userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String dhtvId = util.getId(querystring);
	    IHuynhaphangList   obj = new HuynhaphangList();
	    obj.setUserId(userId);
	    if (action.equals("delete")){	   		  	    	
	    	obj.HuyNhaphang(dhtvId);
	    }
	    
	    if (action.equals("chotdh")){
	    	obj.ChotNhaphang(dhtvId);
	    }
	   	    
		
	  
	    obj.init("");
	    
		session.setAttribute("obj", obj);
 
		String nextJSP = "/TraphacoHYERP/pages/Center/Huynhaphangtt.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
 
	    IHuynhaphangList   obj = new HuynhaphangList();
	    String search = getSearchQuery(request,obj);
	    obj.setUserId(userId);
	    obj.init(search);		
	    session.setAttribute("obj", obj);  	
    	session.setAttribute("userId", userId);
		if(action.equals("toExcel"))
		{
			ToExcel(response, search);
		}		
		response.sendRedirect("/TraphacoHYERP/pages/Center/Huynhaphangtt.jsp");	    	
	    
	}
	
	private String getSearchQuery(HttpServletRequest request,IHuynhaphangList   obj) 
	{
		Utility util = new Utility();
 
		  String userId = util.antiSQLInspection(request.getParameter("userId"));
		  
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
		String query = " select a.sochungtu, a.pk_seq  , a.ngaychot , a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " + 
		 " from huynhaphang a inner join nhanvien b on a.nguoitao = b.pk_seq " +
		 "  inner join nhanvien c on a.nguoisua = c.pk_seq " +
		 " inner join nhaphanphoi f on a.npp_fk = f.pk_seq "+
		 " where a.npp_fk in "+util.quyen_npp(userId);
    	if (tungay.length()>0){
			query = query + " and a.ngaychot > '" + tungay + "'";
    	}
    	if (denngay.length()>0){
			query = query + " and a.ngaychot < '" + denngay + "'";
    	}
    	query = query + " order by a.trangthai, a.pk_seq";
    	return query;
	}
	
	private void ToExcel(HttpServletResponse response, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=HuyHoaDon.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("HuyHoaDon", k);
			sheet.addCell(new Label(0, 1, "Hủy Hóa Đơn : ", new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "SỐ CHỨNG TỪ", cellFormatSpecical));
			sheet.addCell(new Label(2, 4, "SỐ HÓA ĐƠN", cellFormat));
			sheet.addCell(new Label(3, 4, "NGÀY CHỐT", cellFormat));
			sheet.addCell(new Label(4, 4, "TRẠNG THÁI", cellFormat));
			sheet.addCell(new Label(5, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(6, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(7, 4, "NGƯỜI SỬA", cellFormat));
			sheet.addCell(new Label(8, 4, "NGÀY SỬA", cellFormat));
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
		
				cformat = cellFormat2 ;
				stt++;
				number = new Number(0, j, stt, cformat);sheet.addCell(number);
				label = new Label(1, j, rs.getString("pk_seq"), cformat);sheet.addCell(label);
				label = new Label(2, j, rs.getString("sochungtu"), cformat);sheet.addCell(label);				
				label = new Label(3, j, rs.getString("ngaychot"), cformat);sheet.addCell(label);
				label = new Label(4, j, rs.getInt("trangthai") == 0 ? "Chờ duyệt" :rs.getInt("trangthai") == 1?"Đã duyệt":"Đã hủy", cformat);sheet.addCell(label);
				label = new Label(5, j, rs.getString("NgayTao"), cformat);sheet.addCell(label);
				label = new Label(6, j, rs.getString("nguoitao"), cformat);sheet.addCell(label);
				label = new Label(7, j, rs.getString("ngaysua"), cformat);sheet.addCell(label);
				label = new Label(8, j, rs.getString("nguoisua"), cformat);sheet.addCell(label);

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

