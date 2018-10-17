package geso.traphaco.erp.beans.nhomnhacungcapcn.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import geso.traphaco.erp.beans.nhomnhacungcapcn.INhomnhacungcapcn;
import geso.traphaco.erp.beans.nhomnhacungcapcn.INhomnhacungcapcnList;

	public class NhomnhacungcapcnList implements INhomnhacungcapcnList, Serializable
	{
		private static final long serialVersionUID = -9217977556733610214L;

		// Tieu chi tim kiem
		private String ten;
		private String diengiai;
		private String trangthai;		 
		private String tungay;
		private String denngay;
		private List<INhomnhacungcapcn> Ncclist;
		
		private boolean search = false;

		private String maNcc;
		String chixem;
		
		public NhomnhacungcapcnList(String maKH)
		{
			this.maNcc = maKH;
				
		}
		public NhomnhacungcapcnList(String[] param)
		{
			this.ten = param[0];
			this.trangthai = param[1];
			this.tungay = param[2];
			this.denngay = param[3];	
			this.chixem = "0";
		}
		
		public NhomnhacungcapcnList()
		{
			this.ten = "";
			this.maNcc = "";
			this.diengiai = "";
			this.trangthai = "";
			this.tungay = "";
			this.denngay = "";		
			this.chixem = "0";
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
		

		public List<INhomnhacungcapcn> getNccList(){
			return this.Ncclist;
		}
		
		public void setNccList(List<INhomnhacungcapcn> Ncclist){
			this.Ncclist = Ncclist;
		}
		public boolean getSearch()
		{
			return this.search;
		}

		public void setSearch(boolean search)
		{
			this.search = search;
		}
		public String getMaNcc(){
			return this.maNcc;
		}
		public void setMaNcc(String maNcc)
		{
			this.maNcc = maNcc;
		}
		public void DBClose() {
		}
		
		
		public void setChixem(String chixem) {
			
			this.chixem = chixem;
		}

		public String getChixem() {
			
			return this.chixem;
		}
		public String getTen() {
			return ten;
		}
		public void setTen(String ten) {
			this.ten = ten;
		}
		
	
	}
