package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.distributor.beans.xuatkho.IErpXuathoadonKM;
import geso.traphaco.distributor.beans.xuatkho.IErpXuathoadonKMList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpXuathoadonKM;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpXuathoadonKMList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import demo.test.SMSClient;

public class ErpXuathoadonKMSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpXuathoadonKMSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpXuathoadonKMList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	  
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
    	String lsxId = util.getId(querystring);
	    obj = new ErpXuathoadonKMList();
	    
	    /*String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    
	    String phanloai = request.getParameter("loai");
		if(phanloai == null)
			phanloai = "";
		obj.setPhanloai(phanloai);*/
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
    	if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId, userId, nppId);
			obj.setMsg(msg);
    	}
    	else if(action.equals("delete"))
    	{
    		String msg = this.Delete(lsxId, nppId);
			obj.setMsg(msg);
    	}
	    
    	obj.setTdv_dangnhap_id(tdv_dangnhap_id);
 	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpXuatHoaDonKM.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String Delete(String lsxId, String nppId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			//XOA GHI NHAN
			query = "delete ERP_XUATHOADONKM_CTKM where xuathoadonkm_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_XUATHOADONKM_CTKM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_XUATHOADONKM_SANPHAM where xuathoadonkm_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_XUATHOADONKM_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_XUATHOADONKM  where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_XUATHOADONKM " + query;
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
		
		return "";
	}
	
	private String Chot(String lsxId, String userId, String nppId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			query = "update ERP_XUATHOADONKM set trangthai = '1'  where pk_seq = '" + lsxId + "' ";
			System.out.println("---CAP NHAT TRANG THAI: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_XUATHOADONKM " + query;
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
	   
	    
		IErpXuathoadonKMList obj = new ErpXuathoadonKMList();
		
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    obj.setUserId(userId);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    System.out.println("NPPID "+nppId);
	    String khId = request.getParameter("khId");
	    if(khId == null)
	    	khId = "";
	    obj.setNppTen(khId);
	    
	    System.out.println("---NPP ID: " + nppId);
	    if(action.equals("Tao moi"))
	    {
	    	IErpXuathoadonKM lsxBean = new ErpXuathoadonKM();
	    	lsxBean.setUserId(userId);
	    	lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	lsxBean.setNpp_duocchon_id(npp_duocchon_id);
	    	//lsxBean.setXuatcho(phanloai);
	    	lsxBean.createRs();
	    	
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpXuatHoaDonKMNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		    	obj.setNpp_duocchon_id(npp_duocchon_id);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpXuatHoaDonKM.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{	
		    	String search = getSearchQuery(request, obj);
		    	obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		    	obj.setNpp_duocchon_id(npp_duocchon_id);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpXuatHoaDonKM.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpXuathoadonKMList obj)
	{
		String query = "select a.PK_SEQ, a.trangthai, a.ngayhoadon, c.ten as nppTEN, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua " +
				"from ERP_XUATHOADONKM a  " +
				"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
				"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
				"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
				" where a.npp_fk = '" + obj.getNppId() + "' ";
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String nvbanhang=request.getParameter("nvbanhang");
		if(nvbanhang==null)
			nvbanhang="";
		obj.setNvbanhang(nvbanhang);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
	
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setNppTen(khId);
		
		String khohh = request.getParameter("khohhid");
		if(khohh == null)
			khohh = "";
		obj.setKhohh(khohh);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String nguoigiao = request.getParameter("nguoigiao");
		if(nguoigiao == null)
			nguoigiao = "";
		obj.setNguoigiao(nguoigiao);
		
		if(tungay.length() > 0)
			query += " and a.ngayhoadon >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayhoadon <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
		{
			if(trangthai.equals("0"))
				query += " and a.TrangThai = '" + trangthai + "'";
			else
				query += " and a.TrangThai >= '" + trangthai + "'";
		}
		
		if(khId.length() > 0)
		{
			//query += " and isnull(a.npp_Dat_fk, a.khachhang_Fk) = '" + khId + "'";
			query += " and c.timkiem like N'%" + khId + "%'";
		}
	
		System.out.print(query);
		return query;
	}
	private String getExportExcel(HttpServletRequest request, IErpXuathoadonKMList obj)
	{
		Utility util = new Utility();
		String query =
				"select  a.machungtu as N'Mã chứng từ',a.ngayyeucau as N'Ngày yêu cầu', isnull(c.ten, d.ten) as N'Khách hàng', b.ten as N'Kho xuất', " +
						"	 (	Select hd.sohoadon + ', ' AS [text()]  " +
						"		From ERP_SOXUATHANGNPP_DDH YCXK1 inner join ERP_HOADONNPP hd on  YCXK1.hoadon_fk = hd.pk_seq  " +
						"		Where YCXK1.soxuathang_fk = a.pk_seq  " +
						"		For XML PATH ('') )   as N'Số hóa đơn' ,case when a.trangthai  = 0 then N'Chưa chốt' else N'Đã chốt' end as N'Trạng thái' , NV.TEN as N'Người tạo', a.NGAYTAO as N'Ngày tạo', a.NGAYSUA as N'Ngày sửa' , NV2.TEN as N'Người sửa'   " +
						"from ERP_SOXUATHANGNPP a inner join KHO b on a.kho_fk = b.pk_seq " +
						"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
						"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " +
						"   left join NVGN_KH nvgn on a.KHACHHANG_FK=nvgn.KHACHHANG_FK   "+
						"   left join ddkd_khachhang ddkd on a.PK_SEQ=ddkd.khachhang_fk "+
						"   left join NHANVIENGIAONHAN nvgnn on nvgnn.PK_SEQ=nvgn.NVGN_FK  "+	
						"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + obj.getNppId() + "' and a.kho_fk in "+util.quyen_kho(obj.getUserId()); 
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String nvbanhang=request.getParameter("nvbanhang");
		if(nvbanhang==null)
			nvbanhang="";
		obj.setNvbanhang(nvbanhang);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
	
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setNppTen(khId);
		
		String khohh = request.getParameter("khohhid");
		if(khohh == null)
			khohh = "";
		obj.setKhohh(khohh);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String nguoigiao = request.getParameter("nguoigiao");
		if(nguoigiao == null)
			nguoigiao = "";
		obj.setNguoigiao(nguoigiao);
		
		if(tungay.length() > 0)
			query += " and a.ngayyeucau >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayyeucau <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
		{
			if(trangthai.equals("0"))
				query += " and a.TrangThai = '" + trangthai + "'";
			else
				query += " and a.TrangThai >= '" + trangthai + "'";
		}
		
		if(khId.length() > 0)
		{
			//query += " and isnull(a.npp_Dat_fk, a.khachhang_Fk) = '" + khId + "'";
			query += " and isnull(d.timkiem, c.timkiem) like N'%" + khId + "%'";
		}
		
		if(khohh.length()>0)
		{
			query+=" and a.kho_fk="+khohh;
		}
		
		if(nguoitao.length()>0)
		{
			query+=" and dbo.ftBoDau( nv.ten ) like N'%" + util.replaceAEIOU( nguoitao ) + "%'";
		}
		if(nguoigiao.length()>0)
		{
			query+=" and dbo.ftBoDau( nvgnn.ten ) like N'%" + util.replaceAEIOU( nguoigiao ) + "%'";
		}
		
		if(nvbanhang.length()>0)
		{
			query+=" and ddkd.ddkd_fk="+nvbanhang;
		}
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
