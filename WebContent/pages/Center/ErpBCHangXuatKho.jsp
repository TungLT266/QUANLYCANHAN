<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.center.beans.baocao.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IBaocao obj = (IBaocao)session.getAttribute("obj"); %>
<% ResultSet lspRs = (ResultSet)obj.getLoaiSanPhamRs(); %>
<% ResultSet clRs = (ResultSet)obj.getChungloaiRs(); %>
<% ResultSet spRs = (ResultSet)obj.getSanPhamRs(); %>
<% ResultSet khoRs = (ResultSet)obj.getKhoRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% Utility util = (Utility) session.getAttribute("util");
String url="";
url = util.getUrl("ErpBCHangxuatSvl",""); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Diageo - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
  	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
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
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	 function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	 
	 function submitform()
	 { 
		 document.forms["erpDmhForm"].action.value = "submit";
	     document.forms["erpDmhForm"].submit();
	 }
	 
	 function locsanpham()
	 {   
		 document.forms["erpDmhForm"].action.value = "search";
	     document.forms["erpDmhForm"].submit();
	 }
	
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 
	 function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var spIds = document.getElementsByName("spIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	 
	 function sellectAll2()
	 {
		 var chkAll = document.getElementById("chkAll2");
		 var spIds = document.getElementsByName("clIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	</SCRIPT>
</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpBCHangxuatSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="khoTen" id="khoTen" value="" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	<%=url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
    <div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror" style="width: 100%;color: red" readonly="readonly" rows="1" readonly="readonly"><%= obj.getMsg() %></textarea>
		         <% obj.setMsg(""); %>
    	</fieldset>
  	</div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                          
                    <TR >
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTuNgay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR >
                        <TD class="plainlabel" width="15%" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenNgay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <TR style="display: none;" >
                        <TD class="plainlabel" valign="middle" width="15%">Loại sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="loaisanpham" onchange="locsanpham()">
                            	<option value=""></option>
                            	<%
	                        		if(lspRs != null)
	                        		{
	                        			while(lspRs.next())
	                        			{  
	                        			if( lspRs.getString("pk_seq").equals(obj.getLoaiSanPhamIds())){ %>
	                        				<option value="<%= lspRs.getString("pk_seq") %>" selected="selected" ><%= lspRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= lspRs.getString("pk_seq") %>" ><%= lspRs.getString("ten") %></option>
	                        		 <% } } lspRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                     <TR >
                        <TD class="plainlabel" valign="middle" >Kho </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="khoId" id="khoId">
                            	<%
	                        		if(khoRs != null)
	                        		{
	                        			while(khoRs.next())
	                        			{  
	                        			if( khoRs.getString("pk_seq").equals(obj.getKhoIds())){ %>
	                        				<option value="<%= khoRs.getString("pk_seq") %>" selected="selected" ><%= khoRs.getString("khoTen") %></option>
	                        			<%}else { %>
	                        				<option value="<%= khoRs.getString("pk_seq") %>" ><%= khoRs.getString("khoTen") %></option>
	                        		 <% } } khoRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                    <tr style="display: none">
                        <td class="plainlabel">Lấy số lượng lớn hơn 0 </td>
                        <td class="plainlabel">
                        	<% if(obj.getFlag().equals("1")){ %>
                        		<input type="checkbox" name="flag" value="1" checked="checked">
                        	<%} else { %>
                        		<input type="checkbox" name="flag" value="1">
                        	 <% } %>
                         </td>
                    </tr>     
                    <TR style="display: none;" >
                        <TD class="plainlabel" valign="middle" >Chọn chủng loại </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="clId" rel="subcontentCl">
	           	 							&nbsp; <img alt="Thông tin chủng loại" src="../images/vitriluu.png"></a>
	           	 		
                                           
                        </TD>                        
                    </TR>   
                    <TR style="display: none;" >
                        <TD class="plainlabel" valign="middle" >Chọn sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="spId" rel="subcontentSp">
	           	 							&nbsp; <img alt="Thông tin sản phẩm" src="../images/vitriluu.png"></a>
	           	 		
                                      
                        </TD>                        
                    </TR>
                     
                     <tr>
                        <td colspan="2" class="plainlabel">
                            <a class="button" href="javascript:submitform();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo  </a>
                        </td>
                    </tr> 
                    
                    </TABLE>                  
       </fieldset>            					                    
    	</div>
    	 <DIV id="subcontentCl" style="margin-top:0px;margin-left:190px; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; height:300px; overflow-y:scroll; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="100px">Mã CL</th>
				                            <th width="350px">Tên </th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll2()" id="chkAll2" ></th>
				                        </tr>
		                            	<%
			                        		if(clRs != null)
			                        		{
			                        			while(clRs.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%= clRs.getString("pk_seq") %>"></td>
			                        				<td><input style="width: 100%" value="<%= clRs.getString("ten") %>"></td>
			                        				<td align="center">
			                        				<% if(obj.getChungloaiIds().indexOf(clRs.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name="clIds" checked="checked" value="<%= clRs.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="clIds" value="<%= clRs.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } clRs.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentCl')" onclick="locsanpham()" >Hoàn tất</a>
				                     </div>
				            </DIV>      
    </div>  
      <DIV id="subcontentSp" style="margin-top:-327px;margin-left:220px; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; height:300px; overflow-y:scroll; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="100px">Mã sản phẩm</th>
				                            <th width="350px">Tên sản phẩm</th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll()" id="chkAll" ></th>
				                        </tr>
		                            	<%
			                        		if(spRs != null)
			                        		{
			                        			while(spRs.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%= spRs.getString("ma") %>"></td>
			                        				<td><input style="width: 100%" value="<%= spRs.getString("ten") %>"></td>
			                        				<td align="center">
			                        				<% if(obj.getSanPhamIds().indexOf(spRs.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name="spIds" checked="checked" value="<%= spRs.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="spIds" value="<%= spRs.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } spRs.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentSp')">Hoàn tất</a>
				                     </div>
				            </DIV>          
</div>
</form>
</body>
<script type="text/javascript">
	dropdowncontent.init('spId', "right-bottom", 300, "click");
	dropdowncontent.init('clId', "right-bottom", 300, "click");
</script>
</html>