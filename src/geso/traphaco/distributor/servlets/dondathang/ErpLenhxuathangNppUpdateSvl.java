package geso.traphaco.distributor.servlets.dondathang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.dondathang.IErpDondathangDoitac;
import geso.traphaco.distributor.beans.dondathang.IErpDondathangDoitacList;
import geso.traphaco.distributor.beans.dondathang.IErpDuyetddhNppList;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDondathangDoitac;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDondathangDoitacList;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDuyetddhNppList;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpLenhxuathangNppUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpLenhxuathangNppUpdateSvl() 
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
		    
		    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		    
		    /*String phanloai = request.getParameter("phanloai");
			if(phanloai == null)
				phanloai = "";*/
		    
		    String id = util.getId(querystring);  	
		    IErpDondathangDoitac lsxBean = new ErpDondathangDoitac(id);
		    //lsxBean.setPhanloai(phanloai);
		    lsxBean.setUserId(userId); 
		    lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
		    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			session.setAttribute("doitacId", lsxBean.getKhId());
			session.setAttribute("khoId", lsxBean.getKhoNhapId());
    		if(querystring.contains("display"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHangDisplay.jsp";	
    		}
    		else if(querystring.contains("duyet"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHangDuyetDisplay.jsp";
    		}
    		else
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHangUpdate.jsp";
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
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		    
			this.out = response.getWriter();
			IErpDondathangDoitac lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpDondathangDoitac("");
		    }
		    else
		    {
		    	lsxBean = new ErpDondathangDoitac(id);
		    }
	
		    lsxBean.setUserId(userId);
		    lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
		    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
		    
		    
		    String phanloai = request.getParameter("phanloai");
			if(phanloai == null)
				phanloai = "";
			lsxBean.setPhanloai(phanloai);
			
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String ngaydenghi = util.antiSQLInspection(request.getParameter("ngaydenghi"));
		    if(ngaydenghi == null || ngaydenghi.trim().length() <= 0)
		    	ngaydenghi = "";
		    lsxBean.setNgaydenghi(ngaydenghi);
		    	    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu").trim());
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
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
			
			String doitacId = util.antiSQLInspection(request.getParameter("doitacId"));
			if (doitacId == null)
				doitacId = "";				
			lsxBean.setKhId(doitacId);
			
			System.out.println("doi tac id" + doitacId);

			String vat = util.antiSQLInspection(request.getParameter("ptVat"));
			if (vat == null)
				vat = "";				
			lsxBean.setVat(vat);
			
			String loaidonhang = util.antiSQLInspection(request.getParameter("loaidonhang"));
			if (loaidonhang == null)
				loaidonhang = "";				
			lsxBean.setLoaidonhang(loaidonhang); 
			
			String ptChietkhau = util.antiSQLInspection(request.getParameter("ptChietkhau"));
			if (ptChietkhau == null)
				ptChietkhau = "0";				
			lsxBean.setChietkhau(ptChietkhau);
			
			
			String iskm = util.antiSQLInspection(request.getParameter("iskm")==null?"0":request.getParameter("iskm"));
			lsxBean.setIsKm(iskm);

			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] soluongton = request.getParameterValues("soluongton");
			lsxBean.setSpSoluongton(soluongton);

			String[] dongia = request.getParameterValues("dongia");
			lsxBean.setSpGianhap(dongia);
			
			String[] dongiagoc = request.getParameterValues("dongiagoc");
			lsxBean.setSpGiagoc(dongiagoc);
			
			String[] chietkhau = request.getParameterValues("chietkhau");
			lsxBean.setSpChietkhau(chietkhau);
			
			String[] spvat = request.getParameterValues("spvat");
			lsxBean.setSpVat(spvat);

			String[] spScheme = request.getParameterValues("scheme");
			lsxBean.setSpScheme(spScheme);
			
			
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
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHangNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDondathangDoitacList obj = new ErpDondathangDoitacList();
						obj.setLoaidonhang(loaidonhang);
						obj.setTdv_dangnhap_id(tdv_dangnhap_id);
						obj.setNpp_duocchon_id(npp_duocchon_id);
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHang.jsp";
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
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHangUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDondathangDoitacList obj = new ErpDondathangDoitacList();
						obj.setLoaidonhang(loaidonhang);
						obj.setTdv_dangnhap_id(tdv_dangnhap_id);
						obj.setNpp_duocchon_id(npp_duocchon_id);
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("duyet"))
				{
					Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
					for(int i = 0; i < spMa.length; i++ )
					{
						//System.out.println("---SP MA LA: " + spMa[i]);
						String temID = spMa[i] + "__" + spScheme[i];
						
						String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
						String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
						
						String[] spNgayHetHan = request.getParameterValues(temID + "_spNGAYHETHAN");
						
						if(soLUONGXUAT != null)
						{
							for(int j = 0; j < soLUONGXUAT.length; j++ )
							{
								if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0)
								{
									String key = spMa[i] + "__" + spScheme[i] + "__" + spSOLO[j] + "__" + spNgayHetHan[j];
									
									//System.out.println("---KEY SVL: " + ( spMa[i] + "__" + spScheme[i] + "__" + spSOLO[j] + "__" + spNgayHetHan[j] )  + "   --- GIA TRI: " + soLUONGXUAT[j].replaceAll(",", "") );
									sanpham_soluong.put(key , soLUONGXUAT[j].replaceAll(",", "") );								
								}
							}
						}
					}
					
					lsxBean.setSanpham_Soluong(sanpham_soluong);
					
					String dungchungkenh = request.getParameter("dungchungkenh");
					if(dungchungkenh == null)
						dungchungkenh = "0";
					lsxBean.setDungchungKenh(dungchungkenh);	
					
					if(!lsxBean.duyetDH( session.getAttribute("congtyId").toString() ))
					{
						lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
						lsxBean.setNpp_duocchon_id(npp_duocchon_id);
						
						lsxBean.init();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHangDuyetDisplay.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDuyetddhNppList obj = new ErpDuyetddhNppList();
						obj.setUserId(userId);
						obj.setTdv_dangnhap_id(tdv_dangnhap_id);
						obj.setNpp_duocchon_id(npp_duocchon_id);
						System.out.println("----PHAN LOAI:::: " + phanloai);
						obj.setPhanloai(phanloai);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
					lsxBean.setNpp_duocchon_id(npp_duocchon_id);
					
					lsxBean.createRs();
					
					session.setAttribute("dvkdId", lsxBean.getDvkdId());
					session.setAttribute("kbhId", lsxBean.getKbhId());
					session.setAttribute("nppId", lsxBean.getNppId());
					session.setAttribute("doitacId", lsxBean.getKhId());
					session.setAttribute("khoId", lsxBean.getKhoNhapId());
					session.setAttribute("lsxBean", lsxBean);
					
					String nextJSP = "";
					
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHangNew.jsp";
					if(id != null)
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHangUpdate.jsp";
					}
					
					response.sendRedirect(nextJSP);
				}
			}
		}
	}
	public static void main(String[] args) {
		dbutils db = new dbutils();
		// Mở chốt lệnh xuất hàng
		try
		{	
			db.getConnection().setAutoCommit(false);
			String query = "";
				String msg ="";
			query = "select pk_seq,npp_fk from ERP_dondathangnpp where pk_seq in (113442) ";	
				
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				String dhId = rs.getString("pk_seq");
				String nppId = rs.getString("npp_fk");
					
					query = "select DDH_FK from ERP_HOADONNPP_DDH where ddh_fk = '"+dhId+"' and hoadonnpp_fk in ( select pk_seq from ERP_HOADONNPP where trangthai != 3 ) ";
				
				System.out.println("---- CHECK Đơn hàng có hóa đơn hay không: " + query );
				ResultSet rsDDH = db.get(query);
			
				String ddh_fk = "";
				{
									
					if(rsDDH.next())
					{
						ddh_fk = rsDDH.getString("DDH_FK") == null ? "" : rsDDH.getString("DDH_FK");
					}
				}
				
				if(ddh_fk.length() > 0)
				{
					msg = "Đơn này đã có hóa đơn, bạn không thể xóa đơn hàng"+dhId;
					System.out.println(msg);
					db.getConnection().rollback();
					return;
				}	
					
				{
					//TANG KHO NGUOC LAI
					query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
							" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
							" from " +
							" ( " +
							" 	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
							" 	from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM b on a.pk_seq = b.dondathang_fk " +
							" 	where pk_seq = '" + dhId + "' " +
							" 	group by a.kho_fk, a.nhomkenh_fk, b.sanpham_fk " +
							" ) " +
							" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
							" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.nhomkenh_fk = kho.nhomkenh_fk and kho.NPP_FK = '" + nppId + "'  ";
					
					System.out.println("---1.1 CAP NHAT KHO: " + query);
					if(db.updateReturnInt(query) < 1 )
					{
						msg = "Không thể cập nhật NHAPP_KHO " + query;
						System.out.println(msg);
						db.getConnection().rollback();
						
					}
					
					query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
							" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
							" from " +
							" ( " +
							" 	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, b.SOLO, b.ngayhethan, SUM(b.soluong) as tongxuat  " +
							"	from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk   " +
							"	where a.PK_SEQ = '" + dhId + "'   " +
							"	group by a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, b.SOLO, b.ngayhethan " +
							" ) " +
							" CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
							" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.nhomkenh_fk = kho.nhomkenh_fk and CT.solo = kho.solo and CT.ngayhethan = kho.ngayhethan and kho.NPP_FK = '" + nppId + "'  ";
					System.out.println("---2.1 CAP NHAT KHO: " + query);
					if(db.updateReturnInt(query) < 1 )
					{
						msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
						System.out.println(msg);
						db.getConnection().rollback();
						return;
					}
					
				}
				
				//Mở lại trạng thái đơn hàng về chưa duyệt
				query = "update ERP_DONDATHANGNPP set trangthai = 0, cs_duyet = '0', ss_duyet = '0', ngaymoduyet = getdate() where pk_seq = '" + dhId + "' ";
				if(!db.update(query))
				{
					msg = "Không thể hủy ERP_HOADONNPP " + query;
					System.out.println(msg);
					db.getConnection().rollback();
					return;
					
				}
			
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			System.out.println("Exception: " + e.getMessage());	
			db.update("rollback");
			db.shutDown();
			return ;
		}		
		System.out.println("Chạy xong !!!!!!!!!!!!!");
		return ;
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
