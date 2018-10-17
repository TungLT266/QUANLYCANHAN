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
<%  NumberFormat formatter_2 = new DecimalFormat("#,###,###.##"); %>    
<% 	ILapngansach lnsBean = (ILapngansach)session.getAttribute("lnsBean"); %>
<% 	ResultSet bpList = (ResultSet) lnsBean.getBplist(); %>
<% //	ResultSet ncpList = (ResultSet) lnsBean.getNcplist(); %>
<% 	ResultSet cpList = (ResultSet) lnsBean.getCplist(); 

	ResultSet nsList = (ResultSet) lnsBean.getNsList();
	String[] DB_Thang = lnsBean.getDubaoThang();
	
	%>

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
		 var tdt = document.getElementsByName("tdt").item(0).value.replace(/,/g,"") ;
		 var giatri = document.getElementsByName("giatri" + cpId).item(0).value.replace(/,/g,"") ;
		 var phantram = document.getElementsByName("phantram" + cpId).item(0).value.replace(/,/g,"") ;

		 var dutoan = 0;

		 if(flag == 0){  // nhập vào %
			var pt = document.getElementsByName("phantram" + cpId).item(0).value.replace(/,/g,"") ;						
			
		 	for(i = 1; i <= 12; i++){
				var thang = document.getElementsByName("T" + cpId + i);
				var DS_thang = document.getElementsByName("DST" + cpId + i).item(0).value.replace(/,/g,"") ;
				
				dutoan = dutoan + DS_thang*pt/100;
			 	thang.item(0).value = DS_thang*pt/100;
			 	
			 	Dinhdang(thang.item(0));
		 	}			 	

		 	document.getElementsByName("giatri" + cpId).item(0).value = dutoan;
		 	Dinhdang(document.getElementsByName("giatri" + cpId).item(0));
		 	
		 }else{ // nhập vào giá trị
			dutoan = giatri;
		 	
			var pt = 100*parseFloat(dutoan)/parseFloat(tdt);
			document.getElementsByName("phantram" + cpId).item(0).value = pt;
			 
		 	for(i = 1; i <= 12; i++){
				var thang = document.getElementsByName("T" + cpId + i);
				var DS_thang = document.getElementsByName("DST" + cpId + i).item(0).value.replace(/,/g,"") ;
					
			 	thang.item(0).value = parseFloat(DS_thang)*pt/100;
				 	
			 	Dinhdang(thang.item(0));
			}			 	

		 	document.getElementsByName("giatri" + cpId).item(0).value = dutoan;
		 	Dinhdang(document.getElementsByName("giatri" + cpId).item(0));

		 }
		 
		 
	 }
	 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" >
<form name="lnsForm" method="post" action="../../LapngansachCPUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="lnsId" value='<%=lnsBean.getLnsId()  %>'>
<input type="hidden" name="action" value='1'>
<INPUT type = "hidden" name = "tdt" value = "<%= lnsBean.getTongDoanhThu() %>" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">Quản lý ngân sách > Dự toán chi phí
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
    	<legend class="legendtitle">Thông tin dự toán chi phí</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0" >
               <TR>
	               <TD class="plainlabel" width="15%">Kế hoạch ngân sách</TD>
	               <TD class="plainlabel" colspan = 3  >
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
                	<TD class="plainlabel" valign="middle" >Tổng doanh thu</TD>
                	<TD class="plainlabel" valign="middle" colspan = 3 ><INPUT type = "text" name = "doanhthu" value = "<%= formatter.format(lnsBean.getTongDoanhThu()) %>" readonly > </TD>
                </TR>
             	<TR>
                        <TD class="plainlabel" valign="middle" >Bộ phận</TD>
                        <TD class="plainlabel" valign="middle" colspan = 3 >
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
		<legend class="legendtitle">Dự toán chi phí &nbsp;&nbsp;&nbsp;&nbsp;
		</legend>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD align="left" colspan="4">
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">

							<TR class="tbheader">
								<TH width="8%" align="center" rowspan = 2 >Hạng mục</th>
								<TH width="4%" align="center" rowspan = 2 >Ngân sách năm cũ</th>
								<TH width="8%" align="center" colspan = 2 >Ngân sách năm mới</th>
								<TH width="4%" align="center" rowspan = 2 >T.1</th>
								<TH width="4%" align="center" rowspan = 2 >T.2</th>
								<TH width="4%" align="center" rowspan = 2 >T.3</th>
								<TH width="4%" align="center" rowspan = 2 >T.4</th>
								<TH width="4%" align="center" rowspan = 2 >T.5</th>
								<TH width="4%" align="center" rowspan = 2 >T.6</th>
								<TH width="4%" align="center" rowspan = 2 >T.7</th>
								<TH width="4%" align="center" rowspan = 2 >T.8</th>
								<TH width="4%" align="center" rowspan = 2 >T.9</th>
								<TH width="4%" align="center" rowspan = 2 >T.10</th>
								<TH width="4%" align="center" rowspan = 2 >T.11</th>
								<TH width="4%" align="center" rowspan = 2 >T.12</th>
							</TR>
							<TR class = "tbheader" >
								<TH width="4%" align="center">% Doanh thu</th>
								<TH width="4%" align="center">Giá trị</th>
								
							</TR>
			<% 	if(cpList != null){ 
					try{			
						int m = 0;
						while(cpList.next()){
             					if((m % 2 ) == 0) {%>
							<TR class='tbdarkrow'>
								<%}else{ %>							
							<TR class='tblightrow'>
								<%} %>
													
								<TD align="left"><%= cpList.getString("TEN") %>
									<INPUT TYPE = "hidden" NAME = "cpId" VALUE = "<%=cpList.getString("CPID") %>" > 
								</TD>
								
								<TD align="center">	
								<INPUT TYPE = "text" NAME = '<%="namcu" + cpList.getString("CPID") %>' ID = "namcu" VALUE = "<%= formatter.format(Double.parseDouble(cpList.getString("NAMCU"))) %>" maxlength="15" style="width: 100%; text-align:right;" readonly="readonly"> 
								</TD>
														
								
								<TD align="center">
								<INPUT TYPE = "text" NAME = '<%="phantram" + cpList.getString("CPID") %>' ID = "phantram" VALUE = "<%= formatter_2.format(Float.parseFloat(cpList.getString("PHANTRAM"))) %>" maxlength="15"  style="width: 100%; text-align:right;" onkeypress="return isNumberKey(event)" onChange = "Tinhtoan(<%= cpList.getString("CPID")%>, 0);" >
								</TD>

								<TD align="center">
								<INPUT TYPE = "text" NAME = '<%="giatri" + cpList.getString("CPID") %>' ID="giatri" VALUE = "<%= formatter.format(Float.parseFloat(cpList.getString("PHANTRAM"))*lnsBean.getTongDoanhThu()/100) %>" maxlength="15" onkeyup="Dinhdang(this)" style="width: 100%; text-align:right;" onkeypress="return isNumberKey2(event)" onChange = "Tinhtoan(<%= cpList.getString("CPID")%>, 1);">
								</TD>

								
							<% for(int i = 1; i <= 12; i++){ 
								System.out.println("" + i + ": pt = " + Float.parseFloat(cpList.getString("PHANTRAM")) + "; DB: " + Float.parseFloat(DB_Thang[i - 1]) + "; KQ: " + Float.parseFloat(cpList.getString("PHANTRAM"))*Float.parseFloat(DB_Thang[i - 1])/100);
							%>
								<TD align = "center">
									<INPUT TYPE = "text" NAME ='<%= "T" + cpList.getString("CPID") + i %>' ID ='<%= "T" + i %>' VALUE = "<%= formatter.format(Math.round(Double.parseDouble(cpList.getString("PHANTRAM"))*Double.parseDouble(DB_Thang[i - 1])/100)) %>" maxlength="18" onkeyup="Dinhdang(this)" style="width: 100%; text-align:right;" readonly >
									<INPUT TYPE = "hidden" NAME ='<%= "DST" + cpList.getString("CPID") + i %>' ID ='<%= "DST" + i %>' VALUE = "<%= Double.parseDouble(DB_Thang[i - 1]) %>" maxlength="18" >									
																		
								</TD>	
							
				<% 			  }%>
								
							</TR>	
				<%			m++;
						}
						
					}catch(java.sql.SQLException e){}
				}%>
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
//	if(ncpList != null) ncpList.close();
	if( nsList != null) nsList.close();
	if( cpList != null) cpList.close();
	
	lnsBean.closeDB();  
	session.setAttribute("lnsBean", null) ; 
	
	}
	%>
