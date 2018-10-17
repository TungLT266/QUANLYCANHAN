package geso.traphaco.distributor.servlets.chuyenkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.chuyenkho.IChuyenkho;
import geso.traphaco.distributor.beans.chuyenkho.IChuyenkhoList;
import geso.traphaco.distributor.beans.chuyenkho.imp.Chuyenkho;
import geso.traphaco.distributor.beans.chuyenkho.imp.ChuyenkhoList;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChuyenkhoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	String userId;
	
    public ChuyenkhoSvl() 
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
    		IChuyenkho ck = new Chuyenkho(pxkId);
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
		    	IChuyenkho ck = new Chuyenkho(pxkId);
		    	ck.setUserId(userId);
	    		if(!ck.DeleteCk())
	    		{
	    			msg = "Không thể xóa chuyển kho, vui lòng thử lại sau";
	    		}
	    	}
	    }
	   	    
	    IChuyenkhoList obj = new ChuyenkhoList();
	    obj.setUserId(userId);
	    
	    obj.init("");
	    obj.setMsg(msg);
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ChuyenKho.jsp";
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
	    	IChuyenkho pxkBean = (IChuyenkho) new Chuyenkho("");
	    	pxkBean.setUserId(userId);
	    	pxkBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("pxkBean", pxkBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ChuyenKhoNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else
	    {
	    	IChuyenkhoList obj = new ChuyenkhoList();

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/ChuyenKho.jsp");	    		    	
	    }
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, IChuyenkhoList obj) 
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
						"from chuyenkho a inner join daidienkinhdoanh b on a.nvbh_fk = b.pk_seq  " +
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
