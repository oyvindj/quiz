package oj

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/ui")
class UIController {
    @RequestMapping("hello")
    ModelAndView hello() {
        return new ModelAndView("hello/main","name","Kristina!!!")
    }
}
