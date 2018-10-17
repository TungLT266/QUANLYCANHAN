package geso.traphaco.distributor.servlets.dondathang;

import geso.traphaco.center.beans.dondathang.imp.XLkhuyenmaiTT;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.traphaco.distributor.beans.dondathang.IErpDondathangNpp;
import geso.traphaco.distributor.beans.dondathang.IErpDondathangNppList;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDondathangNpp;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDondathangNppList;
import geso.traphaco.distributor.beans.trakhuyenmai.ITrakhuyenmai;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDondathangNppUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpDondathangNppUpdateSvl() 
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
		    IErpDondathangNpp lsxBean = new ErpDondathangNpp(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		 
    		lsxBean.init();
    		
		    String loaidonhang =lsxBean.getLoaidonhang();
		    if(loaidonhang == null)
		    	loaidonhang = "0";
		    lsxBean.setLoaidonhang(loaidonhang);
		    System.out.println("---LOAI DON HANG: " + loaidonhang);
		    
    		
		    session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nhomkenhId", lsxBean.getNhomkenhId());
			session.setAttribute("nppId", lsxBean.getNppId());
    		
    		if(!querystring.contains("display"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
    			if(loaidonhang.equals("4"))
    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangKhacNppUpdate.jsp";
    			else if(loaidonhang.equals("2"))
    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMTichLuyNppUpdate.jsp";
    			else if(loaidonhang.equals("1"))
    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMUngHangNppUpdate.jsp";
    			else if(loaidonhang.equals("3"))
    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangTrungBayNppUpdate.jsp";
    			
    		}
    		else
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppDisplay.jsp";
    			if(loaidonhang.equals("4"))
    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangKhacNppDisplay.jsp";
    			else if(loaidonhang.equals("2"))
    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMTichLuyNppDisplay.jsp";
    			else if(loaidonhang.equals("1"))
    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMUngHangNppDisplay.jsp";
    			else if(loaidonhang.equals("3"))
    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangTrungBayNppDisplay.jsp";
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
			IErpDondathangNpp lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpDondathangNpp("");
		    }
		    else
		    {
		    	lsxBean = new ErpDondathangNpp(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String ngaydenghi = util.antiSQLInspection(request.getParameter("ngaydenghi"));
		    if(ngaydenghi == null || ngaydenghi.trim().length() <= 0)
		    	ngaydenghi = "";
		    lsxBean.setNgaydenghi(ngaydenghi);
		    	    
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
			
			String nhomkenhId = util.antiSQLInspection(request.getParameter("nhomkenhId"));
			if (nhomkenhId == null)
				nhomkenhId = "";				
			lsxBean.setNhomkenhId(nhomkenhId);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
			
			String chietkhau = util.antiSQLInspection(request.getParameter("ptChietkhau"));
			if (chietkhau == null)
				chietkhau = "";				
			lsxBean.setChietkhau(chietkhau);
			
			String vat = util.antiSQLInspection(request.getParameter("ptVat"));
			if (vat == null)
				vat = "";				
			lsxBean.setVat(vat);
			
			String loaidonhang = util.antiSQLInspection(request.getParameter("loaidonhang"));
			if (loaidonhang == null)
				loaidonhang = "";				
			lsxBean.setLoaidonhang(loaidonhang);
			
			String iskm = util.antiSQLInspection(request.getParameter("iskm")==null?"0":request.getParameter("iskm"));
			lsxBean.setIsKm(iskm);

			String[] schemeIds = request.getParameterValues("schemeIds");
			if (schemeIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < schemeIds.length; i++)
				{
					_scheme += schemeIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					lsxBean.setSchemeId(_scheme);
				}
			}
			
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
			
			String[] spvat = request.getParameterValues("spvat");
			lsxBean.setSpVat(spvat);

			String[] spScheme = request.getParameterValues("scheme");
			lsxBean.setSpScheme(spScheme);
			
			session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nhomkenhId", lsxBean.getNhomkenhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			
		    String action = request.getParameter("action");
			if(action.equals("save"))
			{	
				if(id == null)
				{
					boolean kq = lsxBean.createNK();
					
					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);

		      			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppNew.jsp";
		    			if(loaidonhang.equals("4"))
		    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangKhacNppNew.jsp";
		    			else if(loaidonhang.equals("2"))
		    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMTichLuyNppNew.jsp";
		    			else if(loaidonhang.equals("1"))
		    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMUngHangNppNew.jsp";
		    			else if(loaidonhang.equals("3"))
		    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangTrungBayNppNew.jsp";
	    			
		    			response.sendRedirect(nextJSP);
					}
					else
					{
						/*IErpDondathangNppList obj = new ErpDondathangNppList();
						obj.setLoaidonhang(loaidonhang);
						
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNpp.jsp";
			  			if(loaidonhang.equals("4"))
			  				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangKhacNpp.jsp";
			  			else if(loaidonhang.equals("2"))
			  				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMTichLuyNpp.jsp";
			  			else if(loaidonhang.equals("1"))
			  				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMUngHangNpp.jsp";
			  			else if(loaidonhang.equals("3"))
			  				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangTrungBayNpp.jsp";
			  			
			  			response.sendRedirect(nextJSP);*/
						
						//GỬI MAIL
						
						
						lsxBean = new ErpDondathangNpp(id);
					    lsxBean.setUserId(userId); 
					    
			    		lsxBean.init();
			    		lsxBean.setMsg("Số đơn hàng bạn vừa lưu: " + id);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
			    		
				        session.setAttribute("lsxBean", lsxBean);
				        response.sendRedirect(nextJSP);
						
					}
				}
				else
				{
					boolean kq = lsxBean.updateNK();
					
					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
		    			if(loaidonhang.equals("4"))
		    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangKhacNppUpdate.jsp";
		    			else if(loaidonhang.equals("2"))
		    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMTichLuyNppUpdate.jsp";
		    			else if(loaidonhang.equals("1"))
		    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMUngHangNppUpdate.jsp";
		    			else if(loaidonhang.equals("3"))
		    				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangTrungBayNppUpdate.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						/*IErpDondathangNppList obj = new ErpDondathangNppList();
						obj.setLoaidonhang(loaidonhang);
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
					
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNpp.jsp";
						if(loaidonhang.equals("4"))
							nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangKhacNpp.jsp";
						else if(loaidonhang.equals("2"))
							nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMTichLuyNpp.jsp";
						else if(loaidonhang.equals("1"))
							nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangKMUngHangNpp.jsp";
						else if(loaidonhang.equals("3"))
							nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangTrungBayNpp.jsp";
						
						response.sendRedirect(nextJSP);*/
						
						lsxBean = new ErpDondathangNpp(id);
					    lsxBean.setUserId(userId); 
					    
			    		lsxBean.init();
			    		lsxBean.setMsg("Số đơn hàng bạn vừa lưu: " + id);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
			    		
				        session.setAttribute("lsxBean", lsxBean);
				        response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("apkhuyenmai"))
				{
					//Save donhang truoc
					if(id == null)
					{   
						boolean tao = lsxBean.createNK();
						if (!tao)
						{
							lsxBean.createRs();
		    		    	session.setAttribute("lsxBean", lsxBean);
		    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppNew.jsp";
		    				
		    				response.sendRedirect(nextJSP);
		    				return;
						}
						else
						{
							id = lsxBean.getId();		
						}						
					}
					else
					{
						boolean temp = lsxBean.updateNK();
						
						if (!temp)
						{
							lsxBean.createRs();
							session.setAttribute("lsxBean", lsxBean);
		    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
		    				
		    				response.sendRedirect(nextJSP);
		    				
		    				return;
						}
					}
					
					Hashtable<String, Integer> sanpham_soluong = new Hashtable<String, Integer>();
					Hashtable<String, Float> sanpham_dongia = new Hashtable<String, Float>();
					Hashtable<String, Float> sanpham_quycach = new Hashtable<String, Float>();
					
					String soluong1 = "";
					String spMaKM = "";
					String spSOLUONGKM = "";
					String spDONGIAKM = "";
					float tongiatriDH = 0;

					if(id.trim().length() > 0)
					{	
						//INIT SP VOI QUY CACH NEU TRUONG HOP KHONG PHAI LA DV CHUAN
						dbutils db = new dbutils();
						
						//XOA HET KM CU NEU CO
						String query = " delete ERP_DONDATHANG_CTKM_TRAKM where DONDATHANGID = '" + id + "' ";
						db.update(query);
						
						/*query = " select ( select ma from SANPHAM where PK_SEQ = a.sanpham_fk ) as spMA, a.dvdl_fk, b.DVDL_FK as dvCHUAN, a.soluong, a.dongia,  " +
								" 		case when a.dvdl_fk IS null then a.soluong    " +
								" 			 when a.dvdl_fk = b.DVDL_FK then 1  " +
								" 			 else  ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )   end as quycach   " +
								" from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
								" where a.dondathang_fk = '" + id + "'   " ;*/
						
						query = "  select ( select ma from SANPHAM where PK_SEQ = a.sanpham_fk ) as spMA, a.dvdl_fk, b.DVDL_FK as dvCHUAN,  " +
								" 		case when a.dvdl_fk = b.DVDL_FK then a.soluong  " +
								"  			 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )   end as soluong, " +
								"  		case when a.dvdl_fk = b.DVDL_FK then a.dongia  " +
								"  			 else  a.dongia / ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )   end as dongia,  " +
								"  		case when a.dvdl_fk = b.DVDL_FK then 1  " +
								"  			 else  ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )   end as quycach    " +
								"  from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
								"  where a.dondathang_fk = '" + id + "'  " ;
						ResultSet rsSP = db.get(query);
						
						if(rsSP != null)
						{
							try 
							{
								while(rsSP.next())
								{
									sanpham_soluong.put(rsSP.getString("spMA"), rsSP.getInt("soluong"));
									sanpham_dongia.put(rsSP.getString("spMA"), rsSP.getFloat("dongia"));
									sanpham_quycach.put(rsSP.getString("spMA"), rsSP.getFloat("quycach"));
									
									spMaKM += rsSP.getString("spMA") + "__";
									spSOLUONGKM += rsSP.getInt("soluong") + "__";
									spDONGIAKM += rsSP.getString("dongia") + "__";
									
									soluong1 += rsSP.getString("quycach") + "__";
									tongiatriDH += rsSP.getInt("soluong") * rsSP.getFloat("dongia");
								}
								rsSP.close();
							} 
							catch (Exception e) {}	
						}
						
						db.shutDown();
					}
					
					
					XLkhuyenmaiTT xlkm = new XLkhuyenmaiTT(userId, ngayyeucau, nppId, id, ""); //ngay giao dich trong donhang
					
					xlkm.setMasp(spMaKM.substring(0, spMaKM.length() - 2).split("__"));
					xlkm.setSoluong(spSOLUONGKM.substring(0, spSOLUONGKM.length() - 2).split("__"));
					xlkm.setDongia(spDONGIAKM.substring(0, spDONGIAKM.length() - 2).split("__"));
					
					xlkm.setQuycach(soluong1.substring(0, soluong1.length() - 2).split("__"));
					xlkm.setTonggiatriDh(tongiatriDH);
					xlkm.setIdDonhang(id);
					xlkm.setNgaygiaodich(ngayyeucau);
					xlkm.setLoaiDonHang("0");
					
					xlkm.setHashA(sanpham_soluong);
					xlkm.setHashB(sanpham_dongia);
					xlkm.setHash_QuyCach(sanpham_quycach);
					
					xlkm.setDieuchinh(false); //Lay truong hop ngau nhien /*****OneOne set lai la True******/
					
					xlkm.ApKhuyenMai();
					
				    List<ICtkhuyenmai> ctkmResual = xlkm.getCtkmResual();
				    System.out.println("+++So xuat khuyen mai duoc huong: " + ctkmResual.size() + "\n");
				    
				    if(xlkm.checkConfirm()) //bi dung --> sang trang lua chon
				    {
				    	System.out.println("Bi dung san pham roi...\n");
				    	session.setAttribute("xlkm", xlkm);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/KhuyenMaiNppTT.jsp";
						response.sendRedirect(nextJSP);
						return;
				    }
				    
				    String msg = ""; //nhung ctkm khong thoa
					for(int i = 0; i < ctkmResual.size(); i++)
				    {
				    	ICtkhuyenmai ctkhuyenmai = ctkmResual.get(i);
				    	
				    	//System.out.println("ConFi laf: "+ctkhuyenmai.getConfirm());
				    	if(ctkhuyenmai.getConfirm() == false) //khong dung chung san pham
				    	{	
				    		msg += CreateKhuyenmai(ctkhuyenmai, id, nppId, ngayyeucau, Math.round(tongiatriDH), sanpham_soluong, sanpham_dongia);
				    		
					    	//remove khoi danh sach
				    		ctkmResual.remove(i);	
				    		i = i -1;
				    	}
				    }
					
					
					if(msg.length() > 0)
		    		{
		    			lsxBean.init();
						
						xlkm.DBclose();
		    			lsxBean.setMsg("khong the them moi 'dondathang_ctkm_trakm' " + msg);
				    	session.setAttribute("lsxBean", lsxBean);
				        String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
				        response.sendRedirect(nextJSP);
				        return;
		    		}
				    
				    String nextJSP = "";
				    
				    if(ctkmResual.size() > 0)
				    {
						session.setAttribute("xlkm", xlkm);
						nextJSP = "/TraphacoHYERP/pages/Distributor/KhuyenMaiNppTT.jsp";
						response.sendRedirect(nextJSP);
						
						return;
				    }
				    else
				    {	
				    	System.out.println("----VO DAY SANG TRANG CAP NHAT LAI...");
				    	xlkm.DBclose();
				    	lsxBean.init();
				    	session.setAttribute("lsxBean", lsxBean);
				        nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
				        response.sendRedirect(nextJSP);

				        return;
				    }
				}
				else
				{
					lsxBean.createRs();

					session.setAttribute("lsxBean", lsxBean);
					
					String nextJSP = "";
					
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppNew.jsp";
					if(id != null)
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
					}
					
					response.sendRedirect(nextJSP);
				}
			}
		}
	}
	
	
	private String CreateKhuyenmai(ICtkhuyenmai ctkm, String id, String nppId, String tungay, long tongGtridh, Hashtable<String, Integer> sp_sl, Hashtable<String, Float> sp_dg)
	{
		String str = "";
		dbutils db = new dbutils();
		
		try 
		{ 
			db.getConnection().setAutoCommit(false);
		
			List<ITrakhuyenmai> trakmList = ctkm.getTrakhuyenmai();
			for(int count = 0; count < trakmList.size(); count++)
			{
				//ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(0);			
				ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(count);
				
				long tongtienTraKM = 0;
				if(trakm.getType() == 1)
					tongtienTraKM = Math.round(ctkm.getSoxuatKM() * trakm.getTongtien());
				else
				{
					if(trakm.getType() == 2) //tra chiet khau
					{
						System.out.println("___Tong tien tra km: " + ctkm.getTongTienTheoDKKM() + " -- Chiet khau: " + trakm.getChietkhau());
						//Tinh tong gia tri tra khuyen mai theo dieu kien (chu khong phai tren tong gia tri don hang)
						long tonggiatriTrakm = ctkm.getTongTienTheoDKKM();
						tongtienTraKM = Math.round(tonggiatriTrakm * (trakm.getChietkhau() / 100));
						//tongtienTraKM = Math.round(tongGtridh * (trakm.getChietkhau() / 100));
					}
					else
					{
						if(trakm.getHinhthuc() == 1)
						{
							tongtienTraKM = Math.round(ctkm.getSoxuatKM() * trakm.getTongGtriKm());
							System.out.println("Tong tien trakm co dinh: " + tongtienTraKM + "\n");
						}
					}
				}
				
				/*********************************************************************************/
				if(ctkm.getPhanbotheoDH().equals("0"))
				{
					String msg = CheckNghanSach(ctkm.getId(), nppId, Long.toString(tongtienTraKM), "0", db);
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;
					}
				}
				/*********************************************************************************/
					
				if(trakm.getType() == 3) //san pham co so luong co dinh
				{
					if(trakm.getHinhthuc() == 1)
					{

						String sql = "select f.pk_seq as spId, a.soluong, e.GIAMUANPP as dongia, f.ma as spMa  " +
									"from Trakhuyenmai_sanpham a inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ " +
									"	inner join BGMUANPP_SANPHAM e on a.sanpham_fk = e.SANPHAM_FK " +
									"where a.TRAKHUYENMAI_FK = '" + trakm.getId() + "' " +
										"and e.BGMUANPP_FK in ( select top(1) a.PK_SEQ " +
															  "from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
															  "where a.TUNGAY <= '" + tungay + "' and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select KBH_FK from ERP_DONDATHANG where PK_SEQ = '" + id + "' ) and a.DVKD_FK = ( select DVKD_FK from ERP_DONDATHANG where PK_SEQ = '" + id + "' ) " +
															  "order by a.TUNGAY desc  ) -- and e.GIAMUANPP > 0  and e.trangthai = '1'  ";
						
						//System.out.println("Query lay gia san pham co dinh la: " + sql + "\n");
						
						int index = 0;
						ResultSet rsSQl = db.get(sql);
						if(rsSQl != null)
						{
							while(rsSQl.next())
							{
								int slg = Integer.parseInt(rsSQl.getString("soluong")) * (int)ctkm.getSoxuatKM();
								long tonggtri = Math.round(slg * rsSQl.getFloat("dongia"));
								
								
								/*********************************************************************************/
								if(ctkm.getPhanbotheoDH().equals("1"))
								{
									String msg = CheckNghanSach(ctkm.getId(), nppId, Integer.toString(ctkm.getSoxuatKM()), "1", db);
									/*if(msg.trim().length() > 0)
									{
										System.out.println("----MSG LA: " + msg);
										db.getConnection().rollback();
										return msg;
									}*/
									
									System.out.println("--------------CHECK NGAN SACH: " + msg);
									if(msg.trim().length() < 10 && msg.trim().length() > 0)
									{
										ctkm.setSoxuatKM((int)Float.parseFloat(msg));
										slg = Integer.parseInt(rsSQl.getString("soluong")) * (int)ctkm.getSoxuatKM();
									}
									else
									{
										if(msg.trim().length() > 10)
										{
											db.getConnection().rollback();
											return msg;
										}
									}
								}
								/*********************************************************************************/
								
								
								String error = "";
								if(error.length() > 0)
								{
									return error;
								}
								
								//luu tong gia tri o moi dong sanpham
								//String query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tongtienTraKM) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "')";
								String query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tonggtri) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "')";
								System.out.println("1.Chen khuyen mai co dinh: " + query);
								
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}
								
								//cap nhat kho
								/*query = "Update nhapp_kho set available = available - '" + Integer.toString(slg) + "', booked = booked + '" + Integer.toString(slg) + "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkm.getId() + "') and npp_fk = '" + nppId + "' and sanpham_fk = '" + rsSQl.getString("spId") + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId+ "')";   							
								System.out.println("2.Cap nhat kho: " + query);
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}
							
								query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + Long.toString(tongtienTraKM) + "' where ctkm_fk = '" + ctkm.getId() + "' and npp_fk = '" + nppId + "'";
								System.out.println("4.Cap nhat phanbo Phanbokhuyenmai: " + query); 
								if(!db.update(query))
								{
									db.getConnection().rollback(); 
									str = query;
									return str;
								}*/
								
								index ++;
							}
						}
						rsSQl.close();
					}
					else //tinh so luong san pham nhapp da chon, phai check ton kho tung buoc
					{
						if(trakm.getHinhthuc() == 2)
						{
							
							String query = "select a.sanpham_fk as spId, c.MA as spMa, isnull(bgmnpp.dongia, '0') as dongia, isnull(b.TONGLUONG, 0) as tongluong " +
											"from TRAKM_NHAPP a inner join TRAKHUYENMAI b on a.trakm_fk = b.PK_SEQ " +
											" inner join SANPHAM c on a.sanpham_fk = c.PK_SEQ " +
											" left join (  select sanpham_fk, GIAMUANPP as dongia  " +
											"				from BGMUANPP_SANPHAM   " +
											"				where BGMUANPP_FK in (  select top(1) a.PK_SEQ " +
											"										from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK   " +
											"										where a.TUNGAY <= '" + tungay + "' and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select kbh_fk from ERP_DONDATHANG where pk_seq='" + id + "' ) and a.DVKD_FK = ( select dvkd_fk from ERP_DONDATHANG where pk_seq='" + id + "' ) " +
											"										order by a.TUNGAY desc  )   " +
														") bgmnpp on bgmnpp.sanpham_fk=a.sanpham_fk" + 
												   " where a.ctkm_fk = '" + ctkm.getId() + "' and a.npp_fk = '" + nppId + "' and a.trakm_fk = '" + trakm.getId() + "' " +
											"order by a.thutuuutien asc";
							
							System.out.println("5.Query tinh gia km npp chon truoc: " + query);
							
							ResultSet spkm = db.get(query);
							
							String sp = "";
							String ma = "";
							String dg = "";
							String tg = "";
							while(spkm.next())
							{
								sp += spkm.getString("spId") + ",";
								dg += spkm.getString("dongia") + ",";
								tg += spkm.getString("tongluong") + ",";
								ma += spkm.getString("spMa") + ",";
							}
						
							String[] spId = sp.split(",");
							String[] dongia = dg.split(",");
							String[] tongluong = tg.split(",");
							String[] spMa = ma.split(",");
							
							//CheckTonKho nhung tra khuyen mai da duoc npp chon truoc
							String[] arr = checkTonKhoTraKm(nppId, ctkm, spId, dongia, tongluong, spMa);
							if(arr == null)  //nhung san pham da chon truoc cua tra khuyen mai da het hang trong kho
							{
								db.getConnection().rollback();
								str = "Số lượng những sản phẩm bạn chọn trước trong thiết lập sản phẩm trả khuyến mãi không đủ trong kho";
								System.out.println("Error: " + str + "\n");
								return str;
							}
							else
							{
								/*********************************************************************************/
								if(ctkm.getPhanbotheoDH().equals("1"))
								{
									String msg = "";
									if(msg.trim().length() > 0)
									{
										db.getConnection().rollback();
										return msg;
									}
								}
								/*********************************************************************************/
								
								//luu tong gia tri o moi dong sanpham
								query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + arr[2].replaceAll(",", "") + "', '" + arr[3] + "', '" + arr[1].replaceAll(",", "") + "')";
								System.out.println("6.Chen khuyen mai Npp chon truoc: " + query);
								
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}
								
								//cap nhat kho
								/*query = "Update nhapp_kho set available = available - '" + arr[1].replaceAll(",", "") + "', booked = booked + '" + arr[1].replaceAll(",", "") + "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkm.getId() + "') and npp_fk = '" + nppId + "' and sanpham_fk = '" + arr[0] + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId+ "')";   							
								System.out.println("7.Cap nhat npp_kho: " + query);
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}
								
								query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + arr[2].replaceAll(",", "") + "' where ctkm_fk = '" + ctkm.getId() + "' and npp_fk = '" + nppId + "'";
								System.out.println("9.Cap nhat ngan sach Phanbokhuyenmai: " + query);
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}*/
							}
						}
					}
				}
				else
				{
					if(trakm.getType() != 3)//tra tien, tra chiet khau
					{
						String query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + tongtienTraKM + "')";
						System.out.println("10.Chen khuyen mai tien / ck: " + query);
						if(!db.update(query))
						{
							db.getConnection().rollback();
							str = query;
							return str;
						}
					
						/*query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + Long.toString(tongtienTraKM) + "' where ctkm_fk ='" + ctkm.getId() + "' and npp_fk='" + nppId + "'";
						System.out.println("12.Cap nhat ngan sach Phanbokhuyenmai: " + query);
						if(!db.update(query))
						{
							db.getConnection().rollback();
							str = query;
							return str;
						}*/
					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e1)
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e) {}
			return "Loi khi tao moi ctkm " + ctkm + ", " + e1.toString(); 
		}
		finally
		{
			db.shutDown();
		}
		
		return str;
	}
	
	private String[] checkTonKhoTraKm(String nppId, ICtkhuyenmai ctkm, String[] spId, String[] dongia, String[] tongluong, String[] spma) 
	{
		String[] kq = new String[4];
		
		String msg = "";
		try
		{
			for(int i = 0; i < spId.length; i++)
			{
				int slg = Integer.parseInt(tongluong[i]) * ctkm.getSoxuatKM();
				msg = "";
				if(msg == "")  //thoa ton kho
				{
					kq[0] = spId[i];
					kq[1] = Integer.toString(slg);
					kq[2] = Long.toString(Math.round(slg * Float.parseFloat(dongia[i])));
					//System.out.println("Don gia: " + spId[i] + "- dongia: " + dongia[i] + " - Tong gia tri o buoc nay: " + kq[2] + "\n");
					kq[3] = spma[i];
				
					return kq;
				}
			}
		}
		catch (Exception e) {
			return null;
		}
		return null;
	}
	
	private String CheckNghanSach(String ctkmId, String nppId, String ngansach, String loai, dbutils db)
	{
		//String sql ="select * from phanbokhuyenmai where npp_fk ='" + nppId + "' and ctkm_fk ='" + ctkmId +"'";
		
		String sql = "";
		if(loai.equals("0"))  //PHAN BO KHUYEN MAI THEO SO TIEN
		{
			sql = "select a.CTKM_FK, b.scheme, case b.PHANBOTHEODONHANG, a.NGANSACH,  " + 
					"	  ISNULL( 	( select SUM(tonggiatri)  " + 
					"	  from ERP_DONDATHANG_CTKM_TRAKM  " + 
					"	  where CTKMID = a.CTKM_FK and DONDATHANGID in ( select PK_SEQ from ERP_Dondathang where NPP_FK = a.NPP_FK  ) " +
					"		  ), 0 ) as DASUDUNG  " + 
					"from phanbokhuyenmai a inner join CTKHUYENMAI b on a.CTKM_FK = b.PK_SEQ  " + 
					"where npp_fk = '" + nppId + "' and ctkm_fk = '" + ctkmId + "'  ";
		}
		else  //PHAN BO KHUYEN MAI THEO SO LUONG
		{
			sql = "select a.CTKM_FK, b.scheme, b.PHANBOTHEODONHANG, a.NGANSACH,  " + 
					"	  ISNULL( 	( select SUM(SOXUAT)  " + 
					"	  from ERP_DONDATHANG_CTKM_TRAKM  " + 
					"	  where CTKMID = a.CTKM_FK AND SPMA IS NOT NULL and DONDATHANGID in ( select PK_SEQ from ERP_Dondathang where NPP_FK = a.NPP_FK   ) " +
							"  ), 0 ) as DASUDUNG  " + 
					"from phanbokhuyenmai a inner join CTKHUYENMAI b on a.CTKM_FK = b.PK_SEQ  " + 
					"where npp_fk = '" + nppId + "' and ctkm_fk = '" + ctkmId + "'  ";
		}
				
		System.out.println("1.Cau lenh check ngan sach: " + sql + " --- Ngan sach de check: " + ngansach);
		ResultSet rs = db.get(sql);
		String scheme = "";
		
		try 
		{
			float conlai = 0.0f;
			if(rs.next())
			{
				scheme = rs.getString("scheme");
				conlai = Float.parseFloat(rs.getString("ngansach")) - Float.parseFloat(rs.getString("DASUDUNG"));
				rs.close();	
			}
			
			System.out.println("---NGAN SACH ( SOXUAT / TONG GIA TRI ): " + ngansach + " / " + ngansach + " -- CON LAI: " + conlai);
			
			if(Float.parseFloat(ngansach) > conlai)
			{
				Float _conlai = Math.min(Float.parseFloat(ngansach), conlai);
				if(_conlai <= 0)
					return "1.Chương trình khuyến mại: " + scheme + ", đã hết ngân sách phân bổ";
				else
					return  Float.toString(Math.round( _conlai ));
			}
			
		} 
		catch (Exception e) 
		{ 
			System.out.println("__EXCEPTION CHECK NGAN SACH: " + e.getMessage());
			e.printStackTrace();
			return "2.Chương trình khuyến mại: " + scheme + ", đã hết ngân sách phân bổ";
		}
		
		return "";
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
