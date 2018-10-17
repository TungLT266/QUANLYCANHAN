package geso.traphaco.center.servlets.nhomsanpham;

import geso.traphaco.center.beans.nhomsanpham.INhomsanpham;
import geso.traphaco.center.beans.nhomsanpham.imp.Nhomsanpham;
import geso.traphaco.center.beans.nhomsanpham.INhomsanphamList;
import geso.traphaco.center.beans.nhomsanpham.imp.NhomsanphamList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
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

public class NhomsanphamSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   PrintWriter out;
   
	public NhomsanphamSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		
		//HttpServletRequest request;
		 //  HttpServletResponse response;
		   INhomsanphamList obj;
		   //dbutils db;
		
	    
	    //db = new dbutils();
	    
	    response.setContentType("text/html");
	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    obj = new NhomsanphamList();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String nspId = util.getId(querystring);
	    
	    if (action.equals("delete")){	
	    	
	    	Delete(nspId);
	    	

	    }

	    // String query = "select a.nsp_parent_fk as parent, a.pk_seq, a.ten, a.diengiai, a.loaithanhvien, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomsanpham a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ order by nsp_parent_fk ";

	    List<INhomsanpham> nsplist = new ArrayList<INhomsanpham>(); 
	    
	    getNspBeanList("0", nsplist);
	    
		// Save data into session
	    obj.setNspList(nsplist);
	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/TraphacoHYERP/pages/Center/NhomSanPham.jsp";
		response.sendRedirect(nextJSP);
	    
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		
		//HttpServletRequest request;
		 //  HttpServletResponse response;
		   INhomsanphamList obj;
		   //dbutils db;
		   
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    //PrintWriter out = response.getWriter();
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId"));

	    obj = new NhomsanphamList();
	    //db = new dbutils();
	    

	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    //out.println(action); 
	    
	    // Perform searching. Each Nhomsanpham is saved into Nhomsanpham
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	INhomsanpham nspBean = (INhomsanpham) new Nhomsanpham();
	    	
	    	nspBean.UpdateRS();
	    	// Save Data into session
    		session.setAttribute("nspBean", nspBean);
    		session.setAttribute("userId", userId);
    		

    		String nextJSP = "/TraphacoHYERP/pages/Center/NhomSanPhamNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else if (action.equals("search")){
	    	
		    	String search = getSearchQuery(request,obj);
//		    	out.println(search);
		    	
		    	List<INhomsanpham> nsplist = getNspListS(search);	    	

	    		// Saving data into session
			    obj.setNspList(nsplist);
			    obj.setSearch(true);
				session.setAttribute("obj", obj);

	    		session.setAttribute("userId", userId);
		    		
	    		response.sendRedirect("/TraphacoHYERP/pages/Center/NhomSanPham.jsp");
		}
	    else if (action.equals("excel")) {
	    	String search = getSearchQuery(request,obj);
	    	List<INhomsanpham> nsplist = getNspListS(search);
	    	obj.setNspList(nsplist);
		    obj.setSearch(true);
		    
		    ToExcel(response, obj);
	    }
	    else if (action.equals("1"))
	    {
	    	List<INhomsanpham> nsplist = new ArrayList<INhomsanpham>(); 
	    
	    	getNspBeanList("0", nsplist);
	    
		// 	Save data into session
	    	obj.setNspList(nsplist);
	    
	    	session.setAttribute("obj", obj);
	    	session.setAttribute("userId", userId);

	    	String nextJSP = "/TraphacoHYERP/pages/Center/NhomSanPham.jsp";
	    	response.sendRedirect(nextJSP);
	    }
	}

	private void Delete(String nspId){
		
		//HttpServletRequest request;
		 //  HttpServletResponse response;
		   INhomsanphamList obj;
		   dbutils db = new dbutils();
	    
	    String query;
	    String command;
	    query = "select loaithanhvien from nhomsanpham where pk_seq ='" + nspId + "'";
	    out.print(query);
		ResultSet rs = db.get(query);
		String ltv = "";
		try{
			rs.next();
			ltv = rs.getString("loaithanhvien");
			rs.close();	
		}catch(Exception e){
			out.println(e.toString());
			
		}
			
		
		if(ltv.equals("1"))
		{
			query = "select pk_seq from nhomsanpham where nsp_parent_fk ='" + nspId + "'";
			rs = db.get(query);
			try{
				while(rs.next()){
					command = "update nhomsanpham set nsp_parent_fk = 0 where pk_seq = '" + rs.getString("pk_seq")+ "'";
					db.update(command);
				}
				rs.close();
			}catch(Exception e){}
			
			command = "delete from nhomsanpham where pk_seq ='" + nspId + "'";
		   	db.update(command);
		   	

		}
		else
		{
			command = "delete from nhomsanpham_sanpham where nsp_fk ='" + nspId + "'";
			db.update(command);
			
		}
	}

	
	private void  getNspBeanList(String parent, List<INhomsanpham> nsplist)
	{	
		dbutils db = new dbutils();

		String query = "select a.nsp_parent_fk as parent, a.loainhom, a.pk_seq, a.ten, a.diengiai, a.loaithanhvien, a.trangthai," +
				" a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomsanpham a, nhanvien b," +
				" nhanvien c where a.nguoitao = b.PK_SEQ and " +
				" a.nguoisua = c.PK_SEQ and a.nsp_parent_fk = '" + parent + "' and a.type='0' ";

		System.out.println("1.Khoi tao NSP: " + query);
		ResultSet rs = db.get(query);
		try
		{	
			String[] param = new String[11];
			INhomsanpham nspBean;
			while (rs.next())
			{	    			
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("ten");	
				param[2]= rs.getString("diengiai");
				param[3]= rs.getString("loaithanhvien");
				param[4]= rs.getString("trangthai");
				param[5]= rs.getString("ngaytao");
				param[6]= rs.getString("ngaysua");
				param[7]= rs.getString("nguoitao");
				param[8]= rs.getString("nguoisua");			
				param[9]= rs.getString("parent");
				param[10] = rs.getString("loainhom");
				nspBean = new Nhomsanpham(param);					
				nsplist.add(nspBean);
				getNspBeanList(param[0], nsplist);
			}    		
			if(rs!=null){
				rs.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private List<INhomsanpham> getNspListS(String query){  	
		
		//HttpServletRequest request;
		 //  HttpServletResponse response;
		   INhomsanphamList obj;
		   dbutils db = new dbutils();
		
		    
		ResultSet rs = db.get(query);
		List<INhomsanpham> nsplist = new ArrayList<INhomsanpham>();			
						
		INhomsanpham nspBean;
		String[] param = new String[11];
		try{
			while(rs.next()){
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("ten");	
				param[2]= rs.getString("diengiai");
				param[3]= rs.getString("loaithanhvien");
				param[4]= rs.getString("trangthai");
				param[5]= rs.getString("ngaytao");
				param[6]= rs.getString("ngaysua");
				param[7]= rs.getString("nguoitao");
				param[8]= rs.getString("nguoisua");			
				param[9]= rs.getString("parent");
				param[10]= rs.getString("loainhom");
				nspBean = new Nhomsanpham(param);
				nsplist.add(nspBean);			
			}
			if(rs!=null){
    			rs.close();
    		}
		}catch(Exception e){}
		return nsplist;
	}

	private String getSearchQuery(ServletRequest request,INhomsanphamList obj){
		
		//HttpServletRequest request;
		 //  HttpServletResponse response;
/*		   INhomsanphamList obj = new NhomsanphamList();*/
		   dbutils db = new dbutils();
		
//	    PrintWriter out = response.getWriter();
		Utility util = new Utility();
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
    	if (diengiai == null)
    		diengiai = "";
    	obj.setDiengiai(diengiai);
    	String thanhvien = util.antiSQLInspection(request.getParameter("thanhvien"));
    	if (thanhvien == null)
    		thanhvien = "";    	
    	obj.setLoaithanhvien(thanhvien);
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
    	
    	String lnhom = util.antiSQLInspection(request.getParameter("lnhom"));   	
    	if (lnhom == null)
    		lnhom = "";
    	obj.setLoainhom(lnhom);
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	String query = "select a.nsp_parent_fk as parent,a.loainhom,  a.pk_seq, a.ten, a.diengiai, a.loaithanhvien, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomsanpham a, nhanvien b, nhanvien c where a.type='0' and  a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
    	
    	if (diengiai.length()>0){
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper('%" + new geso.traphaco.distributor.util.Utility().replaceAEIOU(diengiai) + "%')";
			//obj.setDiengiai(diengiai);
    	}
    	
    	if (thanhvien.length()>0){
			query = query + " and a.loaithanhvien ='" + thanhvien + "'";
			//obj.setLoaithanhvien(thanhvien);
    	}

    	if (tungay.length() > 0) {
    		query = query + " and a.ngaytao >= '" + tungay + "'";
    		//obj.setTungay(tungay);
    	}
    	
    	if (denngay.length() > 0) {
    		query = query + " and a.ngaytao <= '" + denngay + "'";
    		//obj.setDenngay(denngay);
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		//obj.setTrangthai(trangthai);
    	}
    	
    	if(lnhom.length() > 0){
    		query = query + " and a.loainhom = '"+ lnhom +"'";
    	}
    	query = query + "  order by nsp_parent_fk ";
    	return query;
	}


	private void ToExcel(HttpServletResponse response, INhomsanphamList obj) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=NhomSanPham.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("NhomSanPham", k);
			sheet.addCell(new Label(0, 1, "NHÓM SẢN PHẨM: ", new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "TÊN NHÓM", cellFormat));
			sheet.addCell(new Label(2, 4, "DIỄN GIẢI", cellFormatSpecical));
			sheet.addCell(new Label(3, 4, "LOẠI THÀNH VIÊN", cellFormatSpecical));
			sheet.addCell(new Label(4, 4, "TRẠNG THÁI", cellFormat));
			sheet.addCell(new Label(5, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(6, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(7, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(8, 4, "NGƯỜI SỬA", cellFormat));

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
			List<INhomsanpham> nsplist = (List<INhomsanpham>)obj.getNspList();
			INhomsanpham nsp = null;
			
			for(int i = 0; i < nsplist.size(); i++) {
				nsp = nsplist.get(i);
				stt++;
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;

				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, nsp.getTen(), cformat);
				sheet.addCell(label);
				label = new Label(2, j, nsp.getDiengiai(), cformat);
				sheet.addCell(label);
				label = new Label(3, j, nsp.getThanhvien().equals("2") ? "Sản phẩm" : "Nhóm sản phẩm", cformat);
				sheet.addCell(label);
				label = new Label(4, j, nsp.getTrangthai().equals("1") ? "Hoạt động" : "Ngưng hoạt động", cformat);
				sheet.addCell(label);
				label = new Label(5, j, nsp.getNgaytao(), cformat);
				sheet.addCell(label);
				label = new Label(6, j, nsp.getNguoitao(), cformat);
				sheet.addCell(label);
				label = new Label(7, j, nsp.getNgaysua(), cformat);
				sheet.addCell(label);
				label = new Label(9, j, nsp.getNguoisua(), cformat);
				sheet.addCell(label);

				j++;
			}
			w.write();
			w.close();
			
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