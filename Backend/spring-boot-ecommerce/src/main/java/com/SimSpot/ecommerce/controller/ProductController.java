package com.SimSpot.ecommerce.controller;

import com.SimSpot.ecommerce.dao.ProductRepository;
import com.SimSpot.ecommerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This ProductController class sets out the REST endpoints
 * for CRUD operations for Product entities
 *
 */

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    /**
     * This method getAllProducts, is using @GetMapping to map this method
     * to /products in which once a Get method request created the contents of
     * this method will return all the products in an ArrayList
     * @param name
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String name) {
        try {
            List<Product> products = new ArrayList<Product>();

            if (name == null)
                productRepository.findAll().forEach(products::add);
            else
                productRepository.findByNameContaining(name).forEach(products::add);

            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method getProductById, is using @GetMapping to map this method
     * to /products/id in which once a Get method request is created the contents of
     *  this method will return all the products with the corresponding
     *  ID into an ArrayList
     * @param id
     * @return
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        Optional<Product> productsData = productRepository.findById(id);

        if (productsData.isPresent()) {
            return new ResponseEntity<>(productsData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method createProduct takes a Product object and using @PostMapping will
     * map the contents of that Product into the /products.
     * @param products
     * @return
     */
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product products) {
        try {
            Product _product = productRepository
                    .save(new Product(products.getName(), products.getDescription(),products.getLocation(), products.getImageUrl(), products.getPrice(), products.isOgBoxes(), products.isDeliveryMethod()));
            return new ResponseEntity<>(_product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method updateProduct will update an existing Product object
     * using @PutMapping a PUT request once the Product id is used
     * @param id
     * @param product
     * @return
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            Product _product = productData.get();
            _product.setName(product.getName());
            _product.setDescription(product.getDescription());
            _product.setLocation(product.getLocation());
            _product.setImageUrl(product.getImageUrl());
            _product.setPrice(product.getPrice());
            _product.setOgBoxes(product.isOgBoxes());
            _product.setDeliveryMethod(product.isDeliveryMethod());
//            _product.setCategory(product.getCategory());
            return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method deleteProduct takes a ID as a param, it uses @DeleteMapping / A DELETE request
     * to allow the deletion of a product entity by ID
     * @param id
     * @return
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method deleteAllProducts uses @DeleteMapping / A DELETE request to allow a deletion
     * request of all Products under the /products mapping
     * @return
     */
    @DeleteMapping("/products")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        try {
            productRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
