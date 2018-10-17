<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.bm.IBm"  %>
<%@ page  import = "geso.traphaco.center.beans.bm.imp.*" %>
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

<% IBm bmBean = (IBm)session.getAttribute("bmBean"); %>
<% ResultSet kbh = bmBean.getKbh(); %>
<% ResultSet dvkd = bmBean.getDvkd(); %>
<% ResultSet vung = bmBean.getVung(); %>
<% ResultSet tructhuocrs = bmBean.getTructhuocRs(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
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
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
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
    document.forms["bmForm"].submit();
 }

 function saveform()
 {
	 var ten = document.getElementById('bmTen');
	 var diachi = document.getElementById('DiaChi');
	 var email = document.getElementById('Email');
	 var dienthoai = document.getElementById('DienThoai');
	 var kbhId = document.getElementById('kbhId');
	 var dvkdId = document.getElementById('dvkdId');	
	 var bm_vung = document.getElementsByName("vungId");
	 var tructhuoc = document.getElementsByName("tructhuoc");	
	 
	 if(ten.value.trim() == "")
	 {
		 alert('Vui lòng nhập tên của Trưởng Miền');
		 return;
	 }
 
	 
	 if(dvkdId.value.trim() == "")
	 {
		 alert('Vui lòng chọn Đơn vị kinh doanh');
		 return;
	 }	 

	 if(diachi.value.trim() == "")
	 {
		 alert('Vui lòng nhập địa chỉ');
		 return;
	 }	

	 if(email.value.trim() == "")
	 {
		 alert('Vui lòng nhập địa chỉ Email');
		 return;
	 }	 
	 
	 if(dienthoai.value.trim() == "")
	 {
		 alert('Vui lòng nhập số điện thoại');
		 return;
	 }	 
	 if(tructhuoc.value == "")
	 {
		 alert('Vui lòng chọn trực thuộc');
		 return;
	 }	 			 

	 var flag = '';
	 for(var i in bm_vung)
	 {
		 if(bm_vung[i].checked)
			 flag = flag + bm_vung[i].value;
	 }
	 if(flag == '')
	 {
		 alert('Vui lòng chọn Miền');
		 return;
	 }
	 
	 document.forms['bmForm'].action.value='save';
     document.forms['bmForm'].submit();
 }
</SCRIPT>
</HEAD>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bmForm" method="post" action="../../BmUpdateSvl">
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền kinh doanh &gt; RSM &gt; Tạo mới</TD> 
                             <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>

                        </table>
                    </TD>
                </TR>
            </TABLE>
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
                    <TR class = "tbdarkrow">
                        <TD width="30" align="center"><A href="../../BmSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay VeÂ"  title="Quay Ve" width="30" height="30" border="1" longdesc="Quay veÂ" style="border-style:outset"></A></TD>
                        <TD width="2" align="left" ></TD>
                        <TD width="30" align="left" ><A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu Lai" alt="Luu Lai" border = "1"  style="border-style:outset"></A></TD>

                        <TD align="left" >&nbsp;</TD>
                    </TR>
            </TABLE>
            <TABLE width="100%" border="0" cellpadding="0"  cellspacing="0">
                                <TR>
                                    <TD align="left" colspan="4" class="legendtitle">
                                        <FIELDSET>
                                        <LEGEND class="legendtitle">Thông báo</LEGEND>
                                        
                                        <textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1" ><%= bmBean.getMsg() %></textarea>
                                        <%bmBean.setMsg(""); %>
                                        </FIELDSET>
                                   </TD>
                                </TR>
                
                <TR>
                  <TD height="100%" width="100%">
                        <FIELDSET>
                        <LEGEND class="legendtitle" style="color:black">Thông tin Giám đốc Miền </LEGEND>

                        <TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                            <TR>
                                <TD width="150px" class="plainlabel" >Tên Giám đốc Miền <FONT class="erroralert"> *</FONT></TD>
                                <TD width="250px" class="plainlabel"><INPUT name="bmTen" id="bmTen" type="text" value="<%= bmBean.getTen() %>" ></TD>                                
								   <TD width="150px" class="plainlabel" >Ngày sinh <FONT class="erroralert"> *</FONT></TD>
                                <TD width="250px" class="plainlabel"><INPUT class ="days" name="bmngaysinh" id="bmngaysinh" type="text" value="<%= bmBean.getNgaysinh() %>" ></TD>      
                          		<TD width="150px" class="plainlabel">Đơn vị kinh doanh<FONT class="erroralert"> *</FONT></TD>
							    <TD class="plainlabel">
								    <SELECT name="dvkdId" id = "dvkdId" >
									   <option value=""></option> 
									    <% try{ while(dvkd.next()){ 
										   	if(dvkd.getString("dvkdId").equals(bmBean.getDvkdId())){ %>
									      		<option value='<%=dvkd.getString("dvkdId") %>' selected='selected'><%=dvkd.getString("dvkd") %></option>
									      	<%}else{ %>
									     		<option value='<%=dvkd.getString("dvkdId") %>' ><%=dvkd.getString("dvkd") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									 </SELECT>
								</TD>
								
								
                          	</tr>
                          	
                            <TR>
                                <TD class="plainlabel" > Địa chỉ <FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel">
                                	<INPUT name="DiaChi" id="DiaChi" type="text" value="<%= bmBean.getDiachi() %>" >
                                </TD>
                                <TD class="plainlabel">Điện thoại <FONT class="erroralert"> *</FONT></TD>
                              	<TD class="plainlabel"><input name="DienThoai" id="DienThoai" type="text" value="<%= bmBean.getDienthoai() %>"></TD>    
                              	
                              	<TD class="plainlabel">Ngày vào công ty <FONT class="erroralert"> *</FONT></TD>
                              	<TD class="plainlabel"><input class = "days" name="ngayvaoct" id="ngayvaoct" type="text" value="<%= bmBean.getNgayvaoct() %>"></TD>                      	
                                
                          	</TR>
                            <TR>
                                <TD class="plainlabel" > Email <FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel">
                                	<INPUT name="Email" id="Email" type="text" value="<%= bmBean.getEmail() %>" >
                                </TD>   
                                
                              	<TD class="plainlabel" > Số ĐT công ty</TD>
							  	<TD class="plainlabel"><INPUT type="text" name="sodtct"  value="<%=bmBean.getSoDTcongty()%>"></TD>
							  	  
                              	<TD class="plainlabel" >Số năm làm việc</TD>
							  	<TD class="plainlabel"><INPUT type="text" name="sonamlamviec"  value="<%=bmBean.getSonamlamviec()%>"></TD>                                                    
                          	</TR>
                          	

                          	<tr>
                          		<TD  class="plainlabel">Ghi chú </TD>
						    	<TD class="plainlabel"><INPUT type="text" name="ghichu"  value="<%=bmBean.getGhichu()%>"></TD>
						    	<TD  class="plainlabel">Loại HĐLĐ </TD>
						    	<TD class="plainlabel"><INPUT type="text" name="Loaihdld"  value="<%=bmBean.getLoaiHD()%>"></TD>
								<TD  class="plainlabel">Số HĐLĐ </TD>
						    	<TD class="plainlabel"><INPUT type="text" name="sohdld"  value="<%=bmBean.getSoHD()%>"></TD>
								
								
							</tr>
                            <Tr> 
                            	<TD  class="plainlabel">Ngày ký HĐLĐ </TD>
						    	<TD class="plainlabel"><INPUT class ="days" type="text" name="ngaykyhd"  value="<%=bmBean.getNgaykyHD()%>"></TD>
								<TD  class="plainlabel">Ngày kết thúc HĐLĐ </TD>
						    	<TD class="plainlabel"><INPUT class ="days" type="text" name="ngaykthd"  value="<%=bmBean.getNgayketthucHD()%>"></TD>
                            	 <TD  class="plainlabel">Số TK </TD>
						       <TD class="plainlabel"><INPUT type="text" name="sotk"  value="<%=bmBean.getSotk()%>"></TD>	
                            </Tr>
                            <TR>
                           							
                                <TD class="plainlabel" > Ngân hàng</TD>
                                <TD class="plainlabel">
                                	<INPUT name="NganHang" id="NganHang" type="text" value="<%= bmBean.getNganHang() %>" >
                                </TD>
								 <TD class="plainlabel" > Chi nhánh ngân hàng</TD>
                                <TD class="plainlabel">
                                	<INPUT name="ChiNhanh" id="ChiNhanh" type="text" value="<%= bmBean.getChiNhanh() %>" >
                                </TD>
                                 <TD class="plainlabel" > Trực thuộc Pha Nam MTV</TD>
	                          	 <TD class="plainlabel">
								    <SELECT name="tructhuoc" id = "tructhuoc" >
									   <option value=""></option> 
									    <% try{ while(tructhuocrs.next()){ 
										   	if(tructhuocrs.getString("id").equals(bmBean.getTructhuoc())){ %>
									      		<option value='<%=tructhuocrs.getString("id") %>' selected='selected'><%=tructhuocrs.getString("ten") %></option>
									      	<%}else{ %>
									     		<option value='<%=tructhuocrs.getString("id") %>' ><%=tructhuocrs.getString("ten") %></option>
									     	<%}}}catch(java.sql.SQLException e){
									     		
									     		
									     	e.printStackTrace();
									     	} %>
									     	
									 </SELECT>
								</TD>
                            </tr>
                            
                             <tr>
                            	<TD  class="plainlabel" > Mã cũ </TD>
								<TD  class="plainlabel" colspan="1" > 
									<INPUT type="text" name="maFAST"  value="<%= bmBean.getMaFAST() %>" >
								</TD>    
								<TD  class="plainlabel" > Mã nhân sự </TD>
								<TD  class="plainlabel" colspan="1" > 
									<INPUT type="text" name="manhansu"  value="<%= bmBean.getMaNhanSu() %>" >
								</TD> 
								<TD class="plainlabel"><div align="left">Hoạt động<FONT class="erroralert"> *</FONT></div></TD>
	                            <TD class="plainlabel" colspan ="1" >                         
	                            	<input name="TrangThai" type="checkbox" value ="1" checked readonly>                                                                     
	                            </TD>                        
                            </tr>
                    </TABLE>	
                    </FIELDSET>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                          <TR>
                            <TD colspan="4">
                        <FIELDSET>
                        <LEGEND class="legendtitle" style="color:black">Chọn Miền<FONT class="erroralert"> *</FONT></LEGEND>

                        <TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
                            <TR class="tbheader">
                                <TH width="80%">Miền </TH>   
                                 <TH width="20%">Ngày bắt đầu </TH>
                                <TH width="20%">Ngày kết thúc </TH>                     
                                <TH width="20%">Chọn </TH>
                            </TR>
                     		
                     		<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								try{
									Hashtable<String, String> vungIds = bmBean.getHTVungId();
									if(vung!=null)
									while(vung.next()){ 
										if (i % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
										<%} else {%>
										<TR class= <%= darkrow%> >
										<%}%>
											<TD align="center"><div align="left"><%= vung.getString("VUNG") %> </div></TD>
											<% if (vungIds.containsKey(vung.getString("VUNGID"))){ %>
												<TD align="center"><input class="days" name="ngaybatdau<%=vung.getString("VUNGID")%>" value='<%=vung.getString("ngaybatdau")%>'>  </TD>
											<TD align="center"><input class="days"  name="ngayketthuc<%=vung.getString("VUNGID")%>" value='<%=vung.getString("ngayketthuc")%>'> </TD>
											
												<TD align="center"><input name="vungId" id="vungId" type="checkbox" value ="<%= vung.getString("VUNGID") %>" checked></TD>
											<%}else{ %>
											<TD align="center"><input class="days" name="ngaybatdau<%=vung.getString("VUNGID")%>" value='<%=vung.getString("ngaybatdau")%>'>  </TD>
											<TD align="center"><input class="days"  name="ngayketthuc<%=vung.getString("VUNGID")%>" value='<%=vung.getString("ngayketthuc")%>'> </TD>
											
												<TD align="center"><input name="vungId" id="vungId" type="checkbox" value ="<%= vung.getString("VUNGID") %>" ></TD>
											<%} %>
										</TR>
							     
							     <%i++;}}catch(java.sql.SQLException e){}
							  %>
                     		
                        </TABLE>                        
                        </FIELDSET>

                            </TD></TR>
                        </TABLE>
                     

                        
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
	if(kbh != null) kbh.close();
	if(dvkd != null) dvkd.close();
	if(vung != null) vung.close();

	bmBean.DBClose() ;%>	
<%}%>