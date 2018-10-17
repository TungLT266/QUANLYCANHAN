<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.phanbokhuyenmai.IPhanbokhuyenmai" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% IPhanbokhuyenmai pbkmBean = (IPhanbokhuyenmai)session.getAttribute("pbkm"); %>
<% String schemeId = (String)session.getAttribute("schemeId"); %>
<% ResultSet scheme = (ResultSet)pbkmBean.getSchemeRS() ; %>
<% ResultSet schemeKogh = (ResultSet)pbkmBean.getSchemeKoGioiHanRs(); %>
<%
Hashtable<String, String> usedPro = (Hashtable<String, String>)pbkmBean.getusedPro(); 
ResultSet vungRs=pbkmBean.getVung();
ResultSet khuvucRs=pbkmBean.getKv();
ResultSet nppRs=pbkmBean.getNpp();
ResultSet npp=pbkmBean.getPhanboRs();

NumberFormat formatter = new DecimalFormat("#,###,###");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
    
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

<SCRIPT language="JavaScript" type="text/javascript">

	function submitform()
	{
		document.forms['pbkhForm'].setAttribute('enctype', '', 0);
	    document.forms['pbkhForm'].submit();
	}
	
	function search()
	{
		 document.forms['pbkhForm'].action.value='search';
	    document.forms['pbkhForm'].submit();
	}
	function phanboCTKM_Ko_Gh()
	{
		 document.forms['pbkhForm'].action.value='phanbo';
	    document.forms['pbkhForm'].submit();
	}
	
	function upload()
	{
		document.forms['pbkhForm'].setAttribute('enctype', "multipart/form-data", 0);
	    document.forms['pbkhForm'].submit();
	}
	
	function sellectAll(cbId1,cbId2 )
	{
		 var chkAll_Lct = document.getElementById(cbId1);
		 var loaiCtIds = document.getElementsByName(cbId2);
		 

		 
		 if(chkAll_Lct.checked )
		 {
			 for(var i = 0; i < loaiCtIds.length; i++)
			 {
				/*  if(!loaiCtIds.item(i).disabled)
				{ */
					 loaiCtIds.item(i).checked = true;
				/*  }*/
			 }
		 }
		 else
		 {
			 for(var i = 0; i < loaiCtIds.length; i++)
			 {
				 loaiCtIds.item(i).checked = false;
			 }
		 }
	}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="pbkhForm"  method="post" action="../../PhanbokhuyenmaiSvl" >
<input type="hidden" name="action" value="0">
<input type="hidden" name="flag" value="<%= pbkmBean.getFlag() %>">
<input type="hidden" name="userId" value="<%=userId %>">


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khuyến mãi  &gt; Khai báo &gt; Phân bổ khuyến mãi </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD >&nbsp; </TD>				
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width:100%" rows="1" readonly="readonly" ><%= pbkmBean.getMessage() %></textarea>
    					
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Phân bổ khuyến mãi </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
					  		<TR>
									  <TD class="plainlabel" width="150px">Từ ngày </TD>
									  <TD class="plainlabel" width="270px">
									  <table border=0 cellpadding="0" cellspacing="0">
                                              <TR><TD>
									    <input class="days" type="text" name="tungay" id ="tungay" style="width:202px" value="<%= pbkmBean.getTungay() %>" >
									</TD></TR>
                                        </table>
                                        <TD class="plainlabel" width="100px">Đến ngày </TD>
									  <TD colspan="3" class="plainlabel">
									  <table border=0 cellpadding="0" cellspacing="0">
                                              <TR><TD>
									    <input class="days" type="text" name="denngay" id ="denngay" style="width:202px" value="<%= pbkmBean.getDenngay() %>" >
									</TD></TR>
                                    </table>	</TR>
                              
                   
                               
                             <TR>
                             
                               <TR>
						<TD class="plainlabel">Vùng </TD>
							<TD class="plainlabel">
								<select name="vungId" onchange="seach();" id="vungId" style="width:202px;">
									<option value="" selected>All</option>
									<%if (vungRs != null)
											while (vungRs.next()) {
												if (vungRs.getString("vungId").equals(pbkmBean.getVungId())) {%>
													<option value="<%=vungRs.getString("vungId")%>" selected><%=vungRs.getString("vungTen")%></option>
											<%} else {%>
												<option value="<%=vungRs.getString("vungId")%>"><%=vungRs.getString("vungTen")%></option>
											<%}}%>
								</select>
							</TD>
									<TD class="plainlabel">Khu vực </TD>
							<TD class="plainlabel">
								<select name="khuvucId" onchange="seach();" id="khuvucId" style="width:202px;">
									<option value="" selected>All</option>
									<%if (khuvucRs != null)
											while (khuvucRs.next()) {
												if (khuvucRs.getString("khuvucId").equals(pbkmBean.getKvId()  )) {%>
													<option value="<%=khuvucRs.getString("khuvucId")%>" selected><%=khuvucRs.getString("khuvucTen")%></option>
											<%} else {%>
												<option value="<%=khuvucRs.getString("khuvucId")%>"><%=khuvucRs.getString("khuvucTen")%></option>
											<%}}%>
								</select>
							</TD>
						</TR>
		                    
                    <%-- <TR>
						<TD class="plainlabel">Chi nhánh / Đối tác </TD>
							<TD class="plainlabel">
								<select name="nppId" onchange="seach();" id="nppId" style="width:202px;">
									<option value="" selected>All</option>
									<%if (nppRs != null)
											while (nppRs.next()) {
												if (nppRs.getString("nppId").equals(pbkmBean.getNppId() )) {%>
													<option value="<%=nppRs.getString("nppId")%>" selected><%=nppRs.getString("nppTen")%></option>
											<%} else {%>
												<option value="<%=nppRs.getString("nppId")%>"><%=nppRs.getString("nppTen")%></option>
											<%}}%>
								</select>
							</TD>
						</TR> --%>
						
						 <TR>
                        <TD class="plainlabel" >Ngân sách </TD>
						<TD class="plainlabel"> 
				 			<SELECT name="loaingansach" id="loaingansach" class="legendtitle" >
					 			 <option value=""></option>
								  <% 											 
										if(pbkmBean.getLoaingansach().equals("1")){ %>
											<option value='1' selected>Giới hạn</option>
									  <%}else{ %>
											<option value='1'>Giới hạn</option>
									  <%} %>
								  <%		if(pbkmBean.getLoaingansach().equals("0")){ %>
												<option value='0' selected>Không Giới hạn</option>
										  <%}else{ %>
												<option value='0'>Không Giới hạn</option>
										  <%} %>	 
                            </SELECT>
                        </TD>
                        <TD class="plainlabel" >Tình trạng </TD>
						<TD  class="plainlabel"> 
				 			<SELECT name="phanbo" id="phanbo" class="legendtitle" >
					 			 <option value=""></option>
								  <% 											 
										if(pbkmBean.getPhanbo().equals("1")){ %>
											<option value='1' selected>Đã phân bổ</option>
									  <%}else{ %>
											<option value='1'>Đã phân bổ</option>
									  <%} %>
								  <%		if(pbkmBean.getPhanbo().equals("0")){ %>
												<option value='0' selected>Chưa phân bổ</option>
										  <%}else{ %>
												<option value='0'>Chưa phân bổ</option>
										  <%} %>	 
                            </SELECT>
                        </TD>
                    </TR>		
						  <TR>
							  	<TD class="plainlabel">Chọn tập tin</TD>
						  	  	<TD class="plainlabel" colspan="3" >
						  	  		<INPUT type="file" name="filename" size="40" value=''>&nbsp;&nbsp;&nbsp;&nbsp;
							  	  		<a class="button2"  href="javascript:upload();"><img style="top: -4px;" src="../images/button.png" alt="">Upload</a>
						  	  	</TD>
						  </TR>
                             <TR>
                             	<td class="plainlabel" colspan="4">
						  	  	<a class="button2"  href="javascript:search();"> <img style="top: -4px;" src="../images/button.png" alt="">Lọc CTKM</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a class="button2"  href="javascript:phanboCTKM_Ko_Gh();"><img style="top: -4px;" src="../images/button.png" alt="">Phân bổ CTKM</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="button2"  href="javascript:search();"><img style="top: -4px;" src="../images/button.png" alt="">Lọc NPP</a>&nbsp;&nbsp;&nbsp;&nbsp;
						  	  	</TD>
						  	</TR>

						 </TABLE>
						 </FIELDSET>
						 
						 
						 <FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Chương trình khuyến mại </LEGEND>
						  <TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px"> 
						 	  <TR class="tbheader">
                        		<TH align="center" width="10%"> Scheme</TH>
                        		<TH align="center" width="30%"> Diễn giải </TH>
                        		<TH align="center" width="10%"> Từ ngày </TH>
                        		<TH align="center" width="10%"> Đến ngày </TH>
                        		<TH align="center" width="15%"> Ngân sách</TH>
                        		<TH align="center" width="15%"> Số suất tối đa/Khách hàng</TH>
                        		<TH align="center" width="10%"> Chọn tất cả <input type ="checkbox"   id="cbCtkmId" name ="all" onclick ="sellectAll('cbCtkmId','ctkmId')"></TH>
                    		</TR>
				<%      int k=0;
                    if(scheme != null)
                    {
                    	while(scheme.next())
                    	{
                    		String color="style=\"line-height:30px\"";
                    		if(scheme.getString("DaPhanBo").equals("1"))
                    			color="style=\"line-height:30px;color:red\"";
                    		
                    			if(k % 2 == 0){
                        			%>
                        			<TR class='tbdarkrow'  <%=color %>>
    	                    	<%}else{ %> 
    	                    		 <TR class='tblightrow'  <%=color %>>
    	                    	<% } %>
    	                    	<TD align="left">
	                    				<%=scheme.getString("SCHEME")%>
	                    		</TD>
                    			<TD align="left">
                    				<%=scheme.getString("DIENGIAI")%>
                    			</TD>
                    			<TD align="left">
                    				<%=scheme.getString("TUNGAY")%>
                    			</TD>
                    			<TD align="left">
                    				<%=scheme.getString("DENNGAY")%>
                    			</TD>
                    			<TD align="left">
                    				 <%if(scheme.getString("loaingansach").equals("0")){ %>
                    				 Không giới hạn
                    				 <%}else  {%>
                    				 	Giới hạn
                    				 <%} %>
                    			</TD>
                    			<TD align="center">
                    			
                    			</TD>
                    			
                    			<TD align="center">                    			
                    			<%if(  pbkmBean.getSchemeId().indexOf(scheme.getString("pk_seq") )>=0  ){ %>
                    				<input type ="checkbox" name ="ctkmId" value ="<%=scheme.getString("pk_seq")%>"  checked="checked"  >
                    			<%}else { %>
                    				<input type ="checkbox" name ="ctkmId" value ="<%=scheme.getString("pk_seq")%>"  >
                    				<% 	} %>
                    			</TD>
                    		</TR>
                    	<%	k++;
                    	} }%>
					
						</TABLE>
						</FIELDSET>
					</TD>
				</TR>
				
					
				
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Đối tượng phân bổ </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="1">
						  <TR class="tbheader" >
						  <TH width="35%" >Scheme</TH>
						  <TH width="10%" >Mã đối tượng</TH>
						  <TH width="25%" >Tên đối tượng</TH>
						  <TH width="10%">Ngân sách</TH>
						  <TH width="10%" >Đã sử dụng</TH>
						  <TH width="10%" >Còn lại</TH>
						  
						  <% try{
							    String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								int m = 0;
								if(npp != null){
									while(npp.next()){ 
									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
									<TD align="left"><div align="center"><%= npp.getString("SCHEME")%>__<%= npp.getString("DIENGIAI")%></div></TD>
									<TD align="left"><div align="center"><%= npp.getString("NPPma")%></div></TD>                                   
									<TD align="left"><div align="left"><%= npp.getString("NPPten") %></div></TD>    
									<TD align="left"><div align="left"> <%= npp.getString("ngansach").equals("99999999999") ? "Không giới hạn" : npp.getString("ngansach") %></div></TD>
									<TD align="left"><div align="left"><%= formatter.format(npp.getDouble("dasudung") )%></div></TD>  
					  				<TD align="left"><div align="left"><%=formatter.format(npp.getDouble("ngansach") - npp.getDouble("dasudung")) %></div></TD>
					  			</TR>
							  
						  		<% m++ ;} 
						  		
						  		}%>		
						  		
						  <%}catch(Exception e){e.printStackTrace();}%>
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
	pbkmBean.DBClose();
	pbkmBean=null;	
}%>
