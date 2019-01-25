/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package master;

/**
 *
 * @author Siridet
 */
public class Station {

    private String stationCode;
    private String stationDsc;

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationDsc() {
        return stationDsc;
    }

    public void setStationDsc(String stationDsc) {
        this.stationDsc = stationDsc;
    }

    public Station(String stationCode, String stationDsc) {
        this.stationCode = stationCode;
        this.stationDsc = stationDsc;
    }
}
