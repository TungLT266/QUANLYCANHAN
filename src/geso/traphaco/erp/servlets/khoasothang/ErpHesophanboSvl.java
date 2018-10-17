package geso.traphaco.erp.servlets.khoasothang;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.khoasothang.*;
import geso.traphaco.erp.beans.khoasothang.imp.*;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import net.sf.jasperreports.data.cache.NumberValuesUtils;

import com.oreilly.servlet.MultipartRequest;

public class ErpHesophanboSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\upload\\excel\\";
    
    public ErpHesophanboSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHesophanbo obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	   
	    obj = new ErpHesophanbo();
	    obj.setUserId(userId);
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHeSoPhanBo.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHesophanbo obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
		HttpSession session = request.getSession();

	    obj = new ErpHesophanbo();
	    obj.setUserId(session.getAttribute("userId").toString());
	    obj.setCongtyId( session.getAttribute("congtyId").toString() );
	    obj.setNppId( session.getAttribute("nppId").toString() );
	    
	    String contentType = request.getContentType();
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			String thang = multi.getParameter("thang");
			obj.setThang(thang);
			
			String nam = multi.getParameter("nam");
			obj.setNam(nam);

			Enumeration files = multi.getFileNames();
			String filename = "";

			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			if (filename != null && filename.length() > 0)
				obj.setMsg(this.readExcel(UPLOAD_DIRECTORY + filename, obj, session.getAttribute("userId").toString() ));

			obj.createRs();
			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHeSoPhanBo.jsp";
			response.sendRedirect(nextJSP);
			
			return;
		} 
	    
	    //obj.createRs();
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHeSoPhanBo.jsp";
		response.sendRedirect(nextJSP);
	}

	private String readExcel(String fileName, IErpHesophanbo obj, String userId) 
	{
		dbutils db = new dbutils();
		try
		{
			File inputWorkbook = new File(fileName);
			jxl.Workbook w = null;
			w = jxl.Workbook.getWorkbook(inputWorkbook);
			jxl.Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();

			db.getConnection().setAutoCommit(false);
			
			String sql = "delete ERP_GIATHANH_DONGIALUONG_TEMP where congty_fk = '" + obj.getCongtyId() + "' and thang = '" + obj.getThang() + "' and nam = '" + obj.getNam() + "'  ";
			System.out.println("::: DELETE HS: " + sql);
			if(!db.update(sql))
			{
				db.getConnection().rollback();
				return "1.Lỗi import: " + sql;
			}
			
			System.out.println("::: DONG: " + sodong + " -- COT: " + sheet.getColumns() );
			for (int i = 3; i < sodong; i++)
			{
				String maphanxuong = getValue(sheet, 0, i).trim();
				String masanpham = getValue(sheet, 2, i).trim();
				String dongia = getValue(sheet, 5, i).trim().replaceAll(",", "");
				if( dongia.trim().length() <= 0 )
					dongia = "0";
			
				if( masanpham.trim().length() > 0 && maphanxuong.trim().length() > 0 )
				{
					sql = " insert ERP_GIATHANH_DONGIALUONG_TEMP( congty_fk, thang, nam, manhamay, masanpham, dongiaLuong ) " + 
						  " select '" + obj.getCongtyId() + "', '" + obj.getThang() + "', '" + obj.getNam() + "', N'" + maphanxuong + "', N'" + masanpham + "', '" + dongia + "' ";
					
					System.out.println(":: CHEN HE SO TEMP: " + sql);
					db.update(sql);
				}
				
			}
			
			//Giữ lại LOG đã chèn
			db.getConnection().commit();
			db.getConnection().setAutoCommit(false);
			
			sql = "delete ERP_GIATHANH_DONGIALUONG where congty_fk = '" + obj.getCongtyId() + "' and thang = '" + obj.getThang() + "' and nam = '" + obj.getNam() + "' ";
			if( !db.update(sql) )
			{
				db.getConnection().rollback();
				return "Lỗi upload: " + sql;
			}
			
			sql = 	"insert ERP_GIATHANH_DONGIALUONG( congty_fk, thang, nam, nhamay_fk, sanpham_fk, dongiaLuong ) " +
					" select congty_fk, thang, nam, "+
					" 		( select pk_seq from ERP_NHAMAY where  rtrim(ltrim(manhamay)) =  rtrim(ltrim(a.manhamay)) ), "+
					" 		( select pk_seq from ERP_SANPHAM where  rtrim(ltrim(ma)) =  rtrim(ltrim(a.masanpham )) ), dongiaLuong "+
					" from ERP_GIATHANH_DONGIALUONG_TEMP a "+
					" where congty_fk = '" + obj.getCongtyId() + "' and thang = '" + obj.getThang() + "' and nam = '" + obj.getNam() + "'  ";	
			
			
			
			System.out.println("::: CHEN HE SO PHAN BO: " + sql);
			if( !db.update(sql) )
			{
				db.getConnection().rollback();
				return "Lỗi upload: " + sql;
			}
			
			String query = "select congty_fk, thang, nam, nhamay_fk, sanpham_fk, isnull(dongiaLuong,'0') as dongialuong from  ERP_GIATHANH_DONGIALUONG \n" +
					" where congty_fk = '" + obj.getCongtyId() + "' and thang = '" + obj.getThang() + "' and nam = '" + obj.getNam() + "'  ";
			System.out.println("query check :" +query );
			ResultSet rsCheck = db.get(query);
			if (rsCheck != null){
				while (rsCheck.next()){
					if (rsCheck.getString("sanpham_fk") == null){
						db.getConnection().rollback();
						rsCheck.close();
						return "Lỗi upload . Mã sản phẩm không có trong hệ thống";
					}
					if (rsCheck.getString("nhamay_fk") == null){
						db.getConnection().rollback();
						rsCheck.close();
						return "Lỗi upload . Mã nhà máy không có trong hệ thống";
					}
					if ( Double.parseDouble(rsCheck.getString("dongiaLuong")) <= 0){
						db.getConnection().rollback();
						rsCheck.close();
						return "Lỗi upload . Đơn giá lương phải lớn hơn 0";
					}
				}
				rsCheck.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			return "Vui lòng coi lại file " + e.getMessage();
		}
		
		db.shutDown();
		return "Import thành công";
	
	}
	

	private String getValue(Sheet sheet, int column, int row)
	{
		Cell cell;
		cell = sheet.getCell(column, row);
		try
		{
			return cell.getContents();
		} catch (Exception er)
		{
			return "0";
		}
	}
	
	

}
