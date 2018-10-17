package geso.traphaco.distributor.servlets.noptien;

import geso.traphaco.distributor.beans.noptien.*;
import geso.traphaco.distributor.beans.noptien.imp.*;
import java.sql.ResultSet;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NoptienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private int items = 50;
	private int splittings = 20;
	
	
    public NoptienSvl() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
		
		INoptienList obj;
		PrintWriter out; 
		
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String msg ="";
	    String Id = util.getId(querystring);
	    obj = new NoptienList();
	    
	    if (action.equals("delete"))
	    {	   		  	    	
	       msg = Delete(Id, userId, obj);
	    }
	    else if (action.equals("chot"))
	    {	   		  	    	
	       msg = Chot(Id, userId, obj);
	    }
	   	    
	    
	    
	    settingPage(obj);
	    	
	    obj.setUserId(userId);
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/NopTien.jsp";
		response.sendRedirect(nextJSP);
		}
	}

	private String Chot(String id, String userId, INoptienList obj)
	{
		dbutils db = new dbutils();
		String msg="";
		try
		{
			String query = "update noptien set trangthai = '1' where pk_seq = '"+ id +"' ";
			if(!db.update(query))
			{
				msg = "Không thể chốt phiếu này! ";
				db.getConnection().rollback();
			}
		
		} 
		catch(Exception e) 
		{
			msg ="Lỗi xảy ra trong quá trình chốt !";
			
		}
		db.shutDown();
		
	
		return msg;
	
	}

	private String Delete(String id, String userId, INoptienList obj) 
	{

		dbutils db = new dbutils();
		String msg="";
		try
		{
			// Kiểm tra phiếu thu có sử dụng Số tiền của phiếu nộp này không
			String query = 
				    " select SUM(isnull(sotiendatt,0)) as dasd " +
					" from ERP_THUTIENNPP a inner join ERP_THUTIENNPP_NOPTIEN b on a.pk_seq = b.thutiennpp_fk" +
					" where a.trangthai != 2 and b.noptien_fk = "+ id +" " +
					" group by b.noptien_fk  ";
			System.out.println("Cau check1"+query);
			ResultSet rsKTTien = db.get(query);
			double SotienDSD = 0 ;
			if(rsKTTien!= null)
			{
				while(rsKTTien.next())
				{
					SotienDSD = rsKTTien.getDouble("dasd");
				}
				rsKTTien.close();
			}
			
			if(SotienDSD > 0)
			{
				query = 
					" select a.PK_SEQ  " +
					" from ERP_THUTIENNPP a inner join ERP_THUTIENNPP_NOPTIEN b on a.pk_seq = b.thutiennpp_fk" +
					" where a.trangthai != 2 and b.noptien_fk = "+ id +"  ";
				System.out.println("Cau check "+query);
				ResultSet RSPhieuThuTien =db.get(query);
				
				String ptt="";
				if(RSPhieuThuTien!=null)
				{
					while(RSPhieuThuTien.next())
					{
						ptt+=RSPhieuThuTien.getString("PK_SEQ")+", ";
					}
					RSPhieuThuTien.close();						
				}
				
				msg = "Phiếu nộp tiền này đã được sử dụng trong các phiếu thu tiền ( "+ ptt +" ) .Không thể xóa được .";				
			}
			else
			{							
				query = "update noptien set trangthai = '2' where pk_seq = '"+ id +"' ";
				if(!db.update(query))
				{
					msg = "Không thể xóa phiếu này! ";
					db.getConnection().rollback();
				}
			
			}
		
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
			msg ="Lỗi xảy ra trong quá trình xóa !";
			
		}
		db.shutDown();
		
	
		return msg;
	}

	private void settingPage(INoptienList obj) {
		Utility util = new Utility();
		if(getServletContext().getInitParameter("items") != null){
	    	String i = getServletContext().getInitParameter("items").trim();
	    	if(util.isNumeric(i))
	    		items = Integer.parseInt(i);
	    }
	    
	    if(getServletContext().getInitParameter("splittings") != null){
	    	String i = getServletContext().getInitParameter("splittings").trim();
	    	if(util.isNumeric(i))
	    		splittings = Integer.parseInt(i);
	    }
	    
    	obj.setItems(items);
    	obj.setSplittings(splittings);
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
		
		INoptienList obj = new NoptienList();
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    Utility util = new Utility();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    out.println(action); 
	          
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	INoptien khBean = (INoptien) new Noptien("");
	    	khBean.setUserId(userId);
	    	khBean.createRS();
	    	// Save Data into session
	    	session.setAttribute("khBean", khBean);
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/NopTienNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    
	    settingPage(obj);
	    
	    if (action.equals("search"))
	    {	    
	    	
	    	String search = getSearchQuery(request, obj);
	    	//search = search + " and a.npp_fk='" + userId + "' order by a.ten";
	    	
	    	//obj = new KhachhangList(search);
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/NopTien.jsp");	    		    	
	    }
	    
	    else 

	    if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);

	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/NopTien.jsp");
	    }
	    
	    
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, INoptienList obj)
	{		
		Utility util = new Utility();
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if ( tungay == null)
    		tungay = "";
    	obj.setTungay(tungay);
    	
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if ( denngay == null)
    		denngay = "";
    	obj.setDenngay(denngay);
    	
    	String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
    	if ( sochungtu == null)
    		sochungtu = "";
    	obj.setSochungtu(sochungtu);
		
		String ngaynop = util.antiSQLInspection(request.getParameter("ngaynop"));
    	if ( ngaynop == null)
    		ngaynop = "";
    	obj.setNgaynop(ngaynop);
    	
    	String nvgnId = util.antiSQLInspection(request.getParameter("nvgnId"));
    	if ( nvgnId == null)
    		nvgnId = "";
    	obj.setNvgnId(nvgnId);

    	String nvbhId = util.antiSQLInspection(request.getParameter("nvbhId"));
    	if ( nvbhId == null)
    		nvbhId = "";
    	obj.setNvbhId(nvbhId);
    	
    	String khId = util.antiSQLInspection(request.getParameter("khId"));
    	if ( khId == null)
    		khId = "";
    	obj.setKhId(khId);
       	
    	
    	String query = "select A.PK_SEQ , a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, A.NGAYNOP , ISNULL(A.SOIN, '') AS SOIN ,  " 
			+ " A.SOTIEN ,CASE WHEN A.NVBH_FK IS NOT NULL THEN E.TEN + ' - ' + cast(E.PK_SEQ as nvarchar(100)) " +
			  "                WHEN A.NVGN_FK IS NOT NULL THEN D.TEN + ' - ' + cast(D.PK_SEQ as nvarchar(100)) " +
			  "                WHEN A.NPP_DAT_FK IS NOT NULL THEN  G.TEN + ' - ' + cast(G.PK_SEQ as nvarchar(100) ) " +
			  "                ELSE F.TEN + ' - ' + F.MAFAST END AS DOITUONG"
			+ " from NOPTIEN a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq  "
			+ " left join NHANVIENGIAONHAN D on a.NVGN_FK = D.PK_SEQ " +
			  " left join DAIDIENKINHDOANH  E on a.NVBH_FK = E.PK_SEQ " +
			  " left join KHACHHANG F on a.KHACHHANG_FK = F.PK_SEQ "+
			  " left join NHAPHANPHOI G on a.NPP_DAT_FK = G.PK_SEQ "+
			" where a.pk_seq > 0 ";
    	
    	if(tungay.length() > 0)
    	{
    		query = query + " and a.ngaynop >= '" + tungay + "' ";
    	}
    	
    	if(denngay.length() > 0)
    	{
    		query = query + " and a.ngaynop <= '" + denngay + "' ";
    	}
    	
    	if(sochungtu.length() > 0)
    	{
    		query = query + " and a.pk_seq like '%" + sochungtu + "%' ";
    	}
    	
    	if (ngaynop.length()>0)
    	{ 
    			
			query = query + " and a.ngaynop = '"+ ngaynop +"' ";			
    	}
    	
    	
    	if(nvbhId.length()>0)
    	{
    		query = query + " and a.nvbh_fk = '"+ nvbhId +"' ";	
    	}
    	
    	if(nvgnId.length()>0)
    	{
    		query = query + " and a.nvgn_fk = '"+ nvgnId +"' ";	
    	}
    	
    	if(khId.length()>0)
    	{
    		query = query + " and a.khachhang_fk = '"+ khId +"' ";	
    	}
    	
    	System.out.println("Query lay khach hang: " + query + "\n");

    	
    	return query;
	}	
	


}
