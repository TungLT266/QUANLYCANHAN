package geso.traphaco.erp.servlets.congbosanpham;

import geso.traphaco.erp.beans.congbosanpham.ICongbosanpham;
import geso.traphaco.erp.beans.congbosanpham.ICongbosanphamList;
import geso.traphaco.erp.beans.congbosanpham.imp.Congbosanpham;
import geso.traphaco.erp.beans.congbosanpham.imp.CongbosanphamList;
import geso.traphaco.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

public class CongbosanphamUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public CongbosanphamUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String ctyId = (String)session.getAttribute("congtyId");
		String ctyTen = (String)session.getAttribute("congtyTen");
		Utility util = new Utility();
		String querystring = request.getQueryString();
		
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		ICongbosanpham obj = new Congbosanpham();
		
		obj.setUserId(userId);

		String id = getId(querystring);
		obj.setId(id);
		
		if(querystring.contains("print"))
		{
			System.out.println("Print");
			String fn = getFilename(querystring);
			System.out.println("Filename: "+fn);
			String filePath = getServletContext().getInitParameter("path") + "\\images\\" + fn;
			
			String ft = "";
			if (fn!=null && fn.contains(".")) 
				ft = fn.substring(fn.lastIndexOf(".")+1, fn.length());
			if (ft.length()>0)
			{
			if (ft.toLowerCase().equals("pdf"))
			{
				FileInputStream fstream = new FileInputStream(filePath);
		         
		        response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", " inline; filename=Hosocongbo.pdf");
					
		        ServletOutputStream os = response.getOutputStream();
		        byte[] bufferData = new byte[1024];
		        int read=0;
		        while((read = fstream.read(bufferData))!= -1){
		             os.write(bufferData, 0, read);
		        }
		        os.flush();
		        os.close();
		     	fstream.close();
			}else{
	            
				FileInputStream fstream = new FileInputStream(filePath);
	            response.setContentType("image/gif");
	            ServletOutputStream os = response.getOutputStream();
		        byte[] bufferData = new byte[1024];
		        int read=0;
		        while((read = fstream.read(bufferData))!= -1){
		             os.write(bufferData, 0, read);
		        }
		        os.flush();
		        os.close();
		     	fstream.close();
			}}
			
		}else{
		
		obj.init();

		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/CongbosanphamUpdate.jsp";
		if (querystring.indexOf("display") > 0)
			nextJSP = "/TraphacoHYERP/pages/Erp/CongbosanphamDisplay.jsp";

		response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId"); 
	    String ctyTen = (String)session.getAttribute("congtyTen");
		Utility util = new Utility();
		String contentType = request.getContentType();
		
		ICongbosanpham obj = new Congbosanpham();
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id != null) obj.setId(id);
		
		String userId;
		String masp = "";
		String tensp = "";
		String mancc = "";
		String tenncc = "";
		
		String hancongbo;
		String hinhcongbo = "";
		String filepath = "";
		String action;
		
		
		String sodangki;
		String dangbaoche;
		String ngaybatdauhieuluc;
		String ngayketthuchieuluc;
		
		
		
		
		
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			String filePath = getServletContext().getInitParameter("path") + "\\\\images\\\\";
			MultipartRequest multi = new MultipartRequest(request, filePath, 20000000, "UTF-8");										
			Enumeration files = multi.getFileNames();
			
			String filename;
			filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				if (filename!=null && filename.length()>0) 
				{
					String ft = "";
					if (filename!=null && filename.contains(".")) 
						ft = filename.substring(filename.lastIndexOf("."), filename.length());
					
					hinhcongbo = "hosocongbo" + "_" + System.currentTimeMillis() + ft;
					filepath=filePath+hinhcongbo;
					new File(filePath+filename).renameTo(new File(filepath));
				}
			}		
			obj.setFilepath(filepath);
			obj.setHinhcongbo(hinhcongbo);

			id = util.antiSQLInspection(multi.getParameter("id"));
			if (id != null) obj.setId(id);
			
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			obj.setUserId(userId);
			
			masp = util.antiSQLInspection(multi.getParameter("masp"));
			if (masp == null)
				masp = "";
			obj.setMasp(masp);
			tensp = util.antiSQLInspection(multi.getParameter("tensp"));
			if (tensp == null)
				tensp = "";
			obj.setTensp(tensp);
			mancc = util.antiSQLInspection(multi.getParameter("mancc"));
			if (mancc == null)
				mancc = "";
			obj.setMancc(mancc);
			tenncc = util.antiSQLInspection(multi.getParameter("tenncc"));
			if (tenncc == null)
				tenncc = "";
			obj.setTenncc(tenncc);
			
			hancongbo = util.antiSQLInspection(multi.getParameter("hancongbo"));
			if (hancongbo == null)
				hancongbo = "";
			obj.setHancongbo(hancongbo);
			
			

			sodangki = util.antiSQLInspection(multi.getParameter("sodangki"));
			if (sodangki == null)
				sodangki = "";
			obj.setSodangki(sodangki);
			
			
			dangbaoche = util.antiSQLInspection(multi.getParameter("dangbaoche"));
			if (dangbaoche == null)
				dangbaoche = "";
			obj.setDangbaocheId(dangbaoche);
			
			
			ngaybatdauhieuluc = util.antiSQLInspection(multi.getParameter("ngaybatdau"));
			if (ngaybatdauhieuluc == null)
				ngaybatdauhieuluc = "";
			obj.setNgaybatdauhieuluc(ngaybatdauhieuluc);
			
			ngayketthuchieuluc= util.antiSQLInspection(multi.getParameter("ngayketthuc"));
			if (ngayketthuchieuluc == null)
				ngayketthuchieuluc = "";
			obj.setNgayketthuchieuluc(ngayketthuchieuluc);
			
			
	
			action = multi.getParameter("action");
		}
		else
		{
			userId = util.antiSQLInspection(request.getParameter("userId"));
			obj.setUserId(userId);
			
			id = util.antiSQLInspection(request.getParameter("id"));
			if (id!=null) obj.setId(id);
			
			masp = util.antiSQLInspection(request.getParameter("masp"));
			if (masp == null)
				masp = "";
			obj.setMasp(masp);
			tensp = util.antiSQLInspection(request.getParameter("tensp"));
			if (tensp == null)
				tensp = "";
			obj.setTensp(tensp);
			mancc = util.antiSQLInspection(request.getParameter("mancc"));
			if (mancc == null)
				mancc = "";
			obj.setMancc(mancc);
			tenncc = util.antiSQLInspection(request.getParameter("tenncc"));
			if (tenncc == null)
				tenncc = "";
			obj.setTenncc(tenncc);
	
			hancongbo = util.antiSQLInspection(request.getParameter("hancongbo"));
			if (hancongbo == null)
				hancongbo = "";
			obj.setHancongbo(hancongbo);
	
			hinhcongbo = util.antiSQLInspection(request.getParameter("hinhcongbo"));
			if (hinhcongbo == null)
				hinhcongbo = "";
			obj.setHinhcongbo(hinhcongbo);
			
			filepath = util.antiSQLInspection(request.getParameter("filepath"));
			if (filepath == null)
				filepath = "";
			obj.setFilepath(filepath);
			
			
			sodangki = util.antiSQLInspection(request.getParameter("sodangki"));
			if (sodangki == null)
				sodangki = "";
			obj.setSodangki(sodangki);
			
			
			dangbaoche = util.antiSQLInspection(request.getParameter("dangbaoche"));
			if (dangbaoche == null)
				dangbaoche = "";
			obj.setDangbaocheId(dangbaoche);
			
			
			ngaybatdauhieuluc = util.antiSQLInspection(request.getParameter("ngaybatdau"));
			if (ngaybatdauhieuluc == null)
				ngaybatdauhieuluc = "";
			obj.setNgaybatdauhieuluc(ngaybatdauhieuluc);
			
			ngayketthuchieuluc= util.antiSQLInspection(request.getParameter("ngayketthuc"));
			if (ngayketthuchieuluc == null)
				ngayketthuchieuluc = "";
			obj.setNgayketthuchieuluc(ngayketthuchieuluc);
			
			
	
			action = request.getParameter("action");
		}
		
		if(action.equals("save"))
		{
			if(id.length()==0)
			{
				if (!(obj.createCongbosanpham()))
				{
					obj.createRs();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					String nextJSP = "/TraphacoHYERP/pages/Erp/CongbosanphamNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					ICongbosanphamList obj1 = new CongbosanphamList();
					obj1.setUserId(userId);
					obj1.init("");

					session.setAttribute("obj", obj1);

					String nextJSP = "/TraphacoHYERP/pages/Erp/Congbosanpham.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				if (!(obj.updateCongbosanpham()))
				{
					//obj.setMsg(msg);
					obj.createRs();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					
					String nextJSP = "/TraphacoHYERP/pages/Erp/CongbosanphamUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					ICongbosanphamList obj1 = new CongbosanphamList();
					System.out.println("ma cty: "+ctyId);
					obj1.setUserId(userId);
					obj1.setCtyId(ctyId);
					obj1.init("");
					
					session.setAttribute("obj", obj1);

					String nextJSP = "/TraphacoHYERP/pages/Erp/Congbosanpham.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
		else
		{
			obj.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Erp/CongbosanphamNew.jsp";

			if( id != null )
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/CongbosanphamUpdate.jsp";
			}

			response.sendRedirect(nextJSP);
		}
	}
	
	private String getId(String str) 
	{
		String id = "";
		String tmp = "";
		if (str!=null) if (str.length()>0 && str.contains("&")) tmp = str.split("&")[1];
		if (tmp!=null) if (tmp.length()>0 && tmp.contains("=")) id = tmp.split("=")[1];
		return id;
	}
	
	private String getFilename(String str) 
	{
		String fn = "";
		String tmp = "";
		if (str!=null) if (str.length()>0 && str.contains("&")) tmp = str.split("&")[1];
		if (tmp!=null) if (tmp.length()>0 && tmp.contains("=")) fn = tmp.split("=")[1];
		return fn;
	}
}
