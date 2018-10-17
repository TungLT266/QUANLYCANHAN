package geso.traphaco.center.servlets.nhapkho;

import geso.traphaco.center.db.sql.dbutils;

import geso.traphaco.center.db.sql.dbutils_syn;
import geso.traphaco.center.util.Utility;

import geso.traphaco.center.beans.nhapkho.IErpDontrahang;
import geso.traphaco.center.beans.nhapkho.IErpDontrahangList;
import geso.traphaco.center.beans.nhapkho.imp.ErpDontrahang;
import geso.traphaco.center.beans.nhapkho.imp.ErpDontrahangList;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDontrahangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDontrahangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDontrahangList obj;
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
	    
	    String lsxId = util.getId(querystring);
	    obj = new ErpDontrahangList();
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = this.DeleteChuyenKho(lsxId);
			obj.setMsg(msg);
	    }
	    else if(action.equals("unchot"))
	    {
	    	String msg = this.unchot_ChuyenKho(lsxId);
			obj.setMsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		String msg = this.Chot(lsxId);
    			obj.setMsg(msg);
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonTraHang.jsp";
		response.sendRedirect(nextJSP);
	}

	private String Chot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "";
			query="select count(*) sl from ERP_DONTRAHANG_SANPHAM where  tienvat is null and  dontrahang_fk="+lsxId;
			ResultSet rs=db.get(query);
			rs.next();
			int sl=rs.getInt("sl");
			rs.close();
			if(sl>0)
			{
				msg = "Vui lòng phân bổ tiền vat trên đơn trả hàng ";
				db.getConnection().rollback();
				return msg;
			}
			query = "update ERP_DONTRAHANG set trangthai = '1' where pk_seq = '" + lsxId + "' and trangthai=0";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Chốt không thành công ! Vui lòng kiểm tra trạng thái chứng từ ";
				db.getConnection().rollback();
				return msg;
			}
			
			/*query="select isnull(isktrukho,0) isktrukho,isnull(from_dontrahang,0) as  from_dontrahang  from ERP_DONTRAHANG where pk_seq="+lsxId;
			rs=db.get(query);
			rs.next();
			String dontrahang_fk=rs.getString("from_dontrahang");
			String isktrukho=rs.getString("isktrukho");
			rs.close();
			if(!dontrahang_fk.equals("0"))
			{
				Utility util = new Utility();
				query =
						" select count(*)   as SoDong,a.TrucThuoc_FK ,c.DUNGCHUNGKENH, a.npp_fk " +
						" from DonTraHang a inner join DonTraHang_SP b on a.pk_seq = b.dontrahang_Fk " +
						" 		inner join NHAPHANPHOI c on c.PK_SEQ=a.NPP_FK " +
						" where dontrahang_Fk = '" + dontrahang_fk + "' and a.trangthai in (0,1) " +
						" group by a.TrucThuoc_FK, c.DUNGCHUNGKENH, a.npp_fk " ;
				
				ResultSet rs1 = db.get(query);
				String dungchungKENH = "0";
				 while(rs1.next())
				 {
					
					 dungchungKENH = rs1.getString("dungchungkenh");
				
				 }
				 String sqlKENH = "";
				 if(dungchungKENH.equals("1"))
						sqlKENH = " 100025 as kbh_fk "; //LAY KENH OTC
					else
						sqlKENH = " A.kbh_fk ";
				 
				 	query=	" 	select a.ngaytra,b.ngaynhapkho,a.kho_fk as kho_fk, a.npp_fk, "+sqlKENH+", b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat,b.NgayHetHan  " +
				 			" 	from DonTraHang a inner join DonTraHang_SP b on a.pk_seq = b.dontrahang_Fk " +
				 			" 	where dontrahang_Fk = '" + dontrahang_fk + "' " +
				 			" 	group by a.kho_fk, a.npp_fk, a.kbh_fk, b.solo, b.sanpham_fk,b.NgayHetHan, a.ngaytra,b.ngaynhapkho " ;
				 	
				 	ResultSet nkRs= db.get(query);
				 	while (nkRs.next())
				 	{
				 		String kho_fk=nkRs.getString("kho_fk");
						String npp_fk=nkRs.getString("npp_fk");	
						String kbh_fk=nkRs.getString("kbh_fk");
						String sanpham_fk=nkRs.getString("sanpham_fk");
						String solo=nkRs.getString("solo");
						Double tongxuat=nkRs.getDouble("tongxuat");
						String NgayHetHan=nkRs.getString("NgayHetHan");
						String ngaynhapkho=nkRs.getString("ngaynhapkho");
						String ngaytra=nkRs.getString("ngaytra");
						 
						if(isktrukho.equals("0"))
						{
							msg=util.Update_NPP_Kho_Sp_Chitiet(ngaytra, "Đơn trả hàng về NCC-TT duyệt", db, kho_fk, sanpham_fk, npp_fk, kbh_fk, solo, NgayHetHan, ngaynhapkho, (-1)*tongxuat, (-1)*tongxuat, 0.0,0.0, 0.0);
							if(msg.length()>0)
							{
								db.getConnection().rollback();
								return msg;
							}
							msg=util.Update_NPP_Kho_Sp(ngaytra, "Đơn trả hàng về NCC-UPDATE", db, kho_fk, sanpham_fk, npp_fk, kbh_fk, (-1)*tongxuat,  (-1)*tongxuat, 0.0, 0.0);
							if(msg.length()>0)
							{
								db.getConnection().rollback();
								return msg;
							}
						}
						
				 	}

				query = "update DonTraHang set trangthai = '2' where pk_seq = '" + dontrahang_fk + "'";
				if(!db.update(query))
				{
					msg = "Khong the chot: " + query;
					db.getConnection().rollback();
					return msg;
				}
			}*/
				
			
			
			db.getConnection().commit();
			
			//Dồng bộ qua ERP
			db.getConnection().setAutoCommit(true);
			
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
		
			db.getConnection().setAutoCommit(false);
			String query = "";
			
			query = "update ERP_DONTRAHANG set trangthai = '3' where pk_seq = '" + lsxId + "' and trangthai=0";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Xóa không thành công ! Vui lòng kiểm tra trạng thái chứng từ ";
				db.getConnection().rollback();
			
				return msg;
			}
			
			
			query="select isnull(isktrukho,0) isktrukho,isnull(from_dontrahang,0) as  from_dontrahang  from ERP_DONTRAHANG where pk_seq="+lsxId;
			ResultSet rs=db.get(query);
			rs.next();
			String dontrahang_fk=rs.getString("from_dontrahang");
			String isktrukho=rs.getString("isktrukho");
			rs.close();
			if(!dontrahang_fk.equals("0"))
			{
				
				
				
				
			
					
					
					Utility util = new Utility();
					query =
							" select count(*)   as SoDong,a.TrucThuoc_FK ,c.DUNGCHUNGKENH, a.npp_fk " +
							" from DonTraHang a inner join DonTraHang_SP b on a.pk_seq = b.dontrahang_Fk " +
							" 		inner join NHAPHANPHOI c on c.PK_SEQ=a.NPP_FK " +
							" where dontrahang_Fk = '" + dontrahang_fk + "' and a.trangthai in (0,1) " +
							" group by a.TrucThuoc_FK, c.DUNGCHUNGKENH, a.npp_fk " ;
					
					ResultSet rs1 = db.get(query);
					String dungchungKENH = "0";
					 while(rs1.next())
					 {
						
						 dungchungKENH = rs1.getString("dungchungkenh");
					
					 }
					 String sqlKENH = "";
					 if(dungchungKENH.equals("1"))
							sqlKENH = " 100025 as kbh_fk "; //LAY KENH OTC
						else
							sqlKENH = " A.kbh_fk ";
					 
					 	query=	" 	select a.ngaytra,b.ngaynhapkho,a.kho_fk as kho_fk, a.npp_fk, "+sqlKENH+", b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat,b.NgayHetHan  " +
					 			" 	from DonTraHang a inner join DonTraHang_SP b on a.pk_seq = b.dontrahang_Fk " +
					 			" 	where dontrahang_Fk = '" + dontrahang_fk + "' " +
					 			" 	group by a.kho_fk, a.npp_fk, a.kbh_fk, b.solo, b.sanpham_fk,b.NgayHetHan, a.ngaytra,b.ngaynhapkho " ;
					 	
					 	ResultSet nkRs= db.get(query);
					 	while (nkRs.next())
					 	{
					 		String kho_fk=nkRs.getString("kho_fk");
							String npp_fk=nkRs.getString("npp_fk");	
							String kbh_fk=nkRs.getString("kbh_fk");
							String sanpham_fk=nkRs.getString("sanpham_fk");
							String solo=nkRs.getString("solo");
							Double tongxuat=nkRs.getDouble("tongxuat");
							String NgayHetHan=nkRs.getString("NgayHetHan");
							String ngaynhapkho=nkRs.getString("ngaynhapkho");
							String ngaytra=nkRs.getString("ngaytra");
							
							/*msg=util.Update_NPP_Kho_Sp_Chitiet(ngaytra, "Đơn trả hàng về NCC-TT duyệt", db, kho_fk, sanpham_fk, npp_fk, kbh_fk, solo, NgayHetHan, ngaynhapkho, tongxuat, 0,tongxuat,0.0, 0.0);
							if(msg.length()>0)
							{
								db.getConnection().rollback();
								dbSysn.getConnection().rollback();
								return msg;
							}
							msg=util.Update_NPP_Kho_Sp(ngaytra, "Đơn trả hàng về NCC-UPDATE", db, kho_fk, sanpham_fk, npp_fk, kbh_fk, tongxuat,  tongxuat,0, 0.0);
							if(msg.length()>0)
							{
								db.getConnection().rollback();
								dbSysn.getConnection().rollback();
								return msg;
							}*/
					 	}

					query = "update DonTraHang set trangthai = '0' where pk_seq = '" + dontrahang_fk + "'";
					if(!db.update(query))
					{
						msg = "Khong the chot: " + query;
						db.getConnection().rollback();
					
						return msg;
					}
					
				
				
				
			}
			
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
		
	}
	
	private String unchot_ChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			
			db.getConnection().setAutoCommit(false);
			String query = "";
			
			query = "update ERP_DONTRAHANG set trangthai = '0' where pk_seq = '" + lsxId + "' and trangthai=1";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Bỏ chốt không thành công ! Vui lòng kiểm tra trạng thái chứng từ ";
				db.getConnection().rollback();
				
				return msg;
			}
			
			
			query="select isnull(isktrukho,0) isktrukho,isnull(from_dontrahang,0) as  from_dontrahang  from ERP_DONTRAHANG where pk_seq="+lsxId;
			ResultSet rs=db.get(query);
			rs.next();
			String dontrahang_fk=rs.getString("from_dontrahang");
			String isktrukho=rs.getString("isktrukho");
			rs.close();
			if(!dontrahang_fk.equals("0"))
			{
				
				
				
				
				/*query="select count(*) sl from ERP_DONTRAHANG where trangthai=1 and  pk_seq="+lsxId ;
				rs= dbSysn.get(query);
				rs.next();
				int sl=rs.getInt("sl");
				rs.close();
				if(sl>0)
				{
					query="delete from ERP_DONTRAHANG_SANPHAM where dontrahang_fk="+lsxId;
					if(dbSysn.updateReturnInt(query)<=0)
					{
						msg = "2.Khong the xoa: " + query;
						db.getConnection().rollback();
						dbSysn.getConnection().rollback();
						return msg;
					}
					
					query="delete from ERP_DONTRAHANG where pk_seq="+lsxId;
					if(dbSysn.updateReturnInt(query)<=0)
					{
						msg = "2.Khong the xoa: " + query;
						db.getConnection().rollback();
						dbSysn.getConnection().rollback();
						return msg;
					}
					
					Utility util = new Utility();
					query =
							" select count(*)   as SoDong,a.TrucThuoc_FK ,c.DUNGCHUNGKENH, a.npp_fk " +
							" from DonTraHang a inner join DonTraHang_SP b on a.pk_seq = b.dontrahang_Fk " +
							" 		inner join NHAPHANPHOI c on c.PK_SEQ=a.NPP_FK " +
							" where dontrahang_Fk = '" + dontrahang_fk + "' and a.trangthai in (0,1) " +
							" group by a.TrucThuoc_FK, c.DUNGCHUNGKENH, a.npp_fk " ;
					
					ResultSet rs1 = db.get(query);
					String dungchungKENH = "0";
					 while(rs1.next())
					 {
						
						 dungchungKENH = rs1.getString("dungchungkenh");
					
					 }
					 String sqlKENH = "";
					 if(dungchungKENH.equals("1"))
							sqlKENH = " 100025 as kbh_fk "; //LAY KENH OTC
						else
							sqlKENH = " A.kbh_fk ";
					 
					 	query=	" 	select a.ngaytra,b.ngaynhapkho,a.kho_fk as kho_fk, a.npp_fk, "+sqlKENH+", b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat,b.NgayHetHan  " +
					 			" 	from DonTraHang a inner join DonTraHang_SP b on a.pk_seq = b.dontrahang_Fk " +
					 			" 	where dontrahang_Fk = '" + dontrahang_fk + "' " +
					 			" 	group by a.kho_fk, a.npp_fk, a.kbh_fk, b.solo, b.sanpham_fk,b.NgayHetHan, a.ngaytra,b.ngaynhapkho " ;
					 	
					 	ResultSet nkRs= db.get(query);
					 	while (nkRs.next())
					 	{
					 		String kho_fk=nkRs.getString("kho_fk");
							String npp_fk=nkRs.getString("npp_fk");	
							String kbh_fk=nkRs.getString("kbh_fk");
							String sanpham_fk=nkRs.getString("sanpham_fk");
							String solo=nkRs.getString("solo");
							Double tongxuat=nkRs.getDouble("tongxuat");
							String NgayHetHan=nkRs.getString("NgayHetHan");
							String ngaynhapkho=nkRs.getString("ngaynhapkho");
							String ngaytra=nkRs.getString("ngaytra");
							if(isktrukho.equals("0"))
							{
								msg=util.Update_NPP_Kho_Sp_Chitiet(ngaytra, "Đơn trả hàng về NCC-TT duyệt", db, kho_fk, sanpham_fk, npp_fk, kbh_fk, solo, NgayHetHan, ngaynhapkho, tongxuat, tongxuat,0,0.0, 0.0);
								if(msg.length()>0)
								{
									db.getConnection().rollback();
									dbSysn.getConnection().rollback();
									return msg;
								}
								msg=util.Update_NPP_Kho_Sp(ngaytra, "Đơn trả hàng về NCC-UPDATE", db, kho_fk, sanpham_fk, npp_fk, kbh_fk, tongxuat,  tongxuat,0, 0.0);
								if(msg.length()>0)
								{
									db.getConnection().rollback();
									dbSysn.getConnection().rollback();
									return msg;
								}
							}
							
					 	}

					 	query = "update DonTraHang set trangthai = '1' where pk_seq = '" + dontrahang_fk + "'";
						if(!db.update(query))
						{
							msg = "Khong the chot: " + query;
							db.getConnection().rollback();
							dbSysn.getConnection().rollback();
							return msg;
						}
					
				}
				else
				{
					msg = "Đơn trả hàng này đã chốt bên ERP không thể mở chốt !!!";
					db.getConnection().rollback();
					dbSysn.getConnection().rollback();
					return msg;
				}
				*/
				
				
			}
			
			
			db.getConnection().commit();

			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
		IErpDontrahangList obj = new ErpDontrahangList();
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    obj.setUserId(userId);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpDontrahang lsxBean = new ErpDontrahang();
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonTraHangNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonTraHang.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonTraHang.jsp";
	    		response.sendRedirect(nextJSP);
	    		
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDontrahangList obj)
	{
		String query =  "  select isnull(a.sohoadon,'') sohoadon,a.PK_SEQ, a.trangthai, a.ngaytra, a.lydo, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua,  " + 
						"  			b.Ten as doituong, b.ten as tennpp, b.pk_seq as manpp    " + 
						"  from ERP_DONTRAHANG a left join NHAPHANPHOI b on a.doituongId = b.pk_seq    " + 
						"  	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ    " + 
						"  	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		
		String tungaySX = request.getParameter("tungay");
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);
		
		String denngaySX = request.getParameter("denngay");
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nppId = request.getParameter("npp");
		if(nppId == null)
			nppId = "";
		obj.setnppId(nppId);
		
		String sohd = request.getParameter("sohd");
		if(sohd == null)
			sohd = "";
		obj.setSohdId(sohd);
		
		if(tungaySX.length() > 0)
			query += " and a.ngaytra >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.ngaytra <= '" + denngaySX + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(sohd.length() > 0)
			query += " and a.sohoadon = '" + sohd + "'";
		
		if(nppId.length() > 0)
			query += " and b.pk_seq = '" + nppId + "'";
		
		System.out.print(query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public static void main(String[] arg)
	{
		ErpDontrahangSvl pb = new ErpDontrahangSvl();
		String text = "NÐ155";
		
		/*System.out.println(":: TEXT: " + pb.toNonUnicode(text) );
		System.out.println(":: TO UNICODE: " + pb.toNonUnicode("Đ") );
		System.out.println(":: TO UNICODE 2: " + pb.toNonUnicode("Ð") );
		
		char ch='Đ';
		int code = ch;
		System.out.println(code);*/
		System.out.println((char)208);
		System.out.println((char)272);
		
		//208
		System.out.println(":: TO UNICODE BEFORE: " + pb.toNonUnicode(text) );
		String kq = pb.XuLyChuoi(text);
		System.out.println(":: TO UNICODE AFTER: " + pb.toNonUnicode(kq) );
	}
	
	public static String toNonUnicode(String inputString)
	{ 
		return Normalizer.normalize(inputString, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").replaceAll("\u0111", "d").
		replaceAll("\u0110", "D");
	}
	
	private static String XuLyChuoi(String chuoi) 
	{
		String kq = "";
	    for( int i = 0; i < chuoi.length(); i++ )
	    {
	    	char c = chuoi.charAt(i);
	    	
	    	int code = c;
	    	if( code == 208 )
	    		code = 272;
	    	
	    	//System.out.println("__ CODE: " + code);
	    	kq += (char)code;
	    }
	    
	    System.out.println("__ KQ: " + kq);
	    return kq;
	}
	
	
}
