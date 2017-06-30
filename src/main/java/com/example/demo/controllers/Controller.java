package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repositories.*;
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

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String myprofile(Principal principal, Model model) {

        User u = userRepository.findByUsername(principal.getName());
        List<Person> people = personRepo.findAllByUserid(u.getId());
        try{    // assume its a job seeker
            Integer id = people.get(0).getId();
            model.addAttribute("person", people);
            model = getPersonDataById(id, model, true);
            return "person";
        }
        catch(IndexOutOfBoundsException e){
            //must be a recruiter if they dont have a corresponding profile
            model.addAttribute("person", new Person());
            model.addAttribute("exp", new Exper());
            model.addAttribute("edu", new Edu());
            model.addAttribute("skill", new Skills());
            return "search";
        }
    }

    @RequestMapping("/s")
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

    @RequestMapping("/create")
    public String create(Model model){

        return "create";
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
        model.addAttribute("person", people);
        model = getPersonDataById(id, model, false);
        return "person";
    }

    @RequestMapping("/edit/{id}")
    public String editInfo(Model model, @PathVariable("id") Integer id){
        List<Person> people = personRepo.findAllById(id);
        model.addAttribute("p", people.get(0));
        model = getPersonDataById(id, model, true);
        return "recommend";
    }

    @PostMapping("/editProf/{id}")
    public String persEdit(@ModelAttribute Person person, Model model, @PathVariable("id") Integer id){
        person.setUserid(id);
        personRepo.save(person);
        model.addAttribute("person", person);
        model = getPersonDataById(person.getId(), model, true);
        return "person";
    }

    @RequestMapping("/editEdu/{id}")
    public String eduEdit(@PathVariable("id") Integer id, Model model){
        Edu ed = eduRepo.findAllByEduid(id).get(0);
        model.addAttribute("ed", ed);
        return "editedu";
    }

    @RequestMapping("/eduResult/{id}/{eduid}")
    public String eduEdit(@ModelAttribute Edu edu, Model model, @PathVariable("id") Integer id, @PathVariable("eduid") Integer eduid){
        edu.setPersonid(id);
        edu.setEduid(eduid);
        eduRepo.save(edu);
        List<Person> people = personRepo.findAllById(id);
        model.addAttribute("person", people.get(0));
        model = getPersonDataById(id, model, true);
        return "person";
    }

    @RequestMapping("/editExp/{id}")
    public String expEdit(@PathVariable("id") Integer id, Model model){
        Exper exp = experRepo.findAllByExpid(id).get(0);
        model.addAttribute("exp", exp);
        return "editexp";
    }

    @RequestMapping("/expResult/{id}/{expid}")
    public String eduEdit(@ModelAttribute Exper exp, Model model, @PathVariable("id") Integer id, @PathVariable("expid") Integer expid){
        exp.setPersonid(id);
        exp.setExpid(expid);
        experRepo.save(exp);
        List<Person> people = personRepo.findAllById(id);
        model.addAttribute("person", people.get(0));
        model = getPersonDataById(id, model, true);
        return "person";
    }

    @RequestMapping("/editSkill/{id}")
    public String skillEdit(@PathVariable("id") Integer id, Model model){
        Skills skill = skillsRepo.findAllBySkid(id).get(0);
        model.addAttribute("skill", skill);
        return "editskill";
    }

    @RequestMapping("/skillResult/{id}/{skid}")
    public String eduEdit(@ModelAttribute Skills skills, Model model, @PathVariable("id") Integer id, @PathVariable("skid") Integer skid){
        skills.setPersonid(id);
        skills.setSkid(skid);
        skillsRepo.save(skills);
        List<Person> people = personRepo.findAllById(id);
        model.addAttribute("person", people.get(0));
        model = getPersonDataById(id, model, true);
        return "person";
    }

    @RequestMapping("/addEdu/{id}")
    public String addEdu(@PathVariable("id") Integer id, Model model){
        Edu ed = new Edu();
        ed.setPersonid(id);
        model.addAttribute("ed", ed);
        return "editedu";
    }

    @RequestMapping("/addWork/{id}")
    public String addWork(@PathVariable("id") Integer id, Model model){
        Exper exper = new Exper();
        exper.setPersonid(id);
        model.addAttribute("exp", exper);
        return "editexp";
    }

    @RequestMapping("/addSkill/{id}")
    public String addSkill(@PathVariable("id") Integer id, Model model){
        Skills skills = new Skills();
        skills.setPersonid(id);
        model.addAttribute("skill", skills);
        return "editskill";
    }

    private Model getPersonDataById(int id, Model model, boolean profile){
        List<Edu> eduList = eduRepo.findAllByPersonid(id);
        List<Exper> expList = experRepo.findAllByPersonid(id);
        List<Skills> skills = skillsRepo.findAllByPersonid(id);
        model.addAttribute("edu", eduList);
        model.addAttribute("exp", expList);
        model.addAttribute("skill", skills);
        if(profile) {
            model.addAttribute("edit", "Edit your profile.");
        }
        return model;
    }
}
