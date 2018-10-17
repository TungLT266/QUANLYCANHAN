<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.baocao.*" %>
<%@ page  import = "java.sql.ResultSet" %>



<% IBaocao obj = (IBaocao)session.getAttribute("obj"); %>
<% ResultSet lspRs = (ResultSet)obj.getLoaiSanPhamRs(); %>
<% ResultSet dvkdRs = (ResultSet)obj.getDvkdRs(); %>
<% ResultSet maspRs = (ResultSet)obj.getMaSanPhamRs(); %>
<% ResultSet spRs = (ResultSet)obj.getSanPhamRs(); %>
<% ResultSet khoRs = (ResultSet)obj.getKhoRs(); %>
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
</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpReportFromStoreProcSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="khoTen" id="khoTen" value="" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; Quản lý cung ứng > Báo cáo > Xuất nhập tồn TT
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
                        <TD class="plainlabel" valign="middle" >
                            <select name="khoId" id="khoId" onchange="locsanpham()">
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
 
	if(lspRs !=null){
		lspRs.close();
		}
	if(dvkdRs !=null){
		dvkdRs.close();
		}
	if(maspRs !=null){
		maspRs.close();
		}
	if(spRs !=null){
		spRs.close();
		}
	session.setAttribute("obj", null)  ; 
}catch(Exception er){
	
}
finally{
	
}
%>