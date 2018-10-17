<%@page import="geso.traphaco.erp.beans.doiquycach.IDoiquycachList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% 	IDoiquycachList obj = (IDoiquycachList)session.getAttribute("obj"); %>
<% obj.setNextSplittings(); %>
<% 	ResultSet dqclist = (ResultSet)obj.getDqcRS();
	ResultSet ndqcRs = null;
	int[] quyen = new  int[5];
	quyen = util.Getquyen("DoiquycachSvl","",userId);
%>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
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
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();
	});
</script>

  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax-dynamic-list.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>	
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
	

<SCRIPT language="javascript" type="text/javascript">

function clearform()
{  
	document.forms['dqcForm'].spTen.value= '';
	document.forms['dqcForm'].tungay.value= '';
	document.forms['dqcForm'].denngay.value= '';
	document.forms['dqcForm'].sochungtu.value= '';
	document.forms['dqcForm'].trangthai.value= '';
	document.forms['dqcForm'].nguoitao.value= '';
	document.forms['dqcForm'].spnTen.value= '';
	
    document.forms['dqcForm'].submit();
}

function submitform()
{
	document.forms['dqcForm'].action.value= 'search';
	document.forms['dqcForm'].submit();
}

function newform()
{
	document.forms['dqcForm'].action.value= 'new';
	document.forms['dqcForm'].submit();
}
function deleteform()
{
	document.forms['dqcForm'].action.value= 'delete';
	document.forms['dqcForm'].submit();
}
function thongbao()
{
	if(document.getElementById("msg").value != '') {
		alert(document.getElementById("msg").value);
	}
}

</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" >
<form name="dqcForm" method="post" action="../../DoiquycachSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
<script type="text/javascript">
	thongbao();
</script>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		&nbsp;Quản lý cung ứng &gt; Quản lý tồn kho &gt; Đổi quy cách
							   </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %> &nbsp;</td> 
						    </tr>
						</table>
					</TD>
				</TR>
				<TR>
					<TD>
						<TABLE border="0" width="100%" >
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm  </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
											<TD width="19%" class="plainlabel" >Sản phẩm xuất </TD>
										  <TD width="81%" class="plainlabel" ><INPUT style=" id = "spTen" name="spTen" type="text" size="40" value="<%=obj.getSpTen() %>" onchange="submitform()" ></TD>
										</TR>
										
										<TR>
											<TD width="19%" class="plainlabel" >Sản phẩm nhận </TD>
										  <TD width="81%" class="plainlabel" ><INPUT style=" id = "spnTen" name="spnTen" type="text" size="40" value="<%=obj.getSpnTen() %>" onchange="submitform()" ></TD>
										</TR>
										
										<TR>
											<TD width="19%" class="plainlabel" >Từ ngày</TD>
										  <TD width="81%" class="plainlabel" ><INPUT class="days" name="tungay" type="text" size="40" value="<%=obj.getTungay() %>" onchange="submitform()" ></TD>
										</TR>
										
										<TR>
											<TD width="19%" class="plainlabel" >Đến ngày</TD>
										  <TD width="81%" class="plainlabel" ><INPUT class="days" name="denngay" type="text" size="40" value="<%=obj.getDenngay() %>" onchange="submitform()" ></TD>
										</TR>
										
											<TR>
											<TD width="19%" class="plainlabel" >Số chứng từ</TD>
										  <TD width="81%" class="plainlabel" ><INPUT  name="sochungtu" type="text" size="40" value="<%=obj.getSochungtu() %>" onchange="submitform()" ></TD>
										</TR>
										
										
										<TR>
											<TD width="19%" class="plainlabel" >Người tạo</TD>
										  <TD width="81%" class="plainlabel" ><INPUT  name="nguoitao" type="text" size="40" value="<%=obj.getNguoitao() %>" onchange="submitform()" ></TD>
										</TR>
							
										
										<TR>
											<TD width="19%" class="plainlabel" >Trạng thái </TD>
										  <TD width="81%" class="plainlabel" > 
										  	<% 
                    	String mang[] =new String[]{"0","1","2"};
                    	String mangten[] =new String[]{"Chưa hoàn tất","Hoàn tất","Đã xóa" };
                    	%>
                    	<select name="trangthai"  onchange="submitform()">
                    	 <option value="">  </option> 
                    	<%
                    	for(int j=0;j<mang.length;j++){
							 if(obj.gettrangthai().trim().equals(mang[j])){
							 %>
							 <option selected="selected" value="<%=mang[j]%>"> <%=mangten[j] %> </option>
							 <%
							 }else{
							 %>
							 <option value="<%=mang[j]%>"> <%=mangten[j] %> </option>
							 <% 
							 }
						 }
                    	%>
                    	</select> 
										  </TD>
										</TR>
										
										<TR>
										    <TD class="plainlabel" colspan=4>
										    <a class="button2" href="javascript:clearform()">
													<img style="top: -4px;" src="../images/button.png" alt="" onClick="clearform();">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
	
											<a class="button2" onClick="submitform();">
    												<img style="top: -4px;" src="../images/Display.png"  >Tìm kiếm  </a>
    										                 
							

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
					<TD align="left" >
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Đổi quy cách &nbsp;&nbsp;&nbsp;&nbsp; 
							<%if(quyen[0]!=0){ %>  
							<a class="button3" onClick="newform();">
    							<img style="top: -4px;" src="../images/New.png"  >Tạo mới  </a>
    								<%} %>                            
							
							</LEGEND>
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
											<TH width="5%">Số chứng từ</Th>
											<TH width="8%">Ngày</TH>
											 
											<TH width="10%">Trạng thái</TH>
											<TH width="10%">Người tạo</TH>
											<TH width="8%">Ngày tạo</TH>
											<TH width="10%">Người sửa</TH>
											<TH width="8%">Ngày sửa</TH>
											<TH width="10%">Tác vụ</TH>
										</TR>
								     <%
								String lightrow = "tblightrow";
	                            String darkrow = "tbdarkrow";
	                            int i =0; 
								if(dqclist != null){
	                                 while(dqclist.next()) 
								     {
								    	 if (i % 2 != 0) {%>                     
                                         <TR align="center" align = "left" class=<%=lightrow%> >
                                     <%} else { %>
                                         <TR align="center" align = "left" class=<%= darkrow%> >
                                     <%}%>
                                     <TD ><%=dqclist.getString("DQCID") %></TD>
                                     		<TD ><%=dqclist.getString("NGAY") %></TD>
								     		 
										<% 	if(dqclist.getString("TRANGTHAI").equals("0")){ %>
											 <TD >Chưa hoàn tất</TD>
										<%	}else if(dqclist.getString("TRANGTHAI").equals("1")){ %>
											 <TD >Đã hoàn tất</TD>
										<%	} else {%>
											<TD >Đã xóa</TD>
										<%} %>
										    <TH width="10%"><%=dqclist.getString("nguoitao") %></TH>
											<TH width="10%"><%=dqclist.getString("ngaytao") %></TH>
											<TH width="10%"><%=dqclist.getString("nguoisua") %></TH>
											<TH width="10%"><%=dqclist.getString("ngaysua") %></TH>
											
											<TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                <TR>
                                            <% if(dqclist.getString("TRANGTHAI").equals("0")){  %>
                                                    <TD>

                                                     	<A href = "../../DoiquycachUpdateSvl?userId=<%=userId%>&update=<%=dqclist.getString("DQCID") %>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                    <TD>
                                                        <A  onclick="if(!confirm('Bạn có chắc chốt phiếu xuất kho này?')) {return false;}" href="../../DoiquycachUpdateSvl?userId=<%=userId%>&delete=<%=dqclist.getString("DQCID") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 ></A>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                    <TD>
                                                        <A  onclick="if(!confirm('Bạn có chắc chốt phiếu xuất kho này?')) {return false;}" href="../../DoiquycachUpdateSvl?userId=<%=userId%>&finish=<%=dqclist.getString("DQCID") %>"><img src="../images/Chot.png" alt="Hoàn tất" width="20" height="20" longdesc="Hoàn tất" border=0 ></A>
                                                    </TD>
                                                    <TD>&nbsp;</TD>

                                                    
                                             <%}else{ %>
                                                    <TD>
                                                     	<A href = "../../DoiquycachUpdateSvl?userId=<%=userId%>&display=<%=dqclist.getString("DQCID") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hienthi" border = 0></A>
                                                    </TD>                                             
                                             
                                             <%} %>
                                             </TR>
                                             </TABLE>
                                           </TD>
								         </TR>
							          <% i++;
							          } 
								}
							      %>
							          
								<tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('dqcForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View('dqcForm', eval(dqcForm.nxtApprSplitting.value) -1, 'view')">
															&nbsp; <%}else{ %> <img alt="Trang Truoc"
															src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int ii = 0; ii < listPage.length; ii++){
												%> <% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[ii]);
												
												if(listPage[ii] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[ii] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('dqcForm', <%= listPage[ii] %>, 'view')"><%= listPage[ii] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[ii] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('dqcForm', eval(dqcForm.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('dqcForm', -1, 'view')"> &nbsp; <%} %>
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
<script type="text/javascript">
	//Call dropdowncontent.init("anchorID", "positionString", glideduration, "revealBehavior") at the end of the page:
	dropdowncontent.init("searchlink", "right-bottom", 600, "click");
	jQuery(function()
	{		
		$("#spTen").autocomplete("SanPhamList.jsp");
	});	
</script>

</form>
</BODY>
</HTML>
<%
	if(ndqcRs != null) ndqcRs.close();
	if(dqclist!=null){
		dqclist.close();
	}
	if(obj!=null){
		obj.DbClose();
	}
	
	session.setAttribute("obj",null);
	
}%>