package com.why.gcoads.dao.statistics;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

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
    
    public List<String> findXueyuan() throws SQLException {
        
        String sql = "select (case when xueyuan is null then '' else xueyuan end ) as xueyuan from t_graduate group by xueyuan";
        
        List<String> xueyuanList = qr.query(sql, new ColumnListHandler<String>(1));
        return xueyuanList;
    }
    
    public List<Integer> findYear() throws SQLException {
        
        String sql = "select year(biyeshijian) as year from t_graduate where year(biyeshijian) is not null group by year(biyeshijian) order by year(biyeshijian) desc";
        
        List<Integer> yearList = qr.query(sql, new ColumnListHandler<Integer>(1));
        return yearList;
    }
}
