package geso.traphaco.erp.servlets.tonkhoantoan;


import geso.traphaco.erp.beans.tonkhoantoan.IErpTonkhoantoan;
import geso.traphaco.erp.beans.tonkhoantoan.imp.ErpTonkhoantoan;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpTonkhoantoanUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpTonkhoantoanUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpTonkhoantoan tkatBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	       
	    tkatBean = new ErpTonkhoantoan();
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    tkatBean.setCongtyId(ctyId);
	    tkatBean.setUserId(userId);
	    
	    tkatBean.init();
        session.setAttribute("tkatBean", tkatBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTonkhoantoanUpdate.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpTonkhoantoan tkatBean;
	
		Utility util = new Utility();
    	tkatBean = new ErpTonkhoantoan();
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		tkatBean.setUserId(userId);	       
		
		String ctyId = (String)session.getAttribute("congtyId");
		tkatBean.setCongtyId(ctyId);
		
		String[] khoIds = request.getParameterValues("khoIds");
		String clId = "";
		if (khoIds != null)
		{
			for( int i = 0; i < khoIds.length; i++ )
				clId += khoIds[i] + ",";
			if( clId.trim().length() > 0 )
				clId = clId.substring(0, clId.length() - 1);
		}
		tkatBean.setClId(clId);
		
		String[] sp_1 = request.getParameterValues("spId_1");
		tkatBean.setSp_1(sp_1);
		
		String[] sp_2 = request.getParameterValues("spId_2");
		tkatBean.setSp_2(sp_2);
		
		String[] sp_3 = request.getParameterValues("spId_3");
		tkatBean.setSp_3(sp_3);
				
		String[] tkan_1 = request.getParameterValues("tkat_1");
		tkatBean.setTkat_1(tkan_1);

		String[] tkan_2 = request.getParameterValues("tkat_2");
		tkatBean.setTkat_2(tkan_2);

		String[] tkan_3 = request.getParameterValues("tkat_3");
		tkatBean.setTkat_3(tkan_3);

		String action = request.getParameter("action");
		if(action.equals("save")){
			tkatBean.save();
		}
		
	    tkatBean.init();
        session.setAttribute("tkatBean", tkatBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTonkhoantoanUpdate.jsp";
        response.sendRedirect(nextJSP);
	}
	
}
