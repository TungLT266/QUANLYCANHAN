package geso.traphaco.center.servlets.doihang;

import geso.traphaco.center.beans.doihang.IErpDenghidoihang;
import geso.traphaco.center.beans.doihang.IErpDenghidoihangList;
import geso.traphaco.center.beans.doihang.imp.ErpDenghidoihang;
import geso.traphaco.center.beans.doihang.imp.ErpDenghidoihangList;
import geso.traphaco.center.db.sql.dbutils;
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

public class ErpDenghidoihangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDenghidoihangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDenghidoihangList obj;
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
	    obj = new ErpDenghidoihangList();
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    System.out.println("---LOAI DON HANG: " + loaidonhang);
	    
	    if (action.equals("delete") )
	    {	
	    	String msg = this.DeleteChuyenKho(lsxId);
			obj.setMsg(msg);
	    }
	    else if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId);
			obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHang.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String Chot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			//TANG KHO NGUOC LAI
			String query =  " update kho  " +
							" 	set kho.BOOKED = kho.BOOKED - doihang.denghi, " +
							" 		kho.SOLUONG = kho.SOLUONG + doihang.denghi " +
							" from " +
							" ( " +
							" 	select a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, b.solo, b.denghi  " +
							" 	from ERP_DENGHIDOIHANG a inner join ERP_DENGHIDOIHANG_SANPHAM b on a.pk_seq = b.denghidoihang_fk " +
							" 	where a.pk_seq = '" + lsxId + "' " +
							" ) " +
							" doihang inner join NHAPP_KHO kho on doihang.kho_fk = kho.KHO_FK and doihang.NPP_FK = kho.NPP_FK " +
							" 		and doihang.KBH_FK = kho.KBH_FK and doihang.sanpham_fk = kho.SANPHAM_FK ";
			System.out.println("2.update DDH: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = " update kho  " +
					" 	set kho.BOOKED = kho.BOOKED - doihang.denghi, " +
					" 		kho.SOLUONG = kho.SOLUONG - doihang.denghi " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, b.solo, b.denghi  " +
					" 	from ERP_DENGHIDOIHANG a inner join ERP_DENGHIDOIHANG_SANPHAM b on a.pk_seq = b.denghidoihang_fk " +
					" 	where a.pk_seq = '" + lsxId + "' " +
					" ) " +
					" doihang inner join NHAPP_KHO_CHITIET kho on doihang.kho_fk = kho.KHO_FK and doihang.NPP_FK = kho.NPP_FK " +
					" 		and doihang.KBH_FK = kho.KBH_FK and doihang.sanpham_fk = kho.SANPHAM_FK and doihang.solo = kho.SOLO ";
			System.out.println("3.update DDH: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//TANG KHO CUA NPP
			query = " select a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, b.solo, b.denghi, " +
					" 		( select COUNT(*) from NHAPP_KHO where NPP_FK = a.NPP_FK and KHO_FK = '100004' and SANPHAM_FK = b.sanpham_fk and KBH_FK = a.KBH_FK ) as exitKHOTONG, " +
					" 		( select COUNT(*) from NHAPP_KHO where NPP_FK = a.NPP_FK and KHO_FK = '100004' and SANPHAM_FK = b.sanpham_fk and KBH_FK = a.KBH_FK and SOLO = b.SOLO ) as exitKHOCT " +
					" from ERP_DENGHIDOIHANG a inner join ERP_DENGHIDOIHANG_SANPHAM b on a.pk_seq = b.denghidoihang_fk " +
					" where a.pk_seq = '" + lsxId + "' ";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String npp_fk = rs.getString("npp_fk");
					String kbh_fk = rs.getString("kbh_fk");
					String sanpham_fk = rs.getString("sanpham_fk");
					String solo = rs.getString("solo");
					String denghi = rs.getString("denghi");
					
					String exitTONG = rs.getString("exitKHOTONG");
					String exitCT = rs.getString("exitKHOCT");
					
					if(exitTONG.equals("0"))
					{
						query = "insert NHAPP_KHO(KHO_FK, NPP_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, KBH_FK)  " +
								"values('100004', '" + npp_fk + "', '" + sanpham_fk + "', '" + denghi + "', '0', '" + denghi + "', '" + kbh_fk + "')";
					}
					else
					{
						query = " Update NHAPP_KHO set SOLUONG = SOLUONG + '" + denghi + "', AVAILABLE = AVAILABLE + '" + denghi + "' " +
								" where KHO_FK = '100004' and NPP_FK = '" + npp_fk + "' and KBH_FK = '" + kbh_fk + "' and SANPHAM_FK = '" + sanpham_fk + "' ";
					}
					
					if(!db.update(query))
					{
						msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
						db.getConnection().rollback();
						return msg;
					}
					
					if(exitCT.equals("0"))
					{
						query = "insert NHAPP_KHO_CHITIET(KHO_FK, NPP_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, KBH_FK, SOLO, NGAYHETHAN)  " +
								"select '100004', '" + npp_fk + "', '" + sanpham_fk + "', '" + denghi + "', '0', '" + denghi + "', '" + kbh_fk + "', '" + solo + "', NGAYHETHAN " +
								"from NHAPP_KHO_CHITIET " +
								"where  KHO_FK = '" + rs.getString("kho_fk") + "' and NPP_FK = '" + npp_fk + "' and KBH_FK = '" + kbh_fk + "' and SANPHAM_FK = '" + sanpham_fk + "' and SOLO = '" + solo + "' ";
					}
					else
					{
						query = " Update NHAPP_KHO_CHITIET set SOLUONG = SOLUONG + '" + denghi + "', AVAILABLE = AVAILABLE + '" + denghi + "' " +
								" where KHO_FK = '100004' and NPP_FK = '" + npp_fk + "' and KBH_FK = '" + kbh_fk + "' and SANPHAM_FK = '" + sanpham_fk + "' and SOLO = '" + solo + "' ";
					}
					
					if(!db.update(query))
					{
						msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
						db.getConnection().rollback();
						return msg;
					}
					
				}
				rs.close();
			}
			
			query = "update ERP_Denghidoihang set trangthai = '1' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			//TANG KHO NGUOC LAI
			String query =  " update kho  " +
							" 	set kho.BOOKED = kho.BOOKED - doihang.denghi, " +
							" 		kho.AVAILABLE = kho.AVAILABLE + doihang.denghi " +
							" from " +
							" ( " +
							" 	select a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, b.solo, b.denghi  " +
							" 	from ERP_DENGHIDOIHANG a inner join ERP_DENGHIDOIHANG_SANPHAM b on a.pk_seq = b.denghidoihang_fk " +
							" 	where a.pk_seq = '" + lsxId + "' " +
							" ) " +
							" doihang inner join NHAPP_KHO kho on doihang.kho_fk = kho.KHO_FK and doihang.NPP_FK = kho.NPP_FK " +
							" 		and doihang.KBH_FK = kho.KBH_FK and doihang.sanpham_fk = kho.SANPHAM_FK ";
			System.out.println("2.update DDH: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = " update kho  " +
					" 	set kho.BOOKED = kho.BOOKED - doihang.denghi, " +
					" 		kho.AVAILABLE = kho.AVAILABLE + doihang.denghi " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk, b.solo, b.denghi  " +
					" 	from ERP_DENGHIDOIHANG a inner join ERP_DENGHIDOIHANG_SANPHAM b on a.pk_seq = b.denghidoihang_fk " +
					" 	where a.pk_seq = '" + lsxId + "' " +
					" ) " +
					" doihang inner join NHAPP_KHO_CHITIET kho on doihang.kho_fk = kho.KHO_FK and doihang.NPP_FK = kho.NPP_FK " +
					" 		and doihang.KBH_FK = kho.KBH_FK and doihang.sanpham_fk = kho.SANPHAM_FK and doihang.solo = kho.SOLO ";
			System.out.println("3.update DDH: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "Delete ERP_Denghidoihang_SanPham where denghidoihang_fk = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "Delete ERP_Denghidoihang where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "1.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
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
	    
	    
		IErpDenghidoihangList obj = new ErpDenghidoihangList();
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpDenghidoihang lsxBean = new ErpDenghidoihang();
	    	lsxBean.setLoaidonhang(loaidonhang);
	    	lsxBean.setUserId(userId);
	    	
	    	lsxBean.createRs();
	    	lsxBean.setKhoNhapId("100000");
	    	
	    	session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHangNew.jsp";
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
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHang.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHang.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDenghidoihangList obj)
	{
		String query = "select a.PK_SEQ, a.trangthai, a.ngaydenghi as ngaydonhang, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
						"from ERP_Denghidoihang a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		if(tungay.length() > 0)
			query += " and a.ngaydenghi >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaydenghi <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
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
