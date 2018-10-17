package geso.traphaco.erp.servlets.kiemdinhchatluong.giay;

import geso.dms.center.util.Utility;
import geso.traphaco.center.beans.thongtinsanpham.ITieuchikiemdinh;
import geso.traphaco.center.beans.thongtinsanpham.imp.Tieuchikiemdinh;
 
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.IErpKiemdinhchatluong;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.imp.ErpKiemdinhchatluong;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpLsxKiemDinhSvl")
public class ErpLsxKiemDinhSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpLsxKiemDinhSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		NumberFormat formatter = new DecimalFormat("#######.###");  
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpKiemdinhchatluong dcsxBean;

		Utility util = new Utility();
		String id = util.antiSQLInspection(request.getParameter("kiemdinhId"));
		String congdoanId = util.antiSQLInspection(request.getParameter("congdoanId"));
		String lenhsanxuatId = util.antiSQLInspection(request.getParameter("lenhsanxuatId"));
		String spId = util.antiSQLInspection(request.getParameter("spId"));
		//String ngaysanxuat = util.antiSQLInspection(request.getParameter("ngaysanxuat"));
		String ngaynhanhang = util.antiSQLInspection(request.getParameter("ngaynhanhang"));
		String dinhluong = util.antiSQLInspection(request.getParameter("dinhluong"));
		String dinhtinh = util.antiSQLInspection(request.getParameter("dinhtinh"));
		String datcl = util.antiSQLInspection(request.getParameter("datcl"));
		String soluongkiemdinh = util.antiSQLInspection(request.getParameter("soluongkiemdinh"));
		String soluongdat = util.antiSQLInspection(request.getParameter("soluongdat"));
		String kyhieukd = util.antiSQLInspection(request.getParameter("kyhieukd"));
	 
		String iscapdong = util.antiSQLInspection(request.getParameter("iscapdong"));
		
		if(iscapdong==null){
			iscapdong="0";
		}
		
		double soluongmau_ct=0;
		try{
			soluongmau_ct =Double.parseDouble(request.getParameter("soluongmau").replaceAll(",",""));
		}catch (Exception e) {
			 
		}
		
		double soluongkodat_=0;
		try{
			soluongkodat_ =Double.parseDouble(request.getParameter("soluongkodat").replaceAll(",",""));
		}catch (Exception e) {
			 
		}
		
		
		
		
		String solo = util.antiSQLInspection(request.getParameter("solo"));
		
		String maphieu = util.antiSQLInspection(request.getParameter("maphieu"));
		
		String spTen = util.antiSQLInspection(request.getParameter("spTen"));
		String ngaykiem = util.antiSQLInspection(request.getParameter("ngaykiem"));
		String nhapkhoId = util.antiSQLInspection(request.getParameter("nhapkhoId"));
		
		String khonhanid = util.antiSQLInspection(request.getParameter("khonhanid"));
		
		String khunhanid = util.antiSQLInspection(request.getParameter("khuvuckhoid"));
		String dvdlid = util.antiSQLInspection(request.getParameter("Dvdlid"));
		
		
		
		String khonhanhangdatid = util.antiSQLInspection(request.getParameter("khonhanhangdatid"));
		
		
		String tinhtrang=util.antiSQLInspection(request.getParameter("tinhtrang"));
		String denghixuly = util.antiSQLInspection(request.getParameter("denghixuly"));
		if (id == null)
		{
			dcsxBean = new ErpKiemdinhchatluong();
		} else
		{
			dcsxBean = new ErpKiemdinhchatluong(id);
		}
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		dcsxBean.setUserId(userId);
		String congtyId = ((String) session.getAttribute("congtyId"));
		dcsxBean.setCongtyId(congtyId);
		dcsxBean.setLenhsanxuat(lenhsanxuatId);
		dcsxBean.setNhapkhoId(nhapkhoId);
		dcsxBean.setCongdoanId(congdoanId);
		dcsxBean.setsoluongDat(soluongdat);
		dcsxBean.setSoLuongKiemDinh(soluongkiemdinh);
		dcsxBean.setsoluongmau(formatter.format(soluongmau_ct));
		dcsxBean.setsoluongkhongdat(formatter.format(soluongkodat_));
		dcsxBean.setNgayNhanHang(ngaynhanhang);
		dcsxBean.setDvdlId(dvdlid);		
		dcsxBean.setSolo(solo);
		dcsxBean.setDatCl(datcl);
		dcsxBean.setDinhluong(dinhluong);
		dcsxBean.setDinhtinh(dinhtinh);
		dcsxBean.setSpId(spId);
		//dcsxBean.setNgaySanXuat(ngaysanxuat);
		dcsxBean.setSpTen(spTen);
		dcsxBean.setNgayKiem(ngaykiem);
		dcsxBean.setTrangThai(tinhtrang);
		dcsxBean.setDeNghiXuLy(denghixuly);
		dcsxBean.setKhoNhanId(khonhanid);
		dcsxBean.setKhuvuckhoid(khunhanid);
		dcsxBean.setIsCapDong(iscapdong);
		dcsxBean.setKyhieukd(kyhieukd);
		dcsxBean.setKhonhanhangdatId(khonhanhangdatid);
		
		
		dcsxBean.setMaphieu(maphieu);
		

		String[] tieuchi = request.getParameterValues("tieuchi");
		String[] toantu = request.getParameterValues("toantu");
		String[] giatrichuan = request.getParameterValues("giatrichuan");
		String[] diemdat = request.getParameterValues("diemdat");
		String[] trangthai = request.getParameterValues("trangthai");
		String[] dat = request.getParameterValues("dat");
		String[] nguoisua = request.getParameterValues("nguoisua");

		String[] tieuchi_dinhtinh = request.getParameterValues("tieuchi_dinhtinh");
		String[] ketqua_dinhtinh = request.getParameterValues("ketqua_dinhtinh");
		String[] ghinhan_dinhdinh = request.getParameterValues("ghinhan_dinhtinh");
		String[] nguoisua_dinhtinh = request.getParameterValues("nguoisua_dinhtinh");
		
		
		if(tieuchi_dinhtinh!=null)
		{
			ketqua_dinhtinh=new String [tieuchi_dinhtinh.length];
			for (int i = 0; i < tieuchi_dinhtinh.length; i++)
			{
				String kq= request.getParameter("ketqua_dinhtinh_"+i);
				if(kq==null)
					ketqua_dinhtinh[i]="0";
				else 
					ketqua_dinhtinh[i]="1";
			}
			dcsxBean.setTieuchi_dinhtinh(tieuchi_dinhtinh);
			dcsxBean.setGhinhan_dinhtinh(ghinhan_dinhdinh);
			dcsxBean.setNguoiSua_dinhtinh(nguoisua_dinhtinh);
			dcsxBean.setKetqua_dinhtinh(ketqua_dinhtinh);
			
		}
		
		List<ITieuchikiemdinh> tckdList = new ArrayList<ITieuchikiemdinh>();
		if (tieuchi != null)
		{

			for (int i = 0; i < tieuchi.length; i++)
			{
				if (tieuchi[i] != "")
				{
					ITieuchikiemdinh tckd = new Tieuchikiemdinh();
					tckd.setTieuchi(tieuchi[i]);
					tckd.setToantu(toantu[i]);
					tckd.setGiatrichuan(giatrichuan[i]);
					tckd.setDiemdat(diemdat[i]);
					tckd.setTrangthai(trangthai[i]);
					tckd.setDat(dat[i]);
					String quyetdinh = request.getParameter("quyetdinh_" + i);
					if (quyetdinh == null)
						tckd.setQuyetdinh("0");
					else
						tckd.setQuyetdinh("1");
					
					tckd.setNguoiSua(nguoisua[i]);
					tckdList.add(tckd);
				}
			}

			dcsxBean.setTieuchikiemdinhList(tckdList);
		}

		String action = request.getParameter("action");
		System.out.println("Vào đây nè");
		
		if (action.equals("save"))
		{
			if (!(dcsxBean.updateKiemdinh( request)))
			{
				dcsxBean.createRs();
				session.setAttribute("dcsxBean", dcsxBean);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongGiayUpdate.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				dcsxBean.setMsg("Cập nhật kiểm định chất lượng thành công");
				dcsxBean.createRs();
				session.setAttribute("dcsxBean", dcsxBean);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongGiayUpdate.jsp";
				response.sendRedirect(nextJSP);
			}
		} else if (action.equals("change"))
		{
			if (id != null && id.trim().length() > 0)
				dcsxBean.init();
			else
				dcsxBean = new ErpKiemdinhchatluong(lenhsanxuatId, congdoanId);
			session.setAttribute("userId", userId);
			session.setAttribute("dcsxBean", dcsxBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongGiayUpdate.jsp";
			response.sendRedirect(nextJSP);
		} else if (action.equals("duyet"))
		{
			if (!dcsxBean.updateKiemdinh( request) || !(dcsxBean.duyetKiemDinh()))
			{
				dcsxBean.createRs();
				session.setAttribute("dcsxBean", dcsxBean);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongGiayUpdate.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				dcsxBean.setMsg("Duyệt thành công");
				if (id != null && id.trim().length() > 0)
					dcsxBean.init();
				else
					dcsxBean = new ErpKiemdinhchatluong(lenhsanxuatId, congdoanId);
				session.setAttribute("dcsxBean", dcsxBean);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongGiayUpdate.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
}
