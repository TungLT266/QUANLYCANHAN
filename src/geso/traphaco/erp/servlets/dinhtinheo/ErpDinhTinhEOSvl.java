package geso.traphaco.erp.servlets.dinhtinheo;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.dinhtinheo.IDinhTinhEO;
import geso.traphaco.erp.beans.dinhtinheo.IDinhTinhEOList;
import geso.traphaco.erp.beans.dinhtinheo.imp.DinhTinhEO;
import geso.traphaco.erp.beans.dinhtinheo.imp.DinhTinhEOList;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDinhTinhEOSvl extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
    public ErpDinhTinhEOSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDinhTinhEOList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String Id = util.getId(querystring);
	    obj = new DinhTinhEOList();
	  
	    if (action.equals("delete"))
	    {	
	    	DinhTinhEO detail = new DinhTinhEO();
	    	detail.setId(Id);
	    	boolean check = detail.Delete();
	    	
	    	if(check == false)
	    		obj.setMsg(detail.getMsg());
	    } 
	    else if(action.equals("chot"))
    	{
	    	DinhTinhEO detail = new DinhTinhEO();
	    	detail.setId(Id);
	    	boolean check = detail.Chot();
	    	
	    	if(check == false)
	    		obj.setMsg(detail.getMsg());
    	}
	    else if(action.equals("hoantat"))
		{
		}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDinhTinhEO.jsp";
		response.sendRedirect(nextJSP);
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}

		IDinhTinhEOList obj = new DinhTinhEOList();
		
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		if(action.equals("Tao moi"))
		{
			IDinhTinhEO lsxBean = new DinhTinhEO();
			lsxBean.setUserId(userId);
			lsxBean.createRs();

			session.setAttribute("obj", lsxBean);
			obj.DBclose();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDinhTinhEONew.jsp";
			response.sendRedirect(nextJSP);
		}
		else 
		{
			if(action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDinhTinhEO.jsp";

				response.sendRedirect(nextJSP);
			}
			else
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);		    	
				obj.init(search);				
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDinhTinhEO.jsp";

				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IDinhTinhEOList obj)
	{
		String trangthai = request.getParameter("trangthai");
		String sophieu = request.getParameter("sophieu");
		String khochuyenId = request.getParameter("khochuyenId");
		String loai = request.getParameter("loai");
		String solo = request.getParameter("solo");
		
		if(trangthai == null){
			trangthai = "";
		}
		obj.setTrangthai(trangthai);
		
		if(sophieu == null){
			sophieu = "";
		}
		obj.setSophieu(sophieu);
		
		if(khochuyenId == null){
			khochuyenId = "";
		}
		obj.setKhoId(khochuyenId);
		
		if(loai == null){
			loai = "";
		}
		obj.setLoai(loai);
		
		if(solo == null){
			solo = "";
		}
		obj.setSolo(solo);
		
		return "";
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public static void main(String[] args) {
		DinhTinhEO detail = new DinhTinhEO();
    	detail.setId("100382");
    	detail.MoChot();
    	System.out.println(detail.getMsg());
	}
}
