<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.imp.Lenhsanxuat"%>
<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau"%>
<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.ILenhsanxuat"%>
<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.IErpCannguyenlieu"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkho.*" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkho.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpCannguyenlieu lsxBean = (IErpCannguyenlieu)session.getAttribute("ckBean"); %>
<% ResultSet khoxuatRs = lsxBean.getKhoXuatRs(); %>
<% ResultSet vitriChuyenRs = lsxBean.getVitriChuyenRs(); %>
<% ResultSet doituongRs = lsxBean.getDoituongRs(); %>
<% ResultSet chiphiRs = lsxBean.getChiphiRs(); %>
<% ResultSet spRs = lsxBean.getSanphamRs(); %>
<% ResultSet soloRs = lsxBean.getSoloRs(); %>

<% 
	NumberFormat formater = new DecimalFormat("##,###,###.###");
	
	String[] khott_sp_ct_fk = lsxBean.getSpId();
	String[] vitri = lsxBean.getSpVitri();
	String[] mame = lsxBean.getSpMame();
	String[] mathung = lsxBean.getSpMathung();
	String[] maphieu = lsxBean.getSpMaphieu();
	String[] phieudt = lsxBean.getSpPhieuDT();
	String[] phieueo = lsxBean.getSpPhieuEO();
	String[] marq = lsxBean.getSpMARQ();
	String[] hamluong = lsxBean.getSpHamluong();
	String[] hamam = lsxBean.getSpHamam();
	String[] tonkho = lsxBean.getSpTonkho();
	String[] soluong = lsxBean.getSpSoluong();
	
%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	   NumberFormat formatter3 = new DecimalFormat("#,###,###.######");
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
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

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
<script type="text/javascript" src="../scripts/erp_splist_yeucauchuyenkho.js"></script>
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
		var idsp = document.getElementsByName("idsp");
		var masp = document.getElementsByName("masp");
		var tensp = document.getElementsByName("tensp");  
		var donvi = document.getElementsByName("donvi");
		var soluongtonkho = document.getElementsByName("soluongtonkho");
		var slyc = document.getElementsByName("soluongyeucau");
		
		var tongSL = 0;
		var i;
		for(i = 0; i < masp.length; i++)
		{
			if(masp.item(i).value != "")
			{
				var sp = masp.item(i).value;
				var pos = parseInt(sp.indexOf(" - "));

				if(pos > 0)
				{
					masp.item(i).value = sp.substring(0, pos);
					sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
					
					tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					idsp.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
			 
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					donvi.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					soluongtonkho.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					
					slyc.item(i).value = "0";
					submitform();
				}
				
				if(slyc.item(i).value != "")
				{
				    tongSL = tongSL + parseFloat(slyc.item(i).value.replace(",",""));
				}
			}
			else
			{
				idsp.item(i).value = "";
				masp.item(i).value = "";
				tensp.item(i).value = "";
			    slyc.item(i).value = "";
			    soluongtonkho.item(i).value = "";
				donvi.item(i).value = "";
			}
		}	
		
		document.getElementById("tongSLYC").value = DinhDangDonGia((tongSL).toFixed(3));
		setTimeout(replaces, 500);
	}
	
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
			
		//num = (Math.round( num * 1000 ) / 1000).toString();
			
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
	
	 function saveform()
	 {	
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['hctmhForm'].action.value='submit';
	     document.forms["hctmhForm"].submit();
	 }
	 
	 function changeKHO()
	 {
		document.forms["hctmhForm"].action.value = "changeKHO";
	    document.forms["hctmhForm"].submit();
	 }
	 
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $(".select").select2();  });
     
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpCannguyenlieuUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="id" value='<%=lsxBean.getId()%>'>
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý tồn kho > Chức năng > Cân nguyên liệu  > <%= ( lsxBean.getId().length() >0 ? "Cập nhật" : "Tạo mới" ) %> 
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpCannguyenlieuSvl?userId=<%= userId %>" >
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
    	<legend class="legendtitle">Cân nguyên liệu  </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD style="width: 140px;" class="plainlabel" valign="top">Ngày cân </TD>
                    <TD style="width: 240px;" class="plainlabel" valign="top"  >
                    	<input type="text" class="days" readonly="readonly"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/>
                    </TD>
                    <TD style="width: 120px;" class="plainlabel" valign="top">Lý do </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  name="lydo" value="<%= lsxBean.getLydoyeucau() %>" style="width: 400px;" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Kho cân </TD>
                    <TD class="plainlabel" <%= lsxBean.getCoDoituong().equals("1") ? "" : "colspan='3'" %>  >
                    	<select name = "khoxuatId" style="width: 200px;"  onchange="changeKHO();" class="select" >
                    		<option value=""> </option>
                        	<%
                        		if(khoxuatRs != null)
                        		{
                        			try
                        			{
                        			while(khoxuatRs.next())
                        			{  
                        			if( khoxuatRs.getString("pk_seq").equals(lsxBean.getKhoXuatId())){ %>
                        				<option value="<%= khoxuatRs.getString("pk_seq") %>" selected="selected" ><%= khoxuatRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khoxuatRs.getString("pk_seq") %>" ><%= khoxuatRs.getString("ten") %></option>
                        		 <% } } khoxuatRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                     </TD>
 
                	 <% if( lsxBean.getCoDoituong().equals("1") ) { %> 
                     <TD class="plainlabel">Đối tượng </TD>
                     <TD class="plainlabel" >
                     		<input type="hidden" name="codoituong" value="<%= lsxBean.getCoDoituong() %>" >
                     		<input type="hidden" name="loaidoituongId" value="<%= lsxBean.getLoaidoituongId() %>" >
                        	<select name="doituongId" style="width: 200px;" onchange="submitform();" class="select" >
                    		<option value=""></option>
                    			<% if( doituongRs != null ) { %>
	                    		<% while(doituongRs.next()) 
	                    		{ 
	                    			if(lsxBean.getDoituongId().equals(doituongRs.getString("PK_Seq"))) 
	                    			{ %>
	                    				<option value="<%= doituongRs.getString("PK_Seq") %>" selected="selected"><%= doituongRs.getString("Ten") %></option>
	                    			<% } 
	                    			else { %> 
	                    				<option value="<%= doituongRs.getString("PK_Seq") %>" ><%= doituongRs.getString("Ten") %></option>
	                    			<% }  %> 
	                    		<% } doituongRs.close(); } %>
	                    		
	                    	</select>
                      </TD> 
                      <% }  %>
                </TR>
                
                <TR style="display: none;" >
                    <TD class="plainlabel" >Vị trí cân </TD>
                    <TD class="plainlabel" colspan='3'  >
                    	<select name = "vitriId" style="width: 200px;"  onchange="changeKHO();" class="select" >
                    		<option value=""> </option>
                        	<%
                        		if(vitriChuyenRs != null)
                        		{
                        			try
                        			{
                        			while(vitriChuyenRs.next())
                        			{  
                        			if( vitriChuyenRs.getString("pk_seq").equals(lsxBean.getVitrichuyenId())){ %>
                        				<option value="<%= vitriChuyenRs.getString("pk_seq") %>" selected="selected" ><%= vitriChuyenRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= vitriChuyenRs.getString("pk_seq") %>" ><%= vitriChuyenRs.getString("ten") %></option>
                        		 <% } } vitriChuyenRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                     </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Sản phẩm </TD>
                    <TD class="plainlabel" >
                    	<select name = "sanphamId" style="width: 200px;"  onchange="changeKHO();" class="select" >
                    		<option value=""> </option>
                        	<%
                        		if(spRs != null)
                        		{
                        			try
                        			{
                        			while(spRs.next())
                        			{  
                        			if( spRs.getString("pk_seq").equals(lsxBean.getSanphamId())){ %>
                        				<option value="<%= spRs.getString("pk_seq") %>" selected="selected" ><%= spRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= spRs.getString("pk_seq") %>" ><%= spRs.getString("ten") %></option>
                        		 <% } } spRs.close();} catch(Exception ex){ ex.printStackTrace(); }
                        		}
                        	%>
                    	</select>
                     </TD>
                     <TD class="plainlabel" >Số lô </TD>
                    <TD class="plainlabel" >
                    	<select name = "soloId" style="width: 200px;"  onchange="changeKHO();" class="select" >
                    		<option value=""> </option>
                        	<%
                        		if(soloRs != null)
                        		{
                        			try
                        			{
                        			while(soloRs.next())
                        			{  
                        			if( soloRs.getString("solo").equals(lsxBean.getSoloId())){ %>
                        				<option value="<%= soloRs.getString("solo") %>" selected="selected" ><%= soloRs.getString("solo") %></option>
                        			<%}else { %>
                        				<option value="<%= soloRs.getString("solo") %>" ><%= soloRs.getString("solo") %></option>
                        		 <% } } soloRs.close();} catch(Exception ex){ ex.printStackTrace(); }
                        		}
                        	%>
                    	</select>
                     </TD>
                </TR>
                
                <TR style="display: none;" >
                    <TD class="plainlabel" valign="top">Tổng SL chuyển </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  name="tongSLYC" id="tongSLYC" style="text-align: right;" value="<%= lsxBean.getTongSLYC() %>" readonly="readonly"/>
                    </TD>
                    <TD class="plainlabel" valign="top">Ghi chú </TD>
                    <TD class="plainlabel" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 400px;" />
                    </TD>
                </TR>
            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
			<tr class="tbheader">
				<th align="center" width="5%">Mã mẻ</th>
				<th align="center" width="10%">Mã thùng</th>
				<th align="center" width="15%">Vị trí</th>
				<th align="center" width="10%">Mã phiếu</th>
				<th align="center" width="10%">Phiếu ĐT</th>
				<th align="center" width="9%">Phiếu EO</th>
				<th align="center" width="10%">Marquette</th>
				<th align="center" width="8%">Hàm lượng</th>
				<th align="center" width="5%">Hàm ẩm</th>	
				<th align="center" width="8%">Tồn kho</th>
				<th align="center" width="10%">Cân thực tế</th>	
			</tr>
			
			<% if( tonkho != null ) { for( int i = 0; i < tonkho.length; i++ ) { %>
				<tr>
					<td>
						<input type="hidden" name="khott_sp_ct_fk" value="<%= khott_sp_ct_fk[i] %>" readonly="readonly" style="width: 100%;" >
						<input type="text" name="mame" value="<%= mame[i] %>" readonly="readonly" style="width: 100%;" >
					</td>
					<td><input type="text" name="mathung" value="<%= mathung[i] %>" readonly="readonly" style="width: 100%;" ></td>
					<td><input type="text" name="vitri" value="<%= vitri[i] %>" readonly="readonly" style="width: 100%;" ></td>
					<td><input type="text" name="maphieu" value="<%= maphieu[i] %>" readonly="readonly" style="width: 100%;" ></td>
					<td><input type="text" name="phieudt" value="<%= phieudt[i] %>" readonly="readonly" style="width: 100%;" ></td>
					<td><input type="text" name="phieueo" value="<%= phieueo[i] %>" readonly="readonly" style="width: 100%;" ></td>
					<td><input type="text" name="marq" value="<%= marq[i] %>" readonly="readonly" style="width: 100%;" ></td>
					<td><input type="text" name="hamluong" value="<%= hamluong[i] %>" readonly="readonly" style="width: 100%;" ></td>
					<td><input type="text" name="hamam" value="<%= hamam[i] %>" readonly="readonly" style="width: 100%;" ></td>
					<td><input type="text" name="tonkho" value="<%= tonkho[i] %>" readonly="readonly" style="width: 100%; text-align: right; " ></td>
					<td><input type="text" name="soluong" value="<%= soluong[i] %>" style="width: 100%;  text-align: right; " ></td>
				</tr>
			<% } } %>
			
				
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
		
		if( vitriChuyenRs != null )
			vitriChuyenRs.close();
		if( khoxuatRs != null )
			khoxuatRs.close();
	}
	catch(Exception er)
	{
		er.printStackTrace();
	}
	finally{
		session.setAttribute("lsxBean", null) ; 
	}
	} %>
</form>
</BODY>
</html>