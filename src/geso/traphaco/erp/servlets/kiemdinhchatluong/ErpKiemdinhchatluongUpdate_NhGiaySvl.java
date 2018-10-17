package geso.traphaco.erp.servlets.kiemdinhchatluong;

import geso.traphaco.erp.beans.kiemdinhchatluong.IErpKiemdinhchatluongList_NhGiay;
import geso.traphaco.erp.beans.kiemdinhchatluong.IErpKiemdinhchatluong_NhGiay;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.IErpHoso;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.imp.ErpHoso;
import geso.traphaco.erp.beans.kiemdinhchatluong.imp.ErpKiemdinhchatluongList_NhGiay;
import geso.traphaco.erp.beans.kiemdinhchatluong.imp.ErpKiemdinhchatluong_NhGiay;
import geso.traphaco.center.beans.thongtinsanpham.ITieuchikiemdinh;
import geso.traphaco.center.beans.thongtinsanpham.imp.Tieuchikiemdinh;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKiemdinhchatluongUpdate_NhGiaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpKiemdinhchatluongUpdate_NhGiaySvl() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpKiemdinhchatluong_NhGiay kdcl;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    kdcl = new ErpKiemdinhchatluong_NhGiay(id);
	    kdcl.setYcId(id);
	    kdcl.setUserId(userId);
	    kdcl.setCongtyId((String)session.getAttribute("congtyId"));
	    
	    
	    // lấy nó thuộc loại nào?
	    // lay loai mua hang de xem la mua hang nhap khau hay trong nuoc
		String loaimh = request.getParameter("loaiMH");
		 if(loaimh.equals("0")) // mua hang nhapkhau
	    {
		 	kdcl.setloaimuahang("0");
	    }else if(loaimh.equals("1"))  // mua hang trong nuoc
	    {
	    	kdcl.setloaimuahang("1");
	    }else{
	    	kdcl.setloaimuahang("2");
	    }
		
	    kdcl.init();
        session.setAttribute("kdcl", kdcl);
        
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiayUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiayDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		NumberFormat formatter = new DecimalFormat("#######.####");  
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpKiemdinhchatluong_NhGiay kdcl;

		Utility util = new Utility();
		String id = util.antiSQLInspection(request.getParameter("id"));
		
		String loaispId = util.antiSQLInspection(request.getParameter("loaispId"));
		if(loaispId == null) loaispId = "";
		
		String khoId = util.antiSQLInspection(request.getParameter("khoId"));
		if(khoId == null) khoId = "";
		
		String loai = util.antiSQLInspection(request.getParameter("loai"));
		if(loai == null) loai = "";
		
		String spId = util.antiSQLInspection(request.getParameter("spId"));
		if(spId == null) spId = "";
		
		// cho phép chỉnh lại mã phiếu
		String maphieu = util.antiSQLInspection(request.getParameter("maphieu"));
		if(maphieu == null) maphieu = "";
		
		
		String dinhluong = util.antiSQLInspection(request.getParameter("dinhluong"));
		if(dinhluong == null) dinhluong = "";
		
		String dinhtinh = util.antiSQLInspection(request.getParameter("dinhtinh"));
		if(dinhtinh == null) dinhtinh = "";
		
		String datcl = util.antiSQLInspection(request.getParameter("datcl"));
		if(datcl == null) datcl = "";
		
		String soluongkiemdinh = util.antiSQLInspection(request.getParameter("soluongkiemdinh"));
		if(soluongkiemdinh == null) soluongkiemdinh = "";
		
		String soluongdat = util.antiSQLInspection(request.getParameter("soluongdat"));
		System.out.println("so luong dat Serrvlet:" + soluongdat);
		if(soluongdat == null) soluongdat = "";
		
		String solo = util.antiSQLInspection(request.getParameter("solo"));
		if(solo == null) solo = "";
		
		String spTen = util.antiSQLInspection(request.getParameter("spTen"));
		if(spTen == null) spTen = "";
		
		String ngaykiem = util.antiSQLInspection(request.getParameter("ngaykiem"));
		if(ngaykiem == null) ngaykiem = "";
		
		String nhanhangId = util.antiSQLInspection(request.getParameter("nhanhangId"));
		if(nhanhangId == null) nhanhangId = "";
		
		String tinhtrang = util.antiSQLInspection(request.getParameter("tinhtrang"));
		if(tinhtrang == null) tinhtrang = "";
		
		String denghixuly =  request.getParameter("denghixuly") ;
		if(denghixuly == null) denghixuly = "";
		
		String  nccId = util.antiSQLInspection(request.getParameter("nccId"));
		if(nccId == null) nccId ="";
		
		
		System.out.println(" denghi xu ly : "+denghixuly);
		
		String kyhieumaukd =  request.getParameter("kyhieumaukd") ;
		if(kyhieumaukd == null) kyhieumaukd = "";
		
		String iscapdong = util.antiSQLInspection(request.getParameter("iscapdong"));
		
		if(iscapdong==null){
			iscapdong="0";
		}
		
		if (id == null)
		{
			kdcl = new ErpKiemdinhchatluong_NhGiay();
		} 
		else
		{
			kdcl = new ErpKiemdinhchatluong_NhGiay(id);
		}

		String thieuhoso = util.antiSQLInspection(request.getParameter("thieuhoso"));
		if(thieuhoso==null){
			thieuhoso="0";
		}
		
		kdcl.setThieuhoso(thieuhoso);
	 
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		kdcl.setUserId(userId);
		String congtyId = ((String) session.getAttribute("congtyId"));
		kdcl.setCongtyId(congtyId);
		
		kdcl.setMaPhieu(maphieu);
		kdcl.setNhanhangId(nhanhangId);
		kdcl.setsoluongDat(soluongdat);
		kdcl.setSoLuongKiemDinh(soluongkiemdinh);
		kdcl.setKyhieukd(kyhieumaukd);
		kdcl.setNccId(nccId);
		
		double soluongmau_ct=0;
		try{
			soluongmau_ct =Double.parseDouble(request.getParameter("soluongmau").replaceAll(",",""));
		}catch (Exception e) {
			 
		}
		kdcl.setSoLuongmau(formatter.format(soluongmau_ct));
		

		double tongsoluongnhap =0;
		try{
			tongsoluongnhap =Double.parseDouble(request.getParameter("tongsoluongnhap").replaceAll(",",""));
		}catch (Exception e) {
			 
		}
		kdcl.setTongsoluongnhap(formatter.format(tongsoluongnhap));
		
		double soluongchuakiem =0;
		try{
			soluongchuakiem =Double.parseDouble(request.getParameter("soluongchuakiem").replaceAll(",",""));
		}catch (Exception e) {
			 
		}
		kdcl.setsoluongchuakiem(formatter.format(soluongchuakiem));

		kdcl.setSolo(solo);
		kdcl.setDatCl(datcl);
		kdcl.setDinhluong(dinhluong);
		kdcl.setDinhtinh(dinhtinh);
		
		kdcl.setLOAISPID(loaispId);
		kdcl.setKhoId(khoId);
		kdcl.setLoai(loai);
		
		kdcl.setSpId(spId);
		kdcl.setSpTen(spTen);
		kdcl.setNgayKiem(ngaykiem);
		kdcl.setTrangThai(tinhtrang);
		kdcl.setDeNghiXuLy(denghixuly.trim());
		kdcl.setIsCapDong(iscapdong);
 
		
		// xử lý phần thùng nào thuộc mẻ nào được lấy bao nhiêu
		// 07/06/2016
		String[] listSoThung = request.getParameterValues("listSoThung");
		String[] listSoLuongThung = request.getParameterValues("listSoLuongThung");
		String[] listLayMauThung = request.getParameterValues("listLayMauThung");
		String[] listDatThung = request.getParameterValues("listDatThung");
		String[] listKhongDatThung = request.getParameterValues("listKhongDatThung");
		String[] listHamAm = request.getParameterValues("listHamAm");
		String[] listHamLuong = request.getParameterValues("listHamLuong");
		
		if(listDatThung!=null){
			for(int t=0;  t< listDatThung.length;t++){
				System.out.println("Vào đây nek  : ");
				listDatThung[t]= listDatThung[t].replaceAll(",", "");
				listLayMauThung[t]=listLayMauThung[t].replaceAll(",", "");
				listSoLuongThung[t]=listSoLuongThung[t].replaceAll(",", "");
				listKhongDatThung[t]= listKhongDatThung[t].replaceAll(",", "");
				
			}
		}
		kdcl.setListDatThung(listDatThung);
		kdcl.setListKhongDatThung(listKhongDatThung);
		kdcl.setListLayMauThung(listLayMauThung);
		kdcl.setListSoLuongThung(listSoLuongThung);
		kdcl.setListSoThung(listSoThung);
		kdcl.setListHamAm(listHamAm);
		kdcl.setListHamLuong(listHamLuong);
		
		
		String[] hoso = request.getParameterValues("hoso");
		List<IErpHoso> listhoso = new ArrayList<IErpHoso>();
		if(hoso!=null){
			for (int i = 0; i < hoso.length; i++)
			{
				if (hoso[i] != "")
				{
					IErpHoso hs=new ErpHoso();
					hs.setHoso(hoso[i]);
					listhoso.add(hs);
				}
			}
		}
		
		kdcl.setListHoso(listhoso);
		
		String[] tieuchi = request.getParameterValues("tieuchi");
		String[] toantu = request.getParameterValues("toantu");
		String[] giatrichuan = request.getParameterValues("giatrichuan");
		String[] diemdat = request.getParameterValues("diemdat");
		String[] trangthai = request.getParameterValues("trangthai");
		String[] dat = request.getParameterValues("dat");
		String[] nguoisua = request.getParameterValues("nguoisua");

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

			kdcl.setTieuchikiemdinhList(tckdList);
		}
		
		
		String[] tieuchi_dinhtinh = request.getParameterValues("tieuchi_dinhtinh");
		String[] ketqua_dinhtinh = request.getParameterValues("ketqua_dinhtinh");
		String[] ghinhan_dinhdinh = request.getParameterValues("ghinhan_dinhtinh");
		String[] nguoisua_dinhtinh = request.getParameterValues("nguoisua_dinhtinh");
		
		if(tieuchi_dinhtinh != null)
		{
			ketqua_dinhtinh = new String [tieuchi_dinhtinh.length];
			for (int i = 0; i < tieuchi_dinhtinh.length; i++)
			{
				String kq = request.getParameter("ketqua_dinhtinh_" + i);
				if(kq == null)
					ketqua_dinhtinh[i] = "0";
				else 
					ketqua_dinhtinh[i] = "1";
			}
			
			kdcl.setTieuchi_dinhtinh(tieuchi_dinhtinh);
			kdcl.setGhinhan_dinhtinh(ghinhan_dinhdinh);
			kdcl.setNguoiSua_dinhtinh(nguoisua_dinhtinh);
			kdcl.setKetqua_dinhtinh(ketqua_dinhtinh);
			
		}

		
		String action = request.getParameter("action");
		if (action.equals("save"))
		{
			if (!(kdcl.updateKiemdinh( request)))
			{
				kdcl.createRs();
				session.setAttribute("kdcl", kdcl);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiayUpdate.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				IErpKiemdinhchatluongList_NhGiay obj = new ErpKiemdinhchatluongList_NhGiay();
				 // lấy nó thuộc loại nào?
			    // lay loai mua hang de xem la mua hang nhap khau hay trong nuoc
				String loaimh = request.getParameter("loaiMH");
				 if(loaimh.equals("0")) // mua hang nhapkhau
			    {
					 obj.setloaimuahang("0");
			    }else if(loaimh.equals("1"))  // mua hang trong nuoc
			    {
			    	obj.setloaimuahang("1");
			    }
				 
				//System.out.println("Kiem dinh OK...");
				
			    obj.setUserId(userId);
			   
			    //obj.setloaimuahang("1");
			    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			    obj.init("");
				session.setAttribute("obj", obj);
			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiay.jsp";
				response.sendRedirect(nextJSP);
			}
		} else if(action.equals("capnhathoso")){
			if (!(kdcl.CapNhatHoSo()))
			{
				kdcl.createRs();
				session.setAttribute("kdcl", kdcl);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiayUpdate.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				//System.out.println("Kiem dinh OK...");
				IErpKiemdinhchatluongList_NhGiay obj = new ErpKiemdinhchatluongList_NhGiay();
				 // lấy nó thuộc loại nào?
			    // lay loai mua hang de xem la mua hang nhap khau hay trong nuoc
				String loaimh = request.getParameter("loaiMH");
				 if(loaimh.equals("0")) // mua hang nhapkhau
			    {
					 obj.setloaimuahang("0");
			    }else if(loaimh.equals("1"))  // mua hang trong nuoc
			    {
			    	obj.setloaimuahang("1");
			    }
				 
			    obj.setUserId(userId);
			    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			    obj.init("");
				session.setAttribute("obj", obj);
			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiay.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		else if (action.equals("duyet"))
		{
			String loaimh = request.getParameter("loaiMH"); //mang 2 gia  tri 0( nhap khau) hoac 2(trong nuoc)
			//xet loai mua hang]
			String loaimuahang="";
			if(loaimh!=null)
				loaimuahang=loaimh;
			
			// bây giờ sài hàm này duyetKiemDinhNhapKhau()
			if ( !kdcl.updateKiemdinh( request) || !(kdcl.duyetKiemDinhNhapKhau(loaimuahang)))
			{
				kdcl.setloaimuahang(loaimuahang);
				kdcl.init();
				kdcl.createRs();
				session.setAttribute("kdcl", kdcl);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiayUpdate.jsp";
				response.sendRedirect(nextJSP);
			} 
			else
			{
				IErpKiemdinhchatluongList_NhGiay obj = new ErpKiemdinhchatluongList_NhGiay();
				 // lấy nó thuộc loại nào?
			    // lay loai mua hang de xem la mua hang nhap khau hay trong nuoc
				
				 if(loaimh.equals("0")) // mua hang nhapkhau
			    {
					 obj.setloaimuahang("0");
			    }else if(loaimh.equals("1"))  // mua hang trong nuoc
			    {
			    	obj.setloaimuahang("1");
			    }else{
			    	obj.setloaimuahang(loaimh);
			    }
				 
			    obj.setUserId(userId);
			    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			    obj.init("");
				session.setAttribute("obj", obj);
			    
			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiay.jsp";
			    response.sendRedirect(nextJSP);
			}
		}else{
	    	
			kdcl.setUserId(userId);
			kdcl.setLoai("2");
			kdcl.createRs();
    		
	    	session.setAttribute("kdcl", kdcl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_NhGiayNew.jsp");

		}
	}
	
	
}
