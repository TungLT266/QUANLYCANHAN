<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.banggiancc.IBangGiaNccList"%>
<%@ page import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IBangGiaNccList obj =(IBangGiaNccList)session.getAttribute("obj");
	ResultSet bglist = obj.getBanggiaRs();
	ResultSet ncc = (ResultSet)obj.getNccRs();
	ResultSet dvkd = (ResultSet)obj.getDvkd(); 
	ResultSet vung = (ResultSet)obj.getVungRs();
	ResultSet diaban = (ResultSet)obj.getDiabanRs();
	ResultSet kenh = (ResultSet)obj.getKenh(); 

	int[] quyen = new  int[6];
	quyen = util.Getquyen("BangGiaNccSvl","",userId);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	<script type="text/javascript">
		var msg = "<%=obj.getMsg()%>".trim();
		$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
			
			if(msg.length > 0 && msg !== "null") {
				alert(msg);
			}
        });	
			
	</script>
		<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	 $(document).ready(function() { $("select").select2();  });  
	</script>
	
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
		document.forms['khtt'].nccId.value= "";
	    document.forms['khtt'].bgTen.value= "";
	    document.forms['khtt'].dvkdId.value= "";
	   /*  document.forms['khtt'].kenhId.value = ""; */
	    document.forms['khtt'].trangthai.value = "";
	   /*  document.forms['khtt'].diengiai.value = ""; */
		document.forms['khtt'].submit();
	}

	function submitform()
	{
		document.forms['khtt'].action.value= 'search';
		document.forms['khtt'].submit();
	}

	function newform()
	{
		document.forms['khtt'].action.value= 'new';
		document.forms['khtt'].submit();
	}
	function xuatExcel()
	 {
	 	document.forms['khtt'].action.value= 'toExcel';
	 	document.forms['khtt'].submit();
	 }
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khtt" method="post" action="../../BangGiaNccSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Mua hàng > Bảng giá mua NCC  </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="100%" cellpadding="0" cellspacing="1">
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD width="100%" align="center" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                            	 <TR>
									<TD class="plainlabel">Tên bảng giá</TD>
									<TD class="plainlabel"><INPUT name="bgTen" type="text" size="40" value='<%=obj.getMa() %>' onChange = "submitform();"/></TD>
									
									
									<TD class="plainlabel">Đơn vị kinh doanh </TD>
								    <TD class="plainlabel"><SELECT style=" width:200px;" name="dvkdId" onChange = "submitform();">								      
								  	 		<option value =""></option>
								  	 <% try{ while(dvkd.next()){ 
								    	if(dvkd.getString("dvkdId").equals(obj.getDvkdId())){ %>
								      		<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
								      	<%}else{ %>
								     		<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
									</SELECT></TD>
								</TR>
								<TR>
								    <TD class="plainlabel">Nhà cung cấp </TD>
								    <TD class="plainlabel"><SELECT name="nccId" onChange = "submitform();" style=" width:300px;">								      
								  	 		<option value =""></option>
								  	 <% try{ while(ncc.next()){ 
								    	if(ncc.getString("pk_seq").equals(obj.getNccId())){ %>
								      		<option value='<%=ncc.getString("pk_seq") %>' selected><%=ncc.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=ncc.getString("pk_seq") %>'><%=ncc.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
									</SELECT></TD>
									
									<TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel"><select style=" width:200px;" name="trangthai" onChange = "submitform();">
								  	<% if (obj.getTrangthai().equals("0")){ %>
								    	<option value=""> </option>
								    	<option value="1">Đã chốt</option>
								    	<option value="0" selected>Chưa chốt</option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>
								    	<option value=""> </option>
								    	<option value="1" selected>Đã chốt</option>
								    	<option value="0" >Chưa chốt</option>
									<%}else{ %>
								    	<option value="" selected> </option>
								    	<option value="1" >Đã chốt</option>
								    	<option value="0" >Chưa chốt</option>
									<%}} %>
								    	</select></TD>	
								</TR>
								
								<TR>
								  <%-- <TD class="plainlabel">Nhóm kênh </TD>
								  <TD class="plainlabel">
								      	<SELECT name="kenhId" onChange = "submitform();">								      
								  	 		<option value =""></option>
								  	 <% try{ while(kenh.next()){ 
								    	if(kenh.getString("kenhId").equals(obj.getKenhId())){ %>
								      		<option value='<%=kenh.getString("kenhId") %>' selected><%=kenh.getString("kenh") %></option>
								      	<%}else{ %>
								     		<option value='<%=kenh.getString("kenhId") %>'><%=kenh.getString("kenh") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
                                  </SELECT></TD> --%>
                                  <td colspan="4" class="plainlabel" > 
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
									<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;
                             	</td> 
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
                            <LEGEND class="legendtitle">&nbsp;Bảng giá&nbsp;&nbsp;	
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
									<TH align="center" width="3%">STT</TH>
									<TH align="center" width="20%">Tên bảng giá </TH>
									<TH align="center"  width="10%">ĐVKD </TH>
									<!-- <TH align="center" width="8%">Nhóm kênh </TH> -->
									<!-- <TH width="8%">Từ ngày</TH> -->
									<TH align="center" width="8%">Trạng thái  </TH>
									<TH align="center" width="8%">Ngày tạo</TH>
									<TH align="center" width="12%">Người tạo </TH>
									<TH align="center" width="8%">Ngày sửa </TH>
									<TH align="center" width="12%">Người sửa </TH>
									<TH width="10%" align="center">&nbsp;Tác vụ</TH>
								</TR>
                                
                            <% 
							int m = 0;
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							try{
							while(bglist.next()){
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
							  <%} else {%>
									<TR class= <%= darkrow%> >
							  <%}%>
							  			<TD align="center"><%=m+1%></TD>
										<TD align="left"><%= bglist.getString("ten") %></TD>
										<TD align="left"><%= bglist.getString("dvkd") %></TD>
										<%-- <TD align="left"><%= bglist.getString("kenh") %></TD>	 --%>
										<%-- <TD align="left"><%= bglist.getString("TuNgay") %></TD> --%>
											
									<% if (bglist.getString("DADUYET").equals("1")){ %>
										<TD align="left">Đã chốt</TD>							
									<%}else{ %>
										<TD align="left">Chưa chốt</TD>
									<%} %>
									
										<TD align="center"><%= bglist.getDate("ngaytao").toString() %></TD>	
										<TD align="left"><%= bglist.getString("nguoitao") %></TD>
										<TD align="center"><%= bglist.getDate("ngaysua").toString() %></TD>
										<TD align="left"><%= bglist.getString("nguoisua") %></TD>
										<TD align="center">
											<TABLE border = 0 cellpadding="0" cellspacing="0">
																						
											<TR>											
											<TD>
											<%if(bglist.getString("DADUYET").equals("0")){ %>
												<%if(quyen[Utility.XEM]!=0) {%>
													<A href = "../../BangGiaNccUpdateSvl?userId=<%=userId%>&display=<%= bglist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
												<%}%>
												<%if(quyen[Utility.SUA]!=0) {%>
													<A href = "../../BangGiaNccUpdateSvl?userId=<%=userId%>&edit=<%= bglist.getString("pk_seq") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
												<%}
													if(quyen[Utility.XOA]!=0) {%>
													<A href = "../../BangGiaNccSvl?userId=<%=userId%>&delete=<%= bglist.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa bảng giá này ?')) return false;"></A>  
												<%} 
													if(quyen[Utility.CHOT] != 0){%>
													<A href = "../../BangGiaNccSvl?userId=<%=userId%>&duyet=<%= bglist.getString("pk_seq") %>"><img src="../images/Chot.png" alt="Cap nhat" title="Duyệt" width="20" height="20" longdesc="Cap nhat" border = 0 onclick="if(!confirm('Bạn có muốn duyệt sự thay đổi bảng giá này ?')) return false;"></A>
												<%} %>
											<%} else { %>
												<%if(quyen[Utility.XEM]!=0) {%>
													<A href = "../../BangGiaNccUpdateSvl?userId=<%=userId%>&display=<%= bglist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
												<%}%>
												<%if(quyen[Utility.SUA] != 0){%>
													<A href = "../../BangGiaNccSvl?userId=<%=userId%>&boduyet=<%= bglist.getString("pk_seq") %>"><img src="../images/unChot.png" alt="Bo duyet" title="Bỏ duyệt" width="20" height="20" longdesc="Bo duyet" border = 0 onclick="if(!confirm('Bạn có muốn bỏ duyệt bảng giá này ?')) return false;"></A>
												<%} %>
											<%} %>
											</TD>
											</TR>
											</TABLE>
										</TD>
									</TR>
								<% m++; }
								
								} catch( Exception e){ System.out.println("Exception: " + e.getMessage()); }%>
                                
                                <TR class="tbfooter" >
									<TD align="center" colspan="15"> &nbsp; </TD>
								</TR>                                                  
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
</html>
<% 	
	try
    {
		if(bglist != null)
			bglist.close();
		
		if(obj != null)
		{
			obj.DbClose();
			session.setAttribute("backAttribute",obj);
		}
		obj = null;
		session.setAttribute("obj", null);
		
	}
	catch (SQLException e) {e.printStackTrace();} %>
<%}%>