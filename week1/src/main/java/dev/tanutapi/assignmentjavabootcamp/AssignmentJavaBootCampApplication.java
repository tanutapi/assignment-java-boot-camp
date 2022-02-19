package dev.tanutapi.assignmentjavabootcamp;

import dev.tanutapi.assignmentjavabootcamp.product.Product;
import dev.tanutapi.assignmentjavabootcamp.product.ProductRepository;
import dev.tanutapi.assignmentjavabootcamp.productPicture.ProductPicture;
import dev.tanutapi.assignmentjavabootcamp.productPicture.ProductPictureRepository;
import dev.tanutapi.assignmentjavabootcamp.productVariant.ProductVariant;
import dev.tanutapi.assignmentjavabootcamp.productVariant.ProductVariantRepository;
import dev.tanutapi.assignmentjavabootcamp.user.User;
import dev.tanutapi.assignmentjavabootcamp.user.UserRepository;
import dev.tanutapi.assignmentjavabootcamp.user.UserService;
import dev.tanutapi.assignmentjavabootcamp.userShippingAddress.UserShippingAddress;
import dev.tanutapi.assignmentjavabootcamp.userShippingAddress.UserShippingAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AssignmentJavaBootCampApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentJavaBootCampApplication.class, args);
	}

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserShippingAddressRepository userShippingAddressRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductPictureRepository productPictureRepository;

	@Autowired
	private ProductVariantRepository productVariantRepository;

	@PostConstruct
	void prepareData() {
		try {
			User user1 = userService.createUser("tanutapi", "qwerty", "Tanut", "Apiwong");
			User user2 = userService.createUser("hugo", "abc123", "Hugo", "Man");
			UserShippingAddress userShippingAddress1 = new UserShippingAddress(user1, "Tanut Apiwong", "123 street road", "Chatujak", "Bangkok", "10900", "0812345678");
			UserShippingAddress userShippingAddress2 = new UserShippingAddress(user2, "Hugo Man", "789 1st-street road", "Sathorn", "Bangkok", "10120", "0898765432");
			userShippingAddressRepository.save(userShippingAddress1);
			userShippingAddressRepository.save(userShippingAddress2);
			user1.setUserShippingAddress(userShippingAddress1);
			user2.setUserShippingAddress(userShippingAddress2);
			userRepository.save(user1);
			userRepository.save(user2);

			Product product1 = new Product("Adidas NMD R1 Core Black", "This is a product's description of Adidas NMD R1 Core Black", "Adidas", 5.0f, 100);
			Product product2 = new Product("Adidas NMD R1 PK Japan", "This is a product's description of Adidas NMD R1 PK Japan", "Adidas", 4.5f, 50);
			productRepository.save(product1);
			productRepository.save(product2);

			ProductPicture productPicture1_1 = new ProductPicture(product1, "http://store.com/product1_1.jpg");
			ProductPicture productPicture1_2 = new ProductPicture(product1, "http://store.com/product1_2.jpg");
			ProductPicture productPicture2_1 = new ProductPicture(product2, "http://store.com/product2_1.jpg");
			ProductPicture productPicture2_2 = new ProductPicture(product2, "http://store.com/product2_2.jpg");
			productPictureRepository.save(productPicture1_1);
			productPictureRepository.save(productPicture1_2);
			productPictureRepository.save(productPicture2_1);
			productPictureRepository.save(productPicture2_2);

			ProductVariant productVariant1_1 = new ProductVariant(product1, "Size 32", new BigDecimal(9500));
			ProductVariant productVariant1_2 = new ProductVariant(product1, "Size 34", new BigDecimal(9750));
			ProductVariant productVariant2_1 = new ProductVariant(product2, "Size 35", new BigDecimal(10000));
			ProductVariant productVariant2_2 = new ProductVariant(product2, "Size 36", new BigDecimal(10500));
			productVariantRepository.save(productVariant1_1);
			productVariantRepository.save(productVariant1_2);
			productVariantRepository.save(productVariant2_1);
			productVariantRepository.save(productVariant2_2);

			List<ProductPicture> product1_PictureList = new ArrayList<>();
			product1_PictureList.add(productPicture1_1);
			product1_PictureList.add(productPicture1_2);
			product1.setProductPictures(product1_PictureList);

			List<ProductPicture> product2_PictureList = new ArrayList<>();
			product2_PictureList.add(productPicture2_1);
			product2_PictureList.add(productPicture2_2);
			product2.setProductPictures(product2_PictureList);

			List<ProductVariant> product1_VariantList = new ArrayList<>();
			product1_VariantList.add(productVariant1_1);
			product1_VariantList.add(productVariant1_2);
			product1.setProductVariants(product1_VariantList);

			List<ProductVariant> product2_VariantList = new ArrayList<>();
			product2_VariantList.add(productVariant2_1);
			product2_VariantList.add(productVariant2_2);
			product2.setProductVariants(product2_VariantList);

			productRepository.save(product1);
			productRepository.save(product2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
