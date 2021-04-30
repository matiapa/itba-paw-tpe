package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ErrorController{

    static Map<String, String> CODE_DESC = new HashMap<String, String>() {{
        put("403", "Acceso prohibido");
        put("404", "Página no encontrada");
    }};

    @RequestMapping("/error/{code}")
    public ModelAndView error404(
        @PathVariable(value="code") String code
    ){
        final ModelAndView mav = new ModelAndView("error");
        mav.addObject("code", code);
        mav.addObject("description", CODE_DESC.get(code));
        return mav;
    }

}