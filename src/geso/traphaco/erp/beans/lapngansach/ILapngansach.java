package geso.traphaco.erp.beans.lapngansach;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface ILapngansach {
	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getId();
	
	public void setId(String Id);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);	
	
	public String getNgay();
	
	public void setNgay(String ngay);
	
	public String getCtyId();
	
	public void setCtyId(String ctyId);

	public String getCty();
	
	public void setCty(String cty);	
	
	public void setDoanhthu(String doanhthu);

	public String getDoanhthu();
	
	public void setGiavon(String giavon);

	public String getGiavon();	
	
	public String getView();
	
	public void setView(String view);
	
	public String getBpId();
	
	public void setBpId(String bpId);

	public String getLoai();
	
	public void setLoai(String loai);
	
	public String getLnsId();
	
	public void setLnsId(String lnsId);
	
	public String getNam();
	
	public void setNam(String nam);
	
	public String getNamtruoc();
	
	public void setNamtruoc(String namtruoc);
	
	public String getHieuluc();

	public void setHieuluc(String hieuluc);
	
	public String getMsg();
	
	public void setMsg(String msg);
		
	public float getPTGVdutoan();

	public void setPTGVdutoan(float ptgvdutoan);
	
	public void setCpIds(String[] cpIds);

	public String[] getCpIds();
	
	public void setTsIds(String[] tsIds);

	public String[] getTsIds();	
	
	public void setCdIds(String[] cdIds);

	public String[] getCdIds();
	
	public void setDtIds(String[] dtIds);

	public String[] getDtIds();
	
	public ResultSet getBplist();
	
	public void setBplist(ResultSet bplist);
	
	public ResultSet getNtslist();
	
	public void setNtslist(ResultSet ntslist);
	
	public String getNtsId();
	
	public void setNtsId(String ntsId);
	
	public ResultSet getNamlist();
	
	public void setNamlist(ResultSet namlist);
	
	public String getNdtId();
	
	public void setNdtId(String ndtId);
	
	public ResultSet getNdtlist();
	
	public void setNdtlist(ResultSet ndtlist);
	
	public ResultSet getDtlist();
	
	public void setDtlist(ResultSet dtlist);
	
	public String getNcpId();
	
	public void setNcpId(String ncpId);
	
	public ResultSet getNcplist();
	
	public void setNcplist(ResultSet ncplist);
	
	public ResultSet getCplist();
	
	public void setCplist(ResultSet cplist);

	public ResultSet getTslist();
	
	public void setTslist(ResultSet tslist);

	public ResultSet getCdlist();

	public void setCdlist(ResultSet cdlist);
	
	public void init();
	
	public void init_doanhthu();
	
	public void init_taisan();
	
	public void init_phanbovakhauhaotaisan();
	
	public void save_CP(HttpServletRequest request)throws ServletException, IOException;
	public void save_DT(HttpServletRequest request)throws ServletException, IOException;
	
	public void save_taisan(HttpServletRequest request)throws ServletException, IOException;
	
	public boolean save_khvapbtaisan(HttpServletRequest request)throws ServletException, IOException;
	
	public ResultSet createCongdungList();
	
	public ResultSet getChonnhomchiphi();
	
	public String getChon();
	
	public void setChon(String chon);
	
	public String getDTdutoannamtruoc();
	
	public String getDTdutoan();
	
	public String getGVdutoannamtruoc();
	
	public String getGVdutoan();	
	
	public ResultSet getHienthinhomchiphichon();
	
	public ResultSet getHienthinhomchiphikhac();
	
	public ResultSet getKhauhaotaisandukien();
	
	public boolean save_duyetngansach();
	
	public boolean chot_duyetngansach();
	
	public String getDiengiai();
	
	public void setDiengiai(String diengiai);
	
	public ResultSet getDutoantaisan();
	
	public String getDttsId();
	
	public void setDttsId(String dttsId);
	
	public String getDvkdId(); 
	
	public void setDvkdId(String dvkdId); 
	
	public ResultSet getDvkd(); 
	
	public void setDvkd(ResultSet dvkdRs); 
	
	public ResultSet getNsList(); 
	
	public void setNsList(ResultSet nslist);
	
	public String getNsId(); 
	
	public void setNsId(String LNSId); 
		
	public void closeDB();

	public ResultSet Chonphanbo(String tsId, String ntsId);
	
	public void init_duyetngansach();
	
	public String[] PL_SalesVolumn();
	
	public String[] PL_SalesVolumn_LA();
	
	public String[] PL_SalesVolumn_PA();
	
	public String[][] PL_SalesVolumn_LA_Details();
	
	public String[][] PL_SalesVolumn_PA_Details();
	
	public String[] PL_SalesAmount();
	
	public String[] PL_SalesAmount_Actual();
	
	public String[] PL_SalesAmount_LA();
	
	public String[] PL_SalesAmount_PA();
	
	public String[][] PL_SalesAmount_LA_Details();
	
	public String[][] PL_SalesAmount_PA_Details();	
	
	public String[] PL_MaterialConsume();
	
	public String[] PL_MaterialConsume_LA();
	
	public String[] PL_MaterialConsume_PA();
	
	public String[] PL_MaterialConsume_Paper();
	
	public String[] PL_MaterialConsume_Paper_LA();
	
	public String[] PL_MaterialConsume_Paper_PA();
	
	public String[] PL_MaterialConsume_Foil();
	
	public String[] PL_MaterialConsume_Foil_LA();
	
	public String[] PL_MaterialConsume_Foil_PA();
	
	public String[] PL_MaterialConsume_Glue();
	
	public String[] PL_MaterialConsume_Glue_LA();
	
	public String[] PL_MaterialConsume_Glue_PA();
	
	public String[] PL_MaterialConsume_Lacquer();
	
	public String[] PL_MaterialConsume_Lacquer_LA();
	
	public String[] PL_MaterialConsume_Lacquer_PA();
	
	public String[] PL_MaterialConsume_Submaterial();
	
	public String[] PL_MaterialConsume_Submaterial_LA();

	public String[] PL_MaterialConsume_Submaterial_PA();
	
	public String[] PL_DirectLabour();
	
	public String[] PL_DirectLabour_LA();
	
	public String[] PL_DirectLabour_PA();
	
	public String[] PL_WorkerSalary();
	
	public String[] PL_WorkerSalary_LA();
	
	public String[] PL_WorkerSalary_PA();
	
	public String[] PL_OvertimeWorkers();
	
	public String[] PL_OvertimeWorkers_LA();
	
	public String[] PL_OvertimeWorkers_PA();
	
	public String[] PL_AmortizedWorkers();
	
	public String[] PL_AmortizedWorkers_LA();
	
	public String[] PL_AmortizedWorkers_PA();

	public String[] PL_SocialInsurance();
	
	public String[] PL_SocialInsurance_LA();
	
	public String[] PL_SocialInsurance_PA();
	
	public String[] PL_HealthInsurance();
	
	public String[] PL_HealthInsurance_LA();
	
	public String[] PL_HealthInsurance_PA();
	
	public String[] PL_ProductionOverhead();
	
	public String[] PL_ProductionOverhead_LA();
	
	public String[] PL_ProductionOverhead_PA();
	
	public String[] PL_WorkerAllowances();
	
	public String[] PL_WorkerAllowances_LA();
	
	public String[] PL_WorkerAllowances_PA();
	
	public String[] PL_WorkerBonus();
	
	public String[] PL_WorkerBonus_LA();
	
	public String[] PL_WorkerBonus_PA();
	
	public String[] PL_DepreciationBuilding_Production();
	
	public String[] PL_DepreciationBuilding_Production_LA();
	
	public String[] PL_DepreciationBuilding_Production_PA();
	
	public String[] PL_DepreciationMachine();
	
	public String[] PL_DepreciationMachine_LA();
	
	public String[] PL_DepreciationMachine_PA();
	
	public String[] PL_DepreciationMotorVehicle_Production();
	
	public String[] PL_DepreciationMotorVehicle_Production_LA();
	
	public String[] PL_DepreciationMotorVehicle_Production_PA();
	
	public String[] PL_RentalCharge_Machinery();
	
	public String[] PL_RentalCharge_Machinery_LA();
	
	public String[] PL_RentalCharge_Machinery_PA();
	
	public String[] PL_ElectricityWater();
	
	public String[] PL_ElectricityWater_LA();
	
	public String[] PL_ElectricityWater_PA();
	
	public String[] PL_FactoryExpenses();
	
	public String[] PL_FactoryExpenses_LA();
	
	public String[] PL_FactoryExpenses_PA();
	
	public String[] PL_Printing_Stationery();
	
	public String[] PL_Printing_Stationery_LA();
	
	public String[] PL_Printing_Stationery_PA();
	
	public String[] PL_Workers_Meals();
	
	public String[] PL_Workers_Meals_LA();
	
	public String[] PL_Workers_Meals_PA();
	
	public String[] PL_Upkeep_Motorvehicle();
	
	public String[] PL_Upkeep_Motorvehicle_LA();
	
	public String[] PL_Upkeep_Motorvehicle_PA();
	
	public String[] PL_Upkeep_Machinery();
	
	public String[] PL_Upkeep_Machinery_LA();
	
	public String[] PL_Upkeep_Machinery_PA();
	
	public String[] PL_TrainingCost();
	
	public String[] PL_TrainingCost_LA();
	
	public String[] PL_TrainingCost_PA();
	
	public String[] PL_Welfare();
	
	public String[] PL_Welfare_LA();
	
	public String[] PL_Welfare_PA();
	
	public String[] PL_InsuranceA();
	
	public String[] PL_InsuranceA_LA();
	
	public String[] PL_InsuranceA_PA();
	
	public String[] PL_UpkeepFactory();
	
	public String[] PL_UpkeepFactory_LA();
	
	public String[] PL_UpkeepFactory_PA();
	
	public String[] PL_Workers_Uniform();
	
	public String[] PL_Workers_Uniform_LA();
	
	public String[] PL_Workers_Uniform_PA();
	
	public String[] PL_LandRental();
	
	public String[] PL_LandRental_LA();
	
	public String[] PL_LandRental_PA();
	
	public String[] PL_Tool_Accessories();
	
	public String[] PL_Tool_Accessories_LA();

	public String[] PL_Tool_Accessories_PA();

	public String[] PL_Oil_Machine();
	
	public String[] PL_Oil_Machine_LA();

	public String[] PL_Oil_Machine_PA();

	public String[] PL_IndirectLabourSalary();
	
	public String[] PL_IndirectLabourSalary_LA();

	public String[] PL_IndirectLabourSalary_PA();

	public String[] PL_Indirect_13th_LabourSalary();
	
	public String[] PL_Indirect_13th_LabourSalary_LA();

	public String[] PL_Indirect_13th_LabourSalary_PA();

	public String[] PL_Indirect_LabourBonus();

	public String[] PL_Indirect_LabourBonus_LA();

	public String[] PL_Indirect_LabourBonus_PA();

	public String[] PL_SellingExpenses();
	
	public String[] PL_SellingExpenses_LA();
	
	public String[] PL_SellingExpenses_PA();
	
	public String[] PL_Transportation();
	
	public String[] PL_Transportation_LA();
	
	public String[] PL_Transportation_PA();
	
	
	public String[] PL_Commission();
	
	public String[] PL_Commission_LA();
	
	public String[] PL_Commission_PA();
	
	public String[] PL_PromotionExpenses();
	
	public String[] PL_PromotionExpenses_LA();
	
	public String[] PL_PromotionExpenses_PA();
	
	public String[] PL_AdminOverhead();
	
	public String[] PL_AdminOverhead_LA();
	
	public String[] PL_AdminOverhead_PA();
	
	public String[] PL_Staff_Allowances();
	
	public String[] PL_Staff_Allowances_LA();
	
	public String[] PL_Staff_Allowances_PA();
	
	public String[] PL_BankCharges();
	
	public String[] PL_BankCharges_LA();
	
	public String[] PL_BankCharges_PA();
	
	public String[] PL_StaffsBonus();
	
	public String[] PL_StaffsBonus_LA();
	
	public String[] PL_StaffsBonus_PA();
	
	public String[] PL_Depreciation_Building_Office();
	
	public String[] PL_Depreciation_Building_Office_LA();
	
	public String[] PL_Depreciation_Building_Office_PA();	
	
	public String[] PL_Depreciation_Equipment_Office();
	
	public String[] PL_Depreciation_Equipment_Office_LA();

	public String[] PL_Depreciation_Equipment_Office_PA();

	public String[] PL_Depreciation_Motor_Office();
	
	public String[] PL_Depreciation_Motor_Office_LA();

	public String[] PL_Depreciation_Motor_Office_PA();

	public String[] PL_Phone_Fax_Electricity_Office();
	
	public String[] PL_Phone_Fax_Electricity_Office_LA();

	public String[] PL_Phone_Fax_Electricity_Office_PA();
	
	public String[] PL_PublicRelations();
	
	public String[] PL_PublicRelations_LA();

	public String[] PL_PublicRelations_PA();
	
	public String[] PL_Entertainments();
	
	public String[] PL_Entertainments_LA();

	public String[] PL_Entertainments_PA();
	
	public String[] PL_OfficeExpenses();
	
	public String[] PL_OfficeExpenses_LA();

	public String[] PL_OfficeExpenses_PA();
	
	public String[] PL_HandlingCharges();
	
	public String[] PL_HandlingCharges_LA();

	public String[] PL_HandlingCharges_PA();
	
	public String[] PL_Printing_Stationery_Office();
	
	public String[] PL_Printing_Stationery_Office_LA();

	public String[] PL_Printing_Stationery_Office_PA();
	
	public String[] PL_TrainingFees();
	
	public String[] PL_TrainingFees_LA();

	public String[] PL_TrainingFees_PA();

	public String[] PL_Trevelling();
	
	public String[] PL_Trevelling_LA();

	public String[] PL_Trevelling_PA();

	public String[] PL_Upkeep_Motor_Vehicle_Office();
	
	public String[] PL_Upkeep_Motor_Vehicle_Office_LA();

	public String[] PL_Upkeep_Motor_Vehicle_Office_PA();
	
	public String[] PL_AuditingFees();
	
	public String[] PL_AuditingFees_LA();

	public String[] PL_AuditingFees_PA();

	public String[] PL_Insurance_Office();
	
	public String[] PL_Insurance_Office_LA();

	public String[] PL_Insurance_Office_PA();

	public String[] PL_StaffUniform();
	
	public String[] PL_StaffUniform_LA();

	public String[] PL_StaffUniform_PA();

	public String[] PL_LandRental_Office();
	
	public String[] PL_LandRental_Office_LA();

	public String[] PL_LandRental_Office_PA();

	public String[] PL_RD_Expenses();
	
	public String[] PL_RD_Expenses_LA();

	public String[] PL_RD_Expenses_PA();

	public String[] PL_ManagementFees();
	
	public String[] PL_ManagementFees_LA();

	public String[] PL_ManagementFees_PA();
	
	public String[] PL_AdminSalary();
	
	public String[] PL_AdminSalary_PA();

	public String[] PL_AdminSalary_LA();

	public String[] PL_StaffSalary();
	
	public String[] PL_StaffSalary_LA();

	public String[] PL_StaffSalary_PA();

	public String[] PL_StaffOvertime();
	
	public String[] PL_StaffOvertime_LA();

	public String[] PL_StaffOvertime_PA();

	public String[] PL_Staff_Amortized_13th_Salary();
	
	public String[] PL_Staff_Amortized_13th_Salary_LA();

	public String[] PL_Staff_Amortized_13th_Salary_PA();

	public String[] PL_Staff_SocialInsurance();
	
	public String[] PL_Staff_SocialInsurance_LA();

	public String[] PL_Staff_SocialInsurance_PA();

	public String[] PL_Staff_HealthInsurance();
	
	public String[] PL_Staff_HealthInsurance_LA();

	public String[] PL_Staff_HealthInsurance_PA();

	public String[] PL_Interest_Income();
	
	public String[] PL_Interest_Income_LA();

	public String[] PL_Interest_Income_PA();

	public String[] PL_Gain_Exchange_Reserve();
	
	public String[] PL_Gain_Exchange_Reserve_LA();

	public String[] PL_Gain_Exchange_Reserve_PA();

	public String[] PL_Other_Income();
	
	public String[] PL_Other_Income_LA();
	
	public String[] PL_Other_Income_PA();

	public String[] PL_OtherExpenses();
	
	public String[] PL_OtherExpenses_LA();
	
	public String[] PL_OtherExpenses_PA();
	
	public String[] PL_LoanInterest();
	
	public String[] PL_LoanInterest_LA();
	
	public String[] PL_LoanInterest_PA();
	
	public String[] PL_Loss_Exchange_Reserve();
	
	public String[] PL_Loss_Exchange_Reserve_LA();
	
	public String[] PL_Loss_Exchange_Reserve_PA();
	
	public String[] PL_Financial_Activity();
	
	public String[] PL_Financial_Activity_LA();
	
	public String[] PL_Financial_Activity_PA();
	
	public String[] PL_OtherActivity();
	
	public String[] PL_OtherActivity_LA();
	
	public String[] PL_OtherActivity_PA();
	
	public double getTongDoanhThu();
	
	public void setDubaoThang(String[] amount);

	public String[] getDubaoThang();
	
}
