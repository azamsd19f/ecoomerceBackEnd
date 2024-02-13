package com.controller;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.MediaType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.configs.FileUploadService;
import com.dto.ProductDto;
import com.dto.ProductImageDto;
import com.entity.Product;
import com.entity.ProductImage;
import com.exception.ProductImageExistsException;
import com.exception.ResourceNotFound;
import com.repository.ImageRepository;
import com.repository.ProductRepository;
import com.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/file/")
@RestController
public class ProductFileController {

	@Value("${path.image}")
	private String path;

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private ModelMapper mapper;

	@PostMapping("upload/{productId}")
	public ResponseEntity<ProductImageDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer productId) throws IOException {
		Product product = productRepository.findById(productId).orElseThrow(() ->new ResourceNotFound("ProductId Not Found"));

		 if (product.getProductImage() != null) {
	            throw new ProductImageExistsException("Image Already Exists For product with ID: " + productId);
	        }
		String fileName = this.fileUploadService.uploadImage(path, image);
		ProductImage saveImage = new ProductImage();
		saveImage.setImageName(fileName);
		saveImage.setProduct(product);
		imageRepository.save(saveImage);
		ProductImageDto returnImageDto = this.mapper.map(saveImage, ProductImageDto.class);
		return new ResponseEntity<ProductImageDto>(returnImageDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {
	
	 InputStream resource = this.fileUploadService.getResource(path, imageName);
     response.setContentType(MediaType.IMAGE_JPEG_VALUE);
     StreamUtils.copy(resource,response.getOutputStream());
	}
}
