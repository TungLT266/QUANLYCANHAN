package geso.traphaco.center.servlets.banggia;

import geso.traphaco.center.beans.banggia.IBangGia;
import geso.traphaco.center.beans.banggia.IBangGiaList;
import geso.traphaco.center.beans.banggia.imp.BangGia;
import geso.traphaco.center.beans.banggia.imp.BangGiaList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.util.UtilitySyn;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BangGiaSvl")
public class BangGiaSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public BangGiaSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    
	    
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	msg = Delete(khlId,userId);
	    }
	    
	    if(action.trim().equals("copy"))
	    {
	    	msg = copy_banggia(khlId, userId);
	    }
	    
	    IBangGiaList obj = new BangGiaList();
	    obj.setUserId(userId);

	    obj.init("");
	    obj.setMsg(msg);
	    session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Center/BangGia.jsp";
		response.sendRedirect(nextJSP);
	}

	private String Delete(String id,String userId)
	{
		dbutils db = new dbutils();
		try
		{
			String 
			query = 
				"	 SELECT COUNT(*) AS SoDong "+    
				"	 FROM DONDATHANG DH "+ 
				"	 WHERE EXISTS "+
				"	 ( "+
				"		SELECT * FROM "+
				"		BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
				"		B ON A.PK_SEQ=B.BANGGIAMUANPP_FK  "+
				"		WHERE  B.NPP_FK=DH.NPP_FK "+
				"		AND A.KENH_FK=DH.KBH_FK AND A.DVKD_FK=DH.DVKD_FK "+
				"		AND A.TUNGAY<=DH.NGAYDAT AND DH.TRANGTHAI!=6 "+
				"		AND A.PK_SEQ='"+id+"' "+
				"	 ) " ;
			System.out.println("[DONDATHANG]" + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				return "Bảng giá đã được sử dụng trong đơn đặt hàng,vui lòng kiểm tra lại !";
			}
			query = 
			" SELECT COUNT(*) AS SoDong "+    
			"  FROM DONHANG DH "+
			" WHERE EXISTS "+
			"  ( "+
			"	SELECT * FROM  "+
			"	BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
			"	B ON A.PK_SEQ=B.BANGGIAMUANPP_FK "+
			"	WHERE  B.NPP_FK=DH.NPP_FK "+
			"	AND A.KENH_FK=DH.KBH_FK "+
			"	AND A.TUNGAY<=DH.NGAYNHAP AND DH.TRANGTHAI!=2 "+
			"	AND A.PK_SEQ='"+id+"' "+
			"  ) ";
			
			rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				return "Bảng giá đã được sử dụng trong đơn hàng ,vui lòng kiểm tra lại !";
			}
			
			db.getConnection().setAutoCommit(false);
		
			query=
			"  INSERT INTO BANGGIA_LOG(NGUOISUA,BG_FK,SANPHAM_FK,GIA) "+
			"  SELECT '"+userId+"',BGMUANPP_FK,SANPHAM_FK,GIAMUANPP "+
			" FROM BGMUANPP_SANPHAM "+
			" WHERE BGMUANPP_FK='"+id+"' ";
			if (!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			query = "delete from bgmuanpp_sanpham where bgmuanpp_fk='" + id + "'";
			if (!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			query = "delete from BANGGIAMUANPP_NPP where BANGGIAMUANPP_FK='" + id + "'";
			if (!db.update(query))
			{
				System.out.print(query);
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			query = "delete from banggiamuanpp where pk_seq = '" + id + "'";
			if (!db.update(query))
			{
				System.out.print(query);
				db.getConnection().rollback();
				return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "BGMUANPP_SANPHAM", "BGMUANPP_SANPHAM", "BGMUANPP_FK", id, "2", true);
			UtilitySyn.SynData(db, "BANGGIAMUANPP_NPP", "BANGGIAMUANPP_NPP", "BANGGIAMUANPP_FK", id, "2", true);
			UtilitySyn.SynData(db, "BANGGIAMUANPP", "BANGGIAMUANPP", "PK_SEQ", id, "2", true);
		} 
		catch (Exception e)
		{
			db.update("rollback");
			return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}

	
	private String copy_banggia(String id,String userid)
	{
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			String query="";
			query = " insert BANGGIAMUANPP  (TEN,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,DVKD_FK,KENH_FK,TRANGTHAI,TUNGAY,DENNGAY,CREATED_DATE,CHIETKHAU)	" +
					 " select TEN, convert (char(10),GETDATE(),120), convert (char(10),GETDATE(),120),NGUOITAO,NGUOISUA,DVKD_FK,KENH_FK,0,'2030-12-30',DENNGAY,GETDATE(),CHIETKHAU from BANGGIAMUANPP where PK_SEQ="+id;
			
		
			if(!db.update(query))
			{
				System.out.println(" errotr uery bang gia ma "+query);
				db.getConnection().rollback();
				return "Không thể copy bảng giá,vui lòng liên hệ Admin" + query;
	
			}
			
			query="select scope_identity() as ID ";
			ResultSet rs = db.get(query);
			String idcopy="";
			if (rs.next())
			{
				idcopy = rs.getString("ID");
			}
			if(idcopy.length()>0)
			{
				query="insert BGMUANPP_SANPHAM (BGMUANPP_FK,SANPHAM_FK,GIAMUANPP,TRANGTHAI,GIAMUANPP_TUVC,GIAMUA_SAUCK,chietkhau)  "+
					  " 	 select "+idcopy+",SANPHAM_FK,GIAMUANPP,TRANGTHAI,GIAMUANPP_TUVC,GIAMUA_SAUCK,chietkhau "+
					  "	from  BGMUANPP_SANPHAM where BGMUANPP_FK="+id;
				
				if(!db.update(query))
				{
					System.out.println("error "+query);
					db.getConnection().rollback();
					return "Không thể copy bảng giá,vui lòng liên hệ Admin" + query;
		
				}
				
				query="insert BANGGIAMUANPP_NPP (BANGGIAMUANPP_FK,NPP_FK,ChietKhau )  "+
						  "	select "+idcopy+",NPP_FK,ChietKhau from BANGGIAMUANPP_NPP where BANGGIAMUANPP_FK="+id;
				
					if(!db.update(query))
					{
						System.out.println("error "+query);
						db.getConnection().rollback();
						return "Không thể copy bảng giá,vui lòng liên hệ Admin" + query;
			
					}
				
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			db.update("rollback");
			return "Không thể xóa bảng giá,vui lòng liên hệ Admin" + e.getMessage();
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
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    
	    IBangGiaList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IBangGia khl = new BangGia();
    		khl.setUserId(userId);
    		khl.createRs();
	    	session.setAttribute("csxBean", khl);  	
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/TraphacoHYERP/pages/Center/BangGiaNew.jsp");
	    }
	    else
	    {
	    	obj = new BangGiaList();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/BangGia.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IBangGiaList obj) 
	{
		Utility util= new Utility();
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
	  	if ( nppId == null)
	  		nppId = "";
	  	obj.setNppId(nppId);
		
		String tenbg = request.getParameter("bgTen");
		if(tenbg == null)
			tenbg = "";
		obj.setMa(tenbg);
		
		String dvkdId = request.getParameter("dvkdId");
		if(dvkdId == null)
			dvkdId = "";
		obj.setDvkdId(dvkdId);
		
		String kenhId = request.getParameter("kenhId");
		if(kenhId == null)
			kenhId = "";
		obj.setKenhId(kenhId);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String vung = request.getParameter("vungid");
		if(vung == null)
			vung = "";
		obj.setVungId(vung);
		
		String diaban = request.getParameter("diabanid");
		if(diaban == null)
			diaban = "";
		obj.setDiabanId(diaban);
		
		String diengiai = request.getParameter("diengiai");
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		String fromVung = "";
		String whereVung = "";
		
		if(vung.length() > 0 )
		{
			fromVung =" ,BANGGIAMUANPP_NPP f, NHAPHANPHOI npp, khuvuc kv ";
			whereVung ="  and a.PK_SEQ = f.BANGGIAMUANPP_FK and f.NPP_FK=npp.PK_SEQ and npp.KHUVUC_FK= kv.PK_SEQ 		 ";
		}
		
		String fromDiaban = "";
		String whereDiaban = "";
		
		if(diaban.length() > 0 )
		{
			fromVung="";
			whereVung="";
			fromDiaban =" ,BANGGIAMUANPP_NPP f, NHAPHANPHOI npp, khuvuc kv  ";
			whereDiaban ="  and a.PK_SEQ = f.BANGGIAMUANPP_FK and f.NPP_FK=npp.PK_SEQ and npp.KHUVUC_FK = kv.PK_SEQ 		 ";
		}
		
		String sql = 
				  " select distinct a.pk_seq, a.ten, a.trangthai , d.ten as nguoitao, a.ngaytao as ngaytao,  " +
				  " 	e.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, c.ten as kenh, c.pk_seq as kenhId," +
				  " 	a.TrangThai,a.TuNgay " +
				  " from BANGGIAMUANPP a, donvikinhdoanh b, kenhbanhang c, nhanvien d, nhanvien e   " + fromVung + fromDiaban +
				  " where a.dvkd_fk = b.pk_seq and a.KENH_FK = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq   " + whereVung + whereDiaban +
				  "		 " ;
		if(tenbg.length() > 0)
			sql += " and  dbo.ftBoDau(a.ten) like N'%" + util.replaceAEIOU(tenbg) + "%' ";
		
		if(dvkdId.length() > 0)
			sql += " and a.dvkd_fk = '" + dvkdId + "' ";
		
		if(kenhId.length() > 0)
			sql += " and a.KENH_FK = '" + kenhId + "' ";
		
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		if(vung.length() > 0)
			sql += " and kv.vung_fk = '" + vung + "' ";
		
		if(diaban.length() > 0)
			sql += " and npp.tinhthanh_fk = '" + diaban + "' ";
		
		if(diengiai.trim().length() > 0)
			sql += " and a.pk_seq in ( select BANGGIAMUANPP_FK from BANGGIAMUANPP_NPP where NPP_FK in ( select PK_SEQ from NhaPhanPhoi where dbo.ftBoDau( Ma + ' ' + Ten ) like N'%" + util.replaceAEIOU(diengiai) + "%'  ) ) ";
		return sql;
	}
}
