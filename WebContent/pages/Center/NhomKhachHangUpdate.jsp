<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhomkhachhang.INhomkhachhang" %>
<%@ page  import = "geso.traphaco.center.beans.nhomkhachhang.imp.Nhomkhachhang" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%  INhomkhachhang nkhBean = (INhomkhachhang)session.getAttribute("nkhBean"); 
	ResultSet khList = (ResultSet)nkhBean.getKhList();
	ResultSet khSelected = (ResultSet)nkhBean.getKhSelected();
	ResultSet vungList = (ResultSet)nkhBean.getVungList();
	ResultSet kvList = (ResultSet)nkhBean.getKvList();
	ResultSet nppList = (ResultSet)nkhBean.getNppList();
	String vungId = (String) nkhBean.getVungId();
	String kvId = (String)nkhBean.getKvId();
	String nppId = (String)nkhBean.getNppId();
	
	  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms['nkhForm'].submit();
}

function filterVung()
{
    document.nkhForm.action.value='filter';
    document.nkhForm.kvId.value='0';
    document.nkhForm.nppId.value='0';
    document.forms['nkhForm'].submit();       
}

function filterKv()
{
    document.nkhForm.action.value='filter';
    document.nkhForm.nppId.value='0';
    document.forms['nkhForm'].submit();   
    
}

function filterNpp()
{
    document.nkhForm.action.value='filter';
    document.forms['nkhForm'].submit();       
}

function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.nkhForm.chon.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
}

var lst = null;
function CheckAllCheckbox(ctrl, sValue, e) {
    var chkName = sValue.id;
    var arlstr = sValue.id.split(ctrl.id);
    var id = arlstr[1].split('_')[1].substring(3);

    var ee = (window.event) ? event : e;
    if (ee.shiftKey) {
        if (lst != null) {
            var First = 0, last = 0;
            if (parseInt(lst) > parseInt(id)) {
                First = parseInt(id);
                last = parseInt(lst);
            }
            else {
                First = parseInt(lst);
                last = parseInt(id);
            }

            for (i = First; i < last; i++) {                        
                var p = (i <= 9 ? '0' + i : i);
                var ctrlnew = chkName.replace('ctl' + id, 'ctl' + p);                        
                document.getElementById(ctrlnew).checked = sValue.checked;
            }                    
        }
    }
    lst = id;
}



</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="nkhForm" method="post" action="../../NhomkhachhangUpdateSvl" >
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="nkhId" value='<%= nkhBean.getId()  %>'>
<input type="hidden" name="action" value="0">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu &gt; Dữ liệu nền kinh doanh&gt; Nhóm khách hàng &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../NhomkhachhangSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=nkhBean.getMessage()%></textarea>
						<% nkhBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin nhóm khách hàng </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
							  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="diengiai" style="width:400px" value='<%= nkhBean.getDiengiai() %>'></TD>
						  </TR>
						  <TR>
						  		
							  <TD colspan="2" class="plainlabel" ><label>
							  	<% if(nkhBean.getTrangthai().equals("1")){ %>
							    	<input name="trangthai" type="checkbox" value="1" checked >
							    <%}else{ %>
							    	<input name="trangthai" type="checkbox" value="0" >
							    <%} %>
							    Hoạt động</label></TD>
						  </TR>
						  </table>
						  <LEGEND class="legendtitle" style="color:black">Điều kiện lọc khách hàng </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
				  		  <TR>
						    	<TD class="plainlabel" style="width:100px">Khu Vực</TD>
						  	  	<TD class="plainlabel">
						  	  	<SELECT name="vungId" onChange="filterVung();">
						  	  					<OPTION value="0" >&nbsp;</OPTION>	
						  	  		<%
						  	  		try{   
						  	  			if(vungList!= null){						  	  			
						   					while (vungList.next()){
						  	  					if (vungList.getString("pk_seq").equals(vungId)){%>
						  	  						<OPTION value="<%= vungList.getString("pk_seq")%>" selected><%= vungList.getString("diengiai")%></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value="<%= vungList.getString("pk_seq")%>" ><%= vungList.getString("diengiai") %></OPTION>
						  	  	<%				  }
						  	  				
						  	  				}
						  	  			
						  	  		}%>						  	  			
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
						  		
						  	<TR>
						  	  	<TD class="plainlabel">Khu Vực</TD>
						  	  	<TD class="plainlabel"><SELECT name="kvId" onchange="filterKv();">
						  	  		<OPTION value="0" ></OPTION>	
						  	  		<% if(kvList!= null){						  	  		
							   				while (kvList.next()){
						  	  					if (kvList.getString("pk_seq").equals(kvId)){%>
						  	  						<OPTION value='<%= kvList.getString("pk_seq")%>' selected ><%= kvList.getString("diengiai") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value='<%= kvList.getString("pk_seq")%>'  ><%= kvList.getString("diengiai") %></OPTION>
						  	  		<%			 }
						  	  				
						  	  				}
						  	  			
						  	  		}%>
						  	  		
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
						  	
						  	<TR>
						  	  	<TD class="plainlabel">Chi nhánh / Đối tác</TD>
						  	  	<TD class="plainlabel"><SELECT name="nppId" onchange="filterNpp();">	
						  	  		<OPTION value="0" ></OPTION>
						  	  		<% if(nppList!= null){					  	  			
							   				while (nppList.next()){
							   					
						  	  					if (nppList.getString("pk_seq").equals(nppId)){%>
						  	  						<OPTION value="<%= nppList.getString("pk_seq")%>" selected><%= nppList.getString("ten") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value="<%= nppList.getString("pk_seq")%>" ><%= nppList.getString("ten") %></OPTION>
						  	  	<%				  }
						  	  			
						  	  				}
						  	  			
						  	  		}%>
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
						  	
						  	
						  
						</TABLE>
						 <LEGEND class="legendtitle" style="color:black">Khách hàng</LEGEND>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">							
								<TR class="tbheader">
									<TH width="28%">Mã khách hàng </TH>
									<TH width="60%">Tên khách hàng </TH>
									<TH width="12%">Chọn
										<input type="checkbox" name="chon" onClick="checkedAll(document.nkhForm.khachhang)">								
									
									</TH>
								</TR>

								<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
						   	       rs = khSelected;
								   
								   if (!(rs == null)){			
								   		while (rs.next()){								   			
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
												<TD align="left" class="textfont"><%= rs.getString("pk_seq") %></TD>
												<TD align="center"><div align="left"><%= rs.getString("ten")%></div></TD>
												<TD align="center">
													<input type="checkbox" name="khachhang" value='<%= rs.getString("pk_seq") %>' checked>									
												</TD>

												</TR>
													
									<% 			m++;}
									}	
			
							   	    rs = khList;
									   
									if (!(rs == null)){													    
										while (rs.next()){								   			
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
												<TD align="left" class="textfont"><%= rs.getString("pk_seq") %></TD>
												<TD align="center"><div align="left"><%= rs.getString("ten")%></div></TD>
												<TD align="center">
													<input type="checkbox" name="khachhang" value='<%= rs.getString("pk_seq") %>'>									
												</TD>

												</TR>
													
								<% 			m++;}
										}	
								   }catch(java.sql.SQLException e){}   
									%>	
								   
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
	if(khList != null) khList.close(); 
	if(khSelected != null) khSelected.close();
	if(vungList != null) vungList.close();
	if(kvList != null) kvList.close();
	if(nppList != null) nppList.close() ; %>
<%}%>