package ma.fstt.controller;

import ma.fstt.common.messages.BaseResponse;
import ma.fstt.dto.VolontaringDTO;
import ma.fstt.service.VolontaringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@Validated
@RestController
@RequestMapping("/volontarings")
public class VolontaringController {
    @Autowired
    private VolontaringService volontaringService;

    @GetMapping
    public ResponseEntity<List<VolontaringDTO>> getAllVolontaring() {
        List<VolontaringDTO> list = volontaringService.findVolontaringList();
        return new ResponseEntity<List<VolontaringDTO>>(list, HttpStatus.OK);
    }

    @PostMapping(value = { "/add" })
    public ResponseEntity<BaseResponse> createVolontaring(@RequestBody VolontaringDTO userDTO) {
        BaseResponse response = volontaringService.createOrUpdateVolontaring(userDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<VolontaringDTO>> getAllVolontaringByUserId(@PathVariable Long id) {
        List<VolontaringDTO> list = volontaringService.findVolontaringByUserId(id);
        return new ResponseEntity<List<VolontaringDTO>>(list, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<BaseResponse> updateVolontaring(
            @PathVariable("id") Long id,
            @RequestBody VolontaringDTO updatedVolontaringDTO) {

        BaseResponse response = volontaringService.updateVolontaring(id, updatedVolontaringDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<BaseResponse> deleteVolontaringById(@PathVariable Long id) {
        BaseResponse response = volontaringService.deleteVolontaring(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VolontaringDTO> getVolontaringById(@PathVariable Long id) {
        VolontaringDTO list = volontaringService.findByVolontaringId(id);
        return new ResponseEntity<VolontaringDTO>(list, HttpStatus.OK);
    }

}
