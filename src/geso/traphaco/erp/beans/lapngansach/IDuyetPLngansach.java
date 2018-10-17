package geso.traphaco.erp.beans.lapngansach;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Font;
import com.aspose.cells.Style;

public interface IDuyetPLngansach {
	public String getUserId();
	
	public void setUserId(String userId);
	
	public int getRow_PA();
	public int getRow_SALES_VOLUME_PA();
	public int getRow_SELLING_PRICE_NET_PA();
	public int getRow_TOTAL_SALES_AMOUNT_PA();

	public int getRow_MATERIALS_CONSUMED_PA();
	public int getRow_DIRECT_LABOUR_PA();
	public int getRow_PRODUCTION_OVERHEAD_PA();
	
	public int getRow_SALES_VOLUME();	
	public int getRow_TOTAL_AMOUNT();
	
	public void setValue_Part_1_Budget(Cells cells, Cell cell, int k, Style style);
	
	public int getRow_SALES_AMOUNT();
	public int getRow_MATERIALS_CONSUMED_LA();
	
	public int getRow_DIRECT_LABOUR_LA();
	
	public int getRow_PRODUCTION_OVERHEAD_LA();

	
	public int getRow_LA();
	
	public String getId();
	
	public void setId(String Id);
	
	public String getNgay();
	
	public void setNgay(String ngay);
	
	public void setMonth(String month);

	public String getMonth();
	
	public void setYear(String year);

	public String getYear();
	
	public String getCtyId();
	
	public void setCtyId(String ctyId);

	public String getCty();
	
	public void setCty(String cty);	
	
	public void init();
		
	public String getNam();
	
	public void setNam(String nam);

	public String getMsg();
	
	public void setMsg(String msg);
		
	public ResultSet getNamlist();
	
	public void setNamlist(ResultSet namlist);
			
	public void closeDB();
	
	public String[] getDanhSachMaketoan_LA();
	public String[] getDanhSachMaketoan_PA();
//	public String[] PL_SalesVolumn_Budget();
	public String[] PL_SalesVolumn_LA_Budget();
	public String[] PL_SalesVolumn_PA_Budget();
	
	public String[] PL_SalesVolumn_LA_Budget_Detail(String MA);
	public String[] PL_SalesVolumn_PA_Budget_Detail(String MA);
	
	
	public String[] PL_SalesAmount_Total_Budget();
	
	public String[] PL_SalesAmount_LA_Total_Budget();
		
	public String[] PL_SalesAmount_LA_Return_Budget();
	
	
	public String[] PL_SalesAmount_PA_Total_Budget();
	
	
	public String[] PL_SalesAmount_PA_Return_Budget();
	
	
	public String[] PL_SalesAmount_Others_Budget();
	
	public String[] PL_SalesAmount_Others_Return_Budget();
	

	public String[] PL_MaterialConsume_Budget();
	
	
	public String[] PL_DirectLabour_Budget();
	
	public String[] PL_DirectLabour_LA_Budget();
	
	public String[] PL_DirectLabour_PA_Budget();
	
	
	public String[] PL_WorkerSalary_Budget();
	
	public String[] PL_WorkerSalary_LA_Budget();
	
	public String[] PL_WorkerSalary_PA_Budget();
	
	
	public String[] PL_OvertimeWorkers_Budget();
	
	public String[] PL_OvertimeWorkers_LA_Budget();
	
	public String[] PL_OvertimeWorkers_PA_Budget();
	
	
	public String[] PL_AmortizedWorkers_Budget();
	
	public String[] PL_AmortizedWorkers_LA_Budget();
	
	public String[] PL_AmortizedWorkers_PA_Budget();

	
	public String[] PL_SocialInsurance_Budget();
	
	public String[] PL_SocialInsurance_LA_Budget();
	
	public String[] PL_SocialInsurance_PA_Budget();
	
	
	public String[] PL_HealthInsurance_Budget();
	
	public String[] PL_HealthInsurance_LA_Budget();
	
	public String[] PL_HealthInsurance_PA_Budget();
	
	
	public String[] PL_ProductionOverhead_Budget();
	
	public String[] PL_ProductionOverhead_LA_Budget();
	
	public String[] PL_ProductionOverhead_PA_Budget();
	
	
	public String[] PL_WorkerAllowances_Budget();
	
	public String[] PL_DBCL_Budget();
	
	public String[] PL_KTCL_Budget();
	
	public String[] PL_KKH_Budget();
	
	public String[] PL_KXNK_Budget();

	public String[] PL_QLSX_Budget();
	
	public String[] PL_TVTN_Budget();
	
	public String[] PL_TMNM_Budget();
	
	public String[] PL_TY_Budget();
	
	public String[] PL_DG_Budget();
	
	public String[] PL_SXC_Budget();
	
	public String[] PL_WorkerAllowances_PA_Budget();
	
	
	public String[] PL_WorkerBonus_Budget();
	
	
	
	public String[] PL_WorkerBonus_PA_Budget();
	
	
	public String[] PL_DepreciationBuilding_Production_Budget();
	

	
	public String[] PL_DepreciationBuilding_Production_PA_Budget();
	
	
	public String[] PL_DepreciationMachine_Budget();
	
	
	public String[] PL_DepreciationMachine_PA_Budget();
	
	
	public String[] PL_DepreciationMotorVehicle_Production_Budget();
	

	
	public String[] PL_DepreciationMotorVehicle_Production_PA_Budget();

	
	public String[] PL_RentalCharge_Machinery_Budget();
	

	
	public String[] PL_RentalCharge_Machinery_PA_Budget();
	
	
	public String[] PL_ElectricityWater_Budget();
	
	
	
	public String[] PL_ElectricityWater_PA_Budget();
	
	
	public String[] PL_FactoryExpenses_Budget();
	
	
	public String[] PL_FactoryExpenses_PA_Budget();

	
	public String[] PL_Printing_Stationery_Budget();
	
	
	
	public String[] PL_Printing_Stationery_PA_Budget();
	
	
	public String[] PL_Workers_Meals_Budget();
	
	public String[] PL_Workers_Meals_PA_Budget();
	
	public String[] PL_Upkeep_Motorvehicle_Budget();
	
	public String[] PL_Upkeep_Motorvehicle_LA_Budget();
	
	public String[] PL_Upkeep_Motorvehicle_PA_Budget();
	
	
	public String[] PL_Upkeep_Machinery_Budget();
	
	public String[] PL_Upkeep_Machinery_LA_Budget();
	
	public String[] PL_Upkeep_Machinery_PA_Budget();

	
	public String[] PL_TrainingCost_Budget();
	
	public String[] PL_TrainingCost_LA_Budget();
	
	public String[] PL_TrainingCost_PA_Budget();

	
	public String[] PL_Welfare_Budget();
	
	public String[] PL_Welfare_LA_Budget();
	
	public String[] PL_Welfare_PA_Budget();
	
	
	public String[] PL_InsuranceA_Budget();
	
	public String[] PL_InsuranceA_LA_Budget();
	
	public String[] PL_InsuranceA_PA_Budget();
	
	
	public String[] PL_UpkeepFactory_Budget();
	
	public String[] PL_UpkeepFactory_LA_Budget();
	
	public String[] PL_UpkeepFactory_PA_Budget();

	
	public String[] PL_Workers_Uniform_Budget();
	
	public String[] PL_Workers_Uniform_LA_Budget();
	
	public String[] PL_Workers_Uniform_PA_Budget();
	
	
	public String[] PL_LandRental_Budget();
	
	public String[] PL_LandRental_LA_Budget();
	
	public String[] PL_LandRental_PA_Budget();

	
	public String[] PL_Machinery_Maintenance_Budget();
	
	public String[] PL_Machinery_Maintenance_LA_Budget();
	
	public String[] PL_Machinery_Maintenance_PA_Budget();
	
	
	//public String[] PL_Tool_Accessories_LA_Budget();

	//public String[] PL_Tool_Accessories_PA_Budget();
	
	public String[] PL_Machinery_Overhaul_Budget();
	public String[] PL_Machinery_Overhaul_LA_Budget();
	public String[] PL_Machinery_Overhaul_PA_Budget();
	
	public String[] PL_Production_Equipments_Budget();
	public String[] PL_Production_Equipments_LA_Budget();
	public String[] PL_Production_Equipments_PA_Budget();
	
	public String[] PL_Oil_Machine_Budget();
	
	public String[] PL_Oil_Machine_LA_Budget();

	public String[] PL_Oil_Machine_PA_Budget();

	
	public String[] PL_IndirectLabourSalary_Budget();
	
	public String[] PL_IndirectLabourSalary_LA_Budget();

	public String[] PL_IndirectLabourSalary_PA_Budget();

	
	public String[] PL_Indirect_13th_LabourSalary_Budget();
	
	public String[] PL_Indirect_13th_LabourSalary_LA_Budget();

	public String[] PL_Indirect_13th_LabourSalary_PA_Budget();

	
	public String[] PL_Indirect_LabourBonus_Budget();
	
	public String[] PL_Indirect_LabourBonus_LA_Budget();

	public String[] PL_Indirect_LabourBonus_PA_Budget();

	
	public String[] PL_Outsourced_Printing_Expense_Budget();
	
	public String[] PL_SellingExpenses_Budget();
	
	public String[] PL_SellingExpenses_LA_Budget();
	
	public String[] PL_SellingExpenses_PA_Budget();
	
	
	public String[] PL_Transportation_Budget();
	
	public String[] PL_VPMB_Budget();
	
	public String[] PL_Transportation_PA_Budget();
	
	
	public String[] PL_Commission_Budget();
	
	public String[] PL_CTMB_Budget();
	
	public String[] PL_Commission_PA_Budget();
	
	
	public String[] PL_PromotionExpenses_Budget();
	
	public String[] PL_HN_Budget();
	
	public String[] PL_ND_Budget();
	
	public String[] PL_HP_Budget();
	
	public String[] PL_PromotionExpenses_PA_Budget();

	
	public String[] PL_AdminOverhead_Budget();
	
	public String[] PL_AdminOverhead_LA_Budget();
	
	public String[] PL_AdminOverhead_PA_Budget();
	
	
	public String[] PL_Staff_Allowances_Budget();
	
	public String[] PL_BTGD_Budget();
	
	public String[] PL_Staff_Allowances_PA_Budget();
	
	
	public String[] PL_BankCharges_Budget();
	
	public String[] PL_TCCB_Budget();
	
	public String[] PL_BankCharges_PA_Budget();
	
	
	public String[] PL_StaffsBonus_Budget();
	
	public String[] PL_HCQT_Budget();
	
	public String[] PL_StaffsBonus_PA_Budget();
	
	
	public String[] PL_Depreciation_Building_Office_Budget();
	
	public String[] PL_TCKT_Budget();
	
	public String[] PL_Depreciation_Building_Office_PA_Budget();	
	
	
	public String[] PL_Depreciation_Equipment_Office_Budget();
	
	public String[] PL_QTRR_Budget();

	public String[] PL_Depreciation_Equipment_Office_PA_Budget();

	
	public String[] PL_Depreciation_Motor_Office_Budget();
	
	public String[] PL_MKT_Budget();

	public String[] PL_Depreciation_Motor_Office_PA_Budget();
	
	
	public String[] PL_Phone_Fax_Electricity_Office_Budget();

	public String[] PL_NCPT_Budget();

	public String[] PL_Phone_Fax_Electricity_Office_PA_Budget();
	

	public String[] PL_PublicRelations_Budget();
	
	public String[] PL_XNK_Budget();

	public String[] PL_PublicRelations_PA_Budget();
	
	
	public String[] PL_Entertainments_Budget();
	public String[] PL_KH_Budget();

	public String[] PL_Entertainments_PA_Budget();
	
	
	public String[] PL_OfficeExpenses_Budget();
	public String[] PL_DA_Budget();

	public String[] PL_OfficeExpenses_PA_Budget();

	
	public String[] PL_HandlingCharges_Budget();
	
	public String[] PL_PBC_Budget();

	public String[] PL_HandlingCharges_PA_Budget();
	
	
	public String[] PL_Printing_Stationery_Office_Budget();
	
	public String[] PL_Printing_Stationery_Office_LA_Budget();

	public String[] PL_Printing_Stationery_Office_PA_Budget();
	
	
	public String[] PL_TrainingFees_Budget();
	
	public String[] PL_TrainingFees_LA_Budget();

	public String[] PL_TrainingFees_PA_Budget();

	
	public String[] PL_Trevelling_Budget();
	
	public String[] PL_Trevelling_LA_Budget();

	public String[] PL_Trevelling_PA_Budget();

	
	public String[] PL_Upkeep_Motor_Vehicle_Office_Budget();
	
	public String[] PL_Upkeep_Motor_Vehicle_Office_LA_Budget();

	public String[] PL_Upkeep_Motor_Vehicle_Office_PA_Budget();
	
	
	public String[] PL_AuditingFees_Budget();
	
	public String[] PL_AuditingFees_LA_Budget();

	public String[] PL_AuditingFees_PA_Budget();

	
	public String[] PL_Insurance_Office_Budget();
	
	public String[] PL_Insurance_Office_LA_Budget();

	public String[] PL_Insurance_Office_PA_Budget();

	
	public String[] PL_ProvisionExpenses_Budget();
	
	public String[] PL_ProvisionExpenses_LA_Budget();
	public String[] PL_ProvisionExpenses_PA_Budget();
	
	
//	public String[] PL_StaffUniform_LA_Budget();

//	public String[] PL_StaffUniform_PA_Budget();

	public String[] PL_LandRental_Office_Budget();
	
	
	public String[] PL_LandRental_Office_LA_Budget();

	public String[] PL_LandRental_Office_PA_Budget();
	
	
	public String[] PL_RD_Expenses_Budget();
	
	public String[] PL_RD_Expenses_LA_Budget();

	public String[] PL_RD_Expenses_PA_Budget();
	
	
	public String[] PL_ManagementFees_Budget();
	
	public String[] PL_ManagementFees_LA_Budget();

	public String[] PL_ManagementFees_PA_Budget();
	
	
	public String[] PL_AdminSalary_Budget();
	
//	public String[] PL_AdminSalary_PA_Budget();

//	public String[] PL_AdminSalary_LA_Budget();

	
	public String[] PL_Staff_Salary_Budget();
	
	public String[] PL_Staff_Salary_LA_Budget();

	public String[] PL_Staff_Salary_PA_Budget();
	
	
	public String[] PL_StaffOvertime_Budget();
	
	public String[] PL_StaffOvertime_LA_Budget();

	public String[] PL_StaffOvertime_PA_Budget();

	
	public String[] PL_Staff_Amortized_13th_Salary_Budget();
	
	public String[] PL_Staff_Amortized_13th_Salary_LA_Budget();

	public String[] PL_Staff_Amortized_13th_Salary_PA_Budget();
	
	
	public String[] PL_Staff_SocialInsurance_Budget();
	
	public String[] PL_Staff_SocialInsurance_LA_Budget();

	public String[] PL_Staff_SocialInsurance_PA_Budget();

	
	public String[] PL_Staff_HealthInsurance_Budget();
	
	public String[] PL_Staff_HealthInsurance_LA_Budget();

	public String[] PL_Staff_HealthInsurance_PA_Budget();
	
	
	public String[] PL_Financial_Activity_Budget();
	
//	public String[] PL_Financial_Activity_LA_Budget();
	
//	public String[] PL_Financial_Activity_PA_Budget();
	
	
	public String[] PL_Interest_Income_Budget();
	
	public String[] PL_Interest_Income_LA_Budget();

	public String[] PL_Interest_Income_PA_Budget();

	
	public String[] PL_Gain_Exchange_Reserve_Budget();
	
	public String[] PL_Gain_Exchange_Reserve_LA_Budget();

	public String[] PL_Gain_Exchange_Reserve_PA_Budget();

	
	public String[] PL_LoanInterest_Budget();
	public String[] PL_LoanInterest_LA_Budget();
	public String[] PL_LoanInterest_PA_Budget();
	
	public String[] PL_Loss_Exchange_Reserve_Budget();
	public String[] PL_Loss_Exchange_Reserve_LA_Budget();
	
	public String[] PL_Loss_Exchange_Reserve_PA_Budget();
	
	
	public String[] PL_OtherActivity_Budget();
	
	public String[] PL_OtherActivity_LA_Budget();
	
	public String[] PL_OtherActivity_PA_Budget();
	
	
	public String[] PL_Other_Income_Budget();
	
	public String[] PL_Other_Income_LA_Budget();
	
	public String[] PL_Other_Income_PA_Budget();

	
	public String[] PL_OtherExpenses_Budget();
	public String[] PL_OtherExpenses_LA_Budget();
	public String[] PL_OtherExpenses_PA_Budget();
	
//	public String[] PL_SellingPrice_LA_Budget_Detail(String MA);
//	public String[] PL_SellingPrice_PA_Budget_Detail(String MA);
	
	public String[] getSellingPriceHashTable(String MA);
	public String[] getAmountHashTable(String MA);
	
	public String[] PL_WIP_Opening_LA();
	
	public String[] PL_WIP_Closing_LA();

	public String[] PL_FinishGoods_Opening_LA();
	
	public String[] PL_FinishGoods_Closing_LA();
	
	public String[] PL_WIP_Opening_PA();
	
	public String[] PL_WIP_Closing_PA();

	public String[] PL_FinishGoods_Opening_PA();
	
	public String[] PL_FinishGoods_Closing_PA();

	public String[] PL_CostOfMaterial_LA();
	
	public String[] PL_CostOfMaterial_PA();
	
	public String[] PL_ManufacturingCost_LA();
	
	public String[] PL_ManufacturingCost_PA();
	
	public void SetCellFormula_Month(int i, Cell cell, String item);
	
	public void SetCellFormula_Total(int i, Cell cell, String item);
}
