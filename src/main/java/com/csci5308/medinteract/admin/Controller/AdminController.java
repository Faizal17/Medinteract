package com.csci5308.medinteract.admin.Controller;

import com.csci5308.medinteract.admin.Model.AdminModel;
import com.csci5308.medinteract.admin.Service.AdminService;
import com.csci5308.medinteract.utilities.PasswordEncodeDecode;
import com.csci5308.medinteract.utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/login")

    public ResponseEntity login(@RequestBody AdminModel adminModel) throws Exception {

//        String decodedPwd = PasswordEncodeDecode.decrypt(adminModel.getAdminPassword());
        String encodedpwd = PasswordEncodeDecode.encrypt(adminModel.getAdminPassword());
        System.out.println(encodedpwd);
        if(adminService.isAdminValid(adminModel.getAdminEmail(),encodedpwd))
        {
            Response res = new Response(adminModel, false, "Admin log-in Successful!");
            return  new ResponseEntity<>(res.getResponse(), HttpStatus.OK);

        }
        else {
            Response  res = new Response("null", true, "Admin log-in Failed!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }
}
