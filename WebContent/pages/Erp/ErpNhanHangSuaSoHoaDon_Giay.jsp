<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.nhanhang.*" %>
<%@ page  import = "geso.traphaco.erp.beans.nhanhang.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpNhanhang_Giay nhBean = (IErpNhanhang_Giay)session.getAttribute("nhBean"); %>
<% ResultSet dvthList = nhBean.getDvthList(); %>
<% ResultSet poList = nhBean.getDmhList(); %>
<% ResultSet ndnList = nhBean.getNdnList(); %>
<% ResultSet ldnList = nhBean.getLdnList(); %>
<% ResultSet nccList = nhBean.getNccList(); %>
<% ResultSet hdNccList = nhBean.getHdNccList(); %>
<% ResultSet soTokhaiList = nhBean.getSotokhaiList(); %>
<% List<ISanpham> spList = nhBean.getSpList();  %>
<% NumberFormat formater = new DecimalFormat("#,###,###"); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

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

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
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

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("#nccId").select2(); });
     
 </script>

<script language="javascript" type="text/javascript">
	
	function TangDate(ngay, songay)
	{   
		var ArrNgay=ngay.split("-");
		var d = new Date(ArrNgay[0],ArrNgay[1]-1,ArrNgay[2]);
		d.setDate(d.getDate() + parseInt(songay));
		
		month = parseInt(d.getMonth()) + 1;
		if(month < 10)
			month = '0' + month;
		
		date = parseInt(d.getDate());
		if(date < 10)
			date = '0' + date;
					
		//alert('Ngay sau khi tang: ' + d.getFullYear() + '-' + month + '-' + date);
		return d.getFullYear() + '-' + month + '-' + date;
	}

	var t;
	function replaces()
	{
		var sanpham = document.getElementsByName("idhangmua");
		var ngaynhandukien = document.getElementsByName("ngaynhandukien");
		var soluongnhan = document.getElementsByName("soluongnhan");
		var hansudung = document.getElementsByName("hansudung");
		
		var soluongdat = document.getElementsByName("soluongdat");
		var conlai = document.getElementsByName("conlai");
		var dungsai = document.getElementsByName("dungsai");
		var soluongMaxNhan = document.getElementsByName("soluongMaxNhan");
		
		for(var i = 0; i < sanpham.length; i++)
		{
			var slgDat = soluongdat.item(i).value.replace(/,/g,"");
			//var slgMax = parseInt(slgDat) +  Math.round( parseInt(slgDat) * Math.abs( parseInt(dungsai.item(i).value) ) / 100 );
			
			var tongluong = 0;
			if(sanpham.item(i).value != "")
			{
				var id = sanpham.item(i).value + '.' + ngaynhandukien.item(i).value;
				var solo = document.getElementsByName(id + '.solo');
				var soluong = document.getElementsByName(id + '.soluong');
				var ngaysanxuat = document.getElementsByName(id  + '.ngaysanxuat');
				var ngayhethan = document.getElementsByName(id + '.ngayhethan');
				//var error = document.getElementById(id + '.error');
				
				for( var j = 0; j < soluong.length; j++)
				{
					if(solo.item(j).value != "" && soluong.item(j).value != "")
					{
						//alert('So luong dong ' + j + ' la: ' + soluong.item(j).value);
						
						//Check solo
						var flag = false;
						for(var k = 0; k < j; k++)
						{
							if(solo.item(k).value == solo.item(j).value)
							{
								flag = true;
								break;
							}
						}
						
						if(flag)
						{
							var msg = 'Bạn đã nhập lô ' + solo.item(j).value + ' rồi, Vui lòng nhập số lô khác';
							alert(msg);
							
							solo.item(j).value = "";
							soluong.item(j).value = "";
							ngaysanxuat.item(j).value = "";
							ngayhethan.item(j).value = "";
						}
						
						var slg = soluong.item(j).value.replace(/,/g,"");
						tongluong = parseFloat(tongluong) + parseFloat(slg);
						
						if(ngaysanxuat.item(j).value != "" && hansudung.item(i).value != "" && ngayhethan.item(j).value=="")
							{
								ngayhethan.item(j).value = TangDate(ngaysanxuat.item(j).value, hansudung.item(i).value);
							}
						//alert('Tong luong la: ' + tongluong);
						
						if(parseFloat(tongluong) > parseFloat(soluongMaxNhan.item(i).value))
						{
							tongluong = parseFloat(tongluong) - parseFloat(slg);
							
							var msg = 'Lô ' + solo.item(j).value + ' với số lượng ' + soluong.item(j).value + ' đã vượt quá tổng số lượng đặt và dung sai (' + dungsai.item(i).value + '). Bạn chỉ có thể nhận tối đa là ( ' + soluongMaxNhan.item(i).value + ' ). Vui lòng nhập lại số lượng';
							alert(msg);
							
							//solo.item(j).value = "";
							soluong.item(j).value = "";
							//ngaysanxuat.item(j).value = "";
							//ngayhethan.item(j).value = "";
						}
					}
					else
					{
						if( solo.item(j).value == "" )
						{
							soluong.item(j).value = "";
							ngaysanxuat.item(j).value = "";
							ngayhethan.item(j).value = "";
						}
					}
				}
				
				soluongnhan.item(i).value = tongluong;
				
				if(soluongnhan.item(i).value != "")
					conlai.item(i).value = parseFloat(slgDat) - parseFloat(soluongnhan.item(i).value);
			}
		}
		
		//t = setTimeout(replaces, 100);
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
	
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
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
		 /* var sohoadon = document.getElementById("sohoadon");
		 if(sohoadon.value == "")
		 {
			alert("Vui lòng nhập số hóa đơn");
			return;
		 } */
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['nhForm'].action.value='suaSoHd';
	     document.forms['nhForm'].submit();
	 }
	 
	 function checkTTSanPham()
	 {
		 var solo = document.getElementsByName("solo");
		 var soluongnhan = document.getElementsByName("soluongnhan");
		 var ngaysanxuat = document.getElementsByName("ngaysanxuat");
		
		 for(var h = 0; h < solo.length; h++)
		 {
			if(solo.item(h).value != "")
			{
				if(soluongnhan.item(h).value == "")
				{
					return 'Ban phai nhap so luong nhan';
				}
				if(ngaysanxuat.item(h).value == "")
				{
					return 'Ban phai nhap ngay san xuat';
				}
			}
		 }
		 return '';
	 }
	 
	 function submitform()
	 { 
		 document.forms['nhForm'].action.value='submit';
	     document.forms["nhForm"].submit();
	 }
	 function nextform()
	 { 
		 document.forms['nhForm'].action.value='next';
	     document.forms["nhForm"].submit();
	 }
	 function changePO()
	 { 
		 document.forms['nhForm'].action.value='changePO';
	     document.forms["nhForm"].submit();
	 }
	 function changeLHH()
	 { 
		 document.forms['nhForm'].action.value='changeLHH';
	     document.forms["nhForm"].submit();
	 }
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nhForm" method="post" action="../../ErpNhanhangUpdate_GiaySvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= nhBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý mua hàng > Nhận hàng > Sửa số HĐ
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpNhanhang_GiaySvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
        <A id = "btnPrint" href="../../ErpPhieunhanhangPdfSvl?userId=<%=userId %>&nhId=<%=nhBean.getId() %>" >
        	<img src="../images/Printer30.png" alt="In"  title="In" border="1" longdesc="In" style="border-style:outset"></A>

    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= nhBean.getMsg() %></textarea>
		         <% nhBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Nhận hàng</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="150px" class="plainlabel" valign="top">Ngày nhận hàng</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  disabled="disabled" class="days" id="ngaynhanhang" name="ngaynhanhang" 
                    			value="<%= nhBean.getNgaynhanhang() %>"  maxlength="10" readonly /></TD>
                    <TD class="plainlabel" valign="top">Ngày chốt</TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  disabled="disabled"  class="days" id="ngaychot" name="ngaychot" 
                    			value="<%= nhBean.getNgaychot() %>"  maxlength="10" readonly /></TD>
				</TR>
                <TR >
                	<TD class="plainlabel" >Loại hàng hóa</TD>
					<TD class="plainlabel" colspan="3" >
						<select name="loaihh" id="loaihh" disabled="disabled" onChange="submitform();" >
						<% if(nhBean.getLoaihanghoa().equals("1")) { %>
							<option value="1" selected="selected">Tài sản cố định</option>
							<option value="3">Công cụ dụng cụ</option>
							<option value="0">Sản phẩm nhập kho</option>
							<option value="2">Chi phí dịch vụ</option>
						<% } else { if(nhBean.getLoaihanghoa().equals("2")){ %>
							<option value="2"  selected="selected">Chi phí dịch vụ</option>
							<option value="1">Tài sản cố định</option>
							<option value="3">Công cụ dụng cụ</option>
							<option value="0">Sản phẩm nhập kho</option>
						<% } else { if(nhBean.getLoaihanghoa().equals("3")){ %> 
							<option value="3" selected="selected">Công cụ dụng cụ</option>
							<option value="0">Sản phẩm nhập kho</option>
							<option value="1">Tài sản cố định</option>
							<option value="2">Chi phí dịch vụ</option>
						<%} else { %> 
							<option value="0" selected="selected">Sản phẩm nhập kho</option>
							<option value="1">Tài sản cố định</option>
							<option value="3">Công cụ dụng cụ</option>
							<option value="2">Chi phí dịch vụ</option>
						<% } } } %>
						</select>
					</TD>
                	<TD class="plainlabel" style="display: none;" >Nội dung nhận</TD>
                    <TD class="plainlabel" style="display: none;" >
                        <select name="ndnId" id="ndnId"  disabled="disabled" onChange="changePO()">
                        <option value=""></option>
                        	<%
                        		if(ndnList != null)
                        		{
                        			try
                        			{
                        			while(ndnList.next())
                        			{  
                        			if( ndnList.getString("pk_seq").equals(nhBean.getNdnId())){ %>
                        				<option value="<%= ndnList.getString("pk_seq") %>" selected="selected" ><%= ndnList.getString("noidung") %></option>
                        			<%}else { %>
                        				<option value="<%= ndnList.getString("pk_seq") %>" ><%= ndnList.getString("noidung") %></option>
                        		 <% } } ndnList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>
                <TR >
                    <TD class="plainlabel">Đơn vị thực hiện</TD>
                    <TD class="plainlabel">
                        <select name="dvthId" id="dvthId"  disabled="disabled" onChange="submitform();">
                        	<option value=""> </option>
                        	<%
                        		if(dvthList != null)
                        		{
                        			try
                        			{
                        			while(dvthList.next())
                        			{  
                        			if( dvthList.getString("pk_seq").equals(nhBean.getDvthId())){ %>
                        				<option value="<%= dvthList.getString("pk_seq") %>" selected="selected" ><%= dvthList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= dvthList.getString("pk_seq") %>" ><%= dvthList.getString("ten") %></option>
                        		 <% } } dvthList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                	 
                	 <TD class="plainlabel" valign="middle" > <%= nhBean.getNdnId().equals("100001") ? "Khách hàng" : "Nhà cung cấp" %></TD>   
                      <TD class="plainlabel" valign="middle" colspan="3" >
                      	<select name="nccId" id="nccId"   disabled="disabled" onchange="changeLHH();" style="width: 300px;" >
						<option value=""></option>
						<% if (nccList != null)
						{
							while (nccList.next())
							{
								if (nccList.getString("pk_seq").equals(nhBean.getNccId()))
								{
								%>
									<option value="<%=nccList.getString("pk_seq")%>" selected="selected"><%=nccList.getString("ten")%></option>
								<% } else { %>
									<option value="<%=nccList.getString("pk_seq")%>"><%=nccList.getString("ten")%></option>
						<% } } nccList.close(); } %>
						</select>
                     </TD>      
                	 
                </TR>
                <TR>
                    <TD class="plainlabel">Lý do nhận</TD>
                    <TD class="plainlabel" >
                        <select name="ldnId"  disabled="disabled" id="ldnId" >
                        <option value=""></option>
                        	<%
                        		if(ldnList != null)
                        		{
                        			try
                        			{
                        			while(ldnList.next())
                        			{  
                        			if( ldnList.getString("pk_seq").equals(nhBean.getLdnId())){ %>
                        				<option value="<%= ldnList.getString("pk_seq") %>" selected="selected" ><%= ldnList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= ldnList.getString("pk_seq") %>" ><%= ldnList.getString("ten") %></option>
                        		 <% } } ldnList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                    <TD class="plainlabel">Số PO</TD>
                    <TD class="plainlabel">
                        <select name="soPO" id="soPO" disabled="disabled" onChange="changePO();">
                        	<option value=""> </option>
                        	<%
                        		if(poList != null)
                        		{
                        			try
                        			{
                        			while(poList.next())
                        			{  
                        			if( poList.getString("poId").equals(nhBean.getDonmuahangId())){ %>
                        				<option value="<%= poList.getString("poId") %>" selected="selected" ><%= poList.getString("poTen")%></option>
                        			<%}else { %>
                        				<option value="<%= poList.getString("poId") %>" ><%= poList.getString("poTen") %></option>
                        		 <% } } poList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>

				<TR >
                    <TD class="plainlabel">Số hóa đơn (Invoice)</TD>
                    <TD class="plainlabel">
                    	<% if(nhBean.getIsPONK().equals("1")) { %>
                        <select name="hdNccId"  disabled="disabled" id="hdNccId" >
                        	<option value=""> </option>
                        	<%
                        		if(hdNccList != null)
                        		{
                        			try
                        			{
                        			while(hdNccList.next())
                        			{  
                        			if( hdNccList.getString("pk_seq").equals(nhBean.getHdNccId())){ %>
                        				<option value="<%= hdNccList.getString("pk_seq") %>" selected="selected" ><%= hdNccList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= hdNccList.getString("pk_seq") %>" ><%= hdNccList.getString("ten") %></option>
                        		 <% } } hdNccList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                        <% } else { %>
                        	<input type="text" id="sohoadon" name="sohoadon" value="<%= nhBean.getSohoadon() %>" />
                        <% } %>
                     </TD>
                     
                     <TD  class="plainlabel" valign="top" >Diễn giải</TD>
                     <TD class="plainlabel" valign="top">
                    	<input type="text" id="diengiai" name="diengiai" value="<%= nhBean.getDiengiai() %>" /></TD>
                      
                </TR>
				
				<% if(nhBean.getIsPONK().equals("1")) { %>
				<TR >
                    <TD class="plainlabel">Số tờ khai HQ</TD>
                    <TD class="plainlabel" colspan="3" >
                    	
                        <select name="sotokhaiId"  disabled="disabled" id="sotokhaiId" >
                        	<option value=""> </option>
                        	<%
                        		if(soTokhaiList != null)
                        		{
                        			try
                        			{
                        			while(soTokhaiList.next())
                        			{  
                        			if( soTokhaiList.getString("pk_seq").equals(nhBean.getSotokhaiId())){ %>
                        				<option value="<%= soTokhaiList.getString("pk_seq") %>" selected="selected" ><%= soTokhaiList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= soTokhaiList.getString("pk_seq") %>" ><%= soTokhaiList.getString("ten") %></option>
                        		 <% } } soTokhaiList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
	
                     </TD>                    
                </TR>
                <% } %>
			
            </TABLE>
            <hr> 
            </div>
           
        <div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader"> 
                	<TH align="center" width="5%">STT</TH>
                	
                	<% if(nhBean.getLoaihanghoa().equals("1")) { %>
	                	<TH align="center" width="15%">Tài sản</TH>
	                	<TH align="center" width="20%">Diễn giải</TH>
                	<% } else { if(nhBean.getLoaihanghoa().equals("2")) { %> 
                		<TH align="center" width="15%">Chi phí</TH>
	                	<TH align="center" width="20%">Diễn giải</TH>
                	<% } else { if(nhBean.getLoaihanghoa().equals("0")) { %>
                		<TH align="center" width="10%">Mã sản phẩm</TH>
	                	<TH align="center" width="30%">Tên sản phẩm</TH>
	                	<TH align="center" width="10%">Kho nhận</TH>
                	<% } else { %> 
                		<TH align="center" width="15%">CCDC</TH>
	                	<TH align="center" width="20%">Diễn giải</TH>
                	<% } } } %>
                	
                	<TH align="center" width="8%">Ngày nhận</TH>
                	<TH align="center" width="8%">ĐVT</TH>
               	 	<TH align="center" width="8%">SL đặt</TH>
               	 	<TH align="center" width="8%">SL đã nhận</TH>
               	 	<TH align="center" width="8%">SL nhận</TH>
               	 	<TH align="center" width="8%" style="display: none">Còn lại</TH>
                </TR>
                <%
                	for(int i = 0; i < spList.size(); i++)
                	{
                		ISanpham sp = spList.get(i);
	               		%>
	               		<tr>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align:center; " value="<%= i + 1 %>" name= "stt" readonly="readonly">
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getMa() %>" name= "mahangmua" readonly="readonly" >
           	 					<input type="hidden" value="<%= sp.getId() %>" name= "idhangmua">
           	 					<input type="hidden" value="<%= sp.getHansudung() %>" name= "hansudung">
           	 					<input type="hidden" value="<%= sp.getDungsai() %>" name= "dungsai">
           	 					<input type="hidden" value="<%= sp.getSoluongMaxNhan() %>" name= "soluongMaxNhan">
           	 					<input type="hidden" value="<%= sp.getDongia() %>" name= "dongiaMua">
           	 					<input type="hidden" value="<%= sp.getTiente() %>" name= "tiente">
           	 					<input type="hidden" value="<%= sp.getTigiaquydoi() %>" name= "tygiaquydoi">
           	 					<input type="hidden" value="<%= sp.getDongiaViet() %>" name= "dongiaViet">
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getDiengiai() %>" name= "tenhangmua" readonly="readonly" >
           	 				</td>
           	 				
           	 				<% if(nhBean.getLoaihanghoa().equals("0")) { %>
           	 					<td align="right">
           	 					<% if(nhBean.getNdnId().equals("100001")) { %>
           	 						<input type="hidden" style="width: 100%; text-align: left;" value="" name= "khonhanTen"  >
           	 						<select name="khonhanId" style="width: 100%;">
           	 							<%
           	 								if(sp.getKhoNhanRs() != null)
           	 								{
           	 									while(sp.getKhoNhanRs().next())
           	 									{
           	 										if( sp.getKhonhanId().equals(sp.getKhoNhanRs().getString("khoId")) ) {
           	 										%>
           	 											<option value="<%= sp.getKhoNhanRs().getString("khoId") %>" selected="selected" ><%= sp.getKhoNhanRs().getString("khoTen") %></option>
           	 										<% } else { %> 
           	 											<option value="<%= sp.getKhoNhanRs().getString("khoId") %>" ><%= sp.getKhoNhanRs().getString("khoTen") %></option>
           	 										<% } 
           	 									}
           	 									sp.getKhoNhanRs().close();
           	 								}
           	 							%>
           	 						</select>			
           	 						
           	 					<% } else { %>
	           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getKhonhanTen() %>" name= "khonhanTen" readonly="readonly" >
	           	 					<input type="hidden" style="width: 100%; text-align: left;" value="<%= sp.getKhonhanId() %>" name= "khonhanId"  >
	           	 				<% } %>
	           	 				</td>
           	 				<% } %>
           	 				
           	 				<td align="right">
           	 					<input disabled="disabled"  type="text" style="width: 100%; text-align: center; " value="<%= sp.getNgaynhandukien() %>" name= "ngaynhandukien" readonly="readonly" >
           	 				</td>
           	 				<td align="left">
           	 					<input    type="text" style="width: 100%; text-align: left;" value="<%= sp.getDvdl() %>" name= "dvdl" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input disabled="disabled"  type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongdat() %>" name= "soluongdat" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input disabled="disabled" type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongDaNhan() %>" name= "soluongdanhan" readonly="readonly" >
           	 				</td>
           	 				
           	 				<td align="left">
           	 				<% if(nhBean.getLoaihanghoa().equals("0")){ %>
           	 					<input type="text"  disabled="disabled" style="width: 75%; text-align: right;" value="<%= sp.getSoluongnhan() %>" name= "soluongnhan" readonly="readonly" >
           	
	           	 				<a href="" id="<%= sp.getId() + "." + sp.getNgaynhandukien() %>" rel="subcontent<%= i %>">
	           	 							<img alt="Số lô - Số lượng nhận" src="../images/vitriluu.png"></a>
	           	 				<DIV id="subcontent<%= i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 450px; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="100px">Số lô</th>
				                            <th width="100px">Số lượng</th>
				                            <th width="100px">Ngày sản xuất</th>
				                            <th width="100px">Ngày hết hạn</th>
				                        </tr>
				                        <%
				                        	List<ISpDetail> spDetailList = sp.getSpDetail();
				                        	int stt = 0;
				                        	if(spDetailList.size() > 0)
				                        	{
				                        		for(int sd = 0; sd < spDetailList.size(); sd++)
				                        		{
				                        			ISpDetail spDetail = spDetailList.get(sd);
				                        		%>
				                        			<tr>
							                            <td>
							                            	<input type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + ".solo" %>" value="<%= spDetail.getSolo() %>" onchange="replaces()"/>
							                            </td>
							                            <td>
							                            	<input type="text" size="100px"  name="<%= sp.getId() + "." + sp.getNgaynhandukien() + ".soluong" %>" value="<%= spDetail.getSoluong() %>" style="text-align: right;" onchange="replaces()"/></td>
							                            <td>
							                            	<input type="text" size="100px"  class="days" 
							                            		name="<%= sp.getId() + "." + sp.getNgaynhandukien() + ".ngaysanxuat" %>" value="<%= spDetail.getNgaySx() %>" readonly="readonly" onchange="replaces()" /></td>
							                            <td>
							                            	<input type="text" size="100px"  class="days" 
							                            		name="<%= sp.getId() + "." + sp.getNgaynhandukien() + ".ngayhethan" %>" value="<%= spDetail.getNgayHethan() %>" readonly="readonly"   />
							                            </td>
							                        </tr>
				                        		<% stt++; }
				                        	}
				                        %>
				                        <%
				                        	for(int k = 0; k < 6 - spDetailList.size(); k++)
				                        	{
				                        		%>
				                        		<tr>
						                            <td>
						                            	<input  disabled="disabled" type="text" size="100px" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + ".solo" %>" value="" onchange="replaces()" /></td>
						                            <td>
						                            	<input disabled="disabled"  type="text" size="100px"  name="<%= sp.getId() + "." + sp.getNgaynhandukien() + ".soluong" %>" value="" style="text-align: right;" onchange="replaces()"/></td>
						                            <td>
						                            	<input type="text" size="100px"  class="days" name="<%= sp.getId() + "." + sp.getNgaynhandukien() + ".ngaysanxuat" %>" 
						                            	  value="" readonly="readonly" onChange="replaces()" />
						                            </td>
						                            <td>
						                            	<input type="text" size="100px"  name="<%= sp.getId() + "." + sp.getNgaynhandukien() + ".ngayhethan" %>"  class="days" 
						                            	 value="" readonly="readonly"  />
						                            </td>
						                        </tr>
				                        	<% stt++; }
				                        %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%= i %>')">Hoàn tất</a>
				                     </div>
				                </DIV>
				                <% } else {  %>
				                	<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongnhan() %>" name= "soluongnhan"  >
				                <% } %>
				                
           	 				</td>
           	 				
           	 				<td align="right" style="display: none">
           	 					<%
           	 						String conlai = sp.getSoluongdat().replaceAll(",", "") ;
           	 						if(sp.getSoluongnhan().length() > 0)
           	 						{
           	 							conlai = formater.format(Double.parseDouble(conlai) - Double.parseDouble(sp.getSoluongnhan().replaceAll(",", "") ));
           	 						}
           	 					%>
           	 					<input type="text" style="width: 100%; text-align: right;" 
           	 						value="<%= conlai %>" name= "conlai" readonly="readonly" >
           	 				</td>
           	 			</tr>
           	 	<%} %>
            </TABLE> 
        	</div>      
     
     </fieldset>	
    </div>
</div>
</form>
<script type="text/javascript">
	//replaces();
	<% 
		for(int i = 0; i < spList.size(); i++)
		{
			ISanpham sp = spList.get(i);
	%>
		dropdowncontent.init('<%= sp.getId() + "." + sp.getNgaynhandukien() %>', "left-top", 300, "click");
	<%} %>
	
</script>


<%
try{

	if(spList!=null){
		spList.clear();
	}
	nhBean.close();
	
}catch(Exception er){
	
}

%>
</BODY>
</html>