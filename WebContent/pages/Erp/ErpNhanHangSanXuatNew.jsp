<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.nhanhangsanxuat.*"%>
<%@ page import="geso.traphaco.erp.beans.nhanhangsanxuat.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.Donvi"%>
<%@ page import="geso.traphaco.erp.beans.donmuahangtrongnuoc.IDonvi"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>
<%@page import="geso.traphaco.center.util.Utility"%>



<% IErpNhanhang_Giay nhBean = (IErpNhanhang_Giay)session.getAttribute("nhBean");// %>
<% ResultSet dvthList = nhBean.getDvthList(); %>
<% ResultSet poList = nhBean.getDmhList(); %>
<% ResultSet ndnList = nhBean.getNdnList(); %>
<% ResultSet ldnList = nhBean.getLdnList(); %>
<% ResultSet rskhoNhan = nhBean.getrskhoNhan(); %>
<% ResultSet nccList = nhBean.getNccList(); %>
<% ResultSet hoadonNCCList = nhBean.getHdNccList(); %>
<% ResultSet khRs = nhBean.getKhachhangRs(); %>
<% List<ISanpham> spList = nhBean.getSpList();  %>
<% List<KhuVucKho> listKhuVucKho = nhBean.getListKhuVucKho();  %>
<% List<IDonvi> listDonvi = nhBean.getListDonvi();  %>
<% ResultSet rsKhoChoXuLy = nhBean.getRsKhoChoXuLy(); %>
<% List<KhuVucKho> listKhuVucKhoCXL = nhBean.getListKhuVucKhoCXL(); %>
<% NumberFormat formater = new DecimalFormat("#,###,###.###"); %>
<%	NumberFormat formatter2 = new DecimalFormat("#,###,###.###");
	ResultSet congdoanRs = nhBean.getCongdoanRs();
	ResultSet DoituongNhanRs= nhBean.getDoituongNhanRs();
%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%	Utility util = (Utility) session.getAttribute("util"); 
	String sum = (String) session.getAttribute("sum");
	
	System.out.println("oai aoia ");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TueLinh/index.jsp");
	}else{	
		int pb= util.getPhongBan(userId); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>TueLinh - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link rel="stylesheet" type="text/css" href=".. " />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent2.js"></script>
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

<style>
.detail th {
	padding: 1px 3px;
	text-align: left;
	border: 1px solid #ccc;
}

.detail td {
	padding: 1px 3px;
	text-align: left;
}

.rightaligned {
	margin-right: 0;
	margin-left: auto;
}
</style>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("#nccId").select2(); });
     $(document).ready(function() { $("#loaihh").select2(); });
     $(document).ready(function() { $("#dvthId").select2(); });
     $(document).ready(function() { $("#ldnId").select2(); });
     $(document).ready(function() { $("#khonhanId").select2(); });
     $(document).ready(function() { $("#sohoadonNCC").select2(); });
     $(document).ready(function() { $("select").select2(); });
 </script>

<script language="javascript" type="text/javascript">
	// ham tinh so luong tong cua cac thung
	function sumthung()
	{
		var sothung=document.getElementById('sothung').value;
		var soluongthung=document.getElementById('soluongthung').value;
		return (sothung*soluongthung);
	}
	
	// ham kiem tra nhap so thung va soluong/thung
	 function ValidateForm()
	 {
		 var sothung=document.getElementById('sothung');
			if (sothung.value.trim().length == 0) {
				alert("Nhập số thùng");
				sothung.focus();
				return false;
			}
			
		var soluongthung=document.getElementById('soluongthung');
				if (soluongthung.value.trim().length == 0) {
					alert("Nhập số lượng thùng");
					soluongthung.focus();
					return false;
				}
		 return true;
		 
	 }
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
		
		var nccNK = document.getElementById("nccNK").value;
		var muahangId = document.getElementsByName("muahang_fk");		
		
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
				// them sothung
				var id =  sanpham.item(i).value + '.' + muahangId.item(i).value+i;
				var sothung = document.getElementsByName(id + '.sothung');
				var solo = document.getElementsByName(id + '.solo');
				var soluong = document.getElementsByName(id + '.soluongtong');
				var ngaysanxuat = document.getElementsByName(id  + '.ngaysanxuat');
				var ngayhethan = document.getElementsByName(id + '.ngayhethan');
				
				for( var j = 0; j < soluong.length; j++)
				{
					/* if(solo.item(j).value != "" && soluong.item(j).value == "")
					{
						soluong.item(j).value = 0;
					} */
					
					if(ngaysanxuat.item(j).value != "" && hansudung.item(i).value != "" && ngayhethan.item(j).value=="")
					{
						ngayhethan.item(j).value = TangDate(ngaysanxuat.item(j).value, hansudung.item(i).value);
					}
					
					
					console.log(solo.item(j).value);
					console.log(soluong.item(j).value)
					
					if(solo.item(j).value != "" && soluong.item(j).value != "")
					{					
						//Check solo : không được nhập trùng lô
						/* var flag = false;
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
						  */
						var slg =( soluong.item(j).value.replace(/,/g,""));
						tongluong = parseFloat(tongluong) + parseFloat(slg);
					}
					else
					{
						/* if( solo.item(j).value == "" )
						{
							soluong.item(j).value = "";
							ngaysanxuat.item(j).value = "";
							ngayhethan.item(j).value = "";
						} */
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
	
	function replaceAll(str, find, replace) {
		  return str.replace(new RegExp(find, 'g'), replace);
	}
	
	 function saveform()
	 {	
		 
		 //document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
		 /* var check=ValidateForm(); */
		 
		 var muaHangFk = document.getElementsByName("muahang_fk");
		 var muaHangId = document.getElementsByName("idhangmua");
		 
		 var loaispct = document.getElementsByName("loaispct");
		 var dem = 0 ;
		 for(dem = 0 ; dem < muaHangFk.length;dem++){
			 if(loaispct.item(dem).value=="100000"){
					 var tenMe = document.getElementsByName(muaHangId.item(dem).value+"."+muaHangFk.item(dem).value+dem+"."+"meX");
					 var soLuongMe = document.getElementsByName(muaHangId.item(dem).value+"."+muaHangFk.item(dem).value+dem+"."+"soluongX");
					 var soLuongTong = document.getElementsByName(muaHangId.item(dem).value+"."+muaHangFk.item(dem).value+dem+"."+"soluongtong");
					 var tongChiTiet = 0;
					 var i = 0 ;
					 for(i =0; i<tenMe.length;i++){
						 tongChiTiet = parseFloat(tongChiTiet) + parseFloat(replaceAll(soLuongMe.item(i).value,",",""));
						 if(tenMe.item(i).value =="" || soLuongMe.item(i).value =="" || isNaN(replaceAll(soLuongMe.item(i).value,",",""))){
							 alert("Nhập lại thông tin trên từng mẻ");
							 return ;
						 }
					 }
					 var soLuongSum = 0;
					 for(i = 0 ; i<soLuongTong.length; i++){
						 soLuongSum = parseFloat(soLuongSum) + parseFloat(replaceAll(soLuongTong.item(i).value,",",""));
					 }
		
					 if(roundNumber( soLuongSum,3)  != roundNumber(tongChiTiet,3)){
						 alert("Tổng số lượng trên từng mẻ không bằng số lượng tổng :Số lượng "+soLuongSum+".  Số lượng chi tiết : "+tongChiTiet);
						 return ;
					 }
			 }
		 }
		 var check = true;
		 if(check==false){
			 return false;
		}else{
			 
			 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
		
			document.forms['nhForm'].action.value='save';
		     document.forms['nhForm'].submit();
		}
		 
	 	 
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
	 function changeSoMe(){
		 var muaHangFk = document.getElementsByName("muahang_fk");
		 var muaHangId = document.getElementsByName("idhangmua");
		 var dem = 0 ;
		 for(dem = 0 ; dem < muaHangFk.length;dem++){
			 var soThung = document.getElementsByName(muaHangId.item(dem).value+"."+muaHangFk.item(dem).value+dem+"."+"sothung");
			 var i =0;
			 for(i = 0 ; i<soThung.length ; i++){
				 if (soThung.item(i).value == ""){
					 alert ("Nhập thông tin số thùng của từng phiếu mua");
					 return ;
				 }
			 }
		 }
		 
		 document.forms['nhForm'].action.value='save';
		 document.forms['nhForm'].theloai.value='changeSoMe';
	     document.forms["nhForm"].submit();
	 }
	 
	 function TaoRaSoMe(element){
		 var arr = element.id.split("_");
		 var index = arr[1];
		 var me = document.getElementsByName(""+ element.name);
		 for(var i=index; i< me.length; i++){
			 me.item(i).value = element.value;
		 }
	 }
	 function TaoRaKhuVuc(element){
		 var arr = element.id.split("_");
		 var index = arr[1];
		 
		 var khuvuc = document.getElementsByName(""+ element.name);
		 var value = element.value;
		  
		 for(var i= index ; i< khuvuc.length; i++ ){
			 $("#"+ khuvuc[i].id).select2("val", value); //set the value
		 }
	 }
	 
	 function download()
	 {
		 document.nhForm.setAttribute('enctype', "multipart/form-data", 0);	
		 document.forms['nhForm'].action.value='download';
	     document.forms["nhForm"].submit(); 
	 }
	 
	 function Xoafile()
	 {
	 	var xmlhttp;
	 	if (window.XMLHttpRequest)
	 	{// code for IE7+, Firefox, Chrome, Opera, Safari
	 	   xmlhttp = new XMLHttpRequest();
	 	}
	 	else
	 	{
	 	   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	 	}
	 	xmlhttp.onreadystatechange=function()
	 	{
	 	   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
	 	   {
	 		   console.log("thong tin tra ve: "+xmlhttp.responseText);
	 		   if(xmlhttp.responseText=='')
	 			   {
	 	       			/* document.getElementById('tenfilea').innerHTML = xmlhttp.responseText; */
	 	       			document.getElementById('tenfilea').value = xmlhttp.responseText;
	 	       			document.getElementById("valueten").value='';
	 	       			alert('Đã xóa thành công');
	 			   }
	 		   else
	 			   {
	 			   alert(xmlhttp.responseText);
	 			   }
	 	   }
	 	}
	 	if(confirm("Bạn muốn xóa file đính kèm"))
	 	{
	 		console.log("da den chuyen trang: ")
	 		xmlhttp.open("GET","../../ErpNhanhangsanxuatUpdateSvl?userId="+document.getElementById("userId").value+"&id=" + document.getElementById("id").value+"&task=xoafilenew",true);
	 		xmlhttp.send();
	 	}
	 	else
	 		return false;
	 }
</script>




</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="nhForm" method="post" enctype="multipart/form-data" action="../../ErpNhanhangsanxuatUpdateSvl">
				<input type="hidden" id="id"  name="id" value='<%=nhBean.getId()%>'>
				
		<input type="hidden" id="userId" name="userId" value='<%= userId %>'> <input
			type="hidden" name="action" value='1'> <input type="hidden"
			name="loaimh" value='<%= nhBean.getLoaimh() %>'> <input
			type="hidden" id="nccNK" name="nccNK"
			value='<%= nhBean.getIsNCCNK()%>'>
			<input type="hidden" name="theloai" />
			<input type="hidden" name="tiledvdl" value="<%= nhBean.getTilequydoi_dvdl()%>">

		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">
					<% if(nhBean.getLoaimh().equals("1")){ %>
						Quản lý cung ứng > Sản xuất > Nhận hàng sản xuất> Tạo mới
					<% }else{ %>
						Quản lý cung ứng > Sản xuất > Nhận hàng sản xuất> Tạo mới
					<% } %>
				</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%= userTen %>
					&nbsp;
				</div>
			</div>

			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="../../ErpNhanhangsanxuatSvl?userId=<%= userId %>"> <img
					src="../images/Back30.png" alt="Quay ve" title="Quay ve"
					border="1px" longdesc="Quay ve" style="border-style: outset">
						</A> 
					<% if(nhBean.getTrangthai().equals("0")|| nhBean.getTrangthai().equals("")){ %>	
					
						<span id="btnSave"> <A href="javascript:saveform()"> <IMG
						src="../images/Save30.png" title="Luu lai" alt="Luu lai"
						border="1px" style="border-style: outset">
				</A> </span>
				
				<%} %>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" rows="1" readonly="readonly"
						style="width: 100%"><%= nhBean.getMsg() %></textarea>
					<% nhBean.setMsg(""); %>
				</fieldset>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Nhận hàng</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD width="100px" class="plainlabel" valign="top">Ngày
									chứng từ</TD>
								<TD class="plainlabel" valign="top" colspan="3" ><input
									type="text" class="days" id="ngaynhanhang" name="ngaynhanhang"
									value="<%= nhBean.getNgaynhanhang() %>" maxlength="10" readonly />
								</TD>
								<TD style="display: none" width="150px" class="plainlabel"  valign="top">Kho chờ xử lý </TD>
								<TD style="display: none" class="plainlabel" valign="top"><select
									name="khochoxulyId" id="khochoxulyId"  
									style="width: 250px;">
									<option value=""> </option>
										<%
                        		if(rsKhoChoXuLy != null)
                        		{
                        			try
                        			{
                        			while(rsKhoChoXuLy.next())
                        			{  
                        			if( rsKhoChoXuLy.getString("pk_seq").equals(nhBean.getKhoChoXuLy())){ %>
										<option value="<%= rsKhoChoXuLy.getString("pk_seq") %>"
											selected="selected"><%= rsKhoChoXuLy.getString("ten") %></option>
										<%}else { %>
										<option value="<%= rsKhoChoXuLy.getString("pk_seq") %>"><%= rsKhoChoXuLy.getString("ten") %></option>
										<% } } rsKhoChoXuLy.close();} catch(SQLException ex){ex.printStackTrace();}
                        		}
                        	%>
								</select></TD>


							</TR>
							<TR>
								<TD class="plainlabel">Loại hàng hóa</TD>
								<TD class="plainlabel"><select name="loaihh" id="loaihh"
									onChange="submitform();" style="width: 250px;">
									   
										<option selected="selected" value="0">Sản phẩm nhập kho</option>
									 
								</select></TD>

								<TD width="150px" class="plainlabel" valign="top">Kho sản xuất</TD>
								<TD class="plainlabel" valign="top">
								<select name="khonhanId" id="khonhanId"  style="width: 250px;">
									<option value=""> </option>
										<%
                        		if(rskhoNhan != null)
                        		{
                        			try
                        			{
                        			while(rskhoNhan.next())
                        			{  
                        			if( rskhoNhan.getString("pk_seq").equals(nhBean.getKhoNhanId())){ %>
										<option value="<%= rskhoNhan.getString("pk_seq") %>"
											selected="selected"><%= rskhoNhan.getString("ten") %></option>
										<%}else { %>
										<option value="<%= rskhoNhan.getString("pk_seq") %>"><%= rskhoNhan.getString("ten") %></option>
										<% } } rskhoNhan.close();} catch(SQLException ex){}
                        		}
                        	%>
								</select></TD>

								<TD class="plainlabel" style="display: none;">Nội dung
									nhận</TD>
								<TD class="plainlabel" style="display: none;"><select
									name="ndnId" id="ndnId" onChange="changePO()">
										<%
                        		if(ndnList != null)
                        		{
                        			try
                        			{
                        			while(ndnList.next())
                        			{  
                        			if( ndnList.getString("pk_seq").equals(nhBean.getNdnId())){ %>
										<option value="<%= ndnList.getString("pk_seq") %>"
											selected="selected"><%= ndnList.getString("noidung") %></option>
										<%}else { %>
										<option value="<%= ndnList.getString("pk_seq") %>"><%= ndnList.getString("noidung") %></option>
										<% } } ndnList.close();} catch(SQLException ex){}
                        		}
                        	%>
								</select></TD>
							</TR>

							<%if( nhBean.getKhoNhanId().trim().length() > 0 && (nhBean.getLoaikho().equals("5") || nhBean.getLoaikho().equals("8")) ) { %>
							<TR>
								<TD class="plainlabel">Khách hàng</TD>
								<TD class="plainlabel" colspan="3"><input type="hidden"
									name="loaikho" value=<%= nhBean.getLoaikho() %>> <select
									name="khId" id="khId">
										<%
                        		if(khRs != null)
                        		{
                        			try
                        			{
                        			while(khRs.next())
                        			{  
                        			if( khRs.getString("pk_seq").equals(nhBean.getKhachhangId())){ %>
										<option value="<%= khRs.getString("pk_seq") %>"
											selected="selected"><%= khRs.getString("ten") %></option>
										<%}else { %>
										<option value="<%= khRs.getString("pk_seq") %>"><%= khRs.getString("ten") %></option>
										<% } } khRs.close();} catch(SQLException ex){}
                        		}
                        	%>
								</select></TD>
							</TR>
							<%} %>

							<TR>
								<TD class="plainlabel">Đơn vị thực hiện</TD>
								<TD class="plainlabel"><select name="dvthId" id="dvthId"
									 style="width: 250px;">
										<option value=""></option>
										<%
                        		if(dvthList != null)
                        		{
                        			try
                        			{
                        			while(dvthList.next())
                        			{  
                        			if( dvthList.getString("pk_seq").equals(nhBean.getDvthId())){ %>
										<option value="<%= dvthList.getString("pk_seq") %>"
											selected="selected"><%= dvthList.getString("ten") %></option>
										<%}else { %>
										<option value="<%= dvthList.getString("pk_seq") %>"><%= dvthList.getString("ten") %></option>
										<% } } dvthList.close();} catch(SQLException ex){}
                        		}
                        	%>
								</select></TD>
								
								<TD class="plainlabel">Số lệnh sản xuất </TD>
								<TD class="plainlabel">
									 
									<select name="sohoadonNCC" id="sohoadonNCC"
									onChange="changePO();" style="width: 250px;"   >
										<option value=""></option>
										<%
                    	 	if(hoadonNCCList!=null){
                    	 		try{
                    	 			while(hoadonNCCList.next()){
                    	 			if(hoadonNCCList.getString("pk_seq").trim().equals(nhBean.getHdNccId())){	
                    	 	%>
										<option value="<%= hoadonNCCList.getString("pk_seq") %>"
											selected="selected"><%= hoadonNCCList.getString("sohoadon") %></option>
										<%}else{ %>
										<option value="<%= hoadonNCCList.getString("pk_seq") %>"><%= hoadonNCCList.getString("sohoadon") %></option>
										<%} %>
										<%}}catch(Exception e){e.printStackTrace();}}%>
								</select></TD>
								

							</TR>
							<TR>
								<TD class="plainlabel">Lý do nhận</TD>
								<TD class="plainlabel"><select name="ldnId" id="ldnId" style="width:250px">
										 
										<%
                        		if(ldnList != null)
                        		{
                        			try
                        			{
                        			while(ldnList.next())
                        			{  
                        			if( ldnList.getString("pk_seq").equals(nhBean.getLdnId())){ %>
										<option value="<%= ldnList.getString("pk_seq") %>"
											selected="selected"><%= ldnList.getString("ten") %></option>
										<%}else { %>
										<option value="<%= ldnList.getString("pk_seq") %>"><%= ldnList.getString("ten") %></option>
										<% } } ldnList.close();} catch(SQLException ex){}
                        		}
                        	%>
								</select></TD>

								<TD class="plainlabel" valign="middle">Công Đoạn Sản Xuất </TD>
								<TD class="plainlabel" valign="middle" colspan="3"> 
								<select name="congdoanId" id="congdoanId" style="width:250px" onChange="changePO();">
								<%
                        		if(congdoanRs != null)
                        		{
                        			try
                        			{
                        			while(congdoanRs.next())
                        			{  
                        			if( congdoanRs.getString("pk_seq").equals(nhBean.getIdcongdoan())){ %>
										<option value="<%= congdoanRs.getString("pk_seq") %>"
											selected="selected"><%= congdoanRs.getString("diengiai") %></option>
										<%}else { %>
										<option value="<%= congdoanRs.getString("pk_seq") %>"><%= congdoanRs.getString("diengiai") %></option>
										<% } }} catch(SQLException ex){}
                        		}
                        	%>
                        	</select>
							 	</TD>
	
							</TR>

							<TR>
								<TD class="plainlabel" valign="top">Diễn giải</TD>
								<TD class="plainlabel" valign="top"  >
									<input style="width: 250px;"type="text" id="diengiai" name="diengiai" value="<%= nhBean.getDiengiai() %>" />
								</TD>
								<TD class="plainlabel" valign="middle">Chọn kho nhận hàng đạt</TD>
											<TD class="plainlabel" valign="middle">
											
											 <select style="width: 250px;" id="khonhanhangdatid" name="khonhanhangdatid" onChange="changePO();" >
											 <option   >  </option>
											<%
											ResultSet rskho=nhBean.getRsKhonhanhangdat();
											if(  rskho!=null){
				           	 					while (rskho.next()){ %>
				           	 						<% if( rskho.getString("pk_Seq").equals(nhBean.getKhonhanhangdat()) ) { %>
				           	 							<option selected="selected" value="<%=rskho.getString("pk_Seq")%>" > <%=rskho.getString("ten")%> </option>
				           	 						<%}else{ %>
				           	 							<option value="<%=rskho.getString("pk_Seq")%>" >  <%=rskho.getString("ten")%> </option>
				           	 						<%}%>
				           	 				    <%}  
				           	 				rskho.close();
				           	 					} 
											 %>
											 </select>
											 </TD>

							</TR>
							
							<% if(nhBean.getIsKhoNhanCoDoiTuong().equals("1")){ %>
							<TR>
								<TD class="plainlabel" valign="top"> </TD>
								<TD class="plainlabel" valign="top"  >
								 
								</TD>
 								<TD class="plainlabel" valign="middle"> Chọn công đoạn nhận hàng</TD>
											<TD class="plainlabel" valign="middle">
											
											   <select name="congdoannhanhangId" id="congdoannhanhangId" style="width:250px" >
													<%
					                        		if(DoituongNhanRs != null)
					                        		{
					                        			try
					                        			{
					                        			while(DoituongNhanRs.next())
					                        			{  
					                        			if( DoituongNhanRs.getString("pk_seq").equals(nhBean.getIdCongdoannhan())){ %>
															<option value="<%= DoituongNhanRs.getString("pk_seq") %>"
																selected="selected"><%= DoituongNhanRs.getString("ten") %></option>
															<%}else { %>
															<option value="<%= DoituongNhanRs.getString("pk_seq") %>"><%= DoituongNhanRs.getString("ten") %></option>
															<% } }} catch(SQLException ex){}
					                        		}
					                        	%>
					                        	</select>
											 </TD>

							</TR>
							<%}%>

							<TR>
								<TD class="plainlabel" valign="top">Ghi chú</TD>
								<TD class="plainlabel" valign="top">
								<input type="text" id="ghichu" name="ghichu" value="<%= nhBean.getGhichu() %>" style="width: 250px"/></TD>
								<td class="plainlabel" valign="top">Loại sản phẩm</td>
								<td class="plainlabel" valign="top">
									<select style="width: 250px;" id="loaisanpham" name="loaisanpham" onchange="submitform()">
										<option value=""></option>
										<%  
 											String[] mang=new String[]{"0","1","2","3","4"};
 											String[] mangten=new String[]{"Thành phẩm","Phế phẩm",
 													//"Dư phẩm",
 													"TC - BTP Tái chế"
 													,"Bán thành phẩm","Dư phẩm"};
 								
		 								 	for(int j=0;j<mang.length;j++){
		 									 	if(mang[j].equals(nhBean.getLoaisanpham())){
			 									 %>
			 									 <option selected="selected" value="<%=mang[j]%>"> <%=mangten[j] %> </option>
			 									 <%
			 									 }else{
			 									 %>
			 									 <option value="<%=mang[j]%>"> <%=mangten[j] %> </option>
			 									 <% 
			 									 }
		 								 	}
		 								%>
									</select>
								</td>
							</TR>
							<% if("1".equals(nhBean.getLoaisanpham())){ %>
							<TR>
								<TD class="plainlabel" valign="top"></TD>
								<TD class="plainlabel" valign="top">
								<td class="plainlabel" valign="top">Chọn phế phẩm</td>
								<td class="plainlabel" valign="top">
									<select style="width: 200px;" id="phepham_duphamid" name="phepham_duphamid" onchange="submitform()">
										<option value=""></option>
										<%
											ResultSet phepham=nhBean.getPhepham_duphamRs();
											if(  phepham!=null){
				           	 					while (phepham.next()){ %>
				           	 						<% if( phepham.getString("pk_Seq").equals(nhBean.getIdphepham_dupham()) ) { %>
				           	 							<option selected="selected" value="<%=phepham.getString("pk_Seq")%>" > <%=phepham.getString("tensp")%> </option>
				           	 						<%}else{ %>
				           	 							<option value="<%=phepham.getString("pk_Seq")%>" >  <%=phepham.getString("tensp")%> </option>
				           	 						<%}%>
				           	 				    <%}  
				           	 				phepham.close();
				           	 					} 
											 %>
									</select>
								</td>
							</TR>
							<% } %>
							<% if("2".equals(nhBean.getLoaisanpham())){ %>
							<TR>
								<TD class="plainlabel" valign="top"></TD>
								<TD class="plainlabel" valign="top">
								<td class="plainlabel" valign="top">Chọn TC - BTP Tái chế</td>
								<td class="plainlabel" valign="top">
									<select style="width: 200px;" id="phepham_duphamid" name="phepham_duphamid" onchange="submitform()">
										<option value=""></option>
										<%
											ResultSet dupham=nhBean.getPhepham_duphamRs();
											if(  dupham!=null){
				           	 					while (dupham.next()){ %>
				           	 						<% if( dupham.getString("pk_Seq").equals(nhBean.getIdphepham_dupham()) ) { %>
				           	 							<option selected="selected" value="<%=dupham.getString("pk_Seq")%>" > <%=dupham.getString("tensp")%> </option>
				           	 						<%}else{ %>
				           	 							<option value="<%=dupham.getString("pk_Seq")%>" >  <%=dupham.getString("tensp")%> </option>
				           	 						<%}%>
				           	 				    <%}  
				           	 				dupham.close();
				           	 					} 
											 %>
									</select>
								</td>
							</TR>
							<% } %>
							<% if("3".equals(nhBean.getLoaisanpham())){ %>
							<TR>
								<TD class="plainlabel" valign="top"></TD>
								<TD class="plainlabel" valign="top">
								<td class="plainlabel" valign="top">Chọn bán thành phẩm </td>
								<td class="plainlabel" valign="top">
									<select style="width: 200px;" id="phepham_duphamid" name="phepham_duphamid" onchange="submitform()">
										<option value=""></option>
										<%
											ResultSet dupham=nhBean.getPhepham_duphamRs();
											if(  dupham!=null){
				           	 					while (dupham.next()){ %>
				           	 						<% if( dupham.getString("pk_Seq").equals(nhBean.getIdphepham_dupham()) ) { %>
				           	 							<option selected="selected" value="<%=dupham.getString("pk_Seq")%>" > <%=dupham.getString("tensp")%> </option>
				           	 						<%}else{ %>
				           	 							<option value="<%=dupham.getString("pk_Seq")%>" >  <%=dupham.getString("tensp")%> </option>
				           	 						<%}%>
				           	 				    <%}  
				           	 				dupham.close();
				           	 					} 
											 %>
									</select>
								</td>
							</TR>
							<% } %>

							<% if("4".equals(nhBean.getLoaisanpham())){ %>
							<TR>
								<TD class="plainlabel" valign="top"></TD>
								<TD class="plainlabel" valign="top">
								<td class="plainlabel" valign="top">Chọn dư phẩm </td>
								<td class="plainlabel" valign="top">
									<select style="width: 200px;" id="phepham_duphamid" name="phepham_duphamid" onchange="submitform()">
										<option value=""></option>
										<%
											ResultSet dupham=nhBean.getPhepham_duphamRs();
											if(  dupham!=null){
				           	 					while (dupham.next()){ %>
				           	 						<% if( dupham.getString("pk_Seq").equals(nhBean.getIdphepham_dupham()) ) { %>
				           	 							<option selected="selected" value="<%=dupham.getString("pk_Seq")%>" > <%=dupham.getString("tensp")%> </option>
				           	 						<%}else{ %>
				           	 							<option value="<%=dupham.getString("pk_Seq")%>" >  <%=dupham.getString("tensp")%> </option>
				           	 						<%}%>
				           	 				    <%}  
				           	 				dupham.close();
				           	 					} 
											 %>
									</select>
								</td>
							</TR>
							<% } %>
							
							
							
							<TR style="padding-bottom: 40px ; display: none">
								
								<% System.out.println(" file name :"+nhBean.getFilename() ) ;%>
								
								<TD class="plainlabel"  style="display: none">Chứng từ upload</TD>
						  	  	<TD class="plainlabel" style="display: none" >
						  	  	 <INPUT type="file" name="filename" value="" style="width: 400px">
						  	  		<input type="hidden" id="valueten" name="congvan" value="<%= nhBean.getFilename()%>">
						  	  		&nbsp;&nbsp;&nbsp;&nbsp;
						  	  		<!-- <a class="button2" href="javascript:download()"> 
										<img style="top: -2px; width: 27px; height: 27px;  " src="../images/download.png" alt="">Download chứng từ
									</a> -->
						  	  	</TD>
								<TD class="plainlabel" colspan="2">
									
								</TD>
							</TR>
						<%// xoa file neu can %>
						<% if(nhBean.getFilename().length() > 0 ) {	%>	
							<TR>
							<TD class="plainlabel" style="display: none" >Tên file</TD>
							<td class="plainlabel"  style="display: none" colspan="3">
							
								<input type="text" id="tenfilea" name="tenfilea" value="<%= nhBean.getFilename()%>" readonly="readonly" style="width: 400px; height: 25px">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<!-- <a class="button2"  href="javascript:Xoafile()" > 
										<img style="top: -2px; width: 27px; height: 27px;  " src="../images/x-button.png" alt="Xóa file" longdesc="Xóa file"  style="cursor: pointer;"   onclick="Xoafile()">Xóa file
								</a>  -->
								
								&nbsp;&nbsp;&nbsp;&nbsp;
						  	  		<a  class="button2" href="javascript:download()" > 
										<img style="top: -2px; width: 27px; height: 27px;  " src="../images/download.png" alt="">Download chứng từ
									</a>
								
							</td>
							<!-- <td colspan="1" class="plainlabel">
								 <a class="button2" href="javascript:download()"> 
										<img style="top: -2px; width: 27px; height: 27px;  " src="../images/x-button.png" alt="Xóa file" ongdesc="Xóa file" onclick="Xoafile()">Xóa file
								</a> 
							</td> -->
							</TR>						
						<%} %>	
							
							

						</TABLE>
						
						
						
						
						
						<hr>
					</div>

					<div align="left" style="width: 100%; float: none; clear: none;"
						class="plainlabel">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1"
							cellspacing="1">
							<TR class="tbheader">
								<TH align="center" width="3%">STT</TH>

								<TH style="display: none" align="center" width="10%">Số PO</TH>
								<% if(nhBean.getLoaihanghoa().equals("1")) { %>
								<TH align="center" width="15%">Tài sản</TH>
								<TH align="center" width="20%">Diễn giải</TH>
								<% } else { if(nhBean.getLoaihanghoa().equals("2")) { %>
								<TH align="center" width="15%">Chi phí</TH>
								<TH align="center" width="20%">Diễn giải</TH>
								<% } else { if(nhBean.getLoaihanghoa().equals("0")) { %>
								<TH align="center" width="10%">Mã sản phẩm</TH>
								<TH align="center" width="20%">Tên sản phẩm</TH>
								<!-- <TH align="center" width="10%">Kho nhận</TH> -->

								<% } else { %>
								<TH align="center" width="15%">CCDC</TH>
								<TH align="center" width="20%">Diễn giải</TH>
								<% } } } %>
								<TH align="center" width="8%">ĐVT</TH>
								<% if(nhBean.getLoaimh().equals("0")){ %>
								<TH align="center" width="6%">SL theo HĐ</TH>
								<%}else{ %>
								<TH align="center" width="6%">SL đặt</TH>
								<%} %>

								<%if(pb==100000){ %>
								<TH align="center" width="8%">Đơn giá</TH>
								<%} %>

								<TH align="center" width="8%">SL đã nhận</TH>
								<TH align="center" width="8%">SL nhận</TH>
								<%if(pb == 100000){ %>
								<TH align="center" width="9%">Thành tiền</TH>
								<%} %>

								<TH align="center" width="8%" style="display: none">Còn lại</TH>

							</TR>


							<!--  LOAD SẢN PHẨM RA -->

							<%
                               
                	for(int i = 0; i < spList.size(); i++)
                	{
                		
                		ISanpham sp = spList.get(i);
	               		%>
							<tr>
								<td align="center"><input type="text"
									style="width: 100%; text-align: center;" value="<%= i + 1 %>"
									name="stt" readonly="readonly"></td>

								<td style="display: none"><input type="text"
									style="width: 100%; text-align: left;"
									value="<%= sp.getSoPO() %>" name="po" readonly="readonly">
									
									<input type="hidden" value="<%= sp.getMuahang_fk() %>" name="muahang_fk" id="muahang_fk">
									<input type="hidden" value="<%=  sp.getIdmarquette() %>"  name="idmarquette" >
									<input type="hidden" value="<%=  sp.getIsKiemDinh() %>"  name="kiemdinhsp" >
									<input type="hidden" value="<%=  sp.getloaisp() %>"  name="loaispct" >
									
								</td>

								<td align="left"><input type="text"
									style="width: 100%; text-align: left;"
									value="<%= sp.getMa() %>" name="mahangmua" readonly="readonly">
									<input type="hidden" value="<%= sp.getId() %>" name="idhangmua" id="muahang_id">
									<input type="hidden" value="<%= sp.getHansudung() %>"
									name="hansudung"> <input type="hidden"
									value="<%= sp.getDungsai() %>" name="dungsai"> <input
									type="hidden" value="<%= sp.getSoluongMaxNhan() %>"
									name="soluongMaxNhan"> <input type="hidden"
									value="<%= sp.getDongia() %>" name="dongiaMua"> <input
									type="hidden" value="<%= sp.getTiente() %>" name="tiente">
									<input type="hidden" value="<%= sp.getTigiaquydoi() %>"
									name="tygiaquydoi"> <input type="hidden"
									value="<%= sp.getDongiaViet() %>" name="dongiaViet"></td>
								<td align="left"><input type="text"
									style="width: 100%; text-align: left;"
									value="<%= sp.getDiengiai() %>" name="tenhangmua"
									readonly="readonly"></td>

								<% if(nhBean.getLoaihanghoa().equals("0")) { %>
								<td align="right" style="display: none">
									<% if(nhBean.getNdnId().equals("100001")) { %> <input
									type="hidden" style="width: 100%; text-align: left;" value=""
									name="khonhanTen"> <select name="khonhanIds"
									style="width: 100%;">
										<%
           	 								if(sp.getKhoNhanRs() != null)
           	 								{
           	 									while(sp.getKhoNhanRs().next())
           	 									{
           	 										if( sp.getKhonhanId().equals(sp.getKhoNhanRs().getString("khoId")) ) {
           	 										%>
										<option value="<%= sp.getKhoNhanRs().getString("khoId") %>"
											selected="selected"><%= sp.getKhoNhanRs().getString("khoTen") %></option>
										<% } else { %>
										<option value="<%= sp.getKhoNhanRs().getString("khoId") %>"><%= sp.getKhoNhanRs().getString("khoTen") %></option>
										<% } 
           	 									}
           	 									sp.getKhoNhanRs().close();
           	 								}
           	 							%>
								</select> <% } else { %> <input type="hidden"
									style="width: 100%; text-align: left;"
									value="<%= sp.getKhonhanId() %>" name="khonhanIds"> <% } %>
								</td>
								<% } %>
									
								<td align="left"><input type="hidden"
									style="width: 100%; text-align: left;"
									value="<%= sp.getDvdl() %>" name="dvdl" readonly="readonly">
									
									<input type="hidden"
									style="width: 100%; text-align: left;"
									value="<%= sp.getTiLeQuyDoiDv() %>" name="tilequydoiDV" readonly="readonly">
									
									<select style="width: 100%" name="dvdl" disabled id="<%= "donvitinh_"+i %>" style=" width: 200px">
													<option value=""></option>
												<% if (listDonvi.size() > 0)
													{
														for (int j = 0; j < listDonvi.size(); j++)
														{
															IDonvi donvi =  listDonvi.get(j);
															//System.out.println("DVDL giao dien:"+sp.getDvdl());
															if(donvi.getId().equals(sp.getDvdl())){
														%>
															<option value="<%=donvi.getId()%>" selected="selected"><%=donvi.getDonvi()%></option>
														<% } else { %>
															<option value="<%=donvi.getId()%>"><%=donvi.getDonvi()%></option>
														 <% } } }  %>
										</select>
									
								</td>
								
								
								<td align="right"><input type="text"
									style="width: 100%; text-align: right;"
									value="<%= sp.getSoluongdat() %>" name="soluongdat"
									readonly="readonly"></td>
								<%if(pb == 100000){ %>
								<td align="right"><input type="text"
									style="width: 100%; text-align: right;"
									value="<%= sp. getDongia() %>" readonly="readonly"></td>
								<%} %>
								<td align="right"><input type="text"
									style="width: 100%; text-align: right;"
									value="<%= sp.getSoluongDaNhan() %>" name="soluongdanhan"
									readonly="readonly"></td>

								<td align="left">
									<% if(nhBean.getLoaihanghoa().equals("0")){ %> <input type="text"
									style="width: 75%; text-align: right;"
									value="<%= sp.getSoluongnhan() %>" name="soluongnhan"
									readonly="readonly"> <a href=""
									id="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk()+i %>"
									rel="subcontent<%= i %>"> <img alt="Số lô - Số lượng nhận"
										src="../images/vitriluu.png">
								</a>


									<DIV id="subcontent<%= i %>"
										style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; 
											background-color: white; width: 850px; padding: 4px;  z-index:20; height: 400px;overflow-y: scroll;">
										<table width="80%" align="center" class="detail">
												<% 
												String display_none="";
												if(!sp.getloaisp().equals("100000")){
													display_none="none";
												%> 
												
												<%} %> 
												
											<tr>
												<th width="100px">Số lô</th>
												 
												<th style="display: <%=display_none%>"" width="100px">Số thùng</th>
												 
												<th width="100px">Số lượng tổng</th>
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
												<input type="text" size="100px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".solo" %>"
													value="<%= spDetail.getSolo() %>"  
													  /> <input type="hidden"
													value="<%= spDetail.getDongiaLo() %>"
													name="<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk() +i+ ".dongialo"%>" />
												</td>
												 
												<td  style="display:<%=display_none%>">
												 
												<input type="text" size="120px"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+  ".sothung" %>"
													value="<%= spDetail.getSothung() %>" id="sothung"
													onchange="replaces()" onkeypress="return keypress(event)" />
													</td>
												<td>
													<input type="text" size="100px"
														name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".soluongtong" %>"
														value="<%=spDetail.getSoluong() %>"
														style="text-align: right;"
														onchange="replaces()" />
													 <input type="hidden" size="100px"
														name="<%= sp.getId()+ "." + sp.getMuahang_fk() +i+ ".soluongmax" %>"
														value="<%= spDetail.getSoluongmax() %>"
														style="text-align: right;" onchange="replaces()" /></td>
												<td><input type="text" size="100px" class="days"
													name="<%= sp.getId() + "."  + sp.getMuahang_fk() +i+ ".ngaysanxuat" %>"
													value="<%= spDetail.getNgaySx() %>" readonly="readonly"
													onchange="replaces()" readonly="readonly" />
												</td>
												<td><input type="text" size="100px" class="days"
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngayhethan" %>"
													value="<%= spDetail.getNgayHethan() %>" readonly="readonly"
													readonly="readonly" /></td>
												 
											</tr>
											<% stt++; }
				                        	} 
				                        %>
							 <% if(sp.getloaisp().equals("100000")){%>
											<tr>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td class="rightaligned"><a class="button3"
													href="javascript:changeSoMe()" style="width: 80px;">Ghi nhận</a>
												</td>
											</tr>
										</table>

										<table width="80%" align="center" class="detail">
											<tr>
												<th width="10%">Mã thùng</th>
												<th width="40%">Khu vực</th>
												<th width="20%">Mẻ</th>
												<th width="15%">Số lượng</th>
												<th width="15%">Số lô</th>
											</tr>
											
											<% for(int j=0; j< sp.getListDetailMeSp().size(); j++){ %>
											<% DetailMeSp temp = sp.getListDetailMeSp().get(j); %>
												<tr>
													<td><input type="text" size="100px" 
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".mathungX" %>"
													value="<%= temp.getMaThung() %>" ></td>
													<!-- Phần khu vực này sẽ căn cứ theo sản phẩm nếu có kiểm định thì hiển thị khu vực của kiểm định
													Không kiểm định thì hiển thị không kiểm định -->
													
													<% System.out.println("Sản phẩm kiểm định:"+ sp.getIsKiemDinh()); %>
													<td>
														<% if(sp.getIsKiemDinh().equals("0")){ %>
															<select name= "<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".khuvucX" %>"  
															id = "<%= "sp"+ sp.getId() + sp.getMuahang_fk() +i+ "khuvucX_" +j %>"  onchange= "TaoRaKhuVuc(this); " 
																style="width: 270px;">
																<%for (int k=0; k<listKhuVucKho.size(); k++){ %>
																	<% if (listKhuVucKho.get(k).getId().equals(temp.getKhuVuc())){ %>
																		<option value= "<%= listKhuVucKho.get(k).getId()%>" selected > 
																			<%=listKhuVucKho.get(k).getTen() %> </option>
																	<%} else { %>
																		<option value= "<%= listKhuVucKho.get(k).getId()%>"> 
																			<%=listKhuVucKho.get(k).getTen() %> </option>
																	<%} %>
																<%} %>
															</select>
														
														<%} else { %>
														
															<select name= "<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".khuvucX" %>"  
															id = "<%= "sp"+ sp.getId() + sp.getMuahang_fk() +i+ "khuvucX_" +j %>"  onchange= "TaoRaKhuVuc(this); "
															style="width: 270px;">
																<%for (int k=0; k<listKhuVucKhoCXL.size(); k++){ %>
																	<% if (listKhuVucKhoCXL.get(k).getId().equals(temp.getKhuVuc())){ %>
																		<option value= "<%= listKhuVucKhoCXL.get(k).getId()%>" selected > 
																			<%=listKhuVucKhoCXL.get(k).getTen() %> </option>
																	<%} else { %>
																		<option value= "<%= listKhuVucKhoCXL.get(k).getId()%>"> 
																			<%=listKhuVucKhoCXL.get(k).getTen() %> </option>
																	<%} %>
																<%} %>
															</select>
														<%} %>
														
													</td>
													
													<td><input type="text" size="100px" name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".meX" %>"
													value="<%= temp.getMe() %>"    id= "<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".meX_"+j %>" onchange= "TaoRaSoMe(this); " ></td>
													
													<td><input type="text" size="100px" 
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".soluongX" %>"
													value="<%= temp.getSoLuong() %>" ></td>
													
													<td><input type="text" size="100px" 
													name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".soloX" %>"
													value="<%= temp.getSoLo() %>" readonly="readonly">
													 <!-- Thẻ hidden -->
													 <input type="hidden" name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngaysanxuatX" %>"
													  value = "<%= temp.getNgaySanXuat()%>" >
													   <input type="hidden" name="<%= sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngayhethanX" %>"
													  value = "<%= temp.getNgayHetHan()%>" >
													  
													</td>
												</tr>
											<%}%>
										</table>
										<%} %>


										<div align="right">
											<label style="color: red"></label>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
												href="javascript:dropdowncontent.hidediv('subcontent<%= i %>')">Hoàn
												tất</a>
										</div>
									</DIV> <% } else {  %> <input type="text"
									style="width: 100%; text-align: right;"
									value="<%= sp.getSoluongnhan() %>" name="soluongnhan">
									<% } %>
								</td>

								<%if(pb == 100000){ %>
								<td align="right"><input type="text"
									style="width: 100%; text-align: right;"
									value="<%= sp.getthanhtien() %>" readonly="readonly">
								</td>
								<%} %>

								<td align="right" style="display: none">
									<%
           	 						String conlai = sp.getSoluongdat().replaceAll(",", "") ;
           	 						if(sp.getSoluongnhan().length() > 0)
           	 						{
           	 							conlai = formater.format(Double.parseDouble(conlai) - Double.parseDouble(sp.getSoluongnhan().replaceAll(",", "") ));
           	 						}
           	 					%> <input type="text"
									style="width: 100%; text-align: right;" value="<%= conlai %>"
									name="conlai" readonly="readonly"></td>


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
		   dropdowncontent.init('<%= sp.getId() + "." + sp.getNgaynhandukien() + "." + sp.getMuahang_fk()+i  %>', "left-bottom", 300, "click");
	<%} %>
</script>

	<%
try{
	
	
/* 	if(spList!=null){
		spList.clear(); 
	}
	if(dvthList!=null){
		dvthList.close();
	}
	if(poList!=null){
		poList.close();
	}
	if(ndnList!=null){
		ndnList.close();
	}
	if(ldnList!=null){
		ldnList.close();
	}
	if(rskhoNhan!=null){
		rskhoNhan.close();
	}
	if(nccList!=null){
		nccList.close();
	}
	if(hoadonNCCList!=null){
		hoadonNCCList.close();
	}
	if(khRs!=null){
		khRs.close();
	}
	if(listKhuVucKho!=null){
		listKhuVucKho.clear(); 
	}
	if(listDonvi!=null){
		listDonvi.clear(); 
	}
	if(listKhuVucKhoCXL!=null){
		listKhuVucKhoCXL.clear(); 
	}
	if(rsKhoChoXuLy!=null){
		rsKhoChoXuLy.close();
	}
	if(congdoanRs!=null)
		congdoanRs.close(); */
	nhBean.close();
	
//	if(dvthList!=null) dvthList.close();
	if(poList!=null) poList.close();
//	if(ndnList!=null) ndnList.close();
//	if(ldnList!=null) ldnList.close();
//	if(rskhoNhan!=null) rskhoNhan.close();
	if(nccList!=null) nccList.close();
	if(hoadonNCCList!=null) hoadonNCCList.close();
//	if(khRs!=null) khRs.close();
	if(spList!=null) spList.clear();
	if(listKhuVucKho!=null) listKhuVucKho.clear();
	if(listDonvi!=null) listDonvi.clear();
//	if(rsKhoChoXuLy!=null) rsKhoChoXuLy.close();
	if(listKhuVucKhoCXL!=null) listKhuVucKhoCXL.clear();
	if(congdoanRs!=null) congdoanRs.close();
}catch(Exception er){
	
}
		 }
%>
</BODY>
</html>