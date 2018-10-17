<%@page import="geso.traphaco.distributor.beans.kiemkhotdv.IKiemkhotdv"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.distributor.beans.dieuchinhtonkho.IDieuchinhtonkho"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% NumberFormat formatter = new DecimalFormat("#,###,###.##");%>

<% IKiemkhotdv dctkBean = (IKiemkhotdv)session.getAttribute("dctkBean"); %>
<% ResultSet dvkd = (ResultSet) dctkBean.getDvkd(); %>
<% ResultSet kbh = (ResultSet) dctkBean.getKbh(); %>
<% ResultSet kho = (ResultSet) dctkBean.getKho(); %>
<% String[] spId = (String[])dctkBean.getSpId(); %>
<% String[] masp = (String[])dctkBean.getMasp(); %>
<% String[] tensp = (String[])dctkBean.getTensp() ; %>
<% String[] tonkho = (String[])dctkBean.getTonkho(); %>
<% String[] dv = (String[])dctkBean.getDonvi(); %>
<% String[] dc = (String[])dctkBean.getDc(); %>
<% String[] giamua = (String[])dctkBean.getGiamua(); %>
<% String[] dongiathung = (String[])dctkBean.getDongiathung(); %>
<% String[] ttien = (String[])dctkBean.getTtien(); %>
<% String[] solo = (String[])dctkBean.getSolo(); %>
<% String[] tonthung = (String[])dctkBean.getTonthung(); %>
<% String[] tonle = (String[])dctkBean.getTonle(); %>

<% String[] soluongthung = (String[])dctkBean.getSoluongthung(); %>
<% String[] soluongle = (String[])dctkBean.getSoluongle(); 
String[] spNGAYHETHAN = dctkBean.getSpNgayHetHan();
String[] soluong = (String[])dctkBean.getSoluong(); 
String[] book = (String[])dctkBean.getBook(); 
String[] available = (String[])dctkBean.getAvailable(); 


%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
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
<SCRIPT src="../js/md5.js" type="text/javascript" language="javascript">
</SCRIPT>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">

<SCRIPT src="../js/scripts.js" type="text/javascript" language="javascript"> </SCRIPT>
<SCRIPT src="../js/commun.js" type="text/javascript" language="javascript"> </SCRIPT>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<SCRIPT type="text/javascript">
// function setTTien()
// {	 	
// 	var tonkhothung=document.getElementsByName("tonthung");
// 	var tonkho=document.getElementsByName("tonkho");
// 	var tonkhole=document.getElementsByName("tonle");
// 	var soluongthung=document.getElementsByName("soluongthung");
// 	var soluongle=document.getElementsByName("soluongle");
// 	var dongia=document.getElementsByName("giaton");
// 	var thanhtien=document.getElementsByName("thanhtien");
// 	var masp = document.getElementsByName("masp");
// 	var donvi=document.getElementsByName("donvi");
// 	var quycach=document.getElementsByName("quycach");
// 	var dieuchinh=document.getElementsByName("dieuchinh");
// 	var sodong=masp.length;
// 	var tongtien = 0;
// 	for(var i=0; i < sodong ; i++)
// 	{
// 		var don_gia=dongia.item(i).value.replace(/\,/g,'').replace(",","");
// 		var so_luong=soluongle.item(i).value.replace(/\,/g,'').replace(",","");
// 		var thanh_tien=thanhtien.item(i).value.replace(/\,/g,'').replace(",","");
// 		var ton_kho= tonkho.item(i).value.replace(",","");
// 		var dieu_chinh=dieuchinh.item(i).value.replace(",","");		
// 		if( soluongle.item(i).value  !='')
// 		{ 
		
// 			var dieu_chinh_1 = parseFloat(so_luong) - parseFloat(ton_kho);
			
		
// 			dieuchinh.item(i).value=dieu_chinh_1;
			
// 			thanh_tien=	parseFloat(so_luong	)*	parseFloat(don_gia);	
// 			thanhtien.item(i).value=formatCurrency(thanh_tien);
// 			tongtien = parseFloat(tongtien) + parseFloat(thanh_tien);
// 		}else 		
// 		{
// 			thanh_tien=	0;	
// 			thanhtien.item(i).value=formatCurrency(thanh_tien);
// 			tongtien = parseFloat(tongtien) + parseFloat(thanh_tien);
// 		} 
// 	}
// 	document.getElementById("ttien").value= formatCurrency(tongtien); 
// 	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtien) + " Đồng Việt Nam";		
// }
function QuyRaThung(pos)
{
	
	var quycach = document.getElementById("quycach_" + pos);
	var soluongThung = document.getElementById("soluongthung_" + pos);		
	var soluongLe = document.getElementById("soluongle_" + pos);
	soluongThung.value = "0";
	if( parseFloat(soluongLe.value) > 0 )
	{
		soluongThung.value =  parseFloat(soluongLe.value) / parseFloat(quycach.value)  ;
	}		
	setTTien();
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

function isNumeric(n)
{
    var n2 = n;
    n = parseFloat(n);
    return (n!='NaN' && n2==n);
}
           
function submitform()
{      
   document.forms["dctkForm"].submit();
}

function submitenter(myfield,e)
{
var keycode;
if (window.event) keycode = window.event.keyCode;
else if (e) keycode = e.which;
else return true;

if (keycode == 13)
   {
   return false;
   }
else
   return true;
}

function chotform()
{  document.forms['dctkForm'].action.value='chot';
   document.forms["dctkForm"].submit();
}

 function saveform()
{
	//setTTien();
	document.getElementById("btnSave").innerHTML = "<img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'>";

	document.forms['dctkForm'].action.value='save';
    document.forms["dctkForm"].submit();
}

function printform()
{   
   document.forms["dctkForm"].submit();
}
function QuyRaLe(pos)
{
	
	var quycach = document.getElementById("quycach_" + pos);
	var soluongThung = document.getElementById("soluongthung_" + pos);		
	var soluongLe = document.getElementById("soluongle_" + pos);
	soluongLe.value = "0";
	if( parseFloat(soluongThung.value) > 0 )
	{
		soluongLe.value = DinhDangTien( Math.round( parseFloat(soluongThung.value) * parseFloat(quycach.value) ) );
	}	
	setTTien();
}

function QuyRaThung(pos)
{
	
	var quycach = document.getElementById("quycach_" + pos);
	var soluongThung = document.getElementById("soluongthung_" + pos);		
	var soluongLe = document.getElementById("soluongle_" + pos);
	soluongThung.value = "0";
	if( parseFloat(soluongLe.value) > 0 )
	{
		soluongThung.value =  parseFloat(soluongLe.value) / parseFloat(quycach.value)  ;
	}		
	setTTien();
}

</SCRIPT>

</HEAD>
<body>
	<form name="dctkForm" method="post" action="../../KiemkhotdvUpdateSvl">
		<INPUT name="userId" type="hidden" value='<%=userId %>' size="30"> 
		<INPUT name="nppId" type="hidden" value='<%=dctkBean.getNppId() %>' size="30"> 
		<INPUT name="action" type="hidden" value='1' size="30">
		<input type="hidden" name="id" value="<%=dctkBean.getId() %>"> 
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan=4 valign="top">
					<TABLE border=0 width=100%>

						<TBODY>
							<TR>
								<TD align="left">
									<TABLE width="100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD align="left" class="tbnavigation">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr height="22">
														<TD align="left" colspan="2" class="tbnavigation"> Quản lý tồn kho > Nghiệp vụ khác > Kiểm kho > Cập nhật</TD>
														<TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= dctkBean.getNppTen() %> &nbsp;
														</TD>

													</tr>
												</table>
											</TD>
										</TR>
									</TABLE>
								<TD>
							</TR>
							<TR>
								<TD>
									<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
										<TR>
											<TD>
												<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
													<TR class="tbdarkrow">
														<TD width="30" align="left"><A href="../../KiemkhotdvSvl?userId=<%=userId %>"><img src="../images/Back30.png" alt="Quay về" title="Quay về" border="1" longdesc="Quay về" style="border-style: outset"></A></TD>
														<TD width="2" align="left">&nbsp;</TD>
														<TD width="30" align="left">
															<div id="btnSave">
																<A href="javascript: saveform()"><img src="../images/Save30.png" alt="Lưu lại" title="Lưu lại" border="1" longdesc="Lưu lại" style="border-style: outset"></A>
															</div>
														</TD>
														<TD align="left">&nbsp;</TD>
													</TR>
												</TABLE>
											</TD>
										</TR>

									</TABLE>
								</TD>
							</TR>
							<TR>
								<TD>
									<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD align="left" colspan="4" class="legendtitle">
												<FIELDSET>

													<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>

													<textarea name="dataerror" style="width: 100%; color: #F00; font-weight: bold" style="width:100%" rows="1"><%= dctkBean.getMessage() %></textarea>
													<% dctkBean.setMessage(""); %>
												</FIELDSET>
											</TD>
										</TR>

										<TR>
											<TD align="left">
												<FIELDSET>
													<LEGEND class="legendtitle">&nbsp;Kiểm kho </LEGEND>

													<TABLE cellpadding="3" cellspacing="0" width="100%" border=0>
														<TR>
															<TD width="10%" class="plainlabel">Chi nhánh / Đối tác</TD>
															<TD colspan="3" width="40%" class="plainlabel"><%= dctkBean.getNppTen() %></TD>
														</TR>

														<TR>
															<TD class="plainlabel">Ngày điều chỉnh</TD>
															<TD class="plainlabel"><input class="days" type="text" name="ngaydc" size="11" value="<%=dctkBean.getNgaydc() %>" readonly="readonly"></TD>

															<TD class="plainlabel">Trình dược viên</TD>
															<TD class="plainlabel"><SELECT name="dvkdId" onChange="submitform();">
																	<option value=""></option>
																	<% try{ 
							  	 							while(dvkd.next()){ 
							  	 								if(dvkd.getString("ddkd_fk").equals(dctkBean.getDvkdId())){ %>
																	<option value='<%=dvkd.getString("ddkd_fk")%>' selected><%=dvkd.getString("ten") %></option>
																	<%}else{ %>
																	<option value='<%=dvkd.getString("ddkd_fk")%>'><%=dvkd.getString("ten")  %></option>
																	<%}
							  	 							}
							  	 						}catch(java.sql.SQLException e){
							  	 							e.printStackTrace();
							  	 						} %>

															</SELECT></TD>
														</TR>

														<TR>
															<TD class="plainlabel" >Lý do điều chỉnh</TD>
															<TD class="plainlabel" colspan="1"><input type="text" name="lydo" size="11" value="<%= dctkBean.getLydodc() %>"></TD>

																	<TD class="plainlabel">Nhóm kênh</TD>
															<TD class="plainlabel" colspan="4"><SELECT name="khoId" onChange="submitform();">
																	<option value=""></option>
																	<%  try{
								  								while(kho.next()){								  			
								  								if (dctkBean.getKhoId().equals(kho.getString("Id"))){ %>
																	<option value="<%= kho.getString("Id")%>" selected><%= kho.getString("ten")%></option>
																	<%}else{ %>
																	<option value="<%= kho.getString("Id")%>"><%= kho.getString("ten")%></option>
																	<%}
								  					    }
								  						}catch (java.sql.SQLException e){
								  							
								  							e.printStackTrace();
								  						} %>
															</SELECT></TD>		
															
														</TR>

														<TR class="tblightrow"  style="display: none">
															<TD class="plainlabel" style="display: none">Giá trị điều chỉnh</TD>
															<TD class="plainlabel" style="display: none"><input name="ttien" id="ttien" type="text" value="<%= dctkBean.getGtdc() %>"  style="background-color: #FFFFFF"> VND</TD>

														
														</TR>
														<TR class="tblightrow" style="display: none">
															<TD class="plainlabel">Bằng chữ</TD>
															<TD colspan="3" class="plainlabel"><span id="lblDocSo" style="font-weight: bold; font-style: italic;"></span></TD>
														</TR>

													</TABLE>
												</FIELDSET>

											</TD>


										</TR>

										<TR>
											<TD>
												<fieldset>
													<TABLE id="tab" width=100% cellpadding="1" border="1" style="border-color: #80CB9B" cellspacing="0">
														<TR class="tbheader">
															<TH rowspan="1" width="10%">Mã sản phẩm</TH>
															<TH rowspan="1" width="20%">Tên sản phẩm</TH>
															<TH width="5%" rowspan="1">Số Lô</TH>
															<TH width="10%" colspan="1">Đơn vị</TH>
															<TH width="10%" rowspan="1">Ngày hết hạn</TH>
															<TH width="13%" rowspan="1">Số Lượng</TH>
															<TH width="13%" rowspan="1">Book</TH>
															<TH width="13%" rowspan="1">Available</TH>
															<TH width="10%" rowspan="1">Điều chỉnh</TH>
															<TH width="15%" rowspan="1"  style="display:none;" >Gía mua TB</TH>
															<TH width="20%" rowspan="1"    style="display:none;" >T. tiền (w/o VAT)</TH>
														</TR>
<!-- 														<TR class="tbheader"> -->
<!-- 															<TH width="5%">Thùng</TH> -->
<!-- 															<TH width="5%">Lẻ (quy đổi)</TH> -->
<!-- 														</TR> -->
														<% 
               								String lightrow = "tblightrow";
                                            String darkrow = "tbdarkrow";
                                            int size = dctkBean.getSize();
                                            int m= 0;
                                            if(masp!= null){
                                            for(int i=0; i < size; i++){ 
												if(m%2 == 0){					%>
														<TR class=<%=lightrow%>>
															<% 	}else{%>
														
														<TR class=<%=lightrow%>>
															<%	} %>

															<TD>
																<input type="text" name="masp" id='<%="masp" + masp[i] %>'  readonly="readonly"  value='<%=masp[i]%>' readonly="readonly" style="text-align: left; width: 100%; border: 0">
															</TD>
															<TD>
																<input type="text" name="tensp" value='<%=tensp[i]%>' readonly="readonly" style="text-align: left; width: 100%; border: 0">
															</TD>
															<TD>
																<input type="text" name='solo' id='solo<%=i%>' value="<%= solo[i] %>" readonly="readonly"  style="width: 100%; text-align: left; border: 0">
															</TD>
<!-- 															<TD> -->
<%-- 																<input type="text" name='tonthung'  id='tonthung<%=i%>'  value="<%= tonthung[i] %>"   onchange="Dinhdang(this)" style="width: 100%; text-align: right; border: 0"> --%>
<!-- 															</TD> -->
															<TD>
																<input type="text" name='donvi' value="<%= dv[i] %>"  onchange="Dinhdang(this)" style="width: 100%; text-align: right; border: 0"   >
															</TD>
															
<!-- 															<TD> -->
<%-- 																<input type="text" name='soluongthung' id="soluongthung_<%=i %>" value="<%= soluongthung[i] %>"   style="width: 100%; text-align: right; border: 1"  onChange="QuyRaLe(<%=i %>);setTTien();Dinhdang(this);" style="width:100% ;text-align: left;" onKeyPress="return submitenter(this,event);"> --%>
<!-- 															</TD> -->
															<td>
																<input type="text" name="spNgayHetHan" value="<%= spNGAYHETHAN[i] %>"  style="width: 100%; text-align: left;" readonly="readonly"   >
																</td>
															<TD>
																<input type="text" name='soluong' id="soluong<%=i %>" value="<%= soluong[i] %>"   style="width: 100%; text-align: right; border: 1"  style="width:100% ;text-align: left;"onKeyPress="return submitenter(this,event);">
															</TD>
															<TD>
																<input type="text" name='book' id="book<%=i %>" value="<%= book[i] %>"   style="width: 100%; text-align: right; border: 1"  style="width:100% ;text-align: left;"onKeyPress="return submitenter(this,event);">
															</TD>
																<TD>
																<input type="text" name='available' id="book<%=i %>" value="<%= available[i] %>"   style="width: 100%; text-align: right; border: 1"  style="width:100% ;text-align: left;"onKeyPress="return submitenter(this,event);">
															</TD>
															
															
<!-- 															<TD style="display:none;" > -->
<%-- 																<input type="text" name='dongiathung' id="dongiathung<%=i %>" value="<%=dongiathung[i] %>"  onchange="Dinhdang(this)" style="width: 100%; text-align: right;"readonly="readonly"> --%>
<!-- 															</TD> -->
															
															
															<TD>
																<input type="text" name='dieuchinh' id='dieuchinh<%=i %>' value="<%=dc[i] %>" style="width: 100%; text-align: right;">
															</TD>
															<TD style="display:none;" >
<%-- 																<input type="text" name='thanhtien' id='thanhtien<%=i %>' value="<%=ttien[i] %>"   onchange="Dinhdang(this)" style="width: 100%; text-align: right;" readonly="readonly">  --%>
<%-- 																<input type="hidden" name='tonkho'   id='<%="tkm" + masp[i] %>' value="<%= tonkho[i] %>" style="width: 100%; text-align: left;">  --%>
																<input type="hidden" name="spId" value="<%= spId[i] %>"> 
<%-- 																<input type="hidden" name="quycach" id="quycach_<%=i %>" value='<%=quycach[i]%>'  style="text-align: right; width: 100%;border:0"> --%>
<%-- 																<input type="hidden" name='giaton' id="giaton<%=i %>" value="<%=giamua[i] %>" onchange="Dinhdang(this)" style="width: 100%; text-align: left;" readonly="readonly"> --%>
															</TD>
														</TR>
														<% m++; }} %>
													</TABLE>
												</fieldset>
											</TD>
										</TR>
										
										
									</TABLE>
								</TD>
							</TR>
						</TBODY>
					</TABLE>
				</td>
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
//setTTien(-1);
</script>
</html>
<% 	
	try{
		if(dvkd != null)
			dvkd.close();
		if(kbh != null)
			kbh.close();
	
		if(kho != null)
			kho.close();

		if(dctkBean != null){
			dctkBean.DBclose();
			dctkBean = null;
		}			
		session.setAttribute("dctkBean",null);
		spId=null;
		masp=null;
		tensp=null;
		tonkho=null;
		dv=null;
		dc=null;
		giamua=null;
		ttien=null;
	
	}
	catch (SQLException e) {}

%>
<%}%>
