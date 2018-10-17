package geso.traphaco.erp.servlets.banggiaban;

import geso.traphaco.erp.beans.banggiaban.*;
import geso.traphaco.erp.beans.banggiaban.imp.*;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpBanggiabanGiaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpBanggiabanGiaySvl() {
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
	    	msg = XoaBangGia(khlId);
	    }
	    
	    IErpBanggiabanGiayList obj = new ErpBanggiabanGiayList();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangGiaBanGiay.jsp";
		response.sendRedirect(nextJSP);
	}

	private String XoaBangGia(String bgId) 
	{
	  /*	String msg = "";
		dbutils db = new dbutils();
    	
    	String query = 	"SELECT PK_SEQ FROM ERP_SUAGIABAN WHERE BGBAN_FK = '" + bgId + "' AND TINHTRANG = '2' ";
    	System.out.println(query);
    	boolean cothexoa = true;
    	
    	ResultSet rs = db.get(query);
		try {
			if(rs.next()) {
				cothexoa = false;
				msg = "Bảng giá này đã được sử dụng, không thể xóa!";
			}
			rs.close();
		} catch(Exception e) {
			cothexoa = false;
			msg = "Xảy ra lỗi khi kiểm tra dữ liệu bảng giá! (" + e.getMessage() + ")";
		}
		
		if(cothexoa) {
			//Xóa các sản phẩm của duyệt bản giá chưa duyệt trong ERP_SUABANGGIA
			if(!db.update("DELETE ERP_SUABGBAN_SANPHAM WHERE SUABGBAN_FK IN ( SELECT PK_SEQ FROM ERP_SUAGIABAN WHERE BGBAN_FK = " + bgId + " AND TINHTRANG != '2' )"))
	    	{
	    		msg = "Không thể xóa các phiếu duyệt bảng giá của bảng giá "+bgId+"!";
	    	}
			
			//Xóa các duyệt bản giá chưa duyệt trong ERP_SUABANGGIA
			if(msg.length() == 0 && !db.update("DELETE ERP_SUAGIABAN WHERE BGBAN_FK = " + bgId + ""))
	    	{
	    		msg = "Không thể xóa các phiếu duyệt bảng giá của bảng giá "+bgId+"!";
	    	}

			//Xóa hợp đồng bảng giá vừa tạo mà chưa sử dụng
			if(msg.length() == 0 && !db.update("DELETE ERP_BangGiaBan_HopDong where banggiaban_fk = " + bgId + ""))
	    	{
	    		msg = "Không thể xóa bảng giá "+bgId+"! (1)";
	    	}
			
			//Xóa khách hàng bảng giá vừa tạo mà chưa sử dụng
			if(msg.length() == 0 && !db.update("DELETE ERP_BANGGIABAN_KH where banggiaban_fk = " + bgId + ""))
	    	{
	    		msg = "Không thể xóa bảng giá "+bgId+"! (2)";
	    	}
			
			//Xóa sản phẩm bảng giá vừa tạo mà chưa sử dụng
			if(msg.length() == 0 && !db.update("DELETE ERP_BGBAN_SANPHAM where BGBAN_FK = " + bgId + ""))
	    	{
	    		msg = "Không thể xóa bảng giá "+bgId+"! (3)";
	    	}
			
			//Xóa bảng giá vừa tạo mà chưa sử dụng
			if(msg.length() == 0 && !db.update("DELETE Erp_BangGiaBan where pk_seq = " + bgId + ""))
	    	{
	    		msg = "Không thể xóa bảng giá "+bgId+"! (4)";
	    	}
		}
    	
    	db.shutDown();
    	return msg;
    	*/
		
		String msg = "";
		dbutils db = new dbutils();
		String query = 	" select COUNT(*) as dem " +
						" from ERP_BANGGIABAN " +
						" where "+ bgId +" in  "+
						"(select distinct BG.PK_SEQ "+
						" from ERP_BANGGIABAN BG " +
						" inner join ERP_BANGGIABAN_KH BGKH on BG.PK_SEQ= BGKH.BANGGIABAN_FK "+
					    " inner join (" +
					    "    select PK_SEQ, KHACHHANG_FK, DVKD_FK, KBH_FK, BGID " +
					    "    from ERP_DONDATHANG " +
					    " ) DDH on DDH.KBH_FK= BG.KENH_FK and DDH.DVKD_FK= BG.DVKD_FK and DDH.KHACHHANG_FK= BGKH.KH_FK AND DDH.BGID = BG.PK_SEQ ) ";
    	System.out.println("Check " + query);
    	int kt = 0;
    	
    	ResultSet rs = db.get(query);
		try {
			if(rs.next()) {				
				kt = rs.getInt("dem");	
			}
			rs.close();
		} catch(Exception e) {
			msg = "Xảy ra lỗi khi kiểm tra dữ liệu bảng giá! (" + e.getMessage() + ")";
		}
		
		if(kt > 0) msg = "Bảng giá này đã được sử dụng trong đơn bán hàng, không thể xóa! ";
		else
		{ 
			msg = "" ;
			//Xóa các sản phẩm của duyệt bản giá chưa duyệt trong ERP_SUABANGGIA
			if(!db.update("DELETE ERP_SUABGBAN_SANPHAM WHERE SUABGBAN_FK IN ( SELECT PK_SEQ FROM ERP_SUAGIABAN WHERE BGBAN_FK = " + bgId + ")"))
	    	{
	    		msg = "Không thể xóa các phiếu duyệt bảng giá của bảng giá "+bgId+"!";
	    	}
			
			//Xóa các duyệt bản giá chưa duyệt trong ERP_SUABANGGIA
			if(msg.length() == 0 && !db.update("DELETE ERP_SUAGIABAN WHERE BGBAN_FK = " + bgId + ""))
	    	{
	    		msg = "Không thể xóa các phiếu duyệt bảng giá của bảng giá "+bgId+"!";
	    	}

			//Xóa hợp đồng bảng giá vừa tạo mà chưa sử dụng
			if(msg.length() == 0 && !db.update("DELETE ERP_BangGiaBan_HopDong where banggiaban_fk = " + bgId + ""))
	    	{
	    		msg = "Không thể xóa bảng giá "+bgId+"! (1)";
	    	}
			
			//Xóa khách hàng bảng giá vừa tạo mà chưa sử dụng
			if(msg.length() == 0 && !db.update("DELETE ERP_BANGGIABAN_KH where banggiaban_fk = " + bgId + ""))
	    	{
	    		msg = "Không thể xóa bảng giá "+bgId+"! (2)";
	    	}
			
			//Xóa sản phẩm bảng giá vừa tạo mà chưa sử dụng
			if(msg.length() == 0 && !db.update("DELETE ERP_BGBAN_SANPHAM where BGBAN_FK = " + bgId + ""))
	    	{
	    		msg = "Không thể xóa bảng giá "+bgId+"! (3)";
	    	}
			
			//Xóa bảng giá vừa tạo mà chưa sử dụng
			if(msg.length() == 0 && !db.update("DELETE Erp_BangGiaBan where pk_seq = " + bgId + ""))
	    	{
	    		msg = "Không thể xóa bảng giá "+bgId+"! (4)";
	    	}
			
		
		}
		
		db.shutDown();
    	return msg;
		
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
	    
	    
	    IErpBanggiabanGiayList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpBanggiabanGiay bgBean = new ErpBanggiabanGiay();
    		bgBean.setCongtyId(ctyId);
    		bgBean.setUserId(userId);
    		bgBean.createRs();

	    	session.setAttribute("bgBean", bgBean);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpBangGiaBanGiayNew.jsp");
	    }
	    else
	    {
	    	obj = new ErpBanggiabanGiayList();
	    	obj.setCongtyId(ctyId);
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpBangGiaBanGiay.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpBanggiabanGiayList obj) 
	{
		Utility util= new Utility();
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
		
		String loaikhId = request.getParameter("loaikhId");
		if(loaikhId == null)
			loaikhId = "";
		obj.setLoaikhId(loaikhId);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String diengiai = request.getParameter("diengiai");
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String sql= " select a.pk_seq, a.ten, a.trangthai, a.tinhtrang, d.ten as nguoitao, a.ngaytao as ngaytao, tungay, denngay, " +
					"     e.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, c.ten as kenh, c.pk_seq as kenhId,   " +
					"     case when isnull((SELECT count(pk_seq) as num FROM ERP_SUAGIABAN WHERE BGBAN_FK = a.pk_seq AND TINHTRANG = '2'), 0) > 0 then 0 else 1 end as moitao " +
					" from ERP_BANGGIABAN a, donvikinhdoanh b, kenhbanhang c, nhanvien d, nhanvien e   " +
					" where a.dvkd_fk = b.pk_seq and a.kenh_fk = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq   " +
					" and a.congty_fk = '" + obj.getCongtyId() + "' ";
		
		if(tenbg.length() > 0)
			sql += " and  dbo.ftBoDau(a.ten) like N'%" + util.replaceAEIOU(tenbg) + "%' ";
		
		if(dvkdId.length() > 0)
			sql += " and a.dvkd_fk = '" + dvkdId + "' ";
		
		if(kenhId.length() > 0)
			sql += " and a.kenh_fk = '" + kenhId + "' ";
		
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		if(diengiai.trim().length() > 0)
			sql += " and a.pk_seq in ( select BANGGIABAN_FK from ERP_BANGGIABAN_KH where KH_FK in ( select PK_SEQ from ERP_KHACHHANG where dbo.ftBoDau( Ma + ' ' + Ten ) like N'%" + util.replaceAEIOU(diengiai) + "%'  ) ) ";
		
		if(loaikhId.trim().length() > 0)
			sql += " and a.pk_seq in ( select BANGGIABAN_FK from ERP_BANGGIABAN_KH where KH_FK in ( select PK_SEQ from ERP_KHACHHANG where loaikh_fk='" + loaikhId + "'  ) ) ";
		
		sql += " order by a.pk_seq desc ";
		
		System.out.println(sql);
		return sql;
	}
	
	

}
