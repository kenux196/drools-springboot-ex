package study.example.drools.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.TempSensor;
import study.example.drools.core.service.DroolsService;
import study.example.drools.core.service.RuleService;
import study.example.drools.rest.dto.DeviceAddRequest;
import study.example.drools.rest.dto.DeviceStatusResponse;
import study.example.drools.core.service.DeviceService;
import study.example.drools.rest.dto.RuleDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rules")
public class RuleController {

    private final DeviceService deviceService;
    private final RuleService ruleService;
    private final DroolsService droolsService;

    @PostMapping("/device")
    public ResponseEntity<?> addDevice(@RequestBody @Valid DeviceAddRequest request) {
        for (int i = 0; i < request.getDeviceCount(); i++) {
            final Device airConditioner = Device.createAirConditioner(false);
            deviceService.addDevice(airConditioner);
        }
        return ResponseEntity.ok("Added device count : " + deviceService.getDevices().size());
    }

    @GetMapping("/device")
    public ResponseEntity<?> getDevices() {
        final List<Device> devices = deviceService.getDevices();
        final List<DeviceStatusResponse> responses = devices.stream().
                map(device -> DeviceStatusResponse.builder()
                        .id(device.getDeviceId())
                        .type(device.getType())
                        .operation(device.getOperating())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/sensor")
    public ResponseEntity<?> changeSensorValue(@RequestParam("value") int value,
                                               @RequestParam("deviceId") int deviceId) {
        TempSensor tempSensor = new TempSensor(deviceId, value, 39, 3);
        droolsService.validateRule(tempSensor);
        droolsService.fireAllRules();
        return ResponseEntity.ok("온도 값 설정 완료");
    }

    @GetMapping
    public ResponseEntity<?> getRules() {
        final List<String> rules = droolsService.getRules();
        return ResponseEntity.ok(rules);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getRules(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(ruleService.getRule(id));
    }

    @PostMapping
    public ResponseEntity<?> createRule(@RequestBody RuleDto ruleDto) {
        final Long ruleId = ruleService.createRule(ruleDto);
        return ResponseEntity.ok("created rule : " + ruleId);
    }
//
//    @PostMapping(name = "/drools")
//    public ResponseEntity<?> createDroolsRule(@RequestBody MonitoringDeviceInfo monitoringDeviceInfo) {
//        final SingleStatusRule singleStatusRule = DroolsService.createSingleStatusRule(
//                monitoringDeviceInfo.getRuleId(),
//                monitoringDeviceInfo.getDeviceId(),
//                monitoringDeviceInfo.getOperation(),
//                monitoringDeviceInfo.getTemperature(),
//                monitoringDeviceInfo.getComparator());
//        droolsService.addRule3(singleStatusRule);
//        return ResponseEntity.ok("oK");
//    }

    @GetMapping("/monitoring-start")
    public ResponseEntity<?> startMonitoring() {
        droolsService.validateRules();
        return ResponseEntity.ok("true");
    }
}