package com.why.gcoads.dao.printreportrecord;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.PrintReportRecord;
import com.why.gcoads.utils.StringUtil;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class PrintReportRecordDao {

    private QueryRunner qr = new TxQueryRunner();

    /**
     * 添加学历证明报告记录
     * 
     * @param payRecord
     * @throws SQLException
     */
    public void addPrintReportRecord(PrintReportRecord printReportRecord)
            throws SQLException {
        String sql = "INSERT INTO t_printreportrecord (loginname, reportname,reportpath,printdatetime,printpagenum,printstatus) VALUES (?,?,?,?,?,?)";
        Object[] params = { printReportRecord.getLoginname(),
                printReportRecord.getReportname(),
                printReportRecord.getReportpath(),
                printReportRecord.getPrintdatetime(),
                printReportRecord.getPrintpagenum(),
                printReportRecord.getPrintstatus() };
        qr.update(sql, params);
    }

    /**
     * 更新打印记录
     * 
     * @param printReportRecord
     * @throws SQLException
     */
    public void updatePrintReportRecord(PrintReportRecord printReportRecord)
            throws SQLException {
        String sql = "UPDATE t_payrecord SET loginname = ?, reportname = ?, reportpath = ?, printdatetime = ?, printpagenum = ?, printstatus = ? WHERE pprid = ?";
        Object[] params = { printReportRecord.getLoginname(),
                printReportRecord.getReportname(),
                printReportRecord.getReportpath(),
                printReportRecord.getPrintdatetime(),
                printReportRecord.getPrintpagenum(),
                printReportRecord.getPrintstatus(),
                printReportRecord.getPprid() };
        qr.update(sql, params);
    }

    /**
     * 查询打印记录
     * 
     * @param pprid
     * @return
     * @throws SQLException
     */
    public PrintReportRecord findPrintReportRecord(int pprid)
            throws SQLException {
        String sql = "select * from t_printreportrecord where pprid=?";
        return qr.query(sql, new BeanHandler<PrintReportRecord>(
                PrintReportRecord.class), pprid);
    }

    /**
     * 分页查询，可通过用户名模糊查询打印记录
     * 
     * @param pageEducationalLevel
     * @param educationallevel
     * @return
     * @throws SQLException
     */
    public PageBean<PrintReportRecord> findPrintReportRecordByPager(
            PageBean<PrintReportRecord> pagePrintReportRecord, String loginname)
            throws SQLException {

        if (pagePrintReportRecord == null) {
            pagePrintReportRecord = new PageBean<PrintReportRecord>();
            pagePrintReportRecord.setPc(1);
            pagePrintReportRecord.setPs(10);
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

        String sql = "select count(1) from t_printreportrecord where loginname like ?";
        Number number = (Number) qr.query(sql, new ScalarHandler(), loginname);
        int tr = number.intValue();// 得到了总记录数
        pagePrintReportRecord.setTr(tr);
        if (tr == 0) {
            pagePrintReportRecord.setPc(1);
            pagePrintReportRecord
                    .setBeanList(new ArrayList<PrintReportRecord>());
            return pagePrintReportRecord;
        }
        if (pagePrintReportRecord.getPc() > pagePrintReportRecord.getTp()) {
            pagePrintReportRecord.setPc(pagePrintReportRecord.getTp());
        }

        sql = "select * from t_printreportrecord where loginname like ? order by printdatetime desc limit ?,? ";

        List<PrintReportRecord> beanList = qr
                .query(sql, new BeanListHandler<PrintReportRecord>(
                        PrintReportRecord.class), pagePrintReportRecord,
                        (pagePrintReportRecord.getPc() - 1)
                                * pagePrintReportRecord.getPs(),
                        pagePrintReportRecord.getPs());
        pagePrintReportRecord.setBeanList(beanList);

        return pagePrintReportRecord;
    }
}
