package com.studyprogramming.constant;

public interface ConstantController {
    public static String ROOT_PATH_ADMIN = "/admin";
    public static String ROOT_PATH_CUSTOMER = "";

    //
    // START ADMIN GROUP
    //
    public static String PATH_ADMIN_DASHBOARD = ROOT_PATH_ADMIN + "/";
    // CUSTOMER
    public static String PATH_ADMIN_MANAGEMENT_CUSTOMER = ROOT_PATH_ADMIN + "/customer";
    public static String PATH_ADMIN_MANAGEMENT_CUSTOMER_CREATION = PATH_ADMIN_MANAGEMENT_CUSTOMER + "/create";
    public static String PATH_ADMIN_LOGIN = ROOT_PATH_ADMIN + "/login";
    // PRODUCT
    public static String PATH_ADMIN_PRODUCT = ROOT_PATH_ADMIN + "/product";
    public static String PATH_ADMIN_PRODUCT_MANAGEMENT = PATH_ADMIN_PRODUCT + "/management";
    // ORDER
    public static String PATH_ADMIN_ORDER = ROOT_PATH_ADMIN + "/order";
    public static String PATH_ADMIN_ORDER_DETAIL = PATH_ADMIN_ORDER + "/detail";
    public static String PATH_ADMIN_ORDER_REFUND = PATH_ADMIN_ORDER + "/refund";
    // VENDOR
    public static String PATH_ADMIN_VENDOR = ROOT_PATH_ADMIN + "/vendor";
    public static String PATH_ADMIN_VENDOR_MANAGEMENT = PATH_ADMIN_VENDOR + "/management";
    // CATEGORY
    public static String PATH_ADMIN_CATEGORY = ROOT_PATH_ADMIN + "/category";
    public static String PATH_ADMIN_CATEGORY_MANAGEMENT = PATH_ADMIN_CATEGORY + "/management";
    // Login
    public static String ROOT_PATH_ADMIN_LOGIN = ROOT_PATH_ADMIN + "/login";
    // ADMIN ERROR
    public static String PATH_ADMIN_ERROR_403 = ROOT_PATH_ADMIN + "/403";
    public static String PATH_ADMIN_ERROR_404 = ROOT_PATH_ADMIN + "/404";
    public static String PATH_ADMIN_ERROR_500 = ROOT_PATH_ADMIN + "/500";
    // SALE
    public static String PATH_ADMIN_SALE= ROOT_PATH_ADMIN + "/sale";
    public static String PATH_ADMIN_SALE_NEW= PATH_ADMIN_SALE + "/add";
    public static String PATH_ADMIN_SALE_ADD_CONDITION= PATH_ADMIN_SALE + "/add-condition";
    public static String PATH_ADMIN_SALE_LIST_CONDITION= PATH_ADMIN_SALE + "/condition-list";
    public static String PATH_ADMIN_SALE_USED= PATH_ADMIN_SALE + "/used-sales-list";
    // END ADMIN GROUP


    //
    // CUSTOMER START
    //
    public static String PATH_CUSTOMER_LOGIN = ROOT_PATH_CUSTOMER + "/login";
    public static String PATH_CUSTOMER_REGISTER = ROOT_PATH_CUSTOMER + "/register";
    public static String PATH_CUSTOMER_TEST = ROOT_PATH_CUSTOMER + "/test";
    public static String PATH_CUSTOMER_PRODUCT_LIST = ROOT_PATH_CUSTOMER + "/product";
    public static String PATH_CUSTOMER_PRODUCT_DETAIL = PATH_CUSTOMER_PRODUCT_LIST + "/details";
    public static String PATH_CUSTOMER_ORDER = ROOT_PATH_CUSTOMER + "/order";
    public static String PATH_CUSTOMER_ORDER_CART = PATH_CUSTOMER_ORDER + "/cart";


}
