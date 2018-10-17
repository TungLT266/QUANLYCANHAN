 
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.erp.beans.congdoansanxuatgiay.ITieuchikiemdinh"%>
<%@page import="geso.traphaco.erp.beans.congdoansanxuatgiay.IErpCongDoanSanXuatGiay"%>
<%@page import="geso.traphaco.erp.beans.congdoansanxuatgiay.ITaisan"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.ResultSet"%>
 
<%@page import="java.sql.SQLException"%>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility  util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IErpCongDoanSanXuatGiay obj =(IErpCongDoanSanXuatGiay)session.getAttribute("cdsxBean");
	List<ITieuchikiemdinh> tckdList = (List<ITieuchikiemdinh>)obj.getTieuchikiemdinhList();
	ResultSet nmRs = (ResultSet)obj.getNhamayRs();
	ResultSet tbRs = (ResultSet)obj.getThietbiRs();
	ResultSet rsdmVt=(ResultSet)obj.getRsDmvt();
	ResultSet rsSp=(ResultSet)obj.getRsSp();
	ResultSet phongbanRs = (ResultSet)obj.getPhongbanRs();
	ResultSet giaidoanRs = (ResultSet)obj.getGiaidoanRs();
	String[] sttpb = obj.getSttpb();
	String[] phongbanIds = obj.getPhongbanId();
	String[] giaidoanIds = obj.getGiaidoanId();
	List<ITaisan> tsList = (List<ITaisan>)obj.getLts();
	ResultSet loaimauinSxRs = (ResultSet)obj.getLoaimauinSxRs();
	ResultSet LhsknRs = obj.getLhsknRs();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

<script>
//perform JavaScript after the document is scriptable.

$(function() {
 
	$('#tab').tabs();
});
</script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<SCRIPT language="JavaScript" type="text/javascript">
	function hienthilhskn() {
		if(document.getElementById("yckn").checked == true){
			document.getElementById("lhskn1").style.display = "block";
			document.getElementById("lhskn2").style.display = "block";
		} else {
			document.getElementById("lhskn1").style.display = "none";
			document.getElementById("lhskn2").style.display = "none";
		}
	}

	function submitform()
	{
	    document.forms["cdsx"].submit();
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
	
	function save()
	{
	
	  document.forms["cdsx"].action.value = "save";
	  document.forms["cdsx"].submit(); 
    }
	
	function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var spIds = document.getElementsByName("spIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	 $(document).on('focus','.autocomplete_txt',function(){
			$(this).autocomplete({
				source:function(request,response){
					$.ajax({
						url:"../../ErpCongDoanSanXuatGiayUpdateSvl",
						type:"POST",
						data:{
							term:request.term,
							action:'autoloadts'
						},
						dataType:"json",
						success:function(data){
							response( $.map( data, function( item ) {
							 	var code = item.split("|");
								return {
									label: code[1],
									value: code[1],
									data : item
								}
							}));
						}
					})
				},
			    focus: function() {
		        	 return false;
			    },
				change: function( event , ui ) {
					if(ui.item==null){
						id_arr = $(this).attr('id');
			      		id = id_arr.split("_");
			      		elementId = id[id.length-1];
						$('#tentaisan_'+elementId).val("");
						$('#pk_seq_'+elementId).val("");
					}
		        },
		        select: function(event,ui){
		        	var names = ui.item.data.split("|");
		        	id_arr = $(this).attr('id');
		      		id = id_arr.split("_");
		      		elementId = id[id.length-1];
					$('#tentaisan_'+elementId).val(names[2]);
					$('#pk_seq_'+elementId).val(names[0]);
		        }
			})
		})
</SCRIPT>
<style>
.ui-autocomplete {
    max-height: 100px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
    /* add padding to account for vertical scrollbar */
    padding-right: 20px;
}

html .ui-autocomplete {
    height: 100px;
}
</style>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="cdsx" method="post" action="../../ErpCongDoanSanXuatGiayUpdateSvl">
		<input type="hidden" name="userId" value='<%= userId %>'> <input type="hidden" name="action" value="0">
		<input type="hidden" name="id" value='<%= obj.getId() %>'>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản xuất > Công đoạn sản xuất > Cập Nhật</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A href="../../ErpCongDoanSanXuatGiaySvl?userId=<%= userId%>"><img src="../images/Back30.png"
												alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript: save()"><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1" style="border-style: outset"></A>
											</div>
										</TD>
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>
									<textarea name="dataerror" style="width: 100%; color: #F00; font-weight: bold" style="width: 100% ; color:#F00 ; font-weight:bold"
										readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
								</FIELDSET>
							</TD>
						</tr>
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông tin công đoạn sản xuất </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD class="plainlabel" valign="middle" width="20%">Diễn giải <FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel" style="width: 300px"><input type="text" name="diengiai" style="width:250px" value="<%= obj.getDiengiai() %>"></TD>
											
											<TD class="plainlabel" colspan="2">
												<input type="checkbox" <%=obj.getYckn().equals("1") ? "checked" : "" %> name="yckn" id="yckn" value="1" onchange="hienthilhskn();">Yêu cầu kiểm nghiệm
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="20%">Số nhân công <FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel">
												<input type="text" name="sonhancong" value="<%= obj.getSonhancong() %>" style="width:250px;text-align: right;" onkeypress="return keypress(event);">
											</TD>
											
											<TD class="plainlabel" valign="middle" width="20%" ><div <%=obj.getYckn().equals("1") ? "" : "style=\"display: none;\"" %> id="lhskn1">Chọn loại hồ sơ kiểm nghiệm</div></TD>   
					                        <TD class="plainlabel" valign="middle">
					                        	<div <%=obj.getYckn().equals("1") ? "" : "style=\"display: none;\"" %> id="lhskn2">
						                        	<select multiple="multiple" name="loaihosokn" style="width: 250px" class="select2">
														<%if(LhsknRs != null){
															while(LhsknRs.next()){ 
																if(obj.getLhsknList().indexOf(LhsknRs.getString("pk_seq")) >= 0){ %>
																	<option value="<%=LhsknRs.getString("pk_seq") %>" selected><%=LhsknRs.getString("ten") %></option>
																<%} else { %>
																	<option value="<%=LhsknRs.getString("pk_seq") %>" ><%=LhsknRs.getString("ten") %></option>
																<%}
															}
														} %>
						                        	</select>
					                        	</div>
					                        </TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="20%" >Phân xưởng <FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel" colspan="3">
												<select name="nmIds" style="width: 450px" class="select2">
													<option value=""></option>
													<% if(nmRs != null) {
			                        					while(nmRs.next()) {  
			                        						if( nmRs.getString("pk_seq").equals(obj.getNhamayIds())){ %>
																<option value="<%= nmRs.getString("pk_seq") %>" selected="selected"><%= nmRs.getString("nhamayTen") %></option>
															<%}else { %>
																<option value="<%= nmRs.getString("pk_seq") %>"><%= nmRs.getString("nhamayTen") %></option>
															<% }
		                        						}
			                        					nmRs.close(); 
			                        				} %>
												</select>
											</TD>
										</TR>
										<%-- <TR>
										<TD class="plainlabel"  width="130px">Kiểm định định lượng</TD>
											<TD class="plainlabel" valign="middle">
											<%if(obj.getDinhluong().equals("1")){ %>
											<input type="checkbox" name="dinhluong" value="1" checked="checked" >
											<%}else { %>
											<input type="checkbox" name="dinhluong" value="1">
											
											<%} %>
										
											</TD>
										</TR> --%>
										
										<%-- <TR>
										<TD class="plainlabel"  width="130px">Kiểm định định tính</TD>
											<TD class="plainlabel" valign="middle">
											<%if(obj.getDinhtinh().equals("1")){ %>
											<input type="checkbox" name="dinhtinh" value="1" checked="checked" >
											<%}else { %>
											<input type="checkbox" name="dinhtinh" value="1">
											
											<%} %>
										
											</TD>
										</TR> --%>
										<TR>
										<TD class="plainlabel"  width="20%">Hoạt động </TD>
										<TD class="plainlabel" colspan="3">
											<% if(obj.getTrangthai().equals("1")) { %> <input type="checkbox" name="trangthai" value="1" checked="checked"> <% } else { %> <input
												type="checkbox" name="trangthai" value="1"><i> </i> <% } %>
										</TD>
										</TR>
										<%-- <tr>
										<TD class="plainlabel"  width="130px">Loại mẫu in sản xuất</TD>
										<TD class="plainlabel">
											<select name="loaimauinSxId" id= "loaimauinSxId">
											<option value=""> Chưa có mẫu </option>
											<%
			                        		if(loaimauinSxRs != null)
			                        		{
			                        			while(loaimauinSxRs.next())
			                        			{  
			                        			if( loaimauinSxRs.getString("pk_seq").equals(obj.getLoaimauinSxId())){ %>
													<option value="<%= loaimauinSxRs.getString("pk_seq") %>" selected="selected"><%= loaimauinSxRs.getString("Ten") %></option>
													<%}else { %>
													<option value="<%= loaimauinSxRs.getString("pk_seq") %>"><%= loaimauinSxRs.getString("Ten") %></option>
													<% } } loaimauinSxRs.close(); 
			                        		}
			                        	%>
											</select>
										</TD>
										</TR> --%>
										
										
										<TR id="tab">
											<TD colspan="6">
												<ul>
													<% if(1==1){ %>
													<li><a href="#tabs-1" class="legendtitle">Giai đoạn</a></li>
													<% } %>
													<% if(obj.getDinhluong().equals("1")){%>
													<li><a href="#tabs-2" class="legendtitle">Kiểm định định lượng</a></li>
													<%} %>
													<% if(obj.getDinhtinh().equals("1")){%>
													<li><a href="#tabs-3" class="legendtitle">Kiểm định định tính</a></li>
													<%} %>
												</ul>
												<div>
													<% if(1==1){ %>
													<div id="tabs-1">
														<TABLE border="0" width="100%" cellpadding="0" cellspacing="1">
															<TR class="tbheader">
																<TH width="10%" align="center">STT</TH>
																<TH width="30%">Khu vực sản xuất</TH>
																<TH width="60%">Giai đoạn</TH>
															</TR>
															<tr>
															<% 	int count = 0;
																if(phongbanIds != null)
																{
																	for(int i = 0; i < phongbanIds.length; i++) 
																	{
																	%>
																	<TR>
																		<td><input type="text" readonly="readonly" id="sttpb" name="sttpb" style="text-align: center; width: 100%" value ="<%=i+1 %>"/></td>
																		<Td>
																		<select name="phongban" style="width: 100%" >
																			<option value = "0" ></option>
																			<% if(phongbanRs != null) 
																			{ 
																				phongbanRs.beforeFirst();
																				while(phongbanRs.next())
																				{ 
																					if(phongbanIds[i].trim().equals(phongbanRs.getString("pk_seq"))) { %>
																					<option value="<%= phongbanRs.getString("pk_seq") %>" selected><%= phongbanRs.getString("ten") %></option>
																			<% 		} else { %>
																					<option value="<%= phongbanRs.getString("pk_seq") %>" ><%= phongbanRs.getString("ten") %></option>
																			<% 	} 
																			} } %>
																	 	</select>
																		</Td>
																		<Td>
																		<select name="giaidoan" style="width: 100%" >
																			<option value = "0" ></option>
																			<% if(giaidoanRs != null) 
																			{ 
																				giaidoanRs.beforeFirst();
																				while(giaidoanRs.next())
																				{ 
																					if(giaidoanIds[i].trim().equals(giaidoanRs.getString("pk_seq"))) { %>
																					<option value="<%= giaidoanRs.getString("pk_seq") %>" selected><%= giaidoanRs.getString("ten") %></option>
																			<% 		} else { %>	
																					<option value="<%= giaidoanRs.getString("pk_seq") %>" ><%= giaidoanRs.getString("ten") %></option>
																			<% 	} 
																			} } %>
																	 	</select>
																		</Td>
																	</TR>
															<% count++; } }
															for(int i = count; i <= 15; i++ ) { %>
															<TR>
																<td><input type="text" readonly="readonly" id="sttpb" name="sttpb" style="text-align: center;" value ="<%=i + 1 %>"/>
																<Td>
																<select name="phongban" style="width: 100%" >
																	<option value = "0" ></option>
																	<% if(phongbanRs != null) 
																	{ 
																		phongbanRs.beforeFirst();
																		while(phongbanRs.next())
																		{ %>
																			<option value="<%= phongbanRs.getString("pk_seq") %>" ><%= phongbanRs.getString("ten") %></option>
																	<% 	} 
																	} %>
															 	</select>
																</Td>
																<Td>
																<select name="giaidoan" style="width: 100%" >
																	<option value = "0" ></option>
																	<% if(giaidoanRs != null) 
																	{ 
																		giaidoanRs.beforeFirst();
																		while(giaidoanRs.next())
																		{ %>
																			<option value="<%= giaidoanRs.getString("pk_seq") %>" ><%= giaidoanRs.getString("ten") %></option>
																	<% 	} 
																	} %>
															 	</select>
																</Td>
															</TR>
															<% } %>
														</TABLE>
													</div>
													<% } if(obj.getDinhluong().equals("1")){ 
													%>
													<div id="tabs-2">
														<TABLE border="0" width="100%" cellpadding="0" cellspacing="1">
															<TR class="tbheader">
																<TH width="5%" align="center">STT</TH>
																<TH width="60%">Tiêu chí</TH>
																<TH width="15%">Toán tử</TH>
																<TH width="20%">Giá trị chuẩn</TH>
															</TR>
															<% int count = 0;
																
																		for(int i = 0; i < tckdList.size(); i++) 
																		{
																			ITieuchikiemdinh tckd = tckdList.get(i);
																			
																			%>
															<TR>
																<td><input type="text" readonly="readonly" style="text-align: center;" value ="<%=i + 1 %>"/></td>
																<Td><input type="text" name="tieuchi_dinhluong" value="<%= tckd.getTieuchi() %>" style="width: 100%"></Td>
																<Td><select name="toantu">
																		<option></option>
																		<%for(int ii=0;ii<obj.getToanTu().length;ii++ ){ 
																		if(obj.getToanTu()[ii].equals(tckd.getToantu())){
																	%>
																		<option selected="selected" value="<%=obj.getToanTu()[ii]%>"><%=obj.getToanTu()[ii]%></option>
																		<%}else { %>
																		<option value="<%=obj.getToanTu()[ii]%>"><%=obj.getToanTu()[ii]%></option>
																		<%} %>
																		<%} %>
																</select></Td>
																<Td><input type="text" name="giatrichuan" value="<%= tckd.getGiatrichuan() %>" style="width: 100%; text-align: right;"
																	onkeypress="return keypress(event);"></Td>
															</TR>
															<% count++; } %>
															<% for(int i = count; i <= 15; i++ ) { %>
															<TR>
																<td><input type="text" readonly="readonly" style="text-align: center;" value ="<%=i + 1 %>"/></td>
																<Td><input type="text" name="tieuchi_dinhluong" value="" style="width: 100%"></Td>
																<Td><select name="toantu">
																		<option></option>
																		<%for(int ii=0;ii<obj.getToanTu().length;ii++ ){ %>
																		<option value="<%=obj.getToanTu()[ii]%>"><%=obj.getToanTu()[ii]%></option>
																		<%} %>
																</select></Td>
																<Td><input type="text" name="giatrichuan" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);"></Td>
															</TR>
															<% } %>
														</TABLE>
													</div>
													<%}
													  if(obj.getDinhtinh().equals("1")){ %>
													<div id="tabs-3">
														<TABLE border="0" width="100%" cellpadding="0" cellspacing="1">
															<TR class="tbheader">
																<TH width="5%" align="center">STT</TH>
																<TH width="95%">Tiêu chí</TH>
															</TR>
															<% int count = 0;
																		for(int i = 0; obj.getTieuchi_Dinhtinh()!=null &&i < obj.getTieuchi_Dinhtinh().length; i++) 
																		{
																			String tieuchi =obj.getTieuchi_Dinhtinh()[i];
																			
																			%>
															<TR>
																<td><input type="text" readonly="readonly" style="text-align: center;" value ="<%=i + 1 %>"/></td>
																<Td><input type="text" name="tieuchi_dinhtinh" value="<%= tieuchi %>" style="width: 100%"></Td>
															</TR>
															<% count++; } %>
															<% for(int i = count; i <= 15; i++ ) { %>
															<TR>
																<td><input type="text" readonly="readonly" style="text-align: center;" value ="<%=i + 1 %>"/></td>
																<Td><input type="text" name="tieuchi_dinhtinh" value="" style="width: 100%"></Td>
															</TR>
															<% } %>
														</TABLE>
													</div>
													<%} %>
												</div>
								
											</TD>
										</TR>
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
	if(nmRs != null) nmRs.close();
	if(tbRs != null) tbRs.close();

	if(rsdmVt != null) rsdmVt.close();
	if(rsSp != null) rsSp.close();
	
	if(tckdList!=null){
		tckdList.clear();
	}
	
	if(loaimauinSxRs!= null) loaimauinSxRs.close();
	
	obj.DbClose();
	session.setAttribute("cdsxBean", null) ; 
	
	obj=null;
	}%>
