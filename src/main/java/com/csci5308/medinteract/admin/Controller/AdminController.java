package com.csci5308.medinteract.admin.Controller;

import com.csci5308.medinteract.admin.Model.AdminModel;
import com.csci5308.medinteract.admin.Service.AdminServiceImpl;
import com.csci5308.medinteract.utilities.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminServiceImpl adminService;

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/login")

    public ResponseEntity login(@RequestBody AdminModel adminModel)  {

        if(adminService.isAdminValid(adminModel.getAdminEmail(),adminModel.getAdminPassword()))
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
