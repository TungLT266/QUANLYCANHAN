<%@page import="geso.traphaco.erp.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>

<%  IStockintransit obj = (IStockintransit)session.getAttribute("obj"); %>
<%  String userId = (String) session.getAttribute("userId");  %>
<%  String userTen = (String) session.getAttribute("userTen");   
	ResultSet Rscongty=obj.getRsErpCongty();
	ResultSet RsTiente=obj.getRsErpTiente();
	String[][] mang= obj.getMang2chieu();
	ResultSet ctyRs = obj.getCtyRs();
	String [] ctyIds = obj.getCtyIds();
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
	 
	 function Change()
	 {   
	
	    document.forms["chitietKTForm"].action.value="change";
	    document.forms["chitietKTForm"].submit();
	 }
	 //XEM EXCEL
	 function submitform()
	 {   
		if(!kiemTra()){
			alert("Bạn vui lòng nhập đầy đủ thông tin .");
			return false;
		}
	    document.forms["chitietKTForm"].action.value="Taomoi";
	    document.forms["chitietKTForm"].submit();
	 }
	//XEM WEB
	 function submitform1()
	 {   
		if(!kiemTra()){
			alert("Bạn vui lòng nhập đầy đủ thông tin .");
			return false;
		}
	    document.forms["chitietKTForm"].action.value="submit";
	    document.forms["chitietKTForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["chitietKTForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["chitietKTForm"].msg.value);
	 }
	 function kiemTra(){
		 var tienTeId = document.getElementById("tienteid");
		 var taiKhoanId = document.getElementById("taikhoanktid");
		 if(tienTeId.value.trim().length == 0 || taiKhoanId.value.trim().length == 0 ){
			 return false;
		 }
		 var congTyIds = document.getElementById("ctyIds");
		 if (congTyIds != null){
			 if (congTyIds.value.trim().length == 0){
				 return false;
			 }
		 }
		 return true;
	 }

	
	</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

</head>
<body>
<form name="chitietKTForm" method="post" action="../../BCChiTietTKKeToan">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="view" value="<%= obj.getView() %>" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>' id ="msg">
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; Báo cáo quản trị > Kế toán > Tổng hợp tài khoản kế toán
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tổng hợp tài khoản kế toán</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >			
           <% if(obj.getView().equals("TT")){ %>							                          
                    <TR>
                        <TD class="plainlabel" width="15%">Chọn công ty </TD>
                        <TD class="plainlabel" >
                            <SELECT NAME = "ctyIds"   style = "width:300px;size:5" onChange = "Change();" id = "ctyIds">
                            	<OPTION VALUE = "">&nbsp;</OPTION>
                         
					<%
					if(ctyRs != null){ 
							while(ctyRs.next()){
								int dem = 0;
									if(ctyIds != null){
								for ( int i = 0 ; i<ctyIds.length; i ++){
									if(ctyIds[i]!=null){
								if(ctyIds[i].trim().equals(ctyRs.getString("PK_SEQ"))){
									dem ++;
							%>
								
								<OPTION VALUE = "<%= ctyRs.getString("PK_SEQ")%>" SELECTED ><%= ctyRs.getString("TEN")%></OPTION>
					<%			}}}}
								if (dem <1){ %>
					
								<OPTION VALUE = "<%= ctyRs.getString("PK_SEQ")%>" ><%= ctyRs.getString("TEN")%></OPTION>
								
					<%			}

							} 
						}%>
                            </SELECT>
                        </TD>
                    </TR>
			<%} %>								                          
                					                          
                    <TR>
                        <TD class="plainlabel" valign="middle" width = "20%">Số và tên tài khoản </TD>
                           
                        <TD class="plainlabel" valign="middle" >
                         <input type="text" name="taikhoanktid" id="taikhoanktid" value="<%=obj.getErpTaiKhoanKTId()%>">
              
                        </TD>                        
                    </TR> 
                    <TR>
                       <TD class="plainlabel" valign="middle" >Chọn  năm</TD>
                       <TD class="plainlabel" valign="middle">      

                    			  <select name="nam"  style="width :100px" onchange="loctien()">
										<option value=0> </option>  
									<%
  										for(int n=2010;n<2018;n++){
  										if(obj.getYear().equals(""+n)){
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
                    
                     <TR>
                    <TD class="plainlabel">Tiền tệ </TD>
                    <TD colspan="2" class="plainlabel">
                        <select name="tienteid" id="tienteid" style = "width:200px" >
                        	<option value=""> </option>
                        	<%
                        		if( RsTiente!= null)
                        		{
                        			try
                        			{
                        			while(RsTiente.next())
                        			{  
                        			if( RsTiente.getString("pk_seq").equals(obj.getErpTienteId())){ %>
                        				<option value="<%= RsTiente.getString("pk_seq") %>" selected="selected" ><%= RsTiente.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= RsTiente.getString("pk_seq") %>" ><%= RsTiente.getString("ten") %></option>
                        		 <% } 
                        			} RsTiente.close(); 
                        		 } catch(Exception ex){
                        			 ex.printStackTrace();
                        		 }
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>            
                     <tr>
                    	 <td  class="plainlabel">
                           <a class="button" href="javascript:submitform1();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Xem Web </a>
                        </td>
                        <td class="plainlabel">
                           <a class="button" href="javascript:submitform();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Xem Excel </a>
                        </td>
                        
                        
                    </tr> 
                    
                    </TABLE>                  
       				</fieldset>            					                    
    				</div>
    	  				<div>
    	  				<table width="100%" border="0" cellspacing = "1" cellpadding = "4">
    	  					<tr class="tbheader" >
    	  					<% 	 
    	  					 if( mang!=null){
    	  						
    	  					  for(int i=0; i< mang[0].length;i++)  
    	  					  {
    	  						  if(i == 0){
    	  						 %>
    	  						  	<th width = "10%">
    	  					<% 	  }	%>
    	  						  
    	  					<%	  if(i == 1){
    	  						 %>
    	  						  	<th width = "20%">
    	  					<% 	  }	%>

    	  					<%	  if(i == 2){
    	  						 %>
    	  						  	<th width = "20%">
    	  					<% 	  }	%>

    	  					<%	  if(i == 3){
    	  						 %>
    	  						  	<th width = "20%">
    	  					<% 	  }	%>
    	  						  
    	  					<%	  if(i == 4){
    	  						 %>
    	  						  	<th width = "20%">
    	  					<% 	  }	%>

    	  						  	 <%=mang[0][i] %> 						  
    	  						  </th >
    	  						 <%
    	  					  }
    	  					  %>
    	  					  </tr>
    	  					  <%
    	  					  String format = "";
    	  					  for(int j=1;j<mang.length;j++){
 	  							 if(j % 2 == 0){
	  								 format = "tblightrow";
	  							 }else{
	  								format = "tbdarkrow";
	  							 }

    	  						  if(j == 1 || j == 15){
    	  						  %>
    	  						  <tr height="25" class = <%= format %> style="font-size :1.3em;font-weight: bold;color:navy; " >
    	  						  <%
    	  						 }else{
    	  							 %>
       	  						  	<tr class = <%= format %>   >
       	  						  <%
    	  						  }
    	  						 for(int i=0; i< mang[0].length;i++)  
	       	  					  {
	       	  						 %>
	       	  						  <td>
	       	  						  	 <%=mang[j][i] == null? 0: mang[j][i] %> 						  
	       	  						  </td>
	       	  						 <%
	       	  					  } 
    	  						  %>
	   	  						  <tr>
	   	  						  <%
    	  					  }
    	  					 }
    	  					  %>
    	  					  
    	  				</table>
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
<%obj.DBclose();%>