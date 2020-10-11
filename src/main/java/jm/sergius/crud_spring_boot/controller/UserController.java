package jm.sergius.crud_spring_boot.controller;

import jm.sergius.crud_spring_boot.domain.Role;
import jm.sergius.crud_spring_boot.domain.User;
import jm.sergius.crud_spring_boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.Set;

@Controller
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/admin")
    public String getAllUsers(ModelMap model) {
        model.addAttribute("listOfUsers", userRepository.findAll());
        return "index";
    }

    @GetMapping(value = "/admin/edit")
    public String editUser(@RequestParam long id, ModelMap model) {
        User user = userRepository.getOne(id);
        model.addAttribute("user", user);
        Role[] roles = user.getRoles().toArray(new Role[0]);
        if(roles.length == 1) {
            model.addAttribute("id1", roles[0].getId());
        } else if(roles.length == 2) {
            if (roles[0].getRole().equals("ROLE_USER")) {
                model.addAttribute("id1", roles[0].getId());
                model.addAttribute("id2", roles[1].getId());
            } else if (roles[0].getRole().equals("ROLE_ADMIN")){
                model.addAttribute("id1", roles[1].getId());
                model.addAttribute("id2", roles[0].getId());
            }
        }
        return "edit";
    }

    @PostMapping(value = "/admin/edit")
    public String updateUser(@ModelAttribute("user") User user, @ModelAttribute("role1") Role role1,
                             @ModelAttribute("role2") Role role2) {

        Set<Role> rSet;
        if(role2.getRole() != null){
            rSet = Set.of(role1, role2);
        } else {
            rSet = Set.of(role1);
        }

        user.setRoles(rSet);
        userRepository.saveAndFlush(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/add")
    public String addUser() {
        return "add";
    }

    @PostMapping(value = "/admin/add")
    public String saveUser(@ModelAttribute("user") User user, @ModelAttribute("role1") Role role1,
                           @ModelAttribute("role2") Role role2){
        Set<Role> rSet;
        if(role2.getRole() != null){
            rSet = Set.of(role1, role2);
        } else {
            rSet = Set.of(role1);
        }

        user.setRoles(rSet);
        userRepository.saveAndFlush(user);;
        return "redirect:/admin";
    }

    @GetMapping(value="/admin/delete")
    public String deleteUser(@RequestParam long id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/clearAll")
    public String clearAll() {
        userRepository.deleteAllInBatch();
        return "redirect:/admin";
    }

    @GetMapping(value = "/user")
    public String getUserinfo(ModelMap model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "userinfo";
    }

    @GetMapping(value = "/")
    public String initPage() {
        return "redirect:/login";
    }
}
