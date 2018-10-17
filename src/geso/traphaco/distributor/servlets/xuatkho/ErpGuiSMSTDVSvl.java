package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.distributor.beans.xuatkho.IErpGuiSMSTDV;
import geso.traphaco.distributor.beans.xuatkho.IErpGuiSMSTDVList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpGuiSMSTDV;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpGuiSMSTDVList;
import geso.traphaco.distributor.util.Toll_GuiMail;
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

import demo.test.SMSClient;

public class ErpGuiSMSTDVSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpGuiSMSTDVSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpGuiSMSTDVList obj;
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
	    obj = new ErpGuiSMSTDVList();

	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
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
    	else if(action.equals("deleteSAUKHIGUI"))
    	{
    		String msg = this.DeleteSAUKHIGUI(lsxId, nppId);
			obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpGuiSMSTDV.jsp";
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
			
			query = "delete ERP_GUISMSTDV_DDH where guisms_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_GUISMSTDV_DDH " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_GUISMSTDV_CC where guisms_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_GUISMSTDV_CC " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_GUISMSTDV  where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_GUISMSTDV " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		
		}
		catch (Exception e) 
		{
			db.update("rollback");
			
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}
	

	private String DeleteSAUKHIGUI(String lsxId, String nppId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			query = "update ERP_GUISMSTDV set trangthai = '2' where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_GUISMSTDV " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		
		}
		catch (Exception e) 
		{
			db.update("rollback");
			
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}
	
	
	private String Chot(String lsxId, String userId, String nppId) 
	{
		dbutils db = new dbutils();
		SMSClient smsClient = new SMSClient();
		
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			//GOP CHUNG BUOC YC VA XUAT THANH 1
			query = "update ERP_GUISMSTDV set trangthai = '1'  where pk_seq = '" + lsxId + "' ";
			System.out.println("---CAP NHAT TRANG THAI: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_GUISMSTDV " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//GUI EMAIL
			try
			{
				Toll_GuiMail mail = new Toll_GuiMail();
				mail.Send_DonDatHang_SMS(lsxId, db, "1");
			}
			catch(Exception ex ){}
			
			//Nhắn SMS cho người nhận
			query = "select b.DIENTHOAI, a.machungtu, a.smsMSG " +
					"from ERP_GUISMSTDV a inner join NHANVIEN b on a.nhanvien_denId = b.PK_SEQ  " +
					"where a.pk_seq = '" + lsxId + "' and nhanvien_denQL = '1'";
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					String dienthoai = rs.getString("DIENTHOAI").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\.", "");
					String machungtu = rs.getString("machungtu");
					String smsMSG = rs.getString("smsMSG");
					
					if( dienthoai.trim().length() < 10 || dienthoai.trim().length() >= 14 )
					{
						msg = "Số điện thoại của nhân viên ( " + dienthoai + " ) nhận không hợp lệ";
						db.getConnection().rollback();
						return msg;
					}
					else
						msg = smsClient.sendSMS(dienthoai, smsMSG);
					
					query = "insert SMS_LOG(machungtu, phoneNumber, sms, ghichu ) " + 
							" values ( '" + machungtu + "', '" + dienthoai + "', N'" + smsMSG + "', N'Lỗi: " + msg + "' )";
					if(!db.update(query))
					{
						msg = "Lỗi khi gủi SMS " + query;
						db.getConnection().rollback();
						return msg;
					}		
				}
				rs.close();
			}
			
			//Nhắn SMS CHO CÁC CẤP CC
			query = "select b.DIENTHOAI, c.machungtu, c.smsMSG " +
					"from ERP_GUISMSTDV_CC a inner join NHANVIEN b on a.nhanvien_fk = b.PK_SEQ  " +
					"		inner join ERP_GUISMSTDV c on a.guisms_fk = c.pk_seq " +
					"where a.guisms_fk = '" + lsxId + "'  and nhanvien_CCQL = '1'";
			rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					String dienthoai = rs.getString("DIENTHOAI").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\.", "");
					String machungtu = rs.getString("machungtu");
					String smsMSG = rs.getString("smsMSG");
					
					if( dienthoai.trim().length() < 10 || dienthoai.trim().length() >= 14 )
					{
						msg = "Số điện thoại của quản lý ( " + dienthoai + " ) không hợp lệ";
						db.getConnection().rollback();
						return msg;
					}
					else
						msg = smsClient.sendSMS(dienthoai, smsMSG);
					
					query = "insert SMS_LOG(machungtu, phoneNumber, sms, ghichu ) " + 
							" values ( '" + machungtu + "', '" + dienthoai + "', N'" + smsMSG + "', N'Lỗi: " + msg + "' )";
					if(!db.update(query))
					{
						msg = "Lỗi khi gủi SMS " + query;
						db.getConnection().rollback();
						return msg;
					}		
				}
				rs.close();
			}
			
			query = "Update ERP_GUISMSTDV set ngayguiSMS = getdate(), trangthaiSMS = 0 where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Lỗi khi gủi SMS " + query;
				db.getConnection().rollback();
				return msg;
			}	
			
			
			//NHẮN TIN SMS -> chi khach hang ngoai tinh HCM
			/*query = "select a.PK_SEQ, a.machungtu, SUBSTRING(NgayDonHang, 0, 5) + SUBSTRING(NgayDonHang, 6, 2) + CAST( a.PK_SEQ as varchar(10) ) as orderId, c.DIENTHOAI  " +
					"from ERP_DONDATHANGNPP a inner join ERP_SOXUATHANGNPP_DDH b on a.PK_SEQ = b.ddh_fk  " +
					"	left join GIAMSATBANHANG c on a.GSBH_FK = c.PK_SEQ	" +
					"where b.soxuathang_fk = '" + lsxId + "' and a.khachhang_fk not in ( select PK_SEQ from KHACHHANG where TINHTHANH_FK = '100049' ) ";
			ResultSet rs = db.get(query);
			String ddhIds = "";
			if( rs != null )
			{
				while( rs.next() )
				{
					ddhIds += rs.getString("PK_SEQ") + ",";
					String dienthoai = rs.getString("DIENTHOAI").replaceAll(" ", "").replaceAll(",", "").replaceAll(".", "");
					//String sms = rs.getString("orderId");
					String sms = rs.getString("machungtu");
					
					if( dienthoai.trim().length() < 10 || dienthoai.trim().length() >= 14 )
						msg = "Số điện thoại không hợp lệ";
					else
						msg = smsClient.sendSMS(dienthoai, sms);
					
					query = "insert SMS_LOG( phoneNumber, sms, ghichu ) values ( '" + dienthoai + "', N'" + sms + "', N'Lỗi: " + msg + "' )";
					if(!db.update(query))
					{
						msg = "Lỗi khi gủi SMS " + query;
						db.getConnection().rollback();
						return msg;
					}		
				}
				rs.close();
			}
			
			if( ddhIds.trim().length() > 0 )
			{
				ddhIds = ddhIds.substring(0, ddhIds.length() - 1);
				
				query = "Update ERP_DONDATHANGNPP set ngayguiSMS = getdate(), trangthaiSMS = 0 where pk_seq in ( " + ddhIds + " ) ";
				if(!db.update(query))
				{
					msg = "Lỗi khi gủi SMS " + query;
					db.getConnection().rollback();
					return msg;
				}	
			}*/
				
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}
		catch (Exception e) 
		{
			db.update("rollback");
			
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
	    
		IErpGuiSMSTDVList obj = new ErpGuiSMSTDVList();
		
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
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    String khId = request.getParameter("khId");
	    if(khId == null)
	    	khId = "";
	    obj.setNppTen(khId);
	    
	    System.out.println("---NPP ID: " + nppId);
	    if(action.equals("Tao moi"))
	    {
	    	IErpGuiSMSTDV lsxBean = new ErpGuiSMSTDV();
	    	lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	lsxBean.setNpp_duocchon_id(npp_duocchon_id);
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
	    	lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	lsxBean.setDoituongId(session.getAttribute("doituongId"));
	    	
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpGuiSMSTDVNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		    	obj.setNpp_duocchon_id(npp_duocchon_id);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpGuiSMSTDV.jsp";
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
		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpGuiSMSTDV.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpGuiSMSTDVList obj)
	{
		Utility util = new Utility();
		String query =  "select a.PK_SEQ, a.machungtu, a.trangthai, a.ngaygiaohang, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.ghichu, " +
						"	 (	Select cast(YCXK1.DDH_FK as varchar(10)) + ', ' AS [text()]  " +
						"		From ERP_GUISMSTDV_DDH YCXK1   " +
						"		Where YCXK1.guisms_fk = a.pk_seq  " +
						"		For XML PATH ('') )  as ddhIds    " +
						"from ERP_GUISMSTDV a  " +
						"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
						" where a.pk_seq > 0 "; 
		
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
		
		String machungtu = request.getParameter("machungtu");
		if(machungtu == null){
			machungtu = "";
		}
		obj.setMachungtu(machungtu);
		
		String donhangid = request.getParameter("donhangid");
		if(donhangid == null){
			donhangid ="";
		} 
		obj.setDonhangid(donhangid);
		
		if(donhangid.length() >0){
			query += " and  a. pk_seq in( select guisms_fk from  " +
					" ERP_GUISMSTDV_DDH a where a.DDH_FK = "+ donhangid +")";
		}
		
		if(machungtu.length() >0){
			query += " and a.machungtu ="+ machungtu;
		}
		if(tungay.length() > 0)
			query += " and a.ngaygiaohang >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaygiaohang <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
		{
			if(trangthai.equals("0"))
				query += " and a.TrangThai = '" + trangthai + "'";
			else
				query += " and a.TrangThai = '" + trangthai + "'";
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
