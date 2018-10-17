package geso.traphaco.distributor.servlets.dondathang;

import geso.traphaco.distributor.beans.dondathang.IErpDuyetddhNppList;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDuyetddhNppList;
import geso.traphaco.distributor.beans.hopdong.imp.ErpDonhangNppETC;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDuyetddhNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	public ErpDuyetddhNppSvl() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDuyetddhNppList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();	    
		
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		if (userId.length()==0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String action = util.getAction(querystring);
		
		obj = new ErpDuyetddhNppList();
		
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		
		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		obj.setNpp_duocchon_id(npp_duocchon_id);
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
		
		/*String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
	    obj.setPhanloai(loaidonhang);*/
	    
	    String capduyet = request.getParameter("capduyet");
	    if(capduyet == null)
	    	capduyet = "";
	    obj.setCapduyet(capduyet);
		
		if(action.equals("duyetALL"))
		{
			String msg = this.DuyetALL(request, session.getAttribute("congtyId").toString(), userId, capduyet);
			obj.setMsg(msg);
		}
		
		obj.setUserId(userId);
		obj.init("");
		
		session.setAttribute("obj", obj);
		
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppDuyet.jsp";
		response.sendRedirect(nextJSP);
		
	}
	
	private String DuyetALL(HttpServletRequest request, String congtyId, String userId, String capduyet) 
	{
		String[] dhIds = request.getParameterValues("donhangIds");
		
		if( capduyet.equals("CS") )
		{
			//CHECK CÓ ĐƠN HÀNG TICK TRẢ TÍCH LŨY HAY KHÔNG
			String _dhId = "";
			for(int i = 0; i < dhIds.length; i++)
			{
				_dhId += dhIds[i] + ",";
			}
			
			if( _dhId.trim().length() > 0 )
			{
				_dhId = _dhId.substring(0, _dhId.length() - 1);
				dbutils db = new dbutils();
				
				String query = "select pk_seq from ERP_DONDATHANGNPP where tratichluy = '1' and pk_seq in ( " + _dhId + " ) ";
				ResultSet rs = db.get(query);
				
				String dhtichluyIds = "";
				if( rs != null )
				{
					try 
					{
						while( rs.next() )
						{
							dhtichluyIds += rs.getString("pk_seq");
						}
						rs.close();
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				
				db.shutDown();
				
				if( dhtichluyIds.trim().length() > 0 )
				{
					return "Có đơn hàng trả tích lũy ( " + dhtichluyIds + " ), bạn không thể duyệt đồng loạt";
				}
			}
		}
		
		//String _dhId = "";
		String msg = "";
		if(dhIds != null)
		{
			String error = "Lỗi khi duyệt đơn hàng: \n";
			for(int i = 0; i < dhIds.length; i++)
			{
				//_dhId += dhIds[i] + ",";
				ErpDonhangNppETC donhang = new ErpDonhangNppETC( dhIds[i] );
				donhang.setCapduyet(capduyet);
				donhang.setUserId(userId);
				
				if(!donhang.duyetETC(congtyId, "benngoai"))
				{
					System.out.println("--- Đơn hàng: " + dhIds[i] + " -- Lỗi: " + donhang.getMsg() );
					
					error += " - Đơn hàng: " + dhIds[i] + ": " + donhang.getMsg();
								
					donhang.DBclose();
				}
	
			}
			
			if( !error.equals("Lỗi khi duyệt đơn hàng: \n") )
				msg = error;
			
			/*if(_dhId.trim().length() > 0)
			{
				_dhId = _dhId.substring(0, _dhId.length() - 1);
			}
			
			dbutils db = new dbutils();
			
			if(capduyet.equals("CS"))
				db.update("UPDATE ERP_DONDATHANGNPP set CS_DUYET = 1, thoidiem_cs_duyet = getdate(), userId_cs_duyet = '" + userId + "' where pk_seq in ( " + _dhId + " ) ");
			else
				db.update("UPDATE ERP_DONDATHANGNPP set SS_DUYET = 1, thoidiem_ss_duyet = getdate(), userId_ss_duyet = '" + userId + "' where pk_seq in ( " + _dhId + " ) ");
				
			db.shutDown();*/
		}

		return msg;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		Utility util = new Utility();
		HttpSession session = request.getSession();
		
		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}	    
		
		IErpDuyetddhNppList obj = new ErpDuyetddhNppList();
		
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		
		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		obj.setNpp_duocchon_id(npp_duocchon_id);
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
		
	    String phanloai = request.getParameter("phanloai");
	    if(phanloai == null)
	    	phanloai = "";
	    obj.setPhanloai(phanloai);
	    
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
	    obj.setLoaidonhang(loaidonhang);
	    
	    String capduyet = request.getParameter("capduyet");
	    if(capduyet == null)
	    	capduyet = "";
	    obj.setCapduyet(capduyet);
	    
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		obj.setUserId(userId);
		
		String search = getSearchQuery(request, obj);
		if(action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.setUserId(userId);
			obj.init(search);
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppDuyet.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("duyetALL"))
		{
			String msg = this.DuyetALL(request, session.getAttribute("congtyId").toString(), userId, capduyet);
			obj.setMsg(msg);
			
			obj.setUserId(userId);
			obj.init(search);
			
			session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppDuyet.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("moduyetSS"))
		{
			String msg = this.MoDuyetSS(request, userId);
			obj.setMsg(msg);
			
			obj.setUserId(userId);
			obj.init(search);
			
			session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppDuyet.jsp";
			response.sendRedirect(nextJSP);
		} 
		else if(action.equals("khongduyet"))
		{
			String msg = this.KhongDuyet(request, userId);
			obj.setMsg(msg);
			
			obj.setUserId(userId);
			obj.init(search);
			
			session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppDuyet.jsp";
			response.sendRedirect(nextJSP);
		} 
		else
		{
			obj.setUserId(userId);
			obj.init(search);
			
			session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppDuyet.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	private String KhongDuyet(HttpServletRequest request, String userId)
	{
		String dhId = request.getParameter("dhId");
		if( dhId == null )
			dhId = "";
		
		String lydoxoa = request.getParameter("lydoxoa");
		if( lydoxoa == null )
			lydoxoa = "";
		
		//Không bắt buộc nữa
		//if( lydoxoa.trim().length() <= 0 )
			//return "Bạn phải nhập lý do mở duyệt";
		
		dbutils db = new dbutils();
		
		//CHECK XEM CS DUYET ROI THI KHONG DUOC MO
		String query =  "select CS_DUYET,  "+
						 "	( select COUNT(hd.PK_SEQ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK "+
						 "	  where hd_dh.DDH_FK = a.PK_SEQ and hd.TRANGTHAI not in ( 3, 5 )	 ) as dacoHD "+
						 "from ERP_DONDATHANGNPP a where PK_SEQ = '" + dhId + "'";
		ResultSet rs = db.get(query);
		int CS_DUYET = 0;
		int dacoHD = 0;
		if( rs != null )
		{
			try 
			{
				if( rs.next() )
				{
					CS_DUYET = rs.getInt("CS_DUYET");
					dacoHD = rs.getInt("dacoHD");
				}
				rs.close();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		if( CS_DUYET >= 1 )
		{
			db.shutDown();
			return "Đơn hàng này CS đã duyệt. Bạn không thể tiếp tục thao tác";
		}
		
		if( dacoHD >= 1 )
		{
			db.shutDown();
			return "Đơn hàng này đã có hóa đơn. Bạn không thể tiếp tục thao tác";
		}
		
		//CS CŨNG CÓ THỂ KHÔNG DUYỆT
		query = "Update ERP_DONDATHANGNPP set trangthai = '3', ngaymoduyet = '" + this.getDateTime() + "', lydokhongduyet = N'" + lydoxoa + "' " + 
				" where pk_seq = '" + dhId + "' ";
		System.out.println("::: MO DUYET SS: " + query );
		if( !db.update(query) )
		{
			db.shutDown();
			return "Lỗi khi mở duyệt: " + query;
		}
		
		//CẬP NHẬT TOOLTIP
		db.execProceduce2("CapNhatTooltip", new String[] { dhId } );
		db.shutDown();
		
		return "";
	}
	
	private String MoDuyetSS(HttpServletRequest request, String userId)
	{
		String dhId = request.getParameter("dhId");
		if( dhId == null )
			dhId = "";
		
		String lydoxoa = request.getParameter("lydoxoa");
		if( lydoxoa == null )
			lydoxoa = "";
		
		//Không bắt buộc nữa
		//if( lydoxoa.trim().length() <= 0 )
			//return "Bạn phải nhập lý do mở duyệt";
		
		dbutils db = new dbutils();
		
		//CHECK XEM CS DUYET ROI THI KHONG DUOC MO
		String query =  "select CS_DUYET,  "+
						 "	( select COUNT(hd.PK_SEQ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK "+
						 "	  where hd_dh.DDH_FK = a.PK_SEQ and hd.TRANGTHAI not in ( 3, 5 )	 ) as dacoHD "+
						 "from ERP_DONDATHANGNPP a where PK_SEQ = '" + dhId + "'";
		ResultSet rs = db.get(query);
		int CS_DUYET = 0;
		int dacoHD = 0;
		if( rs != null )
		{
			try 
			{
				if( rs.next() )
				{
					CS_DUYET = rs.getInt("CS_DUYET");
					dacoHD = rs.getInt("dacoHD");
				}
				rs.close();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		if( CS_DUYET >= 1 )
		{
			db.shutDown();
			return "Đơn hàng này CS đã duyệt. Bạn không thể mở duyệt";
		}
		
		if( dacoHD >= 1 )
		{
			db.shutDown();
			return "Đơn hàng này đã có hóa đơn. Bạn không thể mở duyệt";
		}
		
		query = "Update ERP_DONDATHANGNPP set SS_DUYET = 0, trangthai = '0', ngaymoduyet = '" + this.getDateTime() + "', lydomoduyet = N'" + lydoxoa + "' " + 
				" where pk_seq = '" + dhId + "' ";
		System.out.println("::: MO DUYET SS: " + query );
		if( !db.update(query) )
		{
			db.shutDown();
			return "Lỗi khi mở duyệt: " + query;
		}
		
		//CẬP NHẬT TOOLTIP
		db.execProceduce2("CapNhatTooltip", new String[] { dhId } );
		db.shutDown();
				
		return "";
	}

	private String getSearchQuery(HttpServletRequest request, IErpDuyetddhNppList obj)
	{
		Utility util = new Utility();
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		String htbh=request.getParameter("htbhid");
		if(htbh==null)
			htbh="";
		obj.setHtbhId(htbh);
		
		String query = "";
		query =	"select distinct a.PK_SEQ, a.machungtu, a.trangthai, a.ngaydonhang, case a.loaidonhang when 0 then d.ten when 3 then e.ten else c.ten end as nppTEN,a.tooltip, " + 
				"  a.tooltip_scheme, a.loaidonhang, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, isnull(SOTIENTHU, 0) as SOTIENTHU, a.hopdong_fk ,isnull(a.iskm,0) as isKm, a.CS_DUYET, a.SS_DUYET, a.ASM_DUYET, isnull(a.CAPDOGIAOHANG, 0) as CAPDOGIAOHANG, isnull( lydokhongduyet, '' ) lydokhongduyet " +
				"from ERP_DondathangNPP a inner join KHO b on a.kho_fk = b.pk_seq " +
				"	left join KHACHHANG c on a.khachhang_fk = c.pk_seq  " +
				"	left join NHAPHANPHOI d on a.NPP_DAT_FK = d.pk_seq  " +
				" 	left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq " +
				"   inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
				"	left join diaban db on db.pk_seq = c.diaban   " +
				"	left join KHACHHANG_kenhbanhang khkbh on khkbh.khachhang_fk = c.pk_seq  " +
				"	inner join ERP_DondathangNPP_sanpham ycxksp on ycxksp.dondathang_fk = a.pk_seq   " +
				"   inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + nppId + "' and a.trangthai != 3 ";
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String kbhid=request.getParameter("kbhId");
		if(kbhid==null)
			kbhid="";
		obj.setKbhId(kbhid);
		
		String kvid=request.getParameter("kvid");
		if(kvid==null)
			kvid="";
		obj.setKvId(kvid);
		
		String spid=request.getParameter("spid");
		if(spid==null)
			spid="";
		obj.setSpId(spid);
			
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String sodh = request.getParameter("sodh");
		if(sodh == null)
			sodh = "";
		obj.setSodh(sodh);
		
		String makhachhang = request.getParameter("makhachhang");
		if(makhachhang == null)
			makhachhang = "";
		obj.setKhTen(makhachhang);
		
		String kbhId = request.getParameter("kbhId");
		if(kbhId == null)
			kbhId = "";
		obj.setKbhId(kbhId);
		
		String nguoitao = request.getParameter("nguoitao");
	    if(nguoitao == null)
	    	nguoitao = "";
	    obj.setNguoitao(nguoitao);
	    
	    String nguoisua = request.getParameter("nguoisua");
	    if(nguoisua == null)
	    	nguoisua = "";
	    obj.setNguoisua(nguoisua);
		
		if(tungay.length() > 0)
			query += " and a.ngaydonhang >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaydonhang <= '" + denngay + "'";
		
		if(obj.getLoaidonhang().length() > 0)
			query += " and a.LoaiDonHang = '" + obj.getLoaidonhang() + "'";
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}
		
		if(sodh.length()>0){
			query+= " and a.machungtu LIKE '%" + sodh + "%'";
		}
		
		if(makhachhang.length()>0){
			//query += " and case a.loaidonhang when 0 then d.TimKiem when 3 then e.ten else c.timkiem end LIKE N'%'+dbo.ftBoDau('" + makhachhang + "')+'%'";
			query += " and isnull(d.TimKiem, c.timkiem ) LIKE   N'%" + util.replaceAEIOU( makhachhang.trim() ) + "%'";
		}
		if(kvid.length()>0)
		{
			query += " and db.khuvuc_fk = '"+kvid+"'" ;
		}
		
		if(spid.length()>0)
		{
			query += " and ycxksp.sanpham_fk = '"+spid+"'" ;
		}
		
		if(kbhid.length() > 0)
		{
		
			query += "and khkbh.kbh_fk = '"+kbhid+"' ";	
		}
		
		if(htbh.length() > 0)
		{
			query += "and khkbh.kbh_fk  in (select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '"+htbh+"') ";	
		}
		
		if(nguoitao.trim().length() > 0)
		{
			query += " and a.nguoitao in ( select pk_seq from NHANVIEN where timkiem like N'%" + util.replaceAEIOU( nguoitao.trim() ) + "%' ) ";	
		}
		
		if(nguoisua.trim().length() > 0)
		{
			query += " and a.nguoisua in ( select pk_seq from NHANVIEN where timkiem like N'%" + util.replaceAEIOU( nguoisua.trim() ) + "%' ) ";	
		}
		
		System.out.print("caau tim kiem " + query);
		return query;
	}
	
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	
	
	
	
}
