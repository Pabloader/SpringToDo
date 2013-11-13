/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Sunrise
 */
@Controller
public class OloloController {

    @RequestMapping("/pipiska{id}")
    public String listPipiskas(Model ui, @PathVariable("id") String id) {
        ui.addAttribute("ololoshka", "pipiska" + id);
        return "OLOLONDEX";
    }

}
