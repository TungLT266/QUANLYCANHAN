package geso.erp.servlets.dubao;

import geso.dms.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.erp.beans.dubaokinhdoanh.IDubaokinhdoanh;
import geso.erp.beans.dubaokinhdoanh.IDubaokinhdoanhList;
import geso.erp.beans.dubaokinhdoanh.imp.Dubaokinhdoanh;
import geso.erp.beans.dubaokinhdoanh.imp.DubaokinhdoanhList;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DuBaoSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DuBaoSvl() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDubaokinhdoanhList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();	    
	    Utility util = new Utility();	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String dbId = util.getId(querystring);
	    
	    obj = new DubaokinhdoanhList();
	    obj.setCtyId(ctyId);
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(dbId);
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		/*String kq = ChotChuyenkho(nhId);
	    		if(kq.length() > 0)
	    		{
	    			obj.setMsg("Chốt không thành công, lỗi: "+ kq);
	    		}*/
	    	}
	    }
	    obj.setUserId(userId);
	    obj.init("");    
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DuBao.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDubaokinhdoanhList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");
		
		String action = request.getParameter("action");
		
		if(action==null) action="";
		
		Utility util = new Utility();
		
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		 
		if(action.equals("Taomoi"))
		{
			IDubaokinhdoanh dbkdBean = new Dubaokinhdoanh();
			dbkdBean.setCtyId(ctyId);
			
			System.out.println("I am here");
			
			dbkdBean.createRs_New();
			
			session.setAttribute("dbkdBean", dbkdBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DuBaoNew.jsp";
    		
			response.sendRedirect(nextJSP);
		 }
		 else
		 {
			 if(action.equals("view") || action.equals("next") || action.equals("prev"))
		    	{
		    		obj = new DubaokinhdoanhList();
		    		obj.setCtyId(ctyId);
		    		
			    	String search = getSearchQuery(request, obj);
			    	System.out.println("nxtApprSplitting "+Integer.parseInt(request.getParameter("nxtApprSplitting")));
			    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			    	obj.setUserId(userId);
			    	obj.init(search);
			    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			    	session.setAttribute("obj", obj);
			    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DuBao.jsp");	
			    }
		    	else
		    	{
			    	obj = new DubaokinhdoanhList();
			    	obj.setCtyId(ctyId);
			    	
			    	String search = getSearchQuery(request, obj);
			    	obj.init(search);
					obj.setUserId(userId);				
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DuBao.jsp");
		    	}
		 }
	}
	
	private String getSearchQuery(HttpServletRequest request, IDubaokinhdoanhList obj)
	{
		 String query=	"select NV.TEN as TENNV,a.NGAYSUA,a.NGAYTAO,NV.PK_SEQ as MANV, " +
		 				"NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS ,a.TRANGTHAI,a.PK_SEQ, " +
		 				"a.NGAYDUBAO, a.DIENGIAI, c.TEN AS KHO_FK, c.TEN as KHO " +
		 				"from ERP_DUBAO a " +
		 				"left join ERP_KHOTT c on c.pk_seq=a.KHO_FK " +			 		
		 				"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ " +
		 				"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
		 				"where a.CONGTY_FK = " + obj.getCtyId() + " " ;
		Utility util = new Utility();	
		
		String ngaydubao = util.antiSQLInspection(request.getParameter("ngaydubao"));
	    if(ngaydubao == null)
	    	ngaydubao="";
	    obj.setNgaydubao(ngaydubao);
	    
	    String kho = util.antiSQLInspection(request.getParameter("kho"));
	    if(kho==null)
	    	kho="";
	    obj.setKho(kho);
	    
	    
	    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	    if(diengiai == null)
	    	diengiai = "";
	    obj.setDiengiai(diengiai);
	    
		
		if(ngaydubao.length() > 0)
			query += " and a.ngaydubao = '" + ngaydubao + "'";
		
		if(kho.length() > 0)
			query += " and a.kho_fk = '" + kho + "'";
		
	
		if(diengiai.length() > 0)
			query += " and d.DIENGIAI LIKE '%" + diengiai + "%'";
	
		System.out.println("cau query loc la "+query);
		return query;
	}
	
	
	private String Delete(String Id)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			
			String sql="delete from ERP_DUBAOSANPHAM where DUBAO_FK='"+Id+"'";
			System.out.println(sql);
			if(!db.update(sql))
			{
				db.update("rollback");
				return "Không thể xóa! Lỗi: dự báo đã tạo yêu cầu sản xuất không thể xóa"; 
			}
			
			sql="delete from ERP_DUBAO where PK_SEQ='"+Id+"'";
			System.out.println(sql);
			if(!db.update(sql))
			{
				db.update("rollback");
				return "Không thể xóa! Lỗi:  dự báo đã tạo yêu cầu sản xuất không thể xóa"; 
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			return "Không thể xóa! Lỗi: " + e.getMessage(); 
		}
		
	}
	

}
