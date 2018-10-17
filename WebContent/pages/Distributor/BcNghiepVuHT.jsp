<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.distributor.beans.reports.IBcNghiepVuHT"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	IBcNghiepVuHT obj = (IBcNghiepVuHT) session.getAttribute("obj"); %>
<% 	ResultSet khoRs = (ResultSet)obj.getKhoRs(); %>
<% 	ResultSet lspRs = (ResultSet)obj.getLoaiSpRs(); %>
<% 	ResultSet spRs = (ResultSet)obj.getSpRs(); %>
<% 	//ResultSet doituongRs = (ResultSet)obj.getDoituongRs(); %>
<% 	
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	url = util.getUrl("BcNghiepVuHTSvl","");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	
$(document).ready(function() {
	$(".days").datepicker({
		changeMonth : true,
		changeYear : true
	});
	$(".button").hover(function() {
		$(".button img").animate({
			top : "-10px"
		}, 200).animate({
			top : "-4px"
		}, 200) // first jump
		.animate({
			top : "-7px"
		}, 100).animate({
			top : "-4px"
		}, 100) // second jump
		.animate({
			top : "-6px"
		}, 100).animate({
			top : "-4px"
		}, 100); // the last jump
	});
});
</script>

<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">

function clearform()
{   
	document.forms["ckParkForm"].Sdays.value="";
	document.forms["ckParkForm"].Edays.value="";
	document.forms["ckParkForm"].khId.value="";
	document.forms["ckParkForm"].spId.value="";
	document.forms["ckParkForm"].ddkdId.value="";
	document.forms["ckParkForm"].kbhId.value="";
   	document.forms["ckParkForm"].submit();
}

function submitform()
{   
	document.forms['ckParkForm'].action.value='';
	document.forms["ckParkForm"].submit();
}

function xuatExcel()
{
	document.forms['ckParkForm'].action.value= 'excel';
	document.forms['ckParkForm'].submit();	
}

function search()
{
	document.forms['ckParkForm'].action.value= 'search';
	document.forms['ckParkForm'].submit();	
}
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
		
	});
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="ckParkForm" method="post" action="../../BcNghiepVuHTSvl">
		<input type="hidden" name="action" value=''>
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden"name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">
		<% obj.setNextSplittings(); %>
		<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
		
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1"  style="width: 100% ; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Nghiệp vụ Xuất Nhập</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >
						   	<TR>
		                        <TD class="plainlabel" valign="middle" >Kho </TD>
		                        <TD class="plainlabel" valign="middle" >
		                            <select name="khoId" id="khoId" onchange="locsanpham()" class="select2" style="width: 600px"  >
		                            	<option value="">Tất cả</option>
		                            	<%
			                        		if(khoRs != null)
			                        		{
			                        			while(khoRs.next())
			                        			{  
			                        			if( khoRs.getString("pk_seq").equals(obj.getKhoId())){ %>
			                        				<option value="<%= khoRs.getString("pk_seq") %>" selected="selected" ><%= khoRs.getString("khoTen") %></option>
			                        			<%}else { %>
			                        				<option value="<%= khoRs.getString("pk_seq") %>" ><%= khoRs.getString("khoTen") %></option>
			                        		 <% } } khoRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>                        
		                    </TR>
		                    <TR>
		                        <TD class="plainlabel" valign="middle" >Loại sản phẩm </TD>
		                        <TD class="plainlabel" valign="middle" >
		                        	<select name = "loaisanpham"   id="loaisanpham" onchange="locsanpham()" class="select2" style="width: 600px" multiple="multiple" >
		                         		<option value="" >Tất cả</option>
			                        	<%
			                        		if(lspRs != null)
			                        		{
			                        			try
			                        			{
			                        			while(lspRs.next())
			                        			{  
			                        			if( obj.getLoaiSpId().contains(lspRs.getString("pk_seq"))  ){ %>
			                        				<option value="<%= lspRs.getString("pk_seq") %>" selected="selected" ><%= lspRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= lspRs.getString("pk_seq") %>" ><%= lspRs.getString("ten") %></option>
			                        		 <% } } lspRs.close();} catch(Exception ex){}
			                        		}
			                        	%>
			                    	</select>                            
		                        </TD>                        
		                    </TR> 
		                    
		                    <TR>
		                        <TD class="plainlabel" valign="middle" >Chọn sản phẩm </TD>
		                        <TD class="plainlabel" valign="middle" >
		                         	<select name = "spIds" id="spIds" class="select2" style="width: 600px" multiple="multiple" >
		                         		<option value="" >Tất cả</option>
			                        	<%
			                        		if(spRs != null)
			                        		{
			                        			try
			                        			{
			                        			while(spRs.next())
			                        			{  
			                        			if( obj.getSpId().contains(spRs.getString("pk_seq"))  ){ %>
			                        				<option value="<%= spRs.getString("pk_seq") %>" selected="selected" ><%= spRs.getString("ma") + ", " + spRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= spRs.getString("pk_seq") %>" ><%= spRs.getString("ma") + ", " + spRs.getString("ten") %></option>
			                        		 <% } } spRs.close();} catch(Exception ex){}
			                        		}
			                        	%>
			                    	</select>                
		                        </TD>                        
		                    </TR>													   
							<TR>
								<TD class="plainlabel" colspan="6">
									<!-- <a class="button2" href="javascript:search();"> <img style="top: -4px;" src="../images/button.png" alt=""> Tìm kiếm</a>&nbsp;&nbsp;&nbsp;&nbsp; -->
									<a class="button2" href="javascript:xuatExcel();"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất dữ liệu </a>&nbsp;&nbsp;&nbsp;&nbsp;
								</TD>
							</TR>
							</TABLE>
						</div>
			</div>
			</fieldset>
		</div>
		
		
		
		<%-- <TABLE width="100%" cellpadding="0" cellspacing="1">
			    	<TR>
						<TD align="left" >
							<FIELDSET><LEGEND class="legendtitle">&nbsp;Doanh số&nbsp;&nbsp;&nbsp;</LEGEND>
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellpadding="6">
											<TR class="tbheader">
												<TH width="5%" >STT</TH>
												<TH width="10%" >Nghiệp vụ</TH>
												<TH width="10%">Kênh Bán Hàng</TH>
												<TH width="10%" >Kho</TH>
											    <TH width="10%" >Số Chứng Từ</TH>
											    <TH width="10%">Ngày Chứng Từ</TH>											  
											    <TH width="35%">Sản phẩm</TH>
											    <TH width="10%">Số Lượng</TH>
											    <TH width="10%">Tác Vụ</TH>
										  	</TR>
										<% 						
										int m = 0;
										String lightrow = "tblightrow";
										String darkrow = "tbdarkrow";
										try{
											if(hdRs != null)
											while (hdRs.next())
											{
												String nghiepvu =hdRs.getString("nghiepVu");
												double total_DoanhSo=0;
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
												<%} else {%>
													<TR class= <%= darkrow%> >
												<%}%>
													<TD align="left"><%=m+1%></TD>
													<TD align="left"><%=hdRs.getString("nghiepVu")%></TD>
													<TD align="left"><%=hdRs.getString("kbhTEN")%></TD>
													<TD align="left"><%=hdRs.getString("khoTEN")%></TD>
													 <TD align="left"><%=hdRs.getString("ctID")%></TD>
													 <TD align="left"><%=hdRs.getString("ngayCT")%></TD>
													 <TD align="left"><%=hdRs.getString("spMa")+" - "+hdRs.getString("spTEN")  %></TD>
													<TD align="right"><%=formatter.format(hdRs.getDouble("SoLuong") )%></TD>
													
													<TD align="center"> 
                                        <%if(nghiepvu.equals("Xuất Bán")){ %>															
				                   			<A href = "../../DonhangUpdateSvl?userId=<%=userId%>&display=<%=hdRs.getString("ctID") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
				                   		<%}else if(nghiepvu.equals("Xuất chuyển nội bộ")){ %>
												<A href = "../../ErpChuyenkhoNppUpdateSvl?userId=<%=userId%>&display=<%=hdRs.getString("ctID") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>                                        			                   			
				                   			<%}else if(nghiepvu.equals("Xuất ETC")){ %>
				                   				<A href = "../../ErpDonhangNppETCUpdateSvl?userId=<%=userId%>&display=<%=hdRs.getString("ctID") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
				                   			<%}else if(nghiepvu.equals("Xuất ETC Đối Tác")){ %>
														<A href = "../../ErpDondathangDoitacUpdateSvl?userId=<%=userId%>&display=<%=hdRs.getString("ctID") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>				                   			
				                   			<%}else if(nghiepvu.equals("Trả hàng NCC")){ %>
				                   				<A href = "../../DontrahangUpdateSvl?userId=<%=userId%>&display=<%=hdRs.getString("ctID") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
				                   			<%}else if(nghiepvu.equals("Điều chỉnh tồn kho")){ %>
				                   			<A href = "../../DieuchinhtonkhoUpdateSvl?userId=<%=userId%>&display=<%=hdRs.getString("ctID") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
				                   			<%}else if(nghiepvu.equals("Xuất kho khác")){ %>
				                   			<A href = "../../ErpChuyenKhoUpdateSvl?userId=<%=userId%>&display=<%=hdRs.getString("ctID") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
				                   			<%}else if(nghiepvu.equals("Xuất hàng KM")){ %>
					                   			<A href = "../../DonhangUpdateSvl?userId=<%=userId%>&display=<%=hdRs.getString("ctID") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
				                   			<%} %>
				                    </TD>													
												</TR>
												<%m++; } 
											}catch(Exception er){er.printStackTrace();} %>
								
											<TR>	
												<TD height="" colspan="11" align="center" class="tbfooter">&nbsp;</TD>
											</TR>
									</TABLE>
								</TD>
							</TR>
						</TABLE>
						</FIELDSET>
					</TD>
				</TR>
		</TABLE> --%>
	</div>
	</form>
	<%
		if(obj!=null){obj.closeDB();
		obj = null;}
		session.setAttribute("obj", null);
	%>
</body>
</html>