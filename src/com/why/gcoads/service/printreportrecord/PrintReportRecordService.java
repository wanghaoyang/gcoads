package com.why.gcoads.service.printreportrecord;

import java.sql.SQLException;

import com.why.gcoads.dao.printreportrecord.PrintReportRecordDao;
import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.PrintReportRecord;
import com.why.gcoads.model.Student;
import com.why.gcoads.service.graduate.GraduateService;
import com.why.gcoads.utils.StringUtil;

/**
 * 证明报告业务层
 * 
 */
public class PrintReportRecordService {

    private PrintReportRecordDao printReportRecordDao = new PrintReportRecordDao();
    private GraduateService graduateService = new GraduateService();

    public int addPrintReportRecord(PrintReportRecord printReportRecord) {
        try {
            return printReportRecordDao.addPrintReportRecord(printReportRecord);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    public int updatePrintReportRecord(PrintReportRecord printReportRecord) {
        try {
            return printReportRecordDao.updatePrintReportRecord(printReportRecord);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    public PageBean<PrintReportRecord> findPrintReportRecordByPager(
            PageBean<PrintReportRecord> pagePrintReportRecord, String loginname) {
        try {
            if (StringUtil.isNullOrEmpty(loginname)) {
                loginname = StringUtil.Empty;
            }
            pagePrintReportRecord = printReportRecordDao
                    .findPrintReportRecordByPager(pagePrintReportRecord,
                            loginname);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return pagePrintReportRecord;
    }
    
    public PageBean<PrintReportRecord> findPrintReportRecordByLoginname(
            PageBean<PrintReportRecord> pagePrintReportRecord, String loginname) {
        try {
            if (StringUtil.isNullOrEmpty(loginname)) {
                loginname = StringUtil.Empty;
            }
            pagePrintReportRecord = printReportRecordDao
                    .findPrintReportRecordByPager(pagePrintReportRecord,
                            loginname);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return pagePrintReportRecord;
    }

    public PrintReportRecord findPrintReportRecordByPrrid(int prrid) {
        try {
            return printReportRecordDao.findPrintReportRecord(prrid);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }
    
    public PrintReportRecord findPrintReportRecordByshenfenzhenghao(
            String loginname, String shenfenzhenghao) {

        PrintReportRecord printReportRecord = null;
        if (StringUtil.isNullOrEmpty(shenfenzhenghao)) {
            return printReportRecord;
        }
        try {
            printReportRecord = printReportRecordDao
                    .findPrintReportRecordByshenfenzhenghao(loginname,
                            shenfenzhenghao);
            if (printReportRecord != null) {
                String xuehao = graduateService
                        .findXuehaoByshenfenzhenghao(shenfenzhenghao);
                if (StringUtil.isNullOrEmpty(xuehao)) {
                    return printReportRecord;
                }

                Student student = graduateService.findStudentByXuehao(xuehao);
                Graduate graduate = graduateService
                        .findGraduateByXuehao(xuehao);

                if (student != null && graduate != null) {

                    if (printReportRecord != null) {
                        printReportRecord.setStudent(student);
                        printReportRecord.setGraduate(graduate);
                    }
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return printReportRecord;
    }
}
