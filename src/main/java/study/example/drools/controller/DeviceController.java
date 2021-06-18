package study.example.drools.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.example.drools.domain.Device;
import study.example.drools.dto.DeviceAddRequest;
import study.example.drools.dto.DeviceStatusResponse;
import study.example.drools.service.DeviceService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.controller
 * 클래스 명   : DeviceController
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 *
 * @author skyun
 * @date 2021-06-18
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rule")
public class DeviceController {

    private final DeviceService deviceService;

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
    public ResponseEntity<?> changeSensorValue(@RequestParam("value") int value) {
        deviceService.updateSensorData(value);
        return ResponseEntity.ok("온도 값 설정 완료");
    }
}