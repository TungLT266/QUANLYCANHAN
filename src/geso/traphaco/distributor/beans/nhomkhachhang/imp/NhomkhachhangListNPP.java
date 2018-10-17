package geso.traphaco.distributor.beans.nhomkhachhang.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.nhomkhachhang.INhomkhachhangListNPP;
import geso.traphaco.distributor.beans.nhomkhachhang.INhomkhachhangNPP;

import java.io.Serializable;
import java.util.List;

	public class NhomkhachhangListNPP implements INhomkhachhangListNPP, Serializable
	{
		private static final long serialVersionUID = -9217977556733610214L;

		// Tieu chi tim kiem
		String congtyId;
		String userId;
		private String diengiai;
		private String trangthai;		 
		private String tungay;
		private String denngay;
		private List<INhomkhachhangNPP> nkhlist;
		private boolean search = false;
		private Utility util;
		private String maKH;
		
		public NhomkhachhangListNPP(String maKH)
		{
			this.maKH = maKH;
				
		}
		public NhomkhachhangListNPP(String[] param)
		{
			this.diengiai = param[0];
			this.trangthai = param[1];
			this.tungay = param[2];
			this.denngay = param[3];	
			this.util=new Utility();
		}
		
		public NhomkhachhangListNPP()
		{
			
			this.maKH = "";
			this.diengiai = "";
			this.trangthai = "";
			this.tungay = "";
			this.denngay = "";	
			this.util=new Utility();
		}


		public String getDiengiai(){
			return this.diengiai;
		}
		
		public void setDiengiai(String diengiai){
			this.diengiai = diengiai;
		}
		

		public String getTrangthai(){
			return this.trangthai;
		}
		
		public void setTrangthai(String trangthai){
			this.trangthai = trangthai;
		}
		
		public String getTungay(){
			return this.tungay;
		}
		
		public void setTungay(String tungay){
			this.tungay = tungay;
		}

		public String getDenngay(){
			return this.denngay;
		}
		
		public void setDenngay(String denngay){
			this.denngay = denngay;
		}
		

		public List<INhomkhachhangNPP> getNkhList(){
			return this.nkhlist;
		}
		
		public void setNkhList(List<INhomkhachhangNPP> nkhlist){
			this.nkhlist = nkhlist;
		}
		public boolean getSearch()
		{
			return this.search;
		}

		public void setSearch(boolean search)
		{
			this.search = search;
		}
		public void setMaKH(String maKH)
		{
			this.maKH = maKH;
		}
		public String getMaKH(){
			return this.maKH;
		}
		public String getCongtyId() 
		{
			return this.congtyId;
		}

		public void setCongtyId(String congtyId) 
		{
			this.congtyId = congtyId;
		}
		public String getUserId()
		{
			return this.userId;
		}

		public void setUserId(String userId)
		{
			this.userId = userId;
		}
	}
