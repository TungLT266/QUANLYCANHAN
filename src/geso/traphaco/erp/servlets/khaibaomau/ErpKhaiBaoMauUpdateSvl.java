package geso.traphaco.erp.servlets.khaibaomau;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.khaibaomau.imp.ErpKhaiBaoMau;
import geso.traphaco.erp.beans.khaibaomau.imp.ErpKhaiBaoMauChiTiet;
import geso.traphaco.erp.beans.khaibaomau.imp.ErpKhaiBaoMauList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKhaiBaoMauUpdateSvl extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErpKhaiBaoMauUpdateSvl()
	{
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
		    
		    ErpKhaiBaoMau obj = new ErpKhaiBaoMau();
		    String ctyId = (String)session.getAttribute("congtyId");
//		    obj.setCongTyId(ctyId);
		    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    
		    String action = util.getAction(querystring);
		    System.out.println("\n **************action:" + action);
		    
		    String id = util.getId(querystring);

		    obj.setMaSo(id);
		   	
			obj.init();
			
			session.setAttribute("action", action);
	    	session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);
	    		
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKhaiBaoMauUpdate.jsp");
		}  	
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
			HttpSession session = request.getSession();
		    String userId = request.getParameter("userId");
		    String id = request.getParameter("id");
		    
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = "";
		    }
		        
		    ErpKhaiBaoMau obj = new ErpKhaiBaoMau();
		    obj.setMaSo(id);
//		    obj.setNguoiSua(userId);
		    String congTyId = (String)session.getAttribute("congtyId");
//		    obj.setCongTyId(congTyId);
//		    obj.setNguoiTao(userId);
		    System.out.println("congTyId la: " + congTyId);
		    
		    obj.init();
		    
		    String stt = request.getParameter("stt");
		    obj.setStt(stt);
		    
		    String maSo = request.getParameter("maSo");
		    obj.setMaSo(maSo);
		    
		    String thuyetMinh = request.getParameter("thuyetMinh");
		    obj.setThuyetMinh(thuyetMinh);
		    
		    String tenTieuChi = request.getParameter("tenTieuChi");
		    obj.setTenTieuChi(tenTieuChi);
		    
		    String isCongThuc = request.getParameter("isCongThuc");
		    if (isCongThuc != null)
		    	obj.setIsCongThuc(Integer.parseInt(isCongThuc));
		    
		    String congThuc = request.getParameter("congThuc");
		    obj.setCongThuc(congThuc);
		    
		    String inDam = request.getParameter("inDam");
		    if (inDam != null)
		    	obj.setInDam(Integer.parseInt(inDam));
		    
		    String isCongThucExcel = request.getParameter("isCongThucExcel");
		    if (isCongThucExcel != null)
		    	obj.setIsCongThucExcel(Integer.parseInt(isCongThucExcel));
		    
		    String[] checkSoHieuTaiKhoanArr = request.getParameterValues("checkSoHieuTaiKhoan");

		    for (int i = 0; i < checkSoHieuTaiKhoanArr.length; i++)
		    {
		    	String soHieuTaiKhoan = checkSoHieuTaiKhoanArr[i];
		    	
		    	ErpKhaiBaoMauChiTiet chiTiet = new ErpKhaiBaoMauChiTiet();
		    	obj.getChiTietList().add(chiTiet);
		    	
		    	chiTiet.setSoHieuTaiKhoan(soHieuTaiKhoan);
		    	
		    	String loaiTSNV = request.getParameter("loaiTSNV_" + soHieuTaiKhoan);
		    	if (loaiTSNV != null)
		    		chiTiet.setLoaiTSNV(Integer.parseInt(loaiTSNV));
		    	
		    	String laySoDu = request.getParameter("laySoDu_" + soHieuTaiKhoan);
		    	if (laySoDu != null)
		    		chiTiet.setLaySoDu(Integer.parseInt(laySoDu));
		    	
		    	
		    	String laySoDuNo = request.getParameter("laySoDuNo_" + soHieuTaiKhoan);
		    	if (laySoDuNo != null)
		    		chiTiet.setLaySoDuNo(Integer.parseInt(laySoDuNo));
		    	
		    	
		    	String laySoDuCo = request.getParameter("laySoDuCo_" + soHieuTaiKhoan);
		    	if (laySoDuCo != null)
		    		chiTiet.setLaySoDuCo(Integer.parseInt(laySoDuCo));
		    	
		    	
		    	String khongAm = request.getParameter("khongAm_" + soHieuTaiKhoan);
		    	if (khongAm != null)
		    		chiTiet.setKhongAm(Integer.parseInt(khongAm));
		    }
		    System.out.println("action: " + action);
		    if (action.equals("new"))
		    {
		    	if (!obj.create())
		    	{
		    		session.setAttribute("userId", userId);
			    	session.setAttribute("obj", obj);
			    	
			    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhaiBaoMauUpdate.jsp";
		    		response.sendRedirect(nextJSP);
		    		return;
		    	}
		    	else
		    	{
		    		obj.DbClose();
		    		
		    		ErpKhaiBaoMauList ob = new ErpKhaiBaoMauList();
		    		ob.setCongTyId(congTyId);
		    		ob.init();
		    		
		    		session.setAttribute("userId", userId);
			    	session.setAttribute("obj", ob);
			    	
			    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhaiBaoMau.jsp";
		    		response.sendRedirect(nextJSP);
		    		return;
		    	}
		    }else
		    	if (action.equals("update"))
		    	{
		    		if (!obj.edit())
			    	{
			    		session.setAttribute("userId", userId);
				    	session.setAttribute("obj", obj);
				    	
				    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhaiBaoMauUpdate.jsp";
			    		response.sendRedirect(nextJSP);
			    		return;
			    	}
			    	else
			    	{
			    		obj.DbClose();
			    		
			    		ErpKhaiBaoMauList ob = new ErpKhaiBaoMauList();
			    		ob.setCongTyId(congTyId);
			    		ob.init();
			    		
			    		session.setAttribute("userId", userId);
				    	session.setAttribute("obj", ob);
				    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhaiBaoMau.jsp";
			    		response.sendRedirect(nextJSP);
			    		return;
			    	}
		    	}
		    
	    	session.setAttribute("userId", userId);
	    	session.setAttribute("obj", obj);
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhaiBaoMauUpdate.jsp";
 		response.sendRedirect(nextJSP);
 		return;
		}   
	}