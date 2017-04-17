package com.why.gcoads.dao.payrecord;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.PayRecord;
import com.why.gcoads.utils.StringUtil;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class PayRecordDao {

    private QueryRunner qr = new TxQueryRunner();

    /**
     * 添加支付记录
     * 
     * @param payRecord
     * @throws SQLException
     */
    public int addPayRecord(PayRecord payRecord) throws SQLException {
        String sql = "INSERT INTO t_payrecord (loginname,shenfenzhenghao,paystartdatetime,payfinisheddatetime,totalcost,certificationquantity,paystatus) VALUES (?,?,?,?,?,?,?)";
        Object[] params = { payRecord.getLoginname(),
                payRecord.getShenfenzhenghao(),
                payRecord.getPaystartdatetime(),
                payRecord.getPayfinisheddatetime(), payRecord.getTotalcost(),
                payRecord.getCertificationquantity(), payRecord.getPaystatus() };
        qr.update(sql, params);
        sql = "select max(prid) from t_payrecord";
        Number number = (Number) qr.query(sql, new ScalarHandler());
        int key = number.intValue();// 得到了最新记录数
        return key;
    }
    
//    @Test
//    public void T(){
//        PayRecord payRecord = new PayRecord();
//        payRecord.setLoginname("123");
//        payRecord.setShenfenzhenghao("Test");
//        payRecord.setPaystartdatetime(new Date());
//        payRecord.setTotalcost(20);
//        payRecord.setCertificationquantity(10);
//        payRecord.setPaystatus(false);
//        try {
//            int key = new PayRecordDao().addPayRecord(payRecord);
//            System.out.println(key);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    /**
     * 更新支付记录
     * 
     * @param payRecord
     * @throws SQLException
     */
    public int updatePayRecord(PayRecord payRecord) throws SQLException {
        String sql = "UPDATE t_payrecord SET loginname = ?, paystartdatetime = ?, payfinisheddatetime = ?, totalcost = ?, certificationquantity = ?, paystatus = ? WHERE prid = ?";
        Object[] params = { payRecord.getLoginname(),
                payRecord.getPaystartdatetime(),
                payRecord.getPayfinisheddatetime(), payRecord.getTotalcost(),
                payRecord.getCertificationquantity(), payRecord.getPaystatus(),
                payRecord.getPrid() };
        int row = qr.update(sql, params);
        return row;
    }

    /**
     * 查询支付记录
     * 
     * @param prid
     * @return
     * @throws SQLException
     */
    public PayRecord findPayRecordByPrid(int prid) throws SQLException {
        String sql = "select * from t_payrecord where prid=?";
        return qr.query(sql, new BeanHandler<PayRecord>(PayRecord.class), prid);
    }
    
    /**
     * 查询支付记录
     * 
     * @param prid
     * @return
     * @throws SQLException
     */
    public PayRecord findPayRecordByloginname(String loginname) throws SQLException {
        String sql = "select * from t_payrecord where loginname=?";
        return qr.query(sql, new BeanHandler<PayRecord>(PayRecord.class), loginname);
    }

    /**
     * 分页查询，可通过用户名模糊查询支付记录
     * 
     * @param pagePayRecord
     * @param loginname
     * @return
     * @throws SQLException
     */
    public PageBean<PayRecord> findPayRecordByPager(
            PageBean<PayRecord> pagePayRecord, String loginname)
            throws SQLException {

        if (pagePayRecord == null) {
            pagePayRecord = new PageBean<PayRecord>();
            pagePayRecord.setPc(1);
            pagePayRecord.setPs(10);
        }

        if (StringUtil.isNullOrEmpty(loginname)) {
            loginname = StringUtil.Empty;
        } else {
            loginname.replace("\\", "\\\\");
            loginname.replace("%", "\\%");
            loginname.replace("_", "\\_");
            loginname.replace("'", "\'");
            loginname.replace("\"", "\\\"");
        }
        loginname = "%" + loginname + "%";

        String sql = "select count(1) from t_payrecord where loginname like ?";
        Number number = (Number) qr.query(sql, new ScalarHandler(), loginname);
        int tr = number.intValue();// 得到了总记录数
        pagePayRecord.setTr(tr);
        if (tr == 0) {
            pagePayRecord.setPc(1);
            pagePayRecord.setBeanList(new ArrayList<PayRecord>());
            return pagePayRecord;
        }
        if (pagePayRecord.getPc() > pagePayRecord.getTp()) {
            pagePayRecord.setPc(pagePayRecord.getTp());
        }

        sql = "select * from t_payrecord where loginname like ? order by payfinisheddatetime desc, paystartdatetime desc limit ?,? ";

        List<PayRecord> beanList = qr.query(sql,
                new BeanListHandler<PayRecord>(PayRecord.class), loginname,
                (pagePayRecord.getPc() - 1) * pagePayRecord.getPs(),
                pagePayRecord.getPs());
        pagePayRecord.setBeanList(beanList);

        return pagePayRecord;
    }
}
