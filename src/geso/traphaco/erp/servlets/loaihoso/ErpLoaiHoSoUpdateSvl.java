package geso.traphaco.erp.servlets.loaihoso;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaihoso.IErpLoaiHoSo;
import geso.traphaco.erp.beans.loaihoso.IErpLoaiHoSoList;
import geso.traphaco.erp.beans.loaihoso.imp.ErpLoaiHoSo;
import geso.traphaco.erp.beans.loaihoso.imp.ErpLoaiHoSoList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class ErpLoaiHoSoUpdateSvl
 */
@WebServlet("/ErpLoaiHoSoUpdateSvl")
public class ErpLoaiHoSoUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpLoaiHoSoUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId == null || userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IErpLoaiHoSo obj = new ErpLoaiHoSo();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String Id = util.getId(querystring);
    	obj.setId(Id);
    	
    	obj.init();
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaihosoNew.jsp";
	    
	    if(querystring.indexOf("display") >= 0) {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaihosoDisplay.jsp";
        }
	    
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    IErpLoaiHoSo obj = new ErpLoaiHoSo();
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setCongtyId(ctyId);
	    
		String contentType = request.getContentType();
		if (contentType != null && contentType.indexOf("multipart/form-data") >= 0) {
	    	String filePath = getServletContext().getInitParameter("pathPage") +  "\\upload\\";
	    	
	    	MultipartRequest multi = new MultipartRequest(request, filePath, 20000000, "UTF-8");
	    	
	    	String userId = multi.getParameter("userId");
	    	obj.setUserId(userId);
	    	
		    String id = multi.getParameter("id");
		    if(id == null)
		    	id = "";
		    obj.setId(id);
		    
		    String maloaihoso = multi.getParameter("maloaihoso");
			if (maloaihoso == null)
				maloaihoso = "";
			obj.setMaLoaihoso(maloaihoso);
			
			String mabieumau = multi.getParameter("mabieumau");
			if (mabieumau == null)
				mabieumau = "";
			obj.setMaBieumau(mabieumau);
			
			String diengiai = multi.getParameter("diengiai");
			if (diengiai == null)
				diengiai = "";
			obj.setDiengiai(diengiai);
			
			String ngaybanhanh = multi.getParameter("ngaybanhanh");
			if (ngaybanhanh == null)
				ngaybanhanh = "";
			obj.setNgaybanhanh(ngaybanhanh);
			
			String loaimauin = multi.getParameter("loaimauin");
			if (loaimauin == null)
				loaimauin = "";
			obj.setLoaimauin(loaimauin);
			
			String trangthai = multi.getParameter("trangthai");
			if (trangthai == null)
				trangthai = "0";
			obj.setTrangthai(trangthai);
			
			session.setAttribute("obj", obj);
		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaihosoNew.jsp";
			response.sendRedirect(nextJSP);
			
			Enumeration files = multi.getFileNames();
	    	
	    	String filename = "";
	    	String filenameNew = "";
			while (files.hasMoreElements()) {
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				if (filename!=null && filename.length()>0) {
					String ft = "";
					if (filename!=null && filename.contains(".")) 
						ft = filename.substring(filename.lastIndexOf("."), filename.length());
					
					filenameNew = "bieumauhoso" + "_" + System.currentTimeMillis() + ft;
					new File(filePath+filename).renameTo(new File(filePath + filenameNew));
				}
			}		
	    	
			if (filename != null) {
				obj.setBieumauPath(filePath + filenameNew);
				obj.setBieumauName(filename);
			}
	    } else {
	    	String userId = util.antiSQLInspection(request.getParameter("userId"));
	    	obj.setUserId(userId);
	    	
	    	String action = request.getParameter("action");
		    if (action == null)
		    	action = "";
		    
		    String id = util.antiSQLInspection(request.getParameter("id"));	
		    if(id == null)
		    	id = "";
		    obj.setId(id);
		    
		    String maloaihoso = util.antiSQLInspection(request.getParameter("maloaihoso"));
			if (maloaihoso == null)
				maloaihoso = "";
			obj.setMaLoaihoso(maloaihoso);
			
			String mabieumau = util.antiSQLInspection(request.getParameter("mabieumau"));
			if (mabieumau == null)
				mabieumau = "";
			obj.setMaBieumau(mabieumau);
			
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			obj.setDiengiai(diengiai);
			
			String ngaybanhanh = util.antiSQLInspection(request.getParameter("ngaybanhanh"));
			if (ngaybanhanh == null)
				ngaybanhanh = "";
			obj.setNgaybanhanh(ngaybanhanh);
			
			String loaimauin = util.antiSQLInspection(request.getParameter("loaimauin"));
			if (loaimauin == null)
				loaimauin = "";
			obj.setLoaimauin(loaimauin);
			
			String bieumaupath = util.antiSQLInspection(request.getParameter("bieumau_path"));
			if (bieumaupath == null)
				bieumaupath = "";
			obj.setBieumauPath(bieumaupath);
			
			String bieumauname = util.antiSQLInspection(request.getParameter("bieumau_name"));
			if (bieumauname == null)
				bieumauname = "";
			obj.setBieumauName(bieumauname);
			
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null || trangthai == "")
				trangthai = "0";
			obj.setTrangthai(trangthai);
	    	
		    if(action.equals("save")) {
		    	if(id.length() > 0){
		    		if(obj.update()){
		    			obj.DBClose();
		    			IErpLoaiHoSoList objList = new ErpLoaiHoSoList();
		    			
		    			objList.setUserId(userId);
		    			objList.setCongtyId(ctyId);
		    			objList.init();
		    		    
		    			session.setAttribute("obj", objList);
		    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaihoso.jsp";
		    			response.sendRedirect(nextJSP);
		    		} else {
		    			session.setAttribute("obj", obj);
		    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaihosoNew.jsp";
		    			response.sendRedirect(nextJSP);
		    		}
		    	} else {
		    		if(obj.create()){
		    			obj.DBClose();
		    			IErpLoaiHoSoList objList = new ErpLoaiHoSoList();
		    			
		    			objList.setUserId(userId);
		    			objList.setCongtyId(ctyId);
		    			objList.init();
		    		    
		    			session.setAttribute("obj", objList);
		    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaihoso.jsp";
		    			response.sendRedirect(nextJSP);
		    		} else {
		    			session.setAttribute("obj", obj);
		    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaihosoNew.jsp";
		    			response.sendRedirect(nextJSP);
		    		}
		    	}
		    } else if (action.equals("download")) {
		    	try {
		    		// reads input file from an absolute path
			        String filePath = bieumaupath;
			        File downloadFile = new File(filePath);
			        FileInputStream inStream = new FileInputStream(downloadFile);
			        
			        // if you want to use a relative path to context root:
			        String relativePath = getServletContext().getRealPath("");
			        System.out.println("relativePath = " + relativePath);
			        
			        // obtains ServletContext
			        ServletContext context = getServletContext();
			        
			        // gets MIME type of the file
			        String mimeType = context.getMimeType(filePath);
			        if (mimeType == null) {        
			            // set to binary type if MIME mapping not found
			            mimeType = "application/octet-stream";
			        }
			        System.out.println("MIME type: " + mimeType);
			        
			        // modifies response
			        response.setContentType(mimeType);
			        response.setContentLength((int) downloadFile.length());
			        
			        // forces download
			        String headerKey = "Content-Disposition";
			        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
			        response.setHeader(headerKey, headerValue);
			        
			        // obtains response's output stream
			        OutputStream outStream = response.getOutputStream();
			        
			        byte[] buffer = new byte[4096];
			        int bytesRead = -1;
			         
			        while ((bytesRead = inStream.read(buffer)) != -1) {
			            outStream.write(buffer, 0, bytesRead);
			        }
			        
			        inStream.close();
			        outStream.close();
				} catch (Exception e) {
					// TODO: handle exception
					obj.setMsg("File này đã bị xóa không thể download.");
					
					session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaihosoNew.jsp";
	    			response.sendRedirect(nextJSP);
				}
		    }
		}
	}

}
