package geso.traphaco.distributor.servlets.hangtralainpp;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.hangtralai.IErpHangTraLaiNpp;
import geso.traphaco.distributor.beans.hangtralai.IErpHangTraLaiNppList;
import geso.traphaco.distributor.beans.hangtralai.imp.ErpHangTraLaiNpp;
import geso.traphaco.distributor.beans.hangtralai.imp.ErpHangTraLaiNppList;

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

public class ErpHangTraLaiNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHangTraLaiNppSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHangTraLaiNppList obj;
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
	    obj = new ErpHangTraLaiNppList();
	    obj.setUserId(userId);
	    
	    if (action.equals("delete") )
	    {	
	    	String msg = this.DeleteChuyenKho(lsxId, userId);
			obj.setMsg(msg);
	    }
	    else if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId, userId);
			obj.setMsg(msg);
    	}
	    
	    obj.init("");
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHangTraLaiNpp.jsp";
		response.sendRedirect(nextJSP);
	}

	private String Chot(String id, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";
		
		Utility util = new Utility();
		
		msg= util.Check_Huy_NghiepVu_KhoaSo("Erp_HangTraLaiNpp", id, "NgayTra", db);
		if(msg.length()>0)
			return msg;
		
		try
		{
			db.getConnection().setAutoCommit(false);
			String 	query=
			"			select khoxuat_fk as kho_fk, npp_fk, kbh_fk, sp.PK_SEQ as spId, sp.TEN as spTEN , SUM(dathang.soluong) as soluongXUAT,   "+   
			"			SoLo as spSoLo,NgayHetHan  as spNgayHetHan  "+
			"		from        "+
			"		(        "+
			"			select c.kho_fk as khoxuat_fk, c.npp_fk, c.kbh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,  "+      
			"					case when a.dvdl_fk IS null then a.soluong         "+
			"						 when a.dvdl_fk = b.DVDL_FK then a.soluong        "+  
			"						 else  a.soluong * ( select SOLUONG1 from QUYCACH "+
			"							where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )  "+       
			"										 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk  "+
			"										  and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )	 end as soluong ,  "+
			"										  SoLo,a.NgayHetHan     "+
			"			from Erp_HangTraLaiNpp_sanpham a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  "+   
			"					inner join Erp_HangTraLaiNpp c on a.HangTraLai_fk = c.pk_seq       "+
			"			where a.HangTraLai_fk in (   '"+id+"'   )      and a.SoLuong>0 "+
			"		)       "+
			"		dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ "+    
			"		group by khoxuat_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN,SoLo,NgayHetHan  " ;
						
			ResultSet rs =db.get(query);
			String nppId = "";
			while(rs.next())
			{
				String spId = rs.getString("spId");
				String kbhId = rs.getString("kbh_fk");
				String khoId = rs.getString("kho_fk");
				nppId = rs.getString("npp_fk");
				String spTen = rs.getString("spTEN");
				
				String spSoLo = rs.getString("spSoLo");
				String spNgayHetHan = rs.getString("spNgayHetHan");
				
				double SoLuong = rs.getDouble("soluongXUAT");
				
				if(SoLuong==0)
				{
					msg="Sản phẩm "+spTen +" chưa khai báo quy cách !"; 
					db.getConnection().rollback();
					return msg;
				}
				
				
				query = "update NHAPP_KHO set SOLUONG = SOLUONG + '" + SoLuong + "', AVAILABLE = AVAILABLE + '" + SoLuong + "' " +
						"where npp_fk='"+nppId+"' and kbh_fk='"+kbhId+"' and kho_fk='"+khoId+"' and sanpham_fk='"+spId+"' ";
				if(db.updateReturnInt(query)!=1)
				{
					msg="Lỗi phát sinh khi cập nhật ! "+query; 
					db.getConnection().rollback();
					return msg;
				}
				
				query = 
						"select COUNT(*) as soDONG " +
						"from NHAPP_KHO_CHITIET where npp_fk='"+nppId+"' and kbh_fk='"+kbhId+"' and  Kho_fk='"+khoId+"' and sanpham_fk='"+spId+"' and SoLo='"+spSoLo +"' and NgayHetHan='"+spNgayHetHan+"' ";
				System.out.println("CHECK EXITS: " + query);
				
				ResultSet rsCHECK = db.get(query);
				int exits = 0;
				if(rsCHECK != null)
				{
					if(rsCHECK.next())
					{
						exits = rsCHECK.getInt("soDONG");
					}
					rsCHECK.close();
				}
				
				if(exits <= 0)
				{
					query = 
							"insert NHAPP_KHO_CHITIET(KHO_FK, NPP_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, KBH_FK, SOLO, NGAYHETHAN) " +
							"select '"+khoId+"','"+nppId+"','"+spId+"','"+SoLuong+"',0,'"+SoLuong+"','"+kbhId+"','"+spSoLo+"','"+spNgayHetHan+"' ";
				}
				else
				{
					query = " update CT  " +
									" set CT.SOLUONG = CT.SOLUONG + NH.soluongNHAN, " +
									" 			  CT.AVAILABLE = CT.AVAILABLE + NH.soluongNHAN, " +
									" 			  CT.NGAYHETHAN = NH.NGAYHETHAN " +
									" from " +
									" ( " +
									" select  '"+khoId+"' as kho_fk, '"+nppId+"' as   NPP_FK, '"+spId+"' as  SANPHAM_FK,'"+SoLuong+"' as  soluongNHAN, '"+kbhId+"' as   KBH_FK, '"+spSoLo+"'  as SOLO, '"+spNgayHetHan+"' as   NGAYHETHAN " +
									" ) " +
									" NH inner join NHAPP_KHO_CHITIET CT on NH.kho_fk = CT.KHO_FK and NH.NPP_FK = CT.NPP_FK and NH.KBH_FK = CT.KBH_FK and NH.SOLO = CT.SOLO and NH.SANPHAM_FK = CT.sanpham_fk and ct.NgayHetHan=NH.NgayHetHan ";
				}	
				
				if(db.updateReturnInt(query)!=1)
				{
					msg="Lỗi phát sinh khi cập nhật "+query; 
					db.getConnection().rollback();
					return msg;
				}
			}
			query = "update Erp_HangTraLaiNpp set trangthai = '1' where pk_seq = '" + id + "' and TrangThai=0 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg="Lỗi phát sinh khi cập nhật "+query; 
				db.getConnection().rollback();
				return msg;
			}
			
			msg= util.Check_Kho_Tong_VS_KhoCT(nppId, db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}

	private String DeleteChuyenKho(String lsxId, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "delete from Erp_HangTraLaiNpp_sanpham where HangTraLai_fk='"+lsxId+"' ";
			if(!db.update(query))
			{
				msg = "Không thể xóa " + query;
				db.getConnection().rollback();
				return msg;
			}
						
			query = "delete from Erp_HangTraLaiNpp where pk_seq='"+lsxId+"' and Trangthai=0 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Không thể cập nhật Erp_HangTraLaiNpp " + query;
				db.getConnection().rollback();
				return msg;
			}			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			e.printStackTrace();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();
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
	    
		IErpHangTraLaiNppList obj = new ErpHangTraLaiNppList();
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpHangTraLaiNpp lsxBean = new ErpHangTraLaiNpp();
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
	    	session.setAttribute("lsxBean", lsxBean);	    	
	    	session.setAttribute("kenhId", "");
	    	session.setAttribute("khoxuat", "" );
	    	session.setAttribute("nppId", lsxBean.getNppId());
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHangTraLaiNppNew.jsp";
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
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHangTraLaiNpp.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHangTraLaiNpp.jsp";
	    		response.sendRedirect(nextJSP);
	    		
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpHangTraLaiNppList obj)
	{
		String query =  "select a.PK_SEQ, a.trangthai, a.ngaychuyen, a.ghichu as lydo, NV.TEN as nguoitao, b.ten as khoxuat, c.ten as nppTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
						"from ERP_CHUYENKHONPP a inner join KHO b on a.khoxuat_fk = b.pk_seq inner join NHAPHANPHOI c on a.npp_dat_fk = c.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		
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
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		

		if(tungaySX.length() > 0)
			query += " and a.ngaychuyen >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.ngaychuyen <= '" + denngaySX + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(nppId.length() > 0)
			query += " and a.NPP_FK = '" + nppId + "'";
		
		System.out.print(query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
