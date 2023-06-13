package com.spring.main.entity;

import com.spring.main.entity.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	private String id;
	private String name;
	private String img;
	private double price;
	private int qty = 1;
}
