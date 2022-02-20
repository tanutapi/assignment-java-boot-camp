# Entities #
* User
* UserShippingAddress
* Product
* ProductPicture
* ProductVariant
* ShoppingCart
* OrderItem
* Invoice

# Testing #
Right now, only RESTful controllers were test.

```./mvnw test```

*TODO: to test more on service classes.*

# RESTful APIs #

### POST: /login ###
Login with username and password

```
{
  "username": "",
  "password": ""
}
```

It will return a JWT if success.

```
{
  "jwtToken": "xxxxxxxxxx.yyyy.zzzz"
}
```

### GET: /users/{userId} ###
To get user's information. It will return

```
{
  "userId": 1,
  "username": "tanutapi",
  "firstName": "Tanut",
  "lastName": "Apiwong",

  // If any
  "shippingAddress": {
    "userId": 1,
    "name": "Tanut Apiwong",
    "address": "123 street road",
    "postcode": "10900",
    "district": "Chatujak",
    "province": "Bangkok",
    "telephone": "0812345678"
  }
}
```

### GET: /users/{userId}/shippingaddress ###
It will return shipping address of specified userId

```
{
  "userId": 0,
  "name": "first name and last name",
  "address": "",
  "postcode": "",
  "district": "",
  "province": "",
  "telephone": ""
}
```

### PUT: /users/{userId}/shippingaddress ###
Set the shipping address for specified userId

```
{
  "name": "first name and last name",
  "address": "",
  "postcode": "",
  "district": "",
  "province": "",
  "telephone": ""
}
```

### GET /products?q=keyword ###
Return list of products of a given keyword or all products if no keyword given.

```
[{
  "productId": 0,
  "title": "",
  "rating": 4.5,
  "ratingCnt": 100,
  "picture": "https://"
}, ...]
```

### GET /products/{productId} ###
Return a full product detail of given productId.

```
{
  "productId": 0,
  "title": "",
  "desc": "",
  "brand": "",
  "rating": 4.5,
  "ratingCnt": 100,
  "varients": [{
    "Size 30": {"price": 100},
    "Size 32": {"price": 110}
  }, ...],
  "pictures": ["https://", ...]
}
```

### GET /shoppingcarts/{userId} ###
Return a shopping cart of the specified user

```
{
  "userId": 0,
  "products": [{
    "productId": 0,
    "title": "",
    "desc": "",
    "brand": "",
    "rating": 4.5,
    "ratingCnt": 100,
    "varient": "Size 30",
    "unitPrice": 100,
    "pictures": ["https://", ...],
    "amount": 1,
    "totalPrice": 100
  }, ...],
}
```

### POST /shoppingcarts/{userId} ###
Add a new product varient into the shopping cart for specified userId.

```
{
  "productId": 0,
  "varient": "Size 32",
  "amount": 1
}
```
It will return a shopping cart of the specified user (same as above)

### DELETE /shoppingcarts/{userId}/{productId}/{variantName} ###
Remove a product with specified variant name from the user's shopping cart.
It will return a shopping cart of the specified user (same as above)

### POST /checkout/{userId}/{method} ###
Payment method:
1. "cs" - pay at counter service
2. "pp" - prompt pay
3. "bt" - bank transfer

It will return an invoice to the user
```
{
  "invoiceId": 16, 
  "userId": 1, 
  "paymentMethod": "cs", 
  "paid": false,
  "items": [{
    "userId": 1, 
    "productId": 4,
    "title": "Adidas NMD R1 Core Black", 
    "desc": "This is a product's description of Adidas NMD R1 Core Black",
    "brand": "Adidas", 
    "rating": 5.0, 
    "ratingCnt": 100, 
    "variant": "Size 32", 
    "unitPrice": 9500.0, 
    "pictures": ["http://store.com/product1_1.jpg", "http://store.com/product1_2.jpg"], 
    "amount": 2, 
    "totalPrice": 19000.0
  }, {
    "userId": 1, 
    "productId": 5, 
    "title": "Adidas NMD R1 PK Japan", 
    "desc": "This is a product's description of Adidas NMD R1 PK Japan", 
    "brand": "Adidas",
    "rating": 4.5, 
    "ratingCnt": 50, 
    "variant": "Size 35", 
    "unitPrice": 10000.0, 
    "pictures": ["http://store.com/product2_1.jpg", "http://store.com/product2_2.jpg"], 
    "amount": 2, 
    "totalPrice": 20000.0
  }], 
  "totalPrice": 39000.0
}
```
