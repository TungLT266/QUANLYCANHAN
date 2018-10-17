<%@page import="java.util.Calendar"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.khoasothang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% 	IErpKhoasothang obj = (IErpKhoasothang)session.getAttribute("obj"); %>
<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen");  %>
<% 	//ResultSet chungtuRs = (ResultSet) obj.getChungtuRs(); 
	Utility util=new Utility();
	Utility util1 = (Utility) session.getAttribute("util");
	String url="";
//	url = util1.getUrl("ErpKhoasothangSvl","");
	int[] quyen = new  int[5];
//	quyen=util.Getquyen("ErpKhoasothangSvl", "92",userId);

	NumberFormat formatter = new DecimalFormat("#,###,###"); 

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
    <LINK rel="stylesheet" href="../css/cdtab.css" type="text/css">
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js"
		type="text/javascript"></script>
	 <LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
<script>
//perform JavaScript after the document is scriptable.
$(document).ready(function() {

    //When page loads...
    $(".tab_content").hide(); //Hide all content
    var index = $("ul.tabs li.current").show().index(); 
    $(".tab_content").eq(index).show();

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

    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	 function submitform()
	 {   
		 
		 
		 document.forms['erpCngn'].action.value= 'chaygiavon';
	     document.forms["erpCngn"].submit();
	 }
	
	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	 function capnhatdln()
	 {
	 	document.forms['erpCngn'].action.value= 'dln';
	 	document.forms['erpCngn'].submit();
	 }
	 function submitview()
	 { 
		
		 
				 document.forms['erpCngn'].action.value='submitview';
			     document.forms['erpCngn'].submit();	
	 }

	 
	 </SCRIPT>
	 
</head>
<body>
<form name="erpCngn" method="post" action="../../ErpChaygiavonSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="idks" value="<%=obj.getId()%>" >

<input type="hidden" name="msg" id="msg" value="<%= obj.getMsg() %>" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
             <%=url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
    <div align="left" style="width: 100%; float: none; clear: left">
		<fieldset>
			<legend class="legendtitle" > Thông báo</legend>
			<textarea rows="2"  style="width: 100%; ; font-weight:bold">
				<%=obj.getMsg()%> </textarea>
		</fieldset>
	</div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle">Khóa sổ kho tháng</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px">								                          
                    
                    <tr class="plainlabel">
								<td width="15%" class="plainlabel" valign="middle">Năm </td>
								<td>
								<select name="nam" id="nam" onchange="submitview()"  style="width :70px" ">
									<option value=0> </option>  
									<%
									  
									 Calendar c2 = Calendar.getInstance();
										int t=c2.get(Calendar.YEAR) +3;
										
									
  										int n;
  										for(n=2010;n<t;n++){
  										if(obj.getNam().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 											%>
 											<option value=<%=n%> > <%=n%></option> 
 											<%
 										}
 									
 										
 									 }
 									%>
 									</select>
								</td>
							</tr>
							<tr class="plainlabel">
								<td width="20%" class="plainlabel" valign="middle">Tháng </td>
								<td>
								<select name="thang"  onchange="submitview()" id="thang"  style="width :70px" ">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										
  									  if(obj.getThang().equals(k+"")){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 											%>
 											<option value=<%=k%> > <%=k%></option> 
 											<%
 										}
 									
 									  }
 									%>
									</select>
								</td>
								</tr>
								
                    
                    <tr>
                        <td colspan="2" class="plainlabel">
                        	 
                            		<a class="button2" href="javascript:submitform()">
                                	<img style="top: -4px;" src="../images/button.png" alt="">Chạy giá vốn</a>&nbsp;&nbsp;&nbsp;&nbsp;
                               
                        </td>
                    </tr>        					
                </TABLE>    
                
                <hr />

        </fieldset>                      
    	</div>
        
    </div>  
</div>
</form>
</body>
</html>
