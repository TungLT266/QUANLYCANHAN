package geso.traphaco.erp.servlets.congtytrahang;
 
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.congtytrahang.IErpCongtytrahang;
import geso.traphaco.erp.beans.congtytrahang.IErpCongtytrahangList;
import geso.traphaco.erp.beans.congtytrahang.imp.ErpCongtytrahang;
import geso.traphaco.erp.beans.congtytrahang.imp.ErpCongtytrahangList;
import geso.traphaco.erp.beans.donmuahang.IErpDonmuahangList_Giay;
import geso.traphaco.erp.beans.donmuahang.imp.ErpDonmuahangList_Giay;
import geso.traphaco.erp.util.DinhDang;
import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCongtytrahangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpCongtytrahangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ErpCongtytrahangList obj;
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
	    
	    String dmhId = util.getId(querystring);
	    
	    obj = new ErpCongtytrahangList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setIsdontrahang("2");
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(dmhId);
	    	if(msg.length() > 0)
	    		obj.setmsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		dbutils db = new dbutils();
	    		
	    		// KIEM TRA CÓ CO CHE DUYET HK NÊU HK --> DUYET LUN
	    		try {
	    			String query="";
					ResultSet rs = db.get("SELECT COUNT (*) AS CODUYET FROM ERP_DUYETMUAHANG WHERE MUAHANG_FK='"+dmhId +"'");
					int bien_dem=0;
					if (rs.next()) {
						bien_dem= rs.getInt("CODUYET");
					}
					rs.close();
					
					if(bien_dem==0){ // KO CO CO CHE DUYET
						query = "update ERP_MUAHANG set trangthai = 1, DACHOT = '1'  where pk_seq = '" + dmhId + "' and trangthai=0 and DACHOT=0";
						if (db.updateReturnInt(query)!=1) {
							obj.setmsg("Chốt không thành công, vui lòng kiểm tra lại chứng từ");
							
						}
						rs.close();	
					}
					else
					{
						// CO CO CHET THI CHI CHOT
						if(db.updateReturnInt("update ERP_MUAHANG set DACHOT = '1'  where pk_seq = '" + dmhId + "' and DACHOT=0")!=1)
							obj.setmsg("Chốt không thành công, vui lòng kiểm tra lại chứng từ");
					}
		    		
				} catch (Exception e) {
					
					obj.setmsg("Khong the cap nhat ERP_MUAHANG");
				}
	    		db.shutDown();
	    	}
	    	else
	    		if(action.equals("unchot"))
		    	{
	    			String msg =Unchot(dmhId);
	    	    	if(msg.length() > 0)
	    	    		obj.setmsg(msg);
		    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.setIsdontrahang("2");
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongTyTraHang.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		IErpCongtytrahangList obj;
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
	    	IErpCongtytrahang dmhBean = new ErpCongtytrahang();
	    	dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	
	    	dmhBean.setIsdontrahang("1");
	    	dmhBean.setUserId(userId);
	    	
	    	dmhBean.createRs();
    		
	    	session.setAttribute("ctyId", "");
	    	session.setAttribute("lhhId", "");
	    	session.setAttribute("lspId", "");
	    	
	    	session.setAttribute("dmhBean", dmhBean);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongTyTraHangNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpCongtytrahangList();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setIsdontrahang("1");
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_CongTyTraHang.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpCongtytrahangList();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	obj.setIsdontrahang("1");
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
			
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_CongTyTraHang.jsp");  
	    	}
	    }  
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpCongtytrahangList obj)
	{
		Utility util=new Utility();
		String query =  " " ;
		 query =  "  select f.MA AS TIENTE, a.PK_SEQ as dmhId, a.NGAYMUA, b.TEN, c.TEN as nccTen, c.MA as nccMa,  \n" + 
				  "  SOPO as SOCHUNGTU,  \n" + 
				  "  ISNULL (a.TONGTIENAVATNGUYENTE,0) as TONGTIENAVAT, ISNULL (a.TONGTIENAVATNGUYENTE ,0) as VAT,   \n" + 
				  "  ISNULL ( a.TONGTIENBVATNGUYENTE,0) as TONGTIENBVAT,  \n" + 
				  "  a.TRANGTHAI, a.DACHOT,  a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua  \n" + 
				  "  from erp_muahang a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ   \n" + 
				  "  inner join ERP_NHACUNGCAP c on a.NHACUNGCAP_FK = c.PK_SEQ  \n" + 
				  "  inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ   \n" + 
				  "  inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n" + 
				  "  left join ERP_TIENTE f on f.pk_seq =a.TIENTE_FK \n" + 
				  "  where a.congty_fk ="+obj.getCongtyId() +"  and a.type = '2' \n " ;
		
		
		System.out.println("cau search "+query);
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
		
		String ncc = request.getParameter("ncc");
		if(ncc == null)
			ncc = "";
		obj.setNCC(ncc);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		
		String tongtien = request.getParameter("tongtien");
		if(tongtien == null)
			tongtien = "";
		obj.setTongtiensauVat(tongtien);
		
		String sodonmuahang = request.getParameter("sodonmuahang");
		if(sodonmuahang == null)
			sodonmuahang = "";
		obj.setSodonmuahang(sodonmuahang);
		
		
		
		String loaisanpham = request.getParameter("loaisanpham");
		if(loaisanpham == null)
			loaisanpham = "";
		obj.setLoaisanphamid(loaisanpham);

		if(tungay.length() > 0)
			query += " and a.ngaymua >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaymua <= '" + denngay + "'";
		
		if(dvthId.length() > 0)
			query += " and b.pk_seq = '" + dvthId + "'";
		
		if(loaisanpham.length() > 0)
			query += " and a.loaisanpham_fk = '" + loaisanpham + "'";
		
		if(ncc.length() > 0)
			query += " and (c.ma like '%" + ncc + "%' or c.ten like N'%" + ncc + "%')";
		
		if(sodonmuahang.length() > 0)
			query += " and ( b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) LIKE  '%" + sodonmuahang + "%') ";
		
		if(tongtien.length() > 0)
			query += " and a.TONGTIENBVAT >= '" + tongtien + "'";
		
		if(trangthai.length() >0){
			query += " and a.trangthai = '" + trangthai + "'";
		}
		//query += " order by a.NGAYMUA desc, a.trangthai asc, a.pk_seq desc";
		return query;
	}

	private String Delete(String dmhId)
	{
		dbutils db = new dbutils();
		Utility_Kho util_kho=new Utility_Kho();
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "select COUNT(*) as sodong from ERP_NHANHANG where MUAHANG_FK = '" + dmhId + "'";
			System.out.println("Query mua hang: " + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			}
			
		 
			if(sodong > 0)
			{
				db.getConnection().rollback();
				db.shutDown();
				return "Đơn trả hàng này đã có nhận hàng, bạn phải xóa nhận hàng trước khi xóa đơn trả hàng này";
			}

			//truoc khi delete
			
			String loaihanghoa="";
			
			query = " select isnull(MH.LOAIHANGHOA_FK,'1') as LOAIHANGHOA_FK , MHSP.sanpham_fk, MHSP.soluong, MHSP.khott_fk " +
					" from ERP_MUAHANG_SP MHSP " +
					" INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ=MHSP.MUAHANG_FK  where muahang_fk = '" + dmhId + "'";
			 
			rs = db.get(query);
		 
				while(rs.next())
				{

					loaihanghoa=rs.getString("LOAIHANGHOA_FK").trim();

						
					
				}
				rs.close();
				
				//số lượng giữ nguyên, tăng booked, giảm avai
				if(loaihanghoa.trim().equals("0"))
				{
					query="   SELECT isnull(A.NSX_FK,0) NSX_FK,c.ngaymua,A.MARQ,A.SANPHAM_FK, A.SOLO,B.KHOTT_FK as KHO_FK,A.SOLUONG,A.MARQ,A.MATHUNG,A.MAME,A.NGAYHETHAN,A.NGAYNHAPKHO ,ISNULL(A.MAPHIEU,'') AS MAPHIEU,ISNULL(A.PHIEUDT,'') AS MAPHIEUDINHTINH, "
							 +"\nISNULL(A.PHIEUEO,'') AS PHIEUEO , ISNULL(A.HAMLUONG,100) AS HAMLUONG ,ISNULL(A.HAMAM,0) AS HAMAM ,ISNULL(A.BIN_FK,0) AS BIN_FK"
							 +"\nfrom ERP_MUAHANG_SP_CHITIET A inner join ERP_MUAHANG_SP B on A.MUAHANG_FK=B.MUAHANG_FK and A.SANPHAM_FK=B.SANPHAM_FK"
							 +"\ninner join ERP_MUAHANG C on C.PK_SEQ=B.MUAHANG_FK"
							 +"\nwhere C.PK_SEQ='"+dmhId+"' and C.TRANGTHAI=0";
					
							 rs=db.get(query);
							while(rs.next()){
								double booked = (-1)*DinhDang.dinhdangso(rs.getDouble("SOLUONG"));
								double avai =DinhDang.dinhdangso(rs.getDouble("SOLUONG"));
									
								Kho_Lib kholib=new Kho_Lib();
								kholib.setNgaychungtu(rs.getString("ngaymua"));
								kholib.setLoaichungtu("Xóa tăng kho nguoc lại ErpCongtytrahang  :"+dmhId);
								kholib.setKhottId(rs.getString("KHO_FK"));
								kholib.setBinId(rs.getString("BIN_FK"));
								kholib.setSolo(rs.getString("SOLO"));
								kholib.setSanphamId(rs.getString("SANPHAM_FK"));
								kholib.setNsxId(rs.getString("NSX_FK")==null?"":rs.getString("NSX_FK"));
								kholib.setMame(rs.getString("MAME"));
								kholib.setMathung(rs.getString("MATHUNG"));
								
								kholib.setMaphieu(rs.getString("MAPHIEU"));
								kholib.setMaphieudinhtinh(rs.getString("MAPHIEUDINHTINH"));
								kholib.setPhieuEo(rs.getString("PHIEUEO"));
								kholib.setNgayhethan(rs.getString("NGAYHETHAN"));
							 
								kholib.setNgaynhapkho(rs.getString("NGAYNHAPKHO"));
								kholib.setBooked( booked);
								
								kholib.setSoluong(0);
								kholib.setAvailable(avai);
								
								kholib.setMARQ(rs.getString("MARQ"));
								kholib.setDoituongId("");
								kholib.setLoaidoituong("");
								 
								kholib.setHamluong(rs.getString("HAMLUONG"));
								kholib.setHamam(rs.getString("HAMAM"));
								kholib.setNgaysanxuat(rs.getString("NSX_FK"));
								
								String msg1= util_kho.Update_Kho_Sp_Tra_NEW(db,kholib);
							    if( msg1.length() >0)
								{
							    	System.out.println(" cau loi kho : "+ msg1);
							    	db.getConnection().rollback();
							    	db.shutDown();
									return msg1;
								}
							 
							
						}
							
					   rs.close();
				}
			query = "update ERP_MUAHANG set trangthai = '4',dachot=0 where pk_seq = '" + dmhId + "' and trangthai=0";
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				db.shutDown();
				return "Khong the cap nhat ERP_MUAHANG: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			db.shutDown(); 
			return "Khong the xoa don mua hang"; 
		}
		
	}

	
	
	
	private String Unchot(String dmhId)
	{
		dbutils db = new dbutils();
		Utility_Kho util_kho=new Utility_Kho();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "select COUNT(*) as sodong from ERP_HOADON_DDH a INNER JOIN ERP_HOADON b on a.HOADON_FK = b.PK_SEQ where a.DDH_FK='"+dmhId+"' and  b.TRANGTHAI!=2 ";
			System.out.println("Query kiem tra mo chot: " + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			}
			
			if(sodong > 0)
			{
				db.getConnection().rollback();
				return " Bạn không thể mở chốt: đơn trả hàng này đã có hóa đơn, hoặc trạng thái không hợp lệ!";
			}
			
			// bỏ duyeet neu co
			rs = db.get("SELECT COUNT (*) AS CODUYET FROM ERP_DUYETMUAHANG WHERE MUAHANG_FK='"+dmhId +"'");
			int bien_dem=0;
			if (rs.next()) {
				bien_dem= rs.getInt("CODUYET");
			}
			rs.close();
			
			if(bien_dem>0){ // CO CO CHE DUYET
				query = "update ERP_DUYETMUAHANG set TRANGTHAI=0 where MUAHANG_FK='" + dmhId + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
				}
			}
			
			query = "update ERP_MUAHANG set DACHOT = '0' , Trangthai='0'  where pk_seq = '" + dmhId + "' and DACHOT=1";
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Khong the cap nhat ERP_MUAHANG: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			db.shutDown(); 
			return "Khong the xoa don mua hang"; 
		}
		
	}

}
