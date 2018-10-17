package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpCannguyenlieu;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpCannguyenlieuList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpCannguyenlieu;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpCannguyenlieuList;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCannguyenlieuSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpCannguyenlieuSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpCannguyenlieuList obj;
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
	    obj = new ErpCannguyenlieuList();
	  
	    System.out.println("::: ACTION: " + action );
	    if (action.equals("delete"))
	    {	
	    	String msg = this.XoaChuyenKho( lsxId );
    		obj.setMsg(msg);
	    } 
	    else if(action.equals("chot"))
    	{
    		String msg = this.ChotChuyenKho( lsxId );
    		obj.setMsg(msg);
    	}
	    else if(action.equals("bochot"))
	    {
    		/*dbutils db = new dbutils();
	    	db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '0' where pk_seq = '" + lsxId + "'");
	    	db.shutDown();*/
    	}
	    else if(action.equals("hoantat"))
		{
			dbutils db = new dbutils();
	    	db.update("update ERP_CHUYENKHO set trangthai = '3' where pk_seq = '" + lsxId + "'");
	    	db.shutDown();
		}
    	
	    String isnhanhang = request.getParameter("isnhanHang");
		if(isnhanhang == null)
			isnhanhang = "0";
		obj.setIsnhanHang(isnhanhang);
		
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanNguyenLieu.jsp";
		response.sendRedirect(nextJSP);
	}
 
	private String ChotChuyenKho(String lsxId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_CANNGUYENLIEU set trangthai = '1', NGAYCHOT = '" + this.getDateTime() + "' where pk_seq = '" + lsxId + "' and trangthai=0 ";
			System.out.println("::: CHOT CK: " + query);
			
			if( db.updateReturnInt(query)!=1)
			{
				msg = "Lỗi khi chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			//CẬP NHẬT KHO ==> mấy nữa cải tiến booked tính động, không lấy trong kho
			Utility util = new Utility();
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCAN, b.Khott_FK, b.SanPham_fk, a.khott_sp_ct_fk,  " + 
					"  		ISNULL( ( select ten from ERP_SANPHAM where pk_seq = a.sanpham_fk ), '') as tensp, b.SoLo, a.NgayHetHan, a.ngaynhapkho,     " + 
					"  		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, a.SoLuong,   " + 
					"  		c.SOLUONG as tonkho, c.BOOKED, c.AVAILABLE    " + 
					"  from ERP_CANNGUYENLIEU_SANPHAM_CHITIET a inner join ERP_CANNGUYENLIEU b on a.CANNGUYENLIEU_FK = b.PK_SEQ    " + 
					"  	 inner join ERP_KHOTT_SP_CHITIET c on a.khott_sp_ct_fk = c.pk_seq  " + 
					"  where b.PK_SEQ = '" + lsxId + "'  ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					String khott_sp_ct_fk = rs.getString("khott_sp_ct_fk");
					String khoId = rs.getString("Khott_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
					String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");

					double soluong = rs.getDouble("soluong");
					double tonkho = rs.getDouble("tonkho");
					double booked = rs.getDouble("BOOKED");
					double avai = rs.getDouble("AVAILABLE");
					
					//Tính ra số lượng cần điều chỉnh, ở đây là điều chỉnh CỘT SỐ LƯỢNG TRONG KHO
					double soluongDIEUCHINH = 0;
					if( soluong < booked )
					{
						msg = "Tồn kho của sản phẩm ( " + rs.getString("tensp") + " ) sau điều chỉnh ( " + soluong + " ) đang nhỏ hơn số lượng hàng đang booked ( " + booked + " ) ";
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
					
					soluongDIEUCHINH = soluong - tonkho;
					
					query = "update ERP_CANNGUYENLIEU_SANPHAM_CHITIET set soluongHT = '" + tonkho + "', bookedHT = '" + booked + "', avaiHT = '" + avai + "', soluongDIEUCHINH = '" + soluongDIEUCHINH + "' " +
							"where cannguyenlieu_fk = '" + lsxId + "' and khott_sp_ct_fk = '" + khott_sp_ct_fk + "'  ";
					System.out.println("::: CAP NHAT CAN: " + query);
					if( !db.update(query) )
					{
						msg = "Lỗi khi cập nhật ERP_CANNGUYENLIEU_SANPHAM_CHITIET: " + query;
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
					
					if( soluongDIEUCHINH != 0 )
					{
						msg = util.Update_KhoTT(rs.getString("NGAYCAN"), "Chốt cân NL", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
								mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluongDIEUCHINH, 0, soluongDIEUCHINH);
						if( msg.trim().length() > 0 )
						{
							db.getConnection().rollback();
							db.shutDown();
							return msg;
						}
					}
				}
				rs.close();
				
				//Kết chuyển lượng chênh lệch vào 1 thùng lớn nhất, và điều chỉnh tồn kho của thùng đó
				query = "select SUM(soluongDIEUCHINH) as soluongDC from ERP_CANNGUYENLIEU_SANPHAM_CHITIET where cannguyenlieu_fk = '" + lsxId + "' ";
				rs = db.get(query);
				if( rs != null )
				{
					double soluongDC = 0;
					if( rs.next() )
					{
						soluongDC = rs.getDouble("soluongDC");
						rs.close();
					}
					
					if( soluongDC != 0 ) //Tìm mã thùng lớn nhất không có điều chỉnh để ghi nhận ngược lại số lượng điều chỉnh
					{
						soluongDC = -1 * soluongDC;
						
						query = "select top(1) khott_sp_ct_fk " +
								"from ERP_CANNGUYENLIEU_SANPHAM_CHITIET where cannguyenlieu_fk = '" + lsxId + "' and soluongDIEUCHINH = 0 order by mathung desc ";
						rs = db.get(query);
						
						String khott_sp_ct_fk = "";
						if( rs != null )
						{
							if( rs.next() )
							{
								khott_sp_ct_fk = rs.getString("khott_sp_ct_fk");
								rs.close();
							}
						}
						
						if( khott_sp_ct_fk.trim().length() <= 0 )
						{
							msg = "Không tìm thấy mã thùng lớn nhất đủ số lượng để cập nhật tổng lượng chênh lệch sau khi cân.";
							db.getConnection().rollback();
							db.shutDown();
							return msg;
						}
						else
						{
							//GHI NHAN NXT
							query = "  insert ERP_CANNGUYENLIEU_SANPHAM_CHITIET_CHENHLECH ( cannguyenlieu_fk, khott_sp_ct_fk, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, soluong ) " + 
									"  select cannguyenlieu_fk, khott_sp_ct_fk, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, '" + soluongDC + "' soluong  " + 
									"  from ERP_CANNGUYENLIEU_SANPHAM_CHITIET " + 
									"  where cannguyenlieu_fk = '" + lsxId + "' and khott_sp_ct_fk = '" + khott_sp_ct_fk + "' ";
							if( !db.update(query)  )
							{
								msg = "Lỗi khi cập nhật số lượng cân chênh lệch: " + query;
								db.getConnection().rollback();
								db.shutDown();
								return msg;
							}
							
							//CẬP NHẬT KHO LƯỢNG ĐIỀU CHỈNH NÀY
							query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCAN, b.Khott_FK, b.SanPham_fk, a.khott_sp_ct_fk,  " + 
									"  		ISNULL( ( select ten from ERP_SANPHAM where pk_seq = a.sanpham_fk ), '') as tensp, b.SoLo, a.NgayHetHan, a.ngaynhapkho,     " + 
									"  		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, a.SoLuong   " + 
									"  from ERP_CANNGUYENLIEU_SANPHAM_CHITIET_CHENHLECH a inner join ERP_CANNGUYENLIEU b on a.CANNGUYENLIEU_FK = b.PK_SEQ    " + 
									"  where b.PK_SEQ = '" + lsxId + "' and  a.khott_sp_ct_fk = '" + khott_sp_ct_fk + "' ";
							
							System.out.println("::: CAP NHAT KHO - GHI NHAN LUONG CHENH LECH: " + query);
							rs = db.get(query);
							if( rs != null )
							{
								while( rs.next() )
								{
									String khoId = rs.getString("Khott_FK");
									String spId = rs.getString("SanPham_fk");
									String solo = rs.getString("SoLo");
									String ngayhethan = rs.getString("NgayHetHan");
									String ngaynhapkho = rs.getString("ngaynhapkho");
									
									String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
									String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
									
									String mame = rs.getString("mame");
									String mathung = rs.getString("mathung");
									String bin_fk = rs.getString("bin_fk");
									
									String maphieu = rs.getString("maphieu");
									String phieudt = rs.getString("phieudt");
									String phieueo = rs.getString("phieueo");
									
									String marq = rs.getString("marq");
									String hamluong = rs.getString("hamluong");
									String hamam = rs.getString("hamam");

									double soluong = rs.getDouble("soluong");
									
									msg = util.Update_KhoTT(rs.getString("NGAYCAN"), "Chốt cân NL", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
											mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong);
									if( msg.trim().length() > 0 )
									{
										db.getConnection().rollback();
										db.shutDown();
										return msg;
									}
								}
								rs.close();
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
			e.printStackTrace();
			
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
		}
		
		return msg;
	}
	
	private String XoaChuyenKho(String lsxId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_CANNGUYENLIEU set trangthai = '2' where pk_seq = '" + lsxId + "' and trangthai=0  ";
			System.out.println("::: XOA CK: " + query);
			
			if( db.updateReturnInt(query)!=1 )
			{
				msg = "Lỗi khi xóa: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			msg = "Lỗi khi xóa: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
		}
		
		return msg;
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

		IErpCannguyenlieuList obj = new ErpCannguyenlieuList();
		String isnhanhang = request.getParameter("isnhanHang");
		if(isnhanhang == null)
			isnhanhang = "0";
		obj.setIsnhanHang(isnhanhang);
		
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		if(action.equals("Tao moi"))
		{
			IErpCannguyenlieu lsxBean = new ErpCannguyenlieu();
			lsxBean.setUserId(userId);
			lsxBean.createRs();

			session.setAttribute("ckBean", lsxBean);
			session.setAttribute("khochuyenIds", "");
			obj.DBclose();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanNguyenLieuNew.jsp";
			response.sendRedirect(nextJSP);
		}
		else 
		{
			if(action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanNguyenLieu.jsp";

				response.sendRedirect(nextJSP);
			}
			else if(action.equals("chot") || action.equals("bochot"))
			{
				String Id = request.getParameter("chungtu");
				String msg="";
				if(action.equals("chot"))
				{
		    		msg = this.ChotChuyenKho( Id );
		    		obj.setMsg(msg);
				}
				else if(action.equals("bochot"))
				{
					/*dbutils db = new dbutils();
					db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '0' where pk_seq = '" + Id + "'");
					db.shutDown();*/
				}

				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);		    	
				obj.init(search);	
				if(msg.length()>0){
					obj.setMsg(msg);

				}
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanNguyenLieu.jsp";

				response.sendRedirect(nextJSP);
			}
			else
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);		    	
				obj.init(search);				
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCanNguyenLieu.jsp";

				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpCannguyenlieuList obj)
	{
		String	query = " SELECT A.PK_SEQ, A.TRANGTHAI, A.ngaycan, KHOTT.TEN as khoTen, SP.TEN as spTEN, A.SOLO, " +
						" 	A.LYDO, NV.TEN AS NGUOITAO, A.NGAYSUA, A.NGAYTAO, NV2.TEN AS NGUOISUA    " +
						" FROM ERP_CANNGUYENLIEU A   " +
						" INNER join ERP_KHOTT KHOTT on a.khott_fk = KHOTT.PK_SEQ   " +
						" INNER join ERP_SANPHAM SP on a.sanpham_fk = SP.PK_SEQ   " +
						" INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   " +
						" INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ  " +
						" WHERE 1=1  " ;

		String tungaySX = request.getParameter("tungaySX");
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);
		
		String denngaySX = request.getParameter("denngaySX");
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String khonhanid = request.getParameter("khonhanId");
		if(khonhanid == null)
			khonhanid = "";
		obj.setkhonhanId(khonhanid);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setsohoadon(sohoadon);
		
		String khochuyenid = request.getParameter("khochuyenId");
		if(khochuyenid == null)
			khochuyenid = "";
		obj.setKhoChuyenId(khochuyenid);
		
		String sophieu = request.getParameter("sophieu");
		if(sophieu == null)
			sophieu = "";
		obj.setSophieu(sophieu);
		
		String lsxId = request.getParameter("solenhsx");
		if(lsxId == null)
			lsxId = "";
		obj.setlsxId(lsxId);
		
		String ndxuat = request.getParameter("ndxuat");
		if(ndxuat == null)
			ndxuat = "";
		obj.setNdxuat(ndxuat);
		
		String lydo = request.getParameter("lydo");
		if(lydo == null)
			lydo = "";
		obj.setLydo(lydo);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String nguoisua = request.getParameter("nguoisua");
		if(nguoisua == null)
			nguoisua = "";
		obj.setNguoisua(nguoisua);
		
		String sochungtubn = request.getParameter("sochungtubn");
		if(sochungtubn == null)
			sochungtubn = "";
		obj.setsochungtubnId(sochungtubn);
		
		if(tungaySX.length() > 0)
			query += " and a.ngaycan >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.ngaycan  <= '" + denngaySX + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(sophieu.length() > 0){
			query += " and  cast( a.pk_seq as nvarchar(10))  like '%" + sophieu + "%'";
		}
		
		if(khochuyenid.length() > 0){
			query += " and  cast( a.khott_fk as nvarchar(10))  like '%" + khochuyenid + "%'";
		}
		
		if(khonhanid.length() > 0){
			query += " and  cast( a.khott_fk as nvarchar(10))  like '%" + khonhanid + "%'";
		}
		
		if(lydo.length() > 0){
			query += " and a.lydo like N'%" + lydo + "%'";
		}

		if(nguoitao.length() > 0){
			query += " and a.nguoitao = " +nguoitao+ " ";
		}
		if(nguoisua.length() > 0){
			query += " and a.nguoisua = " +nguoisua+ " ";
		}
		
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
