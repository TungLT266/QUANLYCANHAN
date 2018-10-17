package geso.traphaco.erp.servlets.kiemdinhchatluong.kho;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kiemdinhchatluong.kho.IErpKiemDinhChatLuongKho;
import geso.traphaco.erp.beans.kiemdinhchatluong.kho.IErpKiemDinhChatLuongKhoList;
import geso.traphaco.erp.beans.kiemdinhchatluong.kho.imp.ErpKiemDinhChatLuongKho;
import geso.traphaco.erp.beans.kiemdinhchatluong.kho.imp.ErpKiemDinhChatLuongKhoList;

public class ErpKiemDinhChatLuongKhoSvl extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpKiemDinhChatLuongKhoSvl() {
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
	    }
	    // lay loai mua hang de xem la mua hang nhap khau hay trong nuoc
		String loaimh = request.getParameter("loaiMH");
		IErpKiemDinhChatLuongKhoList obj = new ErpKiemDinhChatLuongKhoList();
 
	    obj.setUserId(userId);
	    // loai
	   
	    obj.setloaimuahang("2");
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongKho.jsp";
		response.sendRedirect(nextJSP);
	}

	private String Hoantatkiemdinh(String id, String userId) {
		// TODO Auto-generated method stub
		try{
			
			dbutils db=new dbutils();
			
			String  query=" update ERP_YeuCauKiemDinh set trangthai='2' where pk_seq="+id;
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
	    IErpKiemDinhChatLuongKhoList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
	    	IErpKiemDinhChatLuongKho kdcl = new ErpKiemDinhChatLuongKho();
    		kdcl.setUserId(userId);
    		kdcl.createRs();
    		
	    	session.setAttribute("kdcl", kdcl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongKhoUpdate.jsp");
	    }
	    
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
    	
    		obj = new ErpKiemDinhChatLuongKhoList();
    		
	    	String search = getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongKho.jsp");	
	    }
	    else
	    {
	    	obj = new ErpKiemDinhChatLuongKhoList();
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	System.out.println("---- 13. init search query: " + search);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongKho.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpKiemDinhChatLuongKhoList obj) 
	{
		Utility util = new Utility();
		
		String solo = request.getParameter("solo");
		if(solo == null)
			solo = "";
		obj.setMa(solo);
		
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
		
	String sql =   " select ISNULL(THIEUHOSO,'0')  AS THIEUHOSO, a.pk_seq, b.PREFIX + CAST(a.nhanhang_fk as varchar(10)) as sonhapkho, c.ten as spTen, " +
		" 		 a.soluong, isnull( (select sum(SOLUONGDUYET-SOLUONGHONG-SOLUONGMAU)  as soluongdat from ERP_KIEMDINHCHATLUONG_LANDUYET kd_ld " +
		"       where kd_ld.YEUCAUKIEMDINH_FK=A.pk_seq) , 0) as soluongDat, a.solo, a.ngaytao, isnull(a.ngaysua, '') as ngaysua, " +
		"     b.ngaynhan as ngaynhan, a.trangthai, d.TEN as nguoitao, isnull(e.TEN, '') as nguoisua " +
		" ,isnull(b.trahang_fk,0) as trahang_fk,isnull(b.muahang_fk,0) as muahang_fk, a.KHONHAN_FK  " +
	    " from 	 ERP_YeuCauKiemDinh a inner join ERP_NHANHANG b on a.nhanhang_fk = b.PK_SEQ  " +
	  	" 		 inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ left join NHANVIEN d on a.nguoitao = d.PK_SEQ " +
	  	" 		 left join NHANVIEN e on a.nguoisua = e.PK_SEQ " +
		" where  a.nhanhang_fk is not null ";
		
		
		if(tungayKD.length() > 0)
			sql += " and isnull(a.ngaysua, '') >= '" + tungayKD + "'";
		
		if(ngaynhan.length() > 0)
			sql += " and isnull(b.ngaynhan, '') = '" + ngaynhan + "'";
		
		if(denngayKD.length() > 0)
			sql += " and isnull(a.ngaysua, '') <= '" + denngayKD + "'";
		
		if(tungayNH.length() > 0)
			sql += " and isnull(b.ngaynhan, '') >= '" + tungayNH + "'";
		
		if(denngayNH.length() > 0)
			sql += " and isnull(b.ngaynhan, '') <= '" + denngayNH + "'";
		
		if(solo.length() > 0)
			sql += " and a.solo like N'%" + solo + "%' ";
		
		if(sanpham.length() > 0)
			sql += " and ( dbo.ftBoDau(c.ten) like N'%" + util.replaceAEIOU(sanpham) + "%' or dbo.ftBoDau(c.ma) like N'%" + util.replaceAEIOU(sanpham) + "%' ) ";
		
		if(sonhanhang.length() > 0)
			sql += " and ( b.PREFIX + CAST(a.nhanhang_fk as varchar(10)) like N'%" + sonhanhang + "%' ) ";
		
		if(sochungtu.length() > 0)
			sql += " and ( '200' + CAST(a.pk_seq as varchar(10)) like N'%" + sochungtu + "%' ) ";
		
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		return sql;
	}
	
	



}
