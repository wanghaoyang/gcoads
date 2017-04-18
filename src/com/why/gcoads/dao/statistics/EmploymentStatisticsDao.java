package com.why.gcoads.dao.statistics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import com.why.gcoads.model.EmploymentStatistics;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class EmploymentStatisticsDao {

    private QueryRunner qr = new TxQueryRunner();

    public List<EmploymentStatistics> findEmploymentStatisticsByYear(int year) throws SQLException {
        
        String sql = "select tt_graduate.biyeshijian as `year`, tt_graduate.xueyuan as xueyuan, tt_graduate.num as graduatecount, tt_employment.num as employmentcount from (select year(biyeshijian) as biyeshijian,xueyuan,count(1) as num from t_graduate where gstatus = '毕业' and not(deleted) and year(biyeshijian) = ? group by xueyuan) as tt_graduate left JOIN (select year(biyeshijian) as biyeshijian,xueyuan,count(1) as num from t_graduate where gstatus = '毕业' and not(deleted) and (province is not null OR city is not null) and year(biyeshijian) = ? group by xueyuan) as tt_employment on tt_graduate.xueyuan = tt_employment.xueyuan";
        
        List<EmploymentStatistics> employmentStatisticsList = qr.query(sql,
                new BeanListHandler<EmploymentStatistics>(EmploymentStatistics.class), year,year);
        return employmentStatisticsList;
    }
    
    public List<EmploymentStatistics> findEmploymentStatisticsByXueyuan(String xueyuan) throws SQLException {
        
        String sql = "select tt_graduate.biyeshijian as `year`, tt_graduate.xueyuan as xueyuan, tt_graduate.num as graduatecount, tt_employment.num as employmentcount from (select year(biyeshijian) as biyeshijian,xueyuan,count(1) as num from t_graduate where gstatus = '毕业' and not(deleted) and xueyuan = ? group by year(biyeshijian) ) as tt_graduate left JOIN (select year(biyeshijian) as biyeshijian,xueyuan,count(1) as num from t_graduate where gstatus = '毕业' and not(deleted) and (province is not null OR city is not null) and xueyuan = ? group by year(biyeshijian)) as tt_employment on tt_graduate.biyeshijian = tt_employment.biyeshijian";
        
        List<EmploymentStatistics> employmentStatisticsList = qr.query(sql,
                new BeanListHandler<EmploymentStatistics>(EmploymentStatistics.class), xueyuan,xueyuan);
        return employmentStatisticsList;
    }
    
    @Test
    public void T(){
        List<EmploymentStatistics> list = new ArrayList<EmploymentStatistics>();
        try {
            list = new EmploymentStatisticsDao().findEmploymentStatisticsByXueyuan("黑河学院");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(list);
    }
}
