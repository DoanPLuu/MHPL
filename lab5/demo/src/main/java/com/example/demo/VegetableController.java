package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.ArrayList;

@Controller
public class VegetableController {
    private static ArrayList<Vegetable> list = new ArrayList<>();

    static {
        list.add(new Vegetable(1, "Tomato", "Kg", 30, 20000));
        list.add(new Vegetable(2, "Watermelon", "Kg", 50, 30000));
    }

    @GetMapping("/vegetable")
    public String homepage(Model model) {
        model.addAttribute("list", list);
        return "vegetable/index";
    }
    
    @GetMapping("/vegetable/add")
    public String showAddForm(Model model) {
        model.addAttribute("vegetable", new Vegetable());
        return "vegetable/add";
    }

    @PostMapping("/vegetable/save")
    public String save(@ModelAttribute("vegetable") Vegetable vegetable, Model model) {
        list.add(vegetable);
        model.addAttribute("list", list);
        return "redirect:/vegetable";
    }
    
    @GetMapping("/vegetable/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Vegetable vegetable = list.get(id - 1);
        model.addAttribute("vegetable", vegetable);
        return "vegetable/edit";
    }

    @PostMapping("/vegetable/update")
    public String update(@ModelAttribute("vegetable") Vegetable vegetable) {
        int index = findIndexOfVegetable(vegetable.getVegetableID());
        if (index >= 0) {
            list.set(index, vegetable);
        }
        return "redirect:/vegetable";
    }

    private int findIndexOfVegetable(int vegetableID) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getVegetableID() == vegetableID) {
                return i;
            }
        }
        return -1;
    }
}