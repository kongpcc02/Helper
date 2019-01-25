/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P111;

/**
 *
 * @author siridet_suk
 */
public class TRXModel {

    String stt;
    String revCyber;
    String revGateway;

    public TRXModel(String stt, String revCyber, String revGateway) {
        this.stt = stt;
        this.revCyber = revCyber;
        this.revGateway = revGateway;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getRevCyber() {
        return revCyber;
    }

    public void setRevCyber(String revCyber) {
        this.revCyber = revCyber;
    }

    public String getRevGateway() {
        return revGateway;
    }

    public void setRevGateway(String revGateway) {
        this.revGateway = revGateway;
    }

}
