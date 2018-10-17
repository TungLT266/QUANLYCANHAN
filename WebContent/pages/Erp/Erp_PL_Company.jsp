<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.*" %>
<% 	ILapngansach lnsBean = (ILapngansach)session.getAttribute("lnsBean"); %>
<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen");  %>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>    

								<TR class="tbheader">
						<% 	String[] COGS = new String[12];
							String[] SALES = new String[12];
							String[] NETPROFITBTAX = new String[12];
							for(int i = 0; i < 12; i++){
								SALES[i] = "0";
								COGS[i]  = "0";
								NETPROFITBTAX[i] = "0";
							}
						%>
						
									<TD>SALES VOLUME (KG)</TD>
							<% 	String[] data = lnsBean.PL_SalesVolumn();
								for(int i = 0; i < 12; i++){
									double so_=0;
									try{
										
									 so_=Double.parseDouble(data[i].replaceAll(",",""));
									
									}catch(Exception err){}
									%>
		
									<TD align = right ><%= formatter.format(so_) %></TD> 			
							<%  } %> 					
									 
								</TR>
					
						<% 	int m = 0; 
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>Lamination</TD>
						
							<% 	data = lnsBean.PL_SalesVolumn_LA();
								for(int i = 0; i < 12; i++){ 	
									
									double so_=0;
									try{
										
									 so_=Double.parseDouble(data[i].replaceAll(",",""));
									
									}catch(Exception err){}
									
									%>
		
									<TD align = right ><%= formatter.format(so_) %></TD> 			
							<%  } %> 					
							  
															
								</TR>

						<% 	m++; 
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>

									<TD>Paper core</TD>
							<% 	data = lnsBean.PL_SalesVolumn_PA();
								for(int i = 0; i < 12; i++){  
									
									double so_=0;
									try{
										
									 so_=Double.parseDouble(data[i].replaceAll(",",""));
									
									}catch(Exception err){}
									
									%>
		
									<TD align = right ><%= formatter.format(so_) %></TD> 			
							<%  } %> 					

								</TR>

								<TR class="tbheader">
									<TD>SALES AMOUNT (NET OF TAX)</TD>
						<%	String[] tmp = lnsBean.PL_SalesAmount(); 
							for(int i = 0; i < 12; i++){ 
								
								double sales_=0;
								try{
									
									sales_=Double.parseDouble(SALES[i].replaceAll(",",""));
								
								}catch(Exception err){}
								double tmp_=0;
								try{
									
									tmp_=Double.parseDouble(tmp[i].replaceAll(",",""));
								
								}catch(Exception err){}
								
								
								
									SALES[i] = "" + (sales_ +  tmp_);
							%>			
												
									<TD align = right ><%= formatter.format(tmp_) %></TD>			
						<%	} %>								
								</TR>

						<% 	m = 0; 
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>Sales</TD>

						<%	for(int i = 0; i < 12; i++){ 		 
							
							double tmp_=0;
							try{
								
								tmp_=Double.parseDouble(tmp[i].replaceAll(",",""));
							
							}catch(Exception err){}
							%>			
										
									<TD align = right ><%= formatter.format(tmp_) %></TD>			
						<%	} 
							
						%>								
								</TR>
		
						<% 	m++; 
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>Sales return/allowance</TD>

						<%	for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right >&nbsp;</TD>			
						<%	} 
							
						%>								
								</TR>

						<% 	m++; 
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>Others</TD>

						<%	for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right >&nbsp;</TD>			
						<%	} 
							
							%>
								</TR>

								<TR class="tbheader">
									<TD>MATERIALS CONSUMED</TD>
						<%	tmp = lnsBean.PL_MaterialConsume(); 
							for(int i = 0; i < 12; i++){ 	
								
								double tmp_=0;
								try{
									
									tmp_=Double.parseDouble(tmp[i].replaceAll(",",""));
								
								}catch(Exception err){}
								
								double COGS_=0;
								try{
									
									COGS_=Double.parseDouble(COGS[i].replaceAll(",",""));
								
								}catch(Exception err){}
								
								
								
								COGS[i] = "" + (COGS_ +  tmp_);
							%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} %>								
								</TR>

						<% 	m = 0; 
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>PAPER CONSUMED</TD>

						<%	tmp = lnsBean.PL_MaterialConsume_Paper();
							for(int i = 0; i < 12; i++){  
								
								double tmp_=0;
								try{
									
									tmp_=Double.parseDouble(tmp[i].replaceAll(",",""));
								
								}catch(Exception err){}
								%>			
												
									<TD align = right ><%= formatter.format(tmp_) %></TD>			
						<%	} 
							
						%>								
								</TR>
						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>FOIL CONSUMED</TD>

						<%	tmp = lnsBean.PL_MaterialConsume_Foil();
							for(int i = 0; i < 12; i++){  
								
								double tmp_=0;
								try{
									
									tmp_=Double.parseDouble(tmp[i].replaceAll(",",""));
								
								}catch(Exception err){}
								%>			
												
									<TD align = right ><%= formatter.format(tmp_) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>GLUE CONSUMED</TD>

						<%	tmp = lnsBean.PL_MaterialConsume_Glue();
							for(int i = 0; i < 12; i++){  
								
								double tmp_=0;
								try{
									
									tmp_=Double.parseDouble(tmp[i].replaceAll(",",""));
								
								}catch(Exception err){}
								
								%>			
												
									<TD align = right ><%= formatter.format(tmp_) %></TD>			
						<%	} 
							
						%>								
								</TR>
						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>LACQUER CONSUMED</TD>

						<%	tmp = lnsBean.PL_MaterialConsume_Lacquer();
							for(int i = 0; i < 12; i++){  
								
								
								double tmp_=0;
								try{
									
									tmp_=Double.parseDouble(tmp[i].replaceAll(",",""));
								
								}catch(Exception err){}
								
								%>			
												
									<TD align = right ><%= formatter.format(tmp_) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>SUB-MATERIAL CONSUMED</TD>

						<%	tmp = lnsBean.PL_MaterialConsume_Submaterial();
							for(int i = 0; i < 12; i++){ 
								double tmp_=0;
								try{
									
									tmp_=Double.parseDouble(tmp[i].replaceAll(",",""));
								
								}catch(Exception err){}
								
								%>			
												
									<TD align = right ><%= formatter.format(tmp_) %></TD>			
						<%	} 
							
						%>								
								</TR>

								<TR class="tbheader">
									<TD>DIRECT LABOUR(LUONG TRUC TIEP)</TD>
						<%	tmp = lnsBean.PL_DirectLabour(); 
							for(int i = 0; i < 12; i++){ 			
									COGS[i] = "" + (Double.parseDouble(COGS[i]) +  Double.parseDouble(tmp[i]));
							%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} %>								
								</TR>


						<%	m = 0;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>WORKERS' SALARY</TD>

						<%	tmp = lnsBean.PL_WorkerSalary();
							for(int i = 0; i < 12; i++){ 	
								double tmp_=0;
								try{
									
									tmp_=Double.parseDouble(tmp[i].replaceAll(",",""));
								
								}catch(Exception err){}
								
								%>			
												
									<TD align = right ><%= formatter.format(tmp_) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>OVERTIME FOR WORKERS</TD>

						<%	tmp = lnsBean.PL_OvertimeWorkers();
							for(int i = 0; i < 12; i++){ 		
								double tmp_=0;
								try{
									
									tmp_=Double.parseDouble(tmp[i].replaceAll(",",""));
								
								}catch(Exception err){}
								
								%>			
												
									<TD align = right ><%= formatter.format(tmp_) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>AMORTIZED 13 TH SALARY OF WORKERS</TD>

						<%	tmp = lnsBean.PL_AmortizedWorkers();
							for(int i = 0; i < 12; i++){  
								double tmp_=0;
								try{
									
									tmp_=Double.parseDouble(tmp[i].replaceAll(",",""));
								
								}catch(Exception err){}
								
								%>			
												
									<TD align = right ><%= formatter.format(tmp_) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>SOCIAL INSURANCE+UNION FEE (WORKERS)</TD>

						<%	tmp = lnsBean.PL_SocialInsurance();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>HEALTH INSURANCE (WORKERS)</TD>

						<%	tmp = lnsBean.PL_HealthInsurance();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>



								<TR class="tbheader">
									<TD>PRODUCTION OVERHEAD(CPHI SAN XUAT)</TD>
						<%	tmp = lnsBean.PL_ProductionOverhead(); 
							for(int i = 0; i < 12; i++){ 			
									COGS[i] = "" + (Double.parseDouble(COGS[i]) +  Double.parseDouble(tmp[i]));
							%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} %>								
								</TR>


						<%	m = 0;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>ALLOWANCES FOR WORKERS</TD>

						<%	tmp = lnsBean.PL_WorkerAllowances();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>


						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>BONUS FOR WORKERS</TD>

						<%	tmp = lnsBean.PL_WorkerBonus() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>DEPRECIATION OF BUILDING</TD>

						<%	tmp = lnsBean.PL_DepreciationBuilding_Production() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>DEPRECIATION MACHINE</TD>

						<%	tmp = lnsBean.PL_DepreciationMachine() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>DEPRECIATION MOTOR VEHICLE FOR FACTORY</TD>

						<%	tmp = lnsBean.PL_DepreciationMotorVehicle_Production() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>RENTAL CHARGE OF MACHINERY</TD>

						<%	tmp = lnsBean.PL_RentalCharge_Machinery() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>ELECTRICITY+WATER(FACT.)</TD>

						<%	tmp = lnsBean.PL_ElectricityWater() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>


						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>FACTORY EXPENSES</TD>

						<%	tmp = lnsBean.PL_FactoryExpenses() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>PRINTING & STATIONERY (FACTORY)</TD>

						<%	tmp = lnsBean.PL_Printing_Stationery() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>MEALS FOR WORKERS</TD>

						<%	tmp = lnsBean.PL_Workers_Meals() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>UPKEEP OF MOTORVEHICLE</TD>

						<%	tmp = lnsBean.PL_Upkeep_Motorvehicle() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>UPKEEP OF MACHINERY</TD>

						<%	tmp = lnsBean.PL_Upkeep_Machinery() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>TRAINING COST</TD>

						<%	tmp = lnsBean.PL_TrainingCost() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>WELFARE</TD>

						<%	tmp = lnsBean.PL_Welfare() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>INSURANCE A</TD>

						<%	tmp = lnsBean.PL_InsuranceA() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>UPKEEP OF FACTORY</TD>

						<%	tmp = lnsBean.PL_UpkeepFactory() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>


						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>UNIFORM FOR WORKERS</TD>

						<%	tmp = lnsBean.PL_Workers_Uniform() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>LAND RENTAL (DIVIDED BY 2)</TD>

						<%	tmp = lnsBean.PL_LandRental() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>TOOL & ACCESSORIES</TD>

						<%	tmp = lnsBean.PL_Tool_Accessories() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>OIL FOR MACHINE</TD>

						<%	tmp = lnsBean.PL_Oil_Machine() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>INDIRECT LABOUR SALARY</TD>

						<%	tmp = lnsBean.PL_IndirectLabourSalary() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>


						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>INDIRECT LABOUR 13TH SALARY</TD>

						<%	tmp = lnsBean.PL_Indirect_13th_LabourSalary() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>INDIRECT LABOUR BONUS</TD>

						<%	tmp = lnsBean.PL_Indirect_LabourBonus() ;
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>&nbsp;</TD>

						<%	
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right >&nbsp;</TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>Goods purchased</TD>

						<%	
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right >-</TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>W.I.P Opening</TD>

						<%	
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right >-</TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>W.I.P Closing</TD>

						<%	
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right >-</TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>Finished Goods Opening</TD>

						<%	
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right >-</TD>			
						<%	} 
							
						%>								
								</TR>


						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>Finished Goods Closing</TD>

						<%	
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right >-</TD>			
						<%	} 
							
						%>								
								</TR>
						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>&nbsp;</TD>

						<%	
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right >&nbsp;</TD>			
						<%	} 
							
						%>								
								</TR>
								
								<TR class="tbheader">
															
									<TD>COST OF GOODS SOLD</TD>

						<%	
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(COGS[i]))%></TD>			
						<%	} 
							
						%>								
								</TR>

								<TR class = "tblightrow">
															
									<TD>&nbsp;</TD>

						<%	
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right >&nbsp;</TD>			
						<%	} 
							
						%>								
								</TR>

								<TR class="tbheader">
															
									<TD>GROSS PROFIT</TD>

						<%	
							for(int i = 0; i < 12; i++){ 			
									NETPROFITBTAX[i] = "" + (Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]));
							
							%>			
							<% 		if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ %>				
										<TD align = right ><%= "(" + formatter.format(Double.parseDouble(NETPROFITBTAX[i])*(-1)) + ")" %> </TD>
							<% 		}else{ %>							
										<TD align = right ><%=  formatter.format(Double.parseDouble(NETPROFITBTAX[i])) %> </TD>
							<% 		} %>
												
						<%	}	%> 
														
								</TR>

								<TR class = "tblightrow">
															
									<TD>&nbsp;</TD>

						<%	
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(100*(Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]))/Double.parseDouble( SALES[i].equals("0") ? "1" : SALES[i]))%> %</TD>			
						<%	} 
							
						%>								
								</TR>

								<TR class="tbheader">
									<TD>SELLING EXPENSES(CHI PHI BAN HANG)</TD>

						<%	tmp = lnsBean.PL_SellingExpenses(); 
							for(int i = 0; i < 12; i++){ 			
									NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(tmp[i]));
							%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} %>								
								</TR>

						<% 	m = 0; 
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>TRANSPORTATION</TD>

						<%	tmp = lnsBean.PL_Transportation();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>
						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>COMMISSION /HADNLING CHARGES FOR EXPORT SALES</TD>

						<%	tmp = lnsBean.PL_Commission();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>PROMOTION EXPENSE</TD>

						<%	tmp = lnsBean.PL_PromotionExpenses();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

								<TR class="tbheader">
									<TD>ADMINISTRATIVE OVERHEAD (CHI PHI QUAN LY)</TD>

						<%	tmp = lnsBean.PL_AdminOverhead(); 
							for(int i = 0; i < 12; i++){ 			
									NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(tmp[i]));
							%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} %>								
								</TR>

						<% 	m = 0; 
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>ALLOWANCES FOR STAFFS</TD>

						<%	tmp = lnsBean.PL_Staff_Allowances();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>
						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>BANK CHARGES</TD>

						<%	tmp = lnsBean.PL_BankCharges();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>BONUS FOR STAFFS</TD>

						<%	tmp = lnsBean.PL_StaffsBonus();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>DEPRECIATION OF BUILDING</TD>

						<%	tmp = lnsBean.PL_Depreciation_Building_Office();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>DEPRECIATION OF OFFICE EQUIPMENT</TD>

						<%	tmp = lnsBean.PL_Depreciation_Equipment_Office();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>DEPRECIATION MOTOR VEHICLE FOR OFFICE</TD>

						<%	//tmp = lnsBean.PL_DepreciationMotorVehicle_Production();
							tmp = lnsBean.PL_Depreciation_Motor_Office();	
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>PHONE+FAX +ELECTRICITY CHARGE FOR OFFICE</TD>

						<%	tmp = lnsBean.PL_Phone_Fax_Electricity_Office();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>PUBLIC RELATION EXPENSES</TD>

						<%	tmp = lnsBean.PL_PublicRelations();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>ENTERTAINMENT</TD>

						<%	tmp = lnsBean.PL_Entertainments();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>OFFICE EXPENSES</TD>

						<%	tmp = lnsBean.PL_OfficeExpenses();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>HANDLING CHARGE</TD>

						<%	tmp = lnsBean.PL_HandlingCharges();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>PRINTING & STATIONERY (OFFICE)</TD>

						<%	tmp = lnsBean.PL_Printing_Stationery_Office();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>TRAINING FEE</TD>

						<%	tmp = lnsBean.PL_TrainingFees();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>TRAVELLING</TD>

						<%	tmp = lnsBean.PL_Trevelling();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>UPKEEP OF MOTOR VEHICLE (OFFICE)</TD>

						<%	tmp = lnsBean.PL_Upkeep_Motor_Vehicle_Office();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>AUDITING FEE</TD>

						<%	tmp = lnsBean.PL_AuditingFees();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>INSURANCE (office)</TD>

						<%	tmp = lnsBean.PL_Insurance_Office();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>UNIFORM FOR STAFFS</TD>

						<%	tmp = lnsBean.PL_StaffUniform();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>LAND RENTAL</TD>

						<%	tmp = lnsBean.PL_LandRental_Office();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>R & D EXPENSES</TD>

						<%	tmp = lnsBean.PL_RD_Expenses();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>MANAGEMENT FEE</TD>

						<%	tmp = lnsBean.PL_ManagementFees();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

								<TR class="tbheader">
									<TD>ADMIN. SALARY(LUONG QUAN LY)</TD>

						<%	tmp = lnsBean.PL_AdminSalary(); 
							for(int i = 0; i < 12; i++){ 			
									NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(tmp[i]));
							%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} %>								
								</TR>

						<% 	m = 0; 
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>STAFFS 'S  SALARY</TD>

						<%	tmp = lnsBean.PL_StaffSalary();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>
						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>OVERTIME FOR STAFFS</TD>

						<%	tmp = lnsBean.PL_StaffOvertime();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>AMORTIZED 13 th SALARY OF STAFFS</TD>

						<%	tmp = lnsBean.PL_Staff_Amortized_13th_Salary();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>SOCIAL INSURANCE+UNION FEE OF STAFFS</TD>

						<%	tmp = lnsBean.PL_Staff_SocialInsurance();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>HEALTH INSURANCE (STAFFS)</TD>

						<%	tmp = lnsBean.PL_Staff_HealthInsurance();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>



								<TR class="tbheader">
									<TD>FINANCIAL ACTIVITY(HOAT DONG TAI CHINH)</TD>

						<%	tmp = lnsBean.PL_Financial_Activity(); 
							for(int i = 0; i < 12; i++){ 			
								NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(tmp[i]));
							%>			
							<% 	if(Double.parseDouble(tmp[i]) < 0){ %>				
									<TD align = right ><%= "(" + formatter.format(Double.parseDouble(tmp[i])*(-1)) + ")" %> </TD>
							<% 	}else{ %>							
									<TD align = right ><%=  formatter.format(Double.parseDouble(tmp[i])) %> </TD>
							<% 	} %>
											
						<%	} %>								
								</TR>

						<% 	m = 0; 
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>INTEREST INCOME (51501)</TD>

						<%	tmp = lnsBean.PL_Interest_Income();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= "(" + formatter.format(Double.parseDouble(tmp[i])) + ")" %></TD>			
						<%	} 
							
						%>								
								</TR>
						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>GAIN ON EXCHANGE RESERVE (51502)</TD>

						<%	tmp = lnsBean.PL_Gain_Exchange_Reserve();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= "(" + formatter.format(Double.parseDouble(tmp[i])) + ")" %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>LOAN INTEREST (63501)</TD>

						<%	tmp = lnsBean.PL_LoanInterest();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>LOSS ON EXCHANGE RESERVE (63502)</TD>

						<%	tmp = lnsBean.PL_Loss_Exchange_Reserve();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

								<TR class="tbheader">
									<TD>OTHER ACTIVITY (HOAT DONG KHAC)</TD>

						<%	tmp = lnsBean.PL_OtherActivity(); 
							for(int i = 0; i < 12; i++){ 			
								NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(tmp[i]));
							%>			
							<% 	if(Double.parseDouble(tmp[i]) < 0){ %>				
									<TD align = right ><%= "(" + formatter.format(Double.parseDouble(tmp[i])*(-1)) + ")" %> </TD>
							<% 	}else{ %>							
									<TD align = right ><%=  formatter.format(Double.parseDouble(tmp[i])) %> </TD>
							<% 	} %>
											
						<%	} %>								
								</TR>

						<% 	m = 0; 
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>OTHER INCOME(711)</TD>

						<%	tmp = lnsBean.PL_Other_Income();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%= "(" + formatter.format(Double.parseDouble(tmp[i])) + ")" %></TD>			
						<%	} 
							
						%>								
								</TR>
						<%	m++;
							if (m % 2 != 0) {
						%>
								<TR class = "tblightrow">
								
						<% 	}else{ %>
									
								<TR class = "tbdarkrow">
						<%	}      %>
							
								<TD>OTHER EXPENSE (811)</TD>

						<%	tmp = lnsBean.PL_OtherExpenses();
							for(int i = 0; i < 12; i++){ 			%>			
												
									<TD align = right ><%=  formatter.format(Double.parseDouble(tmp[i])) %></TD>			
						<%	} 
							
						%>								
								</TR>

								<TR class="tbheader">
									<TD>NET PROFIT BEFORE TAX</TD>

						<%	 
							for(int i = 0; i < 12; i++){ 			
							
							%>			
							<% 	if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ %>				
									<TD align = right ><%= "(" + formatter.format(Double.parseDouble(NETPROFITBTAX[i])*(-1)) + ")" %> </TD>
							<% 	}else{ %>							
									<TD align = right ><%=  formatter.format(Double.parseDouble(NETPROFITBTAX[i])) %> </TD>
							<% 	} %>
											
						<%	} %>								
								</TR>

<!-- 								</TR> -->

								<TR class="tblightrow">
									<TD>&nbsp;</TD>

						<%	 
							for(int i = 0; i < 12; i++){ 			
							
							%>			
							<% 	if(Double.parseDouble(SALES[i]) > 0){ 
									if(Double.parseDouble(NETPROFITBTAX[i]) >= 0){
																		%>				
									<TD align = right ><%=  formatter.format(100*Double.parseDouble(NETPROFITBTAX[i])/Double.parseDouble(SALES[i].equals("0") ? "1" : SALES[i] ))  %>% </TD>									
									
							<%		}else{ %>
									
									<TD align = right ><%= "(" + formatter.format(100*Double.parseDouble(NETPROFITBTAX[i])/Double.parseDouble(SALES[i].equals("0") ? "1" : SALES[i] )) + ")" %>%</TD>
									
							<%		} %>
							
							<% 	}else{ %>							
									<TD align = right >&nbsp; </TD>
							<% 	} %>
											
						<%	} %>								
								</TR>

								<TR class="tbdarkrow">
									<TD>CURRENT INCOME TAX EXPENSE</TD>

						<%	 
							for(int i = 0; i < 12; i++){ 			
							
							%>			
							<% 	
								if(Double.parseDouble(NETPROFITBTAX[i]) >= 0){
																		%>				
									<TD align = right ><%=  formatter.format(7.5*Double.parseDouble(NETPROFITBTAX[i])/100)  %> </TD>									
									
							<%	}else{ %>
									
									<TD align = right ><%= "0" %> </TD>
									
							<%	} %>
							
											
						<%	} %>								
								</TR>

								<TR class="tbheader">
									<TD>NET PROFIT AFTER TAX</TD>

						<%	 
							for(int i = 0; i < 12; i++){ 			
							
							%>			
							<% 	if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ %>				
									<TD align = right ><%= "(" + formatter.format(Double.parseDouble(NETPROFITBTAX[i])*(-1) ) + ")" %> </TD>
							<% 	}else{ %>							
									<TD align = right ><%=  formatter.format((100-7.5)*Double.parseDouble(NETPROFITBTAX[i])/100) %> </TD>
							<% 	} %>
											
						<%	} %>								
								</TR>
		

	<% 
	try{
		 
		session.setAttribute("lnsBean", null) ;  
	}catch(Exception er){
		
	}
	finally{
		lnsBean.closeDB();
	}
	%>
	
