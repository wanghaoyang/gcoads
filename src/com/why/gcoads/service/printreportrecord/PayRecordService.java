package com.why.gcoads.service.printreportrecord;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.why.gcoads.dao.payrecord.PayRecordDao;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.PayRecord;
import com.why.gcoads.utils.StringUtil;

/**
 * 证明报告业务层
 * 
 */
public class PayRecordService {

    private PayRecordDao payRecordDao = new PayRecordDao();

    public void addPayRecord(PayRecord payRecord) {
        try {
            payRecordDao.addPayRecord(payRecord);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updatePayRecord(PayRecord payRecord) {
        try {
            payRecordDao.updatePayRecord(payRecord);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    public PageBean<PayRecord> findPayRecordByPager(
            PageBean<PayRecord> pagePayRecord,
            String loginname) {
        try {
            if (StringUtil.isNullOrEmpty(loginname)) {
                loginname = StringUtil.Empty;
            }
            pagePayRecord = payRecordDao
                    .findPayRecordByPager(pagePayRecord,
                            loginname);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return pagePayRecord;
    }

    @Test
    public void T(){
        PayRecordService payRecordService = new PayRecordService();
        PayRecord payRecord = new PayRecord();
        payRecord.setPrid(1);
        payRecord.setLoginname("1235");
        payRecord.setPaystartdatetime(new Date());;
        payRecord.setPayfinisheddatetime(new Date());;
        payRecord.setTotalcost(200);
        payRecord.setPaystatus(true);
        payRecord.setCertificationquantity(100);
        
        List<PayRecord> beanList = payRecordService.findPayRecordByPager(null, "").getBeanList();
        for (PayRecord payRecord2 : beanList) {
            System.out.println(payRecord);
        }
    }
    
}
