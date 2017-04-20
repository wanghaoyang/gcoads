package com.why.gcoads.utils;

import java.util.HashMap;
import java.util.Map;

public class BankMap {

    private static Map<String, String> bankMap = new HashMap<String, String>();
    static {
        bankMap.put("ICBC-NET-B2C", "中国工商银行");
        bankMap.put("CMBCHINA-NET-B2C", "招商银行");
        bankMap.put("ABC-NET-B2C", "中国农业银行");
        bankMap.put("CCB-NET-B2C", "中国建设银行");
        bankMap.put("BCCB-NET-B2C", "北京银行");
        bankMap.put("BOCO-NET-B2C", "交通银行");
        bankMap.put("CIB-NET-B2C", "兴业银行");
        bankMap.put("NJCB-NET-B2C", "南京银行");
        bankMap.put("CMBC-NET-B2C", "中国民生银行");
        bankMap.put("CEB-NET-B2C", "中国光大银行");
        bankMap.put("BOC-NET-B2C", "中国银行");
        bankMap.put("PINGANBANK-NET", "平安银行");
        bankMap.put("CBHB-NET-B2C", "渤海银行");
        bankMap.put("HKBEA-NET-B2C", "BEA東亞銀行");
        bankMap.put("NBCB-NET-B2C", "宁波银行");
        bankMap.put("ECITIC-NET-B2C", "中信银行");
        bankMap.put("SDB-NET-B2C", "深圳发展银行");
        bankMap.put("GDB-NET-B2C", "广东发展银行");
        bankMap.put("SHB-NET-B2C", "上海银行");
        bankMap.put("SPDB-NET-B2C", "上海浦东发展银行");
        bankMap.put("POST-NET-B2C", "中国邮政");
        bankMap.put("BJRCB-NET-B2C", "北京农村商业银行");
        bankMap.put("HXB-NET-B2C", "华夏银行");
        bankMap.put("CZ-NET-B2C", "浙商银行");
    }

    public static String getBank(String bank) {
        if (StringUtil.isNullOrEmpty(bank)) {
            return StringUtil.Empty;
        }
        return bankMap.get(bank);
    }
}
