<%@page import="geso.traphaco.erp.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.donmuahang.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<%  IStockintransit obj = (IStockintransit)session.getAttribute("obj"); %>
<%  String userId = (String) session.getAttribute("userId");  %>
<%  String userTen = (String) session.getAttribute("userTen");   
	ResultSet Rscongty=obj.getRsErpCongty();
	ResultSet rsDonViTH=obj.getRsErpDonViTH();	
%>

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
    <LINK rel="stylesheet" type="text/css" href="../css/style.css" />
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>

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
	 function submitform1()
	 {   
	
	    document.forms["erpDmhForm"].action.value="submit";
	    document.forms["erpDmhForm"].submit();
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
<form name="erpDmhForm" method="post" action="../../BCTheoDoiNganSachChiPhi">
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
        	&#160; Quản lý ngân sách &gt; Theo dõi ngân sách &gt; Theo dõi chi phí
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
                    <TD class="plainlabel" width="20%">Chọn công ty </TD>
                    <TD colspan="2" class="plainlabel">
                        <select name="congtyid" id="congtyid" onchange="submitform1()" >
                        	<option value=""> </option>
                        	<%
                        		if(Rscongty != null)
                        		{
                        			try
                        			{
                        			while(Rscongty.next())
                        			{  
                        			if( Rscongty.getString("pk_seq").equals(obj.getErpCongtyId())){ %>
                        				<option value="<%= Rscongty.getString("pk_seq") %>" selected="selected" ><%= Rscongty.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= Rscongty.getString("pk_seq") %>" ><%= Rscongty.getString("ten") %></option>
                        		 <% } 
                        			} Rscongty.close(); 
                        		 } catch( Exception er){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>
                     <TR>
                    <TD class="plainlabel" width="20%">Đơn vị thực hiện </TD>
                    <TD colspan="2" class="plainlabel">
                        <select name="donvithid" id="donvithid"  >
                        	<option value=""> </option>
                        	<%
                        		if(rsDonViTH != null)
                        		{
                        			try
                        			{
                        			while(rsDonViTH.next())
                        			{  
                        			if( rsDonViTH.getString("pk_seq").equals(obj.getErpDonViTHId())){ %>
                        				<option value="<%= rsDonViTH.getString("pk_seq") %>" selected="selected" ><%= rsDonViTH.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= rsDonViTH.getString("pk_seq") %>" ><%= rsDonViTH.getString("ten") %></option>
                        		 <% } 
                        			} rsDonViTH.close(); 
                        		 } catch( Exception er){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>
                  
                    
              
                  
                      <TR>
                         <TD class="plainlabel" valign="middle" >Chọn  năm</TD>
                       <TD class="plainlabel" valign="middle">      

                    			  <select name="nam"  style="width :50px" onchange="loctien()">
										<option value=0> </option>  
									<%
  										for(int n=2010;n<2018;n++){
  										if(obj.getToYear().equals(""+n)){
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
 									 Từ tháng
                       
                    			  <select name="tuthang"  style="width :50px" >
										<option value=0> </option>  
									<%
  										for(int n=1;n<=12;n++){
  										if(obj.getFromMonth().equals(""+n)){
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
 									Đến tháng
 									
 									 <select name="denthang"  style="width :50px" >
										<option value=0> </option>  
									<%
  										for(int n=1;n<=12;n++){
  										if(obj.getToMonth().equals(""+n)){
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
	jQuery(function()
		{		
			$("#taikhoanktid").autocomplete("ErpTKKetToanList.jsp");
		});	
</script>
</html>
<%
try{
	obj.DBclose();
	session.setAttribute("obj", null) ; 
}catch (Exception ex)
{
	ex.printStackTrace();
}
%>