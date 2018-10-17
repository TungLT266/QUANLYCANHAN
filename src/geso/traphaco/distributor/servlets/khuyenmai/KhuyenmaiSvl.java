package geso.traphaco.distributor.servlets.khuyenmai;

import geso.traphaco.distributor.beans.ctkhuyenmai.*;
import geso.traphaco.distributor.beans.ctkhuyenmai.imp.*;
import geso.traphaco.distributor.beans.donhang.IDonhang;
import geso.traphaco.distributor.beans.donhang.imp.Donhang;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhuyenmaiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public KhuyenmaiSvl() 
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{ 
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  
		
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			Utility util = new Utility();
			userId = util.antiSQLInspection(request.getParameter("userId"));

			String khachhang = util.antiSQLInspection(request.getParameter("khachhang"));
			String dhId = util.antiSQLInspection(request.getParameter("dhId"));

			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if(nppId == null)
				nppId = "";

			String ngaygiaodich = util.antiSQLInspection(request.getParameter("ngaygiaodich"));
			IDonhang dhBean = new Donhang(dhId);
			dhBean.setUserId(userId);
			dhBean.setNgaygiaodich(ngaygiaodich);
			dhBean.setKhId(khachhang);
			dhBean.setNppId(nppId);

			String denghitraCK = request.getParameter("denghitraCK")==null?"0": request.getParameter("denghitraCK");

			dhBean.setDenghitrackThang(denghitraCK);

			String loaidonhang = request.getParameter("loaidonhang");
			if(loaidonhang == null)
				loaidonhang = "0";


			String tonggiatridh = util.antiSQLInspection(request.getParameter("tonggiatri"));
			String[] schemeList = request.getParameterValues("schemeList");

			String[] masp = request.getParameterValues("spMa");
			String[] soluong = request.getParameterValues("spSoluong");
			String[] dongia = request.getParameterValues("spDongia");
			String[] quycach = request.getParameterValues("spQuycach");

			String action = request.getParameter("action");
			if (action == null)
				action = "";

			System.out.println("11.So CTKM: " + schemeList.length);

			String error = "";
			if(action.equals("save"))
			{
				error = dhBean.saveTRAKHUYENMAI(dhBean, request);

				if(error.trim().length() <= 0)
				{
					dhBean.setNpp_duocchon_id(npp_duocchon_id);
					dhBean.setTdv_dangnhap_id(tdv_dangnhap_id);
					
					/*********************************** Them tich luy **********************************/
					//dhBean.ApTichLuy();
					//dhBean.ApTichLuy_Quy();
					dhBean.ApChietKhau(dhId);
					dhBean.Update_GiaTri_DonHang();

					System.out.println("[KhuenMaiSvl]");

					dhBean.init();
					if(!error.equals(""))
						dhBean.setMessage(error);

					dhBean.setAplaikhuyenmai(false);
					dhBean.setMessage("Số đơn hàng bạn vừa lưu " + dhId);
					
					session.setAttribute("dhBean", dhBean);

					String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
					response.sendRedirect(nextJSP);	
					return;
				}
			}

			if( !action.equals("save") || error.trim().length() > 0)
			{
				//XLkhuyenmai xlkm = new XLkhuyenmai(userId, ngaygiaodich, khachhang, loaidonhang);
				XLkhuyenmai xlkm = dhBean.getXLkhuyenmai(userId, ngaygiaodich, khachhang, loaidonhang, dhId);
				xlkm.setDenghitraCK(denghitraCK);

				Hashtable<String,Float> sp_sl = new Hashtable<String,Float>();
				Hashtable<String,Float> sp_dg = new Hashtable<String,Float>();
				Hashtable<String,Float> sp_qc = new Hashtable<String,Float>();

				int m = 0; 
				while(m < masp.length)
				{
					sp_sl.put(masp[m], Float.parseFloat(soluong[m].replaceAll(",", "")));
					sp_dg.put(masp[m], Float.parseFloat(dongia[m].replaceAll(",", "")));
					sp_qc.put(masp[m], Float.parseFloat(quycach[m].replaceAll(",", "")));

					m++;
				}

				/*String[] scheme = request.getParameterValues("Scheme");
				if(scheme != null)
				{
					List<ICtkhuyenmai> listCTKM = xlkm.SortList(xlkm.getCtkmList(), scheme);
					xlkm.setCtkmList(listCTKM); //Sort lai thu tu
				}*/
				
				//Bên này dùng 2 cách: nếu có check thì lấy check, không thì kéo lên kéo xuống để chọn
		    	String[] scheme = request.getParameterValues("Scheme");
		    	String[] schemeSTT = request.getParameterValues("schemeSTT");
		    	
		    	if(scheme != null)
		    	{
		    		if( schemeSTT != null )
		    		{
		    			List<ICtkhuyenmai> listCTKM_HIENHUU = new ArrayList<ICtkhuyenmai>();
		    			
		    			//REMOVE NHUNG SCHEME KHONG DUOC CHON
		    			for(int i = 0; i < xlkm.getCtkmList().size(); i++ )
		    			{
		    				//boolean cotrongSELECTED = false;
		    				for(int j = 0; j < schemeSTT.length; j++ )
		    				{
		    					if( xlkm.getCtkmList().get(i).getId().equals( schemeSTT[j] ) )
		    					{
		    						//cotrongSELECTED = true;
		    						listCTKM_HIENHUU.add( xlkm.getCtkmList().get(i) );
		    						break;
		    					}
		    				}
		    				
		    				/*if(!cotrongSELECTED)
		    				{
		    					System.out.println("::: DA REMOVE: " + listCTKM_HIENHUU.get(i).getId() + " -- SCHEME: " + listCTKM_HIENHUU.get(i).getscheme() );
		    					listCTKM_HIENHUU.remove(i);
		    					i -=1;
		    				}*/
		    			}

			    		xlkm.setCtkmList(listCTKM_HIENHUU); //Sort lai thu tu
		    		}
		    		else
		    		{
			    		List<ICtkhuyenmai> listCTKM = xlkm.SortList(xlkm.getCtkmList(), scheme);
			    		xlkm.setCtkmList(listCTKM); //Sort lai thu tu
		    		}
		    	}

		    	
				String showAll = util.antiSQLInspection(request.getParameter("ShowAll"));
				if(showAll == null)
					xlkm.setDieuchinh(true);
				else
					xlkm.setDieuchinh(false);

				xlkm.setMasp(masp);
				xlkm.setSoluong(soluong);
				xlkm.setDongia(dongia);
				xlkm.setQuycach(quycach);
				xlkm.setIdDonhang(dhId);
				xlkm.setTonggiatriDh(Float.parseFloat(tonggiatridh));
				xlkm.setNgaygiaodich(ngaygiaodich);

				xlkm.setMsg(error);

				xlkm.setHashA(sp_sl);
				xlkm.setHashB(sp_dg);
				xlkm.setHash_QuyCach(sp_qc);

				xlkm.ApKhuyenMai();
				xlkm.checkConfirm();
				session.setAttribute("xlkm", xlkm);

				String nextJSP = "/TraphacoHYERP/pages/Distributor/KhuyenMai.jsp";
				response.sendRedirect(nextJSP);
			}
		} 
	}	

}
