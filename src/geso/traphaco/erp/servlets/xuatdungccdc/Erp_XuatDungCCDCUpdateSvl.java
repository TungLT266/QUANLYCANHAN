package geso.traphaco.erp.servlets.xuatdungccdc;

//import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.xuatdungccdc.Erp_VatTu;
import geso.traphaco.erp.beans.xuatdungccdc.Erp_XuatDungCCDC;
import geso.traphaco.erp.beans.xuatdungccdc.Erp_XuatDungCCDCList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_XuatDungCCDCUpdateSvl extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	   
	   public Erp_XuatDungCCDCUpdateSvl() {
			super();
		}   
	   
	   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	   {
		   System.out.println("do get update");
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    	    
		    HttpSession session = request.getSession();	    
	      
		    Utility util = new Utility();
		    out = response.getWriter();
		    
		    Erp_XuatDungCCDC obj = new Erp_XuatDungCCDC();
		    String ctyId = (String)session.getAttribute("congtyId");
		    obj.setCongTyId(ctyId);
		    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    
		    String action = util.getAction(querystring);
		    System.out.println("\n **************action:" + action);
		    
		    String id = util.getId(querystring);

		    obj.setId(id);
		   	
//	    	obj.setUserId(userId);
//	    	settingPage(obj);
			obj.init();
			
			session.setAttribute("action", action);
	    	session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);
	    		
			response.sendRedirect("/TraphacoHYERP/pages/Erp/XuatDungCCDCUpdate.jsp");
		}  	
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
//		    OutputStream out = response.getOutputStream();
		    
			HttpSession session = request.getSession();
		    String userId = request.getParameter("userId");
		    String id = request.getParameter("id");
		    
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = "";
		    }
		        
		    Erp_XuatDungCCDC obj = new Erp_XuatDungCCDC();
		    obj.setId(id);
		    obj.setNguoiSua(userId);
		    String ctyId = (String)session.getAttribute("congtyId");
//		    String ctyTen = (String)session.getAttribute("congtyTen");
		    obj.setCongTyId(ctyId);
		    obj.setNguoiTao(userId);
		    
		    
		    obj.init();
		    
		    String ccdcId = request.getParameter("ccdcId");
		    obj.setCcdcId(ccdcId);
		    
		    String xoaKhauHao = request.getParameter("xoaKhauHao");
		    System.out.println("xoaKhauHao: " + xoaKhauHao);
		    obj.setXoaKhauHao(Boolean.parseBoolean(xoaKhauHao));
		    System.out.println("xoaKhauHao: " + obj.getXoaKhauHao());
		    String ghiChu = request.getParameter("ghiChu");
		    obj.setGhiChu(ghiChu);
		    
		    String ngayXuatDung = request.getParameter("ngayXuatDung");
		    System.out.println("ngayXuatDung: " + ngayXuatDung);
		    obj.setNgayXuatDung(ngayXuatDung);
		    
		    String[] sanPhamIdArr = request.getParameterValues("sanPhamId");
		    String[] maSanPhamArr = request.getParameterValues("maSanPham");
		    String[] tenVatTuArr = request.getParameterValues("tenVatTu");
		    String[] donViTinhArr = request.getParameterValues("donViTinh");
		    String[] soLuongArr = request.getParameterValues("soLuong");
//		    String[] donGiaArr = request.getParameterValues("donGia");
//		    String[] thanhTienArr = request.getParameterValues("thanhTien");
		    String[] khoIdArr = request.getParameterValues("khoId");
		    String[] tonHienTaiArr = request.getParameterValues("tonHienTai");
		    
		    List<Erp_VatTu> vatTuList = obj.getVatTuList();
		    vatTuList.clear();
		    System.out.println("sanPhamIdArr.length: " + sanPhamIdArr.length);
		    boolean isCon = true;
		    for (int i = 0; isCon == true && i < sanPhamIdArr.length; i++)
		    {
		    	if (soLuongArr[i].trim().length() == 0)
		    		soLuongArr[i] = "0";
//		    	if (donGiaArr[i].trim().length() == 0)
//		    		donGiaArr[i] = "0";
//		    	if (thanhTienArr[i].trim().length() == 0)
//		    		thanhTienArr[i] = "0";
//		    	String donGia = donGiaArr[i].replace(",", "");
//		    	String thanhTien = thanhTienArr[i].replace(",", "");
		    	if (maSanPhamArr[i].trim().length() > 0 || khoIdArr[i].trim().length() > 0)
		    	{
//		    		Erp_VatTu vatTu = new Erp_VatTu(sanPhamIdArr[i], khoIdArr[i], maSanPhamArr[i], tenVatTuArr[i]
//                                  , (donViTinhArr[i]), Float.parseFloat(soLuongArr[i].replace(",", "")), Float.parseFloat(donGia), Float.parseFloat(thanhTien));
		    		Erp_VatTu vatTu = new Erp_VatTu(sanPhamIdArr[i], khoIdArr[i], maSanPhamArr[i], tenVatTuArr[i]
                   , (donViTinhArr[i]), Float.parseFloat(soLuongArr[i].replace(",", "")), 0, 0, Float.parseFloat(tonHienTaiArr[i].replace(",", "")));
		    		vatTuList.add(vatTu);
		    	}
		    	else
		    		isCon = false;
		    }
		    
		    System.out.println("action: " + action);
		    if (action.equals("new"))
		    {
		    	if (!obj.newXuatDung())
		    	{
		    		session.setAttribute("userId", userId);
			    	session.setAttribute("obj", obj);
			    	
			    	String nextJSP = "/TraphacoHYERP/pages/Erp/XuatDungCCDCUpdate.jsp";
		    		response.sendRedirect(nextJSP);
		    		return;
		    	}
		    	else
		    	{
		    		obj.DbClose();
		    		
		    		Erp_XuatDungCCDCList ob = new Erp_XuatDungCCDCList();
		    		ob.init();
		    		
		    		session.setAttribute("userId", userId);
			    	session.setAttribute("obj", ob);
			    	
			    	String nextJSP = "/TraphacoHYERP/pages/Erp/XuatDungCCDC.jsp";
		    		response.sendRedirect(nextJSP);
		    		return;
		    	}
		    }else
		    	if (action.equals("update"))
		    	{
		    		if (!obj.editXuatDung())
			    	{
			    		session.setAttribute("userId", userId);
				    	session.setAttribute("obj", obj);
				    	
				    	String nextJSP = "/TraphacoHYERP/pages/Erp/XuatDungCCDCUpdate.jsp";
			    		response.sendRedirect(nextJSP);
			    		return;
			    	}
			    	else
			    	{
			    		obj.DbClose();
			    		
			    		Erp_XuatDungCCDCList ob = new Erp_XuatDungCCDCList();
			    		ob.init();
			    		
			    		session.setAttribute("userId", userId);
				    	session.setAttribute("obj", ob);
				    	
				    	String nextJSP = "/TraphacoHYERP/pages/Erp/XuatDungCCDC.jsp";
			    		response.sendRedirect(nextJSP);
			    		return;
			    	}
		    	}
		    
		    //Thay đổi kho: nếu là xuất dùng CCDC (mã CCDC trùng với 1 mã sản phẩm trong kho) => bê nguyên cái mã CCDC ra
		    if (action.trim().contains("changeKho_"))
		    {
		    	String[] arr = action.split("_");
		    	if (arr.length > 1)
		    	{
		    		String khoIndex = arr[1];
		    		obj.changeKho(khoIndex);
		    	}
		    		
		    }
	    	session.setAttribute("userId", userId);
	    	session.setAttribute("obj", obj);
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/XuatDungCCDCUpdate.jsp";
    		response.sendRedirect(nextJSP);
    		return;
		}   
			
		boolean kiemtra(dbutils db, String sql,String id)
		{
			
			String query = "select count(*) as num from " + sql + " where sanpham_fk ='"+ id +"'";
		
	    	ResultSet rs = db.get(query);
			try 
			{	
				//kiem tra ben san pham
				while(rs.next())
				{ 
					if(rs.getString("num").equals("0"))
						return false;
				}
			} 
			catch (SQLException e) {}
			return true;
		}
		
//		private void CreateStaticData(Workbook workbook, String query,String userter) 
//		{
//			Worksheets worksheets = workbook.getWorksheets();
//		    Worksheet worksheet = worksheets.getSheet(0);
//	 
//			dbutils db = new dbutils();
//			ResultSet rs = db.get(query);
//			ReportAPI.TaoBaoCao(rs, worksheet, "DỮ LIỆU NỀN KHÁCH HÀNG",null ,userter);
//		 
//		}
	}