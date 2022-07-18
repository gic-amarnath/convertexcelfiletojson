package com.exceltojson.utils;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

@Component
public class ExlUtils {

	public Supplier<Stream<Row>> getRowStreamSupplier(Iterable<Row> rows){
		return () -> getStream(rows);
	}
	public <T> Stream<T> getStream(Iterable<T> iterable){
		return StreamSupport.stream(iterable.spliterator(), false);
	}

	public Supplier<Stream<Integer>> cellIIteratorSupplier(int end){
		return () -> numbetStream(end);
	}
	public Stream<Integer> numbetStream(int end){
		return IntStream.range(0, end).boxed();
	}
}
