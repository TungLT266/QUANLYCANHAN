package geso.traphaco.erp.servlets.canhbaothieuhang;

import geso.traphaco.erp.beans.canhbaothieuhang.IErpCanhbaothieunguyenlieu;
import geso.traphaco.erp.beans.canhbaothieuhang.imp.ErpCanhbaothieunguyenlieu;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

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

public class ErpCanhbaothieunguyenlieuSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpCanhbaothieunguyenlieuSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IErpCanhbaothieunguyenlieu obj = new ErpCanhbaothieunguyenlieu();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    obj.init();
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanhBaoThieuNguyenLieu.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IErpCanhbaothieunguyenlieu obj = new ErpCanhbaothieunguyenlieu();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String thangId = request.getParameter("thangId");
	    if (thangId == null)
	    	thangId = "";
	    obj.setThangId(thangId);
	    
	    String namId = request.getParameter("namId");
	    if (namId == null)
	    	namId = "";
	    obj.setNamId(namId);
	    
	    String viewMode = request.getParameter("viewMode");
	    if (viewMode == null)
	    	viewMode = "";
	    obj.setViewMode(viewMode);
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    String msg = "";
	    if(action.equals("updatePO"))
	    {
		    if(viewMode.equals("1"))
		    {
		    	msg = updatePO(userId, ctyId, thangId, namId, request);
		    }
	    }
	    System.out.println("____Ket qua cap nhat: " + msg);
	    
	    
	    obj.init();
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanhBaoThieuNguyenLieu.jsp";
		response.sendRedirect(nextJSP);
	    
	}
	
	
	private String updatePO(String userId, String ctyId, String thang, String nam, HttpServletRequest request) 
	{
		String msg = "";
		
		String[] spCanhbaoId = request.getParameterValues("spCanhbaoId");
    	String[] spCanhbaoNgay = request.getParameterValues("spCanhbaoNgay");
    	
    	if(spCanhbaoNgay != null)
    	{
    		dbutils db = new dbutils();
    		String query = "";
    		
    		try 
    		{
				db.getConnection().setAutoCommit(false);
				
				for(int i = 0; i < spCanhbaoId.length; i++)
	    		{
	    			String[] poID = request.getParameterValues(spCanhbaoId[i] + "." + spCanhbaoNgay[i] + ".updatePO_ChungTu");
	    			String[] poNgayGiao = request.getParameterValues(spCanhbaoId[i] + "." + spCanhbaoNgay[i] + ".updatePO_NgayGiao");
	    			String[] poNgayGiaoOLD = request.getParameterValues(spCanhbaoId[i] + "." + spCanhbaoNgay[i] + ".updatePO_NgayGiaoOLD");
	    			String[] poSpIdDat = request.getParameterValues(spCanhbaoId[i] + "." + spCanhbaoNgay[i] + ".updatePO_SpIdDat");
	    			String[] poSoLuong = request.getParameterValues(spCanhbaoId[i] + "." + spCanhbaoNgay[i] + ".updatePO_SoLuong");
	    			String[] poNcc = request.getParameterValues(spCanhbaoId[i] + "." + spCanhbaoNgay[i] + ".updatePO_NccId");
	    			
	    			if(poID != null)
	    			{
	    				for(int j = 0; j < poID.length; j++)
	    				{
	    					if(poID[j].trim().length() > 0)  //Cap nhat lai ngay nhan
	    					{
	    						query = "update ERP_MUAHANG_SP set NGAYNHAN = '" + poNgayGiao[j].trim() + "' " +
	    								"where SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where ma = '" + spCanhbaoId[i].trim() + "' ) and MUAHANG_FK = '" + poID[j].trim() + "' and NGAYNHAN = '" + poNgayGiaoOLD[j].trim() + "' ";
	    						if(!db.update(query))
	    						{
	    							msg = "Không thể cập nhật ERP_MUAHANG_SP: " + query;
	    							db.getConnection().rollback();
	    							db.shutDown();
	    							return msg;
	    						}
	    						
	    						//Neu da co nhan hang ma chua chot
	    						query = "update ERP_NHANHANG_SANPHAM set NGAYNHANDUKIEN = '" + poNgayGiao[j].trim() + "' " +
										"where SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where ma = '" + spCanhbaoId[i].trim() + "' ) " +
												"and NHANHANG_FK in ( select pk_seq from ERP_NhanHang where MUAHANG_FK = '" + poID[j].trim() + "' and trangthai in (1, 2) ) " +
														"and NGAYNHANDUKIEN = '" + poNgayGiaoOLD[j].trim() + "' ";
								if(!db.update(query))
								{
									msg = "Không thể cập nhật ERP_NHANHANG_SANPHAM: " + query;
									db.getConnection().rollback();
									db.shutDown();
									return msg;
								}
	    						
	    					}
	    					else  //Tao moi PO 
	    					{
	    						if( poNgayGiao[j].trim().length() > 0 && poSoLuong[j].trim().length() > 0 && poSpIdDat[j].trim().length() > 0 )
	    						{
	    							System.out.println("___Tao mua hang toi day...: ");
	    							query = "insert ERP_MUAHANG(NGAYMUA, DONVITHUCHIEN_FK, NHACUNGCAP_FK, LOAIHANGHOA_FK, LOAISANPHAM_FK, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, TYPE, CONGTY_FK, TONGTIENBVAT, VAT, TONGTIENAVAT, NGUONGOCHH, TIENTE_FK, TyGiaQuyDoi, DungSai, NOTE )  " +
			    							"select '" + poNgayGiao[j].trim() + "', '100002', '" + poNcc[j].trim() + "', '0', LOAISANPHAM_FK, 0, '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '0', '" + ctyId + "', '0', '0', '0', 'TN', '100000', '1', '0', N'Được chuyển từ cảnh báo thiếu nguyên liệu tháng ( " + thang + " ) - Năm ( " +  nam + " ) '   " +
			    							"from ERP_SANPHAM where PK_SEQ = '" + poSpIdDat[j].trim() + "'";
	    							System.out.println("___Tao mua hang: " + query);
	    							if(!db.update(query))
		    						{
		    							msg = "Không thể tạo mới ERP_MUAHANG: " + query;
		    							db.getConnection().rollback();
		    							db.shutDown();
		    							return msg;
		    						}
	    							
	    							
	    							query = "insert ERP_MUAHANG_SP(MUAHANG_FK, SANPHAM_FK, DIENGIAI, SOLUONG, NGAYNHAN, DONVI, DONGIA, DONGIAVIET, THANHTIENVIET)  " +
			    							"select IDENT_CURRENT('ERP_MuaHang'), a.pk_seq, a.ten, '" + poSoLuong[j].trim() + "', '" + poNgayGiao[j].trim() + "', isnull(b.Donvi, ''), '0', '0', '0'   " +
			    							"from ERP_SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
			    							"where a.PK_SEQ = '" + poSpIdDat[j].trim() + "'";
	    							System.out.println("___Tao mua hang - SP: " + query);
	    							if(!db.update(query))
		    						{
		    							msg = "Không thể tạo mới ERP_MUAHANG_SP: " + query;
		    							db.getConnection().rollback();
		    							db.shutDown();
		    							return msg;
		    						}
	    							
	    						}
	    					}
	    				}
	    			}
	    		}
				
				db.getConnection().commit();
				db.shutDown();
			} 
    		catch (Exception e) 
    		{
    			db.update("rollback");
    			db.shutDown();
				System.out.println("Exception: " + e.getMessage());
				msg = "Exception: " + e.getMessage();
			}
    		finally
    		{
    			db.shutDown();
    		}
    	}
    	
    	return msg;
	}
	
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	

}
