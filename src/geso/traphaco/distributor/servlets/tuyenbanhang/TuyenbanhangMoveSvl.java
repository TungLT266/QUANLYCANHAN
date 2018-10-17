package geso.traphaco.distributor.servlets.tuyenbanhang;

import geso.traphaco.distributor.beans.tuyenbanhang.*;
import geso.traphaco.distributor.beans.tuyenbanhang.imp.*;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TuyenbanhangMoveSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
   
    public TuyenbanhangMoveSvl() {
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
		}else{
		
		ITuyenbanhang tbhBean;
		PrintWriter out; 
		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String id = util.getId(querystring);  	
	    tbhBean = new Tuyenbanhang(id);
	    tbhBean.setUserId(userId);
	    tbhBean.init();
	    tbhBean.createNlvList();
	    tbhBean.createDiengiaiList();
	    
        session.setAttribute("tbhBean", tbhBean);
        String nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHangMove.jsp";
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
		}else{
		
		ITuyenbanhang tbhBean;
		PrintWriter out;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

		
		out = response.getWriter();
		
	    String id = request.getParameter("id");
	    tbhBean = new Tuyenbanhang(id);
	    tbhBean.setId(id);
	    
	    
	    
		userId = request.getParameter("userId");
		tbhBean.setUserId(userId);
	    
		String nppId = request.getParameter("nppId");
		if (nppId == null)
			nppId = "";
		tbhBean.setNppId(nppId);
		
		String ddkdTen = request.getParameter("ddkdTen");
		if (ddkdTen == null)
			ddkdTen = "";				
    	tbhBean.setDdkd(ddkdTen);
    	
    	String diengiai = request.getParameter("diengiai");
    	if (diengiai == null)
    		diengiai = "";
    	tbhBean.setDiengiai(diengiai);
    	
    	String nlv = request.getParameter("ngaylamviec");
		if (nlv == null)
			nlv = "";
		tbhBean.setNgaylamviec(nlv);
		
    	String ddkdNewId = request.getParameter("ddkdNewTen");
		if (ddkdNewId == null)
			ddkdNewId = "";				
    	tbhBean.setDdkdNewId(ddkdNewId);
    	
    	String diengiaiNew = request.getParameter("tbhNewTen");
    	if (diengiaiNew == null)
    		diengiaiNew = "";
    	tbhBean.setDiengiaiNew(diengiaiNew);
    	
    	String nlvNew = request.getParameter("nlvNewTen");
		if (nlvNew == null)
			nlvNew = "";
		tbhBean.setNlvNew(nlvNew);
		
		boolean error = false;
		
		if (ddkdNewId.trim().length()== 0){
			tbhBean.setMessage("Vui Long Chon Dai Dien Kinh Doanh New");
			error = true;
		}
		
		if (diengiaiNew.trim().length()== 0){
			tbhBean.setMessage("Vui Long Nhap Dien Giai Tuyen Ban Hang New");
			error = true;
		}
		
		if (nlvNew.trim().length()== 0){
			tbhBean.setMessage("Vui Long Chon Ngay Lam Viec New");
			error = true;		
		}
		
 		String action = request.getParameter("action");
 		
	    String idNew = "";
	    dbutils db = new dbutils();
	    //String sql ="select pk_seq from tuyenbanhang where diengiai='" + diengiaiNew + "' and ngaylamviec='" + nlvNew + "' and ddkd_fk='" + ddkdNewId + "'";
	    String sql ="select pk_seq from tuyenbanhang where ngaylamviec='" + nlvNew + "' and ddkd_fk='" + ddkdNewId + "'";
	    ResultSet rs = db.get(sql);
	    System.out.println("chuyen tuyen:"+ sql);
	    try {
			//while(rs.next())
	    	  rs.next();
				idNew = rs.getString("pk_seq");
		} 
	    catch(Exception e) {}
	      
		if(action.equals("save"))
		{
			String[] khIds = request.getParameterValues("khIds");
			String[] tansoIds = request.getParameterValues("tansoList");	
			String[] sott = request.getParameterValues("thutu");
			if (!(tbhBean.MoveTbh(id, idNew, khIds, tansoIds,sott)))
			{
				tbhBean.setUserId(userId);
				tbhBean.init();
				//tbhBean.createNlvList();
		    	//tbhBean.createDiengiaiList();
				session.setAttribute("tbhBean", tbhBean);
				String nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHangMove.jsp";
				response.sendRedirect(nextJSP);
			}
			else{
				ITuyenbanhangList obj = new TuyenbanhangList();
				obj.setUserId(userId);
				obj.init("");
				session.setAttribute("obj", obj);
					
				String nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHang.jsp";
				response.sendRedirect(nextJSP);			    			    													
			}
		}
		else{
		
			tbhBean.setUserId(userId);
			tbhBean.createRS();
			tbhBean.createNlvList();
			tbhBean.createDiengiaiList();
			
			session.setAttribute("tbhBean", tbhBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHangMove.jsp";   						
			response.sendRedirect(nextJSP);		
		}
	}
	}

}
