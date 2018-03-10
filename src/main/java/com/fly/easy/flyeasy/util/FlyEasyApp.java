package com.fly.easy.flyeasy.util;

import java.util.function.Function;

public interface FlyEasyApp {

	static <T, E> T ofNullable(E e, Function<E, T> function) {
		if (e == null) {
			return null;
		} else {
			return function.apply(e);
		}

	}
}
