package geso.traphaco.erp.servlets.marquette;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.marquette.IMarquette;
import geso.traphaco.erp.beans.marquette.IMarquetteUpdate;
import geso.traphaco.erp.beans.marquette.imp.Marquette;
import geso.traphaco.erp.beans.marquette.imp.MarquetteUpdate;



@WebServlet("/MarquetteUpdateSvl")
public class MarquetteUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

	public MarquetteUpdateSvl() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	

	    IMarquetteUpdate obj = new MarquetteUpdate(id);	    
	    
        obj.setUserId(userId);
        session.setAttribute("obj", obj);
        String nextJSP = "/TraphacoHYERP/pages/Erp/MarquetteUpdate.jsp";
        response.sendRedirect(nextJSP);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IMarquetteUpdate obj;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  
	    	
	    	obj = new MarquetteUpdate("");
	    }else{
	    	obj = new MarquetteUpdate(id);
	    }
	    
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		obj.setUserId(userId);
    	System.out.println("[MarquetteUpdateSvl.doPost] userId = " + obj.getUserId());
	    
    	String ten = util.antiSQLInspection(request.getParameter("ten"));
		if (ten == null)
			ten = "";				
    	obj.setTen(ten.trim());
    	System.out.println("[MarquetteUpdateSvl.doPost] ten = " + obj.getTen());
    	
    	String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai.trim());
    	System.out.println("[MarquetteUpdateSvl.doPost] diengiai = " + obj.getDiengiai());
		
		String maspId = util.antiSQLInspection(request.getParameter("masp"));
    	if ( maspId == null) { maspId = ""; }
    	obj.setspId(maspId);
    	System.out.println("[MarquetteUpdateSvl.doPost] idsanpham = " + obj.getspId());

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	obj.setTrangthai(trangthai.trim());
    	System.out.println("[MarquetteUpdateSvl.doPost] trangthai = " + obj.getTrangthai());
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";
    	obj.setTungay(tungay);
    	System.out.println("[MarquetteUpdateSvl.doPost] tungay = " + obj.getTungay());
    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";
    	obj.setDenngay(denngay);
    	System.out.println("[MarquetteUpdateSvl.doPost] denngay = " + obj.getDenngay());
		String ngaysua = getDateTime();
    	obj.setNgaysua(ngaysua);
    	
    	String sodangky = util.antiSQLInspection(request.getParameter("sodangky"));
    	if (sodangky == null)
    		sodangky = "";
    	obj.setSodangky(sodangky);
    	
    	String quycach = util.antiSQLInspection(request.getParameter("quycach"));
    	if (quycach == null)
    		quycach = "";
    	obj.setquycach(quycach);
		
		String nguoisua = userId;
		obj.setNguoisua(nguoisua);
    	
 		
 		String action = request.getParameter("action");
 		System.out.println("[MarquetteUpdateSvl.doPost] idmaket = " + obj.getId());
		if(action.equals("save"))
		{
			if ( id.length()==0) {
				if (!(obj.create())){				
					session.setAttribute("obj", obj);
					obj.setUserId(userId);
					
					String nextJSP =  "/TraphacoHYERP/pages/Erp/MarquetteUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IMarquette obj1 = new Marquette();
					obj1.setUserId(userId);
					obj1.Init();
					session.setAttribute("obj", obj1);
				    String nextJSP = "/TraphacoHYERP/pages/Erp/MarquetteNew.jsp";
					response.sendRedirect(nextJSP);		    			    									
				}
				
			} else {
				if (!(obj.update())){			
					session.setAttribute("obj", obj);
					obj.setUserId(userId);
					String nextJSP = "/TraphacoHYERP/pages/Erp/MarquetteUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IMarquette obj1 = new Marquette();
					obj1.setUserId(userId);
					obj1.Init();
					session.setAttribute("obj", obj1);
				    String nextJSP = "/TraphacoHYERP/pages/Erp/MarquetteNew.jsp";
					response.sendRedirect(nextJSP);		    			    									
				}
			}
		} 
		else 
		{
			obj.setUserId(userId);
			session.setAttribute("obj", obj);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/TraphacoHYERP/pages/Erp/MarquetteNew.jsp";
			}else{
				nextJSP = "/TraphacoHYERP/pages/Erp/MarquetteUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
			
		}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
