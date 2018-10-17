package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkhoSX;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkhoSXList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkhoSX;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkhoSXList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpYeuCauChuyenKhoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpYeuCauChuyenKhoSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpChuyenkhoSXList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring); 
	    String action = util.getAction(querystring);
	    
	    String lsxId = util.getId(querystring);
	    obj = new ErpChuyenkhoSXList();
	    
	    System.out.println(" Action :" + action + " -- QUERY STRING: " + request.getQueryString());
	    
	    String msg = "";
	    if (action.equals("delete"))
	    {	
	    	dbutils db = new dbutils();
	    	if(db.updateReturnInt("update ERP_YEUCAUCHUYENKHO set trangthai = '4'  where pk_seq = '" + lsxId + "' and trangthai=0")!=1){
	    		msg="Không thể xóa , vui lòng kiểm tra trạng thái";
	    	}
	    	db.shutDown();
	    } 
	    else if(action.equals("chot"))
    	{
	    	//Chốt yêu cầu tự động tạo thành phiếu chuyển kho nằm chờ
	    	msg = this.chotYEUCAU(lsxId);
	    	
	    	/*dbutils db = new dbutils();
	    	db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '1' where pk_seq = '" + lsxId + "'");
	    	db.shutDown();*/
    	}
	    else if(action.equals("bochot"))
	    {
    		dbutils db = new dbutils();
	    	if(db.updateReturnInt("update ERP_YEUCAUCHUYENKHO set trangthai = '0' where pk_seq = '" + lsxId + "' and trangthai=1")!=1){
	    		msg="Không thể bỏ chốt, vui lòng kiểm tra lại trạng thái";
	    	}
	    	db.shutDown();
	    	String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
    	}
	    else if(action.equals("hoantat"))
		{
			dbutils db = new dbutils();
	    	db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '3' where pk_seq = '" + lsxId + "'");
	    	db.shutDown();
	    	String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		}
	    else if(request.getQueryString().indexOf("taochuyenkho") >= 0 ) 
		{
	    	IErpChuyenkho ckBean = new ErpChuyenkho();
	    	ckBean.setUserId(userId);
			if(ckBean.initFromYCXK(id))
			{
				ckBean.setYcckId(id);
				session.setAttribute("ckBean", ckBean);
				session.setAttribute("khochuyenIds", ckBean.getKhoXuatId());
				
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoNew.jsp";
				response.sendRedirect(nextJSP);
				return;
			}
			else
			{
				System.out.println(":: LOI KHI CHUYEN: " + ckBean.getMsg());
				msg = ckBean.getMsg();
				obj.setMsg(msg);
			}
		}
	    
	    String task = request.getParameter("task");
		if(task == null){
			task = "0";
		}
		if(task.equals("chuyenNL_back")){
			String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		}
		obj.setIsnhanHang(task);
	    obj.setUserId(userId);
	    obj.init("");
	    
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKho.jsp";
		 
		
		response.sendRedirect(nextJSP);
	}
 
	private String chotYEUCAU(String ycxkId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_YEUCAUCHUYENKHO set trangthai = '3' where pk_seq = '" + ycxkId + "' and trangthai=0";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Lỗi khi duyệt " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			//CHECK TỒN KHO 
			/*query = "  select sp.MA, sp.TEN, kho.AVAILABLE, xuat.SOLUONGYEUCAU  " + 
					"  from ERP_KHOTT_SANPHAM kho inner join ERP_SANPHAM sp on kho.SANPHAM_FK = sp.PK_SEQ " + 
					"  left join " + 
					"  ( " + 
					"  	select a.NGAYYEUCAU, a.KHOXUAT_FK, b.sanpham_fk, b.SOLUONGYEUCAU " + 
					"  	from ERP_YEUCAUCHUYENKHO a inner join ERP_YEUCAUCHUYENKHO_SANPHAM b on a.PK_SEQ = b.YEUCAUCHUYENKHO_FK  " + 
					"  	where a.PK_SEQ = '" + ycxkId + "' " + 
					"  ) " + 
					"  xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.KHOTT_FK = xuat.KHOXUAT_FK  " + 
					"  where kho.AVAILABLE < xuat.SOLUONGYEUCAU ";*/
			//KHO TP HOANG LIET 100023 khong can co ma phieu
			query = " select sp.MA, sp.TEN, a.NGAYYEUCAU, a.KHOXUAT_FK, b.sanpham_fk, b.SOLUONGYEUCAU,  "+
					 " 	ISNULL( (  "+
					 " 		 select sum(ct.AVAILABLE) "+
					 " 		 from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq   "+
					 " 		 where KHOTT_FK = a.KHOXUAT_FK and SANPHAM_FK = b.SANPHAM_FK "+
					 " 	 		  and ngaynhapkho <= a.NGAYYEUCAU  "+
					 " 	 		  and ( isnull(sp.batbuockiemdinh, 0) = 0 or KHOTT_FK in ( 100023, 100058, 100067, 100069 ) or ( sp.batbuockiemdinh = 1 and isnull( maphieu, '' ) != ''  ) ) "+
					 " 		  ), 0 ) as AVAILABLE "+
					 " from ERP_YEUCAUCHUYENKHO a inner join ERP_YEUCAUCHUYENKHO_SANPHAM b on a.PK_SEQ = b.YEUCAUCHUYENKHO_FK " + 
					 " 	inner join ERP_SANPHAM sp on b.SANPHAM_FK = sp.PK_SEQ   "+
					 " where a.PK_SEQ = '" + ycxkId + "'  ";
			
			System.out.println("::: CHECK TON KHO: " + query);
			ResultSet rsCHECK = db.get(query);
			if( rsCHECK != null )
			{
				while( rsCHECK.next() )
				{
					//System.out.println(":::: SAN PHAM: " + rsCHECK.getString("MA") + " SO LUONG YC:  " + rsCHECK.getDouble("SOLUONGYEUCAU") + " -- AVAI:  " + rsCHECK.getDouble("AVAILABLE") );
					if( rsCHECK.getDouble("SOLUONGYEUCAU") > rsCHECK.getDouble("AVAILABLE") )
					{
						msg = "Tồn kho tới ngày ( " + rsCHECK.getString("NGAYYEUCAU") + " ) của sản phẩm: " + rsCHECK.getString("MA") + " số lượng " + rsCHECK.getDouble("AVAILABLE") + " < số lượng xuất ( " + rsCHECK.getString("SOLUONGYEUCAU") + " ).";
						rsCHECK.close();
						
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
				}
				rsCHECK.close();
			}
			
			query = " insert ERP_CHUYENKHO(CONGDOAN_FK,Yeucauchuyenkho_fk, lenhsanxuat_fk, IsChuyenHangSX, NGUOINHAN, noidungxuat_fk, NgayChuyen, lydo, ghichu, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua, loaidoituong, DOITUONG_FK, loaidoituongNHAN, DOITUONGNHAN_FK, KYHIEU, SOCHUNGTU, LENHDIEUDONG, NGAYDIEUDONG, NGUOIDIEUDONG, VEVIEC, NGUOIVANCHUYEN, PHUONGTIEN, HOPDONG, chiphi_fk,muahang_fk ) " +
					" select CONGDOAN_FK ,pk_seq as Yeucauchuyenkho_fk, lenhsanxuat_fk, IsChuyenHangSX, NGUOINHAN, noidungxuat_fk, NGAYYEUCAU as NgayChuyen, lydo, N'Chuyển kho tự động khi duyệt YCXK: " + ycxkId + "' as ghichu, 0 as trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua, loaidoituong, DOITUONG_FK, loaidoituongNHAN, DOITUONGNHAN_FK, KYHIEU, SOCHUNGTU, LENHDIEUDONG, NGAYDIEUDONG, NGUOIDIEUDONG, VEVIEC, NGUOIVANCHUYEN, PHUONGTIEN, HOPDONG, chiphi_fk, muahang_fk  " + 
					" from ERP_YEUCAUCHUYENKHO where PK_SEQ = '" + ycxkId + "'		";
			
			System.out.println("::: CHEN CHUYEN KHO: " + query);
			if( db.updateReturnInt(query) <= 0 )
			{
				msg = "Lỗi khi duyệt " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			String ckId = "";
			query = "select IDENT_CURRENT('ERP_CHUYENKHO') as ckId";
			
			ResultSet rsPxk = db.get(query);						
			if(rsPxk.next())
			{
				ckId = rsPxk.getString("ckId");
				 
			}
			rsPxk.close();
			
			query = " insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK,  soluongyeucau, soluongxuat, ghichu_chuyenkho, lenhsanxuat_fk  ) " + 
					" select '" + ckId + "', SANPHAM_FK, SOLUONGYEUCAU, SOLUONGYEUCAU, '', NULL " + 
					" from ERP_YEUCAUCHUYENKHO_SANPHAM where YEUCAUCHUYENKHO_FK = '" + ycxkId + "'		";
			
			System.out.println("::: CHEN CHUYEN KHO - SP: " + query);
			if( db.updateReturnInt(query) <= 0 )
			{
				msg = "Lỗi khi duyệt " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			
			
			
			//ĐỀ XUẤT KHO ==> hàng bắt buộc kiểm định mà không có mã phiếu thì không đề xuất
			query = "  select a.lenhsanxuat_fk,a.noidungxuat_fk, a.DOITUONGNHAN_FK, a.NGAYYEUCAU, a.KHOXUAT_FK, c.MA, b.sanpham_fk, b.SOLUONGYEUCAU, a.loaidoituong, a.DOITUONG_FK as doituongId  " + 
					"  from ERP_YEUCAUCHUYENKHO a inner join ERP_YEUCAUCHUYENKHO_SANPHAM b on a.PK_SEQ = b.YEUCAUCHUYENKHO_FK  " +
					"		inner join ERP_SANPHAM c on b.sanpham_fk = c.pk_seq		" +
					"  where a.PK_SEQ = '" + ycxkId + "' ";
			ResultSet rsYC = db.get(query);
			
			Utility util = new Utility();
			while( rsYC.next() )
			{
				String ndxId = rsYC.getString("noidungxuat_fk");
				String lenhsanxuat_fk = rsYC.getString("lenhsanxuat_fk")==null?"":rsYC.getString("lenhsanxuat_fk");
				String ngayyeucau = rsYC.getString("ngayyeucau");
				String khoId = rsYC.getString("KHOXUAT_FK");
				String spId = rsYC.getString("sanpham_fk");
				String spMA = rsYC.getString("MA");
				String tongluong = rsYC.getString("SOLUONGYEUCAU");
				String loaidoituong = rsYC.getString("loaidoituong") == null ? "" : rsYC.getString("loaidoituong");
				String doituongId = rsYC.getString("doituongId") == null ? "" : rsYC.getString("doituongId");
				//kiem tra kho san xuat
				 String sql="select count (PK_SEQ) dem from erp_khott where pk_seq='"+khoId+"' and loaiKHO=10";
				int dem=0;
				ResultSet rsdem=db.get(sql);
				
				try {
					if(rsdem.next())
					dem=rsdem.getInt("dem");
					rsdem.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				query = "select ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, MAPHIEU, MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( bin_fk, 0 ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo, isnull( nsx_fk, 0 ) as nhasanxuat   "+
						"\n from ERP_KHOTT_SP_CHITIET CT inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
						"\n where KHOTT_FK = '" + khoId + "' and SANPHAM_FK = '" + spId + "'   " +
						 	"\n  and ngaynhapkho <= '" + ngayyeucau + "' and ct.AVAILABLE > 0 "+ 
						 	"\n  and ( isnull(sp.batbuockiemdinh, 0) = 0 or KHOTT_FK in (100023,100058,100067,100069 ) or ( sp.batbuockiemdinh = 1 and isnull( maphieu, '' ) != ''  ) ) ";
				
				if( doituongId.trim().length() > 0 )
					query += " and  loaidoituong = '" + loaidoituong + "' and doituongId = '" + doituongId + "' ";
					
				if( ndxId.equals("100073") ) //Xuất chuyển nội bộ
				{
					String DOITUONGNHAN_FK = rsYC.getString("noidungxuat_fk");
					
					String conditionKHONGDUOCPB = "     select b.solo from LINK_DMS_THAT.DataCenter.dbo.ERP_PHANBODONHANG a inner join LINK_DMS_THAT.DataCenter.dbo.ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMA + "' ) " +
		  					  					  " 	where a.TRANGTHAI = '1' and a.tungay <= '" + ngayyeucau + "' and '" + ngayyeucau + "' <= a.denngay and a.PK_SEQ in ( select phanbo_fk from LINK_DMS_THAT.DataCenter.dbo.ERP_PHANBODONHANG_DOITUONG where doituong_fk != '" + DOITUONGNHAN_FK + "' ) ";

					String conditionDUOCPB = "  select b.solo from LINK_DMS_THAT.DataCenter.dbo.ERP_PHANBODONHANG a inner join LINK_DMS_THAT.DataCenter.dbo.ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMA + "' ) " +
											 " 	where a.TRANGTHAI = '1' and a.tungay <= '" + ngayyeucau + "' and '" + ngayyeucau + "' <= a.denngay and a.PK_SEQ in ( select phanbo_fk from LINK_DMS_THAT.DataCenter.dbo.ERP_PHANBODONHANG_DOITUONG where doituong_fk = '" + DOITUONGNHAN_FK + "' ) ";

					query += "\n			and CT.SOLO not in ( select solo from LINK_DMS_THAT.DataCenter.dbo.ERP_HANGCHOPHANBO where sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMA + "' ) )	" +
							 "\n 			and ( CT.SOLO not in ( " + conditionKHONGDUOCPB + " ) or CT.SOLO in ( " + conditionDUOCPB + " ) )	";
				}
				
				query += "\n order by NGAYHETHAN, SOLO, MAPHIEU, dbo.ftConvertToNumber(MAME), dbo.ftConvertToNumber(MATHUNG)  ";
				
				if(lenhsanxuat_fk.trim().length()>0 && this.getMaNDX(ndxId).equals("XK10") && dem>0 ){
					query = " \n select ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, MAPHIEU, MARQ,  HAMLUONG, HAMAM,  vitri,  phieudt,phieueo, isnull( nsx_fk, 0 ) as nhasanxuat   "+
						   " \n	 from UFN_GETLSX_TONCHITIET('"+khoId+"','"+lenhsanxuat_fk+"','"+spId+"')ct "+
						   "\n order by NGAYHETHAN, SOLO, MAPHIEU, dbo.ftConvertToNumber(MAME), dbo.ftConvertToNumber(MATHUNG)  ";
				}
				//de xuat 
				System.out.println("----LAY SO LO ( " + spId + " ): " + query );
				ResultSet rs = db.get(query);

				
				double total = 0;
				double totalCT = 0;
				boolean exit = false;
				while(rs.next())
				{
					double slg = 0;
					double avai = rs.getDouble("AVAILABLE");
					
					total += avai;
					if(total < Double.parseDouble(tongluong))
					{
						slg = avai;
					}
					else
					{
						slg =  Double.parseDouble(tongluong) - ( total - avai );
						exit = true;
					}
						
					//CHÈN VÀO BẢNG CHI TIẾT
					String solo = rs.getString("SOLO");
					String ngayhethan = rs.getString("NGAYHETHAN");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					String MAME = rs.getString("MAME");
					String MATHUNG = rs.getString("MATHUNG");
					String bin_fk = rs.getString("vitri");
					String MAPHIEU = rs.getString("MAPHIEU");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					String MARQ = rs.getString("MARQ");
					String HAMLUONG = rs.getString("HAMLUONG");
					String HAMAM = rs.getString("HAMAM");
					String NSX_FK = rs.getString("nhasanxuat");
					
					//ROUND 3 SỐ
					slg = util.Round(slg, 4);
					if( slg > 0 )
					{
						totalCT += slg;
						//System.out.println("::: TONG LUONG: " + totalCT);
						
						query = "insert ERP_CHUYENKHO_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong, NSX_FK ) " +
								"select '" + ckId + "', pk_seq, ' ', N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', '" + bin_fk + "', " + 
								" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + slg + "', " + NSX_FK + " " +
								"from ERP_SANPHAM where pk_seq = '" + spId + "' ";
						
						System.out.println("1.2.Insert ERP_CHUYENKHO_SANPHAM_CHITIET: " + query);
						if(!db.update(query))
						{
							msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM_CHITIET: " + query;
							db.getConnection().rollback();
							db.shutDown();
							return msg;
						}
						
						//CẬP NHẬT KHO
						msg = util.Update_KhoTT_MOI(ngayyeucau, "Chuyển kho", db, khoId, spId, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk,
										MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, loaidoituong, doituongId, 0, slg, -1 * slg, NSX_FK);
						if( msg.trim().length() > 0 )
						{
							db.getConnection().rollback();
							db.shutDown();
							return msg;
						}
					}
					
					if( exit )
						break;
				}
				rs.close();
				
				if( util.Round(totalCT, 4 ) != util.Round( Double.parseDouble(tongluong), 4 ) )
				{
					msg = "Tổng xuất theo lô của sản phẩm ( " + spMA + " ) ( " + util.Round(totalCT, 4 ) + " ) không bằng tổng số lượng xuất ( " + util.Round( Double.parseDouble(tongluong), 4 ) + " ). Vui lòng liên hệ Admin để xử lý ";
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
				
			}
			rsYC.close();
			
			db.getConnection().commit();
			
			//Cập nhật tooltip
			db.execProceduce2("CapNhatTooltip_CK", new String[] { ckId } );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) { }
			
			msg = e.getMessage();
		}
    	
		db.shutDown();
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

		IErpChuyenkhoSXList obj = new ErpChuyenkhoSXList();
		String task = request.getParameter("task");
		if(task == null){
			task = "0";
		}

		obj.setIsnhanHang(task);
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		if(action.equals("Tao moi"))
		{
			IErpChuyenkhoSX lsxBean = new ErpChuyenkhoSX();
			lsxBean.setUserId(userId);
			lsxBean.createRs();
			lsxBean.settask(task);
			session.setAttribute("lsxBean", lsxBean);
			session.setAttribute("vitriId", "");
			session.setAttribute("khochuyenIds", "");
			obj.DBclose();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKhoNew.jsp";
			response.sendRedirect(nextJSP);
		}
		else 
		{
			if(action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

				String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
 	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
				obj.init("");
				
				
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKho.jsp";

				response.sendRedirect(nextJSP);
			}
			else if(action.equals("chot") || action.equals("bochot"))
			{
				String Id = request.getParameter("chungtu");
				String msg = "";
				if(action.equals("chot"))
				{
					/*dbutils db = new dbutils();
					db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '1' where pk_seq = '" + Id + "'");
					db.shutDown();*/
					
					msg = this.chotYEUCAU(Id);
				}
				else if(action.equals("bochot"))
				{
					dbutils db = new dbutils();
					db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '0' where pk_seq = '" + Id + "'");
					db.shutDown();
				}

				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);
				

				String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
 	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
				obj.init("");	
				
				
				if( msg.length()> 0 )
					obj.setMsg(msg);

				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKho.jsp";

				response.sendRedirect(nextJSP);
			}
			else
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);
				
				obj.init(search);	

				String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,this.getServletName(), querySearch);
				
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKho.jsp";

				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpChuyenkhoSXList obj)
	{
		String query =  "\n SELECT   A.PK_SEQ, A.TRANGTHAI, A.NGAYYEUCAU, A.NOIDUNGXUAT_FK AS NDXID, B.MA + ', ' + B.TEN AS NOIDUNGXUAT,  isnull(KHOTT.TEN,'') as khonhan, " +
						"\n A.LYDO, NV.TEN AS NGUOITAO, A.NGAYSUA, A.NGAYTAO, NV2.TEN AS NGUOISUA  ,isnull(a.sochungtu,'') as sochungtu, isnull(A.tooltip, '') as tooltip, " +
						"\n case when a.dathang_fk is not null then d.ten else '' end nppdat  " +
						"\n FROM ERP_YEUCAUCHUYENKHO A INNER JOIN ERP_NOIDUNGNHAP B ON A.NOIDUNGXUAT_FK = B.PK_SEQ  " +
						"\n left join ERP_KHOTT KHOTT on a.khonhan_fk = KHOTT.PK_SEQ   " +
						"\n INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   " +
						"\n INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ  " +
						//"\n left join LINK_DMS_THAT_NOIBO.DataCenter.dbo.erp_dondathang c on a.dathang_fk = c.PK_SEQ " +
						"\n left join nhaphanphoi d on a.doituongnhan_fk = d.PK_SEQ" +
						"\n WHERE 1=1  ";
			
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
		
		
		String sanphamct = request.getParameter("sanphamct");
		if(sanphamct == null)
			sanphamct = "";
		obj.setSanphamct(sanphamct);
		
		
		if(tungaySX.length() > 0)
			query += "\n and a.ngayyeucau >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += "\n and a.ngayyeucau  <= '" + denngaySX + "'";
	
		if(trangthai.length() > 0)
			query += "\n and a.TrangThai = '" + trangthai + "'";
		
		if(sophieu.length() > 0){
			query += "\n and  cast( a.pk_seq as nvarchar(10))  like '%" + sophieu + "%'";
		}
		
		if(khochuyenid.length() > 0){
			query += "\n and  cast( a.khoxuat_fk as nvarchar(10))  like '%" + khochuyenid + "%'";
		}
		
		if(khonhanid.length() > 0){
			query += "\n and  cast( a.khonhan_fk as nvarchar(10))  like '%" + khonhanid + "%'";
		}
		
		if(lsxId.length() > 0){
			query += "\n and  cast( a.LENHSANXUAT_FK as nvarchar(10))  like '%" + lsxId + "%'";
		}
		
		if(lydo.length() > 0){
			query += "\n and a.lydo like N'%" + lydo + "%'";
		}
		
		if(ndxuat.length() > 0){
			query += "\n and a.noidungxuat_fk = " +ndxuat+ " ";
		}
		if(nguoitao.length() > 0){
			query += "\n and a.nguoitao = " +nguoitao+ " ";
		}
		if(nguoisua.length() > 0){
			query += "\n and a.nguoisua = " +nguoisua+ " ";
		}
		
		if(obj.getsochungtubnId().trim().length()>0){
			query+= "\n and A.LENHDIEUDONG LIKE '%"+sochungtubn+"%' ";
		}
		 
	 
		if(sohoadon.length() >0){
			query+="\n AND A.sochungtu LIKE  '%"+sohoadon+"%'";
		}
		
		
		geso.dms.center.util.Utility  util2= new geso.dms.center.util.Utility();
		
		if(sanphamct.length() >0){
			
			query+="\n and a.pk_seq in ( "+    
					 "\n select c.YEUCAUCHUYENKHO_fk from ERP_YEUCAUCHUYENKHO_sanpham c inner join erp_sanpham sp on sp.pk_seq= c.sanpham_fk "+    
					 "\n where sp.pk_seq ="+ sanphamct +")" ;
		}
		
		System.out.println("\n \n seach  qr: \n" +query +" \n \n");
		return query;
		
		
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	public String getMaNDX(String ndxId) 
	{
		//Không nối database, lấy ID luôn, bảng này là không đổi
		if( ndxId.equals("100057") )
			return "XK01";
		if( ndxId.equals("100058") )
			return "XK02";
		if( ndxId.equals("100059") )
			return "XK03";
		if( ndxId.equals("100060") )
			return "XK04";
		if( ndxId.equals("100061") )
			return "XK05";
		if( ndxId.equals("100062") )
			return "XK06";
		if( ndxId.equals("100063") )
			return "XK07";
		if( ndxId.equals("100064") )
			return "XK08";
		if( ndxId.equals("100065") )
			return "XK09";
		if( ndxId.equals("100066") )
			return "XK10";
		if( ndxId.equals("100067") )
			return "XK11";
		if( ndxId.equals("100068") )
			return "XK12";
		if( ndxId.equals("100069") )
			return "XK13";
		if( ndxId.equals("100070") )
			return "XK14";
		if( ndxId.equals("100071") )
			return "XK15";
		if( ndxId.equals("100072") )
			return "XK16";
		if( ndxId.equals("100073") )
			return "XK17";
		if( ndxId.equals("100076") )
			return "XK17";
		return "";
	}
	
}
