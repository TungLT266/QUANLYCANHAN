package geso.traphaco.distributor.servlets.dondathang;

import geso.traphaco.distributor.beans.dondathang.IErpDuyetddhNpp;
import geso.traphaco.distributor.beans.dondathang.IErpDuyetddhNppList;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDuyetddhNpp;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDuyetddhNppList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDuyetddhNppUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpDuyetddhNppUpdateSvl() 
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
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 
		    
		    String id = util.getId(querystring);  	
		    IErpDuyetddhNpp lsxBean = new ErpDuyetddhNpp(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		String hopdongId = request.getParameter("hopdongId");
		    if(hopdongId == null)
		    	hopdongId = "";
    			
			nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangDuyetDisplay.jsp";
			if(lsxBean.getLoaidonhang().equals("0") && hopdongId.trim().length() > 0 )
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangETCDuyetDisplay.jsp";
    		
	        session.setAttribute("lsxBean", lsxBean);
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
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpDuyetddhNpp lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpDuyetddhNpp("");
		    }
		    else
		    {
		    	lsxBean = new ErpDuyetddhNpp(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null)
		    	ngayyeucau = "";
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String ngaydenghi = util.antiSQLInspection(request.getParameter("ngaydenghi"));
		    if(ngaydenghi == null )
		    	ngaydenghi = "";
		    lsxBean.setNgaydenghi(ngaydenghi);
		    	    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
			if (dvkdId == null)
				dvkdId = "";				
			lsxBean.setDvkdId(dvkdId);
			
			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null)
				kbhId = "";				
			lsxBean.setKbhId(kbhId);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
		    
			String loaidonhang = util.antiSQLInspection(request.getParameter("loaidonhang"));
			if (loaidonhang == null)
				loaidonhang = "";				
			lsxBean.setLoaidonhang(loaidonhang);
			
			String action = util.antiSQLInspection(request.getParameter("action"));
			if (action == null)
				action = "";				
			
			System.out.println("---VO DUYET DON HANG...");
			boolean kq = false;
			
			if(action.equals("save"))
				kq = lsxBean.saveDDH(request);
			else
				kq = lsxBean.duyetDDH(request);
			
			if(!kq)
			{
				lsxBean.init();
				session.setAttribute("lsxBean", lsxBean);
				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangDuyetDisplay.jsp";
				if(lsxBean.getLoaidonhang().equals("4"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhacDuyetDisplay.jsp";
				else if(lsxBean.getLoaidonhang().equals("2"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMTichLuyDuyetDisplay.jsp";
				else if(lsxBean.getLoaidonhang().equals("1"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHangDuyetDisplay.jsp";
				else if(lsxBean.getLoaidonhang().equals("3"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangTrungBayDuyetDisplay.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				IErpDuyetddhNppList obj = new ErpDuyetddhNppList();
				obj.setLoaidonhang(loaidonhang);
				
			    obj.setUserId(userId);
			    obj.init("");
				session.setAttribute("obj", obj);							
				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangDuyet.jsp";
				response.sendRedirect(nextJSP);
			}
		}	
	}
	
	
}
