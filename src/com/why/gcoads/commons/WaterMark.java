package com.why.gcoads.commons;
import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class WaterMark {

	public static void main(String[] args) throws DocumentException,
			IOException {
		// 要输出的pdf文件
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(new File("D:/_testreport/1491554068772_ITextTest.pdf")));
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// 将pdf文件先加水印然后输出
//		setWatermark(bos, "D:/_testreport/ITextTest.pdf", format.format(cal.getTime())
//				+ "  下载使用人：" + "测试user", 16);
		waterMark("D:/_testreport/1491556103797_ITextTest.pdf", "D:/_testreport/Koala.jpg", "D:/_testreport/1_ITextTest.pdf", "学历证明", 16);
	}

	public static boolean waterMark(String inputFile, String imageFile,
            String outputFile, String waterMarkName, int permission) {
        try {
            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
                    outputFile));
            //这里的字体设置比较关键，这个设置是支持中文的写法
            BaseFont base = BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 使用系统字体
            int total = reader.getNumberOfPages() + 1;
            Image image = Image.getInstance(imageFile);
            // 图片位置
            //image.setAbsolutePosition(110, 110);
           
            image.setAbsolutePosition(10, -10); 
            PdfContentByte under;
            Rectangle pageRect = null;
            for (int i = 1; i < total; i++) {
                pageRect = stamper.getReader().
                           getPageSizeWithRotation(i);
                // 计算水印X,Y坐标
                float x = pageRect.getWidth()/10;
                float y = pageRect.getHeight()/10-10;
                // 获得PDF最顶层
                under = stamper.getOverContent(i);
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
                
                while(true){
                	int increasement_x = 100;
                	int increasement_y = 100;
                	if (x < width){
                		under.showTextAligned(Element.ALIGN_CENTER
                                , waterMarkName, x,
                                y, 55);
                		x+=increasement_x;
                	}else{
                		x=0;
                		y+=increasement_y;
                	}
                	
                	if (y > height){
                		break;
                	}
                }
                
                under.endText();
                under.setLineWidth(10f);
                under.stroke();
            }
            stamper.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
