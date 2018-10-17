package geso.traphaco.erp.servlets.nhapkho.giay;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapkho;
import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapkhoList;
import geso.traphaco.erp.beans.nhapkho.giay.imp.ErpNhapkho;
import geso.traphaco.erp.beans.nhapkho.giay.imp.ErpNhapkhoList;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhapkhoLsxSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpNhapkhoLsxSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNhapkhoList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);

		String nkId = util.getId(querystring);

		obj = new ErpNhapkhoList();
		obj.setCongtyId((String)session.getAttribute("congtyId"));
		System.out.println("congtyId:  " + (String)session.getAttribute("congtyId"));
		System.out.println("congtyId:  " + (String)session.getAttribute("congtyid"));
		
		if (action.equals("delete"))
		{
			IErpNhapkho nhapkho=new ErpNhapkho(nkId);
			nhapkho.setCongtyId((String)session.getAttribute("congtyId"));
			nhapkho.setUserId(userId);
			nhapkho.HuyNhapKhoLsx();
			String msg = nhapkho.getmsg();
			if (msg.length() > 0)
				obj.setmsg(msg);
		} else
		{
			if (action.equals("chot"))
			{
				IErpNhapkho nhapkho=new ErpNhapkho(nkId);
				nhapkho.setCongtyId((String)session.getAttribute("congtyId"));
				nhapkho.setUserId(userId);
				nhapkho.init();
				String msg = nhapkho.chotNhapKhoLSX();
				if (msg.length() > 0)
					obj.setmsg(msg);
			}
		}

		obj.setUserId(userId);
		obj.init("");

		session.setAttribute("obj", obj);
		session.setAttribute("congtyId", obj.getCongtyId());

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoGiay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNhapkhoList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}

		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		if (action.equals("Tao moi"))
		{
			IErpNhapkho nkBean = new ErpNhapkho();
			nkBean.setCongtyId((String)session.getAttribute("congtyId"));
			nkBean.createRs();
		
			session.setAttribute("nkBean", nkBean);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoGiayNew.jsp";
			response.sendRedirect(nextJSP);
		} 
		else
		{
			if (action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj = new ErpNhapkhoList();
				obj.setCongtyId((String)session.getAttribute("congtyId"));
				
				String search = getSearchQuery(request, obj);

				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				obj.setUserId(userId);
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhapKhoGiay.jsp");
			} 
			else
			{
				obj = new ErpNhapkhoList();
				obj.setCongtyId((String)session.getAttribute("congtyId"));

				String search = getSearchQuery(request, obj);
				obj.init(search);
				obj.setUserId(userId);

				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhapKhoGiay.jsp");
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IErpNhapkhoList obj)
	{
		
		Utility util=new Utility();
		String query =  " select 	a.PK_SEQ as nhId, a.PREFIX + cast(a.PK_SEQ as varchar(20)) as sonhapkho, (k.PREFIX + cast(a.SOLENHSANXUAT as varchar(20)))  as SOCHUNGTU,isnull(cd.PK_SEQ,0) as CongDoanId, " +
						" 			cast( isnull(cd.PK_SEQ,0)  as varchar(20)) +'--'+ isnull(cd.DienGiai,'') as CongDoan ,"+  
						" 			a.NGAYNHAPKHO, a.NOIDUNGNHAP, b.TEN as ndnTen, a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua   "+
						" from 		ERP_NHAPKHO a inner join ERP_NOIDUNGNHAP b on a.NOIDUNGNHAP = b.PK_SEQ "+  
						" 			inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ "+   
						" 			left join erp_lenhsanxuat_giay k on a.solenhsanxuat = k.pk_seq " +
						" 			left join Erp_CongDoanSanXuat_giay cd on cd.pk_Seq=a.CongDoan_FK " +
						" where 	a.CongTy_FK='"+obj.getCongtyId()+"' " ;
		
		
		
		String LsxId = request.getParameter("LsxId");
		if (LsxId == null)
			LsxId = "";
		obj.setSoLSX(LsxId);
		
		String tungay = request.getParameter("tungay");
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = request.getParameter("denngay");
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String sonhanhang = request.getParameter("sonhanhang");
		if (sonhanhang == null)
			sonhanhang = "";
		obj.setSoPnh(sonhanhang);

		String trangthai = request.getParameter("trangthai");
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String dvkdid = request.getParameter("dvkdid");
		if (dvkdid == null)
			dvkdid = "";
		obj.setIdDvkd(dvkdid);
		
		String xuongId = request.getParameter("xuongId");
		if (xuongId == null)
			xuongId = "";
		obj.setXuongId(xuongId);
		
		if (tungay.length() > 0)
			query += " and a.NGAYNHAPKHO >= '" + tungay + "'";

		if (denngay.length() > 0)
			query += " and a.NGAYNHAPKHO <= '" + denngay + "'";

		if (sonhanhang.length() > 0)
		{
			query += " and a.PREFIX + cast(a.PK_SEQ as varchar(20)) like '%" + sonhanhang +"%' ";
		}
		if (LsxId.length() > 0)
		{
			query += " and a.PREFIX +  (k.PREFIX + cast(a.SOLENHSANXUAT as varchar(20))) like '%" + LsxId +"%' ";
		}
		
		if(dvkdid.length() > 0){
			query += " and a.PK_SEQ in (select sonhapkho_fk  from erp_nhapkho_sanpham nksp inner join erp_sanpham sp on sp.pk_seq=nksp.sanpham_fk where sp.dvkd_fk="+dvkdid+" ) ";
		}
		
		
		if (trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		if(xuongId.length()>0)
			query += " and cd.nhamay_fk ="+xuongId+""; 
				
		System.out.println(query);
		
		return query;
	}

	private String Delete(String nhId)
	{
		dbutils db = new dbutils();

		try
		{
			db.getConnection().setAutoCommit(false);

			// db.update("delete ERP_MUAHANG_SP where muahang_fk = '" + dmhId +
			// "'");
			// db.update("delete ERP_MUAHANG where pk_seq = '" + dmhId + "'");

			db.update("update ERP_Nhapkho set trangthai = '3' where pk_seq = '" + nhId + "'");

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} catch (SQLException e)
		{
			db.shutDown();
			return "Khong the xoa nhap kho";
		}
	}


}
