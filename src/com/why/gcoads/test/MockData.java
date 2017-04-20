package com.why.gcoads.test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import com.why.gcoads.commons.CommonUtils;
import com.why.gcoads.dao.graduate.GraduateDao;
import com.why.gcoads.dao.student.StudentDao;
import com.why.gcoads.dao.user.UserDao;
import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.Role;
import com.why.gcoads.model.Student;
import com.why.gcoads.model.User;
import com.why.gcoads.utils.jdbc.JdbcUtils;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class MockData {

    private static String[] college = new String[] { "数学科学学院", "物理学院", "信息科学技术学院", "化学与分子工程学院", "软件与微电子学院", "经济学院", "艺术学院",
            "外国语学院", "新闻与传播学院", "教育学院", "对外汉语教育学院" };
    private static String[] national = { "汉族", "壮族", "满族", "回族", "苗族", "维吾尔族", "土家族", "彝族", "蒙古族", "藏族", "布依族", "侗族",
        "瑶族", "朝鲜族", "白族", "哈尼族", "哈萨克族", "黎族", "傣族", "畲族", "傈僳族", "仡佬族", "东乡族", "高山族", "拉祜族", "水族", "佤族", "纳西族",
        "羌族", "土族", "仫佬族", "锡伯族", "柯尔克孜族", "达斡尔族", "景颇族", "毛南族", "撒拉族", "布朗族", "塔吉克族", "阿昌族", "普米族", "鄂温克族", "怒族",
        "京族", "基诺族", "德昂族", "保安族", "俄罗斯族", "裕固族", "乌孜别克族", "门巴族", "鄂伦春族", "独龙族", "塔塔尔族", "赫哲族", "珞巴族" };
    private static String[] zhengzhimianmao = { "群众", "共青团员", "中共党员", "民革党员", "民盟盟员", "民建会员", "民进会员", "农工党党员", "致公党党员",
        "九三学社社员", "台盟盟员", "无党派人士" };
    
    private QueryRunner qr = new TxQueryRunner();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
    private GraduateDao graduateDao = new GraduateDao();
    private StudentDao studentDao = new StudentDao();
    private UserDao userDao = new UserDao();

    private static String[] pros = new String[] { "北京市", "天津市", "上海市", "重庆市", "河北省", "山西省", "辽宁省", "吉林省", "黑龙江省", "江苏省",
            "浙江省", "安徽省", "福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省", "海南省", "四川省", "贵州省", "云南省", "陕西省", "甘肃省",
            "青海省", "台湾省", "广西壮族自治区", "内蒙古自治区", "西藏自治区", "宁夏回族自治区", "新疆维吾尔自治区", "香港特别行政区", "澳门特别行政区" };
    private static List<String> proList = new ArrayList<String>(Arrays.asList(pros));
@Test
    public void SS(){
    String ss = "";
        System.out.println(pros.length);
        for (String str : pros) {
            
            List<String> list = cityMap.get(str);
            if (list == null){
                System.out.println(str);
            }
        }
        Set<String> keySet = cityMap.keySet();
        
        System.out.println(keySet.size());
        
        
    }
    
    private static Map<String, List<String>> cityMap = new HashMap<String, List<String>>();
    static {
        cityMap.put("北京市", new ArrayList<String>(Arrays.asList(new String[] { "北京市区", "北京市辖区" })));
        cityMap.put("上海市", new ArrayList<String>(Arrays.asList(new String[] { "上海市区", "上海市辖区" })));
        cityMap.put("天津市", new ArrayList<String>(Arrays.asList(new String[] { "天津市区", "天津市辖区" })));
        cityMap.put("重庆市", new ArrayList<String>(Arrays.asList(new String[] { "重庆市区", "重庆市辖区" })));
        cityMap.put("河北省", new ArrayList<String>(Arrays.asList(new String[] { "石家庄", "唐山市", "邯郸市", "秦皇市岛", "保市定", "张家市口", "承德市", "廊坊市", "沧州市", "衡水市", "邢台市" })));
        cityMap.put("山西省", new ArrayList<String>(Arrays.asList(new String[] { "太原市", "大同市", "阳泉市", "长治市", "晋城市", "朔州市", "晋中市", "运城市", "忻州市", "临汾市", "吕梁市" })));
        cityMap.put("辽宁省", new ArrayList<String>(Arrays.asList(new String[] { "沈阳市", "大连市", "鞍山市", "抚顺市", "本溪市", "丹东市", "锦州市", "营口市", "阜新市", "辽阳市", "盘锦市", "铁岭市", "朝阳市", "葫芦岛市" })));
        cityMap.put("吉林省", new ArrayList<String>(Arrays.asList(new String[] { "长春市", "吉林市", "四平市", "辽源市", "通化市", "白山市", "松原市", "白城市", "延边州", "长白山管委会" })));
        cityMap.put("黑龙江省", new ArrayList<String>(Arrays.asList(new String[] { "哈尔滨市", "齐齐哈尔市", "大庆市", "佳木斯市", "牡丹江市","七台河市", "双鸭山市", "黑河市", "鸡西市", "伊春市", "绥化市", "鹤岗市", "加格达奇市" })));
        cityMap.put("江苏省", new ArrayList<String>(Arrays.asList(new String[] { "南京市", "苏州市", "无锡市", "常州市", "南通市", "扬州市", "镇江市", "泰州市", "盐城市", "连云港市", "宿迁市", "淮安市", "徐州市" })));
        cityMap.put("浙江省", new ArrayList<String>(Arrays.asList(new String[] { "杭州市", "宁波市", "温州市", "嘉兴市", "湖州市", "绍兴市", "金华市", "衢州市", "舟山市", "台州市", "丽水市" })));
        cityMap.put("安徽省", new ArrayList<String>(Arrays.asList(new String[] { "合肥市", "芜湖市", "蚌埠市", "淮南市", "马鞍山市", "淮北市", "铜陵市", "安庆市", "黄山市", "滁州市", "阜阳市", "宿州市", "巢湖市", "六安市", "亳州市", "池州市", "宣城市" })));
        cityMap.put("福建省", new ArrayList<String>(Arrays.asList(new String[] { "福州市", "厦门市", "莆田市", "三明市", "泉州市", "漳州市", "南平市", "龙岩市", "宁德市" })));
        cityMap.put("江西省", new ArrayList<String>(Arrays.asList(new String[] { "南昌市", "景德镇市", "萍乡市", "九江市", "新余市", "鹰潭市", "赣州市", "吉安市", "宜春市", "抚州市", "上饶市" })));
        cityMap.put("山东省", new ArrayList<String>(Arrays.asList(new String[] { "济南市", "青岛市", "淄博市", "枣庄市", "东营市", "烟台市", "潍坊市", "济宁市", "泰安市", "威海市", "日照市", "莱芜市", "临沂市", "德州市", "聊城市", "滨州市", "菏泽市" })));
        cityMap.put("河南省", new ArrayList<String>(Arrays.asList(new String[] { "郑州市", "开封市", "洛阳市", "平顶山市", "安阳市", "鹤壁市", "新乡市", "焦作市", "濮阳市", "许昌市", "漯河市", "三门峡市", "南阳市", "商丘市", "信阳市", "周口市", "驻马店市" })));
        cityMap.put("湖北省", new ArrayList<String>(Arrays.asList(new String[] { "武汉市", "黄石市", "十堰市", "荆州市", "宜昌市", "襄樊市", "鄂州市", "荆门市", "孝感市", "黄冈市", "咸宁市", "随州市" })));
        cityMap.put("湖南省", new ArrayList<String>(Arrays.asList(new String[] { "长沙市", "株洲市", "湘潭市", "衡阳市", "邵阳市", "岳阳市", "常德市", "张家界市", "益阳市", "郴州市", "永州市", "怀化市", "娄底市" })));
        cityMap.put("广东省", new ArrayList<String>(Arrays.asList(new String[] { "广州市", "深圳市", "珠海市", "汕头市", "韶关市", "佛山市", "江门市", "湛江市", "茂名市", "肇庆市", "惠州市", "梅州市", "汕尾市", "河源市", "阳江市", "清远市", "东莞市", "中山市", "潮州市", "揭阳市", "云浮市" })));
        cityMap.put("海南省", new ArrayList<String>(Arrays.asList(new String[] { "文昌市", "琼海市", "万宁市", "五指山市", "东方市", "儋州市" })));
        cityMap.put("四川省", new ArrayList<String>(Arrays.asList(new String[] { "成都市", "自贡市", "攀枝花市", "泸州市", "德阳市", "绵阳市", "广元市", "遂宁市", "内江市", "乐山市", "南充市", "眉山市", "宜宾市", "广安市", "达州市", "雅安市", "巴中市", "资阳市" })));
        cityMap.put("贵州省", new ArrayList<String>(Arrays.asList(new String[] { "贵阳市", "六盘水市", "遵义市", "安顺市" })));
        cityMap.put("云南省", new ArrayList<String>(Arrays.asList(new String[] { "昆明市", "曲靖市", "玉溪市", "保山市", "昭通市", "丽江市", "普洱市", "临沧市" })));
        cityMap.put("陕西省", new ArrayList<String>(Arrays.asList(new String[] { "西安市", "铜川市", "宝鸡市", "咸阳市", "渭南市", "延安市", "汉中市", "榆林市", "安康市", "商洛市" })));
        cityMap.put("甘肃省", new ArrayList<String>(Arrays.asList(new String[] { "兰州市", "金昌市", "白银市", "天水市", "嘉峪关市", "武威市", "张掖市", "平凉市", "酒泉市", "庆阳市", "定西市", "陇南市" })));
        cityMap.put("青海省", new ArrayList<String>(Arrays.asList(new String[] {"西宁市"})));
        cityMap.put("台湾省", new ArrayList<String>(Arrays.asList(new String[] { "台北市", "高雄市", "基隆市", "台中市", "台南市", "新竹市", "嘉义市" })));
        cityMap.put("广西壮族自治区", new ArrayList<String>(Arrays.asList(new String[] { "南宁市", "柳州市", "桂林市", "梧州市", "北海市", "防城港市", "钦州市", "贵港市", "玉林市", "百色市", "贺州市", "河池市", "来宾市", "崇左市" })));
        cityMap.put("内蒙古自治区", new ArrayList<String>(Arrays.asList(new String[] { "呼和浩特市", "包头市", "乌海市", "赤峰市", "通辽市", "鄂尔多斯市", "呼伦贝尔市", "巴彦淖尔市", "乌兰察布市" })));
        cityMap.put("西藏自治区", new ArrayList<String>(Arrays.asList(new String[] { "拉萨市" })));
        cityMap.put("宁夏回族自治区", new ArrayList<String>(Arrays.asList(new String[] { "银川市", "石嘴山市", "吴忠市", "固原市", "中卫市" })));
        cityMap.put("新疆维吾尔自治区", new ArrayList<String>(Arrays.asList(new String[] { "乌鲁木齐市", "克拉玛依市" })));
        cityMap.put("香港特别行政区", new ArrayList<String>(Arrays.asList(new String[] { "台北市", "高雄市", "基隆市", "台中市", "台南市", "新竹市", "嘉义市" })));
        cityMap.put("澳门特别行政区", new ArrayList<String>(Arrays.asList(new String[] { "澳门半岛", "澳门离岛"})));
    }

    public void mockData() {
        String idcard = "1993010";// 10001
        String kaoshenghao = "19";// 10001
        int year = 2017;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < college.length; j++) {
                int collPersonNum = RandomValue.getLimitRandom(150, 200);
                int collEmpPersonNum = RandomValue.getLimitRandom(collPersonNum - 40, collPersonNum);
                System.out.println(collEmpPersonNum+"--"+collPersonNum);
                for (int k = 0; k < collPersonNum; k++) {
                    try {
                        Student student = new Student();
                        student.setKaoshenghao((year - i) + String.format("%02d", j) + kaoshenghao + (1 * 10000 + k));
                        student.setXuehao((year-i) + String.format("%02d", j) + String.format("%04d", k));
                        student.setShenfenzhenghao((year - i) + String.format("%02d", j) + idcard + (1 * 10000 + k));
                        student.setStudentname(RandomValue.getChineseName());
                        student.setStudentgender(Math.random() > 0.5 ? "男" : "女");
                        student.setMinzu(national[RandomValue.getLimitRandom(0, national.length-1)]);
                        student.setZhengzhimianmao(zhengzhimianmao[RandomValue.getLimitRandom(0, zhengzhimianmao.length-1)]);
                        student.setZhuanye("<专业-"+ college[i].substring(0,2) +">TEST-"+k);
                        student.setZhuanyefangxiang("<专业方向"+college[i].substring(0,2)+">test-"+k);;
                        student.setXuezhi(4);
                        Date ruxueshijian = null;
                        Date biyeshijian = null;
                        Date chushengriqi = null;
                        try {
                            ruxueshijian = sdf.parse(year-i-4 + "-09-01");
                            biyeshijian = sdf.parse(year-i + "-06-30");
                            chushengriqi = sdf1.parse(student.getShenfenzhenghao().substring(6, 14));
                        } catch (ParseException e2) {
                            // TODO Auto-generated catch block
                            e2.printStackTrace();
                        }
                        student.setRuxueshijian(ruxueshijian);
                        student.setBiyeshijian(biyeshijian);
                        student.setShifanshengleibie(Math.random() > 0.5 ? "非师范类" : "师范类");
    
                        String collName = college[j];
                        student.setXueyuan(collName);
                        
                        student.setChushengriqi(chushengriqi);
                        student.setEmail(RandomValue.getEmail(6,9));
                        student.setShengyuansuozaidi(pros[RandomValue.getLimitRandom(0, pros.length-1)]);
                        student.setAddress(pros[RandomValue.getLimitRandom(0, pros.length-1)]);
                        
                        Graduate graduate = new Graduate(student);
                        graduate.setElid(3);
                        graduate.setBiyeshijian(biyeshijian);
                        graduate.setGraduatecertificatenum("HHG"+ (year-i) +String.format("%04d", j));
                        graduate.setGstatus("毕业");
                    
                        if (collEmpPersonNum > k) {
                            int pIndex = RandomValue.getLimitRandom(0, pros.length-1);
                            String province = pros[pIndex];
                            System.out.print(province +"<"+pIndex+">" + "---");
                            List<String> list = cityMap.get(province);
                            String[] citys = list.toArray(new String[list.size()]);
                            int cIndex = RandomValue.getLimitRandom(0, citys.length-1);
                            String city = citys[cIndex];
                            System.out.println(city + "<"+cIndex+">");
                            graduate.setProvince(province);
                            graduate.setCity(city);
                            
                        }
                        
                        User user = new User();
                        user.setLoginname(student.getShenfenzhenghao());
                        user.setLoginpass(student.getShenfenzhenghao().substring(student.getShenfenzhenghao().length() - 6));
                        user.setUid(CommonUtils.uuid());
                        user.setRole(Role.毕业生.toString());
                        user.setStatus(true);
                        user.setActivationCode(CommonUtils.uuid()+CommonUtils.uuid());
                        user.setEmail(student.getEmail());
                        user.setDeleted(false);
                        //String.format("%08d", i);
//                        System.out.println(student);
//                        System.out.println(graduate);
//                        System.out.println(user);
                        try {
                            if (!studentDao.isExistStudent(student)) {
                                JdbcUtils.beginTransaction();
                                studentDao.addStudent(student);
                                graduateDao.addGraduate(graduate);
                                userDao.add(user);
                                JdbcUtils.commitTransaction();
                            }
                        } catch (SQLException eeee) {
                        // TODO Auto-generated catch block
                            try {
                                JdbcUtils.rollbackTransaction();
                            } catch (SQLException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            eeee.printStackTrace();
                        }
                    }catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }
            }
        }
    }

public static void main(String[] args) {
    long startTime = System.currentTimeMillis();   //获取开始时间

    new MockData().mockData();

    long endTime = System.currentTimeMillis(); //获取结束时间
    
    long time = endTime - startTime;
    long min = time/1000/60;
    long sec = time/1000%60;
    long ms = time%1000;
    System.out.println(time +"ms = " + min + "min " + sec + "s " + ms + "ms");
    
}


//    @Test
//    public void random() {
//        int ran = (int) (50 * Math.random() + 50);
//        System.out.println(ran);
//        GregorianCalendar gc = new GregorianCalendar();
//        gc.add(1, -1);// 年份减1
//
//        System.out.println(gc.get(1));// 获得年份
//
//        System.out.println(("1234561993010" + 1 * 1000 + 1).length());
//        System.out.println(("1234561993010" + 1 * 1000 + 1));
//        int rd = Math.random() > 0.5 ? 1 : 0;
//        System.out.println(rd);
//    }

}
