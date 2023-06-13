package com.spring.main.service;

import java.util.Collection;

import com.spring.main.entity.Item;

public interface CartService {
	Item add(String id);

	void remove(String id);

	Item update(String id, int qty);

	void clear();

	Collection<Item> getItems();

//	List<Item> getItems();

	int getCount();

	double getAmount();
}
