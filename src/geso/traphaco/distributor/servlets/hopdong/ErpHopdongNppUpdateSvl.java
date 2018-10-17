package geso.traphaco.distributor.servlets.hopdong;

import geso.traphaco.distributor.beans.hopdong.IErpDonhangNppETC;
import geso.traphaco.distributor.beans.hopdong.IErpHopdongNpp;
import geso.traphaco.distributor.beans.hopdong.IErpHopdongNppList;
import geso.traphaco.distributor.beans.hopdong.imp.ErpDonhangNppETC;
import geso.traphaco.distributor.beans.hopdong.imp.ErpHopdongNpp;
import geso.traphaco.distributor.beans.hopdong.imp.ErpHopdongNppList;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHopdongNppUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpHopdongNppUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 
		    
		    String id = util.getId(querystring);  	
		    IErpHopdongNpp lsxBean = new ErpHopdongNpp(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
		    lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	lsxBean.setDoituongId(session.getAttribute("doituongId"));
	    	
	    	String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	 	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	 	    
	 	    lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
			lsxBean.setNpp_duocchon_id(npp_duocchon_id);
	    	
    		if(querystring.contains("display"))
    		{
    			lsxBean.initDisplay();
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNppDisplay.jsp";
    		}
    		else if(querystring.contains("convert"))
    		{    			    
    			lsxBean.initDisplay();
    			
    			session.setAttribute("dvkdId", lsxBean.getDvkdId());
    			session.setAttribute("kenhId", lsxBean.getKbhId());
    			session.setAttribute("khoxuat", lsxBean.getKhoNhapId());
    			session.setAttribute("nppId", lsxBean.getNppId());
    			
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNppDisplay.jsp";
    		}
    		else
    		{
    			lsxBean.init();
    			session.setAttribute("dvkdId", lsxBean.getDvkdId());
    			session.setAttribute("kenhId", lsxBean.getKbhId());
    			session.setAttribute("khoxuat", lsxBean.getKhoNhapId());
    			session.setAttribute("nppId", lsxBean.getNppId());
    			
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNppUpdate.jsp";
    		}
    		
	        session.setAttribute("lsxBean", lsxBean);
	        response.sendRedirect(nextJSP);
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			
			IErpHopdongNpp lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpHopdongNpp("");
		    }
		    else
		    {
		    	lsxBean = new ErpHopdongNpp(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	lsxBean.setDoituongId(session.getAttribute("doituongId"));
	    	
	    	String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	 	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	 	    
	 	    lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
			lsxBean.setNpp_duocchon_id(npp_duocchon_id);
			
		    String tungay = util.antiSQLInspection(request.getParameter("hopdong_tungay"));
		    if(tungay == null || tungay.trim().length() <= 0 )
		    	tungay = "";
		    lsxBean.setTungay(tungay);
		    
		    String denngay = util.antiSQLInspection(request.getParameter("hopdong_denngay"));
		    if(denngay == null || denngay.trim().length() <= 0)
		    	denngay = "";
		    lsxBean.setDenngay(denngay);
		    	    
		    String mahopdong = util.antiSQLInspection(request.getParameter("mahopdong"));
		    if(mahopdong == null)
		    	mahopdong = "";
		    lsxBean.setMahopdong(mahopdong);
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
			if (dvkdId == null)
				dvkdId = "";				
			lsxBean.setDvkdId(dvkdId);
			
			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null)
				kbhId = "";				
			lsxBean.setKbhId(kbhId);
			
			String gsbhId = util.antiSQLInspection(request.getParameter("gsbhId"));
			if (gsbhId == null)
				gsbhId = "";				
			lsxBean.setGsbhId(gsbhId);
			System.out.println("::::GSBH SVL: " + gsbhId);
			
			String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
			if (ddkdId == null)
				ddkdId = "";				
			lsxBean.setDdkdId(ddkdId);
			
			String khId = util.antiSQLInspection(request.getParameter("khId"));
			if (khId == null)
				khId = "";				
			lsxBean.setKhId(khId);
			
			String vat = util.antiSQLInspection(request.getParameter("ptVat"));
			if (vat == null)
				vat = "";				
			lsxBean.setVat(vat);
			
			String loaidonhang = request.getParameter("loaidonhang");
			if (loaidonhang == null)
				loaidonhang = "0";				
			lsxBean.setLoaidonhang(loaidonhang);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
			
			String hopdongId = util.antiSQLInspection(request.getParameter("hopdongId"));
			if (hopdongId == null)
				hopdongId = "";				
			lsxBean.setHopdongId(hopdongId);
			
			String ngaytrungthau = util.antiSQLInspection(request.getParameter("ngaytrungthau"));
			if (ngaytrungthau == null)
				ngaytrungthau = "";				
			lsxBean.setNgaytrungthau(ngaytrungthau);
			
			String chiphibaolanh = util.antiSQLInspection(request.getParameter("chiphibaolanh"));
			if (chiphibaolanh == null)
				chiphibaolanh = "0";				
			lsxBean.setChiphibaolanh(chiphibaolanh);
			
			String capnhatTDV = util.antiSQLInspection(request.getParameter("capnhatTDV"));
			if (capnhatTDV == null)
				capnhatTDV = "0";				
			lsxBean.setCapnhatTDV(capnhatTDV);
			
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] dongia = request.getParameterValues("dongia");
			lsxBean.setSpGianhap(dongia);
			
			String[] spQuyDoi = request.getParameterValues("spQuyDoi");
			lsxBean.setSpQuyDoi(spQuyDoi);
			
			String[] spChietkhau = request.getParameterValues("chietkhau");
			lsxBean.setSpChietkhau(spChietkhau);
			
			String[] spVat = request.getParameterValues("spvat");
			lsxBean.setSpVat(spVat);
			
			String[] trongluong = request.getParameterValues("spTrongLuong");
			lsxBean.setSpTrongluong(trongluong);
			
			String[] thetich = request.getParameterValues("spTheTich");
			lsxBean.setSpThetich(thetich);
			
			String[] spTungay = request.getParameterValues("tungay");
			lsxBean.setSpTungay(spTungay);
			
			String[] spDenngay = request.getParameterValues("denngay");
			lsxBean.setSpDenngay(spDenngay);
			
			String[] spTDV = request.getParameterValues("spTDV");
			lsxBean.setSpTDV(spTDV);
			
			if( capnhatTDV.equals("1") && spTDV != null && ddkdId != null )
			{
				for( int i = 0; i < spTDV.length; i++ )
					spTDV[i] = ddkdId;
			}
			
			//THEM CAC LOAI CHIET KHAU
			String[] dhCK_diengiai = request.getParameterValues("dhCK_diengiai");
			lsxBean.setDhck_Diengiai(dhCK_diengiai);
			String[] dhCK_giatri = request.getParameterValues("dhCK_giatri");
			lsxBean.setDhck_giatri(dhCK_giatri);
			String[] dhCK_loai = request.getParameterValues("dhCK_loai");
			lsxBean.setDhck_loai(dhCK_loai);
			
			String[] khApdungId = request.getParameterValues("khApdungId");
			if(khApdungId != null)
			{
				String _khAP = "";
				for(int i = 0; i < khApdungId.length; i++)
					_khAP += khApdungId[i] + ",";
				
				if(_khAP.trim().length() > 0)
				{
					_khAP = _khAP.substring(0, _khAP.length() - 1);
					lsxBean.setKhApdungId(_khAP);
				}
				
				System.out.println("----KHACH HANG AP DUNG: " + _khAP );
			}
			
			String[] ddhIds = request.getParameterValues("ddhIds");
			if (ddhIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < ddhIds.length; i++)
				{
					_scheme += ddhIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					lsxBean.setDonangmuonIds(_scheme);
				}
			}
				
		    String action = request.getParameter("action");
			if(action.equals("save"))
			{	
				if(id == null)
				{
					boolean kq = lsxBean.createNK();
					if(!kq)
					{
					    lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
						lsxBean.setNpp_duocchon_id(npp_duocchon_id);
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNppNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHopdongNppList obj = new ErpHopdongNppList();
						obj.setLoaidonhang(loaidonhang);
						obj.setLoainhanvien(session.getAttribute("loainhanvien"));
					    obj.setDoituongId(session.getAttribute("doituongId"));
					    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
					    obj.setNpp_duocchon_id(npp_duocchon_id);
						
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNpp.jsp";	
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					String trangthai = request.getParameter("trangthai");
					if(trangthai == null)
						trangthai = "";
					
					//CHỐT RỒI VẪN ĐƯỢC SỬA TDV
					boolean kq = lsxBean.updateNK("1", trangthai );
					if(!kq)
					{
						lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
					    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNppUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHopdongNppList obj = new ErpHopdongNppList();
						obj.setLoaidonhang(loaidonhang);
						obj.setLoainhanvien(session.getAttribute("loainhanvien"));
					    obj.setDoituongId(session.getAttribute("doituongId"));
					    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
					    obj.setNpp_duocchon_id(npp_duocchon_id);
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNpp.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("convert"))
				{
					boolean kq = lsxBean.convertSO();
					String msg = lsxBean.getMsg();
					
					if(!kq)
					{
						lsxBean.init();
						lsxBean.setKhId(khId);
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNppDisplay.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						/*IErpHopdongNppList obj = new ErpHopdongNppList();
						obj.setLoaidonhang(loaidonhang);
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNpp.jsp";
						response.sendRedirect(nextJSP);*/
						
						//CHUYEN SANG TRANG CAP NHAT
						IErpDonhangNppETC lsxBean2 = new ErpDonhangNppETC(msg);
						lsxBean2.setUserId(userId); 
						
						lsxBean2.setTdv_dangnhap_id(tdv_dangnhap_id);
						lsxBean2.setNpp_duocchon_id(npp_duocchon_id);
					    
						lsxBean2.setLoainhanvien(session.getAttribute("loainhanvien"));
		        		lsxBean2.setDoituongId(session.getAttribute("doituongId"));
		    		    
		    		    String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
		    		    
		    		    lsxBean2.init( session.getAttribute("tdv_dangnhap_id").toString() );

		    			session.setAttribute("lsxBean", lsxBean2);
		    	        response.sendRedirect(nextJSP);
						
					}
				}
				else
				{
				
					if(action.equals("changePhuLuc"))
					{
						lsxBean.setSpMa(null);
					}
				    lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
					lsxBean.setNpp_duocchon_id(npp_duocchon_id);
					lsxBean.createRs();
					
					session.setAttribute("dvkdId", lsxBean.getDvkdId());
					session.setAttribute("kenhId", lsxBean.getKbhId());
					session.setAttribute("khoxuat", lsxBean.getKhoNhapId());
					session.setAttribute("nppId", lsxBean.getNppId());
					
					session.setAttribute("lsxBean", lsxBean);
					
					String nextJSP = "";
					
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNppNew.jsp";
					if(id != null)
						nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNppUpdate.jsp";
					
					response.sendRedirect(nextJSP);
				}
			}
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
