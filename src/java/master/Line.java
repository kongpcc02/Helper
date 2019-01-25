/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package master;

/**
 *
 * @author Siridet
 */
public class Line {

    private int lineCode;
    private String lineDsc;

    public int getLineCode() {
        return lineCode;
    }

    public void setLineCode(int lineCode) {
        this.lineCode = lineCode;
    }

    public String getLineDsc() {
        return lineDsc;
    }

    public void setLineDsc(String lineDsc) {
        this.lineDsc = lineDsc;
    }

    public Line(int lineCode, String lineDsc) {
        this.lineCode = lineCode;
        this.lineDsc = lineDsc;
    }
}
