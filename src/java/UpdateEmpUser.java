
import java.sql.Connection;
import java.sql.Statement;
import th.co.exat.helper.System.EtaConnectionFactory;

public class UpdateEmpUser {

    public static void main(String[] args) throws Exception {
        update("21614067", "20614967");
    }

    public static void update(String old, String newId) throws Exception {
        Connection conn = EtaConnectionFactory.getConnection();
        Statement stmt = conn.createStatement();
        System.out.println(stmt.executeUpdate("update rva_trx_trf_opn set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_trf_opn_note  set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_bank_count set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_cpn_count set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_opn_summary set emp_code ='" + newId + "' where emp_code ='" + old + "'"));

        System.out.println(stmt.executeUpdate("update rva_trx_rev_opn set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_tag_opn set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_mnl_summary set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_trf_mnl set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_rev_mnl set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_rev_adj set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_msg set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_cls_summary set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_trf_cls set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_rev_cls set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_uap_cls set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_9e_cnt set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_bnk_cnt_cpn set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_cpn_sold_h set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_trx_cpn_sold_l set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_int_rmt set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_int_trf_opn set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_int_rev_opn set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_int_tag_opn set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_int_cls_summary set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_int_trf_cls set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_int_rev_cls set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_int_uap_cls set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update rva_int_etc_rmt set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
        System.out.println(stmt.executeUpdate("update RVA_INT_ETC_TRX_DTL set emp_code ='" + newId + "' where emp_code ='" + old + "'"));
    }
    
    
    
    
}