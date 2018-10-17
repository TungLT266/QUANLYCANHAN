<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.erp.beans.baocao.*" %>



<% IBaocao obj = (IBaocao)session.getAttribute("obj"); %>

 
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% ResultSet khoRs = (ResultSet)obj.getKhoRs(); %>
<% ResultSet lspRs = (ResultSet)obj.getSanPhamRs(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>SalesUp - Project</title>
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
	 
	 function sellectAll3()
	 {
		 var chkAll = document.getElementById("chkAll3");
		 var spIds = document.getElementsByName("loaisanpham");
		 
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
	 
	 function sellectAll4()
	 {
		 var chkAll = document.getElementById("chkAll4");
		 var dvkdIds = document.getElementsByName("dvkdId");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < dvkdIds.length; i++)
			 {
				 dvkdIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < dvkdIds.length; i++)
			 {
				 dvkdIds.item(i).checked = false;
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
<form name="erpDmhForm" method="post" action="../../BCGiaiDoanLSXSvl">
<input type="hidden" name="userId" value="<%= obj.getUserId() %>" >
<input type="hidden" name="userTen" value="<%= obj.getUserTen() %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="khoTen" id="khoTen" value="" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; Quản lý cung ứng > Sản xuất > Báo cáo giai đoạn LSX
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
                                   id="tungay" name="tungay" value="<%= obj.getTuNgay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" width="15%" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenNgay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" valign="middle" >Kho </TD>
                        <TD class="plainlabel" valign="middle" colspan="3" >
                            <select name="khoId" id="khoId"   style="width: 600px;" class="select2" >
                            	<option value=""></option>
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
                    <TR>
                    	<TD class="plainlabel" valign="middle" > Sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" colspan="3">
                        	<select name="sanpham"  style="width: 600px;" class="select2" >
                        		<option></option>
                        		<%if(lspRs!=null){
                        			try{
                        				while(lspRs.next()){
                        				if(lspRs.getString("pk_seq").equals(obj.getSanPhamIds())){	%>
                        			<option value="<%= lspRs.getString("pk_seq") %>" selected="selected"> <%= lspRs.getString("ten") %></option>
                        		<%}else{ %>
                        			<option value="<%= lspRs.getString("pk_seq") %>"> <%= lspRs.getString("ten") %></option>
                        		
                        		<%}}}catch(Exception e){e.printStackTrace();}}%>
                        	</select>
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
	dropdowncontent.init('dvkdId', "right-bottom", 300, "click");
	dropdowncontent.init('masanpham', "right-bottom", 300, "click");
	dropdowncontent.init('spId', "right-bottom", 300, "click");
	
</script>
</html>

<% 
try{
	//obj.close();
	
	session.setAttribute("obj", null)  ; 
}catch(Exception er){
	
}
finally{
	
}
%>