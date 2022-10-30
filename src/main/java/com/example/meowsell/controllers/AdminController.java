package com.example.meowsell.controllers;


import com.example.meowsell.services.UserService;
import com.example.meowsell.models.User;
import com.example.meowsell.models.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;
@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;
    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("users",userService.list());
                return "admin";
    }
    @PostMapping("/admin/user/ban/{id}")
    public String userBan(@PathVariable("id")Long id){
        userService.banUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user")User user,Model model){
        model.addAttribute("user",user);
        model.addAttribute("roles",Role.values());
        return "user-edit";
    }
    @GetMapping("/admin/user/edit")
    public String userEdit(@PathVariable("userId")User user,@RequestParam Map<String,String> form){
        userService.changeUserRole(user,form);
        return "redirect:/admin";
    }

}
