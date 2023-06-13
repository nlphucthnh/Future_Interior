package com.spring.main.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.spring.main.dao.SanPhamDAO;
import com.spring.main.entity.Item;
import com.spring.main.entity.SanPham;
import com.spring.main.service.CartService;

@SessionScope
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	SanPhamDAO dao;

	Map<String, Item> map = new HashMap<>();

	ArrayList<Item> list = new ArrayList<Item>();

	@Override
	public Item add(String id) {
		Item item = map.get(id);
		if (item == null) {
			item = new Item();
			SanPham p = new SanPham();
			List<SanPham> list = dao.findAll();
			p = list.stream().filter(it -> it.getIdSanPham() == id).collect(Collectors.toList()).get(0);
			item.setId(p.getIdSanPham());
			item.setName(p.getTenSanPham());
			item.setImg((p.getListHinhAnhSP()).toString());
			item.setPrice(p.getGiaSanPham());
			item.setQty(1);
			map.put(id, item);
		} else {
			item.setQty(item.getQty() + 1);
		}
		return item;
	}

	@Override
	public void remove(String id) {
		map.remove(id);
	}

	@Override
	public Item update(String id, int qty) {
		Item item = map.get(id);
		item.setQty(qty);
		return item;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Collection<Item> getItems() {
		return map.values();
	}

//	@Override
//	public List<Item> getItems() {
//		for (Integer i : map.keySet()) {
//			list.add(map.get(i));
//		}
//		return list;
//	}

	@Override
	public int getCount() {
		return map.values().stream().mapToInt(item -> item.getQty()).sum();
	}

	@Override
	public double getAmount() {
		return map.values().stream().mapToDouble(item -> item.getPrice() * item.getQty()).sum();
	}
}

