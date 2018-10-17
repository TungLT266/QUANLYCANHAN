package geso.traphaco.distributor.servlets.duyetdonhangcs;

import geso.traphaco.distributor.beans.duyetdonhangcs.IErpDuyetdonhangcsList;
import geso.traphaco.distributor.beans.duyetdonhangcs.imp.ErpDuyetdonhangcsList;
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

public class ErpDuyetdonhangcsSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	public ErpDuyetdonhangcsSvl() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDuyetdonhangcsList obj = null;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();	    
		
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		String []chotdhid = request.getParameterValues("chotdhid");
		if(chotdhid  != null)
		{
			String dhid = Doisangchuoi(chotdhid);
			obj.setDonHangID(dhid);
		}
		if (userId.length()==0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String action = util.getAction(querystring);
		
		String lsxId = util.getId(querystring);
		obj = new ErpDuyetdonhangcsList();
		
		if(action.equals("duyet"))
		{
			//String msg = this.Chot(lsxId, userId);
			//obj.setMsg(msg);
			//chotdhid các đơn hàng lấy theo mã đơn hàng
			System.out.println("duyet toan bo don hang");
		}
		
		obj.setUserId(userId);
		obj.init("");
		
		session.setAttribute("obj", obj);
		
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDuyetDonHangCS.jsp";
		response.sendRedirect(nextJSP);
		
	}
	private String Doisangchuoi(String[] checknpp)
	{
		
		String str = "";
		if (checknpp != null)
		{
			for (int i = 0; i < checknpp.length; i++)
			{
				if (i == 0)
				{
					str = checknpp[i];
				} else
				{
					str = str + "," + checknpp[i];
				}
			}
		}
		return str;
		
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
		
		IErpDuyetdonhangcsList obj = new ErpDuyetdonhangcsList();
		
		Utility util = new Utility();
		
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		obj.setUserId(userId);
		String []chotdhid = request.getParameterValues("chotdhid");
		if(chotdhid  != null)
		{
			String dhid = Doisangchuoi(chotdhid);
			obj.setDonHangID(dhid);
			
		}
		if(action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			String search = getSearchQuery(request, obj);
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.setUserId(userId);
			obj.init(search);
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDuyetDonHangCS.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String search = getSearchQuery(request, obj);
			obj.setUserId(userId);
			obj.init(search);
			
			session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDuyetDonHangCS.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDuyetdonhangcsList obj)
	{
		Utility util = new Utility();
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		String  query = " select distinct a.TEN as nppten,b.NGAYNHAP as ngaydonhang,b.TRANGTHAI as trangthai, b.PK_SEQ as pk_seq,nv1.TEN as nguoitao, nv2.ten as nguoisua, b.NGAYTAO,b.NGAYSUA "
				+ " from  NHAPHANPHOI a, DONHANG b, nhanvien nv1,nhanvien nv2 "
				+ " where b.NGUOITAO = nv1.PK_SEQ and b.NGUOISUA = nv2.PK_SEQ and a.PK_SEQ = b.NPP_FK and b.NPP_FK in "
				+ " ( select PK_SEQ from NHAPHANPHOI where TRUCTHUOC_FK = "+nppId+") and b.TRANGTHAI = 0 ";
//				"select a.PK_SEQ, a.trangthai, a.ngaydonhang, isnull(c.ten, d.ten) as nppTEN, a.loaidonhang, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, isnull(SOTIENTHU, 0) as SOTIENTHU, a.hopdong_fk ,isnull(a.iskm,0) as isKm " +
//						"from ERP_DondathangNPP a inner join KHO b on a.kho_fk = b.pk_seq " +
//						"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq  " +
//						"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " +
//						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
//						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + nppId + "' and a.trangthai in ( '1') ";
				
		
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
		
		
		String sodh = request.getParameter("sodh");
		if(sodh == null)
			sodh = "";
		obj.setSodh(sodh);
		
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setKhTen(khId);
		
		if(tungay.length() > 0)
			query += " and b.NGAYNHAP >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and b.NGAYNHAP <= '" + denngay + "'";

		if(sodh.length()>0 ){
			query+= " and a.PK_SEQ LIKE '%"+sodh+"%'";
		}
		
		if(khId.length()>0){
			query += " and  a.pk_seq = " + khId+"";
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
