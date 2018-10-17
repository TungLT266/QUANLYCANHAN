<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% 	Utility util = (Utility) session.getAttribute("util"); %>
<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen"); %>
<%	String sum = (String) session.getAttribute("sum"); 
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	

	ILapngansach lnsBean = (ILapngansach)session.getAttribute("lnsBean"); %>
<% 	ResultSet namList = (ResultSet) lnsBean.getNamlist(); %>
<% 	ResultSet bpList = (ResultSet) lnsBean.getBplist(); %>
<% 	ResultSet tsList = (ResultSet) lnsBean.getTslist(); %>
<% 	ResultSet dvkd = (ResultSet) lnsBean.getDvkd();%>

<%  
	
	NumberFormat formatter = new DecimalFormat("#,###,###");   
	ResultSet nsList = (ResultSet) lnsBean.getNsList();%>

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
		 document.forms['lnsForm'].action.value='save';
	     document.forms['lnsForm'].submit();
	 }

	function submitform()
	 { 
		 document.forms['lnsForm'].action.value='new';
	     document.forms['lnsForm'].submit();
	 }

	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	 }

	 function Dinhdang(element)
	 {
	 
	 	element.value = DinhDangTien(element.value);
	 	if(element.value== '' )
	 	{
	 		element.value= '';
	 	}
	 	
	 }
	 
	 function Tinhtoan(tsId){
		 var dongia = document.getElementsByName("dongia" + tsId).item(0).value.replace(',', '').replace(',', '').replace(',', '').replace(',', '');
		 var soluong = document.getElementsByName("soluong" + tsId);
		 var tongsoluong = 0;
		 
		for(i = 1; i <= 12; i++){
			var thang = document.getElementsByName("T" + tsId + i);
		 	tongsoluong = tongsoluong + 1*(thang.item(0).value);
	 	}
		soluong.item(0).value = tongsoluong;  
 		document.getElementsByName("thanhtien" + tsId).item(0).value = (tongsoluong*dongia).toFixed(2);
 		
 		Dinhdang(document.getElementsByName("soluong" + tsId).item(0));
 		Dinhdang(document.getElementsByName("thanhtien" + tsId).item(0));
	 				 
	 }
	 
	 function Tinhtoan_New(n){
		 var dongia = document.getElementsByName("dongia_new" + n).item(0).value.replace(',', '').replace(',', '').replace(',', '').replace(',', '');
		 var soluong = document.getElementsByName("soluong_new" + n);
		 
		 var tongsoluong = 0;
		 
		for(i = 1; i <= 12; i++){
			var thang = document.getElementsByName("T_new" + n + i);
		 	tongsoluong = tongsoluong + 1*(thang.item(0).value);
	 	}
		soluong.item(0).value = tongsoluong;  
 		document.getElementsByName("thanhtien_new" + n).item(0).value = (tongsoluong*dongia).toFixed(2);
 		
 		Dinhdang(document.getElementsByName("soluong_new" + n).item(0));
 		Dinhdang(document.getElementsByName("thanhtien_new" + n).item(0));
	 }

</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="toBottom()">
<form name="lnsForm" method="post" action="../../LapngansachTSUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="lnsId" value='<%=lnsBean.getLnsId()  %>'>
<input type="hidden" name="ctyId" value='<%=lnsBean.getCtyId()  %>'>
<input type="hidden" name="action" value='1'>


<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
	
	<% if(lnsBean.getLoai().equals("1")){ %>
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">Quản lý ngân sách > Dự toán tài sản
    <%}else{ %>
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">Quản lý ngân sách > Dự toán công cụ dụng cụ
    <%} %>
    
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="">
		<%if(lnsBean.getView().equals("1")){ %>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border ="1px" style="border-style:outset"></A>
        </span>
        <%} %>
    </div>
  	
  	<div align="left" style="width:100%%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%%"><%= lnsBean.getMsg() %></textarea>
		         <% lnsBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    <% if(lnsBean.getLoai().equals("1")){ %>
    	<legend class="legendtitle">Thông tin dự toán mua tài sản</legend>
    <%}else{ %>
    	<legend class="legendtitle">Thông tin dự toán mua công cụ dụng cụ</legend>
    <%} %>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0" >
                  						
               <TR>
	               <TD class="plainlabel" width="15%">Kế hoạch ngân sách</TD>
	               <TD class="plainlabel" colspan = 3>
	                   <SELECT name = "nsId"  style="width: 300px;" onChange = "submitform();">
	                       	<OPTION value = "" >&nbsp;</OPTION>
		                        <%
		                    if(nsList != null){
								while(nsList.next()){
									if(nsList.getString("nsId").equals(lnsBean.getLnsId())){
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
                        <TD class="plainlabel" valign="middle" >Bộ phận</TD>
                        <TD class="plainlabel" valign="middle" >
                            <SELECT id="bpId"  name="bpId" onChange="submitform();">
                            		<OPTION value=""></option>
					<% 
						try
						{
							if(bpList != null){
								while(bpList.next()){
						  				if (lnsBean.getBpId().equals(bpList.getString("bpId"))){ %>
									<OPTION value = <%= bpList.getString("bpId")%> selected><%= bpList.getString("bpTen")%> </OPTION>
								<%  	}else{ %>
							  	  	<OPTION value = <%= bpList.getString("bpId")%> ><%= bpList.getString("bpTen")%></OPTION>
							  	 <% 	} 
							   	}
								
						  	}
						}catch(java.sql.SQLException e){}%>	  					
                            	
                            </SELECT>
                        </TD>                          
                        
                 </TR>         

	             <TR>
                        <TD class="plainlabel" valign="middle" >Loại</TD>
                        <TD class="plainlabel" valign="middle" >
                            <SELECT id="loai"  name="loai" onChange="submitform();">

					<% 
						if(lnsBean.getLoai().equals("1")){ %>
                           		<OPTION value="1" selected>Tài sản</option>
                           		<OPTION value="2" >Công cụ dụng cụ</option>
                    <%	}else{ %>
                       		<OPTION value="1">Tài sản</option>
                    		<OPTION value="2" selected>Công cụ dụng cụ</option>
                    <%	} %>								                            	
                            </SELECT>
                        </TD>                          
                        
                 </TR>         

            </TABLE>
            </div>          
     </fieldset>
    </div>
    <div align="left" style="width:140%; float:none; clear:left">
	 <fieldset>
	
	<% if(lnsBean.getLoai().equals("1")){ %>
		<legend class="legendtitle">Dự toán mua tài sản &nbsp;&nbsp;&nbsp;&nbsp;
	<%}else{ %>
		<legend class="legendtitle">Dự toán mua công cụ dụng cụ &nbsp;&nbsp;&nbsp;&nbsp;
	<%} %>
	
		</legend>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD align="left" colspan="4">
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">

							<TR class="tbheader">
								<TH width="15%" align="center">Hạng mục</th>
								<TH width="7%" align="center">Thành tiền</th>
								<TH width="7%" align="center">Đơn giá</th>
								<TH width="3%" align="center">Số lượng</th>
								<TH width="5%" align="center">T.1</th>
								<TH width="5%" align="center">T.2</th>
								<TH width="5%" align="center">T.3</th>
								<TH width="5%" align="center">T.4</th>
								<TH width="5%" align="center">T.5</th>
								<TH width="5%" align="center">T.6</th>
								<TH width="5%" align="center">T.7</th>
								<TH width="5%" align="center">T.8</th>
								<TH width="5%" align="center">T.9</th>
								<TH width="5%" align="center">T.10</th>
								<TH width="5%" align="center">T.11</th>
								<TH width="5%" align="center">T.12</th>
							</TR>

			<% 	
				int m = 0;
				if(tsList != null){ 
					try{			

						while(tsList.next()){
             					if((m % 2 ) == 0) {%>
							<TR class='tbdarkrow'>
								<%}else{ %>							
							<TR class='tblightrow'>
								<%} %>
													
								<TD align="left">
									<INPUT TYPE = "text" NAME = '<%="diengiai" + tsList.getString("TSID") %>' ID = "diengiai" VALUE = "<%= tsList.getString("DIENGIAI") %>" maxlength="200" style="width: 100%; text-align:right;" >
									<INPUT TYPE = "hidden" NAME = "tsId" VALUE = "<%=tsList.getString("TSID") %>" > 
								</TD>
								
								<TD align="center">	
								<INPUT TYPE = "text" NAME = '<%="thanhtien" + tsList.getString("TSID") %>' ID = "thanhtien" VALUE = "<%= formatter.format(Double.parseDouble(tsList.getString("THANHTIEN"))) %>" maxlength="15" style="width: 100%; text-align:right;" readonly="readonly"> 
								</TD>
														
								<TD align="center">
								<INPUT TYPE = "text" NAME = '<%="dongia" + tsList.getString("TSID") %>' ID="dongia" VALUE = "<%= formatter.format(Double.parseDouble(tsList.getString("DONGIA"))) %>" maxlength="15" onkeyup="Dinhdang(this)" style="width: 100%; text-align:right;" onkeypress="return isNumberKey2(event)" onChange = "Tinhtoan(<%= tsList.getString("TSID")%>);">
								</TD>
								
								<TD align="center">
								<INPUT TYPE = "text" NAME = '<%="soluong" + tsList.getString("TSID") %>' ID = "soluong" VALUE = "<%= Float.parseFloat(tsList.getString("SOLUONG")) %>" maxlength="15" onkeyup="Dinhdang(this)" style="width: 100%; text-align:right;" onkeypress="return isNumberKey2(event)" readonly=readonly>
								</TD>

							
							<% for(int i = 1; i <= 12; i++){ %>
								<TD align = "center">
									<INPUT TYPE = "text" NAME ='<%= "T" + tsList.getString("TSID") + i %>' ID ='<%= "T" + i %>' VALUE = "<%= formatter.format(Long.parseLong(tsList.getString("T" + i ))) %>" maxlength="18" onkeyup="Dinhdang(this)" style="width: 100%; text-align:right;" onkeypress="return isNumberKey2(event)" onChange = "Tinhtoan(<%= tsList.getString("TSID")%>);">									
								</TD>	
							
				<% 			  }%>
								
							</TR>	
				<%			m++;
						}
																	
					}catch(java.sql.SQLException e){}
				}				
				
			if(lnsBean.getBpId().length() > 0){	
				for(int n = 1; n <10; n++){
     					if((m %2 ) == 0) {%>
						<TR class='tbdarkrow'>
							<%}else{ %>							
						<TR class='tblightrow'>
							<%} %>
												
							<TD align="left">
							<INPUT TYPE = "text" NAME = '<%= "diengiai_new" + n %>' ID = '<%= "diengiai_new" + n %>' VALUE = "" maxlength="200" style="width: 100%; text-align:right;" > 
 
							</TD>
							
							<TD align="center">	
							<INPUT TYPE = "text" NAME = '<%="thanhtien_new" + n %>' ID = "thanhtien_new" VALUE = "" maxlength="15" style="width: 100%; text-align:right;" readonly="readonly"> 
							</TD>
													
							<TD align="center">
							<INPUT TYPE = "text" NAME = '<%="dongia_new" + n %>' ID="dongia_new" VALUE = "" maxlength="15" onkeyup="Dinhdang(this)" style="width: 100%; text-align:right;" onkeypress="return isNumberKey2(event)" onChange = "Tinhtoan_New(<%= n %>);">
							</TD>
							
							<TD align="center">
							<INPUT TYPE = "text" NAME = '<%="soluong_new" + n %>' ID = "soluong" VALUE = "" maxlength="15" onkeyup="Dinhdang(this)" style="width: 100%; text-align:right;" onkeypress="return isNumberKey2(event)" readonly = readonly>
							</TD>

						
						<% for(int i = 1; i <= 12; i++){ %>
							<TD align = "center">
								<INPUT TYPE = "text" NAME ='<%= "T_new" + n + i %>' ID ='<%= "T" + i %>' VALUE = "" maxlength="18" onkeyup="Dinhdang(this)" style="width: 100%; text-align:right;" onkeypress="return isNumberKey2(event)" onChange = "Tinhtoan_New(<%= n %>);">									
							</TD>	
						
			<% 			  }%>
							
						</TR>	
			<%} 
			
		}%>	
						</TABLE>
					</TD>
				</TR>
			</TABLE>
     	
    </div>
</div>

</form>

</BODY>

</html>
<% 	
	if(bpList != null) bpList.close();
	if(tsList != null) tsList.close(); 
	if(namList != null) namList.close(); 
	if(dvkd != null) dvkd.close(); 
	if(nsList != null) nsList.close(); 
	lnsBean.closeDB(); 
	session.setAttribute("lnsBean", null) ; 
} %>
