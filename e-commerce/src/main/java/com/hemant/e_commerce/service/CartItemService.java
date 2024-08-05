package com.hemant.e_commerce.service;

import com.hemant.e_commerce.exception.CartItemException;
import com.hemant.e_commerce.exception.UserException;
import com.hemant.e_commerce.model.Cart;
import com.hemant.e_commerce.model.CartItem;
import com.hemant.e_commerce.model.Product;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId,Long id, CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId,Long cartItemId) throws CartItemException,UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;


}
