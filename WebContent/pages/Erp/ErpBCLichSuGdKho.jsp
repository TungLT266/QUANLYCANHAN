<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.baocao.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<% ILichSuGdKho bcLSKho = (ILichSuGdKho)session.getAttribute("bcLSKho"); %>
<% ResultSet lspRs = (ResultSet)bcLSKho.getLoaiSanPhamRs(); %>
<% ResultSet spRs = (ResultSet)bcLSKho.getSanPhamRs(); %>
<% ResultSet khoRs = (ResultSet)bcLSKho.getKhoRs(); %>
<% ResultSet lctRs = (ResultSet)bcLSKho.getLoaiCtRs(); %>
<% ResultSet malonsanphamRs = (ResultSet)bcLSKho.getMalonsanphamRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>TraphacoHYERP - Project</TITLE>
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
		 if(document.getElementById("tungay").value == "")
		 {
			 alert('Vui lòng chọn thời gian bắt đầu lấy báo cáo');
			 return;
		 }
		 
		 if(document.getElementById("denngay").value == "")
		 {
			 alert('Vui lòng chọn thời gian kết thúc lấy báo cáo');
			 return;
		 }
		 
		 var khoId = document.getElementById("khoId");
		 document.getElementById("khoTen").value  = khoId.options[khoId.selectedIndex].text
		
		 //alert(document.getElementById("khoTen").value);
		 
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
			 for(var i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(var i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	 
	 function sellectAll_Lct()
	 {
		 var chkAll_Lct = document.getElementById("chkAll_Lct");
		 var loaiCtIds = document.getElementsByName("loaiCtIds");
		 
		 if(chkAll_Lct.checked)
		 {
			 for(var i = 0; i < loaiCtIds.length; i++)
			 {
				 loaiCtIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(var i = 0; i < loaiCtIds.length; i++)
			 {
				 loaiCtIds.item(i).checked = false;
			 }
		 }
	 }
	  function sellectAll_Lsp()
	 {
		 var chkAll_Lsp = document.getElementById("chkAll_Lsp");
		 var loaiSpIds = document.getElementsByName("loaiSpIds");
		 
		 if(chkAll_Lsp.checked)
		 {
			 for(var i = 0; i < loaiSpIds.length; i++)
			 {
				 loaiSpIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(var i = 0; i < loaiSpIds.length; i++)
			 {
				 loaiSpIds.item(i).checked = false;
			 }
		 }
	 }
	  
	  function sellectAll_Mlsp()
		 {
			 var chkAll_Mlsp = document.getElementById("chkAll_Mlsp");
			 var malonsanphamIds = document.getElementsByName("malonsanphamIds");
			 
			 if(chkAll_Mlsp.checked)
			 {
				 for(var i = 0; i < malonsanphamIds.length; i++)
				 {
					 malonsanphamIds.item(i).checked = true;
				 }
			 }
			 else
			 {
				 for(var i = 0; i < malonsanphamIds.length; i++)
				 {
					 malonsanphamIds.item(i).checked = false;
				 }
			 }
		 }
	  
	  function thongbao()
		 {
			 var tt = document.forms["erpDmhForm"].msg.value;
		 	 if(tt.length>0)
		     	alert(document.forms["erpDmhForm"].msg.value);
		 }
	</SCRIPT>
</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpBcLichSuGdKhoSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="khoTen" id="khoTen" value="" >
<input type="hidden" name="msg" value='<%= bcLSKho.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<%System.out.println(" thong bao  "+bcLSKho.getMsg()  ); %>
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; Quản lý cung ứng > Báo cáo > Lịch sử giao dịch kho
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                          
                    <TR>
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= bcLSKho.getTuNgay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" width="15%" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= bcLSKho.getDenNgay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" valign="middle" >Loại sản phẩm </TD>
                                   <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="lspId" rel="subcontentLsp">
				&nbsp; <img alt="Loại sản phẩm" src="../images/vitriluu.png"></a>
	           	 		
                          <DIV id="subcontentLsp" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; height:300px; overflow:auto; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="350px">Loại sản phẩm</th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll_Lsp()" id="chkAll_Lsp" ></th>
				                        </tr>
		                            	<%
			                        		if(lspRs != null)
			                        		{
			                        			while(lspRs.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%;padding: 2px;margin:5px;" value="<%= lspRs.getString("ten") %>" readonly ></td>
			                        				<td align="center">
			                        				<% if(bcLSKho.getLoaiSanPhamIds().indexOf(lspRs.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name=loaiSpIds checked="checked" value="<%= lspRs.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="loaiSpIds" value="<%= lspRs.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        		 <% } 
			                        			lspRs.close();
			                        		 } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentLsp')">Hoàn tất</a>
				                     </div>
				            </DIV>                        
                        </TD>                    
                        
                    </TR> 
                    
                    <TR>
                        <TD class="plainlabel" valign="middle" >Mã lớn sản phẩm </TD>
                                   <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="malonsanphamId" rel="subcontentMlsp">
				&nbsp; <img alt="Mã lớn sản phẩm" src="../images/vitriluu.png"></a>
	           	 		
                          <DIV id="subcontentMlsp" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; height:300px; overflow:auto; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="350px">Mã lớn sản phẩm</th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll_Mlsp()" id="chkAll_Mlsp" ></th>
				                        </tr>
		                            	<%
			                        		if(malonsanphamRs != null)
			                        		{
			                        			while(malonsanphamRs.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%;padding: 2px;margin:5px;" value="<%= malonsanphamRs.getString("MA") %>" readonly ></td>
			                        				<td align="center">
			                        				<% if(bcLSKho.getMalonsanphamIds().indexOf(malonsanphamRs.getString("MA")) >= 0 ){ %>
			                        					<input type="checkbox" name=malonsanphamIds checked="checked" value="<%= malonsanphamRs.getString("MA") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="malonsanphamIds" value="<%= malonsanphamRs.getString("MA") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        		 <% } malonsanphamRs.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentMlsp')">Hoàn tất</a>
				                     </div>
				            </DIV>                        
                        </TD>                    
                        
                    </TR> 
                    
                     <TR>
                        <TD class="plainlabel" valign="middle" >Kho </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="khoId" id="khoId">
                            	<option value=""></option>
                            	<%
	                        		if(khoRs != null)
	                        		{
	                        			while(khoRs.next())
	                        			{  
	                        			if( khoRs.getString("pk_seq").equals(bcLSKho.getKhoIds())){ %>
	                        				<option value="<%= khoRs.getString("pk_seq") %>" selected="selected" ><%= khoRs.getString("khoTen") %></option>
	                        			<%}else { %>
	                        				<option value="<%= khoRs.getString("pk_seq") %>" ><%= khoRs.getString("khoTen") %></option>
	                        		 <% } } khoRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR>     
                    
                     <TR>
                        <TD class="plainlabel" valign="middle" >Loại chứng từ </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="lctId" rel="subcontentLct">
				&nbsp; <img alt="Loại chứng từ" src="../images/vitriluu.png"></a>
	           	 		
                          <DIV id="subcontentLct" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; height:300px; overflow:auto; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="350px">Loại chứng từ</th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll_Lct()" id="chkAll_Lct" ></th>
				                        </tr>
		                            	<%
			                        		if(lctRs != null)
			                        		{
			                        			while(lctRs.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%;padding: 2px;margin:5px;" value="<%= lctRs.getString("ten") %>" readonly ></td>
			                        				<td align="center">
			                        				<% if(bcLSKho.getLoaiCtIds().indexOf(lctRs.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name=loaiCtIds checked="checked" value="<%= lctRs.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="loaiCtIds" value="<%= lctRs.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        		 <% } lctRs.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentLct')">Hoàn tất</a>
				                     </div>
				            </DIV>                        
                        </TD>                        
                    </TR>     
                    
                    
                    
                    <TR>
                        <TD class="plainlabel" valign="middle" >Chọn sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="spId" rel="subcontentSp">
				&nbsp; <img alt="Thông tin sản phẩm" src="../images/vitriluu.png"></a>
	           	 		
                          <DIV id="subcontentSp" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; height:300px; overflow:auto; padding: 4px;">
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
			                        				<td><input style="width: 100%;padding: 2px;margin:5px;" value="<%= spRs.getString("ma") %>"  readonly ></td>
			                        				<td><input style="width: 100%;padding: 2px;margin:5px;" value="<%= spRs.getString("ten") %>" readonly></td>
			                        				<td align="center">
			                        				<% if(bcLSKho.getSanPhamIds().indexOf(spRs.getString("pk_seq")) >= 0 ){ %>
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
    </div>  
</div>
</form>
</body>
<script type="text/javascript">
	dropdowncontent.init('lspId', "right-bottom", 300, "click");
	dropdowncontent.init('spId', "right-bottom", 300, "click");
	dropdowncontent.init('lctId', "right-bottom", 300, "click");
	dropdowncontent.init('malonsanphamId', "right-bottom", 300, "click");
</script>
</html>