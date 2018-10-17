<%@page import="geso.traphaco.erp.beans.cantrucongno.imp.ErpCanTruCongNo"%>
<%@page import="java.util.List"%>
<%@page import="geso.traphaco.erp.beans.thutien.IHoadon"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.sql.ResultSet"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
	<% ErpCanTruCongNo obj = (ErpCanTruCongNo)session.getAttribute("obj"); %>
	<% if(obj==null){ obj = new ErpCanTruCongNo();}  %>	
	<% String userId = (String)session.getAttribute("userId"); %>
	<% String trangThai = obj.getTrangthai(); %>
	<% List<IHoadon> hoaDonKHList = obj.getListHoaDonKhachHang(); %>
	<% List<geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon>  hoadonList = obj.getListHoaDonNCC(); %>
	<% ResultSet tienteList = obj.getTienteRs(); %>
	<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
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
	filter: progid : DXImageTransform.Microsoft.Shadow ( color = gray,
		direction = 135 );
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}

.error {
	color: #f00;
}

input[readonly] {
	color: #111;
	background: #f7f7f7;
}
</style>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>

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
<script src="../scripts/numeral.min.js" type="text/javascript"></script>
<script src="../scripts/Numberformat.js" type="text/javascript"></script>
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
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-chiphi.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<script type="text/javascript">
function tinhToanChenhLech()
{
	//tinh tong NCC

	TinhTong("Ncc");
	
// 	tinh tong KH
	 TinhTong("KH");
	
	
// 	tim McIN
	var tongNCC = parseFloat(document.cpk.tongNcc.value);
	var tongKH = parseFloat(document.cpk.tongKH.value);	
	var MinTong = Math.min(tongNCC,tongKH);
	document.getElementById("sotienthanhtoan").value = MinTong;


	
// 	tru dan NCC
	TruDan("Ncc",MinTong);
	
// 	tru dan KH
	TruDan("KH",MinTong);
	
	
	TinhTongCanTru("Ncc");
	TinhTongCanTru("KH");


}


function TinhTong(loai)
{
var SelectedList = document.getElementsByName("select"+loai);
	
	var tong = parseFloat("0");
	var giatri = 0;
	for(var i = 1; i<SelectedList.length + 1;i++)
		{
		  if(document.forms["cpk"]["select"+loai+"#"+i].checked)
			  {
			  giatri =  document.forms["cpk"]["phaiXoaNo"+loai+"#"+i].value;
			  
			  while (giatri.indexOf(",")>=0) {
				  giatri = giatri.replace(",","");
				}
			  
			  tong+=parseFloat(giatri) ;
			 
			  
			  
			  }
		}	
	document.getElementById("tong"+loai).value = tong;//numeral(tong).format('0,0');
	
}


function TinhTongCanTru(loai)
{
	var SelectedList = document.getElementsByName("select"+loai);
	
	var tongCanTru = parseFloat("0");	
	var giatriCantru = 0;
	for(var i = 1; i<SelectedList.length + 1;i++)
		{
		  if(document.forms["cpk"]["select"+loai+"#"+i].checked)
			  {
			  
			  giatriCantru =  document.forms["cpk"]["xoaNo"+loai+"#"+i].value;
			  
			  while (giatriCantru.indexOf(",")>=0) {
				  
				  giatriCantru = giatriCantru.replace(",","");
				}
			  
			  tongCanTru+=parseFloat(giatriCantru);  
			  
			  }
		}	
	
	document.getElementById("tongCanTru"+loai).value =  formatCurrency(tongCanTru);//numeral(tongCanTru).format('0,0');
}


function TruDan(loai,MinTong)
{
	var tmp = MinTong;
	var SelectedList = document.getElementsByName("select"+loai);
	
	var giatri;
	for(var i = 1; i<SelectedList.length + 1;i++)
		{
		
		  if(document.forms["cpk"]["select"+loai+"#"+i].checked)
			  {
			  if(tmp>0)
				{
			  giatri =  document.forms["cpk"]["phaiXoaNo"+loai+"#"+i].value;
			  
			  while (giatri.indexOf(",")>=0) {
				  giatri = giatri.replace(",","");
				}
			  
			  tmp = parseFloat(tmp) - parseFloat(giatri);
			  
			  
			  if(tmp>=0)
				  {
				  document.forms["cpk"]["xoaNo"+loai+"#"+i].value = document.forms["cpk"]["phaiXoaNo"+loai+"#"+i].value;
				  
				  }else
				 {
					  document.forms["cpk"]["xoaNo"+loai+"#"+i].value = formatCurrency(parseFloat(giatri) + parseFloat(tmp));//numeral(parseFloat(giatri) + parseFloat(tmp)).format('0,0');
					  
				 }		  
			  }else
			{
				  document.forms["cpk"]["xoaNo"+loai+"#"+i].value = "0";
			}
		}else
		{
			  document.forms["cpk"]["xoaNo"+loai+"#"+i].value = "0";
		}
		  
		var phaiXoaNo  =   document.forms["cpk"]["phaiXoaNo"+loai+"#"+i].value;
		while (phaiXoaNo.indexOf(",")>=0) {
			phaiXoaNo = phaiXoaNo.replace(",","");
			}
		
		var xoaNo = document.forms["cpk"]["xoaNo"+loai+"#"+i].value;
		while (xoaNo.indexOf(",")>=0) {
			xoaNo = xoaNo.replace(",","");
			}
		document.forms["cpk"]["conLai"+loai+"#"+i].value =formatCurrency( parseFloat(phaiXoaNo) - parseFloat(xoaNo));//numeral( parseFloat(phaiXoaNo) - parseFloat(xoaNo)).format('0,0');
}
	}

</script>
<script type="text/javascript">
function submitform()
{
	

	
// 	document.forms["cpk"].khId.value = tmpKh;
	document.forms["cpk"].action.value = "update";
// 	document.forms["cpk"].nccId.value = t;
    document.forms["cpk"].submit();
}

</script>
<SCRIPT language="JavaScript" type="text/javascript">

	function hoantat(element){
		dropdowncontent.hidediv(element);
		save();
	}
	
	
	
	function changeKH(elementId)
	{
		
		var state = document.forms["cpk"]["selectKH#"+elementId].checked;
		var tongNCC = parseFloat(document.cpk.tongKH.value);
		var giatri =  document.forms["cpk"]["sotienKH#"+elementId].value;
		while (giatri.indexOf(",")>=0) {
			giatri = giatri.replace(",","");
		}
		
	
		if(state)
			{
			tong+=parseFloat(giatri) ; 
			
			}else
			{
				tong-=parseFloat(giatri) ; 
			}
				
		document.cpk.tongKH.value = tong;
			
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
	
	function save()
	{
		var soChungTu = document.forms["cpk"].sochungtu.value;
		if(soChungTu.length==0)
			{
			alert("Vui lòng điền số chứng từ");
	    	return;
			}
		 var sotienTT = document.forms["cpk"].sotienthanhtoan.value;


		    if(sotienTT==0)
		    	{
		    	alert("Bạn phải chọn Hóa đơn của khách hàng và nhà cung cấp để tạo cấn trừ công nợ!.");
		    	return;
		    	}
	  document.forms["cpk"].action.value = "save";
	  document.forms["cpk"].submit(); 
    }
	
	function replaces()
	{
		
		var nccOld =  document.getElementById("nccId").value;
		var temp = document.getElementById("ncc").value;
		var nccNew = temp.substring(0, parseInt(temp.indexOf(" - ")));
		temp = document.getElementById("kh").value;
		var khOld = document.getElementById("khId").value;
		var khNew = temp.substring(0, parseInt(temp.indexOf(" - ")));
		if(nccNew.length >0||khNew.length >0)
		{
			if(nccOld!=nccNew||khOld!=khNew)
				{			
			
			document.forms["cpk"].khId.value = khNew;			
			document.forms["cpk"].nccId.value = nccNew;			
			submitform();
				}
		}
		
		setTimeout(replaces, 300);

	}
	
	function replacesNV()
	{
		var nvId = document.getElementById("nvId");
		var nvTen = document.getElementById("nvTen");
		
		var temp = nvId.value;
		if(temp == "")
		{
			nvTen.value = "";
		}
		else
		{
			if(parseInt(temp.indexOf(" - ")) > 0)
			{
				nvId.value = temp.substring(0, parseInt(temp.indexOf(" - ")));
				
				temp = temp.substr(parseInt(temp.indexOf(" - ")) + 3);
				temp = temp.substr(parseInt(temp.indexOf(", ")) + 2);
				nvTen.value = temp.substring(0, temp.indexOf(" [ ") );

			}
		}
		var chiphi = document.getElementsByName("chiphi");
		var diengiaicp = document.getElementsByName("diengiaicp");
		
		var sodong =  chiphi.length;
		
		var i;
		for(i = 0; i < 3; i++)
		{
			if(chiphi.item(i).value != "")
			{
				var cp = chiphi.item(i).value;
				var pos = parseInt(cp.indexOf(" -- "));
				
				if(pos > 0)
				{
					chiphi.item(i).value = cp.substring(0, pos);
					cp = cp.substr(parseInt(cp.indexOf(" -- ")) + 3);
					diengiaicp.item(i).value = cp.substring(0, parseInt(cp.indexOf(" [")));
				}
				
			}	
		}

		setTimeout(replacesNV, 500);
		
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

	 function Dinhdangdukien(element)
	 {
	 	element.value=DinhDangTien(element.value);
	 	if(element.value== '' )
	 	{
	 		element.value= '';
	 	}
	 	
	 }

	 function ChangeTienTe(){
//		 document.forms['tthdForm'].action.value= 'changeTT';
	     document.forms["cpk"].submit();
		 
	 }

	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/$|\,/g,'');
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
	
	function tinhthue(){
		var stt = document.getElementsByName("stt");
		
		var tienchuathue = document.getElementsByName("tienchuathue");
		var tongtienthue = document.getElementsByName("thue");
		var tongcong = document.getElementsByName("cong");
			
		for(i = 0; i < stt.length; i++){
			var tongtien = 0;
			var tongthue = 0;
			
			var th = "tienhang" + stt.item(i).value;		
			var tienhang = document.getElementsByName(th);
			
			var ts = "thuesuat" + stt.item(i).value;
			var thuesuat = document.getElementsByName(ts);
			
			var t = "thue" + stt.item(i).value;		
			var thue = document.getElementsByName(t);
			
			var c = "cong" + stt.item(i).value;
			var cong = document.getElementsByName(c);
			
			for(j = 0; j < tienhang.length; j++){
				var temp = parseFloat(tienhang.item(j).value.replace(/$|\,/g,''))*parseFloat(thuesuat.item(j).value.replace(/$|\,/g,''))/100;
				thue.item(j).value = DinhDangTien(temp);
				tongthue = tongthue + temp;
				
				cong.item(j).value =  DinhDangTien(parseFloat(tienhang.item(j).value.replace(/$|\,/g,'')) + temp);
				tongtien = tongtien + parseFloat(cong.item(j).value.replace(/$|\,/g,'')); 
			}
			tienchuathue.item(i).value = DinhDangTien(tongtien);
			tongtienthue.item(i).value = DinhDangTien(tongthue);
			tongcong.item(i).value = DinhDangTien(tongtien + tongthue);
		}
		
	}
</SCRIPT>
</HEAD>
<% if(trangThai.length()==0||"0".equals(trangThai)){%>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="">
	<form name="cpk" method="post" action="../../ErpCanTruCongNoUpdateSvl">
		<input type="hidden" name="userId" value='<%= userId %>' >  
		<input type="hidden" name="action" value="0">
		<input type="hidden" name="Id" value="<%= obj.getId() %>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%" >
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
									<% if(obj.getId().length() == 0){ %>
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý công nợ > Công nợ phải trả > Cấn trừ công nợ > Tạo mới</TD>
									<% }else{ %>
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý công nợ > Công nợ phải trả > Cấn trừ công nợ > Cập nhật</TD>
									<% } %>	
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn Admin</TD>
									</tr>
								</table></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="../../ErpCanTruCongNoSvl?userId= <%= userId%>"><img
												src="../images/Back30.png" alt="Quay về" title="Quay về"
												border="1" longdesc="Quay về" style="border-style: outset">
										</A>
										</TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript: save();"><IMG
													src="../images/Save30.png" title="Lưu lại" alt="Lưu lại"
													border="1" style="border-style: outset">
												</A>
											</div></TD>
										<TD>&nbsp;</TD>
									</TR>
								</TABLE></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>

									<textarea id ="dataerror" name="dataerror"
										style="width: 100%; color: #F00; font-weight: bold"										
										readonly="readonly" rows="1" ><%=obj.getMsg() %></textarea>
								</FIELDSET></TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Nhà cung cấp </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

										<TR>
											<TD class="plainlabel" valign="middle" width="150px">Ngày</TD>
											<TD class="plainlabel" valign="middle" colspan=3>
											<input	type="text" name="ngaynhap" value="<%=obj.getNgayTao() %>" class="days">
											<input type="hidden" style="text-align: right;" name="sotienthanhtoan" id = "sotienthanhtoan"	value=<%=Double.parseDouble(obj.getSoTienThanhToan()) %> > 
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="150px">Số chứng từ</TD>
											<TD class="plainlabel" valign="middle" colspan=3>
											<input	type="text"  id = "sochungtu" id = "sochungtu" name="sochungtu"   value="<%=obj.getSoChungTu()%>">											 
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="150px">Diễn giải chứng từ</TD>
											<TD class="plainlabel" valign="middle" colspan=3>
											<input	type="text"  id = "dienGiaiCT" name="dienGiaiCT"   value="<%=obj.getDienGiaiCT()%>">											 
											</TD>
										</TR>
										<TR>

                    						<TD class="plainlabel">Tiền tệ</TD>
                    						<TD class="plainlabel" style = "width:100px" colspan = "3">
                    	
					                        <SELECT name="tienteId" id="tienteId" style = "width:200px" onChange = "submitform();">
                        						<option value = "" >&nbsp;</option>
                        					<%
                        					if(tienteList != null)
                        					{
                        						try
                        						{
                        							while(tienteList.next())
                        							{  
                        								if( tienteList.getString("pk_seq").equals(obj.getTienteId())){ %>
                        							<option value="<%= tienteList.getString("pk_seq") %>" selected="selected" ><%= tienteList.getString("ten")%></option>
                        								<%}else { %>
                        							<option value="<%= tienteList.getString("pk_seq") %>" ><%= tienteList.getString("ten") %></option>
                        		 						<%} 
                        							} 
                        							tienteList.close();
                        						} 
                        						catch(java.sql.SQLException ex){}
                        					}
                        					%>
                        					</SELECT>
                     						</TD> 
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle">Nhà cung cấp </TD>
											<TD class="plainlabel" valign="middle">
											<%if("0".equals(trangThai)){ %>
											<input type="text" id="ncc" name="ncc" value = "<%=obj.getNccTen() %>" style="width: 500px" disabled="disabled" >
											<%}else{ %>
											<input type="text" id="ncc" name="ncc" value = "<%=obj.getNccTen() %>" style="width: 500px"  > 
											<%} %>
		                     		 
		                     		<input type="hidden" id="nccId" name="nccId" value = "<%=obj.getNccId() %>" style="width: 500px">
                        		</TD> 
                        		<TD class="plainlabel" valign="middle">Tổng </TD> 
                        		<TD class="plainlabel" valign="middle"><input type="hidden" id="tongNcc" value="0"  readonly="readonly" >
                        		<input type="text" id="tongCanTruNcc" value="<%=formatter.format(Double.parseDouble(obj.getSoTienThanhToan())) %>"  readonly="readonly" ></TD>
										</TR>
										<TR>
											<td colspan="4">

												<table width="100%" cellpadding="0px" cellspacing="1px">
													<tr class="tbheader">
														<th align="center" width="5%">STT</th>
														<th align="center" width="12%">Loại hóa đơn</th>
														<th align="center" width="10%">Ngày hóa đơn</th>
														<th align="center" width="12%">Số hóa đơn</th>
														<th align="center" width="13%">Số tiền gốc</th>
														<th align="center" width="13%">Phải xóa nợ</th>
														<th align="center" width="13%">Xóa nợ</th>
														<th align="center" width="13%">Còn lại</th>
														<th align="center" width="13%">Chọn</th>
													</tr>
													
								
           	 	
													
													<%
												
									                	for(int i = 0; i < hoadonList.size(); i++)
									                	{
									                		geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon hoadon = hoadonList.get(i);
										               		%>
													<tr class="tbdarkrow">
													<td> 
													<input style="width: 100%;text-align: center;" name="rowIdNcc" type="text" readonly="readonly" value=<%=(i+1)%>>
													 <input type="hidden" style="width: 100%;" value="<%= hoadon.getId() %>" name= "idHoaDonNcc" readonly="readonly" >  
													</td>
													<td>
													  <input type="hidden" style="width: 100%;" value="<%= hoadon.getLoaihd1() %>" name= "loaiHoaDonNcc" readonly="readonly" >  
													  <input style="text-align:left;" name="tenLoaiHdNcc" id=<%="tenLoaiHdNcc#"+(i+1) %> type="text" readonly="readonly" value="<%=hoadon.getTenloaihd1() %>" >
													</td>
													<td> <input style="width: 100%;text-align:center;" name="ngayHoaDonNcc" id=<%="ngayHoaDonNcc#"+(i+1) %> type="text" readonly="readonly" value=<%=hoadon.getNgay() %> ></td>
													<td> <input style="width: 100%;text-align:center;" name= "soHoaDonNcc" id = <%="soHoaDonNcc#"+(i+1) %> type="text" readonly="readonly" value=<%=hoadon.getSo() %> ></td>
													<td> <input style="width: 100%;text-align: right;" name="soTienNcc" id=<%="soTienNcc#"+(i+1) %> type="text" readonly="readonly" value="<%=hoadon.getTongtiencoVAT()%>" > </td>
													<td> <input style="width: 100%;text-align: right;" name= "phaiXoaNoNcc" id=<%="phaiXoaNoNcc#"+(i+1) %> type="text" value="<%=hoadon.getTongtiencoVAT()%>" readonly="readonly" >  </td>
													<td> <input style="width: 100%;text-align: right;" name= "xoaNoNcc" id=<%="xoaNoNcc#"+(i+1) %> type="text" value="<%=hoadon.getThanhtoan()%>" readonly="readonly"> </td>
													<td> <input style="width: 100%;text-align: right;" name= "conLaiNcc" id=<%="conLaiNcc#"+(i+1) %> type="text" value="<%=hoadon.getConlai() %>" readonly="readonly"> </td>
													<%if("0".equals(hoadon.getThanhtoan())||"".equals(hoadon.getThanhtoan())){ %>
													<td> <input style="width: 100%;" name="selectNcc" id=<%= "selectNcc#"+(i+1) %> type="checkbox" onchange="tinhToanChenhLech();" > </td>		
													<%}else{ %>
													<td> <input style="width: 100%;" name="selectNcc" id=<%= "selectNcc#"+(i+1) %> type="checkbox" checked="checked" onchange="tinhToanChenhLech();" > </td>
													<%} %>
													</tr>
													<%} %>
												

												</table></td>
										</tr>

									</TABLE>

								</FIELDSET></TD>
								
								
								
						</TR>
						
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Khách hàng </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
									<TR>
											<TD class="plainlabel" valign="middle" width="150px">Khách hàng:  </TD>
											<TD class="plainlabel" valign="middle">
									<%if("0".equals(trangThai)){ %>
											<input type="text" id="kh" name="kh" value = "<%=obj.getKhTen() %>" style="width: 500px" disabled="disabled">  
											<%}else{ %>
											<input type="text" id="kh" name="kh" value = "<%=obj.getKhTen() %>" style="width: 500px">  
											<%} %>
											
											
		                     		
		                     		<input type="hidden" id="khId" name="khId" value = "<%=obj.getKhId() %>" style="width: 500px" >
                        		</TD>
                        		<TD class="plainlabel" valign="middle">Tổng </TD> 
                        		<TD class="plainlabel" valign="middle"><input readonly="readonly" id = "tongKH" type="hidden" value = "0"> 
                        		<input readonly="readonly" id = "tongCanTruKH" type="text" value = "<%=formatter.format(Double.parseDouble(obj.getSoTienThanhToan())) %>"> </TD> 
										</TR>

										
										<TR>
											<td colspan="4">

												<table width="100%" cellpadding="0px" cellspacing="1px">
													<tr class="tbheader">
														<th align="center" width="5%">STT</th>
														<th align="center" width="12%">Loại hóa đơn</th>
														<th align="center" width="10%">Ngày hóa đơn</th>
														<th align="center" width="12%">Số hóa đơn</th>
														<th align="center" width="13%">Số tiền gốc</th>
														<th align="center" width="13%">Phải xóa nợ</th>
														<th align="center" width="13%">Xóa nợ</th>
														<th align="center" width="13%">Còn lại</th>
														<th align="center" width="13%">Chọn</th>
													</tr>
													
													
           	 	
           	 	
													
													
													
													 <%
                	for(int i = 0; i < hoaDonKHList.size(); i++)
                	{
                		IHoadon hoadon = hoaDonKHList.get(i);
	               		%>
													<tr class="tbdarkrow">
													<td >
													 <input style="width: 100%;text-align: center;" name="rowIdKH" type="text" readonly="readonly" value=<%=(i+1)%>>
													 <input type="hidden" style="width: 100%;" value="<%= hoadon.getId() %>" name= "idHoaDonKH" readonly="readonly" > 
													 </td>
													  <td > 
													 	<input type="hidden" style="width: 100%;" value="<%= hoadon.getLoaihd() %>" name= "loaiHoaDonKH" readonly="readonly" > 
													    <input style="width: 100%;text-align:center" name="tenloaiHoaDonKH" id=<%="tenloaiHoaDonKH#"+(i+1) %> type="text" readonly="readonly" value="<%=hoadon.getTenloaihd() %>" >
													 </td>
													<td > <input style="width: 100%;text-align:center" name="ngayHoaDonKH" id=<%="ngayHoaDonKH#"+(i+1) %> type="text" readonly="readonly" value=<%=hoadon.getNgay() %> ></td>
													<td> <input style="width: 100%;text-align:center" name= "soHoaDonKH" id = <%="soHoaDonKH#"+(i+1) %> type="text" readonly="readonly" value=<%= hoadon.getSo() %> ></td>

													<td>
													<input style="width: 100%;text-align:right" name="sotienKH" type="text" id = <%="sotienKH#"+(i+1) %> value=<%= hoadon.getTongtiencoVAT()  %> readonly="readonly"> 
													
													</td>
													
													<td> <input style="width: 100%;text-align:right" name="phaiXoaNoKH" type="text" id = <%="phaiXoaNoKH#"+(i+1) %> value=<%= hoadon.getSotienno() %> readonly="readonly"></td>


													<td> <input style="width: 100%;text-align: right" name="xoaNoKH" type="text" id = <%="xoaNoKH#"+(i+1) %> value = <%=hoadon.getThanhtoan()%> readonly="readonly"></td>
													<td><input style="width: 100%;text-align: right" name="conlaiKH" type="text" id = <%="conLaiKH#"+(i+1) %> value=<%=hoadon.getConlai_CantruCN() %> readonly="readonly"> </td>
													<td>
													<%if("0".equals(hoadon.getThanhtoan())||"".equals(hoadon.getThanhtoan())){  %>
													<input style="width: 100%;" name="selectKH" id = <%="selectKH#"+(i+1)%> type="checkbox" onchange="tinhToanChenhLech();">  
												    <%}else{ %>
												    <input style="width: 100%;" name="selectKH" id = <%="selectKH#"+(i+1)%> type="checkbox" checked="checked" onchange="tinhToanChenhLech();">
												    <%} %>
												    </td>
													</tr>
													<%} %>
													
												
												</table></td>
										</tr>

									</TABLE>

								</FIELDSET></TD>
								
								
								
						</TR>
						
					</TABLE></TD>
			</TR>
		</TABLE>




	</form>
<script type="text/javascript">

jQuery(function()
{		
	$("#ncc").autocomplete("ErpNhaCungCapList.jsp");
	$("#kh").autocomplete("ErpKhachhangCanTruList.jsp");
	<% if( obj.getTrangthai().length()==0){%>
	
	replaces();
	<% }%>
	
	
	

});	

</script>
</BODY>
</HTML>
<%	}else{ %>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="cpk" method="post" action="../../ErpCanTruCongNoUpdateSvl">
		<input type="hidden" name="userId" value='100211'> <input
			type="hidden" name="action" value="0">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%" >
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý công nợ > Công nợ phải trả > Cấn trừ công nợ > Hiển thị</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn Admin</TD>
									</tr>
								</table></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="../../ErpCanTruCongNoSvl?userId= <%= userId%>"><img
												src="../images/Back30.png" alt="Quay về" title="Quay về"
												border="1" longdesc="Quay về" style="border-style: outset">
										</A>
										</TD>
										<TD width="2" align="left"></TD>
										
										<TD>&nbsp;</TD>
									</TR>
								</TABLE></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>

									<textarea name="dataerror"
										style="width: 100%; color: #F00; font-weight: bold"
										style="width: 100% ; color:#F00 ; font-weight:bold"
										readonly="readonly" rows="1"></textarea>
								</FIELDSET></TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Nhà cung cấp </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

										<TR>
											<TD class="plainlabel" valign="middle" width="150px">Ngày :</TD>
											<TD class="plainlabel" valign="middle" colspan=3>
											<input	type="text" name="ngaynhap" value="<%=obj.getNgayTao() %>" class="days" disabled="disabled">
											<input type="hidden" style="text-align: right;" name="sotienthanhtoan" id = "sotienthanhtoan"	value=<%=Double.parseDouble(obj.getSoTienThanhToan()) %> > 
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="150px">Số chứng từ:</TD>
											<TD class="plainlabel" valign="middle" colspan=3>
											<input	type="text" readonly="readonly" id = "sochungtu" name="sochungtu"  value="<%=obj.getSoChungTu()%>">											 
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="150px">Diễn giải chứng từ</TD>
											<TD class="plainlabel" valign="middle" colspan=3>
											<input	type="text"  id = "dienGiaiCT" name="dienGiaiCT"   value="<%=obj.getDienGiaiCT()%>">											 
											</TD>
										</TR>
										<TR>

                    						<TD class="plainlabel">Tiền tệ</TD>
                    						<TD class="plainlabel" style = "width:100px">
                    	
					                        <SELECT name="tienteId" id="tienteId" style = "width:150px" onChange = "ChangeTienTe();">
                        		
                        					<%
                        					if(tienteList != null)
                        					{
                        						try
                        						{
                        							while(tienteList.next())
                        							{  
                        								if( tienteList.getString("pk_seq").equals(obj.getTienteId())){ %>
                        							<option value="<%= tienteList.getString("pk_seq") %>" selected="selected" ><%= tienteList.getString("ten")%></option>
                        								<%}else { %>
                        							<option value="<%= tienteList.getString("pk_seq") %>" ><%= tienteList.getString("ten") %></option>
                        		 						<%} 
                        							} 
                        							tienteList.close();
                        						} 
                        						catch(java.sql.SQLException ex){}
                        					}
                        					%>
                        					</SELECT>
                     						</TD> 
										</TR>

										<TR>
											<TD class="plainlabel" valign="middle">Nhà cung cấp </TD>
											<TD class="plainlabel" valign="middle">
		                     		<input type="text" id="ncc" name="ncc" value = "<%=obj.getNccTen() %>" style="width: 500px" disabled="disabled"  >  
		                     		<input type="hidden" id="nccId" name="nccId" value = "<%=obj.getNccId() %>" style="width: 500px" readonly="readonly" >
                        		</TD> 
                        		<TD class="plainlabel" valign="middle">Tổng </TD> 
                        		<TD class="plainlabel" valign="middle"><input type="hidden" id="tongNcc" value="0"  readonly="readonly" >
									<input type="text" id="tongCanTruNcc" value="<%=formatter.format(Double.parseDouble(obj.getSoTienThanhToan())) %>"  readonly="readonly" ></TD>
										</TR>
										<TR>
											<td colspan="4">

												<table width="100%" cellpadding="0px" cellspacing="1px">
													<tr class="tbheader">
														<th align="center" width="5%">STT</th>
														<th align="center" width="12%">Loại hóa đơn</th>
														<th align="center" width="10%">Ngày hóa đơn</th>
														<th align="center" width="12%">Số hóa đơn</th>
														<th align="center" width="13%">Số tiền gốc</th>
														<th align="center" width="13%">Phải xóa nợ</th>
														<th align="center" width="13%">Xóa nợ</th>
														<th align="center" width="13%">Còn lại</th>
														<th align="center" width="13%">Chọn</th>
													</tr>
													
								
           	 	
													
													<%
												
									                	for(int i = 0; i < hoadonList.size(); i++)
									                	{
									                		geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon hoadon = hoadonList.get(i);
										               		%>
													<tr class="tbdarkrow">
													<td> 
													<input style="width: 100%;" name="rowIdNcc" type="text" readonly="readonly" value=<%=(i+1)%>>
													 <input type="hidden" style="width: 100%;" value="<%= hoadon.getId() %>" name= "idHoaDonNcc" readonly="readonly" >  
													</td>
													<td>
													  <input type="hidden" style="width: 100%;" value="<%= hoadon.getLoaihd1() %>" name= "loaiHoaDonNcc" readonly="readonly" >  
													  <input style="text-align:left;" name="tenLoaiHdNcc" id=<%="tenLoaiHdNcc#"+(i+1) %> type="text" readonly="readonly" value="<%=hoadon.getTenloaihd1() %>" >
													</td>
													<td> <input style="width: 100%;text-align:center" name="ngayHoaDonNcc" id=<%="ngayHoaDonNcc#"+(i+1) %> type="text" readonly="readonly" value=<%=hoadon.getNgay() %> ></td>
													<td> <input style="width: 100%;text-align:center" name= "soHoaDonNcc" id = <%="soHoaDonNcc#"+(i+1) %> type="text" readonly="readonly" value=<%=hoadon.getSo() %> ></td>
													<td> <input style="width: 100%;text-align:right" name="soTienNcc" id=<%="soTienNcc#"+(i+1) %> type="text" readonly="readonly" value=<%=hoadon.getSoTienGoc()%> > </td>
													<td> <input style="width: 100%;text-align:right" name= "phaiXoaNoNcc" id=<%="phaiXoaNoNcc#"+(i+1) %> type="text" value="<%=hoadon.getTongtiencoVAT()%>" readonly="readonly" >  </td>
													<td> <input style="width: 100%;text-align:right" name= "xoaNoNcc" id=<%="xoaNoNcc#"+(i+1) %> type="text" value="<%=hoadon.getThanhtoan()%>" readonly="readonly" > </td>
													<td> <input style="width: 100%;text-align:right" name= "conLaiNcc" id=<%="conLaiNcc#"+(i+1) %> type="text" value="<%=hoadon.getConlai()%>" readonly="readonly" > </td>
													
													<%if("0".equals(hoadon.getThanhtoan())||"".equals(hoadon.getThanhtoan())){ %>
													<td> <input style="width: 100%;" name="selectNcc" id=<%= "selectNcc#"+(i+1) %> type="checkbox" onchange="tinhToanChenhLech();" disabled="disabled" > </td>		
													<%}else{ %>
													<td> <input style="width: 100%;" name="selectNcc" id=<%= "selectNcc#"+(i+1) %> type="checkbox" checked="checked"  onchange="tinhToanChenhLech();" disabled="disabled" > </td>
													<%} %>
													</tr>
													<%} %>
												

												</table></td>
										</tr>

									</TABLE>

								</FIELDSET></TD>
								
								
								
						</TR>
						
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Khách hàng </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
									<TR>
											<TD class="plainlabel" valign="middle" width="150px">Khách hàng:  </TD>
											<TD class="plainlabel" valign="middle">
		                     		<input type="text" id="kh" name="kh" disabled="disabled"  value = "<%=obj.getKhTen() %>" style="width: 500px">  
		                     		<input type="hidden" id="khId" readonly="readonly"  name="khId" value = "<%=obj.getKhId() %>" style="width: 500px" >
                        		</TD>
                        		<TD class="plainlabel" valign="middle">Tổng </TD> 
                        		<TD class="plainlabel" valign="middle"><input readonly="readonly" id = "tongKH" type="hidden" value = "0">
                        		<input readonly="readonly" id = "tongCanTruKH" type="text" value = "0"> </TD> 
										</TR>

										
										<TR>
											<td colspan="4">

												<table width="100%" cellpadding="0px" cellspacing="1px">
													<tr class="tbheader">
														<th align="center" width="5%">STT</th>
														<th align="center" width="12%">Loại hóa đơn</th>
														<th align="center" width="10%">Ngày hóa đơn</th>
														<th align="center" width="12%">Số hóa đơn</th>
														<th align="center" width="13%">Số tiền gốc</th>
														<th align="center" width="13%">Phải xóa nợ</th>
														<th align="center" width="13%">Xóa nợ</th>
														<th align="center" width="13%">Còn lại</th>
														<th align="center" width="13%">Chọn</th>
													</tr>
													
													
           	 	
           	 	
													
													
													
													 <%
                	for(int i = 0; i < hoaDonKHList.size(); i++)
                	{
                		IHoadon hoadon = hoaDonKHList.get(i);
	               		%>
													<tr class="tbdarkrow">
													<td >
													 <input style="width: 100%;" name="rowIdKH" type="text" readonly="readonly" value=<%=(i+1)%>>
													 <input type="hidden" style="width: 100%;" value="<%= hoadon.getId() %>" name= "idHoaDonKH" readonly="readonly" > 
													 </td>
													  <td > 
													 	<input type="hidden" style="width: 100%;" value="<%= hoadon.getLoaihd() %>" name= "loaiHoaDonKH" readonly="readonly" > 
													    <input style="width: 100%;text-align:center" name="tenloaiHoaDonKH" id=<%="tenloaiHoaDonKH#"+(i+1) %> type="text" readonly="readonly" value="<%=hoadon.getTenloaihd() %>" >
													 </td>
													<td > <input style="width: 100%;text-align:center" name="ngayHoaDonKH" id=<%="ngayHoaDonKH#"+(i+1) %> type="text" readonly="readonly" value=<%=hoadon.getNgay() %> ></td>
													<td> <input style="width: 100%;text-align:center" name= "soHoaDonKH" id = <%="soHoaDonKH#"+(i+1) %> type="text" readonly="readonly" value=<%= hoadon.getSo() %> ></td>
													
													<td>
													<input style="width: 100%;text-align:right" name="sotienKH" type="text" id = <%="sotienKH#"+(i+1) %> value=<%= hoadon.getTongtiencoVAT()  %> readonly="readonly"> 
													
													</td>
													
													<td> <input style="width: 100%;text-align:right" name="phaiXoaNoKH" type="text" id = <%="phaiXoaNoKH#"+(i+1) %> value=<%= hoadon.getSotienno()%> readonly="readonly"></td>
													<td> <input style="width: 100%;text-align:right" name="xoaNoKH" type="text" id = <%="xoaNoKH#"+(i+1) %> value = <%=hoadon.getThanhtoan()%> readonly="readonly"  ></td>
													<td><input style="width: 100%;text-align:right" name="conlaiKH" type="text" id = <%="conLaiKH#"+(i+1) %> value=<%=hoadon.getConlai_CantruCN() %> readonly="readonly"> </td>
													<td>
													<%if("0".equals(hoadon.getThanhtoan())||"".equals(hoadon.getThanhtoan())){ %>
													<input style="width: 100%;" disabled="disabled" name="selectKH" id = <%="selectKH#"+(i+1)%> type="checkbox" onchange="tinhToanChenhLech();">  
												    <%}else{ %>
												    <input style="width: 100%;" disabled="disabled" name="selectKH" id = <%="selectKH#"+(i+1)%> type="checkbox" checked="checked" onchange="tinhToanChenhLech();">
												    <%} %>
												    </td>
													</tr>
													<%} %>
													
												
												</table></td>
										</tr>

									</TABLE>

								</FIELDSET></TD>
								
								
								
						</TR>
						
					</TABLE></TD>
			</TR>
		</TABLE>




	</form>
	<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<script type="text/javascript">

jQuery(function()
{		
	$("#ncc").autocomplete("ErpNhaCungCapList.jsp");
	$("#kh").autocomplete("ErpKhachhangCanTruList.jsp");
	
	<% if( obj.getTrangthai().length()==0){%>
	
	replaces();
	
	<% }%>
	
	
	

});	
tinhToanChenhLech();

</script>
</BODY>
</HTML>

<%
try{
	if( hoadonList!=null){
		hoadonList.clear();
	}
	
}catch(Exception er){
	
}
%>

<%	}%>

