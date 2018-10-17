package geso.traphaco.erp.servlets.hoadon;
 
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadon.IErpHoaDon;
import geso.traphaco.erp.beans.hoadon.IErpHoaDon_SP;
import geso.traphaco.erp.beans.hoadon.imp.ErpHoaDon;
import geso.traphaco.erp.beans.hoadon.imp.ErpHoanDon_SP;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 

public class ErpHoaDonUpDateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public ErpHoaDonUpDateSvl() {
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

		String ddhId = util.getId(querystring);

		String userId = util.getUserId(querystring);
		System.out.println("Action : " + action);
		
		if (action.equals("update")) 
		{
			IErpHoaDon dhBean = new ErpHoaDon(ddhId);
			dhBean.setCongtyId((String)session.getAttribute("congtyId"));
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonUpdate.jsp";
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);
		} 
		else if (action.equals("display")) 
		{
			IErpHoaDon dhBean = new ErpHoaDon(ddhId);
			dhBean.setCongtyId((String)session.getAttribute("congtyId"));
			
			/*dhBean.initdisplay(ddhId);*/
			session.setAttribute("obj", dhBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonDisPlay.jsp";
			response.sendRedirect(nextJSP);
		} 
		else 
		{
			IErpHoaDon dhBean = new ErpHoaDon();
			dhBean.setCongtyId((String)session.getAttribute("congtyId"));
			
			dhBean.initdisplay(ddhId);
			session.setAttribute("obj", dhBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonChuaHT.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		Utility util = new Utility();
		session.setAttribute("util", util);
		String id = request.getParameter("id");
		String userId = request.getParameter("userId");
		String userTen = request.getParameter("userTen");

		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);

		String action = request.getParameter("action");
		String tenform = request.getParameter("tenform");
		
		System.out.println("acion= "+ action );
		String nextJSP = "";
		if (tenform.equals("newform")) 
		{
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonNew.jsp";
		} 
		else 
		{
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonUpdate.jsp";
		}
		
		//IErpHoaDon dhBean =(ErpHoaDon)session.getAttribute("obj");
		IErpHoaDon dhBean = new ErpHoaDon();
		
		dhBean.setCongtyId((String)session.getAttribute("congtyId"));

		String ngayxuathd = util.antiSQLInspection(request.getParameter("ngayxuathd"));
		if(ngayxuathd.trim().length() <= 0)
			ngayxuathd = getDateTime();
		dhBean.setNgayxuathd(ngayxuathd);
		
		String ngayghinhanCN = util.antiSQLInspection(request.getParameter("ngayghinhanCN"));
		if(ngayghinhanCN.trim().length() <= 0)
			ngayghinhanCN = getDateTime();
		dhBean.setNgayghinhanCN(ngayghinhanCN);
		
		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		dhBean.SetSoHoaDon(sohoadon);
		
		String kyhieu = util.antiSQLInspection(request.getParameter("kyhieu"));
		dhBean.SetKyHieu(kyhieu);

		String hinhthuc = util.antiSQLInspection(request.getParameter("hinhthuc"));
		dhBean.sethinhthuctt(hinhthuc);

		String nguoimuahang = util.antiSQLInspection(request.getParameter("nguoimuahang"));
		dhBean.SetNguoiMuaHang(nguoimuahang);

		dhBean.setId(id);
		
		String ngaytao = this.getDateTime();
		String ngaysua = ngaytao;
		dhBean.setNgaytao(ngaytao);
		dhBean.setNgaysua(ngaysua);
		dhBean.setNguoitao(userId);
		dhBean.setNguoisua(userId);

		String nhappid = util.antiSQLInspection(request.getParameter("nhappid"));
		if (nhappid == null) 
			nhappid = "";
		dhBean.SetNppId(nhappid);
		
		String tennpp = util.antiSQLInspection(request.getParameter("tennpp"));
		dhBean.setNppTen(tennpp);
		
		String diachinpp = util.antiSQLInspection(request.getParameter("diachinpp"));
		dhBean.setNppDiachi(diachinpp);
		

		String PoMt = util.antiSQLInspection(request.getParameter("POMT"));
		if(PoMt == null)
			PoMt = "";
		dhBean.SetPOMT(PoMt);

		String[] ddhid = request.getParameterValues("ddhid");
		List<String> ddhIdList = new ArrayList<String>();
		if (ddhid != null)
		{
			String ddh = "";
			for(int i = 0; i < ddhid.length; i++)
			{
				ddh += ddhid[i] + ",";
				ddhIdList.add(ddhid[i]);
			}
			
			if(ddh.trim().length() > 0)
			{
				ddh = ddh.substring(0, ddh.length() - 1);
				dhBean.setDonDatHang(ddh);
			}
			dhBean.setDonDatHangIdList(ddhIdList);
		}
			
		String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		if(ghichu == null)
			ghichu = "";
		dhBean.setGhichu(ghichu);
		
		String sopokh = util.antiSQLInspection(request.getParameter("sopokh"));
		if(sopokh == null){
			sopokh = "";
		}
		dhBean.setSoPO(sopokh);
		
		String KM_ghichu = util.antiSQLInspection(request.getParameter("KM_ghichu"));
		if(KM_ghichu == null)
			KM_ghichu = "";
		dhBean.setKM_ghichu(KM_ghichu);

		String hopdongId = util.antiSQLInspection(request.getParameter("hopdongId"));
		if(hopdongId == null)
			hopdongId = "";
		dhBean.setHopdongId(hopdongId);
		
		String tienteId = request.getParameter("tienteId");
		if (tienteId == null) 
			tienteId = "";
		dhBean.setTienteId(tienteId);
		
		
		String htXuat = request.getParameter("htXuat");
		if (htXuat == null) 
			htXuat = "";
		dhBean.setHinhthucxuat(htXuat);
		
		String trangthai = request.getParameter("trangthai");
		if (trangthai == null) 
			trangthai = "";
		dhBean.setTrangthai(trangthai);
		
		String paymentTerms = util.antiSQLInspection(request.getParameter("paymentTerms"));
		if (paymentTerms == null) paymentTerms = ""; else paymentTerms = paymentTerms.trim();
		dhBean.setPaymentTerms(paymentTerms);
		
		String deliveryTerms = util.antiSQLInspection(request.getParameter("deliveryTerms"));
		if (deliveryTerms == null) deliveryTerms = ""; else deliveryTerms = deliveryTerms.trim();
		dhBean.setDeliveryTerms(deliveryTerms);
		
		String etd = util.antiSQLInspection(request.getParameter("etd"));
		if (etd == null) etd = ""; else etd = etd.trim();
		dhBean.setETD(etd);
		
		String remarks = util.antiSQLInspection(request.getParameter("remarks"));
		if (remarks == null) remarks = ""; else remarks = remarks.trim();
		dhBean.setRemarks(remarks);

		String tienBH = util.antiSQLInspection(request.getParameter("tienBH")==null?"0":request.getParameter("tienBH").replaceAll(",",""));
		dhBean.setTienBaoHiem(tienBH);

		String tienVC = util.antiSQLInspection(request.getParameter("tienVC")==null?"0":request.getParameter("tienVC").replaceAll(",",""));
		dhBean.setTienVanChuyen(tienVC);
		
		dhBean.CreateRs();
		
		//Cập nhật các dòng sản phẩm có thể in hóa đơn
		String[] spIds = request.getParameterValues("idsp");
		if (spIds == null) spIds = new String[] {};
		String[] spIns = request.getParameterValues("insp");
		if (spIns == null) spIns = new String[] {};
		for(int i = 0; i < dhBean.GetListSanPham().size(); i++) {
			IErpHoaDon_SP sp = dhBean.GetListSanPham().get(i);
			
			for(int j = 0; j < spIds.length; j++) {
				if(sp.getIdSanPham().equals(spIds[j])) {
					sp.setIn(spIns[j]);
					break;
				}
			}
		}
		Hashtable<String, Double> htb_gia=new Hashtable<String, Double>();
		Hashtable<String, Double> htb_soluong=new Hashtable<String, Double>();
		String[] idsp = request.getParameterValues("idsp");
		String[] dongia = request.getParameterValues("dongia");
		String[] soluongmoi = request.getParameterValues("soluong");
		
		if (idsp != null)
		{
			int m = 0;
			while (m < idsp.length)
			{
				if (idsp[m].trim().length() > 0)
				{
					try{
						double gia=Double.parseDouble(dongia[m].replaceAll(",", ""));
						double sl=Double.parseDouble(soluongmoi[m].replaceAll(",", ""));
						
						htb_gia.put(idsp[m], gia);
						htb_soluong.put(idsp[m], sl);
						
					}catch(Exception er){}
				}
				m++;
			}
		}
		
		dhBean.setSanphamGia(htb_gia);
		dhBean.sethtb_SoluongMoi(htb_soluong);
		
		//System.out.println("GHI CHU = " + dhBean.getGhichu() + ", " + dhBean.getGhiChu());
		if(spIds != null)
		{
			Hashtable<String, String> sanpham_ghichu = new Hashtable<String, String>();
			for(int  i = 0; i < spIds.length; i++)
			{
				String[] ghichu_sp = request.getParameterValues(spIds[i] + "_ghichu");
				if(ghichu_sp != null)
				{
					String gh = "";
					for(int j = 0; j < ghichu_sp.length; j++)
					{
						//System.out.println("Dong " + j + " : " + ghichu_sp[j]);
						if(ghichu_sp[j].trim().length() <= 0)
						{
							ghichu_sp[j] = "NA";
						}
						
						gh += ghichu_sp[j] + "__";
					}
					
					if( gh.trim().length() > 0)
					{
						gh = gh.substring(0, gh.length() - 2);
						sanpham_ghichu.put(spIds[i], gh);
					}
				}
			}
			dhBean.setSanphamGhiChu(sanpham_ghichu);
		}
		
		if (action.equals("save")) 
		{
			//Lưu thì cập nhật các giá trị tiền theo người dùng nhập
			String tygia = util.antiSQLInspection(request.getParameter("tygia"));
			if (tygia == null) 
				tygia = "1";
			dhBean.setTyGiaQuyDoi(tygia);
			
			String tienBvat = util.antiSQLInspection(request.getParameter("tienBvat"));
			if(tienBvat == "")
				tienBvat = "0";
			dhBean.setTongtienchuaVat(Double.parseDouble(tienBvat.replaceAll(",", "")));
			
			String CK = util.antiSQLInspection(request.getParameter("CK"));
			if(CK == "")
				CK = "0";
			dhBean.setChietkhau(Double.parseDouble(CK.replaceAll(",", "")));
			
			String tienCK = util.antiSQLInspection(request.getParameter("tienCK"));
			if(tienCK == null || tienCK.trim().length() <= 0)
				tienCK = "0";
			dhBean.setTienChietKhau(Double.parseDouble(tienCK.replaceAll(",", "")));
			
			String tienKM = util.antiSQLInspection(request.getParameter("tienKM"));
			if(tienKM == "")
				tienKM = "0";
			dhBean.setTienkhuyenmai(Double.parseDouble(tienKM.replaceAll(",", "")));

			tienBH = util.antiSQLInspection(request.getParameter("tienBH")==null?"0":request.getParameter("tienBH").replaceAll(",",""));
			dhBean.setTienBaoHiem(tienBH);

			tienVC = util.antiSQLInspection(request.getParameter("tienVC")==null?"0":request.getParameter("tienVC").replaceAll(",",""));
			dhBean.setTienVanChuyen(tienVC);
			
			String tienCK_KM = util.antiSQLInspection(request.getParameter("tienCK_KM"));
			if(tienCK_KM == "")
				tienCK_KM = "0";
			dhBean.setTienSauCK_KM(Double.parseDouble(tienCK_KM.replaceAll(",", "")));
			
			String tienVat = util.antiSQLInspection(request.getParameter("tienVat"));
			if(tienVat == "")
				tienVat = "0";
			dhBean.setTienVAT(Double.parseDouble(tienVat.replaceAll(",", "")));
			
			String tienAvat = util.antiSQLInspection(request.getParameter("tienAvat"));
			if(tienAvat == "")
				tienAvat = "0";
			dhBean.setTongtiensauVAT(Double.parseDouble(tienAvat.replaceAll(",", "")));
			
			
			String invoice = util.antiSQLInspection(request.getParameter("invoice"));
			if(invoice ==null)
				invoice = "";
			dhBean.setinvoice(invoice);
			
			//--- lấy số lượng để cập nhật---//
			IErpHoaDon_SP dhsp = new ErpHoanDon_SP();
			String soluong = util.antiSQLInspection(request.getParameter("soluong").replaceAll(",", ""));
			if(soluong == "")
				soluong = "0";
				double soluong1 = Double.parseDouble(soluong);
			dhsp.setSoLuong(soluong1);
			dhBean.setGhichu(ghichu);
			dhBean.setSoPO(sopokh);
			
			if (id == null || id.trim().length() == 0) 
			{
				if (!(dhBean.Save())) 
				{
					session.setAttribute("obj", dhBean);
					response.sendRedirect(nextJSP);
				} 
				else 
				{
					dhBean = new ErpHoaDon();
					dhBean.setCongtyId((String)session.getAttribute("congtyId"));
					
					dhBean.setUserId(userId);
					dhBean.setListHoaDon("");

					session.setAttribute("obj", dhBean);

					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDon.jsp";
					response.sendRedirect(nextJSP);
				}
			} 
			else 
			{
				//System.out.println("Trang Thai : "+dhBean.getTrangthai());
			
				if (dhBean.getTrangthai().equals("0")) 
				{
					if (!dhBean.Edit()) 
					{
						session.setAttribute("obj", dhBean);
						response.sendRedirect(nextJSP);
					} 
					else 
					{
						dhBean = new ErpHoaDon();
						dhBean.setCongtyId((String)session.getAttribute("congtyId"));
						
						dhBean.setUserId(userId);
						dhBean.setListHoaDon("");

						session.setAttribute("obj", dhBean);

						nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDon.jsp";
						response.sendRedirect(nextJSP);
					}
				} 
				else 
				{
					if (!dhBean.EditHT()) 
					{
						session.setAttribute("obj", dhBean);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDonChuaHT.jsp";
						response.sendRedirect(nextJSP);
					}
					else 
					{
						dhBean = new ErpHoaDon();
						dhBean.setCongtyId((String)session.getAttribute("congtyId"));
						
						dhBean.setUserId(userId);
						dhBean.setListHoaDon("");
						session.setAttribute("obj", dhBean);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDon.jsp";
						response.sendRedirect(nextJSP);
					}
				}

			}
		} 
		else if (action.equals("reload")) 
		{
			//Chỉnh lại ghi chú
			Hashtable<String, String> sanpham_ghichu = new Hashtable<String, String>();
			for(int i = 0; i < dhBean.GetListSanPham().size(); i++) {
				IErpHoaDon_SP sp = dhBean.GetListSanPham().get(i);
				sanpham_ghichu.put(sp.getIdSanPham(), sp.getGhiChu1());
			}
			dhBean.setSanphamGhiChu(sanpham_ghichu);
			
			dhBean.setCongtyId((String)session.getAttribute("congtyId"));
			//dhBean.CreateRs();
			
			session.setAttribute("obj", dhBean);
			response.sendRedirect(nextJSP);
		}


	}
	
	

}
