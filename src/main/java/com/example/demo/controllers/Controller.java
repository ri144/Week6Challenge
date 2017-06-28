package com.example.demo.controllers;

import com.example.demo.models.Person;
import com.example.demo.repositories.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

/**
 * Created by student on 6/28/17.
 */
@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private PersonRepo personRepo;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("person", new Person());
        return "search";
    }

    @RequestMapping("/login")
    public String login(HttpSession session, Principal principal){
        return "login";
    }

    @RequestMapping("/search")
    public String search(@ModelAttribute Person person, Model model) {
        List<Person> peopleList = personRepo.findAllByFirstnameAndLastnameAndMidinit(person.getFirstname(),person.getLastname(),person.getMidinit());
        model.addAttribute("people", peopleList);
        return "results";
    }
}
