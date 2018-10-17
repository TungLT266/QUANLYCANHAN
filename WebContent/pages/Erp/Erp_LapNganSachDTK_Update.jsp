<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen"); 

	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	

%>
<%  NumberFormat formatter = new DecimalFormat("#,###,###"); %>    
<% 	ILapngansach lnsBean = (ILapngansach)session.getAttribute("lnsBean"); %>
<% 	ResultSet bpList = (ResultSet) lnsBean.getBplist(); %>
<% 	ResultSet ndtList = (ResultSet) lnsBean.getNdtlist(); %>
<% 	ResultSet dtList = (ResultSet) lnsBean.getDtlist(); 

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
	 
	 function Tinhtoan(cpId, flag){
		 var namcu = document.getElementsByName("namcu" + cpId).item(0).value.replace(',', '').replace(',', '').replace(',', '').replace(',', '');
		 if(flag == 0){
		 	for(i = 1; i <= 12; i++){
				var thang = document.getElementsByName("T" + cpId + i);
			 	thang.item(0).value = 0;
		 	}

		 	var dutoan = document.getElementsByName("nammoi" + cpId).item(0).value.replace(',', '').replace(',', '').replace(',', '').replace(',', '');
		 	if(namcu > 0){
		 		document.getElementsByName("phantram" + cpId).item(0).value = (100*dutoan/namcu).toFixed(2);
		 	}else{
		 		document.getElementsByName("phantram" + cpId).item(0).value = 0;
		 	}
		 	var phanbo = document.getElementsByName("phanbo" + cpId).item(0).value;
		 
		 	if(phanbo == 1){
				var quy = dutoan / 4;
				document.getElementsByName("T" + cpId + "1").item(0).value = quy;				
				Dinhdang(document.getElementsByName("T" + cpId + "1").item(0));

				document.getElementsByName("T" + cpId + "4").item(0).value = quy;
				Dinhdang(document.getElementsByName("T" + cpId + "4").item(0));
				
				document.getElementsByName("T" + cpId + "7").item(0).value = quy;
				Dinhdang(document.getElementsByName("T" + cpId + "7").item(0));
				
				document.getElementsByName("T" + cpId + "10").item(0).value = quy;
				Dinhdang(document.getElementsByName("T" + cpId + "10").item(0));
			}
		 	
		 	if(phanbo == 2){
				var t = dutoan / 12;
				for(i = 1; i <= 12; i++){
					var thang = document.getElementsByName("T" + cpId + i);
				 	thang.item(0).value = t;
				 	Dinhdang(thang.item(0));
			 	}			 	
			}		
	 	}else{
		 	var dutoan = document.getElementsByName("nammoi" + cpId);
		 	var phanbo = document.getElementsByName("phanbo" + cpId).item(0).value;
		 	var tong = 0;
		 	
		 	if(phanbo == 1){				
				tong = tong + document.getElementsByName("T" + cpId + "1").item(0).value.replace(',', '').replace(',', '').replace(',', '').replace(',', '');
				tong = tong + document.getElementsByName("T" + cpId + "4").item(0).value.replace(',', '').replace(',', '').replace(',', '').replace(',', '');
				tong = tong + document.getElementsByName("T" + cpId + "7").item(0).value.replace(',', '').replace(',', '').replace(',', '').replace(',', '');
				tong = tong + document.getElementsByName("T" + cpId + "10").item(0).value.replace(',', '').replace(',', '').replace(',', '').replace(',', '');
			}else{
				for(i = 1; i <= 12; i++){
					var thang = document.getElementsByName("T" + cpId + i);
				 	tong = tong + thang.item(0).value;
			 	}
			 	
			}
		 	dutoan.item(0).value = tong;
		 	if(namcu > 0){
		 		document.getElementsByName("phantram" + cpId).item(0).value = (100*tong/namcu).toFixed(2);
		 	}else{
		 		document.getElementsByName("phantram" + cpId).item(0).value = 0;
		 	}
		 	Dinhdang(dutoan.item(0));
	 		
	 	}
		 
	 }
	 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="toBottom()">
<form name="lnsForm" method="post" action="../../LapngansachDTKUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="lnsId" value='<%=lnsBean.getLnsId()  %>'>
<input type="hidden" name="action" value='1'>


<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">Quản lý ngân sách > Lập ngân sách > Dự toán doanh thu khác > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	
  	<%if(lnsBean.getView().equals("1")){ %>
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="">
		
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border ="1px" style="border-style:outset"></A>
        </span>
        
    </div>
  	<%} %>
  	<div align="left" style="width:100%%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%%"><%= lnsBean.getMsg() %></textarea>
		         <% lnsBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Thông tin dự toán doanh thu khác</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0" >
               <TR>
	               <TD class="plainlabel" width="15%">Kế hoạch ngân sách</TD>
	               <TD class="plainlabel"  >
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
                            <SELECT id="bpId"  name="bpId" style="width: 300px;" onChange="submitform();">
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

            </TABLE>
            </div>          
     </fieldset>
    </div>
    <div align="left" style="width:140%; float:none; clear:left">
	 <fieldset>
		<legend class="legendtitle">Dự toán doanh thu khác &nbsp;&nbsp;&nbsp;&nbsp;
		</legend>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD align="left" colspan="4">
		<% if(lnsBean.getView().equals("1")){ %>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">

							<TR class="tbheader">
								<TH width="15%" align="center">Hạng mục</th>
								<TH width="7%" align="center">Năm cũ</th>
								<TH width="7%" align="center">Năm mới</th>
								<TH width="3%" align="center">%</th>
								<TH width="3%" align="center">Phân bổ</th>
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
			<% 	if(dtList != null){ 
					try{			
						int m = 0;
						while(dtList.next()){
             					if((m % 2 ) == 0) {%>
							<TR class='tbdarkrow'>
								<%}else{ %>							
							<TR class='tblightrow'>
								<%} %>
													
								<TD align="left"><%= dtList.getString("TEN") %>
									<INPUT TYPE = "hidden" NAME = "dtId" VALUE = "<%=dtList.getString("DTID") %>" > 
								</TD>
								
								<TD align="center">	
								<INPUT TYPE = "text" NAME = '<%="namcu" + dtList.getString("DTID") %>' ID = "namcu" VALUE = "<%= formatter.format(Double.parseDouble(dtList.getString("NAMCU"))) %>" maxlength="15" style="width: 100%; text-align:right;" readonly="readonly"> 
								</TD>
														
								<TD align="center">
								<INPUT TYPE = "text" NAME = '<%="nammoi" + dtList.getString("DTID") %>' ID="nammoi" VALUE = "<%= formatter.format(Double.parseDouble(dtList.getString("NAMMOI"))) %>" maxlength="15" onkeyup="Dinhdang(this)" style="width: 100%; text-align:right;" onkeypress="return isNumberKey2(event)" onChange = "Tinhtoan(<%= dtList.getString("DTID")%>, 0);">
								</TD>
								
								<TD align="center">
								<INPUT TYPE = "text" NAME = '<%="phantram" + dtList.getString("DTID") %>' ID = "phantram" VALUE = "<%= Float.parseFloat(dtList.getString("PHANTRAM")) %>" maxlength="15" onkeyup="Dinhdang(this)" style="width: 100%; text-align:right;" readonly="readonly">
								</TD>

								<TD align="center">
									<SELECT NAME = '<%="phanbo" + dtList.getString("DTID") %>' style="width: 100px" onChange = "Tinhtoan(<%= dtList.getString("DTID")%>, 0);">
										<OPTION VALUE = '0'>&nbsp;</OPTION>

								<% if(dtList.getString("PHANBO").equals("1")){ %>		
										<OPTION VALUE = '1' selected>Theo quý</OPTION>
										<OPTION VALUE = '2'>Theo tháng</OPTION>
								<%}else if (dtList.getString("PHANBO").equals("2")){ %>
										<OPTION VALUE = '1'>Theo quý</OPTION>
										<OPTION VALUE = '2' selected>Theo tháng</OPTION>
								<%} %>								
								
									</SELECT>
								</TD>
								
							<% for(int i = 1; i <= 12; i++){ %>
								<TD align = "center">
									<INPUT TYPE = "text" NAME ='<%= "T" + dtList.getString("DTID") + i %>' ID ='<%= "T" + i %>' VALUE = "<%= formatter.format(Long.parseLong(dtList.getString("T" + i ))) %>" maxlength="18" onkeyup="Dinhdang(this)" style="width: 100%; text-align:right;" onkeypress="return isNumberKey2(event)" onChange = "Tinhtoan(<%= dtList.getString("DTID")%>, 1);">									
								</TD>	
							
				<% 			  }%>
								
							</TR>	
				<%			m++;
						}
						
					}catch(java.sql.SQLException e){}
				}%>
					</TABLE>
		<%}else{%>
					<TABLE width="70%" border="0" cellspacing="1" cellpadding="4">
						<TR class="tbheader">
							<TH width="40%" align="center">Hạng mục</th>
							<TH width="20%" align="center">Năm cũ</th>
							<TH width="20%" align="center">Năm mới</th>
							<TH width="20%" align="center">%</th>
						</TR>
			<% 	if(dtList != null){ 
					try{			
						int m = 0;
						double namcu = 0;
						double nammoi = 0;
						double phantram;
						while(dtList.next()){
             					if((m % 2 ) == 0) {%>
							<TR class='tbdarkrow'>
								<%}else{ %>							
							<TR class='tblightrow'>
								<%} %>
													
								<TD align="left"><%= dtList.getString("TEN") %>
									 
								</TD>
								
								<TD align="center"><%= formatter.format(Double.parseDouble(dtList.getString("NAMCU"))) %>	
								<% namcu = namcu +  Double.parseDouble(dtList.getString("NAMCU")); %> 
								</TD>
														
								<TD align="center"><%= formatter.format(Double.parseDouble(dtList.getString("NAMMOI")))%>
								<% nammoi = nammoi +  Double.parseDouble(dtList.getString("NAMMOI")); %>
								</TD>
								
								<TD align="center"><%= Float.parseFloat(dtList.getString("PHANTRAM")) %>
								</TD>
					<%				
							m++; %>
								</TR>
					<%	}
						String tmp = ""; 
						if(namcu > 0) {
							tmp = "" + 100*nammoi/namcu;
							if(tmp.length() > 5){
								tmp.substring(0,5);
							}
						}
							%>	 
								<TR class="tbheader">
									<TD align="center">Tổng cộng</TD>
									<TD align="center"><%= formatter.format(namcu) %></TD>
									<TD align="center"><%= formatter.format(nammoi) %></TD>
									<TD align="center"><%= tmp %></TD>
								</TR>
				<%		
						
					}catch(java.sql.SQLException e){}
				}
		} %>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
   </fieldset>
</div>

</form>

</BODY>

</html>
<% 
//dropdowncontent.init('spId', "right-top", 300, "click");

	if(bpList != null) bpList.close();
	if(ndtList != null) ndtList.close();
	if( nsList != null) nsList.close();
	if( dtList != null) dtList.close();
	
	lnsBean.closeDB();  
	session.setAttribute("lnsBean", null) ; 
	
	}
	%>
