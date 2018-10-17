<%@page import="geso.traphaco.center.util.Erp_Item"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.phieuthanhtoan.*"%>
<%@ page import="geso.traphaco.erp.beans.phieuthanhtoan.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%
	IErpDonmuahang_Giay dmhBean = (IErpDonmuahang_Giay) session.getAttribute("dmhBean");
	ResultSet dvthList = dmhBean.getDvthList();
	ResultSet loaihhList = dmhBean.getLoaiList();
	ResultSet nvRs = dmhBean.getNhanvienRs();
	ResultSet khRs = dmhBean.getKhachHangRs();
	ResultSet kbhRs = dmhBean.getkbhRs();
	ResultSet htttRs = dmhBean.getHtttRs();
	ResultSet nhacungcapRs = dmhBean.getNhaCungCapRs();
	ResultSet PbRs = dmhBean.getPBList();
	ResultSet SpRs = dmhBean.getSPList();
	ResultSet BvRs = dmhBean.getBenhVienList();
	ResultSet DbRs = dmhBean.getDiabanList();
	ResultSet rsNguoidenghi = dmhBean.getNhanvienRs();
	List<ISanpham> spList = dmhBean.getSpList();
	List<IDonvi> dvList = dmhBean.getDvList();
	List<INhacungcap> nccList = dmhBean.getNccList();
	List<ITiente> ttList = dmhBean.getTienteList();
	List<IKho> khoList = dmhBean.getKhoList();
	String userId = (String) session.getAttribute("userId");
	String nppId = (String) session.getAttribute("nppId");
	String userTen = (String) session.getAttribute("userTen");
	//ResultSet rs = dmhBean.getDuyet();
	String[] cpkDienGiai = dmhBean.getCpkDienGiai();
	String[] cpkSotien = dmhBean.getCpkSoTien();
	Utility util = (Utility) session.getAttribute("util");
	int[] quyen = new  int[5];
	quyen = util.Getquyen("ErpPhieuThanhToanUpdateSvl","",userId);
	int soDongThem = dmhBean.getSoDongThem();
	List<IPhieuchitamung> phieuchiList = dmhBean.getPhieuchiTURs();
	List<Erp_Item> loaiDoiTuongList = dmhBean.getLoaiDoiTuongList();
	List<Erp_Item> chiNhanhList = dmhBean.getChiNhanhList();
	List<Erp_Item> sanPhamKhoList = dmhBean.getSanPhamKhoList();
	List<Erp_Item> doiTuongKhacList = dmhBean.getDoiTuongKhacList();
	
	request.getSession().setAttribute("dvthId", dmhBean.getDvthId());
	
	NumberFormat formatterVND = new DecimalFormat("#,###,###");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100;
}

#ajax_listOfOptions div {re
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
}
</style>

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spList.js"></script>

<!-- <script type="text/javascript" src="../scripts/erp-dntt.js"></script> -->

<script type="text/javascript" src="../scripts/dropdowncontent2.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script language="javascript" type="text/javascript">
function changeNCC()
{ 		
	 document.forms['dmhForm'].action.value='changeNCC';
    document.forms["dmhForm"].submit();
}
function KhoanMucPhi(){
	 document.forms['dmhForm'].action.value='changKMP';
	    document.forms["dmhForm"].submit();
}

function changeHTTT()
{ 	
	var taiKhoanDoiTuongKhacId = document.getElementById("taiKhoanDoiTuongKhacId");
	var tenTaiKhoanDoiTuongKhac = document.getElementById("tenTaiKhoanDoiTuongKhac");
	var dtk = document.getElementById("dtk");
	var tkk1 = document.getElementById("tkk1");
	var tkk2 = document.getElementById("tkk2");
	var htttId = document.getElementById("htttId");
	if (taiKhoanDoiTuongKhacId != null)
	{
		if(htttId != null && htttId.value == "100004" )
		{
			dtk.colSpan = "1";
			tkk1.style.removeProperty("display");
			tkk2.style.removeProperty("display");
		}
		else
		{
			dtk.colSpan = "3";
			tkk1.style.display = 'none';
			tkk2.style.display = 'none';
		}
	}
}



function replaces()
{
	var loaidoituong =  document.forms["dmhForm"].loaidoituong.value ;
	//var loaidoituong = document.getElementById("loaidoituong").value;
 	
  	var idsp = document.getElementsByName("idsp");
	var masp = document.getElementsByName("masp");
	var tensp = document.getElementsByName("tensp");
	 
	var thanhtientruocthue = document.getElementsByName("thanhtientruocthue");
	var tienthue = document.getElementsByName("tienthue");
	var thanhtiensauthue = document.getElementsByName("thanhtiensauthue");

	var sodong =  masp.length;
	var i;
	for(i = 0; i < sodong; i++)
	{
		if(  masp.item(i).value != "" || document.getElementById("loaihh").value == 2 || document.getElementById("loaihh").value == 4   )
		{
			var sp = masp.item(i).value;
			var pos = parseInt(sp.indexOf(" -- "));

			if(pos > 0)
			{
				masp.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" -- ")) + 3);
				//nếu là loại sản phẩm khác thì bình thường, đối với loại 2 chi phí thì không có bỏ diễn giải did
				if( document.getElementById("loaihh").value == 2 || document.getElementById("loaihh").value == 4 ){
//					if(tensp.item(i).value==""){
						tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
						sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
						//donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
//					}else{
//						sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
//					}
					
				}else{
					tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					//donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				}
				
				
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				//nhomhang.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				idsp.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				
			}
		 
		}
		else 
		{
			 if(document.getElementById("loaihh").value != 2){		
					idsp.item(i).value = "";
					tensp.item(i).value = "";
					thanhtientruocthue.item(i).value = "";
					tienthue.item(i).value = "";
					thanhtiensauthue.item(i).value = "";
					 
			 }
		}
	}  
	 
	
	
	//CAP NHAT CHI PHI KHAC
	var sotienCPK = document.getElementsByName("sotienCPK");
	var totalCPK = 0;
	
	for(var j = 0; j < sotienCPK.length; j++ )
	{
		if( sotienCPK.item(j).value != '' )
		{
			totalCPK += parseFloat(sotienCPK.item(j).value.replace(/,/g,""));
			sotienCPK.item(j).value = DinhDangDonGia(sotienCPK.item(j).value.replace(/,/g,""));
		}
	}
	
	document.getElementById("cpKhac").value = DinhDangDonGia(totalCPK); 
	
	var loaidoituong = document.getElementsByName("loaidoituong");
	var stt = document.getElementsByName("stt");
	
	if(loaidoituong.item(0).value != "0")
	{
		for(var j = 0; j < stt.length; j++ )
		{
		   for(var k = 0; k < 20; k++)
		   {    
		     var mst = document.getElementById("mst" +j +k);
		     var tenncc = document.getElementById("tenncc" +j +k); 
		    		     
		     var masothue = document.getElementById("mst" +j +k).value;    
		    
		    if(masothue != "" && masothue != null)
		    {        
			     var pos = parseInt(masothue.indexOf("[ "));
			     
			     if(pos>0)
			     {
			      mst.value  = masothue.substring(0, pos);
			      
			      masothue = masothue.substr(parseInt(masothue.indexOf("[")) + 2);
			      tenncc.value = masothue.substring(0, parseInt(masothue.indexOf("]"))); 
			     }
		     }
		    }
		 }
		}
	
		for (var j = 0; j < stt.length; j++){		
			for(var i = 0; i < 15; i++)
			{
				var masp = document.getElementsByName("masp" + j + i);
				var spId = document.getElementsByName("spId" + j +  i);
				var tensp = document.getElementsByName("tensp" + j +  i);
				var temp = masp.item(0).value;
				
				if(temp != "" && temp != null)
			    {        
				     var pos = parseInt(temp.indexOf("- "));
				     if(pos > 0)
				     {
					      //tensp = masp.substr(parseInt(masp.indexOf("- ")) + 2);
					      
				    	  masp.item(0).value  = temp.substring(0, pos);
				    	  spId.item(0).value  = temp.substring(0, pos);
				    	  tensp.item(0).value = temp.substr(parseInt(temp.indexOf("- ")) + 2);
				     }
		
			    }
			}
		}		
	
		setTimeout(replaces, 500);
	}
	

	
	function TinhTien()
	{
		tinhthue();
	}
	
	function tinhTienTong(index)
	{
		
		var thanhtientruocthue = document.getElementsByName("thanhtientruocthue");
		var tienthue = document.getElementsByName("tienthue");
		var thanhtiensauthue = document.getElementsByName("thanhtiensauthue");
		
		var thanhTienSauThue = 
			parseFloat(thanhtientruocthue.item(index).value.replace(/\$|\,/g,'')) 
					+ parseFloat(tienthue.item(index).value.replace(/\$|\,/g,''));
		thanhtiensauthue.item(index).value = DinhDangTien(thanhTienSauThue);
		
		thanhtientruocthue.item(index).value = DinhDangTien(parseFloat(thanhtientruocthue.item(index).value.replace(/\$|\,/g,''))); 
		tienthue.item(index).value = DinhDangTien(parseFloat(tienthue.item(index).value.replace(/\$|\,/g,''))); 
		var tensp = document.getElementsByName("tensp");
		
		//Tính so sánh với tiền chi tiết
		var cong = document.getElementsByName("cong" + index);
		var tongTienSauThue = 0;
		
		for (var i = 0; i < cong.length; i++)
			if (cong.item(i).value != "")
				tongTienSauThue += parseFloat(cong.item(i).value.replace(/\$|\,/g,''));
		


		if (tongTienSauThue != 0 && tongTienSauThue != thanhTienSauThue)
			document.getElementById("Msg").value = "Thành tiền sau thuế ở dòng " + (index + 1) + " không đúng với chi tiết";
		
		
// 		var 
		tinhthue();
	}
	
	function kiemTraTongTien()
	{
		console.log("start tinh tien tong");
		var cc = document.getElementsByName("thanhtientruocthue");
		var tienthue = document.getElementsByName("tienthue");
		var thanhtiensauthue = document.getElementsByName("thanhtiensauthue");
		var tensp = document.getElementsByName("tensp");
		
		console.log("thanhtiensauthue.lenght: " + thanhtiensauthue.length);
		
		for (var index = 0; index < thanhtiensauthue.length; index++)
		{
			if (tensp.item(index).value != "")
			{
				console.log("masp: " + tensp.item(index).value);
				//Tính so sánh với tiền chi tiết
				var cong = document.getElementsByName("cong" + index);
				var tongTienSauThue = 0;
				var thanhTienSauThue = thanhtiensauthue.item(index).value.replace(/\$|\,/g,'');
				for (var i = 0; i < cong.length; i++)
					if (cong.item(i).value != "")
						tongTienSauThue += parseFloat(cong.item(i).value.replace(/\$|\,/g,''));
				
				console.log("tongTienSauThue: " + tongTienSauThue);
				console.log("thanhTienSauThue: " + thanhTienSauThue);
				if (tongTienSauThue != 0 && tongTienSauThue != thanhTienSauThue)
				{
					document.getElementById("Msg").value = "Thành tiền sau thuế ở dòng " + (index + 1) + " không đúng với chi tiết";
					return false;
				}
				
				console.log("end tinh tien tong");
			}
		}
		return true;
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	 function Dinhdangdukien(element)
	 {
	 	element.value=DinhDangTien(element.value);
	 	if(element.value== '' )
	 	{
	 		element.value= '';
	 	}
	 	
	 }

	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
	
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
		 
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
		
		if(isNaN(num))
		num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

		var kq;
		if(sole.length >= 0)
		{
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
		
		//alert(kq);
		return kq;
	}
	
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	 function saveform()
	 {	
		 var dvth = document.getElementById("dvthId");
		 var nguongoc = document.getElementById("nguongoc");
		 var tiente = document.getElementById("tiente_fk");
		
		 var lhh = document.getElementById("loaihh");
		 var error = document.getElementById("Msg");
		 
		 if(dvth.value == "")
		 {
			 error.value="Vui lòng chọn đơn vị thực hiện";
			 dvth.focus();
			 return;
		 }
		 
		 if(nguongoc.value == "")
		 {
		 	nguongoc.focus();
		 	error.value="Vui lòng chọn nguồn gốc hàng hóa";
			return;
		 }
		 
		 if(tiente.value=="")
		 {
		 	tiente.focus();
		 	error.value="Vui lòng chọn loại tiền tệ!";
			return;
		 }
		 
		 if(document.getElementById("htttId").value == "")
		 {
			 alert('Vui lòng chọn hình thức thanh toán');
			 return;
		 }
		 
		 
		 var loaidoituong =  document.forms["dmhForm"].loaidoituong.value ;
			//var loaidoituong = document.getElementById("loaidoituong").value;
		 
		 if(loaidoituong=="0"){
			 var nccId = document.getElementById("nccId");
			 if(nccId.value == "")
			 {
				 nccId.focus(); 
				 error.value="Vui lòng chọn nhà cung cấp";
				return;
			 }
			}else if(loaidoituong=="1"){
				 var nvId = document.getElementById("nvId");
				 if(nvId.value == "")
				 {
					 nvId.focus(); 
					 error.value="Vui lòng chọn nhân viên";
					return;
				 }			
			}else if(loaidoituong=="2"){
				 var khId = document.getElementById("khId");
				 if(khId.value == "")
				 {
					 khId.focus(); 
					 error.value="Vui lòng chọn khách hàng";
					return;
				 }
			}else if(loaidoituong=="3"){
				 var chiNhanhId = document.getElementById("chiNhanhId");
				 if(chiNhanhId.value == "")
				 {
					 chiNhanhId.focus(); 
					 error.value="Vui lòng chọn 'Chi nhánh'";
					return;
				 }
			}
		 if(lhh.value=="")
		 {
			 lhh.focus();
		 	error.value="Vui lòng chọn loại loại hàng hóa!";
			return;
		 }
		 
		 if(document.getElementById("loaihh").value != "2")
		 {
			 if(checkSanPham() ==false)
			 {	
				 error.value="Không có sản phẩm nào được chọn";
				return;
			 }
		 }
		 if(loaidoituong !="1")
			{
				 var nguoidenghi = document.getElementById("nguoidenghi");
				 if(nguoidenghi.value == "")
				 {
					 error.value="Vui lòng chọn người đề nghị thanh toán";
					 nguoidenghi.focus();
					 return;
				 }
			}
		 if (kiemTraTongTien() == false)
		 {	
			return;
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['dmhForm'].action.value='save';
	     document.forms['dmhForm'].submit();
	 }

	 function checkSanPham()
	 {
		 var masp = document.getElementsByName("masp");
		 for(var hh = 0; hh < masp.length; hh++)
		 {
			if(masp.item(hh).value != "")
			{
				return true;
			}
		 }
		 return false;
	 }
	 
	 function submitform()
	 { 		
		 document.forms['dmhForm'].action.value='submit';
	     document.forms["dmhForm"].submit();
	 }
	 
	 function changeNoiDung()
	 {
		 document.forms['dmhForm'].action.value='changeSP';
	     document.forms["dmhForm"].submit();
	 }
 
	 function backButton()
	 {
			history.back();
	 }
	 
	 function changeNoiDia()
	 {
		document.forms['dmhForm'].nccId.value= "";
		document.forms['dmhForm'].action.value='checkedNoiDia';
		document.forms["dmhForm"].submit();
		
	 } 
	 
	 
	 function excel_ThanhToanTamUng()
	 {
		document.forms['dmhForm'].action.value='excel_ThanhToanTamUng';
		document.forms["dmhForm"].submit();
		
	 } 
	 
 function tinhthue(){
	 var sumbvat=0;
	 var  sumvat=0;
	 var sumbvat=0;
	 
		var stt = document.getElementsByName("stt");
		
		var tienchuathue = document.getElementsByName("thanhtientruocthue");
		var tongtienthue = document.getElementsByName("tienthue");
		var tongcong = document.getElementsByName("thanhtiensauthue");
			
		
		for(i = 0; i < stt.length; i++){
			var tongtien = 0;
			var tongthue = 0;
			
			var th = "tienhang" + stt.item(i).value;		
			var tienhang = document.getElementsByName(th);
			
			var ts = "thuesuat" + stt.item(i).value;
			var thuesuat = document.getElementsByName(ts);
			
			var ts_goc = "thuesuat_goc" + stt.item(i).value;
			var thuesuat_goc = document.getElementsByName(ts_goc);
			
			var t = "thue" + stt.item(i).value;		
			var thue = document.getElementsByName(t);
			
			var c = "cong" + stt.item(i).value;
			var cong = document.getElementsByName(c);
			
			
			for(j = 0; j < tienhang.length; j++){
				
				var tienhang1=0;
				try{
					tienhang1=parseFloat(tienhang.item(j).value.replace(/\$|\,/g,''));
					tienhang.item(j).value = DinhDangTien(tienhang1);
				}catch(err){}
				
				 if(tienhang1 > 0 ){
					 
					var temp =0;
					if (thuesuat.item(j).value == '' || thuesuat.item(j).value == '0')
					{
						thuesuat_goc.item(j).value = 0;
						thuesuat.item(j).value = 0;
					}
					
					if(thuesuat_goc.item(j).value == '' || thuesuat_goc.item(j).value == '0' ||
					  ( Math.round(parseFloat(thuesuat_goc.item(j).value)) - parseFloat(thuesuat.item(j).value) != 0 ) ) 
						thuesuat_goc.item(j).value = thuesuat.item(j).value;
					
			 	  	try{			 		
							temp= Math.round(tienhang1 *parseFloat(thuesuat_goc.item(j).value.replace(/\$|\,/g,''))/100);
			 	  		
					}catch(err){}  
					   
			   		   thue.item(j).value = DinhDangTien(temp);
					   
				 
				  
					tongthue = tongthue + parseFloat(temp);
					cong.item(j).value =  DinhDangTien(parseFloat(tienhang.item(j).value.replace(/\$|\,/g,'')) + parseFloat(temp));
					tongtien = tongtien + parseFloat(cong.item(j).value.replace(/\$|\,/g,'')); 
											
				 }
				 
			}
			
			
// 			tienchuathue.item(i).value = DinhDangTien(tongtien - tongthue);
// 			tongtienthue.item(i).value = DinhDangTien(tongthue);
// 			tongcong.item(i).value = DinhDangTien(tongtien);

			if (tongtien - tongthue > 0)
			{
				tienchuathue.item(i).value = DinhDangTien(tongtien - tongthue);
				tongtienthue.item(i).value = DinhDangTien(tongthue);
				tongcong.item(i).value = DinhDangTien(tongtien);
			}
			else
			{
				tongthue = parseFloat(tongtienthue.item(i).value.replace(/\$|\,/g,''));
				tongtien = parseFloat(tongcong.item(i).value.replace(/\$|\,/g,''));
			}	
			   sumvat=sumvat+tongthue;
			   sumbvat=sumbvat+tongtien;
		}
		
		var sotienCantru = document.getElementById("sotiencantru").value.replace(/,/g,"");
		var CONLAI ;
		console.log('abc :' + document.getElementById("BVAT").value);
		console.log('vat :' + document.getElementById("VAT").value);
		
		document.getElementById("BVAT").value = DinhDangTien(Math.round(sumbvat-sumvat,0));
		document.getElementById("VAT").value = DinhDangDonGia(Math.round(sumvat * 1000,0 ) / 1000);
		
		
		document.getElementById("AVAT").value = DinhDangTien(Math.round(sumbvat * 1000,0 ) / 1000);
		
		if(sotienCantru != '') CONLAI = parseFloat( Math.round(sumbvat * 1000,0 ) / 1000 ) - parseFloat(sotienCantru) ;
		else CONLAI = Math.round(sumbvat * 1000,0 ) / 1000;		
		
		document.getElementById("CONLAI").value = DinhDangDonGia(CONLAI);
		
		
		}
  
 		function tinhthue1(){

 			 var sumbvat=0;
 			 var  sumvat=0;
 			 var sumbvat=0;
 			
 			 
 				var stt = document.getElementsByName("stt");
 				
 				var tienchuathue = document.getElementsByName("thanhtientruocthue");
 				var tongtienthue = document.getElementsByName("tienthue");
 				var tongcong = document.getElementsByName("thanhtiensauthue");
 					
 				for(i = 0; i < stt.length; i++){
 					var tongtien = 0;
 					var tongthue = 0;
 					
 					var th = "tienhang" + stt.item(i).value;		
 					var tienhang = document.getElementsByName(th);
 					
 					var ts = "thuesuat" + stt.item(i).value;
 					var thuesuat = document.getElementsByName(ts);
 					
 					var ts_goc = "thuesuat_goc" + stt.item(i).value;
 					var thuesuat_goc = document.getElementsByName(ts_goc);
 					
 					var t = "thue" + stt.item(i).value;		
 					var thue = document.getElementsByName(t);
 					
 					var c = "cong" + stt.item(i).value;
 					var cong = document.getElementsByName(c);
 					
 					for(j = 0; j < tienhang.length; j++){
 						
 						var tienhang1=0;
 						try{
 							tienhang1=parseFloat(tienhang.item(j).value.replace(/\$|\,/g,''));
 							
 						}catch(err){}
 						
 						 if(tienhang1 > 0 ){
 							 
 							var temp =0;
 					   
 						   try{
 							   
 						    temp =parseFloat (thue.item(j).value.replace(/\$|\,/g,''));
 						  }catch(err){ 
 							   
 						  }  
 						   
 						  	var phantramthue=temp *100/tienhang1;
 						 	 thuesuat.item(j).value=   DinhDangTien(Math.round(phantramthue,0));
 						 	 thuesuat_goc.item(j).value = phantramthue;
 							tongthue = tongthue + temp;
 							cong.item(j).value =  DinhDangTien(parseFloat(tienhang.item(j).value.replace(/\$|\,/g,'')) + temp);
 							tongtien = tongtien + parseFloat(cong.item(j).value.replace(/\$|\,/g,'')); 
 						 }
 						 
 					}
 					tienchuathue.item(i).value = DinhDangTien(tongtien - tongthue);
 					tongtienthue.item(i).value = DinhDangTien(tongthue);
 					tongcong.item(i).value = DinhDangTien(tongtien);
 	  
 					    sumvat=sumvat+tongthue;
 					   sumbvat=sumbvat+tongtien;
 	 
 				}
 				
 				var sotienCantru = document.getElementById("sotiencantru").value.replace(/,/g,"");
 				var CONLAI ;
 				
 				document.getElementById("BVAT").value = DinhDangTien(Math.round(sumbvat-sumvat,0));
 				document.getElementById("VAT").value = DinhDangDonGia(Math.round(sumvat * 1000,0 ) / 1000);
 				document.getElementById("AVAT").value = DinhDangDonGia( Math.round(sumbvat * 1000,0 ) / 1000 );
 				
 				if(sotienCantru != '') CONLAI = parseFloat( Math.round(sumbvat * 1000,0 ) / 1000 ) - parseFloat(sotienCantru) ;
 				else CONLAI = Math.round(sumbvat * 1000,0 ) / 1000;		
 				
 				document.getElementById("CONLAI").value = DinhDangDonGia(CONLAI);
 				
		}
	
		/* function loadTenNCC()
 		{
 			var stt = document.getElementsByName("stt");
 			var nccTen = document.getElementById("nccTen");
 			
 			for(i = 0; i < stt.length; i++){
				
 				var ms = "mausoHD" + stt.item(i).value;	
 				var ms_hd = document.getElementsByName(ms);
 				
				var tncc = "tenncc" + stt.item(i).value;		
				var tenncc = document.getElementsByName(tncc);
				

				for(j = 0; j < ms_hd.length; j++){
					if(ms_hd.item(j).value != '')
						tenncc.item(j).value = nccTen.value;
					else
						tenncc.item(j).value = '';
				}
 
			}
 			
 		} */
		
		function TinhtienCantru(n)
 		{
 			var trahet = document.getElementsByName("trahet");
 			var chon = document.getElementsByName("chon");
 			var sotienAvat = document.getElementsByName("sotienavat");
 			var cantru = document.getElementsByName("cantru");
 			var conlai = document.getElementsByName("conlai");
 			
 			var tongtienCT = '0' ;
 			
 			for(i = 0; i < chon.length; i++)
		 	{
 			  if(chon.item(i).checked)
 			  {

 	 				if(trahet.item(i).checked)
 	 				{
 	 				   if(n==100) // Chon tra het nhung thay doi so tien
 	 				   {
 	 					   var ct ;
 	 					   if(cantru.item(i).value != '') 
 	 					   {   
 	 						   ct = cantru.item(i).value.replace(/,/g,"");
 	 					   }
 	 					   else 
 	 					   {
 	 						   ct = '0'; 					   
 	 					   }
 	 					   
 	 					   if(parseFloat(sotienAvat.item(i).value.replace(/,/g,"")) - parseFloat(ct) > 0)
 	 					   {
 	 						   conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(sotienAvat.item(i).value.replace(/,/g,"")) - parseFloat(ct),0)) ;						   
 	 						   cantru.item(i).value = DinhDangTien(roundNumber(ct,0));
 	 						   
 	 						   trahet.item(i).checked = false;
 	 						   tongtienCT = parseFloat(tongtienCT) + parseFloat(cantru.item(i).value.replace(/,/g,""));
 	 					   }else
 	 					   {
 	 						   conlai.item(i).value = '0' ;
 							   cantru.item(i).value = DinhDangTien(roundNumber(sotienAvat.item(i).value.replace(/,/g,""),0) );
 							   
 							   trahet.item(i).checked = true;
 							   tongtienCT = parseFloat(tongtienCT) + parseFloat(cantru.item(i).value.replace(/,/g,""));
 	 					   }	   
 	 						   
 	 				   }else
 	 				   {
 	 					   var ct = sotienAvat.item(i).value.replace(/,/g,"") ;
 	 	 				   cantru.item(i).value = DinhDangTien(roundNumber(sotienAvat.item(i).value.replace(/,/g,""),0)) ;
 	 	 				   conlai.item(i).value = "0";
 	 	 				   trahet.item(i).checked = true; 
 	 	 				   
 	 	 				   tongtienCT = parseFloat(tongtienCT) + parseFloat(cantru.item(i).value.replace(/,/g,""));
 	 				   }
 	 				   				   
 	 				}else if(i==n) // Bỏ chọn trả hết
 	 				{   
 	 					conlai.item(i).value = DinhDangTien(roundNumber(sotienAvat.item(i).value.replace(/,/g,""),0));
 	 					cantru.item(i).value = '0';
 	 					trahet.item(i).checked = false; 
 	 					
 	 					tongtienCT = parseFloat(tongtienCT) + parseFloat(cantru.item(i).value.replace(/,/g,""));
 	 					
 	 				}else  // Thay đổi tiền cấn trừ
 	 				{
 	 					
 	 					if(cantru.item(i).value != '') 
 						{   
 						   ct = cantru.item(i).value.replace(/,/g,"");
 						}
 						 else 
 						{
 						   ct = '0'; 					   
 						}
 						   
 						   if(parseFloat(sotienAvat.item(i).value.replace(/,/g,"")) - parseFloat(ct) > 0)
 						   {
 							   conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(sotienAvat.item(i).value.replace(/,/g,"")) - parseFloat(ct) ,0) );
 							   cantru.item(i).value = DinhDangTien(roundNumber(ct,0));
 							   
 							   trahet.item(i).checked = false;
 							   tongtienCT = parseFloat(tongtienCT) + parseFloat(cantru.item(i).value.replace(/,/g,""));
 						   }else
 						   {
 							   conlai.item(i).value = '0' ;
 							   cantru.item(i).value = DinhDangTien(roundNumber(sotienAvat.item(i).value.replace(/,/g,""),0) );
 							   
 							   trahet.item(i).checked = true;
 							   tongtienCT = parseFloat(tongtienCT) + parseFloat(cantru.item(i).value.replace(/,/g,""));
 						   }	
 	 				}
		 	  }
 			  else // Bỏ chọn 
 			  {
 				
 				 trahet.item(i).checked = false; 
 				 conlai.item(i).value = DinhDangTien(roundNumber(sotienAvat.item(i).value.replace(/,/g,""),0));
				 cantru.item(i).value = '0';
									
				 tongtienCT = parseFloat(tongtienCT) + parseFloat(cantru.item(i).value.replace(/,/g,""));
 			  }
		 	}
 			
 			document.getElementById("sotiencantru").value = DinhDangTien(roundNumber(tongtienCT,0)); 						
 				
 			tinhAVAT();
 			
 		}
 		
		function tinhAVAT()
 		{
 			var AVAT = document.getElementById("AVAT").value.replace(/,/g,"");
 			
 			var sotienCT = document.getElementById("sotiencantru").value.replace(/,/g,"");
 			
 			if(sotienCT == '') sotienCT ="0";
 			
 			document.getElementById("CONLAI").value = DinhDangTien(Math.round( parseFloat(AVAT) - parseFloat(sotienCT) ));
 			
 		}
 	
	function sellectAll2(count)
	 {
		 var chkAll = document.getElementById("chkAll2_" + count);
		 var spIds = document.getElementsByName("checkSanPhamKho_" + count);
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }	
	
	function upperLetters(element)
	{
		element.value = element.value.toUpperCase();
	}
	
	function changePhongBan()
	{
		var dvthId = document.getElementById("dvthId").value;
		
		var xmlhttp;
		if (window.XMLHttpRequest)
		{
		   xmlhttp = new XMLHttpRequest();
		}
		else
		{
		   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.open("POST","../../AjaxChangePhongBan_DNTT_Svl?dvthId=" + dvthId, true);
		xmlhttp.send();
	}
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dmhForm" method="post" action="../../ErpPhieuThanhToanUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="nppId" value='<%=nppId%>'> 
		<input type="hidden" name="action" value="1" />
		<input type="hidden" name="soDongThem" id = "soDongThem" value='<%=soDongThem %>'>
		<input type="hidden" name="id" value="<%= dmhBean.getId() %>" />
		<input type="hidden" name="sodongPC" value="<%=dmhBean.getSodongPC()%>"> 
		<input type="hidden" name="duyetdn" value="<%=dmhBean.getDuyetdntt_backButton()%>"> 
		
		<div id="main" style="width: 100%; padding-left: 2px">
		<input type="hidden" name="chucnang" value='<%=dmhBean.getChucnang()%>'>
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">
				<%if(dmhBean.getChucnang().equals("ganmacp")){ %>
					Quản lý mua hàng &gt; Gán mã chi phí &gt; Hiển thị
				<%}else if (dmhBean.getChucnang().equals("duyetdntt")) { %>
					Quản lý công nợ &gt; Công nợ phải trả &gt; Duyệt đề nghị thanh toán &gt; Hiển thị
				<%}else{ %>
					Quản lý công nợ &gt; Công nợ phải trả &gt; Đề nghị thanh toán &gt; Hiển thị
				<%} %>
				</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="javascript:backButton();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px"
					longdesc="Quay ve" style="border-style: outset"></A> 
						
				<% if( dmhBean.getIsHoanTat().equals("1") || dmhBean.getIsDaChot().equals("1") || dmhBean.getIsKTT().equals("1") || dmhBean.getIsKTV().equals("1") || dmhBean.getIsTP().equals("1")){ %>
				<% if(dmhBean.getLoaidoituong().equals("1")){%>
				
					<%-- <A href="../../ErpPhieuThanhToanPdfSvl?userId=<%=userId %>&ptt=<%=dmhBean.getId()%> "><img src="../images/Printer30.png" alt="In" title="In phiếu thanh toán tạm ứng" longdesc="In de nghi thanh toan" border=1 style="border-style:outset"></A>		
				 --%>
				
					<A href="javascript:excel_ThanhToanTamUng()"><img src="../images/Excel.jpg" width="30px" height="30px"  alt="In" title="Xuất excel phiếu thanh toán tạm ứng" longdesc="Xuất excel phiếu thanh toán tạm ứng" border=1 style="border-style:outset"></A>		
				
				
				<%} %>	
				<%-- <A href="../../ErpPhieuThanhToanPdfSvl?userId=<%=userId %>&ptt=<%=dmhBean.getId()%> ">
				<img src="../images/Printer30.png" alt="In" title="In đề nghị thanh toán" longdesc="In de nghi thanh toan" border=1 style="border-style:outset"></A>
			 --%>
			 	<% if(dmhBean.getHtttId().equals("100004")){ %> 
						<A href="../../ErpInPhieuKeToanDNTTPdfSvl?userId=<%=userId %>&display=<%=dmhBean.getId()%> "><img src="../images/Printer30.png" alt="In" title="In phiếu kế toán" longdesc="In phiếu kế toán" border=1 style="border-style:outset"></A>				
					<%} %>
						<A href="../../ErpInGiayDeNghiThanhToanPdfSvl?userId=<%=userId %>&display=<%=dmhBean.getId()%> "><img src="../images/Printer30.png" alt="In" title="In giấy đề nghị thanh toán" longdesc="In giấy đề nghị thanh toán" border=1 style="border-style:outset"></A>
						<A href="../../ErpInGiayDeNghiThanhToanPdfSvl?userId=<%=userId %>&exel1=<%=dmhBean.getId()%> "><img src="../images/Excel.jpg"  width="30px" height="30px" alt="In" title="In phiếu nhập mua hàng,dịch vụ" longdesc="In phiếu nhập mua hàng,dịch vụ" border=1 style="border-style:outset"></A>
						<A href="../../ErpInGiayDeNghiThanhToanPdfSvl?userId=<%=userId %>&excel=<%=dmhBean.getId()%> "><img src="../images/Excel.jpg" width="30px" height="30px" alt="In mẫu Excel" title="In giấy đề nghị thanh toán" longdesc="In giấy đề nghị thanh toán" border=1 style="border-style:outset"></A>
						<A href="../../ErpInGiayDeNghiThanhToanPdfSvl?userId=<%=userId %>&display=<%=dmhBean.getId()%>&task=loai1"><img src="../images/Printer30.png" alt="In" title="In phiếu nhập mua hàng hóa, dịch vụ" longdesc="In phiếu nhập mua hàng hóa, dịch vụ" border=1 style="border-style:outset"></A>
				
				<%} %>
		  	</div>
		  	<div align="left" style="width: 100%; float: none; clear: left">		
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="Msg" rows="1" readonly="readonly" style="width: 100%"><%=dmhBean.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle">Đề nghị thanh toán</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Ngày thanh toán</TD>
								<TD   class="plainlabel" valign="top" >
									<input type="text" class="days" id="ngaymuahang" name="ngaymuahang"	value="<%= dmhBean.getNgaymuahang() %>" maxlength="10" style="border-radius:4px; height: 20px;" readonly />
								</TD>
								
								<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>
								
								<TD style="display: none" class="plainlabel">PO nội bộ</TD>
								<TD style="display: none" class="plainlabel">			
									<% if(dmhBean.getCheckedNoiBo().equals("1")) { %>
									<input type="checkbox" id="noibo" name="noibo" value="1" checked = "checked" >
									<% } else {  %>
										<input type="checkbox" id="noibo" name="noibo" value="1" >
									<% } %>
								</td>
							</TR>
							
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Số chứng từ </TD>
								<TD class="plainlabel" valign="top" colspan="5">
									<input type="text" id="soChungTu_Chu" name="soChungTu_Chu" value="<%= dmhBean.getSoChungTu_Chu() %>" maxlength="10" style="border-radius:4px; height: 20px; width: 100px;" readonly="readonly"/>
									<input type="text" id="soChungTu_So" name="soChungTu_So" value="<%= dmhBean.getSoChungTu_So() %>" maxlength="10" style="border-radius:4px; height: 20px; width: 100px;" readonly="readonly"/>
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel">Phòng ban</TD>
								<TD class="plainlabel" width="250px" colspan="3">
									<select name="dvthId" id="dvthId" style="width: 300px"  >
										<% if (dvthList != null)
										{
											while (dvthList.next())
											{
												if (dvthList.getString("pk_seq").equals(dmhBean.getDvthId()))
												{
												%>
													<option value="<%=dvthList.getString("pk_seq")%>" selected="selected"><%=dvthList.getString("ten")%></option>
												<% } else { %>
													<option value="<%=dvthList.getString("pk_seq")%>"><%=dvthList.getString("ten")%></option>
										<% } } dvthList.close(); } %>
										<option value=""></option>
									</select>
								</TD>	
								
								
								<TD class="plainlabel" style="display: none" width="130px">Nguồn gốc</TD>
								<TD class="plainlabel" style="display: none" >
									<select name="nguongoc" id="nguongoc" onChange="submitform();" style="width: 200px">
										<% if (dmhBean.getNguonGocHH().equals("TN")) { %>
											<option value="TN" selected="selected">Nội địa</option>
											<option value="NN">Nhập khẩu</option>
										<% } else if (dmhBean.getNguonGocHH().equals("NN")) { %>
											<option value="TN">Nội địa</option>
											<option value="NN" selected="selected">Nhập khẩu</option>
										<% } else { %>
											<option value="TN">Nội địa</option>
											<option value="NN">Nhập khẩu</option>
										<% } %>
										<option value=""></option>
									</select>
									
									
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span <%= ( dmhBean.getNguonGocHH().equals("NN") ? " " : " style='display: none;'  " ) %> >
									Chi phí khác <input type="text" name="cpKhac" id="cpKhac" readonly="readonly" style="text-align: right;"   ></span>
									
									<a href="" id="chiphiKHAC" rel="cpKHAC" <%= ( dmhBean.getNguonGocHH().equals("NN") ? " " : " style='display: none;'  " ) %> >
	           	 							&nbsp; <img alt="Ghi chú" src="../images/vitriluu.png"></a>
	           	 		
                          			<DIV id="cpKHAC" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 350px; min-height:150px; overflow:auto; padding: 4px;">
				                    	<table width="100%" align="center">
				                        <tr>
				                            <th width="230px" style="text-align: center;" >Diễn giải</th>
				                            <th width="100px" style="text-align: center;" >Số tiền</th>
				                        </tr>
				                        
				                        <% int count2 = 0; 
				                        	if(cpkDienGiai != null) { 
				                        	for(int i = 0; i < cpkDienGiai.length; i++)
				                        	{ %>
				                        		
				                        	 <tr>
					                        	<td><input type="text" style="width: 100%" value="<%= cpkDienGiai[i] %>"  name="diengiaiCPK" ></td>
					                        	<td><input type="text" style="width: 100%; text-align: right;" value="<%= cpkSotien[i] %>"  name="sotienCPK"  ></td>
					                        </tr>
				                        	
				                        <%  count2++; } } %>
				                        
				                        <% for(int i = count2; i < 4; i++ ) { %>
				                        	<tr>
					                        	<td><input type="text" style="width: 100%" name="diengiaiCPK" ></td>
					                        	<td><input type="text" style="width: 100%; text-align: right;" name="sotienCPK"  ></td>
					                        </tr>
				                        <% } %>
				                        
				                    	</table>
					                     <div align="right">
					                     	<label style="color:red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('cpKHAC')" >Đóng lại</a>
					                     </div>
				            		</DIV>   

								</TD>
							</TR>
							<tr>
							<TD class="plainlabel" valign="middle" >Loại đối tượng </TD>   
		                       <TD class="plainlabel" valign="middle" colspan="3"  >
		                        	<SELECT  name = "loaidoituong"  style="width: 300px" >
		                        		<OPTION VALUE = "" SELECTED ></OPTION>
		                        	<%for (Erp_Item item : loaiDoiTuongList)
		                        		if(dmhBean.getLoaidoituong().equals(item.getValue())){ %>
		                      			<OPTION VALUE = "<%=item.getValue() %>" SELECTED ><%=item.getName() %></OPTION>
		                      		<%}else { %>
		                      			<OPTION VALUE = "<%=item.getValue() %>" ><%=item.getName() %></OPTION>
		                      		<%} %>		                      		
		                        	</SELECT> 
		                        </TD>         
							</tr>
							<% if(dmhBean.getLoaidoituong().equals("0")){ %>
							
								<TR>
									<TD class="plainlabel">Nhà cung cấp</TD>
									<TD class="plainlabel">
										<input type="hidden" id="nccId" name ="nccId" value="<%=dmhBean.getNCC() %>" />
										<input type="text" id="tenNCC" name ="tenNCC" value="<%=dmhBean.getNccTen() %>" style=" width: 500px;"  readonly="readonly" />
										
<!-- 										<select name="nccId" id="nccId" style="width: 500px"  onChange = "changeNCC();"> -->
<!-- 											<option value=""></option> -->
<%-- 											<% if (nhacungcapRs != null) --%>
<!-- 											{ -->
<!-- 												while (nhacungcapRs.next()) -->
<!-- 												{ -->
<!-- 													if (nhacungcapRs.getString("pk_seq").equals(dmhBean.getNCC())) -->
<!-- 													{ -->
<!-- 													%> -->
<%-- 														<option value="<%=nhacungcapRs.getString("pk_seq")%>" selected="selected"><%=nhacungcapRs.getString("ten")%></option> --%>
<%-- 													<% } else { %> --%>
<%-- 														<option value="<%=nhacungcapRs.getString("pk_seq")%>"><%=nhacungcapRs.getString("ten")%></option> --%>
<%-- 											<% } } nhacungcapRs.close(); } %> --%>
											
<!-- 										</select> -->
											<script type="text/javascript">
												
													
													jQuery(function()
													{		
														$("#tenNCC").autocomplete("ErpDoiTuongList.jsp");
													});	
												
											</script>
									
									</TD>
									<TD class="plainlabel"></TD>
									<TD class="plainlabel">
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel"> Địa chỉ NCC </TD>
									<TD class="plainlabel" colspan="3"><input type="text" name="diachincc" id="diachincc" value="<%= dmhBean.getDiachiNCC()%>" style=" width: 500px;"> 
									</TD>
								</TR>
								
							<%}else if(dmhBean.getLoaidoituong().equals("1")){ %>
								<TR>
								<TD class="plainlabel">Mã nhân viên</TD>
								<TD class="plainlabel" width = "20%">	
									<%-- <select name="nvId" id="nvId" style="width: 500px" onChange = "submitform();" >
										<% if (nvRs != null)
										{
											while (nvRs.next())
											{
												if (nvRs.getString("pk_seq").equals(dmhBean.getNvId()))
												{
												%>
													<option value="<%=nvRs.getString("pk_seq")%>" selected="selected"><%=nvRs.getString("ten")%></option>
												<% } else { %>
													<option value="<%=nvRs.getString("pk_seq")%>"><%=nvRs.getString("ten")%></option>
										<% } } nvRs.close(); } %>
										
									</select>		 --%>	
									
									<input type="hidden" id="nvId" name ="nvId" value="<%=dmhBean.getNvId() %>" />
									<input type="text" id="nvTen" name ="nvTen" value="<%=dmhBean.getNvTen() %>" style=" width: 500px;"  readonly="readonly" />
										
									<script type="text/javascript">
										
											
											jQuery(function()
											{		
												$("#nvTen").autocomplete("ErpDoiTuongList.jsp");
											});	
										
									</script>																
											
								</TD>
								<TD class="plainlabel" width = "10%"></TD>
								<TD class="plainlabel">
								</TD>
		                  </TR> 
							<%}else if(dmhBean.getLoaidoituong().equals("2")){ %>
								<TR>
								<TD class="plainlabel">Mã khách hàng</TD>
								<TD class="plainlabel" width = "20%">	
									<%-- <select name="khId" id="khId" style="width: 300px; border-radius:4px; height: 20px;" onChange = "submitform();" >
										<% if (khRs != null)
										{
											while (khRs.next())
											{
												if (khRs.getString("pk_seq").equals(dmhBean.getKhId()))
												{
												%>
													<option value="<%=khRs.getString("pk_seq")%>" selected="selected"><%=khRs.getString("ten")%></option>
												<% } else { %>
													<option value="<%=khRs.getString("pk_seq")%>"><%=khRs.getString("ten")%></option>
										<% } } khRs.close(); } %>
										
									</select>	 --%>	
									<input type="hidden" id="khId" name ="khId" value="<%=dmhBean.getKhId() %>" />
									<input type="text" id="khTen" name ="khTen" value="<%=dmhBean.getKhTen() %>" style=" width: 500px;"  readonly="readonly" />
										
									<script type="text/javascript">
										
											
											jQuery(function()
											{		
												$("#khTen").autocomplete("ErpDoiTuongList.jsp");
											});	
										
									</script>																
											
								</TD>
								<TD class="plainlabel" width = "10%"></TD>
								<TD class="plainlabel">
								</TD>
		                  </TR> 
							<%}else if(dmhBean.getLoaidoituong().equals("3")){ %>
								<TR>
								<TD class="plainlabel">Chi nhánh</TD>
								<TD class="plainlabel" width = "20%">	
									<%-- <select name="chiNhanhId" id="chiNhanhId" style="width: 200px; border-radius:4px; height: 20px;" onChange = "submitform();" >
										<% if (chiNhanhList != null)
										{
											for (Erp_Item item : chiNhanhList)
											{
												if (item.getValue().equals(dmhBean.getChiNhanhId()))
												{
												%>
													<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
												<% } else { %>
													<option value="<%=item.getValue()%>"><%=item.getName()%></option>
										<% } } } %>
										
									</select>	 --%>
									
									<input type="hidden" id="chiNhanhId" name ="chiNhanhId" value="<%=dmhBean.getChiNhanhId() %>" />
									<input type="text" id="chiNhanhTen" name ="chiNhanhTen" value="<%=dmhBean.getChiNhanhTen() %>" style=" width: 500px;"  readonly="readonly" />
										
									<script type="text/javascript">
										
											
											jQuery(function()
											{		
												$("#chiNhanhTen").autocomplete("ErpDoiTuongList.jsp");
											});	
										
									</script>															
											
								</TD>
								<TD class="plainlabel" width = "10%"></TD>
								<TD class="plainlabel">
								</TD>
		                  </TR> 
							<%}else if(dmhBean.getLoaidoituong().equals("4")){ %>
							<TR>
								<TD class="plainlabel">Đối tượng khác</TD>
								<TD class="plainlabel" id="dtk" colspan="<%=dmhBean.getHtttId().trim().equals("100004") ? 1 : 3%>">	
									<%-- <select name="doiTuongKhacId" id="doiTuongKhacId" style="width: 200px; border-radius:4px; height: 20px;" onChange = "submitform();" >
										<% if (doiTuongKhacList != null)
										{
											for (Erp_Item item : doiTuongKhacList)
											{
												if (item.getValue().equals(dmhBean.getDoiTuongKhacId()))
												{
												%>
													<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
												<% } else { %>
													<option value="<%=item.getValue()%>"><%=item.getName()%></option>
										<% } } } %>
										
									</select>	 --%>
									
									<input type="hidden" id="doiTuongKhacId" name ="doiTuongKhacId" value="<%=dmhBean.getDoiTuongKhacId() %>" />
									<input type="text" id="doiTuongKhacTen" name ="doiTuongKhacTen" value="<%=dmhBean.getDoiTuongKhacTen() %>" style=" width: 500px;" readonly="readonly" />
										
									<script type="text/javascript">
										
											
											jQuery(function()
											{		
												$("#doiTuongKhacTen").autocomplete("ErpDoiTuongList.jsp");
											});	
										
									</script>															
											
								</TD>
								<%if (dmhBean.getHtttId().trim().equals("100004")){ %>
								<TD class="plainlabel" id="tkk1">Tài khoản đối tượng khác</TD>
								<TD class="plainlabel" id="tkk2">	
									<input type="text" name = "tenTaiKhoanDoiTuongKhac" id = "tenTaiKhoanDoiTuongKhac" value='<%= dmhBean.getTenTaiKhoanDoiTuongKhac()%>' style="width:200px;"/>
                       				<input type="hidden" name = "taiKhoanDoiTuongKhacId" id = "taiKhoanDoiTuongKhacId" value='<%= dmhBean.getTaiKhoanDoiTuongKhacId()%>'/>	
								</TD>
								<%}else{ %>
								<TD class="plainlabel" style="display: none" id="tkk1">Tài khoản đối tượng khác</TD>
								<TD class="plainlabel" style="display: none" id="tkk2">	
									<input type="text" name = "tenTaiKhoanDoiTuongKhac" id = "tenTaiKhoanDoiTuongKhac" value='' style="width:200px;"/>
                       				<input type="hidden" name = "taiKhoanDoiTuongKhacId" id = "taiKhoanDoiTuongKhacId"  value=''/>	
								</TD>
								<%} %>
		                  </TR> 
		                  
							<%} %>
							<%if(!dmhBean.getLoaidoituong().equals("1")){ %>
							<TR class="plainlabel">							
								<TD>Người đề nghị thanh toán</TD>
								<TD>
									<%-- <Select name="nguoidenghi" id="nguoidenghi" style="width: 400px">
									<option value=""> </option>
										<% while(rsNguoidenghi.next()){
													if (dmhBean.getNguoidenghithanhtoan() != null && dmhBean.getNguoidenghithanhtoan().equals(rsNguoidenghi.getString("PK_SEQ")))
													{  %>
														<option value='<%= rsNguoidenghi.getString("PK_SEQ")%>' selected="selected"><%= rsNguoidenghi.getString("Ten") %></option>
													<% } else { %>
														<option value='<%= rsNguoidenghi.getString("PK_SEQ")%>'><%= rsNguoidenghi.getString("Ten") %></option>
										<% } }  %>
									</Select> --%>
									<input TYPE="HIDDEN" name="nguoidenghi" id="nguoidenghi" style="width: 400px" value="<%=(dmhBean.getNguoidenghithanhtoan() != null) ? dmhBean.getNguoidenghithanhtoan() : ""%>" >
									<input TYPE="text" name="nguoidenghiTen" id="nguoidenghiTen" style="width: 400px" value="<%=(dmhBean.getNguoidenghithanhtoan() != null) ? dmhBean.getNguoidenghithanhtoanTen() : ""%>" readonly="readonly>
									
								</TD>
							</TR>
							<%} %>
							<TR class="plainlabel">
							
								<%-- <TD class="plainlabel">Ghi nhận công nợ</TD>
								<TD class="plainlabel">
								<% if(dmhBean.getQuanlycongno().equals("1")) { %>
									<input type="checkbox" name="qlcongno" value="1" checked = "checked">
								<% } else {  %>
									<input type="checkbox" name="qlcongno" value="1" >
								<% } %>
								</td> --%>
							
								<TD>Tiền tệ</TD>
								<TD colspan="3">
									<Select name="tiente_fk" id="tiente_fk"  style="width: 200px">
										<% if (ttList.size() > 0)
											{
												int size = ttList.size();
												for (int i = 0; i < size; i++)
												{
													ITiente t = (Tiente) ttList.get(i);
													if (dmhBean.getTienTe_FK() != null && dmhBean.getTienTe_FK().equals(t.getId()))
													{  %>
														<option value='<%= t.getId() + " - " + t.getTygiaquydoi()%>' selected="selected"><%= t.getMa() %></option>
													<% } else { %>
														<option value='<%= t.getId() + " - " + t.getTygiaquydoi()%>'><%= t.getMa() %></option>
										<% } } } %>
									</Select>
								</TD>
							</TR>
							
							<TR  class="plainlabel">
								<TD class="plainlabel">Loại hàng hóa</TD>
								<TD class="plainlabel" > 
										<select name="loaihh" id="loaihh"  style="width: 200px">		
										<%if(dmhBean.getLhhId().equals("2")) {%>
											<option value="2"  selected="selected">Chi phí dịch vụ</option>
											<option value="4"  >Chi phí dịch vụ đặc biệt</option>
										<%}else if(dmhBean.getLhhId().equals("4")){ %>
									
										<option value="4"  selected="selected">Chi phí dịch vụ đặc biệt</option>
										<option value="2"  >Chi phí dịch vụ</option>
										<%}else{ %>
											<option value="2"  selected="selected">Chi phí dịch vụ</option>
											<option value="4"  >Chi phí dịch vụ đặc biệt</option>
										<%} %>
										</select>
									 
								</TD>
								
								<TD class="plainlabel">Hình thức thanh toán</TD>
								<TD class="plainlabel">
									<select name="htttId" id="htttId" style ="width:200px">
										<option value=""> </option>
										<%
											if(htttRs != null)
											{
												try
												{
													while(htttRs.next())
													{  									 
													if( htttRs.getString("pk_seq").equals(dmhBean.getHtttId())){ %>
													<option value="<%= htttRs.getString("pk_seq") %>" selected="selected" ><%= htttRs.getString("pk_seq") %> - <%= htttRs.getString("ten") %></option>
													<%		
													}else { %>
													<option value="<%= htttRs.getString("pk_seq") %>" ><%= htttRs.getString("pk_seq") %> - <%= htttRs.getString("ten") %> </option>
													<% 		} 
													} 
													htttRs.close();
												}catch(java.sql.SQLException ex){ ex.printStackTrace(); }
											}
									   %>
									</select>
								</TD>	
							
							</TR>
							<%if(dmhBean.getLhhId().equals("4")){ %>
							<TR  class="plainlabel">
								
								<TD class="plainlabel">Khoản mục chi</TD>
								<TD class="plainlabel" colspan="3">  
									<select name="khoanMucChi" id="khoanMucChiId" style ="width:200px" >
										<option value=""> </option>
										<% ResultSet rskhoanMucChi = dmhBean.getRsKhoanChiPhi();%>
											<%if(rskhoanMucChi != null)
											{
												try
												{
													while(rskhoanMucChi.next())
													{  									 
													if( rskhoanMucChi.getString("PK_SEQ").equals(dmhBean.getKhoanChiPhiId())){ %>
													<option value="<%= rskhoanMucChi.getString("PK_SEQ") %>" selected="selected" ><%= rskhoanMucChi.getString("DIENGIAI") %></option>
													<%		
													}else { %>
													<option value="<%= rskhoanMucChi.getString("PK_SEQ") %>" ><%= rskhoanMucChi.getString("DIENGIAI") %> </option>
													<% 		} 
													} 
													rskhoanMucChi.close();
												}catch(java.sql.SQLException ex){ ex.printStackTrace(); }
											}
									   %>
									</select>
								</TD>	
							
							</TR>
								<%} %>
							<TR>
								<TD class="plainlabel"> Diễn giải chứng từ</TD>
								<TD class="plainlabel" colspan="3"><input type="text" name="lydott" id="lydott" value="<%= dmhBean.getLydoTT()%>" style=" width: 500px; border-radius:4px; height: 20px;"> 
								</TD>
							</TR>

							<TR>
								<TD class="plainlabel">Tổng tiền chưa VAT</TD>
								<TD class="plainlabel"><input type="text" name="BVAT" id="BVAT" value="<%=dmhBean.getTongtienchuaVat()%>" style="text-align: right; border-radius:4px; height: 20px;"
									readonly="readonly"> </TD>
								<TD class="plainlabel">VAT</TD>
								<TD class="plainlabel"><input type="text" name="VAT" id="VAT" value="<%=dmhBean.getVat()%>" style="text-align: right; border-radius:4px; height: 20px;"
									onkeypress="return keypress(event);"> </TD>
							</TR>
							<TR>
								<TD class="plainlabel">Tổng tiền sau VAT</TD>
								<TD class="plainlabel">
									<input type="text" name="AVAT" id="AVAT" value="<%=dmhBean.getTongtiensauVat()%>" style="text-align: right; border-radius:4px; height: 20px;" readonly="readonly"> 
								</TD>
								
<!-- 								<TD  style="display: none" class="plainlabel">Dung sai (+/-)</TD> -->
<%-- 								<TD  style="display: none" class="plainlabel"><input type="text" name="dungsai" id="dungsai" value="<%=dmhBean.getDungsai()%>" style="text-align: right; border-radius:4px; height: 20px;" --%>
<!-- 									onkeypress="return keypress(event);"> % -->
<!-- 								</TD> -->
									
								<TD class="plainlabel">Cấn trừ tạm ứng</TD>
								<TD class="plainlabel">
									<input type="text" name="sotiencantru" id="sotiencantru" value="<%=dmhBean.getTongtienCantru()%>" style="text-align: right; border-radius:4px; height: 20px;" readonly="readonly"> 
									<A href="" id="pclist" rel="subcontentPC">&nbsp; 
											     <img alt="Chi tạm ứng" src="../images/vitriluu.png">
										</A>
											 <DIV id="subcontentPC" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 720px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="100%" align="center">
							                        <tr>
							                            <th width="100px">Ngày</th>
							                            <th width="100px">Số chứng từ</th>
							                            <th width="200px">Nội dung tạm ứng</th>
							                            <th width="150px">Số tiền</th>
							                            <th width="150px">Cấn trừ</th>	
							                            <th width="150px">Còn lại</th>
							                            <th width="50px">Chọn</th>	
							                            <th width="50px">Trả hết</th>										                       
							                        </tr>
	                        
						                            <% 	
						                            int j;						                            
						            	            for(j = 0; j < phieuchiList.size(); j++)
						            	            {                   			
								                      IPhieuchitamung pcl = phieuchiList.get(j);						                     
								                      %>
					                        			<tr>
					                        				<td>
					                        					<input type="text" style="width: 100%" readonly="readonly" name="ngaychungtu" value="<%= pcl.getNgaychungtu() %>" />
					                        					<input type="hidden" style="width: 100%" readonly="readonly" name="tthdid" value="<%= pcl.getSochungtu() %>" />
					                        				</td>
								                            <td>											                            	
								                            	<input type="text" style="width: 100%" readonly="readonly" name="sochungtu" value="<%= pcl.getSochungtu() %>" />
								                            </td>
								                            <td>
								                            	<input type="text" style="width: 100%" readonly="readonly" name="noidungtt" value="<%= pcl.getNoidungtt() %>" style="text-align: left" /></td>
								                            <td>
								                            	<input type="text"  style="width: 100%" name="sotienavat" readonly="readonly" value= "<%= pcl.getSotienAvat()  %>" />
								                            <td>
								                            	<input type="text"  style="width: 100%" name="cantru" value="<%= pcl.getSotienCantru()  %>" onchange="TinhtienCantru(100);" />
								                            </td>
								                            <td>
								                            	<input type="text"  style="width: 100%" name="conlai" value="<%= pcl.getConlai()  %>" readonly="readonly" />
								                            </td>
								                            <td>
								                                <% 	if(pcl.getChon().equals("1")){ %>
								                            	<input type="checkbox"  style="width: 100%; text-align: center" name="chon" id="chon<%= j %>" checked="checked"  value="<%= pcl.getChon()  %>" onchange="TinhtienCantru(<%= j %>)" />
								                            	<%}else{ %>
								                            	<input type="checkbox"  style="width: 100%; text-align: center" name="chon" id="chon<%= j %>" value="<%= pcl.getChon()  %>" onchange="TinhtienCantru(<%= j %>)" />
								                            	<%} %>
								                            </td>
								                            <td>
								                            	<% 	if(pcl.getConlai().equals("0")){ %>
								           	 					<input type="checkbox" style="width: 100%; text-align: center" value="<%= pcl.getSochungtu() %>" name = "trahet" id="trahet<%= j %>" checked="checked" onchange="TinhtienCantru(<%= j %>)" >
								           	 				<%} else { %>
								           	 					<input type="checkbox" style="width: 100%; text-align: center"  value="<%= pcl.getSochungtu() %>" name = "trahet" id="trahet<%= j %>" onchange="TinhtienCantru(<%= j %>)" >
								           	 				<%} %>
								                            </td>
								                        </tr>
									               <% } %>
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentPC')">Hoàn tất</a>
								                     </div>
	                							</DIV>
								</TD>
									
							</TR>
							
							<TR>
								<TD class="plainlabel">Tổng tiền còn lại</TD>
								<TD class="plainlabel"><input type="text" name="CONLAI" id="CONLAI" value="<%=dmhBean.getTongtienconlai()%>" style="text-align: right; border-radius:4px; height: 20px;"
									readonly="readonly"> 
								</TD>
								
								<TD class="plainlabel">Lãnh đạo ký duyệt</TD>
								<TD class="plainlabel">
								<% if(dmhBean.getLanhDaoKyDuyet() == 1) { %>
									<input type="checkbox" name="lanhDaoKyDuyet" id="lanhDaoKyDuyet" value="1" checked = "checked">
								<% } else {  %>
									<input type="checkbox" name="lanhDaoKyDuyet" id="lanhDaoKyDuyet" value="1" >
								<% } %>
								</td>
							</TR>
							<TR>
							<TD class="plainlabel">Chi phí ngoài khoán</TD>
								<TD class="plainlabel" colspan="3">
								<% if(dmhBean.getNgoaiKhoan().equals("1")) { %>
									<input type="checkbox" name="ngoaiKhoan" id="ngoaiKhoan" value="1" checked = "checked">
								<% } else {  %>
									<input type="checkbox" name="ngoaiKhoan" id="ngoaiKhoan" value="1" >
								<% } %>
								</td>
							</TR>
							
							<TR style="display: none">
								<TD class="plainlabel">Tổng số lượng</TD>
								<TD class="plainlabel" ><input type="text" name="tongluong" id="tongluong" value="" style="text-align: right; border-radius:4px; height: 20px;"
									readonly="readonly"> </TD>
								<TD class="plainlabel">Hình thức thanh toán</TD>
								<TD class="plainlabel" >
									<input type="text" name="hinhthucthanhtoan" id="hinhthucthanhtoan" value="<%= dmhBean.getHinhThucTT() %>" style="text-align: left; border-radius:4px; height: 20px;">
									 Địa điểm giao hàng 
									<% String ddgh = ""; try { ddgh = dmhBean.getDiaDiemGiaoHang(); } catch(Exception e) { } %>
									<input type="text" name="diadiemgiaohang" id="diadiemgiaohang" value="<%= ddgh %>" style="text-align: left; border-radius:4px; height: 20px;" placeholder="(tùy chọn)">
								</TD>
							</TR>
							
							<TR style="display: none">
								<TD class="plainlabel">Ghi chú</TD>
								<TD class="plainlabel" >
								
									<a href="" id="noidungGhiChu" rel="ndGhiChu">
	           	 							&nbsp; <img alt="Ghi chú" src="../images/vitriluu.png"></a>
	           	 		
                          			<DIV id="ndGhiChu" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 350px; min-height:150px; overflow:auto; padding: 4px;">
				                    	<table width="100%" align="center">
				                        <tr>
				                            <th width="300px">Ghi chú</th>
				                        </tr>
		                            	
		                            	<%
		                            		String[] ghichuArr = dmhBean.getGhiChuArr();
		                            		int sodong = 0;
		                            		if(ghichuArr != null)
		                            		{
		                            			for(int i = 0; i < ghichuArr.length; i++)
		                            			{
		                            				%>
		                            				<Tr>
					                            		<td><input type="text" name="ghichu" value="<%= ghichuArr[i] %>" style="width: 100%" /></td>
					                            	</Tr>
		                            			<% sodong++; }
		                            		}
		                            		
		                            		while(sodong < 5)
		                            		{
		                            			%>
		                            			
		                            			<Tr>
				                            		<td><input type="text" name="ghichu" value="" style="width: 100%" /></td>
				                            	</Tr>
		                            			
		                            		<% sodong++; }
		                            	%>
		                            	
				                    	</table>
					                     <div align="left">
					                     	<label style="color:red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('ndGhiChu')" >Hoàn tất</a>
					                     </div>
				            		</DIV>   
									
								</TD>
								<TD class="plainlabel">Số tham chiếu</TD>
								<TD class="plainlabel" ><input type="text" name="sothamchieu" id="sothamchieu" value="<%= dmhBean.getSoThamChieu() %>" style="text-align: left;"></TD>
							</TR>
							<TR style="<%= dmhBean.getNguonGocHH().equals("NN") ? "" : "display: none;" %>" >
								<TD class="plainlabel" valign="top" width="150px">ETD</TD>
								<TD class="plainlabel" valign="top">
									<input type="text" class="days" id="ETD" name="ETD"
											value="<%= dmhBean.getETD() %>" maxlength="10" readonly />
								</TD>
								<TD class="plainlabel" valign="top" width="150px">ETA</TD>
								<TD class="plainlabel" valign="top">
									<input type="text" class="days" id="ETA" name="ETA" value="<%= dmhBean.getETA() %>" maxlength="10" readonly />
								</TD>
							</TR>
						</TABLE>
						<hr>
					</div>
					
					<%  if(dmhBean.getLoaihanghoa().trim().length() > 0 ) { %>
					
					<div align="left" style="width: 100%; float: none; clear: none;">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1">
							<TR class="tbheader">
								<th align="center" width="4%">Số TT</th>
<%-- 							<%if(! ( dmhBean.getChucnang().equals("10000") || dmhBean.getChucnang().equals("9999")) ){ %> --%>
								<% if(dmhBean.getKhoanChiPhiId().equals("TCKT-24110000") ||dmhBean.getKhoanChiPhiId().equals("TCKT-24120000") || dmhBean.getKhoanChiPhiId().equals("TCKT-24130000") ){ %>
									<th align="center" width="20%">Mã sửa chữa lớn</th>
								<%}else if(dmhBean.getKhoanChiPhiId().equals("TCKT-24210000") ||dmhBean.getKhoanChiPhiId().equals("TCKT-24220000") ){ %>
									<th align="center" width="20%">Danh mục chi phí</th>
									<%}else{ %>
									<th align="center" width="20%">Mã chi phí</th>
									<%} %>
<%-- 							<%}else{ %> --%>
<!-- 							<th style="display: none;" align="center" width="20%">Mã chi phí</th> -->
<%-- 							<%} %> --%>
							<th align="center" width="40%">Diễn giải chi phí</th>											
							<th align="center" width="10%">Tổng tiền chưa thuế</th>
							<th align="center" width="10%">Thuế</th>
							<th align="center" width="10%">Cộng</th>
							<th align="center" width="3%">Hóa đơn</th>
							<th align="center" width="3%">Sản phẩm</th>
							</TR>
							
							<% int count = 0; 
								if(spList.size() > 0) { 
									for(int i = 0; i < spList.size(); i++) { 
										ISanpham sp = spList.get(i);
										%>
										
										<tr>
											<TD align="center" width="2%">
												<input type="hidden" name="stt" value = "<%= i %>" >
												<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
												<input type="hidden" value="<%= sp.getPK_SEQ() %>" name="idsp" style="width: 100%" readonly="readonly" > 
												<input type="hidden" value="<%= sp.getMNLId() %>" name="mnlId" style="width: 100%" readonly="readonly" > 
											</TD>
											<!-- LOẠI CẤP 10004 ĐƯỢC GẮN MÃ CHI PHÍ  -->
								 			<%if(! ( dmhBean.getChucnang().equals("10000") || dmhBean.getChucnang().equals("9999")) ){ %>
											<TD align="center" width="8%" >
												<input type="text" name="masp" style="width: 100%; border-radius:4px; height: 20px;" value="<%= sp.getMasanpham() %>"  readonly >  
											</TD>
											<%}else{ %>
											<TD  align="center" width="8%" >
												<input type="text" name="masp" style="width: 100%; border-radius:4px; height: 20px;" value="<%= sp.getMasanpham() %>"  readonly > 
											</TD>
											<%} %>
							  				<td>
													<input type="text" value="<%= sp.getTensanpham() %>" name="tensp" style="width: 100%; border-radius:4px; height: 20px;" readonly > 
											</TD>
											 
											<TD align="center" width="7%">
												<input type="text" value="<%= sp.getThanhTienTruocThue() %>" name="thanhtientruocthue" id="thanhtientruocthue_<%=i %>" style="width: 100%; text-align: right; border-radius:4px; height: 20px;" onchange="tinhTienTong(<%=i%>);"  onkeypress="return keypress(event);" > 
											</TD>
											<TD align="center" width="8%">
												<input type="text" value="<%= sp.getTienThue() %>" name="tienthue" id="tienthue_<%=i %>" style="width: 100%; text-align: right; border-radius:4px; height: 20px;"  onkeypress="return keypress(event);"  onchange="tinhTienTong(<%=i%>);"> 
											</TD>
											<TD align="center" width="8%">
												<input type="text" value="<%= sp.getThanhtien() %>"  name="thanhtiensauthue" style="width: 100%; text-align: right; border-radius:4px; height: 20px;"  readonly="readonly"> 
											</TD>

									 
<!-- 									 		<TD align="center" width="8%"> -->
<%-- 												<input type="text" value="<%= sp.getTienThanhToan() %>"  name="tienThanhToanSanPham" id="tienThanhToanSanPham_<%=i %>" style="width: 100%; text-align: right; border-radius:4px; height: 20px;">  --%>
<!-- 											</TD> -->
										
											<td align="right">
				           	 					<a href="" id="tenhd_<%= count %>" rel="subcontent<%= count %>">
					           	 				       <img alt="Tên sản phẩm hóa đơn" src="../images/vitriluu.png"></a>
					           	 				<DIV  id="subcontent<%= count %>"  style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
								                             background-color: white; width: 1050px; padding: 4px;  max-height: 200px; overflow: scroll;left: 90px" >
								                              
								                     <table width="100%" align="center" >
						        						<tr class="tbheader">
						        						<th width="25px" >Mẫu hóa đơn</th>
						        							<th width="15px" >Ký hiệu</th>
						         							<th width="25px">Số HĐ</th>
						         							<th width="10px">Ngày HĐ </th>
						         							<th width="83px">Tên NCC</th>
						         							<th width="83px">Địa chỉ NCC</th>
						         							<th width="50px">MST </th>		       							
						         							<th width="50px">Tiền hàng </th>
						         							<th width="5px">Thuế suất </th>
						         							<th width="36px">Tiền thuế </th>
						         							<th width="55px">Cộng </th>
						         							<th width="20px">Ghi chú </th>
						        						</tr>
											      <% 
											      	int m;
											        int dem = 0;
											      NumberFormat formatter = new DecimalFormat("#,###,###");
											      List<IHoadon> HdList = sp.getHoadonList();
											      		
											      		for(m = 0; m < HdList.size(); m++){ 
											      			IHoadon hd=	HdList.get(m);	%>
												      		<tr>   
												      		<td>
												      			<input type="text" name=<%= "mausoHD" + i %> value = "<%=hd.getMauhoadon() %>" style="width: 100%; border-radius:4px; height: 20px;" readonly >
												      		</td>
												      		<td>
												      			<input type="hidden" name=<%= "maHD" + i %> value = "<%= hd.getMahoadon() %>" style="width: 100%; border-radius:4px; height: 20px;" readonly="readonly" >											      	
												      			<%-- <input type="hidden" name=<%= "mausoHD" + i %> value = "<%=hd.getMauhoadon() %>" style="width: 100%; border-radius:4px; height: 20px;" >	 --%>										      		
												      			<input type="text" name=<%= "kyhieu" + i %> value = "<%=hd.getKyhieu() %>" style="width: 100%; border-radius:4px; height: 20px;" readonly >
												      		</td>
												      		
												      		<td>
												      			<input type="text" name=<%= "sohd" + i  %> value = "<%= hd.getSohoadon() %>" style="width: 100%; border-radius:4px; height: 20px;" readonly >
												      		</td>

												      		<td>
												      			<input type="text" name=<%= "ngayhd" + i %> value = "<%= hd.getNgayhoadon() %>" style="width: 100%; border-radius:4px; height: 20px;"  class="days" readonly >
												      		</td>
												      		<td>
												      			<input type="text" name= <%= "tenncc" + i %> id = <%= "tenncc" + i + dem %> value = "<%=hd.getTenNCC() %>" style="width: 100%; border-radius:4px; height: 20px;" readonly >
												      		</td>
												      		<td><input type="text" name= <%= "diaChiNCC" + i %> id = <%= "diaChiNCC" + i + dem %>  value = "<%=hd.getDiaChiNCC() %>" style="width: 100%; border-radius:4px; height: 20px;"></td>
												      		<td>
												      			<input type="text" name= <%= "mst" + i %> id = <%= "mst" + i + dem %>  value = "<%=hd.getMasothue() %>" style="width: 100%; border-radius:4px; height: 20px;"   readonly >
												      		</td>
												      		
												      		
												      		<td>
												      			<input type="text" name=<%= "tienhang" + i %> value = "<%= formatter.format(Double.parseDouble(hd.getTienhang())) %>" style="width: 100%; border-radius:4px; height: 20px; text-align: right; " onkeypress="return keypress(event);" readonly >
												      		</td>

												      		<td>
												      			<input type="text" name=<%= "thuesuat" + i  %> value = "<%= formatter.format(Double.parseDouble(hd.getThuesuat())) %>" style="width: 100%; border-radius:4px; height: 20px; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdangdukien(this)" readonly >
												      			<input type="hidden" name=<%= "thuesuat_goc" + i  %> value = "<%= hd.getThuesuat() %>" style="width: 100%" >
												      		</td>

												      		<td>
												      			<input type="text" name=<%= "thue" + i %> value = "<%= formatter.format(Double.parseDouble(hd.getTienthue())) %>"  style="width: 100%; border-radius:4px; height: 20px; text-align: right;" onkeypress="return keypress(event);" readonly  >
												      		</td>

												      		<td>
												      			<input type="text" name=<%= "cong" + i %> value = "<%= formatter.format(Double.parseDouble(hd.getTienhang()) + Double.parseDouble(hd.getTienthue())) %>" style="width: 100%; border-radius:4px; height: 20px; text-align: right;" readonly=readonly>
												      		</td>

												      		<td>
												      			<input type="text" name=<%= "ghichu" + i %> value = "<%= hd.getGhichu()%>" style="width: 100%; border-radius:4px; height: 20px;" readonly >
												      		</td>

															</tr>
												      	<%    			// Ket thuc vong lap
												      	   dem++;
												      		}
											      	  			 
											      		 
											      // Thêm 5 dòng trống để có thể thêm hóa đơn	 

										      		for(int n = 0; n < 20; n++){ %>
										      		<tr>	
											      		<td>
											      			<input type="text" name=<%= "mausoHD" + i %> value ="" style="width: 100%; border-radius:4px; height: 20px;" readonly >	
											      		</td>
											      		<td>
											      			<input type="hidden" name=<%= "maHD" + i %> value = "" style="width: 100%; border-radius:4px; height: 20px;" readonly="readonly" >											      		
											      		<%-- 	<input type="hidden" name=<%= "mausoHD" + i %> value ="" style="width: 100%; border-radius:4px; height: 20px;" > --%>											      		
											      			<input type="text" name=<%= "kyhieu" + i %> value = "" style="width: 100%; border-radius:4px; height: 20px;" readonly >
											      		</td>
											      		
											      		<td>
											      			<input type="text" name=<%= "sohd" + i  %> value = "" style="width: 100%; border-radius:4px; height: 20px;" readonly >
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "ngayhd" + i %> value = "" style="width: 100%; border-radius:4px; height: 20px;"  class="days" readonly>
											      		</td>
											      		<td>
											      			<input type="text" name= <%= "tenncc" + i %>  id=<%= "tenncc" + i + dem %> value = "" style="width: 100%; border-radius:4px; height: 20px;" readonly >
											      		</td>
											      		<td><input type="text" name= <%= "diaChiNCC" + i %> id = <%= "diaChiNCC" + i + dem %>  value = "" style="width: 100%; border-radius:4px; height: 20px;"></td>
											      		<td>
											      			<input type="text" name= <%= "mst" + i %> id=<%= "mst" + i + dem  %>  value = "" style="width: 100%; border-radius:4px; height: 20px;"  readonly >
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "tienhang" + i %> value = "" style="width: 100%; border-radius:4px; height: 20px;text-align: right;" readonly>
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "thuesuat" + i  %> value = "" style="width: 100%; border-radius:4px; height: 20px;text-align: right;" onkeypress="return keypress(event);" readonly>
											      			<input type="hidden" name=<%= "thuesuat_goc" + i  %> value = "0" style="width: 100%; border-radius:4px; height: 20px;text-align: right;"  >
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "thue" + i %> value = ""   style="width: 100%; border-radius:4px; height: 20px;text-align: right;" readonly   >
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "cong" + i %> value = "" style="width: 100%; border-radius:4px; height: 20px;text-align: right;" readonly=readonly>
											      		</td>

											      		<td>
											      			<input type="text" name=<%= "ghichu" + i %> value = "" style="width: 100%; border-radius:4px; height: 20px;" readonly>
											      		</td>

														</tr>
													
										      	<% dem++;}  			// Ket thuc vong lap for cho 5 dong hoa don trang
										      	%>
													</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%= count %>')">Hoàn tất</a>
								                     </div>
								                </DIV>
				           	 				</td>
				           	 				
<!-- 											Start Sản phẩm kho -->
											<td align="right">
				           	 					<a href="" id="sanPhamKho_<%= count %>" rel="subcontentSanPhamKho_<%= count %>">
					           	 				       <img alt="Tên sản phẩm hóa đơn" src="../images/vitriluu.png"></a>
					           	 				<DIV  id="subcontentSanPhamKho_<%= count %>"  style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
								                             background-color: white; width: 500px; padding: 4px;  max-height: 200px; overflow: auto;" >
								                              
								                     <table width="90%" align="center">
								                        <tr>
								                            <th width="50px">Mã </th>
								                            <th width="100px">Tên </th>
								                            <th width="100px" align="center">%Phân bổ</th>
								                        </tr>
						                            	<%
						                            		int t = 0;
							                        		if(sp.getSanPhamKhoList() != null)
							                        		{
							                        			
							                        			for (Erp_Item item : sp.getSanPhamKhoList())
							                        			{  %>
							                        			
							                    				<tr align="center" >
																	<td>
																		<input type="text" name="<%= "masp" + i + t %>" style="width: 100%; border-radius:4px; height: 20px;" value="<%= item.getValue() %>"  readonly >
																		<input type="hidden" name="<%= "spId" + i + t %>" style="width: 100%; border-radius:4px; height: 20px;" value="<%= item.getValue() %>"  >
																		
																	</td>
																	<td>
																		<input type="text" name="<%= "tensp" + i + t %>" style="width: 100%; border-radius:4px; height: 20px;" value="<%= item.getName() %>"  readonly >
																	</td>

																	<td>
							                        					<input type="text" name="<%= "ptphanbo" + i + t %>" style="width: 100%; border-radius:4px; height: 20px;text-align:right" value="<%= item.getPhantram() %>" readonly>
							                        				</td>
							                        			</tr>
							                        										                        			
							                        		 <% t++; }
							                        		} 
							                        		
							                        		if(t < 15){
							                        			for (int n = t; n < 15; n++)
							                        			{  %>
								                        			<tr align="center" >
																		<td>
																			<input type="text" name="<%= "masp" + i + n %>" style="width: 100%; border-radius:4px; height: 20px;" value=""  readonly>
																			<input type="hidden" name="<%= "spId" + i + n %>" style="width: 100%; border-radius:4px; height: 20px;" value=""  >
																			
																		</td>
																		<td>
																			<input type="text" name="<%= "tensp" + i + n %>" style="width: 100%; border-radius:4px; height: 20px;" value=""  readonly >
																		</td>

																		<td>
								                        					<input type="text" name="<%= "ptphanbo" + i + n %>" style="width: 100%; border-radius:4px; height: 20px;text-align:right" value="" readonly>
								                        				</td>
								                        			</tr>
								                        			
								                        	<% } 
							                        			
							                        		}
							                        		
							                        		%>								                    
							                        </table>
								                     
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentSanPhamKho_<%= count %>')">Hoàn tất</a>
								                     </div>
								                </DIV>
				           	 				</td>
<!-- 											End Sản phẩm kho -->					           	 				
										</tr>
										
									<% count++; 
									}
								} %>
							
							
							<tr class="tbfooter">
								<td colspan="12">&nbsp;</td>
							</tr>
						</TABLE>
					</div>
					
					<% } %>
					
				</fieldset>
			</div>
		</div>
<script type="text/javascript">
	replaces();
	
	dropdowncontent.init("noidungGhiChu", "right-top", 500, "click");
	dropdowncontent.init("chiphiKHAC", "left-top", 500, "click");
	dropdowncontent.init("searchlink", "right-bottom", 500, "click");
	
	var loaidoituong =  document.forms["dmhForm"].loaidoituong.value ;
	if(loaidoituong=="0"){
		
	
		jQuery(function()
		{		
			$("#nccId").autocomplete("ErpNccMuaHangListGiay.jsp");
		});	
	}
</script>

<script type="text/javascript">
var loaidoituong =  document.forms["dmhForm"].loaidoituong.value ;
if(loaidoituong=="1"){
	
	jQuery(function()
	{		
		$("#nvId").autocomplete("ErpNhanvienList.jsp");
	});	
}
</script>

<script type="text/javascript">
    dropdowncontent.init('pclist', "left-bottom", 250, "click");
	dropdowncontent.init('duyetId', "right-bottom", 300, "click");
</script>	

<script type="text/javascript">
	for(var i = 0; i < 30; i++) {
		dropdowncontent.init('tenhd_'+i, "left-top", 300, "click");
	}
	
	for(var i = 0; i < 30; i++) {
		dropdowncontent.init('sanPhamKho_' + i, "left-top", 300, "click");
	}
</script>

	<%
		
		if(spList!=null){ spList.clear(); spList = null; }
 		if(dvList!=null){ dvList.clear(); dvList = null;}
 		if(ttList!=null){ ttList.clear(); ttList = null; }
 		if(khoList!=null){ khoList.clear(); khoList = null; }
 		if(nccList!=null){ nccList.clear(); nccList = null; }
 		if(phieuchiList!= null){ phieuchiList.clear(); phieuchiList = null; }
 		if(dvthList != null) { dvthList.close(); dvthList = null; }
 		if(loaihhList != null) { loaihhList.close(); loaihhList = null; }
 		if(rsNguoidenghi != null) { rsNguoidenghi.close(); rsNguoidenghi = null; }
 		if(nvRs != null) { nvRs.close(); nvRs = null; }
 		if(khRs != null) { khRs.close(); khRs = null; }
 		if(nhacungcapRs != null) { nhacungcapRs.close(); nhacungcapRs = null; }
 		if(kbhRs != null) { kbhRs.close(); kbhRs = null; }
 		if(htttRs != null) { htttRs.close(); htttRs = null; }
 		if(PbRs != null) { PbRs.close(); PbRs = null; }
 		if(SpRs != null) { SpRs.close(); SpRs = null; }
 		if(BvRs != null) { BvRs.close(); BvRs = null; }
 		if(DbRs != null) { DbRs.close(); DbRs = null; }
 		
 		session.setAttribute("dmhBean",null);
		dmhBean.close();
	%>
</form>
</BODY>
</html>