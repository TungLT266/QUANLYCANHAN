<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.distributor.beans.phanbogiaonhan.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>


<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 
		int[] quyen = new  int[6];
		quyen = util.Getquyen("PhanbognUpdateSvl","",userId);%>

<% IPhanbogiaonhan nvgnBean = (IPhanbogiaonhan)session.getAttribute("nvgnBean"); %>
<% ResultSet nvgnRs = (ResultSet)nvgnBean.getNvgnRs(); %>
<% ResultSet khRs = (ResultSet)nvgnBean.getKhRs(); %>
<% ResultSet ddhRs = (ResultSet)nvgnBean.getDdhRs(); %>
<% ResultSet nvgn_newRs = (ResultSet)nvgnBean.getNvgn_newRs(); %>
<% ResultSet tinhthanhRs = (ResultSet)nvgnBean.getTinhthanhRs(); %>
<% ResultSet quanhuyenRs = (ResultSet)nvgnBean.getQuanhuyenRs(); %>

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
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<META http-equiv="Content-Style-Type" content="text/css">
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
		
	});
</script>
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
	function submitform()
	{
		document.forms['nvgnForm'].action.value='submitForm';
	   	document.forms['nvgnForm'].submit();
	}
	
	function saveform()
	{	   
				 
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		 
		document.forms['nvgnForm'].action.value='save';
	    document.forms['nvgnForm'].submit();
	}
	function checkedAll_1()
	{
		var chkAll1 = document.getElementById("checkAll1");
		 var khIds = document.getElementsByName("khId");
		 
		 if(chkAll1.checked)
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = false;
			 }
		 }
		 //document.forms['nvgnForm'].submit();
	}
	function checkedAll_2()
	{
		var chkAll2 = document.getElementById("checkAll2");
		 var ddhIds = document.getElementsByName("ddhId");
		 
		 if(chkAll2.checked)
		 {
			 for(i = 0; i < ddhIds.length; i++)
			 {
				 ddhIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < ddhIds.length; i++)
			 {
				 ddhIds.item(i).checked = false;
			 }
		 }
	}
</SCRIPT>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

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

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="nvgnForm" method="post" action="../../PhanbognUpdateSvl" >
<input type="hidden" name="nppId" id= "nppId" value='<%= nvgnBean.getNppId() %>'>
<input type="hidden" name="action" value='1'>
<input name="userId" type="hidden" value='<%=userId %>' size="30">
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:100%%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Phân bổ ĐH cho giao nhận > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng  <%= nvgnBean.getNppTen() %> &nbsp; 
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../PhanbogiaonhanSvl?userId=<%=userId %>">		 		
	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A>
        <label id="btnSave">
        <A href="javascript:saveform()">
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
        	</label>
    </div>
    
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle">Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1"><%= nvgnBean.getMessage() %></textarea>
		         <% nvgnBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Thông tin phân bổ giao nhận </legend>       	
        <div style="float:none; width:100%" align="left">
            <table width="100%" cellspacing="0" cellpadding="6px">
            	<tr>
            		<TD width="100px" class="plainlabel">Ngày phân bổ <FONT class="erroralert"> *</FONT></TD>
	                <TD width="250px" class="plainlabel">
	                    <INPUT class="days" name="ngay" id="ngay" type="text" value="<%= nvgnBean.getNgay()%>"  >
	              	</TD>
	              	<TD width="100px" class="plainlabel">Đến ngày <FONT class="erroralert"> *</FONT></TD>
	                <TD width="250px" class="plainlabel">
	                    <INPUT class="days" name="denngay" id="denngay" type="text" value="<%= nvgnBean.getDenngay() %>"  >
	              	</TD>
            	</tr>
            	<tr>
            		<TD class="plainlabel">Diễn giải</TD>
	                <TD class="plainlabel" colspan="3" >
	                    <INPUT name="diengiai" id="diengiai" type="text" value="<%= nvgnBean.getDiengiai()%>" >
	              	</TD>
            	</tr>
                <TR>
                    <TD class="plainlabel" > Nhân viên giao nhận <FONT class="erroralert"> *</FONT></TD>
                    <TD class="plainlabel" colspan="3" >
                    	<select name="nvgnId" onchange="submitform();">
						<option value="" selected></option>
							<% try{while(nvgnRs.next()){ 
						    	if(nvgnRs.getString("pk_seq").equals(nvgnBean.getNvgnId())){ %>
						      		<option value='<%= nvgnRs.getString("pk_seq") %>' selected><%= nvgnRs.getString("ten") %></option>
						      	<%}else{ %>
						     		<option value='<%= nvgnRs.getString("pk_seq") %>'><%= nvgnRs.getString("ten") %></option>
						     <%}}}catch(java.sql.SQLException e){} %>	   
						</select>    
					</TD>                                                                                   	                               
                </TR>
                <TR>
                    <TD class="plainlabel" > Nhân viên giao nhận thay thế <FONT class="erroralert"> *</FONT></TD>
                    <TD class="plainlabel" colspan="3">
                    	<select name="nvgn_newId" >
						<option value="" selected></option>
							<%if(nvgn_newRs != null)
							try{while(nvgn_newRs.next()){ 
						    	if(nvgn_newRs.getString("pk_seq").equals(nvgnBean.getNvgn_newId())){ %>
						      		<option value='<%= nvgn_newRs.getString("pk_seq") %>' selected><%= nvgn_newRs.getString("ten") %></option>
						      	<%}else{ %>
						     		<option value='<%= nvgn_newRs.getString("pk_seq") %>'><%= nvgn_newRs.getString("ten") %></option>
						     <%}}}catch(java.sql.SQLException e){} %>	   
						</select>    
					</TD>
                </TR>
                <TR>
                    <TD class="plainlabel" > Tỉnh thành</TD>
                    <TD class="plainlabel" colspan="3" >
                    	<select name="tinhthanhId" onChange = "submitform();">
						<option value="" selected></option>
							<% try{while(tinhthanhRs.next()){ 
						    	if(tinhthanhRs.getString("pk_seq").equals(nvgnBean.getTinhthanhId())){ %>
						      		<option value='<%= tinhthanhRs.getString("pk_seq") %>' selected><%= tinhthanhRs.getString("ten") %></option>
						      	<%}else{ %>
						     		<option value='<%= tinhthanhRs.getString("pk_seq") %>'><%= tinhthanhRs.getString("ten") %></option>
						     <%}}}catch(java.sql.SQLException e){} %>	   
						</select>    
					</TD>
                </TR>
                <%if(quanhuyenRs != null){ %>
                <TR>
                    <TD class="plainlabel" > Quận huyện</TD>
                    <TD class="plainlabel" colspan="3" >
                    	<select name="quanhuyenId" onChange = "submitform();"  >
						<option value="" selected></option>
							<% try{while(quanhuyenRs.next()){ 
						    	if(quanhuyenRs.getString("pk_seq").equals(nvgnBean.getQuanhuyenId())){ %>
						      		<option value='<%= quanhuyenRs.getString("pk_seq") %>' selected><%= quanhuyenRs.getString("ten") %></option>
						      	<%}else{ %>
						     		<option value='<%= quanhuyenRs.getString("pk_seq") %>'><%= quanhuyenRs.getString("ten") %></option>
						     <%}}}catch(java.sql.SQLException e){} %>	   
						</select>    
					</TD>
                </TR>
                <%} %>
                <Tr style="display: none;" >
                	<TD class="plainlabel" colspan='4'>Hoạt động
                        <input name="trangthai" type="checkbox" value="1" checked readonly="readonly" >
                    </TD>  
                </Tr>
             </table>  
         </div>      
    </fieldset>	
    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			<TR>
				<TD>
					<FIELDSET>
						<LEGEND class="legendtitle" style="color: black">Chọn khách hàng</LEGEND>
						<TABLE border="0" width="100%" cellpadding="4"
							cellspacing="1">
							<TR class="tbheader">
								<TH width="30%">Mã khách hàng</TH>
								<TH width="60%">Tên khách hàng</TH>
								<TH width="10%">Chọn <input type="checkbox" id="checkAll1" onchange="checkedAll_1();" ></TH>
							</TR>
							<%
				try
				{
					String lightrow = "tblightrow";
					String darkrow = "tbdarkrow";
					int m = 0;
					if (!(khRs == null))
					{
						while (khRs.next())
						{
							if (m % 2 != 0)
							{
							%>
							<TR class=<%=lightrow%>>
								<%
									} else{
								%>

							<TR class=<%=darkrow%>>
								<%
									}
								%>

								<TD><%=khRs.getString("pk_seq")%></TD>
								<TD><div align="left"><%=khRs.getString("ten")%>
									</div></TD>
								<TD>
									<div align="center">
										<%
										if (nvgnBean.getKhId().indexOf(khRs.getString("pk_Seq")) >=0  ) {%>
										<input type="checkbox" name="khId" value='<%=khRs.getString("pk_Seq")%>' checked>
										<%
											} else{
										%>
										<input type="checkbox" name="khId" value='<%=khRs.getString("pk_Seq")%>' >
										<%
											}
										%>

									</div>
								</TD>
							</TR>
							<%
								m++;
										}
						khRs.close();
									}
								} catch (java.sql.SQLException e)
								{
									e.printStackTrace();
								}
							%>

						</TABLE>
					</FIELDSET>
				</TD>
			</TR>
		</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" style="display: none;" >
			<TR>
				<TD>
					<FIELDSET>
						<LEGEND class="legendtitle" style="color: black">Chọn đơn đặt hàng</LEGEND>
						<TABLE border="0" width="100%" cellpadding="4"
							cellspacing="1">
							<TR class="tbheader">
								<TH width="30%">Mã đơn hàng</TH>
								<TH width="60%">Tên đơn hàng</TH>
								<TH width="10%">Chọn <input type="checkbox" id="checkAll2" onchange="checkedAll_2();" ></TH>
							</TR>
							<%
				try
				{
					String lightrow = "tblightrow";
					String darkrow = "tbdarkrow";
					int m = 0;
					if (!(ddhRs == null))
					{
						while (ddhRs.next())
						{
							if (m % 2 != 0)
							{
							%>
							<TR class=<%=lightrow%>>
								<%
									} else{
								%>

							<TR class=<%=darkrow%>>
								<%
									}
								%>

								<TD><%=ddhRs.getString("pk_seq")%></TD>
								<TD><div align="left"><%=ddhRs.getString("ten")%>
									</div></TD>
								<TD>
									<div align="center">
										<%
										if (nvgnBean.getDdhId().indexOf(ddhRs.getString("pk_Seq")) >=0  ) {%>
										<input type="checkbox" name="ddhId" value='<%=ddhRs.getString("pk_Seq")%>' checked>
										<%
											} else{
										%>
										<input type="checkbox" name="ddhId" value='<%=ddhRs.getString("pk_Seq")%>'>
										<%
											}
										%>

									</div>
								</TD>
							</TR>
							<%
								m++;
										}
						ddhRs.close();
									}
								} catch (java.sql.SQLException e)
								{
									e.printStackTrace();
								}
							%>

						</TABLE>
					</FIELDSET>
				</TD>
			</TR>
		</TABLE>
    </div>
</div>
</form>
</body>
</html>
<% 	


	try{
		if(nvgnRs != null)
			nvgnRs.close();
		if(nvgn_newRs != null)
			nvgn_newRs.close();
		if(tinhthanhRs != null)
			tinhthanhRs.close();
		if(quanhuyenRs != null)
			quanhuyenRs.close();
		if(nvgnBean!=null){
		nvgnBean.DBclose();
		nvgnBean=null;
		}
	}
	catch (SQLException e) {}
%>
<%}%>