<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.distributor.beans.xuatkho.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.xuatkho.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% IErpPhanbodonhang lsxBean = (IErpPhanbodonhang)session.getAttribute("lsxBean"); %>
<% ResultSet tinhthanhRs = lsxBean.getTinhthanhRs(); %>
<% ResultSet quanhuyenRs = lsxBean.getQuanhuyenRs(); %>
<% ResultSet gsbhRs = lsxBean.getGsbhRs(); %>
<% ResultSet spRs = lsxBean.getSanphamRs(); %>
<% Hashtable<String, String> phanbo = lsxBean.getPhanbo(); %>
<% NumberFormat formater = new DecimalFormat("##,###,###"); %>

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
<form name="hctmhForm" method="post" action="../../ErpPhanbodonhangUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="nppId" value='<%= lsxBean.getNppId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý bán hàng > Bán hàng > Phân bổ đơn hàng > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpPhanbodonhangSvl?userId=<%= userId %>" >
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
    	<legend class="legendtitle">Phân bổ đơn hàng </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="130px" class="plainlabel" >Ngày phân bổ </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"   name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>" readonly="readonly" />
                    </TD>
                    
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Từ ngày </TD>
                    <TD class="plainlabel" width="250px" >
                    	<input type="text"   name="tungay" value="<%= lsxBean.getTungay() %>" class="days" />
                    </TD>
                    <TD width="130px" class="plainlabel" >Đến ngày </TD>
                    <TD class="plainlabel" >
                    	<input type="text"   name="denngay" value="<%= lsxBean.getDenngay() %>" class="days" />
                    </TD>
                </TR>
                <TR>
                    <TD class="plainlabel" >Giám sát </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<table>
                    		<tr>
                    			<td>
                    				<select name = "gsbhId" class="select2" style="width: 700px" multiple="multiple" >
			                    		<option value="" ></option>
			                    		<% if( gsbhRs != null ) { 
			                    			while( gsbhRs.next() ) {
			                    				if( lsxBean.getGbshId().contains(gsbhRs.getString("pk_seq")) ){ %>
			                    				<option value="<%= gsbhRs.getString("pk_seq") %>" selected="selected" ><%= gsbhRs.getString("ten") %></option>	
			                    		<% 		} else { %>
			                    				<option value="<%= gsbhRs.getString("pk_seq") %>" ><%= gsbhRs.getString("ten") %></option>		
			                    		<% } } gsbhRs.close(); } %>
			                    	</select>
                    			</td>
                    		</tr>
                    		
                    	</table>
                    </TD>
                </TR>
                <TR>
                	<TD class="plainlabel" >Ghi chú </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" id="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 700px;" />
                    </TD>
                </TR>

            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
				<tr class="tbheader">
					<th align="center" width="5%" >STT</th>
					<th align="center" width="20%" >Mã sản phẩm</th>
					<th align="center" width="45%" >Tên sản phẩm</th>
					<th align="center" width="15%" >Đơn vị</th>
					<th align="center" width="15%" >Phân bổ</th>
				</tr>
				
				<%
					int count = 0;
					if(spRs != null)
					{
						while( spRs.next() )
						{
							String soluongPhanbo = phanbo.get( spRs.getString("PK_SEQ") ) == null ? "" : phanbo.get( spRs.getString("PK_SEQ") );
						%>
					
						<tr >
							<td >
								<input type="text" name="STT" value="<%= (count + 1) %>" style="width: 100%"   > 
							</td>
							<td> 
								<input type="text" name="maSP" value="<%= spRs.getString("MA") %>" style="width: 100%" readonly="readonly"> 
								<input type="hidden" name="idSP" value="<%= spRs.getString("PK_SEQ") %>" style="width: 100%" readonly="readonly"> 
							</td>
							<td>
								<input type="text" value="<%= spRs.getString("TEN") %>" style="width: 100%; " readonly="readonly" >							
							</td>
							<td>
								<input type="text" value="<%= spRs.getString("DONVI") %>" style="width: 100%; " readonly="readonly">							
							</td>
							<td>
								<input type="text" name="soluongPhanbo" value="<%= soluongPhanbo %>" style="width: 100%; text-align: right; " >							
							</td>
							
						</tr>	
							
					<% count ++; } spRs.close(); } %>
				
			</table>
				
            </div>
     </fieldset>	
    </div>
</div>

<%
	try
	{
		lsxBean.DBclose(); 
	}
	catch(Exception er){ }

	} %>
</form>
</BODY>
</html>