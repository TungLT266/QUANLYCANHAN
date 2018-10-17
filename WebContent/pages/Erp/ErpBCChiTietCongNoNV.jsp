<%@page import="geso.traphaco.erp.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
 
<%@ page  import = "java.sql.ResultSet" %>

<% 	IStockintransit obj = (IStockintransit)session.getAttribute("obj"); %>
<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen");   
	
	ResultSet kbhRs = obj.getkenh(); 
	ResultSet khRs = obj.getRsNhanvien();
	ResultSet nhomkhRs = obj.getRsNhomKh();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Phanam - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

			<script type="text/javascript" src="../scripts/jquery.min.js"></script>
		<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
		<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
		<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
		<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
		<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
		<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

	
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
	    document.forms["erpDmhForm"].action.value="Taomoi";
	    document.forms["erpDmhForm"].submit();
	 }

	 function ChangeSalesChannel(){
		    document.forms["erpDmhForm"].action.value="kenhbanhang";
		    document.forms["erpDmhForm"].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 
	</SCRIPT>
	
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpBCChiTietCongNoNV">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Báo cáo quản trị > Công nợ > Công nợ chi tiết nhân viên
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
                                   id="tungay" name="tungay" value="<%= obj.gettungay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getdenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                   
                  <%--   <TR>
                        <TD class="plainlabel" valign="middle" >Kênh bán hàng </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="kbhId" style="width: 200px" onChange = "ChangeSalesChannel();" >
                            	<option value=""></option>
                    <% if(kbhRs != null){
                     		while (kbhRs.next()){ 
                            	if(kbhRs.getString("PK_SEQ").equals(obj.getkenhId())){
                    %>
                            	<option value="<%= kbhRs.getString("PK_SEQ") %>" SELECTED ><%= kbhRs.getString("DIENGIAI") %></option>
                            	
                    <% 			}else{ %>        	
                    			<option value="<%= kbhRs.getString("PK_SEQ") %>"  ><%= kbhRs.getString("DIENGIAI") %></option>
                    <%			} %>
                    <%   	}
                       }	%>
                            </select>
                        </TD>                        
                    </TR>  --%>
                    
              	<%-- 	<TR>
                        <TD class="plainlabel" valign="middle" >Nhóm khách hàng </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="nkhId" style="width: 200px" onChange = "ChangeSalesChannel();">
                            	<option value=""></option>
				                    <% if(nhomkhRs != null){
				                     		while (nhomkhRs.next()){ 
				                            	if(nhomkhRs.getString("PK_SEQ").equals(obj.getNhomKhId())){
				                    %>
				                            	<option value="<%= nhomkhRs.getString("PK_SEQ") %>" SELECTED ><%= nhomkhRs.getString("DIENGIAI") %></option>
				                            	
				                    <% 			}else{ %>        	
				                    			<option value="<%= nhomkhRs.getString("PK_SEQ") %>"  ><%= nhomkhRs.getString("DIENGIAI") %></option>
				                    <%			} %>
				                    <%   	}
				                       }	%>
                            </select>
                        </TD>                        
                    </TR>  --%>
                    
                    <TR>
                        <TD class="plainlabel" valign="middle" >Chọn nhân viên </TD>
                        <TD class="plainlabel" valign="middle" >
                       		 <select name="nvid" id = "nvid" style="width: 200px">
                            	<option value=""></option>
				                    <% if(khRs != null){
				                     		while (khRs.next()){ 
				                            	if(khRs.getString("PK_SEQ").equals(obj.getNhanvienId())){
				                    %>
				                            	<option value="<%= khRs.getString("PK_SEQ") %>" SELECTED ><%= khRs.getString("MA") + ',' + khRs.getString("TEN") %></option>
				                            	
				                    <% 			}else{ %>        	
				                    			<option value="<%= khRs.getString("PK_SEQ") %>"  ><%= khRs.getString("MA") + ',' + khRs.getString("TEN")  %></option>
				                    <%			} %>
				                    <%   	}
				                       }	%>
                            </select>
                                          
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
	/* jQuery(function()
	{		
		$("#khid").autocomplete("ErpKhachHangList.jsp");
	});	 */
	
</script>
</html>
<%
	
	if(kbhRs != null) kbhRs.close();
	obj.DBclose();
%>