package com.itcss.md5;

import org.junit.Test;

import com.itcss.bos.utils.MD5Utils;

public class Md5Test {
	
	@Test
	public void fun1() {
		String md5 = MD5Utils.md5("1234");
		System.out.println("md5"+md5);
	}

}
