<%@page import="geso.traphaco.erp.beans.huychungtu.IErpHuychungtubanhang"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%
	IErpHuychungtubanhang hctbhBean = (IErpHuychungtubanhang) session.getAttribute("hctbhBean");
%>
<%
	ResultSet soctRequest = hctbhBean.getSochungtuRequest();
%>
<%
	ResultSet tthdRequest = hctbhBean.getTTHoaDonRequest();
%>
<%
	ResultSet ttckRequest = hctbhBean.getTTHoaDonCKRequest();
%>
<%
	String userId = (String) session.getAttribute("userId");
%>
<%
	String userTen = (String) session.getAttribute("userTen");
	NumberFormat formatter = new DecimalFormat("#,###,###");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.####");
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

<link media="screen" rel="stylesheet" href="../css/colorbox.css">
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {
        	$(".ndThongtinhangnhap").colorbox({width:"46%", inline:true, href:"#ndThongtinhangnhap"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Viethung - Project.");
                return false;
            });
        });
    </script>
    <LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
    
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
	
	 function saveform()
	 {	
		 if(!checkHuyCt())
		 {
			 alert("Bạn phải hủy các chứng từ từ trên xuống theo đúng quy trình");
			 return;
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value='save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 //alert('chao nhe');
		 document.forms['hctmhForm'].action.value='submit';
	     document.forms["hctmhForm"].submit();
	 }
	 
	 function checkHuyCt()
	 {
		 var sochungtuhuy = document.getElementsByName("sochungtuhuy");
		 for(i = 0; i < sochungtuhuy.length; i++)
		 {
			 if(sochungtuhuy.item(i).checked)
			 {
				 for(j = 0; j < i; j++)
				 {
					 if(sochungtuhuy.item(j).checked == false)
					 {
						 alert('Bạn phải hủy các chứng từ theo đúng thứ tự trong quy trình');
						 return false;
					 }
				 }
			 }
		 } 
		 return true;
	 }
	 
	 function HuyChungTu(pos)
	 {
		 var sochungtuhuy = document.getElementsByName("sochungtuhuy");
		 if(sochungtuhuy.item(pos).checked)
		 {
			 for(i = 0; i < parseInt(pos); i++)
			 {
				 if(sochungtuhuy.item(i).checked == false)
				 {
					 //alert('Bạn phải hủy các chứng từ theo đúng thứu tự trong quy trình');
					 sochungtuhuy.item(pos).checked = false;
					 return false;
				 }
			 } 
		 }
		 return true;
	 }
	 
	 function ReadOnly(pos)
	 {
		 var sochungtuhuy = document.getElementsByName("sochungtuhuy");
		 if(sochungtuhuy.item(pos).checked == false)
		 {
			 sochungtuhuy.item(pos).checked = true;
		 }
	 }

	 function goBack()
	 {
	  	window.history.back();
	 }
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="hctmhForm" method="post" action="../../ErpHuychungtuBhUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value='1'>

		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý cung ứng > Quản lý bán hàng > Hủy chứng từ bán hàng > Tạo mới</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen%>
					&nbsp;
				</div>
			</div>

			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="../../ErpHuychungtuBhSvl?userId=<%=userId%>"> 
					<img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px" longdesc="Quay ve" style="border-style: outset">
				</A> 
				<span id="btnSave"> <A href="javascript:saveform()"> 
				<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset">
				</A> </span>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" rows="1" readonly="readonly"
						style="width: 100% %"><%=hctbhBean.getMsg()%></textarea>
					<%
						hctbhBean.setMsg("");
					%>
				</fieldset>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Hủy chứng từ </legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD width="15%" class="plainlabel" valign="top">Loại chứng từ</TD>
								<TD colspan="2" class="plainlabel" valign="top">
									<%
										String mang[] = new String[]{"HOADON"};
										String mangten[] = new String[]{"Hóa đơn tài chính"};
									%> <select name="loaichungtu1" 	onchange="submitform()">
										<option value=""></option>
										<%
											for (int j = 0; j < mang.length; j++) {
												if (hctbhBean.getloaichungtu().trim().equals(mang[j])) {
										%>
											<option selected="selected" value="<%=mang[j]%>"><%=mangten[j]%></option>
										<%
											} else {
										%>
											<option value="<%=mang[j]%>"><%=mangten[j]%></option>
										<%
											}
											}
										%>
									</select>
									</TD>
							</TR>
							<TR>
								<TD width="15%" class="plainlabel" valign="top">Số chứng từ
								</TD>
								<TD colspan="2" class="plainlabel" valign="top"><input
									type="text" id="sochungtu" name="sochungtu"
									value="<%=hctbhBean.getSochungtu()%>" onchange="submitform()" />
								</TD>
							</TR>
							
					<TR>
                	<TD class="plainlabel" colspan="4" >Thông tin biên bản												
					<a class="ndThongtinhangnhap" href="#"  >&nbsp; 
						<img alt="Theo dõi thông tin hàng nhập" src="../images/vitriluu.png"></a>
    	 				<DIV style='display:none'>
                  		<DIV id="ndThongtinhangnhap" style='padding:0px 5px; background:#fff;'>
                  			<h2 align="center">BIÊN BẢN THU HỒI HOÁ ĐƠN  ĐÃ LẬP </h2>
                  			<h3 align="center">
                  				Số:<input type="text" id="sobienban" name="sobienban" value="<%= hctbhBean.getSobienban() %>" style="width: 80px; border-left: none; border-right: none; border-top: none" />  
                  				/BBTHHĐ                			
                  			</h3> 
                  				
                  			<h3 align="justify" style="font-weight: normal;">- Căn cứ Thông tư 39/2014/TT-BTC ngày 31/3/2014 của Bộ tài chính hướng dẫn thi hành Nghị định số 51/2010/NĐ-CP. </h3>
                  			<h3 align="justify" style="font-weight: normal;">- Căn cứ vào khoàn 7 điều 2 thông tư 26/2015/TT-BTC của Bộ tài chính hướng dẫn thuế giá trị gia tăng; quản lý thuế và hóa đơn.</h3>
                  			
                  			<h3 align="justify" style="font-weight: normal;">
                  				Hôm nay, ngày <input type="text" id="ngaybienban" class="days" name="ngaybienban" value="<%= hctbhBean.getNgaybienban() %>" style="width: 80px; border-left: none; border-right: none; border-top: none" />, đại diện hai bên gồm có:
                  			</h3>
                  			
                  			<h4 align="left"> BÊN A:&nbsp; 
                  				<input type="text" id="benA_bb" name="benA_bb" value="<%= hctbhBean.getBenA_bb() %>" style="width: 400px; border-left: none; border-right: none; border-top: none " /> 
                  			</h4>                  			
                  			
                  			<h4 align="left" style="font-weight: normal;"> Địa chỉ:&nbsp;  
                  				<input type="text" id="diachiA_bb" name="diachiA_bb" value="<%= hctbhBean.getDiachiA_bb() %>" style="width: 395px; border-left: none; border-right: none; border-top: none " />
                  			</h4>
                  			<h4 align="left" style="font-weight: normal;"> Điện thoại: &nbsp;
                  				<input type="text" id="dienthoaiA_bb" name="dienthoaiA_bb" value="<%= hctbhBean.getDienthoaiA_bb() %>" style="width: 150px; border-left: none; border-right: none; border-top: none;" />
                  				&nbsp; MST: 
                  				<input type="text" id="MstA_bb" name="MstA_bb" value="<%= hctbhBean.getMstA_bb() %>" style="width: 180px; border-left: none; border-right: none; border-top: none;" />  </h4>                  			                  			
                  			<h4 align="left" style="font-weight: normal;"> Do Ông (Bà): 
                  				<input type="text" id="OngbaA_bb" name="OngbaA_bb" value="<%= hctbhBean.getOngbaA_bb() %>" style="width: 180px; border-left: none; border-right: none; border-top: none;" /> 
                  				&nbsp;
                  			Chức vụ: <input type="text" id="chucvuA_bb" name="chucvuA_bb" value="<%= hctbhBean.getChucvuA_bb() %>" style="width: 125px; border-left: none; border-right: none; border-top: none;" />                   			
                  			</h4>
                  			
                  			<h4 align="left"> BÊN B:&nbsp; 
                  				<input type="text" id="benB_bb" name="benB_bb" value="<%= hctbhBean.getBenB_bb() %>" style="width: 400px; border-left: none; border-right: none; border-top: none " /> 
                  			</h4>                  			
                  			
                  			<h4 align="left" style="font-weight: normal;"> Địa chỉ:&nbsp;  
                  				<input type="text" id="diachiB_bb" name="diachiB_bb" value="<%= hctbhBean.getDiachiB_bb() %>" style="width: 395px; border-left: none; border-right: none; border-top: none " />
                  			</h4>
                  			<h4 align="left" style="font-weight: normal;"> Điện thoại: &nbsp;
                  				<input type="text" id="dienthoaiB_bb" name="dienthoaiB_bb" value="<%= hctbhBean.getDienthoaiB_bb() %>" style="width: 150px; border-left: none; border-right: none; border-top: none;" />
                  				&nbsp; MST: 
                  				<input type="text" id="MstB_bb" name="MstB_bb" value="<%= hctbhBean.getMstB_bb() %>" style="width: 180px; border-left: none; border-right: none; border-top: none;" />  </h4>                  			                  			
                  			<h4 align="left" style="font-weight: normal;"> Do Ông (Bà): 
                  				<input type="text" id="OngbaB_bb" name="OngbaB_bb" value="<%= hctbhBean.getOngbaB_bb() %>" style="width: 180px; border-left: none; border-right: none; border-top: none;" /> 
                  				&nbsp;
                  			Chức vụ: <input type="text" id="chucvuB_bb" name="chucvuB_bb" value="<%= hctbhBean.getChucvuB_bb() %>" style="width: 125px; border-left: none; border-right: none; border-top: none;" />                   			
                  			</h4>
                  			
                  			<h3 align="justify" style="font-weight: normal;">- Hai bên, thống nhất lập biên thu hồi (Liên 2) hóa đơn 
                  			<input type="text" id="sohoadon1_bb" name="sohoadon1_bb" value="<%= hctbhBean.getSohoadon1_bb() %>" style="width: 60px; border-left: none; border-right: none; border-top: none;" />  
                  			đã lập, có ký hiệu: <input type="text" id="kyhieu1_bb" name="kyhieu1_bb" value="<%= hctbhBean.getKyhieu1_bb() %>" style="width: 60px; border-left: none; border-right: none; border-top: none;" />  số
                  			<input type="text" id="sohoadon2_bb" name="sohoadon2_bb" value="<%= hctbhBean.getSohoadon2_bb() %>" style="width: 60px; border-left: none; border-right: none; border-top: none;" /> ngày
                  			<input type="text" id="ngayhoadon1_bb" class="days" name="ngayhoadon1_bb" value="<%= hctbhBean.getNgayhoadon1_bb() %>" style="width: 120px; border-left: none; border-right: none; border-top: none;" />
                  			để xóa bỏ theo qui định, và sẽ xuất thay thế bằng hóa đơn số 
                  			<input type="text" id="sohoadon3_bb" name="sohoadon3_bb" value="<%= hctbhBean.getSohoadon3_bb() %>" style="width: 60px; border-left: none; border-right: none; border-top: none;" />
                  			ký hiệu
                  			<input type="text" id="kyhieu2_bb" name="kyhieu2_bb" value="<%= hctbhBean.getKyhieu2_bb() %>" style="width: 60px; border-left: none; border-right: none; border-top: none;" />
                  			số
                  			<input type="text" id="sohoadon4_bb" name="sohoadon4_bb" value="<%= hctbhBean.getSohoadon4_bb() %>" style="width: 60px; border-left: none; border-right: none; border-top: none;" />
                  			ngày
                  			<input type="text" id="ngayhoadon2_bb" class="days" name="ngayhoadon2_bb" value="<%= hctbhBean.getNgayhoadon2_bb() %>" style="width: 60px; border-left: none; border-right: none; border-top: none;" />
                  			
                  			</h3>
                  			<h3 align="justify" style="font-weight: normal;">Lý do thu hồi: 
                  			<input type="text" id="Lydothuhoi_bb" name="Lydothuhoi_bb" value="<%= hctbhBean.getLydothuhoi_bb() %>" style="width: 350px; border-left: none; border-right: none; border-top: none;" /> 
                  			</h3>
                  			
                  			<h3 align="justify" style="font-weight: normal;"> Chúng tôi cam kết và hoàn toàn chịu trách nhiệm về việc thu hồi và xóa bỏ hóa đơn này. 
                  			</h3>
                  			<h3 align="justify" style="font-weight: normal;"> Biên bản này lập thành 02 bản, Bên A giữ 01 bản, Bên B giữ 01 bản. 
                  			</h3>
                  			
                  			<h3 align="right" style="font-weight: normal;"> TP.HCM, ngày 
                  			<input type="text" id="ngaybb" name="ngaybb" class="days"  value="<%= hctbhBean.getNgaybb() %>" style="width: 350px; border-left: none; border-right: none; border-top: none;" /> 
                  			</h3>
                  			             			             	
           			   </DIV> 
           			  </div>
					</TD>
                </TR>

							<TR>
								<TD width="15%" class="plainlabel" valign="top">&nbsp;</TD>
								<TD colspan="2" class="plainlabel" valign="top">&nbsp;</TD>
							</TR>

							<%--  <% if(hctbhBean.getSoHoadon().length() > 0){ %>
               	<TR>
                    <TD width="12%" class="plainlabel" valign="top">  </TD>
                    <TD colspan="2" class="plainlabel" valign="top">
                    	<input type="text" id="sohoadon" name="sohoadon" value="<%= hctbhBean.getSoHoadon() %>" readonly="readonly" /></TD>
               	</TR>
             <%} %> --%>
							<%
								if (hctbhBean.getSophieuxuatkho().length() > 0) {
							%>
							<TR>
								<TD width="12%" class="plainlabel" valign="top">Số phiếu
									xuất kho</TD>
								<TD colspan="2" class="plainlabel" valign="top"><input
									type="text" id="soxuatkho" name="soxuatkho"
									value="<%=hctbhBean.getSophieuxuatkho()%>"
									readonly="readonly" />
								</TD>
							</TR>
							<%
								}
							%>
							<%
								if (hctbhBean.getSoDondathang().length() > 0) {
							%>
							<TR>
								<TD width="12%" class="plainlabel" valign="top">Số đơn đặt
									hàng</TD>
								<TD colspan="2" class="plainlabel" valign="top"><input
									type="text" id="sodathang" name="sodathang"
									value="<%=hctbhBean.getSoDondathang()%>" readonly="readonly" />
								</TD>
							</TR>
							<%
								}
							%>
						</TABLE>
						<hr>
					</div>

					<%
						int i = 1;
						if (soctRequest != null) {
					%>

					<div align="left" style="width: 100%; float: none; clear: none;"
						class="plainlabel">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1"
							cellspacing="1">
							<TR class="tbheader">
								<TH align="center" width="5%">STT</TH>
								<TH align="center" width="15%">&nbsp;Số chứng từ</TH>
								<TH align="center" width="15%">&nbsp;Số hóa đơn</TH>
								<TH align="center" width="15%">&nbsp;Ngày chứng từ</TH>
								<TH align="center" width="15%">&nbsp;Trạng thái</TH>
								<TH align="left" width="10%">&nbsp;Loại chứng từ</TH>
								<TH align="center" width="10%">Ngày tạo</TH>
								<TH align="center" width="10%">Chọn hủy</TH>
							</TR>

							<%
								while (soctRequest.next()) {
							%>
							<TR>
								<TD align="center"><input type="text" name="stt"
									style="width: 100%; text-align: center;" value="<%=i%>"
									readonly="readonly"> <input type="hidden" name="thutu"
									style="width: 100%; text-align: center;"
									value="<%=soctRequest.getString("STT")%>"></TD>
								<TD align="center"><input type="text" name="sochungtu"
									style="width: 100%"
									value="<%=soctRequest.getString("SOCHUNGTU")%>"
									readonly="readonly"></TD>
								<TD align="center"><input type="text" name="sohoadon"
									style="width: 100%"
									value="<%=soctRequest.getString("SOHOADON")%>"
									readonly="readonly"></TD>
								<TD align="center"><input type="text" name="ngaychungtu"
									style="width: 100%; text-align: center;"
									value="<%=soctRequest.getString("ngaychungtu")%>"
									readonly="readonly"></TD>
								<TD align="center"><input type="text" name="trangthai"
									style="width: 100%"
									value="<%=soctRequest.getString("trangthai")%>"
									readonly="readonly"></TD>
								<TD align="center"><input type="text" name="loaichungtu"
									style="width: 100%"
									value="<%=soctRequest.getString("loaichungtu")%>"
									readonly="readonly"></TD>
								<TD align="center"><input type="text" name="ngaytao"
									style="width: 100%; text-align: center;"
									value="<%=soctRequest.getString("NGAYTAO")%>"
									readonly="readonly"></TD>
								<TD align="center">
									<%
										if (hctbhBean.getSochungtu().equals(
														soctRequest.getString("SOCHUNGTU"))) {
									%>
									<input type="checkbox" name="sochungtuhuy"
									value="<%=soctRequest.getString("SOCHUNGTU")%>"
									onchange="ReadOnly(<%=i - 1%>)" checked="checked"
									readonly="readonly"> <%
 	} else {
 %> <input
									type="checkbox" name="sochungtuhuy"
									value="<%=soctRequest.getString("SOCHUNGTU")%>"
									onchange="HuyChungTu(<%=i - 1%>)" disabled="disabled">
									<%
										}
									%>
								</TD>
							</TR>
							<%
								i++;
									}
								}
							%>

						</TABLE>

						<%
							if (i > 1) {
						%>
						<table cellpadding="0px" cellspacing="1px" width="100%">

							<tr class="tbheader">
								<th align="center" width="10%">Mã sản phẩm</th>
								<th align="center" width="20%">Tên sản phẩm</th>
								<th align="center" width="10%">Đơn vị</th>
								<th align="center" width="10%">Số lượng</th>
								<th align="center" width="10%">Đơn giá</th>
								<th align="center" width="8%">Chiết khấu</th>
								<th align="center" width="8%">Tiền thuế</th>
								<th align="center" width="10%">Thành tiền</th>
								<th align="center" width="10%">Scheme</th>
							</tr>

							<%
								if (tthdRequest != null) {
										while (tthdRequest.next()) {
							%>

							<tr>
								<td><input type="hidden" name="spId"
									value="<%=tthdRequest.getString("SPID")%>"
									style="width: 100%"> <input type="text" name="spMa"
									value="<%=tthdRequest.getString("MA")%>" style="width: 100%"
									readonly="readonly"></td>
								<td><input type="text" name="spTen"
									value="<%=tthdRequest.getString("TEN")%>" style="width: 100%">
								</td>
								<td><input type="text" name="donvi"
									value="<%=tthdRequest.getString("DONVITINH")%>"
									style="width: 100%; text-align: right;" readonly="readonly">
								</td>
								<td><input type="text" name="soluong"
									value="<%=formatter.format(Double
								.parseDouble(tthdRequest.getString("soluong")))%>"
									style="width: 100%; text-align: right;" readonly="readonly">

								</td>
								<td><input type="text" name="spDongia"
									value="<%=formatter2.format(Double
								.parseDouble(tthdRequest.getString("dongia")))%>"
									style="width: 100%; text-align: right;"></td>
								<td><input type="text" name="spChietkhau"
									value="<%=formatter.format(Double
								.parseDouble(tthdRequest.getString("chietkhau")))%>"
									style="width: 100%; text-align: right;" readonly="readonly">
								</td>
								<td><input type="text" name="spTienthue"
									value="<%=formatter.format(Double
								.parseDouble(tthdRequest.getString("tienVAT")))%>"
									style="width: 100%; text-align: right;"></td>
								<td><input type="text" name="thanhtien"
									value="<%=formatter2.format(Double
								.parseDouble(tthdRequest.getString("THANHTIEN")))%>"
									style="width: 100%; text-align: right;"
									onkeypress="return keypress(event); " readonly="readonly">
								</td>
								<td><input type="text" name="scheme" id="scheme"
									value="<%=tthdRequest.getString("scheme")%>"
									style="width: 100%; text-align: right;"
									onkeypress="return keypress(event); "></td>

							</tr>

							<%
								}

									}
								}
							%>

							<%-- <%
								if (ttckRequest != null) {
									while (ttckRequest.next()) {
							%>
							<TR>
								<td><input type="text" style="width: 100%;"></td>
								<td><input type="text" style="width: 100%;"></td>
								<td><input type="text" style="width: 100%;"></td>
								<td><input type="text" style="width: 100%;"></td>
								<td><input type="text" style="width: 100%;"></td>
								<td><input type="text" style="width: 100%;"></td>
								<td><input type="text" style="width: 100%;"></td>
								<td><input type="text"
									style="width: 100%; text-align: right;"
									value="<%=formatter2.format(Double.parseDouble(tthdRequest
							.getString("giatri")))%>"
									readonly="readonly"></td>
								<td><input type="text" style="width: 100%;"
									value="<%=tthdRequest.getString("scheme")%>"
									readonly="readonly"></td>
								<td></td>
							</TR>
							<%
								}
							%>

							<%
								}
							%> --%>

						</table>

					</div>
				</fieldset>
			</div>
		</div>
	</form>
</BODY>
</html>
<% 
if(hctbhBean!=null)
{
	hctbhBean.DBClose();
	session.setAttribute("hctbhBean",null);
}


%>