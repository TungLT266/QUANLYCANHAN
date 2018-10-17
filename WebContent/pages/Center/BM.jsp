<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.bm.IBm" %>
<%@ page  import = "geso.traphaco.center.beans.bm.IBmList" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IBmList obj =(IBmList)session.getAttribute("obj"); %>
<% ResultSet dvkd = (ResultSet) obj.getDvkd();  %>
<% ResultSet vung = (ResultSet) obj.getVung(); %>
<% ResultSet bmList = (ResultSet) obj.getBmlist();
	int[] quyen = new  int[6];
	quyen = util.Getquyen("BmSvl","",userId);
	

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<SCRIPT language="javascript" type="text/javascript">

function clearform()
{
	document.bmForm.bmTen.value = "";
	document.bmForm.maFAST.value = "";
    document.bmForm.DienThoai.value = "";  
   // document.bmForm.kbhId.selectedIndex = 0;
    document.bmForm.dvkdId.selectedIndex = 0;
    document.bmForm.vungId.selectedIndex = 0;
    document.bmForm.TrangThai.selectedIndex = 2;
    submitform();    
}

function submitform()
{
	document.forms['bmForm'].action.value= 'search';
	document.forms['bmForm'].submit();
}
function xuatexcel()
{
	document.forms['bmForm'].action.value= 'excel';
	document.forms['bmForm'].submit();
}
function newform()
{
	document.forms['bmForm'].action.value= 'new';
	document.forms['bmForm'].submit();
}
function thongbao(){
	var tt = document.forms['bmForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['bmForm'].msg.value);
}

function xuatExcel()
{
	document.forms['bmForm'].action.value= 'excel';
	document.forms['bmForm'].submit();
	document.forms['bmForm'].action.value= '';
}

</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bmForm" method="post" action="../../BmSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>


<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
				  <TD align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">

						  <tr height="20">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kinh doanh &gt; RSM </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>

		</TABLE>
		<TABLE width="100%" cellpadding="0" cellspacing="0">		
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
							      <TR>  
							      		<TD width="24%" class="plainlabel">Tên Giám đốc miền </TD>
								        <TD class="plainlabel"><input type="text" name="bmTen" id="bmTen" value="<%= obj.getTen() %>" onChange="submitform();"></TD>
								    	<TD class="plainlabel">Số điện thoại (ĐT) </TD>
								    	<TD class="plainlabel"><input type="text" name="DienThoai" id="DienThoai" value="<%= obj.getDienthoai() %>" onChange="submitform();"></TD>
							      </TR>
							      
							      <TR>
									<TD width="17%" class="plainlabel">Mã cũ</TD>
								    <TD width="29%" class="plainlabel">
											<INPUT name="maFAST" type="text" value="<%= obj.getMaFAST() %>" size="40" onChange = "submitform();">
								  </TD>
								  <td class="plainlabel" colspan="2"></td>
					
								</TR>

								  <TR>
								    <%-- <TD class="plainlabel">Kênh bán hàng (KBH) </TD>
								    <TD  class="plainlabel">
								    	<SELECT name="kbhId" onChange = "submitform();">
									    <option value=""></option> 
									      <% try{ while(kbh.next()){ 
									    	if(kbh.getString("kbhId").equals(obj.getKbhId())){ %>
									      		<option value='<%=kbh.getString("kbhId") %>' selected='selected'><%=kbh.getString("kbh") %></option>
									      	<%}else{ %>
									     		<option value='<%=kbh.getString("kbhId") %>' ><%=kbh.getString("kbh") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									    </SELECT>
									</TD> --%>
								    <TD class="plainlabel">Đơn vị Kinh doanh </TD>
								    <TD width="20%" class="plainlabel" colspan="3">
								    <SELECT name="dvkdId" onChange = "submitform();">
									    <option value=""></option> 
									      <% try{ while(dvkd.next()){ 
									    	if(dvkd.getString("dvkdId").equals(obj.getDvkdId())){ %>
									      		<option value='<%=dvkd.getString("dvkdId") %>' selected='selected'><%=dvkd.getString("dvkd") %></option>
									      	<%}else{ %>
									     		<option value='<%=dvkd.getString("dvkdId") %>' ><%=dvkd.getString("dvkd") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									    </SELECT> 
									</TD>
								<TR>
						            <TD width="18%" class="plainlabel">Miền </TD>
									<TD width="33%" class="plainlabel">
									<SELECT name="vungId"  onChange = "submitform()">
									<option value=""> </option>
									      <% try{ while(vung.next()){ 
									    	if(vung.getString("vungId").equals(obj.getVungId())){ %>
									      		<option value='<%=vung.getString("vungId") %>' selected='selected'><%=vung.getString("vung") %></option>
									      	<%}else{ %>
									     		<option value='<%=vung.getString("vungId") %>' ><%=vung.getString("vung") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
                                	</SELECT>
									</TD>
						            
								    <TD class="plainlabel">Trạng thái </TD>
								    <TD  class="plainlabel"><SELECT name="TrangThai" onChange = "submitform();">
                                      
                                      <% if (obj.getTrangthai().equals("1")){%>
											  <option value="1" selected>Hoạt động</option>
											  <option value="0">Ngưng hoạt động</option>
											  <option value="2"></option>
											  
										<%}else if(obj.getTrangthai().equals("0")) {%>
											  <option value="0" selected>Ngưng hoạt động</option>
											  <option value="1" >Hoạt động</option>
											  <option value="2" ></option>
											  
										<%}else{%>																						  
											  <option value="1" >Hoạt động</option>
											  <option value="0" >Ngưng hoạt động</option>
											  <option value="2" selected></option>
										<%}%>
                                    </SELECT></TD>
						      </TR>
							    <TR>
									<TD colspan="4" class="plainlabel">
									<a class="button2" href="javascript:clearform()">
											<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
											<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;
                                    </TD>
								</TR>

							</TABLE>

							</FIELDSET>
							</TD>
						</TR>
					</TABLE>
					</TD>
				</TR>

				<TR>

					<TD width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Giám Đốc Miền &nbsp;&nbsp;&nbsp;
							<%if(quyen[Utility.THEM]!=0) {%>
							<a class="button3" href="javascript:newform()">
    								<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>    
							<%} %>
							
						</LEGEND>
	
						    <TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">

								<TR class="tbheader">
									<TH width="4%">STT</TH>
									<TH width="7%">Mã nhân sự </TH>
									<TH width="7%">Mã cũ</TH>
									<TH width="15%">Tên Giám đốc miền </TH>
									<TH width="10%">Số ĐT </TH>
				<!-- 				<TH width="10%">Kênh BH </TH> -->
									<TH width="10%">ĐVKD</TH>
									<TH width="10%">Khu Vực</TH>
									<TH width="9%">Trạng thái </TH>
									<TH width="6%">Ngày tạo</TH>
									<TH width="9%">Người tạo</TH>
									<TH width="6%">Ngày sửa </TH>
									<TH width="9%">Người sửa</TH>
									<TH width="8%" align="center">&nbsp;Tác vụ</TH>
								</TR>

                                <%      
                                   
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    
                                    if(bmList!=null)
                                    	try{
                                    		while (bmList.next()){
                                       
                                        	if (m % 2 != 0) {%>                     
                                            	<TR class= <%=lightrow%> >
                                        	<%} else { %>
                                            	<TR class= <%= darkrow%> >
                                        	<%}%>
                                        			<TD align="center"><%=m+1 %></TD>
                                        			<TD align="left"><div align="left"><%= bmList.getString("manhansu") %></div></TD>
                                        			<TD align="left"><div align="left"><%=bmList.getString("MAFAST") %></div></TD> 
                                            	    <TD align="left"><div align="left"><%= bmList.getString("bmten") %></div></TD>                                   
                                                	<TD><div align="center"><%= bmList.getString("dienthoai")  %></div></TD>
                                                	<%-- <TD align="center"><%= bmList.getString("kbh") %></TD> --%>
                                                	<TD align="center"><%= bmList.getString("dvkd") %></TD>
                                                	<TD align="center"><%= bmList.getString("vung") %></TD>
                                                	<% if (bmList.getString("trangthai").equals("1")){ %>
                                                    	<TD align="center">Hoạt động</TD>
                                                	<%}else{%>
                                                    	<TD align="center">Ngưng hoạt động</TD>
                                                	<%}%>
                                                	<TD align="center"><%= bmList.getString("ngaytao") %></TD>
                                                	<TD align="center"><%= bmList.getString("nguoitao") %></TD>
                                                	<TD align="center"><%=bmList.getString("ngaysua")%></TD>
                                                	<TD align="center"><%= bmList.getString("nguoisua")%></TD>
                                                	<TD align="center">
                                                		<TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    	<TR><TD>
                                                    	<%if(quyen[Utility.SUA]!=0) {%>
                                                    		<A href = "../../BmUpdateSvl?userId=<%=userId%>&update=<%= bmList.getString("bmid") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
                                                    	<%} %>
                                                    	</TD>
                                                    	<TD>&nbsp;</TD>
                                                    	<TD>
                                                    	<%if(quyen[Utility.XOA]!=0) {%>
                                                        	<A href = "../../BmSvl?userId=<%=userId%>&delete=<%= bmList.getString("bmid") %>;<%= bmList.getString("vungId")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa Trưởng Miền này?')) return false;"></A>
                                                    	<%} %>
                                                    	</TD>
                                                    	</TR></TABLE>
                                                	</TD>
                                            	</TR>
                                		<%m++; }}catch(java.sql.SQLException e){}
                                		
                                		%>
                                
                                <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	&nbsp;
											</TD>
										 </tr>  
                                									
							</TABLE>
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
	
	if(dvkd != null) dvkd.close();
	if(vung != null) vung.close();
	obj.DBClose();
	}%>