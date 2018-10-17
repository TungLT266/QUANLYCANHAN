<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.daidienkinhdoanh.IDaidienkinhdoanh" %>
<%@ page  import = "java.util.Hashtable"%>
<%@ page  import = "java.sql.ResultSet "%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IDaidienkinhdoanh ddkdBean = (IDaidienkinhdoanh)session.getAttribute("ddkdBean"); %>
<% ResultSet npp = (ResultSet)ddkdBean.getNppList(); %>
<% String nppId = (String)ddkdBean.getNppId(); %>
<% ResultSet gsbhList = (ResultSet)ddkdBean.getGsbhList(); %>
<% ResultSet rsddkd = (ResultSet)ddkdBean.GetRsDDKDTienNhiem(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout(){
    if(confirm("Bạn có muốn đăng xuất?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function submitform()
 {   
    document.forms["ddkdForm"].submit();
 }

 function saveform()
 {
	 var ten = document.getElementById('ddkdTen');
	 var diachi = document.getElementById('DiaChi');
	 var dienthoai = document.getElementById('DienThoai');
	 var gsbhId = document.getElementsByName("gsbhId");
	 var matkhau = document.getElementById("matkhau");
	 var nppId = document.getElementById("nppId");

	 
	 if(ten.value.trim() == "")
	 {
		 alert('Vui lòng nhập tên nhân viên bán hàng');
		 return;
	 }
	 if(diachi.value.trim() == "")
	 {
		 alert('Vui lòng nhập địa chỉ');
		 return;
	 }	
	 if(dienthoai.value.trim() == "")
	 {
		 alert('Vui lòng nhập số điện thoại');
		 return;
	 }	 
	 /* if(matkhau.value == "")
	 {
		 alert('Vui lòng nhập mật khẩu khởi tạo');
		 return;
	 }	  */
		 
	 if(gsbhId.value == '')
	 {
		 alert('Vui lòng chọn PTT / GĐ CN 2');
		 return;
	 }
	 
	 if(nppId.value == '')
	 {
		 alert('Vui lòng chọn đối tượng trực thuộc');
		 return;
	 }
	 
 	 document.forms['ddkdForm'].action.value='save';
     document.forms['ddkdForm'].submit();
 }
 function keypress(e) 
 {    
 	var keypressed = null;
 	if (window.event)
 		keypressed = window.event.keyCode; //IE
 	else
 		keypressed = e.which; //NON-IE, Standard
 	
 	if (keypressed < 48 || keypressed > 57)
 	{ 
 		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
 		{//Phím Delete và Phím Back
 			return;
 		}
 		return false;
 	}
 }
</SCRIPT>

<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<META http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
		
	});
</script>

<style type="text/css">
	input
	{
		padding: 2px 0px;
	}
</style>

</HEAD>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="ddkdForm" method="post" action="../../DaidienkinhdoanhUpdateSvl">
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"  height="100%">
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
                <TR>
                    <TD align="left" class="tbnavigation">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                          <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">
							 		&nbsp;Dữ liệu nền &gt; Nền kinh doanh &gt;  Trình dược viên &gt; Tạo mới</TD> 
                             <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>

                        </table>
                    </TD>
                </TR>
            </TABLE>
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
                    <TR class = "tbdarkrow">
                        <TD width="30" align="center"><A href="../../DaidienkinhdoanhSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay VeÂ"  title="Quay Ve" width="30" height="30" border="1" longdesc="Quay veÂ" style="border-style:outset"></A></TD>
                        <TD width="2" align="left" ></TD>
                        <TD width="30" align="left" ><A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu Lai" alt="Luu Lai" border = "1"  style="border-style:outset"></A></TD>

                        <TD align="left" >&nbsp;</TD>
                    </TR>
            </TABLE>
            <TABLE width="100%" border="0" cellpadding="0"  cellspacing="0">
                                <TR>
                                    <TD align="left" colspan="4" class="legendtitle">
                                        <FIELDSET>
                                        <LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>
                                        
                                        <textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1" ><%= ddkdBean.getMessage() %></textarea>
                                        <%ddkdBean.setMessage(""); %>
                                        </FIELDSET>
                                   </TD>
                                </TR>
                
                <TR>
                  <TD height="100%" width="100%">
                        <FIELDSET>
                        <LEGEND class="legendtitle" style="color:black">Thông tin  Trình dược viên </LEGEND>

                        <TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                            <TR>
                                <TD width="120px" class="plainlabel" >Họ tên <FONT class="erroralert"> *</FONT></TD>
                                <TD width="250px" class="plainlabel"><INPUT name="ddkdTen" id="ddkdTen" type="text" value="<%= ddkdBean.getTen() %>" ></TD>
                                <TD width="120px" class="plainlabel" >Địa chỉ <FONT class="erroralert"> *</FONT></TD>
                                <TD width="250px" class="plainlabel" ><INPUT name="DiaChi" id="DiaChi" type="text" value="<%= ddkdBean.getDiachi() %>" ></TD>
                          	
                               <TD width="120px"  class="plainlabel">Điện thoại <FONT class="erroralert"> *</FONT></TD>
                               <TD  class="plainlabel"><input name="DienThoai" id="DienThoai" type="text" value="<%= ddkdBean.getSodt()%>"></TD>
                          	  
                          </TR>
                          <TR >

                             <td class="plainlabel">Số điện thoại Cty</td> 
                             <td class="plainlabel"><INPUT name="soDTcty" id="soDTcty" type="text" value="<%= ddkdBean.getSoDTCty()%>"></td>                          
                          	
                          	 <TD class="plainlabel">Email</TD>
                          	 <TD class="plainlabel" colspan="3" ><input name="email" id="email" type="text" value="<%= ddkdBean.getEmail()%>"></TD>
                          	
                          </TR>

                          <TR>
                          		<TD class="plainlabel">Số tài khoản</TD>
                          		<TD class="plainlabel"><INPUT  type="text" name="sotaikhoan" id="sotaikhoan" value="<%= ddkdBean.getSoTaiKhoan()%>"></TD>
                                <TD class="plainlabel" > Ngân hàng</TD>
                                <TD class="plainlabel"><INPUT name="NganHang" id="NganHang" type="text" value="<%= ddkdBean.getNganHang() == null ? "" : ddkdBean.getNganHang() %>" ></TD>
                                <TD class="plainlabel" > Chi nhánh</TD>
								<TD class="plainlabel"><INPUT type="text" name="ChiNhanh" id="ChiNhanh" value="<%=ddkdBean.getChiNhanh() == null ? "" : ddkdBean.getChiNhanh()%>"></TD>
                          	</TR>
                            
                            <TR>
                                <TD class="plainlabel" >Mã FAST</TD>
								<TD class="plainlabel"><INPUT type="text" name="maFAST" id="maFAST"  value="<%=ddkdBean.getMaFAST() == null ? "" : ddkdBean.getMaFAST()%>"></TD>
                          		<TD class="plainlabel" >Mật khẩu khởi tạo </TD>
								<TD class="plainlabel" colspan="3">
									<INPUT type="text" name="matkhau" id="matkhau"  value="<%=ddkdBean.getMatkhau() == null ? "" : ddkdBean.getMatkhau()%>">                          
								</TD>
                          </tr>
                          <TR>
                          	<TD class="plainlabel">Trực thuộc</TD>
							<TD class="plainlabel" colspan="3" >
								<SELECT name="nppId" id="nppId" onChange = "submitform()" class="select2"  style="width: 600px;" multiple="multiple" >
									<option value="" ></option>
									<%
		                             if (npp != null){
		                            	 String [] nppSelected = ddkdBean.getNppId().split(","); 
		                            	 while (npp.next()){   
		                            		 String nppRow = npp.getString("ID");
		                            		 boolean giongnhau = false;
		                            		 for( int chay = 0 ; chay < nppSelected.length ; chay++)
		                            		 {
		                            			 if(nppSelected[chay].equals(nppRow))
		                            			 {
		                            				 giongnhau = true;
		                            				 break;
		                            			 }
		                            		 }                           		 
		                       				 if( giongnhau  ) { %>
		                       				<option value ="<%= npp.getString("ID") %>" selected="selected"> <%=npp.getString("nppTen") %></option>
		                       				<%}else{ %>
		                       				<option value ="<%=npp.getString("ID") %>"> <%=npp.getString("nppTen") %></option>
		                       				<%}     		
		                            	 }
		                             } %>
                               	</SELECT>
							</TD>
							<TD class="plainlabel" colspan="2" >
								Nhân viên trung tâm 
								<% if(ddkdBean.getNppId().equals("1")) { %>
									<input name="isNVTT" type="checkbox" value ="1" checked>  
								<% } else { %>
									<input name="isNVTT" type="checkbox" value ="1" >  
								<% } %>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<% if(ddkdBean.getTrangthai().equals("1")) { %>
									Hoạt động <input name="TrangThai" type="checkbox" value ="1" checked> 
								<% } else { %>
									Hoạt động <input name="TrangThai" type="checkbox" value ="1" > 
								<% } %>
							</TD>
                          								
                          </TR>
                          <TR>
                          <TD class="plainlabel">Phụ trách tỉnh / GĐCN</TD>
							<TD class="plainlabel" colspan="5" >
								<SELECT name="gsbhId" id="gsbhId" class="select2" style="width: 200px;"  >
									<option value=""> </option>
									<%
		                             if (gsbhList != null){
		                            	 while (gsbhList.next()){                      				                       				
		                       				 if(gsbhList.getString("ID").equals(ddkdBean.getGsbanhang())){ %>
		                       				<option value ="<%= gsbhList.getString("ID") %>" selected="selected"> <%=gsbhList.getString("TEN") %></option>
		                       				<%}else{ %>
		                       				<option value ="<%=gsbhList.getString("ID") %>"> <%=gsbhList.getString("TEN") %></option>
		                       				<%}     		
		                            	 }
		                             } %>
                               	</SELECT>
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
try{
	if(npp!=null){
		npp.close();
	}
	
	if(gsbhList!=null){
		gsbhList.close();
	}
	ddkdBean.DBClose();
}catch(Exception er){
	
}

}%>