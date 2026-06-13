package com.example.inventory.controller;

import com.example.inventory.domain.DashboardDto;
import com.example.inventory.mapper.DashboardNexacroMapper;
import com.example.inventory.service.DashboardService;
import com.nexacro.uiadapter.jakarta.core.data.NexacroResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nexacro")
@RequiredArgsConstructor
public class NexacroDashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public NexacroResult getDashboard() {

        DashboardDto dashboard = dashboardService.getDashboard();

        return DashboardNexacroMapper.toResult(dashboard);
    }
}