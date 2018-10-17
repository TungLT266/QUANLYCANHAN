package geso.traphaco.erp.servlets.tinhgiathanh;

import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.danhmucvattu.imp.Danhmucvattu_SP;
import geso.traphaco.erp.beans.lapkehoach.IErpBom;
import geso.traphaco.erp.beans.lapkehoach.IErpBomList;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpBom;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpBomList;
import geso.traphaco.center.beans.thongtinsanpham.IThongtinsanphamList;
import geso.traphaco.center.beans.thongtinsanpham.imp.ThongtinsanphamList;
import geso.traphaco.center.util.Utility;
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


public class ErpTieuhaonvlUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpTieuhaonvlUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpBom thnvlBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    thnvlBean = new ErpBom(id, "");
	    thnvlBean.setId(id);
	    thnvlBean.setUserId(userId);
	    
	    thnvlBean.init();
        session.setAttribute("thnvlBean", thnvlBean);
        
        String nextJSP = "/TraphacoERP/pages/Erp/ErpBomUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpBom thnvlBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	thnvlBean = new ErpBom();
	    }else{
	    	thnvlBean = new ErpBom(id, "");
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		thnvlBean.setUserId(userId);	       
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		thnvlBean.setDiengiai(diengiai);
		
		String spIds = util.antiSQLInspection(request.getParameter("spIds"));
		if (spIds == null)
			spIds = "";
		thnvlBean.setSpId(spIds);
		
		String soluongchuan = util.antiSQLInspection(request.getParameter("soluongchuan"));
		if (soluongchuan == null)
			soluongchuan = "";
		thnvlBean.setSoluongchuan(soluongchuan);
		
		String chophepTT = util.antiSQLInspection(request.getParameter("chophepTT"));
		if (chophepTT == null)
			chophepTT = "0";
		thnvlBean.setChophepTT(chophepTT);

		String[] mavt = request.getParameterValues("mavattu");
		String[] tenvt = request.getParameterValues("tenvattu");
		String[] soluong = request.getParameterValues("soluong");
		String[] mavtTT = request.getParameterValues("mavattuTT");
		String[] tenvtTT = request.getParameterValues("tenvattuTT"); 
		String[] dvtTT = request.getParameterValues("dvtTT");
		String[] tile = request.getParameterValues("tyle");
		
		List<IDanhmucvattu_SP> spList = new ArrayList<IDanhmucvattu_SP>();
		if(mavt != null)
		{	
			for(int m = 0; m < mavt.length; m++)
			{	
				if(mavt[m] != "")
				{	
					if(soluong[m].trim().length() >  0) //chi them nhung san pham co so luong > 0
					{	
						IDanhmucvattu_SP sanpham = null;
						sanpham = new Danhmucvattu_SP();
						
						sanpham.setMaVatTu(mavt[m]);
						sanpham.setTenVatTu(tenvt[m]);
						sanpham.setSoLuong(soluong[m]);
						sanpham.setMaVatTuThayThe(mavtTT[m]);
						sanpham.setTenVatTuThayThe(tenvtTT[m]);
						sanpham.setDvtThayThe(dvtTT[m]);
						sanpham.setTile(tile[m]);
						
						spList.add(sanpham);												
					}
				}				
			}
			
			thnvlBean.setListDanhMuc(spList);	
		}	
		
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(thnvlBean.createBom()))
				{
	 				thnvlBean.createRs();
	 				session.setAttribute("thnvlBean", thnvlBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoERP/pages/Erp/ErpBomNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpBomList obj = new ErpBomList();
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoERP/pages/Erp/ErpBom.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(thnvlBean.updateBom()))
				{
 					thnvlBean.createRs();
	 				session.setAttribute("thnvlBean", thnvlBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoERP/pages/Erp/ErpBomUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpBomList obj = new ErpBomList();
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoERP/pages/Erp/ErpBom.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			thnvlBean.createRs();
			
			session.setAttribute("userId", userId);
			session.setAttribute("thnvlBean", thnvlBean);
			String nextJSP = "/TraphacoERP/pages/Erp/ErpBomNew.jsp";
			
			if(id != null)
			{
				 nextJSP = "/TraphacoERP/pages/Erp/ErpBomUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
