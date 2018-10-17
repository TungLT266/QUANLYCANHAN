<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.nhacungcap.*"%>
<%@page import="geso.traphaco.erp.beans.nhacungcap.imp.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IErpNhaCungCap nccBean = (IErpNhaCungCap) session.getAttribute("nccBean");

	ResultSet rsLoaiNCC = nccBean.getLoaiNCCRs();
	ResultSet rsTaiKhoan = nccBean.getTaiKhoanRs();
	ResultSet rsNganHang = nccBean.getNganHangRs();
	ResultSet rsChiNhanh = nccBean.getChiNhanhRs();
	ResultSet rsNCC = nccBean.getNhaCungCapRs();
  	ResultSet tkkqRs = nccBean.getTkkqRs();
	
	ResultSet spNgc = (ResultSet)nccBean.getSpNhanGiaCong(); 
	ResultSet spKhoNL = (ResultSet)nccBean.getKhoNlRs();
	ResultSet rsLoaigiamua = nccBean.getLoaigiamuaRs();
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<title>Nhà cung cấp</title>
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="..scripts/jquery-1.js"></script>

<script language="javascript" type="text/javascript">
	function xoaform() {
		document.nccBeanForm.ma.value = "";
		document.nccBeanForm.ten.value = "";
		document.nccBeanForm.submit();
	}
	function Submit() {
		document.forms['nccBeanForm'].submit();
	}
	

	function Save() 
	{
		var ma= document.getElementById('ma');
		var ten = document.getElementById('ten');
		var loai_ncc = document.getElementById('loai_ncc');
		var taikhoan = document.getElementById('taikhoan');
		 if(ma.value == "")
		 {
			 alert("Vui lòng nhập vào mã nhà cung cấp");
			 ma.focus();
			 return;
		 }
		 
		 if(ten.value == "")
		 {
			 alert("Vui lòng nhập vào tên nhà cung cấp");
			 ten.focus();
			 return;
		 }
		 
		 if(loai_ncc.value == "")
		 {
			 alert("Vui lòng chọn loại nhà cung cấp");
			 loai_ncc.focus();
			 return;
		 }
		 
		
		
		document.forms['nccBeanForm'].action.value = 'Create';
		document.forms['nccBeanForm'].submit();
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
	function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var spIds = document.getElementsByName("spNgcIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }

	function Dinhdang(element)
	{
		element.value = DinhDangTien(element.value);
		if(element.value== '' )
		{
			element.value= '';
		}
	}	
	
	function kiemTraMST()
	{
		 var maSoThue = document.getElementById("mst").value;
		 var id = document.getElementById("Id").value;
		 
		if (mst != "")
		{
			var xmlhttp;
			if (window.XMLHttpRequest)
			{
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
				   var resultStr = xmlhttp.responseText;
				   if (resultStr != "")
				   document.getElementById("msg").value = "'Mã số thuế' bị trùng với nhà cung cấp: " + xmlhttp.responseText;
			   }
			};
			xmlhttp.open("POST","../../AjaxKiemTraMaSoThueSvl?maSoThue=" + maSoThue + "&id=" + id, true);
			xmlhttp.send();
		}
	}	
	
	
	function replaces()
	{
		var TenNh =  document.getElementsByName("TenNh");
		var NhId =  document.getElementsByName("NhId");
	
			if(TenNh.item(0).value != "" )
			{
				var temp = TenNh.item(0).value;
				var pos = parseInt(temp.indexOf(" - "));
				if(pos > 0 && temp.indexOf(" [ ")>0)
				{
					/// PK_SEQ VÀ nppTEN phân cách bằng ' - ', mã và tên phân cách bằng ' -- '
					
					NhId.item(0).value = temp.substring(0, pos);

					temp = temp.substr(parseInt(temp.indexOf(" [ ")) + 3);
					TenNh.item(0).value = temp.substring(0, parseInt(temp.indexOf(" ]")));
					
				}
			}
			else
			{
				NhId.item(0).value = "";
				TenNh.item(0).value = "";
			}
			
			
			var TenCN =  document.getElementsByName("TenCN");
			var CNId =  document.getElementsByName("CNId");
		
				if(TenCN.item(0).value != "" )
				{
					var temp = TenCN.item(0).value;
					var pos = parseInt(temp.indexOf(" - "));
					if(pos > 0 && temp.indexOf(" [ ")>0)
					{
						/// PK_SEQ VÀ nppTEN phân cách bằng ' - ', mã và tên phân cách bằng ' -- '
						
						CNId.item(0).value = temp.substring(0, pos);

						temp = temp.substr(parseInt(temp.indexOf(" [ ")) + 3);
						TenCN.item(0).value = temp.substring(0, parseInt(temp.indexOf(" ]")));
					
					}
				}
				else
				{
					CNId.item(0).value = "";
					TenCN.item(0).value = "";
				}
			
			setTimeout(replaces, 500);
	}
	
	function xoaCnid() {
		var CNId =  document.getElementsByName("CNId");
		CNId.item(0).value="";
	}
	function xoaNhid() {
		var NhId =  document.getElementsByName("NhId");
		NhId.item(0).value="";
	}
	
	
	
	
	function backButton() 
	{
		
		
		
		document.forms['nccBeanForm'].action.value = 'back';
		document.forms['nccBeanForm'].submit();
	}
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="nccBeanForm" method="post" action="../../ErpNhaCungCapUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>' /> 
		<input type="hidden" name="Id" id="Id" value='' />
		<input type="hidden" name="action" value='1' />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng &gt; Nhà cung cấp &gt; Tạo mới</TD>
										<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%> &nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="3" cellspacing="0">
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr class="tbdarkrow">
										<td width="30" align="left"><a href="javascript:backButton();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset">
										</a></td>
										<td width="2" align="left"></td>
										<td width="30" align="left"><a href="javascript: Save()"><img src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1"
												style="border-style: outset"></a></td>
										<td>&nbsp;</td>
										
									</tr>
								</table>
							</td>
						</tr>
					</table>
					
					<table width="100%" cellspacing="0" cellpadding="6">
						<tr>
							<td align="left" colspan="4" class="legendtitle">
								<fieldset>
									<legend class="legendtitle">Thông báo </legend>
									<textarea id="msg" name="dataerror" style="width: 100%" readonly="readonly" rows="1"><%=nccBean.getMsg()%></textarea>
								</fieldset>
							</td>
						</tr>
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<fieldset>
									<LEGEND class="legendtitle">Nhà cung cấp</LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">
										<tr>
											<td width="15%" class="plainlabel">Mã nhà cung cấp<font class="erroralert">*</font></td>
											<TD class="plainlabel"><input type="text" name="ma" id="ma" value="<%=nccBean.getMa()%>" /></TD>
											
											<TD class="plainlabel">Tên người mua hàng</TD>
											<td class="plainlabel"><input type="text" name="nguoimuahang" id="nguoimuahang" value="<%=nccBean.getNguoiMuaHang()%>" /></td>
										
										</tr>
										<tr>
											<td width="15%" class="plainlabel" width = "15%">Tên nhà cung cấp<font class="erroralert">*</font></td>
											<TD class="plainlabel" ><input type="text" name="ten" id="ten" value="<%=nccBean.getTen()%>" /></TD>
										
											<td class="plainlabel">Tại Ngân hàng</td>
											<TD class="plainlabel">
		                    						 <input type="text" id="TenNh" name="TenNh"  onkeypress="xoaNhid();" value = "<%= nccBean.getNhTen() %>" style="width: 300px"  >  
		                    						 <input type="hidden" id="NhId" name="NhId" onChange="ChangeTienTe();" value = "<%= nccBean.getNhId() %>" style="width: 300px"  >
		                    								 </TD>
													
												
										
											
										</tr>
										<tr>
										<td class="plainlabel">Địa chỉ nhà cung cấp</td>
											<td class="plainlabel"><input type="text" name="diachi_ncc" id="diachi_ncc" value="<%=nccBean.getDiaChi_NCC()%>" /></td>
											
											<TD class="plainlabel">Thuộc chi nhánh</TD>
										
											<TD class="plainlabel">
											
											 <input type="text" id="TenCN" name="TenCN"  onkeypress="xoaCnid();" value="<%= nccBean.getTenCN() %>" style="width: 300px"  >  
		                    						 <input type="hidden" id="CNId" name="CNId" value = "<%= nccBean.getCNId() %>" style="width: 300px"  >
		                    						
											
											
<!-- 													<select name="chinhanh" id="chinhanh" style="width: 200px"> -->
<!-- 														<option value=""></option> -->
<%-- 														<%if (rsChiNhanh != null) --%>
<!-- 															while (rsChiNhanh.next()) { -->
<!-- 																if (nccBean.getChiNhanh().equals( -->
<!-- 																		rsChiNhanh.getString("PK_SEQ"))) {%> -->
<%-- 														<option value="<%=rsChiNhanh.getString("PK_SEQ")%>" selected="selected"><%=rsChiNhanh.getString("Ten")%></option> --%>
<%-- 														<%} else {%> --%>
<%-- 														<option value="<%=rsChiNhanh.getString("PK_SEQ")%>"><%=rsChiNhanh.getString("Ten")%></option> --%>
<%-- 														<%} --%>
<!-- 														}%> -->
<!-- 												</select> -->
											</td>
											
										</tr>
										<tr>
											<TD class="plainlabel">Điện thoại nhà cung cấp</TD>
											<td class="plainlabel"><input type="text" name="dienthoai_ncc" id="dienthoai_ncc" value="<%=nccBean.getDienThoai_NCC()%>" /></td>
																				
											<td class="plainlabel">Số tài khoản ngân hàng</td>
											<td class="plainlabel"><input type="text" name="sotaikhoan" id="sotaikhoan" value="<%=nccBean.getSoTaiKhoan()%>" /></td>
										
										</tr>
										<tr>
											<td class="plainlabel">Mã số thuế</td>
											<td class="plainlabel"><input type="text" name="mst" id="mst" value="<%=nccBean.getMST()%>" onchange="kiemTraMST();"/></td>
										
											<td class="plainlabel">Tài khoản ghi nhận công nợ <font class="erroralert">*</font></td>
											<TD class="plainlabel">
												<select name="taikhoan" id="taikhoan" style="width: 200px">
													<option value=""></option>
													<%if (rsTaiKhoan != null)
														while (rsTaiKhoan.next()) {
															if (nccBean.getTaiKhoan().equals(
																	rsTaiKhoan.getString("soHieuTaiKhoan"))) {%>
													<option value="<%=rsTaiKhoan.getString("soHieuTaiKhoan")%>" selected="selected"><%=rsTaiKhoan.getString("Ma")%>  -  <%=rsTaiKhoan.getString("Ten")%></option>
													<%} else {%>
													<option value="<%=rsTaiKhoan.getString("soHieuTaiKhoan")%>"><%=rsTaiKhoan.getString("Ma")%>  -  <%=rsTaiKhoan.getString("Ten")%></option>
													<%}
														}%>
												</select>
											</td>
										</tr>
										<TR>
											<TD class="plainlabel">Tên người liên hệ</TD>
											<td class="plainlabel"><input type="text" name="nguoilienhe" id="nguoilienhe" value="<%=nccBean.getNguoiLienHe()%>" /></td>
										
											<TD class="plainlabel">Thời hạn nợ</TD>
											<td class="plainlabel"><input type="text" name="thoihanno" id="thoihanno" value="<%=nccBean.getThoiHanNo()%>" /></td>
											
											
										</TR>
										
										<TR>
											<TD class="plainlabel">Điện thoại người liên hệ</TD>
											<td class="plainlabel"><input type="text" name="dienthoai_nlh" id="dienthoai_nlh" value="<%=nccBean.getDienThoai_NLH()%>" /></td>
											
											<TD class="plainlabel">Hạn mức nợ</TD>
											<td class="plainlabel"><input type="text" name="hanmucno" id="hanmucno" value="<%=nccBean.getHanMucNo()%>" /></td>
											
										</TR>
										<TR>
											<TD class="plainlabel">Email người liên hệ</TD>
											<td class="plainlabel"><input type="text" name="email_nlh" id="email_nlh" value="<%=nccBean.getEmail_NLH()%>" /></td>	
											
											<TD class="plainlabel">Hoạt động</TD>
											<TD class="plainlabel">
											<% if(nccBean.getTrangThai().equals("1")) { %>
												<input type="checkbox" name="trangthai" value="1" checked = "checked">
											<% } else { %>
												<input type="checkbox" name="trangthai" value="1" >
											<% } %>
											</td>
										</TR>
										<TR>
											<td class="plainlabel">Loại giá mua</td>
											<TD class="plainlabel" >
												<select name="loaigiamua"  style="width: 200px">
													<option value=""></option>
													<%if (rsLoaigiamua != null)
														while (rsLoaigiamua.next()) {
															if (nccBean.getLoaigiamua().equals(
																	rsLoaigiamua.getString("DIENGIAI"))) {%>
														<option value="<%=rsLoaigiamua.getString("DIENGIAI")%>" selected="selected"><%=rsLoaigiamua.getString("DIENGIAI")%></option>
														<%} else {%>
														<option value="<%=rsLoaigiamua.getString("DIENGIAI")%>"><%=rsLoaigiamua.getString("DIENGIAI")%></option>
														<%}
														}%>
												</select>
											</td>
											
											<TD class="plainlabel">Quản lý công nợ</TD>
											<TD class="plainlabel">
											<% if(nccBean.getQuanlycongno().equals("1")) { %>
												<input type="checkbox" name="qlcongno" value="1" checked = "checked">
											<% } else {  %>
												<input type="checkbox" name="qlcongno" value="1" >
											<% } %>
											</td>
										</TR>
										
										
										<tr class="plainlabel">
											<TD > Mức tín dụng</TD>
											<TD >
												<input type="text" name="muctindung" onkeyup="Dinhdang(this);" value="<%=nccBean.getMucTinDung()%>">
											</TD>
								
											<TD  >Mua hàng khu chế xuất</TD>
											<TD ><% if(nccBean.getIs_khuchexuat().equals("1")){ %>
												<input type="checkbox" name="is_khuchexuat" value="1" checked="checked" >
												<%} else{ %>
												<input type="checkbox" name="is_khuchexuat" value="1">
												<%} %>
											</TD>
										</tr>
										
										
										<tr>
										
											<td class="plainlabel">Loại nhà cung cấp<font class="erroralert">*</font></td>
											<TD class="plainlabel" colspan="1" >
												<select name="loai_ncc" id="loai_ncc"  style="width: 200px" >
													
													<%if (rsLoaiNCC != null)
														while (rsLoaiNCC.next()) {
															if (nccBean.getLoaiNCC().indexOf(
																	rsLoaiNCC.getString("PK_SEQ")) > 0) {%>
														<option value="<%=rsLoaiNCC.getString("PK_SEQ")%>" selected="selected"><%=rsLoaiNCC.getString("Ten")%></option>
														<%} else {%>
														<option value="<%=rsLoaiNCC.getString("PK_SEQ")%>"><%=rsLoaiNCC.getString("Ten")%></option>
														<%}
														}%>
												</select>
											</td>
											
											<TD class="plainlabel">Số Fax</TD>
											<td class="plainlabel"><input type="text" name="fax_num" id="fax_num" value="<%=nccBean.getFax()%>" /></td>	
										</tr>
										
										<TR>
										  <TD  class="plainlabel" >Tài khoản ký quỹ</TD>
										  <TD  class="plainlabel">
										 	<SELECT class="select2" name = "tkkqId" style="width:200px">
												<OPTION VALUE = "">&nbsp;</OPTION>
										<%
										if (tkkqRs != null){
												while(tkkqRs.next()){
													if(tkkqRs.getString("soHieuTaiKhoan").equals(nccBean.getTkkqId())){
											%>																	
														<OPTION VALUE = "<%= tkkqRs.getString("soHieuTaiKhoan") %>" SELECTED><%= tkkqRs.getString("TAIKHOANKT")%></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%=tkkqRs.getString("soHieuTaiKhoan") %>" ><%= tkkqRs.getString("TAIKHOANKT")%></OPTION>
										<% 			}
												}
											}%>
											
											</SELECT>
										  </TD>
										  
										  <TD class="plainlabel" valign="top" width="150px"> Công ty / Chi nhánh</TD>
										  <TD class="plainlabel" valign="top">
										  	<select name="nppId" id="nppId" style="width: 500px;">
										  		<option value="0">All</option>
										<% if (nccBean.getNppList() != null)
										{
											for (Erp_Item item : nccBean.getNppList())
											{
												if (item.getValue().equals(nccBean.getNppId()))
												{
												%>
													<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
												<% } else { %>
													<option value="<%=item.getValue()%>"><%=item.getName()%></option>
										<% } }} %>
										  	</select>
										  </TD>
								
										</TR> 
										
										
										<% if(nccBean.getLoaiNCC().equals("100003")) { %>
										<TR>
											<td class="plainlabel">Kho nguyên liệu<font class="erroralert">*</font></td>
												<TD class="plainlabel"><select name="khoNLId" id="khoNLId" style="width: 200px">
														<option value=""></option>
														<%
															if (spKhoNL != null)
																while (spKhoNL.next()) {
																	if (nccBean.getKhoNlId().equals(spKhoNL.getString("PK_SEQ"))) {
														%>
														<option value="<%=spKhoNL.getString("PK_SEQ")%>" selected="selected"><%=spKhoNL.getString("Ten")%></option>
														<%
															} else {
														%>
														<option value="<%=spKhoNL.getString("PK_SEQ")%>"><%=spKhoNL.getString("Ten")%></option>
														<%
															}
																}
														%>
												</select>
											</td>
											
											<td class="plainlabel">Nguyên liệu nhận gia công<font class="erroralert">*</font></td>
											<TD class="plainlabel">
											
												<a href="" id="spId" rel="subcontentSp">
	           	 										&nbsp; <img alt="Thông tin sản phẩm" src="../images/vitriluu.png"></a>
	           	 								
	           	 								<DIV id="subcontentSp" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
							                             background-color: white; width: 590px; max-height:300px; overflow-y:scroll; padding: 4px;">
							                    <table width="90%" align="center">
							                        <tr>
							                            <th width="100px">Mã nguyên liệu</th>
							                            <th width="350px">Tên nguyên liệu</th>
							                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll()" id="chkAll" ></th>
							                        </tr>
					                            	<%
						                        		if(spNgc != null)
						                        		{
						                        			while(spNgc.next())
						                        			{  %>
						                        			
						                        			<tr>
						                        				<td><input style="width: 100%" value="<%= spNgc.getString("ma") %>"></td>
						                        				<td><input style="width: 100%" value="<%= spNgc.getString("spTen") %>"></td>
						                        				<td align="center">
						                        				<% if(nccBean.getSpNhangiacongIds().indexOf(spNgc.getString("pk_seq")) >= 0 ){ %>
						                        					<input type="checkbox" name="spNgcIds" checked="checked" value="<%= spNgc.getString("pk_seq") %>">
						                        				<%}else{ %>
						                        					<input type="checkbox" name="spNgcIds" value="<%= spNgc.getString("pk_seq") %>">
						                        				<%} %>
						                        				</td>
						                        			</tr>
						                        			
						                        		 <% } spNgc.close(); } %>
							                    </table>
							                     <div align="right">
							                     	<label style="color: red" ></label>
							                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                     	<a href="javascript:dropdowncontent.hidediv('subcontentSp')">Hoàn tất</a>
							                     </div>
							            	</DIV> 
	           	 								
											
											</td>
																		
										</TR>
										<% } else { if( nccBean.getLoaiNCC().equals("100004") ) { %>
											<TR>
												<td class="plainlabel">Kho nguyên liệu<font class="erroralert">*</font></td>
												<TD class="plainlabel" >
													<select name="khoNLId" id="khoNLId" style="width: 200px">
														<option value=""></option>
														<%
															if (spKhoNL != null)
																while (spKhoNL.next()) {
																	if (nccBean.getKhoNlId().equals(spKhoNL.getString("PK_SEQ"))) {
														%>
														<option value="<%=spKhoNL.getString("PK_SEQ")%>" selected="selected"><%=spKhoNL.getString("Ten")%></option>
														<%
															} else {
														%>
														<option value="<%=spKhoNL.getString("PK_SEQ")%>"><%=spKhoNL.getString("Ten")%></option>
														<%
															}
																}
														%>
													</select>
												</td>
												
												<td class="plainlabel">Sản phẩm nhờ gia công<font class="erroralert">*</font></td>
												<TD class="plainlabel">
												
													<a href="" id="spId" rel="subcontentSp">
		           	 										&nbsp; <img alt="Thông tin sản phẩm" src="../images/vitriluu.png"></a>
		           	 								
		           	 								<DIV id="subcontentSp" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
								                             background-color: white; width: 590px; max-height:300px; overflow-y:scroll; padding: 4px;">
								                    <table width="90%" align="center">
								                        <tr>
								                            <th width="100px">Mã sản phẩm</th>
								                            <th width="350px">Tên sản phẩm</th>
								                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll()" id="chkAll" ></th>
								                        </tr>
						                            	<%
							                        		if(spNgc != null)
							                        		{
							                        			while(spNgc.next())
							                        			{  %>
							                        			
							                        			<tr>
							                        				<td><input style="width: 100%" value="<%= spNgc.getString("ma") %>"></td>
							                        				<td><input style="width: 100%" value="<%= spNgc.getString("spTen") %>"></td>
							                        				<td align="center">
							                        				<% if(nccBean.getSpNhangiacongIds().indexOf(spNgc.getString("pk_seq")) >= 0 ){ %>
							                        					<input type="checkbox" name="spNgcIds" checked="checked" value="<%= spNgc.getString("pk_seq") %>">
							                        				<%}else{ %>
							                        					<input type="checkbox" name="spNgcIds" value="<%= spNgc.getString("pk_seq") %>">
							                        				<%} %>
							                        				</td>
							                        			</tr>
							                        			
							                        		 <% } spNgc.close(); } %>
								                    </table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentSp')">Hoàn tất</a>
								                     </div>
								            	</DIV> 
		           	 								
												
												</td>
											</TR>
										<% } } %>
										
										
									</table>
									</fieldset>
					</table>
				</td>
			</tr>
		</table>
	</form>

<script type="text/javascript">
	
	dropdowncontent.init('spId', "left-top", 300, "click");

</script>	
<script type="text/javascript">
	replaces();
	
	
</script>


<script type="text/javascript">
	jQuery(function()
	{		
		$("#TenNh").autocomplete("ErpNganHangHOList.jsp");
		$("#TenCN").autocomplete("ErpChiNhanhHOList.jsp");
	});	

</script>

	
</body>
</html>
<%
	
	if( rsLoaiNCC != null) rsLoaiNCC.close();
	if( rsTaiKhoan != null) rsTaiKhoan.close();
	if( rsNganHang != null) rsNganHang.close();
	if( rsChiNhanh != null) rsChiNhanh.close();
	if( rsNCC != null) rsNCC.close();
	if( spKhoNL != null) spKhoNL.close();
	if( spNgc != null) spNgc.close();
	nccBean.close();
	}
%>