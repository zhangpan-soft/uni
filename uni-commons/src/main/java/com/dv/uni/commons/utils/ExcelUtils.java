package com.dv.uni.commons.utils;

import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.enums.Status;
import com.dv.uni.commons.exceptions.BaseException;
import com.dv.uni.commons.utils.sign.SignType;
import com.dv.uni.commons.utils.sign.SignUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.ApiModel;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/22 0022
 */
public class ExcelUtils {

    public static void export(Map<String,Object> templateMap, List<? extends BaseEntity<? extends Serializable>> list, HttpServletResponse response) {
        List<Map<String, String>> details = JacksonUtils.readValue((String) templateMap.get("details"), new TypeReference<List<Map<String, String>>>() {
        });
        String fileName = "";
        try (SXSSFWorkbook wb = new SXSSFWorkbook(100); OutputStream outputStream = response.getOutputStream()) {
            SXSSFSheet sheet = wb.createSheet();
            AtomicInteger rowNum = new AtomicInteger(0);
            {
                SXSSFRow row = sheet.createRow(rowNum.getAndIncrement());
                for (int i = 0; i < details.size(); i++) {
                    createCell(row, i, CellType.STRING, HorizontalAlignment.CENTER, wb, details.get(i)
                                                                                               .get("title"));
                }
            }
            {
                Class<?> clazz = Class.forName((String) templateMap.get("className"));
                ApiModel apiModel = clazz.getAnnotation(ApiModel.class);
                fileName=apiModel.value();
                list.forEach(l -> {
                    SXSSFRow row = sheet.createRow(rowNum.getAndIncrement());
                    for (int i = 0; i < details.size(); i++) {
                        Field field = null;
                        try {
                            field = clazz.getDeclaredField(details.get(i)
                                                                        .get("fieldName"));
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                            throw BaseException.of(Status.SYSTEM, e);
                        }
                        field.setAccessible(true);
                        String value = "";
                        Object o = null;
                        try {
                            o = field.get(l);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            throw BaseException.of(Status.SYSTEM, e);
                        }
                        if (o != null) {
                            if (field.getType() == Date.class) {
                                value = MyDateFormatUtil.format((Date) o);
                            } else {
                                value = String.valueOf(o);
                            }
                        }
                        createCell(row, i, CellType.valueOf(details.get(i)
                                                                   .get("cellType")), HorizontalAlignment.valueOf(details.get(i)
                                                                                                                         .get("horizontalAlignment")), wb, value);
                        field.setAccessible(false);
                    }
                });
            }
            if (StringUtils.isEmpty(fileName)){
                fileName = StringUtils.uuid();
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + MyDateFormatUtil.format(MyDateFormatUtil.Format.YYYY_MM_DD,new Date()) + ".xlsx", "UTF-8"));
            wb.write(outputStream);
        } catch (Exception e) {
            throw BaseException.of(Status.SYSTEM, e);
        }
    }

    /**
     * 字母转数字
     *
     * @param letter
     * @return
     */
    public static int letter2Number(String letter) {
        char[] chars = new StringBuffer(letter).reverse()
                                               .toString()
                                               .toUpperCase()
                                               .toCharArray();
        int num = 0;
        for (int i = 0; i < chars.length; i++) {
            num += Math.pow(26, i) * (chars[i] - 64);
        }
        return num - 1;
    }

    /**
     * 数字转字母
     *
     * @param number
     * @return
     */
    public static String number2Letter(int number) {
        StringBuffer sb = new StringBuffer();
        number2Letter_(sb, number + 1);
        return sb.reverse()
                 .toString();
    }

    public static SXSSFCell createCell(SXSSFRow row, int cellNum, CellType cellType, HorizontalAlignment horizontalAlignment, SXSSFWorkbook wb, String value) {
        SXSSFCell cell = row.createCell(cellNum, cellType);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(horizontalAlignment);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
        return cell;
    }

    private static void number2Letter_(StringBuffer sb, int number) {
        int m = number / 26;
        int n = number % 26;
        char c = (char) (n + 64);
        sb.append(c);
        if (m != 0) {
            number2Letter_(sb, m);
        }
    }

    public static void main(String[] args) {
        // 请求参数
        Map<String,String> params = new HashMap<>();
        params.put("skdf","sdfs");
        params.put("ckjlsw","dklsdf");
        params.put("weinjd","dksld");
        String sign = SignUtils.sign(params, "3ST6IRdF6Ocdt#UD(SdOrGAgoHY6G1Bm", SignType.MD5);
        System.out.println(sign);

        /*System.out.println(letter2Number("AA"));
        System.out.println(letter2Number("AB"));
        System.out.println(letter2Number("AC"));
        System.out.println(letter2Number("BA"));
        System.out.println(letter2Number("ABA"));
        System.out.println(letter2Number("ABC"));
        System.out.println(729 / 26 / 26 % 26);
        System.out.println(729 % 26 % 26);
        System.out.println(number2Letter(letter2Number("ABCD")));*/
        System.out.println(StringUtils.salt(32));
    }
}
