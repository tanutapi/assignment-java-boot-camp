package dev.tanutapi.assignmentjavabootcamp.product;

import dev.tanutapi.assignmentjavabootcamp.productVariant.ProductVariantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductFullResponse getProductFullResponse(Integer id) {
        Optional<Product> optProduct = productRepository.findById(id);
        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            ProductFullResponse productFullResponse = new ProductFullResponse();

            productFullResponse.setProductId(product.getId());
            productFullResponse.setTitle(product.getTitle());
            productFullResponse.setDesc(product.getDesc());
            productFullResponse.setRating(product.getRating());
            productFullResponse.setRatingCnt(product.getRatingCnt());

            List<String> pictures = new ArrayList<>();
            product.getProductPictures().forEach(productPicture -> pictures.add(productPicture.getUrl()));
            productFullResponse.setPictures(pictures);

            List<ProductVariantResponse> variants = new ArrayList<>();
            product.getProductVariants().forEach(productVariant -> {
                ProductVariantResponse variantResponse = new ProductVariantResponse();
                variantResponse.setName(productVariant.getName());
                variantResponse.setPrice(productVariant.getPrice());
                variants.add(variantResponse);
            });
            productFullResponse.setVariants(variants);

            return productFullResponse;
        }
        throw new ProductNotFoundException("Your specified productId was not found");
    }

    public List<Product> findProducts(String q) {
        return productRepository.findByTitleContaining(q);
    }

    public List<ProductShortResponse> getProductShortResponseList(String q) {
        ArrayList<ProductShortResponse> productList = new ArrayList<>();
        List<Product> result;
        if (q != null) {
            result = productRepository.findByTitleContaining(q);
        } else {
            result = productRepository.findAll();
        }

        result.forEach(product -> {
            ProductShortResponse productShortResponse = new ProductShortResponse();
            productShortResponse.setProductId(product.getId());
            productShortResponse.setTitle(product.getTitle());
            productShortResponse.setRating(product.getRating());
            productShortResponse.setRatingCnt(product.getRatingCnt());
            if (!product.getProductPictures().isEmpty()) {
                productShortResponse.setPicture(product.getProductPictures().get(0).getUrl());
            }
            productList.add(productShortResponse);
        });
        return productList;
    }
}
