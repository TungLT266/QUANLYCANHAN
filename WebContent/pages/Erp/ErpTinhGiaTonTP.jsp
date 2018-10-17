<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.khoasothang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>

<% IErpTinhgiatonTP obj = (IErpTinhgiatonTP)session.getAttribute("obj"); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% 
String userTen = (String) session.getAttribute("userTen"); 
ResultSet giavonRs = obj.getGiavonRs(); 
ResultSet giavonCTRs = obj.getGiavonCTRs();

NumberFormat formatter = new DecimalFormat("#,###,###");
NumberFormat formatter3 = new DecimalFormat("#,###,###.###");

String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
}else{	

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>TraphacoHYERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>

	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
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
    </script>
    
    <LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
    <script>
	//perform JavaScript after the document is scriptable.
	$(document).ready(function() {
	
	    //When page loads...
	    $(".tab_content").hide(); //Hide all content
	    var index = $("ul.tabs li.current").show().index(); 
	    $(".tab_content").eq(0).show();
	
	    //On Click Event
	    $("ul.tabs li").click(function() {
	    	
	        $("ul.tabs li").removeClass("current"); //Remove any "active" class
	        $(this).addClass("current"); //Add "active" class to selected tab
	        $(".tab_content").hide(); //Hide all tab content  
	        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
	        $(activeTab).show(); //Fade in the active ID content
	        return false;
	    });
	
	});
	</script>

    <SCRIPT language="javascript" type="text/javascript">
	 function tinhgiavon()
	 {   
		 document.forms["erpCngn"].action.value = 'tinhgiavon';
	     document.forms["erpCngn"].submit();
	 }
	 
	 function submitform()
	 {   
		 document.forms["erpCngn"].action.value = 'submit';
	     document.forms["erpCngn"].submit();
	 }
	
	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	</SCRIPT>
</head>
<body>
<form name="erpCngn" method="post" action="../../ErpTinhgiatonTPSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
             Quản lý tồn kho > Chức năng > Tính giá tồn kho TP
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
    <div align="left" style="width: 100%; float: none; clear: left">
		<fieldset>
			<legend class="legendtitle"> Thông báo</legend>
			<textarea id="errors" name="errors" rows="2"  style="width: 100%;text-align: left; color:#F00 ; font-weight:bold">
				<%= obj.getMsg() %> </textarea>
		</fieldset>
	</div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle">Tính giá tồn kho</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px">								                          
                    <TR>
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days"  id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <tr>
                        <td colspan="2" class="plainlabel">
                            <a class="button" href="javascript:tinhgiavon()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Tính giá tồn</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Xem giá tồn</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>   
                    <% if( giavonRs != null ) { %>
                    <tr>
                    	<td colspan="2" class="plainlabel">
                    	
                    		<ul class="tabs">    			
                  				<li class="current" ><a href="#Giavon" >Giá vốn</a></li>
	                  			<li><a href="#Theokho">Theo kho</a></li>
			                </ul>
                    	
                    		<div class="panes">
                    	
                    		<div id='Giavon' class ="tab_content" >
                    		
	                    		<TABLE border="0" cellpadding="4"  cellspacing="2" width="100%">
									<TR class="tbheader" >
										<TH width="5%" align = "center" >STT </TH>
										<TH width="10%" align = "center" >Mã SP </TH>															
										<TH width="30%" align = "center" >Tên sản phẩm</TH>
										<TH width="10%" align = "center" >Đơn vị</TH>
										<TH width="10%" align = "center" >Tồn cuối </TH>
										<TH width="10%" align = "center" >Giá vốn</TH>
										<TH width="15%" align = "center" >Tổng giá trị</TH>
									</TR>
									
									<% 
										String lightrow = "tblightrow";
										String darkrow = "tbdarkrow";
										if( giavonRs != null ) { 
											int i = 0;
											String _class = "";
											while( giavonRs.next() ) { 
											
											if( i % 2 == 0 )
												_class = " class = '" + lightrow + "' ";
											else
												_class = " class = '" + darkrow + "' ";
											%> 
											
											<Tr <%= _class %> >
												<td><%= i %></td>
												<td><%= giavonRs.getString("ma") %></td>
												<td><%= giavonRs.getString("ten") %></td>
												<td><%= giavonRs.getString("donvi") %></td>
												<td style="text-align: right;" ><%= formatter3.format( giavonRs.getDouble("soluong") ) %></td>
												<td style="text-align: right;"><%= formatter3.format( giavonRs.getDouble("dongia") ) %></td>
												<td style="text-align: right;" ><%= formatter.format( giavonRs.getDouble("soluong") * giavonRs.getDouble("dongia") ) %></td>
											</Tr>
										
										<% i++; } giavonRs.close();  } %>
									
								</TABLE>
                    		
                    		</div>
                    		
                    		<div id='Theokho' class ="tab_content" >
                    		
	                    		<TABLE border="0" cellpadding="4"  cellspacing="2" width="100%">
									<TR class="tbheader" >
										<TH width="3%" align = "center" >STT </TH>
										<TH width="22%" align = "center" >Kho </TH>	
										<TH width="10%" align = "center" >Mã SP </TH>																
										<TH width="25%" align = "center" >Tên sản phẩm</TH>
										<TH width="10%" align = "center" >Tồn cuối </TH>
										<TH width="10%" align = "center" >G1</TH>
										<TH width="10%" align = "center" >G2</TH>
										<TH width="10%" align = "center" >Chênh lệch</TH>
									</TR>
									
									<% 
										lightrow = "tblightrow";
										darkrow = "tbdarkrow";
										if( giavonCTRs != null ) { 
											int i = 0;
											String _class = "";
											while( giavonCTRs.next() ) { 
											
											if( i % 2 == 0 )
												_class = " class = '" + lightrow + "' ";
											else
												_class = " class = '" + darkrow + "' ";
											%> 
											
											<Tr <%= _class %> >
												<td><%= i %></td>
												<td><%= giavonCTRs.getString("kho") %></td>
												<td><%= giavonCTRs.getString("ma") %></td>
												<td><%= giavonCTRs.getString("ten") %></td>
												<td style="text-align: right;" ><%= formatter3.format( giavonCTRs.getDouble("toncuoi") ) %></td>
												<td style="text-align: right;" ><%= formatter3.format( giavonCTRs.getDouble("G1") ) %></td>
												<td style="text-align: right;"><%= formatter3.format( giavonCTRs.getDouble("G2") ) %></td>
												<td style="text-align: right;" ><%= formatter.format( giavonCTRs.getDouble("chenhlech") ) %></td>
											</Tr>
										
										<% i++; } giavonCTRs.close();  } %>
									
								</TABLE>
                    		
                    		</div>
                    		
                    		</div>
                    		
                    	</td>
                    </tr>     					
                    <% } %>
                </TABLE>                      
        </fieldset>                      
    	</div>
        
    </div>  
</div>
</form>
<%

try{
	session.setAttribute("obj",null);	
	 obj.DbClose();
	
}catch(Exception er){
	
}
} %>
</body>
</html>
