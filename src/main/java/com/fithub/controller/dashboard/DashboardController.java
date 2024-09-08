package com.fithub.controller.dashboard;

import com.fithub.service.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping(path = "/dashboard/subscriptions-by-product", produces = "application/json")
    public String getSubscriptionsByProduct(){
        return dashboardService.getSubscriptionsByProduct().toString();
    }

    @GetMapping(path = "/dashboard/subscriptions-by-product-category", produces = "application/json")
    public String getSubscriptionsByProductCategory(){
        return dashboardService.getSubscriptionsByCategory().toString();
    }

    @GetMapping(path = "/dashboard/subscriptions-by-year", produces = "application/json")
    public String getSubscriptionsByYear(){
        return dashboardService.getSubscriptionsByYear().toString();
    }

    @GetMapping(path = "/dashboard/subscriptions-by-year/{year}", produces = "application/json")
    public String getSubscriptionsByYear(@PathVariable int year){
        return dashboardService.getSubscriptionsByYear(year).toString();
    }

    @GetMapping(path = "/dashboard/subscriptions-by-year-and-month", produces = "application/json")
    public String getSubscriptionsByYearAndMonth(){
        return dashboardService.getSubscriptionsByYearAndMonth().toString();
    }

    @GetMapping(path = "/dashboard/subscriptions-by-year-and-month/{year}", produces = "application/json")
    public String getSubscriptionsByYearAndMonth(@PathVariable int year){
        return dashboardService.getSubscriptionsByYearAndMonth(year).toString();
    }

    @GetMapping(path = "/dashboard/subscriptions-by-year-and-month/{year}/{month}", produces = "application/json")
    public String getSubscriptionsByYearAndMonth(@PathVariable int year, @PathVariable int month){
        return dashboardService.getSubscriptionsByYearAndMonth(year, month).toString();
    }

    @GetMapping(path = "/dashboard/subscriptions-by-year-month-and-day", produces = "application/json")
    public String getSubscriptionsByYearMonthAndDay(){
        return dashboardService.getSubscriptionsByYearMonthAndDay().toString();
    }

    @GetMapping(path = "/dashboard/subscriptions-by-year-month-and-day/{year}", produces = "application/json")
    public String getSubscriptionsByYearMonthAndDay(@PathVariable int year){
        return dashboardService.getSubscriptionsByYearMonthAndDay(year).toString();
    }

    @GetMapping(path = "/dashboard/subscriptions-by-year-month-and-day/{year}/{month}", produces = "application/json")
    public String getSubscriptionsByYearMonthAndDay(@PathVariable int year, @PathVariable int month){
        return dashboardService.getSubscriptionsByYearMonthAndDay(year, month).toString();
    }


    @GetMapping(path = "/dashboard/subscriptions-by-year-month-and-day/{year}/{month}/{day}", produces = "application/json")
    public String getSubscriptionsByYearMonthAndDay(@PathVariable int year, @PathVariable int month, @PathVariable int day){
        return dashboardService.getSubscriptionsByYearMonthAndDay(year, month, day).toString();
    }

    @GetMapping(path = "/dashboard/subscriptions-by-start-date-and-end-date", produces = "application/json")
    public String getSubscriptionsByStartDateAndEndDate(@RequestParam String startDate, @RequestParam String endDate){
        return dashboardService.getSubscriptionsByStartDateAndEndDate(startDate, endDate).toString();
    }
}
