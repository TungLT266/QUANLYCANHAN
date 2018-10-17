package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.distributor.beans.xuatkho.IErpImportdonhangETC;
import geso.traphaco.distributor.beans.xuatkho.IErpImportdonhangETCList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpImportdonhangETC;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpImportdonhangETCList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpImportdonhangETCUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpImportdonhangETCUpdateSvl() 
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
		    IErpImportdonhangETC lsxBean = new ErpImportdonhangETC(id);
		    lsxBean.setUserId(userId); 

		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		if(!querystring.contains("display"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpImportDonHangETCUpdate.jsp";	
    		}
    		else
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpImportdonhangETCDisplay.jsp";
    		}
    		
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
			IErpImportdonhangETC lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpImportdonhangETC("");
		    }
		    else
		    {
		    	lsxBean = new ErpImportdonhangETC(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    String xuatchoId = util.antiSQLInspection(request.getParameter("xuatchoId"));
		    if(xuatchoId == null)
		    	xuatchoId = "0";
		    lsxBean.setXuatcho(xuatchoId);
		    
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
			
			String khId = util.antiSQLInspection(request.getParameter("khId"));
			if (khId == null)
				khId = "";				
			lsxBean.setKhId(khId);
			
			String tinhthanhId = util.antiSQLInspection(request.getParameter("tinhthanhId"));
			if (tinhthanhId == null)
				tinhthanhId = "";				
			lsxBean.setTinhthanhId(tinhthanhId);
			
			String quanhuyenId = util.antiSQLInspection(request.getParameter("quanhuyenId"));
			if (quanhuyenId == null)
				quanhuyenId = "";				
			lsxBean.setQuanhuyenId(quanhuyenId);
			
			String nvbhId = util.antiSQLInspection(request.getParameter("nvbhId"));
			if (nvbhId == null)
				nvbhId = "";				
			lsxBean.setNVBHId(nvbhId);
			
			String nvgnId = util.antiSQLInspection(request.getParameter("nvgnId"));
			if (nvgnId == null)
				nvgnId = "";				
			lsxBean.setNVGNId(nvgnId);
			
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null)
				tungay = "";				
			lsxBean.setTungay(tungay);
			
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null)
				denngay = "";				
			lsxBean.setDenngay(denngay);
			
			String chanhxe = util.antiSQLInspection(request.getParameter("chanhxe"));
			if (chanhxe == null)
				chanhxe = "";				
			lsxBean.setChanhxe(chanhxe);
			
			String dienthoai = util.antiSQLInspection(request.getParameter("dienthoai"));
			if (dienthoai == null)
				dienthoai = "";				
			lsxBean.setDienthoai(dienthoai);
			
			String soluong = util.antiSQLInspection(request.getParameter("soluong"));
			if (soluong == null)
				soluong = "";				
			lsxBean.setSoluong(soluong);
			
			String donvi = util.antiSQLInspection(request.getParameter("donvi"));
			if (donvi == null)
				donvi = "";				
			lsxBean.setDonvi(donvi);
			
			String nhanvienTuId = util.antiSQLInspection(request.getParameter("nhanvienTuId"));
			if (nhanvienTuId == null)
				nhanvienTuId = "";				
			lsxBean.setNhanvien_TuId(nhanvienTuId);
			
			String nhanvienDenId = util.antiSQLInspection(request.getParameter("nhanvienDenId"));
			if (nhanvienDenId == null)
				nhanvienDenId = "";				
			lsxBean.setNhanvien_DenId(nhanvienDenId);
			
			String nhanvienDenQL = util.antiSQLInspection(request.getParameter("nhanvienDenQL"));
			if (nhanvienDenQL == null)
				nhanvienDenQL = "";				
			lsxBean.setNhanvien_DenQL(nhanvienDenQL);
			
			String[] nvCCIds = request.getParameterValues("nhanvienCCQLId");
			if (nvCCIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < nvCCIds.length; i++)
				{
					_scheme += nvCCIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					lsxBean.setNhanvien_CCQLId(_scheme);
				}
			}
			
			String nhanvienCCQL = util.antiSQLInspection(request.getParameter("nhanvienCCQL"));
			if (nhanvienCCQL == null)
				nhanvienCCQL = "";				
			lsxBean.setNhanvien_CCQL(nhanvienCCQL);
			
			String[] ddhIds = request.getParameterValues("ddhIds");
			if (ddhIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < ddhIds.length; i++)
				{
					_scheme += ddhIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					lsxBean.setDondathangId(_scheme);
				}
			}
			
			String maFast = util.antiSQLInspection(request.getParameter("maFastSEARCH"));
			if (maFast == null)
				maFast = "";				
			lsxBean.setMaFast(maFast);
			
			String tenkhachhang = util.antiSQLInspection(request.getParameter("tenkhachhangSEARCH"));
			if (tenkhachhang == null)
				tenkhachhang = "";				
			lsxBean.setTenkhachhang(tenkhachhang);
			
			String machungtuSEARCH = util.antiSQLInspection(request.getParameter("machungtuSEARCH"));
			if (machungtuSEARCH == null)
				machungtuSEARCH = "";				
			lsxBean.setMachungtu(machungtuSEARCH);
			
			String nguoitao = util.antiSQLInspection(request.getParameter("nguoitaoSEARCH"));
			if (nguoitao == null)
				nguoitao = "";				
			lsxBean.setNguoitaodon(nguoitao);
			
		    String action = request.getParameter("action");
			if(action.equals("save"))
			{	
				if(id == null)
				{
					if(!lsxBean.create( request.getParameterValues("machungtu") ))
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpImportDonHangETCNew.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpImportdonhangETCList obj = new ErpImportdonhangETCList();
						
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpImportDonHangETC.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!lsxBean.update( request.getParameterValues("machungtu") ))
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpImportDonHangETCUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpImportdonhangETCList obj = new ErpImportdonhangETCList();
						
					    obj.setUserId(userId);
					    //obj.setPhanloai(xuatchoId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpImportDonHangETC.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				System.out.println(":::: ACTION LA: " + action);
				if( action.equals("loclaiPO") )
					lsxBean.setDondathangId("");

				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpImportDonHangETCNew.jsp";
				if(id != null)
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpImportDonHangETCUpdate.jsp";
				
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
