<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.dutoanvattu.*"%>
<%@ page import="geso.traphaco.erp.beans.dutoanvattu.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%
IErpDutoanvattu dmhBean = (IErpDutoanvattu) session.getAttribute("dmhBean");
ResultSet dvthList = dmhBean.getDvthList();
ResultSet dnmhList = dmhBean.getDmhList();
ResultSet timnccList = dmhBean.getTimNCCList();
ResultSet loaihhList = dmhBean.getLoaiList();

List<IDonvi> dvList = dmhBean.getDvList();
/* ResultSet ttList = dmhBean.getTienteList(); */

List<ITiente> ttList = dmhBean.getTienteList();

List<INhacungcap> nccList = dmhBean.getNhacungcapList();
String userId = (String) session.getAttribute("userId");
String userTen = (String) session.getAttribute("userTen");

	int[] quyen = new  int[5];
	int[] quyen1 = new  int[5];
	Utility util = (Utility) session.getAttribute("util");
	quyen = util.Getquyen("","57",userId);
	//nếu có quyền sửa duyệt mua hàng thì được phép ghõ mã chi phí.
	quyen1 = util.Getquyen("","58",userId);
	
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
	var dnmhId = document.getElementById("dnmhId").value;
	
	if(dnmhId.value != '')
	{
		var nccid = document.getElementsByName("nccid");
		
		var bvat = document.getElementsByName("bvat");
		var vat = document.getElementsByName("vat");
		var avat = document.getElementsByName("avat");
		
		
		var tiente_fk = document.getElementById("tiente_fk").value;
		console.log(" loai tien te :" + tiente_fk);
		var tygia = document.getElementById("tygiaid").value;
		var tienviettruocvat = document.getElementById("tienviettruocvat").value;
		var tienvietsauvat= document.getElementById("tienvietsauvat").value;
		var tongtiendonghang_truocvat=0;
		var tongtiendonghang_sauvat=0;
		
		
		var VNDbvat = document.getElementsByName("VNDbvat");
		var VNDavat = document.getElementsByName("VNDavat");
		
		for(var i = 0; i < nccid.length ; i ++)
		{
			var masp = document.getElementsByName("masp" + i);
			
			var soluong = document.getElementsByName("soluong" + i);
			
			var dongia = document.getElementsByName("dongia" + i);
			
			var thanhtien = document.getElementsByName("thanhtien" + i);
			
			var tongtien = 0;
		
				
			for(var j = 0; j < masp.length; j ++)
			{
				var sl = soluong.item(j).value.replace(/\$|\,/g,''); 
				var dg = dongia.item(j).value.replace(/\$|\,/g,'') ; 
				
				if(sl == '') sl = '0';
				if(dg == '') dg = '0';
				
				if(parseFloat(sl) > 0 && parseFloat(dg) > 0)
				{
					tt = Math.round(sl*dg);
					/* dongia.item(j).value = DinhDangTien(dg) ; */
					/* dongia.item(j).value = DinhDangDonGia_4sole(dg) ;
					thanhtien.item(j).value = DinhDangTien(tt) ; */
					
					 if( tiente_fk =='100000'){  //tien viet
							dongia.item(j).value = DinhDangDonGia_4sole(dg) ;
							thanhtien.item(j).value = DinhDangTien(tt) ;
						}
							
						else{
							dongia.item(j).value = DinhDangDonGia(dg) ; 
							thanhtien.item(j).value = DinhDangTien(tt) ;
						} 
					
					tongtien = tongtien + parseFloat(tt);
					
					
				}
			}
			
			bvat.item(i).value = DinhDangTien(tongtien);
			avat.item(i).value = DinhDangTien(tongtien + Math.round(parseFloat(tongtien*vat.item(i).value/100)) );
			

			tongtiendonghang_truocvat+=tongtien;
			tongtiendonghang_sauvat +=tongtien + Math.round(parseFloat(tongtien*vat.item(i).value/100));
			
			 //tong tien viet tren tung ncc
			 VNDbvat.item(i).value = DinhDangTien(tongtien *tygia);
			 VNDavat.item(i).value = DinhDangTien( (tongtien + Math.round(parseFloat(tongtien*vat.item(i).value/100)))*tygia );
		
			
		}
		
		document.getElementById("tienviettruocvat").value = DinhDangTien(tongtiendonghang_truocvat * tygia);
		document.getElementById("tienvietsauvat").value = DinhDangTien( tongtiendonghang_sauvat * tygia) ;
	}
	
	setTimeout(replaces, 500);		
}
	
	function FormatNumber(e)
	{
		e.value = DinhDangDonGia(e.value);
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
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
		num = Math.floor(num*100);
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
		 
		 var nccid = document.getElementsByName("nccid");
		 for(var i = 0; i < nccid.length ; i ++)
			{
			 var masp = document.getElementsByName("masp" + i);
			 var dongia = document.getElementsByName("dongia" + i);
				for(var j = 0; j < dongia.length; j ++)
				{
				
					var dg = dongia.item(j).value.replace(/\$|\,/g,'') ; 
		
					if(dg == '') dg = '0';
					
					if( parseFloat(dg)== 0)
						{
						 console.log (" con thu khung ");
						 alert("Vui lòng vui nhập đơn giá vật tư");
						 error.value="Vui lòng vui nhập đơn giá vật tư";
						 dongia.focus();
						 return;
						}
				}		
		}
		 
		 
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['dmhForm'].action.value='save';
	     document.forms['dmhForm'].submit();
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
	 
	 function changeNoiDung()
	 {
		 document.forms['dmhForm'].action.value='changeSP';
	     document.forms["dmhForm"].submit();
	 }
	 
	 function goBack()
	 {
	  	window.history.back();
	 }

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	 function changeNoiDia()
	 {
		document.forms['dmhForm'].nccId.value= "";
		document.forms['dmhForm'].action.value='checkedNoiDia';
		document.forms["dmhForm"].submit();
		
	 }
	 
	 function addRow(pos)
	{
		var table = $('#nn_'+pos);
		table.append(
	    	'<tr>'+
	       		'<td> <input type="text" name="sub_ngaynhan_'+pos+'" class="days"> </td>'+
				'<td> <input type="text" name="sub_soluong_'+pos+'"> </td>'+
	    	'</tr>'
	   			);
	}
	
	function ThemNgay(pos)
	{
		 var sl = window.prompt("Nhấp số lượng ngày muốn thêm", '');
		 if(isNaN(sl) == false && sl < 30)
		 {
			 for(var i=0; i < sl ; i++)
				 addRow(pos);
			 $( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
		 }
		 else
		 {
			 alert('Số lượng bạn nhập không hợp lệ. Mọi lần bạn chỉ được thêm tối đa ngày');
			 return;
		 }
	 }
	
	
	function FormatNumber4sole(e)
	{
		e.value = DinhDangDonGia_4sole(e.value);
	}
	function DinhDangDonGia_4sole(num) 
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
		num = Math.floor(num*100);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

		var kq;
		
		
		
		if(sole.length >= 0)
		{
			sole = sole.substring(0,5);
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
		
		//alert(kq);
		return kq;
	}
	function FormatNumber(e)
	{
		e.value = DinhDangDonGia(e.value);
	}
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dmhForm" method="post" action="../../ErpDutoanvattuUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="1" />
		<input type="hidden" name="id" value="<%= dmhBean.getId() %>" />
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation"> Quản lý mua hàng > Dự toán vật tư > Cập nhật</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="javascript:goBack();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px"
					longdesc="Quay ve" style="border-style: outset"></A> <span id="btnSave"> 
				<A href="javascript:saveform();"> <IMG
						src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset"></A>
				</span>									
				<A href="../../ErpDutoanvattuUpdateSvl?userId=<%=userId %>&id=<%= dmhBean.getId() %>&task=print" >
						<img src="../images/Printer30.png" alt="Print"  title="Print" border="1" longdesc="Print" style="border-style:outset"></A>
									
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="Msg" rows="1" readonly="readonly" style="width: 100%%"><%=dmhBean.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Dự toán vật tư</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Ngày dự toán</TD>
								<TD class="plainlabel" valign="top" >
									<input type="text" class="days" id="ngaymuahang" name="ngaymuahang"
											value="<%= dmhBean.getNgaydutoan() %>" maxlength="10" readonly />
								</TD>																
								<TD class="plainlabel" valign="top">Tiền tệ</TD>
								<%-- <TD TD class="plainlabel" valign="top">
								 <input type="hidden" name = "tigia" value = <%= dmhBean.GetTyGiaQuyDoi() %>>
									<Select name="tiente_fk" id="tiente_fk" onChange="submitform();" style="width: 200px">
										<option value=""></option>
										<% if (ttList != null)
										{
											while (ttList.next())
											{
												if (ttList.getString("pk_seq").equals(dmhBean.getTienTe_FK()))
												{
												%>
													<option value="<%=ttList.getString("pk_seq")%>" selected="selected"><%=ttList.getString("ten")%></option>
												<% } else { %>
													<option value="<%=ttList.getString("pk_seq")%>"><%=ttList.getString("ten")%></option>
										<% } } ttList.close(); } %>
									</Select>
								</TD> --%>
								
								<TD class="plainlabel" colspan="3">
									<Select  style="width: 200px" name="tiente_fk" id="tiente_fk" onChange="submitform();"  disabled="disabled" >
										<option value=""></option>
										<% if (ttList.size() > 0)
											{
												int size = ttList.size();
												for (int i = 0; i < size; i++)
												{
													ITiente t = (Tiente) ttList.get(i);
													if (dmhBean.getTienTe_FK() != null && dmhBean.getTienTe_FK().equals(t.getId()))
													{  %>
														<option value='<%= t.getId()%>' selected="selected"><%= t.getMa() %></option>															
													<% } else { %>
														<option value='<%= t.getId()%>'><%= t.getMa() %></option>
										<% } } } %>
										
									</Select>
								    <input type="hidden" name="tiente_fk" id="tiente_fk" value="<%=dmhBean.getTienTe_FK()%>"/>
									
									
									
									
								</TD>
								
							</TR>
							
							<TR>																																
								<TD class="plainlabel">Đề nghị mua hàng</TD>
								<TD class="plainlabel" width="250px" colspan="1">
								
								
									<select name="dnmhId" id="dnmhId" onChange="submitform();" style="width: 200px" disabled="disabled">
										<option value = ""></option>
										<% if (dnmhList != null)
										{
											while (dnmhList.next())
											{
												if (dnmhList.getString("pk_seq").equals(dmhBean.getDnmhId()))
												{
												%>
													<option value="<%=dnmhList.getString("pk_seq")%>" selected="selected"><%=dnmhList.getString("SODENGHI")%></option>
												<% } else { %>
													<option value="<%=dnmhList.getString("pk_seq")%>"><%=dnmhList.getString("SODENGHI")%></option>
										<% } } dnmhList.close(); } %>
										<option value=""></option>
									</select>
									<input type="hidden" id="dnmhId"  name="dnmhId"  value="<%=dmhBean.getDnmhId()%>"/>
									
									
								</TD>
								<input type="hidden" name="timnccId" id="timnccId" value = "<%=dmhBean.getTimNCCId() %>"/>
								<%-- <TD class="plainlabel">Tìm nhà cung cấp</TD>
								<TD class="plainlabel" width="250px">
									<select name="timnccId" id="timnccId" style="width: 200px">
										<option value = ""></option>
										<% if (timnccList != null)
										{
											while (timnccList.next())
											{
												if (timnccList.getString("pk_seq").equals(dmhBean.getTimNCCId()))
												{
												%>
													<option value="<%=timnccList.getString("pk_seq")%>" selected="selected"><%=timnccList.getString("MA")%></option>
												<% } else { %>
													<option value="<%=timnccList.getString("pk_seq")%>"><%=timnccList.getString("MA")%></option>
										<% } } timnccList.close(); } %>
										<option value=""></option>
									</select>
								</TD> --%>
								
								
								
								<TD class="plainlabel">Tỷ giá</TD>
								<TD class="plainlabel" width="250px" colspan="3">		
									<% if (ttList.size() > 0)
											{
												int size = ttList.size();
												for (int i = 0; i < size; i++)
												{
													System.out.println( " tiente_fk : "+ dmhBean.getTienTe_FK());
													ITiente t = (Tiente) ttList.get(i);
													if (dmhBean.getTienTe_FK() != null && dmhBean.getTienTe_FK().equals(t.getId()))
													{  %>
													<input type="text"  id="tygiaid" name="tygiaid"value="<%= t.getTygiaquydoi()%>" readonly />
													
									<% } } } else {%>	
									<input type="text"  id="tygiaid" name="tygiaid"  value="0" readonly />				
									<%} %>	
								</TD>
								
								
								
							</TR>
							
							<TR>
								<TD class="plainlabel">Đơn vị thực hiện</TD>
								<TD class="plainlabel" width="250px">
									<select name="dvthId" id="dvthId" style="width: 200px" disabled="disabled" >
										<option value = ""></option>
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
										<option value=""></option>
									</select>
									
									<input type="hidden" name="dvthId" id="dvthId" value =<%= dmhBean.getDvthId() %>  />
									
								</TD>	
								<TD class="plainlabel" width="130px">Nguồn gốc</TD>
								<TD class="plainlabel" colspan="3">
									<select name="nguongoc" id="nguongoc" style="width: 200px" disabled="disabled">
											<% if (dmhBean.getNguonGocHH().equals("TN")) { %>
												<option value="TN" selected="selected">Nội địa</option>
												<option value="NN">Nhập khẩu</option>
											<% } else if (dmhBean.getNguonGocHH().equals("NN")) { %>
												<option value="TN">Nội địa</option>
												<option value="NN" selected="selected">Nhập khẩu</option>
											<% } else { %>
												<option value="TN">Nội địa</option>
												<option value="NN">Nhập khẩu</option>
											<% } %>
											<option value=""></option>
									</select>
								<input type="hidden" name="nguongoc" id="nguongoc"  value =<%= dmhBean.getNguonGocHH() %>  />
																										   

								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel">Loại hàng hóa</TD>
								<TD class="plainlabel">
									<select name="loaihh" id="loaihh" style="width: 200px" disabled="disabled">
									<% if(dmhBean.getLoaihanghoa().equals("1")) { %>
										<option value="1" selected="selected">Tài sản cố định</option>
										<option value="0">Nhập kho CCDC, VT, Phụ tùng thay thế</option>
										<option value="2">Chi phí dịch vụ</option>
									<% } else if(dmhBean.getLoaihanghoa().equals("2")){ %>
										<option value="2"  selected="selected">Chi phí dịch vụ</option>
										<option value="1">Tài sản cố định</option>
										<option value="0">Nhập kho CCDC, VT, Phụ tùng thay thế</option>
									<% } else  if(dmhBean.getLoaihanghoa().equals("0")){  %> 
										<option value="0" selected="selected">Nhập kho CCDC, VT, Phụ tùng thay thế</option>
										<option value="1">Tài sản cố định</option>
										<option value="2">Chi phí dịch vụ</option>
									<%} else {%>
										<option value="0">Nhập kho CCDC, VT, Phụ tùng thay thế</option>
										<option value="1">Tài sản cố định</option>
										<option value="2">Chi phí dịch vụ</option>								
									
									<%} %>
									</select>
									
								<input type="hidden" name="loaihh" id="loaihh"  value =<%= dmhBean.getLoaihanghoa() %>  />	
									
									
																	
								</TD>
								
								
							<td class="plainlabel" >Tiền Việt trước thuế </td>
								<TD class="plainlabel" width="250px">
									<input type="text"  id="tienviettruocvat" name="tienviettruocvat"
											value=""  readonly />
								</TD>
							
							
								<td class="plainlabel"  width="150px">Tiền Việt sau thuế </td>
								<TD class="plainlabel" width="250px">
									<input type="text"  id="tienvietsauvat" name="tienvietsauvat"
											value=""  readonly /> 
								</TD>
								
								
							</TR>
							
							
							<TR>
								<TD class="plainlabel">Ghi chú</TD>
								<TD class="plainlabel" colspan="5" >																	 
								<%-- 	<input type="text" name= "ghichu" id= "ghichu" value =<%= dmhBean.getGhiChu() %>  > --%>
								<% System.out.println (" ghi chu jsp: " +dmhBean.getGhiChu()); %>
								<input type="text" name="ghichu" id="ghichu" value="<%= dmhBean.getGhiChu()%>"/>
								</TD>
							</TR>
							
						</TABLE>
						<hr>
					</div>
					
				 <%if(nccList != null){ %>
				 <%				  
				    for(int j = 0; j < nccList.size(); j++) {				    
				    	INhacungcap nccDetail = nccList.get(j);			    
				 %>
				  <div  class="plainlabel" id="accordion" style="width:100%; height:auto; float:none; font-size:1.0em" align="left">
				  
					 <h1 class="plainlabel" style= "height:auto;font-size:1.0em;"><a  href="#"><%= nccDetail.getTen() %></a>
					 	<input type="hidden" name = "nccid" value = <%= nccDetail.getId() %>>
					 </h1>
					 
					 <div>
					 	<TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1">
					 	
					 	<%if(!dmhBean.getTienTe_FK().equals("100000")){%>
							<TR class="plainlabel">
							   <TD class="plainlabel" width="300px"></TD>
								<TD class="plainlabel" > Tổng tiền Việt trước thuế</TD>
								<TD class="plainlabel" > <input type="text" name = "VNDbvat" value = "0" readonly></TD> 
								
								<TD class="plainlabel" > &nbsp;&nbsp;</TD>
								<TD class="plainlabel" > &nbsp;&nbsp;</TD>
								
								<TD class="plainlabel" > &nbsp;&nbsp;Tổng tiền Việt sau thuế</TD>
								<TD class="plainlabel" > <input type="text" name = "VNDavat" value = "0" readonly></TD>
							</TR>
							<%} else{ %>
							<TR class="plainlabel">
							   <TD class="plainlabel" width="300px"></TD>
								<TD class="plainlabel"  style="display: none"> Tổng tiền Việt trước thuế</TD>
								<TD class="plainlabel"  style="display: none"> <input type="text" name = "VNDbvat" value = "0" readonly></TD> 
								
								<TD class="plainlabel"  style="display: none"> &nbsp;&nbsp;</TD>
								<TD class="plainlabel"  style="display: none"> &nbsp;&nbsp;</TD>
								
								<TD class="plainlabel"  style="display: none"> &nbsp;&nbsp;Tổng tiền Việt sau thuế</TD>
								<TD class="plainlabel" style="display: none"> <input type="text" name = "VNDavat" value = "0" readonly></TD>
							</TR>
							<%} %>
					 	
					 	
					 	
							<TR class="plainlabel">
							   <TD class="plainlabel" width="400px"></TD>
								<TD class="plainlabel" > Tổng tiền trước thuế</TD>
								<TD class="plainlabel" > <input type="text" name = "bvat" value = <%= nccDetail.getTongtienBvat() %> readonly></TD> 
								<TD class="plainlabel" > &nbsp;&nbsp;VAT</TD>
								<TD class="plainlabel" > <input type="text" name = "vat" value = <%= nccDetail.getVat() %> onKeyPress = "return keypress(event);" 
								<%if(!dmhBean.getTienTe_FK().equals("100000")){%> readonly="readonly" <%} %>></TD>
								<TD class="plainlabel" > &nbsp;&nbsp;Tổng tiền sau thuế</TD>
								<TD class="plainlabel" > <input type="text" name = "avat" value = <%= nccDetail.getTongtienAvat() %> readonly></TD>
							</TR>
						</TABLE>
					 </div>
					 
					 <div style="height:auto">
					 <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1">
							<TR class="tbheader">
								<TH align="center" width="5%">STT</TH>
								
								<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { %>
									<TH align="center" width="10%" >Mã hàng mua</TH>
									<TH align="center" width="15%">Tên hàng mua</TH>
								<% } else { if(dmhBean.getLoaihanghoa().trim().equals("1")) { %> 
									<TH align="center" width="10%" >Mã tài sản</TH>
									<TH align="center" width="15%">Diễn giải</TH>
								<% } else if(dmhBean.getLoaihanghoa().trim().equals("2")){ %>
									<%-- <%if(quyen[0]!=0){ %>
									<TH align="center" width="10%" >Mã chi phí</TH>
									<%}else{ %>
									<TH align="center" width="10%" style="display: none;">Mã chi phí</TH>
									<%} %> --%>
									<TH align="center" width="10%" >Mã chi phí</TH>
									<TH align="center" width="15%">Diễn giải</TH>
								 <% } else if(dmhBean.getLoaihanghoa().trim().equals("3")){%>
								 	<TH align="center" width="10%" >Mã CCDC</TH>
									<TH align="center" width="15%">Diễn giải</TH>
								 <%} } %>
								
								<TH align="center" width="5%">ĐVT</TH>
								<TH align="center" width="5%">Số lượng</TH>
								<TH align="center" width="10%">Đơn giá</TH>
								<TH align="center" width="10%">Thành tiền</TH>
															
							</TR>
							
							<% int count = 0; 
								List<ISanpham> spList = nccDetail.getSanPham();
								if(spList.size() > 0) { 
									for(int i = 0; i < spList.size(); i++) { 
										ISanpham sp = spList.get(i);
										%>
										
										<tr>
											<TD align="center" width="2%">
												<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
												<input type="hidden" value="<%= sp.getId() %>" name=<%= "idsp" + j %> style="width: 100%" readonly="readonly" > 
											
											</TD>
											<%-- <%if(  dmhBean.getLoaihanghoa().trim().equals("0")  ){ %> --%>
											<TD  align="center" width="8%" >
												<input type="text"  name= <%= "masp" +j %> style="width: 100%" value="<%= sp.getMasanpham() %>"   readonly="readonly" > 
											</TD>
											<%-- <%} else {%>
											 <TD  align="center" width="8%" >
												<input type="text"  name= <%= "masp" +j %> style="width: 100%" value="<%= sp.getMasanpham() %>" readonly="readonly" > 
											</TD>
											<%} %> --%>
											<TD align="left" width="10%">
												<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { %>
													<input type="text" value="<%= sp.getTensanpham() %>" name=<%= "tensp" + j%> style="width: 100%" readonly="readonly" > 
												<% } else {  %>
													<input type="text" value="<%= sp.getTensanpham() %>" name=<%= "tensp" + j%> style="width: 100%" > 
												<%  } %>
											</TD>
											<TD align="center" width="6%">
												<input type="text" value="<%= sp.getDonvitinh() %>" name=<%= "donvitinh" + j %> style="width: 100%" readonly> 
											</TD>
											<TD align="center" width="7%">
												<input type="text" value="<%= sp.getSoluong() %>" name= <%= "soluong" + j %> style="width: 100%; text-align: right;" readonly> 
											</TD>
											<TD align="center" width="8%">
											
											<% System.out.println( "tien te_ cho don gia: "+ dmhBean.getTienTe_FK()); %>
											<% if(dmhBean.getTienTe_FK().equals("100000")) {%>
												<%-- <input type="text" value="<%= sp.getDongia() %>" name=<%= "dongia" + j %> style="width: 100%; text-align: right;"    onKeyPress = "return keypress(event);" >  --%>
										
												<input type="text" value="<%= sp.getDongia() %>" name=<%= "dongia" + j %> style="width: 100%; text-align: right;" onkeyup="FormatNumber4sole(this)"   onKeyPress = "return keypress(event);" >
												<%} else { %>
													<input type="text" value="<%= sp.getDongia() %>" name=<%= "dongia" + j %> style="width: 100%; text-align: right;" onkeyup="FormatNumber(this)"  onKeyPress = "return keypress(event);" >
											<%}  %>
											
											
											</TD>
											<TD align="center" width="8%">
												<input type="text" value="<%= sp.getThanhtien() %>"  name=<%= "thanhtien" + j%> style="width: 100%; text-align: right;" > 
											</TD>

										</tr>
										
									<% count++; }
								} %>							
							
							
							<tr class="tbfooter">
								<td colspan="12">&nbsp;</td>
							</tr>
						</TABLE>
					</div>
												
				</div>
				<%} 				  
				 }%>	 

					
				</fieldset>
			</div>
		</div>
<script type="text/javascript">
	replaces();
	dropdowncontent.init("noidungGhiChu", "right-top", 500, "click");
	dropdowncontent.init("chiphiKHAC", "left-top", 500, "click");
	dropdowncontent.init("searchlink", "right-bottom", 500, "click");
	jQuery(function()
	{		
		$("#nccId").autocomplete("ErpNccMuaHangListGiay.jsp");
	});	
	
</script>
<script type="text/javascript">
	dropdowncontent.init('duyetId', "left-bottom", 300, "click");
</script>	

<script type="text/javascript">
	
	for(var i = 0; i < 40; i++) {
		dropdowncontent.init('tenhd_'+i, "left-top", 300, "click");
		dropdowncontent.init('ngaynhan_'+i, "left-top", 300, "click");
	}
</script>
<%

 		if(dvList!=null){
 			dvList.clear();
 		}
 		/* if(ttList!=null){
 			ttList.close();
 		} */
 		if(nccList!=null){
 			nccList.clear();
 		}
 		session.setAttribute("dmhBean",null);
		dmhBean.close();
	%>
	
	
	<%
		dmhBean.close();
	%>
</form>
</BODY>
</html>