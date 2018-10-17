package geso.traphaco.erp.beans.congtytrahang.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.congtytrahang.ISanpham;

public class Sanpham implements ISanpham 
{
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String masp;
	String tensp;
	String soluong;
	String tiente;
	String donvitinh;
	String dongia;
	String thanhtien;
	String nhomhang;
	String ngaynhan;
	String khonhan;
	String dungsai;
	
	List<ISanpham> spList;
	
	public Sanpham()
	{
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.soluong = "";
		this.tiente = ""; //4
		this.donvitinh = "";
		this.dongia = "";
		this.thanhtien = "";
		this.nhomhang = "";
		this.ngaynhan = "";
		this.khonhan = "";
		this.dungsai = "0";
		
		this.spList = new ArrayList<ISanpham>();
	}
	
	public Sanpham(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.tensp = param[2];
		this.soluong = param[3];
		this.tiente = param[4];
		this.donvitinh = param[5];
		this.dongia = param[6];
		this.thanhtien = param[7];
		this.nhomhang = param[8];
		this.ngaynhan = param[9];
		this.khonhan = param[10];
		this.dungsai = "0";
		
		this.spList = new ArrayList<ISanpham>();
	}
	
	public Sanpham(String spId, String spMa, String spTen, String soluong, String tiente, String dvt, String dongia, String thanhtien, String nhomhang, String ngaynhan, String khonhan)
	{
		this.id = spId;
		this.masp = spMa;
		this.tensp = spTen;
		this.soluong = soluong;
		this.tiente = tiente;
		this.donvitinh = dvt;
		this.dongia = dongia;
		this.thanhtien = thanhtien;
		this.nhomhang = nhomhang;
		this.ngaynhan = ngaynhan;
		this.khonhan = khonhan;
		this.dungsai = "0";
	}
	
	public String getId() 
	{	
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;	
	}
	
	public String getMasanpham()
	{	
		return this.masp;
	}
	
	public void setMasanpham(String masp)
	{
		this.masp = masp;		
	}
	
	public String getTensanpham() 
	{	
		return this.tensp;
	}
	
	public void setTensanpham(String tensp) 
	{
		this.tensp = tensp;	
	}

	public String getSoluong()
	{
		return this.soluong;
	}

	public void setSoluong(String soluong) 
	{
		this.soluong = soluong;
	}
	
	public String getDonvitinh() 
	{
		return this.donvitinh;
	}
	
	public void setDonvitinh(String donvitinh) 
	{
		this.donvitinh = donvitinh;		
	}

	public String getDongia() 
	{
		return this.dongia;
	}

	public void setDongia(String dongia) 
	{
		this.dongia = dongia;
	}

	public String getTiente()
	{
		return this.tiente;
	}

	public void setTiente(String tiente) 
	{
		this.tiente = tiente;
	}

	public String getThanhtien()
	{
		return this.thanhtien;
	}

	public void setThanhtien(String thanhtien) 
	{
		this.thanhtien = thanhtien;
	}

	public String getNhomhang() 
	{
		return this.nhomhang;
	}
	
	public void setNhomhang(String nhomhang) 
	{
		this.nhomhang = nhomhang;
	}

	public String getNgaynhan() 
	{
		return this.ngaynhan;
	}

	public void setNgaynhan(String ngaynhan) 
	{
		this.ngaynhan = ngaynhan;
	}

	public String getKhonhan() 
	{
		return this.khonhan;
	}

	public void setKhonhan(String khonhan)
	{
		this.khonhan = khonhan;
	}

	public String getDungsai()
	{
		return this.dungsai;
	}

	public void setDungsai(String dungsai)
	{
		this.dungsai = dungsai;
	}

	@Override
	public List<ISanpham> getListSanPham() {
		// TODO Auto-generated method stub
		return this.spList;
	}

	@Override
	public void setListSanPham(List<ISanpham> spList) {
		// TODO Auto-generated method stub
		this.spList = spList;
	}
	
	private boolean Check(ISanpham des, ISanpham src)
	{
		List<ISanpham> arr = new ArrayList<ISanpham>();
		List<ISanpham> done = new ArrayList<ISanpham>();
		
		for(int i = 0; i < src.getListSanPham().size(); i ++) {
			arr.add(src.getListSanPham().get(i));
		}
		
		while(arr.size() > 0) {
			//System.out.println("arr = " + arr);
			ISanpham current = arr.get(0);
			//System.out.println("Current = " + current.getTensanpham());
			//Quét các con của current
			for(int i = 0; i < current.getListSanPham().size(); i++) {
				ISanpham child = current.getListSanPham().get(i);
				//System.out.println("child["+i+"] = " + child.getTensanpham());
				//Kiểm tra des
				if(child == des) {
					//System.out.println(current.getTensanpham() + " = " + child.getTensanpham());
					return false;
				} 
				//Kiểm tra đã duyệt nút này chưa
				else
				{
					if(!done.contains(child)){
						//System.out.println("done add child " + child.getTensanpham() );
						arr.add(child);
					}
				}
			}
			//Đưa current vào danh sách đã duyệt.
			done.add(current);
			//Xóa current khỏi danh sách chờ
			arr.remove(current);
		}

		return true;
	}
	
	public static void main(String[] arg)
	{
		/*ISanpham A = new Sanpham(); 
		ISanpham B = new Sanpham(); 
		ISanpham C = new Sanpham(); 
		ISanpham D = new Sanpham(); 
		ISanpham E = new Sanpham(); 
		ISanpham F = new Sanpham();
		ISanpham G = new Sanpham(); 
		ISanpham H = new Sanpham();  
		
		A.getListSanPham().add(B);
		A.getListSanPham().add(C);
		A.getListSanPham().add(D);
		B.getListSanPham().add(E);
		//B.getListSanPham().add(F);

		//C.getListSanPham().add(F);
		C.getListSanPham().add(G);
		D.getListSanPham().add(H);
		E.getListSanPham().add(C);
		G.getListSanPham().add(D);
		G.getListSanPham().add(B);
		H.getListSanPham().add(E);
		
		Sanpham sp = new Sanpham();
		
		System.out.println(sp.Check(F, A));*/
		
		List<String[]> ttcp_pb = new ArrayList<String[]>();
		
        ttcp_pb.add(new String[] { "A", "B" }); 
        //ttcp_pb.add(new String[] { "A", "C" });
        ttcp_pb.add(new String[] { "B", "E" });
        //ttcp_pb.add(new String[] { "B", "H" });
        ttcp_pb.add(new String[] { "C", "G" });
        ttcp_pb.add(new String[] { "D", "H" });
        ttcp_pb.add(new String[] { "E", "C" });
        ttcp_pb.add(new String[] { "G", "D" });
        ttcp_pb.add(new String[] { "H", "B" });
        ttcp_pb.add(new String[] { "B", "C" });
        ttcp_pb.add(new String[] { "C", "A" });
        ttcp_pb.add(new String[] { "A", "G" });
        ttcp_pb.add(new String[] { "D", "A" });

        System.out.println(check(ttcp_pb, "F", "A"));
		

	}

	
	
	static boolean check(List<String[]> ttcp_pb_ttcp, String src, String des)
    {
        if (src == des) return false;

        List<String> arr = new ArrayList<String>();
        arr.add(des);

        System.out.println("SIZE: " + ttcp_pb_ttcp.size());
        for (int i = 0; i < ttcp_pb_ttcp.size(); i++)
        {
            if (src == ttcp_pb_ttcp.get(i)[0] && des == ttcp_pb_ttcp.get(i)[1]) 
            	return false;

            if (arr.contains(ttcp_pb_ttcp.get(i)[0]))
            {
                if (ttcp_pb_ttcp.get(i)[1] == src) 
                	return false;
                else if (!arr.contains(ttcp_pb_ttcp.get(i)[1]))
                {
                    arr.add(ttcp_pb_ttcp.get(i)[1]);
                }
            }
        }
        return true;
    }

    /*static void Main(String[] args)
    {
        List<String[]> ttcp_pb = new ArrayList<String[]>();
        ttcp_pb.add(new String[] { "A", "B" }); 
        ttcp_pb.add(new String[] { "A", "C" });
        ttcp_pb.add(new String[] { "B", "E" });
        ttcp_pb.add(new String[] { "B", "F" });
        ttcp_pb.add(new String[] { "C", "G" });
        ttcp_pb.add(new String[] { "D", "H" });
        ttcp_pb.add(new String[] { "E", "C" });
        ttcp_pb.add(new String[] { "G", "D" });
        ttcp_pb.add(new String[] { "F", "A" });

        System.out.println(check(ttcp_pb, "F", "A"));
        
        //Console.WriteLine(check(ttcp_pb, "F", "A"));
        //Console.ReadKey();
    }*/
	

}
