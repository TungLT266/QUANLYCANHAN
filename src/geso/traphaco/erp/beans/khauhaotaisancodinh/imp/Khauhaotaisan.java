package geso.traphaco.erp.beans.khauhaotaisancodinh.imp;

import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.khauhaotaisancodinh.IGiaTriKhauHao;
import geso.traphaco.erp.beans.khauhaotaisancodinh.IKhauhaotaisan;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Khauhaotaisan implements IKhauhaotaisan {
	String userId;
	
	String Id;
	
	String ctyId;
	
	String ctyTen;
	
	ResultSet thangRs;
	
	String soChungTu;
	
	String thang;
	
	ResultSet namRs;
	
	String nam;
	
	String matsstr;
	
	ResultSet taisanRs;
	
	String ntsId;
	
	ResultSet nhomRs;
	
	ResultSet LoaiRs;
	
	String  NppId;
	
	String DVKDID;
	
	String msg;
	
	String[] ma;
	
	String[] diengiai;
	
	String[] nhom;
	
	String[] thangthu;
	
	String[] thangthuTT;
	

	
	double[] khauhao;
	
	double[] khauhaoluyke;
	
	double[] giatriconlai;
	
	dbutils db;
	
	String nppdangnhap;
	
	String dienGiaiCT;
	
	String flag;
	boolean isKhauhao;
	
	List<IGiaTriKhauHao> khauhaoList = new ArrayList<IGiaTriKhauHao>();
	
	public Khauhaotaisan()
	{
		this.Id = "";		
		this.thang = "" + Integer.parseInt(this.getDateTime().substring(5, 7));		
		this.nam = this.getDateTime().substring(0, 4);		
		this.ntsId = "";		
		this.matsstr = "";		
		this.msg = "";
		this.nppdangnhap = "";
		this.dienGiaiCT = "";
		this.flag = "";
		this.isKhauhao=false;
		this.db = new dbutils();		
		this.soChungTu="";
	}

	public String getKhId() 
	{
		return this.Id;
	}

	public void setKhId(String Id) 
	{
		this.Id = Id;
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getCongty() 
	{
		return this.ctyTen;
	}

	public void setCongty(String cty) 
	{
		this.ctyTen = cty;
	}

	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
	}


	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public ResultSet getThangRs() 
	{
		return this.thangRs;
	}

	public void setThangRs(ResultSet thangRs) 
	{
		this.thangRs = thangRs;
	}

	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;
	}

	public ResultSet getNamRs() 
	{
		return this.namRs;
	}

	public void setNamRs(ResultSet namRs) 
	{
		this.namRs = namRs;
	}

	public ResultSet getNhomRs() 
	{
		return this.nhomRs;
	}

	public void setNhomRs(ResultSet nhomRs) 
	{
		this.nhomRs = nhomRs;
	}

	public String getNtsId() 
	{
		return this.ntsId;
	}

	public void setNtsId(String ntsId) 
	{
		this.ntsId = ntsId;
	}

	public ResultSet getTaisanRs() 
	{
		return this.taisanRs;
	}

	public void setTaisanRs(ResultSet taisanRs) 
	{
		this.taisanRs = taisanRs;
	}

	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;
	}
	
	public String getMatsstr() 
	{
		return this.matsstr;
	}

	public void setMatsstr(String matsstr) 
	{
		this.matsstr = matsstr;
	}

	public String[] getMa() 
	{
		return this.ma;
	}

	public void setMa(String[] ma) 
	{
		this.ma = ma;
	}

	public String[] getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String[] diengiai) 
	{
		this.diengiai = diengiai;
	}
	
	public String[] getNhomTS() 
	{
		return this.nhom;
	}

	public void setNhomTS(String[] nhom) 
	{
		this.nhom = nhom;
	}

	public String[] getThangthu() 
	{
		return this.thangthu;
	}

	public void setThangthu(String[] thangthu) 
	{
		this.thangthu = thangthu;
	}
	
	public double[] getKhauhao() 
	{
		return this.khauhao;
	}

	public void setKhauhao(double[] khauhao) 
	{
		this.khauhao = khauhao;
	}

	public double[] getKhauhaoluyke() 
	{
		return this.khauhaoluyke;
	}

	public void setKhauhaoluyke(double[] khauhaoluyke) 
	{
		this.khauhaoluyke = khauhaoluyke;
	}

	public double[] getGiatriconlai() 
	{
		return this.giatriconlai;
	}

	public void setGiatriconlai(double[] giatriconlai) 
	{
		this.giatriconlai = giatriconlai;
	}
	
	public String getDienGiaiCT(){
		return this.dienGiaiCT;
	}
	
	public void setDienGiaiCT(String dienGiaiCT){
		this.dienGiaiCT = dienGiaiCT;
	}
	
	public void createRs() 
	{
		String query = "";
		
		for( int i = 1; i <= 12; i++ )
		{
			query += "select " + i + " as thang, N'Tháng " + i + "' as thangTen  ";
			if(i < 12)
			{
				query += " union all ";
			}
		}
		
		this.thangRs = db.get(query);
		
		int nam = Integer.parseInt(this.getDateTime().substring(0, 4));
		
		query = "";
		for(int i = nam-1; i <= nam + 1; i++)
		{
			query += "select " + i + " as nam, N'Năm " + i + "' as namTen ";
			if(i < nam + 1)
			{
				query += " union all ";
			}
		}
		
		this.namRs = this.db.get(query);
		init();
		
	}

	public String Delete()
	{
		try {
			String query = "";
			 query=" SELECT TOP 1 THANG,NAM FROM CHUNGTUKHAUHAOTAISAN  order by convert(NUMERIC,NAM) desc, convert(NUMERIC,THANG)  desc";
				System.out.println("Du lieu : "+query);
				ResultSet rs=db.get(query);
				int thang_kstruoc=0;
				int nam_kstruoc=0;
				if(rs.next()){
					thang_kstruoc=rs.getInt("THANG");
					nam_kstruoc=rs.getInt("NAM");
				}
				 rs.close();
				query="SELECT PK_SEQ FROM CHUNGTUKHAUHAOTAISAN WHERE thang="+thang_kstruoc+" and nam="+nam_kstruoc+" and PK_SEQ="+this.Id;
				System.out.println(query);
				ResultSet rs1=db.get(query);
				if(!rs1.next()){
					rs1.close();
					return "Vui lòng chỉ được xóa khấu hao tháng gần nhất: "+thang_kstruoc+"-"+nam_kstruoc;
				}
				rs1.close();
				
				query=" SELECT THANG,NAM FROM CHUNGTUKHAUHAOTAISAN    WHERE PK_SEQ ="+this.Id;
				System.out.println("Du lieu : "+query);
				ResultSet rs3=db.get(query);
				
				if(rs3.next()){
					this.thang=rs3.getString("THANG");
					this.nam=rs3.getString("NAM");
				}
				rs3.close();
			db.getConnection().setAutoCommit(false);
			
			
			query = 
					"DELETE ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'Khấu hao tài sản' and sochungtu ="+this.Id;		
			
					System.out.println(query);
					System.out.println("Huy phat sinh ke toan:"+query);
					if(!db.update(query))
					{
						this.msg = "MC1.2 Không thể hủy ERP_PHATSINHKETOAN " + query;
						db.getConnection().rollback();
						return this.msg;
					} 
					
					/*String ngayghinhan = nam + "-" + ( thang.trim().length() < 2 ? "0" + thang : thang )+ "-01";	
					ngayghinhan = getLastDayOfMonth(ngayghinhan);
					query =  "SELECT ISNULL(DIEUCHUYEN.TTCPID,TSCD.TTCP_FK) AS TTCPID, ISNULL(DIEUCHUYEN.TTCPNHANID,PB.TTCPNHAN_FK) AS TTCPNHANID,  ISNULL(DIEUCHUYEN.PHANTRAM,PB.PHANTRAM) AS PHANTRAM, KHTS.KHAUHAO   " +
							 "FROM ERP_TAISANCODINH TSCD " +
							 "LEFT JOIN " +
							 "(" +
							 " SELECT TOP 1 DC.TTCP_FK as TTCPID,DC.TAISAN_FK,PBDC.TTCP_FK AS TTCPNHANID, PBDC.PHANTRAM " +
							 " FROM ERP_DIEUCHUYENTAISAN_PHANBO PBDC " +
							 " INNER JOIN ERP_DIEUCHUYENTAISAN DC ON DC.PK_SEQ = PBDC.DIEUCHUYENTAISAN_FK " +
							 " WHERE DC.TAISAN_FK IN " + matsstr + "  AND DC.NGAYCHUNGTU <= '"+ngayghinhan+"' AND DC.TRANGTHAI=1 " +
							 " ORDER BY DC.NGAYCHUNGTU DESC" +
							 ")DIEUCHUYEN ON DIEUCHUYEN.TAISAN_FK = TSCD.PK_SEQ " +
							 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK  " +
							 "INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TTCPCHO_FK = TTCP.PK_SEQ AND TSCD.PK_SEQ = PB.TAISAN_FK " +
							 "INNER JOIN ERP_KHAUHAOTAISAN KHTS ON KHTS.TAISAN_FK = TSCD.PK_SEQ  " +
							 "WHERE TSCD.PK_SEQ IN " + matsstr + " AND KHTS.THANG = " + this.thang + " AND KHTS.NAM = " + this.nam + "  " +
							 "UNION ALL " +
							 "SELECT TSCD.TTCP_FK AS TTCPID, TTCP.PK_SEQ AS TTCPNHANID, 100 as PHANTRAM, KHTS.KHAUHAO   " +
							 "FROM ERP_TAISANCODINH TSCD " +
							 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK  " +
							 "LEFT JOIN " +
							 "(" +
							 " SELECT TOP 1 DC.TTCP_FK as TTCPID,DC.TAISAN_FK,DC.TTCP_FK AS TTCPNHANID, 100 AS PHANTRAM " +
							 " FROM ERP_DIEUCHUYENTAISAN DC " +
							 " WHERE DC.TAISAN_FK IN " + matsstr + "  AND DC.NGAYCHUNGTU <= '"+ngayghinhan+"'  AND DC.TRANGTHAI=1 " +
							 " ORDER BY DC.NGAYCHUNGTU DESC" +
							 ")DIEUCHUYENCPCHO ON DIEUCHUYENCPCHO.TAISAN_FK = TSCD.PK_SEQ " +
							 "INNER JOIN ERP_KHAUHAOTAISAN KHTS ON KHTS.TAISAN_FK = TSCD.PK_SEQ  " +
							 "WHERE TSCD.PK_SEQ IN " + matsstr + " AND KHTS.THANG = " + this.thang + " AND KHTS.NAM = " + this.nam + " " ;
							
							System.out.println(query);
							
							ResultSet rs2 = this.db.get(query);
							
							while(rs2.next()){
									query = "UPDATE ERP_TRUNGTAMCHIPHI_THUCCHI SET THUCCHI = THUCCHI - CONVERT(float, " + rs2.getString("KHAUHAO") + ")*CONVERT(float," + rs2.getString("PHANTRAM") +")/100  " +
											"WHERE TTCP_FK = " + rs2.getString("TTCPNHANID") + " AND NAM = '" + this.nam + "' AND THANG = '" + this.thang + "'";
									
									System.out.println(query);
									if(!this.db.update(query))
									{
											this.msg="Không thể cập nhật trạng thái "+query;
											this.db.getConnection().rollback();
											return this.msg;
									}
								
							}
							rs2.close();*/
					//HỦY KẾ TOÁN ĐÃ GHI NHẬN
					
					
					
					query="DELETE ERP_KHAUHAOTAISAN WHERE CTKHAUHAO_FK="+this.Id ;
					if(!this.db.update(query))
					{
							this.msg="Không thể cập nhật trạng thái "+query;
							this.db.getConnection().rollback();
							return this.msg;
					}
					
					query="DELETE CHUNGTUKHAUHAOTAISAN WHERE PK_SEQ = "+this.Id ;
					if(!this.db.update(query))
					{
							this.msg="Không thể cập nhật trạng thái "+query;
							this.db.getConnection().rollback();
							return this.msg;
					}
					
				
					
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			this.db.update("rollback");
			return "Đã xảy ra lỗi. Vui lòng kiểm tra lại hoặc liên hệ GESO xử lý.";
		}
		return "";
	}
	
	private void init()
	{
		try
		{
			String query;
			ResultSet rs;
		/*	query = "SELECT DISTINCT NTS.PK_SEQ AS NTSID, NTS.DIENGIAI " + 
					"FROM ERP_NHOMTAISAN NTS " +
					"LEFT JOIN ERP_TAISANCODINH TSCD ON TSCD.NHOMTAISAN_FK = NTS.PK_SEQ " +
  					"WHERE TSCD.TRANGTHAI = 1 AND NTS.CONGTY_FK = " + this.ctyId + " ORDER BY NTS.DIENGIAI";*/
			
			
			
			query = "SELECT DISTINCT LTS.PK_SEQ AS NTSID, LTS.DIENGIAI " + 
					"FROM ERP_LOAITAISAN LTS " +
					"LEFT JOIN ERP_TAISANCODINH TSCD ON TSCD.LOAITAISAN_FK = LTS.PK_SEQ " +
					"WHERE TSCD.TRANGTHAI = 1 AND TSCD.CONGTY_FK = " + this.ctyId + " ";
			System.out.println(query);
			this.LoaiRs = this.db.get(query);
			System.out.println("asdsasdadsa"+query);
			if(this.Id.length() > 0){
				query ="SELECT SOCHUNGTU,DIENGIAI,NAM,THANG FROM CHUNGTUKHAUHAOTAISAN WHERE PK_SEQ= "+this.Id ;
				ResultSet rsCt= db.get(query);
				if(rsCt.next())
				{
					this.soChungTu=rsCt.getString("SOCHUNGTU");
					this.dienGiaiCT=rsCt.getString("DIENGIAI");
					this.nam=rsCt.getString("NAM");
					this.thang=rsCt.getString("THANG");
				}
				rsCt.close();
				query=" select TSCD.ma +'-' + TSCD.diengiai AS MA,LTS.ma +'-' + LTS.diengiai as LTS,THANG,NAM,KHAUHAO,THANGTHU + ISNULL(TSCD.SOTHANGDAKHAUHAO,0) AS THANGTHU,KHAUHAO,khauHaoLuyKe,giaTriConlai,giaTriConlai+khauHaoLuyKe AS TONGGIATRI from ERP_KHAUHAOTAISAN Khts \n" +
					  " INNER JOIN ERP_TAISANCODINH TSCD ON TSCD.PK_SEQ=KHTS.TAISAN_FK \n" +
					  " INNER JOIN ERP_LOAITAISAN LTS ON LTS.PK_SEQ= TSCD.LOAITAISAN_FK \n" +
					  " where CTKHAUHAO_FK =" +this.Id;
				ResultSet khRs=db.get(query);
				System.out.println("bbbbbbbb"+query);
				while(khRs.next())
				{	
					IGiaTriKhauHao khauhao = new GiaTriKhauHao();
					khauhao.setDiengiai(khRs.getString("MA"));
					khauhao.setLoaiTaiSan(khRs.getString("LTS"));
					khauhao.setThangKhauHaoThucTe(khRs.getString("THANGTHU"));
					khauhao.setKhauhaodukien(khRs.getString("KHAUHAO"));
					khauhao.setKhauhaoluykedukien(khRs.getString("khauHaoLuyKe"));
					khauhao.setGiatriconlaidukien(khRs.getString("giaTriConlai"));
					khauhao.setTonggiatri(khRs.getString("tongGiaTri"));
					khauhaoList.add(khauhao);
				}
				khRs.close();
				

			}else{
				
//				query =	"select	count( distinct tscd.ma) as num \n" +
//						"from erp_taisancodinh tscd \n" +
//						"left join erp_taisancodinh_chitiet tsct on tsct.taisan_fk = tscd.pk_seq and tsct.khauhaothucte = 0 \n" + 
//						"inner join erp_nhomtaisan nts on nts.pk_seq = tscd.nhomtaisan_fk \n" +
//						"where tscd.congty_fk = " + this.ctyId + "  and tsct.khauhaodukien > 0 and len(tscd.thangbatdauKH) > 1 \n" +
//						"AND TSCD.PK_SEQ NOT IN ( \n" +
//						"	SELECT DISTINCT TAISAN_FK \n" +
//						"	FROM ERP_THANHLYTAISAN TL \n" +
//						"	INNER JOIN ERP_THANHLYTAISAN_TAISAN TL_TS ON TL_TS.THANHLYTAISAN_FK = TL.PK_SEQ \n" +
//						"	WHERE TL.TRANGTHAI IN (1, 2, 3) ) and tscd.TRANGTHAI = 1 \n" ;							
//
//				if(this.ntsId.length() > 0){
//					query = query + " and nts.pk_seq = " + this.ntsId + "";
//				}
//				
//				System.out.println("-- Lay khau hao: " + query);
			
				String[] param = new String[3];
				param[0] = this.ctyId;
//				param[1] = (this.ntsId.trim().length() > 0 ? this.ntsId : null);
//				param[1]=this.NppId;
				param[1] = this.nam;
				param[2] = this.thang;
				
				rs = this.db.getRsByPro("GetNewPhanBoTSCD_List", param);
				

				String ma = "";
				int i = 0;
				while(rs.next()){
					//Tao danh sach cac san pham, chi dung 1 thang dau de tao danh sach
					if(!rs.getString("ma").equals(ma)){
						ma = rs.getString("ma");
					
						if (this.matsstr.length()==0){
							this.matsstr = "'" +  rs.getString("ma") + "'";
						}else{
							this.matsstr = this.matsstr + ",'" +  rs.getString("ma") + "'";
						}
					
						IGiaTriKhauHao khauhao = new GiaTriKhauHao();
						khauhao.setDiengiai(rs.getString("diengiai"));
						khauhao.setMataisan(rs.getString("ma"));
						khauhao.setLoaiTaiSan(rs.getString("loai"));
						khauhao.setThangKhauHaoTrenHeThong(rs.getString("THANGTHU"));
						khauhao.setKhauhaodukien(rs.getString("khauhaodukien"));
						khauhao.setKhauhaoluykedukien(rs.getString("khauhaoluykedukien"));
						khauhao.setGiatriconlaidukien(rs.getString("giatriconlaidukien"));
						khauhao.setThangKhauHaoThucTe(rs.getString("THANGKHAUHAOTT"));
						khauhao.setTonggiatri(rs.getString("tongGiaTri"));
						khauhaoList.add(khauhao);
						if(i == 0){
							if (this.matsstr.length()==0){
								this.matsstr = "'" + rs.getString("ma") + "'";
							}else{
								this.matsstr = this.matsstr + ",'" + rs.getString("ma") + "'";
							}				
						}
					
						
						i++;
					}
				}
			
				System.out.println("query: \n" + query);
				rs.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

	public boolean save(HttpServletRequest request)throws ServletException, IOException
	{	
		String query;
		try
		{
			db.getConnection().setAutoCommit(false);
			String matsstr = "(";
			this.ma = request.getParameterValues("matscd");
			String khauhao;
			String thangthu;
			String khauhaoluyke;
			String giatriconlai;
			String tonggiatri;
			this.CheckKhauHao(this.nam, this.thang);
			/// NẾU ĐÃ TỒN TẠI KHẤU HAO THÌ XÓA HỦY KẾ TOÁN + GIẢM GIÁ TRỊ PHÂN BỔ TRUNG TÂM CHI PHÍ
			if(isKhauhao)
			{
				
				query = 
					"DELETE ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'Khấu hao tài sản' and sochungtu in (Select pk_Seq from CHUNGTUKHAUHAOTAISAN where  thang=" +this.thang +" AND nam= "+this.nam +")";		
			
					System.out.println(query);
					System.out.println("Huy phat sinh ke toan:"+query);
					if(!db.update(query))
					{
						msg = "MC1.2 Không thể hủy ERP_PHATSINHKETOAN " + query;
						db.getConnection().rollback();
						return false;
					}
				query="select nam,thang,taisan_fk,thangthu,CTKHAUHAO_FK from erp_khauhaotaisan where thang=" +this.thang +" AND nam= "+this.nam ;
				ResultSet rs= db.get(query);
				while(rs.next())
				{
					String id=rs.getString("nam") + ";" + rs.getString("thang") + ";" + rs.getString("taisan_fk") + ";" + rs.getString("thangthu")+ ";" + rs.getString("CTKHAUHAO_FK") ;

					//HỦY KẾ TOÁN ĐÃ GHI NHẬN
					
					query="DELETE ERP_KHAUHAOTAISAN WHERE THANG ="+thang + "AND  NAM ="+nam ;
					if(!this.db.update(query))
					{
							this.msg="Không thể cập nhật trạng thái "+query;
							this.db.getConnection().rollback();
							return false;
					}
					
					query="DELETE CHUNGTUKHAUHAOTAISAN WHERE THANG ="+thang + "AND NAM ="+nam ;
					if(!this.db.update(query))
					{
							this.msg="Không thể cập nhật trạng thái "+query;
							this.db.getConnection().rollback();
							return false;
					}
					
				}
					
			}
			query = "INSERT INTO CHUNGTUKHAUHAOTAISAN(THANG, NAM,TRANGTHAI,DIENGIAI,SOCHUNGTU,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA) \n" +
			"VALUES(" + this.thang.trim() + "," + this.nam.trim() + ",1,N'" + this.dienGiaiCT + "', N'" + this.soChungTu + "', \n" +
			"'" + getDateTime() + "','" + getDateTime() + "',"+this.userId+","+this.userId+" )\n";
					
			System.out.println("cau lenh them khau hao tai san:\n" + query + "\n---------------------------------");
			if(!this.db.update(query))
			{

		
					this.msg="Không thể cập nhật CHUNGTUKHAUHAOTAISAN "+query;
					this.db.getConnection().rollback();
					return false;
		
			}
			
			String id="";
			ResultSet rsCTKH = db.get("select IDENT_CURRENT('CHUNGTUKHAUHAOTAISAN') as ID ");
			if(rsCTKH.next())
			{
				id = rsCTKH.getString("ID");
			}
			rsCTKH.close();
			
			Utility util = new Utility();
			if(this.ma != null)
			{
				for(int i = 0; i < this.ma.length ; i++)
				{
					khauhao = util.antiSQLInspection(request.getParameter("khauhao_" + ma[i])).replace(",", "");
					thangthu = util.antiSQLInspection(request.getParameter("thangthu_" + ma[i]));
					khauhaoluyke = util.antiSQLInspection(request.getParameter("khauhaoluyke_" + ma[i])).replace(",", "");
					giatriconlai = util.antiSQLInspection(request.getParameter("giatriconlai_" + ma[i])).replace(",", "");
					
					
				
					
					
					query = "INSERT INTO ERP_KHAUHAOTAISAN(THANG, NAM, TAISAN_FK, KHAUHAO,KHAUHAOLUYKE,GIATRICONLAI, THANGTHU ,CTKHAUHAO_FK) \n" +
							"VALUES(" + this.thang.trim() + "," + this.nam.trim() + "," + this.ma[i].trim() + ", " + khauhao + ", " + khauhaoluyke + " , " + giatriconlai + " ,\n" +
							"'" + thangthu + "',"+id+" )\n" ;
									
					System.out.println("cau lenh them khau hao tai san:\n" + query + "\n---------------------------------");
					if(!this.db.update(query))
					{
	
				
							this.msg="Không thể cập nhật ERP_TAISANCODINH "+query;
							this.db.getConnection().rollback();
							return false;
				
					}
					matsstr = matsstr + this.ma[i] + ",";
				}
			

				
				if(matsstr.length() > 1)
				{
					matsstr = matsstr.substring(0, matsstr.length() - 1) + ")";

					String ngayghinhan = this.nam + "-" + ( this.thang.trim().length() < 2 ? "0" + this.thang : this.thang );
					ngayghinhan = this.nam + "-" + ( this.thang.trim().length() < 2 ? "0" + this.thang : this.thang )+ "-01";	
					ngayghinhan = getLastDayOfMonth(ngayghinhan);
					
					query = " SELECT ISNULL(DIEUCHINH.TAIKHOAN_FK,NCP.TAIKHOAN_FK) as SOHIEUTAIKHOAN, \n" +
					" TSCD.PK_SEQ as MATAISAN,KH.PK_SEQ AS SOCHUNGTU, TSCD.MA as MATSCD ," +
					/*+ "ROUND(SUM(ISNULL(THAYDOI.PHANTRAM,\n" +
					" ISNULL(CONGDUNG.PHANTRAM,0)) * KH.KHAUHAO / 100 ),0) AS TOTALKHAUHAO, \n" +*/
					" ROUND(SUM(ISNULL(DIEUCHINH.PHANTRAM,ISNULL(KMCP.PHANTRAM,0)) * KH.KHAUHAO / 100 ),0) AS TOTALKHAUHAO," +
					" ISNULL(TK2.SOHIEUTAIKHOAN,'') AS SOHIEUTAIKHOANCO, \n" +
					"  ISNULL(DIEUCHINH.KHOANMUCCHIPHI_FK,KMCP.KHOANMUCCHIPHI_FK) AS KHOANMUCPHI_FK  \n" +
					" FROM  ERP_TAISANCODINH TSCD \n" +
					" INNER JOIN ERP_TAISANCODINH_KHOANMUCCHIPHI KMCP ON KMCP.TAISANCODINH_FK = TSCD.PK_SEQ  and PHANTRAM>0 \n" +
					" INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = KMCP.KHOANMUCCHIPHI_FK \n" +
					" INNER JOIN Erp_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK \n" +
					" INNER JOIN ERP_TAIKHOANKT TK2 ON LTS.TAIKHOANKH_FK = TK2.pk_seq  \n" +
					" left JOIN ERP_KHAUHAOTAISAN KH ON TSCD.PK_SEQ = KH.TAISAN_FK  " +
					" LEFT JOIN " +
					" (" +
					"  SELECT NCPDC.TAIKHOAN_FK,DCTSKMCP.TAISAN_FK,DCTSKMCP.KHOANMUCCHIPHI_FK,DCTSKMCP.PHANTRAM FROM ERP_DIEUCHUYENTAISAN_KHOANMUCCHIPHI DCTSKMCP" +
					"  LEFT JOIN ERP_DIEUCHUYENTAISAN DCTS ON DCTSKMCP.DIEUCHUYENTAISAN_FK=DCTS.PK_SEQ" +
					"  LEFT JOIN ERP_NHOMCHIPHI NCPDC ON NCPDC.PK_SEQ=DCTSKMCP.KHOANMUCCHIPHI_FK" +
					"  WHERE DCTS.NGAYCHUNGTU<='"+ngayghinhan+"' AND DCTS.TRANGTHAI=1" +
					"  )DIEUCHINH" +
					"  ON DIEUCHINH.TAISAN_FK=TSCD.PK_SEQ" +
					"  " +
					/*" LEFT JOIN \n" +
					" ( \n" +
					" SELECT  TS_CD.TAISAN_FK,TS_CD.CONGDUNG_FK,TS_CD.PHANTRAM ,TK1.SOHIEUTAIKHOAN	FROM ERP_TAISANCODINH_CONGDUNG TS_CD \n" +
					" INNER JOIN ERP_CONGDUNG CD ON TS_CD.CONGDUNG_FK = CD.PK_SEQ \n" +
					" INNER JOIN ERP_TAIKHOANKT TK1 ON CD.TAIKHOAN_FK = TK1.SOHIEUTAIKHOAN \n" +
					" ) CONGDUNG ON TSCD.pk_seq = CONGDUNG.TAISAN_FK \n" +
					" AND CONGDUNG.TAISAN_FK NOT IN (SELECT TAISAN_FK FROM ERP_DIEUCHUYENTAISAN WHERE TRANGTHAI=1) \n" +
					" LEFT JOIN  \n" +
					" ( \n" +
					"  SELECT  CD.TAISAN_FK,CD.CONGDUNG_FK,CD.PHANTRAM ,TK3_TD.SOHIEUTAIKHOAN \n" +
					"  FROM ERP_DIEUCHUYENTAISAN_CONGDUNG  CD  \n" +
					"  INNER JOIN ERP_DIEUCHUYENTAISAN DC ON DC.PK_SEQ=CD.DIEUCHUYENTAISAN_FK  \n" +
					"  LEFT JOIN ERP_CONGDUNG CDTD ON CD.CONGDUNG_FK=CDTD.PK_SEQ  \n" +
					"  LEFT JOIN ERP_TAIKHOANKT TK3_TD on TK3_TD.SOHIEUTAIKHOAN =CDTD.TAIKHOAN_FK \n" +
					"  WHERE DC.TRANGTHAi=1 AND DC.NGAYCHUNGTU <= '"+ngayghinhan+"' \n" +
					"  AND DC.TAISAN_FK IN "+matsstr+" AND DC.PK_SEQ IN \n" +
					"  ( SELECT TOP 1 PK_SEQ FROM ERP_DIEUCHUYENTAISAN WHERE TRANGTHAI=1 AND TAISAN_FK IN "+matsstr+"  AND NGAYCHUNGTU <= '"+ngayghinhan+"' \n" +
					"  ORDER BY NGAYCHUNGTU DESC ) \n" +
				 
					"  )THAYDOI ON THAYDOI.TAISAN_FK=TSCD.PK_SEQ  \n" +*/
				 

					"  WHERE TSCD.PK_SEQ IN  "+matsstr+" AND KH.THANG = '" + this.thang + "' AND KH.NAM = '" + this.nam + "' \n" +
					" GROUP BY NCP.TAIKHOAN_FK, TSCD.PK_SEQ,TSCD.MA,   \n" +
					" TSCD.LOAITAISAN_FK,KH.PK_SEQ, TK2.SOHIEUTAIKHOAN,KMCP.KHOANMUCCHIPHI_FK,DIEUCHINH.TAIKHOAN_FK,DIEUCHINH.PHANTRAM,DIEUCHINH.KHOANMUCCHIPHI_FK ";
					
					
										
					System.out.println("____LAY TAI KHOAN KHAU HAO: \n" + query + "\n-------------------------------------");
					ResultSet rsTk = this.db.get(query);
		
					boolean isContinue = false;
					while(rsTk.next())
					{
						isContinue = true;
						double totalKhauHao = rsTk.getDouble("totalKhauHao");
						
						String taikhoanCO_SoHieu = rsTk.getString("sohieutaikhoanCO");
						
						// LẤY SỐ CHỨNG TỪ TỪ BẢNG CHUNGTUKHAUHAOTAISAN
						String sochungtu = id;

						String taikhoanNO_SoHieu = rsTk.getString("sohieutaikhoan");	
						String khoanmucchiphi_fk = rsTk.getString("KHOANMUCPHI_FK")==null?"":rsTk.getString("KHOANMUCPHI_FK");	
//						String khoanmucchiphi_fk = rsTk.getString("KHOANMUCPHI_FK");

//						String TAIKHOAN_FK=rsTk.getString("TAIKHOAN_FK")==null?"":rsTk.getString("TAIKHOAN_FK");
						if(taikhoanCO_SoHieu== null || taikhoanNO_SoHieu== null || taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0   )
						{
							msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return false;
						}
						
						String tiente_fk = "100000";
						
//						this.msg = util.Update_TaiKhoan(db, thang, nam,ngayghinhan,ngayghinhan,	"Khấu hao tài sản", sochungtu ,taikhoanNO_SoHieu, taikhoanCO_SoHieu,"", Double.toString(totalKhauHao),
//						Double.toString(totalKhauHao),	"Tài sản", rsTk.getString("MATAISAN"),  "" , "", "0", "", "", tiente_fk,"", "1", Double.toString(totalKhauHao),
//						Double.toString(totalKhauHao), "Khấu hao tài sản");	
						System.out.println("vao dinh khoan");
						if(totalKhauHao>0)
						{	
//							this.msg = util.Update_TaiKhoan_TheoSoHieu_DienGiai_KhoanMuc( db, thang, nam, ngayghinhan, ngayghinhan, "Khấu hao tài sản", sochungtu, taikhoanNO_SoHieu, taikhoanCO_SoHieu, ""
//									, Double.toString(totalKhauHao), "Tài sản", rsTk.getString("MATAISAN"), "0", "", "", tiente_fk, "", "1", Double.toString(totalKhauHao)
//									, Double.toString(totalKhauHao), "Khấu hao tài sản" ,this.dienGiaiCT,khoanmucchiphi_fk,"",this.soChungTu,this.ctyId,this.NppId,DVKDID);
//								
							
							this.msg = util.Update_TaiKhoan_TheoSoHieu_DienGiai_KhoanMuc_NO_CO(db, thang, nam, ngayghinhan, ngayghinhan, "Khấu hao tài sản", sochungtu, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
									Double.toString(totalKhauHao), Double.toString(totalKhauHao),"","","0", "Tài sản", rsTk.getString("MATAISAN"), "0", "", "", tiente_fk, "", "1", Double.toString(totalKhauHao), Double.toString(totalKhauHao), "Khấu hao tài sản",this.dienGiaiCT,khoanmucchiphi_fk,"",this.soChungTu,"1");
		
							if(this.msg.trim().length() > 0)
							{
								db.getConnection().rollback();
								return false;
							}
						}
					}
					
					if(isContinue == false)
					{
						msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						db.getConnection().rollback();
						return false;
					}
					
					rsTk.close();
									
					query =  "SELECT ISNULL(DIEUCHUYEN.TTCPID,TSCD.TTCP_FK) AS TTCPID, ISNULL(DIEUCHUYEN.TTCPNHANID,PB.TTCPNHAN_FK) AS TTCPNHANID,  ISNULL(DIEUCHUYEN.PHANTRAM,PB.PHANTRAM) AS PHANTRAM, KHTS.KHAUHAO   " +
					 "FROM ERP_TAISANCODINH TSCD " +
					 "LEFT JOIN " +
					 "(" +
					 " SELECT TOP 1 DC.TTCP_FK as TTCPID,DC.TAISAN_FK,PBDC.TTCP_FK AS TTCPNHANID, PBDC.PHANTRAM " +
					 " FROM ERP_DIEUCHUYENTAISAN_PHANBO PBDC " +
					 " INNER JOIN ERP_DIEUCHUYENTAISAN DC ON DC.PK_SEQ = PBDC.DIEUCHUYENTAISAN_FK " +
					 " WHERE DC.TAISAN_FK IN " + matsstr + "  AND DC.NGAYCHUNGTU <= '"+ngayghinhan+"' AND DC.TRANGTHAI=1 " +
					 " ORDER BY DC.NGAYCHUNGTU DESC" +
					 ")DIEUCHUYEN ON DIEUCHUYEN.TAISAN_FK = TSCD.PK_SEQ " +
					 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK  " +
					 "INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TTCPCHO_FK = TTCP.PK_SEQ AND TSCD.PK_SEQ = PB.TAISAN_FK " +
					 "INNER JOIN ERP_KHAUHAOTAISAN KHTS ON KHTS.TAISAN_FK = TSCD.PK_SEQ  " +
					 "WHERE TSCD.PK_SEQ IN " + matsstr + " AND KHTS.THANG = " + this.thang + " AND KHTS.NAM = " + this.nam + "  " +
					 "UNION ALL " +
					 "SELECT TSCD.TTCP_FK AS TTCPID, TTCP.PK_SEQ AS TTCPNHANID, 100 as PHANTRAM, KHTS.KHAUHAO   " +
					 "FROM ERP_TAISANCODINH TSCD " +
					 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK  " +
					 "LEFT JOIN " +
					 "(" +
					 " SELECT TOP 1 DC.TTCP_FK as TTCPID,DC.TAISAN_FK,DC.TTCP_FK AS TTCPNHANID, 100 AS PHANTRAM " +
					 " FROM ERP_DIEUCHUYENTAISAN DC " +
					 " WHERE DC.TAISAN_FK IN " + matsstr + "  AND DC.NGAYCHUNGTU <= '"+ngayghinhan+"'  AND DC.TRANGTHAI=1 " +
					 " ORDER BY DC.NGAYCHUNGTU DESC" +
					 ")DIEUCHUYENCPCHO ON DIEUCHUYENCPCHO.TAISAN_FK = TSCD.PK_SEQ " +
					 "INNER JOIN ERP_KHAUHAOTAISAN KHTS ON KHTS.TAISAN_FK = TSCD.PK_SEQ  " +
					 "WHERE TSCD.PK_SEQ IN " + matsstr + " AND KHTS.THANG = " + this.thang + " AND KHTS.NAM = " + this.nam + " " ;
					
					System.out.println(query);
					
					ResultSet rs = this.db.get(query);
					
					while(rs.next()){

						query = "INSERT INTO ERP_TRUNGTAMCHIPHI_THUCCHI(TTCP_FK, THANG, NAM, THUCCHI) VALUES" +
								"(" + rs.getString("TTCPNHANID") + ", '" + this.thang + "','" + this.nam + "', CONVERT(float, " + rs.getString("KHAUHAO") + ")*CONVERT(float," + rs.getString("PHANTRAM") +")/100 )";
						
						System.out.println(query);
						
						if(!this.db.update(query)){
							query = "UPDATE ERP_TRUNGTAMCHIPHI_THUCCHI SET THUCCHI = THUCCHI + CONVERT(float, " + rs.getString("KHAUHAO") + ")*CONVERT(float," + rs.getString("PHANTRAM") +")/100  " +
									"WHERE TTCP_FK = " + rs.getString("TTCPNHANID") + " AND NAM = '" + this.nam + "' AND THANG = '" + this.thang + "'";
							
							System.out.println(query);
							this.db.update(query);
						}
					}
					rs.close();
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Không thể thực hiện khấu hao. " + e.getMessage();
			return false;
		}
	}

	
	
	public boolean Cancel(String Id)
	{	
		String query;
		try
		{
			db.getConnection().setAutoCommit(false);
			String[] tmp = Id.split(";");
//			Utility util = new Utility();
						
			String nam = tmp[0];
			String thang = tmp[1];
			String tsId = tmp[2];
			String lanthu = tmp[3];
			String sct = tmp[4]; // SỐ PK_SEQ
			
		
			
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU = N'Khấu hao tài sản' and SOCHUNGTU = "+sct+" ";
			System.out.println(query);
			ResultSet rsPSKT = db.get(query);
			try 
			{
				while(rsPSKT.next())
				{
					String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
					String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
					double NO = rsPSKT.getDouble("NO");
					double CO = rsPSKT.getDouble("CO");
					double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
					
					//NEU LA CO THI BAY GIO GHI GIAM CO LAI
					if( NO > 0 )
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					else
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					
					System.out.println("1.REVERT NO-CO: " + query);
					
					if(db.updateReturnInt(query)<0)
					{
						msg = "KHÔNG THỂ REVERT KẾ TOÁN. YÊU CẦU LIÊN HỆ LẬP TRÌNH ";
						db.getConnection().rollback();
						return false;
					}
					
				}
				rsPSKT.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			
			//HỦY KẾ TOÁN ĐÃ GHI NHẬN
			query = " DELETE ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'Khấu hao tài sản' and SOCHUNGTU IN ('" + sct + "') ";		
			
			System.out.println(query);
			System.out.println("xoa phat sinh ke toan 1:"+query);
			if(!db.update(query))
			{
				msg = "Không thể hủy ERP_PHATSINHKETOAN " + query;
				db.getConnection().rollback();
				return false;
			}
			
							
			
			String ngayghinhan = nam + "-" + ( thang.trim().length() < 2 ? "0" + thang : thang )+ "-01";	
			ngayghinhan = getLastDayOfMonth(ngayghinhan);
							
			query= "SELECT ISNULL(DIEUCHUYEN.TTCPID,TSCD.TTCP_FK) AS TTCPID, ISNULL(DIEUCHUYEN.TTCPNHANID,PB.TTCPNHAN_FK) AS TTCPNHANID,  ISNULL(DIEUCHUYEN.PHANTRAM,PB.PHANTRAM) AS PHANTRAM, KHTS.KHAUHAO   " +
			 "FROM ERP_TAISANCODINH TSCD " +
			 "LEFT JOIN " +
			 "(" +
			 " SELECT TOP 1 DC.TTCP_FK as TTCPID,DC.TAISAN_FK,PBDC.TTCP_FK AS TTCPNHANID, PBDC.PHANTRAM " +
			 " FROM ERP_DIEUCHUYENTAISAN_PHANBO PBDC " +
			 " INNER JOIN ERP_DIEUCHUYENTAISAN DC ON DC.PK_SEQ = PBDC.DIEUCHUYENTAISAN_FK " +
			 " WHERE DC.TAISAN_FK = " + tsId + "  AND DC.NGAYCHUNGTU <= '"+ngayghinhan+"' AND DC.TRANGTHAI=1 " +
			 " ORDER BY DC.NGAYCHUNGTU DESC" +
			 ")DIEUCHUYEN ON DIEUCHUYEN.TAISAN_FK = TSCD.PK_SEQ " +
			 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK  " +
			 "INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TTCPCHO_FK = TTCP.PK_SEQ AND TSCD.PK_SEQ = PB.TAISAN_FK " +
			 "INNER JOIN ERP_KHAUHAOTAISAN KHTS ON KHTS.TAISAN_FK = TSCD.PK_SEQ  " +
			 "WHERE TSCD.PK_SEQ = " + tsId + " AND KHTS.THANG = " + Integer.parseInt(thang) + " AND KHTS.NAM = " + nam + "  " +
			 "UNION ALL " +
			 "SELECT TSCD.TTCP_FK AS TTCPID, TTCP.PK_SEQ AS TTCPNHANID, 100 as PHANTRAM, KHTS.KHAUHAO   " +
			 "FROM ERP_TAISANCODINH TSCD " +
			 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK  " +
			 "LEFT JOIN " +
			 "(" +
			 " SELECT TOP 1 DC.TTCP_FK as TTCPID,DC.TAISAN_FK,DC.TTCP_FK AS TTCPNHANID, 100 AS PHANTRAM " +
			 " FROM ERP_DIEUCHUYENTAISAN DC " +
			 " WHERE DC.TAISAN_FK = " + tsId + "  AND DC.NGAYCHUNGTU <= '"+ngayghinhan+"'  AND DC.TRANGTHAI=1 " +
			 " ORDER BY DC.NGAYCHUNGTU DESC" +
			 ")DIEUCHUYENCPCHO ON DIEUCHUYENCPCHO.TAISAN_FK = TSCD.PK_SEQ " +
			 "INNER JOIN ERP_KHAUHAOTAISAN KHTS ON KHTS.TAISAN_FK = TSCD.PK_SEQ  " +
			 "WHERE TSCD.PK_SEQ = " + tsId + " AND KHTS.THANG = " + Integer.parseInt(thang) + " AND KHTS.NAM = " + nam + " " ;
			
			System.out.println(query);
	
			ResultSet rs = this.db.get(query);
	
			while(rs.next()){
				query = "UPDATE ERP_TRUNGTAMCHIPHI_THUCCHI SET THUCCHI = THUCCHI - CONVERT(float, " + rs.getString("KHAUHAO") + ")*CONVERT(float," + rs.getString("PHANTRAM") +")/100  " +
						"WHERE TTCP_FK = " + rs.getString("TTCPNHANID") + " AND NAM = '" + nam + "' AND THANG = '" + Integer.parseInt(thang) + "'";
			
				System.out.println(query);
				this.db.update(query);
			}
			rs.close();
			
			query = "DELETE ERP_KHAUHAOTAISAN WHERE THANG = " + Integer.parseInt(thang) + " AND NAM = " + nam + " AND TAISAN_FK = " + tsId + " AND THANGTHU = " + lanthu;
			System.out.println(query);
			this.db.update(query);
			
			query = "UPDATE ERP_TAISANCODINH_CHITIET \n" +
					"SET KHAUHAOTHUCTE = 0, KHAUHAOLUYKETHUCTE = KHAUHAOLUYKETHUCTE - " + khauhao + ", \n" +
					"GIATRICONLAITHUCTE = GIATRICONLAITHUCTE + " + khauhao + " \n" +
					"WHERE TAISAN_FK = " + tsId + " AND THANG = '" + lanthu + "' \n";
			System.out.println(query);
			this.db.update(query);
			
			query = "SELECT TSCD.THANHTIEN AS TONGGIATRI, TSCD.THANHTIEN - ISNULL(KHAUHAO.TONGKHAUHAO,0) AS GIATRICONLAI \n" +
					"FROM ERP_TAISANCODINH TSCD \n" +
					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TAISAN_FK AS TSID, SUM(KHAUHAO) AS TONGKHAUHAO \n" +
					"	FROM ERP_KHAUHAOTAISAN  \n" +
					"	WHERE TRANGTHAI <> 2 \n" +
					"	GROUP BY TAISAN_FK \n" +
					")KHAUHAO ON KHAUHAO.TSID = TSCD.PK_SEQ \n" +
					"WHERE TSCD.PK_SEQ = " + tsId + " \n"	;
			System.out.println(query);
			rs = this.db.get(query);
			rs.next();
//			double tonggiatri = rs.getDouble("TONGGIATRI");
//			double giatriconlai = rs.getDouble("GIATRICONLAI");
			rs.close();
//			this.Tinhkhauhao_Update(tsId, tonggiatri, giatriconlai);
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Không thể thực hiện khấu hao. " + e.getMessage();
			return false;
		}

	}

//	public boolean Cancel(Idbutils myDb, String Id)
//	{	
//		String query;
//		try
//		{
//			Utility util= new Utility();
//			List<Integer> thangNamKs= new ArrayList<Integer>(2);
//			Utility.GetThangKhoaSoMax(myDb, thangNamKs);
//			
//			
//			
//			String[] tmp = Id.split(";");				
//			String nam = tmp[0];
//			String thang = tmp[1];
//			String tsId = tmp[2];
//			String lanthu = tmp[3];
//			String sct = tmp[4]; // SỐ PK_SEQ
//			
//			if((Integer.parseInt(nam)<thangNamKs.get(1))||(Integer.parseInt(nam)==thangNamKs.get(1) && Integer.parseInt(thang)<thangNamKs.get(0)))
//			{
//				msg = "Dữ liệu phát sinh kế toán tháng "+thang +" , năm  "+nam +" đã có khóa sổ! không thể hủy";
//				myDb.getConnection().rollback();
//				return false;
//			}
//			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
//				    "from ERP_PHATSINHKETOAN " +
//				    "where LOAICHUNGTU = N'Khấu hao tài sản' and SOCHUNGTU = "+sct+" and maDoiTuong = " + tsId;
//			System.out.println(query);
//			ResultSet rsPSKT = myDb.get(query);
//			try 
//			{
//				while(rsPSKT.next())
//				{
//					String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
//					String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
//					double NO = rsPSKT.getDouble("NO");
//					double CO = rsPSKT.getDouble("CO");
//					double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
//					
//					//NEU LA CO THI BAY GIO GHI GIAM CO LAI
//					if( NO > 0 )
//					{
//						query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
//								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
//					}
//					else
//					{
//						query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
//								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
//					}
//					
//					System.out.println("1.REVERT NO-CO: " + query);
//					
//					if(myDb.updateReturnInt(query)<0)
//					{
//						msg = "MC1.1 KHÔNG THỂ REVERT KẾ TOÁN. YÊU CẦU LIÊN HỆ LẬP TRÌNH ";
//						myDb.getConnection().rollback();
//						return false;
//					}
//					
//				}
//				rsPSKT.close();
//			} 
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//	
//			
//							
//			
//			String ngayghinhan = nam + "-" + ( thang.trim().length() < 2 ? "0" + thang : thang )+ "-01";	
//			ngayghinhan = getLastDayOfMonth(ngayghinhan);
//							
//			query= "SELECT ISNULL(DIEUCHUYEN.TTCPID,TSCD.TTCP_FK) AS TTCPID, ISNULL(DIEUCHUYEN.TTCPNHANID,PB.TTCPNHAN_FK) AS TTCPNHANID,  ISNULL(DIEUCHUYEN.PHANTRAM,PB.PHANTRAM) AS PHANTRAM, KHTS.KHAUHAO   " +
//				 "FROM ERP_TAISANCODINH TSCD " +
//				 "LEFT JOIN " +
//				 "(" +
//				 " SELECT TOP 1 DC.TTCP_FK as TTCPID,DC.TAISAN_FK,PBDC.TTCP_FK AS TTCPNHANID, PBDC.PHANTRAM " +
//				 " FROM ERP_DIEUCHUYENTAISAN_PHANBO PBDC " +
//				 " INNER JOIN ERP_DIEUCHUYENTAISAN DC ON DC.PK_SEQ = PBDC.DIEUCHUYENTAISAN_FK " +
//				 " WHERE DC.TAISAN_FK = " + tsId + "  AND DC.NGAYCHUNGTU <= '"+ngayghinhan+"' AND DC.TRANGTHAI=1 " +
//				 " ORDER BY DC.NGAYCHUNGTU DESC" +
//				 ")DIEUCHUYEN ON DIEUCHUYEN.TAISAN_FK = TSCD.PK_SEQ " +
//				 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK  " +
//				 "INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TTCPCHO_FK = TTCP.PK_SEQ AND TSCD.PK_SEQ = PB.TAISAN_FK " +
//				 "INNER JOIN ERP_KHAUHAOTAISAN KHTS ON KHTS.TAISAN_FK = TSCD.PK_SEQ  " +
//				 "WHERE TSCD.PK_SEQ = " + tsId + " AND KHTS.THANG = " + Integer.parseInt(thang) + " AND KHTS.NAM = " + nam + "  " +
//				 "UNION ALL " +
//				 "SELECT TSCD.TTCP_FK AS TTCPID, TTCP.PK_SEQ AS TTCPNHANID, 100 as PHANTRAM, KHTS.KHAUHAO   " +
//				 "FROM ERP_TAISANCODINH TSCD " +
//				 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK  " +
//				 "LEFT JOIN " +
//				 "(" +
//				 " SELECT TOP 1 DC.TTCP_FK as TTCPID,DC.TAISAN_FK,DC.TTCP_FK AS TTCPNHANID, 100 AS PHANTRAM " +
//				 " FROM ERP_DIEUCHUYENTAISAN DC " +
//				 " WHERE DC.TAISAN_FK = " + tsId + "  AND DC.NGAYCHUNGTU <= '"+ngayghinhan+"'  AND DC.TRANGTHAI=1 " +
//				 " ORDER BY DC.NGAYCHUNGTU DESC" +
//				 ")DIEUCHUYENCPCHO ON DIEUCHUYENCPCHO.TAISAN_FK = TSCD.PK_SEQ " +
//				 "INNER JOIN ERP_KHAUHAOTAISAN KHTS ON KHTS.TAISAN_FK = TSCD.PK_SEQ  " +
//				 "WHERE TSCD.PK_SEQ = " + tsId + " AND KHTS.THANG = " + Integer.parseInt(thang) + " AND KHTS.NAM = " + nam + " " ;
//				
//			System.out.println(query);
//	
//			ResultSet rs = myDb.get(query);
//	
//			while(rs.next()){
//
//				query = "UPDATE ERP_TRUNGTAMCHIPHI_THUCCHI SET THUCCHI = THUCCHI - CONVERT(float, " + rs.getString("KHAUHAO") + ")*CONVERT(float," + rs.getString("PHANTRAM") +")/100  " +
//						"WHERE TTCP_FK = " + rs.getString("TTCPNHANID") + " AND NAM = '" + nam + "' AND THANG = '" + Integer.parseInt(thang) + "'";
//			
//				System.out.println(query);
//				myDb.update(query);
//			}
//			rs.close();
//			
//			query = "DELETE ERP_KHAUHAOTAISAN WHERE THANG = " + Integer.parseInt(thang) + " AND NAM = " + nam + " AND TAISAN_FK = " + tsId + " AND THANGTHU = " + lanthu;
//			System.out.println(query);
//			myDb.update(query);
//
//			return true;
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			myDb.update("rollback");
//			this.msg = "MC1.3 Không thể thực hiện khấu hao. " + e.getMessage();
//			return false;
//		}
//
//	}


	public boolean chayKeHoachPhanBoTSCD(Idbutils myDb, String tscdId)
	{
		String[] param = new String[1];
		param[0] = tscdId;
		try{
			String result = myDb.execProceduce2("KeHoachPhanBo_TSCD", param);
			if (result.trim().length() > 0)
			{
				this.setMsg("CKHPBTSCD1.2 Không thể tạo mới tài sản cố định \n" + result);
				myDb.getConnection().rollback();
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMsg("CKHPBTSCD1.3 Không thể tạo mới tài sản cố định \n");
			try {
				myDb.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}
	

	private boolean Tinhkhauhao_Update(String Id, double tonggiatri, double giatriconlai)
	{
		double thanhtien = tonggiatri;
		double sotienconlai = giatriconlai;
		double luykebandau = 0;
		double conlaibandau = 0;
		
		int month = 1;
		int n = 0;
		
		// Tinh so thang khau hao
		String query = "SELECT COUNT(THANG)  AS NUM FROM ERP_TAISANCODINH_CHITIET WHERE TAISAN_FK = '" + Id + "'";
		System.out.println("khauhao_update: " + query);
		ResultSet rs = this.db.get(query);
		
		try{
			if(rs != null) {
				rs.next();			
				
				n = rs.getInt("NUM");
				rs.close();
								
				query = "SELECT THANG, KHAUHAOLUYKETHUCTE, GIATRICONLAITHUCTE FROM ERP_TAISANCODINH_CHITIET " +
						"WHERE KHAUHAOTHUCTE > 0  AND TAISAN_FK = " + Id + " ORDER BY THANG DESC";
					
				rs = this.db.get(query);

				if(rs.next()){					
					month = rs.getInt("THANG") + 1;
					luykebandau = rs.getDouble("KHAUHAOLUYKETHUCTE");
					conlaibandau = rs.getDouble("GIATRICONLAITHUCTE");
					
				}
				rs.close();
				
			}
			double[] khdkVal = new double[n];
			double[] lkdkVal = new double[n];
			double[] gtdkVal = new double[n];
		
			lkdkVal[0] = 0;
			
			int m = n - month + 1;
			System.out.println("m: " + m);
			
			for(int i = 0 ; i < m; i++)
			{
										
				if(i == (m-1))  // thang cuoi cung
				{		
					if(i > 0)
						khdkVal[i] = thanhtien - lkdkVal[i-1];
					else
						khdkVal[i] = sotienconlai;
					
					if(i > 0) 
						lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
					else
						lkdkVal[i] = luykebandau + khdkVal[i] ;

					gtdkVal[i] = thanhtien - lkdkVal[i];
				}
				else
				{
					khdkVal[i] = Math.round(sotienconlai/(m));
					
					if(i > 0) 
						lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
					else
						lkdkVal[i] = luykebandau + khdkVal[i] ;
					
					gtdkVal[i] = thanhtien - lkdkVal[i];
				}					
			 
				query = " INSERT INTO ERP_TAISANCODINH_CHITIET (TAISAN_FK, THANG, KHAUHAODUKIEN, KHAUHAOLUYKEDUKIEN, GIATRICONLAIDUKIEN, " +
						" KHAUHAOTHUCTE, KHAUHAOLUYKETHUCTE, GIATRICONLAITHUCTE) " +
						" VALUES( '" + Id + "', '" + month + "', "+ khdkVal[i] + ", " + lkdkVal[i] + ", " + gtdkVal[i] + ", '0', " + luykebandau + ", " + conlaibandau + " )";
				
				System.out.println("khauhao_update 1: " + query);
				if(!this.db.update(query)){
					query = "UPDATE ERP_TAISANCODINH_CHITIET SET KHAUHAODUKIEN = "+ khdkVal[i] + ", KHAUHAOLUYKEDUKIEN = " + lkdkVal[i] + ", " +
							"GIATRICONLAIDUKIEN = " + gtdkVal[i] + ", KHAUHAOTHUCTE = '0', " +
							"KHAUHAOLUYKETHUCTE = " + luykebandau + ", GIATRICONLAITHUCTE = " + conlaibandau + " " +
							"WHERE TAISAN_FK = '" + Id + "' AND THANG = '" + month + "'";
					
					System.out.println("khauhao_update 2: " + query);
					this.db.update(query);
				}
				month++;
			}
				
		}catch(java.sql.SQLException e){
			e.printStackTrace();
			System.out.println("khauhao_update: " + e.toString());
			return false;
		}
		return true;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public String getnppdangnhap() {
		
		return this.nppdangnhap;
	}

	
	public void setnppdangnhap(String nppdangnhap) {
		
		this.nppdangnhap = nppdangnhap;
	}

	@Override
	public void DBClose() {
		// TODO Auto-generated method stub
		this.db.shutDown();
	}

	public static String getLastDayOfMonth(String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedDate;
		Calendar c = null;
		try {
			convertedDate = dateFormat.parse(date);
			c = Calendar.getInstance();
			c.setTime(convertedDate);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		Date lastDayOfMonth = c.getTime();  
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		return sdf.format(lastDayOfMonth);
	}

	public String[] getThangthuTT() {
		return thangthuTT;
	}

	public void setThangthuTT(String[] thangthuTT) {
		this.thangthuTT = thangthuTT;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public void CheckKhauHao(String nam,String thang)
	{
		String query="Select count(*) as NUM FROM ERP_KHAUHAOTAISAN WHERE THANG ="+thang +" AND NAM ="+nam ;
		System.out.println("check khau hao "+query);
		ResultSet rs= db.get(query);
		try
		{
			rs.next();
			int num = rs.getInt("NUM");
			
			if(num>0)
			{
				this.isKhauhao=true;
				return;
			}
			else
			{
				this.isKhauhao=false;
				return;
			}
		}
		catch (Exception e) {
			this.isKhauhao=false;
			return;
		}
	}

	public boolean isKhauhao() {
		return isKhauhao;
	}

	public void setKhauhao(boolean isKhauhao) {
		this.isKhauhao = isKhauhao;
	}

	public List<IGiaTriKhauHao> getKhauhaoList() {
		return khauhaoList;
	}

	public void setKhauhaoList(List<IGiaTriKhauHao> khauhaoList) {
		this.khauhaoList = khauhaoList;
	}

	public String getSoChungTu() {
		return soChungTu;
	}

	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getNppId() {
		return NppId;
	}

	public void setNppId(String nppId) {
		NppId = nppId;
	}

	public String getDVKDID() {
		return DVKDID;
	}

	public void setDVKDID(String dVKDID) {
		DVKDID = dVKDID;
	}

	public ResultSet getLoaiRs() {
		return LoaiRs;
	}

	public void setLoaiRs(ResultSet loaiRs) {
		LoaiRs = loaiRs;
	}
}