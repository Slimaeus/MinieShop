package com.master.minieshop;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.master.minieshop.entity.*;
import com.master.minieshop.enumeration.*;
import com.master.minieshop.key.PromotionDetailKey;
import com.master.minieshop.repository.ProductRepository;
import com.master.minieshop.repository.UserRepository;
import com.master.minieshop.service.*;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class MinieShopApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LoyaltyCardService loyaltyCardService;

    @Autowired
    private PromotionService promotionService;
    private static Logger logger = LoggerFactory.getLogger(MinieShopApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(MinieShopApplication.class, args);

        logger.info("debug enabled: {}", logger.isDebugEnabled());
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        if (userRepository.count() > 0
                || categoryService.any()
                || productService.any()
                || imageService.any()
                || orderService.any())
            return args -> {
            };
        return args -> {
            Random random = new Random();

            // region User Seed Data
            String password = passwordEncoder.encode("P@ssw0rd");
            AppUser thai = createUser("thai", "thai@gmail.com", "Nguyen Hong Thai", Gender.Male, "0123456789", password,
                    Role.Manager);
            AppUser mei = createUser("mei", "mei@gmail.com", "Truong Thuc Van", Gender.Female, "0987654321", password,
                    Role.Manager);
            AppUser user = createUser("user", "user@gmail.com", "User", Gender.Female, "0987654322", password,
                    Role.LoyalCustomer);
            AppUser manager = createUser("manager", "manager@gmail.com", "Manager", Gender.Female, "0987654323", password,
                    Role.Manager);

            userRepository.save(thai);
            userRepository.save(mei);
            userRepository.save(user);
            userRepository.save(manager);

            LoyaltyCard thaiLoyaltyCard = createLoyaltyCard(10, LoyaltyCardStatus.Closed, thai);
            LoyaltyCard meiLoyaltyCard = createLoyaltyCard(50, LoyaltyCardStatus.Closed, mei);

            loyaltyCardService.save(thaiLoyaltyCard);
            loyaltyCardService.save(meiLoyaltyCard);

            thai.setLoyaltyCard(thaiLoyaltyCard);
            mei.setLoyaltyCard(meiLoyaltyCard);
            userRepository.save(thai);
            userRepository.save(mei);

            // endregion

            // region Category Seed Data
            Category cake = createCategory("Bánh", "banh", "Đa dạng các loại bánh ngon", CategoryStatus.Open);
            Category iceCream = createCategory("Kem", "kem", "Những món tráng miệng kem mát lạnh",
                    CategoryStatus.Closed);
            Category cookies = createCategory("Bánh quy", "banh-quy", "Những món bánh quy thú vị", CategoryStatus.Closed);
            Category pie = createCategory("Bánh bông lan", "banh-bong-lan", "Bánh bông lan giòn ngon",
                    CategoryStatus.Open);
            Category pudding = createCategory("Bánh pudding", "banh-pudding", "Những món bánh pudding mềm mịn",
                    CategoryStatus.Closed);
            Category chocolate = createCategory("Sô cô la", "so-co-la", "Những món sô cô la ngọt ngào",
                    CategoryStatus.Open);
            Category tart = createCategory("Bánh tart", "banh-tart", "Những món bánh tart ngọt chua",
                    CategoryStatus.Open);
            Category mousse = createCategory("Bánh mousse", "banh-mousse", "Những món bánh mousse nhẹ nhàng",
                    CategoryStatus.Closed);
            Category parfait = createCategory("Bánh parfait", "banh-parfait", "Những món bánh parfait xếp lớp tuyệt vời",
                    CategoryStatus.Open);
            Category trifle = createCategory("Bánh trifle", "banh-trifle", "Bánh trifle với những lớp hương vị",
                    CategoryStatus.Open);

            categoryService.save(cake);
            categoryService.save(iceCream);
            categoryService.save(cookies);
            categoryService.save(pie);
            categoryService.save(pudding);
            categoryService.save(chocolate);
            categoryService.save(tart);
            categoryService.save(mousse);
            categoryService.save(parfait);
            categoryService.save(trifle);

            // endregion



            // region Product Seed Data
            List<Category> categories = categoryService.getAll();

            Product pannaCotta = createProduct("Panna Cotta", "panna-cotta",
                    "Món tráng miệng Ý mịn màng với hương vị vani", ProductStatus.Closed, 20000,
                    pudding);
            Product flan = createProduct("Flan", "classic-flan", "Món tráng miệng kem caramel mềm mịn",
                    ProductStatus.Open, 20000, pudding);
            Product tiramisu = createProduct("Tiramisu", "tiramisu",
                    "Món tráng miệng Ý xếp lớp với cà phê và mascarpone", ProductStatus.Closed, 20000,
                    cake);

            Product chocolateCake = createProduct("Chocolate Cake", "chocolate-cake", "Bánh sô cô la đậm đà và ẩm mịn",
                    ProductStatus.Closed, 20000,
                    cake);


            productService.save(pannaCotta);
            productService.save(flan);
            productService.save(chocolateCake);
            productService.save(tiramisu);
            // endregion

// region Image Seed Data
            List<Product> products = productService.getAll();

            Image flanImage = createImage("qriqgvzxbxluixofnicp",
                    "https://res.cloudinary.com/dlrcjwqxz/image/upload/v1686856769/qriqgvzxbxluixofnicp.png",
                    "flan", ImageStatus.Open,
                    flan);
            Image tiramisuImage = createImage("xmqtuxj20f4si2mterw8",
                    "https://res.cloudinary.com/dlrcjwqxz/image/upload/v1686857364/xmqtuxj20f4si2mterw8.png",
                    "tiramisu", ImageStatus.Closed,
                    tiramisu);

            imageService.save(flanImage);
            imageService.save(tiramisuImage);
            // endregion

            // region Comment Seed Data
            Comment comment1 = createComment(5, "Nice!", thai, getRandomProduct(products, random));
            Comment comment2 = createComment(4, "Good", mei, getRandomProduct(products, random));

            commentService.save(comment1);
            commentService.save(comment2);
            // endregion

            // region Promotion Seed Data
            Promotion promotion1 = createPromotion("Hello", "Code for new user", 0.1, 0, PromotionStatus.Open,
                    PromotionType.Global, LocalDate.now(), LocalDate.now());
            Promotion promotion2 = createPromotion("HelloMember", "Code for new loyalty member", 0.2, 0,
                    PromotionStatus.Open, PromotionType.LoyaltyDiscount, LocalDate.now(), LocalDate.now());
            Promotion promotion3 = createPromotion("HelloGoodMember", "Code for good loyalty member", 0.4, 0,
                    PromotionStatus.Open, PromotionType.LoyaltyDiscount, LocalDate.now(), LocalDate.now());

            promotionService.save(promotion1);
            promotionService.save(promotion2);
            promotionService.save(promotion3);

            List<PromotionDetail> promotionDetails = new ArrayList<>();

            PromotionDetail promotionDetail1 = createPromotionDetail(3, 3, thai, promotion2);
            PromotionDetail promotionDetail2 = createPromotionDetail(3, 3, thai, promotion3);

            promotionDetails.add(promotionDetail1);
            promotionDetails.add(promotionDetail2);

            promotionService.savePromotionDetails(promotionDetails);

            // endregion

            // region Order Seed Data
            Order order1 = createOrder("1", "John Doe", "123456789", Gender.Male,
                    "123 Main St", PaymentMethod.Cash, "Please deliver ASAP",
                    50000, 0, 10000, 40000, OrderStatus.Pending, thai);

            Set<OrderDetail> orderDetails1 = new HashSet<>();
            for (int i = 0; i < 3; i++) {
                Product product = getRandomProduct(products, random);
                OrderDetail orderDetail = createOrderDetail(product, order1, 3, 300000);
                orderDetails1.add(orderDetail);
            }

            order1.setOrderDetails(orderDetails1);
            orderService.save(order1);
            // endregion

        };
    }

    private Category getRandomCategory(List<Category> categories, Random random) {
        int index = random.nextInt(categories.size());
        return categories.get(index);
    }

    private Product getRandomProduct(List<Product> products, Random random) {
        int index = random.nextInt(products.size());
        return products.get(index);
    }

    private Product createProduct(String title, String name, String description, ProductStatus status, double price,
            Category category) {
        Product product = new Product();
        product.setTitle(title);
        product.setName(name);
        product.setDescription(description);
        product.setStatus(status);
        product.setPrice(price);
        product.setCategory(category);
        return product;
    }

    private Comment createComment(int rate, String content, AppUser user, Product product) {
        Comment comment = new Comment();

        comment.setRate(rate);
        comment.setContent(content);
        comment.setUser(user);
        comment.setProduct(product);

        return comment;
    }

    private Image createImage(String id, String link, String title, ImageStatus status, Product product) {
        Image image = new Image();
        image.setId(id);
        image.setLink(link);
        image.setTitle(title);
        image.setStatus(status);
        image.setProduct(product);

        return image;
    }

    private Category createCategory(String title, String name, String description, CategoryStatus status) {
        Category category = new Category();
        category.setTitle(title);
        category.setName(name);
        category.setDescription(description);
        category.setStatus(status);
        return category;
    }

    private AppUser createUser(String username, String email, String fullName, Gender gender, String phoneNumber,
            String password, Role role) {
        AppUser user = new AppUser();
        user.setUserName(username);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    private LoyaltyCard createLoyaltyCard(int point, LoyaltyCardStatus status, AppUser user) {
        LoyaltyCard loyaltyCard = new LoyaltyCard();

        loyaltyCard.setPoint(point);
        loyaltyCard.setStatus(status);
        loyaltyCard.setUser(user);

        return loyaltyCard;
    }

    private Promotion createPromotion(String code, String title, double value, int quantity, PromotionStatus status,
            PromotionType type, LocalDate startedAt, LocalDate endedAt) {
        Promotion promotion = new Promotion();

        promotion.setCode(code);
        promotion.setTitle(title);
        promotion.setValue(value);
        promotion.setQuantity(quantity);
        promotion.setRemain(quantity);
        promotion.setStatus(status);
        promotion.setType(type);
        promotion.setStartedAt(startedAt);
        promotion.setEndedAt(endedAt);

        return promotion;
    }

    private PromotionDetail createPromotionDetail(int quantity, int remain, AppUser user, Promotion promotion) {
        PromotionDetail promotionDetail = new PromotionDetail();

        PromotionDetailKey promotionDetailKey = new PromotionDetailKey();
        promotionDetailKey.setUserId(user.getId());
        promotionDetailKey.setPromotionId(promotion.getId());

        promotionDetail.setId(promotionDetailKey);
        promotionDetail.setQuantity(quantity);
        promotionDetail.setRemain(remain);
        promotionDetail.setUser(user);
        promotionDetail.setPromotion(promotion);

        return promotionDetail;
    }

    public Order createOrder(String id, String customerName, String phoneNumber, Gender gender,
            String address, PaymentMethod paymentMethod, String note,
            double totalPrice, double discountPrice, double shippingCost,
            double totalBill, OrderStatus status, AppUser user) {
        Order order = new Order();
        order.setId(id);
        order.setCustomerName(customerName);
        order.setPhoneNumber(phoneNumber);
        order.setGender(gender);
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus(PaymentStatus.Unpaid);
        order.setNote(note);
        order.setTotalPrice(totalPrice);
        order.setDiscountPrice(discountPrice);
        order.setShippingCost(shippingCost);
        order.setTotalBill(totalBill);
        order.setStatus(status);
        order.setUser(user);
        return order;
    }

    private OrderDetail createOrderDetail(Product product, Order order, int quantity, double totalPrice) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setOrder(order);
        orderDetail.setQuantity(quantity);
        orderDetail.setTotalPrice(totalPrice);
        return orderDetail;
    }
}
