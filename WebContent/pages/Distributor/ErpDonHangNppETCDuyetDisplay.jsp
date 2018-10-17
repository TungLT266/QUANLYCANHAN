<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.distributor.beans.hopdong.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.hopdong.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% IErpDonhangNppETC lsxBean = (IErpDonhangNppETC)session.getAttribute("lsxBean"); %>
<% ResultSet dvkdRs = lsxBean.getDvkdRs(); %>
<% ResultSet kbhRs = lsxBean.getKbhRs(); %>
<% ResultSet nppRs = lsxBean.getKhRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet dvtRs = lsxBean.getDvtRs(); %>
<% ResultSet gsbhRs = lsxBean.getGsbhRs(); %>
<% ResultSet ddkdRs = lsxBean.getDdkdRs(); %>
<% ResultSet hopdongRs = lsxBean.getHopdongRs(); %>
<% ResultSet congnoRs = lsxBean.getCongnoRs(); %>
<% ResultSet tichluyRs = lsxBean.getTichluyRs(); %>
<% ResultSet kyguiRs = lsxBean.getKhachhangKGRs(); %>

<% 
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spSoluonton = lsxBean.getSpSoluongton();
	
	String[] spGianhap = lsxBean.getSpGianhap();
	String[] spGianhapGOC = lsxBean.getSpGianhapGOC();
	String[] spChietkhau = lsxBean.getSpChietkhau();
	String[] spVat = lsxBean.getSpVat();
	String[] spSCheme = lsxBean.getSpScheme();
	String[] spTungay = lsxBean.getSpTungay();
	String[] spDenngay = lsxBean.getSpDenngay();
	String[] spTrongluong = lsxBean.getSpTrongluong();
	String[] spThetich = lsxBean.getSpThetich();
	String[] spQuyDoi = lsxBean.getSpQuyDoi();
	
	String[] spDagiao = lsxBean.getSpDagiao();
	
	String[] spScheme = lsxBean.getSpScheme();
	String[] spTDV = lsxBean.getSpTDV();
	String[] spCHIETKHAU_CSKM = lsxBean.getSpChietkhauBHKM();
	
	String[] dhCk_diengiai = lsxBean.getDhck_diengiai();
	String[] dhCk_giatri = lsxBean.getDhck_giatri();
	String[] dhCk_loai = lsxBean.getDhck_loai();
	
	NumberFormat formater = new DecimalFormat("##,###,###");
	NumberFormat formater2 = new DecimalFormat("##,###,###.##");
	
	Hashtable<String, String> sanpham_soluong = lsxBean.getSanpham_Soluong();
	Hashtable<String, String> sanpham_soluongDAXUAT = lsxBean.getSanpham_SoluongDAXUAT_THEODN();
%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else { %>

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

#ajax_listOfOptions div {
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

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

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
<script type="text/javascript" src="../scripts/erp-SpListDonDatHang.js"></script>

<script language="javascript" type="text/javascript">

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
	
	function replaces()
	{
		var spHansd = document.getElementsByName("spHansd");
		var spMa = document.getElementsByName("spMa");
		var spTen = document.getElementsByName("spTen");  
		var donvi = document.getElementsByName("donvi");
		var soluong = document.getElementsByName("soluong");
		var soluongton = document.getElementsByName("soluongton");
		var dongiaGOC = document.getElementsByName("dongiaGOC");
		var dongia = document.getElementsByName("dongia");
		var dongiaDACOVAT = document.getElementsByName("dongiaDACOVAT");
		var thanhtien = document.getElementsByName("thanhtien");
		
		var trongluong = document.getElementsByName("spTrongLuong");
		var thetich = document.getElementsByName("spTheTich");
		var spQuyDoi = document.getElementsByName("spQuyDoi");
		var spSoluongtongoc = document.getElementsByName("soluongtongoc") ; 
		var spvat = document.getElementsByName("spvat");
		
		var spTDV = document.getElementsByName("spTDV");
		var ptchietkhauBHKM = document.getElementsByName("ptchietkhauBHKM");
		var spscheme = document.getElementsByName("scheme");
		
		var loaidonhang = document.getElementById("loaidonhang").value;
		
		var ckId = document.getElementById("txtPTChietKhau");
		var pt_ck = ckId.value;
		if(pt_ck == '')
			pt_ck = 0;
		
		var i;
		for(i = 0; i < spMa.length; i++)
		{
			if(spMa.item(i).value != "")
			{
				console.log(sp);
				var sp = spMa.item(i).value;
				var pos = parseInt(sp.indexOf(" - "));

				if(pos > 0)
				{
					spMa.item(i).value = sp.substring(0, pos);
					sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
					
					spTen.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					
					donvi.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					dongia.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					dongiaGOC.item(i).value = dongia.item(i).value;
										
					/* if( parseFloat(pt_ck) > 0  )
					{
						dongia.item(i).value = parseFloat(dongia.item(i).value) * ( 1 - parseFloat(pt_ck) / 100 );

						var sole = '';
						if(dongia.item(i).value.indexOf(".") >= 0)
						{
							sole = dongia.item(i).value.substring(dongia.item(i).value.indexOf('.'), parseInt(dongia.item(i).value.indexOf('.')) + 5 );
						}
						
						var dg = dongia.item(i).value.split(".");
						dongia.item(i).value = dg[0] + sole;
						dongiaGOC.item(i).value = dg[0] + sole;
						//alert('DG0: ' + dg[0] + '  -- DG1: ' + dg[1] );
					} */
					
					spvat.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					var _ptVAT = spvat.item(i).value;
					if( _ptVAT == '' )
						_ptVAT = 0;
					
					dongiaDACOVAT.item(i).value = parseFloat( dongia.item(i).value ) * ( 1 + parseFloat( _ptVAT ) / 100.0 );
					
					if( loaidonhang != 1 )
					{
						soluongton.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
					 	spSoluongtongoc.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));  	
					}
					else
					{
						soluongton.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
						spSoluongtongoc.item(i).value = soluongton.item(i).value;
						
						sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
						
						spTDV.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					}				
				}
			}
			else
			{
				if( spscheme.item(i).value == '' || spscheme.item(i).value == ' ' )
				{
					spMa.item(i).value = "";
					spTen.item(i).value = "";
					donvi.item(i).value = "";
					soluong.item(i).value = "";
					soluongton.item(i).value = "";
					spSoluongtongoc.item(i).value = "";
					dongia.item(i).value = "";
					dongiaGOC.item(i).value = "";
					dongiaDACOVAT.item(i).value = "";
					thanhtien.item(i).value = "";	
					trongluong.item(i).value = "";	
					thetich.item(i).value = "";	
					spQuyDoi.item(i).value = "";
					spvat.item(i).value = "";
					
					spTDV.item(i).value = "";
					ptchietkhauBHKM.item(i).value = "";
				}
				
			}
		}	
		
		TinhTien();
		setTimeout(replaces, 300);
	}
	
	function TinhTien()
	 {
		var spMa = document.getElementsByName("spMa");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var dongiaGOC = document.getElementsByName("dongiaGOC");
		var dongiaDACOVAT = document.getElementsByName("dongiaDACOVAT");
		var chietkhau = document.getElementsByName("chietkhau");
		var thueVAT = document.getElementsByName("spvat");
		var thanhtien = document.getElementsByName("thanhtien");
		
		var ptchietkhauBHKM = document.getElementsByName("ptchietkhauBHKM");
		
		var trongluong = document.getElementsByName("spTrongLuong");
		var thetich = document.getElementsByName("spTheTich");
		var spQuyDoi = document.getElementsByName("spQuyDoi");
		var spDonVi = document.getElementsByName("donvi");
		
		var vat = document.getElementById("txtVAT").value;
		if(vat == '')
			vat = '0';
		
		var tongtien = 0;
		var tongtienCK = 0;
		var tongtienVAT = 0;
		
		var totalTL = 0;
		var totalTT = 0;
				
		var totalTHG = 0;
		var totalLe = 0 ;
		
		var ptck_csbh = 0;
		var tienck_csbh = 0;
		
		var totalTT_sauVAT = 0;
		var tongtien_dongiaGOC = 0;
		
		for(var i = 0; i < spMa.length; i++)
		{
			if(spMa.item(i).value != "" && dongia.item(i).value != "" && soluong.item(i).value != "" )
			{
				var _ck = chietkhau.item(i).value.replace(/,/g,"");
				if(_ck == '')
					_ck = '0';
				
				var _thueVAT = thueVAT.item(i).value.replace(/,/g,"");
				
				if(  parseFloat(vat) > 0 && _thueVAT == '' )
				{
					thueVAT.item(i).value = vat;
					_thueVAT = vat;
				}
				else
				{
					if(_thueVAT == '')
						_thueVAT = '0';	
				}
					
				dongiaDACOVAT.item(i).value = Math.round( parseFloat( dongiaGOC.item(i).value ) * ( 1 + parseFloat( _thueVAT ) / 100.0 ) );

				//var tt = parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")) - parseFloat(_ck);
				//tt = parseFloat(tt) * ( 1 +  parseFloat(_thueVAT) / 100 );
				var tt = parseFloat( dongiaDACOVAT.item(i).value ) * parseFloat(soluong.item(i).value.replace(/,/g,"")) - parseFloat(_ck);
				thanhtien.item(i).value = DinhDangTien(tt);

				tongtien += ( parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")) );
				tongtienCK += parseFloat( _ck );
				tongtienVAT += ( parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")) - parseFloat(_ck) ) * ( parseFloat(_thueVAT) / 100 );
			
				var _dgGOC = dongiaGOC.item(i).value.replace(/,/g,"");
				if(_dgGOC == 0)
					_dgGOC = dongia.item(i).value.replace(/,/g,"");
				
				var _dgGOC_coVAT = Math.round( _dgGOC * ( 1 + parseFloat( _thueVAT ) / 100 ) );
				tongtien_dongiaGOC += ( parseFloat( _dgGOC_coVAT ) * parseFloat(soluong.item(i).value.replace(/,/g,"")) );
				
				//ptck_csbh = ( 1 - ( parseFloat(dongia.item(i).value.replace(/,/g,"")) / parseFloat( _dgGOC ) ) ) * 100.0;
				ptck_csbh = Math.max(parseFloat( ptchietkhauBHKM.item(i).value ), parseFloat( ptck_csbh ));
				tienck_csbh += ( parseFloat( _dgGOC ) - parseFloat(dongia.item(i).value.replace(/,/g,"")) ) * parseFloat(soluong.item(i).value.replace(/,/g,""));
			
				//totalTT_sauVAT += tt;
				totalTT_sauVAT += Math.round( parseFloat( dongia.item(i).value ) * ( 1 + parseFloat( _thueVAT ) / 100.0 ) ) * parseFloat(soluong.item(i).value.replace(/,/g,"")) ;
			}		
		}
		
		tongtien = Math.round(tongtien);
		tongtienCK = Math.round(tongtienCK);
		tongtienVAT = Math.round(tongtienVAT);
		
		document.getElementById("txtPTChietkhauBH").value = DinhDangTien( ptck_csbh );
		document.getElementById("txtChietkhauBH").value = DinhDangTien( tienck_csbh );
		
		var tongDhCK = document.getElementById("txtTongCK").value.replace(/,/g,"");
		if( tongDhCK == '' )
			tongDhCK = 0;
		
		var tongtien_sau_hoahong = 0;

		tongtienCK += parseFloat(tongDhCK);
		
		//alert( 'TONG TIEN: ' + tongtien + ' - TONG TIEN CK: ' + tongtienCK );
		var tongtienSAUCK = parseFloat(tongtien) - parseFloat(tongtienCK);
		
		//document.getElementById("txtTongCK").value = DinhDangTien(tongtienCK);
		
		//document.getElementById("txtBVAT").value = DinhDangTien(tongtien);
		document.getElementById("txtBVAT").value = DinhDangTien(tongtien_dongiaGOC);
		
		document.getElementById("txtTongSauCK").value = DinhDangTien(tongtienSAUCK);
		
		document.getElementById("txtVAT").value = DinhDangTien(tongtienVAT);
		
		var sotienGIAM = document.getElementById("txtSotiengiam").value.replace(/,/g,"");
		if( sotienGIAM == '' )
			sotienGIAM = 0;
		
		var tientichluy = document.getElementById("txtTientichluy").value.replace(/,/g,"");
		if( tientichluy == '' )
			tientichluy = 0;
		
		//var tongtienSAUVAT = parseFloat(tongtienSAUCK) + ( parseFloat(tongtienVAT) ) - parseFloat( sotienGIAM ) ;
		var tongtienSAUVAT = parseFloat(totalTT_sauVAT) - parseFloat( sotienGIAM ) - parseFloat( tientichluy ) - parseFloat( tongDhCK ) ;
		document.getElementById("txtSauVAT").value = DinhDangTien(tongtienSAUVAT);
		
	 }
	
	 function submitformDuyet()
	 { 
		 document.forms['hctmhForm'].action.value='submitDUYET_CS';
	     document.forms["hctmhForm"].submit();
	 }
	
	 function chotform()
	 {	
		 //CHECK XEM CO PHAI LA KHACH HANG DAC BIET HAY KHONG
		 var xmlhttp;
		 if (window.XMLHttpRequest)
		 {// code for IE7+, Firefox, Chrome, Opera, Safari
		    xmlhttp = new XMLHttpRequest();
		 }
		 else
		 {// code for IE6, IE5
		    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		 }
		 xmlhttp.onreadystatechange=function()
		 {
		   	if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		   	{
		   		//alert(xmlhttp.responseText);
		       	if( xmlhttp.responseText == '1')
		    	{
		       		if(!confirm("Đây là khách hàng đặc biệt. Vui lòng kiểm tra lại đầy đủ thông tin"))
		       		{
		       			return;
		       		}
		       		else
		       		{
		       			 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
				   	 	 document.forms['hctmhForm'].action.value = 'duyet';
				   	     document.forms['hctmhForm'].submit();
		       		}
		    	}
		       	else
		       	{
		       		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
			   	 	 document.forms['hctmhForm'].action.value = 'duyet';
			   	     document.forms['hctmhForm'].submit();
		       	}
		   	}
		 }
		 
		 var khId = document.getElementById("khId").value;
		 xmlhttp.open("GET","../../ErpDonhangNppETCUpdateSvl?type=checkDACBIET&khId=" + khId,true);
		 xmlhttp.send();
		 
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
	 
	function Dinhdang(element)
	{
		element.value = DinhDangTien(element.value);
		if(element.value== '' )
		{
			element.value= '';
		}
	}	
	
	
	function CheckSoLuong_DeXuat(element)
	{
		element.value = DinhDangTien(element.value);
		if(element.value== '' )
		{
			element.value= '';
		}
		
		Update_SoLuong( element );
	}	
	
	function Update_SoLuong( element )
	{
		var spMa = document.getElementsByName("spMa");
		var soluong = document.getElementsByName("soluong");
		var soluong2 = document.getElementsByName("soluong2");
		
		for(var i = 0; i < spMa.length; i++ )
		{
			var soluongDEXUAT = document.getElementsByName(spMa.item(i).value + "_spSOLUONG");
			
			var totalXUAT = 0;
			for(var j = 0; j < soluongDEXUAT.length; j++ )
			{
				totalXUAT = parseFloat(totalXUAT) + parseFloat(soluongDEXUAT.item(j).value.replace(/,/g,""));
			}
			
			//alert(totalXUAT);
			
			if( totalXUAT > parseFloat(soluong.item(i).value.replace(/,/g,"")) )
			{
				soluong2.item(i).value = soluong.item(i).value;
				element.value = '0';
				
				alert('Số lượng xuất ( ' + totalXUAT + ' ) không được phép vượt quá số lượng đặt ( ' + soluong.item(i).value + ' )');
			}

			soluong2.item(i).value = soluong.item(i).value;
		}
		
	}
	 
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
		
	});
</script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<script>

$(function() {
 
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<script>
function goBack() {
    window.history.back();
}
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpDonhangNppETCUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="loaidonhang" value='<%= lsxBean.getLoaidonhang() %>'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>
<input type="hidden" name="nppId" value='<%= lsxBean.getNppId() %>'>
<input type="hidden" name="dungchungkenh" value='<%= lsxBean.getDungchungKenh() %>'>
<input type="hidden" name="capduyet" value='<%= lsxBean.getCapduyet() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý bán hàng > Đơn bán hàng > Duyệt đơn hàng ( <%= lsxBean.getCapduyet() %> ) > Duyệt 
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<%-- <A href= "../../ErpDuyetddhNppSvl?userId=<%= userId %>&loaidonhang=<%= lsxBean.getLoaidonhang() %>&capduyet=<%= lsxBean.getCapduyet() %>" > --%>
       <A href= "javascript:goBack();" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
       <%if(!lsxBean.getTrangthai().equals("4")){ %>     
        <span id="btnSave">
	        <A href="javascript:chotform()" >
	        	<IMG src="../images/Chot.png" title="Duyệt đơn hàng" alt="Duyệt đơn hàng" border ="1px" style="border-style:outset"></A>
        </span>
   <%} %>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= lsxBean.getMsg() %></textarea>
		         <% lsxBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Đơn hàng bán </legend>
        	<div style="float:none; width:100%" align="left">
        	
        	
            <TABLE width="100%" cellpadding="4" cellspacing="0">
            	<TR >
                    <TD class="plainlabel" style="width: 130px;" >Loại đơn hàng </TD>
                    <TD class="plainlabel" style="width: 250px;" >
                    	<select name="loaidonhang" id='loaidonhang' class="select2" style="width: 200px" onchange="changeLOAIDONHANG();">
							<option value=""></option>
							<% if(lsxBean.getLoaidonhang().equals("0")) { %>
								<option value="0" selected="selected" >Bán cho ĐLPP</option>
							<% } else { %>
								<option value="0" >Bán cho ĐLPP</option>
							<% } %>
							<% if(lsxBean.getLoaidonhang().equals("1")) { %>
								<option value="1" selected="selected" >ETC</option>
							<% } else { %>
								<option value="1" >ETC</option>
							<% } %>
							<% if(lsxBean.getLoaidonhang().equals("2")) { %>
								<option value="2" selected="selected" >OTC</option>
							<% } else { %>
								<option value="2" >OTC</option>
							<% } %>
							<% if(lsxBean.getLoaidonhang().equals("3")) { %>
								<option value="3" selected="selected" >Bán nội bộ</option>
							<% } else { %>
								<option value="3" >Bán nội bộ</option>
							<% } %>
						</select>
                    </TD>   
                    <TD class="plainlabel" style="width: 120px;" >Kho đặt hàng</TD>
                    <TD class="plainlabel"   >
                    	<select name = "khonhapId" class="select2" style="width: 200px" onchange="submitform();" >
                        	<%
                        		if(khonhapRs != null)
                        		{
                        			try
                        			{
                        			while(khonhapRs.next())
                        			{  
                        			if( khonhapRs.getString("pk_seq").equals(lsxBean.getKhoNhapId())){ %>
                        				<option value="<%= khonhapRs.getString("pk_seq") %>" selected="selected" ><%= khonhapRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khonhapRs.getString("pk_seq") %>" ><%= khonhapRs.getString("ten") %></option>
                        		 <% } } khonhapRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>     
                </TR>	
            	<TR>
                    <TD class="plainlabel" >Ngày đơn hàng </TD>
                    <TD class="plainlabel" <%= !lsxBean.getLoaidonhang().equals("2") ? "" : " colspan='3' " %> >
                    	<input type="text" class="days" readonly="readonly"   id="hopdong_tungay" name="hopdong_tungay" value="<%= lsxBean.getTungay() %>"/>
                    </TD>
                    
                    <% if(!lsxBean.getLoaidonhang().equals("2")) { %>
                    <TD class="plainlabel" >Ngày đề nghị giao </TD>
                    <TD class="plainlabel" >
                    	<input type="text" class="days" readonly="readonly"   id="hopdong_denngay"  name="hopdong_denngay" value="<%= lsxBean.getDenngay() %>"/>
                    </TD>
                    <% } %>
                </TR>
            	
            	<TR > 
                    <TD class="plainlabel" >Kênh bán hàng </TD>
                    <TD class="plainlabel" colspan="<%= !lsxBean.getKhoNhapId().equals("100003") ? "3" : "1" %>" >
                    	<select name = "kbhId"  id="kbhId"  class="select2" style="width: 200px" onchange="submitform();" >
                    		<% if( !lsxBean.getLoaidonhang().equals("3") ) { %>
                    			<option value="" ></option>
                    		<% } %>
                        	<%
                        		if(kbhRs != null)
                        		{
                        			try
                        			{
                        			while(kbhRs.next())
                        			{  
                        			if( kbhRs.getString("pk_seq").equals(lsxBean.getKbhId())){ %>
                        				<option value="<%= kbhRs.getString("pk_seq") %>" selected="selected" ><%= kbhRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= kbhRs.getString("pk_seq") %>" ><%= kbhRs.getString("ten") %></option>
                        		 <% } } kbhRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>  
                    
                    <% if(lsxBean.getKhoNhapId().equals("100003")) { %>
                    
                    <TD class="plainlabel" >Khách hàng ký gửi </TD>
                    <TD class="plainlabel"  >
                    	<select name = "khKGId"  id="khKGId"  class="select2" style="width: 315px"  >
                    		<option value="" ></option>
                        	<%
                        		if(kyguiRs != null)
                        		{
                        			try
                        			{
                        			while(kyguiRs.next())
                        			{  
                        			if( kyguiRs.getString("pk_seq").equals(lsxBean.getKhachhangKGId())){ %>
                        				<option value="<%= kyguiRs.getString("pk_seq") %>" selected="selected" ><%= kyguiRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= kyguiRs.getString("pk_seq") %>" ><%= kyguiRs.getString("ten") %></option>
                        		 <% } } kyguiRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>  	
                    
                    <% } %>      
                </TR>
            					
                <TR style="display: none;" >
                	<TD class="plainlabel" >Đơn vị kinh doanh </TD>
                    <TD class="plainlabel"   >
                    	<select name = "dvkdId"   id="dvkdId"  class="select2" style="width: 200px" >
                        	<%
                        		if(dvkdRs != null)
                        		{
                        			try
                        			{
                        			while(dvkdRs.next())
                        			{  
                        			if( dvkdRs.getString("pk_seq").equals(lsxBean.getDvkdId())){ %>
                        				<option value="<%= dvkdRs.getString("pk_seq") %>" selected="selected" ><%= dvkdRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= dvkdRs.getString("pk_seq") %>" ><%= dvkdRs.getString("ten") %></option>
                        		 <% } } dvkdRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   	
                </TR>
                <TR>
                	<TD class="plainlabel" >Giám sát </TD>
                    <TD class="plainlabel"   >
                    	<select name = "gsbhId"  class="select2" style="width: 200px" onchange="submitform();" >
                    		<!-- <option value=""> </option> -->
                        	<%
                        		if(gsbhRs != null)
                        		{
                        			try
                        			{
                        			while(gsbhRs.next())
                        			{  
                        			if( gsbhRs.getString("pk_seq").equals(lsxBean.getGsbhId())){ %>
                        				<option value="<%= gsbhRs.getString("pk_seq") %>" selected="selected" ><%= gsbhRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= gsbhRs.getString("pk_seq") %>" ><%= gsbhRs.getString("ten") %></option>
                        		 <% } } gsbhRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   
                    <TD class="plainlabel" >Trình dược viên </TD>
                    <TD class="plainlabel"    >
                    	<select name = "ddkdId"  class="select2" style="width: 315px" onchange="submitform();"  >
                    		<!-- <option value=""  > </option> -->
                        	<%
                        		if(ddkdRs != null)
                        		{
                        			try
                        			{
                        			while(ddkdRs.next())
                        			{  
                        			if( ddkdRs.getString("pk_seq").equals(lsxBean.getDdkdId())){ %>
                        				<option value="<%= ddkdRs.getString("pk_seq") %>" selected="selected" ><%= ddkdRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= ddkdRs.getString("pk_seq") %>" ><%= ddkdRs.getString("ten") %></option>
                        		 <% } } } catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>         	
                </TR>
                
                <TR>
                	<TD class="plainlabel" >
                		<%= lsxBean.getDoituong() %>
                	</TD>
                    <TD class="plainlabel" colspan='3'  >
                    	<select name = "khId"    id="khId"  class="select2" style="width: 700px" onchange="submitform();" >
                    		<option value=""> </option>
                        	<%
                        		if(nppRs != null)
                        		{
                        			try
                        			{
                        			while(nppRs.next())
                        			{  
                        			if( nppRs.getString("pk_seq").equals(lsxBean.getKhId())){ %>
                        				<option value="<%= nppRs.getString("pk_seq") %>" selected="selected" ><%= nppRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nppRs.getString("pk_seq") %>" ><%= nppRs.getString("ten") %></option>
                        		 <% } } nppRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Cấp độ giao hàng </TD>
                    <TD class="plainlabel" <%= ( lsxBean.getIsMTV().equals("1") || lsxBean.getLoaidonhang().equals("1") ) ? "" : " colspan='3' " %>  >
                    	<select name="capdogiaohang" class="select2" style="width: 200px"  >
                    		<option value="" ></option>
                        	<% if( lsxBean.getCapdogiaohang().equals("4") ) { %>
                        		<option value="4" selected>4 tiếng</option>
                        	<% } else { %>
                        		<option value="4" >4 tiếng</option>
                        	<% } %>
                        	<% if( lsxBean.getCapdogiaohang().equals("8") ) { %>
                        		<option value="8" selected>8 tiếng</option>
                        	<% } else { %>
                        		<option value="8" >8 tiếng</option>
                        	<% } %>
                        	<% if( lsxBean.getCapdogiaohang().equals("24") ) { %>
                        		<option value="24" selected>24 tiếng</option>
                        	<% } else { %>
                        		<option value="24" >24 tiếng</option>
                        	<% } %>
                    	</select>
                    </TD>
                
                	<% if(lsxBean.getLoaidonhang().equals("1")) { %>
	                    <TD class="plainlabel" >Hợp đồng </TD>
	                    <TD class="plainlabel" >
	                    	<select name="mahopdong" id="mahopdong"  class="select2" style="width: 200px" onchange="changeLOAIDONHANG();" >
	                    		<option value="" ></option>
	                        	<%
	                        		if(hopdongRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(hopdongRs.next())
	                        			{  
	                        			if( hopdongRs.getString("pk_seq").equals(lsxBean.getMahopdong())){ %>
	                        				<option value="<%= hopdongRs.getString("pk_seq") %>" selected="selected" ><%= hopdongRs.getString("diengiai") %></option>
	                        			<%}else { %>
	                        				<option value="<%= hopdongRs.getString("pk_seq") %>" ><%= hopdongRs.getString("diengiai") %></option>
	                        		 <% } } hopdongRs.close();} catch(SQLException ex){}
	                        		}
	                        	%>
	                    	</select>
	                    </TD>
                	<% } else if (!lsxBean.getIsMTV().equals("0") ) { %>
	                
	                	<TD class="plainlabel" >Số hợp đồng </TD>
	                    <TD class="plainlabel"  >
	                    	<input type="text"  value="<%= lsxBean.getSohopdong() %>" id="sohopdong" style="text-align: right;" name="sohopdong"  />
	                    </TD>
	                
	                <%} %>
                </tr>
                
                <TR style="display: none;" >
                    <TD width="130px"  class="plainlabel" >PT chiết khấu </TD>
                    <TD width="300px"  class="plainlabel" >
                    	<input type="text"  value="<%= lsxBean.getChietkhau() %>" id="txtPTChietKhau" style="text-align: right;" name="ptChietkhau" readonly="readonly" />
                    </TD>
                    <TD width="130px"  class="plainlabel" >Tổng thành tiền </TD>
                    <TD class="plainlabel" >

                    </TD>
                </TR>
                
                <TR style="display: none;" >
                	<TD class="plainlabel" style="display: none;" >Mã voucher </TD>
                    <TD class="plainlabel" style="display: none;" >
                    	<input type="text" value="<%= lsxBean.getMaphieuMH() %>" name="maphieuMH" style="text-align: right; width: 200px; " />
                    </TD>
                    
                    <TD class="plainlabel" ><!-- Giá trị voucher --> Doanh số tích lũy </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="hidden" readonly="readonly"  value="<%= lsxBean.getSotiengiam() %>" id="txtSotiengiam" style="text-align: right;" />
                    
                    	<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	<span class="plainlabel" style="color: red;" >Doanh số tích lũy </span> &nbsp;&nbsp;&nbsp; -->
                    	<input type="text" readonly="readonly"  value="0" id="" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Doanh số </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  id="txtBVAT" value="" style="text-align: right;"  />
                    </TD>
                    
                    <TD class="plainlabel"  style="color: red;" >Chiết khấu </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" readonly="readonly"  value="" id="txtPTChietkhauBH" style="text-align: right; width: 200px; " />
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Tổng tiền tích lũy </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  id="txtTientichluy" name='tientichluy' value="<%= lsxBean.getTientichluy() %>" style="text-align: right;"  />
                    </TD>
                    
                    <TD class="plainlabel"  style="color: red;" >Tổng thanh toán </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" readonly="readonly"  value="" id="txtSauVAT" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR style="display: none;" >
                    <TD class="plainlabel" >Chiết khấu KM </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" readonly="readonly"  value="<%= lsxBean.getTienchietkhau() %>" id="txtTongCK" style="text-align: right; width: 200px; " />
                    </TD>
                    	
                    <TD class="plainlabel" >Tổng tiền sau CK </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  value="" id="txtTongSauCK" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR style="display: none;" >
                	<TD class="plainlabel" >Tổng tiên chiết khấu BH </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  value="" id="txtChietkhauBH" style="text-align: right; width: 200px; " />
                    </TD>
                    
                    <TD class="plainlabel" >Tổng số tiền VAT </TD>
                    <TD class="plainlabel" >
                    	<input type="text" value="<%= lsxBean.getVat() %>" id="txtVAT" style="text-align: right;" name="ptVat" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);"  readonly='readonly'  />
                    </TD>
                    	
                </TR>
                
                
                <TR style="display: none;" >
                    <TD class="plainlabel" >Tổng thùng </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" value="" id="txtTongThung" style="text-align: right;" readonly="readonly" /> 
                    </TD>
                    	
                    <TD class="plainlabel"  >Tổng lẻ </TD>
                    <TD class="plainlabel" >
                    	<input type="text" value="" id="txtTongLe" style="text-align: right;" readonly="readonly"  /> 
                    </TD>
                </TR>
                
                <TR style="display: none;" >
                    <TD class="plainlabel" >Tổng trọng lượng </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" value="" id="txtTongTL" style="text-align: right;" readonly="readonly" /> gram
                    </TD>
                    	
                    <TD class="plainlabel"  >Tổng thể tích </TD>
                    <TD class="plainlabel" >
                    	<input type="text" value="" id="txtTongTT" style="text-align: right;" readonly="readonly"  /> cm3
                    </TD>
                </TR>
                <TR>
                	<TD class="plainlabel" >Công nợ </TD>
                    <TD class="plainlabel" >
                		<a href="" id="popupCONGNO" rel="subcontentCN">
		           	 				&nbsp; <img alt="Thông tin công nợ" src="../images/vitriluu.png" title="Thông tin công nợ" ></a>
		           	 	
			           	 	<DIV id="subcontentCN" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                             background-color: white; width: 500px; max-height:300px; overflow:auto; padding: 4px;">
				                    <table width="95%" align="center">
				                        <tr>
				                            <th width="150px" style="font-size: 12px; text-align: center;">Tổng nợ</th>
				                            <th width="150px" style="font-size: 12px; text-align: center;">Hạn mức nợ</th>
				                            <th width="150px" style="font-size: 12px; text-align: center;">Số ngày nợ</th>
				                            <th width="150px" style="font-size: 12px; text-align: center;">Nợ trong hạn</th>
				                            <th width="150px" style="font-size: 12px; text-align: center;">Nợ quá hạn</th>
				                            <th width="150px" style="font-size: 12px; text-align: center;">Nợ xấu</th>
				                            <th width="150px" style="font-size: 12px; text-align: center;">Nợ quá xấu</th>
				                        </tr>
		                        		<tr>
		                        			<td style="text-align: right;" ><%= formater.format(lsxBean.TongNo()) %> </td>
		                        			<td style="text-align: right;" ><%= formater.format(lsxBean.HanMucNo()) %> </td>
		                        			<td style="text-align: right;" ><%= formater.format(lsxBean.SoNgayNo()) %> </td>
		                        			<td style="text-align: right;" ><%= formater.format( lsxBean.NoTrongHan()) %> </td>
		                        			<td style="text-align: right;" ><%= formater.format( lsxBean.NoQuaHan()) %> </td>
		                        			<td style="text-align: right;" ><%= formater.format( lsxBean.NoXau()) %> </td>
		                        			<td style="text-align: right;" ><%= formater.format( lsxBean.NoQuaXau()) %> </td>
		                        		</tr>
				                   </table>
				                   
				                   <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentCN')" ><b> Đóng lại </b></a>
				                   </div>
				                   
					       </DIV>
                    	
	                    	<script type="text/javascript">
				            	dropdowncontent.init('popupCONGNO', "right-top", 300, "click");
				            </script>
                	</TD>
                	<TD class="plainlabel" >Doanh số tích lũy</TD>
                	<TD class="plainlabel" >
                	
               			<a href="" id="popupTICHLUY" rel="subcontentTL">
	           	 				&nbsp; <img alt="Thông tin doanh số tích lũy" src="../images/vitriluu.png" title="Thông tin doanh số tích lũy" ></a>
	           	 	
		           	 	<DIV id="subcontentTL" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 600px; max-height:300px; overflow:auto; padding: 4px;">
			                    <table width="98%" align="center" cellpadding="2" cellspacing="1"  >
			                        <tr class="plainlabel" >
			                        	<th width="25%" style="font-size: 12px; text-align: center;" rowspan="2" >Scheme</th>
			                            <th width="15%" style="font-size: 12px; text-align: center;" rowspan="2" >DS ký hợp đồng</th>
			                            
			                            <th width="15%" style="font-size: 12px; text-align: center;" rowspan="2" >DS tích lũy</th>
			                            <th width="15%" style="font-size: 12px; text-align: center;" rowspan="2" >DS cần đạt</th>
			                            
			                            <th width="30%" style="font-size: 12px; text-align: center;" colspan="2" >Hiệu lực</th>
			                        </tr>
			                        
			                        <tr class="plainlabel" >
			                            <!-- <th width="15%" style="font-size: 12px; text-align: center;" >Từ</th>
			                            <th width="15%" style="font-size: 12px; text-align: center;">Đến</th> -->
			                          
			                            <th width="15%" style="font-size: 12px; text-align: center;" >Từ ngày</th>
			                            <th width="15%" style="font-size: 12px; text-align: center;">Đến ngày<th>
			                        </tr>
			                        
			                        <%
			                        	if(tichluyRs != null)
			                        	{
			                        		while(tichluyRs.next())
			                        		{ 
			                        			double dsKyhopdong = tichluyRs.getDouble("tumuc");
			                        			double dsKyhopdongMAX = tichluyRs.getDouble("denmuc");
			                        			double doanhso = tichluyRs.getDouble("doanhso");
			                        			
			                        			double dsTichluy = tichluyRs.getDouble("doanhsoTICHLUY");
			                        			double dsCandat = 0;
			                        			if( dsTichluy < dsKyhopdong )
			                        			{
			                        				//dsCandat = dsKyhopdong - dsTichluy;
			                        				dsCandat = doanhso - dsTichluy;
			                        			}
			                        			else
			                        			{
			                        				if( dsTichluy < dsKyhopdongMAX )
			                        				{
			                        					//dsCandat = dsKyhopdongMAX - dsTichluy;
			                        					dsCandat = doanhso - dsTichluy;
			                        				}
			                        			}
			                        			
			                        		%>
			                        		<tr>
			                        			<%-- <td ><input type="text" value='<%= formater.format( dsKyhopdong ) %>' style="width: 100%; text-align: right;" /></td> --%>
			                        			<td ><input type="text" value='<%= tichluyRs.getString("scheme") %>' style="width: 100%;" /></td>
			                        			<td ><input type="text" value='<%= formater.format( doanhso ) %>' style="width: 100%; text-align: right;" /></td>
			                        			<td ><input type="text" value='<%= formater.format( dsTichluy ) %>' style="width: 100%; text-align: right;" /></td>
			                        			<td ><input type="text" value='<%= formater.format( dsCandat ) %>' style="width: 100%; text-align: right;" /></td>
			                        			<td ><input type="text" value='<%= tichluyRs.getString("ngaykyhd") %>' style="width: 100%; text-align: center;" /></td>
			                        			<td ><input type="text" value='<%= tichluyRs.getString("ngayketthuchd") %>' style="width: 100%; text-align: center;" /></td>
			                        		</tr>
			                        		<% }
			                        		tichluyRs.close();
			                        	}
			                        %>
			                   </table>
			                   
			                   <div align="right">
			                     	<label style="color: red" ></label>
			                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                     	<a href="javascript:dropdowncontent.hidediv('subcontentTL')" ><b> Đóng lại </b></a>
			                   </div>
				       </DIV>
                   	
                    	<script type="text/javascript">
			            	dropdowncontent.init('popupTICHLUY', "right-top", 300, "click");
			            </script>
                	
                		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                		<% if( !lsxBean.getLoaidonhang().equals("0") && lsxBean.getCapduyet().equals("CS") ) { %>
	                		<% if(lsxBean.getTratichluy().equals("1")) { %>
	                    		<input type="checkbox" name="tratichluy" value="1" checked="checked" onchange="submitformDuyet();" > Trả tích lũy
	                   		<% } else { %>
	                   			<input type="checkbox" name="tratichluy" value="1" onchange="submitformDuyet();" > Trả tích lũy
	                   		<% } %>
                   		<% } %>
                   		
                		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                		<% if( lsxBean.getLoaidonhang().equals("2") || lsxBean.getLoaidonhang().equals("1") ) { %>
	                		<% if(lsxBean.getDonhangmuon().equals("1")) { %>
	                    		<input type="checkbox" name="donhangmuon" value="1" checked="checked" > Đơn hàng mượn
	                   		<% } else { %>
	                   			<input type="checkbox" name="donhangmuon" value="1" > Đơn hàng mượn
	                   		<% } %>
                   		<% } %>
                	</TD>
                </TR>
                <tr>
                	<TD class="plainlabel" >Ghi chú </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 700px;" />
                    </TD>
                </TR>
                
                <% if( lsxBean.getCapduyet().equals("CS") ) { %>
				<TR >
				   <TD  class="plainlabel" colspan = '4'>
				   <div id="btnApKhuyenMai">
				  		<a class="button2" href="javascript:submitformDuyet();">
							<img style="top: -4px;" src="../images/New.png" alt="">Áp khuyến mại tích lũy </a>
					</div>									
				  </TD>
				</TR>
				<% } %>
                
            </TABLE>
			
			<hr />
				<table cellpadding="0px" cellspacing="1px" width="100%">
					<tr class="tbheader">
						<% if( lsxBean.getLoaidonhang().equals("0") || lsxBean.getLoaidonhang().equals("3") ) { %>
							<th align="center" width="15%" rowspan="2" >Mã sản phẩm</th>
							<th align="center" width="35%" rowspan="2" >Tên sản phẩm</th>
							<th align="center" width="10%" rowspan="2" >Đơn vị</th>
							<th align="center" width="10%" rowspan="2" >Số lượng</th>
							<th align="center" width="20%" rowspan="2" >Đơn giá <br/>(Sau VAT)</th>
							<th align="center" width="1%" rowspan="2" style="display: none;" >Đơn giá <br/>(Sau CK)</th>
							<th align="center" width="1%" rowspan="2" style="display: none;" >PT CK</th>
							<th align="center" width="1%" rowspan="2" style="display: none;" >Chiết khấu</th>
							<th align="center" width="1%" rowspan="2" style="display: none;" >% VAT</th>
							<th align="center" width="15%" rowspan="2" >Thành tiền</th>
						<% } else { %>
							<th align="center" width="10%" rowspan="2" >Mã sản phẩm</th>
							<th align="center" width="25%" rowspan="2" >Tên sản phẩm</th>
							<th align="center" width="7%" rowspan="2" >Đơn vị</th>
							<th align="center" width="7%" rowspan="2" >Số lượng</th>
							<th align="center" width="6%" rowspan="2" >Đơn giá <br/>(Sau VAT)</th>
							<th align="center" width="1%" rowspan="2" style="display: none;" >Đơn giá <br/>(Sau CK)</th>
							<th align="center" width="1%" rowspan="2" style="display: none;" >PT CK</th>
							<th align="center" width="1%" rowspan="2" style="display: none;" >Chiết khấu</th>
							<th align="center" width="1%" rowspan="2" style="display: none;" >% VAT</th>
							<th align="center" width="8%" rowspan="2" >Thành tiền</th>
						<% } %>
						
						<% if(lsxBean.getLoaidonhang().equals("1")) { %>
							<!-- <th align="center" width="10%" colspan="2" >Ngày giao</th> -->
							<th align="center" width="10%" rowspan="2" >Trình dược viên</th>
							<th align="center" width="10%" rowspan="2" >Scheme</th>
						<% } else if(lsxBean.getLoaidonhang().equals("2")) { %>
							<th align="center" width="15%" rowspan="2" >Scheme</th>
						<% } else { %>
							<!-- <th align="center" width="15%" colspan="2" style="display: none;" >Ngày giao</th> -->
						<% } %>
					</tr>
					
					<tr class="tbheader">
					<% if(lsxBean.getLoaidonhang().equals("1")) { %>
						<th align="center" width="7.5%" style="display: none;">Từ ngày</th>
						<th align="center" width="7.5%" style="display: none;">Đến ngày</th>
					<% } else { %>
						<th align="center" width="7.5%"  style="display: none;" >Từ ngày</th>
						<th align="center" width="7.5%" style="display: none;" >Đến ngày</th>
					<% } %>
					</tr>
					
					<%
						int count = 0;
						if(spMa != null)
						{
							for(int i = 0; i < spMa.length; i++)
							{ 
								String dvdl_fk = "";
								%>
							<%-- <% if( !spSoluong[i].equals("0") ) { %> --%>
							<tr>
								<td>
									<input type="text" name="spMa" value="<%= spMa[i].trim() %>" style="width: 100%"   AUTOCOMPLETE="off" readonly="readonly"  > 
									<input type="hidden" name="spTrongLuong" value="<%= spTrongluong[i] %>" > 
									<input type="hidden" name="spTheTich" value="<%= spThetich[i] %>" > 
									<input type="hidden" name="spQuyDoi" value="<%= spQuyDoi[i] %>"  >
								</td>
								<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
								<td>
									<select name="donvi" style="width: 100%" disabled="disabled" >
										<option value="" ></option>
										<% if(dvtRs != null) 
										{ 
												dvtRs.beforeFirst();
												while(dvtRs.next())
												{
													if(spDonvi[i].equals(dvtRs.getString("DONVI")))
													{ dvdl_fk = dvtRs.getString("PK_SEQ"); %>
														<option value="<%= dvtRs.getString("DONVI") %>" selected="selected" ><%= dvtRs.getString("DONVI") %></option>
													<% } else { %>
														<option value="<%= dvtRs.getString("DONVI") %>" ><%= dvtRs.getString("DONVI") %></option>
												   <% } }
										} %>
									 </select> 
								</td>
								
								<td> 
									<input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width: 55%; text-align: right;" readonly="readonly" > 

									<% if(spSoluong[i].trim().length() > 0 && lsxBean.getCapduyet().equals("CS") && !spSoluong[i].equals("0") ) { %>
									<a href="" id="scheme_<%= spMa[i] + "__" + spSCheme[i] %>" rel="subcontent_<%= spMa[i] + "__" + spSCheme[i] %>">
			           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
			           	 		
		           	 		 		<DIV id="subcontent_<%= spMa[i] + "__" + spSCheme[i] %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                             background-color: white; width: 450px; max-height:300px; overflow:auto; padding: 4px;">
					                    <table width="95%" align="center">
					                    	<tr>
					                    		<td ><b>Tổng xuất</b></td>
					                    		<td colspan="3" > <input type="text" name="soluong2" value="<%= spSoluong[i] %>" style="width: 100px; text-align: right;" readonly="readonly" >	</td>
					                    	</tr>
					                        <tr>
					                            <th width="100px" style="font-size: 11px">Số lượng</th>
					                            <th width="100px" style="font-size: 11px">Số lô</th>
					                            <th width="100px" style="font-size: 11px">Ngày hết hạn</th>
					                            <th width="100px" style="font-size: 11px">Tồn kho</th>
					                        </tr>
			                            	<%
				                            	double daXUAT = 0;
			                            		/* if( sanpham_soluongDAXUAT.get(spMa[i]) != null  )
			                            			daXUAT += Double.parseDouble(sanpham_soluongDAXUAT.get(spMa[i]));
			                            		else
			                            			daXUAT += Double.parseDouble( spSoluong[i].replaceAll(",", "") );
			                            		sanpham_soluongDAXUAT.put(spMa[i], Double.toString(daXUAT) ); */
			                            		
			                            		System.out.println("--- MA HANG: " + spMa[i] + " -- DA XUAT: " + daXUAT );
			                            		ResultSet spRs = lsxBean.getSoloTheoSp(spMa[i], dvdl_fk, spSoluong[i]);  
				                        		if(spRs != null)
				                        		{
				                        			while(spRs.next())
				                        			{
				                        				String tudeXUAT = "";
				                        				if(spRs.getString("tuDEXUAT").trim().length() > 0)
				                        					tudeXUAT = formater2.format(spRs.getDouble("tuDEXUAT"));
				                        				
				                        				String temID = spMa[i] + "__" + spSCheme[i];
				                        				
				                        				sanpham_soluongDAXUAT.put(spMa[i] + "__" + spRs.getString("SOLO"), tudeXUAT );
				                        			%>
				                        			
				                        			<tr>
				                        				<td>
				                        				<% if( sanpham_soluong.get( spMa[i] + "__" + spSCheme[i] + "__" + spRs.getString("SOLO") + "__" +  spRs.getString("NGAYHETHAN") ) != null ) { %>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= formater2.format( Double.parseDouble( sanpham_soluong.get( spMa[i] + "__" + spSCheme[i] + "__" + spRs.getString("SOLO") + "__" +  spRs.getString("NGAYHETHAN") ) ) ) %>" onkeyup="CheckSoLuong_DeXuat(this);" >
				                        				<% } else { %>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= tudeXUAT  %>" onkeyup="CheckSoLuong_DeXuat(this);" >
				                        				<% } %>
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spSOLO" value="<%= spRs.getString("SOLO") %>" readonly="readonly">
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spNGAYHETHAN" value="<%= spRs.getString("NGAYHETHAN") %>" readonly="readonly">
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spTONKHO" value="<%= formater2.format(spRs.getDouble("AVAILABLE")) %>" readonly="readonly">
				                        				</td>
				                        			</tr>
				                        			
				                        		 <% } } %>
				                        		 
					                     </table>
					                     <div align="right">
					                     	<label style="color: red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%= spMa[i] + "__" + spSCheme[i] %>')" > Đóng lại </a>
					                     </div>
						            </DIV> 
						            
						            <script type="text/javascript">
						            	dropdowncontent.init('scheme_<%= spMa[i] + "__" + spSCheme[i]  %>', "left-top", 300, "click");
						            </script>
						         <% } else { %>
						         	<!-- <a href="javascript:void(0);" >&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a> -->
						         <% } %>
									
								</td>
								
								<td> 
									<input type="text" name="dongiaDACOVAT" value="<%= spGianhapGOC[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" <%= lsxBean.getChophepsuagia().equals("0") ? " readonly='readonly' " : "  " %> >
									<input type="hidden" name="dongiaGOC" value="<%= spGianhapGOC[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" <%= lsxBean.getChophepsuagia().equals("0") ? " readonly='readonly' " : "  " %> > 
									
									<input type="hidden" name="soluongton" value="<%= spSoluonton[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);"  > 
								 	<input style="display:none;" type="text" name="soluongtongoc" value="0" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" >
								</td>
								
								<td style="display: none;" > 
									<input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" <%= lsxBean.getChophepsuagia().equals("0") ? " readonly='readonly' " : "  " %> > 
								</td>
								<td style="display: none;" > <input type="text" name="ptchietkhauBHKM" value="<%= spCHIETKHAU_CSKM[i] %>" style="width: 100%; text-align: right;" > </td>
								
								<td style="display: none;" > <input type="text" name="chietkhau" value="<%= spChietkhau[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
								<td style="display: none;" > <input type="text" name="spvat" value="<%= spVat[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" readonly="readonly"  > </td>
								
								<td> 
									<input type="text" name="thanhtien" value="<%= spGianhap[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > 
								</td>
							
								<% if(lsxBean.getLoaidonhang().equals("1")) { %>	
									<td style="display: none;"> <input type="text" name="tungay" class="days" value="<%= spTungay[i] %>" style="width: 100%; text-align: center;" readonly="readonly"  > </td>
									<td style="display: none;"> <input type="text" name="denngay" class="days" value="<%= spDenngay[i] %>" style="width: 100%; text-align: center;" readonly="readonly" > </td>
								
									<td>
										<select name = "spTDV"  style="width: 100%"  >
			                    			<option value=""  > </option>
			                    		
				                        	<%
				                        		if(ddkdRs != null)
				                        		{
				                        			try
				                        			{
				                        			ddkdRs.beforeFirst();	
				                        			while(ddkdRs.next())
				                        			{  
				                        			if( ddkdRs.getString("pk_seq").equals( spTDV[i] )){ %>
				                        				<option value="<%= ddkdRs.getString("pk_seq") %>" selected="selected" ><%= ddkdRs.getString("ten") %></option>
				                        			<%}else { %>
				                        				<option value="<%= ddkdRs.getString("pk_seq") %>" ><%= ddkdRs.getString("ten") %></option>
				                        		 <% } } } catch(SQLException ex){}
				                        		}
				                        	%>
			                    		</select>
									</td>
									
									<td><input type="text" name="scheme" value="<%= spScheme[i] %>" style="width: 100%; color: red; " readonly="readonly" > </td>
								
								<% } else if(lsxBean.getLoaidonhang().equals("2")) { %>
									<td> 
										<input type="text" name="scheme" value="<%= spScheme[i] %>" style="width: 100%; color: red; " readonly="readonly" > 
										<input type="hidden" name="spTDV" value="<%= lsxBean.getDdkdId() %>"  > 
									</td>
								<% } else { %>
									<td style="display: none;"> <input type="text" name="tungay" class="days" value="<%= spTungay[i] %>" style="width: 100%; text-align: center; display: none; " readonly="readonly"  > </td>
									<td style="display: none;"> 
										<input type="text" name="denngay" class="days" value="<%= spDenngay[i] %>" style="width: 100%; text-align: center; display: none; " readonly="readonly" > 
										
										<input type="hidden" name="scheme" value="<%= spScheme[i] %>"  > 
										<input type="hidden" name="spTDV" value="<%= lsxBean.getDdkdId() %>"  > 
									</td>
								<% } %>	
							
							</tr>	
							<%-- <% } %> --%>
								
						<% count ++; } } %>
					
				</table>

     		</div>
     </fieldset>	
    </div>
</div>

<script type="text/javascript">
	replaces();
</script>

<%
	try
	{
		if(sanpham_soluong != null) sanpham_soluong.clone();
		if(sanpham_soluongDAXUAT != null) sanpham_soluongDAXUAT.clone();
		
		dvtRs.close();
		lsxBean.DBclose(); 
	}
	catch(Exception er){ }

	} %>
</form>
</BODY>
</html>