/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.todo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Sunrise
 */
@Controller
public class OloloController {

    @RequestMapping("/")
    public String listPipiskas() {
        return "OLOLONDEX";
    }

}
