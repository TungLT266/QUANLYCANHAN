<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.center.beans.tieuchithuong.imp.*" %>
<%@page import="geso.traphaco.center.beans.tieuchithuong.*" %>
<%@ page  import = "geso.traphaco.center.beans.dieukienkhuyenmai.ISanpham" %>
<%@ page  import = "geso.traphaco.center.beans.dieukienkhuyenmai.imp.Sanpham" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	ITieuchithuongDP obj =(ITieuchithuongDP)session.getAttribute("tctskuBean");
	ResultSet nhomspRs = obj.getNhomspRs();
	
	String[] diengiaiMuc = (String[])obj.getDiengiaiMuc();
	String[] tumuc = (String[])obj.getTumuc();
	String[] denmuc = (String[])obj.getDenmuc();
	String[] doanhso = (String[])obj.getDoanhso();
	String[] thuongSR = (String[])obj.getThuongSR();
	String[] thuongTDSR = (String[])obj.getThuongTDSR();
	String[] thuongSS = (String[])obj.getThuongSS();
	String[] thuongTDSS = (String[])obj.getThuongTDSS();
	String[] thuongASM = (String[])obj.getThuongASM();
	String[] thuongTDASM = (String[])obj.getThuongTDASM();
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="..scripts/jquery-1.js"></script>
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
		z-index:100200;
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
</style>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/nhomspthuong.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>

<link media="screen" rel="stylesheet" href="../css/colorbox.css">
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {
        	$(".nhomdieukien").colorbox({width:"500px", inline:true, href:"#nhomdieukien"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("OneOne - Project.");
                return false;
            });
           
        });
    </script>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["tctsku"].submit();
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
	  var thang = document.getElementById("thang").value;
	  var nam = document.getElementById("nam").value;

	  if( thang == '' )
	  {
		  alert("Chọn tháng cần đặt chỉ tiêu");
		  return;
	  }
	  	
	  if( nam == '' )
	  {
		  alert("Chọn năm cần đặt chỉ tiêu");
		  return;
	  }
	  
	  document.forms["tctsku"].action.value = "save";
	  document.forms["tctsku"].submit(); 
  }
	
	function replaces()
	{
		var masp = document.getElementsByName('spMa');
		var tensp = document.getElementsByName('spTen');
		
		for(p=0; p < masp.length; p++)
		{
			if(masp.item(p).value != "")
			{
				var sp = masp.item(p).value;

				var pos = parseInt(sp.indexOf(" - "));
				if(pos > 0)
				{
					masp.item(p).value = sp.substring(0, pos);
					tensp.item(p).value = sp.substr(parseInt(sp.indexOf(" - ")) + 3);					
				}
			}
			else
			{
				tensp.item(p).value = "";
			}			
		}
		
		setTimeout(replaces, 200);
	}	
	
	function ajaxOption(id, value)
	{
		//alert(id + ' - Value: ' + value);
		var xmlhttp;
		
		if (window.XMLHttpRequest)
		{
		  xmlhttp=new XMLHttpRequest();
		}
		else
		{
		  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		xmlhttp.onreadystatechange=function()
		{
		  if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		  {
			 //alert(xmlhttp.responseText);
		     document.getElementById("tbsanpham").innerHTML = xmlhttp.responseText;
		  }
		}
		xmlhttp.open("POST","../../AjaxTctDP?nspId="+ value, true);
		xmlhttp.send();
	}
	
	function LuuNhomDieuKien()
	{  
		var diengiaiNhom = document.getElementById('diengiaiNhom').value; 
		
		var soluongNhom = document.getElementById('soluongNhom').value; 
		if(soluongNhom == '')
			soluongNhom = "0";
		
		var sotienNhom = document.getElementById('sotienNhom').value; 
		if(sotienNhom == '')
			sotienNhom = "0";
		
		var IsThung = "0"; 
		if(document.getElementById('IsThung').checked == true)
			IsThung = "1";
		
		if(diengiaiNhom == '')
		{
			alert('Bạn phải nhập diễn giải cho nhóm điều kiện');
			return;
		}
		
		if( soluongNhom == '' || sotienNhom == '' )
		{
			alert('Bạn phải nhập số lượng hoặc số tiền cho nhóm điều kiện');
			return;
		}

		var masanpham = '';
		
		var masp = document.getElementsByName('spMa');
		for(p = 0; p < masp.length; p++)
		{
			if(masp.item(p).value != "")
			{
				masanpham += masp.item(p).value + ";";
			}		
		}
		
		if(masanpham == '')
		{
			alert('Bạn phải chọn sản phẩm cho nhóm điều kiện');
			return;
		}
		
		var xmlhttp;
		if (window.XMLHttpRequest)
		{
		  xmlhttp=new XMLHttpRequest();
		}
		else
		{
		  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		xmlhttp.onreadystatechange=function()
		{
		   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		   {
			  if(xmlhttp.responseText.length > 10)
			  {
				  alert('Không thể tạo mới nhóm điều kiện, vui lòng kiểm tra lại các thông tin. \n');
			  }
			  else
			  {
				  alert('Tạo mới nhóm điều kiện thành công. \n');
				  document.getElementById('nhomdieukienID').value = xmlhttp.responseText;
			   }
		    }
		}
		
		var dgNhom = encodeURIComponent(diengiaiNhom.replace(" ","+"));
		
		//alert("../../AjaxTctDP?diengiai=" + dgNhom + "&soluongNhom=" + soluongNhom + "&sotienNhom=" + sotienNhom + "&IsThung=" + IsThung + "&sanpham=" + masanpham);
		xmlhttp.open("GET","../../AjaxTctDP?diengiai=" + dgNhom + "&soluongNhom=" + soluongNhom + "&sotienNhom=" + sotienNhom + "&IsThung=" + IsThung + "&sanpham=" + masanpham, true);
		xmlhttp.send(); 
		
	}
	
	function FormatNumber(e)
	{
		e.value = DinhDangTien(e.value.replace(/,/g,""));
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

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="tctsku" method="post" action="../../TieuchithuongDPUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Kinh doanh > Tiêu chí thưởng độ phủ > Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../TieuchithuongDPSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin tiêu chí thưởng độ phủ </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="120px" class="plainlabel" >Tháng <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel" colspan="3">
									<select name="thang" id="thang" style="width: 70px">
									<option value= ""> </option>  
									<%
									int k=1;
									for(k=1; k <= 12; k++ ){
										
									  if(obj.getThang().equals(Integer.toString(k)) ) {
									%>
										<option value=<%= k %> selected="selected" > <%= k %></option> 
									<%  }else{  %>
										<option value=<%= k %> > <%= k %></option> 
									<% } }%>
									</select>
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Năm <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel" colspan="3">
									<select name="nam" id="nam" style="width :70px">
									<option value= ""> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									for(int n=2008; n<year_+3; n++) {
									  if(obj.getNam().equals( Integer.toString(n)) ){									  
									%>
										<option value=<%=n %> selected="selected" > <%=n %></option> 
									<%
									  }else{
									 %>
										<option value=<%=n %> ><%=n %></option> 
									<% } }
									%>
									</select>
						  	  	</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel">Scheme <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="scheme" id="scheme" value="<%= obj.getScheme() %>"> 
						  	  	</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>"> 
						  	  	</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel">Nhóm điều kiện</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		
						  	  		<input type="hidden" name="nhomdieukienID" id="nhomdieukienID" value="<%= obj.getNhomdkId() %>" >
						  	  		
						  	  		<a class="nhomdieukien" href="#">
			                        		<img style="top: -4px;" src="../images/vitriluu.png" title="Tạo mới nhóm điều kiện"></a>
					                <div style='display:none; '>
				                        <div id='nhomdieukien' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Tạo mới nhóm điều kiện</h4>
											<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
				                            <tr>
			                                	<td width="120px" valign="top" align="left"><span style="font-size: 12px">Diễn giải</span></td>
			                                    <td valign="top" align="left">
				                                    <input type="text" name="diengiaiNhom" id="diengiaiNhom" value="<%= obj.getDiengiaiNhom() %>" />
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td  valign="top" align="left"><span style="font-size: 12px">Số tiền</span></td>
			                                    <td valign="top" align="left">
				                                    <input type="text" name="sotienNhom" id="sotienNhom" value="<%= obj.getSotienNhom() %>" />
			                                    </td>
			                                </tr>
			                                <tr>
			                                	<td valign="top" align="left"><span style="font-size: 12px">Số lượng</span></td>
			                                    <td valign="top" align="left">
				                                    <input type="text" name="soluongNhom" id="soluongNhom" value="<%= obj.getSoluongNhom() %>" />
			                                    </td>
			                                </tr>	                                
			                                <TR >
										  	  	<TD > &nbsp; </TD>
										  	  	<TD colspan="3" style="font-size: 0.8em;" >
										  	  		<% if(obj.getIsthung().equals("1")){ %>
										  	  			<input type="checkbox" name="IsThung" id="IsThung" value="1" checked="checked"> <i> Số lượng tính theo thùng </i>
										  	  		<%} else { %> 
										  	  			<input type="checkbox" name="IsThung" id="IsThung" value="1" > <i> Số lượng tính theo thùng </i>
										  	  		<% } %> 
										  	  	</TD>
											</TR>
											<tr>
			                                	<td width="120px" valign="top" align="left"><span style="font-size: 12px">Nhóm sản phẩm</span></td>
			                                    <td valign="top" align="left">
				                                    <select name="nhomsanpham" id="nhomsanpham" onChange = "ajaxOption(this.id, this.value);">
			                                    		<option value=""> </option>
			                                    		<% if(nhomspRs != null)
			                                    		{ 
			                                    			nhomspRs.beforeFirst();
			                                    			while(nhomspRs.next()){ %>
			                                    				<option value="<%= nhomspRs.getString("nspId") %>"><%= nhomspRs.getString("nspTen") %></option>
			                                    		<%} } %>
			                                    	</select>
			                                    </td>
			                                </tr>
				                              <tr>
				                                	<td colspan="2">
				                                		<table align="left" cellpadding="0px" cellspacing="1px">
					                                		<tr class="tbheader">
					                                			<td width="100px" align="center" ><span style="font-size: 0.9em">Mã sản phẩm</span></td>
					                                			<td width="330px" align="center" ><span style="font-size: 0.9em">Tên sản phẩm</span></td>		                                			
					                                		</tr>
					                                	</table>
					                                	<div id="tbsanpham" style="width: 100%; max-height: 180px; overflow: auto">
					                                	<table align="left" cellpadding="0px" cellspacing="1px">
					                                	<% 
					                                		int count = 0;
					                                		String[] spMa = (String[])obj.getSpMa();
					                                		String[] spTen = (String[])obj.getSpTen();
					                                		
					                                		if(spMa != null)
					                                		{
					                                			for(int i = 0; i < spMa.length; i++)
					                                			{
					                                				%>
					                                				
					                                				<tr>
							                                			<td width="100px" align="center">
							                                				<input type="text" value="<%= spMa[i] %>" style="width: 100px" name="spMa" onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off"  >
							                                			</td>
							                                			<td width="200px" align="left">
							                                				<input type="text" value="<%= spTen[i] %>" name="spTen" style="width: 300px" readonly="readonly">
							                                			</td>                   			
							                                		</tr>
					                                				
					                                			<% count ++;
					                                			}
					                                		}
					                                		for(int pos = count; pos < 50; pos++){ %>
					                                		<tr>
					                                			<td width="100px" align="center">
					                                				<input type="text" value="" style="width: 100px" name="spMa" onkeyup="ajax_showOptions(this,'sanpham',event)" AUTOCOMPLETE="off">
					                                			</td>
					                                			<td width="200px" align="left">
					                                				<input type="text" value="" name="spTen" style="width: 300px" readonly="readonly">
					                                			</td>                   			
					                                		</tr>
					                                	<%} %>
				                                		</table>
				                                		</div>
				                                	</td>
				                                </tr>
				                                <tr>
				                                	<td valign="top" align="left" colspan="2">
				        								<a class="button" href="javascript:submitform();">
				        								<img style="top: -4px;" src="../images/button.png" alt=""> Nhập lại </a>
				        								&nbsp;&nbsp;&nbsp;
				        								<a class="button" href="javascript:LuuNhomDieuKien();">
				        								<img style="top: -4px;" src="../images/button.png" alt=""> Tạo mới nhóm điều kiện </a>
				                                	</td>
				                                </tr>
				                            </table>
										</div>
					                </div>
						  	  		
						  	  	</TD>
						  </TR>
						  
						</TABLE>
						<hr />
						
						<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
		                    <!-- <TR class="tbheader">
		                        <TH align="center" width="10%" rowspan="2" >Diễn giải</TH>
		                        <TH align="center" width="10%" rowspan="2" >Từ mức</TH>
		                        <TH align="center" width="10%" rowspan="2" >Đến mức</TH>
		                        <TH align="center" width="10%" rowspan="2" >Doanh số</TH>
		                        <td align="center" width="10%" colspan="2">Thưởng SR</td>
		                    	<td align="center" width="20%" colspan="2" >Thưởng SS</td>
		                    	<td align="center" width="20%" colspan="2" >Thưởng ASM</td>
		                    </TR>
		                    <TR class="tbheader">
		                        <td align="center" width="10%">T. SR</td>
		                    	<td align="center" width="10%" >T. TĐ SR</td>
		                    	<td align="center" width="10%" >T. SS</td>
		                    	<td align="center" width="10%" >T. TĐ SS</td>
		                    	<td align="center" width="10%" >T. ASM</td>
		                    	<td align="center" width="10%" >T. TĐ ASM</td>
		                    </TR> -->

							<TR class="tbheader">
		                        <TH align="center" width="30%" >Diễn giải</TH>
		                        <TH align="center" width="10%" >Mức Outlet</TH>
		                        <TH align="center" width="10%" style="display: none;" >Đến mức</TH>
		                        <TH align="center" width="15%" >Doanh số</TH>
		                        <td align="center" width="15%" >Thưởng SR</td>
		                    	<td align="center" width="15%" >Thưởng SS</td>
		                    	<td align="center" width="15%" >Thưởng ASM</td>
		                    </TR>
									                    
		                    <%
		                    	count = 0;
		                    	if( diengiaiMuc != null ) 
		                    	{
		                    		for(int i = 0; i < diengiaiMuc.length; i++)
		                    		{
										%>   
										
										<tr>
											<td>
												<input type="text" name="diengiaiMuc" value="<%= diengiaiMuc[i] %>" >
											</td>
											<td>
												<input type="text" name="tumuc" value="<%= tumuc[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td style="display: none;" >
												<input type="text" name="denmuc" value="1000000000" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td>
												<input type="text" name="doanhso" value="<%= doanhso[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td>
												<input type="text" name="thuongSR" value="<%= thuongSR[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td style="display: none;">
												<input type="text" name="thuongTDSR" value="<%= thuongTDSR[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td>
												<input type="text" name="thuongSS" value="<%= thuongSS[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td style="display: none;">
												<input type="text" name="thuongTDSS" value="<%= thuongTDSS[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td>
												<input type="text" name="thuongASM" value="<%= thuongASM[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
											<td style="display: none;">
												<input type="text" name="thuongTDASM" value="<%= thuongTDASM[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
											</td>
										</tr>
										                 			
		                    		<% count++; }
		                    		
		                    	}
		                    	
		                    	for(int i = count; i < 15; i++)
	                    		{
	                    			%>
	                    			
	                    			<tr>
										<td>
											<input type="text" name="diengiaiMuc" value="" >
										</td>
										<td>
											<input type="text" name="tumuc" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td style="display: none;">
											<input type="text" name="denmuc" value="1000000000" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td>
											<input type="text" name="doanhso" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td>
											<input type="text" name="thuongSR" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td style="display: none;">
											<input type="text" name="thuongTDSR" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td>
											<input type="text" name="thuongSS" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td style="display: none;">
											<input type="text" name="thuongTDSS" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td>
											<input type="text" name="thuongASM" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td style="display: none;">
											<input type="text" name="thuongTDASM" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
									</tr>
	                    			
	                    		<%  }	
		                    	
		                    %>
							
		                    <TR>
		                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
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
<%}%>
