package backend.dto;

import backend.Model.enums.TaskStatus;

import java.util.List;

public class AnalyticsDtos {

    // ==========================================
    // Analytics Summary
    // ==========================================

    public record SummaryResponse(
            List<NameValue> tasksByStatus,
            List<WeeklyCompletion> completedPerWeek,
            List<EmployeeWorkload> tasksByEmployee,
            List<NameValue> leaveBreakdown
    ) {
    }


    // ==========================================
    // Name / Value
    // ==========================================

    public record NameValue(
            String name,
            int value
    ) {
    }


    // ==========================================
    // Weekly Completion
    // ==========================================

    public record WeeklyCompletion(
            String label,
            int completed
    ) {
    }


    // ==========================================
    // Employee Workload
    // ==========================================

    public record EmployeeWorkload(
            String employeeName,
            int active,
            int completed
    ) {
    }
}