package com.studyprogramming.controller.admin;


import com.studyprogramming.constant.ConstantController;
import com.studyprogramming.controller.BaseController;
import com.studyprogramming.entity.*;
import com.studyprogramming.entity.enums.EGender;
import com.studyprogramming.entity.enums.EObjectName;
import com.studyprogramming.entity.enums.ESaleRule;
import com.studyprogramming.payload.ProductPayload;
import com.studyprogramming.payload.SalePayload;
import com.studyprogramming.payload.UserCreationPayload;
import com.studyprogramming.service.*;
import com.studyprogramming.utils.ControllerUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
//@Secured("ROLE_ADMIN")
public class AdDashboardController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleConditionService saleConditionService;

    @Autowired
    private UsedSalesListService usedSalesListService;

    @Autowired
    private NotificationService notificationService;


    @GetMapping(ConstantController.PATH_ADMIN_DASHBOARD)
    public String index(){
        return "admin/index";
    }



    @GetMapping(ConstantController.PATH_ADMIN_MANAGEMENT_CUSTOMER_CREATION)
    public String register(Model model){
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserCreationPayload());
        }
        model.addAttribute("genders", EGender.values());
        return "admin/customer/create-customer";
    }

    @PostMapping(ConstantController.PATH_ADMIN_MANAGEMENT_CUSTOMER_CREATION)
    public String register(@Valid @ModelAttribute("user") UserCreationPayload user, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return ControllerUtil.redirectTo(ConstantController.PATH_ADMIN_MANAGEMENT_CUSTOMER_CREATION);
        }
        userService.createUser(user);
        return ControllerUtil.redirectTo(ConstantController.PATH_ADMIN_MANAGEMENT_CUSTOMER_CREATION);
    }

    @GetMapping(ConstantController.PATH_ADMIN_MANAGEMENT_CUSTOMER)
    public String listCustomer( Model model){//@PathVariable String email
        List<User> userList = userService.getAllUser();
        model.addAttribute("userList", userService.getImageLink(userList));
        return "admin/customer/list-customer";
    }

    @GetMapping(ConstantController.PATH_ADMIN_PRODUCT)
    public String listProduct(Model model){
        List<Product> productList = productService.findAll();
        List<Category> categorieList = categoryService.findAll();
        model.addAttribute("productList", productList);
        model.addAttribute("categoryList", categorieList);
        return "admin/product/list-product";
    }

    @GetMapping(ConstantController.PATH_ADMIN_PRODUCT_MANAGEMENT)
    public String managementProduct(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("product", new ProductPayload());
        model.addAttribute("categories",categories);
        return "admin/product/management-product";
    }

    @PostMapping(ConstantController.PATH_ADMIN_PRODUCT_MANAGEMENT)
    public String managementProduct(@Valid @ModelAttribute("product") ProductPayload product, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("product", product);
            return ControllerUtil.redirectTo(ConstantController.PATH_ADMIN_PRODUCT_MANAGEMENT);
        }
        productService.createOrUpdate(product);

        return ControllerUtil.redirectTo(ConstantController.PATH_ADMIN_PRODUCT_MANAGEMENT);
    }

    @GetMapping(ConstantController.PATH_ADMIN_ORDER)
    public String orderList(Model model) {
        model.addAttribute("orderList", orderService.findAll());
        return "admin/order/order-list";
    }


    @GetMapping(ConstantController.PATH_ADMIN_ORDER_DETAIL)
    public String orderDetail(Model model){
        BigDecimal subtotal = orderItemService.calculateSubtotal(orderItemService.findAll());
        model.addAttribute("subtotal", subtotal);

        model.addAttribute("orderItems", orderItemService.findAll());
        return "admin/order/order-detail";
    }

    @GetMapping(ConstantController.PATH_ADMIN_ORDER_REFUND)
    public String refund(){
        return "admin/order/refund";
    }

    @GetMapping(ConstantController.PATH_ADMIN_VENDOR)
    public String managementVendor(){
        return "admin/vendor/vendor-list";
    }

    @GetMapping(ConstantController.PATH_ADMIN_VENDOR_MANAGEMENT)
    public String vendorList(Model model){
        model.addAttribute("user", new UserCreationPayload());
        return "admin/vendor/management-vendor";
    }

    @GetMapping(ConstantController.PATH_ADMIN_CATEGORY)
    public String listCategory(){
        return "admin/category/list-category";
    }

    @GetMapping(ConstantController.PATH_ADMIN_CATEGORY_MANAGEMENT)
    public String managementCategory(Model model){
        model.addAttribute("category", new Category());
        return "admin/category/management-category";
    }

    @PostMapping(ConstantController.PATH_ADMIN_CATEGORY_MANAGEMENT)
    public String managementCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("category", category);
            return ControllerUtil.redirectTo(ConstantController.PATH_ADMIN_CATEGORY_MANAGEMENT);
        }
        categoryService.createOrUpdate(category);

        return ControllerUtil.redirectTo(ConstantController.PATH_ADMIN_CATEGORY_MANAGEMENT);
    }

    @GetMapping(ConstantController.PATH_ADMIN_SALE_NEW)
    public String addSale(Model model) {
        model.addAttribute("sale", new SalePayload());
        model.addAttribute("saleConditions", saleConditionService.getAll());
        return "admin/sale/add-sale";
    }

    @PostMapping(ConstantController.PATH_ADMIN_SALE_NEW)
    public String saveSale(@Valid SalePayload sale, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("saleConditions", saleConditionService.getAll());
            model.addAttribute("sale", new SalePayload());
            return "admin/sale/add-sale";
        }

        saleService.saveSale(sale);
        model.addAttribute("sales", saleService.getAllSales());
        return "redirect:/admin/sale";
    }


    @GetMapping(ConstantController.PATH_ADMIN_SALE)
    public String saleList(Model model) {
        model.addAttribute("sales", saleService.getAllSales());
        return "admin/sale/sale-list";
    }

    @GetMapping(ConstantController.PATH_ADMIN_SALE_ADD_CONDITION)
    public String addCondition(Model model) {
        model.addAttribute("saleCondition", new SaleCondition());
        model.addAttribute("objectNames", EObjectName.values());
        model.addAttribute("saleRules", ESaleRule.values());
        return "admin/sale/add-sale-condition";
    }

    @PostMapping(ConstantController.PATH_ADMIN_SALE_ADD_CONDITION)
    public String saveCondition(@Valid SaleCondition saleCondition, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Nếu có lỗi, quay lại form để hiển thị lỗi
            model.addAttribute("objectNames", EObjectName.values());
            model.addAttribute("saleRules", ESaleRule.values());
            return "admin/sale/add-sale-condition";
        }

        // Lưu SaleCondition vào cơ sở dữ liệu
        saleConditionService.save(saleCondition);

        // Redirect về danh sách điều kiện sau khi lưu thành công
        return "redirect:/admin/sale/sale-condition-list";
    }

    @GetMapping(ConstantController.PATH_ADMIN_SALE_LIST_CONDITION)
    public String conditionList() {
        return "/admin/sale/sale-condition-list";
    }

    @GetMapping(ConstantController.PATH_ADMIN_SALE_USED)
    public String getUsedSalesList(Model model) {
        List<UsedSalesList> usedSalesLists = usedSalesListService.getAllUsedSalesLists();
        model.addAttribute("usedSalesLists", usedSalesLists);
        model.addAttribute("objectNamesMap", usedSalesListService.getObjectNamesMap(usedSalesLists));
        return "admin/sale/used-sales-list";
    }


    //send Message with Async and Scheduling
    @GetMapping("/send")
    @ResponseBody
    public String sendNotifications() {
        notificationService.sendBulkMessages();
        return "Notification sending started!";
    }

    @GetMapping("/stop")
    @ResponseBody
    public String stopNotifications() {
        notificationService.stopSendingMessages();
        return "Notification sending stopped!";
    }

}
