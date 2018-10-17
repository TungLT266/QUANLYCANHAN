<%@page import="java.util.Enumeration"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.distributor.beans.dontrahang.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.dontrahang.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>

<% 
	IDontrahang lsxBean = (IDontrahang)session.getAttribute("lsxBean");  
	ResultSet khoxuatRs = lsxBean.getKhoXuatRs();
	ResultSet nppRs = lsxBean.getKhRs();
	ResultSet dvtRs = lsxBean.getDvtRs();
	ResultSet kbhRs = lsxBean.getKbhRs();
	Hashtable<String, String> sanpham_soluong = lsxBean.getSanpham_Soluong();
	
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spGianhap = lsxBean.getSpGianhap();
	String[] spTonkho = lsxBean.getSpTonkho();
	String[] spSoLo = lsxBean.getSpSolo();
	String[] spNgayHetHan = lsxBean.getSpNgayHetHan();
	String[] spVat = lsxBean.getSpVat(); 
	ResultSet tichluyRs = (ResultSet)lsxBean.getTichluyRs();

	NumberFormat formater = new DecimalFormat("##,###,###");
%>

<% Hashtable<String, Integer> sp_sl = (Hashtable<String, Integer>)lsxBean.getSp_Soluong(); %>
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
<script type="text/javascript" src="../scripts/AjaxDonTraHang.js"></script>

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
		var dongia = document.getElementsByName("dongia");
		var spVat = document.getElementsByName("spVat");
		var tonkho = document.getElementsByName("tonkho");
		var soluong = document.getElementsByName("soluong");
		var thanhtien = document.getElementsByName("thanhtien");
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
					
					dongia.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					spVat.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					tonkho.item(i).value = DinhDangTien ( sp.substring(0, parseInt(sp.indexOf("]"))) );
										
					dongia.item(i).value = DinhDangTien(dongia.item(i).value) ;
					thanhtien.item(i).value = '0'; 
				}
			}
			else
			{
				spMa.item(i).value = "";
				spTen.item(i).value = "";
				donvi.item(i).value = "";
				tonkho.item(i).value = "";
				soluong.item(i).value = "";
				dongia.item(i).value = "";
				spVat.item(i).value = "";
				thanhtien.item(i).value = "";	
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
		
		var thueVAT = document.getElementsByName("spVat");
		var thanhtien = document.getElementsByName("thanhtien");
	
		
		var vat =0;
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
				var _ck =0;
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
					
				
				var tt = parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")) - parseFloat(_ck);
				tt = Math.round( parseFloat(tt) * ( 1 +  parseFloat(_thueVAT) / 100 ) );
				thanhtien.item(i).value = DinhDangTien(tt);
				
				tongtien += ( parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")) );
				tongtienCK += parseFloat( _ck );
				tongtienVAT += Math.round(( parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,"")) - parseFloat(_ck) ) * ( parseFloat(_thueVAT) / 100 ));
			}		
		}
	
		
		var tongDhCK = 0;
		var tongtien_sau_hoahong = 0;
		
		
		tongtienCK += parseFloat(tongDhCK);
		
		var tongtienSAUCK = parseFloat(tongtien) - parseFloat(tongtienCK);
		
		document.getElementById("txtTongCK").value = DinhDangTien(tongtienCK);
		document.getElementById("txtBVAT").value = DinhDangTien(tongtien);
		document.getElementById("txtTongSauCK").value = DinhDangTien(tongtienSAUCK);
		document.getElementById("txtVAT").value = DinhDangTien(tongtienVAT);
		var tongtienSAUVAT = parseFloat(tongtienSAUCK) + ( parseFloat(tongtienVAT) );
		document.getElementById("txtSauVAT").value = DinhDangTien(tongtienSAUVAT);
		
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
				if( soluongDEXUAT.item(j).value != '' )
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

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../DontrahangUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="nppId" value='<%= lsxBean.getNppId() %>'>
<input type="hidden" name="kenhId" value='<%= lsxBean.getKbhId() %>'>
<input type="hidden" name="khoxuat" value='<%= lsxBean.getKhoXuatId() %>'>
<input type="hidden" name="loaidonhang" value='<%= lsxBean.getLoaidonhang() %>'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	<% if(lsxBean.getLoaidonhang().equals("0")) { %>
				Quản lý bán hàng > Bán hàng cho NPP > Đơn trả hàng > Hiển thị
			<% } else if(lsxBean.getLoaidonhang().equals("0")) { %>
				Quản lý bán hàng > Bán hàng thầu > Đơn trả hàng  > Hiển thị
			<% } else { %>
				Quản lý bán hàng > Bán hàng không thầu > Đơn trả hàng  > Hiển thị
			<% } %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../DontrahangSvl?userId=<%= userId %>&loaidonhang=<%= lsxBean.getLoaidonhang() %>" >
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
    	<legend class="legendtitle">Đơn trả hàng </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
               <TR>
                    <TD class="plainlabel" >Ngày xuất </TD>
                    <TD  class="plainlabel" >
                    	<input type="text" class="days" readonly="readonly"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/></TD>
                    	
                    <TD class="plainlabel" >Số chứng từ  <FONT class="erroralert"> *</FONT>   </TD>
                    <TD colspan="3" class="plainlabel" >
                    	<input type="text"   name="sochungtu" value="<%= lsxBean.getSoChungTu() %>"/></TD>
                    	
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Kho xuất hàng</TD>
                    <TD class="plainlabel"   width="230px" >
                    	<select name = "khoxuatId" style="width: 200px" class="select2"  >
                        	<%
                        		if(khoxuatRs != null)
                        		{
                        			try
                        			{
                        			while(khoxuatRs.next())
                        			{  
                        			if( khoxuatRs.getString("pk_seq").equals(lsxBean.getKhoXuatId())){ %>
                        				<option value="<%= khoxuatRs.getString("pk_seq") %>" selected="selected" ><%= khoxuatRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khoxuatRs.getString("pk_seq") %>" ><%= khoxuatRs.getString("ten") %></option>
                        		 <% } } khoxuatRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   
                    <TD class="plainlabel" width="120px" >Kênh bán hàng</TD>
                    <TD class="plainlabel"  >
                    	<select name = "kbhId" style="width: 200px" class="select2"  onchange="submitform()">
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
                
                <tr>
                	<TD class="plainlabel" >
                		<% if(lsxBean.getLoaidonhang().equals("0")) { %>
							Nhà phân phối
						<% } else { %>
							Khách hàng
						<% } %>
                	</TD>
                    <TD class="plainlabel" >
                    	<select name = "khId" style="width: 200px" class="select2" onchange="submitform()">
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
                	
                	<TD class="plainlabel" >Loại trừ tích lũy </TD>
                     <TD class="plainlabel">
                         <select name="tichluyId" style="width: 200px;" >
                         <option value=""> </option>
                         <% while(tichluyRs.next()) 
                         {
                           if( lsxBean.getTichluyId().contains( tichluyRs.getString("pk_seq") ) ){ %>
                             <option value="<%= tichluyRs.getString("pk_seq") %>" selected><%=tichluyRs.getString("diengiai") %></option>
                             
                         <%}else { %>
                         	<option value="<%= tichluyRs.getString("pk_seq") %>"><%= tichluyRs.getString("diengiai") %></option>
                         <%}} tichluyRs.close(); %>
                         	
                         </select>
                     </TD>
                </TR>
                <tr>
                	<TD class="plainlabel" >Ghi chú </TD>
                    <TD colspan="3" class="plainlabel" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 400px" />
                    </TD>
                </tr>
                
                 <TR>
                    <TD class="plainlabel" >PT chiết khấu </TD>
                    <TD class="plainlabel" >
                    	<input type="text"  value="" id="txtPTChietKhau" style="text-align: right;" name="ptChietkhau" readonly="readonly" />
                    </TD>
                    <TD class="plainlabel" >Tổng thành tiền </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  id="txtBVAT"  value="" style="text-align: right;"  />
                    </TD>
                </TR>
                
                <TR style="display:none" >
                    <TD class="plainlabel" >Tổng thành tiền </TD>
                    <TD class="plainlabel"  colspan="3" >
                    	<input type="text" readonly="readonly"  id="txtBVAT" value="" style="text-align: right;"  />
                    	
                    
                    	<input type="hidden"  value="" id="txtPTChietKhau" style="text-align: right;" name="ptChietkhau" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" />
                    </TD>
                </TR>
                
                <TR style="display:none" >
                    <TD class="plainlabel" >Tổng tiền chiết khấu </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" readonly="readonly"  value="" id="txtTongCK" style="text-align: right; width: 200px; " />
                    </TD>
                    	
                    <TD class="plainlabel" >Tổng tiền sau CK </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  value="" id="txtTongSauCK" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR  >
                    <TD class="plainlabel" >Tổng tiền VAT </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" value="" id="txtVAT" style="text-align: right;" name="ptVat" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);"  readonly='readonly'  />
                    </TD>
                    	
                    <TD class="plainlabel"  style="color: red;" >Tổng tiền sau VAT </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  value="" id="txtSauVAT" style="text-align: right;" />
                    </TD>
                </TR>
                
                
                
            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
				<tr class="tbheader">
					<th align="center" width="3%" >STT</th>
					<th align="center" width="15%" >Mã sản phẩm</th>
					<th align="center" width="25%" >Tên sản phẩm</th>
					<th align="center" width="10%" >Đơn vị</th>
					<th align="center" width="1%" style="display: none;" >Tồn kho</th>
					<th align="center" width="8%" >Số lượng</th>
					<th align="center" width="10%" >Đơn giá</th>
					<th align="center" width="10%" >Thành tiền</th>
					<th align="center" width="10%" >Số Lô</th>
				</tr>
				
				 <%
					boolean choCHON_DVT = false;
					int count = 0;
					if(spMa != null )
					{
						for(int i = 0; i < spMa.length; i++)
						{%>
					
						<tr>
							<td style="text-align: center;" > <%= i + 1 %> </td>
							<td>
								<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  >
								<input type="hidden" name="spVat" value="<%= spVat[i] %>"  > 
							</td>
							<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
							<td>
							<%if(!choCHON_DVT) { %>
									<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%" readonly="readonly">
								<% } else { %>
									<select name="donvi" style="width: 100%" onchange="CapNhatGia(this, <%= count %>);"   >
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
								<% } %>
								</td>
							
							<td style="display: none;" > <input type="text" name="tonkho" value="<%= spTonkho[i] %>" style="width: 100%; text-align: right;" readonly="readonly"  > </td>
							<td  > <input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" onchange="submitform();" > </td>							
							<td > <input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							<td > <input type="text" name="thanhtien" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
							
							
							<td align="center" > 
								<% if(spSoluong[i].trim().length() > 0) { %>
									<a href="" id="scheme_<%= spMa[i] %>" rel="subcontent_<%= spMa[i] %>">
			           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
			           	 		
		           	 		 		<DIV id="subcontent_<%= spMa[i] %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
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
					                            <!-- <th width="100px" style="font-size: 11px">Tồn kho</th> -->
					                        </tr>
			                            	<%-- <%
			                            		ResultSet spRs = lsxBean.getSoloTheoSp(spMa[i], spSoluong[i]);
			                            		String temID = spMa[i];
			                            		
				                        		if(spRs != null)
				                        		{
				                        			while(spRs.next())
				                        			{
				                        				String tudeXUAT = "";
				                        				if(spRs.getString("tuDEXUAT").trim().length() > 0)
				                        					tudeXUAT = formater.format(spRs.getDouble("tuDEXUAT")); 
				                        				
				                        				String key = spMa[i] + "__" + spRs.getString("SOLO")+"__" + spRs.getString("NGAYHETHAN");
				                        			%>
				                        			
				                        			<tr>
				                        				<td>
				                        				<% if( sanpham_soluong.get( key ) != null ) { %>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= formater.format(Double.parseDouble( sanpham_soluong.get( spMa[i] + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYHETHAN")  ))) %>" onkeyup="CheckSoLuong_DeXuat(this);" >
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
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spTONKHO" value="<%= formater.format(spRs.getDouble("AVAILABLE")) %>" readonly="readonly">
				                        				</td>
				                        			</tr>
				                        			
				                        		 <% } } %> --%>
				                        		 
				                        		 <%
				                        			String temID = spMa[i];
				                        		 	System.out.println(":::: SO DONG CO DATA LAY DUOC: " + sanpham_soluong.size() );
				             						if( sanpham_soluong.size() > 0 )
				             						{
				             							Enumeration<String> keys = sanpham_soluong.keys();
				             							while( keys.hasMoreElements() )
				             							{
				             								String key = keys.nextElement();
				             								
				             								if( key.startsWith( temID ) ) {
				             								
				             								String[] data = key.split("__"); 
				             								
				             								%>
				             								
				             								<tr>
						                        				<td>
						                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= sanpham_soluong.get(key) %>" onkeyup="CheckSoLuong_DeXuat(this);" >
						                        				</td>
						                        				<td>
						                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spSOLO" value="<%= data[1] %>" >
						                        				</td>
						                        				<td>
						                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spNGAYHETHAN" value="<%= data[2] %>" class='days' >
						                        				</td>
						                        			</tr>
				             								
				             					 <% } } } %>
				                        		 
				                        		 <% for( int j = 0; j <= 5; j++ ) { %>
				                        		 	
				                        		 	<tr>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="" onkeyup="CheckSoLuong_DeXuat(this);" >
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spSOLO" value="" >
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spNGAYHETHAN" value="" class='days' >
				                        				</td>
				                        			</tr>
				                        		 	
				                        		 <% } %>
				                        		 
					                     </table>
					                     <div align="right">
					                     	<label style="color: red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%= spMa[i] %>')" > Đóng lại </a>
					                     </div>
						            </DIV> 
						            
						            <script type="text/javascript">
						            	dropdowncontent.init('scheme_<%= spMa[i]  %>', "left-top", 300, "click");
						            </script>
						         <% } else { %>
						         	<a href="javascript:void(0);" >&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
						         <% } %>
							</td>
							
						</tr>	
							
					<% count ++; } } %>
				
				<% for(int i = count; i < 0; i++) { %>
					
					<tr>
						<td style="text-align: center;" > <%= i + 1 %> </td>
						<td>
							<input type="text" name="spMa" value="" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
							<input type="hidden" name="spVat" value=""  >
						</td>
						<td> <input type="text" name="spTen" value="" style="width: 100%" readonly="readonly"> </td>
						
						<td>
							<% if(!choCHON_DVT) { %>
								<input type="text" name="donvi" value="" style="width: 100%" readonly="readonly">
							<% } else { %>
								<select name="donvi" style="width: 100%" onchange="CapNhatGia(this, <%= i %>);"   >
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
							<% } %>
							 </td>
						
						<td style="display: none;" > <input type="text" name="tonkho" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
						<td > <input type="text" name="soluong" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" onchange="submitform();"  > </td>
						
						<td > <input type="text" name="dongia" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
						<td > <input type="text" name="thanhtien" value="" style="width: 100%; text-align: right;" readonly="readonly" > </td>
						
						<td align="center" >
							
							<a href="javascript:void(0);" >
		           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
		           	 				
							<%-- <a href="" id="scheme_<%= i %>" rel="subcontent_<%= i %>">&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
			           	 		
           	 		 		<DIV id="subcontent_<%= i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
			                             background-color: white; width: 450px; max-height:300px; overflow:auto; padding: 4px;">
			                    <table width="95%" align="center">
			                    	<tr>
			                    		<td ><b>Tổng xuất</b></td>
			                    		<td colspan="3" > <input type="text" name="soluong2" value="" style="width: 100px; text-align: right;" readonly="readonly" >	</td>
			                    	</tr>
			                        <tr>
			                            <th width="100px" style="font-size: 11px">Số lượng</th>
			                            <th width="100px" style="font-size: 11px">Số lô</th>
			                            <th width="100px" style="font-size: 11px">Ngày hết hạn</th>
			                        </tr>
	           
                        			<tr>
                        				<td>
                        					<input type="text" style="width: 100%;text-align: right;" name="_spSOLUONG" value="" onkeyup="CheckSoLuong_DeXuat(this);" >
                        				</td>
                        				<td>
                        					<input type="text" style="width: 100%;text-align: center;" name="_spSOLO" value="" readonly="readonly">
                        				</td>
                        				<td>
                        					<input type="text" style="width: 100%;text-align: center;" name="_spNGAYHETHAN" value="" readonly="readonly">
                        				</td>
                        			</tr>
		                        			
			                     </table>
			                     <div align="right">
			                     	<label style="color: red" ></label>
			                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%= i %>')" > Đóng lại </a>
			                     </div>
				            </DIV> 
				            
				            <script type="text/javascript">
				            	dropdowncontent.init('scheme_<%= i  %>', "left-top", 300, "click");
				            </script> --%>
							
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
</script>

<%

try
{
	lsxBean.DBclose(); 
	lsxBean=null;
}
catch(Exception er){er.printStackTrace(); }

lsxBean = (IDontrahang)session.getAttribute("lsxBean");  
if(khoxuatRs!=null)  khoxuatRs.close();
if(nppRs!=null)  nppRs.close();
if(dvtRs!=null)  dvtRs.close();
if(kbhRs!=null)  kbhRs.close();
sanpham_soluong=null;
spMa=null;
spTen=null;
spDonvi=null;
spSoluong=null;
spGianhap=null;
spTonkho=null;
spNgayHetHan=null;

	} %>
</form>
</BODY>
</html>