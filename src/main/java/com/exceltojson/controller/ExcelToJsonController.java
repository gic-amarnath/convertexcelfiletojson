package com.exceltojson.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exceltojson.service.ExcelUploadService;

@RestController
@RequestMapping("/api")
public class ExcelToJsonController {
	@Autowired
	ExcelUploadService excelUploadService;
	@PostMapping(value="/uploadExl")
	public List<Map<String , String>> upload(@RequestParam("file") MultipartFile file) throws IOException{
		return excelUploadService.upload(file);
	}

}


