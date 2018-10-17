package geso.traphaco.erp.servlets.nhapkhonhamay;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhapkhonhamay.IErpNhapkhonhamay;
import geso.traphaco.erp.beans.nhapkhonhamay.IErpNhapkhonhamayList;
import geso.traphaco.erp.beans.nhapkhonhamay.imp.ErpNhapkhonhamay;
import geso.traphaco.erp.beans.nhapkhonhamay.imp.ErpNhapkhonhamayList;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhapkhonhamaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpNhapkhonhamaySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpNhapkhonhamayList obj;
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
	    
	    String nhId = util.getId(querystring);
	    
	    String msg = "";
	    
	    obj = new ErpNhapkhonhamayList();
	    
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setUserId(userId);
	    if (action.equals("delete"))
	    {	
	    	msg = Delete(nhId);
	    	if(msg.length() > 0)
	    	{
	    		obj.setmsg(msg);
	    	}
	    }
	    else if(action.equals("chot"))
	    {
	    	
	    	msg = Chot(nhId);
	    	if(msg.length() > 0)
	    	{
	    		obj.setmsg(msg);
	    	}
	    }
     
	 
	    obj.setmsg(msg);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("congtyId", obj.getCongtyId());
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMay.jsp";
		response.sendRedirect(nextJSP);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNhapkhonhamayList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpNhapkhonhamay nhBean = new ErpNhapkhonhamay();
	    	nhBean.setUserId(userId);
	    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	nhBean.createRs();
    		
	    	session.setAttribute("nhBean", nhBean);
	    	session.setAttribute("spList", "");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMayNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpNhapkhonhamayList();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMay.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpNhapkhonhamayList();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
			
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhapKhoNhaMay.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpNhapkhonhamayList obj)
	{
		Utility util=new Utility();
		String query = " select a.PK_SEQ, a.DIENGIAI, a.NGAYTAO, a.NGAYSUA, e.ten as NGUOISUA, d.ten as NGUOITAO, a.TRANGTHAI, a.ngaynhan, c.SOPO from ERP_NHAPKHONHAMAY a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ"+
				" inner join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ"+
		 " where a.congty_fk = '" + obj.getCongtyId() + "' and c.SOPO is not null  AND A.NHAPHANPHOI_FK=" +util.getIdNhapp(obj.getUserId());
		
		 		//" and b.pk_seq in  "+ util.quyen_donvithuchien(obj.getUserId()) ; 		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String dvthId = request.getParameter("dvth");
		if(dvthId == null)
			dvthId = "";
		obj.setDvthId(dvthId);
		
		String soPo = request.getParameter("sopo");
		if(soPo == null)
			soPo = "";
		obj.setSoPO(soPo);
		String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitaoIds(nguoitao);
		
		if(tungay.length() > 0)
			query += " and a.ngaynhan >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaynhan <= '" + denngay + "'";
		
		if(dvthId.length() > 0)
			query += " and b.pk_seq = '" + dvthId + "'";
		
		if(nguoitao.trim().length() > 0)
			query += " AND a.nguoitao = '" + nguoitao + "' ";
		
		
		if(soPo.length() > 0)
		{
			//query += " and ( CAST(c.PK_SEQ as varchar(10)) + '/' + SUBSTRING(c.NGAYMUA, 6, 2) + '/' + SUBSTRING(c.NGAYMUA, 0, 5) like '%" + soPo + "%' ) ";
		
			query += "  AND ( c.sopo like '%" + soPo.trim() + "%' ) ";
		}
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		

		System.out.println(query);
		return query;
	}
	
	private String Chot(String nhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			String ngaychot = getDateTime();
			String query = "";
			String isnhapkhonm = "";
			query = "select isnhapkhonm from erp_muahang where pk_seq = (select muahang_fk from ERP_NHAPKHONHAMAY where PK_SEQ = '" + nhId + "')";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					isnhapkhonm = rs.getString("isnhapkhonm")==null?"":rs.getString("isnhapkhonm");
				}
			}
			if(!isnhapkhonm.trim().equals("1"))
			{
				query = "update ERP_nhapkhonhamay set trangthai=1, ngaynhap = '"+ngaychot+"' where pk_seq = '" + nhId + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Không thể chốt ERP_nhapkhonhamay: " + query;
				}
				query = "update ERP_muahang set isnhapkhonm=1, trangthai=2 where pk_seq = (select muahang_fk from ERP_NHAPKHONHAMAY where PK_SEQ = '" + nhId + "')";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Không thể chốt ERP_nhapkhonhamay: " + query;
				}
				String spId;
				String soluong;
				String ngaynhan, ngaysx, ngayhh;
				String gia;
				String kho;
				String npp;
				String ncc, nhomkenh, solo;
				Utility_Kho util_kho = new Utility_Kho();
				boolean kt = false;
				query = "select a.SANPHAM_FK, a.solo, a.SOLUONG, a.NGAYNHANDUKIEN, a.GIA, a.KHO_FK, a.NGAYSANXUAT, a.NGAYHETHAN, b.nhaphanphoi_fk, b.ncc_fk, a.nhomkenh_fk from ERP_NHAPKHONHAMAY_SP_CHITIET a inner join ERP_NHAPKHONHAMAY b on a.NHAMAY_FK = b.PK_SEQ"
						+ " WHERE b.pk_seq = '" + nhId + "'  ";
				System.out.println("load san pham khi nhap kho " + query);
				rs = db.get(query);
				if(rs!= null)
				{
					while(rs.next())
					{
						kt = true;
						spId = rs.getString("SANPHAM_FK");
						soluong = rs.getString("SOLUONG");
						ngaynhan = rs.getString("ngaynhandukien");
						ngaysx = rs.getString("NGAYSANXUAT");
						ngayhh = rs.getString("NGAYHETHAN");
						gia = rs.getString("gia");
						kho = rs.getString("kho_fk");
						npp = rs.getString("nhaphanphoi_fk");
						ncc = rs.getString("ncc_fk");
						nhomkenh = rs.getString("nhomkenh_fk");
						solo = rs.getString("solo");
						
						String msg = util_kho.Update_NPP_Kho_Sp(ngaychot, "nhap kho nha may", db, kho, spId, npp, nhomkenh, Double.parseDouble(soluong), 0, Double.parseDouble(soluong), Double.parseDouble(gia));
						if(msg.length() >0){
							db.getConnection().rollback(); 
							return msg;
						}
						msg = util_kho.Update_NPP_Kho_Sp_NCC(ngaychot, "nhap kho nha may", db, kho, spId, npp, nhomkenh, Double.parseDouble(soluong), 0, Double.parseDouble(soluong), Double.parseDouble(gia), ncc);
						if(msg.length() >0){
							db.getConnection().rollback(); 
							return msg;
						}
						msg = util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(ngaychot, "nhap kho nha may", db, kho, spId, npp, nhomkenh, ncc, solo, ngaysx, ngayhh, ngaychot, Double.parseDouble(soluong), 0, Double.parseDouble(soluong), Double.parseDouble(gia));
						if(msg.length() >0){
							db.getConnection().rollback(); 
							return msg;
						}
					}
				}
				if(!kt)
				{
					db.getConnection().rollback();
					return "Không thể chốt ERP_nhapkhonhamay: Không có chi tiết nhập kho!";
				}
			}
			else
			{
				db.getConnection().rollback();
				return "Không thể chốt ERP_nhapkhonhamay: Đơn hàng đã được nhập kho!";
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (Exception e)
		{ 
			db.shutDown(); 
			return "Khong the chot nhap kho"; 
		}
		
	}
	
	private String Delete(String nhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			query = "delete ERP_nhapkhonhamay where pk_seq = '" + nhId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xoa ERP_nhapkhonhamay: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (Exception e)
		{ 
			db.shutDown(); 
			return "Khong the xoa nhap kho"; 
		}
		
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String getTime()
	{
		Date date = new Date();
	    SimpleDateFormat simpDate;

	    //format 24h
	    simpDate = new SimpleDateFormat("kk:mm:ss");
	    
	    return simpDate.format(date);
	}
 
}
