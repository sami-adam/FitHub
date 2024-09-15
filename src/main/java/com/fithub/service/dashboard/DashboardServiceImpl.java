package com.fithub.service.dashboard;

import com.fithub.dto.product.ProductCategoryDTO;
import com.fithub.dto.product.ProductDTO;
import com.fithub.dto.subscription.SubscriptionDTO;
import com.fithub.service.product.ProductCategoryService;
import com.fithub.service.product.ProductService;
import com.fithub.service.subscription.SubscriptionService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final SubscriptionService subscriptionService;
    @Override
    public JsonObject getSubscriptionsByProduct() {
        List<ProductDTO> products = productService.getProducts();
        JsonObject subscriptionsByProduct = new JsonObject();
        products.forEach(product -> {
            JsonObject productJson = new JsonObject();
            productJson.addProperty("name", product.getName());
            productJson.addProperty("productName", product.getName());
            //productJson.addProperty("subscriptionCount", productService.getSubscriptionsByProduct(product.getId()).size());
            //productJson.addProperty("Amount", productService.getSubscriptionsByProduct(product.getId()).stream().mapToDouble(SubscriptionDTO::getNetAmount).sum());
            subscriptionsByProduct.add(product.getId().toString(), productJson);
        });
        return subscriptionsByProduct;
    }

    @Override
    public JsonObject getSubscriptionsByCategory() {
        List<ProductCategoryDTO> productCategories = productCategoryService.getProductCategories();
        JsonObject subscriptionsByCategory = new JsonObject();
        productCategories.forEach(productCategory -> {
            JsonObject productCategoryJson = new JsonObject();
            productCategoryJson.addProperty("name", productCategory.getName());
            productCategoryJson.addProperty("productCategoryName", productCategory.getName());
            productCategoryJson.addProperty("subscriptionCount", productCategoryService.getSubscriptionsByCategory(productCategory.getId()).size());
            productCategoryJson.addProperty("Amount", productCategoryService.getSubscriptionsByCategory(productCategory.getId()).stream().mapToDouble(SubscriptionDTO::getNetAmount).sum());
            subscriptionsByCategory.add(productCategory.getId().toString(), productCategoryJson);
        });

        return subscriptionsByCategory;
    }

    @Override
    public JsonObject getSubscriptionsByYear() {
        // get last 5 years
        List<Integer> years = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            years.add(Calendar.getInstance().get(Calendar.YEAR) - i);
        }
        JsonObject subscriptionsByYear = new JsonObject();
        years.forEach(year -> {
            JsonObject yearJson = new JsonObject();
            yearJson.addProperty("name", year);
            yearJson.addProperty("year", year);
            yearJson.addProperty("subscriptionCount", subscriptionService.getSubscriptionsByYear(year).size());
            yearJson.addProperty("Amount", subscriptionService.getSubscriptionsByYear(year).stream().mapToDouble(SubscriptionDTO::getNetAmount).sum());
            subscriptionsByYear.add(year.toString(), yearJson);
        });
        return subscriptionsByYear;
    }

    @Override
    public JsonObject getSubscriptionsByYear(int year) {
        JsonObject subscriptionsByYear = new JsonObject();
        JsonObject yearJson = new JsonObject();
        yearJson.addProperty("name", year);
        yearJson.addProperty("year", year);
        yearJson.addProperty("subscriptionCount", subscriptionService.getSubscriptionsByYear(year).size());
        yearJson.addProperty("Amount", subscriptionService.getSubscriptionsByYear(year).stream().mapToDouble(SubscriptionDTO::getNetAmount).sum());
        subscriptionsByYear.add(String.valueOf(year), yearJson);
        return subscriptionsByYear;
    }

    @Override
    public JsonObject getSubscriptionsByYearAndMonth() {
        // get Year and Month for the last 5 years
        List< Map<String, Integer> > yearMonthList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for(int j = 1; j <= 12; j++) {
                Map<String, Integer> yearMonth = new HashMap<>();
                yearMonth.put("year", Calendar.getInstance().get(Calendar.YEAR) - i);
                yearMonth.put("month", j);
                yearMonthList.add(yearMonth);
            }
        }
        JsonObject subscriptionsByYearAndMonth = new JsonObject();
        yearMonthList.forEach(yearMonth -> {
            JsonObject yearMonthJson = new JsonObject();
            yearMonthJson.addProperty("name", yearMonth.get("year") + "-" + yearMonth.get("month"));
            yearMonthJson.addProperty("year", yearMonth.get("year"));
            yearMonthJson.addProperty("month", yearMonth.get("month"));
            yearMonthJson.addProperty("subscriptionCount", subscriptionService.getSubscriptionsByYearAndMonth(yearMonth.get("year"), yearMonth.get("month")).size());
            yearMonthJson.addProperty("Amount", subscriptionService.getSubscriptionsByYearAndMonth(yearMonth.get("year"), yearMonth.get("month")).stream().mapToDouble(SubscriptionDTO::getNetAmount).sum());
            subscriptionsByYearAndMonth.add(yearMonth.get("year") + "-" + yearMonth.get("month"), yearMonthJson);
        });

        return subscriptionsByYearAndMonth;
    }

    @Override
    public JsonObject getSubscriptionsByYearAndMonth(int year) {
        List< Map<String, Integer> > yearMonthList = new ArrayList<>();
        for(int j = 1; j <= 12; j++) {
            Map<String, Integer> yearMonth = new HashMap<>();
            yearMonth.put("year", year);
            yearMonth.put("month", j);
            yearMonthList.add(yearMonth);
        }
        JsonObject subscriptionsByYearAndMonth = new JsonObject();
        yearMonthList.forEach(yearMonth -> {
            JsonObject yearMonthJson = new JsonObject();
            yearMonthJson.addProperty("name", yearMonth.get("year") + "-" + yearMonth.get("month"));
            yearMonthJson.addProperty("year", yearMonth.get("year"));
            yearMonthJson.addProperty("month", yearMonth.get("month"));
            yearMonthJson.addProperty("subscriptionCount", subscriptionService.getSubscriptionsByYearAndMonth(yearMonth.get("year"), yearMonth.get("month")).size());
            yearMonthJson.addProperty("Amount", subscriptionService.getSubscriptionsByYearAndMonth(yearMonth.get("year"), yearMonth.get("month")).stream().mapToDouble(SubscriptionDTO::getNetAmount).sum());
            subscriptionsByYearAndMonth.add(yearMonth.get("year") + "-" + yearMonth.get("month"), yearMonthJson);
        });

        return subscriptionsByYearAndMonth;
    }

    @Override
    public JsonObject getSubscriptionsByYearAndMonth(int year, int month) {
        List< Map<String, Integer> > yearMonthList = new ArrayList<>();
        Map<String, Integer> yearMonth = new HashMap<>();
        yearMonth.put("year", year);
        yearMonth.put("month", month);
        yearMonthList.add(yearMonth);
        JsonObject subscriptionsByYearAndMonth = new JsonObject();
        yearMonthList.forEach(yearMonthMap -> {
            JsonObject yearMonthJson = new JsonObject();
            yearMonthJson.addProperty("name", yearMonthMap.get("year") + "-" + yearMonthMap.get("month"));
            yearMonthJson.addProperty("year", yearMonthMap.get("year"));
            yearMonthJson.addProperty("month", yearMonthMap.get("month"));
            yearMonthJson.addProperty("subscriptionCount", subscriptionService.getSubscriptionsByYearAndMonth(yearMonthMap.get("year"), yearMonthMap.get("month")).size());
            yearMonthJson.addProperty("Amount", subscriptionService.getSubscriptionsByYearAndMonth(yearMonthMap.get("year"), yearMonthMap.get("month")).stream().mapToDouble(SubscriptionDTO::getNetAmount).sum());
            subscriptionsByYearAndMonth.add(yearMonthMap.get("year") + "-" + yearMonthMap.get("month"), yearMonthJson);
        });

        return subscriptionsByYearAndMonth;
    }

    @Override
    public JsonObject getSubscriptionsByYearMonthAndDay() {
        // get Year, Month and Day for the last 5 years
        List< Map<String, Integer> > yearMonthDayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for(int j = 1; j <= 12; j++) {
                for(int k = 1; k <= Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH); k++) {
                    Map<String, Integer> yearMonthDay = new HashMap<>();
                    yearMonthDay.put("year", Calendar.getInstance().get(Calendar.YEAR) - i);
                    yearMonthDay.put("month", j);
                    yearMonthDay.put("day", k);
                    yearMonthDayList.add(yearMonthDay);
                }
            }
        }
        JsonObject subscriptionsByYearMonthAndDay = new JsonObject();
        yearMonthDayList.forEach(yearMonthDay -> {
            JsonObject yearMonthDayJson = new JsonObject();
            yearMonthDayJson.addProperty("name", yearMonthDay.get("year") + "-" + yearMonthDay.get("month") + "-" + yearMonthDay.get("day"));
            yearMonthDayJson.addProperty("year", yearMonthDay.get("year"));
            yearMonthDayJson.addProperty("month", yearMonthDay.get("month"));
            yearMonthDayJson.addProperty("day", yearMonthDay.get("day"));
            yearMonthDayJson.addProperty("subscriptionCount", subscriptionService.getSubscriptionsByYearMonthAndDay(yearMonthDay.get("year"), yearMonthDay.get("month"), yearMonthDay.get("day")).size());
            yearMonthDayJson.addProperty("Amount", subscriptionService.getSubscriptionsByYearMonthAndDay(yearMonthDay.get("year"), yearMonthDay.get("month"), yearMonthDay.get("day")).stream().mapToDouble(SubscriptionDTO::getNetAmount).sum());
            subscriptionsByYearMonthAndDay.add(yearMonthDay.get("year") + "-" + yearMonthDay.get("month") + "-" + yearMonthDay.get("day"), yearMonthDayJson);
        });

        return subscriptionsByYearMonthAndDay;
    }

    @Override
    public JsonObject getSubscriptionsByYearMonthAndDay(int year) {
        List< Map<String, Integer> > yearMonthDayList = new ArrayList<>();
        for(int j = 1; j <= 12; j++) {
            for(int k = 1; k <= Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH); k++) {
                Map<String, Integer> yearMonthDay = new HashMap<>();
                yearMonthDay.put("year", year);
                yearMonthDay.put("month", j);
                yearMonthDay.put("day", k);
                yearMonthDayList.add(yearMonthDay);
            }
        }
        JsonObject subscriptionsByYearMonthAndDay = new JsonObject();
        yearMonthDayList.forEach(yearMonthDay -> {
            JsonObject yearMonthDayJson = new JsonObject();
            yearMonthDayJson.addProperty("name", yearMonthDay.get("year") + "-" + yearMonthDay.get("month") + "-" + yearMonthDay.get("day"));
            yearMonthDayJson.addProperty("year", yearMonthDay.get("year"));
            yearMonthDayJson.addProperty("month", yearMonthDay.get("month"));
            yearMonthDayJson.addProperty("day", yearMonthDay.get("day"));
            yearMonthDayJson.addProperty("subscriptionCount", subscriptionService.getSubscriptionsByYearMonthAndDay(yearMonthDay.get("year"), yearMonthDay.get("month"), yearMonthDay.get("day")).size());
            yearMonthDayJson.addProperty("Amount", subscriptionService.getSubscriptionsByYearMonthAndDay(yearMonthDay.get("year"), yearMonthDay.get("month"), yearMonthDay.get("day")).stream().mapToDouble(SubscriptionDTO::getNetAmount).sum());
            subscriptionsByYearMonthAndDay.add(yearMonthDay.get("year") + "-" + yearMonthDay.get("month") + "-" + yearMonthDay.get("day"), yearMonthDayJson);
        });

        return subscriptionsByYearMonthAndDay;
    }

    @Override
    public JsonObject getSubscriptionsByYearMonthAndDay(int year, int month) {
        List< Map<String, Integer> > yearMonthDayList = new ArrayList<>();
        for(int k = 1; k <= Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH); k++) {
            Map<String, Integer> yearMonthDay = new HashMap<>();
            yearMonthDay.put("year", year);
            yearMonthDay.put("month", month);
            yearMonthDay.put("day", k);
            yearMonthDayList.add(yearMonthDay);
        }
        JsonObject subscriptionsByYearMonthAndDay = new JsonObject();
        yearMonthDayList.forEach(yearMonthDay -> {
            JsonObject yearMonthDayJson = new JsonObject();
            yearMonthDayJson.addProperty("name", yearMonthDay.get("year") + "-" + yearMonthDay.get("month") + "-" + yearMonthDay.get("day"));
            yearMonthDayJson.addProperty("year", yearMonthDay.get("year"));
            yearMonthDayJson.addProperty("month", yearMonthDay.get("month"));
            yearMonthDayJson.addProperty("day", yearMonthDay.get("day"));
            yearMonthDayJson.addProperty("subscriptionCount", subscriptionService.getSubscriptionsByYearMonthAndDay(yearMonthDay.get("year"), yearMonthDay.get("month"), yearMonthDay.get("day")).size());
            yearMonthDayJson.addProperty("Amount", subscriptionService.getSubscriptionsByYearMonthAndDay(yearMonthDay.get("year"), yearMonthDay.get("month"), yearMonthDay.get("day")).stream().mapToDouble(SubscriptionDTO::getNetAmount).sum());
            subscriptionsByYearMonthAndDay.add(yearMonthDay.get("year") + "-" + yearMonthDay.get("month") + "-" + yearMonthDay.get("day"), yearMonthDayJson);
        });

        return subscriptionsByYearMonthAndDay;
    }

    @Override
    public JsonObject getSubscriptionsByYearMonthAndDay(int year, int month, int day) {
        JsonObject subscriptionsByYearMonthAndDay = new JsonObject();
        JsonObject yearMonthDayJson = new JsonObject();
        yearMonthDayJson.addProperty("name", year + "-" + month + "-" + day);
        yearMonthDayJson.addProperty("year", year);
        yearMonthDayJson.addProperty("month", month);
        yearMonthDayJson.addProperty("day", day);
        yearMonthDayJson.addProperty("subscriptionCount", subscriptionService.getSubscriptionsByYearMonthAndDay(year, month, day).size());
        yearMonthDayJson.addProperty("Amount", subscriptionService.getSubscriptionsByYearMonthAndDay(year, month, day).stream().mapToDouble(SubscriptionDTO::getNetAmount).sum());
        subscriptionsByYearMonthAndDay.add(year + "-" + month + "-" + day, yearMonthDayJson);
        return subscriptionsByYearMonthAndDay;
    }

    @Override
    public JsonObject getSubscriptionsByStartDateAndEndDate(String startDate, String endDate) {
        JsonObject subscriptionsByStartDateAndEndDate = new JsonObject();
        subscriptionsByStartDateAndEndDate.addProperty("name", startDate + " to " + endDate);
        subscriptionsByStartDateAndEndDate.addProperty("startDate", startDate);
        subscriptionsByStartDateAndEndDate.addProperty("endDate", endDate);
        subscriptionsByStartDateAndEndDate.addProperty("subscriptionCount", subscriptionService.getSubscriptionsByStartDateAndEndDate(startDate, endDate).size());
        subscriptionsByStartDateAndEndDate.addProperty("Amount", subscriptionService.getSubscriptionsByStartDateAndEndDate(startDate, endDate).stream().mapToDouble(SubscriptionDTO::getNetAmount).sum());
        return subscriptionsByStartDateAndEndDate;
    }

}
