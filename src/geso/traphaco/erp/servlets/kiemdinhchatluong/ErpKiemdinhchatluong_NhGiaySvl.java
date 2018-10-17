package geso.traphaco.erp.servlets.kiemdinhchatluong;

import geso.traphaco.erp.beans.kiemdinhchatluong.*;
import geso.traphaco.erp.beans.kiemdinhchatluong.imp.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKiemdinhchatluong_NhGiaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpKiemdinhchatluong_NhGiaySvl() {
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
	    String lsxId = util.getId(querystring);
	    String msg="";	    
	    if(action.equals("hoantat")){
	    	 msg = this.Hoantatkiemdinh(lsxId,userId);
	    	 String loaimh = request.getParameter("loaiMH");
	 	    IErpKiemdinhchatluongList_NhGiay obj = new ErpKiemdinhchatluongList_NhGiay();
	  
	 	    obj.setUserId(userId);
	 	    // set loai mua hang
	 	    // kiem tra la mua hang trong nuoc hay nhap khau
	 	    if(loaimh.equals("0")) // mua hang nhapkhau
	 	    {
	 	    	obj.setloaimuahang("0");
	 	    }else if(loaimh.equals("1"))  // mua hang trong nuoc
	 	    {
	 	    	obj.setloaimuahang("1");
	 	    }else{
	 	    	obj.setloaimuahang(loaimh);
	 	    }
	 	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	 	    obj.init("");
	 	    
	 	    obj.setMsg(msg);
	 		session.setAttribute("obj", obj);
	 	    
	 	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiay.jsp";
	 		response.sendRedirect(nextJSP);
	    	 
	    }else
	    {
	    // lay loai mua hang de xem la mua hang nhap khau hay trong nuoc
		String loaimh = request.getParameter("loaiMH");
	    IErpKiemdinhchatluongList_NhGiay obj = new ErpKiemdinhchatluongList_NhGiay();
 
	    obj.setUserId(userId);
	    // set loai mua hang
	    // kiem tra la mua hang trong nuoc hay nhap khau
	    if(loaimh.equals("0")) // mua hang nhapkhau
	    {
	    	obj.setloaimuahang("0");
	    }else if(loaimh.equals("1"))  // mua hang trong nuoc
	    {
	    	obj.setloaimuahang("1");
	    }else{
	    	obj.setloaimuahang(loaimh);
	    }
	    obj.init("");
	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiay.jsp";
		response.sendRedirect(nextJSP);
	    }
	}

	private String Hoantatkiemdinh(String id, String userId) 
	{
		// TODO Auto-generated method stub
		try
		{
			
			dbutils db=new dbutils();
			
			String  query=" update ERP_YeuCauKiemDinh set trangthai='2' where pk_seq="+id;
			if(!db.update(query)){
				return "Không thể hoàn tất vui lòng thử lại";
			}
			
			//Lưu lại ngày nhập kho trong bc NXT với hàng mẫu
			query = "update a " +
					"	set a.ngaynhapkho = a.ngaykiem " +
					"from ERP_YEUCAUKIEMDINH a inner join ERP_YEUCAUKIEMDINH_THUNG b on a.pk_seq = b.YEUCAUKIEMDINH_FK " +
					"where a.pk_seq = '" + id + "' and isnull(b.soluongMAU, 0) > 0 ";
			if(!db.update(query)){
				return "Không thể hoàn tất vui lòng thử lại";
			}
			
			db.shutDown();
		}catch(Exception er){
			er.printStackTrace();
			return er.getMessage();
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
	    IErpKiemdinhchatluongList_NhGiay obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
	    	ErpKiemdinhchatluong_NhGiay kdcl = new ErpKiemdinhchatluong_NhGiay();
    		kdcl.setUserId(userId);
    		kdcl.setLoai("2");
    		kdcl.createRs();
    		
	    	session.setAttribute("kdcl", kdcl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiayNew.jsp");
	    }
	    
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
    	
    		obj = new ErpKiemdinhchatluongList_NhGiay();
    		
    		/*obj.setCongtyId((String)session.getAttribute("congtyId"));*/
    		
	    	this.getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);
	    	obj.init("");
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiay.jsp");	
	    }
	    else
	    {
	    	obj = new ErpKiemdinhchatluongList_NhGiay();
		    obj.setUserId(userId);

	    	this.getSearchQuery(request, obj);
//	    	System.out.println("---- 13. init search query: " + search);
	    	
	    	obj.setUserId(userId);
	    	obj.init("");
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiay.jsp");	
	    }
	}
	
	private void getSearchQuery(HttpServletRequest request, IErpKiemdinhchatluongList_NhGiay obj) 
	{
		Utility util = new Utility();
		
		String solo = request.getParameter("solo");
		if(solo == null)
			solo = "";
		obj.setMa(solo);
		
		
		String loaimuahang = request.getParameter("loaimuahang");
		if(loaimuahang == null)
			loaimuahang = "";
		obj.setloaimuahang(loaimuahang);
		
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		
		String ngaynhan = request.getParameter("ngaynhan");
		if(ngaynhan == null)
			ngaynhan = "";
		obj.setNgayNhan(ngaynhan);
		
		String tungayKD = request.getParameter("tungayKD");
		if(tungayKD == null)
			tungayKD = "";
		obj.setTungay(tungayKD);
		
		String denngayKD = request.getParameter("denngayKD");
		if(denngayKD == null)
			denngayKD = "";
		obj.setDenngay(denngayKD);
		
		String tungayNH = request.getParameter("tungayNH");
		if(tungayNH == null)
			tungayNH = "";
		obj.setTungayNH(tungayNH);
		
		String denngayNH = request.getParameter("denngayNH");
		if(denngayNH == null)
			denngayNH = "";
		obj.setDenngayNH(denngayNH);
		
		String sanpham = request.getParameter("sanpham");
		if(sanpham == null)
			sanpham = "";
		obj.setSanpham(sanpham);
		
		String sonhanhang = request.getParameter("sonhanhang");
		if(sonhanhang == null)
			sonhanhang = "";
		obj.setSonhanhang(sonhanhang);
		
		String sochungtu = request.getParameter("sochungtu");
		if(sochungtu == null)
			sochungtu = "";
		obj.setsochungtu(sochungtu);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String donMuaHang = request.getParameter("donmuahang");
//		
//	String sql =   "\n select isnull( a.loaimuahang,'') as loaimuahang, " +
//			"  isnull(hd.sohoadon,'')  as spSohoadon, ISNULL(THIEUHOSO,'0')  AS THIEUHOSO, a.pk_seq, b.PREFIX + CAST(a.nhanhang_fk as varchar(10)) as sonhapkho, c.ten as spTen, " +
//		"\n  		 a.soluong, isnull( (select sum(SOLUONGDUYET-SOLUONGHONG-SOLUONGMAU)  as soluongdat from ERP_KIEMDINHCHATLUONG_LANDUYET kd_ld " +
//		"\n        where kd_ld.YEUCAUKIEMDINH_FK=A.pk_seq) , 0) as soluongDat, a.solo, a.ngaytao, isnull(a.ngaysua, '') as ngaysua, " +
//		"\n      b.ngaynhan as ngaynhan, a.trangthai, d.TEN as nguoitao, isnull(e.TEN, '') as nguoisua " +
//		"\n  ,isnull(b.trahang_fk,0) as trahang_fk,isnull(b.muahang_fk,0) as muahang_fk, a.KHONHAN_FK  " +
//	    "\n  from 	 ERP_YeuCauKiemDinh a inner join ERP_NHANHANG b on a.nhanhang_fk = b.PK_SEQ  " +
//	  	"\n  		 inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ left join NHANVIEN d on a.nguoitao = d.PK_SEQ " +
//	  	"\n  		 left join NHANVIEN e on a.nguoisua = e.PK_SEQ " +
//	  	"\n  		 left join ERP_HOADONNCC hd on hd.pk_seq= b.hdncc_fk " +
//		"\n  where  a.nhanhang_fk is not null ";
		
	
//		if(loaimuahang.length() > 0)
//		sql += "\n and a.loaimuahang='"+loaimuahang +"'";
//		
//		if(tungayKD.length() > 0)
//			sql += "\n and isnull(a.ngaysua, '') >= '" + tungayKD + "'";
//		
//		if(ngaynhan.length() > 0)
//			sql += "\n and isnull(b.ngaynhan, '') = '" + ngaynhan + "'";
//		
//		if(denngayKD.length() > 0)
//			sql += "\n and isnull(a.ngaysua, '') <= '" + denngayKD + "'";
//		
//		if(tungayNH.length() > 0)
//			sql += "\n and isnull(b.ngaynhan, '') >= '" + tungayNH + "'";
//		
//		if(denngayNH.length() > 0)
//			sql += "\n and isnull(b.ngaynhan, '') <= '" + denngayNH + "'";
//		
//		if(solo.length() > 0)
//			sql += "\n and a.solo like N'%" + solo + "%' ";
//		
//		
//		if(sonhanhang.length() > 0)
//			sql += "\n and ( b.PREFIX + CAST(a.nhanhang_fk as varchar(10)) like N'%" + sonhanhang + "%' ) ";
//		
//		if(sochungtu.length() > 0)
//			sql += "\n and ( '200' + CAST(a.pk_seq as varchar(10)) like N'%" + sochungtu + "%' ) ";
//		
//		if(trangthai.length() > 0)
//			sql += "\n and a.trangthai = '" + trangthai + "' ";
//		
//		if(sohoadon.length() > 0)
//			sql += "\n and hd.sohoadon = '" + sohoadon + "' ";
//		
//	
//		
//		if(sanpham.length() > 0)
//		{
//			//sql += " and ( dbo.ftBoDau(c.ten) like N'%" + util.replaceAEIOU(sanpham) + "%' or dbo.ftBoDau(c.ma) like N'%" + util.replaceAEIOU(sanpham) + "%' ) ";
//			sql +=" \n  and c.pk_seq ="+  sanpham   ;
//			
//			
//		}
//		
//		System.out.println("\n seach kiem dinh chat luong: "+ sql + "\n");
//		return sql;
	}
	
	

}
