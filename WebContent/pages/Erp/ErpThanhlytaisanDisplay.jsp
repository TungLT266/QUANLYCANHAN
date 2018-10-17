<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.thanhlytaisan.*"%>
<%@ page import="geso.traphaco.erp.beans.thanhlytaisan.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@ page import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IErpThanhlytaisan dtltsBean = (IErpThanhlytaisan) session.getAttribute("dtltsBean");
	List<ISanpham> spList = dtltsBean.getSpList();
	List<IDonvi> dvList = dtltsBean.getDvList();
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
<script type="text/javascript" src="../scripts/erp-tsList.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script language="javascript" type="text/javascript">

function replaces()
{
	var khId = document.getElementById("khId");
	var khTen = document.getElementById("khTen");
	
	var tem = khId.value;
	if(tem == "")
	{
		khTen.value = "";
	}
	else
	{
		if(parseInt(tem.indexOf(" - ")) > 0)
		{
			khId.value = tem.substring(0, parseInt(tem.indexOf(" - ")));
			
			tem = tem.substr(parseInt(tem.indexOf(" - ")) + 3);
			khTen.value = tem.substring(0, tem.indexOf(" [ ") );
						
			if(khId.value != '')
			{
				document.forms['dtltsForm'].action.value='submit';
			    document.forms["dtltsForm"].submit();
			}
		}
	}
	
	var idsp = document.getElementsByName("idsp");
	var masp = document.getElementsByName("masp");
	var tensp = document.getElementsByName("tensp");
	var donvitinh = document.getElementsByName("donvitinh");
	var soluong = document.getElementsByName("soluong");
	var dongia = document.getElementsByName("dongia");	
	var thanhtien = document.getElementsByName("thanhtien");
	var nhomhang = document.getElementsByName("nhomhang");
//	var phantramthue = document.getElementsByName("phantramthue");
	var ngaygiao = document.getElementsByName("ngaygiao");
	
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
	 				if((idsp[i].value == idsp[k].value) && idsp[k].value.length !=0 && ngaygiao[i].value == ngaygiao[k].value && ngaygiao[k].value.length != 0 )
 					{
 						ngaygiao.item(k).value= '';
 					}
	 			}
	 		}
			
			var sp = masp.item(i).value;
			var pos = parseInt(sp.indexOf(" - "));
			var slg = soluong.item(i).value.replace(/,/g,""); 
			
			if(pos > 0)
			{
				masp.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
				tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
				
				sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
				donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				nhomhang.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));

//				nhomhang.item(i).value = "Testing";
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				idsp.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				idsp.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
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
					
				}
			}
			else			
			{
				dongia.item(i).value = "";
				thanhtien.item(i).value = "";				
//				phantramthue.item(i).value = "";
			}
		}
		else
		{
			idsp.item(i).value = "";
			tensp.item(i).value = "";
			donvitinh.item(i).value = "";
			soluong.item(i).value = "";
			dongia.item(i).value = "";
			thanhtien.item(i).value = "";			
//			phantramthue.item(i).value = "";
		}
	}	
	
	TinhTien();
	setTimeout(replaces, 300);
}
	
	function TinhTien()
	{

		var thanhtien = document.getElementsByName("thanhtien");	

		var tongtien = 0;
				
		for(var i = 0; i < thanhtien.length; i++)
		{
			if(thanhtien.item(i).value != "")
			{	
				var tt = thanhtien.item(i).value.replace(/,/g,""); 
				tongtien = parseFloat(tongtien) + parseFloat(tt);
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
		 var khId = document.getElementById("khId");
		 var loai = document.getElementById("loai");
		 var error = document.getElementById("Msg");
		 
		 
		 
		 if(khId.value == "")
		 {
			khId.focus(); 
			error.value="Vui lòng chọn nhà cung cấp";
			return;
		 }
		 
		 if(loai.value=="")
		 {
			loai.focus();
		 	error.value="Vui lòng chọn loại!";
			return;
		 }
		 
		 if(checkSanPham() ==false)
		 {	
			error.value="Không có sản phẩm nào được chọn";
			return;
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['dtltsForm'].action.value='save';
	     document.forms['dtltsForm'].submit();
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
		
		 document.forms['dtltsForm'].action.value='submit';
	     document.forms["dtltsForm"].submit();
	 }
	 
	 function changeNoiDung()
	 {
		 document.forms['dtltsForm'].action.value='changeSP';
	     document.forms["dtltsForm"].submit();
	 }
	 
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dtltsForm" method="post" action="../../ErpThanhlytaisanUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="1" />
		<input type="hidden" name="id" value="<%= dtltsBean.getId() %>" />
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý tài sản > Thanh lý tài sản > Cập nhật</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="../../ErpThanhlytaisanSvl?userId=<%=userId%>"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px"
					longdesc="Quay ve" style="border-style: outset"></A> <span id="btnSave"> <A href="javascript:saveform()"> <IMG
						src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset"></A>
				</span>
				
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="Msg" rows="1" readonly="readonly" style="width: 100%%"><%=dtltsBean.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thanh lý tài sản</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Ngày</TD>
								<TD class="plainlabel" valign="top"  colspan = 3>
									<input type="text" class="days" id="ngay" name="ngay"
											value="<%= dtltsBean.getNgay() %>" maxlength="10" readonly />
								</TD>
								
							</TR>
							<TR>
								<TD class="plainlabel">Mã khách hàng</TD>
								<TD class="plainlabel">
									<input type="text" id="khId" name="khId" value="<%= dtltsBean.getKH() %>" >
								</TD>
								<TD class="plainlabel">Tên khách hàng</TD>
								<TD class="plainlabel">
									
									<input type="text" id="khTen" name="khTen" value="<%= dtltsBean.getKhTen() %>" readonly="readonly" >
								<%if(dtltsBean.getKH().equals("107385")||dtltsBean.getKH().equals("107573")||dtltsBean.getKH().equals("107689")||dtltsBean.getKH().equals("107689")||dtltsBean.getKH().equals("107695")){ %>    
			           			<a href="" id="thongtinkh" rel="sub" >
								<img  alt="Thông tin khách hàng vãng lai " src="../images/vitriluu.png"></a>
		                     	<DIV id="sub" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
		                                  background-color: white; width: 500px; padding: 4px;">
		                         <table width="100%" align="center">	
		                             <tr>
		                                 <td> Họ và tên người mua hàng: </td>
		                                 <td> 
		                                	  <input type="text" id="nguoimuahang" name="nguoimuahang"  style="width: 100%" value="<%=dtltsBean.getNguoiMuaHang()%>" > 
		                                 </td>
		                             </tr>
		                             <tr>
		                                 <td> Đơn vị: </td>
		                                 <td> 
		                                	  <input type="text" id="donvi" name="donvi"  style="width: 100%" value="<%=dtltsBean.getDonVi() %>" > 
		                                 </td>
		                             </tr>		                             
		                             <Tr>
		                                 <td> Địa chỉ: </td>
		                                 <td> 
		                                		 <input type="text" id="diachi" name="diachi"  style="width: 100%" value="<%=dtltsBean.getDiaChi()%>" > 
		                                 </td>
		                             </tr>
		                             <Tr>
		                                 <td> Mã số thuế: </td>
		                                 <td> 
		                                		 <input type="text" id="masothue" name="masothue"  style="width: 100%" value="<%=dtltsBean.getMST() %>" > 
		                                 </td>
		                             </tr>
		                             
		                         </table>
		                          <div align="right">
		                             <label style="color: red" ></label>
		                             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                             <a href="javascript:dropdowncontent.hidediv('sub')">Hoàn tất</a>
		                          </div>
		                     </DIV>  
		                     <%} %> 
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel">Loại</TD>
								<TD class="plainlabel" colspan=3>
									<select name="loai" id="loai" onChange="changeNoiDung();">
                            	<%
                        			if(dtltsBean.getLoai().equals("1")){ %>
                        				<option value="1" selected >Tài sản</option>
                        				<option value="2"  >Công cụ dụng cụ</option>
                        		<%	}else if(dtltsBean.getLoai().equals("2")){ %>
                        				<option value="1" >Tài sản</option>
                        				<option value="2" selected >Công cụ dụng cụ</option>
	                        	<%	}else{ %>
                        				<option value="1" >Tài sản</option>
                        				<option value="2" >Công cụ dụng cụ</option>
								<%	} %>	                        	
									
									
								</TD>
								
							</TR>
							<TR>
								<TD class="plainlabel">Tổng tiền chưa VAT</TD>
								<TD class="plainlabel"><input type="text" name="BVAT" id="BVAT" value="<%=dtltsBean.getTongtienchuaVat()%>" style="text-align: right;"
									readonly="readonly"> VNĐ</TD>
								<TD class="plainlabel">VAT</TD>
								<TD class="plainlabel"><input type="text" name="VAT" id="VAT" value="<%=dtltsBean.getVat()%>" style="text-align: right;"
									onkeypress="return keypress(event);"> %</TD>
							</TR>
							<TR>
								<TD class="plainlabel">Tổng tiền sau VAT</TD>
								<TD class="plainlabel" colspan=3><input type="text" name="AVAT" id="AVAT" value="<%=dtltsBean.getTongtiensauVat()%>" style="text-align: right;"
									readonly="readonly"> VNĐ</TD>
							</TR>
							<TR>
								<TD class="plainlabel">Diễn giải</TD>
								<TD class="plainlabel" colspan="3"><input type="text" name="ghichu" id="ghichu" value="<%= dtltsBean.getGhiChu()%>" style="text-align: left;"></TD>
							</TR>
						</TABLE>
						<hr>
					</div>
					
					
					<div align="left" style="width: 100%; float: none; clear: none;">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1">
							<TR class="tbheader">
								<TH align="center" width="30px">STT</TH>
								
								<TH align="center" width="10%" >Mã tài sản</TH>
								<TH align="center" width="15%">Diễn giải</TH>
								
								<TH align="center" width="5%">ĐVT</TH>
								<TH align="center" width="5%">Số lượng</TH>
								<TH align="center" width="10%">Đơn giá</TH>
								<TH align="center" width="10%">Thành tiền</TH>
								
						<% if(dtltsBean.getLoai().equals("1")){ %>
								<TH align="center" width="10%">Nhóm tài sản</TH>
						<% } else if (dtltsBean.getLoai().equals("2")){%>
								<TH align="center" width="10%">Nhóm công cụ dụng cụ</TH>
						<% }else{ %>
								<TH align="center" width="10%">Nhóm tài sản</TH>
						<%} %>
								<TH align="center" width="5%">Ngày giao</TH>

							</TR>
							
							<% int count = 0; 
								if(spList.size() > 0) { 
									for(int i = 0; i < spList.size(); i++) { 
										ISanpham sp = spList.get(i); %>
										
										<tr>
											<TD align="center" width="2%">
												<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
												<input type="hidden" value="<%= sp.getPK_SEQ() %>" name="idsp" style="width: 100%" readonly="readonly" >
											</TD>
											<TD align="center" width="8%" >
												<input type="text" name="masp" style="width: 100%" value="<%= sp.getMasanpham() %>"  onkeyup="ajax_showOptions(this,'thanhlytaisan',event)" AUTOCOMPLETE="off" > 
											</TD>
											<TD align="left" width="10%">
												<input type="text" value="<%= sp.getTensanpham() %>" name="tensp" style="width: 100%" readonly="readonly" > 
											</TD>
											<TD align="center" width="6%">
												<input type="text" value="<%= sp.getDonvitinh() %>" name="donvitinh" style="width: 100%" > 
											</TD>
											<TD align="center" width="7%">
												<input type="text" value="<%= sp.getSoluong() %>" name="soluong" style="width: 100%; text-align: right;" > 
											</TD>
											<TD align="center" width="8%">
												<input type="text" value="<%= sp.getDongia() %>" name="dongia" style="width: 100%; text-align: right;" > 
											</TD>
											<TD align="center" width="8%">
												<input type="text" value="<%= sp.getThanhtien() %>"  name="thanhtien" style="width: 100%; text-align: right;" > 
											</TD>
											
											<TD align="center" width="8%">
												<input type="text" value="<%= sp.getNhomhang() %>" style="width: 100%" name="nhomhang" readonly="readonly" > 
											</TD>
											
											<TD align="center" width="7%">
												<input type="text" value="<%= sp.getNgaynhan() %>" style="width: 100%" name="ngaygiao" readonly class="days" > 
											</TD>
										</tr>
										
									<% count++; }
								} %>
							
							<% for(int i = count; i < 40; i++) { %>
							
								<tr>
									<TD align="center" width="2%">
										<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
										<input type="hidden" value="" name="idsp" style="width: 100%" readonly="readonly" > 
									</TD>
									<TD align="center" width="8%" >
										<input type="text" value="" name="masp" style="width: 100%" onkeyup="ajax_showOptions(this,'thanhlytaisan',event)" AUTOCOMPLETE="off" > 
									</TD>
									<TD align="left" width="10%">
										<input type="text" value="" name="tensp" style="width: 100%" readonly="readonly" > 
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
									
									<TD align="center" width="5%" style="display: none">
										<input type="text" value="" style="width: 100%" name="phantramthue" > 
									</TD>

									<TD align="center" width="8%">
										<input type="text" value="" style="width: 100%" name="nhomhang" readonly="readonly" > 
									</TD>
									
									<TD align="center" width="7%">
										<input type="text" value="" style="width: 100%" name="ngaygiao" readonly class="days" > 
									</TD>
	
								</tr>
							<% } %>
							
							
							<tr class="tbfooter">
								<td colspan="12">&nbsp;</td>
							</tr>
						</TABLE>
					</div>
					
					
				</fieldset>
			</div>
		</div>
<script type="text/javascript">
	replaces();
	dropdowncontent.init("searchlink", "right-bottom", 500, "click");
	dropdowncontent.init('thongtinkh', "left-top", 500, "click");
	jQuery(function()
	{		
		$("#khId").autocomplete("ErpKhThanhlytaisan.jsp");
	});	
	
</script>

	<%
	if( dvList!=null){
		dvList.clear();
	}
	if(spList !=null){
		spList.clear();
	}
	
		dtltsBean.close();
		session.setAttribute("dtltsBean", null) ;  
	
	%>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</html>
<% } %>