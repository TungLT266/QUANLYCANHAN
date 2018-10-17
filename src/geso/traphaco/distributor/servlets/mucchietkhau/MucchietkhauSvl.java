package geso.traphaco.distributor.servlets.mucchietkhau;

import geso.traphaco.center.beans.nhomkhachhang.INhomkhachhangList;
import geso.traphaco.distributor.beans.mucchietkhau.IMucchietkhau;
import geso.traphaco.distributor.beans.mucchietkhau.IMucchietkhauList;
import geso.traphaco.distributor.beans.mucchietkhau.imp.Mucchietkhau;
import geso.traphaco.distributor.beans.mucchietkhau.imp.MucchietkhauList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class MucchietkhauSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public MucchietkhauSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		IMucchietkhauList obj;
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
	    	    
  
	    obj = new MucchietkhauList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String mckId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(mckId, obj);
	    	out.print(mckId);
	    }
	   	    
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/MucChietKhau.jsp";
		response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		IMucchietkhauList obj;
		 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	  
	    Utility util = new Utility();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	  
	          
	    if (action.equals("new"))
	    {
	    	// Empty Bean for distributor
	    	IMucchietkhau mckBean = (IMucchietkhau) new Mucchietkhau("");
	    	mckBean.setUserId(userId);
	    	mckBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("mckBean", mckBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/MucChietKhauNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    if (action.equals("search"))	    
	    {
	    	obj = new MucchietkhauList();
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/MucChietKhau.jsp");	    		    	
	    }
	    if (action.equals("excel"))
	  		{
	    		obj = new MucchietkhauList();	
	  			ToExcel(response, obj,  getSearchQuery(request,obj));
	  		}
	  	    
	    
		}
	}
	
	private String getSearchQuery(HttpServletRequest request,IMucchietkhauList obj) 
	{
		Utility util = new Utility();
		HttpSession session = request.getSession();		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String chietkhau = util.antiSQLInspection(request.getParameter("chietkhau"));
    	if ( chietkhau == null)
    		chietkhau = "";
    	obj.setMucchietkhau(chietkhau);
    	  	
    	String khTen = util.antiSQLInspection(request.getParameter("khTen"));
    	if ( khTen == null)
    		khTen = "";
    	obj.setKhachhang(khTen);
    	    	
    	String query = "select * from vwMucchietkhau where nppId ='" + nppId + "'";
    		
    	if (chietkhau.length() > 0)
    	{
			query = query + " and  chietkhau=  '" + chietkhau + "'";			
    	}
    	
    	if (khTen.length() > 0)
    	{
     		query = query + " and mckId in (select chietkhau_fk from khachhang where  upper(dbo.ftBoDau(ten)) like  upper(N'%" + util.replaceAEIOU(khTen) + "%'))";
    	}
    	  	
    	query = query + " order by chietkhau";
    	System.out.println("");
    	System.out.println("chiet khau:"+ query);
    	
    	return query;
	}

	private void Delete(String mckId, 	IMucchietkhauList obj) 
	{    
		dbutils db = new dbutils(); 
	    String query = "Select count(pk_seq) as num from KhachHang where chietkhau_fk='" + mckId + "'";
	    ResultSet kh = db.get(query);
	    try {
	    	kh.next();
			if(kh.getString("num").equals("0"))
			{
				db.update("delete from mucchietkhau where pk_seq='" + mckId + "'");
			}
			else
			{
				 System.out.println("===Vuna======");
		         System.out.println("===Vuna===query=else==" + query);
		         obj.setMsg("Muc chiet khau nay da duoc giao cho khach hang roi, khong the xoa duoc");
			}
			
			 System.out.println("===Vuna======");
	         System.out.println("===Vuna===query===" + query);
		} catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	              
         
				
		/*dbutils db = new dbutils(); 
		String query = "Select pk_seq from KhachHang where chietkhau_fk='" + mckId + "'";
		
		ResultSet kh = db.get(query);
		if(kh != null)
		{
			try {
				while(kh.next())
				{
					String khId = kh.getString("pk_seq");
					String sql = "update Khachhang set chietkhau_fk = '0' where pk_seq = '" + khId + "'";
					db.update(sql);
				}
			} catch(Exception e) {}
		}
		db.update("delete from mucchietkhau where pk_seq='" + mckId + "'");
		db.shutDown();
		*/
	}
	private void ToExcel(HttpServletResponse response, IMucchietkhauList obj, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=MucChietKhau.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("MucChietKhau", k);
			sheet.addCell(new Label(0, 1, "Mức chiết khấu : ", new WritableCellFormat(cellTitle)));

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
