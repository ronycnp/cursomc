package com.prony.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	public static List<Integer> decodeIntList(String url){
		List<Integer> categorias = new ArrayList<>();
		String[] ints = url.split(",");
		
		for (int i = 0; i < ints.length; i++) {
			categorias.add(Integer.parseInt(ints[i]));
		}
		
		return categorias;
	}
	
	public static String decodeParam(String url) {
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
