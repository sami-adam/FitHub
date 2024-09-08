package com.fithub.service.dashboard;

import com.google.gson.JsonObject;

public interface DashboardService {
    JsonObject getSubscriptionsByProduct();
    JsonObject getSubscriptionsByCategory();
    JsonObject getSubscriptionsByYear();
    JsonObject getSubscriptionsByYear(int year);
    JsonObject getSubscriptionsByYearAndMonth();
    JsonObject getSubscriptionsByYearAndMonth(int year);
    JsonObject getSubscriptionsByYearAndMonth(int year, int month);
    JsonObject getSubscriptionsByYearMonthAndDay();
    JsonObject getSubscriptionsByYearMonthAndDay(int year);
    JsonObject getSubscriptionsByYearMonthAndDay(int year, int month);
    JsonObject getSubscriptionsByYearMonthAndDay(int year, int month, int day);
    JsonObject getSubscriptionsByStartDateAndEndDate(String startDate, String endDate);
}
