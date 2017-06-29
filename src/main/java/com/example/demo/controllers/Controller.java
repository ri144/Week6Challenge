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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
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
        model.addAttribute("exp", new Exper());
        model.addAttribute("edu", new Edu());
        model.addAttribute("skill", new Skills());
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
        model.addAttribute("title", "User Name");
        return "results";
    }

    @RequestMapping("/searchC")
    public String searchC(@ModelAttribute Exper exper, Model model) {
        List<Exper> expList = experRepo.findAllByCompany(exper.getCompany());
        List<Person> peopleList = new ArrayList<Person>();
        for (Exper e : expList){
            Person p = personRepo.findAllById(e.getPersonid()).get(0);
            if(!peopleList.contains(p)){
                peopleList.add(p);
            }
        }
        model.addAttribute("people", peopleList);
        model.addAttribute("title", "Company");
        return "results";
    }

    @RequestMapping("/searchS")
    public String searchS(@ModelAttribute Edu edu, Model model) {
        List<Edu> eduList = eduRepo.findAllBySchool(edu.getSchool());
        List<Person> peopleList = new ArrayList<Person>();
        for (Edu e : eduList){
            Person p = personRepo.findAllById(e.getPersonid()).get(0);
            if(!peopleList.contains(p)){
                peopleList.add(p);
            }
        }
        model.addAttribute("people", peopleList);
        model.addAttribute("title", "School");
        return "results";
    }

    @RequestMapping("/searchSk")
    public String searchSk(@ModelAttribute Skills skills, Model model) {
        List<Skills> skillList = skillsRepo.findAllBySkill(skills.getSkill());
        List<Person> peopleList = new ArrayList<Person>();
        for (Skills s : skillList){
            Person p = personRepo.findAllById(s.getPersonid()).get(0);
            if(!peopleList.contains(p)){
                peopleList.add(p);
            }
        }
        model.addAttribute("people", peopleList);
        model.addAttribute("title", "School");
        return "results";
    }

    @RequestMapping("/person/{id}")
    public String viewPerson(@PathVariable("id") Integer id, Model model){
        List<Person> people = personRepo.findAllById(id);
        List<Edu> eduList = eduRepo.findAllByPersonid(id);
        List<Exper> expList = experRepo.findAllByPersonid(id);
        List<Skills> skills = skillsRepo.findAllByPersonid(id);
        model.addAttribute("person", people);
        model.addAttribute("edu", eduList);
        model.addAttribute("exp", expList);
        model.addAttribute("skill", skills);
        return "person";
    }

   /* @PostMapping("/endorse")
    public void endorse(){

    }*/
}
