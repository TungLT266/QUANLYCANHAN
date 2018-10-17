package geso.traphaco.erp.servlets.lenhsanxuatgiay;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpYeucaunguyenlieu;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpYeucaunguyenlieuList;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpYeucaunguyenlieu;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpYeucaunguyenlieuList;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpYeucaunguyenlieugiaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpYeucaunguyenlieugiaySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpYeucaunguyenlieuList obj;
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
	    obj = new ErpYeucaunguyenlieuList();
	    if (action.equals("delete"))
	    {	
	    	dbutils db = new dbutils();
	    	db.update("update ERP_YEUCAUNGUYENLIEU set trangthai = '3' where pk_seq = '" + lsxId + "'");
	    	db.shutDown();
	    }else if(action.equals("deleteck")){
	    	this.XoaChuyenKhoLsx( lsxId); 
	    }else if(action.equals("duyetck")){
	    	String msg= this.DuyetChuyenKhoLsx(lsxId,userId);
	    	 obj.setMsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		dbutils db = new dbutils();
		    	db.update("update ERP_YEUCAUNGUYENLIEU set trangthai = '1' where pk_seq = '" + lsxId + "'");
		    	db.shutDown();
	    	}
	    }
	    
	    String task = request.getParameter("task");
		if(task == null)
			task = "";
		if(task.equals("chuyenNL"))
			obj.setIschuyenNL("1");
		
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiay.jsp";
		if(task.equals("chuyenNL"))
		{
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenNguyenLieuGiay.jsp";
		}
		
		response.sendRedirect(nextJSP);
	}

	private String DuyetChuyenKhoLsx(String Id,String Nguoiduyet) {
		// TODO Auto-generated method stub
		dbutils db = new dbutils();
		String msg="";
		try{
				 
		    	String query="update ERP_YEUCAUNGUYENLIEU set duyet='1',nguoiduyet="+Nguoiduyet+" where pk_seq = '" + Id + "'";
		    	if(!db.update(query))
				{
					 msg = "Không thể cập nhật bảng ERP_YEUCAUNGUYENLIEU: " + query;
					 db.getConnection().rollback();
					return msg;
				}
		    	// cập nhật trạng thái phiếu yêu cầu sang trạng thái đã chuyên nguyên liệu
		    	query=" update erp_phieuyeucau set TrangThai=3 where pk_seq= (select phieuyeucau_fk from ERP_YEUCAUNGUYENLIEU where pk_seq="+Id+") ";
		    	if(!db.update(query))
				{
					 msg = "Không thể cập nhật bảng ERP_YEUCAUNGUYENLIEU: " + query;
					 db.getConnection().rollback();
					return msg;
				}
				db.shutDown();
		}catch(Exception er ){
			db.update("rollback");
			er.printStackTrace();
			return er.getMessage();
		}
		return"";
	}

	private String XoaChuyenKhoLsx(String Id) {
		// TODO Auto-generated method stub
		dbutils db = new dbutils();
		String msg="";
		try{
				db.getConnection().setAutoCommit(false);
	    	 
		    	String query="update ERP_YEUCAUNGUYENLIEU set trangthai = '3' where pk_seq = '" + Id + "'";
		    	if(!db.update(query))
				{
					 msg = "Khong the cap nhat erp_khott_sanpham: " + query;
					db.getConnection().rollback();
					return msg;
				}
	    	
	    	    query=	" SELECT B.KHOXUAT_FK,A.SANPHAM_FK,A.SOLUONGNHAN "+
						" FROM ERP_YEUCAUNGUYENLIEU_SANPHAM A   "+
						" INNER JOIN ERP_YEUCAUNGUYENLIEU B ON A.YEUCAUNGUYENLIEU_FK=B.PK_SEQ "+
						" WHERE B.PK_SEQ="+Id;

				ResultSet rs=db.get(query);
				
				while (rs.next()){
					query= " update erp_khott_sanpham set available=available + "+ rs.getString("SOLUONGNHAN")+"  " +
						   " ,booked=booked -"+ rs.getString("SOLUONGNHAN")+" where sanpham_fk="+rs.getString("SANPHAM_FK")+"  and khott_fk= "+rs.getString("KHOXUAT_FK");
					if(db.updateReturnInt(query) != 1)
					{
						 msg = "Khong the cap nhat erp_khott_sanpham: " + query;
						db.getConnection().rollback();
						return msg;
					}
				}

				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
		}catch(Exception er ){
			db.update("rollback");
			er.printStackTrace();
			return er.getMessage();
		}
		return"";
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
	    
		IErpYeucaunguyenlieuList obj = new ErpYeucaunguyenlieuList();
	    String task = request.getParameter("task");
		if(task == null)
			task = "";
		if(task.equals("chuyenNL"))
			obj.setIschuyenNL("1");
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpYeucaunguyenlieu lsxBean = new ErpYeucaunguyenlieu();
	    	lsxBean.SetCtyId((String)session.getAttribute("congtyId"));
	    	lsxBean.createRs();
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiayNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		System.out.println("toi day");
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	if(task.equals("chuyenNL"))
		    	{
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpChuyenNguyenLieuGiay.jsp");	
		    	}
		    	else
		    	{
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiay.jsp");	
		    	}
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		if(task.equals("chuyenNL"))
		    	{
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpChuyenNguyenLieuGiay.jsp");	
		    	}
		    	else
		    	{
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpYeuCauNguyenLieuGiay.jsp");	
		    	}
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpYeucaunguyenlieuList obj)
	{
		String  query = "select  isnull( phieuyeucau_fk,0) as phieuyeucau, ISNULL(A.DUYET,'0') AS DUYET , a.PK_SEQ, a.trangthai, a.ngayyeucau, a.lydo, NV.TEN as nguoitao, " +
						" a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
						 " from ERP_YEUCAUNGUYENLIEU a   " +
						 " inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						 " inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ  " +
						 " where 1=1   ";
		
		
		
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
		
		String sochungtu = request.getParameter("sochungtu");
		if(sochungtu == null)
			sochungtu = "";
		obj.setSochungtu(sochungtu);
		
		String soyeucau = request.getParameter("soyeucau");
		if(soyeucau == null)
			soyeucau = "";
		obj.setSoyeucau(soyeucau);
		
		String lydoyeucau = request.getParameter("lydoyeucau");
		if(lydoyeucau == null)
			lydoyeucau = "";
		obj.setLydo(lydoyeucau);
		
		if(lydoyeucau.length()>0){
			query= query + " and a.lydo like N'%"+lydoyeucau+"%' ";
		}
		 
		if(tungaySX.length() > 0){
			query += " and a.Ngayyeucau >= '" + tungaySX + "'";
		}
		
		if(denngaySX.length() > 0)
			query += " and a.Ngayyeucau <= '" + denngaySX + "'";
		
	
		
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(sochungtu.length() >0){
			query += " and  cast( a.pk_seq as nvarchar(10))  like '%" + sochungtu + "%'";
		}
		
		if(soyeucau.length() >0){
			query += " and  cast( a.phieuyeucau_fk as nvarchar(10))  like '%" + soyeucau + "%'";
		}
		
		System.out.println("Du lieu :"+query);
		
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
