
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.asm.IAsm"  %>
<%@ page  import = "geso.traphaco.center.beans.asm.imp.*" %>
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

<% IAsm asmBean = (IAsm)session.getAttribute("asmBean"); %>
<% ResultSet kbh = asmBean.getKbh(); %>
<% ResultSet dvkd = asmBean.getDvkd(); %>
<% ResultSet kv = asmBean.getKv(); %>
<% ResultSet nccRs = asmBean.getNccRs(); %>
<% ResultSet tructhuocrs = asmBean.getTructhuocRs(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
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
    <link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function() {
		$("select:not(.notuseselect2)").select2({
			width : 'resolve'
		});
	});
</script>
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
    document.forms["asmForm"].submit();
 }

 function saveform()
 {
	 var ten = document.getElementById('asmTen');
	 var diachi = document.getElementById('DiaChi');
	 var email = document.getElementById('Email');
	 var dienthoai = document.getElementById('DienThoai');
	 var kbhId = document.getElementById('kbhId');
	 var dvkdId = document.getElementById('dvkdId');	
	 var asm_kv = document.getElementsByName("kvId");	
	 var tructhuoc = document.getElementsByName("tructhuoc");	
	 var nccId = document.getElementsByName("nccId");
	 if(ten.value == "")
	 {
		 alert('Vui lòng nhập tên của Trưởng Khu Vực');
		 return;
	 }

	/*  if(kbhId.value.trim() == "")
	 {
		 alert('Vui lòng chọn Kênh bán hàng');
		 return;
	 }	 */ 
	 
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
	 if(nccId.value == "")
	 {
		 alert('Vui lòng chọn nhà cung cấp');
		 return;
	 }	 	
	 
	 var flag = '';
	 for(var i in asm_kv)
	 {
		 if(asm_kv[i].checked)
			 flag = flag + asm_kv[i].value;
	 }
	 if(flag == '')
	 {
		 alert('Vui lòng chọn Khu Vực');
		 return;
	 }
	 
	 document.forms['asmForm'].action.value='save';
     document.forms['asmForm'].submit();
 }
</SCRIPT>
</HEAD>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="asmForm" method="post" action="../../ASMUpdateSvl">
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền kinh doanh &gt; ASM &gt; Tạo mới</TD> 
                             <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>

                        </table>
                    </TD>
                </TR>
            </TABLE>
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
                    <TR class = "tbdarkrow">
                        <TD width="30" align="center"><A href="../../ASMSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay Ve"  title="Quay Ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
                                        
                                        <textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1" ><%= asmBean.getMsg() %></textarea>
                                        <%asmBean.setMsg(""); %>
                                        </FIELDSET>
                                   </TD>
                                </TR>
                
                <TR>
                  <TD height="100%" width="100%">
                        <FIELDSET>
                        <LEGEND class="legendtitle" style="color:black">Thông tin Giám đốc Miền </LEGEND>

                        <TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                            <TR>
                                <TD width="150px" class="plainlabel" >Tên phụ trách vùng <FONT class="erroralert"> *</FONT></TD>
                                <TD width="250px" class="plainlabel"><INPUT name="asmTen" id="asmTen" type="text" value="<%= asmBean.getTen() %>" ></TD>                                
								   <TD width="150px" class="plainlabel" >Ngày sinh <FONT class="erroralert"> *</FONT></TD>
                                <TD width="250px" class="plainlabel"><INPUT class ="days" name="bmngaysinh" id="bmngaysinh" type="text" value="<%= asmBean.getNgaysinh() %>" ></TD>      
                          		<TD width="150px" class="plainlabel">Đơn vị kinh doanh<FONT class="erroralert"> *</FONT></TD>
							    <TD class="plainlabel">
								    <SELECT name="dvkdId" id = "dvkdId" >
									   <option value=""></option> 
									    <% try{ while(dvkd.next()){ 
										   	if(dvkd.getString("dvkdId").equals(asmBean.getDvkdId())){ %>
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
                                	<INPUT name="DiaChi" id="DiaChi" type="text" value="<%= asmBean.getDiachi() %>" >
                                </TD>
                                <TD class="plainlabel">Điện thoại <FONT class="erroralert"> *</FONT></TD>
                              	<TD class="plainlabel"><input name="DienThoai" id="DienThoai" type="text" value="<%= asmBean.getDienthoai() %>"></TD>    
                              	
                              	<TD class="plainlabel">Ngày vào công ty <FONT class="erroralert"> *</FONT></TD>
                              	<TD class="plainlabel"><input class = "days" name="ngayvaoct" id="ngayvaoct" type="text" value="<%= asmBean.getNgayvaoct() %>"></TD>                      	
                                
                          	</TR>
                            <TR>
                                <TD class="plainlabel" > Email <FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel">
                                	<INPUT name="Email" id="Email" type="text" value="<%= asmBean.getEmail() %>" >
                                </TD>   
                                
                              	<TD class="plainlabel" > Số ĐT công ty</TD>
							  	<TD class="plainlabel"><INPUT type="text" name="sodtct"  value="<%=asmBean.getSoDTcongty()%>"></TD>
							  	  
                              	<TD class="plainlabel" >Số năm làm việc</TD>
							  	<TD class="plainlabel"><INPUT type="text" name="sonamlamviec"  value="<%=asmBean.getSonamlamviec()%>"></TD>                                                    
                          	</TR>
                          	

                          	<tr>
                          		<TD  class="plainlabel">Ghi chú </TD>
						    	<TD class="plainlabel"><INPUT type="text" name="ghichu"  value="<%=asmBean.getGhichu()%>"></TD>
						    	<TD  class="plainlabel">Loại HĐLĐ </TD>
						    	<TD class="plainlabel"><INPUT type="text" name="Loaihdld"  value="<%=asmBean.getLoaiHD()%>"></TD>
								<TD  class="plainlabel">Số HĐLĐ </TD>
						    	<TD class="plainlabel"><INPUT type="text" name="sohdld"  value="<%=asmBean.getSoHD()%>"></TD>
								
								
							</tr>
                            <Tr> 
                            	<TD  class="plainlabel">Ngày ký HĐLĐ </TD>
						    	<TD class="plainlabel"><INPUT class ="days" type="text" name="ngaykyhd"  value="<%=asmBean.getNgaykyHD()%>"></TD>
								<TD  class="plainlabel">Ngày kết thúc HĐLĐ </TD>
						    	<TD class="plainlabel"><INPUT class ="days" type="text" name="ngaykthd"  value="<%=asmBean.getNgayketthucHD()%>"></TD>
                            	 <TD  class="plainlabel">Số TK </TD>
						       <TD class="plainlabel"><INPUT type="text" name="sotk"  value="<%=asmBean.getSotk()%>"></TD>	
                            </Tr>
                            <TR>
                           							
                                <TD class="plainlabel" > Ngân hàng</TD>
                                <TD class="plainlabel">
                                	<INPUT name="NganHang" id="NganHang" type="text" value="<%= asmBean.getNganHang() %>" >
                                </TD>
								 <TD class="plainlabel" > Chi nhánh ngân hàng</TD>
                                <TD class="plainlabel" colspan="3" >
                                	<INPUT name="ChiNhanh" id="ChiNhanh" type="text" value="<%= asmBean.getChiNhanh() %>" >
                                </TD>
                                 <%-- <TD class="plainlabel" > Trực thuộc Pha Nam MTV</TD>
	                          	 <TD class="plainlabel">
								    <SELECT name="tructhuoc" id = "tructhuoc" >
									   <option value=""></option> 
									    <% try{ while(tructhuocrs.next()){ 
										   	if(tructhuocrs.getString("id").equals(asmBean.getTructhuoc())){ %>
									      		<option value='<%=tructhuocrs.getString("id") %>' selected='selected'><%=tructhuocrs.getString("ten") %></option>
									      	<%}else{ %>
									     		<option value='<%=tructhuocrs.getString("id") %>' ><%=tructhuocrs.getString("ten") %></option>
									     	<%}}}catch(java.sql.SQLException e){
									     		
									     		
									     	e.printStackTrace();
									     	} %>
									     	
									 </SELECT>
								</TD> --%>
                            </tr>
                            
                            <tr>
                            	<TD  class="plainlabel" > Mã cũ </TD>
								<TD  class="plainlabel" colspan="1" > 
									<INPUT type="text" name="maFAST"  value="<%= asmBean.getMaFAST() %>" >
								</TD>    
								 
							    <TD width="120px" class="plainlabel">Nhà cung cấp<FONT class="erroralert"> *</FONT></TD>
							    <TD  class="plainlabel">
								    <SELECT name="nccId" id = "nccId" >
									    <% try{ while(nccRs.next()){ 
										   	if(nccRs.getString("pk_seq").equals(asmBean.getNccId()  )){ %>
									      		<option value='<%=nccRs.getString("pk_seq") %>' selected='selected'><%=nccRs.getString("ten") %></option>
									      	<%}else{ %>
									     		<option value='<%=nccRs.getString("pk_seq") %>' ><%=nccRs.getString("ten") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									 </SELECT>
								</TD>                             		
							           <TD class="plainlabel">Kênh bán hàng<FONT
												class="erroralert">*</FONT>
								<TD class="plainlabel" colspan="5">
											<SELECT name="KenhBH" id="kbhTen" class ="select2" style="width: 200px;" multiple="multiple">
												<option value=""></option>			
													<%
														try{while(kbh.next()){ 
													if(asmBean.getKbhId().indexOf(kbh.getString("KBHID")) >= 0){
													%>
													<option value='<%=kbh.getString("KBHID")%>' selected><%=kbh.getString("KBH")%></option>
													<%
														}else{
													%>
													<option value='<%=kbh.getString("KBHID")%>'><%=kbh.getString("KBH")%></option>
													<%
														}
													}}catch(java.sql.SQLException e){e.printStackTrace();}
													%>
											</SELECT></TD>
                            </tr>
                            <tr>
                            	<TD  class="plainlabel" > Mã nhân sự </TD>
								<TD  class="plainlabel" colspan="1" > 
									<INPUT type="text" name="manhansu"  value="<%= asmBean.getMaNhanSu() %>" >
								</TD> 
                            	<TD class="plainlabel"><div align="left">Hoạt động<FONT class="erroralert"> *</FONT></div></TD>
	                            <TD class="plainlabel" colspan ="3">                         
	                            	<input name="TrangThai" type="checkbox" value ="1" checked readonly>                                                                     
	                            </TD>             
                            </tr>
                    </TABLE>	
                    </FIELDSET>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                          <TR>
                            <TD colspan="4">
                       <TR>
                            <TD colspan="4">
                        <FIELDSET>
                        <LEGEND class="legendtitle" style="color:black">Chọn Khu Vực<FONT class="erroralert"> *</FONT></LEGEND>

                        <TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
                            <TR class="tbheader">
                                <TH width="80%">Khu vực</TH>    
                                  <TH width="20%">Ngày bắt đầu </TH>
                                <TH width="20%">Ngày kết thúc </TH>                       
                                <TH width="20%">Chọn </TH>
                            </TR>
                     		
                     		<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								try{
									Hashtable<String, String> kvIds = asmBean.getHTKvId();
									if(kv!=null)
									while(kv.next()){ 
										if (i % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
										<%} else {%>
										<TR class= <%= darkrow%> >
										<%}%>
											<TD align="center"><div align="left"><%= kv.getString("KV") %> </div></TD>
											<% if (kvIds.containsKey(kv.getString("KVID"))){ %>
												<TD align="center"><input class="days" name="ngaybatdau<%=kv.getString("KVID")%>" value='<%=kv.getString("ngaybatdau")%>'>  </TD>
											<TD align="center"><input class="days"  name="ngayketthuc<%=kv.getString("KVID")%>" value='<%=kv.getString("ngayketthuc")%>'> </TD>
											
												<TD align="center"><input name="kvId" id="kvId" type="checkbox" value ="<%= kv.getString("KVID") %>" checked></TD>
											<%}else{ %>
												<TD align="center"><input class="days" name="ngaybatdau<%=kv.getString("KVID")%>" value='<%=kv.getString("ngaybatdau")%>'>  </TD>
											<TD align="center"><input class="days"  name="ngayketthuc<%=kv.getString("KVID")%>" value='<%=kv.getString("ngayketthuc")%>'> </TD>
											
												<TD align="center"><input name="kvId" id="kvId" type="checkbox" value ="<%= kv.getString("KVID") %>" ></TD>
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
	    </TD>
    </TR>
</TABLE>
</form>
</BODY>
</HTML>

<% 
	if(kbh != null) kbh.close();
	if(dvkd != null) dvkd.close();
	if(kv != null) kv.close();

	asmBean.DBClose() ;%>	
<%}%>