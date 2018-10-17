<%@page import="geso.traphaco.center.beans.mucchietkhautt.IMucChietKhauTT_NhaPP"%>
<%@page import="geso.traphaco.center.beans.mucchietkhautt.IMucChietKhauTT"%>
<%@page import="geso.traphaco.center.beans.gioihancongnott.imp.GioiHangCongNo_Npp"%>
<%@page import="geso.traphaco.center.beans.gioihancongnott.IGioiHangCongNoTT_Npp"%>
<%@page import="java.util.List"%>
<%@page import="geso.traphaco.center.beans.gioihancongnott.IGioiHanCongNoTT"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.distributor.beans.gioihancongno.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<% IMucChietKhauTT ghcnBean = (IMucChietKhauTT)session.getAttribute("ghcnBean"); %>
<% ResultSet khuvuc = (ResultSet)session.getAttribute("rs_khuvuc"); %>
<% List<IMucChietKhauTT_NhaPP> listnhapp=ghcnBean.getListNhaPhanPhoi(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");
String khuvucid = (String) session.getAttribute("khuvucid");
%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<SCRIPT language="javascript" type="text/javascript">
	function confirmLogout()
	{
	   if(confirm("Bạn có muốn đăng xuất?"))
	   {
			top.location.href = "home.jsp";
	   }
	   return
	 }
	function submitform()
	{   
	
	   document.forms["ghcnForm"].action.value="loc";
	   document.forms["ghcnForm"].submit();
	}
	function saveform()
	{	  
		 var diengiai = document.getElementById("diengiai");
		 var mucchietkhau = document.getElementById("mucchietkhau");
		 
		 
		 if(diengiai.value == "")
		 {
			alert("Vui lòng nhập diễn giải mức chiết khấu...");
			return;
		 }
		 if(mucchietkhau.value == "")
		 {
			alert("Vui lòng nhập mức chiết khấu ...");
			return;
		 }
		 
				 
		 document.forms['ghcnForm'].action.value='save';
	     document.forms['ghcnForm'].submit();
	}
	function checkall(value){
		var checkone=document.getElementsByName("checkdetail");
		var giatricheck=document.getElementsByName("valuecheck");
		var chuoi;
		if(value==true){
			chuoi="1";
		}else{
			chuoi="0";
		}

		for(var i=0;i<checkone.length;i++ ){
			checkone.item(i).checked=value;
			giatricheck.item(i).value=chuoi;
		}
	}
	function recheck(){
		var checkone=document.getElementsByName("checkdetail");
		var giatricheck=document.getElementsByName("valuecheck");
		for(var i=0;i<checkone.length;i++ ){
			if(checkone.item(i).checked==true){
				giatricheck.item(i).value="1";
			}else {
				giatricheck.item(i).value="0";			
			}
			
				
		}
	}
</SCRIPT>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="ghcnForm" method="post" action="../../MucChietKhauTTNewSvl">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="action" value='1'>
<input type="hidden" name="formname" value="newform">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
	  <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <TR height="22">
						  	<TD align="left" colspan="2" class="tbnavigation"> &nbsp;Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Mức chiết khấu &gt; Tạo mới
						  	<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp; </TD>
						 </TR>
					  </table>
					</TD>
			  </TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="2">
				<TR >
					<TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class = "tbdarkrow">
								<TD width="30" align="left"><A href= "../../MucChietKhauTTSvl?userId=<%=userId %>">		 		
									<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								<TD width="2" align="left" ></TD>
								<TD width="30" align="left" ><A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
				    			<TD align="left" >&nbsp;</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
					
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" >
				<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
		                        	<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>              
		                        	<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  rows="1" style="width: 100%" readonly="readonly"><%= ghcnBean.getMessage() %></textarea>
		                        	<% ghcnBean.setMessage(""); %>
                        		</FIELDSET>
						   </TD>
					</tr>
				<TR>
				  <TD >
						<FIELDSET>
						<LEGEND class="legendtitle">&nbsp;Thông tin mức tín dụng &nbsp;</LEGEND>
						<TABLE class="tblight" width='100%' cellspacing="0" cellpadding="6">
							<TR>
								<TD width="15%" class="plainlabel">Diễn giải mức chiết khấu <FONT class="erroralert">*</FONT></TD>
								<TD  class="plainlabel"><INPUT name="diengiai" id="diengiai"  style="width:400px"
										type="text" value="<%= ghcnBean.getDiengiai() %>" ></TD>
							</TR>
							<TR>
								<TD class="plainlabel">Mức Chiết Khấu <FONT class="erroralert">*</FONT></TD>
								<TD  class="plainlabel"><INPUT name="mucchietkhau" id="mucchietkhau" size="10"
										type="text" value="<%= ghcnBean.getChietKhau() %>" ></TD>
							</TR>
							
						</TABLE>
	
						</FIELDSET>
				 	</TD>	
				</TR>
               
			</TABLE>
        <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD colspan = "6" width='100%'>
						<FIELDSET><LEGEND class="legendtitle">Chi nhánh / Đối tác được hưởng mức chiết khấu</LEGEND>
						  	<div class="title" align="left"  style="height:30px;background-color:#C5E8CD;padding-top:5px ;padding-left:5px  "> &nbsp;Chi nhánh / Đối tác thuộc Khu Vực
						  	<select name="khuvucid" onChange = "submitform();">
								<option value="" selected></option>
									<% try{while(khuvuc.next()){ 
								    	if(khuvuc.getString("pk_seq").equals(khuvucid)){ %>
								      		<option value='<%= khuvuc.getString("pk_seq") %>' selected><%= khuvuc.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%= khuvuc.getString("pk_Seq") %>'><%= khuvuc.getString("ten") %></option>
								     <%}}}catch(java.sql.SQLException e){} %>	   
								</select><br>
							</div>					
								
					<TABLE  border="0" cellspacing="1" cellpadding="6" width='100%'>
								<TR class="tbheader">
									<TH align="center" width="18%">Khu Vực</TH>
									<TH align="center" width="18%">Mã Chi nhánh / Đối tác</TH>
									<TH width="30%">Tên Chi nhánh / Đối tác </TH>
									<TH width="45%">Địa chỉ </TH>
									<TH align="center" width="7%">Chọn <input type="checkbox" name=checkgaga onchange="checkall(this.checked);" >  </TH>
								</TR>
								<%
								int j = 0;
									int size=listnhapp.size();
									
									while(j<size){
									IMucChietKhauTT_NhaPP NhaPP=listnhapp.get(j);	
										if (j % 2 != 0) {%>						
										<TR class= "tblightrow">
										<%} else {%>
										<TR class= "tbdarkrow" >
										<%}%>
										<TD align="center"><div align="center"><input name="tenkhuvuc" type="hidden" value="<%= NhaPP.getKhuVuc() %>" > <%= NhaPP.getKhuVuc() %> </div></TD>
										<TD align="center"><div align="center"><input name="idnhapp" type="hidden" value="<%=NhaPP.getIdNhaPp() %>" > <%= NhaPP.getIdNhaPp() %> </div></TD>
										<TD align="center"><div align="left"><input name="tennhapp" type="hidden" value="<%=NhaPP.getTenNhaPp() %>" > <%= NhaPP.getTenNhaPp() %></div></TD>
										<TD align="center"><div align="left"><input name="diachinhapp" type="hidden" value="<%=NhaPP.getDiaChi() %>" ><%= NhaPP.getDiaChi() %> </div></TD>
										<%if(NhaPP.getId().equals("1")){ %>
										<TD>
											<input type="hidden" name=valuecheck value="<%=NhaPP.getId()%>">
											<input type="checkbox" name="checkdetail"  checked="checked" onchange="recheck();">
										 </TD>
										<%}else{ %>
										<TD> 
											<input type="hidden" name=valuecheck value="<%=NhaPP.getId()%>">
											<input type="checkbox" name="checkdetail" onchange="recheck();">
										</TD>
										<%} %>
									    <%j++;%> 
							 			<%}%>							     
                                    </TR> 
						</TABLE>
						</FIELDSET>								
					</TD>
				</TR>
		    </TABLE>
	    <!--end body Dossier--></TD>   
	    
	</TR>
</TABLE>
</form>
</body>
</html>