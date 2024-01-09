package ma.fstt.service;

import jakarta.transaction.Transactional;

import ma.fstt.common.exceptions.RecordNotFoundException;
import ma.fstt.common.messages.BaseResponse;
import ma.fstt.common.messages.CustomMessage;
import ma.fstt.common.utils.Topic;

import ma.fstt.dto.UserDTO;
import ma.fstt.dto.VolontaringDTO;
import ma.fstt.entity.VolontaringEntity;
import ma.fstt.repo.VolontaringRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VolontaringService {

    @Autowired
    private VolontaringRepo volontaringRepo;


    private final WebClient webClient;

    public VolontaringService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082/api/v1/auth/users").build();
    }

    public List<VolontaringDTO> findVolontaringList() {
        return volontaringRepo.findAll().stream().map(this::copyVolontaringEntityToDto).collect(Collectors.toList());
    }

    public List<VolontaringDTO> findVolontaringByUserId(Long id) {
        return volontaringRepo.findByUserId(id).stream().map(this::copyVolontaringEntityToDto).collect(Collectors.toList());
    }

    public VolontaringDTO findByVolontaringId(Long volontaringId) {
        VolontaringEntity userEntity = volontaringRepo.findById(volontaringId)
                .orElseThrow(() -> new RecordNotFoundException("Volontaring id '" + volontaringId + "' does not exist !"));
        return copyVolontaringEntityToDto(userEntity);
    }

    public BaseResponse createOrUpdateVolontaring(VolontaringDTO volontaringDTO) {
        // Vérifie d'abord l'existence de l'utilisateur avant de créer une volontaring
        Mono<UserDTO> userMono = webClient.get()
                .uri("/{id}", volontaringDTO.getUserId())
                .retrieve()
                .bodyToMono(UserDTO.class);

        // Attendez la réponse du service d'authentification
        UserDTO userResponse = userMono.block();

        if (userResponse != null && userResponse.getId() != null) {
            // L'utilisateur existe, continuez avec la création ou la mise à jour de l'volontaring
            VolontaringEntity volontaringEntity = copyVolontaringDtoToEntity(volontaringDTO);
            volontaringRepo.save(volontaringEntity);
            return new BaseResponse(Topic.ASSISTANCE.getName() + CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
        } else {
            // L'utilisateur n'existe pas, vous pouvez gérer cela en lançant une exception, par exemple
            throw new RecordNotFoundException("L'utilisateur avec l'ID " + volontaringDTO.getUserId() + " n'existe pas.");
        }

    }

    public BaseResponse updateVolontaring(Long volontaringId, VolontaringDTO updatedVolontaringDTO) {
        // Check if the volontaring with the given ID exists in the database
        if (!volontaringRepo.existsById(volontaringId)) {
            throw new RecordNotFoundException("Volontaring id '" + volontaringId + "' does not exist!");
        }

        // Find the existing VolontaringEntity by ID
        VolontaringEntity existingVolontaringEntity = volontaringRepo.findById(volontaringId)
                .orElseThrow(() -> new RecordNotFoundException("Volontaring id '" + volontaringId + "' does not exist !"));

        // Update the fields of the existing entity with the values from the updated DTO
        existingVolontaringEntity.setAvailability(updatedVolontaringDTO.getAvailability());
        existingVolontaringEntity.setSkill(updatedVolontaringDTO.getSkill());


        // Save the updated entity back to the database
        volontaringRepo.save(existingVolontaringEntity);
        return new BaseResponse(Topic.ASSISTANCE.getName() + CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());
    }

    public BaseResponse deleteVolontaring(Long volontaringId) {
        if (volontaringRepo.existsById(volontaringId)) {
            volontaringRepo.deleteById(volontaringId);
        } else {
            throw new RecordNotFoundException("No record found for given id: " + volontaringId);
        }
        return new BaseResponse(Topic.ASSISTANCE.getName() + CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
    }


    private VolontaringDTO copyVolontaringEntityToDto(VolontaringEntity volontaringEntity) {
        VolontaringDTO volontaringDTO = new VolontaringDTO();
        volontaringDTO.setVolontaringId(volontaringEntity.getVolontaringId());
        volontaringDTO.setUserId(volontaringEntity.getUserId());
        volontaringDTO.setAvailability(volontaringEntity.getAvailability());
        volontaringDTO.setSkill(volontaringEntity.getSkill());
        return volontaringDTO;
    }

    private VolontaringEntity copyVolontaringDtoToEntity(VolontaringDTO volontaringDTO) {
        VolontaringEntity userEntity = new VolontaringEntity();
        userEntity.setVolontaringId(volontaringDTO.getVolontaringId());
        userEntity.setAvailability(volontaringDTO.getAvailability());
        userEntity.setUserId(volontaringDTO.getUserId());
        userEntity.setSkill(volontaringDTO.getSkill());
        return userEntity;
    }

}
