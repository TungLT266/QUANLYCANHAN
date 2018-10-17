package geso.traphaco.distributor.servlets.chuyenkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.chuyenkho.ITrahang;
import geso.traphaco.distributor.beans.chuyenkho.ITrahangList;
import geso.traphaco.distributor.beans.chuyenkho.imp.Trahang;
import geso.traphaco.distributor.beans.chuyenkho.imp.TrahangList;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TrahangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	String userId;
	
    public TrahangSvl() 
    {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    if (userId.length() == 0 )
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String pxkId = util.getId(querystring);

	    String msg = "";
	    if(action.equals("chotphieu"))
	    {
    		ITrahang ck = new Trahang(pxkId);
    		ck.setUserId(userId);
    		if(!ck.ChotCk())
    		{
    			msg = "Không thể chốt chuyển kho, vui lòng thử lại sau";
    		}
	    }
	    else
	    {
	    	if(action.equals("delete"))
	    	{
		    	ITrahang ck = new Trahang(pxkId);
		    	ck.setUserId(userId);
	    		if(!ck.DeleteCk())
	    		{
	    			msg = "Không thể xóa trả kho, vui lòng thử lại sau";
	    		}
	    	}
	    }
	   	    
	    ITrahangList obj = new TrahangList();
	    obj.setUserId(userId);
	    
	    obj.init("");
	    obj.setMsg(msg);
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/TraHang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    userId = request.getParameter("userId");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println("Action la: " + action); 
	    
	    if (action.equals("Tao moi"))
	    {
	    	// Empty Bean for distributor
	    	ITrahang pxkBean = (ITrahang) new Trahang("");
	    	pxkBean.setUserId(userId);
	    	pxkBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("pxkBean", pxkBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/TraHangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else
	    {
	    	ITrahangList obj = new TrahangList();

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/TraHang.jsp");	    		    	
	    }
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, ITrahangList obj) 
	{	
		String nppId = request.getParameter("nppId");
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String nvbhId = request.getParameter("nvbhId");
    	if ( nvbhId == null)
    		nvbhId = "";
    	obj.setNvbhId(nvbhId);
    	
    	String trangthai = request.getParameter("trangthai");
    	if ( trangthai == null)
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String tungay = request.getParameter("tungay");
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = request.getParameter("denngay");
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	   	
    	String query = "select a.pk_seq, a.trangthai, a.ngaychuyen, b.ten as ddkdTen, a.ngaytao, c.ten as nguoitao, a.ngaysua, d.ten as nguoisua  " +
						"from TRAHANG a inner join daidienkinhdoanh b on a.nvbh_fk = b.pk_seq  " +
							"inner join nhanvien c on a.nguoitao = c.pk_seq " +
							"inner join nhanvien d on a.nguoisua = d.pk_seq  " +
						"where a.npp_fk = '" + nppId + "'  ";
    	
    	if(nvbhId.trim().length() > 0)
    		query += " and b.pk_seq = '" + nvbhId + "' ";
    	
    	if(trangthai.trim().length() > 0)
    		query += " and a.trangthai = '" + trangthai + "' ";
    	
    	if(tungay.trim().length() > 0)
    		query += " and a.ngaychuyen >= '" + tungay + "'  ";
    	
    	if(denngay.trim().length() > 0)
    		query += " and a.ngaychuyen <= '" + denngay + "'  ";
						
    	query += " order by a.pk_seq desc ";
    	return query;
	}
	
}
