package geso.traphaco.erp.servlets.nhanhang;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.*
import geso.traphaco.erp.beans.nhanhang.IErpNhanhang;
import geso.traphaco.erp.beans.nhanhang.IErpNhanhangList;
import geso.traphaco.erp.beans.nhanhang.imp.ErpNhanhang;
import geso.traphaco.erp.beans.nhanhang.imp.ErpNhanhangList;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhanhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpNhanhangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpNhanhangList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String nhId = util.getId(querystring);
	    
	    obj = new ErpNhanhangList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(nhId);
	    	if(msg.length() > 0)
	    		obj.setmsg(msg);
	    	else //xoa thanh cong
	    	{
	    		String poId = request.getParameter("poId");
	    		IErpNhanhang nhanhang = new ErpNhanhang(nhId);
	    		nhanhang.init();
	    		nhanhang.updateDonmuahang(poId);
	    	}
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		String lhhId = request.getParameter("lhhId");
	    		if(lhhId == null)
	    			lhhId = "";
	    		
	    		String msg = ChotNhanHang(nhId, lhhId);
	    		obj.setmsg(msg);
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("congtyId", obj.getCongtyId());
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHang.jsp";
		response.sendRedirect(nextJSP);
	}

	private String ChotNhanHang(String nhId, String lhhId) 
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			//Sau nay phai them buoc check xem taikhoan do da co khoasothang chua
			String ngaychot = "";
			String sql = "select a.ngaychot, c.taikhoan_fk " +
						 "from ERP_NhanHang a inner join ERP_Muahang b on a.muahang_fk = b.pk_seq " +
						 	"inner join erp_nhacungcap c on b.nhacungcap_fk = c.pk_seq where a.pk_seq = '" + nhId + "'";
			
			String taikhoanco_fk = "";
			ResultSet rs = db.get(sql);
			if(rs.next())
			{
				ngaychot = rs.getString("ngaychot");
				taikhoanco_fk = rs.getString("taikhoan_fk");
			}
			
			String nam = ngaychot.substring(0, 4);
			String thang = ngaychot.substring(5, 7);
			
			
			if(lhhId.equals("1")) //Tai san co dinh
			{
				//Lay tai khoan No trong bang config
				String query = "select TAISAN_FK, SOLUONGNHAN, DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET from ERP_NHANHANG_SANPHAM where NHANHANG_FK = '" + nhId + "'";
				ResultSet rsSp = db.get(query);
				
				if(rsSp != null)
				{
					while(rsSp.next())
					{
						String taisan_fk = rsSp.getString("TAISAN_FK");
						String soluongNhan = rsSp.getString("SOLUONGNHAN");
						String dongia = rsSp.getString("DONGIA");
						String tiente = rsSp.getString("TIENTE_FK");
						String dongiaViet = rsSp.getString("DONGIAVIET");
						
						//tang so luong, cap nhat don gia, thanh tien
						query = "Update ERP_TAISANCODINH set SoLuong = SoLuong + " + soluongNhan + ", DonGia = '" + dongia + "', ThanhTien = ( SoLuong + " + soluongNhan + " ) * " + dongia + " " +
								"where pk_seq = '" + taisan_fk + "'";
						if(!db.update(query))
						{
							rsSp.close();
							db.getConnection().rollback();
							
							System.out.println("1.Loi: " + query);
							return "1.Không thể cập nhật ERP_TAISANCODINH: " + query;
						}
						
						
						query = "select b.taikhoan_fk from ERP_TAISANCODINH a inner join Erp_NHOMTAISAN b on a.nhomtaisan_fk = b.pk_seq where a.pk_seq = '" + taisan_fk + "'";
						
						ResultSet rsTKNo = db.get(query);
						String taikhoanNo = "";
						if(rsTKNo.next())
						{
							taikhoanNo = rsTKNo.getString("taikhoan_fk");
						}
						rsTKNo.close();
						
						if(taikhoanNo.trim().length() < 0)
						{
							rsSp.close();
							db.getConnection().rollback();
							return "Tài sản trong nhận hàng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nến.";
						}
						
						//Kiem tra xem da cao tai khoan nay trong thang chua
						query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
								"where taikhoankt_fk = '" + taikhoanNo + "' and nguyente_fk = '" + tiente + "' and thang = '" + thang + "' and nam = '" + nam + "'";
						
						rsTKNo = db.get(query);
						int sodong = 0;
						if(rsTKNo.next())
						{
							sodong = rsTKNo.getInt("sodong");
						}
						rsTKNo.close();
						
						
						if(sodong > 0) //daco
						{
							query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + dongiaViet + "*" + soluongNhan + ", " +
																" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongia + "*" + soluongNhan + 
									" where taikhoankt_fk = '" + taikhoanNo + "' and nguyente_fk = '" + tiente + "' and thang = '" + thang + "' and nam = '" + nam + "'";
						}
						else
						{
							query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
									"values('" + taikhoanNo + "', '0', " + dongiaViet + "*" + soluongNhan + ", '" + tiente + "', " + dongia + "*" + soluongNhan + ", '" + thang + "', '" + nam + "')";
						}
						
						//System.out.println("5.Cap nhat: + query");
						if(!db.update(query))
						{
							rsSp.close();
							db.getConnection().rollback();
							
							System.out.println("1.Loi: " + query);
							return "6.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
						}
						
						
						query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + dongiaViet + "*" + soluongNhan + ", " +
														" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongia + "*" + soluongNhan + 
								" where taikhoankt_fk = '" + taikhoanco_fk + "' and nguyente_fk = '" + tiente + "' and thang = '" + thang + "' and nam = '" + nam + "'";
						
						if(!db.update(query))
						{
							rsSp.close();
							db.getConnection().rollback();
							
							System.out.println("2.Loi: " + query);
							return "7.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
						}
					}
					rsSp.close();
				}
				
				//Nhan hang se chuyen sang hoan tat
				query = "Update ERP_NhanHang set trangthai = '2', giochot = '" + getTime() + "' where pk_seq = '" + nhId + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Không thể chôt nhận hàng: " + query;
				}
				
			}
			else
			{
				if(lhhId.equals("2")) //Chi phi dich vu
				{
					//Lay tai khoan No trong bang config
					String query = "select CHIPHI_FK, SOLUONGNHAN, DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET " +
								  "from ERP_NHANHANG_SANPHAM where NHANHANG_FK = '" + nhId + "'";
					System.out.println("__Khoi tao: " + query);
					
					ResultSet rsSp = db.get(query);
					
					if(rsSp != null)
					{
						while(rsSp.next())
						{
							String chiphi_fk = rsSp.getString("CHIPHI_FK");
							String soluongNhan = rsSp.getString("SOLUONGNHAN");
							String dongia = rsSp.getString("DONGIA");
							String tiente = rsSp.getString("TIENTE_FK");
							String dongiaViet = rsSp.getString("DONGIAVIET");
							
							query = "select TAIKHOAN_FK, TTCHIPHI_FK from ERP_NHOMCHIPHI where PK_SEQ = '" + chiphi_fk + "'";
							System.out.println("__Lay tai khoan: " + query);
							
							ResultSet rsTKNo = db.get(query);
							String taikhoanNo = "";
							String ttchiphi_fk = "";
							if(rsTKNo.next())
							{
								taikhoanNo = rsTKNo.getString("taikhoan_fk");
								ttchiphi_fk = rsTKNo.getString("TTCHIPHI_FK");
							}
							rsTKNo.close();
							
							if(taikhoanNo.trim().length() < 0)
							{
								rsSp.close();
								db.getConnection().rollback();
								return "Chi phí trong nhận hàng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nến.";
							}
							
							//Kiem tra xem da cao tai khoan nay trong thang chua
							query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
									"where taikhoankt_fk = '" + taikhoanNo + "' and nguyente_fk = '" + tiente + "' and thang = '" + thang + "' and nam = '" + nam + "'";
							
							rsTKNo = db.get(query);
							int sodong = 0;
							if(rsTKNo.next())
							{
								sodong = rsTKNo.getInt("sodong");
							}
							rsTKNo.close();
							
							
							if(sodong > 0) //daco
							{
								query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + dongiaViet + "*" + soluongNhan + ", " +
																	" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongia + "*" + soluongNhan + 
										" where taikhoankt_fk = '" + taikhoanNo + "' and nguyente_fk = '" + tiente + "' and thang = '" + thang + "' and nam = '" + nam + "'";
							}
							else
							{
								query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
										"values('" + taikhoanNo + "', '0', " + dongiaViet + "*" + soluongNhan + ", '" + tiente + "', " + dongia + "*" + soluongNhan + ", '" + thang + "', '" + nam + "')";
							}
							
							//System.out.println("5.Cap nhat: + query");
							if(!db.update(query))
							{
								rsSp.close();
								db.getConnection().rollback();
								
								System.out.println("1.Loi: " + query);
								return "6.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
							}
							
							
							//Tai khoan co
							query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + dongiaViet + "*" + soluongNhan + ", " +
															" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongia + "*" + soluongNhan + 
									" where taikhoankt_fk = '" + taikhoanco_fk + "' and nguyente_fk = '" + tiente + "' and thang = '" + thang + "' and nam = '" + nam + "'";
							
							if(!db.update(query))
							{
								rsSp.close();
								db.getConnection().rollback();
								
								System.out.println("2.Loi: " + query);
								return "7.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
							}
							
							
							//Trung tam chi phi
							query = "select count(*) as sodong from ERP_TRUNGTAMCHIPHI_NGANSACH " +
									"where TTCP_FK = '" + ttchiphi_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
							
							rsTKNo = db.get(query);
							sodong = 0;
							if(rsTKNo.next())
							{
								sodong = rsTKNo.getInt("sodong");
							}
							rsTKNo.close();
							
							
							if(sodong > 0) //daco
							{
								query = "update ERP_TRUNGTAMCHIPHI_NGANSACH set NGANSACH = NGANSACH + ( " + dongiaViet + "*" + soluongNhan + " ) " +
										" where TTCP_FK = '" + ttchiphi_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
							}
							else
							{
								query = "insert ERP_TRUNGTAMCHIPHI_NGANSACH(TTCP_FK, THANG, NAM, NGANSACH) " +
										"values('" + ttchiphi_fk + "', '" + thang + "', '" + nam + "', " + dongiaViet + "*" + soluongNhan + ")";
							}
							
							System.out.println("5.Cap nhat ERP_TRUNGTAMCHIPHI_NGANSACH: " + query);
							if(!db.update(query))
							{
								rsSp.close();
								db.getConnection().rollback();
								
								System.out.println("1.Loi: " + query);
								return "6.Không thể cập nhật ERP_TRUNGTAMCHIPHI_NGANSACH: " + query;
							}
							
							
							//Tang thuc chi
							query = " update ERP_LAPNGANSACH_CHIPHI  set THUCCHI = THUCCHI + " + soluongNhan + " * " + dongiaViet +
									" where LAPNGANSACH_FK in (select pk_seq from ERP_LAPNGANSACH where NAM = '" + nam + "' and TRANGTHAI = '1' ) " +
										"and donvithuchien_fk = ( select donvithuchien_fk from ERP_NhanHang where pk_seq = '" + nhId + "' ) and chiphi_fk = '" + chiphi_fk + "' ";
							if(!db.update(query))
							{
								rsSp.close();
								db.getConnection().rollback();
								
								System.out.println("1.Loi: " + query);
								return "7.Không thể cập nhật ERP_LAPNGANSACH_CHIPHI: " + query;
							}
							
						}
						rsSp.close();
					}
					
					//Nhan hang se chuyen sang hoan tat
					query = "Update ERP_NhanHang set trangthai = '2', giochot = '" + getTime() + "' where pk_seq = '" + nhId + "'";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						return "Không thể chôt nhận hàng: " + query;
					}
				}
				else
				{
					String query = "Update ERP_NhanHang set trangthai = '1', giochot = '" + getTime() + "' where pk_seq = '" + nhId + "'";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						return "Không thể chôt nhận hàng: " + query;
					}
				}
			}
			
			
			
			db.getConnection().commit();
			db.shutDown();
			
			return "";
		} 
		catch (Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) {}
			
			db.shutDown();
			
			return "Không thể chôt nhận hàng: " + e.getMessage();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNhanhangList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpNhanhang nhBean = new ErpNhanhang();
	    	nhBean.setUserId(userId);
	    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	nhBean.createRs();
    		
	    	session.setAttribute("nhBean", nhBean);
	    	session.setAttribute("spList", "");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpNhanhangList();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHang.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpNhanhangList();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
			
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHang.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpNhanhangList obj)
	{
		Utility util=new Utility();
		String query = " select a.PK_SEQ as nhId, a.SOHOADON, a.NGAYNHAN, b.TEN as dvthTen, b.PREFIX + c.PREFIX + cast(c.PK_SEQ as varchar(10)) as PoId, " +
				"b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, " +
				"a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, a.loaihanghoa_fk  " +
		"from erp_nhanhang a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ inner join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ  " +
			"inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  " +
			"where a.congty_fk = '" + obj.getCongtyId() + "' and b.pk_seq in  "+ util.quyen_donvithuchien(obj.getUserId());
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String dvthId = request.getParameter("dvth");
		if(dvthId == null)
			dvthId = "";
		obj.setDvthId(dvthId);
		
		String soPo = request.getParameter("sopo");
		if(soPo == null)
			soPo = "";
		obj.setSoPO(soPo);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		if(tungay.length() > 0)
			query += " and a.ngaymua >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaymua <= '" + denngay + "'";
		
		if(dvthId.length() > 0)
			query += " and b.pk_seq = '" + dvthId + "'";
		
		if(soPo.length() > 0)
		{
			query += " and b.PREFIX + c.PREFIX + cast(c.pk_seq as varchar(10)) like '%" + soPo + "%' ";
		}
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		
		//query += " order by a.NGAYNHAN desc, a.trangthai asc, a.pk_seq desc;";
		return query;
	}
	
	private String Delete(String nhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "select COUNT(*) as sodong from ERP_NHAPKHO where SOPHIEUNHAPHANG = '" + nhId + "'";
			//System.out.println("Query mua hang: " + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			}
			
			//System.out.println("So dong la: " + sodong + "\n");
			
			if(sodong > 0)
			{
				return "Nhận hàng này đã có nhập kho, bạn phải xóa nhập kho trước khi xóa đơn nhập hàng này";
			}
			
			
			query = "update ERP_NHANHANG set trangthai = '3' where pk_seq = '" + nhId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể cập nhật ERP_NHANHANG: " + query;
			}
			
			query = "update ERP_MUAHANG set trangthai = '1' where pk_seq = ( select muahang_fk from ERP_NhanHang where pk_seq = '" + nhId + "' ) ";
			System.out.println("___Cap nhat mua hang: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể cập nhật ERP_MUAHANG: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (Exception e)
		{ 
			db.shutDown(); 
			return "Khong the xoa don mua hang"; 
		}
		
	}
	

	private String getTime()
	{
		Date date = new Date();
	    SimpleDateFormat simpDate;

	    //format 24h
	    simpDate = new SimpleDateFormat("kk:mm:ss");
	    
	    return simpDate.format(date);
	}
	
}
