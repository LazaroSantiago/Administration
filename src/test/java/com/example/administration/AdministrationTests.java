package com.example.administration;

import entity.Administrator;
import jakarta.annotation.Priority;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import service.AdministratorService;

@SpringBootTest(classes = AdministrationApplication.class)
class AdministrationTests {

    @Autowired
    private AdministratorService administratorService;

    @Test
    @Priority(1)
    public void saveTest() {
        try {
            Administrator a = new Administrator(10L,"juan");
            int cant = administratorService.findAll().size();
            administratorService.save(a);
            Assert.assertEquals(cant +1, administratorService.findAll().size());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Priority(2)
    public void deleteTest() {
        try {
            int cant = administratorService.findAll().size();
            if (administratorService.findById(10L) != null){
                administratorService.delete(10L);
                Assert.assertEquals(cant -1, administratorService.findAll().size());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
