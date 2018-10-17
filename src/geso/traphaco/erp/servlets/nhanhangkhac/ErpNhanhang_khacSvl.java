package geso.traphaco.erp.servlets.nhanhangkhac;

import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhanhangkhac.*;
import geso.traphaco.erp.beans.nhanhangkhac.imp.*;
import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.erp.util.Library;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.management.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhanhang_khacSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpNhanhang_khacSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpNhanhangList_khac obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	  
	     
	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String loaidh = util.antiSQLInspection(request.getParameter("loai"));
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
 
	    String action = util.getAction(querystring);
	    
	    String nhId = util.getId(querystring);
	    
	    String msg = "";
	    String ctyId = "";
	    
	    obj = new ErpNhanhangList_khac();
	    ctyId = (String)session.getAttribute("congtyId") ;
	    
	    String nppId = (String)session.getAttribute("nppId") ;
	   
	    
	    obj.setCongtyId(ctyId);
	    obj.setUserId(userId);
	    obj.setLoaimh(loaidh);
	    
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
						
			String lhhId = request.getParameter("lhhId");
    		if(lhhId == null)
    			lhhId = "";
    		String loai = request.getParameter("loai");
    		if(loai == null)
    			loai = "";
    		
    		msg = ChotNhanHang(nhId, userId, ctyId, lhhId,nppId);
    		session.setAttribute("userId", userId);
    		   		
    		obj.setLoaimh(loai);
    		obj.setCongtyId(ctyId);
			obj.init("");	
			obj.setmsg(msg);
			
	    	session.setAttribute("obj", obj);  	
    
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHang_khac.jsp");
			
    		return;
	    }
	    else if (action.equals("mochot"))
	    {	
	    	String lhhId = request.getParameter("lhhId");
    		if(lhhId == null)
    			lhhId = "";
    		String loai = request.getParameter("loai");
    		if(loai == null)
    			loai = "";
    		
	    	msg = MoChotNhanHang(nhId, userId, ctyId, lhhId);
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
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHang_khac.jsp";
		response.sendRedirect(nextJSP);
	}
	
	

	Hashtable<String, Hashtable<String, Long> > nhan_form_choSelected = null; 
	
	private String ChotNhanHang(String nhId, String userId, String ctyId, String lhhId,String nppfk) 
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			Utility util = new Utility();

			
			Library lib =new Library();
			if(!lib.check_trangthaihople(nhId, "erp_nhaNhang", "0", db)){
				// khac trang thai 0 thì hk cho chot
				db.getConnection().rollback();
				return "Không thể chốt đơn hàng này: phiếu này đã được chốt hoặc trạng thái không hợp lệ! ";
			}
			//TANWG KHO NGAY VAO LUON
			// tăng kho chi tiết
			String sql = " select ISNULL(NSX_FK,0) AS NSX_FK,isnull( a.hamam,'0') as  hamam,isnull( a.hamluong,'100') as  hamluong,isnull( a.phieueo,'') as  phieueo,isnull( a.MAPHIEU,'') as  MAPHIEU,isnull( a.phieudt,'') as  phieudt,C.DOITUONG_FK ,c.KHONHAN_FK KHONHAN , a.SOLO, isnull( a.MATHUNG,'') as  MATHUNG,isnull( a.MAME,'') as mame  ,  isnull(a.BIN_FK,0) BIN_FK, a.SOLUONG,a.NGAYHETHAN, a.NGAYSANXUAT, c.NGAYNHAN," +
				  " a.SANPHAM_FK,a.GIATHEOLO , isnull(a.MARQ,'') as mamarquette " +
				  " from ERP_NHANHANG_SP_CHITIET a "+
				  " inner join ERP_NHANHANG c on c.PK_SEQ = a.NHANHANG_FK "+
				  " where  a.NHANHANG_FK = "+nhId; 
			

			System.out.println("dang sach nhan hang"+ sql);
			ResultSet rs = db.get(sql);
			 
				while(rs.next()){
					double soluong= rs.getDouble("SOLUONG");
					
			
					String msg1 = util.Update_KhoTT_MOI(rs.getString("NGAYNHAN"), "Tăng kho nhập hàng khác " + nhId, db, rs.getString("Khonhan"),  rs.getString("SANPHAM_FK"), rs.getString("solo"), rs.getString("ngayhethan"), rs.getString("NGAYNHAN"), 
							rs.getString("mame"), rs.getString("mathung"),rs.getString("bin_fk"), rs.getString("MAPHIEU"), rs.getString("phieudt"), rs.getString("phieueo"),rs.getString("mamarquette"),rs.getString("hamluong"),rs.getString("hamam"), "", "", soluong,0,soluong,rs.getString("NSX_FK"));			
						
					if( msg1.length() >0)
						{
						   db.getConnection().rollback();
							db.shutDown();
					    	return msg1;
						}
					    
					 
				}
				rs.close();
			
				
			
				String query=" UPDATE erp_nhaNhang  SET TRANGTHAI=1 ,KHONHAN_FK=(  SELECT KHONHAN_FK FROM ERP_YEUCAUNHAPHANG  where pk_seq=(select YEUCAUNHAPHANG_FK  from erp_nhanhang  where PK_SEQ= "+nhId+") ) WHERE PK_SEQ="+nhId +" and   trangthai=0";
				
				if(!db.update(query))
				{
					db.getConnection().rollback();
					db.shutDown();
					return "Không thể chôt nhận hàng - SP: " + query;
				}
			String trangthai="2";
			if(CheckDahoantat(db,nhId)){
				  trangthai="3";	
			}
			query=" UPDATE ERP_YEUCAUNHAPHANG SET TRANGTHAI="+trangthai+"  " +
					" WHERE PK_SEQ=(select YEUCAUNHAPHANG_FK  from erp_nhanhang  where PK_SEQ= "+nhId+")";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.shutDown();
				return "Không thể chôt nhận hàng - SP: " + query;
			}
			
		
			String msg1=lib.capnhatketoan_nhanhangkhac(db,nhId,ctyId);
			if(msg1.trim().length()>0)
			{
				db.getConnection().rollback();
				db.shutDown();
				return msg1;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) {
				e1.printStackTrace();
			}
			
			db.shutDown();
			
			return "Không thể chôt nhận hàng: " + e.getMessage();
		}
	}
 	 
	public boolean CheckDahoantat(dbutils db, String idnhanhang ){
	
		String query = 
				"SELECT DATA.SANPHAM_FK, DATA.SOLUONGYEUCAU, ISNULL(DATA.SOLUONGDANHAN,0) AS SOLUONGDANHAN \n" +   
				"FROM ( \n" + 
				"	SELECT MHSP.SANPHAM_FK, MHSP.SOLUONGYEUCAU, (SELECT SUM( NHSP.SOLUONGNHAN ) FROM erp_nhanhang NH \n" +    
				"	INNER JOIN erp_nhanhang_SANPHAM NHSP ON NH.PK_SEQ = NHSP.NHANHANG_FK \n" +  
				"	WHERE NH.YEUCAUNHAPHANG_FK = MH.PK_SEQ AND NHSP.SANPHAM_FK = MHSP.SANPHAM_FK AND NH.TRANGTHAI IN (1) ) AS SOLUONGDANHAN \n" +   
				"	FROM ERP_YEUCAUNHAPHANG MH \n" +   
				"	INNER JOIN ERP_YEUCAUNHAPHANG_SANPHAM MHSP ON MH.PK_SEQ = MHSP.YEUCAUNHAPHANG_FK \n" +   
				"	WHERE MH.PK_SEQ = (select YEUCAUNHAPHANG_FK from erp_nhanhang where PK_SEQ = " + idnhanhang + ") \n" +    
				") DATA WHERE DATA.SOLUONGYEUCAU - ISNULL(DATA.SOLUONGDANHAN,0) > 0";
		
		ResultSet  kt = db.get(query);
		try{
			 
			if(kt.next()){
				//dang con du lieu co the nhan
				kt.close();
				return false;
			}
			kt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
			
		}
		return true;
	}
			
	private String MoChotNhanHang(String nhId, String userId, String ctyId, String lhhId) 
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			Utility util = new Utility();

			//TANWG KHO NGAY VAO LUON
			// tăng kho chi tiết
			String sql = " select isnull( a.hamam,'0') as  hamam,isnull( a.hamluong,'100') as  hamluong,isnull( a.phieueo,'') as  phieueo,isnull( a.MAPHIEU,'') as  MAPHIEU,isnull( a.phieudt,'') as  phieudt,C.DOITUONG_FK ,B.KHONHAN , a.SOLO, isnull( a.MATHUNG,'') as  MATHUNG,isnull( a.MAME,'') as mame  ,  isnull(a.BIN_FK,0) BIN_FK, a.SOLUONG,a.NGAYHETHAN, a.NGAYSANXUAT, c.NGAYNHAN," +
					  " a.SANPHAM_FK,   ISNULL(NSX_FK,0) AS NSX_FK ,isnull(a.MARQ,'') as mamarquette " +
					  " from ERP_NHANHANG_SP_CHITIET a inner join ERP_NHANHANG_SANPHAM b " +
					  " on a.NHANHANG_FK = b.NHANHANG_FK and a.SANPHAM_FK = b.SANPHAM_FK    " +
					  " inner join ERP_NHANHANG c on c.PK_SEQ = b.NHANHANG_FK "+
					  " where  a.NHANHANG_FK = "+nhId; 
				


			ResultSet rs = db.get(sql);
			 
			while(rs.next()){
				double soluong= rs.getDouble("SOLUONG");
				String msg1 = util.Update_KhoTT_MOI(rs.getString("NGAYNHAN"), "Giảm kho nhập hàng khác " + nhId, db, rs.getString("Khonhan"),  rs.getString("SANPHAM_FK"), rs.getString("solo"), rs.getString("ngayhethan"), rs.getString("NGAYNHAN"), 
						rs.getString("mame"), rs.getString("mathung"),rs.getString("bin_fk"), rs.getString("MAPHIEU"), rs.getString("phieudt"), rs.getString("phieueo"),rs.getString("mamarquette"),rs.getString("hamluong"),rs.getString("hamam"), "", "", (-1)*soluong,0,(-1)*soluong,rs.getString("NSX_FK"));		

				
				if( msg1.length() >0)
					{
					   db.getConnection().rollback();
						db.shutDown();
				    	return msg1;
					}
			}
			rs.close();
		
			String query=" UPDATE erp_nhaNhang SET TRANGTHAI = 0, KHONHAN_FK = ( SELECT KHONHAN_FK FROM ERP_YEUCAUNHAPHANG where pk_seq = (select YEUCAUNHAPHANG_FK from erp_nhanhang where PK_SEQ = "+nhId+") ) WHERE PK_SEQ="+nhId;
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.shutDown();
				return "Không thể chôt nhận hàng - SP: " + query;
			}
			
			query = " UPDATE ERP_YEUCAUNHAPHANG SET TRANGTHAI='2', conhanhang = 1 " +
					" WHERE PK_SEQ = (select YEUCAUNHAPHANG_FK from erp_nhanhang where PK_SEQ = "+nhId+")";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.shutDown();
				return "Không thể chôt nhận hàng - SP: " + query;
			}
						
			Library lib=new Library();
			 String msg= lib.Revert_KeToan_loaichungtu(db,"Nhập hàng khác",nhId);
			 if(msg.length()>0){
				 return msg;
			 }
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) {
				e1.printStackTrace();
			}
			
			db.shutDown();
			
			return "Không thể chôt nhận hàng: " + e.getMessage();
		}
	}
	
	public boolean CheckCaNhanHang(dbutils db, String idnhanhang ){
		
		String query = 
				"select count(*) num from erp_nhanhang where pk_seq = " + idnhanhang + " and trangthai <> 3";
		
		ResultSet  kt = db.get(query);
		try{
			 
			if(kt.next()){
				if(kt.getInt("num") <= 0)
				{
					kt.close();
					return false;
				}
			}
			kt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
			
		}
		return true;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNhanhangList_khac obj;
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
	    String loaimh = util.antiSQLInspection(request.getParameter("loaimh")); 
	    
	    String  ctyId = (String)session.getAttribute("congtyId") ;
  	    
  	    
	    System.out.println("Loai "+loaimh);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpNhanhang_khac nhBean = new ErpNhanhang_khac();
	    	nhBean.setUserId(userId);
	    	 
	    	nhBean.setCongtyId(ctyId);
	  	    
	    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	nhBean.setLoaimh(loaimh);
	    	session.setAttribute("khonhanid",null);
	    	nhBean.createRs();
	    	session.setAttribute("nhBean", nhBean);
	    	session.setAttribute("spList", "");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_khac.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpNhanhangList_khac();
	    		obj.setUserId(userId);
		  	    
	    		obj.setCongtyId(ctyId);
		    	
		    	
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setLoaimh(loaimh);
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHang_khac.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpNhanhangList_khac();
		    	obj.setUserId(userId);

	    		obj.setCongtyId(ctyId);
		    	obj.setLoaimh(loaimh);
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
			
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHang_khac.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpNhanhangList_khac obj)
	{ 
		String   query = " SELECT   A.PK_SEQ AS NHID, yc.PK_SEQ as SOYEUCAU, "+
								" A.NGAYNHAN, B.TEN AS DVTHTEN,  "+
								" CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, "+ 
								" A.TRANGTHAI, A.NGAYSUA, A.NGAYTAO, D.TEN AS NGUOITAO, E.TEN AS NGUOISUA "+ 
								" FROM ERP_NHANHANG  A   "+
								" INNER JOIN ERP_DONVITHUCHIEN B ON A.DONVITHUCHIEN_FK = B.PK_SEQ "+ 
								" INNER JOIN ERP_YEUCAUNHAPHANG YC ON YC.PK_SEQ=A.YEUCAUNHAPHANG_FK  "+ 
								" INNER JOIN NHANVIEN D ON A.NGUOITAO = D.PK_SEQ   "+
								" INNER JOIN NHANVIEN E ON A.NGUOISUA = E.PK_SEQ    \n"+
								" where   a.congty_fk = '" + obj.getCongtyId() + "' ";
		
		 	  
		
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
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sonhanhang = request.getParameter("sonhanhang");
		if(sonhanhang == null)
			sonhanhang = "";
		obj.setSoNhanhang(sonhanhang);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSoHoadon(sohoadon);
		
		String ncc = request.getParameter("ncc");
		if(ncc == null)
			ncc = "";
		obj.setNCC(ncc);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitaoIds(nguoitao);
		
		String mactsp = request.getParameter("mactsp");
		if(mactsp == null)
			mactsp = "";
		obj.setMaCtSp(mactsp);
		String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
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
		
		if(sonhanhang.trim().length() > 0)
		{
			query += " and b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) like N'%" + sonhanhang + "%'  ";
		}
		
		if(sohoadon.trim().length() > 0)
		{
			query += " and a.SOHOADON like N'%" + sohoadon + "%' ";
		}
	 
		
		if(mactsp.trim().length() > 0)
		{
			query +=" and a.pk_seq in (" +
					"     select distinct nhanhang_fk from erp_nhanhangkhac_sanpham where sanpham_fk in ( select distinct pk_seq from ERP_SANPHAM where MA like N'%" + mactsp + "%' and congty_fk="+obj.getCongtyId()+" ) " +
					" ) ";
		}
		 

		System.out.println(query);
		return query;
	}
	
	private String Delete(String nhId)
	{
		dbutils db = new dbutils();
		 
		try 
		{
			db.getConnection().setAutoCommit(false);
			Library lib =new Library();
			if(!lib.check_trangthaihople(nhId, "erp_nhaNhang", "0", db)){
				// khac trang thai 0 thì hk cho chot
				db.getConnection().rollback();
				return "Không thể xoá đơn hàng này: phiếu này đã được chốt hoặc trạng thái không hợp lệ! ";
			}
			String query = "";
			
			// Kiểm tra nhận hàng phải tự động không
			query = "select isnull(istudong,0) istudong from erp_nhanhang  where pk_seq = '" + nhId + "'";
			ResultSet rs = db.get(query);
			int istudong = 0;
			
			if(rs!= null){
				while(rs.next()){
					istudong = rs.getInt("istudong");
				}rs.close();
					
			}
			
			// Nhận hàng tự động khi xóa phải trừ kho Ký gửi
			if(istudong > 0)
			{
				db.getConnection().rollback();
				return "Phiếu nhận hàng này tự động phát sinh. Bạn không được xóa ";
			}
			
			query = "update erp_nhanhang set trangthai = '3' where pk_seq = '" + nhId + "' and trangthai=0";
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Không thể cập nhật erp_nhanhang: " + query;
			}
			
			String conhanhang = "0";
			if(CheckCaNhanHang(db,nhId)){
				conhanhang = "1";	
			}
			query = " UPDATE ERP_YEUCAUNHAPHANG SET conhanhang = " + conhanhang + 
					" WHERE PK_SEQ = (select YEUCAUNHAPHANG_FK from erp_nhanhang where PK_SEQ = "+nhId+")";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.shutDown();
				return "Không thể chôt nhận hàng - SP: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (Exception e)
		{ 
			db.shutDown(); 
			return "Khong the xoa don mua hang"; 
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
