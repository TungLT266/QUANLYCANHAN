package geso.traphaco.distributor.servlets.chuyenkho;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.chuyenkho.IErpChuyenkenhNpp;
import geso.traphaco.distributor.beans.chuyenkho.IErpChuyenkenhNppList;
import geso.traphaco.distributor.beans.chuyenkho.imp.ErpChuyenkenhNpp;
import geso.traphaco.distributor.beans.chuyenkho.imp.ErpChuyenkenhNppList;

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

public class ErpChuyenkenhNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpChuyenkenhNppSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpChuyenkenhNppList obj;
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
	    obj = new ErpChuyenkenhNppList();
	    obj.setUserId(userId);
	    
	    String type = request.getParameter("type");
	    if(type == null)
	    	type = "chuyenkenh";
	    obj.setType(type);
	    
	    if (action.equals("delete") )
	    {	
	    	String msg = this.DeleteChuyenKho(lsxId, userId);
			obj.setMsg(msg);
	    }
	    else if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId, userId);
    		System.out.println("___Chot__"+msg);
    		obj.setMsg(msg);
    	}
	    
	    obj.init("");
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpChuyenKenhNpp.jsp";
		response.sendRedirect(nextJSP);
	}

	private String Chot(String id, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			Utility util = new Utility();
			msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_ChuyenKenh", id, "NgayChuyen", db);
			if(msg.length()>0)
				return msg;
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_ChuyenKenh set trangthai = '1' where pk_seq = '" + id + "' and trangthai=0 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query=
			" 	select count(*)   as SoDong " +
			" 	from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk " +
			" 	where chuyenkenh_fk = '" + id + "' " ;
			
			System.out.println("::::_01"+query);
			int SoDong=0;
			ResultSet rs =db.get(query);
			 while(rs.next())
			 {
				 SoDong=rs.getInt("SoDong");
			 }
			 if(rs!=null)rs.close();
			 
			 
			 query = 
						"	select sp.MA as spMa,sp.TEN as spTen,kho.SOLO,kho.BOOKED,kho.AVAILABLE,CT.tongxuat,kho.SOLUONG  "+
						"	from   "+
						"	( "+
						"		select a.khoxuat_fk as kho_fk, a.npp_fk,a.Kbh_Fk , b.sanpham_fk, b.solo, "+ 
						"				SUM(b.soluong) as tongxuat,b.NgayHetHan    	 "+
						"		from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk "+  	
						"		where chuyenkenh_fk = '"+id+"'  	 "+
						"		group by a.khoxuat_fk, a.npp_fk, a.kbh_fk, b.solo, b.sanpham_fk,b.NgayHetHan "+  
						"	)  CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK   	 "+
						"	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO and CT.NPP_FK = kho.npp_fk "+
						"	and CT.KBH_FK = kho.kbh_fk  and ct.ngayHetHan=kho.NgayHetHan  "+
						"	inner join SANPHAM sp on sp.PK_SEQ=CT.SANPHAM_FK "+
						"	where kho.SOLUONG-CT.tongxuat<0 "; 
							rs=db.get(query);
							msg="";
							while(rs.next())
							{
								msg +=" Sản phẩm "+rs.getString("spMa") +""+rs.getString("spTen")+" Số Lô "+rs.getString("Solo")+" có số lượng xuất lớn hơn số lượng trong kho ("+rs.getDouble("TongXuat")+" > "+rs.getDouble("SOLUONG")+" ) \n" ; 
							}
							if(rs!=null)rs.close();
							
							if(msg.length()>0)
							{
								db.getConnection().rollback();
								return msg;
							}
			 
			 
			//TANG KHO NGUOC LAI
			query = " update kho set kho.SOLUONG = kho.SOLUONG - CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.khoxuat_fk as kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat,b.NgayHetHan   " +
					" 	from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk " +
					" 	where chuyenkenh_fk = '" + id + "' " +
					" 	group by a.khoxuat_fk, a.npp_fk, a.kbh_fk, b.solo, b.sanpham_fk,b.NgayHetHan  " +
					" ) " +
					" CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO and CT.NPP_FK = kho.npp_fk and CT.KBH_FK = kho.kbh_fk  and ct.ngayHetHan=kho.NgayHetHan ";
			
			System.out.println("::::_02"+query);
				if(db.updateReturnInt(query)!=SoDong)
			{
				msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
				db.getConnection().rollback();
				return msg;
			}
				
	
				 query = 
								"	select sp.MA as spMa,sp.TEN as spTen,kho.BOOKED,kho.AVAILABLE,CT.tongxuat,kho.SOLUONG  "+
								" from " +
								" ( " +
								" 	select a.khoxuat_fk as kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
								" 	from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk " +
								" 	where chuyenkenh_fk = '" + id + "' " +
								" 	group by a.khoxuat_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk " +
								" ) " +
								" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
								" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.NPP_FK = kho.npp_fk and CT.KBH_FK = kho.kbh_fk "+
								"	inner join SANPHAM sp on sp.PK_SEQ=CT.SANPHAM_FK "+
								"	where kho.SOLUONG-CT.tongxuat<0 "; 
								rs=db.get(query);
								msg="";
								while(rs.next())
								{
									msg +=" Sản phẩm "+rs.getString("spMa") +""+rs.getString("spTen")+" Số Lô "+rs.getString("Solo")+" có số lượng xuất lớn hơn số lượng trong kho ("+rs.getDouble("TongXuat")+" > "+rs.getDouble("SOLUONG")+" ) \n" ; 
								}
								if(rs!=null)rs.close();
								
								if(msg.length()>0)
								{
									db.getConnection().rollback();
									return msg;
								}
				
			
			
			query = " update kho set kho.SOLUONG = kho.SOLUONG - CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.khoxuat_fk as kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk " +
					" 	where chuyenkenh_fk = '" + id + "' " +
					" 	group by a.khoxuat_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.NPP_FK = kho.npp_fk and CT.KBH_FK = kho.kbh_fk ";
			
			System.out.println("::::_03"+query);
			if(db.updateReturnInt(query)<=0)
			{
				msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//TANG KHO CUA KENH NHAN
			query = " 	select a.khoxuat_fk as kho_fk, a.khonhan_fk, loaichuyen, a.npp_fk, a.kbh_nhan_fk, b.sanpham_fk, b.solo, b.ngayhethan, SUM(b.soluong) as tongnhan  " +
					" 	from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk " +
					" 	where chuyenkenh_fk = '" + id + "' " +
					" 	group by a.khoxuat_fk, a.khonhan_fk, loaichuyen, a.npp_fk, a.kbh_nhan_fk, b.solo, b.ngayhethan, b.sanpham_fk ";
			System.out.println("---CHECK TANG KHO NHAN: " + query);
			rs = db.get(query);
			
			String nppID = "";
			{
				while(rs.next())
				{
					String loaichuyen = rs.getString("loaichuyen");
					String khoID = rs.getString("kho_fk");  //CHUYEN KENH
					if(loaichuyen.equals("chuyenkho"))  //CHUYEN GIUA CAC KHO VOI NHAU
						khoID = rs.getString("khonhan_fk");
					
					nppID = rs.getString("npp_fk");
					String kbhID = rs.getString("kbh_nhan_fk");
					String spID = rs.getString("sanpham_fk");
					String solo = rs.getString("solo");
					String ngayhethan = rs.getString("ngayhethan");
					String tongnhan = rs.getString("tongnhan");
					
					query = " select count(*) as count from NHAPP_KHO_CHITIET " +
							"where NPP_FK = '" + nppID + "' and KBH_FK = '" + kbhID + "' and KHO_FK = '" + khoID + "' and SANPHAM_FK = '" + spID + "' and SOLO = '" + solo + "' and NgayHetHan='"+ngayhethan+"' ";
					int count = 0;
					ResultSet rsCHECK = db.get(query);
					if(rsCHECK.next())
					{
						count = rsCHECK.getInt("count");
						rsCHECK.close();
					}
					
					if(count > 0)
					{
						query = "Update NHAPP_KHO_CHITIET set soluong  = soluong + '" + tongnhan + "', available = available + '" + tongnhan + "' " +
								"where NPP_FK = '" + nppID + "' and KBH_FK = '" + kbhID + "' and KHO_FK = '" + khoID + "' and SANPHAM_FK = '" + spID + "' and SOLO = '" + solo + "' and NgayHetHan='"+ngayhethan+"' ";
					}
					else
					{
						query = "insert NHAPP_KHO_CHITIET(KHO_FK, NPP_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, KBH_FK, SOLO, NGAYHETHAN) " +
								"values('" + khoID + "', '" + nppID + "', '" + spID + "', '" + tongnhan + "', '0', '" + tongnhan + "', '" + kbhID + "', '" + solo + "', '" + ngayhethan + "')";
					}
					
					System.out.println("---CAP NHAT KHO CT: " + query);
					if(db.updateReturnInt(query) != 1 )
					{
						msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
						db.getConnection().rollback();
						return msg;
					}
					
					query = "Update NHAPP_KHO set soluong  = soluong + '" + tongnhan + "', available = available + '" + tongnhan + "' " +
							"where NPP_FK = '" + nppID + "' and KBH_FK = '" + kbhID + "' and KHO_FK = '" + khoID + "' and SANPHAM_FK = '" + spID + "'  ";
					System.out.println("---CAP NHAT KHO TONG: " + query);
					if(db.updateReturnInt(query) != 1 )
					{
						msg = "Không thể cập nhật NHAPP_KHO " + query;
						db.getConnection().rollback();
						return msg;
					}
					
				}
				rs.close();
			}
			msg= util.Check_Kho_Tong_VS_KhoCT(nppID, db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		
		return "";
	}

	private String DeleteChuyenKho(String lsxId, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			Utility util = new Utility();
			msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_ChuyenKenh", lsxId, "NgayChuyen", db);
			if(msg.length()>0)
				return msg;
		
			
			db.getConnection().setAutoCommit(false);

			String query = "update ERP_ChuyenKenh set trangthai = '2' where pk_seq = '" + lsxId + "' and trangthai=0 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//TANG KHO NGUOC LAI
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.khoxuat_fk as kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat ,b.ngayhethan  " +
					" 	from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk " +
					" 	where chuyenkenh_fk = '" + lsxId + "' " +
					" 	group by a.khoxuat_fk, a.npp_fk, a.kbh_fk, b.solo, b.sanpham_fk ,b.ngayhethan " +
					" ) " +
					" CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO and CT.NPP_FK = kho.npp_fk and CT.KBH_FK = kho.kbh_fk and CT.ngayhethan=kho.NGAYHETHAN ";
			
			System.out.println("::_Tang_"+query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.khoxuat_fk as kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_CHUYENKENH a inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkenh_fk " +
					" 	where chuyenkenh_fk = '" + lsxId + "' " +
					" 	group by a.khoxuat_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.NPP_FK = kho.npp_fk and CT.KBH_FK = kho.kbh_fk ";
			
			System.out.println("::_Giam_"+query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return msg;
			}

			msg= util.Check_Kho_Tong_VS_KhoCT(util.getIdNhapp(userId), db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		
		return "";
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
	    
		IErpChuyenkenhNppList obj = new ErpChuyenkenhNppList();
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    String type = request.getParameter("type");
	    if(type == null)
	    	type = "chuyenkenh";
	    obj.setType(type);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpChuyenkenhNpp lsxBean = new ErpChuyenkenhNpp();
	    	lsxBean.setType(type);
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
    		
	    	session.setAttribute("khoxuatID", "");
			session.setAttribute("kenhxuatID", "");
			session.setAttribute("nppID", "");
			
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpChuyenKenhNppNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpChuyenKenhNpp.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setUserId(userId);
		    	obj.init(search);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpChuyenKenhNpp.jsp";
	    		response.sendRedirect(nextJSP);
	    		
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpChuyenkenhNppList obj)
	{
		String query =  "select a.PK_SEQ, a.trangthai, a.ngaychuyen, a.ghichu as lydo, NV.TEN as nguoitao, b.ten as khoxuat, c.diengiai as kbhTEN, d.diengiai as kbhNHANTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.loaichuyen, 'chuyenkenh') as loaichuyen   " +
						"from ERP_CHUYENKENH a inner join ERP_KHOTT b on a.khoxuat_fk = b.pk_seq   " +
						"	inner join KENHBANHANG c on a.kbh_fk = c.pk_seq inner join KENHBANHANG d on a.kbh_nhan_fk = d.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 and a.loaichuyen = '" + obj.getType() + "' ";
		
		String nppId = request.getParameter("nppId");
		query += " and a.NPP_FK = '" + nppId + "' ";
		
		String tungaySX = request.getParameter("tungay");
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);
		
		String denngaySX = request.getParameter("denngay");
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String ctId = request.getParameter("chungtu");
		System.out.println("ma ne:" +ctId);
		if(ctId == null)
			ctId = "";
		obj.setctId(ctId);
		

		if(tungaySX.length() > 0)
			query += " and a.ngaychuyen >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.ngaychuyen <= '" + denngaySX + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(ctId.length() > 0)
			query += " and a.PK_SEQ like'%" + ctId + "%'";
		
		System.out.print("Search:" + query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
