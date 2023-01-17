package com.vithal.electronic.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vithal.electronic.exceptions.BadApiRequest;
import com.vithal.electronic.services.FileService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {


		//vikki.png
		String originalFilename = file.getOriginalFilename();
		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
		String filename = UUID.randomUUID().toString();
		String fileNameWithExtension=filename + extension;
		String fullPathWithFileName=path+fileNameWithExtension;
		
		if(extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpg")|| extension.equalsIgnoreCase(".jpeg")) 
		{
			
			//save
			File folder=new File(path);
			
			if(!folder.exists()) {
				folder.mkdirs();
			}
			
			
			//upload file
			Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
			
			return fileNameWithExtension;
			
		}
		else {
			
			throw new BadApiRequest("File Not found with this "+extension+" upload valid file");
		}
	}

	@Override
	public InputStream getResource(String path, String name) throws FileNotFoundException {
		
		String fullPath=path+File.separator+name;
		log.info("Full path {} ",fullPath);
	//	String fullPath=path+name;
		InputStream inputStream=new FileInputStream(fullPath);
		return inputStream;
	}

}
