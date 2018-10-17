package geso.traphaco.erp.servlets.banggiaban;

import geso.traphaco.erp.beans.banggiaban.IBanggia_sp;
import geso.traphaco.erp.beans.banggiaban.IErpBanggiabanGiay;
import geso.traphaco.erp.beans.banggiaban.IErpBanggiabanGiayList;
import geso.traphaco.erp.beans.banggiaban.imp.Banggia_sp;
import geso.traphaco.erp.beans.banggiaban.imp.ErpBanggiabanGiay;
import geso.traphaco.erp.beans.banggiaban.imp.ErpBanggiabanGiayList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpBanggiabanGiayUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpBanggiabanGiayUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpBanggiabanGiay bgBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    bgBean = new ErpBanggiabanGiay(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    bgBean.setCongtyId(ctyId);
	    bgBean.setId(id);
	    bgBean.setUserId(userId);
	    
	    bgBean.init();
        session.setAttribute("bgBean", bgBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangGiaBanGiayUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangGiaBanGiayDisplay.jsp";
        }else if(querystring.indexOf("copy") >= 0){
        	bgBean.setId("");
        	bgBean.setTenbanggia("Vui lòng nhập tên bảng giá");
        	bgBean.setTungay(util.getDateTime());
        	bgBean.setDenngay("2020-12-31");
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangGiaBanGiayNew.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpBanggiabanGiay bgBean;
	
		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	bgBean = new ErpBanggiabanGiay();
	    }else{
	    	bgBean = new ErpBanggiabanGiay(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		bgBean.setUserId(userId);	       
		
		String ctyId = (String)session.getAttribute("congtyId");
		bgBean.setCongtyId(ctyId);
		
		String tenbanggia = util.antiSQLInspection(request.getParameter("tenbanggia"));
		if (tenbanggia == null)
			tenbanggia = "";
		bgBean.setTenbanggia(tenbanggia);
		
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		bgBean.setDvkdId(dvkdId);
		
		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		bgBean.setKbhId(kbhId);
		
		String lkhId = util.antiSQLInspection(request.getParameter("lkhId"));
		if (lkhId == null)
			lkhId = "";
		bgBean.setLkhId(lkhId);
		
		String khId = util.antiSQLInspection(request.getParameter("khId"));
		if (khId == null)
			khId = "";
		bgBean.setKhId(khId);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "0";
		bgBean.setTrangthai(trangthai);
	
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "0";
		bgBean.setTungay(tungay);

		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "0";
		bgBean.setDenngay(denngay);
		
		String sudung = util.antiSQLInspection(request.getParameter("sudung"));
		if (sudung == null)
			sudung = "0";
		bgBean.setSudung(sudung);
		
		 String[] lspIds = request.getParameterValues("loaisanpham");
		    String lspId = "";
		    if(lspIds != null)
		    {
		    	for(int i = 0; i < lspIds.length; i++)
		    		lspId += lspIds[i] + ",";
		    	if(lspId.length() > 0)
		    		lspId = lspId.substring(0, lspId.length() - 1);
		    	bgBean.setLspstr(lspId);
		    }
		    
		    

		String[] khApDung = request.getParameterValues("khApDung");
		if(khApDung != null)
		{
			String khApDungId = "";
			for(int i = 0; i < khApDung.length; i++)
				khApDungId += khApDung[i] + ",";
			
			if(khApDungId.trim().length() > 0)
			{
				khApDungId = khApDungId.substring(0, khApDungId.length() - 1);
				bgBean.setKhApDungIds(khApDungId);
			}
		}
		System.out.println("---KHACH HANG AP DUNG: " + bgBean.getKhApDungIds() );
		
		String[] hopdong = request.getParameterValues("hopdong");
		String[] idsanpham = request.getParameterValues("idsanpham");
		String[] masanpham = request.getParameterValues("masanpham");
		String[] tensanpham = request.getParameterValues("tensanpham");
		String[] tenNewsanpham = request.getParameterValues("tenNewsanpham");
		String[] giaban = request.getParameterValues("giaban");
		String[] giamoi = request.getParameterValues("giamoi");
		String[] donvi = request.getParameterValues("donvi");
		String[] dvdl = request.getParameterValues("dvdlId");
		
		String[] chonban = request.getParameterValues("chonban");
		String spChonbanIds = "";
		if(chonban != null)
		{
			for(int i = 0; i < chonban.length; i++)
			{
				spChonbanIds += chonban[i] + ",";
			}
			
			if(spChonbanIds.trim().length() > 0)
			{
				spChonbanIds = spChonbanIds.substring(0, spChonbanIds.length() - 1);
			}
		}
		
		//System.out.println("___SP Chon Ban: " + spChonbanIds);
		List<IBanggia_sp> spList = new ArrayList<IBanggia_sp>();
		if(hopdong != null)
		{
			for(int i = 0; i < hopdong.length; i++)
			{
				IBanggia_sp sp = new Banggia_sp();
				sp.setHopdong(hopdong[i]);
				sp.setIdsp(idsanpham[i]);
				sp.setMasp(masanpham[i]);
				sp.setTensp(tensanpham[i]);
				if(tenNewsanpham != null)
					sp.setTenNew(tenNewsanpham[i]);
				else
					sp.setTenNew(tensanpham[i]);
				
				sp.setGiaban(giaban[i]);
				sp.setGiabanNew(giamoi[i]);
				sp.setDonvi("");
				sp.setDvdlId(dvdl[i]);
				
				System.out.println(sp.getDvdlId());
				
				
				if(spChonbanIds.indexOf(idsanpham[i]) >= 0)
					sp.setChonban("1");
				
				spList.add(sp);
			}
		}
		
		bgBean.setSpList(spList);
		
		String action = request.getParameter("action");
		if(action.equals("save"))
		{
			if(id == null)
			{
	 			if (!(bgBean.createBanggia()))
				{
	 				bgBean.createRs();
	 				session.setAttribute("bgBean", bgBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangGiaBanGiayNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpBanggiabanGiayList obj = new ErpBanggiabanGiayList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
					bgBean.DbClose();
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangGiaBanGiay.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				if (!(bgBean.updateBanggia()))
				{
					bgBean.createRs();
	 				session.setAttribute("bgBean", bgBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangGiaBanGiayUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpBanggiabanGiayList obj = new ErpBanggiabanGiayList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
					bgBean.DbClose();
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangGiaBanGiay.jsp";
					response.sendRedirect(nextJSP);
				}
			}
	    } 
		else
		{
			
			
			
			if(action.equals("changeKhachHang") || action.equals("reload_sp") || action.equals("change") ){
				bgBean.setSpList(new ArrayList<IBanggia_sp>());
		  
			}
			bgBean.createRs();
			
			
			session.setAttribute("userId", userId);
			session.setAttribute("bgBean", bgBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangGiaBanGiayNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangGiaBanGiayUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
