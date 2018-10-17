package geso.traphaco.distributor.servlets.hopdong;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.beans.hopdong.IErpDonhangNppETC;
import geso.traphaco.distributor.beans.hopdong.IErpHopdongNpp;
import geso.traphaco.distributor.beans.hopdong.IErpHopdongNppList;
import geso.traphaco.distributor.beans.hopdong.imp.ErpDonhangNppETC;
import geso.traphaco.distributor.beans.hopdong.imp.ErpHopdongNpp;
import geso.traphaco.distributor.beans.hopdong.imp.ErpHopdongNppList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpHopdongNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHopdongNppSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHopdongNppList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
    
	    HttpSession session = request.getSession();	    

	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    
	    Utility util = new Utility();
	       
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	   
	    String action = util.getAction(querystring);
	   
    	String lsxId = util.getId(querystring);
	    obj = new ErpHopdongNppList();
	    
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    
	    if (action.equals("delete") )
	    {	
	    	String msg = this.DeleteChuyenKho(lsxId);
			obj.setMsg(msg);
	    }
	    else if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId);
			obj.setMsg(msg);
    	}
	    else if(action.equals("mochot"))
    	{
    		String msg = this.MoChot(lsxId);
			obj.setMsg(msg);
    	}
	    else if(action.equals("convert"))
    	{
	    	IErpDonhangNppETC lsxBean = new ErpDonhangNppETC();
	    	lsxBean.setLoaidonhang("1");
	    	lsxBean.setUserId(userId); 
	    	lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	lsxBean.setNpp_duocchon_id(npp_duocchon_id);
	    	lsxBean.setCtyId( session.getAttribute("congtyId").toString() );
	    	
    		String msg = this.Convert(lsxId, lsxBean);
    		System.out.println("::: 1.KENH BAN HANG: " + lsxBean.getKbhId() );
    		if(msg.trim().length() <= 10)
    		{
        		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCNew.jsp";
        		lsxBean.setMahopdong(lsxId);
        		
        		lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
        		lsxBean.setDoituongId(session.getAttribute("doituongId"));
        	    
        		lsxBean.createRs( session.getAttribute("tdv_dangnhap_id").toString() );
        		
        		System.out.println("::: 2.KENH BAN HANG: " + lsxBean.getKbhId() );
    			session.setAttribute("dvkdId", lsxBean.getDvkdId());
				session.setAttribute("kbhId", lsxBean.getKbhId());
				session.setAttribute("khoId", lsxBean.getKhoNhapId());
				session.setAttribute("nppId", lsxBean.getNppId());
				session.setAttribute("hopdongId", lsxBean.getMahopdong());
				session.setAttribute("loaidonhang", lsxBean.getLoaidonhang());
				
				session.setAttribute("soHopDong", lsxBean.getSohopdong());
    			
    			session.setAttribute("lsxBean", lsxBean);
    	        response.sendRedirect(nextJSP);
    	        
    	        return;
    		}
    		else
    			obj.setMsg(msg);
    	}
	    System.out.println("user id :"+userId);
	    obj.setUserId(userId);
	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNpp.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String MoChot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "update ERP_HOPDONGNPP set trangthai = '0', ngaymochot = getdate() where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Khong the mo chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "update ERP_HOPDONGNPP set hieuluc = '1' where pk_seq in ( select HOPDONG_FK from ERP_HOPDONGNPP where PK_SEQ = '" + lsxId + "' ) ";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	private String Convert(String lsxId, IErpDonhangNppETC lsxBean) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
		
			/*String query = 	"select loaidonhang, tungay, denngay " +
							"from ERP_HOPDONGNPP where pk_seq = '" + lsxId + "' and tungay <= '" + getDateTime() + "' and '" + getDateTime() + "' <= denngay ";*/
			String query =  "select loaidonhang, hieuluc, tungay, case when denngayPL is null then denngay else  denngayPL end as denngay  " +
							"from " +
							"( " +
							"	select loaidonhang, hieuluc, tungay, denngay, " +
							"			( select max(denngay) from ERP_HOPDONGNPP where hopdong_fk = a.pk_seq and trangthai in (1, 2) ) as denngayPL " +
							"	from ERP_HOPDONGNPP a  " +
							"	where pk_seq = '" + lsxId + "'  " +
							") " +
							"HD " +
							"where tungay <= '" + this.getDateTime() + "' and '" + this.getDateTime() + "' <= ( case when denngayPL is null then denngay else  denngayPL end ) ";
			System.out.println("kiem tra hop dong "+query);
			ResultSet rs = db.get(query);
			String loaihopdong = "";
			String hieuluc = "0";
			if(rs.next())
			{
				loaihopdong = rs.getString("loaidonhang");
				hieuluc = rs.getString("hieuluc");
				rs.close();
			}
			
			//CHECK CON HIEU LUC KHONG
			if(loaihopdong.trim().length() <= 0 || hieuluc.equals("0") )
			{
				msg = "Hợp đồng đã hết hạn. Bạn không thể chuyển thành đơn hàng.";
				db.getConnection().rollback();
				return msg;
			}
			
			query = "	select b.MA as spMa,b.TEN as spTEN,c.DONVI as spDONVI,sanpham_fk, a.dvdl_fk as dvDATHANG, b.dvdl_fk as dvCHUAN,  "+  
					"		case when a.dvdl_fk IS null then 1          "+
					"			 when a.dvdl_fk = b.DVDL_FK then 1         "+
					"			 else  isnull( ( select SOLUONG1 /SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK ),-1) end as soluong, "+   
					"		dongia, chietkhau, thueVAT, tungay, denngay     "+
					"	from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq "+    
					"		inner join DONVIDOLUONG c on c.PK_SEQ=a.dvdl_fk   "+
					"	where HOPDONG_FK = '  "+lsxId+"  '   and a.SoLuong>0  "+
					"		union ALL    "+
					"	select b.ma as spMA,b.TEN as spTEN,c.DONVI as spDONVI,  sanpham_fk, a.dvdl_fk as dvDATHANG, b.dvdl_fk as dvCHUAN, "+  
					"		case when a.dvdl_fk IS null then 1          "+
					"			 when a.dvdl_fk = b.DVDL_FK then 1         "+
					"			 else isnull(  ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK ) ,-1) end as soluong, "+   
					"		dongia, chietkhau, thueVAT, tungay, denngay     "+
					"	from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq "+   
					"		inner join DONVIDOLUONG c on c.PK_SEQ=a.dvdl_fk   "+
					"	where  a.SoLuong>0 and  HOPDONG_FK in ( select pk_seq from ERP_HOPDONGNPP where hopdong_fk = '  "+lsxId+"  ' and trangthai in (1, 2) ) ";   
			rs=db.get(query);
			msg="";
			while(rs.next())
			{
				int SoLuong =rs.getInt("SoLuong");
				if(SoLuong == -1)
				{
					msg += "Sản phẩm "+rs.getString("spMa")+"-"+rs.getString("spTEN")+"-"+rs.getString("spDONVI")+ " chưa khai báo quy đổi ! \n";
				}
			}
			if(rs!=null)rs.close();
			
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			if(loaihopdong.equals("0")) //Hóa đơn bình thường, chỉ được phép đặt bằng số còn lại
			{
				query = "select count(*) as soDONG  " +
						"from " +
						"( " +
						"	select sanpham_fk, dvdl_fk, sum(soluong) as soluong, avg(dongia) as dongia, avg(chietkhau) as chietkhau, avg(thuevat) as thuevat, tungay, denngay " +
						"	from " +
						"	( " +
						"		select sanpham_fk,  " +
						"			case when a.dvdl_fk IS null then a.soluong       " +
						"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
						"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK=b.DVDL_FK )       " +
						"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk  and DVDL1_FK=b.DVDL_FK )	 end as soluong, dongia, chietkhau, thueVAT, b.pk_seq as dvdl_fk, tungay, denngay  " +
						"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq  " +
						"		where HOPDONG_FK = '" + lsxId + "'  " +
						"	union ALL " +
						"		select sanpham_fk,  " +
						"			case when a.dvdl_fk IS null then a.soluong       " +
						"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
						"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK=b.DVDL_FK )       " +
						"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK=b.DVDL_FK )	 end as soluong, dongia, chietkhau, thueVAT, b.pk_seq as dvdl_fk, tungay, denngay  " +
						"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq   " +
						"		where HOPDONG_FK in ( select pk_seq from ERP_HOPDONGNPP where hopdong_fk = '" + lsxId + "' and trangthai in (1, 2) ) " +
						"	) " +
						"	hopdong group by sanpham_fk, dvdl_fk, tungay, denngay " +
						") " +
						"hd left join " +
						"( " +
						"	select sanpham_fk, sum(soluong) as daDAT " +
						"	from " +
						"	( " +
						"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " +
						"				case when a.dvdl_fk IS null then a.soluong       " +
						"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
						"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK=b.DVDL_FK )       " +
						"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK=b.DVDL_FK ) end as soluong  " +
						"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ      " +
						"		where a.dondathang_fk in (   select pk_seq from ERP_DondathangNPP where trangthai != '3' and hopdong_fk = '" + lsxId + "'  )     " +
						"	) " +
						"	dathang group by sanpham_fk " +
						") " +
						"dh on hd.sanpham_fk = dh.sanpham_fk " +
						"where hd.soluong > isnull(dh.daDAT, 0) ";  //KHONG CON SP NAO
				
				System.out.println("----CHECK SANPHAM: " + query );
				rs = db.get(query);
				int soDONG = 0;
				if(rs.next())
				{
					soDONG = rs.getInt("soDONG");
				}
				rs.close();
				
				if(soDONG <= 0)
				{
					msg = "Hợp đồng đã chuyển hết thành SO. Bạn không thể chuyển tiếp.";
					db.getConnection().rollback();
					return msg;
				}
			}
				
			query = "update ERP_HOPDONGNPP set trangthai = '2' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Lỗi khi chuyển sang đơn đặt hàng: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			/*query = " insert ERP_DondathangNPP(ngaydonhang, ngaydenghi, loaidonhang, npp_dachot, ghichu, trangthai, dvkd_fk, kbh_fk, gsbh_fk, ddkd_fk, khachhang_fk, npp_fk, kho_fk, hopdong_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
					" select tungay, denngay, 0, 1 as npp_dachot, ghichu, 0 as trangthai, dvkd_fk, kbh_fk, gsbh_fk, ddkd_fk, khachhang_fk, npp_fk, kho_fk, pk_seq, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua " +
					" from ERP_HOPDONGNPP where pk_seq = '" + lsxId + "' ";
			System.out.println("-- INSERT DDH: " + query );
			if(db.updateReturnInt(query) < 1 )
			{
				msg = "Lỗi khi chuyển sang đơn đặt hàng: " + query;
				db.getConnection().rollback();
				return msg;
			}*/
			
			query =  "select  mahopdong, '" + this.getDateTime() + "' as tungay, 0, 1 as npp_dachot, a.ghichu, 0 as trangthai, dvkd_fk, " + 
					 "   (   select top(1) PK_SEQ from KENHBANHANG " +
					 "		 where TRANGTHAI = '1' and pk_seq in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' )  " +
					 "				and PK_SEQ in ( select kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = ISNULL( a.khachhang_fk, ( select top(1) khachhang_fk from ERP_HOPDONGNPP_APDUNG where hopdong_fk = a.pk_seq order by pk_seq asc ) ) ) ) as kbh_fk, gsbh_fk, ddkd_fk,   " + 
					 "	ISNULL( a.khachhang_fk, ( select top(1) khachhang_fk from ERP_HOPDONGNPP_APDUNG where hopdong_fk = a.pk_seq order by pk_seq asc ) ) as khachhang_fk, a.npp_fk, a.kho_fk, a.pk_seq, chietkhau, vat, a.ngaytao, a.nguoitao, a.ngaysua, a.nguoisua,  " + 
					 "	Isnull( ( select dungchungkenh from NHAPHANPHOI where pk_seq = a.npp_fk ), 0 ) as dungchungkenh , " + 
					 "	case when isnull(b.CAPDOGIAOHANG, 0) >= 24 then convert( varchar(10), DATEADD(dd, 1, a.DenNgay), 120 )  " + 
					 "		 else convert( varchar(10), DATEADD(hh, b.CAPDOGIAOHANG, '" + this.getDateTime() + "'), 120 ) end as denngay " + 
					 "from ERP_HOPDONGNPP a left join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ " + 
					 "where a.pk_seq = '" + lsxId + "' ";
			System.out.println("-- INIT DDH: " + query );
			rs = db.get(query);
			{
				if(rs.next())
				{
					lsxBean.setMahopdong(rs.getString("pk_seq"));
					lsxBean.setTungay(rs.getString("tungay"));
					lsxBean.setDenngay( rs.getString("denngay") == null ? this.getDateTime() : rs.getString("denngay") );
					lsxBean.setGhichu(rs.getString("ghichu"));
					lsxBean.setDvkdId(rs.getString("dvkd_fk"));
					lsxBean.setKbhId(rs.getString("kbh_fk") == null ? "" : rs.getString("kbh_fk") );
					lsxBean.setKhId(rs.getString("khachhang_fk") == null ? "" : rs.getString("khachhang_fk") );
					lsxBean.setKhoNhapId(rs.getString("kho_fk") == null ? "" : rs.getString("kho_fk") );
					lsxBean.setChietkhau(rs.getString("chietkhau"));
					lsxBean.setVat(rs.getString("vat"));
					lsxBean.setGsbhId(rs.getString("gsbh_fk") == null ? "" : rs.getString("gsbh_fk") );
					lsxBean.setDdkdId(rs.getString("ddkd_fk") == null ? "" : rs.getString("ddkd_fk") );
					
					if(rs.getString("mahopdong").trim().length() > 0 )  //Chuyển từ hợp đồng thì ko có chiết khấu
						lsxBean.setChietkhau("0");
					
					lsxBean.setDungchungKenh(rs.getString("dungchungkenh"));
				}
				rs.close();
			}
			
			if( lsxBean.getKbhId().trim().length() <= 0 && !loaihopdong.equals("3") )
			{
				msg = "Lỗi khi xác định kênh bán hàng của khách hàng";
				db.getConnection().rollback();
				return msg;
			}
			
			System.out.println("::: KHACH HANG LAY DUOC: " + lsxBean.getKhId() );
			
			//NẾU KÊNH INS THÌ LÚC BẤM CHUYỂN SẼ RA DANH SÁCH SP
			if( lsxBean.getKbhId().equals("100056") )
			{
				String sqlSOLUONG = "		case when hd.dvCHUAN = hd.dvDATHANG then hd.soluong - isnull(dh.daDAT, 0)  " +
									"			else ( hd.soluong - isnull(dh.daDAT, 0) ) * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = hd.sanpham_fk and DVDL2_FK = hd.dvDATHANG and DVDL1_FK = hd.dvCHUAN ) end as soluong,  ";

				if(loaihopdong.equals("2") || loaihopdong.equals("3")) //Hợp đồng nguyên tắc hoặc hợp đòng chung
					sqlSOLUONG = " isnull(hd.soluong, 0) as soluong, ";


				query = "select hd.sanpham_fk, " + sqlSOLUONG +
						//"       (select kho.available from nhapp_kho kho where kho.sanpham_fk=SP.pk_seq and kho.KHO_FK= "+ lsxBean.getKhoNhapId() +" and NPP_FK=(select npp_fk from ERP_HOPDONGNPP where pk_seq = "+ lsxId +") and kho.KBH_FK=100025 )as soluongton "+
						"		0 as soluongton " +
						"		,hd.dvDATHANG as dvdl_fk, hd.dongia, hd.chietkhau, hd.thueVAT, hd.tungay, hd.denngay, hd.ddkd_fk, ISNULL(sp.trongluong, 0) as trongluong, ISNULL(sp.thetich, 0) as thetich, " +
						"		sp.MA, sp.TEN, DV.donvi, (select soluong1/ soluong2 from QUYCACH where sanpham_fk = hd.sanpham_fk and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '100018') as spQuyDoi  " +
						"from  " +
						"(  " +
						"	select sanpham_fk, dvDATHANG, dvCHUAN, sum(soluong) as soluong, avg(dongia) as dongia, avg(chietkhau) as chietkhau, avg(thuevat) as thuevat, tungay, denngay, ddkd_fk  " +
						"	from  " +
						"	(  " +
						"		select sanpham_fk, a.dvdl_fk as dvDATHANG, b.dvdl_fk as dvCHUAN, " +
						"			case when a.dvdl_fk IS null then a.soluong        " +
						"				 when a.dvdl_fk = b.DVDL_FK then a.soluong       " +
						"				 else  a.soluong * ( select SOLUONG1 /SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK ) end as soluong,  " +
						"			dongia, chietkhau, thueVAT, tungay, denngay, a.ddkd_fk   " +
						"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq   " +
						"		where HOPDONG_FK = '" + lsxId + "'   " +
						"	union ALL  " +
						"		select sanpham_fk, a.dvdl_fk as dvDATHANG, b.dvdl_fk as dvCHUAN, " +
						"			case when a.dvdl_fk IS null then a.soluong        " +
						"				 when a.dvdl_fk = b.DVDL_FK then a.soluong       " +
						"				 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK )  end as soluong,  " +
						"			dongia, chietkhau, thueVAT, tungay, denngay, a.ddkd_fk   " +
						"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq    " +
						"		where HOPDONG_FK in ( select pk_seq from ERP_HOPDONGNPP where hopdong_fk = '" + lsxId + "' and trangthai in (1, 2) )  " +
						"	)  " +
						"	hopdong group by sanpham_fk, dvDATHANG, dvCHUAN, tungay, denngay, ddkd_fk  " +
						")  " +
						"hd left join  " +
						"(  " +
						"	select sanpham_fk, sum(soluong) as daDAT  " +
						"	from  " +
						"	(  " +
						"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,       " +
						"				case when a.dvdl_fk IS null then a.soluong        " +
						"					 when a.dvdl_fk = b.DVDL_FK then a.soluong       " +
						"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )        " +
						"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk ) end as soluong   " +
						"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ       " +
						"		where a.dondathang_fk in (   select pk_seq from ERP_DondathangNPP where trangthai != '3' and hopdong_fk = '" + lsxId + "'  )      " +
						"	)  " +
						"	dathang group by sanpham_fk  " +
						")  " +
						"dh on hd.sanpham_fk = dh.sanpham_fk " +
						" inner Join SanPham SP on hd.SANPHAM_FK = SP.PK_SEQ " +
						" INNER JOIN DONVIDOLUONG DV ON hd.dvDATHANG = dv.PK_SEQ where 1 = 1 ";

				if(!loaihopdong.equals("2") && !loaihopdong.equals("3"))
					query += " and hd.soluong > isnull(dh.daDAT, 0) ";
				
				//Nếu hợp đồng nguyên tắc thì chỉ hiện những SP từng mua
				if( loaihopdong.equals("2") )
					query += " and SP.pk_seq in ( select sanpham_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk in ( select PK_SEQ from ERP_DONDATHANGNPP where TRANGTHAI != 3 and HOPDONG_FK = '" + lsxId + "' ) )  ";

				System.out.println("--INIT SP: " + query);
				rs = db.get(query);
				{
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spSOLUONG = "";
					String spGIANHAP = "";
					String spCHIETKHAU = "";

					String spTUNGAY = "";
					String spDENNGAY = "";

					String spTRONGLUONG = "";
					String spTHETICH = "";

					String spSLTON = "";

					String spQuyDoi ="";
					String spTDV = "";

					String spGIANHAPGOC = "";
					String spTHUEVAT = "";
					String spSCHEME = "";
					String ptChietkhau_KMBH = "";

					String spDagiao = "";

					NumberFormat formater = new DecimalFormat("#,###,####");

					while(rs.next())
					{
						spMA += rs.getString("MA") + "__";
						spTEN += rs.getString("TEN") + "__";
						spDONVI += rs.getString("DONVI") + "__";

						//spSOLUONG += formater.format(rs.getDouble("SOLUONG")) + "__";

						//PHANAM YEU CAU KHONG TU DONG DE XUAT SO LUONG
						spSOLUONG += " __";

						spGIANHAP += rs.getDouble("DONGIA") + "__";
						spGIANHAPGOC += rs.getDouble("DONGIA") + "__";
						spCHIETKHAU += formater.format(rs.getDouble("chietkhau")) + "__";
						spTHUEVAT += formater.format(rs.getDouble("thueVAT")) + "__";
						spSLTON += formater.format(rs.getDouble("SOLUONGTON")) + "__";

						if(rs.getString("tungay").trim().length() > 0)
							spTUNGAY += rs.getString("tungay") + "__";
						else
							spTUNGAY += this.getDateTime() + "__";

						if(rs.getString("denngay").trim().length() > 0)
							spDENNGAY += rs.getString("denngay") + "__";
						else
							spDENNGAY += this.getDateTime() + "__";

						if(rs.getString("ddkd_fk") != null )
							spTDV += rs.getString("ddkd_fk") + "__";
						else
							spTDV += lsxBean.getDdkdId() + " __";

						spTRONGLUONG += rs.getString("trongluong") + "__";
						spTHETICH += rs.getString("thetich") + "__";
						spQuyDoi +=rs.getString("spQuyDoi") + "__";

						spSCHEME += " __";
						ptChietkhau_KMBH += "0__";
						spDagiao +="0__";
					}
					rs.close();

					if(spMA.trim().length() > 0)
					{
						spMA = spMA.substring(0, spMA.length() - 2);
						lsxBean.setSpMa(spMA.split("__"));

						spTEN = spTEN.substring(0, spTEN.length() - 2);
						lsxBean.setSpTen(spTEN.split("__"));

						spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
						lsxBean.setSpDonvi(spDONVI.split("__"));

						spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
						lsxBean.setSpSoluong(spSOLUONG.split("__"));

						spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
						lsxBean.setSpGianhap(spGIANHAP.split("__"));

						spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
						lsxBean.setSpChietkhau(spCHIETKHAU.split("__"));

						spTHUEVAT = spTHUEVAT.substring(0, spTHUEVAT.length() - 2);
						lsxBean.setSpVat(spTHUEVAT.split("__"));

						spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
						lsxBean.setSpTungay(spTUNGAY.split("__"));

						spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
						lsxBean.setSpDenngay(spDENNGAY.split("__"));

						spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
						lsxBean.setSpTrongluong(spTRONGLUONG.split("__"));

						spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
						lsxBean.setSpThetich(spTHETICH.split("__"));

						spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
						lsxBean.setSpQuyDoi(spQuyDoi.split("__"));

						spSLTON = spSLTON.substring(0, spSLTON.length() - 2);
						lsxBean.setSpSoluongton(spSLTON.split("__"));

						spTDV = spTDV.substring(0, spTDV.length() - 2);
						lsxBean.setSpTDV(spTDV.split("__"));


						spGIANHAPGOC = spGIANHAPGOC.substring(0, spGIANHAPGOC.length() - 2);
						lsxBean.setSpGianhapGOC(spGIANHAPGOC.split("__"));

						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						lsxBean.setSpScheme(spSCHEME.split("__"));

						ptChietkhau_KMBH = ptChietkhau_KMBH.substring(0, ptChietkhau_KMBH.length() - 2);
						lsxBean.setSpChietkhauBHKM(ptChietkhau_KMBH.split("__"));

						spDagiao = spDagiao.substring(0, spDagiao.length() - 2);
						lsxBean.setSpDagiao(spDagiao.split("__"));
					}
				}
			}
			
			/*query = "insert ERP_DONDATHANGNPP_CHIETKHAU(DONDATHANG_FK, DIENGIAI, GIATRI, LOAI) " +
					"select '" + msg + "', DIENGIAI, GIATRI, LOAI from ERP_HOPDONGNPP_CHIETKHAU where hopdong_fk = '" + lsxId + "' ";
			System.out.println("--CHEN SP 2: " + query);
			if(!db.update(query))
			{
				msg = "Lỗi khi chuyển sang đơn đặt hàng: " + query;
				db.getConnection().rollback();
				return msg;
			}*/
			
			//CHECK BOOKED THEO DV CHUAN
			/*query =  "select khoxuat_fk as kho_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT,  " +
					"	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and kbh_fk = dathang.kbh_fk and npp_fk = dathang.npp_fk ), 0) as tonkho  " +
					"from     " +
					"(     " +
					"	select c.kho_fk as khoxuat_fk, c.npp_fk, case when ( select dungchungkenh from NHAPHANPHOI where PK_SEQ = c.npp_fk ) = 1 then 100025 else c.KBH_FK end as KBH_FK, " +
					"		a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
					"			case when a.dvdl_fk IS null then a.soluong      " +
					"				 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
					"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )      " +
					"								 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )	 end as soluong   " +
					"	from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
					"			inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq    " +
					"	where a.dondathang_fk in ( " + msg + " )     " +
					")     " +
					"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
					"group by khoxuat_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN  ";
			
			System.out.println("--CHECK TON KHO: " + query);
			
			rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String khoID = rs.getString("kho_fk");
					String kbhID = rs.getString("kbh_fk");
					String nppID = rs.getString("npp_fk");
					String spID = rs.getString("PK_SEQ");
					
					double soluong = rs.getDouble("soluongXUAT");
					double tonkho = rs.getDouble("tonkho");
					
					if(soluong > tonkho)
					{
						msg = "Sản phẩm ( " + rs.getString("TEN") + " ) với số lượng yêu cầu ( " + rs.getString("soluongXUAT") + " ) không đủ tồn kho ( " + rs.getString("tonkho") + " ). Vui lòng kiểm tra lại.";
						db.getConnection().rollback();
						rs.close();
						return msg;
					}
					
					//CAP NHAT KHO XUAT TONG
					query = "Update NHAPP_KHO set booked = booked + '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
							"where KHO_FK = '" + khoID + "' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' ";
					if(!db.update(query))
					{
						msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						db.getConnection().rollback();
						rs.close();
						return msg;
					}
				}
				rs.close();
			}*/
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			e.printStackTrace();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		
		return msg;
	}

	private String Chot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "update ERP_HOPDONGNPP set trangthai = '1' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "update ERP_HOPDONGNPP set hieuluc = '0' where pk_seq in ( select HOPDONG_FK from ERP_HOPDONGNPP where PK_SEQ = '" + lsxId + "' ) ";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
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

			String query = "delete ERP_HopDongNpp_SanPham where hopdong_fk = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "1.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_HopDongNpp_ChietKhau where hopdong_fk = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_HopDongNpp where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "3.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
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
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    
		IErpHopdongNppList obj = new ErpHopdongNppList();
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    obj.setUserId(userId);
	    
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    System.out.println("[action] "+action);
	    if(action.equals("Tao moi"))
	    {
	    	IErpHopdongNpp lsxBean = new ErpHopdongNpp();
	    	
	    	lsxBean.setUserId(userId);
	    	
	    	lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	lsxBean.setDoituongId(session.getAttribute("doituongId"));
	    	lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	lsxBean.setNpp_duocchon_id(npp_duocchon_id);
		    
	    	lsxBean.setLoaidonhang(loaidonhang);
	    	
	    	lsxBean.createRs();
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNppNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else if (action.equals("excel"))
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "Attachment; filename=XNT_Thau.xlsm");
			OutputStream out = response.getOutputStream();
			try
			{
				obj.setUserId(userId);
				obj.setTdv_dangnhap_id(tdv_dangnhap_id);
				obj.setNpp_duocchon_id(npp_duocchon_id);
				CreatePivotTable(out, response, request, obj); 
				
			} catch (Exception ex)
			{
				obj.setMsg(ex.getMessage());
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		}
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj.setUserId(userId);
	    		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
				obj.setNpp_duocchon_id(npp_duocchon_id);
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNpp.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setUserId(userId);
		    	obj.setTdv_dangnhap_id(tdv_dangnhap_id);
				obj.setNpp_duocchon_id(npp_duocchon_id);
		    	obj.init(search);
				
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHopDongNpp.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpHopdongNppList obj)
	{
		//Utility util = new Utility();
		geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();
		
		String query = "select distinct a.PK_SEQ, a.trangthai, a.mahopdong, a.loaidonhang, a.tungay, a.denngay, isnull(c.ten, '') as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, a.hieuluc  " +
						"from ERP_HopDongNPP a inner join KHO b on a.kho_fk = b.pk_seq left join KHACHHANG c on a.khachhang_FK = c.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
						"inner join ERP_HOPDONGNPP_SANPHAM dh_sp on a.pk_seq = dh_sp.hopdong_fk " + 
						" where a.pk_seq > 0  and a.kho_fk in "+util.quyen_kho(obj.getUserId());
		String tungay = request.getParameter("tungay");					
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setKhTen(khId);
		
		String mahd = request.getParameter("mahd");
		if(mahd == null)
			mahd = "";
		obj.setMaHD(mahd);
		
		String loaihdid=request.getParameter("loaihdId");
		if(loaihdid == null)
			loaihdid = "";
		obj.setLoaiHD(loaihdid);	
		
		String ddkdId = request.getParameter("ddkdId");
		if(ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);
		
		String soHopDong = request.getParameter("soHopDong");
		if(soHopDong == null)
		{
			soHopDong = "";
		}
		obj.setSoHopDong(soHopDong);
		
		String hieuLuc = request.getParameter("hieuLuc");
		if(hieuLuc == null)
			hieuLuc = "2";			
		obj.setHieuLuc(hieuLuc);
		
		if(soHopDong.length() > 0)
		{
			query += " and a.pk_seq like N'%"+soHopDong+"%' ";
		}
		
		if(tungay.length() > 0)
			query += " and a.tungay >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.denngay <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(nppId.length() > 0)
			query += " and a.NPP_FK = '" + nppId + "'";
		
		if(khId.length() > 0)
		{
			query += " and a.KhachHang_FK = '" + khId + "'";
		}
		
		/*if(hieuLuc.trim().length() > 0 )
		{
			if( hieuLuc.equals("0") )
				query += "and a.denngay >= '" + getDateTime() + "' and hieuluc = '1' ";				
			else if(hieuLuc.equals("1")) //hết hiệu lực
				query += "and ( a.denngay < '" + getDateTime() + "' or hieuluc = '0' ) ";
		}*/
		
		if(loaihdid.length()>0)
			query += " and a.loaidonhang = '"+loaihdid+"'";
		
		if(mahd.length()>0)
			query += " and a.mahopdong like N'%"+mahd+"%'";
		
		if( ddkdId.trim().length() > 0 )
			query += " and dh_sp.ddkd_fk = '" + ddkdId + "' ";
		
		System.out.print("VUna_query : " + query);
		return query;
	}
		
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, IErpHopdongNppList obj) throws Exception
	{
		dbutils db = new dbutils();
		try
		{
			
			String strfstream = getServletContext().getInitParameter("path") + "\\XNT_Thau.xlsm";
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
	  		Worksheet worksheet = worksheets.getSheet("sheet1");
	  		
	  		Cells cells = worksheet.getCells();
			   
	  		Cell cell = cells.getCell("P1");
			Style style1=cell.getStyle();
			
			this.TaoBaoCao(db,worksheet, obj,"XUẤT NHẬP TỒN THẦU",style1);
			
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			db.shutDown();
			ex.printStackTrace();
			
			throw new Exception("Error Message: " + ex.getMessage());
		}
		db.shutDown();
	}
	
	private void TaoBaoCao(dbutils db, Worksheet worksheet, IErpHopdongNppList obj, String diengiai, Style style12) 
	{
		try{  
			Utility util = new Utility();
			Cells cells = worksheet.getCells();
			for(int i=0;i<30;i++){
				cells.setColumnWidth(i, 10.0f);   
			}
			
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 14, diengiai);
	   
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + this.getDateTime());
		
			cell = cells.getCell("P1");
			Style style1=cell.getStyle();

			worksheet.setGridlinesVisible(false);
			ResultSet rs;
			String query = 
					"select a.PK_SEQ as N'ID hợp đồng', a.MaHopDong as N'Mã hợp đồng', a.TuNgay as N'Từ ngày', a.DenNgay as N'Đến ngày', "+ 
 "case a.LoaiDonHang when 0 then N'Bình thường' else N'Nguyên tắc' end as N'Loại hợp đồng', "+
 "d.maFAST as N'Mã khách hàng', d.TEN as N'Tên khách hàng', c.MA_FAST as N'Mã sản phẩm', c.TEN as N'Tên sản phẩm', "+
 "e.DONVI as N'Đơn vị đặt', b.soluong as N'Tồn đầu', "+
 "ISNULL( ( select SUM(soluong) from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
 "    where dh.HOPDONG_FK = a.PK_SEQ and dh_sp.sanpham_fk = c.PK_SEQ and dh.TRANGTHAI != 3  ), 0 ) as N'Đã xuất', "+
 "b.soluong - "+
 "ISNULL( ( select SUM(soluong) from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
 "    where dh.HOPDONG_FK = a.PK_SEQ and dh_sp.sanpham_fk = c.PK_SEQ and dh.TRANGTHAI != 3  ), 0 ) as N'Còn lại',  "+
 "f.DONVI as N'Đơn vị chuẩn', "+
 "case when e.pk_Seq = f.PK_SEQ then b.soluong "+     
"	 else  b.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = e.PK_SEQ and dvdl1_fk = f.PK_SEQ ) "+       
"	 / ( select SOLUONG2 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = e.PK_SEQ and dvdl1_fk = f.PK_SEQ ) end as N'Tồn đầu', "+     
" case when e.pk_Seq = f.PK_SEQ then b.soluong      "+
	 "else  ISNULL( ( select SUM(soluong) from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
     "where dh.HOPDONG_FK = a.PK_SEQ and dh_sp.sanpham_fk = c.PK_SEQ and dh.TRANGTHAI != 3  ), 0 )  "+
     "* ( select SOLUONG1 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = e.PK_SEQ and dvdl1_fk = f.PK_SEQ ) "+       
	 "/ ( select SOLUONG2 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = e.PK_SEQ and dvdl1_fk = f.PK_SEQ ) end as N'Đã xuất', "+
 "(case when e.pk_Seq = f.PK_SEQ then b.soluong      "+
"	 else  b.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = e.PK_SEQ and dvdl1_fk = f.PK_SEQ ) "+       
"	 / ( select SOLUONG2 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = e.PK_SEQ and dvdl1_fk = f.PK_SEQ ) end ) -       "+
" (case when e.pk_Seq = f.PK_SEQ then b.soluong      "+
	 "else  ISNULL( ( select SUM(soluong) from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
     "where dh.HOPDONG_FK = a.PK_SEQ and dh_sp.sanpham_fk = c.PK_SEQ and dh.TRANGTHAI != 3  ), 0 )  "+
     "* ( select SOLUONG1 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = e.PK_SEQ and dvdl1_fk = f.PK_SEQ ) "+       
	 "/ ( select SOLUONG2 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = e.PK_SEQ and dvdl1_fk = f.PK_SEQ ) end ) as N'Còn lại' "+
"from ERP_HOPDONGNPP a inner join ERP_HOPDONGNPP_SANPHAM b on a.PK_SEQ = b.hopdong_fk "+
" inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ "+
" inner join KHACHHANG d on a.KHACHHANG_FK = d.PK_SEQ "+
" left join DONVIDOLUONG e on b.dvdl_fk = e.PK_SEQ "+
" left join DONVIDOLUONG f on c.DVDL_FK = f.PK_SEQ "+
"where a.PK_SEQ not in ( select PK_SEQ from ERP_HOPDONGNPP where MaHopDong like '%GESO%' ) "+
"order by a.PK_SEQ asc";
	   
			System.out.println("query " + query);
			rs = db.get(query);
	   
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
	   
			int countRow = 4;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );
				Style s = cell.getStyle();
				s.setTextWrapped(true);
				s.setHAlignment(TextAlignmentType.CENTER);
				s.setVAlignment(TextAlignmentType.JUSTIFY);
				cell.setStyle(s);
				ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
				
				ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, rsmd.getColumnName(i));
    	
			}
			countRow ++;
	   
			NumberFormat formatter = new DecimalFormat("#,###,###");
			while(rs.next())
			{
				for(int i = 1; i <= socottrongSql; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
					{
						cell.setStyle(style12);
						ReportAPI.getCellStyle_double(cell, "Arial", Color.BLACK, false, 9,  rs.getDouble(i));
						// ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
						ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString(i));
						// ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
	   
			if(rs!=null)rs.close();

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
