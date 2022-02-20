package dev.tanutapi.assignmentjavabootcamp.shoppingCart;

import dev.tanutapi.assignmentjavabootcamp.product.Product;
import dev.tanutapi.assignmentjavabootcamp.product.ProductNotFoundException;
import dev.tanutapi.assignmentjavabootcamp.product.ProductRepository;
import dev.tanutapi.assignmentjavabootcamp.productVariant.ProductVariantNotFoundException;
import dev.tanutapi.assignmentjavabootcamp.productPicture.ProductPicture;
import dev.tanutapi.assignmentjavabootcamp.productVariant.ProductVariant;
import dev.tanutapi.assignmentjavabootcamp.productVariant.ProductVariantRepository;
import dev.tanutapi.assignmentjavabootcamp.user.UserNotFoundException;
import dev.tanutapi.assignmentjavabootcamp.user.User;
import dev.tanutapi.assignmentjavabootcamp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariantRepository productVariantRepository;

    public ShoppingCartResponse getShoppingCartResponse(Integer userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException("User was not found for specified userId");
        }
        List<ShoppingCartProductResponse> productResponseList = new ArrayList<>();
        shoppingCartRepository.findByUserId(userId).forEach(shoppingCart -> {
            ShoppingCartProductResponse productResponse = new ShoppingCartProductResponse();
            Product product = shoppingCart.getProductVariant().getProduct();
            productResponse.setProductId(product.getId());
            productResponse.setTitle(product.getTitle());
            productResponse.setDesc(product.getDesc());
            productResponse.setBrand(product.getBrand());
            productResponse.setRating(product.getRating());
            productResponse.setRatingCnt(product.getRatingCnt());
            productResponse.setVariant(shoppingCart.getProductVariant().getName());
            productResponse.setUnitPrice(shoppingCart.getProductVariant().getPrice());
            productResponse.setPictures(product.getProductPictures().stream().map(ProductPicture::getUrl).collect(Collectors.toList()));
            productResponse.setAmount(shoppingCart.getAmount());
            productResponse.setTotalPrice(shoppingCart.getTotalPrice());
            productResponseList.add(productResponse);
        });
        return new ShoppingCartResponse(userId, productResponseList);
    }

    public List<ShoppingCart> getShoppingCart(Integer userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    // TODO: Need fix! product with the same variant should be added with its existing amount
    public void addProductToShoppingCart(Integer userId, Integer productId, String variant, Integer amount) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException("User was not found for specified userId");
        }

        Optional<Product> optProduct = productRepository.findById(productId);
        if (optProduct.isEmpty()) {
            throw new ProductNotFoundException("Product was not found for specified productId");
        }

        User user = optUser.get();
        Product product = optProduct.get();

        List<ProductVariant> variants = product.getProductVariants().stream().filter(productVariant -> productVariant.getName().equals(variant)).collect(Collectors.toList());
        if (variants.isEmpty()) {
            System.out.println("Product's variant was not found!");
            throw new ProductVariantNotFoundException("Product variant was not found for specified variant name");
        }

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setAmount(amount);
        shoppingCart.setProductVariant(variants.get(0));
        shoppingCart.setTotalPrice(variants.get(0).getPrice().multiply(new BigDecimal(amount)));
        shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public void remove(Integer userId, Integer productId, String variantName) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException("User was not found for specified userId");
        }

        Optional<ProductVariant> optVariant = productVariantRepository.findByProductIdAndName(productId, variantName);
        if (optVariant.isEmpty()) {
            throw new ProductNotFoundException("Product was not found for specified productId");
        }

        ProductVariant variant = optVariant.get();

        shoppingCartRepository.deleteAllByUserIdAndProductVariantId(userId, variant.getId());
    }

    @Transactional
    public void clearShoppingCart(Integer userId) {
        shoppingCartRepository.deleteAllByUserId(userId);
    }
}
