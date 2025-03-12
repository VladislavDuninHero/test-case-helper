package com.tests.test_case_helper.constants;

public final class Route {
    public static final String API_USER_ROUTE = "/api/v1/user";
    public static final String API_TEST_CASE_ROUTE = "/api/v1/case";
    public static final String API_TEST_SUITE_ROUTE = "/api/v1/suite";
    public static final String API_PROJECT_ROUTE = "/api/v1/project";

    public static final String API_REGISTRATION_ROUTE = "/registration";
    public static final String API_FULL_REGISTRATION_ROUTE = API_USER_ROUTE + API_REGISTRATION_ROUTE;
    public static final String API_LOGIN_ROUTE = "/login";
    public static final String API_FULL_LOGIN_ROUTE = API_USER_ROUTE + API_LOGIN_ROUTE;
    public static final String API_CREATE_ROUTE = "/create";
    public static final String API_GET_PROJECT_ROUTE = "/{id}";
}
