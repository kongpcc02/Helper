/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P117;

import java.util.ArrayList;

/**
 *
 * @author chonpisit_klo
 */
public class SapCode {

    private String StationCode;
    private String SapCode;
    private ArrayList<SapCode> sttSapList = new ArrayList<SapCode>();
    
    public void setSttSapList(SapCode sap){
//        System.out.println(sap.getSapCode()+" ---  \n");
        this.sttSapList.add(sap);
    }
    
    public String getStationCode() {
        return StationCode;
    }

    public void setStationCode(String StationCode) {
        this.StationCode = StationCode;
    }

    public String getSapCode() {
        return SapCode;
    }

    public void setSapCode(String SapCode) {
        this.SapCode = SapCode;
    }
    
    public String findBySapSttCode(String sapCode){
        String sttCode = null;
        for(SapCode sap: this.sttSapList){
            if(sap.getSapCode().equals(sapCode)){
                sttCode = sap.getStationCode();
            }
        }
        return sttCode;
    }

}
