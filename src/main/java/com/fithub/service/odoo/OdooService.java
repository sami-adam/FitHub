package com.fithub.service.odoo;

public interface OdooService {
    void authenticate(String url, String db, String login, String password);
    String getRecords(String url, String model, String[] fields);
}
