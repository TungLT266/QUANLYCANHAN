<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.donmuahang.*"%>
<%@ page import="geso.traphaco.erp.beans.donmuahang.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%
	IErpDonmuahang dmhBean = (IErpDonmuahang) session.getAttribute("dmhBean");
	ResultSet dvthList = dmhBean.getDvthList();
	ResultSet loaihhList = dmhBean.getLoaiList();
	List<ISanpham> spList = dmhBean.getSpList();
	List<IDonvi> dvList = dmhBean.getDvList();
	List<ITiente> ttList = dmhBean.getTienteList();
	List<IKho> khoList = dmhBean.getKhoList();
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	ResultSet rs = dmhBean.getDuyet();
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
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>
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
<script type="text/javascript" src="../scripts/erp-spList.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script language="javascript" type="text/javascript">

	function replaces()
	{
		var masp = document.getElementsByName("masp");
		var tensp = document.getElementsByName("tensp");
		var donvitinh = document.getElementsByName("donvitinh");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var thanhtien = document.getElementsByName("thanhtien");
		var nhomhang = document.getElementsByName("nhomhang");
		var ngaynhan = document.getElementsByName("ngaynhan");
		
		var phantramthue = document.getElementsByName("phantramthue");
		var thuenhapkhau = document.getElementsByName("thuenhapkhau");
		
		var nguongoc = document.getElementById("nguongoc").value;
		
		var sodong =  masp.length;
		var i;
		for(i = 0; i < sodong; i++)
		{
			if(masp.item(i).value != "")
			{
				for(var k = 0;k <sodong ;k++)
		 		{
		 			if(parseInt(k)!=parseInt(i))//khong phai ma hien tai
		 			{
		 				if((masp[i].value == masp[k].value) && masp[k].value.length !=0&&ngaynhan[i].value==ngaynhan[k].value&&ngaynhan[k].value.length!=0 )
	 					{
	 						ngaynhan.item(k).value= '';
	 					}
		 			}
		 		}
				
				var sp = masp.item(i).value;
				var pos = parseInt(sp.indexOf(" -- "));
				var slg = soluong.item(i).value.replace(/,/g,""); 
				
				if(pos > 0)
				{
					masp.item(i).value = sp.substring(0, pos);
					sp = sp.substr(parseInt(sp.indexOf(" -- ")) + 3);
					tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					nhomhang.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				}
				
				if(soluong.item(i).value != "")
				{	
					soluong.item(i).value = DinhDangDonGia(slg);
					if(dongia.item(i).value != "")
					{
						var dgia = dongia.item(i).value.replace(/,/g,""); 
						dongia.item(i).value = DinhDangDonGia(dgia);
						var tt = parseFloat(slg) * parseFloat(dgia);
						thanhtien.item(i).value = DinhDangDonGia(tt);
						
						if(nguongoc == 'NN')
						{
							if(phantramthue.item(i).value != "")
							{
								var ptramthue = phantramthue.item(i).value.replace(/,/g,"");
								var tnhapkhau = parseFloat(slg) * parseFloat(dgia) * parseFloat(ptramthue) / 100;
								
								phantramthue.item(i).value = DinhDangDonGia(ptramthue);
								thuenhapkhau.item(i).value = DinhDangDonGia(tnhapkhau);
							}
							
						}
					}
				}
				else			
				{
					dongia.item(i).value = "";
					thanhtien.item(i).value = "";
					
					phantramthue.item(i).value = "";
					thuenhapkhau.item(i).value = "";
				}
			}
			else
			{
				tensp.item(i).value = "";
				donvitinh.item(i).value = "";
				nhomhang.item(i).value = "";
				soluong.item(i).value = "";
				dongia.item(i).value = "";
				thanhtien.item(i).value = "";
				
				phantramthue.item(i).value = "";
				thuenhapkhau.item(i).value = "";
			}
		}	
		
		TinhTien();
		setTimeout(replaces, 500);
	}
	
	function TinhTien()
	{
		var thanhtien = document.getElementsByName("thanhtien");	
		var tiente = document.getElementById("tiente_fk");
		var tongtien = 0;
		
		var thuenhapkhau = document.getElementsByName("thuenhapkhau");
		
		for(var i = 0; i < thanhtien.length; i++)
		{
			if( thanhtien.item(i).value != "" )
			{
				var thanh_tien = thanhtien.item(i).value.replace(/,/g,"");
				var pos = parseInt(tiente.value.indexOf(" - ")) + 3;
				var tien_te = tiente.value.substring(pos);
				
				//alert('Thanh tien: ' +  (parseFloat(thanh_tien)) + '   -- tien te: ' + parseFloat(tien_te) + ' -- Ket qua:  ' + ( parseFloat(thanh_tien) * parseFloat(tien_te) ) )
				
				if(parseFloat(thuenhapkhau) > 0)
					tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien) * parseFloat(tien_te) + parseFloat(thuenhapkhau);
				else
					tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien) * parseFloat(tien_te);
			}
		}
		
		var tienchuaVAT = tongtien;
		document.getElementById("BVAT").value = DinhDangTien(tienchuaVAT);
	
		var vat = document.getElementById("VAT").value;
		if(vat == "")
			vat = "0";

		var tongtiencoVAT = (parseFloat(tienchuaVAT) * parseFloat(vat) / 100) + parseFloat(tienchuaVAT);
		document.getElementById("AVAT").value = DinhDangTien(Math.round(tongtiencoVAT));
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
	
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
		 
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
		
		if(isNaN(num))
		num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100+0.50000000001);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

		var kq;
		if(sole.length >= 0)
		{
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
		
		//alert(kq);
		return kq;
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
		 var dvth = document.getElementById("dvthId");
		 var nguongoc = document.getElementById("nguongoc");
		 var tiente = document.getElementById("tiente_fk");
		 var nccId = document.getElementById("nccId");
		 var lhh = document.getElementById("loaihh");
		 var error = document.getElementById("Msg");
		 
		 if(dvth.value == "")
		 {
			 error.value="Vui lòng chọn đơn vị thực hiện";
			 dvth.focus();
			 return;
		 }
		 
		 if(nguongoc.value == "")
		 {
		 	nguongoc.focus();
		 	error.value="Vui lòng chọn nguồn gốc hàng hóa";
			return;
		 }
		 
		 if(tiente.value=="")
		 {
		 	tiente.focus();
		 	error.value="Vui lòng chọn loại tiền tệ!";
			return;
		 }
		 
		 if(nccId.value == "")
		 {
			 nccId.focus(); 
			 error.value="Vui lòng chọn nhà cung cấp";
			return;
		 }
		 
		 if(lhh.value=="")
		 {
			 lhh.focus();
		 	error.value="Vui lòng chọn loại loại hàng hóa!";
			return;
		 }
		 
		 if(checkSanPham() ==false)
		 {	
			 error.value="Không có sản phẩm nào được chọn";
			return;
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['dmhForm'].action.value='save';
	     document.forms['dmhForm'].submit();
	 }

	 function checkSanPham()
	 {
		 var masp = document.getElementsByName("masp");
		 for(var hh = 0; hh < masp.length; hh++)
		 {
			if(masp.item(hh).value != "")
			{
				return true;
			}
		 }
		 return false;
	 }
	 
	 function submitform()
	 { 		
		 var dvth = document.getElementById("dvthId");
		 var nguongoc = document.getElementsByName("nguongoc");
		 var tiente = document.getElementById("tiente_fk");
		 var nccId = document.getElementById("nccId");
		 var lhh = document.getElementById("loaihh");
		
		 document.forms['dmhForm'].action.value='submit';
	     document.forms["dmhForm"].submit();
	 }
	 
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dmhForm" method="post" action="">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="1" />
		<input type="hidden" name="id" value="<%= dmhBean.getId() %>" />
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý mua hàng > Hoàn tất đơn mua hàng > Hiển thị</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="../../ErpHoantatdonmuahangSvl?userId=<%=userId%>"> 
					<img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px" longdesc="Quay ve" style="border-style: outset">
				</A> 
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="Msg" rows="1" readonly="readonly" style="width: 100%%"><%=dmhBean.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Đơn mua hàng</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Ngày mua hàng</TD>
								<TD class="plainlabel" valign="top" >
									<input type="text" class="days" id="ngaymuahang" name="ngaymuahang"
											value="<%= dmhBean.getNgaymuahang() %>" maxlength="10" readonly />
								</TD>
								<TD width="5%"class="plainlabel" valign="top" >
								<input type="text" id="sopo" size="5" name="sopo" value="<%=dmhBean.getTrangthaiDuyet()%>" readonly="readonly"/></TD>
		                        <TD width="35%" class="plainlabel" valign="middle" >
                         
        			                 <a href="" id="duyetId" rel="duyet">
	           	 							&nbsp; <img alt="Chi tiết duyệt" src="../images/vitriluu.png"></a>
	           	 		
                          			<DIV id="duyet" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 350px; height:250px; overflow:auto; padding: 4px;">
				                    	<table width="100%" align="center">
				                        <tr>
				                            <th width="100px">Người duyệt</th>
				                            <th width="100px" align="center">Duyệt</th>
				                        </tr>
		                            	<%
			                        		if(rs != null)
			                        		{
			                        			try{
			                        			while(rs.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%= rs.getString("DIENGIAI") %>" readonly=readonly></td>
			                        				<% if(rs.getString("TRANGTHAI").equals("0")){ 
		                        					  if (rs.getString("QUYEN").equals("1")){ // QUYEN == 1 nghia la user login vao duoc quyen duyet PO
			                        						  if( dmhBean.getTrangthaiDuyet().equals("Đã duyệt")){
			                        					  %>	
			                        						     <td align="center">&nbsp;</td>
			                        				<% 	  
			                        						  }else{ %>
			                        							 <td align="center"><input type="checkbox" name="duyetIds" value="<%= rs.getString("CHUCDANH_FK") %>" disabled=disabled></td>  
					                        		<%
					                        				  }	                        						  
			                        						  
			                        					   }else{ %>
			                        						<td align="center">&nbsp;</td>
			                        				<%	  }
			                        				   }else {
			                        					   if (rs.getString("QUYEN").equals("1")){%>			      			                        				   			
			                        						<td align="center"><input type="checkbox" name="duyetIds" checked="checked" value="<%= rs.getString("CHUCDANH_FK") %>" disabled=disabled></td>
			                        						
													<%	   }else{ %>
															<td align="center"><img alt = "Đã duyệt" src="../images/apply.gif"></td>
													<%	   }
			                        				   }
														%>			                        				
													
			                        					
			                        			</tr>
			                        			
			                        		 <% } 
			                        			rs.close(); 
			                        		 
			                        		}catch(java.sql.SQLException e){}
			                        		}%>
				                    	</table>
				                     <div align="left">
				                     	<label style="color:red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('duyet')" onClick="javascript:saveform()">Hoàn tất</a>
				                     </div>
				            		</DIV>                        
                        		</TD>                        
								
							</TR>
							<TR>
								<TD class="plainlabel">Đơn vị thực hiện</TD>
								<TD class="plainlabel" width="280px">
									<select name="dvthId" id="dvthId" >
										<option value=""></option>
										<% if (dvthList != null)
										{
											while (dvthList.next())
											{
												if (dvthList.getString("pk_seq").equals(dmhBean.getDvthId()))
												{
												%>
													<option value="<%=dvthList.getString("pk_seq")%>" selected="selected"><%=dvthList.getString("ten")%></option>
												<% } else { %>
													<option value="<%=dvthList.getString("pk_seq")%>"><%=dvthList.getString("ten")%></option>
										<% } } dvthList.close(); } %>
									</select>
								</TD>	
								<TD class="plainlabel" width="150px">Tính thuế nhập khẩu</TD>
								<TD class="plainlabel">
									<select name="nguongoc" id="nguongoc" onChange="submitform();">
											<option value=""></option>
											<% if (dmhBean.getNguonGocHH().equals("TN")) { %>
												<option value="TN" selected="selected">Không tính thuế</option>
												<option value="NN">Tính thuế</option>
											<% } else if (dmhBean.getNguonGocHH().equals("NN")) { %>
												<option value="TN">Không tính thuế</option>
												<option value="NN" selected="selected">Tính thuế</option>
											<% } else { %>
												<option value="TN">Không tính thuế</option>
												<option value="NN">Tính thuế</option>
											<% } %>
									</select>
								</TD>
							</TR>
							<TR class="plainlabel">
								<TD>Tiền tệ</TD>
								<TD>
									<Select name="tiente_fk" id="tiente_fk" onChange="submitform();">
										<option value=""></option>
										<% if (ttList.size() > 0)
											{
												int size = ttList.size();
												for (int i = 0; i < size; i++)
												{
													ITiente t = (Tiente) ttList.get(i);
													if (dmhBean.getTienTe_FK() != null && dmhBean.getTienTe_FK().equals(t.getId()))
													{  %>
														<option value='<%= t.getId() + " - " + t.getTygiaquydoi()%>' selected="selected"><%= t.getMa() %></option>
													<% } else { %>
														<option value='<%= t.getId() + " - " + t.getTygiaquydoi()%>'><%= t.getMa() %></option>
										<% } } } %>
									</Select>
								</TD>
								<TD class="plainlabel">Mã / Tên nhà cung cấp</TD>
								<TD class="plainlabel">
									<input type="text" id="nccId" name="nccId" value="<%= dmhBean.getNCC() %>" >
								</TD>
							</TR>
							<TR>
								<TD class="plainlabel">Loại hàng hóa</TD>
								<TD class="plainlabel">
									<select name="loaihh" id="loaihh" onChange="submitform();">
									<% if(dmhBean.getLoaihanghoa().equals("1")) { %>
										<option value="1" selected="selected">Tài sản cố định</option>
										<option value="0">Sản phẩm nhập kho</option>
										<option value="2">Chi phí dịch vụ</option>
									<% } else { if(dmhBean.getLoaihanghoa().equals("2")){ %>
										<option value="2"  selected="selected">Chi phí dịch vụ</option>
										<option value="1">Tài sản cố định</option>
										<option value="0">Sản phẩm nhập kho</option>
									<% } else {  %> 
										<option value="0" selected="selected">Sản phẩm nhập kho</option>
										<option value="1">Tài sản cố định</option>
										<option value="2">Chi phí dịch vụ</option>
									<%} } %>
									</select>
								</TD>
								
								<% if(dmhBean.getLoaihanghoa().equals("0")) { %>
									<TD class="plainlabel">Loại sản phẩm</TD>
									<TD class="plainlabel">
											<select name="loaisp" id="loaisp" onChange="submitform();">
												<option value=""></option>
												<% if (loaihhList != null)
												   {
														while (loaihhList.next())
														{
															if (loaihhList.getString("pk_seq").equals(dmhBean.getLoaispId()))
															{ %>
															<option value="<%=loaihhList.getString("pk_seq")%>" selected="selected"><%=loaihhList.getString("ten")%></option>
												<% } else { %>
													<option value="<%=loaihhList.getString("pk_seq")%>"><%=loaihhList.getString("ten")%></option>
												<% } } loaihhList.close(); } %>
											</select>
										
									</TD>
								<% } else { %>
									<td class="plainlabel" colspan="2">&nbsp;</td>
								<% } %>
							</TR>
							<TR>
								<TD class="plainlabel">Tổng tiền chưa VAT</TD>
								<TD class="plainlabel"><input type="text" name="BVAT" id="BVAT" value="<%=dmhBean.getTongtienchuaVat()%>" style="text-align: right;"
									readonly="readonly"> VNĐ</TD>
								<TD class="plainlabel">VAT</TD>
								<TD class="plainlabel"><input type="text" name="VAT" id="VAT" value="<%=dmhBean.getVat()%>" style="text-align: right;"
									onkeypress="return keypress(event);"> %</TD>
							</TR>
							<TR>
								<TD class="plainlabel">Tổng tiền sau VAT</TD>
								<TD class="plainlabel"><input type="text" name="AVAT" id="AVAT" value="<%=dmhBean.getTongtiensauVat()%>" style="text-align: right;"
									readonly="readonly"> VNĐ</TD>
								<TD class="plainlabel">Dung sai (+/-)</TD>
								<TD class="plainlabel"><input type="text" name="dungsai" id="dungsai" value="<%=dmhBean.getDungsai()%>" style="text-align: right;"
									onkeypress="return keypress(event);"> %</TD>
							</TR>
							<TR>
								<TD class="plainlabel">Ghi chú</TD>
								<TD class="plainlabel" colspan="3"><input type="text" name="ghichu" id="ghichu" value="<%= dmhBean.getGhiChu()%>" style="text-align: left;"></TD>
							</TR>
						</TABLE>
						<hr>
					</div>
					
					<%  if(dmhBean.getLoaihanghoa().trim().length() > 0 ) { %>
					
					<div align="left" style="width: 100%; float: none; clear: none;">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1">
							<TR class="tbheader">
								<TH align="center" width="30px">STT</TH>
								
								<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { %>
									<TH align="center" width="10%" >Mã hàng mua</TH>
									<TH align="center" width="15%">Tên hàng mua</TH>
								<% } else { if(dmhBean.getLoaihanghoa().trim().equals("1")) { %> 
									<TH align="center" width="10%" >Mã tài sản</TH>
									<TH align="center" width="15%">Diễn giải</TH>
								<% } else { %>
									<TH align="center" width="10%" >Mã chi phí</TH>
									<TH align="center" width="15%">Diễn giải</TH>
								 <% } } %>
								
								<TH align="center" width="5%">ĐVT</TH>
								<TH align="center" width="5%">Số lượng</TH>
								<TH align="center" width="10%">Đơn giá</TH>
								<TH align="center" width="10%">Thành tiền</TH>
								
								<% if (dmhBean.getNguonGocHH().equals("NN"))
								  { %>
										<TH align="center" width="5%">% TNK</TH>
										<TH align="center" width="5%">Thuế NK</TH>
								<% } else { %>
										<TH align="center" width="5%" style="display: none">% TNK</TH>
										<TH align="center" width="5%" style="display: none">Thuế NK</TH>
								<% } %>

								<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { %>
									<TH align="center" width="10%">Nhóm hàng</TH>
								<% } else { if(dmhBean.getLoaihanghoa().trim().equals("1")) { %> 
									<TH align="center" width="10%">Nhóm tài sản</TH>
								<% } else { %>
									<TH align="center" width="10%">Nhóm chi phí</TH>
								 <% } } %>
								
								<TH align="center" width="5%">Ngày nhận</TH>

								<% if ( dmhBean.getLoaihanghoa().trim().equals("0") )
								   { %>
									<TH align="center" width="5%" >Kho nhận</TH>
								<% } else { %>
									<TH align="center" width="5%" style="display: none" >Kho nhận</TH>
								<% } %>
							</TR>
							
							<% int count = 0; 
								if(spList.size() > 0) { 
									for(int i = 0; i < spList.size(); i++) { 
										ISanpham sp = spList.get(i); %>
										
										<tr>
											<TD align="center" width="2%">
												<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
											</TD>
											<TD align="center" width="8%" >
												<input type="text" name="masp" style="width: 100%" value="<%= sp.getMasanpham() %>"  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
											</TD>
											<TD align="left" width="10%">
												<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { %>
													<input type="text" value="<%= sp.getTensanpham() %>" name="tensp" style="width: 100%" readonly="readonly" > 
												<% } else {  %>
													<input type="text" value="<%= sp.getTensanpham() %>" name="tensp" style="width: 100%" > 
												<%  } %>
											</TD>
											<TD align="center" width="6%">
												<input type="text" value="<%= sp.getDonvitinh() %>" name="donvitinh" style="width: 100%" > 
											</TD>
											<TD align="center" width="7%">
												<input type="text" value="<%= sp.getSoluong() %>" name="soluong" style="width: 100%; text-align: right;" > 
												<input type="hidden" value="<%= sp.getSoluongOLD() %>" name="soluongOld" style="width: 100%; text-align: right;" >
											</TD>
											<TD align="center" width="8%">
												<input type="text" value="<%= sp.getDongia() %>" name="dongia" style="width: 100%; text-align: right;" >
												<input type="hidden" value="<%= sp.getDongiaOLD() %>" name="dongiaOld" style="width: 100%; text-align: right;" > 
											</TD>
											<TD align="center" width="8%">
												<input type="text" value="<%= sp.getThanhtien() %>"  name="thanhtien" style="width: 100%; text-align: right;" > 
											</TD>
											
											<% if (dmhBean.getNguonGocHH().equals("NN"))
											  { %>
													<TD align="center" width="5%">
														<input type="text" value="<%= sp.getPhanTramThue() %>" style="width: 100%; text-align: right;" name="phantramthue" > 
													</TD>
													<TD align="center" width="10%">
														<input type="text" value="<%= sp.getThueNhapKhau() %>" style="width: 100%; text-align: right;" name="thuenhapkhau" > 
													</TD>
											<% } else { %>
													<TD align="center" width="5%" style="display: none">
														<input type="text" value="" style="width: 100%" name="phantramthue" > 
													</TD>
													<TD align="center" width="5%" style="display: none">
														<input type="text" value="" style="width: 100%" name="thuenhapkhau" > 
													</TD>
											<% } %>
											
											<TD align="center" width="8%">
												<input type="text" value="<%= sp.getNhomhang() %>" style="width: 100%" name="nhomhang" readonly="readonly" > 
											</TD>
											<TD align="center" width="7%">
												<input type="text" value="<%= sp.getNgaynhan() %>" style="width: 100%" name="ngaynhan" readonly class="days" > 
											</TD>
			
											<% if ( dmhBean.getLoaihanghoa().trim().equals("0") )
											   { %>
												<TD align="center" width="8%" >
													<select style="width: 100%" name="khonhan">
														<option value=""></option>
														<% if (khoList.size() > 0)
															{
																for (int j = 0; j < khoList.size(); j++)
																{
																	IKho kho = (Kho) khoList.get(j);
																	
																	if(kho.getId().equals(sp.getKhonhan())){
																%>
																	<option value="<%=kho.getId()%>" selected="selected"><%=kho.getMakho()%></option>
																<% } else { %>
																	<option value="<%=kho.getId()%>"><%=kho.getMakho()%></option>
																 <% } } }  %>
													</select>
												</TD>
											<% } else { %>
												<TD align="center" width="8%" style="display: none" >
													<input type="hidden" value=""  name="khonhan"  > 
												</TD>
											<% } %>
										</tr>
										
									<% count++; }
								} %>
							
							<% for(int i = count; i < 40; i++) { %>
							
								<tr>
									<TD align="center" width="2%">
										<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
									</TD>
									<TD align="center" width="8%" >
										<input type="text" value="" name="masp" style="width: 100%" onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
									</TD>
									<TD align="left" width="10%">
									<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { %>
										<input type="text" value="" name="tensp" style="width: 100%" readonly="readonly" > 
									<% } else { %>
										<input type="text" value="" name="tensp" style="width: 100%" > 
									<% } %>
									</TD>
									<TD align="center" width="6%">
										<input type="text" value="" name="donvitinh" style="width: 100%" > 
									</TD>
									<TD align="center" width="7%">
										<input type="text" value="" name="soluong" style="width: 100%; text-align: right;" > 
									</TD>
									<TD align="center" width="8%">
										<input type="text" value="" name="dongia" style="width: 100%; text-align: right;" > 
									</TD>
									<TD align="center" width="8%">
										<input type="text" value=""  name="thanhtien" style="width: 100%; text-align: right;" > 
									</TD>
									
									<% if (dmhBean.getNguonGocHH().equals("NN"))
									  { %>
											<TD align="center" width="5%">
												<input type="text" value="" style="width: 100%; text-align: right;" name="phantramthue" > 
											</TD>
											<TD align="center" width="10%">
												<input type="text" value="" style="width: 100%; text-align: right;" name="thuenhapkhau" > 
											</TD>
									<% } else { %>
											<TD align="center" width="5%" style="display: none">
												<input type="text" value="" style="width: 100%" name="phantramthue" > 
											</TD>
											<TD align="center" width="5%" style="display: none">
												<input type="text" value="" style="width: 100%" name="thuenhapkhau" > 
											</TD>
									<% } %>
									
									<TD align="center" width="8%">
										<input type="text" value="" style="width: 100%" name="nhomhang" readonly="readonly" > 
									</TD>
									<TD align="center" width="7%">
										<input type="text" value="" style="width: 100%" name="ngaynhan" readonly class="days" > 
									</TD>
	
									<% if ( dmhBean.getLoaihanghoa().trim().equals("0") )
									   { %>
										<TD align="center" width="8%" >
											<select style="width: 100%" name="khonhan">
												<option value=""></option>
												<% if (khoList.size() > 0)
													{
														for (int j = 0; j < khoList.size(); j++)
														{
															IKho kho = (Kho) khoList.get(j);
													%>
														<option value="<%=kho.getId()%>"><%=kho.getMakho()%></option>
													<% } }  %>
											</select>
										</TD>
									<% } else { %>
										<TD align="center" width="8%" style="display: none" >
											<input type="hidden" value=""  name="khonhan"  > 
										</TD>
									<% } %>
								</tr>
							<% } %>
							
							
							<tr class="tbfooter">
								<td colspan="12">&nbsp;</td>
							</tr>
						</TABLE>
					</div>
					
					<% } %>
					
				</fieldset>
			</div>
		</div>
<script type="text/javascript">
	replaces();
	dropdowncontent.init("searchlink", "right-bottom", 500, "click");
	jQuery(function()
	{		
		$("#nccId").autocomplete("ErpNhaCungCapList.jsp");
	});	
</script>

<script type="text/javascript">
	dropdowncontent.init('duyetId', "right-bottom", 300, "click");
</script>	


	<%
		dmhBean.close();
	%>
</form>
</BODY>
</html>