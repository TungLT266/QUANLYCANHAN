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

<% IErpHopdongNpp lsxBean = (IErpHopdongNpp)session.getAttribute("lsxBean"); %>
<% ResultSet dvkdRs = lsxBean.getDvkdRs(); %>
<% ResultSet kbhRs = lsxBean.getKbhRs(); %>
<% ResultSet nppRs = lsxBean.getKhRs(); %>
<% ResultSet khApdungRs = lsxBean.getKhApdungRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet dvtRs = lsxBean.getDvtRs(); %>
<% ResultSet gsbhRs = lsxBean.getGsbhRs(); %>
<% ResultSet ddkdRs = lsxBean.getDdkdRs(); %>
<% ResultSet hopdongRs = lsxBean.getHopdongRs(); %>
<% ResultSet ddhRs = lsxBean.getDonhangmuonRs(); %>

<% 
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spGianhap = lsxBean.getSpGianhap();
	String[] spChietkhau = lsxBean.getSpChietkhau();
	String[] spVat = lsxBean.getSpVat();
	String[] spTungay = lsxBean.getSpTungay();
	String[] spDenngay = lsxBean.getSpDenngay();
	String[] spTrongluong = lsxBean.getSpTrongluong();
	String[] spThetich = lsxBean.getSpThetich();
	String[] spQuyDoi = lsxBean.getSpQuyDoi();
	
	String[] spTDV = lsxBean.getSpTDV();
	
	String[] dhCk_diengiai = lsxBean.getDhck_diengiai();
	String[] dhCk_giatri = lsxBean.getDhck_giatri();
	String[] dhCk_loai = lsxBean.getDhck_loai();
	
	NumberFormat formater = new DecimalFormat("##,###,###");
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
<script type="text/javascript" src="../scripts/erp-SpListNhapKho.js"></script>

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
		var dongia = document.getElementsByName("dongia");
		var thanhtien = document.getElementsByName("thanhtien");
		var dongiaSAUVAT = document.getElementsByName("dongiaSAUVAT");
		
		var trongluong = document.getElementsByName("spTrongLuong");
		var thetich = document.getElementsByName("spTheTich");
		var spQuyDoi = document.getElementsByName("spQuyDoi");
		
		var spvat = document.getElementsByName("spvat");
		
		var i;
		for(i = 0; i < spMa.length; i++)
		{
			if(spMa.item(i).value != "")
			{
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
					
					spvat.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					
					//trongluong.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					//sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					//thetich.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					//sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					//spQuyDoi.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					//sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					//dongia.item(i).value = DinhDangTien(sp.substring(0, parseInt(sp.indexOf("]")))); 
					dongia.item(i).value = '';
					
				}
			}
			else
			{
				spMa.item(i).value = "";
				spTen.item(i).value = "";
				donvi.item(i).value = "";
				soluong.item(i).value = "";
				dongia.item(i).value = "";
				dongiaSAUVAT.item(i).value = "";
				thanhtien.item(i).value = "";	
				trongluong.item(i).value = "";	
				thetich.item(i).value = "";	
				spQuyDoi.item(i).value = "";
				spvat.item(i).value = "";
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
		var chietkhau = document.getElementsByName("chietkhau");
		var thueVAT = document.getElementsByName("spvat");
		var thanhtien = document.getElementsByName("thanhtien");
		
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
		
		for(var i = 0; i < spMa.length; i++)
		{
			if(spMa.item(i).value != "" && dongia.item(i).value != "" && soluong.item(i).value != "" )
			{
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
				
				var _ck = chietkhau.item(i).value.replace(/,/g,"");
				if(_ck == '')
					_ck = '0';
				var tienCK = parseFloat(_ck) * 	parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")) / 100.0;
				
				var dongiaCOVAT = Math.round ( parseFloat(dongia.item(i).value.replace(/,/g,""))  * ( 1 +  parseFloat(_thueVAT) / 100 ) );
				
				//var tt = parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")) - parseFloat(tienCK);
				//tt = parseFloat(tt) * ( 1 +  parseFloat(_thueVAT) / 100 );
				
				tt = Math.round ( parseFloat(soluong.item(i).value.replace(/,/g,"")) * parseFloat( dongiaCOVAT ) - parseFloat(tienCK) );
				thanhtien.item(i).value = DinhDangTien(tt);
				
				/* tongtien += tt;
				tongtienCK += parseFloat(_ck);
				tongtienVAT += parseFloat(tt) * ( parseFloat(_thueVAT) / 100 ); */
				
				tongtien += Math.round ( ( parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")) ) );
				//tongtienCK += parseFloat( _ck );
				tongtienCK += parseFloat( tienCK );
				tongtienVAT += Math.round(  ( parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")) - parseFloat(_ck) ) * ( parseFloat(_thueVAT) / 100 ) );
			}		
		}
		
	
		/* var dhCK_diengiai = document.getElementsByName("dhCK_diengiai");
		var dhCK_giatri = document.getElementsByName("dhCK_giatri");
		var dhCK_loai = document.getElementsByName("dhCK_loai"); */
		
		var tongDhCK = 0;
		var tongtien_sau_hoahong = 0;
		
		/* for(var j = 0; j < dhCK_giatri.length; j++ )
		{
			if(dhCK_giatri.item(j).value != '' )
			{
				var loai = dhCK_loai.item(j).value;
				if(loai == '0') //tien
					tongDhCK += parseFloat(dhCK_giatri.item(j).value.replace(/,/g,""));
				else  //CHIET KHAU
				{
					tongDhCK += parseFloat(dhCK_giatri.item(j).value.replace(/,/g,"")) * parseFloat(tongtien_sau_hoahong) / 100;
				}
			}
		} */
		
		tongtienCK += parseFloat(tongDhCK);
		
		var tongtienSAUCK = parseFloat(tongtien) - parseFloat(tongtienCK);
		
		document.getElementById("txtTongCK").value = DinhDangTien(tongtienCK);
		document.getElementById("txtBVAT").value = DinhDangTien(tongtien);
		document.getElementById("txtTongSauCK").value = DinhDangTien(tongtienSAUCK);
		
		document.getElementById("txtVAT").value = DinhDangTien(tongtienVAT);
		
		//var tongtienSAUVAT = parseFloat(tongtienSAUCK) + ( parseFloat(vat) * parseFloat(tongtienSAUCK) / 100 );
		var tongtienSAUVAT = parseFloat(tongtienSAUCK) + ( parseFloat(tongtienVAT) );
		document.getElementById("txtSauVAT").value = DinhDangTien(tongtienSAUVAT);
		
	 }
	
	 function CapNhatGia(e, pos)
	 { 
		 var nppId= document.getElementById("nppId").value;
		var kbhId= document.getElementById("kbhId").value;
		var dvkdId = document.getElementById("dvkdId").value;
		var dvdlId = document.getElementsByName("donvi");
		var spId = document.getElementsByName("spMa");
		var spMa = document.getElementsByName("spMa");
		var dongia=document.getElementsByName("dongia");
		var ngaychuyen = document.getElementById("ngaychuyen").value;
		var spQuyDoi=document.getElementsByName("spQuyDoi");
		var spTrongLuong = document.getElementsByName("spTrongLuong") ;
		var spTheTich = document.getElementsByName("spTheTich") ;
		
		 $( dongia.item(pos) ).val( "" );
		 $( spQuyDoi.item(pos) ).val( "" );
		 $( spTrongLuong.item(pos)).val( "" );
		 $( spTheTich.item(pos)).val( "" );
		 
		$.ajax
		(
			{
		    url: "../../ErpHopdongNppSvl?type=GetDonGia&spMa=" + spMa.item(pos).value + "&dvdlId=" + dvdlId.item(pos).value + "&nppId=" + nppId+"&kbhId="+kbhId+"&dvkdId="+dvkdId+""  ,
		    type : 'GET',
		    dataType: 'json',
		    success: function( data )
		    {
		        var npp = data ;
		        console.log(data);
		       $( dongia.item(pos)        ).val( npp.dongia );
		       $( spQuyDoi.item(pos)         ).val( npp.quycach );
		       $( spTheTich.item(pos)         ).val( npp.thetich );
		       $( spTrongLuong.item(pos)         ).val( npp.trongluong );
		       
		       CapNhatSAUVAT(pos);
		       
		     }
		});
		
		CapNhatSAUVAT(pos);
		 
	 }
	 
		 
	 function saveform()
	 {	
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['hctmhForm'].action.value='submit';
	     document.forms["hctmhForm"].submit();
	 }
	 
	 function changePhuLuc()
	 {
		 document.forms['hctmhForm'].action.value='changePhuLuc';
	     document.forms["hctmhForm"].submit();
	 }
	 
	 function Apkhuyenmai()
	 {
		 document.getElementById("btnApKhuyenMai").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		 document.forms['hctmhForm'].action.value='apkhuyenmai';
		 document.forms['hctmhForm'].submit();
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
	
	function CapNhatSAUVAT(stt)
	{
		var spvat = document.getElementsByName("spvat");
		var dongia = document.getElementsByName("dongia");
		var dongiaSAUVAT = document.getElementsByName("dongiaSAUVAT");
		
		if(dongia.item(stt).value != '')
		{
			//parseFloat(dhCK_giatri.item(j).value.replace(/,/g,""));
			
			var _dg = dongia.item(stt).value.replace(/,/g,"");
			var _vat = spvat.item(stt).value.replace(/,/g,"");
			
			//alert('DG: ' + _dg + ' - VAT: ' + _vat );
			dongiaSAUVAT.item(stt).value = DinhDangTien( Math.round( parseFloat( _dg ) * (  1 + parseFloat( _vat ) / 100 ) )  );
			//dongiaSAUVAT.item(stt).value = toFixed ( ( parseFloat( _dg ) * (  1 + parseFloat( _vat ) / 100 ) ), 2 );
		}
	}
	
	function CapNhatVAT(stt)
	{
		var spvat = document.getElementsByName("spvat");
		var dongia = document.getElementsByName("dongia");
		var dongiaSAUVAT = document.getElementsByName("dongiaSAUVAT");
		
		if(dongiaSAUVAT.item(stt).value != '')
		{
			//parseFloat(dhCK_giatri.item(j).value.replace(/,/g,""));
			
			var _dg = dongiaSAUVAT.item(stt).value.replace(/,/g,"");
			var _vat = spvat.item(stt).value.replace(/,/g,"");
			
			//alert('DG: ' + _dg + ' - VAT: ' + _vat ); 100 / ( 100.0 + 5 )
			//dongia.item(stt).value = parseFloat( _dg ) * (  1 - parseFloat( _vat ) / 100 );
			
			dongia.item(stt).value = toFixed (  parseFloat( _dg ) * (  100 / ( 100 + parseFloat( _vat ) ) ), 4 ) ;
		}
	}
	
	function toFixed(value, precision) 
	{
	    var precision = precision || 0,
	    neg = value < 0,
	    power = Math.pow(10, precision),
	    value = Math.round(value * power),
	    integral = String((neg ? Math.ceil : Math.floor)(value / power)),
	    fraction = String((neg ? -value : value) % power),
	    padding = new Array(Math.max(precision - fraction.length, 0) + 1).join('0');

	    return precision ? integral + '.' +  padding + fraction : integral;
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

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpHopdongNppUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="nppId" value='<%= lsxBean.getNppId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý bán hàng > Bán hàng thầu > Hợp đồng > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpHopdongNppSvl?userId=<%= userId %>&loaidonhang=<%= lsxBean.getLoaidonhang() %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
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
    	<legend class="legendtitle">Đơn đặt hàng </legend>
        	<div style="float:none; width:100%" align="left">
        	
            <TABLE width="100%" cellpadding="4" cellspacing="0">	
            	<TR>
                    <TD class="plainlabel" >Mã hợp đồng </TD>
                    <TD class="plainlabel"  colspan="3" >
                    	<input type="text" name="mahopdong" value="<%= lsxBean.getMahopdong() %>"/>
                    </TD>
                </TR>						
                <TR>
                    <TD width="150px" class="plainlabel" >Từ ngày </TD>
                    <TD width="300px"  class="plainlabel"  >
                    	<input type="text" class="days" readonly="readonly"   id="hopdong_tungay" name="hopdong_tungay" value="<%= lsxBean.getTungay() %>"/>
                    </TD>
                    
                    <TD width="120px" class="plainlabel" >Đến ngày </TD>
                    <TD class="plainlabel" >
                    	<input type="text" class="days" readonly="readonly"   id="hopdong_denngay"  name="hopdong_denngay" value="<%= lsxBean.getDenngay() %>"/>
                    </TD>
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
                    <TD class="plainlabel" >Kênh bán hàng </TD>
                    <TD class="plainlabel"    >
                    	<select name = "kbhId"   id="kbhId"  class="select2" style="width: 200px" >
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
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Giám sát </TD>
                    <TD class="plainlabel"   >
                    	<%-- <input type="hidden" name="gsbhId" value="<%= lsxBean.getGsbhId() %>"  > --%>
                    	<select name="gsbhId" class="select2" style="width: 200px"  >
                    		<option value=""> </option>
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
                    	<select name = "ddkdId"  class="select2" style="width: 200px"  >
                    		<!-- <option value="" ></option> -->
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
                    	
                    	&nbsp;&nbsp;
                    	<% if( lsxBean.getCapnhatTDV().equals("1") ) { %>
                    		<input type="checkbox" name="capnhatTDV" value="1" checked="checked" onchange="submitform();" > Cập nhật tất cả TDV
                    	<% } else { %>
                    		<input type="checkbox" name="capnhatTDV" value="1" onchange="submitform();" > Cập nhật tất cả TDV
                    	<% } %>
                    	
                    </TD>         	
                </TR>
                
                <TR >
                	<TD class="plainlabel">Loại hợp đồng </TD>
                    <TD class="plainlabel"  >
                    	<select name = "loaidonhang"  class="select2" style="width: 200px" onchange="submitform();"  >
                        	<% if(lsxBean.getLoaidonhang().equals("0")) { %>
                        		<option value="0" selected="selected" >INS</option>
                        	<% } else { %>
                        		<option value="0"  >INS</option>
                        	<% } %>
                        	<% if(lsxBean.getLoaidonhang().equals("1")) { %>
                        		<option value="1" selected="selected" >Phụ lục</option>
                        	<% } else { %>
                        		<option value="1"  >Phụ lục</option>
                        	<% } %>
                        	<% if(lsxBean.getLoaidonhang().equals("2")) { %>
                        		<option value="2" selected="selected" >CLC</option>
                        	<% } else { %>
                        		<option value="2"  >CLC</option>
                        	<% } %>
                        	<% if(lsxBean.getLoaidonhang().equals("3")) { %>
                        		<option value="3" selected="selected" >Sở y tế</option>
                        	<% } else { %>
                        		<option value="3"  >Sở y tế</option>
                        	<% } %>
                    	</select>
                    </TD> 
                    
                    <TD class="plainlabel" >Kho đặt hàng</TD>
                    <TD class="plainlabel"   >
                    	<select name = "khonhapId" class="select2" style="width: 200px" >
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
                
                <% if (!lsxBean.getLoaidonhang().equals("3")) { %>
                <TR>
                	<TD class="plainlabel" >Khách hàng </TD>
                    <TD class="plainlabel"   >
                    	<select name = "khId" id="khId"  class="select2" style="width: 200px" onchange="submitform();" >
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
                    
                    <% if(lsxBean.getLoaidonhang().equals("1")) { %> 
                    	<TD class="plainlabel" >Phụ lục của HĐ </TD>
                    <% } else { %>
                    	<TD class="plainlabel" >Hợp đồng </TD>
                    <% } %>
                    
                    <TD class="plainlabel"    >
                    	<select name = "hopdongId"  class="select2" style="width: 200px" onchange="changePhuLuc();" >
                    		<option value="" ></option>
                        	<%
                        		if(hopdongRs != null)
                        		{
                        			try
                        			{
                        			while(hopdongRs.next())
                        			{  
                        			if( hopdongRs.getString("pk_seq").equals(lsxBean.getHopdongId())){ %>
                        				<option value="<%= hopdongRs.getString("pk_seq") %>" selected="selected" ><%= hopdongRs.getString("diengiai") %></option>
                        			<%}else { %>
                        				<option value="<%= hopdongRs.getString("pk_seq") %>" ><%= hopdongRs.getString("diengiai") %></option>
                        		 <% } } 
                        			hopdongRs.close();} 
                        			catch(Exception ex){ ex.printStackTrace(); }
                        		}
                        	%>
                    	</select>
                    </TD>  	
                </TR>
                <% } else { %>
                <TR>
                	<TD class="plainlabel" >Sở y tế áp dụng </TD>
                    <TD class="plainlabel" colspan="3"  >
                    	<select name = "khApdungId" id="khApdungId"  class="select2" style="width: 700px" multiple="multiple" >
                    		<option value=""> </option>
                        	<%
                        		if(khApdungRs != null)
                        		{
                        			try
                        			{
                        			while(khApdungRs.next())
                        			{  
                        			if( lsxBean.getKhApdungId().contains(khApdungRs.getString("pk_seq"))) { %>
                        				<option value="<%= khApdungRs.getString("pk_seq") %>" selected="selected" ><%= khApdungRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khApdungRs.getString("pk_seq") %>" ><%= khApdungRs.getString("ten") %></option>
                        		 <% } } khApdungRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD> 
                </TR>
                <% } %>
                
                <TR>
                	<TD class="plainlabel" >Đơn hàng mượn</TD>
                    <TD class="plainlabel" colspan="3" >
                    	<select name = "ddhIds" class="select2" style="width: 200px" multiple="multiple"  onchange="changePhuLuc();" >
                    		<option value=""> </option>
                        	<%
                        		if(ddhRs != null)
                        		{
                        			try
                        			{
                        			while(ddhRs.next())
                        			{  
                        			if( lsxBean.getDonhangmuonIds().contains(ddhRs.getString("pk_seq")) ){ %>
                        				<option value="<%= ddhRs.getString("pk_seq") %>" selected="selected" ><%= ddhRs.getString("TEN") %></option>
                        			<%}else { %>
                        				<option value="<%= ddhRs.getString("pk_seq") %>" ><%= ddhRs.getString("TEN") %></option>
                        		 <% } } ddhRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Tổng thành tiền </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text" readonly="readonly"  id="txtBVAT" value="" style="text-align: right;"  />
                    	
                    
                    	<input type="hidden"  value="<%= lsxBean.getChietkhau() %>" id="txtPTChietKhau" style="text-align: right;" name="ptChietkhau" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Tổng tiền chiết khấu </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" readonly="readonly"  value="" id="txtTongCK" style="text-align: right; width: 200px; " />
                    </TD>
                    	
                    <TD class="plainlabel" >Tổng tiền sau CK </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  value="" id="txtTongSauCK" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Tổng tiền VAT </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" value="<%= lsxBean.getVat() %>" id="txtVAT" style="text-align: right;" name="ptVat" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);"  readonly='readonly'  />
                    </TD>
                    	
                    <TD class="plainlabel"  style="color: red;" >Tổng tiền sau VAT </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  value="" id="txtSauVAT" style="text-align: right;" />
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
                
                <TR  >
                    <TD class="plainlabel" >Ngày trúng thầu </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" value="<%= lsxBean.getNgaytrungthau() %>" name="ngaytrungthau" class="days" />
                    </TD>
                    	
                    <TD class="plainlabel"  >Chi phí bảo lãnh </TD>
                    <TD class="plainlabel" >
                    	<input type="text" value="<%= lsxBean.getChiphibaolanh() %>" name="chiphibaolanh" style="text-align: right;"  onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" /> 
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Ghi chú </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 700px;" />
                    </TD>
                </TR>
                
            </TABLE>
			
			<hr />
				<table cellpadding="0px" cellspacing="1px" width="100%">
					<!-- <tr class="tbheader">
						<th align="center" width="3%" rowspan="2" >STT</th>
						<th align="center" width="8%" rowspan="2" >Mã sản phẩm</th>
						<th align="center" width="15%" rowspan="2" >Tên sản phẩm</th>
						<th align="center" width="7%" rowspan="2" >Đơn vị</th>
						<th align="center" width="8%" rowspan="2" >Số lượng</th>
						<th align="center" width="7%" rowspan="2" >Đơn giá</th>
						<th align="center" width="7%" rowspan="2" >Đơn giá<br />Sau VAT</th>
						<th align="center" width="7%" rowspan="2" >Chiết khấu</th>
						<th align="center" width="6%" rowspan="2" >% VAT</th>
						<th align="center" width="7%" rowspan="2" >Thành tiền</th>
						<th align="center" width="15%" colspan="2" >Ngày giao</th>
						
						<th align="center" width="10%" rowspan="2" >Trình dược viên</th>
					</tr>
					
					<tr class="tbheader">
						<th align="center" width="7.5%" >Từ ngày</th>
						<th align="center" width="7.5%" >Đến ngày</th>
					</tr> -->
					
					<tr class="tbheader">
						<th align="center" width="3%"  >STT</th>
						<th align="center" width="8%"  >Mã sản phẩm</th>
						<th align="center" width="20%"  >Tên sản phẩm</th>
						<th align="center" width="7%" >Đơn vị</th>
						<th align="center" width="8%"  >Số lượng</th>
						<th align="center" width="7%" >Đơn giá</th>
						<th align="center" width="7%"  >Đơn giá<br />Sau VAT</th>
						<th align="center" width="7%" >Chiết khấu</th>
						<th align="center" width="6%"  >% VAT</th>
						<th align="center" width="7%" >Thành tiền</th>

						<th align="center" width="10%"  >Trình dược viên</th>
					</tr>
					
					<%
						int count = 0;
						if(spMa != null)
						{
							for(int i = 0; i < spMa.length; i++)
							{%>
						
							<tr>
								<td align="center"> <%= i+1 %> </td>
								<td>
									<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
									<input type="hidden" name="spTrongLuong" value="<%= spTrongluong[i] %>" > 
									<input type="hidden" name="spTheTich" value="<%= spThetich[i] %>" > 
									<input type="hidden" name="spQuyDoi" value="<%= spQuyDoi[i] %>"  >
								</td>
								<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
								<td>
									<select name="donvi" style="width: 100%" onchange="CapNhatGia(this, <%= count %>);" >
										<option value="" ></option>
										<% if(dvtRs != null) 
										{ 
												dvtRs.beforeFirst();
												while(dvtRs.next())
												{
													if(spDonvi[i].equals(dvtRs.getString("DONVI")))
													{ %>
														<option value="<%= dvtRs.getString("DONVI") %>" selected="selected" ><%= dvtRs.getString("DONVI") %></option>
													<% } else { %>
														<option value="<%= dvtRs.getString("DONVI") %>" ><%= dvtRs.getString("DONVI") %></option>
												   <% } }
										} %>
									 </select> 
								</td>
								
								<td> 
									<input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > 
								</td>
								<td> <input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" <%= lsxBean.getLoaidonhang().equals("1") ? " readonly='readonly' " : " " %> onkeyup="CapNhatSAUVAT(<%= count %>);"  > </td>
								<td> <input type="text" name="dongiaSAUVAT" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" <%= lsxBean.getLoaidonhang().equals("1") ? " readonly='readonly' " : " " %> onkeyup="CapNhatVAT(<%= count %>);" > </td>
								<td> <input type="text" name="chietkhau" value="<%= spChietkhau[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" <%= lsxBean.getLoaidonhang().equals("1") ? " readonly='readonly' " : "" %> > </td>
								<td> <input type="text" name="spvat" value="<%= spVat[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" readonly="readonly"  <%= lsxBean.getLoaidonhang().equals("1") ? " readonly='readonly' " : "" %> > </td>
								<td> <input type="text" name="thanhtien" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
								
								<td style="display: none;" > <input type="text" name="tungay"  value="<%= spTungay[i] %>" style="width: 100%; text-align: center;" <%= lsxBean.getLoaidonhang().equals("1") ? " readonly='readonly' " : " class='days' " %> > </td>
								<td style="display: none;" > <input type="text" name="denngay" value="<%= spDenngay[i] %>" style="width: 100%; text-align: center;" <%= lsxBean.getLoaidonhang().equals("1") ? " readonly='readonly' " : " class='days' " %> > </td>
								
								<td>
									<select name = "spTDV"  style="width: 100%" class="select2" >
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
								
							</tr>	
								
						<% count ++; } } %>
					
					<% for(int i = count; i < 50; i++) { %>
						
						<tr>
							<td align="center"> <%= i+1 %> </td>
							<td>
								<input type="text" name="spMa" value="" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
								<input type="hidden" name="spTrongLuong" value="" > 
								<input type="hidden" name="spTheTich" value="" > 
								<input type="hidden" name="spQuyDoi" value=""  >
							</td>
							<td> <input type="text" name="spTen" value="" style="width: 100%" readonly="readonly"> </td>
							<td>
								<select name="donvi" style="width: 100%" onchange="CapNhatGia(this, <%= i %>);" >
									<option value="" ></option>
										<% if(dvtRs != null) 
										{ 
											dvtRs.beforeFirst();
											while(dvtRs.next())
											{ %>
												<option value="<%= dvtRs.getString("DONVI") %>"  ><%= dvtRs.getString("DONVI") %></option>
										   <% } 
										} %>
									 </select> 
							 </td>
							
							<td> <input type="text" name="soluong" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
							<td> <input type="text" name="dongia" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="CapNhatSAUVAT(<%= i %>);" > </td>
 							<td> <input type="text" name="dongiaSAUVAT" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="CapNhatVAT(<%= i %>);" > </td>
							<td> <input type="text" name="chietkhau" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
							<td> <input type="text" name="spvat" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" readonly="readonly" > </td>
							<td> <input type="text" name="thanhtien" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							
							<td style="display: none;" > <input type="text" name="tungay" class="days" value="" style="width: 100%; text-align: center;"  > </td>
							<td style="display: none;" > <input type="text" name="denngay" class="days" value="" style="width: 100%; text-align: center;"  > </td>
							
							<td>
								<select name = "spTDV"  style="width: 100%" class="select2" >
	                    			<option value=""  > </option>
	                    		
		                        	<%
		                        		if(ddkdRs != null)
		                        		{
		                        			try
		                        			{
		                        			ddkdRs.beforeFirst();	
		                        			while(ddkdRs.next())
		                        			{  
		                        			if( ddkdRs.getString("pk_seq").equals( "" )){ %>
		                        				<option value="<%= ddkdRs.getString("pk_seq") %>" selected="selected" ><%= ddkdRs.getString("ten") %></option>
		                        			<%}else { %>
		                        				<option value="<%= ddkdRs.getString("pk_seq") %>" ><%= ddkdRs.getString("ten") %></option>
		                        		 <% } } } catch(SQLException ex){}
		                        		}
		                        	%>
	                    		</select>
							</td>
							
						</tr>	
						
					<% } %>	
						
				</table>
			
     		
     		</div>
     </fieldset>	
    </div>
</div>

<script type="text/javascript">
	replaces();
	
	function ResetSAUVAT()
	{
		var spMa = document.getElementsByName("spMa");
		var dongia = document.getElementsByName("dongia");
		var dongiaSAUVAT = document.getElementsByName("dongiaSAUVAT");
		var thueVAT = document.getElementsByName("spvat");

		for(var i = 0; i < spMa.length; i++)
		{
			if(spMa.item(i).value != "" && dongia.item(i).value != "" )
			{
				var _dg = dongia.item(i).value.replace(/,/g,"");
				var _vat = thueVAT.item(i).value.replace(/,/g,"");
	
				dongiaSAUVAT.item(i).value = DinhDangTien ( Math.round ( parseFloat( _dg ) * (  1 + parseFloat( _vat ) / 100 ) ) );
			}	
		}
	}
	
	ResetSAUVAT();
	
</script>

<%
	try
	{
		dvtRs.close();

		if( ddkdRs != null )
			ddkdRs.close();
		lsxBean.DBclose(); 
	}
	catch(Exception er){ } 
}
 %>
</form>
</BODY>
</html>