package com.why.gcoads.commons;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Section;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfEncryption;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.why.gcoads.model.EducationalLevel;
import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PrintReportRecord;
import com.why.gcoads.model.Student;
import com.why.gcoads.utils.StringUtil;

/**
 * 描述：TODO 【JAVA生成PDF】
 * <p>
 * 
 * @title GeneratePDF
 * @author SYJ
 * @email songyanjun_stars@126.com
 * @date 2013-4-6
 * @version V1.0
 */
public class GeneratePDF {

    // //1，设置此PDF文件的权限，只有写上的是答应的。这里只答应打印，读取和保存，不答应修改等。
    // int intPermissions = PdfWriter.ALLOW_PRINTING |
    // PdfWriter.ALLOW_SCREENREADERS;
    // //2，若要实现其他权限如修改等 则需要写进密码，这里是设置密码加密标准或加密类型。
    // int intEncryptionType = PdfEncryption.STANDARD_ENCRYPTION_40;
    // //3，要是用这个方法需要引进一个jar包（bcprov-jdk15-137.jar）。第一个参数：打开时需要的密码；第二个参数：实用其他其他权限时使用的密码；第三个参数：可使用的权限；第四个参数：密码类型
    // writer.setEncryption(null, "hello".getBytes(),
    // intPermissions,intEncryptionType);

    private static final int maxWidth = 520;
    private static final float[] rightTableCol = new float[] { 64f, 64f };
    private static final float[] contentTableCol = new float[] { 64f, 150f };
    private static final int intPermissions = PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_SCREENREADERS;
    private static final int intEncryptionType = PdfEncryption.STANDARD_ENCRYPTION_40;
    private static final byte[] userPassword = null;
    private static final byte[] ownerPassword = "why".getBytes();
    private static final String imagePath = GeneratePDF.class.getResource("gcoads_seal.png").toString();
    private static final String waterMarkName = "学历证明";
    //private static final String SEAL_POSITION = "(盖章位置)";

    public static void writeSimplePdf(String filePath, String fileName, PrintReportRecord report) throws Exception {

        if (StringUtil.isNullOrEmpty(filePath) || StringUtil.isNullOrEmpty(fileName)) {
            return;
        }
        String newfilePath = filePath + "origin\\";
        File file = new File(newfilePath);
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(newfilePath + "目录不存在，需要创建");
            // 创建目录
            file.mkdirs();
        }

        String fileAbsolutePath = newfilePath + fileName + ".pdf";
        String outputAbsolutePath = filePath + fileName + ".pdf";

        // 1.新建document对象
        // 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
        Document document = new Document(PageSize.CROWN_QUARTO, 70, 50, 50, 50);

        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileAbsolutePath));

        /**
         * 新建一个字体,iText的方法 STSongStd-Light 是字体，在iTextAsian.jar 中以property为后缀
         * UniGB-UCS2-H 是编码，在iTextAsian.jar 中以cmap为后缀 H 代表文字版式是 横版， 相应的 V 代表竖版
         */
        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

        Font bold_fontChineseTitle = new Font(bfChinese, 18, Font.BOLD, Color.BLACK);

        Font bold_fontChinese = new Font(bfChinese, 12, Font.BOLD, Color.BLACK);
        Font italic_fontChinese = new Font(bfChinese, 12, Font.ITALIC, Color.BLACK);
        Font impressFont = new Font(bfChinese, 16, Font.BOLDITALIC, Color.BLACK);

        Font normal_fontChinese = new Font(bfChinese, 12, Font.NORMAL, Color.BLACK);

        // 3.打开文档
        document.open();

        // WaterMark.waterMark("D:/_testreport/1491556103797_ITextTest.pdf",
        // "D:/_testreport/Koala.jpg", "D:/_testreport/1_ITextTest.pdf", "学历证明",
        // 16);
        //
        // 4.向文档中添加内容
        // // 通过 com.lowagie.text.Paragraph
        // 来添加文本。可以用文本及其默认的字体、颜色、大小等等设置来创建一个默认段落
        // document.add(new Paragraph("First page of the document."));
        // document.add(new
        // Paragraph("Some more text on the first page with different color and
        // font type.",
        // FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new
        // Color(255, 150, 200))));

        // 插入一个段落
        // Paragraph par = new Paragraph("我们", fontChinese);

        // document.add(par);
        //

        document.addTitle("毕业生学历证明");
        document.addAuthor("毕业生学历证明系统");
        Paragraph title = new Paragraph("毕业生学历证明", bold_fontChineseTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        document.add(new Paragraph(" ", bold_fontChinese));

        PdfPTable rightTable = createTable(2);
        rightTable.setTotalWidth(rightTableCol);
        rightTable.addCell(createCell("报告编号：", bold_fontChinese, Element.ALIGN_RIGHT));
        rightTable.addCell(createCell(report.getDocnum(), bold_fontChinese, Element.ALIGN_LEFT));
        rightTable.addCell(createCell("报告日期：", bold_fontChinese, Element.ALIGN_RIGHT));
        rightTable.addCell(createCell(sdf.format(report.getPrintdatetime()), bold_fontChinese, Element.ALIGN_LEFT));
        rightTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        document.add(rightTable);

        document.add(new Paragraph(" ", bold_fontChinese));
        document.add(new Paragraph(" ", bold_fontChinese));

        PdfPTable table = createTable(2);
        table.setTotalWidth(contentTableCol);
        table.addCell(createCell("姓    名：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
        table.addCell(createCell(report.getGraduate().getStudentname(), normal_fontChinese, Element.ALIGN_LEFT));
        table.addCell(createCell("性    别：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
        table.addCell(createCell(report.getGraduate().getStudentgender(), normal_fontChinese, Element.ALIGN_LEFT));
        table.addCell(createCell("身份证号：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
        table.addCell(createCell(report.getStudent().getShenfenzhenghao(), normal_fontChinese, Element.ALIGN_LEFT));
        table.addCell(createCell("学历类别：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
        table.addCell(createCell(report.getGraduate().getXueli().getEducationallevel(), normal_fontChinese,
                Element.ALIGN_LEFT));
        table.addCell(createCell("专业名称：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
        table.addCell(createCell(report.getGraduate().getZhuanye(), normal_fontChinese, Element.ALIGN_LEFT));
        table.addCell(createCell("学习形式：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
        table.addCell(createCell("全日制", normal_fontChinese, Element.ALIGN_LEFT));
        table.addCell(createCell("学    制：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
        table.addCell(createCell(report.getStudent().getXuezhi() + " 年", normal_fontChinese, Element.ALIGN_LEFT));
        table.addCell(createCell("入学日期：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
        table.addCell(
                createCell(sdf.format(report.getGraduate().getRuxueshijian()), normal_fontChinese, Element.ALIGN_LEFT));
        table.addCell(createCell("毕业日期：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
        table.addCell(
                createCell(sdf.format(report.getGraduate().getBiyeshijian()), normal_fontChinese, Element.ALIGN_LEFT));
        table.addCell(createCell("毕 结 业：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
        table.addCell(createCell(report.getGraduate().getGstatus(), normal_fontChinese, Element.ALIGN_LEFT));
        table.addCell(createCell("证书编号：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
        table.addCell(
                createCell(report.getGraduate().getGraduatecertificatenum(), normal_fontChinese, Element.ALIGN_LEFT));
        table.addCell(createCell("以上学历情况属实，专此认证。", bold_fontChinese, Element.ALIGN_LEFT, 2, false));

        table.setHorizontalAlignment(Element.ALIGN_LEFT);

        document.add(table);
//        PdfContentByte cb = writer.getDirectContent();
//        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
//        cb.beginText();
//        cb.setFontAndSize(bf, 14);
//        cb.setTextMatrix(300, 150);
//        cb.showText(SEAL_POSITION);
//        cb.endText();
//        cb.setLineWidth(10f);
//        cb.stroke();
        // 5.关闭文档
        document.close();

        waterMark(fileAbsolutePath, imagePath, outputAbsolutePath, waterMarkName, intPermissions);
    }

    /**
     * 添加含有章节的pdf文件
     * 
     * @throws Exception
     */
    public static void writeCharpter() throws Exception {

        // 新建document对象 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);

        // 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:\\ITextTest.pdf"));

        // 打开文件
        document.open();

        // 标题
        document.addTitle("Hello mingri example");

        // 作者
        document.addAuthor("wolf");

        // 主题
        document.addSubject("This example explains how to add metadata.");
        document.addKeywords("iText, Hello mingri");
        document.addCreator("My program using iText");

        // document.newPage();
        // 向文档中添加内容
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("First page of the document."));
        document.add(new Paragraph("First page of the document."));
        document.add(new Paragraph("First page of the document."));
        document.add(new Paragraph("First page of the document."));
        document.add(new Paragraph("Some more text on the first page with different color and font type.",
                FontFactory.getFont(FontFactory.defaultEncoding, 10, Font.BOLD, new Color(0, 0, 0))));
        Paragraph title1 = new Paragraph("Chapter 1",
                FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC, new Color(0, 0, 255)));

        // 新建章节
        Chapter chapter1 = new Chapter(title1, 1);
        chapter1.setNumberDepth(0);
        Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1",
                FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
        Section section1 = chapter1.addSection(title11);
        Paragraph someSectionText = new Paragraph("This text comes as part of section 1 of chapter 1.");
        section1.add(someSectionText);
        someSectionText = new Paragraph("Following is a 3 X 2 table.");
        section1.add(someSectionText);
        document.add(chapter1);

        // 关闭文档
        document.close();

    }

    public static PdfPTable createTable(int colNumber) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorderWidth(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    public static PdfPCell createCell(String value, com.lowagie.text.Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setBorder(-1);
        return cell;
    }

    public static PdfPCell createCell(String value, com.lowagie.text.Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    public static PdfPCell createCell(String value, com.lowagie.text.Font font, int align, int colspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    public static PdfPCell createCell(String value, com.lowagie.text.Font font, int align, int colspan,
            boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        if (!boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        }
        return cell;
    }

    private static boolean waterMark(String inputFile, String imageFile, String outputFile, String waterMarkName,
            int permission) {
        try {
            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
            stamper.setEncryption(userPassword, ownerPassword, permission, intEncryptionType);
            // 这里的字体设置比较关键，这个设置是支持中文的写法
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 使用系统字体
            int total = reader.getNumberOfPages() + 1;

            PdfContentByte under;
            Rectangle pageRect = null;
            for (int i = 1; i < total; i++) {
                pageRect = stamper.getReader().getPageSizeWithRotation(i);
                // 计算水印X,Y坐标
                float x = pageRect.getWidth() / 10;
                float y = pageRect.getHeight() / 10;
                // 获得PDF最顶层
                under = stamper.getUnderContent(i);
                under.saveState();
                // set Transparency
                PdfGState gs = new PdfGState();
                // 设置透明度为0.2
                gs.setFillOpacity(1.f);
                under.setGState(gs);
                // 注意这里必须调用一次restoreState 否则设置无效
                under.restoreState();
                under.beginText();
                under.setFontAndSize(base, 30);
                under.setColorFill(new Color(238, 209, 212));

                float width = pageRect.getWidth();
                float height = pageRect.getHeight();
                while (true) {
                    int increasement_x = 100;
                    int increasement_y = 100;
                    if (x < width) {
                        under.showTextAligned(Element.ALIGN_CENTER, waterMarkName, x, y, 45);
                        x += increasement_x;
                    } else {
                        x = 0;
                        y += increasement_y;
                    }

                    if (y > height) {
                        break;
                    }
                }
                under.endText();
                under.setLineWidth(10f);
                under.stroke();
                under = stamper.getOverContent(i);
                Image image = Image.getInstance(imageFile);
                // 图片位置
                image.setAbsolutePosition(pageRect.getWidth() - 180, 100);
                image.scalePercent(50f);
                under.addImage(image);
            }
            stamper.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Test
    public void ad() {
        PrintReportRecord report = new PrintReportRecord();
        Student stu = new Student();
        stu.setStudentname("张三");
        stu.setStudentgender("男");
        ;
        stu.setXuezhi(4);
        ;
        stu.setShenfenzhenghao("123123123412121234");
        Graduate gra = new Graduate(stu);
        EducationalLevel el = new EducationalLevel();
        el.setElid(1);
        el.setEducationallevel("博士毕业生");
        gra.setXueli(el);
        gra.setZhuanye("计算机");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        gra.setRuxueshijian(new Date());
        gra.setBiyeshijian(new Date());
        gra.setGstatus("毕业");
        gra.setGraduatecertificatenum("ABC123123123");
        report.setDocnum("HH00001");
        report.setPrintdatetime(new Date());
        report.setStudent(stu);
        report.setGraduate(gra);
        try {
            GeneratePDF.writeSimplePdf("d:\\_testreport\\", "mytest", report);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
