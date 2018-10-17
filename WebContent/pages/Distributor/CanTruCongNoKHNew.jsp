<%@page import="geso.traphaco.distributor.beans.cantrucongno.ICantrucongno"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp")  ;
	}else{ %>

<% ICantrucongno nvgnBean = (ICantrucongno)session.getAttribute("nvgnBean");
   ResultSet hoadonList = nvgnBean.getHoadonList() ;
   ResultSet ddkdRs = nvgnBean.getDdkdRs();
   ResultSet khRs = nvgnBean.getKhList();
   String[] hdId = nvgnBean.getHdId();
   String[] hdNgayhd = nvgnBean.getHdNgayhd();
   String[] hdKhid = nvgnBean.getHdKhid();
   String[] hdMakh = nvgnBean.getHdMakh();
   String[] hdSohd = nvgnBean.getHdSohd();
   String[] hdTienAvat = nvgnBean.getHdSotien();
   String[] hdChon = nvgnBean.getHdChon();
   String[] hdTienCK = nvgnBean.getHdTienCK();
   
   NumberFormat formatter = new DecimalFormat("#,###,###"); 
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/jquery-1.js"></script>
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
</script>
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
<SCRIPT language="javascript" type="text/javascript">
		
	function confirmLogout()
	{
	   if(confirm("Ban co muon dang xuat?"))
	   {
			top.location.href = "home.jsp";
	   }
	   return
	}
	
	function searchform()
	{
		document.nvgnForm.action.value= 'search';
		document.forms['nvgnForm'].submit();
	}
	
	function submitform()
	{
		if (document.forms["nvgnForm"]["tungay"].value.length == 0) {
			setErrors("Vui lòng chọn từ ngày!");					
			return;
		}
		if (document.forms["nvgnForm"]["tungay"].value.length == 0) {
			setErrors("Vui lòng chọn đến ngày!");					
			return;
		}
		document.forms['nvgnForm'].action.value='submitForm';
	   	document.forms['nvgnForm'].submit();
	}
	 function setErrors(errorMsg) {
			var errors = document.getElementById("dataerror");
			errors.value = errorMsg;
		}
	
	function saveform()
	{	 
		if (document.forms["nvgnForm"]["ngay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày chứng từ!");					
			return;
		} 
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";		
		document.forms['nvgnForm'].action.value='save';
	    document.forms['nvgnForm'].submit();
	}
	
	function keypress(e) //Hàm dùng ngăn người dùng nhập ký tự khác ký tự số vào Textbox
	{    
		
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46 || keypressed == 45)
		{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function CheckAll()//hàm check hết 
	{
		var selectAll = document.getElementById("selectAll");		
		var hdId = document.getElementsByName("hdId");		
		var hdtienck = document.getElementsByName("hdtienck");
		
		var tc_tienCK = document.getElementById("tc_tienCK").value.replace(/,/g, "");
		if(tc_tienCK == '')
			tc_tienCK=0;
				
		if(selectAll.checked)//khi check hết
		{
			for(i = 0; i < hdId.length; i++)
			{					
				document.getElementById(hdId.item(i).value).checked= true;	
				tc_tienCK = parseFloat(tc_tienCK) + parseFloat(hdtienck.item(i).value.replace(/,/g, ""));
			}
			
		}
		else //khi bỏ check
		{
			for(i = 0; i < hdId.length; i++)
			{	
				document.getElementById(hdId.item(i).value).checked = false;
			}
			tc_tienCK=0;
		}
		document.getElementById("tc_tienCK").value=DinhDangTien(tc_tienCK);
	}
	
	function UnCheckedAll()
	{
		var selectAll = document.getElementById("selectAll");
		selectAll.checked = false;
	}
	
	function Change_NO_CO(stt)//khi check từng cái
	{		
		var hdtienck = document.getElementsByName("hdtienck");
		
/* 		var hdghico1 = document.getElementsByName("hdghico1").item(stt);
		var hdghino1 = document.getElementsByName("hdghino1").item(stt); */
		
		var hdId = document.getElementsByName("hdId").item(stt);
		
		var chon = document.getElementById(hdId.value);
		
		var tc_tienCK = document.getElementById("tc_tienCK").value.replace(/,/g, "");
		if(tc_tienCK == '')
			tc_tienCK=0;
		
		if(chon.checked)
		{
			tc_tienCK = parseFloat(tc_tienCK) + parseFloat(hdtienck.item(stt).value.replace(/,/g, ""));
		}
		else
		{	
			tc_tienCK = parseFloat(tc_tienCK) - parseFloat(hdtienck.item(stt).value.replace(/,/g, ""));
		}
		
		
		document.getElementById("tc_tienCK").value=DinhDangTien(tc_tienCK);
		
		UnCheckedAll();
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	
	function Dinhdang(element)
	{
		element.value = DinhDangTien(element.value);
		if(element.value== '' || element.value== '0')
		{
			element.value= '';
		}
	}

</SCRIPT>

	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2(); });
	     
	</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="nvgnForm" method="post" action="../../CantrucongnoUpdateSvl" >
<input type="hidden" name="nppId" id= "nppId" value='<%= nvgnBean.getNppId() %>'>
<input type="hidden" name="action" value='1'>
<input name="userId" type="hidden" value='<%=userId %>' size="30">
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý công nợ > Khấu trừ chiết khấu tháng > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng  <%= nvgnBean.getNppTen() %> &nbsp; 
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../CantrucongnoSvl?userId=<%=userId %>">		 		
	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A>
        <label id="btnSave">
        <A href="javascript:saveform()">
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
        	</label>
    </div>
    
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle">Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror" id="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold;" style="width: 100%" readonly="readonly" rows="1"><%= nvgnBean.getMessage() %></textarea>
		         <% nvgnBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Khấu trừ chiết khấu tháng </legend>       	
        <div style="float:none; width:100%" align="left">
            <table width="100%" cellspacing="0" cellpadding="6px">
                <TR>
                    <TD width="15%" class="plainlabel" > Ngày chứng từ</TD>
                    <TD width="15%" class="plainlabel">
                        <INPUT type="text" name="ngay" class = "days" size="40" value="<%= nvgnBean.getNgay()%>">
                    </TD>
                     <TD width="15%" class="plainlabel" > Ghi chú</TD>
                    <TD width="20%" class="plainlabel">
                        <INPUT type="text" name="ghichu"  size="50" value="<%= nvgnBean.getGhichu()%>">
                    </TD>                      
                                                                                   	                               
                </TR>
                <TR>
                    <TD class="plainlabel">Tìm từ ngày</TD>
                    <TD  class="plainlabel">
                        <INPUT type="text" name="tungay"  class = "days" size="60" value="<%= nvgnBean.getTungay()%>">
                    </TD>

                     <TD class="plainlabel">Tìm đến ngày</TD>
                     <TD  class="plainlabel">
                        <INPUT type="text" name="denngay" class = "days"  size="15" value="<%= nvgnBean.getDenngay() %>"></TD>
                </TR>
                <TR>
                    <TD class="plainlabel">Số hóa đơn từ</TD>
                    <TD  class="plainlabel">
                        <INPUT type="text" name="sohoadontu"   size="60" value="<%= nvgnBean.getSohoadontu()%>">
                    </TD>

                     <TD class="plainlabel">Số hóa đơn đến</TD>
                     <TD  class="plainlabel">
                        <INPUT type="text" name="sohoadonden"  size="15" value="<%= nvgnBean.getSohoadonden() %>"></TD>
                </TR>
                <TR>
                    <TD class="plainlabel">Số tiền từ</TD>
                    <TD  class="plainlabel">
                        <INPUT type="text" name="sotientu" id= "sotientu"    size="15" value="<%= nvgnBean.getSotientu() %>" onKeyPress = "return keypress(event);"  onkeyup="Dinhdang(this)">
                    </TD>

                     <TD class="plainlabel">Số tiền đến</TD>
                     <TD  class="plainlabel">
                        <INPUT type="text" name="sotienden"  id= "sotienden"  size="15" value="<%= nvgnBean.getSotienden() %>" onKeyPress = "return keypress(event);"  onkeyup="Dinhdang(this)"></TD>
                </TR>
                
                <TR>
               	<TD class="plainlabel">Trình dược viên</TD>
					<TD class="plainlabel">
						<SELECT name="ddkdId" id="ddkdId"  style="width: 200px" onChange = "searchform();">
						<option value=""></option>
							<% if(ddkdRs != null){ 
								try{while(ddkdRs.next()){ 
								    	if(ddkdRs.getString("pk_seq").equals(nvgnBean.getDdkdId())){ %>
													<option value='<%= ddkdRs.getString("pk_seq") %>' selected><%= ddkdRs.getString("ten") %></option>
													<%}else{ %>
													<option value='<%= ddkdRs.getString("pk_seq") %>'><%= ddkdRs.getString("ten") %></option>
													<%}}}catch(java.sql.SQLException e){} 
							}
							%>
						</SELECT>
					</TD> 
					
					<TD class="plainlabel">Khách hàng</TD>
					<TD class="plainlabel">
						<SELECT name="khIdLoc" id="khIdLoc" style="width: 200px" onChange = "searchform();">
								<option value=""></option>
								<% if(khRs != null){ try{while(khRs.next()){ 
								    	if(khRs.getString("PK_SEQ").equals(nvgnBean.getKhId())){ %>
											<option value='<%= khRs.getString("PK_SEQ") %>' selected><%=khRs.getString("Ten") %></option>
											<%}else{ %>
											<option value='<%= khRs.getString("PK_SEQ") %>'><%= khRs.getString("Ten") %></option>
											<%}}}catch(java.sql.SQLException e){}
								}
								%> 
						</SELECT>
					</TD>

                </TR>
                <TR>
						<TD class="plainlabel" colspan="4">
							<a class="button2" href="javascript:submitform();"> 
								<img style="top: -4px;" src="../images/Search30.png" alt="">Lọc hóa đơn </a>&nbsp;&nbsp;&nbsp;&nbsp;
				</TR>
             </table>
             
             <div id="xoanokh" align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
	             <table width="100%" border="0" cellspacing="1" cellpadding="1">
	                    <tr class="tbheader">
	                    	<th style="width:2% " align="center">STT </th>
	                        <th style="width:30% " align="center">Tên khách hàng OTC/ETC </th>
	                        <th style="width:10% " align="center">Số chứng từ</th>
	                        <th style="width:8% " align="center">Ngày hóa đơn</th>
	                        <th style="width:10% " align="center">Số hóa đơn</th>
	                        <th style="width:10% " align="center">Tiền hóa đơn </th>
	                        <th style="width:10% " align="center">CK (CT5)</th>                        
	                        <th style="width:5% " align="center" >Chọn <input type="checkbox" id="selectAll" onChange="CheckAll()"/></th>
	                    </tr>
	                   
	                    <Tr>
	                   		<Td align="center" colspan="5">
	                   			<input type="text" readonly="readonly" name="" style="width: 100%" value="" > <!-- STT_TongCong -->
	                   		</Td>
	                   		
	                   		<Td align="center">
	                   			<input type="text" readonly="readonly" name="" style="width: 100%; text-align:right" value="<%= nvgnBean.getTc_duno()%>" > <!-- tiền hóa đơn-->
	                   		</Td>
	                   		<Td align="center">
	                   			<input type="text" readonly="readonly" name="tc_tienCK" id="tc_tienCK" style="width: 100%; text-align:right" value="<%= nvgnBean.getTc_duco()%>" > <!-- Tiền chiết khấu-->
	                   		</Td>
	                   </Tr>
	                    <%
	                    int n = 0;
	                    int stt=1;
	                    if(hdId != null )
	                    {	
	                    	int k =  hdId.length;
							for(int i = 0; i < hdId.length ; i++){ 	
									
								%>
						<TR>
							<TD align="center">
								<input type="text" readonly="readonly" name="STT" style="width: 100%" value="<%= stt%>" > <!-- Tên khách hàng -->							
							</TD>
							
							<TD align="center">
							    <input type="hidden" name="khId"  value="<%= hdKhid[i]%>" > 
								<input type="text" readonly="readonly" name="khMa" style="width: 100%" value="<%= hdMakh[i]%>" > <!-- Tên khách hàng -->							
							</TD>
							<TD align="left">
								<input type="text" readonly="readonly" name="hdId" style="width: 100%" value="<%= hdId[i]%>" > <!-- Số chứng từ -->
							</TD> 
							<TD align="center">
								<input type="text" readonly="readonly" name="hdNgay" style="width: 100%" value="<%= hdNgayhd[i]%>" > <!-- Ngày chứng từ -->
							</TD>
							<TD align="center">
								<input type="text" readonly="readonly" name="hdsohoadon" style="width: 100%; text-align:center" style="width: 100%" value="<%= hdSohd[i] %>" > <!-- Số hóa đơn -->
							</TD>
							<TD align="center">
								<input type="text" readonly="readonly" name="hdtienavat" style="width: 100%; text-align:right" style="width: 100%" value="<%= formatter.format(Double.parseDouble(hdTienAvat[i].replaceAll(",",""))) %>"  onChange="ThanhToan( <%= i  %> );"> <!-- Tiền avat -->
							</TD>
							<TD align="center">
								<input type="text" readonly="readonly" name="hdtienck" style="width: 100%; text-align:right"  value="<%= formatter.format(Double.parseDouble(hdTienCK[i].replaceAll(",",""))) %>" onChange="Change_NO_CO( <%= i  %> );" > <!-- Tiền chiết khấu -->
							</TD>
					
																												
							<TD align="center">
							<% if (hdChon[i].equals(hdId[i] )){ %>
								<input name="<%= hdId[i] %>" id="<%= hdId[i] %>"  value="<%= hdId[i] %>" type="checkbox"  checked  onChange="Change_NO_CO( <%= i  %> );" >
							<%}else{ %>											
								<input name="<%= hdId[i] %>" id="<%= hdId[i] %>" type="checkbox" value="<%= hdId[i]%>"  onChange="Change_NO_CO( <%= i  %> );" >
							<%} %>
						</TD> 
	                    </TR> <%
	                    stt++; } %> 					     	
					   <%  } else {%>
					   	<tr><td class="plainlabel" colspan="10">&nbsp;</td></tr>
					   <%} %>					   
					                    
	             </table> 
             </div>   
         </div>      
    </fieldset>	
    </div>
</div>
</form>
</body>
</html>
<% 	


	try{

		if(nvgnBean!=null){
		nvgnBean.DBclose();
		nvgnBean=null;
		}
	}
	catch (Exception e) {}
%>
<%}%>