package linhVu.service;

import linhVu.model.Product;
import linhVu.model.ProductForm;
import org.springframework.validation.BindingResult;

public interface ProductService extends GeneralService<Product> {
    String uploadFile(ProductForm productForm, BindingResult result);
}

