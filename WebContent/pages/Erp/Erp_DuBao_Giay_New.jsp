<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.erp.beans.dubaokinhdoanh.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% 	IDubaokinhdoanh_Giay dbkdBean = (IDubaokinhdoanh_Giay)session.getAttribute("dbkdBean"); %>
<% 	ResultSet khoList = (ResultSet) dbkdBean.getKhoList(); %>
<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen");  
	
%>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/maskedinput.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">

#diengiai {

    width: 300px;
}
#tab tr:HOVER{
cursor:pointer;
background: #E2F0D9;
}
#tabc tr input:HOVER{
cursor:pointer;
background: #E2F0D9;
}
</style>


<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
	$(document).ready(function() {
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});
	


	function Upload()
	{
		var ngaydubao = document.getElementById("danhsachsp");
		var capnhapbut = document.getElementById("capnhapbut");
		ngaydubao.style.display='none';
		capnhapbut.style.display='none';
	}

	var flag=false;
	function isNumberKey(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode!='46')
	      return false;
	
	   return true;
	}
	function isNumberKey2(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) )
	      return false;
	
	   return true;
	}
	
	 function saveform()
	 { 
		 var diengiai=document.getElementById("diengiai");

		 if(diengiai.value=="")
			{
				alert('Vui long nhap dien giai');
				return;
			 }

		 document.forms['dbkdForm'].action.value='new';
	     document.forms['dbkdForm'].submit();
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
	 

	 function toBottom()
	 {
		 window.scrollTo(0, document.body.scrollTop );
	 }
	 
	 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="toBottom()">
<form name="dbkdForm" method="post" action="../../DuBao_Giay_UpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>


<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Dự báo kinh doanh > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="">
    	<A href= "../../DuBao_GiaySvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border ="1px" style="border-style:outset"></A>
        </span>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%%"><%= dbkdBean.getMsg() %></textarea>
		         <% dbkdBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Thông tin dự báo kinh doanh</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">
		
                <TR>
                    <TD width="20%"  class="plainlabel" valign="middle">Diễn giải<FONT class="erroralert">*</FONT></TD>
                    <TD width="80%"  class="plainlabel" >
                    	<input type="text"  id="diengiai" autocomplete="off" name="diengiai" value="<%= dbkdBean.getDiengiai() %>" maxlength="200" width="300px" /></TD>
                </TR>  
 
                <TR>
                   <TD width="20%"  class="plainlabel" valign="middle">Ngày tạo dự báo<FONT class="erroralert">*</FONT></TD>
                   <TD width="80%"  align="left" class="plainlabel" >
                   	<input type="text"   autocomplete="off" id="ngaydubao" name="ngaydubao" value="<%= dbkdBean.getNgaydubao() %>" 
                   		maxlength="10" readonly=readonly></TD>
                </TR>  
                    
				  	
            </TABLE>
            </div>          
     </fieldset>	
    </div>
</div>

</form>

</BODY>

</html>
<% 
//dropdowncontent.init('spId', "right-top", 300, "click");
	if(khoList != null) khoList.close();

	dbkdBean.close(); 
	session.setAttribute("dbkdBean", null) ; 
	%>
