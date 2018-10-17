package geso.traphaco.erp.beans.dutoanvattu.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.dutoanvattu.IDonvi;
import geso.traphaco.erp.beans.dutoanvattu.IErpDutoanvattu;
import geso.traphaco.erp.beans.dutoanvattu.INhacungcap;
import geso.traphaco.erp.beans.dutoanvattu.ISanpham;
import geso.traphaco.erp.beans.dutoanvattu.ITiente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpDutoanvattu implements IErpDutoanvattu
{
	String congtyId;
	String userId;
	String ctyId;
	
	
	

	String cty;
	String id;

	String dnmhId;
	ResultSet dnmhList;
	
	String timNCCId;
	ResultSet timNCCList;
	
	String NguonGocHH;
	String MaLoaiHH;
	
	String TienTe_FK;
	/*ResultSet tienteList ;*/
	
	List<ITiente> tienteList=new ArrayList<ITiente>();
	public List<ITiente> getTienteList() {
		return tienteList;
	}

	public void setTienteList(List<ITiente> tienteList) {
		this.tienteList = tienteList;
	}

	String GhiChu;
	float TyGiaQuyDoi;
	String ngaydutoan;
	String dvthId;
	ResultSet dvthRs;

	String trangthai;
	String lhhId;
	String sochungtu;
	String maDMH;
	
	Long tiGiaNguyenTe;
	
	public Long getTiGiaNguyenTe() {
		return tiGiaNguyenTe;
	}

	public void setTiGiaNguyenTe(Long tiGiaNguyenTe) {
		this.tiGiaNguyenTe = tiGiaNguyenTe;
	}

	String[] duyetIds;
	ResultSet lhhRs;
	List<ISanpham> spList;
	List<IDonvi> dvList;
	List<INhacungcap> nhacungcapList;
	
	String msg;
	String lspId;
	
	String nppId;
	
	dbutils db;
	
	private Utility util;

	public ErpDutoanvattu()
	{
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = "";
		this.ngaydutoan = "";
		this.dvthId = "";
		this.trangthai = "";
		this.sochungtu = "";
		this.lhhId = "";
		this.msg = "";
		
		this.dnmhId = "";
		this.timNCCId = "";
		
		this.NguonGocHH = "";
		this.MaLoaiHH = "";
		this.TienTe_FK = "";
		this.GhiChu = "";
		this.maDMH="";
		
		this.TyGiaQuyDoi = 1;
		this.spList = new ArrayList<ISanpham>();
		this.dvList = new ArrayList<IDonvi>();
        this.nhacungcapList = new ArrayList<INhacungcap>();
		
		this.lspId = "";
		this.nppId = "";
		this.db = new dbutils();
		this.util=new Utility();
	}

	public ErpDutoanvattu(String id)
	{
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = id;
		this.ngaydutoan = "";
		this.dvthId = "";
		this.trangthai = "";
		this.sochungtu = "";
		this.lhhId = "";
		this.msg = "";
		
		this.dnmhId = "";
		this.timNCCId = "";
		
		this.MaLoaiHH = "";
		this.NguonGocHH = "";
		this.TienTe_FK = "";
		this.GhiChu = "";
		this.maDMH="";
		
		this.TyGiaQuyDoi = 1;
		this.spList = new ArrayList<ISanpham>();
		this.dvList = new ArrayList<IDonvi>();
		this.nhacungcapList = new ArrayList<INhacungcap>();

		this.lspId = "";
		this.nppId = "";
		this.db = new dbutils();
		this.util=new Utility();
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}

	public String getCty()
	{
		return this.cty;
	}

	public void setCty(String cty)
	{
		this.cty = cty;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
		this.nppId = util.getIdNhapp(userId);
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDvthId()
	{
		return this.dvthId;
	}

	public void setDvthId(String dvthid)
	{
		this.dvthId = dvthid;
	}

	public ResultSet getDvthList()
	{
		return this.dvthRs;
	}

	public void setDvthList(ResultSet dvthlist)
	{
		this.dvthRs = dvthlist;
	}


	public String getLoaispId()
	{
		return this.lspId;
	}

	public void setLoaispId(String loaispid)
	{
		this.lspId = loaispid;
	}

	public ResultSet getLoaiList()
	{
		return this.lhhRs;
	}

	public void setLoaiList(ResultSet loaihhlist)
	{
		this.lhhRs = loaihhlist;
	}

	public List<ISanpham> getSpList()
	{
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}

	public String[] getDuyetIds()
	{
		return this.duyetIds ;
	}

	public void setDuyetIds(String[] duyetIds)
	{
		this.duyetIds = duyetIds;
	}

	public void createRs(boolean po)
	{
		NumberFormat formatterVND = new DecimalFormat("#,###,###");
		NumberFormat formatterVND_4sole = new DecimalFormat("#,###,###.####");
		NumberFormat formattergia_usd = new DecimalFormat("#,###,###.##########");
		
		
		/*String sql = " SELECT PK_SEQ, CAST(PK_SEQ as nvarchar(50)) AS SODENGHI "
				   + "\n FROM ERP_DENGHIMUAHANG "
				   + "\n WHERE CONGTY_FK = "+ this.congtyId +" AND PK_SEQ IN (SELECT DENGHIMUA_FK FROM ERP_TIMNCC WHERE TRANGTHAI = 1 )"
				   + "\n       AND PK_SEQ NOT IN (SELECT DENGHI_FK FROM ERP_DUTOANVATTU	 WHERE TRANGTHAI in ('1','2') ";
		*/
		
		// gan buoc chi lay nhung de nghi mua hang chua co trong  du toan vat tu 
		String sql = " SELECT PK_SEQ, CAST(PK_SEQ as nvarchar(50)) AS SODENGHI "
			   + "\n FROM ERP_DENGHIMUAHANG "
			   + "\n WHERE CONGTY_FK = "+ this.congtyId +" AND PK_SEQ IN (SELECT DENGHIMUA_FK FROM ERP_TIMNCC WHERE TRANGTHAI = 1 )"
			   + "\n       AND PK_SEQ NOT IN (SELECT DENGHI_FK FROM ERP_DUTOANVATTU	 WHERE TRANGTHAI in ('0','1','2') ";
	
		
		
		if(this.id.trim().length() > 0 ) sql+= " AND PK_SEQ != "+ this.id +" ";	
		
		/*if(this.lhhId.equals("0")|| this.lhhId.equals("1")||this.lhhId.equals("2") ){
			sql+= "\n ) AND LOAIHANGHOA_FK='"+this.lhhId+"' ";
		}
		else
		{
			sql+= "\n ) AND LOAIHANGHOA_FK='-1' ";
		}*/

		sql += " ) ORDER BY SODENGHI desc ";		
		System.out.println("dnmh RS " + sql);
		this.dnmhList = db.get(sql);
				
		 /*sql=" select pk_seq, ma + ' - ' +ten as ten from ERP_TIENTE  where trangthai = '1' " ;
		 this.tienteList = db.get(sql);*/
		 
		 
		 //======== danh sach tien te va ty gia quy doi
		 
		 
		sql = " select distinct ERP_TIENTE.PK_SEQ, ERP_TIENTE.MA, ERP_TIGIA.TIGIAQUYDOI "
				+ " from ERP_TIENTE inner join  ERP_TIGIA on ERP_TIENTE.PK_SEQ = ERP_TIGIA.TIENTE_FK "
				+ " where ERP_TIENTE.Trangthai = '1' and ERP_TIGIA.Trangthai = '1' "
				+ " and ERP_TIGIA.CONGTY_FK = "+ this.congtyId 
				+ " order by ERP_TIENTE.PK_SEQ ASC";

		System.out.println(" tien te / ty gia: "+ sql);
		ResultSet tiente = db.get(sql);
		this.tienteList.clear();
		if (tiente != null) {
			try {
				while (tiente.next()) {
					
					if(tiente.getString("pk_seq").equals(this.TienTe_FK)){
						this.tiGiaNguyenTe =tiente.getLong("TIGIAQUYDOI");
					}
					this.tienteList.add(new Tiente(tiente.getString("pk_seq"), tiente.getString("ma"),
							tiente.getString("TIGIAQUYDOI")));
				}
				tiente.close();
			} catch (SQLException e) {
			}
		}
		 
		 
		 ///==================
		 
		 
		 
			
	     sql=" select pk_seq, ten from ERP_DONVITHUCHIEN  where trangthai = '1' " ;
		 this.dvthRs = db.get(sql);
		
		 sql = "SELECT PK_SEQ , CAST(PK_SEQ as nvarchar(20)) AS MA FROM ERP_TIMNCC WHERE CONGTY_FK = "+ this.congtyId +"  ";
		 this.timNCCList = db.get(sql);
		
		this.lhhRs = db.get("Select pk_seq, ten, ma From Erp_LoaiSanPham where TRANGTHAI = '1' and CONGTY_FK = "+ this.congtyId +" ");
			
		// Lay TT : DVTH, Loai HH
//		String sql_TT = "";
//		String sql_SP = "";
		
		if(!po)
		{
			if(this.id.trim().length() > 0)
			{
				sql = " SELECT DISTINCT NCC.PK_SEQ AS ID, NCC.TEN AS TENNCC, ISNULL(DT_NCC.TONGTIENBVAT,0) BVAT, ISNULL(DT_NCC.TONGTIENAVAT,0) AVAT,ISNULL(DT_NCC.VAT,0) VAT \n"+
					  " FROM ERP_DUTOANVATTU DT  \n"+
					  "      INNER JOIN ERP_DUTOANVATTU_NCC DT_NCC ON DT.PK_SEQ = DT_NCC.DTVT_FK \n"+
					  "      INNER JOIN ERP_NHACUNGCAP NCC ON DT_NCC.NCC_FK = NCC.PK_SEQ \n"+					  
					  " WHERE DT.PK_SEQ = "+ this.id +" ";
					ResultSet rs = db.get(sql);
					List<INhacungcap> nccList = new ArrayList<INhacungcap>();
					
					try
					{
						INhacungcap ncc = null;
						if(rs!= null)
						{
							while(rs.next())
							{
								String nccId = rs.getString("ID");
								String nccTen = rs.getString("TENNCC");
								String bvat = formatterVND.format(rs.getDouble("BVAT"));
								String vat =  formatterVND.format(rs.getDouble("VAT"));
								String avat = formatterVND.format(rs.getDouble("AVAT"));
								
								ncc = new Nhacungcap(nccId, nccTen, bvat, vat, avat);
								
								 
								
								String query = 
									" SELECT  DISTINCT  DTSP.PK_SEQ as pk_seq,   CASE WHEN DTSP.SANPHAM_FK IS NOT NULL THEN SP.PK_SEQ \n"+
									"        WHEN DTSP.TAISAN_FK IS NOT NULL THEN TSCD.PK_SEQ  \n"+
									"		 WHEN DTSP.CCDC_FK IS NOT NULL THEN CCDC.PK_SEQ  \n"+
									"		 ELSE CP.PK_SEQ END as IDSP,  \n"+
									"	   CASE WHEN DTSP.SANPHAM_FK IS NOT NULL THEN SP.MA \n"+
							        "      WHEN DTSP.TAISAN_FK IS NOT NULL THEN TSCD.ma \n"+
							        "      WHEN DTSP.CCDC_FK IS NOT NULL THEN CCDC.MA \n"+
							        "      ELSE CP.TEN END as MASP, \n"+
									"      CASE WHEN DTSP.SANPHAM_FK IS NOT NULL THEN SP.TEN   \n"+
							        "      WHEN DTSP.TAISAN_FK IS NOT NULL THEN DTSP.diengiai  \n"+
							        "      WHEN DTSP.CCDC_FK IS NOT NULL THEN DTSP.DIENGIAI \n"+
							        "      ELSE DTSP.DIENGIAI END as TENSP, \n"+
							        
							        "      DTSP.DONVI, DTSP.SOLUONG, ISNULL(DTSP.DONGIA,0) DONGIA , ISNULL(DTSP.THANHTIEN,0) THANHTIEN  \n"+
							        " FROM ERP_DUTOANVATTU DT  \n"+
							        "      INNER JOIN ERP_DUTOANVATTU_SANPHAM DTSP ON DTSP.DTVT_FK = DT.PK_SEQ \n"+
							        "      LEFT JOIN ERP_SANPHAM SP ON DTSP.SANPHAM_FK = SP.PK_SEQ \n"+
							        "      LEFT JOIN ERP_MASCLON TSCD ON DTSP.TAISAN_FK = TSCD.PK_SEQ \n"+
							        "      LEFT JOIN ERP_NHOMCHIPHI CP ON DTSP.CHIPHI_FK = CP.PK_SEQ \n"+
							        "      LEFT JOIN ERP_CONGCUDUNGCU CCDC ON DTSP.CCDC_FK = CCDC.PK_SEQ \n"+							        
							        " WHERE DT.PK_SEQ = '"+ this.id +"' AND DTSP.NCC_FK = "+ nccId +"  ORDER by DTSP.PK_SEQ ASC ";
								
								
								System.out.println("Load SP1 "+ query);
								ResultSet rsSP = db.get(query);
								List<ISanpham> spList = new ArrayList<ISanpham>();
								
								if(rsSP!= null)
								{
									ISanpham sp = null;
									
									while(rsSP.next())
									{
										String spId = rsSP.getString("IDSP") == null?"0":rsSP.getString("IDSP") ;
										String spMa = rsSP.getString("MASP") == null?"":rsSP.getString("MASP") ; ;
										String spTen = rsSP.getString("TENSP");
										String spDonvi = rsSP.getString("DONVI");
										
										/*String soluong = formatterVND.format(rsSP.getDouble("SOLUONG"));*/
										String soluong = formatterVND_4sole.format(rsSP.getDouble("SOLUONG"));
										String dongia="";
										if(this.TienTe_FK.equals("")){
										 dongia =  formatterVND_4sole.format(rsSP.getDouble("DONGIA")); 
										}
										else{
										 dongia =  formattergia_usd.format(rsSP.getDouble("DONGIA"));
										}
										
										String thanhtien = formatterVND.format(rsSP.getDouble("THANHTIEN")); 
															
										sp = new Sanpham(spId, spMa, spTen, soluong, this.TienTe_FK, spDonvi, dongia, thanhtien, nccId);
										spList.add(sp);
									}
									rsSP.close();
								}
								
								ncc.setSanPham(spList);
								nccList.add(ncc);
								
								this.nhacungcapList = nccList;
							}
							rs.close();
						}
						
					}catch(Exception e)
					{
						e.printStackTrace();
					}
			}
			else
			{
				if( this.dnmhId.trim().length() > 0)
				{
					sql = " select DISTINCT DN.DONVITHUCHIEN_FK, DN.LOAIHANGHOA_FK LOAIHH, DN.NGUONGOCHH, DN.LOAISANPHAM_FK, DN.TIENTE_FK, ISNULL(DN.TYGIAQUYDOI,1) TYGIAQUYDOI, TNCC.PK_SEQ as TNCCID "
						+ " from ERP_DENGHIMUAHANG DN INNER JOIN ERP_TIMNCC TNCC ON DN.PK_SEQ = TNCC.DENGHIMUA_FK "
						+ " where DN.PK_SEQ = "+ this.dnmhId +" ";
					System.out.println("\n lay loai hang hoa: "+sql +"\n");
				    ResultSet rsTT = db.get(sql) ;
					if (rsTT != null)
					{
						try
						{
							while (rsTT.next())
							{
								this.dvthId = rsTT.getString("DONVITHUCHIEN_FK") == null ? "":rsTT.getString("DONVITHUCHIEN_FK");
								this.TienTe_FK = rsTT.getString("TIENTE_FK") == null ? "":rsTT.getString("TIENTE_FK");
								this.TyGiaQuyDoi = Float.parseFloat(rsTT.getString("TYGIAQUYDOI"));
								this.lhhId = rsTT.getString("LOAIHH");
								this.NguonGocHH = rsTT.getString("NGUONGOCHH");
								this.timNCCId = rsTT.getString("TNCCID"); 
										
							}
							rsTT.close();
						}
						catch (SQLException e) { }
					}	
			
			
					// Load NCC & SP
			
					sql = " SELECT DISTINCT NCC.PK_SEQ AS ID, NCC.TEN AS TENNCC, 0 BVAT, 0 AVAT, 0 VAT \n"+
						  " FROM ERP_DENGHIMUAHANG DN INNER JOIN ERP_TIMNCC TNCC ON DN.PK_SEQ = TNCC.DENGHIMUA_FK \n"+
						  "      INNER JOIN ERP_TIMNCC_NCC TNCC_CT ON TNCC.PK_SEQ = TNCC_CT.TIMNCC_FK \n"+
						  "      INNER JOIN ERP_NHACUNGCAP NCC ON TNCC_CT.NCC_FK = NCC.PK_SEQ \n"+				  
						  " WHERE TNCC.TRANGTHAI = '1' AND DN.PK_SEQ = "+ this.dnmhId +" ";
					ResultSet rs = db.get(sql);
					System.out.println("Cau load NCC "+ sql);
					List<INhacungcap> nccList = new ArrayList<INhacungcap>();
					
					try
					{
						INhacungcap ncc = null;
						if(rs!= null)
						{
							while(rs.next())
							{
								String nccId = rs.getString("ID");
								String nccTen = rs.getString("TENNCC");
								String bvat = formatterVND.format(rs.getDouble("BVAT"));
								String vat =  formatterVND.format(rs.getDouble("VAT"));
								String avat = formatterVND.format(rs.getDouble("AVAT"));
								
								ncc = new Nhacungcap(nccId, nccTen, bvat, vat, avat);
								
							 
								
								/*String query = 
									" SELECT DISTINCT CASE WHEN TNCC_CT.SP_FK IS NOT NULL THEN SP.PK_SEQ \n"+
									"            WHEN TNCC_CT.TAISAN_FK IS NOT NULL THEN TSCD.PK_SEQ  \n"+
									"		     WHEN TNCC_CT.CCDC_FK IS NOT NULL THEN CCDC.PK_SEQ  \n"+
									"		ELSE CP.PK_SEQ END as IDSP,  \n"+
									"	   CASE WHEN TNCC_CT.SP_FK IS NOT NULL THEN SP.MA \n"+
							        "      WHEN TNCC_CT.TAISAN_FK IS NOT NULL THEN TSCD.ma \n"+
							        "      WHEN TNCC_CT.CCDC_FK IS NOT NULL THEN CCDC.MA \n"+
							        "      ELSE CP.TEN END as MASP, \n"+
									"      CASE WHEN TNCC_CT.SP_FK IS NOT NULL THEN SP.TEN   \n"+
							        "      WHEN TNCC_CT.TAISAN_FK IS NOT NULL THEN DNSP.diengiai    \n"+
							        "      WHEN TNCC_CT.CCDC_FK IS NOT NULL THEN DNSP.diengiai \n"+
							        " 	   WHEN TNCC_CT.CHIPHI_FK IS NOT NULL THEN DNSP.diengiai \n"+
							        "      ELSE CP.DIENGIAI END as TENSP, \n"+
							        
							        
							        "      DNSP.DONVI, DNSP.SOLUONG, DNSP.SOLUONGDUYET, 0 DONGIA , 0 THANHTIEN ,  DNSP.DIENGIAI \n"+
							        
							        
							        " FROM ERP_TIMNCC TNCC INNER JOIN ERP_TIMNCC_NCC TNCC_CT ON TNCC.PK_SEQ = TNCC_CT.TIMNCC_FK \n"+
							        "      INNER JOIN ERP_DENGHIMUAHANG_SP DNSP ON TNCC.DENGHIMUA_FK = DNSP.DENGHI_FK  \n"+
							        "      LEFT JOIN ERP_SANPHAM SP ON TNCC_CT.SP_FK = SP.PK_SEQ  AND DNSP.SANPHAM_FK = SP.PK_SEQ \n"+
							        "      LEFT JOIN ERP_MASCLON TSCD ON TNCC_CT.TAISAN_FK = TSCD.PK_SEQ AND DNSP.TAISAN_FK = TSCD.PK_SEQ \n"+
							        "      LEFT JOIN ERP_NHOMCHIPHI CP ON TNCC_CT.CHIPHI_FK = CP.PK_SEQ AND DNSP.CHIPHI_FK = CP.PK_SEQ \n"+
							        "      LEFT JOIN ERP_CONGCUDUNGCU CCDC ON TNCC_CT.CCDC_FK = CCDC.PK_SEQ AND DNSP.CCDC_FK = CCDC.PK_SEQ \n"+						        
							        " WHERE DNSP.DENGHI_FK = '"+ this.dnmhId +"' AND TNCC.TRANGTHAI = 1 AND TNCC_CT.NCC_FK = "+ nccId +" AND  \n"+
							        "      ( CASE WHEN TNCC_CT.SP_FK IS NOT NULL THEN SP.PK_SEQ  \n"+
							        "        WHEN TNCC_CT.TAISAN_FK IS NOT NULL THEN TNCC_CT.TAISAN_FK   \n"+
							    	"	     WHEN TNCC_CT.CCDC_FK IS NOT NULL THEN TNCC_CT.CCDC_FK   \n"+
							    	"	    ELSE CP.PK_SEQ END) is not null \n"+
							        "";
								*/
								String query=" SELECT   CASE WHEN TNCC_CT.SP_FK IS NOT NULL THEN SP.PK_SEQ  \n " + 
								  " WHEN TNCC_CT.TAISAN_FK IS NOT NULL THEN TSCD.PK_SEQ   \n " + 
								  "  WHEN TNCC_CT.CCDC_FK IS NOT NULL THEN CCDC.PK_SEQ   \n " + 
								  " ELSE CP.PK_SEQ END as IDSP,   \n " + 
								  " CASE WHEN TNCC_CT.SP_FK IS NOT NULL THEN SP.MA  \n " + 
								  " WHEN TNCC_CT.TAISAN_FK IS NOT NULL THEN TSCD.ma  \n " + 
								  " WHEN TNCC_CT.CCDC_FK IS NOT NULL THEN CCDC.MA  \n " + 
								  " ELSE CP.TEN END as MASP,  \n " + 
								  " CASE WHEN TNCC_CT.SP_FK IS NOT NULL THEN SP.TEN    \n " + 
								  " WHEN TNCC_CT.TAISAN_FK IS NOT NULL THEN DNSP.diengiai     \n " + 
								  " WHEN TNCC_CT.CCDC_FK IS NOT NULL THEN DNSP.diengiai  \n " + 
								  " WHEN TNCC_CT.CHIPHI_FK IS NOT NULL THEN DNSP.diengiai  \n " + 
								  " ELSE CP.DIENGIAI END as TENSP,  \n " + 
								  " DNSP.DONVI, DNSP.SOLUONG, DNSP.SOLUONGDUYET, 0 DONGIA , 0 THANHTIEN ,  DNSP.DIENGIAI  \n " + 
								  " FROM	ERP_DENGHIMUAHANG DNM INNER JOIN   \n " + 
								  " ERP_DENGHIMUAHANG_SP DNSP ON DNM.PK_SEQ= DNSP.DENGHI_FK \n " + 
								  " INNER JOIN  ERP_TIMNCC TNCC  ON DNM.PK_SEQ=TNCC.DENGHIMUA_FK \n " + 
								  " INNER JOIN ERP_TIMNCC_NCC TNCC_CT ON TNCC.PK_SEQ = TNCC_CT.TIMNCC_FK  \n " + 
								  " AND TNCC.DENGHIMUA_FK = DNSP.DENGHI_FK    \n " + 
								  " AND ISNULL(DNSP.CCDC_FK,0)= ISNULL(TNCC_CT.CCDC_FK,0) \n " + 
								  " AND  ISNULL(DNSP.TAISAN_FK,0)= ISNULL(TNCC_CT.TAISAN_FK,0) \n " + 
								  " AND  ISNULL(DNSP.SANPHAM_FK,0)= ISNULL(TNCC_CT.sp_fk,0) \n " + 
								  " AND  ISNULL(DNSP.CHIPHI_FK,0)= ISNULL(TNCC_CT.CHIPHI_FK,0) \n " + 
								  " AND RTRIM(LTRIM(ISNULL(DNSP.DIENGIAI,'')))=RTRIM(LTRIM(ISNULL(TNCC_CT.DIENGIAI,''))) \n " + 
								  " LEFT JOIN ERP_SANPHAM SP ON TNCC_CT.SP_FK = SP.PK_SEQ  AND DNSP.SANPHAM_FK = SP.PK_SEQ  \n " + 
								  " LEFT JOIN ERP_MASCLON TSCD ON TNCC_CT.TAISAN_FK = TSCD.PK_SEQ AND DNSP.TAISAN_FK = TSCD.PK_SEQ  \n " + 
								  " LEFT JOIN ERP_NHOMCHIPHI CP ON TNCC_CT.CHIPHI_FK = CP.PK_SEQ AND DNSP.CHIPHI_FK = CP.PK_SEQ    \n " + 
								  " LEFT JOIN ERP_CONGCUDUNGCU CCDC ON TNCC_CT.CCDC_FK = CCDC.PK_SEQ AND DNSP.CCDC_FK = CCDC.PK_SEQ  \n " + 
								  " WHERE DNSP.DENGHI_FK = "+ this.dnmhId +" AND TNCC.TRANGTHAI = 1 AND TNCC_CT.NCC_FK = "+ nccId +"  AND   \n " + 
								  " ( CASE WHEN TNCC_CT.SP_FK IS NOT NULL THEN SP.PK_SEQ   \n " + 
								  " WHEN TNCC_CT.TAISAN_FK IS NOT NULL THEN TNCC_CT.TAISAN_FK    \n " + 
								  " WHEN TNCC_CT.CCDC_FK IS NOT NULL THEN TNCC_CT.CCDC_FK    \n " + 
								  " ELSE CP.PK_SEQ END) is not null ";
								
								System.out.println("Load SP "+ query);
								ResultSet rsSP = db.get(query);
								List<ISanpham> spList = new ArrayList<ISanpham>();
								
								if(rsSP!= null)
								{
									ISanpham sp = null;
									
									while(rsSP.next())
									{
										String spId = rsSP.getString("IDSP")== null?"0":rsSP.getString("IDSP");
										String spMa = rsSP.getString("MASP")== null?"":rsSP.getString("MASP");
										String spTen = rsSP.getString("TENSP");
										String spDonvi = rsSP.getString("DONVI");
										
										String soluong = formatterVND_4sole.format(rsSP.getDouble("SOLUONGDUYET"));  
										
										
										/*String dongia =  formatterVND_4sole.format(rsSP.getDouble("DONGIA")); 
										*/
										
										String dongia="";
										if(this.TienTe_FK.equals("")){
										 dongia =  formatterVND_4sole.format(rsSP.getDouble("DONGIA")); 
										}
										else{
										 dongia =  formattergia_usd.format(rsSP.getDouble("DONGIA"));
										}
										
										System.out.println(" don gia lay: "+dongia);
										String thanhtien =   formatterVND.format(rsSP.getDouble("THANHTIEN")); 
															
										sp = new Sanpham(spId, spMa, spTen, soluong, this.TienTe_FK, spDonvi, dongia, thanhtien, nccId);
										spList.add(sp);
									}
									rsSP.close();
								}
								
								ncc.setSanPham(spList);
								nccList.add(ncc);
								
								this.nhacungcapList = nccList;
							}
							rs.close();
						}
						
					}catch(Exception e)
					{
						e.printStackTrace();
					}
	
				}
			}
		}
		else // chuyển dự toán thành po
		{
			if(this.id.trim().length() > 0)
			{			
				try
				{
					String query = 
							" SELECT DISTINCT CASE WHEN DTSP.SANPHAM_FK IS NOT NULL THEN SP.PK_SEQ \n"+
							"        WHEN DTSP.TAISAN_FK IS NOT NULL THEN TSCD.PK_SEQ  \n"+
							"		 WHEN DTSP.CCDC_FK IS NOT NULL THEN CCDC.PK_SEQ  \n"+
							"		 ELSE CP.PK_SEQ END as IDSP,  \n"+
							"	   CASE WHEN DTSP.SANPHAM_FK IS NOT NULL THEN SP.MA \n"+
					        "      WHEN DTSP.TAISAN_FK IS NOT NULL THEN TSCD.ma \n"+
					        "      WHEN DTSP.CCDC_FK IS NOT NULL THEN CCDC.MA \n"+
					        "      ELSE CP.TEN END as MASP, \n"+
							"      CASE WHEN DTSP.SANPHAM_FK IS NOT NULL THEN SP.TEN   \n"+
					        "      WHEN DTSP.TAISAN_FK IS NOT NULL THEN DTSP.diengiai  \n"+
					        "      WHEN DTSP.CCDC_FK IS NOT NULL THEN DTSP.DIENGIAI \n"+
					        "      ELSE DTSP.DIENGIAI END as TENSP \n"+
					        " FROM ERP_DUTOANVATTU DT  \n"+
					        "      INNER JOIN ERP_DUTOANVATTU_SANPHAM DTSP ON DTSP.DTVT_FK = DT.PK_SEQ \n"+
					        "      LEFT JOIN ERP_SANPHAM SP ON DTSP.SANPHAM_FK = SP.PK_SEQ \n"+
					        "      LEFT JOIN ERP_MASCLON TSCD ON DTSP.TAISAN_FK = TSCD.PK_SEQ \n"+
					        "      LEFT JOIN ERP_NHOMCHIPHI CP ON DTSP.CHIPHI_FK = CP.PK_SEQ \n"+
					        "      LEFT JOIN ERP_CONGCUDUNGCU CCDC ON DTSP.CCDC_FK = CCDC.PK_SEQ \n"+							        
					        " WHERE DT.PK_SEQ = '"+ this.id +"' ";
					
					System.out.println("[SP PO]"+ query);
					ResultSet rsSP = db.get(query);
					this.spList = new ArrayList<ISanpham>();
					
					if(rsSP!= null)
					{
						ISanpham sp = null;
						
						while(rsSP.next())
						{
							String spId = rsSP.getString("IDSP") == null?"0":rsSP.getString("IDSP") ;
							String spMa = rsSP.getString("MASP") == null?"":rsSP.getString("MASP") ; ;
							String spTen = rsSP.getString("TENSP");
												
							sp = new Sanpham();
							sp.setId(spId);
							sp.setMasanpham(spMa);
							sp.setTensanpham(spTen);
							
							query = "\n select distinct b.PK_SEQ AS ID,  b.MA + ' - ' + b.TEN AS TENNCC "+
									"\n from ERP_DUTOANVATTU_SANPHAM a inner join ERP_NHACUNGCAP b on a.NCC_FK = b.PK_SEQ "+
									"\n where DTVT_FK = "+ this.id +" "+
									"\n and (a.SANPHAM_FK = "+spId+" or a.TAISAN_FK = "+spId+" or a.CCDC_FK = "+spId+" or a.CHIPHI_FK = "+spId+")";
							System.out.println("[NCC PO]"+query);
							ResultSet nccRs = db.get(query);
							sp.setNccRs(nccRs);
							
							spList.add(sp);
						}
						rsSP.close();
					}
					
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}

	}

	public void init(boolean po)
	{
//		NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
		
		String query = " SELECT DT.NGAY, DT.DENGHI_FK, DT.TIENTE_FK, DT.TYGIAQUYDOI, DT.TIMNCC_FK, DT.LOAIHANGHOA_FK, DT.NGUONGOCHH, DT.DONVITHUCHIEN_FK, \n"+
				       "        DT.LOAISANPHAM_FK, DT.GHICHU, DT.TRANGTHAI, DT.TIMNCC_FK  \n"+
					   " FROM ERP_DUTOANVATTU DT \n"+
					   " WHERE DT.PK_SEQ = "+ this.id +" ";
		
		System.out.println("Du toan vat tu : " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.ngaydutoan = rs.getString("NGAY"); 
					this.TienTe_FK = rs.getString("TIENTE_FK");
					this.dvthId = rs.getString("DONVITHUCHIEN_FK");
					this.dnmhId = rs.getString("DENGHI_FK");

					this.lhhId = rs.getString("LOAIHANGHOA_FK");
					this.lspId = rs.getString("LOAISANPHAM_FK")== null ? "" : rs.getString("LOAISANPHAM_FK");

					this.trangthai = rs.getString("TRANGTHAI");
					this.timNCCId =  rs.getString("TIMNCC_FK")== null ? "" : rs.getString("TIMNCC_FK");
					this.NguonGocHH = rs.getString("NGUONGOCHH");
					this.TyGiaQuyDoi = rs.getFloat("TYGIAQUYDOI");
					this.GhiChu = rs.getString("GHICHU");
					
					System.out.println("ghi chu :" +this.GhiChu +"\n");
					
				}
				rs.close();
			}
			catch (Exception e)
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		
		this.createRs(po);
	}

	
	public boolean createDmh()
	{
		// Kiem tra moi them vao
		String query = "";
		
		if(this.dnmhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đề nghị mua hàng";
			return false;
		}
		
		 			 
		try
		{
			String ngaytao = getDateTime();
			db.getConnection().setAutoCommit(false);
			
			//CAP NHAT SO PO, MA TU DONG TANG
//			String nam = this.ngaydutoan.substring(0, 4);
//			String thang = this.ngaydutoan.substring(5, 7);

			
			query = " INSERT INTO ERP_DUTOANVATTU(NGAY, DENGHI_FK, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK, GHICHU,  " +
			        " TIMNCC_FK, TIENTE_FK , TYGIAQUYDOI, DONVITHUCHIEN_FK, NGUONGOCHH , LOAIHANGHOA_FK )"+
					" Values('" + this.ngaydutoan + "'," + this.dnmhId + ", '0', '" + ngaytao + "', " + this.userId + ",'" + ngaytao + "', " + this.userId + "," + this.congtyId + ", N'" + this.GhiChu + "', "+
					" "+ this.timNCCId +", "+ this.TienTe_FK +", '" + this.TyGiaQuyDoi + "', "+ this.dvthId +", N'"+ this.NguonGocHH +"', '"+ this.lhhId +"')";
			
			System.out.println("Insert into ERP_DUTOANVATTU " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi Du toan vat tu: " + query;
				db.getConnection().rollback();
				return false;
			}

			String dtvtCurrent = "";
			query = "select SCOPE_IDENTITY() as dtvtId";
			ResultSet rsDmh = db.get(query);
			if (rsDmh.next())
			{
				dtvtCurrent = rsDmh.getString("dtvtId");
				this.id = dtvtCurrent;
				rsDmh.close();
			}
			
			
			for (int i = 0; i < this.nhacungcapList.size(); i++)
			{
				INhacungcap ncc = this.nhacungcapList.get(i);
						
				query = " INSERT INTO ERP_DUTOANVATTU_NCC(DTVT_FK, NCC_FK, TONGTIENBVAT, VAT, TONGTIENAVAT, TONGTIENBVATNT, TONGTIENAVATNT)  " +
						" values(" + dtvtCurrent + ", " + ncc.getId() + ", " + ncc.getTongtienBvat() + ", " + ncc.getVat() + ", " + ncc.getTongtienAvat() + ",  " + ncc.getTongtienBvatNT() + ",  " + ncc.getTongtienAvatNT() + ")";
				
				System.out.println("2.Insert Into ERP_DUTOANVATTU_NCC :" + query);
				
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi Du toan - Nha cung cap: " + query;
					db.getConnection().rollback();
					return false;
				}
                
				for(int j = 0;j < ncc.getSanPham().size() ;j++)
				{
					ISanpham sp = ncc.getSanPham().get(j);
					
					String SanPham_FK = "NULL";
					String ChiPhi_FK = "NULL";
					String TaiSan_FK = "NULL";
					String CCDC_FK = "NULL";
					
					if (this.lhhId.equals("0"))
					{
						SanPham_FK = sp.getId();
						if(SanPham_FK == null || SanPham_FK.trim().length() == 0 ) {
							SanPham_FK = "NULL";
						}
					}
					else
					{
						if(this.lhhId.equals("1"))  //Tai san co dinh
						{
						
							TaiSan_FK = sp.getId();
							if(TaiSan_FK == null || TaiSan_FK.trim().length() == 0 || TaiSan_FK.equals("null")) {
								TaiSan_FK = "0";								
							}
						}
						else
						{
							if(this.lhhId.equals("3"))  //CONG CU DUNG CU
							{								
								CCDC_FK = sp.getId();
								if(CCDC_FK == null || CCDC_FK.trim().length() == 0 || CCDC_FK.equals("null")) {
									CCDC_FK = "0";
								}
							}
							else  //Chi phi dich vu
							{
								
								ChiPhi_FK = sp.getId();
								if(ChiPhi_FK == null || ChiPhi_FK.trim().length() == 0 || ChiPhi_FK.equals("null")) {
									ChiPhi_FK = "0";
								}
							}
						}
						
					}
										
					query = " INSERT INTO ERP_DUTOANVATTU_SANPHAM (DTVT_FK, NCC_FK, SANPHAM_FK, TAISAN_FK, CCDC_FK, CHIPHI_FK, DONVI, SOLUONG, DONGIA, VAT, THANHTIEN, DIENGIAI) " +
							" values(" + dtvtCurrent + ", " + ncc.getId() + ", " + SanPham_FK + ", " + TaiSan_FK + ", " + CCDC_FK + ", " + ChiPhi_FK + ", N'"+ sp.getDonvitinh() +"', " + sp.getSoluong() + "," + sp.getDongia() + ", "+ ncc.getVat() +", " + sp.getThanhtien() + ", N'" + sp.getTensanpham() + "')";
					
					System.out.println("2.Insert Into ERP_DUTOANVATTU_SANPHAM :" + query);
					
					if (!db.update(query))
					{
						this.msg = "Khong the tao moi Du toan - San pham: " + query;
						db.getConnection().rollback();
						return false;
					}
					 
				}
				
			} 
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.db.update("rollback");
			this.msg = "1.Exception " + e.getMessage();
			return false;
		}
		
	}

	public boolean updateDmh()
	{
		
		// Kiem tra moi them vao
		String query = "";
		
		if(this.dnmhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đề nghị mua hàng";
			return false;
		}

 
		try
		{
			String ngaysua = getDateTime();
			db.getConnection().setAutoCommit(false);
							
			query = " UPDATE ERP_DUTOANVATTU set NGAY = '" + this.ngaydutoan + "', DENGHI_FK = '" + this.dnmhId + "', " +
					" NGAYSUA = '" + ngaysua + "', NGUOISUA = '" + this.userId + "', GhiChu=N'" + this.GhiChu + "', CONGTY_FK = '" + this.congtyId + "', "+
					" TIMNCC_FK = "+ this.timNCCId +", TIENTE_FK = "+ this.TienTe_FK +", TYGIAQUYDOI = "+ this.TyGiaQuyDoi +", DONVITHUCHIEN_FK = "+ this.dvthId +", NGUONGOCHH = N'"+ this.NguonGocHH +"' , LOAIHANGHOA_FK = '"+ this.lhhId +"'";
					
				query=query+	"  where pk_seq = '" + this.id + "'";

				System.out.println(" qr update : "+query);
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat Du toan: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DUTOANVATTU_NCC where DTVT_FK = '" + this.id + "'";
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_DUTOANVATTU_NCC: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DUTOANVATTU_SANPHAM where DTVT_FK = '" + this.id + "'";
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_DUTOANVATTU_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}
	
			for (int i = 0; i < this.nhacungcapList.size(); i++)
			{
				INhacungcap ncc = this.nhacungcapList.get(i);
						
				query = " INSERT INTO ERP_DUTOANVATTU_NCC(DTVT_FK, NCC_FK, TONGTIENBVAT, VAT, TONGTIENAVAT, TONGTIENBVATNT, TONGTIENAVATNT)  " +
						" values(" + this.id + ", " + ncc.getId() + ", " + ncc.getTongtienBvat() + ", " + ncc.getVat() + ", " + ncc.getTongtienAvat() + ",  " + ncc.getTongtienBvatNT() + ",  " + ncc.getTongtienAvatNT() + ")";
				
				System.out.println("2.Insert Into ERP_DUTOANVATTU_NCC :" + query);
				
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi Du toan - Nha cung cap: " + query;
					db.getConnection().rollback();
					return false;
				}
                
				for(int j = 0;j < ncc.getSanPham().size() ;j++)
				{
					ISanpham sp = ncc.getSanPham().get(j);
					
					String SanPham_FK = "NULL";
					String ChiPhi_FK = "NULL";
					String TaiSan_FK = "NULL";
					String CCDC_FK = "NULL";
					
					if (this.lhhId.equals("0"))
					{
						SanPham_FK = sp.getId();
						
						if(SanPham_FK == null || SanPham_FK.trim().length() == 0 || SanPham_FK.equals("null")) {
							SanPham_FK = "NULL";
						}
					}
					else
					{
						if(this.lhhId.equals("1"))  //Tai san co dinh
						{
						
							TaiSan_FK = sp.getId();
							if(TaiSan_FK == null || TaiSan_FK.trim().length() == 0 || TaiSan_FK.equals("null")) {
								TaiSan_FK = "0";
							}
						}
						else
						{
							if(this.lhhId.equals("3"))  //CONG CU DUNG CU
							{
								CCDC_FK = sp.getId();
								if(CCDC_FK == null || CCDC_FK.trim().length() == 0 || CCDC_FK.equals("null")) {
									CCDC_FK = "0";
								}
							}
							else  //Chi phi dich vu
							{
								
								ChiPhi_FK = sp.getId();
								if(ChiPhi_FK == null || ChiPhi_FK.trim().length() == 0 || ChiPhi_FK.equals("null")) {
									ChiPhi_FK = "0";
								}
							}
						}
						
					}
					
					query = " INSERT INTO ERP_DUTOANVATTU_SANPHAM(DTVT_FK, NCC_FK, SANPHAM_FK, TAISAN_FK, CCDC_FK, CHIPHI_FK, DONVI, SOLUONG,  VAT, DONGIA, THANHTIEN, DIENGIAI) " +
							" values(" + this.id + ", " + ncc.getId() + ", " + SanPham_FK + ", " + TaiSan_FK + ", " + CCDC_FK + ", " + ChiPhi_FK + ", N'"+ sp.getDonvitinh() +"', " + sp.getSoluong() + ","+ ncc.getVat() +", " + sp.getDongia() + "," + sp.getThanhtien() + ", N'" + sp.getTensanpham() + "')";
					
					System.out.println("2.Insert Into ERP_DUTOANVATTU_SANPHAM :" + query);
					
					if (!db.update(query))
					{
						this.msg = "Khong the tao moi Du toan - San pham: " + query;
						db.getConnection().rollback();
						return false;
					}
					 
				}
				
			} 
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Khong the cap nhat don hang " + query;
			return false;
		}
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public List<IDonvi> getDvList()
	{
		return this.dvList;
	}

	public void setDvList(List<IDonvi> dvList)
	{
		this.dvList = dvList;
	}


	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void close()
	{
		try
		{
			
			if (this.dvthRs != null){
				this.dvthRs.close();
			}
			if (this.lhhRs != null){
				this.lhhRs.close();
			}
			if(spList!=null){
				spList.clear();
			}
			if(dvList!=null){
				dvList.clear();
			}
			
			/*if(tienteList!=null){
				tienteList.close();
			}*/
			this.db.shutDown();
		}
		catch (SQLException e)
		{
			
		}
		
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}


	public String getSochungtu() 
	{
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu)
	{
		this.sochungtu = sochungtu;
	}

	public void setNguonGocHH(String nguongoc)
	{
		this.NguonGocHH = nguongoc;
	}

	public String getNguonGocHH()
	{
		return this.NguonGocHH;
	}

	public void setMaLoaiHH(String maloaihh)
	{
		this.MaLoaiHH = maloaihh;

	}

	public String getMaLoaiHH()
	{

		return this.MaLoaiHH;
	}

	public void setTienTe_FK(String tiente_fk)
	{
		this.TienTe_FK = tiente_fk;

	}

	public String getTienTe_FK()
	{

		return this.TienTe_FK;
	}

	public String getGhiChu()
	{

		return this.GhiChu;
	}

	public void setGhiChu(String ghichu)
	{

		this.GhiChu = ghichu;
	}

	

	public void setTyGiaQuyDoi(float tygiaquydoi)
	{
		this.TyGiaQuyDoi = tygiaquydoi;
	}

	public Float GetTyGiaQuyDoi()
	{

		return this.TyGiaQuyDoi;
	}


	public String getLoaihanghoa() 
	{
		return this.lhhId;
	}

	public void setLoaihanghoa(String loaihh) 
	{
		this.lhhId = loaihh;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public String getNppId() {
		return this.nppId;
	}

	public void setNppId(String nppid) {
		this.nppId = nppid;
	}
	
	public String getmaDMH() {
		
		return this.maDMH;
	}


	public void setmaDMH(String maDMH) {
		this.maDMH= maDMH;
		
	}

	public String getNgaydutoan() 
	{
		return this.ngaydutoan;
	}

	public void setNgaydutoan(String ngaydutoan) 
	{
		this.ngaydutoan = ngaydutoan;
	}

	public String getDnmhId() 
	{
		return this.dnmhId;
	}

	public void setDnmhId(String dnmhId) 
	{
		this.dnmhId = dnmhId;
	}

	public ResultSet getDmhList() 
	{
		return this.dnmhList ;
	}

	public void setDmhList(ResultSet dnmhList) 
	{
		this.dnmhList = dnmhList;
	}

	public String getTimNCCId() 
	{
		return this.timNCCId;
	}

	public void setTimNCCId(String timNCCId) 
	{
		this.timNCCId = timNCCId;
	}

	public ResultSet getTimNCCList()
	{
		return this.timNCCList;
	}

	public void setTimNCCList(ResultSet timNCCList) 
	{
		this.timNCCList = timNCCList;
	}

	public List<INhacungcap> getNhacungcapList() 
	{
		return this.nhacungcapList;
	}

	public void setNhacungcapList(List<INhacungcap> nccList) 
	{
		this.nhacungcapList = nccList;
	}
	
	public List<ISanpham> getSanphamList() 
	{
		return this.spList;
	}

	public void setSanphamList(List<ISanpham> spList) 
	{
		this.spList = spList;
	}

	/*public ResultSet getTienteList() 
	{
		return this.tienteList;
	}

	public void setTienteList(ResultSet ttList) 
	{
		this.tienteList = ttList;
	}*/
	
	
	
	

	@Override
	public boolean createPO() {

//		String msg = "";
		String query = "";
		
		dbutils db = new dbutils();

		try 
		{
			db.getConnection().setAutoCommit(false);
								
//			String ngaymuahang = "";
			String nccId = "";
		    String loaihh = "";
		    String dvthId = "";
			double tongtienBvat = 0;
			double Vat = 0;
			double tongtienAvat = 0;
			
			query = "delete ERP_DUTOANVATTU_SP_NCC where dtvt_fk = "+this.id;
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_DUTOANVATTU_SP_NCC: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for (int i = 0; i < this.spList.size(); i++) {
				ISanpham sp = this.spList.get(i);
				query = "insert into ERP_DUTOANVATTU_SP_NCC(dtvt_fk, doituong_fk, ncc_fk, diengiai) "+
						"select "+this.id+", "+sp.getId()+", "+sp.getNccId()+", N'"+sp.getTensanpham()+"'";
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_DUTOANVATTU_SP_NCC: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			query = "SELECT DISTINCT DT_NCC.NCC_FK, DT.DONVITHUCHIEN_FK, DT.LOAIHANGHOA_FK, LOAISANPHAM_FK, NGUONGOCHH , DT.TIENTE_FK\n" +
					", case when DT.TIENTE_FK = 100000 then 1 else isNull(tigia.TIGIAQUYDOI, 0) end TIGIAQUYDOI \n"+
					"FROM ERP_DUTOANVATTU DT inner join ERP_DUTOANVATTU_SP_NCC DT_NCC ON DT.PK_SEQ = DT_NCC.DTVT_FK \n"+
					"left join erp_tigia tigia on tigia.congty_fk = dt.congty_fk and tigia.tiente_fk = dt.tiente_fk and tigia.trangthai = 1  and tigia.tungay <= dt.ngay and tigia.denngay >= dt.ngay \n"+
					"WHERE DT.PK_SEQ = "+ this.id +"";
			System.out.println("Câu lấy TT "+query);
			ResultSet rsMH = db.get(query);

			if(rsMH != null)
			{
				while(rsMH.next())
				{
//					ngaymuahang = getDateTime();
					nccId = rsMH.getString("NCC_FK");
					loaihh = rsMH.getString("LOAIHANGHOA_FK");
					dvthId = rsMH.getString("DONVITHUCHIEN_FK");
					double tigia = rsMH.getDouble("TIGIAQUYDOI");
					if (tigia == 0)
					{
						this.msg = "Chưa có tỉ giá quy đổi, vui lòng khai báo trong DLN tỉ giá";
						db.getConnection().rollback();
						return false;
					}
					
					// SOPO
					query=	" SELECT ISNULL( MAX(SOTUTANG_THEONAM), 0) AS MAXSTT, (SELECT PREFIX FROM ERP_DONVITHUCHIEN WHERE PK_SEQ ="+dvthId+" ) AS PREFIX "+
							" FROM ERP_MUAHANG DMH WHERE SUBSTRING(NGAYMUA, 0, 5) = "+getDateTime().substring(0, 4)+ 
							" and DMH.DONVITHUCHIEN_FK="+dvthId;
					System.out.println("[SOPO] "+query);
					String soPO = "";
					int sotutang_theonam = 0;
					ResultSet rsPO = db.get(query);  
					if(rsPO.next())
					{
						sotutang_theonam =  (rsPO.getInt("maxSTT") + 1 );
						String prefix = rsPO.getString("PREFIX");
						String namPO = getDateTime().substring(2, 4);
						String chuoiso= ("0000"+ Integer.toString(sotutang_theonam)).substring(("0000"+ Integer.toString(sotutang_theonam)).length()-4,("0000"+ Integer.toString(sotutang_theonam)).length());
						
						soPO = prefix + "-" +   chuoiso+ "/" + getDateTime().substring(5, 7) + "/" + namPO;
				 
					}
					rsPO.close();
					
					// Chèn PO
					query = "Insert into Erp_MuaHang(LOAI, DTVT_FK, SOPO, NgayMua, DonViThucHien_FK, NhaCungCap_FK, LoaiHangHoa_FK, LoaiSanPham_FK,  TrangThai, NgayTao, NgaySua, NguoiTao, NguoiSua, NguonGocHH, TienTe_FK, TyGiaQuyDoi,  congty_fk)" +
							" Values('2', "+ this.id +", '"+ soPO +"', '"+ getDateTime() +"', '" + rsMH.getString("DONVITHUCHIEN_FK") + "'," + nccId + "," + rsMH.getString("LOAIHANGHOA_FK") + ", " + rsMH.getString("LOAISANPHAM_FK") + ", '0', " +
						    " '" + getDateTime() + "', '" + getDateTime() + "', " + this.userId + ", "+ this.userId +", '" + rsMH.getString("NGUONGOCHH") + "', '" + rsMH.getString("TIENTE_FK") + "'," + rsMH.getString("TIGIAQUYDOI") + "," + this.congtyId + ")";
					
					System.out.println("Insert into Erp_MuaHang " + query);
					if (!db.update(query))
					{
						this.msg = "Khong the tao moi Mua hang: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					String dmhIdCurrent = "";
					query = "select SCOPE_IDENTITY() as id";
					ResultSet rsDmh = db.get(query);
					if (rsDmh.next())
					{
						dmhIdCurrent = rsDmh.getString("id");
						rsDmh.close();
					}
					
					// Chèn SP
					query = " SELECT DT.PK_SEQ, ISNULL(DT.TYGIAQUYDOI,1) AS TYGIA, SP.SANPHAM_FK, isnull(SP.TAISAN_FK,0) TAISAN_FK, isnull(SP.CCDC_FK,0) CCDC_FK, \n"+
							" SP.CHIPHI_FK, SP.SOLUONG, SP.DONGIA, SP.VAT, SP.DONVI, SP.THANHTIEN, isnull(SP.DIENGIAI,'') DIENGIAI, isnull(SP.DONVI, '') DONVI \n"+
							" FROM ERP_DUTOANVATTU DT INNER JOIN ERP_DUTOANVATTU_SANPHAM SP ON DT.PK_SEQ = SP.DTVT_FK \n"+
							" INNER JOIN ERP_DUTOANVATTU_SP_NCC DT_NCC ON DT.PK_SEQ = DT_NCC.DTVT_FK AND DT_NCC.NCC_FK = SP.NCC_FK \n"+ 
							" and DT_NCC.DOITUONG_FK = isnull(SP.SANPHAM_FK, ISNULL(taisan_fk, isnull(ccdc_fk, chiphi_fk))) and DT_NCC.DIENGIAI = SP.DIENGIAI \n"+
							" WHERE DT.PK_SEQ = "+ this.id +" AND DT_NCC.NCC_FK = "+nccId;
					System.out.println("LAY SP "+query);
					ResultSet rsMH_CT = db.get(query);
					
					if(rsMH_CT != null)
					{
						while(rsMH_CT.next())
						{
							double dongiaviet = rsMH_CT.getDouble("DONGIA")*tigia;
							double thanhtienviet =  Math.round(rsMH_CT.getDouble("SOLUONG") * dongiaviet);
							
							String taisan_fk = "";
							taisan_fk = rsMH_CT.getString("TAISAN_FK");
							
							String CCDC_FK = "";
							CCDC_FK = rsMH_CT.getString("CCDC_FK");
							
							if(loaihh.equals("1"))// TÀI SẢN CỐ ĐỊNH
							{					
								if(taisan_fk.equals("0")) // TÀI SẢN NÀY CHƯA CÓ MÃ TRONG DỮ LIỆU NỀN - TỰ ĐỘNG TẠO MÃ TÀI SẢN
								{
									query =	
									"INSERT INTO ERP_TAISANCODINH(ma, ten, diengiai, congty_fk, soluong ,dongia, thanhtien, sothangKH, thangbatdauKH, trangthai, " +
									"nguoitao, nguoisua, ngaytao, ngaysua, donvi) \n" +
									"SELECT (SELECT 'TS'+ cast((SELECT MAX(pk_seq)+1 FROM ERP_TAISANCODINH ) as nvarchar(50))) , N'" +rsMH_CT.getString("DIENGIAI") +"' , N'" +rsMH_CT.getString("DIENGIAI") +"', '" +this.congtyId+"'," + 
									""+rsMH_CT.getDouble("SOLUONG")+", "+dongiaviet+", "+thanhtienviet+", " +
									"'0', '0', '0' ," + this.userId + ", " + this.userId + ", '" + this.getDateTime() + "', " +
									"'" + this.getDateTime() + "', N'"+rsMH_CT.getString("DONVI")+"' \n "+
									"FROM ERP_DUTOANVATTU WHERE PK_SEQ = " + this.id + " ";
									
									System.out.println(query);
									if (!db.update(query))
									{
										this.msg = "Khong the tao moi Mua hang - San pham: " + query;
										db.getConnection().rollback();
										return false;
									}
																		
									query = "select SCOPE_IDENTITY() as tsId";
									
									System.out.println(query);
									ResultSet rsTs = db.get(query);
									if (rsTs.next())
									{
										taisan_fk = rsTs.getString("tsId");
										rsTs.close();
									}
									
									System.out.println("taisan_fk:"+taisan_fk);
									
									query = "update ERP_DUTOANVATTU_SP_NCC set doituong_fk = "+taisan_fk+" where dtvt_fk = "+this.id+" and diengiai = N'" +rsMH_CT.getString("DIENGIAI") +"' and ncc_Fk = "+nccId;
									System.out.println("[ERP_DUTOANVATTU_SP_NCC] "+query);
									if (!db.update(query))
									{
										this.msg = "Khong the tao moi Mua hang - San pham: " + query;
										db.getConnection().rollback();
										return false;
									}
									query = "update ERP_DUTOANVATTU_SANPHAM set taisan_fk = "+taisan_fk+" where dtvt_fk = "+this.id+" and diengiai = N'" +rsMH_CT.getString("DIENGIAI") +"'";
									System.out.println("[ERP_DUTOANVATTU_SANPHAM] "+query);
									if (!db.update(query))
									{
										this.msg = "Khong the tao moi Mua hang - San pham: " + query;
										db.getConnection().rollback();
										return false;
									}
								}
							}
							else if(loaihh.equals("3")) // CÔNG CỤ DỤNG CỤ
							{
								if(CCDC_FK.equals("0")) // CCDC NÀY CHƯA CÓ MÃ TRONG DỮ LIỆU NỀN - TỰ ĐỘNG TẠO MÃ TÀI SẢN
								{
									query =	
									"INSERT INTO ERP_CONGCUDUNGCU(ma, diengiai, congty_fk, soluong, dongia, thanhtien, sothangKH, thangbatdauKH, trangthai, " +
									"nguoitao, nguoisua, ngaytao, ngaysua, donvi) \n" +
									"SELECT (SELECT 'CCDC'+ cast((SELECT MAX(pk_seq)+1 FROM ERP_CONGCUDUNGCU ) as nvarchar(50))), N'" +rsMH_CT.getString("DIENGIAI") +"', '" +this.congtyId+"'," + 
									""+rsMH_CT.getDouble("SOLUONG")+", "+dongiaviet+", "+thanhtienviet+", " +
									"'0', '0', '0' ," + this.userId + ", " + this.userId + ", '" + this.getDateTime() + "', " +
									"'" + this.getDateTime() + "', N'"+rsMH_CT.getString("DONVI")+"' \n "+
									"FROM ERP_DUTOANVATTU WHERE PK_SEQ = " + this.id + " ";
									
									if (!db.update(query))
									{
										this.msg = "Khong the tao moi Mua hang - San pham: " + query;
										db.getConnection().rollback();
										return false;
									}
																
									query = "select SCOPE_IDENTITY() as tsId";
									ResultSet rsTs = db.get(query);
									if (rsTs.next())
									{
										CCDC_FK = rsTs.getString("tsId");
										rsTs.close();
									}
							
									query = "update ERP_DUTOANVATTU_SP_NCC set doituong_fk = "+CCDC_FK+" where dtvt_fk = "+this.id+" and diengiai = N'" +rsMH_CT.getString("DIENGIAI") +"' and ncc_Fk = "+nccId;
									System.out.println("[ERP_DUTOANVATTU_SP_NCC] "+query);
									if (!db.update(query))
									{
										this.msg = "Khong the tao moi Mua hang - San pham: " + query;
										db.getConnection().rollback();
										return false;
									}
									query = "update ERP_DUTOANVATTU_SANPHAM set CCDC_FK = "+CCDC_FK+" where dtvt_fk = "+this.id+" and diengiai = N'" +rsMH_CT.getString("DIENGIAI") +"'";
									System.out.println("[ERP_DUTOANVATTU_SANPHAM] "+query);
									if (!db.update(query))
									{
										this.msg = "Khong the tao moi Mua hang - San pham: " + query;
										db.getConnection().rollback();
										return false;
									}
								}
							}
					
							if(taisan_fk.equals("0")) taisan_fk = "null";
							if(CCDC_FK.equals("0")) CCDC_FK = "null";
							
							query = " insert into ERP_MUAHANG_SP ( muahang_fk, sanpham_fk, taisan_fk, chiphi_fk, ccdc_fk, donvi, soluong, dongia, vat, thanhtien, dongiaviet, thanhtienviet, soluong_new, dongia_new ) " +
									" values(" + dmhIdCurrent + ", " + rsMH_CT.getString("SANPHAM_FK") + ", " + taisan_fk + ", " + rsMH_CT.getString("CHIPHI_FK") + ", " + CCDC_FK + ", N'" + rsMH_CT.getString("DONVI") + "', "+
											 " " + rsMH_CT.getString("SOLUONG") + "," + rsMH_CT.getString("DONGIA") + "," + rsMH_CT.getDouble("VAT") + ", " + rsMH_CT.getString("THANHTIEN") + ", "+ dongiaviet +", "+ thanhtienviet +", " + rsMH_CT.getString("SOLUONG") + ", " + rsMH_CT.getString("DONGIA")+" )";
							
							System.out.println("2.Insert Into Erp_MuaHang_SP :" + query);
							
							if (!db.update(query))
							{
								this.msg = "Khong the tao moi Mua hang - San pham: " + query;
								db.getConnection().rollback();
								return false;
							}
							
							Vat = rsMH_CT.getDouble("VAT");
							tongtienBvat += rsMH_CT.getDouble("THANHTIEN");
					
						}rsMH_CT.close();
					}
			
					// Cập nhật lại tiền cho PO
					tongtienAvat = tongtienBvat + Math.round(tongtienBvat*Vat/100) ;
					
					query = "UPDATE ERP_MUAHANG SET SOPO = '"+ dmhIdCurrent +"', TONGTIENBVAT = "+ tongtienBvat +", VAT = "+ Vat +", TONGTIENAVAT = "+ tongtienAvat +" WHERE PK_SEQ = "+ dmhIdCurrent +" ";
					if(!db.update(query))
					{
						msg = "Khong the cap nhat Du toan " + query;
						db.getConnection().rollback();
					}		
							
					// Kiểm tra có vượt ngân sách không (Chi phí/ TSCĐ)
					boolean vuotNganSach = false;
				    String nam = getDateTime().substring(0, 4);
					
					if(loaihh.equals("2")) //chi phi, dich vu
					{
				
						query=" INSERT INTO ERP_MUAHANG_SP_HOADON   (	MUAHANG_FK ,MUAHANG_SP_FK  ,MAHOADON  ,	MAUSOHOADON  ,	KYHIEU  ,SOHOADON  ,NGAYHOADON , "+
							  " TENNHACUNGCAP  ,MASOTHUE  ,TIENHANG  ,	THUESUAT  ,	TIENTHUE  ,	TONGCONG   ,GHICHU   )    "+
							  " select mhsp.MUAHANG_FK,mhsp.PK_SEQ,'','','','','','','',mhsp.THANHTIEN,mh.VAT,mhsp.THANHTIEN*mh.VAT/100,mhsp.THANHTIEN+mhsp.THANHTIEN*mh.VAT/100,'' "+
							  " from ERP_MUAHANG_SP mhsp " +
							  " inner join ERP_MUAHANG mh on mh.PK_SEQ=mhsp.MUAHANG_FK "+
							  " where MUAHANG_FK not in (select MUAHANG_FK from ERP_MUAHANG_SP_HOADON) "+
							  " and mh.LOAIHANGHOA_FK='2' and mh.pk_seq= "+dmhIdCurrent;
						if (!db.update(query))
						{
							this.msg = "Khong the tao moi Mua hang - San pham: " + query;
							db.getConnection().rollback();
							return false;
						}
				
				
						query = " select isnull(ngansachConLai.conLai, 0) - muahang.tongGiaTri as cotheSuDung  " +
								"from " +
								"( " +
									"select CHIPHI_FK, SUM(SOLUONG * DONGIA) as tongGiaTri  " +
									"from ERP_MUAHANG_SP where MUAHANG_FK = '" + dmhIdCurrent + "' group by CHIPHI_FK  " +
								")  " +
								"muahang left join " +
								"( " +
									"select nganSach.CHIPHI_FK, isnull(nganSach.DUTOAN, 0) - ISNULL( dukienChi.tongduKien, 0) as conLai  " +
									"from " +
									"( " +
										"select CHIPHI_FK, DUTOAN, THUCCHI   " +
										"from ERP_LAPNGANSACH_CHIPHI   " +
										"where LAPNGANSACH_FK in ( select pk_seq from ERP_LAPNGANSACH where NAM = '" + nam + "' and TRANGTHAI = '1' )   " +
											"and DONVITHUCHIEN_FK = '" + dvthId + "'   " +
									")  " +
									"nganSach left join " +
									"(  " +
										"select CHIPHI_FK, SUM(b.SOLUONG * b.DONGIAVIET) as tongduKien  " +
										"from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK  " +
										"where a.LOAIHANGHOA_FK = '2' and a.DONVITHUCHIEN_FK = '" + dvthId + "'   " +
											"and SUBSTRING(ngaymua, 0, 5) = '" + nam + "' and a.pk_seq != '" + dmhIdCurrent + "'  " +
										"group by CHIPHI_FK  " +
									") " +
									"dukienChi on nganSach.CHIPHI_FK = dukienChi.CHIPHI_FK   " +
								")  " +
								"ngansachConLai on muahang.CHIPHI_FK = ngansachConLai.CHIPHI_FK";
				
						System.out.println("___Check ngan sach chi phi: " + query);
						
						ResultSet rsCheckNS = db.get(query);
						while(rsCheckNS.next())
						{
							if(rsCheckNS.getDouble("cotheSuDung") < 0)
							{
								vuotNganSach = true;
							}
						}
						rsCheckNS.close();
					}
					else    //Tai san co dinh
					{
						if(loaihh.equals("1"))
						{
							query = "select b.pk_seq as taisan_fk, a.SoLuong, a.DonGia, a.THANHTIEN as tongNganSach, a.SOTHANGKH, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12   " +
									"from ERP_LAPNGANSACH_TAISAN a inner join ERP_TAISANCODINH b on a.PK_SEQ = b.LAPNGANSACH_TAISAN_FK  " +
									"where DONVITHUCHIEN_FK = '" + dvthId + "' and b.pk_seq in ( select TAISAN_FK from ERP_MUAHANG_SP where MUAHANG_FK = '" + dmhIdCurrent + "' )";
							
							System.out.println("1.Check ngan sach tai san: " + query);
							
							ResultSet rsNgansach = db.get(query);
							while(rsNgansach.next())
							{
								String taisanId = rsNgansach.getString("taisan_fk");
								double ngansachTong = rsNgansach.getDouble("tongNganSach");
								double tongKhauhao = 0;
								
								int sothangKH = rsNgansach.getInt("SOTHANGKH");
								double dongia = rsNgansach.getDouble("DonGia");
								
								int thangthu = 0;
								for(int i = 1; i <= 12; i++)
								{
									int T1 = rsNgansach.getInt("T" + Integer.toString(i));
									if(T1 > 0)
									{
										double khaukhao_thang = ( ( T1 * dongia ) / sothangKH ) * (12 - thangthu);
										thangthu ++;
										
										tongKhauhao += khaukhao_thang;
									}
								}
						
								//Lay tat cac cac mua hang cua tai san nay, tinh tong Ngansach da su dung va tong khau hao (du kien)
								query = "select a.NGAYMUA, b.SOLUONG, b.DONGIAVIET   " +
										"from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK " +
										"where b.TAISAN_FK = '" + taisanId + "' and a.DONVITHUCHIEN_FK = '" + dvthId + "'";
								
								System.out.println("2.Check ngan sach tai san da su dung: " + query);
								ResultSet rsTaisan = db.get(query);
								
								double tongNganSach_Mua = 0;
								double tongKhauHao_Mua = 0;
								
								if(rsTaisan != null)
								{
									while(rsTaisan.next())
									{
										String thangbdKhauHao_DuKien = rsTaisan.getString("NGAYMUA").split("-")[1];
										
										int soluongMua = rsTaisan.getInt("SOLUONG");
										double dongiaMua = rsTaisan.getDouble("DONGIAVIET");
										
										tongNganSach_Mua += soluongMua * dongiaMua;
										
										tongKhauHao_Mua +=  ( soluongMua * dongiaMua / sothangKH ) * ( 12 - Integer.parseInt(thangbdKhauHao_DuKien) );
									}
									rsTaisan.close();
								}
						
								if( ( tongNganSach_Mua > ngansachTong ) || ( tongKhauHao_Mua > tongKhauhao ) )
								{
									vuotNganSach = true;
									rsNgansach.close();
									break;
								}
						
							}
							rsNgansach.close();
					
							System.out.println("3.Check tai san vuot ngan sach: " + vuotNganSach);
						}
					}
			
					// insert nguoi duyet PO 
					// NẾU CÓ CHÍNH SÁCH DUYỆT DÀNH CHO NCC CỦA ĐƠN MUA HÀNG, THÌ LẤY CHÍNH SÁCH DUYỆT ĐÓ
					query = "SELECT	NCC.CHUCDANH_FK, NCC.QUYETDINH " +
							"FROM ERP_MUAHANG MUAHANG  " +
							"INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " + 
							"INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK " + 
							"WHERE MUAHANG.PK_SEQ = '" + dmhIdCurrent + "' " +
			
							"UNION ALL " +
							// NẾU KO CÓ CHÍNH SÁCH DUYỆT DÀNH CHO SẢN PHẨM CỦA ĐƠN MUA HÀNG VÀ KO CÓ CHÍNH SÁCH DUYỆT CHO NCC CỦA ĐƠN MUA HÀNG
							"SELECT	SP.CHUCDANH_FK, SP.QUYETDINH " + 
							"FROM ERP_MUAHANG MUAHANG  " +  
							"INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +  
							"INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   " + 
							"AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + dmhIdCurrent + "') " +  
							"LEFT JOIN " + 
							"(  " + 
							"	SELECT	COUNT(*) AS NUM " + 
							"	FROM ERP_MUAHANG MUAHANG   " + 
							"	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +  
							"	INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK " +  
							"	AND NCC.NCC_FK IN (SELECT NHACUNGCAP_FK FROM ERP_MUAHANG WHERE PK_SEQ = '" + dmhIdCurrent + "')  " + 
							"	WHERE MUAHANG.PK_SEQ = '" + dmhIdCurrent + "'   " + 
							")DUYET_NCC ON 1 = 1 " + 
							"LEFT JOIN " + 
							"( " + 
							"	SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM " + 
							"	FROM ERP_MUAHANG_SP MH_SP  " + 
							"	INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK " + 
							"	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 " + 
							"	WHERE MH.PK_SEQ = '" + dmhIdCurrent + "'  AND MH_SP.SANPHAM_FK NOT IN  " + 
							"	(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ) " +  
							")KTRA_SP ON 1 = 1 " + 
							"WHERE MUAHANG.PK_SEQ = '" + dmhIdCurrent + "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 " + 
			
							"UNION ALL " + 
							"SELECT	CP.CHUCDANH_FK, CP.QUYETDINH " +  
							"FROM ERP_MUAHANG MUAHANG   " + 
							"INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +   
							"INNER JOIN ERP_CHINHSACHDUYET_CHIPHI CP ON CP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   " + 
							"INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = CP.KHOANGCHIPHI_FK  " +  
							"LEFT JOIN( " + 
							"	SELECT	COUNT(SP.CHUCDANH_FK) AS NUM " + 
							"	FROM ERP_MUAHANG MUAHANG   " + 
							"	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +  
							"	INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   " + 
							"	AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + dmhIdCurrent + "') " +  
							"	LEFT JOIN " + 
							"	( " + 
							"		SELECT	COUNT(*) AS NUM " + 
							"		FROM ERP_MUAHANG MUAHANG   " + 
							"		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +  
							"		INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK " +  
							"		AND NCC.NCC_FK IN (SELECT NHACUNGCAP_FK FROM ERP_MUAHANG WHERE PK_SEQ = '" + dmhIdCurrent + "')  " + 
							"		WHERE MUAHANG.PK_SEQ = '" + dmhIdCurrent + "'   " + 
							"	)DUYET_NCC ON 1 = 1 " + 
							"	LEFT JOIN " + 
							"	( " + 
							"		SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM " + 
							"		FROM ERP_MUAHANG_SP MH_SP  " + 
							"		INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK " + 
							"		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 " + 
							"		WHERE MH.PK_SEQ = '" + dmhIdCurrent + "'  AND MH_SP.SANPHAM_FK NOT IN  " + 
							"		(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ) " +  
							"	)KTRA_SP ON 1 = 1 " + 
							"	WHERE MUAHANG.PK_SEQ = '" + dmhIdCurrent + "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 " + 
			
							")DUYET_SP ON 1 = 1 " + 
							"LEFT JOIN( " + 
							"	SELECT	COUNT(NCC.CHUCDANH_FK) AS NUM " +  
							"	FROM ERP_MUAHANG MUAHANG   " + 
							"	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +  
							"	INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK " +  
							"	WHERE MUAHANG.PK_SEQ = '" + dmhIdCurrent + "'" + 
							")DUYET_NCC ON 1 = 1 " + 
							"WHERE KHOANGCHIPHI.SOTIENTU < MUAHANG.TONGTIENBVAT " +   
							"AND (KHOANGCHIPHI.SOTIENDEN >= MUAHANG.TONGTIENBVAT OR KHOANGCHIPHI.SOTIENDEN IS NULL) " +    
							"AND MUAHANG.PK_SEQ = '" + dmhIdCurrent + "' AND DUYET_SP.NUM = 0 AND DUYET_NCC.NUM = 0 "; 
		
					System.out.println("Câu duyệt PO "+ query);		
					
					ResultSet rsDuyet = db.get(query);					
					if (rsDuyet.next() )
					{							
						query = "if not exists (select * from ERP_DUYETMUAHANG where CHUCDANH_FK = "+rsDuyet.getString("CHUCDANH_FK")+" AND MUAHANG_FK = "+dmhIdCurrent+")" +
								"INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH) " +
								"VALUES('"+ dmhIdCurrent +"', '" + rsDuyet.getString("CHUCDANH_FK") + "', '0','" + rsDuyet.getString("QUYETDINH")+ "') ";
						
						System.out.println("4. Insert Duyet PO:" + query);
						if (!db.update(query))
						{
							this.msg = "Khong the them nguoi duyet cho PO: " + query;
							db.getConnection().rollback();
							return false;
						}					
	
		            }
					
					query = "Update ERP_MUAHANG set VUOTNGANSACH = '" + (vuotNganSach == true ? "1" : "0") + "' where pk_seq = '" + dmhIdCurrent + "' ";
					if (!db.update(query))
					{
						this.msg = "Khong the cap nhat ERP_MUAHANG: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					//CẬP NHẬT TOOLTIP
					db.execProceduce2("CapNhatTooltip_DMH", new String[] { dmhIdCurrent } );
				}rsMH.close();
			}
			//Cập nhật trạng thái Dự toán : Hoàn tất
			query = "Update ERP_DUTOANVATTU set TRANGTHAI = '2' where pk_seq = '" + this.id + "' ";
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_DUTOANVATTU: " + query;
				db.getConnection().rollback();
				return false;
			}
	
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.msg = "Xảy ra lỗi trong quá trình chuyển thành PO ";
			return false;
		}
	}
}