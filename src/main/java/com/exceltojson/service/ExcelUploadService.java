package com.exceltojson.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exceltojson.utils.ExlUtils;

@Service
public class ExcelUploadService {
	private final ExlUtils exlUtils;
	public ExcelUploadService(ExlUtils exlUtils) {
		this.exlUtils=exlUtils;
	}
	public List<Map<String, String>> upload (MultipartFile file) throws IOException {
		Path tempdir=Files.createTempDirectory("");
		File tempFile=tempdir.resolve(file.getOriginalFilename()).toFile();
		file.transferTo(tempFile);
		DataFormatter formatter = new DataFormatter();
		Workbook workbook=WorkbookFactory.create(tempFile);
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Sheet sheet=workbook.getSheetAt(0);

		Supplier<Stream<Row>>streamSupplirer=exlUtils.getRowStreamSupplier(sheet);

		Row headerRow=streamSupplirer.get().findFirst().get();

		List<String>hedercell=exlUtils.getStream(headerRow).map((cell) -> formatter.formatCellValue(cell, evaluator)).collect(Collectors.toList());
		int colCount=hedercell.size();
		System.out.println("hedercell >>> "+hedercell);
		return streamSupplirer.get().skip(1).map(row ->{
			List<String> cellList=exlUtils.getStream(row)
					.map((cell) -> formatter.formatCellValue(cell, evaluator))
					.collect(Collectors.toList());
			return exlUtils.cellIIteratorSupplier(colCount).get()
					.collect(Collectors.toMap(hedercell::get, cellList::get));
		}).collect(Collectors.toList());
	}
}
