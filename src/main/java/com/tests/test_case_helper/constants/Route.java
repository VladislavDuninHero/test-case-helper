package com.tests.test_case_helper.constants;

public final class Route {
    public static final String API_USER_ROUTE = "/api/v1/user";
    public static final String API_TEST_CASE_ROUTE = "/api/v1/case";
    public static final String API_TEST_SUITE_ROUTE = "/api/v1/suite";
    public static final String API_PROJECT_ROUTE = "/api/v1/project";
    public static final String API_CONVERTERS_ROUTE = "/api/v1/converters";
    public static final String API_TEAMS_ROUTE = "/api/v1/team";

    public static final String API_CREATE_ROUTE = "/create";
    public static final String API_GET_ROUTE = "/{id}";
    public static final String API_UPDATE_ROUTE = "/{id}/update";
    public static final String API_DELETE_ROUTE = "/{id}/delete";

    public static final String API_REGISTRATION_ROUTE = "/registration";
    public static final String API_FULL_REGISTRATION_ROUTE = API_USER_ROUTE + API_REGISTRATION_ROUTE;
    public static final String API_LOGIN_ROUTE = "/login";
    public static final String API_FULL_LOGIN_ROUTE = API_USER_ROUTE + API_LOGIN_ROUTE;
    public static final String API_GET_PROJECT_ROUTE = "/{id}";
    public static final String API_GET_TEST_SUITE_ROUTE = "/{id}";
    public static final String API_GET_ACTIVE_TEST_SUITE_RUN_SESSION_ROUTE = "/run/active";
    public static final String API_GET_SLIM_TEST_SUITE_ROUTE = "/{id}/slim";
    public static final String API_RECOVERY_ROUTE = "/{id}/recovery";
    public static final String API_EXCEL_CONVERTER_ROUTE = "/excel";
    public static final String API_RUN_TEST_SUITE_ROUTE = "/{id}/run";
    public static final String API_DELETE_RUN_TEST_SUITE_ROUTE = "/{id}/run/{sessionId}/delete";
    public static final String API_UPDATE_RUN_TEST_SUITE_TC_RESULT_ROUTE = "/run/update";
    public static final String API_GET_RUN_TEST_SUITE_TC_RESULT_ROUTE = "/run/result/{id}";
    public static final String API_END_RUN_TEST_SUITE_SESSION_ROUTE = "/run/{id}/end";
    public static final String API_END_RUN_TEST_SUITE_SESSION_RESULTS_ROUTE = "/run/{id}/end/results";
    public static final String API_END_RUN_TEST_SUITE_SESSION_CONVERT_TO_WORD_ROUTE = "/suite/{id}/run/ended/word";
    public static final String API_ADD_TEAMMATE_ROUTE = "/{id}/teammates/add";
    public static final String API_ADD_TEAMMATE_BY_LDAP_ROUTE = "/{id}/teammates/add/ldap";
    public static final String API_DELETE_TEAMMATE_ROUTE = "/{id}/teammates/{teammateId}/delete";
}
