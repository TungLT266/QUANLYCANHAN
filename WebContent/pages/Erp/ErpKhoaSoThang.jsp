<%@page import="geso.traphaco.erp.beans.khoasothang.IErpKhoasothang"%>
<%@page import="geso.traphaco.center.util.Utility"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@page import="java.util.*"%>

<% 	IErpKhoasothang obj = (IErpKhoasothang)session.getAttribute("obj"); %>
<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen");  %>
<% 	ResultSet chungtuRs = (ResultSet) obj.getChungtuRs();
	ResultSet amkhoRs = (ResultSet) obj.getAmkhoRs();
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>FIT - Project</TITLE>  
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
		 document.forms['erpCngn'].action.value= 'submit';
	     document.forms["erpCngn"].submit();
	 }
	 
	 function KhoaSoThang()
	 {   
		 var r = confirm("Hệ thống sẽ khóa sổ tháng bạn chọn, mọi giao dịch trong tháng sẽ được đóng lại, bạn vẫn muốn tiếp tục?");
		 if(r == false)
			 return;
		 
		 document.forms['erpCngn'].action.value= 'khoasokho';
	     document.forms["erpCngn"].submit();
	 }
	 
	 function MoKhoaSoThang()
	 {   
		 var r = confirm("Hệ thống sẽ mở khóa sổ tháng bạn chọn, bạn vẫn muốn tiếp tục?");
		 if(r == false)
			 return;
		 
		 document.forms['erpCngn'].action.value= 'moKhoasokho';
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
<form name="erpCngn" method="post" action="../../ErpKhoasothangSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" id="msg" value="<%= obj.getMsg() %>" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
 
        	Quản lý tồn kho > Khóa sổ > Khóa sổ tháng 
 
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
            <legend class="legendtitle">Khóa sổ tháng</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px">								                          
                    
                    <tr class="plainlabel">
                    	<td colspan="2" class="plainlabel" valign="middle">Tháng khóa sổ gần nhất ( <%= obj.getThangKSGannhat() + " / " + obj.getNamKSGannhat() %> ) </td>
                    </tr>
                    <tr class="plainlabel">
						<td width="100px" class="plainlabel" valign="middle">Năm </td>
						<td>
							<select name="nam" id="nam" onchange="submitform();" >
								<option value=0> </option>  
								<% Calendar c2 = Calendar.getInstance();
									int t = c2.get(Calendar.YEAR) + 1;
 									for(int n = c2.get(Calendar.YEAR) - 1; n < t; n++ ){
 										if(obj.getNam().equals(""+n)){ %>
										<option value=<%=n%> selected="selected" > <%=n%></option> 
								<% }else{ %>
										<option value=<%=n%> > <%=n%></option> 
								<% } } %>
							</select>
						</td>
					</tr>
					<tr class="plainlabel">
						<td class="plainlabel" valign="middle" >Tháng </td>
						<td>
							<select name="thang"  id="thang" onchange="submitform();" >
								<option value= '' ></option>  
								<% for(int k=1;k<=12;k++){
									  if(obj.getThang().equals(k+"")){ %>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
								<% }else{ %>
									<option value=<%=k%> > <%=k%></option> 
								<% } } %>
							</select>
						</td>
					</tr>	
                    
                    <tr>
                        <td colspan="2" class="plainlabel">
             
                          	<a class="button2" href="javascript:KhoaSoThang()">
                              	<img style="top: -4px;" src="../images/button.png" alt="">Khóa sổ tháng</a>&nbsp;&nbsp;&nbsp;&nbsp;
                              	
                            <a class="button2" href="javascript:MoKhoaSoThang()">
                              	<img style="top: -4px;" src="../images/button.png" alt="">Mở khóa sổ tháng</a>&nbsp;&nbsp;&nbsp;&nbsp;
                               
                        </td>
                    </tr>   
                    
                    <% if( chungtuRs != null ) { %>
                    <tr>
                    	<td colspan="2" class="plainlabel">
                    	
                    		<ul class="tabs">    			
                  				<li class="current" ><a href="#chuanhanhang">Chưa nhận hàng</a></li>
                  				<li ><a href="#amkho">Âm kho</a></li>
			                </ul>
                    	
                    		<div class="panes">
                    	
                    		<div id='chuanhanhang' class ="tab_content" >
                    		
	                    		<TABLE border="0" cellpadding="4"  cellspacing="2" width="100%">
									<TR class="tbheader" >
										<TH width="5%" align = "center" >STT </TH>
										<TH width="10%" align = "center" >Só chứng từ </TH>															
										<TH width="10%" align = "center" >Ngày chứng từ</TH>
										<TH width="40%" align = "center" >Kho chuyển</TH>
										<TH width="35%" align = "center" >Kho nhận</TH>
									</TR>
									
									<% 
										String lightrow = "tblightrow";
										String darkrow = "tbdarkrow";
										if( chungtuRs != null ) { 
											int i = 0;
											String _class = "";
											while( chungtuRs.next() ) { 
											
											if( i % 2 == 0 )
												_class = " class = '" + lightrow + "' ";
											else
												_class = " class = '" + darkrow + "' ";
											%> 
											
											<Tr <%= _class %> >
												<td><%= i %></td>
												<td><%= chungtuRs.getString("pk_seq") %></td>
												<td><%= chungtuRs.getString("NgayChuyen") %></td>
												<td><%= chungtuRs.getString("khochuyen") %></td>
												<td><%= chungtuRs.getString("khonhan") %></td>
											</Tr>
										
										<% i++; } chungtuRs.close();  } %>
									
								</TABLE>
                    		
                    		</div>
                    		
                    		<div id='amkho' class ="tab_content" >
                    		
	                    		<TABLE border="0" cellpadding="4"  cellspacing="2" width="100%">
									<TR class="tbheader" >
										<!-- <TH width="15%" align = "center" >Mã kho </TH>	 -->														
										<TH width="25%" align = "center" >Mã sản phẩm</TH>
										<TH width="55%" align = "center" >Tên sản phẩm</TH>
										<!-- <TH width="10%" align = "center" >Số lô</TH>
										<TH width="10%" align = "center" >Ngày hết hạn</TH>
										<TH width="10%" align = "center" >Ngày nhập kho</TH> -->
										<TH width="20%" align = "center" >Tồn kho</TH>
									</TR>
									
									<% 
										lightrow = "tblightrow";
										darkrow = "tbdarkrow";
										if( amkhoRs != null ) { 
											int i = 0;
											String _class = "";
											while( amkhoRs.next() ) { 
											
											if( i % 2 == 0 )
												_class = " class = '" + lightrow + "' ";
											else
												_class = " class = '" + darkrow + "' ";
											%> 
											
											<Tr <%= _class %> >
												<%-- <td><%= amkhoRs.getString("makho") %></td> --%>
												<td><%= amkhoRs.getString("masanpham") %></td>
												<td><%= amkhoRs.getString("tensanpham") %></td>
												<%-- <td><%= amkhoRs.getString("solo") %></td>
												<td><%= amkhoRs.getString("ngayhethan") %></td>
												<td><%= amkhoRs.getString("ngaynhapkho") %></td> --%>
												<td style="text-align: right;" ><%= amkhoRs.getString("toncuoi") %></td>
											</Tr>
										
										<% i++; } amkhoRs.close();  } %>
									
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

<%

try{
	session.setAttribute("obj",null);	
	 obj.DbClose();
	
}catch(Exception er){
	
}
 %>
</form>
</body>
</html>
