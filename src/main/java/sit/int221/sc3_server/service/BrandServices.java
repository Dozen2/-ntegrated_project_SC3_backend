package sit.int221.sc3_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int221.sc3_server.DTO.BrandDetailDTO;
import sit.int221.sc3_server.entity.Brand;
import sit.int221.sc3_server.exception.ItemNotFoundException;
import sit.int221.sc3_server.repository.BrandRepository;
import sit.int221.sc3_server.repository.ProductRepository;

import java.util.List;

@Service
public class BrandServices {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;
    public List<Brand> getAllBrand() {
        return brandRepository.findAll();
    }


    public BrandDetailDTO getBrandById(int id){
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Brand " + id +" not found"));
        int saleItemCount = productRepository.countByBrandId(brand.getId());
        BrandDetailDTO dto = new BrandDetailDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        dto.setWebsiteUrl(brand.getWebSiteUrl());
        dto.setCountryOfOrigin(brand.getCountryOfOrigin());
        dto.setActive(brand.getIsActive());
        dto.setNoOfSaleItems(saleItemCount);
        return dto;
    }


    //Basic Delete
    public void deleteBrand(int id){
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Brand not found"));
        boolean hasProduct = productRepository.existsByBrand_Id(brand.getId());
        if(hasProduct){
           throw new RuntimeException("Cannot delete brand because it has products");
        }
        brandRepository.deleteById(brand.getId());


    }
}
