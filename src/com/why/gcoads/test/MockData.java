package com.why.gcoads.test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import com.why.gcoads.dao.graduate.GraduateDao;
import com.why.gcoads.dao.student.StudentDao;
import com.why.gcoads.dao.user.UserDao;
import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.Student;
import com.why.gcoads.model.User;
import com.why.gcoads.utils.jdbc.JdbcUtils;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class MockData {

    private static String[] a = new String[] { "数学科学学院", "物理学院", "信息科学技术学院", "化学与分子工程学院", "软件与微电子学院", "经济学院", "艺术学院",
            "外国语学院", "新闻与传播学院", "教育学院", "对外汉语教育学院" };
    private QueryRunner qr = new TxQueryRunner();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private GraduateDao graduateDao = new GraduateDao();
    private StudentDao studentDao = new StudentDao();
    private UserDao userDao = new UserDao();

    private static String[] pros = new String[] { "北京市", "天津市", "上海市", "重庆市", "河北省", "山西省", "辽宁省", "吉林省", "黑龙江省", "江苏省",
            "浙江省", "安徽省", "福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省", "海南省", "四川省", "贵州省", "云南省", "陕西省", "甘肃省",
            "青海省", "台湾省", "广西壮族自治区", "内蒙古自治区", "西藏自治区", "宁夏回族自治区", "新疆维吾尔自治区", "香港特别行政区", "澳门特别行政区" };
    private static List<String> proList = new ArrayList<String>(Arrays.asList(pros));

    private static Map<String, List<String>> cityMap = new HashMap<String, List<String>>();
    static {
        cityMap.put("北京市", new ArrayList<String>(Arrays.asList(new String[] { "北京市区", "北京市辖区" })));
        cityMap.put("上海市", new ArrayList<String>(Arrays.asList(new String[] { "上海市区", "上海市辖区" })));
        cityMap.put("天津市", new ArrayList<String>(Arrays.asList(new String[] { "天津市区", "天津市辖区" })));

        cityMap.put("重庆市", new ArrayList<String>(Arrays.asList(new String[] { "重庆市区", "重庆市辖区" })));
        cityMap.put("河北省", new ArrayList<String>(Arrays.asList(
                new String[] { "石家庄", "唐山市", "邯郸市", "秦皇市岛", "保市定", "张家市口", "承德市", "廊坊市", "沧州市", "衡水市", "邢台市" })));
        cityMap.put("山西省", new ArrayList<String>(Arrays
                .asList(new String[] { "太原市", "大同市", "阳泉市", "长治市", "晋城市", "朔州市", "晋中市", "运城市", "忻州市", "临汾市", "吕梁市" })));
        cityMap.put("辽宁省", new ArrayList<String>(Arrays.asList(new String[] { "沈阳市", "大连市", "鞍山市", "抚顺市", "本溪市", "丹东市",
                "锦州市", "营口市", "阜新市", "辽阳市", "盘锦市", "铁岭市", "朝阳市", "葫芦岛市" })));
        cityMap.put("吉林省", new ArrayList<String>(Arrays
                .asList(new String[] { "长春市", "吉林市", "四平市", "辽源市", "通化市", "白山市", "松原市", "白城市", "延边州", "长白山管委会" })));
        cityMap.put("黑龙江省", new ArrayList<String>(Arrays.asList(new String[] { "哈尔滨市", "齐齐哈尔市", "大庆市", "佳木斯市", "牡丹江市",
                "七台河市", "双鸭山市", "黑河市", "鸡西市", "伊春市", "绥化市", "鹤岗市", "加格达奇市" })));
        cityMap.put("江苏省", new ArrayList<String>(Arrays.asList(new String[] { "南京市", "苏州市", "无锡市", "常州市", "南通市", "扬州市",
                "镇江市", "泰州市", "盐城市", "连云港市", "宿迁市", "淮安市", "徐州市" })));
        cityMap.put("浙江省", new ArrayList<String>(Arrays
                .asList(new String[] { "杭州市", "宁波市", "温州市", "嘉兴市", "湖州市", "绍兴市", "金华市", "衢州市", "舟山市", "台州市", "丽水市" })));
        cityMap.put("安徽省", new ArrayList<String>(Arrays.asList(new String[] { "合肥市", "芜湖市", "蚌埠市", "淮南市", "马鞍山市", "淮北市",
                "铜陵市", "安庆市", "黄山市", "滁州市", "阜阳市", "宿州市", "巢湖市", "六安市", "亳州市", "池州市", "宣城市" })));
        cityMap.put("福建省", new ArrayList<String>(
                Arrays.asList(new String[] { "福州市", "厦门市", "莆田市", "三明市", "泉州市", "漳州市", "南平市", "龙岩市", "宁德市" })));
        cityMap.put("江西省", new ArrayList<String>(Arrays.asList(
                new String[] { "南昌市", "景德镇市", "萍乡市", "九江市", "新余市", "鹰潭市", "赣州市", "吉安市", "宜春市", "抚州市", "上饶市" })));
        cityMap.put("山东省", new ArrayList<String>(Arrays.asList(new String[] { "济南市", "青岛市", "淄博市", "枣庄市", "东营市", "烟台市",
                "潍坊市", "济宁市", "泰安市", "威海市", "日照市", "莱芜市", "临沂市", "德州市", "聊城市", "滨州市", "菏泽市" })));
        cityMap.put("河南省", new ArrayList<String>(Arrays.asList(new String[] { "郑州市", "开封市", "洛阳市", "平顶山市", "安阳市", "鹤壁市",
                "新乡市", "焦作市", "濮阳市", "许昌市", "漯河市", "三门峡市", "南阳市", "商丘市", "信阳市", "周口市", "驻马店市" })));
        cityMap.put("湖北省", new ArrayList<String>(Arrays.asList(
                new String[] { "武汉市", "黄石市", "十堰市", "荆州市", "宜昌市", "襄樊市", "鄂州市", "荆门市", "孝感市", "黄冈市", "咸宁市", "随州市" })));
        cityMap.put("湖南省", new ArrayList<String>(Arrays.asList(new String[] { "长沙市", "株洲市", "湘潭市", "衡阳市", "邵阳市", "岳阳市",
                "常德市", "张家界市", "益阳市", "郴州市", "永州市", "怀化市", "娄底市" })));
        cityMap.put("广东省",
                new ArrayList<String>(
                        Arrays.asList(new String[] { "广州市", "深圳市", "珠海市", "汕头市", "韶关市", "佛山市", "江门市", "湛江市", "茂名市",
                                "肇庆市", "惠州市", "梅州市", "汕尾市", "河源市", "阳江市", "清远市", "东莞市", "中山市", "潮州市", "揭阳市", "云浮市" })));
        cityMap.put("海南省",
                new ArrayList<String>(Arrays.asList(new String[] { "文昌市", "琼海市", "万宁市", "五指山市", "东方市", "儋州市" })));
        cityMap.put("四川省 ", new ArrayList<String>(Arrays.asList(new String[] { "成都市", "自贡市", "攀枝花市", "泸州市", "德阳市",
                "绵阳市", "广元市", "遂宁市", "内江市", "乐山市", "南充市", "眉山市", "宜宾市", "广安市", "达州市", "雅安市", "巴中市", "资阳市" })));
        cityMap.put("贵州省", new ArrayList<String>(Arrays.asList(new String[] { "贵阳市", "六盘水市", "遵义市", "安顺市" })));
        cityMap.put("云南省", new ArrayList<String>(
                Arrays.asList(new String[] { "昆明市", "曲靖市", "玉溪市", "保山市", "昭通市", "丽江市", "普洱市", "临沧市" })));
        cityMap.put("陕西省", new ArrayList<String>(
                Arrays.asList(new String[] { "西安市", "铜川市", "宝鸡市", "咸阳市", "渭南市", "延安市", "汉中市", "榆林市", "安康市", "商洛市" })));
        cityMap.put("甘肃省", new ArrayList<String>(Arrays.asList(
                new String[] { "兰州市", "金昌市", "白银市", "天水市", "嘉峪关市", "武威市", "张掖市", "平凉市", "酒泉市", "庆阳市", "定西市", "陇南市" })));
        cityMap.put("青海省", new ArrayList<String>(Arrays.asList(new String[] { "西宁市" })));
        cityMap.put("台湾省",
                new ArrayList<String>(Arrays.asList(new String[] { "台北市", "高雄市", "基隆市", "台中市", "台南市", "新竹市", "嘉义市" })));
        cityMap.put("广西壮族自治区", new ArrayList<String>(Arrays.asList(new String[] { "南宁市", "柳州市", "桂林市", "梧州市", "北海市",
                "防城港市", "钦州市", "贵港市", "玉林市", "百色市", "贺州市", "河池市", "来宾市", "崇左市" })));
        cityMap.put("内蒙古自治区", new ArrayList<String>(Arrays
                .asList(new String[] { "呼和浩特市", "包头市", "乌海市", "赤峰市", "通辽市", "鄂尔多斯市", "呼伦贝尔市", "巴彦淖尔市", "乌兰察布市" })));
        cityMap.put("西藏自治区", new ArrayList<String>(Arrays.asList(new String[] { "拉萨市" })));
        cityMap.put("宁夏回族自治区",
                new ArrayList<String>(Arrays.asList(new String[] { "银川市", "石嘴山市", "吴忠市", "固原市", "中卫市" })));
        cityMap.put("新疆维吾尔自治区", new ArrayList<String>(Arrays.asList(new String[] { "乌鲁木齐市", "克拉玛依市" })));
        cityMap.put("香港特别行政区",
                new ArrayList<String>(Arrays.asList(new String[] { "台北市", "高雄市", "基隆市", "台中市", "台南市", "新竹市", "嘉义市" })));
    }

    public void mockData() {

        // "Student [sid=" + sid + ", kaoshenghao=" + kaoshenghao + ",
        // shenfenzhenghao=" + shenfenzhenghao
        // + ", xuehao=" + xuehao + ", studentname=" + studentname + ",
        // studentgender=" + studentgender
        // + ", minzu=" + minzu + ", zhengzhimianmao=" + zhengzhimianmao + ",
        // zhuanye=" + zhuanye
        // + ", zhuanyefangxiang=" + zhuanyefangxiang + ", peiyangfangshi=" +
        // peiyangfangshi + ", xuezhi=" + xuezhi
        // + ", ruxueshijian=" + ruxueshijian + ", biyeshijian=" + biyeshijian +
        // ", shifanshengleibie="
        // + shifanshengleibie + ", xueyuan=" + xueyuan + ", xibie=" + xibie +
        // ", banji=" + banji
        // + ", chushengriqi=" + chushengriqi + ", shengyuansuozaidi=" +
        // shengyuansuozaidi + ", email=" + email
        // + ", address=" + address + "]";
        String idcard = "1234561993010";// 10001
        String kaoshenghao = "12345619";// 10001
        String xuehao = "";
        int year = 2017;
        for (int i = 0; i < 5500; i++) {
            Student student = new Student();
            student.setKaoshenghao(kaoshenghao + 1 * 1000 + i);
            student.setXuehao(xuehao);
            student.setShenfenzhenghao(idcard + 1 * 1000 + i);
            student.setStudentname("");
            student.setStudentgender(Math.random() > 0.5 ? "男" : "女");
            String.format("%08d", i);

            User user = new User();
            Graduate graduate = new Graduate();
            try {
                if (!studentDao.isExistStudent(student)) {
                    JdbcUtils.beginTransaction();
                    studentDao.addStudent(student);
                    graduateDao.addGraduate(graduate);
                    userDao.add(user);
                    JdbcUtils.commitTransaction();
                }

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                try {
                    JdbcUtils.rollbackTransaction();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

    @Test
    public void random() {
        int ran = (int) (50 * Math.random() + 50);
        System.out.println(ran);
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(1, -1);// 年份减1

        System.out.println(gc.get(1));// 获得年份

        System.out.println(("1234561993010" + 1 * 1000 + 1).length());
        System.out.println(("1234561993010" + 1 * 1000 + 1));
        int rd = Math.random() > 0.5 ? 1 : 0;
        System.out.println(rd);
    }

}
