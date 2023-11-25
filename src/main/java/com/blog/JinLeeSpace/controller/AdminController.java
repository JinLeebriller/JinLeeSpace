package com.blog.JinLeeSpace.controller;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/admin/test")
    public String adminTest() {
        return "/admin/test";
    }

}
