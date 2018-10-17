<%@page import="geso.traphaco.distributor.beans.hoadontaichinhNPP.IErpHuyhoadontaichinhNPP"%>
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

<%@ page import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 
	
  	IErpHuyhoadontaichinhNPP hdBean=(IErpHuyhoadontaichinhNPP)session.getAttribute("obj");
	ResultSet rslistdh=hdBean.getrsddhChuaXuathd();
	ResultSet ddhRs=hdBean.getDonhangRs();
	ResultSet khRs=hdBean.getkhRs();
	
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
	String[] spSoluonggoc = hdBean.getSpSoluonggoc();
	String[] spDongia = hdBean.getSpDongia();
	String[] spChietkhau = hdBean.getSpChietKhau();
	String[] spTienCK= hdBean.getSpTienCK();
	String[] spVat = hdBean.getSpVat();
	String[] spTienThue= hdBean.getSpTienVat();
	String[] spThanhtien= hdBean.getSpThanhTien();
	String[] spMadonvi = hdBean.getSpMaDonvi();
	
	NumberFormat formatter=new DecimalFormat("#,###,###"); 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
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
		 

		 if (document.forms["dhForm"]["sohoadon"].value.length < 7) {
				setErrors("Số hóa đơn không được để trống và phải có chiều dài > 7 ký tự");					
				return;
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
		 
		 if (document.forms["dhForm"]["nppId"].value.length == 0) {
				setErrors("Vui lòng chọn khách hàng");					
				return;
			}
		 if (document.forms["dhForm"]["kyhieu"].value.length == 0) {
				setErrors("Vui lòng nhập ký hiệu hóa đơn");					
				return;
			}		 
		 if (document.forms["dhForm"]["sohoadon"].value.length <= 7) {
				setErrors("Số hóa đơn không được để trống và phải lớn hơn 7 số");					
				return;
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
		function SoLuong(i)
		{
			var sp_dongia=0;
			var sp_soluong=0;
			var sp_soluong_goc=0;
			
			var sp_vat=0;
			var sp_ck=0;
			
			var sp_thanhtien=0;
			var sp_tienck_banhang=0;
			var sp_tienvat=0;
			
			var sp_tienavat=0;
			
			var ckthuongmai_hd=0;
			
			var spId = document.getElementsByName("spId");
			
			var soluonggoc=document.getElementsByName("soluonggoc");
			var soluong=document.getElementsByName("soluong");		
			
			var dongia=document.getElementsByName("dongia");
			var vat = document.getElementsByName("vat");
			
			var chietkhau = document.getElementsByName("chietkhau");
			var tienck = document.getElementsByName("tienchietkhau");
			
			var tienthue = document.getElementsByName("tienthue");
			var thanhtien = document.getElementsByName("thanhtien");
			
			var total_thanhtien=0;
			var total_tienckbh=0;
			var total_tienthue=0;
			
			var ckthuongmai = document.getElementById("ckthuongmaigoc").value;
			var ckthuongmai_goc=0;
			ckthuongmai_goc=parseFloat(ckthuongmai.replace(/,/g, "") );  
			
			var ckthuongmai_hd=0;
			
			for(k = 0; k < spId.length; k++)
			 {				
				sp_dongia = parseFloat(dongia.item(k).value.replace(/,/g, "") );
				
				sp_soluong = parseFloat(soluong.item(k).value.replace(/,/g, "") );	
				sp_soluong_goc = parseFloat(soluonggoc.item(k).value.replace(/,/g, "") );
				
				if(sp_soluong > sp_soluong_goc)
					sp_soluong = sp_soluong_goc;
				sp_vat = parseFloat(vat.item(k).value.replace(/,/g, "") );	
				sp_ck = parseFloat(chietkhau.item(k).value.replace(/,/g, "") );	
				
				sp_thanhtien = sp_soluong*sp_dongia;
				sp_tienck_banhang = sp_thanhtien*sp_ck/100;
				//sp_tienvat = (sp_thanhtien-sp_tienck_banhang)*sp_vat/100;
				//sp_tienavat = sp_thanhtien-sp_tienck_banhang+sp_tienvat;
				
				total_thanhtien += sp_thanhtien;
				total_tienckbh += sp_tienck_banhang;
				//total_tienthue += sp_tienvat;		
				
				soluong.item(k).value=DinhDangTien(sp_soluong);
				tienck.item(k).value=DinhDangTien(sp_tienck_banhang);
				//tienthue.item(k).value=DinhDangTien(sp_tienvat);
				thanhtien.item(k).value=DinhDangTien(sp_thanhtien - sp_tienck_banhang);
			 }
					
			//TRUONG HOP 1: CHIẾT KHẤU THƯƠNG MẠI > 0
			var ck =0;
			ck=total_thanhtien-total_tienckbh;
		
	 		if(ckthuongmai_goc > 0)
	 			{
	 				if(ckthuongmai_goc >= ck)
	 					ckthuongmai_hd = ck;
	 				else
	 					ckthuongmai_hd = ckthuongmai_goc;
	 			}
			 
			//TRƯỜNG HỢP 2: CHIẾT KHẤU THƯƠNG MAI < 0
			
	 		if(ckthuongmai_goc<0)
				{
					ckthuongmai_goc=ckthuongmai_goc*(-1);
					if(ckthuongmai_goc >= ck)
						ckthuongmai_hd = ck*(-1);
					else
						ckthuongmai_hd = ckthuongmai_goc*(-1);
				}
				 
	 		total_tienthue = (total_thanhtien - total_tienckbh - ckthuongmai_hd)*10/100;
	 		
			total_thucthu = total_thanhtien - total_tienckbh - ckthuongmai_hd + total_tienthue;		
			
			document.getElementById("total_thanhtien").value=DinhDangTien(total_thanhtien);
			document.getElementById("total_ckbh").value=DinhDangTien(total_tienckbh);
			document.getElementById("ckthuongmaihd").value=DinhDangTien(ckthuongmai_hd);
			document.getElementById("total_tienvat").value=DinhDangTien(total_tienthue);
			document.getElementById("tienavat").value=DinhDangTien(total_thucthu);
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
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dhForm" method="post" action="../../HuyhoadontaichinhUpdateSvl">
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
			 								   <TD align="left"  class="tbnavigation">&nbsp; Quản lý cung ứng > Quản lý bán hàng > Hủy hóa đơn tài chính > Tạo mới </TD>								   
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
											<TD width="30" align="left"><A href = "../../HuyhoadontaichinhSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
										    <TD width="30" align="left" ><A id = "btnSave" href="javascript:saveform()" ><img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A></TD>					
											 
							    			<TD width="30" align="left"></TD>
								    		<TD align="left" >&nbsp;</TD>
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="0" width="100%" cellpadding = "6" cellspacing = "0" style="border-color:gray;" >
								<tr>
								
								 <TD  align="left" class="legendtitle" colspan = 2 >
									<fieldset  >
									<legend> Thông báo nhập liệu</legend>
				    				<textarea name="dataerror"  id="dataerror" style="width:100%;margin:0px " readonly="readonly" rows="1"><%=hdBean.getMessage() %></textarea>
							  	</fieldset>
							  	 </TD>
								</tr>
							<TR>
								<TD class="plainlabel" width="100px;" > Ngày hủy HĐTC   
									
								</TD>
								<TD class="plainlabel" >
									<input  type="text" class="days" name="ngayhuy" value="<%= hdBean.getNgayHuy() %>" size="20" >
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" width="100px;" > Loại hóa đơn </TD>
								<TD class="plainlabel" >
								 
									<SELECT NAME = "loaiHD" onchange="submitform();" >
										<OPTION VALUE = "" ></OPTION>
										
								<% if(hdBean.getLoaiHD().equals("0")){ %>
										<OPTION VALUE = "0" SELECTED >Hóa đơn tài chính</OPTION>
										<OPTION VALUE = "6" >Hóa đơn khác </OPTION>
								
								<% }else if(hdBean.getLoaiHD().equals("6")){ %>								
										<OPTION VALUE = "0" >Hóa đơn tài chính</OPTION>
										<OPTION VALUE = "6" SELECTED >Hóa đơn khác </OPTION>
								<% }else{ %>	
										<OPTION VALUE = "0" >Hóa đơn tài chính</OPTION>
										<OPTION VALUE = "6" >Hóa đơn khác </OPTION>
								<% } %>
									</SELECT>
								</TD>
							</TR>

							<TR>
								<TD class="plainlabel" width="100px;" > Số chứng từ </TD>
								<TD class="plainlabel" >
								 
									<input  type="text"   name="sochungtu" value="<%= hdBean.getSoCT() %>" size="20" onchange="submitform();" >
								</TD>
							</TR>
							
							<%if(hdBean.getSoHoaDon().length() >0) { %>
							
								<TR class="plainlabel">
									<TD align="left" colspan = 2 >						
										
										<TABLE  border="0" bordercolor="white" width="100%" cellpadding = "3" cellspacing = "0" style="padding-left:2px ;" >
											<tr class="plainlabel">
											<th width='15%' ></th>
											<th width='35%' ></th>
											<th width='15%' ></th>
											<th width='35%' ></th>
											</tr>
											
											<TR class="plainlabel" >
											  <TD class="plainlabel">Ngày xuất hóa đơn </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" class="days" id="ngayxuathd" name="ngayxuathd" value="<%=hdBean.getNgayxuathd()%>" maxlength="10" /> 
											  </TD>
											  
											  <TD class="plainlabel">Ngày ghi nhận công nợ </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" class="days"
                                     			   id="ngaycongno" name="ngaycongno" value="<%=hdBean.getNgayghino()%>" maxlength="10" /> 
											  </TD>
											</TR>
										
											
                    						<TR class="plainlabel" >
									                    	<TD class="plainlabel" >Khách hàng </TD>
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
									                   	 	
                    						</TR>		
                    						
											<TR class="plainlabel" >
												 <TD >Ký hiệu hóa đơn </TD>
											  <TD >                               
                                                 <input type="text" size="10" 
                                     			   id="kyhieu" name="kyhieu" value="<%=hdBean.getKyHieu() %>" > 
											    </TD>
											    <td>
													Hình thức thanh toán													 
												</td>
												<td>
												   <select name="hinhthuc" id="hinhthuc" class="select2" style="width: 220px">
												    <% 
												    String[] mangchuoi=new String[]{"TM/CK","Chuyển khoản","Tiền mặt", "Thanh toán sau","Bù trừ công nợ"};
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
											<tr class="plainlabel">
											<td > Ghi chú </td>
											<td>
												<input style="width: 400px " type="text" id="ghichuhd" name="ghichuhd"  value="<%=hdBean.getGhiChu()%>">
											</td>
												
											</tr> 
											
                  							<tr>
                   								
                   								</td>
                   								<td colspan="2" valign="bottom" >
                   								  <table class="plainlabel" width="100%"  style="padding-top:0 ; margin-top:0">
                   								    <tr>
                   								    <td width="40%"> Tổng tiền </td>
                   								     
                   								    <td width="60%"> 
                   								    	<input type="text" style="text-align:right" id="total_thanhtien" name="total_thanhtien" value="<%=formatter.format(hdBean.getTienAVAT() - hdBean.getTienCK() - hdBean.getTienCKThuongMai() - hdBean.getTienVat()) %>" readonly="readonly">
                   								    </td>
                   								    </tr>
                   								    <tr>
                   								    <td>Chiết khấu bán hàng </td>
                   								    <td>
                   								      <input type="text" style="text-align:right"  id="total_ckbh" name="total_ckbh" value="<%=formatter.format(hdBean.getTienCK())%>" readonly="readonly">
                   								    </td>
                   								    </tr>
                   								    <tr>
                   								    <td>Chiết khấu thương mại hóa đơn </td>
                   								    <td>
                   								      <input type="hidden" style="text-align:right"  id="ckthuongmaigoc" name="ckthuongmaigoc" value="<%=formatter.format(hdBean.getTienCKThuongMai())%>" readonly="readonly">
                   								      <input type="text" style="text-align:right"  id="ckthuongmaihd" name="ckthuongmaihd" value="<%=formatter.format(hdBean.getTienCKThuongMai())%>" readonly="readonly">
                   								    </td>
                   								    </tr>                   								    
                   								     <tr>
                   								    <td>Tiền VAT</td>
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
							   		<TD colspan = 2 >
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
													<TH width="10%">Chiết khấu </TH>
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
											<td><input type="text" name="stt" value="<%= count %>" style="width: 100%"  readonly="readonly"  > </td>
											<td>
												<input type="hidden" name="spId" value="<%= spId[k] %>" style="width: 100%"   > 
												<input type="text" name="spMa" value="<%= spMa[k] %>" style="width: 100%"  readonly="readonly"  > 
											</td>
											<td> 
												<input type="text" name="spTen" value="<%= spTen[k] %>" style="width: 100%" readonly="readonly"> 
											</td>
											<td>
												<input type="text" name="donvi" value="<%= spDonvi[k] %>" style="width: 100%; text-align: right;" readonly="readonly">
											</td>
											<td>
												<input type="hidden" name="soluonggoc" value="<%= formatter.format(Double.parseDouble(spSoluonggoc[k])) %>" style="width: 100%; text-align: right;" onChange="SoLuong( <%= k  %> );" onkeypress="return keypress(event);">				
									
												<input type="text" name="soluong" value="<%= formatter.format(Double.parseDouble(spSoluong[k])) %>" style="width: 100%; text-align: right;" onChange="SoLuong( <%= k  %> );" onkeypress="return keypress(event);">							
											</td>										
											<td>
												<input type="text" name="dongia" value="<%= formatter.format(Double.parseDouble(spDongia[k])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
											</td>
											<td>
												
												<input type="text" name="tienchietkhau" value="<%= formatter.format(Double.parseDouble(spTienCK[k])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
											</td>
											<td>
												<input type="text" name="thanhtien" value="<%= formatter.format(Double.parseDouble(spThanhtien[k])) %>" style="width: 100%; text-align: right;" readonly="readonly" onkeypress="return keypress(event); " >							
											</td>
										</tr>	
											
									<% count ++; } } %>		
								
								
								</tbody>
								</TABLE>
															  
							</TD>
							  </TR>	
							  <%} %>
							  					   
						  </TABLE>
											</TD>
					</TR>	
					
				</TBODY>
			</TABLE>
	</td>
  </tr>

</TABLE>

<script type="text/javascript">
/* 	replaces();/* 
	jQuery(function()
	{		
		$("#nhappid").autocomplete("/TraphacoHYERP/pages/Center/Erp_NhaPhanPhoiList.jsp");
	}); */	 
	dropdowncontent.init('nhapghichu', "left-bottom", 100, "click");
	dropdowncontent.init('thongtinkhxhd', "right-bottom", 300, "click");
	
</script>
</form>
</BODY>
</HTML>
<% if(!(rslistdh == null)) rslistdh.close(); %>
<% if(!(ddhRs == null)) ddhRs.close(); %>
<% if(!(khRs == null)) khRs.close(); %>
<%if(hdBean != null){
	hdBean.DBclose();
	hdBean = null;
}

}%>
 