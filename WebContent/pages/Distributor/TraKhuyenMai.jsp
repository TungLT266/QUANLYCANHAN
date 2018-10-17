<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.quanlykhuyenmai.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% ITrakhuyenmaiNppList obj = (ITrakhuyenmaiNppList)session.getAttribute("obj"); %>
<% ResultSet tkmList = (ResultSet)obj.getTrakmList(); %>

<% 	String userId = (String) session.getAttribute("userId"); 
	Utility util = (Utility) session.getAttribute("util");
%>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings(); 

int[] quyen = new  int[6];
quyen = util.Getquyen("TrakhuyenmaiNppSvl","",userId);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
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
	    if(confirm("Ban co muon dang xuat?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	 function submitform()
	 {   
	    document.forms["trakmForm"].submit();
	 }
	
	 function clearform()
	 {   
	    document.forms["trakmForm"].diengiai.value = "";
	    document.forms["trakmForm"].tungay.value = "";
	    document.forms["trakmForm"].denngay.value = "";
	    document.forms["trakmForm"].submit();
	 }
	 
	</SCRIPT>
</head>
<body>
<form name="trakmForm" method="post" action="../../TrakhuyenmaiNppSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        Quản lý khuyến mãi > Sản phẩm trả khuyến mãi
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        Chào mừng  <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset>
            <legend class="legendtitle"> Tiêu chí tìm kiếm </legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px">								                 
                    <TR>
                        <TD class="plainlabel" valign="middle" width="15%">Mã CTKM / Diễn giải </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="diengiai" value="<%= obj.getScheme() %>">
                        </TD>                        
                    </TR>            
                    <TR>
                        <TD class="plainlabel" >Từ ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days"
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <tr style="background-color:#C5E8CD">
                        <td colspan="2">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại  </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Trả khuyến mãi</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH align="center">Mã CTKM</TH>
									<TH align="center" width="30%">Diễn giải CTKM</TH>
				                    <TH align="center">Mã trả KM</TH>
				                    <TH align="center" width="30%">Diễn giải TKM</TH>
				                    <TH align="center" >Tác vụ</TH>
				                </TR>
							<%      
		                            int m = 0;
				                if(tkmList !=null)
				                {
		                       		 while (tkmList.next()){
		                         
		                            if(m % 2 == 0){
		                   				 %> 
		                         		<TR class='tbdarkrow'>
		                         		<%}else{ %>
		                         		<TR class='tblightrow'>
		                        	 <%} %>
				                    <TD align="center">
				                    <% if(!tkmList.getString("dathietlap").equals("0")){ %>
				                    	<span style="color: red;font-style: italic;"><%= tkmList.getString("scheme") %></span> 
				                    <%}else{ %>
				                    	<%= tkmList.getString("scheme") %>
				                    <%} %>
				                    </TD>
				                    <TD align="left"><%= tkmList.getString("ctkmDiengiai") %></TD>
				                    <TD align="center"><%= tkmList.getString("tkmId") %></TD>										                                    
				                    <TD align="left"><%= tkmList.getString("diengiai") %></TD>			
				                    <TD align="center">
				                    
				                    	<%if(quyen[Utility.SUA]!=0){ %>
										 <A href = "../../TrakhuyenmaiNppUpdateSvl?userId=<%=userId%>&tkmId=<%= tkmList.getString("tkmId")%>&ctkmId=<%= tkmList.getString("ctkmId")%>"><IMG src="../images/Edit20.png" alt="Cap nhat" title="Cap nhat" border="0"></A>							
				                   		<%} %>
				                   		
				                    </TD>
				                </TR>
		                     <% m++; }}%>										
                                
								<tr class="tbfooter" > 
									 <TD align="center" valign="middle" colspan="13" class="tbfooter">
									 	<%if(obj.getNxtApprSplitting() >1) {%>
											<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('trakmForm', 1, 'view')"> &nbsp;
										<%}else {%>
											<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
											<%} %>
										<% if(obj.getNxtApprSplitting() > 1){ %>
											<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('trakmForm', eval(trakmForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
										<%}else{ %>
											<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
										<%} %>
										
										<%
											int[] listPage = obj.getNextSplittings();
											for(int i = 0; i < listPage.length; i++){
										%>
										
										<% 
										
										//System.out.println("Current page:" + obj.getNxtApprSplitting());
										//System.out.println("List page:" + listPage[i]);
										
										if(listPage[i] == obj.getNxtApprSplitting()){ %>
										
											<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
										<%}else{ %>
											<a href="javascript:View('trakmForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
										<%} %>
											<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
										<%} %>
										
										<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
											&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('trakmForm', eval(trakmForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
										<%}else{ %>
											&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
										<%} %>
										<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
									   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
								   		<%}else{ %>
								   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('trakmForm', -1, 'view')"> &nbsp;
								   		<%} %>
									</TD>
								 </tr>  
						</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
</body>
</html>
<%
try{
	if(tkmList!=null){
		tkmList.close();
	}
	if(obj!=null){
	obj.DBclose();
	obj=null;
	}
	session.setAttribute("obj",null);
	
}catch(Exception er){
	
}

%>
