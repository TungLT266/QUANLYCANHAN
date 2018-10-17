<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page
import="geso.traphaco.center.beans.thongtinsanpham.IThongtinsanpham"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>

<%
String userId = (String) session.getAttribute("userId");
String userTen = (String) session.getAttribute("userTen");
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if (!util.check(userId, userTen, sum))
{
	response.sendRedirect("/TraphacoHYERP/index.jsp");
} else

%>

<%
IThongtinsanpham spBean = (IThongtinsanpham) session.getAttribute("spBean");
%>
<%
NumberFormat formatter = new DecimalFormat("#,###,###");
	ResultSet dvdl = (ResultSet) spBean.getDvdl();
	ResultSet dvkd = (ResultSet) spBean.getDvkd();
	ResultSet nh = (ResultSet) spBean.getNh();
	ResultSet cl = (ResultSet) spBean.getCl();
	ResultSet nsp = (ResultSet) spBean.getNsp();
	ResultSet nganhhang = (ResultSet) spBean.getRsNganhHang();

	ResultSet splist = (ResultSet) spBean.getSanphamRs();

	String dvdlId = (String) spBean.getDvdlId();
	String dvkdId = (String) spBean.getDvkdId();
	String nhId = (String) spBean.getNhId();
	String clId = (String) spBean.getClId();
	Hashtable nspIds = spBean.getNspIds();
	Hashtable<Integer, String> spIds = spBean.getSpIds();

	ResultSet rspacksize = (ResultSet) spBean.getPacksizeRs();

	ResultSet nhomhangRs = (ResultSet) spBean.getNhomHangRs();
	ResultSet loaispRs = (ResultSet) spBean.getLoaispRs();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Bibica - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<SCRIPT language="javascript" type="text/javascript">
function submitform()
{	document.spForm.action.value='abc';
document.forms["spForm"].submit();
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

function saveform()
{
 ///var type = document.getElementById("type").value;
 var kl = document.spForm.kl.value;
 var tt = document.spForm.tt.value;
 var bgblc = document.spForm.giablc.value;
 var sl1 = document.getElementsByName("sl1");
 var sl2 = document.getElementsByName("sl2");
 var dvdl1 = document.getElementsByName("dvdl1");
 var dvdl2 = document.getElementsByName("dvdl2");
 var kt = 0;
 for(var i = 0; i < sl1.length; i++)
 {
	if( sl1.item(i).value == '' || sl2.item(i).value == '' || dvdl1.item(i).value == '' || dvdl2.item(i).value == '')
		kt = 1;
	else
	{
		kt = 0;
		break;
	}
 }
	if(kt == 1)
	{
		alert('Vui long nhap quy cach');
		return;
	}
 if(isNaN(kl) && kl.length > 0)
	 alert("khoi luong phai nhap so");
 //alert(sl1+ ' '+sl2+' '+dvdl1+' '+dvdl2 );
 if(sl1 == '' || sl2 == 0 || dvdl1 == '' || dvdl2 == '')
	 {
	 	alert('Ban phai chon quy cach cho san pham');
		return;
	 }
//  if(type == 1)
//  {
// 	if(checkBundle() == false)
// 	{
// 		alert('Ban phai chon san pham cho Bundle');
// 		return;
// 	}
//  }
//  if( bgblc.length > 0 && isNaN(bgblc))
// 	 alert("Giá bán lẻ chuẩn phải là số");
 document.spForm.action.value='save';
 document.forms["spForm"].submit();
}

function checkBundle()
{
 var spIds = document.getElementsByName("spIds");
 if(spIds != null)
 {
	 for(i = 0; i < spIds.length; i++)
		 if(spIds.item(i).checked)
			 return true;
 }
 return false;
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
function chonSp(mm)
{
 var spIds = document.getElementById("spIds" + mm);
 var spSoluong = document.getElementById("spSoLuong" + mm);
 if(spIds != null)
 {
	 if(spIds.checked)
	 {
		 if(spSoluong.value == '')
		 {
			 spIds.checked = false;
			 alert('Ban phai nhap so luong san pham nay');
			 return;
		 }
		 else
		 {
			 spIds.value = spIds.value + '-' + spSoluong.value;
		 }
	 }
 }
 return false;
}

function updateUoM()
{
 dvdlId = document.getElementById("dvdlId").value;

 for(var i=0;i<= document.getElementById("0").length-1;i=i+1)
 {
	var dvdl1=document.getElementById("0").options[i].value;

	if(dvdl1==dvdlId)
	{
		document.getElementById("0").selectedIndex=i;
		break;
	}
 }
}

function sellectAll_NhId()
{
 var chkAll_Lct = document.getElementById("chkAll_NhId");
 var loaiCtIds = document.getElementsByName("nhanhangIds");

 if(chkAll_Lct.checked)
 {
	 for(var i = 0; i < loaiCtIds.length; i++)
	 {
		 loaiCtIds.item(i).checked = true;
	 }
 }
 else
 {
	 for(var i = 0; i < loaiCtIds.length; i++)
	 {
		 loaiCtIds.item(i).checked = false;
	 }
 }
}


function sellectAll_ClId()
{
 var chkAll_Lct = document.getElementById("chkAll_ClId");
 var loaiCtIds = document.getElementsByName("chungloaiIds");

 if(chkAll_Lct.checked)
 {
	 for(var i = 0; i < loaiCtIds.length; i++)
	 {
		 loaiCtIds.item(i).checked = true;
	 }
 }
 else
 {
	 for(var i = 0; i < loaiCtIds.length; i++)
	 {
		 loaiCtIds.item(i).checked = false;
	 }
 }
}


</SCRIPT>
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

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="spForm" method="post"
	action="../../ThongtinsanphamUpdateSvl">
	<input type="hidden" name="userId" value='<%=spBean.getUserId()%>'>
	<input type="hidden" name="action" value="1">

	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
		height="100%">
		<TR>
			<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
					<TR>
						<TD align="left" class="tbnavigation">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr height="22">
									<TD align="left" colspan="2" class="tbnavigation">
										&nbsp;Dữ liệu nền > Sản phẩm > Thông tin sản phẩm > Tạo mới
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
										href="../../ThongtinsanphamSvl?userId=<%=userId%>"><img
											src="../images/Back30.png" alt="Quay ve" title="Quay ve"
											border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
									<TD width="2" align="left"></TD>
									<TD width="30" align="left"><A
										href="javascript: saveform()"><IMG
											src="../images/Save30.png" title="Luu lai" alt="Luu lai"
											border="1" style="border-style: outset"></A></TD>
									<TD>&nbsp;</TD>
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

								<textarea name="dataerror" style="width: 100%" readonly
									rows="1"><%=spBean.getMessage()%></textarea>
								<%
									spBean.setMessage("");
								%>
							</FIELDSET>
						</TD>
					</tr>

					<TR>
						<TD>

							<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
								<TR>
									<TD>
										<FIELDSET>
											<LEGEND class="legendtitle" style="color: black">Thông
												tin sản phẩm </LEGEND>

											<TABLE border="0" width="100%" cellpadding="4"
												cellspacing="0">
												<TR>
													<TD class="plainlabel">Mã sản phẩm cũ</TD>
													<TD class="plainlabel"><INPUT type="text" name="maspcu" style="width: 200px" value='<%=spBean.getMaspcu()%>'></TD>
													<TD class="plainlabel">Tên sản phẩm <FONT class="erroralert">*</FONT></TD>
													<TD class="plainlabel"><input name="tensp" type="text" style="width: 200px" value='<%=spBean.getTen()%>'></TD>
													<TD class="plainlabel">Khối lượng <FONT class="erroralert"> *</FONT></TD>
													<TD class="plainlabel"><INPUT type="text" name="kl" style="width: 170px; text-align: right;" value='<%=spBean.getKL()%>' onkeypress="return keypress(event);"> &nbsp; <b><i>gram</i></b>
													</TD>
												</TR>


												<TR style="display: none">
													<TD class="plainlabel">Mã chuẩn <FONT
														class="erroralert">*</FONT></TD>
													<TD colspan="3" class="plainlabel"><input
														name="machuan" type="text" style="width: 200px"
														value='<%=spBean.getMachuan()%>'></TD>
												</TR>
												<TR style="display: none">
													<TD class="plainlabel">Tên chuẩn <FONT
														class="erroralert">*</FONT></TD>
													<TD colspan="3" class="plainlabel"><input
														name="tenchuan" type="text" style="width: 200px"
														value='<%=spBean.getTenchuan()%>'></TD>
												</TR>

												<TR>
													
													<TD class="plainlabel">Thể tích <FONT class="erroralert"> *</FONT></TD>
													<TD class="plainlabel"><INPUT type="text" name="tt" style="width: 170px; text-align: right;" value='<%=spBean.getTT()%>' onkeypress="return keypress(event);"> &nbsp; <b><i>m<sup>3</sup></i></b>
													</TD>
													<TD class="plainlabel">Đơn vị đo lường OTC <FONT class="erroralert"> *</FONT></TD>
													<TD class="plainlabel"><select name="dvdlId" id="dvdlId" onChange="updateUoM();">
														<option value=""></option>
															<%
																try {
																		dvdl.beforeFirst();
																		while (dvdl.next())
																		{
																			if (dvdlId.equals(dvdl.getString("dvdlId")))
																			{
															%>
															<option value='<%=dvdl.getString("dvdlId")%>' selected><%=dvdl.getString("dvdlTen")%></option>
															<%
																} else {
															%>
															<option value='<%=dvdl.getString("dvdlId")%>'><%=dvdl.getString("dvdlTen")%></option>
															<%
																} }
															}
															catch (Exception e){ e.printStackTrace(); }
															%>

													</select>

												</TD>
													<TD class="plainlabel">Hạn sử dụng<FONT class="erroralert"> *</FONT></TD>
													<TD class="plainlabel">
														<INPUT type="text" name="hansudung" style="width: 170px; text-align: right;" value='<%=spBean.getHansudung()%>' onkeypress="return keypress(event);"> &nbsp;<i>Tháng</i>
													</TD>
												</TR>
												<TR>
													
												</TR>

												<tr>
													<TD class="plainlabel"> Đơn vị đo lường ETC  <FONT class="erroralert"> *</FONT></TD>
													<TD class="plainlabel">
														<select name="dvdlETCId" id="dvdlETCId">
															<option value=""></option>
															<%
																try {
																	 dvdl.beforeFirst();
																	while (dvdl.next())
																	{
																		if (spBean.getDvdlETCId().equals(dvdl.getString("dvdlId")))
																		{
															%>
															<option value='<%=dvdl.getString("dvdlId")%>' selected><%=dvdl.getString("dvdlTen")%></option>
															<%
																} else {
															%>
															<option value='<%=dvdl.getString("dvdlId")%>'><%=dvdl.getString("dvdlTen")%></option>
															<%
																} }
															}
															catch (Exception e){ e.printStackTrace(); }
															%>
													</select>
													</TD>
													<TD class="plainlabel">Đơn vị kinh doanh<FONT class="erroralert">*</FONT></TD>
													<TD class="plainlabel">
														<select name="dvkdId" onChange="submitform();">
															<option value=""></option>
															<%
																try
																	{
																	while (dvkd.next())
																	{
																		if (dvkdId.equals(dvkd.getString("dvkdId")))
																		{
															%>
															<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkdTen")%></option>
															<%
																} else
																																	{
															%>
															<option value='<%=dvkd.getString("dvkdId")%>'><%=dvkd.getString("dvkdTen")%></option>
															<%
																}
																}
																dvkd.close();
																} catch (java.sql.SQLException e){}
															%>
													</select>
													</TD>
													<TD class="plainlabel">Ngành hàng <FONT class="erroralert">*</FONT></TD>
													<TD class="plainlabel">
														
														<select name="nganhhangid" onChange="submitform();">
														
														<option value=""></option>
														<%
														try
														{
															while (nganhhang.next())
															{
																if (spBean.getNganhhangid().equals(nganhhang.getString("pk_seq")))
																{
																%>
																<option value='<%=nganhhang.getString("pk_seq")%>' selected><%=nganhhang.getString("ten")%></option>
																<%} else{%>
																<option value='<%=nganhhang.getString("pk_seq")%>'><%=nganhhang.getString("ten")%></option>
																<%}
															}
															nganhhang.close();
														} catch (java.sql.SQLException e)
														{
														}%>

														</select>
														</TD>
														
														
														
												</tr>


												<TR>
														
												</TR>


												<TR>
												
												<TD class="plainlabel">Nhãn hàng
													</TD>
													<TD class="plainlabel">
														
														 <select name="nhId" onChange="submitform();">
																<option value=""></option>
															<%try{
															while (nh.next())
															{
																if (nhId.equals(nh.getString("nhId")))
																{
															%>
															<option value='<%=nh.getString("nhId")%>' selected><%=nh.getString("nhTen")%></option>
															<%
															} else
																{
															%>
															<option value='<%=nh.getString("nhId")%>'><%=nh.getString("nhTen")%></option>
															<%
															}

															}
															} catch (java.sql.SQLException e)
															{
																e.printStackTrace();
															}
																%>

														</select>
														</TD>
													<TD class="plainlabel">Chủng loại
														</TD>
															<TD  class="plainlabel">
															 <select name="clId"> 
																<option value=""></option>
																	<%
																	try
																	{
																	while (cl.next())
																	{
																	if (clId.equals(cl.getString("clId")))
																	{
																	%>
																	<option value='<%=cl.getString("clId")%>' selected><%=cl.getString("clTen")%></option>
																	<%
																	} else
																	{
																	%>
																	<option value='<%=cl.getString("clId")%>'><%=cl.getString("clTen")%></option>
																	<%
																	}
																	}
																	} catch (java.sql.SQLException e)
																	{
																	} 
																	
																	%>


														</select>
													</TD>
												
													
													<TD class="plainlabel">Dạng trình bày/bào chế </TD>
													<TD class="plainlabel"><INPUT type="text" name="dangtrinhbay" style="width: 200px; text-align: right;" value='<%=spBean.getDangtrinhbay()%>'> &nbsp;
													</TD>
												</TR>
												<tr> 
													<TD class="plainlabel">Quy cách đóng gói </TD>
													<TD class="plainlabel"><input name="quycachdonggoi" type="text" style="width: 200px" value='<%=spBean.getQuycachdonggoi()%>'></TD>
													<TD class="plainlabel">Tiêu chuẩn chất lượng </TD>
													<TD class="plainlabel"><INPUT type="text" name="tieuchuanchatluong" style="width: 200px; text-align: right;" value='<%=spBean.getTieuchuanchatluong()%>' > &nbsp; 
													<TD class="plainlabel">Nguồn gốc nguyên liệu </TD>
													<TD class="plainlabel">
													<input name="nguongocnguyenlieu" type="text" style="width: 200px" value='<%=spBean.getNguongocnguyenlieu()%>'></TD>
													
													
												</tr>
												<tr> 
														<TD class="plainlabel">Tiêu chuẩn NSX </TD>
													<TD class="plainlabel"><INPUT type="text" name="tieuchuannsx" style="width: 200px; text-align: right;" value='<%=spBean.getTieuchuannsx()%>'> &nbsp; 
													<TD class="plainlabel">Nước SX </TD>
													<TD class="plainlabel"><input name="nuocsx" type="text" style="width: 200px" value='<%=spBean.getNuocsx()%>'></TD>
													<TD class="plainlabel"> Giá bán có thuế  </TD>
													<TD class="plainlabel"><INPUT type="text" name="giabancothue" style="width: 200px; text-align: right;" value='<%=spBean.getGiabancothue()%>'onkeypress="return keypress(event);" > &nbsp;
													</TD>
												</tr>
												<tr> 
														<TD  class="plainlabel">Tên xuất hóa đơn</TD>
											  	<TD  class="plainlabel">
													<a href="" id="noidungtenxuathoadon" rel="ndTenxuathoadon">
	           	 									&nbsp; <img alt="Tên xuất hóa đơn" src="../images/vitriluu.png"></a>
	           	 		
                          							<DIV id="ndTenxuathoadon" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             		background-color: white; width: 350px; min-height:150px; overflow:auto; padding: 4px;">
				                    					<table width="100%" align="center">
									                        <tr>
									                            <th width="300px">Tên xuất hóa đơn</th>
									                        </tr>
							                            	
							                            	<%
							                            		String[] tenxuathoadonArr = spBean.getTenxuathoadonArr();
							                            		int sodong = 0;
							                            		if(tenxuathoadonArr != null)
							                            		{
							                            			for(int i = 0; i < tenxuathoadonArr.length; i++)
							                            			{
							                            				%>
							                            				<Tr>
										                            		<td><input type="text" name="tenxuathoadon" value="<%= tenxuathoadonArr[i] %>" style="width: 100%" /></td>
										                            	</Tr>
							                            			<% sodong++; }
							                            		}
							                            		
							                            		while(sodong < 5)
							                            		{
							                            			%>
							                            			
							                            			<Tr>
									                            		<td><input type="text" name="tenxuathoadon" value="" style="width: 100%" /></td>
									                            	</Tr>
							                            			
							                            		<% sodong++; }
							                            	%>
						                            	
								                    	</table>
								                     	<div align="left">
									                     	<label style="color:red" ></label>
									                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                     	<a href="javascript:dropdowncontent.hidediv('ndTenxuathoadon')" >Hoàn tất</a>
								                     </div>
							            		</DIV>   
											  	</TD>
											  	<TD  class="plainlabel">Nguồn gốc nhà nhập khẩu</TD>
											  	<TD  class="plainlabel">
													<a href="" id="noidungnguongocnhanhapkhau" rel="ndNguongocnhanhapkhau">
	           	 									&nbsp; <img alt="Tên xuất hóa đơn" src="../images/vitriluu.png"></a>
	           	 		
                          							<DIV id="ndNguongocnhanhapkhau" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             		background-color: white; width: 350px; min-height:150px; overflow:auto; padding: 4px;">
				                    					<table width="100%" align="center">
									                        <tr>
									                            <th width="300px">Nguồn gốc nhà nhập khẩu</th>
									                        </tr>
							                            	
							                            	<%
							                            		String[] nguongocArr = spBean.getNguongocnhapkhauArr();
							                            		sodong = 0;
							                            		if(nguongocArr != null)
							                            		{
							                            			for(int i = 0; i < nguongocArr.length; i++)
							                            			{
							                            				%>
							                            				<Tr>
										                            		<td><input type="text" name="nguongocnhanhapkhau" value="<%= nguongocArr[i] %>" style="width: 100%" /></td>
										                            	</Tr>
							                            			<% sodong++; }
							                            		}
							                            		
							                            		while(sodong < 5)
							                            		{
							                            			%>
							                            			
							                            			<Tr>
									                            		<td><input type="text" name="nguongocnhanhapkhau" value="" style="width: 100%" /></td>
									                            	</Tr>
							                            			
							                            		<% sodong++; }
							                            	%>
						                            	
								                    	</table>
								                     	<div align="left">
									                     	<label style="color:red" ></label>
									                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                     	<a href="javascript:dropdowncontent.hidediv('ndNguongocnhanhapkhau')" >Hoàn tất</a>
								                     </div>
							            		</DIV>   
											  	</TD>
												<TD class="plainlabel">Tên viết tắt </TD>
													<TD class="plainlabel" colspan="4"> <input name="tenviettat" type="text" style="width: 200px" value='<%=spBean.getTenviettat()%>'></TD>	
												</tr>
												<tr> 
														<TD class="plainlabel">Giá KK/KKL theo DVT nhỏ nhất </TD>
													<TD class="plainlabel" > <input name="giakk" type="text" style="width: 200px" value='<%=spBean.getGiakktheodvt()%>' onkeypress="return keypress(event);"></TD>
													
													<TD class="plainlabel">Stt theo TT40</TD>
													<TD class="plainlabel"> <input name="stttheott40" type="text" style="width: 200px" value='<%=spBean.getStttheoTT40()%>'></TD>
													
													<TD class="plainlabel">Nhóm sản phẩm HT OTC </TD>
													<TD class="plainlabel"> <input name="nhomsphtotc" type="text" style="width: 200px" value='<%=spBean.getNhomsanphamHtotc()%>'></TD> 
													
												</tr>
												<tr>
												
												</tr>
												<tr>
													
													</tr>
												<TR>
												

												<TD class="plainlabel" >Sản phẩm mới &nbsp; <%
														if (spBean.getSpMoi().equals("1"))
														{
															 %> <input name="spmoi" type="checkbox" value="1" checked> <%
														} else
														{
															%> <input name="spmoi" type="checkbox" value="0">
														<%
															}
														%>
													</TD>
													<TD class="plainlabel" >Sản phẩm chủ lực &nbsp; <%
														if (spBean.getSpChuLuc().equals("1"))
														{
															 %> <input name="spchuluc" type="checkbox" value="1" checked> <%
														} else
														{
															%> <input name="spchuluc" type="checkbox" value="0">
														<%
															}
														%>
													</TD>
													<TD class="plainlabel" colspan="2">
														<i>Hoạt động </i>&nbsp; <%
														if (spBean.getTrangthai().equals("1"))
														{
															 %> <input name="trangthai" type="checkbox" value="1" checked> <%
														} else
														{
															%> <input name="trangthai" type="checkbox" value="0">
														<%
															}
														%>
														
														&nbsp;&nbsp;&nbsp;&nbsp;
														<i>Hoạt động ( đại lý ) </i>&nbsp; <%
														if (spBean.getTrangthaiDaily().equals("1"))
														{
															 %> <input name="trangthaiDL" type="checkbox" value="1" checked> <%
														} else
														{
															%> <input name="trangthaiDL" type="checkbox" value="0">
														<%
															}
														%>
													</TD>
													<td class="plainlabel">Thông tin hàng nhập
													</td>
													<td class="plainlabel">
													
													<a class="ndThongtinhangnhap" href="#"  >
	           	 									&nbsp; <img alt="Theo dõi thông tin hàng nhập" src="../images/vitriluu.png"></a>
	           	 									<div style='display:none'>
                          							<DIV id="ndThongtinhangnhap" style='padding:0px 5px; background:#fff;'>
                          							<h4 align="left">Bảng chi tiết theo dõi thông tin sản phẩm</h4>
				                    					<table width="100%" align="center">
							                            	<tr>
							                            		<td width=200px><font size=2>Số Visa</font></td>
							                            		<td colspan = 2><INPUT type="text" name="visa" style="width: 200px" value='<%=spBean.getVisa() %>'>&nbsp;</td>
							                            	</tr>
							                            	<tr>
							                            		<td width=200px><font size=2>Ngày cấp</font></td>
							                            		<td colspan = 2>
																<TABLE cellpadding="0" cellspacing="0">
																<TR>
																<TD>
																	<input class="days" type="text" name="ngaycap" value='<%=spBean.getNgaycap() %>'  size="20">
																</TD>
																															
																</tr>
															
																</TABLE>		
																</td>
							                            	</tr>
							                            	<tr>
							                            			
																	<TD > <font size=2> Hết hạn visa </font> </TD>
																		<TD > <input class ="days" name="hethanvisa" type="text" style="width: 200px" value='<%=spBean.getHethanvisa()%>'></TD>																	
																
							                            	</tr>
							                            	<tr>
							                            		<td width=200px><font size=2>KKG</font></td>
							                            		<td colspan = 2><INPUT type="text" name="kkg" style="width: 200px" value='<%=spBean.getKkg() %>'>&nbsp;</td>
							                            	</tr>
							                            	<tr>
							                            		<td width=200px><font size=2>NSX</font></td>
							                            		<td colspan = 2><INPUT type="text" name="nsx" style="width: 200px" value='<%=spBean.getNsx() %>'></td>
							                            	</tr>
							                            	<tr>
							                            		<td width=200px><font size=2>NKK</font></td>
							                            		<td colspan = 2><INPUT type="text" name="nkk" style="width: 200px" value='<%=spBean.getNkk() %>'></td>
							                            	</tr>
							                            	<tr>
							                            		<td width=200px><font size=2>NXB</font></td>
							                            		<td colspan = 2><INPUT type="text" name="nxb" style="width: 200px" value='<%=spBean.getNxb() %>'></td>
							                            	</tr>
							                            	<tr>
							                            	<td colspan = 3>
							                            	<table width="100%" align="center">
							                            	<tr>
							                            		<td width="350px" align="center"><font size=2>Ngày</font></td>
							                            		<td width="225px" align="center"><font size=2>Thông tin</font></td>
							                            		<td width="225px" align="center"><font size=2>Ghi chú</font></td>
							                            	</tr>
							                            	</table>
							                            	<div  style="width: 100%; max-height: 300px; overflow: auto">
							                            	<table align="left" cellpadding="2px" cellspacing="2px">
							                            	<%
							                            		String[] thongtinArr = spBean.getThongtinArr();
							                            		String[] ngayArr = spBean.getNgayArr();
							                            		String[] ghichuArr = spBean.getGhichuArr();
							                            		sodong = 0;
							                            		if(ngayArr != null)
							                            		{
							                            			for(int i = 0; i < ngayArr.length; i++)
							                            			{
							                            				%>
							                            				<Tr>
										                            		<td >
											                            		<TABLE cellpadding="0" cellspacing="0">
																				<TR><TD >
																					<input class="days" type="text" name="ngay" value='<%=ngayArr[i] %>' >
																				</TD>
																				
																				</TR>
																				</TABLE>
																			</td>
										                            		<td ><input type="text" name="thongtin" value="<%= thongtinArr[i] %>" style="width: 100%" /></td>
										                            		<td ><input type="text" name="ghichu" value="<%= ghichuArr[i] %>" style="width: 100%" /></td>
										                            	</Tr>
							                            			<% sodong++; }
							                            		}
							                            		
							                            		while(sodong < 20)
							                            		{
							                            			%>
							                            			
							                            			<Tr>
									                            		<td >
										                            		<TABLE cellpadding="0" cellspacing="0">
																			<TR><TD >
																				<input class="days" type="text" name="ngay" value=''  >
																			</TD>
																			
																			</TR>
																			</TABLE>
																		</td>
									                            		<td ><input type="text" name="thongtin" value="" style="width: 100%" /></td>
									                            		<td ><input type="text" name="ghichu" value="" style="width: 100%" /></td>
									                            	</Tr>
							                            			
							                            		<% sodong++; }
							                            	%>
							                            	</table>
							                            	</div>
							                            	
							                            	</td>
						                            		
													
												</TR>
												<tr>
												</tr>
								                    	</table>
								                     	
							            		</DIV> 
							            		</div>
													</td>
													
													
												</tr>
												<TR >
												
													<td class="plainlabel">Loại sản phẩm</td>
													<td class="plainlabel">
													<select name = "loaispId" style="width: 200px" >
														<option value=""></option>
						                        	<%
						                        		if(loaispRs != null)
						                        		{
						                        			try
						                        			{
						                        				
						                        			while(loaispRs.next())
						                        			{  
						                        			if( loaispRs.getString("pk_seq").equals(spBean.getLoaisp())){ %>
						                        				<option value="<%= loaispRs.getString("pk_seq") %>" selected="selected" ><%= loaispRs.getString("ten") %></option>
						                        			<%}else { %>
						                        				<option value="<%= loaispRs.getString("pk_seq") %>" ><%= loaispRs.getString("ten") %></option>
						                        		 <% } } loaispRs.close();} catch(Exception ex){}
						                        		}
						                        	%>
						                    		</select>
													</td>
														<TD class="plainlabel">Nồng độ/Hàm lượng </TD>
													<TD class="plainlabel"><input name="hamluong" type="text" style="width: 200px" value='<%=spBean.getHamluong()%>'></TD>
													<TD class="plainlabel">Tên hoạt chất </TD>
													<TD class="plainlabel"><input name="tenhoatchat" type="text" style="width: 200px" value='<%=spBean.getTenhoatchat()%>'></TD>
														
												</TR>
												<TR >
															<TD class="plainlabel"> Nguồn gốc </TD>
															<TD  class="plainlabel">
															<select name="nguongoc">
															<%
																try{
																	int i = 0;
																	String nguongoc[] = {"NĐ","NK","NM"};
																	while (i < 3)
																	{
																		if (spBean.getNguongoc().equals(nguongoc[i]))
																		{
																		%>
																		<option value='<%=nguongoc[i]%>'
																			selected><%=nguongoc[i]%></option>
																		<%} else{%>
																		<option value='<%=nguongoc[i]%>'><%=nguongoc[i]%></option>
																		<%}
																		i++;
																	}
															
																} catch (Exception e)
																{
																	e.printStackTrace();
																}%>


													</select>
													</TD>
<%-- 													<TD class="plainlabel" > <input name="nguongoc" type="text" style="width: 200px" value='<%=spBean.getNguongoc()%>'></TD> --%>
													
													<TD class="plainlabel">Thuế suất</TD>
													<TD class="plainlabel" colspan = "5"> <input  name="thuexuat" type="text" style="width: 200px" value='<%=spBean.getThuexuat()%>' onkeypress="return keypress(event);">%</TD>
													
													
												</TR>
												<TR style="display: none">
													<TD class="plainlabel">Packsize<FONT
														class="erroralert"> *</FONT></TD>
													<TD colspan="3" class="plainlabel"><select
														name="packsizeid">

															<option value=""></option>
															<%
																try{
																	while (rspacksize.next())
																	{
																		if (spBean.getPacksizeId().equals(rspacksize.getString("pk_seq")))
																		{
															%>
															<option value='<%=rspacksize.getString("pk_seq")%>'
																selected><%=rspacksize.getString("ten")%></option>
															<%} else{%>
															<option value='<%=rspacksize.getString("pk_seq")%>'><%=rspacksize.getString("ten")%></option>
															<%}
															}
																rspacksize.close();
																} catch (java.sql.SQLException e)
																{}%>


													</select></TD>
												</TR>

												<TR class="plainlabel" >
													<TD   class="plainlabel">Giá bán lẻ chuẩn (Chưa VAT) <FONT
														class="erroralert" > *</FONT></TD>
													<TD colspan="6" class="plainlabel" align="left">
														<%
															String gia = "";
															if (spBean.getGiablc().trim().length() != 0)
															{
																gia = formatter.format(Double.parseDouble(spBean.getGiablc()));
															} else
															{
																gia = spBean.getGiablc();
															}
														%> <input name="giablc" type="text" size="10"
														style="text-align: right;" value='<%=gia%>'
														onkeyup="Dinhdang(this)"
														onkeypress="return keypress(event);">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

													</TD>
												</TR>
												<tr style="display: none;">
													<TD class="plainlabel">Số thùng quy đổi dành cho
														thưởng <FONT class="erroralert"> *</FONT>
													</TD>
													<TD colspan="2" class="plainlabel"><INPUT type="text"
														name="quydoithuong"
														style="width: 150px; text-align: right;"
														value='<%=spBean.getquydoithuong()%>'
														onkeypress="return keypress(event);"> &nbsp; <b><i></sup></i></b></TD>
												</tr>

											</TABLE>
										</FIELDSET> <%if(spBean.getType().equals("1")){%>
										<fieldset>
											<legend class="legendtitle"> Tiêu chí lọc sản phẩm
												bundle </legend>
											<table style="width: 100%" cellpadding="4" cellspacing="1">
												<tr class="tbheader">
													<TD width="10%" class="plainlabel">Chủng loại</TD>
													<TD width="90%" valign="middle" class="plainlabel"><a
														href="" id="clId" rel="subcontentClId"> &nbsp; <img
															alt="Loại sản phẩm" src="../images/vitriluu.png"></a>
														<DIV id="subcontentClId"
															style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 590px; height: 300px; overflow: auto; padding: 4px;">
															<table width="90%" align="center">
																<tr class="tbheader">
																	<th width="350px">Chủng loại</th>
																	<th width="100px" align="center">Chọn hết <input
																		type="checkbox" onchange="sellectAll_ClId()"
																		id="chkAll_ClId"></th>
																</tr>
																<%
																	if (cl != null)
																	{
																		cl.beforeFirst();
																		int jj=0;
																		while (cl.next())
																		{
																			if(jj %2 ==0){
																%>
																<tr class="tblightrow">
																	<%
																		}  else {
																	%>

																<tr class="tbdarkrow">
																	<%
																		}
																	%>
																	<td><%=cl.getString("clTen")%></td>
																	<td>
																		<%
																			if (spBean.getChungloaiIds().indexOf(cl.getString("clId")) >= 0)
																			{
																		%> <input type="checkbox" name="chungloaiIds"
																		checked="checked" value="<%=cl.getString("clId")%>" />
																		<%
																			} else{
																		%> <input type="checkbox" name="chungloaiIds"
																		value="<%=cl.getString("clId")%>" /> 
																		<%}%>
																	</td>
																</tr>
																<%
																	jj++;
																	}
																	cl.close();
																%>
																<%}%>
															</table>
															<div align="right">
																<label style="color: red"></label>
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
																	href="javascript:dropdowncontent.hidediv('subcontentClId');submitform();">Hoàn
																	tất</a>
															</div>

														</DIV></TD>
												</TR>

												<tr class="tbheader">
													<TD class="plainlabel">Nhãn hàng</TD>
													<TD class="plainlabel" valign="middle"><a href=""
														id="nhId" rel="subcontentNhIds"> &nbsp; <img
															alt="Nhãn hàng" src="../images/vitriluu.png"></a>
														<DIV id="subcontentNhIds"
															style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 590px; height: 300px; overflow: auto; padding: 4px;">
															<table width="90%" align="center">
																<tr class="tbheader">
																	<th width="350px">Nhãn hàng</th>
																	<th width="100px" align="center">Chọn hết <input
																		type="checkbox" onchange="sellectAll_NhId()"
																		id="chkAll_NhId"></th>
																</tr>
																<%
																	if (nh != null)
																	{
																		nh.beforeFirst();
																		int jj=0;
																		while (nh.next())
																		{
																			if(jj %2 ==0){
																%>
																<tr class="tblightrow">
																	<%
																		}  else {
																	%>

																<tr class="tbdarkrow">
																	<%
																		}
																	%>
																	<td><%=nh.getString("nhTen")%></td>
																	<td>
																		<%
																			if (spBean.getNhanhangIds().indexOf(nh.getString("nhId")) >= 0)
																			{
																		%> <input type="checkbox" name="nhanhangIds"
																		checked="checked" value="<%=nh.getString("nhId")%>" />
																		<%
																			} else{
																		%> <input type="checkbox" name="nhanhangIds"
																		value="<%=nh.getString("nhId")%>" /> <%}%>
																	</td>
																</tr>
																<%
																	jj++;
																	}
																		nh.close();
																%>
																<%
																	}
																%>
															</table>
															<div align="right">
																<label style="color: red"></label>
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
																	href="javascript:dropdowncontent.hidediv('subcontentNhIds');submitform();">Hoàn
																	tất</a>
															</div>

														</DIV></TD>
												</TR>

											</table>
										</fieldset> <%}
													if (splist != null)
														{
															int mm = 0;
													%>
										<fieldset>
											<legend class="legendtitle"> Chọn sản phẩm thuộc
												Bundle </legend>
											<table style="width: 100%" cellpadding="4" cellspacing="1">
												<tr class="tbheader">
													<th align="center">Mã sản phẩm</th>
													<th align="center">Tên sản phẩm</th>
													<th align="center" style="width: 80px">Số lượng</th>
													<th align="center">Chọn</th>
												</tr>
												<%
													while (splist.next())
													{
														if (mm % 2 == 0)
														{
												%>
												<tr class="tblightrow">
													<%
														} else{
													%>

												<tr class="tbdarkrow">
													<%
														}
													%>
													<td><%=splist.getString("spMa")%></td>
													<td><%=splist.getString("spTen")%></td>
													<td><input type="text"
														id="spSoLuong<%=Integer.toString(mm)%>"
														style="width: 100%; text-align: right;"></td>
													<td align="center">
														<%
														if (spIds.contains(splist.getString("pk_seq")))
														{
														%> <input type="checkbox" name="spIds"
														id="spIds<%=Integer.toString(mm)%>"
														value='<%=splist.getString("pk_seq")%>' checked="checked"
														onchange="chonSp(<%=mm%>)"> <%
														} else
																	{
														%> <input type="checkbox" name="spIds"
														id="spIds<%=Integer.toString(mm)%>"
														value='<%=splist.getString("pk_seq")%>'
														onchange="chonSp(<%=mm%>)"> 
														<%}%>
													</td>
												</tr>
												<%
													mm++;
													}
												%>
											</table>
										</fieldset> <%}%>


										<TABLE width="100%" border="0" cellpadding="0"
											cellspacing="0">
											<TR>
												<TD>
													<FIELDSET>
														<LEGEND class="legendtitle" style="color: black">Chọn Nhóm Hàng</LEGEND>
														<TABLE border="0" width="100%" cellpadding="4"
															cellspacing="1">
															<TR class="tbheader">
																<TH width="30%">Mã nhóm</TH>
																<TH width="60%">Tên nhóm</TH>
																<TH width="10%">Chọn</TH>
															</TR>
															<%
												try
												{
													String lightrow = "tblightrow";
													String darkrow = "tbdarkrow";
													int m = 0;
													if (!(nhomhangRs == null))
													{
														while (nhomhangRs.next())
														{
															if (m % 2 != 0)
															{
															%>
															<TR class=<%=lightrow%>>
																<%
																	} else{
																%>

															<TR class=<%=darkrow%>>
																<%
																	}
																%>

																<TD><%=nhomhangRs.getString("ma")%></TD>
																<TD><div align="left"><%=nhomhangRs.getString("ten")%>
																	</div></TD>
																<TD>
																	<div align="center">
																		<%
																		if (spBean.getNhomHangId().indexOf(nhomhangRs.getString("pk_Seq")) >=0  ) {%>
																		<input type="checkbox" name="nhomhangId" value='<%=nhomhangRs.getString("pk_Seq")%>' checked>
																		<%
																			} else{
																		%>
																		<input type="checkbox" name="nhomhangId" value='<%=nhomhangRs.getString("pk_Seq")%>'>
																		<%
																			}
																		%>

																	</div>
																</TD>
															</TR>
															<%
																m++;
																		}
																		nhomhangRs.close();
																	}
																} catch (java.sql.SQLException e)
																{
																	e.printStackTrace();
																}
															%>

														</TABLE>
													</FIELDSET>
												</TD>
											</TR>
										</TABLE>


										<TABLE width="100%" border="0" cellpadding="0"
											cellspacing="0">
											<TR>
												<TD>
													<FIELDSET>
														<LEGEND class="legendtitle" style="color: black">Chọn
															nhóm sản phẩm</LEGEND>
														<TABLE border="0" width="100%" cellpadding="4"
															cellspacing="1">
															<TR class="tbheader">
																<TH width="30%">Nhóm sản phẩm</TH>
																<TH width="60%">Diễn giải</TH>
																<TH width="10%">Chọn</TH>
															</TR>
															<%
																try
															{
																String lightrow = "tblightrow";
																String darkrow = "tbdarkrow";
																int m = 0;
																if (!(nsp == null))
																{
																	while (nsp.next())
																	{
																		if (m % 2 != 0)
																		{
															%>
															<TR class=<%=lightrow%>>
																<%
																	} else{
																%>

															<TR class=<%=darkrow%>>
																<%
																	}
																%>

																<TD><%=nsp.getString("nspTen")%></TD>
																<TD><div align="left"><%=nsp.getString("diengiai")%>
																	</div></TD>
																<TD>
																	<div align="center">
																		<%
																		if (nspIds.contains(nsp.getString("nspId")))
																		{
																		%>
																		<input type="checkbox" name="nspIds"
																			value='<%=nsp.getString("nspId")%>' checked>
																		<%
																			} else{
																		%>
																		<input type="checkbox" name="nspIds"
																			value='<%=nsp.getString("nspId")%>'>
																		<%
																		}
																		%>

																	</div>
																</TD>
															</TR>
															<%
															m++;
																			}
																			nsp.close();
																		}
																	} catch (java.sql.SQLException e)
																	{
																	}
															%>

														</TABLE>
													</FIELDSET>
												</TD>
											</TR>
										</TABLE>

										<TABLE width="100%" cellpadding="0" cellspacing="0">
											<TR>
												<TD>
													<FIELDSET>
														<LEGEND class="legendtitle">&nbsp;Thiết lập quy
															cách</LEGEND>
														<TABLE border="1" cellpadding="0" cellspacing="1"
															width="100%">
															<TR class="tbheader">
																<TH width="21%">Số lượng</TH>
																<TH width="21%">Đơn vị đo lường</TH>
																<TH width="16%">Quy đổi</TH>
																<TH width="21%">Số lượng</TH>
																<TH width="21%">Đơn vị đo lường</TH>
															</TR>
															<%
																String[] sl1 = spBean.getSl1();
																String[] sl2 = spBean.getSl2();
																String[] dvdl1 = spBean.getDvdl1();
																String[] dvdl2 = spBean.getDvdl2();
// 																if (!(dvdl1[0] == null) & !(dvdl2[0] == null))
// 																{
// 																	if (!dvdl1[0].equals(dvdlId))
// 																	{
// 																		dvdl1[0] = dvdlId;
// 																		sl1[0] = "";
// 																	}

// 																	if (!dvdl2[0].equals("100018"))
// 																	{
// 																		dvdl2[0] = "100018";
// 																		sl2[0] = "";
// 																	}
// 																} else
// 																{
// 																	dvdl1[0] = dvdlId;
// 																	sl1[0] = "";
// 																	dvdl2[0] = "100018";
// 																	sl2[0] = "";

// 																}

																for (int i = 0; i < 5; i++)
																{
															%>
															<TR class='tblightrow'>
																<TD align="center">
																	<%
																		if (!(dvdl1[i] == null))
																	{
																		if (dvdl1[i].trim().length() > 0)
																		{
																	%> <input name="sl1" type="text" style="width: 100%"
																	value="<%=sl1[i]%>"> <%
															} else{
													%> <input name="sl1" type="text" style="width: 100%" value="">
																	<%
																		}
																} else
																{
																	%> <input name="sl1" type="text" style="width: 100%" value="">
																	<%
																		}
																	%>
																</TD>
																<TD align="center">
																	<%
																		if (i == 0)
																		{
																	%> <select name="dvdl1" id="<%=i%>"
																	style="width: 100%; height: 22"
																	onChange="updateUoM();">
																	<%
																	} else
																	{
																	%>
																	<select name="dvdl1" id="<%=i%>"
																	style="width: 100%; height: 22">
																	<%
																	}
																	if (i == 1)
																	{
																	%>
																	<option ></option>
																	<%
																	}
																	%>
																	<option value=""></option>
																	<%
																	dvdl = spBean.createDvdlRS();
																	try
																	{
																		while (dvdl.next())
																		{
																			if (!(dvdl1[i] == null))
																			{
																				if (dvdl1[i].equals(dvdl.getString("dvdlId")))
																				{
																	%>
													<option value="<%=dvdl.getString("dvdlId")%>"
													selected><%=dvdl.getString("dvdlTen")%>
													</option>
													<%
													} else
															{
													%>
													<option value="<%=dvdl.getString("dvdlId")%>"><%=dvdl.getString("dvdlTen")%>
													</option>
													<%
													}
														} else
														{
													%>
													<option value="<%=dvdl.getString("dvdlId")%>"><%=dvdl.getString("dvdlTen")%>
													</option>
													<%
													}
													}
													dvdl.close();
													} catch (java.sql.SQLException e)
													{
													}
													%>


										</select>
										</TD>
										<TD align="center">=</TD>
										<TD align="center">
										<%
										if (!(dvdl2[i] == null))
										{
										if (dvdl2[i].trim().length() > 0)
										{
										%> <input name="sl2" type="text" style="width: 100%"
										value=<%=sl2[i]%>> <%
										} else
										{
										%> <input name="sl2" type="text" style="width: 100%" value="">
										<%
										}
										} else
										{
										%> <input name="sl2" type="text" style="width: 100%" value="">
										<%
										}
										%>
										</TD>
										<TD align="center"><select name="dvdl2"
										style="width: 100%; height: 22">
										<%
										if (i == 0)
										{
										%>
										<option ></option>
										<%
										} else
										{
										%>
										<option value=""></option>
										<%
										}
										dvdl = spBean.createDvdlRS();
										try
										{
										while (dvdl.next())
										{
										if (!(dvdl2[i] == null))
										{
											if (dvdl2[i].equals(dvdl.getString("dvdlId")))
											{
										%>
										<option value="<%=dvdl.getString("dvdlId")%>"
										selected><%=dvdl.getString("dvdlTen")%>
										</option>
										<%
										} else
											{
										%>
										<option value="<%=dvdl.getString("dvdlId")%>"><%=dvdl.getString("dvdlTen")%>
										</option>
										<%
										}
										} else
										{
										%>
										<option value="<%=dvdl.getString("dvdlId")%>"><%=dvdl.getString("dvdlTen")%>
										</option>
										<%
										}
										}
										dvdl.close();
										} catch (java.sql.SQLException e)
										{
										}
										%>

																</select></TD>
															</TR>
															<%
																}
															%>
														</TABLE>
													</FIELDSET>
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
							</TABLE>

						</TD>
					</TR>
				</TABLE>
			</TD>
	</TABLE>
	<script type="text/javascript">
dropdowncontent.init('nhId', "right-bottom", 300, "click");
dropdowncontent.init('clId', "right-bottom", 300, "click");
dropdowncontent.init('noidungtenxuathoadon', "right-top", 500, "click");
dropdowncontent.init('noidungnguongocnhanhapkhau', "right-top", 500, "click");

</script>
</form>

</BODY>

</HTML>

<%

if (dvdl != null){
		dvdl.close();
	if (dvkd != null)
		dvkd.close();
	if (nh != null)
		nh.close();
	if (cl != null)
		cl.close();
	if (nsp != null)
		nsp.close();
	spBean.DBClose();
}
%>