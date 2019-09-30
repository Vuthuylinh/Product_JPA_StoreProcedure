package linhVu.repository.impl;

import linhVu.model.Product;
import linhVu.repository.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
@Transactional
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Product> findAll() {
//        TypedQuery<Product> query= em.createQuery("select p from Product p", Product.class);
//        return query.getResultList();

       List<Product> productList = em.createNamedQuery("findAllProducts")
       .getResultList();
       return productList;
    }

    @Override
    public void save(Product product) {
    if(product.getId()!=null){
        em.merge(product);
    }else {
        em.persist(product);
    }
    }

    @Override
    public void add(Product product) {
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        String createDate = sdf.format(product.getCreateDate());
        StoredProcedureQuery spAddProduct = em.createNamedStoredProcedureQuery("addProduct");
        spAddProduct.setParameter("active" , product.getActive());
        spAddProduct.setParameter("createDate", Timestamp.valueOf(createDate));
        spAddProduct.setParameter("description", product.getDescription());
        spAddProduct.setParameter("image", product.getImage());
        spAddProduct.setParameter("name", product.getName());
        spAddProduct.setParameter("price", product.getPrice());
        spAddProduct.setParameter("quantity", product.getQuantity());
        spAddProduct.execute();
    }
}
