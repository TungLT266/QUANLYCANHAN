<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="javax.xml.crypto.Data"%>
<%@page import="geso.traphaco.center.beans.thuongdauthung.imp.Thuongdauthung"%>
<%@page import="geso.traphaco.center.beans.thuongdauthung.IThuongdauthung"%>
<%@ page import="geso.traphaco.center.beans.thuongdauthung.ISanpham"%>
<%@ page import="geso.traphaco.center.beans.thuongdauthung.imp.Sanpham"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%NumberFormat formatter = new DecimalFormat("#,###,##0.###");%>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	IThuongdauthung obj=(Thuongdauthung)session.getAttribute("obj");
 	
 	ResultSet nspRS = obj.getNspRs();
 	List<ISanpham> spList =obj.getSpList();
 	
 	String[] tumuc = (String[])obj.getTumuc();
	String[] denmuc = (String[])obj.getDenmuc();
	String[] thuongSR = (String[])obj.getThuongSR();
	String[] thuongSS = (String[])obj.getThuongSS();
	String[] thuongASM = (String[])obj.getThuongASM();
	String[] thuongBM = (String[])obj.getThuongBM();
	String[] donvi_thuong = (String[])obj.getDonvi_thuong();
 	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>

	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
	<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
	
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<style type="text/css">
		#mainContainer{
			width:600px;
			margin:0 auto;
			text-align:left;
			height:100%;
			border-left:3px double #000;
			border-right:3px double #000;
		}
		#formContent{
			padding:5px;
		}
		/* END CSS ONLY NEEDED IN DEMO */
			
		/* Big box with list of options */
		#ajax_listOfOptions{
			position:absolute;	/* Never change this one */
			width:auto;	/* Width of box */
			height:200px;	/* Height of box */
			overflow:auto;	/* Scrolling features */
			border:1px solid #317082;	/* Dark green border */
			background-color:#C5E8CD;	/* White background color */
	    	color: black;
			text-align:left;
			font-size:1.0em;
			z-index:100000000000;
		}
		#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
			margin:1px;		
			padding:1px;
			cursor:pointer;
			font-size:1.0em;
		}
		#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
			
		}
		#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
			background-color:#317082; /*mau khi di chuyen */
			color:#FFF;
		}
		#ajax_listOfOptions_iframe{
			background-color:#F00;
			position:absolute;
			z-index:5000000000000;
		}
		form{
			display:inline;
		}
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 150px;
			border: 1px solid black;
			padding: 2px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}	
	</style>
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<script type="text/javascript" src="../scripts/AjaxCheckStock.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$(".days").datepicker({
				changeMonth : true,
				changeYear : true
			});
			
			//$("ul.tabs").tabs("div.panes > div");
		});
	</script> 
	
	<script>
	$(function() {
	 	$("ul.tabs").tabs("div.panes > div");
	});
	</script>


<SCRIPT language="JavaScript" type="text/javascript"> 
function submitform()
{
	<%if(!obj.getDisplay().equals("1")){%>
    	document.forms["ChiTieuTTForm"].submit();
    <%}%>
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
}


function FormatNumber(e)
{
	e.value = DinhDangTien(e.value.replace(/,/g,""));
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


function save(){
 
 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		 
		 document.forms["ChiTieuTTForm"].action.value = "save";
		 document.forms["ChiTieuTTForm"].submit();


}

</SCRIPT>

<script language="javascript" type="text/javascript">

function replaces()
{	
	var spId = document.getElementsByName("spId");
	var masp = document.getElementsByName("spMa");
	var tensp = document.getElementsByName("spTen");
	
	var i;
	for(i=0; i < masp.length; i++)
	{
		if(masp.item(i).value != "")
		{
			var sp = masp.item(i).value;
			var pos = parseInt(sp.indexOf(" - "));
			if(pos > 0)
			{
				masp.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
				tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf("[")));
							
				
				sp = sp.substr(parseInt(sp.indexOf(" [")) + 2); //console.log(sp);
			    spId.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
			    
			    
			}			
		}
		else
		{
			tensp.item(i).value = "";
			spId.item(i).value = "";	
		}
	}	
	setTimeout(replaces, 20);
}

function addRow(pos)
{
	var table = $('#tbSanPham');
	table.append(
            '<tr>'+
			'<td>'+
			'<input type="hidden" name="spId"  value=""/>'+ 		                            			
			'<input type="text" name="spMa" style="width: 100%"  value=""   onkeyup="ajax_showOptions(this,\'donmuahang\',event)" AUTOCOMPLETE="off"  />   </td>'+
			'<td><input type="text" name="spTen"  style="width: 100%" value=""/>   </td>'+
		'</TR>');
}



function ThemSanPham(pos)
{
	 var sl = window.prompt("Nhấp số lượng sản phẩm muốn thêm", '');
	 if(isNaN(sl) == false && sl < 100)
	 {
		 for(var i=0; i < sl ; i++)
			 addRow(pos);
	 }
	 else
	 {
		 alert('Số lượng bạn nhập không hợp lệ. Mọi lần bạn chỉ được thêm tối đa 30 sản phẩm');
		 return;
	 }
 } 
 
function xuatExcel()
{
	document.forms['ChiTieuTTForm'].action.value= 'excel';
	document.forms['ChiTieuTTForm'].submit();
	
}
 
</SCRIPT>
<script type="text/javascript" src="../scripts/ajax.js"></script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="ChiTieuTTForm" method="post"
		action="../../ThuongdauthungNewSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="userTen" value='<%=userTen%>'> <input
			type="hidden" name="nkmId" value=""> <input type="hidden"
			name="action" value="0"> <input type="hidden" name="id"
			value='<%=obj.getID()%>'> <input type="hidden" name="tenform"
			value="0"> <input type="hidden" name="loai"
			value="<%=obj.getLoai() %>">
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
											lý chỉ tiêu &gt; Chỉ tiêu Npp &gt; <%=obj.getLoai().equals("0")?"Thưởng đầu cái":"Thưởng sản phẩm mới" %>
											&gt; <%=obj.getID()>0?"Cập nhật":"Tạo mới" %></TD>
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
											href="../../ThuongdauthungSvl?userId=<%=userId%>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<%if(!obj.getDisplay().equals("1")){ %>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript: save()"><IMG
													src="../images/Save30.png" title="Luu lai" alt="Luu lai"
													border="1" style="border-style: outset"></A>
											</div>
										</TD>
										<%} %>
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
									<textarea name="dataerror" style="width: 100%"
										readonly="readonly" rows="1"><%=obj.getMessage()%></textarea>
								</FIELDSET>
							</TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông
										tin thưởng đầu thùng</LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<%System.out.println("thang:"+obj.getThangdt() +"----nam ------"+obj.getNamdt()); %>
										<TR>
											<TD class="plainlabel" width="130px">Tháng</TD>
											<TD class="plainlabel" width="220px">
											<select name="thang">
														<option value=""></option>
													<%for(int i=1;i<13;i++){ %>
														<option value="<%=i%>" <%if(obj.getThangdt().equals(i+"")){%> selected="selected" <%} %>>Tháng <%=i %></option>
													<%} %>
													
													
												</select>
											</TD>
												
												
												
											<TD class="plainlabel" width="140px">Năm</TD>
											<TD class="plainlabel" width="220px">
												<select name="nam">
														<option value=""></option>
													<%for(int i=2014;i<2020;i++){ %>
														<option value="<%=i%>" <%if(obj.getNamdt().equals(i+"")){ %> selected="selected" <%} %>><%=i %></option>
													<%} %>
												</select>
											</TD>
											<TD colspan="2" class="plainlabel"></TD>
										</TR>


										<TR>
											<TD class="plainlabel">Diễn giải</TD>
											<TD class="plainlabel"><input type="text"
												name="diengiai" value="<%=obj.getDienGiai()%>"></TD>

											<TD class="plainlabel" style="font-weight: bold;">Nhóm
												sản phẩm</TD>
											<TD class="plainlabel"><SELECT name="nsp_fk"
												onchange="submitform();">
													<option value=""></option>
													<%
					  	  						if(nspRS!=null){ 
													nspRS.beforeFirst();
						  	  						while (nspRS.next())
						  	  						{
						  	  							if(nspRS.getString("pk_seq").trim().equals(obj.getNsp_fk().trim()))
						  	  							{
						  	  							System.out.println("so sanh MATH= " + obj.getNsp_fk() +" vs " + nspRS.getString("pk_seq"));
						  	  								%>
													<OPTION selected="selected"
														value="<%=nspRS.getString("pk_Seq") %> "><%=nspRS.getString("ten")%>
													</OPTION>
													<% 
						  	  							}else{
						  	  							%>
													<OPTION value="<%=nspRS.getString("pk_Seq") %>"><%=nspRS.getString("ten")%>
													</OPTION>
													<% 
						  	  							}
						  	  						}
					  	  						}
					  	  						%>
											</SELECT></TD>
											<TD class="plainlabel" width="140px"></TD>
											<TD colspan="2" class="plainlabel"></TD>
										</TR>
										<TR>
											<TD class="plainlabel">Tiêu chí</TD>
											<TD class="plainlabel">
												<input type="radio" value="1" name="loailay" <% if(obj.getTieuchilay().equals("1")){  %> checked="checked" <%} %>/> Theo khách hàng
												<input type="radio" value="2" name="loailay"  <% if(obj.getTieuchilay().equals("2")){  %> checked="checked" <%} %>/> Theo sản phẩm
											</TD>
											<TD class="plainlabel" width="140px"></TD>
											<TD class="plainlabel" width="140px"></TD>
												<TD colspan="2" class="plainlabel"></TD>
										</TR>
										<TR>
											<TD class="plainlabel" colspan="6">
												<!-- <a class="button2" href="javascript:search();"> 
									<img style="top: -4px;" src="../images/button.png" alt=""> Tìm kiếm</a>&nbsp;&nbsp;&nbsp;&nbsp; -->
												<a class="button2" href="javascript:xuatExcel();"> <img
													style="top: -4px;" src="../images/button.png" alt="">Xuất
													dữ liệu
											</a>&nbsp;&nbsp;&nbsp;&nbsp;
										</TR>

									</TABLE>

									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<td colspan="6">

												<ul class="tabs">
													<li><a href="#">Chọn sản phẩm</a></li>
													<li><a href="#">Tiêu chí thưởng</a></li>
												</ul>

												<div class="panes">
													<div>

														<TABLE width="100%" border="0" cellspacing="1"
															cellpadding="4">
															<tbody id="tbSanPham">
																<TR class="tbheader">
																	<TH width="28%">Mã sản phẩm</TH>
																	<TH width="60%">Tên sản phẩm</TH>
																	<TH width="60%"><%=obj.getLoai().equals("0")?"Mức tối thiểu":"Trọng số" %>
																	</TH>
																</TR>
																<%
                            	int sodong=spList.size();
                            	int n=0;
                           			while(n<sodong)
                           			{
                           				ISanpham sp =spList.get(n);
                           				n++;
                           		%>
																<tr>
																	<td>
																		<input type="hidden" name="spId" value="<%=sp.getId()%>" /> 
																		<input type="text" name="spMa" style="width: 100%" value="<%=sp.getMa()%>" onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" />
																	</td>
																	<td>
																		<input type="text" name="spTen" style="width: 100%" value="<%=sp.getTen()%>" />
																	</td>
																	<td>
																		<input type="text" onkeypress="return keypress(event);" onkeyup="FormatNumber(this);" name="spTrongso" style="width: 100%" value="<%=sp.getTrongso()%>" />
																	</td>
																</tr>
																<% }
                            			
                            		while(sodong < 40)
                            		{
                            		%>
																<tr>
																	<td>
																		<input type="hidden" name="spId" value="" /> 
																		 <input type="text" name="spMa" style="width: 100%" value="" onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" />
																	</td>
																	<td>
																		<input type="text" name="spTen" style="width: 100%" value="" /></td>
																	<td>
																		<input onkeypress="return keypress(event);" onkeyup="FormatNumber(this);" type="text" name="spTrongso" style="width: 100%" value="" />
																	</td>
																</tr>
																<% sodong++; }
                            	%>
															</tbody>
															<TR>
																<TD><a class="button2"
																	href="javascript:ThemSanPham()"> <img
																		style="top: -4px;" src="../images/add.png" alt="">Thêm
																		sản phẩm
																</a>&nbsp;&nbsp;&nbsp;&nbsp;</TD>
															</TR>
														</TABLE>
													</div>
													
													
													
													<div>

														<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
															<tbody id="tbSanPham">
																<TR class="tbheader">
																	<!-- <TH width="12%">Từ mức</TH>
																	<TH width="12%">Toán tử</TH>
																	<TH width="12%">Đến mức</TH>
																	<TH width="12%">Hình thức thưởng</TH> -->
																	<TH width="12%">Thưởng SR</TH>
																	<TH width="12%">Thưởng SS</TH>
																	<TH width="12%">Thưởng ASM</TH>
																	<TH width="12%">Thưởng BM</TH>
																	
																</TR>
																
																
																
																  <%
				                    	int count = 0;
				                    	if( thuongSR != null || thuongASM!=null || thuongBM!=null || thuongSS!=null  ) 

				                    	{
				                    		int dodai=0;
				                    		if(thuongSR!=null)
				                    			dodai=thuongSR.length;
				                    		if(thuongASM!=null)
				                    			dodai=thuongASM.length;
				                    		if(thuongBM!=null)
				                    			dodai=thuongBM.length;
				                    		if(thuongSS!=null)
				                    			dodai=thuongSS.length;
				                    		for(int i = 0; i < dodai; i++)
				                    		{
												%>   
												<tr>
													<%-- <td>
														<input type="text" name="tumuc" value="<%= tumuc[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<TD align="center" > >= </TD>
													<td>
														<input type="text" name="denmuc" value="<%= denmuc[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<%String[] trangthai = new  String[] {"VNĐ","Tổng tiền"  } ;
													String[]	idTrangThai = new  String[] {"0","1"} ;
													%> 
													<td>
													<select name="donvi_thuong" style="width: 100%;" >
															<% for( int j=0;j<trangthai.length;j++) { 
		    												if(idTrangThai[j].equals(donvi_thuong[i] ) ){ %>
		      											<option value='<%=idTrangThai[j]%>' selected><%=trangthai[j] %></option>
		      		 								<%}else{ %>
		     												<option value='<%=idTrangThai[j]%>'><%=trangthai[j] %></option>
		     														<%}} %>
													</select>
												</td>			 --%>
												
												<td>
														<input type="text" name="thuongSR" value="<%= thuongSR[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<td>
														<input type="text" name="thuongSS" value="<%= thuongSS[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<td>
														<input type="text" name="thuongASM" value="<%= thuongASM[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<td>
														<input type="text" name="thuongBM" value="<%= thuongBM[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													
				      		 	</tr>
				      		 <%}count++;  System.out.println("count :::::::::::::::"+count);
				 				} %>
											<% for(int i = count; i <(count<1?1:count); i++)
				                    		{ %>   
												<tr>
													<%-- <td>
														<input type="text" name="tumuc" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<TD align="center" > >= </TD>
													<td>
														<input type="text" name="denmuc" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<%String[] trangthai = new  String[] {"VNĐ","Tổng tiền"  } ;
													String[]	idTrangThai = new  String[] {"0","1"} ;
													%> 
													<td>
													<select name="donvi_thuong" style="width: 100%;" >
															<% for( int j=0;j<trangthai.length;j++) { %>
		     												<option value='<%=idTrangThai[j]%>'><%=trangthai[j] %></option>
		     														<%} %>
													</select>
												</td>			 --%>
												
												<td>
														<input type="text" name="thuongSR" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<td>
														<input type="text" name="thuongSS" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<td>
														<input type="text" name="thuongASM" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<td>
														<input type="text" name="thuongBM" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													
				      		 	</tr>
				      		 <%} %>
								
														</TABLE>
													</div>
												</div>
											</td>
										</TR>
									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</form>
<script type="text/javascript">
		replaces();
</script>
</BODY>
</HTML>