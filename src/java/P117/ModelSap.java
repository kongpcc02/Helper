/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P117;

/**
 *
 * @author chonpisit_klo
 */
public class ModelSap {

    private char listType = 'I';
    private String companyCode;
    private String trxDate;
    private String documentNo;
    private char amountType;
    private String accountCode;
    private int amount;
    private double vatCode;
    private String businessPlace;
    private String costCenter = null;
    private String profitCenter;
    private String assignmentNo;

    public String getCompanyCode() {
        return companyCode;
    }

    public char getListType() {
        return listType;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(String trxDate) {
        this.trxDate = trxDate;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public char getAmountType() {
        return amountType;
    }

    public void setAmountType(char amountType) {
        this.amountType = amountType;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getVatCode() {
        return vatCode;
    }

    public void setVatCode(double vatCode) {
        this.vatCode = vatCode;
    }

    public String getBusinessPlace() {
        return businessPlace;
    }

    public void setBusinessPlace(String businessPlace) {
        this.businessPlace = businessPlace;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public String getProfitCenter() {
        return profitCenter;
    }

    public void setProfitCenter(String profitCenter) {
        this.profitCenter = profitCenter;
    }

    public String getAssignmentNo() {
        return assignmentNo;
    }

    public void setAssignmentNo(String assignmentNo) {
        this.assignmentNo = assignmentNo;
    }

}
