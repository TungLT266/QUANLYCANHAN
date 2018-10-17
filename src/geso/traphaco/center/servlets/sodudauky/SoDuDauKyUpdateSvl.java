package geso.traphaco.center.servlets.sodudauky;

import geso.traphaco.center.beans.sodudauky.imp.SoDuDauKy;
import geso.traphaco.center.beans.sodudauky.imp.SoDuDauKyItem;
import geso.traphaco.center.beans.sodudauky.imp.SoDuDauKyList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

public class SoDuDauKyUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\";
	
	public SoDuDauKyUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
      
	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    SoDuDauKy obj = new SoDuDauKy();
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
	   	
		obj.init();
		
		session.setAttribute("action", action);
    	session.setAttribute("obj", obj);

		session.setAttribute("userId", userId);
    		
		response.sendRedirect("/TraphacoHYERP/pages/Center/SoDuDauKyUpdate.jsp");
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		HttpSession session = request.getSession();
	    String userId = request.getParameter("userId");
//	    String id = request.getParameter("id");
	    
	    String contentType = request.getContentType();
	    if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
	    	Utility util = new Utility();
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			session.setAttribute("userId", userId);
			String userTen = (String) session.getAttribute("userTen");
			session.setAttribute("userTen", userTen);
			SoDuDauKy obj = new SoDuDauKy();
			obj.setUserId(userId);
		    
			Enumeration<?> files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
			}
			
			if (filename != null && filename.length() > 0)
			{
				obj.readExcelFile(UPLOAD_DIRECTORY + filename);
			}
			session.setAttribute("obj", obj);			
			String nextJSP = "/TraphacoHYERP/pages/Center/SoDuDauKyUpdate.jsp";
			response.sendRedirect(nextJSP);
			return;
		}
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	        
	    SoDuDauKy obj = new SoDuDauKy();
	    obj.setUserId(userId);
	    String congTyId = (String)session.getAttribute("congtyId");
	    obj.setCongTyId(congTyId);

	    setParams(session, request, obj);
	    
	    obj.init();
	    System.out.println("action: " + action);
	    
	    if (action.equals("taoDoiTuong"))
	    {
	    	obj.taoMoiDoiTuong();
    		session.setAttribute("userId", userId);
	    	session.setAttribute("obj", obj);
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Center/SoDuDauKyUpdate.jsp";
    		response.sendRedirect(nextJSP);
    		return;
	    }
	    if (action.equals("ktdl"))
	    {
	    	obj.kiemTraDuLieu();
    		session.setAttribute("userId", userId);
	    	session.setAttribute("obj", obj);
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Center/SoDuDauKyUpdate.jsp";
    		response.sendRedirect(nextJSP);
    		return;
	    }
	    else if (action.equals("new") || action.equals("save"))
	    {
	    	if (!obj.create())
	    	{
	    		session.setAttribute("userId", userId);
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Center/SoDuDauKyUpdate.jsp";
	    		response.sendRedirect(nextJSP);
	    		return;
	    	}
	    	else
	    	{		    		
	    		SoDuDauKyList ob = new SoDuDauKyList();
	    		ob.setCongTyId(congTyId);
	    		ob.init();
	    		
	    		session.setAttribute("userId", userId);
		    	session.setAttribute("obj", ob);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Center/SoDuDauKy.jsp";
	    		response.sendRedirect(nextJSP);
	    		return;
	    	}
	    }
	    
    	session.setAttribute("userId", userId);
    	session.setAttribute("obj", obj);
    	
    	String nextJSP = "/TraphacoHYERP/pages/Center/SoDuDauKyUpdate.jsp";
		response.sendRedirect(nextJSP);
		return;
	}

	private void setParams(HttpSession session, HttpServletRequest request, SoDuDauKy obj) {
		String[] sheetNames = request.getParameterValues("sheetNames");
	    if (sheetNames != null)
	    {
	    	List<String> sns = obj.getSheetNames();
	    	sns.clear();
	    	for (String sheetName : sheetNames)
	    	{
	    		sns.add(sheetName);
	    	}
	    }
	    
	    String[] sheetName = request.getParameterValues("sheetName");
	    String[] trangThai = request.getParameterValues("trangThai");
	    String[] maChiNhanh = request.getParameterValues("maChiNhanh");
	    String[] chiNhanhId = request.getParameterValues("chiNhanhId");
	    String[] doiTuongId = request.getParameterValues("doiTuongId");
	    String[] loaiDoiTuongId = request.getParameterValues("loaiDoiTuongId");
	    String[] isDoiTuongNo = request.getParameterValues("isDoiTuongNo");
	    String[] taiKhoanNoId = request.getParameterValues("taiKhoanNoId");
	    String[] taiKhoanCoId = request.getParameterValues("taiKhoanCoId");
	    String[] tienTeId = request.getParameterValues("tienTeId");
	    String[] taiKhoanNoId1 = request.getParameterValues("taiKhoanNoId1");
	    String[] taiKhoanCoId1 = request.getParameterValues("taiKhoanCoId1");
	    String[] BTTH_Id = request.getParameterValues("BTTH_Id");
	    String[] BTTH_CT_Id = request.getParameterValues("BTTH_CT_Id");
	    String[] isNPP = request.getParameterValues("isNPP");
	    String[] maQuay = request.getParameterValues("maQuay");
	    String[] quayId = request.getParameterValues("quayId");
		
	    String[] soChungTu = request.getParameterValues("soChungTu");
	    String[] ngayChungTu = request.getParameterValues("ngayChungTu");
	    String[] maDoiTuongFast = request.getParameterValues("maDoiTuongFast");
	    String[] maDoiTuongErp = request.getParameterValues("maDoiTuongErp");
	    String[] tenDoiTuong = request.getParameterValues("tenDoiTuong");
	    String[] tenLoaiDoiTuong = request.getParameterValues("tenLoaiDoiTuong");
	    String[] soHieuTaiKhoanNo = request.getParameterValues("soHieuTaiKhoanNo");
	    String[] soHieuTaiKhoanCo = request.getParameterValues("soHieuTaiKhoanCo");
	    String[] soTienVND = request.getParameterValues("soTienVND");
	    String[] tenTienTe = request.getParameterValues("tenTienTe");
	    String[] tiGia = request.getParameterValues("tiGia");
	    String[] soTienNgoaiTe = request.getParameterValues("soTienNgoaiTe");
	    
	    String[] soHieuTaiKhoanNo1 = request.getParameterValues("soHieuTaiKhoanNo1");
	    String[] soHieuTaiKhoanCo1 = request.getParameterValues("soHieuTaiKhoanCo1");
	    String[] soTienVND1 = request.getParameterValues("soTienVND1");
	
	    String[] soHoaDonGoc = request.getParameterValues("soHoaDonGoc");
	    String[] ngayHoaDonGoc = request.getParameterValues("ngayHoaDonGoc");
	    String[] taiKhoanPhanMemFast = request.getParameterValues("taiKhoanPhanMemFast");
	    String[] soTienHoaDonGoc = request.getParameterValues("soTienHoaDonGoc");
	    
	    String[] itemMsg = request.getParameterValues("itemMsg");
	    
	    for (int i = 0; i < chiNhanhId.length; i++)
	    {
	    	SoDuDauKyItem item = new SoDuDauKyItem();
	    	item.setSheetName(sheetName[i]);
	    	item.setTrangThai(Integer.parseInt(trangThai[i]));
	    	item.setMaChiNhanh(maChiNhanh[i]);
	    	item.setChiNhanhId(chiNhanhId[i]);
	    	item.setDoiTuongId(doiTuongId[i]);
	    	item.setLoaiDoiTuongId(loaiDoiTuongId[i]);
	    	item.setIsDoiTuongNo(isDoiTuongNo[i]);
	    	item.setTaiKhoanNoId(taiKhoanNoId[i]);
	    	item.setTaiKhoanCoId(taiKhoanCoId[i]);
	    	item.setTienTeId(tienTeId[i]);
	    	item.setTaiKhoanNoId1(taiKhoanNoId1[i]);
	    	item.setTaiKhoanCoId1(taiKhoanCoId1[i]);
	    	item.setBTTH_Id(BTTH_Id[i]);
	    	item.setBTTH_CT_Id(BTTH_CT_Id[i]);
	    	item.setIsNPP(isNPP[i]);
	    	item.setMaQuay(maQuay[i]);
	    	item.setQuayId(quayId[i]);
	    	
	    	item.setSoChungTu(soChungTu[i]);
	    	item.setNgayChungTu(ngayChungTu[i]);
	    	item.setMaDoiTuongFast(maDoiTuongFast[i]);
	    	item.setMaDoiTuongErp(maDoiTuongErp[i]);
	    	item.setTenDoiTuong(tenDoiTuong[i]);
	    	item.setTenLoaiDoiTuong(tenLoaiDoiTuong[i]);
	    	item.setSoHieuTaiKhoanNo(soHieuTaiKhoanNo[i]);
	    	item.setSoHieuTaiKhoanCo(soHieuTaiKhoanCo[i]);
	    	
	    	try {
	    		item.setSoTienVND(Double.parseDouble(soTienVND[i].replaceAll(",", "")));
	    	} catch (Exception e) {
				e.printStackTrace();
			}	
	    	
	    	item.setTenTienTe(tenTienTe[i]);
	    	
	    	try{
	    		item.setTiGia(Double.parseDouble(tiGia[i].replaceAll(",", "")));
	    	} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	try{
	    		item.setSoTienNgoaiTe(Double.parseDouble(soTienNgoaiTe[i].replaceAll(",", "")));
	    	} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	item.setSoHieuTaiKhoanNo1(soHieuTaiKhoanNo1[i]);
	    	item.setSoHieuTaiKhoanCo1(soHieuTaiKhoanCo1[i]);
	    	try {
	    		item.setSoTienVND1(Double.parseDouble(soTienVND1[i].replaceAll(",", "")));
	    	} catch (Exception e) {
				e.printStackTrace();
			}	
	    	
	    	item.setSoHoaDonGoc(soHoaDonGoc[i]);
	    	item.setNgayHoaDonGoc(ngayHoaDonGoc[i]);
	    	item.setTaiKhoanPhanMemFast(taiKhoanPhanMemFast[i]);
	    	
	    	try{
	    		item.setSoTienHoaDonGoc(Double.parseDouble(soTienHoaDonGoc[i].replaceAll(",", "")));
	    	} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	item.setMsg(itemMsg[i]);
	    	
	    	try{
	    		String isTaoMoiDoiTuong = request.getParameter("isTaoMoiDoiTuong" + i);
	    		if (item.getMaDoiTuongFast().contains("338BHXH"))
	    			System.out.println("isTaoMoiDoiTuong" + i + ": " + isTaoMoiDoiTuong);
	    		if (isTaoMoiDoiTuong != null)
	    			item.setIsTaoMoiDoiTuong(1);
	    		else
	    			item.setIsTaoMoiDoiTuong(0);
	    	} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	obj.getItemList().add(item);
	    }
	}   
}