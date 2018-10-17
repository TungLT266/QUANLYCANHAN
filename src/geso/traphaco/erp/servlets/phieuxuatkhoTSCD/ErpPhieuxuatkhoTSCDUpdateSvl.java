package geso.traphaco.erp.servlets.phieuxuatkhoTSCD;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.phieuxuatkhoTSCD.*;
import geso.traphaco.erp.beans.phieuxuatkhoTSCD.imp.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpPhieuxuatkhoTSCDUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	PrintWriter out;
	
    public ErpPhieuxuatkhoTSCDUpdateSvl() {
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
			IErpPhieuxuatkhoTSCD pxkBean = new ErpPhieuxuatkhoTSCD(id);
			pxkBean.setCongtyId((String)session.getAttribute("congtyId"));
	        pxkBean.setUserId(userId); //phai co UserId truoc khi Init
	        pxkBean.init();
	    	
	        String nextJSP;
	        
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoTSCDDisplay.jsp";
	        }
	        else
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoTSCDUpdate.jsp";
	        }
	 
	        session.setAttribute("pxkBean", pxkBean);
	        session.setAttribute("ndxId", pxkBean.getNdxId());
	        
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
			IErpPhieuxuatkhoTSCD pxkBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
		    if(id == null)
		    {  	
		    	pxkBean = new ErpPhieuxuatkhoTSCD("");
		    }
		    else
		    {
		    	pxkBean = new ErpPhieuxuatkhoTSCD(id);
		    }
	
		    pxkBean.setCongtyId((String)session.getAttribute("congtyId"));
		    pxkBean.setUserId(userId);
			
		    String ngayxk = util.antiSQLInspection(request.getParameter("ngayxuatkho"));
			if (ngayxk == null || ngayxk == "")
				ngayxk = this.getDateTime();				
	    	pxkBean.setNgayxuatkho(ngayxk);
	    	
	    	String ngaychot = util.antiSQLInspection(request.getParameter("ngaychot"));
			if (ngaychot == null || ngaychot == "")
				ngaychot = this.getDateTime();				
	    	pxkBean.setNgaychotNV(ngaychot);
	    	
	    	
	    	String noidungxuat = util.antiSQLInspection(request.getParameter("noidungxuat"));
			if (noidungxuat == null)
				noidungxuat = "";	
			session.setAttribute("ndxId", noidungxuat);
	    	pxkBean.setNdxId(noidungxuat);
	    	
	    	
	    	String ddhId = request.getParameter("ddhId");
			if (ddhId == null)
				ddhId = "";				
	    	pxkBean.setDondathangId(ddhId);
	    	
	    	String nccId = request.getParameter("nccId");
			if (nccId == null)
				nccId = "";				
	    	pxkBean.setNccId(nccId);
	    	
	    	String nccTen = request.getParameter("nccTen");
			if (nccTen == null)
				nccTen = "";				
	    	pxkBean.setNccTen(nccTen);
	    	
	    	String trahangId = request.getParameter("trahangId");
			if (trahangId == null)
				trahangId = "";				
	    	pxkBean.setTrahangNccId(trahangId);

	    	
	    	String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";				
	    	pxkBean.setGhichu(ghichu);
	    	
	    	String changeDdh = request.getParameter("changeDdh");
	    	if(changeDdh.equals("true"))
	    	{
	    		pxkBean.changeDdh(); //tinh lai san pham va kho luu khi thay doi don dat hang
	    	}
	    	else
	    	{
		    	//Luu lai san pham
				String[] idhangmua = request.getParameterValues("idhangmua");
				String[] mahangmua = request.getParameterValues("mahangmua");
				String[] diengiai = request.getParameterValues("diengiai");
				String[] soluongTotal = request.getParameterValues("soluongToltal");
				String[] soluongDaXuat = request.getParameterValues("soluongDaXuat");
				String[] soluong = request.getParameterValues("soluong");
				String[] donvi = request.getParameterValues("donvi");
				String[] ghichusp = request.getParameterValues("ghichusp");
				
				List<ISanpham> spList =  new ArrayList<ISanpham>();
				
				if(mahangmua != null)
				{		
					ISanpham sanpham = null;
					String[] param = new String[11];
					int m = 0;
					while(m < mahangmua.length)
					{
						if(idhangmua[m] != "")
						{
							param[0] = idhangmua[m];
							param[1] = mahangmua[m];
							param[2] = diengiai[m];
							param[3] = soluong[m];
							param[4] = "";
							
							sanpham = new Sanpham(param);
							sanpham.setSoluongTotal(soluongTotal[m]);
							sanpham.setSoluongDaXuat(soluongDaXuat[m]);
							
							sanpham.setDVT(donvi[m]);
							sanpham.setGhiChu(ghichusp[m]);
							
							spList.add(sanpham);
						}
						m++;
					}	
				}
				pxkBean.setSpList(spList);
	    	}
			
			String action = request.getParameter("action");
			
			if(action.equals("changendx"))
			{
				pxkBean.setNccId("");
				pxkBean.setTrahangNccId("");
			}
			
			if(action.equals("locchungtu"))
			{
				pxkBean.setSpList(new ArrayList<ISanpham>());
				pxkBean.setDondathangId("");
				pxkBean.setTrahangNccId("");
			}
			
			if(action.equals("save"))
			{	
				if(id == null)
				{
					if(!pxkBean.createPxk())
					{
						System.out.println("Khong the tao moi Phieu Xuat Kho");
	    		    	pxkBean.createRs();
	    		    
	    		    	session.setAttribute("pxkBean", pxkBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoTSCDNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpPhieuxuatkhoTSCDList obj = new ErpPhieuxuatkhoTSCDList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoTSCD.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!pxkBean.updatePxk())
					{
						pxkBean.createRs();
		    		    
						session.setAttribute("pxkBean", pxkBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoTSCDUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpPhieuxuatkhoTSCDList obj = new ErpPhieuxuatkhoTSCDList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoTSCD.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				String nextJSP;
				pxkBean.createRs();
				
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoTSCDNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoTSCDUpdate.jsp";   						
				}
				
				session.setAttribute("pxkBean", pxkBean);
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
