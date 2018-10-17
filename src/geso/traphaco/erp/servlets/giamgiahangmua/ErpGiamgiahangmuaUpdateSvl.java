package geso.traphaco.erp.servlets.giamgiahangmua;

import geso.traphaco.erp.beans.giamgiahangmua.IErpGiamgiahangmua;
import geso.traphaco.erp.beans.giamgiahangmua.IErpGiamgiahangmuaList;
import geso.traphaco.erp.beans.giamgiahangmua.imp.ErpGiamgiahangmua;
import geso.traphaco.erp.beans.giamgiahangmua.imp.ErpGiamgiahangmuaList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpGiamgiahangmuaUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpGiamgiahangmuaUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpGiamgiahangmua csxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    csxBean = new ErpGiamgiahangmua(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    csxBean.setCongtyId(ctyId);
	    csxBean.setId(id);
	    csxBean.setUserId(userId);
	    
	    csxBean.init();
        session.setAttribute("csxBean", csxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangMuaUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangMuaDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpGiamgiahangmua csxBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	csxBean = new ErpGiamgiahangmua();
	    }else{
	    	csxBean = new ErpGiamgiahangmua(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		csxBean.setUserId(userId);	       
		
		String ctyId = (String)session.getAttribute("congtyId");
		csxBean.setCongtyId(ctyId);
		
		String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
		if (ngayghinhan == null)
			ngayghinhan = "";
		csxBean.setNgayghinhan(ngayghinhan);
		
		String ngayhoadon = util.antiSQLInspection(request.getParameter("ngayhoadon"));
		if (ngayhoadon == null)
			ngayhoadon = "";
		csxBean.setNgayhoadon(ngayhoadon);
		
		String kyhieuhoadon = util.antiSQLInspection(request.getParameter("kyhieuhoadon"));
		if (kyhieuhoadon == null)
			kyhieuhoadon = "";
		csxBean.setKyhieuhoadon(kyhieuhoadon);
		
		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		if (sohoadon == null)
			sohoadon = "";
		csxBean.setSohoadon(sohoadon);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		csxBean.setDiengiai(diengiai);
		
		String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		if (nccId == null)
			nccId = "";
		csxBean.setNccId(nccId);
		
		String nccTen = util.antiSQLInspection(request.getParameter("nccTen"));
		if (nccTen == null)
			nccTen = "";
		csxBean.setNccTen(nccTen);
		System.out.println("Ten NCC: " + nccTen);
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "0";
		csxBean.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "0";
		csxBean.setDenngay(denngay);

		String tienteId = util.antiSQLInspection(request.getParameter("tienteId"));
		if (tienteId == null)
			tienteId = "0";
		csxBean.setTienteId(tienteId);
		
		String[] poIds = request.getParameterValues("poIds");
		if (poIds != null)
		{
		   String poId = "";
		   for(int i = 0; i < poIds.length; i++)
		   {
		    poId += poIds[i] + ",";
		   }
		   
		   if(poId.trim().length() > 0)
		    poId = poId.substring(0, poId.length() - 1);
		   
		   csxBean.setPOId(poId);
		   System.out.println("PO "+poId);
		}
		
		  
		String bVat = util.antiSQLInspection(request.getParameter("bvat"));
		if (bVat == null)
			bVat = "";
		csxBean.setBvat(bVat);
		
		String vat = util.antiSQLInspection(request.getParameter("vat"));
		if (vat == null)
			vat = "";
		csxBean.setVat(vat);
		
		String avat = util.antiSQLInspection(request.getParameter("avat"));
		if (avat == null)
			avat = "";
		csxBean.setAvat(avat);
		
		String[] idSanpham = request.getParameterValues("idSanpham");
		csxBean.setIdsanpham(idSanpham);
		
		String[] maSanpham = request.getParameterValues("maSanpham");
		csxBean.setMasanpham(maSanpham);
		
		String[] hdSanpham = request.getParameterValues("hdSanpham");
		csxBean.setHoadonTen(hdSanpham);
		
		String[] hdId = request.getParameterValues("hdId");
		csxBean.setHoadon(hdId);
		
		String[] tenSanpham = request.getParameterValues("tenSanpham");
		csxBean.setTensanpham(tenSanpham);
		
		String[] soluong = request.getParameterValues("soluong");
		csxBean.setSoluong(soluong);
		
		String[] sotien = request.getParameterValues("dongia");
		csxBean.setTongtien(sotien);
		
		String[] thanhtien = request.getParameterValues("thanhtien");
		csxBean.setTongtien(thanhtien);
		
		String[] sotiengiam = request.getParameterValues("sotien");
		csxBean.setSotien(sotiengiam);
		
		String[] loai = request.getParameterValues("loai");
		csxBean.setLoai(loai);

		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(csxBean.createGiamgia()))
				{
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangMuaNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpGiamgiahangmuaList obj = new ErpGiamgiahangmuaList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangMua.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(csxBean.updateGiamgia()))
				{
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangMuaUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpGiamgiahangmuaList obj = new ErpGiamgiahangmuaList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangMua.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			csxBean.createRS();
			
			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", csxBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangMuaNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangMuaUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
