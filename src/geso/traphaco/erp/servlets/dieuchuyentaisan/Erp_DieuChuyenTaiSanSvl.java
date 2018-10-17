package geso.traphaco.erp.servlets.dieuchuyentaisan;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.dieuchuyentaisan.IErp_DieuChuyenTaiSan;
import geso.traphaco.erp.beans.dieuchuyentaisan.IErp_DieuChuyenTaiSanList;
import geso.traphaco.erp.beans.dieuchuyentaisan.imp.Erp_DieuChuyenTaiSan;
import geso.traphaco.erp.beans.dieuchuyentaisan.imp.Erp_DieuChuyenTaiSanList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_DieuChuyenTaiSanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public Erp_DieuChuyenTaiSanSvl() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		HttpSession session = request.getSession();	   	    
	    Utility util = new Utility();	   
	    session.setAttribute("util", util);
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();	   
	    out = response.getWriter();
	    
	    String userId;
	 	userId= (String)session.getAttribute("userId");
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);
	    
	    IErp_DieuChuyenTaiSanList obj = new Erp_DieuChuyenTaiSanList();
	    obj.setCtyId((String) session.getAttribute("congtyId"));
	   
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    out.println("userId la :"+ userId);
	   
	    String id = util.getId(querystring);
    	if (id == null)
    		id = "";
	    
    	String action = util.getAction(querystring);
    	if (action == null)
    		action = "";
    	
    	if (action.equals("chot"))
		{
    		String msg=chot(id);
    		if(msg.length()>0)
    		{
    			obj.setMsg(msg);
    		}
		}
    	
    	if (action.equals("unchot"))
		{
    		String msg=unchot(id);
    		if(msg.length()>0)
    		{
    			obj.setMsg(msg);
    		}
		}

		if (action.equals("delete"))
		{
			dbutils db= new dbutils();
    		String query= "Update erp_dieuchuyentaisan set trangthai =2 where pk_seq= "+id ;
    		db.update(query);
    		db.shutDown();
		}
		
    	obj.createRs();
		obj.init("");		
		
		session.setAttribute("obj", obj);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DieuChuyenTaiSan.jsp");	
	}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
	   
//	    obj.setCtyId((String) session.getAttribute("congtyId"));
	    String userId;
	    userId = util.antiSQLInspection(request.getParameter("userId"));
//
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }    
	    out.println(action); 	 
	    
	    if(action.equals("new"))
	    {	IErp_DieuChuyenTaiSan dcbean;	    
	    	dcbean = new Erp_DieuChuyenTaiSan();	
	       	
	    	//obj.init("");
	    	dcbean.createRs();
	    	
	    	session.setAttribute("dcBean", dcbean);
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenTaiSan_New.jsp";	 
	    	response.sendRedirect(nextJSP);
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
	    	IErp_DieuChuyenTaiSanList obj;	    
	    	obj = new Erp_DieuChuyenTaiSanList();	
    		System.out.println("toi day");
    		obj.setCtyId((String)session.getAttribute("congtyId"));
    		
	    	String search = getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	/*obj.setUserId(userId);*/
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DieuChuyenTaiSan.jsp");	
	    }
	    
	    else
	    {	IErp_DieuChuyenTaiSanList obj;	    
    		obj = new Erp_DieuChuyenTaiSanList();	    	   
	    	String search = getSearchQuery(request, obj);   	
	    	obj.init(search);	    	
	    	session.setAttribute("obj", obj);	    		
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenTaiSan.jsp";	    	
		    response.sendRedirect(nextJSP);
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErp_DieuChuyenTaiSanList obj)
	{	
		Utility util = new Utility();	
    	String query="SELECT dc.pk_seq,dc.ngaychungtu,dc.Sochungtu,dc.trangthai,ts.diengiai as TAISAN FROM ERP_DIEUCHUYENTAISAN dc inner join erp_taisancodinh ts on dc.taisan_fk=ts.pk_seq" +
    			" inner join ERP_DONVITHUCHIEN dvth on dvth.pk_seq = dc.dvth_fk where 1=1 ";
    	
    	
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null) tungay = "";	
		obj.setTungay(tungay);
		
	 	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null) denngay = "";	
		obj.setDenngay(denngay);
	
		
		String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
		if(sochungtu == null) sochungtu = "";	
		obj.setSochungtu(sochungtu);
		
		
		String sodieuchuyen = util.antiSQLInspection(request.getParameter("sodieuchuyen"));
		if(sodieuchuyen == null) sodieuchuyen = "";	
		obj.setSodieuchuyen(sodieuchuyen);
		
		
		String phongban = util.antiSQLInspection(request.getParameter("phongban"));
		if(phongban == null) phongban = "";	
		obj.setPbId(phongban);
		
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null) trangthai = "";	
		obj.setTrangthai(trangthai);
		
		
		if(trangthai.length()>0)
		{
			query+=" and dc.trangthai ="+trangthai ;
		}
		if(phongban.length() >0)
		{
			query+=" and dvth.pk_seq ="+phongban ;
		}
		if(sodieuchuyen.length()>0)
		{
			query+=" and dc.pk_seq ="+sodieuchuyen ;
		}
		if(sochungtu.length()>0)
		{
			query+=" and dc.sochungtu =N'"+sochungtu +"' ";
		}
		if(tungay.length()>0)
		{
			query+=" and dc.ngaychungtu >='" +tungay + "' ";
		}
		if(denngay.length()>0)
		{
			query+=" and dc.ngaychungtu <='" +denngay + "' ";
		}
		
    	System.out.println("query search la: " + query);
    	return query;
	}	
	
	public String chot(String id) {
		dbutils db = new dbutils();
		Utility util = new Utility();
		try {
			db.getConnection().setAutoCommit(false);
			String query = null;

			//Update trạng thái xuất dùng
			query = "update ERP_DIEUCHUYENTAISAN set TRANGTHAI = 1 WHERE PK_SEQ = " + id ;
			int num = db.updateReturnInt(query);
			if (num == 0)
			{
				db.getConnection().rollback();
				return "Không thể cập nhật trạng thái điều chuyển";
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return "Gặp lỗi khi chốt điều chuyển";
		}
		finally
		{
			try {
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	public String unchot(String id) {
		dbutils db = new dbutils();
		Utility util = new Utility();
		List<Integer> thangNam= new ArrayList<Integer>();
		try {
//			util.
			String ngaydieuchuyen="";
			String taisan_fk="";
			String thang="";
			String nam="";
			db.getConnection().setAutoCommit(false);
			String query="SELECT NGAYCHUNGTU,TAISAN_FK FROM ERP_DIEUCHUYENTAISAN WHERE PK_SEQ ="+id;
			ResultSet rs= db.get(query);
			if(rs.next())
			{
				ngaydieuchuyen=rs.getString("NGAYCHUNGTU");
				nam= ngaydieuchuyen.substring(0,4);
				thang=ngaydieuchuyen.substring(5,7);
				taisan_fk=rs.getString("TAISAN_FK");
			}rs.close();
			String msg = util.CheckKhoaSoKT_bonpp(db, ngaydieuchuyen);
			if(msg.trim().length()>0)
			{
				db.getConnection().rollback();
				return "Đã khóa sổ tháng "+thang +" năm "+ nam +" không thể hủy chốt điều chuyển ";
			}
//			if(Integer.parseInt(thang)<=thangNam.get(0) && Integer.parseInt(nam)<=thangNam.get(1))
//			{
//				db.getConnection().rollback();
//				return "Đã khóa sổ tháng "+thang +" năm "+ nam +" không thể hủy chốt điều chuyển ";
//			}
			
			query="Select top 1 thang,nam from erp_khauhaotaisan where taisan_fk ="+taisan_fk + " order by nam desc,thang desc";
			rs=db.get(query);
			
			if(rs.next())
			{
				if(((rs.getInt("nam")>Integer.parseInt(nam))||(rs.getInt("thang")>Integer.parseInt(thang)) && (rs.getInt("nam")==Integer.parseInt(nam))))
			{
				db.getConnection().rollback();
				return "Đã có khẩu hao tháng "+rs.getInt("thang") +" năm "+ rs.getInt("nam") +" không thể hủy chốt điều chuyển ";
			}
			}
			
			//Update trạng thái xuất dùng
			query = "update ERP_DIEUCHUYENTAISAN set TRANGTHAI = 0 WHERE PK_SEQ = " + id ;
			int num = db.updateReturnInt(query);
			if (num == 0)
			{
				db.getConnection().rollback();
				return "Không thể cập nhật trạng thái điều chuyển";
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return "Cố lỗi xảy ra khi hủy chốt";
		}
		finally
		{
			try {
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	

}



