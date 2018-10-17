package geso.traphaco.erp.servlets.donmuahang;

import geso.traphaco.erp.beans.donmuahang.IErpDonmuahang_Giay;
import geso.traphaco.erp.beans.donmuahang.IErpDonmuahangList_Giay;
import geso.traphaco.erp.beans.donmuahang.imp.ErpDonmuahang_Giay;
import geso.traphaco.erp.beans.nhanhang.IErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhang.imp.ErpNhanhang_Giay;
import geso.traphaco.erp.beans.donmuahang.imp.ErpDonmuahangList_Giay;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDonmuahangList_GiaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDonmuahangList_GiaySvl() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDonmuahangList_Giay obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    // LOAI : 0 Mua hang nhap khau, 1 mua trong nuoc, 2 mua CP/TS/CCDC
	    String loai = util.antiSQLInspection(request.getParameter("loai"));
	    
	    
	    String action = util.getAction(querystring);
	    
	    String dmhId = util.getId(querystring);
	    String loaidh = request.getParameter("loaidh");
	    
	    String msg = "";
	    if (action.equals("delete"))
	    {	
	    	msg = Delete(dmhId,userId);
	    	loai = loaidh;
	        obj = new ErpDonmuahangList_Giay();
		    obj.setUserId(userId);
		    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    obj.setLoai(loai);
		    obj.setIsdontrahang("0");
		    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);

		    if(msg.length() > 0) obj.setmsg(msg);
		    obj.init("");
		    
			session.setAttribute("obj", obj);
					
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangList.jsp";
			response.sendRedirect(nextJSP);
	    	
	    }
	    else if(action.equals("hoantat"))
	    	{
	    		dbutils db = new dbutils();
	    		db.update("update ERP_MUAHANG set trangthai = '2' where pk_seq = '" + dmhId + "'");
	    		db.shutDown();
	    		loai = loaidh;
	    	    obj = new ErpDonmuahangList_Giay();
	    	    obj.setUserId(userId);
	    	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    	    obj.setLoai(loai);
	    	    obj.setIsdontrahang("0");
	    	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);

	    	    if(msg.length() > 0) obj.setmsg(msg);
	    	    obj.init("");
	    	    
	    		session.setAttribute("obj", obj);
	    				
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangList.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    	else if(action.equals("chot"))
		    	{
		    		this.ChotMuahang(dmhId,userId);
		    		loai = loaidh;
		    	    obj = new ErpDonmuahangList_Giay();
		    	    obj.setUserId(userId);
		    	    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	    obj.setLoai(loai);
		    	    obj.setIsdontrahang("0");
		    	  
		    	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	    obj.init("");
		    	    if(msg.length() > 0) obj.setmsg(msg);
		    		session.setAttribute("obj", obj);
		    				
		    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangList.jsp";
		    		response.sendRedirect(nextJSP);
		    	}
	    		
	    	else if(action.equals("unchot"))
		    	{
		    		dbutils db = new dbutils();
		    		loai = loaidh;
		    		//1. DACHOT = 0
	    			//2. TRANGTHAI = 0 , ERP_DUYETMUAHANG set TRANGTHAI = 0
		    		String msg1= MoChot(dmhId);
		    	    obj = new ErpDonmuahangList_Giay();
		    	    obj.setUserId(userId);
		    	    obj.setmsg(msg1);
		    	    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	    obj.setLoai(loai);
		    	    obj.setIsdontrahang("0");
		    	    
		    	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	    obj.init("");
		    	    if(msg.length() > 0) obj.setmsg(msg);
		    		session.setAttribute("obj", obj);
		    				
		    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangList.jsp";
		    		response.sendRedirect(nextJSP);
		    		db.shutDown();
		    		
		    	}
	    		
    	
	else {

	    obj = new ErpDonmuahangList_Giay();
	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setLoai(loai);
	    obj.setIsdontrahang("0");
	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);

	    if(msg.length() > 0) obj.setmsg(msg);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangList.jsp";
		response.sendRedirect(nextJSP);
	}
}

	private void ChotMuahang(String dmhId, String userId) {
		// TODO Auto-generated method stub
		try{
			dbutils db = new dbutils();
    		
    		String query="SELECT ISNULL(ISGIACONG,'0') AS ISGIACONG  FROM ERP_MUAHANG WHERE PK_SEQ= "+dmhId;
    		ResultSet rs=db.get(query);
    		if(rs.next()){
    			if(rs.getString("ISGIACONG").equals("1")){
    				
    				if(db.update(" update ERP_MUAHANG set DACHOT = '1',TRANGTHAI='1' where pk_seq = '" + dmhId + "' and  trangthai=0 and DACHOT=0"))
    				{    				
    					db.update(" update ERP_DUYETMUAHANG set TRANGTHAI='1' where MUAHANG_FK = '" + dmhId + "'");	    				
    				
    				}
    		}
    		}
    		rs.close();
    		
    		
    		db.update("update ERP_MUAHANG set DACHOT = '1' where pk_seq = '" + dmhId + "' and DACHOT=0 and trangthai=0");
    		
    		db.shutDown();
		}catch(Exception er){
			
		}
	}
	
	private String MoChot(String dmhId) {
		// TODO Auto-generated method stub
		try{
			dbutils db = new dbutils();
			String query="";
			ResultSet rs;
			// kiem tra nếu đã ra hóa đơn thì hk cho mở chốt
			
			 query="SELECT COUNT (*) as sl FROM ERP_PARK PARK INNER JOIN ERP_HOADONNCC HD ON HD.park_fk=PARK.pk_seq \n" + 
						  " INNER JOIN ( \n" + 
						  " SELECT distinct HOADONNCC_FK,MUAHANG_FK FROM ERP_HOADONNCC_DONMUAHANG WHERE MUAHANG_FK='"+dmhId +"' \n" + 
						  " ) MH ON MH.HOADONNCC_FK=HD.pk_seq \n" + 
						  " WHERE PARK.trangthai <> 4 ";
    		System.out.println(" chek da ra hoa don: "+ query);
			 rs=db.get(query);
			int check=0;
			if(rs.next()){
				check= rs.getInt("sl");	
			}
			rs.close();
			if(check>0){
				return "Mua hàng đã ra hóa đơn, vui lòng hủy hóa đơn để mở chốt đơn hàng ";	
			}
			
			//-----
			
    		
    		 query="SELECT PK_SEQ FROM ERP_CHUYENKHO CK  WHERE trangthai<>4 and MUAHANG_FK="+dmhId;
    		 rs=db.get(query);
    		
    	 
				if(rs.next()){
					return "Mua hàng đã yêu cầu chuyển kho,vui lòng hủy chuyển kho để mở chốt đơn hàng ";
				}
				rs.close();
    		
    		
    		//1. DACHOT = 0
			//2. TRANGTHAI = 0 , ERP_DUYETMUAHANG set TRANGTHAI = 0
				query="update ERP_MUAHANG set trangthai = '0', DACHOT = '0' where pk_seq = '" + dmhId + "'";
					
	    	if(!	db.update(query)){
	    		return "Không thể cập nhật: "+query;
	    	}
    		query= "update ERP_DUYETMUAHANG set trangthai = '0' where muahang_fk = '" + dmhId + "'";
    	 	if(!	db.update(query)){
	    		return "Không thể cập nhật: "+query;
	    	}
    	 	
    	 	
    	 	
    		/*// xóa cập nhật BOM gia công
    	 	query="delete ERP_MUAHANG_BOM   where muahang_fk = '" + dmhId + "'" ;
    		if(!	db.update(query)){
	    		return "Không thể cập nhật: "+query;
	    	}
    	 	
    		query="update  ERP_MUAHANG_sp set dmvt_fk =NULL  where muahang_fk = '" + dmhId + "'";
    		if(!	db.update(query)){
	    		return "Không thể cập nhật: "+query;
	    	}*/
    	 	
    		return "";
    		
		}catch(Exception er){
			return "Lỗi trong quá trình mở duyệt : "+er.getMessage();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDonmuahangList_Giay obj;
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
	    String loai = util.antiSQLInspection(request.getParameter("loai")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpDonmuahang_Giay dmhBean = new ErpDonmuahang_Giay();
	    	dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	dmhBean.setUserId(userId);
	    	dmhBean.setLoai(loai);
	    	
	    	dmhBean.createRs();
    		
	    	session.setAttribute("ctyId", (String)session.getAttribute("congtyId"));
	    	session.setAttribute("lhhId", "");
	    	session.setAttribute("lspId", "");
	    	session.setAttribute("noibo", "");
	    	
	    	session.setAttribute("dmhBean", dmhBean);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDonMuaHangNew_Giay.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpDonmuahangList_Giay();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setLoai(loai);
	    		
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

	    		this.getSearchQuery(request, obj);
		    	
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDonMuaHangList.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpDonmuahangList_Giay();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	obj.setLoai(loai);
		    	
		    	this.getSearchQuery(request, obj);
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDonMuaHangList.jsp");  
	    	}
	    }
	    
	}

	private void getSearchQuery(HttpServletRequest request, IErpDonmuahangList_Giay obj)
	{
		Utility util = new Utility();
		
		geso.traphaco.center.db.sql.dbutils db = new geso.traphaco.center.db.sql.dbutils();
		
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
		
		String sodonmuahang = request.getParameter("sodonmuahang");
		if(sodonmuahang == null)
			sodonmuahang = "";
		obj.setSodonmuahang(sodonmuahang);
		
		String loaihh = request.getParameter("loaihh");
		if(loaihh == null)
			loaihh = "";
		obj.setLoaihanghoa(loaihh);
		
		String mactsp = request.getParameter("mactsp");
		if(mactsp == null)
			mactsp = "";
		obj.setMaCtSp(mactsp);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitaoIds(nguoitao);

		
		String sothamchieu = request.getParameter("sothamchieu");
		if(sothamchieu == null)
			sothamchieu = "";
		obj.setsothamchieu(sothamchieu);
		
		String loaiDonMuaHang = request.getParameter("loaiDonMuaHang");
		if(loaiDonMuaHang == null)
				loaiDonMuaHang = "";
		obj.setLoaiDonMuaHang(loaiDonMuaHang);
		
		String loai = util.antiSQLInspection(request.getParameter("loai")); 
		
		String sql = "";
		
		if(loai.equals("1"))
			sql = ", (select count(hd.PK_SEQ) from ERP_HOADONNCC hd inner join ERP_HOADONNCC_DONMUAHANG mh_hd on hd.pk_seq = mh_hd.hoadonncc_fk "
				+ "	  where hd.trangthai not in (4) and  mh_hd.MUAHANG_FK = a.PK_SEQ and a.isduocphanbo = 1 ) daxuathd ";
		
//		String query =  " select a.PK_SEQ as dmhId,ISNULL(( select SUM(soluong) from ERP_MUAHANG_SP where MUAHANG_FK = a.PK_SEQ ), 0) AS tongluong, a.NGAYMUA , \n " +
//						" isnull((select TEN from ERP_DONVITHUCHIEN where PK_SEQ = a.DONVITHUCHIEN_FK), '') as ten, isnull(c.TEN, '') as nccTen, c.MA as nccMa,  \n " +
//						" SOPO as SOCHUNGTU,  \n " +
//						" a.TONGTIENAVAT/(	select top(1) tg.TIGIAQUYDOI from ERP_TIGIA  tg " +
//						"	where TIENTE_FK = a.TIENTE_FK and tg.TRANGTHAI ='1' order by PK_SEQ desc ) as TONGTIENAVAT" +
//						" , a.VAT/(select top(1) tg.TIGIAQUYDOI from ERP_TIGIA  tg " +
//						"	where TIENTE_FK = a.TIENTE_FK  and tg.TRANGTHAI ='1' order by PK_SEQ desc ) as VAT,  \n " +
//						" a.TONGTIENBVAT/(select top(1) tg.TIGIAQUYDOI from ERP_TIGIA  tg " +
//						"	where TIENTE_FK = a.TIENTE_FK  and tg.TRANGTHAI ='1' order by PK_SEQ desc ) as TONGTIENBVAT,  \n " +
//						" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, isnull(a.DaInPdf, 0) as DaInPdf,   \n " +
//						" ISNULL(DUYET.DUYET,0) AS DUYET, ISNULL(tt.ma, 'NA') as tiente, isnull(a.NOTE, '') as NOTE, isnull(a.DACHOT, 0) as DACHOT,  \n " +
//						" isnull(c.noibo, 0) as noibo,  \n " +
//						" (select count(*) from ERP_HOADONNCC_PHIEUNHAP where MUAHANG_FK = a.pk_seq )as ktPO, a.SOTHAMCHIEU, a.LOAI, a.ISCOHOADON,   \n " +
//						" isnull((select (cast(PK_SEQ as nvarchar(50)) +' ' + (case trangthai when 0 then N'Chưa chốt' when 1 then N'Đã chốt' end) ) + ','  from ERP_TAMUNG  WHERE muahang_fk = a.PK_SEQ and trangthai not in (2) for xml path('')),'') DENGHITAMUNG, \n"+
//						" isnull(a.isduocphanbo,0) isduocphanbo, a.NHACUNGCAP_FK NCCID, a.tooltip,  \n"+
//						" isnull((select sopo from erp_muahang mh inner join erp_phanbomuahang pb on mh.pk_seq = pb.MUAHANG_FK \n"+
//						" inner join ERP_PHANBOMUAHANG_PO pbpo on pb.PK_SEQ = pbpo.PHANBO_FK \n"+
//						" where pbpo.PODUOCPB = a.PK_SEQ), '') as POgoc";
//				if(loai.equals("1"))
//					query +=  sql;
//						
//				query+=	" from erp_muahang a  \n " +
//						" left join ERP_NHACUNGCAP c on a.NHACUNGCAP_FK = c.PK_SEQ \n " +
//						" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n " +
//						" left join ERP_TIENTE tt on a.tiente_fk = tt.pk_seq  \n " +
//		
//						" left join(  \n " +
//						"	SELECT 	MUAHANG_FK AS DMHID,  \n " +
//						"			CASE WHEN SUM(QUYETDINH) > 0 THEN  \n " +
//						"			(CASE WHEN  \n " +
//						"			( SELECT SUM(TRANGTHAI)  \n " +
//						"			FROM ERP_DUYETMUAHANG   \n " +				
//						"			WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1) > 0 THEN 0  \n " +
//						"			ELSE 1  \n " +
//						"			END)	 \n " +
//						"			ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	END AS DUYET  \n " + 
//						"	FROM ERP_DUYETMUAHANG DUYETMUAHANG  \n " +
//						"	GROUP BY MUAHANG_FK  \n " +
//						"  )DUYET ON DUYET.DMHID = A.PK_SEQ  \n " +
//						" where  a.type = '0' and a.congty_fk = '" + obj.getCongtyId() + "' and a.LOAI = "+ obj.getLoai() + " " ;
//		
//				       /* " and a.DONVITHUCHIEN_FK in  " + util.quyen_donvithuchien(obj.getUserId()) + " " +
//				        " and a.nhacungcap_fk in " + util.quyen_nhacungcap(obj.getUserId());
//		
//		
//		String query1 = " select isnull(is_viewPO, 0) as is_viewPO from nhanvien where pk_seq="+obj.getUserId();
//		
//		ResultSet rs1 = db.get(query1);
//		if(rs1!=null){
//			try{
//				while(rs1.next()){
//					if(!rs1.getString("is_viewPO").equals("1")){
//						query  +=  " and (a.nguoitao in "+util.quyen_xemdon_mua_ban(obj.getUserId())+" or a.nguoitao="+obj.getUserId()+")  " ;
//					}
//				}rs1.close();
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}*/
		
//		if(sothamchieu.trim().length() > 0)
//			query += " and a.SOTHAMCHIEU like '%" + sothamchieu + "%'";
//		
//		if(tungay.length() > 0)
//			query += " and a.ngaymua >= '" + tungay + "'";
//		
//		if(denngay.length() > 0)
//			query += " and a.ngaymua <= '" + denngay + "'";
//		
//		if(dvthId.length() > 0)
//			query += " and a.DONVITHUCHIEN_FK = '" + dvthId + "'";
//		
//		if(loaihh.length() > 0)
//			query += " and a.LOAIHANGHOA_FK = '" + loaihh + "'";
//		
//		if(ncc.length() > 0)
//			query += " and (dbo.ftBoDau(c.ma) like '%" + util.replaceAEIOU(ncc) + "%' or dbo.ftBoDau(c.ten) like N'%" + util.replaceAEIOU(ncc) + "%') ";
//		
//		if(nguoitao.trim().length() > 0)
//			query += " and a.nguoitao = '" + nguoitao.trim() + "' ";
//		
//		if(loaihh.trim().length()> 0){
//			query += " and A.LOAIHANGHOA_FK = '" + loaihh + "'";
//		}
//			
//		if(sodonmuahang.length() > 0)
//		{
//			query += " and a.soPO like '%" + sodonmuahang + "%' ";
//		}
//		
//		if(trangthai.trim().length() > 0)
//		{
//			if(trangthai.equals("0")){//chưa duyệt
//				query += " and a.trangthai = 0 ";
//			}	
//			else if(trangthai.equals("-1")){//đã duyệt
//				query += " and a.trangthai = 1 ";
//			
//			}
//			else if(trangthai.equals("-2")){//Đã có hóa đơn
//				query += " and (select count(*) from ERP_HOADONNCC_PHIEUNHAP where MUAHANG_FK = a.pk_seq ) >0 ";
//			}
//			else if(trangthai.equals("5")){//Chưa chốt
//				query += " and a.trangthai = 0 and isnull(a.DACHOT, 0) = 0 ";
//			}
//			else if(trangthai.equals("2"))//hoàn tất
//			{
//				query += " and a.trangthai = 2 ";
//			}
//			else if(trangthai.equals("3"))//đã xóa
//			{
//				query += " and a.trangthai = 3 ";
//			}
//			else{// đã hủy
//				query += "and a.trangthai = '"+ trangthai +"' and (select count(*) from ERP_HOADONNCC_PHIEUNHAP where MUAHANG_FK = a.pk_seq ) <=0";
//			}
//		}
//		
//		if( loaiDonMuaHang != "")
//		{
//			query += "and ISHOPDONG = '" + loaiDonMuaHang + "'";
//		}
//
//		
//		if(mactsp.trim().length() > 0)
//		{
//			query +=" and a.pk_seq in (" +
//					"     select distinct muahang_fk from erp_muahang_sp where sanpham_fk in ( select distinct pk_seq from erp_Sanpham where MA like N'%" + mactsp + "%' ) " +
//					" ) ";
//		}
//		
//		//query += " order by a.NGAYMUA desc, a.trangthai asc, a.pk_seq desc";
//		//query+=" group by 	a.PK_SEQ , a.NGAYMUA,b.TEN,c.TEN, c.MA,a.NGAYMUA,a.TONGTIENAVAT,a.VAT,a.TONGTIENBVAT,a.TRANGTHAI,a.NGAYSUA,a.NGAYTAO,d.TEN,e.TEN,DUYET.DUYET,tt.ma,a.NOTE,a.DaInPdf ,b.PREFIX";
//		System.out.println();
//		System.out.println("Vuna_searchQuery: " + query);
//		System.out.println();		
//
//		return query;
	}

	private String Delete(String dmhId,String userId)
	{
		dbutils db = new dbutils();
		String query="";
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			query = "select COUNT(*) as sodong from ERP_NHANHANG where MUAHANG_FK = '" + dmhId + "'";
			//System.out.println("1.Query check mua hang: " + query);
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
			
			System.out.println("So dong la: " + sodong + "\n");
			
			if(sodong > 0)
			{
				return "Đơn mua hàng này đã có nhận hàng, bạn phải xóa nhận hàng trước khi xóa đơn mua hàng này";
			}
			
			
			query = "select count(*) as sodong  " +
				   "from ERP_HOADONNCC a inner join ERP_HOADONNCC_PHIEUNHAP b on a.pk_seq = b.hoadonncc_fk " +
				   "where b.muahang_fk = '" + dmhId + "'";
	
			//System.out.println("Query mua hang: " + query);
			sodong = 0;
			rs = db.get(query);
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
				return "Mua hàng này đã xuất hóa đơn, bạn không thể xóa";
			}
					
	
			query = "SELECT TRANGTHAI FROM ERP_MUAHANG WHERE PK_SEQ =  '" + dmhId + "'";
			rs = db.get(query);
			String tt = "";
			if(rs != null)
			{
				if(rs.next())
				{
					tt = rs.getString("TRANGTHAI");
					rs.close();
				}
			}
			
			if(tt.equals("0")){
				
				query = " DELETE ERP_DUYETMUAHANG WHERE MUAHANG_FK = '" + dmhId + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Không thể xóa đơn mua hàng này: " + query;
				}
				
				query = " DELETE ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + dmhId + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Không thể xóa đơn mua hàng này: " + query;
				}
				
				
				query = " DELETE ERP_MUAHANG WHERE PK_SEQ = '" + dmhId + "' and TRANGTHAI=0";
				if(db.updateReturnInt(query)!=1)
				{
					db.getConnection().rollback();
					return "Không thể xóa đơn mua hàng này: " + query;
				}

			}else{
				query = " Update  ERP_MUAHANG set trangthai=3, nguoisua="+userId+"  where pk_seq = '" + dmhId + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Không thể xóa đơn mua hàng này: " + query;
				}

			}
			
			
			
			// Cap nhat DADATHANG trong ERP_MUANGUYENLIEUDUKIEN
			query = "UPDATE ERP_MUANGUYENLIEUDUKIEN	" +  
					"SET ERP_MUANGUYENLIEUDUKIEN.DADATHANG = ISNULL(A.SOLUONG, 0) " + 
					"FROM " +
					"( " +
					"	SELECT SUM(MH_SP.SOLUONG) AS SOLUONG, MH_SP.SANPHAM_FK, SUBSTRING(MH_SP.NGAYNHAN, 1, 4) AS NAM,	" + 
					"	CONVERT(INT, SUBSTRING(MH_SP.NGAYNHAN, 6,2)) AS THANG	" +
					"	FROM ERP_MUAHANG_SP MH_SP " +
					"   INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK " +
					"	WHERE SANPHAM_FK IS NOT NULL AND MH.TRANGTHAI <> 3 " +
					"	GROUP BY MH_SP.SANPHAM_FK, SUBSTRING(MH_SP.NGAYNHAN, 1, 4),CONVERT(INT, SUBSTRING(MH_SP.NGAYNHAN, 6,2))	" +
					")A  " +
					"WHERE ERP_MUANGUYENLIEUDUKIEN.NAM = A.NAM	" + 
					"AND ERP_MUANGUYENLIEUDUKIEN.THANG = A.THANG	" +
					"AND ERP_MUANGUYENLIEUDUKIEN.SANPHAM_FK = A.SANPHAM_FK ";
			
					
			System.out.println("Cap nhat mua NL: " + query);
			
			db.update(query);
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			db.shutDown(); 
			return "Loi-khong the xoa don mua hang:"+query; 
		}
		
	}
}
