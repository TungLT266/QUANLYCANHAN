package geso.traphaco.erp.servlets.nhanhangnhapkhau;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.erp.beans.nhanhangnhapkhau.IErpNhanhangList_Giay;
import geso.traphaco.erp.beans.nhanhangnhapkhau.IErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhangnhapkhau.imp.ErpNhanhangList_Giay;
import geso.traphaco.erp.beans.nhanhangnhapkhau.imp.ErpNhanhang_Giay;
import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.erp.util.Library;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhanhangnhapkhau_GiaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpNhanhangnhapkhau_GiaySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpNhanhangList_Giay obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String loaidh = util.antiSQLInspection(request.getParameter("loai"));
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
 
	    String action = util.getAction(querystring);
	    
	    String nhId = util.getId(querystring);
	    
	    String msg = "";
	    String ctyId = "";
	    
	    obj = new ErpNhanhangList_Giay();
	    ctyId = (String)session.getAttribute("congtyId") ;
	    obj.setCongtyId(ctyId);
	    obj.setTrangthai("0");
	    obj.setUserId(userId);
	    obj.setLoaimh(loaidh);
	    
	    if (action.equals("delete"))
	    {	
	    	try
	    	{
		    	dbutils db = new dbutils();
		    	db.getConnection().setAutoCommit(false);
		    	msg = Delete(nhId, db);
		    	if(msg.length() > 0)
		    	{
		    		System.out.println("loi la :"+msg);
		    		obj.setmsg(msg);
		    		db.getConnection().rollback();
		    		
		    	}
		    	
		    	db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
				
		     
		    		System.out.println("xoa thanh cong........");
		    		String poId = request.getParameter("poId");
		    		IErpNhanhang_Giay nhanhang = new ErpNhanhang_Giay(nhId);
		    		
		    		//nhanhang.init();
		    		if(!poId.equals("null")){
		    			nhanhang.updateDonmuahang(poId.substring(5, poId.length()));
		    		}
		    		 obj.setTrangthai("0");
		    		    
	    		    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		    obj.setUserId(userId);
	    		    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    		    obj.init("");
	    		    obj.setmsg(msg);
	    			session.setAttribute("obj", obj);
	    			session.setAttribute("congtyId", obj.getCongtyId());
	    					
	    			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhau_Giay.jsp";
	    			response.sendRedirect(nextJSP);
	    			return;
		    	 
		    	
		    	
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	    else if(action.equals("chot"))
	    {
						
			String lhhId = request.getParameter("lhhId");
    		if(lhhId == null)
    			lhhId = "";
    		String loai = request.getParameter("loai");
    		if(loai == null)
    			loai = "";
    		
    		msg = ChotNhanHang(nhId, userId, ctyId, lhhId, loai);
    		session.setAttribute("userId", userId);
    		obj.setCongtyId((String)session.getAttribute("congtyId"));
    		obj.setTrangthai("0");	
    		obj.setLoaimh(loai);
    		GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			obj.init("");	
			obj.setmsg(msg);
			
	    	session.setAttribute("obj", obj);  	
    		
	
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhau_Giay.jsp");
			
    		return;
		     
	    }
	    else if(action.equals("convert"))
	    {
	    	IErpNhanhang_Giay nhBean = new ErpNhanhang_Giay();
	    	nhBean.setUserId(userId);
	    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    		    	
	    	String nccId = util.antiSQLInspection(request.getParameter("NCCId")); 	    	
	    	nhBean.setNccId(nccId); 
	    	
	    	String hoadonnccId = util.antiSQLInspection(request.getParameter("hoadonnccId"));
	    	nhBean.setHdNccId(hoadonnccId);
	    	
	    	String loaihd = util.antiSQLInspection(request.getParameter("loaihd"));
	    	nhBean.setLoaimh(loaihd);
	    	
	    	String loaihh = util.antiSQLInspection(request.getParameter("loaihh"));
	    	nhBean.setLoaihanghoa(loaihh);
	    	
	    	nhBean.setNdnId("100000");
	    	nhBean.setLdnId("100046");
	    			    
	    	nhBean.init_convert(hoadonnccId, loaihd);
	    	nhBean.createRs();
    		
	    	session.setAttribute("nhBean", nhBean);
	    	session.setAttribute("spList", "");
	    	
	    	System.out.println("NCCId gét:"+nhBean.getNccId());
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauNew_Giay.jsp";
    		response.sendRedirect(nextJSP);
    		
    		return;
	    }
     
	    obj.setTrangthai("0");
	   
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setUserId(userId);
	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    obj.init("");
	    obj.setmsg(msg);
		session.setAttribute("obj", obj);
		session.setAttribute("congtyId", obj.getCongtyId());
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhau_Giay.jsp";
		response.sendRedirect(nextJSP);
	}
	
	Hashtable<String, Hashtable<String, Long> > nhan_form_choSelected = null; 
	
	private String ChotNhanHang (String nhId, String userId, String ctyId, String lhhId, String loaimh) 
	{
		dbutils db = new dbutils();

		try {
			db.getConnection().setAutoCommit(false);

			Utility util = new Utility();

			String query1 = " select ngaynhan ,ISNULL(ISTUDONG,'0') AS ISTUDONG  from erp_nhanhang where trangthai='0' and pk_seq=" + nhId + " ";

			ResultSet rs1 = db.get(query1);
			String ngaychotNV = "";
			String istudong = "0";
			if (rs1.next()) {
				ngaychotNV = rs1.getString("ngaynhan");
				istudong = rs1.getString("ISTUDONG");

			}else{
				db.update("rollback");
				return "Vui lòng kiểm tra lại trạng thái của chứng từ chốt nhận hàng";
			}
			rs1.close();

			String msg1 = util.checkNgayHopLe(db, ngaychotNV);
			if (msg1.length() > 0) {
				db.update("rollback");
				return msg1;
			}

			// Sau nay phai them buoc check xem taikhoan do da co khoasothang
			// chua
			String ngaychot = "";
//			String ngaychungtu = "";
//			String ttId = "";
			String sql =  " SELECT A.NGAYCHOT, A.NGAYNHAN, A.NOIDUNGNHAN_FK, C.TAIKHOAN_FK, ISNULL(NGUONGOCHH, 'TN') AS NGUONGOCHH, A.KHOCHOXULY_FK \n"
						+ " FROM ERP_NHANHANG A  \n" + " LEFT JOIN  \n" + " (   	 \n"
						+ " SELECT DISTINCT TNK.PK_SEQ,HD_MH.MUAHANG_FK FROM ERP_THUENHAPKHAU TNK  	 \n"
						+ " INNER JOIN ERP_THUENHAPKHAU_HOADONNCC TNKHD ON TNK.PK_SEQ=TNKHD.THUENHAPKHAU_FK   \n"
						+ " INNER JOIN ERP_HOADONNCC_DONMUAHANG HD_MH ON TNKHD.HOADONNCC_FK=HD_MH.HOADONNCC_FK \n"
						+ " )TNK_MH ON TNK_MH.PK_SEQ = A.SOTOKHAI_FK  \n"
						+ " LEFT JOIN ERP_MUAHANG B ON ISNULL(A.MUAHANG_FK,TNK_MH.MUAHANG_FK) = B.PK_SEQ  \n"
						+ " LEFT JOIN ERP_NHACUNGCAP C ON A.NCC_KH_FK = C.PK_SEQ WHERE A.PK_SEQ =  " + nhId;
			// sua b.nhacungcap_fk thanh a.ncc_kh_fk

			System.out.println(sql);
			String nguongocHH = "";
//			String taikhoanco_fk = "";
//			String noidungnhan_fk = "";
			String khoChoXuLy = "";
			
			ResultSet rs = db.get(sql);
			if (rs.next()) {
				ngaychot = rs.getString("ngaychot");
				//ngaychungtu = rs.getString("ngaynhan");
//				noidungnhan_fk = rs.getString("noidungnhan_fk");
//				taikhoanco_fk = rs.getString("taikhoan_fk");
				nguongocHH = rs.getString("nguongocHH");
				khoChoXuLy = rs.getString("KHOCHOXULY_FK");

			}
			rs.close();

//			String nam = ngaychot.substring(0, 4);
//			String thang = ngaychot.substring(5, 7);

			String query = "";
			String ChuyenKhoId = "";
		 
				// Lay tai khoan No trong bang config
				// sua them cac truong de insert xuong bang erp_yeucaukiemdinh
				query =   " SELECT distinct nh1.NCC_KH_FK as NCC , NH.KHONHAN," +
						  " sp.kiemtradinhluong,sp.kiemtradinhtinh, sp.BATBUOCKIEMDINH"
						+ " ,NH.MUAHANG_FK,nh.SANPHAM_FK," +
						  "   TIENTE_FK, TYGIAQUYDOI , ISNULL(NH.DONVI, '') AS DONVI, "
						+ " SP.MA AS MASP  ,SP.TEN AS SPTEN  "
						+ " FROM ERP_NHANHANG nh1 inner join "
						+ " ERP_NHANHANG_SANPHAM NH on nh1.PK_SEQ = NH.NHANHANG_FK "
						+ " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NH.SANPHAM_FK   "
						+ " INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK "
						+ " WHERE NH.NHANHANG_FK ='" + nhId + "' and  nh.SOLUONGNHAN > 0";

				System.out.println("_____LAY TK: " + query);
				ResultSet rsSp = db.get(query);
//				double total_soluongNhan = 0;

//				String NCC = "";
				while (rsSp.next()) {
					// bổ sung NCC
//					NCC = rsSp.getString("NCC");
					String sanphamId = rsSp.getString("SANPHAM_FK");
					String khoNhanId = rsSp.getString("KHONHAN");
					String muaHangId = rsSp.getString("MUAHANG_FK");
					// loc san pham nao co chon kiem dinh dinh luong,kiem dinh
					// dinh tinh,bat buoc kiem dinh
					String dinhluong = rsSp.getString("kiemtradinhluong");
					String dinhtinh = rsSp.getString("kiemtradinhtinh");
					String batbuockiemdinh = rsSp.getString("BATBUOCKIEMDINH");
				 
					if (dinhluong == null) {
						dinhluong = "";
					}
					if (dinhtinh == null) {
						dinhtinh = "";
					}
					if(batbuockiemdinh == null){
						batbuockiemdinh = "0";
					}
					
					// xem xét sản phẩm có kiểm định, thì tăng kho chờ xử lý
					// sản phẩm nào không có kiểm định thì tăng kho được chọn
					if (batbuockiemdinh.equals("1")) {
						// tạo phiếu kiểm định 
						// đang tạo cho từng sản phẩm
						List<String> maPhieuL = new ArrayList<String>();
						
						String s = CreatePhieuKiemDinh( db,sanphamId, nhId, muaHangId, dinhluong, dinhtinh,khoChoXuLy,userId,loaimh,maPhieuL);
						
						if(s.trim().length() > 0){
							db.getConnection().rollback();
							db.shutDown();
							return "88.Không thể chôt nhận hàng, vui lòng báo Admin: không tạo được phiếu kiểm định";
						}
						
						
					} else {
						// tăng kho nhận
						// đang tạo cho từng sản phẩm
						String khoId = khoNhanId;
						boolean check  = TangKhoKiemDinh(db, khoId ,nhId,sanphamId,muaHangId,"");
						if(check == false){
							db.getConnection().rollback();
							db.shutDown();
							return "88.Không thể chôt nhận hàng, vui lòng báo Admin: không tạo được phiếu kiểm định";
						}
					}
					// end tao phieu yeu cau kiem dinh
				}
				rsSp.close();

				// tạo phiếu xuất kho hoá chất
				// tạm thời bỏ qua 24/07/2016
				//ChuyenKhoId = this.CreatePhieuYeuCauHoaChat(db, NCC, nhId, userId,khoChoXuLy);
		 

			// Nhan hang se chuyen sang hoan tat
			// cap nhat them chuyenkho_hoachat_fk trong bang erp_nhanhang
			ChuyenKhoId = "NULL";
			query = "Update ERP_NhanHang set trangthai = '1', giochot = '" + getTime() + "',chuyenkho_hoachat_fk=" + ChuyenKhoId+ " where pk_seq = '" + nhId + "' and trangthai=0";
			if (db.updateReturnInt(query)!=1) {
				db.getConnection().rollback();
				db.shutDown();
				return "88.Không thể chôt nhận hàng: " + query;
			}
			
			
			// Chot nhanhang trong nhapkho
			String msg = "";

			if (msg.trim().length() > 0) {
				db.getConnection().rollback();
				// db.shutDown();
				return "Không thể chôt nhận hàng - chi phí lưu kho: " + msg;
			}
			
			
		 
			Library lib=new Library();
			msg1=lib.Capnhat_Ngaynhapkho_Nhanhang(userId, db, nhId);
			if(msg1.length()>0){
				db.getConnection().rollback();
				return msg1;
			}
			// cập nhật kế toán
			msg1=lib.CapNhatKeToanKho_NhanhangMua_SPHH(userId, db, nhId, false, ctyId);
			if(msg1.length()>0){
				db.getConnection().rollback();
				return msg1;
			}
		

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			db.shutDown();

			return "Không thể chôt nhận hàng: " + e.getMessage();
		}
	}
	private boolean TangKhoKiemDinhMaMe(dbutils db, String khoId, String nhanhang_fk, String sanpham, 
			String muahang_fk, String maphieu, String MaMe,String SoLo){

		try{
			// tăng kho chi tiết
		 
			
			String sql= " select ISNULL( a.NGAYNHAPKHO,'') AS  NGAYNHAPKHO , a.SOLO, a.MATHUNG, a.MAME, a.BIN_FK, a.SOLUONG,a.NGAYHETHAN, a.NGAYSANXUAT,c.NGAYNHAN, "+ 
						" a.SANPHAM_FK,a.GIATHEOLO  as DONGIA ,  "+
						"  isnull(a.MARQ,'' ) as mamarquette,a.NSX_FK   "+
						" from ERP_NHANHANG_SP_CHITIET a   "+
						" inner join ERP_NHANHANG c on c.PK_SEQ = a.NHANHANG_FK "+
						" where a.SANPHAM_FK =  "+sanpham+" and a.NHANHANG_FK = "+nhanhang_fk+" "+
						" and a.MUAHANG_FK = "+ muahang_fk +" and a.MAME = '" +MaMe +"'  and a.SOLO ='"+SoLo+"' ";
			
			Utility_Kho util_kho=new Utility_Kho();
			
			
			 
			String date = "";
			
			ResultSet rs = db.get(sql);
			 
				while(rs.next()){
					String sanpham_fk = rs.getString("SANPHAM_FK");
					String solo = rs.getString("SOLO");
					String khu_fk = rs.getString("BIN_FK");
					double soluong= rs.getDouble("SOLUONG");
					String ngayhethan= rs.getString("NGAYHETHAN");
					String ngaysanxuat = rs.getString("NGAYSANXUAT");
				 
					String mamarquette = rs.getString("mamarquette");
					String nsx_fk = rs.getString("NSX_FK")==null?"":rs.getString("NSX_FK");
					String mathung = rs.getString("MATHUNG");
					String mame = rs.getString("MAME");
					date = rs.getString("NGAYNHAPKHO");
					
					Kho_Lib kholib=new Kho_Lib();
					kholib.setLoaichungtu("erpNhanhangnhapkhau 400 ERP_NHANHANG :"+nhanhang_fk);
					kholib.setNgaychungtu(rs.getString("NGAYNHAN"));
					
					kholib.setKhottId(khoId);
					
					kholib.setBinId(khu_fk);
					
					kholib.setSolo(solo);
					kholib.setSanphamId(sanpham_fk);
					kholib.setNsxId(nsx_fk);
					kholib.setMame(mame);
					kholib.setMathung(mathung);
					kholib.setMaphieu(maphieu);
					kholib.setMaphieudinhtinh("");
					kholib.setPhieuEo("");
					
					kholib.setNgayhethan(ngayhethan);
					kholib.setNgaysanxuat(ngaysanxuat);
					
					kholib.setNgaynhapkho(date);
					 
					kholib.setMARQ(mamarquette);
					kholib.setDoituongId("");
					kholib.setLoaidoituong("");
					
					kholib.setHamluong("100");
					kholib.setHamam("0");
					 
			    	kholib.setBooked(0);
					kholib.setSoluong(soluong);
					kholib.setAvailable(soluong);
					kholib.setDongialo(0);
					 
					String msg1= util_kho.Update_Kho_Sp_Tra_NEW(db,kholib);
				    if( msg1.length() >0)
					{
				    	return false;
					}
				    
					 
				}
				rs.close();
			  
			 
			return true;
		} catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
	}
	
	private boolean TangKhoKiemDinh(dbutils db, String khoId, String nhanhang_fk, String sanpham, String muahang_fk, String maphieu){
		try{
	 
			String sql=" SELECT  A.NGAYNHAPKHO,  A.SOLO, A.MATHUNG, A.MAME, A.BIN_FK, A.SOLUONG,A.NGAYHETHAN, A.NGAYSANXUAT,C.NGAYNHAN, "+ 
				" A.SANPHAM_FK,A.GIATHEOLO  AS DONGIA ,  isnull(a.MARQ,'' ) as mamarquette,a.NSX_FK  "+
				" FROM ERP_NHANHANG_SP_CHITIET A   "+
				" INNER JOIN ERP_NHANHANG C ON C.PK_SEQ = A.NHANHANG_FK   "+
				" WHERE A.SANPHAM_FK = "+sanpham+"  AND A.NHANHANG_FK = "+nhanhang_fk+" AND A.MUAHANG_FK ="+muahang_fk; 
				 
			Utility_Kho util_kho=new Utility_Kho();
		 
			 
			ResultSet rs = db.get(sql);
			 
				while(rs.next()){
					String sanpham_fk = rs.getString("SANPHAM_FK");
					String solo = rs.getString("SOLO");
					String khu_fk = rs.getString("BIN_FK");
					double soluong= rs.getDouble("SOLUONG");
					String ngayhethan= rs.getString("NGAYHETHAN");
					String ngaysanxuat = rs.getString("NGAYSANXUAT");
					 
					String mamarquette = rs.getString("mamarquette");
					String nsx_fk = rs.getString("NSX_FK")==null?"":rs.getString("NSX_FK");
					String mathung = rs.getString("MATHUNG");
					String mame = rs.getString("MAME");
					String NGAYNHAN=rs.getString("NGAYNHAPKHO");
					Kho_Lib kholib=new Kho_Lib();
					kholib.setNgaychungtu(NGAYNHAN);
					kholib.setNsxId(nsx_fk);
					kholib.setLoaichungtu("erpNhanhangnhapkhau 484 ERP_NHANHANG :"+nhanhang_fk);
					kholib.setKhottId(khoId);
					
					kholib.setBinId(khu_fk);
					
					kholib.setSolo(solo);
					kholib.setSanphamId(sanpham_fk);
					
					kholib.setMame(mame);
					kholib.setMathung(mathung);
					kholib.setMaphieu(maphieu);
					kholib.setMaphieudinhtinh("");
					kholib.setPhieuEo("");
					
					kholib.setNgayhethan(ngayhethan);
					kholib.setNgaysanxuat(ngaysanxuat);
					
					kholib.setNgaynhapkho(NGAYNHAN);
					 
					kholib.setMARQ(mamarquette);
					kholib.setDoituongId("");
					kholib.setLoaidoituong("");
					
					kholib.setHamluong("100");
					kholib.setHamam("0");
					 
			    	kholib.setBooked(0);
					kholib.setSoluong(soluong);
					kholib.setAvailable(soluong);
					kholib.setDongialo(0);
					 
					String msg1= util_kho.Update_Kho_Sp_Tra_NEW(db,kholib);
				    if( msg1.length() >0)
					{
				    	return false;
					}
				    
				 
				}
				rs.close();
			  
			 
			return true;
		} catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
	}
	 
	private String createMaPhieuTuDong(String sanphamId, String idKiemDinh, dbutils db){
		 /*001-16 (năm)-TP/NL/BTP (loại sản phẩm)*/
		try{
		// truy vấn thông tin loại sản phẩm
		String query = "  select l.MA from ERP_SANPHAM sp left join ERP_LOAISANPHAM l " +
					   "  on sp.LOAISANPHAM_FK = l.PK_SEQ where sp.PK_SEQ ="+ sanphamId;
		
		ResultSet rs = db.get(query);
		String loaiSanPham = "";
		if(rs !=null){
			if(rs.next()){
				loaiSanPham = rs.getString("MA");
			}
			rs.close();
		}
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(d);
		String year = date.substring(2, 4);
		
		
		return idKiemDinh +"-"+year+ "-"+loaiSanPham;
		} catch (Exception ex){
			ex.printStackTrace();
			return "";
		}
		
	}
	
	private String CreatePhieuKiemDinh(dbutils db, String sanphamId, String nhanhangId, String muaHangId, String dinhluong, String dinhtinh,
			String khoId, String userId, String loaimh, List<String> maphieuL){
		
		// tính toán số lượng phiếu kiểm định sẽ được tạo ra
		// số phiếu kiểm định tạo ra sẽ theo mẻ.
		// 1 lô sẽ có 10 thùng, 10 thùng thuộc 2 mẻ
		// tạo ra 2 phiếu kiểm định tương ứng với 2 mẻ.
		try{
		int i;
		String query ="";
//		int soluongMe =0;
		
		query = "  select nh.NGAYNHAN, a.MAME , SUM(a.SOLUONG) as soluong,a.NGAYHETHAN, a.NGAYSANXUAT, a.solo ," +
				"  isnull(a.MARQ,'' ) as mamarquette,a.NSX_FK ,C.PK_SEQ AS IDMARQUETTE " +
				"  from ERP_NHANHANG_SP_CHITIET a \n " +
				"  inner join erp_nhanhang nh on nh.pk_seq= a.nhanhang_fk  \n"
				+ "	LEFT JOIN MARQUETTE C ON C.MA =isnull(a.MARQ,'' ) \n" +
				"  where a.SANPHAM_FK = "+sanphamId+" and a.NHANHANG_FK = "+nhanhangId+" and a.MUAHANG_FK = "+ muaHangId +
				"  group by nh.NGAYNHAN ,  a.MAME, a.SOLO,a.NGAYHETHAN, a.NGAYSANXUAT, isnull(a.MARQ,'' ) , a.NSX_FK,C.PK_SEQ ";
		
		ResultSet rs = db.get(query);
		List<String> maMe = new ArrayList<String>();
		List<Double> soluong = new ArrayList<Double>();
		List<String> ngaySanXuat = new ArrayList<String>();
		List<String> ngayHetHan = new ArrayList<String>();
		List<String> soLo = new ArrayList<String>();
		List<String> idmarquette = new ArrayList<String>();
		List<String> mamarquette = new ArrayList<String>();
		List<String> dongiaViet = new ArrayList<String>();
		List<String> nsx_fk = new ArrayList<String>();
		String ngaynhanhang="";
	 
		while(rs.next()){
			ngaynhanhang =rs.getString("NGAYNHAN");
			maMe.add(rs.getString("MAME"));
			soluong.add(rs.getDouble("soluong"));
			ngaySanXuat.add(rs.getString("NGAYSANXUAT"));
			ngayHetHan.add(rs.getString("NGAYHETHAN"));
			soLo.add(rs.getString("SOLO"));
			idmarquette.add(rs.getString("IDMARQUETTE")==null?"":rs.getString("IDMARQUETTE"));
			mamarquette.add(rs.getString("mamarquette"));
			nsx_fk.add(rs.getString("NSX_FK")==null?"":rs.getString("NSX_FK"));
		 
		}
		 
		
		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
		String s = timeFormat.format(today.getTime());
		for (i = 0; i < maMe.size(); i++) {
			
				// tạo phiếu kiểm định
				query =   " insert into ERP_YeuCauKiemDinh(ngaykiem ,nhanhang_fk,sanpham_fk,khuvuckho_fk,solo,trangthai,ngayhethan,nguoitao,"
						+ " ngaytao,nguoisua,ngaysua,soluong,ngaysanxuat,dinhluong,dinhtinh," + "ngaynhan,KHONHAN_FK,NGAYHETHONG,"
						+ "  nhapkho_fk,loaimuahang,MAME, IDMARQUETTE, HAMAM, HAMLUONG, MAMARQUETTE,NSX_FK)" + " values('"+ngaynhanhang+"',"+ nhanhangId+ ","+ sanphamId+ ",null,'"
						+ soLo.get(i)+ "','0','"+ngayHetHan.get(i)+"',"+ userId+ ",'"+ s+ "',"+ ""+ userId+ ",'"+ s+ "',"
						+ soluong.get(i)+ ",'"+ngaySanXuat.get(i)+"','"
						+ dinhluong+ "','"+ dinhtinh+ "','',"+ khoId+ ",'"+ s+ "' "
						 + ",null,'"+ loaimh + "','" + maMe.get(i)+"',"+(idmarquette.get(i).trim().length() ==0?"NULL":idmarquette.get(i))+",0,100,'"+mamarquette.get(i)+"',"+(nsx_fk.get(i).trim().length()==0?"NULL":nsx_fk.get(i))+")";
	
				if (db.updateReturnInt(query) != 1) {
					
					return "Lỗi dòng lệnh :" + query;
				}
				
				
				
			
			// Lấy phiếu kiểm định vừa tạo ra.
			query = "select SCOPE_IDENTITY() as nhId";
			String idKiemDinh = "";
			ResultSet rs1 = db.get(query);

			if (rs1 != null) {
				System.out.println(" vao dc day");
				try {
					if (rs1.next()) {
						idKiemDinh = rs1.getString("nhId");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				rs1.close();
			}
			
			// Cập nhật mã phiếu tự động cho phiếu kiểm định
			String maphieu = createMaPhieuTuDong(sanphamId,idKiemDinh,db);
			
			query = "Update ERP_YeuCauKiemDinh set MAPHIEU ='"+maphieu+"' where PK_SEQ = "+ idKiemDinh ;
			
			int check = db.updateReturnInt(query);
			if(check !=1){
				return " Không cập nhật được mã phiếu";
			}
			
			
			maphieuL.add(maphieu);
			
			// Lấy dữ liệu về mã thùng và số lượng để insert vào bảng ERP_YEUCAUKIEMDINH_THUNG
			query =  " select MATHUNG,sum(SOLUONG) as soluong  , BIN_FK from ERP_NHANHANG_SP_CHITIET a where NHANHANG_FK = "+nhanhangId
					+" and SANPHAM_FK = "+sanphamId+" " +" and MUAHANG_FK = "+muaHangId+" and SOLO= '"+soLo.get(i)
					+"' and MAME= '"+maMe.get(i)+"'  group by MATHUNG,BIN_FK ";
			
				List<String> maThung = new ArrayList<String>();
				List<Double> soLuongThung = new ArrayList<Double>();
				List<String> khuVuc = new ArrayList<String>();
				rs1 = db.get(query);
			 
				while(rs1.next()){
					maThung.add(rs1.getString("MATHUNG"));
					soLuongThung.add(rs1.getDouble("SOLUONG"));
					khuVuc.add(rs1.getString("BIN_FK"));
				}
				rs1.close();
			 
			
			// tạo bảng lưu chi tiết số thùng lưu vào bảng
			for(int j=0; j< maThung.size(); j++){
				String maLoThung= soLo.get(i);
				
				query = " insert into ERP_YEUCAUKIEMDINH_THUNG(YEUCAUKIEMDINH_FK, MATHUNG, SOLUONG, SOLUONGMAU, SOLUONGDAT, " +
						" SOLUONGKHONGDAT, MALO, BIN_FK)" +
					" values("+idKiemDinh+",'"+ maThung.get(j)+"',"+soLuongThung.get(j)+",0,"+soLuongThung.get(j)+",0,'"+maLoThung+"',"+khuVuc.get(j)+")";
				int k = db.updateReturnInt(query);
				if( k!=1){
					return "Lỗi lưu chi tiết số thùng trong kiểm định";
				}
			}
			
			// chỗ này tăng kho kiểm định lun
			// tăng kho chờ xử lý
			// đang tạo cho từng sản phẩm
			
			query=" select  (select SUM(soluong) from ERP_YEUCAUKIEMDINH_THUNG b  "+
				  " where b.YEUCAUKIEMDINH_FK=a.pk_seq ) ,* from ERP_YeuCauKiemDinh  a "+
				  " where a.soluong <> (select SUM(soluong) from ERP_YEUCAUKIEMDINH_THUNG b "+ 
				  " where b.YEUCAUKIEMDINH_FK=a.pk_seq ) and a.pk_seq="+idKiemDinh;
			ResultSet rscheck=db.get(query);
			if(rscheck.next()){
				return "Số lượng tổng và chi tiết của kiểm định không bằng nhau, vui lòng báo Admin để xử lý trường hợp này";
			}
			rscheck.close();
			
			
			boolean check1  = TangKhoKiemDinhMaMe(db, khoId, nhanhangId,sanphamId, muaHangId, "",maMe.get(i), soLo.get(i));
			if(check1 == false){
				
				return "88.Không thể chôt nhận hàng, vui lòng báo Admin: không tạo được phiếu kiểm định";
			}
		}
		return "";
		} catch(Exception ex){
			ex.printStackTrace();
			return "Lỗi ";
		}
		
		
	}
  
	public boolean CheckDahoantat(dbutils db, String idnhanhang )
	{		
		String query=
			 " SELECT DATA.SANPHAM_FK,DATA.SOLUONG,DATA.SOLUONGDANHAN FROM ( "+ 
			 " SELECT MHSP.SANPHAM_FK,MHSP.SOLUONG ,(SELECT SUM( NHSP.SOLUONGNHAN ) FROM ERP_NHANHANG NH  "+ 
			 " INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ=NHSP.NHANHANG_FK "+ 
			 " WHERE NH.MUAHANG_FK=MH.PK_SEQ AND NHSP.SANPHAM_FK=MHSP.SANPHAM_FK AND NH.TRANGTHAI  IN (1,2) )  AS SOLUONGDANHAN "+ 
			 " FROM ERP_MUAHANG MH  "+ 
			 " INNER JOIN ERP_MUAHANG_SP MHSP ON MH.PK_SEQ=MHSP.MUAHANG_FK "+ 
			 " WHERE MH.PK_SEQ= (select MUAHANG_FK from ERP_NHANHANG where PK_SEQ= "+idnhanhang+")  "+ 
			 " ) DATA WHERE DATA.SOLUONG-DATA.SOLUONGDANHAN >0";
		
		
		ResultSet  kt = db.get(query);
		try{
			 
			if(kt.next()){
				//dang con du lieu co the nhan
				kt.close();
				return false;
			}
			kt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
			
		}
		return true;
	}
			
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNhanhangList_Giay obj;
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
	    String loaimh = util.antiSQLInspection(request.getParameter("loaimh")); 
	    System.out.println("Loai "+loaimh);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpNhanhang_Giay nhBean = new ErpNhanhang_Giay();
	    	nhBean.setUserId(userId);
	    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	nhBean.setLoaimh(loaimh);
	    	nhBean.setLdnId("100000");
	    	nhBean.createRs();
    		
	    	session.setAttribute("nhBean", nhBean);
	    	session.setAttribute("spList", "");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauNew_Giay.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpNhanhangList_Giay();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setLoaimh(loaimh);
		    	this.getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhau_Giay.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpNhanhangList_Giay();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	obj.setLoaimh(loaimh);
		    	this.getSearchQuery(request, obj);
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhau_Giay.jsp");
	    	}
	    }
	}
	
	private void getSearchQuery(HttpServletRequest request, IErpNhanhangList_Giay obj)
	{
//		Utility util=new Utility();
//		String query = " SELECT distinct a.PK_SEQ as nhId, ncc.Ten as nccTen, \n"+
//				" isnull(case when a.hdNCC_fk is null then isnull(a.SOHOADON,'') else ( select isnull(kyhieu,'') + ' ' + CAST(sohoadon as varchar(50)) as ten  from ERP_HOADONNCC where pk_seq = a.hdNCC_fk ) end, '') as SOHOADON, a.NGAYNHAN, b.TEN as dvthTen, \n"+
//				" c.sopo as PoId, \n"+
//				" b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, \n"+
//				" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, a.loaihanghoa_fk, \n"+
//				" case when a.hdNCC_fk IS null then 0  when a.hdNCC_fk IS NOt null then 1  else -1 end DaRaHd, a.tooltip \n"+
//				" FROM erp_nhanhang a "+
//				" inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ \n"+
//				" left join DonTraHang th on a.TRAHANG_FK = th.PK_SEQ \n"+
//				" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ  \n"+
//				" inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n"+
//				" inner join ERP_MUAHANG c on  a.muahang_fk= c.pk_seq \n"+  
//				" INNER JOIN ERP_HOADONNCC f ON A.HDNCC_FK = f.PK_SEQ \n" +
//				" INNER JOIN ERP_PARK g ON f.PARK_FK = g.PK_SEQ  \n" +  
//				" left join ERP_NHACUNGCAP ncc on g.NCC_FK = ncc.pk_seq   \n"+
//		 " where c.LOAI = "+ obj.getLoaimh() +" and a.congty_fk = '" + obj.getCongtyId() + "' and c.SOPO is not null  ";
//		
		 		//" and b.pk_seq in  "+ util.quyen_donvithuchien(obj.getUserId()) ; 		
		
		String khonhanId = request.getParameter("khonhanId");
		if( khonhanId == null){
			khonhanId = "";
		}
		obj.setKhonhanId(khonhanId);
		
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
		
		String sonhanhang = request.getParameter("sonhanhang");
		if(sonhanhang == null)
			sonhanhang = "";
		obj.setSoNhanhang(sonhanhang);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSoHoadon(sohoadon);
		
		String ncc = request.getParameter("ncc");
		if(ncc == null)
			ncc = "";
		obj.setNCC(ncc);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitaoIds(nguoitao);
		
		String mactsp = request.getParameter("mactsp");
		if(mactsp == null)
			mactsp = "";
		obj.setMaCtSp(mactsp);
		
		
		String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
		
//		if( khonhanId.length() >0){
//			query +=" and a.khonhan_fk ='"+ khonhanId+"'";
//		}
//		if(tungay.length() > 0)
//			query += " and a.ngaynhan >= '" + tungay + "'";
//		
//		if(denngay.length() > 0)
//			query += "\n and a.ngaynhan <= '" + denngay + "'";
//		
//		if(dvthId.length() > 0)
//			query += "\n and b.pk_seq = '" + dvthId + "'";
//		
//		if(nguoitao.trim().length() > 0)
//			query += "\n AND a.nguoitao = '" + nguoitao + "' ";
//		
//		
//		if(soPo.length() > 0)
//		{
//			//query += " and ( CAST(c.PK_SEQ as varchar(10)) + '/' + SUBSTRING(c.NGAYMUA, 6, 2) + '/' + SUBSTRING(c.NGAYMUA, 0, 5) like '%" + soPo + "%' ) ";
//		
//			query += "\n  AND ( c.sopo like '%" + soPo.trim() + "%' ) ";
//		}
//		
//		if(trangthai.length() > 0)
//			query += "\n and a.trangthai = '" + trangthai + "'";
//		
//		if(sonhanhang.trim().length() > 0)
//		{
//			query += "\n and b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) like N'%" + sonhanhang + "%'  ";
//		}
//		
//		if(sohoadon.trim().length() > 0)
//		{
//			query += "\n and ( select isnull(kyhieu,'') + ' ' + CAST(sohoadon as varchar(50)) as ten  from ERP_HOADONNCC where pk_seq = a.hdNCC_fk ) like N'%" + sohoadon + "%' ";
//		}
//		
//		if(ncc.trim().length() > 0)
//		{
//			query += "\n and (  ( ( c.pk_seq is not null ) and ( c.NHACUNGCAP_FK in ( select pk_seq from ERP_NHACUNGCAP where ma like N'%" + ncc + "%' or ten like N'%" + ncc + "%' ) ) )              " +
//					 "\n     or ( ( th.pk_seq is not null ) and ( th.KHACHHANG_FK in  ( select pk_seq from ERP_KHACHHANG where ma like N'%" + ncc + "%' and ten like N'%" + ncc + "%'  )  ) ) )   ";
//		}
		
		geso.dms.center.util.Utility  util2= new geso.dms.center.util.Utility();
		
//		if(mactsp.trim().length() > 0)
//		{
			/*query +="\n and a.pk_seq in (" +
					"\n     select distinct nhanhang_fk from erp_nhanhang_sanpham where sanpham_fk in "+
					" \n ( select distinct pk_seq from ERP_SANPHAM where upper(TIMKIEM) like upper(N'%"+  util2.replaceAEIOU( mactsp) + "%') ) " +
					" ) ";*/
			
//			query +="\n and a.pk_seq in (" +
//			"\n     select distinct nhanhang_fk from erp_nhanhang_sanpham where sanpham_fk = "+ mactsp + " ) " ;
//			
//		}
//		
//		
//		
//		//query += " order by a.NGAYNHAN desc, a.trangthai asc, a.pk_seq desc;";
//
//		System.out.println("qr seach: " +query);
//		return query;
	}
	
	private String Delete(String nhId, dbutils db)
	{
		try 
		{
 
				
			String query = "update ERP_NHANHANG set trangthai = '3' where pk_seq = '" + nhId + "' and trangthai=0";
			if(db.updateReturnInt(query)!=1)
			{
				return "Không thể cập nhật ERP_NHANHANG: " + query;
			}
			
			query = "update ERP_PARK set HOANTAT_NHANHANG = '0' where PK_SEQ in ("
				+ "		select park_fk from ERP_HOADONNCC where pk_seq in (" 
				+ "			 select hdNCC_fk from ERP_NHANHANG where PK_SEQ = " + nhId + "))";
				
				System.out.println("Hoàn tất nhận hàng: "+query);
				if(!db.update(query))
				{
					 
					return "Không thể cập nhật ERP_MUAHANG: " + query;
				}
			
			return "";
		} 
		catch (Exception e)
		{ 
			e.printStackTrace();
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
