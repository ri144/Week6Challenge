package com.example.demo.controllers;

import com.example.demo.models.Edu;
import com.example.demo.models.Exper;
import com.example.demo.models.Person;
import com.example.demo.models.Skills;
import com.example.demo.repositories.EduRepo;
import com.example.demo.repositories.ExperRepo;
import com.example.demo.repositories.PersonRepo;
import com.example.demo.repositories.SkillsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private SkillsRepo skillsRepo;

    @Autowired
    private EduRepo eduRepo;

    @Autowired
    private ExperRepo experRepo;

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

    @RequestMapping("/person/{first}/{mid}/{last}/{email}")
    public String viewPerson(@PathVariable("first") String first,@PathVariable("mid") String mid, @PathVariable("last") String last,
                             @PathVariable("email") String email, Model model){
        Person p = personRepo.findAllByFirstnameAndLastnameAndMidinitAndEmail(first,last,mid,email).get(0);
        List<Edu> eduList = eduRepo.findAllByPersonid(p.getId());
        List<Exper> expList = experRepo.findAllByPersonid(p.getId());
        List<Skills> skills = skillsRepo.findAllByPersonid(p.getId());
        model.addAttribute("person", p);
        model.addAttribute("edu", eduList);
        model.addAttribute("exp", expList);
        model.addAttribute("skill", skills);
        return "person";
    }

}
