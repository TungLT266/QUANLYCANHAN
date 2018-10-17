<%@page import="geso.traphaco.center.beans.chitieu.imp.ChiTieuNPP"%>
<%@page import="java.util.Calendar"%>
<%@page
	import="geso.traphaco.center.beans.chitieuttchovung.imp.ChiTieuTTKhuVuc"%>
<%@page
	import="geso.traphaco.center.beans.chitieuttchovung.imp.ChiTieuTTChoVung"%>
<%@page import="java.util.Date"%>
<%@page import="javax.xml.crypto.Data"%>
<%@page import="geso.traphaco.center.beans.chitieu.imp.ChiTieu"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="geso.traphaco.center.beans.nhomkhuyenmai.INhomkhuyenmai"%>
<%@ page
	import="geso.traphaco.center.beans.nhomkhuyenmai.imp.Nhomkhuyenmai"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	//Lay doi tuong objbean
 	ChiTieu objbean=(ChiTieu)session.getAttribute("obj");
	//truyen qua doi tuong list vung

	String check1=(String)session.getAttribute("check");
	//lay resultset vung de do vao combobox

	String loaichitieu=(String)session.getAttribute("loaichitieu");
	ResultSet rs_dvkd=objbean.getRsDvdk();
	ResultSet	rs_kenh= objbean.getRsKenh();
	
	ResultSet  listnhapp= objbean.getRsNppNhomSp();
	
	NumberFormat formatter = new DecimalFormat("#,###,###.##");
	
	ResultSet rschitieunv=objbean.getRsChitieunhanvien();
	ResultSet rschitieunv2=objbean.getRsChiTieuNV2();
	ResultSet rschitieunv3=objbean.getRsChiTieuNV3();
	
	String []nhomsp=objbean.getNhomSp();
	String []nhomspid=objbean.getNhomSpId();
	ResultSet rs_nhomkenh=objbean.getRsnhomkenh();
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs-panes.css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../scripts/Numberformat.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
<script>
//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 $("ul.tabs").tabs("div.panes > div");
});
</script>
<SCRIPT language="JavaScript" type="text/javascript"> 
function submitform()
{
    document.forms["ChiTieuTTForm"].submit();
}
function keypress(e) 
{    
	var keypressed = null;
	if (window.event)
		keypressed = window.event.keyCode; //IE
	else
		keypressed = e.which; //NON-IE, Standard
	
	if (keypressed < 48 || keypressed > 57)
	{ 
		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
		{//Phím Delete và Phím Back
			return;
		}
		return false;
	}
}
function upload()
{// nhan nut cap nhat
	   if(document.forms["ChiTieuTTForm"].filename.value=="")
	   {		   
		   document.forms["ChiTieuTTForm"].dataerror.value="Chưa tìm file upload lên. Vui lòng chọn file cần upload.";
	   }else
	   {
		 document.forms["ChiTieuTTForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["ChiTieuTTForm"].submit();	
	   }
}
function save()
{
	  document.forms["ChiTieuTTForm"].dataerror.value=" ";
	 var thang=document.forms["ChiTieuTTForm"].Quy.value;
	 var nam=document.forms["ChiTieuTTForm"].nam.value;
	 var tongchitieu=document.forms["ChiTieuTTForm"].tongchitieu.value;
	 var loaichitieu=document.forms["ChiTieuTTForm"].loaichitieu.value;
	  if(nam==0)
	  {
		  document.forms["ChiTieuTTForm"].dataerror.value="Chọn năm cần đạt chỉ tiêu ";
		  return;
	  }
	  if(thang==0){
		  document.forms["ChiTieuTTForm"].dataerror.value="Chọn Quý cần đạt chỉ tiêu ";
		  return;
		  }
	  
	//kiem tra xem thang nam dat chi tieu co hop le hay khong
	   
		document.forms["ChiTieuTTForm"].action.value="capnhatForm";
		document.forms["ChiTieuTTForm"].submit();

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
		element.value=DinhDangTien(element.value);
		if(element.value== '' ||element.value=='0' )
		{
			element.value= '';
		}
	}


</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="ChiTieuTTForm" method="post" action="../../ChiTieuNewnppSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="userTen" value='<%=userTen%>'> 
		<input type="hidden" name="nkmId" value=""> 
		<input type="hidden" name="action" value="0"> 
		<input type="hidden" name="id" value='<%=objbean.getID()%>'> 
		<input type="hidden" name="tenform" value="0"> 
		<input type="hidden" name="luutam" value="<%=check1%>">

		<input type="hidden" name="loaichitieu" value="<%=loaichitieu%>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản
											lý chỉ tiêu &gt; Chỉ tiêu cho NPP &gt; Cập nhật</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%=userTen%>&nbsp;
										</TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="javascript:history.back()"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
								
										<TD width="30" align="left"><A href = "../../ChiTieuNewSvl?userId=<%=userId%>&excel=<%=objbean.getID()%>"><img src="../images/excel.gif" alt="Xuất File Excel NPP" width="20" height="20" title="Xuất File Excel NPP" border = 0></A></TD>
										<TD ><A href = "../../ChiTieuNewSvl?userId=<%=userId%>&excelSR=<%=objbean.getID()%>"><img src="../images/excel.gif" alt="Xuất File Excel NVBH" width="20" height="20" title="Xuất File Excel NVBH" border = 0></A></TD>

									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
									<textarea name="dataerror" style="width: 100%"
										readonly="readonly" rows="1"><%=objbean.getMessage()%></textarea>
								</FIELDSET>
							</TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông
										tin chỉ tiêu</LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

										<%-- <TR>
											<TD width="20%" class="plainlabel">Tháng <FONT
												class="erroralert"> </FONT></TD>
											<TD class="plainlabel"><select name="thang"
												style="width: 50px">
													<option value=0></option>
													<%
  										int k=1;
  									for(k=1;k<=12;k++){
  									  if(k==objbean.getThang()){
  									%>
													<option value=<%=k%> selected="selected">
														<%=k%></option>
													<%
 										}else{
 									%>
													<option value=<%=k%>><%=k%></option>
													<%
 										}
 									  }
 									%>
											</select></TD>
										</TR> --%>
										
										<TR>
								<TD width="20%" class="plainlabel" >Quý <FONT class="erroralert"> </FONT></TD>
								<TD class="plainlabel">
									<select name="Quy" style="width :50px" ">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=4;k++){
  									  if(k==objbean.getQuy()){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									
								</TD>
							</TR>
										
										<TR>
											<TD class="plainlabel">Năm</TD>
											<TD class="plainlabel"><select name="nam"
												style="width: 50px">
													<option value=0></option>
													<%
									  Calendar c2 = Calendar.getInstance();
  										int t=c2.get(Calendar.YEAR) +6;
  										int n;
  										for(n=2008;n<t;n++){
  										if(n==objbean.getNam()){
  									%>
													<option value=<%=n%> selected="selected">
														<%=n%></option>
													<%
 										}else{
 									%>
													<option value=<%=n%>><%=n%></option>
													<%
 										}
 									 }
 									%>
											</select></TD>
										</TR>

									
										
						
										
										
										<tr class="plainlabel">
											<td>Kênh bán hàng</td>
											<td><select name="kbhid">
													<%
                             if (rs_kenh!=null){
                            	 while (rs_kenh.next()){
                       				%>
													<% if(rs_kenh.getString("pk_seq").equals(objbean.getKenhId())){ %>
													<option value="<%=rs_kenh.getString("pk_seq") %>"
														selected="selected">
														<%=rs_kenh.getString("ten") %></option>
													<%}else{ %>
													<option value="<%=rs_kenh.getString("pk_seq") %>">
														<%=rs_kenh.getString("ten") %></option>
													<%} %>
													<%     		
                            	 }
                             }
                             %>
											</select></td>
										</tr>
										<TR>
											<TD class="plainlabel">Số ngày làm việc</TD>
											<TD class="plainlabel"><input
												onkeypress="return keypress(event);" type="text"
												name="songaylamviec" value="<%=objbean.getSoNgayLamViec()%>">

											</TD>
										</TR>
										<TR>
											<TD class="plainlabel">Diễn giải</TD>
											<TD class="plainlabel"><input type="text"
												name="diengiai" value="<%=objbean.getDienGiai()%>">

											</TD>
										</TR>
										
										
										<!-- <tr class="plainlabel">

											<td colspan="2"><INPUT type="file" name="filename"
												size="40" value=''></td>
										</tr> -->
										<!-- <tr class="plainlabel">
											<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; <a
												class="button2" href="javascript:upload()"> <img
													style="top: -4px;" src="../images/button.png" alt="">
													Cập nhật
											</a>
											</td>
										</tr> -->
									</TABLE>

									<ul class="tabs">
										<li><a href="#">Tháng thứ nhất</a></li>
										<li><a href="#">Tháng thứ hai</a></li> 
										<li><a href="#">Tháng thứ ba</a></li> 
									</ul>
									<div class="panes">
										<div>
										<TABLE  width="100%" border="1" cellspacing="1px" cellpadding="3px">
												<TR class="tbheader">
													<TH width="20%">Tên nhân viên</TH>
													<TH  width="5%" >chức vụ</TH>
													<TH width="7%">Doanh số mua vào</TH>
													<TH width="7%">doanh số bán ra</TH>
													<TH width="10%">Số lần viếng thăm có PS đơn hàng</TH>
													<TH width="10%">Số cửa hàng mua hàng</TH>
													<TH width="10%">Số đơn hàng trên ngày</TH>
													<TH width="10%">Giá trị tối thiểu của đơn hàng</TH>
													<TH width="10%">SKU/đơn hàng</TH>
													<TH width="10%">% Giao hàng thành công</TH>
												<%
									int col=5;
									if(nhomsp!=null)
									{
										for(int i=0;i<nhomsp.length;i++)
										{
											col++;
											if(nhomsp[i]!=null){
											%>
											<TH width="10%"><%=nhomsp[i] %></TH>
											<%
										}}
										
									}
									%>
												</TR>
												<%
								int m=0;
									
								if(rschitieunv != null)
								{
								while (rschitieunv.next())
								{
									 %>								
												<tr>
													<TD>
														<input name="tennv_1" style="text-align: left;width: 100%;border: 0 " type="text" value="<%=rschitieunv.getString("ten") %>"/>
														<input name="manv_1" style="text-align: left;width: 100%;border: 0 " type="hidden" value="<%=rschitieunv.getString("nhanvien_fk") %>"/>
													
													</TD>
													<TD>
														<input name="chucvu_1" style="text-align: left;width: 100%;border: 0 " type="text" value="<%=rschitieunv.getString("chucvu") %>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)" name="ctmuavao_1" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv.getInt("chitieubanraSl") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)" name="ctbanra_1" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv.getInt("CHITIEUBANRA") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctSOLANVIENGTHAM_1" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv.getInt("SOLANVIENGTHAM") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctSOCUAHANGMUAHANG_1" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv.getInt("SOCUAHANGMUAHANG") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctSODONHANGTRONGNGAY_1" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv.getInt("SODONHANGTRONGNGAY") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctGIATRIDONHANGTOITHIEU_1" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv.getInt("GIATRIDONHANGTOITHIEU") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctSKU_1" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv.getInt("SKU") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctGIAOHANGTHANHCONG_1" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv.getInt("GIAOHANGTHANHCONG") )%>"/>
													</TD>
												
													
										<%
									if(nhomspid!=null){
										for(int i=0;i<nhomspid.length;i++)
										{
											if(nhomspid[i]!=null)
											{
											%>
											<TD width="10%">   
													<input    onkeyup="Dinhdang(this)" name="ctNppNspId1_<%=nhomspid[i] %>" style="text-align: right;width: 100%;border: 0 " type ="text"    value=" <%=formatter.format(rschitieunv.getInt("CT"+nhomspid[i]))%>"      >
											   </TD>
											<%
										}
									 }
										
									}
									%>
												</tr>
												<%
									m++;
								}
								}
							%>
											</TABLE>
										</div>
										<div>
											
													<TABLE  width="100%" border="1" cellspacing="1px" cellpadding="3px">
												<TR class="tbheader">
													<TH width="20%">Tên nhân viên</TH>
													<TH  width="5%" >chức vụ</TH>
													<TH width="7%">Doanh số mua vào</TH>
													<TH width="7%">doanh số bán ra</TH>
													<TH width="10%">Số lần viếng thăm có PS đơn hàng</TH>
													<TH width="10%">Số cửa hàng mua hàng</TH>
													<TH width="10%">Số đơn hàng trên ngày</TH>
													<TH width="10%">Giá trị tối thiểu của đơn hàng</TH>
													<TH width="10%">SKU/đơn hàng</TH>
													<TH width="10%">% Giao hàng thành công</TH>
												<%
									 col=5;
									if(nhomsp!=null)
									{
										for(int i=0;i<nhomsp.length;i++)
										{
											col++;
											if(nhomsp[i]!=null){
											%>
											<TH width="10%"><%=nhomsp[i] %></TH>
											<%
										}}
										
									}
									%>
												</TR>
												<%
								 m=0;
									
								if(rschitieunv2 != null)
								{
									
								while (rschitieunv2.next())
								{
									 %>								
												<tr>
													<TD>
														<input name="tennv_2" style="text-align: left;width: 100%;border: 0 " type="text" value="<%=rschitieunv2.getString("ten") %>"/>
														<input name="manv_2" style="text-align: left;width: 100%;border: 0 " type="hidden" value="<%=rschitieunv2.getString("nhanvien_fk") %>"/>
												
													</TD>
													<TD>
														<input name="chucvu_2" style="text-align: left;width: 100%;border: 0 " type="text" value="<%=rschitieunv2.getString("chucvu") %>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)" name="ctmuavao_2" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv2.getInt("CHITIEUbanrasl") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)" name="ctbanra_2" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv2.getInt("CHITIEUBANRA") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctSOLANVIENGTHAM_2" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv2.getInt("SOLANVIENGTHAM") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctSOCUAHANGMUAHANG_2" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv2.getInt("SOCUAHANGMUAHANG") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctSODONHANGTRONGNGAY_2" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv2.getInt("SODONHANGTRONGNGAY") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctGIATRIDONHANGTOITHIEU_2" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv2.getInt("GIATRIDONHANGTOITHIEU") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctSKU_2" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv2.getInt("SKU") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctGIAOHANGTHANHCONG_2" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv2.getInt("GIAOHANGTHANHCONG") )%>"/>
													</TD>
												
													
										<%
									if(nhomspid!=null){
										for(int i=0;i<nhomspid.length;i++)
										{
											if(nhomspid[i]!=null)
											{
											%>
											<TD width="10%">   
													<input    onkeyup="Dinhdang(this)" name="ctNppNspId2_<%=nhomspid[i] %>" style="text-align: right;width: 100%;border: 0 " type ="text"    value=" <%=formatter.format(rschitieunv2.getInt("CT"+nhomspid[i]))%>"      >
											   </TD>
											<%
										}
									 }
										
									}
									%>
												</tr>
												<%
									m++;
								}
								}
							%>
											</TABLE>
											
										</div>
										
										
									<div>
											
													<TABLE  width="100%" border="1" cellspacing="1px" cellpadding="3px">
												<TR class="tbheader">
													<TH width="20%">Tên nhân viên</TH>
													<TH  width="5%" >chức vụ</TH>
													<TH width="7%">Doanh số mua vào</TH>
													<TH width="7%">doanh số bán ra</TH>
													<TH width="10%">Số lần viếng thăm có PS đơn hàng</TH>
													<TH width="10%">Số cửa hàng mua hàng</TH>
													<TH width="10%">Số đơn hàng trên ngày</TH>
													<TH width="10%">Giá trị tối thiểu của đơn hàng</TH>
													<TH width="10%">SKU/đơn hàng</TH>
													<TH width="10%">% Giao hàng thành công</TH>
												<%
									 col=5;
									if(nhomsp!=null)
									{
										for(int i=0;i<nhomsp.length;i++)
										{
											col++;
											if(nhomsp[i]!=null){
											%>
											<TH width="10%"><%=nhomsp[i] %></TH>
											<%
										}}
										
									}
									%>
												</TR>
												<%
								 m=0;
									
								if(rschitieunv3 != null)
								{
								while (rschitieunv3.next())
								{
									 %>								
												<tr>
													<TD>
														<input name="tennv_3" style="text-align: left;width: 100%;border: 0 " type="text" value="<%=rschitieunv3.getString("ten") %>"/>
														<input name="manv_3" style="text-align: left;width: 100%;border: 0 " type="hidden" value="<%=rschitieunv3.getString("nhanvien_fk") %>"/>
												
													</TD>
													<TD>
														<input name="chucvu_3" style="text-align: left;width: 100%;border: 0 " type="text" value="<%=rschitieunv3.getString("chucvu") %>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)" name="ctmuavao_3" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv3.getInt("CHITIEUbanrasl") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)" name="ctbanra_3" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv3.getInt("CHITIEUBANRA") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctSOLANVIENGTHAM_3" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv3.getInt("SOLANVIENGTHAM") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctSOCUAHANGMUAHANG_3" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv3.getInt("SOCUAHANGMUAHANG") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctSODONHANGTRONGNGAY_3" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv3.getInt("SODONHANGTRONGNGAY") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctGIATRIDONHANGTOITHIEU_3" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv3.getInt("GIATRIDONHANGTOITHIEU") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctSKU_3" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv3.getInt("SKU") )%>"/>
													</TD>
													<TD>
														<input onkeyup="Dinhdang(this)"  name="ctGIAOHANGTHANHCONG_3" style="text-align: right;width: 100%;border: 0 " type="text" value="<%=formatter.format(rschitieunv3.getInt("GIAOHANGTHANHCONG") )%>"/>
													</TD>
												
													
										<%
									if(nhomspid!=null){
										for(int i=0;i<nhomspid.length;i++)
										{
											if(nhomspid[i]!=null)
											{
											%>
											<TD width="10%">   
													<input    onkeyup="Dinhdang(this)" name="ctNppNspId3_<%=nhomspid[i] %>" style="text-align: right;width: 100%;border: 0 " type ="text"    value=" <%=formatter.format(rschitieunv3.getInt("CT"+nhomspid[i]))%>"      >
											   </TD>
											<%
										}
									 }
										
									}
									%>
												</tr>
												<%
									m++;
								}
								}
							%>
											</TABLE>
											
										</div>	
										
										
									</div>
								</FIELDSET>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</form>
</BODY>
</HTML>