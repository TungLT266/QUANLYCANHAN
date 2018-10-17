package geso.traphaco.distributor.servlets.chuyenkho;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkhoSX;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkhoSXList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkhoSX;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkhoSXList;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpChuyenKhoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpChuyenKhoSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpChuyenkhoSXList obj;
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
	    
	    String lsxId = util.getId(querystring);
	    obj = new ErpChuyenkhoSXList();
	    System.out.println(" action :"+action);
	    if (action.equals("delete"))
	    {	
	    	dbutils db = new dbutils();
	    	db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '4' where pk_seq = '" + lsxId + "'");
	    	db.shutDown();
	    } 
	    else if(action.equals("chot"))
    	{
    		dbutils db = new dbutils();
	    	db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '1' where pk_seq = '" + lsxId + "'");
	    	db.shutDown();
    	}
	    else if(action.equals("bochot"))
	    {
    		dbutils db = new dbutils();
	    	db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '0' where pk_seq = '" + lsxId + "'");
	    	db.shutDown();
    	}
	    else if(action.equals("hoantat"))
		{
			dbutils db = new dbutils();
	    	db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '3' where pk_seq = '" + lsxId + "'");
	    	db.shutDown();
		}
    	
	    String task = request.getParameter("task");
		if(task == null){
			task = "0";
		}
		obj.setIsnhanHang(task);
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp";
		 
		
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

		IErpChuyenkhoSXList obj = new ErpChuyenkhoSXList();
		String task = request.getParameter("task");
		if(task == null){
			task = "0";
		}

		obj.setIsnhanHang(task);
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		if(action.equals("Tao moi"))
		{
			IErpChuyenkhoSX lsxBean = new ErpChuyenkhoSX();
			lsxBean.setUserId(userId);
			lsxBean.createRs();
			lsxBean.settask(task);
			session.setAttribute("lsxBean", lsxBean);
			session.setAttribute("khochuyenIds", "");
			obj.DBclose();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoNew.jsp";
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
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp";

				response.sendRedirect(nextJSP);
			}
			else if(action.equals("chot") || action.equals("bochot"))
			{
				String Id = request.getParameter("chungtu");
				String msg="";
				if(action.equals("chot"))
				{
					dbutils db = new dbutils();
					db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '1' where pk_seq = '" + Id + "'");
					db.shutDown();
				}
				else if(action.equals("bochot"))
				{
					dbutils db = new dbutils();
					db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '0' where pk_seq = '" + Id + "'");
					db.shutDown();
				}

				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);		    	
				obj.init(search);	
				if(msg.length()>0){
					obj.setMsg(msg);

				}
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp";

				response.sendRedirect(nextJSP);
			}
			else
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);		    	
				obj.init(search);				
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp";

				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpChuyenkhoSXList obj)
	{
		String	query = 	
			" SELECT isnull(a.sochungtu,'') as sochungtu,A.PK_SEQ, A.TRANGTHAI, A.NGAYYEUCAU, A.NOIDUNGXUAT_FK AS NDXID, B.MA + ', ' + B.TEN AS NOIDUNGXUAT,  isnull(KHOTT.TEN,'') as khonhan," +
			" 		 A.LYDO, NV.TEN AS NGUOITAO, A.NGAYSUA, A.NGAYTAO, NV2.TEN AS NGUOISUA   " +
			" FROM 	 ERP_YEUCAUCHUYENKHO A INNER JOIN ERP_NOIDUNGNHAP B ON A.NOIDUNGXUAT_FK = B.PK_SEQ  " +
			" left join ERP_KHOTT KHOTT on a.khonhan_fk = KHOTT.PK_SEQ   " +
			" INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   " +
			" INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ  " +
			" WHERE A.PK_SEQ > 0  " ;

		String tungaySX = request.getParameter("tungaySX");
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);
		
		String denngaySX = request.getParameter("denngaySX");
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String khonhanid = request.getParameter("khonhanId");
		if(khonhanid == null)
			khonhanid = "";
		obj.setkhonhanId(khonhanid);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setsohoadon(sohoadon);
		
		String khochuyenid = request.getParameter("khochuyenId");
		if(khochuyenid == null)
			khochuyenid = "";
		obj.setKhoChuyenId(khochuyenid);
		
		String sophieu = request.getParameter("sophieu");
		if(sophieu == null)
			sophieu = "";
		obj.setSophieu(sophieu);
		
		String lsxId = request.getParameter("solenhsx");
		if(lsxId == null)
			lsxId = "";
		obj.setlsxId(lsxId);
		
		String ndxuat = request.getParameter("ndxuat");
		if(ndxuat == null)
			ndxuat = "";
		obj.setNdxuat(ndxuat);
		
		String lydo = request.getParameter("lydo");
		if(lydo == null)
			lydo = "";
		obj.setLydo(lydo);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String nguoisua = request.getParameter("nguoisua");
		if(nguoisua == null)
			nguoisua = "";
		obj.setNguoisua(nguoisua);
		
		String sochungtubn = request.getParameter("sochungtubn");
		if(sochungtubn == null)
			sochungtubn = "";
		obj.setsochungtubnId(sochungtubn);
		
		if(tungaySX.length() > 0)
			query += " and a.ngayyeucau >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.ngayyeucau  <= '" + denngaySX + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(sophieu.length() > 0){
			query += " and  cast( a.pk_seq as nvarchar(10))  like '%" + sophieu + "%'";
		}
		
		if(khochuyenid.length() > 0){
			query += " and  cast( a.khoxuat_fk as nvarchar(10))  like '%" + khochuyenid + "%'";
		}
		
		if(khonhanid.length() > 0){
			query += " and  cast( a.khonhan_fk as nvarchar(10))  like '%" + khonhanid + "%'";
		}
		
		if(lsxId.length() > 0){
			query += " and  cast( a.LENHSANXUAT_FK as nvarchar(10))  like '%" + lsxId + "%'";
		}
		
		if(lydo.length() > 0){
			query += " and a.lydo like N'%" + lydo + "%'";
		}
		
		if(ndxuat.length() > 0){
			query += " and a.noidungxuat_fk = " +ndxuat+ " ";
		}
		if(nguoitao.length() > 0){
			query += " and a.nguoitao = " +nguoitao+ " ";
		}
		if(nguoisua.length() > 0){
			query += " and a.nguoisua = " +nguoisua+ " ";
		}
		
		if(obj.getsochungtubnId().trim().length()>0){
			query+= " and A.LENHDIEUDONG LIKE '%"+sochungtubn+"%' ";
		}
		 
	 
		if(sohoadon.length() >0){
			query+=" AND A.sochungtu LIKE  '%"+sohoadon+"%'";
		}
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
