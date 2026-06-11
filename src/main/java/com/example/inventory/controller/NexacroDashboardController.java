package com.example.inventory.controller;

import com.nexacro.java.xapi.data.DataSet;
import com.nexacro.java.xapi.data.DataTypes;
import com.nexacro.java.xapi.data.PlatformData;
import com.nexacro.java.xapi.tx.HttpPlatformRequest;
import com.nexacro.java.xapi.tx.HttpPlatformResponse;
import com.nexacro.java.xapi.tx.PlatformType;
import com.example.inventory.domain.DashboardDto;
import com.example.inventory.service.DashboardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nexacro")
@RequiredArgsConstructor
public class NexacroDashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/getDashboard.do")
    public void getDashboard(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

        // 1. Nexacro 요청 파싱
        HttpPlatformRequest platformRequest = new HttpPlatformRequest(request);
        platformRequest.receiveData();
        PlatformData inData = platformRequest.getData();

        // 2. 대시보드 데이터 조회
        DashboardDto dashboard = dashboardService.getDashboard();

        // 3. 응답 PlatformData 구성
        PlatformData outData = new PlatformData();

        // 요약 카드 Dataset
        DataSet dsSummary = new DataSet("dsSummary");
        dsSummary.addColumn("totalItemCount", DataTypes.INT);
        dsSummary.addColumn("totalStockQty",  DataTypes.INT);
        dsSummary.addColumn("lowStockCount",  DataTypes.INT);
        dsSummary.addColumn("categoryCount",  DataTypes.INT);
        int row = dsSummary.newRow();
        dsSummary.set(row, "totalItemCount", dashboard.getTotalItemCount());
        dsSummary.set(row, "totalStockQty",  dashboard.getTotalStockQty());
        dsSummary.set(row, "lowStockCount",  dashboard.getLowStockCount());
        dsSummary.set(row, "categoryCount",  dashboard.getCategoryCount());
        outData.addDataSet(dsSummary);

        // 카테고리별 재고 Dataset
        DataSet dsCategoryStock = new DataSet("dsCategoryStock");
        dsCategoryStock.addColumn("categoryName", DataTypes.STRING, 100);
        dsCategoryStock.addColumn("itemCount",    DataTypes.INT);
        dsCategoryStock.addColumn("totalQty",     DataTypes.INT);
        for (DashboardDto.CategoryStockDto cat : dashboard.getCategoryStocks()) {
            int r = dsCategoryStock.newRow();
            dsCategoryStock.set(r, "categoryName", cat.getCategoryName());
            dsCategoryStock.set(r, "itemCount",    cat.getItemCount());
            dsCategoryStock.set(r, "totalQty",     cat.getTotalQty());
        }
        outData.addDataSet(dsCategoryStock);

        // 재고 부족 Dataset
        DataSet dsLowStock = new DataSet("dsLowStock");
        dsLowStock.addColumn("itemName",   DataTypes.STRING, 200);
        dsLowStock.addColumn("itemCode",   DataTypes.STRING, 50);
        dsLowStock.addColumn("minQty",     DataTypes.INT);
        dsLowStock.addColumn("currentQty", DataTypes.INT);
        for (var item : dashboard.getLowStockItems()) {
            int r = dsLowStock.newRow();
            dsLowStock.set(r, "itemName",   item.getItemName());
            dsLowStock.set(r, "itemCode",   item.getItemCode());
            dsLowStock.set(r, "minQty",     item.getMinQty());
            dsLowStock.set(r, "currentQty", item.getCurrentQty());
        }
        outData.addDataSet(dsLowStock);

        // 최근 이력 Dataset
        DataSet dsHistory = new DataSet("dsHistory");
        dsHistory.addColumn("itemName",    DataTypes.STRING, 200);
        dsHistory.addColumn("historyType", DataTypes.STRING, 3);
        dsHistory.addColumn("qty",         DataTypes.INT);
        dsHistory.addColumn("reason",      DataTypes.STRING, 500);
        dsHistory.addColumn("createdBy",   DataTypes.STRING, 100);
        dsHistory.addColumn("createdAt",   DataTypes.STRING, 20);
        for (var history : dashboard.getRecentHistories()) {
            int r = dsHistory.newRow();
            dsHistory.set(r, "itemName",    history.getItemName());
            dsHistory.set(r, "historyType", history.getHistoryType());
            dsHistory.set(r, "qty",         history.getQty());
            dsHistory.set(r, "reason",      history.getReason());
            dsHistory.set(r, "createdBy",   history.getCreatedBy());
            dsHistory.set(r, "createdAt",   history.getCreatedAt());
        }
        outData.addDataSet(dsHistory);

        // 4. 응답 전송
        HttpPlatformResponse platformResponse = new HttpPlatformResponse(
                response, PlatformType.CONTENT_TYPE_XML, "UTF-8"
        );
        platformResponse.setData(outData);
        platformResponse.sendData();
    }

}