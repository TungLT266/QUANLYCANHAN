package geso.traphaco.erp.servlets.taikhoankt;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.taikhoankt.ITaikhoankt;
import geso.traphaco.erp.beans.taikhoankt.ITaikhoanktList;
import geso.traphaco.erp.beans.taikhoankt.imp.Taikhoankt;
import geso.traphaco.erp.beans.taikhoankt.imp.TaikhoanktList;


public class TaikhoanketoanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public TaikhoanketoanSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		
		ITaikhoanktList tkktList = new TaikhoanktList();
		String ctyId = (String)session.getAttribute("congtyId");
		
		tkktList.setCongTyId(ctyId);
		
		String chixem = util.antiSQLInspection(request.getParameter("chixem"));
		if(chixem == null)
			chixem = "0";
		tkktList.setChixem(chixem);
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		tkktList.setUserId(userId);
		
		String task = request.getParameter("task");
		
		if (task != null) {
			if (task.equals("xoa")) {
				String pk = request.getParameter("id");
				String msg = tkktList.Delete(pk);
				tkktList.setMsg(msg);
			}
		}
		
		tkktList.init("");
		session.setAttribute("tkktList", tkktList);
		String nextJSP = "/TraphacoHYERP/pages/Erp/TaiKhoanKt.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");

		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) 
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else 
		{
			ITaikhoanktList tkktList = new TaikhoanktList();
			String ctyId = (String)session.getAttribute("congtyId");
			tkktList.setCongTyId(ctyId);
			
			String chixem = request.getParameter("chixem");
			tkktList.setChixem(chixem);
			
			String LoaiTaiKhoanId = cutil.antiSQLInspection(request.getParameter("LoaiTaiKhoanId"));
			String SoHieuTaiKhoan = cutil.antiSQLInspection(request.getParameter("SoHieuTaiKhoan"));
			String TenTaiKhoan = cutil.antiSQLInspection(request.getParameter("TenTaiKhoan"));
//			String TtcpId = cutil.antiSQLInspection(request.getParameter("TtcpId"));
			String TrangThai = cutil.antiSQLInspection(request.getParameter("TrangThai"));
			System.out.println("TrangThai"+TrangThai);
			if (TrangThai != null)
				tkktList.setTrangThai(TrangThai);

			tkktList.setUserId(userId);

			if (LoaiTaiKhoanId != null)
				tkktList.setLoaiTaiKhoanId(LoaiTaiKhoanId);

			if (SoHieuTaiKhoan != null)
				tkktList.setSoHieuTaiKhoan(SoHieuTaiKhoan);

			if (TenTaiKhoan != null)
				tkktList.setTenTaiKhoan(TenTaiKhoan);


			String action = request.getParameter("action");
			if (action.equals("new"))
			{
				ITaikhoankt tkktBean = new Taikhoankt();
				tkktBean.CreateRs();
				session.setAttribute("tkktBean", tkktBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/TaiKhoanKtNew.jsp";
				response.sendRedirect(nextJSP);
			} 
			else 
			{
				if(action.equals("view") || action.equals("next") || action.equals("prev"))
		    	{
			    	String search = getSearchQuery(request, tkktList);
			    	
			    	tkktList.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			    	tkktList.setUserId(userId);
			    	tkktList.init(search);
			    	tkktList.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			    	session.setAttribute("tkktList", tkktList);
			    	response.sendRedirect("/TraphacoHYERP/pages/Erp/TaiKhoanKt.jsp");	
			    }
		    	else
		    	{
			    	String search = getSearchQuery(request, tkktList);
			    	tkktList.init(search);
					
			    	session.setAttribute("tkktList", tkktList);
		    		session.setAttribute("userId", userId);
			
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/TaiKhoanKt.jsp");  
		    	}
				
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, ITaikhoanktList tkktList) 
	{
		Utility util = new Utility();
		
		String sql = " SELECT TK.PK_SEQ,TK.SOHIEUTAIKHOAN,TK.TENTAIKHOAN,"+
						" CASE "+ 
						"	WHEN TK.TAIKHOANCOCHITIET=1 THEN N'Có ' "+
						"	WHEN TK.TAIKHOANCOCHITIET=0 THEN N'Không' "+
						" END TAIKHOANCOCHITIET, "+
						"LTK.TEN AS LOAITAIKHOAN,NT.TEN AS NGUOITAO,TK.NGAYTAO,NS.TEN AS NGUOISUA,TK.NGAYSUA," + 
						" CASE "+ 
						"	WHEN TK.TRANGTHAI=1 THEN N'Hoạt động'"+
						"	WHEN TK.TRANGTHAI=0 THEN N'Ngưng hoạt động'"+
						" END TRANGTHAI ,CTY.TEN AS CONGTY"+
					" FROM ERP_TAIKHOANKT  TK "+
					" INNER JOIN ERP_LOAITAIKHOAN LTK ON LTK.PK_SEQ=TK.LOAITAIKHOAN_FK "+
					" INNER JOIN NHANVIEN NT ON NT.PK_SEQ=TK.NGUOITAO "+
					" INNER JOIN NHANVIEN NS ON NS.PK_SEQ=TK.NGUOISUA "+
					" INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ=TK.CONGTY_FK " +
					" WHERE TK.CONGTY_FK = '" + tkktList.getCongTyId() + "' ";
 
			if( tkktList.getSoHieuTaiKhoan().trim().length() > 0)
				sql += " AND TK.SoHieuTaiKhoan like N'%" + tkktList.getSoHieuTaiKhoan() + "%' ";
			
			if( tkktList.getTenTaiKhoan().trim().length() > 0)
				sql += " AND dbo.ftBoDau(TK.TenTaiKhoan) LIKE N'%" +  util.replaceAEIOU(tkktList.getTenTaiKhoan()) + "%' ";
			
			if( tkktList.getLoaiTaiKhoanId().trim().length() > 0 )
				sql+=" AND  TK.LOAITAIKHOAN_FK='" + tkktList.getLoaiTaiKhoanId() + "' ";
			
			if( tkktList.getTrangThai().trim().length()>0)
				sql+=" AND  TK.TRANGTHAI='" + tkktList.getTrangThai() + "' ";
		
		return sql;
	}

	public static void main(String[] arg)
	{
		dbutils db = new dbutils();
		String query = "select pk_seq from NHAPHANPHOI where isKHACHHANG = 0 and pk_seq != 1 order by pk_seq asc ";
		ResultSet rs = db.get(query);
		try 
		{
			while( rs.next() )
			{
				String pk_seq = rs.getString("pk_seq");
				String sql = " INSERT INTO ERP_TAIKHOANKT( SOHIEUTAIKHOAN,TENTAIKHOAN,LOAITAIKHOAN_FK, TAIKHOANCOCHITIET,NGUOITAO, NGAYTAO,NGUOISUA,NgaySua,TRANGTHAI, COTTCHIPHI, DUNGCHOKHO, DUNGCHONGANHANG, DUNGCHONCC, DUNGCHOTAISAN, DUNGCHOKHACHHANG, DUNGCHONHANVIEN, dungChoDanhMucDuAn, DUNGCHODOITUONGKHAC, NPP_FK, CONGTY_FK ) "+
							" select SOHIEUTAIKHOAN,TENTAIKHOAN,LOAITAIKHOAN_FK, TAIKHOANCOCHITIET, '100002' NGUOITAO, '2016-11-18' NGAYTAO, '100002' NGUOISUA, '2016-11-18' NgaySua, TRANGTHAI, COTTCHIPHI, DUNGCHOKHO, DUNGCHONGANHANG, DUNGCHONCC, DUNGCHOTAISAN, DUNGCHOKHACHHANG, DUNGCHONHANVIEN, dungChoDanhMucDuAn, DUNGCHODOITUONGKHAC,  "+
							" 		'" + pk_seq + "' as NPP_FK, ( select congty_fk from NHAPHANPHOI where pk_seq = '" + pk_seq + "' ) CONGTY_FK "+
							" from ERP_TAIKHOANKT  "+
							" where npp_fk = '1' and sohieutaikhoan not in ( select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where npp_fk = '" + pk_seq + "' ) ";
				
				System.out.println("::: CHEN TAI KHOAN THIEU: " + sql);
				db.update(sql);
			}
			rs.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		query = " INSERT  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT(PK_SEQ, SOHIEUTAIKHOAN,TENTAIKHOAN,LOAITAIKHOAN_FK, TAIKHOANCOCHITIET,NGUOITAO, NGAYTAO,NGUOISUA,NgaySua,TRANGTHAI, COTTCHIPHI, DUNGCHOKHO, DUNGCHONGANHANG, DUNGCHONCC, DUNGCHOTAISAN, DUNGCHOKHACHHANG, DUNGCHONHANVIEN, dungChoDanhMucDuAn, DUNGCHODOITUONGKHAC, NPP_FK, CONGTY_FK ) "+
				" select PK_SEQ, SOHIEUTAIKHOAN,TENTAIKHOAN,LOAITAIKHOAN_FK, TAIKHOANCOCHITIET,NGUOITAO, NGAYTAO,NGUOISUA,NgaySua,TRANGTHAI, COTTCHIPHI, DUNGCHOKHO, DUNGCHONGANHANG, DUNGCHONCC, DUNGCHOTAISAN, DUNGCHOKHACHHANG, DUNGCHONHANVIEN, dungChoDanhMucDuAn, DUNGCHODOITUONGKHAC, NPP_FK, CONGTY_FK "+
				" from ERP_TAIKHOANKT  "+
				" where  npp_fk = '1' and PK_SEQ not in ( select PK_SEQ from  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT ) ";
		
		System.out.println("::: CHEN TAI KHOAN THIEU DMS: " + query);
		db.update(query);
		
		System.out.println("::: CHAY XONG....");
	}
}