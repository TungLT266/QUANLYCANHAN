package geso.traphaco.erp.servlets.phieuxuatkhoTH;
 
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.phieuxuatkhoTH.*;
import geso.traphaco.erp.beans.phieuxuatkhoTH.imp.*;
 

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpPhieuxuatkhoTHSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpPhieuxuatkhoTHSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpPhieuxuatkhoList obj;
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
	    
	    String nkId = util.getId(querystring);
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    obj = new ErpPhieuxuatkhoList();
	    if (action.equals("delete"))
	    {	
	    	ErpPhieuxuatkho temp = new ErpPhieuxuatkho(nkId);
	    	temp.setCongtyId(ctyId);
    		boolean check = temp.Delete();
	    	if(check == false)
	    		obj.setMsg(temp.getMsg());
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		ErpPhieuxuatkho temp = new ErpPhieuxuatkho(nkId);
	    		temp.setCongtyId(ctyId);
	    		boolean check = temp.Chot();
		    	if(check == false)
		    		obj.setMsg(temp.getMsg());
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.setCtyId(ctyId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKho.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpPhieuxuatkhoList obj;
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
	    	IErpPhieuxuatkho pxkBean = new ErpPhieuxuatkho();
	    	// mặc định xuất kho từ đơn đặt hàng
	    	pxkBean.setLoaixuatkho("DH");
	    	pxkBean.createRs();
    		
	    	session.setAttribute("pxkBean", pxkBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpPhieuxuatkhoList();
	    		obj.setUserId(userId);
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpPhieuXuatKho.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpPhieuxuatkhoList();
		    	
		    	obj.setUserId(userId);
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpPhieuXuatKho.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpPhieuxuatkhoList obj)
	{
		Utility util=new Utility();
		
 
											
		String query = 
				"		SELECT DISTINCT a.PK_SEQ as pxkId,a.noidungxuat, a.NGAYXUAT, b.MA + ' - ' + b.TEN as ndxTen, c.SOHOADON as sohoadon1 ,\n"
			+ 	"		a.PREFIX + cast(a.PK_SEQ as varchar(20)) as soxuatkho, \n"
			+ 	"		case when a.TRAHANGNCC_FK is null \n"  
			+ 	"		then (cast('' as varchar(20)))  "  
			+ 	"		else (isnull(g.PREFIX,'') + isnull(f.PREFIX,'') + cast(a.TRAHANGNCC_fk as varchar(20)) ) \n"
			+ 	"		end as HOADON_FK, case when a.TRAHANGNCC_FK is not null then cast(a.TRAHANGNCC_FK as varchar(20))   "+
			"  			when a.dondathang_fk is not null then '130'+cast(a.dondathang_fk as varchar(20))   \n"
			+	"		else    isnull(HD.SOHOADON,'')  end as SOHOADON, \n"
			+ 	"		a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, isnull(a.machungtu,'') machungtu  \n"
			+ 	"       FROM 	ERP_XUATKHO a inner join ERP_NOIDUNGNHAP b on a.NOIDUNGXUAT = b.PK_SEQ \n"
			+ 	"		INNER JOIN NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ \n"
			+ 	"		LEFT JOIN ERP_HOADON HD on a.HOADON_FK = HD.PK_SEQ \n"
			+ 	"		LEFT JOIN ERP_MUAHANG f on a.TRAHANGNCC_FK = f.pk_seq \n"
			+	"		left join ERP_HOADON_DDH dh on f.PK_SEQ=dh.DDH_FK left join ERP_HOADON c on dh.HOADON_FK=c.PK_SEQ "
			+ 	"		LEFT JOIN ERP_DONVITHUCHIEN g on f.donvithuchien_fk = g.pk_seq  " +
				"   	WHERE  c.TRANGTHAI<>2 and 1 =  1 ";
		
		String tungay =util.antiSQLInspection( request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay =util.antiSQLInspection( request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai =util.antiSQLInspection( request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
	
		String xuatkhoId=util.antiSQLInspection( request.getParameter("xuatkhoId"));
		if(xuatkhoId == null)
			xuatkhoId = "";
		obj.setXuatKhoId(xuatkhoId);
		
		String sochungtu =util.antiSQLInspection( request.getParameter("sochungtu"));
		if(sochungtu == null)
			sochungtu = "";
		obj.setSoChungTu(sochungtu);
		
		
		String khoxuatId=util.antiSQLInspection( request.getParameter("khoxuatId"));
		if(khoxuatId==null){
			khoxuatId="";
		}
		obj.set_khoxuatId(khoxuatId);
		
		String noidungId =util.antiSQLInspection( request.getParameter("ndId"));
		if(noidungId == null)
			noidungId = "";
		obj.setnoidungxuatId(noidungId);
		
		String sohoadon =util.antiSQLInspection( request.getParameter("sohoadon"));
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
	
		
		if(khoxuatId.length() > 0){
			query += " and a.kho_fk="+khoxuatId;
		}
		
		if(tungay.length() > 0)
			query += " and a.NGAYXUAT >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.NGAYXUAT <= '" + denngay + "'";
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		if(xuatkhoId.length()>0)
			query+=" and (a.PREFIX + cast(a.PK_SEQ as varchar(20))) like '%"+xuatkhoId+"%'";
		
		if(sochungtu.length()>0)
			query+= " and (     '130'+cast(a.dondathang_fk as varchar(20)) like '%"+sochungtu+"%'   or cast(a.TRAHANGNCC_FK as varchar(20)) like '%"+sochungtu+"%'   ) ";
		
		if(noidungId.length()>0)
			query+= " and a.NOIDUNGXUAT = '"+noidungId+"'";
		
		if(sohoadon.length()>0)
			query+= " and c.SOHOADON like '%"+sohoadon+"%'";
		//System.out.println("searching:" + query);
		
		return query;
	}
	
	private String Delete(String xkid)
	{
		dbutils db = new dbutils();
		Utility_Kho util_kho=new Utility_Kho();
		String hdtcId = "";
		try 
		{
			
			String maketostock_old="";
			String	query="";
			
			
			db.getConnection().setAutoCommit(false);
			
			 
			
			// trường hợp xuất kho từ đơn trả hàng, thì cũng thuộc loại maketostoke, vì khi trả hàng thì đã giảm availabe lúc save
			
			 query=  "select TRAHANGNCC_FK from ERP_XUATKHO where TRAHANGNCC_FK is not null and  PK_SEQ="+xkid;
			 ResultSet rsck=db.get(query);
			 
			 if(rsck.next()){
				maketostock_old="1";
			 }
				 
			 query = " select xk.nhomkenh_fk ,xk.npp_id ,xk.ngayxuat, xk.npp_fk, xk.KHO_FK, a.SANPHAM_FK, a.SOLO, a.SOLUONG, a.KHUVUCKHO_FK ,A.NGAYBATDAU  "+
					 " from ERP_XUATKHO_SP_CHITIET a " +
					 " inner join ERP_XUATKHO xk  on xk.PK_SEQ=a.XUATKHO_FK "+
					 " where xk.PK_SEQ = " + xkid;
 
				ResultSet rs	=	db.get(query);
				while (rs.next()){				
					String khuvuc_old = rs.getString("KHUVUCKHO_FK");
					String spid_old = rs.getString("SANPHAM_FK");
					String idkhott_old = rs.getString("KHO_FK");
					String ngaybatdau= rs.getString("NGAYBATDAU");
					//String npp_fk_old = rs.getString("npp_fk");
					//String nhanvien_fk_old = rs.getString("nhanvien_fk");
					String solo_old = rs.getString("SOLO");
					String ngaychungtu=rs.getString("ngayxuat");
					double soluongct= rs.getDouble("SOLUONG");
					String nppid=rs.getString("npp_id");
					String nhomkenh_fk=rs.getString("nhomkenh_fk");
					//cộng lại avai,booked giảm lại
					double soluong=0;
					double booked=(-1)*soluongct;
					double available= soluongct;
					
					if(!maketostock_old.equals("1")){
						String msg1= util_kho.Update_NPP_Kho_Sp(ngaychungtu, "Chốt xuất kho", db, idkhott_old, spid_old, nppid, nhomkenh_fk, soluong, booked, available, 0);
						if(msg1.length() >0){
							db.update("rollback");
							return msg1;
						}
					}
					
					
					String msg1=util_kho.Update_NPP_Kho_Sp_Chitiet(ngaychungtu,"Chốt xuất kho", db, idkhott_old, spid_old, nppid, nhomkenh_fk, solo_old, "", "","", soluong, booked, available, 0);
					if(msg1.length() >0){
						db.update("rollback");
						return msg1;
					}
 
				}
				rs.close();

			query=" update ERP_XUATKHO set trangthai = '2' where pk_seq = '" + xkid + "'";
			
			if(!db.update(query)){
				db.update("rollback");
				return query;
				
			}
			 
			query = "update ERP_HOADON set trangthai = '1' where  pk_seq  = (select hoadon_fk from erp_xuatkho where pk_seq="+xkid+")";
			if(!db.update(query))
			{
				db.update("rollback");
				return "Khong the cap nhat trang thai Hoa don: " + query;
			}
		 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (Exception e)
		{ 	
			e.printStackTrace();
			db.update("rollback");
			db.shutDown(); 
			return "Khong the xoa xuat kho";
		}
	}
	
	private String Chot(String Id, String userId, String ctyId)
	{
		IErpPhieuxuatkho phieuxuatkho = new ErpPhieuxuatkho(Id);
		phieuxuatkho.init();
		phieuxuatkho.setCongtyId(ctyId);
		
		if(!phieuxuatkho.chotXuatKho(userId))
		{
			return phieuxuatkho.getMsg();
			
		}
		return "";
	}

}
