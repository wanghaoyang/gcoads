package com.why.gcoads.commons;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.Date;

import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Section;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

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

	private static final int maxWidth = 520;
	private static final float[] rightTableCol = new float[]{64f, 64f};
	private static final float[] contentTableCol = new float[]{64f, 150f};

	public static void main(String[] args) {

		// 调用第一个方法，向C盘生成一个名字为ITextTest.pdf 的文件
		try {
			writeSimplePdf();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 调用第二个方法，向C盘名字为ITextTest.pdf的文件，添加章节。
		try {
			// writeCharpter();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void writeSimplePdf() throws Exception {

		// 1.新建document对象
		// 第一个参数是页面大小。接下来的参数分别是左、右、上和下页边距。
		Document document = new Document(PageSize.CROWN_QUARTO, 70, 50, 50, 50);

		// 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
		// 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("D:\\_testreport\\" + new Date().getTime()
						+ "_ITextTest.pdf"));

		/**
		 * 新建一个字体,iText的方法 STSongStd-Light 是字体，在iTextAsian.jar 中以property为后缀
		 * UniGB-UCS2-H 是编码，在iTextAsian.jar 中以cmap为后缀 H 代表文字版式是 横版， 相应的 V 代表竖版
		 */
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", false);

		Font bold_fontChineseTitle = new Font(bfChinese, 18, Font.BOLD,
				Color.BLACK);

		Font bold_fontChinese = new Font(bfChinese, 12, Font.BOLD, Color.BLACK);
		Font italic_fontChinese = new Font(bfChinese, 12, Font.ITALIC,
				Color.BLACK);
		Font impressFont = new Font(bfChinese, 16, Font.BOLDITALIC, Color.BLACK);

		Font normal_fontChinese = new Font(bfChinese, 12, Font.NORMAL,
				Color.BLACK);

		// 3.打开文档
		document.open();
		
		WaterMark.waterMark("D:/_testreport/1491556103797_ITextTest.pdf", "D:/_testreport/Koala.jpg", "D:/_testreport/1_ITextTest.pdf", "学历证明", 16);
		//
		// 4.向文档中添加内容
		// // 通过 com.lowagie.text.Paragraph
		// 来添加文本。可以用文本及其默认的字体、颜色、大小等等设置来创建一个默认段落
		// document.add(new Paragraph("First page of the document."));
		// document.add(new
		// Paragraph("Some more text on the  first page with different color and font type.",
		// FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new
		// Color(255, 150, 200))));

		// 插入一个段落
		// Paragraph par = new Paragraph("我们", fontChinese);

		// document.add(par);
		//

		document.addTitle("clark Yang-杨洪宇");
		document.addAuthor("杨杨杨");
		Paragraph title = new Paragraph("毕业生学历证明", bold_fontChineseTitle);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);

		document.add(new Paragraph(" ", bold_fontChinese));

		PdfPTable rightTable = createTable(2);
		rightTable.setTotalWidth(rightTableCol);
		rightTable.addCell(createCell("报告编号：", bold_fontChinese, Element.ALIGN_RIGHT));
		rightTable.addCell(createCell("123xxxxx", bold_fontChinese, Element.ALIGN_LEFT));
		rightTable.addCell(createCell("报告日期：", bold_fontChinese, Element.ALIGN_RIGHT));
		rightTable.addCell(createCell("2017-01-01",bold_fontChinese, Element.ALIGN_LEFT));
		rightTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
		document.add(rightTable);

		document.add(new Paragraph(" ", bold_fontChinese));
		document.add(new Paragraph(" ", bold_fontChinese));

		PdfPTable table = createTable(2);
		table.setTotalWidth(contentTableCol);
		table.addCell(createCell("姓    名：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
		table.addCell(createCell("", normal_fontChinese, Element.ALIGN_LEFT));
		table.addCell(createCell("性    别：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
		table.addCell(createCell("", normal_fontChinese, Element.ALIGN_LEFT));
		table.addCell(createCell("出生日期：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
		table.addCell(createCell("", normal_fontChinese, Element.ALIGN_LEFT));
		table.addCell(createCell("学历类别：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
		table.addCell(createCell("", normal_fontChinese, Element.ALIGN_LEFT));
		table.addCell(createCell("层    次：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
		table.addCell(createCell("", normal_fontChinese, Element.ALIGN_LEFT));
		table.addCell(createCell("专业名称：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
		table.addCell(createCell("", normal_fontChinese, Element.ALIGN_LEFT));
		table.addCell(createCell("学习形式：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
		table.addCell(createCell("", normal_fontChinese, Element.ALIGN_LEFT));
		table.addCell(createCell("学    制：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
		table.addCell(createCell("", normal_fontChinese, Element.ALIGN_LEFT));
		table.addCell(createCell("入学日期：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
		table.addCell(createCell("", normal_fontChinese, Element.ALIGN_LEFT));
		table.addCell(createCell("毕业日期：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
		table.addCell(createCell("", normal_fontChinese, Element.ALIGN_LEFT));
		table.addCell(createCell("毕 结 业：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
		table.addCell(createCell("", normal_fontChinese, Element.ALIGN_LEFT));
		table.addCell(createCell("证书编号：", normal_fontChinese, Element.ALIGN_JUSTIFIED_ALL));
		table.addCell(createCell("", normal_fontChinese, Element.ALIGN_LEFT));
		table.addCell(createCell("以上学历情况属实，专此认证。", bold_fontChinese, Element.ALIGN_LEFT, 2, false));
		
		table.setHorizontalAlignment(Element.ALIGN_LEFT);

		document.add(table);

		// 5.关闭文档
		document.close();
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
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream("D:\\ITextTest.pdf"));

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
		document.add(new Paragraph(
				"Some more text on the first page with different color and font type.",
				FontFactory.getFont(FontFactory.defaultEncoding, 10, Font.BOLD,
						new Color(0, 0, 0))));
		Paragraph title1 = new Paragraph("Chapter 1", FontFactory.getFont(
				FontFactory.HELVETICA, 18, Font.BOLDITALIC,
				new Color(0, 0, 255)));

		// 新建章节
		Chapter chapter1 = new Chapter(title1, 1);
		chapter1.setNumberDepth(0);
		Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1",
				FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD,
						new Color(255, 0, 0)));
		Section section1 = chapter1.addSection(title11);
		Paragraph someSectionText = new Paragraph(
				"This text comes as part of section 1 of chapter 1.");
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

	public static PdfPCell createCell(String value, com.lowagie.text.Font font,
			int align) {
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

	public static PdfPCell createCell(String value, com.lowagie.text.Font font,
			int align, int colspan) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}

	public static PdfPCell createCell(String value, com.lowagie.text.Font font,
			int align, int colspan, boolean boderFlag) {
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
}
