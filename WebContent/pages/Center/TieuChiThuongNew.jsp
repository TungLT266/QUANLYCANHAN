<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.center.beans.tieuchithuong.*" %>
<%@ page  import = "geso.traphaco.center.beans.tieuchithuong.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% ITieuchithuong lsxBean = (ITieuchithuong)session.getAttribute("tctBean"); %>
<% ResultSet dvkdRs = lsxBean.getDvkdRs(); %>
<% ResultSet kbhRs = lsxBean.getKbhRs(); %>

<% 
	String[] tuMuc = lsxBean.getTumuc();
	String[] denMuc = lsxBean.getDenmuc();
	String[] loaiThuong = lsxBean.getLoaithuong();
	String[] thuong = lsxBean.getThuong();
	String[] nguonGoc = lsxBean.getNguongoc();
	ResultSet kvRs = lsxBean.getKvRs();
	ResultSet vungRs = lsxBean.getVungRs();
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
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
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
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<script>

$(function() {
 
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../TieuChiThuongUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý chỉ tiêu > Khai báo > Công thức thưởng > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../TieuChiThuongSvl?userId=<%= userId %>" >
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
    	<legend class="legendtitle">Công thức thưởng </legend>
        	<div style="float:none; width:100%" align="left">
        	
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                
                <TR>
                	<TD   width="130px" class="plainlabel"  valign="top">Thời gian áp dụng </TD>
                    <TD width="300px" class="plainlabel" valign="top"  >
                    	<select name="tuthang"  style="width :50px" >
									<option > </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										String chuoi=k<10?"0"+k:""+k;
  									  if(lsxBean.getTuthang().equals(k+"")){
  									%>
									<option value=<%=k%> selected="selected" > <%=chuoi%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=chuoi%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									
									<select name="tunam"  style="width :75px" >
									<option > </option>  
									<%
									  
  										int n;
  										for(n=2014;n<2025;n++){
  										if(lsxBean.getTunam().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
 									</select>
 									&nbsp;&nbsp;&nbsp;
 									Đến 
 									
 									<select name="denthang"  style="width :50px" >
									<option > </option>  
									<%
  										 k=1;
  									for(k=1;k<=12;k++){
  										String chuoi=k<10?"0"+k:""+k;
  									  if(lsxBean.getDenthang().equals(k+"")){
  									%>
									<option value=<%=k%> selected="selected" > <%=chuoi%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=chuoi%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									
									<select name="dennam"  style="width :75px" >
									<option > </option>  
									<%
									  
  										 n=0;
  										for(n=2014;n<2025;n++){
  										if(lsxBean.getDennam().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
 									</select>
 									
 									
									
                    </TD>   
                    <TD   width="120px" class="plainlabel" valign="top">Chức vụ </TD>
                    <TD class="plainlabel" valign="top"   >
                    	
                    	<% 	String[] trangthai = new  String[] {"SR","SS","ASM","RSM"  } ;
							String[] idTrangThai = new  String[] {"SR","SS","ASM","RSM"} ;
						%> 
						<SELECT name="chucvu" >
						<option> </option>
		      		 <% for( int i=0;i<trangthai.length;i++) { 
		    			if(idTrangThai[i].equals(lsxBean.getChucvu() ) ){ %>
		      				<option value='<%=idTrangThai[i]%>' selected><%=trangthai[i] %></option>
		      		 	<%}else{ %>
		     				<option value='<%=idTrangThai[i]%>'><%=trangthai[i] %></option>
		     			<%} 
		      		 }
		      		 	%>
					       	</SELECT>
                    	
                    </TD>         	
                </TR>
                
                 
                
                
                <TR>
                	<TD class="plainlabel" valign="top">Đơn vị kinh doanh </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<select name = "dvkdId"   style="width: 200px" >
                    		<option value=""> </option>
                        	<%
                        		if(dvkdRs != null)
                        		{
                        			try
                        			{
                        			while(dvkdRs.next())
                        			{  
                        			if( dvkdRs.getString("pk_seq").equals(lsxBean.getDvkdId())){ %>
                        				<option value="<%= dvkdRs.getString("pk_seq") %>" selected="selected" ><%= dvkdRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= dvkdRs.getString("pk_seq") %>" ><%= dvkdRs.getString("ten") %></option>
                        		 <% } } dvkdRs.close();} catch(SQLException ex){ex.printStackTrace();}
                        		}
                        	%>
                    	</select>
                    </TD>   
                    <TD class="plainlabel" valign="top">Kênh bán hàng </TD>
                    <TD class="plainlabel" valign="top"   >
                    	<select name = "kbhId" style="width: 200px"  >
                    		<option value=""  > </option>
                        	<%
                        		if(kbhRs != null)
                        		{
                        			try
                        			{
                        			while(kbhRs.next())
                        			{  
                        			if( kbhRs.getString("pk_seq").equals(lsxBean.getKbhId())){ %>
                        				<option value="<%= kbhRs.getString("pk_seq") %>" selected="selected" ><%= kbhRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= kbhRs.getString("pk_seq") %>" ><%= kbhRs.getString("ten") %></option>
                        		 <% } } kbhRs.close();} catch(SQLException ex){ex.printStackTrace();}
                        		}
                        	%>
                    	</select>
                    </TD>         	
                </TR>
                
                 <TR>
                	<TD class="plainlabel" valign="top">Vùng </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<select name = "vungId"   style="width: 200px" multiple="multiple" onchange="submitform();" >
                    		<option value=""> </option>
                        	<%
                        		if(vungRs != null)
                        		{
                        			try
                        			{
                        			while(vungRs.next())
                        			{  
                        			if(lsxBean.getVungId().contains( vungRs.getString("pk_seq")  ) ){ %>
                        				<option value="<%= vungRs.getString("pk_seq") %>" selected="selected" ><%= vungRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= vungRs.getString("pk_seq") %>" ><%= vungRs.getString("ten") %></option>
                        		 <% } } vungRs.close();} catch(SQLException ex){ex.printStackTrace();}
                        		}
                        	%>
                    	</select>
                    </TD>   
                    <TD class="plainlabel" valign="top">Khu vực </TD>
                    <TD class="plainlabel" valign="top"   >
                    	<select name = "kvId" class="select2" style="width: 200px" multiple="multiple" >
                    		<option value=""  > </option>
                        	<%
                        		if(kvRs != null)
                        		{
                        			try
                        			{
                        			while(kvRs.next())
                        			{  
                        				if(lsxBean.getKvId().contains( kvRs.getString("pk_seq")  ) ){ %>
                        				<option value="<%= kvRs.getString("pk_seq") %>" selected="selected" ><%= kvRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= kvRs.getString("pk_seq") %>" ><%= kvRs.getString("ten") %></option>
                        		 <% } } kvRs.close();} catch(SQLException ex){ex.printStackTrace();}
                        		}
                        	%>
                    	</select>
                    </TD>         	
                </TR>
                
                
                 <TR>
                	<TD class="plainlabel" valign="top">Loại tiêu chí </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<% 	trangthai = new  String[] {"Chỉ tiêu bán ra","Chỉ tiêu mua vào" } ;
							 idTrangThai = new  String[] {"0","1"} ;
						%> 
						<SELECT name="loaitieuchi" >
						<option> </option>
		      		 <% for( int i=0;i<trangthai.length;i++) { 
		    			if(idTrangThai[i].equals(lsxBean.getLoaiTieuChi() ) ){ %>
		      				<option value='<%=idTrangThai[i]%>' selected><%=trangthai[i] %></option>
		      		 	<%}else{ %>
		     				<option value='<%=idTrangThai[i]%>'><%=trangthai[i] %></option>
		     			<%} 
		      		 }
		      		 	%>
					       	</SELECT>
                    </TD>   
                     <TD class="plainlabel" valign="middle" ></TD>   
		                        <TD class="plainlabel" valign="middle" >
		                            
		                        </TD>           
                </TR>
                
                
                
                 <TR>
                	<TD class="plainlabel" valign="top">Diễn giải </TD>
                    <TD class="plainlabel" valign="top"  >
                    	<input type="text"  style="width:100%" name="diengiai" value="<%=lsxBean.getDiengiai() %>" />
                    </TD>   
                     <TD class="plainlabel" valign="middle" >Trạng thái </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                            <% if(lsxBean.getTrangthai().equals("1")) { %>
		                            	<input type="checkbox" name = "trangthai" value="1" checked="checked" ><i> Hoạt động</i>
		                            <% } else { %>
		                            	<input type="checkbox" name = "trangthai" value="1" ><i> Hoạt động</i>
		                            <% } %>
		                        </TD>           
                </TR>
                
                
                
            </TABLE>
            
				<table cellpadding="0px" cellspacing="1px" width="100%">
					<tr class="tbheader">
						<th align="center" width="15%" >Từ mức</th>
						<th align="center" width="15%" >Đến mức</th>
						<th align="center" width="15%" > Loại thưởng  </th>
						<th align="center" width="15%" > Nguồn thưởng  </th>
						<th align="center" width="40%" > Thưởng  </th>
					</tr>
					<%
						int count = 0;
						if(tuMuc != null)
						{
							for(int i = 0; i < tuMuc.length; i++)
							{%>
						
							<tr>
								<td>
									<input type="text" name="tuMuc" value="<%= tuMuc[i] %>" style="width: 100%" onkeyup="Dinhdang(this);"  > 
								</td>
								<td>
									<input type="text" name="denMuc" value="<%= denMuc[i] %>" style="width: 100%"  onkeyup="Dinhdang(this);"  > 
								</td>
								<td>
						<% 
						trangthai = new  String[] {"%","Tiền"  } ; 
						idTrangThai = new  String[] {"0","1"} ;%> 
						<SELECT name="loaiThuong" class="notuseselect2" >
									<option> </option>
				      		 <% for(int j=0;j<trangthai.length;j++) { 
				    			if(idTrangThai[j].equals(loaiThuong[i] ) ){ %>
				      				<option value='<%=idTrangThai[j]%>' selected><%=trangthai[j] %></option>
				      		 	<%}else{ %>
				     				<option value='<%=idTrangThai[j]%>'><%=trangthai[j] %></option>
				     			<%} 
				      		 }
				      		 	%>
					       	</SELECT>
							
								</td>
								<td>
									
						<% 
						trangthai = new  String[] {"Chi nhánh / Đối tác","Công ty"  } ; 
						idTrangThai = new  String[] {"0","1"} ;%> 
						<SELECT name="nguonGoc" class="notuseselect2" >
									<option> </option>
				      		 <% for(int j=0;j<trangthai.length;j++) { 
				    			if(idTrangThai[j].equals(nguonGoc[i] ) ){ %>
				      				<option value='<%=idTrangThai[j]%>' selected><%=trangthai[j] %></option>
				      		 	<%}else{ %>
				     				<option value='<%=idTrangThai[j]%>'><%=trangthai[j] %></option>
				     			<%} 
				      		 }
				      		 	%>
					       	</SELECT>
									
									
								</td>
								<td>
									<input type="text" name="thuong" value="<%=thuong[i].length()>0?formater.format(Double.parseDouble( thuong[i].replaceAll(",", "") ) ):"" %>" style="width: 100%" onkeyup="Dinhdang(this);"  > 
								</td>
							</tr>	
								
						<% count ++; } } %>
					
					<% for(int i = count; i < 20; i++) { %>
						
						<tr>
							<td> <input type="text" name="tuMuc" value="" style="width: 100%"  onkeyup="Dinhdang(this);" > </td>
							
							
							<td> <input type="text" name="denMuc" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" onkeyup="Dinhdang(this);" > 
						</td>
							<td> 
							<% 
									trangthai = new  String[] {"%","Tiền"  } ; 
									idTrangThai = new  String[] {"0","1"} ;%> 
									<SELECT name="loaiThuong" class="notuseselect2" >
												<option> </option>
												
							      		 <% for(int j=0;j<trangthai.length;j++) { %> 
							      			<option value='<%=idTrangThai[j]%>'><%=trangthai[j] %></option>				      		 	 
							      		 <%  } %>
								       	</SELECT> 
							
							
							 </td>
							<td> 
							<% 
							trangthai = new  String[] {"Chi nhánh / Đối tác","Công ty"  } ; 
							idTrangThai = new  String[] {"0","1"} ;%> 
						<SELECT name="nguonGoc" class="notuseselect2" >
									<option> </option>
									
				      		 <% for(int j=0;j<trangthai.length;j++) { %> 
				      			<option value='<%=idTrangThai[j]%>'><%=trangthai[j] %></option>				      		 	 
				      		 <%  } %>
					       	</SELECT>
					       </td>
							<td> <input type="text" name="thuong" value="" style="width: 100%; text-align: right;"  onkeyup="Dinhdang(this);" > </td>
							
						</tr>	
						
					<% }} %>	
							</table>
						</div>
					</fieldset>
            	</div>
			</div>
		</form>
	</BODY>
	<%
	lsxBean.dbClose();
	lsxBean=null;
	%>
</html>