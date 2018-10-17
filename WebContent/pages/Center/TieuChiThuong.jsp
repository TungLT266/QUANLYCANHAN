<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.tieuchithuong.ITieuchithuongList" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% 	ITieuchithuongList obj = (ITieuchithuongList)session.getAttribute("obj"); 

	ResultSet dvkd = obj.getdvkd();
	String dvkdId = obj.getdvkdId();	

	ResultSet kbh = obj.getKbh();
	String kbhId = obj.getkbhId();	
	
	ResultSet tct = obj.getTct();
	int[] quyen = new  int[5];
	quyen = util.Getquyen("TieuChiThuongSvl","",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);
	System.out.println(quyen[4]);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
 function submitForm()
{	
	
	document.tctForm.action.value='timkiem';
    document.forms["tctForm"].submit();
}
 

/*  function checklimit(m)
 {
	 var boo = false;
 	 if(document.getElementById("ngaysua"+m).value != "")
 		{		
 			var now = new Date();
 			var month = now.getMonth() + 1;
 			var date = now.getDate();
 			if(month < 10)
 			{month = "0"+month;}	
 			if(date < 10)
 			{date = "0"+date;}
 			var ngaysua = Date.parse(document.getElementById("ngaysua"+m).value);
 			var ngayhientai = Date.parse(now.getFullYear()+"-"+month+"-"+date);	
 			if(ngayhientai - ngaysua > 0)
 			{
 				
 				alert('Tiêu chí này đã hết hạn mở chốt. Vui lòng liên hệ quản trị !');
 				return;
 			}
 			boo = true;
 			
 	}
 	// document.forms["ddkdForm"].submit();
 } */


</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tctForm" method="post" action="../../TieuChiThuongSvl">
<input type="hidden" name="userId" value='<%= obj.getUserId() %>'>
<input type="hidden" name="action" value="1">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền  &gt; Dữ liệu kinh doanh &gt; Tiêu chí tính thưởng </TD>
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>

			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
				<TR>
					<TD>

						<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
							<TR>
								<TD>
									<FIELDSET>
									<LEGEND class="legendtitle" style="color:black">Tiêu chí tìm kiếm </LEGEND>
	
									<TABLE border="0" width="100%" cellpadding="4" cellspacing="0">
									
									<TR>
									  	 <TD class="plainlabel" colspan=2>
											<%if(obj.gethethongBH().equals("100000")){ %>									  	 
								  	 			<INPUT TYPE="radio" NAME="htbh" value="100000" checked onChange="submitForm();" >ETC
								  	 			<INPUT TYPE="radio" NAME="htbh" value="100001" onChange="submitForm();">OTC
								  	 			
								  	 		<%}else{
							  	 				if(obj.gethethongBH().equals("100001")){ %>
						  	 					<INPUT TYPE="radio" NAME="htbh" value="100000" onChange="submitForm();" >ETC
						  	 					<INPUT TYPE="radio" NAME="htbh" value="100001" checked onChange="submitForm();">OTC
					
							  	 			<%}else{%>
						  	 					<INPUT TYPE="radio" NAME="htbh" value="100000" onChange="submitForm();" >ETC
						  	 					<INPUT TYPE="radio" NAME="htbh" value="100001" onChange="submitForm();">OTC
								  	 				
									  	 			
								  	 		  <%}}%>
									  	 </TD>
									</TR>
									
										<TR>
										  	 <TD class="plainlabel" colspan=2>
												<%if(obj.getLoai().equals("1")){ %>									  	 
									  	 			<INPUT TYPE="radio" NAME="loai" value="1" checked onChange="submitForm();" >SR
									  	 			<INPUT TYPE="radio" NAME="loai" value="2" onChange="submitForm();">SS
									  	 			<INPUT TYPE="radio" NAME="loai" value="3" onChange="submitForm();">ASM
									  	 			
									  	 		<%}else{
									  	 				if(obj.getLoai().equals("2")){ %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="1" onChange="submitForm();">SR
									  	 					<INPUT TYPE="radio" NAME="loai" value="2" checked onChange="submitForm();">SS									  	 		
									  	 					<INPUT TYPE="radio" NAME="loai" value="3" onChange="submitForm();">ASM
									  	 					
									  	 			  <%}else{ 
									  	 				 if(obj.getLoai().equals("3")){ %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="1" onChange="submitForm();">SR
									  	 					<INPUT TYPE="radio" NAME="loai" value="2" onChange="submitForm();">SS
									  	 					<INPUT TYPE="radio" NAME="loai" value="3" checked onChange="submitForm();">ASM
									  	 				
										  	 			<%}else{%>
									  	 					<INPUT TYPE="radio" NAME="loai" value="1" onChange="submitForm();">SR
									  	 					<INPUT TYPE="radio" NAME="loai" value="2" onChange="submitForm();">SS
									  	 					<INPUT TYPE="radio" NAME="loai" value="3" onChange="submitForm();">ASM
									  	 				
										  	 			
									  	 		  <%}}}%>
										  	 </TD>
										</TR>
										
										
										<TR style="display: none">
							  				<TD class="plainlabel" width = "20%" >Kênh bán hàng </TD>
						  	  				<TD class="plainlabel"  >
												<select name="kbhId" id="kbhId" onChange="submitForm();">
												<option value="" > </option>
												<%
													if(kbh != null){
													try{
														while(kbh.next()){
								  							if (kbhId.equals(kbh.getString("kbhId"))){ %>											
								  								<option value='<%= kbh.getString("kbhId")%>' selected><%= kbh.getString("kbh")%></option>
								  		  					<%}else{ %>		
								  		  						<option value='<%= kbh.getString("kbhId")%>' ><%= kbh.getString("kbh")%></option>
								  							<%		}
								  						}
														kbh.close();
								  					}catch (java.sql.SQLException e){}} %>
															
										  	  	</select>											
										  	 </TD>
										</TR>

										<TR style="display: none">
							  				<TD class="plainlabel"  >Đơn vị kinh doanh</TD>
						  	  				<TD class="plainlabel"  >
												<select name="dvkdId" id="dvkdId" onChange="submitForm();">
												<option value="" > </option>
												<%
													if(dvkd != null){
													try{
														while(dvkd.next()){
								  							if (dvkdId.equals(dvkd.getString("dvkdId"))){ %>											
								  								<option value='<%= dvkd.getString("dvkdId")%>' selected><%= dvkd.getString("dvkd")%></option>
								  		  					<%}else{ %>		
								  		  						<option value='<%= dvkd.getString("dvkdId")%>' ><%= dvkd.getString("dvkd")%></option>
								  							<%		}
								  						}
														dvkd.close();
								  					}catch (java.sql.SQLException e){}} %>
															
										  	  	</select>											
										  	 </TD>
										</TR>

								<TR>	
									<TD class="plainlabel">Chọn tháng</TD>
									<TD class="plainlabel" >										
										<SELECT name = "month" onChange="submitForm();">
											<option value = "" selected>&nbsp</option>
										<%if(obj.getMonth().equals("01")){ %>
											<option value = "01" selected>T1</option>
										<%}else{ %>
											<option value = "01">T1</option>
										<%} %>

										<%if(obj.getMonth().equals("02")){ %>
											<option value = "02" selected>T2</option>
										<%}else{ %>
											<option value = "02">T2</option>
										<%} %>

										<%if(obj.getMonth().equals("03")){ %>
											<option value = "03" selected>T3</option>
										<%}else{ %>
											<option value = "03">T3</option>
										<%} %>

										<%if(obj.getMonth().equals("04")){ %>
											<option value = "04" selected>T4</option>
										<%}else{ %>
											<option value = "04">T4</option>
										<%} %>

										<%if(obj.getMonth().equals("05")){ %>
											<option value = "05" selected>T5</option>
										<%}else{ %>
											<option value = "05">T5</option>
										<%} %>

										<%if(obj.getMonth().equals("06")){ %>
											<option value = "06" selected>T6</option>
										<%}else{ %>
											<option value = "06">T6</option>
										<%} %>

										<%if(obj.getMonth().equals("07")){ %>
											<option value = "07" selected>T7</option>
										<%}else{ %>
											<option value = "07">T7</option>
										<%} %>

										<%if(obj.getMonth().equals("08")){ %>
											<option value = "08" selected>T8</option>
										<%}else{ %>
											<option value = "08">T8</option>
										<%} %>
										
										<%if(obj.getMonth().equals("09")){ %>
											<option value = "09" selected>T9</option>
										<%}else{ %>
											<option value = "09">T9</option>
										<%} %>
										
										<%if(obj.getMonth().equals("10")){ %>
											<option value = "10" selected>T10</option>
										<%}else{ %>
											<option value = "10">T10</option>
										<%} %>
										
										<%if(obj.getMonth().equals("11")){ %>
											<option value = "11" selected>T11</option>
										<%}else{ %>
											<option value = "11">T11</option>
										<%} %>
										
										<%if(obj.getMonth().equals("12")){ %>
											<option value = "12" selected>T12</option>
										<%}else{ %>
											<option value = "12">T12</option>
										<%} %>
										</SELECT>									
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel">Chọn năm</TD>
									
									<TD class="plainlabel" >
										<SELECT name = "year" onChange="submitForm();">
											<option value = "" selected>&nbsp</option>
										<%if(obj.getYear().equals("2012")){ %>
											<option value= "2012" selected>2012</option>
										<%}else{ %>
											<option value= "2012" >2012</option>
										<%} %>
																						
										<%if(obj.getYear().equals("2013")){ %>
											<option value= "2013" selected>2013</option>
										<%}else{ %>
											<option value= "2013" >2013</option>
										<%} %>

										</SELECT>									
									</TD>
								</TR>

								</TABLE>
								</FIELDSET>
																	
									<TABLE width = "100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<FIELDSET>
												<%if(obj.getLoai().equals("1")){ %>
												<LEGEND class="legendtitle">&nbsp;Tiêu chi tính thưởng cho SR</LEGEND>
												<%}else{ 
													if(obj.getLoai().equals("2")){%>																		
														<LEGEND class="legendtitle">&nbsp;Tiêu chi tính thưởng cho SS</LEGEND>
													<%}else{
														if(obj.getLoai().equals("3")){%>								 
														<LEGEND class="legendtitle">&nbsp;Tiêu chi tính thưởng cho ASM/SSS</LEGEND>
													<%}else{ %>
														<LEGEND class="legendtitle">&nbsp;Tiêu chi tính thưởng cho BM</LEGEND>
													<%}}} %>
												<TABLE  border="0" cellpadding="4"  cellspacing="1" width="100%">
															<TR class="tbheader" >
																<TH width="40%" >Diễn giải </TH>
																<!-- <TH width="10%" >Kênh bán hàng</TH>															
																<TH width="12%" >Đơn vị kinh doanh </TH> -->
																<TH width="5%" >Tháng </TH>
																<TH width="5%" >Năm</TH>
																<TH width="14%" >Người tạo</TH>
																<TH width="7%" >Ngày tạo</TH>
																<TH width="14%" >Người sửa</TH>
																<TH width="7%" >Ngày sửa</TH>
																<TH width="8%" >Tác vụ</TH>
															</TR>
												<%	
												if(tct != null){
													try{
														int m = 0;
														while(tct.next()){
																					
															if(m%2 == 0){						%>
															<TR class= 'tblightrow'>
															<%}else{ %>
															<TR class= 'tbdarkrow'>
															<%} %>
											  					<TD align="left" ><%= tct.getString("diengiai") %></TD>								

											  				<%-- 	<TD align="center" ><%= tct.getString("kbh") %></TD>
																
																<TD align="center" > <%= tct.getString("dvkd") %></TD> --%>
																
											  					<TD align="center" ><%= tct.getString("thang") %> </TD>
														
																
											  					<TD align="center" ><%= tct.getString("nam") %> </TD>
																
																<TD align="center"><%=tct.getString("nguoitao")%></TD>	
																
																<TD align="center"><%= tct.getString("ngaytao").substring(0,10) %></TD>
															
																<TD align="center"><%=tct.getString("nguoisua")%></TD>
																
																<TD align="center"><%=tct.getString("ngaysua").substring(0,10)%>
																<input type="hidden" id = "ngaysua<%=m %>" value = "<%=tct.getString("ngaysua").substring(0,10)%>">
																</TD>
																
																
																<TD align="center">
																	<TABLE border = 0 cellpadding="0" cellspacing="0">
																		<TR>
																		<%if(!tct.getString("trangthai").equals("1")){ %>
																			<TD>
																				<%if(quyen[2]!=0) {%>
													  							<A href = "../../TieuChiThuongUpdateSvl?userId=<%=userId%>&capnhat=<%= tct.getString("pk_seq")%>">
                                               									<img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
																				<%} %>
																			</TD>
																			<TD>&nbsp;</TD>
																			<TD>
																				<%if(quyen[1]!=0) {%>
														  						<A href = "../../TieuChiThuongSvl?userId=<%=userId%>&xoa=<%= tct.getString("pk_seq") %>;<%=obj.getLoai()%>">
    	                                            							<img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa tiêu chí tính thưởng này?')) return false;"></A>
        	                                        							<%} %>
        	                                        						</TD>
            	                                    						<TD>&nbsp;</TD>
            	                                    					
																			<TD>
																			<%if(quyen[4]!=0){ %>
													  						<A href = "../../TieuChiThuongSvl?userId=<%=userId%>&chot=<%= tct.getString("pk_seq") %>;<%=obj.getLoai()%>">
                                                							<img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border=0 onclick="if(!confirm('Bạn có muốn chốt tiêu chí tính thưởng này?')) return false;"></A>
                                                							<%} %>
                                                							</TD>
																	 	<%}else{ %>
																			<TD>
																			<%if(quyen[3]!=0){ %>
													  							<A href = "../../TieuChiThuongUpdateSvl?userId=<%=userId%>&hienthi=<%= tct.getString("pk_seq")%>">
                                               									<img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
																			<%} %>
																			</TD>
																			
																			<TD>
													  							<A href = "../../TieuChiThuongSvl?userId=<%=userId%>&mochot=<%= tct.getString("pk_seq")%>">
                                               									<img src="../images/unChot.png" alt="Mở chốt" width="20" height="20" longdesc="Mo chot" border = 0></A>
																			</TD>
            	                                    						
																			<TD>
																				<%if(quyen[0]!=0){ %>
													  							<A href = "../../TieuChiThuongUpdateSvl?userId=<%=userId%>&copy=<%= tct.getString("pk_seq")%>">
                                               									<img src="../images/convert.gif" alt="Sao chep" width="20" height="20" longdesc="Sao chep" border = 0></A>
																				<%} %>
																			</TD>
            	                                    					
																		<%} %>																	
																		</TR>
																		
																	</TABLE>
																</TD>
																
															</TR>
												<%	m++;															
														}
														}catch(java.sql.SQLException e){}
														  	
												 }%>
												 <tr class="tbfooter" > 
													 <TD align="center" valign="middle" colspan="10" class="tbfooter">&nbsp;</TD>
												</tr>
												</TABLE>
												</FIELDSET>
											</TD>
										</TR>
										
										
									</TABLE>
								</TD>
							</TR>
						</TABLE>
						
					</TD>
	      		</TR>
		  	</TABLE>
		</TD>
	
</TABLE>
</form>
</BODY>
</HTML>

<% 	if(dvkd != null) dvkd.close(); 
	if(tct != null) tct.close();
	obj.closeDB();
}%>