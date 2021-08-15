package study.example.drools.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.example.drools.core.service.DeviceService;
import study.example.drools.rest.dto.DeviceDto;

import java.util.List;

@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class IndexController {

    private final DeviceService deviceService;

    @GetMapping
    public String index(Model model) {
        List<DeviceDto> devices = deviceService.getDevices();
        model.addAttribute("devices", devices);
        return "index";
    }

}
