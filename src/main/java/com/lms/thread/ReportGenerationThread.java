package com.lms.thread;

import com.lms.service.ReportService;

public class ReportGenerationThread extends Thread
{
    private final ReportService reportService;

    public ReportGenerationThread(ReportService reportService)
    {
        this.reportService = reportService;
    }

    @Override
    public void run()
    {
        reportService.generateReport();
    }
}