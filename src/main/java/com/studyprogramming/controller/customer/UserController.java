package com.studyprogramming.controller.customer;

import com.studyprogramming.constant.ConstantController;
import com.studyprogramming.constant.ConstantSecurity;
import com.studyprogramming.entity.*;
import com.studyprogramming.entity.enums.*;
import com.studyprogramming.payload.UserCreationPayload;
import com.studyprogramming.payload.UserLoginPayload;
import com.studyprogramming.service.*;
import com.studyprogramming.utils.ControllerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.studyprogramming.entity.enums.EObjectName.PRODUCT;
import static com.studyprogramming.entity.enums.EObjectName.USER;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private SpecificationService specificationService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WishlistService wishlistService;

    private List<OrderItem> orderItemList = new ArrayList<>();

    private User userAuth =  null;

    @ModelAttribute
    public void addAttributes(Model model) {
        // user auth
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetailMapping) {
            UserDetailMapping userDetails = (UserDetailMapping) authentication.getPrincipal();
            userAuth = userDetails.getUser();
            model.addAttribute("userAuth", userAuth);
        } else {
            model.addAttribute("userAuth", null);
        }

        // category list
        List<Category> categoryList = categoryService.findAllParentCategories();
        model.addAttribute("categoryList", categoryList);

        // orderItemList
        model.addAttribute("orderItemSize", orderItemList == null || orderItemList.isEmpty() ? 0 : orderItemList.size());
    }

    @GetMapping(ConstantController.ROOT_PATH_CUSTOMER)
    public  String index(Model model){
        List<Category> parentCategoriesList = categoryService.findAllParentCategories();
        List<Sale> saleListProduct = saleService.getAllSalesByEndDateAndSaleConditionObjectName(PRODUCT);
        List<Sale> saleListUser = saleService.getAllSalesByEndDateAndSaleConditionObjectName(USER);
        model.addAttribute("parentCategoriesList", parentCategoriesList);
        model.addAttribute("saleListProduct", saleListProduct);
        model.addAttribute("saleListUser", saleListUser);
        return "customer/home";
    }

    @GetMapping(ConstantController.PATH_CUSTOMER_LOGIN)
    public String login(Model model){
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserLoginPayload());
        }
        return "customer/auth/login";
    }

    @PostMapping(ConstantController.PATH_CUSTOMER_LOGIN)
    public String login(@Valid @ModelAttribute("user") UserLoginPayload user, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception{
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return ControllerUtil.redirectTo(ConstantController.PATH_CUSTOMER_LOGIN);
        }

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        Authentication auth = authenticationProvider.authenticate(authReq);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);

        HttpSession session = request.getSession(true);
        session.setAttribute(ConstantSecurity.SECURITY_CONTEXT_FOR_SESSION, securityContext);

        return ControllerUtil.redirectTo(ConstantController.ROOT_PATH_CUSTOMER);
    }

    @GetMapping(ConstantController.PATH_CUSTOMER_REGISTER)
    public String register(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserCreationPayload());
        }
        model.addAttribute("genders", EGender.values());
        return "customer/register";
    }

    @PostMapping(ConstantController.PATH_CUSTOMER_REGISTER)
    public String register(@Valid @ModelAttribute("user") UserCreationPayload user, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return ControllerUtil.redirectTo(ConstantController.PATH_CUSTOMER_REGISTER);
        }
        return Optional.ofNullable(userService.createUser(user))
                .map(t -> ControllerUtil.redirectTo(ConstantController.PATH_CUSTOMER_LOGIN)) // Trả về success nếu save thành công
                .orElse(ControllerUtil.redirectTo(ConstantController.PATH_CUSTOMER_REGISTER)); // Trả về failed nếu không thành công;
    }

    @GetMapping(ConstantController.PATH_CUSTOMER_TEST)
    public String test(){
        return "customer/__Ekka/test";
    }

    @GetMapping(ConstantController.ROOT_PATH_CUSTOMER + "/" + "{categoryName}")
    public String productList(
            @PathVariable("categoryName") String categoryName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Boolean inStock,
            Model model) {

        Category category = categoryService.findByName(categoryName);

        List<Product> filteredProducts = category.getProducts().stream()
                .filter(product -> (minPrice == null || product.getPrice().compareTo(minPrice) >= 0))
                .filter(product -> (maxPrice == null || product.getPrice().compareTo(maxPrice) <= 0))
                .filter(product -> (minRating == null || productService.getAverageRating(product.getRatings()) >= minRating))
                .filter(product -> (inStock == null || (inStock && product.getPrice().compareTo(BigDecimal.ZERO) > 0)))
                .collect(Collectors.toList());

        Map<UUID, Double> productRatings = new HashMap<>();
        for (Product product : filteredProducts) {
            productRatings.put(product.getId(), productService.getAverageRating(product.getRatings()));
        }

        model.addAttribute("selectedCategoryName", categoryName);
        model.addAttribute("category", category);
        model.addAttribute("filteredProducts", filteredProducts);
        model.addAttribute("productRatings", productRatings);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("minRating", minRating);
        model.addAttribute("inStock", inStock);
        return "customer/product/list";
    }


    @GetMapping(ConstantController.PATH_CUSTOMER_PRODUCT_DETAIL + "/{uuid}")
    public String productDetails(@PathVariable("uuid") UUID uuid, Model model){
        Product product = productService.getById(uuid);
        List<Rating> rateList = product.getRatings();
        OptionalDouble average = rateList.stream().mapToInt(Rating::getScore).average();
        // wishlist
        Wishlist wishlist = null;
        if(uuid != null) {
            wishlist = wishlistService.findByProductIdAndUserId(uuid, userAuth.getId());
        }
        if(wishlist != null && !wishlist.isActive()) wishlist = null;

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("product", product);
        model.addAttribute("specification", specificationService.getById(product.getSpecificationId()));
        model.addAttribute("rateList", rateList);
        model.addAttribute("rateAverage", average.orElse(0.0));
        return "customer/product/details";
    }

    @PostMapping(ConstantController.PATH_CUSTOMER_PRODUCT_DETAIL + "/{uuid}")
    public String productDetails(@PathVariable("uuid") UUID uuid, @RequestParam("quantity") int quantity, @RequestParam("note") String note, Model model) {
        Product product = productService.getById(uuid);

        // Tìm kiếm trong danh sách orderItemList xem có sản phẩm với productId này không
        OrderItem existingOrderItem = null;
        for (OrderItem item : orderItemList) {
            if (item.getProduct().getId().equals(uuid)) {
                existingOrderItem = item;
                break;
            }
        }

        if (existingOrderItem != null) {
            // Nếu tồn tại sản phẩm trong giỏ hàng, cập nhật quantity và totalPrice
            existingOrderItem.setQuantity(existingOrderItem.getQuantity() + quantity);
            BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(existingOrderItem.getQuantity()));
            existingOrderItem.setTotalPrice(totalPrice);
            existingOrderItem.setNote(note);
        } else {
            // Nếu không tồn tại, thêm mới OrderItem vào danh sách orderItemList
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            orderItem.setTotalPrice(totalPrice);
            orderItem.setNote(note);
            orderItemList.add(orderItem);
        }

        return "redirect:/order/cart"; // Chuyển hướng về trang giỏ hàng sau khi xử lý
    }

    @GetMapping(ConstantController.PATH_CUSTOMER_ORDER_CART)
    public String orderCart(@RequestParam(value = "selectedSale", required = false) UUID saleId, Model model) {
        // Tính toán subtotal
        BigDecimal subTotal = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItemList) {
            subTotal = subTotal.add(orderItem.getTotalPrice());
        }

        // Sale added
        Sale saleAdded = null;
        if (saleId != null) saleAdded = saleService.findById(saleId);

        // Total
        BigDecimal discountPercentage = saleAdded != null ? saleAdded.getDiscountPercentage() : BigDecimal.ZERO;
        BigDecimal shippingFee = new BigDecimal("30");
        BigDecimal total = subTotal.subtract(subTotal.multiply(discountPercentage).divide(new BigDecimal("100"))).add(shippingFee);

        if (saleAdded != null) saleAdded.setDiscountPercentage(subTotal.multiply(discountPercentage).divide(new BigDecimal("100")));

        // Address
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailMapping userDetails = (UserDetailMapping) authentication.getPrincipal();
        userAuth = userDetails.getUser();

        model.addAttribute("orderItemList", orderItemList);
        model.addAttribute("subTotal", subTotal);
        model.addAttribute("EPaymentMethod", EPaymentMethod.values());
        model.addAttribute("EDelivery", EDelivery.values());
        model.addAttribute("saleAdded", saleAdded);
        model.addAttribute("total", total);
        model.addAttribute("shippingAddress", userAuth.getAddress());


        return "customer/order/cart";
    }


    @PostMapping("/order/cart/delete")
    public String deleteOrderItem(@RequestParam("productId") UUID productId) {
        // Tìm và xóa sản phẩm khỏi danh sách orderItemList
        orderItemList.removeIf(item -> item.getProduct().getId().equals(productId));

        // Chuyển hướng về trang giỏ hàng sau khi xóa
        return "redirect:/order/cart";
    }




    @PostMapping(ConstantController.PATH_CUSTOMER_ORDER_CART)
    public String handlePayment(@RequestParam(value = "shippingAddress", required = false) String shippingAddress,
                                @RequestParam(value = "EPaymentMethod") EPaymentMethod paymentMethod,
                                Model model) {

        // Create new order
        Order order = new Order();
        // Calculate total amount
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem item : orderItemList) {
            item.setOrder(order);
            totalAmount = totalAmount.add(item.getTotalPrice());
        }
        if(totalAmount.doubleValue() == 0) return "redirect:/";
        if(paymentMethod.equals(EPaymentMethod.TRANSFER)) return "redirect:/order/transfer";
        order.setTotalAmount(totalAmount);
        order.setOrderItem(orderItemList);
        order.setUser(userAuth);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus(EPaymentStatus.PENDING);
        order.setFulfilStatus(EFulfilStatus.CONFIRMATION);
        order.setDeliveryType(EDelivery.NORMAL);



        // Save order
        orderService.save(order);

        // Send email
        emailService.sendEmail(userAuth.getEmail(), "Notification", "Your order has been received, go to the order history to view progress.");

        // Send SMS (if needed)
        // smsService.sendSms(user.getPhoneNumber(), "Notification");

        // Redirect to order history or another page
        return "redirect:/order/history";
    }

    @GetMapping("/order/transfer")
    public String transfer() {
        return "customer/order/transfer";
    }

    @GetMapping("/order/sale-list")
    public String saleList(Model model) {
        // Calculate subtotal
        BigDecimal subTotal = BigDecimal.ZERO;
        if (!orderItemList.isEmpty()) {
            for (OrderItem orderItem : orderItemList) {
                subTotal = subTotal.add(orderItem.getTotalPrice());
            }
        }

        // Get all valid sales
        List<Sale> saleList = saleService.getAllSalesStillValid();

        // Filter sales based on subtotal
        BigDecimal finalSubTotal = subTotal;
        List<Sale> topSales = saleList.stream()
                .filter(sale -> {
                    BigDecimal minPrice = BigDecimal.valueOf(sale.getSaleCondition().getMinPrice());
                    return finalSubTotal.compareTo(minPrice) > 0;
                })
                .sorted(Comparator.comparing(Sale::getDiscountPercentage).reversed()) // Sort by discountPercentage descending
                .collect(Collectors.toList());

        // Filter other sales
        List<Sale> otherSales = saleList.stream()
                .filter(sale -> {
                    BigDecimal minPrice = BigDecimal.valueOf(sale.getSaleCondition().getMinPrice());
                    return finalSubTotal.compareTo(minPrice) <= 0;
                })
                .collect(Collectors.toList());

        model.addAttribute("topSales", topSales); // Add filtered top sales
        model.addAttribute("otherSales", otherSales); // Add filtered other sales
        return "customer/order/sale-list";
    }

    @GetMapping("/order/history")
    public String orderHistory(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailMapping userDetails = (UserDetailMapping) authentication.getPrincipal();
        UUID userId = userDetails.getUser().getId();

        model.addAttribute("orderList", orderService.getAllByUserId(userId));
        return "customer/order/history";
    }

    @GetMapping("/product/wishlist")
    public String wishlist(Model model) {
        if (userAuth != null) {

            List<Wishlist> wishlists = wishlistService.findAllByUserId(userAuth.getId());
            List<Product> allWishlist = wishlistService.findAllByProductIdAndUserId(wishlists);

            List<Product> newWishlist = allWishlist.stream()
                    .filter(product -> wishlists.stream().anyMatch(wishlist -> wishlist.getProductId().equals(product.getId()) && wishlist.isActive()))
                    .collect(Collectors.toList());

            List<Product> oldWishlist = allWishlist.stream()
                    .filter(product -> wishlists.stream().anyMatch(wishlist -> wishlist.getProductId().equals(product.getId()) && !wishlist.isActive()))
                    .collect(Collectors.toList());
            if(newWishlist.isEmpty()){
                model.addAttribute("newWishlist", null);
            }else {
                model.addAttribute("newWishlist", newWishlist);
            }
            if(oldWishlist.isEmpty()){
                model.addAttribute("oldWishlist", null);
            }else {
                model.addAttribute("oldWishlist", oldWishlist);
            }
        } else {
            model.addAttribute("newWishlist", null);
            model.addAttribute("oldWishlist", null);
        }

        return "customer/product/wishlist";
    }

    @PostMapping("/product/wishlist")
    public String wishlist(@RequestParam("wishlistId") UUID wishlistId) {
        if (userAuth != null) {
            wishlistService.deactivateWishlistItem(wishlistId, userAuth.getId());
        }
        return "redirect:/product/wishlist";
    }

    @PostMapping("/addOrUpdateWishlist")
    public String addWishlist(@RequestParam("productId") UUID productId) {

        if (userAuth != null) {
            Wishlist wishlist = wishlistService.findByProductIdAndUserId(productId, userAuth.getId());

            if(wishlist != null && !wishlist.isActive()){
                wishlistService.deactivateWishlistItem(productId, userAuth.getId());
            }else{
                wishlistService.addToWishlist(productId, userAuth.getId());
            }

        }
        return ControllerUtil.redirectTo(ConstantController.PATH_CUSTOMER_PRODUCT_DETAIL + "/" + productId);
    }

    @GetMapping("/sale-for-product")
    @ResponseBody
    public String saleForProduct() {
        return "/sale-for-product";
    }

    @GetMapping("/sale-for-user")
    @ResponseBody
    public String saleForUser() {
        return "/sale-for-user";
    }
}
