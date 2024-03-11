package com.hyundai.domain.utils.file;

import com.hyundai.domain.admin.dto.AdminMemberDTO;
import com.hyundai.domain.utils.dto.OrderListDeliveryDTO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * author : 강은구
 * fileName : DownExcelView
 * description :
 * since : 02/13/2024
 */
@RequiredArgsConstructor
@Service
public class DownExcelView {

    public void makeWorkbook(List<AdminMemberDTO> dataList, String fileName, HttpServletResponse response) throws IOException {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(fileName);
        Row row = null;
        Cell cell = null;
        int rowNo =0;
        // 테이블 헤더용 스타일
        CellStyle headStyle = wb.createCellStyle();
        // 가는 경계선을 가집니다.
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        // 배경색은 노란색입니다.
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        String[] rows = {"회원번호", "이메일", "이름","닉네임","전화번호","성별","생년월일","권한","생성일자","탈퇴여부"};

        int colNum = 0;

        row = sheet.createRow(rowNo++);

        for (String ro: rows) {
            cell = row.createCell(colNum++);
            cell.setCellStyle(headStyle);
            cell.setCellValue(ro);
        }
        for(AdminMemberDTO dto : dataList){
            row = sheet.createRow(rowNo++);
            cell = row.createCell(0);
            cell.setCellValue(dto.getMemberId());
            cell = row.createCell(1);
            cell.setCellValue(dto.getMemberEmail());

            cell = row.createCell(2);
            cell.setCellValue(dto.getMemberName());

            cell = row.createCell(3);
            cell.setCellValue(dto.getMemberNickname());

            cell = row.createCell(4);
            cell.setCellValue(dto.getMemberPhone());

            cell = row.createCell(5);
            cell.setCellValue(dto.getMemberGender());

            cell = row.createCell(6);
            cell.setCellValue(dto.getMemberBirth());

            cell = row.createCell(7);
            cell.setCellValue(dto.getMemberRole().toString());

            cell = row.createCell(8);
            cell.setCellValue(dto.getCreatedDate());

            cell = row.createCell(9);
            cell.setCellValue(dto.getDeletedDate());
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        wb.write(response.getOutputStream());
        wb.close();

    }
    public void orderExcel(List<OrderListDeliveryDTO> dataList, String fileName, HttpServletResponse response) throws IOException {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(fileName);
        Row row = null;
        Cell cell = null;
        int rowNo =0;
        // 테이블 헤더용 스타일
        CellStyle headStyle = wb.createCellStyle();
        // 가는 경계선을 가집니다.
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        // 배경색은 노란색입니다.
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        String[] rows = {"상품 이름 및 정보", "주소","우편번호","수령인","전화번호","주문 요구 사항"};

        int colNum = 0;

        row = sheet.createRow(rowNo++);

        for (String ro: rows) {
            cell = row.createCell(colNum++);
            cell.setCellStyle(headStyle);
            cell.setCellValue(ro);
        }
        for(OrderListDeliveryDTO dto : dataList){
            row = sheet.createRow(rowNo++);
            cell = row.createCell(0);
            cell.setCellValue(dto.getProductName() + " " + dto.getProductType() + " " + dto.getOrderProductCount() + "개");
            cell = row.createCell(1);
            cell.setCellValue(dto.getAddress());

            cell = row.createCell(2);
            cell.setCellValue(dto.getZipCode());

            cell = row.createCell(3);
            cell.setCellValue(dto.getReceiverName());

            cell = row.createCell(4);
            cell.setCellValue(dto.getPhoneNumber());

            cell = row.createCell(5);
            cell.setCellValue(dto.getOrderRequired());
        }

        response.setContentType("application/vnd.ms-excel");
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=example.xls");
        wb.write(response.getOutputStream());
        wb.close();

    }
}
