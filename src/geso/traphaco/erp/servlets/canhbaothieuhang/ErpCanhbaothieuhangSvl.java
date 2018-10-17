package geso.traphaco.erp.servlets.canhbaothieuhang;

import geso.traphaco.erp.beans.canhbaothieuhang.*;
import geso.traphaco.erp.beans.canhbaothieuhang.imp.*;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCanhbaothieuhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpCanhbaothieuhangSvl() {
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
	    
	    IErpCanhbaothieuhang obj = new ErpCanhbaothieuhang();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    obj.init();
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanhBaoThieuHang.jsp";
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
	    
	    IErpCanhbaothieuhang obj = new ErpCanhbaothieuhang();
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
	    
	    String tungay = request.getParameter("tungay");
	    if (tungay == null)
	    	tungay = "";
	    obj.setTuNgay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if (denngay == null)
	    	denngay = "";
	    obj.setDenNgay(denngay);
	    
	    String so = request.getParameter("soSO");
	    so = so==null?"":so;
	    obj.setSo(so);
	    
	    String maKH = request.getParameter("maKhachHang");
	    maKH = maKH==null?"":maKH;
	    obj.setMaKH(maKH);
	    
	    String spma = request.getParameter("maSanPham");
	    spma = spma==null?"":spma;
	    obj.setSpMa(spma);
	    
	    String quyCach = request.getParameter("quycach");
	    quyCach = quyCach==null?"":quyCach;
	    obj.setQuyCach(quyCach);
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    String msg = "";
	    if(action.equals("updateLSX"))
	    {
	    	msg = updateLSX(ctyId, userId, request);
		    
	    }
	    System.out.println("____Ket qua cap nhat: " + msg);
	    
	    obj.init();
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanhBaoThieuHang.jsp";
		response.sendRedirect(nextJSP);
	    
	    
	    
	}

	private String updateLSX(String ctyId, String userId, HttpServletRequest request) 
	{
		String msg = "";
		
		String[] spCanhbaoId = request.getParameterValues("spCanhbaoId");
    	String[] spCanhbaoNgay = request.getParameterValues("spCanhbaoNgay");
    	
    	dbutils db = new dbutils();
    	
    	if(spCanhbaoNgay != null)
    	{
    		String query = "";
    		
    		try 
    		{
				db.getConnection().setAutoCommit(false);
				
				for(int i = 0; i < spCanhbaoId.length; i++)
	    		{
	    			String[] lsxID = request.getParameterValues(spCanhbaoId[i] + "." + spCanhbaoNgay[i] + ".updateLSX_ChungTu");
	    			String[] lsxNgayHT = request.getParameterValues(spCanhbaoId[i] + "." + spCanhbaoNgay[i] + ".updateLSX_NgayHT");
	    			String[] lsxSoLuong = request.getParameterValues(spCanhbaoId[i] + "." + spCanhbaoNgay[i] + ".updateLSX_SoLuong");
	    			
	    			if(lsxID != null)
	    			{
	    				for(int j = 0; j < lsxID.length; j++)
	    				{
	    					if(lsxID[j].trim().length() > 0)  //Cap nhat lai ngay hoan thanh
	    					{
	    						query = "Update ERP_LenhSanXuat_Giay set ngaydukienHT = '" + lsxNgayHT[j] + "', nguoisua = '" + userId + "', ngaysua = '" + getDateTime() + "' " +
	    								"where pk_seq = '" + lsxID[j].trim() + "' ";
	    						System.out.println("UPDATE LENH SX : "+query);
	    						if(!db.update(query))
	    						{
	    							msg = "Không thể cập nhật ERP_LenhSanXuat_Giay: " + query;
	    							db.getConnection().rollback();
	    							db.shutDown();
	    							return msg;
	    						}
	    						
	    					}
	    					else  //Tao moi LSX
	    					{
	    						if( lsxNgayHT[j].trim().length() > 0 && lsxSoLuong[j].trim().length() > 0 )
	    						{
	    							//Xac dinh kich ban moi nhat cua SP
	    							String kbsxId = "";
	    							query = "select top(1) b.PK_SEQ from ERP_SANPHAM a inner join ERP_KICHBANSANXUAT_GIAY b on a.ma = b.masanpham " +
	    									"where a.pk_seq = '" + spCanhbaoId[i] + "' " +
	    									"order by b.PK_SEQ desc";
	    							ResultSet rsKB = db.get(query);
	    							if(rsKB != null)
	    							{
	    								if(rsKB.next())
	    								{
	    									kbsxId = rsKB.getString("PK_SEQ");
	    								}
	    								rsKB.close();
	    							}
	    							
	    							System.out.println("__LAY DUOC KICH BAN: " + kbsxId);
	    							if(kbsxId.trim().length() <= 0)
	    							{
	    								return "Sản phẩm bạn chọn chưa có kịch bản sản xuất. Vui lòng kiểm tra lại.";
	    							}
	    							
	    							query = "insert ERP_LenhSanXuat_Giay(KICHBANSANXUAT_FK, CONGTY_FK, SOLUONG, NGAYBATDAU, NGAYDUKIENHT, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA)  " +
	    									"values('" + kbsxId + "', '" + ctyId + "', '" + lsxSoLuong[j].trim() + "', '" + getDateTime() + "', '" + lsxNgayHT[j].trim() + "', '0', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "')";
	    							if(!db.update(query))
		    						{
		    							msg = "Không thể tạo mới ERP_LENHSANXUAT: " + query;
		    							db.getConnection().rollback();
		    							db.shutDown();
		    							return msg;
		    						}
	    							
	    							query = "insert ERP_LENHSANXUAT_SANPHAM(lenhsanxuat_fk, sanpham_fk, soluong)  " +
	    									"select ident_current('ERP_LENHSANXUAT_GIAY'), '" + spCanhbaoId[i] + "', '" + lsxSoLuong[j].trim() + "'";
	    							
	    							if(!db.update(query))
		    						{
		    							msg = "Không thể tạo mới ERP_LENHSANXUAT_SANPHAM: " + query;
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
				//db.shutDown();
			} 
    		catch (Exception e) 
    		{
    			db.update("rollback");
    			//db.shutDown();
				System.out.println("Exception: " + e.getMessage());
				msg = "Exception: " + e.getMessage();
			}
    	}
    	
    	
    	//Check thieu hang
		try
		{
			String query = "SELECT count(kiemtra.SPID) as soDong " +
							"from " +
							"( " +
								"SELECT SP.PK_SEQ AS SPID, SUM(KHO_SP.SOLUONG) AS SoLuong       " +
									"FROM ERP_KHOTT_SANPHAM KHO_SP      " +
									"INNER JOIN ERP_SANPHAM SP on KHO_SP.SANPHAM_FK = SP.PK_SEQ      " +
									"WHERE KHO_SP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where LOAISANPHAM_FK in ( '100005', '100006', '100007') )   " +
									"GROUP BY SP.PK_SEQ     " +
								"union all  " +
									"select c.PK_SEQ as spId, (-1) * sum(b.SOLUONG) as soluong    " +
									"from ERP_DONDATHANG a inner join ERP_DONDATHANG_SP b on a.PK_SEQ = b.DONDATHANG_FK    " +
										"inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ    " +
									"where a.TRANGTHAI <= 3 and b.ngaydukiengiao >= '" + this.getDateTime() + "'  " +
									"group by c.PK_SEQ " +
								" union all  " +
									"select c.PK_SEQ as spId, sum(b.SOLUONG) as SoLuong   " +
									"from ERP_LENHSANXUAT_GIAY a inner join ERP_LENHSANXUAT_SANPHAM b on a.pk_seq = b.lenhsanxuat_fk  " +
									"inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ   " +
									"where a.NGAYDUKIENHT >= '" + this.getDateTime() + "'  " +
									"group by c.PK_SEQ " +
							") " +
							"kiemtra group by kiemtra.SPID " +
							"having SUM(soluong) < 0 ";
			
			ResultSet rsCheck = db.get(query);
			int sodong = 0;
			if(rsCheck.next())
			{
				sodong = rsCheck.getInt("soDong");
			}
			
			
			if(sodong > 0) // THIEU HANG
			{
				//Lay LIST DIEN THOAI
				query = "select SMS, PHONELIST from ERP_CAUHINHSMS where MUCDICHSUDUNG = 'SX' and TRANGTHAI = '1'";
				ResultSet rsPhone = db.get(query);
				if(rsPhone.next())
				{
					String sms = rsPhone.getString("SMS");
					
					String phone = rsPhone.getString("PHONELIST");
					
					if(phone.length() > 0)
					{
						String[] pl = phone.split(";");
						
						for(int i = 0; i < pl.length; i++ )
						{
							if(pl[i].trim().length() > 0)
							{
								query = "insert OutBox(ID, PhoneNumber, Content, Contenttype, SendDate, Status) " +
										"values ('1', '" + pl[i] + "', N'" + sms + "', '0', '" + getDateTime() + "', '0')";
								db.update(query);
							}
						}
					}
				}
				rsPhone.close();	
			}
			else  //DU HANG -- > Kiem TRA NGUYEN LIEU
			{
				query = "select count(kiemtra.SPID) as soDong  " +
						"from  " +
						"(  " +
							"SELECT SP.PK_SEQ AS SPID, SUM(KHO_SP.SOLUONG) AS SoLuong       " +
							"FROM ERP_KHOTT_SANPHAM KHO_SP      " +
								"INNER JOIN ERP_SANPHAM SP on KHO_SP.SANPHAM_FK = SP.PK_SEQ      " +
							"WHERE KHO_SP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where LOAISANPHAM_FK in ( '100000', '100002', '100003', '100009', '100013', '100014', '100015', '100016', '100017' ) )     " +
							"GROUP BY SP.PK_SEQ  " +
						"union all  " +
							"select c.PK_SEQ as spId, sum(b.SOLUONG - ISNULL(nhanhang.SOLUONGNHAN, 0 ) ) as SoLuong    " +
							"from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK     " +
								"inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ     " +
							"left join  " +
							"(  " +
								"select a.MUAHANG_FK, b.NGAYNHANDUKIEN, b.SANPHAM_FK, b.SOLUONGNHAN    " +
								"from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK    " +
								"where TRANGTHAI in (1, 2) and b.SANPHAM_FK is not null and b.NGAYNHANDUKIEN >= '" + getDateTime() + "'   " +
							")  " +
							"nhanhang on a.PK_SEQ = nhanhang.MUAHANG_FK and c.PK_SEQ = nhanhang.SANPHAM_FK and b.NGAYNHAN = nhanhang.NGAYNHANDUKIEN   " +
							"where a.LOAISANPHAM_FK in ('100000', '100002', '100003', '100009', '100013', '100014', '100015', '100016', '100017') and b.NGAYNHAN >= '" + getDateTime() + "'    " +
							"group by c.PK_SEQ " +
						"union all  " +
							"select VT.PK_SEQ as spId,   " +
								"(-1) * SUM ( ( LSX.SOLUONG * isnull(SP.SOLUONGBANTP, 1) / isnull(SP.SOLUONGTP, 1) ) * BOM.TiLeBOM ) as SoLuong    " +
							"from ERP_LENHSANXUAT_GIAY LSX inner join ERP_LENHSANXUAT_SANPHAM LSX_SP on LSX.PK_SEQ = LSX_SP.Lenhsanxuat_fk    " +
							"inner join ERP_SANPHAM SP on LSX_SP.SANPHAM_FK = SP.PK_SEQ  " +
							"inner join   " +
							"(  " +
								"select c.pk_seq as DMID, a.pk_seq as spId,     " +
								"case d.vattu_fk when - 1 then   " +
									"( case ( select distinct loaisanpham_fk from ERP_SanPham where ma = d.mavattu )     " +
									"when 100013 then ( select top(1) pk_seq from ERP_SanPham where rong = ( select max(NO_OF_UP) from erp_no_of_slitting where FG_WIDTH = a.rong ) and ma = d.mavattu )    " +
								"else ( select top(1) pk_seq from ERP_SanPham where rong = ( select max(NO_OF_UP) - 2 from erp_no_of_slitting where FG_WIDTH = a.rong ) and ma = d.mavattu ) end )     " +
								"else d.VATTU_FK	end  as vattu_fk,  ( d.SOLUONG / c.SOLUONGCHUAN ) as TiLeBOM   " +
								"from ERP_SANPHAM a inner join ERP_MAKETOAN b on a.maketoan_fk = b.pk_seq      " +
								"inner join ERP_DANHMUCVATTU c on 'S' + b.ma = c.MaVatTu    " +
								"inner join ERP_DANHMUCVATTU_VATTU d on c.pk_seq = d.danhmucvt_fk      " +
								"where c.hieuluctu <= '" + getDateTime() + "' and '" + getDateTime() + "' <= c.hieulucden and c.trangthai = '1' and c.sudung = 1    " +
							" )  " +
							" BOM on SP.PK_SEQ = BOM.spId inner join ERP_SANPHAM VT on BOM.vattu_fk = VT.PK_SEQ  " +
							"where LSX.NGAYDUKIENHT >= '" + getDateTime() + "'   " +
							" group by VT.PK_SEQ " +
						") " +
						"kiemtra  " +
						"group by kiemtra.SPID  " +
						"having SUM(soluong) < 0  ";
		
				rsCheck = db.get(query);
				sodong = 0;
				if(rsCheck.next())
				{
					sodong = rsCheck.getInt("soDong");
				}
				
				
				if(sodong > 0)
				{
					//Lay LIST DIEN THOAI
					query = "select SMS, PHONELIST from ERP_CAUHINHSMS where MUCDICHSUDUNG = 'SX_MH' and TRANGTHAI = '1'";
					ResultSet rsPhone = db.get(query);
					if(rsPhone.next())
					{
						String sms = rsPhone.getString("SMS");
						sms = sms.replaceAll("(__)",  sodong > 0 ? "dan toi thieu NL" : "NL du dap ung YC " );
						
						String phone = rsPhone.getString("PHONELIST");
						
						if(phone.length() > 0)
						{
							String[] pl = phone.split(";");
							
							for(int i = 0; i < pl.length; i++ )
							{
								if(pl[i].trim().length() > 0)
								{
									query = "insert OutBox(ID, PhoneNumber, Content, Contenttype, SendDate, Status) " +
											"values ('1', '" + pl[i] + "', N'" + sms + "', '0', '" + getDateTime() + "', '0')";
									db.update(query);
								}
							}
						}
					}
					rsPhone.close();	
				}
			}
		}
    	catch (Exception e) {
			
    		System.out.println("Loi gui SMS: " + e.getMessage());
		}
    	
    	db.shutDown();
    	return msg;
	}
	
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
