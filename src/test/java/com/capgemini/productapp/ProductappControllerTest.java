package com.capgemini.productapp;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductappControllerTest {

	@Mock
	private ProductService productService;
	
	@InjectMocks
	private ProductController productController; 
	
	private MockMvc mockMvc;
	
    Product product;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
		product = new Product();
		product.setProductId(123456);
		product.setProductName("Yamaha");
		product.setProductPrice(100000);
		product.setProductCategory("bikes");
	}
	
	@Test
	public void addProductTest()throws Exception {
		
		
		
		when(productService.addProduct(Mockito.isA(Product.class))).thenReturn(product);
		
		mockMvc.perform(post("/product/")
			   .contentType(MediaType.APPLICATION_JSON_UTF8)
			   .content("{\"productId\":123456, \"productName\": \"Yamaha\", \"productPrice\":100000, \"productCategory\":\"bikes\"  }")
			   .accept(MediaType.APPLICATION_JSON))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.productId").exists())
			   .andExpect(jsonPath("$.productName").exists())
			   .andExpect(jsonPath("$.productCategory").exists())
			   .andExpect(jsonPath("$.productPrice").exists())
			   .andDo(print());
	
	}
	@Test
	public void findProductByIdTest() throws Exception {
		
		when(productService.findProductById(123456)).thenReturn(product);
		
		mockMvc.perform(get("/products/123456").accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		   .andExpect(jsonPath("$.productId").exists())
		   .andExpect(jsonPath("$.productName").exists())
		   .andExpect(jsonPath("$.productCategory").exists())
		   .andExpect(jsonPath("$.productPrice").exists())
		   .andExpect(jsonPath("$.productId").value(123456))
		   .andExpect(jsonPath("$.productName").value("Yamaha"))
		   .andExpect(jsonPath("$.productCategory").value("bikes"))
		   .andExpect(jsonPath("$.productPrice").value(100000))
		   .andDo(print());
	}
	@Test
	public void updateProductTest() throws Exception {
		
		when(productService.findProductById(123456)).thenReturn(product);
		
		product.setProductName("Honda");
		
		when(productService.updateProduct(Mockito.isA(Product.class))).thenReturn(product);
		
		mockMvc.perform(put("/product")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"productId\":123456, \"productName\": \"Honda\", \"productPrice\":100000, \"productCategory\":\"bikes\"  }")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").exists())
				.andExpect(jsonPath("$.productName").exists())
				.andExpect(jsonPath("$.productCategory").exists())
				.andExpect(jsonPath("$.productPrice").exists())
				.andExpect(jsonPath("$.productId").value(123456))
				.andExpect(jsonPath("$.productName").value("Honda"))
				.andExpect(jsonPath("$.productCategory").value("bikes"))
				.andExpect(jsonPath("$.productPrice").value(100000))
				.andDo(print());
		
		verify(productService).findProductById(product.getProductId());
	}
	@Test
	public void deleteProductTest() throws Exception{
		
		when(productService.findProductById(123456)).thenReturn(product);
		
		mockMvc.perform(delete("/products/123456")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	
}}