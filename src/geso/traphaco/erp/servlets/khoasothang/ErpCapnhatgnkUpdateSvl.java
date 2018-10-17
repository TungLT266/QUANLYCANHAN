package geso.traphaco.erp.servlets.khoasothang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.khoasothang.IErpCapnhatgianhap;
import geso.traphaco.erp.beans.khoasothang.IErpCapnhatgianhapList;
import geso.traphaco.erp.beans.khoasothang.ISanpham;
import geso.traphaco.erp.beans.khoasothang.imp.ErpCapnhatgianhap;
import geso.traphaco.erp.beans.khoasothang.imp.ErpCapnhatgianhapList;
import geso.traphaco.erp.beans.khoasothang.imp.Sanpham;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCapnhatgnkUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpCapnhatgnkUpdateSvl() {
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
		
			this.out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String id = util.getId(querystring);  	
			IErpCapnhatgianhap nhBean = new ErpCapnhatgianhap(id);
	        nhBean.setUserId(userId); //phai co UserId truoc khi Init
	        nhBean.init();
	    	
	        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCapNhatGiaNhapDisplay.jsp";
	        
	        session.setAttribute("obj", nhBean);
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
			IErpCapnhatgianhap nhBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
		    if(id == null)
		    {  	
		    	nhBean = new ErpCapnhatgianhap("");
		    }
		    else
		    {
		    	nhBean = new ErpCapnhatgianhap(id);
		    }
	
		    nhBean.setUserId(userId);
			
	    	String tungay = request.getParameter("tungay");
			if (tungay == null)
				tungay = "";				
	    	nhBean.setTungay(tungay);
	    	
	    	String denngay = request.getParameter("denngay");
	    	if(denngay == "")
	    		denngay = "";
	    	nhBean.setDenngay(denngay);
	    	
	    	String loaisanpham = request.getParameter("loaisanpham");
	    	if(loaisanpham == "")
	    		loaisanpham = "";
	    	nhBean.setLoaiSanPhamIds(loaisanpham);
	    	
	    	String diengiai = request.getParameter("diengiai");
	    	if(diengiai == "")
	    		diengiai = "";
	    	nhBean.setGhichu(diengiai);
	    	
	    	String[] pnkIds = request.getParameterValues("pnkIds");
	    	String pnkId = "";
	    	if(pnkIds != null)
	    	{
	    		for(int i = 0; i < pnkIds.length; i++)
	    		{
	    			pnkId += pnkIds[i] + ",";
	    		}
	    	}
	    	if(pnkId.length() > 0)
	    		pnkId = pnkId.substring(0, pnkId.length() - 1);
	    	nhBean.setPnkIds(pnkId);
	    	
	    	//Luu lai san pham
	    	String[] idphieunhap = request.getParameterValues("idphieunhap");
	    	String[] maphieu = request.getParameterValues("maphieu");
	    	String[] ngaynhap = request.getParameterValues("ngaynhap");
	    	String[] spId = request.getParameterValues("idsanpham");
			String[] spMa = request.getParameterValues("masanpham");
			String[] spTen = request.getParameterValues("tensanpham");
			String[] soluong = request.getParameterValues("soluong");
			String[] gianhap = request.getParameterValues("gianhap");
			String[] giadieuchinh = request.getParameterValues("giadieuchinh");
			
			List<ISanpham> spList =  new ArrayList<ISanpham>();
			
			if(spMa != null)
			{		
				ISanpham sanpham = null;
				int m = 0;
				while(m < spMa.length)
				{
					sanpham = new Sanpham(maphieu[m], spId[m], spMa[m], spTen[m], soluong[m], gianhap[m], giadieuchinh[m]);
					sanpham.setPnkId(idphieunhap[m]);
					sanpham.setNgaynhap(ngaynhap[m]);
					
					spList.add(sanpham);
						
					m++;
				}	
			}
			nhBean.setSanphamList(spList);
			
			String action = request.getParameter("action");
			
			if(action.equals("save"))
			{	
				if(id == null) //tao moi
				{
					if(!nhBean.createCngn())
					{
	    		    	nhBean.createRs();
	    		    
	    		    	session.setAttribute("obj", nhBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCapNhatGiaNhapNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpCapnhatgianhapList obj = new ErpCapnhatgianhapList();
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCapNhatGiaNhap.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!nhBean.updateCngn())
					{
						nhBean.createRs();
		    		    
						session.setAttribute("nhBean", nhBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCapNhatGiaNhapUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpCapnhatgianhapList obj = new ErpCapnhatgianhapList();
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCapNhatGiaNhap.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				String nextJSP;
				nhBean.createRs();
				
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpCapNhatGiaNhapNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpCapNhatGiaNhapUpdate.jsp";   						
				}
				
				session.setAttribute("obj", nhBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	

}
