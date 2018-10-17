package geso.traphaco.erp.servlets.lapngansach;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lapngansach.IDubaokinhdoanhNam_Giay;
import geso.traphaco.erp.beans.lapngansach.IDubaokinhdoanhNam_Giay_List;
import geso.traphaco.erp.beans.lapngansach.imp.DubaokinhdoanhNam_Giay;
import geso.traphaco.erp.beans.lapngansach.imp.DubaokinhdoanhNam_Giay_List;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DubaoNam_GiaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DubaoNam_GiaySvl() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDubaokinhdoanhNam_Giay_List obj;
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
	    
	    obj = new DubaokinhdoanhNam_Giay_List();
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
		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DuBaoNam_Giay.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDubaokinhdoanhNam_Giay_List obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");
		
		String action = request.getParameter("action");
		
		if(action==null) action="";
		
		Utility util = new Utility();
		
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		 
		String copyId = util.antiSQLInspection(request.getParameter("copyId"));

	 	obj = new DubaokinhdoanhNam_Giay_List();		    		
		obj.setCtyId(ctyId);

		if(action.equals("copy")){
			if(copyId.length() > 0){
				obj.setCopyId(copyId);
				obj.Copy();
			}
		}
		
		
		if(action.equals("Taomoi"))
		{
			IDubaokinhdoanhNam_Giay dbkdBean = new DubaokinhdoanhNam_Giay();
			dbkdBean.setCtyId(ctyId);
			
			System.out.println("I am here");
			
			dbkdBean.createRs_New();
			
			session.setAttribute("dbkdBean", dbkdBean);
			obj.close();
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DubaoNam_Giay_New.jsp";
    		
			response.sendRedirect(nextJSP);
		 }
		 else
		 {
			 if(action.equals("view") || action.equals("next") || action.equals("prev"))
		    	{
		    		
			    	String search = getSearchQuery(request, obj);
			    	System.out.println("nxtApprSplitting "+Integer.parseInt(request.getParameter("nxtApprSplitting")));
			    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			    	obj.setUserId(userId);
			    	obj.init(search);
			    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			    	session.setAttribute("obj", obj);
			    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DuBaoNam_Giay.jsp");	
			    }
		    	else
		    	{
			    	
			    	String search = getSearchQuery(request, obj);
			    	obj.init(search);
					obj.setUserId(userId);				
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DuBaoNam_Giay.jsp");
		    	}
		 }
	}
	
	private String getSearchQuery(HttpServletRequest request, IDubaokinhdoanhNam_Giay_List obj)
	{
		String query=	"select NV.TEN as TENNV,a.NGAYSUA,a.NGAYTAO, b.DONVIKINHDOANH AS DVKD, NV.PK_SEQ as MANV, " +
			 			"NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS ,a.TRANGTHAI,a.PK_SEQ, " +
			 			"a.NAM, a.DIENGIAI , c.TEN AS KHO_FK, c.TEN as KHO,  LNS.DIENGIAI AS NGANSACH " +
			 			"from ERP_LAPNGANSACH_DUBAO a " +
			 			"inner join ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = a.LAPNGANSACH_FK " +
			 			"inner join DONVIKINHDOANH b ON b.PK_SEQ = a.DVKD_FK " +
			 			"left join ERP_KHOTT c on c.pk_seq=a.KHO_FK " +			 		
			 			"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ " +
			 			"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
			 			"WHERE a.CONGTY_FK = " + obj.getCtyId() + " ";
		
		Utility util = new Utility();	
		
		String nsId = util.antiSQLInspection(request.getParameter("nsId"));
	    if(nsId == null)
	    	nsId = "";
	    obj.setNsId(nsId);
	    	    
	    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	    if(diengiai == null)
	    	diengiai = "";
	    obj.setDiengiai(diengiai);
	    

	    String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
	    if(dvkdId == null)
	    	dvkdId = "";
	    obj.setDvkdId(dvkdId);

		if(nsId.length() > 0)
			query += " and LNS.PK_SEQ = '" + nsId + "'";
			
		if(diengiai.length() > 0)
			query += " and d.DIENGIAI LIKE '%" + diengiai + "%'";
	
		if(dvkdId.length() > 0)
			query += " and a.DVKD_FK = " + dvkdId + " ";
		
		System.out.println("cau query loc la "+query);
		return query;
	}
	
	
	private String Delete(String Id)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			
			String sql="delete from ERP_LAPNGANSACH_DUBAOSANPHAM where DUBAO_FK='"+Id+"'";
			System.out.println(sql);
			if(!db.update(sql))
			{
				db.update("rollback");
				return "Không thể xóa! Lỗi: dự báo đã tạo yêu cầu sản xuất không thể xóa"; 
			}
			
			sql="delete from ERP_LAPNGANSACH_DUBAO where PK_SEQ='"+Id+"'";
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
