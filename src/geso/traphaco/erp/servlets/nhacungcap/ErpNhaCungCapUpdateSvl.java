package geso.traphaco.erp.servlets.nhacungcap;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhacungcap.IErpNhaCungCap;
import geso.traphaco.erp.beans.nhacungcap.imp.ErpNhaCungCap;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ErpNhaCungCapUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpNhaCungCapUpdateSvl()
	{
		super();
	}
	
	 public  void VeTrangTong(HttpSession session, HttpServletResponse response,String userId,String ctyId) throws IOException
	{
//	    	Utility util  = new Utility();
    	IErpNhaCungCap obj = null;
    	boolean isExit = false;
		if(session.getAttribute("backAttribute")!= null)
		{
			try {
				if (session.getAttribute("backAttribute").getClass().equals(ErpNhaCungCap.class))
				{
					obj = (IErpNhaCungCap)session.getAttribute("backAttribute");
					obj.NewDbUtil();
	
					session.removeAttribute("backAttribute");
					session.setAttribute("backAttribute", null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (isExit == false)
		{
		   obj = new ErpNhaCungCap();
		}
		obj.setUserId(userId);
		obj.setCongTy(ctyId);
		obj.search();
		obj.createaRs();
		session.setAttribute("nccList", obj);
		session.setAttribute("userId", userId);
		session.setAttribute("congtyId", ctyId);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaCungCap.jsp";
		response.sendRedirect(nextJSP);
	 }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		String ctyId = (String)session.getAttribute("congtyId");
		
		System.out.println("congtyId:"+ctyId);
		
		Utility cutil = new Utility();
		
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} 
		else
		{
			session.setMaxInactiveInterval(30000);
			String nextJSP = "";
			
			String querystring = request.getQueryString();
			
			userId = cutil.getUserId(querystring);
			
			if (userId.length() == 0)
				userId = cutil.antiSQLInspection(request.getParameter("userId"));
			
			String id = cutil.getId(querystring);
			System.out.println("ID la" + id);
			
			IErpNhaCungCap nccBean = new ErpNhaCungCap(id);
			nccBean.setCongTy(ctyId);
			
			nccBean.setUserId(userId);
			
			if (request.getQueryString().indexOf("display") >= 0)
			{
				nccBean.init();
				nccBean.createaRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaCungCapDisplay.jsp";
			} 
			else if (request.getQueryString().indexOf("update") >= 0)
			{
				nccBean.init();
				nccBean.createaRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaCungCapUpdate.jsp";
			}
			System.out.println("Ban dang o trang" + nextJSP);
			session.setAttribute("nccBean", nccBean);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do Post ErpNhaCungCapUpdateSvl!");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		
		Utility util = (Utility) session.getAttribute("util");
		String ctyId = (String)session.getAttribute("congtyId");
		
		System.out.println("congtyId:"+ctyId);
		String action = request.getParameter("action");
		if (action == null)
			action = "";
		if (!util.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} 
		else
		{
			
			if(action.equals("back"))
			{
				VeTrangTong(session, response, userId, ctyId);
				return;
			}
			
			String nextJSP = "";
			IErpNhaCungCap nccBean;
			
			String Id = util.antiSQLInspection(request.getParameter("Id"));			
			String ma = util.antiSQLInspection(request.getParameter("ma"));
			String ten = util.antiSQLInspection(request.getParameter("ten"));
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			String[] loai_ncc = request.getParameterValues("loai_ncc");
			String taikhoan = util.antiSQLInspection(request.getParameter("taikhoan"));
			String diachi_ncc = util.antiSQLInspection(request.getParameter("diachi_ncc"));
			String mst = util.antiSQLInspection(request.getParameter("mst"));
			String sotaikhoan = util.antiSQLInspection(request.getParameter("sotaikhoan"));
			String nhId = util.antiSQLInspection(request.getParameter("NhId"));
			String nhTen = util.antiSQLInspection(request.getParameter("TenNh"));
			String cnId = util.antiSQLInspection(request.getParameter("CNId"));
			String cnTen = util.antiSQLInspection(request.getParameter("TenCN"));
			String thoihanno = util.antiSQLInspection(request.getParameter("thoihanno"));
			String hanmucno = util.antiSQLInspection(request.getParameter("hanmucno"));
			String dienthoai_ncc = util.antiSQLInspection(request.getParameter("dienthoai_ncc"));
			String nguoilienhe = util.antiSQLInspection(request.getParameter("nguoilienhe"));
			String email_nlh = util.antiSQLInspection(request.getParameter("email_nlh"));
			String dienthoai_nlh = util.antiSQLInspection(request.getParameter("dienthoai_nlh"));
			String nguoimuahang = util.antiSQLInspection(request.getParameter("nguoimuahang"));
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			String qlcongno = util.antiSQLInspection(request.getParameter("qlcongno"));
			String fax = util.antiSQLInspection(request.getParameter("fax_num"));
			String muctindung = util.antiSQLInspection(request.getParameter("muctindung"));
			String is_khuchexuat = util.antiSQLInspection(request.getParameter("is_khuchexuat"));
			String tkkqId = util.antiSQLInspection(request.getParameter("tkkqId"));
		
			nccBean =  new ErpNhaCungCap();
			nccBean.setCongTy(ctyId);
			
			if (Id == null) action = "";
			nccBean.setId(Id);
			if(muctindung == null)
				muctindung = "";
			else
				muctindung = muctindung.replace(",","");
			nccBean.setMucTinDung(muctindung);
			
			if(tkkqId == "" )
				tkkqId = null;
			
			nccBean.setTkkqId(tkkqId);	
			
			if (diachi_ncc != null)
				nccBean.setDiaChi_NCC(diachi_ncc);
			
			if (dienthoai_ncc != null)
				nccBean.setDienThoai_NCC(dienthoai_ncc);
			
			if (dienthoai_nlh != null)
				nccBean.setDienThoai_NLH(dienthoai_nlh);
			
			if (email_nlh != null)
				nccBean.setEmail_NLH(email_nlh);
			
			if (hanmucno != null)
				nccBean.setHanMucNo(hanmucno);

			if (thoihanno != null)
				nccBean.setThoiHanNo(thoihanno);

			if (loai_ncc != null && loai_ncc.length > 0){
				String tmp = "";
				for(int i = 0; i < loai_ncc.length; i++ ){
					tmp += loai_ncc[i] + ", ";
					
				}
				tmp = tmp.substring(0, tmp.length() - 2);
				
				nccBean.setLoaiNCC(tmp);
			}else
				nccBean.setLoaiNCC("NULL");

			if (mst != null)
				nccBean.setMST(mst);
			
			if (fax != null)
				nccBean.setFax(fax);

			if (ma != null)
				nccBean.setMa(ma);

			if (ten != null)
				nccBean.setTen(ten);

			if (nppId != null)
				nccBean.setNppId(nppId);
			
			if (nguoilienhe != null)
				nccBean.setNguoiLienHe(nguoilienhe);

			if (nguoimuahang != null)
				nccBean.setNguoiMuaHang(nguoimuahang);

			if (nhId != null && nhId.trim().length() > 0)
				nccBean.setNganHang(nhId);
			else
				nccBean.setNganHang("NULL");
			if (nhTen != null && nhTen.trim().length() > 0)
				nccBean.setNhTen(nhTen);
			
			else
				nccBean.setNhTen("NULL");
			
			if (cnId != null && cnId.trim().length() > 0)
				nccBean.setChiNhanh(cnId);
			else
				nccBean.setChiNhanh("NULL");	
			if (cnTen != null && cnTen.trim().length() > 0)
				nccBean.setTenCN(cnTen);
			else
				nccBean.setTenCN("NULL");

			if (taikhoan != null && taikhoan.trim().length() > 0)
				nccBean.setTaiKhoan(taikhoan);
			else
				nccBean.setTaiKhoan("NULL");
			
			if (sotaikhoan != null)
				nccBean.setSoTaiKhoan(sotaikhoan);
			
			if (trangthai != null)
				nccBean.setTrangThai("1");
			else
				nccBean.setTrangThai("0");
			
			if(qlcongno != null)
				nccBean.setQuanlycongno("1");
			else
				nccBean.setQuanlycongno("0");		
			
			
			if(is_khuchexuat==null){
				is_khuchexuat="0";
			}
			nccBean.setIs_khuchexuat(is_khuchexuat);
			
			nccBean.setUserId(userId);
			
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			
			String[] spNgcIds = request.getParameterValues("spNgcIds");
			if(spNgcIds != null)
			{
				String str = "";
				for(int i = 0; i < spNgcIds.length; i++)
				{
					str += spNgcIds[i] + ",";
				}
				if(str.trim().length() > 0)
				{
					str = str.substring(0, str.length() - 1);
					nccBean.setSpNhangiacongIds(str);
				}
			}
			
			String khoNLId = util.antiSQLInspection(request.getParameter("khoNLId"));
			if(khoNLId == null)
				khoNLId = "";
			nccBean.setKhoNlId(khoNLId);
			
			String loaigiamua = util.antiSQLInspection(request.getParameter("loaigiamua"));
			if(loaigiamua == null)
				loaigiamua = "";
			nccBean.setLoaigiamua(loaigiamua);
			
			
			if (action.equals("Create"))
			{
				System.out.println("Tao moi");
				System.out.println("Ten chi nhanh "+nccBean);
				if (nccBean.CreateNcc())
				{
					VeTrangTong ( session,  response, userId, ctyId);
					return;
				} else
				{
					System.out.println("Khong tao  duoc");
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaCungCapNew.jsp";
				}
			} 
			else if (action.equals("Update"))
			{
				
				if (!nccBean.UpdateNcc())
				{
					System.out.println("Khong Update  duoc");
					session.setAttribute("nccBean", nccBean);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaCungCapUpdate.jsp";
				} else
				{
					VeTrangTong ( session,  response, userId, ctyId);
					return;
				}
			} 
			else if (action.equals("Duyet"))
			{
				if (!nccBean.DuyetNcc())
				{
					session.setAttribute("nccBean", nccBean);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaCungCapUpdate.jsp";
				} else
				{
					VeTrangTong ( session,  response, userId, ctyId);
					return;
				}
			}
			else if (action.equals("BoDuyet"))
			{
				if (!nccBean.BoDuyetNcc())
				{
					session.setAttribute("nccBean", nccBean);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaCungCapUpdate.jsp";
				} else
				{
					VeTrangTong ( session,  response, userId, ctyId);
					return;
				}
			}
			else if (nccBean.getId().trim().length() == 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaCungCapNew.jsp";
			}
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaCungCapUpdate.jsp";
			}
			
			nccBean.createaRs();
			System.out.print("nextJSP " + nextJSP);
			session.setAttribute("nccBean", nccBean);
			response.sendRedirect(nextJSP);
		}
	}
}