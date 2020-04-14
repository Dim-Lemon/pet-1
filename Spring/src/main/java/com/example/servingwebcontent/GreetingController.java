package com.example.servingwebcontent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Map;

@Controller
public class GreetingController {

    //ссылка на модель линк дб
    @Autowired
    private link_dbRepository link_DbRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        Iterable<link_db> link_db = link_DbRepository.findAll();
        model.put("link_db", link_db);
        return "greeting";
    }

    @PostMapping("/add")
    public String setAbbName(@RequestParam String link,@RequestParam String abbName , Map<String, Object> model){
        link_db link_db = new link_db(link, abbName);
        link_DbRepository.save(link_db);
        return "redirect:/";
    }



}