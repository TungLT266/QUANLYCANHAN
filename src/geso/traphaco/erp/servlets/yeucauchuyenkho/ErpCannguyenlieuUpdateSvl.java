package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpCannguyenlieu;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpCannguyenlieuList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpCannguyenlieu;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpCannguyenlieuList;
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

public class ErpCannguyenlieuUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpCannguyenlieuUpdateSvl() 
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
		    IErpCannguyenlieu lsxBean = new ErpCannguyenlieu(id);
		    lsxBean.setUserId(userId); 
		    
	        String nextJSP = "";
			if(request.getQueryString().indexOf("display") >= 0 ) 
			{
				lsxBean.init( true );
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanNguyenLieuDisplay.jsp";
			}
			else
			{
				lsxBean.init( false );
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanNguyenLieuNew.jsp";
			}
	       
			session.setAttribute("khochuyenIds", lsxBean.getKhoXuatId());
			session.setAttribute("vitriId", lsxBean.getVitrichuyenId() );
			session.setAttribute("isDctk", "1");
	        session.setAttribute("ckBean", lsxBean);
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
			IErpCannguyenlieu lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpCannguyenlieu("");
		    }
		    else
		    {
		    	lsxBean = new ErpCannguyenlieu(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String lydo = util.antiSQLInspection(request.getParameter("lydo"));
		    if(lydo == null)
		    	lydo = "";
		    lsxBean.setLydoyeucau(lydo);
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    String khoxuatId = util.antiSQLInspection(request.getParameter("khoxuatId"));
			if (khoxuatId == null)
				khoxuatId = "";				
			lsxBean.setKhoXuatId(khoxuatId);
			session.setAttribute("isDctk", "1");
 
			String vitriId = util.antiSQLInspection(request.getParameter("vitriId"));
			if (vitriId == null)
				vitriId = "";				
			lsxBean.setVitrichuyenId(vitriId);
			session.setAttribute("vitriId", vitriId);
			
			String kyhieu = util.antiSQLInspection(request.getParameter("kyhieu"));
			if (kyhieu == null)
				kyhieu = "";				
			lsxBean.setKyHieu(kyhieu);
			
			String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null)
				sochungtu = "";				
			lsxBean.setSochungtu(sochungtu);
			
			//BÊN CHUYỂN
			String codoituong = util.antiSQLInspection(request.getParameter("codoituong"));
			if (codoituong == null)
				codoituong = "";				
			lsxBean.setCoDoituong(codoituong);
			
			String loaidoituongId = util.antiSQLInspection(request.getParameter("loaidoituongId"));
			if (loaidoituongId == null)
				loaidoituongId = "";				
			lsxBean.setLoaidoituongId(loaidoituongId);
			
			String doituongId = util.antiSQLInspection(request.getParameter("doituongId"));
			if (doituongId == null)
				doituongId = "";				
			lsxBean.setDoituongId(doituongId);
			
			String sanphamId = util.antiSQLInspection(request.getParameter("sanphamId"));
			if (sanphamId == null)
				sanphamId = "";				
			lsxBean.setSanphamId(sanphamId);
			
			String soloId = util.antiSQLInspection(request.getParameter("soloId"));
			if (soloId == null)
				soloId = "";				
			lsxBean.setSoloId(soloId);
			
			//CHI PHI
			String coChiphi = util.antiSQLInspection(request.getParameter("coChiphi"));
			if (coChiphi == null)
				coChiphi = "";				
			lsxBean.setCochiphi(coChiphi);
			
			String chiphiId = util.antiSQLInspection(request.getParameter("chiphiId"));
			if (chiphiId == null)
				chiphiId = "";				
			lsxBean.setChiphiId(chiphiId);
			
			String[] khott_sp_ct_fk = request.getParameterValues("khott_sp_ct_fk");
			lsxBean.setSpId(khott_sp_ct_fk);
			
			String[] mame = request.getParameterValues("mame");
			lsxBean.setSpMame(mame);
			
			String[] mathung = request.getParameterValues("mathung");
			lsxBean.setSpMathung(mathung);
			
			String[] vitri = request.getParameterValues("vitri");
			lsxBean.setSpVitri(vitri);
			
			String[] maphieu = request.getParameterValues("maphieu");
			lsxBean.setSpMaphieu(maphieu);
			
			String[] phieudt = request.getParameterValues("phieudt"); 
			lsxBean.setSpPhieuDT(phieudt);
			
			String[] phieueo = request.getParameterValues("phieueo"); 
			lsxBean.setSpPhieuEO(phieueo);
			
			String[] marq = request.getParameterValues("marq"); 
			lsxBean.setSpMARQ(marq);
			
			String[] hamluong = request.getParameterValues("hamluong");
			lsxBean.setSpHamluong(hamluong);
			
			String[] hamam = request.getParameterValues("hamam");
			lsxBean.setSpHamam(hamam);
			
			String[] tonkho = request.getParameterValues("tonkho");
			lsxBean.setSpTonkho(tonkho);
			
			String[] soluong = request.getParameterValues("soluong"); 
			lsxBean.setSpSoluong(soluong);
	
		    String action = request.getParameter("action");
			if(action.equals("save"))
			{	
				if(id.length() ==0 )
				{
					if(!lsxBean.createCK())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("ckBean", lsxBean);

	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanNguyenLieuNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpCannguyenlieuList obj = new ErpCannguyenlieuList();
						obj.setUserId(userId);

						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpCanNguyenLieu.jsp");
					}
				}
				else
				{
					if(!lsxBean.updateCK())
					{
						lsxBean.createRs();
						session.setAttribute("ckBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanNguyenLieuNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpCannguyenlieuList obj = new ErpCannguyenlieuList();

						obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanNguyenLieu.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				session.setAttribute("khochuyenIds", khoxuatId);
				/*session.setAttribute("nccchuyenId", nccchuyenId);*/
 
				lsxBean.createRs();
				
				session.setAttribute("ckBean", lsxBean);
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanNguyenLieuNew.jsp";
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
