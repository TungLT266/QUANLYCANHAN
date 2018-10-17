<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.buttoantonghop.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.buttoantonghop.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.util.Hashtable" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	System.out.println("");
	NumberFormat formatter=new DecimalFormat("#,###,###"); 
	NumberFormat formatter2=new DecimalFormat("#,###,###.##"); 
	IErpButToanTongHop btthBean =(IErpButToanTongHop)session.getAttribute("btthBean");
	int count = btthBean.getCount();
	System.out.println("Count: " + count);
	ResultSet rstkkt_no = btthBean.getTaiKhoanKT_NoRs();
	ResultSet rstkkt_co = btthBean.getTaiKhoanKT_CoRs();

	ResultSet kbh = btthBean.getKbhRs();
	ResultSet mavv = btthBean.getMavvRs();
	ResultSet diaban = btthBean.getDiabanRs();
	ResultSet tinhthanh = btthBean.getTinhthanhRs();
	ResultSet benhvien = btthBean.getBenhvienRs();
	ResultSet sanpham = btthBean.getSanphamRs();
	ResultSet rsSanPham = btthBean.getRsSanPham();
	
	ResultSet rsTienTe = btthBean.getRsTienTe();
	
	String[] TaiKhoanNoIds = btthBean.getTaiKhoanNoIds();
	String[] TaiKhoanCoIds = btthBean.getTaiKhoanCoIds();
	String[] Sotien = btthBean.getSotien();
	String[] SotienNT = btthBean.getSoTienNT();
	
	String tienTe = btthBean.getTienTe();
	if(Sotien == null){
		Sotien = new String[count];
		for (int i = 0; i < count; i++){
			Sotien[i] = "0";
		}
	}
	 if(SotienNT == null){
		SotienNT = new String[count];
		for (int i = 0; i < count; i++){
			SotienNT[i] = "0";
		}
	} 
	String[] TtcpNoId = btthBean.getTtcpNoIds();
	if(TtcpNoId == null){
		TtcpNoId = new String[count];
		for (int i = 0; i < count; i++){
			TtcpNoId[i] = "";
		}
	}


	
	String[] TtcpCoId = btthBean.getTtcpCoIds();
	
	if(TtcpCoId == null){
		TtcpCoId = new String[count];
		for (int i = 0; i < count; i++){
			TtcpCoId[i] = "";
		}
	}
	
	String[] dc_noIds = btthBean.getDungcho_No();
	if(dc_noIds == null){
		dc_noIds = new String[count];
		for (int i = 0; i < count; i++){
			dc_noIds[i] = "";
		}
	}
	
	String[] dc_noTens = btthBean.getDungcho_NoTen();
	if(dc_noTens == null){
		dc_noTens = new String[count];
		for (int i = 0; i < count; i++){
			dc_noTens[i] = "";
		}
	}
	
	String[] dc_coIds = btthBean.getDungcho_Co();
	
	if(dc_coIds == null){
		dc_coIds = new String[count];
		for (int i = 0; i < count; i++){
			dc_coIds[i] = "";
		}
	}
	String[] dc_coTens = btthBean.getDungcho_CoTen();
	if(dc_coTens == null){
		dc_coTens = new String[count];
		for (int i = 0; i < count; i++){
			dc_coTens[i] = "";
		}
	}
	
	String[] kbhIds = btthBean.getKbhIds();
	if(kbhIds == null){
		kbhIds = new String[count];
		for (int i = 0; i < count; i++){
			kbhIds[i] = "";
		}
	}
	
	String[] mavvIds = btthBean.getMavvIds();
	if(mavvIds == null){
		mavvIds = new String[count];
		for (int i = 0; i < count; i++){
			mavvIds[i] = "";
		}
	}
	
	String[] dg = btthBean.getDg();
	if(dg == null){
		dg = new String[count];
		for (int i = 0; i < count; i++){
			dg[i] = "";
		}
	}
	
	String[] diabanIds = btthBean.getDiabanIds();
	if(diabanIds == null){
		diabanIds = new String[count];
		for (int i = 0; i < count; i++){
			diabanIds[i] = "";
		}
	}
	
	String[] tinhthanhIds = btthBean.getTinhthanhIds();
	if(tinhthanhIds == null){
		tinhthanhIds = new String[count];
		for (int i = 0; i < count; i++){
			tinhthanhIds[i] = "";
		}
	}
	
	String[] benhvienIds = btthBean.getBenhvienIds();
	if(benhvienIds == null){
		benhvienIds = new String[count];
		for (int i = 0; i < count; i++){
			benhvienIds[i] = "";
		}
	}
	
	String[] sanphamIds = btthBean.getSanphamIds();
	if(sanphamIds == null){
		sanphamIds = new String[count];
		for (int i = 0; i < count; i++){
			sanphamIds[i] = "";
		}
	}
	
	ResultSet dc_noRs, dc_coRs;
	
	Hashtable<String, String> btth_mauhd = btthBean.getBtth_Mauhd();
	Hashtable<String, String> btth_kyhieuhd = btthBean.getBtth_Kyhieuhd();
	Hashtable<String, String> btth_msthd = btthBean.getBtth_MSThd();
	Hashtable<String, String> btth_ngayhd = btthBean.getBtth_Ngayhd();
	Hashtable<String, String> btth_sohd = btthBean.getBtth_Sohd();
	Hashtable<String, String> btth_tenNCChd = btthBean.getBtth_TenNCChd();
	Hashtable<String, String> btth_thuesuathd = btthBean.getBtth_Thuesuathd();
	Hashtable<String, String> btth_tienhanghd = btthBean.getBtth_Tienhanghd();
	Hashtable<String, String> btth_tienthuehd = btthBean.getBtth_Tienthuehd();
	Hashtable<String, String> btth_ghichuhd = btthBean.getBtth_Ghichuhd();
	Hashtable<String, String> btth_conghd = btthBean.getBtth_Conghd();
	Hashtable<String, String> btth_diachi = btthBean.getBtth_diachi();
	//----------sản phẩm-------------//
	Hashtable<String, String> spSTT = btthBean.getStt();
	Hashtable<String, Double> spPhanTram = btthBean.getPhanTram();
	Hashtable<String, String> spMaSP = btthBean.getMaSanPham();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>TraphacoERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/dropdowncontent2.js"></script>

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
	width: 400px; /* Width of box */
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

.mySCHME tr,td input
{
	color: red;
}

.mySCHME input
{
	color: red;
}

</style>

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.scrollTableBody-1.0.0.js" type="text/javascript"></script>


<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});
		$('html, body').animate({
            scrollTop: $('body').height()
        },500);
	});	
	
</script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spList.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent2.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<script language="JavaScript" type="text/javascript">
  $(document).ready(function () {
      $('td').end().focus();
  });
	function upperLetters(element)
	{
		element.value = element.value.toUpperCase();
	}

	function validateSpecialCharacters(e) 
	{ 
		 
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || (keypressed > 57 & keypressed < 65) || (keypressed > 90 & keypressed < 97) || (keypressed > 122))
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 46|| keypressed == 45 || keypressed == 47)
			{
				return;
			}
			return false;
		}
	} 
	
	function submitform()
	{
	    document.forms["btth"].submit();
	}


	function chonTienTe(){
		var loaiTienTe = document.getElementById("idTienTe");
		console.log(loaiTienTe.value);
		if(loaiTienTe.value == '100000'){
			var tigia = document.getElementById("tiGia");
			tigia.value = 1;
		}
		
	}
	function fillNgoaiTe(){
		
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


	function dinhdangTG(){
		var tigia = document.getElementById("tiGia");
		tigia.value = DinhDangDonGia( parseFloat(tigia.value).toFixed(2));
		var sotien = document.getElementsByName("Sotien");
		
		var tienTe =  document.getElementById("tienteID").value;
		/* var nt = document.getElementById("SotienNT_" + stt); */
		/* console.log('ti gia:' + tigia.value); */
		var tienVN = document.getElementsByName("Sotien");
		if( tienTe != 100000){
			
			var tienNT = document.getElementsByName("SotienNT"); 
			for(var i =0 ; i< tienVN.length ; i++){
				/* console.log('chi so: ' + i); */
				dinhdangNT(i);
			}
		}
		
	}
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
			
		//num = (Math.round( num * 1000 ) / 1000).toString();
			
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
	function save()
	{	
		 if(document.getElementById("NgayButToan").value == "")
		 {
			 alert('Vui lòng chọn ngày bút toán');
			 return;
		 }
		 if(document.getElementById("DienGiai").value == "")
		 {
			 alert('Vui lòng nhập diễn giải');
			 return;
		 }		
		 var machungtu = document.getElementById("SoChungTu");
		 if(document.getElementById("SoChungTu").value == "")
		 {
			 alert('Vui lòng chọn ngày để có được số chứng từ');
			 return;
		 }		
		// KIỂM TRA TIỀN
		 var pk_seq = document.getElementsByName("pk_seq");
		 var total_Sotien = document.getElementsByName("Sotien");
		 
		 for( i = 0; i < pk_seq.length ; i++ )
		 {
			 var sumtien = 0;
			 var sumtientong = total_Sotien.item(i).value.replace(/,/g,"");
			 
			 for( n = 0; n < 30; n++ )
			 {
	
				 var temID = pk_seq.item(i).value + "__"+n;
				 var tienhang = 0;
				 var thuesuat = 0;
				 var tienthue = 0;
				 var cong = 0; 
				 				 
				 var _hdTIENHANG = document.getElementById(temID+"_hdTIENHANG");
				 var _hdTHUESUAT = document.getElementById(temID+"_hdTHUESUAT");
				 var _hdTIENTHUE = document.getElementById(temID+"_hdTIENTHUE");
				 var _hdCONG = document.getElementById(temID+"_hdCONG");
				 
				 if(_hdTIENHANG.value != '' )
					 tienhang = parseFloat(_hdTIENHANG.value.replace(/,/g,""));
				 
				 if(_hdTHUESUAT.value != '')
					 thuesuat = parseFloat(_hdTHUESUAT.value.replace(/,/g,""));
				 
				 if(_hdTIENTHUE.value != '')
					 tienthue = parseFloat(_hdTIENTHUE.value.replace(/,/g,"")); 
				 			 
				 if(tienhang>=0 && _hdTIENHANG.value != '')
					 sumtien = sumtien + tienhang + tienthue;
				 	 				 
			 } 
			 
	
			 if(sumtien > 0) // kiểm tra với dòng tổng bên ngoài
			 {
				/*  if(sumtientong!=sumtien)
					 {
						 alert('Dòng tiền hóa đơn không khớp với dòng tổng. Vui lòng xem lại dòng có số tiền tổng là: '+sumtientong+', sumtien:'+sumtien );
						 return;
					 }
					  */
			 }		
		 }  
		//KIỂM TRA TỔNG PHẦN TRĂM
			// var pk_seq = document.getElementsByName("pk_seq");
			
			 
			 for( i = 0; i < pk_seq.length -1 ; i++ )
			 {
				 var temp = pk_seq.item(i).value;
				 console.log(temp);
				 var phantram = document.getElementsByName(temp +"_spPhanTram");
				
				 console.log("phan trăm:" + phantram.length);
				 var tong = 0;
				 var kt =0;
				 for( j = 0; j< phantram.length; j++){
					 if(parseFloat(phantram.item(j).value.replace(/,/g,"")) > 0){
						 tong = tong + parseFloat(phantram.item(j).value.replace(/,/g,""));
						 kt =1;
					 }
					
				 }
				 console.log("Tổng là: " + tong);
				 if(kt == 1){
					 if(tong > 100){
						 alert('Tổng phần trăm của sản phẩm ở dòng: '+ (temp +1)+' lớn hơn 100' );
						 return;
					 }
					 if(tong < 100){
						 alert('Tổng phần trăm của sản phẩm ở dòng: '+ (temp +1)+' nhỏ hơn 100' );
						 return;
					 }
				 }
				 
			 }  
		  var pk_seq = document.getElementsByName("pk_seq");
		 
		  document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
	  document.forms["btth"].action.value = "update";
	  document.forms["btth"].submit(); 
	}
	function kiemtra(){
		
	}
	function selectChange( pos)
	{
		var e = document.getElementById("TaiKhoanId_" + pos);
		var checkDungTtcp;
		
		checkDungTtcp = e.options[e.selectedIndex].value;
		var array = checkDungTtcp.split("_");
		
		//Read only doi voi nhung tai khoan khong dung TTCP
		if(array[1] == "0")
		{
			var ele = document.getElementById("TtcpId_"+pos);
			ele.style.display = 'none';
		}
		else 
		{
			var ele = document.getElementById("TtcpId_"+pos);
			ele.style.display = '';
		}
		
	}
	
	function selectChange2( pos)
	{
		var e = document.getElementById("TaiKhoanIds_" + pos);
		var checkDungTtcp;
		
		checkDungTtcp = e.options[e.selectedIndex].value;
		var array = checkDungTtcp.split("_");
		
		//Read only doi voi nhung tai khoan khong dung TTCP
		if(array[1] == "0")
		{
			var ele = document.getElementById("TtcpId_"+pos);
			ele.style.display = 'none';
		}
		else 
		{
			var ele = document.getElementById("TtcpId_"+pos);
			ele.style.display = '';
		}
		
		/* var dungcho = document.getElementsByName("dungcho_" + pos);
		
		if(dungcho.item(e.selectedIndex).value == "0")
		{
			var ele = document.getElementById("dcIds_"+pos);
			ele.style.display='none';			
		}else{
	//		var ele = document.getElementById("dcIds_"+pos);
	//		ele.style.display='';						
			document.forms["btth"].submit(); 
		} */
		
		document.forms["btth"].submit(); 
	}
	
	function AddReadOnly(pos)
	{
		var no=document.getElementById("No_"+pos);
		var co=document.getElementById("Co_"+pos);
		var dinhdang;
		if(parseFloat(no.value)!=0&&no.value!="")
		{
	//		co.style.display='none';
			no.value=DinhDangTien(Math.round(no.value.replace(/,/g,"")));
		}
		else if(parseFloat(co.value)!=0 && co.value!="")
		{
	//		no.style.display='none';
			co.value =	DinhDangTien(Math.round(co.value.replace(/,/g,"") ));
		}
		else 
			{
				no.style.display='';
				co.style.display='';
			}
	}
	
	function Dinhdang(stt){
		var sotien = document.getElementById("Sotien_" + stt);
		sotien.value = DinhDangTien(sotien.value);
		/* console.log('da vao day'); */
		tinhTongTien();
	}
	
	function dinhdangNT(stt){
		var nt = document.getElementById("SotienNT_" + stt);
		var tiGia = document.getElementById("tiGia");
		var tienviet = nt.value*parseFloat(tiGia.value.replace(/,/g,""));
		console.log('tien nt:' + nt.value);
		console.log('ti gia ' + tiGia.value);
		console.log('tien viet: ' + tienviet);
		var tienVND = document.getElementById("Sotien_" + stt);
		tienVND.value = DinhDangTien(tienviet);
		nt.value = DinhDangDonGia( parseFloat(nt.value).toFixed(2));
		
		tinhTongTien();
		
		
		
	}
	function LaySoChungTu() {
		var ngay = document.getElementById("NgayButToan");
		var thang =  ngay.value.toString().substring(5, 7);
		var nam = ngay.value.toString().substring(0, 4);
	
		var sobtth = document.getElementById("btthtieptheo");
		var machungtu = document.getElementById("SoChungTu");
		
		machungtu.value = "BTTH" + thang.toString() +nam.toString() + "-"+ sobtth.value;
		
		
		
	}
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
		num.substring(num.length-(4*i+3)); 
	    return (((sign)?'':'-') + num);
	}
	Number.prototype.format = function(n, x) {
	    var re = '(\\d)(?=(\\d{' + (x || 3) + '})+' + (n > 0 ? '\\.' : '$') + ')';
	    return this.toFixed(Math.max(0, ~~n)).replace(new RegExp(re, 'g'), '$1,');
	};
	
	function tinhthue(){ // đánh tiền hàng
		
		 var pk_seq = document.getElementsByName("pk_seq");
		 var total_Sotien = document.getElementsByName("Sotien");
		 
		 for( i = 0; i < pk_seq.length ; i++ )
		 {
			 var sumtien = 0;
			 
			 for( n = 0; n < 30; n++ )
			 {
				 var temID = pk_seq.item(i).value + "__"+n;
				 var tienhang = 0;
				 var thuesuat = 0;
				 var tienthue = 0;
				 var cong = 0; 
				 				 
				 var _hdTIENHANG = document.getElementById(temID+"_hdTIENHANG");
				 var _hdTHUESUAT = document.getElementById(temID+"_hdTHUESUAT");
				 var _hdTIENTHUE = document.getElementById(temID+"_hdTIENTHUE");
				 var _hdCONG = document.getElementById(temID+"_hdCONG");
				 
				 if(_hdTIENHANG.value != '' )
					 tienhang = parseFloat(_hdTIENHANG.value.replace(/,/g,""));
				 
				 if(_hdTHUESUAT.value != '')
					 thuesuat = parseFloat(_hdTHUESUAT.value.replace(/,/g,""));
				 
				 if(_hdTIENTHUE.value != '')
					 tienthue = parseFloat(_hdTIENTHUE.value.replace(/,/g,"")); 
				 
				 tienthue = Math.round( tienhang*thuesuat/100 );
				 				 
				 if(tienhang>=0 && _hdTIENHANG.value != '')
				 {
					 _hdTIENHANG.value = DinhDangTien(tienhang);
					 _hdTIENTHUE.value = DinhDangTien(tienthue);
					 _hdTHUESUAT.value = DinhDangTien(thuesuat);
					 _hdCONG.value = DinhDangTien(tienhang + tienthue);
					 
					 sumtien = sumtien + tienhang+tienthue ;
				 }
				 
				 if(sumtien > 0)
				 	total_Sotien.item(i).value = DinhDangTien(sumtien);				 				 
			 } 
		 } 
			tinhTongTien();
	}

	function tinhthue2(){ // đánh thuế suất
		
		 var pk_seq = document.getElementsByName("pk_seq");
		 var total_Sotien = document.getElementsByName("Sotien");
		 
		 for( i = 0; i < pk_seq.length ; i++ )
		 {
			 var sumtien = 0;
			 for( n = 0; n < 30; n++ )
			 {
				 var temID = pk_seq.item(i).value + "__"+n;
				 var tienhang = 0;
				 var thuesuat = 0;
				 var tienthue = 0;
				 var cong = 0; 
				 				 
				 var _hdTIENHANG = document.getElementById(temID+"_hdTIENHANG");
				 var _hdTHUESUAT = document.getElementById(temID+"_hdTHUESUAT");
				 var _hdTIENTHUE = document.getElementById(temID+"_hdTIENTHUE");
				 var _hdCONG = document.getElementById(temID+"_hdCONG");
				 
				 if(_hdTIENHANG.value != '' )
					 tienhang = parseFloat(_hdTIENHANG.value.replace(/,/g,""));
				 
				 if(_hdTHUESUAT.value != '')
					 thuesuat = parseFloat(_hdTHUESUAT.value.replace(/,/g,""));
				 
				 if(_hdTIENTHUE.value != '')
					 tienthue = parseFloat(_hdTIENTHUE.value.replace(/,/g,"")); 
				 
				 tienthue = Math.round( tienhang*thuesuat/100 );
				 				 
				 if(thuesuat>=0 && _hdTHUESUAT.value != '')
				 {
					 _hdTIENHANG.value = DinhDangTien(tienhang);
					 _hdTIENTHUE.value = DinhDangTien(tienthue);
					 _hdTHUESUAT.value = DinhDangTien(thuesuat);
					 _hdCONG.value = DinhDangTien(tienhang + tienthue);
					 
					 sumtien = sumtien + tienhang+tienthue ;
				 }				 				 
				 
			 } 
			 
			 if(sumtien > 0)
				 	total_Sotien.item(i).value = DinhDangTien(sumtien);		
		 }  
			tinhTongTien();
	}
	
	function tinhthue3(){ // đánh tiền thuế.
		
		 var pk_seq = document.getElementsByName("pk_seq");
		 var total_Sotien = document.getElementsByName("Sotien");
		 
		 for( i = 0; i < pk_seq.length ; i++ )
		 {
			 var sumtien = 0;
			 
			  for( n = 0; n < 30; n++ )
			 {
				 var temID = pk_seq.item(i).value + "__"+n;
				 var tienhang = 0;
				 var thuesuat = 0;
				 var tienthue = 0;
				 var cong = 0; 
				 				 
				 var _hdTIENHANG = document.getElementById(temID+"_hdTIENHANG");
				 //var _hdTHUESUAT = document.getElementById(temID+"_hdTHUESUAT");
				 var _hdTIENTHUE = document.getElementById(temID+"_hdTIENTHUE");
				 var _hdCONG = document.getElementById(temID+"_hdCONG");
				 
				  if(_hdTIENHANG.value != '' )
					 tienhang = parseFloat(_hdTIENHANG.value.replace(/,/g,""));
				 
				/*  if(_hdTHUESUAT.value != '')
					 thuesuat = parseFloat(_hdTHUESUAT.value.replace(/,/g,""));  */
				 
				 if(_hdTIENTHUE.value != '')
					 tienthue = parseFloat(_hdTIENTHUE.value.replace(/,/g,"")); 
				 				 				 
				 if(tienthue>=0 && _hdTIENTHUE.value != '')
				 {
					 //_hdTIENHANG.value = DinhDangTien(tienhang);
					 _hdTIENTHUE.value = DinhDangTien(tienthue);
					 _hdCONG.value = DinhDangTien(tienhang + tienthue); 
					// _hdTHUESUAT.value = DinhDangTien(Math.round( tienthue/tienhang*100 ));
					 
					 sumtien = sumtien + tienhang+tienthue ;
				 }
				 				 
			 } 
			  
			  if(sumtien > 0)
				 	total_Sotien.item(i).value = DinhDangTien(sumtien);		
		 }  
		 
		 
			tinhTongTien();
	}
	function replaces()
	{
		var count =  document.getElementsByName("count");
		var k= count.item(0).value;
		var dcNoTens =  document.getElementsByName("dcNoTens");
		var dcNoIds =  document.getElementsByName("dcNoIds");
		for(var i=0;i<k;i++)
		{
		
			if(  dcNoTens.item(i).value != "" )
			{
				var sp = dcNoTens.item(i).value;
				var pos = parseInt(sp.indexOf("--"));
				if(pos > 0)
				{
					dcNoIds.item(i).value = sp.substring(0, pos);
					dcNoTens.item(i).value = sp.substring(pos+2, sp.length);
				}
			}
			else
			{
				dcNoIds.item(i).value = "";
				dcNoTens.item(i).value = "";
			}
		}
		
		var dcCoTens =  document.getElementsByName("dcCoTens");
		var dcCoIds =  document.getElementsByName("dcCoIds");
		for(var i=0;i<k;i++)
		{
		
			if(  dcCoTens.item(i).value != "" )
			{
				var sp = dcCoTens.item(i).value;
				var pos = parseInt(sp.indexOf("--"));
				if(pos > 0)
				{
					dcCoIds.item(i).value = sp.substring(0, pos);
					dcCoTens.item(i).value = sp.substring(pos+2, sp.length);
				}
			}
			else
			{
				dcCoIds.item(i).value = "";
				dcCoTens.item(i).value = "";
			}
		}
		/* console.log('đã vào replaces'); */
		/*  var loaidoituong =  document.forms["dmhForm"].loaidoituong.value ; */
		//var loaidoituong = document.getElementById("loaidoituong").value;
		var Dongsp = document.getElementsByName("Sotien");
	 	for(var k = 0 ; k < Dongsp.length; k++ ){
	 		var idsp = document.getElementsByName(k + "_spPK_Seq");
			var masp = document.getElementsByName(k + "_spMA");
			var tensp = document.getElementsByName(k + "_spTEN");
			
			
			var sodong =  masp.length;
			/* console.log('so dong: ' + sodong); */
			var i;
			for(i = 0; i < sodong; i++)
			{
				if(  masp.item(i).value != "" )
				{
					console.log(i);
					var sp = masp.item(i).value;
					console.log(sp);
					var pos = parseInt(sp.indexOf("--"));
					var vtridauma = parseInt(sp.indexOf("-- [")) + 4;
					var vitricuoima = parseInt(sp.indexOf("]["));
					var vitridauten = vitricuoima + 2;
					var vitricuoiten = sp.length-1;
					
					
					if(pos > 0)
					{
						/* console.log('ma ' + sp.substring(0, pos)); */
						idsp.item(i).value = sp.substring(0, pos);
						masp.item(i).value = sp.substring(vtridauma, vitricuoima);
						tensp.item(i).value = sp.substring(vitridauten, vitricuoiten);
	
					}
				 
				}
				else 
				{
					
							idsp.item(i).value = "";
							tensp.item(i).value = "";
							
				}
			}  
			 
	 	}
		//CAP NHAT CHI PHI KHAC
		/* var sotienCPK = document.getElementsByName("sotienCPK");
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
			   for(var k = 0; k < 10; k++)
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
			}		 */
		
			setTimeout(replaces, 500);
		}
		
		 
	function tinhTongTien(){
		var tongtienViet = 0;
		var tongtienNT = 0;
		var tongthue = 0;
		var tienTe =  document.getElementById("tienteID").value;
		/* var nt = document.getElementById("SotienNT_" + stt); */
		var count =  document.getElementById("count").value;
		var tienVN = document.getElementsByName("Sotien");
		if( tienTe != 100000){
			console.log('da lay tien te');
			var tienNT = document.getElementsByName("SotienNT"); 
		}
		
		
		for(var i =0 ; i< tienVN.length ; i++){
			if(tienVN.item(i).value != ''){
				
				console.log('tien viet ' + tongtienViet);
				tongtienViet = tongtienViet + parseFloat(tienVN.item(i).value.replace(/,/g,""));
			}
			if( tienTe != 100000){
			 	if(tienNT.item(i).value != null && tienNT.item(i).value != '' ){
					tongtienNT = tongtienNT + parseFloat(tienNT.item(i).value.replace(/,/g,""));
				} 
			}
			//tính thuế
			for(var n =0; n < 30; n++){
				var temID = i + "__" + n;
				var _hdTIENTHUE = document.getElementById(temID+"_hdTIENTHUE").value;
				if(_hdTIENTHUE != ''){
					tongthue = tongthue + parseFloat(_hdTIENTHUE.replace(/,/g,""));
				}
			}
		}
		document.getElementById("tongtienthue").value = DinhDangTien(tongthue);
		document.getElementById("tongtienvn").value = DinhDangTien(tongtienViet);
		if( tienTe != 100000){
		 	document.getElementById("tongtiennt").value = DinhDangDonGia( parseFloat(tongtienNT).toFixed(2));
		 	
		}
		document.getElementById("tongtiensauthue").value = DinhDangTien(tongtienViet + tongthue);
	}
	
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
	
	function Replace()
	{
		var TaiKhoanId=document.getElementsByName("TaiKhoanId");
		var sodong=TaiKhoanId.length;
	
		for(var i=0;i<sodong;i++)
		{
			AddReadOnly(i);		
			//selectChange(i);
		}		
		
		//mst();
	} 
	
	function goBack()
		{
			window.history.back();
		}
	
	function mst()
	{
		var pk_seq = document.getElementsByName("pk_seq");
		
		for(var i = 0; i < pk_seq.length; i++ )
		{
		   for(var n = 0; n < 30; n++)
		   {    
			   var temID = pk_seq.item(i).value + "__"+n;
			   var _hdMST = document.getElementById(temID+"_hdMST").value;
			   var _hdTENNCC = document.getElementById(temID+"_hdTENNCC");   
			   var mst = document.getElementById(temID+"_hdTENNCC"); 
		    
			    if(_hdMST.value != "" && _hdMST.value != null)
			    {        
				     var pos = parseInt(_hdMST.indexOf("[ "));
				     
				     if(pos>0)
				     {
				    	 mst.value  = masothue.substring(0, pos);
				    	 _hdMST = _hdMST.substr(parseInt(_hdMST.indexOf("[")) + 2);
				    	 _hdTENNCC.value = _hdMST.substring(0, parseInt(_hdMST.indexOf("]"))); 
				    	 
				    	 
				     }
			    }
			    			    
		    }
		 } 
	}

	
</SCRIPT>


<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
 



</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="Replace()">
<form name ="btth" method="post" action="../../ErpButToanTongHopUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="id" value='<%= btthBean.getId() %>' >
<input type="hidden" name="action" value="0">
<input type="hidden" name="count" id = "count" value='<%=count %>'>
<input type="hidden" name="tienteID" id = "tienteID" value='<%=btthBean.getTienTe() %>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý kế toán > Chức năng > Bút toán tổng hợp &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:goBack();" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ;  font-weight:bold"  style="width: 100% ; color:#ffffff ; font-weight:bold" readonly="readonly" rows="1"><%= btthBean.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Bút toán tổng hợp </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Ngày bút toán </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text"  class="days" name="NgayButToan" id="NgayButToan" value="<%= btthBean.getNgayButToan() %>"  > 
		                        </TD>          
		                  </TR> 
		                   <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Số chứng từ</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text"   name="SoChungTu" id="SoChungTu" value="<%=btthBean.getSoChungTu().toUpperCase() %>"  onKeyPress = "return validateSpecialCharacters(event);" onchange="upperLetters(this);" > 
		                        </TD>          
		                  </TR> 
		                  <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Tiền tệ</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	
		                        	<select style="width: 200px"  name="idTienTe" id="idTienTe" onchange="submitform();" >
		                        	<option value  = " "></option>
		                        	<%if(rsTienTe != null){ 
		                        		while(rsTienTe.next()){
		                        			if(btthBean.getTienTe().equals(rsTienTe.getString("PK_SEQ"))){
		                        			%>
		                        			<option value="<%= rsTienTe.getString("PK_SEQ")%>" selected="selected"> <%= rsTienTe.getString("MA") %> -- <%= rsTienTe.getString("TEN") %> </option>
		                        			<%}else{ %>
		                        			<option value="<%= rsTienTe.getString("PK_SEQ")%>"> <%= rsTienTe.getString("MA") %> -- <%= rsTienTe.getString("TEN") %> </option>
		                        			<%} %>
		                        		<%} %>
		                        	<%
		                        	rsTienTe.close();
		                        	} %>
		                        	
								 </select>
		                        </TD>          
		                  </TR>
		                  
		                  <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Tỉ giá</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="tiGia" id="tiGia" value="<%= formatter2.format(btthBean.getTiGia()) %>" onkeypress="return keypress(event);" onchange="dinhdangTG();"> 
		                        </TD>          
		                  </TR>
		                  
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Diễn giải </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="DienGiai" id="DienGiai" value="<%= btthBean.getDienGiai() %>"  > 
		                        </TD>          
		                  </TR> 
		                  
		                   <TR>
		                 	<%double tongVN = 0; %>
		                 	<%for(int k =0; k< Sotien.length; k++){ %>
		                 		<% tongVN = tongVN + Double.parseDouble(Sotien[k].replaceAll(",", "")); %>
		                 	<%} %>
                          		<TD class="plainlabel" valign="middle" width="200px" >Tổng tiền (VND):</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" style="width: 200px;text-align: right;" name="tongtienvn" id="tongtienvn" value="<%= formatter.format(tongVN) %>"  > 
		                        </TD>  
		                         
		                  </TR>
		                  <%if(!tienTe.equals("100000") && !tienTe.equals("")) {%>
								<% double tongNT = 0; %>
								<% for(int k=0; k< SotienNT.length; k++) {%>
										<% tongNT = tongNT + Double.parseDouble(SotienNT[k].replaceAll(",", "")); %>
								<%} %>
		                   <TR>
                          		<TD class="plainlabel" valign="middle" width="200px" >Tổng tiền (ngoại tệ):</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" style="width: 200px;text-align: right;" name="tongtienvn" id="tongtienvn" value="<%= formatter.format(tongNT) %>">
		                        </TD>  
		                     
		                  </TR>
		                  <%} %>
		                  
		                      <TR>
		                      <%int pk_seq1 = 0; 
		                      double tongtienthue = 0;%>
		                      	<%for(int t = 0; t < count;t++){  %>
		                      	 <% for(int n = 0; n < 30; n++){ %>
										<% String temID = pk_seq1 +"__" +n;%>
										<% if ( btth_tienthuehd.get(temID) != null && btth_tienthuehd.get(temID).trim().length() > 0 ) { %>
 												<% tongtienthue = tongtienthue + Double.parseDouble(btth_tienthuehd.get( temID )); %>
										<%} else{%>
											<% tongtienthue = tongtienthue + 0; %>
											<%}%>
								<%}%>
								<% pk_seq1++;
								} %>
                          		<TD class="plainlabel" valign="middle" width="200px" >Tổng tiền thuế:</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" style="width: 200px;text-align: right;" name="tongtienthue" id="tongtienthue" value="<%=formatter.format(tongtienthue) %>"  > 
		                        </TD>     
		                  </TR>
		                  
		                  <TR>
                          		<TD class="plainlabel" valign="middle" width="200px" >Tổng tiền sau thuế</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" style="width: 200px;text-align: right;" readonly="readonly" name="tongtiensauthue" id="tongtiensauthue" value="<%=formatter.format(tongtienthue + tongVN) %>"  > 
		                        </TD>     
		                  </TR>
		                  
						</TABLE>
						
						<table cellpadding="0px" cellspacing="1px" width="80%">
							<tr class="tbheader">
								<th align="center" width = "8%">Tài khoản Nợ</th>
								<th align="center" width = "8%">Tài khoản Có</th>
								<%if(!tienTe.equals("100000") && !tienTe.equals("")) {%>
									<th align="center" width = "5%">Tiền NT</th>
								<%} %>
								
								<th align="center" width = "5%">Số tiền</th>
								<th align="center" width = "8%">Diễn giải</th>
								<th align="center" width = "8%">Đối tượng Nợ </th>
								<th align="center" width = "8%">Đối tượng Có </th>
								<th align="center" width = "8%">Khoản mục chi phí Nợ</th>
								<th align="center" width = "8%">Khoản mục chi phí Có</th>
								<th align="center" width = "8%">Kênh bán hàng </th>
								
								<th align="center" width = "8%">Mã vụ việc</th>
								<th align="center" width = "8%">Địa bàn</th>
								<th align="center" width = "8%">Tỉnh thành</th>
								<th align="center" width = "8%">Bệnh viện</th>
								<th align="center" width = "8%">Sản phẩm</th>
							</tr>
				<% 	int stt = count;
					int pk_seq = 0;
				   	String dungchoNo = "";
				   	String dungchoNoSelected = "";
				   	String dungchoCo = "";
				   	String dungchoCoSelected = "";
					for(int i = 0; i < count;i++){ 
						
						if(TaiKhoanNoIds[i].trim().length() >0) {
							stt++;
						%>
						<TR>
                        	<TD  align="left" >
                        		<input style="text-align: right; width: 100px;" readonly="readonly" type="hidden" name="pk_seq"  id="<%= pk_seq %>" value='<%= pk_seq %>' />
                        		<select style="width: 150px"  name="TaiKhoanNoIds" id="TaiKhoanNoIds_<%=i %>" onchange="submitform();" >
									<option value  = " "></option>
                        		<%	

                        		dungchoNoSelected = "";
                        		
								
                        		rstkkt_no.beforeFirst();
                        		while (rstkkt_no.next())
                        		{  
                        			if(TaiKhoanNoIds[i].indexOf(rstkkt_no.getString("pk_seq"))>=0 ){
                        				
                        				if(rstkkt_no.getString("DUNGCHOKHO").equals("1")){
                        					if(dungchoNoSelected.length() > 0){
                        						dungchoNoSelected += "," + "1";
                        					}else{
                        						dungchoNoSelected = "1";
                        					}
                       					}
                        				if(rstkkt_no.getString("DUNGCHONCC").equals("1")){

                       						if(dungchoNoSelected.length() > 0){
                        						dungchoNoSelected += "," + "2";
                        					}else{
                        						dungchoNoSelected = "2";
                        					}               	

                       					}
                        				if(rstkkt_no.getString("DUNGCHONGANHANG").equals("1")){

                       						if(dungchoNoSelected.length() > 0){
                        						dungchoNoSelected += "," + "3";
                        					}else{
                        						dungchoNoSelected = "3";
                        					}               	

                       					}
                        				if(rstkkt_no.getString("DUNGCHOTAISAN").equals("1")){                  

                       						if(dungchoNoSelected.length() > 0){
                        						dungchoNoSelected += "," + "4";
                        					}else{
                        						dungchoNoSelected = "4";
                        					}               	

                       					}
                        				if(rstkkt_no.getString("DUNGCHOKHACHHANG").equals("1")){    

                       						if(dungchoNoSelected.length() > 0){
                        						dungchoNoSelected += "," + "5";
                        					}else{
                        						dungchoNoSelected = "5";
                        					}               	

                       					}
                        				if(rstkkt_no.getString("COTTCHIPHI").equals("1")){    

                       						if(dungchoNoSelected.length() > 0){
                        						dungchoNoSelected += "," + "6";
                        					}else{
                        						dungchoNoSelected = "6";
                        					}               	

                       					}
                        				if(rstkkt_no.getString("DUNGCHONHANVIEN").equals("1")){    

                       						if(dungchoNoSelected.length() > 0){
                        						dungchoNoSelected += "," + "7";
                        					}else{
                        						dungchoNoSelected = "7";
                        					}               	

                      					}

                        				if(rstkkt_no.getString("DUNGCHODOITUONGKHAC").equals("1")){    

                       						if(dungchoNoSelected.length() > 0){
                        						dungchoNoSelected += "," + "8";
                        					}else{
                        						dungchoNoSelected = "8";
                        					}               	

                      					}
                        				
                        				if(rstkkt_no.getString("DUNGCHOMASCLON").equals("1")){    

                       						if(dungchoNoSelected.length() > 0){
                       							dungchoNoSelected += "," + "9";
                        					}else{
                        						dungchoNoSelected = "9";
                        					}               	

                       					}
                        				if(rstkkt_no.getString("DUNGCHOCHIPHITRATRUOC").equals("1")){    

                       						if(dungchoNoSelected.length() > 0){
                       							dungchoNoSelected += "," + "10";
                        					}else{
                        						dungchoNoSelected = "10";
                        					}               	

                       					}

                        		%>
									<option  selected="selected" value="<%= rstkkt_no.getString("pk_seq")%>"> <%= rstkkt_no.getString("ma") %> -- <%= rstkkt_no.getString("ten") %> </option>
								<%}else{ %>
									<option value="<%= rstkkt_no.getString("pk_seq")%>"> <%= rstkkt_no.getString("ma") %> -- <%= rstkkt_no.getString("ten") %> </option>
								<%} %>
                        	<%  } %> 
								 </select>
								 
							</TD>
					 
                       		<TD  align="left" >
                        		<select style="width: 150px"  name="TaiKhoanCoIds" id="TaiKhoanCoIds_<%=i %>" onchange="submitform();" >
									<option value  = " "></option>
                        		<%	

                        		dungchoCoSelected = "";
                        		
								
                        		rstkkt_co.beforeFirst();
                        		while (rstkkt_co.next())
                        		{  
                        			if(TaiKhoanCoIds[i].indexOf(rstkkt_co.getString("pk_seq"))>=0 ){
                        				
                        				if(rstkkt_co.getString("DUNGCHOKHO").equals("1")){

                       						if(dungchoCoSelected.length() > 0){
                       							dungchoCoSelected += "," + "1";
                        					}else{
                        						dungchoCoSelected = "1";
                        					}               	

                       					}
                        				if(rstkkt_co.getString("DUNGCHONCC").equals("1")){

                       						if(dungchoCoSelected.length() > 0){
                       							dungchoCoSelected += "," + "2";
                        					}else{
                        						dungchoCoSelected = "2";
                        					}               	

                       					}
                        				if(rstkkt_co.getString("DUNGCHONGANHANG").equals("1")){

                       						if(dungchoCoSelected.length() > 0){
                       							dungchoCoSelected += "," + "3";
                        					}else{
                        						dungchoCoSelected = "3";
                        					}               	

                        				}
                        				if(rstkkt_co.getString("DUNGCHOTAISAN").equals("1")){

                       						if(dungchoCoSelected.length() > 0){
                       							dungchoCoSelected += "," + "4";
                        					}else{
                        						dungchoCoSelected = "4";
                        					}               	

                       					}
                        				if(rstkkt_co.getString("DUNGCHOKHACHHANG").equals("1")){    

                       						if(dungchoCoSelected.length() > 0){
                       							dungchoCoSelected += "," + "5";
                        					}else{
                        						dungchoCoSelected = "5";
                        					}               	

                       					}
                        				if(rstkkt_co.getString("COTTCHIPHI").equals("1")){    

                       						if(dungchoCoSelected.length() > 0){
                       							dungchoCoSelected += "," + "6";
                        					}else{
                        						dungchoCoSelected = "6";
                        					}               	

                       					}
                        				if(rstkkt_co.getString("DUNGCHONHANVIEN").equals("1")){    

                       						if(dungchoCoSelected.length() > 0){
                       							dungchoCoSelected += "," + "7";
                        					}else{
                        						dungchoCoSelected = "7";
                        					}               	

                       					}
                        				
                        				if(rstkkt_co.getString("DUNGCHODOITUONGKHAC").equals("1")){    

                       						if(dungchoCoSelected.length() > 0){
                       							dungchoCoSelected += "," + "8";
                        					}else{
                        						dungchoCoSelected = "8";
                        					}               	

                      					}    	
                        				
                        				if(rstkkt_co.getString("DUNGCHOMASCLON").equals("1")){    

                       						if(dungchoCoSelected.length() > 0){
                       							dungchoCoSelected += "," + "9";
                        					}else{
                        						dungchoCoSelected = "9";
                        					}               	

                       					}
                        				if(rstkkt_co.getString("DUNGCHOCHIPHITRATRUOC").equals("1")){    

                       						if(dungchoCoSelected.length() > 0){
                       							dungchoCoSelected += "," + "10";
                        					}else{
                        						dungchoCoSelected = "10";
                        					}               	

                       					}

                        		%>
									<option  selected="selected" value="<%= rstkkt_co.getString("pk_seq")%>"> <%= rstkkt_co.getString("ma") %> -- <%= rstkkt_co.getString("ten") %> </option>
								<%}else{ %>
									<option value="<%= rstkkt_co.getString("pk_seq")%>"> <%= rstkkt_co.getString("ma") %> -- <%= rstkkt_co.getString("ten") %> </option>
								<%} %>
                        	<%} %> 
								 </select>
								 
							</TD>
							<%if(!tienTe.equals("100000") && !tienTe.equals("") ){ %>
								<td align="center">
								  <input style="text-align: right;width: 80px;" type="text" name="SotienNT"  id="SotienNT_<%=i %>" value='<%= SotienNT[i] == null?"0":formatter2.format(Double.parseDouble(SotienNT[i].replaceAll(",", "")))  %>' onkeypress="return keypress(event);" onChange = "dinhdangNT(<%= i %>);"/>
                        	 
								</td>
								
							<% }%>
							
							<TD align="center" >

                        	 <input style="text-align: right;width: 80px;" type="text" name="Sotien"  id="Sotien_<%=i %>" value='<%= Sotien[i] == null?"0":formatter.format(Double.parseDouble(Sotien[i].replaceAll(",", ""))) %>' onkeypress="return keypress(event);" onChange = "Dinhdang(<%= i %>);"/>
                        	 
                        	 <a href="" id="hoadon_<%= pk_seq%>" rel="subcontent_<%= pk_seq %>">&nbsp; 
                        	 	<img alt="Thông tin hóa đơn" src="../images/vitriluu.png">
                        	 </a>
							
							 <DIV id="subcontent_<%= pk_seq %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                             background-color: white; width: 700px; max-height:300px; overflow:scroll; padding: 4px; z-index: 1;"> 							 
			           	 	  <table width="1500px" align="center">
			           	 	  	 <tr>
			                       <th width="80px" style="font-size: 11px">Mẫu HĐ</th>
			                            <th width="70px" style="font-size: 11px">Ký hiệu</th>
			                            <th width="80px" style="font-size: 11px">Số HĐ</th>
			                            <th width="70px" style="font-size: 11px">Ngày HĐ</th>
			                            <th width="170px" style="font-size: 11px">Tên NCC</th>
			                            <th width="170px" style="font-size: 11px">Địa chỉ</th>
			                            <th width="90px" style="font-size: 11px">MST</th>
			                            <th width="100px" style="font-size: 11px">Tiền hàng</th>
			                            <th width="100px" style="font-size: 11px">Thuế suất</th>
			                            <th width="100px" style="font-size: 11px">Tiền thuế</th>
			                            <th width="100px" style="font-size: 11px">Cộng</th>
			                            <th width="100px" style="font-size: 11px">Ghi chú</th>
					             </tr>
					             <% for(int n = 0; n < 30; n++){ %>
					             	<tr>
					             		<td>
						             		 <% 
						             		 	String temID = pk_seq +"__" +n;
						             		 	if ( btth_mauhd.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdMAU"  value="<%= btth_mauhd.get( temID ) %>" onchange="upperLetters(this);" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdMAU"  value='' onchange="upperLetters(this);">				          				
						             		<%} %>
					             		</td>
					             		<td>
					             			 <% 
						             		 	if ( btth_kyhieuhd.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdKYHIEU"  value="<%= btth_kyhieuhd.get( temID ) %>" onchange="upperLetters(this);" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdKYHIEU"  value='' onchange="upperLetters(this);" >				          				
						             		<%} %>						             		
					             		</td>
					             		<td>
					             			 <% 
						             		 	if ( btth_sohd.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdSOHOADON"  value="<%= btth_sohd.get( temID ) %>" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdSOHOADON"  value='' >				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_ngayhd.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdNGAYHD"  value="<%= btth_ngayhd.get( temID ) %>" class="days">				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdNGAYHD"  value='' class="days">				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_tenNCChd.get(temID) != null ) { %>	
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTENNCC" id="<%= temID %>_hdTENNCC"  value="<%= btth_tenNCChd.get( temID ) %>" >			          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTENNCC" id="<%= temID %>_hdTENNCC" value='' >				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_diachi.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdDIACHI" id="<%= temID %>_hdDIACHI"  value="<%= btth_diachi.get( temID ) %>" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdDIACHI" id="<%= temID %>_hdDIACHI" value='' >				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_msthd.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdMST"  id="<%= temID %>_hdMST" value="<%= btth_msthd.get( temID ) %>"   >		 <!-- onkeyup="ajax_showOptions(this,'masothue',event)" AUTOCOMPLETE="off" --> 		          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdMST"  id="<%= temID %>_hdMST" value='' >				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_tienhanghd.get(temID) != null && btth_tienhanghd.get(temID).trim().length() > 0 ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTIENHANG"  id="<%= temID %>_hdTIENHANG"  value="<%= formatter.format(Double.parseDouble(btth_tienhanghd.get( temID ))) %>" onkeypress="return keypress(event);"  onChange="tinhthue()" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTIENHANG"  id="<%= temID %>_hdTIENHANG" value='' onkeypress="return keypress(event);"  onChange="tinhthue()" >				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_thuesuathd.get(temID) != null && btth_thuesuathd.get(temID).trim().length() > 0 ) { %> 
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTHUESUAT" id="<%= temID %>_hdTHUESUAT"  value="<%= formatter.format(Double.parseDouble(btth_thuesuathd.get( temID ))) %>" onkeypress="return keypress(event);" onChange="tinhthue2()" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTHUESUAT" id="<%= temID %>_hdTHUESUAT"  value='' onkeypress="return keypress(event);" onchange="tinhthue2()" >				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_tienthuehd.get(temID) != null && btth_tienthuehd.get(temID).trim().length() > 0 ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTIENTHUE" id="<%= temID %>_hdTIENTHUE"  value="<%= formatter.format(Double.parseDouble(btth_tienthuehd.get( temID )))  %>" onkeypress="return keypress(event);" onChange="tinhthue3()" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTIENTHUE" id="<%= temID %>_hdTIENTHUE" value='' onkeypress="return keypress(event);" onchange="tinhthue3()" >				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_conghd.get(temID) != null && btth_conghd.get(temID).trim().length() > 0 ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdCONG" id="<%= temID %>_hdCONG"  value="<%= formatter.format(Double.parseDouble(btth_conghd.get( temID )))  %>" onkeypress="return keypress(event);" readonly="readonly" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdCONG" id="<%= temID %>_hdCONG"  value='' onkeypress="return keypress(event);" readonly="readonly"  >				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_ghichuhd.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdGHICHU"  value="<%= btth_ghichuhd.get( temID ) %>" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdGHICHU"  value='' >				          				
						             		<%} %>	
					             		</td>	
					             	</tr>
					             <%} %>
					             
					           </table>
					           <div align="right">
		                     		<label style="color: red" ></label>
		                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                     		<a href="javascript:dropdowncontent.hidediv('subcontent_<%= pk_seq %>')" > Đóng lại </a>
		                       </div>
					           </DIV> 
					           
					           <script type="text/javascript">
						            	dropdowncontent.init('hoadon_<%= pk_seq  %>', "right-top", 300, "click");
						       </script>
						              			
    	                    </TD>
                        	<TD align="center" >
                        	 <input style="text-align: right;" type="text" name="dg"  id="Diengiai_<%=i %>" value='<%= dg[i] %>' />
                        	</TD>

<!--                         	<TD align="center"  > -->
<%--                         		<select style="width: 150px;" name = "dcNoIds" id = "dcNoIds_<%=i %>" > --%>
                        	          
<%--                      		  <% System.out.println("DungchoNoSelected: " + dungchoNoSelected); %>  --%>
                       
                       		             		
<!-- 									<option value="">&nbsp;</option> -->
<%-- 							<% --%>
<!-- 								dc_noRs = btthBean.getDoituong_Rs(dungchoNoSelected, "no", pk_seq); -->
<!-- 								String selected = ""; -->
<!-- 								if(dc_noRs != null){ -->
<!-- 									dc_noRs.beforeFirst(); -->
<!-- 									while(dc_noRs.next()){  -->
<!-- 										if(!(dc_noRs.getString("LOAI") + "," + dc_noRs.getString("PK_SEQ")).equals(dc_noIds[i])){ -->
<!-- 											selected = ""; -->
<!-- 										}else{ -->
<!-- 											selected = "selected";	 -->
<!-- 										} -->
<!-- 									%> -->
											
<%-- 									<option   <%= selected %> value = "<%= dc_noRs.getString("LOAI") + "," + dc_noRs.getString("PK_SEQ")%>" > <%= dc_noRs.getString("MA") %> -- <%= dc_noRs.getString("TEN") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									dc_noRs.close(); -->
<!-- 								} 	%> -->
							
						
<!-- 							</select> -->
					
<!-- 						</TD>	 -->
								
						 
<!--                        	<TD align="center"  > -->
<%--                         		<select    style="width: 150px;" name="dcCoIds" id="dcCoIds_<%=i %>" > --%>
                        	          
<%--                      		  <% 	System.out.println("dungchoCoSelected: " + dungchoCoSelected); %>              		 --%>
<!-- 								<option   value="">&nbsp;</option> -->
<%-- 							<% --%>
<!-- 								dc_coRs = btthBean.getDoituong_Rs(dungchoCoSelected,"co", pk_seq); -->

<!-- 								if(dc_coRs != null){ -->
<!-- 									dc_coRs.beforeFirst(); -->
<!-- 									while(dc_coRs.next()){  -->
<!-- 										if(!(dc_coRs.getString("LOAI") + "," + dc_coRs.getString("PK_SEQ")).equals(dc_coIds[i])){ -->
<!-- 											selected = ""; -->
<!-- 										}else{ -->
<!-- 											selected = "selected";	 -->
<!-- 										} -->
<!-- 									%> -->
											
<%-- 									<option   <%= selected %> value = "<%= dc_coRs.getString("LOAI") + "," + dc_coRs.getString("PK_SEQ")%>" > <%= dc_coRs.getString("MA") %> -- <%= dc_coRs.getString("TEN") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									dc_coRs.close(); -->
<!-- 								} 	%> -->
					   
<!-- 							</select> -->
					
<!-- 						</TD>							 -->

								<TD  align="center" >
									<input type="text" name = "dcNoTens" id = "dcNoTens_<%=i %>"  value='<%= dc_noTens[i] %>' style="width:200px;"/>
									<input type="hidden" name = "dcNoIds" id = "dcNoIds_<%=i %>"  value='<%= dc_noIds[i] %>'/>
									<script type="text/javascript">
										jQuery(function()
											{		
												$("#dcNoTens_<%=i %>").autocomplete("Doituongno_BTTH.jsp?&loaidoituong=<%=dungchoNoSelected %>&taikhoan=<%=TaiKhoanNoIds[i]%>");
											});	
									</script>	
								</TD>
                        		<TD  align="center">
                        			<input type="text" name = "dcCoTens" id = "dcCoTens_<%=i %>" value='<%= dc_coTens[i] %>' style="width:200px;"/>
									<input type="hidden" name = "dcCoIds" id = "dcCoIds_<%=i %>" value='<%= dc_coIds[i] %>'/>			
									<script type="text/javascript">
										jQuery(function()
											{		
												$("#dcCoTens_<%=i %>").autocomplete("Doituongno_BTTH.jsp?&loaidoituong=<%=dungchoCoSelected %>&taikhoan=<%=TaiKhoanCoIds[i]%>");
											});	
									</script>	
								</TD>
                       		
                        <TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="TtcpNoIds" id="TtcpNoId_<%=i %>" >
							<option value="">&nbsp;</option>
                        	<% 	
                        	
                        	String tkNoId = (btthBean.getTaiKhoanNoIds())[i] == null?"":(btthBean.getTaiKhoanNoIds())[i];
                        	ResultSet rsttcpNo = btthBean.getTrungTamChiPhiNoRs(tkNoId);
                        	
                        	if(rsttcpNo != null ){
                        			
                        		rsttcpNo.beforeFirst();
                        		while (rsttcpNo.next())
                        		{  
	                         		if(TtcpNoId[i].equals(rsttcpNo.getString("pk_seq")))  {
	                        		%>
									<option selected="selected" value="<%=rsttcpNo.getString("pk_seq")%>"> <%=rsttcpNo.getString("ma") %> --<%=rsttcpNo.getString("ten") %> </option>
								<%	}else{ %>
									<option value="<%=rsttcpNo.getString("pk_seq")%>"> <%=rsttcpNo.getString("ma") %> --<%=rsttcpNo.getString("ten") %> </option>
								<%	} %>
	                         <%  }
                        		rsttcpNo.close();
                       		}%>
							 </select>                        		 
						 
 						</TD>
                   		<TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="TtcpCoIds" id="TtcpCoId_<%=i %>" >
							<option value="">&nbsp;</option>
                        	<% 	
                        	String tkCoId = (btthBean.getTaiKhoanCoIds())[i] == null?"":(btthBean.getTaiKhoanCoIds())[i];
                        	ResultSet rsttcpCo = btthBean.getTrungTamChiPhiCoRs(tkCoId);
                        	if(rsttcpCo != null ){
                        			
                        		rsttcpCo.beforeFirst();
                        		while (rsttcpCo.next())
                        		{  
	                         		if(TtcpCoId[i].equals(rsttcpCo.getString("pk_seq")))  {
	                        		%>
									<option selected="selected" value="<%=rsttcpCo.getString("pk_seq")%>"> <%=rsttcpCo.getString("ma") %> --<%=rsttcpCo.getString("ten") %> </option>
								<%	}else{ %>
									<option value="<%=rsttcpCo.getString("pk_seq")%>"> <%=rsttcpCo.getString("ma") %> --<%=rsttcpCo.getString("ten") %> </option>
								<%	} %>
	                         <%  }
                        		rsttcpNo.close();
                       		}%>
							 </select>                        		 
						 
 						</TD> 							

               			<TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="kbhIds" id="kbhIds_<%=i %>" >
							<option value=""></option>
                        	<% 	
                        	if(kbh != null ){
                        			
                        		kbh.beforeFirst();
                        		while (kbh.next())
                        		{  
	                         		if(kbhIds[i].equals(kbh.getString("pk_seq")))  {
	                        		%>
									<option selected="selected" value="<%=kbh.getString("pk_seq")%>"> <%=kbh.getString("ten") %> </option>
								<%	}else{ %>
									<option value="<%=kbh.getString("pk_seq")%>"> <%=kbh.getString("ten") %> </option>
								<%	} %>
	                         <%  }
                       		}%>
							 </select>                        		 
						 
 						</TD> 							

						
                        
                        <TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="mavvIds" id="mavvIds_<%=i %>" >
							<option value=""></option>
                        	<% 	
                        	if(mavv != null ){
                        			
                        		mavv.beforeFirst();
                        		while (mavv.next())
                        		{                          			
	                         		if(mavvIds[i].equals(mavv.getString("pk_seq")))  {
	                        		%>
									<option selected="selected" value="<%=mavv.getString("pk_seq")%>"> <%=mavv.getString("ten") %> </option>
								<%	}else{ %>
									<option value="<%=mavv.getString("pk_seq")%>"> <%=mavv.getString("ten") %> </option>
								<%	} %>
	                         <%  }
                       		}%>
							 </select>                        		 
						 
 						</TD> 	
 						
 						 <TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="diabanIds" id="diabanIds_<%=i %>" >
							<option value=""></option>
                        	<% 	
                        	if(diaban != null ){
                        			
                        		diaban.beforeFirst();
                        		while (diaban.next())
                        		{                         			
	                         		if(diabanIds[i].equals(diaban.getString("pk_seq")))  {
	                        		%>
									<option selected="selected" value="<%=diaban.getString("pk_seq")%>"> <%=diaban.getString("ten") %> </option>
								<%	}else{ %>
									<option value="<%=diaban.getString("pk_seq")%>"> <%=diaban.getString("ten") %> </option>
								<%	} %>
	                         <%  }
                       		}%>
							 </select>                        		 
						 
 						</TD> 	
 								
                        <TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="tinhthanhIds" id="tinhthanhIds_<%=i %>" >
							<option value=""></option>
                        	<% 	
                        	if(tinhthanh != null ){
                        			
                        		tinhthanh.beforeFirst();
                        		while (tinhthanh.next())
                        		{                         			
	                         		if(tinhthanhIds[i].equals(tinhthanh.getString("pk_seq")))  {
	                        		%>
									<option selected="selected" value="<%=tinhthanh.getString("pk_seq")%>"> <%=tinhthanh.getString("ten") %> </option>
								<%	}else{ %>
									<option value="<%=tinhthanh.getString("pk_seq")%>"> <%=tinhthanh.getString("ten") %> </option>
								<%	} %>
	                         <%  }
                       		}%>
							 </select>                        		 
						 
 						</TD>
 						
 						<TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="benhvienIds" id="benhvienIds_<%=i %>" >
							<option value=""></option>
                        	<% 	
                        	if(benhvien != null ){
                        			
                        		benhvien.beforeFirst();
                        		while (benhvien.next())
                        		{                         			
	                         		if(benhvienIds[i].equals(benhvien.getString("pk_seq")))  {
	                        		%>
									<option selected="selected" value="<%=benhvien.getString("pk_seq")%>"> <%=benhvien.getString("ten") %> </option>
								<%	}else{ %>
									<option value="<%=benhvien.getString("pk_seq")%>"> <%=benhvien.getString("ten") %> </option>
								<%	} %>
	                         <%  }
                       		}%>
							 </select>                        		 
						 
 						</TD>
 						
 						 <%-- <TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="sanphamIds" id="sanphamIds_<%=i %>" >
							<option value=""></option>
                        	<% 	
                        	if(sanpham != null ){
                        			
                        		sanpham.beforeFirst();
                        		while (sanpham.next())
                        		{                         			
	                         		if(sanphamIds[i].equals(sanpham.getString("pk_seq")))  {
	                        		%>
									<option selected="selected" value="<%=sanpham.getString("pk_seq")%>"> <%=sanpham.getString("ten") %> </option>
								<%	}else{ %>
									<option value="<%=sanpham.getString("pk_seq")%>"> <%=sanpham.getString("ten") %> </option>
								<%	} %>
	                         <%  }
                       		}%>
							 </select>                        		 
						 
 						</TD> --%>
 							<% 
 						if( i < btthBean.getTaiKhoanCoIds().length )
 	 					{
 							String sohieutaikhoanco = btthBean.getSoHieu(btthBean.getTaiKhoanCoIds()[i],btthBean.getDb() );
 							String sohieutaikhoanno = btthBean.getSoHieu(btthBean.getTaiKhoanNoIds()[i],btthBean.getDb()); %>
 							<%if(sohieutaikhoanco.indexOf("64") >= 0 || sohieutaikhoanno.indexOf("64") >= 0 ){%>
 							<!-- Start Sản phẩm kho -->
											<td align="right">
				           	 					<a href="" id="sanPhamKho_<%= pk_seq %>" rel="subcontentSanPhamKho_<%= pk_seq %>">
					           	 				       <img alt="Tên sản phẩm hóa đơn" src="../images/vitriluu.png"></a>
					           	 				<DIV  id="subcontentSanPhamKho_<%= pk_seq %>"  style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
								                             background-color: white; width: 600px; padding: 4px;  max-height: 200px; overflow: auto;" >
								                              
								                     <table width="90%" align="center">
								                        <tr>
								                        <!--  <th width="50px">Pk_seq </th> -->
								                            
								                             <th width="50px">Mã </th>
								                            <th width="100px">Tên </th>
								                            <th width="10px" align="center">(%) Phân bổ</th>
								                        </tr>
						                            	<%
						                            	int dem = 0;
						                            	int kt =0;
							                        		if(rsSanPham != null)
							                        		{
							                        			rsSanPham.beforeFirst();
							                        			
							                        			while(rsSanPham.next())
							                        			{  
							                        				%>
							                        			
							                        			<%String temID = String.valueOf(pk_seq); %>
							                        			<%System.out.println("===Ma : " +rsSanPham.getString("PK_SEQ")+ "_" + temID); %>
							                        			<%if(spMaSP.get(temID + "_" + rsSanPham.getString("PK_SEQ")) != null) {%>
							                        				<%if(spMaSP.get(temID + "_" + rsSanPham.getString("PK_SEQ")).equals(rsSanPham.getString("PK_SEQ"))) {%>
							                        					<tr align="center" >
																			<td>
																				<input type="text" name="<%= temID + "_spMA" %>" id="maSanPhamKho"  style="width: 120px; border-radius:4px; height: 20px;" value="<%=rsSanPham.getString("MA") %>"  onkeyup="ajax_showOptions(this,'spbuttoan',event)" AUTOCOMPLETE="off" >
																				<input type="hidden" name="<%= temID+"_spPK_Seq" %>" style="width: 150px; border-radius:4px; height: 20px;" value="<%=rsSanPham.getString("PK_SEQ") %>"  >
																		
																			</td>
																			<td>
																				<input type="text" name="<%= temID + "_spTEN" %>" style="width: 200px; border-radius:4px; height: 20px;" value="<%=rsSanPham.getString("TEN") %>"  readonly >
																			</td>

																			<td>
							                        							<input type="text" name="<%= temID + "_spPhanTram"%>" style="width: 90px; border-radius:4px; height: 20px;text-align:right" value="<%= spPhanTram.get(temID + "_" + rsSanPham.getString("PK_SEQ")) %>" >
							                        						</td>
							                        				</tr>
							                        				<%} %>							                   
							                        			
							                        		 <% dem++;}
							                        			} }%>
							                        		<%
						                        			for (int n = 0; n < 15; n++)
						                        			{  %>
						                        				<% String temID = String.valueOf(pk_seq); %>
							                        			<tr align="center" >
																	<td>
																		<input type="text" name="<%= temID + "_spMA" %>" id="maSanPhamKho"  style="width: 120px; border-radius:4px; height: 20px;" value=""  onkeyup="ajax_showOptions(this,'spbuttoan',event)" AUTOCOMPLETE="off" >
																		<input type="hidden" name="<%= temID+"_spPK_Seq" %>" style="width: 150px; border-radius:4px; height: 20px;" value=""  >
																		
																	</td>
																	<td>
																		<input type="text" name="<%= temID + "_spTEN" %>" style="width: 200px; border-radius:4px; height: 20px;" value=""  readonly >
																	</td>

																	<td>
							                        					<input type="text" name="<%= temID + "_spPhanTram"%>" style="width: 90px; border-radius:4px; height: 20px;text-align:right" value="0" >
							                        				</td>
							                        			</tr>
							                        			
							                        			<% } %>
								                    </table>
								                     
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentSanPhamKho_<%= pk_seq %>')">Hoàn tất</a>
								                     </div>
								                     
								                </DIV>
								                  <script type="text/javascript">
						            					dropdowncontent.init('sanPhamKho_<%= pk_seq  %>', "left-top", 300, "click");
						       					</script>
				           	 				</td>
<!-- 											End Sản phẩm kho -->			
 						<%} }%>	
					</TR>
                	 <%  pk_seq ++; } 
					
					
					} // KẾT THÚC VÒNG LẶP FOR HIỆN RA CÁC DÒNG ĐÃ NHẬP%>

			 
                 <% 
                 int bien = stt+1;
                 for(int i = stt; i< bien; i++){ %>
						<TR>
							<%
								
								stt++;
                        	 
                        	%>                        	
                          		<TD  align="left" >
                          			<input style="text-align: right; width: 100px;" readonly="readonly" type="hidden" name="pk_seq"  id="pk_seq_<%= pk_seq %>" value='<%= pk_seq %>' />
                        			<select style="width: 150px"  name="TaiKhoanNoIds" id="TaiKhoanNoIds_<%=i %>" onchange="submitform();" >
									<option value = " "></option>
                        		<%	

                        		dungchoNoSelected = "0";
                        		
								
                        		rstkkt_no.beforeFirst();
                        		while (rstkkt_no.next())
                        		{  
                        				if(rstkkt_no.getString("DUNGCHOKHO").equals("1")){
                        					dungchoNoSelected = "1";
                       					}else if(rstkkt_no.getString("DUNGCHONCC").equals("1")){
                       						dungchoNoSelected = "2";
                       					}else if(rstkkt_no.getString("DUNGCHONGANHANG").equals("1")){
                       						dungchoNoSelected = "3";
                       					}else if(rstkkt_no.getString("DUNGCHOTAISAN").equals("1")){
                       						dungchoNoSelected = "4";
                       					}else if(rstkkt_no.getString("DUNGCHOKHACHHANG").equals("1")){    
                       						dungchoNoSelected = "5";
                       					}else if(rstkkt_no.getString("COTTCHIPHI").equals("1")){    
                       						dungchoNoSelected = "6";
                       					}else if(rstkkt_no.getString("DUNGCHONHANVIEN").equals("1")){    
                       						dungchoNoSelected = "7";
                       					}else if(rstkkt_no.getString("DUNGCHODOITUONGKHAC").equals("1")){    
                       						dungchoNoSelected = "8";
                       					}else if(rstkkt_no.getString("DUNGCHOMASCLON").equals("1")){    
                       						dungchoNoSelected = "9";
                       					}
                       					else if(rstkkt_no.getString("DUNGCHOCHIPHITRATRUOC").equals("1")){    
                       						dungchoNoSelected = "10";
                       					}%>
                        				
 									<option value="<%= rstkkt_no.getString("pk_seq")%>"> <%= rstkkt_no.getString("ma") %> -- <%= rstkkt_no.getString("ten") %> </option>
                        	<%  }
                        	
                        	%> 
								 </select>
								 
							</TD>
					 
                       		<TD  align="left" >
                        		
                        		
                        		<select style="width: 150px"  name="TaiKhoanCoIds" id="TaiKhoanCoIds_<%=i %>" onchange="submitform();" >
									<option value= " " ></option>
                        		<%	

                        		dungchoCoSelected = "0";
                        		
								rstkkt_co.beforeFirst();
                   				while (rstkkt_co.next())
                        		{  
                        			
                        				
                        				if(rstkkt_co.getString("DUNGCHOKHO").equals("1")){
                        					dungchoCoSelected = "1";
                       					}else if(rstkkt_co.getString("DUNGCHONCC").equals("1")){
                       						dungchoCoSelected = "2";
                       					}else if(rstkkt_co.getString("DUNGCHONGANHANG").equals("1")){
                       						dungchoCoSelected = "3";
                       					}else if(rstkkt_co.getString("DUNGCHOTAISAN").equals("1")){
                       						dungchoCoSelected = "4";
                       					}else if(rstkkt_co.getString("DUNGCHOKHACHHANG").equals("1")){    
                       						dungchoCoSelected = "5";
                       					}else if(rstkkt_co.getString("COTTCHIPHI").equals("1")){    
                       						dungchoCoSelected = "6";
                       					}else if(rstkkt_co.getString("DUNGCHONHANVIEN").equals("1")){    
                       						dungchoCoSelected = "7";
                       					}else if(rstkkt_co.getString("DUNGCHODOITUONGKHAC").equals("1")){    
                       						dungchoCoSelected = "8";
                       					}
                        				else if(rstkkt_co.getString("DUNGCHOMASCLON").equals("1")){    
                       						dungchoCoSelected = "9";
                       					}
                       					else if(rstkkt_co.getString("DUNGCHOCHIPHITRATRUOC").equals("1")){    
                       						dungchoCoSelected = "10";
                       					}
                        				
                        		%>
									<option value="<%= rstkkt_co.getString("pk_seq")%>"> <%= rstkkt_co.getString("ma") %> -- <%= rstkkt_co.getString("ten") %> </option>
                        	<%  	
                        		
                        		}%> 
								 </select>
								 
							</TD>
							<%if(!tienTe.equals("100000") && !tienTe.equals("")){ %>
							<td align="center">
								  <input style="text-align:right;width: 80px;" type="text" name="SotienNT"  id="Sotien_<%=i %>" value='<%= 0%>' onkeypress="return keypress(event);" onChange = "dinhdangNT(<%= i %>);"/>
                        	 
							</td>
							<%} %>
							<TD align="center" >
                        	  <input style="text-align:right;width: 100px;" type="text" name="Sotien"  id="Sotien_<%=i %>" value='' onkeypress="return keypress(event);" onChange = "dinhdangNT(<%= i %>);"/>
                        	 
                        	 <a href="" id="hoadon_<%= pk_seq%>" rel="subcontent_<%= pk_seq %>">&nbsp; 
                        	 	<img alt="Thông tin hóa đơn" src="../images/vitriluu.png">
                        	 </a>
							
							 <DIV id="subcontent_<%= pk_seq %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					           background-color: white; width: 700px; max-height:300px; overflow:auto; padding: 4px;z-index: 1;"> 							 
			           	 	  <table width="1500px" align="center">
			           	 	  	 <tr>
			                          <th width="80px" style="font-size: 11px">Mẫu HĐ</th>
			                            <th width="70px" style="font-size: 11px">Ký hiệu</th>
			                            <th width="80px" style="font-size: 11px">Số HĐ</th>
			                            <th width="70px" style="font-size: 11px">Ngày HĐ</th>
			                            <th width="170px" style="font-size: 11px">Tên NCC</th>
			                            <th width="170px" style="font-size: 11px">Địa chỉ</th>
			                            <th width="90px" style="font-size: 11px">MST</th>
			                            <th width="100px" style="font-size: 11px">Tiền hàng</th>
			                            <th width="100px" style="font-size: 11px">Thuế suất</th>
			                            <th width="100px" style="font-size: 11px">Tiền thuế</th>
			                            <th width="100px" style="font-size: 11px">Cộng</th>
			                            <th width="100px" style="font-size: 11px">Ghi chú</th>
					             </tr>
					             <% for(int n = 0; n < 30; n++){ %>
					             	<tr>
					             		<td>
						             		 <% 
						             		 	String temID = pk_seq +"__" +n;
						             		 	if ( btth_mauhd.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdMAU"  value="<%= btth_mauhd.get( temID ) %>" onchange="upperLetters(this);" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdMAU"  value='' onchange="upperLetters(this);" >				          				
						             		<%} %>
					             		</td>
					             		<td>
					             			 <% 
						             		 	if ( btth_kyhieuhd.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdKYHIEU"  value="<%= btth_kyhieuhd.get( temID ) %>" onchange="upperLetters(this);" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdKYHIEU"  value='' onchange="upperLetters(this);" >				          				
						             		<%} %>						             		
					             		</td>
					             		<td>
					             			 <% 
						             		 	if ( btth_sohd.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdSOHOADON"  value="<%= btth_sohd.get( temID ) %>" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdSOHOADON"  value='' >				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_ngayhd.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdNGAYHD"  value="<%= btth_ngayhd.get( temID ) %>" class="days">				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdNGAYHD"  value='' class="days">				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_tenNCChd.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTENNCC"  id="<%= temID %>_hdTENNCC" value="<%= btth_tenNCChd.get( temID ) %>" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTENNCC"  id="<%= temID %>_hdTENNCC" value='' >				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_diachi.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdDIACHI"  id="<%= temID %>_hdDIACHI" value="<%= btth_diachi.get( temID ) %>" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdDIACHI"  id="<%= temID %>_hdDIACHI" value='' >				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_msthd.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdMST" id="<%= temID %>_hdMST" value="<%= btth_msthd.get( temID ) %>"  >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdMST" id="<%= temID %>_hdMST"   value=''  >				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_tienhanghd.get(temID) != null && btth_tienhanghd.get(temID).trim().length() > 0 ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTIENHANG" id="<%= temID %>_hdTIENHANG"  value="<%= formatter.format(Double.parseDouble(btth_tienhanghd.get( temID ))) %>" onkeypress="return keypress(event);" onchange="tinhthue()" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTIENHANG" id="<%= temID %>_hdTIENHANG" value='' onkeypress="return keypress(event);" onchange="tinhthue()">				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_thuesuathd.get(temID) != null && btth_thuesuathd.get(temID).trim().length() > 0  ) { %> 
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTHUESUAT" id="<%= temID %>_hdTHUESUAT" value="<%= formatter.format(Double.parseDouble(btth_thuesuathd.get( temID ))) %>" onkeypress="return keypress(event);" onchange="tinhthue2()">				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTHUESUAT" id="<%= temID %>_hdTHUESUAT"  value='' onkeypress="return keypress(event);" onchange="tinhthue2()">				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% if ( btth_tienthuehd.get(temID) != null && btth_tienthuehd.get(temID).trim().length() > 0  ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTIENTHUE" id="<%= temID %>_hdTIENTHUE" value="<%= formatter.format(Double.parseDouble(btth_tienthuehd.get( temID )))  %>" onkeypress="return keypress(event);" onchange="tinhthue3()" >				          				             		
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTIENTHUE" id="<%= temID %>_hdTIENTHUE" value="<%= formatter.format(Double.parseDouble(btth_tienthuehd.get( temID )))  %>" onkeypress="return keypress(event);" onchange="tinhthue3()" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTIENTHUE" id="<%= temID %>_hdTIENTHUE" value='' onkeypress="return keypress(event);" onchange="tinhthue3()" >				          				
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdTIENTHUE" id="<%= temID %>_hdTIENTHUE" value='' onkeypress="return keypress(event);" onchange="tinhthue3()" >				          				
						             		<%} %>
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_conghd.get(temID) != null && btth_conghd.get(temID).trim().length() > 0 ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdCONG" id="<%= temID %>_hdCONG" value="<%= formatter.format(Double.parseDouble(btth_conghd.get( temID )))  %>" onkeypress="return keypress(event);" readonly="readonly">				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdCONG" id="<%= temID %>_hdCONG" value='' onkeypress="return keypress(event);" readonly="readonly" >				          				
						             		<%} %>	
					             		</td>
					             		<td>
					             			<% 
						             		 	if ( btth_ghichuhd.get(temID) != null ) { %>
													<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdGHICHU"  id="<%= temID %>_hdGHICHU" value="<%= btth_ghichuhd.get( temID ) %>" >				          				             		
						             		<%	} else { %>
						             				<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_hdGHICHU"  id="<%= temID %>_hdGHICHU" value='' >				          				
						             		<%} %>	
					             		</td>	
					             	</tr>
					             <%} %>
					             
					           </table>
					        
					           <div align="right">
		                     		<label style="color: red" ></label>
		                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                     		<a href="javascript:dropdowncontent.hidediv('subcontent_<%= pk_seq %>')" > Đóng lại </a>
		                       </div>
					            </DIV>
					           
					           <script type="text/javascript">
						            	dropdowncontent.init('hoadon_<%= pk_seq  %>', "right-top", 300, "click");
						       </script>
    	                    </TD>
    	                   
                        	<TD align="center" >
                        	 	<input style="text-align: right;" type="text" name="dg"  id="dg_<%=i %>" value='' />
                        	</TD>

<!--                         	<TD align="center"  > -->
<%--                         		<select    style="width: 150px;" name="dcNoIds" id="dcNoIds_<%=i %>" > --%>
                        	          
<%--                      		  <% 	System.out.println("DungchoNoSelected: " + dungchoNoSelected); --%>
                       
<!--                        	if(dungchoNoSelected.equals("1")){ %>              		 -->
<!-- 								<option   value="">&nbsp;</option> -->
<%-- 							<% --%>
<!-- 								ResultSet spRs = btthBean.getSanpham_NoRs(); -->
<!-- 								String selected = ""; -->
<!-- 								if(spRs != null){ -->
<!-- 									while(spRs.next()){  -->
<!-- 									%> -->
											
<%-- 									<option    value="<%=spRs.getString("PK_SEQ")%>"> <%=spRs.getString("MA") %> --<%= spRs.getString("TEN") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									spRs.close(); -->
<!-- 								} 	%> -->
							
<%-- 						<%}else if(dungchoNoSelected.equals("2")){ %>              		 --%>
<!-- 								<option   value="">&nbsp;</option> -->
<%-- 						<% --%>
<!-- 								dc_noRs = btthBean.getNcc_NoRs(); -->
<!-- 								String selected = ""; -->
<!-- 								if(dc_noRs != null){ -->
<!-- 									while(dc_noRs.next()){  -->
										
<!-- 									%> -->
											
<%-- 									<option  value="<%=dc_noRs.getString("PK_SEQ")%>"> <%=dc_noRs.getString("MA") %> --<%= dc_noRs.getString("TEN") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									dc_noRs.close(); -->
<!-- 								} 	%> -->
<%-- 						<%}else  if(dungchoNoSelected.equals("3")){ %>              		 --%>
<!-- 								<option   value="">&nbsp;</option> -->
<%-- 						<% --%>
<!-- 								dc_noRs = btthBean.getNganhang_NoRs(); -->
<!-- 								String selected = ""; -->
<!-- 								if(dc_noRs != null){ -->
<!-- 									while(dc_noRs.next()){  -->
								
<!-- 									%> -->
											
<%-- 									<option  value="<%=dc_noRs.getString("PK_SEQ")%>"> <%=dc_noRs.getString("MA") %> --<%= dc_noRs.getString("TEN") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									dc_noRs.close(); -->
<!-- 								} 	%> -->

<%--                         <%}else  if(dungchoNoSelected.equals("4")){ %>              		 --%>
<!-- 								<option   value="">&nbsp;</option> -->
<%-- 						<% --%>
<!-- 								dc_noRs = btthBean.getTaisan_NoRs(); -->
<!-- 								String selected = ""; -->
<!-- 								if(dc_noRs != null){ -->
<!-- 									while(dc_noRs.next()){  -->
										
<!-- 									%> -->
											
<%-- 									<option value="<%=dc_noRs.getString("PK_SEQ")%>"> <%=dc_noRs.getString("MA") %> --<%= dc_noRs.getString("DIENGIAI") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									dc_noRs.close(); -->
<!-- 								} 	%> -->
<%-- 						<%}else if(dungchoNoSelected.equals("5")){ %>              		 --%>
<!-- 								<option   value="">&nbsp;</option> -->
<%-- 						<% --%>
<!-- 								dc_noRs = btthBean.getKhachhang_NoRs(); -->
<!-- 								String selected = ""; -->
<!-- 								if(dc_noRs != null){ -->
<!-- 									while(dc_noRs.next()){  -->
										
<!-- 									%> -->
											
<%-- 									<option  value="<%=dc_noRs.getString("PK_SEQ")%>"> <%=dc_noRs.getString("MA") %> --<%= dc_noRs.getString("TEN") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									dc_noRs.close(); -->
<!-- 								} 	%> -->
<%-- 					   <%} else if(dungchoNoSelected.equals("7")){ %>              		 --%>
<!-- 								<option   value="">&nbsp;</option> -->
<%-- 						<% --%>
<!-- 								dc_noRs = btthBean.getNganhang_NoRs(); -->
<!-- 								String selected = ""; -->
<!-- 								if(dc_noRs != null){ -->
<!-- 									while(dc_noRs.next()){  -->

<!-- 									%> -->
											
<%-- 									<option   value="<%=dc_noRs.getString("PK_SEQ")%>"> <%=dc_noRs.getString("MA") %> --<%= dc_noRs.getString("TEN") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									dc_noRs.close(); -->
<!-- 								} 	%> -->
<%-- 					   <%}else{%> --%>
<!-- 						   <option   value="">&nbsp;</option> -->
<%-- 					   <%}	%> --%>
					   
<!-- 							</select> -->
					
<!-- 							</TD>	 -->
								
						 
<!--                         	<TD align="center"  > -->
<%--                         		<select    style="width: 150px;" name="dcCoIds" id="dcCoIds_<%=i %>" > --%>
                        	          
<%--                      		  <% 	System.out.println("dungchoCoSelected: " + dungchoCoSelected); --%>
                       
<!--                        	if(dungchoCoSelected.equals("1")){ %>              		 -->
<!-- 								<option   value="">&nbsp;</option> -->
<%-- 							<% --%>
<!-- 								ResultSet spRs = btthBean.getSanpham_CoRs(); -->
<!-- 								String selected = ""; -->
<!-- 								if(spRs != null){ -->
<!-- 									while(spRs.next()){  -->
<!-- 									%> -->
											
<%-- 									<option value="<%=spRs.getString("PK_SEQ")%>"> <%=spRs.getString("MA") %> --<%= spRs.getString("TEN") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									spRs.close(); -->
<!-- 								} 	%> -->
							
<%-- 						<%}else if(dungchoCoSelected.equals("2")){ %>              		 --%>
<!-- 								<option   value="">&nbsp;</option> -->
<%-- 						<% --%>
<!-- 								dc_coRs = btthBean.getNcc_CoRs(); -->
<!-- 								String selected = ""; -->
<!-- 								if(dc_coRs != null){ -->
<!-- 									while(dc_coRs.next()){  -->
										
<!-- 									%> -->
											
<%-- 									<option value="<%=dc_coRs.getString("PK_SEQ")%>"> <%=dc_coRs.getString("MA") %> --<%= dc_coRs.getString("TEN") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									dc_coRs.close(); -->
<!-- 								} 	%> -->
<%-- 						<%}else  if(dungchoCoSelected.equals("3")){ %>              		 --%>
<!-- 								<option   value="">&nbsp;</option> -->
<%-- 						<% --%>
<!-- 								dc_coRs = btthBean.getNganhang_CoRs(); -->
<!-- 								String selected = ""; -->
<!-- 								if(dc_coRs != null){ -->
<!-- 									while(dc_coRs.next()){  -->
<!-- 									%> -->
											
<%-- 									<option value="<%=dc_coRs.getString("PK_SEQ")%>"> <%=dc_coRs.getString("MA") %> --<%= dc_coRs.getString("TEN") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									dc_coRs.close(); -->
<!-- 								} 	%> -->

<%--                         <%}else  if(dungchoCoSelected.equals("4")){ %>              		 --%>
<!-- 								<option   value="">&nbsp;</option> -->
<%-- 						<% --%>
<!-- 								dc_coRs = btthBean.getTaisan_CoRs(); -->
<!-- 								String selected = ""; -->
<!-- 								if(dc_coRs != null){ -->
<!-- 									while(dc_coRs.next()){  -->
<!-- 									%> -->
											
<%-- 									<option  value="<%=dc_coRs.getString("PK_SEQ")%>"> <%=dc_coRs.getString("MA") %> --<%= dc_coRs.getString("DIENGIAI") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									dc_coRs.close(); -->
<!-- 								} 	%> -->
<%-- 						<%}else if(dungchoCoSelected.equals("5")){ %>              		 --%>
<!-- 								<option   value="">&nbsp;</option> -->
<%-- 						<% --%>
<!-- 								dc_coRs = btthBean.getKhachhang_CoRs(); -->
<!-- 								String selected = ""; -->
<!-- 								if(dc_coRs != null){ -->
<!-- 									while(dc_coRs.next()){  -->
<!-- 									%> -->
											
<%-- 									<option value="<%=dc_coRs.getString("PK_SEQ")%>"> <%=dc_coRs.getString("MA") %> --<%= dc_coRs.getString("TEN") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									dc_coRs.close(); -->
<!-- 								} 	%> -->
<%-- 					   <%} else if(dungchoCoSelected.equals("7")){ %>              		 --%>
<!-- 								<option   value="">&nbsp;</option> -->
<%-- 						<% --%>
<!-- 								dc_coRs = btthBean.getNganhang_CoRs(); -->
<!-- 								String selected = ""; -->
<!-- 								if(dc_coRs != null){ -->
<!-- 									while(dc_coRs.next()){  -->
<!-- 									%> -->
											
<%-- 									<option value="<%=dc_coRs.getString("PK_SEQ")%>"> <%=dc_coRs.getString("MA") %> --<%= dc_coRs.getString("TEN") %> </option>	 --%>
								
<%-- 						<%			} --%>
<!-- 									dc_coRs.close(); -->
<!-- 								} 	%> -->
<%-- 					   <%}else{%> --%>
<!-- 						   <option   value="">&nbsp;</option> -->
<%-- 					   <%}	%> --%>
					   
<!-- 							</select> -->
					
<!-- 						</TD>							 -->

								<TD  align="center" >
									<input type="text" name = "dcNoTens" id = "dcNoTens_<%=i %>"  value='' style="width:200px;"/>
									<input type="hidden" name = "dcNoIds" id = "dcNoIds_<%=i %>"  value=''/>
														
									<script type="text/javascript">
										jQuery(function()
											{		
												$("#dcNoIds_<%=i %>").autocomplete("Doituongno_BTTH.jsp?&loaidoituong=<%=dungchoNoSelected %>?taikhoan=<%=pk_seq%>");
											});	
										</script>	
								</TD>
                       			
                        		<TD  align="center">
	                        		<input type="text" name = "dcCoTens" id = "dcCoTens_<%=i %>" value='' style="width:200px;"/>
	                        		<input type="hidden" name = "dcCoIds" id = "dcCoIds_<%=i %>" value='' style="width:200px;"/>
									<script type="text/javascript">
									jQuery(function()
										{		
											$("#dcNoIds_<%=i %>").autocomplete("Doituongno_BTTH.jsp?&loaidoituong=<%=dungchoNoSelected %>?taikhoan=<%=pk_seq%>");
										});	
									</script>	
								</TD>
                       		
                        <TD align="center"  >
                        	<select class='select2'  style="width: 150px;"  name="TtcpNoIds" id="TtcpNoIds_<%=i %>" >
							<option value="">&nbsp;</option>

							 </select>                        		 
						 
 						</TD>
                   		<TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="TtcpCoIds" id="TtcpCoIds_<%=i %>" >
							<option value="">&nbsp;</option>
							 </select>                        		 
						 
 						</TD> 							

               			<TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="kbhIds" id="kbhIds_<%=i %>" >
							<option value="">&nbsp;</option>
							 </select>                        		 
							 
 						</TD> 	
 						
					
                        
                        <TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="mavvIds" id="mavvIds_<%=i %>" >
							<option value="">&nbsp;</option>
							 </select>                        		 
							 
 						</TD> 
                        		
                         <TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="diabanIds" id="diabanIds_<%=i %>" >
							<option value="">&nbsp;</option>
							 </select>   
 						</TD> 
 						
 						 <TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="tinhthanhIds" id="diabanIds_<%=i %>" >
							<option value="">&nbsp;</option>
							 </select>  
 						 </TD> 
 						 
 						 <TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="benhvienIds" id="benhvienIds_<%=i %>" >
							<option value="">&nbsp;</option>
							 </select>  
 						 </TD> 
 						<!-- Sản phẩm -->
 						 <%-- <TD align="center"  > 
                        	<select class='select2'  style="width: 150px;"  name="sanphamIds" id="sanphamIds_<%=i %>" >
							<option value="">&nbsp;</option>
							 </select>  
 						 </TD>  --%>
 							 <% 
 						if( i < btthBean.getTaiKhoanCoIds().length )
 						{
 							String sohieutaikhoanco = btthBean.getSoHieu(btthBean.getTaiKhoanCoIds()[i],btthBean.getDb() );
 							String sohieutaikhoanno = btthBean.getSoHieu(btthBean.getTaiKhoanNoIds()[i],btthBean.getDb()); %>
 							 <%if(sohieutaikhoanco.indexOf("64") >= 0 || sohieutaikhoanno.indexOf("64") >= 0 ){%>
 							<!-- Start Sản phẩm kho -->
											<td align="right">
				           	 					<a href="" id="sanPhamKho_<%= pk_seq %>" rel="subcontentSanPhamKho_<%= pk_seq %>">
					           	 				       <img alt="Tên sản phẩm hóa đơn" src="../images/vitriluu.png"></a>
					           	 				<DIV  id="subcontentSanPhamKho_<%= pk_seq %>"  style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
								                             background-color: white; width: 600px; padding: 4px;  max-height: 200px; overflow: auto;" >
								                              
								                     <table width="90%" align="center" cellspacing="2">
								                        <tr>
								                            <th width="50px">Mã </th>
								                            <th width="150px">Tên </th>
								                            <th width="50px" align="center">%Phân bổ</th>
								                        </tr>
						                            	<%
						                        			for (int n = 0; n < 15; n++)
						                        			{  %>
						                        				<%String temID = String.valueOf(pk_seq); %>
							                        			<tr align="center" >
																	<td>
																		<input type="text" name="<%= temID + "_spMA" %>" id="maSanPhamKho"  style="width: 120px; border-radius:4px; height: 20px;" value=""  onkeyup="ajax_showOptions(this,'spbuttoan',event)" AUTOCOMPLETE="off" >
																		<input type="hidden" name="<%= temID+"_spPK_Seq" %>" style="width: 150px; border-radius:4px; height: 20px;" value=""  >
																		
																	</td>
																	<td>
																		<input type="text" name="<%= temID + "_spTEN" %>" style="width: 200px; border-radius:4px; height: 20px;" value=""  readonly >
																	</td>

																	<td>
							                        					<input type="text" name="<%= temID + "_spPhanTram"%>" style="width: 90px; border-radius:4px; height: 20px;text-align:right" value="0" >
							                        				</td>
							                        			</tr>
							                        			
							                        	<% } %>
							                         </table>
								                     
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentSanPhamKho_<%= pk_seq %>')">Hoàn tất</a>
								                     </div>
								                </DIV>
								               <script type="text/javascript">
						            							dropdowncontent.init('sanPhamKho_<%= pk_seq  %>', "left-top", 300, "click");
						       						</script>
				           	 				</td>
<!-- 											End Sản phẩm kho -->					           	 				
								<%} }%>
							<%-- <% 
							
							} %> --%>
							
							
							
					</TR>
              <%  pk_seq ++ ; } %>
                        	
                        	
					</TABLE>
						 		
					</FIELDSET>						
				</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
	
	
<script type="text/javascript">
	replaces(); 
	
	
</script>

<script type="text/javascript">
    dropdowncontent.init('pclist', "left-bottom", 250, "click");
	dropdowncontent.init('duyetId', "right-bottom", 300, "click");
</script>	

<script type="text/javascript">
	for(var i = 0; i < 20; i++) {
		dropdowncontent.init('tenhd_'+i, "left-top", 300, "click");
	}
	
	for(var i = 0; i < 20; i++) {
		dropdowncontent.init('sanPhamKho_' + i, "left-top", 300, "click");
	}
</script>
	
	
</form>

</BODY>
</HTML>


<% 
	if(rstkkt_no != null) rstkkt_no.close();
	if(rstkkt_co != null) rstkkt_co.close();
	if(rsSanPham != null) rsSanPham.close();
	
	if(kbh != null) kbh.close();
	btthBean.DBClose();
	session.setAttribute("btthBean", null);

}%>
