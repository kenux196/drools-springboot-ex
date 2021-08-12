package study.example.drools.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.SingleStatusRule;
import study.example.drools.core.domain.TempSensor;
import study.example.drools.core.service.DroolsService;
import study.example.drools.rest.dto.DeviceAddRequest;
import study.example.drools.rest.dto.DeviceStatusResponse;
import study.example.drools.core.service.DeviceService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/drools")
public class DeviceController {

    private final DeviceService deviceService;
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
                        .id(device.getId())
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

    @GetMapping("/rules")
    public ResponseEntity<?> getRules() {
        final List<String> rules = droolsService.getRules();
        return ResponseEntity.ok(rules);
    }

    @PostMapping("/rules")
    public ResponseEntity<?> createRule(@RequestBody SingleStatusRule singleStatusRule) {
        droolsService.addRule3(singleStatusRule);
        return ResponseEntity.ok("oK");
    }

    @GetMapping("/monitoring-start")
    public ResponseEntity<?> startMonitoring() {
        droolsService.validateRules();
        return ResponseEntity.ok("true");
    }
}