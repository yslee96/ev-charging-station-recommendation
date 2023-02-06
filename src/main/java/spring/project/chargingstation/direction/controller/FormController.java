package spring.project.chargingstation.direction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import spring.project.chargingstation.direction.dto.InputDto;
import spring.project.chargingstation.domain.service.ChargingStationRecommendationService;

@Controller
@RequiredArgsConstructor
public class FormController {

    private final ChargingStationRecommendationService chargingStationRecommendationService;

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @PostMapping("/search")
    public ModelAndView postDirection(@ModelAttribute InputDto inputDto){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("output");
        modelAndView.addObject("outputFormList",
                chargingStationRecommendationService.RecommendChargingStationList(inputDto.getAddress()));

        return modelAndView;
    }
}
