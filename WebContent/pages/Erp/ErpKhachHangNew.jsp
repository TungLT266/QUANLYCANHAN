<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.khachhang.*"%>
<%@page import="geso.traphaco.erp.beans.khachhang.imp.*"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %> 

<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SGF/index.jsp");
	}else{ %>

<%
	IErpKhachHang khBean = (IErpKhachHang) session.getAttribute("khBean");
	ResultSet rsTaiKhoan = khBean.getTaiKhoanRs();
	ResultSet rsNganHang = khBean.getNganHangRs();
	ResultSet rsChiNhanh = khBean.getChiNhanhRs();
	ResultSet rsKenh = khBean.getKenhRs();
	ResultSet rsGiamSat = khBean.getGiamSatRs();
	ResultSet rsNhom = khBean.getNhom_KH_Rs();
	ResultSet rsDvkd = khBean.getDvkdRs();
	ResultSet rsChungLoaiSP = khBean.getChungLoaiSPRs();

	ResultSet rsKhuvuc = khBean.getKhuvucrs();
	ResultSet rsTinhThanh = khBean.getTinhthanhrs();
	//ResultSet spList = (ResultSet)khBean.getSpList(); 
	//ResultSet spSelected = (ResultSet)khBean.getSpSelected();
	
	ResultSet spNgc = (ResultSet)khBean.getSpNhanGiaCong(); 
	ResultSet spKhoNL = (ResultSet)khBean.getKhoNlRs();
	ResultSet rs_khodh= khBean.getRS_Khodathang();
	String[] kh_sp_dubaoIds = khBean.getKh_Sp_dubaoIds();
	String[] kh_sp_dubaoMa = khBean.getKh_Sp_dubaoMa();
	String[] kh_sp_dubaoTen = khBean.getKh_Sp_dubaoTen();
	
%>

	<% ResultSet rsloaikh = (ResultSet)khBean.getLoaikhachhangrs() ;  %>
	<% ResultSet rsthuockh = (ResultSet)khBean.getThuockhachhangrs() ;  %>
	<% NumberFormat formatter = new DecimalFormat("#,###,###");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Tạo mới khách hàng</title>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<LINK rel="stylesheet" href="../css/cdtab.css" type="text/css">


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
<script type="text/javascript" src="../scripts/erp-spFilterList.js"></script>

<script type="text/javascript" src="../scripts/ajax_erp_KH_SP_CK.js"></script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script language="javascript" type="text/javascript">
	function xoaform() {
		document.khBeanForm.ma.value = "";
		document.khBeanForm.ten.value = "";
		document.khBeanForm.submit();
	}
	function Submit() {
		document.forms['khBeanForm'].submit();
	}
	
	function formatTien(e)
	{
		var giatrinhap = e.value;
		e.value = DinhDangTien(giatrinhap);
		
	}
	
	function addRow(name)
	{
		
		
		tableName = document.getElementById(name);
		
		var tr = document.createElement("TR");
		var maspAdd = document.createElement("TD");
		var tenspAdd = document.createElement("TD");
		var dvtinhAdd = document.createElement("TD");
		var chietkhauAdd = document.createElement("TD");
		
		
		var idspCK = document.createElement("input");
		idspCK.setAttribute("type", "hidden");
		idspCK.name = 'idspCK';
		maspAdd.appendChild(idspCK);
		
		var masp = document.createElement("input");
		masp.setAttribute("type", "textbox");
		masp.setAttribute("onkeyup", "ajax_showOptions(this,'abc',event)");
		masp.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
		masp.name = 'maspCK';
		maspAdd.appendChild(masp);
		
		var tensp = document.createElement("input");
		tensp.setAttribute("type", "textbox");
		tensp.setAttribute("readonly", "readonly");
		tensp.setAttribute("style"," width:100%;border:1px;	border-style:solid;border-color: #808080;");
		
		tensp.name = 'tenspCK';
		tenspAdd.appendChild(tensp);
		
		
		var dvt = document.createElement("input");
		dvt.setAttribute("type", "textbox");
		dvt.setAttribute("readonly", "readonly");
		dvt.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");
		dvt.name = 'donvitinhCK';
		
		var chietkhau = document.createElement("input");
		chietkhau.setAttribute("type", "textbox");
		chietkhau.setAttribute("readonly", "readonly");
		chietkhau.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
		chietkhau.value = "";
		chietkhau.name = 'ptspCK';
		chietkhauAdd.appendChild(chietkhau);
		
		
		
		tr.appendChild(maspAdd);
		tr.appendChild(tenspAdd);		
		tr.appendChild(dvtinhAdd);
		tr.appendChild(chietkhauAdd);
		
		tableName.appendChild(tr);
	}
	
	function ThemSanPham()
	{
		 var sl = window.prompt("Nhập số lượng sản phẩm muốn thêm", '');
		 if(isNaN(sl) == false && sl < 30)
		 {
			 for(var i=0; i < sl ; i++)
				 addRow("san_pham");
		 }
		 else
		 {
			 alert('Số lượng bạn nhập không hợp lệ. Mọi lần bạn chỉ được thêm tối đa 30 sản phẩm');
			 return;
		 }
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
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
function CheckValid() {
		
	var ma=document.getElementById("ma");
	var ten=document.getElementById("ten");
	var thoihanno=document.getElementById("thoihanno");
	var hanmucno=document.getElementById("hanmucno");
	var mst=document.getElementById("mst");
	var diachi=document.getElementById("diachi");
	var nhom_kh=document.getElementById("nhom_kh");
	var taikhoan=document.getElementById("taikhoan");
	var nganhang=document.getElementById("nganhang");
	var chinhanh=document.getElementById("chinhanh");
	var kenh=document.getElementById("Kenh");
	var nganhang=document.getElementById("nganhang");
	var chinhanh=document.getElementById("chinhanh");
	var giamsat=document.getElementById("giamsat");
	var dvkdIds=document.getElementsByName("dvkdIds");
	var loaikhachhang=document.getElementsByName("loaikhachhang");
	
	var username=document.getElementsByName("username");
	var password=document.getElementsByName("password");
	
	var khuvuc=document.getElementById("khuvucid");
	var tinhthanh=document.getElementById("tinhthanhid");
	var khodhid = document.getElementById("khodhid");
	
	if(ma.value=="")
	{
		ma.focus();
		return 'Vui lòng thêm mã khách hàng ';
	}
	
	if(ten.value=="")
		{
			ten.focus();
			return 'Vui lòng nhập tên khách hàng';
		}
		
	if(diachi.value=="")
	{
		diachi.focus();
		return 'Vui lòng nhập địa chỉ khách hàng ';
	}
	
	if(loaikhachhang.value=="")
	{
		loaikhachhang.focus();
		return 'Vui lòng chọn loại khách hàng ';
	}
	
	if(kenh.value=="")
	{
		kenh.focus();
		return 'Vui lòng chọn kênh ';
	}
		
	if(taikhoan.value=="")
	{
		taikhoan.focus();
		return 'Vui lòng chọn tài khoản ';
	}
		
		
	
/* 	if(nganhang.value=="")
	{
		nganhang.focus();
		return 'Vui lòng chọn ngân hàng';
	}
			
	if(chinhanh.value=="")
	{
		chinhanh.focus();
		return 'Vui lòng chọn chi nhánh';
	} */
			
	if(dvkdIds.value=="")
	{
		dvkdIds.focus();
		return 'Vui lòng chọn đơn vị kinh doanh ';
	}	

	if(khuvuc.value=="")
	{
		khuvuc.focus();
		return 'Vui lòng chọn khu vực ';
	}
	
	if(tinhthanh.value=="")
	{
		tinhthanh.focus();
		return 'Vui lòng chọn tỉnh thành ';
	}
	
	if(khodhid.value=="")
	{
		khodhid.focus();
		return 'Vui lòng chọn kho đặt hàng ';
	}
	
	    return '';
	}

	function Save() {
		
		var msg = document.getElementById("msg");
		msg.value=CheckValid();
		
		if(msg.value==''){
			congDonSPCungMa();
			document.forms['khBeanForm'].action.value = 'Create';
			document.forms['khBeanForm'].submit();
		}else
			{
			return;
			}
		
	}
	
	function checkedAll(chk) {
		for(i=0; i<chk.length; i++){
		 	if(document.khBeanForm.chon.checked==true){
				chk[i].checked = true;
			}else{
				chk[i].checked = false;
			}
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
	
	function replaces()
	{
		var spId = document.getElementsByName("idsanpham");
		var spMa = document.getElementsByName("masanpham");
		var spTen = document.getElementsByName("tensanpham");
		
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
					
					spTen.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					spId.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					
				}
			}
			else
			{
				spMa.item(i).value = "";
				spId.item(i).value = "";
				spTen.item(i).value = "";
			}
		}
		
		var kbhId = document.forms.khBeanForm.Kenh;
		for(x = 0; x < kbhId.length; x++){
			if (kbhId[x].selected)
            {
				//SP CHIET KHAU
				var idspCK = document.getElementsByName("idspCK" + kbhId[x].value);
				var maspCK = document.getElementsByName("maspCK"  + kbhId[x].value);
				var tenspCK = document.getElementsByName("tenspCK"  + kbhId[x].value);
				var dvtCK = document.getElementsByName("donvitinhCK"  + kbhId[x].value);
		
				for(i = 0; i < maspCK.length; i++)
				{
					if(maspCK.item(i).value != "")
					{
						var sp = maspCK.item(i).value;
						
						var pos = parseInt(sp.indexOf(" - "));
				
						if(pos > 0)
						{
							
							maspCK.item(i).value = sp.substring(0, pos);
							sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
					
							pos = parseInt(sp.indexOf(" - "));
							
							tenspCK.item(i).value = sp.substring(0, pos);
							
							sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
					
							dvtCK.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
							sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
							idspCK.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
							
						}
					}
					else
					{
						maspCK.item(i).value = "";
						dvtCK.item(i).value = "";
						idspCK.item(i).value = "";
						tenspCK.item(i).value = "";
					}
				}
            }
		}
		setTimeout(replaces, 500);
	}
	
	function congDonSPCungMa()
	 {
		var idspCK = document.getElementsByName("idspCK");
		var maspCK = document.getElementsByName("maspCK");
		var ptsanphamck = document.getElementsByName("ptspCK");
		
		var ii;
		for(ii = 0; ii < (maspCK.length - 1) ; ii++)
		{
			for( j = ii + 1; j < maspCK.length; j++)
			{
				if(maspCK.item(ii).value != "" && maspCK.item(ii).value == maspCK.item(j).value && idspCK.item(ii).value == idspCK.item(j).value  )
				{	
					//alert("da vao gop san pham");
					if(ptsanphamck.item(j).value == "")
						ptsanphamck.item(j).value = "0";
					
					ptsanphamck.item(ii).value = parseInt(ptsanphamck.item(ii).value) + parseInt(ptsanphamck.item(j).value);
					maspCK.item(j).value = "";
				}
			}
		}
	 }
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="khBeanForm" method="post" action="../../ErpKhachHangUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>' /> 
		<input type="hidden" name="action" value='1' />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kinh doanh &gt; Khách hàng &gt; Tạo mới</TD>
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
										<td width="30" align="left"><a href="../../ErpKhachHangSvl"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
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
					<table width="100%" cellspacing="0" cellpadding="2">
						<tr>
							<td align="left" colspan="4" class="legendtitle">
								<fieldset>
									<legend class="legendtitle">Thông báo </legend>
									<textarea id="msg" name="dataerror" style="width: 100%" readonly="readonly" rows="1"><%=khBean.getMsg()%></textarea>
								</fieldset>
							</td>
						</tr>
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<fieldset>
									<LEGEND class="legendtitle">Khách hàng</LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">
										<tr>
											<td width="15%" class="plainlabel">Mã khách hàng<font class="erroralert">*</font></td>
											<TD class="plainlabel"><input type="text" name="ma" id="ma" value="<%=khBean.getMa()%>" /></TD>
											
											<td class="plainlabel">Số tài khoản ngân hàng  </td>
											<td class="plainlabel"><input type="text" name="sotaikhoan" id="sotaikhoan" value="<%=khBean.getSoTaiKhoan()%>" onkeypress="return keypress(event);" /></td>
											
										</tr>
										<tr>
											<td width="15%" class="plainlabel">Tên xuất hóa đơn</td>
											<td width="15%" class="plainlabel"><input type="text"  name="tenxuathoadon" id="tenxuathoadon" value="<%=khBean.gettenxuathd()%>" /></td>
											<td width="15%" class="plainlabel">Hạn mức đơn hàng tối thiểu:</td>
											<td width="15%" class="plainlabel"><input type="text" name="hanmucdh" id="hanmucdh" value="<%=formatter.format(Double.parseDouble(khBean.getHanmucdh().replaceAll(",", "")))%>" onkeyup="formatTien(this)" onkeypress="return keypress(event);"/></td>
										</tr>
										<tr>
											<td width="15%" class="plainlabel">Tên khách hàng<font class="erroralert">*</font></td>
											<TD class="plainlabel"><input type="text"  name="ten" id="ten" value="<%=khBean.getTen()%>" /></TD>
										
											<td class="plainlabel">Tại Ngân hàng <font class="erroralert">*</font></td>
												<TD class="plainlabel"><select name="nganhang" onchange="Submit()" id="nganhang" style="width: 200px">
														<option value=""></option>
														<%
															if (rsNganHang != null)
																while (rsNganHang.next()) {
																	if (khBean.getNganHang().equals(
																			rsNganHang.getString("PK_SEQ"))) {
														%>
														<option value="<%=rsNganHang.getString("PK_SEQ")%>" selected="selected"><%=rsNganHang.getString("Ten")%></option>
														<%
															} else {
														%>
														<option value="<%=rsNganHang.getString("PK_SEQ")%>"><%=rsNganHang.getString("Ten")%></option>
														<%
															}
																}
														%>
												</select></td>											
										</tr>
										<tr>
											<td class="plainlabel">Địa chỉ khách hàng <font class="erroralert">*</font></td>
											<td class="plainlabel"><input type="text"   name="diachi" id="diachi" value="<%=khBean.getDiaChi()%>" /></td>
											
											<TD class="plainlabel">Thuộc chi nhánh <font class="erroralert">*</font></TD>
											<TD class="plainlabel">
													<select name="chinhanh" id="chinhanh" style="width: 200px">
														<option value=""></option>
														<%
															if (rsChiNhanh != null)
																while (rsChiNhanh.next()) {
																	if (khBean.getChiNhanh().equals(
																			rsChiNhanh.getString("PK_SEQ"))) {
														%>
														<option value="<%=rsChiNhanh.getString("PK_SEQ")%>" selected="selected"><%=rsChiNhanh.getString("Ten")%></option>
														<%
															} else {
														%>
														<option value="<%=rsChiNhanh.getString("PK_SEQ")%>"><%=rsChiNhanh.getString("Ten")%></option>
														<%
															}
																}
														%>
												</select>
											</td>
										</tr>
										
										<TR>
						  		<TD class="plainlabel">Loại khách hàng<font class="erroralert">*</font></TD>
								<TD class="plainlabel">
										<select name="loaikhachhang" id="loaikhachhang" style="width: 200px">
											<option value=""></option>
											<%
												if (rsloaikh != null)
													while (rsloaikh.next()) {
														if (khBean.getLoaikhachhangId().equals(rsloaikh.getString("PK_SEQ"))) {
											%>
											<option value="<%=rsloaikh.getString("PK_SEQ")%>" selected="selected"><%=rsloaikh.getString("Ten")%></option>
											<%
												} else {
											%>
											<option value="<%=rsloaikh.getString("PK_SEQ")%>"><%=rsloaikh.getString("Ten")%></option>
											<%
												}
													}
											%>
									</select>
								</td>
								
								<TD class="plainlabel">Thuộc khách hàng</TD>
								<TD class="plainlabel">
										<select name="thuockhachhang" id="thuockhachhang" style="width: 200px">
											<option value=""></option>
											<%
												if (rsthuockh != null)
													while (rsthuockh.next()) {
														if (khBean.getThuockhachhangId().equals(rsthuockh.getString("PK_SEQ"))) {
											%>
											<option value="<%=rsthuockh.getString("PK_SEQ")%>" selected="selected"><%=rsthuockh.getString("Ten")%></option>
											<%
												} else {
											%>
											<option value="<%=rsthuockh.getString("PK_SEQ")%>"><%=rsthuockh.getString("Ten")%></option>
											<%
												}
													}
											%>
									</select>
								</TD>
						  </TR>
						  
										<tr>
											<td class="plainlabel">Địa chỉ giao hàng 1 </td>
												<td class="plainlabel"><input type="text" name="diachi_gh" id="diachi_gh" value="<%=khBean.getDiaChi_GH()%>" /></td>
											
											<td class="plainlabel">Địa chỉ giao hàng 2 </td>
												<td class="plainlabel"><input type="text" name="diachi_gh2" id="diachi_gh2" value="<%=khBean.getDiaChi_GH2()%>" /></td>
																						
										</tr>
										<tr>											
											<TD class="plainlabel">Số điện thoại khách hàng</TD>
											<td class="plainlabel"><input type="text" name="dienthoai" id="dienthoai" value="<%=khBean.getDienThoai()%>" onkeypress="return keypress(event);" /></td>
											
											<TD class="plainlabel">Số Fax</TD>
											<td class="plainlabel" colspan="3"><input type="text" name="sofax" id="sofax" value="<%=khBean.getSoFax()%>" onkeypress="return keypress(event);" /></td>											
										</tr>
																			
										<TR>
											<td class="plainlabel">Mã số thuế   </td>
											<td class="plainlabel"><input type="text" name="mst" id="mst" value="<%=khBean.getMST()%>"  onkeypress="return keypress(event);"/></td>
											
											<TD class="plainlabel">Tên người liên hệ</TD>
											<td class="plainlabel"><input type="text" name="nguoilienhe" id="nguoilienhe" value="<%=khBean.getNguoiLienHe()%>" /></td>
										</TR>
										
										<TR>
											<td width="15%" class="plainlabel">Kênh bán hàng<font class="erroralert">*</font></td>
											<TD class="plainlabel">
											  <select name="Kenh" id="Kenh" style="width: 200px" multiple size = 4 onChange  = "Submit();">
													<option value=""></option>
													<%
														if (rsKenh != null)
															while (rsKenh.next()) {
																if (khBean.getKenh().contains(rsKenh.getString("PK_SEQ"))) {
													%>
													<option value="<%=rsKenh.getString("PK_SEQ")%>" selected><%=rsKenh.getString("Ten")%></option>
													<%
														} else {
													%>
													<option value="<%=rsKenh.getString("PK_SEQ")%>"><%=rsKenh.getString("Ten")%></option>
													<%
														}
															}
													%>
											</select></td>											
										
											<TD class="plainlabel">Điện thoại & Email người liên hệ</TD>
											<td class="plainlabel"><input type="text" name="dienthoai_nlh" id="dienthoai_nlh" value="<%=khBean.getDienThoai_NLH()%>" /></td>
										
										</TR>
										<TR>
											<td class="plainlabel">Thuộc tài khoản <font class="erroralert">*</font> </td>
												<TD class="plainlabel" ><select name="taikhoan" id="taikhoan" style="width: 200px">
														<option value=""></option>
														<%
															if (rsTaiKhoan != null)
																while (rsTaiKhoan.next()) {
																	if (khBean.getTaiKhoan().equals(
																			rsTaiKhoan.getString("PK_SEQ"))) {
														%>
														<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>" selected="selected"><%=rsTaiKhoan.getString("Ten")%></option>
														<%
															} else {
														%>
														<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>"><%=rsTaiKhoan.getString("Ten")%></option>
														<%
															}
																}
														%>
												</select></td>
												
													<TD class="plainlabel">Đơn vị kinh doanh <font class="erroralert">*</font></TD>
													<td class="plainlabel">											
							                    <table  align="left">							                       
					                            	<%
						                        		if(rsDvkd != null)
						                        		{
						                        			while(rsDvkd.next())
						                        			{  %>						                        			
						                        			<tr>						                        				
						                        				<td><input type="hidden" style="width: 100%" value="<%= rsDvkd.getString("pk_seq") %>" readonly="readonly"  ></td>
						                        				<td><input style="width: 200px" value="<%= rsDvkd.getString("ten") %>" readonly="readonly"  ></td>
						                        				<td align="left">	
						                        					<%
						                        					int test=-1;
						                        					if(khBean.getDvkdIds() != null){
						                        					for(int i = 0; i < khBean.getDvkdIds().length ; i ++){
						                        						if(rsDvkd.getString("pk_seq").equalsIgnoreCase(khBean.getDvkdIds()[i])){
						                        							test=1;						                        							
						                        						}
						                        					} 
						                        					}
						                        					if(test==1){					                        							
						                        					%>
						                        						<input checked="checked" type="checkbox" name="dvkdIds" value="<%= rsDvkd.getString("pk_seq") %>"  onchange="Submit();">	
						                        					<%
						                        					} else {
						                        					%>		                        				
						                        					<input checked="checked" type="checkbox" name="dvkdIds" value="<%= rsDvkd.getString("pk_seq") %>"  onchange="Submit();">
						                        					<% }						                        						
						                        					%>						                        				
						                        				</td>
						                        				
						                        			</tr>
						                        			
						                        		 <% } rsDvkd.close(); } %>
							                    </table> 
										</td>
											<%-- <TD class="plainlabel">Giám sát  </TD>
											<td class="plainlabel"><input type="text" name="giamsat" id="giamsat" value="<%=khBean.getGiamSat()%>" readonly = "readonly"/></td> --%>												
										</TR>
										<TR>
											<TD class="plainlabel">Sản xuất theo đơn đặt hàng</TD>
											<TD class="plainlabel" colspan="3" >
												<% if(khBean.getMakeToStock().equals("0")) { %>
													<input type="checkbox" name="maketoStock" value="0" checked="checked" checked onChange  = "Submit();">
												<% } else { %>
													<input type="checkbox" name="maketoStock" value="0"  onChange  = "Submit();">
												<% } %>
											</td>
										</TR>
										
										<TR>
											<td class="plainlabel">Đơn giá vận chuyển </td>
											<td class="plainlabel"  >
												<input type="text" name="cpvc" id="cpvc" style="text-align: right;" value="<%= khBean.getCPVC() %>" onkeyup="formatTien(this)" />
											</td>
											<TD class="plainlabel">Cho phép sửa giá hóa đơn</TD>
											<TD class="plainlabel">
												<% if(khBean.getChoSuaGiaHD().equals("1")){ %>
												<input type="checkbox" name="chosuagiahoadon" value="1" checked="checked">
												<%}else{ %>
												<input type="checkbox" name="chosuagiahoadon" value="1">
												<%} %> 
											</td>											
										</TR>
										
										<tr>											
											<td width="15%" class="plainlabel">Thuộc nhóm khách hàng<font class="erroralert">*</font></td>
												<TD class="plainlabel"><select name="nhom_kh" id="nhom_kh" onChange  = "Submit();"style="width: 200px">
														<option value=""></option>
														<%
															if (rsNhom != null)
																while (rsNhom.next()) {
																	if (khBean.getNhom_KH().equals(rsNhom.getString("PK_SEQ"))) {
														%>
														<option value="<%=rsNhom.getString("PK_SEQ")%>" selected="selected"><%=rsNhom.getString("Ten")%></option>
														<%
															} else {
														%>
														<option value="<%=rsNhom.getString("PK_SEQ")%>"><%=rsNhom.getString("Ten")%></option>
														<%
															}
																}
														%>
												</select></td>
										
											<TD class="plainlabel">Hoạt động</TD>
											<TD class="plainlabel">
												<input type="checkbox" name="trangthaiHD" value="1" checked="checked"> 
											</TD>
										</TR>
										
								 <TR >
								   	 <TD class="plainlabel">Kho đặt hàng  <FONT class="erroralert"> *</FONT></TD>
									 <TD class="plainlabel" ><SELECT name="khodhid" id="khodhid"  style="width: 200px">
									 	<option value=""></option>
									      <% if(rs_khodh!=null) 
									      try{while(rs_khodh.next()){ 
									    	if(rs_khodh.getString("pk_seq").trim().equals(khBean.getKhodathang().trim())){ %>
									      		<option value='<%=rs_khodh.getString("pk_seq")%>' selected><%=rs_khodh.getString("ten") %></option>
									      	<%}else{ %>
									     		<option value='<%=rs_khodh.getString("pk_seq")%>'><%=rs_khodh.getString("ten") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>	  
	                        				</SELECT>	
	                        		</TD>
	                        		
	                        		<TD class="plainlabel">Khu vực  <FONT class="erroralert"> *</FONT></TD>
									 <TD class="plainlabel" >
									 	<SELECT name="khuvucid" id="khuvucid"  style="width: 200px">									 	   
									 	  <option value=""></option>
									      <% if(rsKhuvuc!=null) 
									      try{while(rsKhuvuc.next()){ 
									    	if(rsKhuvuc.getString("pk_seq").trim().equals(khBean.getKhuvucId().trim())){ %>
									      		<option value='<%=rsKhuvuc.getString("pk_seq")%>' selected><%=rsKhuvuc.getString("ten") %></option>
									      	<%}else{ %>
									     		<option value='<%=rsKhuvuc.getString("pk_seq")%>'><%=rsKhuvuc.getString("ten") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>	  
	                        				</SELECT>	
	                        		</TD>
                        		</TR>
                        		<TR >
								   	 <TD class="plainlabel"> </TD>
									 <TD class="plainlabel" >
									 
	                        		
	                        		<TD class="plainlabel">Tỉnh thành  <FONT class="erroralert"> *</FONT></TD>
									 <TD class="plainlabel" >
									 	<SELECT name="tinhthanhid" id="tinhthanhid"  style="width: 200px">									 	   
									 	  <option value=""></option>
									      <% if(rsTinhThanh!=null) 
									      try{while(rsTinhThanh.next()){ 
									    	if(rsTinhThanh.getString("pk_seq").trim().equals(khBean.getTinhthanhId().trim())){ %>
									      		<option value='<%=rsTinhThanh.getString("pk_seq")%>' selected><%=rsTinhThanh.getString("ten") %></option>
									      	<%}else{ %>
									     		<option value='<%=rsTinhThanh.getString("pk_seq")%>'><%=rsTinhThanh.getString("ten") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>	  
	                        				</SELECT>	
	                        		</TD>
                        		</TR>
                        		
										<% if(khBean.getNhom_KH().equals("100002")) { %>
										<TR>
											
											<td class="plainlabel">Sản phẩm nhận gia công<font class="erroralert">*</font></td>
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
						                        				<% if(khBean.getSpNhangiacongIds().indexOf(spNgc.getString("pk_seq")) >= 0 ){ %>
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
										
											<td class="plainlabel">Kho nguyên liệu<font class="erroralert">*</font></td>
												<TD class="plainlabel"><select name="khoNLId" id="khoNLId">
														<option value=""></option>
														<%
															if (spKhoNL != null)
																while (spKhoNL.next()) {
																	if (khBean.getKhoNlId().equals(spKhoNL.getString("PK_SEQ"))) {
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
																			
										</TR>
										<% } %>
										
										<%if(khBean.getKenh().equals("100000")){ %>
											<tr>
												<td class="plainlabel">Tên đăng nhập <!-- <font class="erroralert">* --></font> </td>
												<td class="plainlabel"><input type="text" name="username" id="username" value="<%=khBean.getUserName()%>" /></td>
											
											<td class="plainlabel">Mật khẩu </td>
												<td class="plainlabel"><input type="text" name="password" id="password" value="<%=khBean.getPassword()%>" /></td>
																						
											</tr>		
										<%} %>
										
									</table>
								</fieldset>
						</TD>
					</TR>
					<TR>			
			<% if(khBean.getMakeToStock().equals("0")){ %>						
					<TD align="left" colspan="4" >
							<fieldset>
								<LEGEND class="legendtitle">Chọn sản phẩm cho dự báo kinh doanh</LEGEND>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR class="tbheader">
									<TH width="5%" align="center" > ID sản phẩm </TH>
									<TH width="24%" align="center" >Mã sản phẩm </TH>
									<TH width="70%" align="center">Tên sản phẩm </TH>
								</TR>
						   		
						   		<% int count = 0;
								   if( kh_sp_dubaoIds != null ) { 
									   for(int i = 0; i < kh_sp_dubaoIds.length; i++) { count++; %>
										   <tr>
										   <td>
							   					<input style="width: 100%"  type="text" readonly="readonly" name="idsanpham" value="<%= kh_sp_dubaoIds[i] %>" >
							   				</td>
						   				   <td>
							   					<input type="text" name="masanpham" style="width: 100%"  onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off" value="<%= kh_sp_dubaoMa[i] %>"  >							   				
							   				</td>
							   				<td>
							   					<input type="text" name="tensanpham" value="<%= kh_sp_dubaoTen[i] %>" style="width: 100%" >
							   				</td>
							   				
							   			</tr>
									 <%  }
								   } %>
						   		
						   		<% for(int i = 0; i < 50; i++) { %>
						   			<tr>
						   			<td>
							   			<input style="width: 100%"  type="text" readonly="readonly" name="idsanpham" value="" >
							   		</td>							   				
					   				<td>
					   					<input type="text" name="masanpham" style="width: 100%"  onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off"  >					   				 
					   				</td>
					   				<td>
					   					<input type="text" name="tensanpham" style="width: 100%" >
					   				</td>
						   				
						   			</tr>
						   		<% } %>
								</TABLE>
							</fieldset>
					</TD>								
					</TR>	
				<%} %>	
						
					</table>
					
<%// Bắt đầu Tab chiết khấu 
	String[] kbhId = khBean.getKenh().split(";");
	String[] hanmuc = khBean.getHanMucNo();
	String[] thoihan = khBean.getThoiHanNo();

	String[] cktt = khBean.getCKtructiep();
	String[] ungck = khBean.getUngck();
	String[] chonck = khBean.getChonChietKhau();
	List<IErpKhachHang_SPCK> spCKlist = (List<IErpKhachHang_SPCK>)khBean.getListSanPhamCK(); 
	List<IErpKhachHang_ChungLoaiSP> chungloaiSP = (List<IErpKhachHang_ChungLoaiSP>)khBean.getListChungLoaiCK();
	Hashtable kbh_cksp = khBean.getKbh_Cksp();
	Hashtable kbh_ckcl = khBean.getKbh_Ckcl();
	
	for(int i = 0; i < kbhId.length; i++){
		String tenkbh = khBean.getTenKenh(kbhId[i]);

		spCKlist = (List<IErpKhachHang_SPCK>)kbh_cksp.get(kbhId[i].trim());
		chungloaiSP = (List<IErpKhachHang_ChungLoaiSP>)kbh_ckcl.get(kbhId[i].trim());
%>
				<TABLE width="100%" cellspacing="0" cellpadding="2">
												
					<TR>
						<TD colspan="3">															
							<DIV id = "tabContaier">
								<DIV style="width:100%; float:left;background:#ddd">
									<div class="titleTab">
									<a  class="active" href = ""><B><%= "Chiết khấu " + tenkbh %></B> </a></div>
								</DIV>
													
								<DIV class="tabDetails">
	   	        					<DIV id="<%= "tab1" + kbhId[i] %>" class="tabContents" >
															
										<TABLE  border="0" cellpadding="6"  cellspacing="0" width="100%">
										<TR>
								  			<TD class="plainlabel" align = "left">Hạn mức nợ</TD>
		                                	<TD class="plainlabel" align = "left">	                                		
		                                		<INPUT name="<%= "hanmuc" + kbhId[i] %>" id="hanmuc" type="text" value="<%= hanmuc == null ? "0" : hanmuc[i].replaceAll(",", "")   %>" onkeypress="return keypress(event);" >
		                                	</TD>
		                                
		                                	<TD class="plainlabel"  align = "left">Thời hạn nợ</TD>
		                                	<TD class="plainlabel"  align = "left">
		                                		<INPUT name="<%= "thoihan" + kbhId[i] %>" id="thoihan" type="text" value="<%= thoihan == null ? "0" : thoihan[i].replaceAll(",", "") %>"  onkeypress="return keypress(event);" >
		                                	</TD>
								  		</TR>
										
										<TR>
								  			<TD class="plainlabel" align = "left">% Chiết khấu trực tiếp</TD>
		                                	<TD class="plainlabel" align = "left">	                                		
		                                		<INPUT name="<%= "cktructiep" + kbhId[i] %>" id="cktructiep" type="text" value="<%= cktt == null ? "0" : cktt[i].replaceAll(",", "") %>" onkeypress="return keypress(event);" >
		                                	</TD>
		                                
		                                	<TD class="plainlabel"  align = "left">% Ứng chiết khấu</TD>
		                                	<TD class="plainlabel"  align = "left">
		                                		<INPUT name="<%= "ungck" + kbhId[i] %>" id="ungck" type="text" value="<%= ungck == null ? "0" : ungck[i].replaceAll(",", "") %>"  onkeypress="return keypress(event);" >
		                                	</TD>
								  		</TR>
										<TR class="tbheader" >
		     	   							
		     	   							<TD class="plainlabel" id="menuchon"  align = "left" >
		     	   							
		     	   		 				<%if(chonck[i].equals("1")) { %>
		     	   							<INPUT type="radio" name="<%= "chonchietkhau" + kbhId[i] %>" value="1" onclick="Submit();" checked>Chiết khấu theo sản phẩm &nbsp;
		     	   						<%} else { %>
		     	   							<INPUT type="radio" name="<%= "chonchietkhau" + kbhId[i] %>" value="1" onclick="Submit();">Chiết khấu theo sản phẩm &nbsp;
		     	   						<%} %>
		     	   			
		     	   							</TD>
		     	   							<TD class="plainlabel" id="menuchon" colspan = "3"  align = "left">
		     	   						<%if(chonck[i].equals("2")) { %>
		     	   							<INPUT type="radio" name="<%= "chonchietkhau" + kbhId[i] %>" value ="2" onclick="Submit();" checked>Chiết khấu theo chủng loại &nbsp;
		     	   						<%} else { %>
		     	   							<INPUT type="radio" name="<%= "chonchietkhau" + kbhId[i] %>" value ="2" onclick="Submit();">Chiết khấu theo chủng loại &nbsp;
		     	   						<%} %>
		     	   			
		     	   							
		     	   							</TD>

										<!-- CHIẾT KHẤU SẢN PHẨM -->
										<% if(chonck[i].equals("1")) {%>
				  							<TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px" >
			       						<%} else { %>
			          						<TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px" style="display: none">
			       						<%} %>
											<TR>
												<TD>
													<TABLE class="tabledetail" width = "100%" border="0" cellpadding="0" cellspacing="1">
													<tbody id="san_pham">
														<TR class="tbheader" >
															<TH width="13%">Mã sản phẩm </TH>
															<TH width="20%">Tên sản phẩm</TH>
															<TH width="7%">Đơn vị tính</TH>
															<TH width="10%">Phần trăm chiết khấu</TH>
														</TR>
							 			<% 
											int m = 0;
							 				
											if(spCKlist != null){
												IErpKhachHang_SPCK sanphamCK;
												int size = spCKlist.size();
								
												while (m < size){
													sanphamCK = spCKlist.get(m); 
												%> 
														<TR class= 'tblightrow' >
															<TD align="left" >
																<input type="hidden" name="stt" value = " <%= m%> " >
																<input value = "<%=sanphamCK.getIdSanPham() %> " name= "<%= "idspCK" + kbhId[i] %>" type='hidden' >
																<input name = "<%= "maspCK"  + kbhId[i] %>" type="text" value=" <%= sanphamCK.getMaSanPham()%> " autocomplete='off'  onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%">
															</TD>
															<TD align="left" >
																<input name="<%= "tenspCK"  + kbhId[i] %>" type="text" readonly value="<%=sanphamCK.getTenSanPham()%>" style="width:100%" >
															</TD>
									
															<TD align = "center" >
								    							<input name="<%= "donvitinhCK"  + kbhId[i] %>" type="text" value="<%= sanphamCK.getDonViTinh() %>" readonly style="width:100%; text-align:center;padding-right: 5">
								    						</TD>								    
								    						<TD align = "center" >
								    							<input name="<%=  "ptspCK"   + kbhId[i] %>" type="text" value="<%= sanphamCK.getPTChietKhau() %>"  style="width:100% ;text-align:right;padding-right: 5">
								    						</TD>
														</TR>
								 				<% m++; 
								 				}
											}
											int soSp=m;
											int max  = m +5;
											while(soSp < max){ 
										%> 
														<TR class= 'tblightrow'>
															<TD align="center" >
																<input type="hidden" name="stt" value = " <%= soSp%> " >
																<input values="" name="<%= "idspCK" + kbhId[i] %>" type='hidden'>
																<input name = "<%= "maspCK" + kbhId[i] %>" type="text" value="" autocomplete='off' onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" >
															</TD>
															<TD align="left" >
																<input name = "<%= "tenspCK" + kbhId[i] %>" type="text" readonly value="" style="width:100%">
															</TD>
															<TD align = "center" >
								    							<input name = "<%= "donvitinhCK" + kbhId[i] %>" type="text" value="" readonly style="width:100%; text-align: center;">
								    						</TD>
								    						<TD align = "center" >
								    							<input name= "<%= "ptspCK" + kbhId[i] %>" type="text" value="" style="width:100%;text-align:right">
								    						</TD>
														</TR>
										<% 
												soSp++;
												m++;
											}
									%>
									
														</tbody>
								
													</TABLE>

											</TD>
							
										</TR>
						
									</TABLE>
							<!-- CHIẾT KHẤU SẢN PHẨM -->
							<% if(chonck[i].equals("2")) {%>
				  					<TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px" >
			       			<%} else { %>
			          				<TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px" style="display: none">
			       			<%} %>
									<TR>
										<TD>

											<TABLE class="tabledetail" width = "100%" border="0" cellpadding="0" cellspacing="1">
											<tbody id="chungloai">
												<TR class="tbheader" >
													<TH width="13%">Mã chủng loại </TH>
													<TH width="13%">Tên chủng loại </TH>
													<TH width="10%">Phần trăm chiết khấu</TH>
												</TR>
							  <% 
							  		m = 0;
									if(chungloaiSP != null){
										IErpKhachHang_ChungLoaiSP clSPDetail;
										int size = chungloaiSP.size();
								
										while (m < size){
											clSPDetail = chungloaiSP.get(m); 
								%> 
												<TR class= 'tblightrow' >
													<TD align="left" >
														<input type="hidden" name="stt" value = " <%= m%> " >
														<input value=" <%=clSPDetail.getIdChungLoai() %> " name= "<%= "idChungLoaiSP" + kbhId[i] %>" type='hidden' >
														<input name= "<%= "maChungLoaiSP" + kbhId[i] %>" type="text" value="  <%=clSPDetail.getMaChungLoai() %>  "  style="width:100%">
													</TD>
													<TD align = "center" >
								    					<input name= "<%= "tenChungLoaiSP" + kbhId[i] %>" type="text" value=" <%= clSPDetail.getTenChungLoai() %> "  style="width:100%">
								    				</TD>							    
								    				<TD align = "center" >
								    					<input name= "<%= "ptCKChungLoai" + kbhId[i] %>" type="text" value=" <%= clSPDetail.getPTChietKhau()  %> "  style="width:100% ;text-align:right;padding-right: 5">
								    				</TD>
												</TR>
								 <%  		m++; 
								 		}
									}%>
											</tbody>
										</TABLE>
									
									</TD>
								</TR>
							</TABLE>
						</TD>
						</TR>
				</table>
										
		</TR>
			                        									
		</TABLE>
				                    						
        </DIV>                        
																	
								</DIV>												
							</TD>
					</TR>
				</TABLE>
<% 	} %>
<%// Kết thúc Tab chiết khấu	%>				
	</form>
	<script type="text/javascript">
	jQuery(function()
	{		
		$("#giamsat").autocomplete("GiamSatList.jsp");
	});	
	
	dropdowncontent.init('spId', "right-top", 300, "click");
	replaces();
</script>
</body>
<%
	if( rsTaiKhoan != null) rsTaiKhoan.close();
	if( rsNganHang != null) rsNganHang.close();
	if( rsChiNhanh != null) rsChiNhanh.close();
	if( rsKenh != null) rsKenh.close();
	if( rsGiamSat != null) rsGiamSat.close();
	if( rsNhom != null) rsNhom.close();
	if( rsKhuvuc != null) rsKhuvuc.close();
	if( rsTinhThanh != null) rsTinhThanh.close();
	if( rsDvkd != null) rsDvkd.close();
	if( rsChungLoaiSP != null) rsChungLoaiSP.close();
	if( spNgc != null) spNgc.close();
	if( spKhoNL != null) spKhoNL.close();
	if( rs_khodh != null) rs_khodh.close();
	if( rsloaikh != null) rsloaikh.close();
	if( rsthuockh != null) rsthuockh.close();
	
	khBean.close();
	session.removeAttribute("khBean");
}
%>
</html>