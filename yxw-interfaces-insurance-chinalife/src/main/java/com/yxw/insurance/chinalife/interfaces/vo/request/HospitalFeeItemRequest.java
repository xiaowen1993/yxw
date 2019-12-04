package com.yxw.insurance.chinalife.interfaces.vo.request;

import java.io.Serializable;

/**
 * 就医处方明细
 * 
 * @author YangXuewen
 *
 */
public class HospitalFeeItemRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8883669669513884702L;

	//处方明细唯一编号
	private String medicalFeeSerialNumber = "";

	//医院处方明细唯一编号
	private String hospitalFeeSerialNumber = "";

	//社保医疗目录编码
	private String medicalCatalogCode = "";

	//社保医疗目录名称
	private String medicalCatalogName = "";

	//医院医疗目录编码
	private String hospitalCatalogCode = "";

	//医院医疗目录名称
	private String hospitalCatalogName = "";

	//社保报销类别
	private String medicalInsurancePaymentType = "";

	//医疗项目类别
	private String medicalClassification = "";

	//单价
	private String unitPrice = "";

	//限价标识
	private String limitedPriceSign = "";

	//数量
	private String quantity = "";

	//自费比例
	private String selfPaymentRatio = "";

	//医疗费用类别
	private String medicalFeeType = "";

	//总金额
	private String totalAmount = "";

	//中药副数
	private String chineseMedicineQuantity = "";

	//合规标识
	private String compliantSign = "";

	//处方药标识
	private String prescriptionIdentification = "";

	//医院自制药物标识
	private String hospitalHomeMadeDrugIdentification = "";

	//单位
	private String unit = "";

	//规格
	private String specification = "";

	//生产批号
	private String batchNumber = "";

	//有效期到
	private String validTill = "";

	//药物剂型
	private String dosageForm = "";

	//剂量（含量）
	private String dosage = "";

	//剂量（含量）单位
	private String dosageUnit = "";

	//常规用量
	private String normalUsage = "";

	//用药频次
	private String usedFrequency = "";

	//单次用量
	private String perUsage = "";

	//用量单位类型
	private String usageUnitType = "";

	//适用范围
	private String applicationRange = "";

	//禁忌范围
	private String contraindicantRange = "";

	//合规金额
	private String compliantPrice = "";

	//社保报销金额
	private String medicalInsurancePayment = "";

	//处方医生
	private String masterDoctor = "";

	//处方医生编码
	private String providerID = "";

	//医嘱时间
	private String orderDate = "";

	//医嘱说明
	private String orderDescription = "";

	//医嘱执行时间
	private String orderExecutedDate = "";

	public String getMedicalFeeSerialNumber() {
		return medicalFeeSerialNumber;
	}

	public void setMedicalFeeSerialNumber(String medicalFeeSerialNumber) {
		this.medicalFeeSerialNumber = medicalFeeSerialNumber;
	}

	public String getHospitalFeeSerialNumber() {
		return hospitalFeeSerialNumber;
	}

	public void setHospitalFeeSerialNumber(String hospitalFeeSerialNumber) {
		this.hospitalFeeSerialNumber = hospitalFeeSerialNumber;
	}

	public String getMedicalCatalogCode() {
		return medicalCatalogCode;
	}

	public void setMedicalCatalogCode(String medicalCatalogCode) {
		this.medicalCatalogCode = medicalCatalogCode;
	}

	public String getMedicalCatalogName() {
		return medicalCatalogName;
	}

	public void setMedicalCatalogName(String medicalCatalogName) {
		this.medicalCatalogName = medicalCatalogName;
	}

	public String getHospitalCatalogCode() {
		return hospitalCatalogCode;
	}

	public void setHospitalCatalogCode(String hospitalCatalogCode) {
		this.hospitalCatalogCode = hospitalCatalogCode;
	}

	public String getHospitalCatalogName() {
		return hospitalCatalogName;
	}

	public void setHospitalCatalogName(String hospitalCatalogName) {
		this.hospitalCatalogName = hospitalCatalogName;
	}

	public String getMedicalInsurancePaymentType() {
		return medicalInsurancePaymentType;
	}

	public void setMedicalInsurancePaymentType(String medicalInsurancePaymentType) {
		this.medicalInsurancePaymentType = medicalInsurancePaymentType;
	}

	public String getMedicalClassification() {
		return medicalClassification;
	}

	public void setMedicalClassification(String medicalClassification) {
		this.medicalClassification = medicalClassification;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getLimitedPriceSign() {
		return limitedPriceSign;
	}

	public void setLimitedPriceSign(String limitedPriceSign) {
		this.limitedPriceSign = limitedPriceSign;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSelfPaymentRatio() {
		return selfPaymentRatio;
	}

	public void setSelfPaymentRatio(String selfPaymentRatio) {
		this.selfPaymentRatio = selfPaymentRatio;
	}

	public String getMedicalFeeType() {
		return medicalFeeType;
	}

	public void setMedicalFeeType(String medicalFeeType) {
		this.medicalFeeType = medicalFeeType;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getChineseMedicineQuantity() {
		return chineseMedicineQuantity;
	}

	public void setChineseMedicineQuantity(String chineseMedicineQuantity) {
		this.chineseMedicineQuantity = chineseMedicineQuantity;
	}

	public String getCompliantSign() {
		return compliantSign;
	}

	public void setCompliantSign(String compliantSign) {
		this.compliantSign = compliantSign;
	}

	public String getPrescriptionIdentification() {
		return prescriptionIdentification;
	}

	public void setPrescriptionIdentification(String prescriptionIdentification) {
		this.prescriptionIdentification = prescriptionIdentification;
	}

	public String getHospitalHomeMadeDrugIdentification() {
		return hospitalHomeMadeDrugIdentification;
	}

	public void setHospitalHomeMadeDrugIdentification(String hospitalHomeMadeDrugIdentification) {
		this.hospitalHomeMadeDrugIdentification = hospitalHomeMadeDrugIdentification;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getValidTill() {
		return validTill;
	}

	public void setValidTill(String validTill) {
		this.validTill = validTill;
	}

	public String getDosageForm() {
		return dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getDosageUnit() {
		return dosageUnit;
	}

	public void setDosageUnit(String dosageUnit) {
		this.dosageUnit = dosageUnit;
	}

	public String getNormalUsage() {
		return normalUsage;
	}

	public void setNormalUsage(String normalUsage) {
		this.normalUsage = normalUsage;
	}

	public String getUsedFrequency() {
		return usedFrequency;
	}

	public void setUsedFrequency(String usedFrequency) {
		this.usedFrequency = usedFrequency;
	}

	public String getPerUsage() {
		return perUsage;
	}

	public void setPerUsage(String perUsage) {
		this.perUsage = perUsage;
	}

	public String getUsageUnitType() {
		return usageUnitType;
	}

	public void setUsageUnitType(String usageUnitType) {
		this.usageUnitType = usageUnitType;
	}

	public String getApplicationRange() {
		return applicationRange;
	}

	public void setApplicationRange(String applicationRange) {
		this.applicationRange = applicationRange;
	}

	public String getContraindicantRange() {
		return contraindicantRange;
	}

	public void setContraindicantRange(String contraindicantRange) {
		this.contraindicantRange = contraindicantRange;
	}

	public String getCompliantPrice() {
		return compliantPrice;
	}

	public void setCompliantPrice(String compliantPrice) {
		this.compliantPrice = compliantPrice;
	}

	public String getMedicalInsurancePayment() {
		return medicalInsurancePayment;
	}

	public void setMedicalInsurancePayment(String medicalInsurancePayment) {
		this.medicalInsurancePayment = medicalInsurancePayment;
	}

	public String getMasterDoctor() {
		return masterDoctor;
	}

	public void setMasterDoctor(String masterDoctor) {
		this.masterDoctor = masterDoctor;
	}

	public String getProviderID() {
		return providerID;
	}

	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}

	public String getOrderExecutedDate() {
		return orderExecutedDate;
	}

	public void setOrderExecutedDate(String orderExecutedDate) {
		this.orderExecutedDate = orderExecutedDate;
	}

}
