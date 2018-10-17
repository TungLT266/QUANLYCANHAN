<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.khauhaotaisancodinh.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.khauhaotaisancodinh.*" %>
<%@ page import="geso.traphaco.erp.beans.taisancodinh.IKhauHaoDuKien"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IKhauhaotaisan khts = (IKhauhaotaisan)session.getAttribute("khts");
	ResultSet namRs = khts.getNamRs();
	ResultSet thangRs = khts.getThangRs();
	ResultSet nhomRs = khts.getNhomRs();
	ResultSet loaiRs=khts.getLoaiRs();
	List<IGiaTriKhauHao> khauhaoList = new ArrayList<IGiaTriKhauHao>();
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	System.out.println("");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>

<HEAD>
<TITLE>Phanam - Project</TITLE>
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

	

	$(function() {
	 // setup ul.tabs to work as tabs for each div directly under div.panes
	 	$("ul.tabs").tabs("div.panes > div");

	});

	function calculation(i){	 
			console.log('aaaaaaaaaa');
			
		var dakhauhao = document.getElementsByName("dakhauhao_" +  i);
		dakhauhao.item(0).value = dakhauhao.item(0).value.replace(',', '').replace(',', '').replace(',', '').replace(',','');
				
		var tonggiatri = document.getElementsByName("tonggiatri_" +  i);
		tonggiatri.item(0).value = tonggiatri.item(0).value.replace(',', '').replace(',', '').replace(',', '').replace(',','');

		var khauhao = document.getElementsByName("khauhao_" +  i);
		khauhao.item(0).value = khauhao.item(0).value.replace(',', '').replace(',', '').replace(',', '').replace(',','');
			
		var khauhaoluyke = document.getElementsByName("khauhaoluyke_" +  i);
		khauhaoluyke.item(0).value = parseFloat(dakhauhao.item(0).value) + parseFloat(khauhao.item(0).value);		
		
		var giatriconlai = document.getElementsByName("giatriconlai_" +  i);
		giatriconlai.item(0).value = parseFloat(tonggiatri.item(0).value) - parseFloat(khauhaoluyke.item(0).value);
		
		Dinhdang(khauhaoluyke.item(0));
		Dinhdang(khauhao.item(0));
		Dinhdang(giatriconlai.item(0));
	}
	
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
	 
	 	element.value=DinhDangTien(element.value);
	 	if(element.value== '' )
	 	{
	 		element.value= '';
	 	}
	 	
	 }

	function checkedAll() 
	{
	 	var chonALL = document.getElementById("chonALL");
	 	var taisanIds = document.getElementsByName("chbox");
	 	
	 	if(chonALL.checked == true)
	 	{
	 		for (var i = 0; i < taisanIds.length; i++) 
		 	{
	 			taisanIds.item(i).checked = true;
		 	}
	 	}
	 	else
	 	{
	 		for (var i = 0; i < taisanIds.length; i++) 
		 	{
	 			taisanIds.item(i).checked = false;
		 	}
	 	}
	}
	 
	function saveform()
	{
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
	 	document.forms['khts'].action.value='save';
	    document.forms['khts'].submit();
	}
	
	function submitform()
	{
	 	document.forms['khts'].action.value='submit';
	    document.forms['khts'].submit();
	}
	 
</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="khts" method="post" action="../../ErpKhauhaotaisanSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<input type="hidden" name="flag" value='<%= khts.getFlag() %>' >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <TR height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý tài sản &gt; Khấu hao tài sản &gt; Khấu hao</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></TR>
						</TABLE>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR ><TD >
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR class = "tbdarkrow">
						<TD width="30" align="left"><A href="../../ErpKhauhaotaisanSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left" >
					    <div id="btnSave"><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
					    </div></TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
				</TABLE>
			</TD></TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= khts.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Chọn tháng khấu hao tài sản </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

		                  <TR>
		                        <TD class="plainlabel" valign="middle" width="10%" >Năm </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                        	<select name="nam" onchange="submitform();" style="width: 200px;">
		                            	<option value=""></option>
		                            	<%
			                        		if(namRs != null)
			                        		{
			                        			while(namRs.next())
			                        			{  
			                        			if( namRs.getString("nam").equals(khts.getNam())){ %>
			                        				<option value="<%= namRs.getString("nam") %>" selected="selected" ><%= namRs.getString("namTen") %></option>
			                        			<%}else { %>
			                        				<option value="<%= namRs.getString("nam") %>" ><%= namRs.getString("namTen") %></option>
			                        		 <% } } namRs.close();
			                        		}
			                        	%>
		                            </select> 
		                        </TD>                
		                  </TR>

                           <TR>
		                        <TD class="plainlabel" valign="middle" width="10%" >Tháng </TD>
		                        <TD class="plainlabel" valign="middle" width="220px" >
		                            <select name="thang" onchange="submitform();" style="width: 200px;">
		                            	<option value=""></option>
		                            	<%
			                        		if(thangRs != null)
			                        		{
			                        			while(thangRs.next())
			                        			{  
			                        			if( thangRs.getString("thang").equals(khts.getThang())){ %>
			                        				<option value="<%= thangRs.getString("thang") %>" selected="selected" ><%= thangRs.getString("thangTen") %></option>
			                        			<%}else { %>
			                        				<option value="<%= thangRs.getString("thang") %>" ><%= thangRs.getString("thangTen") %></option>
			                        		 <% } } thangRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>   
		                  </TR>
		                  
<!--                            <TR> -->
<!-- 		                        <TD class="plainlabel" valign="middle" width="10%" >Nhóm tài sản </TD> -->
<!-- 		                        <TD class="plainlabel" valign="middle" width="220px" > -->
<!-- 		                            <select name="ntsId" onchange="submitform();" style="width: 200px;"> -->
<!-- 		                            	<option value="" ></option> -->
<%-- 		                            	<% --%>
<!-- 			                        		if(nhomRs != null) -->
<!-- 			                        		{ -->
<!-- 			                        			while(nhomRs.next()) -->
<!-- 			                        			{   -->
<!-- 			                        			if( nhomRs.getString("ntsId").equals(khts.getNtsId())){ %> -->
<%-- 			                        				<option value="<%= nhomRs.getString("ntsId") %>" selected="selected" ><%= nhomRs.getString("diengiai") %></option> --%>
<%-- 			                        			<%}else { %> --%>
<%-- 			                        				<option value="<%= nhomRs.getString("ntsId") %>" ><%= nhomRs.getString("diengiai") %></option> --%>
<%-- 			                        		 <% } } nhomRs.close(); --%>
<!-- 			                        		} -->
<!-- 			                        	%> -->
<!-- 		                            </select> -->
<!-- 		                        </TD>    -->
<!-- 		                  </TR> -->

 							 <TR>
		                        <TD class="plainlabel">Số chứng từ </TD>
											  <TD class="plainlabel" >                               
                                                 <input type="text" id="soChungTu" name="soChungTu" value="<%= khts.getSoChungTu() %>"  /> 
                            				  </TD>              
		                  </TR>
		                   <TR>
		                        <TD class="plainlabel">Diễn giải chứng từ </TD>
											  <TD class="plainlabel" >                               
                                                 <input type="text" id="dienGiaiCT" name="dienGiaiCT" value="<%= khts.getDienGiaiCT() %>"  /> 
                            				  </TD>              
		                  </TR>
		                  
		               	</TABLE>
		        		</FIELDSET>
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Chọn tài sản khấu hao </LEGEND>
						<TABLE class="tabledetail" border="0" width="100%" cellpadding="6" cellspacing="1">
							<TR class="tbheader">
								<TH width="20%" align="center"  >Mã và tên tài sản</TH>
								<TH width="20%" align="center"  >Loại tài sản</TH>
								<TH width="10%" align="center"  >Tổng giá trị</TH>
								<TH width="5%" align="center"  >Lần thứ</TH>												
								<TH width="12%" align="center"  >Khấu hao</TH>
								<TH width="12%" align="center" >Lũy kế</TH>
								<TH width="12%" align="center" >Còn lại</TH>
								<TH width="5%" align="center"  >Chọn
								</TH>
							</TR>
<% 
								int m = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								khauhaoList= khts.getKhauhaoList();
								if(khauhaoList!=null){
										for (m=0; m<khauhaoList.size();m++)
										{
										IGiaTriKhauHao giaTriKhauHao= khauhaoList.get(m);
									
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
									  <%} else {%>
											<TR class= <%= darkrow%> >
									  <%} %>
												<TD align = "left"><%= giaTriKhauHao.getDiengiai()%> 

												</TD>
												<TD align = "left"><%= giaTriKhauHao.getLoaiTaiSan()%> </TD>
												<TD align = "right"><%= formatter.format(Double.parseDouble(giaTriKhauHao.getGiatriconlaidukien())+Double.parseDouble(giaTriKhauHao.getKhauhaoluykedukien())) %> </TD>
												<TD align = "center"><%= giaTriKhauHao.getThangKhauHaoThucTe() %> </TD>
												<TD align = "center" style="cursor: pointer;" >
												<input value="<%=formatter.format(Double.parseDouble(giaTriKhauHao.getKhauhaoluykedukien())-Double.parseDouble(giaTriKhauHao.getKhauhaodukien())) %>" name = "dakhauhao_<%= giaTriKhauHao.getMataisan() %>" type="hidden"  > 
												<input value="<%= formatter.format(Double.parseDouble(giaTriKhauHao.getGiatriconlaidukien())+Double.parseDouble(giaTriKhauHao.getKhauhaoluykedukien())) %>" name = "tonggiatri_<%= giaTriKhauHao.getMataisan() %>" type="hidden"  > 
												<input value="<%= giaTriKhauHao.getThangKhauHaoTrenHeThong() %>" name = "thangthu_<%=giaTriKhauHao.getMataisan()%>" type="hidden"  >
												<input value="<%= formatter.format(Double.parseDouble(giaTriKhauHao.getKhauhaodukien())) %>" name="khauhao_<%=giaTriKhauHao.getMataisan()%>" type="text" onkeypress="return keypress(event);" style="width: 100%; text-align:right;" onChange = "calculation(<%= giaTriKhauHao.getMataisan() %>);">  </TD>
												<TD align = "center"> <input value="<%= formatter.format(Math.round(Double.parseDouble(giaTriKhauHao.getKhauhaoluykedukien()))) %>" name="khauhaoluyke_<%= giaTriKhauHao.getMataisan() %>" type="text" style="width: 100%; text-align:right;" readonly="readonly">   </TD>
												<TD align = "center"> <input value="<%= formatter.format(Math.round(Double.parseDouble(giaTriKhauHao.getGiatriconlaidukien()))) %>" name="giatriconlai_<%= giaTriKhauHao.getMataisan() %>" type="text" style="width: 100%; text-align:right;" readonly="readonly">  </TD>
												<TD align = "center">
													<INPUT TYPE = "checkbox" checked="checked" NAME = "matscd" value="<%= giaTriKhauHao.getMataisan() %>">							`					`							
												</TD>
											</TR>										  
						<%			 	
									}
								}khauhaoList.clear(); %>
																	
												
									</TABLE>
								</FIELDSET>
			
				</TD>
			</TR>
		</TABLE>
	</TD>
	</TR>
	</TABLE>
</form>
</BODY>
</HTML>
<%
if(khts!=null)khts.DBClose();
	
	}%>
