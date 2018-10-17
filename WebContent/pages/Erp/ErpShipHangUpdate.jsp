<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.shiphang.*" %>
<%@ page  import = "geso.traphaco.erp.beans.shiphang.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.text.SimpleDateFormat"  %>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page  import = "java.util.Date;"  %>


<% IErpShiphang nhBean = (IErpShiphang)session.getAttribute("nhBean"); %>
<% ResultSet dvthList = nhBean.getDvthList(); %>
<% ResultSet poList = nhBean.getDmhList(); %>
<% ResultSet nccList = nhBean.getNccList(); %>
<% List<ISanpham> spList = nhBean.getSpList();  %>
<% NumberFormat formater = new DecimalFormat("#,###,###"); %>
<%	NumberFormat formatter2 = new DecimalFormat("#,###,###.##"); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");
Utility util = (Utility) session.getAttribute("util");
int pb= util.getPhongBan(userId);
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

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
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
     $(document).ready(function() { $("select").select2(); });
     
 </script>

<script language="javascript" type="text/javascript">
	
	function TangDate(ngay, songay)
	{   
		var ArrNgay=ngay.split("-");
		var d = new Date(ArrNgay[0],ArrNgay[1]-1,ArrNgay[2]);
		d.setDate(d.getDate() + parseInt(songay));
		
		month = parseInt(d.getMonth()) + 1;
		if(month < 10)
			month = '0' + month;
		
		date = parseInt(d.getDate());
		if(date < 10)
			date = '0' + date;
					
		//alert('Ngay sau khi tang: ' + d.getFullYear() + '-' + month + '-' + date);
		return d.getFullYear() + '-' + month + '-' + date;
	}

	var t;
	
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
	 	 document.forms['nhForm'].action.value='save';
	     document.forms['nhForm'].submit();
	 }
	 
	 
	 function submitform()
	 { 
		 document.forms['nhForm'].action.value='submit';
	     document.forms["nhForm"].submit();
	 }
	 function nextform()
	 { 
		 document.forms['nhForm'].action.value='next';
	     document.forms["nhForm"].submit();
	 }
	 function changePO()
	 { 
		 document.forms['nhForm'].action.value='changePO';
	     document.forms["nhForm"].submit();
	 }
	
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nhForm" method="post" action="../../ErpShiphangUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= nhBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	 Quản lý mua hàng > Mua hàng nhập khẩu > Ship hàng > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpShiphangSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
        <%-- <A id = "btnPrint" href="../../ErpShiphangPdfSvl?userId=<%=userId %>&nhId=<%=nhBean.getId() %>" >
        	<img src="../images/Printer30.png" alt="In"  title="In" border="1" longdesc="In" style="border-style:outset"></A> --%>

    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= nhBean.getMsg() %></textarea>
		         <% nhBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Ship hàng</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
               <TR>
                    <TD width="150px" class="plainlabel" valign="top">Ngày chứng từ</TD>
                    <TD class="plainlabel" valign="top" colspan="3">
                    	<input type="text"  class="days" id="ngaychungtu" name="ngaychungtu" 
                    			value="<%= nhBean.getNgaychungtu() %>"  maxlength="10" readonly /></TD>
				</TR>
               
                <TR >
                    <TD class="plainlabel">Đơn vị thực hiện</TD>
                    <TD class="plainlabel" colspan="3">
                        <select name="dvthId" id="dvthId" onChange="submitform();" style="width: 300px;">
                        	<option value=""> </option>
                        	<%
                        		if(dvthList != null)
                        		{
                        			try
                        			{
                        			while(dvthList.next())
                        			{  
                        			if( dvthList.getString("pk_seq").equals(nhBean.getDvthId())){ %>
                        				<option value="<%= dvthList.getString("pk_seq") %>" selected="selected" ><%= dvthList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= dvthList.getString("pk_seq") %>" ><%= dvthList.getString("ten") %></option>
                        		 <% } } dvthList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                	               	       
                	 
                </TR>
               			
				<TR>
					<TD class="plainlabel" valign="middle" > Số PO </TD>   
                      <TD class="plainlabel" valign="middle" colspan="3">
                      	<select name="poId" id="poId" onchange="changePO();" style="width: 300px;" >
						<option value=""></option>
						<% if (poList != null)
						{
							while (poList.next())
							{
								if (poList.getString("pk_seq").equals(nhBean.getDonmuahangId()))
								{
								%>
									<option value="<%=poList.getString("pk_seq")%>" selected="selected"><%=poList.getString("ten")%></option>
								<% } else { %>
									<option value="<%=poList.getString("pk_seq")%>"><%=poList.getString("ten")%></option>
						<% } } poList.close(); } %>
						</select>
                     </TD>
				</TR>
				
				<TR>
					 <TD class="plainlabel" valign="middle" > Nhà nhập khẩu</TD>   
                      <TD class="plainlabel" valign="middle" colspan="3" >
                      	<select name="nccId" id="nccId" style="width: 300px;" >
						<option value=""></option>
						<% if (nccList != null)
						{
							while (nccList.next())
							{
								if (nccList.getString("pk_seq").equals(nhBean.getNccId()))
								{
								%>
									<option value="<%=nccList.getString("pk_seq")%>" selected="selected"><%=nccList.getString("ten")%></option>
								<% } else { %>
									<option value="<%=nccList.getString("pk_seq")%>"><%=nccList.getString("ten")%></option>
						<% } } nccList.close(); } %>
						</select>
                     </TD>   
                     					
				</TR>
				<TR>
					<TD  class="plainlabel" valign="top" >Ghi chú</TD>
                    <TD class="plainlabel" valign="top" colspan="3">
                    	<input type="text" id="ghichu" name="ghichu" value="<%= nhBean.getGhichu() %>" />
                    </TD>
				</TR>

            </TABLE>
            <hr> 
            </div>
           
        <div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader"> 
                	<TH align="center" width="3%">STT</TH>      		
               		<TH align="center" width="10%">Mã sản phẩm</TH>
                	<TH align="center" width="20%">Tên sản phẩm</TH>                	
                	<TH align="center" width="8%">ĐVT</TH>
               	 	<TH align="center" width="6%">Số lượng đặt</TH>
               	 	<TH align="center" width="6%">Số lượng đã ship</TH>
               	 	<TH align="center" width="6%">Số lượng ship</TH>
               	 	<TH align="center" width="8%">Đơn giá</TH>               	 	
               	 	<TH align="center" width="9%">Thành tiền</TH>               	 	 
 					<TH align="center" width="8%">Ngày nhận</TH>             	 	          	 	
               	 	
                </TR>
                
                
                <!--  LOAD SẢN PHẨM RA -->
                
                <%
                               
                	for(int i = 0; i < spList.size(); i++)
                	{
                		ISanpham sp = spList.get(i);
	               		%>
	               		<tr>
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align:center; " value="<%= i + 1 %>" name= "stt" readonly="readonly">
           	 				</td>
           	 				           	 				
           	 				
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getMa() %>" name= "mahangmua" readonly="readonly" >
           	 					<input type="hidden" value="<%= sp.getId() %>" name= "idhangmua">          	 					
           	 					<input type="hidden" value="<%= sp.getDongia() %>" name= "dongiaMua">
           	 				</td>
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getDiengiai() %>" name= "tenhangmua" readonly="readonly" >
           	 				</td>
           	 		          	 				
           	 				<td align="left">
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= sp.getDvdl() %>" name= "dvdl" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongdat() %>" name= "soluongdat" readonly="readonly" >
           	 					<input type="hidden" style="width: 100%; text-align: right;" value="<%= sp.getDungsai() %>" name= "dungsai" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongdaship() %>" name= "soluongdaship" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getSoluongship() %>" name= "soluongship" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp. getDongia() %>" readonly="readonly" >
           	 				</td>
           	 				<td align="right">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= sp.getthanhtien() %>" readonly="readonly" >
           	 				</td>

           	 				<td align="right">
           	 					<input  class="days" type="text" style="width: 100%; text-align: center; " value="<%= sp.getNgaynhandukien() %>" name= "ngaynhandukien"  >
           	 				</td>
           	 				
           	 				
           	 			</tr>
           	 	<%} %>
            </TABLE> 
        	</div>            
     
     </fieldset>	
    </div>
</div>
</form>

<%
try{

	if(spList!=null){
		spList.clear();
	}
	nhBean.close();
	
}catch(Exception er){
	
}

%>
</BODY>
</html>