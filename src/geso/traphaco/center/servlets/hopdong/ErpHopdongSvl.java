package geso.traphaco.center.servlets.hopdong;

import geso.traphaco.center.beans.dondathang.IErpDondathang;
import geso.traphaco.center.beans.dondathang.imp.ErpDondathang;
import geso.traphaco.center.beans.hopdong.IErpHopdong;
import geso.traphaco.center.beans.hopdong.IErpHopdongList;
import geso.traphaco.center.beans.hopdong.imp.ErpHopdong;
import geso.traphaco.center.beans.hopdong.imp.ErpHopdongList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
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

public class ErpHopdongSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHopdongSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHopdongList obj;
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
	    obj = new ErpHopdongList();
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    System.out.println("---LOAI DON HANG: " + loaidonhang);
	    
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
	    else if(action.equals("convert"))
    	{
	    	IErpDondathang lsxBean = new ErpDondathang();
		    lsxBean.setUserId(userId); 
		    
    		String msg = this.Convert(lsxId, lsxBean);
    		if(msg.trim().length() < 10)
    		{
    		    String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangNew.jsp";
    		    
    		    lsxBean.setETC("1");
        		lsxBean.createRs();
        		
        		session.setAttribute("dvkdId", lsxBean.getDvkdId());
    			session.setAttribute("kbhId", lsxBean.getKbhId());
    			session.setAttribute("nppId", lsxBean.getNppId());
    			
    			session.setAttribute("lsxBean", lsxBean);
    	        response.sendRedirect(nextJSP);
    	        
    	        return;
    		}
    		else
    			obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpHopDong.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String Convert(String lsxId, IErpDondathang lsxBean) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			String query =  "select loaidonhang, tungay, case when denngayPL is null then denngay else  denngayPL end as denngay  " +
							"from " +
							"( " +
							"	select loaidonhang, tungay, denngay, " +
							"			( select max(denngay) from ERP_HOPDONG where hopdong_fk = a.pk_seq and trangthai in (1, 2) ) as denngayPL " +
							"	from ERP_HOPDONG a  " +
							"	where pk_seq = '" + lsxId + "'  " +
							") " +
							"HD " +
							"where tungay <= '" + this.getDateTime() + "' and '" + this.getDateTime() + "' <= ( case when denngayPL is null then denngay else  denngayPL end ) ";

			ResultSet rs = db.get(query);
			String loaidonhang = "";
			String tungay = "";
			String denngay = "";
			if(rs.next())
			{
				loaidonhang = rs.getString("loaidonhang");
				tungay = rs.getString("tungay");
				denngay = rs.getString("denngay");
				rs.close();
			}

			//CHECK CON HIEU LUC KHONG
			if(loaidonhang.trim().length() <= 0)
			{
				msg = "Hợp đồng đã hết hạn. Bạn không thể thành đơn hàng.";
				db.getConnection().rollback();
				return msg;
			}

			query = "	select b.MA as spMa,b.TEN as spTEN,c.DONVI as spDONVI,sanpham_fk, a.dvdl_fk as dvDATHANG, b.dvdl_fk as dvCHUAN,  "+  
					"		case when a.dvdl_fk IS null then 1          "+
					"			 when a.dvdl_fk = b.DVDL_FK then 1         "+
					"			 else  isnull( ( select SOLUONG1 /SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK ),-1) end as soluong, "+   
					"		dongia, chietkhau, thueVAT, tungay, denngay     "+
					"	from ERP_HOPDONG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq "+    
					"		inner join DONVIDOLUONG c on c.PK_SEQ=a.dvdl_fk   "+
					"	where HOPDONG_FK = '  "+lsxId+"  '   and a.SoLuong>0  "+
					"		union ALL    "+
					"	select b.ma as spMA,b.TEN as spTEN,c.DONVI as spDONVI,  sanpham_fk, a.dvdl_fk as dvDATHANG, b.dvdl_fk as dvCHUAN, "+  
					"		case when a.dvdl_fk IS null then 1          "+
					"			 when a.dvdl_fk = b.DVDL_FK then 1         "+
					"			 else isnull(  ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK ) ,-1) end as soluong, "+   
					"		dongia, chietkhau, thueVAT, tungay, denngay     "+
					"	from ERP_HOPDONG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq "+   
					"		inner join DONVIDOLUONG c on c.PK_SEQ=a.dvdl_fk   "+
					"	where  a.SoLuong>0 and  HOPDONG_FK in ( select pk_seq from ERP_HOPDONG where hopdong_fk = '  "+lsxId+"  ' and trangthai in (1, 2) ) ";   
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
			
			if(rs != null)
				rs.close();

			if(msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}

			if(loaidonhang.equals("0")) //Hóa đơn bình thường, chỉ được phép đặt bằng số còn lại
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
						"		from ERP_HOPDONG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq  " +
						"		where HOPDONG_FK = '" + lsxId + "'  " +
						"	union ALL " +
						"		select sanpham_fk,  " +
						"			case when a.dvdl_fk IS null then a.soluong       " +
						"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
						"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK=b.DVDL_FK )       " +
						"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK=b.DVDL_FK )	 end as soluong, dongia, chietkhau, thueVAT, b.pk_seq as dvdl_fk, tungay, denngay  " +
						"		from ERP_HOPDONG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq   " +
						"		where HOPDONG_FK in ( select pk_seq from ERP_HOPDONG where hopdong_fk = '" + lsxId + "' and trangthai in (1, 2) ) " +
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
						"		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ      " +
						"		where a.dondathang_fk in (   select pk_seq from ERP_Dondathang where trangthai != '3' and hopdong_fk = '" + lsxId + "'  )     " +
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

			query = "update ERP_HOPDONG set trangthai = '2' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Lỗi khi chuyển sang đơn đặt hàng: " + query;
				db.getConnection().rollback();
				return msg;
			}

			query = " select  mahopdong, tungay, denngay, 0, 1 as npp_dachot, ghichu, 0 as trangthai, dvkd_fk, kbh_fk, gsbh_fk, ddkd_fk, " +
					"	NPP_FK, kho_fk, pk_seq, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua " +
					" from ERP_HOPDONG a where pk_seq = '" + lsxId + "' ";
			System.out.println("-- INIT DDH: " + query );
			rs = db.get(query);
			{
				if(rs.next())
				{
					lsxBean.setMahopdong(rs.getString("pk_seq"));
					lsxBean.setTungay(rs.getString("tungay"));
					lsxBean.setDenngay(rs.getString("denngay"));
					lsxBean.setGhichu(rs.getString("ghichu"));
					lsxBean.setDvkdId(rs.getString("dvkd_fk"));
					lsxBean.setKbhId(rs.getString("kbh_fk"));

					if(rs.getString("NPP_FK") != null)
						lsxBean.setNppId(rs.getString("NPP_FK"));

					lsxBean.setKhoNhapId(rs.getString("kho_fk"));
					lsxBean.setLoaidonhang("0");
					lsxBean.setChietkhau(rs.getString("chietkhau"));
					lsxBean.setVat(rs.getString("vat"));
					lsxBean.setGsbhId(rs.getString("gsbh_fk"));
					lsxBean.setDdkdId(rs.getString("ddkd_fk"));

					if(rs.getString("mahopdong").trim().length() > 0 )  //Chuyển từ hợp đồng thì ko có chiết khấu
						lsxBean.setChietkhau("0");
				}
				rs.close();
			}


			String sqlSOLUONG = "		case when hd.dvCHUAN = hd.dvDATHANG then hd.soluong - isnull(dh.daDAT, 0)  " +
								"			else ( hd.soluong - isnull(dh.daDAT, 0) ) * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = hd.sanpham_fk and DVDL2_FK = hd.dvDATHANG and DVDL1_FK = hd.dvCHUAN ) end as soluong,  ";

			if(loaidonhang.equals("2") || loaidonhang.equals("3")) //Hợp đồng nguyên tắc hoặc hợp đòng chung
				sqlSOLUONG = " isnull(hd.soluong, 0) as soluong, ";

			/*if(loaidonhang.equals("2") || loaidonhang.equals("3")) //Hợp đồng nguyên tắc hoặc hợp đòng chung
				sqlSOLUONG = " 0 as soluong, ";*/

			query = //"insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dvdl_fk, dongia, chietkhau, thueVAT, tungay, denngay )  " +
					//"select '" + msg + "', hd.sanpham_fk, " + sqlSOLUONG +
					"select hd.sanpham_fk, " + sqlSOLUONG +
					"       0 as soluongton "+
					"		, hd.dvDATHANG as dvdl_fk, hd.dongia, hd.chietkhau, hd.thueVAT, hd.tungay, hd.denngay, ISNULL(sp.trongluong, 0) as trongluong, ISNULL(sp.thetich, 0) as thetich, " +
					"		sp.MA, sp.TEN, DV.donvi, (select soluong1/ soluong2 from QUYCACH where sanpham_fk = hd.sanpham_fk and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '100018') as spQuyDoi  " +
					"from  " +
					"(  " +
					"	select sanpham_fk, dvDATHANG, dvCHUAN, sum(soluong) as soluong, avg(dongia) as dongia, avg(chietkhau) as chietkhau, avg(thuevat) as thuevat, tungay, denngay  " +
					"	from  " +
					"	(  " +
					"		select sanpham_fk, a.dvdl_fk as dvDATHANG, b.dvdl_fk as dvCHUAN, " +
					"			case when a.dvdl_fk IS null then a.soluong        " +
					"				 when a.dvdl_fk = b.DVDL_FK then a.soluong       " +
					"				 else  a.soluong * ( select SOLUONG1 /SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK ) end as soluong,  " +
					"			dongia, chietkhau, thueVAT, tungay, denngay   " +
					"		from ERP_HOPDONG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq   " +
					"		where HOPDONG_FK = '" + lsxId + "'   " +
					"	union ALL  " +
					"		select sanpham_fk, a.dvdl_fk as dvDATHANG, b.dvdl_fk as dvCHUAN, " +
					"			case when a.dvdl_fk IS null then a.soluong        " +
					"				 when a.dvdl_fk = b.DVDL_FK then a.soluong       " +
					"				 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK )  end as soluong,  " +
					"			dongia, chietkhau, thueVAT, tungay, denngay   " +
					"		from ERP_HOPDONG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq    " +
					"		where HOPDONG_FK in ( select pk_seq from ERP_HOPDONG where hopdong_fk = '" + lsxId + "' and trangthai in (1, 2) )  " +
					"	)  " +
					"	hopdong group by sanpham_fk, dvDATHANG, dvCHUAN, tungay, denngay  " +
					")  " +
					"hd left join  " +
					"(  " +
					"	select sanpham_fk, sum(soluong) as daDAT  " +
					"	from  " +
					"	(  " +
					"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,       " +
					"				case when a.dvdl_fk IS null then a.soluong        " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong       " +
					"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk  and QUYCACH.DVDL1_FK=b.DVDL_FK )        " +
					"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk   and QUYCACH.DVDL1_FK=b.DVDL_FK) end as soluong   " +
					"		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ       " +
					"		where a.dondathang_fk in (   select pk_seq from ERP_Dondathang where trangthai != '3' and hopdong_fk = '" + lsxId + "'  )      " +
					"	)  " +
					"	dathang group by sanpham_fk  " +
					")  " +
					"dh on hd.sanpham_fk = dh.sanpham_fk " +
					" inner Join SanPham SP on hd.SANPHAM_FK = SP.PK_SEQ " +
					" INNER JOIN DONVIDOLUONG DV ON hd.dvDATHANG = dv.PK_SEQ ";

			if(!loaidonhang.equals("2") && !loaidonhang.equals("3")){
				query += "where hd.soluong > isnull(dh.daDAT, 0) ";
			}
			System.out.println("--CHEN SP: " + query);

			/*System.out.println("--CHEN SP: " + query);
			if(db.updateReturnInt(query) <= 0)
			{
				msg = "Sản phẩm chưa có thiết lập quy cách từ đơn vị đặt về đơn vị chuẩn. Vui lòng kiểm tra lại dữ liệu nền.";
				db.getConnection().rollback();
				return msg;
			}*/

			System.out.println("--INIT SP: " + query);
			rs = db.get(query);
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spVAT = "";
				String spTUNGAY = "";
				String spDENNGAY = "";

				String spTRONGLUONG = "";
				String spTHETICH = "";

				String spSLTON = "";

				String spQuyDoi ="";
				String spScheme = "";
				
				NumberFormat formater = new DecimalFormat("##,###,###.##");

				while(rs.next())
				{
					spMA += rs.getString("MA") + "__";
					spTEN += rs.getString("TEN") + "__";
					spDONVI += rs.getString("DONVI") + "__";
					spSOLUONG += formater.format(rs.getDouble("SOLUONG")) + "__";
					spGIANHAP += rs.getDouble("DONGIA") + "__";
					spCHIETKHAU += formater.format(rs.getDouble("chietkhau")) + "__";
					spVAT += formater.format(rs.getDouble("thueVAT")) + "__";
					spSLTON += formater.format(rs.getDouble("SOLUONGTON")) + "__";

					if(rs.getString("tungay").trim().length() > 0)
						spTUNGAY += rs.getString("tungay") + "__";
					else
						spTUNGAY += " __";

					if(rs.getString("denngay").trim().length() > 0)
						spDENNGAY += rs.getString("denngay") + "__";
					else
						spDENNGAY += " __";

					spTRONGLUONG += rs.getString("trongluong") + "__";
					spTHETICH += rs.getString("thetich") + "__";
					spQuyDoi +=rs.getString("spQuyDoi") + "__";
					spScheme += " __";
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

					spVAT = spVAT.substring(0, spVAT.length() - 2);
					lsxBean.setSpVat(spVAT.split("__"));

					/*spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
					lsxBean.setSpTungay(spTUNGAY.split("__"));

					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
					lsxBean.setSpDenngay(spDENNGAY.split("__"));*/

					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					lsxBean.setSpTrongluong(spTRONGLUONG.split("__"));

					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					lsxBean.setSpThetich(spTHETICH.split("__"));

					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					lsxBean.setSpQuyDoi(spQuyDoi.split("__"));

					/*spSLTON = spSLTON.substring(0, spSLTON.length() - 2);
					lsxBean.setSpSoluongton(spSLTON.split("__"));*/
					
					spScheme = spScheme.substring(0, spScheme.length() - 2);
					lsxBean.setSpScheme(spScheme.split("__"));
				}
			}

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
		
			String query = "update ERP_HOPDONG set trangthai = '1' where pk_seq = '" + lsxId + "'";
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

			String query = "delete ERP_HopDong_SanPham where hopdong_fk = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "1.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_HopDong_ChietKhau where hopdong_fk = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_HopDong where pk_seq = '" + lsxId + "'";
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
	    System.out.println("action: "+ action);
	    if (action == null)
	    {
	    	action = "";
	    }
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    
		IErpHopdongList obj = new ErpHopdongList();
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    obj.setUserId(userId);
	    
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpHopdong lsxBean = new ErpHopdong();
	    	
	    	lsxBean.setUserId(userId);
	    	
	    	lsxBean.setLoaidonhang(loaidonhang);
	    	
	    	lsxBean.createRs();
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpHopDongNew.jsp";
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
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Center/ErpHopDong.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setUserId(userId);
		    	obj.init(search);
				
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpHopDong.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpHopdongList obj)
	{
		//Utility util = new Utility();
		
		String query = "select a.PK_SEQ, a.trangthai, a.mahopdong, a.tungay, a.denngay, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE,  c.MaFAST as mafast   " +
						"from ERP_HopDong a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0  ";
				
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
		
	/*	String ddkdId = request.getParameter("ddkdId");
		System.out.println("trinh duoc vien: "+ddkdId);
		if(ddkdId == null)
			ddkdId = "";
		obj.setDvkdId(ddkdId);*/
		
		String mafast = request.getParameter("mafast");
		if(mafast == null)
			mafast = "";
		obj.setmaFas(mafast);
		
		
		if(tungay.length() > 0)
			query += " and a.tungay >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.denngay <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}
		
		if(mafast.length() > 0)
			query += " and c.MaFAST like '%" + mafast + "%'";
		
		System.out.print("timkiem" + query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
