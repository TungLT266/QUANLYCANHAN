<%@page import="org.apache.log4j.TTCCLayout"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.distributor.beans.hoadonchuyenkhoNPP.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.hoadonchuyenkhoNPP.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="java.util.Enumeration"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% IErpHoadonchuyenkhoNPP hdBean = (IErpHoadonchuyenkhoNPP)session.getAttribute("hdBean"); %>
<% ResultSet khRs = hdBean.getKhRs(); %>
<% ResultSet nppRs = hdBean.getNppRs(); %>
<% ResultSet ddhRs = hdBean.getDondathangRs(); %>
<% ResultSet kbhRs = hdBean.getKbhRs(); %>
<% ResultSet nvRs = hdBean.getNhanvienRs(); %>
<% ResultSet congnoRs = hdBean.getCongnoRs(); %>
<% ResultSet invoiceRs = hdBean.getInvoiceInfo(""); %>
<% 
	String[] spCkId = hdBean.getCkId();
	String[] spId = hdBean.getSpId();
	String[] spMa = hdBean.getSpMa(); 
	String[] spTen = hdBean.getSpTen();
	String[] spDonvi = hdBean.getSpDonvi();
	String[] spLoai = hdBean.getSpLoai();
	String[] spSoluong = hdBean.getSpSoluong();
	String[] spDongia = hdBean.getSpDongia();
	String[] spChietkhau = hdBean.getSpChietkhau();
	String[] spSCheme = hdBean.getSpScheme();
	String[] spVat = hdBean.getSpVat();
	String[] spTienThue = hdBean.getSpTienthue();
	String[] spThanhtien = hdBean.getSpThanhtien();
	String[] spChonin = hdBean.getSpChonin();
	
	NumberFormat formater = new DecimalFormat("##,###,###");
	
	String[] dhCk_diengiai = hdBean.getDhck_diengiai();
	String[] dhCk_giatri = hdBean.getDhck_giatri();
	String[] dhCk_loai = hdBean.getDhck_loai();
	
	String[] tenxuatHD = hdBean.getTenXuatHD();
	
	Hashtable<String, String> sanpham_soluong = hdBean.getSanpham_Soluong();
	Hashtable<String, String> schemeTichluy = hdBean.getSchemeTichluy();
	
%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
    NumberFormat formatter = new DecimalFormat("#,###,###"); 
    NumberFormat formatter2 = new DecimalFormat("#,###,###.####"); 
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else { %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Phanam - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>


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

.mySCHME tr,td input
{
	color: red;
}

.mySCHME input
{
	color: red;
}

</style>

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
	});	
</script>
<script type="text/javascript">
            $(function() {
                $('#tableDDH').scrollTableBody({rowsToDisplay:4});
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
	
	function SuaDonGia(stt)
	{
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("spDongia");
		var chietkhau = document.getElementsByName("spChietkhau");
		var spVat = document.getElementsByName("spVat");
		var tienTHUE = document.getElementsByName("spTienthue");

		if( soluong.item(stt).value != '' && dongia.item(stt).value != '' )
		{
			var _soluong = parseFloat(soluong.item(stt).value.replace(/,/g,""));
			var _dongia = parseFloat(dongia.item(stt).value.replace(/,/g,""));
			
			var _ck = chietkhau.item(stt).value.replace(/,/g,"");
			if(_ck == '')
				_ck = 0;
			
			var _vat = spVat.item(stt).value.replace(/,/g,"");
			if(_vat == '')
				_vat = 0;
			
			tienTHUE.item(stt).value = ( Math.round( _soluong * _dongia ) - _ck ) * _vat / 100 ;
		} 
		
		TinhTien();
	}
	
	function SuaThanhTien(stt)
	{
		//alert('STT: ' + stt );
		/* var nppDNId = document.getElementById("nppDNId").value;

		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("spDongia");
		var chietkhau = document.getElementsByName("spChietkhau");
		var tienTHUE = document.getElementsByName("spTienthue");
		var thanhtien = document.getElementsByName("thanhtien");
		
		if( soluong.item(stt).value != '' && thanhtien.item(stt).value != '' )
		{
			var _soluong = parseFloat(soluong.item(stt).value.replace(/,/g,""));
			var _thanhtien = parseFloat(thanhtien.item(stt).value.replace(/,/g,""));
			
			var _ck = chietkhau.item(stt).value.replace(/,/g,"");
			if(_ck == '')
				_ck = '0';
			
			var _vat = tienTHUE.item(stt).value.replace(/,/g,"");
			if(_vat == '')
				_vat = '0';
			
			//alert('CK: ' + _ck + ' - VAT: ' + _vat + ' - TT: ' + _thanhtien + ' - SO LUONG: ' + _soluong );
			dongia.item(stt).value = ( parseFloat( _thanhtien ) - parseFloat( _vat ) + parseFloat( _ck ) ) / parseFloat( _soluong );
			//alert('DG: ' + dongia.item(stt).value);
		}
		
		TinhTien(); */
	}
	
	function SuaTienThue(stt)
	{
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("spDongia");
		var chietkhau = document.getElementsByName("spChietkhau");
		var tienTHUE = document.getElementsByName("spTienthue");
		var thanhtien = document.getElementsByName("thanhtien");
		
		if( soluong.item(stt).value != '' && thanhtien.item(stt).value != '' )
		{
			var _soluong = parseFloat(soluong.item(stt).value.replace(/,/g,""));
			var _dongia = parseFloat(dongia.item(stt).value.replace(/,/g,""));
			
			var _ck = chietkhau.item(stt).value.replace(/,/g,"");
			if(_ck == '')
				_ck = '0';
			
			var _vat = tienTHUE.item(stt).value.replace(/,/g,"");
			if(_vat == '')
				_vat = '0';
			
			thanhtien.item(stt).value = ( Math.round( _soluong * _dongia ) - _ck ) + _vat;
		}
		
		TinhTien();
	 }
	
	function TinhTien()
	 {	
			var nppDNId = document.getElementById("nppDNId").value;
			var spMa = document.getElementsByName("spMa");
			var soluong = document.getElementsByName("soluong");
			var dongia = document.getElementsByName("spDongia");
			var chietkhau = document.getElementsByName("spChietkhau");
			var spVat = document.getElementsByName("spVat");
			var thanhtien = document.getElementsByName("thanhtien");
			var spTienthue = document.getElementsByName("spTienthue");
			
			var totalCK = 0;  // totalCK= chiet khau sp + ck don hang
			var tongtien = 0;
			var ck= 0;
			var totalvat=0;
			
			
			for(var i = 0; i < spMa.length; i++)
			{
				if(spMa.item(i).value != "" && dongia.item(i).value != "" && soluong.item(i).value != "" )
				{
					var ck_sp = chietkhau.item(i).value.replace(/,/g,"");
					if(ck_sp == '')
						ck_sp = '0';
					
					var tt = Math.round(parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")));
					var tt_coCK = Math.round(parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,""))) - parseFloat(ck_sp);
					//thanhtien.item(i).value = DinhDangTien(tt);
					//alert(thanhtien.item(i).value);
					
					//var vat_sp = parseFloat(tt_coCK) * spVat.item(i).value.replace(/,/g,"")/100;
					var vat_sp = spTienthue.item(i).value.replace(/,/g,"");
					if(vat_sp == '')
						vat_sp = parseFloat(tt_coCK) *  parseFloat ( spVat.item(i).value.replace(/,/g,"") ) / 100;
					
					//alert('TT: ' + tt);
					thanhtien.item(i).value = DinhDangTien( tt );
					
					//alert('TT truoc: ' + parseFloat(tt) + ' -- VAT: ' + parseFloat(vat_sp) + '  -- CK SP: ' + parseFloat(ck_sp) );
					//tt = Math.round( parseFloat(tt) + parseFloat(vat_sp) - parseFloat(ck_sp) );
					tongtien += parseFloat( tt);
					
					ck =  parseFloat(ck) + parseFloat(ck_sp);
					
					totalvat += parseFloat(vat_sp);
				}
				
				//alert('TONG TIEN: ' + tongtien + "  -- TONG VAT: " +  totalvat);
				totalCK = parseFloat(ck);
				
				document.getElementById("tongtien").value = DinhDangTien(Math.round(tongtien));
				document.getElementById("tongchietkhau").value = DinhDangTien(Math.round(totalCK));
				//document.getElementById("tiensauCK").value = DinhDangTien(Math.round(parseFloat(tongtien)- parseFloat(totalCK)));
				
				document.getElementById("tienvat").value = DinhDangTien(Math.round(parseFloat(totalvat)));
				
				var tienSAUVAT = Math.round((parseFloat(tongtien)- parseFloat(totalCK)) + parseFloat(totalvat));
				document.getElementById("tiensauvat").value = DinhDangTien( tienSAUVAT );
				
				var tientichluy = document.getElementById("tientichluy").value.replace(/,/g,"");
				if( tientichluy == '' )
					tientichluy = 0;
				
				tienSAUVAT = parseFloat( tienSAUVAT ) - parseFloat( tientichluy );
				document.getElementById("tiensauvat_sauTL").value = DinhDangTien( tienSAUVAT );
			}
	
	 }
	
	 function saveform()
	 {		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function chotform()
	 {	
		 document.getElementById("btnSave2").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'chot';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['hctmhForm'].action.value='submit';
	     document.forms["hctmhForm"].submit();
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
		
		//UPDATE
		Update_SoLuong();
		
	}	
	
	 function loadcontent()
	 {
		document.forms['hctmhForm'].action.value='reload';
	    document.forms["hctmhForm"].submit();
	 }
	 function goBack()
	 {
		 window.history.go(-1);
	 }
	 
	  
	 function ReadOnly(pos)
	 {
		 var chonin = document.getElementById("spChonin" + pos);
		 
		 if(chonin.checked == false)
		 {			
			chonin.value = '0';
		 } 
		 
		 if(chonin.checked == true)
		 {
			chonin.value = '1';	 
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
		var scheme = document.getElementsByName("scheme");
		var soluong = document.getElementsByName("soluong");
		var soluong2 = document.getElementsByName("soluong2");
		
		//alert('SO DONG: ' + spMa.length )
		for(var i = 0; i < spMa.length; i++ )
		{
			//alert( 'NAME LA: ' + ( spMa.item(i).value + "_" + scheme.item(i).value + "_spSOLUONG" ) );
			var soluongDEXUAT = document.getElementsByName(spMa.item(i).value + "_" + scheme.item(i).value + "_spSOLUONG");
			
			var totalXUAT = 0;
			for(var j = 0; j < soluongDEXUAT.length; j++ )
			{
				if( soluongDEXUAT.item(j).value != '' )
					totalXUAT = parseFloat(totalXUAT) + parseFloat(soluongDEXUAT.item(j).value.replace(/,/g,""));
			}
			
			//alert('Tong xuat: ' + totalXUAT);
			/* if( totalXUAT > parseFloat(soluong.item(i).value.replace(/,/g,"")) )
			{
				soluong2.item(i).value = soluong.item(i).value;
				element.value = '0';
				
				alert('Số lượng xuất ( ' + totalXUAT + ' ) không được phép vượt quá số lượng đặt ( ' + soluong.item(i).value + ' )');
			}
			else
			{ */
				//soluong2.item(i).value = soluong.item(i).value;
				soluong2.item(i).value = totalXUAT;
				soluong.item(i).value = totalXUAT;
			//}
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

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpHoadonchuyenkhoNPPUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="nppDNId"  id="nppDNId" value='<%= hdBean.getnppDangnhap() %>'>
<input type="hidden" name="id" value='<%= hdBean.getId() %>'>
<input type="hidden" name="khachhangkgId" value='<%= hdBean.getKhachhangKGId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý tồn kho > Nghiệp vụ kho > Hóa đơn khác > Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  		<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
  	
        <A href="javascript:goBack();"><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A>
        
        <span id="pr">    			
    			<A href = "../../ErpHoadonchuyenkhoNPPPdfSvl?userId=<%=userId%>&pdf=<%=hdBean.getId() %>&nppId=<%= hdBean.getnppDangnhap()%>">
    			<img src="../images/Printer30.png" alt="Print"  title="Print"  border ="1px" longdesc="Print" style="border-style:outset"></A>
    	</span>
    	
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= hdBean.getMsg() %></textarea>
		         <% hdBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
     <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Xuất hóa đơn </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="130px" class="plainlabel" valign="top">Ngày xuất hóa đơn </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" class="days" style="border-radius:4px; height: 20px;" readonly="readonly"  name="ngayxuat" value="<%= hdBean.getNgayxuatHD() %>"/>
                    </TD>
                    
                    <TD width="150px" class="plainlabel" valign="top">Ngày ghi nhận công nợ </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" class="days" style="border-radius:4px; height: 20px;" readonly="readonly"  name="ngayghinhan" value="<%= hdBean.getNgayghinhanCN() %>"/>
                    </TD>
                </TR>
         
                
                <TR>
                	<TD class="plainlabel">Ký hiệu hóa đơn</TD>
                	<TD class="plainlabel" valign="top">
                    	<input type="text"  name="kyhieuhoadon" style="border-radius:4px; height: 20px;" value="<%= hdBean.getKyhieuhoadon() %>"/>
                    </TD>
                    
                    <TD class="plainlabel">Số hóa đơn</TD>
                	<TD class="plainlabel" valign="top">
                    	<input type="text"   name="sohoadon" style="border-radius:4px; height: 20px;" value="<%= hdBean.getSohoadon().trim() %>"/>
                    </TD>
                </TR>
                
                 <TR>
                   <TD class="plainlabel" style="display: none;" >Loại xuất HD </TD>
                   <TD class="plainlabel" style="display: none;">
                   <input name = "loaiXHD" style="width: 200px" value="<%= hdBean.getLoaiXHD() %>" >
                   </TD>                 
                </TR>
                                
                <TR>
                	<TD class="plainlabel" >Hình thức thanh toán </TD>
                    <TD class="plainlabel" colspan="3">
                    	<select name="hinhthuctt" id="hinhthuctt" style="width: 200px;" class="select2" >
							    <% 
							   String[] mangchuoi=new String[]{"TM/CK","Chuyển khoản","Tiền mặt"};
							   for(int k=0;k < mangchuoi.length;k ++ ){
								   
								   if(hdBean.getHinhthucTT().equals(mangchuoi[k])) {
									   %>
									    	<option value="<%=mangchuoi[k] %>" selected="selected"><%=mangchuoi[k] %> </option>
									   <%
								   }else{
									   %>
								    	<option value="<%=mangchuoi[k] %>" ><%=mangchuoi[k] %> </option>
								  		 <%  
								   }
							   }
							  %>			   
						</select>
                    </TD> 
                  	
                 </TR>
                
                <tr>
                 	<TD class="plainlabel" >Đối tượng </TD>
                    <TD class="plainlabel" colspan="3">
					<select name = "doituong" id= "doituong" style = "width:200px" onchange="loadcontent();" class="select2" >
						<%if(hdBean.getDoituong().equals("0")){ %>
						<option value="0" selected="selected">Nhà phân phối</option>
						<option value="1">Khách hàng</option>
						<%}else if(hdBean.getDoituong().equals("1")){ %>
						<option value="0">Nhà phân phối</option>
						<option value="1" selected="selected">Khách hàng</option>									
						<%} %>
					</select>
                 </tr>
                 
                 <% if(hdBean.getDoituong().equals("1")) { %>
                 <tr>
                 	<TD class="plainlabel" >Khách hàng </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<select name = "khId" id = "khId" class="select2" style="width: 200px"  onchange="loadcontent();"  >
                    		<option value=""></option>
                        	<%
                        		if(khRs != null)
                        		{
                        			try
                        			{
                        			while(khRs.next())
                        			{  
                        			if( khRs.getString("pk_seq").equals(hdBean.getKhId())){ %>
                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("ten") %></option>
                        		 <% } } } catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                   </TD>
                 </tr>
                 <TR> 
                   <TD class="plainlabel" >Tên xuất hóa đơn</TD>
                      <TD class="plainlabel" colspan="3" >                      	
                      	<select name = "tthdId" class="select2" style="width: 200px" onchange="loadcontent();"  >    
                    		<option value="" ></option>  
                    		<% if( invoiceRs != null ) { while( invoiceRs.next()) { %> 
                    			<option value="<%= invoiceRs.getString("pk_seq") %>" <%= hdBean.getTthdId().equals( invoiceRs.getString("pk_seq") ) ? " selected='selected' " : "" %>  ><%= invoiceRs.getString("DONVI") %></option>
                    		<% } invoiceRs.close(); } %>              		
                        	<%-- <% for(int i=0;i < tenxuatHD.length; i++){ 
                        		 	if(tenxuatHD[i].equals(hdBean.getTenxuathd())) {%>
                        				<option value="<%= tenxuatHD[i] %>" selected="selected" ><%= tenxuatHD[i]%></option>
                        	<%		 } else { %>
                        				<option value="<%= tenxuatHD[i] %>" ><%= tenxuatHD[i] %></option>
                        	<% 		} 
                        		}%> --%>
                    	</select>
                    	<a href="" id="thongtinkhxhd" rel="sub" >
							<img  alt="Thông tin xuất hóa đơn " src="../images/vitriluu.png"></a>
						        <DIV id="sub" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 500px; padding: 4px;" >
						           <table width="100%" align="center">	
						           <tr>
						              <td style="width:200px">Họ và tên người mua hàng: </td>
						              <td> 
						               	  <input type="text" id="nguoimuahang" name="nguoimuahang"  style="width: 100%; border-radius:4px; height: 20px;" value="<%= hdBean.getNguoimuahang() %>" > 
						              </td>
						           </tr>
		                           <tr>
		                              <td>Đơn vị: </td>
		                              <td> 
		                            	  <input type="text" id="donvinmh" name="donvinmh"  style="width: 100%; border-radius:4px; height: 20px;" value="<%= hdBean.getDonvimua() %>" > 
		                              </td>
		                           </tr>	
		                           <Tr>
		                               <td>Địa chỉ: </td>
		                               <td> 
		                           		 	<input type="text" id="diachi" name="diachi"  style="width: 100%; border-radius:4px; height: 20px;" value="<%= hdBean.getDiachi() %>" > 
		                               </td>
						           </tr>
		                           <Tr>
		                               <td>Mã số thuế: </td>
		                               <td> 
		                              		 <input type="text" id="masothue" name="masothue"  style="width: 100%; border-radius:4px; height: 20px;" value="<%= hdBean.getMasothue() %>" > 
		                               </td>
			                       </tr>
			                       </table>
				                    <div align="right">
				                    <label style="color: red" ></label>
				                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                   			<a href="javascript:dropdowncontent.hidediv('sub')">Hoàn tất</a>
				                 	</div>
			              		</DIV>  
			              	</TD> 
                </TR>
                 <%} %>
                 
                 <% if(hdBean.getDoituong().equals("0")) { %>
                 <tr>
                 <TD class="plainlabel" >Nhà phân phối </TD>
	                    <TD class="plainlabel" colspan="3" >
	                    	<select name = "nppId" id = "nppId" class="select2" style="width: 200px" >
	                        	<%
	                        		if(nppRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(nppRs.next())
	                        			{  
	                        			if( nppRs.getString("pk_seq").equals(hdBean.getNppId())){ %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" selected="selected" ><%= nppRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" ><%= nppRs.getString("ten") %></option>
	                        		 <% } } nppRs.close();} catch(SQLException ex){}
	                        		}
	                        	%>
	                    	</select>
	                    </TD> 
	                </tr>     
                 <%} %>   
                 
                 <TR>
                	<TD class="plainlabel" >Ghi chú </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text" style="border-radius:4px; height: 20px; width: 500px;"  name="ghichu" value="<%= hdBean.getGhichu() %>"  />
                    </TD>
                </TR>                                
                <TR>
                	 <TD class="plainlabel" colspan="3" >
                    	<fieldset  >
							<legend> Chọn Phiếu xuất/chuyển kho</legend>
							 <div id = "headerKH" style="width:100%; "> 
				    	 		<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
				    	 			<TR class="tbheader">
				                     <TH align="center" width="10%">Số phiếu</TH>
						             <TH align="center" width="10%"> Ngày </TH>		
						             <TH align="center" width="60%"> Lý do xuất/chuyển kho</TH>		
						             <TH align="center" width="10%"> Chọn </TH>
						            </TR>
				    	 		</TABLE>
				    	 	</div>
									    	 	
							 <table id="tableDDH" width="100%">            				
               					<tbody>  
               					    							
             					<%
         							if(ddhRs != null)
         							{
         								while(ddhRs.next()){ %>
           								<tr class= 'tblightrow'>
           									<td align="center" width="10%">
           										 <%=ddhRs.getString("pk_seq") %> 		
           									</td>
           									<td align="center" width="10%">
           										 <%=ddhRs.getString("NgayChuyen") %> 		
           									</td>     
           									<td align="left" width="60%">
           										 <%=ddhRs.getString("lydo") %> 		
           									</td>             									     									
  										<% 
   										 if(hdBean.getDondathangId().contains(ddhRs.getString("pk_seq").trim())){%>
       										<TD align="center" width="10%"><input type="checkbox" checked="checked" name ="ddhid" value ="<%= ddhRs.getString("pk_seq") %>" onchange="loadcontent();" ></TD>
       									<%}else{ %>
       										<TD align="center" width="10%"><input type="checkbox" id="ddhid" name ="ddhid" value ="<%= ddhRs.getString("pk_seq") %>" onchange="loadcontent();" ></TD>
       									<%}%>
   										</tr>
   									<% } ddhRs.close(); } %>
   									</tbody>                     								                  							
                   			</table>
                   		</fieldset>
                    </TD> 
                    
                   <TD class="plainlabel" colspan="2" valign="bottom" >
                		<table class="plainlabel" width="100%"  style="padding-top:0 ; margin-top:0">                 								                   								                   								
               				<tr>
               					<td>Tổng tiền </td>
               					<td>
               						<input type="text" style="text-align:right; border-radius:4px; height: 20px;" id="tongtien" name="tongtien" value="<%= hdBean.getTongtienBVAT() %>" >
               					</td>
               				</tr>
               				<tr>
               					<td>Tiền chiết khấu </td>
               					<td>
               						<input type="text" style="text-align:right; border-radius:4px; height: 20px;" id="tongchietkhau" name="tongchietkhau" value="<%= hdBean.getTongCK() %>">
               					</td>
               				</tr>                 				  
               				<tr style="display: none;" >
               					<td>Phần trăm chiết khấu </td>
               					<td>
               						<input type="text" style="text-align:right; border-radius:4px; height: 20px;" id="ptchietkhau" name="tongchietkhau" value="<%= hdBean.getPtchietkhau() %>">
               					</td>
               				</tr>                   				  
               				<tr>
               					<td>Tiền vat </td>
               					<td>
               						<input type="text" style="text-align:right; border-radius:4px; height: 20px;" id="tienvat" name="tienvat" value="<%= hdBean.getTongVAT() %>">
               					</td>
               				</tr>
               				<tr>
               					<td>Tiền thực thu </td>
               					<td>
               						<input type="text" style="text-align:right; border-radius:4px; height: 20px;" id="tiensauvat" name="tiensauvat" value="<%= hdBean.getTongtienAVAT() %>">
               					</td>
               				</tr>
               				<tr>
               					<td>Tiền tích lũy </td>
               					<td>
               						<input type="text" style="text-align:right; border-radius:4px; height: 20px;" id="tientichluy" name="tientichluy" value="<%= hdBean.getTientichluy() %>">
               					</td>
               				</tr>
               				<tr>
               					<td style="color: red;" >Tiền thực thu (sau tích lũy)</td>
               					<td>
               						<input type="text" style="text-align:right; border-radius:4px; height: 20px;" id="tiensauvat_sauTL" name="tiensauvat_sauTL" value="">
               					</td>
               				</tr>
                  		</table>
                  	</TD>  
                </TR>
                
            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
				<tr class="tbheader">
					<th align="center" width="10%" >Mã sản phẩm</th>
					<th align="center" width="20%" >Tên sản phẩm</th>
					<th align="center" width="10%" >Đơn vị</th>
					<th align="center" width="10%" >Số lượng </th>
					<th align="center" width="10%" >Đơn giá</th>
					<th align="center" width="8%" >Chiết khấu</th>
					<th align="center" width="8%" >Tiền thuế</th>
					<th align="center" width="10%" >Thành tiền</th>
					<th align="center" width="10%" >Scheme</th>
				</tr>
				
				<%
					int count = 0;
					if(spMa != null)
					{
						for(int i = 0; i < spMa.length; i++)
						{ %>
					
						<tr>
							<td>
								<input type="hidden" name="ckId" value="<%= spCkId[i] %>" style="width: 100%; border-radius:4px; height: 20px;"   > 	
								<input type="hidden" name="spId" value="<%= spId[i] %>" style="width: 100%; border-radius:4px; height: 20px;"   > 								
								<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%; border-radius:4px; height: 20px;"  readonly="readonly"  > 
							</td>
							<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%; border-radius:4px; height: 20px;" > </td>
							<td>
								 <input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%; text-align: right; border-radius:4px; height: 20px;" readonly="readonly">	 						
							</td>
							<td>
								<input type="text" name="soluong" value="<%= formatter.format(Double.parseDouble(spSoluong[i])) %>" style="width: 70%; text-align: right; border-radius:4px; height: 20px;" readonly="readonly" >															
									<a href="" id="scheme_<%= spMa[i] + "__" + spSCheme[i] + "__" + spCkId[i] %>" rel="subcontent_<%= spMa[i] + "__" + spSCheme[i] + "__" + spCkId[i] %>">
			           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
			           	 		
		           	 		 		<DIV id="subcontent_<%= spMa[i] + "__" + spSCheme[i] + "__" + spCkId[i] %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                             background-color: white; width: 450px; max-height:300px; overflow:auto; padding: 4px;">
					                    <table width="95%" align="center">
					                    	<tr>
					                    		<td ><b>Tổng xuất</b></td>
					                    		<td colspan="3" > <input type="text" name="soluong2" value="<%= spSoluong[i] %>" style="width: 100px; text-align: right;border-radius:4px; height: 20px;" readonly="readonly" >	</td>
					                    	</tr>
					                        <tr>
					                            <th width="100px" style="font-size: 11px">Số lượng</th>
					                            <th width="100px" style="font-size: 11px">Số lô</th>
					                            <th width="100px" style="font-size: 11px">Ngày nhập kho</th>
					                        </tr>
			                            	<%
			                            		ResultSet spRs = hdBean.getSoloTheoSp(spId[i], spDonvi[i], spSoluong[i], spCkId[i]);  
       			                        		if(spRs != null)
       			                        		{
       			                        			while(spRs.next())
       			                        			{
       			                        				String temID = spMa[i] + "__" + spSCheme[i] + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYHETHAN");
			                            			%>
				                        			<tr>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: right; border-radius:4px; height: 20px;" name="<%= spMa[i] + "_" + spSCheme[i] + "_" + spCkId[i] %>_spSOLUONG" value="<%= formater.format(spRs.getDouble("AVAILABLE")) %>" readonly="readonly">
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: center; border-radius:4px; height: 20px;" name="<%= spMa[i] + "_" + spSCheme[i] + "_" + spCkId[i] %>_spSOLO" value="<%= spRs.getString("SOLO") %>" readonly="readonly">
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: center; border-radius:4px; height: 20px;" name="<%= spMa[i] + "_" + spSCheme[i] + "_" + spCkId[i] %>_spNGAYHETHAN" value="<%= spRs.getString("NGAYHETHAN") %>" readonly="readonly">
				                        				</td>				                        				
				                        			</tr>
				                        			
				                        		 <% } } %>
				                        		 
					                     </table>
					                     <div align="right">
					                     	<label style="color: red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%= spMa[i] + "__" + spSCheme[i] + "__" + spCkId[i] %>')" > Đóng lại </a>
					                     </div>
						            </DIV> 
						            
						            <script type="text/javascript">
						            	dropdowncontent.init('scheme_<%= spMa[i] + "__" + spSCheme[i] + "__" + spCkId[i] %>', "left-top", 300, "click");
						            </script>
								
															
							</td> 
							<td>
								<input type="text" name="spDongia" value="<%= formatter2.format(Double.parseDouble(spDongia[i])) %>" style="width: 100%; text-align: right; border-radius:4px; height: 20px;" readonly="readonly" >
								<input type="hidden" name="spVat" value="<%= formatter.format(Double.parseDouble(spVat[i]))%>" style="width: 100%; text-align: right; border-radius:4px; height: 20px;" readonly="readonly">							
							</td>							
							<td>
								<input type="text" name="spChietkhau" value="<%= formatter.format(Double.parseDouble(spChietkhau[i])) %>" style="width: 100%; text-align: right; border-radius:4px; height: 20px;" readonly="readonly">
							</td>
							<td>
								<input type="text" name="spTienthue" value="<%= formatter.format(Double.parseDouble(spTienThue[i])) %>" style="width: 100%; text-align: right; border-radius:4px; height: 20px;" readonly="readonly"  >
							</td>
							<td>
								
								<input type="text" name="thanhtien" value="<%= formatter2.format(Double.parseDouble(spThanhtien[i])) %>" style="width: 100%; text-align: right; border-radius:4px; height: 20px;" onkeypress="return keypress(event); " readonly="readonly" > 
							</td>
							<td >
								<input type="text" name="scheme" id = "<%= "scheme" + i %>"   value="<%= spSCheme[i] %>" style="width: 100%; text-align: right; border-radius:4px; height: 20px;" readonly="readonly"  >							
							</td>
							
						</tr>	
							
					<% count ++;  
					} } %>
			
			</table> 
				
            </div>
     </fieldset>	
    </div>
</div>

<script type="text/javascript">
	TinhTien();
</script>
<script type="text/javascript">
	dropdowncontent.init('popupCONGNO', "right-top", 300, "click");
	dropdowncontent.init('thongtinkhxhd', "right-top", 500, "click");
</script>
<%
	try
	{
		hdBean.DBclose(); 
	}
	catch(Exception er){ }

	} %>
</form>
</BODY>
</html>