package com.hemant.e_commerce.controller;

import com.hemant.e_commerce.exception.ProductException;
import com.hemant.e_commerce.exception.UserException;
import com.hemant.e_commerce.model.Cart;
import com.hemant.e_commerce.model.User;
import com.hemant.e_commerce.request.AddItemRequest;
import com.hemant.e_commerce.response.ApiResponse;
import com.hemant.e_commerce.service.CartService;
import com.hemant.e_commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{
        User user =userService.findUserProfileByJwt(jwt);
         Cart cart =cartService.findUserCart(user.getId());
         return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
                                                     @RequestHeader("Auhtorization") String jwt) throws UserException, ProductException{
        User user=userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(),req);

        ApiResponse res=new ApiResponse();
        res.setMessage("Item Added to Cart");
        res.setStatus(true);
        return new ResponseEntity<>(res,HttpStatus.OK);

    }



}
