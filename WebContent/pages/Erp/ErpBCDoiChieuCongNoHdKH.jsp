<%@page import="geso.traphaco.erp.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>

<% IStockintransit obj = (IStockintransit)session.getAttribute("obj"); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 

	ResultSet RsNkh=obj.getRsNhomKh();
	ResultSet RsKh=obj.GetRsKhErp();
	ResultSet RsKbh=obj.getkenh();
	ResultSet rsdvkd=obj.getdvkd();
	ResultSet ctyRs = obj.getCtyRs();
	String ctyIds = obj.getErpCongtyId();
	ResultSet khRs = obj.GetRsKhErp();
	//System.out.println("ctyIds:" + ctyIds);
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
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>

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
	 
	 function Change()
	 {   
	
	    document.forms["THCongnoKHForm"].action.value="change";
	    document.forms["THCongnoKHForm"].submit();
	 }
	 
	 function submitform()
	 {   
	
	    document.forms["THCongnoKHForm"].action.value="Taomoi";
	    document.forms["THCongnoKHForm"].submit();
	 }
	 function submitform1()
	 {   
	
	    document.forms["THCongnoKHForm"].action.value="";
	    document.forms["THCongnoKHForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["THCongnoKHForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["THCongnoKHForm"].msg.value);
	 }
	 
	 function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var khIds = document.getElementsByName("khIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = false;
			 }
		 }
	 }
	 function sellectAll1()
	 {
		 var chkAll = document.getElementById("chkAll1");
		 var khIds = document.getElementsByName("nkhIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = false;
			 }
		 }
	 }
	 
	 function sellectNhomAll()
	 {
		 var chkAll = document.getElementById("chkNhAll");
		 var nccIds = document.getElementsByName("nspIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < nccIds.length; i++)
			 {
				 nccIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < nccIds.length; i++)
			 {
				 nccIds.item(i).checked = false;
			 }
		 }
	 }
	</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
</head>
<body>
<form name="THCongnoKHForm" method="post" action="../../ErpBCDoiChieuCongNoHdKH">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="view" value="<%= obj.getView() %>" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Báo cáo quản trị > Công nợ > Báo cáo đối chiếu công nợ KH
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
                        
                         <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getdenngay() %>" maxlength="10" />
                        </TD>
                        
                        
                        <TD class="plainlabel" width="15%"> </TD>
                        <TD class="plainlabel" >
                        </TD>
                    </TR>
                   
                    <TR>
                        <TD class="plainlabel" valign="middle" >Kênh bán hàng </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="kbhid" onChange="submitform1();" style="width: 200px">
                           		<option value=""> </option>
								<%  
   									if(RsKbh != null)
   									{
   										while(RsKbh.next())
   										{
   											if(RsKbh.getString("pk_seq").equals(obj.getkenhId())){ %>
   												<option value="<%= RsKbh.getString("pk_seq") %>" selected="selected" ><%= RsKbh.getString("DIENGIAI") %></option>
   											<%} else { %> 
   												<option value="<%= RsKbh.getString("pk_seq") %>"><%= RsKbh.getString("DIENGIAI") %></option>
   											<%}
   										}
   										RsKbh.close();
   									}
   								%>
                           </select>
                        </TD> 
                        
                        <TD class="plainlabel" valign="middle" >Chọn khách hàng </TD>
                        <TD class="plainlabel" valign="middle" colspan = "3">
                       		 <select name="khid" id = "khid" style="width: 200px">
                            	<option value=""></option>
				                    <% if(khRs != null){
				                     		while (khRs.next()){ 
				                            	if(khRs.getString("PK_SEQ").equals(obj.getErpKhachHangId())){
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
                        <td colspan="4" class="plainlabel">
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
dropdowncontent.init('nkhId', "right-bottom", 300, "click");
dropdowncontent.init('khId', "right-bottom", 300, "click");
dropdowncontent.init('nspId', "right-top", 300, "click");
</script>
</html>
<%
	if(RsNkh != null) RsNkh.close();
	if(RsKh != null) RsKh.close();

	if(rsdvkd != null) rsdvkd.close();
	if(ctyRs != null) ctyRs.close();

	obj.DBclose();%>