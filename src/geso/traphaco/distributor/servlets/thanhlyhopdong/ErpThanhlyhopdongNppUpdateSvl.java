package geso.traphaco.distributor.servlets.thanhlyhopdong;

import geso.traphaco.distributor.beans.thanhlyhopdong.IErpThanhlyhopdongNpp;
import geso.traphaco.distributor.beans.thanhlyhopdong.IErpThanhlyhopdongNppList;
import geso.traphaco.distributor.beans.thanhlyhopdong.imp.ErpThanhlyhopdongNpp;
import geso.traphaco.distributor.beans.thanhlyhopdong.imp.ErpThanhlyhopdongNppList;
import geso.traphaco.distributor.util.Utility;

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

import oracle.sql.LxMetaData;

public class ErpThanhlyhopdongNppUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpThanhlyhopdongNppUpdateSvl() 
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
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 
		    
		    String id = util.getId(querystring);  	
		    IErpThanhlyhopdongNpp lsxBean = new ErpThanhlyhopdongNpp(id);
		    lsxBean.setUserId(userId); 
		    lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
		    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kenhId", lsxBean.getKbhId());
			session.setAttribute("khoxuat", lsxBean.getKhoNhapId());
			session.setAttribute("nppId", lsxBean.getNppId());
    		
    		if(querystring.contains("display"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThanhLyHopDongNppDisplay.jsp";
    		}
    		else if(querystring.contains("convert"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThanhlyhopDongNppDisplay.jsp";
    		}
    		else
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThanhlyhopDongNppUpdate.jsp";
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
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
			
			this.out = response.getWriter();
			
			IErpThanhlyhopdongNpp lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpThanhlyhopdongNpp("");
		    }
		    else
		    {
		    	lsxBean = new ErpThanhlyhopdongNpp(id);
		    }
	
		    lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
		    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
		    
		    lsxBean.setUserId(userId);
		    String action = request.getParameter("action");
		    String idhd = util.antiSQLInspection(request.getParameter("Mahd"));
		    System.out.println("IDhd "+idhd);
		    if(idhd == null )
		    	idhd = "";
		    lsxBean.setMahopdong(idhd);
		    System.out.println("ma hop dong "+idhd);
		    String ngaytl = util.antiSQLInspection(request.getParameter("ngaytl"));
		    if(ngaytl == null  )
		    	ngaytl = "";
		    lsxBean.setNgaythanhly(ngaytl);		
		    
		    String ngaytlkt = util.antiSQLInspection(request.getParameter("ngaytlkt"));
		    if(ngaytlkt == null  )
		    	ngaytlkt = "";
		    lsxBean.setNgaythanhlykt(ngaytlkt);
		    String loaithanhly = util.antiSQLInspection(request.getParameter("loaithanhly"));
		    System.out.println("loai thanh ly "+loaithanhly);
		    if(loaithanhly == null)
		    	loaithanhly = "0";
		    lsxBean.setLoaithanhly(Integer.parseInt(loaithanhly));
		    String mahoadon[] = request.getParameterValues("mahoadon");
			String loaidonhang = request.getParameter("loaidonhang");
			if (loaidonhang == null)
				loaidonhang = "0";				
			lsxBean.setLoaidonhang(loaidonhang);
		    if(mahoadon != null)
		    {
		    	System.out.println("mahoadon "+mahoadon[0]);
		    	lsxBean.setMahoadon(mahoadon);
		    }
		    if(idhd.length() > 0 && !action.equals("save"))
		    {
		    		
		    		IErpThanhlyhopdongNpp lsxBean1 = new ErpThanhlyhopdongNpp(idhd);
					lsxBean1.setUserId(userId); 
					lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
				    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
				    
					lsxBean1.setLoaithanhly(Integer.parseInt(loaithanhly));
					lsxBean1.setNgaythanhly(ngaytl);
					lsxBean1.setNgaythanhlykt(ngaytlkt);
					lsxBean1.setLoaidonhang(loaidonhang);
					 if(mahoadon != null)
					  {
					    	
					    	lsxBean1.setMahoadon(mahoadon);
					  }
					lsxBean1.setMahopdong(idhd);
		    		lsxBean1.initnew();
		    		session.setAttribute("dvkdId", lsxBean1.getDvkdId());
					session.setAttribute("kenhId", lsxBean1.getKbhId());
					session.setAttribute("khoxuat", lsxBean1.getKhoNhapId());
					session.setAttribute("nppId", lsxBean1.getNppId());
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThanhLyHopDongNppNew.jsp";
					session.setAttribute("lsxBean", lsxBean1);
				    response.sendRedirect(nextJSP);
		    }
		    else
		    {
		    
		    
		    String tungay = util.antiSQLInspection(request.getParameter("hopdong_tungay"));
		    if(tungay == null || tungay.trim().length() <= 0 )
		    	tungay = "";
		    lsxBean.setTungay(tungay);
		    
		    String denngay = util.antiSQLInspection(request.getParameter("hopdong_denngay"));
		    if(denngay == null || denngay.trim().length() <= 0)
		    	denngay = "";
		    lsxBean.setDenngay(denngay);
		    	    
		    String mahopdong = util.antiSQLInspection(request.getParameter("mahopdong"));
		    if(mahopdong == null)
		    	mahopdong = "";
		    lsxBean.setMahopdong(mahopdong);
		    
		   
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		   
		    
		  
		    System.out.println("loai thanh ly "+loaithanhly);
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
			
			String gsbhId = util.antiSQLInspection(request.getParameter("gsbhId"));
			if (gsbhId == null)
				gsbhId = "";				
			lsxBean.setGsbhId(gsbhId);
			
			String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
			if (ddkdId == null)
				ddkdId = "";				
			lsxBean.setDdkdId(ddkdId);
			
			String khId = util.antiSQLInspection(request.getParameter("khId"));
			if (khId == null)
				khId = "";				
			lsxBean.setKhId(khId);
			
			String vat = util.antiSQLInspection(request.getParameter("ptVat"));
			if (vat == null)
				vat = "";				
			lsxBean.setVat(vat);
			
		
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
			
			String hopdongId = util.antiSQLInspection(request.getParameter("hopdongId"));
			if (hopdongId == null)
				hopdongId = "";				
			lsxBean.setHopdongId(hopdongId);
		
			if(action.equals("save"))
			{	
					lsxBean.setMahopdong(idhd);
					boolean kq = lsxBean.createNK();
					if(!kq)
					{
						lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
					    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
					    
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThanhLyHopDongNppNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpThanhlyhopdongNppList obj = new ErpThanhlyhopdongNppList();
						obj.setLoaidonhang(loaidonhang);
						obj.setTdv_dangnhap_id(tdv_dangnhap_id);
						obj.setNpp_duocchon_id(npp_duocchon_id);					    
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThanhLyHopDongNpp.jsp";	
			    		response.sendRedirect(nextJSP);
					}
				
			}
			else
				{
					System.out.println("vao day ");
					lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
					lsxBean.setNpp_duocchon_id(npp_duocchon_id);	
					lsxBean.createRs();
					
					session.setAttribute("dvkdId", lsxBean.getDvkdId());
					session.setAttribute("kenhId", lsxBean.getKbhId());
					session.setAttribute("khoxuat", lsxBean.getKhoNhapId());
					session.setAttribute("nppId", lsxBean.getNppId());
					
					session.setAttribute("lsxBean", lsxBean);
					
					String nextJSP = "";
					
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThanhLyHopDongNppNew.jsp";
					if(id != null)
						nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThanhLyHopDongNppUpdate.jsp";
					
					response.sendRedirect(nextJSP);
				}
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
