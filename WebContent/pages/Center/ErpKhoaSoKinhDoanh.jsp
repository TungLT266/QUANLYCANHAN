<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.center.beans.khoasothang.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<% 	IErpKhoasokinhdoanh obj = (IErpKhoasokinhdoanh)session.getAttribute("obj"); %>
<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen");  %>
<% 	ResultSet chungtuRs = (ResultSet) obj.getChungtuRs(); 
	Utility util = new Utility();
	Utility util1 = (Utility) session.getAttribute("util");
	int[] quyen = new int[5];
	quyen=util.Getquyen("ErpKhoasokinhdoanhSvl", "", userId);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>TraphacoHYERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    
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
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	 function submitform()
	 {   
		 document.forms['erpCngn'].action.value='submit';
	     document.forms["erpCngn"].submit();
	 }
	 
	 function saveform()
	 {   
		 var msg = document.getElementById("msg").value;
		 if(msg.length > 0 )
		 {
			 var r = confirm(msg + "\n, bạn vẫn muốn tiếp tuc?");
			 if(r == false)
				 return;
		 }
		 else
		 {
			 var r = confirm("Hệ thống sẽ khóa sổ kinh doanh tháng hiện tại, mọi giao dịch trong tháng sẽ được đóng lại, bạn vẫn muốn tiếp tục?");
			 if(r == false)
				 return;
		 }
		 
		 document.forms['erpCngn'].action.value='save';
	     document.forms["erpCngn"].submit();
	 }
	
	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	 </SCRIPT>
	 
</head>
<body>
<form name="erpCngn" method="post" action="../../ErpKhoasokinhdoanhSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" id="msg" value="<%= obj.getMsg() %>" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
             Quản lý bán hàng > Nghiệp vụ khác > Khóa sổ kinh doanh
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
    <div align="left" style="width: 100%; float: none; clear: left">
		<fieldset>
			<legend class="legendtitle" > Thông báo</legend>
			<textarea rows="2"  style="width: 100%; color:#F00 ; font-weight:bold">
				<%= obj.getMsg().trim() %> </textarea>
		</fieldset>
	</div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle">Khóa sổ kinh doanh</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px">								                          
                    <TR>
                        <TD class="plainlabel" width="15%">Tháng </TD>
                        <TD class="plainlabel">
                            <input type="text" name="thang" value="<%= obj.getThang() %>" readonly="readonly"> 
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Năm </TD>
                        <TD class="plainlabel">
                        	<input type="text" name="nam" value="<%= obj.getNam() %>" readonly="readonly"> 
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" >Ngày </TD>
                        <TD class="plainlabel">
                        	<select name="ngay" onchange="javascript:submitform();" >
                        		<% for(int i = 0; i <= 31; i++ ) { 
                        			if( obj.getNgay().equals( Integer.toString(i) ) ) { %>
                        				<option value="<%= i %>" selected="selected" ><%= i %></option>		
                        		<% } else { %> 
                        				<option value="<%= i %>" ><%= i %></option>		
                        		<% } } %>
                        	</select>
                        </TD>
                    </TR>
                    <tr>
                        <td colspan="2" class="plainlabel">
                        		<%if(quyen[2]!=0){ %>
                            		<a class="button2" href="javascript:saveform();">
                                	<img style="top: -4px;" src="../images/button.png" alt="">Khóa sổ kinh doanh</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                <%} %>
                        </td>
                    </tr>        					
                </TABLE>    
                
                <hr />
                
				<table width="100%" cellpadding="0px" cellspacing="1px">
                	<tr class="tbheader">
                		<th align="center" width="30%"></th>
                		<th align="center" width="60%">Chứng từ</th>
                		<th align="center" width="10%">Tình trạng</th>
                	</tr>
                	<% if(chungtuRs != null) { 
                		while(chungtuRs.next())
                		{
                			%>
                			<tr>
                				<td>
                					<input type="text" style="width: 100%" value="<%= chungtuRs.getString("type") %>" readonly="readonly"> 
                				</td>
                				<td>
                					<input type="text" style="width: 100%" value="<%= chungtuRs.getString("chungtu") %>" readonly="readonly" > 
                				</td>
                				<td>
                					<input type="text" style="width: 100%" value="<%= chungtuRs.getString("trangthai") %>" readonly="readonly" >
                				</td>
                			</tr>
                			
                		<%  } chungtuRs.close();
                	} %>
                </table>    
																
                
                              
        </fieldset>                      
    	</div>
        
    </div>  
</div>
</form>
</body>
</html>
<% 
if(obj!=null)
{
	obj.DBClose();
	session.setAttribute("obj",null);
}
%>