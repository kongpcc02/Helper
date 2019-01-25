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

import th.co.exat.helper.System.EtaConnectionFactory;

/**
 *
 * @author Siridet
 */
public class LineDAO {

    public static List<Line> read() throws Exception {
        Connection c = EtaConnectionFactory.getConnection();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("select * from rva_mst_line order by line_code");

        List list = new ArrayList();

        while (rs.next()) {
            Line l = new Line(rs.getInt("LINE_CODE"), rs.getString("LINE_DSC"));
            list.add(l);
        }
        rs.close();
        stmt.close();
        c.close();
        
        return list;
    }

    public static void main(String[] args) throws Exception {
        List<Line> l = read();

        for (Line line : l) {
            System.out.println(line.getLineCode()+" | " +line.getLineDsc());
        }
    }
}
