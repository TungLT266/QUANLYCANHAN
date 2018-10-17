<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.distributor.beans.donhangctv.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.donhangctv.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>

<% 
	IDonhangctv lsxBean = (IDonhangctv)session.getAttribute("lsxBean");  
	ResultSet ctvRs = lsxBean.getCtvRs();
	ResultSet ddkdRs = lsxBean.getDdkdRs();
	ResultSet khRs = lsxBean.getKhRs();
	ResultSet spRs = lsxBean.getSanphamRs();
	
	String[] spCtvId = lsxBean.getSpCtvId();
	String[] spId = lsxBean.getSpId();
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spGianhap = lsxBean.getSpGianhap();
	String[] spTonkho = lsxBean.getSpTonkho();
	String[] spVat = lsxBean.getSpVat(); 

	NumberFormat formater = new DecimalFormat("##,###,###");
%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else { %>

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
<script type="text/javascript" src="../scripts/dropdowncontent2.js"></script>


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

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/AjaxDonHangCTV.js"></script>

<script language="javascript" type="text/javascript">

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
	
	function replaces()
	{
		//HIỂN THỊ TEXT NGÀY
		var ngaychuyen = document.getElementById("ngaychuyen").value;
		if( ngaychuyen != '' )
		{
			var str = ngaychuyen.split('-');
			var thang = parseInt(str[1]);
			
			if( thang == 1 )
				thang = 12;
			else
				thang = thang - 1;
			
			document.getElementById("spChothang").innerHTML = thang;
		}
		
		TinhTien();
		setTimeout(replaces, 300);
	}
	
	 function TinhTien()
	 {
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var thanhtien = document.getElementsByName("thanhtien");
	
		var tongtien = 0;
		
		for(var i = 0; i < dongia.length; i++)
		{
			if( dongia.item(i).value != "" && soluong.item(i).value != "" )
			{
				var tt = parseFloat(dongia.item(i).value.replace(/,/g,"")) * parseFloat(soluong.item(i).value.replace(/,/g,""));
				thanhtien.item(i).value = DinhDangTien(tt);
				
				tongtien += tt;
			}		
		}
	
		document.getElementById("txtBVAT").value = DinhDangTien(tongtien);

	 }
	
	
	 function saveform()
	 {	 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = "save";
	 	 
	     document.forms['hctmhForm'].submit();
	    
	 }
	 
	 function submitform()
	 { 
		 document.forms['hctmhForm'].action.value='submit';
	     document.forms["hctmhForm"].submit();
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
	
	function CheckSoLuong_DeXuat(element)
	{
		element.value = DinhDangTien(element.value);
		if(element.value== '' )
		{
			element.value= '';
		}
		
		Update_SoLuong( element );
	}	
	
	function Update_SoLuong( element )
	{
		var spMa = document.getElementsByName("spMa");
		var soluong = document.getElementsByName("soluong");
		var soluong2 = document.getElementsByName("soluong2");
		
		for(var i = 0; i < spMa.length; i++ )
		{
			var soluongDEXUAT = document.getElementsByName(spMa.item(i).value + "_spSOLUONG");
			
			var totalXUAT = 0;
			for(var j = 0; j < soluongDEXUAT.length; j++ )
			{
				totalXUAT = parseFloat(totalXUAT) + parseFloat(soluongDEXUAT.item(j).value.replace(/,/g,""));
			}
			
			//alert(totalXUAT);
			
			if( totalXUAT > parseFloat(soluong.item(i).value.replace(/,/g,"")) )
			{
				soluong2.item(i).value = soluong.item(i).value;
				element.value = '0';
				
				alert('Số lượng xuất ( ' + totalXUAT + ' ) không được phép vượt quá số lượng đặt ( ' + soluong.item(i).value + ' )');
			}

			soluong2.item(i).value = soluong.item(i).value;
		}
	}

</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../DonhangctvUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="nppId" value='<%= lsxBean.getNppId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý bán hàng > Báo cáo CTV > Báo cáo cộng tác viên > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../DonhangctvSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= lsxBean.getMsg() %></textarea>
		         <% lsxBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Báo cáo cộng tác viên </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">	
            	<tr>
            		<TD class="plainlabel" ></TD>
            		<TD class="plainlabel" colspan="3" ><b>Báo cáo CTV cho tháng <span id='spChothang' ></span></b> </TD>
            	</tr>						
               <TR>
                    <TD class="plainlabel" width="120px" >Ngày nhập </TD>
                    <TD  class="plainlabel" width="240px" >
                    	<input type="text" class="days" readonly="readonly"  name="ngaychuyen" id='ngaychuyen' value="<%= lsxBean.getNgayyeucau() %>" onchange = "submitform();" /></TD>
                    	
                    <TD class="plainlabel" width="100px" >Số chứng từ  <FONT class="erroralert"> *</FONT>   </TD>
                    <TD class="plainlabel" >
                    	<input type="text"  name="sochungtu" value="<%= lsxBean.getSoChungTu() %>"/>
                    </TD>
                </TR>
                
                <tr>
                	<TD class="plainlabel" >Trình dược viên</TD>
                    <TD class="plainlabel" colspan="3"  >
                    	<select name = "ddkdId" style="width: 560px" class="select2" onchange = "submitform();" >
                    		<option value=""></option>
                        	<%
                        		if(ddkdRs != null)
                        		{
                        			try
                        			{
                        			while(ddkdRs.next())
                        			{  
                        				if( ddkdRs.getString("pk_seq").equals(lsxBean.getDdkdId())){ %>
                        				<option value="<%= ddkdRs.getString("pk_seq") %>" selected="selected" ><%= ddkdRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= ddkdRs.getString("pk_seq") %>" ><%= ddkdRs.getString("ten") %></option>
                        		 <% } } ddkdRs.close();
                        		 } 
                        			catch(Exception ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   
                </TR>
                
                <tr>
                	<TD class="plainlabel" >Khách hàng</TD>
                    <TD class="plainlabel" colspan="3"  >
                    	<select name = "khId" style="width: 560px" class="select2" onchange = "submitform();" >
                    		<option value=""></option>
                        	<%
                        		if(khRs != null)
                        		{
                        			try
                        			{
                        			while(khRs.next())
                        			{  
                        				if( khRs.getString("pk_seq").equals(lsxBean.getKhId())){ %>
                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("ten") %></option>
                        		 <% } } khRs.close();
                        		 } 
                        			catch(Exception ex){}
                        		}
                        	%>
                    	</select>
                    </TD>   
                </TR>
                
                 <TR  >
                    <TD class="plainlabel" style ="display:none" >Tỉnh đội </TD>
                    <TD class="plainlabel"  style ="display:none" >
                    <select name = "tinhdoiid" style="width: 200px" class="select2"  >
                    	<option value="" ></option>
                        	<%
                        	String tinhdoi[] = {"STORM","SUNFLOWER","SUNLIGHT","SUNRISE","SUNSHINE"};
                       			try
                       			{
                       				int i = 0;
                       			while(i < tinhdoi.length)
                       			{  
                       			if(tinhdoi[i].equals(lsxBean.getTinhdoiId()))
                       			{ %>
                       				<option value="<%=tinhdoi[i] %>" selected="selected" ><%= tinhdoi[i] %></option>
                       			<%}else { %>
                       				<option value="<%= tinhdoi[i] %>" ><%= tinhdoi[i] %></option>
                       		 <% } 
                       			i++;
                       			} }
                       			catch(Exception ex){
                       				ex.printStackTrace();
                       			}
                        	%>
                    	</select>
                    </TD>
                    <TD class="plainlabel" >Ghi chú </TD>
                    <TD  class="plainlabel"  colspan = "5">
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 200px" />
                    </TD>
                 
                </TR>
                <TR style="display:none" >
                    <TD class="plainlabel" >Tổng tiền chiết khấu </TD>
                    <TD class="plainlabel"  >
                    	<input type="text" readonly="readonly"  value="" id="txtTongCK" style="text-align: right; width: 200px; " />
                    </TD>
                    	
                    <TD class="plainlabel" >Tổng tiền sau CK </TD>
                    <TD class="plainlabel" >
                    	<input type="text" readonly="readonly"  value="" id="txtTongSauCK" style="text-align: right;" />
                    </TD>
                </TR>
                
                <TR  >
                 <TD class="plainlabel" >Tổng thành tiền </TD>
                    <TD class="plainlabel" colspan = "4">
                    	<input type="text" readonly="readonly"  id="txtBVAT" value="" style="text-align: right;"  />
                    </TD>

                
            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
				<tr class="tbheader">
					<!-- <th align="center" width="3%" >STT</th> -->
					<th align="center" width="20%" >Cộng tác viên</th>
					<th align="center" width="25%" >Sản phẩm</th>
					<th align="center" width="10%" >Số lượng tồn</th>
					<th align="center" width="10%" >Số lượng BC</th>
					<th align="center" width="10%" >Đơn vị</th>
					<th align="center" width="10%" >Đơn giá</th>
					<th align="center" width="10%" >Thành tiền</th>
				</tr>
				
				 <%
					int count = 0;
					if( spId != null )
					{
						for(int i = 0; i < spId.length; i++)
						{%>
					
						<tr>
							<%-- <td style="text-align: center;" > <%= i + 1 %> </td> --%>
							<td>
								<select name = "spCtvId" style="width: 100%;" class="select2"  >
		                    	<option value="" ></option>
		                        	<%
		                        		if(ctvRs != null)
		                        		{
		                        			ctvRs.beforeFirst();
		                        			try
		                        			{
		                        			while(ctvRs.next())
		                        			{  
		                        			if( ctvRs.getString("pk_seq").equals( spCtvId[i] )){ %>
		                        				<option value="<%= ctvRs.getString("pk_seq") %>" selected="selected" ><%= ctvRs.getString("ten") %></option>
		                        			<%}else { %>
		                        				<option value="<%= ctvRs.getString("pk_seq") %>" ><%= ctvRs.getString("ten") %></option>
		                        		 <% } } } catch(Exception ex){}
		                        		}
		                        	%>
		                    	</select>
							</td>
							<td>
								<select name = "spId" style="width: 100%;" class="select2" onchange = "submitform();" >
		                    	<option value="" ></option>
		                        	<%
		                        		if(spRs != null)
		                        		{
		                        			spRs.beforeFirst();
		                        			try
		                        			{
		                        			while(spRs.next())
		                        			{  
		                        			if( spRs.getString("pk_seq").equals( spId[i] )){ %>
		                        				<option value="<%= spRs.getString("pk_seq") %>" selected="selected" ><%= spRs.getString("ten") %></option>
		                        			<%}else { %>
		                        				<option value="<%= spRs.getString("pk_seq") %>" ><%= spRs.getString("ten") %></option>
		                        		 <% } } } catch(Exception ex){}
		                        		}
		                        	%>
		                    	</select>
							</td>

							<td > <input type="text" name="tonkho" value="<%= spTonkho[i] %>" style="width: 99%; text-align: right; padding: 4px 0px;" readonly="readonly"  > </td>
							<td > <input type="text" name="soluong" value="<%= spSoluong[i] %>" style="width: 99%; text-align: right; padding: 4px 0px;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);"  > </td>							
							<td>
								<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 99%; padding: 4px 0px;" readonly="readonly">
							</td>
							<td > <input type="text" name="dongia" value="<%= spGianhap[i] %>" style="width: 99%; text-align: right; padding: 4px 0px; " readonly="readonly"  > </td>
							<td > <input type="text" name="thanhtien" value="" style="width: 99%; text-align: right; padding: 4px 0px; " readonly="readonly" > </td>
							
						</tr>	
							
					<% count ++; } } %>
				
				<% for(int i = count; i < 50; i++) { %>
					
					<tr>
						<%-- <td style="text-align: center;" > <%= i + 1 %> </td> --%>
						<td>
							<select name = "spCtvId" style="width: 100%" class="select2"  >
		                    	<option value="" ></option>
		                        	<%
		                        		if(ctvRs != null)
		                        		{
		                        			ctvRs.beforeFirst();
		                        			try
		                        			{
		                        			while(ctvRs.next())
		                        			{  
		                        			if( ctvRs.getString("pk_seq").equals( "" )){ %>
		                        				<option value="<%= ctvRs.getString("pk_seq") %>" selected="selected" ><%= ctvRs.getString("ten") %></option>
		                        			<%}else { %>
		                        				<option value="<%= ctvRs.getString("pk_seq") %>" ><%= ctvRs.getString("ten") %></option>
		                        		 <% } } } catch(Exception ex){}
		                        		}
		                        	%>
		                    	</select>
						</td>
						<td>
							<select name = "spId" style="width: 100%;" class="select2" onchange = "submitform();"  >
		                    	<option value="" ></option>
		                        	<%
		                        		if(spRs != null)
		                        		{
		                        			spRs.beforeFirst();
		                        			try
		                        			{
		                        			while(spRs.next())
		                        			{  
		                        			if( spRs.getString("pk_seq").equals( "" )){ %>
		                        				<option value="<%= spRs.getString("pk_seq") %>" selected="selected" ><%= spRs.getString("ten") %></option>
		                        			<%}else { %>
		                        				<option value="<%= spRs.getString("pk_seq") %>" ><%= spRs.getString("ten") %></option>
		                        		 <% } } } catch(Exception ex){}
		                        		}
		                        	%>
		                    	</select>
						</td>
						<td > <input type="text" name="tonkho" value="" style="width: 99%; text-align: right; padding: 4px 0px;" readonly="readonly" > </td>
						<td > <input type="text" name="soluong" value="" style="width: 99%; text-align: right; padding: 4px 0px;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);"  > </td>
						<td>
							<input type="text" name="donvi" value="" style="width: 99%; padding: 4px 0px;" readonly="readonly">
						 </td>
						<td > <input type="text" name="dongia" value="" style="width: 99%; text-align: right; padding: 4px 0px; " readonly="readonly" > </td>
						<td > <input type="text" name="thanhtien" value="" style="width: 99%; text-align: right; padding: 4px 0px; " readonly="readonly" > </td>
						
					</tr>	

				<% } %>	
			</table>
			
            </div>
     </fieldset>	
    </div>
</div>

<script type="text/javascript">
	replaces();
</script>

<%

try
{
	lsxBean.DBclose(); 
	lsxBean=null;
}
catch(Exception er){er.printStackTrace(); }

	lsxBean = (IDonhangctv)session.getAttribute("lsxBean");  
	if(ctvRs != null) ctvRs.close();
	if(khRs != null)  khRs.close();
	if(spRs != null)  spRs.close();
	spId=null;
	spTen=null;
	spDonvi=null;
	spSoluong=null;
	spGianhap=null;
	spTonkho=null;

} %>
</form>
</BODY>
</html>