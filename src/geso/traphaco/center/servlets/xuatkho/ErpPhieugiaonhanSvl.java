package geso.traphaco.center.servlets.xuatkho;

import geso.traphaco.center.beans.xuatkho.IErpPhieugiaonhan;
import geso.traphaco.center.beans.xuatkho.IErpPhieugiaonhanList;
import geso.traphaco.center.beans.xuatkho.imp.ErpPhieugiaonhan;
import geso.traphaco.center.beans.xuatkho.imp.ErpPhieugiaonhanList;
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

public class ErpPhieugiaonhanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpPhieugiaonhanSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpPhieugiaonhanList obj;
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
	    obj = new ErpPhieugiaonhanList();
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    //obj.setNppId(nppId);
	    System.out.println("---NPP ID: " + nppId);
	    
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
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpPhieuGiaoNhan.jsp";
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
			
			query = "delete ERP_PHIEUGIAONHAN_DDH where pgn_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_PHIEUGIAONHAN_DDH " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_PHIEUGIAONHAN_SANPHAM where pgn_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_PHIEUGIAONHAN_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_PHIEUGIAONHAN_SANPHAM_CHITIET where pgn_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_PHIEUGIAONHAN_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_PHIEUGIAONHAN  where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_PHIEUGIAONHAN " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "UPDATE ERP_HOADON set hoantatPGN = 0 where pk_seq in ( select hoadon_fk from ERP_PHIEUGIAONHAN_DDH where pgn_fk = '" + lsxId + "' ) ";
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
	
	private String Chot(String lsxId, String userId, String nppId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			//GOP CHUNG BUOC YC VA XUAT THANH 1
			query = "update ERP_PHIEUGIAONHAN set trangthai = '2'  where pk_seq = '" + lsxId + "' and trangthai != 2 ";
			System.out.println("---CAP NHAT TRANG THAI: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Không thể cập nhật ERP_PHIEUGIAONHAN " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//TU DONG HOAN TAT CAC HOA DON TU CU TOI MOI
			query = "select hoadon_fk, ( select xuatcho from ERP_PHIEUGIAONHAN where pk_seq = a.pgn_fk ) as xuatcho " +
					"from ERP_PHIEUGIAONHAN_DDH a where pgn_fk = '" + lsxId + "' order by hoadon_fk asc";
			System.out.println("---CAP NHAT TRANG THAI HOA DON: " + query);
			
			ResultSet rsDDH = db.get(query);
			String ddhID = "";
			if(rsDDH != null)
			{
				while(rsDDH.next())
				{
					ddhID = rsDDH.getString("hoadon_fk") + ",";
					
					query = "  select COUNT(*) as soDONG,   " +
							" 		(   select count(distinct sanpham_fk) as soSP      " +
							"   			from     " +
							"   			(     " +
							"   					select a.sanpham_fk " +
							"   					from ERP_HOADON_SP a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ     " +
							"   					where a.hoadon_fk in (    " + ( ddhID.substring(0, ddhID.length() - 1) ) + "   )    " +
							"   			)     " +
							"   			dathang  )	 as soSP  " +
							"  from  " +
							"  (  " +
							"  	select sanpham_fk, sum(soluongXUAT) as soluongXUAT  " +
							"  	from ERP_PHIEUGIAONHAN_SANPHAM  " +
							" 	where pgn_fk in ( select pgn_fk from ERP_PHIEUGIAONHAN_DDH where hoadon_fk in ( " + ( ddhID.substring(0, ddhID.length() - 1) ) + " ) ) " +
							"  	group by sanpham_fk  " +
							"  )   " +
							"  XUAT inner join   " +
							"  (  " +
							"   	select dathang.sanpham_fk, SUM(dathang.soluong) as soluongDAT      " +
							"   	from     " +
							"   	(     " +
							"   			select a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
							"   					isnull(a.soluong_chuan, a.soluong) as soluong, 0 as loai, ' ' as scheme    " +
							"   			from ERP_HOADON_SP a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ     " +
							"   			where a.hoadon_fk in ( " + ( ddhID.substring(0, ddhID.length() - 1) ) + " )    " +
							"   	)     " +
							"   	dathang   " +
							"   	group by dathang.sanpham_fk  " +
							"  )  " +
							"  DDH on XUAT.sanpham_fk = DDH.sanpham_fk  " +
							"  where XUAT.soluongXUAT >= DDH.soluongDAT ";
					
					System.out.println("CHECK HOAN TAT: " + query);
					ResultSet rsCHECK = db.get(query);
					if(rsCHECK.next())
					{
						String hoantatPGN = "0";
						if(rsCHECK.getInt("soDONG") == rsCHECK.getInt("soSP"))
							hoantatPGN = "1";  //HOAN TAT
						
						query = " UPDATE ERP_HOADON set hoantatPGN = '" + hoantatPGN + "' where pk_seq in ( " + ( ddhID.substring(0, ddhID.length() - 1) ) + " ) ";
						if(!db.update(query))
						{
							msg = "Không thể chốt ERP_PHIEUGIAONHAN " + query;
							db.getConnection().rollback();
							return msg;
						}
					}
				}
				rsDDH.close();
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
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
	    
		IErpPhieugiaonhanList obj = new ErpPhieugiaonhanList();
		
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
		obj.setLoaidonhang(loaidonhang);

	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    obj.setUserId(userId);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    String khId = request.getParameter("khId");
	    if(khId == null)
	    	khId = "";
	    obj.setNppTen(khId);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpPhieugiaonhan lsxBean = new ErpPhieugiaonhan();
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
	    	
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpPhieuGiaoNhanNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Center/ErpPhieuGiaoNhan.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpPhieuGiaoNhan.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpPhieugiaonhanList obj)
	{
		String query = "select a.PK_SEQ, a.trangthai, a.ngayyeucau, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, " +
						"	 (	Select cast(YCXK1.DDH_FK as varchar(10)) + ',' AS [text()]  " +
						"		From ERP_PHIEUGIAONHAN_DDH YCXK1   " +
						"		Where YCXK1.pgn_fk = a.pk_seq  " +
						"		For XML PATH ('') )  as ddhIds    " +
						"from ERP_PHIEUGIAONHAN a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq " +
						"	inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq " +
						"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
						" where 1 = 1 "; 
		
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
		
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setNppTen(khId);
		
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
			query += " and a.npp_fk = '" + khId + "'";
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
