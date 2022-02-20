package dev.tanutapi.assignmentjavabootcamp.invoice;

import dev.tanutapi.assignmentjavabootcamp.orderItem.OrderItem;
import dev.tanutapi.assignmentjavabootcamp.orderItem.OrderItemRepository;
import dev.tanutapi.assignmentjavabootcamp.orderItem.OrderItemResponse;
import dev.tanutapi.assignmentjavabootcamp.productPicture.ProductPicture;
import dev.tanutapi.assignmentjavabootcamp.shoppingCart.ShoppingCart;
import dev.tanutapi.assignmentjavabootcamp.shoppingCart.ShoppingCartEmptyException;
import dev.tanutapi.assignmentjavabootcamp.shoppingCart.ShoppingCartService;
import dev.tanutapi.assignmentjavabootcamp.user.UnknownUserException;
import dev.tanutapi.assignmentjavabootcamp.user.User;
import dev.tanutapi.assignmentjavabootcamp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    OrderItemRepository orderItemRepository;

    InvoiceResponse checkout(Integer userId, String method) {
        // Check for valid payment method ('cs', 'pp', 'bt')
        // cs - pay at counter service, pp - prompt pay, bt - bank transfer
        if (!method.equals("cs") && !method.equals("pp") && !method.equals("bt")) {
            throw new InvalidPaymentMethodException("Only cs, pp, and bt are supported");
        }

        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new UnknownUserException("User was not found for specified userId");
        }
        User user = optUser.get();

        List<ShoppingCart> shoppingCarts = shoppingCartService.getShoppingCart(userId);
        if (shoppingCarts.isEmpty()) {
            throw new ShoppingCartEmptyException("No item in the user's shopping cart");
        }

        Invoice invoice = new Invoice();
        invoice.setUser(user);
        invoice.setPaid(false);
        invoice.setMethod(method);
        invoiceRepository.save(invoice);
        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        shoppingCarts.forEach(shoppingCart -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setUser(user);
            orderItem.setProductVariant(shoppingCart.getProductVariant());
            orderItem.setAmount(shoppingCart.getAmount());
            orderItem.setTotalPrice(shoppingCart.getTotalPrice());
            orderItem.setInvoice(invoice);
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);

            OrderItemResponse orderItemResponse = new OrderItemResponse();
            orderItemResponse.setUserId(shoppingCart.getUser().getId());
            orderItemResponse.setTitle(shoppingCart.getProductVariant().getProduct().getTitle());
            orderItemResponse.setDesc(shoppingCart.getProductVariant().getProduct().getDesc());
            orderItemResponse.setBrand(shoppingCart.getProductVariant().getProduct().getBrand());
            orderItemResponse.setRating(shoppingCart.getProductVariant().getProduct().getRating());
            orderItemResponse.setRatingCnt(shoppingCart.getProductVariant().getProduct().getRatingCnt());
            orderItemResponse.setProductId(shoppingCart.getProductVariant().getProduct().getId());
            orderItemResponse.setUnitPrice(shoppingCart.getProductVariant().getPrice());
            orderItemResponse.setPictures(shoppingCart.getProductVariant().getProduct().getProductPictures().stream().map(ProductPicture::getUrl).collect(Collectors.toList()));
            orderItemResponse.setVariant(shoppingCart.getProductVariant().getName());
            orderItemResponse.setAmount(shoppingCart.getAmount());
            orderItemResponse.setTotalPrice(shoppingCart.getTotalPrice());
            orderItemResponses.add(orderItemResponse);
        });
        BigDecimal totalPriceForThisInvoice = totalPriceForThisInvoice = orderItemResponses.stream().map(OrderItemResponse::getTotalPrice).reduce(BigDecimal::add).get();
        invoice.setItems(orderItems);
        invoice.setTotalPrice(totalPriceForThisInvoice);
        invoiceRepository.save(invoice);

        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setInvoiceId(invoice.getId());
        invoiceResponse.setUserId(invoice.getUser().getId());
        invoiceResponse.setPaymentMethod(invoice.getMethod());
        invoiceResponse.setPaid(invoice.getPaid());
        invoiceResponse.setItems(orderItemResponses);
        invoiceResponse.setTotalPrice(totalPriceForThisInvoice);

        return invoiceResponse;
    }
}
