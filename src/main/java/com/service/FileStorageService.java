//package com.service;
//
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.bean.FrontCoverBean;
//import com.dao.FrontCoverRepository;
//import org.springframework.util.StringUtils;
//
//@Service
//public class FileStorageService {
//
//	@Autowired
//	private FrontCoverRepository frontCoverRepository;
//	public FrontCoverBean store(MultipartFile file) throws IOException{
//		String filename = StringUtils.cleanPath(file.getOriginalFilename());
//		FrontCoverBean frontCoverBean = new FrontCoverBean(filename, file.getBytes());
//		return frontCoverRepository.save(frontCoverBean);
//	}
//}
