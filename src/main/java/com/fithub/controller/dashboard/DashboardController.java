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

    @GetMapping(path = "/dashboard/subscriptionsByProduct", produces = "application/json")
    public String getSubscriptionsByProduct(){
        return dashboardService.getSubscriptionsByProduct().toString();
    }

    @GetMapping(path = "/dashboard/subscriptionsByProductCategory", produces = "application/json")
    public String getSubscriptionsByProductCategory(){
        return dashboardService.getSubscriptionsByCategory().toString();
    }

    @GetMapping(path = "/dashboard/subscriptionsByYear", produces = "application/json")
    public String getSubscriptionsByYear(){
        return dashboardService.getSubscriptionsByYear().toString();
    }

    @GetMapping(path = "/dashboard/subscriptionsByYear/{year}", produces = "application/json")
    public String getSubscriptionsByYear(@PathVariable int year){
        return dashboardService.getSubscriptionsByYear(year).toString();
    }

    @GetMapping(path = "/dashboard/subscriptionsByYearAndMonth", produces = "application/json")
    public String getSubscriptionsByYearAndMonth(){
        return dashboardService.getSubscriptionsByYearAndMonth().toString();
    }

    @GetMapping(path = "/dashboard/subscriptionsByYearAndMonth/{year}", produces = "application/json")
    public String getSubscriptionsByYearAndMonth(@PathVariable int year){
        return dashboardService.getSubscriptionsByYearAndMonth(year).toString();
    }

    @GetMapping(path = "/dashboard/subscriptionsByYearAndMonth/{year}/{month}", produces = "application/json")
    public String getSubscriptionsByYearAndMonth(@PathVariable int year, @PathVariable int month){
        return dashboardService.getSubscriptionsByYearAndMonth(year, month).toString();
    }

    @GetMapping(path = "/dashboard/subscriptionsByYearMonthAndDay", produces = "application/json")
    public String getSubscriptionsByYearMonthAndDay(){
        return dashboardService.getSubscriptionsByYearMonthAndDay().toString();
    }

    @GetMapping(path = "/dashboard/subscriptionsByYearMonthAndDay/{year}", produces = "application/json")
    public String getSubscriptionsByYearMonthAndDay(@PathVariable int year){
        return dashboardService.getSubscriptionsByYearMonthAndDay(year).toString();
    }

    @GetMapping(path = "/dashboard/subscriptionsByYearMonthAndDay/{year}/{month}", produces = "application/json")
    public String getSubscriptionsByYearMonthAndDay(@PathVariable int year, @PathVariable int month){
        return dashboardService.getSubscriptionsByYearMonthAndDay(year, month).toString();
    }


    @GetMapping(path = "/dashboard/subscriptionsByYearMonthAndDay/{year}/{month}/{day}", produces = "application/json")
    public String getSubscriptionsByYearMonthAndDay(@PathVariable int year, @PathVariable int month, @PathVariable int day){
        return dashboardService.getSubscriptionsByYearMonthAndDay(year, month, day).toString();
    }

    @GetMapping(path = "/dashboard/subscriptionsByStartDateAndEndDate", produces = "application/json")
    public String getSubscriptionsByStartDateAndEndDate(@RequestParam String startDate, @RequestParam String endDate){
        return dashboardService.getSubscriptionsByStartDateAndEndDate(startDate, endDate).toString();
    }
}
