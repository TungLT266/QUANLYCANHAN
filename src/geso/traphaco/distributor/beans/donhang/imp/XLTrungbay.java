package geso.traphaco.distributor.beans.donhang.imp;

import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;

public class XLTrungbay
{
	private String donhangId;
	
	public XLTrungbay(String donhangId)
	{
		this.donhangId = donhangId;
	}
	
	public int getSoXuat(String cttb_fk, String khachhang) 
	{
		dbutils db = new dbutils();
		int soxuat = 0;
		
		String sql = "select * from CTTB_NHOMSPTRUNGBAY where cttrungbay_fk = '" + cttb_fk + "' order by thutudieukien";
		ResultSet dieukien = db.get(sql);
		String madk[] = new String[100];
		int i = 0;
		
		String chuoidk="";
		
		if(dieukien != null)
		{
			try 
			{
				while(dieukien.next())
				{
					chuoidk = chuoidk + dieukien.getString("nhomsptrungbay_fk") +",";
					if(Integer.parseInt(dieukien.getString("pheptoan")) == 0)
					{
						madk[i] = chuoidk; //moi mang la mot chuoi cac dieu kien ANH lien tiep
						i++;
						chuoidk ="";
					}
				}
				
				if(i == 0 && chuoidk.length() > 0)
					madk[i] = chuoidk;
				dieukien.close();
				int j = 0;
				
				while( j <= i && chuoidk.length() > 0 )
				{
					String arr[] = madk[j].split(",");//lay ra cac dieu kien co cung phep toan AND lien tieo
					boolean flag = true;
					int lan = 1;
					while(flag)
					{	
						int h = 0;
						while(h < arr.length && flag)
						{	
							int loai = loaict(arr[h], db);
							if(loai == 2)//dieu kien or 
							{ 
								if(!kiemtra_or(arr[h], khachhang, lan, db))//neu la tong luong hoac tong tien
								{   
									flag = false;
									break;
								}
							}
							else
							{  //neu la san pham bat buoc
								if(!kiemtra_and(arr[h], khachhang, lan, db))
								{
									flag = false;
									break;
								}
							}
							
							h++;			
						}
						
						if(flag)
							lan++;//neu thoa thi tiep tuc
						
						if(lan > soxuat && lan > 1 )
						{
							soxuat = lan;
						}
					}
					j++;//tang dieu kien or tiep theo					
				}	
			} 
			catch(Exception e) 
			{	
				System.out.println("115.Exception: " + e.getMessage());
			}
		}

		if(soxuat > 1 )
			soxuat = soxuat - 1;
		
		return soxuat;	
	}
	
	boolean kiemtra_or(String cttb,String khachhang, int lan, dbutils db) //voi san pham khong bat buoc
	{
		String ketqua = "";
		boolean flag = false;
		
		String sql = "select isnull(tongtien,0) as tien, isnull(tongluong,0) as sl from NHOMSPTRUNGBAY where pk_seq = '" + cttb + "'";
		ResultSet rs = db.get(sql);
		try 
		{
			rs.next();
			float tongtien = Float.parseFloat(rs.getString("tien")) * lan;
			int soluong = Integer.parseInt(rs.getString("sl")) * lan;
			sql =	"select isnull(sum(soluong), 0) as tongsoluong, isnull(sum(soluong*giamua), 0) * 1.1 as tongtien " +
					"from donhang_sanpham where donhang_fk = '" + this.donhangId + "' " +
					" 	and sanpham_fk in ( select sanpham_fk from NHOMSPTRUNGBAY_SANPHAM where nhomsptrungbay_fk = '" + cttb + "')" ;
					
			ResultSet tb = db.get(sql);
			if(tb != null)
			{
				if(tb.next())
				{
					if(tongtien > 0 )
					{ 
						if(tongtien <= Float.parseFloat(tb.getString("tongtien")))//neu so tien 
							flag = true;
					}
					else
					{
						if(soluong <= Integer.parseInt(tb.getString("tongsoluong"))) //neu la so luong
							flag = true;
					}
				}
				else
				{
					flag = false;
				}
				
				tb.close();
			}
		} 
		catch(Exception e)
		{
			flag = false;
			System.out.println("115.Error: " + e.getMessage());
		}
		return flag;
	}
		
	boolean kiemtra_and(String cttb,String khachhang, int lan, dbutils db)
	{
		String sql =	"select sanpham_fk, case when soluong is null then 0 else soluong * " + lan + " end as soluong " +
						"from NHOMSPTRUNGBAY_SANPHAM where nhomsptrungbay_fk = '" + cttb + "'";

		ResultSet rs = db.get(sql);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{  
					String sanpham = rs.getString("sanpham_fk");
				    String soluong =rs.getString("soluong");
					
					sql = "select sum(soluong) as sl from donhang_sanpham where  donhang_fk = '" + this.donhangId + "' " +
							"and sanpham_fk ='"+ sanpham +"' group by sanpham_fk having sum(soluong) >= '" + soluong + "'";
				 
					ResultSet tb = db.get(sql);
					System.out.println("dk trung bay :" + tb.getRow() + "------- Tinnh Trung bay :  " + sql);
					
					if(tb != null)
					{	
						if(!tb.next())
							return false;
					}
					tb.close();
				}
				rs.close();
			} 
			catch(Exception e) 
			{	
				System.out.println("115.Loi: " + e.getMessage());
				return false;
			}
		}	
		
		return true;
	}
	
	int loaict(String cttb, dbutils db)
	{   
		int st = 0;
		String sql = "select * from NHOMSPTRUNGBAY where pk_seq = '" + cttb + "' ";
		ResultSet rs = db.get(sql);
		if(rs!=null)
		{
			try {
				rs.next();
				st = Integer.parseInt(rs.getString("loai"));
				rs.close();
			} catch(Exception e) {
				
				e.printStackTrace();
			}
			
		}
		return st;
	}
	
	public static void main(String[] arg)
	{
		XLTrungbay tb = new XLTrungbay("5594601");
		
		System.out.println("Tong so xuat duoc huong: " + tb.getSoXuat("100016", "460429"));
	}
	
}
