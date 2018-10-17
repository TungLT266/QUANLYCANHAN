<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkhoTSCD.*" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>

<%@ page  import = "java.sql.ResultSet" %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 
String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
 IErpPhieuxuatkhoTSCDList obj = (IErpPhieuxuatkhoTSCDList)session.getAttribute("obj"); %>
<% ResultSet pxkList = (ResultSet)obj.getPxkList(); %>
<% ResultSet dvkdList = (ResultSet)obj.getDvkdList(); %>

<% obj.setNextSplittings(); 
List<ISanpham> spList =obj.getSpList();
int[] quyen = new  int[5];
quyen = util.Getquyen("ErpPhieuxuatkhoTSCDSvl","",userId);

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
	
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>	
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/AjaxCheckStock.js"></script>
	
	
<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100;
}

#ajax_listOfOptions div {
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
}
</style>
	
	
	
	
	
	
	
	
   
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
    
    
    function replaces()
    {	
    	var spId = document.getElementsByName("spId");
    	var masp = document.getElementsByName("spMa");
    	var tensp = document.getElementsByName("spTen");
    	
    	var i;
    	for(i=0; i < masp.length; i++)
    	{
    		if(masp.item(i).value != "")
    		{
    			var sp = masp.item(i).value;
    			var pos = parseInt(sp.indexOf(" - "));
    			if(pos > 0)
    			{
    				masp.item(i).value = sp.substring(0, pos);
    				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
    				tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf("[")));
    							
    				
    				sp = sp.substr(parseInt(sp.indexOf(" [")) + 2); //console.log(sp);
    			    spId.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
    			    
    			    
    			}			
    		}
    		else
    		{
    			tensp.item(i).value = "";
    			spId.item(i).value = "";	
    		}
    	}	
    	setTimeout(replaces, 20);
    }
    
    
    function addRow(pos)
	{
		var table = $('#tbSanPham');
		table.append(
                '<tr>'+
    			'<td>'+
				'<input type="hidden" name="spId"  value=""/>'+ 		                            			
    			'<input type="text" name="spMa" style="width: 100%"  value=""   onkeyup="ajax_showOptions(this,\'donmuahang\',event)" AUTOCOMPLETE="off"  />   </td>'+
				'<td><input type="text" name="spTen"  style="width: 100%" value=""/>   </td>'+
			'</TR>');
	}
 
 
 
	function ThemSanPham(pos)
	{
		 var sl = window.prompt("Nhấp số lượng sản phẩm muốn thêm", '');
		 if(isNaN(sl) == false && sl < 100)
		 {
			 for(var i=0; i < sl ; i++)
				 addRow(pos);
		 }
		 else
		 {
			 alert('Số lượng bạn nhập không hợp lệ. Mọi lần bạn chỉ được thêm tối đa 30 sản phẩm');
			 return;
		 }
	 } 
    
    
	 function submitform()
	 {   
	    document.forms["erpPxkForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpPxkForm"].action.value = "Tao moi";
	    document.forms["erpPxkForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpPxkForm"].sophieu.value = "";
	    document.forms["erpPxkForm"].trangthai.value = "";
	    document.forms["erpPxkForm"].tungay.value = "";
	    document.forms["erpPxkForm"].denngay.value = "";
	    document.forms["erpPxkForm"].khachhang.value = "";
		    
	    document.forms["erpPxkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpPxkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpPxkForm"].msg.value);
	 }
	 
	 function processing(id,chuoi)
	 {
		 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
		 document.getElementById(id).href=chuoi;
	 }
	</SCRIPT>
</head>
<body>
<form name="erpPxkForm" method="post" action="../../ErpPhieuxuatkhoTSCDSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý mua hàng > Xuất trả TSCĐ
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
                        <TD class="plainlabel" >Số phiếu/Mã ĐTH </TD>
                        <TD class="plainlabel"  >
                            <input type="text" id="sophieu" name="sophieu" value="<%= obj.getSoPhieu() %>" onchange="submitform()" />
                        </TD>
						 <TD class="plainlabel" >Nhà cung cấp </TD>
                        <TD class="plainlabel">
                            <input type="text"  
                                   id="khachhang" name="khachhang" value="<%= obj.getKhachhang() %>"  onchange="submitform()" />
                        </TD>
                    </TR>								                          
                    <TR>
                        <TD class="plainlabel" width="150px">Từ ngày </TD>
                        <TD class="plainlabel" width="230px" >
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        <TD class="plainlabel" width="100px" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                    
                      <TR>
							
						 <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle" colspan="3">
                           <select name="trangthai" onChange="submitform();" >
                           		<option value=""> </option>
								<%  
   								String[] mang=new String[]{"0","1","2"};
   								String[] mangten=new String[]{" Chưa chốt","Đã chốt"," Đã xóa"};
   								 for(int j=0;j<mang.length;j++){
   									 if(obj.getTrangthai().trim().equals(mang[j])){
   									 %>
   									 <option selected="selected" value="<%=mang[j]%>"> <%=mangten[j] %> </option>
   									 <%
   									 }else{
   									 %>
   									 <option value="<%=mang[j]%>"> <%=mangten[j] %> </option>
   									 <% 
   									 }
   								 }
   								%>
                           </select>
                        </TD> 
						</TR> 
   
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Phiếu xuất kho </span>&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Mã phiếu</TH>
	                    <TH align="center">Ngày lập phiếu</TH>
	                    <TH align="center">Số ĐTH</TH>
	                    <TH align="center">Nội dung xuất</TH>
	                    <TH align="center">NCC</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="center"> Người tạo </TH>
	                    <TH align="center"> Ngày sửa </TH>
	                    <TH align="center"> Người sửa </TH>
	                    <TH align="center" >Tác vụ</TH>
	                </TR>
	               <%
                 		if(pxkList != null)
                 		{
                 			int m = 0;
                 			while(pxkList.next())
                 			{  		
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center">
		                    <% if(pxkList.getString("trangthai").equals("3")){ %>
		                    	<span style="color: red;"><%= pxkList.getString("SOXUATKHO") %></span>
		                    <%}else{ %>
		                    	<span><%= pxkList.getString("SOXUATKHO") %></span>
		                    <%} %>
		                    </TD>
		                    <TD align="center"><%= pxkList.getString("NGAYXUAT") %></TD>
		                    <TD align="center"><%= pxkList.getString("SOCHUNGTU") %></TD>	
		                    <TD align="left"><%= pxkList.getString("ndxTen") %></TD>	
		                     <TD align="left"><%= pxkList.getString("DoiTuong") %></TD>
		                    <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = pxkList.getString("trangthai");
		                    		if(tt.equals("0"))
		                    			trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			if(tt.equals("1"))
		                    				trangthai = "Đã chốt";
		                    			else
		                    			{
		                    				 trangthai = "Đã xóa";
		                    				
		                    			}
		                    		}
		                    	%>
		                    	<%= trangthai %>
		                    </TD>									                                    
		                    <TD align="center"><%= pxkList.getString("ngaytao") %></TD>
		                    <TD align="left"><%= pxkList.getString("nguoitao") %></TD>
		                    <TD align="center"><%= pxkList.getString("ngaysua") %></TD>	
		                    <TD align="left"><%= pxkList.getString("nguoisua") %></TD>				
		                    <TD align="center"> 
		                    <% if(tt.equals("0")){ %>
		                    <%if(quyen[2]!=0){ %>
                                <A id='capnhat<%=pxkList.getString("pxkId")%>'
                                href = "">                                
                                <IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" 
                                border="0" onclick="processing('<%="capnhat"+pxkList.getString("pxkId")%>' , '../../ErpPhieuxuatkhoTSCDUpdateSvl?userId=<%=userId%>&update=<%= pxkList.getString("pxkId") %>')">
                                </A>&nbsp;
                                <%} %>
                                <%if(quyen[4]!=0){ %>
                                <A id='chotphieu<%=pxkList.getString("pxkId") %>'
                                href = "">  
                                <img src="../images/Chot.png" alt="Chốt phiếu" title="Chốt phiếu" width="20" height="20" 
                                border=0 onclick="if(!confirm('Bạn có chắc chốt phiếu xuất kho này?')) {return false;} else{ processing('<%="chotphieu"+pxkList.getString("pxkId")%>' , '../../ErpPhieuxuatkhoTSCDSvl?userId=<%=userId%>&chot=<%=pxkList.getString("pxkId") %>');}"></A>&nbsp;
                                <%} %>
                                <%if(quyen[1]!=0){ %>
                                <A id='xoaphieu<%=pxkList.getString("pxkId") %>'
                                href = "">  
                                <img src="../images/Delete20.png" alt="Xóa phiếu" title="Xóa phiếu" width="20" height="20" 
                                border=0 onclick="if(!confirm('Bạn có muốn xóa phiếu xuất kho này?')) {return false;} else{ processing('<%="xoaphieu"+pxkList.getString("pxkId")%>' , '../../ErpPhieuxuatkhoTSCDSvl?userId=<%=userId%>&delete=<%=pxkList.getString("pxkId") %>');}"></A>
                              	<%} %>  								
		                    <%}else{ %>
		                    	<A href = "../../ErpPhieuxuatkhoTSCDUpdateSvl?userId=<%=userId%>&display=<%= pxkList.getString("pxkId") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp; 
		                    <%} %>
		                    </TD>
		                </TR>
                     <% m++; } pxkList.close(); } %>
					<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	<%if(obj.getNxtApprSplitting() >1) {%>
								<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpPxkForm', 1, 'view')"> &nbsp;
							<%}else {%>
								<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
								<%} %>
							<% if(obj.getNxtApprSplitting() > 1){ %>
								<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpPxkForm', 'prev')"> &nbsp;
							<%}else{ %>
								<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
							<%} %>
							
							<%
								int[] listPage = obj.getNextSplittings();
								for(int i = 0; i < listPage.length; i++){
							%>
							
							<% 							
						
							if(listPage[i] == obj.getNxtApprSplitting()){ %>
							
								<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
							<%}else{ %>
								<a href="javascript:View('erpPxkForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
							<%} %>
								<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
							<%} %>
							
							<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpPxkForm', 'next')"> &nbsp;
							<%}else{ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
							<%} %>
							<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
						   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
					   		<%}else{ %>
					   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpPxkForm', -1, 'view')"> &nbsp;
					   		<%} %>
						</TD>
					 </tr> 
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>

<script type="text/javascript">
		replaces();
		dropdowncontent.init("noidungGhiChu", "right-bottom", 650, "click");
</script>
</form>
</body>
<%
try{
	if(pxkList!=null){
		pxkList.close();
	}
	obj.DBclose();
	obj=null;
}catch(Exception er)
{
	er.printStackTrace();	
}
} %>
</html>
