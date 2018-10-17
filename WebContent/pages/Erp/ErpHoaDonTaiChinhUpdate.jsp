<%@page import="javax.print.DocFlavor.READER"%>
<%@page import="geso.traphaco.erp.beans.hoadontaichinh.IErpHoaDonTaiChinh"%>
<%@page import="java.util.Formatter"%>
<%@page import="java.util.Formattable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.SimpleDateFormat" %>


<% String userId = (String)session.getAttribute("userId");
String userTen=(String)session.getAttribute("userTen");
%>

<%	
	
  	IErpHoaDonTaiChinh hdBean=(IErpHoaDonTaiChinh)session.getAttribute("obj");
	ResultSet rslistdh=hdBean.getrsddhChuaXuathd();
	ResultSet ddhRs=hdBean.getDonhangRs();
	ResultSet khRs=hdBean.getkhRs();
	ResultSet khoRs=hdBean.getKhoRs();
	ResultSet htxuathdRs=hdBean.gethinhthucxhdRs();
	ResultSet ttcpRs=hdBean.getTTCPRs();
	
	boolean checkdh = hdBean.checkdonhang();
	
	String[] ddh=hdBean.getDonDatHang();
	Hashtable<String, String> htbddh = new Hashtable<String, String>();
	int i=0;
	if(ddh!=null){
		while(i< ddh.length){
			htbddh.put(ddh[i],ddh[i]);
			i=i+1;
		}
	}
	
	String[] spId = hdBean.getSpId();
	String[] spMa = hdBean.getSpMa(); 
	String[] spTen = hdBean.getSpTen();
	String[] spDonvi = hdBean.getSpDonvi();
	String[] spSoluong = hdBean.getSpSoluong();
	String[] spDongia = hdBean.getSpDongia();
	String[] spChietkhau = hdBean.getSpChietKhau();
	String[] spTienCK= hdBean.getSpTienCK();
	String[] spVat = hdBean.getSpVat();
	String[] spTienThue= hdBean.getSpTienVat();
	String[] spThanhtien= hdBean.getSpThanhTien();
	String[] spMadonvi = hdBean.getSpMaDonvi();
	String[] spDongiagiamgia = hdBean.getSpDongiagiamgia();
	
 	String[] spKhoTT = hdBean.getSpKhoTT();
 	String[] spnr = hdBean.getSpnr();
 	
	NumberFormat formatter=new DecimalFormat("#,###,###");
	NumberFormat formatter1=new DecimalFormat("#,###,###.##");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
	#mainContainer{
		width:660px;
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
		z-index:100;
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
		z-index:5;
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
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>


<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_erp_bgmuanpp.js"></script>

<script type="text/javascript" src="../scripts/DocTienTiengViet.js"></script>
<script type="text/javascript" src="../scripts/formattien.js"></script>


<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();});
</script>



<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_erp_bgmuanpp.js"></script>

<script type="text/javascript" src="../scripts/DocTienTiengViet.js"></script>
<script type="text/javascript" src="../scripts/formattien.js"></script>

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

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
    $(document).ready(function() { 
    	$(".select2").select2();
	});
</script>


<script src="../scripts/ui/jquery.scrollTableBody-1.0.0.js" type="text/javascript"></script>
<!-- <script type="text/javascript">
            $(function() {
                $('#tableDDH').scrollTableBody({rowsToDisplay:9});
            });
</script> -->

<script type="text/javascript">






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


	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 ||  keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	
	function saveform()
	 {	 
		if (document.forms["dhForm"]["ngayxuathd"].value.length == 0) {
			setErrors("Vui lòng nhập ngày xuất hóa đơn");					
			return;
		}
		 
		 
		 if (document.forms["dhForm"]["ngaycongno"].value.length == 0) {
				setErrors("Vui lòng chọn ngày ghi nhận công nợ");					
				return;
			}
		 
		 if (document.forms["dhForm"]["kyhieu"].value.length == 0) {
				setErrors("Vui lòng nhập ký hiệu hóa đơn");					
				return;
			}	
		 
		 if (document.forms["dhForm"]["sohoadon"].value.length != 7) {
				setErrors("Số hóa đơn không được để trống và phải là 7 số");					
				return;
			}	 
		 
	 	 var tienavat = document.getElementById("tienavat");
			 
		 var total_tienavat = parseFloat(tienavat.value.replace(/,/g,""));
		 
		 if(total_tienavat <=0 )
		 {
		 	setErrors("Tổng tiền thực thu không được nhỏ hơn hoặc bằng 0, xin vui lòng kiểm tra lại.");					
			return;
		 }
		 
		var countdongtrongdonhang = parseFloat(document.getElementById("lenght").value);
		 
		 if(countdongtrongdonhang > 16){
			 alert("Đơn hàng vừa chọn có số sản phẩm vượt quá 16 dòng, các sản phẩm còn lại sẽ được xuất tiếp lần sau !");
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='0' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['dhForm'].action.value='save';
	     document.forms['dhForm'].submit();
	 }
	 
	function chotform()
	 {	 
		if (document.forms["dhForm"]["ngayxuathd"].value.length == 0) {
			setErrors("Vui lòng nhập ngày xuất hóa đơn");					
			return;
		}
		 
		 
		 if (document.forms["dhForm"]["ngaycongno"].value.length == 0) {
				setErrors("Vui lòng chọn ngày ghi nhận công nợ");					
				return;
			}
		 
		 if (document.forms["dhForm"]["kyhieu"].value.length == 0) {
				setErrors("Vui lòng nhập ký hiệu hóa đơn");					
				return;
			}	
		 
		 if(document.forms["dhForm"]["sohoadon2"].value.length > 0){
			 if (document.forms["dhForm"]["sohoadon2"].value.length != 7) {
					setErrors("Số hóa đơn kèm theo không được để trống và phải là 7 số");					
					return;
				}
		 }
		 
		 if(document.forms["dhForm"]["sohoadon"].value == document.forms["dhForm"]["sohoadon2"].value){
			 	setErrors("Số hóa đơn không được trùng với số hóa đơn đính kèm");					
				return;
		 }
		 
	 	 var tienavat = document.getElementById("tienavat");
			 
		 var total_tienavat = parseFloat(tienavat.value.replace(/,/g,""));
		 
		 if(total_tienavat <=0 )
		 {
		 	setErrors("Tổng tiền thực thu không được nhỏ hơn 0, xin vui lòng kiểm tra lại.");					
			return;
		 }
		 
		var countdongtrongdonhang = parseFloat(document.getElementById("lenght").value);
		 
		 if(countdongtrongdonhang > 16){
			 alert("Đơn hàng vừa chọn có số sản phẩm vượt quá 16 dòng, các sản phẩm còn lại sẽ được xuất tiếp lần sau !");
		 }
		 
		 document.getElementById("btnSave2").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='0' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['dhForm'].action.value='chot';
	     document.forms['dhForm'].submit();
	 }
	
	 function submitform()
	 { 
		congDonSPCungMa();
		document.forms['dhForm'].action.value='submit';
	    document.forms["dhForm"].submit();
	 }
	 
	 function loadcontent()
	 {             
		document.forms['dhForm'].action.value='reload';
	    document.forms["dhForm"].submit();
	 }
	 
	 function ChotForm()
	 {
		 congDonSPCungMa();
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		 for(var k = 0; k < masp.length; k++)
		 {
			 if(masp.item(k).value != "")
				if(soluong.item(k).value == "" || tensp.item(k).value == "")
				{
					alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
					return;
				}
		 }
		 document.forms['dhForm'].action.value='chotdonhang';
		 document.forms['dhForm'].submit();
	 }
	 
	 function congDonSPCungMa()
	 {
		var masp = document.getElementsByName("masp");
		var soluong = document.getElementsByName("soluong");
		var ii;
		for(ii = 0; ii < (masp.length - 1) ; ii++)
		{
			for( j = ii + 1; j < masp.length; j++)
			{
				if(masp.item(ii).value != "" && masp.item(ii).value == masp.item(j).value)
				{
					//alert(masp.item(ii).value + "-" + masp.item(j).value);				
					if(soluong.item(j).value == "")
						soluong.item(j).value = "0";
					
					soluong.item(ii).value = parseInt(soluong.item(ii).value) + parseInt(soluong.item(j).value);
					masp.item(j).value = "";
				}
			}
		}
	 }
	 function All()
	 { 
		var npp = document.getElementsByName("ddhid");
		for(i=0; i<npp.length; i++){
		 	if(document.dhForm.all.checked ==true){
		 	  npp[i].checked = true;
			}else{
				npp[i].checked = false;
			}
		}
	}
		
		function getinfoddh(){
			document.forms['dhForm'].action.value='loadddh';
			 document.forms['dhForm'].submit();
		}
		
		function setErrors(errorMsg) {
			var errors = document.getElementById("dataerror");
			errors.value = errorMsg;
		}
		
		function TinhTien()
		{
			// từng sản phẩm
			
		    var spId = document.getElementsByName("spId");
			var spMa = document.getElementsByName("spMa");
			var spsoluong = document.getElementsByName("soluong");
			var spdongia = document.getElementsByName("dongia");
			var spdongiack = document.getElementsByName("dongiack");
			var spvat =  document.getElementsByName("vat"); //% vat
			var sptienthue = document.getElementsByName("tienthue");
			var spthanhtien = document.getElementsByName("thanhtien");
			
			// 
			var tienbvat = document.getElementById("total_thanhtien");
			var ckthuongmaihd = document.getElementById("ckthuongmaihd");
			var tienthue = document.getElementById("total_tienvat");
			var tienavat = document.getElementById("tienavat");
						
			var total_tienbvat=0;
			var total_ckthuongmaihd = 0;
			var total_ckthuongmaihd_goc = 0;
			var total_tienthue=0;
			var total_tienavat = 0;
		    	
		   // 	
		    var i;
		   
		   for(i=0; i < spId.length; i++){
			   if(spId.item(i).value != "")
				{
				   if(spsoluong.item(i).value.replace(/,/g,"") > 0){
					   total_tienbvat += parseFloat(spthanhtien.item(i).value.replace(/,/g,""));
				   }
				}
		   }
		   
		   total_ckthuongmaihd = parseFloat(ckthuongmaihd.value.replace(/,/g, "") );
		   
		   if(ckthuongmaihd.value == '') ckthuongmaihd.value = 0;
		   
		   
		   // Nếu người dùng nhập chiết khấu thương mại hóa đơn lớn hơn số tiền chiết khấu thương mại còn lại thì k cho phép nhập
		   				   
		   total_tienthue = roundNumber((total_tienbvat - total_ckthuongmaihd)*0.1,0);
		   total_tienavat = (total_tienbvat - total_ckthuongmaihd) + total_tienthue;
			
		   document.getElementById("total_thanhtien").value = DinhDangTien(total_tienbvat);
	       document.getElementById("total_tienvat").value = DinhDangTien(total_tienthue);
		   document.getElementById("tienavat").value=DinhDangTien(total_tienavat);
		   document.getElementById("ckthuongmaihd").value=DinhDangTien(total_ckthuongmaihd);
		}
		
		function formatTien(e)
		{
			var giatrinhap = e.value;
			e.value = DinhDangTien(giatrinhap);
			
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
		
		
		function goBack()
		 {
		  	window.history.back();
		 }
		
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dhForm" method="post" action="../../ErpHoaDonTaiChinhUpDateSvl">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type="hidden" name="action" value='reload'>
<input type="hidden" name="id" value="<%=hdBean.getId()%>">
<INPUT type="hidden" name="trangthai" value=''>   
<input type="hidden" name='tenform' value="UpdateForm">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0" >
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								   <TD align="left"  class="tbnavigation">&nbsp; Quản lý cung ứng > Quản lý bán hàng > Xuất hóa đơn tài chính > Chỉnh sửa </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn&nbsp;&nbsp;  <%= userTen%> &nbsp; </TD>
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href="javascript:goBack();" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
										    
										    <TD width="30" align="left" ><A id = "btnSave" href="javascript:saveform()" ><img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A></TD>					
											<TD width="2" align="left" ><span id="btnSave2"><A href="javascript:chotform()" ><IMG src="../images/Chot.png" title="Duyệt yêu cầu" alt="Luu lai" border ="1px" style="border-style:outset"></A></span></TD>
											 <TD width="30" align="left" ><A href="../../ErpHoaDonTaiChinhPdfSvl?userId=<%=userId %>&print=<%=hdBean.getId() %>" ><img src="../images/Printer30.png" alt="Print"  title="Print"  border ="1px" longdesc="Print" style="border-style:outset"></A></TD> 						    
										   	<TD width="2" align="left" >&nbsp;</TD>
							    			<TD width="30" align="left"></TD>
								    		<TD align="left" >&nbsp;</TD>
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="1" width="100%" cellpadding = "1" cellspacing = "0" style="border-color:gray;" >
								<tr>
								
								 <TD align="left" class="legendtitle">
									<fieldset  >
									<legend> Thông báo nhập liệu</legend>
				    				<textarea name="dataerror"  id="dataerror" style="width:100%;margin:0px " readonly="readonly" rows="1"><%=hdBean.getMessage() %></textarea>
							  	</fieldset>
							  	 </TD>
								</tr>
								
								<TR class="plainlabel">
									<TD align="left" >						
										
										<TABLE  border="0" bordercolor="white" width="100%" cellpadding = "3" cellspacing = "0" style="padding-left:2px ;" >
											<tr class="plainlabel">
											<th width='15%' ></th>
											<th width='35%' ></th>
											<th width='15%' ></th>
											<th width='35%' ></th>
											</tr>
											
											<%-- <TR class="plainlabel" >
											    <TD>
													Hình thức xuất hóa đơn
												</TD>
												<td>
												   <select name="hinhthucxhd" id="hinhthucxhd" class="select2" style="width: 200px" onchange="loadcontent();">
												    <% 
												   String[] mangchuoi2=new String[]{"Đơn hàng","Phiếu xuất kho"};
												   for(int k=0;k < mangchuoi2.length;k ++ ){
													   
													   if(hdBean.gethinhthucxhd().equals(mangchuoi2[k])) {
														   %>
														    	<option value="<%=mangchuoi2[k] %>" selected="selected"><%=mangchuoi2[k] %> </option>
														   <%
													   }else{
														   %>
													    	<option value="<%=mangchuoi2[k] %>" ><%=mangchuoi2[k] %> </option>
													  		 <%  
													   }
												   }
												  %>
												   </select>
												</td>
												<TD colspan="2"></TD>
											</TR> --%>
											
											<TR class="plainlabel" >
											    <TD>
													Nội dung xuất hóa đơn
												</TD>
												<TD class="plainlabel"  width="250px" >
						                    		<select name = "hinhthucxhd"   id = "hinhthucxhd" class="select2" style="width: 300px" onchange="loadcontent();"> 
						                    		<option value=""> </option>
							                        	<%
							                        		if(htxuathdRs != null)
							                        		{
							                        			try
							                        			{
							                        			while(htxuathdRs.next())
							                        			{  
							                        			if( htxuathdRs.getString("pk_seq").equals(hdBean.gethinhthucxhd())){ %>
							                        				<option value="<%= htxuathdRs.getString("pk_seq") %>" selected="selected" ><%= htxuathdRs.getString("ten") %></option>
							                        			<%}else { %>
							                        				<option value="<%= htxuathdRs.getString("pk_seq") %>" ><%= htxuathdRs.getString("ten") %></option>
							                        		 <% } } htxuathdRs.close();} catch(SQLException ex){}
							                        		}
							                        	%>
						                    		</select>
						                   	 	</TD> 
												<TD colspan="2"></TD>
											</TR>
											
											<%if(hdBean.gethinhthucxhd().equals("100001")||hdBean.gethinhthucxhd().equals("100002")) {%>
												<TR class="plainlabel" >
												    <TD>
														Trung tâm chi phí
													</TD>
													<TD class="plainlabel"  width="250px" >
							                    		<select name = "ttcpId"   id = "ttcpId" class="select2" style="width: 300px" onchange="loadcontent();"> 
							                    		<option value=""> </option>
								                        	<%
								                        		if(ttcpRs != null)
								                        		{
								                        			try
								                        			{
								                        			while(ttcpRs.next())
								                        			{  
								                        			if( ttcpRs.getString("pk_seq").equals(hdBean.getttcpId())){ %>
								                        				<option value="<%= ttcpRs.getString("pk_seq") %>" selected="selected" ><%= ttcpRs.getString("ten") %></option>
								                        			<%}else { %>
								                        				<option value="<%= ttcpRs.getString("pk_seq") %>" ><%= ttcpRs.getString("ten") %></option>
								                        		 <% } } ttcpRs.close();} catch(SQLException ex){}
								                        		}
								                        	%>
							                    		</select>
							                   	 	</TD> 
													<TD colspan="2"></TD>
												</TR>
											<%} %>
											
											<TR class="plainlabel" >
											  <TD class="plainlabel">Ngày xuất hóa đơn </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" class="days" id="ngayxuathd" name="ngayxuathd" value="<%=hdBean.getNgayxuathd()%>" maxlength="10" onchange="loadcontent();" /> 
											  </TD>
											  
											  <TD class="plainlabel">Ngày ghi nhận công nợ </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" class="days"
                                     			   id="ngaycongno" name="ngaycongno" value="<%=hdBean.getNgayghino()%>" maxlength="10" /> 
											  </TD>
											</TR>
										
											<TR>
												<TD class="plainlabel" >Tìm từ ngày</TD>
						                    	<TD class="plainlabel">                               
                                              		<input type="text" size="10" class="days" id="tungay" name="tungay" value="<%=hdBean.getTungay()%>" maxlength="10" onchange="loadcontent();" /> 
								  				</TD>
								  				
								  				<TD class="plainlabel" >Đến ngày</TD>
						                    	<TD class="plainlabel">                               
                                              		<input type="text" size="10" class="days" id="denngay" name="denngay" value="<%=hdBean.getDenNgay()%>" maxlength="10" onchange="loadcontent();" /> 
								  				</TD>
								  				
											</TR>
											
                    						<TR class="plainlabel" >
						                    	<TD class="plainlabel" >Tìm khách hàng </TD>
						                    	<TD class="plainlabel"  width="250px" >
						                    		<select name = "nppId"   id = "nppId" class="select2" style="width: 200px" onchange="loadcontent();"> 
						                    		<option value=""> </option>
							                        	<%
							                        		if(khRs != null)
							                        		{
							                        			try
							                        			{
							                        			while(khRs.next())
							                        			{  
							                        			if( khRs.getString("pk_seq").equals(hdBean.getkhId())){ %>
							                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("ten") %></option>
							                        			<%}else { %>
							                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("ten") %></option>
							                        		 <% } } khRs.close();} catch(SQLException ex){}
							                        		}
							                        	%>
						                    		</select>
						                   	 	</TD> 
						                   	 	
						                   	 	<TD>Tìm địa chỉ giao hàng</TD>
												<TD>
													<input type="text" id="diachigiaohangtk" name="diachigiaohangtk"  value="<%=hdBean.getDiachigiaohangtk()%>">
												</TD>
                    						</TR>		
                    						
                    						<TR class="plainlabel" >
						                    	<TD class="plainlabel" >Tìm theo kho </TD>
						                    	<TD class="plainlabel"  width="250px" >
						                    		<select name = "khoId"   id = "khoId" class="select2" style="width: 200px" onchange="loadcontent();"> 
						                    		<option value=""> </option>
							                        	<%
							                        		if(khoRs != null)
							                        		{
							                        			try
							                        			{
							                        			while(khoRs.next())
							                        			{  
							                        			if( khoRs.getString("pk_seq").equals(hdBean.getKhoId())){ %>
							                        				<option value="<%= khoRs.getString("pk_seq") %>" selected="selected" ><%= khoRs.getString("ten") %></option>
							                        			<%}else { %>
							                        				<option value="<%= khoRs.getString("pk_seq") %>" ><%= khoRs.getString("ten") %></option>
							                        		 <% } } khoRs.close();} catch(SQLException ex){}
							                        		}
							                        	%>
						                    		</select>
						                   	 	</TD> 									                   	 	
                    						</TR>		
                    						
											<TR class="plainlabel" >
												 <TD >Ký hiệu hóa đơn </TD>
											  <TD >                               
                                                 <input type="text" size="10" 
                                     			   id="kyhieu" name="kyhieu" value="HL/14P" > 
											    </TD>
											    <td>
													Hình thức thanh toán													 
												</td>
												<td>
												   <select name="hinhthuc" id="hinhthuc" class="select2" style="width: 220px">
												    <% 
												    String[] mangchuoi=new String[]{"TM/CK","Chuyển khoản","Tiền mặt","Thanh toán sau", "Không thu tiền"};
												   for(int k=0;k < mangchuoi.length;k ++ ){
													   
													   if(hdBean.gethinhthuctt().equals(mangchuoi[k])) {
														   %>
														    	<option value="<%=mangchuoi[k] %>" selected="selected"><%=mangchuoi[k] %> </option>
														   <%
													   }else{
														   %>
													    	<option value="<%=mangchuoi[k] %>" ><%=mangchuoi[k] %> </option>
													  		 <%  
													   }
												   }
												  %>
												   </select>
												</td>
											</TR>											
											
											<TR class="plainlabel" >
											    <td>Số hóa đơn</td>
												<td>
													<input type="text" id="sohoadon" name="sohoadon" onkeypress="return keypress(event);"  value="<%=hdBean.getSoHoaDon()%>">
												</td>
											
											</TR>
											
											<TR class="plainlabel" style="display: none;" >
											    <td style="display: none;" >Số hóa đơn kèm theo</td>
												<td style="display: none;" >
													<input type="hidden" id="sohoadon2" name="sohoadon2" onkeypress="return keypress(event);"  value="<%=hdBean.getSoHoaDon2()%>">
												</td>
											
											</TR>
											
											<tr class="plainlabel">
											<td > Ghi chú </td>
											<td>
												<input style="width: 400px " type="text" id="ghichuhd" name="ghichuhd"  value="<%=hdBean.getGhiChu()%>">
											</td>
												
											<td > Tên hàng hóa dịch vụ </td>
											<td>
												<input style="width: 400px " type="text" id="tenhanghoadichvu" name="tenhanghoadichvu"  value="<%=hdBean.getTenHangHoaDichVu()%>">
											</td>
											
											</tr> 
											
											<tr class="plainlabel">
											<td > Địa chỉ giao hàng </td>
											<td >
												<input style="width: 400px " type="text" id="diachigiaohang" name="diachigiaohang"  value="<%=hdBean.getDiachigiaohang()%>">
											</td>											
											                    
											<%if(hdBean.getkhxhd().equals("107385")||hdBean.getkhxhd().equals("107573")||hdBean.getkhxhd().equals("107689")){ %>
											<td > Thông tin khách hàng:  											    
						           			 <a href="" id="thongtinkhxhd" rel="sub" >
											<img  alt="Thông tin khách hàng vãng lai " src="../images/vitriluu.png"></a>
					                     	<DIV id="sub" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                                  background-color: white; width: 500px; padding: 4px;">
					                         <table width="100%" align="center">	
					                             <tr>
					                                 <td style="width:200px"> Họ và tên người mua hàng: </td>
					                                 <td> 
					                                	  <input type="text" id="nguoimuahang" name="nguoimuahang"  style="width: 100%" value="<%=hdBean.getNguoiMuaHang()%>" > 
					                                 </td>
					                             </tr>
					                             <Tr></Tr>
					                             <tr>
					                                 <td> Đơn vị: </td>
					                                 <td> 
					                                	  <input type="text" id="donvinmh" name="donvinmh"  style="width: 100%" value="<%=hdBean.getDonVi() %>" > 
					                                 </td>
					                             </tr>	
					                              <Tr></Tr>	                             
					                             <Tr>
					                                 <td> Địa chỉ: </td>
					                                 <td> 
					                                		 <input type="text" id="diachi" name="diachi"  style="width: 100%" value="<%=hdBean.getDiaChi()%>" > 
					                                 </td>
					                             </tr>
					                              <Tr></Tr>
					                             <Tr>
					                                 <td> Mã số thuế: </td>
					                                 <td> 
					                                		 <input type="text" id="masothue" name="masothue"  style="width: 100%" value="<%=hdBean.getMST() %>" > 
					                                 </td>
					                             </tr>
					                              <Tr></Tr>
					                         </table>
					                          <div align="right">
					                             <label style="color: red" ></label>
					                             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                             <a href="javascript:dropdowncontent.hidediv('sub')">Hoàn tất</a>
					                          </div>
					                     </DIV>  
					                     </td>
					                     <td></td>
					                      <%} else { %>
					                      <td></td>
					                      <td></td>
					                      <%} %> 
					                      </tr>						
																						
											  <tr> 
											  <td colspan="2"  class="legendtitle" >											  
<%-- 											 <%	if(hdBean.gethinhthucxhd().equals("Đơn hàng")) { %>
											   <fieldset >
											   <legend> Chọn đơn đặt hàng</legend>
											   <table id="tableDDH" width="100%">
												<TR class="tbheader">
								                        <TH align="center" width="15%">Số đơn hàng</TH>
								                        <TH align="center" width="15%"> Ngày </TH>
								                        <TH align="center" width="55%"> Khách hàng</TH>
								                        <TH align="center" width="8%"> Chọn </TH>
                   								</TR>
                   								<tbody>
                   								
                   								<%
			         							if(ddhRs != null)
			         							{
			         								String dh = hdBean.getDonhangId();
			         								while(ddhRs.next()){ %>
			           								<tr class= 'tblightrow'>
			           									<td align="center" >
			           										<input type="text" name="pk_seq"  id="pk_seq" value="<%=ddhRs.getString("pk_seq") %>" style="width: 100%; text-align: left; border: none;" readonly="readonly">		
			           									</td >
			           									<td align="center" >                   										
			           										 <input type="text" name="ngaydonhang" value="<%=ddhRs.getString("NgayDonHang") %>" style="width: 100%; text-align: left; border: none;" readonly="readonly">	
			           									</td>
			           									<td>                   										
			           										 <input type="text" name="tenkh" value="<%=ddhRs.getString("KHACHHANG") %>" style="width: 100%; text-align: left; border: none;" readonly="readonly">
			           										 <input type="hidden" name="khId" id="khId" value="<%=ddhRs.getString("KHACHHANG_FK") %> " style="width: 100%; text-align: left; border: none;" readonly="readonly"> 	
			           									</td>
			           									
			  										<% 
			   										 if(hdBean.getDonhangId().trim().contains(ddhRs.getString("pk_seq").trim())){%>
			       										<TD align="center"><input type ="radio" checked="checked" name ="ddhid" value ="<%= ddhRs.getString("pk_seq") %>"  onchange="loadcontent();" ></TD>
			       									<%}else{ %>
			       										<TD align="center"><input type ="radio" id="ddhid" name ="ddhid" value ="<%= ddhRs.getString("pk_seq") %>" onchange="loadcontent();" ></TD>
			       									<%}%>
			       									
			   										</tr>
			   									<% } ddhRs.close(); } else{ %>
			   										<tr class= 'tblightrow'><td></td><td></td></tr>
			   									<%} %>   
                   							<%} else { %> --%>
                   							   <fieldset >
                   							   <legend> Chọn phiếu xuất kho</legend>
											   <table id="tableDDH" width="100%">
												<TR class="tbheader">
								                        <TH align="center" width="15%">Số phiếu xuất kho</TH>
								                        <TH align="center" width="15%"> Ngày </TH>
								                        <TH align="center" width="55%"> Khách hàng</TH>
								                        <TH align="center" width="8%"> Chọn </TH>
                   								</TR>
                   								<tbody>
                   								
                   								<%
			         							if(ddhRs != null)
			         							{
			         								String dh = hdBean.getDonhangId();
			         								while(ddhRs.next()){ %>
			           								<tr class= 'tblightrow'>
			           									<td align="center" >
			           										<input type="text" name="pk_seq"  id="pk_seq" value="<%=ddhRs.getString("pk_seq") %>" style="width: 100%; text-align: left; border: none;" readonly="readonly">		
			           									</td >
			           									<td align="center" >                   										
			           										 <input type="text" name="ngaydonhang" value="<%=ddhRs.getString("NgayDonHang") %>" style="width: 100%; text-align: left; border: none;" readonly="readonly">	
			           									</td>
			           									<td>                   										
			           										 <input type="text" name="tenkh" value="<%=ddhRs.getString("KHACHHANG") %>" style="width: 100%; text-align: left; border: none;" readonly="readonly">
			           										 <input type="hidden" name="khId" id="khId" value="<%=ddhRs.getString("KHACHHANG_FK") %> " style="width: 100%; text-align: left; border: none;" readonly="readonly">			           										 
			           									</td>
			           									
			  										<% 
			   										 if(hdBean.getDonhangId().trim().contains(ddhRs.getString("pk_seq").trim())){%>
			       										<TD align="center"><input type ="checkbox" checked="checked" name ="ddhid" value ="<%= ddhRs.getString("pk_seq") %>"  onchange="loadcontent();" ></TD>
			       									<%}else{ %>
			       										<TD align="center"><input type ="checkbox" id="ddhid" name ="ddhid" value ="<%= ddhRs.getString("pk_seq") %>" onchange="loadcontent();" ></TD>
			       									<%}%>
			       									
			   										</tr>
			   									<% } ddhRs.close(); } else{ %>
			   										<tr class= 'tblightrow'><td></td><td></td></tr>
			   									<%} %>   
                   						<%-- 	<%} %> --%>
                   							
			   									</tbody> 
                   								</table>
                   								</fieldset>
                   								</td>
                   								<td colspan="2" valign="bottom" >
                   								  <table class="plainlabel" width="100%"  style="padding-top:0 ; margin-top:0">
                   								    <tr>
                   								    <td width="40%"> Tổng tiền </td>
                   								     
                   								    <td width="60%"> 
                   								    	<input type="text" style="text-align:right" id="total_thanhtien" name="total_thanhtien" value="<%=formatter.format(hdBean.getThanhTien()) %>" readonly="readonly">
                   								    </td>
                   								    </tr>
                   								    <tr>
                   								    <td>Chiết khấu thương mại</td>
                   								    <td>
                   								    	<%if(hdBean.gethinhthucxhd().equals("100000")) {%>
                   								       <input type="text" style="text-align:right"  id="ckthuongmaihd" name="ckthuongmaihd" value="<%=formatter.format(hdBean.getTienCKThuongMai())%>" onChange="TinhTien();" onkeypress="return keypress(event);">
                   								       <%} else { %>
                   								       <input type="text" style="text-align:right"  id="ckthuongmaihd" name="ckthuongmaihd" value="0" readonly="readonly">
                   								       <%} %>                   								       
                   								      	<a href="" id="nhapghichu" rel="subcontent1" >
														<img  alt="Ghi chú" src="../images/vitriluu.png"> </a>
		                                                <DIV id="subcontent1" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 500px; padding: 4px;">
		                                                    <table width="100%" align="center">
		                                                        <tr>
		                                                        	<td>Ghi chú</td>		                                                            
		                                                            <td> 
		                                                           		 <input type="text" id="ghichucktm" name="ghichucktm"  style="width: 100%" value="<%=hdBean.getGhiChu1() %>"> 
		                                                            </td>		                                                            
		                                                        </tr>		                                                       
		                                                    </table>
		                                                     <div align="right">
		                                                        <label style="color: red" ></label>
		                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                                                        <a href="javascript:dropdowncontent.hidediv('subcontent1')">Hoàn tất</a>
		                                                     </div>
		                                                </DIV>	       
                   								    </td>
                   								    </tr>                   								    
                   								     <tr>
                   								    <td>Tiền Vat</td>
                   								    <td>
                   								      <input type="text" style="text-align:right" id="total_tienvat" name="total_tienvat" value="<%=formatter.format(hdBean.getTienVat())%>" readonly="readonly">
                   								    </td>
                   								    </tr>
                   								      <tr>
                   								    <td>
                   								     Tiền thực thu
                   								    </td>
                   								    <td>
                   								      <input type="text" style="text-align:right" id="tienavat" name="tienavat" value="<%=formatter.format(hdBean.getTienAVAT())%>" readonly="readonly">
                   								    </td>
                   								    </tr>

                   								  </table>
                   								  </td>
                   								</tr>
                   								</TABLE>
								
								  </TD>

							   </TR>	
							   
							  <TR>
							   		<TD>
										<TABLE class="tabledetail" width = "100%" border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
												<TR class="tbheader" >
												<TH width="5%" height="5">STT</TH>
													<TH width="10%" height="20">Mã sản phẩm </TH>
													<TH width="25%">Tên sản phẩm</TH>
													<TH width="8%">Đơn vị</TH>
													<TH width="8%">Số lượng</TH>													
													<TH width="10%">Đơn giá </TH>
													<TH width="5%" style="display: none">VAT </TH>
													<TH width="10%">Đơn giá đã giảm giá </TH>
													<TH width="10%" style="display:none">Tiền thuế </TH>	
													<TH width="15%">Thành tiền</TH>	
												</TR>
								<%
									int count = 1;
									if(spMa != null)
									{											
										for(int k = 0; k < spMa.length; k++)
										{%>
										<tr>											
											<td>
												<input type="text" name="stt" value="<%= count %>" style="width: 100%"  readonly="readonly"  >
												<input type="hidden" name="lenght" id = "lenght" value="<%= spMa.length %>" style="width: 100%"   >  
											</td>
											<td>												
												<input type="hidden" name="spId" value="<%= spId[k] %>" style="width: 100%"   >
												<input type="hidden" name="lenght" id = "lenght" value="<%= spMa.length %>" style="width: 100%"   >  
												<input type="text" name="spMa" value="<%= spMa[k] %>" style="width: 100%"  readonly="readonly"  > 
											</td>
											<td> 
												<input type="text" name="spTen" value="<%= spTen[k] %>" style="width: 100%" readonly="readonly"> 
											</td>
											<td>
												<input type="text" name="donvi" value="<%= spDonvi[k] %>" style="width: 100%; text-align: right;" readonly="readonly">							
												<input type="hidden" name="madonvi" value="<%= spMadonvi[k] %>" style="width: 100%; text-align: right;" readonly="readonly">
											</td>

											<td>											
												<input type="text" name="soluong" value="<%= formatter1.format(Double.parseDouble(spSoluong[k])) %>" style="width: 100%; text-align: right;" readonly="readonly">							

											</td>										
											<td>
												<input type="text" name="dongia" value="<%= formatter.format(Double.parseDouble(spDongia[k])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
											</td>
											<td style="display: none">
												<input type="text" name="vat" value="<%= formatter.format(Double.parseDouble(spVat[k])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
											</td>
											<td>
												<input type="hidden" name="chietkhau" value="<%= formatter.format(Double.parseDouble(spChietkhau[k])) %>" style="width: 100%; text-align: right;" readonly="readonly">										
												<input type="text" name="dongiack" value="<%= formatter.format(Double.parseDouble(spDongiagiamgia[k])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
											</td>
											<td  style="display: none">
												<input type="hidden" name="tienthue" value="<%= formatter.format(Double.parseDouble(spTienThue[k])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
												<input type="hidden" name="khott" value="<%= spKhoTT[k] %>" style="width: 100%; text-align: right;" readonly="readonly">
											</td>
											<td>
												<input type="text" name="thanhtien" value="<%= formatter.format(Double.parseDouble(spThanhtien[k])) %>" style="width: 100%; text-align: right;" readonly="readonly" onkeypress="return keypress(event); " >							
												<input type="hidden" name="spnr" value="<%= formatter.format(Double.parseDouble(spnr[k])) %>" style="width: 100%; text-align: right;" readonly="readonly" >
											</td>
										</tr>	
											
									<% 	count ++;
										//chỉ hiển thị 16 dòng trong đơn hàng	
										if(k==15) k = spMa.length;
									} 
								} %>		
								
								
								</tbody>
								</TABLE>
															  
							</TD>
							  </TR>	
							  					   
						  </TABLE>
											</TD>
					</TR>	
					
				</TBODY>
			</TABLE>
	</td>
  </tr>

</TABLE>
<script type="text/javascript"> 
	dropdowncontent.init('nhapghichu', "left-bottom", 100, "click");	
	TinhTien();
</script>

<script type="text/javascript">
	dropdowncontent.init('thongtinkhxhd', "left-top", 500, "click");
</script>

</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</HTML>
<% if(!(rslistdh == null)) rslistdh.close(); %>
<% if(!(ddhRs == null)) ddhRs.close(); %>
<% if(!(khRs == null)) khRs.close(); %>
<%if(hdBean != null){
	
	hdBean = null;
}%>

