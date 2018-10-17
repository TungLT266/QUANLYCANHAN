package geso.traphaco.distributor.servlets.dangkytrungbay;

import geso.traphaco.distributor.beans.dangkytrungbay.IDangkytrungbay;
import geso.traphaco.distributor.beans.dangkytrungbay.IDangkytrungbayList;
import geso.traphaco.distributor.beans.dangkytrungbay.imp.Dangkytrungbay;
import geso.traphaco.distributor.beans.dangkytrungbay.imp.DangkytrungbayList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

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

public class DangkytrungbaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    
    public DangkytrungbaySvl() 
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
		
		IDangkytrungbayList obj;
	 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	   
	    	    
	    
	    Utility util = new Utility();
	   
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	   
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	
	    
	    String dktbId = util.getId(querystring);

	    if(action.equals("delete"))
	    {	   		  	    	
	    	Delete(dktbId);
	
	    }
	    else if(action.equals("chotphieu"))
	    {
	    	Chotphieu(dktbId);
	
	    }else if(action.equals("excel"))
	    {
	    	ToExcel(response, dktbId);
	    }
	   	    
	    obj = new DangkytrungbayList();
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyTrungBay.jsp";
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
		IDangkytrungbayList obj;
		PrintWriter out; 
		
		obj = new DangkytrungbayList();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    
		Utility util = new Utility();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("Tao moi"))
	    {
	    	// Empty Bean for distributor
	    	IDangkytrungbay dktbBean = (IDangkytrungbay) new Dangkytrungbay("");
	    	dktbBean.setUserId(userId);
	    	dktbBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("dktbBean", dktbBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyTrungBayNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }else
	    {
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/DangKyTrungBay.jsp");	    		    	
	    }
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IDangkytrungbayList obj) 
	{	
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String sophieu = util.antiSQLInspection(request.getParameter("sophieu"));
    	if ( sophieu== null)
    		sophieu = "";
    	obj.setScheme(sophieu);
    	
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if ( tungay== null)
    		tungay = "";
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if ( denngay == null)
    		denngay = "";
    	obj.setDenngay(denngay);
    	
    	String query = "select a.pk_seq as dktbId, b.pk_seq as cttbId, b.diengiai as cttbTen, a.ngaydangky, a.ngaysua, a.trangthai, c.ten as nguoitao, d.ten as nguoisua ";
		query = query + "from dangkytrungbay a inner join TIEUCHITHUONGTL b on a.cttrungbay_fk = b.pk_seq inner join nhanvien c on a.nguoitao = c.pk_seq inner join nhanvien d on a.nguoisua = d.pk_seq ";
		query = query + "where a.npp_fk = '" + nppId + "'";
		
		if(sophieu.length() > 0)
			query =  query + " and a.pk_seq like '%" + sophieu + "%'";
		if(tungay.length() > 0)
			query = query + " and a.ngaydangky >= '" + convertDate(tungay) + "'";
		if(denngay.length() > 0)
			query = query + " and a.ngaydangky <= '" + convertDate(denngay) + "'";
    	
    	return query + " order by a.ngaydangky DESC";
	}

	private String convertDate(String date) 
	{		
		//chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if(!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}
	
	private void Delete(String dktbId) 
	{
		dbutils db = new dbutils(); 
		try 
		{
			db.getConnection().setAutoCommit(false);
			String sql="Delete dktrungbay_khachhang where dktrungbay_fk='" + dktbId + "'";
			if( !db.update(sql))
			{
				System.out.println("1.Khong the xoa dktb: " + sql);
				db.update("rollback");
				return ;
			}
			sql="Delete dangkytrungbay where pk_seq='" + dktbId + "'";
			if( !db.update(sql))
			{
				System.out.println("1.Khong the xoa dktb: " + sql);
				db.update("rollback");
				return ;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} 
		catch(Exception e)
		{
			System.out.println("115.Khong the xoa dktb: " + e.getMessage());
			db.update("rollback");	
		}		
	}
	private void Chotphieu(String dktbId) 
	{
		dbutils db = new dbutils(); 
		try{
		
		db.getConnection().setAutoCommit(false);
		db.update("update dangkytrungbay set trangthai='1' where pk_seq = '" + dktbId + "'");
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		
		db.shutDown();
		}catch(Exception er){
			db.update("rollback");
			
		}
		
	}
	private void ToExcel(HttpServletResponse response, String id) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=DangKyTrungBay.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("DangKyTrungBay", k);
			sheet.addCell(new Label(0, 1, "ĐĂNG KÝ TRƯNG BÀY: ", new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "MÃ NHÀ PHÂN PHỐI", cellFormat));
			sheet.addCell(new Label(2, 4, "TÊN NHÀ PHÂN PHỐI", cellFormat));
			sheet.addCell(new Label(3, 4, "Mã KHÁCH HÀNG", cellFormat));
			sheet.addCell(new Label(4, 4, "TÊN KHÁCH HÀNG", cellFormatSpecical));
			sheet.addCell(new Label(5, 4, "ĐỊA CHỈ", cellFormat));
			sheet.addCell(new Label(6, 4, "ĐIỆN THOẠI", cellFormat));
			sheet.addCell(new Label(7, 4, "SỐ XUẤT MUA", cellFormat));
			sheet.addCell(new Label(8, 4, "SỐ XUẤT ĐĂNG KÝ", cellFormat));

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

			String query=
				"	SELECT A.PK_SEQ,D.MA AS NPPMA,D.TEN AS NPPTEN,C.SMARTID,C.TEN AS KHTEN,C.DIENTHOAI,C.DIACHI,B.DANGKY,B.Dat "+
				"	FROM DANGKYTRUNGBAY A "+
				"		INNER JOIN DKTRUNGBAY_KHACHHANG B ON B.DKTRUNGBAY_FK=A.PK_SEQ "+
				"		INNER JOIN KHACHHANG C ON C.PK_SEQ=B.KHACHHANG_FK "+
				"		INNER JOIN NHAPHANPHOI D ON D.PK_SEQ=A.NPP_FK "+
				"	WHERE A.PK_SEQ='"+id+"' ";
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
				stt++;
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;
				number = new Number(0, j,stt, cformat);sheet.addCell(number);
				label = new Label(1, j, rs.getString("nppMa"), cformat);sheet.addCell(label);
				label = new Label(2, j, rs.getString("nppTen"), cformat);sheet.addCell(label);
				label = new Label(3, j, rs.getString("smartId"), cformat);sheet.addCell(label);
				label = new Label(4, j, rs.getString("khTen"), cformat);sheet.addCell(label);
				label = new Label(5, j, rs.getString("dienthoai"), cformat);sheet.addCell(label);
				label = new Label(6, j, rs.getString("diachi"), cformat);sheet.addCell(label);
				number = new Number(7, j, rs.getDouble("dat") , cformat);sheet.addCell(number);
				number = new Number(8, j, rs.getDouble("dangky") , cformat);sheet.addCell(number);
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
