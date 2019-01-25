/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import master.Line;
import master.Station;
import th.co.exat.helper.System.EtaConnectionFactory;

/**
 *
 * @author Siridet
 */
public class StationDAO {

    public static List<Station> read(int line) throws Exception {
        Connection c = EtaConnectionFactory.getConnection();
        Statement stmt = c.createStatement();
        StringBuffer sql = new StringBuffer("select station_code,station_dsc from rva_mst_sct_stt stt ");
        sql.append(" left join rva_mst_line_sct sct on stt.SECTOR_CODE = sct.sector_code  ");
        sql.append("where line_code = " + line);
        sql.append(" and stt.ACTIVE_STATUS = 'A' order by station_code");
        ResultSet rs = stmt.executeQuery(sql.toString());

        List list = new ArrayList();

        while (rs.next()) {
            Station l = new Station(rs.getString("station_code"), rs.getString("station_dsc"));
            list.add(l);
        }
        rs.close();
        stmt.close();
        c.close();

        return list;
    }

    public static void main(String[] args) throws Exception {
        List<Station> l = read(1);

        for (Station line : l) {
            System.out.println(line.getStationCode() + " | " + line.getStationDsc());
        }
    }
}
