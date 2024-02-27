# Application acceptance criteria #

### Problem Statement ###
You are implementing a part of a shopping platform. Your task is to design and implement a
service that will provide a REST API for calculating a given product's price based on the number
of products ordered.


#### Functional Requirements ####
* Products in the system are identified by UUID.
* There should be the possibility of applying discounts based on two policies:
o Amount-based - the more pieces of the product are ordered, the bigger the
discount is (e.g., 10 items – 2 USD off, 100 items – 5 USD off etc).
o Percentage-based - user gets a percentage discount – the more items ordered;
the bigger the percentage discount is. (e.g., 10 items, 3% off, 50 items, 5% off etc).
* Policies should be configurable.

#### Non-functional requirements ####
* Use Java >= 17 and frameworks of your choice
* The project should be containerized and easy to build and run via Gradle or Maven.
* Please provide a README file with instructions on how to launch it
* There's no need for full test coverage. Implement only the most essential (in your
opinion) tests
* Use the git repository for developing the project and after you’re done, send us a link to
it
* Make sure we can run the project easily, without any unnecessary local dependencies
(e.g., Don’t use OS-specific code)
o Try not to spend more than one or two evenings on the assignment
* You will eventually have a chance to explain your code in the next stage of the interview
* If you must make some assumptions, document them as you see fit. DO the same with
the technical assumptions you make.



# How run this application #

1. clone this repository
2. ```cd ***shopping-backend*** directory```
3. ```./gradlew bootRun```

### How load custom discount policy configuration ####

Launch application with additional parameter ***discountsConfigPath*** pointing to the location of YAML file with percentage and absolute discount thresholds configured. See ***application.yaml*** for the reference. Eg:

```
./gradlew bootRun -PappliactionConfigPath=/path/to/your/application.yml
```


### How add new products ####

Use any client (curl, Postman etc.) and invoke POST request to ***localhost:8080/v1/product/add*** along with  JSON request body eg:

```json
{
    "name": "prod",
    "price": 10.00
}

```

in response you will receive the UUID of the newly created product eg. ```0ba7fda8-b1d5-4660-95a1-5b62a67e5c64```

### How calculate price for given amount of products ####
Use any client (curl, Postman etc.) and invoke POST request to ***localhost:8080/v1/price/calculate*** along with JSON request body like below where the ***productId*** is populated with UUID (0ba7fda8-b1d5-4660-95a1-5b62a67e5c64) of existing product

```json
{
"productId": "0ba7fda8-b1d5-4660-95a1-5b62a67e5c64",
"itemsCount": 100,
"discountType": "PERCENTAGE"
}
```

as result, you will receive ***FinalPrice*** calculation like below:

```json
{
"totalPrice": 1000.00,
"totalPriceAfterDiscount": 900.00,
"discountApplied": 10,
"discountType": "PERCENTAGE"
}
```

Feel free to use postman request collection attached at: ***postman/shopping-backend-collection.json***

### Please note... ####
This is just a clunky, fast written example without:
* OpenAPI generated stubs
* Real persistence
* Sufficient test coverage apart from two integration tests in ***PriceCalculationIntegrationTest*** class
* Use of more readable testing frameworks like: Spock or RestAssured