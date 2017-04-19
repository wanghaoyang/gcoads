package com.why.gcoads.service.statistics;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.why.gcoads.dao.statistics.EmploymentStatisticsDao;
import com.why.gcoads.model.EmploymentStatistics;

public class EmploymentStatisticsService {

    private EmploymentStatisticsDao employmentStatisticsDao = new EmploymentStatisticsDao();

    public List<EmploymentStatistics> findEmploymentStatisticsByYear(int year) {
        
        List<EmploymentStatistics> employmentStatisticsList;
        try {
            employmentStatisticsList = employmentStatisticsDao.findEmploymentStatisticsByYear(year);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        
        return employmentStatisticsList;
    }
    
    public List<EmploymentStatistics> findEmploymentStatisticsByXueyuan(String xueyuan) {
        
        List<EmploymentStatistics> employmentStatisticsList;
        try {
            employmentStatisticsList = employmentStatisticsDao.findEmploymentStatisticsByXueyuan(xueyuan);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return employmentStatisticsList;
    }
    
    public List<String> findXueyuan(){
        List<String> xueyuanList = new ArrayList<String>();
        try {
            xueyuanList = employmentStatisticsDao.findXueyuan();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return xueyuanList;
    }
    
    public List<Integer> findYear() {
        List<Integer> yearList = new ArrayList<Integer>();
        try {
            yearList = employmentStatisticsDao.findYear();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return yearList;
    }
    
}
