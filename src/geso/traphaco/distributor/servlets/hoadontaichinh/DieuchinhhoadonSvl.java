package geso.traphaco.distributor.servlets.hoadontaichinh;

import geso.traphaco.distributor.beans.hoadontaichinh.IHoadontaichinh;
import geso.traphaco.distributor.beans.hoadontaichinh.IHoadontaichinhList;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.Hoadontaichinh;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.HoadontaichinhList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
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

public class DieuchinhhoadonSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public DieuchinhhoadonSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IHoadontaichinhList obj;
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
	    
	    
    	String lsxId = util.getId(querystring);
	    obj = new HoadontaichinhList();
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    System.out.println("---LOAI DON HANG: " + loaidonhang);
	    
    	if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId);
			obj.setMsg(msg);
    	}
    	else if(action.equals("delete"))  //DELETE SE GIONG HUY, SE MO LAI TRANG THAI DON HANG
    	{
    		/*String msg = this.Delete(lsxId);
			obj.setMsg(msg);*/
			
			String msg = this.HuyHoaDon(lsxId, userId);
			obj.setMsg(msg);
    	}
    	else if(action.equals("huy"))
    	{
    		String msg = this.HuyHoaDon(lsxId, userId);
			obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init_dieuchinh("");
	    
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhHoaDon.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String HuyHoaDon(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			query = "update HOADON set trangthai = '5', nguoisua = '" + userId + "' where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_HOADON " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//HUY HOA DON KHUYEN MAI HOAC BAN NEU CO
			query = "update HOADON set trangthai = '5', nguoisua = '" + userId + "' " +
					"where  loaihoadon= 1 and pk_seq in (select hoadon_fk from HOADON_DDH where DDH_FK = (select DDH_FK from HOADON_DDH where HOADON_FK = '" + lsxId + "') and hoadon_fk != '" + lsxId + "' )  ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_HOADON " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//TU DONG XOA PHIEU XUAT KHO
			query = "select DDH_FK as donhang_fk,  " +
						"ISNULL( ( select Import from DONHANG where pk_seq = a.DDH_FK ), 0) as Import, " +
						"( select PXK_FK from PHIEUXUATKHO_DONHANG where donhang_fk = a.DDH_FK ) as soPXK  " +
					"from HOADON_DDH a where hoadon_fk = '" + lsxId + "'";
			System.out.println("---HUY HOA DON: " + query );
			
			String donhang_fk = "";
			String Import = "";
			String pxkId = "";
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					donhang_fk = rs.getString("donhang_fk");
					Import = rs.getString("Import");
					
					if(Import.equals("0"))
					{
						pxkId = rs.getString("soPXK");
						
						//CHECK KHOA SO THANG
						//THANG CHOT PHIEU XUAT KHO BUOC PHAI SAU THANG KS + 1
						/*query = "select top(1) NAM as namMax, THANGKS as thangMax, " +
								"	( select ngaylapphieu from PHIEUXUATKHO where pk_seq = '" + pxkId + "' ) as ngaylapphieu " +
								"from KHOASOTHANG where NPP_FK = ( select NPP_FK from DONHANG where pk_seq = '" + donhang_fk + "' ) " +
								"order by NAM desc, THANGKS desc ";
						System.out.println("1.Khoi tao thang: " + query);
						ResultSet rsKS = db.get(query);
						
						try
						{
							if(rsKS != null)
							{
								while(rsKS.next())
								{
									String thangHL = "";
									String namHL = "";
									
									String thangKs = rsKS.getString("thangMax");
									String namKs = rsKS.getString("namMax"); 
							
									String nam = rsKS.getString("ngaylapphieu").substring(0, 4);
									String thang = rsKS.getString("ngaylapphieu").substring(5, 7);
									
									if(thangKs.equals("12"))
									{
										thangHL = "1";
										namHL = Integer.toString(Integer.parseInt(namKs) + 1);
									}
									else
									{
										thangHL = Integer.toString(Integer.parseInt(thangKs) + 1);
										namHL = namKs;
									}
									
									if(thangHL.trim().length() < 2)
										thangHL = "0" + thangHL;
									
									if(	!thangHL.equals(thang) || !namHL.equals(nam) )
									{
										msg = "Bạn chỉ được hủy hóa đơn ở tháng khóa sổ + 1 ";
										rs.close();
										return msg;
									}
									
								}
								rs.close();
							}
						}
						catch (Exception e) 
						{
							e.printStackTrace();
							return "Lỗi khi hủy hóa đơn " + e.getMessage();
						}*/
						
						//TANG KHO NGUOC LAI
						query = "update kho set kho.SOLUONG = kho.SOLUONG + xuat.soluong, " +
								"			   kho.BOOKED = kho.BOOKED + xuat.soluong	 " +
								"from NHAPP_KHO kho inner join " +
								"( " +
								"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, SUM(b.soluong) as soluong " +
								"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SANPHAM b on a.PK_SEQ = b.PXK_FK " +
								"		where a.PK_SEQ = '" + pxkId + "' " +
								"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK " +
								"	union ALL " +
								"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, SUM(b.soluong) as soluong " +
								"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SPKM b on a.PK_SEQ = b.PXK_FK " +
								"		where a.PK_SEQ = '" + pxkId + "' " +
								"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK " +
								") " +
								"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.KBH_FK = xuat.kbh_fk and kho.KHO_FK = xuat.kho_fk ";
						System.out.println("1.TANG KHO: " + query);
						if(!db.update(query))
						{
							rs.close();
							msg = "Không thể hủy " + query;
							db.getConnection().rollback();
							return msg;
						}
						
						query = "update kho set kho.SOLUONG = kho.SOLUONG + xuat.soluong, " +
								"			   kho.BOOKED = kho.BOOKED + xuat.soluong	 " +
								"from NHAPP_KHO_CHITIET kho inner join " +
								"( " +
								"	select npp_fk,kbh_fk,kho_fk,sanpham_fk,sum(SoLuong) as SoLuong,SoLo,NgayHetHan   "+ 
								"	from  "+ 
								"( 		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong,b.NgayHetHan " +
								"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SANPHAM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
								"		where a.PK_SEQ = '" + pxkId + "' " +
								"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " +
								"	union ALL " +
								"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong, b.NgayHetHan" +
								"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SPKM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
								"		where a.PK_SEQ = '" + pxkId + "' " +
								"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " +
								" )  "+								
								") " +
								"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.KBH_FK = xuat.kbh_fk and kho.KHO_FK = xuat.kho_fk and kho.SOLO = xuat.SOLO and kho.NgayHetHan = xuat.NgayHetHan ";
						System.out.println("2.TANG KHO: " + query);
						if(!db.update(query))
						{
							rs.close();
							msg = "Không thể hủy " + query;
							db.getConnection().rollback();
							return msg;
						}
						
						//HUY PHIEU XUAT KHO
						query = "delete phieuxuatkho_tienkm where pxk_fk = '" + pxkId + "'";
						if(!db.update(query))
						{
							rs.close();
							msg = "Không thể hủy " + query;
							db.getConnection().rollback();
							return msg;
						}
								
						query = "delete phieuxuatkho_sanpham where pxk_fk = '" + pxkId + "'";
						if(!db.update(query))
						{
							rs.close();
							msg = "Không thể hủy " + query;
							db.getConnection().rollback();
							return msg;
						}

						query = "delete phieuxuatkho_spkm where pxk_fk = '" + pxkId + "'";
						if(!db.update(query))
						{
							rs.close();
							msg = "Không thể hủy " + query;
							db.getConnection().rollback();
							return msg;
						}
						query = "delete PHIEUXUATKHO_DONHANG where pxk_fk='"+pxkId+"'";
						if(!db.update(query))
						{
							rs.close();
							msg = "Không thể hủy " + query;
							db.getConnection().rollback();
							return msg;
						}

						query="delete phieuxuatkho where pk_seq = '" + pxkId + "'";
						if(! db.update(query))
						{
							rs.close();
							msg = "Không thể hủy " + query;
							db.getConnection().rollback();
							return msg;
						}
						
						//MO LAI TRANG THAI DON HANG
						query = "Update DONHANG set trangthai = '0', daxuathoadon = '0' where pk_seq = '" + donhang_fk + "' ";
						if(!db.update(query))
						{
							rs.close();
							msg = "Không thể hủy đơn hàng " + query;
							db.getConnection().rollback();
							return msg;
						}
					}
					else
					{
						rs.close();
						msg = "Không thể hủy hóa đơn tự IMPORT vào hệ thống ";
						db.getConnection().rollback();
						return msg;
					}
				}
				rs.close();
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

	private String Delete(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			query = "update HOADON set trangthai = '3' where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_HOADON " + query;
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
	
	private String Chot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
						
			
			query = "update HOADON set trangthai = '2'  where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHO " + query;
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
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    
	    
		IHoadontaichinhList obj = new HoadontaichinhList();
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IHoadontaichinh lsxBean = new Hoadontaichinh();
	    	
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhHoaDon.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
	    		obj.setUserId(userId);
		    	obj.init(search);								
		    	
		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhHoaDon.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IHoadontaichinhList obj)
	{
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		String query = "select  a.sohoadon, a.kyhieu , a.PK_SEQ, a.trangthai, a.ngayxuatHD, NV.TEN as nguoitao, KH.TEN as nppTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua ," +
	    "  a.tongtienavat as avat, case a.trangthai when 1 then 1 when 2 then 2 when 3 then 4 when 4 then 3 when 5 then 5 end as STT_SORT " +
		"from HOADON a  " +
		"inner join KHACHHANG KH on a.KHACHHANG_FK=KH.PK_SEQ  " +
		"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
		"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 and a.NPP_FK ="+ nppId +" and isnull(loaihoadon,0) = 0   ";

		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String madonhang = request.getParameter("madonhang");
		if(madonhang == null)
			madonhang = "";
		obj.setMadonhang(madonhang);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String khten = request.getParameter("khTen");		
		if(khten == null)
			khten = "";
		obj.setKhTen(khten);
		
		
		String sohoadon = request.getParameter("sohoadon");		
		if(sohoadon == null)
			sohoadon = "";
		obj.setSoHoaDon(sohoadon.trim());
		
		
		if(tungay.length() > 0)
			query += " and a.ngayxuatHD >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayxuatHD <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(khten.length() > 0)
			query += " and kh.pk_seq like '%" + khten + "%' ";
		
		
		if(sohoadon.length() > 0)
			query += " and a.sohoadon like   '%" + sohoadon + "%' ";
		
		if(madonhang.length() > 0)
			query += " and a.pk_seq in ( select hoadon_fk from HOADON_DDH where ddh_fk = " + madonhang + ") ";
		
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
