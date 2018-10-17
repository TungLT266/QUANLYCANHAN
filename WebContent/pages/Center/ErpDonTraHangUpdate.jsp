<%@page import="java.util.Enumeration"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="geso.traphaco.center.beans.nhapkho.*"%>
<%@ page import="geso.traphaco.center.beans.nhapkho.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%
	IErpDontrahang lsxBean = (IErpDontrahang)session.getAttribute("lsxBean");
	ResultSet nppRs = lsxBean.getKhRs(); 

	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spGianhap = lsxBean.getSpGianhap();
	String[] spVat = lsxBean.getSpVat();
	String[] sptienvat = lsxBean.getTienvat();

	Hashtable<String, String> sp_chitiet = (Hashtable<String, String>)lsxBean.getSp_Chitiet();
%>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else {
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
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
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135);
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
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
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
		var spMa = document.getElementsByName("spMa");
		var spTen = document.getElementsByName("spTen");  
		var donvi = document.getElementsByName("donvi");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var vat = document.getElementsByName("vat");
		var tienvat = document.getElementsByName("tienvat");
		var thanhtien = document.getElementsByName("thanhtien");
		
		for(var i = 0; i < spMa.length; i++)
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
					donvi.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					
					//VAT
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					vat.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					
					submitform();
				}
			
				
			
			}
			else
			{
				spMa.item(i).value = "";
				spTen.item(i).value = "";
				donvi.item(i).value = "";
				soluong.item(i).value = "";
				 dongia.item(i).value ="";
				 vat.item(i).value = "";
				 tienvat.item(i).value = "";
				 thanhtien.item(i).value = "";
			
			}
		}	
		
		console.log('vao day ');
		for(i = 0; i < spMa.length; i++)
		{
			if( spMa.item(i).value != '' )
			{
				var soluongCT = document.getElementsByName(spMa.item(i).value + "_spSOLUONG");
				var totalLUONG = 0;

				for(var j = 0; j < soluongCT.length; j++)
				{
					if(soluongCT.item(j).value != "")
					{
						totalLUONG = parseFloat(totalLUONG) + parseFloat(  soluongCT.item(j).value.replace(/,/g,"") );
						
						
					}
				}
			
				soluong.item(i).value = totalLUONG;
				
				
				
			}
		}
		
		setTimeout(replaces, 500);
	 }
	

	 function tinhtienvat()
	{
		var spMa = document.getElementsByName("spMa");
		var spTen = document.getElementsByName("spTen");  
		var donvi = document.getElementsByName("donvi");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var vat = document.getElementsByName("vat");
		var thanhtien = document.getElementsByName("thanhtien");
		var tienvat = document.getElementsByName("tienvat");
	
		
		//CAP NHAT CAC COT SOLUONG, TIEN
		var _tongtienBVAT = 0;
		var _tongtienVAT = 0;
		var _tongtienAVAT = 0;
		for(i = 0; i < spMa.length; i++)
		{
			if( spMa.item(i).value != '' )
			{
				var soluongCT = document.getElementsByName(spMa.item(i).value + "_spSOLUONG");
				var totalLUONG = 0;
				var _tienvat=tienvat.item(i).value.replace(/,/g,"");
				
				var tienBVAT=0;
				for(var j = 0; j < soluongCT.length; j++)
				{
					if(soluongCT.item(j).value != "")
					{
						totalLUONG = parseFloat(totalLUONG) + parseFloat(  soluongCT.item(j).value.replace(/,/g,"") );
						
						if( dongia.item(i).value != '' && soluong.item(i).value != '' )
						{
							var _soluong =  parseFloat(  soluongCT.item(j).value.replace(/,/g,"") );
							var _dongia = dongia.item(i).value.replace(/,/g,"");
							 tienBVAT = parseFloat(tienBVAT) +Math.round( parseFloat(_soluong) * parseFloat(_dongia) );
						}
						
					}
				}
		
				soluong.item(i).value = totalLUONG;
				var tienAVAT = parseFloat(tienBVAT) + parseFloat(_tienvat);
				tienvat.item(i).value = DinhDangTien ( _tienvat );
				
				
				_tongtienBVAT = parseFloat(_tongtienBVAT) + parseFloat(tienBVAT);
				_tongtienVAT = parseFloat(_tongtienVAT) + parseFloat(_tienvat);
				_tongtienAVAT = parseFloat(_tongtienAVAT) + parseFloat(tienAVAT);
				
				thanhtien.item(i).value = DinhDangTien ( tienAVAT );
				
				
				
				
			}
		}
		
		document.getElementById('tongtienBVAT').value = DinhDangTien( _tongtienBVAT );
		document.getElementById('tongtienVAT').value = DinhDangTien( _tongtienVAT );
		document.getElementById('tongtienAVAT').value = DinhDangTien( _tongtienAVAT );
		
	 } 
	
	
	function tinhtien()
	{
		var spMa = document.getElementsByName("spMa");
		var spTen = document.getElementsByName("spTen");  
		var donvi = document.getElementsByName("donvi");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var vat = document.getElementsByName("vat");
		var thanhtien = document.getElementsByName("thanhtien");
		var tienvat = document.getElementsByName("tienvat");
	
		
		//CAP NHAT CAC COT SOLUONG, TIEN
		var _tongtienBVAT = 0;
		var _tongtienVAT = 0;
		var _tongtienAVAT = 0;
		for(i = 0; i < spMa.length; i++)
		{
			if( spMa.item(i).value != '' )
			{
				var soluongCT = document.getElementsByName(spMa.item(i).value + "_spSOLUONG");
				var totalLUONG = 0;
				var tienBVAT =0;
				var tienVAT=0;
				var tienAVAT=0;
				for(var j = 0; j < soluongCT.length; j++)
				{
					if(soluongCT.item(j).value != "")
					{
						totalLUONG = parseFloat(totalLUONG) + parseFloat(  soluongCT.item(j).value.replace(/,/g,"") );
						
						if( dongia.item(i).value != '' && soluong.item(i).value != '' )
						{
							var _soluong =  parseFloat(  soluongCT.item(j).value.replace(/,/g,"") );
							var _dongia = dongia.item(i).value.replace(/,/g,"");
							var _vat = vat.item(i).value.replace(/,/g,"");
							var _tienBVAT=Math.round( parseFloat(_soluong) * parseFloat(_dongia) );
							var _tienVAT=Math.round( parseFloat(_tienBVAT) * parseFloat(_vat) / 100.0 );
							var _tienAVAT=parseFloat(_tienBVAT) + parseFloat(_tienVAT);
							
							 tienBVAT = parseFloat(tienBVAT)+ parseFloat(_tienBVAT)  ;
							 tienVAT = parseFloat(tienVAT)+  parseFloat(_tienVAT);
							 tienAVAT = parseFloat(tienAVAT) + parseFloat(_tienAVAT)  ;
							
							
							
							
						}
					}
				}
				soluong.item(i).value = totalLUONG;
				thanhtien.item(i).value = DinhDangTien ( tienAVAT );
				tienvat.item(i).value = DinhDangTien ( tienVAT );
				
				
				_tongtienBVAT = parseFloat(_tongtienBVAT) + parseFloat(tienBVAT);
				_tongtienVAT = parseFloat(_tongtienVAT) + parseFloat(tienVAT);
				_tongtienAVAT = parseFloat(_tongtienAVAT) + parseFloat(tienAVAT);
				
			}
		}
		
		document.getElementById('tongtienBVAT').value = DinhDangTien( _tongtienBVAT );
		document.getElementById('tongtienVAT').value = DinhDangTien( _tongtienVAT );
		document.getElementById('tongtienAVAT').value = DinhDangTien( _tongtienAVAT );
		
	 }
	
	

	
	
	 function saveform()
	 {	
		//CHECK TRUNG MA
		var spMa = document.getElementsByName("spMa");		
		for(var i = 0; i < spMa.length - 1; i++)
		{
			for(var j = (i + 1); j < spMa.length; j++)
			{
				if( spMa.item(i).value == spMa.item(j).value && spMa.item(i).value != '' && spMa.item(j).value != '' )
				{
					alert('Sản phẩm có mã ( ' + spMa.item(j).value + ' ) đã bị trùng trong nhập kho, vui lòng kiểm tra lại');
					return;
				}
			}
		}
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		//CHECK TRUNG MA
		var spMa = document.getElementsByName("spMa");		
		for(var i = 0; i < spMa.length - 1; i++)
		{
			for(var j = (i + 1); j < spMa.length; j++)
			{
				if( spMa.item(i).value == spMa.item(j).value && spMa.item(i).value != '' && spMa.item(j).value != '' )
				{
					alert('Sản phẩm có mã ( ' + spMa.item(j).value + ' ) đã bị trùng trong nhập kho, vui lòng kiểm tra lại');
					return;
				}
			}
		}
			
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
	}	
	 
	function Update_NSX(row, e)
	{
		/* var _ngaySX = document.getElementsByName('dong' + row + '_spNGAYSANXUAT');
		
		if(e.value != '')
		{
			for(var i = 0; i < _ngaySX.length; i++)
			{
				if(_ngaySX.item(i).value == '')
				{
					_ngaySX.item(i).value = e.value;
				}
			}
		} */
	}
	
	function Update_SOLO(row, e)
	{
		/* var _SOLO = document.getElementsByName('dong' + row + '_spSOLO');
		
		if(e.value != '')
		{
			for(var i = 0; i < _SOLO.length; i++)
			{
				if(_SOLO.item(i).value == '')
				{
					_SOLO.item(i).value = e.value;
				}
			}
		} */
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
<form name="hctmhForm" method="post" action="../../ErpDontrahangUpdateSvl">
<input type="hidden" name="userId" value='<%=userId%>'> 
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>

		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">
					Quản lý bán hàng > Chức năng > Đơn trả hàng > Cập nhật
				</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen%>
					&nbsp;
				</div>
			</div>

			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
					<A href="../../ErpDontrahangSvl?userId=<%=userId%>"> 
					<img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px" longdesc="Quay ve" style="border-style: outset"></A>
				<span id="btnSave"> 
					<A href="javascript:saveform()"> 
						<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset"></A>
				</span>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" rows="1" readonly="readonly"
						style="width: 100%"><%=lsxBean.getMsg()%></textarea>
					<%
						lsxBean.setMsg("");
					%>
				</fieldset>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle">Đơn trả hàng </legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD width="120px" class="plainlabel" valign="top">Ngày trả </TD>
								<TD width="230px"  class="plainlabel" valign="top" colspan="3" >
									<input type="text" class="days" readonly="readonly" name="tungay" value="<%= lsxBean.getTungay() %>" />
								</TD>
							</TR>
							<TR>
								<TD width="120px" class="plainlabel" valign="top">Ký hiệu </TD>
								<TD width="230px"  class="plainlabel" valign="top">
									<input type="text"   name="kyhieu" value="<%= lsxBean.getKyhieuHD() %>" />
								</TD>
								<TD width="120px" class="plainlabel" valign="top">Số chứng từ </TD>
								<TD width="230px"  class="plainlabel" valign="top"  >
									<input type="text"  name="sochungtu" value="<%= lsxBean.getSohoadon() %>" />
								</TD>
							</TR>
							<TR>
			                    <TD class="plainlabel" >Đối tượng </TD>
			                    <TD class="plainlabel"  >
			                    	<select name="xuatchoId" class="select2" style="width: 200px" onchange="submitform();" >
			                    		<% if(lsxBean.getXuatcho().equals("0")) { %>
			                    			<option value="0" selected="selected" >Nhà phân phối</option>
			                    		<% } else { %>
			                    			<option value="0" >Nhà phân phối</option>
			                    		<% } %>
			                    		<% if(lsxBean.getXuatcho().equals("1")) { %>
			                    			<option value="1" selected="selected" >Khách hàng ETC</option>
			                    		<% } else { %>
			                    			<!-- <option value="1" >Khách hàng ETC</option> -->
			                    		<% } %>
			                    	</select>
			                    </TD>
			                    <TD class="plainlabel" ><%= ( lsxBean.getXuatcho().equals("0") ? "Nhà phân phối" : "Khách hàng ETC" ) %></TD>
			                    <TD class="plainlabel" >
			                    	<select name = "khId" class="select2" style="width: 400px"  >
			                    		<option value=""> </option>
			                        	<%
			                        		if(nppRs != null)
			                        		{
			                        			try
			                        			{
			                        			while(nppRs.next())
			                        			{  
			                        			if( lsxBean.getKhId().contains( nppRs.getString("pk_seq") )){ %>
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
								<TD class="plainlabel" >Tổng tiền BVAT</TD>
								<TD class="plainlabel" >
									<input type="text" id="tongtienBVAT" name="tongtienBVAT" value="<%=lsxBean.getTongtienbvat() %>" style="text-align: right;" readonly="readonly" /> VNĐ
								</TD>
								<TD class="plainlabel" >Tổng tiền VAT</TD>
								<TD class="plainlabel" >
									<input type="text" id="tongtienVAT" name="tongtienVAT" value="<%=lsxBean.getTongtienvat() %>" style="text-align: right;" readonly="readonly" /> VNĐ
								</TD>
							</TR>
							<TR>
								<TD class="plainlabel" >Tổng tiền AVAT</TD>
								<TD class="plainlabel" colspan="3" >
									<input type="text" id="tongtienAVAT" name="tongtienAVAT" value="<%=lsxBean.getTongtienavat() %>" style="text-align: right;" readonly="readonly" /> VNĐ
								</TD>
							</TR>
							<TR>
								<TD class="plainlabel" valign="top">Ghi chú</TD>
								<TD colspan="3" class="plainlabel" valign="top">
									<input type="text" name="ghichu" value="<%=lsxBean.getGhichu()%>" style="width: 100%" />
								</TD>
							</TR>

						</TABLE>
						<hr />

						<table cellpadding="0px" cellspacing="1px" width="100%">
							<tr class="tbheader">
								<th align="center" width="15%">Mã sản phẩm</th>
								<th align="center" width="25%">Tên sản phẩm</th>
								<th align="center" width="10%">Đơn vị</th>
								<th align="center" width="10%">Số lượng</th>
								<th align="center" width="10%">Đơn giá</th>
								<th align="center" width="10%">VAT</th>
								<th align="center" width="10%">Tiền Vat</th>
								<th align="center" width="10%">Thành tiền</th>
							</tr>

							<%
								int count = 0;
								if(spMa != null)
								{
									for(int i = 0; i < spMa.length; i++)
									{
							%>

							<tr>
								<td>
									<input type="text" name="spMa" value="<%=spMa[i]%>" style="width: 100%" onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off">
								</td>
								<td>
									<input type="text" name="spTen" value="<%=spTen[i]%>" style="width: 100%" readonly="readonly"></td>
								<td>
									<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%; " readonly="readonly">
								</td>
								<td >
									<input type="text" name="soluong" value="" style="width: 70%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly="readonly">
									
									<a href="" id="sanpham_<%=count%>" rel="subcontent_<%=count%>"> &nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>

									<DIV id="subcontent_<%=count%>" style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 450px; max-height: 500px; overflow: auto; padding: 4px;">
										<table width="300px" align="center">
											<tr>
												<th width="100px" style="font-size: 11px">Số lượng</th>
												<th width="100px" style="font-size: 11px">Số lô</th>
												<th width="100px" style="font-size: 11px">Ngày hết hạn</th>
											<!-- 	<th width="100px" style="font-size: 11px">tiền vat</th> -->
												<!-- <th width="100px" style="font-size: 11px">Tồn kho</th> -->
											</tr>
										</table>

										<div style="max-height: 500px; overflow: auto;">
											<table width="95%" align="center">
												<%
													int kk = 0;
													System.out.println("spma "+spMa[i] +"--- tien vat "+sptienvat[i]);
													double totaltienvat=0;
													Enumeration<String> keys = sp_chitiet.keys();
						                        	if(keys != null)
						                        	{
						                        		String selected = "";
						                        		while( keys.hasMoreElements()  )
						                        		{
						                        			String key = keys.nextElement();
						                        			
						                        			if( key.startsWith( spMa[i] ) ) {
						                        			String[] arr = key.split("__");
						                        			//String key = spMa[i] + "__" + rsLO.getString("solo") + "__" + rsLO.getString("ngayhethan");
						                        			String _soluong = sp_chitiet.get(key);
						                        			totaltienvat+=Double.parseDouble(arr[3]);
												%>

												<tr>
													<td width="100px" >
														<input type="text" style="width: 100%; text-align: right;" name="<%= spMa[i] %>_spSOLUONG" value="<%= _soluong %>" onkeyup="Dinhdang(this);" onchange="tinhtien();">
													</td>
													<td width="100px" >
														<input type="text" style="width: 100%; text-align: center;" name="<%= spMa[i] %>_spSOLO" value="<%= arr[1] %>" readonly="readonly" >
													</td>
													<td width="100px" >
														<input type="text" style="width: 100%; text-align: center;" name="<%= spMa[i] %>_spNGAYHETHAN" value="<%= arr[2] %>" readonly="readonly" >
													</td>
													
													
												</tr>

												<%  } } } while ( kk < 5 ) { %>
													<tr>
														<td width="100px" >
															<input type="text" style="width: 100%; text-align: right;" name="<%= spMa[i] %>_spSOLUONG" value="" onkeyup="Dinhdang(this);" onchange="tinhtien();">
														</td>
														<td width="100px" >
															<input type="text" style="width: 100%; text-align: center;" name="<%= spMa[i] %>_spSOLO" value=""  >
														</td>
														<td width="100px" >
															<input type="text" style="width: 100%; text-align: center;" name="<%= spMa[i] %>_spNGAYHETHAN" value="" class="days" >
														</td>
														
													</tr>
												<% kk++; } %>

											</table>
											<div align="right">
						                     	<label style="color: red" ></label>
						                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%=count%>')" > Đóng lại </a>
						                     </div>
										</div>
										
									</DIV> 
									<script type="text/javascript">
					            		dropdowncontent.init('sanpham_<%=count%>', "left-top", 300, "click");
					            	</script>
								</td>
								<td >
									<input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 100%; text-align: right; " onkeypress="return keypress(event);" onchange="tinhtien();"  >
								</td>
								<td >
									<input type="text" name="vat" value="<%= spVat[i] %>" style="width: 100%; text-align: right; " onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" onchange="tinhtien();" >
								</td>
								<td >
									<input type="text" name="tienvat" value="<%= totaltienvat %>" style="width: 100%; text-align: right; " onkeypress="return keypress(event);" onchange="tinhtienvat();" >
								</td>
								
								<td >
									<input type="text" name="thanhtien" value="" style="width: 100%; text-align: right; " onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" >
								</td>
							</tr>

							<% count ++; } } %>
							
							<%
								for(int i = count; i < 30; i++) {
							%>

							<tr>
								<td>
									<input type="text" name="spMa" value="" style="width: 100%" onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off">
								</td>
								<td>
									<input type="text" name="spTen" value="" style="width: 100%" readonly="readonly"></td>
								<td> 
									<input type="text" name="donvi" value="" style="width: 100%; " readonly="readonly"> 
								</td>

								<td >
									<input type="text" name="soluong" value="" style="width: 70%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" readonly="readonly">
									<a href="javascript:void(0);" > &nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
								</td>

								<td >
									<input type="text" name="dongia" value="" style="width: 100%; " onchange="tinhtien();" > 
					            </td>
					            
					            <td >
									<input type="text" name="vat" value="" style="width: 100%; " onchange="tinhtien();" > 
					            </td>
					            
					             <td >
									<input type="text" name="tienvat" value="" style="width: 100%; "  onchange="tinhtienvat();"  > 
					            </td>
					            
					            <td >
									<input type="text" name="thanhtien" value="" style="width: 100%;" readonly="readonly"> 
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
	tinhtienvat();
</script>
<%
	try {
			lsxBean.DBclose();

		} catch (Exception er) {
		}

	}
%>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</html>