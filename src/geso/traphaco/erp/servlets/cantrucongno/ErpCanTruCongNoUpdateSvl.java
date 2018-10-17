package geso.traphaco.erp.servlets.cantrucongno;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.cantrucongno.IErpCanTruCongNoList;
import geso.traphaco.erp.beans.cantrucongno.imp.ErpCanTruCongNo;
import geso.traphaco.erp.beans.cantrucongno.imp.ErpCanTruCongNoList;
import geso.traphaco.erp.beans.thutien.IHoadon;
import geso.traphaco.erp.beans.thutien.imp.Hoadon;

public class ErpCanTruCongNoUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpCanTruCongNoUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();
		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		out.println(action);

		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String Id = util.getId(querystring);

		out.println(userId);
		ErpCanTruCongNo bean = null;
		bean = new ErpCanTruCongNo();
		bean.setId(Id);
		
		bean.setUserId(userId);
		bean.setcongtyId((String)session.getAttribute("congtyId"));
		bean.setnppdangnhap((String)session.getAttribute("nppId"));
		bean.setnppdangnhap(util.getIdNhapp(userId));
		
		System.out.println("cong ty:"+ (String)session.getAttribute("congtyId"));
		
		if (action.equals("display")) {
			bean = new ErpCanTruCongNo(Id);
			
			bean.init();
		}else if("delete".equals(action)){
			
		}else
		{				 
			bean.setUserId(userId);
		}
		session.setAttribute("obj", bean);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanTruCongNoNew.jsp";
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextJSP;
		
		// khai báo ServerletName
		String ServerletName = this.getServletName();
		
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		ErpCanTruCongNo bean = new ErpCanTruCongNo();
		
		String action = request.getParameter("action");		
		
		String userId  = util.antiSQLInspection(request.getParameter("userId"));
		
		bean.setnppdangnhap(util.getIdNhapp(userId));
		bean.setcongtyId((String)session.getAttribute("congtyId"));
		bean.setnppdangnhap((String)session.getAttribute("nppId"));
		System.out.println("cong ty:"+ (String)session.getAttribute("congtyId"));
		
		String Id = util.antiSQLInspection(request.getParameter("Id"));
		if(Id == null) Id = "";
		
		String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		if(nccId == null) nccId = "";
		
		String khId = util.antiSQLInspection(request.getParameter("khId"));
		if(khId == null) khId = "";
		
		String khTen = util.antiSQLInspection(request.getParameter("kh"));
		if(khTen == null) khTen = "";
		
		String isNPP = ""; 
		
		System.out.println(khTen);
		if(khTen.trim().length()>0)
		{			
			String[] k = khTen.split(" -- ");
			if (k.length > 1)
				isNPP = k[1].substring(0, k[1].length()) ;
		}
		
		String nccTen = util.antiSQLInspection(request.getParameter("ncc"));
		if(nccTen == null) nccTen = "";
		
		String ngayNhap = util.antiSQLInspection(request.getParameter("ngaynhap"));
		if(ngayNhap == null) ngayNhap = "";
		
		String soChungTu = util.antiSQLInspection(request.getParameter("sochungtu"));
		if(soChungTu == null) soChungTu = "";
		String dienGiaiCT = util.antiSQLInspection(request.getParameter("dienGiaiCT"));
		if(dienGiaiCT == null) dienGiaiCT = "";
		
		String ttId = util.antiSQLInspection(request.getParameter("tienteId"));
		if(ttId == null) ttId = "";
		
		System.out.println(khId);
		if (Id.length() > 0) 
		{
			bean.setId(Id); 
		}
		
		bean.setUserId(userId);
		bean.setNccId(nccId);
		bean.setKhId(khId);
		bean.setKhTen(khTen);
		bean.setNccTen(nccTen);
		bean.setNgayTao(ngayNhap);
		bean.setDienGiaiCT(dienGiaiCT);
		bean.setSoChungTu(soChungTu);
		bean.setTienteId(ttId);
		bean.setisNPP(isNPP);
		
		//System.out.println("NCC ID: "+nccId);
		
		System.out.println("action action: "+action);
		if("update".equals(action))
		{
			session.setAttribute("obj", bean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanTruCongNoNew.jsp";
			
			response.sendRedirect(nextJSP);
		}else if("save".equals(action.trim()))
		{
			System.out.println("thong tin action : "+action);
			//getList Hoa don KH
			
			String[] idHd = request.getParameterValues("idHoaDonKH");
			String[] kyhieuhd = idHd;
			String[] loaiHd = request.getParameterValues("loaiHoaDonKH");			
			String[] sohd = request.getParameterValues("soHoaDonKH");
			String[] ngayhd = request.getParameterValues("ngayHoaDonKH");
//			String[] sotiengoc = request.getParameterValues("sotienKH");
			String[] avat = request.getParameterValues("phaiXoaNoKH");
			String[] thanhtoan = request.getParameterValues("xoaNoKH");
			String[] conlai = request.getParameterValues("conlaiKH");			
			String sotienthanhtoan = request.getParameter("tongCanTruKH").replace(",", "");
						
			bean.setSoTienThanhToan(sotienthanhtoan);
			bean.setUserId(userId);
			bean.setcongtyId((String)session.getAttribute("congtyId"));
						
			List<IHoadon> hdKHlist =  new ArrayList<IHoadon>();
			if(thanhtoan != null)
			{		
				IHoadon hoadon = null;
				int m = 0;
				while(m < thanhtoan.length)
				{
					thanhtoan[m] = thanhtoan[m].replaceAll(",", "");
					avat[m] = avat[m].replaceAll(",", "");
					conlai[m] = conlai[m].replaceAll(",", "");

					//Hoadon(String id, String kyhieu, String so, String ngay, String avat, String sotiennt, String thanhtoan, String ttId,String tygia)
					if (Float.parseFloat(thanhtoan[m])>0) {
						if(ttId.equals("100000")){
							hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m], "0", thanhtoan[m], ttId,"");
							hoadon.setLoaihd(loaiHd[m]);
						}else{
							hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m],  avat[m], thanhtoan[m], ttId, "");
							hoadon.setLoaihd(loaiHd[m]);
						}
						hdKHlist.add(hoadon);
					}
					m++;
				}	
			}
			bean.setListHoaDonKhachHang(hdKHlist);	
			
			// getList Hoa don NCC
			
			String[] loaihd = request.getParameterValues("loaiHoaDonNcc");
			String[] tigiaHd = request.getParameterValues("tigiaHoaDonNcc");
			idHd = request.getParameterValues("idHoaDonNcc");
			kyhieuhd = idHd;
			sohd = request.getParameterValues("soHoaDonNcc");
			ngayhd = request.getParameterValues("ngayHoaDonNcc");
//			soTiengoc = request.getParameterValues("soTienNcc");
			
			avat = request.getParameterValues("phaiXoaNoNcc");
			thanhtoan = request.getParameterValues("xoaNoNcc");
			conlai = request.getParameterValues("conLaiNcc");
			
			List<geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon> hdList =  new ArrayList<geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon>();
			
			System.out.println("thanhtoan nek 23232 : " + thanhtoan.length);
			if(thanhtoan != null)
			{		
				geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon hoadon = null;
				int m = 0;
				while(m < thanhtoan.length)
				{
					thanhtoan[m] = thanhtoan[m].replaceAll(",", "");
					avat[m] = avat[m].replaceAll(",", "");
					conlai[m] = conlai[m].replaceAll(",", "");
					tigiaHd[m] = tigiaHd[m].replaceAll(",", "");
					
					if (!"0".equals(thanhtoan[m])) {						
						//Hoadon(String id, String kyhieu, String so, String ngay, String avat, String sotienno, String thanhtoan)
						hoadon = new geso.traphaco.erp.beans.thanhtoanhoadon.imp.Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], "0",avat[m], thanhtoan[m]);
						hoadon.setLoaihd1(loaihd[m]);
						hoadon.setTigia(tigiaHd[m]);
						hdList.add(hoadon);
					}
					m++;
				}	
			}
			bean.setListHoaDonNCC(hdList);
			bean.setNgayTao(ngayNhap);
			bean.setSoChungTu(soChungTu);
			

			if(Float.parseFloat(sotienthanhtoan)<=0){
				bean.init();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanTruCongNoNew.jsp";	
				System.out.println("Số tiền cần trừ chưa xác định ");
				bean.setMsg("Số tiền cần trừ chưa xác định");
				response.sendRedirect(nextJSP);
				return;
			}
			
			String SoTienConDuCanTru = request.getParameter("SoTienConDuCanTru").replace(",", "");
			if(Float.parseFloat(SoTienConDuCanTru)!=0){
				bean.init();
				bean.setMsg("Số tiền cần trừ phải bằng nhau,vui lòng điều chỉnh lại");
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanTruCongNoNew.jsp";				
				response.sendRedirect(nextJSP);
				return;
			}
			
			if(bean.getId().trim().length() > 0){
				bean.update();
			}else{
				bean.save();
			}
			IErpCanTruCongNoList canTruCongNoList = new ErpCanTruCongNoList();
			canTruCongNoList.setCongtyId((String)session.getAttribute("congtyId"));
			canTruCongNoList.setnppdangnhap((String)session.getAttribute("nppId"));
			canTruCongNoList.setUserId(userId);
			
			//Khởi tạo bean cấn trừ công nợ List mới
			
			String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(canTruCongNoList, searchQuery);
			
			canTruCongNoList.init();
			session.setAttribute("obj", canTruCongNoList);
			
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanTruCongNo.jsp";
			response.sendRedirect(nextJSP);
		}
	}
}