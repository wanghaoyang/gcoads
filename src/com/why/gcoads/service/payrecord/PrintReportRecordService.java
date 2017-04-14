package com.why.gcoads.service.payrecord;

import java.sql.SQLException;

import com.why.gcoads.dao.printreportrecord.PrintReportRecordDao;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.PrintReportRecord;
import com.why.gcoads.utils.StringUtil;

/**
 * 证明报告业务层
 * 
 */
public class PrintReportRecordService {

    private PrintReportRecordDao printReportRecordDao = new PrintReportRecordDao();

    public void addPrintReportRecord(PrintReportRecord printReportRecord) {
        try {
            printReportRecordDao.addPrintReportRecord(printReportRecord);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updatePrintReportRecord(PrintReportRecord printReportRecord) {
        try {
            printReportRecordDao.updatePrintReportRecord(printReportRecord);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    public PageBean<PrintReportRecord> findPrintReportRecordByPager(
            PageBean<PrintReportRecord> pagePrintReportRecord,
            String educationalLevel) {
        try {
            if (StringUtil.isNullOrEmpty(educationalLevel)) {
                educationalLevel = StringUtil.Empty;
            }

            pagePrintReportRecord = printReportRecordDao
                    .findPrintReportRecordByPager(pagePrintReportRecord,
                            educationalLevel);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return pagePrintReportRecord;
    }

}
