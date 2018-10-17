<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="geso.traphaco.erp.beans.nhomkhoanmuc.*"%>
<%@ page import="geso.traphaco.erp.beans.nhomkhoanmuc.imp.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
IErpNhomKhoanMuc  nkmBean = (IErpNhomKhoanMuc) session.getAttribute("nkmBean");
ResultSet rsDonViThucHien =  nkmBean.getRsDonViThucHien();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Best - Project</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<script>
	function goBack() {
	    window.history.back();
	}
	</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="lnccForm" method="post" action="../../ErpNhomKhoanMucUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>' /> 
		<input type="hidden" name="action" value='1' />
		<input type="hidden" name="id" value='<%=nkmBean.getId() %>' />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế Toán&gt; Nhóm Chi Phí > Hiển thị</TD>
										<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%> &nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					
					<table width="100%" border="0" cellpadding="3" cellspacing="0">
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr class="tbdarkrow">
										<td width="30" align="left"><a href="javascript:goBack();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset">
										</a></td>
										<td>&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table width="100%" cellspacing="0" cellpadding="4">
						<tr>
							<td align="left" colspan="4" class="legendtitle">
								<fieldset>
									<legend class="legendtitle">Thông báo </legend>
									<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1"><%= nkmBean.getMsg()%></textarea>
								</fieldset>
							</td>
						</tr>
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<fieldset>
									<LEGEND class="legendtitle">Nhóm Chi Phí </LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">
										<tr>
											<td width="15%" class="plainlabel">Mã tắt<font class="erroralert">*</font></td>
											<td class="plainlabel"><input type="text" name="Ma" id="Ma" size="40" id="Ma" value="<%= nkmBean.getMa()%>"></td>
                                             <td class="plainlabel">Tên <font class="erroralert">*</font></td>
                                             <td class="plainlabel"><input type="text" name="Ten"  id="Ten" value="<%= nkmBean.getTen()%>"></td>
										</tr>
										<tr>
											
										</tr>
								
								<TR>
									<TD class = "plainlabel">Hoạt động</TD>					
									<TD colspan="7" class = "plainlabel">
										<input name="TrangThai" checked="checked" type="checkbox" value="1">
									</TD>											
						</TR>
                                <TR>
                                    <TD class = "plainlabel">Lọc Tên/Mã KMCP</TD>         
                           
                                     <td class="plainlabel"><input type="text" name="tenKhoanMucChiPhi"  id="tenKhoanMucChiPhi" value="<%= nkmBean.getTenKhoanMucChiPhi()%>" ></td>
                              
            
                                    <TD class="plainlabel">Lọc Phòng ban KMCP</TD>
                                            <TD class="plainlabel" >
                                          <TABLE cellpadding="0" cellspacing="0" border="0">
                                            <TR>
                                                <TD><SELECT   name="dvthId" style="width:200px"  class="select2">                       
                                                <option value=""  ></option>
                                              
                                              <%  if(rsDonViThucHien != null){
                                                  try{
                                                    while (rsDonViThucHien.next()){%>
                                                      
                                                      <%  if(rsDonViThucHien.getString("DVTTID").equals(nkmBean.getDvthid())){ %>                      
                                                          <option value= <%=rsDonViThucHien.getString("DVTTID")%> selected><%= rsDonViThucHien.getString("TEN") %></option>                             
                                                      <%}else{%>
                                                          <option value= <%=rsDonViThucHien.getString("DVTTID")%> ><%= rsDonViThucHien.getString("TEN") %></option>                                                                   
                                                      <%  }
                                                    }
                                                    
                                                  }catch(java.sql.SQLException e){}
                                                }
                                                  %>                                                                       
                                                                          </SELECT>
                                                                    </TD>
                                            </TR>
                                          </TABLE>                  
                                        </TD>                  
                              </TR>
                        <TR>
              <td colspan="7">
                              
                <ul class="tabs">
                  <li><a href="#" class= "legendtitle"> Khoản mục chi phí</a></li>                            
                                
                </ul>
                  
                    <div class="panes">
                                                        <div align = "left">
                                        <TABLE style="width: 70%" cellpadding="4px" cellspacing="1px" align="center">
                                          <TR><TD class= "legendtitle" >Chọn khoản mục chi phí</TD></TR>
                                        </TABLE>
                                      
                                        <TABLE style="width: 70%" cellpadding="4px" cellspacing="1px" align="center">
                                          <TR class="tbheader">
                                            <TH width="20%">Mã khoản mục chi phí</TH>
                                            <TH width="70%">Diễn giải</TH>
                                            <TH width="10%">Chọn</TH>
                                          </TR>
                  
                                       <% 
                                    int i = 0;
                                                  String lightrow = "tblightrow";
                                                  String darkrow = "tbdarkrow";
                                      
                                                  List<ErpKhoanMucChiPhi> listKhoanMuc =  nkmBean.getListKhoanMuc();
                                                      
                                                  if(listKhoanMuc != null){
                                              for(ErpKhoanMucChiPhi kmcp :listKhoanMuc){
                                          if (i % 2 != 0) {   %>
                                                                 
                                                                <TR class= <%=lightrow%> >
                                                                  
                                                     <%     } else {%>
                                                                  
                                                              <TR class= <%= darkrow%> >
                                                                   
                                                   <%     }%>
                                       
                                          <TD align = "center"> 
                                           <INPUT TYPE = "HIDDEN" name="kmcpId" value="<%= kmcp.getIdKhoanMuc() %>" >
                                           <INPUT TYPE = "HIDDEN" name="kmcpMa" value="<%= kmcp.getMaKhoanMuc() %>" >
                                           <INPUT TYPE = "HIDDEN" name="kmcpTen" value="<%= kmcp.getTenKhoanMuc() %>" >
                                          <%= kmcp.getMaKhoanMuc() %></TD>
                                          <TD align = "center"> <%= kmcp.getTenKhoanMuc() %>  </TD>
                                            
                                          <TD align = "center">
                                                <% if(kmcp.getChecked().equals("1")) { %>
				                    						<input type="checkbox" name="kmcpCheck" value="1" checked="checked" disabled="disabled" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="kmcpCheck" value="0"  disabled="disabled" >
				                    					<% } %>
                                         
                                          </TD>
                  
                                                              
                                         </TR>
                                                                    
                                    <%    i++;
                                      }

                                    }%> 
                                      
                                        </TABLE>  
                                      </div>
                
                    </div>
                                        
           
              </td>
            </TR>				
								
					</table>
					</fieldset>
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</form>
</body>
</html>
<%
nkmBean.closeDB();
	
}%>