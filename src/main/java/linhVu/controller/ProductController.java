package linhVu.controller;

import linhVu.model.Product;
import linhVu.model.ProductForm;
import linhVu.repository.ProductRepository;
import linhVu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@PropertySource("classpath:global_config_app.properties")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    Environment env;

    @GetMapping("/products")
    public ModelAndView showProducts(){
        List<Product> productList = productService.findAll();
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("productList",productList);
        return modelAndView;
    }

    @GetMapping("/create-product")
    public String createProductForm(Model model){
        model.addAttribute("product", new ProductForm());
        return "product/create";
    }

    @PostMapping("/save-product")
    public ModelAndView createProduct(@ModelAttribute ProductForm productForm, BindingResult result) throws ParseException {

        String fileName=productService.uploadFile(productForm,result);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date lDate = formatter.parse(productForm.getCreateDate());
            // tao doi tuong de luu vao db
            Product productObject = new Product(lDate, fileName, productForm.getName(), productForm.getPrice(), productForm.getQuantity(), productForm.getDescription(), productForm.getActive());
            // luu vao db
            //productService.save(productObject);
            //productService.save(productObject);
            productService.add(productObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("product/create");
        modelAndView.addObject("product", new ProductForm());
        modelAndView.addObject("success","A product is created successfully!");
        return modelAndView;
    }

}
