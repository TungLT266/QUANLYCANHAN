package geso.traphaco.erp.servlets.duyetnhacungcap;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.duyetnhacungcap.IErpDuyetNhaCungCap;
import geso.traphaco.erp.beans.duyetnhacungcap.IErpDuyetNhaCungCapList;
import geso.traphaco.erp.beans.duyetnhacungcap.imp.ErpDuyetNhaCungCap;
import geso.traphaco.erp.beans.duyetnhacungcap.imp.ErpDuyetNhaCungCapList;
import geso.traphaco.erp.beans.hanmucnhapkhau.IErpHanMucNhapKhau;
import geso.traphaco.erp.beans.hanmucnhapkhau.IErpHanMucNhapKhauList;
import geso.traphaco.erp.beans.hanmucnhapkhau.imp.ErpHanMucNhapKhau;
import geso.traphaco.erp.beans.hanmucnhapkhau.imp.ErpHanMucNhapKhauList;
import geso.traphaco.erp.beans.loainhacungcap.IErpLoaiNhaCungCap;
import geso.traphaco.erp.beans.loainhacungcap.imp.ErpLoaiNhaCungCap;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ErpHanMucNhapKhauUpdateSvl")
public class ErpDuyetNhaCungCapUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public ErpDuyetNhaCungCapUpdateSvl()
	{
		super();
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
		Utility util;
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
			String nextJSP = "";
			util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			
			IErpDuyetNhaCungCap lnccBean = new ErpDuyetNhaCungCap();
			
			lnccBean.setUserId(userId);
			if (request.getQueryString().indexOf("update") >= 0)
			{
				lnccBean.setId(id);
				lnccBean.Create_nhacungcap();
				lnccBean.Create_Sanpham();
				lnccBean.Init();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetNhaCungCapNew.jsp";
			}
			session.setAttribute("obj", lnccBean);
			response.sendRedirect(nextJSP);
		}
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
		Utility util = (Utility) session.getAttribute("util");
		if (!util.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			String nextJSP = "";
			IErpDuyetNhaCungCap lnccBean = new ErpDuyetNhaCungCap();
			String Id = util.antiSQLInspection(request.getParameter("id"));
			
			String listnhacungcap = util.antiSQLInspection(request.getParameter("listnhacungcap"));
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			
			String action = request.getParameter("action");
			if(Id==null){Id="";}
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			lnccBean.setId(Id);
			System.out.println("Id: " + Id);
			
			String[] listsanpham = request.getParameterValues("listsanpham");
			// chỗ này được get nhiều mua hàng_fk
		   String muahang = "";
		   if (listsanpham != null )
			{
				for(int i = 0; i < listsanpham.length; i++)
					muahang += listsanpham[i] + ",";
				
				if(muahang.trim().length() > 0)
				{
					muahang = muahang.substring(0, muahang.length() - 1);
				}
			}
			lnccBean.setSanPham_Fk(muahang);
			
			lnccBean.setNhacungcap_Fk(listnhacungcap);
			lnccBean.setTuNgay(tungay);
			lnccBean.setUserId(userId);
			lnccBean.setDenNgay(denngay);
			 if(action.equals("save"))
			    {		
					IErpDuyetNhaCungCapList listduyetncc= new ErpDuyetNhaCungCapList();
			    	if(Id.length() ==0)	// save cua tao moi
			    	{
			    		if(!lnccBean.Save())	// khong thuc hien duoc ham save
			    		{
			    			System.out.println("khong save");
			    			lnccBean.Init();
			    			lnccBean.Create_Sanpham();
			    			lnccBean.Create_nhacungcap();
			    			session.setAttribute("obj", lnccBean);
			    			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDuyetNhaCungCapNew.jsp");
			    		}
			    		else	// save thanh cong
			    		{
			    			System.out.println("dang o save moi");
			    			listduyetncc.Init("");
			    			listduyetncc.Create_Sanpham();
			    			listduyetncc.Create_Nhacungcap();
			    			session.setAttribute("obj", listduyetncc);
			    			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDuyetNhaCungCapList.jsp");
			    		}
			    	}
			    	else 
			    	{
			    		if(!lnccBean.edit())
		    			{
		    				// luu o thanh cong
			    			lnccBean.Init();
			    			lnccBean.Create_Sanpham();
			    			lnccBean.Create_nhacungcap();
			    			session.setAttribute("obj", lnccBean);
			    			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDuyetNhaCungCapNew.jsp");
		    			}
		    			else
		    			{
		    				System.out.println("da sua xong");
		    				listduyetncc.Init("");
		    				listduyetncc.Create_Sanpham();
		    				listduyetncc.Create_Nhacungcap();
			    			session.setAttribute("obj", listduyetncc);
			    			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDuyetNhaCungCapList.jsp");
		    			}
			    	}
			    	
			    }
		}
	}
}
