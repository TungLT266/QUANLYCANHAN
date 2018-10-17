package geso.traphaco.distributor.servlets.kiemkhotdv;

import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.distributor.beans.kiemkhotdv.IKiemkhotdv;
import geso.traphaco.distributor.beans.kiemkhotdv.IKiemkhotdvList;
import geso.traphaco.distributor.beans.kiemkhotdv.imp.Kiemkhotdv;
import geso.traphaco.distributor.beans.kiemkhotdv.imp.KiemkhotdvList;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.distributor.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KiemkhotdvSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
		public KiemkhotdvSvl() {
			super();
		}   	
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
			String userId = (String) session.getAttribute("userId");  
			String userTen = (String) session.getAttribute("userTen");  	
			String sum = (String) session.getAttribute("sum");
			geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
			if(!cutil.check(userId, userTen, sum)){
				response.sendRedirect("/TraphacoHYERP/index.jsp");
			}else{
			IKiemkhotdvList obj;
			   
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    	    
		    
		    Utility util = new Utility();
		    out = response.getWriter();
		    
		    String querystring = request.getQueryString();
		    String action = util.getAction(querystring);
//		    out.println(action);
		    
		    String dctkId = util.getId(querystring);
		    
		    userId = util.getUserId(querystring);
		    
		    if (action.equals("delete"))
		    {	   		  	    	
		    	try 
		    	{
						Delete(dctkId,request);
				} 
		    	catch (SQLException e)
		    	{
					
					e.printStackTrace();
				}
		    	
		    }
		    if(action.equals("chot"))
		    {
		    		try 
		    		{
						chot(dctkId, request);
					} 
		    		catch (SQLException e) 
		    		{
						
						e.printStackTrace();
					}
		    }
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    obj = new KiemkhotdvList();
		    obj.setUserId(userId);
		    obj.init0();
		    obj.createDctklist("");
			session.setAttribute("obj", obj);
					
			String nextJSP = "/TraphacoHYERP/pages/Distributor/KiemKhoTDV.jsp";
			response.sendRedirect(nextJSP);
			}
		}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
			IKiemkhotdvList obj;
		 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    Utility util = new Utility();
		
		IKiemkhotdv dctkBean = (IKiemkhotdv) new Kiemkhotdv();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    out.print(action);
	    if (action.equals("new")){	    	
	    	
	    	dctkBean.setUserId(userId);
	    	dctkBean.setNppId(util.antiSQLInspection(request.getParameter("nppId")));
	    	dctkBean.setNgaydc(getDateTime());
	    	dctkBean.initNew();
	    	session.setAttribute("dctkBean", dctkBean);
    		out.println(dctkBean.getMessage());
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Distributor/KiemKhoTDVNew.jsp";
    		response.sendRedirect(nextJSP);    		

	    }
	    else  if (action.equals("search")){
	    	obj = new KiemkhotdvList();
	    	obj.setUserId(util.antiSQLInspection(request.getParameter("userId")));
			String query = getSearchQuery(request, obj);		    
		    obj.init0();
		    obj.createDctklist(query);
			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/KiemKhoTDV.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	 obj = new KiemkhotdvList();
			    obj.setUserId(userId);
			    obj.init0();
			    obj.createDctklist("");
				session.setAttribute("obj", obj);
						
				String nextJSP = "/TraphacoHYERP/pages/Distributor/KiemKhoTDV.jsp";
				response.sendRedirect(nextJSP);
	    }
		}
	    	
	  
	}

	private String getSearchQuery(HttpServletRequest request, IKiemkhotdvList obj){
//	    PrintWriter out = response.getWriter();
		Utility util = new Utility();
		geso.traphaco.center.util.Utility utilCenter = new geso.traphaco.center.util.Utility();
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));		
    	if (nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
    	if (dvkdId == null)
    		dvkdId = "";
    	obj.setDvkdId(dvkdId);
    	
		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
    	if (kbhId == null)
    		kbhId = "";
    	obj.setKbhId(kbhId);

		String khoId = util.antiSQLInspection(request.getParameter("khoId"));
    	if (khoId == null)
    		khoId = "";
    	obj.setKhoId(khoId);

    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);

    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	//Sua ket theo kieu moi de lay bang cac phieu dieu chinh ton kho,dung cau lenh cu,khong lay duoc nguoi duyet.
    	
		//String query = "select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd, e.ten as kbh, f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e,                        kho f              where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and f.pk_seq = a.kho_fk and a.npp_fk='"+ nppId +"'";    	   	   	
    //	String	query = "select distinct a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd, e.ten as kbh, f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua, g.ten as nguoiduyet from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e, kho f, nhanvien g where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.nguoiduyet = g.pk_seq and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and f.pk_seq = a.kho_fk and a.npp_fk='"+ nppId +"'";
    String query=	"select distinct a.ngaydc, a.pk_seq as chungtu,d.ten as dvkd, e.ten as nhomkenh, "+
    	" f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua, isnull(g.ten,'0') as nguoiduyet "+ 
    	" from kiemkhotdv a inner join  nhanvien b on b.pk_Seq=a.nguoitao inner join nhanvien c on a.nguoisua=c.pk_Seq "+ 
    	" inner join daidienkinhdoanh d on d.pk_Seq=a.ddkd_fk  "+
    	" inner join  nhomkenh e on e.pk_seq=a.nhomkenh_fk inner join  kho f  on f.pk_Seq= a.kho_fk "+ 
    	" left join  nhanvien g  on g.pk_seq=a.nguoiduyet "+
    	" where  a.npp_fk='"+nppId+"'";
    	if (dvkdId.length()>0){
			query = query + " and a.ddkd_fk = '" + dvkdId + "'";
			
    	}
    	
//    	if (kbhId.length()>0){
//			query = query + " and a.kbh_fk = '" + kbhId + "'";
//			
//    	}

    	if (khoId.length()>0){
			query = query + " and a.nhomkenh_fk = '" + khoId + "'";
			
    	}

    	if (tungay.length()>0){
			query = query + " and a.ngaydc >= '" + tungay+ "'";
    		
    	}

    	if (denngay.length()>0){
			query = query + " and a.ngaydc <= '" + denngay+ "'";
    		
    	}

    	query = query + " order by a.trangthai, a.pk_seq";
    	System.out.println("1.Query dieu chinh ton kho: " + query);
    	return query;
    	
	}	
	private void chot(String dctkId,HttpServletRequest request) throws SQLException
	{		
		String msg="";
		dbutils db = new dbutils();
		String idnpp="";
		db.getConnection().setAutoCommit(false);
		Utility util  = new Utility();
		Utility_Kho util_kho=new Utility_Kho();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		String query = "select count(pk_seq) as num,npp_fk from kiemkhotdv where pk_seq='" + dctkId + "' and trangthai = '0' group by NPP_FK ";
		System.out.println("In num "+query);
		ResultSet rs = db.get(query);
		try
		{
			rs.next();
			if(!rs.getString("num").equals("0"))
			{
				idnpp=rs.getString("npp_Fk");
				rs.close();

				
				String sql="select k.ngaydc,k.ddkd_fk, k.npp_fk,(-1)* SUM(ISNULL(TONMOI, '0') - ISNULL(TONHIENTAI, '0')) AS dieuchinh,sp.giamua,sp.sanpham_fk,sp.solo,k.kho_fk," +
						"k.nhomkenh_fk,sp.NgayHetHan from kiemkhotdv_sp sp,kiemkhotdv k" +
						" where k.PK_SEQ=sp.kiemkhotdv_FK and k.pk_seq = '" + dctkId + "'";
				System.out.println("in sql "+sql);
				ResultSet rs1=db.get(sql);
				while(rs1.next())
				{
					System.out.println("____");
					
					double dieuchinh=rs1.getDouble("dieuchinh");
					String masp=rs1.getString("sanpham_fk");
					String solo=rs1.getString("solo");
					String kho_fk=rs1.getString("kho_fk");
					String nhomkenh_fk=rs1.getString("nhomkenh_fk");
					String npp=rs1.getString("npp_fk");
					String ngayhethan=rs1.getString("ngayhethan");
					String ngaydc =rs1.getString("ngaydc");
					String ddkdid = rs1.getString("ddkd_fk");
					double giamua = rs1.getDouble("giamua");
					
					idnpp=npp;
					String qr="update NHAPP_KHO_DDKD_CHITIET set soluong = "+dieuchinh+" , available=("+dieuchinh;
						qr+=") where ddkd_fk = '"+ddkdid+"' and kho_fk="+kho_fk+" and npp_fk="+npp +" and sanpham_fk="+masp +" and nhomkenh_fk="+nhomkenh_fk+" and solo='"+solo+"' and NgayHetHan='"+ngayhethan+"'";
						
						
					if(db.updateReturnInt(qr)!=1)
					{
						System.out.println("Lỗi NHAPP_KHO_DDKD_CHITIET "+qr);
						db.update("rollback");
						
					}
					
					msg=util_kho.Update_NPP_Kho_Sp_Chitiet_DDKD(ngaydc, "Kiểm kho TDV", db, kho_fk, masp, npp, nhomkenh_fk, ddkdid, solo, "NULL", ngayhethan,  "NULL", dieuchinh, 0.0, dieuchinh, giamua);
					if(msg.length()>0)
					{
						db.getConnection().rollback();
						
					}
						
//					qr="update NHAPP_KHO_DDKD  set soluong= "+dieuchinh+" , available="+dieuchinh;
//					qr+=" where ddkd_fk = '"+ddkdid+"' and kho_fk="+kho_fk+" and npp_fk="+npp +" and sanpham_fk="+masp +" and nhomkenh_Fk="+nhomkenh_fk+" ";
//				
//					if(db.updateReturnInt(qr)!=1)
//					{
//						System.out.println("Lỗi NHAPP_KHO_DDKD "+qr);
//						db.update("rollback");
//					}
					msg=util_kho.Update_NPP_Kho_Sp_DDKD(ngaydc, "Kiểm kho TDV", db, kho_fk, masp, npp, nhomkenh_fk, dieuchinh, 0.0, dieuchinh, giamua, ddkdid)	;	
					if(msg.length()>0)
					{
						db.getConnection().rollback();
						
					}
						
				
			
			}
				query = "update KIEMKHOTDV set trangthai = '1',nguoiduyet = '"+userId+"', NGAYCHOT = '"+getDateTime()+"' where pk_seq = '" + dctkId + "'";
				System.out.println(query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
				}
						   
			}	
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}
		catch(Exception e)
		{
			System.out.println("Error  : "+e.toString());
			e.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
		
	}
	private void Delete(String dctkId,HttpServletRequest request) throws SQLException
	{		
		dbutils db = new dbutils();
		String idnpp="";
		db.getConnection().setAutoCommit(false);
		
		String query = "select count(pk_seq) as num,npp_fk from kiemkhotdv where pk_seq='" + dctkId + "' and trangthai = '0' group by NPP_FK ";
		System.out.println(query);
		ResultSet rs = db.get(query);
		try
		{
			rs.next();
			if(!rs.getString("num").equals("0"))
			{
				idnpp=rs.getString("npp_Fk");
				rs.close();
				
//				String sql="select k.npp_fk,sp.dieuchinh,sp.sanpham_fk,sp.solo,k.kho_fk,k.kbh_fk,sp.NgayHetHan from kiemkhotdv_sp sp,dieuchinhtonkho k where k.PK_SEQ=sp.DIEUCHINHTONKHO_FK and k.pk_seq = '" + dctkId + "'";
//				ResultSet rs1=db.get(sql);
//				while(rs1.next())
//				{
//					System.out.println("____");
//					
//					int dieuchinh=rs1.getInt("dieuchinh");
//					String masp=rs1.getString("sanpham_fk");
//					String solo=rs1.getString("solo");
//					String kho_fk=rs1.getString("kho_fk");
//					String kbh_fk=rs1.getString("kbh_fk");
//					String npp=rs1.getString("npp_fk");
//					String ngayhethan=rs1.getString("ngayhethan");
//					
//					idnpp=npp;
//					if(dieuchinh<0)
//					{
//						String qr="update NHAPP_KHO_CHITIET set booked=booked + ("+dieuchinh+") , available=available-("+dieuchinh;
//						qr+=") where kho_fk="+kho_fk+" and npp_fk="+npp +" and sanpham_fk="+masp +" and kbh_fk="+kbh_fk+" and solo='"+solo+"' and NgayHetHan='"+ngayhethan+"'";
//					
//					if(db.updateReturnInt(qr)!=1)
//					{
//						db.update("rollback");
//					}
//						
//					qr="update nhapp_kho  set booked=booked + ("+dieuchinh+") , available=available-("+dieuchinh;
//					qr+=") where kho_fk="+kho_fk+" and npp_fk="+npp +" and sanpham_fk="+masp +" and kbh_Fk="+kbh_fk+" ";
//					if(db.updateReturnInt(qr)!=1)
//					{
//						db.update("rollback");
//					}
//								
//				}
//				
				query = "delete from kiemkhotdv_sp where kiemkhotdv_fk = '" + dctkId + "'";
				System.out.println(query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
				}
				
				query = "delete from kiemkhotdv where pk_seq='" + dctkId + "' and trangthai = '0'";
				System.out.println(query);
				if(db.updateReturnInt(query)!=1)
				{
					db.getConnection().rollback();
				}
			}
						   
				
			
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}
		catch(Exception e)
		{
			System.out.println("Error  : "+e.toString());
			e.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
		
	}
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
