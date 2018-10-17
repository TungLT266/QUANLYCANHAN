<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.khachhang.IKhachhang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>

<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IKhachhang khBean = (IKhachhang)session.getAttribute("khBean"); %>
<% ResultSet tp = (ResultSet)khBean.getTp() ;  %>
<% ResultSet qh = (ResultSet)khBean.getQh() ;  %>
<% ResultSet hch = (ResultSet)khBean.getHangcuahang(); %>
<% ResultSet kbh = (ResultSet)khBean.getKenhbanhang();  %>
<% ResultSet bgst = (ResultSet)khBean.getBgsieuthi();  %>
<% ResultSet vtch = (ResultSet)khBean.getVtcuahang();  %>
<% ResultSet lch = (ResultSet)khBean.getLoaicuahang(); %>
<% ResultSet nch = (ResultSet)khBean.getNhomcuahang();  %>
<% ResultSet mck = (ResultSet)khBean.getMucchietkhau();  %>
<% ResultSet ghcn = (ResultSet)khBean.getGhcongno();  %>
<% ResultSet nvgn = (ResultSet)khBean.getNvgnRs();  %>
<% ResultSet npp =  (ResultSet) khBean.getNhaphanphoi(); %>
<% ResultSet rsbanggiasieuthi = (ResultSet)khBean.getBangGiaST();  %>
<% ResultSet nkh_khList = (ResultSet)khBean.getNkh_khList();  %>
<% ResultSet nkh_khSelected = (ResultSet)khBean.getNkh_KhSelected();  %>
<% Hashtable<Integer, String> nkh_khIds = (Hashtable<Integer, String>)khBean.getNkh_KhIds(); %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
  
 function showElement(id)
 {
 	var element = document.getElementById(id);
 	element.style.display = "";
 }

 function hideElement(id)
 {
 	var element = document.getElementById(id);
 	element.style.display = "none";
 }

 function tongleBangGia(sel)
 {
	 if ( sel.options[sel.selectedIndex].value == "100002" ) //kenh hien dai 
	 {
	  	showElement("banggia");
	 } 
	 else 
	 {
	  	hideElement("banggia");
	 }
 }
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khForm" method="post" action="../../KhachhangUpdateTTSvl">
<input type="hidden" name="userId" value='<%=userId %>'>
<%-- <input type="hidden" name="nppId" value='<%= khBean.getNppTen() %>'> --%>
<input type="hidden" name="action" value='1'>
<INPUT name="id" type="hidden" value='<%= khBean.getId() %>' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Khách hàng > Hiển thị
	   						 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen%> &nbsp; </tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="2">
			<TR ><TD >
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR class = "tbdarkrow">
						<TD width="30" align="left"><A href="../../KhachhangTTSvl?userId=<%=userId %>"  ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
				</TABLE>
			</TD></TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>		
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width:100%" rows="1" ><%= khBean.getMessage() %></textarea>
							<%khBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET><LEGEND class="legendtitle" style="color:black">Thông tin khách hàng</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							
							<TR>
							<TD width="18%" class="plainlabel">Chi nhánh / Đối tác</TD>
									<TD colspan="6"  width="33%" class="plainlabel">
									<SELECT name="nppId" id="nppId" onChange = "submitform()">
									<option value=""> </option>
									<% if(npp != null){
										  try
										  { 
											  System.out.println("1.Id npp : " + khBean.getNppId());
											  String optionGroup = "";
											  String optionName = "";
											  int i = 0;
											  
											  while(npp.next())
											  { 
												 if(i == 0)
												 {
													 optionGroup = npp.getString("kvTen");
													 optionName = npp.getString("kvTen");
													 
													 %>
													 
													 <optgroup label="<%= optionName %>" >
												 <% }
												 
												 optionGroup = npp.getString("kvTen");
												 if(optionGroup.trim().equals(optionName.trim()))
												 { %>
													 <% if(npp.getString("nppId").equals(khBean.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 else
												 {
													 %>
													</optgroup>
													<% optionName = optionGroup; %>
													<optgroup label="<%= optionName %>" >
													<% if(npp.getString("nppId").equals(khBean.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 i++;
								     	 	  } 
											  %>
											  	</optgroup>
											  <%npp.close(); 
								     	 }catch(java.sql.SQLException e){}}%>	  
                                	</SELECT>
								</TD>
																
								</TR>
							
							<TR>
								<TD width="15%" class="plainlabel" > Tên khách hàng<FONT class="erroralert"> *</FONT></TD>
								<TD colspan = "4" class="plainlabel">
									<INPUT type="text" name="khTen" value="<%= khBean.getTen() %>" size="40"></TD>
							    <TD width="16%" class="plainlabel">Hoạt động </TD>
							    <TD width="16%" class="plainlabel">
							    		
						      		<%
						      		if(khBean.getTrangthai() ==null ){
						      			khBean.setTrangthai("0");
						      		}
						      		if (khBean.getTrangthai().equals("1")){%>
										<input name="trangthai" type="checkbox" value ="1" checked>
									<%} else {%>
										<input name="trangthai" type="checkbox" value ="0">
									<%} %>
						      	</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Địa chỉ<FONT class="erroralert"> *</FONT></TD>
						  	  	<TD colspan = "6" class="plainlabel"><INPUT type="text" name="diachi" value="<%= khBean.getDiachi() %>" size="40"></TD>
						  </TR>
						  
						  <TR>
							   	 <TD class="plainlabel">Tỉnh/Thành phố <FONT class="erroralert"> *</FONT></TD>
								 <TD colspan = "2" class="plainlabel"><SELECT name="tpId" id="TP" onChange="submitform();">
								    <option value=""></option>
								      <% try{while(tp.next()){ 
								    	if(tp.getString("tpId").equals(khBean.getTpId())){ %>
								      		<option value='<%=tp.getString("tpId")%>' selected><%=tp.getString("tpTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=tp.getString("tpId")%>'><%=tp.getString("tpTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>	</TD>

							   	 <TD class="plainlabel">Quận/Huyện <FONT class="erroralert"> *</FONT></TD>
								 <TD colspan="3" class="plainlabel"><SELECT name="qhId" id="QH">
								    <option value=""></option>
								      <%if(qh != null){ 
								      		try{while(qh.next()){ 
								    			if(qh.getString("qhId").equals(khBean.getQhId())){ %>
								      				<option value='<%=qh.getString("qhId")%>' selected><%=qh.getString("qhTen") %></option>
								      		 <%}else{ %>
								     				<option value='<%=qh.getString("qhId")%>'><%=qh.getString("qhTen") %></option>
								     		<%}}}catch(java.sql.SQLException e){} 
								     		
								      }	%>
								     		  
                        				</SELECT>	</TD>
                        			
						  </TR>
						  
							<TR>
								<TD width="15%" class="plainlabel">GH công nợ</TD>
						      <TD class="plainlabel" colspan="2"><SELECT name="ghcnTen" >
                                	<OPTION value="" selected></OPTION>
                                	<% try{while(ghcn.next()){ 
								    	if(ghcn.getString("ghcnId").equals(khBean.getGhcnId())){ %>
								      		<option value='<%= ghcn.getString("ghcnId") %>' selected><%= ghcn.getString("ghcnTen") + " d"%></option>
								      	<%}else{ %>
								     		<option value='<%= ghcn.getString("ghcnId") %>'><%= ghcn.getString("ghcnTen") + " d"%></option>
								     	<%}}}catch(java.sql.SQLException e){} 
								     %>
                              </SELECT></TD>
							  <TD width="15%" class="plainlabel">Mức chiết khấu</TD>
							  <TD colspan="3" class="plainlabel"><SELECT name="mckTen" >
                                	<OPTION value="" selected></OPTION>
                                	<% try{while(mck.next()){ 
								    	if(mck.getString("mckId").equals(khBean.getMckId())){ %>
								      		<option value='<%= mck.getString("mckId") %>' selected><%= mck.getString("mckTen")+" %" %></option>
								      	<%}else{ %>
								     		<option value='<%= mck.getString("mckId") %>'><%= mck.getString("mckTen")+ "%" %></option>
								     	<%}}}catch(java.sql.SQLException e){} 
								     %>
                               </SELECT></TD>					      
						  </TR>
							<TR>
							  	 <TD class="plainlabel">Điện thoại</TD>
								 <TD colspan="6" class="plainlabel"><INPUT type="text" name="dienthoai"  value="<%= khBean.getSodienthoai() %>" size="15"></TD>
							</TR>
							<TR>
							  	 <TD class="plainlabel">Mã số thuế</TD>
								 <TD colspan="6" class="plainlabel"><INPUT type="text" name="masothue" value="<%= khBean.getMasothue() %>" size="15"></TD>
							</TR>
							<TR>
							   	 <TD class="plainlabel">Kênh bán hàng<FONT class="erroralert">*</FONT></TD>
								 <TD colspan="6" class="plainlabel"><SELECT name="kbhTen" onChange="tongleBangGia(this);">
								    <OPTION value="" selected></OPTION>
									<% try{while(kbh.next()){ 
								    	if(kbh.getString("kbhId").equals(khBean.getKbhId())){ %>
								      		<option value='<%= kbh.getString("kbhId") %>' selected><%=kbh.getString("kbhTen") %></option>
								      	<%}else{ %>
								     		<option value='<%= kbh.getString("kbhId") %>'><%= kbh.getString("kbhTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} 
								     %>
									</SELECT></TD>
							</TR>	
							<TR>
							   	 <TD class="plainlabel">Nhân viên giao nhận<FONT class="erroralert">*</FONT></TD>
								 <TD colspan="6" class="plainlabel">
									 <SELECT name="nvgnTen" id="nvgnTen">
										<% try{while(nvgn.next()){ 
									    	if(nvgn.getString("nvgnId").equals(khBean.getNvgnId())){ %>
									      		<option value='<%= nvgn.getString("nvgnId") %>' selected><%=nvgn.getString("nvgnTen") %></option>
									      	<%}else{ %>
									     		<option value='<%= nvgn.getString("nvgnId") %>'><%= nvgn.getString("nvgnTen") %></option>
									     	<%}} nvgn.close(); }catch(java.sql.SQLException e){} 
									     %>
									</SELECT>
								</TD>
							</TR>				

							<TR id="banggia" class="plainlabel">
								<td colspan="7" >
								
							<%
							if(khBean.getKbhId() ==null){
								khBean.setKbhId("");
							}
							if(khBean.getKbhId().equals("100002")){ %>
								<TABLE width="100%" border="0" cellspacing="1"  cellpadding="4" >
								<TR class="tbheader">
									<TH width="20%">Mã bảng giá </TH>
									<TH width="50%">Tên bảng giá</TH>
									<TH width="30%">Ðon vị kinh doanh</TH>
								</TR>

								<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								
								try{while(rsbanggiasieuthi.next()){ 
									if (i % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
									<TR class= <%= darkrow%> >
								   <%}%>
									<TD align="center"><div align="left"><%= rsbanggiasieuthi.getString("ma") %> </div></TD>
									<TD align="center"><div align="left"><%= rsbanggiasieuthi.getString("ten") %> </div></TD>
									<TD align="center"><div align="left"><%= rsbanggiasieuthi.getString("dvkdten") %> </div></TD>
									</TR>
									
									
							<% i++; } }catch(Exception er){}}%>
							</TABLE>
								</td>
							</TR>
							<SCRIPT language="javascript" type="text/javascript">
								var kbhId =  <%= khBean.getKbhId() %>
								 if ( kbhId == "100002" ) //kenh hien dai 
								 {
								  	showElement("banggia");
								 } 
								 else {
								  	hideElement("banggia");
								 }
							</SCRIPT>
						</TABLE>
						</FIELDSET>
						<TABLE width = 100% border="0" cellpadding="0" cellspacing ="0">						
							<TR>
								<TD height="100%">
									<FIELDSET><LEGEND class="legendtitle" style="color:black">Phân loại</LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD width="15%" class="plainlabel">Loại cửa hàng</TD>
											<TD  class="plainlabel"><SELECT name="lchTen">
											  <option value="" selected> </option>
											  <% try{while(lch.next()){ 
										    		if(lch.getString("lchId").equals(khBean.getLchId())){ %>
										      			<option value='<%= lch.getString("lchId") %>' selected><%= lch.getString("lchTen") %></option>
										      		<%}else{ %>
										     			<option value='<%= lch.getString("lchId") %>'><%= lch.getString("lchTen") %></option>
										     	<%}}}catch(java.sql.SQLException e){} 
										     %>
											  </SELECT></TD>
										</TR>
										<TR>
										  <TD class="plainlabel" valign="top">Hạng cửa hàng </TD>
										  <TD class="plainlabel" valign="top"><SELECT name="hchTen" >
										    <option value=""> </option>
										     <% try{ while(hch.next()){ 
								    			if(hch.getString("hchId").equals(khBean.getHchId())){ %>
								      				<option value='<%=hch.getString("hchId")%>' selected><%=hch.getString("hchTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=hch.getString("hchId")%>'><%=hch.getString("hchTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
                                          	</SELECT></TD>
									  </TR>
										<TR>
										  <TD class="plainlabel" valign="top">Vị trí cửa hàng</TD>
										  <TD class="plainlabel" valign="top"><SELECT name="vtchTen" >
										    <option value=""> </option>
										    <% try{ while(vtch.next()){ 
								    			if(vtch.getString("vtchId").equals(khBean.getVtchId())){ %>
								      				<option value='<%=vtch.getString("vtchId")%>' selected><%=vtch.getString("vtchTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=vtch.getString("vtchId")%>'><%=vtch.getString("vtchTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
                                          	</SELECT></TD>
									  </TR>
									</TABLE>
									</FIELDSET>								
								</TD>
							</TR>
						</TABLE>
					    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR>
								<TD colspan = "6" width="100%">
									<FIELDSET><LEGEND class="legendtitle">Phân nhóm
									</LEGEND>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
								<TR class="tbheader">
									<TH width="20%">Nhóm khách hàng </TH>
									<TH width="10%">Chọn</TH>
								</TR>

								<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								
								try{while(nkh_khSelected.next()){ 
									if (i % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
								<%}%>
									<TD align="center"><div align="left"><%= nkh_khSelected.getString("nkhTen") %> </div></TD>
									<TD align="center">
										<input name="nkh_khList" type="checkbox" value ="<%= nkh_khSelected.getString("nkhId") %>" checked>
									</TD></TR>
							     	<%i++;}}catch(java.sql.SQLException e){}
							     	
								try{while(nkh_khList.next()){ 
									if (i % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
								<%}%>
									<TD align="center"><div align="left"><%= nkh_khList.getString("nkhTen") %> </div></TD>									
									<TD align="center">
										<input name="nkh_khList" type="checkbox" value ="<%= nkh_khList.getString("nkhId") %>" >
									</TD></TR>
							     	<%i++;}}catch(java.sql.SQLException e){}
							  %>	
							  <tr class="tbfooter" > <td colspan="4" >&nbsp;</td> </tr> 												
							</TABLE>
									</FIELDSET>								
								</TD>
							</TR>
				    	</TABLE>
					</TR>
			  	</TABLE>
			
		<!--end body Dossier-->		
	</TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<% 	
khBean.DBclose();
khBean = null;
	try{
	
		if(bgst != null)
			bgst.close();
		if(ghcn != null)
			ghcn.close();
		if(hch != null)
			hch.close();
		if(kbh != null)
			kbh.close();
		if(lch != null)
			lch.close();
		if(mck != null)
			mck.close();
		if(nkh_khList!= null)
			nkh_khList.close();
		if(tp != null)
			tp.close();
		if(qh != null)
			qh.close();
		if(vtch != null)
			vtch.close();
		if(nch!=null){
			nch.close();
		}
		if(nvgn!=null){
			nvgn.close();
		}
		if(nkh_khList!=null){
			nkh_khList.close();
		}
		if(nkh_khIds!=null){
			nkh_khIds.clear();
		}
		session.setAttribute("khBean",null);
		if(rsbanggiasieuthi!=null){
			rsbanggiasieuthi.close();
		}
	
	}
	catch (SQLException e) {}

%>
<%}%>
