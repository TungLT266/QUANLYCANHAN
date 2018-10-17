 <!-- DỮ LIỆU LINK TỪ DMS QUA  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.traphaco.center.beans.nhaphanphoi.INhaphanphoi" %>
<%@ page  import = "geso.traphaco.center.beans.nhaphanphoi.INhaphanphoiList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
	
 
<% INhaphanphoiList obj = (INhaphanphoiList)session.getAttribute("obj"); %>
<% ResultSet npplist = (ResultSet) obj.getNppList(); %>
<% ResultSet kv = (ResultSet)obj.getKhuvuc();  %>
<% ResultSet kenh = (ResultSet)obj.getRsKenh() ;  %>
<% ResultSet nppRs = (ResultSet)obj.getNppRs(); %>
<% ResultSet vung = (ResultSet)obj.getRsvung(); %>
<% ResultSet tinhthanh = (ResultSet)obj.getRsdiaban(); %>

<% obj.setNextSplittings(); 
	int[] quyen = new  int[6];
	quyen = util.Getquyen("NhaphanphoiSvl","&isKH="+obj.getIsKhachhang()+"",userId);
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
	  <style type="text/css">
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 150px;
			border: 1px solid black;
			padding: 2px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
		th {
		cursor: pointer;
		}	
  	</style>
  	
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
	document.nppForm.maFAST.value = "";	
	document.nppForm.nppTen.value = "";	
	document.nppForm.DienThoai.value = ""; 
	document.nppForm.kvId.selectedIndex = 0;      
    document.nppForm.TrangThai.selectedIndex = 2;
    submitform();    
}

function submitform()
{
	document.forms['nppForm'].action.value= 'search';
	document.forms['nppForm'].submit();
}
function xuatexcel()
{
	document.forms['nppForm'].action.value= 'excel';
	document.forms['nppForm'].submit();
}
function newform()
{
	document.forms['nppForm'].action.value= 'new';
	document.forms['nppForm'].submit();
}
function thongbao()
{var tt = document.forms['nppForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['nppForm'].msg.value);
	}
</SCRIPT>
</HEAD>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nppForm" method="post" action="../../NhaphanphoiSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="userTen" value='<%= userTen %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
<input type="hidden" name="isKH" value='<%=obj.getIsKhachhang() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
		<!--begin common dossier info---> <!--End common dossier info--->
		<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
				
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kinh doanh &gt; <%= obj.getIsKhachhang().equals("1") ? "Khách hàng ETC" : "Chi nhánh / Đối tác" %>  </TD>
   
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
								    <tr>  
								    	<TD class="plainlabel">Tên/Mã </TD>
								    	<TD class="plainlabel"><input type="text" name="nppTen" value="<%=obj.getTen() %>" onChange="submitform();"> 			</TD>
								 		<TD class="plainlabel">Khu Vực</TD>
								    	<TD class="plainlabel"><SELECT name="kvId" onChange = "submitform();">
										    <option value=""></option>
										      <% try{while(kv.next()){ 
										    	if(kv.getString("kvId").equals(obj.getKvId())){ %>
										      		<option value='<%=kv.getString("kvId")%>' selected><%=kv.getString("kvTen") %></option>
										      	<%}else{ %>
										     		<option value='<%=kv.getString("kvId")%>'><%=kv.getString("kvTen") %></option>
										     	<%}}}catch(java.sql.SQLException e){} %>	  
		                        				</SELECT></TD>
								  </TR>
								     <tr>  
								    	<TD class="plainlabel">Vùng</TD>
								    	<TD class="plainlabel"><SELECT name="vung" onChange = "submitform();">
								    	
								    	 <option value=""></option>
										      <% try{while(vung.next()){ 
										    	  System.out.println("vung id "+obj.getVung());
										    	if(vung.getString("vid").equals(obj.getVung())){ %>
										      		<option value='<%=vung.getString("vId")%>' selected><%=vung.getString("vTen") %></option>
										      	<%}else{ %>
										     		<option value='<%=vung.getString("vId")%>'><%=vung.getString("vTen") %></option>
										     	<%}}}catch(java.sql.SQLException e){} %>	  
		                        				</SELECT>
								    	
								    	</TD>
								 		<TD class="plainlabel">Tỉnh thành</TD>
								    	<TD class="plainlabel"><SELECT name="diaban" onChange = "submitform();">
										    <option value=""></option>
										      <% try{while(tinhthanh.next()){ 
										    	if(tinhthanh.getString("tId").equals(obj.getDiaban()  )){ %>
										      		<option value='<%=tinhthanh.getString("tId")%>' selected><%=tinhthanh.getString("tTen") %></option>
										      	<%}else{ %>
										     		<option value='<%=tinhthanh.getString("tId")%>'><%=tinhthanh.getString("tTen") %></option>
										     	<%}}}catch(java.sql.SQLException e){e.printStackTrace();} %>	  
		                        				</SELECT></TD>
								  </TR>
								  
								  	<TR>
										<TD class="plainlabel">Mã FAST</TD>
								   		<TD class="plainlabel"><INPUT name="maFAST" type="text" value="<%= obj.getMaFAST() %>" size="40" onChange = "submitform();">
								 		<TD class="plainlabel">Trạng thái </TD>
								    	<TD class="plainlabel"><select name="TrangThai" onChange = "submitform();">
											
											<% if (obj.getTrangthai().equals("1")){%>
											  <option value="1" selected>Hoạt động</option>
											  <option value="0">Ngưng hoạt động</option>
											  <option value="2"> </option>
											  
											<%}else if(obj.getTrangthai().equals("0")) {%>
											  <option value="0" selected>Ngưng hoạt động</option>
											  <option value="1" >Hoạt động</option>
											  <option value="2" > </option>
											  
											<%}else{%>																						  
											  <option value="1" >Hoạt động</option>
											  <option value="0" >Ngưng hoạt động</option>
											  <option value="2" selected> </option>
											<%}%>
										    </select></TD>
								  	</TR>				
								<tr>
								    <TD class="plainlabel">Số điện thoại (DT) </TD>
								    <TD colspan="3" class="plainlabel"><INPUT name="DienThoai" type="text" size="40" value='<%=obj.getSodienthoai()%>' onChange="submitform();"> </TD>
						      </tr>
						  
						       <TR style="display: none;" >
								    <TD class="plainlabel">Kênh bán hàng</TD>
								    <TD colspan="3" class="plainlabel"><SELECT name="kenhId" onChange = "submitform();">
								    <option value=""></option>
								      <% try{while(kenh.next()){ 
								    	if(kenh.getString("pk_seq").equals(obj.getKenhId() )  ){ %>
								      		<option value='<%=kenh.getString("pk_seq")%>' selected><%=kenh.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=kenh.getString("pk_seq")%>'><%=kenh.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			</TD>
                        				
						      </TR>
						      
						      
								  <TR>
								    
						      </TR>
							    <TR>
									<TD colspan="4" class="plainlabel">
									<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
									<a class="button2" href="javascript:xuatexcel()">
										<img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;	
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
		<TABLE width="100%" cellpadding="0" cellspacing="0">		
				<TR>
					<TD width="100%">
						<FIELDSET><%-- <%= obj.getIsKhachhang().equals("1") %>  --%>
						<LEGEND class="legendtitle">
							<%if(quyen[Utility.THEM]!=0) {%>
							<a class="button3" href="javascript:newform()">
    							<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>   
    						<%} %>        
    						
    						                
						</LEGEND>
	
						    <TABLE class="" width="100%">
						<TR>
							<TD width="100%">
							<TABLE width="100%" id="table" class="tabledetail sortable" border="0" cellspacing="1" cellpadding="4">
							<thead>
								<TR class="tbheader">
									<TH width="4%">STT</TH>
									<TH width="8%">Mã</TH>
									<TH width="8%">Mã FAST</TH>
									<TH width="19%">Tên</TH>
									<TH width="10%">Loại</TH>
									<TH width="10%">Tỉnh thành</TH>
									<TH width="10%">Khu vực </TH>
									<TH width="8%">Trạng thái </TH>
									<!-- <TH width="8%">Ngày tạo</TH> -->
									<TH width="12%">Người tạo </TH>
									<!-- <TH width="8%">Ngày sửa</TH> -->
									<TH width="12%">Người sửa </TH>
									<TH class="nosort" width="8%" align="center">Tác vụ</TH>
								</TR>
								</thead>
								<tbody>
								
								<% 
									INhaphanphoi npp = null;
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(npplist!=null)
									while (npplist.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%=npplist.getString("_no") %></TD>
												<TD align="left"><div align="left"><%=npplist.getString("nppMa")%></div></TD>
												<TD align="left"><div align="left"><%=npplist.getString("MAFAST") %></div></TD>
												<TD align="left"><div align="left"><%=npplist.getString("nppTen")%></div></TD>
												<TD><div align="left"><%=npplist.getString("loaiNPP")%></div></TD>                                 
												<TD><div align="left"><%=npplist.getString("tinhthanh")%></div></TD>                                   
												<TD><div align="left"><%= npplist.getString("khuvuc")%></div></TD>
												<% if (npplist.getString("trangthai").equals("1")){ %>
													<TD align="center">Hoạt động</TD>
												<%}else{%>
													<TD align="center">Ngưng hoạt động</TD>
												<%}%>
												<%-- <TD align="center"><%=npplist.getString("ngaytao")%></TD> --%>
												<TD align="left"><%=npplist.getString("nguoitao")%></TD>
												<%-- <TD align="center"><%=npplist.getString("ngaysua")%></TD> --%>
												<TD align="left"><%=npplist.getString("nguoisua")%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="2">
													<TR><TD>
													<%if(quyen[Utility.SUA]!=0) {%>
														<A href = "../../NhaphanphoiUpdateSvl?userId=<%=userId%>&update=<%= npplist.getString("id") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
													<%if(quyen[Utility.XOA]!=0 && npplist.getString("trangthai").equals("1") ) {%>
														<A href = "../../NhaphanphoiSvl?userId=<%=userId%>&delete=<%=npplist.getString("id")%>&isKH=<%obj.getIsKhachhang();%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa Chi nhánh / Đối tác này?')) return true;"></A></TD>
													<%} %>
													</TR>
													</TABLE>
												</TD>
											</TR>
								<%m++; } %>
								 </tbody>
                					<tfoot>											
								<tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting()>1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('nppForm', 1, 'view')"> &nbsp;
												<%} else { %>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting()< 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('nppForm', eval(nppForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												
												
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('nppForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('nppForm', eval(nppForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('nppForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr> </tfoot> 

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
<script type="text/javascript" src="../scripts/script-table-sorter.js"></script>
	<script type="text/javascript">
		var sorter = new TINY.table.sorter("sorter");
		sorter.head = "HEAD";
		//sorter.asc = "asc";
		sorter.desc = "desc";
		sorter.even = "tblightrow";
		sorter.odd = "tbdarkrow";
		sorter.evensel = "evenselected";
		sorter.oddsel = "oddselected";
		sorter.paginate = true;
		sorter.currentid = "currentpage";
		sorter.limitid = "pagelimit";
		sorter.init("table",0);
	</script> 
</BODY>
</HTML>
<%
	if (obj != null)
		obj.DBclose();
}%>