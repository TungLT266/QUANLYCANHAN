<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.center.beans.dondathang.*" %>
<%@ page  import = "geso.traphaco.center.beans.dondathang.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% IErpDondathang lsxBean = (IErpDondathang)session.getAttribute("lsxBean"); %>
<% ResultSet dvkdRs = lsxBean.getDvkdRs(); %>
<% ResultSet kbhRs = lsxBean.getKbhRs(); %>
<% ResultSet nppRs = lsxBean.getNppRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet dvtRs = lsxBean.getDvtRs(); %>
<% ResultSet congnoRs = lsxBean.getCongnoRs(); %>
<% ResultSet gsbhRs = lsxBean.getGsbhRs(); %>
<% ResultSet ddkdRs = lsxBean.getDdkdRs(); %>

<% 
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spGianhap = lsxBean.getSpGianhap();
	String[] spChietkhau = lsxBean.getSpChietkhau();
	String[] spVat = lsxBean.getSpVat();
	String[] spSCheme = lsxBean.getSpScheme();
	String[] spTrongluong = lsxBean.getSpTrongluong();
	String[] spThetich = lsxBean.getSpThetich();
	String[] spQuyDoi = lsxBean.getSpQuyDoi();
	
	String[] dhCk_diengiai = lsxBean.getDhck_diengiai();
	String[] dhCk_giatri = lsxBean.getDhck_giatri();
	String[] dhCk_loai = lsxBean.getDhck_loai();
	
	NumberFormat formater = new DecimalFormat("##,###,###.##");
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
</style>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
		var dongia = document.getElementsByName("dongia");
		var thanhtien = document.getElementsByName("thanhtien");
		
		var trongluong = document.getElementsByName("spTrongLuong");
		var thetich = document.getElementsByName("spTheTich");
		
		var spQuyDoi = document.getElementsByName("spQuyDoi");
		
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
					
					trongluong.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					thetich.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					spQuyDoi.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					dongia.item(i).value = DinhDangTien(sp.substring(0, parseInt(sp.indexOf("]")))); 
				}
			}
			else
			{
				spMa.item(i).value = "";
				spTen.item(i).value = "";
				donvi.item(i).value = "";
				soluong.item(i).value = "";
				dongia.item(i).value = "";
				thanhtien.item(i).value = "";	
				trongluong.item(i).value = "";	
				thetich.item(i).value = "";	
				spQuyDoi.item(i).value = "";
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
		var thanhtien = document.getElementsByName("thanhtien");
		
		var trongluong = document.getElementsByName("spTrongLuong");
		var thetich = document.getElementsByName("spTheTich");
		var spQuyDoi = document.getElementsByName("spQuyDoi");
		var spDonVi = document.getElementsByName("donvi");
		
		var tongtien = 0;
		var tongtienCK = 0;
		
		var totalTL = 0;
		var totalTT = 0;
		
		var totalTHG = 0;
		var totalLe = 0 ;
		
		for(var i = 0; i < spMa.length; i++)
		{
			if(soluong.item(i).value != '')
				thanhtien.item(i).value = "0";
			
			if(trongluong.item(i).value != '' && soluong.item(i).value != '' )
				totalTL += parseFloat(trongluong.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,""));
			
			if(thetich.item(i).value != '' && soluong.item(i).value != '' )
				totalTT += parseFloat(thetich.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,""));
			
			var so_luong = soluong.item(i).value.replace(/,/g,"");
			var sp_DonVi= spDonVi.item(i).value;
			var sp_QuyDoi=spQuyDoi.item(i).value.replace(/,/g,"");
			
			if(soluong.item(i).value != '')
			{
				if(sp_DonVi =='Thùng' )
				{
					so_luong = parseFloat(so_luong)*parseFloat(sp_QuyDoi);
					console.log("[Thùng]"+totalTHG +"[totalLe]" + totalLe );
				}
				totalTHG += Math.round( (so_luong / sp_QuyDoi) - 0.5 );
				totalLe += Math.round( (so_luong % sp_QuyDoi) - 0.5 );
			}
		}
		
		document.getElementById("txtTongTL").value = DinhDangTien(totalTL);
		document.getElementById("txtTongTT").value = DinhDangTien(totalTT);
		
		document.getElementById("txtTongThung").value = DinhDangTien(totalTHG);
		document.getElementById("txtTongLe").value = DinhDangTien(totalLe);
		
		tongtienCK = 0;
		
		var vat = document.getElementById("txtVAT").value;
		if(vat == '')
			vat = '0';
		
		var tongtienSAUCK = parseFloat(tongtien) - parseFloat(tongtienCK);
		
		document.getElementById("txtTongCK").value = DinhDangTien(tongtienCK);
		document.getElementById("txtBVAT").value = DinhDangTien(tongtien);
		document.getElementById("txtTongSauCK").value = DinhDangTien(tongtienSAUCK);
		
		var tongtienSAUVAT = parseFloat(tongtienSAUCK) + ( parseFloat(vat) * parseFloat(tongtienSAUCK) / 100 );
		document.getElementById("txtSauVAT").value = DinhDangTien(tongtienSAUVAT);
		
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
	
	function readExcel()
	{
		document.forms['hctmhForm'].action.value='readExcel';
		document.forms['hctmhForm'].setAttribute('enctype', "multipart/form-data", 0);
	    document.forms['hctmhForm'].submit();
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

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpDondathangUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="loaidonhang" value='<%= lsxBean.getLoaidonhang() %>'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý bán hàng> Đơn hàng khuyến mại > Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpDondathangSvl?userId=<%= userId %>&loaidonhang=<%= lsxBean.getLoaidonhang() %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
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
    	<legend class="legendtitle">Đơn hàng khuyến mại</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="130px" class="plainlabel" valign="top">Ngày đơn hàng </TD>
                    <TD width="250px"  class="plainlabel" valign="top" >
                    	<input type="text" class="days" readonly="readonly"  name="ngaychuyen" id="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/>
                    </TD>
                    
                    <TD width="120px" class="plainlabel" valign="top">Ngày đề nghị giao </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" class="days" readonly="readonly"  name="ngaydenghi" id="ngaydenghi"  value="<%= lsxBean.getNgaydenghi() %>"/>
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" valign="top">Đơn vị kinh doanh </TD>
                    <TD class="plainlabel" valign="top"   colspan = "4">
                    	<select name = "dvkdId" id="dvkdId"  onchange="submitform();" class="select2" style="width: 200px" >
                    		<option value=""> </option>
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
                    <TD class="plainlabel" valign="top" style="display: none;">Kênh bán hàng </TD>
                    <TD class="plainlabel" valign="top"  style="display: none;" >
                    	<select name = "kbhId" id="kbhId" class="select2" style="width: 200px" onchange="submitform();" >
                    		<option value=""  > </option>
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
                	<TD class="plainlabel" valign="top">Đối tượng </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<select name = "nppId" id="nppId"  class="select2" style="width: 200px" onchange="submitform();" >
                    		<option value=""> </option>
                        	<%
                        		if(nppRs != null)
                        		{
                        			try
                        			{
                        			while(nppRs.next())
                        			{  
                        			if( nppRs.getString("pk_seq").equals(lsxBean.getNppId())){ %>
                        				<option value="<%= nppRs.getString("pk_seq") %>" selected="selected" ><%= nppRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nppRs.getString("pk_seq") %>" ><%= nppRs.getString("ten") %></option>
                        		 <% } } nppRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   
                    <TD class="plainlabel" valign="top">Kho </TD>
                    <TD class="plainlabel" valign="top"  >
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
                
                <TR style="display: none;" >
                    <TD class="plainlabel" valign="top">Tổng thanh tiền </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" readonly="readonly"  id="txtBVAT" value="" style="text-align: right;"  /></TD>
                    	
                    <TD class="plainlabel" valign="top">Chiết khấu </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  value="<%= lsxBean.getChietkhau() %>" id="txtPTChietKhau" style="text-align: right;" name="ptChietkhau" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" /> %
                    </TD>
                </TR>
                
                <TR style="display: none;" >
                    <TD class="plainlabel" valign="top">Tổng tiền chiết khấu </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" readonly="readonly"  value="" id="txtTongCK" style="text-align: right;" /></TD>
                    	
                    <TD class="plainlabel" valign="top">Tổng tiền sau CK </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  value="" id="txtTongSauCK" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR style="display: none;" >
                    <TD class="plainlabel" valign="top">VAT </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" value="<%= lsxBean.getVat() %>" id="txtVAT" style="text-align: right;" name="ptVat" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" /> %
                    </TD>
                    	
                    <TD class="plainlabel" valign="top">Tổng tiền sau VAT </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  value="" id="txtSauVAT" style="text-align: right;" />
                    </TD>
                </TR>
                
                 <TR style="display: none;" >
                    <TD class="plainlabel" valign="top">Tổng thùng </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" value="" id="txtTongThung" style="text-align: right;" readonly="readonly" /> 
                    </TD>
                    	
                    <TD class="plainlabel" valign="top" >Tổng lẻ </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" value="" id="txtTongLe" style="text-align: right;" readonly="readonly"  /> 
                    </TD>
                </TR>
                
                <TR style="display: none;" >
                    <TD class="plainlabel" valign="top">Tổng trọng lượng </TD>
                    <TD class="plainlabel" valign="top" >
                    	<input type="text" value="" id="txtTongTL" style="text-align: right;" readonly="readonly" /> gram
                    </TD>
                    	
                    <TD class="plainlabel" valign="top" >Tổng thể tích </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" value="" id="txtTongTT" style="text-align: right;" readonly="readonly"  /> cm3
                    </TD>
                </TR>
                
                <TR style="display: none;" >
			  	  	<TD class="plainlabel">File Import</TD>
			  	  	<TD class="plainlabel" colspan = 3>
			  	  			<INPUT type="file" name="filename" id="filename"  size="40" value=''>
			  	  	  &nbsp;	<p style="cursor: pointer;" onclick="readExcel();" class="button2">
							<img alt="" src="../images/button.png" style="top: -4px;"> Đọc file</p>
			  	  	</TD>
					  	
			  	</TR>	
                 <TR >	
                   
                    <TD class="plainlabel" valign="top" colspan = 4>
                    	Hàng mẫu &nbsp;
                    	<input type="radio" value="1" name="ishm" <%= lsxBean.getIshm().equals("1") ? " checked='checked' " : "" %> /> &nbsp;&nbsp;&nbsp;
                    	Tài trợ, hỗ trợ &nbsp;
                    	<input type="radio" value="2" name="ishm" <%= lsxBean.getIshm().equals("2") ? " checked='checked' " : "" %> /> &nbsp;&nbsp;&nbsp;
                    	Phúc lợi cho nhân viên &nbsp;
                    	<input type="radio" value="3" name="ishm" <%= lsxBean.getIshm().equals("3") ? " checked='checked' " : "" %> /> &nbsp;&nbsp;&nbsp;
                    	Tiêu dùng nội bộ &nbsp;
                    	<input type="radio" value="4" name="ishm" <%= lsxBean.getIshm().equals("4") ? " checked='checked' " : "" %> /> &nbsp;&nbsp;&nbsp;
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
					<tr class="tbheader">
						<th align="center" width="3%" >STT</th>
						<th align="center" width="15%" >Mã sản phẩm</th>
						<th align="center" width="26%" >Tên sản phẩm</th>
						<th align="center" width="10%" >Đơn vị</th>
						<th align="center" width="10%" >Số lượng</th>
						<th align="center" width="1%" style="display: none;" >Đơn giá</th>
						<th align="center" width="1%" style="display: none;" >Chiết khấu</th>
						<th align="center" width="1%" style="display: none;" >VAT</th>
						<th align="center" width="1%" style="display: none;" >Thành tiền</th>
						<th align="center" width="35%" style="display: none;" >Scheme</th>
					</tr>
					
					<%
						int count = 0;
						if(spMa != null)
						{
							for(int i = 0; i < spMa.length; i++)
							{  %>
						
							<tr>
								<td style="text-align: center;" > <%= i + 1 %> </td>
								<td>
										<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
										<input type="hidden" name="spTrongLuong" value="<%= spTrongluong[i] %>" > 
										<input type="hidden" name="spTheTich" value="<%= spThetich[i] %>" > 
										<input type="hidden" name="spQuyDoi" value="<%= spQuyDoi[i] %>"  >
									</td>
									<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
									<td>
										<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%" readonly="readonly">
									</td>
									
									<td> 
									<input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width: 50%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" onchange="submitform();" > 
									
									<% if( spSoluong[i].trim().length() > 0 ) { %>
									<a href="" id="scheme_<%= spMa[i] + "__" + spSCheme[i]  %>" rel="subcontent_<%= spMa[i] + "__" + spSCheme[i] %>">
			           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
			           	 		
		           	 		 		<DIV id="subcontent_<%= spMa[i] + "__" + spSCheme[i] %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                             background-color: white; width: 700px; max-height:300px; overflow:auto; padding: 4px;">
					                    <table width="98%" align="center">
					                    	<tr>
					                    		<td ><b>Tổng xuất</b></td>
					                    		<td colspan="3" > <input type="text" name="soluong2" value="<%= spSoluong[i] %>" style="width: 100px; text-align: right;" readonly="readonly" >	</td>
					                    	</tr>
					                        <tr>
					                             <th width="80px" style="font-size: 11px">Số lượng</th>
					                            <th width="90px" style="font-size: 11px">Số lô</th>
					                            <th width="100px" style="font-size: 11px">Ngày hết hạn</th>
					                            <th width="100px" style="font-size: 11px">Ngày nhập kho</th>
												<th width="150px" style="font-size: 11px">Vị trí</th>
												<th width="90px" style="font-size: 11px">Mã thùng</th>
												<th width="80px" style="font-size: 11px">Mã mẻ</th>
												<th width="80px" style="font-size: 11px">Mã phiếu</th>
												<th width="80px" style="font-size: 11px">Marquett</th>
												<th width="80px" style="font-size: 11px">Nhà sản xuất</th>
					                            <th width="80px" style="font-size: 11px">Tồn kho</th>
					                        </tr>
			                            	<%
			                            		ResultSet spRs = lsxBean.getSoloTheoSp(spMa[i], spDonvi[i], spSoluong[i]);
				                        		if(spRs != null)
				                        		{
				                        			while(spRs.next())
				                        			{
				                        				String tudeXUAT = "";
				                        				if(spRs.getString("tuDEXUAT").trim().length() > 0)
				                        					tudeXUAT = formater.format(spRs.getDouble("tuDEXUAT"));
				                        				
				                        				String temID = spMa[i] + "__" + spSCheme[i];
				                        				String key = spMa[i] + "__" + spSCheme[i] + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYHETHAN") + "__" + spRs.getString("VITRI") + "__" + spRs.getString("MATHUNG") + "__" + spRs.getString("MAME") + "__" + spRs.getString("MAPHIEU") + "__" + spRs.getString("MARQ") + "__" + spRs.getString("NSX")+ "__" + spRs.getString("NGAYNHAPKHO");
				                        				String key2 = spMa[i] + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYHETHAN") + "__" + spRs.getString("VITRI") + "__" + spRs.getString("MATHUNG") + "__" + spRs.getString("MAME") + "__" + spRs.getString("MAPHIEU") + "__" + spRs.getString("MARQ") + "__" + spRs.getString("NSX")+ "__" + spRs.getString("NGAYNHAPKHO");
				                        				
				                        				if( sanpham_soluongDAXUAT.get(key2) == null && tudeXUAT.trim().length() > 0 )
				                        					sanpham_soluongDAXUAT.put(key2, tudeXUAT);
				                        				else
				                        				{
				                        					double tempSL = 0;
				                        					if( tudeXUAT.trim().length() > 0 )
				                        						tempSL = Double.parseDouble( tudeXUAT.replaceAll(",", "" ) );
				                        					
				                        					System.out.println("::: KEY 2.1 : " + key + " -- VALUE: " +  sanpham_soluong.get(key) );
				                        					double soluongXUAT = tempSL; 
				                        					if( sanpham_soluongDAXUAT.get(key2) != null && sanpham_soluongDAXUAT.get(key2).trim().length() > 0 )
				                        						soluongXUAT +=	Double.parseDouble( sanpham_soluongDAXUAT.get(key2) );
				                        					
				                        					sanpham_soluongDAXUAT.put(key2, Double.toString(soluongXUAT));
				                        				}
				                        			%>
				                        			
				                        			<tr>
				                        				<td>
				                        				<% if(sanpham_soluong.get( key ) != null ) { %>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= formater.format(Double.parseDouble( sanpham_soluong.get( key ))) %>" onkeyup="CheckSoLuong_DeXuat(this);" >
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
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spNGAYNHAPKHO" value="<%= spRs.getString("NGAYNHAPKHO") %>" readonly="readonly">
				                        				</td>
				                        				<td >
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spVITRI" value="<%= spRs.getString("VITRI") %>" readonly="readonly">
				                        				</td>
				                        				<td >
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spMATHUNG" value="<%= spRs.getString("MATHUNG") %>" readonly="readonly">
				                        				</td>
				                        				<td >
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spMAME" value="<%= spRs.getString("MAME") %>" readonly="readonly">
				                        				</td>
				                        				<td >
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spMAPHIEU" value="<%= spRs.getString("MAPHIEU") %>" readonly="readonly">
				                        				</td>
				                        				<td >
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spMARQ" value="<%= spRs.getString("MARQ") %>" readonly="readonly">
				                        				</td>
				                        				<td >
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spNSX" value="<%= spRs.getString("NSX") %>" readonly="readonly">
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spTONKHO" value="<%= formater.format(spRs.getDouble("AVAILABLE")) %>" readonly="readonly">
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
						         	<a href="javascript:void(0);" >&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
						         <% } %>
								</td>
									<td style="display: none;" > <input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 100%; text-align: right;" <%if(!lsxBean.getIsdhk().equals("1")){ %>readonly="readonly"<%} %>  onkeyup="Dinhdang(this);" > </td>
									<td style="display: none;" > <input type="text" name="chietkhau" value="<%= spChietkhau[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
									<td style="display: none;" > <input type="text" name="spvat" value="<%= spVat[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly="readonly" > </td>
									<td style="display: none;" > <input type="text" name="thanhtien" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
									<td style="display: none;"> <input type="text" name="scheme" value="<%= spSCheme[i] %>" style="width: 100%; "  > </td>
							</tr>	
								
						<% count ++; } } %>
					
					<% for(int i = count; i < 0; i++) { %>
						
						<tr>
							<td style="text-align: center;" > <%= i + 1 %> </td>
							<td>
								<input type="text" name="spMa" value="" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
								<input type="hidden" name="spTrongLuong" value="" > 
								<input type="hidden" name="spTheTich" value="" > 
								<input type="hidden" name="spQuyDoi" value=""  >
							</td>
							<td> <input type="text" name="spTen" value="" style="width: 100%" readonly="readonly"> </td>
							<td>
								<input type="text" name="donvi" value="" style="width: 100%" readonly="readonly">
							 </td>
							
							<td> 
								<input type="text" name="soluong" value="" style="width: 50%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" onchange="submitform();" > 
								<a href="javascript:void(0);" >&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
							</td>
							<td style="display: none;" > <input type="text" name="dongia" value="0" style="width: 100%; text-align: right;"  >  </td>
							<td style="display: none;" > <input type="text" name="chietkhau" value="0" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > </td>
							<td style="display: none;" > <input type="text" name="spvat" value="0" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly="readonly" > </td>
							<td style="display: none;" > <input type="text" name="thanhtien" value="0" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							<td style="display: none;"> <input type="text" name="scheme" value="" style="width: 100%; "  > </td>
						</tr>	
						
					<% } %>	
						
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
		dvtRs.close();
		lsxBean.DBclose(); 
	}
	catch(Exception er){ }

	} %>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</html>