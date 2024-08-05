package com.hemant.e_commerce.service;

import com.hemant.e_commerce.exception.ProductException;
import com.hemant.e_commerce.model.Cart;
import com.hemant.e_commerce.model.User;
import com.hemant.e_commerce.request.AddItemRequest;

public interface CartService {


    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);

}
