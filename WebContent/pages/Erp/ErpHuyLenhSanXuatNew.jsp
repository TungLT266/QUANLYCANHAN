<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.huychungtu.*" %>
<%@ page  import = "geso.traphaco.erp.beans.huychungtu.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% IErpHuylenhsanxuat hctmhBean = (IErpHuylenhsanxuat)session.getAttribute("hctmhBean"); %>
<% ResultSet soctRequest = hctmhBean.getSochungtuRequest(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Diageo - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
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


<script language="javascript" type="text/javascript">

	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	 function saveform()
	 {	
		 var r = confirm("Bạn có chắc chắn hủy chứng từ này?")
		 if (r == false)
		 {
		   return;
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value='save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['hctmhForm'].action.value='submit';
	     document.forms["hctmhForm"].submit();
	 }
	 
	 function checkHuyCt()
	 {
		 var sochungtuhuy = document.getElementsByName("sochungtuhuy");
		 for(i = 0; i < sochungtuhuy.length; i++)
		 {
			 if(sochungtuhuy.item(i).checked)
			 {
				 for(j = 0; j < i; j++)
				 {
					 if(sochungtuhuy.item(j).checked == false)
					 {
						 alert('Bạn phải hủy các chứng từ theo đúng thứ tự trong quy trình');
						 return false;
					 }
				 }
			 }
		 } 
		 return true;
	 }
	 
	 function HuyChungTu(pos)
	 {
		 var sochungtuhuy = document.getElementsByName("sochungtuhuy");
		 if(sochungtuhuy.item(pos).checked)
		 {
			 for(i = 0; i < parseInt(pos); i++)
			 {
				 if(sochungtuhuy.item(i).checked == false)
				 {
					 //alert('Bạn phải hủy các chứng từ theo đúng thứu tự trong quy trình');
					 sochungtuhuy.item(pos).checked = false;
					 return false;
				 }
			 } 
		 }
		 return true;
	 }
	 
	 function ReadOnly(pos)
	 {
		 var sochungtuhuy = document.getElementsByName("sochungtuhuy");
		 if(sochungtuhuy.item(pos).checked == false)
		 {
			 sochungtuhuy.item(pos).checked = true;
		 }
	 }
	 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpHuylenhsanxuatUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        		Quản lý cung ứng > Sản xuất > Hủy chứng từ > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpHuylenhsanxuatSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
    </div>
  	
  	<div align="left" style="width:100%%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%;color: red; background-color: white;"><%= hctmhBean.getMsg() %></textarea>
		         <% hctmhBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Hủy chứng từ </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">
            
            <TR>
                    <TD width="15%" class="plainlabel" valign="top"> Loại chứng từ </TD>
                    <TD colspan="2" class="plainlabel" valign="top">
                    	<% 
                    
                    /* 	String mang[] =new String[]{"YEUCAU","CHUYENKHO","THNL", "NK", "LSX","KDCL"};
                    	String mangten[] =new String[]{"Phiếu yêu cầu ","Chuyển kho","Tiêu hao nguyên liệu" ,"Nhập kho","Lệnh sản xuất","Kiểm định chất lượng"}; */
                    	
                    	String mang[] =new String[]{"LSX","CHUYENKHO","NKM","KDCLM","THNL"};
                    	String mangten[] =new String[]{"Lệnh sản xuất","Chuyển kho nguyên liệu","Nhập kho mới","Kiểm định chất lượng mới","Tiêu hao nguyên liệu"};
                    	
                    	
                    	%>                     
                    	<select name="loaichungtu1"  onchange="submitform()">
                    	 <option value="">  </option> 
                    	<%
                    	for(int j=0;j<mang.length;j++){
							 if(hctmhBean.getloaichungtu().trim().equals(mang[j])){
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
                    	
                </TR>
                							
                <TR>
                    <TD width="15%" class="plainlabel" valign="top"> Số chứng từ </TD>
                    <TD colspan="2" class="plainlabel" valign="top">
                    	<input type="text" id="sochungtu" name="sochungtu" value="<%= hctmhBean.getSochungtu() %>" onchange="submitform()"  /></TD>
                </TR>
                 <TR>
	          		<TD class="plainlabel" valign="middle" >Ngày ghi nhận</TD>
					<TD class="plainlabel" valign="middle"  colspan="2">
						<input type="text" class="days" name="ngayghinhan" value="<%= hctmhBean.getNgayghinhan() %>" readonly="readonly">
					</TD>
				</TR>
				<%-- <TR>
	          		<TD class="plainlabel" valign="middle" >Diễn giải chứng từ</TD>
					<TD class="plainlabel" valign="middle"  colspan="2">
						<input type="text" name="dienGiaiCT" value="<%= hctmhBean.getDienGiaiCT() %>">
					</TD>
				</TR>	 --%>	
								
                 <TR>
                    <TD width="15%" class="plainlabel" valign="top">&nbsp;</TD>
                    <TD colspan="2" class="plainlabel" valign="top">&nbsp;</TD>
                </TR>
              <% String sophieu="";  %>
              
              <% if(hctmhBean.getSochungtu().length() > 0){    sophieu=hctmhBean.getSochungtu();} System.out.println(" so chung tu" +sophieu );  %>
              
               <% if(hctmhBean.getKdclId_new().length() > 0){    sophieu=hctmhBean.getKdclId_new();} System.out.println(" so chung tu" +sophieu );  %>
              
             <% if(hctmhBean.getSophieunhapkho().length() > 0){    %>
               	<TR>
                    <TD width="12%" class="plainlabel" valign="top"> Số phiếu nhập kho </TD>
                    <TD colspan="2" class="plainlabel" valign="top">
                    	<input type="text" id="sonhapkho" name="sonhapkho" value="<%= hctmhBean.getSophieunhapkho() %>" readonly="readonly" /></TD>
               	</TR>
             <%} %>
             <% if(hctmhBean.getSoPhieunhanhang().length() > 0){  %>
               	<TR>
                    <TD width="12%" class="plainlabel" valign="top"> Số phiếu nhận hàng </TD>
                    <TD colspan="2" class="plainlabel" valign="top">
                    	<input type="text" id="sonhanhang" name="sonhanhang" value="<%= hctmhBean.getSoPhieunhanhang() %>" readonly="readonly" /></TD>
               	</TR>
             <%} %>
             <% if(hctmhBean.getSoDonMuahang().length() > 0){  %>
               	<TR>
                    <TD width="12%" class="plainlabel" valign="top"> Số đơn đơn mua hàng </TD>
                    <TD colspan="2" class="plainlabel" valign="top">
                    	<input type="text" id="somuahang" name="somuahang" value="<%= hctmhBean.getSoDonMuahang() %>" readonly="readonly" /></TD>
               	</TR>
             <%} %>
            </TABLE>
            <hr> 
            </div>
           
           <% 
           	if(soctRequest != null)
           	{ 
        	   int i = 1; %>
        	   
        	   <div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
	            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
	                <TR class="tbheader"> 
	                	<TH align="center" width="5%">STT</TH>
	                	<TH align="center" width="15%">&nbsp;Số chứng từ</TH>
	                	<TH align="center" width="15%">&nbsp;Ngày chứng từ</TH>
	                	<TH align="center" width="15%">&nbsp;Trạng thái</TH>
	                	<TH align="left" width="30%">&nbsp;Loại chứng từ</TH>
	               	 	<TH align="center" width="10%">Ngày tạo</TH>
	               	 	<TH align="center" width="10%">Chọn hủy</TH>
	                </TR>
        	   
        	<% while(soctRequest.next()){ %>
	                <TR>
	                	<TD align="center">
	                		<input type="text" name="stt" style="width: 100%; text-align: center;" value="<%= i %>" readonly="readonly" >
	                		<input type="hidden" name="thutu" style="width: 100%; text-align: center;" value="<%= soctRequest.getString("STT") %>" >
	                	</TD>
	                	<TD align="center">
	                		<input type="text" name="sochungtu1" style="width: 100%" value="<%= soctRequest.getString("pk_seq") %>" readonly="readonly" >
	                	</TD>
	                	<TD align="center">
	                		<input type="text" name="ngaychungtu" style="width: 100%; text-align: center;" value="<%= soctRequest.getString("ngaychungtu") %>" readonly="readonly" >
	                	</TD>
	                	<TD align="center">
	                		<input type="text" name="trangthai" style="width: 100%" value="<%= soctRequest.getString("trangthai") %>" readonly="readonly" >
	                	</TD>
	                	<TD align="center">
	                		<input type="text" name="loaichungtu" style="width: 100%" value="<%= soctRequest.getString("loaichungtu") %>" readonly="readonly" >
	                		<input type="hidden" name="type" style="width: 100%" value="<%= soctRequest.getString("type") %>" readonly="readonly" >
	                	</TD>
	                	<TD align="center">
	                		<input type="text" name="ngaytao" style="width: 100%; text-align: center;" value="<%= soctRequest.getString("NGAYTAO") %>" readonly="readonly" >
	                	</TD>
	                	<%-- <TD align="center">
	                			<input type="checkbox" name="sochungtuhuy" value="<%= soctRequest.getString("SOCHUNGTU") %>" onchange="HuyChungTu(<%= i - 1 %>)" >
	                	 
	                	</TD> --%>
	                	
	                	<TD align="center">
 
 									<%  System.out.println("sochungtu, sopkseq : "+ sophieu +", "+ soctRequest.getString("pk_seq")); %>
	                				<% if(sophieu.contains(soctRequest.getString("pk_seq"))){ %>
	                					<input type="hidden" name="chon" style="width: 100%; text-align: center;" value="1"  >
									<input type="checkbox" name="sochungtuhuy"
									value="<%= soctRequest.getString("pk_seq") %>"
									onchange="ReadOnly(<%= i - 1 %>)" checked="checked"
									readonly="readonly"> <%} else { %> 
									
									<input type="hidden" name="chon" style="width: 100%; text-align: center;" value="0"  >
									<input 
									type="checkbox" name="sochungtuhuy" disabled="disabled"
									value="<%= soctRequest.getString("pk_seq") %> "
									onchange="HuyChungTu(<%= i - 1 %>)"> <%} %>
 
 
	                	</TD>
	                </TR>
           <% i++; } } %>
           
            </TABLE> 
	        </div> 
     </fieldset>	
    </div>
</div>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<%
	try{
		if(soctRequest!=null){
			soctRequest.close();
		}
		if (hctmhBean != null)
			hctmhBean.DbClose();
		session.removeAttribute("hctmhBean");
		session.setAttribute("hctmhBean", null) ; 
		
	}catch(Exception err){
		err.printStackTrace();	
	}
}
%>
</BODY>
</html>