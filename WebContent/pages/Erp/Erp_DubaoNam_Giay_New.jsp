<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% 	IDubaokinhdoanhNam_Giay dbkdBean = (IDubaokinhdoanhNam_Giay)session.getAttribute("dbkdBean"); %>
<% 	ResultSet dvkd = dbkdBean.getDvkdRs(); %>
<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen");  
	ResultSet nsList = dbkdBean.getNsList();
	ResultSet kbhRs = dbkdBean.getKenhRS();
%>
<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>    
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
		 
		 /* var nam = document.getElementById("nam");
		 if(nam.value=="")
			 {
			 	alert('Vui long chon so thang du bao');
			 return;
			 } */
		 
		 var dvkdId = document.getElementById("dvkdId");
		 if(dvkdId.value=="")
		 {
		 	alert('Vui long chon don vi kinh doanh');
		 	return;
		 }
		 document.forms['nsdbForm'].action.value='new';
	     document.forms['nsdbForm'].submit();
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
<form name="nsdbForm" method="post" action="../../DubaoNam_Giay_UpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>


<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý ngân sách > Dự báo kinh doanh > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="">
    	<A href= "../../DubaoNam_GiaySvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border ="1px" style="border-style:outset"></A>
        </span>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="3" readonly="readonly" style ="width:100%%"><%= dbkdBean.getMsg() %></textarea>
		         <% dbkdBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Dự báo kinh doanh</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="6" cellspacing="0">
               <TR>
	               <TD class="plainlabel" width="15%">Kế hoạch ngân sách</TD>
	               <TD class="plainlabel">
	                   <SELECT name = "nsId"  style="width: 300px;" onChange = "submitform();">
	                       	<OPTION value = "" >&nbsp;</OPTION>
		                        <%
		                    if(nsList != null){
								while(nsList.next()){
									if(nsList.getString("nsId").equals(dbkdBean.getNsId())){
		                        		%>
			                      		<OPTION value = <%= nsList.getString("nsId")  %> selected> <%= nsList.getString("diengiai") %></OPTION>
			                        	
			                        	<%}else{ 	 %>
		                        			
		                        		<OPTION value = <%= nsList.getString("nsId")  %> > <%= nsList.getString("diengiai") %></OPTION>		                        			
		                        		<%	
		                        		}
		                       		}
		                       }%>
	                  	</SELECT>
		                       	
	               </TD>
                </TR>	
                  						
                <TR>
                    <TD width="20%"  class="plainlabel" valign="middle">Diễn giải<FONT class="erroralert">*</FONT></TD>
                    <TD width="80%"  class="plainlabel" >
                    	<input type="text"  id="diengiai" autocomplete="off" name="diengiai" value="<%= dbkdBean.getDiengiai() %>" maxlength="200" width="300px" /></TD>
                </TR>  
                    
                <TR>
                        <TD class="plainlabel" valign="middle" >Năm dự báo</TD>
                        <TD class="plainlabel" valign="middle">
                            <select id="nam"  name="nam" onChange="saveform();">
                            	<option value=""></option>
                        <% 
                            int n = Integer.parseInt(dbkdBean.getDateTime().substring(0, 4));
                            for (int nam = n; nam < n+ 2; nam++){
                            	if (dbkdBean.getNam() == nam){%>
								  	<option value = "<%= nam %>" selected><%= nam %></option>
						<%		}else{ %>	
									<option value = "<%= nam %>" ><%= nam %></option>
						<%		}
                            }%>
                            </select>
                        </TD>                          
                    </TR>         

                     <TR >
                        <TD class="plainlabel" valign="middle">Đơn vị kinh doanh </TD>
                        <TD  valign="middle" class="plainlabel">
                           
                           <select name="dvkdId" id="dvkdId" >
                           <option value=""></option>		
							<% while(dvkd.next()){ 
								
								if (dbkdBean.getDvkdId().equals(dvkd.getString("dvkdId"))){%>
								  	<option value=<%= dvkd.getString("dvkdId") %> selected><%= dvkd.getString("dvkd") %></option>
							<%	}else{ %>
								  	<option value=<%= dvkd.getString("dvkdId") %> ><%= dvkd.getString("dvkd") %></option>
							<%	} 
							 }
								%>
                           </select>
                        </TD>                        
                    </TR>    

                     <TR >
                        <TD class="plainlabel" valign="middle">Kênh bán hàng </TD>
                        <TD  valign="middle" class="plainlabel">
                           
                           <select name="kbhId" id="kbhId" >
                           <option value=""></option>		
							<% while(kbhRs.next()){ 
								
								if (dbkdBean.getKenhId().equals(kbhRs.getString("kbhId"))){%>
								  	<option value=<%= kbhRs.getString("kbhId") %> selected><%= kbhRs.getString("kbh") %></option>
							<%	}else{ %>
								  	<option value=<%= kbhRs.getString("kbhId") %> ><%= kbhRs.getString("kbh") %></option>
							<%	} 
							 }
								%>
                           </select>
                        </TD>                        
                    </TR>    

				    <TR style="display: none;" >
				  	  	<TD class="plainlabel">Lấy dữ liệu từ tập tin Excel</TD>
				  	  	<TD class="plainlabel">
				  	  		<INPUT onchange="Upload()" type="file" name="filename" id="filename"  size="40" value=''>
				  	  	</TD>
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
try{
	session.setAttribute("dbkdBean", null) ; 
	if (dvkd != null)
		dvkd.close(); 
	if (nsList != null)
		nsList.close(); 
	dbkdBean.close(); 
}catch (Exception ex)
{
	ex.printStackTrace();
}%>
